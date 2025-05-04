package ru.java.maryan.api.transactionnotificationservice.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class AwsConfig {
    @Value("${spring.data.aws.access-key-id}")
    private String accessKeyId;

    @Value("${spring.data.aws.secret-key}")
    private String secretKey;

    @Value("${spring.data.aws.region}")
    private String region;

    @Value("${spring.data.aws.endpoint-url}")
    private String endpointUrl;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretKey);

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(URI.create(endpointUrl))
                .forcePathStyle(true)
                .build();
    }
}
