package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.step_definitions.CRM_GeneralTaskStepDef;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.maersk.crm.step_definitions.CRM_TaskManagementStepDef.*;
import static com.maersk.dms.utilities.EventsUtilities.isOnlyValidDate;
import static org.testng.Assert.*;

public class APITaskRecordsController extends CRMUtilities implements ApiBase {

    private String taskManagementBaseURI = ConfigurationReader.getProperty("apiTaskManagementURI");
    private String getInProgressTaskByUserId = "/mars/taskmanagement/assignedto/{user_id}/inprogress";
    private String getTaskEndPoint = "mars/taskmanagement/task";
    private String getTaskRecordEndPoint = "/app/crm/search/taskrecords";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<Integer> taskId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<Integer> taskTypeId = ThreadLocal.withInitial(() -> 0);


    @Given("I initiated get In Progress task by user id")
    public void initiateGetTaskInProgressByUserId(){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiUserId= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getInProgressTaskByUserId.replace("{user_id}", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiUserId));
    }

    @And("I run get task task In Progress by user id")
    public void runGetTaskByStatus(){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @And("I initiate update task")
    public void initiateUpdateTask() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskEndPoint);
    }

    @When("I build payload to update status of task to Complete through api")
    public void buildPayloadToUpdateInProgressTaskToComplete() {
        JsonObject jsonObject = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("task");
        jsonObject.addProperty("taskStatus", "Cancelled");
        jsonObject.addProperty("cancelReason", "CREATED_INCORRECTLY");
        requestParams.set(jsonObject);
    }

    @When("I run update task status API")
    public void runInitaiteTask() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @When("If any In Progress task present then update that to Cancelled")
    public void updateTaskInProgressToComplete(){
        initiateGetTaskInProgressByUserId();
        runGetTaskByStatus();
        taskId.set((int) Optional.ofNullable(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("task.taskId")).orElse(0));
        taskTypeId.set((int) Optional.ofNullable(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("task.taskTypeId")).orElse(0));
        if(taskId.get() != 0 && taskTypeId.get() != 0) {
            System.out.println("User " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiUserId + " is assigned to " + taskId);
            initiateUpdateTask();
            buildPayloadToUpdateInProgressTaskToComplete();
            runInitaiteTask();
        }
        else {
            System.out.println("User " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiUserId + " not assigned to In Progress Task");
        }
    }

    @Given("I initiated search records API")
    public void initiateSaveTaskSearch() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiUserId= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiInboundTaskSearch"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskRecordEndPoint);
    }

    @Then("I validate defaultDueDate does not contain time")
    public void validateDefaultDueDateDoesNotContainTime(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("staffAssignedTo","{user_id}".replace("{user_id}", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiUserId));
        requestParams.set(jsonObject.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        List<String> defaultDueDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("taskRecords.defaultDueDate");
        List<String> listDefaultDueDateLimited =  defaultDueDate.stream().filter(Objects::nonNull).limit(50).collect(Collectors.toList());
        listDefaultDueDateLimited.forEach(ddd->assertTrue(isOnlyValidDate(ddd),"Incorrect date format"));
    }

    @Then("I validate serviceRequestInd value for {string}")
    public void validateTaskOrSrPresentedInTheResponse(String taskOrSR){
        boolean isSR = taskOrSR.equalsIgnoreCase("SR");
        boolean isTask = taskOrSR.equalsIgnoreCase("TASK");
        String id = taskOrSR.equalsIgnoreCase("SR") ? srID.get() : CRM_TaskManagementStepDef.taskId.get();
        getTaskRecordsByServiceRequestIndAndTaskId(isSR, id);
        boolean serviceRequestIndValue = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("taskRecords[0].serviceRequestInd");
        assertEquals(serviceRequestIndValue,isSR);
        getTaskRecordsByServiceRequestIndAndTaskId(isTask, id);
        int count = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("count");
        assertEquals(count,0);
    }

    private void getTaskRecordsByServiceRequestIndAndTaskId(boolean isSR, String id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("serviceRequestInd",isSR);
        jsonObject.addProperty("taskId",id);
        requestParams.set(jsonObject.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
    }

    @Then("I verify request payload {string} contains assignedFlag {string} and serviceRequestInd {string}")
    public void verifyRequestPayload(String parametr,String assignedFlag, String serviceRequestInd) {
        String payloadRequest = EventsUtilities.getRawLogs("traceid", parametr).stream().findFirst().orElseThrow(NullPointerException::new);
        String assignedFlagStr = "assignedFlag\":"+assignedFlag+"";
        String serviceRequestIndStr = "serviceRequestInd\":"+serviceRequestInd+"";
        sa.get().assertThat(payloadRequest.replace("\\", "")).as("Payload Request does not return correct values").contains(assignedFlagStr,serviceRequestIndStr);
    }

    @Then("I verify staffAssignedTo field returns {string}")
    public void verifyStaffAssignedToField(String staffAssignedToValue){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("taskId", CRM_GeneralTaskStepDef.taskValues.get().get("taskID"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(jsonObject.getAsJsonObject());
        if (staffAssignedToValue.equalsIgnoreCase("null")) {
            Assert.assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("taskRecords[0].staffAssignedTo"));
        } else {
            Assert.assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("taskRecords[0].staffAssignedTo"));
        }
    }

}
