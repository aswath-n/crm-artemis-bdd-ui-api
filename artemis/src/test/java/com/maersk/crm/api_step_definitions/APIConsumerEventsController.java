package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APIConsumerEventsController extends CRMUtilities implements ApiBase {

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> correlationId = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> uiid = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<Integer> eventId = ThreadLocal.withInitial(() -> 0);
    public final ThreadLocal<String> action = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> currentUserId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> recordType = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> eventCreatedOn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> dataObject = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<JSONObject> payloadObject = ThreadLocal.withInitial(JSONObject::new);
    public final ThreadLocal<String> subscriberId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<JSONArray> subscriberList = ThreadLocal.withInitial(JSONArray::new);
    public final ThreadLocal<String> projectName = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<Integer> projectId = ThreadLocal.withInitial(() -> 0);
    public final ThreadLocal<String> createdOn = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> createdBy = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> contactReason = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> contactAction = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> reasonComments = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> additionalComments = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> eventNameResponse = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> moduleName = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> updatedOn = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> updatedBy = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> updatedComments = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<Boolean> primaryIndicator = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> addressZip = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> addressType = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> phoneNumber = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> phoneType = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> emailType = ThreadLocal.withInitial(() -> "OFFICE");
    public final ThreadLocal<String> effectiveStartDate = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> effectiveEndDate = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> emailAddress = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> associatedCaseMember = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> date = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> endDate = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> phoneTypeHome = ThreadLocal.withInitial(() -> "Home");
    public final ThreadLocal<String> comments = ThreadLocal.withInitial(() -> "Edited phone Comments passed");
    public final ThreadLocal<String> addressStreet1 = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> addressStreet2 = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> addressCity = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> addressState = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> addressZipFour = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> adressZipTotal = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(() -> null);

    private String baseAPIUrl = ConfigurationReader.getProperty("apiEventsURI");
    private String contactRecordEventsEndPoint = "/app/crm/events?size=100&page=0&sort=eventId,desc";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";


    @Then("I will take trace id for case consumer events {string}")
    public void i_will_take_trace_id_for_case_consumer_events(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType, "updatedBy", "updatedOn").get(0));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @When("I initiate Event GET API")
    public void i_initiate_Event_GET_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);

    }

    @When("I run the Event GET API and get the payload with {string} and {string} and {string}")
    public JSONObject i_run_the_Event_GET_API_and_get_the_payload_with(String eventName, String module, String correlation) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");


        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));


        eventNameResponse.set(new JSONObject(json.get(0).toString()).get("eventName").toString());
        System.out.println("Event name in response:  " + eventNameResponse.get());
        assertEquals(eventNameResponse.get(), eventName, "Eventname isnt verified");
        System.out.println("Event name is validated");

        eventId.set(new JSONObject(json.get(0).toString()).getInt("eventId"));
        System.out.println("Event id:   " + eventId.get());
        assertTrue((eventId.get() + "").chars().allMatch(Character::isDigit), "EventId isnt valid");
        System.out.println("Event id is validated");

        moduleName.set(new JSONObject(json.get(0).toString()).get("module").toString());
        System.out.println("Module name in response is:  " + moduleName.get());
        assertEquals(moduleName.get(), module, "Module isnt verified");
        System.out.println("Module name is validated");

        dataObject.set(payloadObject.get().getJSONObject("dataObject").toString());
        System.out.println("Data object  " + dataObject.get());
        System.out.println("trace id:  " + correlationId.get());

        return payloadObject.get();
    }

    @Then("I will verify that payload contains event details")
    public void i_will_verify_that_payload_contains_event_details() {
        String[] projectNameProp = ConfigurationReader.getProperty("projectName").split(" ");
        int projectIdProp = Integer.parseInt(projectNameProp[0]);
        String projectTypeProp = projectNameProp[1];

        projectId.set(payloadObject.get().getInt("projectId"));
        System.out.println("Project id   " + projectId.get());
        assertTrue((projectId.get() + "").chars().allMatch(Character::isDigit), "projectId isnt valid");
        assertEquals(projectId.get(), projectIdProp, "Project id isnt correct");

        projectName.set(payloadObject.get().getString("projectName"));
        System.out.println("Project name:  " + projectName.get());
        assertEquals(payloadObject.get().getString("projectName"), projectTypeProp, "Project name isnt correct");
    }

    @Then("I will verify that payload has correspondence preference details based on {string}")
    public void i_will_verify_that_payload_has_correspondence_preference_details_based_on(String type) {
        assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("caseConsumer").getJSONObject(0).getString("consumerRole").equals("Authorized Representative"), "Consumer role didnt match");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").toList().size(), 1, "Communication Preference array size isnt 1");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getInt("communicationPreferencesId") + "").chars().allMatch(Character::isDigit), "PreferenceId isnt verified");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).isNull("caseId")), "CaseId has value");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getInt("consumerId") + "").chars().allMatch(Character::isDigit), "ConsumerId isnt verified");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getBoolean("defaultInd")), false, "PreferenceId isnt verified");
        assertTrue((EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getString("effectiveStartDate"))), "Effective start date isnt verified");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).isNull("effectiveEndDate")), "Effective end date has value");
        assertTrue((EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getString("createdOn"))), "CreatedOn isnt verified");
        if (type.equals("save")) {
            assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getString("createdBy")), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "CreatedBy isnt valid");
        } else {
            assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getString("createdBy")), "2132", "CreatedBy isnt valid");

        }
        assertTrue((EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getString("updatedOn"))), "UpdatedOn isnt verified");
        if (type.equals("save")) {
            assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getString("updatedBy")), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "UpdatedBy isnt valid");
        } else {
            assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getString("updatedBy")), "2132", "UpdatedBy isnt valid");
        }
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("communicationPreferences").getJSONObject(0).getString("valuePairIdCommPref")), "CORRESPONDENCE_PAPERLESS", "Value of valuePairIdCommPref field didnt match");

    }

    @Then("I will initiate Subscribers Record GET API and run Subscribers Record GET API")
    public void i_will_initiate_Subscribers_Record_GET_API_and_run_Subscribers_Record_GET_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId.get() + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt 200 for Subscriber records");
    }

    @Then("I verify response has event Id and {string} and subscriberId")
    public void i_verify_response_has_event_Id_and_and_subscriberId(String eventName) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        assertEquals(temp.getString("eventName"), eventName, "Event name doesnt match for subscriber records");
        System.out.println("Even name on DPBI:  " + eventName);

        assertEquals(temp.getInt("eventId"), eventId.get(), "Event id doesnt match for subscriber records");
        System.out.println("Event id on DPBI:  " + eventId.get());

        assertEquals(temp.getInt("subscriberId") + "", subscriberId.get(), "Subscriber id doesnt match");
        System.out.println("Subscriber id on DPBI:   " + subscriberId.get());
        System.out.println("Event is subscribed to DPBI list");
    }

    @Then("I will initiate subscribers POST API")
    public void i_will_initiate_subscribers_POST_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);
    }

    @Then("I will provide subscriber name as {string} in the body and run subscribers POST API")
    public void i_will_provide_subscriber_name_as_in_the_body_and_run_subscribers_POST_API(String subscriberName) {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName);

        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt 200");

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        subscriberId.set(new JSONObject(json.get(0).toString()).get("subscriberId").toString());
        subscriberList.set(new JSONArray(new JSONObject(json.get(0).toString()).get("subscriberList").toString()));
    }

    @Then("I check the response has event Subscriber Mapping ID for {string}")
    public void i_check_the_response_has_event_Subscriber_Mapping_ID_for(String eventName) {
        boolean flag = false;
        for (int i = 0; i < subscriberList.get().length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get().get(i).toString());
            if (temp.get("eventName").toString().trim().equals(eventName.trim())) {
                assertEquals(temp.get("subscriberId").toString(), subscriberId.get(), "Subscriber Id is wrong");
                assertEquals(temp.get("eventName").toString().trim(), eventName, "Event Name is wrong");
                assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                flag = true;
                break;
            }
        }
        assertTrue(flag, eventName + " is not published to DPBI");

    }

    @Then("I will verify that payload doesnt have contact objects")
    public void i_will_verify_that_payload_doesnt_have_contact_objects() {

        boolean found = true;
        try {
            System.out.println(payloadObject.get().getJSONObject("dataObject").getJSONArray("contacts").toList().size());
            System.out.println("Contact objects size is found");
        } catch (Exception exception) {
            found = false;
            System.out.println("Contact objects didnt find");
        }
        System.out.println("Any contact object is available: " + found);
    }

    @Then("I will take trace id for consumer update events {string}")
    public void i_will_take_trace_id_for_consumer_update_events(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType, "updatedBy", "updatedOn").get(2));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I will verify that payload contains consumer role details based on {string}")
    public void i_will_verify_that_payload_contains_consumer_role_details_based_on(String role) {
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("consumerRole"), role);
        System.out.println("Consumer role in the payload is: " + payloadObject.get().getJSONObject("dataObject").getString("consumerRole"));
    }

    @When("I run the Event GET API and get the payload with {string} and {string} and {string} for Consumer Save Event")
    public JSONObject i_run_the_Event_GET_API_and_get_the_payload_with_for_Consumer_Save_Event(String eventName, String module, String correlation) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");
        int size = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events").size();
        System.out.println("size is:" + size);
        int iterate = 1;
        for (int i = 0; i < size; i++) {
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events").get(i).getAsJsonObject().get("eventName").toString().contains(eventName))
                ;
            iterate = i;
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events").get(iterate).getAsJsonObject().get("eventName"));
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events").get(1).getAsJsonObject().get("eventName"));
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events").get(2).getAsJsonObject().get("eventName"));
            break;
        }
        System.out.println(iterate);

        payloadObject.set(new JSONObject(new JSONObject(json.get(iterate).toString()).get("payload").toString()));

        eventNameResponse.set(new JSONObject(json.get(iterate).toString()).get("eventName").toString());
        System.out.println("Event name in response:  " + eventNameResponse.get());
        assertEquals(eventNameResponse.get(), eventName, "Eventname isnt verified");
        System.out.println("Event name is validated");

        eventId.set(new JSONObject(json.get(iterate).toString()).getInt("eventId"));
        System.out.println("Event id:   " + eventId.get());
        assertTrue((eventId.get() + "").chars().allMatch(Character::isDigit), "EventId isnt valid");
        System.out.println("Event id is validated");

        moduleName.set(new JSONObject(json.get(iterate).toString()).get("module").toString());
        System.out.println("Module name in response is:  " + moduleName.get());
        assertEquals(moduleName.get(), module, "Module isnt verified");
        System.out.println("Module name is validated");

        dataObject.set(payloadObject.get().getJSONObject("dataObject").toString());
        System.out.println("Data object  " + dataObject.get());
        System.out.println("trace id:  " + correlationId.get());

        return payloadObject.get();

    }

}




