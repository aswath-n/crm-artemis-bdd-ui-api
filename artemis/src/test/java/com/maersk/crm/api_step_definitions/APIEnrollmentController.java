package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.step_definitions.CRMEnrollmentUpdateStepDef;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class APIEnrollmentController extends CRMUtilities implements ApiBase {

    private final ThreadLocal<String> enrollmentBaseURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEligibilityURI"));
    private final ThreadLocal<String> getEnrollmentListByUser = ThreadLocal.withInitial(()->"mars/eb/enrollments");
    private final ThreadLocal<String> eventURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEventsURI"));
    private final ThreadLocal<String> taskManagentURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiTaskManagementURI"));
    private final ThreadLocal<String> getEvents = ThreadLocal.withInitial(()->"app/crm/events?size=10000&page=0");
    private final ThreadLocal<String>  subscribersEndPoint = ThreadLocal.withInitial(()->"/app/crm/es/event/subscribers");
    private final ThreadLocal<String> subscribersRecordEndPoint = ThreadLocal.withInitial(()->"/app/crm/es/subscriber/records/");
    private final ThreadLocal<String> getEnrollmentByConsumerId = ThreadLocal.withInitial(()->"/mars/eb/enrollments/{consumerId}");
    private final ThreadLocal<String> getEnrollmentBenefitStatusByConsumerId = ThreadLocal.withInitial(()->"/mars/eb/enrollments/{consumerId}/benefitstatues");
    private final ThreadLocal<String> postEnrollmentBenefitStatus = ThreadLocal.withInitial(()->"/mars/eb/enrollments/benefitstatues");
    private final ThreadLocal<String> bpmEndPoint = ThreadLocal.withInitial(()->"/mars/eb/bpmn/eligibility/start/{consumerId}");
    private final ThreadLocal<String> selectionTransactionEndPoint = ThreadLocal.withInitial(()->"/mars/eb/selectiontransactions");
    private final ThreadLocal<String> taskManagent = ThreadLocal.withInitial(()->"/mars/taskmanagement/tasks?page=0&size=1000");
    private final ThreadLocal<String> getCutOffDates = ThreadLocal.withInitial(()->"/mars/eb/enrollment/cutOffDate?programTypeCode={programTypeCode}&transactionType={transactionType}");
    private final ThreadLocal<String> correspondencesEndPoint = ThreadLocal.withInitial(()->"mars/eb/correspondences?consumerId={consumerId}");
    private final ThreadLocal<String> multipleConsumerBenefitSearchEndPoint = ThreadLocal.withInitial(()->"mars/eb/enrollments/benefitstatues/find-all");
    private final ThreadLocal<String> enrollmentLetterEndPoint = ThreadLocal.withInitial(()->"mars/eb/enrollment/letter");
    private final ThreadLocal<String> enrollmentLetterTransactionStatusEndPoint = ThreadLocal.withInitial(()->"mars/eb/transaction/status");
    private final ThreadLocal<String> getEnrollmentTransactionByStatusAndDate = ThreadLocal.withInitial(()->"mars/eb/enrollments/status/{txnStatus}?txnStatusDate={txnStatusDate}&from=0&size=0");
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsForEvent = ThreadLocal.withInitial(JsonObject::new);
    private static final ThreadLocal<JsonArray> requestParamsArray = ThreadLocal.withInitial(JsonArray::new);
    private final ThreadLocal<JsonObject> selectionTransactionObj = new ThreadLocal<>();
    private static final ThreadLocal<List<String>> planCodes = ThreadLocal.withInitial(ArrayList<String>::new);
    /*
        private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
        private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
        private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/
    final ThreadLocal<ApiTestDataUtil> apitdu = ThreadLocal.withInitial(()->ApiTestDataUtil.getApiTestDataUtilThreadLocal());
    final ThreadLocal<Api_Storage> stg = ThreadLocal.withInitial(()->Api_Storage.getInstance());
    private final ThreadLocal<String> subscriberName = ThreadLocal.withInitial(()->"DPBI");

    public final ThreadLocal<String> eventId = ThreadLocal.withInitial(()->"");
    private final ThreadLocal<String> subscriberId = ThreadLocal.withInitial(()->"");
    private static final ThreadLocal<JSONArray> subscriberList = ThreadLocal.withInitial(JSONArray::new);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);

    public static final ThreadLocal<String> consumerId = ThreadLocal.withInitial(()->"");
    private final ThreadLocal<Integer> selectionTxnId = ThreadLocal.withInitial(()->0);
    public static final ThreadLocal<String> traceIdTOCheckEvents = ThreadLocal.withInitial(String::new);

    public static final ThreadLocal<Integer> enrollmentId = ThreadLocal.withInitial(()->0);
    private static final ThreadLocal<Integer> correspondenceId = ThreadLocal.withInitial(()->0);
    public static final ThreadLocal<Map<String,String>> assignedPlanCodes = ThreadLocal.withInitial(HashMap<String,String>::new);
    private static final ThreadLocal<Map<String, Map<String, String>>> enrollmentChannelDetails = ThreadLocal.withInitial(HashMap<String, Map<String, String>>::new);

    @Given("I initiated Enrollment Create API")
    public void initiatedEnrollmentCreateAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentListByUser.get());
    }

    @When("I run the Enrollment Create API for {string}")
    public void runEnrollmentCreateAPI(String enrollmentType) {
        requestParams.set(generateCreateEnrollmentRequest(enrollmentType));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("CREATED"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "CREATED");
    }

    @Then("I will verify the Enrollment data")
    public void verifyTheEnrollmentData() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String responseAnniversaryDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.anniversaryDate").toString();
        String responseOpenEnrollmentStartDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.openEnrollmentStartDate").toString();
        String responseOpenEnrollmentEndDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.openEnrollmentEndDate").toString();
        String responseLockInStartDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.lockInStartDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.serviceRegionCode").toString(), requestParams.get().get("serviceRegionCode").getAsString());
        assertEquals(responseAnniversaryDate, requestParams.get().get("anniversaryDate").getAsString());
        assertEquals(responseOpenEnrollmentStartDate, requestParams.get().get("openEnrollmentStartDate").getAsString());
        assertEquals(responseOpenEnrollmentEndDate, requestParams.get().get("openEnrollmentEndDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.openEnrollmentStatus").toString(), requestParams.get().get("openEnrollmentStatus").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.lockInStatusCode").toString(), requestParams.get().get("lockInStatusCode").getAsString());
        assertEquals(responseLockInStartDate, requestParams.get().get("lockInStartDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());
    }

    @Then("I will verify the Enrollment Medical plan data")
    public void verifyEnrollmentMedicalData() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String responseReinstatementDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.reinstatementDate").toString();
        String responseRetroactiveDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.retroactiveDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.autoAssignIndicator").toString(), requestParams.get().get("autoAssignIndicator").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.planCode").toString(), requestParams.get().get("planCode").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.planEndDateReason").toString(), requestParams.get().get("planEndDateReason").getAsString());
        assertEquals(responseReinstatementDate, requestParams.get().get("reinstatementDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.reinstatementIndicator").toString(), requestParams.get().get("reinstatementIndicator").getAsString());
        assertEquals(responseRetroactiveDate, requestParams.get().get("retroactiveDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.retroactiveIndicator").toString(), requestParams.get().get("retroactiveIndicator").getAsString());
    }

    @Then("I will verify the Enrollment Provider data")
    public void verifyEnrollmentProviderData() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        JsonElement enrollmentProviderInfo = requestParams.get().get("enrollmentProvider");

        String responseProviderEndDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerEndDate").toString();
        String responseProviderStartDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerStartDate").toString();
        assertEquals(responseProviderEndDate, enrollmentProviderInfo.getAsJsonObject().get("providerEndDate").getAsString());
        assertEquals(responseProviderStartDate, enrollmentProviderInfo.getAsJsonObject().get("providerStartDate").getAsString());

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerType").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerType").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerId").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerId").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerPhoneNumber").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerPhoneNumber").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerFirstName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerFirstName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerLastName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerLastName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerMiddleName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerMiddleName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerNpi").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerNpi").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressLine1").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressLine1").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressLine2").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressLine2").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressCity").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressCity").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressCounty").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressCounty").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressState").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressState").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerZipCodeMain").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerZipCodeMain").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerZipCodeExtn").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerZipCodeExtn").getAsString());
    }

    @Then("I will verify the Enrollment Dental plan data")
    public void verifyEnrollmentDentalData() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String responseReinstatementDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.reinstatementDate").toString();
        String responseRetroactiveDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.retroactiveDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.autoAssignIndicator").toString(), requestParams.get().get("autoAssignIndicator").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.planCode").toString(), requestParams.get().get("planCode").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.planEndDateReason").toString(), requestParams.get().get("planEndDateReason").getAsString());
        assertEquals(responseReinstatementDate, requestParams.get().get("reinstatementDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.reinstatementIndicator").toString(), requestParams.get().get("reinstatementIndicator").getAsString());
        assertEquals(responseRetroactiveDate, requestParams.get().get("retroactiveDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.retroactiveIndicator").toString(), requestParams.get().get("retroactiveIndicator").getAsString());
    }

    @Then("I will verify the Enrollment Dentist Information")
    public void verifyEnrollmentDentistInfo() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        JsonElement enrollmentProviderInfo = requestParams.get().get("enrollmentProvider");

        String responseProviderEndDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerEndDate").toString();
        String responseProviderStartDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerStartDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerType").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerType").getAsString());
        assertEquals(responseProviderEndDate, enrollmentProviderInfo.getAsJsonObject().get("providerEndDate").getAsString());
        assertEquals(responseProviderStartDate, enrollmentProviderInfo.getAsJsonObject().get("providerStartDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerId").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerId").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerPhoneNumber").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerPhoneNumber").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerFirstName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerFirstName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerLastName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerLastName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerMiddleName").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerMiddleName").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerNpi").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerNpi").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressLine1").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressLine1").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressLine2").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressLine2").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressCity").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressCity").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressCounty").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressCounty").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerAddressState").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerAddressState").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerZipCodeMain").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerZipCodeMain").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentProvider.providerZipCodeExtn").toString(), enrollmentProviderInfo.getAsJsonObject().get("providerZipCodeExtn").getAsString());
    }

    @Then("I will verify the Enrollment Behavioral health data")
    public void verifyEnrollmentBehavioralData() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String responseReinstatementDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.reinstatementDate").toString();
        String responseRetroactiveDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.retroactiveDate").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.autoAssignIndicator").toString(), requestParams.get().get("autoAssignIndicator").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.planCode").toString(), requestParams.get().get("planCode").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.planEndDateReason").toString(), requestParams.get().get("planEndDateReason").getAsString());
        assertEquals(responseReinstatementDate, requestParams.get().get("reinstatementDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.reinstatementIndicator").toString(), requestParams.get().get("reinstatementIndicator").getAsString());
        assertEquals(responseRetroactiveDate, requestParams.get().get("retroactiveDate").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.retroactiveIndicator").toString(), requestParams.get().get("retroactiveIndicator").getAsString());
    }

    @Then("I will verify an {string} for DPBI is created for enrollment create")
    public void verifyTheEnrollmentDataSaveEvent(String enrollmentSaveEvent) {

        String correlationId = requestParams.get().get("correlationId").getAsString();
        String traceId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId();
        JsonObject event = new JsonObject();
        event.addProperty("correlationId", traceId);
        requestParamsForEvent.set(event);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsForEvent.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        System.out.println("eventContent " + eventsContent);

        String checkFormat = "2019-11-12T12:14:56.000+0000";
        boolean val = checkFormat.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}+\\d{4}");
        System.out.println(val);

        // JsonArray json=api.apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");

        System.out.println("json " + json);
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
        //assertTrue(verifyDatetimeFormat(payloadObject.getJSONObject("dataObject.enrollmentProviderInfo").getString("effectiveStartDate")));
        assertTrue(verifyDatetimeFormat(payloadObject.getJSONObject("dataObject.enrollmentProviderInfo").getString("effectiveEndDate")));
        assertTrue(verifyDatetimeFormat(payloadObject.getJSONObject("dataObject.enrollmentProviderInfo").getString("createdOn")));
        assertTrue(verifyDatetimeFormat(payloadObject.get("eventCreatedOn").toString()));

        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            if (eventsData.get("eventName").equals(enrollmentSaveEvent)) {
                assertEquals(eventsData.get("status"), "success");
                assertEquals(eventsData.get("correlationId"), correlationId);

                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);

        validateSubscriptionProcessEligibilityEvent(enrollmentSaveEvent);
    }

    @Then("I will check the date the enrollment record was created")
    public void recordDateRecordCreated() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.enrollmentType").toString(), requestParams.get().get("enrollmentType").getAsString());

        String createdOnDate = requestParams.get().get("createdOn").getAsString();
        String responsecreatedOnDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.createdOn").toString();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.createdBy").toString(), requestParams.get().get("createdBy").getAsString());
        assertEquals(responsecreatedOnDate, createdOnDate);
    }

    private JsonObject generateCreateEnrollmentRequest(String enrollmentType) {
        JsonObject request = apitdu.get().getJsonFromFile("crm/enrollment/createEnrollment.json").jsonElement.getAsJsonObject();
        //JsonObject request = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String currentDate = dateFormat.format(todayDate);
        String currentTimeStamp = dateFormat.format(todayDate).concat("T06:02:39.607000+00:00");
        Number correlationId = Long.parseLong(apitdu.get().getRandomNumber(10).randomNumber);

        if (consumerId.get() == null)
            consumerId.set(apitdu.get().getRandomNumber(5).randomNumber);
        Number uiid = Integer.parseInt(apitdu.get().getRandomNumber(6).randomNumber);

        request.addProperty("aidCategoryCode", "string");
        request.addProperty("anniversaryDate", currentDate);
        request.addProperty("autoAssignIndicator", "A");
        request.addProperty("consumerId", Integer.parseInt(consumerId.get()));
        request.addProperty("correlationId", correlationId);
        request.addProperty("createdBy", "asad");
        request.addProperty("createdOn", currentTimeStamp);

        request.addProperty("endDate", apitdu.get().addDaysToPresentDate("yyyy-MM-dd", 0, 0, 1));
        request.addProperty("startDate", currentDate);


        JsonObject enrollmentProviderInfo = new JsonObject();

        enrollmentProviderInfo.addProperty("consumerId", Integer.parseInt(consumerId.get()));
        enrollmentProviderInfo.addProperty("correlationId", correlationId);
        enrollmentProviderInfo.addProperty("createdBy", "asad");
        enrollmentProviderInfo.addProperty("createdOn", currentTimeStamp);
        enrollmentProviderInfo.addProperty("effectiveEndDate", currentDate);
        enrollmentProviderInfo.addProperty("effectiveStartDate", currentDate);
        enrollmentProviderInfo.addProperty("providerId", apitdu.get().getRandomNumber(4).randomNumber);
        enrollmentProviderInfo.addProperty("providerAddressCity", "dallas");
        enrollmentProviderInfo.addProperty("providerAddressCounty", "dallas");
        enrollmentProviderInfo.addProperty("providerAddressLine1", "dallas");
        enrollmentProviderInfo.addProperty("providerAddressLine2", "dallas");
        enrollmentProviderInfo.addProperty("providerAddressState", "texas");
        enrollmentProviderInfo.addProperty("providerEndDate", currentDate);
        enrollmentProviderInfo.addProperty("providerFirstName", "firstName1");
        enrollmentProviderInfo.addProperty("providerLastName", "lastName1");
        enrollmentProviderInfo.addProperty("providerMiddleName", "middleName1");
        enrollmentProviderInfo.addProperty("providerNpi", "providerNpi1");
        enrollmentProviderInfo.addProperty("providerPhoneNumber", "1234567890");
        enrollmentProviderInfo.addProperty("providerStartDate", currentDate);
        enrollmentProviderInfo.addProperty("providerType", "providerType1");
        enrollmentProviderInfo.addProperty("providerZipCodeExtn", "12345");
        enrollmentProviderInfo.addProperty("providerZipCodeMain", "12345");
        enrollmentProviderInfo.addProperty("uiid", uiid);
        enrollmentProviderInfo.addProperty("updatedBy", "string");
        enrollmentProviderInfo.addProperty("updatedOn", currentTimeStamp);

        request.add("enrollmentProvider", enrollmentProviderInfo);

        request.addProperty("enrollmentType", enrollmentType);
        request.addProperty("lockInEndDate", currentDate);
        request.addProperty("lockInExemptionReason", "lockInExemptionReason1");
        request.addProperty("lockInStartDate", currentDate);
        request.addProperty("lockInStatusCode", "lock1");
        request.addProperty("openEnrollmentEndDate", currentDate);
        request.addProperty("openEnrollmentStartDate", currentDate);
        request.addProperty("openEnrollmentStatus", "openEnrollentStatus1");
        request.addProperty("planCode", "84");
        request.addProperty("planEndDateReason", "Test");
//        request.addProperty("planId", Integer.parseInt(apitdu.getRandomNumber(2).randomNumber));
        if (selectionTransactionObj.get() != null) {
            request.addProperty("planId", Integer.parseInt(selectionTransactionObj.get().get("planId").toString()));
        } else {
            request.addProperty("planId", 3140);
        }

        request.addProperty("programTypeCode", "MEDICAID");
        request.addProperty("reinstatementDate", currentDate);
        request.addProperty("reinstatementIndicator", "A");
        request.addProperty("retroactiveDate", currentDate);
        request.addProperty("retroactiveIndicator", "A");
        request.addProperty("riskCode", "riskCode1");
        // request.addProperty("selectionTxnId", selectionTxnId);
        request.addProperty("serviceRegionCode", "serviceRegionCode1");
        request.addProperty("specialNeedFlag", "string");
        request.addProperty("subProgramTypeCode", "MEDICAIDMCHB");
        request.addProperty("uiid", uiid);
        request.addProperty("updatedBy", "asad");
        request.addProperty("updatedOn", currentTimeStamp);
        request.addProperty("waiverCode", "waiverCode1");

        return request;
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

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        subscriberId.set(new JSONObject(json.get(0).toString()).get("subscriberId").toString());
        subscriberList.set(new JSONArray(new JSONObject(json.get(0).toString()).get("subscriberList").toString()));

        boolean flag = false;
        for (int i = 0; i < subscriberList.get().length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get().get(i).toString());
            if (temp.get("eventName").toString().equals(enrollmentSaveEvent)) {
                Assert.assertEquals(temp.get("subscriberId").toString(), subscriberId.get(), "Subscriber Id is wrong");
                Assert.assertEquals(temp.get("eventName").toString(), enrollmentSaveEvent, "Event Name is wrong");
                Assert.assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag, enrollmentSaveEvent + " is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI.get());
        subscribersRecordEndPoint.set(subscribersRecordEndPoint.get() + subscriberId.get() + "/" + eventId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray subscriberJson = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(subscriberJson.get(0).toString());
        Assert.assertEquals(temp.getString("eventName"), enrollmentSaveEvent);
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

    @Given("I created a consumer for enrollment record")
    public void i_created_a_consumer_for_enrollment_record() {

        APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
        caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPI();
        caseLoader.runCaseLoaderEligibilityEnrollmentAPI();
        consumerSSN.set(caseLoader.getConsumerSSN());
        APIConsumerSearchController consumerSearch = new APIConsumerSearchController();
        consumerSearch.setConsumerSSN(consumerSSN.get());
        consumerSearch.initiatedCaseConsumerContactSearchAPI();
        consumerSearch.runConsumerSearchAPI("consumerSSN");
        consumerId.set(consumerSearch.getConsumerIdFromSearchResults());
    }

    @Given("I initiated get enrollment by consumer id {string}")
    public void initiatedGetEnrolmentByConsumerID(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentByConsumerId.get().replace("{consumerId}", consumerId));
    }

    @Then("I verify selection status is displayed as {string}")
    public void VerifySelectionStatusIsDisplayedAs(String expectedStatus) {
        ArrayList<String> actualStatus = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.status");
        assertEquals(actualStatus.get(0), expectedStatus);
    }

    @When("I run get enrollment api")
    public void runGetEnrollmentByConsumerId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
// removing commented code , new logic doesn't impact existing steps
        JsonArray array = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            HashMap<String, String> details = new HashMap<String, String>();
            if(obj.has("enrollmentTimeframe")){
                details.put("enrollmentTimeframe", obj.get("enrollmentTimeframe").getAsString());
                details.put("channel", obj.get("channel").getAsString());
                enrollmentChannelDetails.get().put(obj.get("planCode").getAsString(), details);
            }

        }
    }
    @When("I save auto assigned plan code and plan name for {string} with get enrollment api")
    public void saveAutoAssignedPlanCodeAndPlanNameForWithGetEnrollmentApi(String consumerId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        consumerId = String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId)).replace(".0", "");
        String planCode = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("planCode").getAsString();
        assignedPlanCodes.get().put(consumerId, planCode);
        String planName = getPlanNameFromPlanCode(planCode);
        CRMEnrollmentUpdateStepDef.assignedPlanNames.get().put(consumerId, planName);
    }
    public static String getPlanNameFromPlanCode(String planCode){
        switch (planCode){
            case "087358900":
            case "081080400":
                return "AMERIHEALTH CARITAS DC";
            case "077573200":
            case "055558200":
                return "CAREFIRST COMMUNITY HEALTH PLAN DC";
            case "078222800":
            case "044733300":
                return "MEDSTAR FAMILY CHOICE";
            case "84":
                return "AMERIGROUP COMMUNITY CARE";
            case "85":
                return "PEACH STATE";
            case "86":
                return "WELLCARE";
            case "87":
                return "CARESOURCE GEORGIA";
            default:
                return "No Plan Name found for " + planCode + " Plan Code";
        }
    }

    public static String getMcoPhoneNumberFromPlanCode(String planCode){
        switch (planCode){
            case "087358900":
            case "081080400":
                return "888-922-0007";
            case "077573200":
            case "055558200":
                return "855-326-4831";
            case "078222800":
            case "044733300":
                return "855-326-4831";
            default:
                return "No Plan Name found for " + planCode + " Plan Code";
        }
    }

    public static String getPlanCodeFromEnrollmentDetails(String consumerId){
        return assignedPlanCodes.get().get(consumerId);
    }

    @Then("I verify enrollment records are displayed for the consumer {string}")
    public void verifyEnrollmentRecordsDisplayed(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        ArrayList<String> consumerIds = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.consumerId");
        assertEquals(consumerIds.get(0), Integer.parseInt(consumerId));
    }

    @Given("I initiated get benefit status by consumer id {string}")
    public void initiatedGetBenefitStatusByConsumerID(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentBenefitStatusByConsumerId.get().replace("{consumerId}", consumerId));
    }

    @Then("I verify benefit status records are displayed for the consumer {string}")
    public void verifyBenefitStatusRecordsDisplayed(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        ArrayList<String> consumerIds = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.consumerId");
        assertEquals(consumerIds.get(0), Integer.parseInt(consumerId));
    }

    @Then("I verify enrollment by consumer id {string} with data")
    public void iVerifyEnrollmentByConsumerIdWithData(String consumerId, Map<String, String> data) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentByConsumerId.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        JsonArray enrollmentRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        if (data.containsKey("empty")) {
            assertEquals(enrollmentRecords.size(), 0, "enrollmentRecords is not empty! - FAIL!");
            return;
        }
        if (data.containsKey("enrollmentCreated")) {
            if (data.get("enrollmentCreated").equals("false")) {
                assertEquals(enrollmentRecords.size(), 0, "Enrollment was created! - FAIL!");
                return;
            } else {
                assertTrue(enrollmentRecords.size() > 0, "enrollment was NOT created! - Fail");
            }
        }
        JsonObject enrollmentRecord = null;
        // there are 2 enrollment records are some times, we need previous one based on different combination of timeframes
        if (enrollmentRecords.size() > 1) {
            List<String> times = new ArrayList<>();
            for (int i = 0; i < enrollmentRecords.size(); i++) {
                times.add(enrollmentRecords.get(i).getAsJsonObject().get("enrollmentTimeframe").toString().replaceAll("\"", ""));
            }
            if (times.containsAll(Arrays.asList("PAST", "Active", "FUTURE"))) {
                enrollmentRecord = enrollmentRecords.get(times.indexOf("Active")).getAsJsonObject();
            } else if (times.size() == 2) {
                if (enrollmentRecords.get(0).getAsJsonObject().get("enrollmentId").getAsInt() >
                        enrollmentRecords.get(1).getAsJsonObject().get("enrollmentId").getAsInt()) {
                    enrollmentRecord = enrollmentRecords.get(1).getAsJsonObject();
                } else {
                    enrollmentRecord = enrollmentRecords.get(0).getAsJsonObject();
                }
            }
        } else {
            enrollmentRecord = enrollmentRecords.get(0).getAsJsonObject();
        }
        planCodes.get().add(enrollmentRecord.get("planCode").getAsString());
        enrollmentRecord.addProperty("planName", getPlanNameFromPlanCode(enrollmentRecord.get("planCode").getAsString()));
        enrollmentRecord.addProperty("mcoPhoneNumber", getMcoPhoneNumberFromPlanCode(enrollmentRecord.get("planCode").getAsString()));
        if(data.containsKey("saveDetailsWithName")){
            Api_Body api_req = new EventBuilder()
                    .body(enrollmentRecord.toString())
                    .name(data.get("saveDetailsWithName"))
                    .build();
            stg.get().addProduced(api_req);
        }
        commonStepsForLatestAndCurrentEnrollmentVerification(enrollmentRecord, data);
    }

    @Then("I verify latest enrollment by consumer id {string} with data")
    public void iVerifyLatestEnrollmentByConsumerIdWithData(String consumerId, Map<String, String> data) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentByConsumerId.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        JsonArray enrollmentRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        if (data.containsKey("enrollmentCreated")) {
            if (data.get("enrollmentCreated").equals("false")) {
                assertEquals(enrollmentRecords.size(), 0, "Enrollment was created! - FAIL!");
                return;
            } else {
                assertTrue(enrollmentRecords.size() > 0, "enrollment was NOT created! - Fail");
            }
        }
        JsonObject enrollmentRecord = null;
        // there are 2 enrollment records are some times, we need latest one based on different combination of timeframes
        if (enrollmentRecords.size() > 1) {
            List<String> times = new ArrayList<>();
            for (int i = 0; i < enrollmentRecords.size(); i++) {
                times.add(enrollmentRecords.get(i).getAsJsonObject().get("enrollmentTimeframe").toString().replaceAll("\"", ""));
            }
            if (times.containsAll(Arrays.asList("PAST", "Active", "FUTURE"))) {
                enrollmentRecord = enrollmentRecords.get(times.indexOf("FUTURE")).getAsJsonObject();
            } else if (times.size() == 2) {
                if (enrollmentRecords.get(0).getAsJsonObject().get("enrollmentId").getAsInt() >
                        enrollmentRecords.get(1).getAsJsonObject().get("enrollmentId").getAsInt()) {
                    enrollmentRecord = enrollmentRecords.get(0).getAsJsonObject();
                } else {
                    enrollmentRecord = enrollmentRecords.get(1).getAsJsonObject();
                }
            }
        } else {
            enrollmentRecord = enrollmentRecords.get(0).getAsJsonObject();
        }

        commonStepsForLatestAndCurrentEnrollmentVerification(enrollmentRecord, data);
    }

    @Then("I verify enrollmentID {string} by consumer id {string} with data")
    public void iVerifyEnrollmentIDByConsumerIdWithData(String givenEnrolmentId, String consumerId, Map<String, String> data) {
        givenEnrolmentId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(givenEnrolmentId).toString().replace(".0", "");
        System.out.println("Validating enrollmentId " + givenEnrolmentId);
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentByConsumerId.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        JsonArray enrollmentRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        JsonObject enrollmentRecord = null;
        for (int i = 0; i < enrollmentRecords.size(); i++) {
            if (enrollmentRecords.get(i).getAsJsonObject().get("enrollmentId").getAsString().equals(givenEnrolmentId)) {
                enrollmentRecord = enrollmentRecords.get(i).getAsJsonObject();
                break;
            }
        }
        if (data.containsKey("enrollmentPresented")) {
            if (data.get("enrollmentPresented").equals("false")) {
                assertNull(enrollmentRecord, "Enrollment for enrollmentId " + givenEnrolmentId + " is presented! - FAIL!");
                System.out.println("Enrollment for enrolmentId " + givenEnrolmentId + " is not presented - PASS!");
            } else if (data.get("enrollmentPresented").equals("true")) {
                assertNotNull(enrollmentRecord, "Enrollment for enrollmentId " + givenEnrolmentId + " is Not presented! - FAIL!");
                System.out.println("Enrollment for enrolmentId " + givenEnrolmentId + " is presented - PASS!");
            }
        }

        commonStepsForLatestAndCurrentEnrollmentVerification(enrollmentRecord, data);
    }

    public void commonStepsForLatestAndCurrentEnrollmentVerification(JsonObject enrollmentRecord, Map<String, String> data) {
        System.out.println("enrollmentRecord = " + enrollmentRecord);

        for (String key : data.keySet()) {
            switch (key) {
                case "medicalPlanEndDate":
                case "enrollmentEndDate":
                    if (data.get(key).equals("") || data.get(key).equals("null")) {
                        assertTrue(enrollmentRecord.get("endDate").isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(enrollmentRecord.get("endDate").getAsString(),
                                apitdu.get().getEndDate(data.get(key)), key + " mismatch! - FAIL!");
                    }
                    break;
                case "medicalPlanBeginDate":
                case "enrollmentStartDate":
                    if (data.get(key).equals("") || data.get(key).equals("null")) {
                        assertTrue(enrollmentRecord.get("startDate").isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(enrollmentRecord.get("startDate").getAsString(),
                                apitdu.get().getStartDate(data.get(key)), key + " mismatch! - FAIL!");
                    }
                    break;
                case "anniversaryDate":
                case "lockInStartDate":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(enrollmentRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(enrollmentRecord.get(key).getAsString(),
                                apitdu.get().getStartDate(data.get(key)), key + " mismatch! - FAIL!");
                    }
                    break;
                case "lockInEndDate":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(enrollmentRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(enrollmentRecord.get(key).getAsString(),
                                apitdu.get().getEndDate(data.get(key)), key + " mismatch! - FAIL");
                    }
                    break;
                case "updatedOn":
                case "createdOn":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(enrollmentRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(enrollmentRecord.get(key).getAsString().substring(0, 10),
                                apitdu.get().getStartDate(data.get(key)), key + " mismatch! - FAIL!");
                    }
                    break;
                case "planCode":
                    if (data.get("planCode").isEmpty() || data.get("planCode").equals("null")) {
                        assertTrue(enrollmentRecord.get("planCode").isJsonNull(), "planCode is not null! - FAIL!");
                    } else if (data.get("planCode").equalsIgnoreCase("not null")) {
                        assertTrue(!enrollmentRecord.get("planCode").isJsonNull(), "planCode is null! - FAIL!");
                    } else if (data.get("planCode").equals("getFromUISelected")) {
                        String subProgramTypeCode = APIConsumerPopulationDmnController.subProgramTypeCode.get();
                        String planCode = "";
                        switch (CRMEnrollmentUpdateStepDef.selectedPlanName.get()) {
                            case "AMERIGROUP COMMUNITY CARE":
                                planCode = "84";
                                break;
                            case "CARESOURCE GEORGIA":
                                planCode = "87";
                                break;
                            case "PEACH STATE":
                                planCode = "85";
                                break;
                            case "WELLCARE":
                                planCode = "86";
                                break;
                            case "CARESOURCE HIP":
                                planCode = "755726440";
                                break;
                            case "ANTHEM HIP":
                                planCode = "455701400";
                                break;
                            // at time of writing script I didn't know codes, please update if you know
                            case "MANAGED HEALTH SERVICES HIP":
                                planCode = "355787430";
                                break;
                            case "ICHIA ESP HIP":
                                planCode = "155723420";
                                break;
                            case "MDWISE AMERICHOICE HIP":
                                planCode = "555763410";
                                break;
                            case "UNITED HEALTHCARE HCC":
                                planCode = "699842000";
                                break;
                            case "MANAGED HEALTH SERVICES HCC":
                                planCode = "399243310";
                                break;
                            case "ANTHEM HCC":
                                planCode = "499254630";
                                break;
                            case "MDWISE HH":
                                planCode = "500307680";
                                break;
                            case "MANAGED HEALTH SERVICES":
                                planCode = "300119960";
                                break;
                            case "CARESOURCE":
                                planCode = "700410350";
                                break;
                            case "ANTHEM":
                                planCode = "400752220";
                                break;
                            case "AMERIHEALTH CARITAS DC":
                                if (subProgramTypeCode.equals("DCHF") || subProgramTypeCode.equals("ImmigrantChildren"))
                                    planCode = "081080400";
                                else if (subProgramTypeCode.equals("Alliance"))
                                    planCode = "087358900";
                                break;
                            case "CAREFIRST COMMUNITY HEALTH PLAN DC":
                                if (subProgramTypeCode.equals("DCHF") || subProgramTypeCode.equals("ImmigrantChildren"))
                                    planCode = "055558200";
                                else if (subProgramTypeCode.equals("Alliance"))
                                    planCode = "077573200";
                                break;
                            case "MEDSTAR FAMILY CHOICE":
                                if (subProgramTypeCode.equals("DCHF") || subProgramTypeCode.equals("ImmigrantChildren"))
                                    planCode = "044733300";
                                else if (subProgramTypeCode.equals("Alliance"))
                                    planCode = "078222800";
                                break;
                        }
                        assertEquals(enrollmentRecord.get("planCode").getAsString(), planCode, key = " mismatch! - FAIL!");
                    } else if (data.get("planCode").equals("planCodeFromEnrollmentDetails")) {
                        String consumerId = String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId"))).replace(".0", "");
                        assertEquals(enrollmentRecord.get("planCode").getAsString(), getPlanCodeFromEnrollmentDetails(consumerId), key + " mismatch! - FAIL!");
                    } else {
                        assertEquals(enrollmentRecord.get("planCode").getAsString(), data.get("planCode"), key + " mismatch! - FAIL!");
                    }
                    break;
                case "enrollmentCreated":
                case "enrollmentPresented":
                    // ignoring this keys since they used in different circumstances
                    break;
                case "enrollmentProviders":
                    if (data.get("enrollmentProviders").isEmpty() || data.get("enrollmentProviders").equals("null")) {
                        assertTrue(enrollmentRecord.get("enrollmentProviders").isJsonNull(), "enrollmentProviders is not null! - FAIL!");
                    } else if (data.get("enrollmentProviders").equalsIgnoreCase("not null")) {
                        assertTrue(!enrollmentRecord.get("enrollmentProviders").isJsonNull(), "enrollmentProviders is null! - FAIL!");
                    } else {
                        assertEquals(enrollmentRecord.get("enrollmentProviders").getAsString(), data.get("enrollmentProviders"), key + " mismatch! - FAIL!");
                    }
                    break;
                case "enrollmentProvider.providerNpi":
                    assertEquals(enrollmentRecord.get("enrollmentProviders").getAsJsonArray().get(0).getAsJsonObject().get("providerNpi").getAsString(), data.get("enrollmentProvider.providerNpi"));
                    break;
                case "enrollmentProvider.providerStartDate":
                    assertEquals(enrollmentRecord.get("enrollmentProviders").getAsJsonArray().get(0).getAsJsonObject().get("providerStartDate").getAsString(), apitdu.get().getStartDate(data.get("enrollmentProvider.providerStartDate")));
                    break;
                case "enrollmentProvider.providerEndDate":
                    assertEquals(enrollmentRecord.get("enrollmentProviders").getAsJsonArray().get(0).getAsJsonObject().get("providerEndDate").getAsString(), apitdu.get().getEndDate(data.get("enrollmentProvider.providerEndDate")));
                    break;
                case "saveDetailsWithName":
                    //This key is used to store the response details.
                    break;
                case "planEndDateReason":
                case "txnStatus":
                case "status":
                case "rejectionReason":
                case "selectionReason":
                case "enrollmentType":
                case "channel":
                default:
                    // all of data with simple verification fell under default
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(enrollmentRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else if (data.get(key).contains(".")) {
                        assertEquals(enrollmentRecord.get(key).getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)), key + " mismatch! - FAIL!");
                    } else {
                        System.out.println("testing " + key);
                        assertEquals(enrollmentRecord.get(key).getAsString(),
                                data.get(key), key + " mismatch! - FAIL!");
                    }
            }
        }
    }

    @Then("I retrieve task information with name {string} for consumer id {string}")
    public void iRetrieveTaskInformationWithNameForConsumerId(String name, String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        Map<String, String> data = new HashMap<>();
        data.put("payload.consumerId", consumerId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagentURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(taskManagent.get());

        apitdu.get().getJsonFromFile("/crm/task/searchTaskByConsumerId.json");
        String json = apitdu.get().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @Then("I verify if tasks for consumer id {string} is created {string}")
    public void iVerifyNoTasksCreatedForConsumerId(String consumerId, String created) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        Map<String, String> payload = new HashMap<>();
        payload.put("payload.consumerId", consumerId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagentURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(taskManagent.get());

        apitdu.get().getJsonFromFile("/crm/task/searchTaskByConsumerId.json");
        String json = apitdu.get().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, payload));

        if (created.equals("false")) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("tasks").isJsonNull()
                    || API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").size() == 0, "tasks value is not null");
        } else if (created.equals("true")) {
            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("tasks").isJsonNull(), "tasks value is null");
        }
    }

    @Then("I verify latest task information with name {string} for case id {string} with data")
    public void iVerifyLatestTaskInformationForCaseIdWithData(String name, String caseId, Map<String, String> data) {
        if (caseId.isEmpty()) {
            caseId = APIConsumerPopulationDmnController.caseIdentificationNo.get();
        } else if (caseId.equalsIgnoreCase("internal")) {
            caseId = APIConsumerPopulationDmnController.caseId.get();
        } else {
            caseId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(caseId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagentURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(taskManagent.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, "{\"payload\":{\"caseId\":\"" + caseId + "\"}}");

        commonStepsTaskInformationVerificationWithData(data);
    }

    @Then("I verify latest task information with name {string} for consumer id {string} with data")
    public void iVerifyLatestTaskInformationForConsumerIdWithData(String name, String consumerId, Map<String, String> data) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagentURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(taskManagent.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, "{\"payload\":{\"consumerId\":\"" + consumerId + "\"}}");
        if(data.containsKey("saveResponseWithName")){
            Api_Body api_res = new EventBuilder()
                    .body(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString)
                    .name(data.get("saveResponseWithName"))
                    .build();
            stg.get().addConsumed(api_res);
        }
        commonStepsTaskInformationVerificationWithData(data);
    }

    public void commonStepsTaskInformationVerificationWithData(Map<String, String> data) {
        if (data.containsKey("empty")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").size(), 0, "Task was generated! - Fail!");
            return;
        }

        int index = 0;
        int taskTypeId_count = 0;
        if (data.containsKey("find_taskTypeId")) {
            for (int ii = 0; ii < API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").size(); ii++) {
                if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(ii)
                        .getAsJsonObject().get("taskVO").getAsJsonObject().get("taskTypeId").getAsString().equals(data.get("find_taskTypeId"))) {
                    index = ii;
                    taskTypeId_count++;
                }
            }
        }

        JsonObject taskRecord = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(index)
                .getAsJsonObject().get("taskVO").getAsJsonObject();

        JsonArray taskDetails = taskRecord.getAsJsonArray("taskDetails");
        JsonArray taskLinks = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(index)
                .getAsJsonObject().get("taskLinksVOS").getAsJsonArray();

        for (String key : data.keySet()) {
            switch (key) {
                case "recordCount":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("recordCount").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                    break;
                case "staffAssignedTo":
                case "createdBy":
                case "taskStatus":
                case "defaultPriority":
                case "dueInDays":
                case "taskNotes":
                case "taskTypeId":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(taskRecord.get(key).isJsonNull());
                    } else {
                        assertEquals(taskRecord.get(key).getAsString(), data.get(key), key + " mismatch! - FAIL");
                    }
                    break;
                case "createdOn":
                case "statusDate":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(taskRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(taskRecord.get(key).getAsString().substring(0, 10),
                                apitdu.get().getStartDate(data.get(key)), key + " mismatch! - FAIL!");
                    }
                    break;
                case "defaultDueDate":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(taskRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(taskRecord.get(key).getAsString(), apitdu.get().getEndDate(data.get(key)), key + " mismatch! - FAIL!");
                    }
                    break;
                case "taskInfo":
                    if (data.get("taskInfo").isEmpty() || data.get("taskInfo").equals("null")) {
                        assertTrue(taskRecord.get("taskInfo").isJsonNull(), key + " is not null! - FAIL!");
                    } else if (data.get("taskInfo").equals("not null")) {
                        assertFalse(taskRecord.get("taskInfo").isJsonNull(), key + " is null! - FAIL!");
                    } else {
                        String[] keys = data.get("taskInfo").split("\\^");
                        if(keys.length==1){
                            keys = data.get("taskInfo").split("/");
                        }
                        String taskInfo = taskRecord.get("taskInfo").getAsString();
//                        String[] taskInfo = taskRecord.get("taskInfo").getAsString().split("[,-]");
//                        assertTrue(taskInfo[0].contains(keys[0]));
//                        assertTrue(taskInfo[1].contains(
//                                keys[1].contains(".")
//                                        ? String.valueOf(api_common.defineValue(keys[1])).replace(".0", "")
//                                        : APIConsumerPopulationDmnController.externalConsumerId.get()));
//                        assertTrue(taskInfo[2].contains(keys[2]));

                        for (int i = 0; i < keys.length; i++) {
                            if (keys[i].contains("externalConsumerId")||keys[i].contains("caseId")||keys[i].contains("caseIdentificationNumber")) {
                                System.out.println("TaskInfo: " + taskInfo + " contains key: " + keys[i] + " which has value: " + String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(keys[i].trim())).replace(".0", ""));
                                assertTrue(taskInfo.contains(String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(keys[i].trim())).replace(".0", "")));
                            }
                            else {
                                System.out.println("TaskInfo: " + taskInfo + " contains key: " + keys[i]);
                                assertTrue(taskInfo.contains(keys[i]));
                            }
                        }
                    }
                    break;
                case "channel":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Channel")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull(), key + " is not null! - FAIL!");
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "externalCaseId":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("External Case ID")) {
                            assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(),
                                    data.get(key).contains(".")
                                            ? String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))).replace(".0", "")
                                            : APIConsumerPopulationDmnController.externalConsumerId.get(), key + " mismatch! - FAIL!");
                            break;
                        }
                    }
                    break;
                case "disposition":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Disposition")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "informationType":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Information Type")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "applicationType":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Application Type")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "reason":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Reason Text")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "planId":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Plan ID")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "planName":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Plan Name")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "planStartDate":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Plan Start Date")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionDate").getAsString(),
                                        apitdu.get().getStartDate(data.get(key)), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "startDate":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Start Date")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionDate").getAsString(),
                                        apitdu.get().getStartDate(data.get(key)), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "endDate":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("End Date")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionDate").getAsString(),
                                        apitdu.get().getEndDate(data.get(key)), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "program":
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getAsJsonObject().get("selectionFieldName").getAsString().equals("Program")) {
                            if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                                assertTrue(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").isJsonNull());
                            } else {
                                assertEquals(taskDetails.get(i).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "linkToCase":
                    for (int i = 0; i < taskLinks.size(); i++) {
                        if (taskLinks.get(i).getAsJsonObject().get("externalLinkRefType").getAsString().equals("CASE")) {
                            if (data.get(key).equals("null")) {
                                assertTrue(taskLinks.get(i).getAsJsonObject().get("externalLinkRefId").isJsonNull());
                            } else {
                                assertEquals(taskLinks.get(i).getAsJsonObject().get("externalLinkRefId").getAsString(),
                                        data.get(key).contains(".")
//                                                ? String.valueOf(api_common.defineValue(data.get(key)))
                                                ? String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))).replace(".0", "")
                                                : APIConsumerPopulationDmnController.caseIdentificationNo.get(), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "linkToConsumer":
                    for (int i = 0; i < taskLinks.size(); i++) {
                        if (taskLinks.get(i).getAsJsonObject().get("externalLinkRefType").getAsString().equals("CONSUMER")) {
                            if (data.get(key).equals("null")) {
                                assertTrue(taskLinks.get(i).getAsJsonObject().get("externalLinkRefId").isJsonNull());
                            } else {
                                assertEquals(taskLinks.get(i).getAsJsonObject().get("externalLinkRefId").getAsString(),
                                        data.get(key).contains(".")
                                                ? String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)))
                                                : APIConsumerPopulationDmnController.consumerId.get(), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "linkToTask":
                    for (int i = 0; i < taskLinks.size(); i++) {
                        if (taskLinks.get(i).getAsJsonObject().get("externalLinkRefType").getAsString().equals("TASK")) {
                            if (data.get(key).equals("null")) {
                                assertTrue(taskLinks.get(i).getAsJsonObject().get("externalLinkRefId").isJsonNull());
                            } else {
                                assertEquals(taskLinks.get(i).getAsJsonObject().get("externalLinkRefId").getAsString(),
                                        data.get(key).contains(".")
                                                ? String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)))
                                                : APIConsumerPopulationDmnController.consumerId.get(), key + " mismatch! - FAIL!");
                            }
                            break;
                        }
                    }
                    break;
                case "find_taskTypeId":
                    // ignore
                    break;
                case "this_taskTypeId_count":
                    assertEquals(String.valueOf(taskTypeId_count), data.get("this_taskTypeId_count"), "this_taskTypeId_count mismatch! - FAIL!");
                    break;
            }
        }
    }

    @Given("I initiated BPM Process for the consumer {string}")
    public void initiatedbpmByConsumerID(String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        }
        bpmEndPoint.set(bpmEndPoint.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(bpmEndPoint.get());
    }

    @When("I start the BPM process")
    public void startBpmProcess() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @Then("I verify the BPM process status is {string}")
    public void verifyBpmProcessStatus(String statusCode) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, Integer.parseInt(statusCode));
    }

    @Then("I verify benefit status records are displayed with population {string}")
    public void verifyBenefitStatusRecordsDisplayedWIthRightPopulation(String population) {
        ArrayList<String> populations = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.population");
        assertEquals(populations.get(0), population);
        System.out.println("actual population is " + populations.get(0));
    }

    @Then("I verify benefit status records are displayed with programpopulation as {string}")
    public void verifyBenefitStatusRecordsDisplayedWithExpectedProgramPopulation(String programpopulation) {
        ArrayList<String> populations = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.programPopulation");
        assertEquals(populations.get(0), programpopulation);
    }

    @Then("I verify benefit status records displays Program Type as {string} and Timeframe as {string}")
    public void verifyProgramInBenfetiStatusResponse(String programTypeCode, String timeframe) {
        ArrayList<String> populations = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.programTypeCode");
        ArrayList<String> populations2 = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.timeframe");
        assertEquals(populations.get(0), programTypeCode);
        assertEquals(populations2.get(0), timeframe);

    }

    @Then("I verify benefit status records are displayed with benefit status {string}")
    public void verifyBenefitStatusRecordsDisplayedWIthRightBenefitStatus(String benefitStatus) {
        ArrayList<String> populations = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.benefitStatus");
        if (benefitStatus.isEmpty() || benefitStatus.equals("null")) {
            assertNull(populations.get(0), "befitStatus is not null");
        } else {
            assertEquals(populations.get(0), benefitStatus);
        }
    }

    @Then("I verify latest benefit status records are displayed with benefit status {string}")
    public void verifyLatestBenefitStatusRecordsDisplayedWIthRightBenefitStatus(String benefitStatus) {
        ArrayList<String> populations = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.benefitStatus");
        System.out.println("latest benefit status records = " + populations);
        assertEquals(populations.get(populations.size() - 1), benefitStatus);
    }

    @Given("I initiated selection transaction API")
    public void initiateSelectionTransactionAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(selectionTransactionEndPoint.get());
    }

    //To be deleted after modifying scenarios
    @When("I provide selection transaction details for {string} and enrollment {string}")
    public void provideInfoForSelectionTransactionAPI(String planStatus, String enrollment) {
        apitdu.set(apitdu.get().getJsonFromFile("crm/enrollment/selectionTransaction.json"));
        synchronized (selectionTransactionObj){
        selectionTransactionObj.set(apitdu.get().jsonElement.getAsJsonArray().get(0).getAsJsonObject());
        }
        selectionTransactionObj.get().addProperty("txnStatus", planStatus);
        selectionTransactionObj.get().addProperty("consumerId", consumerId.get());
        String startDate = "";
        String endDate = "";
        switch (enrollment) {
            case "current":
                startDate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", 0, -1, 0) + "T00:00:00.000Z";
                endDate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", 0, 0, 1) + "T00:00:00.000Z";
                break;
            case "future":
                startDate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", 0, 0, 1) + "T00:00:00.000Z";
                endDate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", 0, 0, 2) + "T00:00:00.000Z";
                break;

        }
        selectionTransactionObj.get().addProperty("enrollStartDate", startDate);
        selectionTransactionObj.get().addProperty("enrollEndDate", endDate);
        requestParamsArray.set(new JsonArray());
        requestParamsArray.get().add(selectionTransactionObj.get());
        System.out.println(requestParamsArray.get().toString());

    }

    @When("I run selection transaction API")
    public void runSelectionTransactionAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsArray.get().toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("SUCCESS"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "SUCCESS");
        selectionTxnId.set(Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("selectionTxnId").toString()));
    }

    public void setConsumerId(String consumerId) {
        this.consumerId.set(consumerId);
    }

    @When("I provide information to Enrollment Create API for {string}, {string}, {string} and {string}")
    public void runEnrollmentCreateAPI(String enrollmentType, String enrollmentStartDate, String enrollmentEndDate, String enrollmentProviderReq) {
        requestParams.set(generateCreateEnrollmentRequest(enrollmentType));
        if (enrollmentProviderReq.equalsIgnoreCase("no") || enrollmentProviderReq.equalsIgnoreCase("false"))
            requestParams.get().addProperty("enrollmentProvider", (String) null);
        String startDate = "";
        String endDate = "";
        String dateFormat = "yyyy-MM-dd";

        switch (enrollmentStartDate) {
            case "current":
                startDate = apitdu.get().addDaysToPresentDate(dateFormat, 0, 0, 0);
                break;
            case "past":
                startDate = apitdu.get().addDaysToPresentDate(dateFormat, -29, 0, 0);
                break;
            case "future":
                startDate = apitdu.get().addDaysToPresentDate(dateFormat, 10, 10, 1);
                break;
            case "1stDayofNextMonth":
                startDate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", apitdu.get().getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0);
                break;
            case "1stDayofLastMonth":
                startDate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", apitdu.get().getNumberOfDaysToReduceForTheFirstOfThisMonth(), -1, 0);
                endDate = (String) null;
                break;
            case "1stDayofPresentMonth":
                startDate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", apitdu.get().getNumberOfDaysToReduceForTheFirstOfThisMonth(), 0, 0);
                endDate = (String) null;
                break;
            case "nextDay":
                startDate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", 2, 0, 0);
                endDate = (String) null;
                break;
        }

        switch (enrollmentEndDate) {
            case "current":
                endDate = apitdu.get().addDaysToPresentDate(dateFormat, 0, 0, 0);
                break;
            case "past":
                endDate = apitdu.get().addDaysToPresentDate(dateFormat, 0, 0, -1);
                break;
            case "future":
                endDate = apitdu.get().addDaysToPresentDate(dateFormat, 10, 2, 2);
                break;
        }
        requestParams.get().addProperty("startDate", startDate);

        if (enrollmentEndDate.equalsIgnoreCase("null")) {
            requestParams.get().addProperty("endDate", (String) null);
        } else {
            requestParams.get().addProperty("endDate", endDate);
        }
        System.out.println(requestParams.get());
    }

    @And("I run create enrollment API")
    public void runCreateEnrollmentAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        traceIdTOCheckEvents.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        enrollmentId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data").get("enrollmentId").getAsInt());
    }

    @When("I provide selection transaction status as {string} for create enrollment")
    public void provideProgramTypeInfoForSelectionTransactionAPI(String planStatus) {
        requestParams.get().addProperty("txnStatus", planStatus);
    }

    @When("I provide information to Enrollment update API")
    public void updatedEnrollmentCreateAPIPayLoad(DataTable dataToUpdate) {
        Map<String, String> data = dataToUpdate.asMap(String.class, String.class);
        if (requestParams.get() == null)
            requestParams.set(APIConsumerPopulationDmnController.getRequestParams());
        if (requestParams.get().has("enrollment")) {
            requestParams.set(requestParams.get().getAsJsonObject("enrollment"));
        }
        for (String key : data.keySet()) {
            switch (key) {
                case "anniversaryDate":
                    if (data.get(key).equalsIgnoreCase("{random}"))
                        requestParams.get().addProperty("anniversaryDate", apitdu.get().addDaysToPresentDate("yyyy-MM-dd", 0, 3, 0));
                    break;
            }

        }
        if (requestParams.get().has("eligibility")) {
            requestParams.get().remove("eligibility");
        }
        requestParamsArray.set(new JsonArray());
        requestParamsArray.get().add(requestParams.get());
        System.out.println(requestParamsArray.get());
    }

    @And("I run update enrollment API")
    public void runUpdateEnrollmentAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsArray.get().toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        traceIdTOCheckEvents.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify scenario output in the benefit status records are displayed as {string},{string}")
    public void verifyBenefitStatusRecordsDisplayedWithExpectedScenarioOutput(String eligibilityScenario, String enrollmentScenario) {
        // getting latest benefit status based on time frame
        JsonArray enrollmentRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        JsonObject enrollmentRecord = new JsonObject();
        if (enrollmentRecords.size() > 1) {
            List<String> times = new ArrayList<>();
            for (int i = 0; i < enrollmentRecords.size(); i++) {
                times.add(enrollmentRecords.get(i).getAsJsonObject().get("timeframe").toString().replaceAll("\"", ""));
            }
            if (times.containsAll(Arrays.asList("PAST", "Active", "FUTURE"))) {
                enrollmentRecord = enrollmentRecords.get(times.indexOf("FUTURE")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "FUTURE"))) {
                enrollmentRecord = enrollmentRecords.get(times.indexOf("FUTURE")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "null"))) {
                enrollmentRecord = enrollmentRecords.get(times.indexOf("null")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "PAST"))) {
                enrollmentRecord = enrollmentRecords.get(times.indexOf("Active")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("null", "null"))) {
                enrollmentRecord = enrollmentRecords.get(1).getAsJsonObject();
            }
        } else {
            enrollmentRecord = enrollmentRecords.get(0).getAsJsonObject();
        }
        if (enrollmentScenario.equals("null")) {
            assertTrue(enrollmentRecord.get("enrollmentScenario").isJsonNull(), "enrollmentScenario is not null! - FAIL");
        } else {
            try {
                assertEquals(enrollmentRecord.get("enrollmentScenario").getAsString(), enrollmentScenario, "enrollmentScenario mismatch!");
            } catch (UnsupportedOperationException e) {
                fail("enrollmentScenario is null! - FAIL!");
            }
        }
        if (eligibilityScenario.equals("null")) {
            assertTrue(enrollmentRecord.get("eligibilityScenario").isJsonNull(), "eligibilityScenario is not null! - FAIL");
        } else {
            try {
                assertEquals(enrollmentRecord.get("eligibilityScenario").getAsString(), eligibilityScenario, "eligibilityScenario mismatch!");
            } catch (UnsupportedOperationException e) {
                fail("eligibilityScenario is null! - FAIL!");
            }
        }
    }

    @And("I provide more information for create enrollment API")
    public void updateCreateEnrollmentAPIRequestBody(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "subProgramTypeCode":
                    requestParams.get().addProperty("subProgramTypeCode", data.get("subProgramTypeCode"));
                    break;
                case "programTypeCode":
                    requestParams.get().addProperty("programTypeCode", data.get("programTypeCode"));
                    break;
                case "planCode":
                    requestParams.get().addProperty("planCode", data.get("planCode"));
                    break;
                case "txnStatus":
                    requestParams.get().addProperty("txnStatus", data.get("txnStatus"));
                    break;
            }

        }
    }


    @Then("I verify consumer action as {string} and due date for population type {string}")
    public void i_verify_consumer_action_as_and_due_date_as(String expConsumerAction, String populationType) {
        if (expConsumerAction.equalsIgnoreCase("null")) {
            Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().getAsJsonArray("consumerActions").size() == 0);
           /* Assert.assertTrue(api.apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("changeAllowedUntil").isJsonNull());
            Assert.assertEquals("Unavailable", api.apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("action").getAsString());*/

        } else {
            String actConsumerAction = "";
            JsonArray consumerActions = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().getAsJsonArray("consumerActions");
            Assert.assertTrue(consumerActions.size() > 0, "Actions not available");
            for (JsonElement actionJsonElement : consumerActions) {
                actConsumerAction = actionJsonElement.getAsJsonObject().get("consumerAction").getAsString();
                if (actConsumerAction.equalsIgnoreCase(expConsumerAction)) {
                    String changeAllowedFrom = actionJsonElement.getAsJsonObject().get("changeAllowedFrom").getAsString().split("T")[0];
                    String changeAllowedUntil = actionJsonElement.getAsJsonObject().get("changeAllowedUntil").getAsString().split("T")[0];
                    java.time.LocalDateTime date = java.time.LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
                    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    switch (expConsumerAction) {
                        case "Enroll":
                            switch (populationType) {
                                case "NEWBORN":
                                    Assert.assertEquals(date.plusDays(5).format(formatter), changeAllowedUntil);
                                    break;
                                case "MEDICAID-GENERAL":
                                case "FOSTER-CARE":
                                case "PREGNANT-WOMEN":
                                    int numberOfDaysToAdd = apitdu.get().getNumberOfDaysToAddForNextFirstOfMonth();
                                    switch (date.plusDays(numberOfDaysToAdd).getDayOfWeek()) {
                                        case SATURDAY:
                                            numberOfDaysToAdd = numberOfDaysToAdd - 1;
                                        case SUNDAY:
                                            numberOfDaysToAdd = numberOfDaysToAdd - 2;
                                        default:
                                    }
                                    Assert.assertEquals(changeAllowedUntil, date.plusDays(numberOfDaysToAdd).format(formatter));
                                    break;
                            }
                            Assert.assertEquals("Required", actionJsonElement.getAsJsonObject().get("action").getAsString());
                            break;
                        case "Plan Change Anniversary":
                            String anniversary = "future";
                            LocalDate anniversaryDate = LocalDate.parse(apitdu.get().getStartDate("anniversaryDate"), formatter);
                            String expDate = anniversaryDate.minusMonths(2).format(formatter).substring(0, 8) + "15";
                          /*  if (anniversary.equalsIgnoreCase("future")) {
                                expDate = date.plusMonths(1).minusMonths(2).format(formatter).substring(0, 8) + "15";
                            } else if (anniversary.equalsIgnoreCase("future")) {
                                expDate = date.minusYears(11).minusMonths(2).format(formatter).substring(0, 8) + "15";
                            }*/
                            Assert.assertEquals(expDate, changeAllowedFrom);
                            Assert.assertEquals("Available", actionJsonElement.getAsJsonObject().get("action").getAsString());
                            break;
                        case "Plan Change Pre-lockin":
                            if (date.format(formatter).endsWith("01")) {
                                Assert.assertEquals(date.plusDays(92).format(formatter), changeAllowedUntil);
                            } else {
                                int numberOfDaysToAdd = apitdu.get().getNumberOfDaysToAddForNextFirstOfMonth();
                                Assert.assertEquals(date.plusDays(numberOfDaysToAdd + 92).format(formatter), changeAllowedUntil);
                            }
                            Assert.assertEquals("Available", actionJsonElement.getAsJsonObject().get("action").getAsString());
                            break;
                    }
                }
            }
        }
    }

    @Then("I verify benefit status information with data")
    public void I_verify_benefit_status_information_with_data(Map<String, String> data) {
        // there are 2 benefit records are some times, we need previous one based on different combination of timeframes
        JsonArray benefitRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        JsonObject benefitRecord = new JsonObject();
        if (benefitRecords.size() > 1) {
            List<String> times = new ArrayList<>();
            for (int i = 0; i < benefitRecords.size(); i++) {
                times.add(benefitRecords.get(i).getAsJsonObject().get("timeframe").toString().replaceAll("\"", ""));
            }
            if (times.containsAll(Arrays.asList("Active", "FUTURE"))) {
                benefitRecord = benefitRecords.get(times.indexOf("Active")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "null"))) {
                benefitRecord = benefitRecords.get(times.indexOf("Active")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "PAST"))) {
                benefitRecord = benefitRecords.get(times.indexOf("PAST")).getAsJsonObject();
            }
        } else if (benefitRecords.size() == 1) {
            benefitRecord = benefitRecords.get(0).getAsJsonObject();

        } else {
            if (data.containsKey("[0]") && data.get("[0]").equals("empty")) {
                assertEquals(benefitRecords.size(), 0, "Benefit Status is not empty!");
                return;
            }
        }
        Common_Steps_I_verify_benefit_status_information_with_data(benefitRecord, data);
    }

    @Then("I verify latest benefit status information with data")
    public void I_verify_latest_benefit_status_information_with_data(Map<String, String> data) {
        // there are 2 benefit records are some times, we need latest one based on different combination of timeframes
        JsonArray benefitRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        if (data.containsKey("empty")) {
            assertEquals(benefitRecords.size(), 0, "benefitRecords segment is not empty! - FAIL!");
            return;
        }
        JsonObject benefitRecord = new JsonObject();
        if (benefitRecords.size() > 1) {
            List<String> times = new ArrayList<>();
            for (int i = 0; i < benefitRecords.size(); i++) {
                times.add(benefitRecords.get(i).getAsJsonObject().get("timeframe").toString().replaceAll("\"", ""));
            }
            if (times.containsAll(Arrays.asList("Active", "FUTURE"))) {
                benefitRecord = benefitRecords.get(times.indexOf("FUTURE")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "null"))) {
                benefitRecord = benefitRecords.get(times.indexOf("null")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "PAST"))) {
                benefitRecord = benefitRecords.get(times.indexOf("Active")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "Active"))) {
                if (benefitRecords.get(0).getAsJsonObject().get("consumerbenefitStatusId").getAsInt() <
                        benefitRecords.get(1).getAsJsonObject().get("consumerbenefitStatusId").getAsInt()) {
                    benefitRecord = benefitRecords.get(1).getAsJsonObject();
                } else {
                    benefitRecord = benefitRecords.get(0).getAsJsonObject();
                }
            }
        } else {
            benefitRecord = benefitRecords.get(0).getAsJsonObject();
        }
        Common_Steps_I_verify_benefit_status_information_with_data(benefitRecord, data);
    }

    public void Common_Steps_I_verify_benefit_status_information_with_data(JsonObject dataObject, Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "startDate":
                case "anniversaryDate":
                case "lockInStartDate":
                    if (data.get(key).isEmpty() || data.get(key).equalsIgnoreCase("null")) {
                        assertTrue(dataObject.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        Assert.assertEquals(dataObject.get(key).getAsString(), apitdu.get().getStartDate(data.get(key)));
                    }
                    break;
                case "enrollmentEndDate":
                case "lockInEndDate":
                    if (data.get(key).isEmpty() || data.get(key).equalsIgnoreCase("null")) {
                        assertTrue(dataObject.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        Assert.assertEquals(dataObject.get(key).getAsString(), apitdu.get().getEndDate(data.get(key)));
                    }
                    break;
                case "eligStartDate":
                case "createdOn":
                case "updateddOn":
                    if (data.get(key).isEmpty() || data.get(key).equalsIgnoreCase("null")) {
                        assertTrue(dataObject.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        Assert.assertEquals(dataObject.get(key).getAsString().substring(0, 10), apitdu.get().getStartDate(data.get(key)));
                    }
                    break;
                case "eligEndDate":
                    if (data.get(key).isEmpty() || data.get(key).equalsIgnoreCase("null")) {
                        assertTrue(dataObject.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        Assert.assertEquals(dataObject.get(key).getAsString().substring(0, 10), apitdu.get().getEndDate(data.get(key)));
                    }
                    break;
                default:
                    if (data.get(key).isEmpty() || data.get(key).equalsIgnoreCase("null")) {
                        assertTrue(dataObject.get(key).isJsonNull(), key + " is not null!");
                    } else {
                        assertEquals(dataObject.get(key).getAsString().toLowerCase(), data.get(key).toLowerCase(), key + " mismatch!");
                    }
            }
        }
    }

    @Then("I verify following fields are captured in the newly created Enrollment Record")
    public void verifyEnrollmentDetails(DataTable dataTable) {
        CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
        //  Assert.assertEquals(api.apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("createdBy").getAsString(), contactRecord.userId.getText().substring(3));

        Map<Object, Object> data = dataTable.asMap((Type) String.class, String.class);
        if (data.containsKey("planCode")) {
            String planCode = data.get("planCode").toString();
            if (planCode.isEmpty() || planCode == null) {
                switch (CRMEnrollmentUpdateStepDef.selectedPlanName.get()) {
                    case "AMERIGROUP COMMUNITY CARE":
                        planCode = "84";
                        break;
                    case "CARESOURCE GEORGIA":
                        planCode = "87";
                        break;
                    case "PEACH STATE":
                        planCode = "85";
                        break;
                    case "WELLCARE":
                        planCode = "86";
                        break;
                }
            }
            if (data.containsKey("planCode")) {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("planCode").getAsString(), planCode);
            }
            if (data.containsKey("enrollmentType")) {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("enrollmentType").getAsString(), data.get("enrollmentType"));
            }
            if (data.containsKey("startDate")) {
                String enrollmentStartDate = apitdu.get().getStartDate(data.get("enrollmentStartDate").toString());
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("startDate").getAsString(), enrollmentStartDate);
            }
            if (data.containsKey("enrollmentEndDate")) {
                String enrollmentEndDate = apitdu.get().getEndDate(data.get("enrollmentEndDate").toString());
                if (!API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("endDate").isJsonNull())
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("endDate").getAsString(), enrollmentEndDate);
                else
                    Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("endDate").isJsonNull());
            }
            if (data.containsKey("anniversaryDate")) {
                String anniversaryDate = apitdu.get().getStartDate(data.get("anniversaryDate").toString());
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("anniversaryDate").getAsString(), anniversaryDate);
            }
            if (data.containsKey("selectionReason")) {
                String selectionReason = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("selectionReason").isJsonNull() ? "null" : API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("selectionReason").getAsString();
                Assert.assertEquals(selectionReason, data.get("selectionReason"));
            }
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("serviceRegionCode").getAsString(), data.get("serviceRegionCode"));
            if (data.containsKey("autoAssignIndicator")) {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("autoAssignIndicator").getAsString().toLowerCase(Locale.ROOT),
                        data.get("autoAssignIndicator").toString().replaceAll("\"", "").toLowerCase());
            }
            if (data.containsKey("lockInStartDate")) {
                String lockInStartDate = apitdu.get().getEndDate(data.get("lockInStartDate").toString());
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("lockInStartDate").getAsString(), lockInStartDate);
            }

            if (data.containsKey("lockInEndDate")) {
                String lockInEndDate = apitdu.get().getEndDate(data.get("lockInEndDate").toString());
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("lockInEndDate").getAsString(), lockInEndDate);
            }
            if (data.containsKey("lockInStatusCode")) {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("lockInStatusCode").getAsString(), data.get("lockInStatusCode"));
            }
            if (data.containsKey("lockInExemptionReason")) {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("lockInExemptionReason").getAsString(), data.get("lockInExemptionReason"));
            }
            if (data.containsKey("createdOn")) {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("createdOn").getAsString(), data.get("createdOn"));
            }
            if (data.containsKey("createdBy")) {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("createdBy").getAsString(), data.get("createdBy"));
            }
        }
    }

    public static JsonArray getRequestParamArray() {
        return requestParamsArray.get();
    }


    @Then("I run API call to get benefit status by consumer id {string}")
    public void iRunAPICallToGetBenefitStatusByConsumerId(String consumerId) {
        getEnrollmentBenefitStatusByConsumerId.set(getEnrollmentBenefitStatusByConsumerId.get().replace("{consumerId}", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(consumerId).substring(0, 5)));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentBenefitStatusByConsumerId.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
//        status message changed
//        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @And("I verify Enrollment Scenario is {string}")
    public void iVerifyEnrollmentScenarioIs(String message) {
        if (message.equalsIgnoreCase("null")) {
            assertNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.data[0].enrollmentScenario"), "enrollmentScenario is not: NULL");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.data[0].enrollmentScenario"), message,
                    "enrollmentScenario is not: " + message);
        }
    }

    @Then("Verify Consumer Action is {string}")
    public void verifyConsumerActionIs(String action) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.data[0].consumerActions[0].action"), action,
                "enrollmentScenario is not: " + action);
    }

    @And("I verify Scenarios {string}")
    public void iVerifyScenariosNotFound(String result) {
        if (result.equalsIgnoreCase("Not Found")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.data[1].eligibilityScenario"), "Not Found",
                    "eligibilityScenario is FOUND");
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.data[1].enrollmentScenario"), "Not Found",
                    "enrollmentScenario is FOUND");
        } else if (result.equalsIgnoreCase("Found")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.data[0].eligibilityScenario"), "New Retroactive Eligibility",
                    "eligibilityScenario NOT FOUND");
            assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.data[0].enrollmentScenario"), "New Retroactive Enrollment",
                    "enrollmentScenario NOT FOUND");
        }
    }

    @Then("I Verify Consumer Actions are {string}")
    public void iverifyConsumerActionsAre(String action) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().getAsJsonArray("consumerActions").get(0).getAsJsonObject().get("action").getAsString(), action);

    }

    @Then("I Verify Consumer Actions as following data")
    public void iverifyConsumerActionsAsFollowingData(Map<String, String> data) {
        // there are 2 benefit records are some times, we need latest one based on different combination of timeframes
        JsonArray benefitRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        if (data.containsKey("empty")) {
            assertEquals(benefitRecords.size(), 0, "benefitRecords segment is not empty! - FAIL!");
            return;
        }
        JsonArray objectData = null;
        String eligStartDate = null;
        List<String> times = new ArrayList<>();
        if (benefitRecords.size() > 1) {
            for (int i = 0; i < benefitRecords.size(); i++) {
                times.add(benefitRecords.get(i).getAsJsonObject().get("timeframe").toString().replaceAll("\"", ""));
            }
            if (times.containsAll(Arrays.asList("Active", "FUTURE"))) {
                objectData = benefitRecords.get(times.indexOf("FUTURE")).getAsJsonObject().getAsJsonArray("consumerActions");
                if(!benefitRecords.get(times.indexOf("FUTURE")).getAsJsonObject().get("eligStartDate").isJsonNull()){
                    eligStartDate = benefitRecords.get(times.indexOf("FUTURE")).getAsJsonObject().get("eligStartDate").getAsString();
                }
            } else if (times.containsAll(Arrays.asList("Active", "null"))) {
                objectData = benefitRecords.get(times.indexOf("null")).getAsJsonObject().getAsJsonArray("consumerActions");
                if(!benefitRecords.get(times.indexOf("null")).getAsJsonObject().get("eligStartDate").isJsonNull()){
                    eligStartDate = benefitRecords.get(times.indexOf("null")).getAsJsonObject().get("eligStartDate").getAsString();
                }
            } else if (times.containsAll(Arrays.asList("Active", "PAST"))) {
                objectData = benefitRecords.get(times.indexOf("Active")).getAsJsonObject().getAsJsonArray("consumerActions");
                if(!benefitRecords.get(times.indexOf("Active")).getAsJsonObject().get("eligStartDate").isJsonNull()){
                    eligStartDate = benefitRecords.get(times.indexOf("Active")).getAsJsonObject().get("eligStartDate").getAsString();
                }
            } else if (times.containsAll(Arrays.asList("Active", "Active"))) {
                if (benefitRecords.get(0).getAsJsonObject().get("consumerbenefitStatusId").getAsInt() >
                        benefitRecords.get(0).getAsJsonObject().get("consumerbenefitStatusId").getAsInt()) {
                    objectData = benefitRecords.get(0).getAsJsonObject().getAsJsonArray("consumerActions");
                    if(!benefitRecords.get(0).getAsJsonObject().get("eligStartDate").isJsonNull()){
                        eligStartDate = benefitRecords.get(0).getAsJsonObject().get("eligStartDate").getAsString();
                    }
                } else {
                    objectData = benefitRecords.get(1).getAsJsonObject().getAsJsonArray("consumerActions");
                    if(!benefitRecords.get(1).getAsJsonObject().get("eligStartDate").isJsonNull()){
                        eligStartDate = benefitRecords.get(1).getAsJsonObject().get("eligStartDate").getAsString();
                    }

                }
            }
        } else {
            objectData = benefitRecords.get(0).getAsJsonObject().getAsJsonArray("consumerActions");
            if(!benefitRecords.get(0).getAsJsonObject().get("eligStartDate").isJsonNull()){
                eligStartDate = benefitRecords.get(0).getAsJsonObject().get("eligStartDate").getAsString();
            }

        }
        eligStartDate = eligStartDate!=null?eligStartDate.split("T")[0]:null;
        if (data.containsKey("[0]") && data.get("[0]").equals("empty")) {
            assertEquals(objectData.size(), 0, "Consumer Actions are not empty!");
            return;
        }
        System.out.println("actualConsumerActions = " + objectData);
        // consumer actions coming in random order in the array, so I have to adapt to verify both action regardless in what order they are coming:
        // This implementation is adapted to verify many actions
        int maxNum = 0;
        for (String each : data.keySet()) {
            maxNum = Math.max(Integer.parseInt(each.substring(1, 2)), maxNum);
        }
        List<String> verifiedAction = new ArrayList<>();
        for (int j = 0; j < objectData.size(); j++) {
            JsonObject actualConsumerAction = objectData.get(j).getAsJsonObject();
            boolean actionFound = false;
            for (int i = 0; i <= maxNum; i++) {
                if (verifiedAction.contains(Integer.toString(i))) continue;
                if (data.containsKey("[" + i + "].consumerAction")
                        && data.get("[" + i + "].consumerAction").equals(actualConsumerAction.get("consumerAction").toString().replaceAll("\"", ""))) {
                    actionFound = true;
                    verifiedAction.add(Integer.toString(i));
                    System.out.println("Validating action " + data.get("[" + i + "].consumerAction"));
                    System.out.println("i = " + i);
                    if (data.containsKey("[" + i + "].action")) {
                        if (data.get("[" + i + "].action").isEmpty() || data.get("[" + i + "].action").contains("null")) {
                            assertTrue(actualConsumerAction.get("action").isJsonNull(), "[" + i + "].action is not null");
                        } else {
                            assertEquals(actualConsumerAction.get("action").getAsString(),
                                    data.get("[" + i + "].action"), "[" + i + "].action mismatch! - FAIL!");
                        }
                    }
                    if (data.containsKey("[" + i + "].planSelectionAllowed")) {
                        if (data.get("[" + i + "].planSelectionAllowed").isEmpty() || data.get("[" + i + "].planSelectionAllowed").contains("null")) {
                            assertTrue(actualConsumerAction.get("planSelectionAllowed").isJsonNull(), "[" + i + "].planSelectionAllowed is not null");
                        } else {
                            assertEquals(actualConsumerAction.get("planSelectionAllowed").getAsString(),
                                    data.get("[" + i + "].planSelectionAllowed"), "[" + i + "].planSelectionAllowed mismatch! - FAIL!");
                        }
                    }
                    String changeAllowedFromDate = null;
                    java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    if (data.containsKey("[" + i + "].changeAllowedFrom") && eligStartDate!=null) {
                        if (data.get("[" + i + "].changeAllowedFrom").isEmpty() || data.get("[" + i + "].changeAllowedFrom").contains("null")) {
                            assertTrue(actualConsumerAction.get("changeAllowedFrom").isJsonNull(), "[" + i + "].changeAllowedFrom is not null");
                        } else {
                            LocalDate currentDate = LocalDate.parse(LocalDate.now(ZoneOffset.UTC).format(df), df);
                            LocalDate parsedEligStartDate = LocalDate.parse(eligStartDate, df);
                            switch(data.get("[" + i + "].changeAllowedFrom")){
                                case "futureEnrollActionDCHF":
                                    long differenceInDays = ChronoUnit.DAYS.between(currentDate, parsedEligStartDate);
                                    if(differenceInDays>45){
                                        LocalDate tempChangeAllowedFrom = parsedEligStartDate.minusDays(45);
                                        if(tempChangeAllowedFrom.getMonthValue()<10){
                                            changeAllowedFromDate = tempChangeAllowedFrom.getYear()+"-0"+tempChangeAllowedFrom.getMonthValue()+"-16";
                                        }else{
                                            changeAllowedFromDate = tempChangeAllowedFrom.getYear()+"-"+tempChangeAllowedFrom.getMonthValue()+"-16";
                                        }
                                    }
                                    break;
                                case "pastEnrollActionDCHF":
                                    changeAllowedFromDate = currentDate.toString();
                                    break;
                                case "enrollActionNonDCHF":
                                    changeAllowedFromDate = parsedEligStartDate.minusDays(15).toString();
                                    break;
                                case "anniversaryWindow":
                                    System.out.println(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("[" + i + "].enrollmentStartDate")));
                                    String enrollmentStartDate = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("[" + i + "].enrollmentStartDate")).toString().split("T")[0];
                                    LocalDate tempDate = LocalDate.parse(enrollmentStartDate, df);
                                    int count = 1;
                                    while(tempDate.getYear() < LocalDate.now().getYear()-1){
                                        count++;
                                        tempDate = tempDate.plusYears(1);
                                    }
                                    changeAllowedFromDate = LocalDate.parse(enrollmentStartDate, df).plusYears(count).toString();
                                    break;
                                default:
                                    changeAllowedFromDate = apitdu.get().getStartDate(data.get("[" + i + "].changeAllowedFrom"));
                            }
                            assertEquals(actualConsumerAction.get("changeAllowedFrom").getAsString().substring(0, 10),
                                    changeAllowedFromDate, "[" + i + "].changeAllowedFrom mismatch! - FAIL!");
                        }
                    }
                    String changeAllowedUntilDate = null;
                    if (data.containsKey("[" + i + "].changeAllowedUntil") && eligStartDate!=null) {
                        if (data.get("[" + i + "].changeAllowedUntil").isEmpty() || data.get("[" + i + "].changeAllowedUntil").contains("null")) {
                            assertTrue(actualConsumerAction.get("changeAllowedUntil").isJsonNull(), "[" + i + "].changeAllowedUntil is not null");
                        } else {
                            switch(data.get("[" + i + "].changeAllowedUntil")){
                                case "futureEnrollActionDCHF":
                                case "pastEnrollActionDCHF":
                                    changeAllowedUntilDate = LocalDate.parse(changeAllowedFromDate, df).plusDays(29).toString();
                                    break;
                                case "enrollActionNonDCHF":
                                    changeAllowedUntilDate = changeAllowedFromDate;
                                    break;
                                case "anniversaryWindow":
                                    changeAllowedUntilDate = LocalDate.parse(changeAllowedFromDate, df).plusDays(89).toString();
                                    break;
                                default:
                                    changeAllowedUntilDate = apitdu.get().getEndDate(data.get("[" + i + "].changeAllowedUntil"));
                            }
                            assertEquals(actualConsumerAction.get("changeAllowedUntil").getAsString().substring(0, 10),
                                    changeAllowedUntilDate, "[" + i + "].changeAllowedUntil mismatch! - FAIL!");
                        }
                    }
                }
            }
            assertTrue(actionFound,
                    actualConsumerAction.get("consumerAction").toString().replaceAll("\"", "") + " is listed in available actions but not expected - FAIL!");
        }
        for (int i = 0; i <= maxNum; i++) {
            if (!verifiedAction.contains(Integer.toString(i))) {
                fail(data.get("[" + i + "].consumerAction") + " is missing in benefit status! - FAIL!");
            }
        }
    }

    @Given("I initiated get cut off dates by programTypeCode {string} and transactionType {string}")
    public void initializeGetCutOffDates(String programTypeCode, String transactionType) {
        getCutOffDates.set(getCutOffDates.get().replace("{programTypeCode}", programTypeCode));
        getCutOffDates.set(getCutOffDates.get().replace("{transactionType}", transactionType));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCutOffDates.get());
    }

    @When("I run get cut off dates api")
    public void runGetCutOffDates() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify cut off dates by programTypeCode {string} and transactionType {string}")
    public void validateCutOffDates(String programTypeCode, String transactionType) {
        String actualDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("data").getAsJsonObject().get("effectiveStartDate").getAsString();
        String today = apitdu.get().addDaysToPresentDate("dd", 0, 0, 0);
        String dateToValidate = "";
        switch (programTypeCode) {
            case "R":
                if (Integer.parseInt(today) <= 25)
                    dateToValidate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", apitdu.get().getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0);
                else
                    dateToValidate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", apitdu.get().getNumberOfDaysToAddForNextFirstOfMonth(), 1, 0);
                break;
            case "A":
                if (transactionType.equalsIgnoreCase("NewEnrollment")) {
                    if (Integer.parseInt(today) <= 10)
                        dateToValidate = apitdu.get().addDaysToPresentDate("yyyy-MM", 0, 0, 0) + "-15";
                    else if (Integer.parseInt(today) <= 25)
                        dateToValidate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", apitdu.get().getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0);
                    else
                        dateToValidate = apitdu.get().addDaysToPresentDate("yyyy-MM", 0, 1, 0) + "-15";
                } else if (transactionType.equalsIgnoreCase("PlanChange")) {
                    if (Integer.parseInt(today) <= 25)
                        dateToValidate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", apitdu.get().getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0);
                    else
                        dateToValidate = apitdu.get().addDaysToPresentDate("yyyy-MM-dd", apitdu.get().getNumberOfDaysToAddForNextFirstOfMonth(), 1, 0);
                }
                break;
            default:
        }
        Assert.assertEquals(actualDate, dateToValidate);
    }

    @Then("I update latest consumer actions by consumerId {string} with data")
    public void I_update_latest_consumer_actions_by_consumerId_with_data(String consumerId, Map<String, String> data) {
        initiatedGetBenefitStatusByConsumerID(consumerId);
        runGetEnrollmentByConsumerId();
        // there are 2 benefit records are some times, we need latest one based on different combination of timeframes
        JsonArray benefitRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        JsonObject objectData = null;
        List<String> times = new ArrayList<>();
        if (benefitRecords.size() > 1) {
            for (int i = 0; i < benefitRecords.size(); i++) {
                times.add(benefitRecords.get(i).getAsJsonObject().get("timeframe").toString().replaceAll("\"", ""));
            }
            if (times.containsAll(Arrays.asList("PAST", "Active", "FUTURE"))) {
                objectData = benefitRecords.get(times.indexOf("FUTURE")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "FUTURE"))) {
                objectData = benefitRecords.get(times.indexOf("FUTURE")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "null"))) {
                objectData = benefitRecords.get(times.indexOf("null")).getAsJsonObject();
            } else if (times.containsAll(Arrays.asList("Active", "PAST"))) {
                objectData = benefitRecords.get(times.indexOf("Active")).getAsJsonObject();
            }
        } else {
            objectData = benefitRecords.get(0).getAsJsonObject();
        }
        if (data.containsKey("[0]") && data.get("[0]").equals("empty")) {
            assertEquals(objectData.size(), 0, "Consumer Actions are not empty!");
            return;
        }
        // consumer actions coming in random order in the array, so I have to adapt to verify both action regardless in what order they are coming:
        // This implementation is adapted to verify many actions
        int maxNum = 0;
        for (String each : data.keySet()) {
            maxNum = Math.max(Integer.parseInt(each.substring(1, 2)), maxNum);
        }
        List<String> verifiedAction = new ArrayList<>();
        for (int i = 0; i <= maxNum; i++) {
            for (int j = 0; j < Objects.requireNonNull(objectData).size(); j++) {
                if (verifiedAction.contains(Integer.toString(i))) continue;
                if (data.containsKey("[" + i + "].consumerAction")
                        && data.get("[" + i + "].consumerAction").equals(objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().get("consumerAction").toString().replaceAll("\"", ""))) {
                    verifiedAction.add(Integer.toString(i));
                    System.out.println("Updating action " + data.get("[" + i + "].consumerAction"));
                    System.out.println("i = " + i);
                    if (data.containsKey("[" + i + "].action")) {
                        if (data.get("[" + i + "].action").isEmpty() || data.get("[" + i + "].action").equals("null")) {
                            objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().add("action", null);
                        } else if (data.get("[" + i + "].action").equals("delete")) {
                            objectData.getAsJsonArray("consumerActions").remove(j);
                        } else {
                            objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().addProperty("action", data.get("[" + i + "].action"));
                        }
                    }
                    if (data.containsKey("[" + i + "].planSelectionAllowed")) {
                        if (data.get("[" + i + "].planSelectionAllowed").isEmpty() || data.get("[" + i + "].planSelectionAllowed").equals("false")) {
                            objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().addProperty("planSelectionAllowed", false);
                        } else {
                            objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().addProperty("planSelectionAllowed", true);
                        }
                    }
                    if (data.containsKey("[" + i + "].changeAllowedFrom")) {
                        if (data.get("[" + i + "].changeAllowedFrom").isEmpty() || data.get("[" + i + "].changeAllowedFrom").contains("null")) {
                            objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().add("changeAllowedFrom", null);
                        } else {
                            objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().addProperty("changeAllowedFrom",
                                    apitdu.get().getStartDate(data.get("[" + i + "].changeAllowedFrom")) + "T00:00:00.000000+00:00");
                        }
                    }
                    if (data.containsKey("[" + i + "].changeAllowedUntil")) {
                        if (data.get("[" + i + "].changeAllowedUntil").isEmpty() || data.get("[" + i + "].changeAllowedUntil").contains("null")) {
                            objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().add("changeAllowedUntil", null);
                        } else {
                            objectData.getAsJsonArray("consumerActions").get(j).getAsJsonObject().addProperty("changeAllowedUntil",
                                    apitdu.get().getEndDate(data.get("[" + i + "].changeAllowedUntil")) + "T00:00:00.000000+00:00");
                        }
                    }
                }
            }
        }
        for (int i = 0; i <= maxNum; i++) {
            if (!verifiedAction.contains(Integer.toString(i))) {
                fail(data.get("[" + i + "].consumerAction") + " is missing in benefit status! - FAIL!");
            }
        }
        // after updating consumer actions now we will send it to API
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(postEnrollmentBenefitStatus.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(objectData.toString());
    }

    @Then("I update consumers benefit by consumerId {string} with data")
    public void I_update_consumers_benefit_by_consumerId_with_data(String consumerId, Map<String, String> data) {
        initiatedGetBenefitStatusByConsumerID(consumerId);
        runGetEnrollmentByConsumerId();
        JsonArray benefitRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        int maxNum = 0;
        for (String each : data.keySet()) {
            maxNum = Math.max(Integer.parseInt(each.substring(1, 2)), maxNum);
        }
        List<String> updatedEnrollment = new ArrayList<>();
        for (int j = 0; j < benefitRecords.size(); j++) {
            JsonObject benefitRecord = benefitRecords.get(j).getAsJsonObject();
            boolean enrollmentFound = false;
            for (int i = 0; i <= maxNum; i++) {
                if (updatedEnrollment.contains(Integer.toString(i))) continue;
                if (data.containsKey("[" + i + "].timeframe")
                        && data.get("[" + i + "].timeframe").equals(benefitRecord.get("timeframe")
                        .toString().replaceAll("\"", ""))) {
                    enrollmentFound = true;
                    updatedEnrollment.add(Integer.toString(i));
                    System.out.println("Updating benefit by time frame " + data.get("[" + i + "].timeframe"));
                    System.out.println("i = " + i);
                    if (data.containsKey("[" + i + "].eligStartDate")) {
                        if (data.get("[" + i + "].eligStartDate").isEmpty() || data.get("[" + i + "].eligStartDate").contains("null")) {
                            benefitRecord.add("eligStartDate", null);
                        } else {
                            benefitRecord.addProperty("eligStartDate", apitdu.get().getStartDate(data.get("[" + i + "].eligStartDate")));
                        }
                    }
                    if (data.containsKey("[" + i + "].eligEndDate")) {
                        if (data.get("[" + i + "].eligEndDate").isEmpty() || data.get("[" + i + "].eligEndDate").contains("null")) {
                            benefitRecord.add("eligEndDate", null);
                        } else {
                            benefitRecord.addProperty("eligEndDate", apitdu.get().getEndDate(data.get("[" + i + "].eligEndDate")));
                        }
                    }
                    if (data.containsKey("[" + i + "].consumerAction")) {
                        JsonArray consumerActions = benefitRecord.getAsJsonArray("consumerActions");
                        for(int k=0;k<consumerActions.size();k++){
                            if(consumerActions.get(k).getAsJsonObject().get("consumerAction").getAsString()
                                    .equalsIgnoreCase(data.get("[" + i + "].consumerAction"))){
                                consumerActions.get(k).getAsJsonObject().addProperty("changeAllowedFrom", apitdu.get().getStartDate(data.get("[" + i + "].changeAllowedFrom")));
                                consumerActions.get(k).getAsJsonObject().addProperty("changeAllowedUntil", apitdu.get().getStartDate(data.get("[" + i + "].changeAllowedUntil")));
                            }
                        }
                        benefitRecord.add("consumerActions", consumerActions);
                    }

                }
            }
            assertTrue(enrollmentFound,
                    benefitRecord.get("timeframe").toString().replaceAll("\"", "") + " is expected but not found in benefit records - FAIL!");
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentBenefitStatusByConsumerId.get().replace("/{consumerId}", ""));
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(benefitRecord.toString());
        }
        for (int i = 0; i <= maxNum; i++) {
            if (!updatedEnrollment.contains(Integer.toString(i))) {
                fail("[" + i + "].timeframe = " + data.get("[" + i + "].timeframe") + " is missing in benefit status! - FAIL!");
            }
        }
    }

    @Then("I update consumers enrollment by consumerId {string} with data")
    public void I_update_consumers_enrollment_by_consumerId_with_data(String consumerId, Map<String, String> data) {
        initiatedGetEnrolmentByConsumerID(consumerId);
        runGetEnrollmentByConsumerId();
        JsonArray enrollmentRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        int maxNum = 0;
        for (String each : data.keySet()) {
            maxNum = Math.max(Integer.parseInt(each.substring(1, 2)), maxNum);
        }
        List<String> updatedEnrollment = new ArrayList<>();
        for (int j = 0; j < enrollmentRecords.size(); j++) {
            JsonObject enrollmentRecord = enrollmentRecords.get(j).getAsJsonObject();
            boolean enrollmentFound = false;
            for (int i = 0; i <= maxNum; i++) {
                if (updatedEnrollment.contains(Integer.toString(i))) continue;
                if (data.containsKey("[" + i + "].enrollmentTimeframe")
                        && data.get("[" + i + "].enrollmentTimeframe").equals(enrollmentRecord.get("enrollmentTimeframe").toString().replaceAll("\"", ""))) {
                    enrollmentFound = true;
                    updatedEnrollment.add(Integer.toString(i));
                    System.out.println("Updating enrollment by time frame " + data.get("[" + i + "].enrollmentTimeframe"));
                    System.out.println("i = " + i);
                    if (data.containsKey("[" + i + "].startDate")) {
                        if (data.get("[" + i + "].startDate").isEmpty() || data.get("[" + i + "].startDate").contains("null")) {
                            enrollmentRecord.add("startDate", null);
                        } else {
                            enrollmentRecord.addProperty("startDate", apitdu.get().getStartDate(data.get("[" + i + "].startDate")));
                        }
                    }
                    if (data.containsKey("[" + i + "].endDate")) {
                        if (data.get("[" + i + "].endDate").isEmpty() || data.get("[" + i + "].endDate").contains("null")) {
                            enrollmentRecord.add("endDate", null);
                        } else {
                            enrollmentRecord.addProperty("endDate", apitdu.get().getEndDate(data.get("[" + i + "].endDate")));
                        }
                    }
                }
            }
            assertTrue(enrollmentFound,
                    enrollmentRecord.get("enrollmentTimeframe").toString().replaceAll("\"", "") + " is expected but not found in enrolment records - FAIL!");
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentByConsumerId.get().replace("/{consumerId}", ""));
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(enrollmentRecord.toString());
        }
        for (int i = 0; i <= maxNum; i++) {
            if (!updatedEnrollment.contains(Integer.toString(i))) {
                fail("[" + i + "].enrollmentTimeframe = " + data.get("[" + i + "].enrollmentTimeframe") + " is missing in enrollment status! - FAIL!");
            }
        }
    }

    @Then("I verify new correspondences generated for consumerId {string} with data")
    public void I_verify_new_correspondences_generated_for_consumerId_with_data(String consumerId, Map<String, String> data) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(correspondencesEndPoint.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        JsonArray correspondences = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        for (int i = 0; i < correspondences.size(); i++) {
            if (data.containsKey("[0].correspondenceTypeToMatch") && correspondences.get(i).getAsJsonObject().get("correspondenceType").getAsString().equalsIgnoreCase(data.get("[0].correspondenceTypeToMatch"))) {
                correspondenceId.set(correspondences.get(i).getAsJsonObject().get("correspondenceId").getAsInt());
                break;
            }
        }
        if (data.containsKey("[0]") && data.get("[0]").equals("empty")) {
            assertEquals(correspondences.size(), 0, "Correspondence is not empty! - Fail");
            return;
        }
        for (String key : data.keySet()) {
            int i = Integer.parseInt(key.substring(1, 2));
            String field = key.split("\\.", 2)[1];
            switch (field) {
                case "correspondenceRequestId":
                case "consumerId":
                case "enrollmentId":
                case "caseId":
                case "externalConsumerId":
                case "externalCaseId":
                case "correspondenceId":
                case "status":
                case "correspondenceType":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(correspondences.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null! - Fail!");
                    } else {
                        assertEquals(correspondences.get(i).getAsJsonObject().get(field).getAsString(),
                                String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))), key + " mismatch!");
                    }
                    break;
                case "createdOn":
                case "updatedOn":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(correspondences.get(i).getAsJsonObject().get(field).isJsonNull(), key + " is not null! - Fail!");
                    } else {
                        assertEquals(correspondences.get(i).getAsJsonObject().get(field).getAsString().substring(0, 10),
                                apitdu.get().getStartDate(data.get(key)), key + " mismatch!");
                    }
                    break;
            }
        }
    }

    @Then("I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name {string}")
    public void I_save_enrollment_ids_by_discontinued_and_selected_with_name(String name) {
        JsonArray enrollmentRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        String discontinuedEnrollmentId = "";
        String discontinuedPlanCode = "";
        String discontinuedEndDate = "";
        String selectedEnrollmentId = "";
        String selectedPlanCode = "";
        for (int i = 0; i < enrollmentRecords.size(); i++) {
            if (Arrays.asList("DISENROLL_SUBMITTED", "DISENROLL_REQUESTED", "DISENROLLED")
                    .contains(enrollmentRecords.get(i).getAsJsonObject().get("status").getAsString())) {
                discontinuedEnrollmentId = enrollmentRecords.get(i).getAsJsonObject().get("enrollmentId").getAsString();
                discontinuedPlanCode = enrollmentRecords.get(i).getAsJsonObject().get("planCode").getAsString();
                discontinuedEndDate = enrollmentRecords.get(i).getAsJsonObject().get("endDate").getAsString();
            } else {
                selectedEnrollmentId = enrollmentRecords.get(i).getAsJsonObject().get("enrollmentId").getAsString();
                selectedPlanCode = enrollmentRecords.get(i).getAsJsonObject().get("planCode").getAsString();
            }
        }
        Api_Body api_req = new EventBuilder()
                .body("{\"discontinuedEnrollmentId\": \"" + discontinuedEnrollmentId + "\", \"selectedEnrollmentId\": \"" + selectedEnrollmentId + "\", "
                        + "\"discontinuedPlanCode\": \"" + discontinuedPlanCode + "\", \"selectedPlanCode\": \"" + selectedPlanCode + "\", "
                        + "\"discontinuedEndDate\": \"" + discontinuedEndDate + "\"},")
                .name(name)
                .build();
        stg.get().addProduced(api_req);
    }

    @Then("I save FUTURE enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name {string}")
    public void I_save_future_enrollment_ids_by_discontinued_and_selected_with_name(String name) {
        JsonArray enrollmentRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        String futureDiscontinuedEnrollmentId = "";
        String futureDiscontinuedPlanCode = "";
        String futureSelectedEnrollmentId = "";
        String futureSelectedPlanCode = "";
        for (int i = 0; i < enrollmentRecords.size(); i++) {
            if (Arrays.asList("DISENROLL_SUBMITTED", "DISENROLL_REQUESTED", "DISENROLLED")
                    .contains(enrollmentRecords.get(i).getAsJsonObject().get("status").getAsString())
                    && enrollmentRecords.get(i).getAsJsonObject().get("enrollmentTimeframe").getAsString().equals("FUTURE")
            ) {
                futureDiscontinuedEnrollmentId = enrollmentRecords.get(i).getAsJsonObject().get("enrollmentId").getAsString();
                futureDiscontinuedPlanCode = enrollmentRecords.get(i).getAsJsonObject().get("planCode").getAsString();
            } else if (Arrays.asList("SUBMITTED_TO_STATE", "SELECTION_MADE", "DISENROLLED")
                    .contains(enrollmentRecords.get(i).getAsJsonObject().get("status").getAsString())
                    && enrollmentRecords.get(i).getAsJsonObject().get("enrollmentTimeframe").getAsString().equals("FUTURE")
            ) {
                futureSelectedEnrollmentId = enrollmentRecords.get(i).getAsJsonObject().get("enrollmentId").getAsString();
                futureSelectedPlanCode = enrollmentRecords.get(i).getAsJsonObject().get("planCode").getAsString();
            }
        }
        System.out.println("futureDiscontinuedEnrollmentId = " + futureDiscontinuedEnrollmentId);
        System.out.println("futureSelectedEnrollmentId = " + futureSelectedEnrollmentId);
        System.out.println("futureDiscontinuedPlanCode = " + futureDiscontinuedPlanCode);
        System.out.println("futureSelectedPlanCode = " + futureSelectedPlanCode);
        Api_Body api_req = new EventBuilder()
                .body("{\"futureDiscontinuedEnrollmentId\": \"" + futureDiscontinuedEnrollmentId + "\", \"futureSelectedEnrollmentId\": \"" + futureSelectedEnrollmentId + "\", "
                        + "\"futureDiscontinuedPlanCode\": \"" + futureDiscontinuedPlanCode + "\", \"futureSelectedPlanCode\": \"" + futureSelectedPlanCode + "\"},")
                .name(name)
                .build();
        stg.get().addProduced(api_req);
    }


    @Then("I verify enrollment by consumer id {string} with data for multiple records")
    public void iVerifyEnrollmentByConsumerIdWithDataForMultipleRecords(String consumerId, Map<String, String> data) {
        Map<Integer, Map<String, String>> indexWiseData = new HashMap<Integer, Map<String, String>>();
        for (String key : data.keySet()) {
            int index = Integer.parseInt(key.substring(1, 2));
            if (indexWiseData.containsKey(index)) {
                indexWiseData.get(index).put(key.substring(4), data.get(key));
            } else {
                indexWiseData.put(index, new HashMap<String, String>());
            }
        }

        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentByConsumerId.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        JsonArray enrollmentRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        for (int i = 0; i < indexWiseData.size(); i++) {
            commonStepsForLatestAndCurrentEnrollmentVerification(enrollmentRecords.get(i).getAsJsonObject(), indexWiseData.get(i));
        }

    }


    @Then("I verify benefit statues and program population for multiple consumers")
    public void iVerifyBenefitStatusAndProgramPopulationForMultipleRecords(Map<String, String> data) {
        String consumerIds = "";
        Map<Integer, Map<String, String>> indexWiseData = new HashMap<Integer, Map<String, String>>();
        for (String key : data.keySet()) {
            int index = Integer.parseInt(key.substring(1, 2));
            if (indexWiseData.containsKey(index)) {
                indexWiseData.get(index).put(key.substring(4), data.get(key));
            } else {
                indexWiseData.put(index, new HashMap<String, String>());
                indexWiseData.get(index).put(key.substring(4), data.get(key));
            }
            if (key.contains("consumerId")) {
                consumerId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString().replace(".0", ""));
                consumerIds += consumerId.get() + ",";
            }
        }
        consumerIds = consumerIds.substring(0, consumerIds.length() - 1);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(multipleConsumerBenefitSearchEndPoint.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody("{\"consumerIds\":[" + consumerIds + "]}");

        JsonArray benefitStatusRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
        if (data.containsKey("[0]") && data.get("[0]").equals("empty")) {
            assertEquals(benefitStatusRecords.size(), 0, "benefitStatusRecords is not empty! - Fail");
            return;
        }
        for (int i = 0; i < benefitStatusRecords.size(); i++) {
            commonStepsForBenefitStatusVerification(benefitStatusRecords.get(i).getAsJsonObject(), indexWiseData.get(benefitStatusRecords.size() - (i + 1)));
        }
    }


    public void commonStepsForBenefitStatusVerification(JsonObject benefitStatusRecord, Map<String, String> data) {
        System.out.println("benefitStatusRecord = " + benefitStatusRecord);

        for (String key : data.keySet()) {
            switch (key) {
                case "consumerId":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(benefitStatusRecord.get(key).isJsonNull(), key + " is not null! - Fail!");
                    } else {
                        assertEquals(benefitStatusRecord.get(key).getAsString(),
                                String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))), key + " mismatch!");
                    }
                    break;
                case "eligEndDate":
                    if (data.get(key).equals("") || data.get(key).equals("null")) {
                        assertTrue(benefitStatusRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(benefitStatusRecord.get(key).getAsString(),
                                apitdu.get().getEndDate(data.get(key)) + "T00:00:00.000000+00:00", key + " mismatch! - FAIL!");
                    }
                    break;
                case "eligStartDate":
                    if (data.get(key).equals("") || data.get(key).equals("null")) {
                        assertTrue(benefitStatusRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(benefitStatusRecord.get(key).getAsString(),
                                apitdu.get().getStartDate(data.get(key)) + "T00:00:00.000000+00:00", key + " mismatch! - FAIL!");
                    }
                    break;
                default:
                    if (data.get(key).equals("") || data.get(key).equals("null")) {
                        assertTrue(benefitStatusRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        assertEquals(benefitStatusRecord.get(key).getAsString(), data.get(key), key + " mismatch! - FAIL!");
                    }
                    break;
            }
        }
    }

    @Then("I verify equal auto plan assignment")
    public void verifyEqualPlanAssignment(List<String> expectedValues) {
        Map<String, Integer> map = new HashMap<>();
        for (String planCode : planCodes.get()) {
            if (map.containsKey(planCode)) {
                map.put(planCode, map.get(planCode) + 1);
            } else {
                map.put(planCode, 1);
            }
        }
        if (planCodes.get().size() == 1) {
            assertTrue(expectedValues.containsAll(planCodes.get()));
        } else if (planCodes.get().size() == 2) {
            assertTrue(expectedValues.containsAll(planCodes.get()));
            assertTrue(planCodes.get().get(0) != planCodes.get().get(1));
        } else {
            assertTrue(expectedValues.containsAll(map.keySet()));
            assertTrue(map.keySet().containsAll(expectedValues));
            int totalPlanCodesSize = planCodes.get().size();
            int expPlanCodesSize = expectedValues.size();
            for (String planCode : map.keySet()) {
                if (totalPlanCodesSize % expPlanCodesSize == 0) {
                    assertEquals((int) map.get(planCode), totalPlanCodesSize / expPlanCodesSize);
                } else {
                    assertTrue(map.get(planCode) == totalPlanCodesSize / expPlanCodesSize
                            || map.get(planCode) == totalPlanCodesSize / expPlanCodesSize + 1);
                }
            }

        }

    }

    @Then("I verify correspondence details of correspondence id {string}")
    public void verifyCorrespondenceDetails(String corrId, Map<String, String> data) {
        if (corrId.isEmpty())
            corrId = String.valueOf(correspondenceId.get());
        JsonPath jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(corrId);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject);
        JsonObject bodyData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("bodyData");

        for(String key:data.keySet()){
            switch(key){
                case "mcoPhoneNumber":
                case "pcpPhoneNumber":
                case "pcp":
                    if (data.get(key).equalsIgnoreCase("null")) {
                        assertTrue(bodyData.get(key).isJsonNull());
                    } else{
                        if(key.equalsIgnoreCase("mcoPhoneNumber") && data.get(key).contains("planCode")){
                            assertEquals(bodyData.get(key).getAsString(), getMcoPhoneNumberFromPlanCode(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString()));
                        }else{
                            assertEquals(bodyData.get(key).getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)));
                        }

                    }
                    break;
                case "planCode":
                    assertEquals(bodyData.get("planCode").getAsString(), data.get(key));
                    break;
                case "startDate":
                case "dateSignedUp":
                case "date":
                    if(data.get(key).contains(".")){
                        assertEquals(bodyData.get(key).getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)));
                    }else{
                        assertEquals(bodyData.get(key).getAsString(), apitdu.get().getStartDate(data.get(key)));
                    }

                    break;
                case "planChangeDueDate":
                    assertEquals(bodyData.get("planChangeDueDate").getAsString(), apitdu.get().getEndDate(data.get("planChangeDueDate")));
                    break;
                case "healthPlan":
                case "priorHealthPlan":
                    if(bodyData.get(key).isJsonArray()){
                        JsonArray healthPlansArray = bodyData.get(key).getAsJsonArray();
                        List<String> expValues = Arrays.stream(data.get(key).split(",")).collect(Collectors.toList());
                        List<String> actValues = new ArrayList<>();
                        for(int i=0;i<healthPlansArray.size();i++){
                            actValues.add(healthPlansArray.get(i).getAsString());
                        }
                        assertTrue(actValues.containsAll(expValues) && expValues.containsAll(actValues), "exp:"+expValues+"but act:"+actValues);
                    }else{
                        if(data.get(key).contains("planCode")){
                            assertEquals(bodyData.get(key).getAsString(), getPlanNameFromPlanCode(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString()));
                        }else{
                            assertEquals(bodyData.get(key).getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString());
                        }

                    }
                    break;
                case "anniversaryDate":
                    if(data.get("anniversaryDate").contains("startDate")){
                        String startDate = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString();
                        assertEquals(bodyData.get("anniversaryDate").getAsString(), LocalDate.parse(startDate).plusYears(1).toString());
                    }else{
                        assertEquals(bodyData.get("anniversaryDate").getAsString(), apitdu.get().getStartDate(data.get("anniversaryDate")));
                    }

                    break;
                default:
                    throw new RuntimeException("No case is matched with key "+key);
            }
        }

    }

    @Then("I verify letter type {string} not generated for non DCHF consumer with id {string}")
    public void verifyLetter160NotGeneratedForNonDCHF(String letterType, String consumerId) {
        if (consumerId.isEmpty()) {
            consumerId = this.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(correspondencesEndPoint.get().replace("{consumerId}", consumerId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("data").isJsonNull()) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("data").isJsonNull());
        } else {
            boolean letter160Found = false;
            JsonArray correspondences = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").getAsJsonArray();
            for (int i = 0; i < correspondences.size(); i++) {
                if (correspondences.get(i).getAsJsonObject().get("correspondenceType").getAsString().equalsIgnoreCase(letterType)) {
                    letter160Found = true;
                    break;
                }
            }
            assertFalse(letter160Found, "Letter 160 found for NON DCHF population");
        }

    }

    public static Map<String, Map<String, String>> getEnrollmentChannelDetails() {
        return enrollmentChannelDetails.get();
    }

    @Given("I initiated enrollment letter API")
    public void initiatedEnrollmentLetterApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(enrollmentLetterEndPoint.get());
    }

    @Given("I run enrollment letter API")
    public void initiatedEnrollmentLetterApi(Map<String, String> data) {
        JsonObject letterObject = new JsonObject();
        for(String key:data.keySet()){
            switch(key){
                case "mmisCode":
                    letterObject.addProperty("mmisCode", data.get(key));
                    break;
                default:
                    throw new RuntimeException("No key is matched for "+key);
            }
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(letterObject.toString());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 202);
    }

    @Given("I initiated enrollment letter transaction status API")
    public void initiatedEnrollmentLetterTransactionStatusApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(enrollmentLetterTransactionStatusEndPoint.get());
    }

    @And("I run enrollment letter transaction status API")
    public void runEnrollmentLetterTransactionStatusApi(Map<String, String> data) {
        JsonObject request = new JsonObject();
        for(String key:data.keySet()){
            switch(key){
                case "transactionType":
                    request.addProperty("transactionType", data.get(key));
                    break;
                case "moduleName":
                    request.addProperty("moduleName", data.get(key));
                    break;
                case "createdOn":
                    request.addProperty(key, apitdu.get().getStartDate(data.get(key)));
                    break;
            }

        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify enrollment letter transaction status API response")
    public void verifyEnrollmentLetterTransactionStatusApi(Map<String, String> data) {
        JsonObject dataObj = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data");
        Assert.assertEquals(dataObj.get("transactionType").getAsString(), data.get("transactionType"));
        Assert.assertEquals(dataObj.get("moduleName").getAsString(), data.get("moduleName"));
        Assert.assertEquals(dataObj.get("transactionStatus").getAsString(), data.get("transactionStatus"));
        Assert.assertEquals(dataObj.get("createdOn").getAsString(), apitdu.get().getStartDate(data.get("createdOn")));
        Assert.assertEquals(dataObj.get("updatedOn").getAsString(), apitdu.get().getStartDate(data.get("updatedOn")));
    }


    @Given("I run get enrollment transaction by status {string} and date {string}")
    public void runGetTransactionByStatusAndDate(String status, String date) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseURI.get());
        if(status.contains(".")){
            status = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(status).toString();
        }
        if(date.contains(".")){
            date = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(date).toString().split("\\.")[0];
            java.time.LocalDateTime dateTime = java.time.LocalDateTime.parse(date);
            dateTime = dateTime.minusSeconds(1);
            date = dateTime.toString().replace("T", " ");
           if(date.length()<18){
               date = date + ":00";
           }
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentTransactionByStatusAndDate.get().replace("{txnStatus}", status)
                .replace("{txnStatusDate}", date));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @Then("I verify enrollment transaction status and date")
    public void verifyEnrollmentTransactionStatusAndDate(Map<String, String> data) {
        JsonArray dataArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        Assert.assertTrue(dataArray.size()>0);
        for(int i=0;i<dataArray.size();i++){
            JsonObject transactionObj = dataArray.get(i).getAsJsonObject();
            Assert.assertEquals(transactionObj.get("txnStatus").getAsString(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("txnStatus")).toString());
            java.time.LocalDateTime datePassedInUrl = null;
            if(data.get("txnStatusDate").contains(".")){
                String date = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("txnStatusDate")).toString().split("\\.")[0];
                java.time.LocalDateTime dateTime = java.time.LocalDateTime.parse(date);
                datePassedInUrl = dateTime.minusSeconds(1);
            }
            java.time.LocalDateTime txnStatusDateFromResponse = java.time.LocalDateTime.parse(transactionObj.get("txnStatusDate").getAsString().split("\\.")[0]);
            Assert.assertTrue(txnStatusDateFromResponse.compareTo(datePassedInUrl)>0);
            if(data.containsKey("enrollmentProviders")){
               JsonArray actEnrollmentProvidersArray = transactionObj.getAsJsonArray("enrollmentProviders");
                JsonParser parser = new JsonParser();
                JsonArray expEnrollmentProvidersArray =  parser.parse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("enrollmentProviders")).toString()).getAsJsonArray();
                for(int j=0;j<expEnrollmentProvidersArray.size();j++){
                    JsonObject expProvider = expEnrollmentProvidersArray.get(j).getAsJsonObject();
                    JsonObject actProvider = null;
                    for(int k=0;k<actEnrollmentProvidersArray.size();k++){
                        actProvider = actEnrollmentProvidersArray.get(k).getAsJsonObject();
                        if(actProvider.get("providerId").getAsString().equalsIgnoreCase(expProvider.get("providerId").getAsString()))
                            break;
                    }
                    Assert.assertEquals(actProvider.get("providerFirstName").getAsString(), expProvider.get("providerFirstName").getAsString());
                    Assert.assertEquals(actProvider.get("providerMiddleName").getAsString(), expProvider.get("providerMiddleName").getAsString());
                    Assert.assertEquals(actProvider.get("providerLastName").getAsString(), expProvider.get("providerLastName").getAsString());
                    Assert.assertEquals(actProvider.get("providerNpi").getAsString(), expProvider.get("providerNpi").getAsString());
                    Assert.assertEquals(actProvider.get("providerId").getAsString(), expProvider.get("providerId").getAsString());
                }
            }
        }
    }

}