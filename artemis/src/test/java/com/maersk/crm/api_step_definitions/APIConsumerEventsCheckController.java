package com.maersk.crm.api_step_definitions;

import com.google.gson.*;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import io.restassured.path.json.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.testng.Assert.*;

public class APIConsumerEventsCheckController extends CRMUtilities implements ApiBase {

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseConsumerForEvents = "app/crm/caseloader";

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String getEvents = "app/crm/events?size=10000000&page=0&sort=eventId,desc";
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";


    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(() -> new HashMap<>(getNewTestData2()));

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> dob1 = ThreadLocal.withInitial(() -> APICaseRestController.addDaysToCurrentDate("MM/dd/yyyy", -8000));
    private final ThreadLocal<String> dob2 = ThreadLocal.withInitial(() -> APICaseRestController.addDaysToCurrentDate("MM/dd/yyyy", -8001));

    private final ThreadLocal<String> phoneNum = ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());
    private final ThreadLocal<String> phoneType = ThreadLocal.withInitial(() -> "Home");
    private final ThreadLocal<String> emailAddress = ThreadLocal.withInitial(() -> getRandomString(5) + "@" + getRandomString(4) + ".com");
    private final ThreadLocal<String> addressLine1 = ThreadLocal.withInitial(() -> getRandomNumber(3) + " " + getRandomString(5) + " Ct.");
    private final ThreadLocal<String> addressCity = ThreadLocal.withInitial(() -> getRandomString(6) + " City");
    private final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(() -> getRandomString(6) + " County");
    private final ThreadLocal<String> addressZip = ThreadLocal.withInitial(() -> newConsumer.get().get("zip").toString());
    private final ThreadLocal<String> addressType = ThreadLocal.withInitial(() -> "Mailing");
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> userId = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> associatedCaseMember = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> consumerNameSearch = ThreadLocal.withInitial(() -> null);

    private final ThreadLocal<String> firstNameMember = ThreadLocal.withInitial(() -> "fn".concat(getRandomString(5)));
    private final ThreadLocal<String> lastNameMember = ThreadLocal.withInitial(() -> "ln".concat(getRandomString(5)));
    private final ThreadLocal<String> dobMember = ThreadLocal.withInitial(() -> "07/11/1996");
    private final ThreadLocal<String> genderMember = ThreadLocal.withInitial(() -> "Male");
    private final ThreadLocal<String> startDateMember = ThreadLocal.withInitial(() -> "01/01/2019");
    private final ThreadLocal<String> ssnMember = ThreadLocal.withInitial(() -> "111223333");
    private final ThreadLocal<String> relationshipToPIMember = ThreadLocal.withInitial(() -> "Guardian");
    private final ThreadLocal<String> accessTypeMember = ThreadLocal.withInitial(() -> "Full Access");
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);


    private final ThreadLocal<Integer> eventId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> consumerDob = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> consumerRole = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(() -> null);

    private ThreadLocal<String> lastNameUpdated = ThreadLocal.withInitial(() -> "ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
    private ThreadLocal<String> phoneNumUpdated = ThreadLocal.withInitial(() -> API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
    private ThreadLocal<String> ssn = ThreadLocal.withInitial(() -> API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
    private final ThreadLocal<JsonPath> consumerSaveEventResponse = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<Integer> consumerSaveEventIndex = ThreadLocal.withInitial(() -> 0);
    private CRMCreateConsumerProfilePage crmCreateConsumerProfilePage = new CRMCreateConsumerProfilePage();
    private CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();
    private CRMContactRecordUIPage crmContactRecordUIPage1 = new CRMContactRecordUIPage();
    private CRMActiveContactPage crmActiveContactPage = new CRMActiveContactPage();
    private CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    private CRMManualCaseConsumerSearchPage manualCaseConsumerSearchPage = new CRMManualCaseConsumerSearchPage();

    @Given("I create a consumer for consumer events check")
    public void createConsumerForConsumerEvents() {
        createNewConsumerForConsumerEventsCheck();
    }

    @And("I search created consumer for consumer events check")
    public void searchConsumerForEventCheck() {
        manualCaseConsumerSearchPage.firstName.sendKeys(firstName.get());
        manualCaseConsumerSearchPage.lastName.sendKeys(lastName.get());
        manualCaseConsumerSearchPage.searchButton.click();
        waitFor(1);
        manualCaseConsumerSearchPage.consumerCancelBtn.click();
        waitFor(1);

        manualCaseConsumerSearchPage.firstName.sendKeys(firstName.get());
        manualCaseConsumerSearchPage.lastName.sendKeys(lastName.get());
        manualCaseConsumerSearchPage.searchButton.click();
        waitFor(4);
        crmContactRecordUIPage1.consumerIdFirstRecord.click();
        waitForVisibility(demographicContactInfoPage.demographicInfoTab, 10);
    }

    @And("I update consumer for consumer events check")
    public void updateConsumerForConsumerEventsCheck() {
        waitForVisibility(demographicMemberPage.contactEdit, 10);
        waitFor(1);
        demographicMemberPage.contactEdit.click();
        //waitForVisibility(crmEditConsumerProfilePage.profileContactPage, 10);
        System.out.println("Last Name updated: " + lastNameUpdated.get());
        waitFor(1);
        clearAndFillText(demographicMemberPage.lastName, lastNameUpdated.get());
        waitFor(1);
        clearAndFillText(demographicMemberPage.ssn, ssnMember.get());
        waitFor(1);
        selectDropDown(demographicMemberPage.languagePre, "Russian");
        waitFor(1);
        demographicMemberPage.contactEditSaveBtn.click();
        waitFor(2);
        if (demographicMemberPage.continuePop.isDisplayed()) {
            demographicMemberPage.continuePop.click();
        }
        waitFor(2);
        waitForVisibility(demographicMemberPage.contactEdit, 10);
    }

    @And("I create {string} member from active contact page of created consumer")
    public Map<String, String> createMemberFromActiveContact(String consumerRole) {
        Map<String, String> consumerNames = new HashMap<>();
        consumerNames.put("firstName", firstNameMember.get());
        consumerNames.put("lastName", lastNameMember.get());
        demographicContactInfoPage.demographicInfoTabFromSearch.click();

        System.out.println("First name of new created member: " + firstNameMember.get());
        System.out.println("Last name of new created member: " + lastNameMember.get());
        System.out.println("DOB of new created member: " + dobMember.get());
        System.out.println("Gender of new created member: " + genderMember.get());
        System.out.println("Start date of new created member: " + startDateMember.get());
        System.out.println("SSN of new created member: " + ssnMember.get());
        System.out.println("SSN of new created member: " + relationshipToPIMember.get());
        System.out.println("Access Type of new created member: " + accessTypeMember.get());
        System.out.println("AR Start Date of new created member: " + startDateMember.get());

        if (consumerRole.equals("")) {
            demographicMemberPage.addPrimaryIndividualBtn.click();
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);
            clearAndFillText(demographicMemberPage.firstName, firstNameMember.get());
            clearAndFillText(demographicMemberPage.lastName, lastNameMember.get());
            waitFor(1);
            clearAndFillText(demographicMemberPage.ssn, ssnMember.get());
            waitFor(1);
            clearAndFillText(demographicMemberPage.consumerDoB, dobMember.get());
            waitFor(1);
            clearAndFillText(demographicMemberPage.effectiveStartDate, startDateMember.get());
            waitFor(1);
            selectDropDown(demographicMemberPage.gender, genderMember.get());
            scrollDownUsingActions(4);
            waitFor(1);
        } else if (consumerRole.equals("Member")) {
            demographicMemberPage.addCaseMemberBtn.click();
            waitForVisibility(demographicMemberPage.caseMemberPage, 10);

            clearAndFillText(demographicMemberPage.consumerFrstName, firstNameMember.get());
            clearAndFillText(demographicMemberPage.consumerLastName, lastNameMember.get());
            waitFor(1);
            clearAndFillText(demographicMemberPage.effectiveStartDate, startDateMember.get());
            clearAndFillText(demographicMemberPage.consumerSsn, ssnMember.get());
            waitFor(1);
            clearAndFillText(demographicMemberPage.consumerDoB, dobMember.get());
            waitFor(1);
            selectDropDown(demographicMemberPage.relationShp, relationshipToPIMember.get());
            selectDropDown(demographicMemberPage.caseGender, genderMember.get());
            waitFor(1);
        } else if (consumerRole.equals("Authorized Representative")) {
            demographicMemberPage.addAuthorizedRepBtn.click();
            waitForVisibility(demographicMemberPage.authorizedRepPage, 10);

            waitFor(2);
            scrollUpUsingActions(2);
            waitFor(1);
            selectDropDown(demographicMemberPage.arAccessType, accessTypeMember.get());
            clearAndFillText(demographicMemberPage.consumerFrstName, firstNameMember.get());
            clearAndFillText(demographicMemberPage.consumerLastName, lastNameMember.get());
            waitFor(1);
            clearAndFillText(demographicMemberPage.effectiveStartDate, startDateMember.get());
            clearAndFillText(demographicMemberPage.consumerSsn, ssnMember.get());
            waitFor(1);
            demographicMemberPage.consumerDoB.click();
            clearAndFillText(demographicMemberPage.consumerDoB, dobMember.get());
            waitFor(1);
            selectDropDown(demographicMemberPage.arGender, genderMember.get());
            scrollDownUsingActions(4);
            waitFor(1);
        }

        demographicMemberPage.saveMemberBtn.click();
        try {
            if (!demographicMemberPage.continueMemberBtn.isDisplayed())
                System.out.println("Continue button is available to click");
        } catch (Exception e) {
            demographicMemberPage.saveMemberBtn.click();
        }
        waitForVisibility(demographicMemberPage.continueMemberBtn, 10);
        demographicMemberPage.continueMemberBtn.click();

        waitForVisibility(demographicContactInfoPage.demographicInfoTabFromSearch, 10);

        return consumerNames;
    }

    @When("I will take {string} for {string} for {string} consumer event check")
    public void i_will_take_trace_Id_for_Events(String idType, String event, String eventType) {
        if (event.contains("CONSUMER_SAVE")) {
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(0));
        } else if (event.contains("CONSUMER_UPDATE_EVENT")) {
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(5));
        } else {
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(4));
        }
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Given("I initiated Case Loader API for checking events")
    public void initiatedConsumerCreateAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);
    }

    @When("I run the Case Loader API for checking events payload with consumer role {string}")
    public void runConsumerCreateAPI(String consumerRoleInput) {
        consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        String[] consumerRoleSplit = consumerRoleInput.split("-");
        String traceIdOrNot = consumerRoleSplit[0];
        consumerRole.set(consumerRoleSplit[1]);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(),
                consumerDob.get(), "", "", consumerRole.get()));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        if (traceIdOrNot.equals("correlation")) {
            correlationId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
            eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
        } else if (traceIdOrNot.equals("case")) {
            JsonElement caseLoaderReq = requestParams.get().getAsJsonArray("caseLoaderRequest").get(0);
            JsonObject caseObj = caseLoaderReq.getAsJsonObject().get("case").getAsJsonObject();
            correlationId.set(caseObj.get("correlationId").getAsString());
            eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
        }

        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @And("I search for created case using consumer details")
    public void searchAndSelectConsumerAssociatedToACase() {
        waitFor(2);
        crmContactRecordUIPage1.firstName.sendKeys(consumerFirstName.get());
        crmContactRecordUIPage1.lastName.sendKeys(consumerLastName.get());
        crmContactRecordUIPage1.searchButton.click();

        waitFor(3);
        consumerNameSearch.set(crmContactRecordUIPage1.searchResultFirstRowFirstName.getText() + ' ' +
                crmContactRecordUIPage1.searchResultFirstRowLastName.getText());
        System.out.println(consumerNameSearch.get());
        crmContactRecordUIPage1.caseIdFirstRecord.click();
        waitFor(1);
    }

    @Then("I will verify an {string} is created with fields in payload for {string} events")
    public void verifyTheCaseConsumerEvents(String eventName, String eventType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        boolean recordFound = false;

        if (eventType.equals("consumerId")) {
            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

                eventId.set((int) eventsData.get("eventId"));

                if (eventName.contains("CONSUMER_SAVE")) {
                    assertEquals(payLoadData.get("action").getAsString(), "Create");
                    //assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");
                    assertTrue(dataObjectData.get("consumerLastName").getAsString().equalsIgnoreCase(lastName.get()));
                    assertTrue(dataObjectData.get("consumerFirstName").getAsString().equalsIgnoreCase(firstName.get()));
                    // jenkins job system time -1 day
                    if (dataObjectData.get("consumerDateOfBirth").getAsString().equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(dob1.get()))) {
                        assertEquals(dataObjectData.get("consumerDateOfBirth").getAsString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(dob1.get()));
                    } else {
                        assertEquals(dataObjectData.get("consumerDateOfBirth").getAsString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(dob2.get()));
                    }
                    //assertEquals(dataObjectData.get("consumerStatus").getAsString(), "Active");
                } else if (eventName.contains("CONSUMER_UPDATE")) {
                    assertEquals(payLoadData.get("action").getAsString(), "Update");
                    assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");
                    assertTrue(dataObjectData.get("consumerLastName").getAsString().equalsIgnoreCase(lastNameUpdated.get()));

                    assertTrue(dataObjectData.get("consumerFirstName").getAsString().equalsIgnoreCase(firstName.get()));
                    assertEquals(dataObjectData.get("consumerDateOfBirth").getAsString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(dob1.get()));
                    assertEquals(dataObjectData.get("consumerSSN").getAsString(), ssnMember.get());
                }

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    recordFound = true;
                    break;
                }
            }
        } else if (eventType.equals("case")) {
            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                eventId.set((int) eventsData.get("eventId"));
                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    assertEquals(payLoadData.get("action").getAsString(), "Create");
                    assertEquals(payLoadData.get("recordType").getAsString(), "Case");
                    recordFound = true;
                    break;
                }
            }
        } else if (eventType.contains("consumerRole")) {
            for (int i = 0; i < eventsContent.size(); i++) {
                consumerRole.set(eventType.split("-")[1]);
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

                eventId.set((int) eventsData.get("eventId"));

                assertEquals(payLoadData.get("action").getAsString(), "Create");
                assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");

                assertTrue(dataObjectData.get("consumerFirstName").getAsString().equalsIgnoreCase(consumerFirstName.get()));
                assertTrue(dataObjectData.get("consumerLastName").getAsString().equalsIgnoreCase(consumerLastName.get()));
                assertEquals(dataObjectData.get("consumerSSN").getAsString(), consumerSSN.get());
                assertEquals(dataObjectData.get("consumerType").getAsString(), "Consumer");

                JsonArray caseConsumersData = dataObjectData.get("caseConsumer").getAsJsonArray();
                assertEquals(caseConsumersData.get(0).getAsJsonObject().get("consumerRole").getAsString(), consumerRole.get());

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    recordFound = true;
                    break;
                }
            }
        } else if (eventType.contains("consumerUI")) {
            for (int i = 0; i < eventsContent.size(); i++) {
                consumerRole.set(eventType.split("-")[1]);
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

                eventId.set((int) eventsData.get("eventId"));

                assertEquals(payLoadData.get("action").getAsString(), "Create");
                assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");

                assertTrue(dataObjectData.get("consumerFirstName").getAsString().equalsIgnoreCase(firstNameMember.get()));
                assertTrue(dataObjectData.get("consumerLastName").getAsString().equalsIgnoreCase(lastNameMember.get()));
                assertEquals(dataObjectData.get("consumerSSN").getAsString(), ssnMember.get());
                assertEquals(dataObjectData.get("consumerType").getAsString(), "Consumer");

                JsonArray caseConsumersData = dataObjectData.get("caseConsumer").getAsJsonArray();
                assertEquals(caseConsumersData.get(0).getAsJsonObject().get("consumerRole").getAsString(), consumerRole.get());

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    recordFound = true;
                    break;
                }
            }
        }
        assertTrue(recordFound);
    }

    @Then("I will verify an {string} is created with datetime fields in UTC format in payload")
    public void verifyTheConsumerDateTimeSaveEvents(String eventName) {

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDatetimeFormat(payLoadData.get("eventCreatedOn")));
            //  assertEquals(getCurrentDate(), api.getDateForISOFormat(payLoadData.get("eventCreatedOn").getAsString()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDatetimeFormat(dataObjectData.get("createdOn")));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDatetimeFormat(dataObjectData.get("updatedOn")));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDatetimeFormat(dataObjectData.get("effectiveStartDate")));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDatetimeFormat(dataObjectData.get("effectiveEndDate")));

            if (eventsData.get("eventName").equals(eventName)) {
                assertEquals(eventsData.get("status"), "success");
                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);
    }

    @Then("I will verify an {string} is created with date fields in date format in payload")
    public void verifyTheConsumerDataSaveEventUpdated(String eventName) {
        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        boolean recordFound = false;

        if (eventName.equals("CONSUMER_SAVE_EVENT") || eventName.equals("CONSUMER_UPDATE_EVENT") || eventName.equals("CASE_MEMBER_SAVE_EVENT")) {
            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(dataObjectData.get("consumerDateOfBirth").getAsString()));

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    recordFound = true;
                    break;
                }
            }
        } else if (eventName.equals("CASE_SAVE_EVENT")) {
            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    recordFound = true;
                    break;
                }
            }
        }

        assertTrue(recordFound);
    }

    @Then("I will verify subscriber received {string} event for {string} is created for consumer events")
    public void dpbiConsumerEventGenerationCheck(String eventName, String subscriberName) {
        boolean recordCreated = true;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);


        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName);

        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray jsonSubscriber = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        String subscriberId = new JSONObject(jsonSubscriber.get(0).toString()).get("subscriberId").toString();
        JSONArray subscriberList = new JSONArray(new JSONObject(jsonSubscriber.get(0).toString()).get("subscriberList").toString());

        for (int i = 0; i < subscriberList.length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get(i).toString());
            if (temp.get("eventName").toString().trim().equals(eventName.trim())) {
                Assert.assertEquals(temp.get("subscriberId").toString(), subscriberId, "Subscriber Id is wrong");
                Assert.assertEquals(temp.get("eventName").toString().trim(), eventName, "Event Name is wrong");
                Assert.assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                recordCreated = true;
                break;
            }
        }
        assertTrue(recordCreated, eventName + " is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        assertEquals(temp.getString("eventName"), eventName);
        System.out.println("Event name on DPBI:  " + eventName);

        assertEquals(temp.getInt("eventId"), eventId.get());
        System.out.println("Event id on DPBI:  " + eventId.get());

        Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId);
        System.out.println("Subscriber id on DPBI:   " + subscriberId);
        System.out.println("Event is subscribed to DPBI list");
    }

    private void createNewConsumerForConsumerEventsCheck() {
        waitFor(3);
        crmContactRecordUIPage1.initContact.click();
        assertTrue(crmContactRecordUIPage1.contactInProgressGreenSign.isDisplayed());

        clearAndFillText(crmContactRecordUIPage1.firstName, firstName.get());
        clearAndFillText(crmContactRecordUIPage1.lastName, lastName.get());
        consumerName.set(firstName.get() + " " + lastName.get());
        System.out.println(firstName.get() + " " + lastName.get());

        waitFor(2);
        assertTrue(crmContactRecordUIPage1.searchButton.isDisplayed());
        crmContactRecordUIPage1.searchButton.click();
        waitFor(2);

        waitForVisibility(crmCreateConsumerProfilePage.addConsumerButton, 5);
        assertTrue(crmCreateConsumerProfilePage.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        crmCreateConsumerProfilePage.addConsumerButton.click();
        waitFor(3);
        assertTrue(crmCreateConsumerProfilePage.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        clearAndFillText(crmCreateConsumerProfilePage.consumerFN, firstName.get());
        clearAndFillText(crmCreateConsumerProfilePage.consumerLN, lastName.get());
        clearAndFillText(crmCreateConsumerProfilePage.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(crmCreateConsumerProfilePage.consumerDB, dob1.get());
        selectRandomDropDownOption(crmCreateConsumerProfilePage.consumerGender);
        clearAndFillText(crmCreateConsumerProfilePage.consumerEmail, emailAddress.get());
        clearAndFillText(crmCreateConsumerProfilePage.consumerPhoneNum, phoneNum.get());
        selectDropDown(crmCreateConsumerProfilePage.phoneType, phoneType.get());
        waitFor(1);
        selectDropDown(crmCreateConsumerProfilePage.consumerAddressType, addressType.get());
        waitFor(1);
        clearAndFillText(crmCreateConsumerProfilePage.consumerAddress1, addressLine1.get());
        clearAndFillText(crmCreateConsumerProfilePage.consumerCity, addressCity.get());
        selectRandomDropDownOption(crmCreateConsumerProfilePage.consumerState);
        selectRandomDropDownOption(crmCreateConsumerProfilePage.consumerCountyField);
        waitFor(3);
        click(crmCreateConsumerProfilePage.consumerZipCode);
        clearAndFillText(crmCreateConsumerProfilePage.consumerZipCode, addressZip.get());
        waitFor(6);
        crmCreateConsumerProfilePage.createConsumerButton.click();
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", crmContactRecordUIPage1.lblCrmConsumerId.getText());
        String consumerId = crmContactRecordUIPage1.lblCrmConsumerId.getText();
        String consumerFullName = crmActiveContactPage.fullName.getText();
        associatedCaseMember.set(consumerFullName.concat(" (").concat(consumerId).concat(")"));
        userId.set(crmContactRecordUIPage1.userId.getText());
        userId.set(userId.get().substring(3));
        System.out.println("User Id: " + userId.get());
    }

    public JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                       String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip, String consumerRole) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " + caseIdentificationNumber);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDateOfBirth);
        if (!consumerRole.isEmpty())
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", consumerRole);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @Then("I will get the {string} by traceId")
    public void iWillGetTheByTraceId(String EVENT) {
        APIClassUtil apiClass = APIClassUtil.getApiClassUtilThreadLocal();
        apiClass.setbaseUri(ConfigurationReader.getProperty("apiEventsURI"));
        apiClass.setEndPoint("/app/crm/event/correlation/" + correlationId.get());
        consumerSaveEventResponse.set(apiClass.getAPI().jsonPathEvaluator);
        assertEquals(apiClass.statusCode, 200, "Get Events API status");
        consumerSaveEventIndex.set(0);
        List<String> eventNames = consumerSaveEventResponse.get().get("events.eventName");
        for (int counter = 0; counter < eventNames.size(); counter++) {
            if (eventNames.get(counter).equalsIgnoreCase(EVENT)) {
                consumerSaveEventIndex.set(counter);
            }
        }
        eventId.set(consumerSaveEventResponse.get().getInt("events[" + consumerSaveEventIndex.get() + "].eventId"));

    }

    @Then("I validate in the {string} the following fields")
    public void iValidateInTheTheFollowingFields(String arg0, List<String> fields) {
        String responseString = consumerSaveEventResponse.get().get("events[" + consumerSaveEventIndex.get() + "].payload");

        assertTrue(responseString.contains("valuePairIdCommPref\":\"SPOKEN_LANGUAGE_ENGLISH\""));
        assertTrue(responseString.contains("valuePairIdCommPref\":\"WRITTEN_LANGUAGE_ENGLISH\""));

        //validate dates
        for (String date : EventsUtilities.getGenericMatch(responseString, "Date\":\"\\w{4}-\\w{2}-\\w{5}:\\w{2}:\\w{2}.\\w{6}[+]\\w{2}:\\w{2}").split("Date")) {
            if (date.length() < 1) continue;
            date = date.substring(3, date.length() - 1);
        }
        //vaidate created/updated
        for (String date : EventsUtilities.getGenericMatch(responseString, "On\":\"\\w{4}-\\w{2}-\\w{5}:\\w{2}:\\w{2}.\\w{6}[+]\\w{2}:\\w{2}").split("On")) {
            if (date.length() < 1) continue;
            date = date.substring(3, date.length() - 1);
        }
        //validate users
        for (String by : EventsUtilities.getGenericMatch(responseString, "By\":\"\\d{4}").split("By")) {
            if (by.length() < 1) continue;
            by = by.substring(3);
            assertTrue(BrowserUtils.hasOnlyDigits(by));
        }
    }

    @And("I validate there is no communicationPreferences record created for correspondence preference in the payload")
    public void iValidateThereIsNoCommunicationPreferencesRecordCreatedForCorrespondencePreferenceInThePayload() {
        String responseString = consumerSaveEventResponse.get().get("events[" + consumerSaveEventIndex.get() + "].payload");
        assertTrue((!responseString.contains("CORRESPONDENCE")));
    }

    @And("I validate there are only communicationPreference records for Spoken and Written Language in the payload")
    public void iValidateThereAreOnlyCommunicationPreferenceRecordsForSpokenAndWrittenLanguageInThePayload() {
        String responseString = consumerSaveEventResponse.get().get("events[" + consumerSaveEventIndex.get()+ "].payload");
        assertTrue(responseString.contains("valuePairIdCommPref\":\"SPOKEN_LANGUAGE_ENGLISH\""));
        assertTrue(responseString.contains("valuePairIdCommPref\":\"WRITTEN_LANGUAGE_ENGLISH\""));
    }

    @Then("I will verify createdOn,createdBy,updatedOn,updatedBy fields in {string} payload")
    public void iWillVerifyCreatedOnCreatedByUpdatedOnUpdatedByFieldsInPayload(String eventName) {
        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        if (eventName.equals("CONSUMER_SAVE_EVENT")) {
            HashMap eventsData = (HashMap) eventsContent.get(0);
            System.out.println(eventsData.get("payload"));
            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertTrue(dataObjectData.get("createdBy").getAsString().equalsIgnoreCase(dataObjectData.get("updatedBy").getAsString()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(dataObjectData.get("updatedOn").getAsString()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(dataObjectData.get("createdOn").getAsString()));

            JsonArray communicationPreferences = dataObjectData.get("communicationPreferences").getAsJsonArray();

            for (int i = 0; i < communicationPreferences.size(); i++) {
                assertEquals(communicationPreferences.get(i).getAsJsonObject().get("createdBy").getAsString(),
                        communicationPreferences.get(i).getAsJsonObject().get("updatedBy").getAsString());
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(communicationPreferences.get(i).getAsJsonObject().get("updatedOn").getAsString()));
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(communicationPreferences.get(i).getAsJsonObject().get("createdOn").getAsString()));
            }
            JsonArray consumerConsent = dataObjectData.get("consumerConsent").getAsJsonArray();
            for (int i = 0; i < consumerConsent.size(); i++) {
                assertEquals(consumerConsent.get(i).getAsJsonObject().get("createdBy").getAsString(),
                        consumerConsent.get(i).getAsJsonObject().get("updatedBy").getAsString());
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(consumerConsent.get(i).getAsJsonObject().get("updatedOn").getAsString()));
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(consumerConsent.get(i).getAsJsonObject().get("createdOn").getAsString()));

            }

            if (dataObjectData.get("caseConsumer").isJsonNull()) {
                System.out.println("CaseConsumer field is null");
            } else {
                JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();

                assertEquals(caseConsumer.get(0).getAsJsonObject().get("createdBy").getAsString(),
                        caseConsumer.get(0).getAsJsonObject().get("updatedBy").getAsString());
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumer.get(0).getAsJsonObject().get("updatedOn").getAsString()));
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumer.get(0).getAsJsonObject().get("createdOn").getAsString()));
            }
        } else if (eventName.equals("CASE_SAVE_EVENT")) {
            HashMap eventsData = (HashMap) eventsContent.get(0);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertTrue(dataObjectData.get("createdBy").getAsString().equalsIgnoreCase(dataObjectData.get("updatedBy").getAsString()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(dataObjectData.get("updatedOn").getAsString()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(dataObjectData.get("createdOn").getAsString()));

            JsonArray communicationPreferences = dataObjectData.get("communicationPreferences").getAsJsonArray();

            assertEquals(communicationPreferences.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    communicationPreferences.get(0).getAsJsonObject().get("updatedBy").getAsString());
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(communicationPreferences.get(0).getAsJsonObject().get("updatedOn").getAsString()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(communicationPreferences.get(0).getAsJsonObject().get("createdOn").getAsString()));

            JsonArray consumerConsent = dataObjectData.get("consumerConsent").getAsJsonArray();

            assertEquals(consumerConsent.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    consumerConsent.get(0).getAsJsonObject().get("updatedBy").getAsString());
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(consumerConsent.get(0).getAsJsonObject().get("updatedOn").getAsString()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(consumerConsent.get(0).getAsJsonObject().get("createdOn").getAsString()));

            JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();

            assertTrue(caseConsumer.get(0).getAsJsonObject().get("consumerRole").getAsString().equalsIgnoreCase(consumerRole.get()));
            assertEquals(caseConsumer.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    caseConsumer.get(0).getAsJsonObject().get("updatedBy").getAsString());
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumer.get(0).getAsJsonObject().get("updatedOn").getAsString()));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumer.get(0).getAsJsonObject().get("createdOn").getAsString()));
        }

    }


    @Then("I will send create CASE_SAVE_EVENT is created with fields in payload for case events")
    public void iWillSendCreateCASE_SAVE_EVENTIsCreatedWithFieldsInPayloadForCaseEvents() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I will send Case Loader API for checking events payload with consumer role {string}")
    public void iWillSendCaseLoaderAPIForCheckingEventsPayloadWithConsumerRole(String roleInput) {

        consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        consumerRole.set(roleInput);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(),
                consumerDob.get(), "", "", roleInput));
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        correlationId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();


        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @And("I will take taceId for CONSUMER_SAVE_EVENT for consumer event check")
    public void iWillTakeTaceIdForCONSUMER_SAVE_EVENTForConsumerEventCheck() {
        correlationId.set(EventsUtilities.getLogs("traceid", "consumerId").get(1));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @And("I will initiate get {string} for optin information check")
    public void iWillInitiateGetForOptinInformationCheck(String event) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + event + "%n" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I will verify updatedOn and updatedBy matches the createdon and createdBy values for {string} event")
    public void iWillVerifyUpdatedOnUpdatedByMatchesTheCreatedonCreatedByValuesForEvent(String eventName) {
        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        if (eventName.equals("CONSUMER_SAVE_EVENT")) {
            HashMap eventsData = (HashMap) eventsContent.get(0);
            System.out.println(eventsData.get("payload"));
            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertTrue(dataObjectData.get("createdBy").getAsString().
                    equalsIgnoreCase(dataObjectData.get("updatedBy").getAsString()));
            assertTrue(dataObjectData.get("updatedOn").getAsString().substring(0, 15).
                    equalsIgnoreCase(dataObjectData.get("createdOn").getAsString().substring(0, 15)));


            JsonArray communicationPreferences = dataObjectData.get("communicationPreferences").getAsJsonArray();

            for (int i = 0; i < communicationPreferences.size(); i++) {
                assertEquals(communicationPreferences.get(i).getAsJsonObject().get("createdBy").getAsString(),
                        communicationPreferences.get(i).getAsJsonObject().get("updatedBy").getAsString());
                assertTrue(communicationPreferences.get(i).getAsJsonObject().get("updatedOn").getAsString().
                        equalsIgnoreCase(communicationPreferences.get(i).getAsJsonObject().get("createdOn").getAsString()));
            }
            JsonArray consumerConsent = dataObjectData.get("consumerConsent").getAsJsonArray();
            for (int i = 0; i < consumerConsent.size(); i++) {
                assertEquals(consumerConsent.get(i).getAsJsonObject().get("createdBy").getAsString(),
                        consumerConsent.get(i).getAsJsonObject().get("updatedBy").getAsString());
                assertTrue(consumerConsent.get(i).getAsJsonObject().get("updatedOn").getAsString().
                        equalsIgnoreCase(consumerConsent.get(i).getAsJsonObject().get("createdOn").getAsString()));
            }

            if (dataObjectData.get("caseConsumer").isJsonNull()) {
                System.out.println("CaseConsumer field is null");
            } else {
                JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();

                assertEquals(caseConsumer.get(0).getAsJsonObject().get("createdBy").getAsString(),
                        caseConsumer.get(0).getAsJsonObject().get("updatedBy").getAsString());
                assertTrue(caseConsumer.get(0).getAsJsonObject().get("updatedOn").getAsString().
                        equalsIgnoreCase(caseConsumer.get(0).getAsJsonObject().get("createdOn").getAsString()));
            }
        } else if (eventName.equals("CASE_SAVE_EVENT")) {
            HashMap eventsData = (HashMap) eventsContent.get(0);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertTrue(dataObjectData.get("createdBy").getAsString().equalsIgnoreCase(dataObjectData.get("updatedBy").getAsString()));
            assertTrue(dataObjectData.get("updatedOn").getAsString().substring(0, 15).
                    equalsIgnoreCase(dataObjectData.get("createdOn").getAsString().substring(0, 15)));

            JsonArray communicationPreferences = dataObjectData.get("communicationPreferences").getAsJsonArray();

            assertEquals(communicationPreferences.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    communicationPreferences.get(0).getAsJsonObject().get("updatedBy").getAsString());

            assertTrue(communicationPreferences.get(0).getAsJsonObject().get("updatedOn").getAsString().
                    equalsIgnoreCase(communicationPreferences.get(0).getAsJsonObject().get("createdOn").getAsString()));

            JsonArray consumerConsent = dataObjectData.get("consumerConsent").getAsJsonArray();

            assertEquals(consumerConsent.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    consumerConsent.get(0).getAsJsonObject().get("updatedBy").getAsString());
            assertTrue(consumerConsent.get(0).getAsJsonObject().get("updatedOn").getAsString().
                    equalsIgnoreCase(consumerConsent.get(0).getAsJsonObject().get("createdOn").getAsString()));

            JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();

            assertTrue(caseConsumer.get(0).getAsJsonObject().get("consumerRole").getAsString().equalsIgnoreCase(consumerRole.get()));
            assertEquals(caseConsumer.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    caseConsumer.get(0).getAsJsonObject().get("updatedBy").getAsString());

            assertTrue(caseConsumer.get(0).getAsJsonObject().get("updatedOn").getAsString().
                    equalsIgnoreCase(caseConsumer.get(0).getAsJsonObject().get("createdOn").getAsString()));
        } else if (eventName.equals("CONSUMER_UPDATE_EVENT")) {
            HashMap eventsData = (HashMap) eventsContent.get(0);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertTrue(dataObjectData.get("createdBy").getAsString().equalsIgnoreCase(dataObjectData.get("updatedBy").getAsString()));
            assertTrue(dataObjectData.get("updatedOn").getAsString().substring(0, 15).
                    equalsIgnoreCase(dataObjectData.get("createdOn").getAsString().substring(0, 15)));

            JsonArray communicationPreferences = dataObjectData.get("communicationPreferences").getAsJsonArray();

            assertEquals(communicationPreferences.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    communicationPreferences.get(0).getAsJsonObject().get("updatedBy").getAsString());

            assertTrue(communicationPreferences.get(0).getAsJsonObject().get("updatedOn").getAsString().
                    equalsIgnoreCase(communicationPreferences.get(0).getAsJsonObject().get("createdOn").getAsString()));

            JsonArray consumerConsent = dataObjectData.get("consumerConsent").getAsJsonArray();

            assertEquals(consumerConsent.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    consumerConsent.get(0).getAsJsonObject().get("updatedBy").getAsString());
            assertTrue(consumerConsent.get(0).getAsJsonObject().get("updatedOn").getAsString().
                    equalsIgnoreCase(consumerConsent.get(0).getAsJsonObject().get("createdOn").getAsString()));

            if (dataObjectData.get("caseConsumer").isJsonNull()) {
                System.out.println("CaseConsumer field is null");
            } else {
                JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();

                assertEquals(caseConsumer.get(0).getAsJsonObject().get("createdBy").getAsString(),
                        caseConsumer.get(0).getAsJsonObject().get("updatedBy").getAsString());
                assertTrue(caseConsumer.get(0).getAsJsonObject().get("updatedOn").getAsString().
                        equalsIgnoreCase(caseConsumer.get(0).getAsJsonObject().get("createdOn").getAsString()));
            }
        }
    }

    @Then("I will initiate api call for CONSUMER_UPDATE_EVENT")
    public void IwillinitiateapicallforCONSUMER_UPDATE_EVENT() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
    }
}