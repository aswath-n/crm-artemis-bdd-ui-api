package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.*;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CRM_OptInOutStepDef extends CRMUtilities implements ApiBase {

    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();


    @When("I select one of the {string} options on Create Consumer Profile Page")
    public void i_select_one_of_the_options_on_Create_Consumer_Profile_Page(String optInOutChannel) {
        waitFor(5);
        createConsumer.selectOptInOutOption(optInOutChannel);

    }

    @Then("I see a Warning message for {string} on Create Consumer Profile Page")
    public void i_see_an_Warning_message_for_on_Create_Consumer_Profile_Page(String channel) {
        createConsumer.verifyOptInOutWarning(channel);
    }

    @When("I provide a valid data for this {string} on Create Consumer Profile Page")
    public void i_provide_a_valid_data_for_this_on_Create_Consumer_Profile_Page(String option) {
        waitFor(2);
        switch (option) {
            case "Cell Phone Number":
                createConsumer.populateOneRelatedField("phoneNumber");
                selectDropDown(createConsumer.phoneType, "Cell");
                break;
            case "Fax Phone Number":
                createConsumer.populateOneRelatedField("phoneNumber");
                selectDropDown(createConsumer.phoneType, "Fax");
                break;
            case "Email Address":
                createConsumer.createConsumerWith("Email");
                break;
            case "Work/Cell/Home Phone Number":
                createConsumer.populateOneRelatedField("phoneNumber");
                selectRandomDropDownOption(createConsumer.phoneType);
                break;
            case "Mailing Address":
                createConsumer.createConsumerWith("Address");
                break;
        }
    }

    @Then("I do not see any Warning message on Create Consumer Profile Page")
    public void i_do_not_see_any_Warning_message_on_Create_Consumer_Profile_Page() {
        assertFalse(isElementDisplayed(createConsumer.optInWarningMessage), "Warning message should not be displayed");
    }


    @When("I select Continue Button on Opt-in warning message")
    public void i_select_Continue_Button_on_this_warning_message() {
       waitForVisibility(createConsumer.optInWarningMessage,10);

        assertTrue(createConsumer.optInWarningContinueButton.isDisplayed(), "Continue button on Warning message is not displayed");
        createConsumer.optInWarningContinueButton.click();
        staticWait(300);
        try {
            createConsumer.optInWarningContinueButton.click();
        } catch (Exception e) {
            System.out.println("Second click after continue button is not needed");
        }
    }


    @When("I select Cancel Button on Opt-in warning message")
    public void i_select_Cancel_Button_on_this_warning_message() {
        waitForVisibility(createConsumer.optInWarningMessage,10);
        assertTrue(createConsumer.optInWarningCancelButton.isDisplayed(), "Cancel button on Warning message is not displayed");
        createConsumer.optInWarningCancelButton.click();
    }

    @Then("I see all required fields are present on on Create Consumer Profile Page")
    public void i_see_all_required_fields_are_present_on_on_Create_Consumer_Profile_Page() {
        assertTrue(createConsumer.consumerAddress1.isDisplayed(), "Address Line 1 field is not displayed");
        assertTrue(createConsumer.consumerPhoneNum.isDisplayed(), "Phone field is not displayed");
        assertTrue(createConsumer.consumerEmail.isDisplayed(), "Email field is not displayed");
    }

    @When("I provide one piece of demographic info not related to this {string} channel")
    public void i_provide_one_piece_of_demographic_info_not_related_to_this_channel(String option) {
        waitFor(2);
        switch (option) {
            case "Text":
                createConsumer.populateOneRelatedField("phoneNumber");
                selectDropDown(createConsumer.phoneType, "Fax");
                createConsumer.createConsumerButton.click();
                break;
            case "Fax":
                createConsumer.populateOneRelatedField("phoneNumber");
                selectDropDown(createConsumer.phoneType, "Cell");
                createConsumer.createConsumerButton.click();
                break;
            case "Email":
                createConsumer.createConsumerWith("Address");
                break;
            case "Phone":
                createConsumer.createConsumerWith("Address");
                break;
            case "Mail":
                createConsumer.createConsumerWith("Email");
                break;
        }

    }
}
