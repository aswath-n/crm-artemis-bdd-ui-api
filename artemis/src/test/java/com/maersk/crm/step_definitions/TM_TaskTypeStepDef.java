package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMTaskTemplatePage;
import com.maersk.crm.pages.tm.TMTaskTypePage;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

import static org.testng.Assert.assertTrue;

public class TM_TaskTypeStepDef extends BrowserUtils {
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMTaskTypePage tmTaskTypePage = new TMTaskTypePage();
    TMTaskTemplatePage tmTaskTemplatePage = new TMTaskTemplatePage();
    final ThreadLocal<TM_ProjectRoleDetailsStepDef> tmProjectRoleDetailsStepDef = ThreadLocal.withInitial(TM_ProjectRoleDetailsStepDef::new);
    // String taskType = null;
    final ThreadLocal<String> randNumb = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> rawLogs = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> rawPostData = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<ApiTestDataUtil> apitdu = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);
//    List<String> taskTypeList = new ArrayList<>();

    final ThreadLocal<List<String>> ActiveStartDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> FutureStartDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> InactiveEndDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> tempStartDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> tempEndDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> associatePermissions = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> obtainedScreenValue = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> sortedScreenValue = ThreadLocal.withInitial(ArrayList::new);


    public static ThreadLocal<String> taskType = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> taskTypeId = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> selectedSrCategory = ThreadLocal.withInitial(String::new);


    @Given("I navigate to Task Type Page")
    public void navigate_to_task_type() {
        Assert.assertTrue(tmProjectDetailsPage.projConfig.isDisplayed(), "Project configuration icon is  not displayed");
        tmProjectDetailsPage.projConfig.click();
        Assert.assertTrue(tmProjectDetailsPage.taskTypeNavigation.isDisplayed(), "Task type icon is  not displayed");
        scrollUpRobot();
        tmProjectDetailsPage.taskTypeNavigation.click();
    }

    @When("I click on add new task type button")
    public void i_click_on_add_new_task_type_button() {
        waitForVisibility(tmTaskTypePage.btnAddTaskType, 5);
        Assert.assertTrue(tmTaskTypePage.btnAddTaskType.isDisplayed(), "Add task type button is not displayed");
        tmTaskTypePage.btnAddTaskType.click();
        waitForVisibility(tmTaskTypePage.lblCreateTaskType, 5);
        Assert.assertTrue(tmTaskTypePage.lblCreateTaskType.isDisplayed(), "Create task type page is not displayed");
    }

    @Then("I verify task type field accepts max 50 characters")
    public void verifyTaskTypeFieldAcceptsUptoThirtyCharacters() {
        String textToEnter = "Entering > 50 characters with special and alphanumeric in task type";
        System.out.println("Entered String length: " + textToEnter.length());
        clearAndFillText(tmTaskTypePage.txtTaskType, textToEnter);
        String accptedText = getTextFromInputField(tmTaskTypePage.txtTaskType);
        System.out.println("Accepted String length: " + accptedText.length());
        Assert.assertEquals(accptedText, textToEnter.substring(0, 50));
    }

    @Then("I verify fields on the create task type screen")
    public void verifyCreateTaskTypeFields(List<String> fields) {

        for (String field : fields) {
            switch (field) {
                case "Task type":
                    Assert.assertTrue(tmTaskTypePage.txtTaskType.isDisplayed(), "Task type field is displayed");
                    break;
                case "Priority":
                    Assert.assertTrue(tmTaskTypePage.lstTaskPriority.isDisplayed(), "Task priority field is displayed");
                    break;
                case "Due In Days":
                    Assert.assertTrue(tmTaskTypePage.txtDueInDays.isDisplayed(), "Task due in days field is displayed");
                    break;
                case "Description":
                    Assert.assertTrue(tmTaskTypePage.txtTaskDescription.isDisplayed(), "Task description field is displayed");
                    break;
                case "Type of Days":
                    Assert.assertTrue(tmTaskTypePage.lstTypeOfDays.isDisplayed(), "Task type of days field is displayed");
                    break;
                case "CASE":
                    Assert.assertTrue(tmTaskTypePage.caseRadioBtnInViewMode.isDisplayed(), "Case Radio Button is not displayed");
                    break;
                case "CONSUMER":
                    Assert.assertTrue(tmTaskTypePage.consumerRadioBtnInViewMode.isDisplayed(), "Consumer Radio Button is not displayed");
                    break;
                case "CASE AND CONSUMER":
                    Assert.assertTrue(tmTaskTypePage.caseNconsumerRadioBtnInViewMode.isDisplayed(), "Case And Consumer Radio Button is not displayed");
                    break;
                case "CORRESPONDENCE":
                    Assert.assertTrue(tmTaskTypePage.correspandanceChkBxInViewMode.isDisplayed(), "Correspondance checkbox is not displayed");
                    break;
                case "Required Links Header":
                    Assert.assertTrue(tmTaskTypePage.requiredLinksHeader.isDisplayed(), "Required Links Header is not displayed");
                    break;


            }
        }
    }

    @Then("I verify fields on the task type details screen")
    public void verifyTaskTypeFields(List<String> fields) {

        for (String field : fields) {
            switch (field) {
                case "CASE":
                    Assert.assertTrue(tmTaskTypePage.caseRadioBtnInViewMode.isDisplayed(), "Case Radio Button is not displayed");
                    break;
                case "CONSUMER":
                    Assert.assertTrue(tmTaskTypePage.consumerRadioBtnInViewMode.isDisplayed(), "Consumer Radio Button is not displayed");
                    break;
                case "CASE AND CONSUMER":
                    Assert.assertTrue(tmTaskTypePage.caseNconsumerRadioBtnInViewMode.isDisplayed(), "Case And Consumer Radio Button is not displayed");
                    break;
                case "CORRESPONDENCE":
                    Assert.assertTrue(tmTaskTypePage.correspandanceChkBxInViewMode.isDisplayed(), "Correspondance checkbox is not displayed");
                    break;
                case "Required Links Header":
                    Assert.assertTrue(tmTaskTypePage.requiredLinksHeader.isDisplayed(), "Required Links Header is not displayed");
                    break;

            }
        }
    }

    @Then("I verify type of days field selected by default value {string}")
    public void verifyDefaultValueOfTypeOfDays(String expectedDefaultValue) {
        waitForVisibility(tmTaskTypePage.lstTypeOfDays, 5);
        String actDefaultValue = tmTaskTypePage.txtTypeOfDays.getText();
        Assert.assertEquals(actDefaultValue, expectedDefaultValue);
    }

    @When("I click on back button on create task type")
    public void clickBackButton() {
        waitForVisibility(tmTaskTypePage.btnBack, 5);
        tmTaskTypePage.btnBack.click();
        waitForVisibility(tmTaskTypePage.btnContinueWarningWindow, 5);
    }

    @Then("I verify user is on create task type screen when click on cancel")
    public void verifyUserIsoOnCreateTaskTypeScreen() {
        waitForVisibility(tmTaskTypePage.btnCancelWarningWindow, 5);
        tmTaskTypePage.btnCancelWarningWindow.click();
        Assert.assertTrue(tmTaskTypePage.lblCreateTaskType.isDisplayed(), "Create task type page is not displayed");
    }

    @Then("I verify user is redirected to task type configuration screen after clicking continue")
    public void verifyUserIsRedirectedToTaskTypeConfiguration() {
        waitForVisibility(tmTaskTypePage.btnContinueWarningWindow, 5);
        tmTaskTypePage.btnContinueWarningWindow.click();
        Assert.assertTrue(tmTaskTypePage.lblTaskType.isDisplayed(), "task type configuration page is not displayed");
    }

    @Then("I verify task type description field specifications")
    public void verifyDescriptionFieldSpecification() {
        String textToEnter = "Entering > 150 characters with special characters like @ * and alphanumeric like alpha768 in description filed description field also accepts spaces additional text";
        System.out.println("Entered String length: " + textToEnter.length());
        clearAndFillText(tmTaskTypePage.txtTaskDescription, textToEnter);
        String accptedText = getTextFromInputField(tmTaskTypePage.txtTaskDescription);
        System.out.println("Accepted String length: " + accptedText.length());
        Assert.assertEquals(accptedText.length(), 150);
        Assert.assertTrue(!accptedText.contains("@"));
        Assert.assertTrue(!accptedText.contains("*"));
        Assert.assertTrue(accptedText.contains("768"));
        Assert.assertTrue(accptedText.contains(" "));
    }

    @Then("I verify task type due in days field specifications")
    public void verifyDueInDaysFieldSpecification() {
        //Only digits are accepted
        String textToEnter = "ha34";
        clearAndFillText(tmTaskTypePage.txtDueInDays, textToEnter);
        String accptedText = getTextFromInputField(tmTaskTypePage.txtDueInDays);
        System.out.println("Entered String: " + textToEnter);
        System.out.println("Accepted String: " + accptedText);
        Assert.assertEquals(accptedText, "34");

        //Only whole numbers are accepted
        textToEnter = "35.21";
        clearAndFillText(tmTaskTypePage.txtDueInDays, textToEnter);
        accptedText = getTextFromInputField(tmTaskTypePage.txtDueInDays);
        System.out.println("Entered String: " + textToEnter);
        System.out.println("Accepted String: " + accptedText);
        Assert.assertEquals(accptedText, "352");

        //Maximum Length validation
        textToEnter = "3521";
        clearAndFillText(tmTaskTypePage.txtDueInDays, textToEnter);
        accptedText = getTextFromInputField(tmTaskTypePage.txtDueInDays);
        System.out.println("Entered String: " + textToEnter);
        System.out.println("Accepted String: " + accptedText);
        Assert.assertEquals(accptedText.length(), 3);
        Assert.assertEquals(accptedText, "352");

    }

    @Then("I verify priority field has dropdown with values")
    public void verifyPriorityDropDownValues(List expectedValues) {
        waitForVisibility(tmTaskTypePage.lstTaskPriority, 5);
        tmTaskTypePage.lstTaskPriority.click();
        List actualValues = new LinkedList();
        waitForVisibility(tmTaskTypePage.taskPriorityValues.get(0), 5);
        for (WebElement ele : tmTaskTypePage.taskPriorityValues) {
            actualValues.add(ele.getText());
        }
        Assert.assertEquals(actualValues, expectedValues);
    }

    @When("I click on save button on create task type screen")
    public void clickSaveButton() {
        waitFor(1);
        //waitForVisibility(tmTaskTypePage.btnSaveTaskType, 5);
        tmTaskTypePage.btnSaveTaskType.click();
    }

    @When("I verify error is displayed for mandatory fields")
    public void verifyManadatoryFieldErrorMEssages() {
        waitForVisibility(tmTaskTypePage.lblTaskTypeFieldMessage, 5);
        Assert.assertEquals(tmTaskTypePage.lblTaskTypeFieldMessage.getText().toLowerCase(), "The Task Type is required and cannot be left blank.".toLowerCase());
        Assert.assertEquals(tmTaskTypePage.lblDueInDateFieldMessage.getText().toLowerCase(), "The DueinDays is a required field and cannot be left blank".toLowerCase());
        Assert.assertEquals(tmTaskTypePage.lblTaskPriorityFieldMessage.getText().toLowerCase(), "The Task Priority is required and cannot be left blank.".toLowerCase());
        Assert.assertEquals(tmTaskTypePage.lblDescription.getCssValue("color"), "rgba(0, 0, 0, 0.7)");
    }

    //refactoredBy: Basha 04-27-2020
    @When("I provide the required information for creating task")
    public void enterRequiredTaskTypeInformation() {
        waitForVisibility(tmTaskTypePage.txtTaskType, 5);
        taskType.set( RandomStringUtils.randomAlphabetic(5));
        clearAndFillText(tmTaskTypePage.txtTaskType, taskType.get());
        randNumb.set(RandomStringUtils.randomNumeric(2));
        //clearAndFillText(tmTaskTypePage.txtDueInDays, RandomStringUtils.randomNumeric(2));
        clearAndFillText(tmTaskTypePage.txtDueInDays, randNumb.get());
        taskTypeId.set(RandomStringUtils.randomNumeric(10));
        clearAndFillText(tmTaskTypePage.taskTypeIdInputBox, taskTypeId.get());
        selectDropDown(tmTaskTypePage.lstTaskPriority, "3");
        waitFor(4);
        enterAssociateScreenName("View Contact Record Details");
        waitFor(3);

        //tmTaskTypePage.permToWorkArrow.click();
        // waitFor(1);
        //List<WebElement> listValues = Driver.getDriver().findElements(By.xpath("//ul[@id='_Select Permission Group(s)-popup']/li"));
        //jsClick(listValues.get(0));

        tmTaskTypePage.lstPermisionGroupToWork.click();
        tmTaskTypePage.txtPermisionGroupToWork.sendKeys("Csr" + Keys.ENTER);
        waitFor(3);
        tmTaskTypePage.lstPermisionGroupToWorkEscalated.click();
        tmTaskTypePage.txtPermisionGroupToWorkEscalated.sendKeys("Csr" + Keys.ENTER);
        waitFor(3);

        //tmTaskTypePage.permToWorkEscalatedArrow.click();
        //waitFor(3);
        //listValues = Driver.getDriver().findElements(By.xpath("//ul[@id='_Select Permission Group(s)-popup']/li"));
        //jsClick(listValues.get(0));

    }

    @When("I provide the required information for creating task with created role name")
    public void enterRequiredTaskTypeInformationWithNewRole() {
        waitForVisibility(tmTaskTypePage.txtTaskType, 5);
        taskType.set( RandomStringUtils.randomAlphabetic(5));
        clearAndFillText(tmTaskTypePage.txtTaskType, taskType.get());
        randNumb.set(RandomStringUtils.randomNumeric(2));
        //clearAndFillText(tmTaskTypePage.txtDueInDays, RandomStringUtils.randomNumeric(2));
        clearAndFillText(tmTaskTypePage.txtDueInDays, randNumb.get());

        selectDropDown(tmTaskTypePage.lstTaskPriority, "3");
        waitFor(4);
        enterAssociateScreenName("View Contact Record Details");

        waitFor(3);
        tmTaskTypePage.lstPermisionGroupToWork.click();
        tmTaskTypePage.txtPermisionGroupToWork.sendKeys(tmProjectRoleDetailsStepDef.get().roleForPermGr.get() + Keys.ENTER);
        waitFor(3);
        tmTaskTypePage.lstPermisionGroupToWorkEscalated.click();
        tmTaskTypePage.txtPermisionGroupToWorkEscalated.sendKeys(tmProjectRoleDetailsStepDef.get().roleForPermGr.get() + Keys.ENTER);
        waitFor(3);
    }


    @Then("I verify updated role name in Task Type Detail screen")
    public void i_verify_updated_role_name_in_edit_task_type_screen() {

        List<String> GroupToWorkSeletedValues = new ArrayList<>();
        List<String> GroupToWorkEscalatedSeletedValues = new ArrayList<>();

        for (int i = 0; i < tmTaskTypePage.permisionGroupToWorkSeletedValues.size(); i++) {
            GroupToWorkSeletedValues.add(tmTaskTypePage.permisionGroupToWorkSeletedValues.get(i).getText());
            GroupToWorkEscalatedSeletedValues.add(tmTaskTypePage.permisionGroupToWorkEscalatedSeletedValues.get(i).getText());
        }
        Assert.assertTrue(GroupToWorkSeletedValues.contains(tmProjectRoleDetailsStepDef.get().roleForPermGr.get() + "new"), "User role not updated");
        Assert.assertTrue(GroupToWorkEscalatedSeletedValues.contains(tmProjectRoleDetailsStepDef.get().roleForPermGr.get() + "new"), "User role not updated");

    }


    @Then("I verify success message is displayed on task type screen")
    public void verifySuccessMessage() {
        waitForVisibility(tmTaskTypePage.successSnackbar, 5);
        Assert.assertTrue(tmTaskTypePage.taskTypeCreatedMessage.isDisplayed());
    }

    @When("I click on cancel button on create tasktype screen")
    public void clickCancelButton() {
        waitForVisibility(tmTaskTypePage.btnCancelTaskType, 5);
        tmTaskTypePage.btnCancelTaskType.click();
    }

    @Then("I verify warning message is displayed on task type screen")
    public void verifyWarningMessageDisplayed() {
        waitForVisibility(tmTaskTypePage.btnContinueWarningWindow, 5);
        Assert.assertTrue(tmTaskTypePage.btnContinueWarningWindow.isDisplayed());
    }

    @Then("I verify following fields are displayed")
    public void verifyPermissionGroupFieldsDisplayed(List<String> fields) {
        for (String field : fields) {
            switch (field) {
                case "ASSOCIATE PERMISSION GROUP TO WORK":
                    Assert.assertTrue(tmTaskTypePage.lstPermisionGroupToWork.isDisplayed(), "Task type field is displayed");
                    break;
                case "ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED":
                    Assert.assertTrue(tmTaskTypePage.lstPermisionGroupToWorkEscalated.isDisplayed(), "Task priority field is displayed");
                    break;
                case "ASSOCIATE PERMISSION GROUP TO CREATE":
                    Assert.assertTrue(tmTaskTypePage.lstPermisionGroupToCreate.isDisplayed(), "Task due in days field is displayed");
                    break;
                case "ASSOCIATE PERMISSION GROUP TO EDIT":
                    Assert.assertTrue(tmTaskTypePage.lstPermisionGroupToEdit.isDisplayed(), "Task due in days field is displayed");
                    break;

            }
        }

    }

    @Then("I enter {string} as {string} in task type screen")
    public void enterPermissionGroupFields(String field, String value) {
        scrollToElement(tmTaskTypePage.screenName);
        waitFor(3);
        switch (field) {
            case "ASSOCIATE PERMISSION GROUP TO WORK":
                tmTaskTypePage.lstPermisionGroupToWork.click();
                break;
            case "ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED":
                tmTaskTypePage.lstPermisionGroupToWorkEscalated.click();
                break;
            case "ASSOCIATE PERMISSION GROUP TO CREATE":
                tmTaskTypePage.lstPermisionGroupToCreate.click();
                break;
            case "ASSOCIATE PERMISSION GROUP TO EDIT":
                tmTaskTypePage.lstPermisionGroupToEdit.click();
                break;

        }
        Driver.getDriver().findElement(By.xpath("//li[contains(text(), '" + value + "')]")).click();
    }

    @Then("I verify the {string} is multiselect")
    public void verifyPermissionGroupFieldMultiSelected(String field) {
        int size = 0;
        switch (field) {
            case "ASSOCIATE PERMISSION GROUP TO WORK":
                size = tmTaskTypePage.permisionGroupToWorkSeletedValues.size();
                break;
            case "ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED":
                size = tmTaskTypePage.permisionGroupToWorkEscalatedSeletedValues.size();
                break;
            case "ASSOCIATE PERMISSION GROUP TO CREATE":
                size = tmTaskTypePage.permisionGroupToWorkCreatedSeletedValues.size();
                break;
            case "ASSOCIATE PERMISSION GROUP TO EDIT":
                size = tmTaskTypePage.permisionGroupToWorkEditSeletedValues.size();
                break;


        }
        Assert.assertTrue(size > 1);
    }

    @Then("I verify newly created task type record")
    public void verifyTaskTypeRecordExits() {
        System.out.println("Task type:" + taskType.get());
        waitFor(3);
        boolean recordFound = false;
        Assert.assertTrue(tmTaskTypePage.getTaskTypewithName(taskType.get()).isDisplayed());
    }

    @When("I enter the Associate Screen Name as {string}")
    public void enterAssociateScreenName(String name) {
        waitFor(3);
        scrollToElement(tmTaskTypePage.lstTaskPriority);
        //waitFor(3);
        selectDropDown(tmTaskTypePage.lstAssociateScreenDD, name);
//        clearAndFillText(tmTaskTypePage.txtAssociateScreen,name);
        //waitFor(1);
//        tmTaskTypePage.screenName.click();
//        select_dropdown_value_as_active_contact(name);
        //Assert.assertTrue(tmTaskTypePage.lstAutoList.isDisplayed());
        //tmTaskTypePage.lstAutoList.findElement(By.xpath("//li//span[contains(text(), '"+name+"')]")).click();
        //tmTaskTypePage.lstAutoList.click();
    }

    @Then("I verify {string} is auto complete when {string} is entered")
    public void verifyPermissionGroupFieldsAutoComplete(String field, String value) {
        switch (field) {
            case "ASSOCIATE PERMISSION GROUP TO WORK":
                tmTaskTypePage.lstPermisionGroupToWork.click();
                tmTaskTypePage.txtPermisionGroupToWork.sendKeys(value.substring(0, 2));
                break;
            case "ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED":
                tmTaskTypePage.lstPermisionGroupToWorkEscalated.click();
                tmTaskTypePage.txtPermisionGroupToWorkEscalated.sendKeys(value.substring(0, 2));
                break;
            case "ASSOCIATE PERMISSION GROUP TO CREATE":
                tmTaskTypePage.lstPermisionGroupToCreate.click();
                tmTaskTypePage.txtPermisionGroupToCreate.sendKeys(value.substring(0, 2));
                break;
            case "ASSOCIATE PERMISSION GROUP TO EDIT":
                tmTaskTypePage.lstPermisionGroupToEdit.click();
                tmTaskTypePage.txtPermisionGroupToEdit.sendKeys(value.substring(0, 2));
                break;

        }
        List<WebElement> elements = Driver.getDriver().findElements(By.xpath("//div[contains(text(), '" + value + "')]/../div"));
        for (WebElement element : elements) {
            System.out.println(element.getText());
            Assert.assertTrue(element.getText().toLowerCase().contains(value.toLowerCase().substring(0, 2)));
        }

    }

    @Then("I verify fields displayed under Associate Template")
    public void verify_asociate_template_fields() {
        waitForVisibility(tmTaskTypePage.templateDropdown, 8);
        Assert.assertTrue(tmTaskTypePage.assocTemplateHeader.isDisplayed(), "Associate Template Header is not displayed");
        Assert.assertTrue(tmTaskTypePage.templateDropdown.isDisplayed(), "Template dropdown is not displayed");
        Assert.assertTrue(tmTaskTypePage.versionId.isDisplayed(), "Template version ID is not displayed");
        Assert.assertTrue(tmTaskTypePage.templateEndDate.isDisplayed(), "Template end date is not displayed");
        Assert.assertTrue(tmTaskTypePage.templateStartDate.isDisplayed(), "Template start date is not displayed");
        Assert.assertTrue(tmTaskTypePage.templateButton.isDisplayed(), "Template add button is not displayed");
        // Assert.assertTrue(tmTaskTypePage.templateRefreshBtn.isDisplayed(),"Template refresh button is not displayed");
    }

    @When("I select value {string} for template dropdown")
    public void selectTemplateValue(String templateName) {

            /*waitForVisibility(tmTaskTypePage.txtTaskType, 5);
            taskType.set( RandomStringUtils.randomAlphabetic(5));
            clearAndFillText(tmTaskTypePage.txtTaskType, taskType.get());
            clearAndFillText(tmTaskTypePage.txtDueInDays, RandomStringUtils.randomNumeric(2));
            selectDropDown(tmTaskTypePage.lstTaskPriority, "3");*/
        if (templateName.equalsIgnoreCase("random")) {
            tmTaskTypePage.templateDropdown.click();
            scrollToElement(tmTaskTypePage.templateValues.get(0));
            hover(tmTaskTypePage.templateValues.get(0));
            tmTaskTypePage.templateValues.get(0).click();
        } else
            selectDropDown(tmTaskTypePage.templateDropdown, templateName);
        waitFor(3);
       /* tmTaskTypePage.lstPermisionGroupToWork.click();
        Driver.getDriver().findElement(By.xpath("//*[contains(text(), 'Just')]")).click();
        tmTaskTypePage.txtPermisionGroupToWork.click();
        waitFor(10);*/

    }

    @Then("I verify mandatory error is displayed for")
    public void verify_mandatory_error_associate_template(List<String> field) {
        for (String s : field) {
            if (s.equalsIgnoreCase("Start Date"))
                Assert.assertTrue(tmTaskTypePage.startDateMandatoryError.isDisplayed(), "Start date mandatory error is not displayed");
            if (s.equalsIgnoreCase("Select Template")) {
                Assert.assertTrue(tmTaskTypePage.templateMandatoryError.isDisplayed(), "Select template mandatory error is not displayed");
                Assert.assertTrue(tmTaskTypePage.versionMandatoryError.isDisplayed(), "Version ID mandatory error is not displayed");
            }
        }
    }

    @And("I click on refresh template button")
    public void clickRefreshTemplateButton() {
        scrollToElement(tmTaskTypePage.lstTaskPriority);
        waitFor(3);
        tmTaskTypePage.templateRefreshBtn.click();
    }

    @And("I click on add template button on task type")
    public void clickAddTemplate() {
        //  tmTaskTypePage.templateAddBtn.click();
        jsClick(tmTaskTypePage.templateAddBtn);
        waitFor(2);


    }

    @And("I enter start date as {string} on associate template")
    public void enterTemplateStartDate(String startDate) {
        if (startDate.equalsIgnoreCase("today"))
            startDate = getCurrentDate();
        if (startDate.equalsIgnoreCase("+2"))
            startDate = getGreaterDate(2);
        clearAndFillText(tmTaskTypePage.templateStartDate, startDate);
    }

    @Then("I verify end date is updated to {string} on task template")
    public void verifyEndDate(String endDate) {
        waitFor(10);
        System.out.println(endDate);
        if (endDate.equalsIgnoreCase("+1"))
            endDate = getGreaterDate(1);
        //String aa = tmTaskTypePage.firstTemplateEndDate.getText();
        if (!tmTaskTypePage.firstTemplateEndDate.getText().replace("/", "").isEmpty()) {
            assertTrue(tmTaskTypePage.firstTemplateEndDate.getText().replace("/", "").equalsIgnoreCase(endDate), "End date is not updated correctly");
        } else {
            assertTrue(tmTaskTypePage.firstTemplateEndDate.getText().isEmpty(), "End date is not updated correctly");
        }
        //System.out.println(tmTaskTypePage.templateEndDate.getAttribute("value") + "   " + endDate);
        //assertTrue(tmTaskTypePage.templateEndDate.getAttribute("value").replace("/", "").equalsIgnoreCase(endDate), "End date is not updated correctly");
    }

    @When("I see {string} message under the {string} field on task type page")
    public void i_see_message_under_the_field_on_task_type_page(String message, String field) {
        if (field.equals("start")) {
            scrollDownUsingActions(1);
            assertTrue(tmTaskTypePage.priorStartDateError.isDisplayed());
            assertTrue(tmTaskTypePage.priorStartDateError.getText().equals(message));
            scrollUpUsingActions(1);
        }
        if (field.equals("end")) {
            scrollDownUsingActions(1);
            assertTrue(tmTaskTypePage.priorEndDateError.isDisplayed());
            assertTrue(tmTaskTypePage.priorEndDateError.getText().equals(message));
            scrollUpUsingActions(1);
        }
    }

    @When("I enter the End Date {string} to the Start Date on task type page")
    public void i_enter_the_End_Date_to_the_Start_Date(String value) {
        //clearAndFillText(tmTaskTypePage.templateStartDate, getCurrentDate());
        clearAndFillText(tmTaskTypePage.startDate, getCurrentDate());
        switch (value) {
            case "prior":
                //clearAndFillText(tmTaskTypePage.templateEndDate, getPriorDate(2));
                clearAndFillText(tmTaskTypePage.endDate, getPriorDate(2));

                break;

            case "equal":
                //clearAndFillText(tmTaskTypePage.templateEndDate, getCurrentDate());
                clearAndFillText(tmTaskTypePage.endDate, getCurrentDate());
                break;

        }
    }

    @Then("I verify template association is cleared")
    public void verifyTemplateCleared() {
        System.out.print(tmTaskTypePage.templateDropdown.getAttribute("value") + "-" + tmTaskTypePage.versionId.getAttribute("value") + "-" + tmTaskTypePage.templateStartDate.getAttribute("value") + "-" + tmTaskTypePage.templateEndDate.getAttribute("value"));
        assertTrue(tmTaskTypePage.templateDropdown.isDisplayed(), "Template dropdown is not cleared");
        assertTrue(tmTaskTypePage.versionIdValue.getAttribute("value").isEmpty(), "Version ID is not cleared");
        assertTrue(tmTaskTypePage.templateStartDate.getAttribute("value").isEmpty(), "Template start date is not cleared");
        assertTrue(tmTaskTypePage.templateEndDate.getAttribute("value").isEmpty(), "Template end date is not cleared");
    }

    @Then("I delete template {string}")
    public void deleteTemplate(String index) {
        if (index.equalsIgnoreCase("one"))
            tmTaskTypePage.templateDelete.get(0).click();
        if (index.equalsIgnoreCase("two"))
            tmTaskTypePage.templateDelete.get(1).click();
    }

    /* Validation of newly created task Type details page
    Author - Aswath
    Date - 03/26/2019
    Parameters -
    */
    @And("I navigate to newly created task Type details page")
    public void i_navigate_to_newly_created_task_type_details_page() {
        waitFor(2);
        System.out.print(taskType.get());
        for (WebElement record : tmTaskTypePage.tasktypeRecords) {
            String actualTaskType = record.findElement(By.xpath("./td[2]/a")).getText();
            scrollDown();
            if (actualTaskType.equalsIgnoreCase(taskType.get())) {
                record.findElement(By.xpath("./td[2]/a")).click();
                waitFor(5);
                break;
            }
        }
        waitForPageToLoad(5);
        String currentPageURL = Driver.getDriver().getCurrentUrl();
        String actualPageURLQaDev = "https://max-nonprod-qa-dev.apigee.pcf-maersk.com/mars-tm-web/tenant/projectDetails/configuration/task-type/add";
        String actualPageURLQa = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-tm-web/tenant/projectDetails/configuration/task-type/add";
        assertTrue(actualPageURLQaDev.startsWith(currentPageURL) || actualPageURLQa.startsWith(currentPageURL));
        Assert.assertTrue(tmTaskTypePage.taskTypeIdInView.getText().equals(taskTypeId.get()));
        Assert.assertEquals(tmTaskTypePage.lblTaskTypeName.getText(), taskType.get(), "Failed to navigated newly created Task type record");
    }

    /* Verification of task type detail section fields
    Author - Aswath
    Date - 03/26/2019
    Parameters -
    */
    @And("I verify the task type detail section fields")
    public void i_verify_the_task_type_detail_section_fields() {
        scrollUpRobot();
        scrollToElement(tmTaskTypePage.createdDate);
        //String actualDate = getCurrentDate();
        String actualDate = getCurrentDateInYearFormat();
        //String createdDate = tmTaskTypePage.createdDate.getText();
        String createdDateTime = tmTaskTypePage.createdDate.getText();

        String[] values = createdDateTime.split("\\s+");
        String createdDate = values[0];
        Assert.assertEquals(createdDate, actualDate, "Created date is not matched with actual date");

        String createdBy = tmTaskTypePage.createdBy.getText();
        Assert.assertTrue(createdBy != "Service Account1", "Created by not displayed");
        scrollUpUsingActions(4);

        String statusVal = tmTaskTypePage.statusTaskType.getText();
        if (!statusVal.equals("INACTIVE")) {
            System.out.print("Status not displayed");
        }

//        waitForVisibility(tmTaskTypePage.txtTaskType, 10);
//        String taskTypeVal = tmTaskTypePage.txtTaskType.getAttribute("value");
//        Assert.assertTrue(taskTypeVal.equalsIgnoreCase(taskType.get()), "Task Type value is not present");

        waitForVisibility(tmTaskTypePage.readTaskType, 10);
        String taskTypeVal = tmTaskTypePage.readTaskType.getText();
        Assert.assertTrue(taskTypeVal.equalsIgnoreCase(taskType.get()), "Task Type value is not present");


//        waitFor(5);
//        waitForVisibility(tmTaskTypePage.txtTypeOfDays, 10);
//        String businessDays = tmTaskTypePage.txtTypeOfDays.getText();
//        Assert.assertTrue(businessDays.equals("Business Days"), "Type of days value is not present");

//        waitForVisibility(tmTaskTypePage.txtDueInDays, 10);
//        String noOfDays = tmTaskTypePage.txtDueInDays.getAttribute("value");
//        Assert.assertTrue(noOfDays.equals(randNumb.get()), "Type of days value is not present");

        waitForVisibility(tmTaskTypePage.readDueInDays, 10);
        String noOfDays = tmTaskTypePage.readDueInDays.getText();
        System.out.println(noOfDays);
        Assert.assertTrue(noOfDays.equals(randNumb.get() + " Business Days"), "Type of days value is not present");


//        waitForVisibility(tmTaskTypePage.priortyVal, 10);
//        String priorityVal = tmTaskTypePage.priortyVal.getText();
//        Assert.assertTrue(priorityVal.equals("3"), "Proirity is empty");

        waitForVisibility(tmTaskTypePage.readPriorityVal, 10);
        String priorityVal = tmTaskTypePage.readPriorityVal.getText();
        Assert.assertTrue(priorityVal.equals("3"), "Priority is empty");

//        waitForVisibility(tmTaskTypePage.txtTaskDescription, 10);
//        String desc = tmTaskTypePage.txtTaskDescription.getAttribute("value");
//        Assert.assertTrue(desc.equals(taskType.get()), "Task is not empty");

        waitForVisibility(tmTaskTypePage.readTaskDescription, 10);
        String desc = tmTaskTypePage.readTaskDescription.getText();
        Assert.assertTrue(desc.equals("No Values"), "Description not matches");

    }

    /* Selecting the dates
    Author - Aswath
    Date - 03/26/2019
    Parameters -
    */
    public void selectDates(String startDate, String endDate) {
        if (startDate.equals("current")) {
            clearAndFillText(tmTaskTypePage.startDate, getCurrentDate());
        } else {
            clearAndFillText(tmTaskTypePage.startDate, getGreaterDate(2));
        }
        if (endDate.equals("current")) {
            clearAndFillText(tmTaskTypePage.endDate, getCurrentDate());
        } else if (endDate.equals("future")) {
            clearAndFillText(tmTaskTypePage.endDate, getGreaterDate(8));
        }

    }

    /* Creating the task with associating template
    Author - Aswath
    Date - 03/26/2019
    Parameters -
    */
    @And("I create task type record with associate template start date {string} and end Date {string}")
    public void i_create_task_type_record_with_associate_template_start_date_something_and_end_date_something(String startdate, String enddate) {
        i_click_on_add_new_task_type_button();
        waitFor(2);
        enterRequiredTaskTypeInformation();
        waitFor(2);
        enterAssociateScreenName("Active Contact");
        clickAddTemplateButton();
        selectDropDown(tmTaskTypePage.selectTemplate, "General");
        selectDropDown(tmTaskTypePage.selectVersion, "1");
        selectDates(startdate, enddate);
        waitFor(2);
        waitForVisibility(tmTaskTypePage.addBtnAsscoiateTemplate, 10);
        tmTaskTypePage.addBtnAsscoiateTemplate.click();

        scrollDown();
        clickSaveButton();

        waitForPageToLoad(10);
        waitFor(5);
        String expectedURL = "https://mars-tenant-manager-ui-qa.apps.non-prod.pcf-maersk.com/tenant/projectDetails/configuration/task-type";
        String actualURL = Driver.getDriver().getCurrentUrl();
        assertTrue(expectedURL.startsWith(actualURL));
    }

    /* Verifying the order status as Active, Future and Inactive
    Author - Aswath
    Date - 03/26/2019
    Parameters -
    */
    @When("Active task type are displayed on top followed by future and inactive")
    public void active_task_type_are_displayed_on_top_followed_by_future_and_inactive() throws StaleElementReferenceException {
        String previous = "ACTIVE";
        String previous1 = "FUTURE";
        String previous2 = "INACTIVE";
        try {

            for (WebElement pn : tmTaskTypePage.listOfTaskType) {
                List<WebElement> listofTaskTypeName = pn.findElements(By.xpath("td[6]"));
                for (WebElement e : listofTaskTypeName) {
                    String status = e.getText();
                    assertTrue(previous.equals("ACTIVE") && status.equals("ACTIVE") ||
                            previous.equals("ACTIVE") && status.equals("FUTURE") ||
                            previous1.equals("FUTURE") && status.equals("FUTURE") ||
                            previous1.equals("FUTURE") && status.equals("INACTIVE") ||
                            previous2.equals("INACTIVE") && status.equals("INACTIVE"), "Records are not in expected sort order");

                    assertFalse(previous1.equals("INACTIVE") && previous2.equals("FUTURE") && status.equals("ACTIVE"), "Records are not in expected sort order");
                    previous = status;
                }
            }


        } catch (Exception e) {
        }
    }

    /* Verify the task type Status
        Author - Aswath
        Date - 03/26/2019
        Modified =03/31/2020 by shruti
        Parameters - Accepts Status value
     */
    @Then("verify status of Task type as {string}")
    public void verify_status_of_something(String status) {
        int lenTT = taskType.get().length();
        String trimTaskType = taskType.get().substring(1, lenTT);
        WebElement taskTypeStatus = Driver.getDriver().findElement(By.xpath("//td/a[contains(.,'" + trimTaskType + "')]//ancestor::tr/td[6]"));
        Assert.assertEquals(taskTypeStatus.getText(), status, "Invalid Task Type status");
    }

    /*Captures the Tasktype Status
        Author - Aswath
        Date - 03/26/2019
        Parameters - Accepts Status value
     */
    public void getTaskTypeDates() {
        try {
            for (WebElement pn : tmTaskTypePage.listOfTaskType) {
                List<WebElement> listofTaskTypeName = pn.findElements(By.xpath("td[5]"));
                for (WebElement e : listofTaskTypeName) {
                    hover(e);
                    waitFor(2);
                    String startDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[1]/h6")).getText();
                    String endDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[2]/h6")).getText();
                    if (e.getText().equals("ACTIVE")) {
                        waitFor(2);
                        ActiveStartDate.get().add(startDate);
                    }
                    waitFor(1);
                    if (e.getText().equals("FUTURE")) {
                        waitFor(2);
                        FutureStartDate.get().add(startDate);
                    }

                    waitFor(1);
                    if (e.getText().equals("INACTIVE")) {
                        waitFor(2);
                        if (!endDate.equals(null)) {
                            InactiveEndDate.get().add(endDate);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

    }
    /*  Verify order of the active state Task Type Status are in ascending order
        Author - Aswath
        Date - 02/26/2019
        Parameters -
     */

    @Then("Active Task Type are sorted by start date in ascending order")
    public void active_task_type_are_sorted_by_start_date_in_ascending_order() {
        getTaskTypeDates();
        assertTrue(ascendingOrderDatesString(ActiveStartDate.get()));
    }

    /*  Verify order of the future state Task Type Status are in ascending order
       Author - Aswath
       Date - 02/22/2019
       Parameters -
    */
    @Then("Future Task Type are sorted by start date in ascending order")
    public void future_task_type_are_sorted_by_start_date_in_ascending_order() {
        assertTrue(ascendingOrderDatesString(FutureStartDate.get()));

    }
     /*  Verify order of the future state Task Type Status are in ascending order
        Author - Aswath
        Date - 02/22/2019
        Parameters -
     */

    @And("Inactive Task Type are sorted by end date in descending order")
    public void inactive_task_type_are_sorted_by_end_date_in_descending_order() {
        assertTrue(descendingOrderDatesString(InactiveEndDate.get()));

    }

    /* Creating the task with multiple associate template
    Author - Aswath
    Date - 03/28/2019
    Parameters -
    */
    //refactoredBy: Basha 04-27-2020
    @And("I create task type record with multiple associate template multiple")
    public void i_create_task_type_record_with_multiple_associate_template_multiple() {

        i_click_on_add_new_task_type_button();
        waitFor(5);
        enterRequiredTaskTypeInformation();
        waitFor(5);
        //enterAssociateScreenName ( "Active Contact" );
        clickAddTemplateButton();
        waitFor(2);
        //selectDropDown ( tmTaskTypePage.selectTemplate, "Template New" );

        click(tmTaskTypePage.selectTemplate);
        waitFor(1);
        click(tmTaskTypePage.lstAutoDropDownValues.get(0));
        waitFor(2);

        //selectDropDown ( tmTaskTypePage.selectVersion, "1" );
        //waitFor(2);
        clearAndFillText(tmTaskTypePage.startDate, getCurrentDate());
        clearAndFillText(tmTaskTypePage.endDate, getGreaterDate(3));
        waitForVisibility(tmTaskTypePage.addBtnAsscoiateTemplate, 10);
        clickAddTemplateButton();
        waitFor(10);

        //selectDropDown ( tmTaskTypePage.selectTemplate, "testing" );
        click(tmTaskTypePage.selectTemplate);
        waitFor(1);
        click(tmTaskTypePage.lstAutoDropDownValues.get(1));
        waitFor(2);
        //selectDropDown ( tmTaskTypePage.selectVersion, "1" );
        clearAndFillText(tmTaskTypePage.startDate, getGreaterDate(5));
        clearAndFillText(tmTaskTypePage.endDate, getGreaterDate(10));
        waitForVisibility(tmTaskTypePage.addBtnAsscoiateTemplate, 10);
        clickAddTemplateButton();
        waitFor(5);

        //selectDropDown ( tmTaskTypePage.selectTemplate, "task template 1" );
        click(tmTaskTypePage.selectTemplate);
        waitFor(1);
        click(tmTaskTypePage.lstAutoDropDownValues.get(2));
        waitFor(2);
        //selectDropDown ( tmTaskTypePage.selectVersion, "1" );
        clearAndFillText(tmTaskTypePage.startDate, getGreaterDate(11));
        clearAndFillText(tmTaskTypePage.endDate, getGreaterDate(14));
        waitForVisibility(tmTaskTypePage.addBtnAsscoiateTemplate, 10);
        clickAddTemplateButton();

        tmTaskTypePage.btnSaveTaskType.click();
    }

    /* Verifying the sort date of associate templates
    Author - Aswath
    Date - 03/28/2019
    Parameters -
    */
    @Then("I verify the associate template sort order")
    public void i_verify_the_associate_template_sort_order() {

        //tmTaskTypePage.selectTemplateValue.getAttribute("value");
        //03-31-3030 Shruti, modified logic to validated all records instead the number of records limiting to 3
        List<WebElement> startDates = Driver.getDriver().findElements(By.xpath("//p[text()='START DATE']/../h6"));
        List<WebElement> endDates = Driver.getDriver().findElements(By.xpath("//p[text()='END DATE']/../h6"));
        for (WebElement startDate : startDates) {
            tempStartDate.get().add(startDate.getText());
        }
        for (WebElement endDate : endDates) {
            tempEndDate.get().add(endDate.getText());
        }
        ascendingOrderDatesVal(tempStartDate.get());
        ascendingOrderDatesVal(tempEndDate.get());
    }

    /* Verifying the ascending sort dates input as string
   Author - Aswath
   Date - 03/28/2019
   Parameters -
   */
    public boolean ascendingOrderDatesVal(List<String> dates) {
        boolean is = false;
        String topDate;
        String nextBelowDate;
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (int i = 0; i <= dates.size() - 1; i++) {

            topDate = dates.get(i);
            if (i == dates.size() - 1) {
                continue;
            } else {
                nextBelowDate = dates.get(i + 1);
                LocalDate date1 = LocalDate.parse(topDate, sdf);
                LocalDate date2 = LocalDate.parse(nextBelowDate, sdf);
                if (date1.isEqual(date2)) {
                    is = true;
                } else {
                    is = (date1.isAfter(date2));
                }
            }
        }
        return is;
    }

    /* Verifying the number of associates screens linked
       Author - Aswath
       Date - 03/28/2019
       Parameters -
       */
    @Then("I verify the number of associates screen linked")
    public void i_verify_the_number_of_associates_screen_linked() {
        //scrollUpUsingActions(1);
        //selectDropDown(tmTaskTypePage.lstAssociateScreen, "Active Contact");
        //waitFor(1);
        /*Assert.assertTrue ( tmTaskTypePage.lstAutoList.isDisplayed () );
        tmTaskTypePage.lstAutoList.findElement ( By.xpath ( "./li//span[contains(text(),'Active Contact')]" ) ).click ();*/
        String actualScreens = tmTaskTypePage.screenName.getAttribute("value");
        String expScreen = "View Contact Record Details";
        System.out.println("===>" + actualScreens);
        System.out.println("==>" + expScreen);
        assertTrue(actualScreens.startsWith(expScreen), "Multiple screens linked to one task type");
    }
    /* Adding multiple permissions associates group to work
       Author - Aswath
       Date - 03/28/2019
       Parameters -
       */

    @Then("I add multiple permissions to associated group to work")
    public void i_add_multiple_permissions_to_associated_group_to_work() {
        tmTaskTypePage.lstPermisionGroupToWork.click();
        //Driver.getDriver().findElement(By.xpath("//*[contains(text(), 'NewPermissionDUp')]")).click();
        //List<WebElement> listValues = Driver.getDriver().findElements(By.xpath("//ul[@id='_Select Permission Group(s)-popup']/li"));
        //jsClick(listValues.get(0));
        tmTaskTypePage.txtPermisionGroupToWork.sendKeys("Non Csr" + Keys.ENTER);
        waitFor(3);

        /*waitForVisibility ( tmTaskTypePage.secondPermisionGroupToWork,10 );
        tmTaskTypePage.secondPermisionGroupToWork.click ();
        waitFor ( 2 );
        WebElement ddselect = Driver.getDriver().findElement ( By.xpath ( "//*[contains(text(), 'Group Permission')]" ) );
        waitForVisibility ( ddselect,10 );
        ddselect.click ();
        waitFor ( 5 );*/
    }

    /* Verifying multiple permissions associates group to work
       Author - Aswath
       Date - 03/28/2019
       Parameters -
       */
    @Then("I verify multiple permissions to associated group to work")
    public void i_verify_multiple_permissions_to_associated_group_to_work() {
        //refactoredBy: Shruti 04-13-2020
        List<WebElement> associatePermissionGrpsToWork = Driver.getDriver().findElements(By.xpath("//*[text()='ASSOCIATE PERMISSION GROUP TO WORK']/../following-sibling::div/div/div"));
        for (WebElement associatePermissionGrpToWork : associatePermissionGrpsToWork) {
            //WebElement associatedGrpWrk = Driver.getDriver().findElement(By.xpath("((//label[contains(text(),'Select Permission Group')])[1]/following-sibling::div/div/div/div/div/div/span)[" + i + "]"));
            waitForVisibility(associatePermissionGrpToWork, 10);
            String assciatedGrpWrkVal = associatePermissionGrpToWork.getText();
            associatePermissions.get().add(assciatedGrpWrkVal);
        }
        assertTrue(associatePermissionGrpsToWork.size() == 2, "Multiple permissions are not present");


    }
    /* Adding multiple permissions associates group to work Escalated
       Author - Aswath
       Date - 03/28/2019
       Parameters -
       */

    @Then("I add multiple permissions to associated group to work - Escalated")
    public void i_add_multiple_permissions_to_associated_group_to_work_escalated() {

        tmTaskTypePage.lstPermisionGroupToWorkEscalated.click();
        Driver.getDriver().findElement(By.xpath("//*[contains(text(), 'Non Csr')]")).click();
        waitFor(5);

        waitForVisibility(tmTaskTypePage.lstPermisionGroupToWorkEscalated, 10);
        tmTaskTypePage.lstPermisionGroupToWorkEscalated.click();
        waitFor(3);//Need to remove
        WebElement select2 = Driver.getDriver().findElement(By.xpath("//*[contains(text(), 'Role')]"));
        waitForVisibility(select2, 10);
        select2.click();

        waitFor(5);
        waitForVisibility(tmTaskTypePage.lstPermisionGroupToWorkEscalated, 10);
        tmTaskTypePage.lstPermisionGroupToWorkEscalated.click();
        waitFor(3);//Need to remove
        WebElement select3 = Driver.getDriver().findElement(By.xpath("//*[contains(text(), 'Role')]"));
        waitForVisibility(select3, 10);
        select3.click();
    }

    /* Verifying multiple permissions associates group to work Escalated
       Author - Aswath
       Date - 03/28/2019
       Parameters -
       */
    @Then("I verify multiple permissions to associated group to work - Escalated")
    public void i_verify_multiple_permissions_to_associated_group_to_work_escalated() {
        for (int i = 1; i <= 2; i++) {
            String associatedGrpWrkEscalted = Driver.getDriver().findElement(By.xpath("((//p[contains(text(),'SELECT PERMISSION GROUP(S)')])[4]//following-sibling::div//span)[" + i + "]")).getText();
            associatePermissions.get().add(associatedGrpWrkEscalted);
        }
        assertTrue(associatePermissions.get().size() == 2, "Multiple permissions are not present");
    }

    /* Adding multiple permissions associates group to create/Edit
           Author - Aswath
           Date - 03/28/2019
           Parameters -
           */
    @Then("I add multiple permissions to associated group to create")
    public void i_add_multiple_permissions_to_associated_group_to_create() {

        tmTaskTypePage.lstPermisionGroupToCreate.click();
        tmTaskTypePage.txtPermisionGroupToCreate.sendKeys("Csr" + Keys.ENTER);
        tmTaskTypePage.lstPermisionGroupToCreate.click();
        waitFor(3);//Need to remove
        tmTaskTypePage.txtPermisionGroupToCreate.sendKeys("Non Csr" + Keys.ENTER);
    }

    @Then("I add multiple permissions to associated group to edit")
    public void i_add_multiple_permissions_to_associated_group_to_edit() {
        tmTaskTypePage.lstPermisionGroupToEdit.click();
        tmTaskTypePage.txtPermisionGroupToEdit.sendKeys("Csr" + Keys.ENTER);
        tmTaskTypePage.lstPermisionGroupToEdit.click();
        waitFor(3);//Need to remove
        tmTaskTypePage.txtPermisionGroupToEdit.sendKeys("Non Csr" + Keys.ENTER);
    }

    /* Verifying multiple permissions associates group to work Escalated
       Author - Aswath
       Date - 03/28/2019
       Parameters -
       */
    //refactoredBy: Basha 04-27-2020
    @Then("I verify multiple permissions to associated group to create")
    public void i_verify_multiple_permissions_to_associated_group_to_create() {

        List<WebElement> associatePermissionGrpsToCreate = Driver.getDriver().findElements(By.xpath("//*[text()='ASSOCIATE PERMISSION GROUP TO CREATE']/../following-sibling::div/div/div"));
        for (WebElement associatePermissionGrpToCreate : associatePermissionGrpsToCreate) {
            //WebElement associatedGrpWrk = Driver.getDriver().findElement(By.xpath("((//label[contains(text(),'Select Permission Group')])[1]/following-sibling::div/div/div/div/div/div/span)[" + i + "]"));
            waitForVisibility(associatePermissionGrpToCreate, 10);
            String assciatedGrpCreateVal = associatePermissionGrpToCreate.getText();
            associatePermissions.get().add(assciatedGrpCreateVal);
        }

        //for (int i = 1; i <= 2; i++) {
        //    String associatedGrpcreatedit = Driver.getDriver().findElement(By.xpath("((//label[contains(text(),'Select Permission Group')])[3]/following-sibling::div/div/div/div/div/div/span)[" + i + "]")).getText();
        //    associatePermissions.get().add(associatedGrpcreatedit);
        //}
        assertTrue(associatePermissions.get().size() == 2, "Multiple permissions are not present");
    }

    @Then("I verify multiple permissions to associated group to edit")
    public void i_verify_multiple_permissions_to_associated_group_to_edit() {

        List<WebElement> associatePermissionGrpsToEdit = Driver.getDriver().findElements(By.xpath("//*[text()='ASSOCIATE PERMISSION GROUP TO EDIT']/../following-sibling::div//span"));
        for (WebElement associatePermissionGrpToEdit : associatePermissionGrpsToEdit) {
            //WebElement associatedGrpWrk = Driver.getDriver().findElement(By.xpath("((//label[contains(text(),'Select Permission Group')])[1]/following-sibling::div/div/div/div/div/div/span)[" + i + "]"));
            waitForVisibility(associatePermissionGrpToEdit, 10);
            String assciatedGrpEditVal = associatePermissionGrpToEdit.getText();
            associatePermissions.get().add(assciatedGrpEditVal);
        }

        //for (int i = 1; i <= 2; i++) {
        //    String associatedGrpcreatedit = Driver.getDriver().findElement(By.xpath("((//label[contains(text(),'Select Permission Group')])[3]/following-sibling::div/div/div/div/div/div/span)[" + i + "]")).getText();
        //    associatePermissions.get().add(associatedGrpcreatedit);
        //}
        assertTrue(associatePermissions.get().size() == 2, "Multiple permissions are not present");
    }


    /* Author -Paramita to develop - CP-2002
       This method to select a task type record of ACTIVE  status from task type list page */
    @When("I select a task type {string} record")
    public void selectTaskTypeRecord(String taskTypeName) {
        waitForVisibility(Driver.getDriver().findElement(By.xpath("//a[text()='" + taskTypeName + "']")), 10);
        Driver.getDriver().findElement(By.xpath("//a[text()='" + taskTypeName + "']")).click();
        waitFor(2);

        /*if(taskTypeName.equalsIgnoreCase("General")){

         }*/
    }

    @When("I select previously created Task Type")
    public void i_select_previously_created_task_type_for_editing() {
        waitForVisibility(Driver.getDriver().findElement(By.xpath("//a[text()='" + taskType.get() + "']")), 10);
        Driver.getDriver().findElement(By.xpath("//a[text()='" + taskType.get() + "']")).click();
        waitFor(2);
    }


    /* Author -Paramita
       This method to see all the fields as Ready only in View Task Type screen */
    @Then("I should see all the fields in Ready only state in View Task Type screen")
    public void readOnlyFieldsViewTaskTypeScreen() {
        waitFor(2);

        String taskTypeHeader[] = {"CREATED BY", "CREATED ON", "TASK TYPE", "PRIORITY", "DUE IN DAYS", "DESCRIPTION", "SELECT TEMPLATE",
                "VERSION", "START DATE", "END DATE", "SELECT PERMISSION GROUP(S)", "SELECT PERMISSION GROUP(S)", "SELECT PERMISSION GROUP(S)", "SELECT PERMISSION GROUP(S)"};

        scrollToElement(tmTaskTypePage.taskTypelastComponent);
        waitFor(1);
        scrollDownUsingActions(1);
        waitFor(1);
        scrollDownUsingActions(2);


        for (int i = 0; i < tmTaskTypePage.taskHeader.size(); i++) {
            //System.out.println("Count"+ tmTaskTypePage.taskHeader.get(i).getText());
            //String aa = tmTaskTypePage.taskHeader.get(i).getText();
            Assert.assertTrue(tmTaskTypePage.taskHeader.get(i).getText().contains(taskTypeHeader[i]),
                    "validating '" + tmTaskTypePage.taskHeader.get(i).getText() + "' contains text '" + taskTypeHeader[i] + "'");
        }

        int n = 0;
        for (int i = 0; i < 11; i++) {
            if (i == 5 && n == 0) {
                try {
                    Assert.assertTrue(tmTaskTypePage.descriptionValue.isEnabled(), "Description value not displayed");
                    i--;
                    n++;
                } catch (Exception e) {
                    Assert.assertTrue(tmTaskTypePage.tasktypefieldsVal.get(i).isDisplayed(), taskTypeHeader[i] + " value not displayed");
                }
            } else {
                try {
                    Assert.assertTrue(tmTaskTypePage.tasktypefieldsVal.get(i).isEnabled(), taskTypeHeader[i + n] + " value not displayed");
                } catch (IndexOutOfBoundsException e) {
                    Assert.assertTrue(false, "Screen Name value not displayed");
                }
            }
        }
        Assert.assertTrue(tmTaskTypePage.groupPermssionFieldValue.get(0).isDisplayed(), "Associate Permission Group to Work value not displayed");
        Assert.assertTrue(tmTaskTypePage.groupPermssionFieldValue.get(1).isEnabled(), "Associate Permission Group to Work -Escalated value not displayed");
        Assert.assertTrue(tmTaskTypePage.groupPermssionFieldValue.get(2).isEnabled(), "Associate Permission Group to Create/Edit  value not displayed");
        Assert.assertTrue(tmTaskTypePage.taskTypeStatus.isDisplayed(), "Status  value not displayed");
    }

    @And("I do not see the Save or Cancel Button in View Task Type screen")
    public void DoNotDisplaySaveCancelButtonInViewTaskTypeScreen() {
        scrollToElement(tmTaskTypePage.taskTypelastComponent);
        Assert.assertTrue(tmTaskTypePage.taskTypeSAVECancelButton.size() == 0, "SAVE and Cancel button is displayed");
    }


    /* Author -Paramita
   This method to verify EDIT button displayed in View Task Type screen */
    @Then("I verify EDIT button displayed in View Task Type screen")
    public void verifyEditButtonInViewTaskTypeScreen() {
        waitFor(2);
        Assert.assertTrue(tmTaskTypePage.taskTypeEDITButton.isDisplayed(), "Edit button is not displayed");
    }


    /* Author -Paramita
   This method to click on EDIT button in View Task Type screen */
    @When("I click on edit button")
    public void click_on_edit_button() {
        jsClick(tmTaskTypePage.taskTypeEDITButton);
    }

    /* Author -Paramita
    This method to select value from Task Type template dropdown */
    @And("I select value {string} from template dropdown")
    public void selectTaskTypeTemplateValue(String templateName) {
        scrollToElement(tmTaskTypePage.assocaiteTemplate);
        if (templateName.equalsIgnoreCase("random")) {
            waitFor(2);
            tmTaskTypePage.templateDropdown.click();
            scrollToElement(tmTaskTypePage.templateValues.get(0));
            hover(tmTaskTypePage.templateValues.get(0));
            tmTaskTypePage.templateValues.get(0).click();
        }
    }

    /* Author -Paramita
      This method to select start date while associating new template  */
    @And("I select start date as {string} on associate template")
    public void enterStartDateAssociateTemplate(String startdate) {
        waitFor(2);
        if (startdate.equalsIgnoreCase("today")) {
            startdate = getCurrentDate();
        } else if (startdate.equalsIgnoreCase("future")) {
            startdate = getGreaterDate(3);
        }

        clearAndFillText(tmTaskTypePage.templateStartDate, startdate);
        waitFor(2);

    }


    /* Author -Paramita
    This method to select End Date value while associating task template  */
    @And("I select {string}  on associate template")
    public void enterEndDateAssociateTemplate(String endDate) {
        clearAndFillText(tmTaskTypePage.templateEndDate, getGreaterDate(5));
    }

    /* Author -Paramita
    This method to  verify color code of the associated Task Template based on today or future date */
    @Then("I verify color code of the associated Task Template based on {string} date")
    public void verifyColorCodeAssociatedTaskTemplate(String start_date) {
        waitFor(2);
        switch (start_date) {
            case "today":
                Assert.assertEquals("#5bd59a", getColorCode1(tmTaskTypePage.associatedTemplatecolor),
                        "Green color is missing");
                break;
            case "future":
                Assert.assertEquals("#ff913c", getColorCode1(tmTaskTypePage.associatedTemplatecolor),
                        "Orange color is missing");
                break;
        }
    }

    /* Author -Paramita
    This method to select a newly created task type record */
    @When("I select a newly created task type record")
    public void selectNewlyCreatedTaskType() {
        scrollDown();
        Driver.getDriver().findElement(By.xpath("//a[text()='" + taskType.get() + "']")).click();
        scrollToElement(tmTaskTypePage.assocaiteTemplate);
    }


    /* Author -Paramita
    This method to select Associate Screen Name */
    @And("I enter Associate Screen Name as {string}")
    public void selectAssociateScreenName(String name) {
        waitForVisibility(tmTaskTypePage.lstAssociateScreen, 10);
        selectDropDownNoCheck(tmTaskTypePage.lstAssociateScreen, name);
        Assert.assertTrue(tmTaskTypePage.lstAssociateScreen.getAttribute("value").contains(name), "Selector " + name + " - wasn't selected");
       /* waitForVisibility(tmTaskTypePage.lstAutoList,10);
        tmTaskTypePage.lstAutoList.click();*/
    }

    @Then("I verify that Service Request checkbox is displayed")
    public void verifyServiceCheckBoxDisplayed() {
        Assert.assertTrue(isElementDisplayed(tmTaskTypePage.serviceRequestCheckBox.findElement(By.xpath("./parent::span"))));
    }

    @Then("I verify that Service Request checkbox is not mandatory")
    public void verifyServiceCheckBoxIsMandatoryField() {
        verifySuccessMessage();
    }

    @When("I provide the required information for creating service request")
    public void enterRequiredInformationToCreateServiceRequest() {
        //enterRequiredTaskTypeInformation();
        waitForVisibility(tmTaskTypePage.txtTaskType, 5);
        taskType.set( RandomStringUtils.randomAlphabetic(5));
        clearAndFillText(tmTaskTypePage.txtTaskType, taskType.get());
        randNumb.set(RandomStringUtils.randomNumeric(2));
        //clearAndFillText(tmTaskTypePage.txtDueInDays, RandomStringUtils.randomNumeric(2));
        clearAndFillText(tmTaskTypePage.txtDueInDays, randNumb.get());

        selectDropDown(tmTaskTypePage.lstTaskPriority, "3");
        waitFor(4);
        enterAssociateScreenName("View Contact Record Details");
        waitFor(3);

        scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        selectServiceRequestCheckBox();
        selectValueForSrCategoryField();
        waitFor(5);

        //Adding Template
        waitForVisibility(tmTaskTypePage.addBtnAsscoiateTemplate, 10);
        tmTaskTypePage.addBtnAsscoiateTemplate.click();
        tmTaskTypePage.selectTemplate.click();
        System.out.println(tmTaskTypePage.listOfTemplates.size());
        int index = (int) (Math.random() * (tmTaskTypePage.listOfTemplates.size() - 0)) + 0;
        scrollToElement(tmTaskTypePage.listOfTemplates.get(index));
        tmTaskTypePage.listOfTemplates.get(index).click();
        selectDropDown(tmTaskTypePage.selectVersion, "1");
        clearAndFillText(tmTaskTypePage.startDate, getCurrentDate());
        clearAndFillText(tmTaskTypePage.endDate, getGreaterDate(3));

    }

    @When("I select the Service Request checkbox on Edit Task Type page")
    public void selectServiceRequestCheckBoxOnEditTaskType() {
        //scrollToElement(tmTaskTypePage.serviceRequestCheckBox);
        waitFor(1);
        scrollUpUsingActions(5);
        waitFor(1);
        if (tmTaskTypePage.serviceRequestCheckBox.getAttribute("value").equalsIgnoreCase("false"))
            tmTaskTypePage.serviceRequestCheckBox.click();
    }

    @When("I select the  Service Request checkbox")
    public void selectServiceRequestCheckBox() {
        scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        if (tmTaskTypePage.serviceRequestCheckBox.getAttribute("value").equalsIgnoreCase("false"))
            tmTaskTypePage.serviceRequestCheckBox.click();
    }

    @When("I uncheck the  Service Request checkbox")
    public void unCheckServiceRequestCheckBox() {
        scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        if (tmTaskTypePage.serviceRequestCheckBox.getAttribute("value").equalsIgnoreCase("true"))
            tmTaskTypePage.serviceRequestCheckBox.click();
    }

    @When("I select a value for SR category field")
    public void selectValueForSrCategoryField() {
        scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        tmTaskTypePage.srCategory.click();
        System.out.println(tmTaskTypePage.listOfSrCategories.size());
        int index = (int) (Math.random() * (tmTaskTypePage.listOfSrCategories.size() - 0)) + 0;
        selectedSrCategory.set(tmTaskTypePage.listOfSrCategories.get(index).getText().trim());
        tmTaskTypePage.listOfSrCategories.get(index).click();
        // selectDropDown ( tmTaskTypePage.srCategory, selectedSrCategory);
    }

    @Then("I verify SR category field is displayed on create task screen")
    public void verifySrCategoryFieldDisplayed() {
        Assert.assertTrue(isElementDisplayed(tmTaskTypePage.srCategory));
    }

    @Then("I verify SR category field is mandatory")
    public void verifySrCategoryFieldIsMandatory() {
        Assert.assertTrue(isElementDisplayed(tmTaskTypePage.srCategoryFieldErrorMessage));
        Assert.assertEquals(tmTaskTypePage.srCategoryFieldErrorMessage.getText(), "The SR Category is required and cannot be left blank.");
    }

    @Then("I verify SR category field has values")
    public void verifySrCategoryFieldValues(List<String> expValues) {
        List<String> actValues = new ArrayList<>();
        tmTaskTypePage.srCategory.click();
        waitFor(2);
        for (WebElement element : tmTaskTypePage.listOfSrCategories) {
            actValues.add(element.getText().trim());
        }
        Assert.assertEquals(actValues, expValues);
    }

    @When("I click on the created SR")
    public void clickOnCreatedSr() {
        i_navigate_to_newly_created_task_type_details_page();
    }

    @Then("I verify the SR checkbox and SR category values are saved")
    public void verifySrCHeckBoxAndCategoriesSaved() {
        Assert.assertTrue(tmTaskTypePage.serviceRequestCheckBox.getAttribute("value").equalsIgnoreCase("true"));
        Assert.assertEquals(selectedSrCategory.get(), tmTaskTypePage.lblSrCategory.getText().trim());
    }

    @Then("I verify icon is displayed for Task and Service Request")
    public void iconDisplayed() {
        waitFor(2);
        boolean recordFound = false;
        String typeOfRequest = "";
        for (WebElement record : tmTaskTypePage.tasktypeRecords) {
            if (record.findElement(By.xpath("./td[2]/a")).getText().equalsIgnoreCase(taskType.get())) {
                recordFound = true;
                typeOfRequest = record.findElement(By.xpath("./td[1]/span")).getAttribute("title");
                break;
            }
        }
        Assert.assertTrue(recordFound);
        Assert.assertEquals(typeOfRequest, "Service Request");
    }

    @Then("I verify SR category field is single select dropdown")
    public void verifySrCategoryIsSingleSelectableFiled() {

        tmTaskTypePage.srCategory.click();
        waitFor(1);
        tmTaskTypePage.listOfSrCategories.get(0).click();
        waitFor(1);
        tmTaskTypePage.srCategory.click();
        waitFor(1);
        tmTaskTypePage.listOfSrCategories.get(1).click();
        waitFor(1);
        Assert.assertTrue(tmTaskTypePage.listOfSelectedSrCategories.size() == 1);
    }


    @When("I click add associate template button")
    public void clickAddTemplateButton() {
        scrollToElement(tmTaskTypePage.lstTaskPriority);
        click(tmTaskTypePage.addAssocTemplateButton);
    }

    //CP-1075 - author Paramita
    @Then("I see display of 'ADD TEMPLATE' button")
    public void display_of_add_template_button() {
        scrollToElement(tmTaskTypePage.assocaiteTemplate);
        Assert.assertTrue(tmTaskTypePage.templateButton.isDisplayed(), "ADD Template is not displaying");
    }


    @Then("I see no row get added under Associate Template section on click'ADD TEMPLATE' button")
    public void no_records_templatesection() {
        jsClick(tmTaskTypePage.templateButton);
        Assert.assertTrue(tmTaskTypePage.noRecordText.isDisplayed(), "Template Row is available");
    }

    @And("I click on Add template button")
    public void click_add_template_button() {
        jsClick(tmTaskTypePage.templateButton);
        waitFor(1);
    }

    @And("I click on restrict duplicate checkbox")
    public void i_click_on_restrict_duplicate_checkbox() {
        scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        if (tmTaskTypePage.restrictDuplicateCheckbox.getAttribute("value").equalsIgnoreCase("false"))
            tmTaskTypePage.restrictDuplicateCheckbox.click();
    }

    @And("I click on 'Add template' button once again")
    public void add_template_button() {
        jsClick(tmTaskTypePage.templateButton);
        //scrollDownUsingActions(1);
        waitFor(1);

    }

    @Then("I see blank record get added in Template section")
    public void blank_record_added_in_template_section() {
        waitFor(2);
        Assert.assertTrue(tmTaskTypePage.blankRecordCount.size() == 1, "Blank record Row is not get added");
        Assert.assertTrue(tmTaskTypePage.templateDropdownValue.getAttribute("value") == null, "Template dropdown value not showing blank");
    }

    @And("I provide the required task type information")
    public void enterRequiredTaskTypeDetails() {
        waitForVisibility(tmTaskTypePage.txtTaskType, 5);
        taskType.set( RandomStringUtils.randomAlphabetic(5));
        clearAndFillText(tmTaskTypePage.txtTaskType, taskType.get());
        clearAndFillText(tmTaskTypePage.txtDueInDays, RandomStringUtils.randomNumeric(2));
        selectDropDown(tmTaskTypePage.lstTaskPriority, "3");
    }

    @And("I provide Associate Screen name")
    public void enterScreenName() {
        selectDropDown(tmTaskTypePage.screenName, "Active Contact");
        waitFor(1);
    }

    @And("I provide the permission information for creating task")
    public void enterPermssionTaskTypeInformation() {
        waitFor(1);
        tmTaskTypePage.lstPermisionGroupToWork.click();
        tmTaskTypePage.txtPermisionGroupToWork.sendKeys("Csr" + Keys.ENTER);
        waitFor(3);
        tmTaskTypePage.lstPermisionGroupToWorkEscalated.click();
        tmTaskTypePage.txtPermisionGroupToWorkEscalated.sendKeys("Csr" + Keys.ENTER);
        waitFor(3);
    }


    @When("I delete task template")
    public void delete_taskTemplate() {
        tmTaskTypePage.deleteTemplate.click();
        waitFor(1);
    }

    //CP-8954- Author - Paramita

    @And("I click on Associate Screen dropdown field")
    public void click_associate_dropdown_field() {
        tmTaskTypePage.screenName.click();
    }

    @Then("I see dropdown options sorted in alphabetical order")
    public void screen_dropdown_options_sorted_in_alphabetical_order() {
        List<String> expectedScreenOption = Arrays.asList("Active Contact", "Application Details", "Case Demographic Info Members Subtab", "Create Correspondence", "Enrollment Update",
                "Member Matching", "View Consumer Profile", "View Contact Record Details",
                "View Inbound Correspondence Details", "View Outbound Correspondence Details",
                "View Service Request Details", "View Task Details");
        for (int i = 1; i < tmTaskTypePage.actualScreenvalue.size(); i++) {
            Assert.assertEquals(tmTaskTypePage.actualScreenvalue.get(i).getText(), expectedScreenOption.get(i));
            obtainedScreenValue.get().add(tmTaskTypePage.actualScreenvalue.get(i).getText());
        }

        Collections.sort(obtainedScreenValue.get());
        for (String s : obtainedScreenValue.get()) {
            sortedScreenValue.get().add(s);
        }

        Collections.sort(sortedScreenValue.get());
        Assert.assertTrue(sortedScreenValue.get().equals(obtainedScreenValue.get()), "Screen value are not alphabetically sorted");
    }

    @And("I click Edit task type button")
    public void click_edit_task_type_button() {
        waitForClickablility(tmTaskTypePage.taskTypeEDITButton, 10);
        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
        executor.executeScript("arguments[0].click();", tmTaskTypePage.taskTypeEDITButton);
    }

    @And("I select dropdown value as {string}")
    public void select_dropdown_value_as_active_contact(String fieldname) {
        waitFor(1);
        WebElement e1;
        e1 = Driver.getDriver().findElement(By.xpath("//ul//li//*[text()='" + fieldname + "']"));
        jsClick(e1);
    }

    @Then("I verify update success message is displayed on View task type screen")
    public void update_success_message_is_displayed_on_view_task_type_screen() {
        Assert.assertTrue(tmTaskTypePage.successSnackbar.isDisplayed(), "Update success message is not displayed");
        Assert.assertTrue(tmTaskTypePage.taskTypeEDITButton.isDisplayed(), "Edit Task type button is  displaying");
    }

    @Then("I verify update success message is displayed")
    public void i_verify_update_success_message_is_displayed() throws Throwable {
        Assert.assertTrue(tmTaskTypePage.successSnackbar.isDisplayed(), "Update success message is not displayed");
        waitFor(3);
    }

    @Then("I verify saved value for screen name {string} in View screen")
    public void verifySavedScreenName(String savedscreen) {
        waitFor(1);
        Assert.assertEquals(tmTaskTypePage.screenName.getAttribute("value"), savedscreen, "Saved Screen name is not matching");
    }

    @And("I scroll down to Associate Screen")
    public void scrollToAssociateScreenName() {
        scrollToElement(tmTaskTypePage.associateScreen);
    }

    @When("I select following required links radio buttons and checkbox")
    public void i_select_required_links_radio_buttons_something_something_and_checkbox(List<String> fields) {
        for (String field : fields) {
            switch (field) {
                case "CASE RADIO BUTTON":
                    tmTaskTypePage.caseRadioBtnInViewMode.click();
                    break;
                case "CONSUMER RADIO BUTTON":
                    tmTaskTypePage.consumerRadioBtnInViewMode.click();
                    break;
                case "CASE AND CONSUMER RADIO BUTTON":
                    tmTaskTypePage.caseNconsumerRadioBtnInViewMode.click();
                    break;
                case "CORRESPONDENCE CHECKBOX":
                    tmTaskTypePage.correspandanceChkBxInViewMode.click();
                    break;

            }

        }
    }


    @When("I click on back button on Task Type Details screen")
    public void clickBackButtonOnTaskCreatePage() {
        waitForVisibility(tmTaskTypePage.btnBack, 5);
        tmTaskTypePage.btnBack.click();
    }

    @Then("I verify Required Links got saved as per selection")
    public void i_verify_required_links_got_saved_as_per_selection(List<String> fields) {
        for (String field : fields) {
            switch (field) {
                case "CASE RADIO BUTTON":
                    Assert.assertTrue(tmTaskTypePage.caseRadioBtnInViewMode.isEnabled());
                    break;
                case "CONSUMER RADIO BUTTON":
                    Assert.assertTrue(tmTaskTypePage.consumerRadioBtnInViewMode.isEnabled());
                    break;
                case "CASE AND CONSUMER RADIO BUTTON":
                    Assert.assertTrue(tmTaskTypePage.caseNconsumerRadioBtnInViewMode.isEnabled());
                    break;
                case "CORRESPONDENCE CHECKBOX":
                    Assert.assertTrue(tmTaskTypePage.correspandanceChkBxInViewMode.isEnabled());
                    break;
            }
        }
    }

    @Then("I verify readonly mode value for screen name in View Task type screen")
    public void verify_read_mode_value_for_screen_name_in_view_task_type_screen() {
        Assert.assertTrue(!tmTaskTypePage.associateScreenDD.getAttribute("value").isEmpty(), "Screen name  showing empty value");
        Assert.assertTrue(tmTaskTypePage.associateScreenDD.getAttribute("class").contains("Mui-disabled") == true, "field value not showing readonly");
    }

    @Then("I verify screen disables and clears any existing content from the PERMISSION GROUP TO WORK and the PERMISSION GROUP TO WORK - ESCALATED fields")
    public void verifyPermissionGroupToWorkAndPermissionGroupToWorkEscalatedFieldsAreClearedAndDisabled() {
        waitFor(1);
        assertEquals(tmTaskTypePage.txtPermisionGroupToWork.getAttribute("disabled"), "true");
        assertEquals(tmTaskTypePage.txtPermisionGroupToWorkEscalated.getAttribute("disabled"), "true");
        Assert.assertTrue(tmTaskTypePage.txtPermisionGroupToWork.getText().isEmpty(), "PERMISSION GROUP TO WORK Field is not empty");
        Assert.assertTrue(tmTaskTypePage.txtPermisionGroupToWorkEscalated.getText().isEmpty(), "PERMISSION GROUP TO WORK - ESCALATED Field is not empty value");

    }

    @When("I select a value for SR category field on Edit Task Type page")
    public void selectValueForSrCategoryFieldOnEditTaskType() {
        //scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        tmTaskTypePage.srCategory.click();
        System.out.println(tmTaskTypePage.listOfSrCategories.size());
        int index = (int) (Math.random() * (tmTaskTypePage.listOfSrCategories.size() - 0)) + 0;
        selectedSrCategory.set(tmTaskTypePage.listOfSrCategories.get(index).getText().trim());
        tmTaskTypePage.listOfSrCategories.get(index).click();
        // selectDropDown ( tmTaskTypePage.srCategory, selectedSrCategory);
    }

    @Then("I verify Permission Group to Work drop down values as expected")
    public void verifyPermissionToWorkDropDownValues(List expectedValues) {
        //scrollToElement(tmTaskTypePage.lstAssociateScreen);
        scrollToElement(tmTaskTypePage.associateScreen);
        waitFor(2);
        List actualValues = new LinkedList();
        waitForVisibility(tmTaskTypePage.permisionGroupToWorkSeletedValues.get(0), 5);
        for (int i = 0; i < tmTaskTypePage.permisionGroupToWorkSeletedValues.size(); i++) {
            actualValues.add(tmTaskTypePage.permisionGroupToWorkSeletedValues.get(i).getText());
        }
        List expectedValuess = new LinkedList(expectedValues);
        Collections.sort(actualValues);
        Collections.sort(expectedValuess);
        assertEquals(actualValues, expectedValues, "Permission Group to Work drop down values are incorrect");
    }

    @Then("I verify Permission Group to Work Escalate drop down values as expected")
    public void verifyPermissionToEscalateDropDownValues(List expectedValues) {
        waitFor(2);
        List actualValues = new LinkedList();
        waitForVisibility(tmTaskTypePage.permisionGroupToWorkEscalatedSeletedValues.get(0), 5);
        for (int i = 0; i < tmTaskTypePage.permisionGroupToWorkEscalatedSeletedValues.size(); i++) {
            actualValues.add(tmTaskTypePage.permisionGroupToWorkEscalatedSeletedValues.get(i).getText());
        }
        List expectedValuess = new LinkedList(expectedValues);
        Collections.sort(actualValues);
        Collections.sort(expectedValuess);
        assertEquals(actualValues, expectedValuess, "Permission Group to Escalate drop down values are incorrect");
    }

    @Then("I verify Permission Group to Create or Edit drop down values as expected")
    public void verifyPermissionToCreateOrEditDropDownValues(List expectedValues) {
        waitFor(2);
        List actualValuesCreated = new LinkedList();
        List actualValueEdited = new LinkedList();
        waitForVisibility(tmTaskTypePage.permisionGroupToWorkCreatedSeletedValues.get(0), 5);
        for (int i = 0; i < tmTaskTypePage.permisionGroupToWorkCreatedSeletedValues.size(); i++) {
            actualValuesCreated.add(tmTaskTypePage.permisionGroupToWorkCreatedSeletedValues.get(i).getText());
            actualValueEdited.add(tmTaskTypePage.permisionGroupToWorkEditSeletedValues.get(i).getText());
        }
        List expectedValuess = new LinkedList(expectedValues);
        Collections.sort(actualValuesCreated);
        Collections.sort(actualValueEdited);
        Collections.sort(expectedValuess);
        assertEquals(actualValuesCreated, expectedValuess, "Permission Group to Create drop down values are incorrect");
        assertEquals(actualValueEdited, expectedValuess, "Permission Group to Edit drop down values are incorrect");

    }

    @And("I get rawLogs for {string} {string} {string} {string}")
    public void iGetRawLogsFor(String arg0, String arg1, String arg2, String arg3) {
        List<String> rawList = EventsUtilities.getRawLogs(arg0);
        for (int i = 0; i <= rawList.size() - 1; i++) {
            if (rawList.get(i).contains("permissions")) rawLogs.set(rawList.get(i));
            else continue;
        }
        String today = apitdu.get().getCurrentDateAndTime("yyyy-MM-dd");
        for (int i = 0; i <= 0; i++) {
            Assert.assertTrue(rawLogs.get().contains("\\\"startDate\\\":\\\"" + today));
            Assert.assertTrue(rawLogs.get().contains("\\\"endDate\\\":null"), "End Date Is Not Null");
        }
        String[] rawArray = rawLogs.get().split("permissions");
        rawPostData.set(rawArray[1]);
        Assert.assertTrue(rawPostData.get().contains(arg1));
        Assert.assertTrue(rawPostData.get().contains(arg2));
        Assert.assertTrue(rawPostData.get().contains(arg3));
    }

    @Then("I verify fields in task type screen")
    public void verifyFieldsInTaskTypeScreen(Map<String, String> data) {
        Assert.assertEquals(tmTaskTypePage.lblTaskTypeName.getText(), data.get("taskType"), "Task type not matches");
        Assert.assertEquals(tmTaskTypePage.serviceRequestCheckBox.getAttribute("value"), data.get("serviceRequestCBStatus"), "Service request check box value not matches");
        Assert.assertEquals(tmTaskTypePage.readPriorityVal.getText(), data.get("priority"), "Priority not matches");
        Assert.assertEquals(tmTaskTypePage.readDueInDays.getText(), data.get("dueInDays"), "Type of days value is not present");
        Assert.assertEquals(tmTaskTypePage.readTaskDescription.getText(), data.get("description"), "Description not matches");
        Assert.assertEquals(tmTaskTypePage.readSelectTemplate.getText(), data.get("selectTemplate"), "Select template not matches");
        Assert.assertEquals(tmTaskTypePage.associateScreenDD.getAttribute("value"), data.get("associateScreenDD"), "Select template not matches");
    }

    @And("I verify that Systematically Close checkbox is displayed")
    public void verifySystematicallyCloseCheckBoxDisplayed() {
        Assert.assertTrue(isElementDisplayed(tmTaskTypePage.systematicallyCloseCheckBox), "Systematically close check box is not displayed");
    }

    @And("I verify that restrict duplicate checkbox is displayed")
    public void verifyrestrictDuplicateCheckBoxDisplayed() {
        Assert.assertTrue(isElementDisplayed(tmTaskTypePage.restrictDuplicateCheckbox), "restrict duplicate check box is not displayed");
    }

    @Then("I select the Systematically Close checkbox in {string} page")
    public void i_select_the_Systematically_Close_checkbox_in_page(String string) {
        if (!string.equalsIgnoreCase("Edit")) {
            scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        }
        if (tmTaskTypePage.systematicallyCloseCheckBox.getAttribute("value").equalsIgnoreCase("false"))
            tmTaskTypePage.systematicallyCloseCheckBox.click();
    }

    @Then("I select the restrict duplicate checkbox in {string} page")
    public void i_select_the_restrict_duplicate_checkbox_in_page(String string) {
        if (!string.equalsIgnoreCase("Edit")) {
            scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        }
        if (tmTaskTypePage.restrictDuplicateCheckbox.getAttribute("value").equalsIgnoreCase("false"))
            tmTaskTypePage.restrictDuplicateCheckbox.click();
    }


    @When("I uncheck the Systematically Close checkbox in {string} page")
    public void i_uncheck_the_Systematically_Close_checkbox_in_Page(String string) {
        if (!string.equalsIgnoreCase("Edit")) {
            scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        }
        if (tmTaskTypePage.systematicallyCloseCheckBox.getAttribute("value").equalsIgnoreCase("true"))
            tmTaskTypePage.systematicallyCloseCheckBox.click();
    }

    @When("I uncheck the restrict duplicate checkbox in {string} page")
    public void i_uncheck_the_restrict_duplicate_checkbox_in_Page(String string) {
        if (!string.equalsIgnoreCase("Edit")) {
            scrollToElement(tmTaskTypePage.lblCreateTaskTypeHeader);
        }
        if (tmTaskTypePage.restrictDuplicateCheckbox.getAttribute("value").equalsIgnoreCase("true"))
            tmTaskTypePage.restrictDuplicateCheckbox.click();
    }

    @Then("I verify that Systematically Close checkbox is not mandatory")
    public void verifySystematicallyCloseCheckBoxIsMandatoryField() {
        waitForVisibility(tmTaskTypePage.successSnackbar, 5);
        Assert.assertTrue(tmTaskTypePage.taskTypeCreatedMessage.isDisplayed(), "Systematically close check box is mandatory");
    }

    @Then("I verify that restrict duplicate checkbox is not mandatory")
    public void verifyrestrictduplicateCheckBoxIsMandatoryField() {
        waitForVisibility(tmTaskTypePage.successSnackbar, 5);
        Assert.assertTrue(tmTaskTypePage.taskTypeCreatedMessage.isDisplayed(), "Restrict duplicate check box is mandatory");
    }

    @And("I verify that systemtically Close checkbox is disabled and cleared out")
    public void iVerifyThatSystemticallyCloseCheckboxIsDisabledAndClearedOut() {
        Assert.assertFalse((tmTaskTypePage.systematicallyCloseCheckBox.isEnabled()), "SYSTEMATICALLY CLOSE checkbox is not disabled and cleared out");
        assertEquals(tmTaskTypePage.systematicallyCloseCheckBoxDC.getAttribute("aria-disabled"), "true", "SYSTEMATICALLY CLOSE checkbox is not disabled and cleared out");
    }

    @And("I verify that restrict duplicate checkbox is enabled")
    public void iVerifyThatrestrictduplicateCheckboxIsEnabled() {
        waitFor(2);
        Assert.assertTrue((tmTaskTypePage.restrictDuplicateCheckbox.isEnabled()), "Restrict duplicate checkbox is not disabled and cleared out");
    }

    @Then("I uncheck the Service Request checkbox on Edit Task Type page")
    public void uncheckServiceRequestCheckboxOnEditTaskTypePage() {
        if (tmTaskTypePage.serviceRequestCheckBox.getAttribute("value").equalsIgnoreCase("true"))
            tmTaskTypePage.serviceRequestCheckBox.click();
    }

}
