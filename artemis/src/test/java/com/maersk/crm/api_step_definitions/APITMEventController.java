package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.step_definitions.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class APITMEventController extends CRMUtilities implements ApiBase {
    ArrayList<String> values = null;
    APITaskManagementController taskType = new APITaskManagementController();
    CRM_TaskManagementStepDef myTask = new CRM_TaskManagementStepDef();
    TM_AddNewUserToProjectStepDef addUser = new TM_AddNewUserToProjectStepDef();
    APITMUserRestController apitmUserRestController = new APITMUserRestController();

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> facilityName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> facilityType = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> eventId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> eventName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> action = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> recordType = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> projectId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> projectName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> eventCreatedOn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> dataObject = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<JSONObject> payloadObject = ThreadLocal.withInitial(JSONObject::new);
    public final ThreadLocal<JSONObject> payloadObject1 = ThreadLocal.withInitial(JSONObject::new);
    public final ThreadLocal<String> subscriberId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<JSONArray> subscriberList = ThreadLocal.withInitial(JSONArray::new);
    public final ThreadLocal<String> status = ThreadLocal.withInitial(() -> "success");
    public final ThreadLocal<String> mapId = ThreadLocal.withInitial(String::new);
    private String baseURI = ConfigurationReader.getProperty("apiEventsURI");
    private final ThreadLocal<String> eventEndPoint = ThreadLocal.withInitial(String::new);
    private String eventEndPoint1 = "/app/crm/event/correlation/";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    Api_Storage stg = Api_Storage.getInstance();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    public static boolean taskToCase = false, taskToConsumer = false, taskToCR = false;
    public List<String> eventIds = new ArrayList<>();
    private final ThreadLocal<String> eventId1 = ThreadLocal.withInitial(String::new);
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";

    @Given("I initiated Event GET API")
    public void i_initiated_event_post_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventEndPoint.get());
    }

    @And("I will get {string} from traceId and pass it as end point")
    public void iWillGetCorrelationIdAsTraceIdAndPassItAsEndPoint(String correlationId) {

        if (correlationId.equals("UNLINKCRToCase")) {
            eventName.set("UNLINK");
            values = new ArrayList<>(EventsUtilities.getLogs("traceid",
                    "effectiveEndDate\\\":\\\""));
            correlationId = values.get(1);
        } else if (correlationId.equals("UNLINKCRToConsumer")) {
            eventName.set("UNLINK");
            correlationId = values.get(0);
        } else if (correlationId.equals("CRToCase")) {
            eventName.set("LINK");
            values = new ArrayList<>(EventsUtilities.getLogs("traceid",
                    "internalRefType\\\":\\\"contact_record\\\""));
            correlationId = values.get(0);
        } else if (correlationId.equals("CRToConsumer")) {
            eventName.set("LINK");
            correlationId = values.get(1);
        } else if (correlationId.equals("consumerAuthenticatedInd")) {
            String caseId = CRM_ViewContactRecordHistoryStepDef.consumerId.get();
            correlationId = EventsUtilities.getLogs("traceid",
                    "\\\"authenticationRefId\\\":" + caseId + ",").get(0);
        } else if (correlationId.equals("Task_update")) {
            String status = myTask.taskStatus.get();
            System.out.println("taskStatus\\\":\\\"" + status);
            correlationId = EventsUtilities.getLogs("traceid",
                    "taskStatus\\\":\\\"" + status).get(0);
        } else if (correlationId.equals("Created")) {
            String status = "Created";
            System.out.println("taskStatus\\\":\\\"" + status);
            correlationId = EventsUtilities.getLogs("traceid",
                    "taskStatus\\\":\\\"" + status).get(0);
        } else if (correlationId.equals("StaffUpdate")) {
            String status = "true";
            correlationId = EventsUtilities.getLogs("traceid",
                    "inactivateImmediately\\\":" + status).get(0);
        } else if (correlationId.equals("traceId")) {
            correlationId = taskType.traceId.get();
        } else if (correlationId.equals("traceId1")) {
            correlationId = taskType.traceId1.get();
        } else if (correlationId.equals("cookie")) {
            correlationId = EventsUtilities.getLogs("cookie",
                    "https://mars-tenant-manager-ui-qa.apps.non-prod.pcf-maersk.com/terms").get(0).toString();
            System.out.println("Printing onelogin jwt" + correlationId);
        } else if (correlationId.equals("onelogin-jwt")) {
            List<String> list = EventsUtilities.getRawLogs("onelogin-jwt");
            int i;
            //System.out.print(list);
            for (i = 0; i < list.size(); i++) {
                String str = list.get(i);
                if (str.contains("\":method\":\"POST"))
                    break;
            }
            correlationId = list.get(i).toString();
            EventsUtilities.parseOneloginJWT(correlationId);
        } else if (correlationId.equals("onelogin-jwt-GET")) {
            List<String> list = EventsUtilities.getRawLogs("onelogin-jwt");
            int i;
            System.out.print(list);
            for (i = 0; i < list.size(); i++) {
                String str = list.get(i);
                if (str.contains("\":method\":\"GET"))
                    break;
            }
            correlationId = list.get(i).toString();
            EventsUtilities.parseOneloginJWT(correlationId);
        }


//        else if (correlationId.equals("onelogin-jwt")) {
//            correlationId = EventsUtilities.getRawLogs("onelogin-jwt").get(0).toString();
//            System.out.println("Printing onelogin jwt :" + correlationId);
//            EventsUtilities.parseOneloginJWT(correlationId);
//        }
        else if (correlationId.equals("Task_update_Link_Unlink")) {
            String editReason = "CORRECTED_DATA_ENTRY";
            System.out.println("editReason\\\":\\\"" + editReason);
            correlationId = EventsUtilities.getLogs("traceid",
                    "editReason\\\":\\\"" + editReason).get(0);
        } else {
            correlationId = EventsUtilities.getLogs("traceid", correlationId).get(0);
        }
        this.correlationId.set(correlationId);
        eventEndPoint.set(eventEndPoint1 + correlationId);
    }


    @And("I run the Event GET API and get the payload")
    public ThreadLocal<JSONObject> i_run_the_event_get_api_and_the_payload() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject/*.getAsJsonObject("events")*/.getAsJsonArray("events");

        if (eventName.equals("LINK") || eventName.equals("UNLINK")) {
            assertTrue(json.size() == 2);
            payloadObject1.set(new JSONObject(new JSONObject(json.get(1).toString()).get("payload").toString()));
        }

        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));

        eventName.set(new JSONObject(json.get(0).toString()).get("eventName").toString());
        correlationId.set(new JSONObject(json.get(0).toString()).getString("correlationId").toString());
        eventId.set(new JSONObject(json.get(0).toString()).get("eventId").toString());
        action.set(payloadObject.get().getString("action"));
        recordType.set(payloadObject.get().getString("recordType"));
        eventCreatedOn.set(payloadObject.get().getString("eventCreatedOn"));
        projectId.set(payloadObject.get().getInt("projectId") + "");
        projectName.set(payloadObject.get().getString("projectName"));
        dataObject.set(payloadObject.get().getJSONObject("dataObject").toString());

        return payloadObject;
    }

    @And("I will run the Event GET API to get the payload and verify facility name {string} and facility type {string}")
    public ThreadLocal<JSONObject> i_will_run_the_event_get_api_to_the_payload(String facilityName, String facilityType) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("response for event" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject/*.getJSONObject("events")*/.getAsJsonArray("events");
        payloadObject1.set(new JSONObject(new JSONObject(json.toString())));
        System.out.println(payloadObject1.get());

        if (eventName.equals("CONTACT_RECORD_SAVE_EVENT") || eventName.equals("CONTACT_RECORD_UPDATE_EVENT")) {
            assertTrue(json.size() == 2);
            payloadObject.set(new JSONObject(new JSONObject(json.toString())));
            Assert.assertEquals(payloadObject.get().getString("facilityName"), null);
            System.out.println(payloadObject.get().getString("facilityName"));
            Assert.assertEquals(payloadObject.get().getString("facilityType"), facilityType);
        }

        Assert.assertEquals(payloadObject1.get().getString("facilityName"), null);
        System.out.println(payloadObject1.get().getString("facilityName"));
        Assert.assertEquals(payloadObject1.get().getString("facilityType"), facilityType);


        return payloadObject;

    }

    @Then("I will verify facilityName {string} and facilityType {string} fields in {string} payload")
    public void iWillVerifyCreatedOnCreatedByUpdatedOnUpdatedByFieldsInPayload(String facilityName, String facilityType, String eventName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        if (eventName.equals("CONTACT_RECORD_SAVE_EVENT") || eventName.equals("CONTACT_RECORD_UPDATE_EVENT")) {
            HashMap eventsData = (HashMap) eventsContent.get(0);
            System.out.println(eventsData.get("payload"));
            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertTrue(dataObjectData.get("createdBy").getAsString().equalsIgnoreCase(dataObjectData.get("updatedBy").getAsString()));


        }
    }

    @And("I run the Event GET API and get the payload for eventName {string} and verify the payload")
    public ThreadLocal<JSONObject> i_run_the_event_get_api_and_the_payload_for_contact_record(String eventName1) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject/*.getJSONObject("events")*/.getAsJsonArray("events");
        JsonArray json1 = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");
        if (eventName.equals(eventName1) || eventName.equals("CONTACT_RECORD_SAVE_EVENT") || eventName.equals("CONTACT_RECORD_UPDATE_EVENT")) {
            assertTrue(json.size() == 2);
            payloadObject1.set(new JSONObject(new JSONObject(json.get(1).toString()).get("payload").toString()));
        }

        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));

        eventName.set(new JSONObject(json.get(0).toString()).get("eventName").toString());
        System.out.println("jeventName = " + eventName.get());
        dataObject.set(payloadObject.get().getString("dataObject"));
        //Assert.assertEquals(eventName,"CONTACT_RECORD_SAVE_EVENT");
        Assert.assertTrue(eventName.get().contains("CONTACT_RECORD"));

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
            System.out.println("dataObjectData = " + dataObjectData);
            System.out.println("FacilityName: " + dataObjectData.get("facilityName").toString());
            System.out.println("FacilityType: " + dataObjectData.get("facilityType").toString());
            assertTrue(dataObjectData.toString().contains("facilityName"));
            assertTrue(dataObjectData.toString().contains("facilityType"));
            Assert.assertEquals(dataObjectData.get("facilityName").getAsString(), "Facility-Other", "incorrect result: " + dataObjectData.get("facilityName").getAsString());
        }

        return payloadObject;
    }

    @Then("I verify that payload contains facilityNAME {string} and FacilityType {string}")
    public void i_verify_that_payload_contains_facilityType_and_FacilityName(String facilityNAME, String facilityType) {
        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
            System.out.println("dataObjectData = " + dataObjectData);
            System.out.println("FacilityName: " + dataObjectData.get("facilityName").toString());
            System.out.println("FacilityType: " + dataObjectData.get("facilityType").toString());
            assertTrue(dataObjectData.toString().contains("facilityName"));
            assertTrue(dataObjectData.toString().contains("facilityType"));
            Assert.assertEquals(dataObjectData.get("facilityName").getAsString(), facilityNAME, "incorrect result: " + dataObjectData.get("facilityName").toString());
            Assert.assertEquals(dataObjectData.get("facilityType").getAsString(), facilityType, "incorrect result: " + dataObjectData.get("facilityType").toString());
        }
    }

    @Then("I verify that payload contains event details for plan update")
    public void i_verify_that_payload_contains_event_details_for_plan_update() {
        System.out.println("Printing Project name" + payloadObject.get().getString("projectName"));
        assertEquals(payloadObject.get().getString("projectName"), "BLCRM");
        assertEquals(payloadObject.get().getString("action"), action.get());
        assertEquals(payloadObject.get().getString("recordType"), recordType.get());
        assertEquals(payloadObject.get().getString("dataObject"), dataObject.get());
    }

    @Then("I verify that payload contains business unit details which I recently created")
    public void verifyBUPayloadData() {
        assertEquals(payloadObject.get().getString("projectName"), TM_SearchProjectStepDefs.projectname.get(),
                "Project Name is mismatch");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is mismatch");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a create");
        assertEquals(recordType.get().toLowerCase(), "business unit", "Record type is not a Business Unit");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("businessUnitId") + "").
                chars().allMatch(Character::isDigit), "Business Unit Id is mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("businessUnitName"),
                TM_AddBusinessUnitStepDef.businessUnitValue.get(), "Business unit name mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("description"),
                TM_AddBusinessUnitStepDef.descriptionValue.get(), "Description is mismatch");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("effectiveStartDate")), "effectiveStartDate date time field IS NOT valid");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("effectiveEndDate")), "effectiveEndDate date time field IS NOT valid");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("createdBy") + ""),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("updatedBy") + ""),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");

        /*taskType.initiateGetTaskTypeApi("");
        taskType.runGetTaskTypeApi();
        taskType.i_verify_Task_type_records_are_retrieved();*/
        //commented lines 163 164 tasks object not exist
        //System.out.println(payloadObject.get().getJSONObject("dataObject").getJSONObject("tasks").toString());
        //Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONObject("tasks").toString().contains(taskType.taskTypeId));
    }

    @Then("I verify that payload contains team details which I recently created")
    public void verifyTeamPayloadData() {
        assertEquals(payloadObject.get().getString("projectName"), TM_SearchProjectStepDefs.projectname.get(),
                "Project Name is mismatch");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is mismatch");
        Assert.assertEquals(action.get().toLowerCase(), "create", "Action is not a create");
        Assert.assertEquals(recordType.get().toLowerCase(), "team", "Record type is not a team");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()),
                "eventCreatedOn date time field IS NOT valid");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("teamId") + "").
                chars().allMatch(Character::isDigit), "Team Id is mismatch");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("businessUnitId") + "").
                chars().allMatch(Character::isDigit), "Business id is mismatch");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is mismatch");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("teamName"),
                TM_AddTeamStepDef.teamValue.get(), "Team mane is mismatch");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("description"),
                TM_AddTeamStepDef.teamDescription.get(), "Description is mismatch");
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("effectiveStartDate")), "effectiveStartDate date time field IS NOT valid");
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("effectiveEndDate")), "effectiveEndDate date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "CreatedOn date time field IS NOT valid");
    }

    //verify updateby and updateOn should be null ?
    @Then("I verify that payload contains team user details which I recently created")
    public void verifyTeamUserPayloadData() {
        Assert.assertFalse(payloadObject.get().getJSONObject("dataObject").getBoolean("supervisorFlag"));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("teamId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("userId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("teamUserId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Date currentDate = new Date();
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String actualDate = sdf.format(currentDate);
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"),
                actualDate);
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
//        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("updatedBy"));
//        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("updatedOn"));
        Assert.assertEquals(action.get().toLowerCase(), "create", "Action is not a create");
        Assert.assertEquals(recordType.get().toLowerCase(), "team user", "Record type is not a Team User");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
    }

    @Given("I initiated Subscribers POST API")
    public void i_initiated_Subscribers_post_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);
    }

    @And("I will provide the subscriber name as {string} in body")
    public void i_can_search_project_by(String subscriberName) {
        requestParams.set(new JsonObject());

        requestParams.get().addProperty("subscriberName", subscriberName);
        System.out.println(requestParams);
    }

    @And("I run the Subscribers POST API")
    public void i_run_the_Subscribers_post_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get().toString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        subscriberId.set(new JSONObject(json.get(0).toString()).get("subscriberId").toString());
        subscriberList.set(new JSONArray(new JSONObject(json.get(0).toString()).get("subscriberList").toString()));
    }

    @Then("I will check the response has event Subscriber Mapping Id for {string}")
    public void verifyResponseHasEventNameAndMappingId(String eventName) {
        boolean flag = false;
        for (int i = 0; i < subscriberList.get().length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get().get(i).toString());
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

    @And("I initiated Subscribers Record GET API")
    public void initiatedSubscribersRecordGETAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId.get() + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);
    }

    @And("I run the Subscribers Record GET API")
    public void runTheSubscribersRecordGETAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify response has event id and {string} and subscriberId")
    public void verifyResponse(String eventName) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        Assert.assertEquals(temp.getString("eventName"), eventName);
        Assert.assertEquals(temp.getInt("eventId") + "", eventId);
        Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId);
    }

    @Then("I verify two link event payloads has correct data")
    public void verifyLinkEventPayloads() {
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("createdOn")),
                "CreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("updatedOn")),
                "UpdatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").isNull("effectiveEndDate"));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("externalLinkId") + "").chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("internalId") + "").chars().allMatch(Character::isDigit));
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Assert.assertEquals(action.get().toLowerCase(), "create");
        Assert.assertEquals(recordType.get().toLowerCase(), "link");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "CreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        //As only correlationId once caputred outside the payload object
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId);
        System.out.println("Print date" + payloadObject1);
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getJSONObject("dataObject").
                getJSONObject("externalLinkPayload").getString("createdOn")), "CreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getJSONObject("dataObject").
                getJSONObject("externalLinkPayload").getString("updatedOn")), "UpdatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        Assert.assertTrue(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                isNull("effectiveEndDate"));
        Assert.assertTrue((payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("externalId") + "").chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("externalLinkId") + "").chars().allMatch(Character::isDigit));
        Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Assert.assertEquals(payloadObject1.get().getString("action"), "create");
        Assert.assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "CreatedOn date time field IS NOT valid");
        assertEquals(payloadObject1.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        //As only correlationId once caputred outside the payload object
        Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"), correlationId);

        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("CONTACT_RECORD")) {
            Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("internalRefType"), "CONTACT_RECORD");
            Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("externalRefType"), "CASE");
            Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("internalRefType"), "CASE");
            Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("externalRefType"), "CONTACT_RECORD");
            Assert.assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getInt("externalId") + ""), CRM_ContactRecordUIStepDef.caseId.get());
            Assert.assertEquals((payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getInt("internalId") + ""), CRM_ContactRecordUIStepDef.caseId.get());
        } else {
            Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("internalRefType"), "CONTACT_RECORD");
            Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("externalRefType"), "CASE");
            Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("internalRefType"), "CASE");
            Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("externalRefType"), "CONTACT_RECORD");
            Assert.assertEquals((payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getInt("externalId") + ""), CRM_ContactRecordUIStepDef.caseId.get());
            Assert.assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getInt("internalId") + ""), CRM_ContactRecordUIStepDef.caseId.get());
        }

    }

    @Then("I verify two unlink event payloads has correct data")
    public void verifyUnLinkEventPayloads() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("createdOn")),
                "CreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("updatedOn")),
                "UpdatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                isNull("effectiveStartDate"));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("externalId") + "").chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("externalLinkId") + "").chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("internalId") + "").chars().allMatch(Character::isDigit));
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Assert.assertEquals(action.get().toLowerCase(), "update");
        Assert.assertEquals(recordType.get().toLowerCase(), "link");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "CreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        //As only correlationId once caputred outside the payload object
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId);


        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("createdOn")),
                "CreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("updatedOn")),
                "UpdatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getJSONObject("dataObject").
                        getJSONObject("externalLinkPayload").getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        Assert.assertTrue(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                isNull("effectiveStartDate"));
        Assert.assertTrue((payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("externalId") + "").chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("externalLinkId") + "").chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getInt("internalId") + "").chars().allMatch(Character::isDigit));
        Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        Assert.assertEquals(payloadObject1.get().getString("action"), "update");
        Assert.assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "CreatedOn date time field IS NOT valid");
        assertEquals(payloadObject1.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        //As only correlationId once caputred outside the payload object
        Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"), correlationId);

        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("CONTACT_RECORD")) {
            Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("internalRefType"), "CONTACT_RECORD");
            Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("externalRefType"), "CASE");
            Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("internalRefType"), "CASE");
            Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("externalRefType"), "CONTACT_RECORD");
        } else {
            Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("internalRefType"), "CONTACT_RECORD");
            Assert.assertEquals(payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("externalRefType"), "CASE");
            Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("internalRefType"), "CASE");
            Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                    getString("externalRefType"), "CONTACT_RECORD");
        }

    }

    @Then("I verify consumer authentication event payloads has correct data")
    public void verifyConsumerAuthEventPayloads() {
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").
                getInt("authenticationRefId") + "", CRM_ViewContactRecordHistoryStepDef.consumerId.get());
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").
                getString("authenticationType"), "Consumer");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getBoolean("consumerAuthenticatedInd"));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").
                getInt("consumerAuthenticationId") + "").chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").
                getInt("contactRecordId") + "").chars().allMatch(Character::isDigit));
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId);
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        assertEquals(recordType.get().toLowerCase(), "consumer authentication");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "createdOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        Assert.assertEquals(action.get().toLowerCase(), "create");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("authentications").
                getJSONObject(0).getString("fieldName"), "0_caDob");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("authentications").
                getJSONObject(0).getInt("linkRefId") + "", CRM_ViewContactRecordHistoryStepDef.consumerId.get());
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("authentications").
                getJSONObject(0).getString("linkRefType"), "Agency");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("authentications").
                getJSONObject(0).getString("propertyName"), "caDob");

        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("authentications").
                getJSONObject(1).getString("fieldName"), "0_caSsn");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("authentications").
                getJSONObject(1).getInt("linkRefId") + "", CRM_ViewContactRecordHistoryStepDef.consumerId.get());
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("authentications").
                getJSONObject(1).getString("linkRefType"), "Agency");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("authentications").
                getJSONObject(1).getString("propertyName"), "caSsn");
    }

    @Then("I verify that payload contains team user details which I recently updated")
    public void verifyUpdeatedTeamUserPayloadData() {
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("teamId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("userId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("teamUserId") + "").
                chars().allMatch(Character::isDigit));
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        try {
            Date javaDate = sdfrmt.parse(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"));
            Assert.assertTrue(true);
        }
        /* Date format is invalid */ catch (ParseException e) {
            Assert.assertTrue(false, "effectiveStartDate is not in yyyy-MM-dd format");
        }

        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");

        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        Assert.assertNotNull(payloadObject.get().getJSONObject("dataObject").getBoolean("supervisorFlag"));

        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        Assert.assertEquals(action.get().toLowerCase(), "update", "Action is not a update");
        Assert.assertEquals(recordType.get().toLowerCase(), "team user", "Record type is not a team user");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
    }

    @Then("I verify response has created on and updated on in UTC format")
    public void verifyCreatedAndUpdatedOn() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        Assert.assertTrue(verifyDatetimeFormatIsUTC(temp.getString("createdOn")));
        Assert.assertTrue(verifyDatetimeFormatIsUTC(temp.getString("updatedOn")));
    }

    private boolean verifyDatetimeFormatIsUTC(String datetimeField) {
        boolean datetimeFormatCheck = false;
        DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(datetimeField, dtf);
            if (parsedDate != null)
                datetimeFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return datetimeFormatCheck;
    }

    @Then("I verify that payload contains edited business unit details which I recently created")
    public void verifyEditedBUPayloadData() {
        assertEquals(payloadObject.get().getString("projectName"), TM_SearchProjectStepDefs.projectname.get(),
                "Project Name is mismatch");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is mismatch");
        assertEquals(action.get().toLowerCase(), "update", "Update is not a create");
        assertEquals(recordType.get().toLowerCase(), "business unit", "Record type is not a Business Unit");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("businessUnitId") + "").
                chars().allMatch(Character::isDigit));
        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("businessUnitName").equalsIgnoreCase(
                TM_EditBusinessUnitStepDef.update_businessUnitValue.get()));
        assertEquals(payloadObject.get().getJSONObject("dataObject").getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("description"),
                TM_EditBusinessUnitStepDef.update_descriptionValue.get());
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("effectiveStartDate")), "effectiveStartDate date time field IS NOT valid");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("effectiveEndDate")), "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        //Assert.assertTrue(!payloadObject.get().getJSONObject("dataObject").getJSONObject("tasks").toString().isEmpty());
    }


    @Then("I verify that payload contains edited plan details which I recently created")
    public void verifyEditedPUPayloadData() {

        //Assert.assertEquals(payloadObject.get().getInt("projectId")+"",api.getProjectId());
        Assert.assertEquals(payloadObject.get().getInt("projectId") + "", "44");
    }

    @Then("I verify that payload contains project details which I recently created")
    public void verifyProjectDetailsPage() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "create", "Create is not a create");
        Assert.assertEquals(recordType.get().toLowerCase(), "project", "Record type is not a Project");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        if (!payloadObject.get().getJSONObject("dataObject").
                get("contractStartDate").toString().equalsIgnoreCase("null")) {
            Assert.assertTrue(apiClassUtil.verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                    getString("contractStartDate")), "contractStartDate date time field IS NOT valid");
        }
        if (!payloadObject.get().getJSONObject("dataObject").
                get("contractEndDate").toString().equalsIgnoreCase("null")) {
            Assert.assertTrue(apiClassUtil.verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                    getString("contractEndDate")), "contractEndDate date time field IS NOT valid");
        }

        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText1"), " dataObject has field genericFieldText1");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText2"), " dataObject has field genericFieldText2");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText3"), " dataObject has field genericFieldText3");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText4"), " dataObject has field genericFieldText4");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber1"), " dataObject has field genericFieldNumber1");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber2"), " dataObject has field genericFieldNumber2");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber3"), " dataObject has field genericFieldNumber3");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber4"), " dataObject has field genericFieldNumber4");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate1"), " dataObject has field genericFieldDate1");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate2"), " dataObject has field genericFieldDate2");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate3"), " dataObject has field genericFieldDate3");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate4"), " dataObject has field genericFieldDate4");

        LocalDate dateAfterNDays = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertTrue(apiClassUtil.verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("goLiveDate")) || payloadObject.get().getJSONObject("dataObject").isNull("goLiveDate"), "Go live date field is not valid");

        JSONArray temp = payloadObject.get().getJSONObject("dataObject").getJSONArray("projectStatus");
        for (int i = 0; i < temp.length(); i++) {
            JSONObject projestStatusElement = (JSONObject) temp.get(i);
            assertFalse(projestStatusElement.isNull("projectStatusId"), "Project Staus ID is Null");
            assertTrue(!projestStatusElement.isNull("projectId") || projestStatusElement.getString("projectId").equals(projectId), "Project Staus ID is not valid");
            assertFalse(projestStatusElement.isNull("provisioningStatus"), "Invalid provisioning status");
            Assert.assertTrue(EventsUtilities.isValidDate(projestStatusElement.getString("createdOn").toString()), "CreatedOn date time field IS NOT valid");
            Assert.assertTrue(EventsUtilities.isValidDate(projestStatusElement.getString("updatedOn").toString()), "updatedOn date time field IS NOT valid");
            Assert.assertFalse(projestStatusElement.isNull("createdBy"), "CreatedBy value is Null");
            Assert.assertFalse(projestStatusElement.isNull("updatedBy"), "UpdatedBy value is Null");
            Assert.assertTrue(EventsUtilities.isValidDate(projestStatusElement.getString("effectiveStartDate").toString()), "effectiveStartDate date time field IS NOT valid");
            Assert.assertTrue(!projestStatusElement.isNull("correlationId") || projestStatusElement.getString("correlationId").equals("correlationId"), "correlationId value is not valid");
            Assert.assertTrue(projestStatusElement.has("effectiveEndDate"), "effectiveEndDate field missing");

            assertFalse(projestStatusElement.has("genericFieldText1"), " projectStatus has field genericFieldText1");
            assertFalse(projestStatusElement.has("genericFieldText2"), " projectStatus has field genericFieldText2");
            assertFalse(projestStatusElement.has("genericFieldText3"), " projectStatus has field genericFieldText3");
            assertFalse(projestStatusElement.has("genericFieldText4"), " projectStatus has field genericFieldText4");
            assertFalse(projestStatusElement.has("genericFieldNumber1"), " projectStatus has field genericFieldNumber1");
            assertFalse(projestStatusElement.has("genericFieldNumber2"), " projectStatus has field genericFieldNumber2");
            assertFalse(projestStatusElement.has("genericFieldNumber3"), " projectStatus has field genericFieldNumber3");
            assertFalse(projestStatusElement.has("genericFieldNumber4"), " projectStatus has field genericFieldNumber4");
            assertFalse(projestStatusElement.has("genericFieldDate1"), " projectStatus has field genericFieldDate1");
            assertFalse(projestStatusElement.has("genericFieldDate2"), " projectStatus has field genericFieldDate2");
            assertFalse(projestStatusElement.has("genericFieldDate3"), " projectStatus has field genericFieldDate3");
            assertFalse(projestStatusElement.has("genericFieldDate4"), " projectStatus has field genericFieldDate4");
        }

    }

    @Then("I verify that payload contains project updated details which I recently created")
    public void verifyProjectUpdateDetailsPage() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "update", "Update is not a create");
        Assert.assertEquals(recordType.get().toLowerCase(), "project", "Record type is not a Project");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        if (!payloadObject.get().getJSONObject("dataObject").
                get("contractStartDate").toString().equalsIgnoreCase("null")) {
            Assert.assertTrue(apiClassUtil.verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                    getString("contractStartDate")), "contractStartDate date time field IS NOT valid");
        }
        if (!payloadObject.get().getJSONObject("dataObject").
                get("contractEndDate").toString().equalsIgnoreCase("null")) {
            Assert.assertTrue(apiClassUtil.verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                    getString("contractEndDate")), "contractEndDate date time field IS NOT valid");
        }

        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText1"), " dataObject has field genericFieldText1");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText2"), " dataObject has field genericFieldText2");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText3"), " dataObject has field genericFieldText3");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText4"), " dataObject has field genericFieldText4");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber1"), " dataObject has field genericFieldNumber1");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber2"), " dataObject has field genericFieldNumber2");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber3"), " dataObject has field genericFieldNumber3");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber4"), " dataObject has field genericFieldNumber4");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate1"), " dataObject has field genericFieldDate1");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate2"), " dataObject has field genericFieldDate2");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate3"), " dataObject has field genericFieldDate3");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate4"), " dataObject has field genericFieldDate4");

        LocalDate dateAfterNDays = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertTrue(apiClassUtil.verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("goLiveDate")) || payloadObject.get().getJSONObject("dataObject").isNull("goLiveDate"), "Go live date field is not valid");

        JSONArray temp = payloadObject.get().getJSONObject("dataObject").getJSONArray("projectStatus");
        for (int i = 0; i < temp.length(); i++) {
            JSONObject projestStatusElement = (JSONObject) temp.get(i);
            assertFalse(projestStatusElement.isNull("projectStatusId"), "Project Staus ID is Null");
            assertTrue(!projestStatusElement.isNull("projectId") || projestStatusElement.getString("projectId").equals(projectId), "Project Staus ID is not valid");
            assertFalse(projestStatusElement.isNull("provisioningStatus"), "Invalid provisioning status");
            Assert.assertTrue(EventsUtilities.isValidDate(projestStatusElement.getString("createdOn").toString()), "CreatedOn date time field IS NOT valid");
            Assert.assertTrue(EventsUtilities.isValidDate(projestStatusElement.getString("updatedOn").toString()), "updatedOn date time field IS NOT valid");
            Assert.assertFalse(projestStatusElement.isNull("createdBy"), "CreatedBy value is Null");
            Assert.assertFalse(projestStatusElement.isNull("updatedBy"), "UpdatedBy value is Null");
            Assert.assertTrue(EventsUtilities.isValidDate(projestStatusElement.getString("effectiveStartDate").toString()), "effectiveStartDate date time field IS NOT valid");
            Assert.assertTrue(!projestStatusElement.isNull("correlationId") || projestStatusElement.getString("correlationId").equals("correlationId"), "correlationId value is not valid");
            Assert.assertTrue(projestStatusElement.has("effectiveEndDate"), "effectiveEndDate field missing");

            assertFalse(projestStatusElement.has("genericFieldText1"), " projectStatus has field genericFieldText1");
            assertFalse(projestStatusElement.has("genericFieldText2"), " projectStatus has field genericFieldText2");
            assertFalse(projestStatusElement.has("genericFieldText3"), " projectStatus has field genericFieldText3");
            assertFalse(projestStatusElement.has("genericFieldText4"), " projectStatus has field genericFieldText4");
            assertFalse(projestStatusElement.has("genericFieldNumber1"), " projectStatus has field genericFieldNumber1");
            assertFalse(projestStatusElement.has("genericFieldNumber2"), " projectStatus has field genericFieldNumber2");
            assertFalse(projestStatusElement.has("genericFieldNumber3"), " projectStatus has field genericFieldNumber3");
            assertFalse(projestStatusElement.has("genericFieldNumber4"), " projectStatus has field genericFieldNumber4");
            assertFalse(projestStatusElement.has("genericFieldDate1"), " projectStatus has field genericFieldDate1");
            assertFalse(projestStatusElement.has("genericFieldDate2"), " projectStatus has field genericFieldDate2");
            assertFalse(projestStatusElement.has("genericFieldDate3"), " projectStatus has field genericFieldDate3");
            assertFalse(projestStatusElement.has("genericFieldDate4"), " projectStatus has field genericFieldDate4");
        }

    }

    @Then("I verify that payload contains user details which I recently created")
    public void verifyUserDetailsPage() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "create", "create is not a create");
        Assert.assertEquals(recordType.get().toLowerCase(), "staff", "Record type is not a Staff");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("Regression"), "Project Name is not correct");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        Assert.assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        assertEquals(temp.getString("firstName"), addUser.getFirstName());
        assertEquals(temp.getString("lastName"), addUser.getLastName());
        Assert.assertTrue(temp.isNull("middleName"), "middle name is not null");
        assertEquals(temp.getString("ismaerskInternalEmployee"), "Yes", "ismaerskInternalEmployee is miss match");
        assertEquals(temp.getString("email"), addUser.getEmail(), "email is not correct");
        assertEquals(temp.getString("maxId"), addUser.getMaxID(), "maxId is not correct");
        Assert.assertFalse(temp.isNull("correlationId"), "correlationId is null");
        Assert.assertTrue(temp.isNull("password"), "password is not null");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"), "createdOn and updatedOn is miss match");
        assertEquals(temp.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        Assert.assertTrue(temp.isNull("uiid"), "uiid is not null");
        Assert.assertTrue(temp.isNull("startDate"), "startDate is not null");
        Assert.assertTrue(temp.isNull("endDate"), "endDate is not null");
    }

    @Then("I verify the user details payload after user creation")
    public void i_verify_the_user_details_payload_after_user_creation() throws Throwable {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("Regression"), "Project Name is not correct");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a create");

        assertEquals(recordType.get().toLowerCase(), "user", "Record type is not a User");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");
        assertEquals(payloadObject.get().getString("projectId"), temp.getString("projectId"), "Project Id is not correct");
        assertTrue(temp.getInt("userId") != 0 && !temp.isNull("userId"), "userId is null or equal to 0");
        assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        assertEquals(temp.getString("accountType"), "Individual - maersk", "Account type mismatch");
        assertEquals(temp.getString("applicationType"), "ConnectionPoint", "Application type mismatch");

        assertTrue(temp.isNull("accountAuthorization"), "accountAuthorization is not null");
        assertTrue(apiClassUtil.verifyDateFormat(temp.getString("accountAuthorizationdate")), "accountAuthorizationdate date time field is not valid");
        assertEquals(temp.getString("overrideAuthorization"), "No", "Override Authorization field mismatch");
        assertTrue(temp.isNull("overrideAuthorizationReason"), "override Authorization Reason field mismatch");
        assertEquals(temp.getString("requirePHIAccess"), "No", "Require PHI Access field value mismatch");
        assertTrue(temp.isNull("reasonPHIAccess"), "Reason PHI Access field value mismatch");
        assertEquals(temp.getString("requirePIIAccess"), "No", "Require PII Access field value mismatch");
        assertTrue(temp.isNull("reasonPIIAccess"), "Reason PII Access field value mismatch");
        assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        assertTrue(temp.isNull("inactivateReason"), "Inactivate Reason field mismatch");
        assertFalse(temp.isNull("inactivateImmediately"), "Inactivate Immediately field value mismatch");

        assertTrue(apiClassUtil.verifyDateFormat(temp.get("startDate").toString()) || temp.isNull("startDate"), "start date time field is not valid");
        assertTrue(apiClassUtil.verifyDateFormat(temp.get("endDate").toString()) || temp.isNull("endDate"), "end date time field is not valid");
        assertEquals(temp.getString("userStatus"), "ACTIVE", "User Status mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn field date is not valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn field date is not valid");
        assertEquals(temp.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");

        JSONArray userRolePayload = temp.getJSONArray("userProjectRole");
        assertFalse(userRolePayload.length() == 0, "No user roles assigned to User");
        JSONObject userRoleElement = (JSONObject) userRolePayload.get(0);
        Assert.assertTrue(userRoleElement.getInt("userProjectRoleId") != 0 && userRoleElement.getInt("userProjectRoleId") != 0, "USER ROlE ID is null");
        Assert.assertTrue(userRoleElement.getInt("userId") != 0 && !userRoleElement.isNull("userId"), "userId in UserRolePayload is null or equal to 0");
        Assert.assertEquals(userRoleElement.get("userId"), temp.getInt("userId"), "UserId unmatched");
        Assert.assertTrue(userRoleElement.getInt("projectRoleId") != 0 && !userRoleElement.isNull("projectRoleId"), "userId in UserRolePayload is null or equal to 0");
        assertTrue(apiClassUtil.verifyDateFormat(userRoleElement.get("startDate").toString()) || temp.isNull("startDate"), "start date time field is not valid");
        assertTrue(apiClassUtil.verifyDateFormat(userRoleElement.get("endDate").toString()) || temp.isNull("endDate"), "end date time field is not valid");
        assertFalse(userRoleElement.isNull("isDefault"), "Is default value is null");
        assertTrue(EventsUtilities.isValidDate(userRoleElement.getString("createdOn")), "createdOn field date is not valid");
        assertTrue(EventsUtilities.isValidDate(userRoleElement.getString("updatedOn")), "updatedOn field date is not valid");
        assertEquals(userRoleElement.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(userRoleElement.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        Assert.assertFalse(userRoleElement.isNull("correlationId"), "correlationId is null");
    }

    @Then("I verify the role details payload after role {string}")
    public void i_verify_the_role_details_payload_after_role_something(String action) throws Throwable {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("Regression"), "Project Name is not correct");
        assertTrue(payloadObject.get().getString("action").equalsIgnoreCase(action), "Action is not a correct");

        assertEquals(recordType.get().toLowerCase(), "project role", "Record type is not a User");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");
        assertEquals(payloadObject.get().getString("projectId"), temp.getString("projectId"), "Project Id is not correct");
        assertTrue(temp.getInt("projectRoleId") != 0 && !temp.isNull("projectRoleId"), "userId is null or equal to 0");
        assertFalse(temp.isNull("roleName"), "roleName field empty");
        assertFalse(temp.isNull("activeInd"), "roleName field empty");
        assertFalse(temp.isNull("roleDesc"), "roleName field empty");
        assertTrue(temp.get("roleStatus").equals("Active") || temp.get("roleStatus").equals("Inactive"), "Incorrect roleStatus value");

        assertTrue(apiClassUtil.verifyDateFormat(temp.get("startDate").toString()) || temp.isNull("startDate"), "start date time field is not valid");
        assertTrue(apiClassUtil.verifyDateFormat(temp.get("endDate").toString()) || temp.isNull("endDate"), "end date time field is not valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn field date is not valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn field date is not valid");
        assertEquals(temp.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        assertFalse(temp.has("genericFieldText1"), " payload has field genericFieldText1");
        assertFalse(temp.has("genericFieldText2"), " payload has field genericFieldText2");
        assertFalse(temp.has("genericFieldText3"), " payload has field genericFieldText3");
        assertFalse(temp.has("genericFieldText4"), " payload has field genericFieldText4");
        assertFalse(temp.has("genericFieldNumber1"), " payload has field genericFieldNumber1");
        assertFalse(temp.has("genericFieldNumber2"), " payload has field genericFieldNumber2");
        assertFalse(temp.has("genericFieldNumber3"), " payload has field genericFieldNumber3");
        assertFalse(temp.has("genericFieldNumber4"), " payload has field genericFieldNumber4");
        assertFalse(temp.has("genericFieldDate1"), " payload has field genericFieldDate1");
        assertFalse(temp.has("genericFieldDate2"), " payload has field genericFieldDate2");
        assertFalse(temp.has("genericFieldDate3"), " payload has field genericFieldDate3");
        assertFalse(temp.has("genericFieldDate4"), " payload has field genericFieldDate4");

    }

    @Then("I verify the user details payload after user updation")
    public void i_verify_the_user_details_payload_after_user_updation() throws Throwable {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("Regression"), "Project Name is not correct");
        assertEquals(action.get().toLowerCase(), "update", "Action is not a create");

        assertEquals(recordType.get().toLowerCase(), "user", "Record type is not a User");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");
        assertEquals(payloadObject.get().getString("projectId"), temp.getString("projectId"), "Project Id is not correct");
        assertTrue(temp.getInt("userId") != 0 && !temp.isNull("userId"), "userId is null or equal to 0");
        assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        assertEquals(temp.getString("accountType"), "Individual - maersk", "Account type mismatch");
        assertEquals(temp.getString("applicationType"), "ConnectionPoint", "Application type mismatch");

        assertTrue(temp.isNull("accountAuthorization"), "accountAuthorization is not null");
        assertTrue(apiClassUtil.verifyDateFormat(temp.getString("accountAuthorizationdate")), "accountAuthorizationdate date time field is not valid");
        assertEquals(temp.getString("overrideAuthorization"), "No", "Override Authorization field mismatch");
        assertTrue(temp.isNull("overrideAuthorizationReason"), "override Authorization Reason field mismatch");
        assertEquals(temp.getString("requirePHIAccess"), "No", "Require PHI Access field value mismatch");
        assertTrue(temp.isNull("reasonPHIAccess"), "Reason PHI Access field value mismatch");
        assertEquals(temp.getString("requirePIIAccess"), "No", "Require PII Access field value mismatch");
        assertTrue(temp.isNull("reasonPIIAccess"), "Reason PII Access field value mismatch");
        assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        assertTrue(temp.isNull("inactivateReason"), "Inactivate Reason field mismatch");
        assertFalse(temp.isNull("inactivateImmediately"), "Inactivate Immediately field value mismatch");

        assertTrue(apiClassUtil.verifyDateFormat(temp.get("startDate").toString()) || temp.isNull("startDate"), "start date time field is not valid");
        assertTrue(apiClassUtil.verifyDateFormat(temp.get("endDate").toString()) || temp.isNull("endDate"), "end date time field is not valid");
        assertEquals(temp.getString("userStatus"), "ACTIVE", "User Status mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn field date is not valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn field date is not valid");
        assertEquals(temp.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");

        JSONArray userRolePayload = temp.getJSONArray("userProjectRole");
        assertFalse(userRolePayload.length() == 0, "No user roles assigned to User");
        JSONObject userRoleElement = (JSONObject) userRolePayload.get(0);
        Assert.assertTrue(userRoleElement.getInt("userProjectRoleId") != 0 && userRoleElement.getInt("userProjectRoleId") != 0, "USER ROlE ID is null");
        Assert.assertTrue(userRoleElement.getInt("userId") != 0 && !userRoleElement.isNull("userId"), "userId in UserRolePayload is null or equal to 0");
        Assert.assertEquals(userRoleElement.get("userId"), temp.getInt("userId"), "UserId unmatched");
        Assert.assertTrue(userRoleElement.getInt("projectRoleId") != 0 && !userRoleElement.isNull("projectRoleId"), "userId in UserRolePayload is null or equal to 0");
        assertTrue(apiClassUtil.verifyDateFormat(userRoleElement.get("startDate").toString()) || userRoleElement.isNull("startDate"), "start date time field is not valid");
        assertTrue(apiClassUtil.verifyDateFormat(userRoleElement.get("endDate").toString()) || userRoleElement.isNull("endDate"), "end date time field is not valid");
        assertFalse(userRoleElement.isNull("isDefault"), "Is default value is null");
        assertTrue(EventsUtilities.isValidDate(userRoleElement.getString("createdOn")), "createdOn field date is not valid");
        assertTrue(EventsUtilities.isValidDate(userRoleElement.getString("updatedOn")), "updatedOn field date is not valid");
        assertFalse(userRoleElement.isNull("createdBy"), "createdBy is Null");
        assertFalse(userRoleElement.isNull("updatedBy"), "updatedBy is Null");
        Assert.assertFalse(userRoleElement.isNull("correlationId"), "correlationId is null");
    }

    @Then("I verify the user details payload after user updation for values")
    public void i_verify_the_user_details_payload_after_user_updation_for_values(Map<String, String> data) {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("Regression"), "Project Name is not correct");
        assertEquals(action.get().toLowerCase(), "update", "Action is not a create");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");
        assertEquals(payloadObject.get().getString("projectId"), temp.getString("projectId"), "Project Id is not correct");
        assertTrue(temp.getInt("userId") != 0 && !temp.isNull("userId"), "userId is null or equal to 0");
        assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        assertEquals(temp.getString("accountType"), "Individual - maersk", "Account type mismatch");
        assertEquals(temp.getString("applicationType"), "ConnectionPoint", "Application type mismatch");
        assertTrue(temp.isNull("accountAuthorization"), "accountAuthorization is not null");
        assertTrue(apiClassUtil.verifyDateFormat(temp.getString("accountAuthorizationdate")), "accountAuthorizationdate date time field is not valid");
        assertEquals(temp.getString("overrideAuthorization"), "No", "Override Authorization field mismatch");
        assertTrue(temp.isNull("overrideAuthorizationReason"), "override Authorization Reason field mismatch");
        assertEquals(temp.getString("requirePHIAccess"), "No", "Require PHI Access field value mismatch");
        assertTrue(temp.isNull("reasonPHIAccess"), "Reason PHI Access field value mismatch");
        assertEquals(temp.getString("requirePIIAccess"), "No", "Require PII Access field value mismatch");
        assertTrue(temp.isNull("reasonPIIAccess"), "Reason PII Access field value mismatch");
        assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        for (String key : data.keySet()) {
            if (data.get(key).equals("true")) {
                assertTrue(temp.getBoolean("inactivateImmediately"), "Inactivate Immediately field value mismatch");
                break;
            }
            if (data.get(key).equals("false")) {
                assertFalse(temp.getBoolean("inactivateImmediately"), "Inactivate Immediately field value mismatch");
                break;
            }
            assertEquals(temp.getString(key), data.get(key), "value mismatch");
        }
        assertTrue(apiClassUtil.verifyDateFormat(temp.get("startDate").toString()) || temp.isNull("startDate"), "start date time field is not valid");
        assertTrue(apiClassUtil.verifyDateFormat(temp.get("endDate").toString()) || temp.isNull("endDate"), "end date time field is not valid");
        if (temp.getBoolean("inactivateImmediately")) {
            assertEquals(temp.getString("userStatus"), "INACTIVE", "User Status mismatch");
        } else
            assertEquals(temp.getString("userStatus"), "ACTIVE", "User Status mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn field date is not valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn field date is not valid");
        assertEquals(temp.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");

        JSONArray userRolePayload = temp.getJSONArray("userProjectRole");
        assertFalse(userRolePayload.length() == 0, "No user roles assigned to User");
        JSONObject userRoleElement = (JSONObject) userRolePayload.get(0);
        Assert.assertTrue(userRoleElement.getInt("userProjectRoleId") != 0 && userRoleElement.getInt("userProjectRoleId") != 0, "USER ROlE ID is null");
        Assert.assertTrue(userRoleElement.getInt("userId") != 0 && !userRoleElement.isNull("userId"), "userId in UserRolePayload is null or equal to 0");
        Assert.assertEquals(userRoleElement.get("userId"), temp.getInt("userId"), "UserId unmatched");
        Assert.assertTrue(userRoleElement.getInt("projectRoleId") != 0 && !userRoleElement.isNull("projectRoleId"), "userId in UserRolePayload is null or equal to 0");
        assertTrue(userRoleElement.isNull("startDate") || apiClassUtil.verifyDateFormat(userRoleElement.get("startDate").toString()), "start date time field is not valid");
        assertTrue(userRoleElement.isNull("endDate") || apiClassUtil.verifyDateFormat(userRoleElement.get("endDate").toString()), "end date time field is not valid");
        assertFalse(userRoleElement.isNull("isDefault"), "Is default value is null");
        assertTrue(EventsUtilities.isValidDate(userRoleElement.getString("createdOn")), "createdOn field date is not valid");
        assertTrue(EventsUtilities.isValidDate(userRoleElement.getString("updatedOn")), "updatedOn field date is not valid");
        assertFalse(userRoleElement.isNull("createdBy"), "createdBy is Null");
        assertFalse(userRoleElement.isNull("updatedBy"), "updatedBy is Null");
        Assert.assertFalse(userRoleElement.isNull("correlationId"), "correlationId is null");
    }

    @Then("I verify that payload contains user update details which I recently created")
    public void verifyUserUpdateDetailsPage() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "update", "Action is not update");
        Assert.assertEquals(recordType.get().toLowerCase(), "staff", "Record type is not a Staff");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("Regression"), "Project Name is not correct");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        Assert.assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        assertEquals(temp.getString("firstName"), addUser.getFirstName());
        assertEquals(temp.getString("lastName"), addUser.getLastName());
        temp.has("middleName");
        assertEquals(temp.getString("ismaerskInternalEmployee"), "Yes", "ismaerskInternalEmployee is miss match");
        assertEquals(temp.getString("email"), addUser.getEmail(), "email is not correct");
        assertEquals(temp.getString("maxId"), addUser.getMaxID(), "maxId is not correct");
        Assert.assertFalse(temp.isNull("correlationId"), "correlationId is null");
        Assert.assertTrue(temp.isNull("password"), "password is not null");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
        Assert.assertFalse(temp.getString("createdOn").equalsIgnoreCase(temp.getString("updatedOn")), "createdOn and updatedOn are same");
        assertEquals(temp.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        Assert.assertTrue(temp.isNull("uiid"), "uiid is not null");
        Assert.assertTrue(temp.isNull("startDate"), "startDate is not null");
        Assert.assertTrue(temp.isNull("endDate"), "endDate is not null");
    }

    /* Code to get projectStatusID for project Update and Save Event
     *  Author : Paramita  Date : 14/01/2020 */

    @Then("I verify that payload contains projectStatusId details on Project Update or Save Event")
    public void verify_payload_contains_projectStatusId_details() {
        Assert.assertFalse(payloadObject.get().getJSONObject("dataObject").getJSONObject("projectStatus").isNull("projectStatusId"));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONObject("projectStatus").getInt("projectStatusId") + "").chars().allMatch(Character::isDigit));
    }

    @Then("I verify task event payloads has correct data")
    public void verifyTasskCreatedPayload() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "create", "Action is not a create");
        Assert.assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getString("projectName"), "BLCRM", "Project Name is not a BLCRM");
        Assert.assertEquals(payloadObject.get().getInt("projectId"), 44, "Project Id is not 44");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("defaultDueDate")), "defaultDueDate date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("statusDate")), "statusDate date time field IS NOT valid");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("updatedBy"),
                "updatedBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("updatedOn"),
                "updatedOn date is not a null value");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "Created on not a userId");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("action"),
                "INSERT", "action inside dataObject on not a INSERT");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "Co-relation id is not matching");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionTaken"),
                "actionTaken date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionedOn"),
                "actionedOn date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("cancelReason"),
                "cancelReason date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("defaultPriority") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("dueIn"),
                "dueIn date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("dueInDays") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("editReason"),
                "editReason date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("holdReason"),
                "holdReason date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overrideDueDate"),
                "overrideDueDate date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedBy"),
                "overridePerformedBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedDate"),
                "overridePerformedDate date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePriority"),
                "overridePriority date is not a null value");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("source").toLowerCase(),
                "user", "source inside dataObject on not a User");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("staffAssignedTo"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo on not a userId");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffForwardBy"),
                "staffForwardBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffWorkedBy"),
                "staffWorkedBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskDetails"),
                "taskDetails date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskDisposition"),
                "taskDisposition date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskTypeId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("taskInfo").toLowerCase(),
                "Test Info".toLowerCase(), "taskInfo inside dataObject on not a Test Info");
        /*
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskNotes"),
                "taskNotes date is not a null value");
         */
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("taskStatus").toLowerCase(),
                "created", "taskStatus inside dataObject on not a created");

    }

    @Then("I verify task update event payloads has correct data")
    public void verifyTaskUpdatePayload() {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(action.get().toLowerCase(), "update", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "taskvo", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("statusDate")), "statusDate date time field IS NOT valid");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy not a userId");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("createdBy").chars().
                allMatch(Character::isDigit), "createdBy not a userId");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("action"),
                "UPDATE", "action inside dataObject on not a UPDATE");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "Co-relation id is not matching");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionTaken"),
                "actionTaken date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionedOn"),
                "actionedOn date is not a null value");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("status").equals("Escalated"))
            assertTrue(payloadObject.get().getJSONObject("dataObject").getBoolean("escalatedFlag"), "escalatedFlag is not true");
        else
            assertFalse(payloadObject.get().getJSONObject("dataObject").getBoolean("escalatedFlag"), "escalatedFlag is not true");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("cancelReason"),
                "cancelReason date is not a null value");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("defaultPriority") + "").
                chars().allMatch(Character::isDigit));
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("dueIn"),
                "dueIn date is not a null value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").isNull("dueInDays"), "dataObject is null");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("editReason") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("editReason").isEmpty(),
                "editReason date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("holdReason") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("holdReason").isEmpty(),
                "holdReason date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overrideDueDate"),
                "overrideDueDate date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedBy"),
                "overridePerformedBy date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedDate"),
                "overridePerformedDate date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePriority"),
                "overridePriority date is not a null value");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("source").toLowerCase(),
                "user", "source inside dataObject on not a User");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("assignee") == null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("-- --") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty()) {
            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffAssignedTo"), "staffAssignedTo is not null");
        } else {
            assertEquals(payloadObject.get().getJSONObject("dataObject").getInt("staffAssignedTo") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
        }
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffForwardBy"),
                "staffForwardBy date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffWorkedBy"),
                "staffWorkedBy date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskDisposition"),
                "taskDisposition date is not a null value");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskId") + "").
                chars().allMatch(Character::isDigit));
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskTypeId") + "").
                chars().allMatch(Character::isDigit));
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskInfo") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("taskInfo").isEmpty(),
                "taskInfo is not a null value");
        /*
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskNotes") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("taskNotes").isEmpty(),
                "taskNotes is not a null value");
         */
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("taskStatus"),
                CRM_GeneralTaskStepDef.taskValues.get().get("status"), "taskStatus inside dataObject is mismatch");
    }

    @Then("I verify task update {string} event payloads has correct data")
    public void verifyTaskUpdateWithStatusPayload(String status) {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(action.get().toLowerCase(), "update", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("statusDate")), "statusDate date time field IS NOT valid");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy not a userId");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("createdBy").chars().
                allMatch(Character::isDigit), "createdBy not a userId");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("action"),
                "UPDATE", "action inside dataObject on not a UPDATE");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "Co-relation id is not matching");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionTaken"),
                "actionTaken date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionedOn"),
                "actionedOn date is not a null value");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("defaultPriority") + "").
                chars().allMatch(Character::isDigit));
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("dueIn"),
                "dueIn date is not a null value");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("dueInDays") + "").
                chars().allMatch(Character::isDigit));
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("editReason"),
                "editReason date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overrideDueDate"),
                "overrideDueDate date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedBy"),
                "overridePerformedBy date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedDate"),
                "overridePerformedDate date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePriority"),
                "overridePriority date is not a null value");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("source").toLowerCase(),
                "user", "source inside dataObject on not a User");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("staffAssignedTo"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo on not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffForwardBy"),
                "staffForwardBy date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffWorkedBy"),
                "staffWorkedBy date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails").isEmpty(),
                "taskDetails date is not a null value");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskDisposition"),
                "taskDisposition date is not a null value");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskId") + "").
                chars().allMatch(Character::isDigit));
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskTypeId") + "").
                chars().allMatch(Character::isDigit));
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskInfo") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("taskInfo").isEmpty(),
                "taskNotes date is not a null value");
        /*
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskNotes") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("taskNotes").isEmpty(),
                "taskNotes date is not a null value");
         */
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("taskStatus").toLowerCase(),
                status.toLowerCase(), "taskStatus inside dataObject on not a " + status);
        switch (status) {
            case "onhold":
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("holdReason"),
                        "Application On Hold", "holdReason date is not a null value");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("cancelReason"),
                        "cancelReason date is not a null value");
                break;
            case "Cancelled":
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("cancelReason"),
                        "CREATED_INCORRECTLY", "holdReason date is not a null value");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("holdReason") ||
                                !payloadObject.get().getJSONObject("dataObject").getString("holdReason").isEmpty(),
                        "taskNotes date is not a null value");
                break;
        }

    }

    @Then("I verify that payload contains role update details which I recently updated")
    public void verifyUserRoleUpdateDetailsPage() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "update", "Update is not a create");
        Assert.assertEquals(recordType.get().toLowerCase(), "staff", "Record type is not a Staff");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getString("projectName"), "BLCRM", "Project Name is not showing correct value");
        Assert.assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONObject("user").
                getString("startDate")), "startDate date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");

        //Verify update date value with the actual date


        String actualUpdateOn = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0, 10);
        String expectedUpdateOn = payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn").substring(0, 10);

        Assert.assertEquals(expectedUpdateOn, actualUpdateOn);


        Assert.assertTrue(!payloadObject.get().getJSONObject("dataObject").getJSONArray("userProjectRoleVOList").getJSONObject(0).getJSONArray("projectRole").getJSONObject(0).
                isNull("startDate"));
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("userProjectRoleVOList").getJSONObject(0).getJSONArray("projectRole").getJSONObject(0).
                getString("endDate")), "endDate date time field IS NOT valid");
        Assert.assertTrue(!payloadObject.get().getJSONObject("dataObject").getJSONArray("userProjectRoleVOList").getJSONObject(0).getJSONArray("projectRole").getJSONObject(0).
                isNull("roleName"));
        Assert.assertTrue(!payloadObject.get().getJSONObject("dataObject").getJSONArray("userProjectRoleVOList").getJSONObject(0).getJSONArray("projectRole").getJSONObject(0).
                isNull("projectRoleId"));
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("userProjectRoleVOList").getJSONObject(0).getJSONArray("projectRole").getJSONObject(0).
                getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());


    }

    @Then("I will send API call to get Event")
    public void iWillSendAPICallToGetEvent() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
    }

    @And("I verify response for Event details")
    public void iVerifyResponseForEventDetails() {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status"), "success", "STATUS IS FAIL");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].eventName"), "PLAN_UPDATE_EVENT", "Event Name is fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].module"), "PLAN_AND_PROVIDER", "Module is Fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].correlationId"), correlationId);
        eventId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.events[0].eventId").toString());

        Api_Body api_res = new EventBuilder()
                .body(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].payload"))
                .name("pl")
                .build();
        stg.addConsumed(api_res);

        Assert.assertEquals((int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl.projectId"), 6203);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.projectName"), "BLCRM", "Project name is Fail");
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.eventCreatedOn")));
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.dataObject.plan.createdOn")));
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.dataObject.plan.updatedOn")));


    }

    @And("I send Api call {string} for subsciber name")
    public void iSendApiCallForSubsciberName(String payload, Map<String, String> data) {
        apitdu.getJsonFromFile("tenantmanager/Provider/" + payload + ".json");
        String json = apitdu.jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("I will verify the response has event {string} with Subscriber Mapping Id")
    public void iWillVerifyTheResponseHasEventWithSubscriberMappingId(String Event) {
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.subscriberEntity[0].subscriberList[*].eventName").toString().contains(Event));
        Assert.assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.subscriberEntity[0].subscriberList[?(@.eventName== '" + Event + "')].eventSubscriberMappingId"));
        mapId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.subscriberEntity[0].subscriberList[?(@.eventName== 'PLAN_UPDATE_EVENT')].subscriberId").replaceAll("[^\\d]", ""));
    }

    @And("Send API call to get Subscribers Record")
    public void sendAPICallToGetSubscribersRecord() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint + mapId.get() + "/" + eventId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify the response has event {string} with SubscriberId")
    public void iVerifyTheResponseHasEventWithSubscriberId(String eventName) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status"), "success");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.eventSubscriberRecords[0].subscriberId").toString(), mapId.get());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.eventSubscriberRecords[0].eventName"), eventName);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.eventSubscriberRecords[0].eventId").toString(), eventId.get());
    }

    @Then("I verify All Fields task event payloads has correct data")
    public void verifyAllFieldsTaskEventPayloadsHasCorrectData() {
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not correct");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        Assert.assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"), "taskId is null or equal to 0");
        Assert.assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"), "taskTypeId is null or equal to 0");
        Assert.assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
        assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")), "defaultDueDate is miss match");
        Assert.assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
        Assert.assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        Assert.assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        //assertEquals(temp.getInt("staffAssignedTo")+"",api.userId,"staffAssignedTo is miss match");
        Assert.assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        Assert.assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        Assert.assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        Assert.assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").charAt(0) + "", "dueInDays is miss match");
        assertEquals(temp.getString("source"), "User", "source is miss match");
        Assert.assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        //Assert.assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        Assert.assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        Assert.assertTrue(temp.isNull("editReason"), "editReason is not null");
        Assert.assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        Assert.assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        Assert.assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");

        Assert.assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), correlationId.get(), "correlationId is miss match");
        //Assert.assertTrue(!temp.isNull("uiid"), "uiid is null");
        assertEquals(temp.getString("action"), "INSERT", "action is miss match");
        Assert.assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"), "createdOn and updatedOn is miss match");

        JSONArray json = payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails");
        for (int i = 0; i < json.length(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());

            String fldName = temp1.getString("selectionFieldName");

            if (fldName.equalsIgnoreCase("Appointment Date") ||
                    fldName.equalsIgnoreCase("Date of Birth") ||
                    fldName.equalsIgnoreCase("Plan Effective Date") ||
                    fldName.equalsIgnoreCase("Plan Start Date") ||
                    fldName.equalsIgnoreCase("Preferred Call Back Date")) {
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp1.getString("selectionDate")));
                //assertEquals(temp1.getString("selectionDate"),CRM_GeneralTaskStepDef.orderOfFields.get().get(i),"selectionDate is miss match");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
            } else if (fldName.equalsIgnoreCase("Inbound Correspondence Workable Flag") ||
                    fldName.equalsIgnoreCase("Requested New Plan") ||
                    fldName.equalsIgnoreCase("Requested New Provider") ||
                    fldName.equalsIgnoreCase("Urgent Access to Care")) {
                assertTrue(temp1.getBoolean("selectionBoolean"), "selectionBoolean is not true");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null");
            } else if (fldName.equalsIgnoreCase("Inbound Correspondence Type")) {
                assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null");
            } else {
                assertEquals(temp1.getString("selectionVarchar"), CRM_GeneralTaskStepDef.orderOfFields.get().get(i), "selectionVarchar is miss match");
                assertTrue(temp1.isNull("selectionDate"), "selectionVarchar is not null");
                assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
            }
            assertTrue(temp1.getInt("taskId") != 0 || !temp1.isNull("taskId"),
                    "taskId is null or equal 0");
            assertTrue(temp1.getInt("taskDetailsId") != 0 || !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal 0");
            assertTrue(temp1.getInt("taskFieldId") != 0 || !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal 0");
            assertEquals(temp1.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp1.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            /*assertTrue(!temp1.getString("uiid").isEmpty() || !temp1.isNull("uiid"),
                    "uiid is null or empty string");*/
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(!temp1.getString("correlationId").isEmpty() || !temp1.isNull("correlationId"),
                    "correlationId is null or empty string");

            if (fldName.contains("Case Worker")) {
                assertEquals(temp1.getString("fieldGroup"), "CASE_WORKER", "fieldGroup is miss match");
            } else if (fldName.contains("New Addres")) {
                assertEquals(temp1.getString("fieldGroup"), "NEW_ADDRESS", "fieldGroup is miss match");
            } else if (fldName.contains("Old Address")) {
                assertEquals(temp1.getString("fieldGroup"), "OLD_ADDRESS", "fieldGroup is miss match");
            } else if (fldName.contains("Provider Address")) {
                assertEquals(temp1.getString("fieldGroup"), "PROVIDER_ADDRESS", "fieldGroup is miss match");
            } else if (fldName.contains("Provider First Name")) {
                assertEquals(temp1.getString("fieldGroup"), "PROVIDER", "fieldGroup is miss match");
            } else if (fldName.contains("Provider Last Name")) {
                assertEquals(temp1.getString("fieldGroup"), "PROVIDER", "fieldGroup is miss match");
            } else {
                assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null");
            }
        }
    }

    @Then("I verify task save event payloads has {string} char data")
    public void verifyTaskSaveEventHas3kChar(String mesBody) {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        Assert.assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getString("projectName"), "BLCRM", "Project Name is not a BLCRM");
        Assert.assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskTypeId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("defaultPriority") + "").
                chars().allMatch(Character::isDigit));

        /*Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("defaultDueDate")), "defaultDueDate date time field IS NOT valid");*/

        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("taskStatus").toLowerCase(),
                "created", "taskStatus inside dataObject on not a " + status);
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("statusDate")), "statusDate date time field IS NOT valid");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("dueIn"),
                "dueIn date is not a null value");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("action"),
                "INSERT", "action inside dataObject on not a UPDATE");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                taskType.traceId.get(), "Co-relation id is not matching");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionTaken"),
                "actionTaken date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionedOn"),
                "actionedOn date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("dueInDays") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("editReason"),
                "editReason date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overrideDueDate"),
                "overrideDueDate date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedBy"),
                "overridePerformedBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedDate"),
                "overridePerformedDate date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePriority"),
                "overridePriority date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffForwardBy"),
                "staffForwardBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffWorkedBy"),
                "staffWorkedBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskDisposition"),
                "taskDisposition date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskInfo"),
                "taskNotes date is not a null value");
        /*
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskNotes"),
                "taskNotes date is not a null value");
         */
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("cancelReason"),
                "cancelReason date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("holdReason"),
                "taskNotes date is not a null value");

        JSONArray json = payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails");

        boolean flag = false;

        for (int i = 0; i < json.length(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            System.out.println("Vidya+" + temp1);
            if (temp1.getString("selectionFieldName").equalsIgnoreCase("Message")) {
                System.out.println(temp1.getString("selectionFieldName"));
                flag = true;
                if (Integer.parseInt(mesBody) == 3000) {
                    Assert.assertTrue(temp1.isNull("createdBy"), "createdBy is ot null have some value");
                    Assert.assertTrue(temp1.isNull("updatedBy"), "updatedBy is ot null have some value");
                    Assert.assertTrue(temp1.isNull("uiid"), "uiid is ot null have some value");
                    Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("createdBy")
                            , "createdBy not a null");
                    Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("updatedBy")
                            , "updatedBy not a null");
                    Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffAssignedTo"),
                            "staffAssignedTo on not a ull");
                    Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                            "uiid on not a ull");
                    Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("source").toLowerCase(),
                            "system", "source inside dataObject on not a User");
                    assertTrue(temp1.getString("selectionVarchar2").length() == 3000, "3k Char is not stored in Task Details DB");
                } else {
                    Assert.assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null have some value");
                    Assert.assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is  null have some value");
                    Assert.assertTrue(!temp1.isNull("uiid"), "uiid is null have some value");
                    Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy not a userId");
                    Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy not a userId");
                    Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("staffAssignedTo"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo on not a userId");
                    Assert.assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                            "uiid on is a ull");
                    Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("source").toLowerCase(),
                            "user", "source inside dataObject on not a User");
                    assertTrue(temp1.getString("selectionVarchar2").length() == 4000, "4k Char is not stored in Task Details DB");
                }
                assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                        "taskDetailsId is null or equal to 0");
                assertTrue(temp1.getInt("taskId") != 0 && !temp1.isNull("taskId"),
                        "taskId is null or equal to 0");
                assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                        "taskFieldId is null or equal to 0");
                Assert.assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                        "CreatedOn date time field IS NOT valid");
                Assert.assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                        "updatedOn date time field IS NOT valid");
                Assert.assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is ot null have some value");
                Assert.assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is ot null have some value");
                Assert.assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is ot null have some value");
                Assert.assertTrue(temp1.isNull("selectionDate"), "selectionDate is ot null have some value");
                Assert.assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is ot null have some value");
                Assert.assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is ot null have some value");
                assertEquals(temp1.getString("correlationId"), taskType.traceId.get(),
                        "correlationId is null or has empty string");
                assertEquals(temp1.getString("selectionVarchar2"), taskType.msgBody.get(), "Message boday text is not matching");
            }
            if (flag) {
                break;
            }
        }
        assertTrue(flag, "Message body is not there in Task Details Object Array");
    }

    @Then("I verify task save event payloads has {string} {string} {string} char data")
    public void verifyTaskSaveEventHas3kChar(String phone, String email, String channel) {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "taskvo", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        assertEquals(temp.getInt("taskId"), taskType.taskId.get(), "TaskId is mismatch");
        System.out.println(temp.getInt("taskTypeId") + " " + taskType.taskTypeId.get());
        assertEquals(temp.getInt("taskTypeId") + "", taskType.taskTypeId.get(), "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", taskType.taskPriority.get(), "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", taskType.dueInDays.get(), "dueInDays is miss match");
        assertEquals(temp.getString("source"), "System", "source is miss match");
        assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertTrue(temp.isNull("createdBy"), "createdBy is not null");
        assertTrue(temp.isNull("updatedBy"), "updatedBy is not null");
        Assert.assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), taskType.traceId.get(), "correlationId is miss match");
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertEquals(temp.getString("action"), "INSERT", "action is miss match");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");

        JSONArray json = payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails");

        String str[] = {"Message Subject", "Channel", "From Email", "From Phone", "Message"};
        String value[] = {taskType.msgSubject.get(), channel, email, phone, taskType.msgBody.get()};

        for (int i = 0; i < json.length(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());

            assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp1.getInt("taskId"), taskType.taskId, "TaskId is miss match");
            assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(temp1.isNull("createdBy"), "createdBy is ot null have some value");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp1.isNull("updatedBy"), "updatedBy is ot null have some value");
            if (str[i].equals("Message")) {
                assertEquals(temp1.getString("selectionVarchar2"), value[i],
                        "selectionVarchar2 is ot null have some value");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
            } else {
                assertEquals(temp1.getString("selectionVarchar"), value[i],
                        "selectionVarchar is ot null have some value");
                assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            }

            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertEquals(temp1.getString("selectionFieldName"), str[i],
                    "selectionVarchar is ot null have some value");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertEquals(temp1.getString("correlationId"), taskType.traceId.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp1.isNull("uiid"), "uiid is not null have some value");
        }
    }

    @Then("I also Verify information in LINK_EVENT for {string}")
    public void verifyLINK_EVENT(String caseId) {

        payloadObject.set(new JSONObject(new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events").
                get(1).toString()).get("payload").toString()));

        payloadObject1.set(new JSONObject(new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events").
                get(2).toString()).get("payload").toString()));

        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("TASK")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertEquals(temp.getInt("internalId"), taskType.taskId, "internalId is miss match");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");
        assertEquals(temp.getInt("externalId") + "", caseId, "externalId is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(temp.isNull("createdBy"), "createdBy is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(temp.isNull("updatedBy"), "updatedBy is not null have some value");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                taskType.traceId.get(), "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertEquals(temp1.getInt("internalId") + "", caseId, "internalId is miss match");
        assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "TASK", "externalRefType is miss match");
        assertEquals(temp1.getInt("externalId"), taskType.taskId.get(), "externalId is miss match");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(temp1.isNull("createdBy"), "createdBy is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(temp1.isNull("updatedBy"), "updatedBy is not null have some value");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                taskType.traceId.get(), "correlationId not a userId");
        assertTrue(payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify task save event payloads has correct data for {string} task")
    public void i_verify_task_save_event_payloads_has_correct_data_for_task(String taskStatus) {
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a BLCRM");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"), "taskId is null or equal to 0");
        assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"), "taskTypeId is null or equal to 0");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
        assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), taskStatus, "taskStatus is miss match");
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
        assertEquals(temp.getInt("dueInDays") + "", CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").
                charAt(0) + "", "dueInDays is miss match");
        assertEquals(temp.getString("source"), "User", "source is miss match");
        assertEquals(temp.getString("taskInfo"), "Task Info", "taskInfo is miss match");
        //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        if (taskStatus.equals("Escalated")) {
            assertTrue(temp.getBoolean("escalatedFlag"), "escalatedFlag is not true");
        } else {
            assertTrue(false, "Task Status not matches");
        }
        assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
        //assertTrue(!temp.isNull("uiid"), "uiid is null");
        assertEquals(temp.getString("action"), "INSERT", "action is miss match");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"),
                "createdOn and updatedOn is miss match");
    }

    @Then("I verify task save event payloads has correct data")
    public void verifyTaskSaveEvent() {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "taskvo", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"),
                "taskId is null or equal to 0");
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
        assertEquals(temp.getString("taskInfo"), CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"), "taskInfo is miss match");
        //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        Assert.assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
        //assertTrue(!temp.isNull("uiid"), "uiid is null");
        assertEquals(temp.getString("action"), "INSERT", "action is miss match");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"),
                "createdOn and updatedOn is miss match");
    }

    @And("I will get {string} from traceId for LINK Event and pass it as end point")
    public void iGetCorrelationIdAsTraceIdAndPassItAsEndPoint(String correlationId) {
        if (correlationId.equals("taskToCase")) {
            values = new ArrayList<>(EventsUtilities.getLogs("traceid",
                    "internalRefType\\\":\\\"TASK\\\""));
            eventName.set("LINK");
            correlationId = values.get(2);
        } else if (correlationId.equals("taskToConsumer")) {
            eventName.set("LINK");
            correlationId = values.get(4);
        } else if (correlationId.equals("taskToContactRecord")) {
            eventName.set("LINK");
            correlationId = values.get(0);
        } else if (correlationId.equals("caseToTask")) {
            eventName.set("LINK");
            values = new ArrayList<>(EventsUtilities.getLogs("traceid",
                    "internalRefType\\\":\\\"TASK\\\""));
            correlationId = values.get(0);
        }
        this.correlationId.set(correlationId);
        eventEndPoint.set(eventEndPoint1 + correlationId);
    }

    @Then("I verify link event from task to case")
    public void verifyLINK_EVENTTaskToCase() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject.get().getString("action").toLowerCase(), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("TASK")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        if (temp.getString("externalRefType").equals("CASE")) {
            assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_ContactRecordUIStepDef.caseId.get(),
                    "externalId is miss match");
            taskToCase = true;
        } else if (temp.getString("externalRefType").equals("CONSUMER")) {
            assertEquals(temp.getString("externalRefType"), "CONSUMER", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"),
                    "externalId is miss match");
            taskToConsumer = true;
        } else if (temp.getString("externalRefType").equals("CONTACT_RECORD")) {
            assertEquals(temp.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"),
                    "externalId is miss match");
            taskToCR = true;
        }
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp.getString("createdBy").isEmpty() && !temp.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp.getString("updatedBy").isEmpty() && !temp.isNull("updatedBy"),
                "updatedBy is null or equal to 0");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        if (temp.getString("externalRefType").equals("CASE")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_ContactRecordUIStepDef.caseId.get(), "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        } else if (temp.getString("externalRefType").equals("CONSUMER")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"),
                    "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CONSUMER", "internalRefType is miss match");
        } else if (temp.getString("externalRefType").equals("CONTACT_RECORD")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"),
                    "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        }
        assertEquals(temp1.getString("externalRefType"), "TASK", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp1.getString("createdBy").isEmpty() && !temp1.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp1.getString("updatedBy").isEmpty() && !temp1.isNull("updatedBy"),
                "updatedBy is null or equal to 0");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify link event from task to consumer")
    public void verifyLINK_EVENTTaskToConsumer() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("TASK")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        if (temp.getString("externalRefType").equals("CASE")) {
            assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_ContactRecordUIStepDef.caseId.get(),
                    "externalId is miss match");
            taskToCase = true;
        } else if (temp.getString("externalRefType").equals("CONSUMER")) {
            assertEquals(temp.getString("externalRefType"), "CONSUMER", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"),
                    "externalId is miss match");
            taskToConsumer = true;
        } else if (temp.getString("externalRefType").equals("CONTACT_RECORD")) {
            assertEquals(temp.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"),
                    "externalId is miss match");
            taskToCR = true;
        }
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp.getString("createdBy").isEmpty() && !temp.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp.getString("updatedBy").isEmpty() && !temp.isNull("updatedBy"),
                "updatedBy is null or equal to 0");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        if (temp.getString("externalRefType").equals("CASE")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_ContactRecordUIStepDef.caseId.get(), "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        } else if (temp.getString("externalRefType").equals("CONSUMER")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"),
                    "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CONSUMER", "internalRefType is miss match");
        } else if (temp.getString("externalRefType").equals("CONTACT_RECORD")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"),
                    "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        }
        assertEquals(temp1.getString("externalRefType"), "TASK", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp1.getString("createdBy").isEmpty() && !temp1.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp1.getString("updatedBy").isEmpty() && !temp1.isNull("updatedBy"),
                "updatedBy is null or equal to 0");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify link event from task to contact record")
    public void verifyLINK_EVENTTaskToContactRecord() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("TASK")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        if (temp.getString("externalRefType").equals("CASE")) {
            assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_ContactRecordUIStepDef.caseId.get(),
                    "externalId is miss match");
            taskToCase = true;
        } else if (temp.getString("externalRefType").equals("CONSUMER")) {
            assertEquals(temp.getString("externalRefType"), "CONSUMER", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"),
                    "externalId is miss match");
            taskToConsumer = true;
        } else if (temp.getString("externalRefType").equals("CONTACT_RECORD")) {
            assertEquals(temp.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
            assertEquals(temp.getInt("externalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"),
                    "externalId is miss match");
            taskToCR = true;
        }
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp.getString("createdBy").isEmpty() && !temp.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp.getString("updatedBy").isEmpty() && !temp.isNull("updatedBy"),
                "updatedBy is null or equal to 0");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        if (temp.getString("externalRefType").equals("CASE")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_ContactRecordUIStepDef.caseId.get(), "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        } else if (temp.getString("externalRefType").equals("CONSUMER")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"),
                    "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CONSUMER", "internalRefType is miss match");
        } else if (temp.getString("externalRefType").equals("CONTACT_RECORD")) {
            assertEquals(temp1.getInt("internalId") + "", CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"),
                    "internalId is miss match");
            assertEquals(temp1.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        }
        assertEquals(temp1.getString("externalRefType"), "TASK", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp1.getString("createdBy").isEmpty() && !temp1.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp1.getString("updatedBy").isEmpty() && !temp1.isNull("updatedBy"),
                "updatedBy is null or equal to 0");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
        assertTrue(taskToCase && taskToConsumer && taskToCR, "Link Events are not generated");
    }

    @Then("I verify task save event payloads for task created from external api")
    public void verifyTaskSaveEventCreatedFromexteralAPI(List<String> additionalFlds) {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not correct");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "taskvo", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        assertEquals(temp.getInt("taskId"), taskType.taskId.get(), "TaskId is mismatch");
        assertEquals(temp.getInt("taskTypeId") + "", taskType.taskTypeId.get(), "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", taskType.taskPriority.get(), "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", taskType.dueInDays.get(), "dueInDays is miss match");
        assertEquals(temp.getString("source"), "System", "source is miss match");
        assertEquals(temp.getString("taskInfo"), taskType.taskInfo.get(), "taskInfo is null");
        //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        Assert.assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), taskType.traceId1.get(), "correlationId is miss match");
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertEquals(temp.getString("action"), "INSERT", "action is miss match");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");

        JSONArray json = payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails");

        int j = 0;
        ArrayList<String> dynamicFileds = new ArrayList<>(additionalFlds);
        for (int i = 0; i < json.length(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());

            if (taskType.allFieldsValue.get().size() > 0 &&
                    taskType.allFieldsValue.get().contains(temp1.getString("selectionFieldName"))) {
                assertTrue(dynamicFileds.contains(temp1.getString("selectionFieldName")),
                        "selectionFieldName is value is mismatch");
                assertTrue(taskType.allFieldsValue.get().contains(temp1.getString("selectionVarchar")),
                        "selectionVarchar is value is mismatch");
                dynamicFileds.remove(temp1.getString("selectionFieldName"));
                taskType.allFieldsValue.get().remove(temp1.getString("selectionFieldName"));
                taskType.allFieldsValue.get().remove(temp1.getString("selectionVarchar"));
            } else {
                assertTrue(dynamicFileds.contains(temp1.getString("selectionFieldName")),
                        "selectionFieldName is value is mismatch");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                dynamicFileds.remove(temp1.getString("selectionFieldName"));
            }

            assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp1.getInt("taskId"), taskType.taskId.get(), "TaskId is miss match");
            assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            /*assertEquals(temp1.getString("selectionFieldName"), taskType.allFieldsValue.get(j++),
                    "selectionFieldName is value is mismatch");
            assertEquals(temp1.getString("selectionVarchar"), taskType.allFieldsValue.get(j++),
                    "selectionVarchar is value is mismatch");*/
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertEquals(temp1.getString("correlationId"), taskType.traceId1.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp1.isNull("uiid"), "uiid is not null have some value");

        }
        assertTrue(dynamicFileds.size() == 0, "All Dynamic fields are not present");
        assertTrue(taskType.allFieldsValue.get().size() == 0, "All passed information is not there in events");
    }

    ArrayList<JSONObject> listofTemps = new ArrayList<>();
    JsonArray teamjson = null;

    @And("I run the Event GET API and get the payload for task created by external api")
    public ArrayList<JSONObject> iRunGETEvetAPi() throws Exception {
        Thread.sleep(3000);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        teamjson = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");

        for (int i = 0; i < teamjson.size(); i++) {
            listofTemps.add(new JSONObject(new JSONObject(teamjson.get(i).toString()).get("payload").toString()));
        }
        return listofTemps;
    }

    @Then("I verify link events are generated and payload has {string} {string} {string}")
    public void verifyLinkEvetsAreGeneratedWithValidPayload(String caseId, String consumerId, String inboundId) {
        boolean caseToTask = false, taskToCase = false, consumerToTask = false, taskToConsumer = false,
                inboundToTask = false, taskToInbound = false;
        for (int i = 1; i < listofTemps.size(); i++) {
            if (listofTemps.get(i).getString("recordType").equalsIgnoreCase("link")) {
                payloadObject.set(listofTemps.get(i));
                Assert.assertFalse(payloadObject.get().isNull("dataObject"));
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                        "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                        "Project Name is not correct");
                assertEquals(payloadObject.get().getString("action"), "create",
                        "Action is not a Update");
                assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                        "recordType is not a Update");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                        "eventCreatedOn date time field IS NOT valid");

                JSONObject temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");

                if (temp.getString("internalRefType").equals("TASK") &&
                        temp.getString("externalRefType").equals("CONSUMER")) {
                    assertEquals(temp.getInt("internalId"), taskType.taskId.get(),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId") + "", consumerId,
                            "externalId is miss match");
                    taskToConsumer = true;
                } else if (temp.getString("internalRefType").equals("TASK") &&
                        temp.getString("externalRefType").equals("CASE")) {
                    assertEquals(temp.getInt("internalId"), taskType.taskId.get(),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId") + "", caseId,
                            "externalId is miss match");
                    taskToCase = true;
                } else if (temp.getString("internalRefType").equals("TASK") &&
                        temp.getString("externalRefType").equals("INBOUND_CORRESPONDENCE")) {
                    assertEquals(temp.getInt("internalId"), taskType.taskId.get(),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId") + "", inboundId,
                            "externalId is miss match");
                    taskToInbound = true;
                } else if (temp.getString("internalRefType").equals("INBOUND_CORRESPONDENCE") &&
                        temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "", inboundId,
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId"), taskType.taskId.get(),
                            "externalId is miss match");
                    inboundToTask = true;
                } else if (temp.getString("internalRefType").equals("CASE") &&
                        temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "", caseId,
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId"), taskType.taskId.get(),
                            "externalId is miss match");
                    caseToTask = true;
                } else if (temp.getString("internalRefType").equals("CONSUMER") &&
                        temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "", consumerId,
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId"), taskType.taskId.get(),
                            "externalId is miss match");
                    consumerToTask = true;
                }
                assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                        "externalLinkId is null or equal to 0");
                assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                        "effectiveStartDate date time field IS NOT valid");
                assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
                assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                        "createdOn date time field IS NOT valid");
                assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                        "updatedOn date time field IS NOT valid");
                assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
                assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");

                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                        correlationId, "correlationId not a userId");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                        "uiid is not null have some value");
            }
        }
        System.out.println(taskToCase + " " + taskToConsumer + " " + taskToInbound + " " + caseToTask + " " + consumerToTask + " " + inboundToTask);
        assertTrue(taskToCase && taskToConsumer && taskToInbound && caseToTask && consumerToTask &&
                inboundToTask, "All 6 Events are not generated");
    }

    @Then("I verify response for {string} for Link_Event details")
    public void iVerifyResponseForLINK_EVENTForBusinessUnitDetails(String eventName) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status"), "success", "STATUS IS FAIL");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].eventName"), eventName, "Event Name is fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].module"), "TENANT_MANAGER", "Module is Fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].correlationId"), correlationId);
        eventId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].eventId"));
        Api_Body api_res = new EventBuilder()
                .body(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[0].payload"))
                .name("pl")
                .build();
        stg.addConsumed(api_res);
        Assert.assertEquals((int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl.projectId"), 6203);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.projectName"), "BLCRM", "Project name is Fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.recordType"), "Business Unit", "Record Type name is Fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.action"), "Update", "Record Type name is Fail");
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.eventCreatedOn")));
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl.dataObject.updatedOn")));
    }

    @And("I verify {string} is generated for Task Type")
    public void iVerifyLINK_EVENTIsGeneratedForTaskType(String eventName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status"), "success", "STATUS IS FAIL");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[1].eventName"), eventName, "Event Name is fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[1].module"), "LINK_MANAGEMENT", "Module is Fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[1].correlationId"), correlationId);
        eventId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.events[1].eventId").toString());
        Api_Body api_res = new EventBuilder()
                .body(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[1].payload"))
                .name("pl1")
                .build();
        stg.addConsumed(api_res);
        Assert.assertEquals((int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl1.projectId"), 6203);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.projectName"), "BLCRM", "Project name is Fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.recordType"), "Link", "Record Type name is Fail");
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.eventCreatedOn")));
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.dataObject.externalLinkPayload.updatedOn")));
        Assert.assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.dataObject.externalLinkPayload.internalRefType").isEmpty(), "Internal Ref Type name is Fail");
        Assert.assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.dataObject.externalLinkPayload.externalRefType").isEmpty(), "External Ref Type name is Fail");
        Assert.assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl1.dataObject.externalLinkPayload.internalId"));
        Assert.assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl1.dataObject.externalLinkPayload.externalId"));
    }

    @And("I Verify Link_EVENT for Task Type when UNLINK_EVENT and Link_EVENT is triggered")
    public void iVerifyLink_EVENTForTaskTypeUNLINK_EVENTandLink_EVENTistriggered() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status"), "success", "STATUS IS FAIL");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[2].eventName"), "LINK_EVENT", "Event Name is fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[2].module"), "LINK_MANAGEMENT", "Module is Fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[2].correlationId"), correlationId);
        eventId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.events[2].eventId").toString());
        Api_Body api_res = new EventBuilder()
                .body(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.events[2].payload"))
                .name("pl1")
                .build();
        stg.addConsumed(api_res);
        Assert.assertEquals((int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl1.projectId"), 6203);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.projectName"), "BLCRM", "Project name is Fail");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.recordType"), "Link", "Record Type name is Fail");
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.eventCreatedOn")));
        Assert.assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.dataObject.externalLinkPayload.updatedOn")));
        Assert.assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.dataObject.externalLinkPayload.internalRefType").isEmpty(), "Internal Ref Type name is Fail");
        Assert.assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl1.dataObject.externalLinkPayload.externalRefType").isEmpty(), "External Ref Type name is Fail");
        Assert.assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl1.dataObject.externalLinkPayload.internalId"));
        Assert.assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl1.dataObject.externalLinkPayload.externalId"));
    }

    @Then("I verify task save event with status Complete and Disposition")
    public void verifyTaskSaveEventCompleteandDisposition() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        Assert.assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("defaultDueDate")), "defaultDueDate is miss match");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("statusDate")), "statusDate date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy not a userId");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy not a userId");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "Co-relation id is not matching");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionTaken"),
                "actionTaken date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionedOn"),
                "actionedOn date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("cancelReason"),
                "cancelReason date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("defaultPriority") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("dueIn"),
                "dueIn date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("dueInDays") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("editReason"),
                "editReason date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("holdReason"),
                "holdReason date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overrideDueDate"),
                "overrideDueDate date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedBy"),
                "overridePerformedBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedDate"),
                "overridePerformedDate date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePriority"),
                "overridePriority date is not a null value");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("source").toLowerCase(),
                "user", "source inside dataObject on not a User");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffAssignedTo"),
                "staffAssignedTo date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffForwardBy"),
                "staffForwardBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffWorkedBy"),
                "staffWorkedBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskDisposition"),
                "taskDisposition date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskTypeId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskInfo") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("taskInfo").isEmpty(),
                "taskNotes date is not a null value");
        /*
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskNotes") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("taskNotes").isEmpty(),
                "taskNotes date is not a null value");
         */
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getString("taskStatus").equalsIgnoreCase("Complete"));
        JSONArray inObAr = payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails");
        for (int i = 0; i < inObAr.length(); i++) {
            JSONObject inOb = new JSONObject(inObAr.get(i).toString());

            assertTrue(EventsUtilities.isValidDate(inOb.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertFalse(inOb.isNull("selectionVarchar"),
                    "selectionVarchar is value is mismatch");
            assertTrue(inOb.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(inOb.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(inOb.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(inOb.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(inOb.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(inOb.isNull("fieldGroup"), "fieldGroup is not null have some value");
        }
    }

    @Then("I verify task update event with status {string} and Disposition is {string}")
    public void verifyTaskUpdateEventCompleteandDisposition(String status, String dis) {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "update", "Action is not a Update");
        Assert.assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payloadObject.get().getJSONObject("dataObject").
                getString("defaultDueDate")), "defaultDueDate is miss match");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("statusDate")), "statusDate date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy not a userId");
        Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy not a userId");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("action"),
                "UPDATE", "action inside dataObject on not a UPDATE");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "Co-relation id is not matching");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionTaken"),
                "actionTaken date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("actionedOn"),
                "actionedOn date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("cancelReason"),
                "cancelReason date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("defaultPriority") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("dueIn"),
                "dueIn date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("dueInDays") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("editReason"),
                "editReason date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overrideDueDate"),
                "overrideDueDate date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedBy"),
                "overridePerformedBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePerformedDate"),
                "overridePerformedDate date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("overridePriority"),
                "overridePriority date is not a null value");
        Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("source").toLowerCase(),
                "user", "source inside dataObject on not a User");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffForwardBy"),
                "staffForwardBy date is not a null value");
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("staffWorkedBy"),
                "staffWorkedBy date is not a null value");
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("taskTypeId") + "").
                chars().allMatch(Character::isDigit));
        Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("taskInfo") ||
                        !payloadObject.get().getJSONObject("dataObject").getString("taskInfo").isEmpty(),
                "taskNotes date is not a null value");
        if (payloadObject.get().getJSONObject("dataObject").getString("taskStatus").equalsIgnoreCase("Complete")) {
            if (dis.equalsIgnoreCase("Consumer reached")) {
                Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getString("taskDisposition").equalsIgnoreCase("CONSUMER_REACHED"), "taskDisposition not correct");
            } else if (dis.equalsIgnoreCase("User closed ")) {
                Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getString("taskDisposition").equalsIgnoreCase("user_closed"), "taskDisposition not correct");
            } else if (dis.equalsIgnoreCase("Consumer not reached")) {
                Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getString("taskDisposition").equalsIgnoreCase("consumer_not_reached"), "taskDisposition not correct");
            }
        } else {
            Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").getString("taskStatus").equalsIgnoreCase(status));
        }
    }

    @Then("I verify task update event with status {string}")
    public void iVerifyTaskUpdateEventWithStatus(String status) {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(action.get().toLowerCase(), "update", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        assertTrue((temp.getInt("taskId") + "").chars().allMatch(Character::isDigit),
                "taskId is not a number");
        assertTrue((temp.getInt("taskTypeId") + "").chars().allMatch(Character::isDigit),
                "taskTypeId is not a number");
        assertTrue(temp.isNull("overridePriority"), "overridePriority date is not a null value");
        assertTrue((temp.getInt("defaultPriority") + "").chars().allMatch(Character::isDigit));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")),
                "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate date is not a null value");
        assertEquals(temp.getString("taskStatus"), status, "taskStatus is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy date is not a null value");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy date is not a null value");
        assertEquals(temp.getString("staffAssignedTo"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId,
                "staffAssignedTo on not a userId");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy date is not a null value");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate date is not a null value");
        assertTrue(temp.isNull("dueIn"), "dueIn date is not a null value");
        assertTrue((temp.getInt("dueInDays") + "").chars().allMatch(Character::isDigit));
        assertEquals(temp.getString("source").toLowerCase(), "user",
                "source inside dataObject on not a User");
        assertTrue(temp.isNull("taskInfo") || !temp.getString("taskInfo").isEmpty(),
                "taskInfo is not a null value");
        //assertEquals(temp.getString("taskNotes"), myTask.taskNotesEntered, "taskNotes date is not a null value");
        assertTrue(temp.isNull("actionTaken"), "actionTaken date is not a null value");
        assertTrue(temp.isNull("cancelReason"), "cancelReason date is not a null value");
        assertTrue(temp.isNull("editReason") || !temp.getString("editReason").isEmpty(),
                "editReason date is not a null value");
        assertTrue(temp.isNull("holdReason") || !temp.getString("holdReason").isEmpty(),
                "holdReason date is not a null value");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition date is not a null value");
        assertTrue(temp.getString("createdBy").chars().allMatch(Character::isDigit),
                "createdBy not a userId");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy not a userId");
        assertTrue(temp.getBoolean("escalatedFlag") || !temp.getBoolean("escalatedFlag"),
                "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), correlationId,
                "Co-relation id is not matching");
        //assertFalse(temp.getString("uiid").isEmpty(),"uiid is not a string");
        assertEquals(temp.getString("action"), "UPDATE",
                "action inside dataObject on not a UPDATE");
        assertTrue(temp.isNull("actionedOn"), "actionedOn date is not a null value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "CreatedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I verify task update event has external application id")
    public void iVerifyTaskUpdateEventHasExternalApplicationId() {

        JSONArray json = payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails");

        for (int i = 0; i < json.length(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());

            if (temp1.getString("selectionFieldName").equals("External Application ID")) {
                assertEquals(temp1.getString("selectionVarchar"), CRM_GeneralTaskStepDef.
                        taskValues.get().get("externalApplicationId"), "External Appication Id is mismatch");
            } else {
                assertTrue(temp1.isNull("selectionVarchar") || !temp1.isNull("selectionVarchar"));
            }

            assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp1.getInt("taskId") + "", myTask.taskId, "TaskId is miss match");
            assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertFalse(temp1.isNull("correlationId"), "Co-relation id is not matching");
            assertTrue(temp1.isNull("uiid"), "uiid is not null");
        }
    }

    @Then("I verify two link event from contact record and case payloads has correct data")
    public void verifyLINK_EVENTContactRecordToCase() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo();
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("CONTACT_RECORD")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");
        assertEquals(temp.getInt("externalId") + "", CRM_ContactRecordUIStepDef.caseId.get(),
                "externalId is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertEquals(temp1.getInt("internalId") + "", CRM_ContactRecordUIStepDef.caseId.get(),
                "internalId is miss match");
        assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify two unlink event from contact record and consumer payloads has correct data")
    public void verifyUNLINK_EVENTFromContactRecordToConsumer() {
        System.out.println("payloadObject----------------");
        System.out.println(payloadObject);
        System.out.println("payloadObject1--------------");
        System.out.println(payloadObject1);
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject.get().getString("action"), "update",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("CONTACT_RECORD")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertTrue(temp.getInt("externalId") != 0 && !temp.isNull("externalId"),
                "externalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CONSUMER", "externalRefType is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertTrue(!(temp.getString("createdOn").equals(temp.getString("updatedOn"))), "Created on & updated on are same");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject1.get().getString("action"), "update",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp1.getInt("internalId") != 0 && !temp1.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp1.getString("internalRefType"), "CONSUMER", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertTrue(!(temp1.getString("createdOn").equals(temp1.getString("updatedOn"))), "Created on & updated on are same");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");
    }

    @Then("I verify two unlink event from contact record and case payloads has correct data")
    public void verifyUNLINK_EVENTContactRecordToCase() {
        System.out.println("payloadObject----------------");
        System.out.println(payloadObject);
        System.out.println("payloadObject1--------------");
        System.out.println(payloadObject1);
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject.get().getString("action"), "update",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not Link");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("CONTACT_RECORD")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");
        assertEquals(temp.getInt("externalId") + "", CRM_ContactRecordUIStepDef.caseId.get(),
                "externalId is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertTrue(!(temp.getString("createdOn").equals(temp.getString("updatedOn"))), "Created on & updated on are same");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject1.get().getString("action"), "update",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not Link");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertEquals(temp1.getInt("internalId") + "", CRM_ContactRecordUIStepDef.caseId.get(), "internalId is miss match");
        assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertTrue(!(temp1.getString("createdOn").equals(temp1.getString("updatedOn"))), "Created on & updated on are same");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify two link event from contact record and consumer payloads has correct data")
    public void verifyLINK_EVENTFromContactRecordToConsumer() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo();
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a Correct");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("CONTACT_RECORD")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertTrue(temp.getInt("externalId") != 0 && !temp.isNull("externalId"),
                "externalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CONSUMER", "externalRefType is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a Correct");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp1.getInt("internalId") != 0 && !temp1.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp1.getString("internalRefType"), "CONSUMER", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");
    }

    @Then("I verify NJ task event payload has correct data")
    public void verifyNJTaskEventPayloadHasCorrectData() {
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not correct");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "taskvo", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertFalse(payloadObject.get().isNull("dataObject"));

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"), "taskId is null or equal to 0");
        assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"), "taskTypeId is null or equal to 0");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
        assertEquals(temp.getString("defaultDueDate") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").charAt(0) + "", "dueInDays is miss match");
        assertEquals(temp.getString("source"), "User", "source is miss match");
        assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");

        assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
        //Assert.assertTrue(!temp.isNull("uiid"), "uiid is null");
        assertEquals(temp.getString("action"), "INSERT", "action is miss match");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"), "createdOn and updatedOn is miss match");

        JSONArray json = payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails");
        for (int i = 0; i < json.length(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());

            String fldName = temp1.getString("selectionFieldName");

            if (fldName.equalsIgnoreCase("Complaint About")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.
                        taskValues.get().get("complaintAbout")), "complaintAbout is mismatch");
            } else if (fldName.equalsIgnoreCase("External Application ID")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.
                        taskValues.get().get("externalApplicationId")), "exAppId is mismatch");
            } else if (fldName.equalsIgnoreCase("External Case ID")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.
                        taskValues.get().get("externalCaseId")), "extCaseID is mismatch");
            } else if (fldName.equalsIgnoreCase("Name")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.
                        taskValues.get().get("name")), "name is mismatch");
            }
            assertTrue(temp1.getInt("taskId") != 0 || !temp1.isNull("taskId"),
                    "taskId is null or equal 0");
            assertTrue(temp1.getInt("taskDetailsId") != 0 || !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal 0");
            assertTrue(temp1.getInt("taskFieldId") != 0 || !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal 0");
            assertEquals(temp1.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp1.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            /*assertTrue(!temp1.getString("uiid").isEmpty() || !temp1.isNull("uiid"),
                    "uiid is null or empty string");*/
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
            assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(!temp1.getString("correlationId").isEmpty() || !temp1.isNull("correlationId"),
                    "correlationId is null or empty string");
        }
    }

    @Then("I verify link event from task to consumer for NJ")
    public void verifyLINK_EVENTTaskToConsumerForNj() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("TASK")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CONSUMER", "externalRefType is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp.getString("createdBy").isEmpty() && !temp.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp.getString("updatedBy").isEmpty() && !temp.isNull("updatedBy"),
                "updatedBy is null or equal to 0");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");

        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");
        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertEquals(temp1.getString("internalRefType"), "CONSUMER", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "TASK", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp1.getString("createdBy").isEmpty() && !temp1.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp1.getString("updatedBy").isEmpty() && !temp1.isNull("updatedBy"),
                "updatedBy is null or equal to 0");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify link event from task to Case for NJ")
    public void verifyLINK_EVENTTaskToCaseForNj() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("TASK")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp.getString("createdBy").isEmpty() && !temp.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp.getString("updatedBy").isEmpty() && !temp.isNull("updatedBy"),
                "updatedBy is null or equal to 0");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");

        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");
        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "TASK", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!temp1.getString("createdBy").isEmpty() && !temp1.isNull("createdBy"),
                "createdBy is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!temp1.getString("updatedBy").isEmpty() && !temp1.isNull("updatedBy"),
                "updatedBy is null or equal to 0");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid") ||
                        !payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify two link event from contact record and case for NJ")
    public void iVerifyTwoLinkEventFromContactRecordAndCaseForNJ() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo();
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("CONTACT_RECORD")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a correct");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify two link event from contact record and consumer for NJ")
    public void iVerifyTwoLinkEventFromContactRecordAndConsumerForNJ() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo();
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a Correct");
        assertEquals(payloadObject.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("CONTACT_RECORD")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertTrue(temp.getInt("externalId") != 0 && !temp.isNull("externalId"),
                "externalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "CONTACT_RECORD", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CONSUMER", "externalRefType is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project Name is not a Correct");
        assertEquals(payloadObject1.get().getString("action"), "create",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp1.getInt("internalId") != 0 && !temp1.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp1.getString("internalRefType"), "CONSUMER", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "CONTACT_RECORD", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp1.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");
    }

    @Then("I verify task update event with with status {string} for Nj")
    public void iVerifyTaskUpdateEventWithStatusForNj(String status) {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        assertTrue((temp.getInt("taskId") + "").chars().allMatch(Character::isDigit),
                "taskId is not a number");
        assertTrue((temp.getInt("taskTypeId") + "").chars().allMatch(Character::isDigit),
                "taskTypeId is not a number");
        assertTrue(temp.isNull("overridePriority"), "overridePriority date is not a null value");
        assertTrue((temp.getInt("defaultPriority") + "").chars().allMatch(Character::isDigit));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")),
                "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate date is not a null value");
        assertEquals(temp.getString("taskStatus"), status, "taskStatus is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy date is not a null value");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy date is not a null value");

        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy date is not a null value");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate date is not a null value");
        assertTrue(temp.isNull("dueIn"), "dueIn date is not a null value");
        assertTrue((temp.getInt("dueInDays") + "").chars().allMatch(Character::isDigit));
        assertEquals(temp.getString("source").toLowerCase(), "user",
                "source inside dataObject on not a User");
        assertTrue(temp.isNull("taskInfo") || !temp.getString("taskInfo").isEmpty(),
                "taskInfo is not a null value");

        assertTrue(temp.isNull("actionTaken"), "actionTaken date is not a null value");
        assertTrue(temp.isNull("cancelReason"), "cancelReason date is not a null value");
        assertTrue(temp.isNull("editReason") || !temp.getString("editReason").isEmpty(),
                "editReason date is not a null value");
        assertTrue(temp.isNull("holdReason") || !temp.getString("holdReason").isEmpty(),
                "holdReason date is not a null value");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition date is not a null value");
        assertTrue(temp.getString("createdBy").chars().allMatch(Character::isDigit),
                "createdBy not a userId");

        assertTrue(temp.getBoolean("escalatedFlag") || !temp.getBoolean("escalatedFlag"),
                "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), correlationId,
                "Co-relation id is not matching");
        assertFalse(temp.getString("uiid").isEmpty(), "uiid is not a string");
        assertEquals(temp.getString("action"), "UPDATE",
                "action inside dataObject on not a UPDATE");
        assertTrue(temp.isNull("actionedOn"), "actionedOn date is not a null value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "CreatedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I verify task save event payloads has correct data for NJ")
    public void verifyTaskSaveEventForNj() {
        assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(),
                "Project Name is not a BLCRM");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a Update");
        assertEquals(recordType.get().toLowerCase(), "task", "Record type is not a Task");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        assertTrue(temp.getInt("taskId") != 0 && !temp.isNull("taskId"),
                "taskId is null or equal to 0");
        assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"),
                "taskTypeId is null or equal to 0");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");

        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");

        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");

        assertEquals(temp.getString("source"), "User", "source is miss match");
        assertTrue(temp.isNull("taskInfo"), "taskInfo  is not null");
        //assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), correlationId, "correlationId is miss match");
        assertTrue(!temp.isNull("uiid"), "uiid is null");
        assertEquals(temp.getString("action"), "INSERT", "action is miss match");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"),
                "createdOn and updatedOn is miss match");
    }

    @Then("I verify task save event payloads for task created from incomplete contact record api")
    public void i_verify_task_save_event_payloads_for_task_created_from_incomplete_contact_record_api() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            String eventName = temp1.getString("eventName");

            if (eventName.equalsIgnoreCase("TASK_SAVE_EVENT")) {
                eventId.set(new JSONObject(teamjson.get(i).toString()).get("eventId").toString());
                assertEquals(temp1.getString("module"), "TASK_MANAGEMENT", "module is miss match");
                Api_Body api_res = new EventBuilder()
                        .body(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(temp1.getString("payload")))
                        .name("pl2")
                        .build();
                stg.addConsumed(api_res);
                assertEquals((int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl2.projectId"), Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId()), "Project Id is Fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name is Fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.action"), "Create", "action is Fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.recordType"), "Task", "recordType is Fail");
                assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.eventCreatedOn")), "eventCreatedOn is Fail");
                assertEquals((int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl2.dataObject.taskId"), taskType.taskId.get(), "TaskId is mismatch");
                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl2.dataObject.taskTypeId") != 0, "taskTypeId is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.overridePriority"), "overridePriority is fail");
                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl2.dataObject.defaultPriority") != 0, "defaultPriority is Fail");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.defaultDueDate")), "defaultDueDate IS NOT valid");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.overrideDueDate"), "overrideDueDate is Fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskStatus"), "Created", "taskStatus is miss match");
                assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.statusDate")), "statusDate is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.staffWorkedBy"), "staffWorkedBy is Fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.staffAssignedTo"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is mismatch");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.staffForwardBy"), "staffForwardBy is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.overridePerformedBy"), "overridePerformedBy is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.overridePerformedDate"), "overridePerformedDate is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.dueIn"), "dueIn is Fail");
                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl2.dataObject.dueInDays") != 0, "taskTypeId is Fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.source"), "System", "source is mismatch");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskInfo"), "taskInfo is Fail");
                //assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskNotes"), "Contact Record marked Incomplete due to the user being logged out during Active Contact", "taskNotes is mismatch");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.actionTaken"), "actionTaken is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.editReason"), "editReason is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.holdReason"), "holdReason is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.cancelReason"), "cancelReason is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDisposition"), "taskDisposition is Fail ");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.escalatedFlag"), "escalatedFlag is Fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.correlationId"), correlationId, "correlationId is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.uiid"), "uiid is Fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.action"), "INSERT", "action is mismatch");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.actionedOn"), "actionedOn is Fail");
                assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.createdOn")), "createdOn is fail");
                assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.updatedOn")), "updatedOn is fail");

                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl2.dataObject.taskDetails[0].taskDetailsId") != 0, "taskDetailsId is Fail");
                assertEquals((int) API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("pl2.dataObject.taskDetails[0].taskId"), taskType.taskId.get(), "TaskId is mismatch");
                assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].createdOn")), "createdOn is fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
                assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].updatedOn")), "updatedOn is fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].selectionVarchar"), "selectionVarchar is fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].selectionVarchar2"), "selectionVarchar2 is fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].selectionBoolean"), "selectionBoolean is fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].selectionNumeric"), "selectionNumeric is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].selectionDate"), "selectionDate is fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].selectionDateTime"), "selectionDateTime is fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].selectionFieldName"), "Disposition", "selectionFieldName is mismatch");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].fieldGroup"), "fieldGroup is fail");
                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].correlationId"), correlationId, "correlationId is Fail");
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("pl2.dataObject.taskDetails[0].uiid"), "uiid is fail");
            } else if (eventName.equalsIgnoreCase("LINK_EVENT")) {
                System.out.println(" ");
            } else
                assertFalse(true, "Event name not matches");
        }
    }

    @Then("I verify link events are generated for incomplete contact record api")
    public void i_verify_link_events_are_generated_for_incomplete_contact_record_api() {
        boolean caseToTask = false, taskToCase = false, consumerToTask = false, taskToConsumer = false,
                crToTask = false, taskToCR = false;

        JsonArray json1 = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");
        for (int j = 0; j < json1.size(); j++) {
            JSONObject temp1 = new JSONObject(json1.get(j).toString());
            String eventName = temp1.getString("eventName");

            if (eventName.equalsIgnoreCase("LINK_EVENT")) {
                assertEquals(temp1.getString("module"), "LINK_MANAGEMENT", "module is miss match");

                payloadObject.set(new JSONObject(new JSONObject(json1.get(j).toString()).get("payload").toString()));

                Assert.assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is Fail");
                Assert.assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name is Fail");
                Assert.assertEquals(payloadObject.get().getString("action"), "Create", "action is Fail");
                Assert.assertEquals(payloadObject.get().getString("recordType"), "Link", "recordType is Fail");
                Assert.assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "eventCreatedOn is fail");

                Assert.assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get());
                Assert.assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("uiid"), "uiid is not null");
                Assert.assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                        getInt("externalLinkId") + "").chars().allMatch(Character::isDigit), "externalLinkId is fail");

                JSONObject temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");

                if (temp.getString("internalRefType").equals("TASK") &&
                        temp.getString("externalRefType").equals
                                (APITaskManagementController.taskVO1.get().get("externalRefTypeConsumer"))) {
                    assertEquals(temp.getInt("internalId"), taskType.taskId.get(),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId") + "",
                            APITaskManagementController.taskVO1.get().get("externalRefIdConsumer"),
                            "externalId is miss match");
                    taskToConsumer = true;
                } else if (temp.getString("internalRefType").equals("TASK") &&
                        temp.getString("externalRefType").equals
                                (APITaskManagementController.taskVO1.get().get("externalRefTypeCase"))) {
                    assertEquals(temp.getInt("internalId"), taskType.taskId.get(),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId") + "",
                            APITaskManagementController.taskVO1.get().get("externalRefIdCase"),
                            "externalId is miss match");
                    taskToCase = true;
                } else if (temp.getString("internalRefType").equals("TASK") &&
                        temp.getString("externalRefType").equals
                                (APITaskManagementController.taskVO1.get().get("externalRefTypeCR"))) {
                    assertEquals(temp.getInt("internalId"), taskType.taskId.get(),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId") + "",
                            APITaskManagementController.taskVO1.get().get("externalRefIdCR"),
                            "externalId is miss match");
                    taskToCR = true;
                } else if (temp.getString("internalRefType").equals
                        (APITaskManagementController.taskVO1.get().get("externalRefTypeCR")) &&
                        temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "" +
                                    "", APITaskManagementController.taskVO1.get().get("externalRefIdCR"),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId"), taskType.taskId.get(),
                            "externalId is miss match");
                    crToTask = true;
                } else if (temp.getString("internalRefType").equals
                        (APITaskManagementController.taskVO1.get().get("externalRefTypeCase")) &&
                        temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "",
                            APITaskManagementController.taskVO1.get().get("externalRefIdCase"),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId"), taskType.taskId.get(),
                            "externalId is miss match");
                    caseToTask = true;
                } else if (temp.getString("internalRefType").equals
                        (APITaskManagementController.taskVO1.get().get("externalRefTypeConsumer")) &&
                        temp.getString("externalRefType").equals("TASK")) {
                    assertEquals(temp.getInt("internalId") + "",
                            APITaskManagementController.taskVO1.get().get("externalRefIdConsumer"),
                            "internalId is null or equal to 0");
                    assertEquals(temp.getInt("externalId"), taskType.taskId.get(),
                            "externalId is miss match");
                    consumerToTask = true;
                }
                Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")), "effectiveStartDate is fail");
                Assert.assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is Fail");
                Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn is fail");
                assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
                Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn is fail");
                assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            } else if (eventName.equalsIgnoreCase("TASK_SAVE_EVENT")) {
                System.out.println(" ");
            } else
                Assert.assertFalse(true, "Event name not matches");
        }
        assertTrue(taskToCase && taskToConsumer && taskToCR && caseToTask && consumerToTask &&
                crToTask, "All 6 Events are not generated");
    }


    @Then("I verify other fields should be null in the payload")
    public void iVerifyOtherFieldsShouldBeNullInThePayload() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        Assert.assertEquals(action.get().toLowerCase(), "create", "create is not a create");
        Assert.assertEquals(recordType.get().toLowerCase(), "staff", "Record type is not a Staff");
        Assert.assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("Regression"), "Project Name is not correct");

        JSONObject temp = payloadObject.get().getJSONObject("dataObject");

        Assert.assertTrue(temp.getInt("staffId") != 0 && !temp.isNull("staffId"), "staffId is null or equal to 0");
        assertEquals(temp.getString("firstName"), addUser.getFirstName());
        assertTrue(temp.isNull("lastName"));
        assertTrue(temp.isNull("middleName"), "middle name is not null");
        assertTrue(temp.isNull("email"), "email is not correct");
        assertTrue(temp.isNull("maxId"), "maxId is not correct");
        Assert.assertFalse(temp.isNull("correlationId"), "correlationId is null");
        Assert.assertTrue(temp.isNull("password"), "password is not null");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");
        Assert.assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdOn"), temp.getString("updatedOn"), "createdOn and updatedOn is miss match");
        assertEquals(temp.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        Assert.assertTrue(temp.isNull("uiid"), "uiid is not null");
        Assert.assertTrue(temp.isNull("startDate"), "startDate is not null");
        Assert.assertTrue(temp.isNull("endDate"), "endDate is not null");
    }

    @Then("I verify that payload contains business day details which I recently created {string}")
    public void verifyBusinessDayPayloadData(String startDate) {
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is mismatch");
        assertEquals(payloadObject.get().getString("projectName"), TM_SearchProjectStepDefs.projectname.get(),
                "Project Name is mismatch");
        assertEquals(action.get().toLowerCase(), "create", "Action is not a create");
        assertEquals(recordType.get().toLowerCase(), "business days", "Record type is not a Business Days");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("businessDaysId") + "").
                chars().allMatch(Character::isDigit), "Business Days Id is mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("businessDayName"),
                TM_BusinessDaysStepDef.businessDayNameValue, "Business Day name mismatch");

        //start date
        String temp;
        if (startDate.equals("today")) {
            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("startDate"),
                    TM_BusinessDaysStepDef.startDateValue.get(), "StartDay is mismatch");
        } else if (startDate.contains("+")) {
            temp = TM_BusinessDaysStepDef.startDateValue.get();
            String startDate1 = temp.substring(0, 2) + "/" + temp.substring(2, 4) + "/" + temp.substring(4);
            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("startDate"),
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(startDate1), "StartDay is mismatch");
        }

        //end date
        temp = TM_BusinessDaysStepDef.endDateValue.get();
        String endDate1 = temp.substring(0, 2) + "/" + temp.substring(2, 4) + "/" + temp.substring(4);
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("endDate"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(endDate1), "endDate is mismatch");

        //business days
        for (String businessDay : TM_BusinessDaysStepDef.businessDays.get()) {
            assertTrue(payloadObject.get().getJSONObject("dataObject").getString("businessDays").contains(businessDay), "Business Day not matches");
        }

        assertEquals(payloadObject.get().getJSONObject("dataObject").getBoolean("taskIndicator"),
                TM_BusinessDaysStepDef.taskIndicator.get(), "taskIndicator is mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getBoolean("srIndicator"),
                TM_BusinessDaysStepDef.srIndicator.get(), "srIndicator is mismatch");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("createdBy") + ""),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("updatedBy") + ""),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");

    }

    @Then("I verify that payload contains business day details which I recently updated {string}")
    public void verifyBusinessDayPayloadDataForUpdateEvent(String startDate) {
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(),
                "Project Id is mismatch");
        assertEquals(payloadObject.get().getString("projectName"), TM_SearchProjectStepDefs.projectname.get(),
                "Project Name is mismatch");
        assertEquals(action.get().toLowerCase(), "update", "Action is not a create");
        assertEquals(recordType.get().toLowerCase(), "business days", "Record type is not a Business Days");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "eventCreatedOn date time field IS NOT valid");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("businessDaysId") + "").
                chars().allMatch(Character::isDigit), "Business Days Id is mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getInt("projectId") + "",
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project Id is mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("businessDayName"),
                TM_BusinessDaysStepDef.businessDayNameValue.get(), "Business Day name mismatch");

        //startDate;
        String temp;
        if (startDate.equals("today")) {
            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("startDate"),
                    TM_BusinessDaysStepDef.startDateValue.get(), "StartDay is mismatch");
        } else if (startDate.contains("+")) {
            temp = TM_BusinessDaysStepDef.startDateValue.get();
            String startDate1 = temp.substring(0, 2) + "/" + temp.substring(2, 4) + "/" + temp.substring(4);
            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("startDate"),
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(startDate1), "StartDay is mismatch");
        }

        //end date
        temp = TM_BusinessDaysStepDef.endDateValue.get();
        String endDate1 = temp.substring(0, 2) + "/" + temp.substring(2, 4) + "/" + temp.substring(4);
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("endDate"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(endDate1), "endDate is mismatch");

        //business days
        for (String businessDay : TM_BusinessDaysStepDef.businessDays.get()) {
            assertTrue(payloadObject.get().getJSONObject("dataObject").getString("businessDays").contains(businessDay), "Business Day not matches");
        }
        assertEquals(payloadObject.get().getJSONObject("dataObject").getBoolean("taskIndicator"),
                TM_BusinessDaysStepDef.taskIndicator.get(), "taskIndicator is mismatch");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getBoolean("srIndicator"),
                TM_BusinessDaysStepDef.srIndicator.get(), "srIndicator is mismatch");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("createdBy") + ""),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").
                getString("updatedOn")), "updatedOn date time field IS NOT valid");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("updatedBy") + ""),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is mismatch");
    }

    @Then("I will verify action taken values in event task details array")
    public void verifyTaskDetailsArrayHasActionTakenValuesInEvents() {
        JSONArray json = payloadObject.get().getJSONObject("dataObject").getJSONArray("taskDetails");

        String[] str = CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").split(",");

        for (int i = 0; i < json.length(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());

            if (temp1.getString("selectionFieldName").equals("Disposition")) {
                assertEquals(temp1.getString("selectionVarchar"),
                        CRM_GeneralTaskStepDef.taskValues.get().get("disposition").toUpperCase(),
                        "selectionVarchar value is miss match");
            } else {
                assertEquals(temp1.getString("selectionVarchar").replaceAll("_", " "),
                        str[i].toUpperCase(), "selectionVarchar value is miss match");
                assertEquals(temp1.getString("selectionFieldName"), "Action Taken",
                        "selectionFieldName value is miss match");
            }

            assertTrue(temp1.getInt("taskId") != 0 || !temp1.isNull("taskId"),
                    "taskId is null or equal 0");
            assertTrue(temp1.getInt("taskDetailsId") != 0 || !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal 0");
            assertTrue(temp1.getInt("taskFieldId") != 0 || !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal 0");
            assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
            assertTrue(temp1.isNull("selectionDate"), "selectionVarchar is not null");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null");
            assertEquals(temp1.getString("correlationId"), correlationId, "correlationId is miss match");
        }
    }

    @Then("I send Subscribers Record GET API and Verify response has eventId and {string} and subscriberId")
    public void iInitiatedUnlinkEventsToSubscribersRecordGETAPI(String event) {
        System.out.println("Size of calls " + eventIds.size());
        for (int i = 0; i < eventIds.size(); i++) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
            subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId.get() + "/" + eventIds.get(i);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
            subscribersRecordEndPoint = subscribersRecordEndPoint.substring(0, 31);

            JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
            JSONObject temp = new JSONObject(json.get(0).toString());
            Assert.assertEquals(temp.getString("eventName"), event);
            Assert.assertEquals(temp.getInt("eventId") + "", eventIds.get(i));
            Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId);
        }
    }

    @And("I will get {string} from traceId for the end point")
    public void iWillGetCorrelationIdAsTraceIdForTheEndPoint(String correlationId) {
        if (correlationId.equals("UNLINKCRToCase")) {
            eventName.set("UNLINK");
            values = new ArrayList<>(EventsUtilities.getLogs("traceid",
                    "effectiveEndDate\\\":\\\""));
            correlationId = values.get(0);
        } else if (correlationId.equals("UNLINKCRToConsumer")) {
            eventName.set("UNLINK");
            correlationId = values.get(1);
        } else {
            correlationId = EventsUtilities.getLogs("traceid", correlationId).get(0);
        }
        this.correlationId.set(correlationId);
        eventEndPoint.set(eventEndPoint1 + correlationId);
    }

    @Then("I verify two unlink event from task and consumer payloads has correct data")
    public void iVerifyTwoUnlinkEventFromTaskAndConsumerPayloadsHasCorrectData() {
        System.out.println("payloadObject----------------");
        System.out.println(payloadObject);
        System.out.println("payloadObject1--------------");
        System.out.println(payloadObject1);
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId"),
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject.get().getString("action"), "update",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("TASK")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CONSUMER", "externalRefType is miss match");

        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!(temp.getString("createdOn").equals(temp.getString("updatedOn"))), "Created on & updated on are same");


        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject1.get().getString("action"), "update",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertEquals(temp1.getString("internalRefType"), "CONSUMER", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "TASK", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!(temp1.getString("createdOn").equals(temp1.getString("updatedOn"))), "Created on & updated on are same");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @Then("I verify two unlink event from task and case payloads has correct data")
    public void iVerifyTwoUnlinkEventFromTaskAndCasePayloadsHasCorrectData() {
        Assert.assertFalse(payloadObject.get().isNull("dataObject"));
        assertEquals(payloadObject.get().getInt("projectId"),
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject.get().getString("action"), "update",
                "Action is not a Update");
        assertEquals(payloadObject.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");

        JSONObject temp = null;
        JSONObject temp1 = null;
        if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").
                getString("internalRefType").equals("TASK")) {
            temp = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        } else {
            temp = payloadObject1.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
            temp1 = payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload");
        }

        assertTrue(temp.getInt("externalLinkId") != 0 && !temp.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");
        assertTrue(temp.getInt("internalId") != 0 && !temp.isNull("internalId"),
                "internalId is null or equal to 0");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        assertEquals(temp.getString("externalRefType"), "CASE", "externalRefType is miss match");

        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(!(temp.getString("createdOn").equals(temp.getString("updatedOn"))), "Created on & updated on are same");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is null have some value");

        Assert.assertFalse(payloadObject1.get().isNull("dataObject"));
        assertEquals(payloadObject1.get().getInt("projectId") + "",
                (ConfigurationReader.getProperty("projectName").split(" "))[0],
                "Project Id is not correct");
        assertEquals(payloadObject1.get().getString("projectName"),
                (ConfigurationReader.getProperty("projectName").split(" "))[1],
                "Project Name is not a BLCRM");
        assertEquals(payloadObject1.get().getString("action"), "update",
                "Action is not a Update");
        assertEquals(payloadObject1.get().getString("recordType").toLowerCase(), "link",
                "recordType is not a Update");
        assertTrue(EventsUtilities.isValidDate(payloadObject1.get().getString("eventCreatedOn")),
                "eventCreatedOn date time field IS NOT valid");


        assertTrue(temp1.getInt("externalLinkId") != 0 && !temp1.isNull("externalLinkId"),
                "externalLinkId is null or equal to 0");

        assertEquals(temp1.getString("internalRefType"), "CASE", "internalRefType is miss match");
        assertEquals(temp1.getString("externalRefType"), "TASK", "externalRefType is miss match");
        assertTrue(temp1.getInt("externalId") != 0 && !temp1.isNull("externalId"),
                "externalId is null or equal to 0");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveEndDate")),
                "effectiveEndDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(!(temp1.getString("createdOn").equals(temp1.getString("updatedOn"))), "Created on & updated on are same");
        assertEquals(payloadObject1.get().getJSONObject("dataObject").getString("correlationId"),
                correlationId, "correlationId not a userId");
        assertTrue(!payloadObject1.get().getJSONObject("dataObject").isNull("uiid"),
                "uiid is not null have some value");
    }

    @And("I send GET Event API and get the payload")
    public ThreadLocal<JSONObject> i_send_get_event_and_get_the_payload() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject/*.getJSONObject("events")*/.getAsJsonArray("events");

        if (eventName.equals("LINK") || eventName.equals("UNLINK")) {
            Assert.assertTrue(json.size() == 2);
            payloadObject1.set(new JSONObject(new JSONObject(json.get(1).toString()).get("payload").toString()));
        }

        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));

        correlationId.set(new JSONObject(json.get(0).toString()).getString("correlationId"));
        eventId.set(new JSONObject(json.get(0).toString()).get("eventId").toString());
        eventId1.set(new JSONObject(json.get(1).toString()).get("eventId").toString());
        eventIds.add(eventId.get());
        eventIds.add(eventId1.get());

        action.set(payloadObject.get().getString("action"));
        recordType.set(payloadObject.get().getString("recordType"));
        eventCreatedOn.set(payloadObject.get().getString("eventCreatedOn"));
        projectId.set(String.valueOf(payloadObject.get().getInt("projectId")));
        projectName.set(payloadObject.get().getString("projectName"));
        dataObject.set(payloadObject.get().getJSONObject("dataObject").toString());

        return payloadObject;
    }
}

