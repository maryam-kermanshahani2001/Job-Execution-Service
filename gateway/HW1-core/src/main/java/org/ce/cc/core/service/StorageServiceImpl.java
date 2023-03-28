
package org.ce.cc.core.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ce.cc.core.entity.enumeration.Language;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

//    public String uploadFileToS3(MultipartFile file, String email) {
    public String uploadFileToS3(MultipartFile file, long uploadId) {
        File fileObj = convertMultiPartFileToFile(file);
//        String fileName = email + "_" + file.getOriginalFilename() + System.currentTimeMillis();
        String fileName = String.valueOf(uploadId);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
//        boolean deleteResult = fileObj.delete();
        return "File uploaded : " + fileName;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {

            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    public File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }







/*    private String bucketName = "mycloudcomputing-course-bucket";
    private String putObjectUrl = "https://s3.ir-thr-at1.arvanstorage.com/{Bucket}/{Key}";

    public boolean uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        return true;
    }

    public File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartfile to file", e);

        }
        return convertedFile;
    }*/
}
