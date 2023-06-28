package com.maersk.crm.api_step_definitions;

import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.utilities.EventBaseClass;
import io.cucumber.java.en.And;
import org.json.JSONObject;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

public class APITaskManagmentMultiTenancyController extends EventBaseClass {

    @And("I verify {string} is generated for {string} Tenant")
    public void verifyEventIsGenerated(String eventName, String tenantName) throws Exception {
        assertEquals(getListOfEventNames().get(0), eventName, "Event name is miss match");
        assertEquals(getListOfEventNames().size(), 1, "More then 1 event is generated");
        CRM_TaskManagementStepDef.taskId.set(getListOfEventsThreadLocal().get(0).getJSONObject("dataObject").getInt("taskId") + "");
        CRM_TaskManagementStepDef.taskType.set(getListOfEventsThreadLocal().get(0).getJSONObject("dataObject").getInt("taskTypeId") + "");
    }

    @And("I verify {string} is not generated for {string} Tenant with {string}")
    public void verifyEventIsNotGenerated(String eventName, String tenantName, String traceId) throws Exception {
        API_THREAD_LOCAL_FACTORY.getProviderControllerThreadLocal().getAuthenticationToken(tenantName, "CRM");
        i_initiated_event_get_api(traceId);
        i_run_the_event_get_api_and_the_payload1();
        assertEquals(getListOfEventNames().size(), 0, "Event is generated in " + tenantName);
    }

    @And("I verify Task with {string} is saved in task table present in {string} Tenant")
    public void verifyTaskIsSaved(String taskTypeId, String tenantName) throws Exception {
        API_THREAD_LOCAL_FACTORY.getProviderControllerThreadLocal().getAuthenticationToken(tenantName, "CRM");
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().initiateGetTaskByTaskId("getFromUi");
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().getTaskByTaskId();
        assertEquals(new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString()).getInt("taskTypeId") + "", taskTypeId, "Task is not created in " + tenantName);
    }

    @And("I verify Task with {string} is not saved in task table present in {string} Tenant")
    public void verifyTaskIsNotSaved(String taskTypeId, String tenantName) throws Exception {
        API_THREAD_LOCAL_FACTORY.getProviderControllerThreadLocal().getAuthenticationToken(tenantName, "CRM");
        APITaskManagementController.allFieldsValue.set(new ArrayList<>());
        APITaskManagementController.allFieldsValue.get().add(CRM_TaskManagementStepDef.taskType.get());
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().getTaskLisForWorkQueue();
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().provideTaskTypeIdsForCSRRolePermission("");
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().runWorkQueuePostApiToGetTaskList();
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("tasks").isJsonNull() && API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskSearchList").isJsonNull() &&
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("recordCount").isJsonNull(), "Task is created in " + tenantName);
    }

    @And("I verify task with {string} is present in task search for {string}")
    public void verifyAbleToSearchTask(String taskTypeId, String tenantName) throws Exception {
        API_THREAD_LOCAL_FACTORY.getProviderControllerThreadLocal().getAuthenticationToken(tenantName, "CRM");
        API_THREAD_LOCAL_FACTORY.getTaskSearchThreadLocal().initiateTaskSearchPostApi();
        HashMap<String, String> data = new HashMap<>();
        data.put("taskId", CRM_TaskManagementStepDef.taskId.get());
        API_THREAD_LOCAL_FACTORY.getTaskSearchThreadLocal().iCanprovideTaskDetails(data);
        API_THREAD_LOCAL_FACTORY.getTaskSearchThreadLocal().i_run_the_create_inquiry_task_api();
        assertEquals(new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskRecords").get(0).
                getAsJsonObject().toString()).getInt("taskTypeId") + "", taskTypeId, "Not able to Search Task in " + tenantName);
    }

    @And("I verify task with {string} is not present in task search for {string}")
    public void verifyNotAbleToSearchTask(String taskTypeId, String tenantName) throws Exception {
        API_THREAD_LOCAL_FACTORY.getProviderControllerThreadLocal().getAuthenticationToken(tenantName, "CRM");
        API_THREAD_LOCAL_FACTORY.getTaskSearchThreadLocal().initiateTaskSearchPostApi();
        HashMap<String, String> data = new HashMap<>();
        data.put("taskTypeId", taskTypeId);
        API_THREAD_LOCAL_FACTORY.getTaskSearchThreadLocal().iCanprovideTaskDetails(data);
        API_THREAD_LOCAL_FACTORY.getTaskSearchThreadLocal().i_run_the_create_inquiry_task_api();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskRecords").size());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskRecords").toString());
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskRecords").size() == 0, "Able to search Task in " + tenantName);
    }

    @And("I verify {string} is generated for {string} Tenant for SR with {string}")
    public void verifySREventIsGenerated(String eventName, String tenantName, String taskTypeId) throws Exception {
        boolean taskTypeMatched = false;
        for (int i = 0; i < getListOfEventsThreadLocal().size(); i++) {
            if (getListOfEventNames().get(i).equalsIgnoreCase("TASK_SAVE_EVENT") || getListOfEventNames().get(i).equalsIgnoreCase("TASK_UPDATE_EVENT")) {
                JSONObject temp = getListOfEventsThreadLocal().get(i).getJSONObject("dataObject");
                if (temp.get("taskTypeId").toString().equals(taskTypeId)) {
                    CRM_TaskManagementStepDef.taskId.set(temp.getInt("taskId") + "");
                    taskTypeMatched = true;
                    break;
                }
            }
        }
        assertTrue(taskTypeMatched, "Event is not generated for " + tenantName);
    }
}
