package br.com.localstack.localstack.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(new CreateBucketRequest(bucketName));
        }
    }
    public List<Bucket> listBuckets() {
        return amazonS3.listBuckets();
    }

    public void uploadFile(String bucketName, MultipartFile file) throws IOException {
        // Gere um nome único para o arquivo usando UUID
        String key = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);

            // O arquivo foi carregado com sucesso
            System.out.println("Arquivo carregado com sucesso: " + key);
        } catch (Exception e) {
            // Lide com exceções de carregamento de arquivo aqui
            e.printStackTrace();
            throw new IOException("Erro ao fazer upload do arquivo para o Amazon S3", e);
        }
    }


    public S3Object getObject(String bucketName, String key) {
        return amazonS3.getObject(bucketName, key);
    }

    public void deleteObject(String bucketName, String key) {
        amazonS3.deleteObject(bucketName, key);
    }

    public List<S3ObjectSummary> listObjects(String bucketName) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        return objectListing.getObjectSummaries();
    }
}
