package com.maersk.dms.steps;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.step_definitions.OutboundCorrespondenceDefinitionStepDefs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import java.text.ParseException;
import static com.maersk.crm.utilities.BrowserUtils.*;
import static org.testng.Assert.assertTrue;

public class ViewOutboundCorrespondenceSummaryInformationSteps {

    OutboundCorrespondenceDefinitionStepDefs outboundCorrespondenceDefinitionStepDefs = new OutboundCorrespondenceDefinitionStepDefs();


    @When("I click on the details carat of the Correspondence")
    public void i_click_on_the_details_carat_of_the_Correspondence() {
        outboundCorrespondenceDefinitionStepDefs.clickCorrespondenceDetailsBTN();
    }

    @Then("I should see the details of the Correspondence")
    public void i_should_see_the_details_of_the_Correspondence() {
        outboundCorrespondenceDefinitionStepDefs.verifyDetailsOfTheCorrespondence();
    }

    @Then("I should see the Correspondence ID of the Correspondence")
    public void i_should_see_the_Correspondence_ID_of_the_Correspondence() {
        outboundCorrespondenceDefinitionStepDefs.verifyCorrespondenceIDDisplayed();
    }

    @Then("I should see the Correspondence Type of the Correspondence")
    public void i_should_see_the_Correspondence_Type_of_the_Correspondence() {
        outboundCorrespondenceDefinitionStepDefs.verifyCorrespondenceTypeDisplayed();
    }

    @When("I populate only {string} for an internal Case ID")
    public void i_populate_only_for_an_internal_Case_ID(String caseId) {
        outboundCorrespondenceDefinitionStepDefs.populateAndVerifyInternalCaseID(caseId);
    }

    @Given("I have more than one notification with different languages")
    public void i_have_more_than_one_notification_with_different_languages() {
        outboundCorrespondenceDefinitionStepDefs.verifyActiveContactPreferredLanguageIsDisplayed();
    }

    @Then("I should see the Correspondence Language is the same of the first notification")
    public void i_should_see_the_Correspondence_Language_is_the_same_of_the_first_notification() {
        outboundCorrespondenceDefinitionStepDefs.verifyOCLanguageFirstIsTheSame();
    }

    @When("I click on the details carat of the Correspondence from recipients")
    public void i_click_on_the_details_carat_of_the_Correspondence_from_recipients() {
        outboundCorrespondenceDefinitionStepDefs.clickOutboundCorresRecipientCarat();
    }

    @When("I hover over an elipsis at the recipients column")
    public void i_hover_over_an_elipsis_at_the_recipients_column() {
        outboundCorrespondenceDefinitionStepDefs.hoverElipsisRecipientColumn();
    }

    @Then("I should see the recipients of the correspondence")
    public void i_should_see_the_recipients_of_the_correspondence() {
        outboundCorrespondenceDefinitionStepDefs.verifyOCRecipientToRecipientDetails();
    }

    @When("I click on the details carat of the Correspondence from Channel")
    public void i_click_on_the_details_carat_of_the_Correspondence_from_Channel() {
        outboundCorrespondenceDefinitionStepDefs.clickOutboundCorresChannelCarat();
    }


    @When("I hover over an elipsis at the recipients channel")
    public void i_hover_over_an_elipsis_at_the_recipients_channel() {
        outboundCorrespondenceDefinitionStepDefs.hoverElipsisChannelColumn();
    }

    @Then("I should see {string} channels of the correspondence")
    public void i_should_see_the_channels_of_the_correspondence(String channels) {
        outboundCorrespondenceDefinitionStepDefs.verifyElipsisChannelColumn(channels);
    }

    @Then("I should see the status of the Correspondence")
    public void i_should_see_the_status_of_the_Correspondence() {
        outboundCorrespondenceDefinitionStepDefs.verifyOutboundCorresStatusIsDisplayed();
    }

    @Then("I should see the Status Date is displayed in MM\\/DD\\/YYYY format")
    public void i_should_see_the_Status_Date_is_displayed_in_MM_DD_YYYY_format() {
        outboundCorrespondenceDefinitionStepDefs.verifyOutboundCorresDateFormat();
    }

    @Then("I should see the view icon to view the correspondence is present")
    public void i_should_see_the_view_icon_to_view_the_correspondence_is_present() {
        outboundCorrespondenceDefinitionStepDefs.verifyViewIconCorrespondenceIsDisplayed();
    }

    @Then("I should see the sort order of correspondence is most recent first")
    public void i_should_see_the_sort_order_of_correspondence_is_most_recent_first() throws ParseException {
        outboundCorrespondenceDefinitionStepDefs.verifyOutboundCorresSortOrder();
    }

}


