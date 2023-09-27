package br.com.localstack.localstack.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final AmazonS3 amazonS3;

    @Autowired
    public S3Controller(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @GetMapping("/list-buckets")
    public List<Bucket> listBuckets() {
        List<Bucket> buckets = amazonS3.listBuckets();
        return buckets;
    }

    @Bean
    public String createBucket(AmazonS3 amazonS3) {
        String bucketName = "meu-s3";

        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(new CreateBucketRequest(bucketName));
            return "Bucket criado com sucesso!";
        } else {
            return "O bucket já existe.";
        }
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String bucketName = "meu-s3";
        String objectKey = file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, file.getInputStream(), metadata));

            return "Arquivo enviado com sucesso para o bucket " + bucketName;
        } catch (AmazonS3Exception e) {
            return "Erro ao enviar o arquivo para o bucket " + bucketName + ": " + e.getMessage();
        }
    }


}
