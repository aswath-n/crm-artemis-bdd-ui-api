package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMConsumerSearchResultPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMThirdPartyContactRecPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.maersk.crm.step_definitions.CRM_ViewContactRecordHistoryStepDef.phoneNumber;
import static org.testng.Assert.assertTrue;

/*
 * Author:Shilpa P
 * Created 09/12/2018
 * */
public class CRM_CloseContactRecordStepDef extends BrowserUtils {
    CRMConsumerSearchResultPage searchResult = new CRMConsumerSearchResultPage();
    CRMContactRecordUIPage crmContactRecordUIPage = new CRMContactRecordUIPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRMThirdPartyContactRecPage thirdParty=new CRMThirdPartyContactRecPage();
    CRM_CreateConsumerProfileStepDef createConsumerProfileStepDef = new CRM_CreateConsumerProfileStepDef();
    CRM_ViewContactRecordHistoryStepDef viewContRecHistory = new CRM_ViewContactRecordHistoryStepDef();
    private ThreadLocal<List<String>> searchResults = ThreadLocal.withInitial(ArrayList::new);
    public String expectedTime;
    public String expectedStopDuration;
    public static ThreadLocal<String> selectedChannel = new ThreadLocal<>();


    /*
     * Author:Shilpa P
     * This method works only for unique First Name and Last Name it clicks on the Link record
     * */
    @And("I click on the {string} record link button")
    public void i_click_on_the_record_link_button(String expected) {
        waitFor(2);
        for (WebElement e : searchResult.FirstNames) {
            searchResults.get().add(e.getText());

        }

        Iterator<WebElement> itr = searchResult.LinkButtons.iterator();
        while (itr.hasNext()) {
            for (String actualFName : searchResults.get()) {
                assertTrue(expected.equalsIgnoreCase(actualFName.substring(0, expected.length())), "First name search result is incorrect");

                WebElement element = itr.next();
                highLightElement(element);
                element.click();

            }
        }
    }

    @When("I click on the close button on the Header")
    public void i_click_on_the_close_button_on_the_header() {
        waitFor(2);
        crmContactRecordUIPage.stopContact.click();
        expectedTime = timeNow();

    }

    @Then("The Timer should be stopped")
    public void the_timer_should_be_stopped() {
      /* String actualTime=crmContactRecordUIPage.contactStart.getText();
       String actaulTimeStandard=actualTime.substring(6,14);
       System.out.print(actaulTimeStandard);*/
        String expectedDuration = crmContactRecordUIPage.contactDurationValue.getText();
        waitFor(5);
        String durationAfterSomeTime = crmContactRecordUIPage.contactDurationValue.getText();
        Assert.assertEquals(durationAfterSomeTime, expectedDuration);

    }

    @And("I click on the Close button in the bottom")
    public void i_click_on_the_close_button_in_the_bottom() {
        waitFor(2);
        crmContactRecordUIPage.closeButton.click();
    }

    @Then("I should see Contact Dispotions should be present")
    public void i_should_see_contact_dispotions_should_be_present() {
        Assert.assertTrue(crmContactRecordUIPage.contactDispositions.isDisplayed());
    }

    @Then("I should see Linked Contact in the Header")
    public void i_should_see_linked_contact_in_the_header() {
        assertTrue(crmContactRecordUIPage.linkedConsumerInContact.isDisplayed());
        highLightElement(crmContactRecordUIPage.linkedConsumerInContact);
    }

    @Then("I click on the Consumer Type {string}")
    public void i_click_on_the_consumer_type(String expected) {
        switch (expected) {
            case "Authorized representative":
                staticWait(1000);
                crmContactRecordUIPage.consumerType.click();
                crmContactRecordUIPage.getElementsConsumerType(2).click();
                break;

            case "Member":
                crmContactRecordUIPage.consumerType.click();
                crmContactRecordUIPage.getElementsConsumerType(3).click();
                break;

            case "Non-Member":
                crmContactRecordUIPage.consumerType.click();
                crmContactRecordUIPage.getElementsConsumerType(4).click();
                break;

            case "Primary Individual":
                crmContactRecordUIPage.consumerType.click();
                crmContactRecordUIPage.getElementsConsumerType(5).click();
                break;

            case "Unverified Contact":
                crmContactRecordUIPage.consumerType.click();
                crmContactRecordUIPage.getElementsConsumerType(6).click();
                break;


        }


    }


    @And("I click on the Contact Type {string}")
    public void i_click_on_the_contact_type(String expected) {
        switch (expected) {
            case "Inbound":
                staticWait(1000);
                selectDropDown(crmContactRecordUIPage.contactType,"Inbound");
                  break;
            case "Outbound":
                waitFor(2);
                selectDropDown(crmContactRecordUIPage.contactType,"Outbound");
                break;
            case "Inbound MultiSelect":
                waitFor(1);
                selectOptionFromMultiSelectDropDownNew(crmContactRecordUIPage.contactType, "Inbound");
                break;
            case "Outbound MultiSelect":
                waitFor(1);
                selectOptionFromMultiSelectDropDownNew(crmContactRecordUIPage.contactType, "Outbound");
                break;
        }
    }

    @And("I click on the Inbound Call queue {string}")
    public void i_click_on_the_inbound_call_queue(String option) {
        selectDropDown(crmContactRecordUIPage.callQueueType, option);

    }

    @And("I click on the Contact Channel Type {string}")
    public void i_click_on_the_contact_channel_type(String option) {
        selectedChannel.set(option);
        selectDropDown(crmContactRecordUIPage.contactChannelType, option);

    }

    @And("I click on the Preffered lanaguage {string}")
    public void i_click_on_the_preffered_language(String expected) {
        switch (expected) {
            case "English":
                staticWait(1000);
                crmContactRecordUIPage.preferredLanguage.click();
                crmContactRecordUIPage.getElementsPrefferedLangauge(2).click();
                break;

            case "Spanish":
                crmContactRecordUIPage.preferredLanguage.click();
                crmContactRecordUIPage.getElementsPrefferedLangauge(3).click();
                break;
        }

    }

    @And("I click on Consumer Authenticated")
    public void i_click_on_consumer_authenticated() {
        staticWait(1000);
        crmContactRecordUIPage.consumerAuthenticate.click();

    }

    @Then("I click on the contact dispotions {string}")
    public void i_click_on_the_contact_dispotions(String expected) {
        waitFor(4);
        selectDropDown(crmContactRecordUIPage.contactDispositions, expected);
       /* switch(expected){
            case "Complete":
                crmContactRecordUIPage.contactDispositions.click();
                crmContactRecordUIPage.getElementsContactDispostions(2).click();
                break;

            case "Dropped":
                crmContactRecordUIPage.contactDispositions.click();
                crmContactRecordUIPage.getElementsContactDispostions(3).click();
                break;

            case "Escalate":
                crmContactRecordUIPage.contactDispositions.click();
                crmContactRecordUIPage.getElementsContactDispostions(4).click();
                break;

            case "Incomplete":
                crmContactRecordUIPage.contactDispositions.click();
                crmContactRecordUIPage.getElementsContactDispostions(5).click();
                break;

            case "OutboundIncomplete":
                staticWait(1000);
                crmContactRecordUIPage.contactDispositions.click();
                crmContactRecordUIPage.getElementsContactDispostions(6).click();
                break;

            case "Requested Call Back":
                crmContactRecordUIPage.contactDispositions.click();
                crmContactRecordUIPage.getElementsContactDispostions(7).click();
                break;

            case "Transfer":
                crmContactRecordUIPage.contactDispositions.click();
                crmContactRecordUIPage.getElementsContactDispostions(8).click();
                break;
        }*/
    }

    @Then("I should see the Hamburger Menu Displayed")
    public void i_should_see_the_hamburger_menu_displayed() {
        Assert.assertTrue(crmContactRecordUIPage.hamBurgerMenu.isDisplayed());
        highLightElement(crmContactRecordUIPage.hamBurgerMenu);

    }

    @And("I scroll the Page to Reasons and comments")
    public void i_scroll_the_page_to_reasons_and_comments() {
        staticWait(5000);
        // crmContactRecordUIPage.clickWrapBoxElement.click();
        //scrollUp();
        System.out.print("Clicked");
        //staticWait(100);
    }

    @And("I scroll the Page to the Bottom")
    public void i_scroll_the_page_to_the_bottom() {
        staticWait(100);
        scrollDown();
    }

    @When("I enter contact phone number {string}")
    public void iEnterContactPhoneNumber(String phone) {
        waitFor(2);
        clearText(crmContactRecordUIPage.phoneNumber);
        crmContactRecordUIPage.phoneNumber.sendKeys(phone);
        phoneNumber.set(phone);
    }

    @When("I select program type {string}")
    public void selectProgramType(String programType) {
        // crmContactRecordUIPage.lstSelectProgram.click();
        waitFor(2);
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.lstSelectProgram, programType);
        //selectDropDown(crmContactRecordUIPage.lstSelectProgram, programType);
    }

    @Then("I verify that dashboard page is displayed")
    public void iVerifyDashBoardScreenDisplayed() {
//        Assert.assertEquals(crmContactRecordUIPage.icnRecordingIcon.getCssValue("color"), "rgba(189, 189, 189, 1)"); this assertion is not valid after design changes 06/06/19
        Assert.assertTrue(crmContactRecordUIPage.initContact.isDisplayed(), "User is not on CP landing page");

    }

    @Then("I verify phone number field is displayed as mandatory")
    public void i_verify_phone_number_field_is_displayed_as_mandatory() {
        assertTrue(crmContactRecordUIPage.phoneNumber.isDisplayed(), "Phone number field is not displayed");
        i_scroll_the_page_to_the_bottom();
        waitFor(3);
        crmContactRecordUIPage.closeButton.click();
        assertTrue(crmContactRecordUIPage.phoneNumberBlankError.isDisplayed(), "Please enter phone number error is not displayed");
    }

    @Then("I verify the format of phone number field")
    public void i_verify_the_format_of_phone_number_field() {
        clearAndFillText(crmContactRecordUIPage.phoneNumber, "1111");
//        i_scroll_the_page_to_the_bottom();
//        waitFor(3);
        crmContactRecordUIPage.closeButton.click();
        assertTrue(crmContactRecordUIPage.phoneNumberIncorrectError.isDisplayed(), "Please enter phone number with 10 characters error is not displayed");
    }

    @Then("I verify email field is displayed as mandatory")
    public void i_verify_email_field_is_displayed_as_mandatory() {
        crmContactRecordUIPage.saveContactRecordButton.click();
        assertTrue(crmContactRecordUIPage.emailID.isDisplayed(), "EmailID field is not displayed");
        crmContactRecordUIPage.closeButton.click();
        assertTrue(crmContactRecordUIPage.emailBlankError.isDisplayed(), "Please enter email error is not displayed");
    }

    @Then("I verify the format of email field")
    public void i_verify_the_format_of_email_field() {
        clearAndFillText(crmContactRecordUIPage.emailID, "     test  @  test.  com");
        crmContactRecordUIPage.closeButton.click();
        assertTrue(crmContactRecordUIPage.emailIncorrectError.isDisplayed(), "Invalid Format error is not displayed");
        clearAndFillText(crmContactRecordUIPage.emailID, "test");
        crmContactRecordUIPage.closeButton.click();
        assertTrue(crmContactRecordUIPage.emailIncorrectError.isDisplayed(), "Invalid Format error is not displayed");
        clearAndFillText(crmContactRecordUIPage.emailID, "test@@test.com");
        crmContactRecordUIPage.closeButton.click();
        assertTrue(crmContactRecordUIPage.emailIncorrectError.isDisplayed(), "Invalid Format error is not displayed");
    }

    @Then("I see General Consumer in Contact header is displayed")
    public void i_see_General_Consumer_in_Contact_header_is_displayed() {

    }

    @Then("I see Associations\\/Links section is displayed")
    public void i_see_Associations_Links_section_is_displayed() {

    }

    @Then("I see {string} consumer information is added to the Link section")
    public void i_see_consumer_information_is_added_to_the_Link_section(String string) {

    }

    @Then("I verify Contact Type radio buttons {string} is displayed")
    public void i_verify_Contact_Type_radio_buttons_is_displayed(String header) {
        switch (header) {
            case "GENERAL CONTACT":
                Assert.assertEquals(thirdParty.thirdPartyGeneralContactHeader.getText(), header);
                break;
            case "THIRD PARTY CONTACT":
                Assert.assertEquals(thirdParty.thirdPartyThirdPartyHeader.getText(), header);
                break;
            case "UNIDENTIFIED CONTACT":
                Assert.assertEquals(thirdParty.thirdPartyUnidentifiedContactHeader.getText(), header);
                break;
            default:
                break;


        }
    }

    @Then("I click on save button to wrap up contact record")
    public void i_click_on_save_button_to_wrap_up_contact_record() {
        waitFor(1);
        crmContactRecordUIPage.closeButton.click();
        waitFor(1);
    }

    @When("I select outcome of contact {string} for outbound")
    public void i_select_outcome_of_contact_for_outbound(String outcome) {
        waitFor(2);
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.outcomeOfContactDropDown, outcome);
    }
    @And("I click on the cancel button in the bottom")
    public void i_click_on_the_cancel_button_in_the_bottom() {
        waitFor(2);
        crmContactRecordUIPage.cancelButton.click();
    }

    @And("I select {string} {string} {string} {string} for active contact selection and save contact record")
    public void iSelectForActiveContactSelectionAndSaveContactRecord(String program, String contactChannelType, String contactType, String data) {
        // "<Program>" "<ContactChannelType>" "<ContactType>" "<Phone Number>"
        i_click_on_the_contact_type(contactType);
        i_click_on_the_contact_channel_type(contactChannelType);
        if ("Phone".equals(contactChannelType) || "SMS Text".equals(contactChannelType)){
            iEnterContactPhoneNumber(data);
        }else if ("Web Chat".equals(contactChannelType) || "Email".equals(contactChannelType)){
            waitFor(3);
            clearAndFillText(crmContactRecordUIPage.contactEmailInput, data);
        }
        createConsumerProfileStepDef.iSelectProgramType(program);
        WebElement cReason = Driver.getDriver().findElement(By.id("mui-component-select-contactReason"));
        selectDropDown(cReason,"Other");
        jsClick(crmContactRecordUIPage.reasonSaveButton);
        waitFor(2);
        selectDropDown(crmContactRecordUIPage.contactDispositions, "Complete");
        waitFor(2);
        crmContactRecordUIPage.stopContact.click();
        waitFor(1);
        crmContactRecordUIPage.closeButton.click();
    }
}