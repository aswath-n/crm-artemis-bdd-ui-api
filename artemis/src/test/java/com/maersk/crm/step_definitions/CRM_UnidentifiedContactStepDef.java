package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMManualContactRecordSearchPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.maersk.crm.pages.crm.CRMUnidentifiedContactPage;
import org.testng.Assert;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_UnidentifiedContactStepDef extends CRMUtilities implements ApiBase {
    CRMUnidentifiedContactPage unidentifiedContact = new CRMUnidentifiedContactPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMManualContactRecordSearchPage contactRecordSearch = new CRMManualContactRecordSearchPage();
    final ThreadLocal<Map<String, String>> newConsumer = new ThreadLocal<>();
    public static final ThreadLocal<String> phoneNumber=ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> email=ThreadLocal.withInitial(String::new);;

    @When("I select {string} Contact Record Type")
    public void i_select_Contact_Record_Type(String value) {
        scrollUpUsingActions(1);
        waitFor(2);
        clickOnContactRecordType(value);
        //        selectDropDown(unidentifiedContact.contactRecordTypeDropDown, value);
//        assertTrue(unidentifiedContact.unidentifiedContactSection.isDisplayed());
    }

    @Then("I verify the following fields are unavailable")
    public void i_verify_the_following_fields_are_unavailable() {
        try {
            unidentifiedContact.consumerFirstNameInput.isDisplayed();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
        try {
            unidentifiedContact.cconsumerLastNameInput.isDisplayed();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
        try {
            unidentifiedContact.consumerMiddleNameInput.isDisplayed();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
        try {
            unidentifiedContact.dobInput.isDisplayed();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
        try {
            unidentifiedContact.ssnVauleInput.isDisplayed();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Then("I verify the default value for {string} {string} {string}")
    public void i_verify_the_default_value_for(String string, String string2, String string3) {
        unidentifiedContact.callerTypeVauleAnonymous.isDisplayed();
        unidentifiedContact.conatactTypeVauleInbound.isDisplayed();
        unidentifiedContact.contactDispositionVauleComplete.isDisplayed();
    }

    @Then("I verify the link button is not displayed")
    public void i_verify_the_link_button_is_not_displayed() {

        try {
            contactRecord.linkRecordButton.isDisplayed();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Then("I see Caller Type is default to {string} and Preferred language dropdown are displayed")
    public void i_see_Caller_Type_is_default_to_and_Preferred_language_dropdown_are_displayed(String string) {
        assertTrue(unidentifiedContact.callerTypeVauleAnonymous.isDisplayed());
        assertTrue(unidentifiedContact.preferredLanguageDropDown.isDisplayed());
    }

    @Then("I verify the field Caller Type is present")
    public void i_verify_the_field_label_caller_type() {
        waitForVisibility(contactRecord.callerTypeLabel, 10);
        assertTrue(contactRecord.callerTypeLabel.isDisplayed());
        assertTrue(contactRecord.callerTypeValue.isDisplayed());

    }

    @When("I enter unidentified cotact details preferred language {string}, contact type {string}, inbound call queue {string} and contactType channel {string}")
    public void i_enter_unidentified_contact_details(String preferredLanguage, String contactType, String inboundCallQueue, String contactTypeChannel) {
        staticWait(1000);
        selectDropDown(unidentifiedContact.preferredLanguageDropDown, preferredLanguage);
        //selectDropDown(unidentifiedContact.contactTypeDropDown, contactType);
        selectDropDown(unidentifiedContact.inboundCallQueueDropDown, inboundCallQueue);
        selectDropDown(unidentifiedContact.contactChannelTypeDropDown, contactTypeChannel);
        switch(contactTypeChannel){
            case "Phone":
            case "SMS Text":
                phoneNumber.set(RandomStringUtils.randomNumeric(10));
                sendKeyToTextField(unidentifiedContact.phoneNumber, phoneNumber.get());
                break;
            case "Email":
            case "Web Chat":
                email.set(RandomStringUtils.randomAlphabetic(10).toLowerCase()+"@"+RandomStringUtils.randomAlphabetic(5).toLowerCase()+".com");
                sendKeyToTextField(unidentifiedContact.email, email.get());
                break;
        }


    }

    @When("I click save button on unidentified contact page")
    public void clickSaveButton() {
        staticWait(1000);
        click(unidentifiedContact.saveButton);
    }

    @When("I click advance search button on contact record search")
    public void clickAdvancedSearchButton() {
        staticWait(1000);
        click(contactRecordSearch.advancedSearchButton);
    }

    @When("I enter search field {string} as {string} and click on search")
    public void enterSearchCriteria(String field, String value) {
        staticWait(1000);
        switch (field){
            case "Phone":
            case "SMS Text":
                if(value.isEmpty())
                    value = phoneNumber.get();
                sendKeyToTextField(contactRecordSearch.phoneNumberTextBox, value);
                break;
            case "Email":
            case "Web Chat":
                if(value.isEmpty())
                    value = email.get();
                sendKeyToTextField(contactRecordSearch.emailTextBox, value);
                break;

        }

        click(contactRecordSearch.searchButton);

    }

    @When("I edit first record from contact record search results")
    public void clickFirstRecordFromSearchResults() {
        staticWait(1000);
        click(contactRecordSearch.searchResultRows.get(0).findElement(By.xpath("./td[3]")));
    }

    @And("I edit the record")
    public void iEditTheRecord(){
        waitForVisibility(unidentifiedContact.editBtn.get(0),10);
        jsClick(unidentifiedContact.editBtn.get(0));
        selectDropDown(unidentifiedContact.reasonForEditDropDown,"Adding Additional Comment");
        click(unidentifiedContact.saveBtn);
    }

    @Then("I will check all headers are in order")
    public void verifyAllHeadersAreInOrder(){
        waitForVisibility(unidentifiedContact.unidentifiedHeader,10);
        Assert.assertTrue(unidentifiedContact.unidentifiedHeader.isDisplayed());
        Assert.assertTrue(unidentifiedContact.contactDetailsHeader.isDisplayed());
        Assert.assertTrue(unidentifiedContact.wrapUpAndCloseHeader.isDisplayed());
        Assert.assertTrue(unidentifiedContact.contactEditReasonHeader.isDisplayed());
    }

    @And("I will verify Contact Details and Edit History tab present")
    public void verifyContactDetailsAndEditHistoryTabPresent(){
        waitForVisibility(unidentifiedContact.contactDetailsTab,10);
        Assert.assertTrue(unidentifiedContact.contactDetailsTab.isDisplayed());
        Assert.assertTrue(unidentifiedContact.editHistoryTab.isDisplayed());
    }

    @And("I will verify Edit Contact button displayed at page level")
    public void verifyEditContactButtonDisplayedAtPageLevel(){
        Assert.assertTrue(unidentifiedContact.editBtn.size()==1);
    }
}
