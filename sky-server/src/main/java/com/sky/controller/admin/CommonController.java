package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/common")
@Api(value="通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private MinioUtil minioService;
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public Result<String> uploadFile( MultipartFile file) {
        try {
            String objectName = minioService.uploadFile(file);log.info("文件上传成功");
            String url= minioService.getFileUrl(objectName, 3600);
            log.info(url);
            return Result.success( url);
        } catch (Exception e) {
            log.info("文件上传失败"+e.getMessage());
            return Result.error(MessageConstant.UPLOAD_FAILED + e.getMessage());
        }
    }
//    //
//    @GetMapping("getUrl")
//    public Result<String> getFileUrl(@RequestParam String objectName) {
//        try {
//            String url = minioService.getFileUrl(objectName, 3600);
//            return Result.ok(url);
//        } catch (Exception e) {
//            return Result.status(500).body("获取文件URL失败: " + e.getMessage());
//        }
//    }
//    // 删除文件
//    @GetMapping("/delete")
//    public Result<String> deleteFile(@RequestParam String objectName) {
//        try {
//            minioService.deleteFile(objectName);
//            return Result.ok("文件删除成功");
//        } catch (Exception e) {
//            return Result.status(500).body("文件删除失败: " + e.getMessage());
//        }
//    }
}
