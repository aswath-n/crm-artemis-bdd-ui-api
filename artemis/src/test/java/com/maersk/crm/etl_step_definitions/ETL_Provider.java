package com.maersk.crm.etl_step_definitions;

import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.etl_util.ETLBaseClass;
import com.maersk.crm.utilities.etl_util.ETL_Storage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ETL_Provider extends CRMUtilities implements ETLBaseClass, ApiBase {

    public static int lineNum;

    ETL_Storage etlStg = ETL_Storage.getInstance();
    Api_Storage stg = Api_Storage.getInstance();

    @When("I create new provider line by name {string} with data")
    public void I_create_new_provider_line_by_name_with_data(String name, Map<String, String> data) {
        Api_Body api_req = new EventBuilder()
                .body(file.allFileJsonCreator(data).toString())
                .name(name)
                .build();
        stg.addProduced(api_req);
    }

    @When("I create next provider line by name {string} based on {string} with data")
    public void I_create_next_provider_line_by_name_with_data(String name, String nameTemplate, Map<String, String> data) {
        Api_Body api_req = new EventBuilder()
                .body(data.containsKey("same")
                        ? stg.getProduced(nameTemplate).getBody()
                        : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(stg.getProduced(nameTemplate).getBody(), data))
                .name(name)
                .build();
        stg.addProduced(api_req);
    }

    @When("I create {string} file and send to S3 bucket {string} S3folder {string} with names list")
    public void I_create_file_and_send_to_S3_bucket_with_names_list(String fileType, String bucketName, String S3folder, List<String> providers) {
        String fileName = "";
        String filePath = "";
        String sheetName = "Interface Specs Layout";
        lineNum = 0;
        StringBuilder text = new StringBuilder();

        switch (fileType) {
            case "PROVIDER":
                sheetName = "Interface SpecsCrosswalk Layout";
                filePath = "src/test/resources/testData/ETL_data/IN-EB Provider File Crosswalk to CP.xlsx";
                break;
            case "RECIPIENT":
                filePath = "src/test/resources/testData/ETL_data/HHW HCC Member Demographic File to EB.xlsx";
                break;
            case "HRECIP":
                filePath = "src/test/resources/testData/ETL_data/HIP Member Demographic to EB.xlsx";
                break;
            case "ASSIGN":
                filePath = "src/test/resources/testData/ETL_data/HHW HCC EB Assignment File to MMIS.xlsx";
                break;
            case "HASSIGN":
                filePath = "src/test/resources/testData/ETL_data/HIP EB Assignment File to MMIS.xlsx";
                break;
            case "RESPONSE":
                filePath = "src/test/resources/testData/ETL_data/HHW HCC HIP Member Assignment Response File to EB.xlsx";
                break;
            case "HRESPONSE":
                filePath = "src/test/resources/testData/ETL_data/HHW HCC HIP Member Assignment Response File to EB.xlsx";
                break;
            case "POTENTIAL":
                filePath = "src/test/resources/testData/ETL_data/HHW HCC Potentials to EB.xlsx";
                break;
            case "LOC":
                filePath = "src/test/resources/testData/ETL_data/Level of Care File to EB.xlsx";
                break;
            case "LOCKIN":
                filePath = "src/test/resources/testData/ETL_data/Lockin Lockout File to EB.xlsx";
                break;
            case "HLOCKIN":
                filePath = "src/test/resources/testData/ETL_data/Lockin Lockout File to EB.xlsx";
                break;

        }
        fileName = fileType + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".0611";
        for (String eachProvider : providers) {
            text.append(file.allFileLineCreatorFromJson(filePath, sheetName, parser.parse(stg.getProduced(eachProvider).getBody()).getAsJsonObject())).append("\n");
            lineNum++;
        }

        text.append("TRL      ").append(lineNum);
        System.out.println("text = \n" + text);
        file.createFileForS3(fileName, text.toString());
       s3.uploadObject(bucketName, S3folder + fileName, "target/" + fileName);
    }

    @When("I run JDBC")
    public void I_run_JDBC() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-ecms-order-blcrm`.EXTERNAL_LINKS where EXTERNAL_LINK_ID='1'");
        db.displayAllData();
    }

    @When("I run talent job for environment {string} project {string} job name {string}")
    public void I_run_talent_job_for_environment_project_job_name(String env, String project, String jobName) {
        List<String> IDS = apiHelper.workSpaceCall(project, env);
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
            if (count > 20) {
                Assert.fail(jobName + " job on " + env + " env for " + project + " failed");
                break;
            }
        }
        System.out.println(jobName + " job on " + env + " env for " + project + " is completed successfully");
    }

    @When("I verify warning messages on last ETL provider upload in database with data")
    public void I_verify_worning_messages_on_last_ETL_provider_upload_in_database_with_data(Map<String, String> data) {
        db.runQuery("select max(job_id) FROM `mars-etl-ineb`.ETL_REPORT where jobname_cd='Provider_file_to_stgdb;`'");
        String jobId = db.getFirstRowFirstColumn();
        int rowNum=1;
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_PROV_INTR2 where JOB_ID = '" + jobId + "';");
        for (String key : data.keySet()) {
//            String PMP_ID = String.valueOf(api_common.defineValue(key + ".ProviderPMPId")).replace(".0", "");
//            String PMP_ID=data.get("PMP_ID");
//            db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_PROV_INTR2 where PMP_id = '" + PMP_ID + "' and JOB_ID = '" + jobId + "';");

            rowNum++;
            String WarningMessage = db.getCellValue(rowNum, "WRNG_MSG");
            System.out.println(WarningMessage);
//            Assert.assertTrue(WarningMessage.contains(data.get(key)),
//                    "Warning message for " + key + " mismatch! - FAIL!");

            if (!WarningMessage.equals("")) {
                Assert.assertTrue(WarningMessage.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(WarningMessage.isEmpty());
            }
        }
    }
    @Then("I validate record content trailer in DB")
    public void i_validate_record_content_trailer_in_DB() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_REPORT_CONTROL where job_id='" + jobID + "'");
        int recordContentTrailer = db.getNumericCellValue(1, "RECORD_CONTENT_TRAILER");
        System.out.println("recordContentTrailer = " + recordContentTrailer);
        System.out.println("linenum="+lineNum);
        Assert.assertTrue(recordContentTrailer==lineNum);

    }
    @Given("I create {string} file with planCode {string} and send to S-three bucket {string} to S-three folder {string} with names list")
    public void i_create_file_with_planCode_and_send_to_S_three_bucket_to_S_three_folder_with_names_list(String fileType,String planCode , String bucketName, String S3folder, List<String> providers) {
        String fileName = "";
        String filePath = "";
        String sheetName = "Interface Specs Layout";
        lineNum = 0;
        StringBuilder text = new StringBuilder();

        switch (fileType) {
            case "NEWBORNS":
                filePath = "src/test/resources/testData/ETL_data/DCEB Newborns File Crosswalk to CP.xlsx";
                break;
            case "PROVIDERDATA":
                filePath = "src/test/resources/testData/ETL_data/DCEB Provider File Crosswalk to CP.xlsx";

        }

        fileName = fileType + "_" + planCode +"_"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hhmm")) + ".dat";

        for (String eachProvider : providers) {
            text.append(file.allFileLineCreatorFromJson(filePath, sheetName, parser.parse(stg.getProduced(eachProvider).getBody()).getAsJsonObject())).append("\n");
            lineNum++;
        }
        System.out.println("text = \n" + text);
        file.createFileForS3(fileName, text.toString());
        s3.uploadObject(bucketName, S3folder + fileName, "target/" + fileName);

    }


}
