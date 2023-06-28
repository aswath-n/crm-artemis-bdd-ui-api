package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;

public class APIConsumerOptOutController extends CRMUtilities implements ApiBase {
    private String baseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String createConsumerEndPoint = "app/crm/consumer";

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String getConsumerSaveEvents = "app/crm/events?size=10000000&page=0&sort=eventId,desc";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    private String newEventURI = ConfigurationReader.getProperty("apiMarsEvent");
    private String newEventsEndPoint = "?size=100page=0&sort=eventId,desc";
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> apiconsumerFirstName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiconsumerLastName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiphoneNumber = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiaddressZip = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    private ThreadLocal<Integer> eventId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<String> NULL_CHECK = ThreadLocal.withInitial(() -> "null");

    @Given("I initiated Create Consumer API for Consumer Opt in")
    public void initiatedCaseConsumerContactSearchAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerEndPoint);
    }

    @When("I can provide consumer information with {string} {string} {string} and {string} for Consumer {string} Opt in")
    public void consumerInfoForConsumerOptIn(String consumerFirstName, String consumerLastName, String phoneNumber, String addressZip, String optOutType) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumerWithConsentForOptOut.json");
        long currentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", currentDateTime);
        if (consumerFirstName.isEmpty() || consumerFirstName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        } else {
            apiconsumerFirstName.set(consumerFirstName);
        }
        System.out.println("Consumer First Name :" + apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        if (consumerLastName.isEmpty() || consumerLastName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        } else {
            apiconsumerLastName.set(consumerLastName);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
        System.out.println("Consumer Last Name :" + apiconsumerLastName.get());
        if (phoneNumber.isEmpty() || phoneNumber.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
            apiphoneNumber.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        } else {
            apiphoneNumber.set(phoneNumber);
        }
        System.out.println("Phone Number :" + apiphoneNumber.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", (apiphoneNumber.get()).toString());
        if (addressZip.isEmpty() || addressZip.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5);
            apiaddressZip.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        } else {
            apiaddressZip.set(addressZip);
        }
        System.out.println("Zip Code :" + apiaddressZip.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", (apiaddressZip.get()).toString());

        if (optOutType.equals("phone")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(0).getAsJsonObject().addProperty("optIn", true);
        } else if (optOutType.equals("text")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(2).getAsJsonObject().addProperty("optIn", true);
        } else if (optOutType.equals("email")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(1).getAsJsonObject().addProperty("optIn", true);
        } else if (optOutType.equals("fax")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(4).getAsJsonObject().addProperty("optIn", true);
        } else if (optOutType.equals("mail")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(3).getAsJsonObject().addProperty("optIn", true);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16);
        correlationId.set((API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @And("I run Create Consumer API for Consumer Opt in")
    public void runCreateConsumerAPIForConsumerOptIn() {
        System.out.println("Request Consumer: " + requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I validate request is passed for a {string} for {string} to be generated for Consumer {string} Opt in")
    public void iValidateRequestIsPassedForAToBeGenerated(String eventName, String subscriberName, String optOutType) {
        requestParams.set(generateEventRequest(eventName));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(newEventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(newEventsEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            System.out.println("Payload: " + payLoadData);

            eventId.set((int) eventsData.get("eventId"));

            JsonElement dataObject = payLoadData.get("dataObject");

            if (dataObject.getAsJsonObject().get("consumerFirstName").toString().contains(apiconsumerFirstName.get())) {
                assertEquals(eventsData.get("status"), "SUCCESS");
                assertTrue(dataObject.getAsJsonObject().get("consumerFirstName").toString().contains(apiconsumerFirstName.get()));
                assertTrue(dataObject.getAsJsonObject().get("consumerLastName").toString().contains(apiconsumerLastName.get()));

                JsonArray consumerConsent = dataObject.getAsJsonObject().getAsJsonArray("consumerConsent");
                for (JsonElement cc : consumerConsent) {
                    if (cc.getAsJsonObject().get("consentType").getAsString().equalsIgnoreCase(optOutType)) {
                        assertTrue(cc.getAsJsonObject().get("optIn").getAsBoolean());
                    } else {
                        assertFalse(cc.getAsJsonObject().get("optIn").getAsBoolean());
                    }
                }

                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);

        boolean dpbiEventGenerated = dpbiEventGenerationCheck(eventName, subscriberName);
        assertTrue(dpbiEventGenerated);
    }

    @Then("I verify created on and created by values for {string} for Consumer Opt in")
    public void verifyCreatedOnAndCreatedBy(String eventName) {
        requestParams.set(generateEventRequest(eventName));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(newEventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(newEventsEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            assertTrue(verifyDatetimeFormat(payLoadData.get("dataObject").getAsJsonObject().get("createdOn").getAsString()));
            assertNotNull(payLoadData.get("dataObject").getAsJsonObject().get("createdBy"));
            assertTrue(verifyDatetimeFormat(payLoadData.get("dataObject").getAsJsonObject().get("effectiveStartDate").getAsString()));
            if (payLoadData.get("dataObject").getAsJsonObject().get("updatedBy") != null)
                assertNotNull(payLoadData.get("dataObject").getAsJsonObject().get("updatedBy"));
            if (payLoadData.get("dataObject").getAsJsonObject().get("updatedOn") != null)
                assertTrue(verifyDatetimeFormat(String.valueOf(payLoadData.get("dataObject").getAsJsonObject().get("updatedOn"))));
            if (payLoadData.get("dataObject").getAsJsonObject().get("effectiveEndDate") != null)
                assertTrue(verifyDatetimeFormat(String.valueOf(payLoadData.get("dataObject").getAsJsonObject().get("effectiveEndDate"))));

            if (eventsData.get("eventName").equals(eventName)) {
                assertEquals(eventsData.get("status"), "SUCCESS");

                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);
    }

    private JsonObject generateEventRequest(String eventName) {
        JsonObject request = new JsonObject();

        request.addProperty("eventId", "");
        request.addProperty("eventName", eventName);
        request.addProperty("module", "");
        request.addProperty("payload", "");
        request.addProperty("status", "");
        request.addProperty("errorStack", "");
        //request.addProperty("correlationId", "1176586223339058");
        request.addProperty("correlationId", "");
        request.addProperty("uiid", "");
        request.addProperty("createdOn", "");
        request.addProperty("updatedOn", "");
        request.addProperty("createdBy", "");
        request.addProperty("updatedBy", "");
        request.addProperty("message", "");

        return request;
    }

    private boolean verifyDatetimeFormat(String datetimeField) {
        if (datetimeField.equals(NULL_CHECK.get()))
            return true;
        boolean datetimeFormatCheck = false;
        DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
        System.out.println(datetimeField);

        try {
            LocalDateTime parsedDate = dtf.parseLocalDateTime(datetimeField);
            if (parsedDate != null)
                datetimeFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return datetimeFormatCheck;
    }

    private boolean dpbiEventGenerationCheck(String eventName, String subscriberName) {
        boolean recordCreated = true;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName);

        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray jsonSubscriber = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        String subscriberId = new JSONObject(jsonSubscriber.get(0).toString()).get("subscriberId").toString();
        JSONArray subscriberList = new JSONArray(new JSONObject(jsonSubscriber.get(0).toString()).get("subscriberList").toString());

        for (int i = 0; i < subscriberList.length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get(i).toString());
            if (temp.get("eventName").toString().trim().equals(eventName.trim())) {
                assertTrue(temp.get("subscriberId").toString().contains(subscriberId));
                assertEquals(temp.get("eventName").toString().trim(), eventName);
                assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                recordCreated = true;
                break;
            }
        }
        assertTrue(recordCreated);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        assertEquals(temp.getString("eventName"), eventName);
        System.out.println("Even name on DPBI:  " + eventName);

        assertEquals(temp.getInt("eventId"), eventId.get());
        System.out.println("Event id on DPBI:  " + eventId.get());

        assertEquals(temp.getInt("subscriberId") + "", subscriberId);
        System.out.println("Subscriber id on DPBI:   " + subscriberId);
        System.out.println("Event is subscribed to DPBI list");

        return recordCreated;
    }
}
