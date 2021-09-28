package com.singhmnm;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;
import java.util.Calendar;

public class CreatePresignedURL {
    public static void main(String[] args) {
		
        Regions regions = Regions.AP_SOUTH_1;
        String bucketName = "{Provide Bucket Name}";
        String objectKey = "{File that needs to be downloaded}";
        String accessKey = "{accessKey}";
        String secretKey = "{secretKey}";

        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                    .withRegion(regions)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            Calendar expiration = Calendar.getInstance();
            expiration.add(Calendar.MINUTE, 10);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration.getTime());


            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            System.out.println("Presigned URL : " + url);

        } catch (AmazonServiceException ase) {
            ase.printStackTrace();

        } catch (SdkClientException sce) {
            sce.printStackTrace();
        }
    }
}
