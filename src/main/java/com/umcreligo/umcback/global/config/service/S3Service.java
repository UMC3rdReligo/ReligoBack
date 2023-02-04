package com.umcreligo.umcback.global.config.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Service
@NoArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;

    public static final String CLOUD_FRONT_DOMAIN_NAME = "https://d2m054qjd5qsz5.cloudfront.net";

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(this.region)
            .build();
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID().toString();
        String extension = StringUtils.trimToNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        String s3FileName = (extension != null) ? (fileName + "." + extension) : fileName;

        //파일 사이즈를 s3에 알려줌
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        objMeta.setContentType(multipartFile.getContentType());

        s3Client.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return CLOUD_FRONT_DOMAIN_NAME + "/" + s3FileName;
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
