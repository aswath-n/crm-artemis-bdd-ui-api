package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.maersk.crm.step_definitions.CRMOptInOutDemographicMember;
import com.maersk.crm.step_definitions.CRM_CaseConsumerProfileSearchStepDef;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static com.maersk.crm.utilities.World.createNewWorld;
import static com.maersk.crm.utilities.World.getWorld;
import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

public class APIConsumerRestController extends CRMUtilities implements ApiBase {
    public static final ThreadLocal<String> addressType = ThreadLocal.withInitial(String::new);
    private String baseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String createConsumerEndPoint = "app/crm/consumer";
    private String searchConsumerEndPoint = "app/crm/case/consumers";
    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String getEvents = "app/crm/events?size=10000&page=0";
    private String createCaseMemberEndPoint = "app/crm/casemember";
    private String getCaseMemberEndPoint = "app/crm/casemember/{caseId}";
    private final ThreadLocal<JsonObject> consumerRequestParams = new ThreadLocal<>();
    private final ThreadLocal<JsonObject> caseMemberRequestParams = new ThreadLocal<>();
    private final ThreadLocal<JsonObject> requestParamsForEvent = new ThreadLocal<>();
    private String createConsumerV1 = "app/crm/consumer";
    private String newEventsAPI = ConfigurationReader.getProperty("getEventAPI");
    private String getEventsByCorrelationID = "/correlation/";
    final ThreadLocal<JSONObject> payloadObject = ThreadLocal.withInitial(JSONObject::new);
    final ThreadLocal<JSONObject> adressPayloadObject = ThreadLocal.withInitial(JSONObject::new);

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> apiconsumerFirstName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiconsumerLastName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiconsumerSSN = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiconsumerId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> consumerDetail = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> caseId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> uiid = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> consumerSsn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> consumerDob = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiconsumerExternalId = ThreadLocal.withInitial(String::new);
    private List<HashMap<String, String>> caseMembersData = new ArrayList<HashMap<String, String>>();
    public static final ThreadLocal<String> addressLine1 = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> addressLine2 = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> addressCity = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> addressState = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> apiaddressZip = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> effectiveStartDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> apiphoneNumber = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> phoneType = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> source = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> emailAddress = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> emailType = ThreadLocal.withInitial(String::new);

    private CRMOptInOutDemographicMember crmOptInOutDemographicMember = new CRMOptInOutDemographicMember();

    public static void setCorrelationId(String correlationId) {
        APIConsumerRestController.correlationId.set(correlationId);
    }

    public static void setAddressLine1(String addressLine1) {
        APIConsumerRestController.addressLine1.set(addressLine1);
    }

    public static void setAddressLine2(String addressLine2) {
        APIConsumerRestController.addressLine2.set(addressLine2);
    }

    public static void setAddressCity(String addressCity) {
        APIConsumerRestController.addressCity.set(addressCity);
    }

    public static void setAddressState(String addressState) {
        APIConsumerRestController.addressState.set(addressState);
    }

    public static void setAddressType(String addressType) {
        APIConsumerRestController.addressType.set(addressType);
    }

    public static void setAddressCounty(String addressCounty) {
        APIConsumerRestController.addressCounty.set(addressCounty);
    }

    public static void setApiaddressZip(String apiaddressZip) {
        APIConsumerRestController.apiaddressZip.set(apiaddressZip);
    }

    public static void setEffectiveStartDate(String effectiveStartDate) {
        APIConsumerRestController.effectiveStartDate.set(effectiveStartDate);
    }

    public static void setApiphoneNumber(String apiphoneNumber) {
        APIConsumerRestController.apiphoneNumber.set(apiphoneNumber);
    }

    public static void setPhoneType(String phoneType) {
        APIConsumerRestController.phoneType.set(phoneType);
    }

    @Given("I initiated Consumer Search API")
    public void i_initiated_consumer_search_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchConsumerEndPoint);
    }

    public static void setEmailAddress(String emailAddress) {
        APIConsumerRestController.emailAddress.set(emailAddress);
    }

    public static void setEmailType(String emailType) {
        APIConsumerRestController.emailType.set(emailType);
    }

    @Given("I initiated Create Consumer API")
    public void i_initiated_create_consumer_api() {
        //projectRestCntrl.initiateApigeeApi();
        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        //projectRestCntrl.defineApigeeInfomration();
        //projectRestCntrl.runInitiateApigee();
        //projectRestCntrl.verifyUserId();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerEndPoint);
    }


    @When("I can provide consumer information with {string} {string} {string} and {string}")
    public void i_can_provide_consumer_information_with(String consumerFirstName, String consumerLastName, String phoneNumber, String addressZip) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");
        consumerDob.set(addDaysToSysDate("yyyy-MM-dd", -7000));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", consumerDob.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
        consumerSsn.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", consumerSsn.get());
        long currentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", currentDateTime);
//        ((JsonObject) apitdu.jsonElement).getAsJsonArray("consumerIdentificationNumber")
//                .get(0).getAsJsonObject().addProperty("externalConsumerId", (apitdu.generate_random_externalId()));
        if (consumerFirstName.isEmpty() || consumerFirstName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        } else {
            apiconsumerFirstName.set(consumerFirstName);
        }
        System.out.println("Consuumer First Name :" + apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        if (consumerLastName.isEmpty() || consumerLastName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        } else {
            apiconsumerLastName.set(consumerLastName);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
        System.out.println("Consuumer Last Name :" + apiconsumerLastName.get());
        if (phoneNumber.isEmpty() || phoneNumber.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
            apiphoneNumber.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        } else {
            apiphoneNumber.set(phoneNumber);
        }
        System.out.println("Phone Number :" + apiphoneNumber.get().toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", (apiphoneNumber).toString());
        if (addressZip.isEmpty() || addressZip.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5);
            apiaddressZip.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        } else {
            apiaddressZip.set(addressZip);
        }
        System.out.println("Zip Code :" + apiaddressZip.toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", (apiaddressZip).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16);
        correlationId.set((API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getProjectRestCntrlThreadLocal().userIdFromApigee);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", System.currentTimeMillis());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", apiuiid);

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(consumerRequestParams.get());
    }

    @When("I can provide consumer's information randomly")
    public void i_can_provide_consumers_information_randomly() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");
        //long currentDateTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
        apiconsumerExternalId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", apiconsumerExternalId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
        apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        // Adding Consumer Last Name randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
        apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
        apiconsumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        // Adding Consumer phone number randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
        apiphoneNumber.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("effectiveStartDate", offsetTime);
        // Adding Consumer address Zip code randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5);
        apiaddressZip.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("effectiveStartDate", offsetTime);


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16);
        correlationId.set((API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(consumerRequestParams.get());
    }

    @When("I can provide same consumer information that is created earlier")
    public void i_can_provide_same_consumer_information_that_is_created_earlier() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");
        long currentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", currentDateTime);
        // Adding Consumer First Name
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        // Adding Consumer Last Name
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
        ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", apiconsumerExternalId.get());
        // Adding Consumer phone number
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", (apiphoneNumber.get()).toString());
        // Adding Consumer address Zip code
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", (apiaddressZip.get()).toString());

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(consumerRequestParams.get());
    }

    @When("I can provide consumer address State with {string}")
    public void i_can_provide_consumer_address_state_with(String addressState) {
        i_can_provide_consumers_information_randomly();
        // Adding Consumer address state code randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressState", (addressState).toString());

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(consumerRequestParams.get());
    }

    @When("I can search consumer by the {string} and {string}")
    public void i_can_search_consumer_by_the(String consumerFirstName, String consumerLastName) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // -- Make consumer search by first and last property. --
        consumerRequestParams.get().addProperty("consumerFirstName", consumerFirstName);
        consumerRequestParams.get().addProperty("consumerLastName", consumerLastName);
        System.out.println(consumerRequestParams.get());
    }

    @When("I can search consumer by {string} with value {string}")
    public void i_can_search_consumer_by(String item, String value) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // -- Make consumer search by specific property. --
        switch (item) {
            case "consumerDateOfBirth":
                try {
                    consumerRequestParams.get().addProperty("consumerDateOfBirth", NumberFormat.getInstance().parse(value));
                    break;
                } catch (java.text.ParseException e) {
                    System.out.println("Date of Birth is not string input.");
                }
            case "consumerFirstName":
                consumerRequestParams.get().addProperty("consumerFirstName", value);
                break;
            case "consumerLastName":
                consumerRequestParams.get().addProperty("consumerLastName", value);
                break;
            case "consumerMiddleName":
                consumerRequestParams.get().addProperty("consumerMiddleName", value);
                break;
            case "consumerSSN":
                consumerRequestParams.get().addProperty("consumerSSN", value);
                break;
            case "consumerIdentificationNo":
                consumerRequestParams.get().addProperty("consumerIdentificationNo", value);
                break;
        }
        System.out.println(consumerRequestParams.get());
    }

    @When("I can search consumer by {string} with above created values {string}")
    public void i_can_search_consumer_by_with_above_created_values(String item, String value) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // -- Make consumer search by specific property. --
        value = apiconsumerFirstName.get();
        switch (item) {
            case "consumerFirstName":
                consumerRequestParams.get().addProperty("consumerFirstName", value);
                break;
            case "consumerLastName":
                value = apiconsumerLastName.get();
                consumerRequestParams.get().addProperty("consumerLastName", value);
                break;
            case "consumerSSN":
                value = apiconsumerSSN.get();
                consumerRequestParams.get().addProperty("consumerSSN", value);
                break;
        }
        System.out.println("consumerRequestParams " + consumerRequestParams.get());
    }

    @And("I run the consumer search API")
    public void i_run_the_consumer_search_api() {
        System.out.println("request params" + consumerRequestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(consumerRequestParams.get().toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        consumerId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("object.result[0].consumers[0].consumerId"));
    }

    @And("I run the caseConsumer search API")
    public void i_run_the_caseConsumer_search_api() {
        System.out.println("request params" + consumerRequestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(consumerRequestParams.get().toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        caseId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("object.result[0].cases[0].caseId"));
    }

    @And("I can run create consumer API")
    public void i_can_run_create_consumer_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
        System.out.println("response");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @And("I can run try to create duplicate consumer via API")
    public void i_can_try_to_create_duplicate_consumer_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
        System.out.println("Message is -- \n" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("message"));
        System.out.println("Message is -- \n" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errorResponse"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errorResponse").toString().contains("Consumer Exists"));
    }

    @Then("I can verify consumer consumerLastName with value {string} on API response")
    public void i_can_verify_the_consumer_last_name_search_api(String value) {
        if (value.equals("{random}")) {
            value = apiconsumerLastName.get();
        }
        System.out.println("consumerLastName : " + value);
        System.out.println("Initiating Consumer Search API...");
        i_initiated_consumer_search_api();
        System.out.println("Consumer Search API informaiton loading...");
        i_can_search_consumer_by("consumerLastName", value);
        System.out.println("Consumer Search API running...");
        i_run_the_consumer_search_api();
        //System.out.println(api.responseString);
        i_can_verify_the_consumer_search_api("consumerLastName", value);
    }

    @And("I get the consumerId from API response")
    public void get_the_consumer_id() {
        List<List> consumerListID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerId");
        //System.out.println(consumerListID);
        for (List consumerId : consumerListID) {
            apiconsumerId.set(consumerId.toString());
            break;
        }

    }

    @Then("I can verify consumer consumerId on API response")
    public void i_can_verify_consumer_unique_id() {
        i_can_verify_the_consumer_search_api("consumerId", "");
    }

    @Then("I can verify consumer {string} with value {string} on API response")
    public void i_can_verify_the_consumer_search_api(String field, String value) {
        //Object consumerDoBList = api.jsonPathEvaluator.getList("consumersList.content.consumerDateOfBirth");
        Object consumerDoBList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerDateOfBirth");
        System.out.println(consumerDoBList);
        int consumerListCount = ((List) consumerDoBList).size();
        // System.out.println(consumerListCount);
        assertTrue(consumerListCount >= 1);
        // System.out.println(api_step_definitions.responseString);
        switch (field) {
            case "consumerDateOfBirth":
                List<List> consumerListDOB = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerDateOfBirth");
                //System.out.println(consumerListDOB);
                Iterator iterdob = consumerListDOB.iterator();
                Object dob = iterdob.next();
                assertTrue(dob.equals(value));
                break;
            case "consumerFirstName":
                List<List> consumerListFN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerFirstName");
                //System.out.println(consumerListFN);
                Iterator iterfn = consumerListFN.iterator();
                Object fn = iterfn.next();
                String firstname = (fn.toString()).toLowerCase();
                System.out.println(value.toLowerCase());
                assertTrue(firstname.contains(value.toLowerCase()));
                break;
            case "consumerLastName":
                List<List> consumerListLN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerLastName");
                Iterator iterln = consumerListLN.iterator();
                Object ln = iterln.next();
                String lastname = (ln.toString()).toLowerCase();
                System.out.println(value.toLowerCase());
                assertTrue(lastname.contains(value.toLowerCase()));
                break;
            case "consumerMiddleName":
                List<List> consumerListMN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerMiddleName");
                //System.out.println(consumerListMN);
                Iterator itermn = consumerListMN.iterator();
                Object mn = itermn.next();
                String middlename = (mn.toString()).toLowerCase();
                System.out.println(value.toLowerCase());
                assertTrue(middlename.contains(value.toLowerCase()));
                break;
            case "consumerSSN":
                List<List> consumerListSSN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerSSN");
                //System.out.println(consumerListSSN);
                Iterator iterssn = consumerListSSN.iterator();
                ArrayList ssn = (ArrayList) iterssn.next();
                String ssnumber = (ssn.get(0).toString()).toLowerCase();
                System.out.println(value.toLowerCase());
                assertEquals(ssnumber, value);
                break;
            case "consumerId":
                List<List> consumerListID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerId");
                // System.out.println(consumerListID);
                Iterator consumerIdIter = consumerListID.iterator();
                Object consumerIdObj = consumerIdIter.next();
                apiconsumerId.set(consumerIdObj.toString());
                String consumerId = (consumerIdObj.toString()).toLowerCase();
                System.out.println("consumerId : " + consumerId);
                assertFalse(consumerId.isEmpty());
                break;
        }
    }

    @Then("I can verify consumer {string} with above created value {string} on API response")
    public void i_can_verify_consumer_with_above_created_value_on_API_response(String field, String value) {
        //Object consumerDoBList = api.jsonPathEvaluator.getList("consumersList.content.consumerDateOfBirth");
        Object consumerDoBList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerDateOfBirth");
        System.out.println(consumerDoBList);
        int consumerListCount = ((List) consumerDoBList).size();
        // System.out.println(consumerListCount);
        assertTrue(consumerListCount >= 1);
        // System.out.println(api_step_definitions.responseString);
        value = apiconsumerFirstName.get();
        switch (field) {
            case "consumerFirstName":
                List<List> consumerListFN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerFirstName");
                //System.out.println(consumerListFN);
                Iterator iterfn = consumerListFN.iterator();
                Object fn = iterfn.next();
                String firstname = (fn.toString()).toLowerCase();
                System.out.println(value.toLowerCase());
                assertTrue(firstname.contains(value.toLowerCase()));
                break;
            case "consumerLastName":
                value = apiconsumerLastName.get();
                List<List> consumerListLN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerLastName");
                Iterator iterln = consumerListLN.iterator();
                Object ln = iterln.next();
                String lastname = (ln.toString()).toLowerCase();
                System.out.println(value.toLowerCase());
                assertTrue(lastname.contains(value.toLowerCase()));
                break;
            case "consumerSSN":
                value = apiconsumerSSN.get();
                List<List> consumerListSSN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerSSN");
                //System.out.println(consumerListSSN);
                Iterator iterssn = consumerListSSN.iterator();
                ArrayList ssn = (ArrayList) iterssn.next();
                String ssnumber = (ssn.get(0).toString()).toLowerCase();
                System.out.println(value.toLowerCase());
                assertEquals(ssnumber, value);
                break;
        }
    }

    @Then("I can verify found consumer has communicationPreferences marked as {string}")
    public void i_can_verify_found_consumer_has_communicationPreferences_marked_as(String paperless) {
        List<String> communicationPreference = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.communicationPreferences[0].valuePairIdCommPref[0]");
        Collections.sort(communicationPreference);
        String actual = communicationPreference.get(0);
        assertEquals(paperless, actual);
    }

    @Given("I created a consumer using API")
    public void i_created_a_consumer_using_api() {
        i_initiated_create_consumer_api();
        i_can_provide_consumers_information_randomly();
        i_can_run_create_consumer_api();
        i_can_verify_the_case_search_api_response();
        get_the_consumer_id_from_response(apiconsumerFirstName.get());
        get_the_consumer_correlationId_id_from_response(apiconsumerFirstName.get());
    }

    @Given("I have created a consumer using API")
    public void createRandomConsumerAPIStep() {
        createNewWorld();
        i_initiated_create_consumer_api();
        getRandomConsumerFromWorld();
        i_can_run_create_consumer_api();
        verifyConsumerCreatedAPI();
        getWorld().contactBean.get().setConsumerID(get_the_consumer_id_from_response(getWorld().contactBean.get().getFirstName()));
    }

    @Then("I can verify on consumer search API response")
    public void i_can_verify_the_case_search_api_response() {
        i_initiated_consumer_search_api();
        System.out.println("1 search");
        i_can_search_consumer_by("consumerFirstName", apiconsumerFirstName.get());
        System.out.println("2 search");
        i_run_the_consumer_search_api();
        System.out.println("3 search");
        i_can_verify_the_consumer_search_api("consumerFirstName", apiconsumerFirstName.get());
        System.out.println("4 search");
    }

    public void verifyConsumerCreatedAPI() {
        i_initiated_consumer_search_api();
        i_can_search_consumer_by("consumerFirstName", getWorld().contactBean.get().getFirstName());
        i_run_the_consumer_search_api();
        i_can_verify_the_consumer_search_api("consumerFirstName", getWorld().contactBean.get().getFirstName());
    }

    public String get_the_consumer_correlationId_id(String consumerFirstName, String consumerLastName) {
        i_initiated_consumer_search_api();
        i_can_search_consumer_by_the(consumerFirstName, consumerLastName);
        i_run_the_consumer_search_api();
        List<List> correlationIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("consumersList.content.correlationId");
        // System.out.println(correlationIdList);
        Iterator iterfn = correlationIdList.iterator();
        Object correlationIdObj = iterfn.next();
        correlationId.set(correlationIdObj.toString());
        System.out.println(correlationId.get());
        return correlationId.get();
    }


    public String get_the_consumer_correlationId_id_from_response(String consumerFirstName) {
        i_initiated_consumer_search_api();
        i_can_search_consumer_by("consumerFirstName", consumerFirstName);
        i_run_the_consumer_search_api();
        List<List> correlationIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.correlationId");
        // System.out.println(correlationIdList);
        Iterator iterfn = correlationIdList.iterator();
        ArrayList correlationIdsArr = (ArrayList) iterfn.next();
        correlationId.set(correlationIdsArr.get(0).toString());
        System.out.println(correlationId.get());
        return correlationId.get();
    }

    public String get_the_consumer_uiid_from_response(String consumerFirstName) {
        i_can_search_consumer_by("consumerFirstName", consumerFirstName);
        i_run_the_consumer_search_api();
        List<List> uiidList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("consumersList.content.uiid");
        // System.out.println(uiidList);
        Iterator uiidIter = uiidList.iterator();
        Object uiidObj = uiidIter.next();
        uiid.set((uiidObj.toString()));
        System.out.println(uiid.get());
        return uiid.get();
    }

    public String get_the_consumer_id_from_response(String consumerFirstName) {
        i_can_search_consumer_by("consumerFirstName", consumerFirstName);
        i_run_the_consumer_search_api();
        List<List> consumerIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerId");

        /*System.out.println(consumerIdList);
        Iterator  iterId = consumerIdList.iterator();
        Object consumerIdObj = iterId.next();
        apiconsumerId = (consumerIdObj.toString());*/
        apiconsumerId.set(consumerIdList.get(0).get(0).toString());
        System.out.println(consumerIdList.get(0).get(0));
        consumerDetail.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return apiconsumerId.get();
    }

    public String get_the_consumer_id(String consumerFirstName, String consumerLastName) {
        i_initiated_consumer_search_api();
        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        i_can_search_consumer_by_the(consumerFirstName, consumerLastName);
        i_run_the_consumer_search_api();
        List<List> consumerIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerId");
        System.out.println(consumerIdList);
        Iterator iterId = consumerIdList.iterator();
        ArrayList consumerIdObj = (ArrayList) iterId.next();
        System.out.println(consumerIdObj.get(0).toString());
        apiconsumerId.set((consumerIdObj.get(0).toString()));
        System.out.println(apiconsumerId.get());
        consumerDetail.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return apiconsumerId.get();
    }

    @When("I can provide case member information with {string} {string} {string} {string} {string} {string}and {string}")
    public void i_can_provide_case_member_information_with_and(String consumerFirstName, String consumerLastName, String consumerDateOfBirth, String consumerSSN, String genderCode, String effectiveStartDate, String effectiveEndDate) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCreateCaseMember.json");
        long currentDateTime = System.currentTimeMillis();

        if (consumerFirstName.isEmpty() || consumerFirstName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        }

        if (consumerLastName.isEmpty() || consumerLastName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", getDateInMilli(consumerDateOfBirth));
        if (consumerSSN.equals("{random}") || consumerSSN.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("genderCode", genderCode);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", getDateInMilli(effectiveStartDate));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", getDateInMilli(effectiveEndDate));

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(caseMemberRequestParams.get());
    }

    @And("I can run create case member API")
    public void i_can_run_create_case_member_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(caseMemberRequestParams.get().toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I can verify case member is created on API response")
    public void i_can_verify_case_member_is_created() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");
        System.out.println(results);
        HashMap consumerType = (HashMap) results.get(1);
        ArrayList consumers = (ArrayList) consumerType.get("consumers");

        boolean memberFound = false;
        if (consumers.size() > 1) {
            for (int i = 0; i < consumers.size(); i++) {
                HashMap consumer = (HashMap) consumers.get(i);
                if (consumer.get("consumerFirstName").equals(apiconsumerFirstName.get())
                        && consumer.get("consumerLastName").equals(apiconsumerLastName.get())) {
                    memberFound = true;
                    break;
                }
            }
        }
        assertTrue(memberFound);
    }


    @Then("I verify the case member details not fetched using API")
    public void i_can__verify_case_member_details_not_fetched(DataTable dataTable) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");

        for (Map<String, String> data : dataTable.asMaps(String.class, String.class)) {
            HashMap consumerType = (HashMap) results.get(1);
            for (int i = 0; i < results.size(); i++) {
                consumerType = (HashMap) results.get(i);
                if (consumerType.get("consumerType").toString().equalsIgnoreCase(data.get("consumerType").toString()))
                    break;
            }

            String elementToBeValidated = "";
            if (data.containsKey("relationShip")) {
                elementToBeValidated = "relationShip";
            } else if (data.containsKey("consumerStatus")) {
                elementToBeValidated = "consumerStatus";
            }
            String expValue = data.get(elementToBeValidated).toString();
            ArrayList consumers = (ArrayList) consumerType.get("consumers");

            boolean memberFound = false;
            if (consumers.size() > 1) {

                for (HashMap<String, String> member : caseMembersData) {
                    for (int i = 0; i < consumers.size(); i++) {
                        HashMap consumer = (HashMap) consumers.get(i);
                        if (consumer.get("consumerFirstName").toString().equalsIgnoreCase((member.get("consumerFirstName")))
                                && consumer.get("consumerLastName").toString().equalsIgnoreCase((member.get("consumerLastName")))
                                && consumer.get(elementToBeValidated).toString().equalsIgnoreCase((member.get(elementToBeValidated)))) {
                            memberFound = true;
                            break;
                        }
                    }
                }
            }
            System.out.println(memberFound);
            System.out.println("member found for the " + elementToBeValidated + ": " + expValue);
            assertTrue(memberFound, "member found for the " + elementToBeValidated + ": " + expValue);
        }

    }

    /**
     * <Description> This method valdate the order of status for given consumer type</>
     *
     * @param consumerType
     */
    @Then("I verify case members fetched in the order of their start date for consumerType {string} using API")
    public void i_can__verify_case_member_order_latest_start_date_first(String consumerType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");

        HashMap dataConsumerType = (HashMap) results.get(0);
        for (int i = 0; i < results.size(); i++) {
            dataConsumerType = (HashMap) results.get(i);
            if (dataConsumerType.get("consumerType").toString().equalsIgnoreCase(consumerType))
                break;
        }
        ArrayList consumers = (ArrayList) dataConsumerType.get("consumers");

        boolean flag = true;
        long previousCaseMemberStartDate = 0;
        if (consumers.size() > 1) {
            for (int i = 0; i < consumers.size(); i++) {
                HashMap consumer = (HashMap) consumers.get(i);

                System.out.println(consumer.get("effectiveStartDate").toString());
                if (i == 0) {
                    previousCaseMemberStartDate = Long.parseLong(consumer.get("effectiveStartDate").toString());
                    continue;
                }
                long presentCaseMemberStartDate = Long.parseLong(consumer.get("effectiveStartDate").toString());
                if (presentCaseMemberStartDate <= previousCaseMemberStartDate) {
                    flag = true;
                    previousCaseMemberStartDate = presentCaseMemberStartDate;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        assertTrue(flag, "Case member records not fetched in the order of their start date");

    }


    /**
     * <Description> returns date in milli with added/subtracted number of days to current date</>
     *
     * @param date
     * @return
     */
    private long getDateInMilli(String date) {
        long dateValueToReturn = 0;
        int days = 0;
        boolean isDateFuture = false;
        if (date.contains("+")) {
            days = Integer.parseInt(date.split("\\+")[1]);
            date = date.split("\\+")[0];
            isDateFuture = true;
        } else if (date.contains("-")) {
            days = Integer.parseInt(date.split("-")[1]);
            date = date.split("-")[0];
        }

        if (isDateFuture) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(days);
            dateValueToReturn = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates;
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(days);
            dateValueToReturn = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates;
        }
        return dateValueToReturn;
    }

    public static long pastDates(int i) {
        Date myDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        System.out.print(cal.getTime() + " ==> ");
        cal.add(Calendar.DATE, -i);
        System.out.print(cal.getTime() + " ==> ");
        long temp = cal.getTimeInMillis();
        return temp;
    }

    public static long futureDates(int i) {
        Date myDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        System.out.print(cal.getTime() + " ==> ");
        cal.add(Calendar.DATE, +i);
        System.out.print(cal.getTime() + " ==> ");
        long temp = cal.getTimeInMillis();
        return temp;
    }

    public void createConsumerAndReturnCorrelationIDStep() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerEndPoint);
        i_can_provide_consumer_information_with("{random}", "{random}", "{random}", "{random}");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
    }


    @Given("I have a created a consumer using API with the following values:")
    public void createConsumerAPIStep(Map<String, String> dataTable) {
        i_initiated_create_consumer_api();
        getRandomConsumerFromWorld();
        i_can_run_create_consumer_api();
        i_can_verify_the_case_search_api_response();
        get_the_consumer_id_from_response(apiconsumerFirstName.get());
        get_the_consumer_correlationId_id_from_response(apiconsumerFirstName.get());
    }

    public void getRandomConsumerFromWorld() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");
        long currentDateTime = System.currentTimeMillis();
        Map<String, String> dataTable = getWorld().contactBean.get().getRandomConsumer();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", dataTable.get("firstName"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerMiddleName", dataTable.get("middleInitial"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", dataTable.get("lastName"));
        ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId()));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("genderCode", dataTable.get("gender"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", getWorld().contactBean.get().getDateOfBirthMilliseconds());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", dataTable.get("phoneNumber"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressStreet1", dataTable.get("addressLine1"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressStreet2", dataTable.get("addressLine2"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressState", dataTable.get("state"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", dataTable.get("zipCode"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressCity", dataTable.get("city"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressCounty", dataTable.get("county"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressType", dataTable.get("addressType"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", dataTable.get("ssn"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", getWorld().contactBean.get().getCorrelationID());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", dataTable.get("uniqueID"));
        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(consumerRequestParams.get());
    }

    @When("I can provide consumer ssn and date of birth with {string} and {string}")
    public void i_can_provide_consumer_ssn_dob_with(String ssn, String dob) {


        if (ssn.isEmpty() || ssn.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
            consumerSsn.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);
        }

        if (dob.isEmpty() || dob.equals("{random}")) {
            consumerDob.set(addDaysToSysDate("yyyy-MM-dd", -7000));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", consumerDob.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);
        }

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(consumerRequestParams.get());
    }

    /**
     * <b>Description</b> Adds no of dates to System Date
     *
     * @param noOfDay No. of days
     * @param format  date format
     */
    public String addDaysToSysDate(String format, int noOfDay) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, noOfDay);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @When("I create consumers with following information")
    public void provideConsumerInformation(DataTable consumerInfo) {
        for (Map<String, String> data : consumerInfo.asMaps(String.class, String.class)) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");
            long currentDateTime = System.currentTimeMillis();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", currentDateTime);
            ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                    .get(0).getAsJsonObject().addProperty("externalConsumerId", (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId()));

            if (data.containsKey("consumerFirstName")) {
                String firstName = data.get("consumerFirstName").toString();
                if (firstName.isEmpty() || firstName.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", firstName);
                }
            }

            if (data.containsKey("consumerLastName")) {
                String lastName = data.get("consumerLastName").toString();
                if (lastName.isEmpty() || lastName.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", lastName);
                }
            }

            if (data.containsKey("consumerSSN")) {
                String ssn = data.get("consumerSSN").toString();
                if (ssn.isEmpty() || ssn.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
                    apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);
                }

            }

            if (data.containsKey("consumerDateOfBirth")) {
                String dob = data.get("consumerDateOfBirth").toString();
                if (dob.isEmpty() || dob.equals("{random}")) {
                    //implement random date logic
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", dob);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", dob);
                }
            }

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16);
            correlationId.set((API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
            String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

            consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
            System.out.println(consumerRequestParams.get());

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("Consumer already exists"))
                return;
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        }

    }

    @When("I can search consumer by consumer first name as {string} consumer last name as {string} consumer ssn as {string} and consumer date of birth as {string}")
    public void prepareRequestBodyToSearchConsumer(String firstName, String lastName, String ssn, String dateOfBirth) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // -- Make consumer search by specific property. --
        consumerRequestParams.get().addProperty("consumerFirstName", firstName);
        consumerRequestParams.get().addProperty("consumerLastName", lastName);
        consumerRequestParams.get().addProperty("consumerSSN", ssn);
        consumerRequestParams.get().addProperty("consumerDateOfBirth", dateOfBirth);

        System.out.println(consumerRequestParams.get());
    }

    @Then("I verify all consumers retrieved with {string} as {string} on API response")
    public void verifyConsumerDetailsWithRespectToSearchCriteria(String field, String value) {
        Object consumerDoBList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerDateOfBirth");
        System.out.println(consumerDoBList);
        int consumerListCount = ((List) consumerDoBList).size();
        assertTrue(consumerListCount >= 1);
        switch (field) {
            case "consumerDateOfBirth":
                List<List> consumerListDOB = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerDateOfBirth");
                //System.out.println(consumerListDOB);
                Iterator iterdob = consumerListDOB.iterator();
                while (iterdob.hasNext()) {
                    String dob = iterdob.next().toString();
                    dob = dob.replaceAll("\\[", "").replaceAll("]", "");
                    dob = dob.split(",")[0];
                    assertEquals(dob, value);
                }
                break;
            case "consumerFirstName":
                List<List> consumerListFN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerFirstName");
                //System.out.println(consumerListFN);
                Iterator iterfn = consumerListFN.iterator();
                while (iterfn.hasNext()) {
                    Object fn = iterfn.next();
                    String firstname = (fn.toString()).toLowerCase();
                    firstname = firstname.replaceAll("\\[", "").replaceAll("]", "");
                    assertTrue(firstname.contains(value.toLowerCase()));
                }
                break;
            case "consumerLastName":
                List<List> consumerListLN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerLastName");
                Iterator iterln = consumerListLN.iterator();
                while (iterln.hasNext()) {
                    Object ln = iterln.next();
                    String lastname = (ln.toString()).toLowerCase();
                    lastname = lastname.replaceAll("\\[", "").replaceAll("]", "");
                    assertTrue(lastname.contains(value.toLowerCase()));
                }
                break;
            case "consumerSSN":
                List<List> consumerListSSN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerSSN");
                //System.out.println(consumerListSSN);
                Iterator iterssn = consumerListSSN.iterator();
                while (iterssn.hasNext()) {
                    Object ssn = iterssn.next();
                    String ssnumber = (ssn.toString()).toLowerCase();
                    ssnumber = ssnumber.replaceAll("\\[", "").replaceAll("]", "");
                    assertEquals(ssnumber, value);
                }
                break;

        }
    }

    @Then("I verify consumer event by searching events with correlation id")
    public void verifyEvents() {
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().initiateSearchEvents();
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().prepareRequestBodyWithCorrelationIdForSearchEvents(correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().runSearchEvent();
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().verifyCaseIdInEvents("CONSUMER_SAVE_EVENT", apiconsumerId.get().substring(1, apiconsumerId.get().length() - 1));
    }

    @When("I create consumers with following information and correspondence preference info")
    public void i_create_consumers_with_following_information_and_correspondence_preference_info(DataTable consumerInfo) {
        for (Map<String, String> data : consumerInfo.asMaps(String.class, String.class)) {
            //apitdu.getJsonFromFile("crm/consumer/apiCreateConsumer.json");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiNewestCreateConsumer.json");
            //long currentDateTime = System.currentTimeMillis();
            String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", offsetTime);
            ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                    .get(0).getAsJsonObject().addProperty("externalConsumerId", (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId()));
            if (data.containsKey("consumerFirstName")) {
                String firstName = data.get("consumerFirstName").toString();
                System.out.println("firstName " + firstName);
                if (firstName.equals("random")) {
                    System.out.println(" raaaandooom ");
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", firstName);
                }
            }

            if (data.containsKey("consumerLastName")) {
                String lastName = data.get("consumerLastName").toString();
                if (lastName.equals("random")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", lastName);
                }
            }

            if (data.containsKey("consumerSSN")) {
                String ssn = data.get("consumerSSN").toString();
                if (ssn.equals("random")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
                    apiconsumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", apiconsumerSSN.get());
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);
                }

            }

            if (data.containsKey("consumerDateOfBirth")) {
                String dob = data.get("consumerDateOfBirth").toString();
                System.out.println("dob " + dob);
                if (dob.isEmpty() || dob.equals("random")) {
                    //implement random date logic
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", dob);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", dob);
                }
            }

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16);
            correlationId.set((API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("communicationPreferences").get(0).getAsJsonObject().addProperty("valuePairIdCommPref", data.get("communicationPreferences").toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("communicationPreferences").get(0).getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("communicationPreferences").get(0).getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
            String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

            consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
            System.out.println(consumerRequestParams.get());

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
            System.out.println("api.responseString");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("Consumer already exists"))
                return;
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        }
    }

    @Then("I will verify an {string} for DPBI is created for above scenario")
    public void i_will_verify_an_for_DPBI_is_created_for_above_scenario(String string) {
        String traceId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId();
        JsonObject event = new JsonObject();
        event.addProperty("correlationId", traceId);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get().toString());
        System.out.println("api.responseString");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(apiconsumerFirstName.get()) && API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(apiconsumerFirstName.get()));
    }


    /*public APIClassUtil getApiClassUtil() {
        return api;
    }*/

    @Then("I verify error message during create new consumer with already existing consumer data except below fields")
    public void iVerifyWarningMessageDuringCreateNewConsumerWithAlreadyExistingConsumerDataExceptSpecificFields(List<Map<String, String>> data) {
        String externalConsumerId = "";

        for (Map.Entry<String, String> consumerData : data.get(0).entrySet()) {
            if (consumerData.getKey().contains("consumerSSN") && consumerData.getValue() != null) {
                consumerSsn.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
                consumerRequestParams.get().getAsJsonObject().addProperty("consumerSSN", consumerSsn.get());
            }
            if (consumerData.getKey().contains("consumerExternalID") && consumerData.getValue() != null) {
                externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);
                consumerRequestParams.get().getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
            }
            if (consumerData.getKey().contains("consumerFirstName") && consumerData.getValue() != null) {
                apiconsumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
                consumerRequestParams.get().getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
            }
            if (consumerData.getKey().contains("consumerLastName") && consumerData.getValue() != null) {
                apiconsumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
                consumerRequestParams.get().getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
            }
        }
        System.out.println("Updated consumer request-->" + consumerRequestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errorResponse").toString().contains("Consumer Exists"));
    }

    @Then("I try create new consumer with already existing consumer data except SSN, firstName, lastName, DOB, EXTN ID with MEDICAID")
    public void iVerifyWarningMessageDuringCreateNewConsumerWithAlreadyExistingConsumerDataExceptSSNFirstNameLastNameDOBWithEXTDWithMEDICAID() {
        apiconsumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        apiconsumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSsn.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        consumerDob.set(LocalDate.now().minusYears(19).toString());
        consumerRequestParams.get().getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        consumerRequestParams.get().getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
        consumerRequestParams.get().getAsJsonObject().addProperty("consumerSSN", consumerSsn.get());
        consumerRequestParams.get().getAsJsonObject().addProperty("consumerDateOfBirth", consumerDob.get());
        consumerRequestParams.get().getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().addProperty("identificationNumberType", "MEDICAID");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @And("I will verify an newly created consumer first,last name by searching on UI")
    public void iVerifyNewlyCreatedConsumerDetailsOnUI() {
        crmOptInOutDemographicMember.iVerifyNewlyCreatedConsumerDetailsSearchingOnUI(apiconsumerFirstName.get(), apiconsumerLastName.get());
    }

    @Then("I verify new consumer is not created with unexpected SSN field parameter {string}")
    public void iVerifyNewConsumerIsNotCreatedWithUnexpectedSsnFieldParameter(String ssn) {
        consumerRequestParams.get().getAsJsonObject().addProperty("consumerSSN", ssn);
        System.out.println("Updated consumer request-->" + consumerRequestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");

    }

    @When("I can provide random consumer information with SSN {string} randomly")
    public void i_can_provide_random_consumer_information_with_ssn(String ssn) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");
        //long currentDateTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
        apiconsumerExternalId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", apiconsumerExternalId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
        apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        // Adding Consumer Last Name randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
        apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
        apiconsumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);
        // Adding Consumer phone number randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
        apiphoneNumber.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("effectiveStartDate", offsetTime);
        // Adding Consumer address Zip code randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5);
        apiaddressZip.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("effectiveStartDate", offsetTime);


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16);
        correlationId.set((API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);
        consumerDob.set(addDaysToSysDate("yyyy-MM-dd", -7000));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", consumerDob.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", apiuiid);

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(consumerRequestParams.get());
    }

    @Given("I initiated Create Consumer API v1")
    public void i_initiated_create_consumer_api_V1() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerV1);
    }

    @When("Create new consumer using v1 API with following data")
    public void create_new_consumer_using_v1(Map data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/createConsumerV1Payload.json");
        //Consumer information--------------------------------------------------------------
        apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        apiconsumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN());
        correlationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        uiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        consumerSsn.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN());
        consumerDob.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateRandomDOB());
        consumerDob.set(consumerDob.get().substring(0, 4) + "-" + consumerDob.get().substring(4, 6) + "-12");
        source.set("Consumer Reported");
        //Address information-----------------------------------------------------------------
        addressLine1.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomAddress());
        addressLine2.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomAddress());
        addressCity.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        addressState.set("New York");
        addressType.set("Mailing");
        addressCounty.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        apiaddressZip.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_Zip());
        effectiveStartDate.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate("current") + "T04:00:00Z");
        //Phone information-------------------------------------------------------------------
        apiphoneNumber.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomPhoneNumber());
        phoneType.set("Cell");
        //Email information-------------------------------------------------------------------
        emailAddress.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomEmail());
        emailType.set("OFFICE");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", uiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", consumerDob.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", apiconsumerSSN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());

        if (data.get("address").toString().contains("yes")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCity", addressCity.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCounty", addressCounty.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("addressState", addressState.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("addressStreet1", addressLine1.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("addressStreet2", addressLine2.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("addressType", addressType.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("addressZip", apiaddressZip.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("effectiveStartDate", effectiveStartDate.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("address").getAsJsonObject().addProperty("addressSource", source.get());
        }
        if (data.get("phone").toString().contains("yes")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("phone").getAsJsonObject().addProperty("phoneNumber", apiphoneNumber.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("phone").getAsJsonObject().addProperty("effectiveStartDate", effectiveStartDate.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("phone").getAsJsonObject().addProperty("phoneType", phoneType.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("phone").getAsJsonObject().addProperty("phoneSource", source.get());
        }
        if (data.get("email").toString().contains("yes")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("email").getAsJsonObject().addProperty("emailSource", source.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("email").getAsJsonObject().addProperty("effectiveStartDate", effectiveStartDate.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("email").getAsJsonObject().addProperty("emailAddress", emailAddress.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contacts").getAsJsonObject().get("email").getAsJsonObject().addProperty("emailType", emailType.get());
        }

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(consumerRequestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerRequestParams.get().toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        consumerId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].consumerId"));
        CRM_CaseConsumerProfileSearchStepDef.setConsumerID(consumerId.get());
    }

    @When("I GET events by correlationID for new created consumer")
    public void get_events_by_crrID() {
        BrowserUtils.waitFor(10);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(newEventsAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEventsByCorrelationID + correlationId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @Then("I will verify event published for following events")
    public void verify_event_list(List<String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");
        ArrayList<String> listActualEventName = new ArrayList<>();
        for (int i = 0; i < json.size(); i++) {
            String actualEventName = json.get(i).getAsJsonObject().get("eventName").toString().replace("\"", "");
            listActualEventName.add(actualEventName);
        }
        for (int i = 0; i < data.size(); i++) {
            assertTrue(listActualEventName.contains(data.get(i)), data.get(i) + " not exist in the events list");
        }
    }

    @Then("I will verify consumer save event payload data")
    public void getEventPayloadForEvent() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {

            if (each.getAsJsonObject().get("eventName").toString().contains("CONSUMER_SAVE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("consumerFirstName").toString().contains(apiconsumerFirstName.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("consumerLastName").toString().contains(apiconsumerLastName.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("consumerId").toString().contains(consumerId.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").toString().contains(correlationId.get()));

            } else if (each.getAsJsonObject().get("eventName").toString().contains("CONSUMER_V2_SAVE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("firstName").toString().contains(apiconsumerFirstName.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("lastName").toString().contains(apiconsumerLastName.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("consumerId").toString().contains(consumerId.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").toString().contains(correlationId.get()));

            } else if (each.getAsJsonObject().get("eventName").toString().contains("CASE_V2_SAVE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("type").toString().contains("MEDICAID"));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("externalId").toString().contains(apiconsumerExternalId.get()));

            }
        }
    }

    @Then("I will verify address save event payload data")
    public void I_will_verify_address_save_event_payload() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("ADDRESS_SAVE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressCity").toString().contains(addressCity.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressCounty").toString().contains(addressCounty.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressState").toString().contains(addressState.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressStreet1").toString().contains(addressLine1.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressStreet2").toString().contains(addressLine2.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressType").toString().contains(addressType.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressZip").toString().contains(apiaddressZip.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("effectiveStartDate").toString().contains(effectiveStartDate.get().substring(0, 10)));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressSource").toString().contains(source.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").toString().contains(correlationId.get()));
            }
        }
    }

    @Then("I will verify phone save event payload data")
    public void I_will_verify_phone_save_event_payload() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("PHONE_SAVE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("phoneNumber").toString().contains(apiphoneNumber.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("effectiveStartDate").toString().contains(effectiveStartDate.get().substring(0, 10)));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("phoneType").toString().contains(phoneType.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("phoneSource").toString().contains(source.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").toString().contains(correlationId.get()));
            }
        }
    }

    @Then("I will verify email save event payload data")
    public void I_will_verify_email_save_event_payload() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("EMAIL_SAVE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("emailAddress").toString().contains(emailAddress.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("effectiveStartDate").toString().contains(effectiveStartDate.get().substring(0, 10)));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("emailType").toString().contains(emailType.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("emailSource").toString().contains(source.get()));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").toString().contains(correlationId.get()));
            }
        }
    }

    @Then("I will verify address update event payload contains updated {string}")
    public void I_will_verify_address_update_event_payload(String field) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("ADDRESS_UPDATE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                String actualAddressLine1 = payloadObject.get().getJSONObject("dataObject").get("addressStreet1").toString();
                String actualAddressLine2 = payloadObject.get().getJSONObject("dataObject").get("addressStreet2").toString();
                String actualCity = payloadObject.get().getJSONObject("dataObject").get("addressCity").toString();
                String actualCounty = payloadObject.get().getJSONObject("dataObject").get("addressCounty").toString();
                String actualState = payloadObject.get().getJSONObject("dataObject").get("addressState").toString();
                String actualZip = payloadObject.get().getJSONObject("dataObject").get("addressZip").toString();
                String actualType = payloadObject.get().getJSONObject("dataObject").get("addressType").toString();
                String actualStartDate = payloadObject.get().getJSONObject("dataObject").get("effectiveStartDate").toString();

                assertTrue(actualAddressLine1.equalsIgnoreCase(addressLine1.get()), actualAddressLine1 + " not equal to " + addressLine1.get());
                assertTrue(actualAddressLine2.equalsIgnoreCase(addressLine2.get()), actualAddressLine2 + " not equal to " + addressLine2.get());
                assertTrue(actualCity.equalsIgnoreCase(addressCity.get()), actualCity + " not equal to " + addressCity.get());
                assertTrue(actualCounty.equalsIgnoreCase(addressCounty.get()), actualCounty + " not equal to " + addressCounty.get());
                assertTrue(actualState.equalsIgnoreCase(addressState.get()), actualState + " not equal to " + addressState.get());
                assertTrue(actualType.equalsIgnoreCase(addressType.get()), actualType + " not equal to " + addressType.get());
                assertTrue(actualStartDate.contains(effectiveStartDate.get().substring(4, 8) + "-" + effectiveStartDate.get().substring(0, 2) + "-" + effectiveStartDate.get().substring(2, 4)), actualStartDate + " not equal to " + effectiveStartDate.get().substring(4, 8) + "-" + effectiveStartDate.get().substring(0, 2) + "-" + effectiveStartDate.get().substring(2, 4));

            }
        }
    }

    @Then("I will verify phone update event payload contains updated fields")
    public void I_will_verify_phone_update_event_payload() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("PHONE_UPDATE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                String actualphoneNumber = payloadObject.get().getJSONObject("dataObject").get("phoneNumber").toString();
                String actualphoneType = payloadObject.get().getJSONObject("dataObject").get("phoneType").toString();
                String actualStartDate = payloadObject.get().getJSONObject("dataObject").get("effectiveStartDate").toString();

                assertTrue(actualphoneNumber.equalsIgnoreCase(apiphoneNumber.get()), actualphoneNumber + " not equal to " + apiphoneNumber.get());
                assertTrue(actualphoneType.equalsIgnoreCase(phoneType.get()), actualphoneType + " not equal to " + phoneType.get());
                assertTrue(actualStartDate.contains(effectiveStartDate.get().substring(4, 8) + "-" + effectiveStartDate.get().substring(0, 2) + "-" + effectiveStartDate.get().substring(2, 4)), actualStartDate + " not equal to " + effectiveStartDate.get().substring(4, 8) + "-" + effectiveStartDate.get().substring(0, 2) + "-" + effectiveStartDate.get().substring(2, 4));

            }
        }
    }

    @Then("I will verify email update event payload contains updated fields")
    public void I_will_verify_email_update_event_payload() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("EMAIL_UPDATE_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                String actualphoneEmailAddress = payloadObject.get().getJSONObject("dataObject").get("emailAddress").toString();
                String actualemailType = payloadObject.get().getJSONObject("dataObject").get("emailType").toString();
                String actualStartDate = payloadObject.get().getJSONObject("dataObject").get("effectiveStartDate").toString();

                assertTrue(actualphoneEmailAddress.equalsIgnoreCase(emailAddress.get()), actualphoneEmailAddress + " not equal to " + apiphoneNumber.get());
                assertTrue(actualemailType.equalsIgnoreCase(emailType.get()), actualemailType + " not equal to " + phoneType.get());
                assertTrue(actualStartDate.contains(effectiveStartDate.get().substring(4, 8) + "-" + effectiveStartDate.get().substring(0, 2) + "-" + effectiveStartDate.get().substring(2, 4)), actualStartDate + " not equal to " + effectiveStartDate.get().substring(4, 8) + "-" + effectiveStartDate.get().substring(0, 2) + "-" + effectiveStartDate.get().substring(2, 4));

            }
        }
    }

    @When("I GET events by UUID using event correlation API")
    public void get_events_by_crrIDUUID() {
        BrowserUtils.waitFor(6);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(newEventsAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEventsByCorrelationID + APICreateConsumerContactController.getUUID());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events").size() == 0) {
            BrowserUtils.waitFor(5);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        }
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events").size() != 0);
    }

    @Then("I will verify consumer update response contains following data")
    public void IwillVerifyConsumerUpdateResponse(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {

            if (each.getAsJsonObject().get("eventName").toString().contains("CONSUMER_V2_UPDATE_EVENT")) {
                System.out.println(each);
                assertNotNull(each.getAsJsonObject().get("eventId"), "eventId NULL or empty");
                assertEquals("Consumer-v2", each.getAsJsonObject().get("module").toString().replaceAll("\"", ""));
                assertEquals("SUCCESS", each.getAsJsonObject().get("status").toString().replaceAll("\"", ""));
                assertEquals(APICreateConsumerContactController.getUUID(), each.getAsJsonObject().get("correlationId").toString().replaceAll("\"", ""));
                assertNotNull(each.getAsJsonObject().get("createdOn"));
                assertNotNull(each.getAsJsonObject().get("updatedOn"));
                assertEquals("3493", each.getAsJsonObject().get("createdBy").toString().replaceAll("\"", ""));
                assertEquals("3493", each.getAsJsonObject().get("updatedBy").toString().replaceAll("\"", ""));
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("firstName").toString().contains(data.get("firstName")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("lastName").toString().contains(data.get("lastName")));
            }
        }
    }

    @Then("I will verify case update event response contains following data")
    public void IwillVerifyCaseUpdateResponse(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {

            if (each.getAsJsonObject().get("eventName").toString().contains("CASE_V2_UPDATE_EVENT")) {
                System.out.println(each);
                assertNotNull(each.getAsJsonObject().get("eventId"), "eventId NULL or empty");
                assertEquals("Consumer-v2", each.getAsJsonObject().get("module").toString().replaceAll("\"", ""));
                assertEquals("SUCCESS", each.getAsJsonObject().get("status").toString().replaceAll("\"", ""));
                assertEquals(APICreateConsumerContactController.getUUID(), each.getAsJsonObject().get("correlationId").toString().replaceAll("\"", ""));
                assertNotNull(each.getAsJsonObject().get("createdOn"));
                assertNotNull(each.getAsJsonObject().get("updatedOn"));
                assertEquals("3493", each.getAsJsonObject().get("createdBy").toString().replaceAll("\"", ""));
                assertEquals("3493", each.getAsJsonObject().get("updatedBy").toString().replaceAll("\"", ""));
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                String expectedCaeExternalId = APICreateConsumerContactController.getExternalcaseId();
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("caseId"));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("externalId").toString().contains(expectedCaeExternalId));
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("type").toString().contains("MEDICAID"));
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("txnId"));
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getJSONArray("caseProfiles").getJSONObject(0).get("caseProfileId"));
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getJSONArray("caseProfiles").getJSONObject(0).get("profileId"));
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getJSONArray("caseProfiles").getJSONObject(0).get("caseId"));
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getJSONArray("caseProfiles").getJSONObject(0).get("txnId"));
            }
        }
    }

    @Then("I will verify NEW_CONSUMER_BUSINESS_EVENT payload contains following data")
    public void verify_v2_business_event_data(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains(data.get("eventName"))) {
                System.out.println("Response object ===== " + each);
                assertNotNull(each.getAsJsonObject().get("eventId"), "eventId is null, actual eventId == " + each.getAsJsonObject().get("eventId"));
                String modul = each.getAsJsonObject().get("module").toString().replaceAll("\"", "");
                String status = each.getAsJsonObject().get("status").toString().replaceAll("\"", "");
                String correlationId = each.getAsJsonObject().get("correlationId").toString().replaceAll("\"", "");
                String createdBy = each.getAsJsonObject().get("createdBy").toString().replaceAll("\"", "");
                String message = each.getAsJsonObject().get("message").toString();
                //events object meta data validation
                assertEquals(data.get("module"), modul);
                assertEquals(data.get("status"), status);
                assertEquals(APICreateConsumerContactController.getUUID(), correlationId);
                assertEquals(message, "null");
                assertNotNull(each.getAsJsonObject().get("createdOn"));
                assertEquals(data.get("createdBy"), createdBy);
                //events object nested payload validation
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                System.out.println("Response payload object ===== " + payloadObject.get().toString());
                assertEquals(data.get("projectName"), payloadObject.get().get("projectName"));
                assertEquals(data.get("action"), payloadObject.get().get("action"));
                assertEquals(data.get("recordType"), payloadObject.get().get("recordType"));
                //nested dataObject payload validation
                assertEquals(APICreateConsumerContactController.getProfileId(), payloadObject.get().getJSONObject("dataObject").get("consumerId").toString());
                String fullName = APICreateConsumerContactController.getConsumerfirstName() + " " + APICreateConsumerContactController.getConsumerlastName();
                assertEquals(fullName, payloadObject.get().getJSONObject("dataObject").get("consumerName"));
                assertEquals(data.get("dataSource"), payloadObject.get().getJSONObject("dataObject").get("dataSource"));
                assertEquals(APICreateConsumerContactController.getExternalconsumerId(), payloadObject.get().getJSONObject("dataObject").get("consumerIdExternal"));
                assertEquals(data.get("channel"), payloadObject.get().getJSONObject("dataObject").get("channel"));
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("linkedObjects"), "linkedObjects object is NULL");

            }
        }

    }

    @Then("I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured change")
    public void I_verify_update_event_payload_captured_change(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("UPDATE_CONSUMER_BUSINESS_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertEquals(data.get("consumerName"), payloadObject.get().getJSONObject("dataObject").get("consumerName").toString());
                assertEquals(data.get("dateOfBirth"), payloadObject.get().getJSONObject("dataObject").get("dateOfBirth"));
                assertEquals(data.get("sexCode"), payloadObject.get().getJSONObject("dataObject").get("sexcode"));
                assertEquals(data.get("externalLinkId"), payloadObject.get().getJSONObject("dataObject").getJSONArray("linkedObjects").getJSONObject(0).get("externalLinkId").toString());
            }
        }
    }

    @Then("I verify UPDATE_CONSUMER_BUSINESS_EVENT has updatet data")
    public void verify_following_data(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("UPDATE_CONSUMER_BUSINESS_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));

                assertEquals(data.get("consumerIdExternal"), payloadObject.get().getJSONObject("dataObject").get("consumerIdExternal").toString());
                assertEquals(data.get("caseIdExternal"), payloadObject.get().getJSONObject("dataObject").get("caseIdExternal"));
                assertEquals(data.get("ssn"), payloadObject.get().getJSONObject("dataObject").get("ssn"));
                assertEquals(data.get("ethnicityCode"), payloadObject.get().getJSONObject("dataObject").get("ethnicityCode"));
                assertEquals(data.get("raceCode"), payloadObject.get().getJSONObject("dataObject").get("raceCode"));
                assertEquals(data.get("dateOfDeath"), payloadObject.get().getJSONObject("dataObject").get("dateOfDeath"));
                assertEquals(data.get("residencyStatus"), payloadObject.get().getJSONObject("dataObject").get("residencyStatus"));
                assertEquals(true, payloadObject.get().getJSONObject("dataObject").get("pregnancyInd"));
                assertEquals(data.get("pregnancyDueDate"), payloadObject.get().getJSONObject("dataObject").get("pregnancyDueDate"));
            }
        }
    }

    @Then("I will verify NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT payload contains following data")
    public void verify_v2_NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains(data.get("eventName"))) {
                System.out.println("Response object ===== " + each);
                assertNotNull(each.getAsJsonObject().get("eventId"), "eventId is null, actual eventId == " + each.getAsJsonObject().get("eventId"));
                String modul = each.getAsJsonObject().get("module").toString().replaceAll("\"", "");
                String status = each.getAsJsonObject().get("status").toString().replaceAll("\"", "");
                String correlationId = each.getAsJsonObject().get("correlationId").toString().replaceAll("\"", "");
                String createdBy = each.getAsJsonObject().get("createdBy").toString().replaceAll("\"", "");
                String message = each.getAsJsonObject().get("message").toString();
                //events object meta data validation
                assertEquals(data.get("module"), modul);
                assertEquals(data.get("status"), status);
                assertEquals(APICreateConsumerContactController.getUUID(), correlationId);
                assertEquals(message, "null");
                assertNotNull(each.getAsJsonObject().get("createdOn"));
                assertEquals(data.get("createdBy"), createdBy);
                //events object nested payload validation
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                System.out.println("Response payload object ===== " + payloadObject.toString());
                assertEquals(data.get("projectName"), payloadObject.get().get("projectName"));
                assertEquals(data.get("action"), payloadObject.get().get("action"));
                assertEquals(data.get("recordType"), payloadObject.get().get("recordType"));
                //nested dataObject payload validation
                assertEquals(APICreateConsumerContactController.getProfileId(), payloadObject.get().getJSONObject("dataObject").get("consumerId").toString());
                String fullName = APICreateConsumerContactController.getConsumerfirstName() + " " + APICreateConsumerContactController.getConsumerlastName();
                assertEquals(fullName, payloadObject.get().getJSONObject("dataObject").get("consumerName"));
                assertEquals(data.get("dataSource"), payloadObject.get().getJSONObject("dataObject").get("dataSource"));
                assertEquals(data.get("channel"), payloadObject.get().getJSONObject("dataObject").get("channel"));
                assertEquals(data.get("businessEvent"), payloadObject.get().getJSONObject("dataObject").get("businessEvent"));
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("addresses"), "address object is NULL");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("phones"), "phones object is NULL");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("emails"), "emails object is NULL");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("linkedObjects"), "linkedObjects object is NULL");
            }
        }

    }

    @Then("I will verify UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT payload contains following data")
    public void verify_v2_UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains(data.get("eventName"))) {
                System.out.println("Response object ===== " + each);
                assertNotNull(each.getAsJsonObject().get("eventId"), "eventId is null, actual eventId == " + each.getAsJsonObject().get("eventId"));
                String modul = each.getAsJsonObject().get("module").toString().replaceAll("\"", "");
                String status = each.getAsJsonObject().get("status").toString().replaceAll("\"", "");
                String correlationId = each.getAsJsonObject().get("correlationId").toString().replaceAll("\"", "");
                String createdBy = each.getAsJsonObject().get("createdBy").toString().replaceAll("\"", "");
                String message = each.getAsJsonObject().get("message").toString();
                //events object meta data validation
                assertEquals(data.get("module"), modul);
                assertEquals(data.get("status"), status);
                assertEquals(APICreateConsumerContactController.getUUID(), correlationId);
                assertEquals(message, "null");
                assertNotNull(each.getAsJsonObject().get("createdOn"));
                assertEquals(data.get("createdBy"), createdBy);
                //events object nested payload validation
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                System.out.println("Response payload object ===== " + payloadObject.get().toString());
                assertEquals(data.get("projectName"), payloadObject.get().get("projectName"));
                assertEquals(data.get("action"), payloadObject.get().get("action"));
                assertEquals(data.get("recordType"), payloadObject.get().get("recordType"));
                //nested dataObject payload validation
                assertEquals(APICreateConsumerContactController.getProfileId(), payloadObject.get().getJSONObject("dataObject").get("consumerId").toString());
                String fullName = APICreateConsumerContactController.getConsumerfirstName() + " " + APICreateConsumerContactController.getConsumerlastName();
                assertEquals(fullName, payloadObject.get().getJSONObject("dataObject").get("consumerName"));
                assertEquals(data.get("dataSource"), payloadObject.get().getJSONObject("dataObject").get("dataSource"));
                assertEquals(data.get("channel"), payloadObject.get().getJSONObject("dataObject").get("channel"));
                assertEquals(data.get("businessEvent"), payloadObject.get().getJSONObject("dataObject").get("businessEvent"));
                //contacts verification
                assertEquals(data.get("addressType"), payloadObject.get().getJSONObject("dataObject").getJSONArray("addresses").getJSONObject(0).get("addressType"));
                assertEquals(data.get("addressStreet1"), payloadObject.get().getJSONObject("dataObject").getJSONArray("addresses").getJSONObject(0).get("addressStreet1"));
                assertEquals(data.get("addressStreet2"), payloadObject.get().getJSONObject("dataObject").getJSONArray("addresses").getJSONObject(0).get("addressStreet2"));
                assertEquals(data.get("addressCity"), payloadObject.get().getJSONObject("dataObject").getJSONArray("addresses").getJSONObject(0).get("addressCity"));
                assertEquals(data.get("addressState"), payloadObject.get().getJSONObject("dataObject").getJSONArray("addresses").getJSONObject(0).get("addressState"));
                assertEquals(data.get("addressZip"), payloadObject.get().getJSONObject("dataObject").getJSONArray("addresses").getJSONObject(0).get("addressZip"));
                assertEquals(data.get("addressZipFour"), payloadObject.get().getJSONObject("dataObject").getJSONArray("addresses").getJSONObject(0).get("addressZipFour"));
                //phones validation
                assertEquals(data.get("phoneNumber"), payloadObject.get().getJSONObject("dataObject").getJSONArray("phones").getJSONObject(0).get("phoneNumber"));
                assertEquals(data.get("phoneType"), payloadObject.get().getJSONObject("dataObject").getJSONArray("phones").getJSONObject(0).get("phoneType"));
                //emails validation
                assertEquals(data.get("emailAddress"), payloadObject.get().getJSONObject("dataObject").getJSONArray("emails").getJSONObject(0).get("emailAddress"));
                assertEquals(data.get("emailType"), payloadObject.get().getJSONObject("dataObject").getJSONArray("emails").getJSONObject(0).get("emailType"));
                //linkedObject
                assertEquals(data.get("externalLinkId"), payloadObject.get().getJSONObject("dataObject").getJSONArray("linkedObjects").getJSONObject(0).get("externalLinkId"));
                assertEquals(data.get("externalRefType"), payloadObject.get().getJSONObject("dataObject").getJSONArray("linkedObjects").getJSONObject(0).get("externalRefType"));
            }
        }

    }

    @Then("I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change")
    public void I_verify_update_event_payload_captured_previous_id_change(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("UPDATE_CONSUMER_BUSINESS_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                System.out.println(data.get("consumerIdExternal").toString());
                assertEquals(APICreateConsumerContactController.getExternalconsumerId(), payloadObject.get().getJSONObject("dataObject").get("consumerIdExternal").toString());
                assertEquals(APICreateConsumerContactController.getProfileId(), payloadObject.get().getJSONObject("dataObject").get("consumerId").toString());
            }
        }
    }

    @Then("I verify NEW_CONSUMER_BUSINESS_EVENT payload captured previous id change")
    public void I_verify_new_event_payload_captured_previous_id_change(Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains("NEW_CONSUMER_BUSINESS_EVENT")) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                assertEquals(APICreateConsumerContactController.getExternalconsumerId(), payloadObject.get().getJSONObject("dataObject").get("consumerIdExternal").toString());
                assertEquals(data.get("dateOfBirth"), payloadObject.get().getJSONObject("dataObject").get("dateOfBirth"));
                assertEquals(data.get("dataSource"), payloadObject.get().getJSONObject("dataObject").get("dataSource"));
                assertEquals(APICreateConsumerContactController.getProfileId(), payloadObject.get().getJSONObject("dataObject").get("consumerId").toString());
            }
        }
    }

    @Then("I verify {string} payload captured previous id change")
    public void I_verify_new_contactInfo_event_payload_captured_previous_id_change(String eventName, Map<String, String> data) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonArray("events");

        for (JsonElement each : json) {
            if (each.getAsJsonObject().get("eventName").toString().contains(eventName)) {
                payloadObject.set(new JSONObject(new JSONObject(each.toString()).get("payload").toString().replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("}\"", "}")));
                JSONArray tempArray = payloadObject.get().getJSONObject("dataObject").getJSONArray("addresses");
                if (eventName.contains("NEW")){
                    assertEquals(APICreateConsumerContactController.getAddressStreet1(), tempArray.getJSONObject(0).getString("addressStreet1"));
                    assertEquals(APICreateConsumerContactController.getAddressZip(), tempArray.getJSONObject(0).getString("addressZip"));
                }
                assertEquals(data.get("dataSource"), payloadObject.get().getJSONObject("dataObject").get("dataSource"));
                assertEquals(APICreateConsumerContactController.getProfileId(), payloadObject.get().getJSONObject("dataObject").get("consumerId").toString());
            }
        }
    }

}