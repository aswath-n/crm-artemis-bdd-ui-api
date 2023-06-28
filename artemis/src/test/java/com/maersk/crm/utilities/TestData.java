package com.maersk.crm.utilities;

import com.maersk.crm.utilities.ConfigurationReader;

import static org.testng.Assert.assertTrue;

public class TestData {
    private static TestData testdata;
    public ConfigurationReader ConfigurationReader;
    public static String reportTestData;
    ;


    public TestData(){
        this.ConfigurationReader = new ConfigurationReader();
        reportTestData = ConfigurationReader.getProperty("apiCaseConsumerURI");
    }

    public static TestData createtestdata(){
        if(testdata==null){
            testdata = new TestData();
        }
        return testdata;
    }
    public static TestData gettestdatad(){
        testdata=null;
        testdata=new TestData();
        System.out.println(reportTestData);
        return testdata;
    }
}
