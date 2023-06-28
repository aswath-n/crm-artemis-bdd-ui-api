package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

//import static com.maersk.crm.utilities.APIClassUtil.getApiProjectNameThreadLocal();
import static org.testng.Assert.*;

public class APICaseLoaderEligibilityEnrollmentController extends CRMUtilities implements ApiBase {

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String caseLoaderBaseURIForDC = ConfigurationReader.getProperty("apiCaseLoaderURIForDC");
    private String syncContactInfoBaseURIForDC = ConfigurationReader.getProperty("apiUpdateContactRecordURIForDC");
    private String createCaseWithEligibilityEnrollment = "app/crm/caseloader";
    private String createCaseWithEligibilityEnrollmentForDC = "app/crm/consumers";
    private String syncContactInfoEndpointForDC = "mars/crm/data/sync/contacts";
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> caseIdentificationNo = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> externalConsumerId = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> scenarioName = ThreadLocal.withInitial(() -> null);
    private String createCaseWithConsumers = "app/crm/caseloader";
    private String createNote = "app/crm/note";
    private String createNotes = "app/crm/notes";
    private String caseNoteBaseURI = ConfigurationReader.getProperty("digitalPrimaryContactBaseURI");
    private final ThreadLocal<String> consumerDateOfBirth = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> firstExternalConsumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> phoneNumber = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> phoneType = ThreadLocal.withInitial(String::new);

    @Given("I initiated Case Loader API for eligibility and enrollment")
    public void initiatedCaseLoaderEligibilityEnrollmentAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithEligibilityEnrollment);
    }

    public void initiatedCaseLoaderEligibilityEnrollmentAPIForDC() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURIForDC);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithEligibilityEnrollmentForDC);
    }

    public void initiateSyncContactInfoForDC() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(syncContactInfoBaseURIForDC);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(syncContactInfoEndpointForDC);
    }

    public JsonArray prepareSyncContactInfoForDC() {
        System.out.println("APITDU BEFORE: " + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiUpdateContactRecordDC.json");
        System.out.println("APITDU AFTER: " + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String uiid = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonObject().addProperty("uiid", uiid);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("addresses").get(1).getAsJsonObject().addProperty("uiid", uiid);
//        apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("addresse").addProperty("uiid", uiid);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("addresses").get(1).getAsJsonObject().addProperty("correlationId", correlationId);
//        apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("address").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("email").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("phone").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("externalRefId", getConsumerIdFromCaseLoaderRun());
        System.out.println("APITDU AFTER2: " + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray();

    }

    public JsonArray prepareSyncContactInfoForDCWithoutAddress() {
        System.out.println("APITDU BEFORE: " + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/contactrecords/apiUpdateContactRecordDCWithoutAddress.json");
        System.out.println("APITDU AFTER: " + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String uiid = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("email").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("phone").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("externalRefId", getConsumerIdFromCaseLoaderRun());
        System.out.println("APITDU AFTER2: " + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray();
    }

    public JsonObject runSyncContactInfoForDC() {
        String reqParam = prepareSyncContactInfoForDC().toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(reqParam);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    public JsonObject runSyncContactInfoForDCwithoutAddress() {
        String reqParam = prepareSyncContactInfoForDCWithoutAddress().toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(reqParam);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    @When("I run Case Loader API accepts eligibility and enrollment information in request")
    public JsonObject runCaseLoaderEligibilityEnrollmentAPI() {
        if (requestParams.get() == null)
            requestParams.set(generateCaseLoaderRequest());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    public JsonObject runCaseLoaderEligibilityEnrollmentAPIForDC() {
        if (requestParams.get() == null)
            requestParams.set(generateCaseLoaderRequestForDC());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    @Then("I will get success response indicating input JSON was acceptable")
    public void checkStatusCodeCaseLoaderEligibilityEnrollmentAPI() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    public JsonObject generateCaseLoaderRequest() {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE.json");

        if (scenarioName.get() == null || !(scenarioName.get() != null && scenarioName.get().equalsIgnoreCase(Hooks.scenarioName.get()))) {
            caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
            scenarioName.set(Hooks.scenarioName.get());
        }

        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        consumerFirstName.set("Meg".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("Tea".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        externalConsumerId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);

        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date todayDate = new Date();
        String currentDate = dateForm.format(todayDate);

        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        currentDate = dateTime.format(formatter);


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equals("IN-EB")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                    .getAsJsonArray("caseLoaderRequest")
                    .get(0).getAsJsonObject().getAsJsonObject("case")
                    .addProperty("caseIdentificationNumberType", "State");
        }
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();

    }

    public JsonObject generateCaseLoaderRequestForDC() {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE_DC.json");

        if (scenarioName.get() == null || !(scenarioName.get() != null && scenarioName.get().equalsIgnoreCase(Hooks.scenarioName.get()))) {
            caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);
            scenarioName.set(Hooks.scenarioName.get());
        }

        consumerFirstName.set("Meg".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("Tea".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        externalConsumerId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerRequests").get(0).getAsJsonObject().addProperty("firstName", consumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerRequests").get(0).getAsJsonObject().addProperty("lastName", consumerLastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerRequests").get(0).getAsJsonObject().addProperty("ssn", consumerSSN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerRequests").get(0).getAsJsonObject().getAsJsonObject("consumerProfile").addProperty("externalId", externalConsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerRequests").get(0).getAsJsonObject().getAsJsonObject("case").addProperty("externalId", caseIdentificationNo.get());
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();

    }


    public String getConsumerIdFromCaseLoaderRun() {
        String result = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].profileId").toString();
        System.out.println("Consumer ID : " + result);
        return result;
    }

    public String getCaseIdFromCaseLoaderRun() {
        String result = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].caseId").toString();
        System.out.println("Case ID : " + result);
        return result;
    }

    public JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName, String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
        System.out.println("caseIdentificationNo " + caseIdentificationNo.get());
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);
        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDateOfBirth);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equals("IN-EB")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                    .getAsJsonArray("caseLoaderRequest")
                    .get(0).getAsJsonObject().getAsJsonObject("case")
                    .addProperty("caseIdentificationNumberType", "State");
        }
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public void updateCaseLoaderRequestBody() {
        JsonObject caseLoader = generateCaseLoaderRequest();
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public String getConsumerSSN() {
        return consumerSSN.get();
    }

    public void setRequestParams(JsonObject requestParams) {
        this.requestParams.set(requestParams);
    }

    public void createCase() {
        initiatedCaseLoaderEligibilityEnrollmentAPI();
        runCaseLoaderEligibilityEnrollmentAPI();
        checkStatusCodeCaseLoaderEligibilityEnrollmentAPI();

    }

    public String getConsumerFirstName() {
        return consumerFirstName.get();
    }

    public String getConsumerLastName() {
        return consumerLastName.get();
    }

    public String getExternalConsumerId() {
        return externalConsumerId.get();
    }

    public String getFirstExternalConsumerId() {
        return firstExternalConsumerId.get();
    }

    public String getCaseIdentificationNo() {
        return caseIdentificationNo.get();
    }

    public String getconsumerDateOfBirth() {
        return consumerDateOfBirth.get();
    }

    private void search_case_by_2(JsonObject requestParams, String item, String value) {
        // -- Make consumer search by specific property. --
        switch (item) {
            case "consumerFirstName":
                if (value.isEmpty())
                    value = consumerFirstName.get();
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
                    value = consumerLastName.get();
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerLastName", value);
                break;
            case "consumerMiddleName":
                requestParams.addProperty("consumerMiddleName", value);
                break;
            case "consumerSSN":
                if (value.isEmpty())
                    value = consumerSSN.get();
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerSSN", value);
                break;

        }

    }

    @Then("I can Search Case by {string}")
    public void iCanSearchCaseBy(String arg0) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        search_case_by_2(requestParams.get(), arg0, "");
    }

    public JsonObject generateCaseLoaderRequest(String caseId, String consumerRole, String consumerFirstName, String consumerLastName, String consumerDateOfBirth) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE.json");

        if (scenarioName.get() == null || !(scenarioName.get() != null && scenarioName.get().equalsIgnoreCase(Hooks.scenarioName.get()))) {
            if (caseId.length() > 0 || caseId == null) {
                caseIdentificationNo.set(caseId);
            } else {
                caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
                scenarioName.set(Hooks.scenarioName.get());
            }
        }
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        //  consumerFirstName = "Fn".concat(apitdu.getRandomString(5).randomString);
        //  consumerLastName = "Ln".concat(apitdu.getRandomString(5).randomString);
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        externalConsumerId.set("x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber));
        this.consumerDateOfBirth.set(consumerDateOfBirth);
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date todayDate = new Date();
        String currentDate = dateForm.format(todayDate);

        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        currentDate = dateTime.format(formatter);


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDateOfBirth);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", consumerRole);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equals("IN-EB")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                    .getAsJsonArray("caseLoaderRequest")
                    .get(0).getAsJsonObject().getAsJsonObject("case")
                    .addProperty("caseIdentificationNumberType", "State");
        }
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();

    }

    @And("I create a case in INEB using Case Loader API with data")
    public void createCaseInINEB(Map<String, String> data) {
        System.out.println(data.toString());
        String str = null;
        consumerFirstName.set("Fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("Ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerDateOfBirth.set(data.get("Date of Birth"));
        firstExternalConsumerId.set("x" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++" + firstExternalConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest(data.get("caseID"), data.get("case role"), consumerFirstName.get(), consumerLastName.get(), data.get("Date of Birth")));
        // requestParams = caseLoaderRequest.generateCaseLoaderRequestWithSpecifiedParametersForINEB("Member", "Name", "lastname","245362738", "2009-05-08", "Male","spouse");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", firstExternalConsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerConsent", str);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("contacts", str);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(new APICaseLoaderEligibilityEnrollmentController().getCaseIdentificationNo());
    }

    @When("I add new member older then PI for this case")
    public void i_add_new_member_older_then_PI_for_this_case(Map<String, String> data) {
        if (!data.get("First Name").equals("RandomName")) {
            requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest(getCaseIdentificationNo(), data.get("case role"), data.get("First Name"), data.get("First Name"), data.get("Date of Birth")));
        } else {
            requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest(getRandomString(6), data.get("case role"), getRandomString(7), getRandomString(7), data.get("Date of Birth")));

        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response string");
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I send request for case note API using following data")
    public void sendRequestForCaseNoteAPI(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseNoteBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createNote);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCaseNote.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", data.get("caseId"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", data.get("consumerId"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("noteText", data.get("noteText"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", data.get("createdBy"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", data.get("updatedBy"));

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify response contains following data")
    public void verifyResponse(Map<String, String> data) {

        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(temp.getString("status"), data.get("status"), "status is failed");
        Integer caseId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[0].caseId");
        Integer consumerId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[0].consumerId");
        String noteText = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[0].noteText");
        Integer updatedBy = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[0].updatedBy");
        Integer createdBy = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[0].createdBy");
        if (data.get("caseId").equals("null")) {
            assertNull(caseId, "caseId not NULL");
        } else {
            assertEquals(caseId.toString(), data.get("caseId"), "no case ID");
        }
        assertEquals(consumerId.toString(), data.get("consumerId"), "no consumer ID");
        assertEquals(createdBy.toString(), data.get("createdBy"));
        assertEquals(updatedBy.toString(), data.get("updatedBy"));
        assertEquals(noteText, data.get("noteText"), "text doest match");
    }

    @When("I send request for cases note API using following data to get notes")
    public void sendRequestForCaseNotesAPI(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseNoteBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createNotes);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCaseNote_ss.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", data.get("consumerId"));
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I should get response with all notes")
    public void verifyResponseNotes(Map<String, String> data) {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        ArrayList<JSONObject> listOfNotes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes");
        assertEquals(temp.getString("status"), data.get("status"), "status is failed");
        Integer caseId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[" + (listOfNotes.size() - 1) + "].caseId");
        Integer consumerId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[" + (listOfNotes.size() - 1) + "].consumerId");
        String noteText = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[" + (listOfNotes.size() - 1) + "].noteText");
        Integer updatedBy = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[" + (listOfNotes.size() - 1) + "].updatedBy");
        Integer createdBy = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("notes[" + (listOfNotes.size() - 1) + "].createdBy");
        assertEquals(consumerId.toString(), data.get("consumerId"), "no consumer ID");
        assertEquals(createdBy.toString(), data.get("createdBy"));
        assertEquals(updatedBy.toString(), data.get("updatedBy"));
        assertEquals(noteText, data.get("noteText"), "text doest match");
    }

    @When("I send a request to get note from case level")
    public void sendPostRequestToGetNoteFromCase(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseNoteBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createNotes);
        JsonObject json = new JsonObject();
        String str = data.keySet().toString().substring(1, data.keySet().toString().length() - 1);
        json.addProperty(str, data.get("caseId"));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(json);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I update existing member and populate DoD {string}")
    public void updateExistingMemberOnCase(String DoD) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date todayDate = new Date();
        String currentDate = dateForm.format(todayDate);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest(getCaseIdentificationNo(), "Member", consumerFirstName.get(), consumerLastName.get(), getconsumerDateOfBirth()));
        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        currentDate = dateTime.format(formatter);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfDeath", DoD);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfDeathNotifiedDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfDeathNotifiedBy", "Anonymous");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", getFirstExternalConsumerId());

        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I create a case whith cunsumerConsent null in INEB using Case Loader")
    public void createCaseINEBconsumerConsentNull() {
        caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().getRandomNumber(6));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/caseLoaderRequestINEB");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().getRandomString(8));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().getRandomNumber(9));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I create a case in INEB with address source {string}")
    public void createCaseWithaddressSource(String addressSource) {
        consumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        consumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN().substring(0, 4));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
        String randomDateOfB = "19" + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().getRandomNumber(2) + "-02-02";
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest(caseIdentificationNo.get(), "Member", consumerFirstName.get(), consumerLastName.get(), randomDateOfB));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("address").addProperty("addressSource", addressSource);
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I create a case in INEB with phone source {string}")
    public void i_create_a_case_in_INEB_with_phone_source(String phoneSource) {
        consumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        consumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN().substring(0, 4));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
        String randomDateOfB = "19" + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().getRandomNumber(2) + "-02-02";
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest(caseIdentificationNo.get(), "Member", consumerFirstName.get(), consumerLastName.get(), randomDateOfB));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("phone").addProperty("phoneSource", phoneSource);
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I will update address source for this consumer to {string}")
    public void updateConsumerAddressSource(String addressSource) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("address").addProperty("addressSource", addressSource);
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I will update phone source for this consumer to {string}")
    public void i_will_update_phone_source_for_this_consumer_to(String phoneSource) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("phone").addProperty("phoneSource", phoneSource);
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I create a case consumer in INEB with phone number {string} type {string}")
    public void createCasePhone(String phoneNumber, String phoneType) {
        this.phoneNumber.set(phoneNumber);
        this.phoneType.set(phoneType);
        consumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        consumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN().substring(0, 4));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
        String randomDateOfB = "19" + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().getRandomNumber(2) + "-02-02";
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest(caseIdentificationNo.get(), "Member", consumerFirstName.get(), consumerLastName.get(), randomDateOfB));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("phone").addProperty("phoneNumber", phoneNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("phone").addProperty("phoneType", phoneType);
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I will update phone record for this consumer with {string} {string}")
    public void updateConsumerPhoneRecord(String actionType, String changeData) {
        if (actionType.contains("different number")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                    .getAsJsonObject().getAsJsonObject("phone").addProperty("phoneNumber", changeData);
            phoneNumber.set(changeData);
            System.out.println(requestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
            System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        } else if (changeData.contains("null")) {
            String nullString = null;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                    .getAsJsonObject().getAsJsonObject("phone").addProperty("phoneNumber", nullString);
            phoneNumber.set(changeData);
            System.out.println(requestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
            System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        } else {
            System.out.println(requestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
            System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
    }

    @And("I send API call for searching the Consumer With first name and Last name")
    public void iSearchingTheConsumerWithFirstAndLastName() {
        Map<String, String> data = new HashMap<>();
        data.put("consumerLastName", consumerLastName.get());
        data.put("consumerFirstName", consumerFirstName.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/empty.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);

        for (int i = 0; i < 30; i++) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status").equalsIgnoreCase("success")) {
                System.out.println("System get call from " + (i + 1) + " attempts");
                break;
            }
        }
        System.out.println("Search results = " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());

    }

    @Then("I will verify {string}")
    public void i_will_verify(String actionType) {
        int sizeOfPhones = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonObject("object").getAsJsonArray("result").get(0).getAsJsonObject().getAsJsonArray("contacts").get(0).getAsJsonObject().getAsJsonArray("phones").size();
        if (actionType.contains("new phone record not created")) {
            assertEquals(sizeOfPhones, 1, "phones records more then 1");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].phones[0].phoneNumber"), phoneNumber.get(), "Phone number not match");
        } else if (actionType.contains("new phone record got created ")) {
            String endDate = null;
            assertEquals(sizeOfPhones, 2, "phones records more then or less 2");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].phones[0].effectiveEndDate"), endDate, "effectiveEndDate not null");
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].phones[0].phoneNumber"), phoneNumber.get(), "Phone number not match");
        } else if (actionType.contains("null")) {
            String nullText = null;
            assertEquals(sizeOfPhones, 1, "phones records more then 1");
            assertNotEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].phones[0].phoneNumber"), nullText, "Phone number is null");
        } else if (actionType.contains("Updated On date")) {
            Clock clock = Clock.system(ZoneId.of("GMT"));
            ZonedDateTime now = ZonedDateTime.now(clock);
            String actualDateAndTime = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].phones[0].updatedOn");
            actualDateAndTime = actualDateAndTime.substring(0, 19);
            String expectedDateAndTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(now);
            expectedDateAndTime = expectedDateAndTime.substring(0, 19);
            System.out.println(actualDateAndTime + "==================================" + expectedDateAndTime);
            assertNotEquals(actualDateAndTime, expectedDateAndTime, "updateOn time mismatch");
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].phones[0].phoneType"), phoneType.get(), "phoneType number not match");

    }

    @When("I will update {string} phone record for this consumer")
    public void uodatePhoneRecordWithSameData(String changeType) {
        if (changeType.contains("phoneSource")) {
            String nullString = null;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                    .getAsJsonObject().getAsJsonObject("phone").addProperty("phoneNumber", "Consumer Reported");
            System.out.println(requestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
            System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
    }

    @And("I add same consumer to new case")
    public void I_add_same_consumer_to_new_case() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++" + firstExternalConsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get() + 2);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(new APICaseLoaderEligibilityEnrollmentController().getCaseIdentificationNo());
    }

    public void resetScenarioName() {
        scenarioName.set(null);
    }

    public JsonObject generateCaseLoaderVersionforCore() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE.json");
        if (scenarioName.get() == null || !(scenarioName.get() != null && scenarioName.get().equalsIgnoreCase(Hooks.scenarioName.get()))) {
            caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
            scenarioName.set(Hooks.scenarioName.get());
        }
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        consumerFirstName.set(RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomAlphabetic(7).toLowerCase());
        consumerLastName.set(RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomAlphabetic(7).toLowerCase());
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        externalConsumerId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date todayDate = new Date();
        String currentDate = dateForm.format(todayDate);
        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        currentDate = dateTime.format(formatter);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equals("IN-EB")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                    .getAsJsonArray("caseLoaderRequest")
                    .get(0).getAsJsonObject().getAsJsonObject("case")
                    .addProperty("caseIdentificationNumberType", "State");
        }
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }
}