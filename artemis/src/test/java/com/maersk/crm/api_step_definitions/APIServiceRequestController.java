package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.step_definitions.CRM_GeneralTaskStepDef;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.step_definitions.LoginStepDef;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.World;
import com.maersk.dms.step_definitions.InboundDocumentTaskStepDefs;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class APIServiceRequestController extends CRMUtilities implements ApiBase {

    private String taskManagementBaseURI = ConfigurationReader.getProperty("apiTaskManagementURI");
    private String externalSr = "/mars/servicerequest/external/save";
    private String bpmInstanceId = "mars/servicerequest/bpm/{srId}?key={processName}";

    public static final ThreadLocal<ArrayList<String>> allFieldsValue = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<String> traceId = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> traceId1 = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> taskInfo = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> taskTriggerDate= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> requestParams =ThreadLocal.withInitial(JsonObject::new);

    private CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    private InboundDocumentTaskStepDefs inboundDocumentTaskStepDefs = new InboundDocumentTaskStepDefs();
    private APICreateConsumerContactController apiCreateConsumerContactController = new APICreateConsumerContactController();


    @When("I will provide required information to create external sr with {string} {string} {string} {string} {string}")
    public void iWillProvideInfoToCreateExternalTask(String caseId, String consumerId, String inboundId, String taskInfo, String triggerDate, List<String> additionalFlds) {
        allFieldsValue.set(new ArrayList<>(additionalFlds));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/externalServiceRequest.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId",
                Integer.parseInt(APITaskManagementController.taskTypeId.get()));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo());

        traceId1.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", traceId1.get());

        this.taskInfo.set(null);
        if (!taskInfo.isEmpty()) {
            this.taskInfo.set(taskInfo);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskInfo", taskInfo);
        }

        if (!inboundId.isEmpty()) {
            CRM_GeneralTaskStepDef.taskValues.get().put("inboundId", inboundId);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(0).getAsJsonObject()
                    .addProperty("externalRefId", inboundId);
        }

        if (!caseId.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(1).getAsJsonObject()
                    .addProperty("externalRefId", caseId);
        }

        if (!consumerId.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(2).getAsJsonObject()
                    .addProperty("externalRefId", consumerId);
        }

        if (allFieldsValue.get().size() >= 1) {
            for (int i = 0; i < allFieldsValue.get().size(); i++) {
                JsonObject temp = new JsonObject();
                temp.add("selectionNumeric", null);
                temp.add("selectionBoolean", null);
                temp.add("selectionDate", null);
                temp.add("selectionDateTime", null);
                temp.add("fieldGroup", null);
                temp.addProperty("selectionFieldName", allFieldsValue.get().get(i));
                i++;
                if(allFieldsValue.get().size()==1){
                    temp.add("selectionVarchar", null);
                }
                else{
                    temp.addProperty("selectionVarchar", allFieldsValue.get().get(i));
                }
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").add(temp);
            }
        }

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    //Vidya-- Method to initiate put External request
    @And("I initiated external create sr api")
    public void initiateExternalCreateSRApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(externalSr);
    }

    @And("I run the create external sr API and check the status code is {string}")
    public void i_run_the_create_external_task_api(String statusCode) throws Exception {
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode+"", statusCode);
        if(statusCode.equals("200"))
            APITaskManagementController.taskId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskId").getAsInt());
        CRM_TaskManagementStepDef.taskId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskId").getAsInt()+"");
        traceId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
    }

    @And("I run external createORupdate sr api call with data")
    public void iRunExternalCreateUpdateSrApiCallWithData( Map<String, String> dataTable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/externalTask.json");
        String json1 = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        HashMap<String, String> newData = new HashMap<>();
        newData.putAll(dataTable);
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "externalLinks[0].externalRefType":
                    if(dataTable.get("externalLinks[0].externalRefType").equalsIgnoreCase("CASE")){  //when linking to case only
                        newData.put("externalLinks[0].externalRefId", "int::" + apiCreateConsumerContactController.getCaseId());
                    }else {
                        newData.put("externalLinks[0].externalRefId", "int::" + apiCreateConsumerContactController.getExternalConsumerId()); //Consumer
                    }
                    break;
                case "externalLinks[1].externalRefType":  //CASE
                    newData.put("externalLinks[1].externalRefId", "int::" + apiCreateConsumerContactController.getCaseId());
                    break;
                case "taskTriggerDate":
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar date = Calendar.getInstance();
                    date.add(Calendar.DATE, +30);
                    taskTriggerDate.set(dateFormat.format(date.getTime())); //30 days from today
                    newData.put("taskTriggerDate", taskTriggerDate.get());
                    break;
                case "taskTypeId":
                    newData.put("taskTypeId", dataTable.get(data));
                    break;
                case "createdBy":
                    newData.put("createdBy", dataTable.get(data));
                    break;
                case "correlationId":
                    newData.put("correlationId", dataTable.get(data));
                    break;
                case "taskId":
                    newData.put("taskId", "int::" + World.generalSave.get().get("TASKID").toString());
                    newData.put("taskDetails[0].taskId", "int::" + World.generalSave.get().get("TASKID").toString());
                    newData.put("taskDetails[0].taskDetailsId", "int::" + inboundDocumentTaskStepDefs.getTaskDetailsIdByTaskId(World.generalSave.get().get("TASKID").toString()));
                    break;
            }
        }

        initiateExternalCreateSRApi();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json1, newData));

        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< External create SR API is FAIL >>");
        CRM_TaskManagementStepDef.srID.set("" + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.taskId"));
        World.generalSave.get().put("TASKID", "" + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.taskId"));
        System.out.println("taskid : " + "" + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.taskId"));
    }

    @Then("I verify Task table information in response of external sr")
    public void verifyTaskTableInformationInResponseExternalTask() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        assertEquals(temp.getInt("taskId"), APITaskManagementController.taskId.get(), "TaskId is miss match");
        assertEquals(temp.getInt("taskTypeId") + "", APITaskManagementController.taskTypeId.get(), "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", APITaskManagementController.taskPriority.get(), "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Open", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", APITaskManagementController.dueInDays.get(), "dueInDays is miss match");
        assertEquals(temp.getString("source"), "System", "source is miss match");
        System.out.println("Vidya " + taskInfo.get() + "    " + temp.get("taskInfo").toString());
        if (taskInfo.get() == null || taskInfo.get().isEmpty() || taskInfo.get().equals("")) {
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        } else {
            assertEquals(temp.getString("taskInfo"), taskInfo.get(), "taskInfo is null");
        }
        //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
        Assert.assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), traceId1.get(), "correlationId is miss match");
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertTrue(temp.isNull("action"), "action is not null");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I verify Task history table information in response of external sr")
    public void verifyTaskHistoryTableInformationInResponseExternalTask() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskHistoryVOs").get(0).getAsJsonObject().toString());
        assertEquals(temp.getInt("taskId"), APITaskManagementController.taskId, "TaskId is miss match");
        assertTrue(temp.getInt("taskHistoryId") != 0 || !temp.isNull("taskHistoryId"),
                "taskHistoryId is null or equal 0");
        assertEquals(temp.getInt("taskTypeId") + "", APITaskManagementController.taskTypeId.get(), "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", APITaskManagementController.taskPriority.get(), "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Open", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", APITaskManagementController.dueInDays.get(), "dueInDays is miss match");
        assertEquals(temp.getString("source"), "System", "source is miss match");
        if (taskInfo.get() == null) {
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        } else {
            assertEquals(temp.getString("taskInfo"), taskInfo.get(), "taskInfo is null");
        }
        //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
        assertEquals(temp.getString("correlationId"), traceId1.get(), "correlationId is miss match");
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertEquals(temp.getString("action"), "INSERT", "action is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("actionedOn")),
                "actionedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I verify Task Details and Task Details History table information in response of external sr")
    public void verify3informationInTaskDetailsAndHistoryDB() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("taskDetails");

        JsonArray json1 = APITaskManagementController.taskHisCall.get();

        assertTrue(json.size() != 0, "Task Details Array is empty");
        assertTrue(json.size() == json1.size(), "Task Details and Task Details History Table array size is mismatch");

        int j = 0;
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp2 = new JSONObject(json1.get(i).toString());

            assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp1.getInt("taskId"), APITaskManagementController.taskId, "TaskId is miss match");
            assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertEquals(temp1.getString("selectionFieldName"), allFieldsValue.get().get(j++),
                    "selectionFieldName is value is mismatch");
            if (allFieldsValue.get().size() == 1) {
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                j--;
            }else{
                assertEquals(temp1.getString("selectionVarchar"), allFieldsValue.get().get(j--),
                        "selectionVarchar is value is mismatch");
            }
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertEquals(temp1.getString("correlationId"), traceId1.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp1.isNull("uiid"), "uiid is not null have some value");

            assertTrue(temp2.getInt("taskDetailHistoryId") != 0 && !temp2.isNull("taskDetailHistoryId"),
                    "taskDetailHistoryId is null or equal to 0");
            assertTrue(temp2.getInt("taskDetailsId") != 0 && !temp2.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp2.getInt("taskId"), APITaskManagementController.taskId, "TaskId is miss match");
            assertTrue(temp2.getInt("taskFieldId") != 0 && !temp2.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertEquals(temp2.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp2.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp2.getString("selectionFieldName"), allFieldsValue.get().get(j++),
                    "selectionFieldName is value is mismatch");
            if(allFieldsValue.get().size()==1){
                assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar is not null");
            }else{
                assertEquals(temp2.getString("selectionVarchar"), allFieldsValue.get().get(j++),
                        "selectionVarchar is value is mismatch");
            }
            assertTrue(temp2.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp2.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp2.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp2.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(temp2.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertEquals(temp2.getString("correlationId"), traceId1.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp2.isNull("uiid"), "uiid is not null have some value");
        }
    }

    @Then("I verify External Link table information in response of external sr has {string} {string} {string} {string}")
    public void verifyTInformationInExternalLinkTable(String caseId, String consumerId, String inboundId, String taskId) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonArray("taskLinksVOS");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            assertTrue(temp.getInt("taskLinkId") != 0 && !temp.isNull("taskLinkId"),
                    "taskLinkId is null or equal to 0");
            assertEquals(temp.getInt("internalRefId"), APITaskManagementController.taskId, "internalRefId is miss match");
            assertEquals(temp.getString("internalRefType"), "SERVICE_REQUEST", "internalRefType is miss match");
            switch (temp.getString("externalLinkRefType")){
                case "INBOUND_CORRESPONDENCE":
                    assertEquals(temp.getInt("externalLinkRefId") + "", inboundId, "externalLinkRefId is miss match");
                    inboundId="";
                    break;
                case "CASE":
                    assertEquals(temp.getInt("externalLinkRefId") + "", caseId, "externalLinkRefId is miss match");
                    caseId="";
                    break;
                case "CONSUMER":
                    assertEquals(temp.getInt("externalLinkRefId") + "", consumerId, "externalLinkRefId is miss match");
                    consumerId="";
                    break;
                case "TASK":
                    assertEquals(temp.getInt("externalLinkRefId"), (APITaskManagementController.taskId.get()+1), "externalLinkRefId is miss match");
                    taskId="";
                    break;
                default:
                    assertTrue(false,"Switch case not match for "+temp.getString("externalLinkRefType"));
                    break;
            }

            assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                    "effectiveStartDate date time field IS NOT valid");
            assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
            assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                    "createdOn date time field IS NOT valid");
            assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp.getString("correlationId"), traceId1.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp.isNull("uiid"), "uiid is not null have some value");
        }
        assertTrue(inboundId.isEmpty() && caseId.isEmpty() && consumerId.isEmpty() && taskId.isEmpty(),"All link events are not returned");
    }

    @Given("I initiated bpm process get api by sr id for process name {string}")
    public void initiateBmpProcessGetAPIBySRId(String ProcessName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        bpmInstanceId = bpmInstanceId.replace("{srId}", APITaskManagementController.taskId+"").replace("{processName}",ProcessName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(bpmInstanceId);
    }

    @When("I run bpm process get api by sr id")
    public void getBpmProcessGetApiBySrId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I will check response of bpm process get api has all the data with bpm instance id")
    public void verifyResponseOfBPMProcessGetApi() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("serviceRequestId").toString(),
                APITaskManagementController.taskId+"", "Service Request Id is not correct");
        assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("bpmInstanceId").toString().isEmpty(),
                "bpmInstanceId Id is null");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId,
                "createdBy Id is not correct");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId,
                "updatedBy Id is not correct");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdOn").toString()),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedOn").toString()),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I will check bpm process is not started")
    public void verifyBpmProcessIsNotStarted() {
        JSONObject temp=new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());
        assertTrue(temp.isNull("serviceRequestId"), "Service Request Id is not null");
        assertTrue(temp.isNull("bpmInstanceId"), "bpmInstanceId Id is not null");
        assertTrue(temp.isNull("createdBy"), "createdBy is not null");
        assertTrue(temp.isNull("updatedBy"), "updatedBy is not null");
        assertTrue(temp.isNull("createdOn"), "createdOn is not null");
        assertTrue(temp.isNull("updatedOn"), "updatedOn is not null");
    }

    @Given("I initiated bpm process get api by service request id for process name {string}")
    public void initiateBmpProcessGetAPIByServiceRequestId(String ProcessName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        bpmInstanceId = bpmInstanceId.replace("{srId}", CRM_TaskManagementStepDef.srID.get()).replace("{processName}",ProcessName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(bpmInstanceId);
    }

    @Then("I will check response of service request bpm process")
    public void verifyResponseOfServiceRequestBPMProcess() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("serviceRequestId").toString(), CRM_TaskManagementStepDef.srID.get()+"", "Service Request Id is not correct");
        assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("bpmInstanceId").toString().isEmpty(), "bpmInstanceId Id is null");
        if(myTask.lblCreatedBy.getText().trim().equalsIgnoreCase("ECMS Service")){
            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString().isEmpty(), "createdBy Id is not correct");
            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString().isEmpty(), "updatedBy Id is not correct");
        }else if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("created_by") && CRM_GeneralTaskStepDef.taskValues.get().get("created_by").equalsIgnoreCase("Service AccountTwo")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString(), "3773", "createdBy Id is not correct");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString(), "3773", "updatedBy Id is not correct");
        }else if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("created_by") && CRM_GeneralTaskStepDef.taskValues.get().get("created_by").equalsIgnoreCase("Service AccountOne")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString(), "8369", "createdBy Id is not correct");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString(), "8369", "updatedBy Id is not correct");
        }else{
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy Id is not correct");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy Id is not correct");
        }
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdOn").toString()), "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedOn").toString()), "updatedOn date time field IS NOT valid");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("state").toString(), "Active", "state is not correct");
    }
    @Then("I verify all tokens should be terminated in the process")
    public void verifyalltokensshouldbeterminated() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("serviceRequestId").toString(), CRM_TaskManagementStepDef.srID.get() + "", "Service Request Id is not correct");
        assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("bpmInstanceId").toString().isEmpty(), "bpmInstanceId Id is null");
        if (!CRM_GeneralTaskStepDef.taskValues.get().containsKey("created_by")) {
            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString().isEmpty(), "createdBy Id is not correct");
            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString().isEmpty(), "updatedBy Id is not correct");
        }else if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("created_by") && CRM_GeneralTaskStepDef.taskValues.get().get("created_by").equalsIgnoreCase("Service AccountTwo")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString(), "3773", "createdBy Id is not correct");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString(), "3773", "updatedBy Id is not correct");
        }else if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("created_by") && CRM_GeneralTaskStepDef.taskValues.get().get("created_by").equalsIgnoreCase("Service TesterTwo") && LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString(), "3652", "createdBy Id is not correct");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString(), "3652", "updatedBy Id is not correct");
        }else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy Id is not correct");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy Id is not correct");
        }
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdOn").toString()), "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedOn").toString()), "updatedOn date time field IS NOT valid");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("state").toString(), "COMPLETED", "state is not correct");
    }
}
