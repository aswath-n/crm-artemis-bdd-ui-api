 package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_EditUnidentifiedContactRecordStepDef extends CRMUtilities implements ApiBase {

    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(() -> new HashMap<>(getNewTestData2()));
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();

    private final ThreadLocal<String> phone = ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());

    @When("I create Unidentified Contact Record for Contact Record Edit")
    public void createUnidentifiedContactRecord() {
        creatNewUnidentifiedContactRecordForContactRecord();
        waitFor(2);
    }

    @And("I click contact search tab for Contact Record Edit")
    public void clickContactSearchTab() {
        waitFor(2);
        dashBoard.contactRecordSearchTabIcon.click();
        waitFor(4);
        //dashBoard.contactRecordSearchTab.click();

    }

    @Then("I am able to see edit button displayed")
    public void ableToSelectToUpdateOrAddInformation() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        contactRecord.contactPhoneField.sendKeys(phone.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecord.contactIdFirstRecord.click();
        waitFor(1);
        assertTrue(contactRecord.editContactButton.isDisplayed());
    }

    @Then("I select the edit button and will be able to edit")
    public void selectTheEditButtonAndEdit() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        contactRecord.contactPhoneField.sendKeys(phone.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecord.contactIdFirstRecord.click();
        waitFor(1);
        editUnidentifiedContactRecordAddingReasonAction("Complaint - Account Access", "Resolved-3");

        scrollUpUsingActions(2);
        contactRecord.editContactHistoryButton.click();
        waitForVisibility(contactRecord.contactRecordEditedByNameFirstRow, 10);

        String editedByFirstRow = contactRecord.contactRecordEditedByNameFirstRow.getText();
        assertTrue(editedByFirstRow.contains("8369"));
        assertEquals(contactRecord.contactRecordEditHistoryReasonForEdit.getText(), "Adding Contact Reason/Action");
    }

    @Then("I select to edit and will be required to select a single Reason for Edit")
    public void selectToEditAndRequiredToSelectASingleReasonForEdit() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        contactRecord.contactPhoneField.sendKeys(phone.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecord.contactIdFirstRecord.click();
        waitFor(1);
        contactRecord.editContactButton.click();
        waitFor(3);

        selectDropDown(contactRecord.contactReason, "Enrollment");
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Plan Change");
        waitFor(2);
        contactRecord.reasonSaveButton.click();
        waitFor(2);

        contactRecord.saveContactRecordEditButton.click();
        assertTrue(contactRecord.reasonForEditRegErrorMessage.isDisplayed(), "Reason for edit error message is not displayed");
    }

    @And("I select to open and edit the contact record")
    public void selectToOpenAndEditTheContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        contactRecord.contactPhoneField.sendKeys(phone.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecord.contactIdFirstRecord.click();
        waitFor(1);
        editUnidentifiedContactRecordAddingReasonAction("Complaint - Account Access", "Resolved-3");
        scrollUpUsingActions(2);
        editUnidentifiedContactRecordAddingReasonAction("Enrollment", "Plan Change");
    }

    @Then("I will be able to view the most recent edit made to the Contact Record")
    public void willBeAbleToViewTheMostRecentEdit() {
        waitFor(2);
        scrollUpUsingActions(6);
        waitFor(2);
        contactRecord.editContactHistoryButton.click();
        waitForVisibility(contactRecord.contactRecordEditedByNameFirstRow, 10);
        String editedByFirstRow = contactRecord.contactRecordEditedByNameFirstRow.getText();
        String editedByMostRecentRow = contactRecord.contactRecordEditedByNameSecondRow.getText();

        assertTrue(editedByFirstRow.contains("8369"));
        assertEquals(contactRecord.contactRecordEditedByFieldLabel.getText(), "REASON FOR EDIT");
        assertEquals(contactRecord.contactRecordEditHistoryReasonForEdit.getText(), "Adding Contact Reason/Action");
        assertTrue(editedByMostRecentRow.contains("8369"));
        assertEquals(contactRecord.contactRecordEditHistoryReasonForEditSecondRow.getText(), "Adding Contact Reason/Action");
        waitFor(1);
    }

    private void creatNewUnidentifiedContactRecordForContactRecord() {
        waitFor(3);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());
        contactRecord.stopContact.click();
        waitFor(2);

        contactRecord.selectUnidentifiedContactRecordType.click();
        //waitForVisibility(contactRecord.unidentifiedContactRecordPageTitle, 10);
        selectDropDown(contactRecord.unidentifiedContactRecordReason, "Complaint - Account Access");
        selectOptionFromMultiSelectDropDown(contactRecord.unidentifiedContactRecordAction, "Resolved-3");
        clearAndFillText(contactRecord.commentArea, "Unidentified Contact Record");
        contactRecord.saveReasonButton.click();
        waitFor(2);
        System.out.println("Phone Value Entered:"+phone.get());
        clearAndFillText(contactRecord.unidentifiedContactRecordPhoneInput, phone.get());
        selectOptionFromMultiSelectDropDown(contactRecord.inboundCallQueueDropDownSelection, "Eligibility");
        selectOptionFromMultiSelectDropDown(contactRecord.preferredLanguage, "English");
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram,"Program A");
        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);
        contactRecord.saveEditContactRecordBtn.click();
    }

    private void editUnidentifiedContactRecordAddingReasonAction(String reason, String action){
        waitFor(2);
        contactRecord.editContactButton.click();
        waitFor(3);

        if(!contactRecord.contactReason.isDisplayed()){
            Driver.getDriver().findElement(By.xpath("(//span[@class='MuiIconButton-label'])[1]")).click();
        }
        selectDropDown(contactRecord.contactReason, reason);
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, action);
        waitFor(2);
        contactRecord.reasonSaveButton.click();
        waitFor(2);

        scrollDownUsingActions(2);
        selectOptionFromMultiSelectDropDown(contactRecord.contactRecordReasonForEdit, "Adding Contact Reason/Action");
        waitFor(2);

        contactRecord.saveContactRecordEditButton.click();
        waitFor(3);
    }

    @When("I start creating  Unidentified Contact Record")
    public void i_start_creating_Unidentified_Contact_Record() {
        contactRecord.selectUnidentifiedContactRecordType.click();
        waitFor(2);
        System.out.println("Phone Value Entered:"+phone.get());
        clearAndFillText(contactRecord.unidentifiedContactRecordPhoneInput, phone.get());
        selectOptionFromMultiSelectDropDown(contactRecord.inboundCallQueueDropDownSelection, "Eligibility");
        selectOptionFromMultiSelectDropDown(contactRecord.preferredLanguage, "English");
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram,"Program A");
        waitFor(2);
    }

    @Then("I edit disposition for above contact record")
    public void i_edit_disposition_for_above_contact_record() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        contactRecord.contactPhoneField.sendKeys(phone.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecord.contactIdFirstRecord.click();
        waitFor(1);

        String editButton = contactRecord.editContactButton.getText();
        assertTrue(editButton.contains("EDIT CONTACT"));

        contactRecord.editContactButton.click();
        waitFor(3);
        selectDropDown(contactHistory.contactDispositionDropdown, "Dropped");
        waitFor(2);
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit,"Correcting Disposition");
        contactHistory.btnSave.click();

    }

    @When("I select above contact record to edit")
    public void select_above_contact_record_to_edit() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        contactRecord.contactPhoneField.sendKeys(phone.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecord.contactIdFirstRecord.click();
        waitFor(1);
        contactRecord.editContactButton.click();
        waitFor(3);
    }

    @Then("I edit disposition for above contact record with {string} Reason and verify edit displayed on Edit History")
    public void i_edit_disposition_for_above_contact_record_with_Reason_and_verify_edit_displayed_on_Edit_History(String editReason) {
        selectDropDown(activeContact.disposition, "Escalate");
        selectOptionFromMultiSelectDropDown(contactRecord.contactRecordReasonForEdit, editReason);
        contactRecord.saveEditContactRecordBtn.click();
        waitFor(2);
        scrollUpUsingActions(2);
        contactRecord.editContactHistoryButton.click();
        waitForVisibility(contactRecord.contactRecordEditedByNameFirstRow, 10);
        String editedByFirstRow = contactRecord.contactRecordEditedByNameFirstRow.getText();
        assertTrue(editedByFirstRow.contains("8369"));
        assertEquals(contactRecord.contactRecordEditedByFieldLabel.getText(), "REASON FOR EDIT");
        assertEquals(contactRecord.contactRecordEditHistoryReasonForEdit.getText(), "Correcting Disposition");
    }
}
