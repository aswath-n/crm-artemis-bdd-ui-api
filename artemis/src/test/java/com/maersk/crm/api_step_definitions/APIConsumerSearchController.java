package com.maersk.crm.api_step_definitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.UiBase;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class APIConsumerSearchController extends CRMUtilities implements ApiBase, UiBase {

    private String consumerSearchBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String eventsBase = ConfigurationReader.getProperty("apiMarsEvent");
    private String caseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String caseCorrespondenceSearchAPI = "app/crm/casecorrespondence";
    private String getConsumerSearchListByUser = "app/crm/case/consumers";
    private String createCaseWithConsumers = "app/crm/caseloader";
    private String searchByCaseConsumer = "app/crm/case/consumers";
    private String updatePrimaryContact = "app/crm/cases/contacts/primary";
    private String getEvents = "?size=100&page=0&sort=eventId,desc";

    final ThreadLocal<Map<String, String>> caseLoaderParams = ThreadLocal.withInitial(()->new HashMap<>());
    public static final ThreadLocal<String> consumerSSNRandom = ThreadLocal.withInitial(()-> RandomStringUtils.random(9, false, true));
    public static final ThreadLocal<String> externalConsumerId = ThreadLocal.withInitial(()-> RandomStringUtils.random(10, true, true));
    public static final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(()->RandomStringUtils.random(7, true, false));
    public static final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(()-> RandomStringUtils.random(7, true, false));
    public static final ThreadLocal<String> caseIdentificationNumber = ThreadLocal.withInitial(()->RandomStringUtils.random(12, false, true));

    private final ThreadLocal<JsonObject> requestParams= ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> consumerSSN= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> caseID= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> primaryCaseId= ThreadLocal.withInitial(()->0);
    private final ThreadLocal<Integer> emaiId= ThreadLocal.withInitial(()->0);
    public final ThreadLocal<JSONObject> payloadObject= ThreadLocal.withInitial(JSONObject::new);
    private final ThreadLocal<String> eventNameResponse= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> emailAddress= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> preEmailId= ThreadLocal.withInitial(()->0);
    private final ThreadLocal<JsonArray> json= ThreadLocal.withInitial(JsonArray::new);
    private final ThreadLocal<JSONObject> payload= ThreadLocal.withInitial(JSONObject::new);
    private final ThreadLocal<Integer> phoneId= ThreadLocal.withInitial(()->0);
    private final ThreadLocal<String> phoneNumber= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> addressId= ThreadLocal.withInitial(()->0);
    private final ThreadLocal<Integer> initialAddressId= ThreadLocal.withInitial(()->0);
    private final ThreadLocal<String> initiallAddressStreet1= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressStreet1= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressStreet2= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressState= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressCounty= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressCity= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressZip= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressZipFour= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressCountyCode= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> firstName= ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> staticConsumerSSN= ThreadLocal.withInitial(String::new);


    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final ThreadLocal<String> PhoneEffectiveStartDate= ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> MailEffectiveStartDate= ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> AddressEffectiveStartDate= ThreadLocal.withInitial(String::new);
    /*private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private APICaseLoaderEligibilityEnrollmentController caseLoaderRequest = new APICaseLoaderEligibilityEnrollmentController();
*/
    @Given("I initiated Case Consumer Search API for Consumer Match")
    public void initiatedCaseConsumerContactSearchAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerSearchListByUser);
    }

    @Given("I initiated Case Loader API for Consumer Match")
    public void initiatedCaseLoaderAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);

        requestParams.set(generateCreateConsumerRequest());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

    }

    @When("I run Consumer Search API using {string}")
    public JsonObject runConsumerSearchAPI(String searchType) {
        requestParams.set(generateConsumerSearchRequest(searchType));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    @When("I run Case Loader API for Consumer ID for Consumer Match")
    public void runCaseLoaderAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I will select that consumer")
    public void selectThatConsumer() {
        List consumersList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");

        for (int i = 0; i < consumersList.size(); i++) {
            HashMap consumersData = (HashMap) consumersList.get(i);
            List consumerIdNumberList = (List) consumersData.get("consumerIdentificationNumber");
            for (int j = 0; j < consumerIdNumberList.size(); j++) {
                HashMap consumerIdNumberListData = (HashMap) consumerIdNumberList.get(i);
                assertEquals(consumerIdNumberListData.get("identificationNumberType"),
                        requestParams.get().get("consumerIdType").getAsString());
                assertEquals(consumerIdNumberListData.get("externalConsumerId"), requestParams.get().get("consumerIdentificationNumber").getAsString());
            }
        }
    }

    @Then("I will only find one consumer record with that unique external consumer ID")
    public void willOnlyFindOneConsumerRecord() {
        List consumersList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");
        boolean singleRecord = false;

        for (int i = 0; i < consumersList.size(); i++) {
            if (consumersList.size() == 1) {
                singleRecord = true;
                HashMap consumersData = (HashMap) consumersList.get(i);
                List consumerIdNumberList = (List) consumersData.get("consumerIdentificationNumber");
                for (int j = 0; j < consumerIdNumberList.size(); j++) {
                    HashMap consumerIdNumberListData = (HashMap) consumerIdNumberList.get(i);
                    assertEquals(consumerIdNumberListData.get("identificationNumberType"), requestParams.get().get("consumerIdType").getAsString());
                    assertEquals(consumerIdNumberListData.get("externalConsumerId"), requestParams.get().get("consumerIdentificationNumber").getAsString());
                }
            } else {
                singleRecord = false;
                break;
            }
        }
        assertTrue(singleRecord);
    }

    @Then("I will attempt to match a consumer using exact SSN")
    public void matchAConsumerUsingSSN() {
        List consumersList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");

        for (int i = 0; i < consumersList.size(); i++) {
            HashMap consumersData = (HashMap) consumersList.get(i);
            assertEquals(consumersData.get("consumerSSN"), requestParams.get().get("ssn").getAsString());
        }
    }

    @Then("I will not process the record, error the record out and capture the error reason")
    public void willNotProcessTheRecord() {
        assertEquals("fail", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("status"));

        List validationList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("validationList");

        if (validationList.size() == 1) {
            for (int i = 0; i < validationList.size(); i++) {
                HashMap validationData = (HashMap) validationList.get(i);
                assertNotNull(validationData.get("validationErrorCode"));
            }
        } else {
            System.out.println("Multiple error lists found. Exiting");
        }
    }

    @Then("I will attempt to match a consumer")
    public void attemptToMatchAConsumer() {
        List consumersList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");

        for (int i = 0; i < consumersList.size(); i++) {
            HashMap consumersData = (HashMap) consumersList.get(i);
            assertEquals(consumersData.get("consumerFirstName"), requestParams.get().get("consumerFirstName").getAsString());
            assertEquals(consumersData.get("consumerLastName"), requestParams.get().get("consumerLastName").getAsString());

            String dob = null;
            try {
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(requestParams.get().get("dob").getAsString());
                dob = dateFormat.format(dateOfBirth);
            } catch (ParseException e) {
                System.out.println("Exception occured when parsing the date of Birth");
            }
            assertEquals(consumersData.get("consumerDateOfBirth"), dob);
        }
    }

    private JsonObject generateConsumerSearchRequest(String searchType) {
        JsonObject request = new JsonObject();

        if (searchType.equals("details")) {
            request.addProperty("consumerFirstName", "Tammy");
            request.addProperty("consumerLastName", "Nicole");
            request.addProperty("dob", "1993-12-15");
        } else if (searchType.equals("consumerID")) {
            request.addProperty("consumerIdType", "Medicaid");
            request.addProperty("consumerIdentificationNumber", "2kg68Q");
        } else if (searchType.equals("consumerSSN")) {
            if (consumerSSN.get() != null) {
                System.out.println("printing the SSN " + consumerSSN.get());
                request.addProperty("consumerSSN", consumerSSN.get());
            } else if (staticConsumerSSN.get() != null) {
                System.out.println("printing the SSN " + staticConsumerSSN.get());
                request.addProperty("consumerSSN", staticConsumerSSN.get());
            } else
                request.addProperty("consumerSSN", "127000092");
        }

        return request;
    }

    public JsonObject generateCreateConsumerRequestOld() {
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
        CASE.addProperty("portfolioIdentificationNo", "");
        CASE.addProperty("programName", "CRM");
        CASE.addProperty("subProgramName", "");
        CASE.addProperty("needTranslatorInd", "true");
        CASE.addProperty("jobId", "1");
        CASE.addProperty("correlationId", "1");

        caseLoaderRequest.add("case", CASE);

        JsonObject consumers = new JsonObject();
        JsonArray consumersList = new JsonArray();
        consumersList.add(consumers);

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
        consumers.addProperty("createdOn", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("dateOfDeath", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("dateOfDeathNotifiedBy", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("dateOfDeathNotifiedDate", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("dateOfSsnValidation", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("effectiveEndDate", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("effectiveStartDate", "2019-05-08T14:05:31.799Z");
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
        consumers.addProperty("updatedOn", "2019-05-08T14:05:31.799Z");
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

        consumers.add("communicationPreferences", gson.toJsonTree(communicationPreferencesList).getAsJsonArray());

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

        consumers.add("consumerConsent", gson.toJsonTree(consumerConsentList).getAsJsonArray());

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

    public JsonObject generateCreateConsumerRequest() {
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
        CASE.addProperty("portfolioIdentificationNo", "");
        CASE.addProperty("programName", "CRM");
        CASE.addProperty("subProgramName", "");
        CASE.addProperty("needTranslatorInd", "true");
        CASE.addProperty("jobId", "1");
        CASE.addProperty("correlationId", "1");

        caseLoaderRequest.add("case", CASE);

        JsonObject consumers = new JsonObject();
        JsonArray consumersList = new JsonArray();
        consumersList.add(consumers);
//        consumers.addProperty("consumerIdType", "MEDICAID");
//        consumers.addProperty("consumerIdentificationNo", apitdu.getRandomNumber(7).randomNumber);
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
        consumers.addProperty("createdOn", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("dateOfDeath", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("dateOfDeathNotifiedBy", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("dateOfDeathNotifiedDate", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("dateOfSsnValidation", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("effectiveEndDate", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("effectiveStartDate", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("ethnicityCode", "");
        consumers.addProperty("aiNa", "");
        consumers.addProperty("genderCode", "");
        consumers.addProperty("mergeReason", "");
        consumers.addProperty("mergedConsumerId", "");
        consumers.addProperty("notBornInd", "false");
        consumers.addProperty("preferredLanguage", "");
        consumers.addProperty("raceCode", "");
        consumers.addProperty("relationShip", "");
        consumers.addProperty("ssnValidationAgency", "");
        consumers.addProperty("ssnValidationCode", "");
        consumers.addProperty("uiid", "");
        consumers.addProperty("updatedBy", "465");
        consumers.addProperty("updatedOn", "2019-05-08T14:05:31.799Z");
        consumers.addProperty("usResidentStatusCode", "");
        consumers.addProperty("pregnancyInd", "");
        consumers.addProperty("pregnancyDueDate", "");

        JsonObject consumerIdentificationNumber = new JsonObject();
        JsonArray consumerIdentificationNumberList = new JsonArray();
        consumerIdentificationNumberList.add(consumerIdentificationNumber);

        consumerIdentificationNumber.addProperty("externalConsumerId", "X6846545");
        consumerIdentificationNumber.addProperty("identificationNumberType", "CHIPa");
        consumerIdentificationNumber.addProperty("createdBy", "APIMK");
        consumerIdentificationNumber.addProperty("effectiveStartDate", "2019-08-06T06:15:14.000+0000");

        consumers.add("consumerIdentificationNumber", gson.toJsonTree(consumerIdentificationNumberList).getAsJsonArray());

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

        JsonObject contacts = new JsonObject();

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

        consumers.add("communicationPreferences", gson.toJsonTree(communicationPreferencesList).getAsJsonArray());

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

        consumers.add("consumerConsent", gson.toJsonTree(consumerConsentList).getAsJsonArray());

        JsonObject eligibility = new JsonObject();
        JsonArray eligibilityList = new JsonArray();
        eligibilityList.add(eligibility);

        eligibility.addProperty("coreEligibilitySegmentsId", "");
        eligibility.addProperty("startDate", "2019-08-13");
        eligibility.addProperty("endDate", "");
        eligibility.addProperty("categoryCode", "Alpha002");
        eligibility.addProperty("coverageCode", "Beta01ne3");
        eligibility.addProperty("exemptionCode", "Delta01n");
        eligibility.addProperty("programCode", "P");
        eligibility.addProperty("eligibilityStatusCode", "ESTATUSC");
        eligibility.addProperty("consumerDeprivationCode", "DEPCODE");
        eligibility.addProperty("createdOn", "2019-08-19");
        eligibility.addProperty("createdBy", "PSTMAN-UAT1");
        eligibility.addProperty("updatedOn", "");
        eligibility.addProperty("updatedBy", "");
        eligibility.addProperty("correlationId", "4398230948");
        eligibility.addProperty("uiid", "89898999966");

        JsonObject futureEligibility = new JsonObject();

        futureEligibility.addProperty("startDate", "2019-08-13");
        futureEligibility.addProperty("endDate", "");
        futureEligibility.addProperty("categoryCode", "CC01");
        futureEligibility.addProperty("coverageCode", "CvgC001");
        futureEligibility.addProperty("exemptionCode", "EXMPCD001");
        futureEligibility.addProperty("programCode", "P");
        futureEligibility.addProperty("eligibilityStatusCode", "EE010808");

        eligibility.add("futureEligibility", futureEligibility);

        JsonObject specialPopulationInfo = new JsonObject();

        specialPopulationInfo.addProperty("startDate", "2019-08-15");
        specialPopulationInfo.addProperty("specialEligibilityCode", "SPCLE001upd");
        specialPopulationInfo.addProperty("specialPopulationCaseNumber", "SPCLPCN002upd");
        specialPopulationInfo.addProperty("specialPopulationIndicator", "SPCLPI002uod02");

        eligibility.add("specialPopulationInfo", specialPopulationInfo);

        consumers.add("eligibility", gson.toJsonTree(eligibilityList).getAsJsonArray());

        JsonObject enrollment = new JsonObject();
        JsonArray enrollmentList = new JsonArray();
        enrollmentList.add(enrollment);

        enrollment.addProperty("aidCategoryCode", "string");
        enrollment.addProperty("anniversaryDate", "2019-09-04T09:27:30.257Z");
        enrollment.addProperty("autoAssignIndicator", "A");
        enrollment.addProperty("correlationId", "4398230948");
        enrollment.addProperty("createdBy", "POSTMAN-UAT1");
        enrollment.addProperty("createdOn", "2019-09-04T09:27:30.257Z");
        enrollment.addProperty("endDate", "2019-09-04T09:27:30.257Z");

        JsonObject enrollmentProviderInfo = new JsonObject();

        enrollmentProviderInfo.addProperty("correlationId", "4398230948");
        enrollmentProviderInfo.addProperty("createdBy", "POSTMAN-UAT");
        enrollmentProviderInfo.addProperty("createdOn", "2019-09-04T09:27:30.257Z");
        enrollmentProviderInfo.addProperty("effectiveEndDate", "2019-09-04T09:27:30.257Z");
        enrollmentProviderInfo.addProperty("effectiveStartDate", "2019-09-04T09:27:30.257Z");
        enrollmentProviderInfo.addProperty("providerEndDate", "2019-09-04T09:27:30.257Z");
        enrollmentProviderInfo.addProperty("providerId", "PP001");
        enrollmentProviderInfo.addProperty("providerLastName", "Rick");
        enrollmentProviderInfo.addProperty("providerMiddleName", "Rick");
        enrollmentProviderInfo.addProperty("providerNpi", "string");
        enrollmentProviderInfo.addProperty("providerPhoneNumber", "string");
        enrollmentProviderInfo.addProperty("providerStartDate", "2019-09-04T09:27:30.257Z");
        enrollmentProviderInfo.addProperty("providerType", "Thirdparty");
        enrollmentProviderInfo.addProperty("providerZipCodeExtn", "12");
        enrollmentProviderInfo.addProperty("providerZipCodeMain", "string");
        enrollmentProviderInfo.addProperty("uiid", "454374343");

        enrollment.add("enrollmentProviderInfo", enrollmentProviderInfo);

        enrollment.addProperty("enrollmentType", "BEHAVIORAL");
        enrollment.addProperty("lockInEndDate", "2019-09-04T09:27:30.257Z");
        enrollment.addProperty("lockInExemptionReason", "string");
        enrollment.addProperty("lockInStartDate", "2019-09-04T09:27:30.257Z");
        enrollment.addProperty("lockInStatusCode", "string");
        enrollment.addProperty("openEnrollentEndDate", "2019-09-04T09:27:30.257Z");
        enrollment.addProperty("openEnrollentStartDate", "2019-09-04T09:27:30.257Z");

        enrollment.addProperty("openEnrollentStatus", "string");
        enrollment.addProperty("planCode", "string");
        enrollment.addProperty("planEndDateReason", "string");
        enrollment.addProperty("planId", "12");
        enrollment.addProperty("programTypeCode", "MEDICAID");
        enrollment.addProperty("reinstatementDate", "2019-09-04T09:27:30.257Z");
        enrollment.addProperty("reinstatementIndicator", "A");
        enrollment.addProperty("retroactiveDate", "2019-09-04T09:27:30.257Z");
        enrollment.addProperty("retroactiveIndicator", "A");
        enrollment.addProperty("riskCode", "A");
        enrollment.addProperty("selectionTxnId", 0);
        enrollment.addProperty("serviceRegionCode", "string");
        enrollment.addProperty("specialNeedFlag", "string");
        enrollment.addProperty("startDate", "2019-09-04T09:27:30.257Z");
        enrollment.addProperty("subProgramTypeCode", "MEDICAID_GF");
        enrollment.addProperty("uiid", "454374343");
        enrollment.addProperty("waiverCode", "A");

        consumers.add("enrollment", gson.toJsonTree(enrollmentList).getAsJsonArray());

        JsonObject caseCommunicationPreferences = new JsonObject();
        JsonArray caseCommunicationPreferencesList = new JsonArray();
        caseCommunicationPreferencesList.add(caseCommunicationPreferences);

        caseCommunicationPreferences.addProperty("defaultInd", "boolean");
        caseCommunicationPreferences.addProperty("effectiveEndDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("effectiveStartDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("updatedBy", "string");
        caseCommunicationPreferences.addProperty("updatedOn", "2019-05-08T14:05:31.798Z");

        CASE.add("consumers", gson.toJsonTree(consumersList).getAsJsonArray());
        CASE.add("communicationPreferences", gson.toJsonTree(caseCommunicationPreferencesList).getAsJsonArray());

        request.add("caseLoaderRequest", gson.toJsonTree(caseList).getAsJsonArray());

        return request;
    }

    public void setConsumerSSN(String consumerSSN) {
        this.consumerSSN.set(consumerSSN);
        staticConsumerSSN.set(consumerSSN);
    }

    public String getConsumerIdFromSearchResults() {
        List consumersList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result.consumers");
        String consumerId = null;
        for (int i = 0; i < consumersList.size(); i++) {
            HashMap consumersData = (HashMap) ((ArrayList) consumersList.get(i)).get(0);
            if (consumersData.get("consumerSSN").toString().equalsIgnoreCase(requestParams.get().get("consumerSSN").getAsString())) {
                consumerId = consumersData.get("consumerId").toString();
                break;
            }

        }
        return consumerId;
    }

    @Given("I initiated Case Consumer Search API for case Match")
    public void initiatedCaseConsumerSearchAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchByCaseConsumer);
    }

    private JsonObject generateCaseConsumerSearchRequest(String searchType) {
        JsonObject request = new JsonObject();

        if (searchType.equals("details")) {
            request.addProperty("consumerFirstName", "Tammy");
            request.addProperty("consumerLastName", "Nicole");
            request.addProperty("dob", "1993-12-15");
        } else if (searchType.equals("consumerID")) {
            request.addProperty("consumerIdType", "Medicaid");
            request.addProperty("consumerIdentificationNo", "2kg68Q");
        } else if (searchType.equals("consumerSSN")) {
            if (consumerSSN.get() != null) {
                System.out.println("printing the SSN " + consumerSSN.get());
                request.addProperty("consumerSSN", consumerSSN.get());
            } else
                request.addProperty("consumerSSN", "127000092");
        }

        return request;
    }

    @When("I run Case Consumer Search API using {string}")
    public String runCaseConsumerSearchAPI(String searchType) {
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().getConsumerSSN());
        System.out.println("Printing SSN from Caseloader" + consumerSSN.get());
        requestParams.set(generateCaseConsumerSearchRequest(searchType));
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);


        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");
        for (int i = 0; i < results.size(); i++) {
            HashMap<String, Object> res = (HashMap) results.get(0);
            HashMap<String, Object> cases = (HashMap) res.get("cases");
            System.out.println(cases.get("caseId"));
        }
        return caseID.get();

    }

    @Given("I initiated Case Loader API request")
    public void iInitiatedCaseLoaderAPIRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
    }

    @And("User send Api call with payload {string} for CaseLoader")
    public void userSendApiCallWithPayloadForCaseLoader(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/" + payload + ".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("Verify Case Loader API validationErrorCode message from response")
    public void verifyMessageFromResponseWhenMatchingMoreThanOneSSN(List<String> message) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.validationList[0].validationErrorCode"), message.get(0), "Message not expected");
    }

    @Then("Verify validationErrorCode message from response when Matching more than one SSN")
    public void verifyValidationErrorCodeMessageFromResponseWhenMatchingMoreThanOneSSN(List<String> message) {
        String[] error = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.validationList[0].validationErrorCode").split(":");
        assertEquals(message.get(0), error[0], "Message not expected");
    }

    @Then("Verify Case Loader API message from response")
    public void verifyCaseLoaderAPIMessageFromResponse(List<String> message) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.message"), message.get(0), "Message not expected");
    }

    @And("User send Api call with name {string} payload {string} for CaseLoader")
    public void userSendApiCallWithNamePayloadForCaseLoader(String name, String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/" + payload + ".json");
        for (String each : data.keySet()) {
            if (!each.contains("SSN") && !each.contains("ConsumerId") && !each.contains("FirstName") && !each.contains("LastName") && !each.contains("caseIdentificationNumber")) {
                String trimedEsch = each.substring(39, each.length());
                System.out.println(trimedEsch);
                caseLoaderParams.get().put(trimedEsch, data.get(each));
            }
        }
        ThreadLocal<String> json = ThreadLocal.withInitial(()->API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        System.out.println("Printing json payload" + json.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json.get(), data));
        waitFor(5);
    }

    @Then("I initiate the Consumer search API")
    public void iInitiateTheConsumerSearchAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchByCaseConsumer);
    }

    @And("I send API call for searching the Consumer With")
    public void iSearchingTheConsumerWith(Map<String, String> data) {
        waitFor(5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/empty.json");
        ThreadLocal<String> json = ThreadLocal.withInitial(()->API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        System.out.println("Printing json payload" + json.get());

        for (int i = 0; i < 30; i++) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json.get(), data));
            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status").equalsIgnoreCase("success")) {
                System.out.println("System get call from " + (i + 1) + " attempts");
                break;
            }
        }
        System.out.println("Search results = " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());

    }

    @Then("I send api call to Update Primary Contact info")
    public void iSendApiCallToUpdatePrimaryContactInfo(Map<String, String> data) {
        primaryCaseId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.object.result[0].cases.caseId"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/primaryContactInfoUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("I initiate the Update Primary Contact info API")
    public void iInitiateTheUpdatePrimaryContactInfoAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updatePrimaryContact);
    }

    @And("Verify response primary Contact Email updated with {string} email")
    public void verifyResponsePrimaryContactEmailUpdatedWithEmail(String email) {
        emaiId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.email.emailId"));
        emailAddress.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.email.emailAddress"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.email.emailId"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.email.emailAddress"), email);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.email.effectiveStartDate")));
        API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().verifyThat("c.caseContacts.email.primaryIndicator", "==", "true");
    }

    @Then("Send api call with CaseId to Contact Primary API")
    public void sendApiCallWithCaseIdToContactPrimaryAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/cases/" + primaryCaseId.get() + "/contacts/primary");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
    }

    @Then("User send Api call to get {string}")
    public void userSendApiCallToGet(String event) {
        Map<String, String> data = new HashMap<>();
        data.put("eventName", event);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/empty.json");
        String jsonBody = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json.get());
        String payloadWith = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(jsonBody, data);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(payloadWith);

        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I initiate the Events APi")
    public void iInitiateTheEventsAPi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsBase);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents);
    }

    @Then("Verify {string} response payload")
    public void verifyResponsePayload(String eventName) {
        json.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content"));

        if (eventName.equalsIgnoreCase("EMAIL_SAVE_EVENT")) {
            for (int i = 0; i < json.get().size(); i++) {
                payload.set(new JSONObject(new JSONObject(json.get().get(i).toString()).get("payload").toString()));
                if (payload.get().getJSONObject("dataObject").get("emailId").toString().equalsIgnoreCase(String.valueOf(emaiId.get()))) {
                    assertEquals(json.get().get(0).getAsJsonObject().get("eventName").getAsString(), eventName);
                    assertEquals(payload.get().getString("recordType"), "Email");
                    assertEquals(payload.get().getJSONObject("dataObject").get("emailId").toString(), String.valueOf(emaiId.get()));
                    assertEquals(payload.get().getJSONObject("dataObject").get("emailAddress").toString(), emailAddress.get());
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("createdOn").toString()));
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("updatedOn"));
                    assertTrue(payload.get().getJSONObject("dataObject").get("primaryIndicator").equals(true));
                }
            }

        } else if (eventName.equalsIgnoreCase("EMAIL_UPDATE_EVENT")) {
            for (int i = 0; i < json.get().size(); i++) {
                payload.set(new JSONObject(new JSONObject(json.get().get(i).toString()).get("payload").toString()));
                if (payload.get().getJSONObject("dataObject").get("emailId").toString().equalsIgnoreCase(String.valueOf(preEmailId.get()))) {
                    assertEquals(json.get().get(0).getAsJsonObject().get("eventName").getAsString(), eventName);
                    assertEquals(payload.get().getString("recordType"), "Email");
                    assertEquals(payload.get().getJSONObject("dataObject").get("emailId").toString(), String.valueOf(preEmailId.get()));
                    assertEquals(payload.get().getJSONObject("dataObject").get("emailAddress").toString(), emailAddress.get());
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("createdOn").toString()));
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("updatedOn").toString()));
                    assertTrue(payload.get().getJSONObject("dataObject").get("updatedOn").toString().substring(0, 15).
                            equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("createdOn").toString().substring(0, 15)));
                    assertTrue(payload.get().getJSONObject("dataObject").get("effectiveStartDate").toString().substring(0, 15).
                            equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("effectiveEndDate").toString().substring(0, 15)));
                    assertTrue(payload.get().getJSONObject("dataObject").get("effectiveStartDate").toString().substring(0, 15).
                            equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("updatedOn").toString().substring(0, 15)));
                }
            }
        }
    }

    @Then("I search for Contact Primary with CaseId")
    public void iSearchForContactPrimaryWithCaseId() {
        primaryCaseId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.object.result[0].cases.caseId"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/cases/" + primaryCaseId.get() + "/contacts/primary");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        preEmailId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.email.emailId"));
        emailAddress.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.email.emailAddress"));
    }

    @Then("Sending api call for Update Primary Contact info")
    public void sendingApiCallForUpdatePrimaryContactInfo(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/primaryContactInfoUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("I send api call to Update Primary Phone info")
    public void iSendApiCallToUpdatePrimaryPhoneInfo(Map<String, String> data) {
        primaryCaseId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.object.result[0].cases.caseId"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/primaryContactPhoneUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @And("Verify response primary Contact Phone updated with {string} phone number with {string} phone type")
    public void verifyResponsePrimaryContactPhoneUpdatedWithPhoneNumberWithPhonetypePhoneType(String phone, String phoneType) {
        if (phoneType.equalsIgnoreCase("null::")) {
            phoneId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.phone.phoneId"));
            phoneNumber.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.phoneNumber"));
            assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.phone.phoneId"));
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.phoneNumber"), phone);
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.phoneType"), "Home");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.effectiveStartDate")));
            API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().verifyThat("c.caseContacts.phone.primaryIndicator", "==", "true");
        } else {
            phoneId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.phone.phoneId"));
            phoneNumber.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.phoneNumber"));
            assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.phone.phoneId"));
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.phoneNumber"), phone);
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.phoneType"), phoneType);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.effectiveStartDate")));
            API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().verifyThat("c.caseContacts.phone.primaryIndicator", "==", "true");
        }
    }

    @Then("Verify {string} response payload with {string} phone Type")
    public void verifyResponsePayloadWithPhoneType(String eventName, String phoneType) {
        json.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content"));
        if (eventName.equalsIgnoreCase("PHONE_SAVE_EVENT") && !phoneType.equalsIgnoreCase("Fax")) {
            for (int i = 0; i < json.get().size(); i++) {
                payload.set(new JSONObject(new JSONObject(json.get().get(i).toString()).get("payload").toString()));
                if (payload.get().getJSONObject("dataObject").get("phoneId").toString().equalsIgnoreCase(String.valueOf(phoneId.get()))) {
                    assertEquals(json.get().get(0).getAsJsonObject().get("eventName").getAsString(), eventName, "Event Name is incorrect");
                    assertEquals(payload.get().getString("recordType"), "Phone", "Record Type is incorrect");
                    assertEquals(payload.get().getJSONObject("dataObject").get("phoneId").toString(), String.valueOf(phoneId.get()), "Phone Id not matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("phoneNumber").toString(), phoneNumber.get(), "Phone Number not Matched");
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("createdOn").toString()), "Created On date format is incorrect");
                    assertTrue(payload.get().getJSONObject("dataObject").get("updatedOn").toString().substring(0, 15).
                                    equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("createdOn").toString().substring(0, 15)),
                            "UpdatedOn not equal to CreatedOn");
                    assertTrue(payload.get().getJSONObject("dataObject").get("primaryIndicator").equals(true), "Primary indicator not true");
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("createdBy"), "createdBy is not null");
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("updatedBy"), "updatedBy not null");
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate not null");


                }
            }
        } else if (eventName.equalsIgnoreCase("PHONE_UPDATE_EVENT")) {
            for (int i = 0; i < json.get().size(); i++) {
                payload.set(new JSONObject(new JSONObject(json.get().get(i).toString()).get("payload").toString()));
                if (payload.get().getJSONObject("dataObject").get("phoneId").toString().equalsIgnoreCase(String.valueOf(phoneId.get()))) {
                    assertEquals(json.get().get(0).getAsJsonObject().get("eventName").getAsString(), eventName, "Event Name is incorrect");
                    assertEquals(payload.get().getString("recordType"), "Phone", "Record Type is incorrect");
                    assertEquals(payload.get().getJSONObject("dataObject").get("phoneId").toString(), String.valueOf(phoneId.get()), "Phone Id not matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("phoneNumber").toString(), phoneNumber.get(), "Phone Number not Matched");
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("createdOn").toString()), "Created On date format is incorrect");
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("updatedOn").toString()), "Updated On date format is incorrect");
                    assertTrue(payload.get().getJSONObject("dataObject").get("updatedOn").toString().substring(0, 15).
                            equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("createdOn").toString().substring(0, 15)), "UpdatedOn not equal to CreatedOn");
                    assertTrue(payload.get().getJSONObject("dataObject").get("effectiveStartDate").toString().substring(0, 15).
                                    equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("effectiveEndDate").toString().substring(0, 15)),
                            "effectiveStartDate not equal to effectiveEndDate");
                    assertTrue(payload.get().getJSONObject("dataObject").get("effectiveStartDate").toString().substring(0, 15).
                                    equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("updatedOn").toString().substring(0, 15)),
                            "effectiveStartDate not equal to updatedOn");
                    assertTrue(payload.get().getJSONObject("dataObject").get("primaryIndicator").equals(false), "PrimaryIndicator is true");
                    assertEquals(payload.get().getJSONObject("dataObject").get("createdBy").toString(),
                            payload.get().getJSONObject("dataObject").get("updatedBy").toString(), "createdBy is not equal to updatedBy");
                    assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().verifyUtcFormat(payload.get().getJSONObject("dataObject").get("effectiveEndDate").toString().substring(0, 19)),
                            "effectiveEndDate wrong UTC format");
                }
            }
        } else if (eventName.equalsIgnoreCase("PHONE_SAVE_EVENT") && phoneType.equalsIgnoreCase("Fax")) {
            for (int i = 0; i < json.get().size(); i++) {
                payload.set(new JSONObject(new JSONObject(json.get().get(i).toString()).get("payload").toString()));
                if (payload.get().getJSONObject("dataObject").get("phoneId").toString().equalsIgnoreCase(String.valueOf(phoneId.get()))) {
                    assertEquals(json.get().get(0).getAsJsonObject().get("eventName").getAsString(), eventName, "Event Name is incorrect");
                    assertEquals(payload.get().getString("recordType"), "Phone", "Record Type is incorrect");
                    assertEquals(payload.get().getJSONObject("dataObject").get("phoneId").toString(), String.valueOf(phoneId.get()),
                            "Phone Id not matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("phoneNumber").toString(), phoneNumber.get(),
                            "Phone Number not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("phoneType").toString(), "Home",
                            "Phone Type is incorrect");
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("createdOn").toString()),
                            "CreatedOn date format is incorrect");
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("updatedOn"), "UpdatedOn not NULL");
                    assertTrue(payload.get().getJSONObject("dataObject").get("primaryIndicator").equals(true), "Primary indicator is false");
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate not Null");
                    assertEquals(payload.get().getJSONObject("dataObject").get("createdBy").toString(),
                            payload.get().getJSONObject("dataObject").get("updatedBy").toString());
                }
            }
        }
    }

    @Then("I search for Contact Primary with CaseId for Phone update")
    public void iSearchForContactPrimaryWithCaseIdForPhoneUpdate() {
        primaryCaseId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.object.result[0].cases.caseId"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/cases/" + primaryCaseId.get() + "/contacts/primary");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        phoneId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.phone.phoneId"));
        phoneNumber.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.phoneNumber"));
    }

    @Then("^I verify that \"([^ ]+)\\s+([^ ]+)\\s+(.+)\"$")
    public void verifyThat(String obj1, String sign, String obj2) {
        API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().verifyThat(obj1, sign, obj2);
    }

    @Then("I search again for Contact Primary with CaseId for Phone update")
    public void iSearchAgainForContactPrimaryWithCaseIdForPhoneUpdate() {
        primaryCaseId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseId"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/cases/" + primaryCaseId.get() + "/contacts/primary");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        phoneId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.phone.phoneId"));
        phoneNumber.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.phone.phoneNumber"));
    }

    @Then("I initiate the Case Correspondence API")
    public void iInitiateTheCaseCorrespondenceAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseCorrespondenceSearchAPI);
    }

    @Then("I send API call for Correspondence")
    public void iSendAPICallForCorrespondence(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCaseCorrespondence.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("I send API call for DC Correspondence")
    public void iSendAPICallForDCCorrespondence() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCaseCorrespondence.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        HashMap data = new HashMap<>();
        System.out.println("Printing json payload" + json);
        data.put("consumerId", API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getExternalConsumerId());
        data.put("caseId", API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId());
        waitFor(5);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));

    }

    @And("I verify response for Phone Type Cell primaryFlag is {string}")
    public void iVerifyResponseForActiveEmailsUsableFlagIs(String bool) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels.text.phoneNumbers[0].attributes.primaryFlag"), bool);
    }

    @And("I verify response for Phone Type Fax primaryFlag is {string}")
    public void iVerifyResponseForPhoneTypeFaxPrimaryFlagIs(String bool) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels.fax.phoneNumbers[0].attributes.primaryFlag"), bool);
    }

    @And("I verify response for Active Emails primaryFlag is {string}")
    public void iVerifyResponseForActiveEmailsPrimaryFlagIs(String bool) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels.email.emailAddresses[0].attributes.primaryFlag"), bool);
    }

    @And("I verify response for Address primaryFlag is {string}")
    public void iVerifyResponseForAddressPrimaryFlagIs(String bool) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels.mail.addresses[0].attributes.primaryFlag"), bool);
    }


    @And("I verify NONdefault {string} and usability flags for channels {string}")
    public void iVerifyNONdefaultAndUsabilityFlagsForChannels(String nonDefault, String channels) {
        List<JsonNode> count = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.consumers");
        int consumers = count.size() - 1;
        for (int x = 0; x <= consumers; x++) {
            // assertEquals(api_common.asStr("c.consumers[" + x + "].preferredWrittenLanguage"), null); //remove when 14841 is complete
            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].defaultFlag").equalsIgnoreCase("true")) {
                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].consumerRole").equalsIgnoreCase("Primary Individual"));
            } else {
                assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].consumerRole").equalsIgnoreCase("Primary Individual"));
            }
            if (nonDefault.compareToIgnoreCase("false") == 0) {
                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].defaultFlag").equalsIgnoreCase("true"));
            }
            if (channels.contains(",")) {
                String[] chans = channels.split(",");
                for (int i = 0; i <= chans.length - 1; i++) {
                    switch (chans[i].toLowerCase()) {
                        case "mail":
                            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("true")) {
                                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.unusableReason"), null);
                                assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".addresses[0].address").equalsIgnoreCase("null"));
                            } else {
                                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.unusableReason").equalsIgnoreCase("Consumer opted out"));
                                assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".addresses[0]"));
                            }
                            break;
                        case "email":
                            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("true")) {
                                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.unusableReason"), null);
                                assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".emailAddresses[0].emailAddress").equalsIgnoreCase("null"));
                            } else {
                                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.unusableReason").equalsIgnoreCase("Consumer opted out"));
                                assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".emailAddresses[0]"));
                            }
                            break;
                        case "fax":
                            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("true")) {
                                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.unusableReason"), null);
                                assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".phoneNumbers[0].phoneNumber").equalsIgnoreCase("null"));
                            } else {
                                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.unusableReason").equalsIgnoreCase("Consumer opted out"));
                                assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".phoneNumbers[0]"));
                            }
                            break;
                        case "text":
                            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("true")) {
                                assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.unusableReason"), null);
                                assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".phoneNumbers[0].phoneNumber").equalsIgnoreCase("null"));
                            } else {
                                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.unusableReason").equalsIgnoreCase("Consumer opted out"));
                                assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".phoneNumbers[0]"));
                                break;
                            }
                    }
                }
            } else {
                switch (channels) {
                    case "mail":
                        if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("true")) {
                            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.unusableReason"), null);
                            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".addresses[0].address").equalsIgnoreCase("null"));
                        } else {
                            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.unusableReason").equalsIgnoreCase("Consumer opted out"));
                            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".addresses[0]"), null);
                        }
                        break;
                    case "email":
                        if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("true")) {
                            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.unusableReason"), null);
                            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".emailAddresses[0].emailAddress").equalsIgnoreCase("null"));
                        } else {
                            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.unusableReason").equalsIgnoreCase("Consumer opted out"));
                            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".addresses[0]"), null);
                        }
                        break;
                    case "fax":
                        if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("true")) {
                            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.unusableReason"), null);
                            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".phoneNumbers[0].phoneNumber").equalsIgnoreCase("null"));
                        } else {
                            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.unusableReason").equalsIgnoreCase("Consumer opted out"));
                            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".addresses[0]"), null);
                        }
                        break;
                    case "text":
                        if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("true")) {
                            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.unusableReason"), null);
                            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".phoneNumbers[0].phoneNumber").equalsIgnoreCase("null"));
                        } else {
                            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.unusableReason").equalsIgnoreCase("Consumer opted out"));
                            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".addresses[0]"), null);
                            break;
                        }
                }
            }
        }
    }

    @And("I verify channels are not usable {string}")
    public void iVerifyChannelsAreNotUsable(String channels) {
        List<JsonNode> count = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.consumers");
        int consumers = count.size() - 1;
        for (int x = 0; x <= consumers; x++) {
            if (channels.contains(",")) {
                String[] chans = channels.split(",");
                for (int i = 0; i <= chans.length - 1; i++) {
                    assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + chans[i].toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("false"));
                }
            } else {
                assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[" + x + "].channels." + channels.toLowerCase() + ".usability.usableFlag").equalsIgnoreCase("false"));
            }
        }
    }

    @And("I verify no recipients are returned in the response")
    public void iVerifyNoRecipientsAreReturnedInTheResponse() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers"), (null));
    }


    @And("Verify response primary Contact Address updated with {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} all detail with {string} address type")
    public void verifyResponsePrimaryContactAddressUpdatedWithAddress1WithAddressType(String callN, String AS1, String AS2, String State, String County, String countyCode, String City, String zip, String zip4, String addressType) {

        addressId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.address.addressId"));
        addressStreet1.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressStreet1"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.address.addressId"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressStreet2"), AS2);
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressZip"), zip);
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressZipFour"), zip4);
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressState"), State);
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressCity"), City);
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressCounty"), County);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.effectiveStartDate")));
        API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().verifyThat("c.caseContacts.address.primaryIndicator", "==", "false");//digital endpoint update primary indicator has bean changed to false CP-13048

    }

    @Then("I send api call to Update Primary Address info")
    public void iSendApiCallToUpdatePrimaryAddressInfo(Map<String, String> data) {
        primaryCaseId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.object.result[0].cases.caseId"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/primaryContactAddressUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("I search for Contact Primary Address with CaseId")
    public void iSearchForContactPrimaryAddressWithCaseId() {
        primaryCaseId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.object.result[0].cases.caseId"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/cases/" + primaryCaseId.get() + "/contacts/primary");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        addressId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.address.addressId"));
    }

    @Then("Verify {string} response payload with {string} address Type {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void verifyResponsePayloadWithAddressType(String eventName, String addressType, String AS1, String AS2, String State, String County, String countyCode, String City, String zip, String zip4) throws Throwable {
        json.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content"));
        if (eventName.equalsIgnoreCase("ADDRESS_SAVE_EVENT") && !addressType.equalsIgnoreCase("Physical")) {
            for (int i = 0; i < json.get().size(); i++) {
                payload.set(new JSONObject(new JSONObject(json.get().get(i).toString()).get("payload").toString()));
                if (payload.get().getJSONObject("dataObject").get("addressId").toString().equalsIgnoreCase(String.valueOf(addressId.get()))) {
                    assertEquals(json.get().get(0).getAsJsonObject().get("eventName").getAsString(), eventName, "Event Name is incorrect");
                    assertEquals(payload.get().getString("recordType"), "Address", "Record Type is incorrect");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressId").toString(), String.valueOf(addressId), "Address Id does not matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressStreet1").toString(), AS1, "Address Street1 does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressStreet2").toString(), AS2, "Address Street1 does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressState").toString(), State, "addressState does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressCity").toString(), City, "addressCity does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressCounty").toString(), County, "addressCounty does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressCountyCode").toString(), countyCode, "addressCountyCode does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressZip").toString(), zip, "addressZip does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressZipFour").toString(), zip4, "addressZipFour does not Matched");
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("createdOn").toString()), "Created On date format is incorrect");
                    assertTrue(payload.get().getJSONObject("dataObject").get("updatedOn").toString().substring(0, 15).
                                    equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("createdOn").toString().substring(0, 15)),
                            "UpdatedOn not equal to CreatedOn");
                    assertTrue(payload.get().getJSONObject("dataObject").get("primaryIndicator").equals(true), "Primary indicator not true");
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("createdBy"), "createdBy is not null");
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("updatedBy"), "updatedBy not null");
                    assertTrue(payload.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate not null");
                    break;

                }
            }
        } else if (eventName.equalsIgnoreCase("ADDRESS_UPDATE_EVENT")) {
            for (int i = 0; i < json.get().size(); i++) {
                payload.set(new JSONObject(new JSONObject(json.get().get(i).toString()).get("payload").toString()));
                if (payload.get().getJSONObject("dataObject").get("addressId").toString().equalsIgnoreCase(String.valueOf(initialAddressId.get()))) {
                    assertEquals(json.get().get(0).getAsJsonObject().get("eventName").getAsString(), eventName, "Event Name is incorrect");
                    assertEquals(payload.get().getString("recordType"), "Address", "Record Type is incorrect");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressStreet1").toString(), addressStreet1.get(), "Address Street1 does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressStreet2").toString(), addressStreet2.get(), "Address Street1 does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressState").toString(), addressState.get(), "addressState does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressCity").toString(), addressCity.get(), "addressCity does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressCounty").toString(), addressCounty.get(), "addressCounty does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressZip").toString(), addressZip.get(), "addressZip does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressZipFour").toString(), addressZipFour.get(), "addressZipFour does not Matched");
                    assertEquals(payload.get().getJSONObject("dataObject").get("addressCountyCode").toString(), addressCountyCode.get(), "addressCountyCode does not Matched");

                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("createdOn").toString()), "Created On date format is incorrect");
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(payload.get().getJSONObject("dataObject").get("updatedOn").toString()), "Updated On date format is incorrect");
                    assertTrue(payload.get().getJSONObject("dataObject").get("updatedOn").toString().substring(0, 15).
                            equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("createdOn").toString().substring(0, 15)), "UpdatedOn not equal to CreatedOn");
                    assertTrue(payload.get().getJSONObject("dataObject").get("effectiveStartDate").toString().substring(0, 15).
                                    equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("effectiveEndDate").toString().substring(0, 15)),
                            "effectiveStartDate not equal to effectiveEndDate");
                    assertTrue(payload.get().getJSONObject("dataObject").get("effectiveStartDate").toString().substring(0, 15).
                                    equalsIgnoreCase(payload.get().getJSONObject("dataObject").get("updatedOn").toString().substring(0, 15)),
                            "effectiveStartDate not equal to updatedOn");
                    assertTrue(payload.get().getJSONObject("dataObject").get("primaryIndicator").equals(false), "PrimaryIndicator is true");
                    assertNotEquals(payload.get().getJSONObject("dataObject").get("createdBy").toString(),
                            payload.get().getJSONObject("dataObject").get("updatedBy").toString(), "createdBy is equal to updatedBy");
                    assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().verifyUtcFormat(payload.get().getJSONObject("dataObject").get("effectiveEndDate").toString().substring(0, 19)),
                            "effectiveEndDate wrong UTC format");
                    break;
                }
            }
            throw new PendingException();
        }
    }

    @Then("Send api call with CaseId to Contact Primary API to retrive Initial address info")
    public void sendApiCallWithCaseIdToContactPrimaryAPIToRetriveInitialAddressInfo() throws Throwable {
        primaryCaseId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.object.result[0].cases.caseId"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/cases/" + primaryCaseId.get() + "/contacts/primary");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        initialAddressId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.caseContacts.address.addressId"));
        initiallAddressStreet1.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressStreet1"));
        addressStreet2.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressStreet2"));
        addressState.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressState"));
        addressCountyCode.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressCountyCode"));
        addressCounty.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressCounty"));
        addressCity.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressCity"));
        addressZip.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressZip"));
        addressZipFour.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.caseContacts.address.addressZip"));
    }

    @And("I map infos from Consumer Search API")
    public void iMapInfosFromConsumerSearchAPI() {
        caseLoaderParams.get().put("consumerSSN", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerSSN"));
        caseLoaderParams.get().put("consumerPrefix", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerPrefix"));
        caseLoaderParams.get().put("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerFirstName"));
        caseLoaderParams.get().put("consumerLastName", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerLastName"));
        caseLoaderParams.get().put("consumerMiddleName", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerMiddleName"));
        caseLoaderParams.get().put("consumerSuffix", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerSuffix"));
        caseLoaderParams.get().put("genderCode", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].genderCode"));
        caseLoaderParams.get().put("ethnicityCode", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].ethnicityCode"));
        caseLoaderParams.get().put("raceCode", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].raceCode"));
        caseLoaderParams.get().put("citizenship", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].citizenship"));
        caseLoaderParams.get().put("usResidentStatusCode", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].usResidentStatusCode"));
        caseLoaderParams.get().put("consumerDateOfBirth", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerDateOfBirth"));
        caseLoaderParams.get().put("dateOfDeath", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].dateOfDeath"));
        caseLoaderParams.get().put("dateOfDeathNotifiedDate", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].dateOfDeathNotifiedDate"));
        caseLoaderParams.get().put("dateOfDeathNotifiedBy", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].dateOfDeathNotifiedBy"));
        caseLoaderParams.get().put("aiNa", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].aiNa"));
        caseLoaderParams.get().put("relationShip", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].relationShip"));
        caseLoaderParams.get().put("writtenLanguage", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].writtenLanguage"));
        caseLoaderParams.get().put("spokenLanguage", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].spokenLanguage"));
        caseLoaderParams.get().put("caseIdentificationNumberId", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberId"));
        caseLoaderParams.get().put("externalCaseId", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.caseIdentificationNumber[0].externalCaseId"));
        caseLoaderParams.get().put("caseIdentificationNumberTypeId", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberTypeId"));
        caseLoaderParams.get().put("updatedBy", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.updatedBy"));
        caseLoaderParams.get().put("caseId", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.caseId"));
        caseLoaderParams.get().put("correlationId", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].correlationId"));
        caseLoaderParams.get().put("consumerId", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerId"));
        caseLoaderParams.get().put("CUpdatedBy", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].updatedBy"));
        caseLoaderParams.get().put("updatedOn", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].updatedOn"));
    }

    @And("I map infos from Consumer Search API for second run")
    public void iMapInfosFromConsumerSearchAPIForSecondRun() {
        assertEquals(caseLoaderParams.get().get("caseIdentificationNumberId"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberId"));
        assertEquals(caseLoaderParams.get().get("externalCaseId"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.caseIdentificationNumber[0].externalCaseId"));
        assertEquals(caseLoaderParams.get().get("caseIdentificationNumberTypeId"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberTypeId"));
        assertEquals(caseLoaderParams.get().get("updatedBy"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.updatedBy"));
        assertEquals(caseLoaderParams.get().get("consumerDateOfBirth"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerDateOfBirth"));
        assertEquals(caseLoaderParams.get().get("caseId"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].cases.caseId"));
        assertEquals(caseLoaderParams.get().get("consumerLastName"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerLastName"));
        assertEquals(caseLoaderParams.get().get("consumerFirstName"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerFirstName"));
        assertEquals(caseLoaderParams.get().get("correlationId"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].correlationId"));
        assertEquals(caseLoaderParams.get().get("consumerId"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerId"));
        assertEquals(caseLoaderParams.get().get("consumerSSN"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerSSN"));
    }

    @And("I verify values updated")
    public void iVerifyValuesUpdated() {
        assertEquals(caseLoaderParams.get().get("consumerSSN"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerSSN"));
        assertEquals(caseLoaderParams.get().get("consumerPrefix"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerPrefix"));
        assertEquals(caseLoaderParams.get().get("consumerFirstName"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerFirstName"));
        assertEquals(caseLoaderParams.get().get("consumerMiddleName"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerMiddleName"));
        assertEquals(caseLoaderParams.get().get("consumerLastName"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerLastName"));
        assertEquals(caseLoaderParams.get().get("consumerSuffix"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerSuffix"));
        assertEquals(caseLoaderParams.get().get("genderCode"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].genderCode"));
        assertEquals(caseLoaderParams.get().get("ethnicityCode"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].ethnicityCode"));
        assertEquals(caseLoaderParams.get().get("raceCode"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].raceCode"));
        assertEquals(caseLoaderParams.get().get("citizenship"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].citizenship"));
        assertEquals(caseLoaderParams.get().get("usResidentStatusCode"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].usResidentStatusCode"));
        assertEquals(caseLoaderParams.get().get("consumerDateOfBirth"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerDateOfBirth"));
        assertEquals(caseLoaderParams.get().get("dateOfDeath"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].dateOfDeath"));
        assertEquals(caseLoaderParams.get().get("dateOfDeathNotifiedDate"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].dateOfDeathNotifiedDate"));
        assertEquals(caseLoaderParams.get().get("dateOfDeathNotifiedBy"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].dateOfDeathNotifiedBy"));
        assertEquals(caseLoaderParams.get().get("aiNa"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].aiNa"));
        assertEquals(caseLoaderParams.get().get("relationShip"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].relationShip"));
    }

    @And("I verify channel {string} is not usable with {string}")
    public void iVerifyChannelIsNotUsableWith(String channel, String usableOutput) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".usability.unusableReason"), usableOutput);
    }

    @And("I verify channel {string} is usable")
    public void iVerifyChannelIsUsable(String channel) {
        if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".usability.usableFlag").equals("true")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".usability.usableFlag").equals("true"));
            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".addresses").equals(null));
        } else {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[1].channels." + channel.toLowerCase() + ".usability.usableFlag").equals("true"));
            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[1].channels." + channel.toLowerCase() + ".addresses").equals(null));
        }
    }

    @And("I verify channel {string} is not usable")
    public void iVerifyChannelIsNotUsable(String channel) {
        if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".usability.usableFlag").equals("false")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".usability.usableFlag").equals("false"));
        } else {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[1].channels." + channel.toLowerCase() + ".usability.usableFlag").equals("false"));
        }
    }

    @And("I verify {string} channel is usable oly for consumer without {string}")
    public void iVerifyChannelIsUsableOlyForConsumerWithout(String channel, String value) {
        if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".usability").contains(value)) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".addresses"), null);
            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[1].channels." + channel.toLowerCase() + ".addresses").equals(null));
        } else {
            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].channels." + channel.toLowerCase() + ".addresses").equals(null));
            assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[1].channels." + channel.toLowerCase() + ".addresses").equals(null));
        }
    }

    @And("I verify {string} is updated {string}")
    public void iVerifyIsUpdated(String field, String value) {
        waitFor(5);
        if (field.equalsIgnoreCase("suffix")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerSuffix").contains(value));
        } else if (field.equalsIgnoreCase("prefix")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerPrefix").contains(value));
        } else if (field.equalsIgnoreCase("gender")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].genderCode").contains(value));
        } else if (field.equalsIgnoreCase("ethnicity")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].ethnicityCode").contains(value));
        } else if (field.equalsIgnoreCase("race")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].raceCode").contains(value));
        } else if (field.equalsIgnoreCase("role")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].caseConsumers[0].consumerRole").contains(value));
        } else if (field.equalsIgnoreCase("relationShip")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].relationShip").contains(value));
        }
    }

    @Then("I will see consumer record successfully updated with null value for {string} field")
    public void iWillSeeConsumerRecordSuccessfullyUpdatedWithNullValueForField(String value) {
        if (value.equalsIgnoreCase("suffix")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.consumerSuffix") == null || API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.consumerSuffix").contains("UNKNOWN"));
        } else if (value.equalsIgnoreCase("prefix")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.consumerPrefix") == null || API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.consumerPrefix").contains("UNKNOWN"));
        } else if (value.equalsIgnoreCase("gender")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.genderCode") == null || API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.genderCode").contains("UNKNOWN"));
        } else if (value.equalsIgnoreCase("ethnicity")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.ethnicityCode") == null || API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.ethnicityCode").contains("UNKNOWN"));
        } else if (value.equalsIgnoreCase("race")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.raceCode") == null || API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers.raceCode").contains("UNKNOWN"));
        }
    }

    @Then("I see list of parameters that remain as per initial save")
    public void iSeeListOfParametersThatRemainAsPerInitialSave() {
        assertEquals(caseLoaderParams.get().get("consumerSSN"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerSSN"));
        assertEquals(caseLoaderParams.get().get("consumerFirstName"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerFirstName"));
        assertEquals(caseLoaderParams.get().get("consumerLastName"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerLastName"));
        assertEquals(caseLoaderParams.get().get("genderCode"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].genderCode"));
        assertEquals(caseLoaderParams.get().get("ethnicityCode"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].ethnicityCode"));
        assertEquals(caseLoaderParams.get().get("raceCode"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].raceCode"));
        assertEquals(caseLoaderParams.get().get("citizenship"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].citizenship"));
        assertEquals(caseLoaderParams.get().get("usResidentStatusCode"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].usResidentStatusCode"));
        assertEquals(caseLoaderParams.get().get("consumerDateOfBirth"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerDateOfBirth"));
        assertEquals(caseLoaderParams.get().get("relationShip"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].relationShip"));
        assertEquals(caseLoaderParams.get().get("writtenLanguage"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].writtenLanguage[0]"));
        assertEquals(caseLoaderParams.get().get("spokenLanguage"), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].spokenLanguage[0]"));
    }

    @Then("Response will contain an error {string} and record will not be saved")
    public void responseWillContainAnErrorAndRecordWillNotBeSaved(String message) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.asString().contains(message), "Response don't have message " + message);
    }

    @And("I verify languages updated {string} and {string}")
    public void iVerifyLanguagesUpdatedAnd(String written, String spoken) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].communicationPreferences[0].valuePairIdCommPref").contains(written.toUpperCase()) ||
                API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].communicationPreferences[1].valuePairIdCommPref").contains(written.toUpperCase()) ||
                API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].communicationPreferences[2].valuePairIdCommPref").contains(written.toUpperCase()));
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].communicationPreferences[0].valuePairIdCommPref").contains(spoken.toUpperCase()) ||
                API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].communicationPreferences[1].valuePairIdCommPref").contains(spoken.toUpperCase()) ||
                API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].communicationPreferences[2].valuePairIdCommPref").contains(spoken.toUpperCase()));
    }

    @And("I verify updated bys {string} and {string}")
    public void iVerifyUpdatedBysAnd(String UpBy, String UpOn) {
        Assert.assertFalse(caseLoaderParams.get().get("updatedOn").equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].updatedBy[0]")));
        Assert.assertFalse(caseLoaderParams.get().get("CUpdatedBy").equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].updatedOn[0]")));
    }

    @Then("I validate record saved with {string} as {string}")
    public void iValidateRecordSavedWithAs(String field, String expectedValue) throws Throwable {
        String actualValue = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumer" + field);
        assertTrue(actualValue.equals(expectedValue), field + " value should be " + expectedValue);
    }

    @Then("Caseloder Response will contain an error {string} and record will not be saved")
    public void caseloderResponseWillContainAnErrorAndRecordWillNotBeSaved(String expecTedError) throws Throwable {
        String actualMessage = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.validationList[0].validationErrorCode");
        assertTrue(actualMessage.equals(expecTedError), "Wrong error message returned");

    }

    @Then("I validate Case Consumer record has not been saved")
    public void iValidateCaseConsumerRecordHasNotBeenSaved() throws Throwable {
        String searchResult = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0]");
        assertNull(searchResult, "Search result is not null");
    }

    @And("I send API call for quick searching the Consumer With")
    public void iSearcForConsumerWith(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/empty.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);

    }

    @Then("Case Loader service will set the Consumer Type to Consumer")
    public void caseLoaderServiceWillSetTheConsumerTypeToConsumer() throws Throwable {
        String expectedValue = "Consumer";
        String actualValue = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerType");
        System.out.println(actualValue);
        assertTrue(actualValue.equals(expectedValue), "Consumer type value should be " + expectedValue);
    }

    @Then("I validate Case Consumer record has been created {string}")
    public void iValidateCaseConsumerRecordHasBeenCreated(String callName) throws Throwable {
        String firstName = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerFirstName");
        String lastName = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerLastName");
        String consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumerId");
        assertTrue(firstName.equals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(callName + ".caseLoaderRequest[0].case.consumers[0].consumerFirstName")));
        assertTrue(lastName.equals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(callName + ".caseLoaderRequest[0].case.consumers[0].consumerLastName")));
        assertNotNull(consumerId);
        System.out.println(firstName);
        System.out.println(lastName);
    }

    @Then("I will see consumer record successfully created with {string} value for {string} field for {string}")
    public void iWillSeeConsumerRecordSuccessfullyCreatedWithValueForPrefixField(String expected, String field, String callName) throws Throwable {
        iValidateCaseConsumerRecordHasBeenCreated(callName);
        String actual = "";
        if (field.equals("consumerRole")) {
            actual = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].caseConsumers[0]." + field);
        } else {
            actual = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0]." + field);
        }
        assertTrue(actual.equals(expected), field + "value does not match, expected is " + expected + " actual -" + actual);


    }

    @Then("I will see consumer record successfully created with null value for {string} field for {string}")
    public void iWillSeeConsumerRecordSuccessfullyCreatedWithNullValueForField(String field, String callName) throws Throwable {
        iValidateCaseConsumerRecordHasBeenCreated(callName);
        String actualFiled = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0]." + field);
        assertNull(actualFiled, "The value for " + field + " should be null");
    }

    @Then("I will see consumer record successfully created with {string} {string} value for languages fields for {string}")
    public void iWillSeeConsumerRecordSuccessfullyCreatedWithValueForLanguagesFieldsFor(String written, String spoken, String callName) throws Throwable {
        iValidateCaseConsumerRecordHasBeenCreated(callName);
        String expectedWritten = "WRITTEN_LANGUAGE_" + written;
        String expectedSpoken = "SPOKEN_LANGUAGE_" + spoken;
        String actualSpoken = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].communicationPreferences[0].valuePairIdCommPref");
        String actualWritten = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].communicationPreferences[1].valuePairIdCommPref");
        System.out.println(actualSpoken);
        System.out.println(expectedSpoken);
        System.out.println(actualWritten);
        System.out.println(expectedWritten);
        // assertTrue(actualSpoken.endsWith(spoken), "The value for spoken language should be " + spoken);
        //  assertTrue(actualWritten.endsWith(written), "The value for written language should be " + written);
        assertEquals("WRITTEN_LANGUAGE_" + written, expectedWritten, "The value for spoken language should be " + written);
        assertEquals("SPOKEN_LANGUAGE_" + spoken, expectedSpoken, "The value for spoken language should be " + spoken);
    }

    @Then("I verify updated consumer details in event list of response string")
    public void iVerifyUpdatedConsumerDetailsInEventListOfResponseString() {
        STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().iverifyUpdatedConsumerDetailsIneventListResponse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject);
    }

    @And("I capture communication preferences details before update")
    public void iCaptureCommunicationPreferencesDetailsBeforeUpdate() {
        STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().iCaptureCommPrefDetailsBeforeSpokenLanguageUpdate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject);
    }

    @Then("I verify capture communication preferences details after spoken language update")
    public void iVerifyCaptureCommunicationPreferencesDetailsBeforeSpokenLanguageUpdate() {
        STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().iVerifyCommPrefDetailsAfterSpokenLanguageUpdate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject);
    }

    @Then("I verify capture communication preferences details when language not updated")
    public void iVerifyCaptureCommunicationPreferencesDetailsWhenNoLanguageUpdate() {
        STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().iVerifyCommPrefDetailsWithNoLanguageUpdate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject);
    }

    @Then("I verify capture communication preferences details after Correspondence Preference updated")
    public void iVerifyCaptureCommunicationPreferencesDetailsWhenCorresPrefUpdate() {
        STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().iVerifyCommPrefDetailsWhenCorresPrefUpdated(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject);
    }

    @Then("I send api call to Update Primary Address info With Valid ID")
    public void iSendApiCallToUpdatePrimaryAddressInfoWithValidID(Map<String, String> data) {
        //primaryCaseId = api_common.asInt("c.object.result[0].cases.caseId");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/primaryContactAddressUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("verify the response that is returned includes the data that was saved in the database")
    public void verify_the_response_that_is_returned_includes_the_data_that_was_saved_in_the_database(Map<String, String> data) {
        // set time zone according Greenwich time to be able to compare time from the response
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        AddressEffectiveStartDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.effectiveStartDate"));
        PhoneEffectiveStartDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.phone.effectiveStartDate"));
        MailEffectiveStartDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.email.effectiveStartDate"));
        String PhoneformatedEffectiveStartDate = PhoneEffectiveStartDate.get().substring(0, 19).replaceAll("T", " ");
        String AddressformatedEffectiveStartDate = AddressEffectiveStartDate.get().substring(0, 19).replaceAll("T", " ");
        String MailformatedEffectiveStartDate = MailEffectiveStartDate.get().substring(0, 19).replaceAll("T", " ");
        //asset that current time equals to time of data created +-3sec
        System.out.println("Current GMT time is : -- " + df.format(date));
        assertTrue(PhoneformatedEffectiveStartDate.compareTo(df.format(date)) >= -3 && PhoneformatedEffectiveStartDate.compareTo(df.format(date)) <= 3, "NOT matching time");
        assertTrue(AddressformatedEffectiveStartDate.compareTo(df.format(date)) >= -3 && AddressformatedEffectiveStartDate.compareTo(df.format(date)) <= 3, "NOT matching time");
        assertTrue(MailformatedEffectiveStartDate.compareTo(df.format(date)) >= -3 && MailformatedEffectiveStartDate.compareTo(df.format(date)) <= 3, "NOT matching time");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressStreet1"), data.get("caseContacts.address.addressStreet1"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressStreet2"), data.get("caseContacts.address.addressStreet2"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressCity"), data.get("caseContacts.address.addressCity"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressState"), data.get("caseContacts.address.addressState"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressCountyCode"), data.get("caseContacts.address.addressCountyCode"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressZip"), data.get("caseContacts.address.addressZip"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressZipFour"), data.get("caseContacts.address.addressZipFour"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.email.emailAddress"), data.get("caseContacts.email.emailAddress"));
    }


    @Then("I verify consumer details  without an External Case ID")
    public void verifyConsumerDetailsWithoutExternalId() {
        JsonObject reqObj = APIConsumerPopulationDmnController.caseLoaderReq.get();
        JsonArray consumersArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("object").getAsJsonArray("result").get(0)
                .getAsJsonObject().getAsJsonArray("consumers");
        assertEquals(consumersArray.get(0).getAsJsonObject().get("consumerFirstName").getAsString(),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("consumerFirstName").getAsString());

        assertEquals(consumersArray.get(0).getAsJsonObject().get("consumerLastName").getAsString(),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("consumerLastName").getAsString());


        assertEquals(consumersArray.get(0).getAsJsonObject().get("consumerMiddleName").getAsString(),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("consumerMiddleName").getAsString());

        assertEquals(consumersArray.get(0).getAsJsonObject().get("consumerSSN").getAsString(),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("consumerSSN").getAsString());

        assertEquals(consumersArray.get(0).getAsJsonObject().get("consumerDateOfBirth").getAsString(),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("consumerDateOfBirth").getAsString());

        assertEquals(consumersArray.get(0).getAsJsonObject().get("dateOfDeath"),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("dateOfDeath"));

        assertEquals(consumersArray.get(0).getAsJsonObject().get("genderCode").getAsString(),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("genderCode").getAsString());

        assertTrue(consumersArray.get(0).getAsJsonObject().get("citizenship").isJsonNull());
        assertTrue(consumersArray.get(0).getAsJsonObject().get("maritalCode").isJsonNull());

        assertEquals(consumersArray.get(0).getAsJsonObject().get("pregnancyInd").getAsString(),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("pregnancyInd").getAsString());

        assertEquals(consumersArray.get(0).getAsJsonObject().get("pregnancyDueDate"),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("pregnancyDueDate"));

        assertEquals(consumersArray.get(0).getAsJsonObject().get("createdOn").getAsString().substring(0, 10),
                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("createdOn").getAsString().substring(0, 10));

//        assertEquals(consumersArray.get(0).getAsJsonObject().get("createdBy").getAsString(),
//                reqObj.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").
//                        getAsJsonArray("consumers").get(0).getAsJsonObject().get("requestedBy").getAsString());


    }

    @Then("I verify data Synchronous saved in database")
    public void I_verify_data_Synchronous_saved_in_database() {
        String actualStartDateAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.effectiveStartDate");
        String actualStartDatePhone = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.phone.effectiveStartDate");
        String actualStartDateMail = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.email.effectiveStartDate");
        assertTrue(actualStartDateAddress.substring(0, 19).compareTo(AddressEffectiveStartDate.get().substring(0, 19)) <= 1 && actualStartDateAddress.substring(0, 19).compareTo(AddressEffectiveStartDate.get().substring(0, 19)) >= -1, "Time for Address mismatch");
        assertTrue(actualStartDatePhone.substring(0, 19).compareTo(PhoneEffectiveStartDate.get().substring(0, 19)) <= 1 && actualStartDatePhone.substring(0, 19).compareTo(AddressEffectiveStartDate.get().substring(0, 19)) >= -1, "Time for Phone mismatch");
        assertTrue(actualStartDateMail.substring(0, 19).compareTo(MailEffectiveStartDate.get().substring(0, 19)) <= 1 && actualStartDateMail.substring(0, 19).compareTo(AddressEffectiveStartDate.get().substring(0, 19)) >= -1, "Time for Mail mismatch");
    }

    @When("Send api call with CaseId to Contact Primary API for {string}")
    public void sendApiCallWithCaseIdToContactPrimaryAPIForID(String id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/cases/" + id + "/contacts/primary");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
    }

    @Then("I will verify error message {string} and error code {string}")
    public void i_will_verify_error_message_and_error_code(String message, String code) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("errorCode"), code, "Error code not match expected ");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("errorMessage"), message, "Error code not match expected ");
    }

    @Then("I send api call to Update Primary Address info With Valid ID and null values for {string}")
    public void iSendApiCallToUpdatePrimaryAddressInfoWithValidIDNullValues(String value) {
        String body = "";
        switch (value) {

            case "address":
                body = "{\n" +
                        "    \"caseId\": \"57\",\n" +
                        "    \"caseContacts\": {\n" +
                        "        \"address\": {\n" +
                        "            \"addressStreet1\": null,\n" +
                        "            \"addressStreet2\": null,\n" +
                        "            \"addressCity\": \"Name2\",\n" +
                        "            \"addressState\": \"Name2\",\n" +
                        "            \"addressCounty\": \"Name2\",\n" +
                        "            \"addressCountyCode\": null,\n" +
                        "            \"addressZip\": \"Name2\",\n" +
                        "            \"addressZipFour\": null\n" +
                        "        },\n" +
                        "            \"phone\": {\n" +
                        "            \"phoneNumber\": 2432462346\n" +
                        "                    },\n" +
                        "        \"email\": {\n" +
                        "            \"emailAddress\": \"test@gmail.com\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
                break;
            case "phone":
                body = "{\n" +
                        "    \"caseId\": \"57\",\n" +
                        "    \"caseContacts\": {\n" +
                        "        \"address\": {\n" +
                        "            \"addressStreet1\": \"Neka;sf\",\n" +
                        "            \"addressStreet2\": null,\n" +
                        "            \"addressCity\": \"Name2\",\n" +
                        "            \"addressState\": \"Name2\",\n" +
                        "            \"addressCounty\": \"Name2\",\n" +
                        "            \"addressCountyCode\": null,\n" +
                        "            \"addressZip\": \"Name2\",\n" +
                        "            \"addressZipFour\": null\n" +
                        "        },\n" +
                        "            \"phone\": {\n" +
                        "            \"phoneNumber\": null\n" +
                        "                    },\n" +
                        "        \"email\": {\n" +
                        "            \"emailAddress\": \"test@gmail.com\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
                break;
            case "email":
                body = "{\n" +
                        "    \"caseId\": \"57\",\n" +
                        "    \"caseContacts\": {\n" +
                        "        \"address\": {\n" +
                        "            \"addressStreet1\": \"Neka;sf\",\n" +
                        "            \"addressStreet2\": null,\n" +
                        "            \"addressCity\": \"Name2\",\n" +
                        "            \"addressState\": \"Name2\",\n" +
                        "            \"addressCounty\": \"Name2\",\n" +
                        "            \"addressCountyCode\": null,\n" +
                        "            \"addressZip\": \"Name2\",\n" +
                        "            \"addressZipFour\": null\n" +
                        "        },\n" +
                        "            \"phone\": {\n" +
                        "            \"phoneNumber\": 2341234\n" +
                        "                    },\n" +
                        "        \"email\": {\n" +
                        "            \"emailAddress\": null\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
                break;
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/primaryContactAddressUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        //api.putApiRequest(api_common.updateJson(json, data));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(body);
    }

    @Then("I send api call to Update Primary Address info With Valid ID and invalid data")
    public void iSendApiCallToUpdatePrimaryAddressInfoWithValidIDAndInvalid_data(Map<String, String> data) {
        //primaryCaseId = api_common.asInt("c.object.result[0].cases.caseId");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/primaryContactAddressUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("I validate record saved with {string} as null")
    public void i_validate_record_saved_with_as_null(String field) {
        String actualValue = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.object.result[0].consumers[0].consumer" + field);
        System.out.println("Actual value of " + field + " = " + actualValue);
        assertNull(actualValue, field + " is not null");
    }

    @Then("I verify consumer project fields details")
    public void verifyNewFields() {
        JsonObject reqConsumerObj = APIConsumerPopulationDmnController.caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject();

        if (reqConsumerObj.entrySet().contains("maritalCode"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("projectFields").get(0)
                    .getAsJsonObject().get("maritalCode").getAsString(), reqConsumerObj.get("maritalCode").getAsString());
        if (reqConsumerObj.entrySet().contains("medicallyFrailConfirmationCode"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("projectFields").get(0)
                    .getAsJsonObject().get("medicallyFrailConfirmationCode").getAsString(), reqConsumerObj.get("medicallyFrailConfirmationCode").getAsString());
        if (reqConsumerObj.entrySet().contains("pregnancyStartDate"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("projectFields").get(0)
                    .getAsJsonObject().get("pregnancyStartDate").getAsString(), reqConsumerObj.get("pregnancyStartDate").getAsString());
        if (reqConsumerObj.entrySet().contains("pregnancyEndDate"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("projectFields").get(0)
                    .getAsJsonObject().get("pregnancyEndDate").getAsString(), reqConsumerObj.get("pregnancyEndDate").getAsString());
        if (reqConsumerObj.entrySet().contains("recipientCountyWard"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("projectFields").get(0)
                    .getAsJsonObject().get("recipientCountyWard").getAsString(), reqConsumerObj.get("recipientCountyWard").getAsString());
        if (reqConsumerObj.entrySet().contains("recipientWardType"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("projectFields").get(0)
                    .getAsJsonObject().get("recipientWardType").getAsString(), reqConsumerObj.get("recipientWardType").getAsString());
        if (reqConsumerObj.entrySet().contains("pmpPaper"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("projectFields").get(0)
                    .getAsJsonObject().get("pmpPaper").getAsString(), reqConsumerObj.get("pmpPaper").getAsString());

        if (reqConsumerObj.entrySet().contains("spouseExternalConsumerId"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("projectFields").get(0)
                    .getAsJsonObject().get("spouseExternalConsumerId").getAsString(), reqConsumerObj.get("spouseExternalConsumerId").getAsString());
        if (reqConsumerObj.entrySet().contains("medicaidIdActiveIndicator"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber").get(0)
                    .getAsJsonObject().get("medicaidIdActiveIndicator").getAsString(), reqConsumerObj.get("medicaidIdActiveIndicator").getAsString());
    }


    @Then("I verify the response that is returned without County Code does not have error")
    public void I_verify_the_response_that_is_returned_without_County_Code_does_not_have_error(Map<String, String> data) {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        AddressEffectiveStartDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.effectiveStartDate"));
        String AddressformatedEffectiveStartDate = AddressEffectiveStartDate.get().substring(0, 19).replaceAll("T", " ");
        System.out.println("Current GMT time is : -- " + df.format(date));
        assertTrue(AddressformatedEffectiveStartDate.compareTo(df.format(date)) >= -2 && AddressformatedEffectiveStartDate.compareTo(df.format(date)) <= 2, "NOT matching time");

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressStreet1"), data.get("caseContacts.address.addressStreet1"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressStreet2"), data.get("caseContacts.address.addressStreet2"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressCity"), data.get("caseContacts.address.addressCity"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressState"), data.get("caseContacts.address.addressState"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressZip"), data.get("caseContacts.address.addressZip"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.addressZipFour"), data.get("caseContacts.address.addressZipFour"));

    }

    @Then("I verify record has been saved and response does not have an error")
    public void I_verify_record_has_been_saved_and_response_does_not_have_an_error() {
        String actualStartDateAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("caseContacts.address.effectiveStartDate");
        assertTrue(actualStartDateAddress.substring(0, 19).compareTo(AddressEffectiveStartDate.get().substring(0, 19)) <= 1 && actualStartDateAddress.substring(0, 19).compareTo(AddressEffectiveStartDate.get().substring(0, 19)) >= -1, "Time for Address mismatch");

    }

    @And("I verify response contains middle {string} name and suffix {string}")
    public void iVerifyresponse_contains_middle_name_and_suffix(String middle, String suffix) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].middleName"), middle);
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].suffix"), suffix);
    }

    @And("I verify case correspondence response returns external Case Id for {string}")
    public void iVerifyresponse_returns_external_case_id_for(String payloadName) {
        String actualExternalCaseId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalIds[0].id");
        String actualExternalCaseIdType = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalIds[0].type");
        assertTrue(actualExternalCaseIdType.equals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(payloadName + ".consumerRequests[0].case.type")));
        assertTrue(actualExternalCaseId.equals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(payloadName + ".consumerRequests[0].case.externalId")));

    }

    @And("I verify case correspondence response returns external Consumer Id for {string}")
    public void iVerifyresponse_returns_external_consumer_id_for(String payloadName) {
        String actualExternalConsumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].externalIds[0].id");
        String actualExternalConsumerIdType = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].externalIds[0].type");
        assertTrue(actualExternalConsumerId.equals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(payloadName + ".consumerRequests[0].consumerProfile.externalId")));
        assertTrue(actualExternalConsumerIdType.equals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(payloadName + ".consumerRequests[0].consumerProfile.type")));
    }

    @And("I verify case correspondence response returns name with Parent Guardian prefix {string}")
    public void iVerifyresponse_returns_name_with_parent_guardian_prefix(String payloadName) {
        String expectedPrefix = "Parent /Guardian of ";
        String actualFirstName = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].firstName");
        assertTrue(actualFirstName.equals(expectedPrefix + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(payloadName + ".consumerRequests[0].firstName")));
    }

    @And("I verify case correspondence does not return name with Parent Guardian prefix {string}")
    public void iVerifyresponse_does_not_return_name_with_parent_guardian_prefix(String payloadName) {
        String actualFirstName = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.consumers[0].firstName");
        assertTrue(actualFirstName.equals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(payloadName + ".consumerRequests[0].firstName")));
    }
    @And("I verify casecorrespondence response data")
    public void iVerifyChannelIsUsable(Map<String, String > data) {

        for (String each : data.keySet())
            if(data.get(each).contains("null")) {
                assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(each), "Not null actual value == "+API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(each));
            }else{
                if(each.contains("address")){
                    assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(each).contains("ConsumerRep address ct"), "actual addres line 1 == " + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(each));
                }else assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(each).contains(data.get(each)), "actual field value = "+API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(each));

            }
    }
}
//api_common.asStr("c.consumers[0].channels.mail.usability.usableFlag")