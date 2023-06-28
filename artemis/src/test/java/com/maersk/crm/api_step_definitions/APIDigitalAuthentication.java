package com.maersk.crm.api_step_definitions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maersk.crm.api_pojos.ApiDigitalAuthResponse;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.testng.Assert;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APIDigitalAuthentication extends CRMUtilities implements ApiBase, UiBase {
    public static final ThreadLocal<String> staticCaseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> caseId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<Integer> consumerId = ThreadLocal.withInitial(() -> 0);
    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String digitalIntBaseURI = ConfigurationReader.getProperty("apiDigitalIntegration");
    private String digitalPrimaryContactBaseURI = ConfigurationReader.getProperty("digitalPrimaryContactBaseURI");
    private String createCaseWithConsumers = "app/crm/caseloader";
    private String digitalAuthEndPoint = "/app/crm/case/authentication";
    private final ThreadLocal<String> digitalPrimaryContactEndPoint = ThreadLocal.withInitial(() -> "/app/crm/cases/" + caseId.get() + "/contacts/primary");
    private final ThreadLocal<String> digitalCaseConsumerInfoEndPoint = ThreadLocal.withInitial(() -> "/app/crm/cases/" + caseId.get() + "/members/active");


    private final ThreadLocal<String> DOB = ThreadLocal.withInitial(() -> addDaysToCurrentDate("yyyy-MM-dd", -8000));
    private final ThreadLocal<String> SSN = ThreadLocal.withInitial(() -> RandomStringUtils.random(9, false, true));
    private final ThreadLocal<String> FN = ThreadLocal.withInitial(() -> RandomStringUtils.random(10, true, false));
    private final ThreadLocal<String> LN = ThreadLocal.withInitial(() -> RandomStringUtils.random(10, true, false));
    private final ThreadLocal<String> externalId = ThreadLocal.withInitial(() -> RandomStringUtils.random(10, true, true));
    private final ThreadLocal<String> caseIdentificationNumber = ThreadLocal.withInitial(() -> RandomStringUtils.random(10, true, true));
    private String[] externalIdArray = {"CHIP", "MEDICAID"};
    private final ThreadLocal<String[]> externalIdTypes = ThreadLocal.withInitial(() -> externalIdArray);
    private final ThreadLocal<String> externalIdType = ThreadLocal.withInitial(() -> externalIdArray[0]);
    private final ThreadLocal<String> consumerRole = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressStreet1 = ThreadLocal.withInitial(() -> RandomStringUtils.random(10, true, true));
    private final ThreadLocal<String> addressCity = ThreadLocal.withInitial(() -> "Columbia");
    private final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(() -> "Howard");
    private final ThreadLocal<String> addressState = ThreadLocal.withInitial(() -> "MD");
    private final ThreadLocal<String> addressStreet2 = ThreadLocal.withInitial(() -> RandomStringUtils.random(2, true, true));
    private String[] addressTypes = {"Mailing", "Physical"};
    private String addressType = addressTypes[0];
    private final ThreadLocal<String> addressZip = ThreadLocal.withInitial(() -> RandomStringUtils.random(5, false, true));
    private final ThreadLocal<String> addressZipFour = ThreadLocal.withInitial(() -> RandomStringUtils.random(4, false, true));
    private final ThreadLocal<String> phoneNumber = ThreadLocal.withInitial(() -> 2 + RandomStringUtils.random(9, false, true));
    private final ThreadLocal<String> phoneType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> emailAddress = ThreadLocal.withInitial(() -> RandomStringUtils.random(5, true, false) + "@qatest.com");
    private final ThreadLocal<String> traceId = ThreadLocal.withInitial(() -> RandomStringUtils.random(12, false, true));
    private final ThreadLocal<ZonedDateTime> nowUTC = ThreadLocal.withInitial(() -> ZonedDateTime.now(ZoneOffset.UTC));
    private final ThreadLocal<LocalDateTime> localDateTime = ThreadLocal.withInitial(() -> LocalDateTime.ofInstant(nowUTC.get().toInstant(), ZoneOffset.UTC));
    private final ThreadLocal<String> expectedDateAndHour = ThreadLocal.withInitial(() -> localDateTime.toString().substring(0, 13));
    final ThreadLocal<String> offsetTime = ThreadLocal.withInitial(() -> OffsetDateTime.now(ZoneOffset.UTC).toString().replaceAll("021", "029"));
    final ThreadLocal<String> pastOneDayDate = ThreadLocal.withInitial(() -> OffsetDateTime.now(ZoneOffset.UTC).minusDays(1).toString().replaceAll("021", "029"));
    private final ThreadLocal<String> effectiveEndDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> caseIdentificationNumberTypeReportLabel = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> externalCaseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> updatedBy = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> caseIdentificationNumberTypeId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> createdBy = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> effectiveStartDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> updatedOn = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> createdOn = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> agencyName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> searchQAEvent = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> caseIdentificationNumberId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<JsonObject> caseloaderJO = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> digitalAuthJO = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<ResponseBody> digitalAuthRespBody = new ThreadLocal<>();
    private final ThreadLocal<ResponseBody> digitalPrimaryContactRespBody = new ThreadLocal<>();
    private final ThreadLocal<ResponseBody> digitalCaseConsumerRespBody = new ThreadLocal<>();
    final ThreadLocal<ApiDigitalAuthResponse> authenticationPojo = ThreadLocal.withInitial(ApiDigitalAuthResponse::new);
    final ThreadLocal<DateFormat> dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    final ThreadLocal<SimpleDateFormat> dateForm = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    final ThreadLocal<Date> todayDate = ThreadLocal.withInitial(() -> new Date());
    final ThreadLocal<String> currentDate = ThreadLocal.withInitial(() -> dateForm.get().format(todayDate));
  /*  PublishDPBIEventsOutboundCorrespondenceChangesStepDefs outCorrEvents = new PublishDPBIEventsOutboundCorrespondenceChangesStepDefs();
    private ApiTestDataUtil testDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private APIClassUtil classUtil = APIClassUtil.getApiClassUtilThreadLocal();
    APIConsumerRestController consumerSearch = new APIConsumerRestController();*/

    private static String addDaysToCurrentDate(String format, int numOfDaysoAdd) {
        String dateToReturn = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, numOfDaysoAdd);
        dateToReturn = sdf.format(cal.getTime());
        return dateToReturn;
    }

    @Given("I set up test data for a new {string} caseConsumer with {string} created by caseloader with {string}, {string}, {string}")
    public void iSetUpTestDataForANewCaseConsumerWithCreatedByCaseloader(String status, String role, String primary, String contactType, String channel) throws Throwable {
        switch (role) {
            case "CM":
                consumerRole.set("Member");
                break;
            case "PI":
                consumerRole.set("Primary Individual");
                break;
            case "AR":
                consumerRole.set("Authorized Representative");
                break;
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest")
                .get(0).getAsJsonObject().getAsJsonObject("case")
                .addProperty("correlationId", traceId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("correlationId", traceId.get());
        System.out.println("caseIdentificationNumber " + caseIdentificationNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest")
                .get(0).getAsJsonObject().getAsJsonObject("case")
                .addProperty("caseIdentificationNumber", caseIdentificationNumber.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerDateOfBirth", DOB.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerSSN", SSN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerFirstName", FN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerLastName", LN.get());
        if (status.equalsIgnoreCase("Inactive")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                    .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                    .getAsJsonArray("consumers").get(0).getAsJsonObject()
                    .addProperty("effectiveEndDate", pastOneDayDate.get());
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest")
                .get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .getAsJsonArray("consumerIdentificationNumber").get(0)
                .getAsJsonObject().addProperty("externalConsumerId", externalId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest") // array
                .get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .getAsJsonArray("consumerIdentificationNumber").get(0)
                .getAsJsonObject().addProperty("identificationNumberType", externalIdType.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerRole", consumerRole.get());
        if (contactType.length() > 1) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                    getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                    getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                    addProperty("primaryIndicator", primary);
            if (contactType.equalsIgnoreCase("email")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("emailAddress", emailAddress.get());
            }

            if (contactType.equalsIgnoreCase("address")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("addressStreet1", addressStreet1.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("addressStreet2", addressStreet2.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("addressCity", addressCity.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("addressCounty", addressCounty.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("addressState", addressState.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("addressType", addressType);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("addressZip", addressZip.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("addressZipFour", addressZipFour.get());
            }
            if (contactType.equalsIgnoreCase("phone")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("phoneType", channel);

                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).
                        getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).
                        getAsJsonObject().get("contacts").getAsJsonObject().get(contactType).getAsJsonObject().
                        addProperty("phoneNumber", phoneNumber.get());
                phoneType.set(channel);
            }
        }
        caseloaderJO.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }


    @And("I initiated Case Loader API for Create New Consumer for Digital Integration")
    public void iInitiatedCaseLoaderAPIForCreateNewConsumerForDigitalIntegration() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
    }

    @And("I run the CaseLoader PUT API to Create New Consumer for Digital Integration")
    public void iRunTheCaseLoaderPUTAPIToCreateNewConsumerForDigitalIntegration() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(caseloaderJO.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        waitFor(5);
    }

    @And("I store Consumer details data for digital authentication api call {string}, {string}, {string}, {string}, {string}, {string}")
    public void iStoreConsumerDetailsDataForDigitalAuthenticationApiCall(String dob, String ssn, String fn, String
            ln, String extId, String extIdType) throws Throwable {
        System.out.println(DOB);
        System.out.println(FN);
        System.out.println(LN);
        System.out.println(SSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiDigitalAuthentication.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonObject().getAsJsonObject("consumerIdentificationNumber")
                .addProperty("externalConsumerId", extId.equalsIgnoreCase("invalid") ? extId : externalId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonObject().getAsJsonObject("consumerIdentificationNumber")
                .addProperty("identificationNumberType", extIdType.equalsIgnoreCase("invalid") ? extIdType : externalIdType.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonObject()
                .addProperty("consumerDateOfBirth", dob.equalsIgnoreCase("invalid") ? addDaysToCurrentDate("yyyy-MM-dd", -4000) : DOB.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonObject()
                .addProperty("consumerSSN", ssn.equalsIgnoreCase("invalid") ? RandomStringUtils.random(4, false, true) : SSN.get().substring(5));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonObject()
                .addProperty("consumerFirstName", fn.equalsIgnoreCase("invalid") ? fn : FN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonObject()
                .addProperty("consumerLastName", ln.equalsIgnoreCase("invalid") ? ln : LN.get());

        digitalAuthJO.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("Digital Auth body: \n" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @And("I initiate digital Authentication api call with parameters")
    public void iInitiateDigitalAuthenticationApiCallWithParameters() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(digitalIntBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(digitalAuthEndPoint);
    }

    @And("I run the Digital Authentication POST API")
    public void iRunTheDigitalAuthenticationPOSTAPI() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(digitalAuthJO.get());
        System.out.println("POST Digital Auth Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode + ": "
                + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("authenticationStatus"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        digitalAuthRespBody.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        authenticationPojo.set(new Gson().fromJson(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject, ApiDigitalAuthResponse.class));
    }


    @When("I get a case id and consumer id created from Consumer Search API")
    public void iGetACaseIdAndConsumerIdCreatedFromConsumerSearchAPI() throws Throwable {/*
        consumerSearch.getApiClassUtil().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        consumerSearch.getApiClassUtil().setEndPoint("/app/crm/case/consumers");*/
        waitFor(5);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/case/consumers");
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_search_consumer_by("consumerFirstName", FN.get());
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_run_the_consumer_search_api();
        //caseId = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseId");
        caseId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseId"));
        synchronized (digitalPrimaryContactEndPoint){
            digitalPrimaryContactEndPoint.set("/app/crm/cases/" + caseId.get() + "/contacts/primary");
        }
        synchronized (digitalCaseConsumerInfoEndPoint){
            digitalCaseConsumerInfoEndPoint.set("/app/crm/cases/" + caseId.get() + "/members/active");
        }
        System.out.println(caseId + "================================================");
//        consumerId = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].consumers[0].consumerId");
        consumerId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].consumers[0].consumerId"));
        System.out.println(consumerId + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    @Then("I validate response according to request parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void iValidateResponseAccordingToRequestParameters(String dob, String ssn, String fn, String ln, String
            extId, String extIdType, String status) throws Throwable {

        if (status.equalsIgnoreCase("inactive") || dob.equalsIgnoreCase("invalid") || ssn.equalsIgnoreCase("invalid")
                || fn.equalsIgnoreCase("invalid") || ln.equalsIgnoreCase("invalid")) {
            assertTrue(authenticationReturnsCaseId(), "Authentication is missing expected Case Id " + caseId.get());
            assertTrue(authenticationPojo.get().getAuthenticationStatus().equals("Authentication Unsuccessful"), "Unexpected Authentication Status");
            assertTrue(authenticationPojo.get().getAuthenticationId() > 0, "Authentication Id is not a digit");
            assertTrue(authenticationPojo.get().getConsumer() == null, "Consumer Object should be NULL");

        } else if (extId.equalsIgnoreCase("invalid") || extIdType.equalsIgnoreCase("invalid")) {
            assertTrue(!authenticationReturnsCaseId(), "Authentication should not display Case Id " + caseId.get());
            assertTrue(authenticationPojo.get().getAuthenticationStatus().equals("Authentication Unsuccessful"), "Unexpected Authentication Status");
            assertTrue(authenticationPojo.get().getAuthenticationId() > 0, "Authentication Id is not a digit");
            assertTrue(authenticationPojo.get().getConsumer() == null, "Consumer Object should be NULL");

        } else if (extId.equalsIgnoreCase(null) || extIdType.equalsIgnoreCase(null)) {
            assertEquals(authenticationPojo.get().getCaseId(), caseId.get(), "Case ID is from POJO obj is not match");
            assertTrue(authenticationPojo.get().getAuthenticationStatus().equals("Authentication Successful"), "Unexpected Authentication Status");
            assertTrue(authenticationPojo.get().getAuthenticationId() > 0, "Authentication Id is not a digit");
            assertEquals(authenticationPojo.get().getConsumer().getConsumerId(), consumerId.get(), "Consumer Id is not " + consumerId.get());
            assertEquals(authenticationPojo.get().getConsumer().getCaseConsumers().getConsumerRole(), consumerRole.get(), "Consumer Role is not " + consumerRole);
            assertEquals(authenticationPojo.get().getConsumer().getConsumerIdentificationNumber().getExternalConsumerId(), externalId.get(),
                    "Consumer external Id is not " + externalId.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerIdentificationNumber().getIdentificationNumberType(), externalIdType.get(),
                    "Consumer external Id Type is not " + externalIdType.get());
            SSN.set(SSN.get().substring(5));
            assertEquals(authenticationPojo.get().getConsumer().getConsumerSSN(), SSN.get(), "Consumers last 4 digits of SSN are not " + SSN.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerStatus(), "Active", "Consumers Status is not Active");
            assertEquals(authenticationPojo.get().getConsumer().getConsumerFirstName(), FN.get(), "Consumer FN is not " + FN.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerLastName(), LN.get(), "Consumer LN is not " + LN.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerDateOfBirth(), DOB.get(), "Consumer DOB is not " + DOB.get());

        } else {
            assertEquals(authenticationPojo.get().getCaseId(), caseId.get(), "Case ID is from POJO obj is not match");
            assertTrue(authenticationPojo.get().getAuthenticationStatus().equals("Authentication Successful"), "Unexpected Authentication Status");
            assertTrue(authenticationPojo.get().getAuthenticationId() > 0, "Authentication Id is not a digit");
            assertEquals(authenticationPojo.get().getConsumer().getConsumerId(), consumerId.get(), "Consumer Id is not " + consumerId.get());
            assertEquals(authenticationPojo.get().getConsumer().getCaseConsumers().getConsumerRole(), consumerRole.get(), "Consumer Role is not " + consumerRole.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerIdentificationNumber().getExternalConsumerId(), externalId.get(),
                    "Consumer external Id is not " + externalId.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerIdentificationNumber().getIdentificationNumberType(), externalIdType.get(),
                    "Consumer external Id Type is not " + externalIdType.get());
            SSN.set(SSN.get().substring(5));
            assertEquals(authenticationPojo.get().getConsumer().getConsumerSSN(), SSN.get(), "Consumers last 4 digits of SSN are not " + SSN.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerStatus(), "Active", "Consumers Status is not Active");
            assertEquals(authenticationPojo.get().getConsumer().getConsumerFirstName(), FN.get(), "Consumer FN is not " + FN.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerLastName(), LN.get(), "Consumer LN is not " + LN.get());
            assertEquals(authenticationPojo.get().getConsumer().getConsumerDateOfBirth(), DOB.get(), "Consumer DOB is not " + DOB.get());
        }
        System.out.println(authenticationPojo.toString());
    }

    private boolean authenticationReturnsCaseId() {
        return (authenticationPojo.get().getCaseId() == caseId.get());
    }

    @When("I get rawLogs for External Case ID with its case ID")
    public void iGetRawLogsForExternalCaseIDWithItsCaseID() {
        List<String> externalCaseIdsString = EventsUtilities.getRawLogs("externalCaseId");
        for (int i = 0; i <= externalCaseIdsString.size() - 1; i++) {
            String st1 = externalCaseIdsString.get(i);
            String externalCaseIds[] = st1.split(",");
            for (int y = 0; y <= externalCaseIds.length - 1; y++) {
                if (externalCaseIds[y].contains("externalCaseId")) {
                    String st2[] = externalCaseIds[y].split(":");
                    externalCaseId.set(st2[2].replaceAll("\\[\\\\\\\"", "").replaceAll("\\\\\\\"\\]", ""));
                }
            }
        }
        caseId.set(STEP_DEFINITION_THREAD_LOCAL_FACTORY.getOutCorrEventsThreadLocal().getIntCaseID());
    }

    @When("I get CaseIdentificationNumber load created from Consumer Search API")
    public void iGetCaseIdentificationNumberLoadCreatedFromConsumerSearchAPI() {/*
        caseIdentificationNumberId = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberId");
        effectiveEndDate = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].effectiveEndDate");
        caseIdentificationNumberTypeReportLabel = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberTypeReportLabel");
        externalCaseId = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].externalCaseId");
        updatedBy = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].updatedBy");
        caseIdentificationNumberTypeId = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberTypeId");
        createdBy = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].createdBy");
        effectiveStartDate = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].effectiveStartDate");
        updatedOn = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].updatedOn");
        createdOn = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].createdOn");
        agencyName = consumerSearch.getApiClassUtil().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].agencyName");*/


        caseIdentificationNumberId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberId"));
        effectiveEndDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].effectiveEndDate"));
        caseIdentificationNumberTypeReportLabel.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberTypeReportLabel"));
        externalCaseId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].externalCaseId"));
        updatedBy.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].updatedBy"));
        caseIdentificationNumberTypeId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].caseIdentificationNumberTypeId"));
        createdBy.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].createdBy"));
        effectiveStartDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].effectiveStartDate"));
        updatedOn.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].updatedOn"));
        createdOn.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].createdOn"));
        agencyName.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseIdentificationNumber[0].agencyName"));
        System.out.println(caseIdentificationNumberTypeReportLabel.get());
    }

    @Then("I verify case_save_event payload for the correct case identification object")
    public void iVerifyCase_save_eventPayloadForTheCorrectCaseIdentificationObject() {
        assertTrue(searchQAEvent.get().contains("\"caseIdentificationNumberId\":" + caseIdentificationNumberId.get()));
        assertTrue(searchQAEvent.get().contains("\"caseId\":" + caseId.get()));
        assertTrue(searchQAEvent.get().contains("\"externalCaseId\":\"" + externalCaseId.get()));
        assertTrue(searchQAEvent.get().contains("\"agencyName\":" + agencyName.get()));
        assertTrue(searchQAEvent.get().contains("\"caseIdentificationNumberTypeId\":\"" + caseIdentificationNumberTypeId.get()));
        assertTrue(searchQAEvent.get().contains("\"createdBy\":\"" + createdBy.get()));
        assertTrue(searchQAEvent.get().contains("\"createdOn\":\"" + createdOn.get().substring(0, 16)));
        assertTrue(searchQAEvent.get().contains("\"updatedBy\":\"" + updatedBy.get()));
        assertTrue(searchQAEvent.get().contains("\"updatedOn\":\"" + updatedOn.get().substring(0, 16)));
        assertTrue(searchQAEvent.get().contains("\"effectiveStartDate\":\"" + effectiveStartDate.get().substring(0, 16)));
        assertTrue(searchQAEvent.get().contains("\"effectiveEndDate\":" + effectiveEndDate.get()));
    }

    @When("I search {string} endpoint for DBPI project {string}")
    public void iSearchEndpointForDBPI(String eventName, String project) throws IOException {
        String accessToken = STEP_DEFINITION_THREAD_LOCAL_FACTORY.getOutCorrEventsThreadLocal().getAccessToken();
        if (project.compareToIgnoreCase("blcrm") == 0) {
            caseId.set(Integer.parseInt(staticCaseId.get()));
            searchQAEvent.set(STEP_DEFINITION_THREAD_LOCAL_FACTORY.getOutCorrEventsThreadLocal().searchQAEventsRightPayload(accessToken, eventName, caseId.get()).replaceAll("\\\\", ""));
        } else if (project.compareToIgnoreCase("nj-sbe") == 0) {
            searchQAEvent.set(STEP_DEFINITION_THREAD_LOCAL_FACTORY.getOutCorrEventsThreadLocal().searchQAEventsRightPayloadNJ(accessToken, eventName, caseId.get()).replaceAll("\\\\", ""));
            System.out.println(searchQAEvent.get());
        }
    }

    @Then("I verify payload information")
    public void iVerifyPayloadInformation() {
        Map<String, String> currentPayload = new HashMap<>();
        currentPayload = STEP_DEFINITION_THREAD_LOCAL_FACTORY.getOutCorrEventsThreadLocal().convertPayloadToMap(searchQAEvent.get());
        Assert.assertEquals(currentPayload.get("createdBy"), currentPayload.get("updatedBy"));
        Assert.assertEquals(currentPayload.get("createdOn").substring(0, 16), currentPayload.get("updatedOn").substring(0, 16));
        Assert.assertEquals(currentPayload.get("createdOn").substring(0, 16), currentPayload.get("effectiveStartDate").substring(0, 16));
        Assert.assertEquals(currentPayload.get("updatedOn").substring(0, 16), currentPayload.get("effectiveStartDate").substring(0, 16));
        Assert.assertTrue(Integer.parseInt(currentPayload.get("caseIdentificationNumberId")) > 0);
        Assert.assertEquals(caseId.get(), Integer.parseInt(currentPayload.get("caseId")));
        Assert.assertEquals(externalCaseId.get(), currentPayload.get("externalCaseId"));
        Assert.assertEquals(currentPayload.get("caseIdentificationNumberTypeId"), currentPayload.get("caseIdentificationNumberTypeReportLabel"));
        Assert.assertEquals(currentPayload.get("caseIdentificationNumberTypeId"), currentPayload.get("caseIdentificationNumberTypeId"));
        Assert.assertEquals(currentPayload.get("caseIdentificationNumberTypeReportLabel"), currentPayload.get("caseIdentificationNumberTypeId"));
        Assert.assertEquals(currentPayload.get("agencyName"), "null");
        Assert.assertEquals(currentPayload.get("effectiveEndDate"), "null");
    }

    @And("I initiate Digital Primary Contact api call with parameters")
    public void iInitiateDigitalPrimaryContactApiCallWithParameters() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(digitalPrimaryContactBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(digitalPrimaryContactEndPoint.get());
    }


    @And("I run the Digital Primary Contact GET API")
    public void iRunTheDigitalPrimaryContactGETAPI() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("GET Primary Contact Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode + ": "
                + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("authenticationStatus"));
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        digitalPrimaryContactRespBody.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);

    }

    @Then("I validate response according to request parameters {string}, {string}, {string}, {string}, {string}")
    public void iValidateResponseAccordingToRequestParameters(String status, String primary, String
            contactType, String channel, String expectedInResponse) throws Throwable {
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
        Boolean inResponse = Boolean.valueOf(expectedInResponse);
        Boolean found = false;

        if (primary.equalsIgnoreCase("false")) {
            found = !(jsonPath.getString("caseContacts" + "." + contactType) == null);
            assertTrue(found == inResponse, "Contact Type " + contactType +
                    " should be null.");

        } else {
            if (contactType.equalsIgnoreCase("address")) {
                assertTrue(Integer.valueOf(jsonPath.get("caseContacts.address.addressId").toString()) > 0);

                assertTrue(jsonPath.get("caseContacts.address.addressStreet1").toString().equals(addressStreet1.get()));
                assertTrue(jsonPath.get("caseContacts.address.addressStreet2").toString().equals(addressStreet2.get()));
                assertTrue(jsonPath.get("caseContacts.address.addressState").toString().equals(addressState.get()));
                assertTrue(jsonPath.get("caseContacts.address.addressCity").toString().equals(addressCity.get()));
                assertTrue(jsonPath.get("caseContacts.address.addressCity").toString().equals(addressCity.get()));
                assertTrue(jsonPath.get("caseContacts.address.addressZip").toString().equals(addressZip.get()));
                assertTrue(jsonPath.get("caseContacts.address.addressZipFour").toString().equals(addressZipFour.get()));
                found = (jsonPath.get("caseContacts.address.primaryIndicator").toString().equals(primary));
                assertTrue(found == inResponse);

            } else if (contactType.equalsIgnoreCase("email")) {
                assertTrue(Integer.valueOf(jsonPath.get("caseContacts.email.emailId").toString()) > 0);
                assertTrue(jsonPath.get("caseContacts.email.emailAddress").toString().equals(emailAddress.get()));
                found = (jsonPath.get("caseContacts.email.primaryIndicator").toString().equals(primary));
                assertTrue(found == inResponse);

            } else if (contactType.equalsIgnoreCase("phone")) {
                if (channel.equalsIgnoreCase("fax")) {
                    found = !(jsonPath.getString("caseContacts.phone") == null);
                    assertTrue(found == inResponse, "Contact Type " + contactType +
                            "with channel " + channel + "should be null.");
                } else {

                    assertTrue(Integer.valueOf(jsonPath.get("caseContacts.phone.phoneId").toString()) > 0);
                    assertTrue(jsonPath.get("caseContacts.phone.phoneNumber").toString().equals(phoneNumber.get()));
                    assertTrue(jsonPath.get("caseContacts.phone.phoneType").toString().equals(phoneType.get()));
                    found = (jsonPath.get("caseContacts.phone.primaryIndicator").toString().equals(primary));
                    assertTrue(found == inResponse);
                }
            }
        }
    }


    @Then("I validate error code {string} with error message {string} in response according to request parameters")
    public void iValidateErrorCodeWithErrorMessageInResponseAccordingToRequestParameters(String errorCode, String errorMessage) throws Throwable {
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
        assertTrue(jsonPath.get("errorCode").toString().equals(errorCode), "Error code should be " + errorCode);
        assertTrue(jsonPath.get("errorMessage").toString().equals(errorMessage), "Error message should be " + errorMessage);

    }


    @And("I initiate the Digital Primary Contact GET API with unexpected Case Id to trigger {string}")
    public void iInitiateTheDigitalPrimaryContactGETAPIWithUnexpectedCaseIdToTrigger(String errorMessage) throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(digitalIntBaseURI);
        System.out.println("====" + errorMessage + "=====");
        if (errorMessage.equalsIgnoreCase("caseId cannot be null.")) {

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/cases/" + " " + "/contacts/primary");

        } else if (errorMessage.equalsIgnoreCase("Case not found or is inactive.")) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/cases/" + RandomStringUtils.random(8, false, true) + "/contacts/primary");
        }
    }

    @And("I run the Digital CaseConsumer Information GET API")
    public void iRunTheDigitalCaseConsumerInformationGETAPI() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("GET Primary Contact Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode + ": "
                + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("authenticationStatus"));
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        digitalCaseConsumerRespBody.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        System.out.println(digitalCaseConsumerRespBody);
    }

    @And("I initiate the CaseConsumer Information GET API with unexpected Case Id to trigger {string}")
    public void iInitiateTheCaseConsumerInformationGETAPIWithUnexpectedCaseIdToTrigger(String errorMessage) throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(digitalIntBaseURI);
        System.out.println("====" + errorMessage + "=====");
        if (errorMessage.equalsIgnoreCase("caseId cannot be null.")) {

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/cases/" + " " + "/members/active");

        } else {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/cases/" + RandomStringUtils.random(8, false, true) + "/members/active");
        }
    }

    @And("I initiate Digital Case Consumer Information api call with parameters")
    public void iInitiateDigitalCaseConsumerInformationApiCallWithParameters() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(digitalPrimaryContactBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(digitalCaseConsumerInfoEndPoint.get());
    }

    @Then("I validate response according to CaseConsumer Information request parameters {string}")
    public void iValidateResponseAccordingToCaseConsumerInformationRequestParameters(String role) throws Throwable {
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
        ArrayList consumerArray = (ArrayList) jsonPath.get("consumer");
        System.out.println(consumerArray.toString());
        HashMap<String, Object> consumerDetailsMap = (HashMap<String, Object>) consumerArray.get(0);
        System.out.println(consumerDetailsMap.get("consumerDateOfBirth").toString());
        ArrayList eligibilityArray = (ArrayList) consumerDetailsMap.get("consumerIdentificationNumber");
        HashMap<String, Object> IdNumberMap = (HashMap<String, Object>) eligibilityArray.get(0);
        HashMap<String, Object> caseConsumerMap = (HashMap<String, Object>) consumerDetailsMap.get("caseConsumer");

        System.out.println(IdNumberMap.get("externalConsumerId"));
        switch (role) {
            case "CM":
                consumerRole.set("Member");
                break;
            case "PI":
                consumerRole.set("Primary Individual");
                break;
            case "AR":
                consumerRole.set("Authorized Representative");
                break;
        }

        assertTrue((Integer.valueOf(jsonPath.get("caseId").toString()).equals(caseId.get())), "Expected case ID is " + caseId.get());
        assertEquals(jsonPath.get("caseIdentificationNumber.externalCaseId").toString(), (caseIdentificationNumber.get()),
                "Expected caseIdentificationNumber is " + caseIdentificationNumber.get());
        assertEquals(jsonPath.get("caseIdentificationNumber.identificationNumberType").toString(), externalIdType.get(),
                "Expected externalIdType is " + externalIdType.get());
        assertTrue(Integer.valueOf(consumerDetailsMap.get("consumerId").toString()).equals(consumerId.get()),
                "Expected consumerId is " + consumerId.get());
        assertEquals(consumerDetailsMap.get("consumerDateOfBirth").toString(), DOB.get(),
                "Expected DOB is " + DOB.get());
        assertEquals(consumerDetailsMap.get("consumerFirstName").toString(), FN.get(),
                "Expected FN is " + FN.get());
        assertEquals(consumerDetailsMap.get("consumerLastName").toString(), LN.get(),
                "Expected LN is " + LN.get());
        assertEquals(caseConsumerMap.get("consumerRole").toString(), consumerRole.get(),
                "Expected consumerRole is " + consumerRole.get());


    }

    @Then("I verify case_save_event payload contains UpdatedByOn CraetedByOn")
    public void iVerifyCase_save_eventPayloadContainsUpdatedByOnCraetedByOn() {
        assertTrue(searchQAEvent.get().contains("createdBy"));
        assertTrue(searchQAEvent.get().contains("createdOn"));
        assertTrue(searchQAEvent.get().contains("updatedBy"));
        assertTrue(searchQAEvent.get().contains("updatedOn"));
    }


    @Then("I validate response null to request parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void iValidateResponseNullToRequestParameters(String dob, String ssn, String fn, String ln, String
            extId, String extIdType, String status) throws Throwable {
        if (dob.equalsIgnoreCase(null) || ssn.equalsIgnoreCase(null)) {
            assertTrue(authenticationReturnsCaseId(), "Authentication is missing expected Case Id " + caseId.get());
            assertTrue(authenticationPojo.get().getAuthenticationStatus().equals("Authentication Unsuccessful"), "Unexpected Authentication Status");
            assertTrue(authenticationPojo.get().getAuthenticationId() > 0, "Authentication Id is not a digit");
            assertTrue(authenticationPojo.get().getConsumer() == null, "Consumer Object should be NULL");
            assertTrue(authenticationPojo.get().getErrorResponse().equals("The following fields are required: External consumer Id OR SSN and Date of Birth"));

        } else if (fn.equalsIgnoreCase(null) || ln.equalsIgnoreCase(null)) {
            assertTrue(authenticationReturnsCaseId(), "Authentication is missing expected Case Id " + caseId.get());
            assertTrue(authenticationPojo.get().getAuthenticationStatus().equals("Authentication Unsuccessful"), "Unexpected Authentication Status");
            assertTrue(authenticationPojo.get().getAuthenticationId() > 0, "Authentication Id is not a digit");
            assertTrue(authenticationPojo.get().getConsumer() == null, "Consumer Object should be NULL");
            assertTrue(authenticationPojo.get().getErrorResponse().equals("Invalid Consumer FirstName or LastName"));

        }
    }

    @Then("I should see the payload for the business {string} created by caseloader is as expected")
    public void iShouldSeeThePayloadForTheBIsByCaseloaderAsExpected(String event) throws ParseException {
        verifyNewCaseConsumerEvent(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()), event);
    }

    public void verifyNewCaseConsumerEvent(JsonPath eventbyTraceId, String businessEvent) throws ParseException {
        List<Map<String, Object>> eventsList = eventbyTraceId.getList("events");
        boolean validated = false;
        for (int i = 0; i < eventsList.size(); i++) {
            if (eventbyTraceId.get("events[" + i + "].eventName").equals(businessEvent)) {
                String expectedPayload = eventbyTraceId.get("events[" + i + "].payload").toString();
                assertEquals(eventbyTraceId.get("events[" + i + "].eventName"), businessEvent);
                assertEquals(eventbyTraceId.get("events[" + i + "].module"), "CONSUMER");
                assertTrue(expectedPayload.contains("\"consumerId\":" + consumerId.get()));
                assertTrue(expectedPayload.contains("\"caseId\":" + caseId.get()));
                assertTrue(expectedPayload.contains("\"consumerIdType\":\"" + externalIdType.get()));
                assertTrue(expectedPayload.contains("\"businessEvent\":\"" + businessEvent + ""));
                assertTrue(expectedPayload.contains("\"channel\":\"" + "User Data Entry" + "\""));
                assertTrue(expectedPayload.contains("\"processDate\":\"" + offsetTime.get().substring(0, 15)));
                assertTrue(expectedPayload.contains("\"consumerFirstName\":\"" + FN.get()));
                assertTrue(expectedPayload.contains("\"consumerLastName\":\"" + LN.get()));
                assertTrue(expectedPayload.contains("\"dateOfBirth\":\"" + DOB.get()));
                assertTrue(expectedPayload.contains("\"roleOnCase\":\"" + consumerRole.get()));
                assertTrue(expectedPayload.contains("\"caseIdExternal\":null"));
//                assertTrue(expectedPayload.contains("\"startDate\":\"" + effectiveStartDate.substring(0,16)) ||
//                        expectedPayload.contains("\"startDate\":\"" + offsetTime.substring(0,16)));
                assertTrue(expectedPayload.contains("\"pregnancy\":null"));
                assertTrue(expectedPayload.contains("\"pregnancyDueDate\":null"));
                assertTrue(expectedPayload.contains("\"dateOfDeath\":null"));
                assertTrue(expectedPayload.contains("\"dodNotificationDate\":null"));
                assertTrue(expectedPayload.contains("\"dodNotificationSource\":null"));
                assertTrue(expectedPayload.contains("\"communicationOptIn\":[\"")
                        || expectedPayload.contains("\"communicationOptIn\":[\"Mail\",\"Phone\"]"));
                assertTrue(expectedPayload.contains("\"consumerStatus\":\"Active\""));
                validated = true;
            }

        }
        assertTrue(validated);


    }
}