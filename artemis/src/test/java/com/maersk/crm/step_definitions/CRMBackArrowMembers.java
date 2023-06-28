package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APITMEventController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

public class CRMBackArrowMembers extends CRMUtilities implements ApiBase {
    CRMCreateConsumerProfilePage createConsumerProfilePage = new CRMCreateConsumerProfilePage();
    CRMEditConsumerProfilePage editconsumer = new CRMEditConsumerProfilePage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    CRMDashboardPage dashboardPage = new CRMDashboardPage();
    CRMAddPrimaryIndividualPage crmAddPrimaryIndividualPage = new CRMAddPrimaryIndividualPage();
    CRMAddCaseMemberPage caseMemberPage = new CRMAddCaseMemberPage();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    CRMAddContactInfoPage ddContactInfoPage = new CRMAddContactInfoPage();

    private final ThreadLocal<String> caseLoaderBaseURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiCaseLoaderURI"));
    private final ThreadLocal<String> createCaseConsumer = ThreadLocal.withInitial(()->"app/crm/caseloader");

    private final ThreadLocal<APITMEventController> apitmEventController = ThreadLocal.withInitial(APITMEventController::new);

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> firstNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerDob = ThreadLocal.withInitial(String::new);

    @When("I create a case for consumer of type {string} using Case Loader API for members")
    public void createACaseUsingCaseLoaderAPIForDemographicMember(String consumerRole) {
        consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumer.get());
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

    @And("I search consumer with first name and last name for members")
    public void searchedForCustomerWithFirstNameAndLastNameForMember() {
        waitFor(3);
        contactRecord.initContact.click();
        System.out.println("First Name of Case Loader:" + firstNameCaseLoader.get());
        System.out.println("Last Name of Case Loader:" + lastNameCaseLoader.get());
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader.get());
        clearAndFillText(contactRecord.lastName, lastNameCaseLoader.get());
        contactRecord.searchButton.click();
        waitFor(5);
    }

    @And("I search consumer from consumer search with first name and last name for members")
    public void searchedFromConsumerSearchWithFirstNameAndLastNameForDemographicMember() {
        waitForVisibility(contactRecord.firstName, 10);
        System.out.println("First Name of Case Loader:" + firstNameCaseLoader.get());
        System.out.println("Last Name of Case Loader:" + lastNameCaseLoader.get());
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader.get());
        clearAndFillText(contactRecord.lastName, lastNameCaseLoader.get());
        contactRecord.searchButton.click();
        waitFor(5);
        waitForVisibility(contactRecord.consumerSearchFirstRecord, 10);
        contactRecord.consumerSearchFirstRecord.click();
    }

    @And("I click on demographic tab and edit the {string} member")
    public void editMember(String type) {
        waitForVisibility(demographicContactInfo.demographicInfoTab, 20);
        demographicContactInfo.demographicInfoTab.click();
        waitForVisibility(demographicMemberPage.addPIButton, 10);
        try {
            contactRecord.consumerSearchFirstRecordPI.click();
        } catch (Exception e) {
            contactRecord.consumerSearchFirstRecord.click();
        }
        if (type.equals("Authorized Representative"))
            waitForVisibility(demographicMemberPage.authorizedRepPage, 10);
        else if (type.equals("Primary Individual"))
            waitForVisibility(demographicMemberPage.primaryIndividualPage, 10);
        else if (type.equals("Case Member"))
            waitForVisibility(demographicMemberPage.caseMemberPage, 10);
    }

    @Then("I verify that I see back arrow beneath the consumer profile icon for {string}")
    public void verifyBackArrow(String type) {
        waitFor(2);
        assertTrue(demographicMemberPage.memberBackBtn.isDisplayed());
        demographicMemberPage.memberBackBtn.click();
        waitForVisibility(demographicContactInfo.demographicInfoTab, 10);
    }

    @Then("I verify that I see back arrow beneath the consumer profile icon from search")
    public void verifyBackArrowFromSearch() {
        waitFor(2);
        assertTrue(demographicMemberPage.memberBackBtn.isDisplayed());
        waitForVisibility(demographicContactInfo.demographicInfoTab, 10);
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                        String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip, String consumerRole) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNo = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " + caseIdentificationNo);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();
        String futureDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(6).toString();

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
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", consumerRole);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", futureDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @Then("Verify the consumer has been systematically inactivated {string}")
    public void verify_the_consumer_has_been_systematically_inactivated(String consumerType) {
        consumerType = consumerType.toLowerCase();
        switch (consumerType) {
            case "primary individual":
                System.out.println("Actual PI status is  === " + crmAddPrimaryIndividualPage.pIstatus.getText());
                assertTrue(crmAddPrimaryIndividualPage.pIstatus.getText().equals("INACTIVE"), "Inactive status isnt verified");
                break;
            case "member":
                System.out.println("Actual status member is  === " + crmAddPrimaryIndividualPage.pIstatus.getText());
                assertTrue(caseMemberPage.caseMembersStatus.getText().equals("INACTIVE"), "Inactive status isnt verified");
                break;
        }
    }

    @Given("I populate the Date of Death {string} and EndDate {string}")
    public void i_populate_the_Date_of_Death_and_EndDate(String dateOfDeth, String endDate) {
        String dayOfTheActualDeth = "";
        String dayOfEndDay = "";

        switch (dateOfDeth) {
            case "curentDate":
                dayOfTheActualDeth = getCurrentDateWithFormat();
                break;
            case "past1":
                dayOfTheActualDeth = getPriorDate(1);
                break;
            case "past2":
                dayOfTheActualDeth = getPriorDate(2);
                break;
            case "past5":
                dayOfTheActualDeth = getPriorDate(5);
                break;
        }
        switch (endDate) {
            case "curentDate":
                dayOfEndDay = getCurrentDateWithFormat();
                break;
            case "past1":
                dayOfEndDay = getPriorDate(1);
                break;
            case "past2":
                dayOfEndDay = getPriorDate(2);
                break;
            case "past5":
                dayOfEndDay = getPriorDate(5);
                break;
            case "future1":
                dayOfEndDay = getPriorDate(1);
                break;
            case "future5":
                dayOfEndDay = getPriorDate(5);
                break;
        }
        System.out.println("date format ==== " + dayOfTheActualDeth);
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getPriorDate(30));
        clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, dayOfEndDay);
        clearAndFillText(crmAddPrimaryIndividualPage.piDOD, dayOfTheActualDeth);
        crmAddPrimaryIndividualPage.pregnancyIndCheckBox.click();
        waitFor(2);
        clearAndFillText(crmAddPrimaryIndividualPage.dateOfDeathNotifiedDate, dayOfTheActualDeth);
        clearAndFillText(crmAddPrimaryIndividualPage.dateOfDeathNotifiedBy, "unknown");
        click(crmAddPrimaryIndividualPage.saveButton);
        try {
            waitForVisibility(ddContactInfoPage.popupAlertContinueButton, 10);
            ddContactInfoPage.popupAlertContinueButton.click();
        } catch (Exception e) {
        }
    }

    @Then("Verify the End Date is systematically set to match the DoD")
    public void verify_the_End_Date_is_systematically_set_to_match_the_DoD_value() {
        waitFor(2);
        try {
            contactRecord.consumerSearchFirstRecordPI.click();
        } catch (Exception e) {
            contactRecord.consumerSearchFirstRecord.click();
        }
        System.out.println("Actual date of Deth = " + crmAddPrimaryIndividualPage.piDOD.getAttribute("value"));
        System.out.println("Expected date of Deth = " + crmAddPrimaryIndividualPage.piEndDate.getAttribute("value"));
        assertEquals(crmAddPrimaryIndividualPage.piEndDate.getCssValue("value"), crmAddPrimaryIndividualPage.piDOD.getCssValue("value"));
    }

    @Then("Verify the End Date is stay same")
    public void verify_the_End_Date_is_stay_same() {
        waitFor(2);
        contactRecord.consumerSearchFirstRecordPI.click();
        waitForPageToLoad(10);
        System.out.println("Actual date of Deth = " + crmAddPrimaryIndividualPage.piDOD.getAttribute("value"));
        System.out.println("Expected date of End Date = " + crmAddPrimaryIndividualPage.piEndDate.getAttribute("value"));
        assertTrue(crmAddPrimaryIndividualPage.piDOD.getAttribute("value") != "");
        assertTrue(crmAddPrimaryIndividualPage.piEndDate.getAttribute("value") != "");
        assertFalse(crmAddPrimaryIndividualPage.piEndDate.getAttribute("value").equals(crmAddPrimaryIndividualPage.piDOD.getCssValue("value")), " date is equals ");
    }

    @When("I click on add button Case Member")
    public void i_click_on_added_Case_Member() {
        assertTrue(isElementDisplayed(demographicMemberPage.addCaseMemberBtn));
        click(demographicMemberPage.addCaseMemberBtn);
    }

    @Given("I create case member with end date {string} and DoD {string}")
    public void i_populate_the_Date_of_Death_field_for_caseMember(String endDate, String date) {
        waitFor(3);
        selectRandomDropDownOption(demographicMemberPage.relationshipToPIDropDown);
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, "01012000");
        if (!endDate.equals("")) {
            switch (date) {
                case "past":
                    clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getPriorDate(1));
                    break;
                case "present":
                    clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getCurrentDate());
                    break;
                case "future":
                    clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getFutureDate(1));
                    break;
            }
        }
        selectDropDown(demographicMemberPage.receiveCorrespondence, "No");
        clearAndFillText(demographicMemberPage.firstName, getRandomString(5));
        clearAndFillText(demographicMemberPage.lastName, getRandomString(5));
        clearAndFillText(demographicMemberPage.arDob, "01012000");
        clearAndFillText(demographicMemberPage.ssn, getRandomNumber(9));
        selectDropDown(demographicMemberPage.gender, "Male");
        switch (date) {
            case "past":
                clearAndFillText(crmAddPrimaryIndividualPage.piDOD, getPriorDate(1));
                click(demographicMemberPage.saveMemberBtn);
                waitFor(2);
                clearAndFillText(crmAddPrimaryIndividualPage.dateOfDeathNotifiedDate, getPriorDate(1));
                break;
            case "present":
                clearAndFillText(crmAddPrimaryIndividualPage.piDOD, getCurrentDate());
                click(demographicMemberPage.saveMemberBtn);
                waitFor(2);
                clearAndFillText(crmAddPrimaryIndividualPage.dateOfDeathNotifiedDate, getCurrentDate());
                break;
            case "future":
                clearAndFillText(crmAddPrimaryIndividualPage.piDOD, getFutureDate(1));
                click(demographicMemberPage.saveMemberBtn);
                waitFor(2);
                clearAndFillText(crmAddPrimaryIndividualPage.dateOfDeathNotifiedDate, getFutureDate(1));
                break;
        }
        clearAndFillText(crmAddPrimaryIndividualPage.dateOfDeathNotifiedBy, "today");
        jsClick(crmAddPrimaryIndividualPage.saveButton);
        try {
            waitForVisibility(ddContactInfoPage.popupAlertContinueButton, 3);
            click(ddContactInfoPage.popupAlertContinueButton);
        } catch (Exception e) {
        }
    }

    @Then("Verify the End Date is systematically set to match the DoD for case member")
    public void verify_the_End_Date_is_systematically_set_to_match_the_DoD_for_caseMember() {
        waitForVisibility(caseMemberPage.caseMembersStatus, 2);
        try {
            jsClick(caseMemberPage.caseMembersStatus);
        } catch (Exception e) {
            caseMemberPage.caseMembersStatus.click();
        }
        System.out.println("Actual date of Deth = " + crmAddPrimaryIndividualPage.piDOD.getAttribute("value"));
        System.out.println("Expected date of Deth = " + crmAddPrimaryIndividualPage.piEndDate.getAttribute("value"));
        assertEquals(crmAddPrimaryIndividualPage.piEndDate.getCssValue("value"), crmAddPrimaryIndividualPage.piDOD.getCssValue("value"));
    }

    @Then("I NOT see the Communication Opt-In Information component on {string}")
    public void i_NOT_see_the_Communication_Opt_In_Information_component_on(String page) {
        page = page.toLowerCase();

        switch (page) {
            case "consumer profile page":
                waitForVisibility(editconsumer.consumerEditButton, 10);
                assertFalse(isElementDisplayed(editconsumer.communicationOptInInfoTitle), "Communication Opt-In Information component is displayed");
                break;
            case "add PI page":
                waitForVisibility(crmAddPrimaryIndividualPage.consumerRoleField, 10);
                assertFalse(isElementDisplayed(editconsumer.communicationOptInInfoTitle), "Communication Opt-In Information component is displayed");
                break;
            case "add CM page":
                waitForVisibility(caseMemberPage.caseNavigationHeader, 10);
                assertFalse(isElementDisplayed(editconsumer.communicationOptInInfoTitle), "Communication Opt-In Information component is displayed");
                break;
            case "Create Consumer page":
                waitForVisibility(createConsumerProfilePage.consumerDetalsLableHeader, 10);
                assertFalse(isElementDisplayed(editconsumer.communicationOptInInfoTitle), "Communication Opt-In Information component is displayed");
                break;


        }
    }
}