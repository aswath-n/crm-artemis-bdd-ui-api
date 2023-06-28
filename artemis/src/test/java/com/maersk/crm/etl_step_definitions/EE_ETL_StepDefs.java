package com.maersk.crm.etl_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.etl_util.ETLBaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Integer.parseInt;
import static org.testng.Assert.*;

public class EE_ETL_StepDefs extends CRMUtilities implements ETLBaseClass, ApiBase {
    public static String dailyEligible_Job_in = "QE/DailyEligible/in/";
    Api_Storage stg = Api_Storage.getInstance();

    public static Map<String, String> nameToMemberId = new HashMap<>();
    public static Map<String, String> memberIdToConsumerId = new HashMap<>();

    public static Map<String, String> consumerIdToOldEnrollmentId = new HashMap<>();

    public static Map<String, String> consumerIdToNewEnrollmentId = new HashMap<>();

    public static Map<String, String> nameToNewEnrollmentId = new HashMap<>();

    public static Map<String, String> nameToOldEnrollmentId = new HashMap<>();
    public static Map<String, String> consumerIdMap = new HashMap<>();
    public static String consumerId;
    public static int randomReferenceId;
    public static String internalConsumerID;
    public static String internalConsumerIDmig;


    private String enrollmentBaseURI = ConfigurationReader.getProperty("apiEligibilityURI");

    private String getEnrollmentByConsumerId = "/mars/eb/enrollments/{consumerId}";


    @Given("I upload to S3 bucket {string} with key {string} and file {string}")
    public void i_upload_to_S3_bucket_with_key_and_file(String bucketName, String key, String filePath) {
        s3.uploadObject(bucketName, dailyEligible_Job_in + key, filePath);
    }

    @Then("I validate row count according to matching rules")
    public void I_validate_row_count_according_to_matching_rules() {

        db.createConnection();

        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        System.out.println("job id is:" + jobID);

        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_PROV_INTR1 where JOB_ID=" + jobID);
        System.out.println(db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_PROV_INTR1 where JOB_ID=" + jobID));


        for (Map<String, String> map : db.getAllRowAsListOfMap()) {

            String planCode = map.get("MCE_ID");
            String groupId = map.get("Group_ID");
            String pmpId = map.get("PMP_ID");

            db.runQuery("SELECT * FROM `mars-plan-provider-ineb`.PROVIDER_NETWORK where STATE_PROVIDER_ID='" + pmpId + "'\n" +
                    "and group_id IN ('" + groupId + "') and PLAN_CODE IN ('" + planCode + "')");

            int rowCount = db.getRowCount();
            Assert.assertTrue(rowCount == 1, "Expected row counts mismatched.Expected :1 but found " + rowCount);


        }


    }

    @Given("I create new file line by name {string} with data")
    public void i_create_new_file_line_by_name_with_data(String name, Map<String, String> data) {
        Api_Body api_req = new EventBuilder()
                .body(file.allFileJsonCreator(data).toString())
                .name(name)
                .build();
        stg.addProduced(api_req);
    }

    @Given("I create next file line by name {string} based on {string} with data")
    public void i_create_next_file_line_by_name_based_on_with_data(String name, String nameTemplate, Map<String, String> data) {
        Api_Body api_req = new EventBuilder()
                .body(data.containsKey("same")
                        ? stg.getProduced(nameTemplate).getBody()
                        : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(stg.getProduced(nameTemplate).getBody(), data))
                .name(name)
                .build();
        stg.addProduced(api_req);
    }

    @Then("I validate subprogram type code mapping in payload")
    public void I_validate_subprogram_type_code_mapping_in_payload() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        System.out.println("job id is:" + jobID);
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].eligibility[0].coreEligibility.subProgramTypeCode')\n" +
                "as 'Whole Json String'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");

        for (String code : db.getColumnDataAsList(1)) {

            if (code.contains("HoosierCareConnect")) {
                Assert.assertTrue(code.contains("HoosierCareConnect"));
            } else if (code.contains("HoosierHealthwise")) {
                Assert.assertTrue(code.contains("HoosierHealthwise"));
            } else if (code.contains("HealthyIndianaPlan")) {
                Assert.assertTrue(code.contains("HealthyIndianaPlan"));
            }

        }

    }

    @Then("I validate error messages on last ETL demographic staging table in database with data")
    public void I_validate_error_messages_on_last_ETL_demographic_staging_table_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_DEMO_INIT where job_id='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
            rowNum++;
            if (!errorText.equals("")) {
                System.out.println(data.get(key) + " PAssed error text");
                System.out.println(errorText + " +++++++++Error text");
                Assert.assertTrue(errorText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(errorText.isEmpty());
            }

        }

    }

    @Then("I validate core eligibility existance in payload and record id value")
    public void i_validate_core_eligibility_existance_in_payload_and_record_id_value() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_CASELDR_CASE where job_id='" + jobID + "'");
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].eligibility[0].recordId')\n" +
                "as 'Record_Id'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        String recordId = db.getFirstRowFirstColumn();
        System.out.println("recordId = " + recordId);
        String expectedRecId = "21";

        for (String id : db.getColumnDataAsList(1)) {
            Assert.assertEquals(expectedRecId, id, "record id is not matching");

        }

    }

    @Then("I validate enrollment record id's in database with data")
    public void i_validate_enrollment_record_id_s_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].enrollment[0].recordId')\n" +
                "as 'RECORD_ID'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        System.out.println(jobID);
        int rowNum = 1;
        for (String key : data.keySet()) {
            String enrollmentRecordId = db.getCellValue(rowNum, "RECORD_ID");
            System.out.println("enrollmentRecordId = " + enrollmentRecordId);
            rowNum++;

            if (!enrollmentRecordId.equals("")) {
                System.out.println(data.get(key));
                Assert.assertEquals(enrollmentRecordId, data.get(key),
                        "Enrollment Record ID for " + key + " mismatch! - FAIL!");

            } else {
                Assert.assertTrue(enrollmentRecordId.isEmpty());
            }
        }

        String expectedCaseRecordId = "100";
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case[0].recordId')\n" +
                "as 'CASE_RECORD_ID'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        for (String id : db.getColumnDataAsList(1)) {
            System.out.println("id = " + id);
            Assert.assertEquals(expectedCaseRecordId, id, " Case Record ID mismatch! - FAIL!");

        }


    }

    @Then("I validate error messages on last ETL ASSIGN UPDATE staging table in database with data")
    public void i_validate_error_messages_on_last_ETL_ASSIGN_UPDATE_staging_table_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_ASSIGN_UPDATE_INIT where job_id='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
            rowNum++;

            if (errorText != null) {
                Assert.assertTrue(errorText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("errorText = " + errorText);
            } else {
                Assert.assertNull(errorText);
                System.out.println("errorText = " + errorText);
            }

        }
    }

    @Then("I validate multiple enrollment segments in payload with data")
    public void i_validate_multiple_enrollment_segments_in_payload_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].enrollment[0].subProgramTypeCode')\n" +
                "as 'Enrollment_Segment_1',\n" +
                "JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].enrollment[1].subProgramTypeCode')\n" +
                "as 'Enrollment_Segment_2'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;
        int colNum = 1;
        for (String key : data.keySet()) {
            String subProgCode = db.getCellValue(rowNum, colNum);
            colNum++;

            if (subProgCode != null) {
                Assert.assertTrue(subProgCode.contains(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("subprgmcd = " + subProgCode);
            } else {
                Assert.assertNull(subProgCode);
                System.out.println("subprgm = " + subProgCode);
            }

        }


    }

    @Then("I validate LOC eligibility in payload with data")
    public void I_validate_LOC_eligibility_in_payload_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].eligibility[0].otherEligibilitySegments[1].segmentTypeCode')\n" +
                "as 'LOC_1',\n" +
                "JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].eligibility[0].otherEligibilitySegments[2].segmentTypeCode')\n" +
                "as 'LOC_2',\n" +
                "JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].eligibility[0].otherEligibilitySegments[3].segmentTypeCode')\n" +
                "as 'LOC_3'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;
        int colNum = 1;
        for (String key : data.keySet()) {
            String loc = db.getCellValue(rowNum, colNum);
            colNum++;

            if (loc != null) {
                Assert.assertTrue(loc.contains(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("subprgmcd = " + loc);
            } else {
                Assert.assertNull(loc);
                System.out.println("subprgm = " + loc);
            }

        }
    }

    @Then("I validate LILO enrollment in payload with data")
    public void I_validate_LILO_enrollment_in_payload_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].enrollment[0].otherEnrollmentSegments[1].segmentTypeCode')\n" +
                "as 'LILO_1',\n" +
                "JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].enrollment[0].otherEnrollmentSegments[2].segmentTypeCode')\n" +
                "as 'LILO__2',\n" +
                "JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].enrollment[0].otherEnrollmentSegments[3].segmentTypeCode')\n" +
                "as 'LILO__3'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;
        int colNum = 1;
        for (String key : data.keySet()) {
            String lilo = db.getCellValue(rowNum, colNum);
            colNum++;

            if (lilo != null) {
                Assert.assertTrue(lilo.contains(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("segmentTypeCode = " + lilo);
            } else {
                Assert.assertNull(lilo);
                System.out.println("segmentTypeCode = " + lilo);
            }

        }
    }

    @Then("I validate error messages on last LOC staging table in database with data")
    public void I_validate_error_messages_on_last_LOC_staging_table_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_LOC_INIT where job_id='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String errorText = db.getCellValue(rowNum, "ERROR_TEXT").trim();
            rowNum++;
            if (!errorText.equals("")) {
                Assert.assertTrue(errorText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(errorText.isEmpty());
            }

        }

    }

    @Then("I validate eligibility record id's in database with data")
    public void i_validate_eligibility_record_id_s_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case[0].consumers[0].eligibility[0].recordId')\n" +
                "as 'RECORD_ID'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;
        for (String key : data.keySet()) {
            String eligibilityRecordId = db.getCellValue(rowNum, "RECORD_ID");
            System.out.println("eligibilityRecordId = " + eligibilityRecordId);
            rowNum++;

            if (!eligibilityRecordId.equals("")) {
                Assert.assertEquals(eligibilityRecordId, data.get(key),
                        "Eligibility Record ID for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(eligibilityRecordId.isEmpty());
            }
        }

    }

    @Given("I validate case level record id's in database with data")
    public void i_validate_case_level_record_id_s_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        String expectedCaseRecordId = "null";
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case[0].recordId')\n" +
                "as 'CASE_RECORD_ID'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;
        for (String key : data.keySet()) {
            String caseRecordId = db.getCellValue(rowNum, "CASE_RECORD_ID");
            System.out.println("caseRecordId = " + caseRecordId);
            rowNum++;

            if (!caseRecordId.equals("")) {
                Assert.assertEquals(caseRecordId, data.get(key),
                        "Case Record ID for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(caseRecordId.isEmpty());
            }
        }
    }

    @Then("I validate error messages on last ETL potential staging table in database with data")
    public void i_validate_error_messages_on_last_ETL_potential_staging_table_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_POTENTIAL_INIT where job_id='" + jobID + "'");

        int rowNum = 1;
        for (String key : data.keySet()) {
            String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
            rowNum++;
            if (!errorText.equals("")) {
                Assert.assertTrue(errorText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(errorText.isEmpty());
            }

        }


    }

    @Then("I Validate only the last record on the file was processed from multiple lines with the same Medicaid ID")
    public void i_Validate_only_the_last_record_on_the_file_was_processed_from_multiple_lines_with_the_same_Medicaid_ID() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_CASELDR_CASE where job_id='" + jobID + "'");
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].eligibility[0].otherEligibilitySegments[0].segmentDetailValue2')\n" +
                "as 'B' \n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        String expectedCodeReason = "\"B\"";
        for (String cd : db.getColumnDataAsList(1)) {
            if (cd.contains("\"B\"")) {
                System.out.println("The last record Reason Code = " + cd);
                Assert.assertEquals(cd, expectedCodeReason, "Code reason is not matching");
            } else {
                System.out.println("The first record Reason Code = " + cd);
            }
        }


    }


    @When("I get consumerId by externalId")
    public void getConsumerIdByExternalId(List<String> data) {
        for (String name : data) {
            nameToMemberId.put(name, parser.parse(stg.getProduced(name).getBody()).getAsJsonObject().get("MemberId").getAsString());
            System.out.println(nameToMemberId);
        }
        db.createConnection();
        int rowNum = 1;
        for (String key : nameToMemberId.keySet()) {
            db.runQuery("SELECT * FROM `mars-consumer-ineb`.CONSUMER_IDENTIFICATION_NUMBER where EXTERNAL_CONSUMER_ID='" + nameToMemberId.get(key) + "'");
            System.out.println("External Consumer ID/ Member Id = " + nameToMemberId.get(key));
            String consumerId = db.getCellValue(1, "CONSUMER_ID");
            memberIdToConsumerId.put(nameToMemberId.get(key), consumerId);
        }


    }

    @Then("I save enrollmentId of consumer {string}")
    public void i_save_enrollmentId_of_consumerId(String name) {
        String memberId = nameToMemberId.get(name);
        System.out.println("memberId = " + memberId);
        String consumerId = memberIdToConsumerId.get(memberId);
        System.out.println("consumerId = " + consumerId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentByConsumerId.replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        JsonArray enrollmentRecord = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        System.out.println("enrollmentRecord = " + enrollmentRecord);
        consumerIdToNewEnrollmentId.put(consumerId, enrollmentRecord.get(0).getAsJsonObject().get("enrollmentId").toString());
        System.out.println("consumerIdtoenewenrollmentid" + consumerIdToNewEnrollmentId);
//        consumerIdToOldEnrollmentId.put(consumerId,enrollmentRecord.get(1).getAsJsonObject().get("enrollmentId").toString());
        nameToNewEnrollmentId.put(name, enrollmentRecord.get(0).getAsJsonObject().get("enrollmentId").toString());
        System.out.println("nametonewenrollment=" + nameToNewEnrollmentId);
//        nameToOldEnrollmentId.put(consumerId, enrollmentRecord.get(1).getAsJsonObject().get("enrollmentId").toString());

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I validate record content that enrollment status is as expected with data for consumer {string}")
    public void i_validate_record_content_that_enrollment_status_is_as_expected_with_data_for_consumer(String name, Map<String, String> data) {
        String memberId = nameToMemberId.get(name);
        String consumerId = memberIdToConsumerId.get(memberId);
        String enrollmentId = consumerIdToNewEnrollmentId.get(consumerId);
        db.createConnection();

        db.runQuery("select JSON_EXTRACT(RECORD_CONTENT, '$.status')\n" +
                "as 'enrollment_status'\n" +
                "FROM `mars-etl-ineb`.ETL_SELECTION_INIT\n" +
                "where ENROLLMENT_ID='" + enrollmentId + "'");

        int rowNum = 1;
        for (String key : data.keySet()) {
            String enrollmentStatus = db.getCellValue(rowNum, "enrollment_status");
            System.out.println("enrollmentStatus = " + enrollmentStatus);
            rowNum++;

            if (!enrollmentStatus.equals("")) {
                Assert.assertTrue(enrollmentStatus.contains(data.get(key)),
                        "enrollment status for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(enrollmentStatus.isEmpty());
            }
        }
    }

    @Then("I validate enrollment status is as expected with data in enrollment table for consumer  {string}")
    public void i_validate_enrollment_status_is_as_expected_with_data_in_enrollment_table_for_consumer(String name, Map<String, String> data) {
        String memberId = nameToMemberId.get(name);
        String consumerId = memberIdToConsumerId.get(memberId);
        String enrollmentId = consumerIdToNewEnrollmentId.get(consumerId);
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-enrollment-ineb`.ENROLLMENTS where ENROLLMENT_ID='" + enrollmentId + "'");

        int rowNum = 1;
        for (String key : data.keySet()) {
            String enrollmentStatus = db.getCellValue(rowNum, "TXN_STATUS");
            System.out.println("enrollmentStatus = " + enrollmentStatus);
            rowNum++;

            if (!enrollmentStatus.equals("")) {
                Assert.assertTrue(enrollmentStatus.contains(data.get(key)),
                        "enrollment status for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(enrollmentStatus.isEmpty());
            }
        }
    }

    @Then("I validate the File Source Field value for {string}")
    public void i_validate_the_File_Source_Field_value_for(String name, Map<String, String> data) {
        String memberId = nameToMemberId.get(name);
        String consumerId = memberIdToConsumerId.get(memberId);
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-consumer-ineb`.CONSUMER where CONSUMER_ID='" + consumerId + "'");

        int rowNum = 1;
        for (String key : data.keySet()) {
            String sourceFileValue = db.getCellValue(rowNum, "FILE_SOURCE");
            System.out.println("sourceFileValue = " + sourceFileValue);
            rowNum++;

            if (!sourceFileValue.equals("")) {
                Assert.assertTrue(sourceFileValue.contains(data.get(key)),
                        "enrollment status for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(sourceFileValue.isEmpty());
            }
        }
    }

    @When("I get consumerId by externalId for recipient file")
    public void getConsumerIdByExternalIdForRecipientFile(List<String> data) {
        for (String name : data) {
            nameToMemberId.put(name, parser.parse(stg.getProduced(name).getBody()).getAsJsonObject().get("RecipientID").getAsString());
            System.out.println(nameToMemberId);
        }
        db.createConnection();
        for (String key : nameToMemberId.keySet()) {
            db.runQuery("SELECT * FROM `mars-consumer-ineb`.CONSUMER_IDENTIFICATION_NUMBER where EXTERNAL_CONSUMER_ID='" + nameToMemberId.get(key) + "'");
            System.out.println("External Consumer ID/ Member Id = " + nameToMemberId.get(key));
            String consumerId = db.getCellValue(1, "CONSUMER_ID");
            memberIdToConsumerId.put(nameToMemberId.get(key), consumerId);
        }

    }

    @Then("I validate error messages on last ETL ASSIGN UPDATE staging table in database with data new CC")
    public void i_validate_error_messages_on_last_ETL_ASSIGN_UPDATE_staging_table_in_database_with_dataCOPY(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_DEMO_INIT where job_id='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
            rowNum++;

            if (errorText != null) {
                Assert.assertTrue(errorText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("errorText = " + errorText);
            } else {
                Assert.assertNull(errorText);
                System.out.println("errorText = " + errorText);
            }

        }
    }

    @Then("I validate if the valid SSN passed to Case & Consumer")
    public void i_validate_if_the_valid_SSN_passed_to_Case_Consumer() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_CASELDR_CASE where job_id='" + jobID + "'");
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].consumerSSN')\n" +
                "as 'consumerSSN' \n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        String savedSSN = db.getCellValue(1, "consumerSSN").replace("\"", "").trim();
        System.out.println("SSN is " + savedSSN);
        db.runQuery("select * from `mars-etl-ineb` .ETL_DEMO_INIT where job_id='" + jobID + "'");
        int rowNum = 1;
        String actualSSN = db.getCellValue(rowNum, "RECIP_SSN");
        System.out.println("Actual SSN is " + actualSSN);
        System.out.println("JOB ID IS = " + jobID);
        Assert.assertEquals(savedSSN, actualSSN, "SSN is not matching");
    }

    @Then("I validate if ETL passed NULL SSN to Case & Consumer and saved the record")
    public void i_validate_if_ETL_passed_NULL_SSN_to_Case_Consumer_and_saved_the_record() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_CASELDR_CASE where job_id='" + jobID + "'");
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].consumerSSN')\n" +
                "as 'consumerSSN' \n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        String savedSSN = db.getCellValue(1, "consumerSSN");
        System.out.println("SSN is " + savedSSN);
        db.runQuery("select * from `mars-etl-ineb` .ETL_DEMO_INIT where job_id='" + jobID + "'");
        String expectedSSN = "null";
        System.out.println("JOB ID IS = " + jobID);
        Assert.assertEquals(savedSSN, expectedSSN, "SSN is not matching");

    }

    @Then("I validate ETL error or warning message for invalid SSN")
    public void i_validate_ETL_error_or_warning_message_for_invalid_SSN(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb` .ETL_DEMO_INIT where job_id='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String warningText = db.getCellValue(rowNum, "WARNING_TEXT");
            rowNum++;
            if (!warningText.equals("")) {
                System.out.println(data.get(key) + " PAssed error text");
                System.out.println(warningText + " +++++++++Error text");
                Assert.assertTrue(warningText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertTrue(warningText.isEmpty());
            }

        }
    }

    @Then("I validate LOC eligibility in payload with data new")
    public void I_validate_LOC_eligibility_in_payload_with_data_new(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].recordId')\n" +
                "as 'recordId'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String recordId = db.getCellValue(rowNum, "recordId");
            System.out.println("recordId = " + recordId);
            rowNum++;

            if (!recordId.equals("") || recordId.equals(null)) {
                Assert.assertEquals(recordId, data.get(key) + "",
                        "recordId  for " + key + " mismatch! - FAIL!");
            } else {
                System.out.println("recordId  is not match or null");
            }
        }

    }

    @Then("I validate on last ETL HIP file in staging table in database")
    public void i_validate_on_last_ETL_HIP_file_staging_table_in_database(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb`.ETL_DEMO_HIP_INIT where job_id='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String expected = stg.getProduced(key).getBodyAttribute(data.get(key)).toString();
            String fieldToValidate = "";
            switch (data.get(key)) {
                case "SpouseMemberId":
                    fieldToValidate = db.getCellValue(rowNum, "SPOUSE_MEMBER_ID");
                    rowNum++;
                    break;
                case "PregnancyStartDate":
                    fieldToValidate = db.getCellValue(rowNum, "PREGNANCY_START_DATE");
                    rowNum++;
                    break;
                case "PregnancyEndDate":
                    fieldToValidate = db.getCellValue(rowNum, "PREGNANCY_END_DATE");
                    rowNum++;
                    break;
                case "MedicallyFrailConfirmationCode":
                    fieldToValidate = db.getCellValue(rowNum, "MEDICALLY_FRAIL_CONFIRMATION_CODE");
                    rowNum++;
                    break;
                case "MedicallyFrailLastConfirmedAssessmentDate":
                    fieldToValidate = db.getCellValue(rowNum, "SPOUSE_MEMBER_ID");
                    rowNum++;
                    break;
            }
            if (fieldToValidate != null) {
                System.out.println("fieldToValidate " + fieldToValidate);
                System.out.println("data.getKey " + data.get(key));
                System.out.println("expected " + expected);
                Assert.assertNotNull(fieldToValidate.equals(expected),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("fieldToValidate" + fieldToValidate);
            } else {
                Assert.assertNull(fieldToValidate);
                System.out.println("fieldToValidate = " + fieldToValidate);
            }

        }
    }

    @Then("I validate on last ETL RECIP file in staging table in database")
    public void i_validate_on_last_ETL_RECIP_file_staging_table_in_database(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-ineb`.ETL_DEMO_INIT where job_id='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String expected = stg.getProduced(key).getBodyAttribute(data.get(key)).toString();
            String fieldToValidate = "";
            switch (data.get(key)) {
                case "IllegalIndicator":
                    fieldToValidate = db.getCellValue(rowNum, "ILLEGAL_INDICATOR");
                    rowNum++;
                    break;
                case "NativeAmerican":
                    fieldToValidate = db.getCellValue(rowNum, "NATIVE_AMERICAN");
                    rowNum++;
                    break;
            }
            if (fieldToValidate != null) {
                System.out.println("fieldToValidate " + fieldToValidate);
                System.out.println("data.getKey " + data.get(key));
                System.out.println("expected " + expected);
                Assert.assertNotNull(fieldToValidate.equals(expected),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("fieldToValidate" + fieldToValidate);
            } else {
                Assert.assertNull(fieldToValidate);
                System.out.println("fieldToValidate = " + fieldToValidate);
            }

        }
    }

    @Then("I validate case record id's in database with data")
    public void i_validate_case_record_id_s_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case[0].recordId')\n" +
                "as 'CASE_RECORD_ID'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;
        for (String key : data.keySet()) {
            String caseRecordId = db.getCellValue(rowNum, "CASE_RECORD_ID");
            System.out.println("caseRecordId = " + caseRecordId);
            rowNum++;

            if (!caseRecordId.equals("")) {
                System.out.println(data.get(key));
                Assert.assertEquals(caseRecordId, data.get(key),
                        "Case Record ID for " + key + " mismatch! - FAIL!");

            } else {
                Assert.assertTrue(caseRecordId.isEmpty());
            }
        }
    }

    @Then("I validate multiple response records in payload with data")
    public void I_validate_multiple_response_records_in_payload_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT_OUT, '$.caseLoaderRequest[0].case.consumers[0].enrollment[0].otherEnrollmentSegments[0].genericFieldText2')\n" +
                "as 'Transaction_1',\n" +
                "JSON_EXTRACT(RECORD_CONTENT_OUT, '$.caseLoaderRequest[0].case.consumers[0].enrollment[1].otherEnrollmentSegments[0].genericFieldText2')\n" +
                "as 'Transaction_2',\n" +
                "JSON_EXTRACT(RECORD_CONTENT_OUT, '$.caseLoaderRequest[0].case.consumers[0].enrollment[2].otherEnrollmentSegments[0].genericFieldText2')\n" +
                "as 'Transaction_3'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;
        int colNum = 1;
        for (String key : data.keySet()) {
            String transactionType = db.getCellValue(rowNum, colNum);
            System.out.println("Transactiontype" + transactionType);
            colNum++;

            if (transactionType != null) {
                Assert.assertTrue(transactionType.contains(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("transactionType = " + transactionType);
            } else {
                Assert.assertNull(transactionType);
                System.out.println("transactionType = " + transactionType);
            }

        }


    }

    @Given("I create new 834 file line by name {string} with data")
    public void i_create_new_834_file_line_by_name_with_data(String name, Map<String, String> data) {
        edi.fileText834 = String.join(edi.newLine, edi.makeRecords(data));

        Api_Body api_req = new EventBuilder()
                .body(edi.makeTemplate2(data).toString())
                .name(name)
                .build();
        stg.addProduced(api_req);
        System.out.println("create step data:" + data);
    }

    @When("I create 834 {string} file and send to S3 bucket {string} S3folder {string} with names list with data")
    public static void I_create_834_file_and_send_to_S3_bucket_with_names_list(String fileType, String bucketName, String S3folder, List<String> providers) {
        String fileName = "";
        fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + fileType + "_auto." + RandomStringUtils.random(3, false, true);
        file.createFileForS3(fileName, String.join(edi.newLine, edi.fileText834));
        s3.uploadObject(bucketName, S3folder + fileName, "target/" + fileName);
    }

    @When("I create footer for 834 file with data")
    public static void I_create_834_file_footer(Map<String, String> data) {
        edi.fileText834 += "\n" + edi.makeFooter(data);
        edi.records.clear();
        edi.headerExists = false;

    }

    @When("I create header for 834 file with data")
    public static void I_create_834_file_header(Map<String, String> data) {
        edi.fileText834 = "";
        edi.fileText834 += "\n" + edi.makeHeader(data);

    }

    @Then("I validate enrollment record id's in database with data based on transaction type and code")
    public void i_validate_enrollment_record_id_s_in_database_with_data_based_on_transaction_type_and_code(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_EXTRACT(RECORD_CONTENT, '$.enrollments[0].recordId')\n" +
                "as 'RECORD_ID'\n" +
                "FROM `mars-etl-dceb`.ETL_CASELDR_ELIG_ENROLL\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;
        for (String key : data.keySet()) {
            String enrollmentRecordId = db.getCellValue(rowNum, "RECORD_ID");
            rowNum++;

            if (!enrollmentRecordId.equals("")) {
                Assert.assertEquals(enrollmentRecordId, data.get(key),
                        "Enrollment Record ID for " + key + " mismatch! - FAIL!");

            } else {
                Assert.assertTrue(enrollmentRecordId.isEmpty());
            }
        }

    }

    @Then("I validate response code on consumer result table")
    public void i_validate_response_code_on_consumer_result_table() {
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_CASELDR_RESULT WHERE JOB_ID='" + jobID + "'");
        int rowNumber = 1;
        for (Map<String, String> map : db.getAllRowAsListOfMap()) {
            if (map.get("STEP").equals("CONSUMER")) {
                String expectedResponseCode = "200";
                String actualResponseCode = db.getCellValue(rowNumber, "RESPONSE_CODE");
                System.out.println(actualResponseCode);
                Assert.assertEquals(expectedResponseCode, actualResponseCode, "Response code mismatch!");
                rowNumber++;


            }
        }

    }

    @Then("I very only one LOC record processed in db with data")
    public void i_very_only_one_LOC_record_processed_in_db_with_data() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  DATE_FORMAT(JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].eligibility[0].otherEligibilitySegments[0].startDate'),'%Y%m%d')\n" +
                "as 'startDate',\n" +
                "DATE_FORMAT(JSON_EXTRACT(RECORD_CONTENT_OUT, '$[0].caseLoaderRequest[0].case.consumers[0].eligibility[0].otherEligibilitySegments[0].endDate'),'%Y%m%d')\n" +
                "as 'endDate'\n" +
                "FROM `mars-etl-ineb` .ETL_CASELDR_CASE\n" +
                "where JOB_ID='" + jobID + "'" + "and row_num=4");

        String startDateExpected=API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate("firstDayOfCurrentYearETLver");
        System.out.println("startDateExpected= " + startDateExpected);
        String endDateExpected=API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("highDateETLver");
        System.out.println("endDateExpected = " + endDateExpected);

        String acutalStartDate = db.getCellValue(1, "startDate");
        System.out.println("acutalStartDate = " + acutalStartDate);
        String actualEndDate = db.getCellValue(1, "endDate");
        System.out.println("actualEndDate = " + actualEndDate);
        Assert.assertEquals(startDateExpected, acutalStartDate, "Mismatched" + acutalStartDate + "doesn't match");
        Assert.assertEquals(endDateExpected, actualEndDate, "Mismatched" + actualEndDate + "doesn't match");

    }

    @Then("I validate error messages on last ETL DCEB staging table in database with data")
    public void I_validate_error_text_on_last_ETL_DCEB_staging_table_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-dceb`.ETL_834LOOP2000_INIT where job_id='" + jobID + "'");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
            rowNum++;
            if (errorText != null) {
                Assert.assertTrue(errorText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertNotNull(errorText);
                System.out.println("Error text not found");
            }

        }

    }

    @Given("I verify error code on last ETL provider upload in database with data")
    public void i_verify_error_code_on_last_ETL_provider_upload_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-ineb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        System.out.println("JobId is ===  " + jobID);
        db.runQuery("select  JSON_EXTRACT(BODY, '$[0].root.errorResponse.errorCode')\n" +
                "as 'errorCode'\n" +
                "FROM `mars-etl-ineb`.ETL_RESPONSE_TEMP\n" +
                "where JOB_ID='" + jobID + "'");

        int rowNum = 1;
        int colNum = 1;
        for (String key : data.keySet()) {
            String errorCode = db.getCellValue(rowNum, colNum);
            colNum++;
            System.out.println("DATES = " + errorCode);
            if (errorCode != null) {
                assertTrue(errorCode.contains(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
                System.out.println("errorCode = " + errorCode);
            } else {
                Assert.assertNull(errorCode);
                System.out.println("errorCode = " + errorCode);
            }

        }
    }

    @Then("I validate status code on Response Temp table")
    public void i_validate_status_code_on_Response_Temp_table() {
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_RESPONSE_TEMP WHERE JOB_ID='" + jobID + "'");
        int rowNumber = 1;
        for (Map<String, String> map : db.getAllRowAsListOfMap()) {
            if (map.get("ROW_NUM").equals("1")) {
                String expectedStatusCode = "200";
                String actualStatusCode = db.getCellValue(rowNumber, "STATUSCODE");
                System.out.println(actualStatusCode);
                Assert.assertEquals(expectedStatusCode, actualStatusCode, "Status code mismatch!");
                rowNumber++;

            }
        }
    }

    @Then("I validate survey status update on staging table")
    public void i_validate_survey_status_update_on_staging_table() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_OUTBOUND_MCO_SURVEY_STG where JOB_ID='" + jobID + "'order by 1 desc");
        String expectedStatus = "In Excel File";
        String expectedProcessValue = "success";
        int rowNumber = 1;
        for (Map<String, String> map : db.getAllRowAsListOfMap()) {
            if (StringUtils.isNotEmpty(map.get("survey_status_update")) && StringUtils.isNotEmpty(map.get("mark_processed"))) {
                String actualStatus = db.getCellValue(rowNumber, "survey_status_update");
                System.out.println("actualStatus = " + actualStatus);
                Assert.assertEquals(actualStatus, expectedStatus, "Status don't match!");
                String actualProcessValue = db.getCellValue(rowNumber, "mark_processed");
                System.out.println("actualProcessValue = " + actualProcessValue);
                Assert.assertEquals(actualProcessValue, expectedProcessValue, "Process Value don't match!");
                rowNumber++;
            }

        }
    }

    @Then("I validate Reevaluatepi staging table for status INPROGRESS with data")
    public void i_validate_Reevaluatepi_staging_table_for_status_INPROGRESS_with_data(Map<String, String> data) {
        db.createConnection();
        String projectName = data.get("projectName");
        db.runQuery("SELECT * FROM `mars-etl-" + projectName + "`" + ".ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-" + projectName + "`" + ".ETL_REEVALUATEPI_INIT where job_id='" + jobID + "'");
        int rowNum = 1;
        String status = db.getCellValue(rowNum, "STATUS");
        String tenant = db.getCellValue(rowNum, "TENANT");
        String errorCount = db.getCellValue(rowNum, "ERROR_COUNT");
        String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
        String recordCount = db.getCellValue(rowNum, "RECORDCOUNT");
        assertEquals(tenant, data.get("tenant"), "Tenant value doesn't match!");
        assertEquals(status, data.get("status"), "Status doesn't match!");
        assertNull(errorCount, "Error count is not null!");
        assertNull(errorText, "Error Text is not null");
        assertNotNull(recordCount, "RecordCount is not null");
    }

    @Then("I validate Reevaluatepi staging table for status COMPLETED with data")
    public void i_validate_Reevaluatepi_staging_table_for_status_COMPLETED_with_data(Map<String, String> data) {
        db.createConnection();
        String projectName = data.get("projectName");
        db.runQuery("SELECT * FROM `mars-etl-" + projectName + "`" + ".ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-" + projectName + "`" + ".ETL_REEVALUATEPI_INIT where job_id='" + jobID + "'");
        int rowNum = 2;
        String status = db.getCellValue(rowNum, "STATUS");
        String tenant = db.getCellValue(rowNum, "TENANT");
        String errorCount = db.getCellValue(rowNum, "ERROR_COUNT");
        String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
        assertEquals(tenant, data.get("tenant"), "Tenant value doesn't match!");
        assertEquals(status, data.get("status"), "Status doesn't match!");
        assertNull(errorCount, "Error count is not null!");
        assertNull(errorText, "Error Text is not null");
    }


    @Then("I validate the consumer payload with firstName and lastName")
    public void i_validate_the_consumer_payload_with_firstName_and_lastName(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT JOB_ID FROM `mars-etl-dceb`.ETL_CASELDR_CONSUMER order by JOB_ID DESC;");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery(
                "SELECT " +
                        "JSON_UNQUOTE(JSON_EXTRACT(RECORD_CONTENT, '$[0].consumerRequests[0].firstName')) AS 'firstname'," + "\n" +
                        "JSON_UNQUOTE(JSON_EXTRACT(RECORD_CONTENT, '$[0].consumerRequests[0].lastName')) AS 'lastname', ROW_NUM" + "\n" +
                        "FROM `mars-etl-dceb`.ETL_CASELDR_CONSUMER" + "\n" +
                        "WHERE JOB_ID=" + jobID + "\n" +
                        "ORDER BY ROW_NUM DESC" + "\n" +
                        "LIMIT 1;"
        );

        String firstName = db.getCellValue(1, 1);
        String lastName = db.getCellValue(1, 2);
        assertEquals(firstName, data.get("FirstName"), "FirstName does not match!");
        assertEquals(lastName, data.get("LastName"), "LastName does not match!");
    }

    @Then("I validate active eligibility flag has valid value in consumer payload")
    public void i_validate_active_eligibility_flag_has_valid_value_in_consumer_payload(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT JOB_ID FROM `mars-etl-dceb`.ETL_CASELDR_CONSUMER order by JOB_ID DESC;");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery(
                "SELECT " +
                        "JSON_UNQUOTE(JSON_EXTRACT(RECORD_CONTENT, '$[0].consumerRequests[0].activeEligibility')) AS 'activeEligibility',  ROW_NUM" + "\n" +
                        "FROM `mars-etl-dceb`.ETL_CASELDR_CONSUMER" + "\n" +
                        "WHERE JOB_ID=" + jobID + "\n" +
                        "ORDER BY ROW_NUM DESC" + "\n" +
                        "LIMIT 1;"
        );

        String activeEligibilityFlag = db.getCellValue(1, 1);
        assertEquals(activeEligibilityFlag, data.get("activeEligibilityFlag"), "activeEligibilityFlag does not have valid value!");

    }

    @Then("I validate error message if eligibility flag is not set to true")
    public void i_validate_error_message_if_eligibility_flag_is_not_set_to_true(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-dceb`.ETL_834LOOP2000_INIT where job_id='" + jobID + "'");
        int rowNum = 1;
        String activeEligibilityFlag = db.getCellValue(rowNum, "ERROR_TEXT");
        assertTrue(activeEligibilityFlag.contains(data.get("ErrorMessage")), "activeEligibilityFlag does not have valid value!");
    }

    @Then("I validate that internal consumer ID is captured in Consumer Migration table")
    public void i_validate_that_internal_consumer_ID_is_captured_in_Consumer_Migration_table() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("SELECT JSON_UNQUOTE(JSON_EXTRACT(RESPONSE_MESSAGE, '$[0].data[0].internalConsumerId')) AS 'internalConsumerID'" + "\n" +
                "FROM `mars-etl-dceb`.ETL_CASELDR_RESULT\n" +
                "where RESPONSE_CODE=200 AND UPPER(STEP) = 'CONSUMER'\n" +
                "and JOB_ID='" + jobID + "'");
        List<String> consumeridsResponse = db.getColumnDataAsList(1);

        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_CONSUMER_MIGRATION WHERE JOB_ID='" + jobID + "'");
        List<String> internalIDs = db.getColumnDataAsList("INTERNAL_CONSUMER_ID");

        for (String id : internalIDs) {

            if (id != null) {
                Assert.assertEquals(consumeridsResponse, internalIDs, "internal consumer id's are not matching!!!");
                Assert.assertNotNull(id);
                System.out.println("internalConsumerID=" + id);
            } else {
                System.out.println("internalConsumerID=" + id);
                Assert.assertNull(id);
                System.out.println("internalConsumerID=" + id);
            }
        }
    }

    @Then("I validate error messages on ETL NEWBORN DCEB staging table in database with data")
    public void i_validate_error_messages_on_ETL_NEWBORN_DCEB_staging_table_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-dceb`.ETL_NEWBORN_DCEB_INT where job_id='" + jobID + "' and ERROR_TEXT<>'' ORDER BY ROW_NUM asc");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
            rowNum++;
            if (errorText != null) {
                Assert.assertTrue(errorText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertNotNull(errorText);
                System.out.println("Error text not found");
            }
        }
    }

    @Then("I validate eligibility and enrollment payload in database with data")
    public void i_validate_eligibility_and_enrollment_payload_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_UNQUOTE(JSON_EXTRACT(RECORD_CONTENT, '$[0].eligibilities[0].coreEligibility.eligibilityEndReason'))\n" +
                "as 'eligibilityEndReason'\n" +
                "FROM `mars-etl-dceb`.ETL_CASELDR_ELIG_ENROLL\n" +
                "where JOB_ID='" + jobID + "'");
        int rowNum = 1;

        List<String> endReasonCodes = new ArrayList();
        List<String> actualEndReasonCodes = new ArrayList();
        for (String key : data.keySet()) {

            endReasonCodes.add(data.get(key));
            String eligibilityEndReason = db.getCellValue(rowNum, "eligibilityEndReason");
            actualEndReasonCodes.add(eligibilityEndReason);
            rowNum++;
        }
        Collections.sort(endReasonCodes);
        Collections.sort(actualEndReasonCodes);
        assertEquals(actualEndReasonCodes, endReasonCodes);
    }

    @Then("I validate selection status in staging table for Selections with data")
    public void i_validate_selection_status_in_staging_table_for_Selections_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select  JSON_UNQUOTE(JSON_EXTRACT(RECORD_CONTENT, '$[0].txnStatus'))\n" +
                "as 'txnStatus'\n" +
                "FROM `mars-etl-dceb`.ETL_SELECTION_INIT\n" +
                "where JOB_ID='" + jobID + "'" + "order by PROFILE_ID desc");

        String txnStatusActual = db.getCellValue(1, "txnStatus");
        System.out.println("txnStatus = " + txnStatusActual);

        assertEquals(txnStatusActual, data.get("TxnStatus"), "Enrollment status is not as expected");


    }

    @Then("I validate error messages on ETL PROV STG DCEB table in database with data")
    public void i_validate_error_messages_on_ETL_PROV_STG_DCEB_table_in_database_with_data(Map<String, String> data) {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-dceb`.ETL_PROV_STG_DCEB where job_id='" + jobID + "' and ERROR_TEXT<>'' ORDER BY ROW_NUM asc");
        int rowNum = 1;

        for (String key : data.keySet()) {
            String errorText = db.getCellValue(rowNum, "ERROR_TEXT");
            rowNum++;
            if (errorText != null) {
                Assert.assertTrue(errorText.equals(data.get(key)),
                        "Error message for " + key + " mismatch! - FAIL!");
            } else {
                Assert.assertNotNull(errorText);
                System.out.println("Error text not found");
            }
        }


    }

    @Then("I validate Contact Payload data")
    public void i_validate_Contact_data() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_CASELDR_CONTACTS order by JOB_ID DESC;");
        String payLoad = db.getCellValue(1, 7);
        JsonElement payLoadData = new JsonParser().parse(payLoad);
        System.out.println("contacts payLoad  ==== " + payLoadData.toString());
        assertNotNull(payLoadData, "contacts payLoad is NULL");

        assertTrue(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonObject().get("addressType").toString().contains("Physical"));
        assertTrue(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(1).getAsJsonObject().get("addressType").toString().contains("Mailing"));
//phone object validation
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("uiid"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("createdBy"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("createdOn"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("phoneType"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("updatedBy"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("updatedOn"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("phoneNumber"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("phoneSource"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("correlationId"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().get("effectiveStartDate"));
//address validation
        assertEquals(payLoadData.getAsJsonObject().getAsJsonArray("addresses").size(), 2);
        for (int i = 0; i < 2; i++) {
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("uiid"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("createdBy"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("createdOn"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("addressType"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("addressCity"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("addressZip"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("addressState"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("addressSource"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("correlationId"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("addressStreet1"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("addressZipFour"));
            assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(i).getAsJsonObject().get("addressCountyCode"));
        }
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonObject().get("addressCounty"));


    }

    @Then("I validate consumer data in staging table")
    public void i_validate_consumer_data_in_staging_table() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-dceb`.ETL_CASEEXT_CONSUMER where job_id='" + jobID + "' order by PROFILE_ID desc");

        String payLoad = db.getCellValue(1, "RECORD_CONTENT");
        JsonElement payLoadData = new JsonParser().parse(payLoad);
        System.out.println("consumer payLoad  ==== " + payLoadData.toString());
        assertNotNull(payLoadData, "consumer payLoad is NULL");

        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().get("contact").getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonObject().get("addressStreet1"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().get("contact").getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonObject().get("addressCity"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().get("contact").getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonObject().get("addressState"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().get("contact").getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonObject().get("addressZip"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().getAsJsonArray("cases").get(0).getAsJsonObject().get("caseId"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().get("contact").getAsJsonObject().getAsJsonArray("phones").get(1).getAsJsonObject().get("phoneNumber"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().get("ssn"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().get("ethnicityCode"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("profiles").get(0).getAsJsonObject().get("ethnicityCode"));

        assertNotNull(payLoadData.getAsJsonObject().get("firstName"));
        assertNotNull(payLoadData.getAsJsonObject().get("lastName"));
        assertNotNull(payLoadData.getAsJsonObject().get("lastName"));
        assertNotNull(payLoadData.getAsJsonObject().get("sexCode"));
        assertNotNull(payLoadData.getAsJsonObject().get("dateOfBirth"));
        assertNotNull(payLoadData.getAsJsonObject().get("sexCode"));

        db.runQuery("select * from `mars-etl-dceb`.ETL_SELECTION_ELIGIBILITY where job_id='" + jobID + "' order by PROFILE_ID desc");
        String eligibilityPayLoad = db.getCellValue(1, "RECORD_CONTENT");
        JsonElement eligibilityPayLoadData = new JsonParser().parse(eligibilityPayLoad);

        assertNotNull(eligibilityPayLoadData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get("startDate"));
        assertNotNull(eligibilityPayLoadData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get("endDate"));
        assertNotNull(eligibilityPayLoadData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get("coverageCode"));

        db.runQuery("select * from `mars-etl-dceb`.ETL_SELECTION_INIT where job_id='" + jobID + "' order by PROFILE_ID desc");
        String selectionPayLoad = db.getCellValue(1, "RECORD_CONTENT");
        JsonElement selectionPayLoadData = new JsonParser().parse(selectionPayLoad);

        assertNotNull(selectionPayLoad, "selection payLoad is NULL");
        assertNotNull(selectionPayLoadData.getAsJsonObject().get("planCode"));
        assertNotNull(selectionPayLoadData.getAsJsonObject().get("status"));
        assertNotNull(selectionPayLoadData.getAsJsonObject().get("txnStatus"));
        assertNotNull(selectionPayLoadData.getAsJsonObject().get("consumerId"));
        assertNotNull(selectionPayLoadData.getAsJsonObject().get("serviceRegionCode"));
        assertNotNull(selectionPayLoadData.getAsJsonObject().get("subProgramTypeCode"));

    }

    @Then("I validate synchronous API calls to Consumer V2, Contact and Eligibility and Enrollment")
    public void i_validate_synchronous_API() {
        db.createConnection();
        db.runQuery("select max(job_id) FROM `mars-etl-dceb`.ETL_REPORT where JOBNAME_CD='dailyEligibleJob';");
        String jobId = db.getFirstRowFirstColumn();
        System.out.println("JOB_ID ======== " + jobId);
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_CASELDR_RESULT where JOB_ID='"+jobId+"';");
        ArrayList<String> listOfAPInames = new ArrayList<>();
        for(int i=1; i<4;i++){
            //add api name in to list for validation
            String actualAPIname = db.getCellValue(i, 7);
            System.out.println("APIname === " + actualAPIname);
            listOfAPInames.add(actualAPIname);
            //verify if response status code is 200
            String statusCode = db.getCellValue(i, 5);
            assertEquals(statusCode, "200");
            //verify response is not null
            String responsePayload = db.getCellValue(i, 6);
            JsonElement payLoadData = new JsonParser().parse(responsePayload);
            assertNotNull(responsePayload, "JSON response is empty or NULL");
            assertTrue(payLoadData.getAsJsonObject().get("status").toString().contains("success"));
        }
        //verify ETL will make synchronous API calls to
        assertTrue(listOfAPInames.contains("EE"));
        assertTrue(listOfAPInames.contains("CONSUMER"));
        assertTrue(listOfAPInames.contains("CONTACT"));

    }

    @Then("I will map the Staging data relating to the Eligibility and Enrollment Payload")
    public void i_validate_Enrollment_Payload() {
        db.createConnection();
        db.runQuery("select max(job_id) FROM `mars-etl-dceb`.ETL_REPORT where JOBNAME_CD='dailyEligibleJob';");
        String jobId = db.getFirstRowFirstColumn();
        System.out.println("JOB_ID ======== " + jobId);
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_CASELDR_ELIG_ENROLL where JOB_ID='"+jobId+"';");

        String payLoad = db.getCellValue(1, "RECORD_CONTENT");
        JsonElement payLoadData = new JsonParser().parse(payLoad);
        System.out.println("Eligibility and Enrollment Payload  ==== " + payLoadData.toString());
        assertNotNull(payLoadData, "consumer payLoad is NULL");

        //verify of ENROLLMENT DATA
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject());
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().get("planId"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().get("planCode"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().get("recordId"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().get("correlationId"));
        assertTrue(payLoadData.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().get("enrollmentType").toString().contains( "MEDICAL"));
        assertTrue(payLoadData.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().get("programTypeCode").toString().contains( "MEDICAID"));
        assertTrue(payLoadData.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().get("subProgramTypeCode").toString().contains(  "DCHF"));
        //verify of ELIGIBILITIES DATA not NULL
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject());
        //verify of CORE ELIGIBILITY DATA
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().get("coreEligibility"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("programCode"));
        assertTrue(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("programCode").toString().contains(  "MEDICAID"));
        assertTrue(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("programTypeCode").toString().contains(  "MEDICAID"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("coverageCode"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("correlationId"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("subProgramTypeCode"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("eligibilityEndReason"));




    }
    @Then("I validate prior consumer medicaid id in caseloader result payload for consumer")
    public void i_validate_prior_consumer_medicaid_id_in_caseloader_result_payload_for_consumer() {

        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-dceb`.ETL_CASELDR_RESULT WHERE JOB_ID='" + jobID + "'");
        String ConsumerPayload=db.getCellValue(1,"RESPONSE_MESSAGE");
        System.out.println(ConsumerPayload);
        JsonElement payLoadData= new JsonParser().parse(ConsumerPayload);
        System.out.println("consumer payload =" + ConsumerPayload.toString());
        assertNotNull(ConsumerPayload,"Consumer payload is Null");

        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("data").get(0).getAsJsonObject().get("priorConsumerProfile"));
        assertNotNull(payLoadData.getAsJsonObject().getAsJsonArray("data").get(0).getAsJsonObject().get("consumer").getAsJsonObject().get("internalConsumerId"));

    }
    @Then("I validate prior consumer medicaid id as null in caseloader result payload for consumer")
    public void i_validate_prior_consumer_medicaid_id_as_null_in_caseloader_result_payload_for_consumer() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select * from `mars-etl-dceb`.ETL_CASELDR_RESULT WHERE JOB_ID='" + jobID + "'");
        String ConsumerPayload=db.getCellValue(1,"RESPONSE_MESSAGE");
        System.out.println(ConsumerPayload);
        JsonElement payLoadData= new JsonParser().parse(ConsumerPayload);
        System.out.println("consumer payload =" + ConsumerPayload.toString());
        assertNotNull(ConsumerPayload,"Consumer payload is Null");

        //assertNull(payLoadData.getAsJsonObject().getAsJsonArray("data").get(0).getAsJsonObject().get("priorConsumerProfile"));
        assertEquals(String.valueOf(payLoadData.getAsJsonObject().getAsJsonArray("data").get(0).getAsJsonObject().get("priorConsumerProfile")),"null","This value is not null");
        System.out.println(payLoadData.getAsJsonObject().getAsJsonArray("data").get(0).getAsJsonObject().get("priorConsumerProfile"));


    }

}






