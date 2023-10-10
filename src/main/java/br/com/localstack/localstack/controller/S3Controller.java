package br.com.localstack.localstack.controller;


import br.com.localstack.localstack.service.S3Service;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/listBuckets")
    public List<Bucket> listBuckets() {
        List<Bucket> buckets = s3Service.listBuckets();
        return buckets;
    }


    @PostMapping("/createBucket/{bucketName}")
    public String createBucket(@PathVariable String bucketName) {
        s3Service.createBucket(bucketName);
        return "Bucket criado com sucesso: " + bucketName;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileToS3(@RequestParam("bucketName") String bucketName,
                                                 @RequestParam("file") MultipartFile file) {
        try {
            s3Service.uploadFile(bucketName, file);
            return ResponseEntity.ok("Arquivo carregado com sucesso no bucket " + bucketName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao fazer upload do arquivo: " + e.getMessage());
        }
    }


    @GetMapping("/getObject/{bucketName}/{key}")
    public S3Object getObject(@PathVariable String bucketName, @PathVariable String key) {
        return s3Service.getObject(bucketName, key);
    }

    @DeleteMapping("/deleteObject/{bucketName}/{key}")
    public String deleteObject(@PathVariable String bucketName, @PathVariable String key) {
        s3Service.deleteObject(bucketName, key);
        return "Objeto excluído com sucesso!";
    }

    @GetMapping("/listObjects/{bucketName}")
    public List<S3ObjectSummary> listObjects(@PathVariable String bucketName) {
        return s3Service.listObjects(bucketName);
    }
}
