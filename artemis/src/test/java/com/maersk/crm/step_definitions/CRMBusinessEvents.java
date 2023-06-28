package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.api_step_definitions.APIPhoneEmailAddressEventsCheckController;
import com.maersk.crm.utilities.*;
import com.maersk.dms.step_definitions.DPBIEventsStepDefs;
import com.maersk.dms.step_definitions.InboundCorrespondenceNoteEventStepDefs;
import com.maersk.dms.step_definitions.OutboundCorrespondenceDefinitionStepDefs;
import com.maersk.dms.step_definitions.OutboundCorrespondenceStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import java.text.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class CRMBusinessEvents extends CRMUtilities implements ApiBase {
    private final ThreadLocal<APIClassUtil> apiClassUtil = ThreadLocal.withInitial(APIClassUtil::getApiClassUtilThreadLocal);
    private final ThreadLocal<Response> events = new ThreadLocal<>();
    private final ThreadLocal<CRM_ViewAddressInformationStepDef> viewAddressInfo = ThreadLocal.withInitial(CRM_ViewAddressInformationStepDef::new);
    private final ThreadLocal<CRM_ViewContactRecordHistoryStepDef> viewConsumer = ThreadLocal.withInitial(CRM_ViewContactRecordHistoryStepDef::new);
    private final ThreadLocal<APIPhoneEmailAddressEventsCheckController> phoneEmailAddress = ThreadLocal.withInitial(APIPhoneEmailAddressEventsCheckController::new);
    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    private final ThreadLocal<String> traceId = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> eventId = ThreadLocal.withInitial(String::new);
    private ZonedDateTime nowUTC = ZonedDateTime.now(ZoneOffset.UTC);
    private LocalDateTime localDateTime = LocalDateTime.ofInstant(nowUTC.toInstant(), ZoneOffset.UTC);
    private final ThreadLocal<String> expectedDateAndHour = ThreadLocal.withInitial(() -> localDateTime.toString().substring(0, 13));
    private final ThreadLocal<String> expectedDate = ThreadLocal.withInitial(() -> localDateTime.toString().substring(0, 11));


    @When("I search the Get Events endpoint for {string} business event name")
    public void iSearchGetBEventsEndpoint(String eventName) {
        switch (eventName) {
            case "NEW_ADDRESS":
                searchBEvents(eventName, "Name");
                break;
            case "OUTBOUND_CORRESPONDENCE_SETTING_UPDATE_EVENT":
                searchBEvents(eventName, "outboundCorrespondenceEcmsDocumentType");
                break;
        }
    }

    @Then("I should see the business  {string} that was created")
    public void iShouldSeeBEventWasCreated(String event) {
        switch (event) {
            case "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT":
                verifyBEvent(event, "Name");
                break;
            case "OUTBOUND_CORRESPONDENCE_SETTING_UPDATE_EVENT":
                verifyBEvent(event, "outboundCorrespondenceEcmsDocumentType");
                break;
        }
    }


    @And("I retrieve the {string} business event that is produced by trace id")
    public void iRetrieveTheBEventThatIsProducedByTraceId(String event) {
        switch (event) {
            case "NEW_ADDRESS":
                traceId.set(newAddressGetTraceId());
                System.out.println("TRACEID++++++++++++++" + traceId.get());
                break;
            case "ADDRESS_CHANGE":
                traceId.set(newAddressGetTraceId());
                break;
            case "NEW_PHONE":
                traceId.set(newAddressGetTraceId());
                System.out.println("TRACEID++++++++++++++" + traceId.get());
                break;
            case "PHONE_CHANGE":
                traceId.set(newAddressGetTraceId());
                break;
            case "NEW_CONSUMER_PROFILE":
                traceId.set(newConsumerGetTraceId());
                break;

        }

    }

    @And("I retrieve the {string} DPBI event that is produced by trace id")
    public void iRetrieveTheDPBIEventThatIsProducedByTraceId(String event) {
        switch (event) {
            case "ADDRESS_SAVE_EVENT":
                traceId.set(newAddressGetTraceId());
                System.out.println("TRACEID++++++++++++++" + traceId.get());
                break;
            case "ADDRESS_UPDATE_EVENT":
                traceId.set(newAddressGetTraceId());
                break;
            case "PHONE_SAVE_EVENT":
                traceId.set(newPhoneGetTraceId());
                System.out.println("TRACEID++++++++++++++" + traceId.get());
                break;
            case "PHONE_UPDATE_EVENT":
                traceId.set(newAddressGetTraceId());
                break;
            case "CONSUMER_SAVE_EVENT":
                traceId.set(newConsumerGetTraceId());
                break;
            case "CONSUMER_UPDATE_EVENT":
                if (apiClassUtil.get().getProjectName().equalsIgnoreCase("NJ-SBE")) {
                    traceId.set(njConsumerUpdateGetTraceId());
                    break;
                }
                traceId.set(newConsumerGetTraceId());
                break;

        }

    }

    public void searchBEvents(String key, String eventName) {
        apiClassUtil.set(APIClassUtil.getApiClassUtilThreadLocal(true));
        apiClassUtil.get().setbaseUri("https://mars-event-api-qa.apps.non-prod.pcf-maersk.com");
        apiClassUtil.get().setEndPoint("/app/crm/events");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventName);
        jsonObject.addProperty(key, World.save.get().get(key));
        synchronized (events) {
            events.set((Response) apiClassUtil.get().PostAPIWithParameter(jsonObject).responseBody);
        }
        org.testng.Assert.assertEquals(200, events.get().statusCode());

    }

    public void searchDPBIEventByCorrelation(String traceId) {
        apiClassUtil.get().setbaseUri(ConfigurationReader.getProperty("apiEventsURI"));
        apiClassUtil.get().setEndPoint("/app/crm/events?size=100&page=0&sort=eventId,desc");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("correlationId", traceId);
        apiClassUtil.get().PostAPIWithParameter(jsonObject);
        World.generalSave.get().put("response", apiClassUtil.get().jsonPathEvaluator);
        org.testng.Assert.assertEquals(200, apiClassUtil.get().statusCode);
    }

    public void searchBEventByEventName(String eventName) {
        apiClassUtil.get().setbaseUri(ConfigurationReader.getProperty("apiEventsURI"));
        apiClassUtil.get().setEndPoint("/app/crm/events?size=100&page=0&sort=eventId,desc");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventName);
        apiClassUtil.get().PostAPIWithParameter(jsonObject);
        World.generalSave.get().put("response", apiClassUtil.get().jsonPathEvaluator);
        org.testng.Assert.assertEquals(200, apiClassUtil.get().statusCode);
    }

    public void verifyBEvent(String event, String data) {
        List<String> list = events.get().body().jsonPath().getList("eventsList.content.payload");
        boolean found = false;
        List<String> delete = new ArrayList<>();
        for (String load : list) {
            if (load.contains(World.save.get().get(data))) {
                delete.add(load);
                found = true;
            }
        }
        org.testng.Assert.assertTrue(found);
    }

    public void verifyNewAddressEvent(JsonPath response) {
        String date = ApiTestDataUtil.getApiTestDataUtilThreadLocal().getEndDate("current");
        eventId.set(response.get("events[0].eventId").toString());
        String projectId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId();
        String projectName = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectName();
        org.testng.Assert.assertEquals(response.get("events[0].eventName"), "NEW_ADDRESS");
        String payload = response.get("events[0].payload").toString().replaceAll("\"", "");
        System.out.println("payload : " + payload);
        org.testng.Assert.assertTrue(payload.contains("projectId:" + projectId + ""));
        org.testng.Assert.assertTrue(payload.contains("projectName:" + projectName + ""));
        org.testng.Assert.assertTrue(payload.contains("action:Update"));
        org.testng.Assert.assertTrue(payload.contains("receivedDate:" + date + ""));


    }

    public void verifyAddressChnageEvent(JsonPath response) {
        String date = ApiTestDataUtil.getApiTestDataUtilThreadLocal().getEndDate("current");
        eventId.set(response.get("events[0].eventId").toString());
        String projectId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId();
        String projectName = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectName();
        org.testng.Assert.assertEquals(response.get("events[0].eventName"), "NEW_ADDRESS");
        String payload = response.get("events[0].payload").toString().replaceAll("\"", "");
        System.out.println("payload : " + payload);
        org.testng.Assert.assertTrue(payload.contains("projectId:" + projectId + ""));
        org.testng.Assert.assertTrue(payload.contains("projectName:" + projectName + ""));
        org.testng.Assert.assertTrue(payload.contains("action:Update"));
        org.testng.Assert.assertTrue(payload.contains("receivedDate:" + date + ""));


    }

    public String newConsumerGetTraceId() {
        List<String> result = EventsUtilities.getRawLogs("apigee.pcf-maersk.com/mars-cp-web/mars-consumer-api/app/crm/case/consumers");


        for (String s : result) {
            System.out.println(s);
        }

        String correlation = EventsUtilities.getGenericMatch(result.get(0), "[\\d]{12}");
//        String correlation = EventsUtilities.getGenericMatch(result.get(0), "(consumer/)[\\d]*[^\"]");
        return correlation;
    }

    public String njConsumerUpdateGetTraceId() {

        List<String> result = EventsUtilities.getLogs("traceid",
                "/external");

        String correlation = result.get(0);

        return correlation;
    }

    public String newAddressGetTraceId() {
        List<String> result = EventsUtilities.getLogs("traceId", "/contacts");
        System.out.println(result);
        return result.get(4);
    }

    public String newPhoneGetTraceId() {
        List<String> result = EventsUtilities.getLogs("traceId", "/contacts");
        System.out.println(result);
        return result.get(4);
    }

    public String updateContactGetTraceId() {
        List<String> result = EventsUtilities.getLogs("traceId", "/contacts");
        System.out.println(result + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return result.get(4);
    }

    public void verifyNewContactEvent(JsonPath eventbyTraceId, String businessEvent) {
        eventId.set(eventbyTraceId.get("events[1].eventId").toString());
        String endDate = getGreaterDateInYearFormat(3);
        String startDate = getPriorDateInYearFormat(4);
        System.out.println(endDate);
        System.out.println(startDate);
        System.out.println("eventId is : " + eventId.get());

        assertEquals(eventbyTraceId.get("events[1].eventName"), businessEvent);
        assertEquals(eventbyTraceId.get("events[1].module"), "CONTACTS");
        assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"businessEvent\":\"" + businessEvent + ""));
        assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"channel\":\"" + "User Data Entry" + "\""));
        assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"processDate\":\"" + expectedDateAndHour.get() + ""));
        assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"createdBy\":\"" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId + "\""));
        switch (businessEvent) {
            case "NEW_ADDRESS":
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"addressLine1\":\"" + viewAddressInfo.get().conAddLineOne.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"addressLine2\":\"" + viewAddressInfo.get().addLineTwo.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"city\":\"" + viewAddressInfo.get().addCity.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"county\":\"" + viewAddressInfo.get().addCountyLine.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"state\":\"" + viewAddressInfo.get().selState.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"zipCode\":\"" + viewAddressInfo.get().zipCode.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"type\":\"" + "Physical"));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"source\":\"Consumer Reported\""));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"endDate\":\"" + endDate));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"startDate\":\"" + startDate));
                break;
            case "NEW_PHONE":
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"startDate\":\"" + expectedDate.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"phone\":\"" + phoneEmailAddress.get().phone.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"type\":\"" + phoneEmailAddress.get().addPhoneType.get()));
                break;
        }
    }

    public void verifyContactUpdateEvent(JsonPath eventbyTraceId, String businessEvent) {
        int i = 1;
        if (!eventbyTraceId.get("events[" + i + "].eventName").equals(businessEvent)) i = 0;
        eventId.set(eventbyTraceId.get("events[1].eventId").toString());
        String endDate = getGreaterDateInYearFormat(3);
        String startDate = getPriorDateInYearFormat(4);
        System.out.println(endDate);
        System.out.println(startDate);
        System.out.println("eventId is : " + eventId.get());

        assertEquals(eventbyTraceId.get("events[1].eventName"), businessEvent);
        assertEquals(eventbyTraceId.get("events[1].module"), "CONTACTS");
        assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"businessEvent\":\"" + businessEvent + ""));
        assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"channel\":\"" + "User Data Entry" + "\""));
        assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"processDate\":\"" + expectedDateAndHour.get() + ""));
        assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"createdBy\":\"" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId + "\""));
        switch (businessEvent) {
            case "ADDRESS_CHANGE":
//                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"addressLine1\":\"" + viewAddressInfo.get().conAddLineOne));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"addressLine2\":\"" + viewAddressInfo.get().addLineTwo.get()));
//                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"city\":\"" + viewAddressInfo.get().addCity));
//                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"county\":\"" + viewAddressInfo.get().addCountyLine));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"state\":\"" + viewAddressInfo.get().selState.get()));
//                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"zipCode\":\"" + viewAddressInfo.get().zipCode));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"type\":\"" + "Physical"));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"source\":\"Consumer Reported\""));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"startDate\":\"" + expectedDateAndHour.get()) ||
                        eventbyTraceId.get("events[1].payload").toString().contains("\"startDate\":\"" + startDate));
                break;
            case "PHONE_CHANGE":
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"startDate\":\"" + expectedDate.get()));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"phone\":\"" + CRM_AddNewPhoneNumberStepDef.phone));
                assertTrue(eventbyTraceId.get("events[1].payload").toString().contains("\"type\":\"" + phoneEmailAddress.get().addPhoneType.get()));
                break;
        }
    }

    @Then("I should see the payload for the business {string} is as expected")
    public void iShouldSeeThePayloadForTheBIsAsExpected(String event) throws ParseException {
        switch (event) {
            case "NEW_ADDRESS":
                verifyNewContactEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "NEW_PHONE":
                verifyNewContactEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "PHONE_CHANGE":
                verifyContactUpdateEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "ADDRESS_CHANGE":
                verifyContactUpdateEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "NEW_CONSUMER_PROFILE":
                verifyNewConsumerEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            default:
                org.junit.Assert.fail("no matching events in case");
        }

    }

    @Then("I should see the payload for the DPBI {string} is as expected")
    public void iShouldSeeThePayloadForTheDPBIIsAsExpected(String event) throws ParseException {
        switch (event) {
            case "ADDRESS_SAVE_EVENT":
                verifyNewContactEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "PHONE_SAVE_EVENT":
                verifyNewContactEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "PHONE_UPDATE_EVENT":
                verifyContactUpdateEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "ADDRESS_UPDATE_EVENT":
                verifyContactUpdateEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "CONSUMER_SAVE_EVENT":
                verifyNewConsumerEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            case "CONSUMER_UPDATE_EVENT":
                verifyNJConsumerUpdateEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            default:
                org.junit.Assert.fail("no matching events in case");
        }

    }

    public void verifyNewConsumerEvent(JsonPath eventbyTraceId, String businessEvent) throws ParseException {
        List<Map<String, Object>> eventsList = eventbyTraceId.getList("events");
        boolean validated = false;
        for (int i = 0; i < eventsList.size(); i++) {
            if (eventbyTraceId.get("events[" + i + "].eventName").equals(businessEvent)) {
                String expectedPayload = eventbyTraceId.get("events[" + i + "].payload").toString();

                eventId.set(eventbyTraceId.get("events[" + i + "].eventId").toString());
                String endDate = getGreaterDateInYearFormat(3);
                String startDate = getPriorDateInYearFormat(4);
                System.out.println(endDate);
                System.out.println(startDate);
                System.out.println("eventId is : " + eventId.get());

                assertEquals(eventbyTraceId.get("events[" + i + "].eventName"), businessEvent);
                assertEquals(eventbyTraceId.get("events[" + i + "].module"), "Consumer");
                assertTrue(expectedPayload.contains("\"businessEvent\":\"" + businessEvent + ""));
                assertTrue(expectedPayload.contains("\"channel\":\"" + "User Data Entry" + "\""));
                assertTrue(expectedPayload.contains("\"processDate\":\"" + expectedDateAndHour.get() + ""));
                assertTrue(expectedPayload.contains("\"consumerFirstName\":\"" + viewConsumer.get().firstName.get()));
                assertTrue(expectedPayload.contains("\"consumerLastName\":\"" + viewConsumer.get().lastName.get()));
                assertTrue(expectedPayload.contains("\"dateOfBirth\":\"" + apiClassUtil.get().convertUIDateToAPI(viewConsumer.get().birthday.get())));
                assertTrue(expectedPayload.contains("\"caseIdExternal\":null"));
                assertTrue(expectedPayload.contains("\"startDate\":\"" + expectedDateAndHour.get()) ||
                        expectedPayload.contains("\"startDate\":\"" + startDate));
                assertTrue(expectedPayload.contains("\"pregnancy\":null"));
                assertTrue(expectedPayload.contains("\"pregnancyDueDate\":null"));
                assertTrue(expectedPayload.contains("\"dateOfDeath\":null"));
                assertTrue(expectedPayload.contains("\"dodNotificationDate\":null"));
                assertTrue(expectedPayload.contains("\"dodNotificationSource\":null"));
                assertTrue(expectedPayload.contains("\"communicationOptIn\":[\"Phone\",\"Mail\"]")
                        || expectedPayload.contains("\"communicationOptIn\":[\"Mail\",\"Phone\"]"));
                assertTrue(expectedPayload.contains("\"consumerStatus\":\"Active\""));
                validated = true;
            }

        }
        assertTrue(validated);


    }


    public void verifyNJConsumerUpdateEvent(JsonPath eventByTraceId, String dpbiEvent) throws ParseException {
        List<Map<String, Object>> eventsList = eventByTraceId.getList("events");
        boolean validated = false;
        for (int i = 0; i < eventsList.size(); i++) {
            if (eventByTraceId.get("events[" + i + "].eventName").equals(dpbiEvent)) {
                String expectedPayload = eventByTraceId.get("events[" + i + "].payload").toString();

                eventId.set(eventByTraceId.get("events[" + i + "].eventId").toString());
                String updatedOn = getCurrentDateInYearFormat();
                System.out.println("eventId is : " + eventId.get());

                assertEquals(eventByTraceId.get("events[" + i + "].eventName"), dpbiEvent);
                assertEquals(eventByTraceId.get("events[" + i + "].module"), "Consumer");
                assertTrue(expectedPayload.contains("\"action\":\"Update\""));
                assertTrue(expectedPayload.contains("\"recordType\":\"ConsumerEventRequest\""));
                assertTrue(expectedPayload.contains("\"consumerFirstName\":\"Queen\""));
                assertTrue(expectedPayload.contains("\"consumerLastName\":\"Faith\""));
                assertTrue(expectedPayload.contains("\"consumerDateOfBirth\":\"1980-10-10\""));
                assertTrue(expectedPayload.contains("\"correlationId\":\"" + traceId.get()));
                assertTrue(expectedPayload.contains("\"updatedOn\":\"" + updatedOn));
                assertTrue(expectedPayload.contains("\"consumerStatus\":\"Active\""));
                validated = true;
            }

        }
        assertTrue(validated);


    }


    @Then("I should see updatedOn and updatedBy matches the Event createdOn and createdBy values {string}")
    public void iShouldSeeUpdatedOnCreatedOnMatchesEventValues(String event) throws ParseException {
        switch (event) {
            case "CONSUMER_UPDATE_EVENT":
                verifyCreatedAndUpdatedValuesForEvent(apiAutoUtilities.get().getEventbyTraceId(traceId.get()), event);
                break;
            default:
                org.junit.Assert.fail("no matching events in case");
        }

    }


    public void verifyCreatedAndUpdatedValuesForEvent(JsonPath eventByTraceId, String dpbiEvent) throws ParseException {
        List<Map<String, Object>> eventsList = eventByTraceId.getList("events");
        boolean validated = false;
        for (int i = 0; i < eventsList.size(); i++) {
            if (eventByTraceId.get("events[" + i + "].eventName").equals(dpbiEvent)) {
                String expectedPayload = eventByTraceId.get("events[" + i + "].payload").toString();

                eventId.set(eventByTraceId.get("events[" + i + "].eventId").toString());
                String updatedOn = getCurrentDateInYearFormat();
                System.out.println("eventId is : " + eventId.get());
                String userId = apiClassUtil.get().getApiUserInfo();

                assertEquals(eventByTraceId.get("events[" + i + "].eventName"), dpbiEvent);
                assertEquals(eventByTraceId.get("events[" + i + "].module"), "Consumer");
                assertEquals(eventByTraceId.get("events[" + i + "].createdBy"), userId);
                assertEquals(eventByTraceId.get("events[" + i + "].updatedBy"), userId);
                assertEquals(eventByTraceId.get("events[" + i + "].updatedOn").toString().substring(0, 10), updatedOn);
                assertTrue(expectedPayload.contains("\"updatedBy\":\"" + userId));
                assertTrue(expectedPayload.contains("\"eventCreatedOn\":\"" + updatedOn));
                assertTrue(expectedPayload.contains("\"correlationId\":\"" + traceId.get()));
                assertTrue(expectedPayload.contains("\"updatedOn\":\"" + updatedOn));
                validated = true;
            }
        }
        assertTrue(validated);
    }
}
