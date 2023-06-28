package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class APIEnrollmentUpdateController extends CRMUtilities implements ApiBase {
    private final ThreadLocal<String> enrollmentUpdateBaseURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEligibilityURI"));
    private final ThreadLocal<String> getEnrollmentUpdateListByUser = ThreadLocal.withInitial(()->"mars/eb/enrollment");
    private final ThreadLocal<String> eventURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEventsURI"));
    private final ThreadLocal<String> getEvents = ThreadLocal.withInitial(()->"app/crm/events?size=10000&page=0");
    private final ThreadLocal<String> subscribersEndPoint = ThreadLocal.withInitial(()->"/app/crm/es/event/subscribers");
    private final ThreadLocal<String> subscribersRecordEndPoint = ThreadLocal.withInitial(()->"/app/crm/es/subscriber/records/");
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsForEvent = ThreadLocal.withInitial(JsonObject::new);

    /*private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/
    private final ThreadLocal<String> subscriberName = ThreadLocal.withInitial(()->"DPBI");

    public final ThreadLocal<String> eventId=ThreadLocal.withInitial(()->"");
    private final ThreadLocal<String> subscriberId=ThreadLocal.withInitial(()->"");
    private final ThreadLocal<JSONArray> subscriberList = ThreadLocal.withInitial(JSONArray::new);

    @Given("I initiated Enrollment Update API")
    public void initiatedEnrollmentUpdateAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentUpdateBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentUpdateListByUser.get());
    }

    @When("I run the Enrollment Update API")
    public void runEnrollmentUpdateAPI() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/enrollment/enrollmentUpdate.json");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I will verify the Enrollment data updated")
    public void verifyTheEnrollmentDataUpdated() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String responseAnniversaryDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.anniversaryDate").toString();
        String responseOpenEnrollmentStartDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.openEnrollmentStartDate").toString();
        String responseOpenEnrollmentEndDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.openEnrollmentEndDate").toString();
        String responseLockInStartDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.lockInStartDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.serviceRegionCode").toString(), requestParams.get().get("serviceRegionCode").getAsString());
        assertEquals(responseAnniversaryDate, requestParams.get().get("anniversaryDate").getAsString());
        assertEquals(responseOpenEnrollmentStartDate, requestParams.get().get("openEnrollentStartDate").getAsString());
        assertEquals(responseOpenEnrollmentEndDate, requestParams.get().get("openEnrollentEndDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.openEnrollentStatus").toString(), requestParams.get().get("openEnrollentStatus").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.lockInStatusCode").toString(), requestParams.get().get("lockInStatusCode").getAsString());
        assertEquals(responseLockInStartDate, requestParams.get().get("lockInStartDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());
    }

    @Then("I will verify the Medical plan data updated")
    public void verifyMedicalDataUpdated() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String responseReinstatementDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.reinstatementDate").toString();
        String responseRetroactiveDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.retroactiveDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.autoAssignIndicator").toString(), requestParams.get().get("autoAssignIndicator").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.planCode").toString(), requestParams.get().get("planCode").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.planEndDateReason").toString(), requestParams.get().get("planEndDateReason").getAsString());
        assertEquals(responseReinstatementDate, requestParams.get().get("reinstatementDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.reinstatementIndicator").toString(), requestParams.get().get("reinstatementIndicator").getAsString());
        assertEquals(responseRetroactiveDate, requestParams.get().get("retroactiveDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.retroactiveIndicator").toString(), requestParams.get().get("retroactiveIndicator").getAsString());
    }

    @Then("I will verify the Provider data updated")
    public void verifyProviderDataUpdated() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        JsonElement enrollmentProviderInfo = requestParams.get().get("enrollmentProviderInfo");

        String responseProviderEndDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerEndDate").toString();
        String responseProviderStartDate =  API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerStartDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerType").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerType").getAsString());
        assertEquals(responseProviderEndDate, enrollmentProviderInfo.getAsJsonObject().get("providerEndDate").getAsString());
        assertEquals(responseProviderStartDate, enrollmentProviderInfo.getAsJsonObject().get("providerStartDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerId").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerId").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerPhoneNumber").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerPhoneNumber").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerFirstName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerFirstName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerLastName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerLastName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerMiddleName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerMiddleName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerNpi").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerNpi").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressLine1").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressLine1").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressLine2").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressLine2").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressCity").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressCity").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressCounty").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressCounty").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressState").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressState").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerZipCodeMain").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerZipCodeMain").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerZipCodeExtn").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerZipCodeExtn").getAsString());
    }

    @Then("I will verify the Dental plan data updated")
    public void verifyDentalDataUpdated() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String responseReinstatementDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.reinstatementDate").toString();
        String responseRetroactiveDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.retroactiveDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.autoAssignIndicator").toString(), requestParams.get().get("autoAssignIndicator").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.planCode").toString(), requestParams.get().get("planCode").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.planEndDateReason").toString(), requestParams.get().get("planEndDateReason").getAsString());
        assertEquals(responseReinstatementDate, requestParams.get().get("reinstatementDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.reinstatementIndicator").toString(), requestParams.get().get("reinstatementIndicator").getAsString());
        assertEquals(responseRetroactiveDate, requestParams.get().get("retroactiveDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.retroactiveIndicator").toString(), requestParams.get().get("retroactiveIndicator").getAsString());
    }

    @Then("I will verify the Dentist Information Updated")
    public void verifyDentistInfoUpdated() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        JsonElement enrollmentProviderInfo = requestParams.get().get("enrollmentProviderInfo");

        String responseProviderEndDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerEndDate").toString();
        String responseProviderStartDate =  API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerStartDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerType").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerType").getAsString());
        assertEquals(responseProviderEndDate, enrollmentProviderInfo.getAsJsonObject().get("providerEndDate").getAsString());
        assertEquals(responseProviderStartDate, enrollmentProviderInfo.getAsJsonObject().get("providerStartDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerId").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerId").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerPhoneNumber").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerPhoneNumber").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerFirstName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerFirstName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerLastName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerLastName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerMiddleName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerMiddleName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerNpi").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerNpi").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressLine1").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressLine1").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressLine2").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressLine2").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressCity").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressCity").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressCounty").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressCounty").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerAddressState").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressState").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerZipCodeMain").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerZipCodeMain").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentProviderInfo.providerZipCodeExtn").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerZipCodeExtn").getAsString());
    }

    @Then("I will verify the Behavioral health data updated")
    public void verifyBehavioralDataUpdated() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String responseReinstatementDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.reinstatementDate").toString();
        String responseRetroactiveDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.retroactiveDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.autoAssignIndicator").toString(), requestParams.get().get("autoAssignIndicator").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.planCode").toString(), requestParams.get().get("planCode").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.planEndDateReason").toString(), requestParams.get().get("planEndDateReason").getAsString());
        assertEquals(responseReinstatementDate, requestParams.get().get("reinstatementDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.reinstatementIndicator").toString(), requestParams.get().get("reinstatementIndicator").getAsString());
        assertEquals(responseRetroactiveDate, requestParams.get().get("retroactiveDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("enrollmentResponse.retroactiveIndicator").toString(), requestParams.get().get("retroactiveIndicator").getAsString());
    }

    @Then("I will verify an {string} for DPBI is created for enrollment update")
    public void verifyTheEnrollmentDataSaveEventUpdated(String enrollmentUpdateEvent) {
        String correlationId = requestParams.get().get("correlationId").getAsString();

        JsonObject event = new JsonObject();
        event.addProperty("correlationId", correlationId);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        String checkFormat = "2019-11-12T12:14:56.000+0000";
        boolean val = checkFormat.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}+\\d{4}");
        System.out.println(val);

        JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        JSONObject eventData= new JSONObject(json.get(json.size()-1).toString());
        JSONObject payloadObject=new JSONObject(new JSONObject(json.get(json.size()-1).toString()).get("payload").toString());
        Iterator<String> payloadKeys = payloadObject.keys();
        System.out.println("Payload:"+ payloadObject);

        String correlationIdValue =new JSONObject(json.get(json.size()-1).toString()).get("correlationId").toString();
        eventId.set(new JSONObject(json.get(json.size()-1).toString()).get("eventId").toString());
        String action=payloadObject.getString("action");
        String recordType=payloadObject.getString("recordType");
        String eventCreatedOn=payloadObject.getString("eventCreatedOn");
        JSONObject dataObject=payloadObject.getJSONObject("dataObject");
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


        System.out.println((eventsContent));
        boolean recordFound = false;

        for(int i=0;i<eventsContent.size();i++) {
            HashMap eventsData = (HashMap)eventsContent.get(i);
            if(eventsData.get("eventName").equals(enrollmentUpdateEvent)) {
                assertEquals(eventsData.get("status"), "success");
                assertEquals(eventsData.get("correlationId"), correlationId);

                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);

        validateSubscriptionProcessEligibilityEvent(enrollmentUpdateEvent);
    }

    private void validateSubscriptionProcessEligibilityEvent(String enrollmentSaveEvent) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint.get());

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName.get());

        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        subscriberId.set(new JSONObject(json.get(0).toString()).get("subscriberId").toString());
        subscriberList.set(new JSONArray(new JSONObject(json.get(0).toString()).get("subscriberList").toString()));

        boolean flag=false;
        for(int i=0;i<subscriberList.get().length();i++){
            JSONObject temp=new JSONObject(subscriberList.get().get(i).toString());
            if(temp.get("eventName").toString().equals(enrollmentSaveEvent)){
                Assert.assertEquals(temp.get("subscriberId").toString(),subscriberId.get(), "Subscriber Id is wrong");
                Assert.assertEquals(temp.get("eventName").toString(),enrollmentSaveEvent, "Event Name is wrong");
                Assert.assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                flag=true;
                break;
            }
        }
        Assert.assertTrue(flag,enrollmentSaveEvent+" is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        subscribersRecordEndPoint.set(subscribersRecordEndPoint.get()+subscriberId.get()+"/"+eventId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray subscriberJson= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp= new JSONObject(subscriberJson.get(0).toString());
        Assert.assertEquals(temp.getString("eventName"),enrollmentSaveEvent);
        Assert.assertEquals(temp.getInt("eventId")+"",eventId.get());
        Assert.assertEquals(temp.getInt("subscriberId")+"",subscriberId.get());
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
