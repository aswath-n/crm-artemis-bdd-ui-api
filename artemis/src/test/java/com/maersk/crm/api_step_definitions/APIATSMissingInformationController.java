package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.step_definitions.CRM_CreateApplicationStepDef;
import com.maersk.crm.step_definitions.CRM_MissingInformationTabStepDef;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.*;
import java.time.LocalDateTime;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.*;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.apiUserID;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.createdConsumerDetails;
import static org.testng.Assert.*;

public class APIATSMissingInformationController extends CRMUtilities implements ApiBase {

    APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    EventsUtilities eventsUtilities = new EventsUtilities();
    private ApiTestDataUtil testDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    CRM_MissingInformationTabStepDef mIUiTab = new CRM_MissingInformationTabStepDef();
    CRM_CreateApplicationStepDef createApplication = new CRM_CreateApplicationStepDef();

    private final String baseMissingInfoUrl = ConfigurationReader.getProperty("apiMissingInfoAPi");
    private final String baseApplicationDataUrl = ConfigurationReader.getProperty("apiATSApplicationData");
    private final String baseApiLinksUrl = ConfigurationReader.getProperty("apiLinksLink");
    private final String postMiLinkEndpoint = "/link";
    private final String getMiLinkByApplicationEndpoint = "/links/application/";
    private final String getMiLinkByMIEndpoint = "/links/missing_info/";
    private final String pageSizeValueEndPoint = "?size=5&page=0";
    private final String getMissingInfoEndPint = "/missing-information/";
    private final String createMissingInfoItem = "/mars/missinginformation";
    private final String createMIFromASService = "/missing-information";
    private final String getMIFromMIService = "/mars/search-missinginformations";
    private final String searchMIFromMIService = "/mars/search-missinginfo";
    private final String updateMIfromMIService = "/mars/missinginformation/";
    private final String initiateMIService = "/missing-information/initiate";
    private final ThreadLocal <JsonObject> appRequestPayload = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonObject> missingInfoRequestPayload = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <Map<String, String>> keysToOverride = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> dependentKeysToOverride = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Integer> entityId = ThreadLocal.withInitial(()->0);
    private final ThreadLocal <Integer> dependentRecordId = ThreadLocal.withInitial(()->0);
    private final ThreadLocal <String> missingInfoConsumerType =ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> missingInfoID = ThreadLocal.withInitial(String::new);
    final ThreadLocal <HashMap<String, Object>> actualMissingInfoDetails = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal <HashMap<String, String>> expectedMissingInfoDetails = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String,Object>> externalLinkDetailsMap = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <String> primaryFullName = ThreadLocal.withInitial(String::new);

    @And("I initiated create missing information api for ats using {string} service")
    public void iInitiatedCreateMissingInformationApiForAtsUsingMIService(String service) {
        if (service.equalsIgnoreCase("MI")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseMissingInfoUrl);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createMissingInfoItem);
        }
        if (service.equalsIgnoreCase("AS")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/" + applicationIdAPI.get() + createMIFromASService);
        }
    }

    @And("I initiate create missing information api for {string} using {string} service")
    public void iInitiateCreateMissingInformationApiForUsingServiceString(String consumerType, String service, List<Map<String, String>> data) {
        int applicationID = Integer.parseInt(applicationIdAPI.get());
        missingInfoConsumerType.set(consumerType);
        if (service.equals("MI")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/miMissingInfo.json");
            missingInfoRequestPayload.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject());
            missingInfoRequestPayload.get().get("tags").getAsJsonObject().addProperty("application", applicationID);
        }
        if (service.equals("AS")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/asMissingInfo.json");
            missingInfoRequestPayload.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject());
        }
        for (String consumerID : createdConsumerDetails.get().keySet()) {
            if (createdConsumerDetails.get().get(consumerID).equalsIgnoreCase(missingInfoConsumerType.get()))
                entityId.set(Integer.parseInt(consumerID));
            missingInfoRequestPayload.get().addProperty("entityId", entityId.get());
        }
        keysToOverride.set(data.get(0));
        for (String key : keysToOverride.get().keySet()) {
            if (keysToOverride.get().get(key).equalsIgnoreCase("RANDOM")) {
                missingInfoRequestPayload.get().addProperty(key, getRandomString(10));
            } else if (keysToOverride.get().get(key).equalsIgnoreCase("RANDOM DIGIT")) {
                missingInfoRequestPayload.get().addProperty(key, "" + getRandomNumber(5));
            } else if (keysToOverride.get().get(key).equalsIgnoreCase("TODAY'S DATE")) {
                missingInfoRequestPayload.get().addProperty(key, "" + getCurrentDateInYearFormat());
            } else {
                missingInfoRequestPayload.get().addProperty(key, keysToOverride.get().get(key));
            }
            if (key.equalsIgnoreCase("missingInfoItemId") && keysToOverride.get().get(key).equalsIgnoreCase("CURRENT")) {
                missingInfoRequestPayload.get().addProperty(key, missingInfoID.get());
            }
        }
    }

    @And("I provide the following request parameters to store a Missing Information Dependent Entity using {string} service")
    public void iInitiateMissingInformationDependentAPIUsingService(
            String service, List<Map<String, String>> data) {
        dependentKeysToOverride.set(data.get(0));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/missingInfoDependent.json");
        JsonObject dependent = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        for (String consumerID : createdConsumerDetails.get().keySet()) {
            if (createdConsumerDetails.get().get(consumerID).equalsIgnoreCase(missingInfoConsumerType.get())) {
                dependentRecordId.set(Integer.parseInt(consumerID));
            }
            dependent.addProperty("dependentRecordId", dependentRecordId.get());
        }
        for (String key : dependentKeysToOverride.get().keySet()) {
            if (dependentKeysToOverride.get().get(key).equalsIgnoreCase("RANDOM")) {
                dependentKeysToOverride.get().replace(key, getRandomString(10));
            } else if (dependentKeysToOverride.get().get(key).equalsIgnoreCase("RANDOM DIGIT")) {
                dependentKeysToOverride.get().replace(key, getRandomNumber(5));
            }
            dependent.addProperty(key, dependentKeysToOverride.get().get(key));
        }
        missingInfoRequestPayload.get().getAsJsonArray("missingInformationDependents").add(dependent);
    }

    @And("I provide scope {string} with scope ID {string} to missing information api")
    public void addscopeAndScopeIDtoMIPayload(String scope, String scopeID) {
        if (scopeID.equalsIgnoreCase("CURRENT"))
            scopeID = applicationIdAPI.get();
        appRequestPayload.get().getAsJsonObject("tags").addProperty(scope, Integer.parseInt(scopeID));
    }

    @And("I POST ATS save missing information api")
    public void iPOSTATSSaveMissingInformationApi() {
        JsonArray missingInfoRequestPayloadArray = new JsonArray();
        missingInfoRequestPayloadArray.add(missingInfoRequestPayload.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(missingInfoRequestPayloadArray);
        System.out.println("Entity id for save missing Info: " + entityId);
    }

    @Then("I get response from missing information ats api with status code {string}")
    public void i_get_response_from_application_ats_api_with_status_code_and_status(String statusCode) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, Integer.parseInt(statusCode), "Status code is not correct for Missing Information API Call");
        System.out.println("View Summary response is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I initiated retrieve Missing Information API for {string} Application using {string}")
    public void iInitiatedRetrieveMissingInformationAPIForApplicationUsing(String applicationID, String service) {
        // added wait for retrieve MI for Auto create scenarios
        waitFor(10);
        JSONObject obj = new JSONObject();
        if (service.equalsIgnoreCase("Application Service")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
            if (applicationID.equalsIgnoreCase("CURRENT")) {
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getMissingInfoEndPint + applicationIdAPI.get());
            } else if ("UI CREATED".equals(applicationID)) {
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getMissingInfoEndPint + CRM_CreateApplicationStepDef.applicationId);
            } else {
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getMissingInfoEndPint + applicationID);
            }
        }
        if (service.equalsIgnoreCase("Missing Information Service")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseMissingInfoUrl);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getMIFromMIService);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/miSearchMissingInfo.json");
            appRequestPayload.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
            System.out.println(appRequestPayload);
        }

    }

    @When("I run the Retrieve missing information API using {string}")
    public void iRunTheRetrieveMissingInformationAPIForTheApplicationUsing(String service) {
        if (service.equalsIgnoreCase("Missing Information Service")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload.get());
        }
        if (service.equalsIgnoreCase("Application Service")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        }
        System.out.println("GET View API Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        //Cmmenting below line as MI service returns only status code
        //assertTrue(api.jsonPathEvaluator.get("status").equals("success"));
        System.out.println("View response is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        World.generalSave.get().put("MissingInformationItemId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].missingInfoItemId"));

    }

    @Then("I verify all the response details of Retrieve Missing Information API response for {string}")
    public void iVerifyAllTheResponseDetailsOfRetrieveMissingInformationAPIResponseFor(String service) {
        Map<String, Object> missingInfoMap;
        if (service.equalsIgnoreCase("Missing Information Service")) {
            ArrayList updateResponseArray;
            updateResponseArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get();
            for (int i = 0; i < updateResponseArray.size(); i++) {
                missingInfoMap = (HashMap<String, Object>) updateResponseArray.get(i);
                List<Map<String, Object>> missingInformationDependentsList = (List<Map<String, Object>>) missingInfoMap.get("missingInformationDependents");
                Map<String, Object> missingInfoDependentsMap = missingInformationDependentsList.get(0);

                assertTrue(missingInfoMap.get("missingInfoItemId").toString().chars().allMatch(Character::isDigit), "Missing Info Item ID is not digit for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("recordClass"), keysToOverride.get().get("recordClass"), "Record Class isn't verified for Retrieve Missing Info Application API");
                assertTrue(missingInfoMap.get("entityId").toString().chars().allMatch(Character::isDigit), "Entity Id is not digit for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("entityRecordType"), keysToOverride.get().get("entityRecordType"), "EntityRecord Type isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("attributeName"), keysToOverride.get().get("attributeName"), "Attribute Name isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("updatedBy").toString(), apiUserID.get(), "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("createdBy").toString(), apiUserID.get(), "CreatedBy isn't valid for Retrieve Missing Info Application API");
                assertTrue(missingInfoMap.get("attributeValue").toString().chars().allMatch(Character::isAlphabetic), "Attribute Value isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("status"), keysToOverride.get().get("status"), "Status isn't verified for Retrieve Missing Info Application API");
                assertTrue(missingInfoMap.get("comments").toString().chars().allMatch(Character::isAlphabetic), "Comments isn't verified for Retrieve Missing Info Application API");
                assertTrue(EventsUtilities.isValidDate(missingInfoMap.get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                assertTrue(EventsUtilities.isValidDate(missingInfoMap.get("createdOn").toString()), "CreatedOn date time field IS NOT valid for Retrieve Missing Info Application API");

                assertTrue(missingInfoDependentsMap.get("missingInfoDependentId").toString().chars().allMatch(Character::isDigit), "MissingInfo Dependent Id isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoDependentsMap.get("dependentRecordId").toString(), "" + dependentRecordId.get(), "Dependent Record Id isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoDependentsMap.get("dependentType"), dependentKeysToOverride.get().get("dependentType"), "Dependent Type isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoDependentsMap.get("updatedBy").toString(), apiUserID.get(), "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                assertEquals(missingInfoDependentsMap.get("createdBy").toString(), apiUserID.get(), "CreatedBy isn't valid for Retrieve Missing Info Application API");
                assertTrue(EventsUtilities.isValidDate(missingInfoDependentsMap.get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                assertTrue(EventsUtilities.isValidDate(missingInfoDependentsMap.get("createdOn").toString()), "CreatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
            }
        }
        if (service.equalsIgnoreCase("Application Service")) {
            List<Map<String, Object>> missingInfoDataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data");
            for (int i = 0; i < missingInfoDataList.size(); i++) {
                missingInfoMap = missingInfoDataList.get(i);
                List<Map<String, Object>> missingInformationDependentsList = (List<Map<String, Object>>) missingInfoMap.get("missingInformationDependents");
                Map<String, Object> missingInfoDependentsMap = missingInformationDependentsList.get(0);

                assertTrue(missingInfoMap.get("missingInfoItemId").toString().chars().allMatch(Character::isDigit), "Missing Info Item ID is not digit for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("recordClass"), keysToOverride.get().get("recordClass"), "Record Class isn't verified for Retrieve Missing Info Application API");
                assertTrue(missingInfoMap.get("entityId").toString().chars().allMatch(Character::isDigit), "Entity Id is not digit for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("entityRecordType"), keysToOverride.get().get("entityRecordType"), "EntityRecord Type isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("attributeName"), keysToOverride.get().get("attributeName"), "Attribute Name isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("updatedBy").toString(), apiUserID.get(), "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("createdBy").toString(), apiUserID.get(), "CreatedBy isn't valid for Retrieve Missing Info Application API");
                assertTrue(missingInfoMap.get("attributeValue").toString().chars().allMatch(Character::isAlphabetic), "Attribute Value isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoMap.get("status"), keysToOverride.get().get("status"), "Status isn't verified for Retrieve Missing Info Application API");
                assertTrue(missingInfoMap.get("comments").toString().chars().allMatch(Character::isAlphabetic), "Comments isn't verified for Retrieve Missing Info Application API");
                assertTrue(EventsUtilities.isValidDate(missingInfoMap.get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                assertTrue(EventsUtilities.isValidDate(missingInfoMap.get("createdOn").toString()), "CreatedOn date time field IS NOT valid for Retrieve Missing Info Application API");

                assertTrue(missingInfoDependentsMap.get("missingInfoDependentId").toString().chars().allMatch(Character::isDigit), "MissingInfo Dependent Id isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoDependentsMap.get("dependentRecordId").toString(), "" + dependentRecordId.get(), "Dependent Record Id isn't verified for Retrieve Missing Info Application API");
                assertEquals(missingInfoDependentsMap.get("dependentType"), dependentKeysToOverride.get().get("dependentType"), "Dependent Type isn't verified for Retrieve Missing Info Application API");
                System.out.println(apiUserID.get());
                assertEquals(missingInfoDependentsMap.get("updatedBy").toString(), apiUserID.get(), "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                assertEquals(missingInfoDependentsMap.get("createdBy").toString(), apiUserID.get(), "CreatedBy isn't valid for Retrieve Missing Info Application API");
                assertTrue(EventsUtilities.isValidDate(missingInfoDependentsMap.get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                assertTrue(EventsUtilities.isValidDate(missingInfoDependentsMap.get("createdOn").toString()), "CreatedOn date time field IS NOT valid for Retrieve Missing Info Application API");

                if (service.equalsIgnoreCase("Application Service")) {
                    switch (missingInfoConsumerType.get()) {
                        case "APPLICATION MEMBER":
                            //assertEquals(missingInfoMap.get("consumerFullName").toString(), amFirstName + " " + amLastName, "Consumer Full Name isn't valid for Retrieve Missing Info Application API");
                            assertEquals(missingInfoDependentsMap.get("consumerFullName").toString(), amFirstName.get() + " " + amLastName.get(), "ConsumerFullName isn't valid for Retrieve Missing Info Application API");
                            System.out.println("Retrieve Missing Info Application API validated for Application Consumer");
                            break;
                        default:
                            assertEquals(missingInfoMap.get("consumerFullName").toString(), piFirstName.get() + " " + piLastName.get(), "Consumer Full Name isn't valid for Retrieve Missing Info Application API");
                            // assertEquals(missingInfoDependentsMap.get("consumerFullName").toString(), piFirstName + " " + piLastName, "ConsumerFullName isn't valid for Retrieve Missing Info Application API");
                            System.out.println("Retrieve Missing Info Application API validated for Primary Individual");
                            break;
                    }
                    switch (missingInfoConsumerType.get()) {
                        case "APPLICATION MEMBER":
                            assertEquals(missingInfoMap.get("consumerFullName").toString(), amFirstName.get() + " " + amLastName.get(), "Consumer Full Name isn't valid for Retrieve Missing Info Application API");
                            assertEquals(missingInfoDependentsMap.get("consumerFullName").toString(), amFirstName.get() + " " + amLastName.get(), "ConsumerFullName isn't valid for Retrieve Missing Info Application API");
                            System.out.println("Retrieve Missing Info Application API validated for Application Consumer");
                            break;
                        default:
                            assertEquals(missingInfoMap.get("consumerFullName").toString(), piFirstName.get() + " " + piLastName.get(), "Consumer Full Name isn't valid for Retrieve Missing Info Application API");
                            assertEquals(missingInfoDependentsMap.get("consumerFullName").toString(), piFirstName.get() + " " + piLastName.get(), "ConsumerFullName isn't valid for Retrieve Missing Info Application API");
                            System.out.println("Retrieve Missing Info Application API validated for Primary Individual");
                            break;
                    }
                }
            }
        }
    }

    @Then("I verify the sort order for Retrieve Missing Information API")
    public void iVerifyTheSortOrderForRetrieveMissingInformationAPI() {
        List<Map<String, Object>> missingInfoDataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data");
        List<String> statusList = new ArrayList<>();
        List<Integer> missingInfoItemIdList = new ArrayList<>();
        for (int i = 0; i < missingInfoDataList.size(); i++) {
            Map<String, Object> tempMap = missingInfoDataList.get(i);
            statusList.add(tempMap.get("status").toString());
            missingInfoItemIdList.add((Integer) tempMap.get("missingInfoItemId"));
        }
        for (int i = 0; i < statusList.size() - 1; i++) {
            if (statusList.get(i).equalsIgnoreCase(statusList.get(i + 1))) {
                assertTrue(missingInfoItemIdList.get(i) > missingInfoItemIdList.get(i + 1), "Missing Info Item Id sort is not as expected for Retrieve Missing Info Application API");
                statusList.remove(i + 1);
                missingInfoItemIdList.remove(i + 1);
            }
        }
        if (statusList.contains("PENDING")) {
            assertEquals(statusList.get(0), "PENDING", "Sorting according to Status is not as expected for Retrieve Missing Info Application API");
            statusList.remove(0);
        }
        if (statusList.contains("SATISFIED")) {
            assertEquals(statusList.get(0), "SATISFIED", "Sorting according to Status is not as expected for Retrieve Missing Info Application API");
            statusList.remove(0);
        }
        if (statusList.contains("DISREGARDED")) {
            assertEquals(statusList.get(0), "DISREGARDED", "Sorting according to Status is not as  for Retrieve Missing Info Application API");
            statusList.remove(0);
        }
        assertTrue(statusList.isEmpty(), "Sorting according to Status is not as  for Retrieve Missing Info Application API");
    }

    @And("I get Missing Info Details from the {string} API response")
    public void iGetMissingInfoIDFromAPIResponse(String apiType) {
        ArrayList updateResponseArray;
        if (apiType.equalsIgnoreCase("Save")) {
            updateResponseArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("missingInfoItems.data");
            actualMissingInfoDetails.set((HashMap<String, Object>) updateResponseArray.get(0));
        } else {
            actualMissingInfoDetails.set((HashMap<String, Object>) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObjectMap);
        }
        missingInfoID.set(actualMissingInfoDetails.get().get("missingInfoItemId").toString());
    }

    @Then("I verify missing information has updated on automatically set")
    public void i_verify_PI_UPDATEDON_is_later_than_PI_CREATEDON() {
        String createdOnStr = actualMissingInfoDetails.get().get("createdOn").toString();
        String updatedOnStr = actualMissingInfoDetails.get().get("updatedOn").toString();

        LocalDateTime createdOn = LocalDateTime.parse(createdOnStr.substring(0, createdOnStr.indexOf("+")));
        LocalDateTime updatedOn = LocalDateTime.parse(updatedOnStr.substring(0, createdOnStr.indexOf("+")));

        assertTrue(updatedOn.isAfter(createdOn), "UpdatedOn same as createdOn");
        String presentTimeStamp = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("yyyy-MM-dd'T'");
        assertTrue(updatedOnStr.contains(presentTimeStamp));
    }


    @Then("I verify Application UI created {string} with missing information API")
    public void iVerifyApplicationUICreatedWithMissingInformationAPI(String valueToCheck) {
        switch (valueToCheck) {
            case "MI ID":
                missingInfoID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].missingInfoItemId"));
                String miId = CRM_MissingInformationTabStepDef.savedMIValues.get().get(0);
                assertEquals(miId, missingInfoID, "Mismatch in UI and API response MI ID#");
                break;
            case "THREE MI ID":
                List<String> actualIdList = new ArrayList<>();
                List<String> expectedIdList = new ArrayList<>(CRM_MissingInformationTabStepDef.savedMIValues.get());
                for (int i = 0; i < 3; i++) {
                    actualIdList.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[" + i + "].missingInfoItemId"));
                }
                Collections.sort(actualIdList);
                Collections.sort(expectedIdList);
                assertEquals(actualIdList, expectedIdList, "Mis match in UI MI ID# and GET MI ID#");
                for (int i = 0; i < 3; i++) {
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[" + i + "].entityRecordType"), "Application Consumer", "Expected Application Consumer as Host Entity Type");
                }
                break;
            default:
                fail("Provided value to check did not match");
        }
        CRM_MissingInformationTabStepDef.savedMIValues.get().clear();
    }

    @And("I initiate update missing information API with following values")
    public void iInitiateUpdateMissingInformationAPIWithFollowingValues(Map<String, String> datatable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/updateMissingInfoAPI.json");
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "COMMENTS":
                    String comments;
                    comments = RandomStringUtils.randomAlphanumeric(36);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("comments", comments);
                    expectedMissingInfoDetails.get().put("comments", comments);
                    break;
                case "COMPLETED BY":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("completedBy", datatable.get("COMPLETED BY"));
                    expectedMissingInfoDetails.get().put("completedBy", datatable.get("COMPLETED BY"));
                    break;
                case "STATUS":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("status", datatable.get("STATUS"));
                    expectedMissingInfoDetails.get().put("status", datatable.get("STATUS"));
                    break;
                case "UPDATED BY":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", datatable.get("UPDATED BY"));
                    expectedMissingInfoDetails.get().put("updatedBy", datatable.get("UPDATED BY"));
                    break;
                default:
                    fail("Entered Key did not match existing keys: " + eachVerifyValue);
            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("requestParams = " + requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseMissingInfoUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateMIfromMIService + String.valueOf(missingInfoID.get()));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPIWithParameter(requestParams.get());
        System.out.println("Status change Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify all the response details of Update Missing Information API response")
    public void iVerifyAllTheResponseDetailsOfUpdateMissingInformationAPIResponseFor() {
        for (String key : expectedMissingInfoDetails.get().keySet()) {
            assertEquals(actualMissingInfoDetails.get().get(key), expectedMissingInfoDetails.get().get(key), "Missing Info Update does not match or exist");
        }
    }

    @Then("I verify missing information has completed on as {string}")
    public void iVerifyMissingInformationHasCompletedOnAs(String set) {
        if (set.equalsIgnoreCase("true")) {
            String completedOn = actualMissingInfoDetails.get().get("completedOn").toString();
            String presentTimeStamp = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("yyyy-MM-dd'T'");
            assertTrue(completedOn.contains(presentTimeStamp));
        } else {
            assertNull(actualMissingInfoDetails.get().get("completedOn"), "Completed On is not null when completed By is null");
            assertNull(actualMissingInfoDetails.get().get("completedBy"), "Completed By is not null");
        }

    }

    @And("I get Missing Info ID from the API response")
    public void getMissingInfoIDFromAPIResponse() {
        try {
            missingInfoID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("missingInfoItems.data[0].missingInfoItemId"));
            missingInfoID.set(missingInfoID.get().replace("[", ""));
            missingInfoID.set(missingInfoID.get().replace("]", ""));
        } catch (NullPointerException e) {
            List<Map<String, Object>> missingInfoDataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data");
            Map<String, Object> missingInfoMap = missingInfoDataList.get(0);
            missingInfoID.set(missingInfoMap.get("missingInfoItemId").toString());
        }
        System.out.println("Missing info id is:" + missingInfoID.get());
    }

    @And("I retrieve Missing Info Id from create MI response")
    public void iGetMissingInfoId(){
        missingInfoID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].missingInfoItemId").toString());
        System.out.println(missingInfoID.get());
    }
    @Then("I verify the updated fields for the {string} API")
    public void iVerifyTheUpdatedFieldsForTheAPI(String endPoint) {
        switch (endPoint) {
            case "Retrieve MI in the Application":
                List<Map<String, Object>> missingInfoDataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data");
                for (int i = 0; i < missingInfoDataList.size(); i++) {
                    Map<String, Object> missingInfoMap = missingInfoDataList.get(i);
                    assertEquals(missingInfoMap.get("updatedBy"), expectedMissingInfoDetails.get().get("updatedBy"), "updatedBy isn't verified for " + endPoint);
                    assertEquals(missingInfoMap.get("completedBy"), expectedMissingInfoDetails.get().get("completedBy"), "completedBy isn't verified for " + endPoint);
                    assertEquals(missingInfoMap.get("comments"), expectedMissingInfoDetails.get().get("comments"), "Comments isn't verified for " + endPoint);
                    assertTrue(EventsUtilities.isValidDate(missingInfoMap.get("completedOn").toString()), "completedOn isn't verified for " + endPoint);
                    assertEquals(missingInfoMap.get("status"), expectedMissingInfoDetails.get().get("status"), "status isn't verified for " + endPoint);
                    System.out.println("Updated Fields verified for " + endPoint);
                }
                break;
            case "Search Missing Information":
                JsonObject missingInfoResponse = (JsonObject) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonArray.get(0);
                System.out.println(missingInfoResponse);
                assertEquals(missingInfoResponse.get("updatedBy").getAsString(), expectedMissingInfoDetails.get().get("updatedBy"), "updatedBy isn't verified for " + endPoint);
                assertEquals(missingInfoResponse.get("completedBy").getAsString(), expectedMissingInfoDetails.get().get("completedBy").toString(), "completedBy isn't verified for " + endPoint);
                assertEquals(missingInfoResponse.get("comments").getAsString(), expectedMissingInfoDetails.get().get("comments"), "Comments isn't verified for " + endPoint);
                String completedOn = actualMissingInfoDetails.get().get("completedOn").toString();
                String presentTimeStamp = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("yyyy-MM-dd'T'");
                assertTrue(completedOn.contains(presentTimeStamp));
                assertEquals(missingInfoResponse.get("status").getAsString(), expectedMissingInfoDetails.get().get("status"), "status isn't verified for " + endPoint);
                System.out.println("Updated Fields verified for " + endPoint);
                break;
        }
    }

    @And("I initiate retrieve existing missing information with tags api")
    public void iInitiateRetrieveExistingMissingInformationWithTagsApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseMissingInfoUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchMIFromMIService);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/miSearchMissingInfo.json");
        appRequestPayload.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(appRequestPayload.get());
    }

    @And("I post retrieve existing missing information with tags api")
    public void iPostRetrieveExistingMissingInformationWithTagsApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload.get());
    }

    @And("I verify the details of the retrieve existing missing information api")
    public void iVerifyTheDetailsOfTheRetrieveExistingMissingInformationApi(List<String> statusList) {
        List<Map<String, Object>> missingInfoItemsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get();
        assertTrue(missingInfoItemsList.size() == statusList.size(), "Retrieve MI API size is mismatched, Found: "+missingInfoItemsList.size()+" but found: "+statusList.size());
        for (int i = 0; i < missingInfoItemsList.size(); i++) {
            Map<String, Object> missingInfoMap = missingInfoItemsList.get(i);
            assertTrue(missingInfoMap.get("missingInfoItemId").toString().chars().allMatch(Character::isDigit), "Missing Info Item ID isn't verified for retrieve MI API");
            assertEquals(missingInfoMap.get("entityId").toString(), String.valueOf(entityId.get()), "Entity ID  isn't verified for retrieve MI API");
            assertEquals(missingInfoMap.get("entityRecordType"), keysToOverride.get().get("entityRecordType"), "entityRecordType isn't verified for retrieve MI API");
            assertEquals(missingInfoMap.get("status"), statusList.get(i), "status isn't verified for retrieve MI API");
            System.out.println("Updated Fields verified for Retrieve Missing Information API");
        }
    }

    @And("I verify Missing Information Flag is set")
    public void iVerifyMissingInformationFlagIsSet(Map<String, String> datatable) {
        List<Map<String, Object>> applicationConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers");
        int k;
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "APPLICATION MISSING INFORMATION FLAG":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.appMissingInfoFlag").toString(), datatable.get("APPLICATION MISSING INFORMATION FLAG"), "Application Missing Info Flag is not correct");
                    break;
                case "PI MISSING INFORMATION FLAG":
                    for (k = 0; k < applicationConsumers.size(); k++) {
                        if ("Primary Individual".equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerRoleType"))) {
                            System.out.println("PM");
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].appMemberMissingInfoFlag").toString(), datatable.get("PI MISSING INFORMATION FLAG"), "Primary Individual Missing Information Flag is not correct");
                        }
                    }
                    break;
                case "AM MISSING INFORMATION FLAG":
                    for (k = 0; k < applicationConsumers.size(); k++) {
                        if ("Application Member".equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].consumerRoleType"))) {
                            System.out.println("AM");
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.applicationConsumers[" + k + "].appMemberMissingInfoFlag").toString(), datatable.get("AM MISSING INFORMATION FLAG"), "Application Member Missing Information Flag is not correct");
                        }
                    }
                    break;
            }
        }
    }

    @And("I verify the number of missing information returned in the response are {string}")
    public void iVerifyMissingInformationAareReturnedInTheResponse(String number) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data").size() == Integer.parseInt(number), "Missing Information count does not match. Expected: " + number + " But Found: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data").size());
    }

    @And("I initiate and POST Missing information initiate with {string}")
    public void iInitiateAndPOSTMissingInformationInitiateWith(String appType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        switch (appType) {
            case "AUTO CREATED":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(applicationIdAPI.get() + initiateMIService);
                break;
            default:
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(appType + initiateMIService);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/missingInfoInitiate.json");
        appRequestPayload.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(appRequestPayload.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Mismatch in expected 200 status code");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success", "Mismatch in expected success status");
    }

    @Then("I verify response for auto created Missing Info initiate for Application {string}")
    public void iVerifyResponseForAutoCreatedMissingInfoInitiateForApplication(String missingInfoType) {
        Map<String, Object> missingInfoMap;
        List<Map<String, Object>> missingInfoDataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data");
        List<Map<String, Object>> missingInformationDependentsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.missingInformationDependents[0]");
        String expectedConsumerFullName;

        switch (missingInfoType) {
            case "SIGNATURE DATE":
                for (int i = 0; i < missingInfoDataList.size(); i++) {
                    missingInfoMap = missingInfoDataList.get(i);
                    missingInfoLevelVerification(missingInfoMap);
                    expectedConsumerFullName = piFirstName.get() + " " + piMiddleName.get() + " " + piLastName.get() + " " + piSuffix.get();
                    assertEquals(missingInfoMap.get("consumerFullName"), expectedConsumerFullName, "Mismatch in expected Primary Individual consumerFullName for Missing info item");
                    assertEquals(missingInfoMap.get("attributeName"), "Application Signed", "Attribute Name isn't verified for Retrieve Missing Info Application API");
                    assertEquals(missingInfoMap.get("entityId").toString(), applicationIdAPI.get(), "Entity Id is not digit for Retrieve Missing Info Application API");
                    assertEquals(missingInfoMap.get("entityRecordType"), "Application", "EntityRecord Type isn't verified for Retrieve Missing Info Application API");
                    for (int j = 0; j < missingInformationDependentsList.size(); j++) {
                        assertEquals(missingInformationDependentsList.get(j).get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInformationDependentsList.get(j).get("dependentRecordId"))), "Mismatch in expected Dependent Full Name");
                        assertEquals(missingInformationDependentsList.get(j).get("dependentType"), "APPLICATION CONSUMER", "dependentType isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("createdBy"), "ATS Service", "CreatedBy isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("updatedBy"), "ATS Service", "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("createdOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                    }
                }
                break;
            case "MI DOB":
                for (int i = 0; i < missingInfoDataList.size(); i++) {
                    missingInfoMap = missingInfoDataList.get(i);
                    missingInfoLevelVerification(missingInfoMap);
                    assertTrue(APIATSSendEventAndCreateLinksController.appconsumerIdList.get().contains(missingInfoMap.get("entityId").toString()), "Did not find Expected entityId for MI DOB");
                    assertEquals(missingInfoMap.get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInfoMap.get("entityId"))));
                    assertEquals(missingInfoMap.get("attributeName"), "DOB", "Attribute Name isn't verified for Retrieve Missing Info Application API");
                    assertEquals(missingInfoMap.get("entityRecordType"), "Application Consumer", "EntityRecord Type isn't verified for Retrieve Missing Info Application API");
                    for (int j = 0; j < missingInformationDependentsList.size(); j++) {
                        assertEquals(missingInformationDependentsList.get(j).get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInformationDependentsList.get(j).get("dependentRecordId"))), "Mismatch in expected Dependent Full Name");
                        assertEquals(missingInformationDependentsList.get(j).get("dependentType"), "APPLICATION CONSUMER", "dependentType isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("createdBy"), "ATS Service", "CreatedBy isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("updatedBy"), "ATS Service", "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("createdOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                    }
                }
                break;
            case "MI SSN":
                for (int i = 0; i < missingInfoDataList.size(); i++) {
                    missingInfoMap = missingInfoDataList.get(i);
                    missingInfoLevelVerification(missingInfoMap);
                    assertTrue(APIATSSendEventAndCreateLinksController.appconsumerIdList.get().contains(missingInfoMap.get("entityId").toString()), "Did not find Expected entityId for MI DOB");
                    assertEquals(missingInfoMap.get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInfoMap.get("entityId"))));
                    assertEquals(missingInfoMap.get("attributeName"), "SSN", "Attribute Name isn't verified for Retrieve Missing Info Application API");
                    assertEquals(missingInfoMap.get("entityRecordType"), "Application Consumer", "EntityRecord Type isn't verified for Retrieve Missing Info Application API");
                    for (int j = 0; j < missingInformationDependentsList.size(); j++) {
                        assertEquals(missingInformationDependentsList.get(j).get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInformationDependentsList.get(j).get("dependentRecordId"))), "Mismatch in expected Dependent Full Name");
                        assertEquals(missingInformationDependentsList.get(j).get("dependentType"), "APPLICATION CONSUMER", "dependentType isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("createdBy"), "ATS Service", "CreatedBy isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("updatedBy"), "ATS Service", "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("createdOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                    }
                }
                break;
            case "FIRST NAME":
                for (int i = 0; i < missingInfoDataList.size(); i++) {
                    missingInfoMap = missingInfoDataList.get(i);
                    missingInfoLevelVerification(missingInfoMap);
                    assertTrue(APIATSSendEventAndCreateLinksController.appconsumerIdList.get().contains(missingInfoMap.get("entityId").toString()), "Did not find Expected entityId for MI DOB");
                    assertEquals(missingInfoMap.get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInfoMap.get("entityId"))));
                    assertEquals(missingInfoMap.get("attributeName"), "First Name", "Attribute Name isn't verified for Retrieve Missing Info Application API");
                    assertEquals(missingInfoMap.get("entityRecordType"), "Application Consumer", "EntityRecord Type isn't verified for Retrieve Missing Info Application API");
                    for (int j = 0; j < missingInformationDependentsList.size(); j++) {
                        assertEquals(missingInformationDependentsList.get(j).get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInformationDependentsList.get(j).get("dependentRecordId"))), "Mismatch in expected Dependent Full Name");
                        assertEquals(missingInformationDependentsList.get(j).get("dependentType"), "APPLICATION CONSUMER", "dependentType isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("createdBy"), "ATS Service", "CreatedBy isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("updatedBy"), "ATS Service", "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("createdOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                    }
                }
                break;
            case "LAST NAME":
                for (int i = 0; i < missingInfoDataList.size(); i++) {
                    missingInfoMap = missingInfoDataList.get(i);
                    missingInfoLevelVerification(missingInfoMap);
                    assertTrue(APIATSSendEventAndCreateLinksController.appconsumerIdList.get().contains(missingInfoMap.get("entityId").toString()), "Did not find Expected entityId for MI DOB");
                    assertEquals(missingInfoMap.get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInfoMap.get("entityId"))));
                    assertEquals(missingInfoMap.get("attributeName"), "Last Name", "Attribute Name isn't verified for Retrieve Missing Info Application API");
                    assertEquals(missingInfoMap.get("entityRecordType"), "Application Consumer", "EntityRecord Type isn't verified for Retrieve Missing Info Application API");
                    for (int j = 0; j < missingInformationDependentsList.size(); j++) {
                        assertEquals(missingInformationDependentsList.get(j).get("consumerFullName"), APIATSSendEventAndCreateLinksController.idFullNameList.get().get(String.valueOf(missingInformationDependentsList.get(j).get("dependentRecordId"))), "Mismatch in expected Dependent Full Name");
                        assertEquals(missingInformationDependentsList.get(j).get("dependentType"), "APPLICATION CONSUMER", "dependentType isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("createdBy"), "ATS Service", "CreatedBy isn't valid for Retrieve Missing Info Application API");
                        assertEquals(missingInformationDependentsList.get(j).get("updatedBy"), "ATS Service", "UpdatedBy isn't valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("createdOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                        assertTrue(EventsUtilities.isValidDate(missingInformationDependentsList.get(j).get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
                    }
                }
                break;
            default:
                fail("Provided verify case did not match");
        }
    }

    public void missingInfoLevelVerification(Map<String, Object> missingInfoMap){
        assertTrue(missingInfoMap.get("missingInfoItemId").toString().chars().allMatch(Character::isDigit), "Missing Info Item ID is not digit for Retrieve Missing Info Application API");
        assertEquals(missingInfoMap.get("recordClass"), "Data", "Record Class isn't verified for Retrieve Missing Info Application API");
        assertEquals(missingInfoMap.get("updatedBy").toString(), "ATS Service", "UpdatedBy isn't valid for Retrieve Missing Info Application API");
        assertEquals(missingInfoMap.get("createdBy").toString(), "ATS Service", "CreatedBy isn't valid for Retrieve Missing Info Application API");
        assertTrue(missingInfoMap.get("attributeValue").toString().chars().allMatch(Character::isAlphabetic), "Attribute Value isn't verified for Retrieve Missing Info Application API");
        assertEquals(missingInfoMap.get("status"), "PENDING", "Status isn't verified for Retrieve Missing Info Application API");
        assertNull(missingInfoMap.get("comments"), "Comments isn't verified for Retrieve Missing Info Application API");
        assertTrue(EventsUtilities.isValidDate(missingInfoMap.get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
        assertTrue(EventsUtilities.isValidDate(missingInfoMap.get("createdOn").toString()), "CreatedOn date time field IS NOT valid for Retrieve Missing Info Application API");
    }

    @And("I get Missing Info error message from the API response")
    public void getMissingInfoErrorMessageFromAPIResponse(List<String> errorMessages) {
        System.out.println("View Summary response is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        String actualErrorMessage = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message");
        assertEquals(errorMessages.get(0),actualErrorMessage, " Error Message does not match");
    }

    @And ("I update the entityId to ApplicationId for Create Missing Info request")
    public void iUpdateEntityIdToApplicationId() {
        missingInfoRequestPayload.get().getAsJsonObject().remove("entityId");
        missingInfoRequestPayload.get().getAsJsonObject().addProperty("entityId",applicationIdAPI.get());
    }

    @And("I initiate POST missing info link api for ATS")
    public void iInitiateAndPostMILink() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApiLinksUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(postMiLinkEndpoint);
    }

    @And("I POST missing info link request")
    public void iPostMILink(){
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/missingInfoLink.json");
        appRequestPayload.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        appRequestPayload.get().getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId",applicationIdAPI.get());
        appRequestPayload.get().getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId",missingInfoID.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(appRequestPayload.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Mismatch in expected 200 status code");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success", "Mismatch in expected success status");
    }


    @And("I initiate GET missing info links api for ATS using {string} reference type")
    public void iInitiateGetMILinks(String referenceType){
        if (referenceType.equalsIgnoreCase("APPLICATION")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApiLinksUrl);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getMiLinkByApplicationEndpoint+ applicationIdAPI.get() + pageSizeValueEndPoint);
        }
        if (referenceType.equalsIgnoreCase("MISSING-INFO")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApiLinksUrl);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getMiLinkByMIEndpoint+ missingInfoID.get() + pageSizeValueEndPoint);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        System.out.println("View response is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I retrieve and verify missing info for external reference type {string} and internal reference type {string} as {string}")
    public void iRetrieveMILinksInfo(String externalRefType, String internalRefType,String condition){
        List<Map<String, Object>> externalLinkDetailsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("externalLinkDetails.content");
        for (int i = 0; i < externalLinkDetailsList.size(); i++) {
            String actualExtRefType = externalLinkDetailsList.get(i).get("externalRefType").toString();
            String actualIntRefType = externalLinkDetailsList.get(i).get("internalRefType").toString();
            if(externalRefType.equalsIgnoreCase(actualExtRefType) && internalRefType.equalsIgnoreCase(actualIntRefType) ){
                externalLinkDetailsMap.set(( Map<String,Object>) externalLinkDetailsList.get(i));

            }
        }
        System.out.println(externalLinkDetailsMap);
        if ("DO NOT EXIST".equalsIgnoreCase(condition)){
            Assert.assertTrue(externalLinkDetailsMap.get().size() ==0 );
        }else if("APPLICATION".equalsIgnoreCase(externalLinkDetailsMap.get().get("internalRefType").toString())  && "EXPECTED".equalsIgnoreCase(condition) ) {
            Assert.assertEquals(externalLinkDetailsMap.get().get("internalId").toString(), applicationIdAPI.get());
            Assert.assertEquals(externalLinkDetailsMap.get().get("id").toString(), missingInfoID.get());
        } else if("MISSING_INFO".equalsIgnoreCase(externalLinkDetailsMap.get().get("internalRefType").toString()) && "EXPECTED".equalsIgnoreCase(condition) ) {
            Assert.assertEquals(externalLinkDetailsMap.get().get("internalId").toString(), missingInfoID.get());
            Assert.assertEquals(externalLinkDetailsMap.get().get("id").toString(), applicationIdAPI.get());
        }
    }

}