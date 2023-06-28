package com.maersk.crm.utilities.etl_util;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.Api_Body;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.EventBuilder;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 10/20/2021
 */
public class S3_util {
    public AmazonS3 s3Client;
    String accessKey;
    String secretKey;
    public S3_util(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public S3_util() {
        initAwsS3Client();
    }

    public void initAwsS3Client() {
//        InstanceProfileCredentialsProvider mInstanceProfileCredentialsProvider = new InstanceProfileCredentialsProvider();
//        AWSCredentials credentials = mInstanceProfileCredentialsProvider.getCredentials();
        System.setProperty("aws.accessKeyId",System.getProperty("GITHUBTOKEN"));
        System.setProperty("aws.secretKey",System.getProperty("GITHUBSECRET"));
        s3Client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain());
    }
    public List<String> listOfBuckets() {
        List<Bucket> buckets = s3Client.listBuckets();
        List<String> bucketsList = new ArrayList<>();
        for (Bucket bucket : buckets) {
            bucketsList.add(bucket.getName());
        }
        return bucketsList;
    }

    public void downloadObjectFromS3(String bucketName, String s3Key) throws IOException {
        S3Object obj = s3Client.getObject(new GetObjectRequest(bucketName, s3Key));
        System.out.println("Content-Type: " + obj.getObjectMetadata().getContentType());
//        File file = new File("path" + s3Key);
        File file = new File(s3Key);
        displayText(obj.getObjectContent());

//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> jsonMap = mapper.readValue(obj.getObjectContent().toString(), Map.class);
//        Api_Body api_res = new EventBuilder()
//                .body(jsonMap.toString())
//                .name("")
//                .build();
        // if the directory does not exist, create it
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    public void downloadObjectFromS3(String bucketName, String s3Key, String destination) throws IOException {
        S3Object obj = s3Client.getObject(new GetObjectRequest(bucketName, s3Key));
        System.out.println("Content-Type: " + obj.getObjectContent());
        FileUtils.copyInputStreamToFile(obj.getObjectContent(), new File(destination));
    }

    public void uploadObject(String bucketName, String key, String filePath){
        System.out.println("bucketName = " + bucketName);
        System.out.println("key = " + key);
        System.out.println("filePath = " + filePath);
        s3Client.putObject(
                bucketName,
                key,
                new File(filePath)
        );
    }

    private static void displayText(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            sb.append(line);
            System.out.println(line);
        }
    }

    public void close() {
        if (s3Client != null) {
            s3Client.shutdown();
        }
    }

    public List<String> getAllFilesInFolder(String bucketName, String path){
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(path);
        List<String> keys = new ArrayList<>();
        ObjectListing objects = s3Client.listObjects(listObjectsRequest);
        while (true) {
            List<S3ObjectSummary> summaries = objects.getObjectSummaries();
            if (summaries.size() < 1) {
                break;
            }
            for (S3ObjectSummary s : summaries) {
                keys.add(s.getKey());
            }
            objects = s3Client.listNextBatchOfObjects(objects);
        }
       return keys;
    }

    public static JsonObject getJsonFromTempFolder(String path, String fileName) {
        JsonObject data = null;
        try {
            JsonParser parser = new JsonParser();
             data = (JsonObject) parser.parse(new FileReader(path+fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }



}
