package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APICaseConsumerAlertController extends CRMUtilities implements ApiBase {

    private String caseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String caseAlertAPI = "/app/crm/alert";
    String rundomFileAtt = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber + "_automated_alerts_file.xlsx";
    private String alertURI = ConfigurationReader.getProperty("AlertServices");
    private String fileUploadAPI = "app/crm/fileupload";
    private String filetransactionAPI = "app/crm/filetransaction";
    private String alertRecordsAPI = "app/crm/records";
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(() -> null);
    private String newAlertsAPI = "/app/crm/alerts";

    @Given("I initiated Case Consumer Alert API request")
    public void iInitiatedCaseConsumerAlertAPIRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseAlertAPI);
    }

    @Given("I initiated alert services API request for upload file")
    public void iInitiatedAlertAPIRequestforUploadFile() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(alertURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(fileUploadAPI);
    }

    @And("I run Case Consumer Alert API with {string} file format")
    public void iRunCaseConsumerAlertAPIWithFileFomat(String fileName) throws Throwable {
      String rundomFileName = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        switch (fileName) {
            case "unexpectedFormatDoc":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload("unexpectedFormat_alerts_file.doc", "case_alerts.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "unexpectedFormatTxt":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload("unexpectedFormat_alerts_file.txt", "case_alerts.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingAlertIdColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingAlertIdCol.xlsx", "missingAlertIdColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingCaseIdColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingCaseIdCol.xlsx", "missingCaseIdColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingConsumerIdColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingConsumerIdCol.xlsx", "missingConsumerIdColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingAlertTextColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingAlertTextCol.xlsx", "missingAlertTextColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingStartDateColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingStartDateCol.xlsx", "missingStartDateColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingEndDateColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingEndDateCol.xlsx", "missingEndDateColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingTypeColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingTypeCo.xlsx", "missingTypeColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "duplicateName":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload("case_alerts.xlsx", "case_alerts.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingFieldsCaseConsIds":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingFieldsCaseConsIds.xlsx", "missingFieldsCaseConsIds.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingFieldAlertText":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingFieldAlertText.xlsx", "missingFieldAlertText.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingFieldStartDate":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingFieldStartDate.xlsx", "missingFieldStartDate.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingFieldType":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingFieldType.xlsx", "missingFieldType.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "notNumericAlertId":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"notNumericAlertId.xlsx", "notNumericAlertId.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "notNumericCaseId":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"notNumericCaseId.xlsx", "notNumericCaseId.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "notNumericConsumerId":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"notNumericConsumerId.xlsx", "notNumericConsumerId.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "notFormatedStartDate":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"notFormatedStartDate.xlsx", "notFormatedStartDate.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "notFormatedEndDate":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"notFormatedEndDate.xlsx", "notFormatedEndDate.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "1001AlertText":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"1001AlertText.xlsx", "1001AlertText.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingFieldCaseTypeNew":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingFieldCaseTypeNew.xlsx", "missingFieldCaseTypeNew.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingFieldConsumerTypeNew":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingFieldConsumerTypeNew.xlsx", "missingFieldConsumerTypeNew.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingCaseTypeColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingCaseTypeColumn.xlsx", "missingCaseTypeColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            case "missingConsumerTypeColumn":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileName+"missingConsumerTypeColumn.xlsx", "missingConsumerTypeColumn.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
                break;
            default:
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutMiltiPartUpload(rundomFileAtt, "case_alerts.xlsx");
                System.out.println("++++++++++++++++++++==case_alerts.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());

        }
    }

    @Then("I validate Alert Response for successful upload")
    public void i_validate_Alert_Response_for_successful_upload() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("validationList").isJsonNull(), "validationList should be null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorResponse").isJsonNull(), "errorResponse should be null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("status").toString().equals("\"success\""));

        //       +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++due to change of response for this endpoint no longer has all this fields check
//        JsonObject alert0 = api.apiJsonObject.get("alert").getAsJsonArray().get(0).getAsJsonObject();
//        JsonObject alert1 = api.apiJsonObject.get("alert").getAsJsonArray().get(1).getAsJsonObject();
//
//        assertTrue(alert0.get("alertId").getAsInt() > 0, "Alert Id at index[0] should be >0");
//        assertTrue(alert0.get("caseId").getAsInt() == 121, "Case Id at index[0] does not match");
//        assertTrue(alert0.get("consumerId").getAsInt() == 560, "Consumer Id at index[0] does not match");
//        System.out.println(alert0.get("alertText").toString());
//        assertTrue(alert0.get("alertText").toString().equals("\"muhabbat\""), "Alert text at index[0] does not match");
//        assertTrue(alert0.get("effectiveStartDate").toString().contains("2021-09-12"), "Alert effective start date at index[0] does not match");
//        assertTrue(alert0.get("effectiveEndDate").isJsonNull(), "Alert effective end date at index[0] should be null");
//        assertTrue(alert0.get("createdOn").toString().contains(browserutils.getCurrentDateInYearFormat()));
//        assertTrue(alert0.get("updatedOn").toString().contains(browserutils.getCurrentDateInYearFormat()));
//        assertTrue(alert0.get("createdBy").toString().equals("\"3455\""));
//        assertTrue(alert0.get("updatedBy").toString().equals("\"3455\""));
//        System.out.println(alert0.get("createFileName").toString());
//        assertTrue(alert0.get("createFileName").toString().contains(rundomFileAtt));
//        assertTrue(alert0.get("lastUpdateFileName").isJsonNull());
//
//        assertTrue(alert1.get("alertId").getAsInt() > 0);
//        assertTrue(alert1.get("caseId").getAsInt() == 121);
//        assertTrue(alert1.get("consumerId").isJsonNull());
//        assertTrue(alert1.get("alertText").toString().equals("\" !@#$%^&*()_+|}{POIUYTREWQASDFGHJKL:?><MNBVCXZ~!@#$%^&*()_+=-0987654321`/.,';][qwertyuiopasdfghjklzxcvbnm\""));
//        assertTrue(alert1.get("effectiveStartDate").toString().contains("2021-10-01"));
//        assertTrue(alert1.get("effectiveEndDate").toString().contains("2024-10-01"), "Alert effective end date at index[1] does not match");
//        assertTrue(alert1.get("createdOn").toString().contains(browserutils.getCurrentDateInYearFormat()));
//        assertTrue(alert1.get("updatedOn").toString().contains(browserutils.getCurrentDateInYearFormat()));
//        assertTrue(alert1.get("createdBy").toString().equals("\"3455\""));
//        assertTrue(alert1.get("updatedBy").toString().equals("\"3455\""));
//        assertTrue(alert1.get("createFileName").toString().contains(rundomFileAtt));
//        assertTrue(alert1.get("lastUpdateFileName").isJsonNull());

    }


    @Then("Alert upload Error in response is returned with message {string}")
    public void alertUploadErrorInResponseIsReturnedWithMessage(String message) throws Throwable {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("validationList").isJsonNull(), "validationList should be null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("status").toString().equals("\"fail\""));

        JsonObject errorResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorResponse").getAsJsonObject();
        assertTrue(errorResponse.get("errorCode").isJsonNull());
        assertTrue(errorResponse.get("traceId").isJsonNull());
        assertTrue(errorResponse.get("errorMessage").toString().contains(message));
    }

    @When("I initiated alert filetransaction API")
    public void i_initiated_alert_filetransaction_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(alertURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(filetransactionAPI);
    }

    @When("I run filetransaction API with payload")
    public void i_run_filetransaction_API_with_payload(Map<String,String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/alert/alertTransactionsPayLoad.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uploadedBy", data.get("uploadedBy"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uploadedOnStartDate", data.get("uploadedOnStartDate"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uploadedOnEndDate", data.get("uploadedOnEndDate"));

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I validate response contains following data")
    public void i_validate_response_contains_following_data(Map<String, String> data) {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        Assert.assertEquals(temp.getString("status"), data.get("status"), "status is failed");

        if(data.containsKey("uploadedBy")){
            int size = temp.getJSONArray("alertTransactions").length();
            System.out.println("Size of array object alertTransactions ===== " + size);

            for(int i=0;i<size;i++){
                String actualUploadedBy = temp.getJSONArray("alertTransactions").getJSONObject(i).getString("uploadedBy");
                assertEquals(actualUploadedBy, data.get("uploadedBy") );
            }
        }

        if(data.containsKey("alertId")){
            Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getInt("alertId")+"", data.get("alertId"), "alertId not match");
        }
        if(data.containsKey("consumerId")) {
            Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getString("consumerId"), data.get("consumerId"), "consumerId not match");
            Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getString("consumerIdType"), data.get("consumerType"), "consumerType not match");
        }

        if(data.containsKey("caseId")) {
            Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getString("caseId"), data.get("caseId"), "caseId not match");
            Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getString("caseIdType"), data.get("caseType"), "caseIdType not match");
        }
    }

    @Then("I validate alertFileTransactionId is sorted by descending order")
    public void validateSortedByDescendingOrder(){
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
           int size = temp.getJSONArray("alertTransactions").length();
            System.out.println("Size of array object alertTransactions ===== " + size);

            for(int i=0;i<size-1;i++){
                int actualAlertFileTransactionId = temp.getJSONArray("alertTransactions").getJSONObject(i).getInt("alertFileTransactionId");
                int nextAlertFileTransactionId = temp.getJSONArray("alertTransactions").getJSONObject(i+1).getInt("alertFileTransactionId");
                assertTrue(actualAlertFileTransactionId>nextAlertFileTransactionId);
        }
    }

    @When("I initiated alert records API")
    public void i_initiated_alert_records_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(alertURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(alertRecordsAPI);
    }

    @When("I run records API with payload")
    public void i_run_records_API_with_payload(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/alert/recordsPayload.json");

        if(data.get("alertId")!=null) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("alertId", data.get("alertId"));
        }
        if(data.get("consumerId")!=null) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", data.get("consumerId"));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", data.get("consumerType"));
        }
        if(data.get("caseId")!=null) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", data.get("caseId"));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseType", data.get("caseType"));
        }


        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I initiated alerts API")
    public void I_initiated_alerts_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(alertURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(newAlertsAPI);
    }
    @When("I update existing alert using following data")
    public void update_existing_alert(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/alert/udateArertPayLoad.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("alertId", data.get("alertId"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("alertText", data.get("alertText"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("type", data.get("type"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("effectiveStartDate", data.get("effectiveStartDate"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("effectiveEndDate", data.get("effectiveEndDate"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("consumerId", data.get("consumerId"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("consumerIdType", data.get("consumerIdType"));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("caseId", data.get("caseId"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("alerts").get(0).getAsJsonObject().addProperty("caseIdType", data.get("caseIdType"));

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify alert record updated with following data")
    public void I_verify_alert_record_updated_with_following_data(Map<String, String> data) {

        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        Assert.assertEquals(temp.getString("status"), "success", "status is failed");
        Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getInt("alertId")+"", data.get("alertId"), "alertId not match");
        Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getString("alertText")+"", data.get("alertText"), "alertText not match");
        Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getString("effectiveStartDate").substring(0,10)+"", data.get("effectiveStartDate"), "effectiveStartDate not match");
        Assert.assertEquals(temp.getJSONArray("alerts").getJSONObject(0).getString("effectiveEndDate").substring(0,10)+"", data.get("effectiveEndDate"), "effectiveEndDate not match");
        }

}