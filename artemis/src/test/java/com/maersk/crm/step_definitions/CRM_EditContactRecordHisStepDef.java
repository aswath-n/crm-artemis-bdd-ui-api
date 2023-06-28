package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class CRM_EditContactRecordHisStepDef extends CRMUtilities implements ApiBase {

    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();

    private final ThreadLocal<String> prefixFirstName = ThreadLocal.withInitial(() -> getRandomString(4));
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private String consumerId;
    private String contactType = "Inbound";
    private String contactChannelType = "Phone";
    private String contactDispositionsValue = "Complete";
    private String contactSearchRecordType = "General";

    @When("I create consumer for Contact Record for Edit Contact Record History")
    public void createConsumerForContactRecord() {
        createNewConsumerForContactRecord(prefixFirstName+getRandomString ( 4 ));
    }

    @And("I click contact search tab for Contact Record History")
    public void iClickContactSearchTabForContactRecordHistory() {
        waitFor(5);
        hover(dashBoard.contactRecordSearchSidebarIcon);
        waitFor(1);
        dashBoard.contactRecordSearchTab.click();

    }

    @And("I select to view the edit information of contact record History")
    public void selectToViewTheEditInformationContactRecordHistory() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        //contactRecord.contactFN.sendKeys(firstName);
        contactRecord.contactFN.sendKeys("Aika");
        waitFor(3);
        contactRecord.searchButton.click();
        waitForVisibility( contactRecord.contactIdFirstRecord,10);
        contactRecord.contactIdFirstRecord.click();
        waitFor(1);
        contactRecord.editContactButton.click();
        waitFor(4);
        scrollDownUsingActions(1);
        selectOptionFromMultiSelectDropDown(contactRecord.contactDispositionButton, "Complete");
        selectOptionFromMultiSelectDropDown(contactRecord.contactReasonForEdit, "Adding Additional Comment");
        waitFor(2);

        scrollUpUsingActions(2);
        selectOptionFromMultiSelectDropDown(contactRecord.editContactReason, "Information Request");
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.editContactAction, "Provided Program Information");
        waitFor(2);
        scrollDownUsingActions(1);
        waitFor(2);
        sendKeyToTextField(contactRecord.editReasComments, "comment");
        waitFor(2);
        contactRecord.saveEditComments.click();
        clearAndFillText(contactRecord.additionalComments, "Edited");
        waitFor(1);
        contactRecord.saveEditAdditionalComments.click();

        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitFor(2);

        String editedExists = contactRecord.editedOn.getText();
        assertEquals(editedExists, "EDITED ON");
        waitFor(2);
    }

    @Then("I am able to select to view the full Edit History for the Contact Record History")
    public void ableToViewTheFullEditHistory() {
        scrollUpUsingActions(2);
        waitFor(1);
        contactRecord.editHistoryButton.click();
        String editedOn = contactRecord.contactEditedOn.getText();
        System.out.println(editedOn);
        assertEquals(editedOn, "EDITED ON");
        waitFor(1);
    }

    @Then("I will see a comprehensive detailed view of edit information of contact record History")
    public void seeDetailedViewOfEditInformationContactRecordHistory() {
        scrollUpUsingActions(2);
        waitFor(1);
        contactRecord.editHistoryButton.click();
        assertNotNull(contactRecord.contactEditedOnValue);
        assertNotNull(contactRecord.contactEditedByName);
        assertNotNull(contactRecord.contactEditedReason);
        assertNotNull(contactRecord.contactEditedField);
        assertNotNull(contactRecord.contactEditedPrevValue);
        assertNotNull(contactRecord.contactEditedCurValue);

        String editedOnValue = contactRecord.contactEditedOnValue.getText();
        editedOnValue = editedOnValue.substring(0, 18);
        System.out.println("Edited On Value after substring:"+editedOnValue);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy '|' HH:mm");
            Date editedOnDate = dateFormat.parse(editedOnValue);
            System.out.println("Edited On Date:"+editedOnDate);
            assertNotNull(editedOnDate);
        } catch (
        ParseException e) {
            System.out.println("Exception occured when parsing the Edited On Date");
        }
    }

    private void createNewConsumerForContactRecord(String firstNameSent) {
        waitFor(3);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());

        clearAndFillText(contactRecord.firstName, firstNameSent);
        clearAndFillText(contactRecord.lastName, lastName.get());
        consumerName.set(firstNameSent + " " + lastName.get());
        System.out.println(firstNameSent + " " + lastName.get());

        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitFor(2);

        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        clearAndFillText(createConsumer.consumerFN, firstNameSent);
        clearAndFillText(createConsumer.consumerLN, lastName.get());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        createConsumer.consumerDB.sendKeys("02/02/1992");
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
//        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
//        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
//        consumerId = contactRecord.lblCrmConsumerId.getText();
        contactRecord.stopContact.click();
        waitFor(2);
        contactRecord.cancelButtonAfterSavingReason.click();
        waitFor(3);
        waitForVisibility(contactRecord.cancelWarningContinueButton, 10);
        contactRecord.cancelWarningContinueButton.click();
    }

    @Then("I will edit call summary field and verify edit is displayed on edit history screen")
    public void i_will_edit_call_summary_field_and_verify_edit_is_displayed_on_edit_history_screen() {
        manualContactRecordSearchPage.editButton.click();
        waitFor(3);
        waitFor(5);
        jsClick(contactRecord.expandAdditionalComments);
        click(contactRecord.editIcon);
        waitFor(5);

        clearAndFillText(contactRecord.editAdditionalCommentText, "Call Summary Edit");
        try {
            click(contactRecord.expandcommentSaveButton);
        } catch (Exception e) {
            Driver.getDriver().switchTo().defaultContent();
            click(contactRecord.expandcommentSaveButton);
        }

        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, "Correcting Call Summary");
        waitFor(4);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitFor(6);
        contactHistory.editHistoryTab.click();
        waitFor(4);
        assertTrue(contactHistory.callSummaryReasonEditHistory.isDisplayed());
        assertEquals(contactHistory.fieldLabelEditHistory.getText(), "Call summary");
        assertEquals(contactHistory.previousValueEditHistory.getText(), "Call Summary");
        assertEquals(contactHistory.updatedValueEditHistory.getText(), "Call Summary Edit");
    }

    @Then("I will edit the saved call summary and the new value is saved and displayed")
    public void i_will_edit_the_saved_call_summary_and_the_new_value_is_saved_and_displayed() {
        waitFor(2);
        scrollDownUsingActions(3);
        waitFor(2);
        scrollDownUsingActions(3);
        waitFor(2);
        manualContactRecordSearchPage.callSummaryEditButton.click();
        clearAndFillText(manualContactRecordSearchPage.callSummaryEditedText, "Edited first call summary");
        manualContactRecordSearchPage.firstCallSummarySavedButton.click();
        assertEquals(getText(manualContactRecordSearchPage.readTextCallSummary), "Edited first call summary");
    }
}
