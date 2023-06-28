package com.maersk.crm.api_step_definitions;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.restassured.path.json.JsonPath;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMApplicationSearchPage;
import com.maersk.crm.pages.crm.CRMCreateApplicationPage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMMemberMatchingPage;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ResponseBody;
import com.maersk.crm.utilities.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.util.*;

import java.util.HashMap;


import static com.maersk.crm.api_step_definitions.APIATSCorrespondenceController.createdOutboundCorrespondenceID;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.*;
import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class APIATSApplicationController extends CRMUtilities implements ApiBase {

    private final String baseApplicationDataUrl = ConfigurationReader.getProperty("apiApplicationData");
    private final String applicationDataService = ConfigurationReader.getProperty("apiApplicationDataServices");
    private final String caseConsumerAPI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private final String viewApp = "/mars/applicationdata/applications/";
    private final String appSummary = "/mars/applicationdata/applications/summary";
    private final String createApp = "/mars/applicationdata/applications";
    private final String matchApplicationMember = "/mars/applicationdata/matching";
    private final String eligibility = "/consumers-eligibility";
    private final String bpmInstance = "/mars/applicationdata/bpm/";
    private final String updateStatus = "/mars/applicationdata/applications/status";
    private final String initiateActions = "/initiate-action";
    private final String viewAppDetails = "applicationdetails/";
    private final String caseConsumers = "/app/crm/case/consumers";
    private final String consumerUpdate = "/app/crm/casemember";
    private final String notes = "/notes";


    CRMCreateApplicationPage createApplication = new CRMCreateApplicationPage();
    CRMDashboardPage crmDashboardPage = new CRMDashboardPage();
    final ThreadLocal <APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    CRMMemberMatchingPage crmMemberMatchingPage = new CRMMemberMatchingPage();
    APIATSSendEventAndCreateLinksController sendEventAndCreateLinksController = new APIATSSendEventAndCreateLinksController();
    APIATSConsumersController consumersController = new APIATSConsumersController();
    APICreateCaseController caseController = new APICreateCaseController();
    APIConsumerPopulationDmnController consumerPopulationDmnController = new APIConsumerPopulationDmnController();

    // it's public to reuse in other parts of the framework
    public static final ThreadLocal <String> loggedInUserId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> loggedInUserName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> applicationId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> applicationIdAPI = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> caseSearchRequestParams = ThreadLocal.withInitial(JsonObject::new);
    private JsonObject appRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/createApplication_1.json");
    private JsonObject consumerUpdateRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/caseConsumerUpdate.json");
    public static final ThreadLocal<JsonObject> appPayload = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<ResponseBody> viewAppResponse = new ThreadLocal<>();

    private final ThreadLocal <String> currentDateHour = ThreadLocal.withInitial(()->apiAutoUtilities.get().getDateTimeNow("Current_SysDate"));

    private final ThreadLocal <List<String>> actualPrograms = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> actualProgramEligibility = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> expectedPrograms = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> actualOptIn = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> expectedOptIn = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <Boolean> pregnancyIndicator = ThreadLocal.withInitial(()->false);


    private final ThreadLocal <List<String>> appMemActualPrograms = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> appMemExpectedPrograms = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <Integer> counter = ThreadLocal.withInitial(()->0);
    private final ThreadLocal <Integer> authCounter = ThreadLocal.withInitial(()->0);
    private final ThreadLocal <Map<String, Object>> authorizedRepAdress = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> createdMemberDetails = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal <Map<String, String>> applicationToCase = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal <String> piFirstName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> piLastName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> piMiddleName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> piSuffix = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> piDOB = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> piSSN = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> amFirstName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> amLastName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> amDOB = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> amSSN = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> amMiddleName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> amSuffix = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> authRepFirstName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> authRepLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> randomPhonNum = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> currentTimeStamp = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> pastTimeStamp = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> futureTimeStamp = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> duplicateId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> externalAppID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> duplicateFirstname = ThreadLocal.withInitial(()->RandomStringUtils.randomAlphabetic(10));
    public static final ThreadLocal <String> duplicateLastName = ThreadLocal.withInitial(()->RandomStringUtils.randomAlphabetic(10));
    public static final ThreadLocal <String> duplicateDOB = ThreadLocal.withInitial(()->BrowserUtils.getPriorDateFormatYYYYMMdd(11000));
    public static final ThreadLocal <String> duplicateSSN = ThreadLocal.withInitial(()->RandomStringUtils.randomNumeric(9));
    public static final ThreadLocal <String> duplicateFirstnameAm = ThreadLocal.withInitial(()->RandomStringUtils.randomAlphabetic(10));
    public static final ThreadLocal <String> duplicateLastNameAm = ThreadLocal.withInitial(()->RandomStringUtils.randomAlphabetic(10));
    public static final ThreadLocal <String> duplicateDOBAm = ThreadLocal.withInitial(()->BrowserUtils.getPriorDateFormatYYYYMMdd(11000));
    public static final ThreadLocal <String> duplicateSSNAm = ThreadLocal.withInitial(()->RandomStringUtils.randomNumeric(9));
    private static final ThreadLocal <ArrayList<ResponseBody>> memberMatchingResponse = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> memberMatchingAppId = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal <List<String>> memberMatchingAppIdForUI = ThreadLocal.withInitial(ArrayList::new);
    private JsonObject matchRequest = getRequestPayloadFromPath("crm/ats/matching.json");
    private final ThreadLocal <ResponseBody> matchApiResponse = new ThreadLocal<>();
    private final ThreadLocal <ResponseBody> inboundAppResponse = new ThreadLocal<>();
    private final ThreadLocal <ResponseBody> matchingAppOneResponse =new ThreadLocal<>();
    private final ThreadLocal <ResponseBody> matchingApptwoResponse = new ThreadLocal<>();
    private final ThreadLocal <List<Map<String, Object>>> matchingApplicationsList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> matchingCollectionsList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <Map<Integer, String>> matchedConsumersMap = ThreadLocal.withInitial(LinkedHashMap::new);
    private final ThreadLocal <List<Map<String, Object>>> appConsumers = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> matchIncAppConsumerList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> matchingConsumerList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> inboundAppConsumerList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <Map<String, String>> matchingAppData = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <List<Map<String, Object>>> matchingAddressList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> expectedIdList = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal <List<String>> consumerRoleList = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal <List<Integer>> consumerIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> duplicateApplicationIds = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal <Map<String, String>> createdConsumerDetailsForAppl1 = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal <Map<String, String>> createdConsumerDetailsForAppl2 = ThreadLocal.withInitial(HashMap::new);

    private final ThreadLocal <JsonPath> jsonPathEvaluator1 = new ThreadLocal<>();
    private final ThreadLocal <JsonPath> jsonPathEvaluator2 = new ThreadLocal<>();

    private final ThreadLocal <List<Integer>> actualAppIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Integer>> actualMatchScoreList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Integer>> actualAppConsumerId = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal <List<String>> appIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <String> inbAppMemFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> inbAppMemLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> inbAppMemDob = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> inbAppMemSSN = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> inbAppMemConsumerId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <Integer> applicationConsumerId = ThreadLocal.withInitial(()->0);
    private static final ThreadLocal <Integer> applicationProgramId = ThreadLocal.withInitial(()->0);
    private static final ThreadLocal <Integer> eligibilityId = ThreadLocal.withInitial(()->0);

    private static final ThreadLocal <String> subProgramId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> applicationCode = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> consumerExternalId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <JsonArray> eligibilityRequest = ThreadLocal.withInitial(JsonArray::new);
    private final ThreadLocal <ResponseBody> eligibilityResponse = new ThreadLocal<>();
    private final ThreadLocal <String> eligibilityStartDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> eligibilityEndDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> eligibilityDeterminationDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <JsonArray> denialReasons = ThreadLocal.withInitial(JsonArray::new);
    public static final ThreadLocal <String> apiCreatedTaskId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <List<Integer>> applicationConsumerIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Integer>> eligibilityIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Integer>> applicationProgramIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> eligibilityStatusList = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal <List<Map<String, String>>> eligibilityDetailsList = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal <Map<String, String>> applicationLevelValues = ThreadLocal.withInitial(LinkedHashMap::new);
    public static final ThreadLocal <String> taskIDFromLinks = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> applicationReceivedLanguage = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <Boolean> isApplicationDuplicate = ThreadLocal.withInitial(()->false);
    public static final ThreadLocal <List<String>> searchCaseIdList = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal <List<String>> searchConsumerIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <String> firstNameSearch = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> lastNameSearch = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <List<Map<String, Object>>> matchingApplicationEligibilityStatusList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> matchingApplicationEligibilityPopulationList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <JsonObject> saveNotesRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <ResponseBody> saveNotesResponse = new ThreadLocal<>();
    public static final ThreadLocal <String> applicationDeadlineDate = ThreadLocal.withInitial(String::new);

    @When("I run create application api for ats from json file {string} with overrided keys as")
    public void i_run_create_application_api_for_ats_from_json_file_with_overrided_keys_as(String filePath, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        requestParams.set(getCreateApplicationRequestFromJSONFile(filePath));

        for (String key : keysToOverride.keySet()) {
            requestParams.get().addProperty(key, keysToOverride.get(key));
        }
        System.out.println(requestParams.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        applicationIdAPI.set(appData != null ? String.valueOf(appData.get("applicationId")) : null);

        System.out.println("Application id is for ATS: " + applicationIdAPI.get());
    }

    @When("I run create application api for ats from json file {string} with base overridden primary individual details as")
    public void i_run_create_application_api_for_ats_from_json_file_with_base_overridden_primary_individual_details_as(String filePath, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        requestParams.set(getCreateApplicationRequestFromJSONFile(filePath));

        for (String key : keysToOverride.keySet()) {
            String value;

            // assign random value
            switch (keysToOverride.get(key).toLowerCase()) {
                case "alphabetic 10":
                    value = getRandomString(10);
                    break;
                case "random past date":
                    int randomNum = Integer.parseInt(getRandomNumber(2));

                    // saving this to be able to use in the next creations(to make sure do not get duplicate)
                    createdMemberDetails.get().put("randomNumberForDate", String.valueOf(randomNum));

                    value = getPriorDateFormatYYYYMMdd(randomNum);
                    break;
                default:
                    value = keysToOverride.get(key);
            }

            // save data for next validation step
            createdMemberDetails.get().put(key, value);

            // override in the json
            requestParams.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().addProperty(key, value);

            // if input given as null from input table, remove from json body
            if (value.equalsIgnoreCase("null")) {
                requestParams.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().remove(key);
            }
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        applicationIdAPI.set(appData != null ? String.valueOf(appData.get("applicationId")) : null);

        System.out.println("Application id is: " + applicationIdAPI.get());
    }

    @When("I run create application api for ats from json file {string} with overridden primary individual details as")
    public void i_run_create_application_api_for_ats_from_json_file_with_overridden_primary_individual_details_as(String filePath, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        requestParams.set(getCreateApplicationRequestFromJSONFile(filePath));

        for (String key : keysToOverride.keySet()) {
            String value;

            // assign random value
            switch (keysToOverride.get(key).toLowerCase()) {
                case "alphabetic 10":
                    value = getRandomString(10);
                    break;
                case "random past date":
                    int randomNum = Integer.parseInt(getRandomNumber(2));
                    value = getPriorDateFormatYYYYMMdd(randomNum);
                    break;
                case "new random past date":
                    int newRandomNum = Integer.parseInt(createdMemberDetails.get().get("randomNumberForDate"));
                    newRandomNum = newRandomNum - 1; // to make it different from base date
                    value = getPriorDateFormatYYYYMMdd(newRandomNum);
                    break;
                case "base + alphabetic 5":
                    value = createdMemberDetails.get().get(key) + getRandomString(5);
                    break;
                case "base + alphabetic 2":
                    value = createdMemberDetails.get().get(key) + getRandomString(2);
                    break;
                case "base":
                    value = createdMemberDetails.get().get(key);
                    break;
                default:
                    value = keysToOverride.get(key);
            }

            // override in the json
            requestParams.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().addProperty(key, value);

            // if input given as null from input table, remove from json body
            if (value.equalsIgnoreCase("null")) {
                requestParams.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().remove(key);
            }

        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint());
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        applicationIdAPI.set(appData != null ? String.valueOf(appData.get("applicationId")) : null);

        System.out.println("Application id for ATS is: " + applicationIdAPI.get());
    }

    @Then("I get response from application ats api with status code {string} and status {string}")
    public void i_get_response_from_application_ats_api_with_status_code_and_status(String statusCode, String status) {
        System.out.println("Request is: "+appRequestPayload);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, Integer.parseInt(statusCode), "Status code is not correct for Save Application API Call");
        System.out.println("Status from response: "+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"));
        if (!status.isEmpty())
            assertTrue(status.equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status")), "Wrong status is displayed for Save Application API Call");
    }

    @And("I get application id from the Create Application Page")
    public void getAppIdCreateApplication() {
        String val = createApplication.applicationId.getText();
        String[] values = val.split("\\r?\\n");
        applicationId.set(values[1]);
        duplicateId.set(values[1]);
        System.out.println("Application Id from UI: " + applicationId);
    }

    @When("I initiate the View Application Details API for the Created Application")
    public void initiateViewAppDetailsAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(viewApp + applicationId);
    }

    @When("I run the View Application Details API for the Created Application")
    public void runViewApplicationDetailsAPI() {
        waitFor(12);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("GET View API Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("success"));
        System.out.println("View response is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        World.generalSave.get().put("ApplicationDetails", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator);
    }

    @And("I save the application Id from Inbound document creation for fetching application details")
    public void iSaveApplicationId() {
        applicationId.set(inbCreatedAppId.get());
    }

    @When("I initiated View application api for API created applications")
    public void i_initiated_View_application_api_for_API_created_applications() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        if (!applicationId.get().equals("")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(viewApp + applicationId.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(viewApp + applicationIdAPI.get());
        }
    }

    @Then("I verify PI UPDATEDON is later than PI CREATEDON")
    public void i_verify_PI_UPDATEDON_is_later_than_PI_CREATEDON() {
        String createdOnStr = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].createdOn");
        String updatedOnStr = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].updatedOn");

        System.out.println("CREATEDON: " + createdOnStr);
        System.out.println("UPDATEDON: " + updatedOnStr);

        LocalDateTime createdOn = LocalDateTime.parse(createdOnStr.substring(0, createdOnStr.indexOf("+")));
        LocalDateTime updatedOn = LocalDateTime.parse(updatedOnStr.substring(0, createdOnStr.indexOf("+")));

        System.out.println("CREATEDON DATE: " + createdOn);
        System.out.println("UPDATEDON DATE: " + updatedOn);

        Assert.assertTrue(updatedOn.isAfter(createdOn), "UpdatedOn should be after createdOn");
    }


    @Then("I verify the interactive indicator from application details of the Created Application from {string} for interactive type {string}")
    public void verifyInteractiveIndApplicationDetails(String creationType, String interactiveType) {
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        String appId = String.valueOf(appData.get("applicationId"));

        boolean interactiveInd = (boolean) appData.get("interactiveInd");
        System.out.println("Interactive Indicator: " + interactiveInd);

        if (creationType.equals("UI")) {
            assertEquals(appId, applicationId.get());
            assertTrue(interactiveInd);
        } else if (creationType.equals("API") && (interactiveType.equals("false") || interactiveType.equals("null"))) {
            assertEquals(appId, applicationIdAPI.get());
            assertFalse(interactiveInd);
        } else if (creationType.equals("API") && interactiveType.equals("true")) {
            assertEquals(appId, applicationIdAPI.get());
            assertTrue(interactiveInd);
        }
    }


    @Then("I verify all consumers objects have applicationConsumerStatus field")
    public void i_verify_all_consumers_objects_have_applicationConsumerStatus_field() {
        List<Map<String, Object>> applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        for (Map<String, Object> applicationConsumer : applicationConsumers) {
            System.out.println("Consumer type is:" + applicationConsumer.get("consumerRoleType"));

            if (applicationConsumer.get("consumerRoleType").toString().equals("Authorized Rep")) {
                System.out.println("Auth rep app cons status: " + applicationConsumer.get("consumerRoleType").toString());
                assertNull(applicationConsumer.get("applicationConsumerStatus"));
            } else
                assertTrue(applicationConsumer.get("applicationConsumerStatus").toString().equals("Received") || applicationConsumer.get("applicationConsumerStatus").toString().equals("Determining"));
        }
    }

    @Then("I verify all consumers objects have notApplyingIndicator field in the response")
    public void i_verify_all_consumers_objects_have_notApplyingIndicator_field_in_the_response() {
        List<Map<String, Object>> applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        for (Map<String, Object> applicationConsumer : applicationConsumers) {
            assertTrue(applicationConsumer.get("notApplyingIndicator") instanceof Boolean, "notApplyingIndicator should be boolean");
            if (applicationConsumer.get("notApplyingIndicator").toString().equalsIgnoreCase("true")) {
                assertNull(applicationConsumer.get("programs"), "Programs should be null if not applying is true");
            }
        }
    }

    @Then("I expect applicationStatus from application details of the Created Application as {string}")
    public void i_expect_applicationStatus_from_application_details_of_the_Created_Application_as(String expectedApplicationStatus) {
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        String appId = String.valueOf(appData.get("applicationId"));

        String actualApplicationStatus = (String) appData.get("applicationStatus");
        System.out.println("Actual applicationStatus: " + actualApplicationStatus);
        System.out.println("Expected applicationStatus: " + expectedApplicationStatus);

        Assert.assertEquals(appId, applicationIdAPI.get(), "application id is not matching");
        Assert.assertEquals(actualApplicationStatus, expectedApplicationStatus, "application status is not matching");
    }

    @Then("I expected error message as response from create application api for ats with status {string}")
    public void i_expected_error_message_as_response_from_create_application_api_for_ats(String expectedStatus, List<Map<String, String>> data) {

        String actualStatus = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status");
        List<Map<String, String>> actualErrors = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors");

        if (!expectedStatus.isEmpty()) Assert.assertEquals(actualStatus, expectedStatus);

        for (int i = 0; i < data.size(); i++) {
            Map<String, String> expectedError = data.get(i);
            Map<String, String> actualError = actualErrors.get(i);
            Assert.assertEquals(actualError.get("message"), expectedError.get("error message"), "Incorrect error message");
        }
    }

    @Then("I validate ats search application response data with root keys:")
    public void i_validate_ats_search_application_response_data_with_root_keys(List<Map<String, String>> data) {
        Map<String, String> expectedData = data.get(0);

        HashMap<String, Object> actualData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");

        for (String key : expectedData.keySet()) {
            Assert.assertEquals(String.valueOf(actualData.get(key)).trim(), String.valueOf(expectedData.get(key)).trim(), key + " is not matching");
        }

    }

    @Then("I validate ats application applicationConsumer in response data by array index {string}")
    public void i_validate_ats_application_applicationConsumer_in_response_data_by_array_index(String consumerIndex, List<Map<String, String>> data) {
        Map<String, String> expectedData = data.get(0);

        HashMap<String, Object> actualData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        ArrayList<HashMap<String, String>> actualConsumers = (ArrayList<HashMap<String, String>>) actualData.get("applicationConsumers");
        Assert.assertTrue(actualConsumers.size() > Integer.parseInt(consumerIndex));
        HashMap<String, String> actualConsumer = actualConsumers.get(Integer.parseInt(consumerIndex));

        for (String key : expectedData.keySet()) {
            Assert.assertEquals(String.valueOf(actualConsumer.get(key)).trim(), String.valueOf(expectedData.get(key)).trim(), key + " is not matching");
        }
    }

    @Then("I validate applicationConsumerAddress by array index {string} of applicationConsumer by array index {string}")
    public void i_validate_applicationConsumerAddress_by_array_index_of_applicationConsumer_by_array_index(String addressIndex, String consumerIndex, List<Map<String, String>> data) {
        Map<String, String> expectedData = data.get(0);

        HashMap<String, Object> actualData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        ArrayList<HashMap<String, Object>> actualConsumers = (ArrayList<HashMap<String, Object>>) actualData.get("applicationConsumers");
        Assert.assertTrue(actualConsumers.size() > Integer.parseInt(consumerIndex));

        HashMap<String, Object> actualConsumer = actualConsumers.get(Integer.parseInt(consumerIndex));
        List<HashMap<String, String>> actualAddresses = (ArrayList<HashMap<String, String>>) actualConsumer.get("applicationConsumerAddress");
        Assert.assertTrue(actualAddresses.size() > Integer.parseInt(addressIndex));

        Collections.sort(actualAddresses, new Comparator<HashMap<String, String>>() {
            public int compare(HashMap<String, String> one, HashMap<String, String> two) {
                return one.get("addressCity").compareTo(two.get("addressCity"));
            }
        });

        HashMap<String, String> actualAddress = actualAddresses.get(Integer.parseInt(addressIndex));
        for (String key : expectedData.keySet()) {
            Assert.assertEquals(String.valueOf(actualAddress.get(key)).trim(), String.valueOf(expectedData.get(key)).trim(), key + " is not matching");
        }
    }

    @Then("I validate applicationConsumerEmail by array index {string} of applicationConsumer by array index {string}")
    public void i_validate_applicationConsumerEmail_by_array_index_of_applicationConsumer_by_array_index(String emailIndex, String consumerIndex, List<Map<String, String>> data) {
        Map<String, String> expectedData = data.get(0);

        HashMap<String, Object> actualData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        ArrayList<HashMap<String, Object>> actualConsumers = (ArrayList<HashMap<String, Object>>) actualData.get("applicationConsumers");
        Assert.assertTrue(actualConsumers.size() > Integer.parseInt(consumerIndex));

        HashMap<String, Object> actualConsumer = actualConsumers.get(Integer.parseInt(consumerIndex));
        ArrayList<HashMap<String, String>> actualEmails = (ArrayList<HashMap<String, String>>) actualConsumer.get("applicationConsumerEmail");
        Assert.assertTrue(actualEmails.size() > Integer.parseInt(emailIndex));

        HashMap<String, String> actualEmail = actualEmails.get(Integer.parseInt(emailIndex));

        for (String key : expectedData.keySet()) {
            Assert.assertEquals(String.valueOf(actualEmail.get(key)).trim(), String.valueOf(expectedData.get(key)).trim(), key + " is not matching");
        }
    }

    @Then("I validate applicationConsumerPhone by array index {string} of applicationConsumer by array index {string}")
    public void i_validate_applicationConsumerPhone_by_array_index_of_applicationConsumer_by_array_index(String phoneIndex, String consumerIndex, List<Map<String, String>> data) {
        Map<String, String> expectedData = data.get(0);

        HashMap<String, Object> actualData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        ArrayList<HashMap<String, Object>> actualConsumers = (ArrayList<HashMap<String, Object>>) actualData.get("applicationConsumers");
        Assert.assertTrue(actualConsumers.size() > Integer.parseInt(consumerIndex));

        HashMap<String, Object> actualConsumer = actualConsumers.get(Integer.parseInt(consumerIndex));
        ArrayList<HashMap<String, String>> actualPhones = (ArrayList<HashMap<String, String>>) actualConsumer.get("applicationConsumerPhone");
        Assert.assertTrue(actualPhones.size() > Integer.parseInt(phoneIndex));

        Collections.sort(actualPhones, new Comparator<HashMap<String, String>>() {
            public int compare(HashMap<String, String> one, HashMap<String, String> two) {
                return one.get("phoneNumber").compareTo(two.get("phoneNumber"));
            }
        });

        HashMap<String, String> actualPhone = actualPhones.get(Integer.parseInt(phoneIndex));

        for (String key : expectedData.keySet()) {
            Assert.assertEquals(String.valueOf(actualPhone.get(key)).trim(), String.valueOf(expectedData.get(key)).trim(), key + " is not matching");
        }
    }

    @Then("I validate programs of applicationConsumer by array index {string}")
    public void i_validate_programs_of_applicationConsumer_by_array_index(String consumerIndex, List<List<String>> data) {
        List<String> expectedData = data.get(0);

        HashMap<String, Object> actualData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        ArrayList<HashMap<String, Object>> actualConsumers = (ArrayList<HashMap<String, Object>>) actualData.get("applicationConsumers");
        Assert.assertTrue(actualConsumers.size() > Integer.parseInt(consumerIndex));

        HashMap<String, Object> actualConsumer = actualConsumers.get(Integer.parseInt(consumerIndex));
        ArrayList<String> programs = (ArrayList<String>) actualConsumer.get("programs");
        Assert.assertEquals(programs.size(), expectedData.size());

        Collections.sort(expectedData);
        Collections.sort(programs);
        Assert.assertEquals(programs, expectedData, "Programs are not matching");
    }

    @Then("I validate consumerOptInInformation of applicationConsumer by array index {string}")
    public void i_validate_consumerOptInInformation_of_applicationConsumer_by_array_index(String consumerIndex, List<List<String>> data) {
        List<String> expectedData = data.get(0);

        HashMap<String, Object> actualData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        ArrayList<HashMap<String, Object>> actualConsumers = (ArrayList<HashMap<String, Object>>) actualData.get("applicationConsumers");
        Assert.assertTrue(actualConsumers.size() > Integer.parseInt(consumerIndex));

        HashMap<String, Object> actualConsumer = actualConsumers.get(Integer.parseInt(consumerIndex));
        ArrayList<String> consumerOptInInformation = (ArrayList<String>) actualConsumer.get("consumerOptInInformation");
        Assert.assertEquals(consumerOptInInformation.size(), expectedData.size());

        Collections.sort(expectedData);
        Collections.sort(consumerOptInInformation);
        Assert.assertEquals(consumerOptInInformation, expectedData, "consumerOptInInformation are not matching");
    }

    @When("I saved logged in user ID")
    public void i_saved_login_user_ID() {
        String idText = crmDashboardPage.loggedInUserId.getText().trim();
        String userName = crmDashboardPage.loggedInUserName.getText();
        idText = idText.substring(idText.indexOf("ID") + 2).trim();
        System.out.println("LOGGED IN ID: " + idText);
        loggedInUserId.set(idText);
        loggedInUserName.set(userName);
    }

    @Then("I Verify the following from the GET Application with ApplicationId")
    public void iVerifyTheFollowingFromTheGETApplicationWithApplicationId(Map<String, String> datatable) {
        waitFor(2);
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "APP MEM CREATEDBY":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].consumerRoleType"), "Application Member", "The array values are not from Application Member");
                    if (datatable.get("APP MEM CREATEDBY").equalsIgnoreCase("base")) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].createdBy"), loggedInUserId);
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].createdBy"), datatable.get("APP MEM CREATEDBY"));
                    }
                    break;
                case "APP MEM CREATEDON":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].consumerRoleType"), "Application Member", "The array values are not from Application Member");
                    if ("today's date and hour".equals(datatable.get("APP MEM CREATEDON"))) {
                        assertTrue(apiAutoUtilities.get().compareDateTimes(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].createdOn"), currentDateHour.get()), "Application Member CreatedOn value does not match");
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].createdOn"), datatable.get("APP MEM CREATEDON").substring(0, 10), "Application Member CreatedOn value does not match");
                    }
                    break;
                case "PI CREATEDBY":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].consumerRoleType"), "Primary Individual", "The array values are not from Primary Individual");
                    if (datatable.get("PI CREATEDBY").equalsIgnoreCase("base")) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].createdBy"), loggedInUserId);
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].createdBy"), datatable.get("PI CREATEDBY"));
                    }
                    break;
                case "PI CREATEDON":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].consumerRoleType"), "Primary Individual", "The array values are not from Application Member");
                    if ("today's date and hour".equals(datatable.get("PI CREATEDON"))) {
                        assertTrue(apiAutoUtilities.get().compareDateTimes(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].createdOn"), currentDateHour.get()), "Application Member CreatedOn value does not match");
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].createdOn"), datatable.get("PI CREATEDON").substring(0, 10), "Application Member CreatedOn value does not match");
                    }
                    break;
                case "PI UPDATEDBY":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].consumerRoleType"), "Primary Individual", "The array values are not from Primary Individual");
                    if (datatable.get("PI UPDATEDBY").equalsIgnoreCase("base")) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].updatedBy"), loggedInUserId);
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].updatedBy"), datatable.get("PI UPDATEDBY"));
                    }
                    break;
                case "PI UPDATEDON":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].consumerRoleType"), "Primary Individual", "The array values are not from Application Member");
                    if ("today's date and hour".equals(datatable.get("PI UPDATEDON"))) {
                        assertTrue(apiAutoUtilities.get().compareDateTimes(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].updatedOn"), currentDateHour.get()), "Application Member CreatedOn value does not match");
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].updatedOn"), datatable.get("PI UPDATEDON").substring(0, 10), "Application Member CreatedOn value does not match");
                    }
                    break;
                case "APP MEM UPDATEDBY":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].consumerRoleType"), "Application Member", "The array values are not from Application Member");
                    if (datatable.get("APP MEM UPDATEDBY").equalsIgnoreCase("base")) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].updatedBy"), loggedInUserId);
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].updatedBy"), datatable.get("APP MEM UPDATEDBY"));
                    }
                    break;
                case "APP MEM UPDATEDON":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].consumerRoleType"), "Application Member", "The array values are not from Application Member");
                    if ("today's date and hour".equals(datatable.get("APP MEM UPDATEDON"))) {
                        assertTrue(apiAutoUtilities.get().compareDateTimes(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].updatedOn"), currentDateHour.get()), "Application Member CreatedOn value does not match");
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].updatedOn"), datatable.get("APP MEM UPDATEDON").substring(0, 10), "Application Member CreatedOn value does not match");
                    }
                    break;
                case "APP DATA UPDATEDBY":
                    if (datatable.get("APP DATA UPDATEDBY").equalsIgnoreCase("base")) {
                        System.out.println("Logged in userId to application: " + loggedInUserId);
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.updatedBy"), loggedInUserId, "APP DATA UPDATEDBY value does not match:");
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.updatedBy"), datatable.get("APP DATA UPDATEDBY"));
                    }
                    break;
                case "APP DATA UPDATEDON":
                    if ("today's date and hour".equals(datatable.get("APP DATA UPDATEDON"))) {
                        assertTrue(apiAutoUtilities.get().compareDateTimes(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.updatedOn"), currentDateHour.get()), "APP DATA UPDATEDON value does not match:");
                    } else {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.updatedOn"), datatable.get("APP DATA UPDATEDON").substring(0, 10), "APP DATA UPDATEDON value does not match:");
                    }
                    break;
                case "PI ID":
                    assertEquals("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].applicationConsumerId"), crmMemberMatchingPage.firstConsumerDetails.get(0).getText(), "The first application Member ID doesn't match");
                case "APP ID":
                    assertEquals("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].applicationConsumerId"), crmMemberMatchingPage.secondConsumerDetails.get(0).getText(), "The second application Member ID doesn't match");
                    break;
                case "MI DUE DATE":
                    if ("MEDICAL ASSISTANCE MI DUE DATE".equals(datatable.get("MI DUE DATE"))) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.appMissingInfoDueDate"), getFutureDateFormatYYYYMMdd(45 - 7));
                    } else if ("LONG TERM CARE MI DUE DATE".equals(datatable.get("MI DUE DATE"))) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.appMissingInfoDueDate"), getFutureDateFormatYYYYMMdd(90 - 4));
                    }
                    break;
                default:
                    Assert.fail("Entered Key did not match existing keys");
            }
        }
    }

    /**
     * @param filePath "testData/api/" already provided
     * @return
     */
    private JsonObject getCreateApplicationRequestFromJSONFile(String filePath) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile(filePath);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }


    @Then("I verify Primary Individual Details in the retrieved response with appId")
    public void iVerifyPrimaryIndividualDetailsInTheRetrievedResponseWithAppId(Map<String, String> datatable) {
        int k;
        List<Map<String, Object>> applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");

        for (k = 0; k < applicationConsumers.size(); k++) {
            if ("Primary Individual".equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerRoleType"))) {
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerRoleType"), "Primary Individual", "Consumer Role Type Primary Individual Expected but found: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerRoleType").toString());
                for (String eachVerifyValue : datatable.keySet()) {
                    switch (eachVerifyValue.toUpperCase()) {
                        case "FULL NAME":
                            String actualFirstName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerFirstName");
                            String actualLastName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerLastName");
                            String actualMi = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerMiddleName");
                            String actualSuffix = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerSuffix");
                            String actualFullName = actualFirstName + " " + actualMi + " " + actualLastName + " " + actualSuffix;
                            assertEquals(actualFullName, datatable.get("FULL NAME"), "Actual Primary Individual Full Name does not match with expected");
                            break;
                        case "DOB":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].dateOfBirth"), datatable.get("DOB"), "Primary Individual Actual DoB does not match expected DoB");
                            break;
                        case "SSN":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].ssn"), datatable.get("SSN"), "Primary Individual Actual SSN does not match expected SSN");
                            break;
                        case "GENDER":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].genderCode"), datatable.get("GENDER"), "Primary Individual Actual Gender does not match expected Gender");
                            break;
                        case "SPOKEN LANGUAGE":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].spokenLanguage"), datatable.get("SPOKEN LANGUAGE"), "Primary Individual Actual SPOKEN LANGUAGE does not match expected SPOKEN LANGUAGE");
                            break;
                        case "WRITTEN LANGUAGE":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].writtenLanguage"), datatable.get("WRITTEN LANGUAGE"), "Primary Individual Actual WRITTEN LANGUAGE does not match expected WRITTEN LANGUAGE");
                            break;
                        case "PROGRAMS":
                            List<String> actualProgramsArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[" + k + "].programs");
                            for (int j = 0; j < actualProgramsArray.size(); j++) {
                                actualPrograms.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].programs[" + j + "].programType"));
                            }
                            expectedPrograms.set(Arrays.asList(datatable.get("PROGRAMS").split(" ")));
                            Collections.sort(actualPrograms.get());
                            Collections.sort(expectedPrograms.get());
                            assertEquals(actualPrograms.get(), expectedPrograms.get(), "Primary Individual Actual PROGRAMS does not match expected PROGRAMS");
                            break;
                        case "PROGRAMS ELIGIBILITY STATUS":
                            expectedPrograms.set(Arrays.asList(datatable.get("PROGRAMS ELIGIBILITY STATUS").split(" ")));
                            Map<String, String> expectedProgramElig = new HashMap<>();
                            Map<String, String> actualProgramElig = new HashMap<>();
                            expectedProgramElig.put(expectedPrograms.get().get(0), expectedPrograms.get().get(1));
                            List<String> actualProgramArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[" + k + "].programs");
                            for (int j = 0; j < actualProgramArray.size(); j++) {
                                String programName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].programs[" + j + "].programType");
                                if (programName.equals(expectedPrograms.get().get(0)))
                                    actualProgramElig.put(programName, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].programs[" + j + "].eligibilityStatus"));
                            }
                            System.out.println(expectedProgramElig);
                            System.out.println(actualProgramElig);
                            assertTrue(expectedProgramElig.equals(actualProgramElig), "Program & eligibility don't match");
                            break;
                        case "COMM OPT-INS":
                            actualPrograms.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[" + k + "].consumerOptInInformation"));
                            expectedPrograms.set(Arrays.asList(datatable.get("COMM OPT-INS").split(" ")));
                            Collections.sort(actualOptIn.get());
                            Collections.sort(expectedOptIn.get());
                            assertEquals(actualOptIn.get(), expectedOptIn.get(), "Primary Individual Actual COMM OPT-INS does not match expected COMM OPT-INS");
                            break;
                        case "ARE YOU PREGNANT":
                            if ("true".equals(datatable.get("ARE YOU PREGNANT"))) {
                                pregnancyIndicator.set(true);
                            } else {
                                pregnancyIndicator.set(false);
                            }
                            boolean actual = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.applicationConsumers[" + k + "].pregnancyInd");
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.applicationConsumers[" + k + "].pregnancyInd"), pregnancyIndicator, "Primary Individual ARE YOU PREGNANT does not match ARE YOU PREGNANT");
                            break;
                        case "NO BABIES EXPECTED":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.applicationConsumers[" + k + "].expectedBabies"), Integer.parseInt(datatable.get("NO BABIES EXPECTED")), "Primary Individual NO BABIES EXPECTED does not match NO BABIES EXPECTED ");
                            break;
                        case "EXPECTED DUE DATE":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].expectedDueDate"), datatable.get("EXPECTED DUE DATE"), "Primary Individual Actual DUE DATE does not match EXPECTED DUE DATE");
                            break;
                        case "EXTERNAL CONSUMER ID":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].externalConsumerId"), datatable.get("EXTERNAL CONSUMER ID"), "Primary Individual Actual EXTERNAL CONSUMER ID does not match expected EXTERNAL CONSUMER ID");
                            break;
                        case "EXTERNAL ID TYPE":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].externalConsumerIdType"), datatable.get("EXTERNAL ID TYPE"), "Primary Individual Actual EXTERNAL ID TYPE does not match expected EXTERNAL ID TYPE");
                            break;
                        //Below case compares response from Application Details API
                        case "PI CONSUMER STATUS":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].applicationConsumerStatus"), datatable.get("PI CONSUMER STATUS"), "Primary Individual Expected CONSUMER STATUS does not match expected CONSUMER STATUS: ");
                            break;
                        //Below case compares response from Application Summary API
                        case "CONSUMER STATUS":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerStatus"), datatable.get("CONSUMER STATUS"), "Primary Individual Expected CONSUMER STATUS does not match expected CONSUMER STATUS: ");
                            break;
                        case "CONSUMER ROLE":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerRoleType"), datatable.get("CONSUMER ROLE"), "Primary Individual Expected CONSUMER ROLE does not match expected CONSUMER ROLE: ");
                            break;
                        case "FIRST NAME":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerFirstName"), datatable.get("FIRST NAME"), "Actual Primary Individual First Name does not match with expected");
                            break;
                        case "LAST NAME":
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerLastName"), datatable.get("LAST NAME"), "Actual Primary Individual Last Name does not match with expected");
                            break;
                        default:
                            Assert.fail("Entered Key did not match existing keys");
                    }
                }
            }
        }
    }

    /**
     * @param "PRIMARY PHONE INDICATOR" values are CELL, HOME, WORK and FAX
     *                 to check for true on primaryContactTypeInd
     */
    @And("I verify Primary Individual phone contact information in the retrieved response with appId")
    public void iVerifyPrimaryIndividualPhoneContactInformationInTheRetrievedResponseWithAppId
    (Map<String, String> datatable) {
        List<Map<String, Object>> applicationConsumerPhone = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[0].applicationConsumerPhone");
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "CELL PHONE":
                    for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                        if ("cellPhoneNumber".equals(eachPhone.get("phoneType"))) {
                            assertEquals(eachPhone.get("phoneNumber"), datatable.get("CELL PHONE"), "Actual Application Cell Phone Number does not match the expected CELL PHONE");
                        }
                    }
                    break;
                case "HOME PHONE":
                    for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                        if ("homePhoneNumber".equals(eachPhone.get("phoneType"))) {
                            assertEquals(eachPhone.get("phoneNumber"), datatable.get("HOME PHONE"), "Actual Application HOME Phone Number does not match the expected HOME PHONE");
                        }
                    }
                    break;
                case "WORK PHONE":
                    for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                        if ("workPhoneNumber".equals(eachPhone.get("phoneType"))) {
                            assertEquals(eachPhone.get("phoneNumber"), datatable.get("WORK PHONE"), "Actual Application WORK Phone Number does not match the expected WORK PHONE");
                        }
                    }
                    break;
                case "FAX":
                    for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                        if ("faxnumber".equals(eachPhone.get("phoneType"))) {
                            assertEquals(eachPhone.get("phoneNumber"), datatable.get("FAX"), "Actual Application FAX Number does not match the expected FAX");
                        }
                    }
                    break;
                case "PRIMARY PHONE INDICATOR":
                    if ("CELL".equals(datatable.get("PRIMARY PHONE INDICATOR"))) {
                        for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                            if ("cellPhoneNumber".equals(eachPhone.get("phoneType"))) {
                                assertEquals(eachPhone.get("primaryContactTypeInd"), true, "Expected Cell Phone to be Primary Phone but found false");
                            }
                        }
                    } else if ("WORK".equals(datatable.get("PRIMARY PHONE INDICATOR"))) {
                        for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                            if ("workPhoneNumber".equals(eachPhone.get("phoneType"))) {
                                assertEquals(eachPhone.get("primaryContactTypeInd"), true, "Expected WORK Phone to be Primary Phone but found false");
                            }
                        }
                    } else if ("HOME".equals(datatable.get("PRIMARY PHONE INDICATOR"))) {
                        for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                            if ("homePhoneNumber".equals(eachPhone.get("phoneType"))) {
                                assertEquals(eachPhone.get("primaryContactTypeInd"), true, "Expected HOME Phone to be Primary Phone but found false");
                            }
                        }
                    } else if ("FAX".equals(datatable.get("PRIMARY PHONE INDICATOR"))) {
                        for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                            if ("faxnumber".equals(eachPhone.get("phoneType"))) {
                                assertEquals(eachPhone.get("primaryContactTypeInd"), true, "Expected FAX number to be Primary Phone but found false");
                            }
                        }
                    } else {
                        fail("Entered Primary Phone Indicator did not match CELL, WORK, HOME, FAX");
                    }
                    break;
                default:
                    Assert.fail("Entered Key did not match existing keys");
            }
        }
    }

    @And("I verify Application Email {string} contact information in the retrieved response with appId")
    public void iVerifyApplicationEmailContactInformationInTheRetrievedResponseWithAppId(String email) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].applicationConsumerEmail[0].emailAddress"), email, "Expected email address did not match:");
    }

    @And("I verify Application Residence and Mailing address information in the retrieved response with appId")
    public void iVerifyApplicationResidenceAndMailingAddressInformationInTheRetrievedResponseWithAppId
            (Map<String, String> datatable) {
        List<Map<String, Object>> applicationConsumerAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[0].applicationConsumerAddress");
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "RESIDENCE ADDRESS 1":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressStreet1"), datatable.get("RESIDENCE ADDRESS 1"), "Actual Application RESIDENCE ADDRESS 1 does not match the expected RESIDENCE ADDRESS 1:");
                        }
                    }
                    break;
                case "RESIDENCE ADDRESS 2":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressStreet2"), datatable.get("RESIDENCE ADDRESS 2"), "Actual Application RESIDENCE ADDRESS 2 does not match the expected RESIDENCE ADDRESS 2:");
                        }
                    }
                    break;
                case "RESIDENCE CITY":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressCity"), datatable.get("RESIDENCE CITY"), "Actual Application RESIDENCE CITY does not match the expected RESIDENCE CITY:");
                        }
                    }
                    break;
                case "RESIDENCE STATE":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressState"), datatable.get("RESIDENCE STATE"), "Actual Application RESIDENCE STATE does not match the expected RESIDENCE STATE:");
                        }
                    }
                    break;
                case "RESIDENCE ZIP":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressZip"), datatable.get("RESIDENCE ZIP"), "Actual Application RESIDENCE ZIP does not match the expected RESIDENCE ZIP:");
                        }
                    }
                    break;
                case "RESIDENCE COUNTY":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressCounty"), datatable.get("RESIDENCE COUNTY"), "Actual Application RESIDENCE COUNTY does not match the expected RESIDENCE COUNTY:");
                        }
                    }
                    break;
                case "MAIL ADDRESS 1":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressStreet1"), datatable.get("MAIL ADDRESS 1"), "Actual Application MAIL ADDRESS 1 does not match the expected MAIL ADDRESS 1:");
                        }
                    }
                    break;
                case "MAIL ADDRESS 2":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressStreet2"), datatable.get("MAIL ADDRESS 2"), "Actual Application MAIL ADDRESS 2 does not match the expected MAIL ADDRESS 2:");
                        }
                    }
                    break;
                case "MAIL CITY":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressCity"), datatable.get("MAIL CITY"), "Actual Application MAIL CITY does not match the expected MAIL CITY:");
                        }
                    }
                    break;
                case "MAIL STATE":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressState"), datatable.get("MAIL STATE"), "Actual Application MAIL STATE does not match the expected MAIL STATE:");
                        }
                    }
                    break;
                case "MAIL ZIP":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressZip"), datatable.get("MAIL ZIP"), "Actual Application MAIL ZIP does not match the expected MAIL ZIP:");
                        }
                    }
                    break;
                default:
                    fail("Key did not match Mailing or Residential address values");
            }
        }
    }

    @And("I verify Application Info for the Application with the retrieved response with appId")
    public void iVerifyApplicationInfoForTheApplicationWithTheRetrievedResponseWithAppId(Map<String, String> datatable) {
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "APPLICATION CYCLE":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationCycle"), datatable.get("APPLICATION CYCLE"), "Actual Application Cycle does not match expected Application Cycle:");
                    break;
                case "APPLICATION RECEIVED DATE":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationReceivedDate"), datatable.get("APPLICATION RECEIVED DATE"), "Actual APPLICATION RECEIVED DATE does not match expected APPLICATION RECEIVED DATE:");
                    break;
                case "CHANNEL":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.channelId"), datatable.get("CHANNEL"), "Actual APPLICATION CHANNEL does not match expected APPLICATION CHANNEL:");
                    break;
                case "APPLICATION SIGNATURE":
                    boolean expected = false;
                    if ("true".equals(datatable.get("APPLICATION SIGNATURE"))) {
                        expected = true;
                    }
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.applicationSignatureExistsInd"), expected, "Actual APPLICATION SIGNATURE does not match expected APPLICATION SIGNATURE:");
                    break;
                case "SIGNATURE DATE":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationSignatureDate"), datatable.get("SIGNATURE DATE"), "Actual APPLICATION SIGNATURE DATE does not match expected APPLICATION SIGNATURE DATE:");
                    break;
                case "APPLICATION STATUS":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationStatus"), datatable.get("APPLICATION STATUS"), "Actual APPLICATION STATUS does not match expected APPLICATION STATUS:");
                    break;
                default:
                    fail("Application Info Key does not match Entered Value");
            }
        }
    }

    @And("I verify Application Members Detail in the Application with the retrieved response with appId from {string} call")
    public void iVerifyApplicationMembersDetailInTheApplicationWithTheRetrievedResponseWithAppId(String callType, Map<String, String> datatable) {
        List<Map<String, Object>> applicationConsumers;
        if (callType.equalsIgnoreCase("Summary")) {
            applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data[0].applicationConsumers");
        } else {
            applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        }

        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "FULL NAME":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            String actualFirstName = eachApplicant.get("consumerFirstName").toString();
                            String actualLastName = eachApplicant.get("consumerLastName").toString();
                            String actualMi = eachApplicant.get("consumerMiddleName").toString();
                            String actualSuffix = eachApplicant.get("consumerSuffix").toString();
                            String actualFullName = actualFirstName + " " + actualMi + " " + actualLastName + " " + actualSuffix;
                            assertEquals(actualFullName, datatable.get("FULL NAME"), "Actual Application Member Full Name does not match:");
                        }
                    }
                    break;
                case "DOB":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("dateOfBirth").toString(), datatable.get("DOB"), "Actual Application Member Date of Birth does not match:");
                        }
                    }
                    break;
                case "SSN":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("ssn").toString(), datatable.get("SSN"), "Actual Application Member SSN does not match:");
                        }
                    }
                    break;
                case "GENDER":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("genderCode"), datatable.get("GENDER"), "Actual Application Member gender does not match:");
                        }
                    }
                    break;
                case "PROGRAMS":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            List<String> appMemProgramArray;
                            if (callType.equalsIgnoreCase("Summary")) {
                                appMemProgramArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data[0].applicationConsumers[" + counter.get() + "].programs");
                            } else {
                                appMemProgramArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[" + counter.get() + "].programs");
                            }
                            for (int i = 0; i < appMemProgramArray.size(); i++) {
                                if (callType.equalsIgnoreCase("Summary")) {
                                    appMemActualPrograms.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[" + counter.get() + "].programs[" + i + "].programType"));
                                } else {
                                    appMemActualPrograms.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + counter.get() + "].programs[" + i + "].programType"));
                                }
                            }
                            appMemExpectedPrograms.set(Arrays.asList(datatable.get("PROGRAMS").split(", ")));
                            Collections.sort(appMemActualPrograms.get());
                            Collections.sort(appMemExpectedPrograms.get());
                            assertEquals(appMemActualPrograms.get(), appMemExpectedPrograms.get(), "Application Member Actual PROGRAMS does not match:");
                        }
                        counter.set(counter.get()+1);
                    }
                    break;
                case "ARE YOU PREGNANT":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("pregnancyInd").toString(), datatable.get("ARE YOU PREGNANT"), "Actual Application Member Full Name does not match:");
                        }
                    }
                    break;
                case "NO BABIES EXPECTED":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("expectedBabies").toString(), datatable.get("NO BABIES EXPECTED"), "Actual Application Member NO BABIES EXPECTED does not match:");
                        }
                    }
                    break;
                case "EXPECTED DUE DATE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("expectedDueDate").toString(), datatable.get("EXPECTED DUE DATE"), "Actual Application Member EXPECTED DUE DATE does not match:");
                        }
                    }
                    break;
                case "EXTERNAL CONSUMER ID":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("externalConsumerId").toString(), datatable.get("EXTERNAL CONSUMER ID"), "Actual Application Member EXTERNAL CONSUMER ID does not match:");
                        }
                    }
                    break;
                case "EXTERNAL ID TYPE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("externalConsumerIdType").toString(), datatable.get("EXTERNAL ID TYPE"), "Actual Application Member EXTERNAL ID TYPE does not match:");
                        }
                    }
                    break;
                case "AM CONSUMER STATUS":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("applicationConsumerStatus").toString(), datatable.get("AM CONSUMER STATUS"), "Actual Application Member CONSUMER STATUS does not match: ");
                        }
                    }
                    break;
                //Below case compares response from Application Summary API
                case "CONSUMER STATUS":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("consumerStatus").toString(), datatable.get("CONSUMER STATUS"), "Application Member CONSUMER STATUS does not match expected CONSUMER STATUS: ");
                        }
                    }
                    break;
                case "CONSUMER ROLE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("consumerRoleType").toString(), datatable.get("CONSUMER ROLE"), "Application Member CONSUMER ROLE does not match expected CONSUMER ROLE: ");
                        }
                    }
                    break;
                case "FIRST NAME":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("consumerFirstName").toString(), datatable.get("FIRST NAME"), "Application Member First Name does not match with expected");
                        }
                    }
                    break;
                case "LAST NAME":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Application Member".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("consumerLastName").toString(), datatable.get("LAST NAME"), "Application Member Last Name does not match with expected");
                        }
                    }
                    break;
                default:
                    fail("Application Members detail Key does not match Entered Value");
            }
        }
    }

    @And("I verify Authorized Representative Detail in the Application with the retrieved response with appId from {string} call")
    public void iVerifyAuthorizedRepresentativeDetailInTheApplicationWithTheRetrievedResponseWithAppId(String callType, Map<String, String> datatable) {
        List<Map<String, Object>> applicationConsumers;
        if (callType.equalsIgnoreCase("Summary")) {
            applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data[0].applicationConsumers");
        } else {
            applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        }
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "FULL NAME":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            String actualFirstName = eachApplicant.get("consumerFirstName").toString();
                            String actualLastName = eachApplicant.get("consumerLastName").toString();
                            String actualMi = eachApplicant.get("consumerMiddleName").toString();
                            String actualFullName = actualFirstName + " " + actualMi + " " + actualLastName;
                            assertEquals(actualFullName, datatable.get("FULL NAME"), "Actual Authorized Representative Full Name does not match:");
                        }
                    }
                    break;
                case "AUTH TYPE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("consumerType").toString(), datatable.get("AUTH TYPE"), "Actual Authorized Representative AUTH TYPE does not match:");
                        }
                    }
                    break;
                case "ACCESS TYPE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("accessType").toString(), datatable.get("ACCESS TYPE"), "Actual Authorized Representative ACCESS TYPE does not match:");
                        }
                    }
                    break;
                case "CORRESPONDENCE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("correspondence").toString(), datatable.get("CORRESPONDENCE"), "Actual Authorized Representative CORRESPONDENCE does not match:");
                        }
                    }
                    break;
                case "AUTHORIZED":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("authorizedRepSignature").toString(), datatable.get("AUTHORIZED"), "Actual Authorized Representative AUTH TYPE does not match:");
                        }
                    }
                    break;
                case "ORG NAME":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("authorizedRepOrgName").toString(), datatable.get("ORG NAME"), "Actual Authorized Representative ORG NAME does not match:");
                        }
                    }
                    break;
                case "ID NUM":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("authorizedRepExternalId").toString(), datatable.get("ID NUM"), "Actual Authorized Representative ID NUM does not match:");
                        }
                    }
                    break;
                case "START DATE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(eachApplicant.get("authorizedRepAppStartDate").toString(), datatable.get("START DATE")), "Authorized Representative Start Date value does not match:");
                        }
                    }
                    break;
                case "END DATE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(eachApplicant.get("authorizedRepAppEndDate").toString(), datatable.get("END DATE")), "Authorized Representative END DATE value does not match:");
                        }
                    }
                    break;
                case "AUTHORIZED SIGNATURE DATE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(eachApplicant.get("authorizedRepSignatureDate").toString(), datatable.get("AUTHORIZED SIGNATURE DATE")), "Authorized Representative AUTHORIZED SIGNATURE DATE value does not match:");
                        }
                    }
                    break;
                case "AUTH REP CONSUMER STATUS":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertNull(eachApplicant.get("applicationConsumerStatus"), "Expected Auth Rep Consumer Status to be null but found not null");
                        }
                    }
                    break;
                //Below case compares response from Application Summary API
                case "CONSUMER STATUS":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertNull(eachApplicant.get("applicationConsumerStatus"), "Authorized Rep CONSUMER STATUS does not match expected CONSUMER STATUS: ");
                        }
                    }
                    break;
                case "CONSUMER ROLE":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("consumerRoleType").toString(), datatable.get("CONSUMER ROLE"), "Authorized Rep CONSUMER ROLE does not match expected CONSUMER ROLE: ");
                        }
                    }
                    break;
                case "FIRST NAME":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("consumerFirstName").toString(), datatable.get("FIRST NAME"), "Authorized Rep First Name does not match with expected");
                        }
                    }
                    break;
                case "LAST NAME":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            assertEquals(eachApplicant.get("consumerLastName").toString(), datatable.get("LAST NAME"), "Authorized Rep Last Name does not match with expected");
                        }
                    }
                    break;
                default:
                    Assert.fail("Entered Key did not match existing keys");
            }
        }
    }

    @And("I verify Authorized Representative address Detail in the Application with the retrieved response with appId")
    public void iVerifyAuthorizedRepresentativeAddressDetailInTheApplicationWithTheRetrievedResponseWithAppId
            (Map<String, String> datatable) {
        List<Map<String, Object>> applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        for (Map<String, Object> eachApplicant : applicationConsumers) {
            if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                authorizedRepAdress.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data.applicationConsumers[" + authCounter.get() + "].applicationConsumerAddress[0]"));
            }
            authCounter.set(authCounter.get()+1);
        }
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "ADDRESS LINE 1":
                    assertEquals(authorizedRepAdress.get().get("addressStreet1"), datatable.get("ADDRESS LINE 1"), "Actual Auth Representative Address line 1 did not match:");
                    break;
                case "ADDRESS LINE 2":
                    assertEquals(authorizedRepAdress.get().get("addressStreet2"), datatable.get("ADDRESS LINE 2"), "Actual Auth Representative Address line 2 did not match:");
                    break;
                case "CITY":
                    assertEquals(authorizedRepAdress.get().get("addressCity"), datatable.get("CITY"), "Actual Auth Representative City did not match:");
                    break;
                case "STATE":
                    assertEquals(authorizedRepAdress.get().get("addressState"), datatable.get("STATE"), "Actual Auth Representative State did not match:");
                    break;
                case "ZIP":
                    assertEquals(authorizedRepAdress.get().get("addressZip"), datatable.get("ZIP"), "Actual Auth Representative Zip did not match:");
                    break;
                default:
                    Assert.fail("Entered Key did not match existing keys");
            }
        }
    }

    @And("I initiate and Post created application with update status API with following values")
    public void iInitiateAndPostCreatedApplicationWithUpdateStatusAPIWithFollowingValues
            (Map<String, String> datatable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/updateStatusAPI.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", applicationIdAPI.get());
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "APPLICATION ID":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", datatable.get("APPLICATION ID"));
                    break;
                case "NEW APPLICATION STATUS":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("newApplicationStatus", datatable.get("NEW APPLICATION STATUS"));
                    break;
                case "REASON":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationChangeReason", datatable.get("REASON"));
                    break;
                case "UPDATED BY":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", datatable.get("UPDATED BY"));
                    break;
                case "CREATED BY":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", datatable.get("CREATED BY"));
                    break;
                case "DUPLICATED APPLICATION ID":
                    if (datatable.get("DUPLICATED APPLICATION ID").startsWith("-")) {
                        int IDDistinction = Integer.parseInt(datatable.get("DUPLICATED APPLICATION ID"));
                        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("duplicateApplicationId", String.valueOf(Integer.parseInt(applicationIdAPI.get()) + IDDistinction));
                    } else {
                        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("duplicateApplicationId", datatable.get("DUPLICATED APPLICATION ID"));
                    }
                    break;
                default:
                    Assert.fail("Entered Key did not match existing keys: " + eachVerifyValue);
            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("requestParams = " + requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateStatus);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(requestParams.get().toString());
        System.out.println("Status change Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.toString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    public JsonObject getRequestParams() {
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/createApplication_1.json").jsonElement.getAsJsonObject();
    }

    public JsonObject getAppRequestPayload() {
        JsonObject requestpayload = appPayload.get();
        return requestpayload;
    }

    public String getApplicationIdAPI() {
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        applicationIdAPI.set(appData != null ? String.valueOf(appData.get("applicationId")) : null);
        return applicationIdAPI.get();
    }

    @When("I send request to matching criteria rule AppMember First Last DOB as")
    public void i_send_request_to_matching_criteria_rule_AppMember_First_Last_DOB_as
            (List<Map<String, String>> data) {
        Map<String, String> searchInput = prepareInputData(data.get(0));
        System.out.println("Input: " + searchInput);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(matchApplicationMember);

        String requestBody = new Gson().toJson(searchInput);
        System.out.println("Request body: " + requestBody);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestBody);
    }

    @Then("I verify all application consumers in the matching criteria rule AppMember First Last DOB have")
    public void i_verify_all_application_consumers_in_the_matching_criteria_rule_AppMember_First_Last_DOB_have
            (List<Map<String, String>> data) {
        // note, this is search functionality and we are expecting every response will have this(same) data
        Map<String, String> expectedResultData = prepareInputData(data.get(0));

        List<HashMap<String, String>> response = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        System.out.println("RESPONSE:\n" + response);
        System.out.println("Number of results:\n" + response.size());
        for (Map<String, String> member : response) {
            for (String key : expectedResultData.keySet()) {
                if (key.equalsIgnoreCase("dob") && expectedResultData.get(key).equalsIgnoreCase("any")) {
                    // if dob any we don't need to validate because search will return members with any dob
                    continue;
                }

                Assert.assertEquals(member.get(key), expectedResultData.get(key), "AppMember_First_Last_DOB does not match");
            }

        }
    }

    private Map<String, String> prepareInputData(Map<String, String> searchInput) {
        Map<String, String> res = new HashMap<>();
        for (String key : searchInput.keySet()) {
            if (searchInput.get(key).equalsIgnoreCase("base")) {
                // get correct values from member creation step
                switch (key) {
                    case "lastName":
                        res.put(key, createdMemberDetails.get().get("consumerLastName"));
                        break;
                    case "firstName":
                        res.put(key, createdMemberDetails.get().get("consumerFirstName"));
                        break;
                    case "dob":
                        res.put(key, createdMemberDetails.get().get("dateOfBirth"));
                        break;
                    default:
                        res.put(key, createdMemberDetails.get().get(key));
                }
            } else {
                res.put(key, searchInput.get(key));
            }
        }

        return res;
    }

    @Then("I verify there is no application consumers in the matching criteria rule AppMember First Last DOB")
    public void i_verify_there_is_no_application_consumers_in_the_matching_criteria_rule_AppMember_First_Last_DOB
            () {
        List<HashMap<String, String>> response = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        Assert.assertEquals(response.size(), 0, "Data should be empty in matching criteria rule AppMember First Last DOB");
    }

    @Then("I verify number of results in all application consumers in the matching criteria is {string}")
    public void i_verify_number_of_results_in_all_application_consumers_in_the_matching_criteria_is
            (String expectedResNumber) {
        List<HashMap<String, String>> response = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        Assert.assertEquals(String.valueOf(response.size()), expectedResNumber, "Wrong result number");
    }

    @Then("I verify the following Authorized Representative values from the GET Application with ApplicationId")
    public void iVerifyTheFollowingAuthorizedRepresentativeValuesFromTheGETApplicationWithApplicationId
            (Map<String, String> datatable) {
        List<Map<String, Object>> applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "CREATED BY":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            if (datatable.get("CREATED BY").equalsIgnoreCase("base")) {
                                assertEquals(eachApplicant.get("createdBy").toString(), loggedInUserId, "Actual Authorized Representative CREATED BY does not match: ");
                            } else {
                                assertEquals(eachApplicant.get("createdBy").toString(), datatable.get("CREATED BY"), "Actual Authorized Representative CREATED BY does not match: ");
                            }
                        }
                    }
                    break;
                case "CREATED ON":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            if ("today's date and hour".equals(datatable.get("CREATED ON"))) {
                                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(eachApplicant.get("createdOn").toString(), currentDateHour.get()), "Application Member CreatedOn value does not match: ");
                            } else {
                                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(eachApplicant.get("createdOn").toString(), datatable.get("CREATED ON")), "Application Member CreatedOn value does not match: ");
                            }
                        }
                    }
                    break;
                case "UPDATED BY":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            if (datatable.get("UPDATED BY").equalsIgnoreCase("base")) {
                                assertEquals(eachApplicant.get("updatedBy").toString(), loggedInUserId, "Actual Authorized Representative UPDATED BY does not match: ");
                            } else {
                                assertEquals(eachApplicant.get("updatedBy").toString(), datatable.get("UPDATED BY"), "Actual Authorized Representative UPDATED BY does not match: ");
                            }
                        }
                    }
                    break;
                case "UPDATED ON":
                    for (Map<String, Object> eachApplicant : applicationConsumers) {
                        if ("Authorized Rep".equals(eachApplicant.get("consumerRoleType"))) {
                            if ("today's date and hour".equals(datatable.get("UPDATED ON"))) {
                                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(eachApplicant.get("updatedOn").toString(), currentDateHour.get()), "Application Member UPDATED ON value does not match: ");
                            } else {
                                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(eachApplicant.get("updatedOn").toString(), datatable.get("UPDATED ON")), "Application Member UPDATED ON value does not match: ");
                            }
                        }
                    }
                    break;
                default:
                    Assert.fail("Entered Key did not match existing keys");
            }
        }
    }

    @Then("I verify that application priority and application deadlineDate on response")
    public void i_verify_that_application_priority_and_application_deadlineDate_on_response() {
        System.out.println("Application priority is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationPriority"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationPriority").equals("Normal"));

        System.out.println("Application deadline date is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationDeadlineDate"));
        assertTrue(EventsUtilities.isOnlyValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationDeadlineDate")));
    }

    @Then("I verify application status as {string} on the response")
    public void i_verify_application_status_as_on_the_response(String appStatus) {
        applicationCode.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationCode"));
        applicationDeadlineDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationDeadlineDate"));
        System.out.println("Application Status is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationStatus"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationStatus").equals(appStatus), "Application status is not verified for ATS.Expected Status: " + appStatus + ", but found : " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationStatus"));
    }

    @And("I initiate and POST application with following Application type and application Deadline Date")
    public void iInitiateAndPOSTApplicationWithFollowingApplicationTypeAndApplicationDeadlineDate
            (List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        requestParams.set(getCreateApplicationRequestFromJSONFile("crm/ats/createApplication_1.json"));
        for (String key : keysToOverride.keySet()) {
            switch (key) {
                case "applicationType":
                    if ("Long Term Care".equals(keysToOverride.get(key))) {
                        requestParams.get().addProperty(key, keysToOverride.get(key));
                        requestParams.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").remove(1);
                        requestParams.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").remove(0);
                    }
                    break;
                case "applicationDeadlineDate":
                    requestParams.get().addProperty("applicationReceivedDate", getCurrentDateInYearFormat());
                    if ("PRESENT".equals(keysToOverride.get(key))) {
                        requestParams.get().addProperty(key, getCurrentDateInYearFormat());
                        System.out.println(requestParams.toString());
                        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
                        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
                        applicationIdAPI.set(appData != null ? String.valueOf(appData.get("applicationId")) : null);
                        System.out.println("Application id is for ATS: " + applicationIdAPI.get());
                    } else if ("FUTURE".equals(keysToOverride.get(key))) {
                        requestParams.get().addProperty(key, getFutureDateFormatYYYYMMdd(radomNumber(10)));
                        System.out.println(requestParams.toString());
                        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
                        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
                        applicationIdAPI.set(appData != null ? String.valueOf(appData.get("applicationId")) : null);
                        System.out.println("Application id is for ATS: " + applicationIdAPI.get());
                    } else if ("PAST".equals(keysToOverride.get(key))) {
                        requestParams.get().addProperty(key, getPriorDateFormatYYYYMMdd(radomNumber(10)));
                        System.out.println(requestParams.toString());
                        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
                    }
                    break;
                default:
                    Assert.fail("Entered Key did not match existing keys");
            }
        }
    }

    @And("I verify the ATS POST save application error message for applicationDeadlineDate marked before applicationReceivedDate")
    public void iVerifyTheATSPOSTSaveApplicationErrorMessageForApplicationDeadlineDateMarkedBeforeApplicationReceivedDate
            () {
        String dlDateSetInPastMsg = "Application Deadline Date is invalid. It should be greater than or equal to applicaiton received date.";
        String actualErrorMsg = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("errors[0]").get("message").toString();
        Assert.assertEquals(actualErrorMsg, dlDateSetInPastMsg, "Expected a error Message in the response but non found");
    }

    @And("I initiate save applications api with application level key values")
    public void iInitiateSaveApplicationsApiWithApplicationLevelKeyValues
            (List<Map<String, String>> data) {
        initiateAppLevelData(data);
    }

    @And("I initiate save applications api using previous request")
    public void iUsePreviousRequest() {

        appRequestPayload = appPayload.get();
    }

    @And("I initiate save applications api with {string} data for consumer {int}")
    public void iInitiateSaveApplicationsApiWithDataForConsumer(String consumerType,
                                                                int appConsumerNum, List<Map<String, String>> data) {
        switch (consumerType) {
            case "PRIMARY INDIVIDUAL":
                initiatePrimaryIndividual(appConsumerNum, data);
                break;
            case "APPLICATION MEMBER":
                initiateApplicationMember(appConsumerNum, data);
                break;
            case "AUTHORIZED REPRESENTATIVE":
                initiateAuthorizedRepresentative(appConsumerNum, data);
                break;
        }
    }

    @And("I initiate create {string} api for existing application")
    public void iInitiateCreateApplicationMemberForExistngApplication(String consumerType, List<Map<String, String>> data) {
        if (consumerType.equalsIgnoreCase("APPLICATION MEMBER")) {
            appRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/appMember.json");
        } else {
            appRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/authRep.json");
        }
        Map<String, String> keysToOverride = data.get(0);

        for (String key : keysToOverride.keySet()) {
            switch (keysToOverride.get(key)) {
                case "RANDOM FIRST":
                    synchronized (amFirstName){
                        amFirstName.set(RandomStringUtils.randomAlphabetic(7));
                    }
                    appRequestPayload.addProperty(key, amFirstName.get());
                    break;
                case "RANDOM LAST":
                    synchronized (amLastName){
                        amLastName.set(RandomStringUtils.randomAlphabetic(7));
                    }
                    appRequestPayload.addProperty(key, amLastName.get());
                    break;
                default:
                    if (keysToOverride.containsKey("addressZip")) {
                        JsonObject appAddress = getCreateApplicationRequestFromJSONFile("crm/ats/consumerAddress.json");
                        appAddress.addProperty("addressZip", keysToOverride.get("addressZip"));
                        appRequestPayload.getAsJsonArray("applicationConsumerAddress").add(appAddress);
                    } else
                        appRequestPayload.addProperty(key, keysToOverride.get(key));

            }

        }

        System.out.println(appRequestPayload);
    }

    @And("I POST ATS save application api")
    public void iPOSTATSSaveApplicationApi() {
        appPayload.set(appRequestPayload);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload);
        waitFor(2);
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        applicationIdAPI.set(appData != null ? String.valueOf(appData.get("applicationId")) : null);
        duplicateId.set(applicationIdAPI.get());
        System.out.println("Application id is for ATS: " + applicationIdAPI.get());
        applicationIdAPI.set(applicationIdAPI.get());
    }

    // This is used for posting two applications that match depending on given criteria
    @And("I post on ATS save application api and update following values for {string} consumer {int} with following data for matching application")
    public void iDoublePOSTATSSaveApplicationApi(String entityType, int appConsumerNum, List<Map<String, String>> data) {
        duplicateApplicationIds.set(new ArrayList<>());
        appPayload.set(appRequestPayload);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload);
        jsonPathEvaluator1.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator);
        i_get_response_from_application_ats_api_with_status_code_and_status("200", "success");
//        sendEventAndCreateLinksController.iGetTheApplicationConsumerIDConsumerRoleTypeAndUserIDFromAPIResponse();
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        applicationIdAPI.set(appData != null ? String.valueOf(appData.get("applicationId")) : null);
        duplicateApplicationIds.get().add(applicationIdAPI.get());
        System.out.println("Application id is for ATS: " + applicationIdAPI.get());
        createdConsumerDetailsForAppl1.set(iGetConsumersForApplication(applicationIdAPI.get()));
        waitFor(8);
        appPayload.set(appRequestPayload);
        switch (entityType) {
            case "PRIMARY INDIVIDUAL":
                initiatePrimaryIndividual(appConsumerNum, data);
                break;
            case "APPLICATION MEMBER":
                if (appConsumerNum > 1) {
                    iInitiateCreateApplicationApiWithA(entityType);
                }
                initiateApplicationMember(appConsumerNum, data);
                break;
            case "AUTHORIZED REPRESENTATIVE":
                initiateAuthorizedRepresentative(appConsumerNum, data);
                break;
            case "APPLICATION":
                initiateAppLevelData(data);
                break;
            default:
                Assert.fail(" Could not find matching case for the value");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload);
        jsonPathEvaluator2.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator);
        HashMap appData2 = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        applicationIdAPI.set(appData2 != null ? String.valueOf(appData2.get("applicationId")) : null);
        duplicateId.set(applicationIdAPI.get());
        duplicateApplicationIds.get().add(applicationIdAPI.get());
        System.out.println(" Latest Application id is for ATS: " + applicationIdAPI.get());
        createdConsumerDetailsForAppl2.set(iGetConsumersForApplication(applicationIdAPI.get()));
    }

    @And("I get consumers for application {string}")
    public Map<String, String> iGetConsumersForApplication(String applicationId) {
        consumerRoleList.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerRoleType"));
        consumerIdList.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.applicationConsumerId"));
        Map<String, String> createdConsumerDetailsForAppl = new HashMap<>();
        for (int i = 0; i < consumerIdList.get().size(); i++) {
            createdConsumerDetailsForAppl.put("" + consumerIdList.get().get(i), consumerRoleList.get().get(i));
        }
        return createdConsumerDetailsForAppl;
    }

    @Then("I verify the following for application {int} created above")
    public void iSetApplicationId(int applicationNumber) {
        applicationId.set(duplicateApplicationIds.get().get(applicationNumber - 1));
        applicationIdAPI.set(applicationId.get());
        System.out.println(applicationId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator = applicationNumber == 1 ? jsonPathEvaluator1.get() : ((applicationNumber == 2) ? jsonPathEvaluator2.get() : null);
        createdConsumerDetails.set(applicationNumber == 1 ? createdConsumerDetailsForAppl1.get() : ((applicationNumber == 2) ? createdConsumerDetailsForAppl2.get() : null));
    }

    @And("I save the case Id linked to the application")
    public String saveCaseLinkedToApplication() {
        applicationLinks();
        List<Map<String, String>> linksResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("links");
        String caseId = String.valueOf(linksResponse.get(0).get("id"));
        System.out.println(caseId);
        System.out.println(applicationId);
        if (caseId != null) {
            World.generalSave.get().put("CASEID", caseId);
            applicationToCase.get().put(applicationId.get(), caseId);
        } else {
            Assert.fail("Application is not linked to a case");
        }
        return caseId;
    }

    @And("I verify application is linked to previous case")
    public void verifyCaseLinkedToApplication() {
        Assert.assertEquals(applicationToCase.get().get(duplicateApplicationIds.get().get(0)), applicationToCase.get().get(duplicateApplicationIds.get().get(1)));
    }

    @And("I initiate save applications api consumer {int} with program")
    public void iInitiateSaveApplicationsApiConsumerWithProgram(int appConsumerNum, List<String> data) {
        addProgramsToConsumer(appConsumerNum, data);
    }

    @And("I initiate create application api with a {string}")
    public void iInitiateCreateApplicationApiWithA(String consumerType) {
        addConsumerToApplication(consumerType);
    }

    /**
     * Application level key : value
     *
     * @param data required applicationType: Medical Assistance / Long Term Care
     * @param data required applicationReceivedDate in yyyy-MM-dd
     */
    public void initiateAppLevelData(List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        for (String key : keysToOverride.keySet()) {
            if ("CURRENT YYYYMMDD".equals(keysToOverride.get(key))) {
                appRequestPayload.addProperty(key, getCurrentDateInYearFormat());
            } else if ("PAST YYYYMMDD".equals(keysToOverride.get(key))) {
                appRequestPayload.addProperty(key, getPriorDateFormatYYYYMMdd(radomNumber(10)));
            } else if (keysToOverride.get(key).contains("PRIOR")) {
                appRequestPayload.addProperty(key, getPriorDateFormatYYYYMMdd(Integer.parseInt(keysToOverride.get(key).substring(6))));
            } else if ("FUTURE YYYYMMDD".equals(keysToOverride.get(key))) {
                appRequestPayload.addProperty(key, getFutureDateFormatYYYYMMdd(radomNumber(10)));
            } else if ("RANDOM EXTERNAL APP ID".equals(keysToOverride.get(key))) {
                synchronized (externalAppID) {
                    externalAppID.set(RandomStringUtils.randomAlphanumeric(36));
                }
                appRequestPayload.addProperty(key, externalAppID.get());
            } else if ("created previously".equals(keysToOverride.get(key))) {
                if (appIdList.get().isEmpty()) {
                    if (applicationIdAPI.get().equals("")) {
                        System.out.println("Printing apiApplicationID: "+apiApplicationID.get());
                        appRequestPayload.addProperty(key, apiApplicationID.get());
                      //  appRequestPayload.addProperty(key, applicationIdAPI.get());

                    } else {
                        System.out.println("Printing applicationIdAPI: "+applicationIdAPI.get());
                        appRequestPayload.addProperty(key, applicationIdAPI.get());
                    }
                } else {

                appRequestPayload.addProperty(key, appIdList.get().get(0));
            }

            } else if ("applicationReceivedLanguage".equals(keysToOverride.get(key))) {
                appRequestPayload.addProperty(key, keysToOverride.get(key));
            } else if (keysToOverride.get(key).equalsIgnoreCase("null")) {
                appRequestPayload.add(key, null);
            } else {
                appRequestPayload.addProperty(key, keysToOverride.get(key));
            }
        }

    }

    public void initiateAppLevelDataForMatching(String apptype) {
        appRequestPayload.addProperty("applicationReceivedDate", getPriorDateFormatYYYYMMdd(radomNumber(10)));
        if ("LTC SUBMITTED".equals(apptype)) {
            appRequestPayload.addProperty("applicationType", "Long Term Care");
            appRequestPayload.addProperty("submittedInd", "true");
        } else if ("MA SUBMITTED".equals(apptype)) {
            appRequestPayload.addProperty("applicationType", "Medical Assistance");
            appRequestPayload.addProperty("submittedInd", "true");
        } else {
            appRequestPayload.addProperty("applicationType", apptype);
        }
    }

    public void initiatePrimaryIndividual(int appConsumerNum, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        for (String key : keysToOverride.keySet()) {
            switch (keysToOverride.get(key)) {
                case "RANDOM FIRST":
                    synchronized (piFirstName){
                    piFirstName.set(RandomStringUtils.randomAlphabetic(7));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piFirstName.get());
                    break;
                case "RANDOM LAST":
                    isApplicationDuplicate.set(false);
                    synchronized (piLastName){
                        piLastName.set(RandomStringUtils.randomAlphabetic(7));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piLastName.get());
                    break;
                case "RANDOM MIDDLE INITIAL":
                    synchronized (piMiddleName){
                        piMiddleName.set(RandomStringUtils.randomAlphabetic(1));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piMiddleName.get());
                    break;
                case "RANDOM SUFFIX":
                    synchronized (piSuffix){
                        piSuffix.set(RandomStringUtils.randomAlphabetic(3));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piSuffix.get());
                    break;
                case "RANDOM DOB":
                    synchronized (piDOB){
                        piDOB.set(BrowserUtils.getPriorDateFormatYYYYMMdd(Integer.parseInt(RandomStringUtils.randomNumeric(4))));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piDOB.get());
                    break;
                case "RANDOM SSN":
                    synchronized (piSSN){
                        piSSN.set(RandomStringUtils.randomNumeric(9));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piSSN.get());
                    break;
                case "SSN from case":
                    //   caseController.apiSearchConsumerSSN;
                    System.out.println("SSN from case loader: " + caseController.apiSearchConsumerSSN);
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, caseController.apiSearchConsumerSSN.get());
                    break;
                case "SSN from EE":
                    System.out.println("SSN from case EE: "+consumerPopulationDmnController.consumerSSN.get());
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, consumerPopulationDmnController.consumerSSN.get());
                    break;
                case "true":
                    synchronized (piSSN){
                        piSSN.set(RandomStringUtils.randomNumeric(9));
                    }
                  //  piSSN = RandomStringUtils.randomNumeric(9);
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, true);
                    break;
                case "FUTURE DATE":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, getFutureDateFormatYYYYMMdd(20));
                    break;
                case "DUPLICATE FIRST":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateFirstname.get());
                    isApplicationDuplicate.set(true);
                    break;
                case "DUPLICATE LAST":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateLastName.get());
                    break;
                case "DUPLICATE DOB":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateDOB.get());
                    break;
                case "DUPLICATE SSN":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateSSN.get());
                    break;
                case "RANDOM FIRST FOR NO MATCH":
                    synchronized (piFirstName){
                        piFirstName.set(RandomStringUtils.randomAlphabetic(13));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piFirstName.get());
                    break;
                case "RANDOM LAST FOR NO MATCH":
                    synchronized (piLastName){
                        piLastName.set(RandomStringUtils.randomAlphabetic(13));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piLastName.get());
                    break;
                case "MAX CHARACTER FIRST":
                    synchronized (piFirstName){
                        piFirstName.set(RandomStringUtils.randomAlphabetic(30));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piFirstName.get());
                    break;
                case "MAX CHARACTER LAST":
                    synchronized (piLastName){
                        piLastName.set(RandomStringUtils.randomAlphabetic(30));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, piLastName.get());
                    break;
                case "empty":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, "");
                    break;
                case "null":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, (String) null);
                    break;
                case "applicationConsumerIdPI":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, consumersController.getAppConsumerIDByRole("PRIMARY INDIVIDUAL"));
                    break;
                case "AUTO CREATED EXTERNAL ID":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, consumerExternalId.get());
                    break;
                case "NEW EXTERNAL ID":
                    synchronized (consumerExternalId){
                        consumerExternalId.set(RandomStringUtils.randomAlphanumeric(10));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, consumerExternalId.get());
                    break;
                default:
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, keysToOverride.get(key));
            }
        }
    }

    public void initiateApplicationMember(int appConsumerNum, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        for (String key : keysToOverride.keySet()) {
            switch (keysToOverride.get(key)) {
                case "RANDOM FIRST":
                    synchronized (amFirstName){
                        amFirstName.set(RandomStringUtils.randomAlphabetic(7));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, amFirstName.get());
                    break;
                case "RANDOM LAST":
                    synchronized (amLastName){
                        amLastName.set(RandomStringUtils.randomAlphabetic(7));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, amLastName.get());
                    break;
                case "RANDOM MIDDLE INITIAL":
                    synchronized (amMiddleName){
                        amMiddleName.set(RandomStringUtils.randomAlphabetic(1));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, amMiddleName.get());
                    break;
                case "RANDOM SUFFIX":
                    synchronized (amSuffix){
                        amSuffix.set(RandomStringUtils.randomAlphabetic(3));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, amSuffix.get());
                    break;
                case "RANDOM DOB":
                    synchronized (amDOB){
                        amDOB.set(BrowserUtils.getPriorDateFormatYYYYMMdd(9000));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, amDOB.get());
                    break;
                case "RANDOM SSN":
                    synchronized (amSSN){
                        amSSN.set(RandomStringUtils.randomNumeric(9));
                    }
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, amSSN.get());
                    break;
                case "FUTURE DATE":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, getFutureDateFormatYYYYMMdd(20));
                    break;
                case "DUPLICATE SSN":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateSSN.get());
                    break;
                case "DUPLICATE FIRST":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateFirstname.get());
                    break;
                case "DUPLICATE LAST":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateLastName.get());
                    break;
                case "DUPLICATE DOB":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateDOB.get());
                    break;
                case "DUPLICATE SSN AM":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateSSNAm.get());
                    break;
                case "DUPLICATE FIRST AM":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateFirstnameAm.get());
                    break;
                case "DUPLICATE LAST AM":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateLastNameAm.get());
                    break;
                case "DUPLICATE DOB AM":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateDOBAm.get());
                    break;
                case "empty":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, "");
                    break;
                case "null":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, (String) null);
                    break;
                case "applicationConsumerIdAM":
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, consumersController.getAppConsumerIDByRole("APPLICATION MEMBER"));
                    break;
                default:
                    appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, keysToOverride.get(key));
            }
        }
    }

    public void initiateAuthorizedRepresentative(int appConsumerNum, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        for (String key : keysToOverride.keySet()) {
            if ("RANDOM FIRST".equals(keysToOverride.get(key))) {
                synchronized (authRepFirstName){
                    authRepFirstName.set(RandomStringUtils.randomAlphabetic(7));
                }
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, authRepFirstName.get());
            } else if ("RANDOM LAST".equals(keysToOverride.get(key))) {
                synchronized (authRepLastName){
                    authRepLastName.set(RandomStringUtils.randomAlphabetic(7));
                }
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, authRepLastName.get());
            } else if ("CURRENT TIMESTAMP".equals(keysToOverride.get(key))) {
                synchronized (currentTimeStamp){
                    currentTimeStamp.set(getCurrentDateInYearFormat() + "T10:54:57.274000+00:00");
                }
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, currentTimeStamp.get());
            } else if ("PAST TIMESTAMP".equals(keysToOverride.get(key))) {
                synchronized (pastTimeStamp){
                    pastTimeStamp.set(getPriorDateFormatYYYYMMdd(20) + "T10:54:57.274000+00:00");
                }
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, pastTimeStamp.get());
            } else if ("FUTURE TIMESTAMP".equals(keysToOverride.get(key))) {
                synchronized (futureTimeStamp){
                    futureTimeStamp.set(getFutureDateFormatYYYYMMdd(20) + "T10:54:57.274000+00:00");
                }
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, futureTimeStamp.get());
            } else if ("empty".equals(keysToOverride.get(key))) {
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, "");
            } else if ("null".equals(keysToOverride.get(key))) {
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, (String) null);
            } else if ("applicationConsumerIdAP".equals(keysToOverride.get(key))) {
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, consumersController.getAppConsumerIDByRole("Authorized Rep"));
            } else if ("DUPLICATE FIRST".equals(keysToOverride.get(key))) {
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateFirstname.get());
            } else if ("DUPLICATE LAST".equals(keysToOverride.get(key))) {
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, duplicateLastName.get());
            } else {
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty(key, keysToOverride.get(key));
            }
        }
    }


    /**
     * @param appConsumerNum is the order number in applicationConsumers array
     * @param data           Medical Assistance: Medicaid, CHIP, Pregnancy Assistance
     * @param data           Long Term Care: HCBS
     * @param data           If Not Applying to any programs: notApplying
     */
    public void addProgramsToConsumer(int appConsumerNum, List<String> data) {
        for (String eachProgram : data) {
            if (eachProgram.equalsIgnoreCase("notApplying")) {
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().addProperty("notApplyingIndicator", true);
            } else {
                JsonObject program = getCreateApplicationRequestFromJSONFile("crm/ats/appProgram.json");
                program.addProperty("programType", eachProgram);
                appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().getAsJsonArray("programs").add(program);
            }
        }
    }

    public void addConsumerToApplication(String consumerType) {
        if ("APPLICATION MEMBER".equals(consumerType)) {
            JsonObject applicationConsumers = getCreateApplicationRequestFromJSONFile("crm/ats/appMember.json");
            appRequestPayload.getAsJsonArray("applicationConsumers").add(applicationConsumers);

        } else if ("AUTHORIZED REPRESENTATIVE".equals(consumerType)) {
            JsonObject applicationConsumers = getCreateApplicationRequestFromJSONFile("crm/ats/authRep.json");
            appRequestPayload.getAsJsonArray("applicationConsumers").add(applicationConsumers);
        }
    }

    @And("I initiate save applications api consumer {int} with applicationConsumerPhone")
    public void iInitiateSaveApplicationsApiConsumerWithApplicationConsumerPhone(
            int appConsumerNum, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        JsonObject appPhone = getCreateApplicationRequestFromJSONFile("crm/ats/consumerPhone.json");
        for (String key : keysToOverride.keySet()) {
            if ("RANDOM PHONE".equals(keysToOverride.get(key))) {
                randomPhonNum.set("7" + RandomStringUtils.randomNumeric(9));
                appPhone.addProperty(key, randomPhonNum.get());
            } else {
                appPhone.addProperty(key, keysToOverride.get(key));
            }
        }
        appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().getAsJsonArray("applicationConsumerPhone").add(appPhone);
    }

    @And("I initiate save applications api consumer {int} with applicationConsumerEmail")
    public void iInitiateSaveApplicationsApiConsumerWithApplicationConsumerEmail(
            int appConsumerNum, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        JsonObject appEmail = getCreateApplicationRequestFromJSONFile("crm/ats/consumerEmail.json");
        for (String key : keysToOverride.keySet()) {
            appEmail.addProperty(key, keysToOverride.get(key));
        }
        appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().getAsJsonArray("applicationConsumerEmail").add(appEmail);
    }

    @And("I initiate save applications api consumer {int} with applicationConsumerAddress")
    public void iInitiateSaveApplicationsApiConsumerWithApplicationConsumerAddress(
            int appConsumerNum, List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        JsonObject appAddress = getCreateApplicationRequestFromJSONFile("crm/ats/consumerAddress.json");
        for (String key : keysToOverride.keySet()) {
            appAddress.addProperty(key, keysToOverride.get(key));
        }
        appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").add(appAddress);
    }

    @And("I initiate save applications api consumer {int} with consumerOptInInformation")
    public void iInitiateSaveApplicationsApiConsumerWithConsumerOptInInformation(
            int appConsumerNum, List<String> data) {
        for (String eachOptIN : data) {
            appRequestPayload.getAsJsonArray("applicationConsumers").get(appConsumerNum).getAsJsonObject().getAsJsonArray("consumerOptInInformation").add(eachOptIN);
        }
    }

    @When("I initiate the View Application Summary API for the Created Application")
    public void initiateViewAppSummaryAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(appSummary + "?applicationId=" + applicationIdAPI.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
    }

    @When("I run the View Application Summary API for the Created Application")
    public void runViewApplicationSummaryAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("GET View API Summary Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("success"));
        System.out.println("View Summary response is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I initiate and run the View Application Summary API for the Created Application with {string} query")
    public void initiateAndRunViewApplicationSummaryAPIWithQuery(String idType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(appSummary);
        Map<String, String> queryParams = new HashMap<>();
        if (idType.equalsIgnoreCase("ApplicationID")) {
            queryParams.put("applicationId", applicationIdAPI.get());
        } else if (idType.equalsIgnoreCase("ExternalApplicationID")) {
            queryParams.put("externalApplicationId", externalAppID.get());
        } else if (idType.equalsIgnoreCase("EMPTY EXTERNAL APP ID")) {
            queryParams.put("externalApplicationId", "");
        } else if (idType.equalsIgnoreCase("EMPTY APPLICATION ID")) {
            queryParams.put("applicationId", "");
        }
        System.out.println(queryParams);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPIAndQuery(queryParams);
        System.out.println("GET View API Summary Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        if (idType.toUpperCase().contains("EMPTY")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("invalid"), "Status is not verified for Invalid External Application ID or Application ID");
        } else {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("success"));
        }
        System.out.println("View Summary response is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify all the eligibility status for the programs set to {string} for Application Consumers")
    public void iVerifyAllTheEligibilityStatusForTheProgramsSetToForApplicationConsumers(String eligibilityStatus) {
        waitFor(2);
        int k;
        List<Map<String, Object>> applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        for (k = 0; k < applicationConsumers.size(); k++) {
            if (!"Authorized Rep".equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerRoleType"))) {
                List<Map<String, Object>> programs = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[" + k + "].programs");
                for (Map<String, Object> program : programs) {
                    Assert.assertEquals(program.get("eligibilityStatus"), eligibilityStatus, "Eligibility Status didn't match.Expected " + eligibilityStatus + " Actual : " + program.get("eligibilityStatus"));
                }
            }
        }
    }

    @Then("I verify The response includes deadline date and set to end of the calculated date for {string}")
    public void iVerifyTheResponseIncludesDeadlineDateAndSetToEndOfTheCalculatedDateFor(String applicationType) {
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        String expectedApplicationDeadlineDate = "";
        if (applicationType.equalsIgnoreCase("MEDICAL ASSISTANCE")) {
            expectedApplicationDeadlineDate = getFutureDateFormatYYYYMMdd(45);
        } else if (applicationType.equalsIgnoreCase("LONG TERM CARE")) {
            expectedApplicationDeadlineDate = getFutureDateFormatYYYYMMdd(90);
        }
        assertEquals(appData.get("applicationDeadlineDate"), expectedApplicationDeadlineDate, "Expected Application Deadline date : " + expectedApplicationDeadlineDate + " didn't match with actual deadline date: " + appData.get("applicationDeadlineDate"));
    }

    @Then("I verify there is no eligibility status set for {string}")
    public void iVerifyThereIsNoEligibilityStatusSetFor(String applicationConsumerType) {
        int k;
        List<Map<String, Object>> applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        for (k = 0; k < applicationConsumers.size(); k++) {
            if (applicationConsumerType.equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerRoleType"))) {
                Assert.assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].programs.eligibilityStatus"), "Eligibility Status found for " + applicationConsumerType);
                ;
            }
        }
    }

    @Then("I verify that external application ID is included in API response with value {string}")
    public void i_verify_that_external_application_id_is_present(String expectedExtAppID) {
        System.out.println("Application priority is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationPriority"));
        if (expectedExtAppID.isEmpty())
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.externalApplicationId") == null, "External Application ID is not null");
        else if (expectedExtAppID.equals("random"))
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.externalApplicationId").equals(externalAppID.get()), "External Application ID is not as expected:" + externalAppID);
        else
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.externalApplicationId").equals(expectedExtAppID), "External Application ID is not as expected:" + expectedExtAppID);
    }

    @And("I set external application ID to value {string} in save API")
    public void set_external_application_ID_in_save_API(String expectedExtAppID) {
        if (!expectedExtAppID.isEmpty())
            switch (expectedExtAppID) {
                case "random":
                    synchronized (externalAppID){
                        externalAppID.set(generateRandomSpecialrChars2(15));
                    }
                    break;
                case "duplicate":

                    break;
                default:
                    externalAppID.set(expectedExtAppID);
                    break;
            }
        appRequestPayload.addProperty("externalApplicationId", externalAppID.get());
    }

    @And("I POST ATS save submit application api and store appId and response in list")
    public void iPOSTATSSaveSubmitApplicationApiAndStoreAppIdAndResponseInList() {
        String appId;
        appPayload.set(appRequestPayload);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload);
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        appId = appData != null ? String.valueOf(appData.get("applicationId")) : null;
        memberMatchingAppId.get().add(appId);
        setAppIdList(memberMatchingAppId.get());
        memberMatchingResponse.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        setMemberMatchingResponse(memberMatchingResponse.get());
        appRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/createApplication_1.json");
        System.out.println("Application id is for ATS: " + appId);
        System.out.println("Application id in List: " + memberMatchingAppId.toString());
        APIATSApplicationController.applicationIdAPI.set(appId);
        System.out.println("APIATSApplicationController.applicationId = " + APIATSApplicationController.applicationIdAPI.get());
    }

    public void postSaveApplicationForDuplicate() {
        String appId;
        appPayload.set(appRequestPayload);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload);
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        appId = appData != null ? String.valueOf(appData.get("applicationId")) : null;
        memberMatchingAppId.get().add(appId);
        setAppIdList(memberMatchingAppId.get());
        memberMatchingResponse.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        setMemberMatchingResponse(memberMatchingResponse.get());
        appRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/createApplication_1.json");
        System.out.println("Application id is for ATS: " + appId);
        System.out.println("Application id in List: " + memberMatchingAppId.toString());
    }

    public void addResidentialAddressToPrimaryMem() {
        JsonObject appAddress = getCreateApplicationRequestFromJSONFile("crm/ats/consumerAddress.json");
        appAddress.addProperty("addressStreet1", "9 Metro Ave");
        appAddress.addProperty("addressStreet2", "2nd apt");
        appAddress.addProperty("addressCity", "Herndon");
        appAddress.addProperty("addressState", "Virginia");
        appAddress.addProperty("addressCounty", "Loudoun");
        appAddress.addProperty("addressZip", "20171");
        appAddress.addProperty("addressType", "Residential");
        appRequestPayload.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").add(appAddress);
    }

    @And("I POST {int} {string} Application API with Primary Individual with same programs and Residential address plus following consumer data")
    public void iPOSTApplicationAPIWithPrimaryIndividualWithSameProgramsAndResidentialAddressPlusFollowingConsumerData(int appNum, String appType, List<Map<String, String>> duplicateTypes) {
        waitFor(5);
        switch (appType) {
            case "MEDICAL ASSISTANCE":
                for (int i = 0; i < appNum; i++) {
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp);
                    initiateAppLevelDataForMatching("Medical Assistance");
                    initiatePrimaryIndividual(0, duplicateTypes);
                    addResidentialAddressToPrimaryMem();
                    List<String> programsList = new ArrayList<>(Arrays.asList("CHIP", "Medicaid", "Pregnancy Assistance"));
                    for (String eachProgram : programsList) {
                        JsonObject program = getCreateApplicationRequestFromJSONFile("crm/ats/appProgram.json");
                        program.addProperty("programType", eachProgram);
                        appRequestPayload.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").add(program);
                    }
                    postSaveApplicationForDuplicate();
                }
                break;
            case "LONG TERM CARE":
                for (int i = 0; i < appNum; i++) {
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp);
                    initiateAppLevelDataForMatching("Long Term Care");
                    initiatePrimaryIndividual(0, duplicateTypes);
                    addResidentialAddressToPrimaryMem();
                    List<String> programsList = new ArrayList<>(Arrays.asList("HCBS"));
                    for (String eachProgram : programsList) {
                        JsonObject program = getCreateApplicationRequestFromJSONFile("crm/ats/appProgram.json");
                        program.addProperty("programType", eachProgram);
                        appRequestPayload.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").add(program);
                    }
                    postSaveApplicationForDuplicate();
                }
                break;
            case "LTC SUBMITTED APP STATUS":
                for (int i = 0; i < appNum; i++) {
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp);
                    initiateAppLevelDataForMatching("LTC SUBMITTED");
                    initiatePrimaryIndividual(0, duplicateTypes);
                    addResidentialAddressToPrimaryMem();
                    List<String> programsList = new ArrayList<>(Arrays.asList("HCBS"));
                    for (String eachProgram : programsList) {
                        JsonObject program = getCreateApplicationRequestFromJSONFile("crm/ats/appProgram.json");
                        program.addProperty("programType", eachProgram);
                        appRequestPayload.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").add(program);
                    }
                    postSaveApplicationForDuplicate();
                }
                break;
            case "MA SUBMITTED APP STATUS":
                for (int i = 0; i < appNum; i++) {
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp);
                    initiateAppLevelDataForMatching("MA SUBMITTED");
                    initiatePrimaryIndividual(0, duplicateTypes);
                    addResidentialAddressToPrimaryMem();
                    List<String> programsList = new ArrayList<>(Arrays.asList("CHIP", "Medicaid"));
                    for (String eachProgram : programsList) {
                        JsonObject program = getCreateApplicationRequestFromJSONFile("crm/ats/appProgram.json");
                        program.addProperty("programType", eachProgram);
                        appRequestPayload.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").add(program);
                    }
                    postSaveApplicationForDuplicate();
                }
                break;
            case "MA WITH MEDICAID":
                for (int i = 0; i < appNum; i++) {
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp);
                    initiateAppLevelDataForMatching("MA SUBMITTED");
                    initiatePrimaryIndividual(0, duplicateTypes);
                    addResidentialAddressToPrimaryMem();
                    List<String> programsList = new ArrayList<>(Arrays.asList("Medicaid"));
                    for (String eachProgram : programsList) {
                        JsonObject program = getCreateApplicationRequestFromJSONFile("crm/ats/appProgram.json");
                        program.addProperty("programType", eachProgram);
                        appRequestPayload.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").add(program);
                    }
                    postSaveApplicationForDuplicate();
                }
                break;
            default:
                fail("Provided Application type did not match Medical Assistance or Long Term Care");
        }
        applicationIdAPI.set(memberMatchingAppId.get().get(memberMatchingAppId.get().size() - 1));
    }

    private JsonObject getRequestPayloadFromPath(String filePath) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile(filePath);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @And("I POST ATS applicationdata matching API")
    public ResponseBody iPOSTATSApplicationdataMatchingAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(matchRequest);
        matchApiResponse.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        System.out.println(matchApiResponse.get().prettyPrint());
        return matchApiResponse.get();
    }

    @And("I initiate applicationdata matching POST API with the {string} created applicationId")
    public void iInitiateApplicationdataMatchingPOSTAPIWithTheCreatedApplicationId(String appIdNumInList) {
        switch (appIdNumInList) {
            case "LAST":
                matchRequest.addProperty("applicationId", appIdList.get().get(appIdList.get().size() - 1));
                break;
            case "FIRST":
                matchRequest.addProperty("applicationId", appIdList.get().get(0));
                break;
            case "NOT FOUND":
                matchRequest.addProperty("applicationId", 99999);
                break;
            case "Latest ApplicationId":
                matchRequest.addProperty("applicationId", Integer.parseInt(applicationIdAPI.get()));
                break;
            case "NULL APP ID":
                break;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(matchApplicationMember);
    }

    @Then("I verify the matching response for {string} match response")
    public void iVerifyTheMatchingResponseForMatchResponse(String matchType) {

        matchingApplicationsList.set(matchApiResponse.get().jsonPath().getList("data.matchingApplications"));
        matchingCollectionsList.set(matchApiResponse.get().jsonPath().getList("data.matchingCollections"));
        appConsumers.set(matchApiResponse.get().jsonPath().getList("data.matchingApplications.applicationConsumers"));
        matchIncAppConsumerList.set(matchApiResponse.get().jsonPath().getList("data.matchingApplications.applicationConsumers.matchingIncomingApplicationConsumers"));
        expectedIdList.set(memberMatchingAppId.get());
        actualAppIdList.set(matchApiResponse.get().jsonPath().getList("data.matchingApplications.applicationId"));
        switch (matchType) {
            case "SINGLE":
                inboundAppResponse.set(memberMatchingResponse.get().get(1));
                inboundAppConsumerList.set(inboundAppResponse.get().jsonPath().getList("data.applicationConsumers"));
                matchingAppOneResponse.set(memberMatchingResponse.get().get(0));
                matchingAppData.set(matchingAppOneResponse.get().jsonPath().getMap("data"));
                matchingConsumerList.set(matchingAppOneResponse.get().jsonPath().getList("data.applicationConsumers"));
                matchingAddressList.set(matchingAppOneResponse.get().jsonPath().get("data.applicationConsumers.applicationConsumerAddress[0]"));
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1);
                assertEquals(matchingApplicationsList.get().get(0).get("matchScore"), 100);
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationStatus").toString(), matchingAppOneResponse.get().jsonPath().get("data.applicationStatus"), "Matching Response applicationStatus mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumerAddress.addressType").toString(), matchingAddressList.get().get(0).get("addressType"), "Matching Response addressType mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumerAddress.addressStreet1").toString(), matchingAddressList.get().get(0).get("addressStreet1"), "Matching Response addressStreet1 mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumerAddress.addressStreet2").toString(), matchingAddressList.get().get(0).get("addressStreet2"), "Matching Response addressStreet2 mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumerAddress.addressCity").toString(), matchingAddressList.get().get(0).get("addressCity"), "Matching Response addressCity mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumerAddress.addressState").toString(), matchingAddressList.get().get(0).get("addressState"), "Matching Response addressState mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumerAddress.addressZip").toString(), matchingAddressList.get().get(0).get("addressZip"), "Matching Response addressZip mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[0].applicationConsumers[0].applicationConsumerId"), matchingConsumerList.get().get(0).get("applicationConsumerId"), "Matching Response applicationConsumerId mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumers[0].consumerRoleType").toString(), matchingConsumerList.get().get(0).get("consumerRoleType"), "Matching Response consumerRoleType mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumers[0].consumerFirstName").toString(), matchingConsumerList.get().get(0).get("consumerFirstName"), "Matching Response consumerFirstName mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumers[0].consumerMiddleName").toString(), matchingConsumerList.get().get(0).get("consumerMiddleName"), "Matching Response consumerMiddleName mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumers[0].consumerLastName").toString(), matchingConsumerList.get().get(0).get("consumerLastName"), "Matching Response consumerLastName mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumers[0].consumerSuffix").toString(), matchingConsumerList.get().get(0).get("consumerSuffix"), "Matching Response consumerSuffix mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumers[0].dateOfBirth").toString(), matchingConsumerList.get().get(0).get("dateOfBirth"), "Matching Response dateOfBirth mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumers[0].ssn").toString(), matchingConsumerList.get().get(0).get("ssn"), "Matching Response ssn mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[0].matchScore"), 100, "Matching Response matchScore mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[0].applicationConsumerId"), inboundAppConsumerList.get().get(0).get("applicationConsumerId"), "Matching Response applicationConsumerId mismatched");
                List<String> expectedProgList = new ArrayList<>(matchingAppOneResponse.get().jsonPath().getList("data.applicationConsumers[0].programs.programType"));
                List<String> ActualProgList = new ArrayList<>(matchApiResponse.get().jsonPath().get("data.matchingApplications[0].applicationConsumers[0].programs"));
                Collections.sort(expectedProgList);
                Collections.sort(ActualProgList);
                assertEquals(ActualProgList, expectedProgList, "Matching Response programs mismatched");
                break;
            case "MULTIPLE SSN RULE":
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success", "Matching Response status success mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 3, "Matching Response totalCount mismatched");
                inboundAppResponse.set(memberMatchingResponse.get().get(expectedIdList.get().size() - 1));
                String expectedSSN = inboundAppResponse.get().jsonPath().get("data.applicationConsumers[0].ssn");
                for (int i = 0; i < matchApiResponse.get().jsonPath().getList("data.matchingApplications.applicationConsumers.ssn").size(); i++) {
                    if (matchApiResponse.get().jsonPath().get("data.matchingApplications[" + i + "].applicationConsumers[0].ssn") == null) {
                        assertEquals(expectedSSN, matchApiResponse.get().jsonPath().get("data.matchingApplications[" + i + "].applicationConsumers[1].ssn"), "Matching Response ssn mismatched");
                    } else {
                        assertEquals(expectedSSN, matchApiResponse.get().jsonPath().get("data.matchingApplications[" + i + "].applicationConsumers[0].ssn"), "Matching Response ssn mismatched");
                    }
                }
                for (Integer integer : actualAppIdList.get()) {
                    assertTrue(expectedIdList.get().contains(String.valueOf(integer)), "Matching Response expected ID List mismatched");
                }
                for (Map<String, Object> stringObjectMap : matchingApplicationsList.get()) {
                    assertEquals(stringObjectMap.get("matchScore"), 50, "Matching Response matchScore mismatched");
                }
                break;
            case "MULTIPLE NAME RULE":
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success", "Matching Response ssn mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 3, "Matching Response ssn mismatched");
                inboundAppResponse.set(memberMatchingResponse.get().get(expectedIdList.get().size() - 1));
                String expectedFirstName = inboundAppResponse.get().jsonPath().get("data.applicationConsumers[0].consumerFirstName");
                String expectedLastName = inboundAppResponse.get().jsonPath().get("data.applicationConsumers[0].consumerLastName");
                String expectedDoB = inboundAppResponse.get().jsonPath().get("data.applicationConsumers[0].dateOfBirth");
                for (int i = 0; i < matchingApplicationsList.get().size(); i++) {
                    if (((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("dateOfBirth") == null) {
                        assertEquals(matchingApplicationsList.get().get(i).get("matchScore"), 40, "Matching Response ssn mismatched");
                    } else {
                        assertEquals(matchingApplicationsList.get().get(i).get("matchScore"), 50, "Matching Response ssn mismatched");
                    }
                }
                for (int i = 0; i < appConsumers.get().size(); i++) {
                    if (expectedFirstName.equals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("consumerFirstName"))) {
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("consumerFirstName"), expectedFirstName, "Matching Response consumerFirstName mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("consumerLastName"), expectedLastName, "Matching Response consumerLastName mismatched");
                        if (((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("dateOfBirth") == null) {
                            break;
                        } else {
                            assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("dateOfBirth"), expectedDoB, "Matching Response dateOfBirth mismatched");
                        }
                    } else {
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(1)).get("consumerFirstName"), expectedFirstName, "Matching Response consumerFirstName mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(1)).get("consumerLastName"), expectedLastName, "Matching Response consumerLastName mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(1)).get("dateOfBirth"), expectedDoB, "Matching Response dateOfBirth mismatched");
                    }
                }
                for (int i = 0; i < actualAppIdList.get().size(); i++) {
                    assertTrue(expectedIdList.get().contains(String.valueOf(actualAppIdList.get().get(i))));
                }
                break;
            case "APPLICATION MEMBER SINGLE":
                inboundAppResponse.set(memberMatchingResponse.get().get(expectedIdList.get().size() - 1));
                inboundAppConsumerList.set(inboundAppResponse.get().jsonPath().getList("data.applicationConsumers"));
                matchingAppOneResponse.set(memberMatchingResponse.get().get(0));
                matchingApptwoResponse.set(memberMatchingResponse.get().get(1));
                matchingAppData.set(matchingAppOneResponse.get().jsonPath().getMap("data"));
                matchingConsumerList.set(matchingAppOneResponse.get().jsonPath().getList("data.applicationConsumers"));
                matchingAddressList.set(matchingAppOneResponse.get().jsonPath().get("data.applicationConsumers.applicationConsumerAddress[0]"));
                consumerRoleList.set(inboundAppResponse.get().jsonPath().getList("data.applicationConsumers.consumerRoleType"));
                for (int i = 0; i < consumerRoleList.get().size(); i++) {
                    if ("Application Member".equals(consumerRoleList.get().get(i))) {
                        inbAppMemFirstName.set(inboundAppResponse.get().jsonPath().getString("data.applicationConsumers[" + i + "].consumerFirstName"));
                        inbAppMemLastName.set(inboundAppResponse.get().jsonPath().getString("data.applicationConsumers[" + i + "].consumerLastName"));
                        inbAppMemDob.set(inboundAppResponse.get().jsonPath().getString("data.applicationConsumers[" + i + "].dateOfBirth"));
                        inbAppMemSSN.set(inboundAppResponse.get().jsonPath().getString("data.applicationConsumers[" + i + "].ssn"));
                        inbAppMemConsumerId.set(String.valueOf(inboundAppResponse.get().jsonPath().getString("data.applicationConsumers[" + i + "].applicationConsumerId")));
                    }
                }
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success", "Matching Response status mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 2, "Matching Response totalCount mismatched");
                for (int i = 0; i < actualAppIdList.get().size(); i++) {
                    assertTrue(expectedIdList.get().contains(String.valueOf(actualAppIdList.get().get(i))));
                }
                for (int i = 0; i < appConsumers.get().size(); i++) {
                    if (((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("consumerLastName") == null) {
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(1)).get("consumerFirstName"), inbAppMemFirstName, "Matching Response consumerFirstName mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(1)).get("consumerLastName"), inbAppMemLastName, "Matching Response consumerLastName mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(1)).get("dateOfBirth"), inbAppMemDob, "Matching Response dateOfBirth mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(1)).get("ssn"), inbAppMemSSN, "Matching Response ssn mismatched");
                    } else {
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("consumerFirstName"), inbAppMemFirstName, "Matching Response consumerFirstName mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("consumerLastName"), inbAppMemLastName, "Matching Response consumerLastName mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("dateOfBirth"), inbAppMemDob, "Matching Response dateOfBirth mismatched");
                        assertEquals(((HashMap) ((ArrayList) appConsumers.get().get(i)).get(0)).get("ssn"), inbAppMemSSN, "Matching Response ssn mismatched");
                    }
                }
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[0].matchScore"), 100, "Matching Response matchScore mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[1].applicationConsumers[1].matchingIncomingApplicationConsumers[0].matchScore"), 100, "Matching Response matchScore mismatched");
                assertTrue(matchApiResponse.get().jsonPath().get("data.matchingApplications[1].applicationConsumers[0].matchingIncomingApplicationConsumers[0].matchScore") == null, "Matching Response matchScore mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getString("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[0].applicationConsumerId"), inbAppMemConsumerId, "Matching Response applicationConsumerId mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getString("data.matchingApplications[1].applicationConsumers[1].matchingIncomingApplicationConsumers[0].applicationConsumerId"), inbAppMemConsumerId, "Matching Response applicationConsumerId mismatched");
                break;
            case "NULL FIRST LAST DOB":
                inboundAppResponse.set(memberMatchingResponse.get().get(expectedIdList.get().size() - 1));
                inboundAppConsumerList.set(inboundAppResponse.get().jsonPath().getList("data.applicationConsumers"));
                matchingAppOneResponse.set(memberMatchingResponse.get().get(0));
                inbAppMemConsumerId.set(String.valueOf(inboundAppResponse.get().jsonPath().getString("data.applicationConsumers[0].applicationConsumerId")));
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success", "Matching Response status mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1, "Matching Response totalCount mismatched");
                assertEquals(matchingApplicationsList.get().get(0).get("matchScore"), 50, "Matching Response matchScore mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[0].matchScore"), 50, "Matching Response matchScore mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getString("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[0].applicationConsumerId"), inbAppMemConsumerId, "Matching Response applicationConsumerId mismatched");
                break;
            case "NULL SSN":
                inboundAppResponse.set(memberMatchingResponse.get().get(expectedIdList.get().size() - 1));
                inboundAppConsumerList.set(inboundAppResponse.get().jsonPath().getList("data.applicationConsumers"));
                matchingAppOneResponse.set(memberMatchingResponse.get().get(0));
                inbAppMemConsumerId.set(String.valueOf(inboundAppResponse.get().jsonPath().getString("data.applicationConsumers[0].applicationConsumerId")));
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success", "Matching Response status mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1, "Matching Response totalCount mismatched");
                assertEquals(matchingApplicationsList.get().get(0).get("matchScore"), 50, "Matching Response matchScore mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[0].matchScore"), 50, "Matching Response matchScore mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getString("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[0].applicationConsumerId"), inbAppMemConsumerId, "Matching Response applicationConsumerId mismatched");
                break;
            case "NOT FOUND":
                assertEquals(matchApiResponse.get().jsonPath().getInt("status"), 404, "Matching Response status mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("error"), "Not Found", "Matching Response error mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("message"), "Application not found.", "Matching Response message mismatched");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 404, "Matching Response statusCode mismatched");
                break;
            case "NULL APP ID":
                assertEquals(matchApiResponse.get().jsonPath().getInt("status"), 400, "Matching Response status mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("error"), "Bad Request", "Matching Response error mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("message"), "ApplicationId must be supplied.", "Matching Response message mismatched");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 400, "Matching Response statusCode mismatched");
                break;
            case "MATCHING APP SORTING":
                assertEquals(matchingApplicationsList.get().get(0).get("applicationId"), Integer.valueOf(expectedIdList.get().get(4)), "Matching Response applicationId mismatched");//100
                assertEquals(matchingApplicationsList.get().get(1).get("applicationId"), Integer.valueOf(expectedIdList.get().get(2)), "Matching Response applicationId mismatched");//90
                assertEquals(matchingApplicationsList.get().get(2).get("applicationId"), Integer.valueOf(expectedIdList.get().get(3)), "Matching Response applicationId mismatched");//90
                assertEquals(matchingApplicationsList.get().get(3).get("applicationId"), Integer.valueOf(expectedIdList.get().get(0)), "Matching Response applicationId mismatched");//50
                assertEquals(matchingApplicationsList.get().get(4).get("applicationId"), Integer.valueOf(expectedIdList.get().get(1)), "Matching Response applicationId mismatched");//50
                for (int i = 0; i < matchingApplicationsList.get().size(); i++) {
                    actualMatchScoreList.get().add((Integer) matchingApplicationsList.get().get(i).get("matchScore"));
                }
                assertTrue(isSortedDescending((ArrayList<Integer>) actualMatchScoreList.get()), "Matching Response actualMatchScoreList mismatched");
                break;
            case "MATCHING APP MEMBERS SORTING":
                for (int i = 0; i < matchApiResponse.get().jsonPath().getList("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers").size(); i++) {
                    actualMatchScoreList.get().add(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[" + i + "].matchScore"));
                }
                assertTrue(isSortedDescending((ArrayList<Integer>) actualMatchScoreList.get()), "Matching Response actualMatchScoreList mismatched");
                for (int i = 0; i < matchApiResponse.get().jsonPath().getList("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers").size(); i++) {
                    actualAppConsumerId.get().add(matchApiResponse.get().jsonPath().getInt("data.matchingApplications[0].applicationConsumers[0].matchingIncomingApplicationConsumers[" + i + "].applicationConsumerId"));
                }
                assertTrue(actualAppConsumerId.get().get(1) < actualAppConsumerId.get().get(2), "Matching Response application consumer Id not in descending order");
                assertTrue(actualAppConsumerId.get().get(3) < actualAppConsumerId.get().get(4), "Matching Response application consumer Id not in descending order");
                break;
            case "CASE CONSUMERS FLD RULE":
                collectInboundAndMatchingFromResponse();
                matchingAddressList.set(matchingAppOneResponse.get().jsonPath().get("data.applicationConsumers.applicationConsumerAddress[0]"));
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1);
                assertEquals(matchingCollectionsList.get().get(0).get("matchScore"), 50, "Mismatch in expected Matching rule score");
                assertEquals(String.valueOf(matchingCollectionsList.get().get(0).get("id")), searchCaseIdList.get().get(0));
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumerAddress.addressType").toString(), "Physical", "Matching Response addressType mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumerAddress.addressStreet1").toString(), matchingAddressList.get().get(0).get("addressStreet1"), "Matching Response addressStreet1 mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumerAddress.addressStreet2").toString(), matchingAddressList.get().get(0).get("addressStreet2"), "Matching Response addressStreet2 mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumerAddress.addressCity").toString(), matchingAddressList.get().get(0).get("addressCity"), "Matching Response addressCity mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumerAddress.addressState").toString(), matchingAddressList.get().get(0).get("addressState"), "Matching Response addressState mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumerAddress.addressZip").toString(), matchingAddressList.get().get(0).get("addressZip"), "Matching Response addressZip mismatched");
                assertEquals(String.valueOf(matchApiResponse.get().jsonPath().getInt("data.matchingCollections[0].consumers[0].consumerId")), searchConsumerIdList.get().get(0), "Matching Response ConsumerId mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].consumerRoleType").toString(), matchingConsumerList.get().get(0).get("consumerRoleType"), "Matching Response consumerRoleType mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].consumerFirstName").toString(), matchingConsumerList.get().get(0).get("consumerFirstName"), "Matching Response consumerFirstName mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].consumerLastName").toString(), matchingConsumerList.get().get(0).get("consumerLastName"), "Matching Response consumerLastName mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].caseConsumerStatus").toString(), "Active", "Matching Response caseConsumerStatus mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].consumerStatus").toString(), "Active", "Matching Response consumerStatus mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].dateOfBirth").toString(), matchingConsumerList.get().get(0).get("dateOfBirth"), "Matching Response dateOfBirth mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingCollections[0].consumers[0].matchingIncomingConsumers[0].matchScore"), 50, "Matching Response matchScore mismatched");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.matchingCollections[0].consumers[0].matchingIncomingConsumers[0].consumerId"), inboundAppConsumerList.get().get(0).get("applicationConsumerId"), "Matching Response applicationConsumerId mismatched");
                break;
            case "CASE CONSUMERS SSN RULE":
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1);
                assertEquals(matchingCollectionsList.get().get(0).get("matchScore"), 50, "Mismatch in expected Matching rule score");
                assertEquals(String.valueOf(matchingCollectionsList.get().get(0).get("id")), searchCaseIdList.get().get(0));
                assertEquals(String.valueOf(matchApiResponse.get().jsonPath().getInt("data.matchingCollections[0].consumers[0].consumerId")), searchConsumerIdList.get().get(0), "Matching Response ConsumerId mismatched");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.matchingCollections[0].consumers[0].ssn").toString(), matchingConsumerList.get().get(0).get("ssn"), "Matching Response ssn mismatched");
                break;
            case "CASE CONSUMERS EXTERNAL ID RULE":
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1);
                assertEquals(matchingCollectionsList.get().get(0).get("matchScore"), 60, "Mismatch in expected Matching rule score");
                assertEquals(String.valueOf(matchingCollectionsList.get().get(0).get("id")), searchCaseIdList.get().get(0));
                assertEquals(String.valueOf(matchApiResponse.get().jsonPath().getInt("data.matchingCollections[0].consumers[0].consumerId")), searchConsumerIdList.get().get(0), "Matching Response ConsumerId mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].ssn").toString(), matchingConsumerList.get().get(0).get("ssn"), "Matching Response consumerFirstName mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].consumerIdentificationNumber[0].externalConsumerId").toString(), consumerExternalId, "Matching Response externalConsumerId mismatched");
                assertEquals(matchApiResponse.get().jsonPath().get("data.matchingCollections[0].consumers[0].consumerIdentificationNumber[0].identificationNumberType").toString(), "CHIP", "Matching Response identificationNumberType mismatched");
                break;
            case "INBOUND APP MATCHING MULTIPLE CASE":
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 3);
                assertEquals(matchingCollectionsList.get().get(0).get("matchScore"), 60, "Mismatch in expected Matching rule score consumer matching External id with 60 pts as first in list");
                assertEquals(matchingCollectionsList.get().get(1).get("matchScore"), 50, "Mismatch in expected Matching rule score consumer matching; SSN matching expected 50");
                assertEquals(matchingCollectionsList.get().get(2).get("matchScore"), 40, "Mismatch in expected Matching rule score consumer matching; First and Last name expected 40");
                break;
            case "SAME SCORE MATCHING SORTING MULTIPLE CASE":
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 3);
                List<Integer> actualConmatchList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.matchingCollections[0].consumers[0].matchingIncomingConsumers.matchScore");
                for (Integer each : actualConmatchList) {
                    assertEquals(each, Integer.valueOf("40"));
                }
                Integer consumerIdOne = (Integer) matchingCollectionsList.get().get(0).get("id");
                Integer consumerIdTwo = (Integer) matchingCollectionsList.get().get(1).get("id");
                Integer consumerIdThree = (Integer) matchingCollectionsList.get().get(2).get("id");
                assertTrue(consumerIdOne < consumerIdTwo && consumerIdTwo < consumerIdThree, "Expected matching id in ascending order but found non-ascending");
                break;
            case "MULTIPLE APP MEM MATCHING TO A SINGLE CONSUMER":
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1);
                List<Integer> expectedConsumerIdList = new ArrayList<>();
                expectedConsumerIdList.add(sortConsumerIdListByMatchingPoints(" A ", idFullNameList.get()));
                expectedConsumerIdList.add(sortConsumerIdListByMatchingPoints(" B ", idFullNameList.get()));
                expectedConsumerIdList.add(sortConsumerIdListByMatchingPoints(" C ", idFullNameList.get()));
                expectedConsumerIdList.add(sortConsumerIdListByMatchingPoints(" D ", idFullNameList.get()));
                List<String> actualConsumerIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.matchingCollections[0].consumers[0].matchingIncomingConsumers.consumerId");
                assertEquals(expectedConsumerIdList, actualConsumerIdList, "Mismatch in expected consumer Id order in matchingIncomingConsumers array");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.matchingCollections[0].consumers[0].matchingIncomingConsumers.matchScore[0]"), 100, "Mismatch in expected match score order");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.matchingCollections[0].consumers[0].matchingIncomingConsumers.matchScore[1]"), 60, "Mismatch in expected match score order");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.matchingCollections[0].consumers[0].matchingIncomingConsumers.matchScore[2]"), 50, "Mismatch in expected match score order");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.matchingCollections[0].consumers[0].matchingIncomingConsumers.matchScore[3]"), 40, "Mismatch in expected match score order");
                break;
            case "MULTIPLE APP MEM SAME SCORE MATCHING TO A SINGLE CONSUMER":
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1);
                List<Integer> actualConMatchList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.matchingCollections[0].consumers[0].matchingIncomingConsumers.matchScore");
                for (Integer each : actualConMatchList) {
                    assertEquals(each, Integer.valueOf("50"));
                }
                List<Integer> actualConIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.matchingCollections[0].consumers[0].matchingIncomingConsumers.consumerId");
                assertTrue(isSortedAscending((ArrayList<Integer>) actualConIdList));
                break;
            case "RETRIEVE NON ACTIVE CONSUMER":
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 0, "Expected count of 0 for return of non active members");
                assertEquals(matchApiResponse.get().jsonPath().getString("data.matchingType"), "caseConsumers", "Mismatch in matchingType");
                break;
            case "AUTH REP FILTER POSITIVE":
                // To verify no match retrieved for AUTH REP
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 0, "Expected count of 0 for return of auth rep matching members");
                assertEquals(matchApiResponse.get().jsonPath().getString("data.matchingType"), "caseConsumers", "Mismatch in matchingType");
                break;
            case "AUTH REP FILTER NEGATIVE":
                // To verify match is retrieved for the same name used by AUTH REP but as a different role
                collectInboundAndMatchingFromResponse();
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                assertEquals(matchApiResponse.get().jsonPath().get("status"), "success");
                assertEquals(matchApiResponse.get().jsonPath().getInt("data.totalCount"), 1, "Expected count of 1 for return of auth rep matching members");
                assertEquals(matchApiResponse.get().jsonPath().getString("data.matchingType"), "caseConsumers", "Mismatch in matchingType");
                assertEquals(matchingCollectionsList.get().get(0).get("matchScore"), 50, "Mismatch in expected Matching rule score");
                break;
            default:
                fail("Mismatch in expected Case");
        }
    }

    private Integer sortConsumerIdListByMatchingPoints(String middleInitial, Map<String, String> data) {
        for (Map.Entry<String, String> each : data.entrySet()) {
            if (each.getValue().contains(middleInitial)) {
                return Integer.valueOf(each.getKey());
            }
        }
        return Integer.valueOf("0");
    }

    public void collectInboundAndMatchingFromResponse() {
        inboundAppResponse.set(memberMatchingResponse.get().get(1));
        inboundAppConsumerList.set(inboundAppResponse.get().jsonPath().getList("data.applicationConsumers"));
        matchingAppOneResponse.set(memberMatchingResponse.get().get(0));
        matchingAppData.set(matchingAppOneResponse.get().jsonPath().getMap("data"));
        matchingConsumerList.set(matchingAppOneResponse.get().jsonPath().getList("data.applicationConsumers"));
    }

    boolean isSortedDescending(ArrayList<Integer> list) {
        boolean sorted = true;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) >= (list.get(i))) {
                sorted = true;
            } else {
                return false;
            }
        }
        return sorted;
    }

    boolean isSortedAscending(ArrayList<Integer> list) {
        boolean sorted = true;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) <= (list.get(i))) {
                sorted = true;
            } else {
                return false;
            }
        }
        return sorted;
    }

    public void setAppIdList(List<String> appIdList) {

        this.appIdList.set(appIdList);
    }

    public void setMemberMatchingResponse(ArrayList<ResponseBody> memberMatchingResponse) {
        this.memberMatchingResponse.set(memberMatchingResponse);
    }

    public ArrayList<ResponseBody> getMemberMatchingResponse() {
        return memberMatchingResponse.get();
    }

    @Then("I store program details for updating eligibility status")
    public void i_store_program_details_for_updating_eligibility_status() {
        //Multiple Scenario Valid only 4 2 members with 2 programs each
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationConsumers[1].programs[1].eligibilityId") != null) {
            applicationConsumerIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.applicationConsumers[0].applicationConsumerId"));
            applicationConsumerIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.applicationConsumers[0].applicationConsumerId"));
            applicationConsumerIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].applicationConsumerId"));
            applicationConsumerIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].applicationConsumerId"));

            applicationProgramIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].programId"));
            applicationProgramIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[1].programId"));
            applicationProgramIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].programs[0].programId"));
            applicationProgramIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].programs[1].programId"));

            eligibilityIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].eligibilityId"));
            eligibilityIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[1].eligibilityId"));
            eligibilityIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].programs[0].eligibilityId"));
            eligibilityIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[1].programs[1].eligibilityId"));
        } else {
            applicationConsumerId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].applicationConsumerId"));
            System.out.println("Application consumer id from View: " + applicationConsumerId);

            applicationProgramId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].programId"));
            System.out.println("Program id from View: " + applicationProgramId);

            eligibilityId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].eligibilityId"));
            System.out.println("Eligibility id from View: " + eligibilityId);
        }
    }

    @When("I initiated ats eligibility save application api")
    public void i_initiated_ats_eligibility_save_application_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(viewApp + applicationIdAPI.get() + eligibility);
        System.out.println("Endpoint for eligibility update: " + baseApplicationDataUrl + viewApp + applicationIdAPI.get() + eligibility);
    }

    @Then("I initiate eligibility status API for ats with following values for {string} status")
    public void i_initiate_eligibility_status_API_for_ats_with_following_values_for_status(String status, List<Map<String, String>> data) {
        eligibilityDetailsList.set(data);
        if (!eligibilityIdList.get().isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/atsEligibiltyMultiple.json");
            for (int i = 0; i < data.size(); i++) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty("applicationConsumerId", applicationConsumerIdList.get().get(i));
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty("applicationProgramId", applicationProgramIdList.get().get(i));
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty("eligibilityId", eligibilityIdList.get().get(i));
            }
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/atsEligibility.json");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("applicationConsumerId", applicationConsumerId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("applicationProgramId", applicationProgramId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("eligibilityId", eligibilityId.get());
            subProgramId.set(data.get(0).get("subProgramId"));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("subProgramId", subProgramId.get());
        }
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> keysToOverride = data.get(i);
            for (String key : keysToOverride.keySet()) {
                if ("Today".equals(keysToOverride.get(key))) {
                    eligibilityDeterminationDate.set(getCurrentDateInYearFormat());
                    System.out.println("Determination date: " + eligibilityDeterminationDate);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty(key, eligibilityDeterminationDate.get());
                } else if ("Future".equals(keysToOverride.get(key))) {
                    synchronized (eligibilityStartDate){
                        eligibilityStartDate.set( getFutureDateFormatYYYYMMdd(Integer.parseInt(RandomStringUtils.randomNumeric(1))));
                    }

                    System.out.println("Start date: " + eligibilityStartDate);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty(key, eligibilityStartDate.get());
                } else if ("Future End".equals(keysToOverride.get(key))) {
                    synchronized (eligibilityEndDate){
                        eligibilityEndDate.set( getFutureDateFormatYYYYMMdd(Integer.parseInt(RandomStringUtils.randomNumeric(2))));
                    }
                    System.out.println("End date: " + eligibilityEndDate);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty(key, eligibilityEndDate.get());
                } else if ("eligibilityStatus".equalsIgnoreCase(key)) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty(key, keysToOverride.get(key));
                    eligibilityStatusList.get().add(keysToOverride.get(key));
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty(key, keysToOverride.get(key));
                }
            }
        }

        eligibilityRequest.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray());
        System.out.println("Eligibility request payload = " + eligibilityRequest);
    }

    @Then("I provide denial reason for eligibility")
    public void i_provide_denial_reason_for_eligibility(List<String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/atsEligibility.json");
        denialReasons.set(new JsonArray());
        for (String d : data) {
            denialReasons.get().add(d);
        }
        if (!eligibilityIdList.get().isEmpty()) {
            for (int i = 0; i < eligibilityStatusList.get().size(); i++) {
                if (eligibilityStatusList.get().get(i).equalsIgnoreCase("NOT ELIGIBLE")) {
                    System.out.println("......");
                    eligibilityRequest.get().getAsJsonArray().get(i).getAsJsonObject().add("denialReasons", denialReasons.get());
                }
            }
        } else {
            eligibilityRequest.get().getAsJsonArray().get(0).getAsJsonObject().add("denialReasons", denialReasons.get());
        }
    }

    @Then("I POST ATS Update Eligibility status application api with {string} status")
    public ResponseBody i_POST_ATS_Update_Eligibility_status_application_api_with_status(String status) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(eligibilityRequest.get().toString());
        System.out.println("Request payload for eligibility: " + eligibilityRequest);
        eligibilityResponse.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        System.out.println(eligibilityResponse.get().prettyPrint());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertTrue(eligibilityResponse.get().jsonPath().get("status").equals(status));
        return eligibilityResponse.get();
    }

    @Then("I verify that eligibility status is updated as expected for {string}")
    public void i_verify_that_eligibility_status_is_updated_as_expected_for(String status) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt verified for ats eligibility update");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString().equals("success"), "Status isnt verified for eligibility update");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumerId").toString().equals(String.valueOf(applicationConsumerId.get())), "applicationConsumerId isnt verified for eligibility update");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationProgramId").toString().equals(String.valueOf(applicationProgramId.get())), "applicationProgramId isnt verified for eligibility update");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].eligibilityId").toString().equals(String.valueOf(eligibilityId.get())), "eligibilityId isnt verified for eligibility update");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].eligibilityStatus").toString().equals(status), "Eligibility status isnt verified for eligibility update");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].createdOn").toString()), "createdOn isnt verified for eligibility update");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].updatedOn").toString()), "updatedOn isnt verified for eligibility update");

        switch (status) {
            case "Eligible":
                assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].denialReasons"), "denial reasons are available for eligible status");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].startDate").toString().equals(eligibilityStartDate.get()), "start date isnt verified for eligible status eligibility update");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].endDate").toString().equals(eligibilityEndDate.get()), "end date isnt verified for eligible status eligibility update");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].determinationDate").toString().equals(eligibilityDeterminationDate.get()), "determination date isnt verified for eligible status eligibility update");
                if(apiProgramType.get().equalsIgnoreCase("Pregnancy Assistance")){
                    assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].subProgramId"), "Sub Program isnt verified for eligible status eligibility update");
                } else
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].subProgramId").toString().contains(subProgramId.get()), "Sub Program isnt verified for eligible status eligibility update");
                break;
            case "Not Eligible":
                ArrayList<String> convertedRequest = new ArrayList<>();
                convertedRequest = (convertJsonArraytoArrayList(denialReasons.get()));
                Collections.sort(convertedRequest);
                System.out.println("Denial reasons from scenario: " + convertedRequest);

                JsonArray denialReasonsfromResponse = new JsonArray();
                for (int i = 0; i < denialReasons.get().size(); i++) {
                    denialReasonsfromResponse.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data[0].denialReasons").get(i).toString());
                }
                ArrayList<String> converted = new ArrayList<>();
                converted = (convertJsonArraytoArrayList(denialReasonsfromResponse));
                Collections.sort(converted);
                System.out.println("Denial reasons from response: " + converted);

                assertTrue(converted.equals(convertedRequest), "Scenario denial reasons arent same with response denial reasons");
                break;
            default:
                assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].determinationDate"), "determination date is available for pending or wait list status");
                break;
        }
    }

    public static ArrayList<String> convertJsonArraytoArrayList(JsonArray conv) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0, l = conv.size(); i < l; i++) {
            list.add(conv.get(i).toString());
        }
        return list;
    }

    @Then("I verify expected error messages for {string} status")
    public void i_verify_expected_error_messages_for_status(String status) {

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString().equals("invalid"), "Status isnt correct for error messages");
        List<Map<String, Object>> errorsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message");
        if (status.equalsIgnoreCase("Eligible")) {
            assertTrue(errorsList.size() == 3);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(0).toString().equals("Eligibility Determination Date is required when saving the Eligibility"), "Eligible error message is not correct for determination date");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(1).toString().equals("Eligibility Start Date is required when saving the Eligibility"), "Eligible error message is not correct for start date");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(2).toString().equals("Eligibility Denial Reason is not required when saving the Eligibility"), "Eligible error message is not correct for end date");
        } else if (status.equalsIgnoreCase("Not Eligible")) {
            assertTrue(errorsList.size() == 2);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(0).toString().equals("Eligibility Determination Date is required when saving the Eligibility"), "Not Eligible error message is not correct for determination date");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(1).toString().equals("Eligibility Denial Reason is required when saving the Eligibility"), "Not Eligible error message is not correct for denial reasons");
        } else if (status.equalsIgnoreCase("Unexpected")) {
            assertTrue(errorsList.size() == 1);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(0).toString().equals(status + " is Invalid value for Eligibility Status. Possible values are Eligible, Not Eligible, Pending, Wait List"), "Error message isnt correct for unexpected status");
        } else if (status.equalsIgnoreCase("Unexpected Reason")) {
            assertTrue(errorsList.size() == 1);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(0).toString().equals(status + " is Invalid value for Eligibility Denial Reason. Possible values are Already Eligible, Immigration Status, Medicaid Eligible, Non-Compliance, Other Insurance, Out of State Resident, Over Age, Over Income"), "Error message isnt correct for unexpected denial reasons");
        } else if (status.equalsIgnoreCase("Pregnancy Assistance")) {
            assertTrue(errorsList.size() == 1);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(0).toString().equals("SubPrograms are not configured for Program : " + status));
        } else if (status.equalsIgnoreCase("Missing SubProgram")) {
            assertTrue(errorsList.size() == 1);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("errors.message").get(0).toString().equals("subProgramId is required when saving the Eligibility"));
        }

    }
    @And("I set Application Ids created in a list to be used for in ATS UI functionality")
    public void iSetApplicationIdsCreatedInAListToBeUsedForInATSUIFunctionality() {
        if (memberMatchingAppIdForUI.get().size() <= 1) {
            memberMatchingAppIdForUI.get().clear();
        }
        setMemberMatchingAppIdForUI(memberMatchingAppId.get());
    }

    public List<String> getMemberMatchingAppIdForUI() {
        return memberMatchingAppIdForUI.get();
    }

    @And("I will verify the response details for get linked applications-tasks  response")
    public void iWillVerifyTheResponseDetailsForGetLinkedApplicationsTasksResponse(Map<String, String> datatable) {
        waitFor(3);
        boolean memberMatchingResearchBoolean = true;
        boolean inboundCorrespondenceBoolean = true;
        boolean memberMatchingTaskBoolean = true;
        boolean reviewIncompleteTask = true;
        boolean caseBoolean = true;
        boolean outboundCorrespondenceBoolean = true;
        boolean outboundCorrespondenceMIboolean = true;

        for (String key : datatable.keySet()) {
            if (datatable.get(key).contains("Member Matching Research")) {
                memberMatchingResearchBoolean = false;
            } else if (datatable.get(key).contains("Inbound Correspondence")) {
                inboundCorrespondenceBoolean = false;
            } else if (datatable.get(key).equalsIgnoreCase("Member Matching")) {
                memberMatchingTaskBoolean = false;
            } else if (datatable.get(key).contains("Review Incomplete")) {
                reviewIncompleteTask = false;
            } else if (datatable.get(key).contains("Case")) {
                caseBoolean = false;
            } else if (datatable.get(key).equalsIgnoreCase("Outbound Correspondence")) {
                outboundCorrespondenceBoolean = false;
            } else if (datatable.get(key).equalsIgnoreCase("Outbound Correspondence MI")) {
                outboundCorrespondenceMIboolean = false;
            }

            List<Map<String, String>> linksResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("links");
            for (int i = 0; i < linksResponse.size(); i++) {
                if (!linksResponse.get(i).get("name").equalsIgnoreCase("Case")) {
                    Assert.assertEquals(linksResponse.get(i).get("statusDate"), getCurrentDateInYearFormat(), "Status Date isn't verified for Retrieve Linked Applications API");
                }
                if (linksResponse.get(i).get("name").contains("Inbound")) {
                    inboundCorrespondenceBoolean = true;
                    Assert.assertEquals(linksResponse.get(i).get("name"), datatable.get("nameIC"), "Name isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("type"), datatable.get("typeIC"), "Type isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("status"), datatable.get("statusIC"), "Status isn't verified for Retrieve Linked Applications API");
                }
                else if (linksResponse.get(i).get("name").contains("Case")) {
                    caseBoolean = true;
                    Assert.assertEquals(linksResponse.get(i).get("name"), datatable.get("nameCase"), "Name isn't verified for Retrieve Linked Applications API");
                    if (APIContactRecordController.caseID.get() != 0) {
                        Assert.assertEquals(String.valueOf(linksResponse.get(i).get("id")), String.valueOf(APIContactRecordController.caseID.get()), "Case ID isn't verified for Retrieve Linked Applications API");
                    } else {
                        APIContactRecordController.caseID.set(Integer.parseInt(String.valueOf(linksResponse.get(i).get("id"))));
                    }
                }
                else if (linksResponse.get(i).get("type").contains("Review Incomplete")) {
                    reviewIncompleteTask = true;
                    Assert.assertEquals(linksResponse.get(i).get("name"), datatable.get("nameRI"), "Name isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("type"), datatable.get("typeRI"), "Type isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("status"), datatable.get("statusRI"), "Status isn't verified for Retrieve Linked Applications API");
                }
                else if (linksResponse.get(i).get("type").equalsIgnoreCase("Member Matching")) {
                    memberMatchingTaskBoolean = true;
                    Assert.assertEquals(linksResponse.get(i).get("name"), datatable.get("nameMM"), "Name isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("type"), datatable.get("typeMM"), "Type isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("status"), datatable.get("statusMM"), "Status isn't verified for Retrieve Linked Applications API");
                }
                else if (linksResponse.get(i).get("type").contains("Required")) {
                    outboundCorrespondenceBoolean = true;
                    Assert.assertEquals(linksResponse.get(i).get("name"), datatable.get("nameOC"), "Name isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("type"), datatable.get("typeOC"), "Type isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("status"), datatable.get("statusOC"), "Status isn't verified for Retrieve Linked Applications API");
                }
                else if (linksResponse.get(i).get("type").contains("BL MI Missing Information")) {
                    outboundCorrespondenceMIboolean = true;
                    Assert.assertTrue(datatable.get("nameMIOC").contains(linksResponse.get(i).get("name")), "Expected: "+ datatable.get("nameMIOC")+" But found: "+linksResponse.get(i).get("name")+"Name isnt verified for MI Outbound Correspondence");
                    Assert.assertTrue(linksResponse.get(i).get("type").contains(datatable.get("typeMIOC")), "Type isn't verified for MI Outbound Correspondence");
                    Assert.assertEquals(linksResponse.get(i).get("status"), datatable.get("statusMIOC"), "Status isn't verified MI Outbound Correspondence");
                }
                else if (linksResponse.get(i).get("type").contains("Member Matching Research")) {
                    memberMatchingResearchBoolean = true;
                    Assert.assertEquals(linksResponse.get(i).get("name"), datatable.get("nameResearch"), "Name isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("type"), datatable.get("typeResearch"), "Type isn't verified for Retrieve Linked Applications API");
                    Assert.assertEquals(linksResponse.get(i).get("status"), datatable.get("statusResearch"), "Status isn't verified for Retrieve Linked Applications API");
                }
                Assert.assertTrue(String.valueOf(linksResponse.get(i).get("id")).chars().allMatch(Character::isDigit), "ID isn't verified for Retrieve Linked Applications API");
                System.out.println(i + 1 + ".Retrieved Linked Entity for Application Verified ");
                System.out.println("Name is: "+linksResponse.get(i).get("name"));
                System.out.println("Type is: "+linksResponse.get(i).get("type"));
                System.out.println("Status is: "+linksResponse.get(i).get("status"));
            }
            Assert.assertTrue(inboundCorrespondenceBoolean, "Inbound Correspondence Document is not found in linked Entities");
            Assert.assertTrue(memberMatchingResearchBoolean, "Member Matching Research Task is not found in linked Entities");
            Assert.assertTrue(memberMatchingTaskBoolean, "Member Matching Task is not found in linked Entities");
            Assert.assertTrue(reviewIncompleteTask, "Review Incomplete Task is not found in linked Entities");
            Assert.assertTrue(caseBoolean, "Case is not found in linked Entities");
            Assert.assertTrue(outboundCorrespondenceBoolean, "Outbound correspondence is not found in linked Entities");
            Assert.assertTrue(outboundCorrespondenceMIboolean, "Outbound correspondence MI isnt found");
        }
    }

    // Use this method to verify multiple same entities linked to the application
    @And("I will verify there are {int} linked {string} for link application response with following values")
    public void iWillVerifyThereAreLinkedForLinkApplicationResponseWithFollowingValues(int entityCount, String entityName, Map<String, String> datatable) {
        int actualEntityCount = 0;
        List<Map<String, String>> linksResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("links");
        for (int i = 0; i < linksResponse.size(); i++) {
            if (linksResponse.get(i).get("name").equalsIgnoreCase(entityName)) {
                Assert.assertEquals(linksResponse.get(i).get("name"), datatable.get("name"), "Name isn't verified for Retrieve Linked Applications API");
                Assert.assertEquals(linksResponse.get(i).get("type"), datatable.get("type"), "Type isn't verified for Retrieve Linked Applications API");
                Assert.assertEquals(linksResponse.get(i).get("status"), datatable.get("status"), "Status isn't verified for Retrieve Linked Applications API");
                actualEntityCount++;
            }
        }
        Assert.assertTrue(entityCount == actualEntityCount, "Expected " + entityName + " didn't match for link response.Expected " + entityCount + ", but found " + actualEntityCount);
    }

    public void setMemberMatchingAppIdForUI(List<String> memberMatchingAppIdForUI) {
        APIATSApplicationController.memberMatchingAppIdForUI.set(memberMatchingAppIdForUI);
    }

    @Then("I verify entities are sorted on reverse chronological order based on link create date {string}")
    public void iVerifyEntitiesAreSortedOnReverseChronologicalOrderBasedOnLinkCreateDate(String order) {
        List<Map<String, Object>> linksResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("links");
        ArrayList<Integer> linkedIDList = new ArrayList();
        for (int i = 0; i < linksResponse.size(); i++) {
            linkedIDList.add((Integer) linksResponse.get(i).get("id"));
        }
        for (int i = 0; i < linkedIDList.size() - 1; i++) {
            if (order.equals("reverse")) {
                Assert.assertTrue(linkedIDList.get(i) < linkedIDList.get(i + 1), "Linked Entities are not Sorted");
            } else
                Assert.assertTrue(linkedIDList.get(i) > linkedIDList.get(i + 1), "Linked Entities are not Sorted");
        }
    }

    @And("I initiate and run Get Application Links Call")
    public JsonPath applicationLinks() {
        waitFor(15);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiATSApplicationData"));
        if (!inbCreatedAppId.get().equals("")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/" + inbCreatedAppId.get() + "/links?size=5&page=0&sort=EXTERNAL_LINK_ID,desc");
        } else {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/" + applicationIdAPI.get() + "/links?size=5&page=0&sort=EXTERNAL_LINK_ID,desc");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Linked Applications/Tasks from GET Application Links call API Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    @And("I verify the response outcome has following Eligibility Outcome for Each Member and Each Program")
    public void iVerifyTheResponseOutcomeHasFollowingEligibilityOutcomeForEachMemberAndEachProgram() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt verified for ats eligibility update");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString().equals("success"), "Status isnt verified for eligibility update");
        Collections.sort(applicationProgramIdList.get());
        Collections.sort(eligibilityIdList.get());
        ArrayList<Integer> eligibilityIdListFromResponse = new ArrayList<>();
        for (int i = 0; i < eligibilityDetailsList.get().size(); i++) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].applicationConsumerId").toString().equals(String.valueOf(applicationConsumerIdList.get().get(i))), "applicationConsumerId isnt verified for eligibility update");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].applicationProgramId").toString().equals(String.valueOf(applicationProgramIdList.get().get(i))), "applicationProgramId isnt verified for eligibility update");
            eligibilityIdListFromResponse.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].eligibilityId")); // eligibilityId is not in order on Response
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].eligibilityStatus").toString().equals(eligibilityDetailsList.get().get(i).get("eligibilityStatus")), "Eligibility status isnt verified for eligibility update");
            assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].createdOn").toString()), "createdOn isnt verified for eligibility update");
            assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].updatedOn").toString()), "updatedOn isnt verified for eligibility update");
            if (eligibilityStatusList.get().get(i).equalsIgnoreCase("NOT ELIGIBLE")) {
                ArrayList<String> convertedRequest = (convertJsonArraytoArrayList(denialReasons.get()));
                Collections.sort(convertedRequest);
                System.out.println("Denial reasons from scenario: " + convertedRequest);

                JsonArray denialReasonsfromResponse = new JsonArray();
                for (int k = 0; k < denialReasons.get().size(); k++) {
                    denialReasonsfromResponse.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data[" + i + "].denialReasons").get(k).toString());
                }
                ArrayList<String> converted = (convertJsonArraytoArrayList(denialReasonsfromResponse));
                Collections.sort(converted);
                System.out.println("Denial reasons from response: " + converted);

                assertTrue(converted.equals(convertedRequest), "Scenario denial reasons arent same with response denial reasons");
            } else {
                assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].denialReasons"), "denial reasons are available for eligible status");
            }
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].startDate").toString().equals(eligibilityDetailsList.get().get(i).get("startDate")), "start date isnt verified for eligibility update");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].endDate").toString().equals(eligibilityDetailsList.get().get(i).get("endDate")), "end date isnt verified for eligibility update");
            if (eligibilityDetailsList.get().get(i).get("determinationDate").equalsIgnoreCase("TODAY")) {
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].determinationDate").toString().equals(getCurrentDateInYearFormat()), "determination date isnt verified for eligibility update");
            } else {
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].determinationDate").toString().equals(eligibilityDetailsList.get().get(Integer.parseInt("determinationDate"))), "determination date isnt verified for eligibility update");
            }
        }
        Collections.sort(eligibilityIdListFromResponse);
        Assert.assertTrue(eligibilityIdListFromResponse.equals(eligibilityIdList.get()), "Eligibility ID isn't verified for eligibility update");
    }

    @Then("I verify View Application Response has Eligibility details for Each Applying Members")
    public void iVerifyViewApplicationResponseHasEligibilityDetailsForEachApplyingMembers() {
        List<Map<String, Object>> programResponseList = new ArrayList<>();
        ArrayList<Integer> eligibilityIdListFromResponse = new ArrayList<>();
        ArrayList<Integer> programIdListFromResponse = new ArrayList<>();

        programResponseList.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.applicationConsumers[0].programs[0]"));
        programResponseList.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.applicationConsumers[0].programs[1]"));
        programResponseList.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.applicationConsumers[1].programs[0]"));
        programResponseList.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.applicationConsumers[1].programs[1]"));

        for (int i = 0; i < eligibilityDetailsList.get().size(); i++) {
            Assert.assertEquals(programResponseList.get(i).get("eligibilityStatus"), eligibilityDetailsList.get().get(i).get("eligibilityStatus"), "Eligibility Status isnt verified for eligibility update for view API");
            assertTrue(EventsUtilities.isValidDate(programResponseList.get(i).get("createdOn").toString()), "createdOn isnt verified for eligibility update for view API");
            assertTrue(EventsUtilities.isValidDate(programResponseList.get(i).get("updatedOn").toString()), "createdOn isnt verified for eligibility update for view API");
            Assert.assertEquals(programResponseList.get(i).get("startDate"), eligibilityDetailsList.get().get(i).get("startDate"), "startDate isnt verified for eligibility update for view API");
            Assert.assertEquals(programResponseList.get(i).get("endDate"), eligibilityDetailsList.get().get(i).get("endDate"), "endDate isnt verified for eligibility update for view API");
            if (eligibilityDetailsList.get().get(i).get("determinationDate").equalsIgnoreCase("TODAY")) {
                Assert.assertTrue(programResponseList.get(i).get("determinationDate").toString().equals(getCurrentDateInYearFormat()), "determinationDate isnt verified for eligibility update for view API");
            } else {
                Assert.assertEquals(programResponseList.get(i).get("determinationDate"), eligibilityDetailsList.get().get(i).get("determinationDate"), "determinationDate isnt verified for eligibility update for view API");
            }
            if (eligibilityStatusList.get().get(i).equalsIgnoreCase("NOT ELIGIBLE")) {
                ArrayList<String> convertedRequest = (convertJsonArraytoArrayList(denialReasons.get()));
                Collections.sort(convertedRequest);
                System.out.println("Denial reasons from scenario: " + convertedRequest);

                ArrayList<String> tempDenialReason = (ArrayList<String>) programResponseList.get(i).get("denialReasons");
                Collections.sort(tempDenialReason);
                System.out.println("Denial reasons from response: " + tempDenialReason);

                for (int j = 0; j < tempDenialReason.size(); j++) {
                    Assert.assertTrue(convertedRequest.get(i).replace("\"", "").equalsIgnoreCase(tempDenialReason.get(i)), "Scenario denial reasons arent same with response denial reasons");
                }
            } else {
                ArrayList<String> tempDenialReason = (ArrayList<String>) programResponseList.get(i).get("denialReasons");
                assertTrue(tempDenialReason.isEmpty(), "denial reasons are available for eligible status");
            }
            eligibilityIdListFromResponse.add((Integer) programResponseList.get(i).get("eligibilityId"));
            programIdListFromResponse.add((Integer) programResponseList.get(i).get("programId"));
        }

        Collections.sort(eligibilityIdListFromResponse);
        Collections.sort(programIdListFromResponse);
        Assert.assertTrue(eligibilityIdListFromResponse.equals(eligibilityIdList.get()), "Eligibility ID isn't verified for eligibility update");
        Assert.assertTrue(programIdListFromResponse.equals(applicationProgramIdList.get()), "Application Program ID isn't verified for eligibility update");

        applicationConsumerIdList.get().clear();
        eligibilityStatusList.get().clear();
        eligibilityIdList.get().clear();
        applicationProgramIdList.get().clear();
    }

    @And("I clear application save submit {string} list")
    public void iClearApplicationSaveSubmitList(String listType) {
        switch (listType) {
            case "RESPONSE":
                memberMatchingResponse.get().clear();
                break;
            case "APP ID":
                memberMatchingAppIdForUI.get().clear();
                break;
            case "REQUEST":
                appRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/createApplication_1.json");
                System.out.println("Cleared request payload is: "+appRequestPayload);
                break;
            case "AppIDList":
                appIdList.set(new ArrayList<>());
                break;
            default:
                fail("Provided value does not match List Type");
        }
    }

    @Then("I verify that there is no {string} task is created")
    public void i_verify_that_there_is_no_task_is_created(String taskName) {
        List<Map<String, Object>> linkData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links");
        if (linkData.size() != 0) {
            for (int i = 0; i < linkData.size(); i++) {
                if ("Case".equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.name").get(i).toString())) {
                    assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.type").get(i));
                } else if ("Consumer Profile".equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.name").get(i).toString())) {
                    assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.type").get(i));
                } else {
                    assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.type").get(i).toString().equalsIgnoreCase(taskName), "Task type isnt verified");
                }
            }
        } else {
            assertTrue(linkData.size() == 0);
        }

    }

    @Then("I verify response details has additional fields for View Application Summary API")
    public void iVerifyResponseDetailsHasAdditionalFieldsForViewApplicationSummaryAPI(Map<String, String> datatable) {
        applicationLevelValues.set(datatable);
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "APPLICATION CYCLE":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationCycle"), datatable.get("APPLICATION CYCLE"), "Actual Application Cycle does not match expected Application Cycle:");
                    break;
                case "APPLICATION RECEIVED DATE":
                    if (datatable.get("APPLICATION RECEIVED DATE").equalsIgnoreCase("TODAY")) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationReceivedDate"), getCurrentDateInYearFormat(), "Actual APPLICATION RECEIVED DATE does not match expected APPLICATION RECEIVED DATE:");
                    } else
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationReceivedDate"), datatable.get("APPLICATION RECEIVED DATE"), "Actual APPLICATION RECEIVED DATE does not match expected APPLICATION RECEIVED DATE:");
                    break;
                case "APPLICATION TYPE":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationType"), datatable.get("APPLICATION TYPE"), "Actual APPLICATION TYPE does not match expected APPLICATION TYPE");
                    break;
                case "EXTERNAL APPLICATION ID":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].externalApplicationId"), externalAppID.get(), "External Application ID  does not match expected External Application ID :");
                    break;
                case "APPLICATION STATUS":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationStatus"), datatable.get("APPLICATION STATUS"), "Application Status  does not match expected Application status :");
                    break;
                default:
                    fail("Application Info Key does not match Entered Value");
            }
        }
    }

    @Then("I verify the error message for invalid Application ID for view API summary as {string}")
    public void iVerifyTheErrorMessageForInvalidApplicationIDForViewAPISummaryAs(String expectedMessage) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message"), expectedMessage);
    }

    @Then("I verify View Application Summary API returns {string} multiple records")
    public void iVerifyViewApplicationSummaryAPIReturnsMultipleRecords(String recordCount) {
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        int recordCountInt = Integer.parseInt(recordCount);
        for (int i = 0; i < recordCountInt; i++) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[" + i + "].externalApplicationId").equals(externalAppID.get()), "External Application ID isn't verified for " + (i + 1) + ". record of View Application Summary");
            System.out.println("External Application ID verified for " + (i + 1) + ". record of View Application Summary");
        }
    }

    @Then("I verify the details of the retrieve Application Summary API")
    public void iVerifyTheDetailsOfTheRetrieveApplicationSummaryAPI() {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationId").toString(), applicationIdAPI.get(), "Application ID isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationStatus"), "Entering Data", "application Status isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].externalApplicationId"), externalAppID.get(), "External App ID isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationCycle"), applicationLevelValues.get().get("APPLICATION CYCLE"), "Application Cycle isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationType"), applicationLevelValues.get().get("APPLICATION TYPE"), "Application Cycle isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationReceivedDate"), getCurrentDateInYearFormat(), "Application Cycle isn't verified for retrieve Application Summary API");

        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].applicationConsumerId").toString().chars().allMatch(Character::isDigit), "Application Consumer Id isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].consumerRoleType"), "Primary Individual", "Consumer Role Type isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].consumerFirstName"), piFirstName.get(), "Consumer First Name isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].consumerLastName"), piLastName.get(), "Consumer Last Name Cycle isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].consumerStatus"), "Received", "Consumer Status isn't verified for retrieve Application Summary API");

        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].programs[0].programId").toString().chars().allMatch(Character::isDigit), "Program ID isn't verified for retrieve Application Summary API");
        Assert.assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].programs[0].eligibilityId"), "Eligibility ID isn't verified for retrieve Application Summary API");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].programs[0].programType").toString(), "HCBS", "Program Type isn't verified for retrieve Application Summary API");
        Assert.assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].applicationConsumers[0].programs[0].eligibilityStatus"), "Eligibility Status isn't verified for retrieve Application Summary API");
    }

    @Then("I verify the Updated On and Updated By for program {string} save")
    public void iVerifyTheUpdatedOnAndUpdatedByForProgramSave(String eligibilityType) {

        switch (eligibilityType) {

            case "ELIGIBILITY":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].eligibilityStatus"), "Eligible");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].programType"), "Medicaid");
                assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].updatedOn").equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].createdOn")));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].updatedBy"), loggedInUserId);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].createdBy"), "ATS Service");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].determinationDate"), getCurrentDateInYearFormat());
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].startDate"), getCurrentDateInYearFormat());
                break;
            case "NOT ELIGIBILITY":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].eligibilityStatus"), "Not Eligible");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].programType"), "Medicaid");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].denialReasons[0]"), "Over Age");
                assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].updatedOn").equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].createdOn")));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].updatedBy"), loggedInUserId);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].createdBy"), "ATS Service");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].determinationDate"), getCurrentDateInYearFormat());
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[0].programs[0].startDate"), getCurrentDateInYearFormat());
                break;
        }
    }

    @Then("I initiated bpm process get api for application data service")
    public void i_initiated_bpm_process_get_api_for_application_data_service() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        if (!duplicateId.get().equals("")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(bpmInstance + duplicateId.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(bpmInstance + applicationIdAPI.get());
        }
    }

    @Then("I run bpm process get api using application id")
    public void i_run_bpm_process_get_api_using_application_id() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt correct for BPM Instance call");
    }

    @Then("I verify camunda process should be terminated")
    public void i_verify_camunda_process_should_be_terminated() {
        assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("bpmInstanceId").toString().isEmpty(), "bpmInstanceId Id is null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("applicationId").toString().equals(duplicateId), "Application id isnt verified");
        assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdBy").toString().isEmpty(), "createdBy Id is not correct");
        assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedBy").toString().isEmpty(), "updatedBy Id is not correct");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("createdOn").toString()), "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("updatedOn").toString()), "updatedOn date time field IS NOT valid");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("state").toString(), "COMPLETED", "State is not correct, process isnt terminated");
    }

    @When("I initiate Application send to research Initiate Action API")
    public void iInitiateApplicationSendToResearchInitiateActionAPI(List<Map<String, String>> data) {
        requestParams.set(getRequestPayloadFromPath("crm/ats/initiateAction.json"));
        Map<String, String> keysToOverride = data.get(0);
        for (String key : keysToOverride.keySet()) {
            switch (keysToOverride.get(key)) {
                case "250 CHARACTERS":
                    requestParams.get().addProperty(key, RandomStringUtils.randomAlphanumeric(250));
                    break;
                case "251 CHARACTERS":
                    requestParams.get().addProperty(key, RandomStringUtils.randomAlphanumeric(251));
                    break;
                case "Document Misclassified":
                    requestParams.get().getAsJsonArray("reasons").add(keysToOverride.get(key));
                    break;
                case "Insufficient Information":
                    requestParams.get().getAsJsonArray("reasons").add(keysToOverride.get(key));
                    break;
                case "Multiple Matches":
                    requestParams.get().getAsJsonArray("reasons").add(keysToOverride.get(key));
                    break;
                case "ultiple Matches":
                    requestParams.get().getAsJsonArray("reasons").add(keysToOverride.get(key));
                    break;
                case "Document/Insufficient":
                    requestParams.get().getAsJsonArray("reasons").add("Document Misclassified");
                    requestParams.get().getAsJsonArray("reasons").add("Insufficient Information");
                    break;
                case "Document/Multiple":
                    requestParams.get().getAsJsonArray("reasons").add("Document Misclassified");
                    requestParams.get().getAsJsonArray("reasons").add("Multiple Matches");
                    break;
                case "Insufficient/Multiple":
                    requestParams.get().getAsJsonArray("reasons").add("Insufficient Information");
                    requestParams.get().getAsJsonArray("reasons").add("Multiple Matches");
                    break;
                case "Document/Insufficient/Multiple":
                    requestParams.get().getAsJsonArray("reasons").add("Document Misclassified");
                    requestParams.get().getAsJsonArray("reasons").add("Insufficient Information");
                    requestParams.get().getAsJsonArray("reasons").add("Multiple Matches");
                    break;
                default:
                    requestParams.get().addProperty(key, keysToOverride.get(key));
            }
        }
    }

    @And("I Post Application send to research Initiate Action API")
    public void iPostApplicationSendToResearchInitiateActionAPI() {
        waitFor(10);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp + "/" + applicationIdAPI.get() + initiateActions);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
    }

    @Then("I verify Application send to research Initiate Action API")
    public void iVerifyApplicationSendToResearchInitiateActionAPI() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("data"), "Application Action Initiated Successfully");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("status"), "success");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @And("I will verify the response details for get linked applications for initiate actions")
    public void iWillVerifyTheResponseDetailsForGetLinkedApplicationsForInitiateActions() {
        List<Map<String, String>> linksResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("links");
        for (int i = 0; i < linksResponse.size(); i++) {
            Assert.assertEquals(linksResponse.get(i).get("statusDate"), getCurrentDateInYearFormat(), "Status Date isn't verified for Retrieve Linked Applications API");
            if (linksResponse.get(i).get("type").contains("Research")) {
                Assert.assertEquals(linksResponse.get(i).get("name"), "Task", "Name isn't verified for Retrieve Linked Applications API");
                Assert.assertEquals(linksResponse.get(i).get("type"), "Member Matching Research", "Type isn't verified for Retrieve Linked Applications API");
                Assert.assertEquals(linksResponse.get(i).get("status"), "Created", "Status isn't verified for Retrieve Linked Applications API");
            } else {
                Assert.assertEquals(linksResponse.get(i).get("name"), "Task", "Name isn't verified for Retrieve Linked Applications API");
                Assert.assertEquals(linksResponse.get(i).get("type"), "Member Matching", "Type isn't verified for Retrieve Linked Applications API");
                Assert.assertEquals(linksResponse.get(i).get("status"), "Complete", "Status isn't verified for Retrieve Linked Applications API");
            }
            Assert.assertTrue(String.valueOf(linksResponse.get(i).get("id")).chars().allMatch(Character::isDigit), "ID isn't verified for Retrieve Linked Applications API");
            System.out.println(i + 1 + ".Retrieved Linked Entity for Application Verified ");
        }
    }

    @Then("I verify Application send to research Initiate Action API {string} Failure")
    public void iVerifyApplicationSendToResearchInitiateActionAPIFailure(String errorType) {
        List<Map<String, String>> linksResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors");
        switch (errorType) {
            case "NOTES":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 400);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "invalid");
                Assert.assertEquals(linksResponse.get(0).get("message"), "Notes length should not exceed 250 Characters", "Expected Exceeded Character Notes message but none found");
                break;
            case "REASONS":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 400);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "invalid");
                assertEquals(linksResponse.get(0).get("message"), "ultiple Matches is Invalid value for reason. Possible values are Document Misclassified, Insufficient Information, Multiple Matches", "Expected Exceeded Character Notes message but none found");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].fields.reason"), "ultiple Matches", "Expected invalid reasons value but found different");
                break;
            case "ACTIONS":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 400);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "invalid");
                assertEquals(linksResponse.get(0).get("message"), "end To Research is Invalid value for action. Possible values are Send To Research", "Expected Exceeded Character Notes message but none found");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].fields.action"), "end To Research", "Expected invalid actions value but found different");
                break;
        }
    }

    @And("I initiate and run get application details for link component api")
    public void iInitiateAndRunGetApplicationDetailsForLinkComponentApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        if (!inbCreatedAppId.get().equals("")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(viewApp + viewAppDetails + applicationIdAPI.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(viewApp + viewAppDetails + inbCreatedAppId.get());
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt correct for get details for link component details response");
    }

    @And("I verify get application details for link component details response for values")
    public void iVerifyGetApplicationDetailsForLinkComponentDetailsResponseForValues(Map<String, String> expectedData) {
        waitFor(2);
        if (!inbCreatedAppId.get().equals("")) {
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.id").toString(), applicationIdAPI.get(), "Application ID is not valid for  get details for link component details response.");
        } else {
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.id").toString(), inbCreatedAppId.get(), "Application ID is not valid for  get details for link component details response.");
        }
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.status"), expectedData.get("status"), "Application Status is not valid for  get details for link component details response");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.name"), "Application", "Application name is not valid for  get details for link component details response");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.type"), expectedData.get("type"), "Application type is not valid for  get details for link component details response");
        if (expectedData.get("statusDate").equalsIgnoreCase("CURRENT YYYYMMDD")) {
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.statusDate"), getCurrentDateInYearFormat(), "Status Date is not valid for  get details for link component details response");
        } else {
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.statusDate"), expectedData.get("statusDate"), "Status Date is not valid for  get details for link component details response");
        }
    }

    @Then("I verify the error message in the response when submitting application without minimum information")
    public void iVerifyTheErrorMessageInTheResponseWhenSubmittingApplicationWithoutMinimumInformation() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "error", "Mismatch in expected status");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message"), "Application is saved but not submitted as it is missing the critical information", "Mismatch in expected error message");
    }

    @Then("I will get {string} ID for {string} type from the application links response")
    public void i_will_get_ID_with_type_from_the_response(String component, String taskType) {
        waitFor(3);
        List<Map<String, Object>> linkData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links");
        for (int i = 0; i < linkData.size(); i++) {
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.name").get(i).toString().equalsIgnoreCase(component)) {
                switch (component) {
                    case "Outbound Correspondence":
                        createdOutboundCorrespondenceID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.id").get(i).toString());
                        System.out.println("Link between Outbound Correspondence & Application is created");
                        break;
                    case "Task":
                        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.type").get(i).toString().equalsIgnoreCase(taskType)) {
                            World.generalList.get().clear();
                            World.generalList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.id").get(i).toString());
                            System.out.println("Value in World: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.id").get(i).toString());
                            taskIDFromLinks.set( World.generalList.get().get(0));
                            System.out.println("API Created Task id from links: " + World.generalList.get().get(0));
                            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.type").get(i).toString());
                            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.type").get(i).toString().equalsIgnoreCase(taskType), "Task type isnt verified");
                        }
                        break;
                    case "Inbound Correspondence":
                        sendEventAndCreateLinksController.documentId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.id").get(i).toString());
                        System.out.println("Task id from links: " + sendEventAndCreateLinksController.documentId);
                        break;
                    default:
                        Assert.fail("Task Type Didn't match");
                }
            }
        }
    }

    @Then("I verify the response includes {string} application received language for {string} appplication")
    public void iVerifyTheResponseIncludesApplicationReceivedLanguageSetToTheEndFor(String language, String applicationType) {
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        String expectedApplicationReceivedLanguage = "";
        applicationReceivedLanguage.set(language);
        if (applicationType.equalsIgnoreCase("MEDICAL ASSISTANCE")) {
            expectedApplicationReceivedLanguage = applicationReceivedLanguage.get();
        } else if (applicationType.equalsIgnoreCase("LONG TERM CARE")) {
            expectedApplicationReceivedLanguage = applicationReceivedLanguage.get();
        }
        assertEquals(appData.get("applicationReceivedLanguage"), expectedApplicationReceivedLanguage, "Expected Application Received Language : " + expectedApplicationReceivedLanguage + " didn't match with actual Application Received Language: " + appData.get("applicationReceivedLanguage"));
    }

    /**
     * Scenario step created to reinitialize duplicate static data for consumers
     *
     * @param types
     */
    @And("I reinitialize Application data for {string} for duplicates")
    public void iReinitializeApplicationDataForForDuplicates(String types) {
        switch (types) {
            case "ALL DUPLICATES DATA":
                reDoDuplicateData();
                break;
            case "AM DUPLICATES DATA":
                synchronized (duplicateFirstnameAm){
                    duplicateFirstnameAm.set(RandomStringUtils.randomAlphabetic(10));
                }
                synchronized (duplicateLastNameAm){
                    duplicateLastNameAm.set(RandomStringUtils.randomAlphabetic(10));
                }
                synchronized (duplicateDOBAm){
                    duplicateDOBAm.set(BrowserUtils.getPriorDateFormatYYYYMMdd(Integer.parseInt(getRandomNumber(4))));
                }
               synchronized (duplicateSSNAm){
                   duplicateSSNAm.set(RandomStringUtils.randomNumeric(9));

               }
                break;
            case "ALL STATIC DATA":
                reDoDuplicateData();
                memberMatchingResponse.get().clear();
                memberMatchingAppId.get().clear();
                assertEquals(memberMatchingResponse.get().size(), 0);
                assertEquals(memberMatchingAppId.get().size(), 0);
                break;
            default:
                fail("Provided Key does not match case");
        }
    }

    private void reDoDuplicateData() {
        String toCheckFM = duplicateFirstname.get();
        String toCheckLM = duplicateLastName.get();
        String toCheckDOB = duplicateDOB.get();
        String toCheckSSN = duplicateSSN.get();
        synchronized (duplicateFirstname){
            duplicateFirstname.set(RandomStringUtils.randomAlphabetic(10));
        }
        synchronized (duplicateLastName){
            duplicateLastName.set(RandomStringUtils.randomAlphabetic(10));
        }
        synchronized (duplicateDOB){
            duplicateDOB.set(getPriorDateFormatYYYYMMdd(Integer.parseInt(getRandomNumber(4))));
        }
        synchronized (duplicateSSN){
            duplicateSSN.set(RandomStringUtils.randomNumeric(9));
        }

        assertFalse(toCheckFM.equals(duplicateFirstname), "Duplicate FM still assigned previous value");
        assertFalse(toCheckLM.equals(duplicateLastName), "Duplicate LM still assigned previous value");
        assertFalse(toCheckDOB.equals(duplicateDOB), "Duplicate DOB still assigned previous value");
        assertFalse(toCheckSSN.equals(duplicateSSN), "Duplicate SSN still assigned previous value");
    }

    @And("I I clear the {string} list")
    public void iIClearTheList(String idType) {
        switch (idType) {
            case "application ID":
                appIdList.get().clear();
                break;
            case "CASE CONSUMER ID":
                searchCaseIdList.get().clear();
                searchConsumerIdList.get().clear();
                break;
        }
    }

    @And("I initiate and POST search Case Consumers with {string} Application consumer to retrieve and store ids")
    public void iInitiateAndPOSTSearchCaseConsumersWithApplicationConsumerToRetrieveAndStoreIds(String searchType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseConsumers);
        switch (searchType) {
            case "PRIMARY INDIVIDUAL DUPLICATE":
                firstNameSearch.set(duplicateFirstname.get());
                lastNameSearch.set(duplicateLastName.get());
                caseSearchRequestParams.get().addProperty("consumerFirstName", firstNameSearch.get());
                caseSearchRequestParams.get().addProperty("consumerLastName", lastNameSearch.get());
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(caseSearchRequestParams.get());
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Expected 200 status code but found unexpected status code");
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                waitFor(3);
                searchCaseIdList.get().add(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("object.result[0].cases.caseId")));
                searchConsumerIdList.get().add(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("object.result[0].consumers[0].consumerId")));
                System.out.println("Search Case Id List = " + searchCaseIdList);
                System.out.println("Search Consumer Id List = " + searchConsumerIdList);
                break;
            case "PRIMARY INDIVIDUAL PI":
                firstNameSearch.set(piFirstName.get());
                lastNameSearch.set(piLastName.get());
                caseSearchRequestParams.get().addProperty("consumerFirstName", firstNameSearch.get());
                caseSearchRequestParams.get().addProperty("consumerLastName", lastNameSearch.get());
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(caseSearchRequestParams.get());
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Expected 200 status code but found unexpected status code");
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
                waitFor(3);
                searchCaseIdList.get().add(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("object.result[0].cases.caseId")));
                searchConsumerIdList.get().add(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("object.result[0].consumers[0].consumerId")));
                System.out.println("Search Case Id List = " + searchCaseIdList);
                System.out.println("Search Consumer Id List = " + searchConsumerIdList);
                break;
        }
    }

    @And("I initiate and PUT consumer request to update {string} with following")
    public void iInitiateAndPUTConsumerRequestToUpdateWithFollowing(String consumer, List<Map<String, String>> data) {
        consumerUpdateRequestPayload.getAsJsonObject().addProperty("consumerId", searchConsumerIdList.get().get(0));
        consumerUpdateRequestPayload.getAsJsonObject().addProperty("caseId", searchCaseIdList.get().get(0));
        consumerUpdateRequestPayload.getAsJsonObject().addProperty("consumerFirstName", firstNameSearch.get());
        consumerUpdateRequestPayload.getAsJsonObject().addProperty("consumerLastName", lastNameSearch.get());
        synchronized (consumerExternalId){
            consumerExternalId.set(RandomStringUtils.randomAlphanumeric(10));
        }
        switch (consumer) {
            case "PRIMARY CONSUMER":
                toAssignDataToUpdateConsumer(data);
                break;
            case "PRIMARY CONSUMER WITH EXISTING EXID AND TYPE":
                toAssignDataToUpdateConsumer(data);
                break;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(consumerUpdate);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(consumerUpdateRequestPayload);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    public void toAssignDataToUpdateConsumer(List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        for (String key : keysToOverride.keySet()) {
            switch (keysToOverride.get(key)) {
                case "RANDOM ID":
                    consumerUpdateRequestPayload.getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().addProperty(key, consumerExternalId.get());
                    break;
                case "CHIP":
                    consumerUpdateRequestPayload.getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().addProperty(key, "CHIP");
                    break;
                case "null":
                    consumerUpdateRequestPayload.getAsJsonObject().addProperty(key, (String) null);
                    break;
                case "DUPLICATE SSN":
                    consumerUpdateRequestPayload.getAsJsonObject().addProperty("consumerSSN", duplicateSSN.get());
                    break;
                case "RANDOM SSN":
                    consumerUpdateRequestPayload.getAsJsonObject().addProperty("consumerSSN", piSSN.get());
                    break;
                case "EXID SAME AS APPLICATION":
                    consumerUpdateRequestPayload.getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().addProperty(key, consumerExternalId.get());
                    break;
                default:
                    consumerUpdateRequestPayload.getAsJsonObject().addProperty(key, keysToOverride.get(key));
            }
        }
    }

    @Then("I verify the matching response has {string} benefit status with {string} on the response")
    public void i_verify_the_matching_response_has_benefit_status_with_on_the_response(String benefitStatus, String benefitType) {
        if(benefitStatus.equalsIgnoreCase("null") || benefitType.equalsIgnoreCase("null")){
            assertNull(matchApiResponse.get().jsonPath().getList("data.matchingCollections.consumer[0].eligibilities"));
        } else {
            matchingApplicationEligibilityStatusList.set(matchApiResponse.get().jsonPath().getList("data.matchingCollections.consumers[0].eligibilities[0].benefitStatus"));
            matchingApplicationEligibilityPopulationList.set(matchApiResponse.get().jsonPath().getList("data.matchingCollections.consumers[0].eligibilities[0].programPopulation"));
            assertEquals(matchingApplicationEligibilityStatusList.get().get(0), benefitStatus, "Benefit status isnt verified");
            assertEquals(matchingApplicationEligibilityPopulationList.get().get(0), benefitType, "Benefit population isnt verirfied");
        }

    }

    @Then("I verify the matching response has null value for Eligibility details")
    public void i_verify_the_matching_response_has_null_value_for_Eligibility_details() {
        assertNull(matchApiResponse.get().jsonPath().getList("data.matchingCollections.consumer[0].eligibilities"));

    }

    @Then("I initiated Save Notes API for ats")
    public void i_initiated_Save_Notes_API_for_ats() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp + notes);
        System.out.println("Endpoint for notes: " + baseApplicationDataUrl + createApp + notes);
    }

    @Then("I initiated POST Notes API to retrieve note details")
    public void i_initiated_POST_Notes_API_to_retrieve_note_details(){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp+"/notedetails");
        System.out.println("Endpoint for to GET notes: "+baseApplicationDataUrl + createApp + "/notedetails");
    }

    @Then("I run Save Notes API for {string} level")
    public ResponseBody i_run_Save_Notes_API_for_level(String saveType) { //updated due to CP-39289
        consumerRoleList.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerRoleType"));
        consumerIdList.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.applicationConsumerId"));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/saveNotes.json");
        if (saveType.equalsIgnoreCase("applicationConsumerPI")) {
            for (int i = 0; i < consumerRoleList.get().size(); i++) {
                if (consumerRoleList.get().get(i).equalsIgnoreCase("Primary Individual")) {
                    applicationConsumerId.set(consumerIdList.get().get(i));
                }
            }
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationConsumerId", applicationConsumerId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", applicationIdAPI.get());
         //   apitdu.jsonElement.getAsJsonObject().addProperty("applicationId", "null");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("noteText", getRandomString(100));
        } else if (saveType.equalsIgnoreCase("applicationConsumerAM")) {
            for (int i = 0; i < consumerRoleList.get().size(); i++) {
                if (consumerRoleList.get().get(i).equalsIgnoreCase("Application Member")) {
                    applicationConsumerId.set(consumerIdList.get().get(i));
                }
            }
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationConsumerId", applicationConsumerId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", applicationIdAPI.get());
         //   apitdu.jsonElement.getAsJsonObject().addProperty("applicationId", "null");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("noteText", getRandomString(100));
        } else if (saveType.equalsIgnoreCase("applicationConsumerAR")) {
            for (int i = 0; i < consumerRoleList.get().size(); i++) {
                if (consumerRoleList.get().get(i).equalsIgnoreCase("Authorized Rep")) {
                    applicationConsumerId.set(consumerIdList.get().get(i));
                }
            }
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationConsumerId", applicationConsumerId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", applicationIdAPI.get());
         //   apitdu.jsonElement.getAsJsonObject().addProperty("applicationId", "null");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("noteText", getRandomString(100));
        } else if (saveType.equalsIgnoreCase("application")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationConsumerId", "null");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", applicationIdAPI.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("noteText", getRandomString(100));
        } else if (saveType.equalsIgnoreCase("unexpected length")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationConsumerId", "null");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", applicationIdAPI.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("noteText", getRandomString(1001));
        }

        saveNotesRequest.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("Save notes request payload = " + saveNotesRequest.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(saveNotesRequest.get().toString());
        saveNotesResponse.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        System.out.println(saveNotesResponse.get().prettyPrint());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return saveNotesResponse.get();
    }

    @Then("I verify Save Notes response includes {string} level details")
    public void i_verify_Save_Notes_response_includes_level_details(String saveType) {

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt verified for save notes");
     //   assertTrue(api.jsonPathEvaluator.get("status").toString().equals("SUCCESS"), "Status isnt verified for save notes");

        assertTrue((API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].noteId").toString() + "").chars().allMatch(Character::isDigit));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].createdBy").toString().equals("1066"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].updatedBy").toString().equals("1066"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].noteText").toString().equals(saveNotesRequest.get().get("noteText").getAsString()));

        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].createdOn").toString()), "createdOn isnt verified for save notes");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].updatedOn").toString()), "updatedOn isnt verified for save notes");

        if (saveType.equalsIgnoreCase("application")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].applicationId").toString().equals(applicationIdAPI.get()));
            assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].applicationConsumerId"));
        } else {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].applicationId").toString().equals(applicationIdAPI.get()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("notes[0].applicationConsumerId").toString().equals(saveNotesRequest.get().get("applicationConsumerId").getAsString()));
        }
    }

    @Then("I verify Save Notes response includes error message for length")
    public void i_verify_Save_Notes_response_includes_error_message_for_length() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("validationList[0].validationErrorCode").toString().equals("Note Text length should not exceed 1000 Characters"));
    }

    @Then("I run POST Notes API for {string} level")
    public void i_run_POST_Notes_API_for_level(String saveType) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/getNotes.json");

      if (saveType.equalsIgnoreCase("application")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationConsumerId", "null");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", applicationIdAPI.get());
        } else {
          API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationConsumerId", applicationConsumerId.get());
          API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", "null");
      }

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
      API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
     //   api.GETWithRequestBody(requestParams.toString());
        System.out.println("Response body from GET Call " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint());
        System.out.println("Status code from GET Call " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
    }



}