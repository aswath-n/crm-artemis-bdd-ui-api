package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.api_step_definitions.APICaseLoaderEligibilityEnrollmentController;
import com.maersk.crm.api_step_definitions.APITMEventController;
import com.maersk.crm.pages.crm.CRMAddPrimaryIndividualPage;
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
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CRMOptInOutDemographicMember extends CRMUtilities implements ApiBase {
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    private final ThreadLocal<APITMEventController> apitmEventController = ThreadLocal.withInitial(APITMEventController::new);

    private final String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private final String createCaseConsumerForEvents = "app/crm/caseloader";
    private final String eventsBaseURI = ConfigurationReader.getProperty("apiEventsURI");
    private final String eventsURI = "app/crm/events";
    private final ThreadLocal<String> eventsCorrelationIdEndPoint = ThreadLocal.withInitial(() -> "/app/crm/event/correlation/");
    private final ThreadLocal<String> subscribersEndPoint = ThreadLocal.withInitial(() -> "/app/crm/es/event/subscribers");
    private final ThreadLocal<String> subscribersRecordEndPoint = ThreadLocal.withInitial(() -> "/app/crm/es/subscriber/records/");

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);

    public static final ThreadLocal<String> firstNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    private int eventId;
    private final ThreadLocal<String> userId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerDob = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerRole = ThreadLocal.withInitial(() -> "Authorized Representative");

    private final ThreadLocal<String> apiconsumerFirstName = ThreadLocal.withInitial(() -> "fnar" + RandomStringUtils.random(5, true, false));
    private final ThreadLocal<String> apiconsumerLastName = ThreadLocal.withInitial(() -> "lnar" + RandomStringUtils.random(5, true, false));
    private final ThreadLocal<String> apiConsumerSSN = ThreadLocal.withInitial(() -> RandomStringUtils.random(9, false, true));

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private APICaseLoaderEligibilityEnrollmentController caseLoaderRequest = new APICaseLoaderEligibilityEnrollmentController();
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    CRMAddPrimaryIndividualPage crmAddPrimaryIndividualPage = new CRMAddPrimaryIndividualPage();

    private final String accessTypeMember = "Full Access";
    private final ThreadLocal<String> firstNameMember = ThreadLocal.withInitial(() -> "fn".concat(getRandomString(5)));
    private final ThreadLocal<String> lastNameMember = ThreadLocal.withInitial(() -> "ln".concat(getRandomString(5)));
    private final String dobMember = "07/11/1996";
    private final String genderMember = "Male";
    private final String startDateMember = "01/01/2019";
    private final String relationshipToPIMember = "Guardian";
    private final ThreadLocal<String> ssnMember = ThreadLocal.withInitial(() -> getRandomNumber(9));
    private final String languagePref = "Russian";

    @When("I create a case for consumer using Case Loader API for demographic member for {string}")
    public Map<String, String> createACaseUsingCaseLoaderAPIForDemographicMember(String consumerRoleInput) {
        Map<String, String> consumerNames = new HashMap<String, String>();
        consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate).replaceAll("2023", "2020"));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);
        // //apitmEventController.getAuthenticationToken("BLCRM", "CRM");
        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(),
                consumerDob.get(), "", "", consumerRoleInput));
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
        consumerNames.put("firstName", firstNameCaseLoader.get());
        consumerNames.put("lastName", lastNameCaseLoader.get());
        consumerNames.put("correlationId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        consumerNames.put("requestParams", requestParams.get().toString());
        return consumerNames;
    }

    @And("I search consumer with first name and last name for demographic member")
    public void searchedForCustomerWithFirstNameAndLastNameForDemographicMember() {
        waitFor(3);
        if (isElementDisplayed(contactRecord.initContact)) {
            jsClick(contactRecord.initContact);
        }
        System.out.println("First Name of Case Loader:" + firstNameCaseLoader.get());
        System.out.println("Last Name of Case Loader:" + lastNameCaseLoader.get());
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader.get());
        clearAndFillText(contactRecord.lastName, lastNameCaseLoader.get());
        contactRecord.searchButton.click();
        waitFor(5);
    }

    @And("I link the contact to an Consumer Profile for demographic member")
    public void linkTheContactToAnConsumerProfileForDemographicMember() {
        scrollDownUsingActions(1);
        waitFor(2);
        contactRecord.contactShowMoreInfo.click();
        waitFor(1);
        contactRecord.contactMoreInfoName.click();
        waitFor(1);
        contactRecord.contactMoreInfoDOB.click();
        waitFor(1);
        contactRecord.contactMoreInfoSSN.click();
        waitFor(2);
        waitForVisibility(contactRecord.contactMoreInfoLinkRecord, 5);
        contactRecord.contactMoreInfoLinkRecord.click();
    }

    @And("I click on demographic tab and click on add for {string}")
    public void clickOnDemographicTabAndClickOnAddForDemographicMember(String type) {
        waitForVisibility(demographicContactInfo.demographicInfoTab, 20);
        demographicContactInfo.demographicInfoTab.click();
        waitFor(3);
        if (type.equals("AR")) {
            demographicMemberPage.addARButton.click();
        } else if (type.equals("PI")) {
            scrollUpUsingActions(2);
            waitFor(2);
            demographicMemberPage.addPIButton.click();
        } else if (type.equals("CM")) {
            demographicMemberPage.addCaseMemeberButton.click();
        }
    }

    //Locators are changed in commented lines
    @And("I add {string} for optin information check with {string} End Date")
    public Map<String, String> addDemMeber(String type, String endDate) {
        System.out.println("First Name for added member:" + firstNameMember.get());
        System.out.println("Last Name for added member:" + lastNameMember.get());
        System.out.println("Start Date for added member:" + startDateMember);
        System.out.println("End Date for added member:" + endDate);
        System.out.println("Access Type for added member:" + accessTypeMember);
        System.out.println("Gender for added member:" + genderMember);
        System.out.println("SSN for added member:" + ssnMember.get());

        if (type.equals("Authorized Representative")) {
            //              waitForVisibility(demographicMemberPage.arPage, 10);
            //
            waitFor(2);

            waitFor(1);
            selectDropDown(demographicMemberPage.arAccessType, accessTypeMember);
            waitFor(1);
            selectDropDown(demographicMemberPage.receiveCorrespondence, "Yes");
            waitFor(1);
            clearAndFillText(demographicMemberPage.consumerFrstName, firstNameMember.get());
            clearAndFillText(demographicMemberPage.consumerLastName, lastNameMember.get());
            waitFor(1);
            clearAndFillText(demographicMemberPage.effectiveStartDate, startDateMember);
            waitFor(1);
            clearAndFillText(demographicMemberPage.effectiveEndDate, endDate);
            clearAndFillText(demographicMemberPage.consumerSsn, ssnMember.get());
            waitFor(1);
            selectDropDown(crmAddPrimaryIndividualPage.authRefTypeValue, "Power of Attorney");
            waitFor(1);

            demographicMemberPage.consumerDoB.click();
            clearAndFillText(demographicMemberPage.consumerDoB, dobMember);
            waitFor(1);
            selectDropDown(demographicMemberPage.arGender, genderMember);
            scrollDownUsingActions(4);
            waitFor(1);
        } else if (type.equals("Primary Individual")) {
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);

            clearAndFillText(demographicMemberPage.firstName, firstNameMember.get());
            clearAndFillText(demographicMemberPage.lastName, lastNameMember.get());
            waitFor(1);
            selectDropDown(demographicMemberPage.receiveCorrespondence, "Yes");
            waitFor(1);
            clearAndFillText(demographicMemberPage.ssn, ssnMember.get());
            waitFor(1);

            clearAndFillText(demographicMemberPage.consumerDoB, dobMember);
            waitFor(1);

            clearAndFillText(demographicMemberPage.effectiveStartDate, startDateMember);
            waitFor(1);
            clearAndFillText(demographicMemberPage.effectiveEndDate, endDate);

            selectDropDown(demographicMemberPage.gender, genderMember);
            scrollDownUsingActions(4);
            waitFor(1);
        } else if (type.equals("Case Member")) {
            //waitForVisibility(demographicMemberPage.caseMemberPage, 10);

            //            scrollUpUsingActions(2);
            //selectDropDown(demographicMemberPage.arAccessType, accessTypeMember);
            waitFor(3);
            scrollUpRobot();
            waitFor(1);
            clearAndFillText(demographicMemberPage.consumerFrstName, firstNameMember.get());
            clearAndFillText(demographicMemberPage.consumerLastName, lastNameMember.get());
            waitFor(1);
            selectDropDown(demographicMemberPage.receiveCorrespondence, "Yes");
            waitFor(1);
            clearAndFillText(demographicMemberPage.effectiveStartDate, startDateMember);
            clearAndFillText(demographicMemberPage.effectiveEndDate, endDate);
            clearAndFillText(demographicMemberPage.consumerSsn, ssnMember.get());
            waitFor(1);

            clearAndFillText(demographicMemberPage.consumerDoB, dobMember);
            waitFor(1);
            selectDropDown(demographicMemberPage.relationShp, relationshipToPIMember);

            selectDropDown(demographicMemberPage.caseGender, genderMember);
            waitFor(1);
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

        Map<String, String> memberNames = new HashMap<String, String>();
        memberNames.put("firstName", firstNameMember.get());
        memberNames.put("lastName", lastNameMember.get());
        return memberNames;
    }

    //Locators are changed in commented lines
    @And("I update {string} for optin information check")
    public void updateAuthorizedRepForOptin(String type) {
        if (type.equals("Authorized Representative")) {
            contactRecord.consumerSearchSecondRecord.click();
            waitForVisibility(demographicMemberPage.authorizedRepPage, 10);
        } else if (type.equals("Primary Individual")) {
            contactRecord.consumerSearchFirstRecord.click();
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);
        } else if (type.equals("Case Member")) {
            contactRecord.consumerSearchSecondRecord.click();
            //waitForVisibility(demographicMemberPage.caseMemberPage, 10);
        }
        waitFor(1);
//        scrollDownUsingActions(4);
        waitFor(1);
        contactRecord.conEmail.click();
        contactRecord.conFax.click();
        contactRecord.conMail.click();
        contactRecord.conPhone.click();

        if (type.equals("Authorized Representative")) {
            //demographicMemberPage.arSaveMemberBtn.click();
            demographicMemberPage.saveAr.click();
            waitFor(2);
        } else if (type.equals("Primary Individual")) {
            demographicMemberPage.saveMemberBtn.click();
        } else if (type.equals("Case Member")) {
            //demographicMemberPage.saveMemberBtn.click();
            demographicMemberPage.saveAr.click();
        }

        waitFor(2);
        if (demographicMemberPage.continuePop.isDisplayed()) {
            demographicMemberPage.continuePop.click();
        }
//        waitForVisibility(demographicMemberPage.continueMemberBtn, 10);
//        demographicMemberPage.continueMemberBtn.click();

        waitForVisibility(demographicContactInfo.demographicInfoTabFromSearch, 10);
    }

    @When("I will take {string} for {string} for {string} optin information check")
    public void i_will_take_trace_Id_for_Events(String idType, String event, String eventType) {
        if (event.contains("CONSUMER_SAVE")) {
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(1));
        } else if (event.contains("CONSUMER_UPDATE")) {
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(2));
        }
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint.set(eventsCorrelationIdEndPoint.get() + correlationId.get());
    }

    //Recordtype changed to Consumer
    @Then("I will verify the {string} in the {string} for optin information check")
    public void verifyTheSelectionsInTheCASE_MEMBER_SAVE_EVENT(String type, String eventName) {
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

            eventId = (int) eventsData.get("eventId");

            Assert.assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");

            assertTrue(dataObjectData.get("consumerFirstName").getAsString().equalsIgnoreCase(firstNameMember.get()));
            assertTrue(dataObjectData.get("consumerLastName").getAsString().equalsIgnoreCase(lastNameMember.get()));
            Assert.assertEquals(dataObjectData.get("consumerSSN").getAsString(), ssnMember.get());
            Assert.assertEquals(dataObjectData.get("consumerType").getAsString(), "Consumer");

            JsonArray consumerConsentList = dataObjectData.get("consumerConsent").getAsJsonArray();

            if (type.contains("SAVE")) {
                Assert.assertEquals(payLoadData.get("action").getAsString(), "Create");
                for (JsonElement consumerConsent : consumerConsentList) {
                    if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Phone")) {
                        assertTrue(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                    } else if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Mail")) {
                        assertTrue(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                    } else if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Fax")) {
                        assertFalse(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                    } else if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Email")) {
                        assertFalse(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                    } else if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Text")) {
                        assertFalse(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                    }

                    assertEquals(consumerConsent.getAsJsonObject().get("howConsentProvided").getAsString(), "Phone");
                    assertEquals(consumerConsent.getAsJsonObject().get("createdBy").getAsString(), userId.get());
                    assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("createdOn").getAsString()));
                    if (!consumerConsent.getAsJsonObject().get("updatedBy").isJsonNull())
                        assertEquals(consumerConsent.getAsJsonObject().get("updatedBy").getAsString(), userId.get());
                    if (!consumerConsent.getAsJsonObject().get("updatedOn").isJsonNull())
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("updatedOn").getAsString()));
                    assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("effectiveStartDate").getAsString()));
                    if (!consumerConsent.getAsJsonObject().get("effectiveEndDate").isJsonNull())
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("effectiveEndDate").getAsString()));
                    assertTrue(consumerConsent.getAsJsonObject().get("dateOfConsent").isJsonNull());
                    assertTrue((consumerConsent.getAsJsonObject().get("consumerConsentId").getAsInt() + "").chars().allMatch(Character::isDigit));
                    assertTrue((consumerConsent.getAsJsonObject().get("consumerId").getAsInt() + "").chars().allMatch(Character::isDigit));
                }
            } else if (type.contains("UPDATE")) {
                Assert.assertEquals(payLoadData.get("action").getAsString(), "Update");
                for (JsonElement consumerConsent : consumerConsentList) {
                    if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Phone")) {
                        assertFalse(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("effectiveEndDate").getAsString()));
                        assertEquals(consumerConsent.getAsJsonObject().get("updatedBy").getAsString(), userId.get());
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("updatedOn").getAsString()));
                    } else if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Mail")) {
                        assertFalse(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("effectiveEndDate").getAsString()));
                        assertEquals(consumerConsent.getAsJsonObject().get("updatedBy").getAsString(), userId.get());
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("updatedOn").getAsString()));
                    } else if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Fax")) {
                        assertTrue(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                        assertEquals(consumerConsent.getAsJsonObject().get("updatedBy").getAsString(), userId.get());
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("updatedOn").getAsString()));
                    } else if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Email")) {
                        assertTrue(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                        assertEquals(consumerConsent.getAsJsonObject().get("updatedBy").getAsString(), userId.get());
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("updatedOn").getAsString()));
                    } else if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals("Text")) {
                        assertFalse(consumerConsent.getAsJsonObject().get("optIn").getAsBoolean());
                        if (!consumerConsent.getAsJsonObject().get("updatedOn").isJsonNull())
                            assertEquals(consumerConsent.getAsJsonObject().get("updatedBy").getAsString(), userId.get());
                        if (!consumerConsent.getAsJsonObject().get("updatedOn").isJsonNull())
                            assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("updatedOn").getAsString()));
                    }

                    assertEquals(consumerConsent.getAsJsonObject().get("howConsentProvided").getAsString(), "Phone");
                    assertEquals(consumerConsent.getAsJsonObject().get("createdBy").getAsString(), userId.get());
                    assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("createdOn").getAsString()));
                    assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("effectiveStartDate").getAsString()));
                    if (!consumerConsent.getAsJsonObject().get("effectiveEndDate").isJsonNull())
                        assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("effectiveEndDate").getAsString()));
                    assertTrue(consumerConsent.getAsJsonObject().get("dateOfConsent").isJsonNull());
                    assertTrue((consumerConsent.getAsJsonObject().get("consumerConsentId").getAsInt() + "").chars().allMatch(Character::isDigit));
                    assertTrue((consumerConsent.getAsJsonObject().get("consumerId").getAsInt() + "").chars().allMatch(Character::isDigit));
                }
            } else if (type.contains("DATES_CHECK")) {
                for (JsonElement consumerConsent : consumerConsentList) {
                    assertEquals(consumerConsent.getAsJsonObject().get("createdBy").getAsString(), userId.get());
                    assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("createdOn").getAsString()));
                    assertTrue(verifyDatetimeFormat(consumerConsent.getAsJsonObject().get("effectiveStartDate").getAsString()));
                }
            }
        }

//            if (eventsData.get("eventName").equals(eventName)) {
//                Assert.assertEquals(eventsData.get("status"), "success");
//                recordFound = true;
//                break;
//            }
//        }
//        assertTrue(recordFound);
    }

    @Then("I will verify subscriber received {string} event for {string} is created for optin information check")
    public void dpbiConsumerEventGenerationCheckForOptinInformation(String eventName, String subscriberName) {
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
        subscribersRecordEndPoint.set(subscribersRecordEndPoint.get() + subscriberId + "/" + eventId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        Assert.assertEquals(temp.getString("eventName"), eventName);
        System.out.println("Event name on DPBI:  " + eventName);

        Assert.assertEquals(temp.getInt("eventId"), eventId);
        System.out.println("Event id on DPBI:  " + eventId);

        Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId);
        System.out.println("Subscriber id on DPBI:   " + subscriberId);
        System.out.println("Event is subscribed to DPBI list");
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                        String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip, String consumerRole) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNo = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " + caseIdentificationNo);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

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
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", consumerRole);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDateOfBirth);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("identificationNumberType", "CHIP");

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private boolean verifyDatetimeFormat(String datetimeField) {
        if (datetimeField == null)
            return true;
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

    public void iVerifyNewlyCreatedConsumerDetailsSearchingOnUI(String firstName, String lastName) {
        waitFor(3);
        System.out.println("First Name of Case Loader:" + firstName);
        System.out.println("Last Name of Case Loader:" + lastName);
        clearAndFillText(contactRecord.firstName, firstName);
        clearAndFillText(contactRecord.lastName, lastName);
        contactRecord.searchButton.click();
        waitFor(5);
        assertTrue(contactRecord.consumerLastName.isDisplayed());
        assertTrue(contactRecord.consumerFirstName.isDisplayed());
    }

    @And("I search consumer with first name for demographic member")
    public void searchedForCustomerWithFirstNameForDemographicMember() {
        waitFor(3);
        contactRecord.initContact.click();
        System.out.println("First Name of Case Loader:" + firstNameCaseLoader.get());
        System.out.println("Last Name of Case Loader:" + lastNameCaseLoader.get());
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader.get());
        try {
            jsClick(contactRecord.searchButton);
        } catch (Exception e) {
            contactRecord.searchButton.click();
        }
        waitFor(5);
    }
}