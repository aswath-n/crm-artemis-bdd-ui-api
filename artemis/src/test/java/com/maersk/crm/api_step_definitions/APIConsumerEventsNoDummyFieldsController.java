package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.step_definitions.CRMOptInOutDemographicMember;
import com.maersk.crm.step_definitions.CRM_ContactRecordConsumerSummaryStepDef;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class APIConsumerEventsNoDummyFieldsController extends CRMUtilities implements ApiBase {

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseConsumerForEvents = "app/crm/caseloader";
    private String eventURI = ConfigurationReader.getProperty("apisURI");
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> accessTypeMember = ThreadLocal.withInitial(() -> "Full Access");
    private final ThreadLocal<String> updateDOB = ThreadLocal.withInitial(() -> "01/01/1995");
    private final ThreadLocal<String> updateDOBCaseLoader = ThreadLocal.withInitial(() -> "1995-01-01");
    private CRMCreateConsumerProfilePage crmCreateConsumerProfilePage = new CRMCreateConsumerProfilePage();
    private CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();
    private CRMContactRecordUIPage crmContactRecordUIPage1 = new CRMContactRecordUIPage();
    private CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    private CRMEditConsumerProfilePage crmEditConsumerProfilePage = new CRMEditConsumerProfilePage();
    private CRMUnidentifiedContactPage unidentifiedContact = new CRMUnidentifiedContactPage();
    private CRMOptInOutDemographicMember crmOptInOutDemographicMember = new CRMOptInOutDemographicMember();
    private CRM_ContactRecordConsumerSummaryStepDef contactRecordConsumer = new CRM_ContactRecordConsumerSummaryStepDef();

    @When("I create new consumer for no Dummy field check")
    public void contactRecordConsumerNoDummyFieldCheck() {
        Map<String, String> consumerNames = contactRecordConsumer.createNewConsumerForContactRecord();
        firstName.set(consumerNames.get("firstName"));
        lastName.set(consumerNames.get("lastName"));
        System.out.println("Create consumer First Name: " + firstName.get() + " and Last Name: " + lastName.get());
    }

    @When("I create new consumer using case loader for no Dummy field check with {string}")
    public void contactRecordConsumerNoDummyFieldCheckCaseLoader(String consumerRoleInput) {
        Map<String, String> consumerNames = crmOptInOutDemographicMember.createACaseUsingCaseLoaderAPIForDemographicMember(consumerRoleInput);
        firstName.set(consumerNames.get("firstName"));
        lastName.set(consumerNames.get("lastName"));
        correlationId.set(consumerNames.get("correlationId"));
        requestParams.set(new JsonParser().parse(consumerNames.get("requestParams")).getAsJsonObject());
        System.out.println("Create consumer First Name: " + firstName.get() + " and Last Name: " + lastName.get());
        System.out.println("Request Params for creating case from case loader: " + requestParams.get());
    }

    @When("I update new consumer using case loader for no Dummy field check")
    public void UpdateConsumerNoDummyFieldCheckCaseLoader() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);

        System.out.println("Request Params: " + requestParams.get());
        requestParams.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", updateDOBCaseLoader.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        correlationId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @When("I search for the created consumer for dummy field check for {string}")
    public void searchConsumerDummyFieldsCheck(String creationType) {
        crmContactRecordUIPage1.firstName.sendKeys(firstName.get());
        crmContactRecordUIPage1.lastName.sendKeys(lastName.get());
        waitFor(2);
        crmContactRecordUIPage1.searchButton.click();
        waitFor(1);
        if (creationType.equals("UI"))
            crmContactRecordUIPage1.consumerIdFirstRecord.click();
        else
            crmContactRecordUIPage1.caseIdFirstRecord.click();
        if (creationType.equals("UI"))
            waitForVisibility(crmEditConsumerProfilePage.consumerEditButton, 10);
        else if (creationType.equals("Case Loader"))
            waitForVisibility(demographicContactInfoPage.demographicInfoTabFromSearch, 10);
    }

    @And("I edit the consumer profile for no Dummy field check")
    public void createConsumerNoDummyFieldsCheck() {
        crmEditConsumerProfilePage.consumerEditButton.click();
        waitForVisibility(crmCreateConsumerProfilePage.consumerEditCorrespondencePref, 10);
        crmCreateConsumerProfilePage.consumerEditCorrespondencePref.click();
        waitForVisibility(crmCreateConsumerProfilePage.consumerDB, 10);
        scrollUpUsingActions(2);
        waitFor(1);
        clearAndFillText(crmCreateConsumerProfilePage.consumerDB, updateDOB.get());
        waitFor(1);
        selectDropDown(crmCreateConsumerProfilePage.consumerEditPreflang, "English");
        waitFor(1);
        unidentifiedContact.saveBtn.click();
        waitForVisibility(crmCreateConsumerProfilePage.continueAfterSaveButton, 10);
        crmCreateConsumerProfilePage.continueAfterSaveButton.click();
        waitForVisibility(crmEditConsumerProfilePage.consumerEditButton, 10);
    }

    @When("I created {string} member from active contact page of created consumer for no dummy field check")
    public void createCaseMemberNoDummyFieldCheck(String memberType) {
        Map<String, String> consumerNames = API_THREAD_LOCAL_FACTORY.getConsumerEventThreadLocal().createMemberFromActiveContact(memberType);
        firstName.set(consumerNames.get("firstName"));
        lastName.set(consumerNames.get("lastName"));
        System.out.println("Create consumer First Name: " + firstName.get() + " and Last Name: " + lastName.get());
    }

    @When("I updated {string} member from active contact page of created consumer for no dummy field check")
    public void updateCaseMemberNoDummyFieldCheck(String memberType) {
        lastName.set("lupd".concat(getRandomString(4)));
        System.out.println("Updated Last Name: " + lastName.get());
        waitFor(1);
        crmContactRecordUIPage1.consumerSearchFirstRecord.click();
        if (memberType.equals("Authorized Representative")) {
            waitForVisibility(demographicMemberPage.authorizedRepPage, 10);

            selectDropDown(demographicMemberPage.arAccessType, accessTypeMember.get());
            clearAndFillText(demographicMemberPage.lastName, lastName.get());
            waitFor(1);
            demographicMemberPage.saveAr.click();
            waitFor(2);
        } else if (memberType.equals("Primary Individual")) {
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);

            clearAndFillText(demographicMemberPage.lastName, lastName.get());
            waitFor(1);
            demographicMemberPage.saveMemberBtn.click();
        } else if (memberType.equals("Member")) {
            waitForVisibility(demographicMemberPage.caseMemberPage, 10);

            clearAndFillText(demographicMemberPage.lastName, lastName.get());
            selectDropDown(demographicMemberPage.caseGender, "Male");
            selectDropDown(demographicMemberPage.relationShp, "Guardian");
            waitFor(1);
            demographicMemberPage.saveAr.click();
        }
        waitFor(1);

        try {
            if (demographicMemberPage.continuePop.isDisplayed())
                demographicMemberPage.continuePop.click();
        } catch (Exception e) {
            System.out.println("Error clicking continue button or it is not displayed");
        }

        waitForVisibility(demographicContactInfoPage.demographicInfoTabFromSearch, 10);
    }

    @When("I will take {string} for {string} for {string} consumer event for no Dummy field check")
    public void i_will_take_trace_Id_for_Events(String idType, String event, String eventType) {
        if (event.contains("SAVE"))
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(1));
        else if (event.contains("UPDATE"))
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(2));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I verify that no dummy fields present in {string} from {string}")
    public void verifyCorrespondencePrefs(String event, String eventType) {
        if (eventType.contains("Case"))
            eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal()
                .jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            if (!eventsData.get("eventName").equals(event))
                continue;

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            if (event.contains("SAVE"))
                assertEquals(payLoadData.get("action").getAsString(), "Create");
            else if (event.contains("UPDATE"))
                assertEquals(payLoadData.get("action").getAsString(), "Update");
            assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");

            assertEquals(dataObjectData.get("consumerFirstName").getAsString().toLowerCase(), firstName.get().toLowerCase());
            assertEquals(dataObjectData.get("consumerLastName").getAsString().toLowerCase(), lastName.get().toLowerCase());
            assertTrue(dataObjectData.get("ethnicityCode").isJsonNull());
            assertTrue(dataObjectData.get("raceCode").isJsonNull());
            assertTrue(dataObjectData.get("usResidentStatusCode").isJsonNull());
            assertTrue(dataObjectData.get("notBornInd").isJsonNull());
            assertTrue(dataObjectData.get("mergedConsumerId").isJsonNull());

            if (eventsData.get("eventName").equals(event)) {
                assertEquals(eventsData.get("status"), "success");
                recordFound = true;
                System.out.println("Event is found");
                break;
            }
        }

        assertTrue(recordFound);
    }
}
