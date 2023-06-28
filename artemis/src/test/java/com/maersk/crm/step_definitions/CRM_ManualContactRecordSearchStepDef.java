package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.exception.CucumberException;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import static com.maersk.crm.utilities.ApiBaseClass.api_commonThreadLocal;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class CRM_ManualContactRecordSearchStepDef extends CRMUtilities implements ApiBase {

    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMApplicationSearchPage applicationSearchPage = new CRMApplicationSearchPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMDashboardPage crmDashboardPage = new CRMDashboardPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();
    CRMCreateGeneralTaskPage createTaskPage = new CRMCreateGeneralTaskPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMCaseAndConsumerSearchPage caseConsumerSrhPage = new CRMCaseAndConsumerSearchPage();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
    CRMContactHistoryPage contactHistoryPage = new CRMContactHistoryPage();
    private final ThreadLocal<ApiTestDataUtil> apitdu = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRM_Edit_ContactRecord_Page edit_cont_Rec = new CRM_Edit_ContactRecord_Page();
    CRMTaskManageMyTasksPage crmTaskManageMyTasksPage = new CRMTaskManageMyTasksPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMBulkUpdatePage bulkUpdatePage = new CRMBulkUpdatePage();
    Actions actions = new Actions(Driver.getDriver());


    final ThreadLocal<CRM_ContactRecordUIStepDef> crm_contactRecordUIStepDef = ThreadLocal.withInitial(CRM_ContactRecordUIStepDef::new);
    final ThreadLocal<String> contactId = ThreadLocal.withInitial(String::new);

    private final ThreadLocal<String> getInsuredCaseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> getInsuredConsumerId = ThreadLocal.withInitial(String::new);
    final ThreadLocal<JSONObject> XMLFormat = ThreadLocal.withInitial(JSONObject::new);
    private final ThreadLocal<String> updateddisposition = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preDisposition = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preContactReason = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preContactAction = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> updatedContactReason = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> updatedAddComment = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preFrstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preLstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preOrgName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> upFrstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> upLstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> upOrgName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preConsumerType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preLanguage = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> upConsumerType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> upLanguage = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> userId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preCaseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> preConsumerId= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> caseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> applicationIdValue = ThreadLocal.withInitial(String::new);
//    Map<String, Object> newConsumer = new HashMap<>(getNewTestData2());
    final ThreadLocal<ArrayList<String>> tabs = ThreadLocal.withInitial(ArrayList::new);
    CRMTaskNoteComponentPage noteComp = new CRMTaskNoteComponentPage();
    CRMTaskEditHistoryPage editHistory = new CRMTaskEditHistoryPage();
    CRMSRViewEditPage srViewEditPage = new CRMSRViewEditPage();

    @When("I navigate to manual contact record search page")
    public void navigateToManualContactSearch() {
        waitFor(3);
        //    Assert.assertTrue(manualContactRecordSearchPage.manualCRSearchMenu.isDisplayed(),"Manual Contact Search Icon not displayed");
        jsClick(manualContactRecordSearchPage.manualCRSearchMenu);
        Assert.assertTrue(manualContactRecordSearchPage.labelManualCRSearch.isDisplayed(), "Not navigated to Manual Contact Search page");
    }

    @When("I search for contact record by {string}, {string}")
    public void i_search_for_contact_record_by(String consumerType, String contactType) {
        selectDropDown(manualContactRecordSearchPage.consumerTypeDropdown, consumerType);
        singleSelectFromDropDown(manualContactRecordSearchPage.contactTypeDropdown, contactType);
        waitFor(2);
        manualContactRecordSearchPage.searchButton.click();
    }

    @When("I expand first contact record in search results")
    public void i_expand_first_contact_record_in_search_results() {
        synchronized (contactId){contactId.set(manualContactRecordSearchPage.contactRecordIDs.get(0).getText());}
        manualContactRecordSearchPage.contactRecordIDs.get(0).click();
        waitForVisibility(manualContactRecordSearchPage.contactDetailsTab, 5);
    }

    @Then("I see a read-only view of contact record details")
    public void i_see_a_read_only_view_of_contact_record_details() {
        Assert.assertTrue(manualContactRecordSearchPage.contactDetailsTab.isDisplayed());
        Assert.assertTrue(manualContactRecordSearchPage.labelUnidentifiedContact.isDisplayed());
        Assert.assertTrue(manualContactRecordSearchPage.labelContactDetailsNew.isDisplayed());
        Assert.assertTrue(manualContactRecordSearchPage.editButton.isDisplayed());
    }

    @When("I search for contact record by value {string}, {string}, {string}")
    public void i_search_for_contact_record_by_value(String contactId, String consumerType, String contactType) {
        manualContactRecordSearchPage.contactRecordNo.sendKeys(contactId);
        selectDropDown(manualContactRecordSearchPage.consumerTypeDropdown, consumerType);
        singleSelectFromDropDown(manualContactRecordSearchPage.contactTypeDropdown, contactType);
        waitFor(2);
        manualContactRecordSearchPage.searchButton.click();
    }

    @And("I verify appropriated fields headers and there values are displayed")
    public void verifyHeadersandThereValues() {
        String[] headers = {"Contact Id", "User Name", "User Id", "Contact Start", "Contact End", "Caller Type",
                "Preferred Language", "Contact Type", "Inbound Call Queue", "Contact Channel", "Phone Number",
                "Contact Disposition"};

        String[] values = {"20671", "Service Account1", "692", "08/30/2019", "08/30/2019", "Anonymous", "Vietnamese",
                "Inbound", "Enrollment", "Phone", "8209488337", "Complete"};

        String[] editHeader = {"EDITED ON", "EDITED BY", "REASON FOR EDIT"};

        String[] editValue = {"08/30/2019", "Service Account1", "Adding Additional Comment"};

        for (int i = 0; i < manualContactRecordSearchPage.contactDetailsHeaders.size(); i++) {
            Assert.assertEquals(manualContactRecordSearchPage.contactDetailsHeaders.get(i).getText().toLowerCase(),
                    headers[i].toLowerCase(), "Headers are not present");
            Assert.assertTrue(manualContactRecordSearchPage.contactDetailsAllValues.get(i).getText().
                    contains(values[i]), "Values Are not present");
        }

        Assert.assertTrue(manualContactRecordSearchPage.contactRecordLabel.isDisplayed(), "Contact Record Label is not present");
        Assert.assertTrue(manualContactRecordSearchPage.labelUnidentifiedContact.isDisplayed(), "Unidentified Contact Label is not present");
        Assert.assertTrue(manualContactRecordSearchPage.contactDetailsLabel.isDisplayed(), "Contact Details Label is not present");
        Assert.assertTrue(manualContactRecordSearchPage.contactEditDetailsLabel.isDisplayed(), "Contact Edit Details Label is not present");

        for (int i = 0; i < manualContactRecordSearchPage.contactEditDetailsHeaders.size(); i++) {
            Assert.assertEquals(manualContactRecordSearchPage.contactEditDetailsHeaders.get(i).getText().toLowerCase(),
                    editHeader[i].toLowerCase(), "Edit part Headers are not present");
            Assert.assertTrue(manualContactRecordSearchPage.contactEditDetailsAllValues.get(i).getText().
                    contains(editValue[i]), "Edit part Values Are not present");
        }

        Assert.assertTrue(manualContactRecordSearchPage.contactReasonHeader.isDisplayed(), "Contact Record header is not present");
        Assert.assertTrue(manualContactRecordSearchPage.contactReasonValue.isDisplayed(), "Contact Record value is not present");
        Assert.assertTrue(manualContactRecordSearchPage.additionalCommentsHeader.isDisplayed(), "AdditionalComments header is not present");
        Assert.assertTrue(manualContactRecordSearchPage.additionalCommentsValue.isDisplayed(), "AdditionalComments value is not present");
    }

    @When("I verify appropriate fields headers after advanced search are displayed")
    public void i_verify_appropriate_fields_headers_after_advanced_search_are_displayed() {
        String[] headers = {"CONTACT ID", "CREATED ON", "CASE ID", "CONSUMER ID", "CONSUMER NAME", "TYPE", "CONTACT DISPOSITION",};
        for (int i = 0; i < headers.length; i++) {
            WebElement header = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + headers[i] + "')]"));
            Assert.assertTrue(header.isDisplayed(), "header is present");
        }
    }

    @When("I verify search result is in access as expected")
    public void i_verify_search_result_is_in_access_as_expected() {
        Assert.assertTrue(manualContactRecordSearchPage.searchIsInExcessError.isDisplayed());
    }

    @When("I search for contact record by id {string}, {string}")
    public void iSearchForContactRecordById(String contactId, String contactType) {
        if (!contactId.isEmpty()) {
            manualContactRecordSearchPage.contactRecordNo.sendKeys(contactId);
        }
        selectDropDown(manualContactRecordSearchPage.contactTypeDropdown, contactType);
        manualContactRecordSearchPage.searchButton.click();
    }

    @Then("I verify the contact icon displayed is {string}")
    public void checkIcon(String icon) {
        waitForClickablility(manualContactRecordSearchPage.contactIcon, 10);
        switch (icon) {
            case "Manual User Captured":
                Assert.assertEquals(manualContactRecordSearchPage.contactIcon.getAttribute("title"),
                        "Manual User Captured", "Icon is not as Manual User Captured");
                break;

            case "System Captured":
                Assert.assertEquals(manualContactRecordSearchPage.contactIcon.getAttribute("title"),
                        "System Captured", "Icon is not as System Captured");
                break;
        }
    }

    @And("I verify icon displayed in contact details tab is {string}")
    public void checkIconDisplayedInContactDetailsTab(String icon) {
        waitForClickablility(manualContactRecordSearchPage.contactDetailsTabIcon, 10);
        switch (icon) {
            case "Manual User Captured":
                Assert.assertEquals(manualContactRecordSearchPage.contactDetailsTabIcon.getAttribute("title"),
                        "Manual User Captured", "Icon is not as Manual User Captured");
                break;

            case "System Captured":
                Assert.assertEquals(manualContactRecordSearchPage.contactDetailsTabIcon.getAttribute("title"),
                        "System Captured", "Icon is not as System Captured");
                break;
        }
    }

    @And("I verify contact type displayed in contact details page is Outbound")
    public void verifyContactTypeInContactDetailsTab() {
        // waitForClickablility(manualContactRecordSearchPage.contactDetailsTabContactType,10);
        Assert.assertEquals(manualContactRecordSearchPage.contactDetailsTabContactType.getText(),
                "Outbound", "Contact type is not a Outbound");
    }

    @And("I click on edit button on contact details tab")
    public void clickOnEditButton() {
        waitFor(5);
        highLightElement(manualContactRecordSearchPage.editButton);
        manualContactRecordSearchPage.editButton.click();
        waitForVisibility(manualContactRecordSearchPage.contactDetailsUserId, 10);
        userId.set(manualContactRecordSearchPage.contactDetailsUserId.getText());
        System.out.println("User ID : " + userId.get());
    }

    @Then("I unlink consumer from contact record")
    public void iUnlinkConsumerFromContactRecord() {
        waitForPageToLoad(1);
        contactHistory.unLink.click();
    }

    @And("I select {string} on contact dispositions dropdown")
    public void selectContactDispositionsDropdown(String val) {
        selectDropDown(manualContactRecordSearchPage.dispositionDrpDwn, val);
    }

    @And("I click on Active Contact widget")
    public void clickOnWidget() {
        click(manualContactRecordSearchPage.widgetPhoneIcon);
    }

    @Then("I verify warning popup is displayed with message")
    public void verifyWarningMessageIsDisplayed() {
        waitFor(5);
        actions.moveToElement(manualContactRecordSearchPage.warningMsg).click().perform();
        Assert.assertTrue(manualContactRecordSearchPage.warningMsg.isDisplayed(), "Warning message is not displayed");
        Assert.assertTrue(manualContactRecordSearchPage.warningMsgTxt.isDisplayed(), "Warning message text is not displayed");
    }

    @Then("I verify warning message is displayed")
    public void i_verify_warning_message_is_displayed() {
        waitForVisibility(manualContactRecordSearchPage.warningMsg, 15);
        Assert.assertTrue(manualContactRecordSearchPage.warningMsg.isDisplayed(), "Warning message is not displayed");
        Assert.assertTrue(manualContactRecordSearchPage.warningMsgNavigate.isDisplayed(), "Warning message is not displayed");
    }

    @Then("Verify should I remains on screen and the information add or update captured will not be cleared")
    public void verifyCancelButtonFunctionality(String page) {
        switch (page) {
            case "Manual Contact Record Search":
                Assert.assertTrue(manualContactRecordSearchPage.contactDetailsTab.isDisplayed());
                Assert.assertTrue(manualContactRecordSearchPage.labelContactDetails2.isDisplayed());
                Assert.assertEquals(manualContactRecordSearchPage.dispositionDrpDwn.getText(), "Complete");
                break;

            case "Create Task":
                Assert.assertTrue(createTaskPage.createTaskPageHeader.isDisplayed());
                Assert.assertTrue(createTaskPage.lblSource.isDisplayed());
                Assert.assertTrue(createTaskPage.txtTaskInfo.getText().contains("Task info"));
                break;

            case "Task Details":
                Assert.assertTrue(myTask.taskDetailsHeader.isDisplayed());
                Assert.assertTrue(createTaskPage.lblSource.isDisplayed());
                break;

            case "Task Details Edit":
                assertTrue(myTask.taskDetailsHeader.isDisplayed());
                /*
                assertEquals(myTask.taskInfo.getAttribute("value"),
                        CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
                 */
                assertEquals(createTaskPage.txtTaskInfo.getAttribute("value"),
                        CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                assertEquals(myTask.assigneeDropDown.getAttribute("value"),
                        CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
                assertEquals(createTaskPage.lstTaskPriority.getText(),
                        CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
                assertEquals(createTaskPage.txtTaskStatus.getText(),
                        CRM_GeneralTaskStepDef.taskValues.get().get("status"));
                assertEquals(myTask.reasonForHoldDropDown.getText(),
                        CRM_GeneralTaskStepDef.taskValues.get().get("reasonForOnHold"));
                break;

            case "Case And Consumer Search":
                Assert.assertTrue(caseConsumerSrhPage.caseConsumerSearchEditPageHeader.isDisplayed());
                Assert.assertEquals(caseConsumerSrhPage.preferredLanguageDropdown.getText(), "Other");
                break;

            case "Task Search":
                Assert.assertTrue(taskSearchPage.saveTaskSearchLabel.isDisplayed());
                Assert.assertTrue(taskSearchPage.searchName.isDisplayed());
                Assert.assertTrue(taskSearchPage.searchName.getAttribute("value").contains("Srch"));
                break;

            case "PRIMARY INDIVIDUAL":
                Assert.assertTrue(caseConsumerSrhPage.primaryIndividualLabel.isDisplayed());
                break;

            case "Edit SR Details":
                waitFor(2);
                assertEquals(crmTaskManageMyTasksPage.srType.getText(), "General Service Request");
                assertEquals(crmTaskManageMyTasksPage.lblSRInfo.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"),
                        "Task Info is mismatch");
               /*
                if(CRM_GeneralTaskStepDef.taskValues.get().get("taskNote") != null) {
                    assertEquals(crmTaskManageMyTasksPage.lblSRNote.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"),
                            "Task Note is mismatch");
                }else {
                    assertEquals(crmTaskManageMyTasksPage.lblSRNote.getText(), "-- --",
                            "Task Note is mismatch");
                }
                */
                assertEquals(crmTaskManageMyTasksPage.lblPriority.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("priority"),
                        "Priority is mismatch");
                assertEquals(crmTaskManageMyTasksPage.lblStatus.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("status"),
                        "Status is mismatch");
                ;
                assertEquals(crmTaskManageMyTasksPage.reasonForEdit.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("reasonForEdit")
                        , "Reason For Edit is mismatch");
                break;

            case "Bulk Update Task":
                assertTrue(bulkUpdatePage.bulUpdateHeader.isDisplayed(), "User is not in bulk update screen");
                assertEquals(bulkUpdatePage.taskIdBulkUpdate.size(), 5, "Task Id are not displayed");
                break;

            case "Bulk Update Screen":
                assertTrue(bulkUpdatePage.bulUpdateHeader.isDisplayed(), "User is not in bulk update screen");
                if (CRM_TaskBulkUpdateStepDef.bulkUpdate.get().get("priority") != null)
                    assertEquals(bulkUpdatePage.priorityDD.getText(), CRM_TaskBulkUpdateStepDef.bulkUpdate.get().get("priority"), "User is not in bulk update screen");
                else if (CRM_TaskBulkUpdateStepDef.bulkUpdate.get().get("assignee") != null)
                    assertEquals(bulkUpdatePage.assigneeDD.getText(), CRM_TaskBulkUpdateStepDef.bulkUpdate.get().get("assignee"), "User is not in bulk update screen");
                else if (CRM_TaskBulkUpdateStepDef.bulkUpdate.get().get("note") != null)
                    assertEquals(bulkUpdatePage.noteTxt.getText(), CRM_TaskBulkUpdateStepDef.bulkUpdate.get().get("note"), "User is not in bulk update screen");
                break;
            case "view Task SR page":
                sa.get().assertThat(isElementDisplayed(editHistory.editHistoryTab)).as("Edit History Tab is not displaying").isTrue();
                if (isElementDisplayed(myTask.taskDetailsHeader))
                    sa.get().assertThat(isElementDisplayed(myTask.btnEditTask)).as("Edit button is not present on view page").isTrue();
                else
                    sa.get().assertThat(isElementDisplayed(srViewEditPage.btnEditSR)).as("SR edit button is not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(noteComp.notesFieldTxtBox)).as("Note Component field is not displaying").isTrue();
                break;
            case "Edit Task SR page":
                sa.get().assertThat(isElementDisplayed(editHistory.editHistoryTab)).as("Edit History Tab is not displaying").isTrue();
                if (isElementDisplayed(myTask.taskDetailsHeader)) {
                    sa.get().assertThat(isElementDisplayed(myTask.assigneeDropDown)).as("Assignee DD is not displaying").isTrue();
                    sa.get().assertThat(isElementDisplayed(myTask.btnEditTask)).as("Edit button is present").isFalse();
                } else {
                    sa.get().assertThat(isElementDisplayed(srViewEditPage.srIdOnViewPage)).as("SR id is not displayed").isTrue();
                    sa.get().assertThat(isElementDisplayed(srViewEditPage.btnEditSR)).as("SR edit button is displayed").isFalse();
                }
                sa.get().assertThat(isElementDisplayed(createTaskPage.lstTaskPriority)).as("Task Info field is not displaying").isTrue();
                sa.get().assertThat(isElementDisplayed(noteComp.notesFieldTxtBox)).as("Note Component field is not displaying").isTrue();
                break;
        }

    }

    @Then("Verify should I return back to Active Contact screen")
    public void verifyShouldIReturnBackToActiveContactScreen() {
        waitForVisibility(activeContactPage.generalConsumerInContactSighn, 10);
        Assert.assertTrue(crmDashboardPage.activeContactLink.getAttribute("class").contains("active"),
                "It is not navigate back to active contact screen");
        Assert.assertTrue(activeContactPage.generalConsumerInContactSighn.isDisplayed(),
                "It is not navigate back to active contact screen");
    }

    @And("I search for contact record by contact id {string}")
    public void iSearchForContactRecordByContactId(String contactId) {
        if (!contactId.isEmpty()) {
            manualContactRecordSearchPage.contactRecordNo.sendKeys(contactId);
        }
        jsClick(manualContactRecordSearchPage.searchButton);
    }

    @When("I search for contact record by type {string}")
    public void i_search_for_contact_record_by_type(String consumerType) {
        waitForVisibility(manualContactRecordSearchPage.consumerTypeDropdown, 10);
        selectDropDown(manualContactRecordSearchPage.consumerTypeDropdown, consumerType);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String time = sdf.format(date);
        System.out.println("time " + time);
        manualContactRecordSearchPage.manualSearchDate.sendKeys(time);
        manualContactRecordSearchPage.searchButton.click();
    }

    @And("I will click on search button on contact record search page")
    public void clickOnSearchButton() {
        manualContactRecordSearchPage.searchButton.click();
        waitFor(3);
        //waitForVisibility(edit_cont_Rec.frstRecord, 13);
        //edit_cont_Rec.frstRecord.click();
    }


    //CP-670 Author-Paramita - Method to verify update message in PRIMARY INDIVIDUAL EDIT PAGE
    @Then("I verify update warning popup is displayed with message")
    public void verifyUpdateWarningMessageIsDisplayed() {
        waitForVisibility(manualContactRecordSearchPage.warningMsg, 10);
        Assert.assertTrue(manualContactRecordSearchPage.warningMsg.isDisplayed(), "Warning message is not displayed");
        Assert.assertTrue(manualContactRecordSearchPage.UpdatewarningMsgTxt.isDisplayed(), "Update Warning message text is not displayed");
    }

    @When("I save GetInsured Case Id and GetInsured Consumer Id")
    public void i_save_GetInsured_Case_Id_and_GetInsured_Consumer_Id() {
        synchronized (getInsuredCaseId){getInsuredCaseId.set(contactRecord.getInsuredCaseId.getText());}
        System.out.println("getInsuredCaseId " + getInsuredCaseId.get());
        synchronized (getInsuredConsumerId){getInsuredConsumerId.set(contactRecord.getInsuredConsumerId.getText());}
        System.out.println("getInsuredConsumerId " + getInsuredConsumerId.get());
    }

    @And("I save GetInsured Case Id and GetInsured Consumer Id in case consumer search")
    public void iSaveGetInsuredCaseIdAndGetInsuredConsumerIdInCaseConsumerSearch() {
        synchronized (getInsuredCaseId){getInsuredCaseId.set(contactRecord.getInsuredCaseIdCCSearch.getText());}
        System.out.println("getInsuredCaseId " + getInsuredCaseId.get());
        synchronized (getInsuredConsumerId){getInsuredConsumerId.set(contactRecord.getInsuredConsumerIdCCSearch.getText());}
        System.out.println("getInsuredConsumerId " + getInsuredConsumerId.get());
    }

    @When("I expand the found contact record and verify GetInsured Case Id and GetInsured Consumer Id are displayed")
    public void i_expand_the_found_contact_record_and_verify_GetInsured_Case_Id_and_GetInsured_Consumer_Id_are_displayed() {
        waitFor(4);
        Assert.assertEquals(manualContactRecordSearchPage.manConRecSearchgGetInsuredCaseId.getText(), getInsuredCaseId.get());
        waitFor(3);
        Assert.assertEquals(manualContactRecordSearchPage.manConRecSearchgGetInsuredConsumerId.getText(), getInsuredConsumerId.get());
    }

    @Then("I verify Reason for Edit dropdown populated with values")
    public void iVerifyReasonForEditDropdownPopolatedWithValues(List<String> values) {
        dropListcheck(manualContactRecordSearchPage.reasonForEdit,values);
    }

    @And("I searched customer with Contact Record ID {string}")
    public void iSearchedCustomerWithContactRecordID(String conRecId) {
        waitForVisibility(manualContactRecordSearchPage.manualSearchContactRecordId, 10);
        if (conRecId.equalsIgnoreCase("getFromUI"))
            manualContactRecordSearchPage.manualSearchContactRecordId.sendKeys(CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"));
        else
            manualContactRecordSearchPage.manualSearchContactRecordId.sendKeys(conRecId);
        waitFor(3);
        System.out.println(conRecId);
        click(contactRecord.searchButton);
    }

    @Then("I choose Reason for Edit dropdown populated with value")
    public void iChooseReasonForEditDropdownPopolatedWithValues(String val) {
        //remove try catch block
        try{ // call management issue
            Driver.getDriver().findElement(By.xpath("//span[@class='mdl-button__ripple-container']")).click();
        }catch (NoSuchElementException e){}
        switch(val){
            case "Adding Contact Reason/Action":
                waitFor(3);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
                waitFor(1);
                scrollUpUsingActions(1);
                selectRandomDropDownOption(manualContactRecordSearchPage.contactReasonEditPage);
                selectRandomOptionFromMultiSelectDropDown(manualContactRecordSearchPage.contactActionEditPage);
                waitFor(2);
                manualContactRecordSearchPage.saveReason.click();
                break;
            case "Correcting Contact Reason/Action":
                waitFor(3);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
                waitFor(1);
                manualContactRecordSearchPage.frstaddedContactReason.click();
                synchronized (preContactReason){preContactReason.set(manualContactRecordSearchPage.preContactReason.getText());}
                manualContactRecordSearchPage.editButtonAddedConcatReason.click();
                selectRandomDropDownOption(manualContactRecordSearchPage.addedContactReasonDrpDwn);
                synchronized (updatedContactReason){updatedContactReason.set(manualContactRecordSearchPage.updatedContactReason.getText());}
                selectRandomOptionFromMultiSelectDropDown(manualContactRecordSearchPage.addedContactActionDrpDwn);
                waitFor(1);
                manualContactRecordSearchPage.addedCommentContactReason.click();
                clearAndFillText(manualContactRecordSearchPage.addedCommentContactReason, val);
                manualContactRecordSearchPage.frstaddedCRSavebutton.click();
                waitFor(2);
                break;
            case "Correcting Contact Reason/Action BLCRM":
                waitFor(1);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val.replace(" BLCRM", ""));
                scrollDownUsingActions(1);
                Driver.getDriver().findElement(By.xpath("(//i[text()='chat']/../../following-sibling::div)[2]")).click();
                synchronized (preContactReason){preContactReason.set(Driver.getDriver().findElement(By.xpath("//p[text()='CONTACT REASON']/following-sibling::p")).getText());}
                manualContactRecordSearchPage.editButtonAddedConcatReason.click();
                selectRandomDropDownOption(Driver.getDriver().findElement(By.xpath("//div[contains(@id,'mui-component-select-contactReason-')]")));
                synchronized (updatedContactReason){updatedContactReason.set(Driver.getDriver().findElement(By.xpath("//div[contains(@id,'mui-component-select-contactReason-')]")).getText());}
                selectRandomOptionFromMultiSelectDropDown(Driver.getDriver().findElement(By.xpath("//div[contains(@id,'mui-component-select-contactAction-')]")));
                waitFor(1);
                manualContactRecordSearchPage.frstaddedCRSavebutton.click();
                waitFor(2);
                break;
            case "Adding Additional Comment":
                waitFor(3);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
                waitFor(1);
                manualContactRecordSearchPage.getAdditionalComment.click();
                clearAndFillText(manualContactRecordSearchPage.getAdditionalComment, val);
                manualContactRecordSearchPage.additionalCommSave.click();
                waitFor(2);
                break;
            case "Correcting Additional Comment":
                waitFor(3);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
                waitFor(1);
                scrollDownUsingActions(2);
                synchronized (updatedAddComment){updatedAddComment.set(manualContactRecordSearchPage.updatedAddComment.getText());}
                waitFor(2);
                break;
            case "Correcting Third Party Information":
                waitFor(3);
                synchronized (preFrstName){preFrstName.set(manualContactRecordSearchPage.thirdPartyFrstName.getAttribute("value"));}
                synchronized (preLstName){preLstName.set(manualContactRecordSearchPage.thirdPartyLstName.getAttribute("value"));}
                synchronized (preOrgName){preOrgName.set(manualContactRecordSearchPage.thirdPartyOrgName.getAttribute("value"));}
                clearAndFillText(manualContactRecordSearchPage.thirdPartyFrstName, getRandomString(5));
                clearAndFillText(manualContactRecordSearchPage.thirdPartyLstName, getRandomString(5));
                clearAndFillText(manualContactRecordSearchPage.thirdPartyOrgName, getRandomString(4));
                synchronized (upFrstName){upFrstName.set(manualContactRecordSearchPage.thirdPartyFrstName.getAttribute("value"));}
                synchronized (upLstName){upLstName.set(manualContactRecordSearchPage.thirdPartyLstName.getAttribute("value"));}
                synchronized (upOrgName){upOrgName.set(manualContactRecordSearchPage.thirdPartyOrgName.getAttribute("value"));}
                waitFor(1);
                synchronized (preConsumerType){preConsumerType.set(manualContactRecordSearchPage.consumerTypeDrpDwn.getText());}
                synchronized (preLanguage){preLanguage.set(manualContactRecordSearchPage.prepeferedLanDrpDwn.getText());}

                selectRandomDropDownOption(manualContactRecordSearchPage.consumerTypeDrpDwn);
                selectRandomDropDownOption(manualContactRecordSearchPage.prepeferedLanDrpDwn);
                synchronized (upConsumerType){upConsumerType.set(manualContactRecordSearchPage.consumerTypeDrpDwn.getText());}
                synchronized (upLanguage){upLanguage.set(manualContactRecordSearchPage.prepeferedLanDrpDwn.getText());}
                scrollDownUsingActions(1);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
                waitFor(2);
                break;
            case "Correcting Third Party Information for error msg":
                scrollDownUsingActions(1);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val.replace(" for error msg", ""));
                break;
            case "Correcting Third Party Information BLCRM":
                waitFor(3);
                clearAndFillText(manualContactRecordSearchPage.thirdPartyFrstName, "Emma");
                clearAndFillText(manualContactRecordSearchPage.thirdPartyLstName, "Jones");
                manualContactRecordSearchPage.searchButton.click();
                waitFor(3);
                manualContactRecordSearchPage.expandFrstRecord.click();
                manualContactRecordSearchPage.primaryIndividualRadioButton.click();
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//span[contains(text(),'LINK')]")).click();
                scrollDownUsingActions(1);
                waitFor(2);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val.replace(" BLCRM", ""));
                waitFor(2);
                break;
            case "Correcting Case/Consumer Link":
                waitFor(3);
                manualContactRecordSearchPage.unlinkContactRecord.click();
                waitFor(1);
                clearAndFillText(manualContactRecordSearchPage.thirdPartyFrstName, "Julia");
                clearAndFillText(manualContactRecordSearchPage.thirdPartyLstName, "Lee");
                manualContactRecordSearchPage.searchButton.click();
                waitFor(3);
                manualContactRecordSearchPage.expandFrstRecord.click();
                manualContactRecordSearchPage.primaryIndividualRadioButton.click();
                waitFor(1);
                try {
                    manualContactRecordSearchPage.validateandlink.click();
                } catch (NoSuchElementException e) {
                    manualContactRecordSearchPage.validateandlinked.click();
                }

                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
                waitFor(1);
                break;
            case "Correcting Case/Consumer Link BLCRM":
                waitFor(3);
                synchronized (preCaseId){preCaseId.set( Driver.getDriver().findElement(By.xpath("//tr/td[1]/span")).getText());}
                synchronized (preConsumerId){preConsumerId.set(Driver.getDriver().findElement(By.xpath("//tr/td[2]/span")).getText());}
                manualContactRecordSearchPage.unlinkContactRecord.click();
                waitFor(1);
                clearAndFillText(manualContactRecordSearchPage.thirdPartyFrstName, "Emma");
                clearAndFillText(manualContactRecordSearchPage.thirdPartyLstName, "Jones");
                manualContactRecordSearchPage.searchButton.click();
                waitFor(3);
                manualContactRecordSearchPage.expandFrstRecord.click();
                manualContactRecordSearchPage.primaryIndividualRadioButton.click();
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//span[contains(text(),'LINK')]")).click();
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit,  val.replace(" BLCRM",""));
                waitFor(1);
                synchronized (userId){userId.set(manualContactRecordSearchPage.contactDetailsUserId.getText() + " Service AccountOne");}
                synchronized (caseId){caseId.set(Driver.getDriver().findElement(By.xpath("//tr/td[1]/span")).getText());}
                synchronized (consumerId){consumerId.set(Driver.getDriver().findElement(By.xpath("//tr/td[2]/span")).getText());}
                break;
            case "Correcting Case/Consumer Link Unidentified":
                waitFor(2);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val.replace(" Unidentified", ""));
                break;
            case "Correcting Contact Details":
                waitFor(3);
                clearAndFillText(manualContactRecordSearchPage.contactPhone, String.valueOf(getRandomNumber(10)));
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
                waitFor(1);
                synchronized (preDisposition){preDisposition.set(manualContactRecordSearchPage.dispositionDrpDwn.getText());}
                //  selectRandomDropDownOption(manualContactRecordSearchPage.dispositionDrpDwn);
                // updateddisposition = manualContactRecordSearchPage.dispositionDrpDwn.getText();
                break;
            default:
                waitFor(3);
                selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
        }
    }

    @Then("I navigated to the Edit History tab")
    public void iNavigatedToTheEditHistoryTab() {
        jsClick(contactHistory.editHistoryTab);
        waitFor(1);
        //Clicking on this continue button is a work-around, it should not display this pop-up CP-15265
        try {
            contactHistory.continueBtnPop.click();
        } catch (NoSuchElementException e) {
        }
        waitFor(2);
        assertTrue(contactHistory.editHistoryHeader.isDisplayed());
    }

    @And("Verify EDIT History with Reason for Edit {string}")
    public void verifyEDITHistoryWithReasonForEdit(String val) {
        switch (val) {
            case "Correcting Third Party Information":
                for (int i = 0; i < contactHistory.listOfeditedOn.size(); i++) {
                    assertTrue(contactHistory.listOfeditedOn.get(i).getText().equalsIgnoreCase(val));
                }
                assertTrue(contactHistory.fieldLabelLstName.getText().equalsIgnoreCase("Last Name"));
                assertTrue(contactHistory.fieldLabelPrfLan.getText().equalsIgnoreCase("Preferred Language"));
                assertTrue(contactHistory.fieldLabelOrg.getText().equalsIgnoreCase("Organization"));
                assertTrue(contactHistory.fieldLabeConType.getText().equalsIgnoreCase("Consumer Type"));
                assertTrue(contactHistory.fieldLabeFrstName.getText().equalsIgnoreCase("First Name"));

                assertTrue(contactHistory.preConsumerType.getText().equalsIgnoreCase(preConsumerType.get()));
                assertTrue(contactHistory.upConsumerType.getText().equalsIgnoreCase(upConsumerType.get()));
                assertTrue(contactHistory.preLanguage.getText().equalsIgnoreCase(preLanguage.get()));
                assertTrue(contactHistory.upLanguage.getText().equalsIgnoreCase(upLanguage.get()));
                assertTrue(contactHistory.preFrstName.getText().equalsIgnoreCase(preFrstName.get()));
                assertTrue(contactHistory.upFrstName.getText().equalsIgnoreCase(upFrstName.get()));
                assertTrue(contactHistory.preLstName.getText().equalsIgnoreCase(preLstName.get()));
                assertTrue(contactHistory.upLstName.getText().equalsIgnoreCase(upLstName.get()));
                assertTrue(contactHistory.preOrganization.getText().equalsIgnoreCase(preOrgName.get()));
                assertTrue(contactHistory.upOrganization.getText().equalsIgnoreCase(upOrgName.get()));
                break;
            case "Correcting Third Party Information BLCRM":
                assertTrue(contactHistory.reasonForEditInHistory.getText().equalsIgnoreCase(val.replace(" BLCRM", "")));

                String updatedVal = contactHistory.fieldLabelList.get(0).getText();
                switch (updatedVal) {
                    case "Last Name":
                        assertTrue(contactHistory.fieldLabelLstName.getText().equalsIgnoreCase("Last Name"));
                        assertTrue(contactHistory.preLstName.getText().equalsIgnoreCase(preLstName.get()));
                        assertTrue(contactHistory.upLstName.getText().equalsIgnoreCase(upLstName.get()));
                        break;
                    case "Consumer Type":
                        assertTrue(contactHistory.preConsumerType.getText().equalsIgnoreCase(preConsumerType.get()));
                        assertTrue(contactHistory.upConsumerType.getText().equalsIgnoreCase(upConsumerType.get()));
                        break;
                    case "First Name":
                        assertTrue(contactHistory.fieldLabeFrstName.getText().equalsIgnoreCase("First Name"));
                        assertTrue(contactHistory.preFrstName.getText().equalsIgnoreCase(preFrstName.get()));
                        assertTrue(contactHistory.upFrstName.getText().equalsIgnoreCase(upFrstName.get()));
                        break;
                    case "Preferred Language":
                        assertTrue(contactHistory.fieldLabelPrfLan.getText().equalsIgnoreCase("Preferred Language"));
                        assertTrue(contactHistory.preLanguage.getText().equalsIgnoreCase(preLanguage.get()));
                        assertTrue(contactHistory.upLanguage.getText().equalsIgnoreCase(upLanguage.get()));
                        break;
                    case "Organization":
                        assertTrue(contactHistory.fieldLabelOrg.getText().equalsIgnoreCase("Organization"));
                        assertTrue(contactHistory.preOrganization.getText().equalsIgnoreCase(preOrgName.get()));
                        assertTrue(contactHistory.upOrganization.getText().equalsIgnoreCase(upOrgName.get()));
                        break;
                }
                break;
            case "Correcting Case/Consumer link":
                assertTrue(contactHistory.reasonForEditInHistory.getText().equalsIgnoreCase(val));
                assertTrue(contactHistory.scndReasonForEdit.getText().equalsIgnoreCase(val));
                break;
            case "Correcting Case/Consumer Link BLCRM":
                waitFor(3);
                assertTrue(contactHistory.reasonForEditInHistory.getText().equalsIgnoreCase(val.replace(" BLCRM", "")));
                assertTrue(contactHistory.editedOnEditHistory.getText().contains(getCurrentDate()));
                assertEquals(contactHistory.editedByEditHistory.getText(), userId.get());
                if (contactHistory.fieldLabelEditHistory.getText().equalsIgnoreCase("case id")) {
                    assertEquals(contactHistory.previousValueEditHistory.getText(), preCaseId.get());
                    assertEquals(contactHistory.updatedValueEditHistory.getText(), caseId.get());
                } else {
                    assertEquals(contactHistory.previousValueEditHistory.getText(), preConsumerId.get());
                    assertEquals(contactHistory.updatedValueEditHistory.getText(), consumerId.get());
                }
                break;
            case "Correcting Contact Reason/Action":
                assertTrue(contactHistory.reasonForEditInHistory.getText().equalsIgnoreCase(val));
                assertTrue(contactHistory.previousValueEditHistory.getText().contains(preContactReason.get()));
                assertTrue(contactHistory.updatedValueEditHistory.getText().contains(updatedContactReason.get()));
                break;
            case "Correcting Contact Details":
                assertTrue(contactHistory.reasonForEditInHistory.getText().equalsIgnoreCase(val));
//                    assertTrue(contactHistory.previousValueEditHistory.getText().equalsIgnoreCase(preDisposition));
//                    assertTrue(contactHistory.updatedValueEditHistory.getText().equalsIgnoreCase(updateddisposition));
                break;
            case "Correcting Additional Comment":
                assertTrue(contactHistory.reasonForEditInHistory.getText().equalsIgnoreCase(val));
                assertTrue(contactHistory.previousValueEditHistory.getText().equalsIgnoreCase(updatedAddComment.get()));
                assertTrue(contactHistory.updatedValueEditHistory.getText().equalsIgnoreCase("Updated"));
                break;
            default:
                assertTrue(contactHistory.reasonForEditInHistory.getText().equalsIgnoreCase(val));
                assertTrue(contactHistory.editedOnEditHistory.isDisplayed());
                assertTrue(contactHistory.editedByEditHistory.isDisplayed());
                assertTrue(contactHistory.fieldLabelEditHistory.isDisplayed());
                assertTrue(contactHistory.previousValueEditHistory.isDisplayed());
                assertTrue(contactHistory.updatedValueEditHistory.isDisplayed());
        }

    }

    @Then("Verify in Edit History page EditedOn is descending order")
    public void verifyInEditHistoryPageEditedOnIsDescendingOrder() {
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < contactHistory.listEditedOn.size(); i++) {
            dates.add(contactHistory.listEditedOn.get(i).getText().substring(0, 10));
        }
        assertTrue(descendingOrderDatesString(dates));
    }

    @When("I verify Email Search Field is NOT displayed in Manual Contact Record Search")
    public void i_verify_Email_Search_Field_is_NOT_displayed_in_Manual_Contact_Record_Search() {
        waitFor(4);

        try {
            Assert.assertFalse(manualContactRecordSearchPage.emailAddressAdvancedSearch.isDisplayed());
        } catch (NoSuchElementException e) {
            System.out.println(("Element not displayed as expected"));
        }


    }

    @When("I expand the found contact record and verify Email Field is NOT displayed in Manual Contact Record Search")
    public void i_expand_the_found_contact_record_and_verify_Email_Field_is_NOT_displayed_in_Manual_Contact_Record_Search
            () {
        waitFor(4);
        manualContactRecordSearchPage.expandFieldButton.click();
        waitFor(2);
        try {
            Assert.assertFalse(manualContactRecordSearchPage.emailExpandedView.isDisplayed());
        } catch (NoSuchElementException e) {
            System.out.println(("Element not displayed as expected"));
        }


    }


    @And("I run GetInsured search request\\/response event")
    public void iRunGetInsuredSearchRequestResponseEvent() throws IOException {
        String consumerBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
        String getConsumerListByUser = "/app/crm/integrations/external/";
        String presentTimeStamp = apitdu.get().getCurrentDateAndTime("yyyy-MM-dd");

        String oauthQAToken = PublishDPBIEventsOutboundCorrespondenceChangesStepDefs.getOAuthQA();
        String responseString = null;
        Response responseBody = given()
                .header("Project-Name", "NJ-SBE")
                .header("Authorization", "Bearer " + oauthQAToken)
                .contentType("application/json")
                .auth().preemptive().oauth2(oauthQAToken)
                .get(consumerBaseURI + getConsumerListByUser + presentTimeStamp + "?limit=10&sort=eventId,desc");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        JSONObject json = new JSONObject(responseString.replace("[", "").replace("]", ""));
        String xmlToJson = XML.toString(json);
        synchronized (XMLFormat){XMLFormat.set(XML.toJSONObject(xmlToJson));}
    }

    @And("I verify GetInsure search response contains info {string} {string}")
    public void iVerifyGetInsureSearchResponseContainsInfo(String FName, String LName) {
        Assert.assertTrue(XMLFormat.get().getString("createdOn").contains(apitdu.get().getCurrentDateAndTime("yyyy-MM-dd")));
        Assert.assertTrue(XMLFormat.get().getString("correlationId").matches("[A-Za-z0-9]{10,}"));
        System.out.println(XMLFormat.get().getString("response"));
        Assert.assertTrue(XMLFormat.get().getString("response").contains(FName));
        Assert.assertTrue(XMLFormat.get().getString("response").contains(LName));
        Assert.assertTrue(XMLFormat.get().getString("response").contains(getInsuredCaseId.get()));
        Assert.assertTrue(XMLFormat.get().getString("response").contains(getInsuredConsumerId.get()));
        Assert.assertTrue(XMLFormat.get().getString("createdBy").contains("SYSTEM"));
    }

    @Then("I verify popup with Error message displayed for {string}")
    public void iVerifyPopupWithErrorMessageDisplayedFor(String msg) {
        if (msg.equalsIgnoreCase("Correcting Case/Consumer Link")) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, msg);
            contactRecord.saveEditContactRecordBtn.click();
            assertTrue(manualContactRecordSearchPage.errorMsgCaseLink.isDisplayed());
        } else if (msg.equalsIgnoreCase("Correcting Third Party Information")) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, msg);
            contactRecord.saveEditContactRecordBtn.click();
            assertTrue(manualContactRecordSearchPage.errorMsgThirdParty.isDisplayed());
        }
    }

    @When("I click on found contact record and verify wrap-up time is displayed for {string}")
    public void i_click_on_found_contact_record_and_verify_wrap_up_time_is_displayed_for(String wrapUpTime) {
        manualContactRecordSearchPage.contactiD.click();
        waitFor(10);
        contactHistoryPage.wrapUpTime.isDisplayed();
        if (wrapUpTime.equalsIgnoreCase("End-Save")) {
            waitFor(10);
            Assert.assertEquals(contactHistoryPage.WrapUpTime_Time.getText(), "00:01:10");
        } else if (wrapUpTime.equalsIgnoreCase("End-Cancel")) {
            waitFor(10);
            Assert.assertEquals(contactHistoryPage.WrapUpTime_Time.getText(), "00:00:00");
        }

    }

    @And("Wait for {int} seconds")
    public void waitForSeconds(int second) {
        waitFor(second);
    }

    @When("Click on first Contact Record ID on Contact Record")
    public void clickOnFirstContactRecordIDOnContactRecord() {
        waitForVisibility(manualContactRecordSearchPage.contactiD, 15);
        manualContactRecordSearchPage.contactiD.click();
    }

    @Then("I will see a comprehensive detailed view information for each edit made to the Contact Record")
    public void iWillSeeAComprehensiveDetailedViewOfTheFollowingInformationForEachEditMadeToTheContactRecord() {
        assertTrue(contactHistory.editedOnEditHistory.isDisplayed());
        assertTrue(contactHistory.editedByEditHistory.isDisplayed());
        assertTrue(contactHistory.fieldLabelEditHistory.isDisplayed());
        assertTrue(contactHistory.previousValueEditHistory.isDisplayed());
        assertTrue(contactHistory.updatedValueEditHistory.isDisplayed());
    }

    @Then("Verify maximum five records for each Pagination are displayed")
    public void verifyMaximumFiveRecordsForEachPaginationAreDisplayed() {
        if (manualContactRecordSearchPage.frontArrow.isDisplayed()) {
            for (int i = 0; i < manualContactRecordSearchPage.listOfPag.size(); i++) {
                waitFor(2);
                manualContactRecordSearchPage.frontArrow.click();
                waitFor(2);
                assertTrue(contactHistory.listOfeditedOn.size() <= 5);
            }
        } else {
            assertTrue(contactHistory.listOfeditedOn.size() <= 5);
        }

    }

    @And("I verify the value for Edited On is in correct format")
    public void iVerifyTheValueForEditedOnIsInCorrectFormat() {
        for (int i = 0; i < contactHistory.listEditedOn.size(); i++) {
            assertTrue(verifyDateFormat(contactHistory.listEditedOn.get(i)));
            assertEquals(contactHistory.listEditedOn.get(i).getText().substring(11, 16),
                    systemTimeNow().substring(0, 5));
        }
    }

    @And("I verify Edited By has the correct user ID & user name")
    public void iVerifyEditedByHasTheCorrectUserIDUserName() {
        assertEquals(contactHistory.editedByEditHistory.getText().substring(0, 4), userId);
        assertEquals(contactHistory.editedByEditHistory.getText().substring(5), "Service AccountOne");
    }

    @Then("I navigated to the Edit History tab and clicking Continue")
    public void iNavigatedToTheEditHistoryTabClickCaontinue() {
        jsClick(contactHistory.editHistoryTab);
        waitFor(1);
        //Clicking on this continue button is a work-around, it should not display this pop-up CP-15265
        // contactHistory.continueBtnPop.click();
        waitFor(2);
        assertTrue(contactHistory.editHistoryHeader.isDisplayed());
    }


    @And("I do NOT see {string} as a dropdown option")
    public void iDoNOTSeeAsADropdownOption(String arg0) {
        try {
            assertFalse(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//li[text()='" + arg0 + "']"))));
        } catch (NoSuchElementException e) {
        }
    }

    @And("I link consumer if not linked for {string} {string}")
    public void iLinkConsumerIfNotLinkedFor(String fN, String lN) {
        try {
            waitFor(3);
            if (manualContactRecordSearchPage.unlinkContactRecord.isDisplayed()) {
                manualContactRecordSearchPage.unlinkContactRecord.click();
                waitFor(1);
                clearAndFillText(manualContactRecordSearchPage.thirdPartyFrstName, fN);
                clearAndFillText(manualContactRecordSearchPage.thirdPartyLstName, lN);
                manualContactRecordSearchPage.searchButton.click();
                waitFor(3);
                manualContactRecordSearchPage.expandFrstRecord.click();
                manualContactRecordSearchPage.primaryIndividualRadioButton.click();
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//span[contains(text(),'LINK')]")).click();
            }
        } catch (NoSuchElementException e) {
            waitFor(1);
            clearAndFillText(manualContactRecordSearchPage.thirdPartyFrstName, fN);
            clearAndFillText(manualContactRecordSearchPage.thirdPartyLstName, lN);
            manualContactRecordSearchPage.searchButton.click();
            waitFor(3);
            manualContactRecordSearchPage.expandFrstRecord.click();
            manualContactRecordSearchPage.primaryIndividualRadioButton.click();
            waitFor(1);
            Driver.getDriver().findElement(By.xpath("//span[contains(text(),'LINK')]")).click();
        }
    }

    @And("I unlink if linked")
    public void iUnlinkIfLinked() {
        try {
            waitFor(3);
            manualContactRecordSearchPage.unlinkContactRecord.click();
        } catch (NoSuchElementException e) {
        }
    }

    @Then("I search with {string} field with todays date")
    public void i_search_with_field_with_todays_date(String symbol) {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.dispositionSearch, "Complete");
        waitFor(1);
        selectDropDown(manualContactRecordSearchPage.createdDataSymbol, symbol);
        waitFor(1);
        clearAndFillText(manualContactRecordSearchPage.CreatedOn, getCurrentDate());
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(2);
    }

    @Then("I verify appropriate CoverVA Contact Record Search Result field headers are displayed")
    public void i_verify_appropriate_CoverVA_Contact_Record_Search_Result_field_headers_are_displayed() {
        String[] headers = {"CONTACT ID", "CREATED ON", "CONSUMER ID", "CONSUMER NAME", "TYPE", "CONTACT DISPOSITION",};
        for (int i = 0; i < headers.length; i++) {
            WebElement header = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + headers[i] + "')]"));
            Assert.assertTrue(header.isDisplayed(), "header is present");
        }
    }


    @And("And I unlink existing consumer and linking consumer fn {string} and ln {string}")
    public void andIUnlinkExistingConsumerAndLinkingConsumerFnAndLn(String fn, String ln) {
        waitFor(3);
        manualContactRecordSearchPage.unlinkContactRecord.click();
        waitFor(1);
        clearAndFillText(manualContactRecordSearchPage.thirdPartyFrstName, fn);
        clearAndFillText(manualContactRecordSearchPage.thirdPartyLstName, ln);
        manualContactRecordSearchPage.searchButton.click();
        manualContactRecordSearchPage.expandFrstRecord.click();
        manualContactRecordSearchPage.primaryIndividualRadioButton.click();
        waitFor(1);
        manualContactRecordSearchPage.linkCaseAndConsumer.click();
        waitFor(2);
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, "Correcting Case/Consumer Link");
        waitFor(1);
    }

    @When("I search and select contact record created above with phone number")
    public void i_search_and_select_third_party_contact_record_created_above_with_phone_number() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(3);
        contactRecord.contactPhoneField.sendKeys(crm_contactRecordUIStepDef.get().contactPhoneNumber.get());
        waitFor(3);
        contactRecord.searchButton.click();
        waitFor(5);
        contactRecord.contactIdFirstRecord.click();
    }

    @And("I verify {string} have following options")
    public void iVerifyInboundCallQueueHaveFollowingOptions(String dropdown , List<String> options) {
        for (String option : options) {
            switch (dropdown) {
                case "Inbound Call Queue":
                    selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, option);
                    break;
                case "Call Campaign":
                    selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.callCamp, option);
                    break;
                case "Outcome of Contact":
                    selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.outComeCon, option);
                    break;
                default:
                    throw new CucumberException(dropdown + " Drop Down Not Found");

            }
        }
    }

    @Then("Clicking Advanced Search on Contact Record search in CoverVa")
    public void clickingAdvancedSearchOnContactRecordSearchInCoverVa() {
        waitFor(2);
        manualContactRecordSearchPage.callManagmentTab.click();
        waitFor(3);
        manualContactRecordSearchPage.advancedSearch.click();
        waitFor(4);
    }


    @When("I verify {string} field accept {int} characters in total")
    public void iVerifyFieldAcceptCharactersInTotal(String field, int length) {
        String actual = "";
        switch (field) {
            case "ContactRecordId":
                clearAndFillText(manualContactRecordSearchPage.manualSearchContactRecordId, getRandomNumber(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.manualSearchContactRecordId.getAttribute("value");
                assertTrue(actual.length() == 10, "Field accepts text which is not " + length + " characters long");
                break;
            case "firstName":
                clearAndFillText(manualContactRecordSearchPage.firstNameSearch, RandomStringUtils.randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.firstNameSearch.getAttribute("value");
                assertTrue(actual.length() == 30, "Field accepts text which is not " + length + " characters long");
                break;
            case "lastName":
                clearAndFillText(manualContactRecordSearchPage.lasttNameSearch, RandomStringUtils.randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.lasttNameSearch.getAttribute("value");
                assertTrue(actual.length() == 30, "Field accepts text which is not " + length + " characters long");
                break;
            case "createdOn":
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date todayDate = new Date();
                String randomDate = dateFormat.format(todayDate);
                clearAndFillText(manualContactRecordSearchPage.CreatedOn, randomDate);
                waitFor(1);
                actual = manualContactRecordSearchPage.CreatedOn.getAttribute("value");
                assertEquals(actual, randomDate, "createdOn field is Failed");
                break;
            case "ConsumerId":
                clearAndFillText(manualContactRecordSearchPage.manualSearchInternalConsumerId, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.manualSearchInternalConsumerId.getAttribute("value");
                assertTrue(actual.length() == 12, "Field accepts text which is not " + length + " characters long");
                break;
            case "Phone":
                clearAndFillText(manualContactRecordSearchPage.phoneNumberTextBox, getRandomNumber(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.phoneNumberTextBox.getAttribute("value");
                assertTrue(actual.length() == 12, "Field accepts text which is not " + length + " characters long");
                break;
            case "Email":
                clearAndFillText(manualContactRecordSearchPage.emailTextBox, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.emailTextBox.getAttribute("value");
                assertTrue(actual.length() == 30, "Field accepts text which is not " + length + " characters long");
                break;
            case "ThirdPartyFirstName":
                clearAndFillText(manualContactRecordSearchPage._3rdPartyFistNameAdvancedSearch, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage._3rdPartyFistNameAdvancedSearch.getAttribute("value");
                assertTrue(actual.length() == 30, "Field accepts text which is not " + length + " characters long");
                break;
            case "ThirdPartyLasttName":
                clearAndFillText(manualContactRecordSearchPage._3rdPartyLastNameAdvancedSearch, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage._3rdPartyLastNameAdvancedSearch.getAttribute("value");
                assertTrue(actual.length() == 30, "Field accepts text which is not " + length + " characters long");
                break;
            case "ThirdPartyOrganization":
                clearAndFillText(manualContactRecordSearchPage.thirdPartyOrgName, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.thirdPartyOrgName.getAttribute("value");
                assertTrue(actual.length() == length, "Field accepts text which is not " + length + " characters long");
                break;
            default:
                throw new CucumberException(field + " Field Not Found");

        }
    }

    @Then("Verify {string} dropDown has a options")
    public void verifyDropDownHasAOptions(String dropdown, List<String> options) {
        for (String option : options) {
            switch (dropdown) {
                case "ContactType":
                    selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.contactTypeDropdown, option);
                    break;
                case "Channel":
                    selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.channelSearch, option);
                    break;
                case "Disposition":
                    selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.dispositionDrpDwn, option);
                    break;
                case "Type":
                    selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.consumerTypeDropdown, option);
                    break;
                case "ConsumerIdType":
                    selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.searchConsumerType, option);
                    break;
                default:
                    throw new CucumberException(dropdown + " Drop Down Not Found");

            }
        }
    }

    @Then("I verify {string} field has Auto-complete Dropdown when {string} is opened")
    public void iVerifyFieldHasAutoCompleteDropdown(String field, String val) {
        switch (field) {
            case "CreatedBy":
                manualContactRecordSearchPage.createdBy.click();
                manualContactRecordSearchPage.createdBy.sendKeys(val);
                waitFor(3);
                manualContactRecordSearchPage.createdByDrop.click();
                waitFor(3);
                Assert.assertTrue(manualContactRecordSearchPage.createdBy.getAttribute("value").contains(val),
                        "Auto Complete Not Working for " + field + " dropdown");
                break;
            case "UserId":
                manualContactRecordSearchPage.userId.click();
                manualContactRecordSearchPage.userId.sendKeys(val);
                waitFor(3);
                manualContactRecordSearchPage.createdByDrop.click();
                waitFor(3);
                Assert.assertEquals(manualContactRecordSearchPage.userId.getAttribute("value").substring(0, 2), val,
                        "Auto Complete Not Working for " + field + " dropdown");
                break;
            default:
                throw new CucumberException(field + " Field Not Found");
        }
    }

    @When("I pass {int} characters in total I verify {string} field doesn't accept more {int} characters")
    public void iPassCharactersInTotalIVerifyFieldDoesnTAcceptMoreCharacters(int length, String field, int len) {
        String actual = "";
        switch (field) {
            case "ContactRecordId":
                clearAndFillText(manualContactRecordSearchPage.manualSearchContactRecordId, getRandomNumber(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.manualSearchContactRecordId.getAttribute("value");
                assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
                break;
            case "firstName":
                clearAndFillText(manualContactRecordSearchPage.firstNameSearch, RandomStringUtils.randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.firstNameSearch.getAttribute("value");
                assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
                break;
            case "lastName":
                clearAndFillText(manualContactRecordSearchPage.lasttNameSearch, RandomStringUtils.randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.lasttNameSearch.getAttribute("value");
                assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
                break;
            case "createdOn":
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date todayDate = new Date();
                String randomDate = dateFormat.format(todayDate);
                clearAndFillText(manualContactRecordSearchPage.CreatedOn, randomDate);
                waitFor(1);
                actual = manualContactRecordSearchPage.CreatedOn.getAttribute("value");
                assertEquals(actual, randomDate, "createdOn field is Failed");
                break;
            case "ConsumerId":
                clearAndFillText(manualContactRecordSearchPage.manualSearchInternalConsumerId, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.manualSearchInternalConsumerId.getAttribute("value");
                assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
                break;
            case "Phone":
                clearAndFillText(manualContactRecordSearchPage.phoneNumberTextBox, getRandomNumber(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.phoneNumberTextBox.getAttribute("value");
                assertTrue(actual.length() == len + 2, "Field accepts text which is not " + len + " characters long");
                break;
            case "Email":
                clearAndFillText(manualContactRecordSearchPage.emailTextBox, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.emailTextBox.getAttribute("value");
                assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
                break;
            case "ThirdPartyFirstName":
                clearAndFillText(manualContactRecordSearchPage._3rdPartyFistNameAdvancedSearch, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage._3rdPartyFistNameAdvancedSearch.getAttribute("value");
                assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
                break;
            case "ThirdPartyLasttName":
                clearAndFillText(manualContactRecordSearchPage._3rdPartyLastNameAdvancedSearch, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage._3rdPartyLastNameAdvancedSearch.getAttribute("value");
                assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
                break;
            case "ThirdPartyOrganization":
                clearAndFillText(manualContactRecordSearchPage.thirdPartyOrgName, randomAlphanumeric(length));
                waitFor(1);
                actual = manualContactRecordSearchPage.thirdPartyOrgName.getAttribute("value");
                assertTrue(actual.length() == length, "Field accepts text which is not " + len + " characters long");
                break;
            default:
                throw new CucumberException(field + " Field Not Found");

        }
    }

    @When("I pass {int} characters in total I verify MMIS field doesn't accept more {int} characters")
    public void i_pass_characters_in_total_I_verify_MMIS_field_doesn_t_accept_more_characters(int length, int len) {
        waitFor(3);
        selectDropDown(manualContactRecordSearchPage.searchConsumerTypeCaseConsumer, "MMIS");
        clearAndFillText(manualContactRecordSearchPage.caseConsumerID, getRandomNumber(length));
        waitFor(1);
        String actual = manualContactRecordSearchPage.caseConsumerID.getAttribute("value");
        assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
    }

    @When("I pass {int} characters in total I verify MMIS field doesn't accept more {int} characters in Case and Consumer page")
    public void i_pass_characters_in_total_I_verify_MMIS_field_doesn_t_accept_more_characters_in_Case_and_Consumer_page(int length, int len) {
        waitFor(3);
        selectDropDown(manualContactRecordSearchPage.searchConsumerType, "MMIS");
        clearAndFillText(manualContactRecordSearchPage.consumerExternalId, getRandomNumber(length));
        waitFor(1);
        String actual = manualContactRecordSearchPage.consumerExternalId.getAttribute("value");
        assertTrue(actual.length() == len, "Field accepts text which is not " + len + " characters long");
    }

    @Then("I verify the presence of {string} field")
    public void i_verify_the_presence_of_field(String applicationID) {
        Assert.assertTrue(applicationSearchPage.applicationID.isDisplayed());
    }

    @Then("I verify {string} field accepts {int} alphanumeric characters in total")
    public void i_verify_field_accepts_alphanumeric_characters_in_total(String applicationID, int length) {
        applicationSearchPage.applicationID.click();
        clearAndFillText(applicationSearchPage.applicationID, randomAlphanumeric(length));
        waitFor(1);
        String actual = applicationSearchPage.applicationID.getAttribute("value");
        assertTrue(actual.length() == length);
    }

    @Then("I verify {string} field doesn't accepts special characters")
    public void i_verify_field_doesn_t_accepts_special_characters(String applicationID) {
        clearAndFillText(applicationSearchPage.applicationID, "76rt$%#_+");
        waitFor(1);
        String actual = applicationSearchPage.applicationID.getAttribute("value");
        assertTrue(actual.length() == 4);
    }

    @Then("I search for newly created Contact Record ID {string}")
    public void iSearchForNewlyCreatedContactRecordID(String conRecId) {
        waitForVisibility(manualContactRecordSearchPage.manualSearchContactRecordId, 10);
        synchronized (contactId){contactId.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(conRecId).substring(0,6));}
        manualContactRecordSearchPage.manualSearchContactRecordId.sendKeys(contactId.get());
        waitFor(3);
        click(contactRecord.searchButton);
    }

    @Then("Verify Contact Details have email {string} and link {string}")
    public void verifyContactDetailsHaveEmailAndLink(String email, String link) {
        waitForVisibility(manualContactRecordSearchPage.conDetEmail, 10);
        assertEquals(manualContactRecordSearchPage.conDetEmail.getText(), API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr(email),"CONTACT DETAILS" +
                " EMAIL NOT MATCHED");
        assertEquals(manualContactRecordSearchPage.viewChatTranscriptButton.getText(), link, "VIEW CHAT TRANSCRIPT " +
                "NOT VISIBLE");
    }

    @Then("Verify after clicking VIEW CHAT TRANSCRIPT button Link Navigates to MedChat PDF")
    public void verifyAfterClickingVIEWCHATTRANSCRIPTButtonLinkNavigatesToMedChatPDF() {
        waitForVisibility(manualContactRecordSearchPage.viewChatTranscriptButton, 10);
        manualContactRecordSearchPage.viewChatTranscriptButton.click();
        waitFor(7);
        tabs.set(new ArrayList<>(Driver.getDriver().getWindowHandles()));
        if (tabs.get().size() > 1)
        {
            Driver.getDriver().switchTo().window(tabs.get().get(1));
            String pdf_url = Driver.getDriver().getCurrentUrl();
            assertTrue(pdf_url.contains("https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com"), "PDF url not matches");
        }
        else fail("PDF window is not displayed");
        Driver.getDriver().close();
        waitFor(3);
        Driver.getDriver().switchTo().window(tabs.get().get(0));
        waitFor(3);
    }

    @And("I searched with {string} Consumer Id {string}")
    public void iSearchedWithConsumerId(String dropDwn, String consumerId) {
        waitFor(3);
        if (dropDwn.equalsIgnoreCase("Internal")) {
            selectDropDown(manualContactRecordSearchPage.searchConsumerType, dropDwn);
            clearAndFillText(manualContactRecordSearchPage.manualSearchInternalConsumerId, consumerId);
            waitFor(2);
            manualContactRecordSearchPage.searchButton.click();
        } else {
            selectDropDown(manualContactRecordSearchPage.searchConsumerType, dropDwn);
            clearAndFillText(manualContactRecordSearchPage.consumerExternalId, consumerId);
            waitFor(2);
            manualContactRecordSearchPage.searchButton.click();
        }
    }

    @Then("Verify {string} and Consumer Id {string} is visible")
    public void verifyAndConsumerIdIsVisible(String ext, String consumerId) {
        waitForVisibility(manualContactRecordSearchPage.extConsumerId, 5);
        if (ext.equalsIgnoreCase("MMIS")) {
            assertTrue(manualContactRecordSearchPage.extConsumerId.getText().contains("MMIS CONSUMER ID"));
            assertFalse(isElementDisplayed(manualContactRecordSearchPage.VaCMSIdCol));
            assertTrue(manualContactRecordSearchPage.manualSearchConsumerExtIdNew.getText().equalsIgnoreCase(consumerId));
        } else if (ext.equalsIgnoreCase("VaCMS")) {
            assertTrue(manualContactRecordSearchPage.extConsumerId.getText().contains("VaCMS CONSUMER ID"));
            assertFalse(isElementDisplayed(manualContactRecordSearchPage.VaCMSIdCol));
            assertTrue(manualContactRecordSearchPage.manualSearchConsumerExtIdNew.getText().equalsIgnoreCase(consumerId));
        } else if (ext.equalsIgnoreCase("Internal")) {
            assertTrue(manualContactRecordSearchPage.extConsumerId.getText().contains("CONSUMER ID"));
            assertFalse(isElementDisplayed(manualContactRecordSearchPage.VaCMSIdCol));
            assertTrue(manualContactRecordSearchPage.manualSearchConsumerExtIdNew.getText().equalsIgnoreCase(consumerId));
        } else if (ext.equalsIgnoreCase("CHIP")) {
            assertTrue(manualContactRecordSearchPage.extCaseConsumerId.getText().contains("CHIP CONSUMER ID"));
            assertTrue(manualContactRecordSearchPage.manualSearchConsumerExtId.getText().equalsIgnoreCase(consumerId));
        }
    }

    @Then("Verify Search Results will display the \\(Internal) Consumer ID column and Contact Record ID {string}")
    public void verifySearchResultsWillDisplayTheInternalConsumerIDColumnAndContactRecordID(String contactId) {
        manualContactRecordSearchPage.callManagmentTab.click();
        assertTrue(manualContactRecordSearchPage.extConsumerId.getText().equalsIgnoreCase("CONSUMER ID"));
        assertFalse(isElementDisplayed(manualContactRecordSearchPage.VaCMSIdCol));
        assertTrue(manualContactRecordSearchPage.contactiD.getText().equalsIgnoreCase(contactId));
    }

    @Then("I expand first contact record after contact record search")
    public void iExpandFirstContactRecordAfterContactRecordSearch() {
        waitForVisibility(manualContactRecordSearchPage.frstConRecord, 10);
        manualContactRecordSearchPage.frstConRecord.click();
    }

    @And("Verify I will see a read only version of the Electronic Signature Captured checkbox")
    public void verifyIWillSeeAReadOnlyVersionOfTheElectronicSignatureCapturedCheckbox() {
        assertEquals(manualContactRecordSearchPage.elSigCapAfExpand.getAttribute("data-indeterminate"), "false");
    }

    @Then("Verify I will see a Business Unit as part of expanded search result")
    public void verify_I_will_see_a_Business_Unit_as_part_of_expanded_search_result() {
        assertTrue(manualContactRecordSearchPage.businessUnitExpandedSearch.isDisplayed());
    }

    @When("I pass {int} characters in {string} and {string} fields to verify search execution")
    public void i_pass_characters_in_and_fields_to_verify_search_execution(Integer int1, String string, String string2) {
        clearAndFillText(createGeneralTask.consumerFirstName, "Er");
        clearAndFillText(createGeneralTask.consumerLastName, "Vd");
        waitForVisibility(createGeneralTask.consumerSearchBtn, 10);
        click(createGeneralTask.consumerSearchBtn);
        waitFor(1);
        Assert.assertTrue(!isElementDisplayed(createGeneralTask.firstNameErrorMsg));
        Assert.assertTrue(!isElementDisplayed(createGeneralTask.lastNameErrorMsg));
    }

    @When("I pass {int} character in {string} and {string} fields to verify error message")
    public void i_pass_character_in_and_fields_to_verify_error_message(Integer int1, String string, String string2) {
        clearAndFillText(createGeneralTask.consumerFirstName, "E");
        clearAndFillText(createGeneralTask.consumerLastName, "V");
        waitForVisibility(createGeneralTask.consumerSearchBtn, 10);
        click(createGeneralTask.consumerSearchBtn);
        waitFor(1);
        Assert.assertTrue(createGeneralTask.firstNameErrorMsg.isDisplayed());
        Assert.assertTrue(createGeneralTask.lastNameErrorMsg.isDisplayed());
    }

    @And("I verify for contact record for type {string} and ContactType {string} with reasonForEdit")
    public void iSearchForContactRecordByContactidForTypeAndContactType(String type, String conType, List<String> reasonsForEdit) {
        if (type.equalsIgnoreCase("General") && conType.equalsIgnoreCase("Inbound") ||
                type.equalsIgnoreCase("ThirdParty") && conType.equalsIgnoreCase("Inbound") ||
                type.equalsIgnoreCase("Unidentified") && conType.equalsIgnoreCase("Inbound")) {
            editPageDropListcheck(manualContactRecordSearchPage.reasonForEdit, manualContactRecordSearchPage.reasonsForEditList, reasonsForEdit);
        }
    }

    public void editPageDropListcheck(WebElement dropType, List<WebElement> dropList, List<String> expectedList) {
        waitFor(1);
        dropType.click();
        waitFor(2);
        List<String> expected = new ArrayList<>();
        expectedList.forEach(each -> expected.add(each));
        List<String> actual = new ArrayList<>();
        dropList.forEach(each -> actual.add(each.getText()));
        Collections.sort(expected);
        Collections.sort(actual);
        System.out.println("actual = " + actual);
        System.out.println("expected = " + expected);
        Assert.assertEquals(actual, expected, "List actual Values did not meet expected list");
        unselectdropdown();
    }

    public void unselectdropdown() {
        int counter = 0;
        WebElement webPage = Driver.getDriver().findElement(By.xpath("//body"));
        do {
            webPage.click();
            counter++;
        } while (null == webPage.getAttribute("style") || counter == 5);
    }

    @Then("I verify for contact record for type {string} and ContactType {string} with InboundCallQueue")
    public void iVerifyForContactRecordForTypeAndContactTypeWithInboundCallQueue(String type, String conType, List<String> callQues) {
        if (type.equalsIgnoreCase("General") && conType.equalsIgnoreCase("Inbound") ||
                type.equalsIgnoreCase("ThirdParty") && conType.equalsIgnoreCase("Inbound") ||
                type.equalsIgnoreCase("Unidentified") && conType.equalsIgnoreCase("Inbound")) {
            editPageDropListcheck(manualContactRecordSearchPage.inboundCallQueueDropDown, manualContactRecordSearchPage.nonMultiDropList, callQues);
        }
    }

    @Then("I verify for contact record for type {string} and ContactType {string} with ConsumerType and PhoneNumber")
    public void iVerifyForContactRecordForTypeAndContactTypeWithConsumerType(String type, String conType, List<String> cType) {
        assertTrue(manualContactRecordSearchPage.contactPhone.isDisplayed(), "Phone number Not Displayed");
        if (type.equalsIgnoreCase("ThirdParty") && conType.equalsIgnoreCase("Inbound") ||
                type.equalsIgnoreCase("ThirdParty") && conType.equalsIgnoreCase("Outbound")) {
            editPageDropListcheck(manualContactRecordSearchPage.searchConsumerDropdown, manualContactRecordSearchPage.nonMultiDropList, cType);
        }
    }

    @Then("I verify for contact record for type {string} and ContactType {string} with CallerType")
    public void iVerifyForContactRecordForTypeAndContactTypeWithCallerType(String type, String conType) {
        if (type.equalsIgnoreCase("Unidentified") && conType.equalsIgnoreCase("Inbound") ||
                type.equalsIgnoreCase("Unidentified") && conType.equalsIgnoreCase("Outbound")) {
            assertEquals(manualContactRecordSearchPage.callerTypeVal.getText(), "Anonymous", "CallerType value is Incorrect");
        }
    }

    @Then("I verify for contact record for type {string} and ContactType {string} with Preferred Languages and Channel")
    public void iVerifyForContactRecordForTypeAndContactTypeWithPreferredLanguages(String type, String conType, List<String> preLans) {
        assertEquals(manualContactRecordSearchPage.channelType.getText(), "Phone", "Channel value is Incorrect");
        if (type.equalsIgnoreCase("ThirdParty") && conType.equalsIgnoreCase("Outbound") ||
                type.equalsIgnoreCase("Unidentified") && conType.equalsIgnoreCase("Outbound")) {
            selectDropDown(manualContactRecordSearchPage.callCampaignDropDown, "Outbound Calls - English");
            selectDropDown(manualContactRecordSearchPage.callCampaignDropDown, "Outbound Calls - Spanish");
            editPageDropListcheck(manualContactRecordSearchPage.prepeferedLanDrpDwn, manualContactRecordSearchPage.nonMultiDropList, preLans);
        }
    }

    @Then("I verify for contact record for type {string} and ContactType {string} with CallCampaign and OutcomeOfContact")
    public void iVerifyForContactRecordForTypeAndContactTypeWithCallCampaignAndOutcomeOfContact(String type, String conType, List<String> outCon) {
        assertEquals(manualContactRecordSearchPage.channelType.getText(), "Phone", "Channel value is Incorrect");
        if (type.equalsIgnoreCase("ThirdParty") && conType.equalsIgnoreCase("Outbound") ||
                type.equalsIgnoreCase("Unidentified") && conType.equalsIgnoreCase("Outbound")) {
            editPageDropListcheck(manualContactRecordSearchPage.callOutcomeDropDown, manualContactRecordSearchPage.nonMultiDropList, outCon);
        }
    }

    @Then("I verify for contact record has TranslationService on Edit page")
    public void iVerifyForContactRecordHasProgramAndTranslationServiceOnEditPage(List<String> trLans) {
        editPageDropListcheck(manualContactRecordSearchPage.translationLanDrpDwn, manualContactRecordSearchPage.nonMultiDropList, trLans);
    }

    @And("I verify for contact record has Program on Edit page")
    public void iVerifyForContactRecordHasProgramOnEditPage(List<String> program) {
        editPageDropListcheck(contactRecord.lstSelectProgram, manualContactRecordSearchPage.contactDetailsProgramsList, program);
    }

    @And("Verify Contact Record fields editable with ContactType {string}")
    public void verifyContactRecordfieldsContactType(String conType) {
        if (conType.equalsIgnoreCase("Inbound")) {
            selectRandomDropDownOption(contactRecord.inboundCallQueueDropDownSelection);
            selectRandomDropDownOption(contactRecord.contactChannelType);
            clearAndFillText(contactRecord.contactPhoneLabel, getRandomNumber(10));
            selectRandomOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram);
        } else if (conType.equalsIgnoreCase("Outbound")) {
            selectRandomDropDownOption(contactRecord.contactChannelType);
            clearAndFillText(contactRecord.contactPhoneLabel, getRandomNumber(10));
            selectRandomOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram);
            selectRandomDropDownOption(contactRecord.callCampaignReference);
            selectRandomDropDownOption(contactRecord.outcomeOfContact);
        }
    }

    @And("I verify that following Inbound Call Queue and Corresponding CVCC Business Unit Value is displayed")
    public void iVerifyInboundCallQueueHaveFollowingOptionswithCorrespondingCVCCBusinessUnitValue(List<String> options) {
        for (String option : options) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, option);
            assertEquals(activeContactPage.businessUnitCVCCvalue.getText(), "CVCC");
        }
    }

    @And("I verify that following Inbound Call Queue and Corresponding CVIU Business Unit Value is not displayed")
    public void iVerifyInboundCallQueueHaveFollowingOptionswithCorrespondingCVIUBusinessUnitValueNotDisplayed(List<String> options) {
        for (String option : options) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, option);
            assertFalse(activeContactPage.businessUnitDropdwn.getText().contains("CVUI"));
        }
    }

    @And("I verify that business unit dropdown is blank after Change a CVCC Inbound Call Queue to a CVIU Call Queue")
    public void iVerifyThatBusinessUnitDropdownIsBlank() {
        assertFalse(activeContactPage.businessUnitDropdwn.getText().contains("CVCC"));
        assertEquals(activeContactPage.businessUnitDropdwn.getText(), "", "It should clear business unit field");
    }

    @And("I verify that changing Inbound Call Queue from a CVCC value to another CVCC value then the Business Unit selection would be CVCC")
    public void iVerifyThatBusinessUnitDropdownStillCVCCafterChangingCVCCvalueToAnotherCVCC() {
        assertTrue(activeContactPage.businessUnitDropdwn.getText().contains("CVCC"));
        assertTrue(activeContactPage.businessUnitCVCCvalue.isDisplayed());
    }

    @Then("I can able to change the systematically chosen Business Unit value")
    public void iCanAbleToChangeTheSystematicallyChosenBusinessUnitValue() {
        activeContactPage.businessUnitDropdwn.click();
        List<WebElement> businessUnitValues = Driver.getDriver().findElements(By.xpath("//li[contains(@class, 'MuiButtonBase-root')]"));
        for(WebElement businessUnitValue : businessUnitValues) {
            businessUnitValue.click();
            activeContactPage.businessUnitDropdwn.click();
        }
    }

    @And("I am able to change the systematically chosen Business Unit value")
    public void iVerifyInboundCallQueueHaveFollowingOptionswithCorrespondingCVCCBusinessUnit(List<String> options) {
        for (String option : options) {
            selectOptionFromMultiSelectDropDown(activeContactPage.businessUnitDropdwn, option);
        }
    }

    @And("I search for contact record by contact firstName {string} and lastName {string}")
    public void iSearchForContactRecordByNameAndSurname(String firstName, String lastName) {
        manualContactRecordSearchPage.manualSearchFirstName.sendKeys(firstName);
        manualContactRecordSearchPage.manualSearchLastName.sendKeys(lastName);
        waitForSeconds(1);
        jsClick(manualContactRecordSearchPage.searchButton);
    }

    @When("I select {string} option from inbound call queue")
    public void i_select_option_from_inbound_call_queue(String option) {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, option);
    }

    @When("I verify {string} Program is selected")
    public void i_verify_Program_is_selected(String programOption) {
        assertEquals(manualContactRecordSearchPage.chosenProgram.getAttribute("value"), "HCC");
    }

    @Then("I verify I am able to unselect {string} program")
    public void i_verify_I_am_able_to_unselect_program(String programOption) {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.programs, programOption);
        waitFor(3);
        assertTrue(manualContactRecordSearchPage.chosenProgram.getAttribute("value").isEmpty());
    }

    @And("I search for contact record id by {string} CR type")
    public void iSearchForContactRecordByTaskType(String crType) {
        switch (crType) {
            case "General":
                manualContactRecordSearchPage.contactRecordNo.sendKeys("408045");
                break;
            case "ThirdParty":
                manualContactRecordSearchPage.contactRecordNo.sendKeys("412769");
                break;
            case "Unidentified":
                manualContactRecordSearchPage.contactRecordNo.sendKeys("392393");
                break;
        }
        jsClick(manualContactRecordSearchPage.searchButton);
    }

    @Then("I verify the dropdown values for DISPOSITION Contact Details dropdown list")
    public void iVerifyTheDropdownValuesForDISPOSITIONContactDetailsDropdownList(List<String> data) {
        editPageDropListcheck(manualContactRecordSearchPage.dispositionDrpDwn, manualContactRecordSearchPage.nonMultiDropList, data);
    }

    public void dropListcheck(WebElement dropType, List<String> expectedList) {
        waitFor(1);
        dropType.click();
        waitFor(2);
        List<String> expected = new ArrayList<>();
        expectedList.forEach(each -> expected.add(each));
        List<String> actual = new ArrayList<>();
        List<WebElement> droplist = new ArrayList<>(Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li")));
        droplist.forEach(each -> actual.add(each.getText()));
        Collections.sort(expected);
        Collections.sort(actual);
        System.out.println("actual = " + actual);
        System.out.println("expected = " + expected);
        Assert.assertEquals(actual, expected, "List actual Values did not meet expected list");
        actions.sendKeys(Keys.ESCAPE);
    }
}













