package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.step_definitions.CRMEnrollmentUpdateStepDef;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.exception.CucumberException;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.testng.Assert.*;

public class APIEnrollmentEventsCheckController extends CRMUtilities implements ApiBase {
    private final ThreadLocal<String> enrollmentBaseURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEligibilityURI"));
    private final ThreadLocal<String> apiEnrollment = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEnrollment"));
    private final ThreadLocal<String> getEnrollmentListByUser = ThreadLocal.withInitial(()->"enrollments");
    private final ThreadLocal<String> eventsURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEventsURI"));
    private final ThreadLocal<String> eventURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEventsURI"));
    private final ThreadLocal<String> apiInitContactRecord = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiInitContactRecord"));
    private final ThreadLocal<String> getEvents = ThreadLocal.withInitial(()->"app/crm/events?size=10000000&page=0&sort=eventId,desc");
    private final ThreadLocal<String> subscribersEndPoint = ThreadLocal.withInitial(()->"/app/crm/es/event/subscribers");
    private final ThreadLocal<String> outOfStateCheckEndPoint = ThreadLocal.withInitial(()->"/mars/eb/enrollments/out-of-state-enrollments/{consumerId}");
    private final ThreadLocal<String> subscribersRecordEndPoint = ThreadLocal.withInitial(()->"/app/crm/es/subscriber/records/");
    private final ThreadLocal<String> otherEnrollmentSegments = ThreadLocal.withInitial(()->"/mars/eb/otherEnrollmentSegments/?consumerIds=");
    private final ThreadLocal<String> contactRecordEndpoint = ThreadLocal.withInitial(()->"app/crm/contactrecord/customers");
    final ThreadLocal<DateTimeFormatter> df = ThreadLocal.withInitial(()->DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    final ThreadLocal<DateTimeFormatter> dfUIver = ThreadLocal.withInitial(()->DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsUpdated = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsForEvent = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<APIConsumerPopulationDmnController> acp = ThreadLocal.withInitial(APIConsumerPopulationDmnController::new);
    final ThreadLocal<ApiTestDataUtil> apitdu = ThreadLocal.withInitial(()->ApiTestDataUtil.getApiTestDataUtilThreadLocal());
    final ThreadLocal<APIEligibilityUpdateController> apiEligibilityUpdateController = ThreadLocal.withInitial(APIEligibilityUpdateController::new);
 /*   private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private APIClassUtil apiEvent = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/
    private final ThreadLocal<String> planCodeUpdated = ThreadLocal.withInitial(()->"plancode1" + RandomStringUtils.random(3, false, true));
    private final ThreadLocal<Integer> eventId = ThreadLocal.withInitial(()->0);

    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);

    private final ThreadLocal<Boolean> consumerExist = ThreadLocal.withInitial(()->false);;

    @Given("I initiated Enrollment Create API for checking events payload")
    public void initiatedEnrollmentCreateAPIForCheckingEvents() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentListByUser.get());
    }

    @When("I run the {string} API for {string} for checking events payload")
    public void runEnrollmentCreateAPIForCheckingEvents(String createOrUpdate, String enrollmentType) {
        requestParams.set(generateCreateEnrollmentRequestForEventPayloadCheck(createOrUpdate, enrollmentType));
        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        correlationId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 201);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("CREATED"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "CREATED");
    }

    @Then("I will verify an {string} for {string} is created with fields in payload")
    public void verifyTheEnrollmentDataSaveEventForCheckingEvents(String enrollmentEvent, String subscriberName) {
        JsonObject event = new JsonObject();
        event.addProperty("correlationId", APIEnrollmentController.traceIdTOCheckEvents.get());
        event.addProperty("eventName", enrollmentEvent);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println("=========================================================================================================");

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString().toLowerCase(), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));
            System.out.println("=========================================================================================================");

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            System.out.println("Payload: " + payLoadData);

            eventId.set((int) eventsData.get("eventId"));

            assertNotNull(payLoadData.get("action"));
            assertNotNull(payLoadData.get("recordType"));
            assertNotNull(payLoadData.get("eventCreatedOn"));
            assertNotNull(payLoadData.get("dataObject"));
            assertEquals(payLoadData.get("projectId").toString(), ConfigurationReader.getProperty("projectName").split(" ")[0]);
            assertEquals(payLoadData.get("projectName").getAsString(), ConfigurationReader.getProperty("projectName").split(" ")[1]);
            if (enrollmentEvent.equals("ENROLLMENT_SAVE_EVENT") || enrollmentEvent.equals("ENROLLMENT_UPDATE_EVENT")) {
                assertEquals(payLoadData.get("recordType").getAsString(), "Enrollment");
            }
            else if ( enrollmentEvent.equals("DISENROLL_REQUESTED")) {
                    assertEquals(payLoadData.get("recordType").getAsString(), "DisenrollBusinessEvent");
            } else {
                // Below fix needs double check
//                assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("Eligibility"));
                assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("EligibilityBusinessEvent") || payLoadData.get("recordType").getAsString().equalsIgnoreCase("ArrayList"));
            }
            JsonObject dataObjectData = null;
            if (enrollmentEvent.equals("ENROLLMENT_UPDATE_EVENT")
                    || enrollmentEvent.equals("ELIGIBILITY_UPDATE_EVENT")
                    || enrollmentEvent.equals("ELIGIBILITY_SAVE_EVENT")
                    || enrollmentEvent.equals("LOSS_OF_ELIGIBILITY")
                    || enrollmentEvent.equals("ENROLLMENT_SAVE_EVENT")
            ) {
                if (payLoadData.get("dataObject").isJsonObject()) {
                    dataObjectData = payLoadData.getAsJsonObject("dataObject");
                } else if (payLoadData.get("dataObject").isJsonArray()) {
                    dataObjectData = payLoadData.getAsJsonArray("dataObject").get(0).getAsJsonObject();
                }
            }

            if (eventsData.get("eventName").equals(enrollmentEvent)) {
                assertEquals(eventsData.get("status").toString().toLowerCase(), "success");
                if (enrollmentEvent.equals("ENROLLMENT_SAVE_EVENT")) {
                    assertEquals(payLoadData.get("action").getAsString(), "Create");
                    assertEquals(dataObjectData.get("consumerId").getAsString(), APIEnrollmentController.consumerId.get());
                } else if (enrollmentEvent.equals("ENROLLMENT_UPDATE_EVENT")) {
                    assertEquals(payLoadData.get("action").getAsString(), "Update");
                    //assertEquals(dataObjectData.get("consumerId").getAsString(), APIEnrollmentController.consumerId);
                } else if (enrollmentEvent.equals("ELIGIBILITY_SAVE_EVENT")) {
                    assertEquals(payLoadData.get("action").getAsString(), "Create");
                    assertEquals(dataObjectData.get("consumerId").getAsString(), APIEligibilityController.consumerId);
                } else if (enrollmentEvent.equals("ELIGIBILITY_UPDATE_EVENT")) {
                    assertEquals(payLoadData.get("action").getAsString(), "Update");
                    //assertEquals(dataObjectData.get("consumerId").getAsString(), APIEligibilityController.consumerId);
                } else if (enrollmentEvent.equals("LOSS_OF_ELIGIBILITY")) {
//                    assertEquals(payLoadData.get("action").getAsString(), "Update");
                    // Below fix needs double check. With loss of eligibility does action become Create or update??
                    assertEquals(payLoadData.get("action").getAsString(), "Update");
                    // assertEquals(dataObjectData.get("consumerId").getAsString(), APIEligibilityController.consumerId);
                } else if (enrollmentEvent.equals("DISENROLL_REQUESTED")) {
                    assertEquals(payLoadData.get("action").getAsString(), "Update");
                    // assertEquals(dataObjectData.get("consumerId").getAsString(),
                }
                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);

        //boolean dpbiEventGenerated = dpbiEventGenerationCheck(enrollmentEvent, subscriberName);
        //assertTrue(dpbiEventGenerated);
    }


    @Given("I initiated Enrollment Update API for checking events payload")
    public void initiatedEnrollmentUpdateAPIForCheckingEvents() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentListByUser.get());
    }

    @When("I run the Enrollment Update API for {string} for checking events payload")
    public void runEnrollmentUpdateAPIForCheckingEvents(String createOrUpdate, String enrollmentType) {
        requestParams.set(generateCreateEnrollmentRequestForEventPayloadCheck(createOrUpdate, enrollmentType));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        requestParamsUpdated.set(generateUpdateEnrollmentRequestForEventPayloadCheck(requestParams.get()));
        System.out.println(requestParamsUpdated.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsUpdated.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I will verify an {string} for DPBI is created with datetime fields in UTC format in payload")
    public void verifyTheEnrollmentDataSaveEventUpdatedForCheckingDates(String enrollmentEvent) {
        JsonObject event = new JsonObject();
        event.addProperty("eventName", enrollmentEvent);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = null;
            if (enrollmentEvent.equals("ENROLLMENT_SAVE_EVENT")) {
                dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
            } else if (enrollmentEvent.equals("ENROLLMENT_UPDATE_EVENT") || enrollmentEvent.equals("ELIGIBILITY_UPDATE_EVENT") || enrollmentEvent.equals("ELIGIBILITY_SAVE_EVENT")) {
                dataObjectData = payLoadData.get("dataObject").getAsJsonArray().get(0).getAsJsonObject();
            }

            System.out.println(payLoadData.get("eventCreatedOn"));
            System.out.println(dataObjectData.get("createdOn"));
            System.out.println(dataObjectData.get("updatedOn"));
            System.out.println(dataObjectData.get("enrollmentProvider").getAsJsonObject().get("effectiveStartDate"));
            System.out.println(dataObjectData.get("enrollmentProvider").getAsJsonObject().get("effectiveEndDate"));

            assertTrue(verifyDatetimeFormat(payLoadData.get("eventCreatedOn")));
            assertTrue(verifyDatetimeFormat(dataObjectData.getAsJsonObject().get("createdOn")));
            assertTrue(verifyDatetimeFormat(dataObjectData.getAsJsonObject().get("updatedOn")));
            /*assertTrue(verifyDatetimeFormat(payLoadData.get("dataObject").getAsJsonObject().get("enrollmentProvider").getAsJsonObject().get("effectiveStartDate")));
            assertTrue(verifyDatetimeFormat(payLoadData.get("dataObject").getAsJsonObject().get("enrollmentProvider").getAsJsonObject().get("effectiveEndDate")));*/

            if (eventsData.get("eventName").equals(enrollmentEvent)) {
                assertEquals(eventsData.get("status"), "success");
                if (enrollmentEvent.equals("ENROLLMENT_SAVE_EVENT") || enrollmentEvent.equals("ENROLLMENT_UPDATE_EVENT")) {
                    assertTrue(dataObjectData.get("planCode").getAsInt() > 0);
                    assertEquals(dataObjectData.getAsJsonObject("enrollmentProvider").get("enrollmentId"), dataObjectData.get("enrollmentId"));
                }

                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);
    }

    private boolean verifyDatetimeFormat(JsonElement datetimeField) {
        boolean datetimeFormatCheck = false;
        String dateTimeFieldFormatted = datetimeField.toString().substring(1, datetimeField.toString().length() - 1);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(dateTimeFieldFormatted, dtf);
            if (parsedDate != null)
                datetimeFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return datetimeFormatCheck;
    }

    private JsonObject generateCreateEnrollmentRequestForEventPayloadCheck(String createOrUpdate, String enrollmentType) {
        JsonObject request = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String currentDate = dateFormat.format(todayDate) + "T06:02:39.607+00:00";
        Number correlationId = Long.parseLong(apitdu.get().getRandomNumber(10).randomNumber);
        Number consumerId = Integer.parseInt(apitdu.get().getRandomNumber(5).randomNumber);
        Number uiid = Integer.parseInt(apitdu.get().getRandomNumber(6).randomNumber);

        request.addProperty("aidCategoryCode", "string");
        request.addProperty("anniversaryDate", currentDate);
        request.addProperty("autoAssignIndicator", "A");
        request.addProperty("consumerId", consumerId);
        request.addProperty("correlationId", correlationId);
        request.addProperty("createdBy", "asad");
        request.addProperty("createdOn", currentDate);
        request.addProperty("endDate", currentDate);

        JsonObject enrollmentProviderInfo = new JsonObject();

        enrollmentProviderInfo.addProperty("consumerId", consumerId);
        enrollmentProviderInfo.addProperty("correlationId", correlationId);
        enrollmentProviderInfo.addProperty("createdBy", "asad");
        enrollmentProviderInfo.addProperty("createdOn", currentDate);
        enrollmentProviderInfo.addProperty("effectiveEndDate", currentDate);
        enrollmentProviderInfo.addProperty("effectiveStartDate", currentDate);
        enrollmentProviderInfo.addProperty("providerId", apitdu.get().getRandomNumber(4).randomNumber);
        enrollmentProviderInfo.addProperty("providerAddressCity", "dallas");
        enrollmentProviderInfo.addProperty("providerAddressCounty", "dallas");
        enrollmentProviderInfo.addProperty("providerAddressLine1", "dallas");
        enrollmentProviderInfo.addProperty("providerAddressLine2", "dallas");
        enrollmentProviderInfo.addProperty("providerAddressState", "texas");
        enrollmentProviderInfo.addProperty("providerEndDate", currentDate);
        enrollmentProviderInfo.addProperty("providerFirstName", "firstName1");
        enrollmentProviderInfo.addProperty("providerLastName", "lastName1");
        enrollmentProviderInfo.addProperty("providerMiddleName", "middleName1");
        enrollmentProviderInfo.addProperty("providerNpi", "providerNpi1");
        enrollmentProviderInfo.addProperty("providerPhoneNumber", "1234567890");
        enrollmentProviderInfo.addProperty("providerStartDate", currentDate);
        enrollmentProviderInfo.addProperty("providerType", "providerType1");
        enrollmentProviderInfo.addProperty("providerZipCodeExtn", "12345");
        enrollmentProviderInfo.addProperty("providerZipCodeMain", "12345");
        enrollmentProviderInfo.addProperty("uiid", uiid);
        enrollmentProviderInfo.addProperty("updatedBy", "string");
        enrollmentProviderInfo.addProperty("updatedOn", currentDate);

        request.add("enrollmentProvider", enrollmentProviderInfo);

        request.addProperty("enrollmentType", enrollmentType);
        request.addProperty("lockInEndDate", currentDate);
        request.addProperty("lockInExemptionReason", "lockInExemptionReason1");
        request.addProperty("lockInStartDate", currentDate);
        request.addProperty("lockInStatusCode", "lock1");
        request.addProperty("openEnrollmentEndDate", currentDate);
        request.addProperty("openEnrollmentStartDate", currentDate);
        request.addProperty("openEnrollmentStatus", "openEnrollentStatus1");
        if (createOrUpdate.equals("ENROLLMENT_CREATE")) {
            request.addProperty("planCode", "planCode1");
        } else {
            request.addProperty("planCode", planCodeUpdated.get());
        }

        request.addProperty("planEndDateReason", "1");
        request.addProperty("planId", Integer.parseInt(apitdu.get().getRandomNumber(2).randomNumber));
        request.addProperty("programTypeCode", "MEDICAID");
        request.addProperty("reinstatementDate", currentDate);
        request.addProperty("reinstatementIndicator", "A");
        request.addProperty("retroactiveDate", currentDate);
        request.addProperty("retroactiveIndicator", "A");
        request.addProperty("riskCode", "riskCode1");
        request.addProperty("selectionTxnId", 0);
        request.addProperty("serviceRegionCode", "serviceRegionCode1");
        request.addProperty("specialNeedFlag", "string");
        request.addProperty("startDate", currentDate);
        request.addProperty("subProgramTypeCode", "MEDICAID_GF");
        request.addProperty("uiid", uiid);
        request.addProperty("updatedBy", "asad");
        request.addProperty("updatedOn", currentDate);
        request.addProperty("waiverCode", "waiverCode1");

        return request;
    }

    private JsonObject generateUpdateEnrollmentRequestForEventPayloadCheck(JsonObject requestParams) {
        JsonObject enrollmentProviderInfo = requestParams.getAsJsonObject("enrollmentProviderInfo");
        String providerFirstName = enrollmentProviderInfo.get("providerFirstName").getAsString();
        System.out.println("providerFirstName:" + providerFirstName);
        enrollmentProviderInfo.addProperty("providerFirstName", "firstName2");
        String providerFirstNameUpdated = enrollmentProviderInfo.get("providerFirstName").getAsString();
        System.out.println("providerFirstNameUpdated:" + providerFirstNameUpdated);

        return requestParams;
    }

    private boolean dpbiEventGenerationCheck(String eventName, String subscriberName) {
        boolean recordCreated = true;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint.get());

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
                Assert.assertEquals(temp.get("subscriberId").toString(), subscriberId, "Subscriber Id is wrong");
                Assert.assertEquals(temp.get("eventName").toString().trim(), eventName, "Event Name is wrong");
                Assert.assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                recordCreated = true;
                break;
            }
        }
        Assert.assertTrue(recordCreated, eventName + " is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsURI.get());
        String subscribersRecordEndPointToSend = subscribersRecordEndPoint.get() + subscriberId + "/" + eventId.get();
        // if we modify value of subscribersRecordEndPoint then second api call will concat on top
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPointToSend);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        Assert.assertEquals(temp.getString("eventName").trim(), eventName);
        System.out.println("Even name on DPBI:  " + eventName);

        Assert.assertEquals(temp.getInt("eventId"), eventId.get());
        System.out.println("Event id on DPBI:  " + eventId.get());

        Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId);
        System.out.println("Subscriber id on DPBI:   " + subscriberId);
        System.out.println("Event is subscribed to DPBI list");

        return recordCreated;
    }

    @Then("I will verify business events {string} are generated with")
    public void verifyEvents(String enrollmentEvent, Map<String, String> data) {
        JsonObject event = new JsonObject();
        event.addProperty("correlationId", APIEnrollmentController.traceIdTOCheckEvents.get());
        event.addProperty("eventName", enrollmentEvent);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println("=========================================================================================================");

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));
            System.out.println("=========================================================================================================");

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            System.out.println("Payload: " + payLoadData);

            eventId.set((int) eventsData.get("eventId"));
            JsonObject dataObjectData = null;
            if (enrollmentEvent.equals("ENROLLMENT_UPDATE_EVENT")
                    || enrollmentEvent.equals("ELIGIBILITY_UPDATE_EVENT")
                    || enrollmentEvent.equals("ELIGIBILITY_SAVE_EVENT")
                    || enrollmentEvent.equals("LOSS_OF_ELIGIBILITY")
                    || enrollmentEvent.equals("NEW_RETROACTIVE_ENROLLMENT")
            ) {
                if (payLoadData.get("dataObject").isJsonObject()) {
                    dataObjectData = payLoadData.getAsJsonObject("dataObject");
                } else if (payLoadData.get("dataObject").isJsonArray()) {
                    dataObjectData = payLoadData.getAsJsonArray("dataObject").get(0).getAsJsonObject();
                }
            }
            if (dataObjectData.get("consumerId").getAsString().equalsIgnoreCase(APIConsumerPopulationDmnController.consumerId.get())) {
                assertEquals(dataObjectData.get("consumerId").getAsString(), APIConsumerPopulationDmnController.consumerId.get());
                if (data.containsKey("consumerName")) {
                    assertEquals(dataObjectData.get("consumerName").getAsString(), APIConsumerPopulationDmnController.consumerFirstName.get() + " " + APIConsumerPopulationDmnController.consumerLastName.get());
                }
                if (data.containsKey("externalCaseId")) {
                    assertEquals(dataObjectData.get("externalCaseId").getAsString(), APIConsumerPopulationDmnController.caseIdentificationNo.get());
                }
                if (data.containsKey("externalConsumerId")) {
                    assertEquals(dataObjectData.get("externalConsumerId").getAsString(), APIConsumerPopulationDmnController.externalConsumerId.get());
                }
                if (data.containsKey("consumerDateOfBirth")) {
                    assertNotNull(dataObjectData.get("consumerDateOfBirth").getAsString());
                }
                if (data.containsKey("channel")) {
                    assertEquals(dataObjectData.get("channel").getAsString(), data.get("channel"));
                }
                if (data.containsKey("bussinessEventName")) {
                    assertEquals(dataObjectData.get("bussinessEventName").getAsString(), data.get("bussinessEventName"));
                }
                if (data.containsKey("processDate")) {
                    assertFalse(dataObjectData.get("processDate").getAsString().isEmpty());
                }
                if (data.containsKey("eligibilityStartDate")) {
                    String eligibilityStartDate = apitdu.get().getStartDate(data.get("eligibilityStartDate"));
                    Assert.assertEquals(dataObjectData.get("eligibilityStartDate").getAsString(), eligibilityStartDate);
                }
                if (data.containsKey("eligibilityEndDate")) {
                    assertEquals(dataObjectData.get("eligibilityEndDate").getAsString(), data.get("eligibilityEndDate"));
                }
                if (data.containsKey("bussinessEventName")) {
                    assertEquals(dataObjectData.get("bussinessEventName").getAsString(), data.get("bussinessEventName"));
                }
                if (data.containsKey("eligibilityEndReason")) {
                    assertEquals(dataObjectData.get("eligibilityEndReason").getAsString(), data.get("eligibilityEndReason"));
                }
                if (data.containsKey("eligibilityCoverageCode")) {
                    assertEquals(dataObjectData.get("eligibilityCoverageCode").getAsString(), data.get("eligibilityCoverageCode"));
                }
                if (data.containsKey("eligibilityProgramCode")) {
                    assertEquals(dataObjectData.get("eligibilityProgramCode").getAsString(), data.get("eligibilityProgramCode"));
                }
                if (data.containsKey("eligibilityExemptionCode")) {
                    assertEquals(dataObjectData.get("eligibilityExemptionCode").getAsString(), data.get("eligibilityExemptionCode"));
                }
                if (data.containsKey("eligibilitySubProgramCode")) {
                    assertEquals(dataObjectData.get("eligibilitySubProgramCode").getAsString(), data.get("eligibilitySubProgramCode"));
                }
                if (data.containsKey("consumerPopulation")) {
                    assertEquals(dataObjectData.get("consumerPopulation").getAsString(), data.get("consumerPopulation"));
                }
                if (data.containsKey("benefitStatus")) {
                    assertEquals(dataObjectData.get("benefitStatus").getAsString(), data.get("benefitStatus"));
                }
                if (data.containsKey("startDate")) {
                    String startDate = apitdu.get().getStartDate(data.get("startDate"));
                    Assert.assertEquals(dataObjectData.get("startDate").getAsString(), startDate);
                }
                if (data.containsKey("enrollmentType")) {
                    Assert.assertEquals(dataObjectData.get("enrollmentType").getAsString(), data.get("enrollmentType"));
                }
                if (data.containsKey("status")) {
                    Assert.assertEquals(dataObjectData.get("status").getAsString(), data.get("status"));
                }
                if (data.containsKey("createdOn")) {
                    Assert.assertNotNull(dataObjectData.get("createdOn").getAsString());
                }
                if (data.containsKey("createdBy")) {
                    Assert.assertEquals(dataObjectData.get("createdBy").getAsString(), data.get("createdBy"));
                }
                recordFound = true;
            }

            assertTrue(recordFound);
        }
    }

    @Then("I will verify business events are generated with data")
    public void verifyBusinessEvents(Map<String, String> data) {
        String enrollmentEvent = data.get("eventName");
        JsonObject event = new JsonObject();
        if (data.containsKey("correlationId")) {
            System.out.println(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("correlationId")));
            event.addProperty("correlationId", data.get("correlationId").equals("null") ? null : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("correlationId"))).replace(".0", ""));
        } else {
            event.addProperty("correlationId", APIEnrollmentController.traceIdTOCheckEvents.get());
        }
        if(data.containsKey("module")){
            event.addProperty("module", data.get("module"));
        }
        event.addProperty("eventName", enrollmentEvent);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
//        System.out.println(api.responseString);

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        // if consumerId is not found in events it will give false pass result
        // that's why we need to verify that consumerId found in events list
        boolean consumerFoundInEventsList = false;

        String consumerId =
                data.containsKey("consumerId")
                        ? String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId"))).replace(".0", "")
                        : APIConsumerPopulationDmnController.consumerId.get();

        for (int i = 0; i < eventsContent.size(); i++) {
            JsonObject dataObjectData = null;
            HashMap eventsData = (HashMap) eventsContent.get(i);

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            List<String> events = new ArrayList<>(Arrays.asList("ENROLLMENT_UPDATE_EVENT", "ENROLLMENT_EDIT", "ENROLLMENT_SAVE_EVENT",
                    "NEW_ENROLLMENT_OVERRIDE", "NEW_ENROLLMENT_EDIT_OVERRIDE",
                    "OTHER_ENROLLMENT_SAVE_EVENT", "OTHER_ENROLLMENT_UPDATE_EVENT",
                    "ELIGIBILITY_UPDATE_EVENT", "ELIGIBILITY_SAVE_EVENT",
                    "OTHER_ELIGIBILITY_SAVE_EVENT", "OTHER_ELIGIBILITY_UPDATE_EVENT",
                    "REINSTATEMENT_OF_ELIGIBILITY", "LOSS_OF_ELIGIBILITY",
                    "NEW_ENROLLMENT", "NEW_ENROLLMENT_ACCEPT_RESPONSE", "NEW_ENROLLMENT_REJECT_RESPONSE", "NEW_RETROACTIVE_ENROLLMENT",
                    "NEW_ENROLLMENT_EDIT_OVERRIDE","NEW_ENROLLMENT_ACCEPT_RESPONSE_AUTO_ASSIGNMENT",
                    "PLAN_CHANGE", "PLAN_CHANGE_EDIT", "PLAN_CHANGE_ACCEPT_RESPONSE", "PLAN_CHANGE_REJECT_RESPONSE", "PLAN_CHANGE_OVERRIDE", "PLAN_CHANGE_EDIT_OVERRIDE",
                    "DISENROLL_REQUESTED", "DISENROLL_REQUEST_ACCEPT_RESPONSE", "DISENROLL_REQUEST_REJECT_RESPONSE",
                    "DISENROLL_WITH_LOSS_OF_ELIGIBILITY", "DISREGARDED_NEW_ENROLLMENT", "DISREGARDED_DISENROLL_REQUEST", "DISREGARDED_PLAN_CHANGE",
                    "RETRO_ACTIVE_ENROLLMENT_DATE_CHANGE", "RETRO_ACTIVE_ELIGIBILITY_DATE_CHANGE",
                    "RECONCILIATION", "FREE_CHANGE",
                    "CONSUMER_SAVE_EVENT", "CONSUMER_UPDATE_EVENT",
                    "PROSPECTIVE_ELIGIBILITY_DATE_CHANGE", "SPECIAL_COVERAGE", "LOCKIN_LOCKOUT",
                    "DEFAULT_ELIGIBILITY", "DEFAULT_ENROLLMENT"));

            if (events.contains(enrollmentEvent)) {
                if (enrollmentEvent.equalsIgnoreCase("Reconciliation") && data.keySet().contains("module")){
                    if (!data.get("module").equalsIgnoreCase(eventsData.get("module").toString())){
                        System.out.println("Found EventName    : " + eventsData.get("eventName").toString());
                        System.out.println("Searched EventName : " + data.get("eventName"));
                        System.out.println("Found Module       : " + eventsData.get("module").toString());
                        System.out.println("Searched Module    : " + data.get("module"));
                        System.out.println("EventName matches but Module does not match so skipping this found businessevent to search further!!");
                        continue;
                    }
                }

                if (payLoadData.get("dataObject").isJsonObject()) {
                    dataObjectData = payLoadData.getAsJsonObject("dataObject");
                } else if (payLoadData.get("dataObject").isJsonArray()) {
                    dataObjectData = payLoadData.getAsJsonArray("dataObject").get(0).getAsJsonObject();
                }

                if (enrollmentEvent.equalsIgnoreCase("ENROLLMENT_UPDATE_EVENT") && data.keySet().contains("txnStatus")){
                    System.out.println("Checking ENROLLMENT_UPDATE_EVENT txnStatus");
                    if (!data.get("txnStatus").equalsIgnoreCase(dataObjectData.get("txnStatus").getAsString())){
                        System.out.println("Found EventName    : " + eventsData.get("eventName").toString());
                        System.out.println("Searched EventName : " + data.get("eventName"));
                        System.out.println("Found txnStatus       : " + dataObjectData.get("txnStatus").getAsString());
                        System.out.println("Searched txnStatus    : " + data.get("txnStatus"));
                        System.out.println("EventName matches but txnStatus/status does not match so skipping this found businessevent to search further!!");
                        continue;
                    }
                }
            }

            System.out.println("looking for consumerId in events list");
            String actualConsumerId = "";
            try {
                actualConsumerId = dataObjectData.get("consumerId").getAsString();
                System.out.println("dataObjectData.get(\"consumerId\").getAsString() = " + actualConsumerId);
            } catch (java.lang.UnsupportedOperationException e) {
                actualConsumerId = dataObjectData.getAsJsonObject("coreEligibility").get("consumerId").getAsString();
                System.out.println("dataObjectData.getAsJsonObject(\"coreEligibility\").get(\"consumerId\").getAsString() = " + actualConsumerId);
            }
            System.out.println("APIConsumerPopulationDmnController.consumerId.get() = " + consumerId);

            if (actualConsumerId.equalsIgnoreCase(consumerId)) {
                // consumerId found in events list
                consumerFoundInEventsList = true;

                System.out.println("payLoadData = " + payLoadData);
                List<String> failedKeys = new ArrayList<>();

                for (String key : data.keySet()) {
                    try {
                        switch (key) {
                            case "newValue.enrollmentStartDate":
                            case "oldValue.enrollmentStartDate":
                                // Since actual is jsonnull we cannot read jsonnull as string so it fails. Need to update print out code below
//                                String actual = dataObjectData.getAsJsonObject().get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString();
                                System.out.println("expected " + key + " value : " + apitdu.get().getStartDate(data.get(key)));
//                                System.out.println("actual " + key + " value   : " + actual);
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
//                                    assertTrue(dataObjectData.get(key).isJsonNull(), key + " is not null! - FAIL!");
                                    assertTrue(dataObjectData.getAsJsonObject().get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).isJsonNull(), key + " is not null! - FAIL!");
                                } else {
                                    Assert.assertEquals(dataObjectData.getAsJsonObject().get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString(),
                                            apitdu.get().getStartDate(data.get(key)), key + " mismatch!");
                                }
                                break;
                            case "newValue.enrollmentEndDate":
                            case "oldValue.enrollmentEndDate":
                                // Since actual is jsonnull we cannot read jsonnull as string so it fails. Need to update print out code below
//                                actual = dataObjectData.getAsJsonObject().get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString();
                                System.out.println("expected " + key + " value : " + apitdu.get().getEndDate(data.get(key)));
//                                System.out.println("actual " + key + " value   : " + actual);
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
//                                    assertTrue(dataObjectData.get(key).isJsonNull(), key + " is not null! - FAIL!");
                                    assertTrue(dataObjectData.getAsJsonObject().get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).isJsonNull(), key + " is not null! - FAIL!");
                                } else {
                                    Assert.assertEquals(dataObjectData.getAsJsonObject().get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString(),
                                            apitdu.get().getEndDate(data.get(key)), key + " mismatch!");
                                }
                                break;
                            case "taskId":
                            case "contactId":
                                Assert.assertNotNull(dataObjectData.get("contactId"));
                                break;
                            case "externalCaseId":
                            case "caseId":
                            case "externalConsumerId":
                                Assert.assertEquals(dataObjectData.get(key).getAsString(),
                                        API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString().replace(".0", ""), key + " mismatch!");
                                break;
                            case "updatedOn":
                            case "createdOn":
                            case "disenrollRequestedDate":
                                Assert.assertEquals(dataObjectData.get(key).getAsString().substring(0, 10),
                                        apitdu.get().getStartDate(data.get(key)), key + " mismatch!");
                                break;
                            case "disenrollEffectiveDate":
                                Assert.assertEquals(dataObjectData.get(key).getAsString().substring(0, 10),
                                        apitdu.get().getEndDate(data.get(key)), key + " mismatch!");
                                break;
                            case "processDate":
                                assertFalse(dataObjectData.get("processDate").getAsString().isEmpty());
                                break;
                            case "consumerName":
                                String fullName = data.get(key).isEmpty()
                                        ? APIConsumerPopulationDmnController.consumerFirstName.get() + " " + APIConsumerPopulationDmnController.consumerLastName.get()
                                        : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key) + ".firstName") + " " + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key) + ".lastName");
                                assertEquals(dataObjectData.get("consumerName").getAsString(), fullName, key + " missmatch!");
                                break;
                            case "eligibilityStartDate":
                            case "anniversaryDate":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.get(key).isJsonNull(), key + " is not null! - FAIL!");
                                } else {
                                    Assert.assertEquals(dataObjectData.get(key).getAsString(), apitdu.get().getStartDate(data.get(key)), key + " mismatch!");
                                }
                                break;
                            case "eligibilityEndDate":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.get(key).isJsonNull(), key + " is not null! - FAIL!");
                                } else {
                                    Assert.assertEquals(dataObjectData.get(key).getAsString(), apitdu.get().getEndDate(data.get(key)), key + " mismatch!");
                                }
                                break;
                            case "channel":
                            case "createdBy":
                            case "recordId":
                            case "eligibilityEndReason":
                            case "eligibilityCategoryCode":
                            case "eligibilityCoverageCode":
                            case "eligibilityProgramCode":
                            case "eligibilityExemptionCode":
                            case "eligibilitySubProgramCode":
                            case "consumerPopulation":
                            case "benefitStatus":
                            case "planChangeReason":
                            case "planEndDateReason":
                            case "rejectionReason":
                            case "selectionStatus":
                            case "enrollmentType":
                            case "selectionReason":
                            case "planSelectionReason":
                            case "disenrollReason":
                            case "disenrollSelectionStatus":
                            case "pcpNpi":
                            case "pcpName":
                            case "oldValue":
                            case "reconciliationAction":
                            case "outCome":
                            case "outcome":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.get(key).isJsonNull(), key + " is not null! - FAIL!");
                                } else {
                                    System.out.println("expected " + key + " value : " + data.get(key));
                                    System.out.println("actual " + key + " value   : " + dataObjectData.get(key).getAsString());
                                    Assert.assertEquals(dataObjectData.get(key).getAsString(), data.get(key), key + " mismatch!");
                                }
                                break;
                            // 2 hops
                            case "eligibility.eligibilityStartDate":
                            case "enrollment.enrollmentStartDate":
                            case "priorSegment.eligibilityStartDate":
                            case "newSegment.eligibilityStartDate":
                            case "priorPlan.enrollmentStartDate":
                            case "newPlan.enrollmentStartDate":
                            case "previousEnrollment.enrollmentStartDate":
                            case "requestedSelection.enrollmentStartDate":
                            case "priorPlan.anniversaryDate":
                            case "newPlan.anniversaryDate":
                            case "previousEnrollment.anniversaryDate":
                            case "requestedSelection.anniversaryDate":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else if (data.get(key).equals("getFromUISelected")) {
                                    assertEquals(dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString(),
                                            LocalDate.parse(CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get(), dfUIver.get()).format(df.get()),
                                            key + " mismatch!");
                                } else {
                                    assertEquals(dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString(),
                                            apitdu.get().getStartDate(data.get(key)), key + " mismatch!");
                                }
                                break;
                            case "eligibility.eligibilityEndDate":
                            case "enrollment.enrollmentEndDate":
                            case "priorSegment.eligibilityEndDate":
                            case "newSegment.eligibilityEndDate":
                            case "priorPlan.enrollmentEndDate":
                            case "newPlan.enrollmentEndDate":
                            case "previousEnrollment.enrollmentEndDate":
                            case "requestedSelection.enrollmentEndDate":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else if (data.get(key).equals("DayBeforeSelectedPlanStartDate")) {
                                    assertEquals(dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString(),
                                            LocalDate.parse(CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get(), dfUIver.get()).minusDays(1).format(df.get()), key + " mismatch!");
                                } else {
                                    assertEquals(dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString(),
                                            apitdu.get().getEndDate(data.get(key)), key + " mismatch!");
                                }
                                break;
                            case "eligibility.eligibilityEndReason":
                            case "eligibility.eligibilityCategoryCode":
                            case "eligibility.eligibilityProgramCode":
                            case "eligibility.eligibilitySubProgramCode":
                            case "eligibility.eligibilityCoverageCode":
                            case "eligibility.eligibilityExemptionCode":
                            case "eligibility.consumerPopulation":
                            case "eligibility.consumerBenefitStatus":
                            case "enrollment.planChangeReason":
                            case "enrollment.rejectionReason":
                            case "enrollment.planCode":
                            case "enrollment.enrollmentType":
                            case "enrollment.selectionStatus":
                            case "enrollment.consumerPopulation":
                            case "enrollment.consumerBenefitStatus":
                            case "oldValue.eligibilityProgramCode":
                            case "oldValue.eligibilitySubProgramCode":
                            case "oldValue.eligibilityEndReason":
                            case "oldValue.eligibilityCategoryCode":
                            case "oldValue.eligibilityCoverageCode":
                            case "oldValue.eligibilityExemptionCode":
                            case "oldValue.benefitStatus":
                            case "oldValue.planChangeReason":
                            case "oldValue.rejectionReason":
                            case "oldValue.selectionReason":
                            case "oldValue.planCode":
                            case "oldValue.enrollmentType":
                            case "oldValue.selectionStatus":
                            case "oldValue.pcpNpi":
                            case "oldValue.pcpName":
                            case "oldValue.additionalFileds":
                            case "newValue.eligibilityProgramCode":
                            case "newValue.eligibilitySubProgramCode":
                            case "newValue.eligibilityEndReason":
                            case "newValue.eligibilityCategoryCode":
                            case "newValue.eligibilityCoverageCode":
                            case "newValue.eligibilityExemptionCode":
                            case "newValue.benefitStatus":
                            case "newValue.planChangeReason":
                            case "newValue.rejectionReason":
                            case "newValue.selectionReason":
                            case "newValue.planCode":
                            case "newValue.enrollmentType":
                            case "newValue.selectionStatus":
                            case "newValue.pcpNpi":
                            case "newValue.pcpName":
                            case "priorSegment.eligibilityProgramCode":
                            case "priorSegment.eligibilitySubProgramCode":
                            case "priorSegment.eligibilityEndReason":
                            case "priorSegment.eligibilityCategoryCode":
                            case "priorSegment.eligibilityCoverageCode":
                            case "priorSegment.eligibilityExemptionCode":
                            case "priorSegment.benefitStatus":
                            case "newSegment.eligibilityProgramCode":
                            case "newSegment.eligibilitySubProgramCode":
                            case "newSegment.eligibilityEndReason":
                            case "newSegment.eligibilityCategoryCode":
                            case "newSegment.eligibilityCoverageCode":
                            case "newSegment.eligibilityExemptionCode":
                            case "newSegment.benefitStatus":
                            case "priorPlan.planSelectionReason":
                            case "priorPlan.planChangeReason":
                            case "priorPlan.planCode":
                            case "priorPlan.enrollmentType":
                            case "priorPlan.selectionStatus":
                            case "priorPlan.pcpNpi":
                            case "priorPlan.pcpName":
                            case "priorPlan.pdpNpi":
                            case "priorPlan.pdpName":
                            case "priorPlan.outCome":
                            case "newPlan.planSelectionReason":
                            case "newPlan.planChangeReason":
                            case "newPlan.rejectionReason":
                            case "newPlan.planCode":
                            case "newPlan.enrollmentType":
                            case "newPlan.selectionStatus":
                            case "newPlan.pcpNpi":
                            case "newPlan.pcpName":
                            case "newPlan.pdpNpi":
                            case "newPlan.pdpName":
                            case "newPlan.outCome":
                            case "previousEnrollment.benefitStatus":
                            case "previousEnrollment.planChangeReason":
                            case "previousEnrollment.rejectionReason":
                            case "previousEnrollment.planSelectionReason":
                            case "previousEnrollment.selectionReason":
                            case "previousEnrollment.planCode":
                            case "previousEnrollment.enrollmentType":
                            case "previousEnrollment.selectionStatus":
                            case "previousEnrollment.pcpNpi":
                            case "previousEnrollment.pcpName":
                            case "previousEnrollment.pdpNpi":
                            case "previousEnrollment.pdpName":
                            case "requestedSelection.benefitStatus":
                            case "requestedSelection.planChangeReason":
                            case "requestedSelection.rejectionReason":
                            case "requestedSelection.planSelectionReason":
                            case "requestedSelection.selectionReason":
                            case "requestedSelection.enrollmentType":
                            case "requestedSelection.selectionStatus":
                            case "requestedSelection.pcpNpi":
                            case "requestedSelection.pcpName":
                            case "requestedSelection.pdpNpi":
                            case "requestedSelection.pdpName":
                                System.out.println("expected " + key + " value : " + data.get(key));
//                                Since actual is jsonnull we cannot read jsonnull as string so it fails. Need to update print out code below
//                                System.out.println("actual " + key + " value   : " + dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString());
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertEquals(dataObjectData.get(key.split("\\.")[0]).getAsJsonObject().get(key.split("\\.")[1]).getAsString(),
                                            data.get(key), key + " mismatch!");
                                }
                                break;
                            // 3 hops
                            case "additionalFileds.exactMatch.Managed Care Status":
                            case "additionalFileds.exactMatch.HIP Status":
                            case "additionalFileds.noMatch.HIP Status":
                            case "additionalFileds.noMatch.Base Eligibility Category Code":
                            case "additionalFileds.noMatch.Managed Care Status":
                            case "additionalFileds.OtherSegmentInfos.Open Enrollment Status":
                            case "additionalFileds.OtherSegmentInfos.Debt Indicator":
                            case "additionalFileds.OtherSegmentInfos.Potential Plus Indicator":
                            case "additionalFileds.noEligSegment.hipStatus":
                            case "additionalFileds.noEligSegment.Added Eligibility Category Code":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).getAsString(),
                                            data.get(key), key + " mismutch!");
                                }
                                break;
                            case "additionalFileds.noMatch.Base Eligibility Category  Begin Date":
                            case "additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date":
                            case "additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date":
                            case "additionalFileds.OtherSegmentInfos.Redetermination Date":
                            case "additionalFileds.OtherSegmentInfos.Benefit Effective Date":
                            case "additionalFileds.noEligSegment.Added Eligibility Category Begin Date":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).getAsString(),
                                            apitdu.get().getStartDate(data.get(key)), key + " mismutch!");
                                }
                                break;
                            case "additionalFileds.noMatch.Base Eligibility Category End Date":
                            case "additionalFileds.OtherSegmentInfos.Open Enrollment End Date":
                            case "additionalFileds.OtherSegmentInfos.Conditional Eligibility Date":
                            case "additionalFileds.OtherSegmentInfos.Benefit End Date":
                            case "additionalFileds.noEligSegment.Added Eligibility Category End Date":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).get(key.split("\\.")[2]).getAsString(),
                                            apitdu.get().getEndDate(data.get(key)), key + " mismutch!");
                                }
                                break;
                            // 4 hops
                            case "additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Code":
                            case "additionalFileds.noMatch.ADDED_CATEGORY2.Added Eligibility Category Code":
                            case "additionalFileds.noMatch.ADDED_CATEGORY3.Added Eligibility Category Code":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Code":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Code":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY3.Added Eligibility Category Code":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).getAsString(),
                                            data.get(key), key + " mismutch!");
                                }
                                break;
                            case "additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date":
                            case "additionalFileds.noMatch.ADDED_CATEGORY2.Added Eligibility Category Begin Date":
                            case "additionalFileds.noMatch.ADDED_CATEGORY3.Added Eligibility Category Begin Date":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Begin Date":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY3.Added Eligibility Category Begin Date":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).getAsString(),
                                            apitdu.get().getStartDate(data.get(key)), key + " mismutch!");
                                }
                                break;
                            case "additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category End Date":
                            case "additionalFileds.noMatch.ADDED_CATEGORY2.Added Eligibility Category End Date":
                            case "additionalFileds.noMatch.ADDED_CATEGORY3.Added Eligibility Category End Date":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category End Date":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category End Date":
                            case "additionalFileds.exactMatch.ADDED_CATEGORY3.Added Eligibility Category End Date":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonArray(key.split("\\.")[0]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[1]).getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).getAsString(),
                                            apitdu.get().getEndDate(data.get(key)), key + " mismutch!");
                                }
                                break;
                            case "oldValue.additionalFileds.eligibilityUpdated.Managed Care Status":
                            case "oldValue.additionalFileds.eligibilityUpdated.HIP Status":
                            case "oldValue.additionalFileds.eligibilityUpdated.Base Eligibility Category Code":
                            case "newValue.additionalFileds.eligibilityUpdated.Managed Care Status":
                            case "newValue.additionalFileds.eligibilityUpdated.HIP Status":
                            case "newValue.additionalFileds.eligibilityUpdated.Base Eligibility Category Code":
                            case "priorSegment.additionalFileds.programChange.Managed Care Status":
                            case "priorSegment.additionalFileds.programChange.HIP Status":
                            case "priorSegment.additionalFileds.programChange.Base Eligibility Category Code":
                            case "newSegment.additionalFileds.programChange.Managed Care Status":
                            case "newSegment.additionalFileds.programChange.HIP Status":
                            case "newSegment.additionalFileds.programChange.Base Eligibility Category Code":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Open Enrollment Status":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Debt Indicator":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Potential Plus Indicator":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).getAsString(),
                                            data.get(key), key + " mismutch!");
                                }
                                break;
                            case "oldValue.additionalFileds.eligibilityUpdated.Base Eligibility Category  Begin Date":
                            case "newValue.additionalFileds.eligibilityUpdated.Base Eligibility Category  Begin Date":
                            case "priorSegment.additionalFileds.programChange.Base Eligibility Category  Begin Date":
                            case "newSegment.additionalFileds.programChange.Base Eligibility Category  Begin Date":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Redetermination Date":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Benefit Effective Date":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).getAsString(),
                                            apitdu.get().getStartDate(data.get(key)), key + " mismutch!");
                                }
                                break;
                            case "oldValue.additionalFileds.eligibilityUpdated.Base Eligibility Category End Date":
                            case "newValue.additionalFileds.eligibilityUpdated.Base Eligibility Category End Date":
                            case "priorSegment.additionalFileds.programChange.Base Eligibility Category End Date":
                            case "newSegment.additionalFileds.programChange.Base Eligibility Category End Date":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Open Enrollment End Date":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Conditional Eligibility Date":
                            case "newSegment.additionalFileds.OtherSegmentInfos.Benefit End Date":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject().getAsJsonObject(key.split("\\.")[2]).get(key.split("\\.")[3]).getAsString(),
                                            apitdu.get().getEndDate(data.get(key)), key + " mismutch!");
                                }
                                break;
                            // 5 hops
                            case "priorSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category Code":
                            case "newSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category Code":
                            case "newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY1.Added Eligibility Category Code":
                            case "priorSegment.additionalFileds.programChange.ADDED_CATEGORY2.Added Eligibility Category Code":
                            case "newSegment.additionalFileds.programChange.ADDED_CATEGORY2.Added Eligibility Category Code":
                            case "newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY2.Added Eligibility Category Code":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                                    .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                            .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                                    .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).getAsString(),
                                            data.get(key), key + " mismutch!");
                                }
                                break;
                            case "priorSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category End Date":
                            case "newSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category End Date":
                            case "newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY1.Added Eligibility Category End Date":
                            case "priorSegment.additionalFileds.programChange.ADDED_CATEGORY2.Added Eligibility Category End Date":
                            case "newSegment.additionalFileds.programChange.ADDED_CATEGORY2.Added Eligibility Category End Date":
                            case "newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY2.Added Eligibility Category End Date":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                                    .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                            .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                                    .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).getAsString(),
                                            apitdu.get().getEndDate(data.get(key)), key + " mismutch!");
                                }
                                break;
                            case "priorSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category Begin Date":
                            case "newSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category Begin Date":
                            case "newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY1.Added Eligibility Category Begin Date":
                            case "priorSegment.additionalFileds.programChange.ADDED_CATEGORY2.Added Eligibility Category Begin Date":
                            case "newSegment.additionalFileds.programChange.ADDED_CATEGORY2.Added Eligibility Category Begin Date":
                            case "newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY2.Added Eligibility Category Begin Date":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                                    .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).isJsonNull(),
                                            key + " is not null! - FAIL!");
                                } else {
                                    assertFalse(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                            .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).isJsonNull());
                                    assertEquals(dataObjectData.getAsJsonObject(key.split("\\.")[0]).getAsJsonArray(key.split("\\.")[1]).get(0).getAsJsonObject()
                                                    .getAsJsonObject(key.split("\\.")[2]).getAsJsonObject(key.split("\\.")[3]).get(key.split("\\.")[4]).getAsString(),
                                            apitdu.get().getStartDate(data.get(key)), key + " mismutch!");
                                }
                                break;
                            case "enrollmentStartDate":
                                String expVal = data.get("enrollmentStartDate").contains("-")
                                        ? data.get("enrollmentStartDate")
                                        : data.get(key).equals("getFromUISelected")
                                        ? LocalDate.parse(CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get(), dfUIver.get()).format(df.get())
                                        : apitdu.get().getStartDate(data.get("enrollmentStartDate"));
                                String actVal = dataObjectData.has("startDate")
                                        ? dataObjectData.get("startDate").getAsString()
                                        : dataObjectData.get("enrollmentStartDate").getAsString();
                                System.out.println("expected " + key + " value : " + expVal);
                                System.out.println("actual " + key + " value   : " + actVal);
                                assertEquals(actVal, expVal);
                                break;
                            case "enrollmentEndDate":
                                String actualEnrollmentEndDate = dataObjectData.has("endDate")
                                        // JsonObject.get("NodeName").getAsString() -> gives NullPointer if it's null
                                        ? dataObjectData.get("endDate").toString().replaceAll("\"", "")
                                        : dataObjectData.get("enrollmentEndDate").toString().replaceAll("\"", "");
                                if (data.get("enrollmentEndDate").isEmpty() || data.get("enrollmentEndDate").equals("null")) {
                                    assertEquals(actualEnrollmentEndDate, "null", "enrollmentEndDate is not null!");
                                } else if (data.get(key).equals("DayBeforeSelectedPlanStartDate")) {
                                    assertEquals(actualEnrollmentEndDate, LocalDate.parse(CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get(), dfUIver.get()).minusDays(1).format(df.get()),
                                            key + " mismutch!");
                                } else {
                                    System.out.println("expected " + key + " value : " + apitdu.get().getEndDate(data.get("enrollmentEndDate")));
                                    System.out.println("actual " + key + " value   : " + actualEnrollmentEndDate);
                                    assertEquals(actualEnrollmentEndDate, apitdu.get().getEndDate(data.get("enrollmentEndDate")));
                                }
                                break;
                            case "planCode":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.get(key).isJsonNull(), key + " is not null! - FAIL!");
                                } else if (data.get(key).equals("getFromUISelected")) {
                                    assertEquals(dataObjectData.get(key).getAsString(), getPlanCodeBasedOnUISelection(), key + " mismatch!");
                                } else if (data.get(key).equals("planCodeFromEnrollmentDetails")) {
                                    assertEquals(dataObjectData.get(key).getAsString(), APIEnrollmentController.getPlanCodeFromEnrollmentDetails(consumerId), key + " mismatch!");
                                } else {
                                    assertEquals(dataObjectData.get(key).getAsString(), data.get(key));
                                }
                                break;
                            case "requestedSelection.planCode":
                                if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonObject(key.split("\\.")[0]).get(key.split("\\.")[1]).isJsonNull());
                                } else if (data.get(key).equals("getFromUISelected")) {
                                    assertEquals(dataObjectData.getAsJsonObject(key.split("\\.")[0]).get(key.split("\\.")[1]).getAsString(),
                                            getPlanCodeBasedOnUISelection(), key + " mismatch!");
                                } else {
                                    assertEquals(dataObjectData.getAsJsonObject(key.split("\\.")[0]).get(key.split("\\.")[1]).getAsString(),
                                            data.get(key), key + " mismatch!");
                                }
                                break;
                            case "providerFirstName":
                                if (data.get("providerFirstName").isEmpty() || data.get("providerFirstName").equals("null")) {
                                    assertTrue(dataObjectData.getAsJsonObject("enrollmentProvider").get("providerFirstName").isJsonNull());
                                }
                                break;
                            case "DPBI":
                                try {
                                    eventId.set(Integer.parseInt(eventsData.get("eventId").toString()));
                                    dpbiEventGenerationCheck(data.get("eventName"), "DPBI");
                                } catch (Exception e) {
                                    fail(data.get("eventName") + " was NOT captured in DPBI subscriber! - FAIL!");
                                }
                                break;
                            case "correlationId":
                                // ignore, this key is used in another actions
                                break;
                            case "status":
                            case "errorStack":
                                assertEquals(eventsData.get(key), data.get(key), key + " missmatch! - FAIL!");
                                break;

                            // 2 hops with array
                            case "lockInLockOut.[0].medicaidId":
                            case "lockInLockOut.[0].memberDateEffective":
                            case "lockInLockOut.[0].memberDateEnd":
                            case "lockInLockOut.[0].providerDateEffective":
                            case "lockInLockOut.[0].providerDateEnd":
                            case "lockInLockOut.[0].providerId":
                            case "lockInLockOut.[0].providerName":
                            case "lockInLockOut.[0].providerSpecialty":
                            case "lockInLockOut.[0].providerTypeCode":
                            case "lockInLockOut.[0].restrictionIndicator":
                            case "lockInLockOut.[1].medicaidId":
                            case "lockInLockOut.[1].memberDateEffective":
                            case "lockInLockOut.[1].memberDateEnd":
                            case "lockInLockOut.[1].providerDateEffective":
                            case "lockInLockOut.[1].providerDateEnd":
                            case "lockInLockOut.[1].providerId":
                            case "lockInLockOut.[1].providerName":
                            case "lockInLockOut.[1].providerSpecialty":
                            case "lockInLockOut.[1].providerTypeCode":
                            case "lockInLockOut.[1].restrictionIndicator":
                            default:
                                if (key.matches(".*\\.\\[\\d*\\]\\..*")) {
                                    switch (key.split("\\.\\[|\\]\\.")[2]) {
                                        case "locDateEffective":
                                        case "memberDateEffective":
                                        case "providerDateEffective":
                                            assertEquals(dataObjectData.get(key.split("\\.\\[|\\]\\.")[0]).getAsJsonArray()
                                                            .get(Integer.parseInt(key.split("\\.\\[|\\]\\.")[1])).getAsJsonObject()
                                                            .get(key.split("\\.\\[|\\]\\.")[2]).getAsString(),
                                                    apitdu.get().getStartDate(data.get(key)), key + " mismatch! - FAIL!");
                                            break;
                                        case "locDateEnd":
                                        case "memberDateEnd":
                                        case "providerDateEnd":
                                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                                assertTrue(dataObjectData.get(key.split("\\.\\[|\\]\\.")[0]).getAsJsonArray()
                                                        .get(Integer.parseInt(key.split("\\.\\[|\\]\\.")[1])).getAsJsonObject()
                                                        .get(key.split("\\.\\[|\\]\\.")[2]).isJsonNull(), key + " is not null! - FAIL!");
                                            } else {
                                                assertEquals(dataObjectData.get(key.split("\\.\\[|\\]\\.")[0]).getAsJsonArray()
                                                                .get(Integer.parseInt(key.split("\\.\\[|\\]\\.")[1])).getAsJsonObject()
                                                                .get(key.split("\\.\\[|\\]\\.")[2]).getAsString(),
                                                        apitdu.get().getEndDate(data.get(key)), key + " mismatch! - FAIL!");
                                            }
                                            break;
                                        case "providerId":
                                        case "codeLevelOfCare":
                                        case "providerName":
                                        case "providerSpecialty":
                                        case "providerTypeCode":
                                        case "restrictionIndicator":
                                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                                assertTrue(dataObjectData.get(key.split("\\.\\[|\\]\\.")[0]).getAsJsonArray()
                                                        .get(Integer.parseInt(key.split("\\.\\[|\\]\\.")[1])).getAsJsonObject()
                                                        .get(key.split("\\.\\[|\\]\\.")[2]).isJsonNull(), key + " is not null! - FAIL!");
                                            } else {
                                                assertEquals(dataObjectData.get(key.split("\\.\\[|\\]\\.")[0]).getAsJsonArray()
                                                                .get(Integer.parseInt(key.split("\\.\\[|\\]\\.")[1])).getAsJsonObject()
                                                                .get(key.split("\\.\\[|\\]\\.")[2]).getAsString(),
                                                        data.get(key), key + " mismatch! - FAIL!");
                                            }
                                            break;
                                        case "medicaidId":
                                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                                assertTrue(dataObjectData.get(key.split("\\.\\[|\\]\\.")[0]).getAsJsonArray()
                                                        .get(Integer.parseInt(key.split("\\.\\[|\\]\\.")[1])).getAsJsonObject()
                                                        .get(key.split("\\.\\[|\\]\\.")[2]).isJsonNull(), key + " is not null! - FAIL!");
                                            } else {
                                                assertEquals(dataObjectData.get(key.split("\\.\\[|\\]\\.")[0]).getAsJsonArray()
                                                                .get(Integer.parseInt(key.split("\\.\\[|\\]\\.")[1])).getAsJsonObject()
                                                                .get(key.split("\\.\\[|\\]\\.")[2]).getAsString(),
                                                        String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))), key + " mismatch! - FAIL!");
                                            }
                                            break;
                                    }
                                }
                        }
                    } catch (Exception e) {
                        System.out.println("Failed while testing " + key);
                        System.out.println(e.getMessage());
                        failedKeys.add(key);
                    }
                }
                assertEquals(failedKeys.size(), 0, "Found missing fields in event payload! - FAIL!\n" + failedKeys.toString());
                break;
            }
        }

        // verify that consumerId found in events list
        if (data.containsKey("consumerFound")) {
            assertEquals(Boolean.toString(consumerFoundInEventsList), data.get("consumerFound"), "consumerId " +
                    consumerId + " is NOT found in events list " + enrollmentEvent + " ! - FAIL!");
        } else {
            assertTrue(consumerFoundInEventsList, "consumerId " +
                    consumerId + " is not found in events list " + enrollmentEvent + "!");
        }

    }

    public String getPlanCodeBasedOnUISelection() {
        String subProgramTypeCode = APIConsumerPopulationDmnController.subProgramTypeCode.get();
        switch (CRMEnrollmentUpdateStepDef.selectedPlanName.get()) {
            case "AMERIGROUP COMMUNITY CARE":
                return "84";
            case "CARESOURCE GEORGIA":
                return "87";
            case "PEACH STATE":
                return "85";
            case "WELLCARE":
                return "86";
            case "CARESOURCE HIP":
                return "755726440";
            case "ANTHEM HIP":
                return "455701400";
            case "MANAGED HEALTH SERVICES HIP":
                return "355787430";
            case "MDWISE AMERICHOICE HIP":
                return "555763410";
            case "ICHIA ESP HIP":
                return "155723420";
            case "UNITED HEALTHCARE HCC":
                return "699842000";
            case "MANAGED HEALTH SERVICES HCC":
                return "399243310";
            case "ANTHEM HCC":
                return "499254630";
            case "MDWISE HCC":
                return "599347220";
            case "MDWISE HH":
                return "500307680";
            case "MANAGED HEALTH SERVICES":
                return "300119960";
            case "CARESOURCE":
                return "700410350";
            case "ANTHEM":
                return "400752220";
            case "AMERIHEALTH CARITAS DC":
                if (subProgramTypeCode.equals("DCHF") || subProgramTypeCode.equals("ImmigrantChildren"))
                    return "081080400";
                else if (subProgramTypeCode.equals("Alliance"))
                    return "087358900";
                break;
            case "CAREFIRST COMMUNITY HEALTH PLAN DC":
                if (subProgramTypeCode.equals("DCHF") || subProgramTypeCode.equals("ImmigrantChildren"))
                    return"055558200";
                else if (subProgramTypeCode.equals("Alliance"))
                    return "077573200";
                break;
            case "MEDSTAR FAMILY CHOICE":
                if (subProgramTypeCode.equals("DCHF") || subProgramTypeCode.equals("ImmigrantChildren"))
                    return "044733300";
                else if (subProgramTypeCode.equals("Alliance"))
                    return "078222800";
                break;
        }
        return "Not found! Please update plans based on your tenant Plans Configuration file";
    }

    @Then("I verify {string} events are generated in DPBI with status {string}")
    public void verifyBusinessEventsAreGeneratedInDpbi(String enrollmentEvent, String status) {
        JsonObject event = new JsonObject();
        event.addProperty("correlationId", APIEnrollmentController.traceIdTOCheckEvents.get());
        event.addProperty("eventName", enrollmentEvent);
        requestParamsForEvent.set(event);
        System.out.println(requestParamsForEvent.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");


        for (int i = 0; i < eventsContent.size(); i++) {
            JsonObject dataObjectData = null;
            HashMap eventsData = (HashMap) eventsContent.get(i);

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            if (enrollmentEvent.equals("ENROLLMENT_UPDATE_EVENT")) {
                dataObjectData = payLoadData.getAsJsonObject("dataObject");
            }

            if (dataObjectData.get("consumerId").getAsString().equalsIgnoreCase(APIConsumerPopulationDmnController.consumerId.get())) {
                System.out.println(dataObjectData.toString());
                assertEquals(dataObjectData.get("consumerId").getAsString(), APIConsumerPopulationDmnController.consumerId.get());
                assertEquals(dataObjectData.get("status").getAsString(), status);
                break;
            }
        }
    }

    @Then("I sending api call for business events with data")
    public void iSendingApiCallForBusinessEventsWithData(Map<String, String> data) {
        JsonObject event = new JsonObject();
        event.addProperty("correlationId", APIEnrollmentController.traceIdTOCheckEvents.get());
        event.addProperty("eventName", data.get("eventName"));
        requestParamsForEvent.set(event);
        System.out.println(requestParamsForEvent.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");


        for (int i = 0; i < eventsContent.size(); i++) {
            JsonObject dataObjectData = null;
            HashMap eventsData = (HashMap) eventsContent.get(i);

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            dataObjectData = payLoadData.getAsJsonObject("dataObject");

            if (data.get("eventName").equals("NEW_ENROLLMENT_REJECT_RESPONSE")) {
                if (dataObjectData.get("consumerId").getAsString().equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("ND5.consumerId").substring(0, 5))) {
                    consumerExist.set(true);

                    // this hardcoded step not applicable for future use. Hase to be reworked.

                    assertEquals(dataObjectData.get("consumerId").getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("ND5.consumerId").substring(0, 5));
                    assertEquals(dataObjectData.get("bussinessEventName").getAsString(), "New Enrollment Reject Response");
                    assertEquals(dataObjectData.get("channel").getAsString(), "SYSTEM_INTEGRATION");
                    assertTrue(dataObjectData.get("createdBy").getAsString().chars().allMatch(Character::isLetterOrDigit));
                    assertFalse(dataObjectData.get("processDate").getAsString().isEmpty());
                    assertTrue(dataObjectData.get("externalCaseId").getAsString().chars().allMatch(Character::isLetterOrDigit));
                    assertTrue(dataObjectData.get("externalConsumerId").getAsString().chars().allMatch(Character::isLetterOrDigit));
                    assertTrue(dataObjectData.get("consumerId").getAsString().chars().allMatch(Character::isLetterOrDigit));
                    assertEquals(dataObjectData.get("bussinessEventName").getAsString(), "New Enrollment Reject Response");
                    assertEquals(dataObjectData.get("consumerName").getAsString(), acp.get().consumerFirstName.get() + " " + acp.get().consumerLastName);
                    assertEquals(dataObjectData.get("enrollmentStartDate").getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("ST5.enrollment.startDate"));
                    assertTrue(dataObjectData.get("enrollmentEndDate").isJsonNull());
                    assertEquals(dataObjectData.get("planCode").getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("ST5.enrollment.planCode"));
                    assertEquals(dataObjectData.get("rejectionReason").getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("ST5.enrollment.rejectionReason"));
                    assertEquals(dataObjectData.get("enrollmentType").getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("ST5.enrollment.enrollmentType"));
                    assertEquals(dataObjectData.get("selectionStatus").getAsString(), "REJECTED");
                    assertEquals(dataObjectData.get("enrollmentType").getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("ST5.enrollment.enrollmentType"));
                    assertEquals(dataObjectData.get("consumerPopulation").getAsString(), "NEWBORN");
                    assertTrue(dataObjectData.get("benefitStatus").getAsString().chars().allMatch(Character::isLetterOrDigit));
                    assertEquals(dataObjectData.get("consumerDateOfBirth").getAsString(), acp.get().consumerDateOfBirth.get());

                    break;
                }
            }
        }
        if (consumerExist.get() == false) {
            throw new CucumberException("Business event not found");
        }
    }

    @Then("I verify out of state address record for consumerId {string} is not empty by API")
    public void I_verify_out_of_state_address_record_is_not_empty_by_API(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = APIConsumerPopulationDmnController.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }

        outOfStateCheckEndPoint.set(outOfStateCheckEndPoint.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiEnrollment.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(outOfStateCheckEndPoint.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").size() > 0,
                "Out Of State record for consumerId " + consumerId + " is NOT generated!");
    }

    @Then("I verify other enrollment segment for coma separated consumerIds {string} with data")
    public void I_verify_other_enrollment_segment_for_coma_separated_consumerIds_with_data(String comaSeparatedConsumerIds, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        String otherEnrollmentSegmentsEndPoint = otherEnrollmentSegments.get();
        if (comaSeparatedConsumerIds.isEmpty()) {
            otherEnrollmentSegmentsEndPoint = otherEnrollmentSegmentsEndPoint + APIConsumerPopulationDmnController.consumerId.get();
        } else {
            int i = 0;
            for (String each : comaSeparatedConsumerIds.split(",")) {
                if (i == 0) {
                    otherEnrollmentSegmentsEndPoint = otherEnrollmentSegmentsEndPoint + String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(each)).replace(".0", "");
                } else {
                    otherEnrollmentSegmentsEndPoint = otherEnrollmentSegmentsEndPoint + "&consumerIds=" + String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(each)).replace(".0", "");
                }
                i++;
            }
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(otherEnrollmentSegmentsEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        JsonArray otherEnrollmentSegments = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();

        if (data.containsKey("[0].empty")) {
            assertEquals(otherEnrollmentSegments.size(), 0, "other enrollment segment is NOT empty! - FAIL!");
            System.out.println("other enrollment segment is empty! - PASS!");
            return;
        }

        for (String key : data.keySet()) {
            int i = Integer.parseInt(key.substring(1, 2));
            String field = key.split("\\.")[1];
            switch (field) {
                case "otherEnrollmentSegmentsId":
                    System.out.println("otherEnrollmentSegmentsId is " + otherEnrollmentSegments.get(i).getAsJsonObject().get("otherEnrollmentSegmentsId").toString().replaceAll("\"", ""));
                    assertFalse(otherEnrollmentSegments.get(i).getAsJsonObject().get("otherEnrollmentSegmentsId").isJsonNull());
                    break;
                case "consumerId":
                    assertEquals(otherEnrollmentSegments.get(i).getAsJsonObject().get(field).getAsString(),
                            String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))).replace(".0", ""), key + " mismatch!");
                    break;
                case "startDate":
                case "genericFieldDate1":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(otherEnrollmentSegments.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(otherEnrollmentSegments.get(i).getAsJsonObject().get(field).getAsString(), apitdu.get().getStartDate(data.get(key)), key + " mismatch!");
                    }
                    break;
                case "createdOn":
                case "updatedOn":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(otherEnrollmentSegments.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(otherEnrollmentSegments.get(i).getAsJsonObject().get(field).getAsString().substring(0, 10), apitdu.get().getStartDate(data.get(key)), key + " mismatch!");
                    }
                    break;
                case "endDate":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(otherEnrollmentSegments.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(otherEnrollmentSegments.get(i).getAsJsonObject().get("endDate").getAsString(), apitdu.get().getEndDate(data.get(key)), key + " mismatch!");
                    }
                    break;
                default:
                    // all simple field validations here
                    System.out.println("testing " + key);
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(otherEnrollmentSegments.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(otherEnrollmentSegments.get(i).getAsJsonObject().get(field).getAsString(), data.get(key), key + " mismatch!");
                    }
            }
        }
    }

    @Then("I will get contactId by consumerId {string} and save it with name {string}")
    public void I_will_get_contactId_by_consumerId_and_save_it_with_name(String consumerId, String name) {
        consumerId = consumerId.equals("")
                ? APIConsumerPopulationDmnController.consumerId.get()
                : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId)).replace(".0", "");
        String json = "{\"linkRefType\":\"consumer\",\"linkRefId\":" + consumerId + ",\"contactRecordId\":0}";
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiInitContactRecord.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactRecordEndpoint.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostRequestSaveResponseByName(name, json);
    }

    @Then("I will verify link events are generated with data")
    public void verifyLinkEvents(Map<String, String> data) {
        String enrollmentEvent = data.get("eventName");
        JsonObject event = new JsonObject();
        if (data.containsKey("correlationId")) {
            System.out.println(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("correlationId")));
            event.addProperty("correlationId", data.get("correlationId").equals("null") ? null : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("correlationId"))).replace(".0", ""));
        } else {
            event.addProperty("correlationId", APIEnrollmentController.traceIdTOCheckEvents.get());
        }
        if(data.containsKey("module")){
            event.addProperty("module", data.get("module"));
        }
        event.addProperty("eventName", enrollmentEvent);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        System.out.println(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId")));
        System.out.println(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("taskId")));

        for (int i = 0; i < eventsContent.size(); i++) {
            JsonObject dataObjectData = null;
            HashMap eventsData = (HashMap) eventsContent.get(i);

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            if (payLoadData.get("dataObject").isJsonObject()) {
                dataObjectData = payLoadData.getAsJsonObject("dataObject");
            } else if (payLoadData.get("dataObject").isJsonArray()) {
                dataObjectData = payLoadData.getAsJsonArray("dataObject").get(0).getAsJsonObject();
            }
            JsonObject externalLinkPayload = dataObjectData.getAsJsonObject("externalLinkPayload");

            if (externalLinkPayload.get("internalRefType").getAsString().equals("CONSUMER")
                    && externalLinkPayload.get("externalRefType").getAsString().equals("TASK")) {
                assertEquals(externalLinkPayload.get("internalId").getAsInt() + "", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId")), "internalId is miss match");
                assertEquals(externalLinkPayload.get("externalId").getAsInt() + "", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("taskId")).toString().replace(".0", ""), "internalId is miss match");
            } else if (externalLinkPayload.get("internalRefType").getAsString().equals("TASK")
                    && externalLinkPayload.get("externalRefType").getAsString().equals("CONSUMER")) {
                assertEquals(externalLinkPayload.get("externalId").getAsInt() + "", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId")), "internalId is miss match");
                assertEquals(externalLinkPayload.get("internalId").getAsInt() + "", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("taskId")).toString().replace(".0", ""), "internalId is miss match");
            }
        }
    }
}
