package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

//import static com.maersk.crm.utilities.ApiBase(API_THREAD_LOCAL_FACTORY;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APITMAddBusinessUnitController extends CRMUtilities implements ApiBase{

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public static final ThreadLocal<Integer> businessUnitId  = ThreadLocal.withInitial(() -> 0);
    public static final ThreadLocal<String> businessUnitName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessUnitDesc = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> buStartDate =ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> buEndDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> buCreatedOn = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> buUpdatedOn = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> buUpdatedBy = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> buCreatedBy = ThreadLocal.withInitial(String::new);
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String createBusinessUnitEndPoint = "mars/tm/businessunit";

    public static final ThreadLocal<String> dateFormat = ThreadLocal.withInitial(()->"dd-MM-yyyy hh:mm");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.get());
    APITaskManagementController taskTypeController = new APITaskManagementController();

    public static String ConvertMilliSecondsToFormattedDate(String milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
        return simpleDateFormat.format(calendar.getTime());
    }

    @Given("I initiated Create Business Unit API")
    public void i_initiated_create_business_unit_api() throws Exception {
        //throw new Exception("Step Disabled untill further notification");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createBusinessUnitEndPoint);
    }

    @When("I can provide business unit details with {string} {string} {string} {string} {string} {string}")
    public void iCanprovideBUDetails(String projectId, String buName, String buDesc, String startDate, String endDate, String taskType) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateBusinessUnit.json");

        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectId", projectId);

        if (buName.isEmpty() || buName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            this.businessUnitName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("businessUnitName", this.businessUnitName.get());

        if (buDesc.isEmpty() || buDesc.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            this.businessUnitDesc.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("description", this.businessUnitDesc.get());

        if (startDate.equalsIgnoreCase("currentDate")) {
            buStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0, 10));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", buStartDate.get());
        } else if (!startDate.isEmpty() && startDate.contains("+")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(Integer.parseInt(startDate.replace("+", "")));
            buStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates),
                    ZoneOffset.UTC).toString().substring(0, 10));
            buStartDate.set(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(Integer.parseInt(startDate.replace("+", ""))).toString().substring(0, 10));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", buStartDate.get());
        } else if (!startDate.isEmpty() && startDate.contains("-")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(Integer.parseInt(startDate.replace("-", "")));
            buStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates);
        }

        if (endDate.equalsIgnoreCase("currentDate")) {
            buEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", buEndDate.get());
        } else if (!endDate.isEmpty() && endDate.contains("+")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(Integer.parseInt(endDate.replace("+", "")));
            buEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", buEndDate.get());
        } else if (!endDate.isEmpty() && endDate.contains("-")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(Integer.parseInt(endDate.replace("-", "")));
            buEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", buEndDate.get());
        } else {
            String date = null;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", date);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", System.currentTimeMillis());
        buCreatedOn.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0, 16));
        ;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", System.currentTimeMillis());
        buUpdatedOn.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0, 16));

        buCreatedBy.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        buUpdatedBy.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", buCreatedBy.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", buUpdatedBy.get());

        if (taskType.equals("{select}")) {
            taskTypeController.initiateGetTaskTypeApi(projectId);
            taskTypeController.runGetTaskTypeApi();
            taskTypeController.i_verify_Task_type_records_are_retrieved();
            int taskId = Integer.parseInt(taskTypeController.taskTypeId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("tasks").getAsJsonArray("create").
                    add(taskId);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @And("I run the create business unit API")
    public void i_run_the_create_business_unit_api() throws Exception {
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        businessUnitName.set(requestParams.get().get("businessUnitName").toString().replace("\"", ""));
    }

    @Then("I can get API response status as {string} and error code as {string}")
    public void statusCheck(String status, String errorCode) {
        switch (status) {
            case "success":
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
                businessUnitId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data").get("businessUnitId").getAsInt());
                break;

            case "fail":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("errorResponse").
                        get("errorCode").getAsString(), errorCode);
                break;

        }
    }

    @When("I can provide business unit details with {string} {string} {string} {string} {string} {string} {string}")
    public void iCanprovideBUDetails(String buId, String projectId, String buName, String buDesc, String startDate, String endDate, String taskType) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiEditBusinessUnit.json");

        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectId", projectId);

        if (buId.isEmpty()) {
            buId = APITMListBusinessUnitController.businessUnit + "";
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("businessUnitId", buId);

        if (buName.isEmpty() || buName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            this.businessUnitName.set("BU" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("businessUnitName", this.businessUnitName.get());

        if (buDesc.isEmpty() || buDesc.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            this.businessUnitDesc.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("description", this.businessUnitDesc.get());

        if (startDate.equalsIgnoreCase("currentDate")) {
            buStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", System.currentTimeMillis());
        } else if (!startDate.isEmpty() && startDate.contains("+")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(Integer.parseInt(startDate.replace("+", "")));
            buStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates);
        } else if (!startDate.isEmpty() && startDate.contains("-")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(Integer.parseInt(startDate.replace("-", "")));
            buStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates);
        }

        if (endDate.equalsIgnoreCase("currentDate")) {
            buEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", System.currentTimeMillis());
        } else if (!endDate.isEmpty() && endDate.contains("+")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(Integer.parseInt(endDate.replace("+", "")));
            buEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates);
        } else if (!endDate.isEmpty() && endDate.contains("-")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(Integer.parseInt(endDate.replace("-", "")));
            buEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates);
        } else {
            String date = null;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", date);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", System.currentTimeMillis());
        buCreatedOn.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0, 16));
        ;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", System.currentTimeMillis());
        buUpdatedOn.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0, 16));

        buCreatedBy.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        buUpdatedBy.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", buCreatedBy.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", buUpdatedBy.get());

        if (taskType.equals("{select}")) {
            taskTypeController.initiateGetTaskTypeApi(projectId);
            taskTypeController.runGetTaskTypeApi();
            taskTypeController.i_verify_Task_type_records_are_retrieved();
            int taskId = Integer.parseInt(taskTypeController.taskTypeId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("tasks").getAsJsonArray("create").
                    add(taskId);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }
}
