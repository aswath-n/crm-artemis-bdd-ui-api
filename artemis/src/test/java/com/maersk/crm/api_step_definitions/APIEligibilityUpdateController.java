package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

public class APIEligibilityUpdateController extends CRMUtilities implements ApiBase {


    private String eligibilityBaseURI = ConfigurationReader.getProperty("apiEligibilityURI");
    private String getEligibilityListByUser = "mars/eb/eligibilities";//"app/crm/eligibility";

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String getEvents = "app/crm/events?size=10000&page=0";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";

    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonObject> requestParamsForEvent = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonObject> eligibilityRequest = ThreadLocal.withInitial(JsonObject::new);

  /*  private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/

    private final ThreadLocal <String> subscriberName = ThreadLocal.withInitial(()->"DPBI");
    public final ThreadLocal <String> eventId = ThreadLocal.withInitial(()->"");
    private final ThreadLocal <String> subscriberId = ThreadLocal.withInitial(()->"");
    private final ThreadLocal <JSONArray> subscriberList = ThreadLocal.withInitial(JSONArray::new);

    @Given("I initiated Eligibility API")
    public void initiatedEligibilityAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eligibilityBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEligibilityListByUser);
    }

    @When("I update long term care info")
    public void updateCoreEligibility() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/eligibility/updateEligibility.json");
        JsonObject eligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        //apitdu.jsonElement.getAsJsonObject().addProperty("categoryCode", "Alpha".concat(apitdu.getRandomNumber(4).randomNumber.toString()));
        eligibility.getAsJsonObject("longTermCareInfo").addProperty("providerName", "Alpha".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(4).randomNumber.toString()));
        eligibilityRequest.set(eligibility);
    }

    @When("I run the Eligibility API")
    public void runEligibilityAPI() {
        JsonArray requestParam = new JsonArray();
        requestParam.add(eligibilityRequest.get());
        System.out.println(requestParam.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParam.toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("SUCCESS"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "SUCCESS");
    }

    @Then("I will verify the long term care data is updated")
    public void updateTheLongTermCareData() {

        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> longTermCareInfo = (HashMap<String, Object>) eligibilityResponseMap.get("longTermCareInfo");

        assertEquals(longTermCareInfo.get("providerName").toString(), eligibilityRequest.get().get("longTermCareInfo").getAsJsonObject().get("providerName").getAsString());
        assertEquals(longTermCareInfo.get("coverageCode").toString(), eligibilityRequest.get().get("longTermCareInfo").getAsJsonObject().get("coverageCode").getAsString());
        assertEquals(longTermCareInfo.get("npiId").toString(), eligibilityRequest.get().get("longTermCareInfo").getAsJsonObject().get("npiId").getAsString());
        assertEquals(eligibilityResponseMap.get("dbChanges").toString(), "updated");
    }

    @When("I update facility info")
    public void updateFacilityInfo() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/eligibility/updateEligibility.json");
        JsonObject eligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        eligibility.getAsJsonObject("facilityInfo").addProperty("placementCode", "pCode".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibility.getAsJsonObject("facilityInfo").addProperty("placementCountyCode", "pcCode".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibility.getAsJsonObject("facilityInfo").addProperty("referralCountyCode", "rcCode".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibilityRequest.set(eligibility);
    }

    @Then("I will verify the facility data is updated")
    public void updateTheFacilityPlacementData() {

        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        assertEquals(eligibilityResponseMap.get("dbChanges").toString(), "updated");
        HashMap<String, Object> facilityInfo = (HashMap<String, Object>) eligibilityResponseMap.get("facilityInfo");

        assertEquals(facilityInfo.get("placementCode").toString(), eligibilityRequest.get().get("facilityInfo").getAsJsonObject().get("placementCode").getAsString());
        assertEquals(facilityInfo.get("placementCountyCode").toString(), eligibilityRequest.get().get("facilityInfo").getAsJsonObject().get("placementCountyCode").getAsString());
        assertEquals(facilityInfo.get("referralCountyCode").toString(), eligibilityRequest.get().get("facilityInfo").getAsJsonObject().get("referralCountyCode").getAsString());
    }

    @When("I update hospice info")
    public void updateHospiceInfo() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/eligibility/updateEligibility.json");
        JsonObject eligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        eligibility.getAsJsonObject("hospiceInfo").addProperty("hospiceIndicator", "hInd".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));

        eligibilityRequest.set(eligibility);
    }

    @Then("I will verify the hospice placement data is updated")
    public void updateTheHospiceData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        assertEquals(eligibilityResponseMap.get("dbChanges").toString(), "updated");
        HashMap<String, Object> hospiceInfo = (HashMap<String, Object>) eligibilityResponseMap.get("hospiceInfo");

        assertEquals(hospiceInfo.get("hospiceIndicator").toString(), eligibilityRequest.get().get("hospiceInfo").getAsJsonObject().get("hospiceIndicator").getAsString());

    }

    @When("I update third party insurance info")
    public void updateThirdPartyInfo() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/eligibility/updateEligibility.json");
        JsonObject eligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        eligibility.getAsJsonObject("thirdPartyInsuranceInfo").addProperty("insuranceSource", "src".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibility.getAsJsonObject("thirdPartyInsuranceInfo").addProperty("insuranceName", "name".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibility.getAsJsonObject("thirdPartyInsuranceInfo").addProperty("insurancePolicyHolderName", "plcyName".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibility.getAsJsonObject("thirdPartyInsuranceInfo").addProperty("insuranceGroupNumber", "grpNum".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));

        eligibilityRequest.set(eligibility);
    }

    @Then("I will verify the third party insurance is updated")
    public void verifyUpdatedThirdPartyData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        assertEquals(eligibilityResponseMap.get("dbChanges").toString(), "updated");
        HashMap<String, Object> thirdPartyInsuranceInfo = (HashMap<String, Object>) eligibilityResponseMap.get("thirdPartyInsuranceInfo");

        assertEquals(thirdPartyInsuranceInfo.get("insuranceSource").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insuranceSource").getAsString());
        assertEquals(thirdPartyInsuranceInfo.get("insuranceName").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insuranceName").getAsString());
        assertEquals(thirdPartyInsuranceInfo.get("insurancePolicyHolderName").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insurancePolicyHolderName").getAsString());
        assertEquals(thirdPartyInsuranceInfo.get("insuranceGroupNumber").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insuranceGroupNumber").getAsString());

    }

    @When("I update waiver info")
    public void updateWaiverInfo() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/eligibility/updateEligibility.json");
        JsonObject eligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        eligibility.getAsJsonObject("waiverInfo").addProperty("waiverCode", "code".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibility.getAsJsonObject("waiverInfo").addProperty("waiverCounty", "county".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));

        eligibilityRequest.set(eligibility);
    }

    @Then("I will verify the waiver data is updated")
    public void verifyUpdatedTWaiverData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        assertEquals(eligibilityResponseMap.get("dbChanges").toString(), "updated");
        HashMap<String, Object> waiverInfo = (HashMap<String, Object>) eligibilityResponseMap.get("waiverInfo");

        assertEquals(waiverInfo.get("waiverCode").toString(), eligibilityRequest.get().get("waiverInfo").getAsJsonObject().get("waiverCode").getAsString());
        assertEquals(waiverInfo.get("waiverCounty").toString(), eligibilityRequest.get().get("waiverInfo").getAsJsonObject().get("waiverCounty").getAsString());

    }

    @When("I update special population data")
    public void updateSpecialPopulationInfo() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/eligibility/updateEligibility.json");
        JsonObject eligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        eligibility.getAsJsonObject("specialPopulationInfo").addProperty("specialEligibilityCode", "code".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibility.getAsJsonObject("specialPopulationInfo").addProperty("specialPopulationCaseNumber", "case".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));
        eligibility.getAsJsonObject("specialPopulationInfo").addProperty("specialPopulationIndicator", "spI".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));

        eligibilityRequest.set(eligibility);
    }

    @Then("I will verify the special population data is updated")
    public void verifyUpdatedSpecialPopulationData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        assertEquals(eligibilityResponseMap.get("dbChanges").toString(), "updated");
        HashMap<String, Object> specialPopulationInfo = (HashMap<String, Object>) eligibilityResponseMap.get("specialPopulationInfo");

        assertEquals(specialPopulationInfo.get("specialEligibilityCode").toString(), eligibilityRequest.get().get("specialPopulationInfo").getAsJsonObject().get("specialEligibilityCode").getAsString());
        assertEquals(specialPopulationInfo.get("specialPopulationCaseNumber").toString(), eligibilityRequest.get().get("specialPopulationInfo").getAsJsonObject().get("specialPopulationCaseNumber").getAsString());
        assertEquals(specialPopulationInfo.get("specialPopulationIndicator").toString(), eligibilityRequest.get().get("specialPopulationInfo").getAsJsonObject().get("specialPopulationIndicator").getAsString());

    }

    @When("I update financial data")
    public void updateFinancialInfo() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/eligibility/updateEligibility.json");
        JsonObject eligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        eligibility.getAsJsonObject("financialInfo").addProperty("costShareAmount", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(3).randomNumber));
        eligibility.getAsJsonObject("financialInfo").addProperty("familySize", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1).randomNumber));
        eligibility.getAsJsonObject("financialInfo").addProperty("fpil", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(3).randomNumber));
        eligibility.getAsJsonObject("financialInfo").addProperty("income", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber));
        eligibility.getAsJsonObject("financialInfo").addProperty("spendDownCode", "spcd".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString()));

        eligibilityRequest.set(eligibility);
    }

    @Then("I will verify the financial data is updated")
    public void verifyUpdatedFinancialData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        assertEquals(eligibilityResponseMap.get("dbChanges").toString(), "updated");
        HashMap<String, Object> financialInfo = (HashMap<String, Object>) eligibilityResponseMap.get("financialInfo");

        assertEquals(financialInfo.get("costShareAmount").toString(), eligibilityRequest.get().get("financialInfo").getAsJsonObject().get("costShareAmount").getAsString());
        assertEquals(financialInfo.get("familySize").toString(), eligibilityRequest.get().get("financialInfo").getAsJsonObject().get("familySize").getAsString());
        assertEquals(financialInfo.get("fpil").toString(), eligibilityRequest.get().get("financialInfo").getAsJsonObject().get("fpil").getAsString());
        assertEquals(financialInfo.get("income").toString(), eligibilityRequest.get().get("financialInfo").getAsJsonObject().get("income").getAsString());
        assertEquals(financialInfo.get("spendDownCode").toString(), eligibilityRequest.get().get("financialInfo").getAsJsonObject().get("spendDownCode").getAsString());

    }

    @Then("I will verify an {string} for DPBI is created for elgibility update")
    public void publishEligibilityUpdateEvent(String eligibilityUpdateEvent) {
        String correlationId = eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("correlationId").getAsString();

        JsonObject event = new JsonObject();
        event.addProperty("correlationId", correlationId);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        String checkFormat = "2019-11-12T12:14:56.000+0000";
        boolean val = checkFormat.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}+\\d{4}");
        System.out.println(val);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        JSONObject eventData = new JSONObject(json.get(json.size() - 1).toString());
        JSONObject payloadObject = new JSONObject(new JSONObject(json.get(json.size() - 1).toString()).get("payload").toString());
        Iterator<String> payloadKeys = payloadObject.keys();
        System.out.println("Payload:" + payloadObject);

        String correlationIdValue = new JSONObject(json.get(json.size() - 1).toString()).get("correlationId").toString();
        eventId.set(new JSONObject(json.get(json.size() - 1).toString()).get("eventId").toString());
        String action = payloadObject.getString("action");
        String recordType = payloadObject.getString("recordType");
        String eventCreatedOn = payloadObject.getString("eventCreatedOn");
        JSONObject dataObject = payloadObject.getJSONObject("dataObject");
        Iterator<String> dataObjectKeys = dataObject.keys();

        //validation of payload
        assertTrue(payloadObject.has("projectId"));
        assertTrue(payloadObject.has("projectName"));
        assertTrue(payloadObject.has("action"));
        assertTrue(payloadObject.has("recordType"));
        assertTrue(payloadObject.has("eventCreatedOn"));
        assertTrue(payloadObject.has("dataObject"));

        //validation of date format
        assertTrue(verifyDatetimeFormat(payloadObject.getJSONObject("dataObject").getString("effectiveStartDate")));
        assertTrue(verifyDatetimeFormat(payloadObject.getJSONObject("dataObject").getString("createdOn")));
        assertTrue(verifyDatetimeFormat(payloadObject.get("eventCreatedOn").toString()));

        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            if (eventsData.get("eventName").equals(eligibilityUpdateEvent)) {
                assertEquals(eventsData.get("status"), "success");
                assertEquals(eventsData.get("correlationId"), correlationId);

                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);

        validateSubscriptionProcessEligibilityEvent(eligibilityUpdateEvent);
    }

    public static String convertMilliSecondsToDateFormatMMddyyyy(long milliseconds) {
        DateFormat simple = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(milliseconds);
        simple.setTimeZone(TimeZone.getTimeZone("GMT"));

        return simple.format(date);
    }

    public void validateSubscriptionProcessEligibilityEvent(String eligibilityUpdateEvent) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName.get());

        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        subscriberId.set(new JSONObject(json.get(0).toString()).get("subscriberId").toString());
        subscriberList.set(new JSONArray(new JSONObject(json.get(0).toString()).get("subscriberList").toString()));

        boolean flag = false;
        for (int i = 0; i < subscriberList.get().length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get().get(i).toString());
            if (temp.get("eventName").toString().trim().equals(eligibilityUpdateEvent)) {
                Assert.assertEquals(temp.get("subscriberId").toString(), subscriberId, "Subscriber Id is wrong");
                Assert.assertEquals(temp.get("eventName").toString().trim(), eligibilityUpdateEvent, "Event Name is wrong");
                Assert.assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag, eligibilityUpdateEvent + " is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId.get() + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray subscriberJson = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(subscriberJson.get(0).toString());
        Assert.assertEquals(temp.getString("eventName").trim(), eligibilityUpdateEvent);
        Assert.assertEquals(temp.getInt("eventId") + "", eventId.get());
        Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId.get());
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
}
