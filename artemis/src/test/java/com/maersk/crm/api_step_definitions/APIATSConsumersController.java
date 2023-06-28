package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.*;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.*;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.*;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.createdConsumerDetails;
import static org.testng.Assert.*;


public class APIATSConsumersController<requestbody> extends CRMUtilities implements ApiBase {
    private String baseConsumer = ConfigurationReader.getProperty("apiATSCaseConsumer");
    private String consumerMatchingEndpoint = "/app/crm/consumers/matching";
    private String caseMemberEndpoint = "/app/crm/casemember/";
    private String consumerMatchingEndpointLinkInfoTrue = "?linksInfo=true";
    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonObject> appRequestPayload = ThreadLocal.withInitial(JsonObject::new);
    private final String applicationDataService = ConfigurationReader.getProperty("apiApplicationDataServices");
    private final String caseConsumerActionAPI = "/link/case";
    private final String matchingConsumerLinksAPI = "/matchingConsumerLinks";
    private final ThreadLocal <JsonObject> personObject = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonArray> matchingRequest = ThreadLocal.withInitial(JsonArray::new);
    private final ThreadLocal <String> appConsumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> caseConsumerId = ThreadLocal.withInitial(String::new);

    public static final ThreadLocal <List<Integer>> applicationConsumerIdList = ThreadLocal.withInitial(ArrayList::new);

    @When("I initiate and run GET consumers on a case")
    public void initiateGetConsumersOnCase() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumer);
        String caseId = World.generalSave.get().get("CASEID").toString();
        if(caseId !=null) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseMemberEndpoint + caseId);
        }
        waitFor(2);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("GET View API Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("success"));
    }

    private void initiatePersonMatchingObject(List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/consumersMatching.json");
        personObject.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject());
        JsonObject externalConsumerId = new JsonObject();
        for (String key : keysToOverride.keySet()) {
            switch (key) {
                case "externalConsumerId":
                    externalConsumerId.addProperty(key, keysToOverride.get(key));
                    break;
                case "externalConsumerIdType":
                    externalConsumerId.addProperty(key, keysToOverride.get(key));
                    break;
                case "AUTO externalConsumerId":
                    externalConsumerId.addProperty("externalConsumerId", consumerExternalId.get() );
                    break;
                case "Latest ApplicationId":
                    externalConsumerId.addProperty("applicationId", Integer.parseInt(applicationIdAPI.get()));
                    break;
                default:
                    personObject.get().addProperty(key, keysToOverride.get(key));
            }
        }
        personObject.get().getAsJsonArray("externalConsumerIdentifications").add(externalConsumerId);
        for (String key : keysToOverride.keySet()) {
            switch (keysToOverride.get(key)) {
                case "DUPLICATE FIRST":
                    personObject.get().addProperty(key, duplicateFirstname.get());
                    break;
                case "DUPLICATE LAST":
                    personObject.get().addProperty(key, duplicateLastName.get());
                    break;
                case "DUPLICATE DOB":
                    personObject.get().addProperty(key, duplicateDOB.get());
                    break;
                case "DUPLICATE SSN":
                    personObject.get().addProperty(key, duplicateSSN.get());
                    break;
            }
        }


    }

    @And("I initiate consumers for matching API")
    public void iInitiateConsumersForMatchingAPI(List<Map<String, String>> data) {
        initiatePersonMatchingObject(data);
        matchingRequest.get().add(personObject.get());
    }

    @And("I send POST consumers matching API for {string} response")
    public void iSendPOSTConsumersMatchingAPIForResponse(String requestType) {
        if ("Successful".equals(requestType)) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumer);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(consumerMatchingEndpoint);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(matchingRequest.get());
        } else if ("Unsuccessful".equals(requestType)) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(matchingRequest.toString());
        } else if ("LinksInfoTrue".equals(requestType)){
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumer);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(consumerMatchingEndpoint+consumerMatchingEndpointLinkInfoTrue);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(matchingRequest.get());
        }
    }

    @Then("I verify the response from a successful {string} consumer matching API")
    public void iVerifyTheResponseFromASuccessfulConsumerMatchingAPI(String verifyType) {
        waitFor(5);
        switch (verifyType) {
            case "Case Consumer SSN":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                // CaseID
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseId").toString(), "47837", "Expected CaseId mismatch");
                // matchingCases -> caseIdentificationNumber
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber[0].externalCaseId").toString(), "1112april", "Expected externalCaseId mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber[0].identificationNumberType").toString(), "CHIP", "Expected identificationNumberType mismatch");
                // matchingCases -> caseConsumers
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerLastName").toString(), "Month", "Expected consumerLastName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerFirstName").toString(), "April", "Expected consumerFirstName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerMiddleName").toString(), "B", "Expected consumerMiddleName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerSsn").toString(), "555551112", "Expected consumerSsn mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerRole").toString(), "Primary Individual", "Expected consumerRole mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerId").toString(), "93914", "Expected consumerId mismatch");
                // matchingCases -> caseConsumers -> matchingIncomingPersons
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].matchingIncomingPersons[0].personId").toString(), "1111", "Expected personId mismatch");
                // matchingCases -> caseConsumers -> consumerIdentificationNumber
                List<Map<String, String>> consumerSSNIdentificationNumber = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerIdentificationNumber");
                for (Map<String, String> eachId : consumerSSNIdentificationNumber) {
                    if ("CHIP".equals(eachId.get("identificationNumberType"))) {
                        assertEquals(eachId.get("externalConsumerId"), "1112aprilC", "Expected externalConsumerId mismatch");
                    } else if ("Medicaid".equals(eachId.get("identificationNumberType"))) {
                        assertEquals(eachId.get("externalConsumerId"), "1112aprilM", "Expected externalConsumerId mismatch");
                    }
                }
                // matchingCases -> caseConsumers -> consumerContacts -> addresses
                List<Map<String, String>> addresses = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerContacts.addresses");
                verifyCaseConsumerAddressVerify(addresses);
                break;
            case "Non Case Consumer SSN":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                verifyNonCaseConsumerResponse();
                break;
            case "Case Consumer External Id":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                // CaseID
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseId").toString(), "47838", "Expected CaseId mismatch");
                // matchingCases -> caseIdentificationNumber
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber[0].externalCaseId").toString(), "1113february", "Expected externalCaseId mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber[0].identificationNumberType").toString(), "CHIP", "Expected identificationNumberType mismatch");
                // matchingCases -> caseConsumers
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerLastName").toString(), "Month", "Expected consumerLastName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerFirstName").toString(), "February", "Expected consumerFirstName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerMiddleName").toString(), "C", "Expected consumerMiddleName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerSsn").toString(), "555551113", "Expected consumerSsn mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerRole").toString(), "Primary Individual", "Expected consumerRole mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerId").toString(), "93915", "Expected consumerId mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerDateOfBirth").toString(), "2000-09-04", "Expected consumerDateOfBirth mismatch");
                // matchingCases -> caseConsumers -> matchingIncomingPersons
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].matchingIncomingPersons[0].personId").toString(), "1111", "Expected personId mismatch");
                // matchingCases -> caseConsumers -> consumerIdentificationNumber
                List<Map<String, String>> caseConsumerExId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerIdentificationNumber");
                for (Map<String, String> eachId : caseConsumerExId) {
                    if ("CHIP".equals(eachId.get("identificationNumberType"))) {
                        assertEquals(eachId.get("externalConsumerId"), "1113februaryC", "Expected externalConsumerId mismatch");
                    } else if ("Medicaid".equals(eachId.get("identificationNumberType"))) {
                        assertEquals(eachId.get("externalConsumerId"), "1113februaryM", "Expected externalConsumerId mismatch");
                    }
                }
                // matchingCases -> caseConsumers -> consumerContacts -> addresses
                List<Map<String, String>> caseConsumerExIdaddresses = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerContacts.addresses");
                verifyCaseConsumerAddressVerify(caseConsumerExIdaddresses);
                break;
            case "Non Case Consumer External Id":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                verifyNonCaseConsumerResponse();
                break;
            case "Case Consumer First Last Name":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                // CaseID
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseId").toString(), "47837", "Expected CaseId mismatch");
                // matchingCases -> caseIdentificationNumber
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber[0].externalCaseId").toString(), "1112april", "Expected externalCaseId mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber[0].identificationNumberType").toString(), "CHIP", "Expected identificationNumberType mismatch");
                // matchingCases -> caseConsumers
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerLastName").toString(), "Month", "Expected consumerLastName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerFirstName").toString(), "April", "Expected consumerFirstName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerMiddleName").toString(), "B", "Expected consumerMiddleName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerSsn").toString(), "555551112", "Expected consumerSsn mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerRole").toString(), "Primary Individual", "Expected consumerRole mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerId").toString(), "93914", "Expected consumerId mismatch");
                // matchingCases -> caseConsumers -> matchingIncomingPersons
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].matchingIncomingPersons[0].personId").toString(), "1111", "Expected personId mismatch");
                // matchingCases -> caseConsumers -> consumerIdentificationNumber
                List<Map<String, String>> consumerFLnameRuleID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerIdentificationNumber");
                for (Map<String, String> eachId : consumerFLnameRuleID) {
                    if ("CHIP".equals(eachId.get("identificationNumberType"))) {
                        assertEquals(eachId.get("externalConsumerId"), "1112aprilC", "Expected externalConsumerId mismatch");
                    } else if ("Medicaid".equals(eachId.get("identificationNumberType"))) {
                        assertEquals(eachId.get("externalConsumerId"), "1112aprilM", "Expected externalConsumerId mismatch");
                    }
                }
                // matchingCases -> caseConsumers -> consumerContacts -> addresses
                List<Map<String, String>> fnRuleAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerContacts.addresses");
                verifyCaseConsumerAddressVerify(fnRuleAddress);
                break;
            case "Non Case Consumer First Last Name":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                verifyNonCaseConsumerResponse();
                break;
            case "Case Consumer First Last DoB":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                // CaseID
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseId").toString(), "47839", "Expected CaseId mismatch");
                // matchingCases -> caseIdentificationNumber
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber[0].externalCaseId").toString(), "1115january", "Expected externalCaseId mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber[0].identificationNumberType").toString(), "CHIP", "Expected identificationNumberType mismatch");
                // matchingCases -> caseConsumers
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerLastName").toString(), "Month", "Expected consumerLastName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerFirstName").toString(), "January", "Expected consumerFirstName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerMiddleName").toString(), "D", "Expected consumerMiddleName mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerSsn").toString(), "555551114", "Expected consumerSsn mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerRole").toString(), "Primary Individual", "Expected consumerRole mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerId").toString(), "93916", "Expected consumerId mismatch");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerDateOfBirth").toString(), "1981-01-01", "Expected consumerDateOfBirth mismatch");
                // matchingCases -> caseConsumers -> matchingIncomingPersons
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].matchingIncomingPersons[0].personId").toString(), "1111", "Expected personId mismatch");
                // matchingCases -> caseConsumers -> consumerIdentificationNumber
                List<Map<String, String>> consumerFLDnameRuleID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerIdentificationNumber");
                for (Map<String, String> eachId : consumerFLDnameRuleID) {
                    if ("CHIP".equals(eachId.get("identificationNumberType"))) {
                        assertEquals(eachId.get("externalConsumerId"), "1114januaryC", "Expected externalConsumerId mismatch");
                    } else if ("Medicaid".equals(eachId.get("identificationNumberType"))) {
                        assertEquals(eachId.get("externalConsumerId"), "1114januaryM", "Expected externalConsumerId mismatch");
                    }
                }
                // matchingCases -> caseConsumers -> consumerContacts -> addresses
                List<Map<String, String>> firstLastDoBRuleAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerContacts.addresses");
                verifyCaseConsumerAddressVerify(firstLastDoBRuleAddress);
                break;
            case "Non Case Consumer First Last DoB":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                verifyNonCaseConsumerResponse();
                break;
            case "No Duplicate Cases":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                // CaseID
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseId").toString(), "47839", "Expected CaseId mismatch");
                break;
            case "Multiple Matches for Single Consumer":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size(), 1, "Expected one Matching result but found " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases").size() + " matches");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseId").toString(), "47838", "Expected CaseId mismatch");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].matchingIncomingPersons.personId").contains("1111"), "No match in expected PersonId");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].matchingIncomingPersons.personId").contains("2222"), "No match in expected PersonId");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].matchingIncomingPersons.personId").contains("3333"), "No match in expected PersonId");
                break;
            case "Success Response with minimal":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Mismatch in Expected successful status code response from minimal rule sent");
                break;
            case "Invalid Request Response":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 400, "Mismatch in Expected Unsuccessful status code response from minimal rule sent");
                break;
            case "Multiple Matches for Multiple Consumer in a case":
                List<Map<String, String>> consumersInCase = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers");
                for (int i = 0; i < consumersInCase.size(); i++) {
                    if ("March".equals(consumersInCase.get(i).get("consumerFirstName"))) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[" + i + "].matchingIncomingPersons[0].personId").toString(), "1111", "Expected personId match for 1111");
                    } else if ("August".equals(consumersInCase.get(i).get("consumerFirstName"))) {
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[" + i + "].matchingIncomingPersons[0].personId").toString(), "2222", "Expected personId match for 2222");
                    } else if ("July".equals(consumersInCase.get(i).get("consumerFirstName"))) {
                        assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[" + i + "].matchingIncomingPersons[0].personId"), "Expected personId match to be null");
                    }
                }
                break;
            case "Multiple Matches for Multiple case/non-case consumers":
                List<Map<String, String>> multiConsumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases");
                for (int i = 0; i < multiConsumers.size(); i++) {
                    if ("47883".equals(String.valueOf(multiConsumers.get(i).get("caseId")))) {
                        // first matching consumer in case one
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[" + i + "].caseConsumers[0].consumerFirstName"), "January");
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[" + i + "].caseConsumers[0].consumerLastName"), "Month");
                        // second matching consumer in case one
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[" + i + "].caseConsumers[1].consumerFirstName"), "January");
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[" + i + "].caseConsumers[1].consumerLastName"), "Month");
                    } else if ("47839".equals(String.valueOf(multiConsumers.get(i).get("caseId")))) {
                        // first matching consumer in case two
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[" + i + "].caseConsumers[0].consumerFirstName"), "January");
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[" + i + "].caseConsumers[0].consumerLastName"), "Month");
                    }
                }
                break;
            case "LINKS INFO TRUE FLD RULE":
                verifyLinksTrueInfoResponse();
                break;
            case "LINKS INFO TRUE SSN RULE":
                verifyLinksTrueInfoResponse();
                break;
            case "LINKS INFO TRUE EXID RULE":
                verifyLinksTrueInfoResponse();
                break;
            default:
                fail("Mismatch in Case Consumer Verification case type");
        }
    }

    private void verifyLinksTrueInfoResponse(){
        appConsumerId.set(String.valueOf( consumerIdList.get().get(0)));
        caseConsumerId.set(String.valueOf( searchConsumerIdList.get().get(0)));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCaseConsumers[0].consumerId").toString(), caseConsumerId);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCaseConsumers[0].links[0].externalRefId").toString(), appConsumerId);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCaseConsumers[0].links[0].externalRefType").toString(), "APPLICATION_CONSUMER");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint();
    }

    private void verifyNonCaseConsumerResponse() {
        // caseId/caseIdentificationNumber
        Assert.assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseId"), "Expected CaseId mismatch");
        Assert.assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseIdentificationNumber"), "Expected caseIdentificationNumber mismatch");
        // matchingCases -> caseConsumers
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerLastName").toString(), "Month", "Expected consumerLastName mismatch");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerFirstName").toString(), "December", "Expected consumerFirstName mismatch");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerMiddleName").toString(), "H", "Expected consumerMiddleName mismatch");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerSsn").toString(), "555551118", "Expected consumerSsn mismatch");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerDateOfBirth").toString(), "2000-01-02", "Expected consumerDateOfBirth mismatch");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].consumerId").toString(), "93972", "Expected consumerId mismatch");
        // matchingCases -> caseConsumers -> consumerIdentificationNumber
        List<Map<String, String>> nonCaseSSNconsumerIdentificationNumber = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("matchingCases[0].caseConsumers[0].consumerIdentificationNumber");
        for (Map<String, String> eachId : nonCaseSSNconsumerIdentificationNumber) {
            if ("MEDICAID".equals(eachId.get("identificationNumberType"))) {
                assertEquals(eachId.get("externalConsumerId"), "1118decemberM", "Expected externalConsumerId mismatch");
            }
        }
        // matchingCases -> caseConsumers -> matchingIncomingPersons
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("matchingCases[0].caseConsumers[0].matchingIncomingPersons[0].personId").toString(), "1111", "Expected personId mismatch");
    }

    private void verifyCaseConsumerAddressVerify(List<Map<String, String>> addressData) {
        for (Map<String, String> each : addressData) {
            if ("Physical".equals(each.get("addressType"))) {
                assertEquals(each.get("addressStreet1"), "987 third first st", "Expected addressStreet1 mismatch");
                assertEquals(each.get("addressCity"), "Preble", "Expected addressCity mismatch");
                assertEquals(each.get("addressState"), "IN", "Expected addressState mismatch");
                assertEquals(each.get("addressZip"), "46782", "Expected addressZip mismatch");
                assertEquals(each.get("addressZipFour"), "4345", "Expected addressZipFour mismatch");
                assertEquals(each.get("addressCounty"), "Adams", "Expected addressCounty mismatch");
            } else if ("Mailing".equals(each.get("addressType"))) {
                assertEquals(each.get("addressStreet1"), "34 third Second address St", "Expected addressStreet1 mismatch");
                assertEquals(each.get("addressCity"), "Preble", "Expected addressCity mismatch");
                assertEquals(each.get("addressState"), "IN", "Expected addressState mismatch");
                assertEquals(each.get("addressZip"), "46782", "Expected addressZip mismatch");
                assertEquals(each.get("addressZipFour"), "4444", "Expected addressZipFour mismatch");
                assertEquals(each.get("addressCounty"), "Adams", "Expected addressCounty mismatch");
            }
        }
    }
    public String getAppConsumerIDByRole(String role) {
        for (String consumerID : createdConsumerDetails.get().keySet()) {
            if (createdConsumerDetails.get().get(consumerID).equalsIgnoreCase(role)) {
                return consumerID;
            }
        }
        Assert.fail("Consumer Role couldn't be found in Created Consumer Details Map");
        return "-1";
    }

    @And("I save consumer Id from application linked to Inbound Doc For Search")
    public void saveApplicationLinkedtoInboundForSearch(){
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator = (JsonPath)  World.generalSave.get().get("ApplicationDetails");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationConsumers"));
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationConsumers[0].applicationConsumerId"));
            String consumerId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationConsumers[0].applicationConsumerId");
            World.generalSave.get().put("ConsumerId",consumerId);
            String consumerRoleType = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationConsumers[0].consumerRoleType");
    }

    @And("I initiate link case api for primary user") // this was created for linking case to an application which is created from Inbound correspondence
    public void iInitiateLinkCasetoApplicationWithOneUser(){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(applicationDataService);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/application/" + inbCreatedAppId + caseConsumerActionAPI);
        requestParams.set(getRequestPayloadFromPath("crm/ats/caseConsumerAction2.json"));
        requestParams.get().getAsJsonArray("applicationConsumers").remove(1);
        requestParams.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().addProperty("applicationConsumerId", World.generalSave.get().get("ConsumerId").toString());
        requestParams.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().addProperty("actionType", "CREATE_NEW");
        requestParams.get().addProperty("caseId", World.generalSave.get().get("caseConsumerCaseId").toString());
    }

    @Then("I initiate link case api {int}")
    public void iInitiateLinkCaseApi(int order, Map<String, String> data) {
        if (order == 0) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(applicationDataService);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/application/" + applicationIdAPI.get() + caseConsumerActionAPI);
            requestParams.set(getRequestPayloadFromPath("crm/ats/caseConsumerAction2.json"));
        }
        for (String field : data.keySet()) {
            if (field.equalsIgnoreCase("ROLE")) {
                String applicationConsumerID = getAppConsumerIDByRole(data.get("ROLE"));
                requestParams.get().getAsJsonArray("applicationConsumers").get(order).getAsJsonObject().addProperty("applicationConsumerId", applicationConsumerID);
            } else if (data.get(field).equals("PRIOR")) {
                requestParams.get().getAsJsonArray("applicationConsumers").get(order).getAsJsonObject().addProperty("matchedConsumerId", applicationConsumerIdList.get().get(order));
            } else if (field.equals("caseId")) {
                if (data.get("caseId").equals("CASE/CONSUMER")) {
                    requestParams.get().addProperty("caseId", APIContactRecordController.caseID.get());
                } else if((data.get("caseId").equals("CASE"))){
                    requestParams.get().addProperty("caseId", World.generalSave.get().get("caseConsumerCaseId").toString());
                } else {
                    requestParams.get().addProperty("caseId", data.get(field));
                }
            } else {
                requestParams.get().getAsJsonArray("applicationConsumers").get(order).getAsJsonObject().addProperty(field, data.get(field));
            }
        }
    }

    @Then("I initiate create case api and create a new case")
    public void iInitiateCreateNewCaseApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumer);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/case");
        requestParams.set(getRequestPayloadFromPath("crm/ats/caseCreation.json"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("Create Case Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        APIContactRecordController.caseID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("cases[0].caseId"));
        World.generalSave.get().put("caseConsumerCaseId",APIContactRecordController.caseID.get());
        System.out.println( APIContactRecordController.caseID.get());
    }


    @And("I run the link case api")
    public void iRunTheLinkCaseApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(requestParams.toString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("success"));
    }

    @Then("I run the link case api to verify expected error message for Inactive Status")
    public void i_run_the_link_case_api_to_verify_expected_error_message_for_Inactive_Status() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(requestParams.toString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("error"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message").toString().contains("Failed to create Consumers. Application ConsumerIds which failed"));
    }

    private JsonObject getRequestPayloadFromPath(String filePath) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile(filePath);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    //need to look at this part
    @Then("I initiate and run the {string} action for link case api")
    public void iInitiateAndRunTheActionForLinkCaseApi(String actionType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(applicationDataService);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/application/" + applicationIdAPI.get() + caseConsumerActionAPI);
        requestParams.set(getRequestPayloadFromPath("crm/ats/caseConsumerAction.json"));
        requestParams.get().getAsJsonObject("auditInfo").addProperty("createdBy", "2492");

        switch (actionType.toUpperCase()) {
            case "CREATE NEW CASE AND NEW CONSUMERS":
                requestParams.get().addProperty("actionType", "CREATE_CASE_AND_CONSUMERS");
                break;
            case "CREATE CONSUMERS":
                requestParams.get().addProperty("caseId", APIContactRecordController.caseID.get());
                requestParams.get().addProperty("actionType", "CREATE_CONSUMERS");
                int i = 0;
                for (String consumerID : createdConsumerDetails.get().keySet()) {
                    requestParams.get().getAsJsonArray("applicationConsumers").get(i).getAsJsonObject().addProperty("applicationConsumerId", Integer.parseInt(consumerID));
                    i++;
                }
                break;
            case "CASE LINK":
                requestParams.get().addProperty("actionType", "CASE_LINK");
                requestParams.get().addProperty("caseId", APIContactRecordController.caseID.get());
                break;
        }
        System.out.println(requestParams.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(requestParams.toString());

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("success"));
        System.out.println("View response is: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

    }

    @And("I capture the case id from link case api")
    public void iCaptureTheCaseIdFromLinkCaseApi() {
        APIContactRecordController.caseID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.caseId"));
    }

    @And("I capture the consumer id from link case api")
    public void iCaptureTheConsumerIdFromLinkCaseApi() {
        applicationConsumerIdList.set(new ArrayList<>());
        for (int i = 0; i < API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.caseMemberResponses.consumers.consumerId").size(); i++) {
            applicationConsumerIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.caseMemberResponses[" + i + "].consumers[0].consumerId"));
        }
    }

    @And("I verify the details of link case api response for {string}")
    public void iVerifyTheDetailsOfLinkCaseApiResponseFor(String memberType, Map<String, String> datatable) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.caseId"), APIContactRecordController.caseID, "Case ID didn't match for Link Case API");
        Assert.assertEquals(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.applicationId")), applicationIdAPI.get(), "Application ID didn't match for Link Case API");
        switch (memberType.toUpperCase()) {
            case "PRIMARY INDIVIDUAL":
                if (isApplicationDuplicate.get()) {
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerFirstName"), duplicateFirstname, memberType + "First name didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerLastName"), duplicateLastName, memberType + " Last name didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerDateOfBirth"), duplicateDOB, memberType + " DOB  didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerSSN"), duplicateSSN, memberType + " SSN  didn't match for Link Case API");
                } else {
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerFirstName"), piFirstName, memberType + "First name didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerLastName"), piLastName, memberType + " Last name didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerDateOfBirth"), piDOB, memberType + " DOB  didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerSSN"), piSSN, memberType + " SSN  didn't match for Link Case API");
                }
                Assert.assertTrue(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.caseMemberResponses[0].consumers[0].consumerId")).chars().allMatch(Character::isDigit), memberType + " Consumer ID isn't digit for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerStatus"), "Active", memberType + " Status  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerType"), "Consumer", memberType + " Consumer Type  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].createdBy"), apiUserID, memberType + " CreatedBy  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].caseConsumers[0].consumerRole"), "Primary Individual", memberType + "Consumer Role Type  didn't match for Link Case API");
                Assert.assertTrue(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.caseMemberResponses[0].consumers[0].consumerId")).chars().allMatch(Character::isDigit), memberType + "Consumer ID isn't digit for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].caseConsumers[0].receiveCorrespondence"), "YES", memberType + "Consumer ID isn't digit for Link Case API");
                for (String verifyField : datatable.keySet()) {
                    if (verifyField.contains("LANGUAGE")) {
                        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].communicationPreferences[0].valuePairIdCommPref").contains("SPOKEN")) {
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].communicationPreferences[0].valuePairIdCommPref"), "SPOKEN_LANGUAGE_" + datatable.get("SPOKEN LANGUAGE"), memberType + "SPOKEN LANGUAGE didn't match for Link Case API");
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].communicationPreferences[1].valuePairIdCommPref"), "WRITTEN_LANGUAGE_" + datatable.get("WRITTEN LANGUAGE"), memberType + "WRITTEN LANGUAGE didn't match for Link Case API");
                        } else {
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].communicationPreferences[1].valuePairIdCommPref"), "SPOKEN_LANGUAGE_" + datatable.get("SPOKEN LANGUAGE"), memberType + "SPOKEN LANGUAGE didn't match for Link Case API");
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].communicationPreferences[0].valuePairIdCommPref"), "WRITTEN_LANGUAGE_" + datatable.get("WRITTEN LANGUAGE"), memberType + "WRITTEN LANGUAGE didn't match for Link Case API");
                        }
                    } else if (verifyField.contains("CONSENT")) {
                        String[] expectedConsentTypeArray = datatable.get("CONSENT TYPE").split(",");
                        List<String> expectedConsentTypeList = Arrays.asList(expectedConsentTypeArray);
                        List<String> actualConsentTypeList = new ArrayList<>();
                        List<Map<String,String>> actualConsentTypeMap = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.caseMemberResponses[0].consumers[0].consumerConsent");
                        for (int i = 0; i < actualConsentTypeMap.size(); i++) {
                            if(String.valueOf(actualConsentTypeMap.get(i).get("optIn")).equals("true")){
                                actualConsentTypeList.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerConsent[" + i + "].consentType"));
                            }
                        }
                        System.out.println(actualConsentTypeList);
                        Collections.sort(expectedConsentTypeList);
                        Collections.sort(actualConsentTypeList);
                        Assert.assertTrue(actualConsentTypeList.equals(expectedConsentTypeList), memberType + "Consent Types mismatched");
                    } else
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0]." + verifyField), datatable.get(verifyField), memberType + " " + verifyField + " didn't match for Link Case API");
                }
                break;
            case "APPLICATION MEMBER":
                if (isApplicationDuplicate.get()) {
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerFirstName"), duplicateFirstname, memberType + "First name didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerLastName"), duplicateLastName, memberType + " Last name didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerDateOfBirth"), duplicateDOB, memberType + " DOB  didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].consumerSSN"), duplicateSSN, memberType + " SSN  didn't match for Link Case API");
                }else{
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerFirstName"), amFirstName, memberType + "First name didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerLastName"), amLastName, memberType + "Last name didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerDateOfBirth"), amDOB, memberType + "DOB  didn't match for Link Case API");
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerSSN"), amSSN, memberType + "SSN  didn't match for Link Case API");
                }
                Assert.assertTrue(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.caseMemberResponses[1].consumers[0].consumerId")).chars().allMatch(Character::isDigit), memberType + "Consumer ID isn't digit for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerStatus"), "Active", memberType + "Status  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerType"), "Consumer", memberType + "Consumer Type  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].caseConsumers[0].receiveCorrespondence"), "NO", memberType + " RECEIVE CORRESPONDENCE  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].caseConsumers[0].consumerRole"), "Member", memberType + " Consumer Role Type  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].createdBy"), apiUserID, memberType + " CreatedBy  didn't match for Link Case API");
                List<Map<String,Object>> actualConsentTypeMap = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.caseMemberResponses[1].consumers[0].consumerConsent");
                for (int i = 0; i < actualConsentTypeMap.size(); i++) {
                    if(actualConsentTypeMap.get(i).get("consentType").equals("Mail")){
                        Assert.assertEquals(String.valueOf(actualConsentTypeMap.get(i).get("optIn")),"true",memberType + " Default consent values mismatched");
                    }
                    else if(actualConsentTypeMap.get(i).get("consentType").equals("Phone")){
                        Assert.assertEquals(String.valueOf(actualConsentTypeMap.get(i).get("optIn")),"true",memberType + " Default consent values mismatched");
                    }
                    else {
                        Assert.assertEquals(String.valueOf(actualConsentTypeMap.get(i).get("optIn")),"false",
                                memberType + " Default consent values mismatched for " + actualConsentTypeMap.get(i).get("consentType") +" Only Mail and Phone Consent Types should be optIn true.");
                    }
                }
                for (String verifyField : datatable.keySet()) {
                    if (verifyField.contains("LANGUAGE")) {
                        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].communicationPreferences[0].valuePairIdCommPref").contains("SPOKEN")) {
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].communicationPreferences[0].valuePairIdCommPref"), "SPOKEN_LANGUAGE_" + datatable.get("SPOKEN LANGUAGE"), memberType + " SPOKEN LANGUAGE didn't match for Link Case API");
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].communicationPreferences[1].valuePairIdCommPref"), "WRITTEN_LANGUAGE_" + datatable.get("WRITTEN LANGUAGE"), memberType + " WRITTEN LANGUAGE didn't match for Link Case API");
                        } else {
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].communicationPreferences[1].valuePairIdCommPref"), "SPOKEN_LANGUAGE_" + datatable.get("SPOKEN LANGUAGE"), memberType + " SPOKEN LANGUAGE didn't match for Link Case API");
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].communicationPreferences[0].valuePairIdCommPref"), "WRITTEN_LANGUAGE_" + datatable.get("WRITTEN LANGUAGE"), memberType + " WRITTEN LANGUAGE didn't match for Link Case API");
                        }
                    } else
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0]." + verifyField), datatable.get(verifyField), memberType + " " + verifyField + " didn't match for Link Case API");
                }
                break;
            case "AUTHORIZED REPRESENTATIVE":
                Assert.assertTrue(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.caseMemberResponses[1].consumers[0].consumerId")).chars().allMatch(Character::isDigit), memberType + " Consumer ID isn't digit for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerFirstName"), authRepFirstName, memberType + " First name didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerLastName"), authRepLastName, memberType + "  Last name didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerMiddleName"),datatable.get("consumerMiddleName") , memberType + "  Middle name didn't match for Link Case API");
                if (datatable.get("effectiveStartDate") != null && datatable.get("effectiveStartDate").equalsIgnoreCase("PAST TIMESTAMP")) {
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].caseConsumers[0].effectiveStartDate").substring(0, 9), pastTimeStamp.get().substring(0, 9), memberType + " effectiveStartDate didn't match for Link Case API");
                }
                if (datatable.get("effectiveEndDate") != null && datatable.get("effectiveEndDate").equalsIgnoreCase("FUTURE TIMESTAMP")) {
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].caseConsumers[0].effectiveEndDate").substring(0, 9), futureTimeStamp.get().substring(0, 9), memberType + " effectiveEndDate didn't match for Link Case API");
                }
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerStatus"), datatable.get("consumerStatus"), memberType + " Status  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].consumerType"), "Consumer", memberType + " Consumer Type  didn't match for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].caseConsumers[0].consumerRole"), "Authorized Representative", memberType + " Consumer Role Type  didn't match for Link Case API");
                Assert.assertTrue(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.caseMemberResponses[1].consumers[0].consumerId")).chars().allMatch(Character::isDigit), memberType + " Consumer ID isn't digit for Link Case API");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[1].consumers[0].caseConsumers[0].receiveCorrespondence"), datatable.get("RECEIVE CORRESPONDENCE"), memberType + " RECEIVE CORRESPONDENCE  didn't match for Link Case API");
                break;
            case "CONTACT INFORMATION":
                for (String verifyField : datatable.keySet()) {
                    if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].contacts." + verifyField) != null) {
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.caseMemberResponses[0].consumers[0].contacts." + verifyField), datatable.get(verifyField), memberType + " " + verifyField + " didn't match for Link Case API");
                    }
                }
                List<String> phoneArray = new ArrayList<>();
                List<String> addressArray = new ArrayList<>();
                List<Map<String, String>> addresses ;
                List<Map<String, String>> phones;
                if(datatable.keySet().contains("P.addressType")) addressArray.add("Physical");
                if(datatable.keySet().contains("M.addressType")) addressArray.add("Mailing");
                if(datatable.keySet().contains("W.phoneType")) phoneArray.add("Work");
                if(datatable.keySet().contains("C.phoneType")) phoneArray.add("Cell");
                if(datatable.keySet().contains("H.phoneType")) phoneArray.add("Home");
                if(datatable.keySet().contains("F.phoneType")) phoneArray.add("Fax");

                if(!addressArray.isEmpty()){
                    addresses = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.caseMemberResponses[0].consumers[0].contacts.addresses");
                    for (int i = 0; i < addresses.size(); i++) {
                        String addressType = addresses.get(i).get("addressType");
                        if (addressType.equalsIgnoreCase("Mailing")) {
                            for (String verifyField : datatable.keySet()) {
                                if (verifyField.startsWith("M")) {
                                    String tempVerifyField = verifyField.substring(1);
                                    Assert.assertEquals(addresses.get(i).get(tempVerifyField), datatable.get(tempVerifyField), tempVerifyField + "mismatched");
                                    addressArray.remove("Mailing");
                                }
                            }
                        } else if (addressType.equalsIgnoreCase("Physical")) {
                            for (String verifyField : datatable.keySet()) {
                                if (verifyField.startsWith("P")) {
                                    String tempVerifyField = verifyField.substring(1);
                                    Assert.assertEquals(addresses.get(i).get(tempVerifyField), datatable.get(tempVerifyField), tempVerifyField + "mismatched");
                                    addressArray.remove("Physical");
                                }
                            }
                        }
                    }
                }
                if(!phoneArray.isEmpty()){
                    phones = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.caseMemberResponses[0].consumers[0].contacts.phones");
                    for (int i = 0; i < phones.size(); i++) {
                        String phoneType = phones.get(i).get("phoneType");
                        switch (phoneType) {
                            case "Work":
                                for (String verifyField : datatable.keySet()) {
                                    if (verifyField.startsWith("W")) {
                                        String tempVerifyField = verifyField.substring(1);
                                        Assert.assertEquals(phones.get(i).get(tempVerifyField), datatable.get(tempVerifyField), tempVerifyField + "mismatched");
                                    }
                                }
                                phoneArray.remove("Work");
                                break;
                            case "Cell":
                                for (String verifyField : datatable.keySet()) {
                                    if (verifyField.startsWith("C")) {
                                        String tempVerifyField = verifyField.substring(1);
                                        Assert.assertEquals(phones.get(i).get(tempVerifyField), datatable.get(tempVerifyField), tempVerifyField + "mismatched");
                                    }
                                }
                                phoneArray.remove("Cell");
                                break;
                            case "Home":
                                for (String verifyField : datatable.keySet()) {
                                    if (verifyField.startsWith("H")) {
                                        String tempVerifyField = verifyField.substring(1);
                                        Assert.assertEquals(phones.get(i).get(tempVerifyField), datatable.get(tempVerifyField), tempVerifyField + "mismatched");
                                    }
                                }
                                phoneArray.remove("Home");
                                break;
                            case "Fax":
                                for (String verifyField : datatable.keySet()) {
                                    if (verifyField.startsWith("F")) {
                                        String tempVerifyField = verifyField.substring(1);
                                        Assert.assertEquals(phones.get(i).get(tempVerifyField), datatable.get(tempVerifyField), tempVerifyField + "mismatched");
                                    }
                                }
                                phoneArray.remove("Fax");
                                break;
                        }
                    }
                }
                Assert.assertTrue(phoneArray.isEmpty(), phoneArray + " phoneType couldn't found in the response");
                Assert.assertTrue(addressArray.isEmpty(), addressArray + "addressType couldn't found in the response");
        }
    }

    @And("I get the application ids linked to the case for {string}")
    public void iGetApplicationsLinkedToTheCase(String consumerRoleType){
        ArrayList dataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.results");
        Map<Integer,Map<String,Object>> map = new TreeMap<>();
        for (Object o : dataList) {
            HashMap dataValues = (HashMap) o;
            if (dataValues.get("consumerRoleType")!=null && dataValues.get("consumerRoleType").toString().equalsIgnoreCase(consumerRoleType)) {
                map.put(Integer.parseInt(dataValues.get("applicationId").toString()),dataValues);
                System.out.println(map);
                World.generalSave.get().put("ApplicationId" , dataValues.get("applicationId").toString());
            }
        }
        System.out.println(map);
        World.generalSave.get().put("CaseApplicationMap" , map);
    }

    @And("I initiate and run the matching Consumer Links API")
    public void iRunTheMatchingConsumerLinksAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(applicationDataService);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(applicationIdAPI.get() + matchingConsumerLinksAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Matching Consumer Links  API Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
    }

    @And("I verify matching Consumer Links API with the following values")
    public void iVerifyMatchingConsumerLinksAPIWithTheFollowingValues(Map<String, String> datatable) {
        Assert.assertTrue(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.matchingConsumers[0].consumerId")).chars().allMatch(Character::isDigit),"Consumer Id is not found");
        ArrayList<LinkedHashMap> applicationLinks = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.matchingConsumers[0].applicationLink");
        for (int i = 0; i < applicationLinks.size(); i++) {
            Assert.assertEquals(String.valueOf(applicationLinks.get(i).get("applicationId")), appIdList.get().get(i), "Application ID mismatched");
            Assert.assertEquals(applicationLinks.get(i).get("applicationStatus"), datatable.get("applicationStatus" + i), "Application Status mismatched");
        }
    }

    @And("I verify matching Consumer Links API ignores the application")
    public void iVerifyMatchingConsumerLinksAPIIgnoresTheApplication() {
        Assert.assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.matchingConsumers"),"Matching Consumer Links API didn't ignore the application.Application ID " + applicationIdAPI.get() );
    }
    @And("I verify number of consumerIds linked to the case are {int}")
    public void iVerifyNumberOfConsumersLinkedToCase(int numberOfConsumers){
        ArrayList dataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("result");
        List<String> consumerIds = new ArrayList<>();
        for (Object o : dataList) {
            HashMap dataValues = (HashMap) o;
            ArrayList consumer =  (ArrayList) dataValues.get("consumers");
            for(int i=0;i<consumer.size();i++){
                HashMap consumerValues = (HashMap) consumer.get(i);
                String consId =consumerValues.get("consumerId").toString();
                consumerIds.add(consId);
            }
        }
        System.out.println(consumerIds);
        System.out.println(consumerIds.size());
        Assert.assertEquals(consumerIds.size(),numberOfConsumers);
    }


}
