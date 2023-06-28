package com.maersk.crm.api_step_definitions;

import com.google.gson.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;


public class APICreateCaseController extends CRMUtilities implements ApiBase {

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String baseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String searchCaseEndPoint = "app/crm/case/consumers";
    private String caseConsumerAPI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String eventsAPI = ConfigurationReader.getProperty("apiEventsURI");
    private String createCaseWithConsumers = "app/crm/caseloader";
    private String createConsumer = "/app/crm/consumer";
    private String createCaseWithCaseCheck = "app/crm/cases";
    private String getConsumerListByUser = "app/crm/case/consumers";
    private String getEvents = "app/crm/events?size=1&page=0";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsExistingConsumer = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsWithoutConsumer = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsCaseConsumer = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsCaseCheck = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsEvents = ThreadLocal.withInitial(JsonObject::new);

    private final ThreadLocal<String> subscriberName = ThreadLocal.withInitial(() -> "DPBI");
    public final ThreadLocal<String> eventId = ThreadLocal.withInitial(() -> "");
    private final ThreadLocal<String> subscriberId = ThreadLocal.withInitial(() -> "");
    private final ThreadLocal<JSONArray> subscriberList = ThreadLocal.withInitial(JSONArray::new);
    public static final ThreadLocal<String> correlationId = ThreadLocal.withInitial(() -> "");

    final ThreadLocal<String> apiSearchConsumerFirstName = ThreadLocal.withInitial(() -> "");
    final ThreadLocal<String> apiSearchConsumerLastName = ThreadLocal.withInitial(() -> "");
    static final ThreadLocal<String> apiSearchConsumerSSN = ThreadLocal.withInitial(() -> "");
    final ThreadLocal<String> createdCaseId = ThreadLocal.withInitial(() -> "");
    final ThreadLocal<JSONObject> payloadObject = new ThreadLocal<>();
    final ThreadLocal<Gson> gson = ThreadLocal.withInitial(Gson::new);

    @Given("I initiated Case Loader API for Create New Case")
    public void initiatedCasLoaderAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
    }

    @When("I run Case Loader API for Create New Case and Customer")
    public void runCaseLoaderAPI() {
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest());
        System.out.println("request params");
        System.out.println(requestParams);
        correlationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response string");
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I will create a new case for case loader case creation")
    public void createNewCaseForCaseLoaderCaseCreation() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithCaseCheck);

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            String caseIdentificationNumber = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNumber));
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println("requestParamsCaseCheck1 " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("caseList"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"));

        requestParamsWithoutConsumer.set(generateCreateConsumerRequestWithoutConsumer());
        System.out.println(requestParamsWithoutConsumer);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsWithoutConsumer.get());

        System.out.println("requestParamsWithoutConsumer" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray caseLoaderWithoutConsumer = (JsonArray) requestParamsWithoutConsumer.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoaderWithoutConsumer.size(); i++) {
            JsonElement consumersData = caseLoaderWithoutConsumer.get(i).getAsJsonObject().get("case");
            String caseIdentificationNumber = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNumber));
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println("requestParamsCaseCheck2 " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("caseList"));
        //assertNotNull(apiCaseConsumer.jsonPathEvaluator.getJsonObject("caseList.content"));
        JsonArray empty = new JsonArray();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"), empty);
    }

    @Given("I initiated Case Loader API for Create New Case and Existing Consumer")
    public void initiatedCasLoaderAPIForExistingConsumer() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
    }

    @When("I run Case Loader API for Create New Case and Existing Consumer")
    public void runCaseLoaderAPIForExistingConsumer() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumer);

        requestParamsExistingConsumer.set(generateCreateConsumerRequest());
        System.out.println(requestParamsExistingConsumer);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsExistingConsumer.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        requestParams.set(generateCreateConsumerRequestForExistingConsumer(requestParamsExistingConsumer.get()));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("The consumer will automatically be linked to the provided Case")
    public void consumerWillLinkToNewCase() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithCaseCheck);

        String consumerSSN = null;
        String caseIdentificationNo = null;

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            caseIdentificationNo = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNo));
            JsonArray consumer = (JsonArray) consumersData.getAsJsonObject().get("consumers");
            for (int j = 0; j < consumer.size(); j++) {
                consumerSSN = consumer.get(i).getAsJsonObject().get("consumerSSN").getAsString();
                requestParamsCaseConsumer.set(generateConsumerCheck(consumerSSN));
            }
        }

        //Case Check Api
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("caseList"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"));

        //Consumer Api
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerListByUser);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseConsumer.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("object"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result"));

        List resultList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");
        boolean caseRecordFound = false;
        boolean consumerRecordFound = false;

        for (int i = 0; i < resultList.size(); i++) {
            HashMap resultData = (HashMap) resultList.get(i);

            HashMap casesMap = (HashMap) resultData.get("cases");
            List caseIdentificationNoList = (List) casesMap.get("caseIdentificationNumber");
            for (int j = 0; j < caseIdentificationNoList.size(); j++) {
                HashMap caseData = (HashMap) caseIdentificationNoList.get(i);
                if (caseData.get("externalCaseId").equals(caseIdentificationNo)) {
                    caseRecordFound = true;
                    break;
                }
            }

            List consumerList = (List) resultData.get("consumers");
            System.out.println("consumerList " + consumerList);
            for (int j = 0; j < consumerList.size(); j++) {
                HashMap consumerData = (HashMap) consumerList.get(i);
                if (consumerData.get("consumerSSN").equals(consumerSSN)) {
                    consumerRecordFound = true;
                    break;
                }
            }
        }
        assertTrue(caseRecordFound);
        assertTrue(consumerRecordFound);
    }

    @Then("The Consumer will have a Consumer Role of Primary Individual")
    public void consumerWillHaveConsumerRoleOfPrimaryIndividual() {
        //Consumer Api
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerListByUser);

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            JsonArray consumer = (JsonArray) consumersData.getAsJsonObject().get("consumers");
            for (int j = 0; j < consumer.size(); j++) {
                String consumerSSN = consumer.get(i).getAsJsonObject().get("consumerSSN").getAsString();
                requestParamsCaseConsumer.set(generateConsumerCheck(consumerSSN));
            }
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseConsumer.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("object"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result"));

        List resultList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");

        for (int i = 0; i < resultList.size(); i++) {
            HashMap resultData = (HashMap) resultList.get(i);
            List consumerList = (List) resultData.get("consumers");
            for (int j = 0; j < consumerList.size(); j++) {
                HashMap consumerData = (HashMap) consumerList.get(i);
                List caseConsumersList = (List) consumerData.get("caseConsumers");
                for (int k = 0; k < caseConsumersList.size(); k++) {
                    HashMap caseConsumers = (HashMap) caseConsumersList.get(i);
                    assertEquals(caseConsumers.get("consumerRole"), "Primary Individual");
                }
            }
        }
    }

    @Then("The user should find the newly created case with associated consumer profiles and case details")
    public void userShouldFindTheNewlyCreatedCase() {
        //Case Check Api
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithCaseCheck);

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            String caseIdentificationNo = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNo));
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("caseList"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"));
    }

    @Then("I will publish an {string} event for DPBI to consume for reporting")
    public void publishAnEventForDPBIToConsume(String caseMemberSaveEvent) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri("https://mars-event-api-qa.apps.non-prod.pcf-maersk.com");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents + "&sort=eventId,desc");

              /* JsonArray caseLoader = (JsonArray) requestParams.get("caseLoaderRequest");
                for (int i = 0; i < caseLoader.size(); i++) {
                    JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
                    JsonArray consumer = (JsonArray) consumersData.getAsJsonObject().get("consumers");
                    for (int j = 0; j < consumer.size(); j++) {
                        String correlationId = consumer.get(i).getAsJsonObject().get("correlationId").getAsString();
                        apitdu.getJsonFromFile("events/searchEvents.json");
                        apitdu.jsonElement.getAsJsonObject().addProperty("correlationId", correlationId);
                        requestParamsEvents = apitdu.jsonElement.getAsJsonObject();
                    }
                }*/
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("events/searchEvents.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", "");
//        if(!correlationId.isEmpty())
//            apitdu.jsonElement.getAsJsonObject().addProperty("correlationId", correlationId);
//        else
//            apitdu.jsonElement.getAsJsonObject().addProperty("correlationId", "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("eventName", caseMemberSaveEvent);
        // apitdu.jsonElement.getAsJsonObject().addProperty("module", "consumer");
        requestParamsEvents.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsEvents.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("eventsList"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content"));

        List eventsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        String checkFormat = "2019-11-12T12:14:56.000+0000";
        boolean val = checkFormat.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}+\\d{4}");
        System.out.println(val);

        //JsonArray json = api.apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        //JSONObject eventData = new JSONObject(json.get(json.size() - 1).toString());
        synchronized (payloadObject) {
            payloadObject.set(new JSONObject(new JSONObject(json.get(json.size() - 1).toString()).get("payload").toString()));
        }
        Iterator<String> payloadKeys = payloadObject.get().keys();
        System.out.println("Payload:" + payloadObject.get());

        String correlationIdValue = new JSONObject(json.get(json.size() - 1).toString()).get("correlationId").toString();
        eventId.set(new JSONObject(json.get(json.size() - 1).toString()).get("eventId").toString());
        String action = payloadObject.get().getString("action");
        String recordType = payloadObject.get().getString("recordType");
        String eventCreatedOn = payloadObject.get().getString("eventCreatedOn");
        JSONObject dataObject = payloadObject.get().getJSONObject("dataObject");
        Iterator<String> dataObjectKeys = dataObject.keys();

        //validation of payload
        assertTrue(payloadObject.get().has("projectId"));
        assertTrue(payloadObject.get().has("projectName"));
        assertTrue(payloadObject.get().has("action"));
        assertTrue(payloadObject.get().has("recordType"));
        assertTrue(payloadObject.get().has("eventCreatedOn"));
        assertTrue(payloadObject.get().has("dataObject"));

        //validation of date format
        assertTrue(verifyDatetimeFormat(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate")));
        assertTrue(verifyDatetimeFormat(payloadObject.get().getJSONObject("dataObject").getString("createdOn")));
        assertTrue(verifyDatetimeFormat(payloadObject.get().get("eventCreatedOn").toString()));


        System.out.println((eventsList));

        boolean recordFound = false;

        for (int i = 0; i < eventsList.size(); i++) {
            HashMap contentData = (HashMap) eventsList.get(i);

            if (contentData.get("eventName").equals(caseMemberSaveEvent)) {
                assertEquals(contentData.get("status"), "SUCCESS");

                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);

        // validateSubscriptionProcessCaseMemberSaveEvent(caseMemberSaveEvent);
    }

    @Then("I will record the date the records were created")
    public void recordTheDate() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents);

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            JsonArray consumer = (JsonArray) consumersData.getAsJsonObject().get("consumers");
            for (int j = 0; j < consumer.size(); j++) {
                String correlationId = consumer.get(i).getAsJsonObject().get("correlationId").getAsString();
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("events/searchEvents.json");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId);
                requestParamsEvents.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
            }
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsEvents.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("eventsList"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content"));

        List eventsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String createdOn = dateFormat.format(todayDate);

        for (int i = 0; i < eventsList.size(); i++) {
            HashMap contentData = (HashMap) eventsList.get(i);
            for (int j = 0; j < caseLoader.size(); j++) {
                JsonElement consumersData = caseLoader.get(j).getAsJsonObject().get("case");
                JsonArray consumer = (JsonArray) consumersData.getAsJsonObject().get("consumers");
                for (int k = 0; k < consumer.size(); k++) {
                    String createdBy = consumer.get(k).getAsJsonObject().get("createdBy").getAsString();
                    assertEquals(contentData.get("createdBy"), createdBy);
                    String createdOnResult = contentData.get("createdOn").toString();
                    assertEquals(createdOnResult.substring(0, createdOnResult.indexOf("T")), createdOn);
                }
            }
        }
    }

    public JsonObject generateConsumerCheck(String ssn) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public JsonObject generateCaseCheck(String caseIdentificationNo) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCaseCheck.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseIdentificationNumber").get(0).getAsJsonObject().addProperty("externalCaseId", caseIdentificationNo);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public JsonObject generateCreateConsumerRequestWithoutConsumer() {
        JsonObject request = new JsonObject();
        JsonObject caseLoaderRequest = new JsonObject();
        JsonArray caseList = new JsonArray();
        caseList.add(caseLoaderRequest);

        JsonObject CASE = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        String randomDate = dateFormat.format(todayDate);

        CASE.addProperty("caseIdentificationNumber", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
        CASE.addProperty("identificationNoType", "CHIP");
        CASE.addProperty("caseStatus", "Active");
        CASE.addProperty("caseType", "Member");
        CASE.addProperty("portfolioIdentificationNo", (String) null);
        CASE.addProperty("programName", "CRM");
        CASE.addProperty("subProgramName", "");
        CASE.addProperty("needTranslatorInd", "true");
        CASE.addProperty("jobId", "1");
        CASE.addProperty("correlationId", "1");

        caseLoaderRequest.add("case", CASE);

        JsonObject caseCommunicationPreferences = new JsonObject();
        JsonArray caseCommunicationPreferencesList = new JsonArray();
        caseCommunicationPreferencesList.add(caseCommunicationPreferences);

        caseCommunicationPreferences.addProperty("defaultInd", "boolean");
        caseCommunicationPreferences.addProperty("effectiveEndDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("effectiveStartDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("updatedBy", "string");
        caseCommunicationPreferences.addProperty("updatedOn", "2019-05-08T14:05:31.798Z");

        CASE.add("communicationPreferences", gson.get().toJsonTree(caseCommunicationPreferencesList).getAsJsonArray());

        request.add("caseLoaderRequest", gson.get().toJsonTree(caseList).getAsJsonArray());

        return request;

    }

    private JsonObject generateCreateConsumerRequestForExistingConsumer(JsonObject existingConsumer) {
        JsonObject request = new JsonObject();
        Gson gson = new GsonBuilder().create();
        JsonObject caseLoaderRequest = new JsonObject();
        JsonArray caseList = new JsonArray();
        caseList.add(caseLoaderRequest);

        JsonObject CASE = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        String randomDate = dateFormat.format(todayDate);

        CASE.addProperty("caseIdentificationNumber", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
        CASE.addProperty("identificationNoType", "CHIP");
        CASE.addProperty("caseStatus", "Active");
        CASE.addProperty("caseType", "Member");
        CASE.addProperty("portfolioIdentificationNo", (String) null);
        CASE.addProperty("programName", "CRM");
        CASE.addProperty("subProgramName", "");
        CASE.addProperty("needTranslatorInd", "true");
        CASE.addProperty("jobId", "1");
        CASE.addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        caseLoaderRequest.add("case", CASE);

        JsonObject consumers = existingConsumer;
        JsonArray consumersList = new JsonArray();
        consumersList.add(consumers);

        CASE.add("consumers", gson.toJsonTree(consumersList).getAsJsonArray());

        JsonObject caseCommunicationPreferences = new JsonObject();
        JsonArray caseCommunicationPreferencesList = new JsonArray();
        caseCommunicationPreferencesList.add(caseCommunicationPreferences);

        caseCommunicationPreferences.addProperty("defaultInd", "boolean");
        caseCommunicationPreferences.addProperty("effectiveEndDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("effectiveStartDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("updatedBy", "string");
        caseCommunicationPreferences.addProperty("updatedOn", "2019-05-08T14:05:31.798Z");

        CASE.add("communicationPreferences", gson.toJsonTree(caseCommunicationPreferencesList).getAsJsonArray());

        request.add("caseLoaderRequest", gson.toJsonTree(caseList).getAsJsonArray());

        return request;
    }

    private JsonObject generateCreateConsumerRequest() {
        JsonObject consumers = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date todayDate = new Date();
        String randomDate = dateFormat.format(todayDate);
        String currentDate = dateForm.format(todayDate);

        consumers.addProperty("consumerIdType", "MEDICAID");
        consumers.addProperty("consumerIdentificationNo", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(7).randomNumber);
        consumers.addProperty("consumerDateOfBirth", randomDate);
        consumers.addProperty("consumerFirstName", "fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(4).randomNumber));
        consumers.addProperty("consumerLastName", "ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(4).randomNumber));
        consumers.addProperty("consumerMiddleName", "");
        consumers.addProperty("consumerRole", "Primary Individual");
        consumers.addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        consumers.addProperty("consumerPrefix", "");
        consumers.addProperty("consumerSuffix", "");
        consumers.addProperty("consumerType", "Consumer");
        consumers.addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
        consumers.addProperty("createdBy", "464");
        consumers.addProperty("createdOn", currentDate);
        consumers.addProperty("dateOfDeath", "");
        consumers.addProperty("dateOfDeathNotifiedBy", "");
        consumers.addProperty("dateOfDeathNotifiedDate", "");
        consumers.addProperty("dateOfSsnValidation", currentDate);
        consumers.addProperty("effectiveEndDate", "");
        consumers.addProperty("effectiveStartDate", currentDate);
        consumers.addProperty("ethnicityCode", "");
        consumers.addProperty("aiNa", "");
        consumers.addProperty("genderCode", "");
        consumers.addProperty("mergeReason", "");
        consumers.addProperty("mergedConsumerId", "");
        consumers.addProperty("notBornInd", "false");
        consumers.addProperty("preferredLanguage", "");
        //consumers.addProperty("raceCode", "null");
        consumers.addProperty("relationShip", "");
        consumers.addProperty("ssnValidationAgency", "");
        consumers.addProperty("ssnValidationCode", "");
        consumers.addProperty("uiid", "");
        consumers.addProperty("updatedBy", "465");
        consumers.addProperty("updatedOn", currentDate);
        consumers.addProperty("usResidentStatusCode", "");
        consumers.addProperty("pregnancyInd", "");
        consumers.addProperty("pregnancyDueDate", "");

        JsonObject contacts = new JsonObject();

        JsonObject address = new JsonObject();

        address.addProperty("addrAttn", "");
        address.addProperty("addrHouseCode", "");
        address.addProperty("addressCity", "dallas");
        address.addProperty("addressCounty", "dallas");
        address.addProperty("addressState", "tx");
        address.addProperty("addressStreet1", "4321 test ln");
        address.addProperty("addressStreet2", "");
        address.addProperty("addressType", "Mailing");
        address.addProperty("addressVerfied", "false");
        address.addProperty("addressZip", "12345");
        address.addProperty("addressZipFour", "4345");
        address.addProperty("createdBy", "");

        JsonObject email = new JsonObject();
        email.addProperty("createdBy", "");
        email.addProperty("emailAddress", "test1@test.com");
        email.addProperty("emailType", "Primary");
        email.addProperty("updatedBy", "");
        email.addProperty("updatedOn", "");

        JsonObject phone = new JsonObject();
        phone.addProperty("createdBy", "");
        phone.addProperty("phoneNumber", "6789098767");
        phone.addProperty("phoneType", "");
        phone.addProperty("smsEnabledInd", "false");
        phone.addProperty("updatedBy", "");
        phone.addProperty("updatedOn", "");

        contacts.add("address", address);
        contacts.add("email", email);
        contacts.add("phone", phone);

        consumers.add("contacts", contacts);

        JsonObject communicationPreferences = new JsonObject();
        JsonArray communicationPreferencesList = new JsonArray();
        communicationPreferencesList.add(communicationPreferences);

        communicationPreferences.addProperty("defaultInd", "true");
        communicationPreferences.addProperty("effectiveEndDate", "2019-05-08T14:05:31.798Z");
        communicationPreferences.addProperty("effectiveStartDate", "2019-05-08T14:05:31.798Z");
        communicationPreferences.addProperty("updatedBy", "string");
        communicationPreferences.addProperty("updatedOn", "2019-05-08T14:05:31.798Z");

        consumers.add("communicationPreferences", gson.get().toJsonTree(communicationPreferencesList).getAsJsonArray());

        JsonObject consumerConsent = new JsonObject();
        JsonArray consumerConsentList = new JsonArray();
        consumerConsentList.add(consumerConsent);

        consumerConsent.addProperty("howConsentProvided", "Email");
        consumerConsent.addProperty("consentType", "Phone");
        consumerConsent.addProperty("noResponseInd", "false");
        consumerConsent.addProperty("effectiveStartDate", "2019-05-08T14:05:31.798Z");
        consumerConsent.addProperty("effectiveEndDate", "2019-05-08T14:05:31.798Z");
        consumerConsent.addProperty("createdBy", "464");
        consumerConsent.addProperty("createdOn", "2019-05-08T14:05:31.798Z");
        consumerConsent.addProperty("updatedBy", "464");
        consumerConsent.addProperty("updatedOn", "2019-05-08T14:05:31.798Z");

        consumers.add("consumerConsent", gson.get().toJsonTree(consumerConsentList).getAsJsonArray());

        return consumers;
    }

    private void validateSubscriptionProcessCaseMemberSaveEvent(String caseMemberSaveEvent) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName.get());

        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        subscriberId.set(new JSONObject(json.get(0).toString()).get("subscriberId").toString());
        subscriberList.set(new JSONArray(new JSONObject(json.get(0).toString()).get("subscriberList").toString()));

        boolean flag = false;
        for (int i = 0; i < subscriberList.get().length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get().get(i).toString());
            if (temp.get("eventName").toString().equals(caseMemberSaveEvent)) {
                Assert.assertEquals(temp.get("subscriberId").toString(), subscriberId, "Subscriber Id is wrong");
                Assert.assertEquals(temp.get("eventName").toString(), caseMemberSaveEvent, "Event Name is wrong");
                Assert.assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag, caseMemberSaveEvent + " is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsAPI);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId + "/" + eventId;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray subscriberJson = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(subscriberJson.get(0).toString());
        Assert.assertEquals(temp.getString("eventName"), caseMemberSaveEvent);
        Assert.assertEquals(temp.getInt("eventId") + "", eventId);
        Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId);
    }

    private boolean verifyDatetimeFormat(String datetimeField) {
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

    @When("I run Case Loader API for Create New Case and Customer with below details:")
    public void i_run_Case_Loader_API_for_Create_New_Case_and_Customer_with_below_details(DataTable caseMember) {
        String consumerType = "";
        String consumerDateOfBirth = "";
        String genderCode = "";
        String relationShip = "";


        for (Map<String, String> data : caseMember.asMaps(String.class, String.class)) {
            consumerType = data.get("consumerType").toString();
            consumerDateOfBirth = data.get("consumerDateOfBirth").toString();
            genderCode = data.get("GenderCode").toString();
            relationShip = data.get("relationShip").toString();

            if (data.containsKey("consumerFirstName")) {
                String firstName = data.get("consumerFirstName").toString();
                if (firstName.isEmpty() || firstName.equals("{random}")) {
                    synchronized (apiSearchConsumerFirstName) {
                        apiSearchConsumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
                        System.out.println("consumerFirstName " + apiSearchConsumerFirstName.get());

                    }

                } else {
                    apiSearchConsumerFirstName.set(data.get("consumerFirstName").toString());
                }
            }
            if (data.containsKey("consumerLastName")) {
                String lastName = data.get("consumerLastName").toString();
                if (lastName.isEmpty() || lastName.equals("{random}")) {
                    synchronized (apiSearchConsumerLastName) {
                        apiSearchConsumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
                        System.out.println("consumerLastName " + apiSearchConsumerLastName.get());

                    }

                } else {
                    apiSearchConsumerLastName.set(data.get("consumerLastName").toString());
                }

            }
            if (data.containsKey("consumerSSN")) {
                String ssn = data.get("consumerSSN").toString();
                if (ssn.isEmpty() || ssn.equals("{random}")) {
                    synchronized (apiSearchConsumerSSN) {
                        apiSearchConsumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
                        System.out.println("consumerSSN " + apiSearchConsumerSSN.get());

                    }

                } else {
                    apiSearchConsumerSSN.set(data.get("consumerSSN").toString());
                }
            }
        }

        //requestParams = caseLoaderRequest.generateCaseLoaderRequest();
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequestWithSpecifiedParameters(consumerType, apiSearchConsumerFirstName.get(), apiSearchConsumerLastName.get(), apiSearchConsumerSSN.get(), consumerDateOfBirth, genderCode, relationShip));
        System.out.println("request params");
        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response string");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I can Search Case by {string}, {string}, {string}, {string}")
    public void i_can_Search_Case_by(String consumerFirstName, String consumerLastName, String consumerSSN, String consumerIdentificationNo4) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        search_case_by(requestParams.get(), consumerFirstName, "");
        search_case_by(requestParams.get(), consumerLastName, "");
        search_case_by(requestParams.get(), consumerSSN, "");
        search_case_by(requestParams.get(), consumerIdentificationNo4, "");

    }

    private void search_case_by(JsonObject requestParams, String item, String value) {
        // -- Make consumer search by specific property. --
        switch (item) {
            case "consumerFirstName":
                if (value.isEmpty())
                    value = apiSearchConsumerFirstName.get();
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerFirstName", value);
                break;
            case "consumerIdentificationNo":
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerIdentificationNo", value);
                break;
            case "consumerLastName":
                if (value.isEmpty())
                    value = apiSearchConsumerLastName.get();
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerLastName", value);
                break;
            case "consumerMiddleName":
                requestParams.addProperty("consumerMiddleName", value);
                break;
            case "consumerSSN":
                if (value.isEmpty())
                    value = apiSearchConsumerSSN.get();
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerSSN", value);
                break;

        }
    }

    @Then("I initiated Search Case API for new CaseLoader")
    public void i_initiated_Search_Case_API_for_new_CaseLoader() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchCaseEndPoint);
    }

    @Then("I run the case search API for new CaseLoader")
    public void i_run_the_case_search_API_for_new_CaseLoader() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("api response after search");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        org.junit.Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I can verify on case search API response will be {string} for new CaseLoader")
    public void i_can_verify_on_case_search_API_response_will_be_for_new_CaseLoader(String success) {
        verify_case_search_response(success);
    }

    private void verify_case_search_response(String success) {
        Object caseList;
        int caseListCount;
        try {
            caseList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result");
            System.out.println(caseList);
            caseListCount = ((List) caseList).size();
        } catch (NullPointerException e) {
            caseList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.cases");
            System.out.println(caseList);
            caseListCount = ((List) caseList).size();
        }
        System.out.println(caseListCount);
        System.out.println(success);
        if (caseListCount > 0) {
            assertEquals(Boolean.TRUE, Boolean.valueOf(success));
        } else {
            assertEquals(Boolean.FALSE, Boolean.valueOf(success));
            // System.out.println(api_step_definitions.responseString);
        }
    }


    @Then("I verify case id created using new Case Loader")
    public void i_verify_case_id_created_using_new_Case_Loader() {
        System.out.println("respone " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println("caseList Content" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"));
        HashMap<String, Object> caselist = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList");
        System.out.println("1");
        HashMap<String, Object> content = (HashMap<String, Object>) ((ArrayList) caselist.get("content")).get(0);
        System.out.println("2");
        createdCaseId.set(content.get("caseId").toString());
        System.out.println("Case Id created is:" + createdCaseId);
        //assertTrue(createdCaseId!=null);
        assertTrue(!StringUtils.isEmpty(createdCaseId.get()));
    }

    @Then("I will create a new case for case loader case creation without consumers")
    public void i_will_create_a_new_case_for_case_loader_case_creation_without_consumers() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithCaseCheck);

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            String caseIdentificationNo = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNo));
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println("requestParamsCaseCheck1 " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("caseList"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"));
    }

    @Then("I will verify case consumer aka case member was successfully created from CaseLoader")
    public void i_will_verify_case_consumer_aka_case_member_was_successfully_created_from_CaseLoader() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithCaseCheck);


        String consumerSSN = null;
        String caseIdentificationNo = null;

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderRequest");
        System.out.println("caseLoader last step" + caseLoader);
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            System.out.println("consumersData " + consumersData);
            caseIdentificationNo = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNo));
            JsonArray consumer = (JsonArray) consumersData.getAsJsonObject().get("consumers");
            System.out.println("consumer " + consumer);
            for (int j = 0; j < consumer.size(); j++) {
                consumerSSN = consumer.get(i).getAsJsonObject().get("consumerSSN").getAsString();
                System.out.println("consumerSSN " + consumerSSN);
                requestParamsCaseConsumer.set(generateConsumerCheck(consumerSSN));
            }
        }

        //Case Check Api
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println("apiCaseConsumer.responseString");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("caseList"));
        System.out.println("caseList.content");
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"));


        //Consumer Api
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerListByUser);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseConsumer.get());
        System.out.println("apiCaseConsumer.responseString");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("object"));
        System.out.println("object.result");
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result"));

        List resultList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");
        boolean caseRecordFound = false;
        boolean consumerRecordFound = false;

        for (int i = 0; i < resultList.size(); i++) {
            HashMap resultData = (HashMap) resultList.get(i);

            HashMap casesMap = (HashMap) resultData.get("cases");
            System.out.println("cases Map " + casesMap);
            List caseIdentificationNoList = (List) casesMap.get("caseIdentificationNumber");
            for (int j = 0; j < caseIdentificationNoList.size(); j++) {
                HashMap caseData = (HashMap) caseIdentificationNoList.get(i);
                if (caseData.get("externalCaseId").equals(caseIdentificationNo)) {
                    caseRecordFound = true;
                    break;
                }
            }

            List consumerList = (List) resultData.get("consumers");
            System.out.println("consumerList " + consumerList);
            for (int j = 0; j < consumerList.size(); j++) {
                HashMap consumerData = (HashMap) consumerList.get(i);
                if (consumerData.get("consumerSSN").equals(consumerSSN)) {
                    consumerRecordFound = true;
                    break;
                }
            }
        }
        assertTrue(caseRecordFound);
        assertTrue(consumerRecordFound);
    }

    @Then("I verify consumer_save_event payload contains UpdatedByOn CreatedByOn")
    public void iVerifyConsumer_save_eventPayloadContainsUpdatedByOnCreatedByOn() {
        String consumerIdentificationNumber = payloadObject.get().getJSONObject("dataObject").get("consumerIdentificationNumber").toString();
        ;
        String consumerConsent = payloadObject.get().getJSONObject("dataObject").get("consumerConsent").toString();
        ;
        String caseConsumer = payloadObject.get().getJSONObject("dataObject").get("caseConsumer").toString();
        ;
        String communicationPreferences = payloadObject.get().getJSONObject("dataObject").get("communicationPreferences").toString();
        String payLoadSvaeEvent = payloadObject.get().toString();
        assertTrue(payLoadSvaeEvent.contains("updatedBy"));
        assertTrue(payLoadSvaeEvent.contains("updatedOn"));
//        MUTED DUE TO PAYLOAD NO LONGER IN JSON FORMAT
//        assertTrue(consumerIdentificationNumber.contains("createdBy"));
//        assertTrue(consumerIdentificationNumber.contains("createdOn"));
//        assertTrue(communicationPreferences.contains("updatedBy"));
//        assertTrue(communicationPreferences.contains("updatedOn"));
//        assertTrue(communicationPreferences.contains("createdBy"));
//        assertTrue(communicationPreferences.contains("createdOn"));
//        assertTrue(consumerConsent.contains("updatedBy"));
//        assertTrue(consumerConsent.contains("updatedOn"));
//        assertTrue(consumerConsent.contains("createdBy"));
//        assertTrue(consumerConsent.contains("createdOn"));
//        assertTrue(caseConsumer.contains("updatedBy"));
//        assertTrue(caseConsumer.contains("updatedOn"));
//        assertTrue(caseConsumer.contains("createdBy"));
//        assertTrue(caseConsumer.contains("createdOn"));
        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), correlationId);
        //assertTrue(payLoadSvaeEvent.contains(correlationId), "Event not contains correlationId = "+correlationId);

    }


}

