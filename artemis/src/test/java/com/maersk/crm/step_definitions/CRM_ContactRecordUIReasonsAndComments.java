package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static com.maersk.crm.utilities.ApiBaseClass.contactRecord;
import static com.maersk.crm.utilities.Driver.*;
import static org.testng.Assert.*;

public class CRM_ContactRecordUIReasonsAndComments extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage crmContactRecordUIPage = new CRMContactRecordUIPage();
    CRMDemographicContactInfoPage crmDemographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRMDashboardPage crmDashboardPage = new CRMDashboardPage();
    //    CRM_Edit_ContactRecord_Page crmEditContactRecordPage = new CRM_Edit_ContactRecord_Page();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    //    private CRMUtilities crmUtilities = new CRMUtilities();
    public final String comments = "This text verifies text limit of 500 characters including numbers-1234567890, special characters-`~1234567890-=[]\\;’,./~!@#$%^&*()_+{}|:”<>? This text verifies text limit of 500 characters including numbers-1234567890, special characters-`~1234567890-=[]\\;’,./~!@#$%^&*()_+{}|:”<>?This text verifies text limit of 500 characters including numbers-1234567890, special characters-`~1234567890-=[]\\;’,./~!@#$%^&*()_+{}|:”<>? This text verifies text limit of 500 characters including numbers-1234567890.!";
    public static final ThreadLocal<String> reasonActionUpdatedValue = ThreadLocal.withInitial(String::new);

    @And("I click on Reasons dropdown")
    public void i_click_on_reasons_dropdown() {
        Assert.assertTrue(crmContactRecordUIPage.expendReasonButton.isDisplayed());
        crmContactRecordUIPage.expendReasonButton.click();
        waitFor(5);
    }

    @And("I should see all the attributes present in the Reasons Section")
    public void i_should_see_all_the_attributes_present_in_the_reasons_section() {
        Assert.assertTrue(crmContactRecordUIPage.contactReason.isDisplayed());
        Assert.assertTrue(crmContactRecordUIPage.contactAction.isDisplayed());
        Assert.assertTrue(crmContactRecordUIPage.reasonsComments.isDisplayed());
    }


    @And("I Enter valid data")
    public void i_enter_valid_data_and_click_on_the_save_button() {

        //waitForVisibility(crmContactRecordUIPage.reasonsComments,2);
        //Wait for Visibility Did not work
        staticWait(1000);
        crmContactRecordUIPage.reasonsComments.click();
        sendKeyToTextField(crmContactRecordUIPage.reasonsComments, comments);
        waitForVisibility(crmContactRecordUIPage.reasonSaveButton, 10);


    }

    @And("I click on the save button")
    public void i_click_on_the_save_button() {
        crmContactRecordUIPage.saveReasonButton.click();
    }


    @And("I Enter valid data and click on the cancel button")
    public void i_enter_valid_data_and_click_on_the_cancel_button() {
        staticWait(1000);
        crmContactRecordUIPage.reasonsComments.click();
        sendKeyToTextField(crmContactRecordUIPage.reasonsComments, comments);
        waitForVisibility(crmContactRecordUIPage.saveReasonButton, 30);
        crmContactRecordUIPage.cancelButtonReason.click();

    }

    //Refactored -12/14 ; commented lines 85 &86 , old locator reasonsDisplayed was used for reasons and additional comments, created separate locators
    @Then("I should see the Time stamp should be displayed with the comments")
    public void i_should_see_the_time_stamp_should_be_displayed_with_the_comments() {
        String actualTime = "chat " + getCurrentDate();
        waitForVisibility(crmContactRecordUIPage.timeStamp, 10);
        highLightElement(crmContactRecordUIPage.timeStamp);
        assertTrue(crmContactRecordUIPage.timeStamp.isDisplayed(), "Contact Reason dropdown is not displayed");
        String expectedTime = crmContactRecordUIPage.timeStamp.getText();
        System.out.print(expectedTime);
        System.out.println(actualTime);
        highLightElement(crmContactRecordUIPage.readTextContactReason);
        Assert.assertTrue(crmContactRecordUIPage.readTextContactReason.isDisplayed());
    }

    @Then("I should see the Time stamp should be displayed with the additional comments")
    public void i_should_see_the_Time_stamp_should_be_displayed_with_the_additional_comments() {
        waitForVisibility(crmContactRecordUIPage.timeStampforAdditionalComments, 10);
        assertTrue(crmContactRecordUIPage.timeStampforAdditionalComments.isDisplayed(), "Additional Comment dropdown is not displayed");
        System.out.println(crmContactRecordUIPage.timeStampforAdditionalComments.getText());
        String actualTime = crmContactRecordUIPage.timeStampforAdditionalComments.getText();
        String expectedTime = crmContactRecordUIPage.timeStamp.getText();
        System.out.println("expectedTime=" + expectedTime);
        System.out.println("actual=" + actualTime);
        Assert.assertTrue(crmContactRecordUIPage.readTextAdditionalComments.isDisplayed());
        assertTrue(actualTime.contains(expectedTime));
    }

//    @Then("I should see the Time stamp should be displayed with the additional  comments")
//    public void i_should_see_the_time_stamp_should_be_displayed_with_the_additional_comments() {
//        String actualTime = "chat " + getCurrentDate();
//        waitForVisibility(crmContactRecordUIPage.timeStamp, 10);
//        highLightElement(crmContactRecordUIPage.timeStamp);
//        assertTrue(crmContactRecordUIPage.timeStamp.isDisplayed(), "Contact Reason dropdown is not displayed");
//        String expectedTime = crmContactRecordUIPage.timeStamp.getText();
//        System.out.print(expectedTime);
//        System.out.println(actualTime);
//        highLightElement(crmContactRecordUIPage.savedAdditionalComments);
//        assertTrue(crmContactRecordUIPage.savedAdditionalComments.isDisplayed());
//
//    }

    @And("I click on the Additional Comments")
    public void i_click_on_the_additional_comments() {
        waitFor(1);
        crmContactRecordUIPage.expendAdditionalCommentsButton.click();
        waitFor(1);
    }

    @And("I click on Continue button")
    public void i_click_on_continue_button() {
        staticWait(100);
        crmContactRecordUIPage.continueButtonReasons.click();


    }

    @And("I click on Cancel button")
    public void i_click_on_cancel_button() {
        staticWait(100);
        crmContactRecordUIPage.cancelButtonReasons.click();

    }

    @Then("I should see empty field in comments")
    public void i_should_see_empty_field_in_comments() {
        waitForVisibility(crmContactRecordUIPage.reasonsComments, 10);
        assertTrue(textIsNotPresent(comments), "Text is not present");

    }

    @Then("I should see the text present in comments")
    public void i_should_see_the_text_present_in_comments() {
        waitForVisibility(crmContactRecordUIPage.reasonsComments, 10);
        Assert.assertFalse(textIsNotPresent(comments), "Text is present");
    }


    @Then("I should be able to save the additional comments")
    public void i_should_be_able_to_save_the_additonal_comments() {

        crmContactRecordUIPage.saveAdditionalComments.click();


    }

    @And("I Enter Valid  additional Comments")
    public void i_enter_valid_comments() {
        staticWait(1000);
        crmContactRecordUIPage.additionalCommentsTextBox.click();
        clearAndFillText(crmContactRecordUIPage.additionalCommentsTextBox, comments);

    }

    @Then("I should see a new  window with Information Lost message")
    public void i_should_see_a_new_window_with_information_lost_message() {
        staticWait(100);
        waitFor(3);
        highLightElement(crmContactRecordUIPage.informationLostMessage);
        assertTrue(crmContactRecordUIPage.informationLostMessage.isDisplayed(), "The Information Lost Message is not Displayed ");

    }

    //refactoring/smoke adjustment 11/27/18
    @When("I expand the button of the saved comments")
    public void i_expand_the_button_of_the_saved_comments() {
        waitFor(2);
        click(crmContactRecordUIPage.expandAdditionalComments);
        //Not working belwo locator
        // crmContactRecordUIPage.expandSavedComments.click();
    }

    @Then("I should see the comments displayed")
    public void i_should_see_the_comments_displayed() {
        assertTrue(crmContactRecordUIPage.savedCommentsText.isDisplayed(), "Comments are Displayed");
    }

    @Then("I should see the Additional comments displayed")
    public void i_should_see_the_additional_comments_displayed() {
        assertTrue(crmContactRecordUIPage.savedAdditionalComments.isDisplayed());
    }


    @When("I create new reason {string} with actions")
    public void i_create_new_reason_with_actions(String option, List<String> actions) {
//        crmContactRecordUIPage.expendReasonButton.click();
        selectDropDown(crmContactRecordUIPage.contactReason, option);
        for (int i = 0; i < actions.size(); i++) {
            //   singleSelectFromMultipleOptionDropDown(crmContactRecordUIPage.contactAction, actions.get(i));
            selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.contactAction, actions.get(i));
        }
    }

    @Then("I Enter valid data and click on the save button")
    public void i_Enter_valid_data_and_click_on_the_save_button() {
        sendKeyToTextField(crmContactRecordUIPage.reasonsComments, "This text verifies text limit of 500 characters including numbers-1234567890, special characters-`~1234567890-=[]\\;’,./~!@#$%^&*()_+{}|:”<>? This text verifies text limit of 500 characters including numbers-1234567890, special characters-`~1234567890-=[]\\;’,./~!@#$%^&*()_+{}|:”<>?This text verifies text limit of 500 characters including numbers-1234567890, special characters-`~1234567890-=[]\\;’,./~!@#$%^&*()_+{}|:”<>? This text verifies text limit of 500 characters including numbers-1234567890.!");
        waitForVisibility(crmContactRecordUIPage.saveReasonButton, 1000);
        click(crmContactRecordUIPage.saveReasonButton);
        waitFor(3);
    }

    @Then("I edit the saved {string} and {string}")
    public void i_edit_the_saved_and(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        jsClick(crmContactRecordUIPage.expandreasonsComments);
        waitForClickablility(crmContactRecordUIPage.editIcon, 5);
        jsClick(crmContactRecordUIPage.editIcon);

        click(crmContactRecordUIPage.expendcontactReasonDropDown);
        click(crmContactRecordUIPage.expendcontactReasonVauleInformationRequest);
        waitFor(3);
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.expendcontactActionDropDown, "Provided Financial Information");
        sendKeyToTextField(crmContactRecordUIPage.editReasonsComments, " Updated");

    }

    @Then("I verify reason and actions are edited")
    public void i_verify_reason_and_actions_are_edited() {
        try {
            click(crmContactRecordUIPage.expandreasonSaveButton);
        } catch (Exception e) {
            getDriver().switchTo().defaultContent();
            click(crmContactRecordUIPage.expandreasonSaveButton);
        }
    }

    @Then("I verify edited reason and actions are displayed")
    public void i_verify_edited_reason_and_actions_are_displayed() {
        try {
            click(crmContactRecordUIPage.expandreasonSaveButton);
        } catch (Exception e) {
            getDriver().switchTo().defaultContent();
            click(crmContactRecordUIPage.expandreasonSaveButton);
        }
        waitFor(2);
        assertEquals(getText(crmContactRecordUIPage.readTextContactReason), "Information Request");
        assertEquals(getText(crmContactRecordUIPage.readTextContactAction), "Provided Financial Information");
        assertTrue(getText(crmContactRecordUIPage.readTextComments).contains("Update"));
    }

    @Then("I verify all select actions are displayed")
    public void i_verify_all_select_actions_are_displayed() {
        try {
            click(crmContactRecordUIPage.viewActionTextInformationRequest);
        } catch (Exception e) {
            getDriver().switchTo().defaultContent();
            click(crmContactRecordUIPage.viewActionTextInformationRequest);
        }
        waitFor(2);
        List<String> messageList = new ArrayList<String>();
        String getText = get_not_found_selected_action("Provided Eligibility Status/Information");
        if (getText != null) {
            messageList.add(getText);
            getText = null;
        }
        getText = get_not_found_selected_action("Provided Program Information");
        if (getText != null) {
            messageList.add(getText);
            getText = null;
        }
        getText = get_not_found_selected_action("Provided Enrollment Status/Information");
        if (getText != null) {
            messageList.add(getText);
            getText = null;
        }
        getText = get_not_found_selected_action("Provided Appeal Information");
        if (getText != null) {
            messageList.add(getText);
            getText = null;
        }
        getText = get_not_found_selected_action("Provided Case Status/Information");
        if (getText != null) {
            messageList.add(getText);
            getText = null;
        }
        getText = get_not_found_selected_action("Provided Financial Information");
        if (getText != null) {
            messageList.add(getText);
            getText = null;
        }
        if (!messageList.isEmpty()) {
            for (int i = 0; i < messageList.size(); i++) {
                System.out.println(messageList.get(i).toString());
            }
            assertTrue((messageList.isEmpty()), "All Actions are not displayed.");
        }

    }


    private String get_not_found_selected_action(String expectedText) {
        boolean action_found = false;
        String getActionValue = null;
        for (int i = 1; i < 7; i++) {
            getActionValue = getText(crmContactRecordUIPage.getElementsContactAction(i));
            if (getActionValue.contains(expectedText)) {
                System.out.println("Action Found : " + getActionValue);
                action_found = true;
                break;
            }
        }
        if (action_found != true) {
            return "Expected Value '" + expectedText + "' is not found.\r";
        } else {
            return null;
        }
    }

    @Given("I click on Save button for additional comments")
    public void i_click_on_Save_button_for_additional_comments() {

        try {
            click(crmContactRecordUIPage.saveAditionalCommentsButton);
        } catch (Exception e) {
            click(crmContactRecordUIPage.saveAdditionalComments);
        }//catch(Exception e){click(crmContactRecordUIPage.savedAdditionalComments);}

    }

    @When("I edit the saved Additional comments")
    public void i_edit_the_saved_Additional_comments() {
        click(crmContactRecordUIPage.editAdditionalCommentsIcon);
        waitFor(5);

        clearAndFillText(crmContactRecordUIPage.editAdditionalCommentText, "Updated");
        try {
            click(crmContactRecordUIPage.expandcommentSaveButton);
        } catch (Exception e) {
            getDriver().switchTo().defaultContent();
            click(crmContactRecordUIPage.expandcommentSaveButton);
        }
    }

    @And("I edit the saved Additional comments after saving reason action")
    public void i_edit_the_saved_Additional_comments_after_saving_reason_action() {
        click(crmContactRecordUIPage.editAdditionalCommentsIconSecond);
        waitFor(5);

        clearAndFillText(crmContactRecordUIPage.editAdditionalCommentText, "Updated");
        try {
            click(crmContactRecordUIPage.expandcommentSaveButton);
        } catch (Exception e) {
            getDriver().switchTo().defaultContent();
            click(crmContactRecordUIPage.expandcommentSaveButton);
        }
    }

    @Then("I verify Additional comments are edited")
    public void i_verify_Additional_comments_are_edited() {
        waitFor(2);
        System.out.println(getText(crmContactRecordUIPage.readTextAdditionalComments));
        assertTrue(getText(crmContactRecordUIPage.readTextAdditionalComments).endsWith("Updated"));
    }

    @Then("I verify edited Additional comments are displayed")
    public void i_verify_edited_Additional_comments_are_displayed() {
        waitFor(2);
        System.out.println(getText(crmContactRecordUIPage.readTextAdditionalComments));
        assertTrue(getText(crmContactRecordUIPage.readTextAdditionalComments).endsWith("Updated"));
    }

    //Added  01/02 for missed TC 636
    @Then("I verify reason is not saved without action")
    public void reason_not_saved_without_action() {
        waitFor(2);
        assertTrue(crmContactRecordUIPage.contactActionError.isDisplayed(), "Reason Saved without Actions");
        crmContactRecordUIPage.saveReasonButton.click();
        waitFor(2);
        assertEquals(true, IsElementDisplayed(crmContactRecordUIPage.expandreasonsComments), "Reason Saved without action");

    }

    @Then("I verify I am able to see the delete button \\(trash can icon) for reason&action")
    public void i_verify_I_am_able_to_see_the_delete_button_trash_can_icon_for_reason_action() {
        waitFor(3);
        crmContactRecordUIPage.deleteReasonAction.isDisplayed();

    }

    @Then("I verify when I hover over trash icon I see text \"Delete Reason”")
    public void i_verify_when_I_hover_over_trash_icon_I_see_text_Delete_Reason() {
        String hoverOverDeleteReasonText = crmContactRecordUIPage.deleteReasonAction.getAttribute("title");
        System.out.println(hoverOverDeleteReasonText);
        assertEquals(hoverOverDeleteReasonText, " Delete Reason");
    }

    @Then("I am able to delete above reason&action by clicking trash icon and clicking Continue")
    public void i_am_able_to_delete_above_reason_action_by_clicking_trash_icon_and_clicking_Continue() {
        waitFor(3);
        crmContactRecordUIPage.deleteReasonAction.click();
        waitForVisibility(crmContactRecordUIPage.deleteReasonActionContinueNew, 3);
        jsClick(crmContactRecordUIPage.deleteReasonActionContinueNew);
        waitFor(2);
    }

    @Then("I verify above reason {string} is deleted and not displaying on UI")
    public void i_verify_above_reason_is_deleted_and_not_displaying_on_UI(String reasonText) {
        waitFor(2);
        try {
            assertFalse(isElementDisplayed(crmContactRecordUIPage.savedReasonAction(reasonText)));
        } catch (Exception e) {
            System.out.println("Passed, element is not displayed");
        }
    }

    @Then("I am able to cancel the deletion of above reason&action by clicking trash icon and clicking Cancel")
    public void i_am_able_to_cancel_the_deletion_of_above_reason_action_by_clicking_trash_icon_and_clicking_Cancel() {
        waitFor(5);
        crmContactRecordUIPage.deleteReasonAction.click();
        waitFor(4);
        jsClick(crmContactRecordUIPage.deleteReasonActionCancel);
        waitFor(3);
    }

    @Then("I verify above reason {string} is NOT deleted and still displaying on UI")
    public void i_verify_above_reason_is_NOT_deleted_and_still_displaying_on_UI(String reasonText) {
        assertTrue(isElementDisplayed(crmContactRecordUIPage.savedReasonAction(reasonText)));
    }

    @Then("I edit Reason Action with {string} reason and {string} action values")
    public void i_edit_contact_reason(String reason, String action) {
        reasonActionUpdatedValue.set(reason + "/" + action);
        waitForClickablility(crmContactRecordUIPage.editIcon, 5);
        jsClick(crmContactRecordUIPage.editIcon);
        click(crmContactRecordUIPage.expandEditContactReasonDropdown);
        waitFor(3);
        getDriver().findElement(By.xpath("//li[text()='" + reason + "']")).click();
        waitFor(3);
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.expendcontactActionDropDown, action);
        clearAndFillText(crmContactRecordUIPage.editReasonActionComments, "Contact Reason Updated with " + reason + " & " + action + " values");
        crmContactRecordUIPage.editReasonActionSaveButton.click();
    }


    @Then("I add new Reason Action with {string} reason and {string} action values")
    public void i_add_new_reason_contact_reason(String reason, String action) {
        reasonActionUpdatedValue.set(reason + "/" + action);
        click(contactRecord.contactReason);
        waitFor(3);
        getDriver().findElement(By.xpath("//li[text()='" + reason + "']")).click();
        waitFor(3);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, action);
        waitFor(1);
        clearAndFillText(contactRecord.editReasonsComments, "Contact Reason Updated with " + reason + " & " + action + " values");
        contactRecord.saveReasonButton.click();
    }

    @Then("I click on save Call Summary")
    public void i_click_on_save_call_summary() {
        crmContactRecordUIPage.saveCallSummaryButton.click();
        waitFor(1);
    }

    @Then("I verify Call Summary field input requirements")
    public void i_verify_call_summary_field_requirements() {
        sendKeyToTextField(crmContactRecordUIPage.additionalCommentsTextBox, getRandomString(radomNumber(9)));
        crmContactRecordUIPage.saveCallSummaryButton.click();
        assertTrue(isElementDisplayed(crmContactRecordUIPage.callSummaryErrorMsg),
                "Error message is not displayed for Call Summary");
        clearAndFillText(crmContactRecordUIPage.additionalCommentsTextBox, getRandomString(505));
        assertTrue(crmContactRecordUIPage.additionalCommentsTextBox.getText().length() == 500);
        clearTextField(crmContactRecordUIPage.additionalCommentsTextBox);
    }

    @When("I choose a contact reason from reason dropdown list as {string}")
    public void i_choose_a_contact_reason_from_reason_dropdown_list_as(String reason) {
        scrollUpUsingActions(2);
        waitFor(3);
        selectOptionFromMultiSelectDropDown(contactRecord.reasonList, reason);
    }

    @When("I verify {string} error message")
    public void i_verify_call_summary_error_msg_as(String errorMsg) {
        assertEquals(crmContactRecordUIPage.missingCallSummaryErrorMsg.getText(), errorMsg, "Error message is incorrect");
    }

    @Then("I enter Call Summary as {string}")
    public void i_enter_call_summary(String data) {
        waitFor(1);
        switch (data) {
            case "First call summary":
                crmContactRecordUIPage.expandCallSummaryButton.click();
                break;
        }

        contactRecord.additionalComments.click();
        sendKeyToTextField(contactRecord.additionalComments, data);
    }

    @Then("I verify Call Summary {int} saved as {string} for {string}")
    public void i_verify_saved_call_summary(Integer index, String summary, String cr) {
        if (cr.equals("active contact")) {
            assertEquals(crmContactRecordUIPage.savedCallSummaries.get(index - 1).getText(), summary, "Incorrect Call Summary saved");
        } else {
            assertEquals(crmContactRecordUIPage.savedCallSummaries.get(index - 1).getText(), summary, "Incorrect Call Summary saved");
        }
    }

    @Then("I verify {int} Call Summaries were stacked")
    public void i_verify_saved_call_summary(Integer count) {
        assertTrue(crmContactRecordUIPage.savedCallSummaries.size() == count, "Stacked Call Summary count is incorrect");
    }

    @Then("I verify Call Summary field label is displayed")
    public void i_verify_call_summary_displayed() {
        assertTrue(crmContactRecordUIPage.additionalComments.isDisplayed(), "Call Summary is not displayed");
    }

    @Then("I should return to dashboard page")
    public void i_should_return_to_dashboard_page() {
        waitFor(1);
        assertTrue(getDriver().getTitle().equals("Dashboard"), "User was not navigated to Dashboard page");
    }

    @When("I verify action&reason dependence from program error message")
    public void i_verify_program_selection_error_msg() {
        assertEquals(crmDemographicContactInfoPage.lblErrorMessage.getText(), "You have already selected a " +
                "Reason/Action for your original Program selection. If you would like to adjust your Program choice, " +
                "please delete the Reason(s)/Action(s)", "Error message is incorrect");
    }

    @Then("I delete above reason&action by clicking trash icon and clicking Continue")
    public void i_delete_above_reason_action_by_clicking_trash_icon_and_clicking_Continue() {
        waitFor(3);
        crmContactRecordUIPage.deleteReasonAction.click();
        waitForVisibility(crmDashboardPage.warningPopupContinueBtn, 3);
        jsClick(crmDashboardPage.warningPopupContinueBtn);
        waitFor(2);
    }

    @Then("I verify {string} and {string} are selected as Program")
    public void i_verify_program_selections(String sel1, String sel2) {

        assertTrue(crmContactRecordUIPage.programSelectedValues.size() == 2, "number of selected values is wrong");
        assertTrue(crmContactRecordUIPage.programSelectedValues.get(0).getText().equals(sel1), "selected values are wrong");
        assertTrue(crmContactRecordUIPage.programSelectedValues.get(1).getText().equals(sel2), "selected values are wrong");
    }

    @Then("I verify that comments field is invisible below the Actions")
    public void i_verify_that_comment_field_is_invisible_below_comment() {
        //Assert.assertFalse(contactRecord.reasonsComments.isDisplayed(),"comment field is displayed");
        Assert.assertFalse(isElementDisplayed(contactRecord.reasonsComments));

    }

    @When("I am able to edit saved reason action by selecting one more {string} action")
    public void i_am_able_to_edit_saved_reason_action_by_selecting_more_action(String additonalAction) {
        crmContactRecordUIPage.editReasonActionButton.click();
        crmContactRecordUIPage.existingAction.click();
        crmContactRecordUIPage.addedAction.click();
        jsClick(crmContactRecordUIPage.saveEditedReason);
        waitFor(3);
    }

    @Then("I verify there are {int} actions for saved contact reason\\/action")
    public void i_verify_there_are_actions_for_saved_contact_reason_action(Integer two, List<String> actions) {
        waitFor(3);
        jsClick(contactRecord.expandreasonsComments);
        waitFor(2);

        try {
            click(crmContactRecordUIPage.viewActionTextInformationRequest);
        } catch (Exception e) {
            getDriver().switchTo().defaultContent();
            click(crmContactRecordUIPage.viewActionTextInformationRequest);
        }
        waitFor(2);
        List<String> messageList = new ArrayList<String>();
        String getText = get_not_found_selected_action(actions.get(0));
        if (getText != null) {
            messageList.add(getText);
            getText = null;
        }
        getText = get_not_found_selected_action(actions.get(1));
        if (getText != null) {
            messageList.add(getText);
            getText = null;
        }

        if (!messageList.isEmpty()) {
            for (int i = 0; i < messageList.size(); i++) {
                System.out.println(messageList.get(i).toString());
            }
            assertTrue((messageList.isEmpty()), "All Actions are not displayed.");
        }
    }

    @When("I am able to edit saved reason action by removing {string} action")
    public void i_am_able_to_edit_saved_reason_action_by_removing_action(String removedAction) {
        crmContactRecordUIPage.editReasonActionButton.click();
        crmContactRecordUIPage.existingAction.click();
        crmContactRecordUIPage.addedAction.click();
        jsClick(crmContactRecordUIPage.saveEditedReason);
        waitFor(3);
    }

    @Then("I verify below actions are saved")
    public void i_verify_there_is_action_for_saved_contact_reason_action(List<String> actions) {
        waitFor(3);
        contactRecord.expandreasonsComments.click();
        waitFor(2);

        try {
            click(crmContactRecordUIPage.viewActionTextInformationRequest);
        } catch (Exception e) {
            getDriver().switchTo().defaultContent();
            click(crmContactRecordUIPage.viewActionTextInformationRequest);
        }
        waitFor(2);
        List<String> actualActionList;
        String getText = crmContactRecordUIPage.savedContactActionText.getText();
        actualActionList = Arrays.asList(getText.split(", "));

        assertEquals(actions, actualActionList, "Actions are not same as passed");
    }

    @And("I verify mandatory fields error message {string} in active contact page")
    public void iVerifyMandatoryFieldsErrorMessageInActiveContactPage(String msgType) {
        switch (msgType) {
            case "CALL SUMMARY REQUIRED":
                assertEquals(crmContactRecordUIPage.callSummaryRqdErrorMsg.getText(), "CALL SUMMARY is required and cannot be left blank", "Mismatch in expected field error message");
                break;
            default:
                fail("Mismatch in expected case value");
        }
    }

    @Then("I verify that saved additional comments is displayed")
    public void i_verify_that_saved_additional_comments_is_displayed() {
        waitFor(2);
        scrollDownUsingActions(2);
        assertEquals(getText(crmContactRecordUIPage.additionalCommentsAndCallSummaryReadText("First additional comment")), "First additional comment");
    }

    @Then("I will edit the saved additional comments and the new value is saved and displayed")
    public void i_will_edit_the_saved_additional_comments_and_the_new_value_is_saved_and_displayed() {
        waitFor(2);
        scrollDownUsingActions(3);
        waitFor(2);
        crmContactRecordUIPage.additionalCommentAndCallSummaryEditButton.click();
        clearAndFillText(crmContactRecordUIPage.additionalCommentsEditedText, "Edited Additional Comments");
        crmContactRecordUIPage.firstAdditionalCommentsSavedButton.click();
        assertEquals(getText(crmContactRecordUIPage.additionalCommentsAndCallSummaryReadText("Edited Additional Comments")), "Edited Additional Comments");
    }

    @Then("I verify that saved Call Summary is displayed")
    public void i_verify_that_saved_Call_Summary_is_displayed() {
        waitFor(2);
        scrollDownUsingActions(2);
        assertEquals(getText(crmContactRecordUIPage.additionalCommentsAndCallSummaryReadText("First call summary")), "First call summary");
    }
}

