package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_ViewConsumerProfileStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearch = new CRMManualCaseConsumerSearchPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);

    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMEditConsumerProfilePage editConsumerProfilePage = new CRMEditConsumerProfilePage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();

    final ThreadLocal<CRM_CreateConsumerProfileStepDef> createConsumerProfileStepDef = ThreadLocal.withInitial(CRM_CreateConsumerProfileStepDef::new);
    final ThreadLocal<CRM_ContactRecordUIStepDef> contactRecordUIStepDef = ThreadLocal.withInitial(CRM_ContactRecordUIStepDef::new);
    final ThreadLocal<CRM_CaseMemberStepDef> caseMemberStepDef = ThreadLocal.withInitial(CRM_CaseMemberStepDef::new);


    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> expectedCaseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> expectedConsumerName = ThreadLocal.withInitial(String::new);

    @When("I created consumer for viewing consumer profile")
    public void createConsumerForViewingConsumerProfile() {
        createNewConsumerForContactRecord();
    }

    @And("I navigate to Consumer profile summary information for viewing consumer profile")
    public void navigateToConsumerProfile() {
        contactRecord.consumerIdFirstRecord.click();
    }

    @Then("I will be able to view the Consumer Information for the Consumer Profile I have selected for viewing consumer profile")
    public void viewConsumerInformation() {
        String clickedId = contactRecord.consumerIdFirstRecord.getText();
        contactRecord.consumerIdFirstRecord.click();
        waitForVisibility(editConsumerProfilePage.consumerId, 5);
        String actualId = editConsumerProfilePage.consumerId.getText();
        assertEquals(clickedId, actualId);
    }

    @Then("I see {string} field on consumer profile page has {int} characters in total for consumer profile")
    public void fieldOnConsumerProfileHasCharacters(String field, int stringLength) {
        String actual = "";
        String[] consumerName = new String[2];
        switch (field) {
            case "consumerId":
                waitForVisibility(editConsumerProfilePage.consumerId, 5);
                actual = editConsumerProfilePage.consumerId.getText();
                break;
            case "consumerFirstName":
                waitForVisibility(editConsumerProfilePage.updatedFullName, 5);
                consumerName = editConsumerProfilePage.updatedFullName.getText().split(" ");
                actual = consumerName[0];
                break;
            case "consumerLastName":
                waitForVisibility(editConsumerProfilePage.updatedFullName, 5);
                consumerName = editConsumerProfilePage.updatedFullName.getText().split(" ");
                actual = consumerName[1];
                break;
            case "consumerAge":
                waitForVisibility(editConsumerProfilePage.consumerAge, 5);
                actual = editConsumerProfilePage.consumerAge.getText();
                assertTrue(actual.matches("[0-9]*"), "Age has to contain only digits.");
                break;
        }
        assertTrue(actual.length() <= stringLength);
    }

    @Then("I see gender for consumer profile")
    public void seeGender() {
        String gender = editConsumerProfilePage.consumerGender.getText();

        ArrayList genderValues = new ArrayList();
        genderValues.add("Male");
        genderValues.add("Female");
        genderValues.add("Neutral");
        genderValues.add("Other");
        genderValues.add("Unknown");


        assertTrue(genderValues.contains(gender));
    }

    @Then("I see Date of Birth in {string} format")
    public void seeDateOfBirth(String format) {
        String expectedFormat = format;
        String actualFormat = "";
        if (editConsumerProfilePage.consumerDOB.getText().endsWith("visibility_off")) {
            actualFormat = editConsumerProfilePage.consumerDOB.getText().substring(0, 10);
        } else {
            actualFormat = editConsumerProfilePage.consumerDOB.getText().substring(0, 10);
        }
        assertEquals(actualFormat, expectedFormat);
    }

    @Then("I see SSN {int} digits value for consumer profile")
    public void seeSSNDigits(int ssnLength) {
        String consumerSsn = editConsumerProfilePage.consumerSSN.getText().substring(0, 11);
        consumerSsn = consumerSsn.replaceAll("-", "");
        assertTrue(consumerSsn.length() == ssnLength, "SSN has to have " + ssnLength + " digits.");
    }

    @Then("I see preferred spoken and written languages for consumer profile")
    public void seePreferredLanguages() {
        String prefSpokenLang = editConsumerProfilePage.consumerSpokenLang.getText();
        assertEquals(prefSpokenLang, "English", "SpOken language on view isnt verified.");

        String prefWrittenLang = editConsumerProfilePage.consumerWrittenLang.getText();
        assertEquals(prefWrittenLang, "English", "Written language on view isnt verified.");
    }

    @Then("I see Consumer Start Date and End Date format for consumer profile")
    public void seeConsumerStartDateAndEndDate() {
        //Start Date
        waitFor(1);
        boolean startDateFormatCheck = false;
        String startDate = editConsumerProfilePage.consumerStartDate.getText();
        if (startDate != null) {
            try {
                Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
                if (date1 != null)
                    startDateFormatCheck = true;
            } catch (Exception e) {
                System.out.println("Date is not in the expected format");
            }
        }

        assertTrue(startDateFormatCheck);

        //End Date
        boolean endDateFormatCheck = false;
        String endDate = editConsumerProfilePage.consumerEndDate.getText();
        if (endDate != null) {
            try {
                Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
                if (date1 != null)
                    endDateFormatCheck = true;
            } catch (Exception e) {
                System.out.println("Date is not in the expected format");
            }
        } else {
            endDateFormatCheck = true;
        }

        assertTrue(endDateFormatCheck);
    }

    @Then("I see Correspondence Preferences for consumer profile")
    public void seeCorrespondencePreferences() {
        String correspondencePref = editConsumerProfilePage.consumerCorrespondencePref.getText();
        assertEquals(correspondencePref, "Paperless");
    }

    @Then("I see profile status for consumer profile")
    public void seeProfileStatus() {
        waitForVisibility(editConsumerProfilePage.consumerStatus, 5);
        String consumerStatus = editConsumerProfilePage.consumerStatus.getText();

        ArrayList consumerStatusValues = new ArrayList();
        consumerStatusValues.add("INACTIVE");
        consumerStatusValues.add("ACTIVE");
        consumerStatusValues.add("FUTURE");

        assertTrue(consumerStatusValues.contains(consumerStatus));
    }

    private void createNewConsumerForContactRecord() {

        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        clearAndFillText(contactRecord.lastName, (newConsumer.get().get("surname").toString()));
        consumerName.set(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());

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
        clearAndFillText(createConsumer.consumerDB, "01/20/1992");
        clearAndFillText(createConsumer.consumerSSN, getRandomNumber(9));
        selectRandomDropDownOption(createConsumer.consumerGender);
        selectDropDown(createConsumer.consumerCorrespondencePref, "Paperless");
        waitFor(1);
        //    createConsumer.correspondencePrefOut.click();
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        consumerId.set(contactRecord.lblCrmConsumerId.getText());
    }


    @When("I expand the record to navigate Case Consumer Record from manual view")
    public void i_expand_the_record_to_navigate_Case_Consumer_Record_from_manual_view() {
        waitFor(1);
        expectedConsumerName.set(manualCaseConsumerSearch.consumerFNs.get(0).getText() + " " + manualCaseConsumerSearch.consumerLNs.get(0).getText());
        expectedCaseId.set(manualCaseConsumerSearch.caseIDs.get(0).getText());
        manualCaseConsumerSearch.caseIDs.get(0).click();
    }

    @When("I expand the record to navigate Consumer Record from manual view")
    public void i_expand_the_record_to_navigate_Consumer_Record_from_manual_view() {
        waitFor(1);
        expectedConsumerName.set(manualCaseConsumerSearch.consumerFNs.get(0).getText() + " " + manualCaseConsumerSearch.consumerLNs.get(0).getText());
        manualCaseConsumerSearch.consumerIDs.get(0).click();
    }

    @Then("I verify value of First Name as {string}, Last Name as {string} and Date Of Birth as {string} in Search Results")
    public void i_verify_value_of_First_Name_as_Last_Name_as_and_Date_Of_Birth_as_in_Search_Results(String fname, String lname, String dob) {
        assertEquals(manualCaseConsumerSearch.consumerFNs.get(0).getText(), fname);
        assertEquals(manualCaseConsumerSearch.consumerLNs.get(0).getText(), lname);
        waitFor(2);
        activeContact.unmaskDOBSearchResult.click();
        assertEquals(manualCaseConsumerSearch.searchResultDOBVisible.getText(), dob);
    }

    @Then("I verify visual indication of viewing a Case Information")
    public void i_verify_visual_indication_of_viewing_a_Case_Information() {
        waitFor(1);
        assertTrue(manualCaseConsumerSearch.caseIcon.isDisplayed(), "Case Icon is not displayed");
    }

    @Then("I hover over the Case Icon and see its text and Title property has {string} value")
    public void i_hover_over_the_Case_Icon_and_see_its_text(String tileAndTextName) {
        waitFor(1);
        hover(manualCaseConsumerSearch.caseIcon);
        assertEquals(manualCaseConsumerSearch.caseIcon.getAttribute("title"), (tileAndTextName), "Title/Hover Over Text is not displayed");
    }

    @Then("I verify Case Id next to Case Icon")
    public void Case_Id_next_to_Case_Icon() {
        waitFor(1);
        waitFor(1);
        if (expectedCaseId.get().equals("")) {
            expectedCaseId.set(contactRecordUIStepDef.get().caseId.get());
        }
        assertEquals(expectedCaseId, manualCaseConsumerSearch.caseIdNextToCaseIcon.getText(),
                "Viewed CaseId is not next to Case Icon");

    }


    @Then("I see Consumer Name is next to Case Icon")
    public void Consumer_Name_next_to_Case_Icon() {
        waitFor(1);
        assertEquals(manualCaseConsumerSearch.nameNextToCaseIcon.getText(), expectedConsumerName.get(),
                "Viewed Consumer Name is not next to Case Icon");

    }


}

