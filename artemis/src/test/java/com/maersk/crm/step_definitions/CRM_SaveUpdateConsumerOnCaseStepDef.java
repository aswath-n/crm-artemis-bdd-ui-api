package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.pages.crm.CRMDemographicMemberPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.*;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

public class CRM_SaveUpdateConsumerOnCaseStepDef extends CRMUtilities implements ApiBase {

    private final String caseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private final String searchCaseConsumer = "app/crm/case/consumers";
    private final String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private APITMEventController apitmEventController = new APITMEventController();

    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMOptInOutDemographicMember createCaseLoader = new CRMOptInOutDemographicMember();

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> currentDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> userId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> firstNameMember  = ThreadLocal.withInitial(() -> "fn".concat(getRandomString(5)));
    private final ThreadLocal<String> lastNameMember = ThreadLocal.withInitial(() -> "ln".concat(getRandomString(5)));
    private final ThreadLocal<String> startDateMember = ThreadLocal.withInitial(() -> "01/01/2019");
    private final ThreadLocal<String> endDateMember = ThreadLocal.withInitial(() -> "01/01/2020");
    private final ThreadLocal<String> genderMember = ThreadLocal.withInitial(() -> "Male");
    private final ThreadLocal<String> relationshipToPIMember = ThreadLocal.withInitial(() -> "Guardian");
    private final ThreadLocal<String> endDateMemberUpdated = ThreadLocal.withInitial(() -> "06/01/2020");

    @When("I create new consumer using case loader for case consumer date field check for {string}")
    public void createConsumerCaseConsumerCheck(String consumerRoleInput) {
        Map<String, String> consumerNames = createCaseLoader.createACaseUsingCaseLoaderAPIForDemographicMember(consumerRoleInput);
        firstName.set(consumerNames.get("firstName"));
        lastName.set(consumerNames.get("lastName"));
        correlationId.set(consumerNames.get("correlationId"));
        System.out.println("Create consumer First Name: " + firstName.get() + " and Last Name: " + lastName);
    }

    @When("I will take correlation Id for {string} for {string} for case consumer date field check")
    public void i_will_take_trace_Id_for_Events(String event, String eventType) {
        eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";
        if (event.contains("SAVE")) {
            correlationId.set(EventsUtilities.getLogs("traceid", eventType).get(1));
        } else if (event.contains("UPDATE")) {
            correlationId.set(EventsUtilities.getLogs("traceid", eventType).get(2));
        }
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @When("I search consumer with first name and last name for case consumer date field check")
    public void searchConsumerCommCaseConsumerCheck() {
        contactRecord.firstName.sendKeys(firstName.get());
        contactRecord.lastName.sendKeys(lastName.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(1);
        contactRecord.caseIdFirstRecord.click();
        waitFor(2);
    }

    @And("I add {string} for case consumer date field check with {string} End Date")
    public void addDemMeberCaseConsumerCheck(String type, String endDate) {
        if (endDate.equals("populated"))
            endDate = endDateMember.get();
        Map<String, String> memberNames = createCaseLoader.addDemMeber(type, endDate);
        firstNameMember.set(memberNames.get("firstName"));
        lastNameMember.set(memberNames.get("lastName"));
        System.out.println("Member Created. First Name: " + firstName + " Last Name: " + lastName);
    }

    @And("I update added {string} for case consumer date field check")
    public void updateDemMemberCaseConsumerCheck(String type) {
        scrollUpUsingActions(1);
        waitFor(2);
        contactRecord.consumerSearchFirstRecord.click();
        if (type.equals("Authorized Representative")) {
            waitForVisibility(demographicMemberPage.authorizedRepPage, 10);
            waitFor(1);
        } else if (type.equals("Primary Individual")) {
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);
            selectDropDown(demographicMemberPage.caseGender, genderMember.get());
        } else if (type.equals("Case Member")) {
            waitForVisibility(demographicMemberPage.caseMemberPage, 10);
            selectDropDown(demographicMemberPage.relationShp, relationshipToPIMember.get());

            selectDropDown(demographicMemberPage.caseGender, genderMember.get());
        }
        waitFor(1);
        clearAndFillText(demographicMemberPage.effectiveEndDate, endDateMemberUpdated.get());
        waitFor(1);

        try {
            demographicMemberPage.saveMemberBtn.click();
            waitForVisibility(demographicMemberPage.continueMemberBtn, 3);
            demographicMemberPage.continueMemberBtn.click();
            waitFor(2);
        } catch (Exception e) {
            demographicMemberPage.saveMemberBtn.click();
            waitForVisibility(demographicMemberPage.continueMemberBtn, 10);
            demographicMemberPage.continueMemberBtn.click();
            waitFor(2);
        }

        userId.set(contactRecord.userId.getText());
        userId.set(userId.get().substring(3));
        System.out.println("User Id Save Update Consumer on Case: " + userId.get());
        waitForVisibility(demographicContactInfo.demographicInfoTabFromSearch, 10);
    }

    @Then("I will verify effective start date and end date for {string} case consumer date field check")
    public void verifyDatesCaseConsumerCheck(String dateCapture) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchCaseConsumer);

        JsonObject searchRequest = new JsonObject();
        searchRequest.addProperty("consumerFirstName", firstNameMember.get());
        searchRequest.addProperty("consumerLastName", lastNameMember.get());
        requestParams.set(searchRequest);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());

        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        List resultContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");
        assertTrue(resultContent.size() > 0);

        for (int i = 0; i < resultContent.size(); i++) {
            HashMap resultData = (HashMap) resultContent.get(i);
            System.out.println(resultData.get("consumers"));

            ArrayList consumersArray = (ArrayList) resultData.get("consumers");
            assertTrue(consumersArray.size() > 0);

            for (int j = 0; i < consumersArray.size(); i++) {
                HashMap consumerData = (HashMap) consumersArray.get(j);
                if (consumerData.get("consumerFirstName").toString().toLowerCase().equals(firstName.get()))
                    continue;
                assertEquals(consumerData.get("consumerFirstName").toString().toLowerCase(), firstNameMember.get().toLowerCase());
                assertEquals(consumerData.get("consumerLastName").toString().toLowerCase(), lastNameMember.get().toLowerCase());
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(consumerData.get("effectiveStartDate").toString()), startDateMember.get());
                assertNull(consumerData.get("effectiveEndDate"));

                if (dateCapture.contains("NoEnd")) {
                    assertNull(consumerData.get("effectiveEndDate"));
                } else if (dateCapture.contains("YesEnd")) {
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(consumerData.get("effectiveEndDate").toString()));
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(consumerData.get("effectiveEndDate").toString()), endDateMember.get());
                }

                ArrayList caseConsumersArray = (ArrayList) consumerData.get("caseConsumers");
                assertEquals(caseConsumersArray.size(), 1);

                HashMap caseConsumerData = (HashMap) caseConsumersArray.get(0);
                if (dateCapture.contains("NoEnd")) {
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumerData.get("effectiveStartDate").toString()));
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(caseConsumerData.get("effectiveStartDate").toString()), startDateMember.get());
                    assertNull(caseConsumerData.get("effectiveEndDate"));
                } else if (dateCapture.contains("YesEnd")) {
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumerData.get("effectiveStartDate").toString()));
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(caseConsumerData.get("effectiveStartDate").toString()), startDateMember.get());
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumerData.get("effectiveEndDate").toString()));
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(caseConsumerData.get("effectiveEndDate").toString()), endDateMember.get());
                } else if (dateCapture.contains("update")) {
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumerData.get("effectiveStartDate").toString()));
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(caseConsumerData.get("effectiveStartDate").toString()), startDateMember.get());
                    assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumerData.get("effectiveEndDate").toString()));
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(caseConsumerData.get("effectiveEndDate").toString()), endDateMemberUpdated.get());
                }
            }
        }
    }

    @Then("I will verify an {string} is created for case consumer date field check in payload for {string} event")
    public void verifyDupeFieldsEvent(String eventName, String type) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        ////apitmEventController.getAuthenticationToken("BLCRM", "CRM");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            if (eventName.contains("SAVE"))
                assertEquals(payLoadData.get("action").getAsString(), "Create");
            else
                assertEquals(payLoadData.get("action").getAsString(), "Update");

            assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("Consumer"));
            assertNotNull(payLoadData.get("dataObject"));
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertEquals(dataObjectData.get("consumerFirstName").getAsString().toLowerCase(), firstNameMember.get().toLowerCase());
            assertEquals(dataObjectData.get("consumerLastName").getAsString().toLowerCase(), lastNameMember.get().toLowerCase());
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(dataObjectData.get("effectiveStartDate").getAsString()), startDateMember.get());
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(dataObjectData.get("effectiveStartDate").getAsString()));
            assertTrue(dataObjectData.get("effectiveEndDate").isJsonNull());

            JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();
            JsonObject caseConsumerData = caseConsumer.get(0).getAsJsonObject();

            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumerData.get("effectiveStartDate").getAsString()));
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(caseConsumerData.get("effectiveStartDate").getAsString()), startDateMember.get());
            if (type.contains("No")) {
                assertTrue(caseConsumerData.get("effectiveEndDate").isJsonNull());
            } else {
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumerData.get("effectiveEndDate").getAsString()));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(caseConsumerData.get("effectiveEndDate").getAsString()), endDateMemberUpdated.get());
            }
            if (!eventName.contains("SAVE")) {
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(caseConsumerData.get("updatedOn").getAsString()));
                assertEquals(caseConsumerData.get("updatedBy").getAsString(), userId);
            }

            if (eventsData.get("eventName").equals(eventName)) {
                assertEquals(eventsData.get("status"), "success");
                break;
            }
        }
    }

    @Then("I will verify Case Member end dated")
    public void i_will_verify_Case_Member_end_dated() {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();
            JsonObject caseConsumerData = caseConsumer.get(0).getAsJsonObject();

            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(caseConsumerData.get("effectiveEndDate").getAsString()), getCurrentDate());
        }
    }
}