package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.maersk.crm.step_definitions.CRM_ContactRecordUIReasonsAndComments.reasonActionUpdatedValue;
import static com.maersk.crm.step_definitions.CRM_ViewContactRecordHistoryStepDef.reasonForEditUpdatedValue;
import static org.testng.Assert.*;

public class CRM_EditContactRecordHistoryStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage crmContactRecordUIPage = new CRMContactRecordUIPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRMTaskManageMyTasksPage crmTaskManageMyTasksPage = new CRMTaskManageMyTasksPage();
    CRM_Edit_ContactRecord_Page crmEditContactRecordPage = new CRM_Edit_ContactRecord_Page();
    CRMUnidentifiedContactPage unidentifiedContact = new CRMUnidentifiedContactPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMThirdPartyContactRecPage thirdPartyDetails = new CRMThirdPartyContactRecPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    final ThreadLocal<CRM_CORE_HistoryTabStepDef>  coreHistoryTabStepDef = ThreadLocal.withInitial(CRM_CORE_HistoryTabStepDef::new);

    Actions actions = new Actions(Driver.getDriver());

    private String preContactReason;

    @When("I edit the saved contact reason for {string} and {string} on Edit Contact History Page")
    public void i_edit_the_saved_and(String contactReason, String comment, List<String> contactActions) {
        scrollUpUsingActions(2);
        waitFor(3);
        //click(contactHistory.expandSavedReasonsComments);
//        jsClick(contactHistory.expandSavedReasonsComments);
//        jsClick(contactHistory.expandreasonsComments);
//        waitForClickablility(crmContactRecordUIPage.editIcon, 5);
//       click(crmContactRecordUIPage.editIcon);
//        waitFor(3);
        // scrollToElement(contactHistory.lstEditContactReason);
        selectDropDown(contactHistory.lstEditContactReason, contactReason);
        waitFor(3);
        for (String contactAction : contactActions) {
            selectOptionFromMultiSelectDropDown(contactHistory.expendcontactActionDropDown, contactAction);
        }
        waitFor(2);
        waitForVisibility(contactHistory.editReasonsComments, 10);
        sendKeyToTextField(contactHistory.editReasonsComments, comment);
        waitFor(2);
        contactHistory.btnSaveEditedContactReason.click();

    }

    @Then("I verify reason and actions are edited {string}, {string}")
    public void verifyEditedContactReasonDetailsSaved(String contactReason, String comments, List<String> contactActions) {
        waitFor(2);
        jsClick(contactHistory.expandreasonsComments);
        int count = 0;
        waitFor(2);
        scrollUpUsingActions(1);
        String actReason = contactHistory.resAfEdit.getText();
        String actComment = contactHistory.commentIr.getText();
        Assert.assertEquals(actReason, contactReason);
        Assert.assertEquals(actComment, comments);
        for (WebElement contactAction : contactHistory.lblEditedContactActions) {
            Assert.assertEquals(contactAction.getText(), contactActions.get(0));
            count++;
        }
    }

    @Then("I verify Reason for Edit Field is displayed")
    public void verifyReasonForEditFieldDisplayed() {
        waitFor(2);
        Assert.assertTrue(contactHistory.lstReasonForEdit.isDisplayed());
    }

    @Then("I verify the dropdown values for the Reason For Edit Field")
    public void verifyReasonFOrEditDropDownValues(List<String> options) {
        waitFor(4);
        int cntnu = 0;
        contactHistory.lstReasonForEdit.click();
        waitFor(2);
        ArrayList<String> actValues = new ArrayList<String>();
        ArrayList<String> expValues = new ArrayList<String>();
        for (String option : options) {
            expValues.add(option);
        }
        for (WebElement editReason : contactHistory.reasonForEditDropDownValues) {
            actValues.add(editReason.getText());
        }
        Collections.sort(actValues);
        Collections.sort(expValues);
        Assert.assertEquals(actValues, expValues);
    }


    @When("I select reason for edit as {string} in contact history page")
    public void selectReasonForEdit(String reason) {
        waitFor(5);
        selectDropDown(contactHistory.lstReasonForEdit, reason);
        waitFor(2);
    }

    @And("I click on the Additional Comments on edit contact history page")
    public void i_click_on_the_additional_comments() {
        waitFor(3);

        try {
            contactHistory.expendAdditionalCommentsButton.click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
            executor.executeScript("arguments[0].click();", contactHistory.expendAdditionalCommentsButton);
        }

        waitFor(1);
    }

    @And("I Enter Valid  additional Comments {string} on edit contact history page")
    public void i_enter_valid_comments(String comments) {
        staticWait(1000);
        contactHistory.additionalCommentsTextBox.click();
        clearAndFillText(contactHistory.additionalCommentsTextBox, comments);

    }

    @Given("I click on Save button for additional comments on edit contact history page")
    public void i_click_on_Save_button_for_additional_comments() {

        try {
            click(contactHistory.saveAditionalCommentsButton);
        } catch (Exception e) {
            click(contactHistory.saveAdditionalComments);
        }//catch(Exception e){click(crmContactRecordUIPage.savedAdditionalComments);}

    }

    @Then("I verify Additional comments {string} on edit contact history page")
    public void i_verify_Additional_comments_are_edited(String comments) {
        waitFor(2);
        scrollUpUsingActions(1);
        System.out.println(getText(contactHistory.commentRef));
        assertTrue(getText(contactHistory.commentRef).equalsIgnoreCase(comments));
    }

    @When("I click on save button on the Contact History page")
    public void clickSave() {
        waitFor(2);
        scrollUpUsingActions(3);
        waitFor(1);
        if (contactHistory.additionalComments.isDisplayed())
            sendKeyToTextField(contactHistory.additionalComments, "momento");
        else {
            contactHistory.additionalIcon.click();
            waitFor(1);
            sendKeyToTextField(contactHistory.additionalComments, "momento");
        }
        waitFor(2);
        contactHistory.saveAditionalCommentsButton.click();
        waitFor(1);
        contactHistory.btnSave.click();
    }

    /* This method verifies reason for edit, edited by & edited on are captured on contact history
    Parameters - Expected value of Edited By & Edited On
               - If edited by is empty, the username displayed on header is considered to be the expected value
    Author - Shruti
    Updated by - Vinuta on Feb 19, 2019
     */
    @Then("I verify Contact Edit Details are captured {string} and {string}")
    public void verifyReasonforEditDetails(String editedBy, String reasonForEdit) {
        waitFor(1);
        scrollUpUsingActions(2);
        waitFor(2);
        contactHistory.editHistoryTab.click();
        waitFor(2);
        if (editedBy.isEmpty()) {
            editedBy = crmContactRecordUIPage.headerUsername.getText();
            System.out.print(editedBy);
        }
        waitFor(1);
        Assert.assertTrue(contactHistory.reasonForEd.getText().equals(reasonForEdit) ||
                contactHistory.reasonForEd.getText().equals("Adding Contact Details"));
        Assert.assertTrue(contactHistory.editedO.getText().startsWith(getCurrentDate()));
        Assert.assertTrue(contactHistory.editB.getText().endsWith(editedBy));
    }

    /*This method updates the contact disposition field on contact history details page for a completed contact record
    Parameters - New value of contact disposition
    Author - Vinuta on Feb 19, 2019
     */
    @When("I edit the saved contact disposition to {string} on Edit Contact History Page")
    public void i_edit_saved_contact_disposition_to(String contactDisposition) {
        waitFor(3);
        selectDropDown(contactHistory.contactDispositionDropdown, contactDisposition);
        waitFor(2);
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.contactRecordReasonForEdit, "Correcting Disposition");
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.contactRecordReasonForEdit, "Adding Contact Details");
        contactHistory.btnSave.click();
    }

    /*This method verifies the value of saved contact disposition field on contact history page for a completed contact record
    Parameters - Expected value of contact disposition
    Author - Vinuta on Feb 19,2019
     */
    @Then("I verify contact disposition is edited to {string}")
    public void i_verify_contact_disposition_is_edited_to(String contactDisposition) {
        waitFor(2);
        String actualDisposition = contactHistory.readOnlyContactDisposition.getText();
        Assert.assertEquals(actualDisposition, contactDisposition);
    }

    @And("I verify in Contact History a Disposition of {string} is displayed a Disposition of {string}  is NOT displayed")
    public void iVerifyInContactHistoryADispositionOfIsDisplayedADispositionOfIsNOTDisplayed(String displayed, String notDisplayed) {
        Boolean isDisp = false;
        waitFor(8);

        click(crmTaskManageMyTasksPage.geographicalInfo_default_ItemsPerPage);
        click(Driver.getDriver().findElement(By.xpath("//li[@data-value='20']")));
        waitFor(5);
        ArrayList<String> allStatuses = new ArrayList<>();
        int pages = 0;
        List<WebElement> pageNums = Driver.getDriver().findElements(By.xpath("//div[@class='mt-2']/ul/li/a"));

        for (int i = 0; i <= pageNums.size() - 1; i++) {
            if (pageNums.get(i).getAttribute("aria-label").contains("number 1") || pageNums.get(i).getAttribute("aria-label").contains("last page")) {
                continue;
            } else {
                pages += 1;
            }
        }
        for (int i = 1; i <= pages; i++) {
            for (WebElement e : contactHistory.dispositionStatuses) {
                allStatuses.add(e.getText());
            }
            if (i < pages) {
                click(Driver.getDriver().findElement(By.xpath("//a[@aria-label='Go to page number " + i + "']")));
                waitFor(6);
                continue;
            } else {
                break;
            }
        }

        for (String s : allStatuses) {
            if (s.compareToIgnoreCase(displayed) == 0 && s.compareToIgnoreCase(notDisplayed) != 0) {
                isDisp = true;
                continue;
            } else if (s.compareToIgnoreCase(displayed) != 0 && s.compareToIgnoreCase(notDisplayed) != 0) {
                isDisp = true;
                continue;
            } else {
                break;
            }
        }

        assertTrue(isDisp);

    }


    @Then("I edit above CR by keeping same Contact Reason as before & Adding Action")
    public void i_edit_above_CR_by_keeping_same_Contact_Reason_as_before_Adding_Action() {
        waitFor(3);
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.reasonForEdit, "Correcting Contact Reason/Action");
        waitFor(1);
        crmEditContactRecordPage.editReason.click();
        hover(crmEditContactRecordPage.saveEditReason);
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.contactEditReasonDd, "Plan Change");
        crmEditContactRecordPage.saveEditReason.click();
        waitFor(2);
    }


    @Then("I edit above CR by keeping same Contact Reason as before & Changing existing Action")
    public void i_edit_above_CR_by_keeping_same_Contact_Reason_as_before_Changing_existing_Action() {
        waitFor(3);
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.reasonForEdit, "Correcting Contact Reason/Action");
        waitFor(1);
        crmEditContactRecordPage.editReason.click();
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.contactEditReasonDd, "New Enrollment");
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.contactEditReasonDd, "Plan Change");
        crmEditContactRecordPage.frstaddedCRSavebutton.click();
        waitFor(2);
    }

    @Then("I edit above CR by changing Contact Reason and choosing new appropriate action")
    public void i_edit_above_CR_by_changing_Contact_Reason_and_choosing_new_appropriate_action() {
        waitFor(3);
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.reasonForEdit, "Correcting Contact Reason/Action");
        waitFor(1);
        crmEditContactRecordPage.frstaddedContactReason.click();
        crmEditContactRecordPage.editButtonAddedConcatReason.click();
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.chooseReason, "Materials Request");
        waitFor(1);
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.addNewAction, "Re-Sent Notice");
        crmEditContactRecordPage.frstaddedCRSavebutton.click();
        waitFor(2);

    }


    @Then("Verify on EDIT History page that Reason and Action is concatenated and Reason for Edit is {string}")
    public void verify_on_EDIT_History_page_that_Reason_and_Action_is_concatenated_and_Reason_for_Edit_is(String editReason) {
        assertTrue(crmEditContactRecordPage.reasonForEditInHistory.getText().equalsIgnoreCase(editReason));
        waitFor(1);
        assertTrue(crmEditContactRecordPage.previousReasonAction.getText().equalsIgnoreCase("Enrollment/New Enrollment"));
        assertTrue(crmEditContactRecordPage.updatedReasonAction.getText().equalsIgnoreCase("Enrollment/New Enrollment,Plan Change"));
        //reset back
        crmEditContactRecordPage.contactDetails.click();
        crmEditContactRecordPage.editButton.click();
        waitFor(2);
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.reasonForEdit, "Correcting Contact Reason/Action");
        waitFor(1);
        crmEditContactRecordPage.frstaddedContactReason.click();
        crmEditContactRecordPage.editButtonAddedConcatReason.click();
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.addNewAction, "Plan Change");
        crmEditContactRecordPage.frstaddedCRSavebutton.click();

        waitFor(2);
        crmEditContactRecordPage.editSave.click();
        waitFor(1);
    }

    @Then("Verify on EDIT History page that Action is changed and Reason for Edit is {string}")
    public void verify_on_EDIT_History_page_that_Action_is_changed_and_Reason_for_Edit_is(String editReason) {
        assertTrue(crmEditContactRecordPage.reasonForEditInHistory.getText().equalsIgnoreCase(editReason));
        waitFor(1);
        assertTrue(crmEditContactRecordPage.updatedReasonAction.getText().equalsIgnoreCase("Enrollment/Plan Change"));
        //reset back
        crmEditContactRecordPage.contactDetails.click();
        crmEditContactRecordPage.editButton.click();
        waitFor(2);
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.reasonForEdit, "Correcting Contact Reason/Action");
        waitFor(1);
        crmEditContactRecordPage.frstaddedContactReason.click();
        crmEditContactRecordPage.editButtonAddedConcatReason.click();
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.addNewAction, "Plan Change");
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.addNewAction, "New Enrollment");
        crmEditContactRecordPage.frstaddedCRSavebutton.click();

        waitFor(2);
        crmEditContactRecordPage.editSave.click();
        waitFor(1);
    }

    @Then("Verify on EDIT History page that Reason is changed and Reason for Edit is {string}")
    public void verify_on_EDIT_History_page_that_Reason_is_changed_and_Reason_for_Edit_is(String editReason) {
        assertTrue(crmEditContactRecordPage.reasonForEditInHistory.getText().equalsIgnoreCase(editReason));
        waitFor(1);
        assertTrue(crmEditContactRecordPage.updatedReasonAction.getText().equalsIgnoreCase("Materials Request/Re-Sent Notice"));
        //reset back
        crmEditContactRecordPage.contactDetails.click();
        crmEditContactRecordPage.editButton.click();
        waitFor(2);
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.reasonForEdit, "Correcting Contact Reason/Action");
        waitFor(1);
        crmEditContactRecordPage.frstaddedContactReason.click();
        crmEditContactRecordPage.editButtonAddedConcatReason.click();
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.chooseReason, "Enrollment");
        selectOptionFromMultiSelectDropDown(crmEditContactRecordPage.addNewAction, "New Enrollment");
        crmEditContactRecordPage.frstaddedCRSavebutton.click();

        waitFor(2);
        crmEditContactRecordPage.editSave.click();
        waitFor(1);
    }

    @When("I verify there is NO field named {string}")
    public void i_verify_there_is_NO_field_named(String string) {
        Assert.assertFalse(isElementDisplayed(crmEditContactRecordPage.claimIDField));
        crmEditContactRecordPage.editButton.click();
        waitFor(3);
        Assert.assertFalse(isElementDisplayed(crmEditContactRecordPage.claimIDField));
    }

    @Then("I verify reason and actions are edited with {string}, {string}")
    public void verifyreasonandactionsareeditedwith(String contactReason, String comments, List<String> contactActions) {
        waitFor(2);
        jsClick(contactHistory.expandreasonsComments);
        waitFor(2);
        scrollUpUsingActions(1);
        String actReason = contactHistory.resAfEdit.getText();
        Assert.assertEquals(actReason, contactReason);
    }


    @Then("I verify previous and updated value on Edit History page")
    public void verifyPreviousAndUpdatedValueOnEditHistoryPage() {
        contactHistory.editHistoryTab.click();
        assertEquals(crmEditContactRecordPage.reasonForEditInHistory.getText(), reasonForEditUpdatedValue.get());
        assertEquals(crmEditContactRecordPage.updatedReasonAction.getText(), reasonActionUpdatedValue.get());
    }

    @And("I verify in Contact History a Disposition of cancelled is displayed and a Disposition of invalid is not displayed")
    public void iVerifyInContactHistoryADispositionOfIsDisplayedAandDispositionOfIsNOTDisplayed() {
        waitFor(3);
        WebElement cancelledDisplayed = Driver.getDriver().findElement(By.xpath("(//td[contains(text(),'Cancelled')])[1]"));
        System.out.println(cancelledDisplayed.getText());
        assertTrue(cancelledDisplayed.isDisplayed(), "Cancelled not displayed");
        waitFor(3);
        WebElement invalidDisplayed = Driver.getDriver().findElement(By.xpath("(((//tbody)[1]//tr)[1]//td)[7]"));
        System.out.println(invalidDisplayed.getText());
        assertNotEquals(invalidDisplayed.getText(), "Invalid");
        waitFor(2);

    }

    @And("I clean up after editing the CR by returning previous reason and action value")
    public void iCleanUpByReturningPreviousValue() {
        waitFor(10); //additional wait as the page loads
        highLightElement(manualContactRecordSearchPage.editButton);
        scrollUpUsingActions(2);
        manualContactRecordSearchPage.editButton.click();
        waitFor(1);
        selectDropDown(contactHistory.lstEditContactReason, "Other");
        selectDropDown(contactHistory.lstEditContactReason, "Enrollment");
        selectOptionFromMultiSelectDropDown(contactHistory.expendcontactActionDropDown, "New Enrollment");
        waitFor(1);
    }

    @And("I navigate back to contact details page")
    public void iNavigateBackToContactDetailsPage() {
        unidentifiedContact.contactDetailsHeader.click();
    }


    public void editCrDetailPageFieldsFiller(Map<String, String> fieldsToInput) {
        for (String fields : fieldsToInput.keySet()) {
            waitFor(2);
            switch (fields) {
                case "FIRST NAME":
                    clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, fieldsToInput.get(fields));
                    break;
                case "EMAIL":
                    clearAndFillText(crmContactRecordUIPage.contactEmailInput, fieldsToInput.get(fields));
                    break;
            }
        }
    }

    @And("I select the following Reasons for Edit values in the Wrap up and Close panel for Contact Record edit page")
    public void iSelectTheFollowingReasonsForEditValuesInTheWrapUpAndClosePanelForContactRecordEditPage(List<String> datable) {
        waitFor(1);
        thirdPartyDetails.reasonForEditDropdown.click();
        for (String each : datable) {
            waitFor(1);
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='" + each + "']"))).click().build().perform();
        }
        waitFor(1);
        Driver.getDriver().findElement(By.xpath("//body")).click();
    }

    @And("I verify the edited reasons with on and by are displayed in the Contact Edit Reasons panel")
    public void iVerifyTheEditedReasonsWithOnAndByAreDisplayedInTheContactEditReasonsPanel(List<Map<String, String>> data) {
        scrollDownUsingActions(1);
        Map<String, String> keys = data.get(0);
        for (String fields : keys.keySet()) {
            switch (fields) {
                case "REASON FOR EDIT":
                    for (WebElement each : thirdPartyDetails.reasonForEditDataColumn) {
                        assertEquals(each.getText(), keys.get(fields));
                    }
                    break;
                case "EDITED BY":
                    for (WebElement each : thirdPartyDetails.editedByDataColumn) {
                        assertEquals(each.getText(), keys.get(fields));
                    }
                    break;
                case "EDITED ON":
                    for (WebElement each : thirdPartyDetails.editedOnDataColumn) {
                        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        Date date = new Date();
                        System.out.println("today's date " + dateFormat.format(date));
                        assertTrue(each.getText().contains(dateFormat.format(date)));
                        assertTrue(each.getText().contains("PM") || each.getText().contains("AM"));
                    }
                    break;
                case "MULTI REASON FOR EDIT":
                    List<String> editReasonList = new ArrayList<>(Arrays.asList(keys.get(fields).split(", ")));
                    System.out.println("editReasonList = " + editReasonList);
                    for (WebElement each : thirdPartyDetails.reasonForEditDataColumn) {
                        System.out.println("each = " + each.getText());
                        assertTrue(editReasonList.contains(each.getText()));
                    }
                    break;
            }
        }
    }

    @And("I enter Third Party Details Panel with the following field values")
    public void iEnterThirdPartyDetailsPanelWithTheFollowingFieldValues(Map<String, String> data) {
        waitFor(1);
        scrollUpUsingActions(2);
        for (String eachVerifyValue : data.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "FIRST NAME":
                    clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, data.get(eachVerifyValue));
                    break;
                case "LAST NAME":
                    clearAndFillText(thirdPartyDetails.thirdPartyLastNameNew, data.get(eachVerifyValue));
                    break;
                case "ORGANIZATION NAME":
                    clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, data.get(eachVerifyValue));
                    break;
                case "CONSUMER TYPE":
                    selectDropDown(thirdPartyDetails.lstThirdPartyConsumerType, data.get(eachVerifyValue));
                    break;
                case "PREFERRED LANGUAGE":
                    selectDropDown(thirdPartyDetails.lstThirdPartyPreferredLanguage, data.get(eachVerifyValue));
                    break;
                default:
                    fail();
            }
        }
    }

    @And("I Verify edited {string} field values for contact record")
    public void iVerifyEditedFieldValuesForContactRecord(String type, Map<String, String> data) {
        waitFor(2);
        switch (type) {
            case "THIRD PARTY PANEL":
                for (String eachVerifyValue : data.keySet()) {
                    switch (eachVerifyValue) {
                        case "FIRST NAME":
                            assertEquals(contactHistory.lblThirdPartyFirstName.getText(), data.get(eachVerifyValue), "Mismatch in Third party FIRST NAME");
                            break;
                        case "LAST NAME":
                            assertEquals(contactHistory.lblThirdPartyLastName.getText(), data.get(eachVerifyValue), "Mismatch in Third party LAST NAME");
                            break;
                        case "ORGANIZATION NAME":
                            assertEquals(contactHistory.lblThirdPartyOrganizationName.getText(), data.get(eachVerifyValue), "Mismatch in Third party ORGANIZATION NAME");
                            break;
                        case "CONSUMER TYPE":
                            assertEquals(contactHistory.lblThirdPartyConsumerType.getText(), data.get(eachVerifyValue), "Mismatch in Third party CONSUMER TYPE");
                            break;
                        case "PREFERRED LANGUAGE":
                            assertEquals(contactHistory.lblThirdPartyPreferredLanguage.getText(), data.get(eachVerifyValue), "Mismatch in Third party PREFERRED LANGUAGE");
                            break;
                        default:
                            fail();
                    }
                }
                break;
            case "CONTACT DETAILS PANEL":
                scrollDownUsingActions(1);
                for (String eachVerifyValue : data.keySet()) {
                    switch (eachVerifyValue) {
                        case "CHANNEL":
                            assertEquals(contactHistory.contactChannelDetail.getText(), data.get(eachVerifyValue), "Mismatch in Third party CHANNEL");
                            break;
                        case "EMAIL":
                            assertEquals(contactHistory.emailContactValues.getText(), data.get(eachVerifyValue), "Mismatch in Third party EMAIL");
                            break;
                        case "PROGRAM":
                            assertEquals(contactHistory.programValues.getText(), data.get(eachVerifyValue), "Mismatch in Third party PROGRAM");
                            break;
                        case "TRANSLATION SERVICE":
                            assertEquals(contactHistory.translationServiceValues.getText(), data.get(eachVerifyValue), "Mismatch in Third party TRANSLATION SERVICE");
                            break;
                        default:
                            fail();
                    }
                }
                break;
            case "CONTACT REASON AND ACTION PANEL":
              scrollUpRobot();
                waitFor(1);
                WebElement firstReasonActionPanelCarrot = activeContact.reasonActionPanelOpenButtonList.get(0);
                actions.moveToElement(firstReasonActionPanelCarrot).click().build().perform();
                waitFor(1);
                for (String eachVerifyValue : data.keySet()) {
                    switch (eachVerifyValue) {
                        case "CONTACT REASON":
                            assertEquals(activeContact.contactReasonSavedValue.getText(), data.get(eachVerifyValue), "Mismatch in Third party CONTACT REASON");
                            break;
                        case "CONTACT ACTION":
                            assertEquals(activeContact.contactActionSavedValue.getText(), data.get(eachVerifyValue), "Mismatch in Third party CONTACT ACTION");
                            break;
                        default:
                            fail();
                    }
                }
                break;
            default:
                fail();
        }
    }

    @And("I Enter the following Contact Details field values in the Contact Record Edit page")
    public void iEnterTheFollowingContactDetailsFieldValuesInTheContactRecordEditPage(List<Map<String, String>> datatable) {
        Map<String, String> data = datatable.get(0);
        waitFor(2);
        for (String eachVerifyValue : data.keySet()) {
            switch (eachVerifyValue) {
                case "CHANNEL":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, data.get(eachVerifyValue));
                    break;
                case "EMAIL":
                    clearAndFillText(crmContactRecordUIPage.contactEmailInput, data.get(eachVerifyValue));
                    break;
                case "PROGRAM":
                    List<String> programList = new ArrayList<>(Arrays.asList(data.get(eachVerifyValue).split(", ")));
                    editProgramSelector(programList);
                    break;
                case "TRANSLATION SERVICE":
                    selectDropDown(activeContact.translationServiceDropDown, data.get(eachVerifyValue));
                    break;
                default:
                    fail();
            }
        }
    }

    public void editProgramSelector(List<String> data) {
        List<WebElement> preSelectedprogramList = Driver.getDriver().findElements(By.xpath("//div[@id='mui-component-select-programTypes']/div/div/span[@class='MuiChip-label']"));
        List<String> preSelectedValues = new ArrayList<>();
        preSelectedprogramList.forEach(each -> preSelectedValues.add(each.getText()));
        activeContact.programTypes.click();
        waitFor(1);
        for (String selector : preSelectedValues) {
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='" + selector + "']"))).click().build().perform();
            waitFor(1);
        }
        for (String newSelector : data) {
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='" + newSelector + "']"))).click().build().perform();
            waitFor(1);
        }
        Driver.getDriver().findElement(By.xpath("//body")).click();

    }

    @And("I edit saved contact record Reason and Action with the following")
    public void iEditSavedContactRecordReasonAndActionWithTheFollowing(Map<String, String> data) {
        activeContact.reasonActionPanelEditButton.get(0).click();
        waitFor(1);
        for (String each : data.keySet()) {
            switch (each) {
                case "CONTACT REASON":
                    selectDropDown(activeContact.firstSavedEditContactReasonDropdown, data.get(each));
                    break;
                case "CONTACT ACTION":
                    List<String> actionList = new ArrayList<>(Arrays.asList(data.get(each).split(", ")));
                    coreHistoryTabStepDef.get().scrollDownWithArrow(2);
                    activeContact.firstSavedEditContactActionDropdown.click();
                    for (String newSelector : actionList) {
                        actions.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='" + newSelector + "']"))).click().build().perform();
                        waitFor(1);
                    }
                    Driver.getDriver().findElement(By.xpath("//body")).click();
                    break;
                default:
                    fail();
            }
        }
        crmContactRecordUIPage.saveEditedReason.click();
    }

    @And("I verify fields dependencies on channel field for inbound contact type")
    public void i_verify_fields_dependencies_on_channel_field_for_inbound_contact_type(List<String> channels) {
        waitFor(1);
        for (String each : channels) {
            switch (each) {
                case "Email":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.email), "Email field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.contactInboundCall), "Inbound Call Queue field is displayed");
                    break;
                case "Phone":
                case "IVR Self-Service":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is not displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.contactInboundCall), "Inbound Call Queue field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.email), "Email field is displayed");
                    break;
                case "SMS Text":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.contactInboundCall), "Inbound Call Queue field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.email), "Email field is displayed");
                    break;
                case "Web Chat":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.contactInboundCall), "Inbound Call Queue field is displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.email), "Email field is not displayed");
                    break;
                case "Web Portal":
                case "Mobile App":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.contactInboundCall), "Inbound Call Queue field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.email), "Email field is displayed");
                    break;
                default:
                    fail("Incorrect channel selection");
            }
        }
    }

    @And("I verify fields dependencies on channel field for outbound contact type")
    public void i_verify_fields_dependencies_on_channel_field_for_outbound_contact_type(List<String> channels) {
        waitFor(1);
        for (String each : channels) {
            switch (each) {
                case "Email":
                case "Web Chat":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.email), "Email field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.outcomeOfContactDropDown), "Outcome of Contact field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.callCampaignDropDown), "Call Campaign field is displayed");
                    break;
                case "Phone":
                case "IVR Self-Service":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.email), "Email field is displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.outcomeOfContactDropDown), "Outcome of Contact field is not displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.callCampaignDropDown), "Call Campaign field is not displayed");
                    break;
                case "SMS Text":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.email), "Email field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.outcomeOfContactDropDown), "Outcome of Contact field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.callCampaignDropDown), "Call Campaign field is displayed");
                    break;
                case "Web Portal":
                case "Mobile App":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.email), "Email field is displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.outcomeOfContactDropDown), "Outcome of Contact field is not displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.callCampaignDropDown), "Call Campaign field is not displayed");
                    break;
                default:
                    fail("Incorrect channel selection");
            }
        }
    }

    @And("I verify fields dependencies on channel field for cover va inbound contact type")
    public void i_verify_fields_dependencies_on_channel_field_for_cover_va_inbound_contact_type(List<String> channels) {
        waitFor(1);
        for (String each : channels) {
            switch (each) {
                case "Web Chat":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.email), "Email field is not displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.contactInboundCall), "Inbound Call Queue field is displayed");
                    break;
                case "Phone":
                case "IVR Self-Service":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.email), "Email field is displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.contactInboundCall), "Inbound Call Queue field is not displayed");
                    break;
                default:
                    fail("Incorrect channel selection");
            }
        }
    }

    @And("I verify fields dependencies on channel field for cover va outbound contact type")
    public void i_verify_fields_dependencies_on_channel_field_for_outbound_cover_va_contact_type(List<String> channels) {
        waitFor(1);
        for (String each : channels) {
            switch (each) {
                case "Web Chat":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.email), "Email field is not displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.outcomeOfContactDropDown), "Outcome of Contact field is displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.callCampaignDropDown), "Call Campaign field is displayed");
                    break;
                case "Phone":
                    selectDropDown(crmContactRecordUIPage.contactChannelType, each);
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.phoneNumber), "Phone field is not displayed");
                    assertFalse(isElementDisplayed(crmContactRecordUIPage.email), "Email field is displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.outcomeOfContactDropDown), "Outcome of Contact field is not displayed");
                    assertTrue(isElementDisplayed(crmContactRecordUIPage.callCampaignDropDown), "Call Campaign field is not displayed");
                    break;
                default:
                    fail("Incorrect channel selection");
            }
        }
    }

    @Then("I changed the Outcome of Contact to {string}")
    public void i_changed_the_Outcome_of_Contact_to(String selection) {
        selectDropDown(crmContactRecordUIPage.outcomeOfContactDropDown , selection);
    }

    @Then("I changed the Outcome of Contact to none selected")
    public void i_changed_the_Outcome_of_Contact_to_none_selected() {
        selectDropDownByIndex(crmContactRecordUIPage.outcomeOfContactDropDown , null, 0);
    }

    @Then("I select the Disposition value {string}")
    public void i_select_the_Disposition_value(String selection) {
        scrollDownUsingActions(4);
        selectDropDown(manualContactRecordSearchPage.dispositionDrpDwn , selection);
        scrollUpUsingActions(3);
    }

    @Then("I verify the Disposition selection is cleared")
    public void i_verify_the_Disposition_selection_is_cleared() {
        scrollDownUsingActions(4);
        assertTrue(manualContactRecordSearchPage.dispositionDrpDwn.getText().isEmpty(), "Disposition value is not cleared");
    }

    @Then("I validate {string} single select drop down values")
    public void validateSingleSelectDropDownValues(String ddName, List<String> expectedValues) {
        waitFor(3);
        WebElement dropdown =
                Driver.getDriver().findElement(By.xpath("//input[@id='" + ddName + "']/.."));
        hover(dropdown);
        waitFor(2);
        dropdown.click();
        waitFor(3);
        List<WebElement> dropdownVlu = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + ddName + "')]//ul/li[string-length() > 0]"));
        sa.get().assertThat(dropdownVlu)
                .as(ddName +" Size does not match").hasSize(expectedValues.size())
                .as(ddName+" has duplicate values").doesNotHaveDuplicates()
                .as(ddName + " drop down values not match ").extracting(WebElement::getText).containsExactlyInAnyOrderElementsOf(expectedValues);
        dropdownVlu.get(0).sendKeys(Keys.ESCAPE);
    }
}


