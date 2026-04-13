package com.sky.properties;


import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "minio")
@Component
@Data
public class MinioProperties {
    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucket;
    @Bean
    public MinioClient minioClient(){
        return  MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey)
                .build();
    }
}
