package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.World;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_AddNewAddressInformationStepDef extends CRMUtilities implements ApiBase {

    CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRMAddContactInfoPage crmAddContactInfoPage = new CRMAddContactInfoPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMEditProfileDetailsOnProfileContactPage editProfileDetails = new CRMEditProfileDetailsOnProfileContactPage();

    public String addressLineOnExpected;
    public String addressLineTwoExpected;
    public String cityFieldExpected;
    public String countyFieldExpected;


    @And("I should see the Demographic Tab")
    public void i_should_see_the_demographic_tab() {
        assertTrue(demographicContactInfoPage.demographicInfoTab.isDisplayed());

    }

    @Then("I click on the Demographic Info Tab")
    public void i_click_on_the_demographic_info_tab() {
        waitForVisibility(demographicContactInfoPage.demographicInfoTab, 20);
        demographicContactInfoPage.demographicInfoTab.click();
        waitFor(2);
    }


    @And("I should see contact Info Tab")
    public void i_should_see_contact_info_tab() {
        waitForVisibility(demographicContactInfoPage.contactInfoTab, 2);
        assertTrue(demographicContactInfoPage.contactInfoTab.isDisplayed());
    }

    @Then("I click on the Contact Info Tab")
    public void i_click_on_the_contact_info_tab() {
        waitFor(5);
        jsClick(demographicContactInfoPage.contactInfoTab);
    }

    @And("I should see the Address label should be displayed")
    public void i_should_see_the_address_label_should_be_displayed() {
        assertTrue(demographicContactInfoPage.addressLabel.isDisplayed());
    }

    @And("I should see the Add button displayed")
    public void i_should_see_the_add_button_displayed() {
        assertTrue(demographicContactInfoPage.addAddressButton.isDisplayed());
    }

    @Then("I click on the Add button")
    public void i_click_on_the_add_button() {
        waitForVisibility(demographicContactInfoPage.addAddressButton, 2);
        try {
            demographicContactInfoPage.addAddressButton.click();
        }catch (Exception e){
            scrollDown();
            jsClick(demographicContactInfoPage.addAddressButton);
        }
    }

    @Then("i verify that {string} is not able to add address")
    public void i_verify_that_is_not_able_to_add_address(String string) {
        Assert.assertTrue(!isElementDisplayed(crmAddContactInfoPage.addressLineOne));
        Assert.assertTrue(!isElementDisplayed(crmAddContactInfoPage.addressLineTwo));
        Assert.assertTrue(!isElementDisplayed(crmAddContactInfoPage.saveButton));
    }

    @Then("I verify that  {string} is able to see phoneNumbers under Contact Info Tab")
    public void i_verify_that_is_able_to_see_phoneNumbers_under_Contact_Info_Tab(String string) {
        Assert.assertTrue(crmAddContactInfoPage.listOfPhoneNumbers.size() > 0);
        if (crmAddContactInfoPage.listOfPhoneNumbers.size() > 0) {
            for (int i = 0; i < crmAddContactInfoPage.listOfPhoneNumbers.size(); i++) {
                Assert.assertTrue(isElementDisplayed(crmAddContactInfoPage.listOfPhoneNumbers.get(i)));

            }
        }
    }

    @Then("{string}  should not be able to edit any of the Profile Contact fields")
    public void should_not_be_able_to_edit_any_of_the_Profile_Contact_fields(String string) {
        String disabledAttribute = editProfileDetails.profileDetailsFirstName.getAttribute("disabled");
        System.out.println("disbaledAttribute " + disabledAttribute);
        assertNotNull((disabledAttribute));
        assertEquals(editProfileDetails.profileDetailsFirstName.getAttribute("disabled"), "true");
        assertEquals(editProfileDetails.profileDetailsLastName.getAttribute("disabled"), "true");
        assertEquals(editProfileDetails.profileDetailsSSN.getAttribute("disabled"), "true");
    }

    @Then("I verify that I am in the Address Page")
    public void i_verify_that_i_am_in_the_address_page() {
        assertTrue(demographicContactInfoPage.addAddressButton.isDisplayed());
    }

    @And("I verify by entering valid data in the address field1 {string}")
    public void i_verify_by_entering_valid_data_in_the_address_field1(String address) {
        clearAndFillText(crmAddContactInfoPage.addressLineOne, address);
    }

    @And("I verify by entering valid data in the address field 2 {string}")
    public void i_verify_by_entering_valid_date_in_the_address_feild_2(String addressLine) {
        clearAndFillText(crmAddContactInfoPage.addressLineTwo, addressLine);
    }

    @And("I verify by entering valid data in the City field {string}")
    public void i_verify_by_entering_valid_data_in_the_city_field(String city) {
        clearAndFillText(crmAddContactInfoPage.city, city);
    }

    @And("I verify by entering valid data in the zip code {string}")
    public void i_verify_by_entering_valid_data_in_the_zip_code(String zipcode) {
        clearAndFillText(crmAddContactInfoPage.zip, zipcode);
    }

    @Then("I verify by providing a valid Current Date")
    public void i_verify_by_providing_a_valid_current_date() {
        String date = getCurrentDate();
        clearAndFillText(crmAddContactInfoPage.startDate, date);
    }

    @And("I click on Save button")
    public void i_click_on_save_button() {
        waitFor(2);
        crmAddContactInfoPage.saveButton.click();

    }

    @Then("I should be navigated to the Demographic Page")
    public void i_should_be_navigated_to_the_demographic_page() {

        assertTrue(demographicContactInfoPage.addressLabel.isDisplayed());
    }

    @And("I verify by entering invalid length in the address field1 {string}")
    public void i_verify_by_entering_invalid_length_in_the_address_field1(String invalid) {


        addressLineOnExpected = invalid;
        clearAndFillText(crmAddContactInfoPage.addressLineOne, invalid);

    }

    @Then("The Address Field one should not accept more than 50 characters")
    public void it_should_not_accept_more_than_50_characters() {

        String actual = crmAddContactInfoPage.addressLineOne.getAttribute("value");
        assertNotEquals(addressLineOnExpected.length() == 50, actual.length() == 50);

    }

    @And("I verify by entering invalid length in the address field2 {string}")
    public void i_verify_by_entering_invalid_length_in_the_address_field2(String expected) {
        addressLineTwoExpected = expected;
        clearAndFillText(crmAddContactInfoPage.addressLineTwo, expected);

    }

    @Then("The Address field two should not accept more than 50 characters")
    public void the_address_field_two_should_not_accept_more_than_50_characters() {
        String actual = crmAddContactInfoPage.addressLineTwo.getAttribute("value");
        assertNotEquals(addressLineTwoExpected, actual);
    }

    @And("I provide more than thirty characters in the City field {string}")
    public void i_provide_more_than_thirty_characters_in_the_city_field(String expected) {
        cityFieldExpected = expected;
        clearAndFillText(crmAddContactInfoPage.city, expected);

    }

    @Then("The City field should not accept more than 30 characters")
    public void the_city_field_should_accept_more_than_30_characters() {
        String actual = crmAddContactInfoPage.city.getAttribute("value");
        assertNotEquals(cityFieldExpected, actual);

    }

    @And("I verify by selecting a valid state")
    public void i_verify_by_selecting_a_valid_state() {

    }

    @Then("I verify by selecting a valid email address type")
    public void i_verify_by_selecting_a_valid_email_address_type() {
        try {
            selectDropDown(crmAddContactInfoPage.addressType, "Mailing");
            waitFor(1);
        } catch (Exception e) {
            System.out.print("The dropDown has not selected" + e.getStackTrace());
        }

    }

    @Then("I provide more than thirty characters in the county field {string}")
    public void i_provide_more_than_thirty_characters_in_the_county_field(String expected) {
        countyFieldExpected = expected;
        clearAndFillText(crmAddContactInfoPage.county, expected);

    }

    @Then("The county field should not accept more than {int} characters")
    public void the_county_field_should_not_accept_more_than_characters(Integer int1) {
        String actualCounty = crmAddContactInfoPage.county.getAttribute("value");
        assertTrue(actualCounty.length() == int1);


    }

    @When("I enter only special character in Address Line one field on the Demographic page")
    public void i_enter_only_special_character_in_Address_Line_one_field_on_the_Demographic_page() {
        clearAndFillText(crmAddContactInfoPage.addressLineOne, "***(((");
        crmAddContactInfoPage.saveButton.click();
    }

    @Then("I verify error is displayed for the Address Line one on the Demographic page")
    public void i_verify_error_is_displayed_for_the_Address_Line_one_on_the_Demographic_page() {
        waitFor(1);
        String ad1 = crmAddContactInfoPage.addressLineOne.getText();
        if (ad1 == null | ad1 == "") {
            System.out.println("unable enter Special characters");

        } else {
            System.out.println("Enable enter Special characters");
        }
        //assertNull(ad1,"Addressline allow to enter special characters");

    }

    @When("I enter only special character in Address Line two field on the Demographic page")
    public void i_enter_only_special_character_in_Address_Line_two_field_on_the_Demographic_page() {
        clearAndFillText(crmAddContactInfoPage.addressLineTwo, "***(((");
        crmAddContactInfoPage.saveButton.click();
    }

    @Then("I verify error is displayed for the Address Line two on the Demographic page")
    public void i_verify_error_is_displayed_for_the_Address_Line_two_on_the_Demographic_page() {
        waitFor(1);
        String ad2 = crmAddContactInfoPage.addressLineTwo.getText();
        if (ad2 == null | ad2 == "") {
            System.out.println("Unable enter Special characters");
        } else {
            System.out.println("Enable enter Special characters");
        }
    }

    @Then("I see all add new address fields are displayed")
    public void i_see_all_add_new_address_fields_are_displayed() {
        try {
            assertTrue(crmAddContactInfoPage.addressConsumer.isDisplayed());
        }catch (Exception e){
            System.out.println("add address page on consumer profile not have consumer dropdown");
            }
        assertTrue(crmAddContactInfoPage.addressLineOne.isDisplayed());
        assertTrue(crmAddContactInfoPage.addressLineTwo.isDisplayed());
        assertTrue(crmAddContactInfoPage.city.isDisplayed());
        assertTrue(crmAddContactInfoPage.county.isDisplayed());
        assertTrue(crmAddContactInfoPage.state.isDisplayed());
        assertTrue(crmAddContactInfoPage.zip.isDisplayed());
        assertTrue(crmAddContactInfoPage.addressType.isDisplayed());
        assertTrue(crmAddContactInfoPage.startDate.isDisplayed());
        assertTrue(crmAddContactInfoPage.endDate.isDisplayed());
        assertTrue(crmAddContactInfoPage.saveButton.isDisplayed());
        assertTrue(crmAddContactInfoPage.cancelButton.isDisplayed());

    }

    @Then("I am navigated in Demographic Info tab for view Contact Info")
    public void iAmNavigatedInDemographicInfoTabforviewContactInfo() {
        waitForVisibility(demographicContactInfoPage.contactInfoTab, 10);
        demographicContactInfoPage.contactInfoTab.click();
        waitForVisibility(demographicContactInfoPage.addAddressButton, 10);
        demographicContactInfoPage.addAddressButton.click();
        waitFor(1);
    }

    @And("Verify fields are marked as mandatory")
    public void verifyFieldsAreMarkedAsMandatory(List<String> fields) {
        for (String field : fields) {
            assertTrue(markedMandatory(crmAddContactInfoPage.selectField(field)));
        }
    }

    @And("Verify mandatory fields cannot be left blank")
    public void verifyMandatoryFieldsCannotBeLeftBlank(List<String> fields) {
        crmAddContactInfoPage.saveButton.click();
        waitFor(2);
        scrollUpUsingActions(1);
        for (String field : fields) {
            assertEquals(crmAddContactInfoPage.mandatoryFields(field).getText(), field + " is required and cannot be left blank",
                    "Mandatory field is Fail");
        }
    }

    @And("I select a consumer from dropdown on edit contact info page")
    public void iSelectAConsumerFromDropdownOnEditContactInfoPage() {
        selectRandomDropDownOption(crmAddContactInfoPage.consumer);
    }


}
