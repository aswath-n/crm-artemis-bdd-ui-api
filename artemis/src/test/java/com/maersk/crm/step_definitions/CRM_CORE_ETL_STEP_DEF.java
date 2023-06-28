package com.maersk.crm.step_definitions;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.etl_util.ETLBaseClass;

import java.util.List;


public class CRM_CORE_ETL_STEP_DEF  extends CRMUtilities implements ETLBaseClass, ApiBase {

    public void uploadFile(String fileType, String bucketName, String S3folder, String fileName) {
        String env = "QE/";
        if ("QA-DEV".equalsIgnoreCase(System.getProperty("env"))) {
            env = "QE-DEV/";
        }
        s3.uploadObject(bucketName, env + S3folder + fileName,"src/test/resources/testData/CORE_ETL/" + fileName);
    }

    public void executeJob(String jobName, String project) {
        String env = "QE";
        if ("QA-DEV".equalsIgnoreCase(System.getProperty("env"))) {
            env = "QE-DEV";
        }
        List<String> IDS = apiHelper.workSpaceCall(project, env);
        System.out.println("project " + project);
        System.out.println("env " + env);
        System.out.println("IDS.toString() = " + IDS.toString());
        String executionID = apiHelper.executablePostCall1(apiHelper.getExecutableCall(jobName, IDS.get(0), IDS.get(1)));
        System.out.println("executionID = " + executionID);
        String status = "";
        int count = 0;
        while (!status.equals("execution_successful")) {
            System.out.printf("\r" + jobName + " job on " + env + " env for " + project + " is still running . . ." + ++count + " minutes");
            status = apiHelper.executionAssertion(executionID);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            count++;
            if (count > 10) {
                org.testng.Assert.fail(jobName + " job on " + env + " env for " + project + " is completed successfully");
                break;
            }
        }
        System.out.println(jobName + " job on " + env + " env for " + project + " is completed successfully");
    }
}
