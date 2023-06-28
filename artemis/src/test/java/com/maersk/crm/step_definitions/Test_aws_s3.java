package com.maersk.crm.step_definitions;

import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.etl_util.S3_util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 10/20/2021
 */
public class Test_aws_s3 {
   private static S3_util S3;
   public static String Response_Job="QE/Dialer/Response/out/";
   public static String Response_Job_in="QE/Dialer/Response/in/";

   public void init(){
      S3=new S3_util(ConfigurationReader.getProperty("S3accessKey"),ConfigurationReader.getProperty("S3secretKey"));
      S3.initAwsS3Client();
   }

   @When("I connect to S3 bucket")
   public void i_expend_first_Task_from_the_list_by_clicking_in_Task_ID() {
     init();
   }


   @When("I getting list of S3 buckets")
   public void IgettinglistofS3buckets() {
      getListS3();
   }

   @And("I download file {string} from batch {string}")
   public void IDownloadfilefrombatch(String key , String bucketName) throws IOException {
      S3.downloadObjectFromS3(bucketName,Response_Job+key);
   }


   public void getListS3(){
      S3.listOfBuckets();
      System.out.println( S3.listOfBuckets());
   }

   @And("I upload to bucket {string} with key {string} and file {string}")
   public void iUploadToBucketWithKeyAndFile(String bucketName, String key, String filePath) {
      S3.uploadObject(bucketName,Response_Job_in+key,filePath);
   }
}
