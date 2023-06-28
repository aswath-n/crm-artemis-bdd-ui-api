package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMCaseAndConsumerSearchPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.exception.CucumberException;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class CRM_CORE_UserInterfaceStepDef extends CRMUtilities implements ApiBase{

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMCaseAndConsumerSearchPage caseConsumerSrhPage = new CRMCaseAndConsumerSearchPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();

    private String caseId;

    @When("I expand the Case Consumer this contact relates to in search result")
    public void i_expand_the_Case_Consumer_in_search_result() {
        waitForVisibility(contactRecord.expandFistConsumer, 10);
        contactRecord.expandFistConsumer.click();
        waitForVisibility(contactRecord.caseIdFirstRecord, 10);
        caseId = contactRecord.caseIdFirstRecord.getText();
    }

    @Then("I verify system displays the Home Address with Authentication checkboxes")
    public void verifyHomeAddressDetails() {
        contactRecord.authGridMailingCheckbox.isDisplayed();
    }

    @Then("I verify system displays the Home Address, DOB and SSN with Authentication checkboxes")
    public void i_verify_system_displays_the_Home_Address_DOB_and_SSN_with_Authentication_checkboxes() {
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.HomeAddressCheckBox));
        waitFor(3);
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.coverVADOBCheckBoxNew));
        waitFor(3);
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.coverVASSNcheckBox));
    }

    @Then("I verify system displays the Home Address above the grid, DOB and SSN with Authentication checkboxes")
    public void i_verify_system_displays_the_Home_Address_above_the_grid_DOB_and_SSN_with_Authentication_checkboxes() {
        assertTrue(quickIsDisplayed(contactRecord.consumerAddressLabel), "Missing address label in the Consumer Authentication Panel");
        waitFor(1);
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.inebDOBCheckBox), "Missing DOB checkbox in the Consumer Authentication Panel");
        waitFor(1);
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.SSNcheckBox), "Missing DOB checkbox in the Consumer Authentication Panel");
        waitFor(1);
    }

    @Then("I verify system displays the DOB, SSN and Phone Number with Authentication checkboxes")
    public void verifyDOBSSNAndPhoneNumDetails() {
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.DOBcheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.SSNcheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.PhoneNumberCheckBox));
        String dobValMasked = contactRecord.ConsumerDOBVal.getText();
        String[] dobValMaskedSplit = dobValMasked.split("/");
        for (String val : dobValMaskedSplit) {
            System.out.println("DOB Split value: " + val);
            assertTrue(checkEachCharacterVal(val, '*'));
        }
        contactRecord.ConsumerDOBShowBtn.click();
        waitFor(1);
        String dobValUnMasked = contactRecord.ConsumerDOBVal.getText();
        String[] dobValUnMaskedSplit = dobValUnMasked.split("/");
        for (String val : dobValUnMaskedSplit) {
            System.out.println("SSN Split value after Unmasked: " + val);
            assertTrue(NumberUtils.isNumber(val));
        }
        waitFor(1);
        String ssnValMasked = contactRecord.ConsumerSSNVal.getText();
        String[] ssnValMaskedSplit = ssnValMasked.split("-");
        for (String val : ssnValMaskedSplit) {
            System.out.println("SSN Split value: " + val);
            assertTrue(checkEachCharacterVal(val, '*'));
        }
        contactRecord.ConsumerSSNShowBtn.click();
        waitFor(1);
        String ssnValUnMasked = contactRecord.ConsumerSSNVal.getText();
        String[] ssnValUnMaskedSplit = ssnValUnMasked.split("-");
        System.out.println("SSN Split value Unmasked: " + ssnValUnMaskedSplit[0]);
        assertTrue(checkEachCharacterVal(ssnValUnMaskedSplit[0], '*'));
        System.out.println("SSN Split value Unmasked: " + ssnValUnMaskedSplit[1]);
        assertTrue(checkEachCharacterVal(ssnValUnMaskedSplit[1], '*'));
        System.out.println("SSN Split value Unmasked: " + ssnValUnMaskedSplit[2]);
        assertTrue(NumberUtils.isNumber(ssnValUnMaskedSplit[2]));
        String phoneNumVal = contactRecord.ConsumerPhoneNumberVal.getText();
        phoneNumVal = phoneNumVal.replaceAll("[^0-9]", "");
        System.out.println("Phone Number after removing non-numeric characters: " + phoneNumVal);
        assertTrue(NumberUtils.isNumber(phoneNumVal));
    }

    @Then("I verify display of 'Consumer Authenticated' message by selecting two checkboxes")
    public void verifyConsumerAuthMsg() {
        contactRecord.ConsumerFullNameCheckBox.click();
        waitFor(2);

        //External Id is removed for NJ as part of CP-13009
        //Verify with Home Address and External Case Id Check boxes
        //contactRecord.HomeAddressCheckBox.click();
        //contactRecord.CaseIdCheckBox.click();
        //waitFor(1);
        //assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());

        //Verify with Home Address and DOB Check boxes
        contactRecord.authGridMailingCheckbox.click();
        contactRecord.DOBcheckBox.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());

        //Verify with Home Address and SSN Check boxes
        contactRecord.DOBcheckBox.click();
        contactRecord.SSNcheckBox.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());

        //Verify with Home Address and Phone Number Check boxes
        contactRecord.SSNcheckBox.click();
        contactRecord.PhoneNumberCheckBox.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());

        //Verify with External Case Id and DOB Check boxes
        contactRecord.PhoneNumberCheckBox.click();
        contactRecord.authGridMailingCheckbox.click();
        contactRecord.SSNcheckBox.click();
        contactRecord.DOBcheckBox.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());

        //Verify with External Case Id and Phone Number Check boxes
        contactRecord.SSNcheckBox.click();
        contactRecord.PhoneNumberCheckBox.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());

        //uncheck DOB & Phone for next test
        contactRecord.PhoneNumberCheckBox.click();
        contactRecord.DOBcheckBox.click();
        waitFor(2);

        //Verify with DOB and SSN Check boxes
        contactRecord.PhoneNumberCheckBox.click();
        // contactRecord.CaseIdCheckBox.click();
        contactRecord.DOBcheckBox.click();
        contactRecord.SSNcheckBox.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());

        //uncheck SSN & Phone for next test
        contactRecord.SSNcheckBox.click();
        contactRecord.PhoneNumberCheckBox.click();
        waitFor(1);

        //Verify with DOB and Phone Number Check boxes
        contactRecord.SSNcheckBox.click();
        contactRecord.PhoneNumberCheckBox.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());
    }

    @Then("I verify display of {string} message by selecting two checkboxes for CoverVA")
    public void i_verify_display_of_message_by_selecting_two_checkboxes_for_CoverVA(String string) {
        contactRecord.ConsumerFullNameCheckBox.click();
        waitFor(2);

//Verify with Home Address and DOB Check boxes
        contactRecord.HomeAddressCheckBox.click();
        waitFor(3);
        contactRecord.coverVADOBCheckBoxNew.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());

//Verify with DOB and SSN Check boxes
        waitFor(3);
        contactRecord.coverVADOBCheckBoxNew.click();
        waitFor(3);
        contactRecord.coverVASSNcheckBox.click();
        waitFor(1);
        assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());
    }

    @Then("I verify display of {string} message by selecting FullName, HomeAddress, DOB and SSN checkboxes for IN-EB")
    public void i_verify_display_of_message_by_selecting_FullName_HomeAddress_DOB_and_SSN_checkboxes_for_IN_EB(String data) {
        waitFor(1);
        contactRecord.ConsumerFullNameCheckBox.click();
        waitFor(1);
        contactRecord.consumerAddressCheckbox.click();
        waitFor(1);
        contactRecord.inebDOBCheckBox.click();
        Assert.assertFalse(quickIsDisplayed(caseConsumerSrhPage.consumerAuthenticateButton));
        waitFor(1);
        contactRecord.SSNcheckBox.click();
        waitFor(2);
        assertEquals(caseConsumerSrhPage.consumerAuthenticateButton.getText(), "check_circle_outline" + data, data + " message label is not displayed after authenticating");
    }

    @Then("I verify display of '-- --' for null values")
    public void verifyNullValues() {
        String ssnVal = contactRecord.ConsumerSSNVal.getText();
        String[] ssnValSplit = ssnVal.split(" ");
        for (String val : ssnValSplit) {
            assertTrue(checkEachCharacterVal(val, '-'));
        }

        try {
            contactRecord.SSNcheckBox.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Exception is: " + e);
            assertTrue(e.toString().contains("no such element: Unable to locate element"));
        }
    }

    private boolean checkEachCharacterVal(String in, char c) {
        boolean result = true;
        for (char i : in.toCharArray()) {
            if (i != c) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Then("Verify in Authentication Grid - External \\(GetInsured) Case & Consumer ID are not displayed with a checkbox")
    public void verifyInAuthenticationGridExternalGetInsuredCaseConsumerIDAreNotDisplayedWithACheckbox() {
        assertFalse(isElementDisplayed(contactRecord.CaseIdCheckBox));
        assertFalse(isElementDisplayed(contactRecord.ConsumerIdCheckBox));
    }

    @Then("I verify display of {string} for home address null value")
    public void i_verify_display_of_for_home_address_null_value(String doubleDash) {
        String homeaddressVal = contactRecord.authGridMailingCheckbox.getText();
        String[] homeaddressValSpli = homeaddressVal.split(" ");
        for (String val : homeaddressValSpli) {
            assertTrue(checkEachCharacterVal(val, '-'));
        }

        try {
            contactRecord.authGridMailingCheckbox.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Exception is: " + e);
            assertTrue(e.toString().contains("no such element: Unable to locate element"));
        }
    }

    @Then("Verify in Authentication Grid - consumerID and program consumerID are not displayed")
    public void verify_in_Authentication_Grid_consumerID_and_program_consumerID_are_not_displayed() {
        assertFalse(quickIsDisplayed(contactRecord.programConsumerID));
        assertFalse(quickIsDisplayed(contactRecord.ConsumerIdCheckBox));
    }

    @Then("I verify that phone number of primary individual is displayed with auth checkbox")
    public void i_verify_that_phone_number_of_primary_individual_is_displayed_with_auth_checkbox() {
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.phoneNumberAuthCheckBox), "Phone number Authorize check box is not displayed");
    }

    @Then("I verify that Medicaid RID is displayed")
    public void i_verify_that_Medicaid_RID_is_displayed() {
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.medicaidRIDAuthCheckBox));
    }

    @Then("I verify display of {string}")
    public void i_verify_display_of(String addressType) {
        if (addressType.equalsIgnoreCase("Mailing")) {
            contactRecord.authGridMailingCheckbox.isDisplayed();
        } else if (addressType.equalsIgnoreCase("Physical")) {
            contactRecord.inebPhysicalAddressCheckBox.isDisplayed();
        }
    }

    @Then("I verify that StateCaseID and Medicaid RID are displayed with auth checkboxes")
    public void i_verify_that_StateCaseID_and_Medicaid_RID_are_displayed_with_auth_checkbox() {
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.caseIDAuthCheckBoxLabel));
        waitFor(3);
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.medicaidRIDAuthCheckBox));
        waitFor(3);
    }

    @Then("I verify that StateCaseID and Medicaid RID are displayed with auth checkboxes on General Contact")
    public void i_verify_that_StateCaseID_and_Medicaid_RID_are_displayed_with_auth_checkboxes_on_General_Contact() {
        assertTrue(contactRecord.caseIDAuthCheckBox.isDisplayed());
        waitFor(3);
        assertTrue(contactRecord.medicaidRIDAuthCheckBox.isDisplayed());
        waitFor(3);
    }

    @Then("I click on FullName, HomeAddress, DOB checkboxes and verify that Link Record not displayed for IN-EB")
    public void i_click_on_FullName_HomeAddress_DOB_checkboxes_for_IN_EB() {
        contactRecord.ConsumerFullNameCheckBox.click();
        waitFor(2);
        contactRecord.consumerAddressCheckbox.click();
        waitFor(1);
        contactRecord.inebDOBCheckBox.click();
        Assert.assertFalse(isElementDisplayed(caseConsumerSrhPage.consumerAuthenticateButton));
    }

    @Then("I verify display of Consumer Authenticated message by selecting FullName, HomeAddress, DOB and one of the following checkboxes for IN-EB")
    public void i_verify_display_of_message_by_selecting_FullName_HomeAddress_DOB_and_one_of_the_SNN_or_medicaidRid_or_stateCaseId_checkboxes_for_IN_EB(List<String> options) {
        for (String option : options) {
            switch (option) {
                case "State Case ID":
                    contactRecord.CaseIdCheckBox.click();
                    waitFor(1);
                    assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());
                    break;
                case "Medicaid/RID":
                    contactRecord.CaseIdCheckBox.click();
                    waitFor(1);
                    contactRecord.medicaidRIDAuthCheckBox.click();
                    waitFor(1);
                    assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());
                    break;
                case "SSN":
                    contactRecord.medicaidRIDAuthCheckBox.click();
                    waitFor(1);
                    contactRecord.SSNcheckBox.click();
                    waitFor(1);
                    assertTrue(caseConsumerSrhPage.consumerAuthenticateButton.isDisplayed());
                    break;
            }
        }
    }

    @Then("I verify system does not display the Phone Number column on search result before expanding arrow")
    public void verify_that_system_doesnt_display_phoneNumber_on_result() {
        assertFalse(isElementDisplayed(activeContactPage.phoneNumberAutGrid));
    }

    @Then("I verify system displays the phone number with Authentication checkbox for General")
    public void i_verify_system_displays_the_phoneNumber_with_Authentication_checkbox_general() {
        waitFor(2);
        assertTrue(UIAutoUitilities.isElementPresent(activeContactPage.phoneNumberAutGrid));
        waitFor(2);
    }

    @Override
    public boolean quickIsDisplayed(WebElement element) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        try {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            if (element.isDisplayed()) return true;
        } catch (Exception exception) {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            return false;
        }
        return false;
    }

    @And("I click on the expand carrot for the {string} Case Consumer in the search result")
    public void iClickOnTheExpandCarrotForTheCaseConsumerInTheSearchResult(String consumerRow) {
        switch (consumerRow) {
            case "FIRST":
                contactRecord.firstSearchResultChevronButton.click();
                waitForVisibility(contactRecord.caseIdFirstRecord, 10);
                caseId = contactRecord.stateCaseIdColumResult.get(0).getText();
                break;
            case "SECOND":
                contactRecord.secondSearchResultChevronButton.click();
                waitForVisibility(contactRecord.caseIdFirstRecord, 10);
                caseId = contactRecord.stateCaseIdColumResult.get(1).getText();
                break;
            default:
                fail("Provided Key does not match");
        }
    }

    @Then("I verify {string} data is displayed for the following in Contact Record Authentication")
    public void iVerifyDataIsDisplayedForTheFollowingInContactRecordAuthentication(String dataValue, List<String> dataList) {
        for (String each : dataList) {
            switch (each) {
                case "HOME ADDRESS":
                    if ("-- --".equals(dataValue)) {
                        assertEquals(contactRecord.consumerNullAddressLabel.getText(), dataValue, "Mismatch of " + dataValue + " and HOME ADDRESS");
                    } else {
                        assertEquals(contactRecord.consumerAddressLabel.getText(), dataValue, "Mismatch of " + dataValue + " and HOME ADDRESS");
                    }
                    break;
                default:
                    fail("Mismatch in provided key");
            }
        }
    }

    @When("I Enter the following data in the Third Party Contact tab")
    public void iEnterTheFollowingDataInTheThirdPartyContactTab(Map<String, String> data) {
        for (String eachValue : data.keySet()) {
            switch (eachValue.toUpperCase()) {
                case "SEARCH FIRST NAME":
                    clearAndFillTextWithSendKeys(contactRecord.thirdPartyFirstName.get(1), data.get("SEARCH FIRST NAME"));
                    break;
                case "SEARCH LAST NAME":
                    clearAndFillTextWithSendKeys(contactRecord.thirdPartyLastName.get(1), data.get("SEARCH LAST NAME"));
                    break;
                default:
                    fail("Mismatch in provided key");
            }
        }
        click(contactRecord.searchButton);
        waitFor(2);
    }

    @And("I click on the {string} Case Consumer this contact relates to in search result")
    public void iClickOnTheCaseConsumerThisContactRelatesToInSearchResult(String data) {
        switch (data) {
            case "FIRST":
                waitForVisibility(contactRecord.expandFistConsumer, 10);
                contactRecord.expandFistConsumer.click();
                break;
            default:
                fail("Provided key did not match");
        }
    }

    @Then("I Validate {string} label for Genesys Widget {string} displayed")
    public void iValidateLabelForGenesysWidgetDisplayed(String data, String type) {
        waitFor(5);
        switch (type) {
            case "is":
                assertEquals(activeContactPage.callManagment.getText(), "public"+data, "Expected Genesys Widget labeled as INTERACTION MANAGEMENT but Actual label: " + activeContactPage.callManagment.getText());
                break;
            case "is not":
                assertEquals(Driver.getDriver().findElements(By.xpath("//button[contains(text(),'" + data + "')]")).size(), 0, "CALL MANAGEMENT label found, expected: INTERACTION MANAGEMENT");
                break;
        }
    }
}
