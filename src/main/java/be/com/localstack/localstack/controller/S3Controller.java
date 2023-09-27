package be.com.localstack.localstack.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
