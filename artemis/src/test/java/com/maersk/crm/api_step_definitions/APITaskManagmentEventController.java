package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.maersk.crm.pages.crm.CRMSRViewEditPage;
import com.maersk.crm.step_definitions.CRM_GeneralTaskStepDef;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.step_definitions.CRM_TaskNoteComponentStepDef;
import com.maersk.crm.utilities.EventBaseClass;
import com.maersk.crm.utilities.World;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class APITaskManagmentEventController extends EventBaseClass {
    CRMSRViewEditPage srViewEditPage = new CRMSRViewEditPage();
    APITaskManagementController apiTaskManagementController = new APITaskManagementController();

    final ThreadLocal<List<String>> tasks = ThreadLocal.withInitial(() -> Collections.unmodifiableList(
            Arrays.asList("JC Outbound Call", "Just Cause Follow-up Email",
                    "Just Cause Resolution", "Just Cause State Discussion",
                    "Just Cause Decision Letter", "Review Appeals Form",
                    "Just Cause Request", "QA Review Complaint",
                    "Supervisor Review Complaint", "State Escalated Complaint",
                    "Review Appeal Form", "GCNJ Appeals Acknowledgement Letter",
                    "NJ SBE Appeal Form", "Generate IDR Successful Letter","Support Level 1")));

    @And("I initiate Event get api for trace id {string}")
    public void initiateEventGETAPI(String traceID) throws Exception {
        i_initiated_event_get_api1(traceID);
    }

    @And("I Initiate Event Get Api for trace_id {string}")
    public void initiateEventGETAPI1(String traceID) throws Exception {
        i_initiated_event_get_api(traceID);
    }

    @And("I will run the Event GET API and get the payload")
    public void getEventPayload() throws Exception {
        listOfEventsThreadLocal.set(i_run_the_event_get_api_and_the_payload1());
    }

    @Then("I verify TASK_SAVE_EVENT for SR has all the proper data")
    public void verifyTaskSaveEventForSR() throws Exception {
        boolean flag = false;
        for (int i = 0; i < getListOfEventsThreadLocal().size(); i++) {
            if (getListOfEventNames().get(i).equalsIgnoreCase("TASK_SAVE_EVENT")) {
                JSONObject temp = getListOfEventsThreadLocal().get(i).getJSONObject("dataObject");
                int taskTypeId = temp.getInt("taskTypeId");
                boolean taskTypeMatched = Arrays.asList(15180, 13475, 15350, 15414, 13495, 15410, 15349, 16044, 16277, 16402).contains(taskTypeId);
                if (taskTypeMatched) {
                    eventId = getListOfEventIds().get(i);
                    flag = true;
                    payloadObject = getListOfEventsThreadLocal().get(i);
                    assertFalse(getListOfEventsThreadLocal().get(i).isNull("dataObject"));
                    assertEquals(getListOfEventsThreadLocal().get(i).getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a BLCRM");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("action").toLowerCase(), "create", "Action is not a Update");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("recordType").toLowerCase(), "taskvo", "Record type is not a Task");
                    assertTrue(EventsUtilities.isValidDate(getListOfEventsThreadLocal().get(i).getString("eventCreatedOn")), "eventCreatedOn date time field IS NOT valid");
                    assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"), "taskId is null or equal to 0");
                    assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"), "taskTypeId is null or equal to 0");
                    assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
                    verifyServiceRequestInd(taskTypeId, temp);
                        // need a refactoring here
                    if (temp.getInt("taskTypeId") == 15410) {
                        assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
                        assertEquals(temp.getString("defaultDueDate"), API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 7, 0, 0), "defaultDueDate is miss match");
                        assertEquals(temp.getInt("dueInDays"), 7, "dueInDays is miss match");
                    } else if (temp.getInt("taskTypeId") == 15349) {
                        assertEquals(temp.getInt("defaultPriority"), 1, "defaultPriority is miss match");
                        assertEquals(temp.getString("defaultDueDate"), getDueDate("localDate", 3, "IN-EB"), "defaultDueDate is miss match");
                        assertEquals(temp.getInt("dueInDays"), 3, "dueInDays is miss match");
                    } else if (temp.getInt("taskTypeId") == 16277) {
                        assertEquals(temp.getInt("defaultPriority"), 2, "defaultPriority is miss match");
                        assertEquals(temp.getString("defaultDueDate"), API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 31, 0, 0), "defaultDueDate is miss match");
                        assertEquals(temp.getInt("dueInDays"), 31, "dueInDays is miss match");
                    }
                    else if (temp.getInt("taskTypeId") == 16402) {
                        assertEquals(temp.getInt("defaultPriority"), 2, "defaultPriority is miss match");
                        assertEquals(temp.getInt("dueInDays"), 9, "dueInDays is miss match");
                    }

                    else {
                        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("priority") && CRM_GeneralTaskStepDef.taskValues.get().get("priority") != null)
                            assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
                        else
                            assertTrue(temp.getInt("defaultPriority") != 0 && !temp.isNull("defaultPriority"), "defaultPriority is null or equal to 0");
                        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("dueDate") && CRM_GeneralTaskStepDef.taskValues.get().get("dueDate") != null)
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")), "defaultDueDate is miss match");
                        else
                            assertTrue(!temp.getString("defaultDueDate").isEmpty() && !temp.isNull("defaultDueDate"), "defaultDueDate is null or equal to 0");
                        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("dueIn") && CRM_GeneralTaskStepDef.taskValues.get().get("dueIn") != null)
                            assertEquals(temp.getInt("dueInDays") + "", CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").split(" ")[0], "dueInDays is miss match");
                        else
                            assertTrue(temp.getInt("dueInDays") != 0 && !temp.isNull("dueInDays"), "dueInDays is null or equal to 0");
                    }
                    assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status") != null)
                        assertEquals(temp.getString("taskStatus"), CRM_GeneralTaskStepDef.taskValues.get().get("status"), "taskStatus is miss match");
                    else
                        assertEquals(temp.getString("taskStatus"), "Open", "taskStatus is miss match");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
                    assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("assignee") == null || CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("-- --") || CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty()) {
                        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
                    } else {
                        assertEquals(temp.getInt("staffAssignedTo") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
                    }
                    assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
                    assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
                    assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
                    assertTrue(temp.isNull("dueIn"), "dueIn is not null");
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("dueIn") && CRM_GeneralTaskStepDef.taskValues.get().get("dueIn") != null)
                        assertEquals(temp.getInt("dueInDays") + "", CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").split(" ")[0], "dueInDays is miss match");
                    else
                        assertTrue(temp.getInt("dueInDays") != 0 && !temp.isNull("dueInDays"), "dueInDays is null or equal to 0");
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status") != null)
                        assertEquals(temp.getString("source"), "User", "source is miss match");
                    else
                        assertEquals(temp.getString("source"), "System", "source is miss match");
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskInfo") && !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("-- --") &&
                            !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals(""))
                        assertEquals(temp.getString("taskInfo"), CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"), "taskInfo is miss match");
                    else
                        assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
                    //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
                    assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
                    assertTrue(temp.isNull("editReason"), "editReason is not null");
                    assertTrue(temp.isNull("holdReason"), "holdReason is not null");
                    assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
                    assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status") != null)
                        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
                    else
                        assertFalse(temp.getString("createdBy").isEmpty(), "createdBy is null or empty");

                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status") != null)
                        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
                    else
                        assertFalse(temp.getString("updatedBy").isEmpty(), "updatedBy is null or empty");
                    assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
                    assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
                    assertEquals(temp.getString("action"), "INSERT", "action is miss match");
                    assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
                    assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"), "createdOn and updatedOn is miss match");
                    break;
                }
            }
        }
        assertTrue(flag, "TASK_SAVE_EVENT is not generated");
    }

    @And("I will check {string} is mapping with {string} Subscriber ID")
    public void checkEventIsSubscribedWithDPBI(String eventName, String subscriberName) throws Exception {
        i_initiated_Subscribers_post_api();
        i_can_search_project_by(subscriberName);
        i_run_the_Subscribers_post_api();
        verifyResponseHasEventNameAndMappingId(eventName);
    }

    @And("I will verify {string} publish to DPBI")
    public void getEventPayload(String eventName) throws Exception {
        initiatedSubscribersRecordGETAPI();
        runTheSubscribersRecordGETAPI();
        verifyResponse(eventName);
    }

    @Then("I verify task details information in task save event for coverVA Task or SR")
    public void verifyTaskDetailsInformationInTaskSaveEvent() {
        JSONArray json = payloadObject.getJSONObject("dataObject").getJSONArray("taskDetails");
        JSONObject temp1 = payloadObject.getJSONObject("dataObject");
        boolean flag = false;
        for (int i = 0; i < json.length(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            String field = temp.getString("selectionFieldName").toLowerCase();
            flag = true;
            switch (field) {
                case "external consumer id":
                    assertEquals(temp.getInt("selectionNumeric") + "", CRM_GeneralTaskStepDef.taskValues.get().get("externalConsumerID"), "selectionNumeric value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "external case id":
                    assertEquals(temp.getInt("selectionNumeric") + "", CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId"), "selectionNumeric value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "preferred language":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("language").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "information type":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("informationType").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "action taken":
                    String actionTaken = "";
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("actionTaken") && CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").contains("(") && !(temp1.getInt("taskTypeId") == 13368)) {
                        actionTaken = CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").replace("(", "").replace(")", "");
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), actionTaken.toUpperCase(), "selectionVarchar value is miss match");
                    } else if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("actionTaken") && !(temp1.getInt("taskTypeId") == 13368) && !(temp1.getInt("taskTypeId") == 15352))
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").toUpperCase(), "selectionVarchar value is miss match");
                    else
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date translation escalated":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationEscalated")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date translation received":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationReceived")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date translation mailed":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationMailed")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "disposition":
                    boolean taskTypeMatched = Arrays.asList(13511, 13368, 15352, 15573, 15574, 13367).contains(temp1.getInt("taskTypeId"));
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("disposition") && !taskTypeMatched)
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("disposition").toUpperCase(), "selectionVarchar value is miss match");
                    else
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "external application id":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("externalApplicationId"))
                        assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"), "selectionVarchar value is miss match");
                    else
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "connectionpoint contact record id":
                    assertEquals(temp.getInt("selectionNumeric") + "", CRM_GeneralTaskStepDef.taskValues.get().get("cpCRID"), "selectionNumeric value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "connectionpoint sr id":
                    assertEquals(temp.getInt("selectionNumeric") + "", CRM_GeneralTaskStepDef.taskValues.get().get("cpSRID"), "selectionNumeric value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "connectionpoint task id":
                    assertEquals(temp.getInt("selectionNumeric") + "", CRM_GeneralTaskStepDef.taskValues.get().get("cpTaskID"), "selectionNumeric value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "document due date":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("documentDueDate"))
                        assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("documentDueDate")), "selectionDate value is miss match");
                    else
                        assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "contact reason":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " ").toUpperCase(), CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "outcome":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " ").toUpperCase(), CRM_GeneralTaskStepDef.taskValues.get().get("outcome").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "application type":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("applicationType")) {
                        if (CRM_GeneralTaskStepDef.taskValues.get().get("applicationType").equalsIgnoreCase("Ex Parte Renewal - CVIU")) {
                            assertEquals(temp.getString("selectionVarchar").replaceAll("_", " ").replaceAll("-", " "), "EX PARTE CVIU", "selectionVarchar value is miss match");
                        } else {
                            assertEquals(temp.getString("selectionVarchar").replaceAll("_", " ").replaceAll("-", " "), CRM_GeneralTaskStepDef.taskValues.get().get("applicationType").replace("- ", "")
                                    .toUpperCase(), "selectionVarchar value is miss match");
                        }
                    } else {
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "program":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("program").replace("- ", "")
                            .toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "inbound correspondence type":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("InbDocType"))
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("InbDocType").replace("- ", "")
                                .toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "remand reason":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("remandReason").toUpperCase()
                                    .replace("- ", ""),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "eligibility decision":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("eligibilityDecision").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "remand completion date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("remandCompletionDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "remand due date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("remandDueDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "received date":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("receivedDate"))
                        assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("receivedDate")), "selectionDate value is miss match");
                    else
                        assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "locality":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("locality"))
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("locality").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "request type":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("requestType").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "issue type":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("issueType").contains("CPU Error"))
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("issueType").replace(" (CPU Error)", "").toUpperCase(), "selectionVarchar value is miss match");
                    else
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("issueType").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "case worker first name":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("caseWorkerFirstName"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "CASE_WORKER", "fieldGroup value is invalid");
                    break;

                case "case worker last name":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("caseWorkerLastName"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "CASE_WORKER", "fieldGroup value is invalid");
                    break;

                case "complaint type":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("complaintType").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date resent":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateResent")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "returned mail reason":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("returnedMailReason").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "business unit":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("businessUnit").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "appointment date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("appointmentDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "accessibility needed":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("accessibilityNeeded").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "appointment time":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("appointmentTime"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "provider first name":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("providerFirstName"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "PROVIDER", "fieldGroup value is invalid");
                    break;

                case "provider last name":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("providerLastName"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "PROVIDER", "fieldGroup value is invalid");
                    break;

                case "provider phone":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("providerPhone"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "provider email":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("providerEmail"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "facility name":
                    if (!CRM_GeneralTaskStepDef.taskValues.get().containsKey("facilityName")) {
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    } else
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("facilityName").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "facility type":
                    if (!CRM_GeneralTaskStepDef.taskValues.get().containsKey("facilityType")) {
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    } else
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("facilityType").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "from name":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("contactName"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "plan name":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("planName").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "plan change reason":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("planChangeReason").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date health plan contacted":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateHealthPlanContacted")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date follow-up email sent":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateFollowupEmailSent")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date state notified":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateSateNotified")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date decision letter sent":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateDecisionLetterSent")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "preferred call back date":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("preferredCallBackDate"))
                        assertEquals(temp.get("selectionDate"), (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("preferredCallBackDate"))), "selectionDate value is miss match");
                    else
                        assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "preferred call back time":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("preferredCallBackTime"))
                        assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("preferredCallBackTime"), "selectionVarchar value is miss match");
                    else
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "preferred phone":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("preferredPhone"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "name":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("name")) {
                        assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("name"), "selectionVarchar value is miss match");
                    } else
                        assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("memberName"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date grievance received":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateReceivedGrievance")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "requested new plan":
                    assertEquals(String.valueOf(temp.getBoolean("selectionBoolean")), CRM_GeneralTaskStepDef.taskValues.get().get("requestedNewPlan"), "selectionBoolean value is miss match");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "invalid":
                    assertEquals(String.valueOf(temp.getBoolean("selectionBoolean")), CRM_GeneralTaskStepDef.taskValues.get().get("invalid"), "selectionBoolean value is miss match");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "rid":
                    assertEquals(temp.getInt("selectionNumeric") + "", CRM_GeneralTaskStepDef.taskValues.get().get("rid"), "selectionNumeric value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "action taken single":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("actionTakenSingle")) {
                        actionTaken = CRM_GeneralTaskStepDef.taskValues.get().get("actionTakenSingle").replace("(", "").replace(")", "");
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), actionTaken.toUpperCase(), "selectionVarchar value is miss match");
                    } else
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "Caller Name":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("callerName"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "Preferred Phone":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("preferredPhone"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "Other":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("other"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;


                case "Invalid Reason":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("Invalid Reason"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "complaint About":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("complaintAbout"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "incident Date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("incidentDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "CSR Name":
                    assertEquals(temp.getString("selectionDate").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("csrName"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "escalated":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("escalated") != null)
                        assertEquals(String.valueOf(temp.getBoolean("selectionBoolean")), CRM_GeneralTaskStepDef.taskValues.get().get("escalated"), "selectionBoolean value is miss match");
                    else
                        assertFalse(temp.getBoolean("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "Escalation Reason":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("escalationReason"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "Organization":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("Organization"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "program required":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("programRequired"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "current plan":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("currentPlan").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "desired plan":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("desiredPlanJC").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "reason":
                    String reason = "";
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonExplanation")) {
                        reason = CRM_GeneralTaskStepDef.taskValues.get().get("reasonExplanation").replace("(", "").replace(")", "");
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), reason.toUpperCase(), "selectionVarchar value is miss match");
                        assertFalse(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    } else {
                        assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                        assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    break;
                case "explanation":
                    assertEquals(temp.getString("selectionVarchar").toLowerCase(), CRM_GeneralTaskStepDef.taskValues.get().get("explanation").toLowerCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertFalse(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "consumer satisfied?":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("consumerSatisfied").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "missing info required?":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("missingInfoRequired").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "decision":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("decision").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "email":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("AREmail"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "new plan start date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("newPlanStartDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "final decision":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("finalDecision").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Denial Reason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("denialReason")) {
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("denialReason").toUpperCase(), "selectionVarchar value is miss match");
                    } else
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("denialReasonSingle").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Application Received Date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("applicationReceivedDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Renewal Processing Due Date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("renewalProcessingDueDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Renewal Date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("renewalDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Renewal Cutoff Date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("renewalCutoffDate")), "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Application Status":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("applicationStatus").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "No Of Approved Applicants":
                    assertEquals(temp.getString("selectionDate"), CRM_GeneralTaskStepDef.taskValues.get().get("noOfApprovedApplicants"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Channel":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("channel").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Decision Source":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("decisionSource").toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "No Of Applicants in Household":
                    assertEquals(temp.getString("selectionDate"), CRM_GeneralTaskStepDef.taskValues.get().get("noOfApplicantsInHousehold"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Application Signature Date":
                    assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("applicationSignatureDate")), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "HPE?":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("hpe"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "Closed Renewal":
                    assertEquals(temp.getString("selectionVarchar"), CRM_GeneralTaskStepDef.taskValues.get().get("closedRenewal"), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
            }

            assertEquals(temp.getInt("taskId"), payloadObject.getJSONObject("dataObject").getInt("taskId"), "TaskId is miss match");
            assertTrue(temp.getInt("taskDetailsId") != 0 || !temp.isNull("taskDetailsId"), "taskDetailsId is null or equal 0");
            assertTrue(temp.getInt("taskFieldId") != 0 || !temp.isNull("taskFieldId"), "taskFieldId is null or equal 0");
           /* if(CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status") != null &&
                    !CRM_GeneralTaskStepDef.taskValues.get().get("status").equalsIgnoreCase("Closed"))
                assertEquals(temp.getString("createdBy"), api.userId, "createdBy is miss match");
            else if(CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status").equalsIgnoreCase("Closed"))
                assertFalse(temp.isNull("createdBy"),"Created by is null");
            else
                assertFalse(temp.getString("createdBy").isEmpty(), "createdBy is null or empty");
            if(CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status") != null &&
                    !temp.getString("selectionFieldName").equalsIgnoreCase("Expedited"))
                assertEquals(temp.getString("updatedBy"), api.userId, "updatedBy is miss match");
            else if(temp.getString("selectionFieldName").equalsIgnoreCase("Expedited"))
                assertFalse(temp.isNull("updatedBy"),"Updated by is null");
            else
                assertFalse(temp.getString("updatedBy").isEmpty(), "updatedBy is null or empty");*/
            assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
            assertTrue(!temp.getString("correlationId").isEmpty() || !temp.isNull("correlationId"), "correlationId is null or empty string");
        }
        assertTrue(flag, "Task Details are not present");
    }

    @Then("I verify TASK_SAVE_EVENT for TASK has all the proper data")
    public void verifyTaskSaveEvent() {
        payloadObject = getListOfEventsThreadLocal().get(0);
        eventId = getListOfEventIds().get(0);
        assertFalse(payloadObject.isNull("dataObject"));
        assertEquals(payloadObject.getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a BLCRM");

        assertEquals(payloadObject.getString("action").toLowerCase(), "create", "Action is not a Update");
        assertEquals(payloadObject.getString("recordType").toLowerCase(), "taskvo", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(payloadObject.getString("eventCreatedOn")), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.getJSONObject("dataObject");

        assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"), "taskId is null or equal to 0");
        CRM_TaskManagementStepDef.taskId.set(String.valueOf(temp.getInt("taskId")));
        System.out.println("Task id from task save event: " + CRM_TaskManagementStepDef.taskId.get());
        assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"), "taskTypeId is null or equal to 0");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
        assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), CRM_GeneralTaskStepDef.taskValues.get().get("status"), "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("assignee") == null || CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("-- --") || CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty())
            assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        else
            assertEquals(temp.getInt("staffAssignedTo") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").split(" ")[0], "dueInDays is miss match");
        assertEquals(temp.getString("source"), "User", "source is miss match");
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskInfo") && !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("-- --") &&
                !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals(""))
            assertEquals(temp.getString("taskInfo"), CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"), "taskInfo is miss match");
        else
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        /*
        if(CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote") && !CRM_GeneralTaskStepDef.taskValues.get().get("taskNote").equals("-- --"))
            assertEquals(temp.getString("taskNotes"), CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"), "taskNotes is miss match");
        else
            assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
            */
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
        assertEquals(temp.getString("action"), "INSERT", "action is miss match");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"), "createdOn and updatedOn is miss match");
        verifyServiceRequestInd(temp.getInt("taskTypeId"), temp);
    }

    @Then("I verify link events are generated for a {string} from {string}")
    public void i_verify_link_events_are_generated_for_a_sr_from(String type, String contactType) {
        boolean srToCR = false, consumerToSR = false, srToConsumer = false, crToSR = false,
                taskToSR = false, srToTask = false, taskToConsumer = false, consumerToTask = false,
                caseToSR = false, srToCase = false, taskToCr = false, crToTask = false, taskToCase = false, caseToTask = false,
                inboundToSR = false, srToInbound = false, inboundToTask = false;
        List<String> expectedLinksObjects = new ArrayList<>(Collections.emptyList());
        if (contactType.contains(",")) {
            expectedLinksObjects = Arrays.stream(contactType.split(",")).collect(Collectors.toList());
            expectedLinksObjects.remove(0);
            contactType = contactType.split(",")[0];
        }
        if (contactType.equals("ECMS Side")) {
            CRM_TaskManagementStepDef.srID.set(APITaskManagementController.taskId.get() + "");
        }
        ArrayList<String> actualLinkObjects = new ArrayList<>(Collections.emptyList());

        for (int i = 0; i < getListOfEventsThreadLocal().size(); i++) {
            if (getListOfEventNames().get(i).equalsIgnoreCase("LINK_EVENT")) {
                eventId = getListOfEventIds().get(i);
                payloadObject = getListOfEventsThreadLocal().get(i);
                assertEquals(payloadObject.getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is Fail");
                assertEquals(payloadObject.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name is Fail");
                assertEquals(payloadObject.getString("action"), "Create", "action is Fail");
                assertEquals(payloadObject.getString("recordType"), "Link", "recordType is Fail");
                assertTrue(EventsUtilities.isValidDate(payloadObject.getString("eventCreatedOn")), "eventCreatedOn is fail");

                assertEquals(payloadObject.getJSONObject("dataObject").getString("correlationId"), correlationId);
                //    assertTrue((payloadObject.getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                //          getInt("externalLinkId") + "").chars().allMatch(Character::isDigit), "externalLinkId is fail");

                JSONObject temp = payloadObject.getJSONObject("dataObject").getJSONObject("externalLinkPayload");
                int taskId = (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())) + 1;

                String internal = temp.getString("internalRefType"), external = temp.getString("externalRefType");

                if (temp.getString("internalRefType").equals("SERVICE_REQUEST") && temp.getString("externalRefType").equals("CONTACT_RECORD")) {
                    assertEquals(temp.getInt("internalId") + "", srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D", ""), "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"), "externalId is miss match");
                    srToCR = true;
                    actualLinkObjects.add("srToCR");
                } else if (temp.getString("internalRefType").equals("CONTACT_RECORD") && temp.getString("externalRefType").equals("SERVICE_REQUEST")) {
                    assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"), "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D", ""), "externalId is miss match");
                    crToSR = true;
                    actualLinkObjects.add("crToSR");
                } else if (temp.getString("internalRefType").equalsIgnoreCase("CONSUMER") && temp.getString("externalRefType").equalsIgnoreCase("SERVICE_REQUEST")) {
                    assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D", ""), "externalId is miss match");
                    consumerToSR = true;
                    actualLinkObjects.add("consumerToSR");
                } else if (temp.getString("internalRefType").equalsIgnoreCase("SERVICE_REQUEST") && temp.getString("externalRefType").equalsIgnoreCase("CONSUMER")) {
                    assertEquals(temp.getInt("internalId") + "", srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D", ""), "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "externalId is miss match");
                    srToConsumer = true;
                    actualLinkObjects.add("srToConsumer");
                } else if (temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("SERVICE_REQUEST")) {
                    assertTrue(temp.getInt("internalId") >= taskId, "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D", ""), "externalId is miss match");
                    taskToSR = true;
                    actualLinkObjects.add("taskToSR");
                } else if (temp.getString("internalRefType").equals("SERVICE_REQUEST") && temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "", srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D", ""), "internalId is miss match");
                    assertTrue(temp.getInt("externalId") >= taskId, "externalId is miss match");
                    srToTask = true;
                    actualLinkObjects.add("srToTask");
                } else if (temp.getString("internalRefType").equals("CONSUMER") && temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "internalId is miss match");
                    assertTrue(temp.getInt("externalId") >= taskId, "externalId is miss match");
                    consumerToTask = true;
                    actualLinkObjects.add("consumerToTask");
                } else if (temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("CONSUMER")) {
                    assertTrue(temp.getInt("internalId") >= taskId, "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "externalId is miss match");
                    taskToConsumer = true;
                    actualLinkObjects.add("taskToConsumer");
                } else if (temp.getString("internalRefType").equalsIgnoreCase("CASE") && temp.getString("externalRefType").equalsIgnoreCase("SERVICE_REQUEST")) {
                    assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D", ""), "externalId is miss match");
                    caseToSR = true;
                    actualLinkObjects.add("caseToSR");
                } else if (temp.getString("internalRefType").equalsIgnoreCase("SERVICE_REQUEST") && temp.getString("externalRefType").equalsIgnoreCase("CASE")) {
                    assertEquals(temp.getInt("internalId") + "", srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D", ""), "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "externalId is miss match");
                    srToCase = true;
                    actualLinkObjects.add("srToCase");
                } else if (temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("CONTACT_RECORD")) {
                    assertTrue(temp.getInt("internalId") >= taskId, "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"), "externalId is miss match");
                    taskToCr = true;
                    actualLinkObjects.add("taskToCR");
                } else if (temp.getString("internalRefType").equals("CONTACT_RECORD") && temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"), "internalId is miss match");
                    assertTrue(temp.getInt("externalId") >= taskId, "externalId is miss match");
                    crToTask = true;
                    actualLinkObjects.add("crToTask");
                } else if (temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("CASE")) {
                    assertTrue(temp.getInt("internalId") >= taskId, "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "externalId is miss match");
                    taskToCase = true;
                    actualLinkObjects.add("taskToCase");
                } else if (temp.getString("internalRefType").equals("CASE") && temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "internalId is miss match");
                    assertTrue(temp.getInt("externalId") >= taskId, "externalId is miss match");
                    caseToTask = true;
                    actualLinkObjects.add("caseToTask");
                } else if (temp.getString("internalRefType").equals("SERVICE_REQUEST") && temp.getString("externalRefType").equals("INBOUND_CORRESPONDENCE")) {
                    assertEquals(temp.getInt("internalId") + "", APITaskManagementController.taskId.get() + "", "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", World.generalSave.get().get("InboundDocumentId") + "", "externalId is miss match");
                    srToInbound = true;
                    actualLinkObjects.add("srToInb");
                } else if (temp.getString("internalRefType").equals("INBOUND_CORRESPONDENCE") && temp.getString("externalRefType").equals("SERVICE_REQUEST")) {
                    assertEquals(temp.getInt("internalId") + "", World.generalSave.get().get("InboundDocumentId") + "", "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", APITaskManagementController.taskId.get() + "", "externalId is miss match");
                    inboundToSR = true;
                    actualLinkObjects.add("inbToSR");
                } else if (temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("INBOUND_CORRESPONDENCE")) {
                    assertTrue(temp.getInt("internalId") >= taskId, "internalId is miss match");
                    assertEquals(temp.getInt("externalId") + "", World.generalSave.get().get("InboundDocumentId") + "", "externalId is miss match");
                    srToInbound = true;
                    actualLinkObjects.add("taskToInb");
                } else if (temp.getString("internalRefType").equals("INBOUND_CORRESPONDENCE") && temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "", World.generalSave.get().get("InboundDocumentId") + "", "internalId is miss match");
                    assertTrue(temp.getInt("externalId") >= taskId, "externalId is miss match");
                    inboundToSR = true;
                    actualLinkObjects.add("inbToTask");
                }
                if (!((internal.equals("INBOUND_CORRESPONDENCE") && external.equals("CONSUMER")) || (internal.equals("CONSUMER") && external.equals("INBOUND_CORRESPONDENCE")) ||
                        (internal.equals("INBOUND_CORRESPONDENCE") && external.equals("CASE")) || (internal.equals("CASE") && external.equals("INBOUND_CORRESPONDENCE")))) {
                    assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")), "effectiveStartDate is fail");
                    assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is Fail");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn is fail");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn is fail");
                    if(contactType.equalsIgnoreCase("ECMS Side") || contactType.equalsIgnoreCase("Complaint Sr For INEB Side")){
                        assertFalse(temp.isNull("createdBy"),"createdBy is null");
                        assertFalse(temp.isNull("updatedBy"),"updatedBy is null");
                    } else {
                        switch (temp.getString("createdBy")){
                            case "8935":
                            case "8934":
                            case "8932":
                                assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserIdbyUserName("Task Mgmt Service"));
                                break;
                            case "9165":
                                assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserIdbyUserName("ECMS Service"));
                                break;
                            default:
                                assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo(), "createdBy is miss match");
                        }
                        switch (temp.getString("updatedBy")){
                            case "8935":
                                assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserIdbyUserName("Task Mgmt Service"));
                                break;
                            case "9165":
                                assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserIdbyUserName("ECMS Service"));
                                break;
                            default:
                                assertEquals(temp.getString("updatedBy"), apiTaskManagementController.getUserIdByUserNameAndProjectName(), "updatedBy is miss match");
                        }
                    }
                }
            }
        }
        if (type.equalsIgnoreCase("SR")) {
            switch (contactType) {
                case "Active Contact":
                    assertTrue(srToConsumer && consumerToSR && srToCR && crToSR && srToTask && taskToSR, "All 6 Events are not generated for SR | " + contactType);
                    break;
                case "Outside of Contact":
                    assertTrue(srToConsumer && consumerToSR && taskToSR && srToTask && consumerToTask && taskToConsumer, "All 6 Events are not generated for SR | " + contactType);
                    break;
                case "Active contact with case/consumer/task":
                    assertTrue(srToCase && caseToSR && srToConsumer && consumerToSR && crToSR && srToCR && taskToSR && srToTask, "All 8 Events are not generated SR | " + contactType);
                    break;
                case "ECMS Side":
                case "CUSTOM_PROVIDED":
                    assertTrue(actualLinkObjects.containsAll(expectedLinksObjects), "Link section contains incorrect objects");
                    assertEquals(actualLinkObjects.size(), expectedLinksObjects.size(), "Link section contains duplicate object");
                    break;
                case "Missing Information":
                    assertTrue(taskToSR && srToTask && consumerToTask && taskToConsumer, "All 4 Events are not generated for SR | " + contactType);
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect switch argument " + contactType);
            }
        } else if (type.equalsIgnoreCase("TASK")) {
            switch (contactType) {
                case "Active Contact":
                    assertTrue(taskToCase && caseToTask && taskToConsumer && consumerToTask && !taskToCr && !crToTask && taskToSR && srToTask, "All 6 Events are not generated for TASK | " + contactType);
                    break;
                case "Outside of contact with case/consumer/sr":
                    assertTrue(taskToConsumer && consumerToTask && taskToCase && caseToTask && taskToSR && srToTask, "All 6 Events are not generated for TASK | " + contactType);
                    break;
                case "Complaint Sr For INEB Side":
                    assertTrue(actualLinkObjects.containsAll(expectedLinksObjects), "Link section contains incorrect objects");
                    assertEquals(actualLinkObjects.size(), expectedLinksObjects.size(), "Link section contains duplicate object");
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect switch argument " + contactType);
            }
        } else throw new IllegalArgumentException("Please select SR or Task : Currently Selected " + type);
    }

    @Then("I verify TASK_SAVE_EVENT for Task has all the proper data")
    public void verifyTaskSaveEventForTask() throws Exception {
        boolean flag = false, flag2 = false;
        int k = 0;
        for (int i = 0; i < getListOfEventsThreadLocal().size(); i++) {
            if (getListOfEventNames().get(i).equalsIgnoreCase("TASK_SAVE_EVENT")) {
                JSONObject temp = getListOfEventsThreadLocal().get(i).getJSONObject("dataObject");
                int taskTypeId = temp.getInt("taskTypeId");
                boolean taskTypeMatched = Arrays.asList(13366, 13367, 13368, 13474, 13496, 13511, 15182, 15351, 15356, 15372, 15373, 15441, 15352, 15371, 15364, 15573, 15574, 15367, 15365, 15373,16604,16605,16606,16607,16608,16609,16610).contains(taskTypeId);
                if (taskTypeMatched) {
                    eventId = getListOfEventIds().get(i);
                    flag = true;
                    k++;
                    payloadObject = getListOfEventsThreadLocal().get(i);
                    assertFalse(getListOfEventsThreadLocal().get(i).isNull("dataObject"));
                    assertEquals(getListOfEventsThreadLocal().get(i).getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a BLCRM");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("action").toLowerCase(), "create", "Action is not a Update");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("recordType").toLowerCase(), "taskvo", "Record type is not a Task");
                    assertTrue(EventsUtilities.isValidDate(getListOfEventsThreadLocal().get(i).getString("eventCreatedOn")), "eventCreatedOn date time field IS NOT valid");
                    assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"), "taskId is null or equal to 0");
                    assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"), "taskTypeId is null or equal to 0");
                    assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
                    verifyServiceRequestInd(taskTypeId, temp);

                    switch (temp.getInt("taskTypeId")) {
                        case 13474:
                        case 15372:
                        case 15351:
                        case 15352:
                        case 13496:
                        case 15371:
                        case 15364:
                        case 15356:
                        case 15367:
                        case 15365:
                            assertEquals(temp.getInt("defaultPriority"), 1, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", getDueDate("localDate", 1, "IN-EB"), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 1, "dueInDays is miss match");
                            break;
                        case 15182:
                            assertEquals(temp.getInt("defaultPriority"), 2, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 3, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 3, "dueInDays is miss match");
                            break;
                        case 13511:
                            assertEquals(temp.getInt("defaultPriority"), 1, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 10, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 10, "dueInDays is miss match");
                            break;
                        case 13366:
                            assertEquals(temp.getInt("defaultPriority"), 2, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 7, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 7, "dueInDays is miss match");
                            break;
                        case 15441:
                            assertEquals(temp.getInt("defaultPriority"), 1, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", getDueDate("localDate", 7, "NJ-SBE"), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 7, "dueInDays is miss match");
                            break;
                        case 13368:
                            assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 1, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 1, "dueInDays is miss match");
                            break;
                        case 13367:
                            assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
//                            assertEquals(temp.getString("defaultDueDate") + "", apitdu.addDaysToPresentDate("yyyy-MM-dd", 21, 0, 0), "defaultDueDate is miss match");
//                            assertEquals(temp.getInt("dueInDays"), 21, "dueInDays is miss match");
                            break;
                        case 15573:
                        case 15574:
                            assertEquals(temp.getInt("defaultPriority"), 5, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", getDueDate("localDate", 7, "NJ-SBE"), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 7, "dueInDays is miss match");
                            break;
                    }
                    assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
                    assertEquals(temp.getString("taskStatus"), "Created", "Task Status is miss match");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
                    assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
                    if (temp.getInt("taskTypeId") == 13368 || temp.getInt("taskTypeId") == 15573 || temp.getInt("taskTypeId") == 15574 || temp.getInt("taskTypeId") == 13367 || CRM_GeneralTaskStepDef.taskValues.get().get("assignee") == null || CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("-- --") || CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty())
                        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
                    else
                        assertEquals(temp.getInt("staffAssignedTo") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
                    assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
                    assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
                    assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
                    assertTrue(temp.isNull("dueIn"), "dueIn is not null");
                    assertEquals(temp.getString("source"), "System", "source is miss match");
                    assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
                    //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
                    assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
                    assertTrue(temp.isNull("editReason"), "editReason is not null");
                    assertTrue(temp.isNull("holdReason"), "holdReason is not null");
                    assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
                    assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
                    assertFalse(temp.isNull("createdBy"), "createdBy is null");
                    assertFalse(temp.isNull("updatedBy"), "updatedBy is null");
                    assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
                    assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
                    assertEquals(temp.getString("action"), "INSERT", "action is miss match");
                    assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
                    assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"), "createdOn and updatedOn is miss match");
                    if ((temp.getInt("taskTypeId") == 15441 || temp.getInt("taskTypeId") == 13366) && k != 2) {
                        flag2 = true;
                        continue;
                    } else
                        break;
                }
            }
        }
        assertTrue(flag, "TASK_SAVE_EVENT is not generated");
        if (flag2)
            assertEquals(k, 2, "2 Task Save events are not generated");
    }

    @Then("I verify task details information for a Task which is created when SR got created")
    public void verifyTaskDetailsInformationInTaskSaveEventForaTask() {
        JSONArray json = payloadObject.getJSONObject("dataObject").getJSONArray("taskDetails");
        boolean flag = false;
        for (int i = 0; i < json.length(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            String field = temp.getString("selectionFieldName");
            flag = true;
            switch (field) {
                case "Missing Info Required?":
                case "Application Received Date":
                case "Application Status":
                case "External Application ID":
                case "Denial Reason":
                case "No Of Approved Applicants":
                case "Channel":
                case "Decision Source":
                case "Date of Contact":
                case "Action Taken Single":
                case "No Of Applicants in Household":
                case "Date Received":
                case "Application Signature Date":
                case "HPE?":
                case "Date of Birth":
                case "Disposition":
                case "Facility Name":
                case "Application Type":
                case "Facility Type":

                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");

                    assertEquals(temp.getInt("taskId"), payloadObject.getJSONObject("dataObject").
                            getInt("taskId"), "TaskId is miss match");
                    assertTrue(temp.getInt("taskDetailsId") != 0 || !temp.isNull("taskDetailsId"),
                            "taskDetailsId is null or equal 0");
                    assertTrue(temp.getInt("taskFieldId") != 0 || !temp.isNull("taskFieldId"),
                            "taskFieldId is null or equal 0");
                    assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
                    assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                            "CreatedOn date time field IS NOT valid");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                            "updatedOn date time field IS NOT valid");
                    assertTrue(!temp.getString("correlationId").isEmpty() ||
                            !temp.isNull("correlationId"), "correlationId is null or empty string");
                    break;
                default:
                    Assert.fail("The " + field + " is not declared in the switch statement");
            }

        }
        assertTrue(flag, "Task Details are not present");
    }

    @Then("I verify TASK_UPDATE_EVENT for TASK has all the proper data")
    public void verifyTaskUpdateEvent() {
        payloadObject = getListOfEventsThreadLocal().get(0);
        eventId = getListOfEventIds().get(0);
        assertFalse(payloadObject.isNull("dataObject"));
        assertEquals(payloadObject.getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");

        assertEquals(payloadObject.getString("action").toLowerCase(), "update",
                "Action is not a Update");
        assertEquals(payloadObject.getString("recordType").toLowerCase(), "taskvo",
                "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(payloadObject.getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.getJSONObject("dataObject");

        assertEquals(temp.getInt("taskId") + "", CRM_TaskManagementStepDef.taskId.get(),
                "taskId is mismatch");
        assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"),
                "taskTypeId is null or equal to 0");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"),
                "defaultPriority is miss match");
        assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")),
                "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), CRM_GeneralTaskStepDef.taskValues.get().get("status"),
                "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("assignee") == null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("-- --") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty()) {
            assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        } else {
            assertEquals(temp.getInt("staffAssignedTo") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
        }
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").equals("-- --"))
            assertEquals(temp.getInt("dueInDays") + "",
                    CRM_TaskManagementStepDef.dueIn.get().split(" ")[0], "dueInDays is miss match");
        else
            assertEquals(temp.getInt("dueInDays") + "",
                    CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").split(" ")[0], "dueInDays is miss match");
        assertEquals(temp.getString("source"), "User", "source is miss match");
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskInfo") && !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("-- --") &&
                !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals(""))
            assertEquals(temp.getString("taskInfo"), CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"), "taskInfo is miss match");
        else
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        /*
        if(CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote") && !CRM_GeneralTaskStepDef.taskValues.get().get("taskNote").equals("-- --"))
            assertEquals(temp.getString("taskNotes"), CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"), "taskNotes is miss match");
        else
            assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
         */
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForOnHold") && !CRM_GeneralTaskStepDef.taskValues.get().get("reasonForOnHold").equals("-- --"))
            assertEquals(temp.getString("holdReason").replaceAll("_", " ").toLowerCase(),
                    CRM_GeneralTaskStepDef.taskValues.get().get("reasonForOnHold").toLowerCase(), "holdReason is miss match");
        else
            assertTrue(temp.isNull("holdReason"), "holdReason is not null");

        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForCancel") && !CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel").equals("-- --"))
            assertEquals(temp.getString("cancelReason").replaceAll("_", " ").toLowerCase(),
                    CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel").toLowerCase(), "cancelReason is miss match");
        else
            assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");

        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("status").equalsIgnoreCase("Closed"))
            assertFalse(temp.isNull("createdBy"), "Created by is null");
        else
            assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
        assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertNotEquals(temp.getString("createdOn"), temp.getString("updatedOn"),
                "createdOn and updatedOn is miss match");
        verifyServiceRequestInd(temp.getInt("taskTypeId"), temp);
    }

    @Then("I verify TASK_UPDATE_EVENT for SR has all the proper data")
    public void verifyTaskUpdateEventForSR() throws Exception {
        boolean flag1 = false;
        for (int i = 0; i < getListOfEventsThreadLocal().size(); i++) {
            if (getListOfEventNames().get(i).equalsIgnoreCase("TASK_UPDATE_EVENT")) {
                JSONObject temp = getListOfEventsThreadLocal().get(i).getJSONObject("dataObject");
                int taskTypeId = temp.getInt("taskTypeId");
                boolean taskTypeMatched = Arrays.asList(15410, 15349, 15350, 15180, 13493, 16277, 15351, 16565).contains(taskTypeId);
                if (taskTypeMatched) {
                    eventId = getListOfEventIds().get(i);
                    flag1 = true;
                    payloadObject = getListOfEventsThreadLocal().get(i);
                    assertFalse(getListOfEventsThreadLocal().get(i).isNull("dataObject"));
                    assertEquals(getListOfEventsThreadLocal().get(i).getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a BLCRM");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("action").toLowerCase(), "update", "Action is not a Update");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("recordType").toLowerCase(), "taskvo", "Record type is not a Task");
                    assertTrue(EventsUtilities.isValidDate(getListOfEventsThreadLocal().get(i).getString("eventCreatedOn")), "eventCreatedOn date time field IS NOT valid");
                    assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"), "taskId is null or equal to 0");
                    assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"), "taskTypeId is null or equal to 0");
                    assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
                    verifyServiceRequestInd(taskTypeId, temp);
                    switch (temp.getInt("taskTypeId")) {
                        case 15410:
                            assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 7, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 7, "dueInDays is miss match");
                            assertEquals(temp.getString("source"), "System", "source is miss match");
                            break;
                        case 15349:
                            assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", getDueDate("localDate", 3, "IN-EB"), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 3, "dueInDays is miss match");
                            assertEquals(temp.getString("source"), "User", "source is miss match");
                            break;
                        case 15350:
                            assertEquals(temp.getInt("defaultPriority"), 1, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", getDueDate("localDate", 30, "IN-EB"), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 30, "dueInDays is miss match");
                            assertEquals(temp.getString("source"), "User", "source is miss match");
                            break;
                        case 15180:
                            assertEquals(temp.getInt("defaultPriority"), 1, "defaultPriority is miss match");
//                            assertEquals(temp.getString("defaultDueDate") + "", getDueDate("localDate",0, "CoverVA"), "defaultDueDate is miss match");
//                            assertEquals(temp.getInt("dueInDays"), 0, "dueInDays is miss match");
                            assertEquals(temp.getString("source"), "User", "source is miss match");
                            break;
                        case 13493:
                            assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
                            assertEquals(temp.getInt("dueInDays"), 60, "dueInDays is miss match");
                            assertEquals(temp.getString("source"), "User", "source is miss match");
                            break;
                        case 16277:
                            assertEquals(temp.getInt("defaultPriority"), 2, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate"), API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 31, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 31, "dueInDays is miss match");
                            break;
                    }
                    assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
                    assertEquals(temp.getString("taskStatus"), "Closed", "taskStatus is not Closed");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
                    assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
                    assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
                    assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
                    assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
                    assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
                    assertTrue(temp.isNull("dueIn"), "dueIn is not null");
                    assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
                    //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
                    assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
                    assertTrue(temp.isNull("editReason"), "editReason is not null");
                    assertTrue(temp.isNull("holdReason"), "holdReason is not null");
                    assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
                    assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
                    /*assertEquals(temp.getString("createdBy"), api.userId, "createdBy is miss match");
                    assertEquals(temp.getString("updatedBy"), api.userId, "updatedBy is miss match");*/
                    assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
                    assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
                    assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                    assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
                    break;
                }
            }
        }
        assertTrue(flag1, "TASK_UPDATE_EVENT is not generated");
    }

    @Then("I verify TASK_NOTE_SAVE_EVENT for task or SR")
    public void verifyTaskNoteSaveEventForTask() {
        boolean flag = false;
        for (int i = 0; i < getListOfEventsThreadLocal().size(); i++) {
            if (getListOfEventNames().get(i).equalsIgnoreCase("TASK_NOTE_SAVE_EVENT")) {
                JSONObject temp = getListOfEventsThreadLocal().get(i).getJSONObject("dataObject");
                eventId = getListOfEventIds().get(i);
                flag = true;
                payloadObject = getListOfEventsThreadLocal().get(i);
                assertFalse(getListOfEventsThreadLocal().get(i).isNull("dataObject"));
                assertEquals(getListOfEventsThreadLocal().get(i).getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
                assertEquals(getListOfEventsThreadLocal().get(i).getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal());
                assertEquals(getListOfEventsThreadLocal().get(i).getString("action").toLowerCase(), "create", "Action is not a Create");
                assertEquals(getListOfEventsThreadLocal().get(i).getString("recordType").toLowerCase(), "tasknoteseventvo", "Record type is not a taskNote");

                assertEquals(temp.getInt("taskId") + "", CRM_TaskManagementStepDef.taskId.get(), "TaskId is mismatch");
                assertTrue(temp.getInt("taskNoteId") != 0 && !temp.isNull("taskNoteId"), "taskNoteId is null or equal to 0");
                assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
                assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
                assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
                assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
                assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
                assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"), "createdOn and updatedOn is miss match");
                assertEquals(temp.getString("text"), CRM_TaskNoteComponentStepDef.taskNoteValues.get().get("noteValue"), "Task Note is mismatch");
                break;
            }
        }
        assertTrue(flag, "TASK_NOTE_SAVE_EVENT is not generated");
    }

    @Then("I verify TASK_UPDATE_EVENT for Task has all the proper data")
    public void verifyTaskUpdateEventForTask() throws Exception {
        boolean flag = false;
        for (int i = 0; i < getListOfEventsThreadLocal().size(); i++) {
            if (getListOfEventNames().get(i).equalsIgnoreCase("TASK_UPDATE_EVENT")) {
                JSONObject temp = getListOfEventsThreadLocal().get(i).getJSONObject("dataObject");
                int taskTypeId = temp.getInt("taskTypeId");
                boolean taskTypeMatched = Arrays.asList(13368, 15372, 15414, 15373, 12840, 15415, 15182, 15351, 15352, 15355, 15364, 15371, 13494, 13541).contains(taskTypeId);
                if (taskTypeMatched) {
                    eventId = getListOfEventIds().get(i);
                    flag = true;
                    payloadObject = getListOfEventsThreadLocal().get(i);
                    assertFalse(getListOfEventsThreadLocal().get(i).isNull("dataObject"));
                    assertEquals(getListOfEventsThreadLocal().get(i).getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a BLCRM");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("action").toLowerCase(), "update", "Action is not a Update");
                    assertEquals(getListOfEventsThreadLocal().get(i).getString("recordType").toLowerCase(), "taskvo", "Record type is not a Task");
                    assertTrue(EventsUtilities.isValidDate(getListOfEventsThreadLocal().get(i).getString("eventCreatedOn")), "eventCreatedOn date time field IS NOT valid");
                    assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"), "taskId is null or equal to 0");
                    assertTrue(taskTypeId != 0 && !temp.isNull("taskTypeId"), "taskTypeId is null or equal to 0");
                    assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
                    verifyServiceRequestInd(taskTypeId, temp);
                    switch (taskTypeId) {
                        case 13368:
                            assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 1, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 1, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertEquals(temp.getString("taskStatus"), "Created", "Task Status is miss match");
                            break;
                        case 15372:
                            assertEquals(temp.getInt("defaultPriority"), 1, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 1, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 1, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertEquals(temp.getString("taskStatus"), "Complete", "Task Status is miss match");
                            break;
                        case 15414:
                            assertEquals(temp.getInt("defaultPriority"), 2, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 60, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 60, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertEquals(temp.getString("taskStatus"), "Closed", "Task Status is miss match");
                            assertEquals(temp.getString("source"), "User", "source is miss match");
                            break;
                        case 15373:
                            assertEquals(temp.getInt("defaultPriority"), 2, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 3, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 3, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertTrue(temp.getString("taskStatus").equals("Cancelled") || temp.getString("taskStatus").equals("Complete"), "Task Status is miss match");
                            break;
                        case 12840:
                            assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")), "defaultDueDate is miss match");
                            if (CRM_GeneralTaskStepDef.taskValues.get().get("status").equals("Complete") ||
                                    CRM_GeneralTaskStepDef.taskValues.get().get("status").equals("Cancelled")) {
                                assertTrue(!temp.isNull("dueInDays"), "dueInDays is miss match");
                            } else
                                assertEquals(temp.getInt("dueInDays"), 3, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertEquals(temp.getString("taskStatus"), CRM_GeneralTaskStepDef.taskValues.get().get("status"), "Task Status is miss match");
                            assertEquals(temp.getString("source"), "User", "source is miss match");
                            break;
                        case 15415:
                            assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")), "defaultDueDate is miss match");
                            if (CRM_GeneralTaskStepDef.taskValues.get().get("status").equals("Complete") ||
                                    CRM_GeneralTaskStepDef.taskValues.get().get("status").equals("Cancelled")) {
                                assertTrue(!temp.isNull("dueInDays"), "dueInDays is miss match");
                            } else
                                assertEquals(temp.getInt("dueInDays"), 2, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertEquals(temp.getString("taskStatus"), CRM_GeneralTaskStepDef.taskValues.get().get("status"), "Task Status is miss match");
                            assertEquals(temp.getString("source"), "User", "source is miss match");
                            break;
                        case 15182:
                            assertEquals(temp.getInt("defaultPriority"), 2, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 5, 0, 0), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 4, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertEquals(temp.getString("taskStatus"), "Cancelled", "Task Status is miss match");
                            break;
                        case 15351:
                        case 15352:
                        case 15355:
                        case 15364:
                        case 15371:
                            assertEquals(temp.getInt("defaultPriority"), 1, "defaultPriority is miss match");
                            assertEquals(temp.getString("defaultDueDate") + "", getDueDate("localDate", 1, "IN-EB"), "defaultDueDate is miss match");
                            assertEquals(temp.getInt("dueInDays"), 1, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertTrue(temp.getString("taskStatus").equals("Cancelled") || temp.getString("taskStatus").equals("Complete"), "Task Status is miss match");
                            break;
                        case 13494:
                            assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
                            assertEquals(temp.getInt("dueInDays"), 5, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertTrue(temp.getString("taskStatus").equals("Cancelled") || temp.getString("taskStatus").equals("Complete"), "Task Status is miss match");
                            break;
                        case 13541:
                            assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
                            assertEquals(temp.getInt("dueInDays"), 55, "dueInDays is miss match");
                            assertEquals(temp.getString("action"), "UPDATE", "action is miss match");
                            assertTrue(temp.getString("taskStatus").equals("Cancelled") || temp.getString("taskStatus").equals("Complete"), "Task Status is miss match");
                            break;
                    }
                    assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
                    assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("assignee") == null ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("-- --") ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty()) {
                        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
                    } else {
                        assertEquals(temp.getInt("staffAssignedTo") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
                    }
                    assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
                    assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
                    assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
                    assertTrue(temp.isNull("dueIn"), "dueIn is not null");
                    if (taskTypeId != 15414 && taskTypeId != 12840 && taskTypeId != 15415) {
                        assertEquals(temp.getString("source"), "System", "source is miss match");
                    }
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskInfo") && !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("-- --") &&
                            !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals(""))
                        assertEquals(temp.getString("taskInfo") + "", CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"), "taskInfo is miss match");
                    else
                        assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
                    /*
                    if(CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote") && !CRM_GeneralTaskStepDef.taskValues.get().get("taskNote").equals("-- --") &&
                            !CRM_GeneralTaskStepDef.taskValues.get().get("taskNote").equals(""))
                        assertEquals(temp.getString("taskNotes") + "", CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"), "taskNotes is miss match");
                    else
                        assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
                        */
                    assertTrue(temp.isNull("editReason"), "editReason is not null");
                    assertTrue(temp.isNull("holdReason"), "holdReason is not null");
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForCancel") && !CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel").equals("-- --") &&
                            !CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel").equals(""))
                        assertEquals(temp.getString("cancelReason").replaceAll("_", " ") + "", CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel").toUpperCase(), "cancelReason is miss match");
                    else
                        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
                    assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("createdBy")) {
                        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
                        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
                    } else {
                        assertFalse(temp.isNull("createdBy"), "createdBy is null");
                        assertFalse(temp.isNull("updatedBy"), "updatedBy is null");
                    }
                    assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
                    assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
                    assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
                    assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
                    assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
                    break;
                }
            }
        }
        assertTrue(flag, "TASK_UPDATE_EVENT is not generated");
    }

    @Then("I verify {string} generated for {string} task : {string}")
    public void i_verify_link_events_are_generated_for_a_task_from(String event_type, String taskType, String expectedLinksObjects) {
        String action = event_type.equalsIgnoreCase("UNLINK_EVENT") ? "Update" : "Create";
        List<String> expectedLinkEvents = Arrays.stream(expectedLinksObjects.split(",")).sorted().collect(Collectors.toList());
        List<String> actualLinkEvents = new ArrayList<>(Collections.emptyList());
        for (int i = 0; i < getListOfEventsThreadLocal().size(); i++) {
            if (getListOfEventNames().get(i).equalsIgnoreCase(event_type)) {
                eventId = getListOfEventIds().get(i);
                payloadObject = getListOfEventsThreadLocal().get(i);
                assertEquals(payloadObject.getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is Fail");
                assertEquals(payloadObject.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name is Fail");
                assertEquals(payloadObject.getString("action"), action, "action is Fail");
                assertEquals(payloadObject.getString("recordType"), "Link", "recordType is Fail");
                assertTrue(EventsUtilities.isValidDate(payloadObject.getString("eventCreatedOn")), "eventCreatedOn is fail");
                assertEquals(payloadObject.getJSONObject("dataObject").getString("correlationId"), correlationId);
                assertTrue((payloadObject.getJSONObject("dataObject").getJSONObject("externalLinkPayload").getInt("externalLinkId") + "").chars().allMatch(Character::isDigit), "externalLinkId is fail");
                JSONObject temp = payloadObject.getJSONObject("dataObject").getJSONObject("externalLinkPayload");
                String linkCombination = temp.getString("internalRefType") + " TO " + temp.getString("externalRefType");
                System.out.println(linkCombination.toUpperCase());
                actualLinkEvents.add(linkCombination);
                switch (linkCombination.toUpperCase()) {
                    case "TASK TO SERVICE_REQUEST":
                        validateLinkEventTaskToSr(temp);
                        break;
                    case "SERVICE_REQUEST TO TASK":
                        validateLinkEventSrToTask(temp);
                        break;
                    case "TASK TO CONSUMER":
                        validateLinkEventTaskToConsumer(temp);
                        break;
                    case "CONSUMER TO TASK":
                        validateLinkEventConsumerToTask(temp);
                        break;
                    case "TASK TO CONTACT_RECORD":
                        validateLinkEventTaskToContactRecord(temp);
                        break;
                    case "CONTACT_RECORD TO TASK":
                        validateLinkEventContactRecordToTask(temp);
                        break;
                    case "TASK TO CASE":
                        validateLinkEventTaskToCase(temp);
                        break;
                    case "CASE TO TASK":
                        validateLinkEventCaseToTask(temp);
                        break;
                    case "TASK TO INBOUND_CORRESPONDENCE":
                        validateLinkEventTaskToInbound(temp);
                        break;
                    case "INBOUND_CORRESPONDENCE TO TASK":
                        validateLinkEvenInboundToTask(temp);
                        break;
                    case "SERVICE_REQUEST TO INBOUND_CORRESPONDENCE":
                        validateLinkEventSrToInbound(temp);
                        break;
                    case "INBOUND_CORRESPONDENCE TO SERVICE_REQUEST":
                        validateLinkEventInboundToSr(temp);
                        break;
                    case "SERVICE_REQUEST TO SERVICE_REQUEST":
                        validateLinkEventSrToSR(temp);
                        break;
                    case "SERVICE_REQUEST TO CONSUMER":
                        validateLinkEventSrToConsumer(temp);
                        break;
                    case "CONSUMER TO SERVICE_REQUEST":
                        validateLinkEventConsumerToSr(temp);
                        break;
                    case "SERVICE_REQUEST TO CASE":
                        validateLinkEventSrToCase(temp);
                        break;
                    case "CASE TO SERVICE_REQUEST":
                        validateLinkEventCaseToSr(temp);
                        break;
                    default:
                        fail("Incorrect argument in switch statement " + linkCombination);
                }

                if (event_type.equalsIgnoreCase("UNLINK_EVENT")) {
                    assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveEndDate")), "effectiveEndDate is Fail");
                } else {
                    assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is Fail");
                }
                    // need a refactoring here
                assertNotNull(Integer.valueOf(temp.getString("createdBy")), "createdBy is null");
                assertNotNull(Integer.valueOf(temp.getString("updatedBy")), "updatedBy is null");
                assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")), "effectiveStartDate is fail");
                assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn is fail");
                assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn is fail");
            }
        }
        assertEquals(actualLinkEvents.size(), expectedLinkEvents.size(), event_type + "  not generated ");
        assertEquals(actualLinkEvents.stream().map(String::toUpperCase).sorted().collect(Collectors.toList()), expectedLinkEvents, "List link events not ");
    }

    private void validateLinkEventContactRecordToTask(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("CONTACT_RECORD") && temp.getString("externalRefType").equals("TASK"));
        assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"), "internalId is miss match");
        assertTrue(temp.getInt("externalId") >= (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())), "externalId is miss match");
    }

    private void validateLinkEventTaskToContactRecord(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("CONTACT_RECORD"));
        assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"), "externalId is miss match");
        assertTrue(temp.getInt("internalId") >= (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())), "internalId is is miss match");
    }

    private void validateLinkEventCaseToTask(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("CASE") && temp.getString("externalRefType").equals("TASK"));
        assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "internalId is miss match");
        assertTrue(temp.getInt("externalId") >= (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())), "externalId is miss match");
    }

    private void validateLinkEventTaskToCase(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("CASE"));
        assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "externalId is miss match");
        assertTrue(temp.getInt("internalId") >= (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())), "internalId is miss match");
    }

    private void validateLinkEventConsumerToTask(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equalsIgnoreCase("CONSUMER") && temp.getString("externalRefType").equalsIgnoreCase("TASK"));
        assertEquals(temp.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "internalId is miss match");
        assertTrue(temp.getInt("externalId") >= (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())), "externalId is miss match");
    }

    private void validateLinkEventTaskToConsumer(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equalsIgnoreCase("TASK") && temp.getString("externalRefType").equalsIgnoreCase("CONSUMER"));
        assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "externalId is miss match");
        assertTrue(temp.getInt("internalId") >= (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())), "internalId is miss match");
    }

    private void validateLinkEventTaskToSr(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("SERVICE_REQUEST"));
        assertEquals(temp.getInt("externalId") + "", CRM_TaskManagementStepDef.srID.get(), "externalId is miss match");
        assertTrue(temp.getInt("internalId") > Integer.parseInt(CRM_TaskManagementStepDef.srID.get()));
    }

    private void validateLinkEventSrToTask(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("SERVICE_REQUEST") && temp.getString("externalRefType").equals("TASK"));
        assertEquals(temp.getInt("internalId") + "", CRM_TaskManagementStepDef.srID.get(), "internalId is miss match");
        assertTrue(temp.getInt("externalId") > (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())));
    }

    private void validateLinkEventSrToSR(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("SERVICE_REQUEST") && temp.getString("externalRefType").equals("SERVICE_REQUEST"));
    }

    public String getDueDate(String date, int days, String project) throws Exception {
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().i_initiated_create_business_unit_api(project, "2022");
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().getHolidayList();
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().i_initiated_create_business_unit_api(project);
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().getTimeFrameConfiguredForProject();

        LocalDate result = LocalDate.now();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (!date.equalsIgnoreCase("localDate")) {
            formatter = formatter.withLocale(Locale.US);
            result = LocalDate.parse(date, formatter);
        }
        int addedDays = 0;

        while (addedDays < days) {
            result = result.plusDays(1);
            if (APITMBusinessDayRestController.businessDay.get().contains(result.getDayOfWeek().toString())) {
                if (!APITMHolidayController.holiday.contains(result.format(formatter))) {
                    addedDays++;
                }
            }
        }
        return result.format(formatter);
    }

    private void verifyServiceRequestInd(int taskTypeId, JSONObject temp) {
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().initiateGetTaskTypeApi("");
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().runGetTaskTypeApi();
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            if (temp1.getInt("taskTypeId") == taskTypeId) {
                assertEquals(temp.getBoolean("serviceRequestInd"), temp1.getBoolean("serviceRequestInd"), "serviceRequestInd is miss match");
                break;
            }
        }
    }

    private void validateLinkEventTaskToInbound(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("TASK") && temp.getString("externalRefType").equals("INBOUND_CORRESPONDENCE"));
        assertEquals(temp.getInt("externalId") + "", Optional.ofNullable(CRM_GeneralTaskStepDef.taskValues.get().get("InboundDocumentId")).orElse(String.valueOf(World.generalSave.get().get("InboundDocumentId"))), "externalId is miss match");
        assertTrue(temp.getInt("internalId") >= (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())), "internalId is miss match");
    }

    private void validateLinkEvenInboundToTask(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("INBOUND_CORRESPONDENCE") && temp.getString("externalRefType").equals("TASK"));
        assertEquals(temp.getInt("externalId") + "", Optional.ofNullable(CRM_GeneralTaskStepDef.taskValues.get().get("taskID")).orElse(CRM_TaskManagementStepDef.taskId.get()), "externalId is miss match");
    }

    private void validateLinkEventSrToInbound(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("SERVICE_REQUEST") && temp.getString("externalRefType").equals("INBOUND_CORRESPONDENCE"));
        assertEquals(temp.getInt("internalId") + "", CRM_TaskManagementStepDef.srID.get(), "internalId is miss match");
        assertTrue(temp.getInt("externalId") > (Integer.parseInt(CRM_TaskManagementStepDef.srID.get())));
    }

    private void validateLinkEventInboundToSr(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equals("INBOUND_CORRESPONDENCE") && temp.getString("externalRefType").equals("SERVICE_REQUEST"));
        assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("taskID"), "externalId is miss match");
        assertTrue(temp.getInt("internalId") > Integer.parseInt(CRM_TaskManagementStepDef.srID.get()));
    }

    private void validateLinkEventSrToConsumer(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equalsIgnoreCase("SERVICE_REQUEST") && temp.getString("externalRefType").equalsIgnoreCase("CONSUMER"));
    }

    private void validateLinkEventConsumerToSr(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equalsIgnoreCase("CONSUMER") && temp.getString("externalRefType").equalsIgnoreCase("SERVICE_REQUEST"));
    }

    private void validateLinkEventSrToCase(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equalsIgnoreCase("SERVICE_REQUEST") && temp.getString("externalRefType").equalsIgnoreCase("CASE"));
    }

    private void validateLinkEventCaseToSr(JSONObject temp) {
        assertTrue(temp.getString("internalRefType").equalsIgnoreCase("CASE") && temp.getString("externalRefType").equalsIgnoreCase("SERVICE_REQUEST"));
    }
}
