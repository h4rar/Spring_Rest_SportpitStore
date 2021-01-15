package h4rar.jwt.token.demo.service.impl;

import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import h4rar.jwt.token.demo.exception.*;
import h4rar.jwt.token.demo.service.S3Services;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class S3ServicesImpl implements S3Services {
    private final AmazonS3 s3client;

    @Value("${jsa.s3.bucket}")
    private String bucketName;

    public S3ServicesImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    @Override
    public S3ObjectInputStream downloadFile(String filename, String pathS3) {
        S3ObjectInputStream s3is;
        try {
            S3Object object = s3client.getObject(bucketName + pathS3, filename);
            s3is = object.getObjectContent();
        }
        catch(Exception e) {
            throw new NotFoundException("Документ не найден");
        }
        return s3is;
    }

    @Override
    public void uploadFile(MultipartFile multipartFile, String uniqueName, String pathS3) {
        InputStream inputStream = null;
        try {
            byte[] bytesMultipartFile = multipartFile.getBytes();
            inputStream = new ByteArrayInputStream(bytesMultipartFile);
            long contentLength = (long)bytesMultipartFile.length;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentLength);
//            s3client.putObject(bucketName + pathS3, uniqueName, inputStream, metadata);

            s3client.putObject(new PutObjectRequest(bucketName + pathS3, uniqueName, inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ServiceUnavailableException("Загрузка файлов временно недоступна");
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
