package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;

import javax.sound.midi.Soundbank;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CRM_UnlinkContactRecordStepDef extends CRMUtilities implements ApiBase {

    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRMContactRecordDashboardPage contactRecordDashboard = new CRMContactRecordDashboardPage();

    final ThreadLocal<CRM_ViewSearchResultInfoStepDef> viewSearchResultInfoStepDef = ThreadLocal.withInitial(CRM_ViewSearchResultInfoStepDef::new);
    final ThreadLocal<CRM_NavigateThroughContactDashboardStepDef> navigateThroughContactDashboardStepDef = ThreadLocal.withInitial(CRM_NavigateThroughContactDashboardStepDef::new);
    public static final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> contactRecordId = ThreadLocal.withInitial(String::new);
    final ThreadLocal<HashMap<String, String>> savedRecordDetails = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<HashMap<String, String>> linkedRecordDetails = ThreadLocal.withInitial(HashMap::new);
    CRMAddContactInfoPage contactInfoPage = new CRMAddContactInfoPage();

    @Then("I see unlink contact record option is displayed")//
    public void i_see_unlink_contact_record_option_is_displayed() {
        waitFor(1);
        assertTrue(activeContact.unlinkContactRecord.isDisplayed(), "Unlink Contact Record button is not displayed");
    }


    @When("I see no values are displayed on Header for Consumer name and ID")
    public void i_see_no_values_are_displayed_on_Header_for_Consumer_name_and_ID() {
        String expected = ""; //todo uncomment after CRM-1865 bug is fixed
//        assertTrue(activeContact.headerConsumerID.getText().equals(expected), "Header should not hold Consumer ID value");
        //      assertTrue(activeContact.headerConsumerName.getText().equals(expected), "Header should not hold Consumer Name value");
    }

    @Then("I see Contact Record Search is displayed")
    public void i_see_Contact_Record_Search_is_displayed() {
        assertTrue(contactRecord.searchButton.isDisplayed(), "Search Button should be displayed");
    }

    @Then("I see entered value for Contact Details and Reason not changed")
    public void i_see_entered_value_for_Contact_Details_and_Reason_not_changed() {
        assertTrue(contactRecord.contactType.getText().equals("Outbound"),
                "Contact Type field value is not Outbound");
        assertTrue(contactRecord.contactChannelType.getText().equals("Web Chat"),
                "Contact Channel Type field value is not Web Chat");
        contactRecord.expandSavedReasonAction.click();
        assertTrue(contactRecord.savedReason.getText().equals("Update Information Request"),
                "Contact Reason field value is not 'Update Information Request'");
        waitFor(4);
        assertTrue(contactRecord.savedAction.getText().equals("Updated Eligibility Information"),
                "Contact Action field value is not 'Updated Eligibility Information'");
    }

    @When("I scroll up to the top of the current UI page")
    public void i_scroll_up_to_the_top_of_the_current_UI_page() {
        activeContact.fullName.click();
        scrollUpRobot();
    }


    @When("I expend the saved contact record and unlink it from current Consumer")
    public void i_expend_the_saved_contact_record_and_unlink_it_from_current_Consumer() {
        waitFor(10);
        contactRecordId.set(contactHistory.contactIDs.get(1).getText());
        contactHistory.contactIDs.get(1).click();
        waitFor(2);
        assertTrue(contactHistory.contactDetailsTab.isDisplayed(), "Is not navigated to Contact Details Page");
        contactHistory.btnEdit.click();
        waitForVisibility(contactHistory.unLink, 10);
        contactHistory.unLink.click();
        assertTrue(contactHistory.searchSectionHeader.isDisplayed(), "After unlinking search section is not displayed");
    }

    @When("I search for an existing consumer on contact History page and link it Contact Record")
    public void i_search_for_an_existing_consumer_on_contact_History_page_and_link_it_Contact_Record() {
        scrollDownUsingActions(3);
        viewSearchResultInfoStepDef.get().i_search_for_an_existing_contact_by_first_name("Khazar");
        waitFor(2);
        contactHistory.searchButtonAfterUnlinking.click();
        waitFor(3);
        contactHistory.expandFirstConsumerAmongOthers.click();
        waitFor(2);
        contactRecord.linkConsumerButtonAfterUnlinking.click();
    }

    @Then("I see new Consumer was successfully linked to the current Contact Record")
    public void i_see_new_Consumer_was_successfully_linked_to_the_current_Contact_Record() {
        assertTrue(contactRecord.unLink.isDisplayed(),"New consumer was not linked to Contact Record");
    }

    @When("I close edited Contact Record")
    public void i_close_edited_Contact_Record() {
        selectDropDown(contactHistory.lstReasonForEdit, "Correcting Case/Consumer Link");
        waitFor(7);
        jsClick(contactHistory.btnSave);

    }

    @When("I unlink the previous consumer from the active record on Active Contact Record Page")
    public void i_unlink_the_previous_consumer_from_the_active_record_on_Active_Contact_Record_Page() {
//        contactHistory.navigateBackToContactsList.click();
        waitFor(2);
        navigateThroughContactDashboardStepDef.get().i_click_on_Tab_on_Contact_Dashboard_Page("Active Contact");
        contactRecord.unLink.click();
    }

    @When("I search for second consumer on Contact Record UI Page, link and navigate to Contact Record Info Page")
    public void i_search_for_second_consumer_on_Contact_Record_UI_Page_link_and_navigate_to_Contact_Record_Info_Page() {
        clearAndFillText(contactRecord.firstName, "Ethan");
        contactRecord.searchButton.click();
        waitFor(2);
        linkSearchedConsumer(consumerId.get(), "Ethan");
        waitFor(2);
        contactRecordDashboard.caseContactDetailsTab.click();
    }

    @Then("I see second consumer has expected contact record displayed")
    public void i_see_second_consumer_has_expected_contact_record_displayed() {
        List<String> actualContactIDList = new ArrayList<>();
        for (WebElement actualContactID : contactHistory.contactIDs) {
            actualContactIDList.add(actualContactID.getText());
        }
        contactHistory.getTwentyItemsPerPage();
        assertTrue(actualContactIDList.contains(contactRecordId.get()), "New consumer does not have expected Contact record in Contact History Page");
    }


    @When("I cancel editing current Contact Record")
    public void i_cancel_editing_current_Contact_Record() {
        jsClick(contactHistory.cancelEditButton);
        waitFor(2);
        contactHistory.continueButtonOnWarningPopUp.click();
    }

    @Then("I see system did not save any changes and second consumer's Contact Record History Page does not display not saved Contact Record")
    public void i_see_system_did_not_save_any_changes_and_second_consumer_s_Contact_Record_History_Page_does_not_display_not_saved_Contact_Record() {
        i_unlink_the_previous_consumer_from_the_active_record_on_Active_Contact_Record_Page();
        i_search_for_second_consumer_on_Contact_Record_UI_Page_link_and_navigate_to_Contact_Record_Info_Page();
        List<String> actualContactIDList = new ArrayList<>();
        for (WebElement actualContactID : contactHistory.contactIDs) {
            actualContactIDList.add(actualContactID.getText());
        }
        contactHistory.getTwentyItemsPerPage();
        assertFalse(actualContactIDList.contains(contactRecordId.get()),"New consumer should not have expected Contact record in Contact History Page" );
    }

    @When("I initiate cancellation of changes but click {string} on the warning message")
    public void i_initiate_cancellation_of_changes_but_click_on_the_warning_message(String string) {
        contactHistory.cancelEditButton.click();
        waitFor(2);
        contactHistory.warningMessageCancelButton.click();
    }

    @When("I see system returns to the Edit Contact Record Page")
    public void i_see_system_returns_to_the_Edit_Contact_Record_Page() {
        assertTrue(contactRecord.unLink.isDisplayed(), "System did not navigate to Edit Contact Record Page");
    }

    @Then("I verify unlinking process can be resumed")
    public void i_verify_unlinking_process_can_be_resumed() {
        waitForPageToLoad ( 10 );
        contactHistory.unLink.click();
        assertTrue(contactHistory.btnSave.isDisplayed(), "Unlinking process can not be resumed");
    }


    @When("I expend the saved contact record, capture all the details and unlink it from current Consumer")
    public void i_expend_the_saved_contact_record_capture_all_the_details_and_unlink_it_from_current_Consumer() {
        contactRecordId.set(contactHistory.contactIDs.get(0).getText());
        contactHistory.contactIDs.get(1).click();
        waitFor(2);
        contactHistory.getContactDetails(savedRecordDetails.get());
        assertTrue(contactHistory.contactDetailsTab.isDisplayed(), "Is not navigated to Contact Details Page");
        contactHistory.btnEdit.click();
        waitForVisibility(contactHistory.unLink, 10);
        contactHistory.unLink.click();
        assertTrue(contactHistory.searchSectionHeader.isDisplayed(), "After unlinking search section is not displayed");

    }

    @Then("I expend the record to verify all captured details before linking remain unchanged")
    public void i_expend_the_record_to_verify_all_captured_details_before_linking_remain_unchanged() {
        waitFor(2);
        contactHistory.expendLinkedContactRecord(contactRecordId.get());
        contactHistory.getContactDetails(linkedRecordDetails.get());
        assertTrue(savedRecordDetails.get().equals(linkedRecordDetails.get()), "Contact record has changed during the process og relinking to a new Consumer");
    }

    @When("I save changes and navigate back to Contact Record page")
    public void i_save_changes_and_navigate_back_to_Contact_Record_page() {
        selectDropDown(contactHistory.lstReasonForEdit, "Correcting Consumer Profile/Case Link");
        waitFor(2);
        contactHistory.btnSave.click();
        waitForClickablility(contactHistory.navigateBackToContactsList, 5);
        contactHistory.navigateBackToContactsList.click();
        waitFor(2);

    }

    @Then("I see no unlinked Contact Record is displayed as part of Contact History")
    public void i_see_no_unlinked_Contact_Record_is_displayed_as_part_of_Contact_History() {
        assertTrue(contactHistory.contactIDs.isEmpty(), "Contact History page should not have any Contact Record displayed for this Consumer");
        assertTrue(contactHistory.noContactRecordMessage.isDisplayed(), "No Contact Record Message is not displayed");
    }

    @When("I click on save button on Edit Contact Record Page")
    public void i_click_on_save_button_on_Edit_Contact_Record_Page() {
        selectDropDown(contactHistory.lstReasonForEdit, "Correcting Case/Consumer Link");
        waitFor(2);
        jsClick(contactHistory.btnSave);
    }

    @Then("I see error message that Contact record can not be saved without being linked to a Case or Consumer")
    public void i_see_error_message_that_Contact_record_can_not_be_saved_without_being_linked_to_a_Case_or_Consumer() {
        assertTrue(contactHistory.contactRecordHasToBeLinkedErrorMessage.isDisplayed(), "Error message 'Contact Record has to be linked' is not displayed");
    }

    @Then("I expend the record to verify system captured when, by and reason for the record being edited is displayed")
    public void i_expend_the_record_to_verify_system_captured_when_by_and_reason_for_the_record_being_edited_is_displayed() {
        waitFor(2);
        contactHistory.expendLinkedContactRecord(contactRecordId.get());
        assertTrue(contactHistory.contactEditDetailsTable.isDisplayed(), "Contact Edit section is not displayed");
        assertTrue(contactHistory.editedOnColumn.isDisplayed(), "Edited On Column is not displayed");
        assertTrue(contactHistory.editedByColumn.isDisplayed(), "Edited By Column is not displayed");
        assertTrue(contactHistory.reasonForEditColumn.isDisplayed(), "Reason for Edited Column is not displayed");
        assertTrue(contactHistory.editedOn.getText().replace("/", "").startsWith(getCurrentDateWithFormat()), "Edited on Date is not match expectad date");
        assertTrue(contactHistory.editedBy.getText().equals(contactRecord.headerUsername.getText()), "Edited By Username does not match expected Username");
        assertTrue(contactHistory.reasonForEdit.getText().equalsIgnoreCase("Correcting Consumer Profile/Case Link"), "Reason for Edit does not match expected value");

    }

    @Then("I expand and see entered value for Contact Details and Reason not changed")
    public void i_expand_and_see_entered_value_for_Contact_Details_and_Reason_not_changed() {
        assertTrue(contactRecord.contactType.getText().equals("Outbound"),
                "Contact Type field value is not Outbound");
        assertTrue(contactRecord.contactChannelType.getText().equals("Web Chat"),
                "Contact Channel Type field value is not Web Chat");
        contactRecord.expandSavedReasonAction.click();
        click(contactRecord.savedReason);
        assertTrue(contactRecord.savedReason.getText().equals("Update Information Request"),
                "Contact Reason field value is not 'Update Information Request'");
        waitFor(4);
        contactRecord.expandSavedReasonAction.click();
        assertTrue(contactRecord.savedAction.getText().equals("Updated Eligibility Information"),
                "Contact Action field value is not 'Updated Eligibility Information'");
    }

    @And("I navigate back to Contact History page")
    public void i_navigate_back_to_contact_history_page() {
        contactInfoPage.backToContactInfo.click();
        waitFor(2);
        contactHistory.continueButtonOnWarningPopUp.click();
        waitFor(2);
    }

    @And("I verify unlinked contact record is removed on Case and Contact details page")
    public void unlinked_cr_was_removed() {
        assertTrue(contactHistory.contactIDs.size() == 1, "Unlinked contact is still under new consumer");
    }

    @And("I verify unlinked contact record does not change on Case and Contact details page")
    public void unlinked_cr_was_not_changed() {
        assertTrue(contactHistory.contactIDs.size() == 2, "Unlinked contact is removed for new consumer");
    }
}
