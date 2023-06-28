package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_ConsumerOptOutStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMTaskManageMyTasksPage MyTasksPage = new CRMTaskManageMyTasksPage();

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String getConsumerSaveEvents = "app/crm/events?size=10&page=0&sort=eventId,desc";
    private String eventURI2 = ConfigurationReader.getProperty("apiMarsEvent");
    private String getConsumerSaveEvents2 = "?size=10&page=0&sort=eventId,desc";

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private String consumerId;

    @When("I create consumer for Contact Record for Consumer {string} Opt in")
    public void createConsumerForContactRecordForConsumerOptout(String optOutType) {
        createNewConsumerForContactRecordForConsumerOptout(optOutType);
    }

    @And("I initiate Consumer Events API to check from {string} Consumer Opt in")
    public void initiateEventsAPIForConsumerOptout(String eventName) {
        requestParams.set(generateEventRequest(eventName));
        System.out.println(requestParams.get());


        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI2);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerSaveEvents2);

        //apitmEventController.getAuthenticationToken("BLCRM", "CRM");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I validate Consumer {string} Opt in details from {string} for the Consumer")
    public void validateConsumerOptout(String optOutType, String eventName) {
        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            System.out.println("Payload: " + payLoadData);

            JsonElement dataObject = payLoadData.get("dataObject");

            if (dataObject.getAsJsonObject().get("consumerFirstName").toString().contains(firstName.get())) {
                assertEquals(eventsData.get("status"), "success");
                assertTrue(dataObject.getAsJsonObject().get("consumerFirstName").toString().contains(firstName.get()));
                assertTrue(dataObject.getAsJsonObject().get("consumerLastName").toString().contains(lastName.get()));

                JsonArray consumerConsent = dataObject.getAsJsonObject().getAsJsonArray("consumerConsent");
                for (JsonElement cc : consumerConsent) {
                    if (cc.getAsJsonObject().get("consentType").getAsString().equalsIgnoreCase(optOutType)) {
                        assertTrue(cc.getAsJsonObject().get("optIn").getAsBoolean());
                    } else {
                        assertFalse(cc.getAsJsonObject().get("optIn").getAsBoolean());
                    }
                }

                recordFound = true;
                break;
            }
        }
        // assertTrue(recordFound);
    }

    private void createNewConsumerForContactRecordForConsumerOptout(String optOutType) {
        waitFor(3);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());

        clearAndFillText(contactRecord.firstName, firstName.get());
        clearAndFillText(contactRecord.lastName, lastName.get());
        consumerName.set(firstName.get() + " " + lastName.get());
        System.out.println(firstName.get() + " " + lastName.get());

        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitFor(2);

        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        clearAndFillText(createConsumer.consumerFN, firstName.get());
        clearAndFillText(createConsumer.consumerLN, lastName.get());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        selectRandomDropDownOption(createConsumer.consumerGender);

        if (optOutType.equals("phone")) {
            if (!createConsumer.commOptMail.isSelected()) {
                createConsumer.commOptMail.click();
            }
        } else if (optOutType.equals("text")) {
            createConsumer.commOptMail.click();
            createConsumer.commOptPhone.click();
            if (!createConsumer.commOptText.isSelected()) {
                createConsumer.commOptText.click();
            }
        } else if (optOutType.equals("email")) {
            if (!createConsumer.commOptMail.isSelected()) {
                createConsumer.commOptMail.click();
            }
            createConsumer.commOptPhone.click();
            createConsumer.commOptEmail.click();
        } else if (optOutType.equals("fax")) {
            createConsumer.commOptMail.click();
            createConsumer.commOptPhone.click();
            if (!createConsumer.commOptFax.isSelected()) {
                createConsumer.commOptFax.click();
            }
        } else if (optOutType.equals("mail")) {
            createConsumer.commOptPhone.click();
        }

        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        waitFor(2);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
    }

    private JsonObject generateEventRequest(String eventName) {
        JsonObject request = new JsonObject();

        request.addProperty("eventId", "");
        request.addProperty("eventName", eventName);
        request.addProperty("module", "");
        request.addProperty("payload", "");
        request.addProperty("status", "");
        request.addProperty("errorStack", "");
        request.addProperty("correlationId", "");
        request.addProperty("uiid", "");
        request.addProperty("createdOn", "");
        request.addProperty("updatedOn", "");
        request.addProperty("createdBy", "");
        request.addProperty("updatedBy", "");
        request.addProperty("message", "");

        return request;
    }

    @And("I check all optIn checkbox")
    public void optIn_checkbox() {
        contactRecord.conEmail.click();
        contactRecord.conFax.click();
        contactRecord.conMail.click();
        contactRecord.conPhone.click();
        contactRecord.conText.click();
        jsClick(MyTasksPage.saveBtn);
    }

    @Then("I verify Consumer {string} Opt in details from {string} for the Consumer")
    public void validateConsumerOptoutBLCRM(String optOutType, String eventName) {
        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));
            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            System.out.println("Payload: " + payLoadData);
            JsonElement dataObject = payLoadData.get("dataObject");
            if (dataObject.getAsJsonObject().get("consumerFirstName").toString().contains(firstName.get())) {
                assertEquals(eventsData.get("status"), "success");
                assertTrue(dataObject.getAsJsonObject().get("consumerFirstName").toString().contains(firstName.get()));
                assertTrue(dataObject.getAsJsonObject().get("consumerLastName").toString().contains(lastName.get()));
                JsonArray consumerConsent = dataObject.getAsJsonObject().getAsJsonArray("consumerConsent");
                for (JsonElement cc : consumerConsent) {
                    if (cc.getAsJsonObject().get("consentType").getAsString().equalsIgnoreCase(optOutType)) {
                        assertTrue(cc.getAsJsonObject().get("optIn").getAsBoolean());
                    } else {
                        assertFalse(cc.getAsJsonObject().get("optIn").getAsBoolean());
                    }
                }
                break;
            }
        }
    }
}
