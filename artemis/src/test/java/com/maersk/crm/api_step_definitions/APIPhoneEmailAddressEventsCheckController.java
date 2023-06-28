package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class APIPhoneEmailAddressEventsCheckController extends CRMUtilities implements ApiBase {

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(() -> new HashMap(getNewTestData2()));
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseConsumerForEvents = "app/crm/caseloader";

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String getEvents = "app/crm/events?size=10000000&page=0&sort=eventId,desc";
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    private String newEventsAPI = "https://mars-event-api-qa.apps.non-prod.pcf-maersk.com";
    private String getEventsByCorrelationID = "/app/crm/event/correlation/";
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsForEvent = ThreadLocal.withInitial(JsonObject::new);
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();

    private final ThreadLocal<Integer> eventId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<String> correlationId= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> associatedCaseMember= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> userId= ThreadLocal.withInitial(String::new);

    private final ThreadLocal<String> phoneType = ThreadLocal.withInitial(()->"Home");
    public final ThreadLocal<String> addPhoneType = ThreadLocal.withInitial(()->"Work");
    public final ThreadLocal<String> phone = ThreadLocal.withInitial(()->"7774445");
    public final ThreadLocal<String> phoneNum = ThreadLocal.withInitial(()->phone.get() + getRandomNumber(3));
    private final ThreadLocal<String> phoneNumUpdated = ThreadLocal.withInitial(()->getRandomNumber(10));
    private final ThreadLocal<String> emailType = ThreadLocal.withInitial(()->"OFFICE");
    private final ThreadLocal<String> emailAddress = ThreadLocal.withInitial(()->getRandomString(5) + "@" + getRandomString(4) + ".com");
    private final ThreadLocal<String> emailAddressUpdated = ThreadLocal.withInitial(()->getRandomString(5) + "@" + getRandomString(4) + ".com");
    private final ThreadLocal<String> addressLine1 = ThreadLocal.withInitial(()->getRandomNumber(3) + " " + getRandomString(5) + " Ct.");
    private final ThreadLocal<String> addressCity = ThreadLocal.withInitial(()->getRandomString(6) + " City");
    private final ThreadLocal<String> addressCityUpdated = ThreadLocal.withInitial(()->getRandomString(6) + " City");
    private final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(()->getRandomString(6) + " County");
    private final ThreadLocal<String> addressZip = ThreadLocal.withInitial(()->"12345");
    private final ThreadLocal<String> addressType = ThreadLocal.withInitial(()->"Mailing");
    private final ThreadLocal<String> currentDate = ThreadLocal.withInitial(()->getCurrentDate());
    //private APITMEventController apitmEventController = new APITMEventController();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearchPage = new CRMManualCaseConsumerSearchPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRMAddContactInfoPage addContactInfoPage = new CRMAddContactInfoPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMActiveContactPage crmActiveContactPage = new CRMActiveContactPage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();

    @When("I create a consumer for event check")
    public void createConsumerForEventCheck() {
        createNewConsumerForEventCheck();
    }

    @And("I search created consumer for event check")
    public void searchConsumerForEventCheck() {
        manualCaseConsumerSearchPage.firstName.sendKeys(firstName.get());
        manualCaseConsumerSearchPage.lastName.sendKeys(lastName.get());
        manualCaseConsumerSearchPage.lastName.click();
        waitFor(1);
        manualCaseConsumerSearchPage.consumerCancelBtn.click();
        waitFor(1);

        manualCaseConsumerSearchPage.firstName.sendKeys(firstName.get());
        manualCaseConsumerSearchPage.lastName.sendKeys(lastName.get());
        manualCaseConsumerSearchPage.searchButton.click();
        waitFor(4);
        contactRecord.consumerIdFirstRecord.click();

    }

    @And("I add new phone to the created consumer for event check")
    public void addNewPhoneToCreatedConsumer() {
        demographicContactInfoPage.demographicInfoTab.click();
        waitForVisibility(demographicContactInfoPage.addPhoneButton, 10);
        demographicContactInfoPage.addPhoneButton.click();
        waitForVisibility(addContactInfoPage.phoneNumber, 10);
        System.out.println("New phone number added: " + phoneNum.get());
        clearAndFillText(addContactInfoPage.phoneNumber, phoneNum.get());
        selectDropDown(addContactInfoPage.phoneType, addPhoneType.get());
        waitFor(3);
        clearAndFillText(addContactInfoPage.startDate, getCurrentDate());
        addContactInfoPage.saveButton.click();
        waitForVisibility(demographicContactInfoPage.addPhoneButton, 10);
    }

    //line 127 comment out due change of func CP-11287
    @And("I add new phone to the searched consumer for event check")
    public void addNewPhoneToSearchConsumer() {
        waitForVisibility(demographicContactInfoPage.addPhoneButton, 10);
        scrollDownUsingActions(1);
        waitFor(2);
        demographicContactInfoPage.addPhoneButton.click();
        waitForVisibility(addContactInfoPage.phoneNumber, 10);
        System.out.println("New phone number added: " + phoneNum.get());
        clearAndFillText(addContactInfoPage.phoneNumber, phoneNum.get());
        selectDropDown(addContactInfoPage.phoneType, addPhoneType.get());
        clearAndFillText(addContactInfoPage.startDate, getCurrentDate());
        addContactInfoPage.saveButton.click();
        waitForVisibility(demographicContactInfoPage.addPhoneButton, 10);
    }

    //line 136 137 144 comment out due change of func CP-11287
    @And("I update phone of the created consumer for event check")
    public void updatePhoneOfCreateConsumer() {
        demographicContactInfoPage.demographicInfoTab.click();
        waitFor(2);

        jsClick(addContactInfoPage.contactInfoPhoneFirstRecord);
        waitFor(2);
        System.out.println("phone number updated: " + phoneNumUpdated.get());
        clearAndFillText(addContactInfoPage.phoneNumber, phoneNumUpdated.get());
        addContactInfoPage.saveButton.click();
        waitFor(2);
    }

    //line 161 comment out due change of func CP-11287
    @And("I update phone of the searched consumer for event check")
    public void updatePhoneOfSearchedConsumer() {
        scrollDownUsingActions(4);
        waitFor(1);
        addContactInfoPage.contactInfoPhoneFirstRecord.click();
        waitForClickablility(addContactInfoPage.phoneNumber, 10);
        System.out.println("phone number updated: " + phoneNumUpdated.get());
        clearAndFillText(addContactInfoPage.phoneNumber, phoneNumUpdated.get());
        // selectDropDown(crmAddContactInfoPage.associatedCaseMember, associatedCaseMember);
        addContactInfoPage.saveButton.click();
        waitForClickablility(demographicContactInfoPage.demographicInfoTab, 10);
    }

    //line 170 171 178 comment out due change of func CP-11287
    @And("I add new email to the created consumer for event check")
    public void addNewEmailToCreatedConsumer() {
        demographicContactInfoPage.demographicInfoTab.click();
        //  waitForVisibility(demographicContactInfoPage.contactInfoTab, 10);
        //  demographicContactInfoPage.contactInfoTab.click();
        waitForVisibility(demographicContactInfoPage.addEmailButton, 10);
        demographicContactInfoPage.addEmailButton.click();
        waitForVisibility(addContactInfoPage.emailAddressField, 10);

        System.out.println("New email address added: " + emailAddress.get());
        clearAndFillText(addContactInfoPage.emailAddressField, emailAddress.get());
        //  selectDropDown(crmAddContactInfoPage.associatedCaseMember, associatedCaseMember);
        clearAndFillText(addContactInfoPage.startDate, getCurrentDate());
        addContactInfoPage.saveButton.click();
        waitForVisibility(demographicContactInfoPage.addEmailButton, 10);
    }

    //line 194 comment out due change of func CP-11287
    @And("I add new email to the searched consumer for event check")
    public void addNewEmailToSearchConsumer() {
        waitForVisibility(demographicContactInfoPage.addEmailButton, 10);
        scrollDownUsingActions(4);
        waitFor(1);
        jsClick(demographicContactInfoPage.addEmailButton);
        waitForVisibility(addContactInfoPage.emailAddressField, 10);

        System.out.println("New email address added: " + emailAddress.get());
        clearAndFillText(addContactInfoPage.emailAddressField, emailAddress.get());
        //selectDropDown(crmAddContactInfoPage.associatedCaseMember, associatedCaseMember);
        clearAndFillText(addContactInfoPage.startDate, getCurrentDate());
        addContactInfoPage.saveButton.click();
        waitForVisibility(demographicContactInfoPage.addEmailButton, 10);
    }

    //line 204 205 212 comment out due change of func CP-11287
    @And("I update email of the created consumer for event check")
    public void updateEmailOfCreatedConsumer() {
        demographicContactInfoPage.demographicInfoTab.click();
        jsClick(addContactInfoPage.contactInfoEmailFirstRecord);
        waitForClickablility(addContactInfoPage.emailAddressField, 10);
        System.out.println("email address updated: " + emailAddressUpdated.get());
        clearAndFillText(addContactInfoPage.emailAddressField, emailAddressUpdated.get());
        // selectDropDown(crmAddContactInfoPage.associatedCaseMember, associatedCaseMember);
        addContactInfoPage.saveButton.click();
        waitForClickablility(demographicContactInfoPage.demographicInfoTab, 10);
    }

    //line 225 comment out due change of func CP-11287
    @And("I update email of the searched consumer for event check")
    public void updateEmailOfSearchedConsumer() {
        scrollDownUsingActions(4);
        waitFor(1);
        addContactInfoPage.contactInfoEmailFirstRecord.click();
        waitForVisibility(addContactInfoPage.emailAddressField, 10);
        System.out.println("email address updated: " + emailAddressUpdated.get());
        clearAndFillText(addContactInfoPage.emailAddressField, emailAddressUpdated.get());
        // selectDropDown(crmAddContactInfoPage.associatedCaseMember, associatedCaseMember);
        addContactInfoPage.saveButton.click();
        waitForVisibility(demographicContactInfoPage.demographicInfoTab, 10);
    }

    //line 233 234 comment out due change of func CP-11287
    @And("I add new address to the created consumer for event check")
    public void addNewAddressToCreatedConsumer() {
        demographicContactInfoPage.demographicInfoTab.click();
        //waitForVisibility(demographicContactInfoPage.contactInfoTab, 10);
        //demographicContactInfoPage.contactInfoTab.click();
        waitForVisibility(demographicContactInfoPage.addAddressButton, 10);
        jsClick(demographicContactInfoPage.addAddressButton);
        waitForVisibility(addContactInfoPage.addressLineOne, 10);

        System.out.println("New address city added: " + addressCity.get());
        addContactInfoPage.addressLineOne.click();
        clearAndFillText(addContactInfoPage.addressLineOne, addressLine1.get());
        clearAndFillText(addContactInfoPage.city, addressCity.get());
        clearAndFillText(addContactInfoPage.county, addressCounty.get());
        selectRandomDropDownOption(addContactInfoPage.state);
        clearAndFillText(addContactInfoPage.zip, addressZip.get());
        selectDropDown(addContactInfoPage.addressType, "Physical");
        clearAndFillText(addContactInfoPage.startDate, getCurrentDate());
        addContactInfoPage.saveButton.click();
        waitForVisibility(demographicContactInfoPage.addAddressButton, 10);
    }

    @And("I add new address to the searched consumer for event check")
    public void addNewAddressToSearchConsumer() {
        waitForVisibility(demographicContactInfoPage.addAddressButton, 10);
        scrollDownUsingActions(3);
        waitFor(1);
        jsClick(demographicContactInfoPage.addAddressButton);
        waitForVisibility(addContactInfoPage.addressLineOne, 10);

        System.out.println("New address city added: " + addressCity.get());
        addContactInfoPage.addressLineOne.click();
        clearAndFillText(addContactInfoPage.addressLineOne, addressLine1.get());
        clearAndFillText(addContactInfoPage.city, addressCity.get());
        clearAndFillText(addContactInfoPage.county, addressCounty.get());
        selectRandomDropDownOption(addContactInfoPage.state);
        clearAndFillText(addContactInfoPage.zip, addressZip.get());
        selectDropDown(addContactInfoPage.addressType, "Physical");
        clearAndFillText(addContactInfoPage.startDate, getCurrentDate());
        addContactInfoPage.saveButton.click();
        waitForVisibility(demographicContactInfoPage.addAddressButton, 10);
    }

    //line 276 277 comment out due change of func CP-11287
    @And("I update address of the created consumer for event check")
    public void updateAddressOfCreatedConsumer() {
        demographicContactInfoPage.demographicInfoTab.click();
        //waitForVisibility(demographicContactInfoPage.contactInfoTab, 10);
        // demographicContactInfoPage.contactInfoTab.click();
        waitFor(2);

        jsClick(addContactInfoPage.contactInfoAddressFirstRecord);
        waitFor(2);
        System.out.println("address city updated: " + addressCityUpdated.get());
        clearAndFillText(addContactInfoPage.city, addressCityUpdated.get());
        addContactInfoPage.saveButton.click();
        waitFor(2);
    }

    @And("I update address of the searched consumer for event check")
    public void updateAddressOfSearchedConsumer() {
        scrollDownUsingActions(4);
        waitFor(1);
        addContactInfoPage.contactInfoAddressFirstRecord.click();
        waitForClickablility(addContactInfoPage.addressLineOne, 10);
        System.out.println("address city updated: " + addressCityUpdated.get());
        clearAndFillText(addContactInfoPage.city, addressCityUpdated.get());
        addContactInfoPage.saveButton.click();
        waitFor(2);
    }

    @When("I will take correlation Id for {string} for {string} event check")
    public void i_will_take_trace_Id_for_Events(String event, String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType).get(1));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I will verify an {string} is created with fields in payload for {string} event")
    public void verifyThePhoneEmailAddressEvents(String eventName, String eventType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        ////apitmEventController.getAuthenticationToken("BLCRM", "CRM");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        if (eventType.equals("phone")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

                eventId.set((int) eventsData.get("eventId"));

                if (eventName.contains("SAVE"))
                    assertEquals(payLoadData.get("action").getAsString(), "Create");
                else
                    assertEquals(payLoadData.get("action").getAsString(), "Update");

                assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("Phone"));
                assertTrue(verifyDateFormat(payLoadData.get("eventCreatedOn").getAsString()));
                assertNotNull(payLoadData.get("dataObject"));
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                assertEquals(dataObjectData.get("phoneType").getAsString(), phoneType.get());
                if (eventName.contains("SAVE")) {
                    assertEquals(dataObjectData.get("phoneNumber").getAsString(), phoneNum.get());
                    assertTrue(dataObjectData.get("associatedCaseMember").isJsonNull());
                    assertTrue(dataObjectData.get("comments").isJsonNull());
                    assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                    assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                    assertNotNull(dataObjectData.get("primaryIndicator"));
                    assertNotNull(dataObjectData.get("phoneId"));
                } else {
                    assertEquals(dataObjectData.get("phoneNumber").getAsString(), phoneNumUpdated.get());
                    assertTrue(dataObjectData.get("associatedCaseMember").isJsonNull());
                    assertTrue(dataObjectData.get("comments").isJsonNull());
                    assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                    assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                    assertNotNull(dataObjectData.get("primaryIndicator"));
                    assertNotNull(dataObjectData.get("phoneId"));
                }
                assertTrue(verifyDateFormat(dataObjectData.get("effectiveStartDate").getAsString()));
                if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                    assertTrue(verifyDateFormat(dataObjectData.get("effectiveEndDate").getAsString()));
                }
                if (eventName.contains("SAVE")) {
                    assertTrue(verifyDateFormat(dataObjectData.get("createdOn").getAsString()));
                    assertEquals(dataObjectData.get("createdBy").getAsString(), userId.get());
                } else {
                    assertTrue(verifyDateFormat(dataObjectData.get("updatedOn").getAsString()));
                    assertEquals(dataObjectData.get("updatedBy").getAsString(), userId.get());
                }

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    break;
                }
            }
        } else if (eventType.equals("email")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

                eventId.set((int) eventsData.get("eventId"));

                if (eventName.contains("SAVE"))
                    assertEquals(payLoadData.get("action").getAsString(), "Create");
                else
                    assertEquals(payLoadData.get("action").getAsString(), "Update");

                assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("email"));
                assertTrue(verifyDateFormat(payLoadData.get("eventCreatedOn").toString()));
                assertNotNull(payLoadData.get("dataObject"));
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                if (eventName.contains("SAVE")) {
                    assertEquals(dataObjectData.get("emailAddress").getAsString(), emailAddress.get());
                } else {
                    assertEquals(dataObjectData.get("emailAddress").getAsString(), emailAddressUpdated.get());
                }
                assertEquals(dataObjectData.get("emailType").getAsString(), emailType.get());
                assertTrue(dataObjectData.get("associatedCaseMember").isJsonNull());
                assertTrue(verifyDateFormat(dataObjectData.get("effectiveStartDate").getAsString()));
                if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                    assertTrue(verifyDateFormat(dataObjectData.get("effectiveEndDate").getAsString()));
                }
                if (eventName.contains("SAVE")) {
                    assertTrue(verifyDateFormat(dataObjectData.get("createdOn").getAsString()));
                    assertEquals(dataObjectData.get("createdBy").getAsString(), userId.get());
                    assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                    assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                    assertNotNull(dataObjectData.get("primaryIndicator"));
                } else {
                    assertTrue(verifyDateFormat(dataObjectData.get("updatedOn").getAsString()));
                    assertEquals(dataObjectData.get("updatedBy").getAsString(), userId.get());
                    assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                    assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                    assertNotNull(dataObjectData.get("primaryIndicator"));
                }

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    break;
                }
            }
        } else if (eventType.equals("address")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

                eventId.set((int) eventsData.get("eventId"));

                if (eventName.contains("SAVE"))
                    assertEquals(payLoadData.get("action").getAsString(), "Create");
                else
                    assertEquals(payLoadData.get("action").getAsString(), "Update");

                assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("Address"));
                assertTrue(verifyDateFormat(payLoadData.get("eventCreatedOn").getAsString()));
                assertNotNull(payLoadData.get("dataObject"));
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                assertTrue(dataObjectData.get("addressStreet1").getAsString().equalsIgnoreCase(addressLine1.get()));
                if (eventName.contains("SAVE")) {
                    assertTrue(dataObjectData.get("addressCity").getAsString().equalsIgnoreCase(addressCity.get()));
                } else {
                    assertTrue(dataObjectData.get("addressCity").getAsString().equalsIgnoreCase(addressCityUpdated.get()));
                }
                assertTrue(dataObjectData.get("addressZip").getAsString().equalsIgnoreCase(addressZip.get()));
                assertTrue(dataObjectData.get("addressType").getAsString().equalsIgnoreCase(addressType.get()));
                assertTrue(verifyDateFormat(dataObjectData.get("effectiveStartDate").getAsString()));
                if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                    assertTrue(verifyDateFormat(dataObjectData.get("effectiveEndDate").getAsString()));
                }
                if (eventName.contains("SAVE")) {
                    assertTrue(verifyDateFormat(dataObjectData.get("createdOn").getAsString()));
                    assertEquals(dataObjectData.get("createdBy").getAsString(), userId.get());
                    assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                    assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                    assertNotNull(dataObjectData.get("primaryIndicator"));
                } else {
                    assertTrue(verifyDateFormat(dataObjectData.get("updatedOn").getAsString()));
                    assertEquals(dataObjectData.get("updatedBy").getAsString(), userId.get());
                    assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                    assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                    assertNotNull(dataObjectData.get("primaryIndicator"));
                }

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");
                    break;
                }
            }
        }
    }

    @Then("I will verify subscriber received {string} event for {string} is created")
    public void dpbiEventGenerationCheck(String eventName, String subscriberName) {
        boolean recordCreated = true;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName);

        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray jsonSubscriber = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        String subscriberId = new JSONObject(jsonSubscriber.get(0).toString()).get("subscriberId").toString();
        JSONArray subscriberList = new JSONArray(new JSONObject(jsonSubscriber.get(0).toString()).get("subscriberList").toString());

        for (int i = 0; i < subscriberList.length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get(i).toString());
            if (temp.get("eventName").toString().trim().equals(eventName.trim())) {
                assertEquals(temp.get("subscriberId").toString(), subscriberId, "Subscriber Id is wrong");
                assertEquals(temp.get("eventName").toString().trim(), eventName, "Event Name is wrong");
                assertTrue(temp.get("eventSubscriberMappingId").toString().
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

        assertEquals(temp.getInt("subscriberId") + "", subscriberId);
        System.out.println("Subscriber id on DPBI:   " + subscriberId);
        System.out.println("Event is subscribed to DPBI list");
    }

    private void createNewConsumerForEventCheck() {
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
        clearAndFillText(createConsumer.consumerDB, "12/12/1987");
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerEmail, emailAddress.get());
        clearAndFillText(createConsumer.consumerPhoneNum, phoneNum.get());
        selectDropDown(createConsumer.phoneType, phoneType.get());
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, addressType.get());
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, addressLine1.get());
        clearAndFillText(createConsumer.consumerCity, addressCity.get());
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        waitFor(2);
        clearAndFillText(createConsumer.consumerZipCode, addressZip.get());
        waitFor(2);
        createConsumer.createConsumerButton.click();
        waitFor(3);
        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        String consumerId = contactRecord.lblCrmConsumerId.getText();
        String consumerFullName = crmActiveContactPage.fullName.getText();
        associatedCaseMember.set(consumerFullName.concat(" (").concat(consumerId).concat(")"));
        userId.set(contactRecord.userId.getText());
        userId.set(userId.get().substring(3));
        System.out.println("User Id: " + userId.get());
    }

    private boolean verifyDateFormat(String dateField) {
        if (dateField == null)
            return true;
        boolean dateFormatCheck = false;
        String dateFieldFormatted = dateField.substring(1, dateField.length() - 1);
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateFieldFormatted);
            if (date1 != null)
                dateFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return dateFormatCheck;
    }

    public String getCurrentDate() {
        Date currentDate = new Date();
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String actualDate = sdf.format(currentDate);
        return actualDate;

    }

    @Then("I will take trace id for {string}")
    public void iWillTakeTraceIdFor(String eventName) {
        correlationId.set(EventsUtilities.getLogs("traceid", "addressType").get(0));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @And("Verify an {string} is created with fields")
    public void verifyAnIsCreatedWithFields(String eventName) {
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
            eventId.set((int) eventsData.get("eventId"));

            assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("address"));
            assertTrue(verifyDateFormat(payLoadData.get("eventCreatedOn").getAsString()));
            assertNotNull(payLoadData.get("dataObject"));
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
            assertTrue(dataObjectData.get("addressStreet1").getAsString().equalsIgnoreCase(addressLine1.get()));
            assertTrue(dataObjectData.get("addressCity").getAsString().equalsIgnoreCase(addressCity.get()));
            assertTrue(dataObjectData.get("addressZip").getAsString().equalsIgnoreCase(addressZip.get()));
            assertTrue(dataObjectData.get("addressType").getAsString().equalsIgnoreCase(addressType.get()));
            assertTrue(verifyDateFormat(dataObjectData.get("effectiveStartDate").getAsString()));
            if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                assertTrue(verifyDateFormat(dataObjectData.get("effectiveEndDate").getAsString()));
            }
            if (eventsData.get("eventName").equals(eventName)) {
                assertEquals(eventsData.get("status"), "success");
                break;
            }
        }
    }


    @And("I adding new address to Contact Info")
    public void iAddingNewAddressAndVerifyingTheFields() {
        selectRandomDropDownOption(addContactInfoPage.addressConsumer);
        clearAndFillText(addContactInfoPage.addressLineOne, addressLine1.get());
        clearAndFillText(addContactInfoPage.city, addressCity.get());
        clearAndFillText(addContactInfoPage.county, addressCounty.get());
        selectRandomDropDownOption(addContactInfoPage.state);
        clearAndFillText(addContactInfoPage.zip, addressZip.get());
        selectDropDown(addContactInfoPage.addressType, addressType.get());
        clearAndFillText(addContactInfoPage.startDate, currentDate.get());
        waitFor(1);
        addContactInfoPage.saveButton.click();
    }

    @And("I adding new {string} address to Contact Info same address")
    public void iAddingNewAddressAndVerifyingTheFieldsAddresType1(String addressType) {
        try {
            selectRandomDropDownOption(addContactInfoPage.addressConsumer);
        } catch (Exception e) {
        }
        clearAndFillText(addContactInfoPage.addressLineOne, "192 GvrQa Ct.");
        clearAndFillText(addContactInfoPage.city, "NKpTgv City");
        clearAndFillText(addContactInfoPage.county, "QTLqev County");
        selectDropDown(addContactInfoPage.state, "West Virginia");
        clearAndFillText(addContactInfoPage.zip, "12345");
        selectDropDown(addContactInfoPage.addressType, addressType);
        clearAndFillText(addContactInfoPage.startDate, getCurrentDate());
        waitFor(1);
        addContactInfoPage.saveButton.click();
        try {
            jsClick(demographicMemberPage.continueMemberBtn);
        } catch (Exception e) {
        }
    }

    @And("I add new phone to the case consumer for event check")
    public void addNewPhoneToCaseConsumer() {
        scrollDownUsingActions(2);
        waitForVisibility(demographicContactInfoPage.addPhoneButton, 10);
//        scrollDownUsingActions(4);
        waitFor(2);
        demographicContactInfoPage.addPhoneButton.click();
        waitForVisibility(addContactInfoPage.phoneNumber, 10);

        System.out.println("New phone number added: " + phoneNum.get());
        clearAndFillText(addContactInfoPage.phoneNumber, phoneNum.get());
        selectDropDown(addContactInfoPage.phoneType, addPhoneType.get());
        selectRandomDropDownOption(addContactInfoPage.consumer);
        clearAndFillText(addContactInfoPage.startDate, getCurrentDate());
        addContactInfoPage.saveButton.click();
        waitForVisibility(demographicContactInfoPage.addPhoneButton, 10);
    }

    @When("I will take correlation Id for CC {string} for {string} event check")
    public void i_will_take_trace_Id_for_EventsCC(String event, String eventType) {
        waitFor(6);
        correlationId.set(EventsUtilities.getLogs("correlationid", eventType).get(1));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I will verify CC {string} is created with fields in payload for {string} event")
    public void verifyTheCCPhoneEmailAddressEvents(String eventName, String eventType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        ////apitmEventController.getAuthenticationToken("BLCRM", "CRM");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        if (eventType.equals("phone")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);
            HashMap eventsData = null;
            List<LinkedHashMap> jsObj = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            for (int i = 0; i < eventsContent.size(); i++) {
                if (jsObj.get(i).get("eventName").toString().contains(eventName)) {
                    eventsData = (HashMap) eventsContent.get(i);
                    System.out.println("Payload event = === " + eventsData.toString());
                    break;
                }
            }
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            eventId.set((int) eventsData.get("eventId"));

            if (eventName.contains("SAVE"))
                assertEquals(payLoadData.get("action").getAsString(), "Create");
            else
                assertEquals(payLoadData.get("action").getAsString(), "Update");

            assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("PhoneResponse"), "recordType is ===== " + payLoadData.get("recordType").getAsString());
            assertTrue(verifyDateFormat(payLoadData.get("eventCreatedOn").getAsString()));
            assertNotNull(payLoadData.get("dataObject"));
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
            assertEquals(dataObjectData.get("phoneType").getAsString(), phoneType.get());
            if (eventName.contains("SAVE")) {
                assertEquals(dataObjectData.get("phoneNumber").getAsString(), phoneNum.get());
                assertTrue(dataObjectData.get("comments").isJsonNull());
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("primaryIndicator"));
                assertNotNull(dataObjectData.get("phoneId"));
            } else {
                assertEquals(dataObjectData.get("phoneNumber").getAsString(), phoneNumUpdated.get());
                assertTrue(dataObjectData.get("comments").isJsonNull());
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("primaryIndicator"));
                assertNotNull(dataObjectData.get("phoneId"));
            }
            assertTrue(verifyDateFormat(dataObjectData.get("effectiveStartDate").getAsString()));
            if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                assertTrue(verifyDateFormat(dataObjectData.get("effectiveEndDate").getAsString()));
            }
            if (eventName.contains("SAVE")) {
                assertTrue(verifyDateFormat(dataObjectData.get("createdOn").getAsString()));
                assertEquals(dataObjectData.get("createdBy").getAsString(), userId.get());
            } else {
                assertTrue(verifyDateFormat(dataObjectData.get("updatedOn").getAsString()));
                assertEquals(dataObjectData.get("updatedBy").getAsString(), userId.get());
            }

            if (eventsData.get("eventName").equals(eventName)) {
                assertEquals(eventsData.get("status"), "SUCCESS");

            }
        } else if (eventType.equals("email")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);

            HashMap eventsData = null;
            List<LinkedHashMap> jsObj = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            for (int i = 0; i < eventsContent.size(); i++) {
                if (jsObj.get(i).get("eventName").toString().contains(eventName)) {
                    eventsData = (HashMap) eventsContent.get(i);
                    System.out.println("Payload event = === " + eventsData.toString());
                    break;
                }
            }
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            eventId.set((int) eventsData.get("eventId"));

            if (eventName.contains("SAVE"))
                assertEquals(payLoadData.get("action").getAsString(), "Create");
            else
                assertEquals(payLoadData.get("action").getAsString(), "Update");

            assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("EmailResponse"));
            assertTrue(verifyDateFormat(payLoadData.get("eventCreatedOn").toString()));
            assertNotNull(payLoadData.get("dataObject"));
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
            if (eventName.contains("SAVE")) {
                assertEquals(dataObjectData.get("emailAddress").getAsString(), emailAddress.get());
            } else {
                assertEquals(dataObjectData.get("emailAddress").getAsString(), emailAddressUpdated.get());
            }
            if (eventName.contains("SAVE")) {
                assertEquals(dataObjectData.get("emailType").getAsString(), "Primary");
            } else {
                assertEquals(dataObjectData.get("emailType").getAsString(), "OFFICE");
            }
            assertTrue(verifyDateFormat(dataObjectData.get("effectiveStartDate").getAsString()));
            if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                assertTrue(verifyDateFormat(dataObjectData.get("effectiveEndDate").getAsString()));
            }
            if (eventName.contains("SAVE")) {
                assertTrue(verifyDateFormat(dataObjectData.get("createdOn").getAsString()));
                assertEquals(dataObjectData.get("createdBy").getAsString(), userId.get());
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("primaryIndicator"));
            } else {
                assertTrue(verifyDateFormat(dataObjectData.get("updatedOn").getAsString()));
                assertEquals(dataObjectData.get("updatedBy").getAsString(), userId.get());
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("primaryIndicator"));
            }

            if (eventsData.get("eventName").equals(eventName)) {
                assertEquals(eventsData.get("status"), "SUCCESS");
            }
        } else if (eventType.equals("address")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);

            HashMap eventsData = null;
            List<LinkedHashMap> jsObj = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            for (int i = 0; i < eventsContent.size(); i++) {
                if (jsObj.get(i).get("eventName").toString().contains(eventName)) {
                    eventsData = (HashMap) eventsContent.get(i);
                    System.out.println("Payload event = === " + eventsData.toString());
                    break;
                }
            }
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            eventId.set((int) eventsData.get("eventId"));

            if (eventName.contains("SAVE"))
                assertEquals(payLoadData.get("action").getAsString(), "Create");
            else
                assertEquals(payLoadData.get("action").getAsString(), "Update");

            assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("AddressResponse"));
            assertTrue(verifyDateFormat(payLoadData.get("eventCreatedOn").getAsString()));
            assertNotNull(payLoadData.get("dataObject"));
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
            assertTrue(dataObjectData.get("addressStreet1").getAsString().equalsIgnoreCase(addressLine1.get()));
            if (eventName.contains("SAVE")) {
                assertTrue(dataObjectData.get("addressCity").getAsString().equalsIgnoreCase(addressCity.get()));
            } else {
                assertTrue(dataObjectData.get("addressCity").getAsString().equalsIgnoreCase(addressCityUpdated.get()));
            }
            assertTrue(dataObjectData.get("addressZip").getAsString().equalsIgnoreCase(addressZip.get()));
            assertTrue(dataObjectData.get("addressType").getAsString().equalsIgnoreCase(addressType.get()));
            assertTrue(verifyDateFormat(dataObjectData.get("effectiveStartDate").getAsString()));
            if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                assertTrue(verifyDateFormat(dataObjectData.get("effectiveEndDate").getAsString()));
            }
            if (eventName.contains("SAVE")) {
                assertTrue(verifyDateFormat(dataObjectData.get("createdOn").getAsString()));
                assertEquals(dataObjectData.get("createdBy").getAsString(), userId.get());
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("primaryIndicator"));
            } else {
                assertTrue(verifyDateFormat(dataObjectData.get("updatedOn").getAsString()));
                assertEquals(dataObjectData.get("updatedBy").getAsString(), userId.get());
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertFalse(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("primaryIndicator"));
            }

            if (eventsData.get("eventName").equals(eventName)) {
                assertEquals(eventsData.get("status"), "SUCCESS");
            }
        }
    }

    @When("I will take correlation Id for {string} with {string}")
    public void i_will_take_trace_Id_for_phoneUpdateEvent(String eventType, String type) {
        waitFor(6);
        correlationId.set(EventsUtilities.getLogs("correlationid", type).get(2));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

}
