package com.maersk.crm.api_step_definitions;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class APIEligibilityController extends CRMUtilities implements ApiBase {

    private String eligibilityBaseURI = ConfigurationReader.getProperty("apiEligibilityURI");
    private String getEligibilityListByUser = "mars/eb/eligibilities";

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String getEvents = "app/crm/events?size=10000&page=0";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    private final ThreadLocal<String> getEligibilityByConsumerId = ThreadLocal.withInitial(()->"/mars/eb/eligibilities/{consumerId}");
    private final ThreadLocal<String> getEligibilitySegmentsByConsumerId = ThreadLocal.withInitial(()->"/mars/eb/eligibilities/{consumerId}/segments");
    private final ThreadLocal<String> getEligibilityProcessIndicatorByConsumerId = ThreadLocal.withInitial(()->"/mars/eb/eligibilities/hrecip?consumerId={consumerId}");
    private String createSpecialCoverageLevelOfCareByConsumerId = "/mars/eb/eligibilities/segments";

    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonObject> requestParamsForEvent = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonObject> eligibilityRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <JsonArray> requestParamsArray = ThreadLocal.withInitial(JsonArray::new);

    /* private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
     private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
     private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
     private APITMEventController apitmEventController = new APITMEventController();*/
    ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();

    private final ThreadLocal <String> subscriberName = ThreadLocal.withInitial(()-> "DPBI");
    public final ThreadLocal <String> eventId = ThreadLocal.withInitial(()-> "");
    private final ThreadLocal <String> subscriberId = ThreadLocal.withInitial(()-> "");
    private final ThreadLocal <JSONArray> subscriberList = ThreadLocal.withInitial(JSONArray::new);
    private final ThreadLocal <String> consumerSSN = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> consumerId = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal <String> programType = ThreadLocal.withInitial(()->"M");
    private final ThreadLocal <String> eligibilityType = ThreadLocal.withInitial(()->null);
    private final ThreadLocal <String> caseId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> subProgramTypeCode = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> traceIdTOCheckEvents = ThreadLocal.withInitial(String::new);

    @Given("I initiated Eligibility Create API")
    public void initiatedEligibilityCreateAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eligibilityBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEligibilityListByUser);
    }

    @When("I run the Eligibility Create API")
    public void runEligibilityCreateAPI() {
        apitdu = apitdu.getJsonFromFile("crm/eligibility/eligibility.json");
        JsonObject eligibility = apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        if (consumerId.get() == null)
            eligibility.getAsJsonObject("coreEligibility").addProperty("consumerId", Integer.parseInt(apitdu.getRandomNumber(7).randomNumber));
        else
            eligibility.getAsJsonObject("coreEligibility").addProperty("consumerId", Integer.parseInt(consumerId.get()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String startDate = "";
        String endDate = "";
        if (eligibilityType.get() != null) {
            switch (eligibilityType.get()) {
                case "current":
                    startDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 0);
                    endDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 1);
                    break;
                case "future":
                    startDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 1);
                    endDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 3);
                    break;
                case "1stDayofNextMonth":
                    startDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0);
                    endDate = (String) null;
                    break;
                case "1stDayofLastMonth":
                    startDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToReduceForTheFirstOfThisMonth(), -1, 0);
                    endDate = (String) null;
                    break;
                case "1stDayofPresentMonth":
                    startDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToReduceForTheFirstOfThisMonth(), 0, 0);
                    endDate = (String) null;
                    break;

            }
        } else {
            startDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 1);
            endDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 3);
        }


       /* JsonObject futureEligibility = new JsonObject();

        futureEligibility.addProperty("startDate", currentDate);
        futureEligibility.addProperty("endDate", (String) null);
        futureEligibility.addProperty("categoryCode", "AA001ne");
        futureEligibility.addProperty("coverageCode", "BB001ne6");
        futureEligibility.addProperty("exemptionCode", "CC001ne7");
        futureEligibility.addProperty("programCode", "P");
        futureEligibility.addProperty("eligibilityStatusCode", "EE010808");
        eligibility.add("futureEligibility", futureEligibility);*/
        eligibility.getAsJsonObject("coreEligibility").addProperty("startDate", startDate);
        eligibility.getAsJsonObject("coreEligibility").addProperty("endDate", endDate);
        eligibility.getAsJsonObject("coreEligibility").addProperty("correlationId", apitdu.getRandomNumber(16).randomNumber.toString());
        eligibility.getAsJsonObject("coreEligibility").addProperty("uiid", apitdu.getRandomNumber(10).randomNumber.toString());
        eligibility.getAsJsonObject("coreEligibility").addProperty("programCode", programType.get());


        eligibility.getAsJsonObject("facilityInfo").addProperty("startDate", startDate);
        eligibility.getAsJsonObject("facilityInfo").addProperty("endDate", endDate);

        eligibility.getAsJsonObject("hospiceInfo").addProperty("startDate", startDate);
        eligibility.getAsJsonObject("hospiceInfo").addProperty("endDate", endDate);

        eligibility.getAsJsonObject("financialInfo").addProperty("startDate", startDate);
        eligibility.getAsJsonObject("financialInfo").addProperty("endDate", endDate);

        eligibility.getAsJsonObject("waiverInfo").addProperty("startDate", startDate);
        eligibility.getAsJsonObject("waiverInfo").addProperty("endDate", endDate);

        eligibility.getAsJsonObject("medicareInfo").addProperty("partAStartDate", startDate);
        eligibility.getAsJsonObject("medicareInfo").addProperty("partBStartDate", startDate);
        eligibility.getAsJsonObject("medicareInfo").addProperty("partDStartDate", startDate);
        eligibility.getAsJsonObject("medicareInfo").addProperty("titleXVIIIStartDate", startDate);

        eligibility.getAsJsonObject("thirdPartyInsuranceInfo").addProperty("startDate", startDate);

        eligibility.getAsJsonObject("specialPopulationInfo").addProperty("startDate", startDate);


        eligibility.getAsJsonObject("medicareInfo").addProperty("partAEndDate", endDate);
        eligibility.getAsJsonObject("medicareInfo").addProperty("partBEndDate", endDate);
        eligibility.getAsJsonObject("medicareInfo").addProperty("partDEndDate", endDate);

        eligibility.getAsJsonObject("longTermCareInfo").addProperty("startDate", startDate);
        eligibility.getAsJsonObject("longTermCareInfo").addProperty("endDate", endDate);

        eligibilityRequest.set(eligibility);
        JsonArray requestParam = new JsonArray();
        requestParam.add(eligibility);
        System.out.println(requestParam.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParam.toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("SUCCESS"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "SUCCESS");
        subProgramTypeCode.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").get("subProgramTypeCode").getAsString());
    }

    @Then("I will capture the eligibility data")
    public void captureTheEligibilityData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> coreEligibility = (HashMap<String, Object>) eligibilityResponseMap.get("coreEligibility");

        String startDate = eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("startDate").getAsString();
        String responseStartDate = coreEligibility.get("startDate").toString().split("T")[0];

        assertEquals(responseStartDate, startDate);
        assertEquals(coreEligibility.get("categoryCode").toString(), eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("categoryCode").getAsString());
        assertEquals(coreEligibility.get("programCode").toString(), eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("programCode").getAsString());
        assertEquals(coreEligibility.get("coverageCode").toString(), eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("coverageCode").getAsString());
        assertEquals(coreEligibility.get("eligibilityStatusCode").toString(), eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("eligibilityStatusCode").getAsString());
        assertEquals(coreEligibility.get("exemptionCode").toString(), eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("exemptionCode").getAsString());

      /*  JsonElement futureEligibility = requestParams.get("futureEligibility");
        String futureEligibilityStartDate = futureEligibility.getAsJsonObject().get("startDate").getAsString();
        String futureEligibilityResponseStartDate = convertMilliSecondsToDateFormatMMddyyyy(api.jsonPathEvaluator.getJsonObject("eligibility.futureEligibility.startDate"));

        assertEquals(futureEligibilityResponseStartDate, futureEligibilityStartDate);
        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.futureEligibility.categoryCode").toString(), futureEligibility.getAsJsonObject().get("categoryCode").getAsString());
        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.futureEligibility.programCode").toString(), futureEligibility.getAsJsonObject().get("programCode").getAsString());
        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.futureEligibility.coverageCode").toString(), futureEligibility.getAsJsonObject().get("coverageCode").getAsString());
        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.futureEligibility.eligibilityStatusCode").toString(), futureEligibility.getAsJsonObject().get("eligibilityStatusCode").getAsString());
        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.futureEligibility.exemptionCode").toString(), futureEligibility.getAsJsonObject().get("exemptionCode").getAsString());*/
    }

    @Then("I will capture the long term care data")
    public void captureTheLongTermCareData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> longTermCareInfo = (HashMap<String, Object>) eligibilityResponseMap.get("longTermCareInfo");

        String startDate = eligibilityRequest.get().get("longTermCareInfo").getAsJsonObject().get("startDate").getAsString().split("T")[0];
        String responseStartDate = longTermCareInfo.get("startDate").toString().split("T")[0];

        assertEquals(responseStartDate, startDate);
        assertEquals(longTermCareInfo.get("providerName").toString(), eligibilityRequest.get().get("longTermCareInfo").getAsJsonObject().get("providerName").getAsString());
        assertEquals(longTermCareInfo.get("coverageCode").toString(), eligibilityRequest.get().get("longTermCareInfo").getAsJsonObject().get("coverageCode").getAsString());
        assertEquals(longTermCareInfo.get("npiId").toString(), eligibilityRequest.get().get("longTermCareInfo").getAsJsonObject().get("npiId").getAsString());
    }

    @Then("I will capture the facility placement data")
    public void captureTheFacilityPlacementData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> facilityInfo = (HashMap<String, Object>) eligibilityResponseMap.get("facilityInfo");

        String startDate = eligibilityRequest.get().get("facilityInfo").getAsJsonObject().get("startDate").getAsString();
        String responseStartDate = facilityInfo.get("startDate").toString().split("T")[0];

        assertEquals(responseStartDate, startDate);
        assertEquals(facilityInfo.get("placementCode").toString(), eligibilityRequest.get().get("facilityInfo").getAsJsonObject().get("placementCode").getAsString());
        assertEquals(facilityInfo.get("placementCountyCode").toString(), eligibilityRequest.get().get("facilityInfo").getAsJsonObject().get("placementCountyCode").getAsString());
        assertEquals(facilityInfo.get("referralCountyCode").toString(), eligibilityRequest.get().get("facilityInfo").getAsJsonObject().get("referralCountyCode").getAsString());
    }

    @Then("I will capture the hospice data")
    public void captureTheHospiceData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> hospiceInfo = (HashMap<String, Object>) eligibilityResponseMap.get("hospiceInfo");

        String startDate = eligibilityRequest.get().get("hospiceInfo").getAsJsonObject().get("startDate").getAsString();
        String responseStartDate = hospiceInfo.get("startDate").toString().split("T")[0];

        assertEquals(responseStartDate, startDate);
        assertEquals(hospiceInfo.get("hospiceIndicator").toString(), eligibilityRequest.get().get("hospiceInfo").getAsJsonObject().get("hospiceIndicator").getAsString());
    }

    @Then("I will capture the Medicare data")
    public void captureTheMedicareData() {

        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> medicareInfo = (HashMap<String, Object>) eligibilityResponseMap.get("medicareInfo");

        assertEquals(medicareInfo.get("partAStartDate").toString().split("T")[0], eligibilityRequest.get().get("medicareInfo").getAsJsonObject().get("partAStartDate").getAsString());
        if (medicareInfo.get("partBStartDate") != null)
            assertEquals(medicareInfo.get("partBStartDate").toString().split("T")[0], eligibilityRequest.get().get("medicareInfo").getAsJsonObject().get("partBStartDate").getAsString());
        assertEquals(medicareInfo.get("partDStartDate").toString().split("T")[0], eligibilityRequest.get().get("medicareInfo").getAsJsonObject().get("partDStartDate").getAsString());
        if (medicareInfo.get("titleXVIIIStartDate") != null)
            assertEquals(medicareInfo.get("titleXVIIIStartDate").toString().split("T")[0], eligibilityRequest.get().get("medicareInfo").getAsJsonObject().get("titleXVIIIStartDate").getAsString());
        assertEquals(medicareInfo.get("titleXVIII").toString(), eligibilityRequest.get().get("medicareInfo").getAsJsonObject().get("titleXVIII").getAsString());
    }

    @Then("I will capture the third party insurance data")
    public void captureTheThirdPartyInsuranceData() {

        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> thirdParyInsuranceInfo = (HashMap<String, Object>) eligibilityResponseMap.get("thirdPartyInsuranceInfo");
        if (thirdParyInsuranceInfo.get("startDate") == null)
            assertEquals(thirdParyInsuranceInfo.get("startDate").toString().split("T")[0], eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("startDate").getAsString());

        assertEquals(thirdParyInsuranceInfo.get("insuranceSource").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insuranceSource").getAsString());
        assertEquals(thirdParyInsuranceInfo.get("insuranceCode").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insuranceCode").getAsString());
        assertEquals(thirdParyInsuranceInfo.get("insuranceName").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insuranceName").getAsString());
        assertEquals(thirdParyInsuranceInfo.get("insurancePolicyHolderName").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insurancePolicyHolderName").getAsString());
        assertEquals(thirdParyInsuranceInfo.get("insuranceGroupNumber").toString(), eligibilityRequest.get().get("thirdPartyInsuranceInfo").getAsJsonObject().get("insuranceGroupNumber").getAsString());
    }

    @Then("I will capture the waiver data")
    public void captureTheWaiverData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> waiverInfo = (HashMap<String, Object>) eligibilityResponseMap.get("waiverInfo");

        assertEquals(waiverInfo.get("startDate").toString().split("T")[0], eligibilityRequest.get().get("waiverInfo").getAsJsonObject().get("startDate").getAsString());

        assertEquals(waiverInfo.get("waiverCode").toString(), eligibilityRequest.get().get("waiverInfo").getAsJsonObject().get("waiverCode").getAsString());
        assertEquals(waiverInfo.get("waiverCounty").toString(), eligibilityRequest.get().get("waiverInfo").getAsJsonObject().get("waiverCounty").getAsString());
    }

    @Then("I will capture the special population data")
    public void captureTheSpecialPopulationData() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> specialPopulationInfo = (HashMap<String, Object>) eligibilityResponseMap.get("specialPopulationInfo");

        assertEquals(specialPopulationInfo.get("startDate").toString().split("T")[0], eligibilityRequest.get().get("specialPopulationInfo").getAsJsonObject().get("startDate").getAsString());
        assertEquals(specialPopulationInfo.get("specialEligibilityCode").toString(), eligibilityRequest.get().get("specialPopulationInfo").getAsJsonObject().get("specialEligibilityCode").getAsString());
        assertEquals(specialPopulationInfo.get("specialPopulationCaseNumber").toString(), eligibilityRequest.get().get("specialPopulationInfo").getAsJsonObject().get("specialPopulationCaseNumber").getAsString());
        assertEquals(specialPopulationInfo.get("specialPopulationIndicator").toString(), eligibilityRequest.get().get("specialPopulationInfo").getAsJsonObject().get("specialPopulationIndicator").getAsString());
    }

    @Then("I will capture the financial data")
    public void captureTheFinancialData() {
        //JsonElement financialInfo = requestParams.get("financialInfo");

//        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.financialInfo.costShareAmount").toString(), financialInfo.getAsJsonObject().get("costShareAmount").getAsString());
//        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.financialInfo.familySize").toString(), financialInfo.getAsJsonObject().get("familySize").getAsString());
//        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.financialInfo.fpil").toString(), financialInfo.getAsJsonObject().get("fpil").getAsString());
//        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.financialInfo.income").toString(), financialInfo.getAsJsonObject().get("income").getAsString());
//        assertEquals(api.jsonPathEvaluator.getJsonObject("eligibility.financialInfo.spendDownCode").toString(), financialInfo.getAsJsonObject().get("spendDownCode").getAsString());
    }

    @Then("I will verify an {string} for DPBI is created for eligibility create")
    public void publishEligibilitySaveEvent(String eligibilitySaveEvent) {

        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> coreEligibility = (HashMap<String, Object>) eligibilityResponseMap.get("coreEligibility");
        String correlationId = coreEligibility.get("correlationId").toString();
        JsonObject event = new JsonObject();
        event.addProperty("correlationId", correlationId);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal());
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
            if (eventsData.get("eventName").equals(eligibilitySaveEvent)) {
                assertEquals(eventsData.get("status"), "success");
                assertEquals(eventsData.get("correlationId"), correlationId);

                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);

        validateSubscriptionProcessEligibilityEvent(eligibilitySaveEvent);
    }

    @Then("I will check the date the eligibility record was created")
    public void recordTheDateTheRecordWasCreated() {
        ArrayList eligibilityArray = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        HashMap<String, Object> eligibilityResponseMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> coreEligibility = (HashMap<String, Object>) eligibilityResponseMap.get("coreEligibility");
        String responsecreatedOnDate = coreEligibility.get("createdOn").toString();
        String createdOnDate = eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("createdOn").getAsString();


        assertEquals(coreEligibility.get("createdBy").toString(), eligibilityRequest.get().get("coreEligibility").getAsJsonObject().get("createdBy").getAsString());
        assertEquals(responsecreatedOnDate.split("T")[0], createdOnDate.split("T")[0]);
    }

    public static String convertMilliSecondsToDateFormatMMddyyyy(long milliseconds) {
        DateFormat simple = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(milliseconds);
        simple.setTimeZone(TimeZone.getTimeZone("GMT"));

        return simple.format(date);
    }

    public String convertMilliSecondsToCurrentDateFormatMMddyyyy(long milliseconds) {
        DateFormat simple = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(milliseconds);

        return simple.format(date);
    }

    public JsonArray generateCreateEligibilityRequest() {
        JsonObject request = new JsonObject();

        JsonObject coreEligibility = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String currentDate = dateFormat.format(todayDate);

        if (consumerId.get() == null)
            Integer.parseInt(apitdu.getRandomNumber(7).randomNumber);
        coreEligibility.addProperty("coreEligibilitySegmentsId", (String) null);
        coreEligibility.addProperty("consumerId", consumerId.get());
        coreEligibility.addProperty("startDate", currentDate);
        coreEligibility.addProperty("endDate", (String) null);
        coreEligibility.addProperty("categoryCode", "Alpha01n");
        coreEligibility.addProperty("coverageCode", "Beta01ne3");
        coreEligibility.addProperty("exemptionCode", "Delta01n");
        coreEligibility.addProperty("programCode", "M");
        coreEligibility.addProperty("eligibilityStatusCode", "ESTATUSC");
        coreEligibility.addProperty("consumerDeprivationCode", "DEPCODE");
        coreEligibility.addProperty("createdOn", currentDate);
        coreEligibility.addProperty("createdBy", "400");
        coreEligibility.addProperty("updatedOn", currentDate);
        coreEligibility.addProperty("updatedBy", "300");
        coreEligibility.addProperty("correlationId", apitdu.getRandomNumber(16).randomNumber.toString());
        coreEligibility.addProperty("uiid", apitdu.getRandomNumber(10).randomNumber.toString());

        JsonObject futureEligibility = new JsonObject();

        futureEligibility.addProperty("startDate", currentDate);
        futureEligibility.addProperty("endDate", (String) null);
        futureEligibility.addProperty("categoryCode", "AA001ne");
        futureEligibility.addProperty("coverageCode", "BB001ne6");
        futureEligibility.addProperty("exemptionCode", "CC001ne7");
        futureEligibility.addProperty("programCode", "P");
        futureEligibility.addProperty("eligibilityStatusCode", "EE010808");

        JsonObject longTermCareInfo = new JsonObject();

        longTermCareInfo.addProperty("startDate", currentDate);
        longTermCareInfo.addProperty("providerName", "providerName4");
        longTermCareInfo.addProperty("coverageCode", "coverageCode4");
        longTermCareInfo.addProperty("id_npi", "id_npi_0808");

        JsonObject facilityInfo = new JsonObject();

        facilityInfo.addProperty("startDate", currentDate);
        facilityInfo.addProperty("placementCode", "PP0808");
        facilityInfo.addProperty("placementCountyCode", "placementCounty0808");
        facilityInfo.addProperty("referralCountyCode", "referralCountyCod0808");

        JsonObject hospiceInfo = new JsonObject();

        hospiceInfo.addProperty("startDate", currentDate);
        hospiceInfo.addProperty("hospiceIndicator", "hospiceIndicator3");

        JsonObject waiverInfo = new JsonObject();

        waiverInfo.addProperty("startDate", currentDate);
        waiverInfo.addProperty("waiverCode", "waiverCode1");
        waiverInfo.addProperty("waiverCounty", "waiverCounty1");

        JsonObject medicareInfo = new JsonObject();

        medicareInfo.addProperty("partAStartDate", currentDate);
        medicareInfo.addProperty("partBStartDate", currentDate);
        medicareInfo.addProperty("partDStartDate", currentDate);
        medicareInfo.addProperty("titleXVIIIStartDate", currentDate);
        medicareInfo.addProperty("titleXVIII", "titleXVIII_created");

        JsonObject thirdParyInsuranceInfo = new JsonObject();

        thirdParyInsuranceInfo.addProperty("startDate", currentDate);
        thirdParyInsuranceInfo.addProperty("insuranceSource", "insuranceSource1");
        thirdParyInsuranceInfo.addProperty("insuranceCode", "insuranceCode1");
        thirdParyInsuranceInfo.addProperty("insuranceName", "insuranceName1");
        thirdParyInsuranceInfo.addProperty("insurancePolicyHolderName", "insurancePolicyHolderName1");
        thirdParyInsuranceInfo.addProperty("insuranceGroupNumber", "insuranceGroupNumber1");

        JsonObject specialPopulationInfo = new JsonObject();

        specialPopulationInfo.addProperty("startDate", currentDate);
        specialPopulationInfo.addProperty("specialEligibilityCode", "SPCLE001new1");
        specialPopulationInfo.addProperty("specialPopulationCaseNumber", "SPCLPCN002new");
        specialPopulationInfo.addProperty("specialPopulationIndicator", "SPCLPI002new11");

        request.add("coreEligibility", coreEligibility);
        request.add("futureEligibility", futureEligibility);
        request.add("longTermCareInfo", longTermCareInfo);
        request.add("facilityInfo", facilityInfo);
        request.add("hospiceInfo", hospiceInfo);
        request.add("financialInfo", null);
        request.add("waiverInfo", waiverInfo);
        request.add("medicareInfo", medicareInfo);
        request.add("thirdParyInsuranceInfo", thirdParyInsuranceInfo);
        request.add("specialPopulationInfo", specialPopulationInfo);
        JsonArray requestArray = new JsonArray();
        requestArray.add(request);
        return requestArray;
    }

    private void validateSubscriptionProcessEligibilityEvent(String eligibilitySaveEvent) {
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
            if (temp.get("eventName").toString().equals(eligibilitySaveEvent)) {
                Assert.assertEquals(temp.get("subscriberId").toString(), subscriberId.get(), "Subscriber Id is wrong");
                Assert.assertEquals(temp.get("eventName").toString(), eligibilitySaveEvent, "Event Name is wrong");
                Assert.assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag, eligibilitySaveEvent + " is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId.get() + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray subscriberJson = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(subscriberJson.get(0).toString());
        Assert.assertEquals(temp.getString("eventName"), eligibilitySaveEvent);
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

    @Given("I created a consumer for eligibility record")
    public void i_created_a_consumer_for_eligibility_record() {

        APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
        caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPI();

        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        caseLoader.runCaseLoaderEligibilityEnrollmentAPI();
        consumerSSN.set(caseLoader.getConsumerSSN());
        APIConsumerSearchController consumerSearch = new APIConsumerSearchController();
        consumerSearch.setConsumerSSN(consumerSSN.get());
        consumerSearch.initiatedCaseConsumerContactSearchAPI();
        consumerSearch.runConsumerSearchAPI("consumerSSN");
        consumerId.set(consumerSearch.getConsumerIdFromSearchResults());
    }

    @Given("I initiated get eligibility by consumer id {string}")
    public void initiatedGetEligibilityByConsumerID(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eligibilityBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEligibilityByConsumerId.get().replace("{consumerId}", consumerId));
    }

    @When("I run get eligibility api")
    public void runGetEligibilityByConsumerID() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Given("I initiated get eligibility segment by consumer id {string}")
    public void initiatedGetEligibilitySegmentByConsumerID(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        }
        getEligibilitySegmentsByConsumerId.set(getEligibilitySegmentsByConsumerId.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eligibilityBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEligibilitySegmentsByConsumerId.get());
    }

    @Then("I verify eligibility records are displayed for the consumer {string}")
    public void verifyEligibilityRecordsDisplayed(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        }
        ArrayList<String> consumerIds = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.coreEligibility.consumerId");
        assertEquals(consumerIds.get(0), Integer.parseInt(consumerId));
    }

    @Then("I verify eligibility segment records are displayed for the consumer {string}")
    public void verifyEligibilitySegmentRecordsDisplayed(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        }
        ArrayList<String> consumerIds = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.consumerId");
        assertEquals(consumerIds.get(0), Integer.parseInt(consumerId));
    }

    public void setConsumerId(String consumerId) {
        this.consumerId.set( consumerId );
    }

    public void runEligibilityCreateAPI(String otherSegments, String categoryCode) {
        apitdu = apitdu.getJsonFromFile("crm/eligibility/eligibility.json");
        JsonObject eligibility = apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        if (consumerId.get() == null)
            eligibility.getAsJsonObject("coreEligibility").addProperty("consumerId", Integer.parseInt(apitdu.getRandomNumber(7).randomNumber));
        else
            eligibility.getAsJsonObject("coreEligibility").addProperty("consumerId", Integer.parseInt(consumerId.get()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String startDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 1);
        eligibility.getAsJsonObject("coreEligibility").addProperty("startDate", startDate);
        eligibility.getAsJsonObject("coreEligibility").addProperty("endDate", "2022-12-12");
        eligibility.getAsJsonObject("coreEligibility").addProperty("correlationId", apitdu.getRandomNumber(16).randomNumber.toString());
        eligibility.getAsJsonObject("coreEligibility").addProperty("uiid", apitdu.getRandomNumber(10).randomNumber.toString());
        if (!categoryCode.isEmpty())
            eligibility.getAsJsonObject("coreEligibility").addProperty("categoryCode", categoryCode);

        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> mapObj = gson.fromJson(eligibility, type);
        for (String key : mapObj.keySet()) {
            if (key.equalsIgnoreCase("coreEligibility"))
                continue;
            if (otherSegments.contains(key)) {
                if (key.equalsIgnoreCase("medicareInfo")) {
                    eligibility.getAsJsonObject("medicareInfo").addProperty("partAStartDate", startDate);
                    eligibility.getAsJsonObject("medicareInfo").addProperty("partBStartDate", startDate);
                    eligibility.getAsJsonObject("medicareInfo").addProperty("partDStartDate", startDate);
                    eligibility.getAsJsonObject("medicareInfo").addProperty("titleXVIIIStartDate", (String) null);
                    eligibility.getAsJsonObject("medicareInfo").addProperty("partAEndDate", "2022-02-02");
                    eligibility.getAsJsonObject("medicareInfo").addProperty("partBEndDate", "2022-02-02");
                    eligibility.getAsJsonObject("medicareInfo").addProperty("partDEndDate", "2022-02-02");
                } else if (key.equalsIgnoreCase("financialInfo")) {
                    eligibility.getAsJsonObject(key).addProperty("caseId", caseId.get());
                    eligibility.getAsJsonObject(key).addProperty("startDate", startDate);
                    eligibility.getAsJsonObject(key).addProperty("endDate", (String) null);
                } else {
                    eligibility.getAsJsonObject(key).addProperty("startDate", startDate);
                    eligibility.getAsJsonObject(key).addProperty("endDate", (String) null);
                }

            } else {
                eligibility.addProperty(key, (String) null);
            }
        }

        eligibilityRequest.set(eligibility);
        JsonArray requestParam = new JsonArray();
        requestParam.add(eligibility);
        System.out.println(requestParam.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParam.toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("SUCCESS"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "SUCCESS");
    }

    @When("I provide information to eligibility update API")
    public void updatedEligibilityCreateAPIPayLoad(DataTable dataToUpdate) {
        Map<String, String> data = dataToUpdate.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            switch (key) {
                case "eligibilityStatusCode":
                    eligibilityRequest.get().getAsJsonObject("coreEligibility").addProperty("eligibilityStatusCode", data.get(key));
                    break;
                case "consumerId":
                    if (data.get(key).isEmpty())
                        eligibilityRequest.get().getAsJsonObject("coreEligibility").addProperty("consumerId", consumerId.get());
                    else
                        eligibilityRequest.get().getAsJsonObject("coreEligibility").addProperty("consumerId", data.get(key));
                    break;
            }

        }
        requestParamsArray.set(new JsonArray());
        requestParamsArray.get().add(eligibilityRequest.get());
        System.out.println(requestParamsArray.get());
    }

    @And("I run update eligibility API")
    public void runUpdateEligibilityAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsArray.toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        traceIdTOCheckEvents.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("SUCCESS"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "SUCCESS");
    }

    public void setProgramType(String programType) {
        this.programType.set(programType);
    }

    public void setCaseId(String caseId) {
        this.caseId.set(caseId);
    }

    public void setEligibilityType(String eligibilityType) { this.eligibilityType.set(eligibilityType);
    }

    @Then("I verify following fields are captured in the newly created Eligibility Record")
    public void I_verify_following_fields_are_captured_in_the_newly_created_Eligibility_Record(Map<String, String> data) {
        JsonArray eligibilities = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        if (data.containsKey("empty")) {
            assertEquals(eligibilities.size(), 0, "eligibilities segment is not empty! - FAIL!");
            return;
        }
        JsonObject latestEligibility = null;
        if (eligibilities.size() > 1) {
            List<String> times = new ArrayList<>();
            for (int i = 0; i < eligibilities.size(); i++) {
                times.add(eligibilities.get(i).getAsJsonObject().get("eligibilityStatus").getAsString());
            }
            if (times.containsAll(Arrays.asList("Active", "FUTURE"))) {
                latestEligibility = eligibilities.get(times.indexOf("FUTURE")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "PAST"))) {
                latestEligibility = eligibilities.get(times.indexOf("Active")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "Active"))) {
                if (eligibilities.get(0).getAsJsonObject().getAsJsonObject("coreEligibility").get("coreEligibilitySegmentsId").getAsInt() <
                        eligibilities.get(1).getAsJsonObject().getAsJsonObject("coreEligibility").get("coreEligibilitySegmentsId").getAsInt()) {
                    latestEligibility = eligibilities.get(1).getAsJsonObject();
                } else {
                    latestEligibility = eligibilities.get(0).getAsJsonObject();
                }
            }
        } else {
            latestEligibility = eligibilities.get(0).getAsJsonObject();
        }
        verifyEligibilityDetails(latestEligibility, data);
    }

    @Then("I verify following fields are captured in previously created Eligibility Record")
    public void I_verify_following_fields_are_captured_in_previously_created_Eligibility_Record(Map<String, String> data) {
        JsonArray eligibilities = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        JsonObject previousEligibility = null;
        if (eligibilities.size() > 1) {
            List<String> times = new ArrayList<>();
            for (int i = 0; i < eligibilities.size(); i++) {
                times.add(eligibilities.get(i).getAsJsonObject().get("eligibilityStatus").getAsString());
            }
            if (times.containsAll(Arrays.asList("Active", "FUTURE"))) {
                previousEligibility = eligibilities.get(times.indexOf("Active")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "PAST"))) {
                previousEligibility = eligibilities.get(times.indexOf("PAST")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "Active"))) {
                if (eligibilities.get(0).getAsJsonObject().getAsJsonObject("coreEligibility").get("coreEligibilitySegmentsId").getAsInt() <
                        eligibilities.get(1).getAsJsonObject().getAsJsonObject("coreEligibility").get("coreEligibilitySegmentsId").getAsInt()) {
                    previousEligibility = eligibilities.get(0).getAsJsonObject();
                } else {
                    previousEligibility = eligibilities.get(1).getAsJsonObject();
                }
            }
        } else {
            previousEligibility = eligibilities.get(0).getAsJsonObject();
        }
        verifyEligibilityDetails(previousEligibility, data);
    }

    public void verifyEligibilityDetails(JsonObject eligibilityData, Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "categoryCode":
                case "programCode":
                case "subProgramTypeCode":
                case "coverageCode":
                case "eligibilityStatusCode":
                case "exemptionCode":
                case "genericFieldText1":
                case "genericFieldText2":
                case "createdBy":
                case "updatedBy":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue((eligibilityData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get(key).isJsonNull()), key + " is not null! - FAIL!");
                    } else {
                        Assert.assertEquals(eligibilityData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get(key).getAsString(), data.get(key), key + " mismatch!");
                    }
                    break;
                case "eligibilityStatus":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(eligibilityData.getAsJsonObject().get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        Assert.assertEquals(eligibilityData.getAsJsonObject().get(key).getAsString(), data.get(key), key + " mismatch! - FAIL");
                    }
                    break;
                case "eligibilityStartDate":
                    String eligibilityStartDate = apitdu.getStartDate(data.get(key));
                    Assert.assertEquals(eligibilityData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get("startDate").getAsString(), eligibilityStartDate, key + " mismatch!" );
                    break;
                case "eligibilityEndDate":
                    if (!data.get("eligibilityEndDate").equals("null")) {
                        String eligibilityEndDate = apitdu.getEndDate(data.get("eligibilityEndDate").toString());
                        Assert.assertEquals(eligibilityData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get("endDate").getAsString(), eligibilityEndDate, key + " mismatch!");
                    } else
                        Assert.assertTrue(eligibilityData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get("endDate").isJsonNull());
                    break;
                case "createdOn":
                    Assert.assertEquals(eligibilityData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get("createdOn").getAsString().substring(0, 10), apitdu.getCurrentDate("yyyy-MM-dd"));
                    break;
                case "updatedOn":
                    Assert.assertEquals(eligibilityData.getAsJsonObject().get("coreEligibility").getAsJsonObject().get("updatedOn").getAsString().substring(0, 10), apitdu.getCurrentDate("yyyy-MM-dd"));
                    break;
            }
        }

    }

    @Then("I verify that following fields are captured in the newly created Eligibility Record")
    public void verifyPartOfEligibilityDetails(DataTable dataTable) {
        Map<Object, Object> data = dataTable.asMap((Type) String.class, String.class);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("programCode").getAsString(), data.get("programCode"));
        String eligibilityStartDate = apitdu.getStartDate(data.get("eligibilityStartDate").toString());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("startDate").getAsString(), eligibilityStartDate);
        if (data.containsKey("eligibilityEndDate")) {
            String eligibilityEndDate = apitdu.getEndDate(data.get("eligibilityEndDate").toString());
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("endDate").getAsString(), eligibilityEndDate);
        }
        if (data.containsKey("createdBy")) {
            Assert.assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("createdBy").getAsString());
        }
        if (data.containsKey("updatedBy")) {
            Assert.assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("coreEligibility").getAsJsonObject().get("updatedBy").getAsString());
        }


    }

    @Then("I verfify other eiigbility segment type {string} details for coma separated consumerIds {string} with data")
    public void I_verfify_other_eiigbility_segment_details_for_consumerIds_with_data(String segmentTypeCode, String comaSeparatedConsumerIds, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eligibilityBaseURI);
        String otherEligibilitySegmentsEndPoint = getEligibilitySegmentsByConsumerId.get();
        if (comaSeparatedConsumerIds.isEmpty()) {
            otherEligibilitySegmentsEndPoint = otherEligibilitySegmentsEndPoint.replace("{consumerId}", consumerId.get());
        } else {
            int i = 0;
            String consumerIds = "";
            for (String each : comaSeparatedConsumerIds.split(",")) {
                if (i == 0) {
                    consumerIds = consumerIds + String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(each)).replace(".0", "");
                } else {
                    consumerIds = consumerIds + "," + String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(each)).replace(".0", "");
                }
                i++;
            }
            otherEligibilitySegmentsEndPoint = otherEligibilitySegmentsEndPoint.replace("{consumerId}", consumerIds);
        }
        if (segmentTypeCode.isEmpty() || segmentTypeCode.equalsIgnoreCase("ALL")) {
            otherEligibilitySegmentsEndPoint = otherEligibilitySegmentsEndPoint + "/v2";
        } else {
            otherEligibilitySegmentsEndPoint = otherEligibilitySegmentsEndPoint + "/v2?segmenttypecode=" + segmentTypeCode.toUpperCase();
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(otherEligibilitySegmentsEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        JsonArray otherEligbilitySegments = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();

        if (data.containsKey("empty")) {
            assertEquals(otherEligbilitySegments.size(), 0, "other eiigbility segment is not empty! - FAIL!");
            return;
        }

        for (String key : data.keySet()) {
            int i = Integer.parseInt(key.substring(1, 2));
            String field = key.split("\\.", 2)[1];
            switch (field) {
                case "otherEligbilitySegmentsId":
                    System.out.println("otherEligbilitySegmentsId is " + otherEligbilitySegments.get(i).getAsJsonObject().get("otherEligbilitySegmentsId").toString().replaceAll("\"", ""));
                    assertFalse(otherEligbilitySegments.get(i).getAsJsonObject().get("otherEligbilitySegmentsId").isJsonNull());
                    break;
                case "startDate":
                case "segmentDetailValue3":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(otherEligbilitySegments.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(otherEligbilitySegments.get(i).getAsJsonObject().get(field).getAsString(), apitdu.getStartDate(data.get(key)), key + " mismatch!");
                    }
                    break;
                case "endDate":
                case "segmentDetailValue4":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(otherEligbilitySegments.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(otherEligbilitySegments.get(i).getAsJsonObject().get(field).getAsString(), apitdu.getEndDate(data.get(key)), key + " mismatch!");
                    }
                    break;
                case "createdOn":
                case "updatedOn":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(otherEligbilitySegments.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(otherEligbilitySegments.get(i).getAsJsonObject().get(field).getAsString().substring(0, 10), apitdu.getStartDate(data.get(key)), key + " mismatch!");
                    }
                    break;
                case "consumerId":
                    if (comaSeparatedConsumerIds.isEmpty()) {
                        assertEquals(otherEligbilitySegments.get(i).getAsJsonObject().get(field).getAsString(),
                                consumerId, key + " mismatch!");
                    } else {
                        assertEquals(otherEligbilitySegments.get(i).getAsJsonObject().get(field).getAsString(),
                                String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))).replace(".0", ""), key + " mismatch!");
                    }

                    break;
                default:
                    // all simple field validations here
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(otherEligbilitySegments.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(otherEligbilitySegments.get(i).getAsJsonObject().get(field).getAsString(), data.get(key), key + " mismatch!");
                    }
            }
        }
    }


    @When("I update consumers eligibility by consumerId {string} with data")
    public void I_update_consumers_enrollment_by_consumerId_with_data(String consumerId, Map<String, String> data) {
        initiatedGetEligibilityByConsumerID(consumerId);
        runGetEligibilityByConsumerID();
        JsonArray eligibilityRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        int maxNum = 0;
        for (String each : data.keySet()) {
            maxNum = Math.max(Integer.parseInt(each.substring(1, 2)), maxNum);
        }
        List<String> updatedEligibility = new ArrayList<>();
        for (int j = 0; j < eligibilityRecords.size(); j++) {
            JsonObject eligibilityRecord = eligibilityRecords.get(j).getAsJsonObject();
            boolean eligibilityFound = false;
            for (int i = 0; i <= maxNum; i++) {
                if (updatedEligibility.contains(Integer.toString(i))) continue;
                if (data.containsKey("[" + i + "].eligibilityStatus")
                        && data.get("[" + i + "].eligibilityStatus").equals(eligibilityRecord.get("eligibilityStatus").toString().replaceAll("\"", ""))) {
                    eligibilityFound = true;
                    updatedEligibility.add(Integer.toString(i));
                    System.out.println("Updating eligibility by eligibilityStatus " + data.get("[" + i + "].eligibilityStatus"));
                    System.out.println("i = " + i);
                    if (data.containsKey("[" + i + "].startDate")) {
                        if (data.get("[" + i + "].startDate").isEmpty() || data.get("[" + i + "].startDate").contains("null")) {
                            eligibilityRecord.getAsJsonObject("coreEligibility").add("startDate", null);
                        } else {
                            eligibilityRecord.getAsJsonObject("coreEligibility").addProperty("startDate", apitdu.getStartDate(data.get("[" + i + "].startDate")));
                        }
                    }
                    if (data.containsKey("[" + i + "].endDate")) {
                        if (data.get("[" + i + "].endDate").isEmpty() || data.get("[" + i + "].endDate").contains("null")) {
                            eligibilityRecord.getAsJsonObject("coreEligibility").add("endDate", null);
                        } else {
                            eligibilityRecord.getAsJsonObject("coreEligibility").addProperty("endDate", apitdu.getEndDate(data.get("[" + i + "].endDate")));
                        }
                    }
                }
            }
            assertTrue(eligibilityFound,
                    eligibilityRecord.get("eligibilityStatus").toString().replaceAll("\"", "") + " is expected but not found in eligibility records - FAIL!");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eligibilityBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEligibilityByConsumerId.get().replace("/{consumerId}", ""));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(eligibilityRecords.toString());
        for (int i = 0; i <= maxNum; i++) {
            if (!updatedEligibility.contains(Integer.toString(i))) {
                fail("[" + i + "].eligibilityStatus = " + data.get("[" + i + "].eligibilityStatus") + " is missing in eligibility status! - FAIL!");
            }
        }
    }


    @Then("I verify hrecip eligibility information by consumerId {string} with data")
    public void I_verify_process_indicator_information_with_data(String consumerId, Map<String, String> data) {

        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eligibilityBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEligibilityProcessIndicatorByConsumerId.get().replace("{consumerId}", consumerId));

        runGetEligibilityByConsumerID();

        JsonArray processIndicatorRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        JsonObject processIndicatorRecord = new JsonObject();
        if (processIndicatorRecords.size() == 1) {
            processIndicatorRecord = processIndicatorRecords.get(0).getAsJsonObject();
        } else {
            if (data.containsKey("empty")) {
                assertEquals(processIndicatorRecords.size(), 0, "Process Indicator is not empty!");
                return;
            }
        }
        Common_Steps_I_verify_process_indicator_information_with_data(processIndicatorRecord, data);
    }


    public void Common_Steps_I_verify_process_indicator_information_with_data(JsonObject dataObject, Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "createdOn":
                case "updatedOn":
                    if (data.get(key).isEmpty() || data.get(key).equalsIgnoreCase("null")) {
                        assertTrue(dataObject.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        Assert.assertEquals(dataObject.get(key).getAsString().substring(0, 10), apitdu.getStartDate(data.get(key)));
                    }
                    break;
                case "programCode":
                case "hipStatus":
                case "eligibilityStatusCode":
                case "processInd":
                default:
                    if (data.get(key).isEmpty() || data.get(key).equalsIgnoreCase("null")) {
                        assertTrue(dataObject.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(dataObject.get(key).getAsString(), data.get(key), key + " mismatch!");
                    }
            }
        }
    }



    @Given("I add level of care with data")
    public void initiatedAddLevelOfCareByConsumerID(Map<String, String> data) {
        if (consumerId.get().isEmpty()) {
            consumerId.set(this.consumerId.get());
        } else {
            consumerId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId.get()).toString().replace(".0", ""));
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eligibilityBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createSpecialCoverageLevelOfCareByConsumerId);
        JsonObject levelOfCare = new JsonObject();
        levelOfCare.getAsJsonObject().addProperty("consumerId", Integer.parseInt(consumerId.get()));
        if(data.containsKey("facilityInfo")) {
            JsonObject facilityInfo = new JsonObject();
            facilityInfo.addProperty("startDate", apitdu.getStartDate(data.get("facilityInfo.startDate")));
            facilityInfo.addProperty("placementCode", data.get("facilityInfo.placementCode"));
            facilityInfo.addProperty("placementCountyCode", data.get("facilityInfo.placementCountyCode"));
            facilityInfo.addProperty("endDate", data.get("facilityInfo.endDate").equals("null") ? null : apitdu.getEndDate(data.get("facilityInfo.endDate")));
            levelOfCare.getAsJsonObject().add("facilityInfo",facilityInfo);
        }
        if(data.containsKey("hospiceInfo")) {
            JsonObject hospiceInfo = new JsonObject();
            hospiceInfo.addProperty("hospiceIndicator", data.get("hospiceInfo.hospiceIndicator"));
            hospiceInfo.addProperty("startDate", apitdu.getStartDate(data.get("hospiceInfo.startDate")));
            hospiceInfo.addProperty("endDate", data.get("hospiceInfo.endDate").equals("null") ? null : apitdu.getEndDate(data.get("hospiceInfo.endDate")));
            levelOfCare.getAsJsonObject().add("hospiceInfo",hospiceInfo);
        }
        if(data.containsKey("waiverInfo")) {
            JsonObject waiverInfo = new JsonObject();
            waiverInfo.addProperty("startDate", apitdu.getStartDate(data.get("waiverInfo.startDate")));
            waiverInfo.addProperty("waiverCode", data.get("waiverInfo.waiverCode"));
            waiverInfo.addProperty("waiverCounty", data.get("waiverInfo.waiverCounty"));
            waiverInfo.addProperty("endDate", data.get("waiverInfo.endDate").equals("null") ? null : apitdu.getEndDate(data.get("waiverInfo.endDate")));
            levelOfCare.getAsJsonObject().add("waiverInfo",waiverInfo);
        }
        System.out.println(levelOfCare.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody("["+levelOfCare.toString()+"]");
    }






}
