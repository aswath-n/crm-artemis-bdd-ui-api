package com.maersk.crm.api_step_definitions;


import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class APITaskQFilterController extends CRMUtilities implements ApiBase {
    private String taskManagementBaseURI = ConfigurationReader.getProperty("apiTaskManagementURI");
    private String getTaskQueueFilter = "/mars/taskmanagement/taskqueuefilters";
    private String viewTaskQueueFilter = "/mars/taskmanagement/taskqueuefilter/";
    private String workQueueTasks = "/mars/taskmanagement/workqueue/tasks";
    private String taskListSize = "?size=5&page=0";
    private String apiAssesment = ConfigurationReader.getProperty("apiMarsAssesment");
    private String surveyRes = "/survey-response";
    private static final ThreadLocal<String> token = ThreadLocal.withInitial(() -> null);
    final ThreadLocal<String> taskQFilterId = ThreadLocal.withInitial(() -> null);
    final ThreadLocal<JSONObject> taskQFilterRecord = ThreadLocal.withInitial(() -> null);
    final ThreadLocal<JSONObject> taskTypeRecord = ThreadLocal.withInitial(() -> null);
    final ThreadLocal<JSONObject> taskScopeRecord = ThreadLocal.withInitial(() -> null);

    //Vidya-- Method to initiate task queue filter get api
    @And("I initiate task queue filter Get APi")
    public void initiateTaskQueueFilterGetApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskQueueFilter);
    }

    @When("I run the task queue filter Get APi")
    public void willRunTheTaskQueueFilterGetApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @Then("I verify the response of task queue filter Get api has task queue table data")
    public void verifyTaskQueueFilterGetApiResponse() {
        taskQFilterRecord.set(new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskQueueFilterVOs").
                get(0).getAsJsonObject().toString()));
        taskQFilterId.set(taskQFilterRecord.get().getInt("taskQueueFilterId") + "");
        assertTrue(taskQFilterRecord.get().getInt("taskQueueFilterId") != 0 &&
                !taskQFilterRecord.get().isNull("taskQueueFilterId"), "taskQueueFilterId is mismatch");
        assertTrue(!taskQFilterRecord.get().getString("queueName").isEmpty()
                && !taskQFilterRecord.get().isNull("queueName"), "queueName is empty or null");
        assertTrue(!taskQFilterRecord.get().get("description").toString().isEmpty() ||
                taskQFilterRecord.get().isNull("description"), "description is empty");
        assertTrue(!taskQFilterRecord.get().getString("scopeType").isEmpty() &&
                !taskQFilterRecord.get().isNull("scopeType"), "scopeType is empty or null");
        assertTrue(taskQFilterRecord.get().getBoolean("status") || !taskQFilterRecord.get().getBoolean("status"),
                "status is empty or null");
        assertTrue(!taskQFilterRecord.get().getString("createdBy").isEmpty() &&
                !taskQFilterRecord.get().isNull("createdBy"), "createdBy is empty or null");
        assertTrue(!taskQFilterRecord.get().getString("updatedBy").isEmpty() &&
                !taskQFilterRecord.get().isNull("updatedBy"), "updatedBy is empty or null");
        assertTrue(EventsUtilities.isValidDate(taskQFilterRecord.get().getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(taskQFilterRecord.get().getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!taskQFilterRecord.get().getString("correlationId").isEmpty() &&
                !taskQFilterRecord.get().isNull("correlationId"), "correlationId is empty or null");
        assertTrue(!taskQFilterRecord.get().getString("uiid").isEmpty() && !taskQFilterRecord.get().isNull("uiid"),
                "uiid is empty or null");
    }

    @Then("I verify the response has task queue filter task type table data")
    public void verifyTaskQueueFilterTaskTypeTableData() {
        taskTypeRecord.set(new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskQueueFilterVOs").
                get(0).getAsJsonObject().getAsJsonArray("taskQueueFilterTaskTypes").get(0).
                getAsJsonObject().toString()));
        assertTrue(taskTypeRecord.get().getInt("taskQueueFilterTaskTypeId") != 0 && !taskTypeRecord.get().isNull("taskQueueFilterTaskTypeId"),
                "taskQueueFilterTaskTypeId is mismatch");
        assertTrue(taskTypeRecord.get().getInt("taskQueueFilterId") != 0 && !taskTypeRecord.get().isNull("taskQueueFilterId"),
                "taskQueueFilterId is mismatch");
        assertTrue(taskTypeRecord.get().getInt("taskTypeId") != 0 && !taskTypeRecord.get().isNull("taskTypeId"),
                "taskTypeId is mismatch");
        assertTrue(EventsUtilities.isValidDate(taskTypeRecord.get().getString("effectiveStartDate")),
                "createdOn date time field IS NOT valid");
        assertTrue(taskTypeRecord.get().isNull("effectiveEndDate"), "effectiveEndDate is not null");
        assertTrue(!taskTypeRecord.get().getString("createdBy").isEmpty() && !taskTypeRecord.get().isNull("createdBy"),
                "createdBy is empty or null");
        assertTrue(!taskTypeRecord.get().getString("updatedBy").isEmpty() && !taskTypeRecord.get().isNull("updatedBy"),
                "updatedBy is empty or null");
        assertTrue(EventsUtilities.isValidDate(taskTypeRecord.get().getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(taskTypeRecord.get().getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!taskTypeRecord.get().getString("correlationId").isEmpty() && !taskTypeRecord.get().isNull("correlationId"),
                "correlationId is empty or null");
        assertTrue(!taskTypeRecord.get().getString("uiid").isEmpty() && !taskTypeRecord.get().isNull("uiid"),
                "uiid is empty or null");
    }

    @Then("I verify the response has task queue filter scopes table data")
    public void verifyTaskQueueFilterScopesTableData() {
        taskScopeRecord.set(new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskQueueFilterVOs").
                get(0).getAsJsonObject().getAsJsonArray("taskQueueFilterScopes").get(0).
                getAsJsonObject().toString()));
        assertTrue(taskScopeRecord.get().getInt("taskQueueFilterScopeId") != 0 && !taskScopeRecord.get().isNull("taskQueueFilterScopeId"),
                "taskQueueFilterScopeId is mismatch");
        assertTrue(taskScopeRecord.get().getInt("taskQueueFilterId") != 0 && !taskScopeRecord.get().isNull("taskQueueFilterId"),
                "taskQueueFilterId is mismatch");
        assertTrue(taskScopeRecord.get().getInt("scopeId") != 0 && !taskScopeRecord.get().isNull("scopeId"),
                "scopeId is mismatch");
        assertTrue(EventsUtilities.isValidDate(taskScopeRecord.get().getString("effectiveStartDate")),
                "createdOn date time field IS NOT valid");
        assertTrue(taskScopeRecord.get().isNull("effectiveEndDate"), "effectiveEndDate is not null");
        assertTrue(!taskScopeRecord.get().getString("createdBy").isEmpty() && !taskScopeRecord.get().isNull("createdBy"),
                "createdBy is empty or null");
        assertTrue(!taskScopeRecord.get().getString("updatedBy").isEmpty() && !taskScopeRecord.get().isNull("updatedBy"),
                "updatedBy is empty or null");
        assertTrue(EventsUtilities.isValidDate(taskScopeRecord.get().getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(taskScopeRecord.get().getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!taskScopeRecord.get().getString("correlationId").isEmpty() && !taskScopeRecord.get().isNull("correlationId"),
                "correlationId is empty or null");
        assertTrue(!taskScopeRecord.get().getString("uiid").isEmpty() && !taskScopeRecord.get().isNull("uiid"),
                "uiid is empty or null");
    }

    //Vidya-- Method to initiate task queue filter get api
    @And("I initiate view task queue filter Get APi")
    public void initiateViewTaskQueueFilterGetApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        viewTaskQueueFilter = viewTaskQueueFilter + taskQFilterId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(viewTaskQueueFilter);
    }

    @Then("I verify the response of view task queue filter Get api has task queue table data")
    public void verifyViewTaskQueueFilterGetApiResponse() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("taskQueueFilterVO").toString());
        assertEquals(temp.getInt("taskQueueFilterId"), taskQFilterRecord.get().getInt("taskQueueFilterId"),
                "taskQueueFilterId is mismatch");
        assertEquals(temp.getString("queueName"), taskQFilterRecord.get().getString("queueName"),
                "queueName is mismatch");
        assertEquals(temp.get("description").toString(), taskQFilterRecord.get().get("description").toString(),
                "description is mismatch");
        assertEquals(temp.getString("scopeType"), taskQFilterRecord.get().getString("scopeType"),
                "scopeType is mismatch");
        assertEquals(temp.getBoolean("status"), taskQFilterRecord.get().getBoolean("status"),
                "status is mismatch");
        assertEquals(temp.getString("createdBy"), taskQFilterRecord.get().getString("createdBy"),
                "createdBy is mismatch");
        assertEquals(temp.getString("updatedBy"), taskQFilterRecord.get().getString("updatedBy"),
                "updatedBy is mismatch");
        assertEquals(temp.getString("createdOn"), taskQFilterRecord.get().getString("createdOn"),
                "createdOn is mismatch");
        assertEquals(temp.getString("updatedOn"), taskQFilterRecord.get().getString("updatedOn"),
                "updatedOn is mismatch");
        assertEquals(temp.getString("correlationId"), taskQFilterRecord.get().getString("correlationId"),
                "correlationId is mismatch");
        assertEquals(temp.getString("uiid"), taskQFilterRecord.get().getString("uiid"),
                "uiid is mismatch");
    }

    @Then("I verify the response has view task queue filter task type table data")
    public void verifyViewTaskQueueFilterTaskTypeTableData() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("taskQueueFilterVO").
                getAsJsonArray("taskQueueFilterTaskTypes").get(0).getAsJsonObject().toString());
        assertEquals(temp.getInt("taskQueueFilterTaskTypeId"), taskTypeRecord.get().getInt("taskQueueFilterTaskTypeId"),
                "taskQueueFilterTaskTypeId is mismatch");
        assertEquals(temp.getInt("taskQueueFilterId"), taskTypeRecord.get().getInt("taskQueueFilterId"),
                "taskQueueFilterId is mismatch");
        assertEquals(temp.getInt("taskTypeId"), taskTypeRecord.get().getInt("taskTypeId"),
                "taskTypeId is mismatch");
        assertEquals(temp.getString("effectiveStartDate"), taskTypeRecord.get().getString("effectiveStartDate"),
                "effectiveStartDate is mismatch");
        assertEquals(temp.get("effectiveEndDate"), taskTypeRecord.get().get("effectiveEndDate"),
                "effectiveEndDate is mismatch");
        assertEquals(temp.getString("createdBy"), taskTypeRecord.get().getString("createdBy"),
                "createdBy is mismatch");
        assertEquals(temp.getString("updatedBy"), taskTypeRecord.get().getString("updatedBy"),
                "updatedBy is mismatch");
        assertEquals(temp.getString("createdOn"), taskTypeRecord.get().getString("createdOn"),
                "createdOn is mismatch");
        assertEquals(temp.getString("updatedOn"), taskTypeRecord.get().getString("updatedOn"),
                "updatedOn is mismatch");
        assertEquals(temp.getString("correlationId"), taskTypeRecord.get().getString("correlationId"),
                "correlationId is mismatch");
        assertEquals(temp.getString("uiid"), taskTypeRecord.get().getString("uiid"),
                "uiid is mismatch");
    }

    @Then("I verify the response has view task queue filter scopes table data")
    public void verifyViewTaskQueueFilterScopesTableData() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("taskQueueFilterVO").
                getAsJsonArray("taskQueueFilterScopes").get(0).getAsJsonObject().toString());
        assertEquals(temp.getInt("taskQueueFilterScopeId"), taskScopeRecord.get().getInt("taskQueueFilterScopeId"),
                "taskQueueFilterScopeId is mismatch");
        assertEquals(temp.getInt("taskQueueFilterId"), taskScopeRecord.get().getInt("taskQueueFilterId"),
                "taskQueueFilterId is mismatch");
        assertEquals(temp.getInt("scopeId"), taskScopeRecord.get().getInt("scopeId"),
                "scopeId is mismatch");
        assertEquals(temp.getString("effectiveStartDate"), taskScopeRecord.get().getString("effectiveStartDate"),
                "effectiveStartDate is mismatch");
        assertEquals(temp.get("effectiveEndDate"), taskScopeRecord.get().get("effectiveEndDate"),
                "effectiveEndDate is mismatch");
        assertEquals(temp.getString("createdBy"), taskScopeRecord.get().getString("createdBy"),
                "createdBy is mismatch");
        assertEquals(temp.getString("updatedBy"), taskScopeRecord.get().getString("updatedBy"),
                "updatedBy is mismatch");
        assertEquals(temp.getString("createdOn"), taskScopeRecord.get().getString("createdOn"),
                "createdOn is mismatch");
        assertEquals(temp.getString("updatedOn"), taskScopeRecord.get().getString("updatedOn"),
                "updatedOn is mismatch");
        assertEquals(temp.getString("correlationId"), taskScopeRecord.get().getString("correlationId"),
                "correlationId is mismatch");
        assertEquals(temp.getString("uiid"), taskScopeRecord.get().getString("uiid"),
                "uiid is empty mismatch");
    }

    @When("I initiated Work Queue Task Retrieval API")
    public void initiatedWorkQueueTaskRetrievalAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(workQueueTasks + taskListSize);
    }

    @Then("I run Work Queue Task Retrieval API call with data")
    public void iRunWorkQueueTaskRetrievalApiCallWithData(Map<String, String> field) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/workQueueTaskAPIBusinessUnit.json");

        String json1 = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json1, field));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Work Queue Task Retrieval API is FAIL >>");
    }

    @And("Validate Work Queue API call logged with businessUnitId as part of the payload request")
    public void validateWorkQueueAPICallLoggedWithBusinessUnitIdAsPartOfThePayloadRequest(Map<String, String> field) {
        assertEquals(Integer.parseInt(field.get("businessUnitAssignedTo")), (int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.tasks[0].taskVO.businessUnitAssignedTo"));
        assertEquals(Integer.parseInt(field.get("taskTypeId")), (int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.tasks[0].taskVO.taskTypeId"));
        assertEquals(Integer.parseInt(field.get("taskId")), (int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.tasks[0].taskVO.taskId"));
    }

    @When("I initiated Survey Response API")
    public void initiatedSurveyResponseAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiAssesment);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(surveyRes);
    }

    @Then("I run Survey Response API call with data")
    public void iRunSurveyResponseApiCallWithData(Map<String, String> field) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/survey/hraSurveyResponse.json");
        Map<String, String> header = new HashMap<String, String>();

        header.put("Authorization", "Bearer " + token.get());
        header.put("Content-Type", "application/json");
        String json1 = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithHeaders(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json1, field), header);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Survey Response is FAIL >>");
    }

    @And("Validate Survey Response API payload response")
    public void ValidateSurveyResponseAPIPayloadResponse() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status"), "success");
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.survey[0].referenceId"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("37438-03.consumerId"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.survey[0].createTs"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.survey[0].createdBy"));
    }

    @When("I will get the Authentication token for proxy API with projectId {string} roleId {string}")
    public void getAuthenticationTokenForProxyApi(String projectId, String roleId) {
        getOAuthQAForProxy(projectId, roleId);
    }

    public static String getOAuthQAForProxy(String projectId, String roleId) {
        Response responseToken = given()
                .header("roleId", roleId)
                .header("projectId", projectId)
                .header("Authorization", "Basic d2ZUdTA5VTQ4SHNTWXY2ODhub0dVeG9adW5ESVRQV2M6b0pRQ1A3TVphRTBZWmNzZA==")
                .contentType("application/json")
                .body("username=SVC_mars_user_1" + "&password=6Y88Uwcw")
                .post(ConfigurationReader.getProperty("apiOauth2") + "/client/accesstoken?grant_type=client_credentials");
        if (responseToken.getStatusCode() == 200) {
            token.set(responseToken.jsonPath().get("access_token"));
            System.out.println("token: " + token.get());

        }
        return token.get();
    }
}
