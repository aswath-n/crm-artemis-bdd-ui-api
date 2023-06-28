package com.maersk.crm.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.api_step_definitions.APITaskSearchController;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.dms.utilities.EventsUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EventBaseClass extends CRMUtilities implements ApiBase {

    CRM_TaskManagementStepDef myTask = new CRM_TaskManagementStepDef();
    APITaskManagementController taskType = new APITaskManagementController();
    String baseURI = ConfigurationReader.getProperty("apiEventsURI");
    String eventEndPoint = "/app/crm/event/correlation/correlation_id";
    String subscribersEndPoint = "/app/crm/es/event/subscribers";
    String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/{subscriberId}/{eventId}";
//    public static ThreadLocal<ArrayList<JSONObject>> listOfEventsThreadLocal = new ThreadLocal<>();
//    public static ArrayList<String> listOfEventIds= new ArrayList<>();
//    public static ArrayList<String> listOfEventNames= new ArrayList<>();
    private JsonObject requestParams;
    public static String correlationId="";
    public String subscriberId = "";
    public JSONArray subscriberList;
    public String eventId = "";
    public static JSONObject payloadObject;
    ArrayList<String> eventValues = null;

    public final ThreadLocal<ArrayList<JSONObject>> listOfEventsThreadLocal = ThreadLocal.withInitial(ArrayList<JSONObject>::new);
    public ArrayList<JSONObject> getListOfEventsThreadLocal(){
        if (listOfEventsThreadLocal.get() == null){
            listOfEventsThreadLocal.set(new ArrayList<JSONObject>());
        }
        return listOfEventsThreadLocal.get();
    }

    public final ThreadLocal<ArrayList<String>> listOfEventIds = ThreadLocal.withInitial(ArrayList<String>::new);
    public ArrayList<String> getListOfEventIds(){
        if (listOfEventIds.get() == null){
            listOfEventIds.set(new ArrayList<String>());
        }
        return listOfEventIds.get();
    }

    public final ThreadLocal<ArrayList<String>> listOfEventNames = ThreadLocal.withInitial(ArrayList<String>::new);
    public ArrayList<String> getListOfEventNames(){
        if (listOfEventNames.get() == null){
            listOfEventNames.set(new ArrayList<String>());
        }
        return listOfEventNames.get();
    }

    public String getCorrelationId(String traceId){
        String status="";
        switch (traceId){
            case "Task_update":
                status = CRM_TaskManagementStepDef.taskStatus.get();
                correlationId = EventsUtilities.getLogs("traceid", "taskStatus\\\":\\\"" + status).get(0);
                break;
            case "Created":
                status = "Created";
                correlationId = EventsUtilities.getLogs("traceid", "taskStatus\\\":\\\"" + status).get(0);
                break;
            case "Open":
                status = "Open";
                correlationId = EventsUtilities.getLogs("traceid", "taskStatus\\\":\\\"" + status).get(0);
                break;
            case "StaffUpdate":
                status = "true";
                correlationId = EventsUtilities.getLogs("traceid", "inactivateImmediately\\\":" + status).get(0);
                break;
            case "taskId":
                correlationId = EventsUtilities.getLogs("traceid", "taskId\\\":" + CRM_TaskManagementStepDef.taskId.get()).get(0);
                break;
            case "traceId":
                correlationId = APITaskManagementController.traceId.get();
                break;
            case "apiClassUtilTraceId":
                correlationId = APIClassUtil.traceId.get();
                break;
            case "traceId1":
                correlationId = taskType.traceId1.get();
                break;
            case "disposition":
            case "disposition_idr_successful":
                status="IDR_SUCCESSFUL";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "manually_link_sr_to_task":
            case "manually_unlink_sr_from_task":
            case "manually_link_sr_to_sr":
            case "manually_unlink_sr_from_sr":
                correlationId = EventsUtilities.getLogs("traceid", "externalLinkPayload").get(0);
                break;
            case "actionTaken":
                String actionTaken = "SENT_FOLLOW_UP_EMAIL_TO_MCE";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + actionTaken).get(0);
                break;
            case "actionTakenContactMember":
                status = "Contacted Member";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "actionTakenDiscussedWithState":
                status = "DISCUSSED_WITH_STATE";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "manually_link_case_to_task":
                eventValues = new ArrayList<>(EventsUtilities.getLogs("traceid", "internalRefType\\\":\\\"TASK\\\""));
                correlationId = eventValues.get(0);
                break;
            case "manually_unlink_case_from_task":
                eventValues = new ArrayList<>(EventsUtilities.getLogs("traceid", "internalRefType\\\":\\\"TASK\\\""));
                correlationId = eventValues.get(4);
                break;
            case "manually_link_consumer_to_task":
                correlationId = eventValues.get(2);
                break;
            case "manually_unlink_consumer_from_task":
                correlationId = eventValues.get(6);
                break;
            case "actionTakenProvideSummaryReport":
                status = "PROVIDE_SUMMARY_REPORT";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "escalatedReason":
                status = "CONSUMER_CONTACTING_STATE";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "actionTakenSentApprovalLetter":
                status = "SENT_APPROVAL_LETTER";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "manually_link_case_to_task_with_SR":
                eventValues = new ArrayList<>(EventsUtilities.getLogs("traceid", "internalRefType\\\":\\\"SERVICE_REQUEST\\\""));
                correlationId = eventValues.get(0);
                break;
            case "disposition_NJ":
                status="CONSUMER_NOT_REACHED";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "disposition_IN-EB":
                status="SUCCESSFULLY_REACHED_CONSUMER";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "disposition_abandoned":
                status="ABANDONED";
                correlationId = EventsUtilities.getLogs("traceid", "selectionVarchar\\\":\\\"" + status).get(0);
                break;
            case "TASK_SAVE_EVENT":
            case "LINK_EVENT":
            case "TASK_UPDATE_EVENT":
                correlationId = getCorrelationIdByTaskId(traceId);
                break;
            default:
                correlationId = EventsUtilities.getLogs("traceid", correlationId).get(0);
                break;
        }
        return correlationId;
    }

    public void i_initiated_event_get_api1(String traceId) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventEndPoint.replace("correlation_id",getCorrelationId(traceId)));
    }

    public void i_initiated_event_get_api(String traceId) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if(correlationId.isEmpty())
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventEndPoint.replace("correlation_id",getCorrelationId(traceId)));
        else
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventEndPoint.replace("correlation_id",correlationId));
    }

    public ArrayList<JSONObject> i_run_the_event_get_api_and_the_payload1() throws Exception {
        ArrayList<JSONObject> listofTemps = new ArrayList<>();
        Thread.sleep(3000);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");
        getListOfEventIds().clear();
        getListOfEventNames().clear();
        for (int i = 0; i < json.size(); i++) {
            listofTemps.add(new JSONObject(new JSONObject(json.get(i).toString()).get("payload").toString()));
            getListOfEventIds().add(new JSONObject(json.get(i).toString()).get("eventId").toString());
            getListOfEventNames().add(new JSONObject(json.get(i).toString()).get("eventName").toString());
        }
        return listofTemps;
    }

    public void i_initiated_Subscribers_post_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);
    }

    public void i_can_search_project_by(String subscriberName) {
        requestParams = new JsonObject();
        requestParams.addProperty("subscriberName", subscriberName);
    }

    public void i_run_the_Subscribers_post_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        subscriberId = new JSONObject(json.get(0).toString()).get("subscriberId").toString();
        subscriberList = new JSONArray(new JSONObject(json.get(0).toString()).get("subscriberList").toString());
    }

    public void verifyResponseHasEventNameAndMappingId(String eventName) {
        boolean flag = false;
        for (int i = 0; i < subscriberList.length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get(i).toString());
            if (temp.get("eventName").toString().trim().equals(eventName.trim())) {
                assertEquals(temp.get("eventName").toString().trim(), eventName, "Event Name is wrong");
                assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                flag = true;
                break;
            }
        }
        assertTrue(flag, eventName + " is not published to DBPI");
    }

    public void initiatedSubscribersRecordGETAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint.replace("{subscriberId}",subscriberId).replace("{eventId}",eventId));
    }

    public void runTheSubscribersRecordGETAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    public void verifyResponse(String eventName) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        assertEquals(temp.getString("eventName"), eventName);
        assertEquals(temp.getInt("eventId") + "", eventId);
        assertEquals(temp.getInt("subscriberId") + "", subscriberId);
    }

    public String getCorrelationIdByTaskId( String eventName) {
        ArrayList<String> listCorrelationIds = new ArrayList<>();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiEventsURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/events?size=1000&page=0&sort=eventId,desc");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(jsonObject);
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        for (int i = 0; i < json.size(); i++) {
            if (json.get(i).toString().contains(CRM_TaskManagementStepDef.taskId.get())) {
                listCorrelationIds.add(json.get(i).getAsJsonObject().get("correlationId").getAsString());
            }
        }
        return listCorrelationIds.get(0);
    }
}
