package br.com.aws.controller;

//import com.amazonaws.services.s3.AmazonS3;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/s3")
//public class S3Controller {
//
//    @Autowired
//    private AmazonS3 amazonS3;
//
//    @PostMapping("/create-bucket")
//    public String createBucket(@RequestParam String bucketName) {
//        if (!amazonS3.doesBucketExistV2(bucketName)) {
//            amazonS3.createBucket(bucketName);
//            return "Bucket '" + bucketName + "' criado com sucesso.";
//        } else {
//            return "Bucket '" + bucketName + "' já existe.";
//        }
//    }
//}

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//@RequestMapping("/api/s3")
//public class S3Controller {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @PostMapping("/create-bucket")
//    public ResponseEntity<String> createBucket(@RequestParam String bucketName) {
//        // Define a URL para criar um bucket no LocalStack S3
//        String createBucketUrl = "http://localhost:4566/createBucket/" + bucketName;
//
//        // Envie uma solicitação POST para criar o bucket
//        ResponseEntity<String> response = restTemplate.postForEntity(createBucketUrl, null, String.class);
//
//        // Verifique a resposta e retorne uma mensagem apropriada
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return ResponseEntity.ok("Bucket '" + bucketName + "' criado com sucesso.");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erro ao criar o bucket '" + bucketName + "'.");
//        }
//    }
//}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create-bucket")
    public ResponseEntity<String> createBucket(@RequestParam String bucketName) {
        // Define a URL para criar um bucket no LocalStack S3
        String createBucketUrl = "http://localhost:4566/createBucket/" + bucketName;

        // Envie uma solicitação POST para criar o bucket
        ResponseEntity<String> response = restTemplate.postForEntity(createBucketUrl, null, String.class);

        // Verifique a resposta e retorne uma mensagem apropriada
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Bucket '" + bucketName + "' criado com sucesso.");
        } else {
            // Se o status não for 200 (OK), você pode considerar que houve um erro
            // e retornar uma mensagem de erro com o status HTTP
            return ResponseEntity.status(response.getStatusCode())
                    .body("Erro ao criar o bucket '" + bucketName + "'.");
        }
    }
}


