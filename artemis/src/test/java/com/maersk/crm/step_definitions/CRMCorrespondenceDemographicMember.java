package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CRMCorrespondenceDemographicMember extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    //private APITMEventController apitmEventController = new APITMEventController();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMAddAuthorizedRepresentativePage AuthorizedRep = new CRMAddAuthorizedRepresentativePage();
    CRMOptInOutDemographicMember createCaseMember = new CRMOptInOutDemographicMember();

    private final String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private final String createCaseConsumerForEvents = "app/crm/caseloader";
    private final String eventsBaseURI = ConfigurationReader.getProperty("apiEventsURI");
    private final ThreadLocal<String> eventsCorrelationIdEndPoint = ThreadLocal.withInitial(() -> "/app/crm/event/correlation/");
    private final ThreadLocal<String> subscribersEndPoint = ThreadLocal.withInitial(() -> "/app/crm/es/event/subscribers");
    private final ThreadLocal<String> subscribersRecordEndPoint = ThreadLocal.withInitial(() -> "/app/crm/es/subscriber/records/");

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> firstNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> eventId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<String> userId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerDob = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> externalId = ThreadLocal.withInitial(String::new);

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();

    private final ThreadLocal<String> firstNameMember = ThreadLocal.withInitial(() -> "fn".concat(getRandomString(5)));
    private final ThreadLocal<String> lastNameMember = ThreadLocal.withInitial(() -> "ln".concat(getRandomString(5)));
    private final ThreadLocal<String> dobMember = ThreadLocal.withInitial(() -> "07/11/1996");
    private final ThreadLocal<String> genderMember = ThreadLocal.withInitial(() -> "Male");
    private final ThreadLocal<String> startDateMember = ThreadLocal.withInitial(() -> "01/01/2019");
    private final ThreadLocal<String> relationshipToPIMember = ThreadLocal.withInitial(() -> "Guardian");
    private final ThreadLocal<String> ssnMember = ThreadLocal.withInitial(() -> getRandomNumber(9));

    @When("I create a case for consumer using Case Loader API with Consumer Role {string} for Correspondence preference")
    public void createACaseUsingCaseLoaderAPIForCorrespondencePref(String consumerRole) {
        consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);
        ////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(),
                consumerDob.get(), "", "", consumerRole));
        System.out.println("Request Params: " + requestParams.get());

        JsonObject caseLoaderValues = requestParams.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject();
        JsonArray consumerValues = caseLoaderValues.getAsJsonObject("case").getAsJsonArray("consumers");
        firstNameCaseLoader.set(consumerValues.get(0).getAsJsonObject().get("consumerFirstName").getAsString());
        lastNameCaseLoader.set(consumerValues.get(0).getAsJsonObject().get("consumerLastName").getAsString());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        waitFor(2);
    }

    @And("I search consumer with first name and last name for Correspondence preference")
    public void searchedForCustomerWithFirstNameAndLastNameForCorrespondencePref() {
        waitFor(3);
        contactRecord.initContact.click();
        System.out.println("First Name of Case Loader:" + firstNameCaseLoader.get());
        System.out.println("Last Name of Case Loader:" + lastNameCaseLoader.get());
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader.get());
        clearAndFillText(contactRecord.lastName, lastNameCaseLoader.get());
        contactRecord.searchButton.click();
        waitFor(5);
    }

    @And("I link the contact to an Consumer Profile for Correspondence preference")
    public void linkTheContactToAnConsumerProfileForCorrespondencePref() {
        waitFor(2);
        contactRecord.contactShowMoreInfoBtn.click();
        waitFor(1);
        contactRecord.contactMoreInfoName.click();
        waitFor(1);
        contactRecord.contactMoreInfoDOB.click();
        waitFor(1);
        contactRecord.contactMoreInfoSSN.click();
        waitFor(2);
        contactRecord.linkRecordButton.click();
    }

    @And("I click on demographic tab and click on existing {string} for Correspondence preference")
    public void clickOnDemographicTabAndClickOnExistingMember(String type) {
        waitForVisibility(demographicContactInfo.demographicInfoTab, 20);
        demographicContactInfo.demographicInfoTab.click();
        waitForVisibility(demographicMemberPage.demographicMembersPage, 10);
        //scrollUpUsingActions(4);
        waitFor(1);
        demographicMemberPage.demographicMembersPage.click();
        waitForVisibility(demographicMemberPage.demographicMembersCheck, 10);

        if (type.equals("Primary Individual")) {
            waitForVisibility(contactRecord.consumerSearchFirstRecordPI, 10);
            contactRecord.consumerSearchFirstRecordPI.click();
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);
        } else if (type.equals("Member")) {
            waitForVisibility(contactRecord.consumerSearchFirstRecordPI, 10);
            contactRecord.consumerSearchFirstRecordPI.click();
            waitForVisibility(demographicMemberPage.caseMemberPage, 10);
        }
    }

    @And("I click on demographic tab and click on add {string} for Correspondence preference")
    public void clickOnDemographicTabAndClickOnAddMember(String type) {
        waitForVisibility(demographicContactInfo.demographicInfoTab, 20);
        demographicContactInfo.demographicInfoTab.click();
        waitForVisibility(demographicMemberPage.demographicMembersPage, 10);
        //scrollUpUsingActions(4);
        waitFor(1);
        demographicMemberPage.demographicMembersPage.click();
        waitForVisibility(demographicMemberPage.demographicMembersCheck, 10);
        if (type.equals("Primary Individual")) {
            demographicMemberPage.addPrimaryIndividualBtn.click();
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);
        } else if (type.equals("Member")) {
            demographicMemberPage.addCaseMemberBtn.click();
            waitForVisibility(demographicMemberPage.caseMemberPage, 10);
        }
    }

    @And("I add {string} for Correspondence Preference check")
    public void addMemberForCorrespondencePref(String type) {
        System.out.println("First Name for Correspondence preference:" + firstNameMember.get());
        System.out.println("Last Name for Correspondence preference:" + lastNameMember.get());
        System.out.println("DOB for Correspondence preference:" + dobMember.get());
        System.out.println("Gender for Correspondence preference:" + genderMember.get());
        System.out.println("Start Date for Correspondence preference:" + startDateMember.get());
        System.out.println("SSN for Correspondence preference:" + ssnMember.get());

        if (type.equals("Primary Individual")) {
            waitFor(1);
            selectDropDown(demographicMemberPage.receiveCorrespondence, "Yes");
            waitFor(1);
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
            demographicMemberPage.correspondencePrefPI.click();
            waitFor(1);
            demographicMemberPage.correspondencePrefDropDownVal.click();
        } else if (type.equals("Case Member")) {
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
            demographicMemberPage.correspondencePrefPI.click();
            waitFor(1);
            demographicMemberPage.correspondencePrefDropDownVal.click();
            waitFor(1);
            selectDropDown(demographicMemberPage.receiveCorrespondence, "Yes");
        }

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

        waitForVisibility(demographicContactInfo.demographicInfoTabFromSearch, 10);
        userId.set(contactRecord.userId.getText());
        userId.set(userId.get().substring(3));
        System.out.println("User Id: " + userId.get());
    }

    @And("I update {string} for Correspondence Preference check")
    public void updateCorrespondencePrefForMember(String type) {
        if (type.equals("Primary Individual")) {
            contactRecord.consumerSearchFirstRecord.click();
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);
        } else if (type.equals("Case Member")) {
            contactRecord.consumerSearchSecondRecord.click();
            waitForVisibility(demographicMemberPage.caseMemberPage, 10);
        }

        demographicMemberPage.correspondencePrefPI.click();
        waitFor(1);
        demographicMemberPage.getCorrespondencePrefDropDownValSelected.click();
        waitFor(1);

        demographicMemberPage.saveMemberBtn.click();
        waitForVisibility(demographicMemberPage.continueMemberBtn, 10);
        demographicMemberPage.continueMemberBtn.click();

        waitForVisibility(demographicContactInfo.demographicInfoTabFromSearch, 10);
    }

    @And("I deselect correspondence preference option for {string}")
    public void deselectCorrespondencePref(String type) {
        if (type.equals("Primary Individual")) {
            contactRecord.consumerSearchFirstRecordPI.click();
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);
            AuthorizedRep.correspondencePreferenceDropdownOptions.click();
            waitForVisibility(createConsumer.correspondencePrefSelect, 10);
            createConsumer.correspondencePaperlessValue.click();
            waitFor(2);
        } else if (type.equals("Member")) {
            contactRecord.consumerSearchSecondRecord.click();
            waitForVisibility(demographicMemberPage.caseMemberPage, 10);
            AuthorizedRep.correspondencePreferenceDropdownOptions.click();
            waitForVisibility(createConsumer.correspondencePrefNonSelect, 10);
            createConsumer.correspondencePrefNonSelect.click();
            waitFor(2);
        }
    }

    @When("I will take {string} for {string} for {string} correspondence preferences check")
    public void i_will_take_trace_Id_for_Events(String idType, String event, String eventType) {
        if (event.contains("CONSUMER_SAVE")) {
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(1));
        } else if (event.contains("CONSUMER_UPDATE")) {
            System.out.println();
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(1));

        }
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint.set(eventsCorrelationIdEndPoint.get() + correlationId.get());
    }

    @Then("I verify that Consumers Correspondence Preference is present")
    public void verifyConsumerCorrespondencePrefPresent() {
        assertTrue(demographicMemberPage.correspondencePrefPI.isDisplayed());
        assertFalse(markedMandatory(demographicMemberPage.correspondencePrefPI));
    }

    @Then("I verify Consumers Correspondence Preference dropdown option based on {string} for {string}")
    public void verifyCorrespondencePrefDropdown(String select, String memberType) {
        waitFor(2);
        if (select.equals("nonselected")) {
            createConsumer.consumerEditCorrespondencePref.click();
            createConsumer.correspondencePrefNonSelect.click();
            System.out.println("Actual dropdown value for CORRESPONDENCE PREFERENCE(S) " + createConsumer.consumerEditCorrespondencePref.getText());
            assertFalse(createConsumer.consumerEditCorrespondencePref.getText().contains("Paperless"));
        } else if (select.equals("selected")) {
            if (memberType.equals("Case Member")) {
                createConsumer.consumerEditCorrespondencePref.click();
                System.out.println("Preference: " + createConsumer.correspondencePrefNonSelect.getText());
                Assert.assertTrue(createConsumer.correspondencePrefNonSelect.getText().contains("Paperless"));
            } else {
                createConsumer.consumerEditCorrespondencePref.click();
                System.out.println("Preference: " + createConsumer.correspondencePrefNonSelect.getText());
                assertTrue(createConsumer.correspondencePrefNonSelect.getText().contains("Paperless"));
            }
        }
    }

    @Then("I verify Consumers Correspondence Preference multi select dropdown option for {string}")
    public void verifyCorrespondencePrefMultiSelectDropdown(String memberType) {
        if (memberType.equals("Primary Individual")) {
            selectOptionFromMultiSelectDropDown(createConsumer.consumerEditCorrespondencePref, "CORRESPONDENCE_PAPERLESS");
            assertTrue(createConsumer.consumerEditCorrespondencePref.isDisplayed());
        } else if (memberType.equals("Case Member")) {
            selectOptionFromMultiSelectDropDown(createConsumer.consumerEditCorrespondencePref, "CORRESPONDENCE_PAPERLESS");
            assertTrue(createConsumer.consumerEditCorrespondencePref.isDisplayed());
        }
    }

    @Then("I will verify the {string} in the {string} for Correspondence Preference check")
    public void verifyTheCorrespondencePrefInTheCASE_MEMBER_SAVE_EVENT(String type, String eventName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            eventId.set((int) eventsData.get("eventId"));

            Assert.assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");

            assertTrue(dataObjectData.get("consumerFirstName").getAsString().equalsIgnoreCase(firstNameMember.get()));
            assertTrue(dataObjectData.get("consumerLastName").getAsString().equalsIgnoreCase(lastNameMember.get()));
            Assert.assertEquals(dataObjectData.get("consumerType").getAsString(), "Consumer");

            JsonArray communicationPrefList = dataObjectData.get("communicationPreferences").getAsJsonArray();
            boolean commPrefFound = false;

            if (type.contains("SAVE")) {
                Assert.assertEquals(payLoadData.get("action").getAsString(), "Create");
                for (JsonElement communicationPref : communicationPrefList) {
                    String commPref = communicationPref.getAsJsonObject().get("valuePairIdCommPref").getAsString();
                    if (commPref.equals("CORRESPONDENCE_PAPERLESS")) {
                        assertEquals(commPref, "CORRESPONDENCE_PAPERLESS");
                        commPrefFound = true;
                        assertEquals(communicationPref.getAsJsonObject().get("createdBy").getAsString(), userId.get());
                        assertTrue(verifyDatetimeFormat(communicationPref.getAsJsonObject().get("createdOn").getAsString()));
                        assertEquals(communicationPref.getAsJsonObject().get("updatedBy").getAsString(), userId.get());
                        assertTrue(verifyDatetimeFormat(communicationPref.getAsJsonObject().get("updatedOn").getAsString()));
                        assertTrue(verifyDatetimeFormat(communicationPref.getAsJsonObject().get("effectiveStartDate").getAsString()));
                        assertTrue(communicationPref.getAsJsonObject().get("effectiveEndDate").isJsonNull());
                        assertTrue((communicationPref.getAsJsonObject().get("communicationPreferencesId").getAsInt() + "").chars().allMatch(Character::isDigit));
                        assertTrue(communicationPref.getAsJsonObject().get("caseId").isJsonNull());
                        assertFalse(communicationPref.getAsJsonObject().get("defaultInd").getAsBoolean());
                    }
                }
            } else if (type.contains("UPDATE")) {
                Assert.assertEquals(payLoadData.get("action").getAsString(), "Update");
                for (JsonElement communicationPref : communicationPrefList) {
                    JsonElement commPref = communicationPref.getAsJsonObject().get("valuePairIdCommPref");
                    if (commPref.isJsonNull()) {
                        assertTrue(communicationPref.getAsJsonObject().get("valuePairIdCommPref").isJsonNull());
                        assertEquals(communicationPref.getAsJsonObject().get("createdBy").getAsString(), userId.get());
                        assertTrue(verifyDatetimeFormat(communicationPref.getAsJsonObject().get("createdOn").getAsString()));
                        assertEquals(communicationPref.getAsJsonObject().get("updatedBy").getAsString(), userId.get());
                        assertTrue(verifyDatetimeFormat(communicationPref.getAsJsonObject().get("updatedOn").getAsString()));
                        assertTrue(verifyDatetimeFormat(communicationPref.getAsJsonObject().get("effectiveStartDate").getAsString()));
                        assertTrue(communicationPref.getAsJsonObject().get("effectiveEndDate").isJsonNull());
                        assertTrue((communicationPref.getAsJsonObject().get("communicationPreferencesId").getAsInt() + "").chars().allMatch(Character::isDigit));
                        assertTrue(communicationPref.getAsJsonObject().get("caseId").isJsonNull());
                        assertFalse(communicationPref.getAsJsonObject().get("defaultInd").getAsBoolean());
                    }
                }
            }
            assertTrue(commPrefFound);

            if (eventsData.get("eventName").equals(eventName)) {
                Assert.assertEquals(eventsData.get("status"), "success");
                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);
    }

    @Then("I will verify subscriber received {string} event for {string} is created for Correspondence Preference check")
    public void dpbiConsumerEventGenerationCheckForCorrespondencePref(String eventName, String subscriberName) {
        boolean recordCreated = true;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint.get());


        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName);

        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

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
        Assert.assertTrue(recordCreated, eventName + " is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsBaseURI);
        subscribersRecordEndPoint.set(subscribersRecordEndPoint.get() + subscriberId + "/" + eventId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        Assert.assertEquals(temp.getString("eventName"), eventName);
        System.out.println("Event name on DPBI:  " + eventName);

        Assert.assertEquals(temp.getInt("eventId"), eventId.get());
        System.out.println("Event id on DPBI:  " + eventId.get());

        Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId);
        System.out.println("Subscriber id on DPBI:   " + subscriberId);
        System.out.println("Event is subscribed to DPBI list");
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                        String consumerSSN, String consumerDateOfBirth, String GenderCode,
                                                                        String relationShip, String consumerRole) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNo = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNo " + caseIdentificationNo);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);
        externalId.set(externalConsumerId);

        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo);
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
        if (consumerRole.equals("Member"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", consumerRole);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
//        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
//                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", "2039-12-31");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("identificationNumberType", "CHIP");

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
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

    @When("I edit Consumer Role from {string} to {string} and save")
    public void iEditConsumerRoleFromToAndSave(String fromRole, String toRole) {
        selectDropDown(demographicMemberPage.receiveCorrespondence, "Yes");
        waitFor(1);
        selectDropDown(demographicMemberPage.roleDropdown, toRole);
        waitFor(1);
        if (fromRole.equalsIgnoreCase("Primary Individual")) {
            selectDropDown(demographicMemberPage.relationshipToPI, "Child");
            waitFor(2);
        }
        contactRecord.closeButton.click();
//    reateConsumer.saveButton.click();
        waitFor(1);
        createConsumer.warningMessageContinueButton.click();
        waitFor(2);
    }

    @And("I verify External ID Persists after editing role")
    public void iVerifyExternalIDPersistsAfterEditingRole() throws Throwable {
        String actualId = createConsumer.existingExternalId.getText();
        assertTrue(externalId.get().equals(actualId), "externalId = " + externalId.get() + "   AND     " + "actualId = " + actualId);
    }

    @And("I expand saved {string} view screen")
    public void iExpandSavedViewScreen(String type) {
        // scrollUpUsingActions(2);
        waitFor(1);
        if (type.equals("Primary Individual")) {
            demographicMemberPage.firstPIConsumerID.click();
        } else if (type.equals("Member")) {
            try {
                demographicMemberPage.firstCMConsumerID.click();
            } catch (Exception e) {
                demographicMemberPage.caseMemberEachRows.get(0).click();
            }
        }
    }


}