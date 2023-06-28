package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import org.openqa.selenium.By;
import java.util.Map;
import java.util.HashMap;

import static org.testng.Assert.*;

public class CRM_ContactRecordTypesStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();

    private final ThreadLocal<String> phone = ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());

    @When("I create new unidentified contact record for Contact Record Edit")
    public void createNewUnidentifiedContactRecordForContactRecordEdit() {
        waitFor(2);
        contactRecord.selectUnidentifiedContactRecordType.click();
        selectOptionFromMultiSelectDropDown(contactRecord.preferredLanguage, "English");
        selectDropDown(contactRecord.contactReason, "Complaint - Account Access");
        waitFor(3);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Resolved-3");
        waitFor(4);
        contactRecord.reasonsComments.click();
        waitFor(2);
        sendKeyToTextField(contactRecord.reasonsComments,"reason for undentified contact record");
        waitForVisibility(contactRecord.saveReasonButton,10);
        jsClick(contactRecord.saveReasonButton);
        contactRecord.stopContact.click();
        waitFor(2);
        clearAndFillText(contactRecord.phoneNumber, phone.get());
        singleSelectFromMultipleOptionDropDown(contactRecord.inboundCallQueueDropDownSelection, "Eligibility");
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram, "Program A");
        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);

        contactRecord.closeButton.click();
    }

    @And("I search and view results for Contact Record Edit")
    public void searchAndViewResultsForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        contactRecord.contactPhoneField.sendKeys(phone.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(2);
    }

    @Then("I navigate to edit history and I will view the Edited By field")
    public void navigateToEditHistoryAndViewEditedByField() {
        validateUnidentifiedContactRecordSearched();
        editUnidentifiedContactRecord("Adding Contact Reason/Action");
        waitFor(2);
        scrollUpUsingActions(6);
        waitFor(2);
        contactRecord.editContactHistoryButton.click();
        assertTrue(contactRecord.contactRecordEditedByFieldLabel.isDisplayed());
        assertTrue(contactRecord.contactRecordEditedByFieldLabel.getText().contains("EDITED BY"));
    }

    @Then("I navigate to edit history and I will see the first and last name of the User who made the most recent Edit")
    public void navigateToEditHistoryAndViewMostRecentEdit() {
        validateUnidentifiedContactRecordSearched();
        editUnidentifiedContactRecord("Adding Contact Reason/Action");
        editUnidentifiedContactRecord("Adding Contact Reason/Action");
        waitFor(2);
        scrollUpUsingActions(6);
        waitFor(2);
        jsClick(contactRecord.editContactHistoryButton);
        waitForVisibility(contactRecord.contactRecordEditedByNameFirstRow, 10);
        String editedByFirstRow = contactRecord.contactRecordEditedByNameFirstRow.getText();
        String editedByMostRecentRow = contactRecord.contactRecordEditedByNameSecondRow.getText();

        assertTrue(editedByFirstRow.contains(ConfigurationReader.getProperty("loginForCreatedBy")));
        assertTrue(editedByMostRecentRow.contains(ConfigurationReader.getProperty("loginForCreatedBy")));

        String[] split2EditedByNameMostRecent = editedByMostRecentRow.split("\\s+");
        String firstNameMostRecent = split2EditedByNameMostRecent[1];
        String lastNameMostRecent = split2EditedByNameMostRecent[2];

        System.out.println("First Name of Most Recent Edit: "+firstNameMostRecent);
        System.out.println("Last Name of Most recent Edit: "+lastNameMostRecent);
        String[] splitEditedByNameonUI=ConfigurationReader.getProperty("loginForCreatedBy").split("\\s");
        String firstNameOnUI=splitEditedByNameonUI[0];
        String lastNameOnUI=splitEditedByNameonUI[1];
        assertTrue(firstNameMostRecent.contains(firstNameOnUI));
        assertTrue(lastNameMostRecent.contains(lastNameOnUI));
    }

    @Then("I navigate to edit history and I will validate the character length of first and last name of the User who made the most recent Edit")
    public void navigateToEditHistoryAndValidateNameLength() {
        validateUnidentifiedContactRecordSearched();
        editUnidentifiedContactRecord("Adding Contact Reason/Action");
        waitFor(2);
        scrollUpUsingActions(6);
        waitFor(2);
        contactRecord.editContactHistoryButton.click();
        waitForVisibility(contactRecord.contactRecordEditedByNameFirstRow, 10);
        String editedBy = contactRecord.contactRecordEditedByNameFirstRow.getText();

        assertTrue(editedBy.contains(ConfigurationReader.getProperty("loginForCreatedBy")));

        String[] split2EditedByName = editedBy.split("\\s+");
        String firstName = split2EditedByName[0];
        String lastName = split2EditedByName[1];

        System.out.println("firstName: "+firstName);
        System.out.println("lastName: "+lastName);
        assertTrue(firstName.length() < 15);
        assertTrue(lastName.length() < 15);
    }

    private void validateUnidentifiedContactRecordSearched() {
        String contactTypeValue = contactRecord.contactRecordTypeValue.getText();
        assertEquals("Unidentified Contact", contactTypeValue);

        contactRecord.contactIdEditFirstRecord.click();
        waitFor(3);
        String phoneFieldText = contactRecord.unidentifiedContactSearchPhoneField.getText();
        String phoneFieldValueWithoutHyphen = phoneFieldText.replace("-", "");
        String number = phone.get().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        assertEquals(number, phoneFieldText);
    }

    private void editUnidentifiedContactRecord(String editReason) {
        scrollUpRobot();
        highLightElement(contactRecord.editContactButton);
        contactRecord.editContactButton.click();
        waitFor(3);

        if(!contactRecord.contactReason.isDisplayed()){
            Driver.getDriver().findElement(By.xpath("(//span[@class='MuiIconButton-label'])[1]")).click();
        }
        selectDropDown(contactRecord.contactReason,"Complaint - Account Access");
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction,"Resolved-3");
        waitFor(2);
        jsClick(contactRecord.reasonSaveButton);
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.contactRecordReasonForEdit, editReason);
        waitFor(2);
        contactRecord.saveContactRecordEditButton.click();
        waitFor(3);
    }
}