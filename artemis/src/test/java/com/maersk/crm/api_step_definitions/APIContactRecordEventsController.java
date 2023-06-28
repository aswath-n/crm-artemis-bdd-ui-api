package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMContactHistoryPage;
import com.maersk.crm.pages.crm.CRMAddContactInfoPage;
import com.maersk.crm.pages.crm.CRMManualContactRecordSearchPage;
import com.maersk.crm.step_definitions.CRM_AddNewEmailInfoStepDef;
import com.maersk.crm.step_definitions.CRM_AddNewPhoneNumberStepDef;
import com.maersk.crm.step_definitions.CRM_ViewAddressInformationStepDef;
import com.maersk.crm.step_definitions.CRM_ViewContactRecordHistoryStepDef;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.testng.Assert.*;


public class APIContactRecordEventsController extends CRMUtilities implements ApiBase {
    final ThreadLocal<Map<String, Object>> randomdata = ThreadLocal.withInitial(() -> new HashMap<>(getNewTestData2()));

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> correlationId = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> uiid = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<Integer> eventId = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> action = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> currentUserId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> recordType = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> eventCreatedOn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> dataObject = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<JSONObject> payloadObject = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> subscriberId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<JSONArray> subscriberList = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> projectName = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<Integer> projectId = ThreadLocal.withInitial(() -> null);
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
    public final ThreadLocal<String> externalRefId = ThreadLocal.withInitial(() -> null);
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
    public final ThreadLocal<String> emailUIprovided = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> caseMemberUI = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> emailEdited = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> phoneNumberEdited = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> associatedCaseMemberEdited = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> dateEnd = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> effectiveStartDateUI = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> wrapUpTime = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> contactType = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<Integer> contactRecordId = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<List<String>> correlationIdList = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<Map<String, String>> correlationIdAndEmailMap = ThreadLocal.withInitial(() -> new HashMap<>());
    public static final ThreadLocal<String> emailIDToBeValidated = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> phoneNumberToBeValidated = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> consumerID = ThreadLocal.withInitial(() -> null);


    private final String baseAPIUrl = ConfigurationReader.getProperty("apiEventsURI");
    private final String apiProdEventsBaseURI = ConfigurationReader.getProperty("apiProdEventsBaseURI");
    private final String contactRecordEventsEndPoint = "/app/crm/events?size=100&page=0&sort=eventId,desc";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";
    private String checkEventEndPoint = "/app/crm/events";
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();

    private final ThreadLocal<CRM_ViewAddressInformationStepDef> addressInfoStepDef = ThreadLocal.withInitial(CRM_ViewAddressInformationStepDef::new);
    private final ThreadLocal<CRMAddContactInfoPage> addContactInfoPage = ThreadLocal.withInitial(CRMAddContactInfoPage::new);
    private final ThreadLocal<CRM_AddNewPhoneNumberStepDef> addNewPhoneStepDef = ThreadLocal.withInitial(CRM_AddNewPhoneNumberStepDef::new);
    private final ThreadLocal<CRM_AddNewEmailInfoStepDef> addNewEmailStepDef = ThreadLocal.withInitial(CRM_AddNewEmailInfoStepDef::new);


    @When("I will take trace Id for Events and {string}")
    public void i_will_take_trace_Id_for_Events(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType, "createdBy", "createdOn").get(0));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @When("I initiate Event POST API")
    public void i_initiate_Event_POST_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactRecordEventsEndPoint);
    }

    @When("I initiate check Event POST API")
    public void i_initiate_check_Event_POST_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiProdEventsBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactRecordEventsEndPoint);
    }


    @When("I will initiate Event GET API")
    public void i_will_initiate_Event_GET_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);

    }

    @When("I provide the correlation in the body")
    public void I_provoide_the_corelation_in_the_body() {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("correlationId", correlationId.get());
        System.out.println("Request params:  " + requestParams.get());
    }

    @When("I provide the {string} and {string} and {string} in the body")
    public void i_provide_the_and_and_in_the_body(String eventName, String module, String correlation) {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("eventName", eventName);
        requestParams.get().addProperty("module", module);

        requestParams.get().addProperty("correlationId", correlationId.get());

        System.out.println(requestParams.get().get("eventName") + "  " + requestParams.get().get("module") + "  " + requestParams.get().get("correlationId"));
        System.out.println("Request params:  " + requestParams.get());

    }

    @Then("I will run the Event POST API and get the payload with {string} and {string} and {string}")
    public JSONObject i_will_run_the_Event_POST_API_and_get_the_payload(String eventName, String module, String correlation) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("Response string  " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
        eventNameResponse.set(new JSONObject(json.get(0).toString()).get("eventName").toString());
        System.out.println("Event name in response:  " + eventNameResponse.get());
        assertEquals(eventNameResponse.get(), eventName, "Event name isnt validated");
        System.out.println("Event name is validated");

        eventId.set(new JSONObject(json.get(0).toString()).getInt("eventId"));
        System.out.println("Event id:   " + eventId.get());
        assertTrue((eventId.get() + "").chars().allMatch(Character::isDigit), "eventId isnt valid");
        System.out.println("Event id is validated");

        moduleName.set(new JSONObject(json.get(0).toString()).get("module").toString());
        System.out.println("Module name in response is:  " + moduleName.get());
        assertEquals(moduleName.get(), module, "Module name isnt valid");
        System.out.println("Module name is validated");
        correlationId.set(new JSONObject(json.get(0).toString()).get("correlationId").toString());
        try {
            dataObject.set(payloadObject.get().getJSONObject("dataObject").toString());
            System.out.println("Data object  " + dataObject.get());
            System.out.println("trace id:  " + correlationId.get());
        } catch (Exception e) {
        }
        return payloadObject.get();
    }

    @Then("I verify that payload contains event details")
    public void i_verify_that_payload_contains_event_details() {
        projectId.set(payloadObject.get().getInt("projectId"));
        System.out.println("Project id   " + projectId.get());
        assertTrue((projectId.get() + "").chars().allMatch(Character::isDigit), "projectId isnt valid");
        assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");

        projectName.set(payloadObject.get().getString("projectName"));
        System.out.println("Project name:  " + projectName.get());
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name isnt correct");

        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("uiid" + "").chars().allMatch(Character::isDigit), "Uiid isnt valid in terms of format or no uiid");
        System.out.println("UIID generated and validated");
    }

    @Then("I will verify that payload contains save event date time fields")
    public void i_will_verify_that_payload_contains_save_event_date_time_fields() {

        createdOn.set(payloadObject.get().getJSONObject("dataObject").getString("createdOn"));
        System.out.println("Created on date time:  " + createdOn.get());
        assertTrue((EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn"))), "CreatedOn isnt valid");
        System.out.println("Created on date time field is validated");

        createdBy.set(payloadObject.get().getJSONObject("dataObject").getString("createdBy"));
        System.out.println("Created by is:  " + createdBy.get());
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "CreatedBy isnt valid");
        System.out.println("Created by field is validated");

        updatedOn.set(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"));
        System.out.println("Updated on date time:  " + updatedOn.get());
        assertTrue((EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"))), "updatedOn isnt valid for date time validation");
        System.out.println("Updated on date time field is validated");

        updatedBy.set(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"));
        System.out.println("Updated by is:  " + updatedBy.get());
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy isnt valid for date time validation");
        System.out.println("Updated by field is validated");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), payloadObject.get().getJSONObject("dataObject").getString("createdOn"), "Save Events have different values for createdOn and updatedOn");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "EventCreatedOn date time field IS NOT valid");
        System.out.println("Event created on date time field is validated");

    }

    @Then("I initiate subscribers POST API")
    public void i_initiate_subscribers_POST_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);
    }

    @Then("I provide subscriber name as {string} in the body and run subscribers POST API")
    public void i_provide_subscriber_name_as_in_the_body_and_run_subscribers_POST_API(String subscriberName) {
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

    @Then("I will check the response has event Subscriber Mapping ID for {string}")
    public void i_will_check_the_response_has_event_Subscriber_Mapping_ID_for(String eventName) {
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

    @Then("I initiate Subscribers Record GET API and run Subscribers Record GET API")
    public void i_initiate_Subscribers_Record_GET_API_and_run_Subscribers_Record_GET_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId.get() + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt 200 for Subscriber records");
    }

    @Then("I will verify response has event Id and {string} and subscriberId")
    public void i_will_verify_response_has_event_Id_and_and_subscriberId(String eventName) {
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

    @Then("I verify that payload contains contact record reason save event details")
    public void i_verify_that_payload_contains_contact_record_reason_save_event_details() {
        assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt valid for contact record reason save event");
        assertEquals(payloadObject.get().getString("recordType"), "Contact Record Reason", "Record type isnt valid for contact record reason save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record reason save event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordReasonId") + "").chars().allMatch(Character::isDigit), "ContactRecordReason id isnt valid for contact record reason save event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "ContactRecordid isnt valid for contact record reason save event");

        contactReason.set(payloadObject.get().getJSONObject("dataObject").getString("contactRecordReasonType"));
        System.out.println("Contact reason:  " + contactReason.get());
        assertEquals(contactReason.get(), "Complaint - Benefits Issues", "Reason type doesnt match for contact record reason save event");
        System.out.println("Contact reason is verified");

        reasonComments.set(payloadObject.get().getJSONObject("dataObject").getString("notes"));
        System.out.println("Contact reason comments:  " + reasonComments.get());
        assertEquals(reasonComments.get(), "First saved reason and action", "Comment doesnt match for contact record reason save event");
        System.out.println("Contact record reason is verified");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for contact record reason save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for contact record reason save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "correlationId isnt valid for contact record reason save event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record reason save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for contact record reason save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for contact record reason save event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").getJSONObject(0).getInt("contactRecordActionId") + "").chars().allMatch(Character::isDigit), "Contact record action id isnt valid for contact record reason save event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").getJSONObject(0).getInt("contactRecordReasonId")), payloadObject.get().getJSONObject("dataObject").getInt("contactRecordReasonId"), "Contact Record ReasonId doesnt match for contact record reason save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").getJSONObject(0).getString("contactRecordActionType"), "Escalated", "Contact record action type isnt valid for contact record reason save event");
        System.out.println("Contact record action is verified");


        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "createdBy isnt valid for contact record reason save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("createdOn")), "createdOn isnt valid for contact record reason save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "updatedBy isnt valid for contact record reason save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("updatedOn")), "updatedOn isnt valid for contact record reason save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("correlationId"), correlationId.get(), "correlationId doesnt match");
        assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("uiid").chars().allMatch(Character::isDigit), "uiid isnt valid");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions")
                .getJSONObject(0).isNull("comment")), "there is comment for contact actions");

    }

    @Then("I will take trace Id for Update Events and {string} {string}")
    public void i_will_take_trace_Id_for_Update_Events_and(String eventType, String eventName) {
        correlationId.set(EventsUtilities.getLogs("traceid", "updatedBy", "updatedOn", "contactRecordReasonId").get(1));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I verify that payload contains contact record reason update event details")
    public void i_verify_that_payload_contains_contact_record_reason_update_event_details() {
        assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt valid for contact record reason update event");
        assertEquals(payloadObject.get().getString("recordType"), "Contact Record Reason", "Record type isnt valid for contact record reason update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record reason update event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordReasonId") + "").chars().allMatch(Character::isDigit), "contactRecordReasonId isnt valid for contact record reason update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "contactRecordId isnt valid for contact record reason update event");

        contactReason.set(payloadObject.get().getJSONObject("dataObject").getString("contactRecordReasonType"));
        System.out.println("Contact reason:  " + contactReason.get());
        assertEquals(contactReason.get(), "Enrollment - Family Dental", "contactReason isnt valid for contact record reason update event");
        System.out.println("Contact reason is verified");

        reasonComments.set(payloadObject.get().getJSONObject("dataObject").getString("notes"));
        System.out.println("Contact reason comments:  " + reasonComments.get());
        assertEquals(reasonComments.get(), "Third last updated reason and action", "ReasonComments isnt valid for contact record reason update event");
        System.out.println("Contact record reason is verified");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "createdBy isnt valid for contact record reason update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "updatedBy isnt valid for contact record reason update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "correlationId doesnt match for contact record reason update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record reason update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for contact record reason update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for contact record reason update event");
        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), payloadObject.get().getJSONObject("dataObject").getString("createdOn"), "Contact Record Reason Update Event doesnt have different values for createdOn and updatedOn");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getInt("contactRecordActionId") + "").chars().allMatch(Character::isDigit), "contactRecordActionId isnt valid for contact record reason update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getInt("contactRecordReasonId")), payloadObject.get().getJSONObject("dataObject").getInt("contactRecordReasonId"), "contactRecordReasonId isnt valid for contact record reason update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("contactRecordActionType"), "Enrolled - Molina", "contactRecordActionType isnt valid for contact record reason update event");
        System.out.println("Contact record action is verified");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy inside actions isnt valid for contact record reason update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("createdOn")), "createdOn inside actions isnt valid for contact record reason update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy inside actions isnt valid for contact record reason update event");
        System.out.println("Updated by field is validated.");
        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(1).getString("updatedBy"), "createdBy and updatedBy inside contactRecordActions dont have different value");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("updatedOn")), "updatedOn inside actions isnt valid for contact record reason update event");
        System.out.println("Updated on date time field is validated.");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                getJSONObject(0).getString("correlationId"), correlationId.get(), "correlationId inside actions isnt valid for contact record reason update event");
        //    assertTrue(payloadObject.getJSONObject("dataObject").getJSONArray("contactRecordActions").
        //     getJSONObject(0).getString("uiid").chars().allMatch(Character::isDigit),"uiid inside actions isnt valid for contact record reason update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions")
                .getJSONObject(0).isNull("comment")), "There is cooment inside actions for contact record reason update event");
    }

    @Then("I verify that payload contains contact record comment save event details")
    public void i_verify_that_payload_contains_contact_record_comment_save_event_details() {
        assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt valid for contact record comment save event");
        assertEquals(payloadObject.get().getString("recordType"), "Contact Record Comment", "Record type isnt valid for contact record comment save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record comment save event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordCommentId") + "").chars().allMatch(Character::isDigit), "ContactRecordCommentId isnt valid for contact record comment save event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "ContactRecordId isnt valid for contact record comment save event");

        additionalComments.set(payloadObject.get().getJSONObject("dataObject").getString("comment"));
        System.out.println("Additional comments:  " + additionalComments.get());
        assertEquals(additionalComments.get(), "This is a valid Test Comments", "Comments dont match for contact record comment save event");
        System.out.println("Additional comment is validated");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "createdBy isnt valid for contact record comment save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "updatedBy isnt valid for contact record comment save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "correlationId doesnt match for contact record comment save event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record comment save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for contact record comment save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for contact record comment save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), "createdOn and updatedOn dont have same value for contact record reason save event");
    }

    @Then("I verify that payload contains contact record comment update event details")
    public void i_verify_that_payload_contains_contact_record_comment_update_event_details() {
        assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt valid for contact record comment update event");
        assertEquals(payloadObject.get().getString("recordType"), "Contact Record Comment", "Record type isnt valid for contact record comment update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record comment update event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordCommentId") + "").chars().allMatch(Character::isDigit), "contactRecordCommentId isnt valid for contact record comment update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "contactRecordId isnt valid for contact record comment update event");

        updatedComments.set(payloadObject.get().getJSONObject("dataObject").getString("comment"));
        System.out.println("Additional comments:  " + updatedComments.get());
        assertEquals(updatedComments.get(), "Updated comment", "Comments dont match for contact record comment update event");
        System.out.println("Updated comments are validated");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "createdBy isnt valid for contact record comment update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "updatedBy isnt valid for contact record comment save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "correlationId doesnt match for contact record comment save event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record comment save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for contact record comment save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for contact record comment save event");
        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), payloadObject.get().getJSONObject("dataObject").getString("createdOn"), "Contact Record Comment Update Event doesnt have different values for createdOn and updatedOn");

    }

    @When("I will take trace id for contact record comment update event {string}")
    public void i_will_take_trace_id_for_contact_record_comment_update_event(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType, "updatedBy", "updatedOn").get(1));
        System.out.println("Log gets correlation id for contact record comment update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @When("I will take trace Id for Contact Record update Event {string}")
    public void i_will_take_trace_Id_for_Contact_Record_update_Event(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", "updatedBy", "updatedOn", "contactRecordEndTime").get(3));
        System.out.println("Log gets correlation id for contact record update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @When("I will take trace Id for Contact Record update Event {string} and number {int}")
    public void i_take_trace_Id_for_Contact_Record_update_Event(String eventType, int num) {
        correlationId.set(EventsUtilities.getLogs("traceid", "updatedBy", "updatedOn", "contactRecordEndTime").get(num));
        System.out.println("Log gets correlation id for contact record update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I verify that payload contains primaryIndicator paramater as true")
    public void i_verify_that_payload_contains_primaryIndicator_paramater_as_true() {
        primaryIndicator.set(payloadObject.get().getJSONObject("dataObject").getBoolean("primaryIndicator"));
        System.out.println("The value of primary indicator:  " + primaryIndicator.get());
        assertEquals(primaryIndicator.get(), true, "primary indicator is false");
    }

    @Then("I will verify that payload contains effective start and end date time fields")
    public void i_will_verify_that_payload_contains_effective_start_and_end_date_time_fields() {
        effectiveStartDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"));
        System.out.println("EffectiveStartDate in payload:  " + effectiveStartDate.get());
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveStartDate.get()), "Effective start date isnt valid");
        System.out.println("EffectiveStartDate date time field is validated.");

        effectiveEndDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveEndDate"));
        System.out.println("EffectiveEndDate in payload:  " + effectiveEndDate.get());
        assertTrue(effectiveEndDate.get().isEmpty() | API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveEndDate.get()), "Effective end date isnt valid");
        System.out.println("EffectiveEndDate date time field is validated.");
    }

    @Then("I will verify that payload contains updated date time fields")
    public void i_will_verify_that_payload_contains_updated_date_time_fields() {

        createdOn.set(payloadObject.get().getJSONObject("dataObject").getString("createdOn"));
        System.out.println("Created on date time:  " + createdOn.get());
        assertTrue((EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn"))), "createdOn isnt valid for date time validation");
        System.out.println("Created on date time field is validated");

        createdBy.set(payloadObject.get().getJSONObject("dataObject").getString("createdBy"));
        System.out.println("Created by is:  " + createdBy.get());
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy isnt valid for date time validation");
        System.out.println("Created by field is validated");

        updatedOn.set(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"));
        System.out.println("Updated on date time:  " + updatedOn.get());
        assertTrue((EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"))), "updatedOn isnt valid for date time validation");
        System.out.println("Updated on date time field is validated");

        updatedBy.set(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"));
        System.out.println("Updated by is:  " + updatedBy.get());
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy isnt valid for date time validation");
        System.out.println("Updated by field is validated");

        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), payloadObject.get().getJSONObject("dataObject").getString("createdOn"), " Update Event doesnt have different values for createdOn and updatedOn");
        assertTrue(EventsUtilities.isValidDate(eventCreatedOn.get()), "EventCreatedOn date time field IS NOT valid");
        System.out.println("Event created on date time field is validated");

    }

    @Then("I add new address for address events to verify details")
    public void i_add_new_address_for_address_events_to_verify_details() {
        addressInfoStepDef.get().i_add_a_new_address_to_a_consumer_profile("active", "Physical");
        waitFor(10);
    }

    @When("I will take trace Id for Address save Events and {string}")
    public void i_will_take_trace_Id_for_Address_save_Events_and(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", "addressType").get(1));
        System.out.println("Log gets trace id for address save event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I verify that payload contains address details")
    public void i_verify_that_payload_contains_address_details() {
        addressStreet1.set(payloadObject.get().getJSONObject("dataObject").getString("addressStreet1"));
        System.out.println("Address street 1 in payload:   " + addressStreet1.get());
        System.out.println("Address street 1 in UI provided   " + addressInfoStepDef.get().expectedAddressOne.get());
        assertTrue(addressStreet1.get().equals(addressInfoStepDef.get().expectedAddressOne.get()), "Address Street1 isnt valid");
        System.out.println("Address street 1 is verified");

        addressStreet2.set(payloadObject.get().getJSONObject("dataObject").getString("addressStreet2"));
        System.out.println("Address street 2:    " + addressStreet2.get());
        System.out.println(addressInfoStepDef.get().expectedAddressTwo.get());
        assertTrue(addressStreet2.get().equals(addressInfoStepDef.get().expectedAddressTwo.get()), "Address Street2 isnt valid");
        System.out.println("Address street 2 is verified");

        addressCity.set(payloadObject.get().getJSONObject("dataObject").getString("addressCity"));
        System.out.println("City in payload:   " + addressCity.get());
        System.out.println("City in UI provided: " + addressInfoStepDef.get().expectedCity.get());
        assertTrue(addressCity.get().equals(addressInfoStepDef.get().expectedCity.get()), "City isnt valid");
        System.out.println("City is verified");

        addressState.set(payloadObject.get().getJSONObject("dataObject").getString("addressState"));
        System.out.println("State in the payload:   " + addressState.get());
        System.out.println("State in UI provided:  " + addressInfoStepDef.get().expectedState.get());
        assertTrue(addressState.get().equals(addressInfoStepDef.get().expectedState.get()), "State isnt valid");
        System.out.println("State is verified");

        addressZip.set(payloadObject.get().getJSONObject("dataObject").getString("addressZip"));
        System.out.println("Address zip four first in the payload:  " + addressZip.get());
        System.out.println("Zip in UI provided:  " + addressInfoStepDef.get().expectedZip.get());
        assertTrue(addressZip.get().equals(addressInfoStepDef.get().expectedZip.get()), "Address zip isnt valid");
        System.out.println("Zip code is verified");

        addressCounty.set(payloadObject.get().getJSONObject("dataObject").getString("addressCounty"));
        System.out.println("County in the payload:   " + addressCounty.get());
        System.out.println("County in UI provide:  " + addressInfoStepDef.get().expectedCounty.get());
        assertTrue(addressCounty.get().equals(addressInfoStepDef.get().expectedCounty.get()), "county isnt valid");
        System.out.println("County is verified");

        addressType.set(payloadObject.get().getJSONObject("dataObject").getString("addressType"));
        System.out.println("Address type in the payload:   " + addressType.get());
        System.out.println("Address type in UI provided:  " + addressInfoStepDef.get().expectedType.get());
        assertTrue(addressType.get().equals(addressInfoStepDef.get().expectedType.get()), "Addres type isnt valid");
        System.out.println("Address type is verified");

        effectiveStartDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"));
        System.out.println("EffectiveStartDate in the payload:  " + effectiveStartDate.get());
        System.out.println("Effective start date in UI provided:  " + addressInfoStepDef.get().effectiveStartDateUI.get());
        System.out.println(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4));
        assertTrue(addressInfoStepDef.get().effectiveStartDateUI.get().equals(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4)), "Effective start date for address isnt correct");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveStartDate.get()), "Effective start date for address isnt valid");
        System.out.println("Value of effective start date and format of it is verified");

        effectiveEndDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveEndDate"));
        System.out.println("EffectiveEndDate in the payload:  " + effectiveEndDate.get());
        System.out.println("Effective end date in UI provided:  " + addressInfoStepDef.get().effectiveEndDateUI.get());
        System.out.println(effectiveEndDate.get().substring(5, 7) + "/" + effectiveEndDate.get().substring(8, 10) + "/" + effectiveEndDate.get().substring(0, 4));
        assertTrue(addressInfoStepDef.get().effectiveEndDateUI.get().equals(effectiveEndDate.get().substring(5, 7) + "/" + effectiveEndDate.get().substring(8, 10) + "/" + effectiveEndDate.get().substring(0, 4)), "Effective end date for address isnt correct");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveEndDate.get()), "Effective end date for address isnt valid");
        System.out.println("Value of effective end date and format of it is verified");
    }

    @Then("I edit address information for address update event")
    public void i_edit_address_information_for_address_update_event() {
        addressInfoStepDef.get().i_edit_all_required_fields_for_address_information("active", "Mailing");
    }

    @Then("I will take trace Id for Address update Events and {string}")
    public void i_will_take_trace_Id_for_Address_update_Events_and(String string) {
        correlationId.set(EventsUtilities.getLogs("traceid", "addressType").get(2));
        System.out.println("Log gets trace id for address update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I verify that payload contains updated address details")
    public void i_verify_that_payload_contains_updated_address_details() {
        addressStreet1.set(payloadObject.get().getJSONObject("dataObject").getString("addressStreet1"));
        System.out.println("Address street 1 in payload:   " + addressStreet1.get());
        System.out.println("Address street 1 in UI provided   " + addressInfoStepDef.get().editedAddressOne.get());
        assertTrue(addressStreet1.get().equals(addressInfoStepDef.get().editedAddressOne.get()), "Address street1 doesnt match with updated address");
        System.out.println("Address street 1 is verified");

        addressStreet2.set(payloadObject.get().getJSONObject("dataObject").getString("addressStreet2"));
        System.out.println("Address street 2 in payload:    " + addressStreet2.get());
        System.out.println("Address street 2 in UI provided:  " + addressInfoStepDef.get().editedAddressTwo.get());
        assertTrue(addressStreet2.get().equals(addressInfoStepDef.get().editedAddressTwo.get()), "Address street2 doesnt match with updated address");
        System.out.println("Address street 2 is verified");

        addressCity.set(payloadObject.get().getJSONObject("dataObject").getString("addressCity"));
        System.out.println("City in payload:   " + addressCity.get());
        System.out.println("City in UI provided: " + addressInfoStepDef.get().editedCity.get());
        assertTrue(addressCity.get().equals(addressInfoStepDef.get().editedCity.get()), "City doesnt match with updated address");
        System.out.println("City is verified");

        addressState.set(payloadObject.get().getJSONObject("dataObject").getString("addressState"));
        System.out.println("State in the payload:   " + addressState.get());
        System.out.println("State in UI provided:  " + addressInfoStepDef.get().editedState.get());
        assertTrue(addressState.get().equals(addressInfoStepDef.get().editedState.get()), "State doesnt match with updated address");
        System.out.println("State is verified");

        addressZip.set(payloadObject.get().getJSONObject("dataObject").getString("addressZip"));
        addressZipFour.set(payloadObject.get().getJSONObject("dataObject").getString("addressZipFour"));
        adressZipTotal.set(addressZip.get() + "-" + addressZipFour.get());
        System.out.println("Address zip five first in the payload:  " + addressZip.get());
        System.out.println("Adress zip five first in UI provided: " + addressInfoStepDef.get().editedZip.get().substring(0, 5));
        assertTrue(addressZip.get().equals(addressInfoStepDef.get().editedZip.get().substring(0, 5).toString()), "Zip doesnt match with updated address");
        System.out.println("Zip five first verified");

        System.out.println("Address zip four in the payload:   " + addressZipFour.get());
        System.out.println("Zip four in UI provided: " + addressInfoStepDef.get().editedZip.get().substring(6, 9));
        assertTrue(addressZipFour.get().equals(addressInfoStepDef.get().editedZip.get().substring(6, 10)), "Lastfour Zip doesnt match with updated address");

        System.out.println("Zip code is verified");

        addressCounty.set(payloadObject.get().getJSONObject("dataObject").getString("addressCounty"));
        System.out.println("County in the payload:   " + addressCounty.get());
        System.out.println("County in UI provide:  " + addressInfoStepDef.get().editedCounty.get());
        assertTrue(addressCounty.get().equals(addressInfoStepDef.get().editedCounty.get()), "County doesnt match with updated address");
        System.out.println("County is verified");

        addressType.set(payloadObject.get().getJSONObject("dataObject").getString("addressType"));
        System.out.println("Address type in the payload:   " + addressType.get());
        System.out.println("Address type in UI provided:  " + addressInfoStepDef.get().editedType.get());
        assertTrue(addressType.get().equals(addressInfoStepDef.get().editedType.get()), "Address type doesnt match with updated address");
        System.out.println("Address type is verified");

        effectiveStartDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"));
        System.out.println("EffectiveStartDate in the payload:  " + effectiveStartDate.get());
        System.out.println("Effective start date in UI provided:  " + addressInfoStepDef.get().editedEffectiveStartDate.get());
        System.out.println(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4));
        assertTrue(addressInfoStepDef.get().editedEffectiveStartDate.get().equals(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4)), "Effective start date doesnt match with updated address");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveStartDate.get()), "Effective start date isnt valid");
        System.out.println("Value of effective start date and format of it is verified");

        effectiveEndDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveEndDate"));
        System.out.println("EffectiveEndDate in the payload:  " + effectiveEndDate.get());
        System.out.println("Effective end date in UI provided:  " + addressInfoStepDef.get().editedEffectiveEndDate.get());
        System.out.println(effectiveEndDate.get().substring(5, 7) + "/" + effectiveEndDate.get().substring(8, 10) + "/" + effectiveEndDate.get().substring(0, 4));
        assertTrue(addressInfoStepDef.get().editedEffectiveEndDate.get().equals(effectiveEndDate.get().substring(5, 7) + "/" + effectiveEndDate.get().substring(8, 10) + "/" + effectiveEndDate.get().substring(0, 4)), "Effective end date doesnt match with updated address");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveEndDate.get()), "Effective end date isnt valid");
        System.out.println("Value of effective end date and format of it is verified");
    }

    @Then("I add new email for email events to verify details and click on save")
    public void i_add_new_email_for_email_events_to_verify_details_and_click_on_save() {
        // addressInfoStepDef.get().i_enter_the_mandatory_fields_on_the_add_email_page_and_click_on_save();
        emailUIprovided.set(randomdata.get().get("email").toString());
        clearAndFillText(addContactInfoPage.get().emailAddressField, emailUIprovided.get());

        addContactInfoPage.get().associatedCaseMember.click();
        caseMemberUI.set(addContactInfoPage.get().caseMemberName.getText());

        scrollToElement(addContactInfoPage.get().dropdownLastItem);
        hover(addContactInfoPage.get().dropdownLastItem);
        addContactInfoPage.get().dropdownLastItem.click();
        waitFor(2);
        date.set(getCurrentDate());
        clearAndFillText(addContactInfoPage.get().startDate, date.get());
        dateEnd.set(getGreaterDate(100));
        clearAndFillText(addContactInfoPage.get().endDate, dateEnd.get());
        addContactInfoPage.get().saveButton.click();
        waitFor(5);
    }

    @Then("I click latest email address to edit email details")
    public void i_click_latest_email_address_to_edit_email_details() {
        scrollDownUsingActions(3);
        waitForClickablility(addContactInfoPage.get().emailAddressList.get(0), 7);
        jsClick(addContactInfoPage.get().emailAddressList.get(0));
        waitForPageToLoad(10);
        scrollUpUsingActions(3);
    }

    @Then("I add new phone number for phone events to verify details")
    public void i_add_new_phone_number_for_phone_events_to_verify_details() {
        addNewPhoneStepDef.get().i_provide_valid_data_in_all_the_fields_on_Add_Phone_Number_page();
        addNewPhoneStepDef.get().i_set_start_Date_as_and_end_Date("past", "future");
    }

    @When("I will take trace Id for Phone save Events and {string}")
    public void i_will_take_trace_Id_for_Phone_save_Events_and(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", "phoneType").get(1));
        System.out.println("Log gets trace id for phone save event:  " + correlationId.get());
    }

    @Then("I verify that payload contains phone number details")
    public void i_verify_that_payload_contains_phone_number_details() {
        phoneNumber.set(payloadObject.get().getJSONObject("dataObject").getString("phoneNumber"));
        System.out.println("Phone number in payload:    " + phoneNumber.get());
        assertEquals(phoneNumber.get(), addNewPhoneStepDef.get().phoneNumber.get(), "Phone number doesnt match for phone save event");
        System.out.println("Phone number is verified");

        phoneType.set(payloadObject.get().getJSONObject("dataObject").getString("phoneType"));
        System.out.println("Phone type:    " + phoneType.get());
        assertEquals(phoneType.get(), addNewPhoneStepDef.get().phoneType, "Phone type doesnt match for phone save event");
        System.out.println("Phone type is verified");

        associatedCaseMember.set(payloadObject.get().getJSONObject("dataObject").getString("associatedCaseMember"));
        System.out.println("Associated case member in payload:    " + associatedCaseMember.get());
        System.out.println("Associated case member in UI provided:  " + addNewPhoneStepDef.get().associatedCaseMember);
        assertTrue(associatedCaseMember.get().equals(addNewPhoneStepDef.get().associatedCaseMember), "Associated case member doesnt match for phone save event");
        System.out.println("Associated case member is verified");

        comments.set(payloadObject.get().getJSONObject("dataObject").getString("comments"));
        System.out.println("Comments in payload: " + comments.get());
        System.out.println("Comments in UI provided: " + addNewPhoneStepDef.get().comments);
        assertTrue(comments.get().equals(addNewPhoneStepDef.get().comments), "Phone comment doesnt match for phone save event");
        System.out.println("Comments is verified");

        effectiveStartDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"));
        System.out.println("EffectiveStartDate in the payload:  " + effectiveStartDate.get());
        System.out.println("Effective start date in UI provided:  " + addNewPhoneStepDef.get().effectiveStartDate.get());
        System.out.println(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4));
        assertTrue(addNewPhoneStepDef.get().effectiveStartDate.get().equals(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4)), "Effective start date doesnt match for phone save event");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveStartDate.get()), "Effective start date isnt valid for phone save event");
        System.out.println("Value of effective start date and format of it is verified");

        effectiveEndDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveEndDate"));
        System.out.println("EffectiveEndDate in the payload:  " + effectiveEndDate.get());
        System.out.println("Effective end date in UI provided:  " + addNewPhoneStepDef.get().effectiveEndDate.get());
        System.out.println(effectiveEndDate.get().substring(5, 7) + "/" + effectiveEndDate.get().substring(8, 10) + "/" + effectiveEndDate.get().substring(0, 4));
        assertTrue(addNewPhoneStepDef.get().effectiveEndDate.equals(effectiveEndDate.get().substring(5, 7) + "/" + effectiveEndDate.get().substring(8, 10) + "/" + effectiveEndDate.get().substring(0, 4)), "Effective end date doesnt match for phone save event");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveEndDate.get()), "Effective end date isnt valid for phone save event");
        System.out.println("Value of effective end date and format of it is verified");
    }

    @Then("I edit previously saved phone number for phone update events to verify details")
    public void i_edit_previously_saved_phone_number_for_phone_update_events_to_verify_details() {
        phoneNumberEdited.set(getRandomNumber(10));
        waitFor(2);
        clearAndFillText(addContactInfoPage.get().phoneNumber, phoneNumberEdited.get());
        waitForClickablility(addContactInfoPage.get().phoneType, 10);
        selectDropDown(addContactInfoPage.get().phoneType, phoneTypeHome.get());
        addContactInfoPage.get().getConsumerDropdownOption(2);
        associatedCaseMemberEdited.set(addContactInfoPage.get().associatedCaseMember.getText());
        waitFor(2);
        clearAndFillText(addContactInfoPage.get().phoneComments, comments.get());
        waitFor(5);
        addNewPhoneStepDef.get().i_set_start_Date_as_and_end_Date("current", "future");
    }

    @Then("I will take trace Id for Phone update Events and {string}")
    public void i_will_take_trace_Id_for_Phone_update_Events_and(String string) {
        correlationId.set(EventsUtilities.getLogs("traceid", "phoneType").get(2));
        System.out.println("Log gets trace id for phone update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I verify that payload contains updated phone number details")
    public void i_verify_that_payload_contains_updated_phone_number_details() {
        phoneNumber.set(payloadObject.get().getJSONObject("dataObject").getString("phoneNumber"));
        System.out.println("Phone number in payload:    " + phoneNumber.get());
        System.out.println("Phone number in UI provided: " + phoneNumberEdited.get());
        assertEquals(phoneNumber.get(), phoneNumberEdited.get(), "Edited phone number doesnt match for phone update event");
        System.out.println("Phone number is verified");

        phoneType.set(payloadObject.get().getJSONObject("dataObject").getString("phoneType"));
        System.out.println("Phone type in payload:    " + phoneType.get());
        System.out.println("Phone type in UI provided: " + phoneTypeHome.get());
        assertEquals(phoneType.get(), addNewPhoneStepDef.get().phoneTypeHome, "Edited phone type doesnt match for phone update event");
        System.out.println("Phone type is verified");

        associatedCaseMember.set(payloadObject.get().getJSONObject("dataObject").getString("associatedCaseMember"));
        System.out.println("Associated case member in payload:    " + associatedCaseMember.get());
        System.out.println("Associated case member in UI provided:  " + associatedCaseMemberEdited.get());
        assertTrue(associatedCaseMember.get().equals(associatedCaseMemberEdited.get()), "Associated case member doesnt match for phone update event");
        System.out.println("Associated case member is verified");

        comments.set(payloadObject.get().getJSONObject("dataObject").getString("comments"));
        System.out.println("Comments in payload: " + comments.get());
        System.out.println("Comments in UI provided: " + addNewPhoneStepDef.get().comments);
        assertTrue(comments.get().equals(addNewPhoneStepDef.get().comments), "Phone comment doesnt match for phone update event");
        System.out.println("Comments is verified");

        effectiveStartDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"));
        System.out.println("EffectiveStartDate in the payload:  " + effectiveStartDate.get());
        System.out.println("Effective start date in UI provided:  " + addNewPhoneStepDef.get().effectiveStartDate.get());
        System.out.println(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4));
        assertTrue(addNewPhoneStepDef.get().effectiveStartDate.get().equals(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4)), "Effective start date doesnt match for phone update event");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveStartDate.get()), "Effective start date isnt valid");
        System.out.println("Value of effective start date and format of it is verified");

        effectiveEndDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveEndDate"));
        System.out.println("EffectiveEndDate in the payload:  " + effectiveEndDate.get());
        System.out.println("Effective end date in UI provided:  " + addNewPhoneStepDef.get().effectiveEndDate.get());
        System.out.println(effectiveEndDate.get().substring(5, 7) + "/" + effectiveEndDate.get().substring(8, 10) + "/" + effectiveEndDate.get().substring(0, 4));
        assertTrue(addNewPhoneStepDef.get().effectiveEndDate.equals(effectiveEndDate.get().substring(5, 7) + "/" + effectiveEndDate.get().substring(8, 10) + "/" + effectiveEndDate.get().substring(0, 4)), "Effective end date doesnt match for phone update event");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveEndDate.get()), "Effective end date isnt valid");
        System.out.println("Value of effective end date and format of it is verified");
    }

    @When("I will take trace Id for Email save Events and {string}")
    public void i_will_take_trace_Id_for_Email_save_Events_and(String string) {
        correlationId.set(EventsUtilities.getLogs("traceid", "emailType").get(0));
        System.out.println("Log gets trace id for email save event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I verify that payload contains email address details")
    public void i_verify_that_payload_contains_email_address_details() {
        emailAddress.set(payloadObject.get().getJSONObject("dataObject").getString("emailAddress"));
        System.out.println("Email address in payload:    " + emailAddress.get());
        System.out.println("Email address in UI provided:  " + emailUIprovided.get());
        assertTrue(emailAddress.get().equals(emailUIprovided.get()), "Email address doesnt match for email save event");
        System.out.println("Email address is verified");

        emailType.set(payloadObject.get().getJSONObject("dataObject").getString("emailType"));
        System.out.println("Email type in payload:    " + emailType.get());
        System.out.println("Email type in UI provided:  " + "OFFICE");
        assertTrue(emailType.get().equals("OFFICE"), "Email type doesnt match for email save event");
        System.out.println("Email type is verified");

        associatedCaseMember.set(payloadObject.get().getJSONObject("dataObject").getString("associatedCaseMember"));
        System.out.println("Associated case member in payload:    " + associatedCaseMember.get());
        System.out.println("Associated case member in UI provided:  " + caseMemberUI.get());
        assertTrue(associatedCaseMember.get().equals(caseMemberUI.get()), "Associated case member doesnt match for email save event");
        System.out.println("Associated case member is verified");

        effectiveStartDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"));
        System.out.println("EffectiveStartDate in payload:    " + effectiveStartDate.get());
        System.out.println("effectiveStartDate in UI provided:  " + date.get());
        System.out.println(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4));
        assertTrue(date.get().equals(effectiveStartDate.get().substring(5, 7) + "/" + effectiveStartDate.get().substring(8, 10) + "/" + effectiveStartDate.get().substring(0, 4)), "Effective start date doesnt match for email save event");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveStartDate.get()), "Effective start date isnt valid");
        System.out.println("EffectiveStartDate of email save event and format of it is verified");

        effectiveEndDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveEndDate"));
        System.out.println("EffectiveEndDate in payload:    " + effectiveEndDate.get());
        System.out.println("effectiveEndDate in UI provided:  " + dateEnd.get());
        //  System.out.println(effectiveEndDate.substring(5,7)+"/"+effectiveEndDate.substring(8,10)+"/"+effectiveEndDate.substring(0,4));
        assertTrue(dateEnd.get().equals(effectiveEndDate.get().substring(5, 7) + effectiveEndDate.get().substring(8, 10) + effectiveEndDate.get().substring(0, 4)), "Effective end date doesnt match for email save event");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveEndDate.get()), "Effective end date isnt valid");
        System.out.println("EffectiveEndDate of email save event and format of it is verified");
    }

    @Then("I edit already created email for address update event and click on save")
    public void i_edit_already_created_email_for_address_update_event_and_click_on_save() {
        addNewEmailStepDef.get().i_edit_already_created_email_and_click_on_save();
    }

    @Then("I will take trace Id for Email update Events and {string}")
    public void i_will_take_trace_Id_for_Email_update_Events_and(String string) {
        correlationId.set(EventsUtilities.getLogs("traceid", string).get(1));
        System.out.println("Log gets trace id for email update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I verify that payload contains updated email address details")
    public void i_verify_that_payload_contains_updated_email_address_details() {
        emailAddress.set(payloadObject.get().getJSONObject("dataObject").getString("emailAddress"));
        System.out.println("Email address in payload:    " + emailAddress.get());
        System.out.println("Email address in UI provided:  " + addNewEmailStepDef.get().emailUIprovided1.get());
        assertTrue(emailAddress.get().equals(addNewEmailStepDef.get().emailUIprovided1.get()), "Email address doesnt match for email update event");
        System.out.println("Email address is verified");

        emailType.set(payloadObject.get().getJSONObject("dataObject").getString("emailType"));
        System.out.println("Email type in payload:    " + emailType.get());
        System.out.println("Email type in UI provided:  " + "OFFICE");
        assertTrue(emailType.get().equals("OFFICE"), "Email type doesnt match for email update event");
        System.out.println("Email type is verified");

        associatedCaseMember.set(payloadObject.get().getJSONObject("dataObject").getString("associatedCaseMember"));
        System.out.println("Associated case member in payload:    " + associatedCaseMember.get());
        System.out.println("Associated case member in UI provided:  " + caseMemberUI.get());
        assertTrue(associatedCaseMember.get().equals(caseMemberUI.get()), "Associated case member doesnt match for email update event");
        System.out.println("Associated case member is verified");

        effectiveStartDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate"));
        System.out.println("EffectiveStartDate in payload:    " + effectiveStartDate.get());
        System.out.println("effectiveStartDate in UI provided:  " + addNewEmailStepDef.get().date);
        //   System.out.println(effectiveStartDate.substring(5,7)+"/"+effectiveStartDate.substring(8,10)+"/"+effectiveStartDate.substring(0,4));
        assertTrue(addNewEmailStepDef.get().date.equals(effectiveStartDate.get().substring(5, 7) + effectiveStartDate.get().substring(8, 10) + effectiveStartDate.get().substring(0, 4)), "Effective start date doesnt match for email update event");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveStartDate.get()), "Effective start date isnt valid");
        System.out.println("EffectiveStartDate of email update event and format of it is verified");

        effectiveEndDate.set(payloadObject.get().getJSONObject("dataObject").getString("effectiveEndDate"));
        System.out.println("EffectiveEndDate in payload:    " + effectiveEndDate.get());
        System.out.println("effectiveEndDate in UI provided:  " + addNewEmailStepDef.get().endDate);
        //   System.out.println(effectiveEndDate.substring(5,7)+"/"+effectiveEndDate.substring(8,10)+"/"+effectiveEndDate.substring(0,4));
        assertTrue(addNewEmailStepDef.get().endDate.equals(effectiveEndDate.get().substring(5, 7) + effectiveEndDate.get().substring(8, 10) + effectiveEndDate.get().substring(0, 4)), "Effective end date doesnt match for email update event");
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidDate(effectiveEndDate.get()), "Effective end date isnt valid");
        System.out.println("EffectiveEndDate of email update event and format of it is verified");
    }

    @Then("I will verify that payload contains wrap up time and {string} fields")
    public void i_will_verify_that_payload_contains_wrap_up_time_and_fields(String contactTypeUI) {
        contactType.set(payloadObject.get().getJSONObject("dataObject").getString("contactType"));
        System.out.println("Contact type UI provided: " + contactTypeUI);
        assertTrue(contactTypeUI.equals(contactType.get()), "Contact type isnt verified");

        wrapUpTime.set(payloadObject.get().getJSONObject("dataObject").getString("wrapUpTime"));
        System.out.println("Wrap up time in the payload: " + wrapUpTime.get());
        assertTrue(API_THREAD_LOCAL_FACTORY.getEventsUtilitiesThreadLocal().isValidTime(wrapUpTime.get()), "Wrap up time isnt valid");
        System.out.println("actual wrapUpTime: "+wrapUpTime.get());
        assertTrue(wrapUpTime.get().equals("00:00:45"), "Wrapup time value couldnt match");
        System.out.println("Format of wrap up time is verified");
    }

    @When("I will take trace Id for Contact Record update Event for Unidentified Record{string}")
    public void i_will_take_trace_Id_for_Contact_Record_update_Event_for_Unidentified_Record(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType, "updatedBy", "updatedOn").get(1));
        System.out.println("Log gets correlation id for contact record update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @When("I will run the Event GET API and get the payload with {string} and {string} and {string}")
    public JSONObject i_will_run_the_Event_GET_API_and_get_the_payload_with(String eventName, String module, String correlation) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

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

    @Then("I will verify consumer authentication details")
    public void i_will_verify_consumer_authentication_details() {
        assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getString("recordType"), "Consumer Authentication", "Record type verified for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for consumer authentication event");

        //   assertTrue((payloadObject.getJSONObject("dataObject").getInt("consumerAuthenticationId")+"").chars().allMatch(Character::isDigit),"ConsumerAuthenticationId isnt verified for consumer authentication event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "ContactRecordId isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getBoolean("consumerAuthenticatedInd"), true, "ConsumerAuthenticationInd isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("authenticationType"), "Consumer", "Authentication type isnt valid");
        System.out.println(payloadObject.get().getJSONObject("dataObject").getInt("authenticationRefId"));
        assertEquals(payloadObject.get().getJSONObject("dataObject").getInt("authenticationRefId") + "", CRM_ViewContactRecordHistoryStepDef.consumerId.get(), "authenticationRefId isnt valid for consumer authentication event");

        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getInt("consumerAuthenticationId"), payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getInt("consumerAuthenticationId"), "First and second consumerAuthenticationId dont have different values");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getInt("consumerAuthenticationId") + "").chars().allMatch(Character::isDigit), "Consumer Authentication id isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getInt("linkRefId") + "", CRM_ViewContactRecordHistoryStepDef.consumerId.get(), "First Linkrefid isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getString("linkRefType"), "Consumer", "First Linkreftype isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getString("propertyName"), "0_caDob", "First propertyName isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getString("fieldName"), "0_caDob", "First fieldName isnt valid for consumer authentication event");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getInt("consumerAuthenticationId"), payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getInt("consumerAuthenticationId"));
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getInt("consumerAuthenticationId") + "").chars().allMatch(Character::isDigit), "Consumer Authentication id isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getInt("linkRefId") + "", CRM_ViewContactRecordHistoryStepDef.consumerId.get(), "Second Linkrefid isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getString("linkRefType"), "Consumer", "Second LinkrefType isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getString("propertyName"), "0_caCrmConsumerId", "Second Property name isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getString("fieldName"), "0_caCrmConsumerId", "Second field name isnt valid for consumer authentication event");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "CreatedBy isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "UpdatedBy isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "CorrelationId doesnt match fro consumer authentication event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), "CreatedOn and UpdatedOn dont have same value for consumer authentication save event");

    }

    @Then("I will verify contact record save event details")
    public void i_will_verify_contact_record_save_event_details() {
        assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt valid for contact record save event");
        assertEquals(payloadObject.get().getString("recordType"), "Contact Record", "RecordType isnt valid for contact record save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record save event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "contactRecordid isnt valid for contact record save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordType"), "Inbound", "contactRecordType isnt valid for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerType"), "ConsumerType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactType"), "ContactType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("preferredLanguageCode"), "PreferredLanguageCode has value for contact record save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("contactRecordStartTime")), "contactRecordStartTime isnt valid for contact record save event");
        //  assertTrue(payloadObject.getJSONObject("dataObject").isNull("contactRecordStartTime"),"contactRecordStartTime has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactRecordEndTime"), "contactRecordendTime has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerPhone"), "ConsumerPhone has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerFirstName"), "Consumer firstName has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerLastName"), "Consumer lastName has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerEmail"), "Consumer email has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactRecordStatusType"), "contactRecordStatusType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerAuthenticatedInd"), "consumerAuthenticationInd has value for contact record save event");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "CreatedBy isnt valid for consumer authentication");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT for contact record save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "UpdatedBy isnt valid for consumer authentication");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("linkRefType"), "LinkRefType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("linkRefId"), "LinkRefId has value for contact record save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), "updatedOn and createdOn dont have same values for contact record save event");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "Correlation id doesnt match for contact record save event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record save event");

        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("wrapUpTime"), "wrapUpTime has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("callDuration"), "callDuration has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("inboundCallQueue"), "inboundCallQueue has value for contact record save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactChannelType"), "Phone", "contactChannelType doesnt match for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactCampaignType"), "contactCampaignType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactReasonEditType"), "contactReasonEditType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactOutcome"), "contactOutcome has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("organizationName"), "Organization name has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("programTypes"), "programTypes has value for contact record save event");
    }

    @Given("I will verify common contact record update event details")
    public void i_will_verify_common_contact_record_update_event_details() {
        assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt valid for contact record update event");
        assertEquals(payloadObject.get().getString("recordType"), "Contact Record", "RecordType isnt valid for contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record update event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "ContactRecord id isnt valid for contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerType"), "ConsumerType has value for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactType"), "General", "ContactTypeisnt valid for contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("preferredLanguageCode"), "PreferredLangauage has value for contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("contactRecordStartTime")), "contactRecordStartTime isnt valid for contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("contactRecordEndTime")), "contactRecordStartTime isnt valid for contact record update event");
        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordStartTime"), payloadObject.get().getJSONObject("dataObject").getString("contactRecordEndTime"), "contactRecordStartTime and EndTime dont have diiferent values for contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerFirstName"), "Consumer first name has value for contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerLastName"), "Consumer last name has value for contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerEmail"), "Consumer email has value for contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerAuthenticatedInd"), "consumerAuthenticationId has value for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy isnt valid for contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy isnt valid for contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for contact record update event");
        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), payloadObject.get().getJSONObject("dataObject").getString("createdOn"), "Contact Record Update Event doesnt have different values for createdOn and updatedOn");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("linkRefType"), "consumer", "linkRefTpe isnt valid for contact record update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("linkRefId") + "").chars().allMatch(Character::isDigit), "linkRefId isnt valid for contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("linkRefId") + ""), CRM_ViewContactRecordHistoryStepDef.savedConsumerID.get(), "Consumerid didnt match with linkRefId");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "correlationId doesnt match for contact record update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record update event");
        assertTrue(EventsUtilities.isValidTime(payloadObject.get().getJSONObject("dataObject").getString("wrapUpTime")), "Wrap up time isnt valid for contact record update event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("callDuration") + "").chars().allMatch(Character::isDigit), "CallDuration isnt valid for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactChannelType"), "Phone", "contactChannelType isnt valid for contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("organizationName"), "organizationName has value for contact record update event");

    }

    @Given("I will verify contact record update event details for saving contact record")
    public void i_will_verify_contact_record_update_event_details_for_saving_contact_record() {
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordType"), "Outbound", "ContactRecordType isnt valid for contact record update event by saving");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("consumerPhone"), "3334445566", "Consumer phone isnt valid for contact record update event by saving");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordStatusType"), "Complete", "contactRecordStatusType isnt valid for contact record update event by saving");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("inboundCallQueue"), "inboundCallQueue isnt null for contact record update event by saving");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactCampaignType"), "Enrollment Reminder", "contactCampaignType doesnt have value for contact record update event by saving");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactReasonEditType"), "contactReasonEditType has value for contact record update event by saving");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactOutcome"), "Reached Successfully", "contactOutcome doesnt have value for contact record update event by saving");
        System.out.println("Wrapup Time: " + payloadObject.get().getJSONObject("dataObject").getString("wrapUpTime"));
        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("wrapUpTime").equals("00:00:50"), "Wrapup time value couldnt match");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getInt("programTypeId") + "").chars().allMatch(Character::isDigit), "First programTypeId isnt valid for contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getInt("contactRecordId")), payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId"), "First contactRecordId isnt valid for contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getString("programType")), "Program A", "First programType isnt valid for contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getString("status")), "Active", "First Status for programType isnt valid for contact record update event");

    }

    @Given("I will verify contact record update event details by updating")
    public void i_will_verify_contact_record_update_event_details_by_updating() {
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordType"), "Inbound", "ContactRecordType isnt valid for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("consumerPhone"), "3334445566", "Consumer phone isnt valid for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordStatusType"), "Complete", "contactRecordStatusType isnt valid for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").isNull("inboundCallQueue"), "inboundCallQueue has value for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactCampaignType"), "Enrollment Reminder", "contactCampaignType doesnt value for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactReasonEditType"), "Adding Additional Comment", "contactReasonEditType isnt valid for contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactOutcome"), "Reached Successfully", "contactOutcome doesnt have value for contact record update event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getInt("programTypeId") + "").chars().allMatch(Character::isDigit), "First programTypeId isnt valid for contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getInt("contactRecordId")), payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId"), "First contactRecordId isnt valid for contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getString("programType")), "Program A", "First programType isnt valid for contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getString("status")), "Active", "First Status for programType isnt valid for contact record update event");


    }

    @Given("I will verify payload contains third party contact record update event details")
    public void i_will_verify_payload_contains_third_party_contact_record_update_event_details() {
        assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt valid for third party contact record update event");
        assertEquals(payloadObject.get().getString("recordType"), "Contact Record", "RecordType isnt valid for third party contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for third party contact record update event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "ContactRecord id isnt valid for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordType"), "Inbound", "Contact record type isnt valid for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("consumerType"), "Agency", "ConsumerType isnt valid for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactType"), "Third Party", "ContactType isnt valid for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("preferredLanguageCode"), "Russian", "PreferredLangauage isnt correct for third party contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("contactRecordStartTime")), "contactRecordStartTime isnt valid for contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("contactRecordEndTime")), "contactRecordStartTime isnt valid for contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerPhone"), "Consumer phone has value for third party contact record update event");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("consumerFirstName"), "Emma", "Consumer first name isnt valid for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("consumerLastName"), "Jackie", "Consumer last name isnt valid for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("consumerEmail"), "emma@test.com", "Consumer email isnt valid for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordStatusType"), "Complete", "Contact record status type isnt valid for third party contact record update event");

        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerAuthenticatedInd"), "consumerAuthenticationId has value for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy isnt valid for third party contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy isnt valid for third party contact record update event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for third party contact record update event");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("linkRefType"), "consumer", "linkRefTpe isnt valid for contact record update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("linkRefId") + "").chars().allMatch(Character::isDigit), "linkRefId isnt valid for contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("linkRefId") + ""), CRM_ViewContactRecordHistoryStepDef.savedConsumerID.get(), "Consumerid didnt match with linkRefId");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "correlationId doesnt match for contact record update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record update event");
        assertTrue(EventsUtilities.isValidTime(payloadObject.get().getJSONObject("dataObject").getString("wrapUpTime")), "Wrap up time isnt valid for contact record update event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("callDuration") + "").chars().allMatch(Character::isDigit), "CallDuration isnt valid for contact record update event");

        System.out.println("Wrapup Time: " + payloadObject.get().getJSONObject("dataObject").getString("wrapUpTime"));
        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("wrapUpTime").equals("00:00:50"), "Wrapup time value couldnt match");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("inboundCallQueue"), "inboundCallQueu has value for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactChannelType"), "Email", "contactChannelType isnt valid for third party contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactCampaignType"), "contactCampaignType has value for third party contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactReasonEditType"), "contactReasonEditType has value for third party contact record update event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactOutcome"), "contactOutcome has value for third party contact record update event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("organizationName"), "Test Group", "organizationName isnt valid for third party contact record update event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getInt("programTypeId") + "").chars().allMatch(Character::isDigit), "First programTypeId isnt valid for third party contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getInt("contactRecordId")), payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId"), "First contactRecordId isnt valid for third party contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getString("programType")), "Program B", "First programType isnt valid for third party contact record update event");
        assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("programTypes").
                getJSONObject(0).getString("status")), "Active", "First Status for programType isnt valid for third party contact record update event");
    }

//    @Then("I wait for wrapup time")
//    public void i_wait_for_wrapup_time() {
//        waitFor(43);
//    }

    @When("I will take trace Id for Contact Record update Event for editing {string}")
    public void i_will_take_trace_Id_for_Contact_Record_update_Event_for_editing(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", "updatedBy", "updatedOn", "contactRecordEndTime").get(1));
        System.out.println("Log gets correlation id for contact record update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I wait for wrapup time")
    public void i_wait_for_wrapup_time() {
        waitFor(44);
    }


    @Then("I verify authentications array has been replaced with consumerAuthentications and response")
    public void iVerifyAuthenticationsArrayHasBeenReplacedWithConsumerAuthenticationsAndResponse() {
        assertEquals(payloadObject.get().getString("action"), "Create",
                "Action isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getString("recordType"), "Consumer Authentication",
                "Record type verified for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")),
                "Event created On isnt verified for consumer authentication event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "")
                .chars().allMatch(Character::isDigit), "ContactRecordId isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getBoolean("consumerAuthenticatedInd"), true,
                "ConsumerAuthenticationInd isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("authenticationType"), "Consumer",
                "Authentication type isnt valid");
        System.out.println(payloadObject.get().getJSONObject("dataObject").getInt("authenticationRefId"));
        assertEquals(payloadObject.get().getJSONObject("dataObject").getInt("authenticationRefId") + "",
                CRM_ViewContactRecordHistoryStepDef.consumerId.get(), "authenticationRefId isnt valid for consumer authentication event");

        JSONArray json = payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications");
        JSONObject temp = new JSONObject(json.get(0).toString());
        JSONObject temp1 = new JSONObject(json.get(1).toString());

        assertNotEquals(temp.getInt("consumerAuthenticationId"), temp1.getInt("consumerAuthenticationId"),
                "First and second consumerAuthenticationId dont have different values");
        assertTrue((temp.getInt("consumerAuthenticationId") + "").chars().allMatch(Character::isDigit),
                "Consumer Authentication id isnt valid for consumer authentication event");
        assertEquals(temp.getInt("linkRefId") + "", CRM_ViewContactRecordHistoryStepDef.consumerId.get(),
                "First Linkrefid isnt valid for consumer authentication event");
        assertEquals(temp.getString("linkRefType"), "Consumer",
                "First Linkreftype isnt valid for consumer authentication event");
        assertEquals(temp.getString("propertyName"), "caDob",
                "First propertyName isnt valid for consumer authentication event");
        assertEquals(temp.getString("fieldName"), "0_caDob",
                "First fieldName isnt valid for consumer authentication event");
        assertNotEquals(temp.getInt("consumerAuthenticationId"), temp1.getInt("consumerAuthenticationId"));
        assertTrue((temp1.getInt("consumerAuthenticationId") + "").chars().allMatch(Character::isDigit),
                "Consumer Authentication id isnt valid for consumer authentication event");
        assertEquals(temp1.getInt("linkRefId") + "", CRM_ViewContactRecordHistoryStepDef.consumerId.get(),
                "Second Linkrefid isnt valid for consumer authentication event");
        assertEquals(temp1.getString("linkRefType"), "Consumer",
                "Second LinkrefType isnt valid for consumer authentication event");
        assertEquals(temp1.getString("propertyName"), "caCrmConsumerId",
                "Second Property name isnt valid for consumer authentication event");
        assertEquals(temp1.getString("fieldName"), "0_caCrmConsumerId",
                "Second field name isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId,
                "CreatedBy isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId,
                "UpdatedBy isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId,
                "CorrelationId doesnt match fro consumer authentication event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit),
                "uiid isnt valid for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")),
                "CreatedOn date time field IS NOT valid for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")),
                "UpdatedOn date time field IS NOT valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"),
                payloadObject.get().getJSONObject("dataObject").getString("updatedOn"),
                "CreatedOn and UpdatedOn dont have same value for consumer authentication save event");
    }

    @Then("Verify genericFields are removed")
    public void verifyGenericFieldsAreRemoved() {
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText1"), "GenericFieldText1 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText2"), "GenericFieldText2 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText3"), "GenericFieldText3 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldText4"), "GenericFieldText4 has value");

        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber1"), "GenericFieldNumber1 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber2"), "GenericFieldNumber2 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber3"), "GenericFieldNumber3 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldNumber4"), "GenericFieldNumber4 has value");

        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate1"), "GenericFieldDate1 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate2"), "GenericFieldDate2 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate3"), "GenericFieldDate3 has value");
        assertFalse(payloadObject.get().getJSONObject("dataObject").has("genericFieldDate4"), "GenericFieldDate4 has value");

    }

    @When("I will save contact record id from CONTACT_RECORD_SAVE_EVENT")
    public void i_will_save_contact_record_id_from_CONTACT_RECORD_SAVE_EVENT() {
        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "ContactRecordid isnt valid for contact record reason save event");
        contactRecordId.set(payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId"));
        System.out.println("contact record id " + contactRecordId.get());
    }

    @And("I will save contact record id from CASE & CONTACT DETAILS tab Contact History")
    public void i_will_save_contact_record_id_from_CC_details() {
        contactRecordId.set(Integer.valueOf(contactHistory.contactIDs.get(0).getText()));
        System.out.println("contact record id " + contactRecordId);
    }

    @When("I search for an existing contact record by above ContactRecordId under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_above_ContactRecordId_under_Advanced_Search() {
        CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
        manualContactRecordSearchPage.manualSearchContactRecordId.sendKeys(Integer.toString(contactRecordId.get()));
        System.out.println("contact record id sent");
        manualContactRecordSearchPage.searchButton.click();
        waitFor(1);
    }

    @When("I will take trace Id for Events and {string} with index {string}")
    public void i_will_take_trace_Id_for_Events_and_with_index(String eventType, String index) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType, "createdBy", "createdOn").get(Integer.valueOf(index)));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();

    }

    @Then("I should not see Contact Id Present on Case & Contact Details Page")
    public void i_should_not_see_Contact_Id_Present_on_Case_Contact_Details_Page() {
        WebElement elem = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + contactRecordId.get() + "')]"));
        assertFalse(elem.isDisplayed());
    }

    @Then("I verify payload contains {string} details")
    public void iVerifyPayloadContainsDetails(String event) {
        if (event.equalsIgnoreCase("CONTACT_RECORD_REASON_SAVE_EVENT")) {

            assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt valid for contact record reason save event");
            assertEquals(payloadObject.get().getString("recordType"), "Contact Record Reason", "Record type isnt valid for contact record reason save event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record reason save event");

            assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordReasonId") + "").chars().allMatch(Character::isDigit), "ContactRecordReason id isnt valid for contact record reason save event");
            assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "ContactRecordid isnt valid for contact record reason save event");

            contactReason.set(payloadObject.get().getJSONObject("dataObject").getString("contactRecordReasonType"));
            System.out.println("Contact reason:  " + contactReason.get());
            assertEquals(contactReason.get(), "Complaint - Benefits Issues", "Reason type doesnt match for contact record reason save event");
            System.out.println("Contact reason is verified");

            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for contact record reason save event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for contact record reason save event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId, "correlationId isnt valid for contact record reason save event");
            assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record reason save event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for contact record reason save event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for contact record reason save event");

            assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").getJSONObject(0).getInt("contactRecordActionId") + "").chars().allMatch(Character::isDigit), "Contact record action id isnt valid for contact record reason save event");
            assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").getJSONObject(0).getInt("contactRecordReasonId")), payloadObject.get().getJSONObject("dataObject").getInt("contactRecordReasonId"), "Contact Record ReasonId doesnt match for contact record reason save event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").getJSONObject(0).getString("contactRecordActionType"), "Escalated", "Contact record action type isnt valid for contact record reason save event");
            System.out.println("Contact record action is verified");

            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy isnt valid for contact record reason save event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("createdOn")), "createdOn isnt valid for contact record reason save event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy isnt valid for contact record reason save event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("updatedOn")), "updatedOn isnt valid for contact record reason save event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("correlationId"), correlationId.get(), "correlationId doesnt match");
            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("uiid").chars().allMatch(Character::isDigit), "uiid isnt valid");
            assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions")
                    .getJSONObject(0).isNull("comment")), "there is comment for contact actions");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("effectiveStartDate"), getCurrentDateInYearFormat(), "effectiveStartDate not valid");
            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).isNull("effectiveEndDate"), "effectiveEndDate not valid");

        } else if (event.equalsIgnoreCase("CONTACT_RECORD_REASON_UPDATE_EVENT")) {

            assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt valid for contact record reason update event");
            assertEquals(payloadObject.get().getString("recordType"), "Contact Record Reason", "Record type isnt valid for contact record reason update event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record reason update event");

            assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordReasonId") + "").chars().allMatch(Character::isDigit), "contactRecordReasonId isnt valid for contact record reason update event");
            assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "contactRecordId isnt valid for contact record reason update event");

            contactReason.set(payloadObject.get().getJSONObject("dataObject").getString("contactRecordReasonType"));
            System.out.println("Contact reason:  " + contactReason.get());
            assertEquals(contactReason.get(), "Enrollment - Family Dental", "contactReason isnt valid for contact record reason update event");
            System.out.println("Contact reason is verified");

            reasonComments.set(payloadObject.get().getJSONObject("dataObject").getString("notes"));
            System.out.println("Contact reason comments:  " + reasonComments.get());
            assertEquals(reasonComments.get(), "Third last updated reason and action", "ReasonComments isnt valid for contact record reason update event");
            System.out.println("Contact record reason is verified");

            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "createdBy isnt valid for contact record reason update event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "updatedBy isnt valid for contact record reason update event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "correlationId doesnt match for contact record reason update event");
            assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record reason update event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for contact record reason update event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for contact record reason update event");
            assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), payloadObject.get().getJSONObject("dataObject").getString("createdOn"), "Contact Record Reason Update Event doesnt have different values for createdOn and updatedOn");

            assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getInt("contactRecordActionId") + "").chars().allMatch(Character::isDigit), "contactRecordActionId isnt valid for contact record reason update event");
            assertEquals((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getInt("contactRecordReasonId")), payloadObject.get().getJSONObject("dataObject").getInt("contactRecordReasonId"), "contactRecordReasonId isnt valid for contact record reason update event");

            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy inside actions isnt valid for contact record reason update event");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("createdOn")), "createdOn inside actions isnt valid for contact record reason update event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy inside actions isnt valid for contact record reason update event");
            System.out.println("Updated by field is validated.");
            assertNotEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(1).getString("updatedBy"), "createdBy and updatedBy inside contactRecordActions dont have different value");
            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("updatedOn")), "updatedOn inside actions isnt valid for contact record reason update event");
            System.out.println("Updated on date time field is validated.");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("correlationId"), correlationId.get(), "correlationId inside actions isnt valid for contact record reason update event");
            assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions")
                    .getJSONObject(0).isNull("comment")), "There is comment inside actions for contact record reason update event");

            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("effectiveStartDate"), getCurrentDateInYearFormat(), "effectiveStartDate not valid");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("contactRecordActions").
                    getJSONObject(0).getString("effectiveEndDate"), getCurrentDateInYearFormat(), "effectiveEndDate not valid");
        }
    }

    @And("I will verify consumer authentication details for NJ")
    public void iWillVerifyConsumerAuthenticationDetailsForNJ() {
        assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getString("recordType"), "Consumer Authentication", "Record type verified for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for consumer authentication event");


        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "ContactRecordId isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getBoolean("consumerAuthenticatedInd"), true, "ConsumerAuthenticationInd isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("authenticationType"), "Consumer", "Authentication type isnt valid");
        System.out.println(payloadObject.get().getJSONObject("dataObject").getInt("authenticationRefId"));


        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getInt("consumerAuthenticationId"), payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getInt("consumerAuthenticationId"), "First and second consumerAuthenticationId dont have different values");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getInt("consumerAuthenticationId") + "").chars().allMatch(Character::isDigit), "Consumer Authentication id isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getString("linkRefType"), "Consumer", "First Linkreftype isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getString("propertyName"), "caDob", "First propertyName isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getString("fieldName"), "0_caDob", "First fieldName isnt valid for consumer authentication event");


        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getInt("consumerAuthenticationId") + "").chars().allMatch(Character::isDigit), "Consumer Authentication id isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getString("linkRefType"), "Consumer", "Second LinkrefType isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getString("propertyName"), "caSsn", "Second Property name isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getString("fieldName"), "0_caSsn", "Second field name isnt valid for consumer authentication event");


        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "CorrelationId doesnt match fro consumer authentication event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), "CreatedOn and UpdatedOn dont have same value for consumer authentication save event");

    }

    @And("I will verify spoken & written languages are included in the {string} payload")
    public void verifySpokenWrittenLanguagesInPayload(String eventName) {
        String payload = payloadObject.toString();
        if (eventName.equalsIgnoreCase("CONTACT_RECORD_SAVE_EVENT")) {
            assertTrue(payload.contains("spokenLanguageCode"));
            assertTrue(payload.contains("writtenLanguageCode"));
        }
        if (eventName.equalsIgnoreCase("CONTACT_RECORD_UPDATE_EVENT")) {
            assertTrue(payload.contains("spokenLanguageCode"));
            assertTrue(payload.contains("writtenLanguageCode"));
        }
    }

    @And("I will verify contact record status is {string}")
    public void iWillVerifyContactRecordStatusIs(String arg0) {
        assertEquals(payloadObject.get().getJSONObject("dataObject").get("contactRecordStatusType"), arg0, "contactRecordStatusType is not" + arg0);
    }

    @When("I will take trace Id for Contact Record update Event after the logout {string}")
    public void i_will_take_trace_Id_for_Contact_Record_update_Event_after_the_logout(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", "updatedBy", "updatedOn", "contactRecordEndTime").get(0));
        System.out.println("Log gets correlation id for contact record update event:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I will verify {string} save and update events for the captured trace IDs")
    public void i_will_verify_email_phone_num_save_and_update_events_for_the_captured_trace_IDs(String event) {
        String events = null;
        int count = 0;
        String consolidatedEventsName = "";

        if (event.contains("email")) {
            events = "EMAIL_UPDATE_EVENT,EMAIL_SAVE_EVENT";

        } else if (event.contains("phone")) {
            events = "PHONE_UPDATE_EVENT,PHONE_SAVE_EVENT";
        }

        for (Map.Entry<String, String> map : correlationIdAndEmailMap.get().entrySet()) {
            eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + map.getKey();
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiProdEventsBaseURI);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactRecordEventsEndPoint);
            requestParams.set(new JsonObject());
            requestParams.get().addProperty("correlationId", map.getKey());
            System.out.println("Request params:  " + requestParams.get());

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
            System.out.println("Response string  " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

            JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
            payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));

            eventNameResponse.set(new JSONObject(json.get(0).toString()).get("eventName").toString());
            System.out.println("Event name in response:  " + eventNameResponse.get());

            if (eventNameResponse.get().equalsIgnoreCase(events.split(",")[count]) && event.contains("email")) {
                System.out.println("Event name matched in response:  " + eventNameResponse.get());
                System.out.println("Email id to be validated in response:  " + eventNameResponse.get());
                if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(map.getValue())) {
                    System.out.println("Email id found in response:  " + emailIDToBeValidated.get());
                    consolidatedEventsName = consolidatedEventsName + eventNameResponse.get();
                }
                System.out.println("correlationId before map:  " + correlationId.get());
                System.out.println("Email id before map:  " + emailIDToBeValidated.get());
            } else if (event.contains("phone")) {
                System.out.println("Event name matched in response:  " + eventNameResponse.get());
                System.out.println("phone num to be validated in response:  " + eventNameResponse.get());
                if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(map.getValue())) {
                    System.out.println("phone num found in response:  " + phoneNumberToBeValidated.get());
                    consolidatedEventsName = consolidatedEventsName + eventNameResponse.get();
                }
                System.out.println("correlationId before map:  " + correlationId.get());
                System.out.println("Phone num before map:  " + phoneNumberToBeValidated.get());
            }
            count++;
        }
        System.out.println("Consolidated events before validation: " + consolidatedEventsName);
        assertTrue(consolidatedEventsName.toLowerCase().contains("updated"), "UPDATED event is not captured.");

    }


    @When("I will capture required trace ID out of multiple matching Event Type {string} and Event Name {string}")
    public void i_will_generate_all_the_trace_Ids_for_Phone_Or_Email_Or_Address_Events(String eventType, String eventName) {
        List<String> correlationIdList = EventsUtilities.getLogs("traceid", eventType, "createdBy", "createdOn");
        System.out.println("List of all correlation id's found in this current transaction:  " + correlationIdList.toString());
        int listSize = correlationIdList.size();
        for (int counter = 0; counter < listSize; counter++) {
            String currentCorrelationId = correlationIdList.get(counter);
            eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + currentCorrelationId;
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactRecordEventsEndPoint);
            requestParams.set(new JsonObject());
            requestParams.get().addProperty("correlationId", currentCorrelationId);
            System.out.println("Request params:  " + requestParams.get());

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
            System.out.println("Response string  " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

            JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
            if (json.size() > 0) {
                payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));

                eventNameResponse.set(new JSONObject(json.get(0).toString()).get("eventName").toString());
                System.out.println("Event name in response:  " + eventNameResponse.get());

                if (eventNameResponse.get().equalsIgnoreCase(eventName) && eventName.contains("EMAIL")) {
                    System.out.println("Event name matched in response:  " + eventNameResponse.get());
                    System.out.println("Email id to be validated in response:  " + eventNameResponse.get());
                    if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(emailIDToBeValidated.get())) {
                        System.out.println("Email id found in response:  " + emailIDToBeValidated.get());
                        correlationId.set(currentCorrelationId);
                        correlationIdAndEmailMap.get().put(correlationId.get(), emailIDToBeValidated.get());
                        System.out.println("correlationId before map:  " + correlationId.get());
                        System.out.println("Email id before map:  " + emailIDToBeValidated.get());
                        break;
                    }
                } else if (eventNameResponse.get().equalsIgnoreCase(eventName) && eventName.contains("PHONE")) {
                    System.out.println("Event name matched in response:  " + eventNameResponse.get());
                    System.out.println("Phone Num to be validated in response:  " + phoneNumberToBeValidated.get());
                    if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(phoneNumberToBeValidated.get())) {
                        System.out.println("Phone Num found in response:  " + phoneNumberToBeValidated.get());
                        correlationId.set(currentCorrelationId);
                        System.out.println("correlationId before map:  " + correlationId.get());
                        System.out.println("phone Num before map:  " + phoneNumberToBeValidated.get());
                        correlationIdAndEmailMap.get().put(correlationId.get(), phoneNumberToBeValidated.get());
                        break;
                    }
                } else if (eventNameResponse.get().equalsIgnoreCase(eventName) && eventName.contains("AUTHENTICATION")) {
                    System.out.println("Event name matched in response:  " + eventNameResponse.get());
                    correlationIdAndEmailMap.get().put("createdOn", new JSONObject(json.get(0).toString()).get("createdOn").toString());
                    correlationIdAndEmailMap.get().put("createdBy", new JSONObject(json.get(0).toString()).get("createdBy").toString());
                    correlationIdAndEmailMap.get().put("module", new JSONObject(json.get(0).toString()).get("module").toString());
                    break;
                }
            } else {
                System.out.println("JSON response contains no objects, size is 0 ");
            }
        }
    }

    @When("I select another consumer from dropdown in edit email page")
    public void i_select_another_consumer_from_dropdown_in_edit_email_page() {
        addContactInfoPage.get().addressConsumer.click();
        addContactInfoPage.get().emailFirstConsumer.click();
        waitFor(2);
        consumerID.set(addContactInfoPage.get().addressConsumer.getText());
        System.out.println(consumerID.get());
        addContactInfoPage.get().saveButton.click();
    }

    @When("I will capture required externalRefId and externalRefType out of Event Type {string} and Event Name {string}")
    public void i_will_capture_required_externalRefId_and_externalRefType_out_of_Event_Type_and_Event_Name(String eventType, String eventName) {
        List<String> correlationIdList = EventsUtilities.getLogs("traceid", eventType, "createdBy", "createdOn");
        System.out.println("List of all correlation id's found in this current transaction:  " + correlationIdList.toString());
        int listSize = correlationIdList.size();
        for (int counter = 0; counter < listSize; counter++) {
            String currentCorrelationId = correlationIdList.get(counter);
            eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + currentCorrelationId;
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri("https://mars-event-api-qa.apps.non-prod.pcf-maersk.com");
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/events?size=100&page=0&sort=eventId,desc");
            requestParams.set(new JsonObject());
            requestParams.get().addProperty("correlationId", currentCorrelationId);
            System.out.println("Request params:  " + requestParams.get());

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
            System.out.println("Response string  " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

            JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
            if (json.size() > 0) {
                payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));

                eventNameResponse.set(new JSONObject(json.get(0).toString()).get("eventName").toString());
                System.out.println("Event name in response:  " + eventNameResponse.get());

                if (eventNameResponse.get().equalsIgnoreCase(eventName) && eventName.contains("EMAIL")) {
                    assertTrue(payloadObject.get().getString("externalRefId").contains(consumerID.get()));
                    assertTrue(payloadObject.get().getString("externalRefType").contains("consumer"));
                }
            }
        }
    }

    @Then("I will see the following updated fields")
    public void iWillSeeTheFollowingUpdatedFields(List<Map<String, String>> data1) {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactRecordEventsEndPoint.replace("100", "10"));
        Map<String, String> data = new HashMap<>();
        data.put("eventName", "CONSUMER_UPDATE_EVENT");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/empty.json");
        String jsonBody = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + jsonBody);
        String payloadWith = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(jsonBody, data);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(payloadWith);
        System.out.println("Response string  " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");

        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));
        System.out.println(payloadObject.get().getJSONObject("dataObject").get("genderCode").toString());
        for (Map<String, String> col : data1) {
            System.out.println(col.get("Field1") + "-->" + payloadObject.get().getJSONObject("dataObject").get(col.get("Field1")).toString());
            System.out.println(col.get("Field2") + "-->" + payloadObject.get().getJSONObject("dataObject").get(col.get("Field2")).toString());
            System.out.println(col.get("Field3") + "-->" + payloadObject.get().getJSONObject("dataObject").get(col.get("Field3")).toString());

            assertTrue(payloadObject.get().getJSONObject("dataObject").get(col.get("Field1")).toString().contains(LocalDate.now().toString()), col.get("Field1") + " is failed");
            assertTrue(payloadObject.get().getJSONObject("dataObject").get(col.get("Field2")).toString().contains(ConfigurationReader.getProperty("createdByIdServiceAccountOne")), col.get("Field2") + " is failed");
            assertTrue(payloadObject.get().getJSONObject("dataObject").get(col.get("Field3")).toString().contains("Female"), col.get("Field3") + " is failed");
        }
    }


    @Then("I will verify contact record save event details for Cover VA has ApplicationID field")
    public void i_will_verify_contact_record_save_event_details_for_Cover_VA_has_ApplicationID_field() {
        assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt valid for contact record save event");
        assertEquals(payloadObject.get().getString("recordType"), "Contact Record", "RecordType isnt valid for contact record save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event CreatedOn isnt valid for contact record save event");

        assertTrue((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + "").chars().allMatch(Character::isDigit), "contactRecordid isnt valid for contact record save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactRecordType"), "Inbound", "contactRecordType isnt valid for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerType"), "ConsumerType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactType"), "ContactType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("preferredLanguageCode"), "PreferredLanguageCode has value for contact record save event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("contactRecordStartTime")), "contactRecordStartTime isnt valid for contact record save event");
        //  assertTrue(payloadObject.getJSONObject("dataObject").isNull("contactRecordStartTime"),"contactRecordStartTime has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactRecordEndTime"), "contactRecordendTime has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerPhone"), "ConsumerPhone has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerFirstName"), "Consumer firstName has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerLastName"), "Consumer lastName has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerEmail"), "Consumer email has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactRecordStatusType"), "contactRecordStatusType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerAuthenticatedInd"), "consumerAuthenticationInd has value for contact record save event");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "CreatedBy isnt valid for consumer authentication");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT for contact record save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "UpdatedBy isnt valid for consumer authentication");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("linkRefType"), "LinkRefType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("linkRefId"), "LinkRefId has value for contact record save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), "updatedOn and createdOn dont have same values for contact record save event");

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId.get(), "Correlation id doesnt match for contact record save event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for contact record save event");

        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("wrapUpTime"), "wrapUpTime has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("callDuration"), "callDuration has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("inboundCallQueue"), "inboundCallQueue has value for contact record save event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("contactChannelType"), "Phone", "contactChannelType doesnt match for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactCampaignType"), "contactCampaignType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactReasonEditType"), "contactReasonEditType has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("contactOutcome"), "contactOutcome has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("organizationName"), "Organization name has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("programTypes"), "programTypes has value for contact record save event");
        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("applicationId"), "applicationId has value for contact record save event");
    }


    @When("I will take trace Id for Contact Update Events and {string}")
    public void i_will_take_trace_Id_for_Update_Events(String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType, "createdBy", "createdOn").get(2));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Given("I will take trace Id for Event with exact match on {string}")
    public void i_will_take_trace_Id_for_Event_with_exact_match_on(String keyword) {
        System.out.println(String.format("Looking for traceid and filtering LogEntries on keyword \"%s\"", keyword));
        List<String> traceIds = EventsUtilities.getLogs("traceid", keyword);
        correlationId.set(EventsUtilities.onlyOneUnique(traceIds));
        System.out.println("Found traceid: " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @When("I initiate Event POST API to get only 1 page")
    public void i_initiate_Event_POST_API_getFirst_page() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/events?size=1&page=0&sort=eventId,desc");
    }


    @Then("I will verify response containse following data")
    public void i_will_verify_response_containse(Map<String, String> data) {
        for (int i = 0; i < data.size(); i++) {
            boolean isPresent = payloadObject.get().getJSONObject("dataObject").getJSONArray("projectFields").getJSONObject(i).toString().contains(data.get("projectFields[" + i + "]"));
            assertTrue(isPresent, "value not exist in response");
        }

    }

    @Then("I will verify events response capture updated data")
    public void i_will_verify_response_have_updated_data(Map<String, String> data) {
        String acualID = payloadObject.get().getJSONArray("consumerIdExternal").getJSONObject(0).getString("consumerIdExternal");
        System.out.println("acualID " + acualID);
        assertEquals(acualID, data.get("consumerIdExternal[0].consumerIdExternal"), "ID not match");

    }

    @When("I will provide payload with event name {string} and createdBy {string}")
    public void payload_data_call_to(String eventName, String createdBy) {
        waitFor(5);
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("eventName", eventName);
        requestParams.get().addProperty("createdBy", createdBy);
        System.out.println("Request params:  " + requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt 200");
    }

    @Then("I will verify CONSUMER_SUBSCRIPTION_UPDATE event is published with blocked phone number")
    public void verify_CONSUMER_SUBSCRIPTION_UPDATE_response() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(phoneNumberToBeValidated.get()), "ERROR actual phone  " + phoneNumberToBeValidated.get());
    }

    @Then("I will verify CONSUMER_AUTHENTICATION_SAVE_EVENT response")
    public void verify_CONSUMER_AUTHENTICATION_SAVE_EVENT_response() {
        projectName.set(payloadObject.get().getString("projectName"));
        assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name is not correct");

        assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for CONSUMER_AUTHENTICATION_SAVE_EVENT");
        assertEquals(payloadObject.get().getString("recordType"), "Consumer Authentication", "Record type verified for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for consumer authentication event");

        assertEquals((payloadObject.get().getJSONObject("dataObject").getInt("contactRecordId") + ""), contactRecordId + "", "ContactRecordId isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getBoolean("consumerAuthenticatedInd"), true, "ConsumerAuthenticationInd isnt verified for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("authenticationType"), "Consumer", "Authentication type isnt valid");

        assertNotEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getInt("consumerAuthenticationId"), payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getInt("consumerAuthenticationId"), "First and second consumerAuthenticationId dont have different values");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getInt("consumerAuthenticationId") + "").chars().allMatch(Character::isDigit), "Consumer Authentication id isnt valid for consumer authentication event");

        assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getString("propertyName").contains("Dob"), "DOB propertyName isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(0).getString("fieldName"), "0_caDob", "DOB fieldName isnt valid for consumer authentication event");

        assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getString("propertyName").contains("Ssn"), "Ssn propertyName isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(1).getString("fieldName"), "0_caSsn", "Ssn fieldName isnt valid for consumer authentication event");

        if (projectName.get().equalsIgnoreCase("IN-EB")) {
            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(2).getString("propertyName").contains("HomeAddress"), "HomeAddress propertyName isnt valid for consumer authentication event");
        } else if (projectName.get().equalsIgnoreCase("NJ-SBE")) {
            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(2).getString("propertyName").contains("Phonenumber"), "Phonenumber propertyName isnt valid for consumer authentication event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(2).getString("fieldName"), "0_caPhonenumber", "Phonenumber fieldName isnt valid for consumer authentication event");

            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(3).getString("propertyName").contains("HomeAddress"), "HomeAddress propertyName isnt valid for consumer authentication event");
        } else {
            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(2).getString("propertyName").contains("ConsumerId"), "ConsumerId propertyName isnt valid for consumer authentication event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(2).getString("fieldName"), "0_caCrmConsumerId", "ConsumerId fieldName isnt valid for consumer authentication event");

            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(3).getString("propertyName").contains("Phonenumber"), "Phonenumber propertyName isnt valid for consumer authentication event");
            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(3).getString("fieldName"), "0_caPhonenumber", "Phonenumber fieldName isnt valid for consumer authentication event");

            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerAuthentications").getJSONObject(4).getString("propertyName").contains("HomeAddress"), "HomeAddress propertyName isnt valid for consumer authentication event");
        }

        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "CreatedBy isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "UpdatedBy isnt valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId, "CorrelationId doesnt match fro consumer authentication event");
        assertTrue((payloadObject.get().getJSONObject("dataObject").getString("uiid")).chars().allMatch(Character::isDigit), "uiid isnt valid for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for consumer authentication event");
        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for consumer authentication event");
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"), "CreatedOn and UpdatedOn dont have same value for consumer authentication save event");
    }
}
