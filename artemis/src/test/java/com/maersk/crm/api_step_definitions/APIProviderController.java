package com.maersk.crm.api_step_definitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.maersk.crm.beans.createProvider.ProviderRequest;
import com.maersk.crm.beans.eventResponse.Example;
import com.maersk.crm.beans.searchProviderRequest.ProviderSearch;
import com.maersk.crm.beans.searchProviderRequest.ProviderSearchRequest;
import com.maersk.crm.beans.searchProviderResponse.ProviderSearchResponse;
import com.maersk.crm.step_definitions.*;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class APIProviderController extends CRMUtilities implements ApiBase {
    //private api api = new api();
    APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    TM_AddNewUserToProjectStepDef proRole = new TM_AddNewUserToProjectStepDef();
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> uiid = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<Integer> eventId = ThreadLocal.withInitial(() -> 0);
    public final ThreadLocal<String> action = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> recordType = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> subscriberId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> projectName = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<Integer> projectId = ThreadLocal.withInitial(() -> 0);
    public final ThreadLocal<String> createdOn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> createdBy = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> contactReason = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> contactAction = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> updatedOn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> updatedBy = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> npiRandom = ThreadLocal.withInitial(String::new);
    private static String planManagementBaseURI = ConfigurationReader.getProperty("apiPlanManagementURI");
    private static String getAllCaseBaseURI = ConfigurationReader.getProperty("apiArtBaseURL");
    private String planProviderEndPoint = "/mars/eb/provider";
    private String getAllCaseDetialEndPoint = "/artemis/services/cases?listViewConfigName=Default View&offSet=0&limit=10";
    private String planProviderEndPointV2 = "/mars/eb/provider/v2";
    private String retryHeremapCall = "/mars/eb/retryheremapcall";
    private String providerSearchEndPoint = "/mars/eb/provider/providers";
    private String digitalProviderSearchEndPoint = "/mars/eb/digital/provider/providers";
    private String baseAPIUrl = ConfigurationReader.getProperty("apiEventsURI");
    private String baseProviderAPIUrl = ConfigurationReader.getProperty("apiProviderURI");
    private String contactRecordEventsEndPoint = "/app/crm/events?size=100000000&page=0&sort=eventId,desc";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    private String apiTMURI = ConfigurationReader.getProperty("apiTMURI");
    ProviderRequest cp;
    final ThreadLocal<String> req = ThreadLocal.withInitial(String::new);
    ProviderSearch ps;
    ProviderSearchRequest psr;
    ProviderSearchResponse psresp;
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private final ThreadLocal<ObjectMapper> om = ThreadLocal.withInitial(ObjectMapper::new);
    private final ThreadLocal<Example> response = ThreadLocal.withInitial(Example::new);
    //public Api_Body apistrg = Api_Body.getInstance();
    private String providerSearchLang = "/mars/eb/provider/languages";
    private String proUserInfo = "/mars/tm/project/userinfo";
    private String LDSSUpdate = "mars/eb/providerdata";
    private final ThreadLocal<String> frstResName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> scndResName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> frstResUserId = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> scndResUserId = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> fname = ThreadLocal.withInitial(String::new), lname = ThreadLocal.withInitial(String::new), temp_npi = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Map<String, String>> realMap = ThreadLocal.withInitial(HashMap::new);
    //public Api_CommonSteps api_commonSteps=new Api_CommonSteps();

    @Given("I initiated Plan Management for service provider API")
    public void i_initiated_Plan_Management_for_service_provider_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(planProviderEndPoint);
    }

    @Given("I initiated Plan Management for service provider API with new version")
    public void i_initiated_Plan_Management_for_service_provider_API2() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(planProviderEndPointV2);
    }

    @Given("I initiated Plan Management for retrying Heremap call API")
    public void i_initiated_Plan_Management_for_Retrying_heremap_call_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(retryHeremapCall);
    }
// @sean thorson
//
//    @When("I will get the Authentication token for {string} in {string}")
//    public void getAuthenticationToken(String projectName,String app){
//        api.getOauthToken(app);
//        api.getProjectName1(projectName);
//    }
//
//    @When("I create a \"(.*)\" request payload with the appropriate data and post with data")
//    public void i_create_a_new_provider_with_request_payload_with_the_appropriate_data_and_postwithData(String payload, Map<String, String> data) throws IOException {
//        om = new ObjectMapper();
//        cp = om.readValue(generateProviderPayload(payload,data).toString(),ProviderRequest.class);
//        req = om.writeValueAsString(cp);
//        api.PostAPIWithParameter(req);
//    }
//
////@Sean Thorson


    @When("I create a new provider with request payload with the appropriate data and post")
    public void i_create_a_new_provider_with_request_payload_with_the_appropriate_data_and_post() throws IOException {
        om.set(new ObjectMapper());
        cp = om.get().readValue(generateProvider().toString(), ProviderRequest.class);
//        cp.getProviders().get(0).setLastName("JamesBond");
        req.set(om.get().writeValueAsString(cp));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(req.get());
    }

    @When("I create a {string} request payload with the appropriate data and post with data")
    public void i_create_a_new_provider_with_request_payload_with_the_appropriate_data_and_postwithData(String payload, Map<String, String> data) throws IOException {
        om.set(new ObjectMapper());
        cp = om.get().readValue(generateProviderPayload(payload, data).toString(), ProviderRequest.class);
        req.set(om.get().writeValueAsString(cp));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(req.get());
    }

    @Then("I verify the response status code {string} with creation status {string}")
    public void i_verify_the_response_status_code_with_creation_status(String statusCode, String messageCreation) {
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode == 404) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("Unable to geo-locate"));
        }
        System.out.println("api.statusCode = " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(statusCode, String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(messageCreation));
        correlationId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
    }

    @Then("I verify errors and warnings in the response after creating/updating provider with data")
    public void I_verify_errors_and_warnings_in_the_response_after_creating_or_updating_provider_with_data(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "errors":
                    if (data.get(key).equals("null") || data.get(key).isEmpty()) {
                        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorResponse").isJsonNull(), "errors aren't empty! - FAIL!");
                    } else {
                        List<String> actualErrors = Arrays.stream(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("errorResponse").get("errorCode").toString()
                                .replaceAll("[\"\\[\\]\\s+]", "").split(",")).sorted().collect(Collectors.toList());
                        List<String> expectedErrors = Arrays.stream(data.get(key).split(", ")).sorted().collect(Collectors.toList());
                        System.out.println("error codes are: " + expectedErrors);
                        assertEquals(actualErrors, expectedErrors, "errors mispatch! - FAIL!");
                    }
                    break;
                case "warnings":
                    if (data.get(key).equals("null") || data.get(key).isEmpty()) {
                        try {
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("warnings").size(), 0, "warnings aren't empty! - FAIL!");
                        } catch (NullPointerException e) {
                            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.has("warnings"));
                        }
                    } else {
                        List<String> actualWornings = Arrays.stream(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("warnings").toString()
                                .replaceAll("[\"\\[\\]\\s+]", "").split(",")).sorted().collect(Collectors.toList());
                        List<String> expectedWornings = Arrays.stream(data.get(key).split(",")).sorted().collect(Collectors.toList());
                        System.out.println("warning codes are: " + expectedWornings);
                        assertEquals(actualWornings, expectedWornings, "warnings mispatch! - FAIL!");
                    }
                    break;
            }
        }
    }

    @When("I initiate Event POST API Services")
    public void i_initiate_Event_POST_API_Services() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactRecordEventsEndPoint);
    }

    @Then("I verify the payload has action, recordType, eventCreatedOn, and dataObject")
    public void i_verify_the_payload_has_action_recordType_eventCreatedOn_and_dataObject() throws IOException {
        om.set(new ObjectMapper());
        response.set(om.get().readValue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString, Example.class));
        assertTrue(response.get().getEventsList().getContent().get(0).getPayload().toString().contains("action"));
        assertTrue(response.get().getEventsList().getContent().get(0).getPayload().toString().contains("recordType"));
        assertTrue(response.get().getEventsList().getContent().get(0).getPayload().toString().contains("eventCreatedOn"));
        assertTrue(response.get().getEventsList().getContent().get(0).getPayload().toString().contains("dataObject"));
    }

    @When("I create a new event with request payload with the appropriate data and trace-id and post")
    public void i_create_a_new_event_with_request_payload_with_the_appropriate_data_and_trace_id_and_post() throws IOException {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        om.set(new ObjectMapper());
        response.set(om.get().readValue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString, Example.class));
        subscriberId.set("12700");
        System.out.println(">>>" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(">>>eventId" + response.get().getEventsList().getContent().get(0).getEventId());
        eventId.set(response.get().getEventsList().getContent().get(0).getEventId());
        createdOn.set(response.get().getEventsList().getContent().get(0).getCreatedOn());
        updatedOn.set(response.get().getEventsList().getContent().get(0).getUpdatedOn());
        createdBy.set(response.get().getEventsList().getContent().get(0).getCreatedBy());
    }

    @Then("I verify created date and created by fields as appropriate")
    public void I_verify_created_date_and_created_by_fields_as_appropriate() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        assertEquals(temp.getString("createdBy"), "SYSTEM");
        assertEquals(temp.getString("updatedBy"), "SYSTEM");
        assertEquals(temp.getString("createdOn"), createdOn.get());
        assertEquals(temp.getString("updatedOn"), updatedOn.get());
    }

    @Then("I initiate Subscribers Record GET API and run Subscribers Record GET API Services")
    public void i_initiate_Subscribers_Record_GET_API_and_run_Subscribers_Record_GET_API_Services() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId.get() + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I will verify Subscribers response has event Id and {string} and subscriberId")
    public void i_will_verify_Subscribers_response_has_event_Id_and_and_subscriberId(String eventName) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        System.out.println(json.toString());
        JSONObject temp = new JSONObject(json.get(0).toString());
        assertEquals(temp.getString("eventName"), eventName);
        System.out.println("Even name on DPBI:  " + eventName);

        assertEquals(temp.getInt("eventId"), eventId.get());
        System.out.println("Event id on DPBI:  " + eventId.get());

        assertEquals(temp.getInt("subscriberId") + "", subscriberId.get());
        System.out.println("Subscriber id on DPBI:   " + subscriberId.get());
        System.out.println("Event is subscribed to DPBI list");
    }

    public JsonObject generateProvider() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/createProvider.json");
        Random r = new Random();
        String groupName = "g".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(2).randomString);
        String firstName = "fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString);
        String lastName = "ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString);
        String ssn = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber;
        String stateProviderId = "spId".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(7).randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("providers").get(0).getAsJsonObject().addProperty("groupName", groupName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("providers").get(0).getAsJsonObject().addProperty("firstName", firstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("providers").get(0).getAsJsonObject().addProperty("lastName", lastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("providers").get(0).getAsJsonObject().addProperty("ssn", ssn);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("providers").get(0).getAsJsonObject().addProperty("stateProviderId", stateProviderId);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public JsonObject generateProviderPayload(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/" + payload + ".json");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("providers").get(0).getAsJsonObject().addProperty(k, v);
        }
        String NPI = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("providers").get(0).getAsJsonObject().get("npi").toString();
        if (NPI == null || NPI == "") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("providers").get(0).getAsJsonObject().addProperty("npi", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomNpi());
        }
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @When("I initiate Provider Search API")
    public void i_initiate_Provider_Search_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseProviderAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(providerSearchEndPoint);
    }

    @When("I initiate digital Provider Search API")
    public void i_initiate_digital_Provider_Search_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseProviderAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(digitalProviderSearchEndPoint);
    }

    @When("I create a request payload with all the search field values and post")
    public void i_create_a_request_payload_with_all_the_search_field_values_and_post() throws JsonProcessingException {
        ps = new ProviderSearch();
        psr = new ProviderSearchRequest();
        om.set(new ObjectMapper());
        ps.setFirstName(cp.getProviders().get(0).getFirstName());
        ps.setLastName(cp.getProviders().get(0).getLastName());
        ps.setGroupName(cp.getProviders().get(0).getGroupName());
        ps.setPlanName(cp.getProviders().get(0).getPlanName());
        ps.setMiddleName(cp.getProviders().get(0).getMiddleName());
        ps.setPlanCode(cp.getProviders().get(0).getPlanCode());
        ps.setZipCode(cp.getProviders().get(0).getProviderAddress().get(0).getZipCode());
        ps.setHandicappedAccesibleInd(cp.getProviders().get(0).getHandicappedAccesibleInd());
        ps.setCity(cp.getProviders().get(0).getProviderAddress().get(0).getCity());
        ps.setNameSuffix((String) cp.getProviders().get(0).getNameSuffix());
        ps.setTitleName((String) cp.getProviders().get(0).getTitleName());
        ps.setNpi(cp.getProviders().get(0).getNpi());
        ps.setStateProviderId(cp.getProviders().get(0).getStateProviderId());
        ps.setGenderCd(cp.getProviders().get(0).getGenderCd());
        ps.setAcceptObInd(cp.getProviders().get(0).getAcceptObInd());
        ps.setAgeLowLimit(cp.getProviders().get(0).getAgeLowLimit());
        ps.setAgeHighLimit(cp.getProviders().get(0).getAgeHighLimit());
        psr.setProviderSearch(ps);
        psr.setSize(100);
        psr.setFrom(0);
        req.set(om.get().writeValueAsString(psr));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(req.get());
    }

    @Then("I verify all the search result parameters with the new provider parameters values")
    public void i_verify_all_the_search_result_parameters_with_the_new_provider_parameters_values() throws IOException {
        om.set(new ObjectMapper());
        psresp = om.get().readValue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString, ProviderSearchResponse.class);
        System.out.println("below");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getPlanCode(), cp.getProviders().get(0).getPlanCode(), "INVALID PlanCode");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getPlanName(), cp.getProviders().get(0).getPlanName(), "INVALID PlanName");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getMcoId().toString(), cp.getProviders().get(0).getMcoId(), "INVALID McoId");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getProgramTypeCd(), cp.getProviders().get(0).getProgramTypeCd(), "INVALID ProgramTypeCd");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getEffectiveStartDate(), cp.getProviders().get(0).getEffectiveStartDate(), "INVALID EffectiveStartDate");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getEffectiveEndDate(), cp.getProviders().get(0).getEffectiveEndDate(), "INVALID EffectiveEndDate");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getNpi(), cp.getProviders().get(0).getNpi(), "INVALID NPI");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getStateProviderId(), cp.getProviders().get(0).getStateProviderId(), "INVALID StateProviderId");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getPcpFlag(), cp.getProviders().get(0).getPcpFlag(), "INVALID PcpFlag");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getGenderCd(), cp.getProviders().get(0).getGenderCd(), "INVALID GenderCd");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getSexLimitsCd(), cp.getProviders().get(0).getSexLimitsCd(), "INVALID SexLimitsCd");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getAgeLowLimit().toString(), cp.getProviders().get(0).getAgeLowLimit(), "INVALID AgeLowLimit");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getAgeHighLimit().toString(), cp.getProviders().get(0).getAgeHighLimit(), "INVALID AgeHighLimit");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getClassificationCd(), cp.getProviders().get(0).getClassificationCd(), "INVALID ClassificationCd");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getGroupFlag(), cp.getProviders().get(0).getGroupFlag(), "INVALID GroupFlag");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getGroupName(), cp.getProviders().get(0).getGroupName(), "INVALID GroupName");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getFirstName(), cp.getProviders().get(0).getFirstName(), "INVALID FirstName");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getLastName(), cp.getProviders().get(0).getLastName(), "INVALID LastName");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getNameSuffix(), cp.getProviders().get(0).getNameSuffix(), "INVALID NameSuffix");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getTitleName(), cp.getProviders().get(0).getTitleName(), "INVALID TitleName");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getSsn(), cp.getProviders().get(0).getSsn(), "INVALID Ssn");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getFein(), cp.getProviders().get(0).getFein(), "INVALID Fein");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getEmailAddress(), cp.getProviders().get(0).getEmailAddress(), "INVALID EmailAddress");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getMiddleName(), cp.getProviders().get(0).getMiddleName(), "INVALID MiddleName");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getAcceptObInd(), cp.getProviders().get(0).getAcceptObInd(), "INVALID AcceptObInd");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getAcceptNewPatientsInd(), cp.getProviders().get(0).getAcceptNewPatientsInd(), "INVALID AcceptNewPatientsInd");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getHandicappedAccesibleInd(), cp.getProviders().get(0).getHandicappedAccesibleInd(), "INVALID HandicappedAccesibleInd");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getReasonForChange(), cp.getProviders().get(0).getReasonForChange(), "INVALID ReasonForChange");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getCreatedOn(), cp.getProviders().get(0).getCreatedOn(), "INVALID CreatedOn");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getUpdatedOn(), cp.getProviders().get(0).getUpdatedOn(), "INVALID UpdatedOn");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getCreatedBy(), cp.getProviders().get(0).getCreatedBy(), "INVALID CreatedBy");
        assertEquals(psresp.getProviderSearchResponse().getContent().get(0).getUpdatedBy(), cp.getProviders().get(0).getUpdatedBy(), "INVALID UpdatedBy");
    }

    @Then("I verify first found provider search result from api with data")
    public void I_verify_first_found_provider_search_result_from_api_with_data(Map<String, String> data) {
        String payloadType = (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("providerSearchResponse") != null) ? "providerSearchResponse" : "providers";
        JsonObject firstFoundProvider = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject(payloadType).getAsJsonArray("content").get(0).getAsJsonObject();
        System.out.println("firstFoundProvider = " + firstFoundProvider);
        for (String key : data.keySet()) {
            switch (key) {
                case "distance":
                    if (data.get("distance").isEmpty() || data.get("distance").equals("null")) {
                        assertTrue(firstFoundProvider.getAsJsonArray("providerAddress").get(0)
                                        .getAsJsonObject().getAsJsonObject("normalizedPhysicalAddress").getAsJsonObject("distance").isJsonNull(),
                                "provider distance is not NULL!");
                    } else {
                        assertEquals(firstFoundProvider.getAsJsonArray("providerAddress").get(0)
                                        .getAsJsonObject().getAsJsonObject("normalizedPhysicalAddress").getAsJsonObject("distance").getAsString(),
                                data.get("distance"), "provider distance mismatch!");
                    }
                    break;
                case "firstName":
                case "lastName":
                case "npi":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(firstFoundProvider.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        assertTrue(firstFoundProvider.get(key).getAsString().equalsIgnoreCase(String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)))
                        ), key + " missmatch!");
                    }
                    break;
                case "providerPhoneNumber is present in Mailing address":
                    JsonArray providerAddress1 = firstFoundProvider.getAsJsonArray("providerAddress");
                    for (int i = 0; i < providerAddress1.size(); i++) {
                        if (providerAddress1.get(i).getAsJsonObject().get("addressType").getAsString().equals("Mailing")) {
                            assertTrue(providerAddress1.get(i).getAsJsonObject().getAsJsonArray("phoneNumbers")
                                    .get(0).getAsJsonObject().has("phoneNumber"), "providerPhoneNumber is NOT present in Mailing address!");
                        }
                    }
                    break;
                case "providerPhoneNumber is present in Physical address":
                    JsonArray providerAddress2 = firstFoundProvider.getAsJsonArray("providerAddress");
                    for (int i = 0; i < providerAddress2.size(); i++) {
                        if (providerAddress2.get(i).getAsJsonObject().get("addressType").getAsString().equals("Physical")) {
                            assertTrue(providerAddress2.get(i).getAsJsonObject().getAsJsonArray("phoneNumbers")
                                    .get(0).getAsJsonObject().has("phoneNumber"), "providerPhoneNumber is NOT present in Physical address!");
                        }
                    }
                    break;
                case "normalizedPhysicalAddress is present in providerAddress":
                    JsonArray providerAddress3 = firstFoundProvider.getAsJsonArray("providerAddress");
                    for (int i = 0; i < providerAddress3.size(); i++) {
                        assertTrue(providerAddress3.get(i).getAsJsonObject().has("normalizedPhysicalAddress"),
                                "normalizedPhysicalAddress is NOT present in providerAddress!");
                    }
                    break;
                case "lattitude":
                    assertEquals(firstFoundProvider.getAsJsonArray("providerAddress").get(1)
                            .getAsJsonObject().getAsJsonObject("normalizedPhysicalAddress").get("lattitude").toString(), data.get("lattitude"), "Lattitude value unmatched");
                    break;
                case "longitude":
                    assertEquals(firstFoundProvider.getAsJsonArray("providerAddress").get(1)
                            .getAsJsonObject().getAsJsonObject("normalizedPhysicalAddress").get("longitude").toString(), data.get("longitude"), "Longitude value unmatched");
                    break;
                case "retryCount":
                    assertTrue(Integer.parseInt(firstFoundProvider.getAsJsonArray("providerAddress").get(1)
                            .getAsJsonObject().getAsJsonObject("normalizedPhysicalAddress").get("retryCount").toString()) <= Integer.parseInt(data.get("retryCount")), "RetryCount value is invalid");
                    break;
            }
        }
    }

    @Then("I verify provider search response from api with data")
    public void I_verify_provider_search_response_from_api_with_data(Map<String, String> data) {
        JsonObject response = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
        JsonObject providers = response.has("providers")
                ? response.getAsJsonObject("providers")
                : response.getAsJsonObject("providerSearchResponse");
        for (String key : data.keySet()) {
            switch (key) {
                case "providers count":
                    String providerCount = providers.getAsJsonArray("content").size() + "";
                    System.out.println("providerCount = " + providerCount);
                    if (data.get(key).equals("0") || data.get(key).isEmpty() || data.get(key).equals("null") || data.get(key).equals("empty")) {
                        assertEquals(providerCount, "0", key + " is not empty!");
                    } else if (data.get(key).equals("> 1")) {
                        assertNotEquals(providerCount, "0", key + " is 0");
                    } else {
                        assertEquals(providerCount, data.get(key), key + " mismatch!");
                    }
                    break;
                case "totalElements":
                case "numberOfElements":
                    System.out.println(key + " = " + providers.get(key).getAsString());
                    assertEquals(providers.get(key).getAsString(), data.get(key), key + " mismatch!");
                    break;
                case "totalRecords":
                case "status":
                    System.out.println(key + " = " + response.get(key).toString().replaceAll("\"", ""));
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(response.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(response.get(key).getAsString(), data.get(key), key + " mismatch!");
                    }
                    break;
                case "errorCode":
                    assertTrue(response.getAsJsonObject("errorResponse").get("errorCode").getAsString().contains(data.get(key)));
                    break;
            }
        }
    }

    @When("I create a request payload with First Name, Last Name, Group Name, and Phone Number search field values and post")
    public void i_create_a_request_payload_with_First_Name_Last_Name_Group_Name_and_Phone_Number_search_field_values_and_post() throws JsonProcessingException {
        ps = new ProviderSearch();
        psr = new ProviderSearchRequest();
        om.set(new ObjectMapper());
        ps.setFirstName(cp.getProviders().get(0).getFirstName());
        ps.setLastName(cp.getProviders().get(0).getLastName());
        ps.setGroupName(cp.getProviders().get(0).getGroupName());
        ps.setPhoneNumber(cp.getProviders().get(0).getProviderAddress().get(0).getPhoneNumbers().get(0).getPhoneNumber());
        psr.setProviderSearch(ps);
        psr.setSize(100);
        psr.setFrom(0);
        req.set(om.get().writeValueAsString(psr));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(req.get());
    }

    @When("I create a request payload with Plan Name, Provider Type, Language, Gender Served, Provider Gender, and Specialty search field values and post")
    public void i_create_a_request_payload_with_Plan_Name_Provider_Type_Language_Gender_Served_Provider_Gender_and_Specialty_search_field_values_and_post() throws JsonProcessingException {
        ps = new ProviderSearch();
        psr = new ProviderSearchRequest();
        om.set(new ObjectMapper());

        ps.setPlanName(cp.getProviders().get(0).getPlanName());
        ps.setStateProviderId(cp.getProviders().get(0).getStateProviderId());
        ps.setAdditionalProperty("languageTypeCd", cp.getProviders().get(0).getProviderLanguages().get(0).getLanguageTypeCd());
        ps.setGenderCd(cp.getProviders().get(0).getGenderCd());
        ps.setAdditionalProperty("specialityCd", cp.getProviders().get(0).getProviderSpeciality().get(0).getSpecialityCd());

        psr.setProviderSearch(ps);
        psr.setSize(100);
        psr.setFrom(0);
        req.set(om.get().writeValueAsString(psr));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(req.get());
    }

    @When("I create a request payload with First Name, Last Name, and Group Name search field values and post")
    public void i_create_a_request_payload_with_First_Name_Last_Name_and_Group_Name_search_field_values_and_post() throws JsonProcessingException {
        ps = new ProviderSearch();
        psr = new ProviderSearchRequest();
        om.set(new ObjectMapper());
        ps.setFirstName(cp.getProviders().get(0).getFirstName());
        ps.setLastName(cp.getProviders().get(0).getLastName());
        ps.setGroupName(cp.getProviders().get(0).getGroupName());
        psr.setProviderSearch(ps);
        psr.setSize(100);
        psr.setFrom(0);
        req.set(om.get().writeValueAsString(psr));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(req.get());
    }

    @When("I create a request payload with data for search field values and post")
    public void i_create_a_request_payload_with_data_for_search_field_values_and_post(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/providerSearch.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @When("I create a request payload with data for provider search field values and post")
    public void i_create_a_request_payload_with_data_for_provider_search_field_values_and_post(Map<String, String> data) {
        waitFor(5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/newprovidersearch.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @When("I create a request payload with data for cloning permission for given data")
    public void i_create_a_request_payload_with_data_for_cloning_permission(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiClonePermissions.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
        waitFor(4);
    }

    @When("User send API call {string} request payload with the appropriate data and post with data")
    public void User_Send_Api_request_payload_with_the_appropriate_data_and_postwithData(String payload, Map<String, String> data) throws IOException {
        om.set(new ObjectMapper());
        cp = om.get().readValue(generateProviderPayload(payload, data).toString(), ProviderRequest.class);
        req.set(om.get().writeValueAsString(cp));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(req.get());
    }

    @And("User send Api call {string} request payload for search provider")
    public void userSendApiCallRequestPayloadForSearchProvider(String payload, Map<String, String> data) throws IOException {
        om.set(new ObjectMapper());
        cp = om.get().readValue(generateProviderSearch(payload, data).toString(), ProviderRequest.class);
        req.set(om.get().writeValueAsString(cp));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(req.get());
    }

    public JsonObject generateProviderSearch(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/" + payload + ".json");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject().get("providerSearch").getAsJsonObject().addProperty(k, v);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject().addProperty("size", 100);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @Given("I initiated Plan Management for Search Provider")
    public void iInitiatedPlanManagementForSearchProvider() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(providerSearchEndPoint);
    }

    @And("User send Api call with payload {string} for search provider")
    public void updateProviderSearch(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/" + payload + ".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @And("User send Api call with payload {string} for creating provider with name {string} with data")
    public void User_send_Api_call_with_payload_for_creating_provider_with_name_with_data(String payload, String name, Map<String, String> data) throws JsonProcessingException {
        realMap.set(new HashMap<String, String>(data));
        if (data.get("providers[0].firstName").equals("random")) {
            fname.set(generateRandomCharacters());
            realMap.get().put("providers[0].firstName", fname.get());
        }
        if (data.get("providers[0].lastName").equals("random")) {
            lname.set(generateRandomCharacters());
            realMap.get().put("providers[0].lastName", lname.get());
        }
        if (data.get("providers[0].npi").equals("random")) {
            temp_npi.set(generateRandomNumberOfLength(10));
            realMap.get().put("providers[0].npi", temp_npi.get());
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/" + payload + ".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, realMap.get()));
    }

    @And("User send Api call with payload {string} for updating provider with name {string} with data")
    public void User_send_Api_call_with_payload_for_updating_provider_with_name_with_data(String payload, String name, Map<String, String> data) throws JsonProcessingException {
        Map<String, String> realMap = new HashMap<String, String>(data);
        realMap.put("providers[0].firstName", fname.get());
        realMap.put("providers[0].lastName", lname.get());
        realMap.put("providers[0].npi", temp_npi.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/" + payload + ".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, realMap));
    }


    @Then("I send Put API call for retrying heremap call for {string} times")
    public void i_send_put_api_call_for_retrying_heremap_call_with_name_something(String strArg2) {
        for (int i = 0; i < Integer.parseInt(strArg2); i++) {
            System.out.println(requestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPIwithoutBody(requestParams.get());
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        }
    }


    @Given("I initiated Plan Management for Search Provider by Language")
    public void SearchProviderbyLanguage() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(providerSearchLang);
    }

    @And("User send Api call with name {string} payload {string} for Provider Search")
    public void postWithNameRequest(String name, String payload, Map<String, String> data) throws JsonProcessingException {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/" + payload + ".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @When("I will get the Authentication token for {string} in {string}")
    public void getAuthenticationToken(String projectName, String app) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1(projectName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getOauthToken(app, projectName);
    }

    @When("I initiated User info for service provider API")
    public void iInitiatedUserInfoForServiceProviderAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiTMURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(proUserInfo);
    }

    @And("I sending API call to get {string} created new user UserId")
    public void iSendingAPICallWithcreatednewuserUserId(String user) {
        if (user.equalsIgnoreCase("first")) {
            Map<String, String> data = new HashMap<>();
            data.put("projectName", ConfigurationReader.getProperty("tmRegressionProjectName").split(" ")[1]);
            data.put("firstName", proRole.getFirstName());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/projectUserInfo.json");
            String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonStepsThreadLocal2().updateJson(json, data));
            frstResName.set(API_THREAD_LOCAL_FACTORY.getApi_commonStepsThreadLocal2().asStr("c.data[0].firstName"));
            frstResUserId.set(API_THREAD_LOCAL_FACTORY.getApi_commonStepsThreadLocal2().asStr("c.data[0].userId"));
        } else if (user.equalsIgnoreCase("second")) {
            Map<String, String> data = new HashMap<>();
            data.put("projectName", TM_AddNewUserToProjectStepDef.getFirsProName());
            data.put("firstName", proRole.getFirstName());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/projectUserInfo.json");
            String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonStepsThreadLocal2().updateJson(json, data));
            scndResName.set(API_THREAD_LOCAL_FACTORY.getApi_commonStepsThreadLocal2().asStr("c.data[0].firstName"));
            scndResUserId.set(API_THREAD_LOCAL_FACTORY.getApi_commonStepsThreadLocal2().asStr("c.data[0].userId"));
        }
    }

    @Then("Verify in the API response that user ID is different for both users")
    public void verifyInTheAPIResponseThatUserIDIsDifferentForBothUsers() {
        assertEquals(frstResName.get(), scndResName.get());
        assertNotEquals(frstResUserId.get(), scndResUserId.get());
    }

    @And("I send {string} obtained from DMAS and update/save a LDSS Phone Number Lookup table")
    public void I_send_file_obtained_from_DMAS_and_update_a_LDSS_Phone_Number_Lookup_table(String fileSelection) {
        switch (fileSelection) {
            case "LDSSDirectoryFile":
                try {
                    BrowserUtils.classLoader("testData/tm/planAndRegionConfig/LDSSPhoneNumber/LDSSPhoneNumberUpdates.csv");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "LDSSDirectoryFile_Insert":
                try {
                    BrowserUtils.classLoader("testData/tm/planAndRegionConfig/LDSSPhoneNumber/LDSSPhoneNumberUpdates_Insert.csv");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "LDSSDirectoryFile_Update":
                try {
                    BrowserUtils.classLoader("testData/tm/planAndRegionConfig/LDSSPhoneNumber/LDSSPhoneNumberUpdates_Update.csv");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "LDSSDirectoryFile_Delete":
                try {
                    BrowserUtils.classLoader("testData/tm/planAndRegionConfig/LDSSPhoneNumber/LDSSPhoneNumberUpdates_Delete.csv");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(LDSSUpdate);
        String absPath = BrowserUtils.replaceTrimURL.get();
        System.out.println("absPath = " + absPath);
        System.out.println("Printing Abs path: " + absPath);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithKey("File", absPath);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @And("I will verify corresponding Agency Phone Number by provided zip with data")
    public void I_will_verify_corresponding_Agency_Phone_Number_by_provided_zip_with_data(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseProviderAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(providerSearchEndPoint);
        List<String> numbers = new ArrayList<>();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/providerSearch.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        Map<String, String> zipData = new HashMap<>();
        zipData.put("providerSearch.providerTypeCd", "NOT_MEDICAID");
        for (String zip : data.keySet()) {
            zipData.put("providerSearch.zipCode", zip);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, zipData));
            JsonArray foundAgencies = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("providerSearchResponse").getAsJsonArray("content");
            assertTrue(foundAgencies.size() > 0, "Social Services Agents are not found by given zipCode!");
            JsonArray phoneNumbers = foundAgencies.get(0).getAsJsonObject().get("providerAddress").getAsJsonArray()
                    .get(0).getAsJsonObject().getAsJsonArray("phoneNumbers");


            if (data.get(zip).isEmpty() || data.get(zip).equals("null")) {
                assertEquals(phoneNumbers.size(), 0, "Phone number by zipCode " + zip + " is not empty!");
            } else if (data.get(zip).equals("is deleted")) {
                for (int i = 0; i < phoneNumbers.size(); i++) {
                    if (!phoneNumbers.get(i).getAsJsonObject().get("phoneNumber").isJsonNull()) {
                        numbers.add(phoneNumbers.get(i).getAsJsonObject().get("phoneNumber").getAsString());
                    }
                }
                assertFalse(numbers.contains(data.get(zip)), "Phone number by zipCode is not deleted!..Fail");
            } else {
                for (int i = 0; i < phoneNumbers.size(); i++) {
                    if (!phoneNumbers.get(i).getAsJsonObject().get("phoneNumber").isJsonNull()) {
                        numbers.add(phoneNumbers.get(i).getAsJsonObject().get("phoneNumber").getAsString());
                    }
                }
                assertTrue(numbers.contains(data.get(zip)), "Phone number by zipCode " + zip + " is missing!");
            }
        }
    }

    @And("I will verify corresponding geolocation for the following zip code")
    public void I_will_verify_corresponding_Phone_Number_by_provided_zip_with_data(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseProviderAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(providerSearchEndPoint);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/providerSearch.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        Map<String, String> zipData = new HashMap<>();
        zipData.put("providerSearch.providerTypeCd", "NOT_MEDICAID");
        for (String zip : data.keySet()) {
            zipData.put("providerSearch.zipCode", zip);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, zipData));
            JsonArray foundAgencies = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("providerSearchResponse").getAsJsonArray("content");
            assertTrue(foundAgencies.size() > 0, "Social Services Agents are not found by given zipCode!");

            JsonArray phoneNumbers = foundAgencies.get(0).getAsJsonObject().get("providerAddress").getAsJsonArray()
                    .get(0).getAsJsonObject().getAsJsonArray("phoneNumbers");

            JsonElement lattitude = foundAgencies.get(0).getAsJsonObject().get("providerAddress").getAsJsonArray()
                    .get(0).getAsJsonObject().getAsJsonObject("normalizedPhysicalAddress").get("lattitude");


            JsonElement longitude = foundAgencies.get(0).getAsJsonObject().get("providerAddress").getAsJsonArray()
                    .get(0).getAsJsonObject().getAsJsonObject("normalizedPhysicalAddress").get("longitude");


            assertTrue(!lattitude.toString().equals("0.00"), "Geo location lattitude is empty.. Fail");
            assertTrue(!longitude.toString().equals("0.00"), "Geo location lattitude is empty.. Fail");

        }
    }


    public JsonObject getProviderSearchResponse() {
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }


    @Given("I initiated get all cases details API")
    public void i_initiated_get_case_all_details() {
        String apiCode = "200";
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(getAllCaseBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getAllCaseDetialEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("api.statusCode = " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(apiCode, String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode));

    }


}



