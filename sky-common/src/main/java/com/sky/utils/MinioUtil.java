package com.sky.utils;




import com.sky.properties.JwtProperties;
import com.sky.properties.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinioUtil {
    @Autowired
    private MinioProperties minioConfig;
    @Autowired
    private MinioClient minioClient;

    /**
     * 上传文件到MinIO
     * @param file 要上传的文件（Spring MultipartFile）
     * @return 文件在MinIO中的唯一标识（对象名称）
     */
    public String uploadFile(MultipartFile file) throws  Exception{
        // 1. 检查存储桶是否存在，不存在则创建
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucket()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConfig.getBucket()).build());
        }

        // 2. 生成唯一文件名（避免重名）
        String originalFilename = file.getOriginalFilename();  // example.txt
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")); // .txt
        String objectName = UUID.randomUUID().toString() + fileExtension;  // 随机字符串 + .txt
        //也可以加目录，比如 objectName = "test/" + UUID.randomUUID().toString() + fileExtension;

        // 3. 上传文件
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioConfig.getBucket())          // 存储桶名称
                        .object(objectName)          // 对象名称（文件名,可以包含目录, test/随机字符串.txt）
                        .stream(file.getInputStream(), file.getSize(), -1)  // 文件流, 文件大小, 每次读取上传大小，-1表示自动
                        .contentType(file.getContentType())  // 文件类型，比如 image/jpeg
                        .build());

        return objectName;
        //返回结果：随机字符串.txt
    }
    /**
     * 获取文件临时访问URL（适合前端直接下载）
     * @param objectName 文件在MinIO中的唯一标识
     * @param expiry 有效期（单位：分钟）
     * @return 可访问的URL
     */
    public String getFileUrl(String objectName, int expiry) throws Exception {
        //objectName = "随机字符串.txt"
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(minioConfig.getBucket())
                        .object(objectName)
                        .expiry(expiry, TimeUnit.MINUTES)
                        .build());
        //返回结果：http://localhost:9000/bucket/随机字符串.txt?......
    }
    /**
     * 删除文件
     * @param objectName 文件在MinIO中的唯一标识
     */
    public void deleteFile(String objectName) throws Exception {
        //objectName = "随机字符串.txt"
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minioConfig.getBucket())
                        .object(objectName)
                        .build());
    }
    //后续需要研究一下minio的路径问题，需要解决。
}

