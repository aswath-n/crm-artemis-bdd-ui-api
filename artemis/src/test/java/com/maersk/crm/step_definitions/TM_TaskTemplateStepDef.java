package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMTaskFieldDetailsPage;
import com.maersk.crm.pages.tm.TMTaskTemplatePage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import io.cucumber.java.en_lol.AN;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.*;

public class TM_TaskTemplateStepDef extends BrowserUtils {

    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMTaskTemplatePage tmTaskTemplatePage = new TMTaskTemplatePage();
    TMTaskFieldDetailsPage tmTaskFieldDetailsPage = new TMTaskFieldDetailsPage();

    final ThreadLocal<String> templateName = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> templateDesc = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> newFieldname = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> fieldKey = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> field1 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> field2 = ThreadLocal.withInitial(String::new);
    Actions actions = new Actions(Driver.getDriver());

    /*This method navigates to Task Template List Page from left navigation menu
    Author -Vinuta
     */
    @Given("I navigate to Task Template Page")
    public void navigate_to_task_template() {
        Assert.assertTrue(tmProjectDetailsPage.projConfig.isDisplayed(), "Project configuration icon is  not displayed");
        tmProjectDetailsPage.projConfig.click();
        //Assert.assertTrue(tmProjectDetailsPage.taskTemplateNavigation.isDisplayed(), "Task template icon is  not displayed");
        scrollUpRobot();
        tmProjectDetailsPage.taskTemplateNavigation.click();
        waitFor(10);
    }

    /*This method is used to click on + icon(Add button) on Task Template List Page
    Author -Vinuta
     */
    @When("I click on add new task template button")
    public void i_click_on_add_new_task_template_button() {
        waitForVisibility(tmTaskTemplatePage.addTaskTemplateBtn, 5);
        Assert.assertTrue(tmTaskTemplatePage.addTaskTemplateBtn.isDisplayed(), "Add task template button is not displayed");
        tmTaskTemplatePage.addTaskTemplateBtn.click();
        waitForVisibility(tmTaskTemplatePage.templateDetailsLabel, 5);
        Assert.assertTrue(tmTaskTemplatePage.templateDetailsLabel.isDisplayed(), "Create template page is not displayed");
    }


    @And("I verify Add Field button is displayed and click it")
    public void i_verify_add_field_button_is_displayed() throws Throwable {
        waitForVisibility(tmTaskTemplatePage.addFieldButton, 5);
        Assert.assertTrue(tmTaskTemplatePage.addFieldButton.isDisplayed(), "Add Field button is not displayed");
        tmTaskTemplatePage.addFieldButton.click();
        waitFor(2);
    }

    @When("I select Create grouped field")
    public void i_select_create_grouped_field() throws Throwable {
        tmTaskFieldDetailsPage.CreateGrpFieldChkBox.click();
        Assert.assertTrue(tmTaskFieldDetailsPage.groupName.isDisplayed());
        Assert.assertTrue(tmTaskFieldDetailsPage.AddFieldButton.isDisplayed());

    }

    @Then("I verify I am able to create multiple fields")
    public void i_verify_i_am_able_to_create_multiple_fields() throws Throwable {

        tmTaskFieldDetailsPage.groupName.sendKeys(getRandomString(8));

        field1.set(getRandomString(10));
        tmTaskFieldDetailsPage.displayNameInputbx.sendKeys(field1.get());
        selectDropDown(tmTaskFieldDetailsPage.fieldTypeDrpDwn, "Checkbox");
        waitFor(3);
        Assert.assertTrue(!tmTaskFieldDetailsPage.fieldKeyInputBx.getAttribute("value").isEmpty());

        tmTaskFieldDetailsPage.AddFieldButton.click();
        Assert.assertTrue(tmTaskFieldDetailsPage.deleteBtn.isDisplayed());

        field2.set(getRandomString(10));
        tmTaskFieldDetailsPage.displayNameInputbx2.sendKeys(field2.get());
        selectDropDown(tmTaskFieldDetailsPage.fieldTypeDrpDwn2, "Checkbox");
        waitFor(3);
        Assert.assertTrue(!tmTaskFieldDetailsPage.fieldKeyInputBx2.getAttribute("value").isEmpty());

        tmTaskFieldDetailsPage.saveButton.click();
        waitFor(3);
        waitForVisibility(tmTaskTemplatePage.FieldCreatedSuccessMsg, 10);
        tmTaskTemplatePage.FieldCreatedSuccessMsg.isDisplayed();
    }

    @And("I verify dropdown values in field type")
    public void i_verify_dropdown_values_in_field_type() throws Throwable {

        List<String> fieldTypesList = Arrays.asList("Alphabetic 15", "Alphabetic 30", "Alphabetical", "Alphabetical 100", "Alphabetical 50", "Alphanumeric", "Alphanumeric & Special Characters 100",
                "Alphanumeric + Special Character 10", "Alphanumeric + Special Characters 60", "Alphanumeric 140", "Alphanumeric 30", "Alphanumeric 3K characters", "Alphanumeric 50",
                "Auto-complete dropdown", "Checkbox", "Date", "Date picker", "Dropdown", "Long", "Multi-select dropdown", "Numeric", "Numeric 10", "Numeric 12", "Numeric 9", "Single-select dropdown",
                "String", "Text", "Text 30", "Time Picker");
        tmTaskFieldDetailsPage.fieldTypeDrpDwn.click();
        waitFor(1);
        List<String> dropdwnValues = new ArrayList<>();
        for (WebElement e : tmTaskFieldDetailsPage.fieldTypeDrpDwnList) {
            dropdwnValues.add(e.getText());
        }
        Assert.assertEquals(fieldTypesList.size(), dropdwnValues.size(), "List elements not matched ");           // This step verifies dropdown list size
        Assert.assertEquals(fieldTypesList, dropdwnValues, "All dropdown values are not present ");               // This step verifies dropdown list contents match or not

        actions.moveToElement(tmTaskFieldDetailsPage.displayNameInputbx).click().perform();
    }

    @And("I add new field and save it")
    public void i_add_new_field_and_save_it() throws Throwable {

        Assert.assertTrue(tmTaskFieldDetailsPage.TaskFieldDetailsLabel.isDisplayed(), "Task Field Details label is not displayed");

        waitForVisibility(tmTaskFieldDetailsPage.displayNameInputbx, 5);
        Assert.assertTrue(tmTaskFieldDetailsPage.displayNameInputbx.isDisplayed(), "Display Name input box is not displayed");
        waitForVisibility(tmTaskFieldDetailsPage.fieldTypeDrpDwn, 5);
        Assert.assertTrue(tmTaskFieldDetailsPage.fieldTypeDrpDwn.isDisplayed(), "Field Type dropdown is not displayed");
        waitForVisibility(tmTaskFieldDetailsPage.fieldKeyInputBx, 5);
        Assert.assertTrue(tmTaskFieldDetailsPage.fieldKeyInputBx.isDisplayed(), "Field key input box is not displayed");

        tmTaskFieldDetailsPage.displayNameInputbx.sendKeys("abc12345@&randomString");
        selectDropDown(tmTaskFieldDetailsPage.fieldTypeDrpDwn, "Checkbox");
        waitFor(3);
        System.out.println(tmTaskFieldDetailsPage.fieldKeyInputBx.getAttribute("value"));
        Assert.assertTrue(!tmTaskFieldDetailsPage.fieldKeyInputBx.getAttribute("value").isEmpty());

        tmTaskFieldDetailsPage.displayNameInputbx.sendKeys(Keys.CONTROL + "a");                         //---From this
        tmTaskFieldDetailsPage.displayNameInputbx.sendKeys(Keys.DELETE);
        waitFor(1);
        Assert.assertTrue(tmTaskFieldDetailsPage.fieldKeyInputBx.getAttribute("value").isEmpty());                  // --- to this, verifies defect CP-28119

        newFieldname.set(getRandomString(20));
        Assert.assertTrue(newFieldname.get().matches("^(?!\\d+$)\\w+\\S+"));  //--This step verifies display name contains of Alphanumeric, spaces , special characters

        tmTaskFieldDetailsPage.displayNameInputbx.sendKeys(newFieldname.get());

        selectDropDown(tmTaskFieldDetailsPage.fieldTypeDrpDwn, "Date picker");
        waitFor(3);
        System.out.println(tmTaskFieldDetailsPage.fieldKeyInputBx.getAttribute("value"));
        Assert.assertTrue(!tmTaskFieldDetailsPage.fieldKeyInputBx.getAttribute("value").isEmpty());             //-- This step verifies if field key has been generated or not
        fieldKey.set(tmTaskFieldDetailsPage.fieldKeyInputBx.getAttribute("value"));
        tmTaskFieldDetailsPage.saveButton.click();
        waitForVisibility(tmTaskTemplatePage.FieldCreatedSuccessMsg, 20);
        Assert.assertTrue(tmTaskTemplatePage.FieldCreatedSuccessMsg.isDisplayed());        //--This step verifies Success message is displayed or not

    }

    @Then("I add new field with same field key")
    public void i_add_new_field_with_same_field_key() {
        tmTaskFieldDetailsPage.displayNameInputbx.sendKeys("pqr12345@&randomString");
        selectDropDown(tmTaskFieldDetailsPage.fieldTypeDrpDwn, "Checkbox");

        tmTaskFieldDetailsPage.fieldKeyInputBx.sendKeys(Keys.CONTROL + "a");
        tmTaskFieldDetailsPage.fieldKeyInputBx.sendKeys(Keys.DELETE);
        tmTaskFieldDetailsPage.fieldKeyInputBx.sendKeys(fieldKey.get());
        waitFor(3);
        tmTaskFieldDetailsPage.saveButton.click();
    }

    @Then("I verify if error message for field key is displayed")
    public void i_verify_if_error_message_is_displayed() throws Throwable {
        waitFor(1);
        Assert.assertTrue(tmTaskFieldDetailsPage.errormsgFieldKey.isDisplayed(), "No error msg displayed");
    }

    @Then("I verify if error message for field name is displayed")
    public void i_verify_if_error_message_for_field_name_is_displayed() {
        waitFor(1);
        Assert.assertTrue(tmTaskFieldDetailsPage.errormsgFieldName.isDisplayed(), "No error msg displayed");
    }

    @Then("I add new field with same field name")
    public void i_add_new_field_with_same_field_name() {
        tmTaskFieldDetailsPage.displayNameInputbx.sendKeys(newFieldname.get());
        selectDropDown(tmTaskFieldDetailsPage.fieldTypeDrpDwn, "Checkbox");
        waitFor(3);
        tmTaskFieldDetailsPage.saveButton.click();
    }


    /*This method verifies Template Name field is displayed & mandatoryerror is displayed when not populated
    Author -Vinuta
     */
    @Then("I verify template name field is present & mandatory")
    public void i_verify_template_name_field_is_present_mandatory() {
        Assert.assertTrue(tmTaskTemplatePage.templateName.isDisplayed(), "Template Name field is not displayed");
        tmTaskTemplatePage.saveTaskTemplateBtn.click();
        Assert.assertTrue(tmTaskTemplatePage.templateNameMandatoryError.isDisplayed(), "Template name mandatory error is not displayed");
    }

    /*This method verifies that Template Name accepts only alphanumeric & special characters
    Input paramter - number of characters    Author -Vinuta
     */
    @Then("Template name accepts {int} alphanumeric & special characters")
    public void template_name_accepts_alphanumeric_special_characters(Integer int1) {
        String text = "TaskTemplate 123 >;,?[])( 12387 | {}+=^ 098hbs-<%.6";
        tmTaskTemplatePage.templateName.sendKeys(text);
        Assert.assertTrue(tmTaskTemplatePage.templateName.getAttribute("value").length() == int1, "Template name field length is not 50 characters");
    }

    /*This method verifies Template Description field is displayed & is optional
    Author -Vinuta
    refactorBy: paramita Date:1-31-2020
     */
    @Then("I verify template description field is optional")
    public void i_verify_template_description_field_is_present_optional() {
        Assert.assertTrue(tmTaskTemplatePage.templateDescription.isDisplayed(), "Template description field is not displayed");
        clearAndFillText(tmTaskTemplatePage.templateDescription, " ");
        tmTaskTemplatePage.templateName.sendKeys(getRandomString(8));
        tmTaskTemplatePage.saveTaskTemplateBtn.click();
        waitFor(6);
        Assert.assertTrue(tmTaskTemplatePage.addTaskTemplateBtn.isDisplayed(), "Template list is not displayed");
    }

    /*This method verifies Template Description accepts alphanumeric characters
    Input paramters - number of characters
    Author -Vinuta
    refactorBy: Vidya Date:1-28-2020
    refactorBy: Vinuta July-05-2021 - text fields do not allow anything except alphanumeric & >150 chars, so no error is displayed
     */
    @Then("Template description accepts {int} alphanumeric characters")
    public void template_description_accepts_alphanumeric_characters(Integer int1) {
        tmTaskTemplatePage.templateDescription.sendKeys(getRandomString(150));
        Assert.assertTrue(tmTaskTemplatePage.templateDescription.getAttribute("value").length() == int1, "Description field length is not 150 characters");
        clearAndFillText(tmTaskTemplatePage.templateDescription, getRandomString(175));
        Assert.assertTrue(tmTaskTemplatePage.templateDescription.getAttribute("value").length() == int1, "Description field length is not 150 characters");
        //Assert.assertTrue(tmTaskTemplatePage.desFieldLevelErrorMsg.isDisplayed());
    }

    /*This method verifies the default selected fields are displayed for every new task template
    Author -Vinuta
    refactorBy: Vidya Date:1-28-2020
     */
    @Then("I verify default fields in selected fields sections")
    public void default_fields_in_selected_section() {
        tmTaskTemplatePage.taskTemplateBackArrow.click();
        tmTaskTemplatePage.addTaskTemplateBtn.click();
        Assert.assertTrue(tmTaskTemplatePage.selectedFieldsLabel.isDisplayed(), "Selected fields label is not displayed");
        List<String> expectedLabels = Arrays.asList("Assignee", "Created By", "Created On", "Due Date", "Due In", "Priority",
                "Status", "Status Date", "Task ID", "Task Info", "Task Type");
        for (int i = 0; i < tmTaskTemplatePage.defaultFieldValues.size(); i++) {
            waitFor(1);
            scrollDownUsingActions(1);
            Assert.assertEquals(tmTaskTemplatePage.defaultFieldValues.get(i).getText(), expectedLabels.get(i));
            scrollDownUsingActions(1);
        }
    }

    /*This method verifies that All Fields section has given optional fields
    Author -Vinuta
    refactorBy: Vidya Date:1-28-2020
    refactorBy: Basha Date:5-21-2020
     */
    @Then("I verify additional fields in all fields sections")
    public void additional_fields_present() {
        List<String> expectedFields = new ArrayList<>();
        Assert.assertTrue(tmTaskTemplatePage.allFieldsLabel.isDisplayed(), "All fields label is not displayed");
        List<String> expectedLabels = Arrays.asList("Accessibility Needed", "Action Taken", "Action Taken Single", "Address Line 1", "Address Line 2", "Address City", "Address State", "Address Zip Code", "Address Source", "Address Type",
                "Appeal Case Summary Status", "Appeal Reason", "Appeal Source", "Appeals Case Summary Due Date", "Appeals Case Summary Hyperlink", "Appellant", "Application ID", "Application Received Date", "Application Signature Date",
                "Application Source", "Application Status", "Application Type", "Appointment Date", "Appointment Time", "Business Unit", "CSR Name", "Caller Name", "Case Status", "Case Worker First Name", "Case Worker Last Name", "Channel",
                "Community Outreach Session Type", "Complaint", "Complaint About", "Complaint Sub-Type", "Complaint Type", "Conference ID", "ConnectionPoint Contact Record ID", "ConnectionPoint SR ID", "ConnectionPoint Task ID", "Consumer Satisfied?",
                "Contact Reason", "CoverVA Attendance", "CoverVA Received Date", "Current Plan", "DMAS Feedback", "DMAS Received Date", "DMAS Reviewer", "Date Appeal Closed", "Date DMAS Review Started", "Date Decision Letter Sent", "Date Follow-up Email Sent",
                "Date Grievance Received", "Date Health Plan Contacted", "Date Ready for DMAS Review", "Date Received", "Date Resent", "Date State Notified", "Date Summary Accepted", "Date Summary Returned to CVA", "Date Summary Sent", "Date Translation Escalated",
                "Date Translation Mailed", "Date Translation Received", "Date of Birth", "Date of Contact", "Date of Voicemail", "Decision", "Decision Source", "Decision in DMIS Date", "Denial Reason", "Disenrollment Date", "Disenrollment Reason", "Disposition",
                "Document Due Date", "Document Sub-Type", "Eligibility Decision", "Email", "End Date", "Escalated", "Escalation Reason", "Ex Parte?", "Expedited", "External Application ID", "External Case ID", "External Consumer ID", "Facility Name", "Facility Type",
                "Fair Hearing Involved Parties", "Fair Hearing Location", "Fair Hearing Method", "Final Decision", "First Name", "Last Name", "From Email", "From Name", "From Phone", "Grievance #", "HPE?", "Hearing Officer Name", "Hospital Name", "Inbound Correspondence ID",
                "Inbound Correspondence Type", "Inbound Correspondence Workable Flag", "Incident Date", "Information Type", "Invalid", "Invalid Address Reason", "Invalid Reason", "Issue Type", "Language", "Letter Sent", "Locality", "MI Received Date", "Member Attendance", "Message",
                "Message Subject", "Missing Info Required?", "Name", "New Address Line 1", "New Address Line 2", "New Address City", "New Address State", "New Address Zip Code", "Newborn Name", "No Of Applicants in Household", "No Of Approved Applicants", "Notification ID",
                "Old Address Line 1", "Old Address Line 2", "Old Address City", "Old Address State", "Old Address Zip Code", "Organization", "Other", "Outbound Correspondence ID", "Outbound Correspondence Type", "Outcome", "Outreach Location", "Phone", "Phone Number Extension",
                "Plan Change Reason", "Plan Effective Date", "Plan ID", "Plan Name", "Plan Start Date", "Pre-Hearing Outcome", "Pre-Hearing Reason", "Preferred Call Back Date", "Preferred Call Back Time", "Preferred Language", "Preferred Phone", "Program", "Provider Address Line 1",
                "Provider Address Line 2", "Provider Address City", "Provider Address State", "Provider Address Zip Code", "Provider Address County", "Provider Email", "Provider First Name", "Provider Last Name", "Provider NPI", "Provider Phone", "Provider Record Issue", "Provider State ID",
                "RID #", "Reason", "Received Date", "Remand Completion Date", "Remand Due Date", "Remand Instructions", "Remand Reason", "Request Type", "Requested Date", "Requested New Plan", "Requested New Provider", "Returned Mail Reason", "Returned Mail Received Date", "Review Outcome",
                "Service Request ID", "Session Region", "Site Type", "Site Address Line 1", "Site Name", "Site Address Line 2", "Site Address City", "Site Address State", "Site Address Zip Code", "Site County", "Site Contact First Name", "Site Contact Last  Name", "Site Contact Phone",
                "Site Contact Fax", "Site Contact Email", "Start Date", "Summary in DMIS Date", "Time of Voicemail", "Transfer to LDSS Required?", "Transferred Date", "Urgent Access to Care", "Used Tobacco in the last 6 months?", "VCL Due Date", "VCL Sent Date", "Address Line 1",
                "Address Line 2", "City", "State", "Zip", "Sent NOA Date", "Initial Review Date", "Desired Plan", "Requester VaCMS Case ID", "Requester MMIS Enrollee ID", "Medicaid ID Card Holder 2 MMIS Enrollee ID", "Request Reason 2", "Medicaid ID Card Holder 3 Name", "Medicaid ID Card Holder 3 MMIS Enrollee ID",
                "Requester SSN", "Requester DOB", "Medicaid ID Card Holder 1 Name", "Medicaid ID Card Holder 1 MMIS Enrollee ID", "Request Reason 1", "Medicaid ID Card Holder 2 Name", "Request Reason 3", "Medicaid ID Card Holder 4 Name", "Medicaid ID Card Holder 4 MMIS Enrollee ID", "Request Reason 4",
                "Medicaid ID Card Holder 5 Name", "Medicaid ID Card Holder 5 MMIS Enrollee ID", "Request Reason 5");
        for (int j = 0; j < tmTaskTemplatePage.allFieldValues.size(); j++)
            expectedFields.add(tmTaskTemplatePage.allFieldValues.get(j).getAttribute("label"));

        for (int i = 0; i < expectedLabels.size(); i++) {
            //  Assert.assertEquals(tmTaskTemplatePage.allFieldValues.get(i).getAttribute("label"), expectedLabels.get(i));
            Assert.assertTrue(expectedFields.contains(expectedLabels.get(i)), expectedLabels.get(i) + " not found");
        }
    }

    @Then("I verify newly added field has appreared in all fields sections")
    public void i_verify_newly_added_field_has_appreared_in_all_fields_sections() throws Throwable {
        Assert.assertTrue(tmTaskTemplatePage.allFieldsLabel.isDisplayed(), "All fields label is not displayed");

        newFieldname.set(newFieldname.get().substring(0, 1).toUpperCase() + newFieldname.get().substring(1));
        Assert.assertTrue(tmTaskFieldDetailsPage.getFieldLocator(newFieldname.get()).isDisplayed(), "Field did not appeared");
    }

    @Then("I verify newly added fields have appreared in all fields sections")
    public void i_verify_newly_added_fields_have_appreared_in_all_fields_sections() throws Throwable {
        Assert.assertTrue(tmTaskTemplatePage.allFieldsLabel.isDisplayed(), "All fields label is not displayed");

        field1.set(field1.get().substring(0, 1).toUpperCase() + field1.get().substring(1));
        Assert.assertTrue(tmTaskFieldDetailsPage.getFieldLocator(field1.get()).isDisplayed(), "Field1 did not appeared");

        field2.set(field2.get().substring(0, 1).toUpperCase() + field2.get().substring(1));
        Assert.assertTrue(tmTaskFieldDetailsPage.getFieldLocator(field2.get()).isDisplayed(), "Field2 did not appeared");


    }

    /*This method populates Template Name & TemplateDescription fields
    Input Parameters - if name, description are passed, else if RANDOM is passed, random name, description are generated
    Author -Vinuta
    refactorBy: Vidya Date:1-28-2020
     */
    @When("I populate template name as {string} template description as {string}")
    public void i_populate_template_name_as_template_description_as(String name, String description) {
        if (name.equalsIgnoreCase("random"))
            name = "Task " + getRandomString(5);
        if (description.equalsIgnoreCase("random"))
            description = "Description " + getRandomString(5) + " " + getRandomNumber(5);
        clearAndFillText(tmTaskTemplatePage.templateName, name);
        clearAndFillText(tmTaskTemplatePage.templateDescription, description);
        templateName.set(name);
        templateDesc.set(description);
        waitFor(3);
    }

    /*This method clicks on Save button on Task Template Detail Page
    Author -Vinuta
     */
    @When("I click on save task template button")
    public void i_click_on_save_task_template_button() {
        Assert.assertTrue(tmTaskTemplatePage.saveTaskTemplateBtn.isDisplayed(), "Save button is not displayed");
        tmTaskTemplatePage.saveTaskTemplateBtn.click();
    }

    /*This method validates success message is displayed when task template is saved
    Author -Vinuta
     */
    @Then("Success message is displayed on task template list page")
    public void success_message_is_displayed_on_task_template_list_page() {
        waitForVisibility(tmTaskTemplatePage.successSnackbar, 10);
        Assert.assertTrue(tmTaskTemplatePage.successSnackbar.isDisplayed());
        Assert.assertTrue(tmTaskTemplatePage.templateCreatedMessage.isDisplayed(), "template created successfully pop-up is not displayed");
        waitForVisibility(tmTaskTemplatePage.addTaskTemplateBtn, 10);
    }

    /*This method clicks on Cancel button on Task Template Page
    Author -Vinuta
     */
    @When("I click on cancel task template button")
    public void i_click_on_cancel_task_template_button() {
        Assert.assertTrue(tmTaskTemplatePage.cancelTaskTemplateBtn.isDisplayed(), "Cancel button is not displayed");
        tmTaskTemplatePage.cancelTaskTemplateBtn.click();
        waitForVisibility(tmTaskTemplatePage.WarningPopupCancelBtn, 5);
    }

    /*This method clicks n back arrow on Task Template Detail page
    Author -Vinuta
     */
    @When("I click on back arrow on task template button")
    public void i_click_on_backarrow_task_template_button() {
        Assert.assertTrue(tmTaskTemplatePage.taskTemplateBackArrow.isDisplayed(), "Back arrow is not displayed");
        tmTaskTemplatePage.taskTemplateBackArrow.click();
        waitForVisibility(tmTaskTemplatePage.WarningPopupCancelBtn, 5);
    }

    @When("I click on back arrow on task template button to return to create another template")
    public void i_click_on_backarrow_task_template_button_to_return() {
        Assert.assertTrue(tmTaskTemplatePage.taskTemplateBackArrow.isDisplayed(), "Back arrow is not displayed");
        tmTaskTemplatePage.taskTemplateBackArrow.click();
    }

    /*This method verifies if warning message is displayed on Task Template Detail page
    Author -Vinuta
     */
    @Then("Warning message is displayed on task template details page")
    public void warning_message_is_displayed_on_task_template_details_page() {
        waitFor(1);
        Assert.assertTrue(tmTaskTemplatePage.cancelWarningPopUp.isDisplayed(), "Warning Pop Up is not displayed");
    }

    /*This method clicks on Cancel button on warnin gmessage of Task Template Detail Page
    Author -Vinuta
     */
    @When("I click on cancel button on task template warning message")
    public void i_click_on_cancel_button_on_task_template_warning_message() {
        tmTaskTemplatePage.WarningPopupCancelBtn.click();
        Assert.assertTrue(tmTaskTemplatePage.templateName.getAttribute("value").length() > 0,
                "Template Name did not remain the same");
        Assert.assertTrue(tmTaskTemplatePage.templateDescription.getAttribute("value").length() > 0,
                "Template description did not remain the same");
    }

    /*This method clicks on Continue button on warnin gmessage of Task Template Detail Page
    Author -Vinuta
     */
    @When("I click on continue button on task template warning message")
    public void i_click_on_continue_button_on_task_template_warning_message() {
        waitFor(1);
        Assert.assertTrue(tmTaskTemplatePage.WarningPopupContinueBtn.isDisplayed(), "Continue button on warning pop up is not displayed");
        tmTaskTemplatePage.WarningPopupContinueBtn.click();
    }

    /*This method verifies if Task Template List Page is displayed
    Author -Vinuta
     */
    @Then("I verify task template list is displayed")
    public void task_template_list_page_displayed() {
        waitForVisibility(tmTaskTemplatePage.templateListLabel, 5);
        Assert.assertTrue(tmTaskTemplatePage.templateListLabel.isDisplayed(), "Template list page is not displayed");
    }

    /*This method selects the checkboxes of fields from All Fields section
    Author -Vinuta
    refactorBy: Vidya Date:1-29-2020
     */
    @When("I select fields from All Fields section")
    public void i_select_fields_from_All_Fields_section(List<String> options) {
        waitFor(5);
        scrollDown();
        WebElement e;
        for (String s : options) {
            e = Driver.getDriver().findElement(By.xpath("//span[text()='" + s + "']/../span/span/input"));
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            highLightElement(e);
            scrollToElement(e);
            js.executeScript("arguments[0].click()", e);
        }

    }

    /*This method clicks on Add Fields button after selection additional fields on Task Template Detail Page
    Author -Vinuta
    refactorBy: Vidya Date:1-29-2020
     */
    @When("I click on Add Fields button")
    public void i_click_on_Add_Fields_button() {
        scrollToElement(tmTaskTemplatePage.addFieldsBtn);
        Assert.assertTrue(tmTaskTemplatePage.addFieldsBtn.isDisplayed(), "Add fields button is not displayed");
        jsClick(tmTaskTemplatePage.addFieldsBtn);
    }

    @Then("I click on edit field button for {string}")
    public void i_click_on_edit_field_button_for_something(String strArg1) {
        jsClick(tmTaskTemplatePage.selectedTaskFieldeditButton);
        Assert.assertTrue(tmTaskTemplatePage.editFieldPanel.isDisplayed(), "Edit fields panel is not displayed");
    }

    @Then("I can verify user is able to choose whether the field is required, optional, or conditional to Create the Task")
    public void i_can_verify_user_is_able_to_choose_whether_the_field_is_required_optional_or_conditional_to_Create_the_Task() {
        tmTaskTemplatePage.TocreateTaskOption("required").click();
        Assert.assertTrue(tmTaskTemplatePage.TocreateTaskOption("required").isSelected());
        tmTaskTemplatePage.TocreateTaskOption("optional").click();
        Assert.assertTrue(tmTaskTemplatePage.TocreateTaskOption("optional").isSelected());
        tmTaskTemplatePage.TocreateTaskOption("conditional").click();
        Assert.assertTrue(tmTaskTemplatePage.TocreateTaskOption("conditional").isSelected());
    }

    @And("I can verify user is able to choose whether the field is {string} to Create the Task")
    public void iCanVerifyUserIsAbleToChooseWhetherTheFieldIsToCreateTheTask(String option) {
        tmTaskTemplatePage.TocreateTaskOption(option).click();
        Assert.assertTrue(tmTaskTemplatePage.TocreateTaskOption(option).isSelected());
    }

    @And("I can verify user is able to choose whether the field is {string} to Complete the Task")
    public void iCanVerifyUserIsAbleToChooseWhetherTheFieldIsToCompleteTheTask(String option) {
        tmTaskTemplatePage.ToCompleteTaskOption(option).click();
        Assert.assertTrue(tmTaskTemplatePage.ToCompleteTaskOption(option).isSelected());
    }

    @Then("I verify the selected options are saved correctly for {string} to Create the Task")
    public void iVerifyTheSelectedOptionsAreSavedCorrectlyWhenSavedtoCreateTask(String option) {
        tmTaskTemplatePage.editFieldSaveButton.click();
        jsClick(tmTaskTemplatePage.selectedTaskFieldeditButton);
        Assert.assertTrue(tmTaskTemplatePage.editFieldPanel.isDisplayed(), "Edit fields panel is not displayed");
        Assert.assertTrue(tmTaskTemplatePage.TocreateTaskOption(option).isSelected());
    }

    @Then("I verify the selected options are saved correctly for {string} to Complete the Task")
    public void iVerifyTheSelectedOptionsAreSavedCorrectlyWhenSavedToCompleteTask(String option) {
        tmTaskTemplatePage.editFieldSaveButton.click();
        jsClick(tmTaskTemplatePage.selectedTaskFieldeditButton);
        Assert.assertTrue(tmTaskTemplatePage.editFieldPanel.isDisplayed(), "Edit fields panel is not displayed");
        Assert.assertTrue(tmTaskTemplatePage.ToCompleteTaskOption(option).isSelected());
    }

    @Then("I can verify user is able to choose whether the field is required, optional, or conditional to Complete the Task")
    public void i_can_verify_user_is_able_to_choose_whether_the_field_is_required_optional_or_conditional_to_Complete_the_Task() {
        tmTaskTemplatePage.ToCompleteTaskOption("required").click();
        Assert.assertTrue(tmTaskTemplatePage.ToCompleteTaskOption("required").isSelected());
        tmTaskTemplatePage.ToCompleteTaskOption("optional").click();
        Assert.assertTrue(tmTaskTemplatePage.ToCompleteTaskOption("optional").isSelected());
        tmTaskTemplatePage.ToCompleteTaskOption("conditional").click();
        Assert.assertTrue(tmTaskTemplatePage.ToCompleteTaskOption("conditional").isSelected());
    }

    @Then("I can verify user is able to choose whether the field appears on the task slider")
    public void i_can_verify_user_can_choose_whether_field_Appears_on_task_slider() {
        tmTaskTemplatePage.displayOnTaskbarChkBox.click();
        Assert.assertTrue(tmTaskTemplatePage.displayOnTaskbarChkBox.isSelected());
    }

    @And("I verify To Create Task field is by default selected with {string} value")
    public void i_verify_to_create_task_and_to_complete_task_fields_are_by_default_selected_with_something_value(String value) throws Throwable {
        Assert.assertTrue(tmTaskTemplatePage.TocreateTaskOption(value).isSelected(), "");
    }

    @And("I verify To Complete Task field is by default selected with {string} value")
    public void i_verify_to_complete_task_fields_is_by_default_selected_with_something_value(String value) {
        Assert.assertTrue(tmTaskTemplatePage.ToCompleteTaskOption(value).isSelected(), "");
    }

    /*This method checks if the fields selected are removed from All Fields section & added to Selected Fields section
    Author -Vinuta
    refactorBy: Vidya Date:1-29-2020
     */
    @Then("fields are removed from all fields and added to selected fields section")
    public void fields_are_removed_from_all_fields_and_added_to_selected_fields_section(List<String> options) {
        waitFor(1);
        WebElement allField;
        WebElement selectedField;
        for (String s : options) {
            try {
                allField = Driver.getDriver().findElement(By.xpath("//span[text()='ALL FIELDS']/../following-sibling::div//span[@label='" + s + "']"));
                Assert.assertEquals(allField.getAttribute("aria-disabled"), "true", "Field is displayed although moved to Selected Field section");
            } catch (Exception e) {
                Assert.assertTrue(true);
            }
            selectedField = Driver.getDriver().findElement(By.xpath("//span[text()='SELECTED FIELDS']/../following-sibling::div//span[text()='" + s + "']"));
            Assert.assertTrue(selectedField.isDisplayed(), "Field is not displayed although moved to Selected Field section");
        }
    }

    /*This method selects the checkboxes of fields from Selected Fields section
    Author -Vidya
     */
    @When("I select fields from Selected Fields section")
    public void i_select_fields_from_Selected_Fields_section(List<String> options) {
        waitFor(1);
        WebElement e;
        for (String s : options) {
            e = Driver.getDriver().findElement(By.xpath("//span[text()='SELECTED FIELDS']/../following-sibling::div//span[text()='" + s + "']"));
            e.click();
        }

    }

    /*This method clicks on Remove Fields button after selecting additional fields on Task Template Detail Page
   Author -Vinuta
    refactorBy: Vidya Date:1-29-2020
    */
    @When("I click on Remove Fields button")
    public void i_click_on_Remove_Fields_button() {
        scrollToElement(tmTaskTemplatePage.removeFieldsBtn);
        Assert.assertTrue(tmTaskTemplatePage.removeFieldsBtn.isDisplayed(), "Add fields button is not displayed");
        jsClick(tmTaskTemplatePage.removeFieldsBtn);
    }

    /*This method checks if the fields selected are removed from Selected Fields section & added to All Fields section
   Author -Vinuta
    refactorBy: Vidya Date:1-29-2020
    */
    @Then("fields are removed from selected fields and added to all fields section")
    public void fields_are_removed_from_selected_fields_and_added_to_all_fields_section(List<String> options) {
        waitFor(1);
        WebElement allField;
        WebElement selectedField;
        for (String s : options) {
            try {
                selectedField = Driver.getDriver().findElement(By.xpath("//span[text()='SELECTED FIELDS']/../following-sibling::div//span[text()='" + s + "']"));
                Assert.assertFalse(selectedField.isDisplayed(), "Field is not displayed although moved to Selected Field section");
            } catch (Exception e) {
                Assert.assertTrue(true);
            }
            allField = Driver.getDriver().findElement(By.xpath("//span[text()='ALL FIELDS']/../following-sibling::div//span[@label='" + s + "']"));
            scrollToElement(allField);
            Assert.assertEquals(allField.getAttribute("aria-disabled"), "false", "Field is displayed although moved to Selected Field section");
        }
    }

    /*This method verifies the attrobutes on task template list
    Author - Vinuta 25th March
    refactorBy: Vidya Date:1-29-2020
     */
    @Then("I verify attributes on the task template list are correct")
    public void verifyTemplateListAttributes() {
        List<WebElement> ele = Driver.getDriver().findElements(By.xpath("//tr//td//a[text()='" + templateName.get() + "']/../following-sibling::td"));
        Assert.assertTrue(ele.get(0).getText().equalsIgnoreCase("1"));
        Assert.assertTrue(ele.get(1).getText().equalsIgnoreCase(templateDesc.get()));
        Assert.assertTrue(ele.get(2).getText().equalsIgnoreCase(getCurrentDate()));
        Assert.assertTrue(ele.get(3).getText().equalsIgnoreCase("Service AccountOne"));
    }


    @And("I expand the task template {string}")
    public void expandTemplate(String templateName) {
        Driver.getDriver().findElement(By.xpath("//a[contains(text(),'" + templateName + "')]//parent::td//parent::tr//td//button")).click();
    }

    /*This method selects or clicks on the given template name to view template details
   Author - Vinuta on March 13th, 2019
   Parameters - Template name
    */
    @When("I select template {string} on Task Template List page")
    public void i_select_template_on_Task_Template_List_page(String templateName) {
        waitFor(5);
        if (templateName.equalsIgnoreCase("random"))
            templateName = this.templateName.get();
        WebElement template = Driver.getDriver().findElement(By.xpath("//a[text()='" + templateName + "']"));
        Assert.assertTrue(template.isDisplayed(), "Template is not displayed");
        jsClick(template);
        waitForVisibility(tmTaskTemplatePage.selectedFieldsLabel, 5);
        Assert.assertTrue(tmTaskTemplatePage.selectedFieldsLabel.isDisplayed(), "Template details page is not displayed");
    }

    /*This method selects or clicks on the given template name to view template details - New
      Author - Basha  */
    @When("I select template {string} on Task Template page")
    public void i_select_template_on_Task_Template_page(String templateName) {
        waitFor(15);
        if (templateName.equalsIgnoreCase("random"))
            templateName = this.templateName.get();
        boolean result = retryingFindClick(By.xpath("//a[text()='" + templateName + "']"));
        this.templateName.set(templateName);
        waitForVisibility(tmTaskTemplatePage.viewTemplateName, 5);
        Assert.assertTrue(tmTaskTemplatePage.viewTemplateName.isDisplayed(), "Template details page is not displayed");
    }

    // This method is used to get rid of StaleElementReference Exception
    //Author: Basha
    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                //Driver.getDriver().findElement(by).click();
                //Driver.getDriver().findElement(By.xpath("//a[text()='" + templateName.get()  + "']"));
                jsClick(Driver.getDriver().findElement(by));
                result = true;
                break;
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
            attempts++;
        }
        return result;
    }

    /*This methods validates that the template name, description, created on, created by, all fields, selected fields have same values as populated
    when saving on Task Template Details Page
    Author - Vinuta on March 13th, 2019
    refactorBy: Vidya Date:1-30-2020
    Refactored by: Vinuta 07-12-2021
     */
    @Then("I verify template details are displayed as expected")
    public void i_verify_template_details_are_displayed_as_expected(List<String> options) {
        Assert.assertTrue(tmTaskTemplatePage.createdBy.isDisplayed(), "Created by field is not displayed");
        Assert.assertTrue(tmTaskTemplatePage.createdOn.getText().equals(getCurrentDate()), "Created on date is incorrect");
        Assert.assertTrue(tmTaskTemplatePage.viewTemplateName.getText().equalsIgnoreCase(templateName.get()), "Template name is not the same as saved");
        Assert.assertTrue(tmTaskTemplatePage.viewTemplateDesc.getText().equalsIgnoreCase(templateDesc.get()), "Template description is not the same as saved");

        /* Commenting display of all fields in View Template due to CP-3912
        //to check All fields values in view page
        List<String> expectedLabels =
                Arrays.asList("Accessibility Needed", "Action Taken", "Action Taken Single", "Address Line 1", "Address Line 2", "Address City", "Address State", "Address Zip Code", "Address Source", "Address Type",
                        "Appeal Case Summary Status", "Appeal Reason", "Appeal Source", "Appeals Case Summary Due Date", "Appeals Case Summary Hyperlink", "Appellant", "Application ID", "Application Received Date", "Application Signature Date",
                        "Application Source", "Application Status", "Application Type", "Appointment Date", "Appointment Time", "Business Unit", "CSR Name", "Caller Name", "Case Status", "Case Worker First Name", "Case Worker Last Name", "Channel",
                        "Community Outreach Session Type", "Complaint", "Complaint About", "Complaint Sub-Type", "Complaint Type", "Conference ID", "ConnectionPoint Contact Record ID", "ConnectionPoint SR ID", "ConnectionPoint Task ID", "Consumer Satisfied?",
                        "Contact Reason", "CoverVA Attendance", "CoverVA Received Date", "Current Plan", "DMAS Feedback", "DMAS Received Date", "DMAS Reviewer", "Date Appeal Closed", "Date DMAS Review Started", "Date Decision Letter Sent", "Date Follow-up Email Sent",
                        "Date Grievance Received", "Date Health Plan Contacted", "Date Ready for DMAS Review", "Date Received", "Date Resent", "Date State Notified", "Date Summary Accepted", "Date Summary Returned to CVA", "Date Summary Sent", "Date Translation Escalated",
                        "Date Translation Mailed", "Date Translation Received", "Date of Birth", "Date of Contact", "Date of Voicemail", "Decision", "Decision Source", "Decision in DMIS Date", "Denial Reason", "Disenrollment Date", "Disenrollment Reason", "Disposition",
                        "Document Due Date", "Document Sub-Type", "Eligibility Decision", "Email", "End Date", "Escalated", "Escalation Reason", "Ex Parte?", "Expedited", "External Application ID", "External Case ID", "External Consumer ID", "Facility Name", "Facility Type",
                        "Fair Hearing Involved Parties", "Fair Hearing Location", "Fair Hearing Method", "Final Decision", "First Name", "Last Name", "From Email", "From Name", "From Phone", "Grievance #", "HPE?", "Hearing Officer Name", "Hospital Name", "Inbound Correspondence ID",
                        "Inbound Correspondence Type", "Inbound Correspondence Workable Flag", "Incident Date", "Information Type", "Invalid", "Invalid Address Reason", "Invalid Reason", "Issue Type", "Language", "Letter Sent", "Locality", "MI Received Date", "Member Attendance", "Message",
                        "Message Subject", "Missing Info Required?", "Name", "New Address Line 1", "New Address Line 2", "New Address City", "New Address State", "New Address Zip Code", "Newborn Name", "No Of Applicants in Household", "No Of Approved Applicants", "Notification ID",
                        "Old Address Line 1", "Old Address Line 2", "Old Address City", "Old Address State", "Old Address Zip Code", "Organization", "Other", "Outbound Correspondence ID", "Outbound Correspondence Type", "Outcome", "Outreach Location", "Phone", "Phone Number Extension",
                        "Plan Change Reason", "Plan Effective Date", "Plan ID", "Plan Name", "Plan Start Date", "Pre-Hearing Outcome", "Pre-Hearing Reason", "Preferred Call Back Date", "Preferred Call Back Time", "Preferred Language", "Preferred Phone", "Program", "Provider Address Line 1",
                        "Provider Address Line 2", "Provider Address City", "Provider Address State", "Provider Address Zip Code", "Provider Address County", "Provider Email", "Provider First Name", "Provider Last Name", "Provider NPI", "Provider Phone", "Provider Record Issue", "Provider State ID",
                        "RID #", "Reason", "Received Date", "Remand Completion Date", "Remand Due Date", "Remand Instructions", "Remand Reason", "Request Type", "Requested Date", "Requested New Plan", "Requested New Provider", "Returned Mail Reason", "Returned Mail Received Date", "Review Outcome",
                        "Service Request ID", "Session Region", "Site Type", "Site Address Line 1", "Site Name", "Site Address Line 2", "Site Address City", "Site Address State", "Site Address Zip Code", "Site County", "Site Contact First Name", "Site Contact Last Name", "Site Contact Phone",
                        "Site Contact Fax", "Site Contact Email", "Start Date", "Summary in DMIS Date", "Time of Voicemail", "Transfer to LDSS Required?", "Transferred Date", "Urgent Access to Care", "Used Tobacco in the last 6 months?", "VCL Due Date", "VCL Sent Date");
        for (int i = 0; i < tmTaskTemplatePage.allFieldValuesInViewPage.size(); i++) {
            Assert.assertEquals(tmTaskTemplatePage.allFieldValuesInViewPage.get(i).getText(), expectedLabels.get(i));
        } */

        //to check Default fields values in view page
        List<String> expectedLabels1 = Arrays.asList("Assignee", "Created By", "Created On", "Due Date", "Due In", "Priority", "Source",
                "Status", "Status Date", "Task Id", "Task Info", "Task Type");
        for (int i = 0; i < tmTaskTemplatePage.defaultFieldValuesInViewPage.size(); i++) {
            Assert.assertEquals(tmTaskTemplatePage.defaultFieldValuesInViewPage.get(i).getText(), expectedLabels1.get(i));
        }

        //to check selected fields values frm All fields to Default fields in view page
        WebElement selectedField;
        for (String s : options) {
            selectedField = Driver.getDriver().findElement(By.xpath("//*[text()='SELECTED FIELDS']/../following-sibling::div//*[text()='" + s + "']"));
            Assert.assertTrue(selectedField.isDisplayed(), s + " -Field is not displayed although moved to Selected Field section");
        }
    }

    /*This methods validates that clone tempplate option is present
    Author - Vinuta on March 21st, 2019
     */
    @And("I verify Clone option is displayed to clone Task Template")
    public void cloneDisplayed() {
        Assert.assertTrue(tmTaskTemplatePage.cloneTemplate.isDisplayed(), "Clone template option is not displayed");
    }

    /*This methods validates task types are displayed on expanding task template
    Author - Vidya on 1-30-2020
     */
    @Then("I verify task types are displayed on expanding task template")
    public void verufyTaskTypesAreDisplayedOnExpandingTaskTemplate() {
        Assert.assertTrue(tmTaskTemplatePage.taskTypeHeader.isDisplayed(), "Task Type header is not displayed");
        Assert.assertTrue(tmTaskTemplatePage.generalTaskType.isDisplayed(), "General Task Type is not displayed");
    }


    /*This methods validates selecting any one of the group field will systematically  select other Grouped Fields
    Author -Paramita on 2-3-2020
     */
    @Then("I click on Grouped fields")
    public void i_click_on_grouped_fields(List<String> groupedOptions) {
        tmTaskTemplatePage.taskTemplateBackArrow.click();
        tmTaskTemplatePage.addTaskTemplateBtn.click();
        waitFor(3);
        WebElement e1;
        scrollDown();
        for (String groupedfield : groupedOptions) {
            //  scrollDownUsingActions(2);
            scrollDown();
            e1 = Driver.getDriver().findElement(By.xpath("//span[text()='" + groupedfield + "']/..//input[@type='checkbox']"));
            e1.click();
            scrollDown();
            waitFor(3);
        }
    }

    @Then("I verify all other grouped fields get selected")
    public void other_grouped_fields_selected() {
        scrollUpUsingActions(3);
        List<String> groupedFields = Arrays.asList("Case Worker First Name", "Case Worker Last Name", "New Address Line 1", "New Address Line 2",
                "New Address City", "New Address State", "New Address Zip Code", "Old Address Line 1", "Old Address Line 2", "Old Address City",
                "Old Address State", "Old Address Zip Code", "Provider Address Line 1", "Provider Address Line 2", "Provider Address City",
                "Provider Address State", "Provider Address Zip Code", "Provider Address County", "Provider First Name", "Provider Last Name",
                "First Name", "Last Name", "Address Line 1", "Address Line 2", "Address City", "Address State", "Address Zip Code");
        for (int t = 0; t < groupedFields.size(); t++) {
            WebElement checkedElement = Driver.getDriver().findElement(By.xpath("//span[text()='" + groupedFields.get(t) + "']/..//input[@type='checkbox']"));
            System.out.println(groupedFields.get(t));
            System.out.println(checkedElement.isSelected());
            Assert.assertTrue(checkedElement.isSelected(), "Elements showing not selected" + "" + groupedFields.get(t));
        }

    }

    /*This methods validates de-selecting any one of the group field will systematically  de-select other Grouped Fields
    Author -Paramita on 2-3-2020
     */

    @When("I click on any selected Grouped fields,all other selected grouped fields get deselected")
    public void i_click_to_deselect_groupField(List<String> anyGroupedField) {
        waitFor(3);
        scrollUp();
        for (String selectedgroupedfield : anyGroupedField) {
            scrollDown();
            WebElement e4 = Driver.getDriver().findElement(By.xpath("//span[text()='" + selectedgroupedfield + "']/..//input[@type='checkbox']"));
            if (e4.isSelected()) {
                e4.click();
                scrollDown();
                waitFor(3);
            }
        }
    }

    @Then("I verify all grouped fields get de-selected")
    public void i_verify_all_grouped_fields_get_deselected() {
        scrollUpUsingActions(3);
        List<String> uncheckedGroupedFields = Arrays.asList("Case Worker First Name", "Case Worker Last Name", "New Address Line 1", "New Address Line 2",
                "New Address City", "New Address State", "New Address Zip Code", "Old Address Line 1", "Old Address Line 2", "Old Address City",
                "Old Address State", "Old Address Zip Code", "Provider Address Line 1", "Provider Address Line 2", "Provider Address City",
                "Provider Address State", "Provider Address Zip Code", "Provider Address County", "Provider First Name", "Provider Last Name",
                "First Name", "Last Name", "Address Line 1", "Address Line 2", "Address City", "Address State", "Address Zip Code");
        for (int p = 0; p < uncheckedGroupedFields.size(); p++) {
            WebElement uncheckedElement = Driver.getDriver().findElement(By.xpath("//span[text()='" + uncheckedGroupedFields.get(p) + "']/..//input[@type='checkbox']"));
            Assert.assertFalse(uncheckedElement.isSelected(), "Elements showing selected" + " " + uncheckedGroupedFields.get(p));
        }
    }


    /*This methods validates template with same name with template description and different version
       Author -Paramita on 2-3-2020
     */
    @Then("I create a task template with same name with template description and different version")
    public void task_template_with_same_name_with_different_version() {
        scrollToElement(tmTaskTemplatePage.templateListLabel);
        i_click_on_add_new_task_template_button();
        //waitFor(3);
        clearAndFillText(tmTaskTemplatePage.templateName, "Task Template Version" + getRandomNumber(3));
        clearAndFillText(tmTaskTemplatePage.templateDescription, "Description" + getRandomString(5));
        tmTaskTemplatePage.saveTaskTemplateBtn.click();
        success_message_is_displayed_on_task_template_list_page();

    }

    /* This method verifies template details in Task Template View page
       Author : Basha*/
    @Then("I verify template fields are displayed as expected")
    public void i_verify_template_fields_are_displayed_as_expected(List<String> options) {
        //to check template details
        //Assert.assertTrue(tmTaskTemplatePage.createdBy.isDisplayed(),"Created by field is not displayed");
        Assert.assertFalse(tmTaskTemplatePage.createdBy.getText().isEmpty(), "Created by field is not displayed");
        //Assert.assertTrue(tmTaskTemplatePage.createdOn.getText().equals(getCurrentDate()),"Created on date is incorrect");
        Assert.assertFalse(tmTaskTemplatePage.createdOn.getText().isEmpty(), "Created on date is incorrect");
        Assert.assertTrue(tmTaskTemplatePage.viewTemplateName.getText().equalsIgnoreCase(templateName.get()), "Template name is not the same as saved");
        //Assert.assertTrue(tmTaskTemplatePage.viewTemplateDesc.getText().equalsIgnoreCase(templateDesc.get()),"Template description is not the same as saved");
        Assert.assertFalse(tmTaskTemplatePage.viewTemplateDesc.getText().isEmpty(), "Template description is not the same as saved");

        //to check selected fields values frm All fields to Default fields in view page
        waitFor(1);
        WebElement selectedField;
        for (String s : options) {
            selectedField = Driver.getDriver().findElement(By.xpath("//span[text()='SELECTED FIELDS']/../following-sibling::div//p[text()='" + s + "']"));
            Assert.assertTrue(selectedField.isDisplayed(), "Field is not displayed although moved to Selected Field section");
        }

        //to check Default fields values in view page
        List<String> expectedLabels1 = Arrays.asList("Assignee", "Created By", "Created On", "Due Date",
                "Due In", "Priority", "Source", "Status", "Status Date", "Task ID", "Task Info", "Task Type");
        for (int i = 0; i < tmTaskTemplatePage.defaultFieldValuesInViewPage.size(); i++) {
            Assert.assertEquals(tmTaskTemplatePage.defaultFieldValuesInViewPage.get(i).getText(), expectedLabels1.get(i));
        }
    }

    @Then("I verify template has following optional fields under selected fields")
    public void i_verify_template_has_following_optional_fields_under_selected_fields(List<String> options) {
        WebElement selectedField;
        for (String s : options) {
            selectedField = Driver.getDriver().findElement(By.xpath("//*[text()='SELECTED FIELDS']/../following-sibling::div//*[text()='" + s + "']"));
            Assert.assertTrue(selectedField.isDisplayed(), s + " -Field is not displayed although moved to Selected Field section");
        }
    }

    @And("I click on Edit button on task template view page")
    public void clickEditButtonTaskTemplateViewPage() {
        click(tmTaskTemplatePage.editTaskTemplateBtn);
    }

    @And("I click on Add field button on task template edit page")
    public void clickAddFieldButton() {
        click(tmTaskTemplatePage.addFieldBtn);
    }

    @And("I select Field Type as {string}")
    public void selectFieldType(String fieldTypeToSelect) {
        waitFor(1);
        click(tmTaskTemplatePage.fieldTypeDropDown);
        for (WebElement fieldType : tmTaskTemplatePage.fieldTypeDropDownValues) {
            if (fieldType.getText().trim().equalsIgnoreCase(fieldTypeToSelect)) {
                click(fieldType);
                break;
            }
        }
    }

    @And("I verify Min and Max character fields are {string}")
    public void verifyMinMaxFieldsDisplayed(String verifyCheck) {
        switch (verifyCheck) {
            case "displayed":
                Assert.assertTrue(isElementDisplayed(tmTaskTemplatePage.minCharactersInput), "min characters field not displayed");
                Assert.assertTrue(isElementDisplayed(tmTaskTemplatePage.maxCharactersInput), "max characters field not displayed");
                break;
            case "not displayed":
                Assert.assertFalse(isElementDisplayed(tmTaskTemplatePage.minCharactersInput), "min characters field displayed");
                Assert.assertFalse(isElementDisplayed(tmTaskTemplatePage.maxCharactersInput), "max characters field displayed");
                break;
        }
    }

    @And("I verify Service and Table fields are {string}")
    public void verifyServiceTableFieldsDisplayed(String verifyCheck) {
        switch (verifyCheck) {
            case "displayed":
                waitForVisibility(tmTaskTemplatePage.serviceDropDown, 15);
                Assert.assertTrue(isElementDisplayed(tmTaskTemplatePage.serviceDropDown), "service field not displayed");
                Assert.assertTrue(isElementDisplayed(tmTaskTemplatePage.tableDropDown), "table field not displayed");
                break;
            case "not displayed":
                Assert.assertFalse(isElementDisplayed(tmTaskTemplatePage.serviceDropDown), "service field displayed");
                Assert.assertFalse(isElementDisplayed(tmTaskTemplatePage.tableDropDown), "table field displayed");
                break;
        }
    }

    @And("I verify error message for {string} field is displayed as {string}")
    public void verifyErrorMessages(String fieldName, String expMessage) {
        String actErrorMessage = null;
        switch (fieldName) {
            case "min characters":
                actErrorMessage = getText(tmTaskTemplatePage.minCharactersErrorMessage).trim();
                break;
            case "max characters":
                actErrorMessage = getText(tmTaskTemplatePage.maxCharactersErrorMessage).trim();
                break;
            case "service":
                actErrorMessage = getText(tmTaskTemplatePage.serviceErrorMessage).trim();
                break;
            case "table":
                actErrorMessage = getText(tmTaskTemplatePage.tableErrorMessage).trim();
                break;
        }
        Assert.assertEquals(actErrorMessage, expMessage, "error messages are not matched");
    }

    @And("I click save button on add field page")
    public void clickSaveButton() {
        click(tmTaskTemplatePage.addFieldSaveBtn);
    }

    @And("I select service as {string}")
    public void selectService(String serviceToSelecct) {
        click(tmTaskTemplatePage.serviceDropDown);
        for (WebElement fieldType : tmTaskTemplatePage.serviceDropDownValues) {
            if (fieldType.getText().trim().equalsIgnoreCase(serviceToSelecct)) {
                click(fieldType);
                break;
            }
        }
    }

    @And("I select table as {string}")
    public void selectTable(String tableToSelect) {
        waitFor(2);
        click(tmTaskTemplatePage.tableDropDown);
        for (WebElement fieldType : tmTaskTemplatePage.tableDropDownValues) {
            if (fieldType.getText().trim().equalsIgnoreCase(tableToSelect)) {
                click(fieldType);
                break;
            }
        }
    }
}
