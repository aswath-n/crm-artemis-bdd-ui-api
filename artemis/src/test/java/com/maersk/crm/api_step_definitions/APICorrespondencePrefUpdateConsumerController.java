package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMEditConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMUnidentifiedContactPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.ss.formula.functions.T;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class APICorrespondencePrefUpdateConsumerController extends CRMUtilities implements ApiBase {


    final ThreadLocal <Map<String, Object>> newConsumer = ThreadLocal.withInitial(()->getNewTestData2());
    public static final ThreadLocal <String> consumerName = ThreadLocal.withInitial(()->"");

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String eventsCorrelationIdEndPoint="/app/crm/event/correlation/";

    private final ThreadLocal <String> firstName = ThreadLocal.withInitial(()->newConsumer.get().get("name").toString());
    private final ThreadLocal <String> lastName = ThreadLocal.withInitial(()->newConsumer.get().get("surname").toString());
    private final ThreadLocal <String> dob = ThreadLocal.withInitial(()->"12/01/1991");
    private final ThreadLocal <String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> userId = ThreadLocal.withInitial(String::new);

    private CRMCreateConsumerProfilePage crmCreateConsumerProfilePage = new CRMCreateConsumerProfilePage();
    private CRMUnidentifiedContactPage unidentifiedContact = new CRMUnidentifiedContactPage();
    private CRMContactRecordUIPage crmContactRecordUIPage1 = new CRMContactRecordUIPage();
    private CRMEditConsumerProfilePage crmEditConsumerProfilePage = new CRMEditConsumerProfilePage();

    @When("I create a consumer for Correspondence preference with {string} preferences from consumer profile for API")
    public void createACaseUsingCaseLoaderAPIForCorrespondencePref(String prefs) {
        createNewConsumerForCorrespondencePref(prefs);
    }

    @And("I search consumer with first name and last name for Correspondence preference from consumer profile for API")
    public void searchedForCustomerWithFirstNameAndLastNameForCorrespondencePref() {
        crmContactRecordUIPage1.firstName.sendKeys(firstName.get());
        crmContactRecordUIPage1.lastName.sendKeys(lastName.get());
        waitFor(2);
        crmContactRecordUIPage1.searchButton.click();
        waitFor(1);
        crmContactRecordUIPage1.consumerIdFirstRecord.click();
        waitForVisibility(crmEditConsumerProfilePage.consumerEditButton, 10);
    }

    @When("I will take {string} for {string} for {string} consumer event check for correspondence prefs check")
    public void i_will_take_trace_Id_for_Events(String idType, String event, String eventType) {
        correlationId.set(EventsUtilities.getLogs(idType, eventType).get(3));
//        if (event.contains("FIRST"))
//            correlationId = EventsUtilities.getLogs(idType, eventType).get(3);
//        else {
//            eventsCorrelationIdEndPoint="/app/crm/event/correlation/";
//            correlationId = EventsUtilities.getLogs(idType, eventType).get(0);
//        }
        System.out.println("Log gets correlation id:  "+correlationId);
        eventsCorrelationIdEndPoint=eventsCorrelationIdEndPoint+correlationId;
    }

    @And("I edit the consumer profile to change correspondence preferences to {string} in consumer profile")
    public void editConsumerprofileToChangeCorrespondencePref(String prefType) {
        crmEditConsumerProfilePage.consumerEditButton.click();
        waitForVisibility(crmCreateConsumerProfilePage.consumerEditCorrespondencePref, 10);
        crmCreateConsumerProfilePage.consumerEditCorrespondencePref.click();
        if (prefType.equals("")) {
            waitForVisibility(crmCreateConsumerProfilePage.correspondencePrefSelect, 10);
            crmCreateConsumerProfilePage.correspondencePrefSelect.click();
        } else if (prefType.equals("Paperless")) {
            waitForVisibility(crmCreateConsumerProfilePage.correspondencePrefNonSelect, 10);
            crmCreateConsumerProfilePage.correspondencePrefNonSelect.click();
        }
        waitFor(1);
        selectDropDown(crmCreateConsumerProfilePage.consumerEditPreflang, "English");
        waitFor(1);
        unidentifiedContact.saveBtn.click();
        waitForVisibility(crmCreateConsumerProfilePage.continueAfterSaveButton, 10);
        crmCreateConsumerProfilePage.continueAfterSaveButton.click();
        waitForVisibility(crmEditConsumerProfilePage.consumerEditButton, 10);
    }

    @Then("I verify that Consumers Correspondence Preference is {string} in {string}")
    public void verifyCorrespondencePrefs(String prefType, String event) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: "+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        boolean recordFound = false;

        for(int i=0;i<eventsContent.size();i++) {
            HashMap eventsData = (HashMap)eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            if (event.contains("CONSUMER_UPDATE")) {
                assertEquals(payLoadData.get("action").getAsString(), "Update");
                assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");

                JsonArray commPrefs = dataObjectData.get("communicationPreferences").getAsJsonArray();

                if (commPrefs == null)
                    System.out.println("No Communication Preferences found");

                JsonObject correspondencepref = commPrefs.get(0).getAsJsonObject();

                assertFalse(correspondencepref.get("communicationPreferencesId").isJsonNull());
                assertFalse(correspondencepref.get("consumerId").isJsonNull());
                assertTrue(verifyDatetimeFormat(correspondencepref.get("effectiveStartDate").getAsString()));
                assertTrue(verifyDatetimeFormat(correspondencepref.get("createdOn").getAsString()));
                assertEquals(correspondencepref.get("createdBy").getAsString(), userId);
                assertEquals(correspondencepref.get("valuePairIdCommPref").getAsString(), "CORRESPONDENCE_PAPERLESS");

                if (prefType.equals("")) {
                    assertTrue(verifyDatetimeFormat(correspondencepref.get("effectiveEndDate").getAsString()));
                    assertTrue(verifyDatetimeFormat(correspondencepref.get("updatedOn").getAsString()));
                    assertEquals(correspondencepref.get("updatedBy").getAsString(), userId);
                    assertNull(correspondencepref.get("valuePairIdCommPref"));
                }
            }

            if(eventsData.get("eventName").equals(event)) {
                assertEquals(eventsData.get("status"), "success");
                recordFound = true;
                break;
            }
        }

        assertTrue(recordFound);
    }

    private void createNewConsumerForCorrespondencePref(String prefs) {
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
        clearAndFillText(crmCreateConsumerProfilePage.consumerDB, dob.get());
        clearAndFillText(crmCreateConsumerProfilePage.consumerSSN, getRandomNumber(9));
        waitFor(1);
        selectRandomDropDownOption(crmCreateConsumerProfilePage.consumerGender);
        if (prefs.equals("Paperless"))
            selectDropDown(crmCreateConsumerProfilePage.consumerCorrespondencePref, "Paperless");
        clearAndFillText(crmCreateConsumerProfilePage.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        waitFor(2);
        selectDropDown(crmCreateConsumerProfilePage.phoneType, "Home");
        waitFor(1);
        selectDropDown(crmCreateConsumerProfilePage.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(crmCreateConsumerProfilePage.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(crmCreateConsumerProfilePage.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(crmCreateConsumerProfilePage.consumerState);
        selectRandomDropDownOption(crmCreateConsumerProfilePage.consumerCountyField);
        clearAndFillText(crmCreateConsumerProfilePage.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        crmCreateConsumerProfilePage.createConsumerButton.click();
        waitFor(1);
        waitForVisibility(crmContactRecordUIPage1.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", crmContactRecordUIPage1.lblCrmConsumerId.getText());
        consumerId.set(crmContactRecordUIPage1.lblCrmConsumerId.getText());
        userId.set(crmContactRecordUIPage1.userId.getText());
        userId.set(userId.get().substring(3));
        System.out.println("User Id: "+userId.get());
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