package com.maersk.crm.step_definitions;

import com.github.javafaker.Faker;
import com.maersk.crm.api_step_definitions.APICreateConsumerContactController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_ManualCaseConsumerSearchStepDef extends CRMUtilities implements ApiBase {


    CRMManualCaseConsumerSearchPage manualCaseConsumerSearch = new CRMManualCaseConsumerSearchPage();
    CRMDashboardPage crmDashboardPage = new CRMDashboardPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();
    CRMContactRecordDashboardPage contactRecDashPage = new CRMContactRecordDashboardPage();
    ViewConsumerProfilePage viewProfile = new ViewConsumerProfilePage();
    ViewDcCasePage viewCase = new ViewDcCasePage();

    private final ThreadLocal<String> address1 = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> address2 = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressCity = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> state = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> state2 = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> zipCode = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> email = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> phoneNumber = ThreadLocal.withInitial(String::new);

    @When("I navigate to Manual Consumer search page")
    public void i_navigate_to_Manual_Consumer_search_page() {
//        hover(crmDashboardPage.case_ConsumerSearchTab);
        waitFor(2);
        jsClick(crmDashboardPage.case_ConsumerSearchTab);
    }

//    @When("I expend the Address record on View Consumer Profile page to verify all fields are present")
//    public void i_expend_the_Address_record_on_View_Consumer_Profile_pageto_verify_all_fields_are_present() {
//        demographicContactInfo.addressesStatuses.get(0).click();
//        waitFor(1);
//        assertTrue(addContactInfo.addressLineOne.isDisplayed());
//        assertTrue(addContactInfo.addressLineTwo.isDisplayed());
//        assertTrue(addContactInfo.city.isDisplayed());
//        assertTrue(addContactInfo.county.isDisplayed());
//        assertTrue(addContactInfo.state.isDisplayed());
//        assertTrue(addContactInfo.zip.isDisplayed());
//        assertTrue(addContactInfo.addressType.isDisplayed());
//        assertTrue(addContactInfo.startDate.isDisplayed());
//        assertTrue(addContactInfo.endDate.isDisplayed());
//        assertTrue(addContactInfo.addressStatus.isDisplayed());
//
//    }


    @Then("I see all search parameters remain per search by {string} {string}")
    public void iSeeAllSearchParametersRemainPerSearchBy(String fn, String ln) throws Throwable {
        waitFor(1);
        assertTrue(manualCaseConsumerSearch.firstName.getAttribute("value").equals(fn),
                "Expected search parameter is not displayed");
        assertTrue(manualCaseConsumerSearch.lastName.getAttribute("value").equals(ln),
                "Expected search parameter is not displayed");
    }

    @Then("I verify search parameters on Manual Consumer search UI are cleared")
    public void iVerifySearchParametersOnManualConsumerSearchUIAreCleared() throws Throwable {
        assertTrue(manualCaseConsumerSearch.firstName.getAttribute("value").equals(""),
                "Expected search parameter should be cleared");
        assertTrue(manualCaseConsumerSearch.lastName.getAttribute("value").equals(""),
                "Expected search parameter should be cleared");
    }

    @And("I click on cancel button on manual case Consumer search UI")
    public void iClickOnCancelButtonOnManualCaseConsumerSearchUI() throws Throwable {
        waitFor(1);
        manualCaseConsumerSearch.lastName.click();
        manualCaseConsumerSearch.cancelButton.click();
    }

    @And("I verify search result component Consumer Profile Id is {string}")
    public void iVerifySearchResultComponentConsumerProfileIdIs(String expected) throws Throwable {
        String actual = activeContactPage.searchResultConsumerId.getText();
        assertTrue(actual.equals(expected));
    }

    @And("I navigate to consumer profile page by clicking on caseId")
    public void iNavigateToConsumerProfilePageByClickingOnCaseId() {
        manualCaseConsumerSearch.dcCaseIDs.get(0).click();
    }

    @When("I navigate to consumer profile page by clicking on stateId")
    public void iNavigateToConsumerProfilePageByClickingOnStateId() {
        manualCaseConsumerSearch.dcStateID.click();
    }

    @And("I navigate to DC consumer profile page by clicking on name")
    public void iNavigateToDCConsumerProfilePageByClickingOnConsumerName() {
        manualCaseConsumerSearch.dcConsumerName.get(0).click();
    }

    @Then("I validate all configured tabs from View profile screen")
    public void iValidateAllConfiguredTabsFromViewProfileScreen() {
        assertTrue(contactRecDashPage.profileInfoTab.isDisplayed(), "Profile Ingo Tab is not displayed");
        assertTrue(contactRecDashPage.caseContactDetailsTab.isDisplayed(), "Case & Contact Details Tab is not displayed");
        assertTrue(contactRecDashPage.taskServiceRequestTab.isDisplayed(), "Task & Service Request Tab is not displayed");
        assertTrue(contactRecDashPage.historyTab.isDisplayed(), "History Tab is not displayed");
    }


    @Then("I validate all the fields on View profile screen")
    public void iValidateAllTheFieldsOnViewProfileScreen() {
        assertTrue(viewProfile.ccProfIdLabel.isDisplayed(), "ID field is not displayed");
        assertTrue(viewProfile.cProfNameLabel.isDisplayed(), "Name field is not displayed");
        assertTrue(viewProfile.ccProfDobLabel.isDisplayed(), "DOB field is not displayed");
        assertTrue(viewProfile.ccProfSsnLabel.isDisplayed(), "SSN field is not displayed");
        assertTrue(viewProfile.ccProfAgeLabel.isDisplayed(), "Age field is not displayed");
        assertTrue(viewProfile.ccProfileIdLabel.isDisplayed(), "Profile ID field is not displayed");
        assertTrue(viewProfile.ccProfDodLabel.isDisplayed(), "DOD field is not displayed");
        assertTrue(viewProfile.ccProfGenderLabel.isDisplayed(), "Gender field is not displayed");
        assertTrue(viewProfile.ccProfileAddressLabel.isDisplayed(), "Address field is not displayed");
        assertTrue(viewProfile.ccProfilePhoneLabel.isDisplayed(), "Phone field is not displayed");
        assertTrue(viewProfile.ccProfileEmailLabel.isDisplayed(), "Email field is not displayed");
        assertTrue(viewProfile.ccProfRaceCodeLabel.isDisplayed(), "Race Code field is not displayed");

    }

    @Then("I see unmasking DOB and SSN buttons is on View Consumer Profile Page")

    public void iSeeUnmaskingDobButton() {
        assertTrue(viewProfile.ccProfDobUnmask.isDisplayed(), "Unmasking DOB button on Active Contact Record Page is not displayed");
        assertTrue(viewProfile.ccProfSsnLabel.isDisplayed(), "Unmasking SSN button on Active Contact Record Page is not displayed");
        waitFor(2);
    }

    @When("I and click on unmasking SSN and DOB buttons on View Consumer Profile Page")
    public void i_and_click_on_unmasking_SSN_DOB() {
        viewProfile.ccProfDobUnmask.click();
        viewProfile.ccProfSsnLabel.click();

    }

    @Then("I see DOB in expected format unmasked View Consumer Profile Page")
    public void i_see_DOB_in_expected_format_unmasked_on_View_Consumer_Profile_Page() {
        String actualDOB = "";
        if (viewProfile.ccProfDobUnmask.getText().endsWith("visibility_off")) {
            actualDOB = viewProfile.getConsumerProfileFieldValue("DATE OF BIRTH");
            actualDOB = actualDOB.substring(0, 10);
        } else {
            actualDOB = viewProfile.getConsumerProfileFieldValue("DATE OF BIRTH");
        }
        assertTrue(isMMddYYYYformat(actualDOB), "DOB on View Profile Page is not in MM/DD/YYYY format.");
    }

    @And("I will see the Consumer Name next to the consumer icon")
    public void iWillSeeTheConsumerNameNextToTheConsumerIcon() {

        assertTrue(viewProfile.viewRecordConsumerNameHeader.isDisplayed(), "Consumer Name is not displayed");
        assertTrue(viewProfile.getConsumerNameFromHeader(viewProfile.viewRecordConsumerNameHeader)
                .contains(viewProfile.getConsumerProfileFieldValue("CONSUMER NAME")), "Consumer Name does not match");
    }

    @When("I click on add address button on consumer profile page")
    public void I_click_on_add_address_button_on_consumer_profile_page() {
        waitFor(5);
        assertEquals(Driver.getDriver().getTitle(), "Consumer summary - Profile Info", "Wrong page not a Consumer summary page");
        jsClick(viewProfile.addAddressButton);
    }

    @When("I input all required fields for {string} address")
    public void I_input_all_required_fields(String addressType, Map<String, String> data) {
        assertTrue(isElementDisplayed(viewProfile.mailingAddressLine1), "No at the add address page OR Locator is not displayed");
        Faker random = new Faker();
        synchronized (address1) {
            address1.set(random.address().streetAddress());
        }
        synchronized (address2) {
            address2.set(random.address().secondaryAddress());
        }
        synchronized (addressCity) {
            addressCity.set(random.address().city());
        }
        synchronized (zipCode) {
            zipCode.set(random.address().zipCode().substring(0, 5));
        }
        if (addressType.contains("Mailing")) {
            clearAndFillText(viewProfile.mailingAddressLine1, address1.get());
            clearAndFillText(viewProfile.mailingAddressLine2, address2.get());
            clearAndFillText(viewProfile.mailingCity, addressCity.get());
            clearAndFillText(viewProfile.mailingZipCode, zipCode.get());
            selectRandomDropDownOption(viewProfile.mailingStateDropdown);
            waitFor(1);
            synchronized (state) {
                state.set(viewProfile.mailingStateDropdown.getText());
            }
        }
        if (addressType.contains("Physical")) {
            clearAndFillText(viewProfile.physicalAddressLine1, address1.get());
            clearAndFillText(viewProfile.physicalAddressLine2, address2.get());
            clearAndFillText(viewProfile.physicalCity, addressCity.get());
            clearAndFillText(viewProfile.physicalZipCode, zipCode.get());
            selectRandomDropDownOption(viewProfile.physicalStateDropdown);
            waitFor(1);
            synchronized (state2) {
                state2.set(viewProfile.physicalStateDropdown.getText());
            }
        }
    }

    @Then("I verify {string} consumer reported address record got saved and display on page")
    public void I_verify_consumer_reported_address_record_got_saved_and_display_on_page(String addressType) {
        //verify I am on consumer profile page
        assertTrue(isElementDisplayed(viewProfile.stateReportedPanel), "Wrong page");
        if (addressType.contains("Mailing")) {
            String actualFullAddressOnProfilePage = viewProfile.mailingFullAddressLineOnProfilePage.getText();
            assertTrue(actualFullAddressOnProfilePage.contains(address1.get()), "Mailing address1 not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(address2.get()), "Mailing address2 not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(addressCity.get()), "Mailing addressCity not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(state.get()), "Mailing state not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(zipCode.get()), "Mailing zipCode not displayed OR incorrect");
        }
        if (addressType.contains("Physical")) {
            String actualFullAddressOnProfilePage = viewProfile.physicalFullAddressLineOnProfilePage.getText();
            assertTrue(actualFullAddressOnProfilePage.contains(address1.get()), "Physical address1 not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(address2.get()), "Physical address2 not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(addressCity.get()), "Physical addressCity not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(state2.get()), "Physical state not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(zipCode.get()), "Physical zipCode not displayed OR incorrect");
        }
    }

    @Then("I click on edit address button icon")
    public void I_click_on_edit_address_button_icon() {
        jsClick(viewProfile.editAddressButton);
        //verify I am on edit address page
        assertTrue(isElementDisplayed(viewProfile.addressDetailsLableOnEditPage), "Wrong page");
    }

    @Then("I verify all address fields has a value on edit address page")
    public void I_verify_all_address_fields_has_a_value_on_edit_address_page() {
        //fetching fields values for validation
        //mailing
        assertNotNull(viewProfile.mailingAddressLine1.getText(), "mailing address line1 is empty");
        assertNotNull(viewProfile.mailingAddressLine2.getText(), "mailing address line2 is empty");
        assertNotNull(viewProfile.mailingCity.getText(), "mailing City is empty");
        assertNotNull(viewProfile.mailingStateDropdown.getText(), "mailing State is empty");
        assertNotNull(viewProfile.mailingZipCode.getText(), "mailing zipCode is empty");
        //physical
        assertNotNull(viewProfile.physicalAddressLine1.getText(), "physical address line1 is empty");
        assertNotNull(viewProfile.physicalAddressLine2.getText(), "physical address line2 is empty");
        assertNotNull(viewProfile.physicalCity.getText(), "physical City is empty");
        assertNotNull(viewProfile.physicalStateDropdown.getText(), "physical State is empty");
        assertNotNull(viewProfile.physicalZipCode.getText(), "physical zipCode is empty");
    }

    @Then("Verify All fields, buttons and labels is display on edit consumer reported screen")
    public void Verify_All_fields_is_display_on_edit_consumer_reported_screen() {
        //mailing address field validation
        assertTrue(isElementDisplayed(viewProfile.mailingAddressLine1), "mailingAddressLine1 Field is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.mailingAddressLine2), "mailingAddressLine2 Field is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.mailingCity), "mailingCity Field is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.mailingStateDropdown), "mailingState Dropdown is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.mailingZipCode), "mailingZipCode Field is not displayed or wrong locator");
        //physical address field validation
        assertTrue(isElementDisplayed(viewProfile.physicalAddressLine1), "physicalAddressLine1 Field is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.physicalAddressLine2), "physicalAddressLine2 Field is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.physicalCity), "physicalgCity Field is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.physicalStateDropdown), "physicalState Dropdown is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.physicalZipCode), "physicalZipCode Field is not displayed or wrong locator");
        //button address validation
        assertTrue(isElementDisplayed(viewProfile.saveButtonOnAddressPage), "saveButtonOnAddressPage is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.cancelButtonOnAddressPage), "cancelButtonOnAddressPage is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.deleteMailingAddressButtonOnAddressPage), "delete MailingAddress Button is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.deletePhysicalAddressButtonOnAddressPage), "delete PhysicalAddress Button is not displayed or wrong locator");
        //labels validation
        assertTrue(isElementDisplayed(viewProfile.addressDetailsLableOnEditPage), "ADDRESS DETAILS Lable is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.mailingAddressLabel), "MAILING ADDRESS Lable is not displayed or wrong locator");
        assertTrue(isElementDisplayed(viewProfile.physicalAddressLabel), "PHYSICAL ADDRESS Lable is not displayed or wrong locator");
    }

    @Then("I verify message {string} is display")
    public void verify_message_after_saveButtonClick(String message) {
        waitForVisibility(viewProfile.consumerSuccessfullyUpdatedMessage, 5);
        assertTrue(isElementDisplayed(viewProfile.consumerSuccessfullyUpdatedMessage), "Message no displayed");
    }

    @And("I click on delete {string} address button")
    public void click_on_delete_address_button(String addressType) {
        if (addressType.contains("Mailing")) {
            waitFor(2);
            jsClick(viewProfile.deleteMailingAddressButtonOnAddressPage);
        }
        if (addressType.contains("Physical")) {
            waitFor(2);
            jsClick(viewProfile.deletePhysicalAddressButtonOnAddressPage);
        }
    }

    @Then("I will verify {string} address record is deleted")
    public void verifyAddressDeleted(String addressType) {
    }

    @When("I click on add phone button on consumer profile page")
    public void i_click_on_add_phone_button_on_consumer_profile_page() {
        waitFor(1);
        assertEquals(Driver.getDriver().getTitle(), "Consumer summary - Profile Info", "Wrong page not a Consumer summary page");
        jsClick(viewProfile.addPhoneButton);
        waitFor(4);
    }

    @Then("I validate all the fields on Phone Number Details screen")
    public void i_validate_all_the_fields_on_Phone_Number_Details_screen() {
        assertTrue(viewProfile.orderLabel.isDisplayed(), "Order field is not displayed");
        assertTrue(viewProfile.typeLabel.isDisplayed(), "Type field is not displayed");
        assertTrue(viewProfile.phoneNumberLabel.isDisplayed(), "Phone number field is not displayed");
        assertTrue(viewProfile.clearButton.get(1).isDisplayed(), "Clear button is not displayed");

    }

    @When("I am able to add a consumer-reported phone in the editable fields")
    public void i_am_able_to_add_a_consumer_reported_phone_in_the_editable_fields() {
        selectDropDown(viewProfile.cellPhoneType, "1");
        viewProfile.cellPhoneNumField.sendKeys("4126549765");
        selectDropDown(viewProfile.homePhoneType, "2");
        viewProfile.homePhoneNumField.sendKeys("4526549765");
        selectDropDown(viewProfile.workPhoneType, "3");
        viewProfile.workPhoneNumField.sendKeys("4526009765");
    }

    @When("I click on Save Button")
    public void i_click_on_Save_Button() {
        viewProfile.phoneSaveButton.click();
        waitFor(2);
    }

    @Then("I verify phone numbers are displayed in Consumer-Reported Communication Panel")
    public void i_verify_phone_numbers_are_displayed_in_Consumer_Reported_Communication_Panel() {
        assertEquals(viewProfile.cellPhoneNumValue.getText(), "(412) 654-9765");
        assertEquals(viewProfile.homePhoneNumValue.getText(), "(452) 654-9765");
        assertEquals(viewProfile.workPhoneNumValue.getText(), "(452) 600-9765");
    }

    @When("I click on Edit button on phone number field")
    public void i_click_on_Edit_button_on_phone_number_field() {
        waitFor(2);
        viewProfile.editButton.get(0).click();
    }

    @Then("I verify updated phone value after editing existing cell phone number")
    public void i_verify_updated_phone_value_after_editing_existing_cell_phone_number() {
        waitFor(3);
        viewProfile.cellPhoneNumField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        viewProfile.cellPhoneNumField.sendKeys(Keys.DELETE);
        viewProfile.cellPhoneNumField.sendKeys("5385380654");
        waitFor(2);
        viewProfile.phoneSaveButton.click();
        waitFor(2);
        Assert.assertEquals(viewProfile.cellPhoneNumValue.getText(), "(538) 538-0654");
    }

    @Then("I verify cell phone number is not displayed after clearing existing cell phone value")
    public void i_verify_cell_phone_number_is_not_displayed_after_clearing_existing_cell_phone_value() {
        waitFor(2);
        viewProfile.clearButton.get(1).click();
        viewProfile.phoneSaveButton.click();
        waitFor(2);
        Assert.assertEquals(viewProfile.cellPhoneNumValue.getText(), "(452) 654-9765");
    }

    @Then("I will remain in Phone Number Details Page")
    public void i_will_remain_in_Phone_Number_Details_Page() {
        assertTrue(viewProfile.phoneNumDetailPage.isDisplayed(), "Phone Number Details Page is not displayed");
    }

    @Then("I verify inline error message")
    public void I_verify_inline_error_message(Map<String, String> data) {
        for (String each : data.keySet()) {
            WebElement el = Driver.getDriver().findElement(By.xpath("//p[text()='" + data.get(each) + "']"));
            assertTrue(isElementDisplayed(el), "inline message " + data.get(each) + " is not displayed");
        }
    }

    @Then("I will be navigated to Consumer-reported Communication Page")
    public void i_will_be_navigated_to_Consumer_reported_Communication_Page() {
        assertTrue(viewProfile.consumerRepComPage.isDisplayed(), "Consumer-reported Communication Page is not displayed");
    }

    @Then("I clear existing phone numbers")
    public void i_clear_existing_phone_numbers() {
        viewProfile.clearButton.get(1).click();
        viewProfile.clearButton.get(2).click();
        viewProfile.clearButton.get(3).click();
    }

    @When("I input invalid data in to City and ZIP fields")
    public void I_input_invalid_data_in_to_City_and_ZIP_fields() {
        clearAndFillText(viewProfile.mailingAddressLine1, "Str name");
        clearAndFillText(viewProfile.mailingCity, "2345135");
        clearAndFillText(viewProfile.mailingZipCode, "1234");
    }

    @Then("I verify following errors is displayed")
    public void I_verify_following_errors_is_displayed(Map<String, String> data) {
        for (String each : data.keySet()) {
            WebElement el = Driver.getDriver().findElement(By.xpath("//p[text()='" + data.get(each) + "']"));
            assertTrue(isElementDisplayed(el), "inline message " + data.get(each) + " is not displayed");
        }
    }

    @When("I start input address fields")
    public void I_start_input_address_fields() {
        waitForVisibility(viewProfile.mailingAddressLine2, 5);
        clearAndFillText(viewProfile.mailingAddressLine2, "mailing test st 2");
    }

    @Then("I verify fields in Cases Panel is displayed")
    public void i_verify_fields_in_Cases_Panel_is_displayed() {
        waitFor(2);
        assertTrue(viewProfile.casesTitle.isDisplayed(), "Cases Panel is not displayed");
        assertTrue(viewProfile.caseHeader.isDisplayed(), "Case Header is not displayed");
        assertTrue(viewProfile.caseIDHeader.isDisplayed(), "Case ID Header is not displayed");
        assertTrue(viewProfile.medicaidType.isDisplayed(), "Medicaid Column is not displayed");
        assertTrue(viewProfile.medicaidType.isDisplayed(), "Medicaid Column is not displayed");
    }

    @When("I click on Medicaid Case ID")
    public void i_click_on_Medicaid_Case_ID() {
        waitFor(2);
        scrollDownUsingActions(4);
        viewProfile.caseIdValue.click();
    }

    @Then("I will be navigated to Medicaid Case Details screen")
    public void i_will_be_navigated_to_Medicaid_Case_Details_screen() {
        waitFor(2);
        assertTrue(viewProfile.caseAddressTxt.isDisplayed(), "Case Address Text is not displayed");
        assertTrue(viewProfile.casePhoneNumTxt.isDisplayed(), "Case Phone Number is not displayed");
        assertTrue(viewProfile.caseEmailTxt.isDisplayed(), "Case Email Text is not displayed");
    }

    @Then("I validate Phone Standard on {string} screen")
    public void iValidatePhoneStandardOnScreen(String page) {
        String expectedPhone = "(444) 444-45559";
        String actualPhone = "";
        switch (page) {
            case "searchResult":
                actualPhone = manualCaseConsumerSearch.dcSearchResultPhones.get(0).getText();
                break;
            case "viewConsumerProfile":
                iNavigateToConsumerProfilePageByClickingOnStateId();
                actualPhone = viewProfile.ccProfilePhoneNumber.getText();
                break;
            case "viewCaseInfo":
                waitFor(2);
                actualPhone = viewCase.casePhoneNumber.getText();
                System.out.println("++++++++++++++++++++++++++" + actualPhone + "+++++++++++++++++++++++");
                break;
        }
        assertTrue(expectedPhone.equals(actualPhone));
    }


    @And("I navigate to case view page by clicking on DC caseId")
    public void iNavigateToCasePageByClickingOnDCCaseId() {
        manualCaseConsumerSearch.dcCaseIDs.get(0).click();
    }

    @When("I select a case to view from the list of consumers by clicking on the Case ID, if there is one for the consumer")
    public void click_on_case_id() {
        String expectedCaseID = APICreateConsumerContactController.getExternalcaseId();
        String actualMadicaidCaseId = manualCaseConsumerSearch.dcCaseIDs.get(0).getText();
        assertEquals(actualMadicaidCaseId, expectedCaseID, "CaseId is not equals, expected = " + expectedCaseID);
        jsClick(manualCaseConsumerSearch.dcCaseIDs.get(0));
        assertTrue(isElementDisplayed(viewProfile.caseDetailsTab), "Wrong page");
    }

    @When("I will verify following tabs displayed")
    public void verifyAllFieldsOnCaseDedailsPage(List<String> tabs) {
        for (String each : tabs) {
            switch (each) {
                case "CASE DETAILS":
                    assertEquals(viewProfile.caseDetailsTab.getText().toUpperCase(), each.toUpperCase());
                    break;
                case "CONTACT DETAILS":
                    assertEquals(viewProfile.contactDetailsTab.getText().toUpperCase(), each.toUpperCase());
                    break;
                case "TASK & SERVICE REQUEST":
                    assertEquals(viewProfile.taskANDserviceRequestTab.getText().toUpperCase(), each.toUpperCase());
                    break;
                case "Program & Benefit Info":
                    assertEquals(viewProfile.programANDBenefitTab.getText().toUpperCase(), each.toUpperCase());
                    break;
                case "History":
                    assertEquals(viewProfile.historyTab.getText().toUpperCase(), each.toUpperCase());
                    break;
            }
        }

    }

    @Then("I verify all case members headers and labels")
    public void i_verify_all_case_members_headers_and_labels(List<String> data) {
        assertTrue(isElementDisplayed(viewProfile.stateIdHeaderLabel), data.get(0) + "header not displayed");
        assertTrue(isElementDisplayed(viewProfile.fullNameHeaderLabel), data.get(1) + "header not displayed");
        assertTrue(isElementDisplayed(viewProfile.dateOfBirthHeaderLabel), data.get(2) + "header not displayed");
        assertTrue(isElementDisplayed(viewProfile.ageGenderHeaderLabel), data.get(3) + "header not displayed");
        assertTrue(isElementDisplayed(viewProfile.authorizedRepHeaderLabel), data.get(4) + "header not displayed");
    }

    @Then("I verify all following consumer data")
    public void I_verify_all_following_consumer_data(List<String> data) {
        //validation of contact headers on case details page
        assertTrue(isElementDisplayed(viewProfile.caseAddressTxt), "caseAddress header not displayed");
        assertTrue(isElementDisplayed(viewProfile.casePhoneNumTxt), "casePhoneNum header not displayed");
        assertTrue(isElementDisplayed(viewProfile.caseEmailTxt), "caseEmail header not displayed");

        //validation of contacts values on case details page
        assertTrue(!viewProfile.caseAddressValueOnCaseDetailsPage.getText().equalsIgnoreCase("--"), "case address value is empty");
        assertTrue(!viewProfile.casePhoneValueOnCaseDetailsPage.getText().equalsIgnoreCase("--"), "case phone value is empty");
        assertTrue(!viewProfile.caseEmailValueOnCaseDetailsPage.getText().equalsIgnoreCase("--"), "case email value is empty");
    }

    @When("I click on add {string} button on consumer profile page")
    public void i_click_on_add_button_on_consumer_profile_page(String type) {
        if (type.contains("Phone")) {
            waitFor(2);
            jsClick(viewProfile.addPhoneButton);
        }
        if (type.contains("Language")) {
            waitFor(2);
            jsClick(viewProfile.addLanguageButton);
        }
    }

    @When("I click on edit {string} button on consumer profile page")
    public void i_click_on_edit_button_on_consumer_profile_page(String string) {
        waitFor(2);
        jsClick(viewProfile.editLanguageButton);
    }


    @Then("I verify fields in Consumer-Reported Communication Panel are displayed")
    public void i_verify_fields_in_Consumer_Reported_Communication_Panel_are_displayed() {
        waitFor(3);
        assertTrue(viewProfile.mailCheckbox.isDisplayed(), "Mail Checkbox is not displayed");
        assertTrue(viewProfile.emailCheckbox.isDisplayed(), "Email Checkbox is not displayed");
        assertTrue(viewProfile.textCheckbox.isDisplayed(), "Text Checkbox is not displayed");
    }

    @Then("I input all required fields for {string} in Correspondence Preference Page")
    public void i_input_all_required_fields_for_in_Correspondence_Preference_Page(String fieldType) {
        if (fieldType.equals("Address")) {
            waitFor(3);
            jsClick(viewProfile.mailCheckbox);
            Faker random = new Faker();
            synchronized (address1) {
                address1.set(random.address().streetAddress());
            }
            synchronized (address2) {
                address2.set(random.address().secondaryAddress());
            }
            synchronized (addressCity) {
                addressCity.set(random.address().city());
            }
            synchronized (zipCode) {
                zipCode.set(random.address().zipCode().substring(0, 5));
            }


            clearAndFillText(viewProfile.mailAddressLine1, address1.get());
            clearAndFillText(viewProfile.mailAddressLine2, address2.get());
            clearAndFillText(viewProfile.mailCity, addressCity.get());
            clearAndFillText(viewProfile.mailZipCode, zipCode.get());
            selectRandomDropDownOption(viewProfile.mailStateDropdown);
            waitFor(1);
            synchronized (state) {
                state.set(viewProfile.mailStateDropdown.getText());
            }
        }
        if (fieldType.equals("Email")) {
            waitFor(2);
            jsClick(viewProfile.emailCheckbox);
            synchronized (email) {
                email.set("TestEmailField123@gmail.com");
            }
            clearAndFillText(viewProfile.emailTextField, email.get());
        }
        if (fieldType.equals("Text")) {
            waitFor(2);
            jsClick(viewProfile.textCheckbox);
            synchronized (phoneNumber) {
                phoneNumber.set("9045642876");
            }
            clearAndFillText(viewProfile.phoneNumTextField, phoneNumber.get());
        }
    }

    @Then("I verify correspondence preference {string} is saved and displayed in communication panel")
    public void i_verify_correspondence_preference_is_saved_and_displayed_in_communication_panel(String fieldType) {
        if (fieldType.equals("Address")) {
            waitFor(2);
            String actualFullAddressOnProfilePage = viewProfile.mailingCheckBoxAddress.getText();
            assertTrue(actualFullAddressOnProfilePage.contains(address1.get()), "Address1 not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(address2.get()), "Address2 not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(addressCity.get()), "AddressCity not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(state.get()), "State not displayed OR incorrect");
            assertTrue(actualFullAddressOnProfilePage.contains(zipCode.get()), "ZipCode not displayed OR incorrect");
        }
        if (fieldType.equals("Email")) {
            waitFor(2);
            String actualFullEmailOnProfilePage = viewProfile.emilCheckBoxText.getText();
            assertTrue(actualFullEmailOnProfilePage.contains(email.get()), "Email not displayed OR incorrect");
        }
        if (fieldType.equals("Text")) {
            waitFor(2);
            String actualFullEmailOnProfilePage = viewProfile.phoneNumCheckBoxText.getText();
            assertTrue(actualFullEmailOnProfilePage.contains("(904) 564-2876"), "Phone Number is not displayed OR incorrect");
        }
    }

    @When("I click on uncheck {string} checkbox")
    public void i_click_on_uncheck_checkbox(String checkbox) {
        if (checkbox.equals("MAIL")) {
            waitFor(2);
            jsClick(viewProfile.mailCheckbox);
        }
        if (checkbox.equals("EMAIL")) {
            waitFor(2);
            jsClick(viewProfile.emailCheckbox);
        }
        if (checkbox.equals("TEXT")) {
            waitFor(2);
            jsClick(viewProfile.textCheckbox);
        }
    }

    @Then("I see Consumer-Reported Communication panel for PI")
    public void iSeeConsumerReportedCommunicationPanelForPI() {
        waitFor(2);
        jsClick(viewProfile.mailCheckbox);
        jsClick(viewProfile.emailCheckbox);
        jsClick(viewProfile.textCheckbox);
        jsClick(viewProfile.doNotCallCheckbox);
        viewProfile.addCommunicationsButton.isDisplayed();
    }

    @Then("I see Add button for Consumer-Reported Communication panel for PI")
    public void iSeeAddButtonForConsumerReportedCommunicationPanelForPI() {
        waitFor(2);
        assertTrue(viewProfile.addCommunicationsButton.isDisplayed());
    }

    @Then("I see Edit button for Consumer-Reported Communication panel for PI")
    public void iSeeEditButtonForConsumerReportedCommunicationPanelForPI() {
        assertTrue(viewProfile.editLanguageButton.isDisplayed());
    }

    @When("I will verify navigation following tabs")
    public void verifyNavigationToFollowingTubs(List<String> tabs) {
        for (String each : tabs) {
            switch (each) {
                case "CASE DETAILS":
                    viewProfile.caseDetailsTab.click();
                    break;
                case "CONTACT DETAILS":
                    viewProfile.contactDetailsTab.click();
                    break;
                case "TASK & SERVICE REQUEST":
                    viewProfile.taskANDserviceRequestTab.click();
                    break;
                case "Program & Benefit Info":
                    viewProfile.programANDBenefitTab.click();
                    break;
                case "History":
                    viewProfile.historyTab.click();
                    break;
            }
        }

    }

    @And("I click on Back Arrow on DC view case Consumer details Page")
    public void iClickOnBackArrowOnDCViewCaseConsumerDetailsPage() {
        waitForClickablility(viewProfile.backArrow, 5);
        viewProfile.backArrow.click();
    }

    @Then("I should see {string} name on relationships module")
    public void I_should_see_relationships_module(String relationType) {
        waitFor(3);
        assertTrue(isElementDisplayed(viewProfile.RELATIONSHIPSTable), "RELATIONSHIPSTable no displayd");
        assertTrue(isElementDisplayed(viewProfile.RELATEDtoColumn), "RELATED to Column not displayed");
        assertTrue(isElementDisplayed(viewProfile.RELATIONSHIPcolumn), "RELATIONSHIP to Column not displayed");
        if (relationType.equalsIgnoreCase("child")) {
            assertTrue(isElementDisplayed(viewProfile.childNameProfileOnRelationshipsPanel));
        } else if (relationType.equalsIgnoreCase("mother")) {
            assertTrue(isElementDisplayed(viewProfile.motherNameProfileOnRelationshipsPanel));
        }
    }

    @When("I click on child name")
    public void I_click_on_child_name() {
        jsClick(viewProfile.childNameProfileOnRelationshipsPanel);
        waitFor(2);

    }

    @When("I click on mother name")
    public void I_click_on_mother_name() {
        jsClick(viewProfile.motherNameProfileOnRelationshipsPanel);
        waitFor(2);
    }

    @Then("I should see language dropdown is in alphabetical order")
    public void i_should_see_language_dropdown_is_in_alphabetical_order() {
        viewProfile.languageDropDown.click();
        waitFor(2);
        List<String> actual = new ArrayList<>();
        viewProfile.languageDropDownOptions.stream().forEach((k) -> {
            actual.add(k.getText());
        });
        List<String> expected = new ArrayList<>();
        expected.addAll(actual);
        Collections.sort(expected);
        assertEquals(actual, expected);
    }


    @Then("I input all required fields with stopped destination for {string} on Correspondence Preference Page")
    public void i_input_all_required_fields_wth_stopped_destination_for_on_Correspondence_Preference_Page(String fieldType) {
        if (fieldType.equals("Address")) {
            waitFor(3);
            jsClick(viewProfile.mailCheckbox);
            Faker random = new Faker();
            synchronized (address1) {
                address1.set(random.address().streetAddress());
            }
            synchronized (address2) {
                address2.set(random.address().secondaryAddress());
            }
            synchronized (addressCity) {
                addressCity.set(random.address().city());
            }
            synchronized (zipCode) {
                zipCode.set(random.address().zipCode().substring(0, 5));
            }


            clearAndFillText(viewProfile.mailAddressLine1, address1.get());
            clearAndFillText(viewProfile.mailAddressLine2, address2.get());
            clearAndFillText(viewProfile.mailCity, addressCity.get());
            clearAndFillText(viewProfile.mailZipCode, zipCode.get());
            selectRandomDropDownOption(viewProfile.mailStateDropdown);
            waitFor(1);
            synchronized (state) {
                state.set(viewProfile.mailStateDropdown.getText());
            }
        }
        if (fieldType.equals("Email")) {
            waitFor(2);
            jsClick(viewProfile.emailCheckbox);
            synchronized (email) {
                email.set("stopped@gmail.com");
            }
            clearAndFillText(viewProfile.emailTextField, email.get());
        }
        if (fieldType.equals("Text")) {
            waitFor(2);
            jsClick(viewProfile.textCheckbox);
            synchronized (phoneNumber) {
                phoneNumber.set("0236304804");
            }
            clearAndFillText(viewProfile.phoneNumTextField, phoneNumber.get());
        }
    }

    @And("I see stopped destination Error message {string} is displayed")
    public void i_see_stopped_destanation_error_message_is_displayed(String messageText) {
        waitFor(1);
        assertTrue(viewProfile.stoppedPhoneErrorMessage.isDisplayed(), "Error message is not displayed");
        System.out.println("?"+viewProfile.stoppedPhoneErrorMessage.getText()+"?");
        assertTrue(viewProfile.stoppedPhoneErrorMessage.getText().equals(messageText), "Error message is not (" + messageText + ")");
    }

}
