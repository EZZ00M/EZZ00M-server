package ezz00m.ezz00mserver.global.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;



@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Uploader {

/*    @Value("${cloud.aws.s3.domain}")
    private String CLOUD_FRONT_DOMAIN_NAME;*/

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String newFilename = UUID.randomUUID() + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3Client.putObject(bucket, newFilename, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {

        }

        return amazonS3Client.getUrl(bucket, newFilename).toString();
        //return CLOUD_FRONT_DOMAIN_NAME+"/"+newFilename;
    }
}
