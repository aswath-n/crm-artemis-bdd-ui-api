package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMEditTaskTypePage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMTaskTemplatePage;
import com.maersk.crm.pages.tm.TMTaskTypePage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;
import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class TM_EditTaskTypeStepDef extends BrowserUtils {

    TMEditTaskTypePage tmEditTaskTypePage = new TMEditTaskTypePage();

    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TM_TaskTypeStepDef  tmTaskTypeStepDef = new TM_TaskTypeStepDef();
    public static String taskType = "";



    //Use static wait to create task type record
    @When("I select existing task type {string} record, if not will create new task type with required information")
    public void selectExistingTaskTypeRecord(String taskTypeName) {
        WebElement w1;boolean flag=true;

        if(tmEditTaskTypePage.taskTypeCount.size() > 0) {
            for (WebElement row : tmEditTaskTypePage.taskTypeCount) {
                flag = row.findElement(By.xpath("./td[2]/a")).getText().equals(taskTypeName);

                if (flag) {
                    row.findElement(By.xpath("./td[2]/a")).click();
                    break;
                }
                flag=false;
            }
            if(!flag){
                tmEditTaskTypePage.btnAddTaskType.click();

                waitForVisibility(tmEditTaskTypePage.txtTaskType, 5);
                clearAndFillText(tmEditTaskTypePage.txtTaskType, taskTypeName);
                clearAndFillText(tmEditTaskTypePage.txtDueInDays, RandomStringUtils.randomNumeric(2));
                selectDropDown(tmEditTaskTypePage.lstTaskPriority, "3");
                clearAndFillText(tmEditTaskTypePage.txtTaskDescription, "Description");


                waitFor(3);
                tmTaskTypeStepDef.click_add_template_button();
                selectDropDown(tmEditTaskTypePage.selectTemplate, "General");
                waitFor(1);
                clearAndFillText(tmEditTaskTypePage.startDate, getCurrentDate());
                clearAndFillText(tmEditTaskTypePage.endDate, getGreaterDate(3));
                tmTaskTypeStepDef.click_add_template_button();


                selectDropDown(tmEditTaskTypePage.lstAssociateScreen, "Active Contact");
                waitFor(1);

                tmTaskTypeStepDef.enterPermssionTaskTypeInformation();
                click(tmEditTaskTypePage.editSaveButton);
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//a[text()='" + taskTypeName + "']")).click();
            }
        }


    }

    @And("I click on Edit option")
    public void verifyEditButtonInViewTaskTypeScreen() {
        waitFor(1);
        scrollUpUsingActions(3);
        waitFor(1);
        scrollRight();
        waitFor(1);
        Assert.assertTrue(tmEditTaskTypePage.taskTypeEDITButton.isDisplayed(), "Edit button is not displayed");
        //jsClick(tmEditTaskTypePage.taskTypeEDITButton);
        click(tmEditTaskTypePage.taskTypeEDITButton);
    }

    @And("I verify the Task Type Id is displayed")
    public void verifyTaskTypeIdField(){
    Assert.assertTrue(tmEditTaskTypePage.taskTypeIdEditPage.isDisplayed());
    }

    @Then("I see the fields in Ready only state in Edit Task Type screen")
    public void verify_read_only_fields_edit_TastType_screen() {
        waitFor(3);//  using static wait To get ready only fields value and element

        String editTaskTypeReadOnlyHeader[] = {"CREATED BY", "CREATED ON", "TASK TYPE", "PRIORITY", "DUE IN DAYS", "DESCRIPTION", "SELECT TEMPLATE", "VERSION", "START DATE", "END DATE"};

        //  using static wait To get ready only fields value and element
        waitFor(1);
        scrollDownUsingActions(1);
        waitFor(1);
        scrollDownUsingActions(2);
        scrollToElement(tmEditTaskTypePage.associateTemplate);
        scrollToElement(tmEditTaskTypePage.taskTypeDetails);

        for (int i = 0; i < tmEditTaskTypePage.editTaskReadOnlyfield.size() - 1; i++) {
            Assert.assertTrue(tmEditTaskTypePage.editTaskReadOnlyfield.get(i).getText().contains(editTaskTypeReadOnlyHeader[i]));
            Assert.assertEquals(tmEditTaskTypePage.readOnlyTasktypefieldsVal.get(i).isEnabled(),tmEditTaskTypePage.readOnlyTasktypefieldsVal.get(i).isDisplayed(),"value is not displaying");
        }
    }



    @And("I should see the Save or Cancel Button in Edit Task Type screen")
    public void display_Save_Cancel_button_edit_TastType_screen() {
        scrollToElement(tmEditTaskTypePage.taskAssociateScreen);
        Assert.assertTrue(tmEditTaskTypePage.taskTypeSAVECancelButton.size() == 2, "SAVE and Cancel button is not displayed");
        Assert.assertTrue(isElementDisplayed(tmEditTaskTypePage.editSaveButton), "SAVE  button is not displayed");
        Assert.assertTrue(isElementDisplayed(tmEditTaskTypePage.editCancelButton), "Cancel  button is not displayed");
    }


  @When("I clear data for required Associate permission group {string},then I see error error validation message")
  public void required_permission_group_field(String fieldName){
        scrollToElement(tmEditTaskTypePage.taskAssociateScreen);
        Actions actions = new Actions(Driver.getDriver());
        switch(fieldName){
        case "ASSOCIATE PERMISSION GROUP TO WORK":
            actions.moveToElement(tmEditTaskTypePage.roleDeleteIconPerm);
            actions.click().build().perform();
           tmEditTaskTypePage.editSaveButton.click();
          Assert.assertTrue(tmEditTaskTypePage.errorMesg1.isDisplayed(), "The Permission Group Name is required and cannot be left blank.");
           break;
        case "ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED":
            actions.moveToElement(tmEditTaskTypePage.roleDeleteIcon2ndPerm);
            actions.click().build().perform();
           tmEditTaskTypePage.editSaveButton.click();
          Assert.assertTrue(tmEditTaskTypePage.errorMesg2.isDisplayed(), "The Permission Group Name is required and cannot be left blank.");
           break;
        }
  }

    @Then("I should see the error validation message for the required permission field")
    public void error_Mesg_Required_PermissionField() {
        Assert.assertTrue(tmEditTaskTypePage.errorMesg1.isDisplayed(), "The Permission Group Name is required and cannot be left blank.");
        Assert.assertTrue(tmEditTaskTypePage.errorMesg2.isDisplayed(), "The Permission Group Escalated Name is required and cannot be left blank.");

    }


    @When("I clear data for optional Associate permission group {string}")
    public void optional_permission_group_field(String optionalfield) {
        waitFor(1);
        scrollToElement(tmEditTaskTypePage.taskAssociateScreen);
        if(optionalfield.equals("ASSOCIATE PERMISSION GROUP TO CREATE")) {
            for(WebElement e1:tmEditTaskTypePage.roleDeleteIcon3rdPerm){
                e1 = Driver.getDriver().findElement(By.xpath("//h5[text()='ASSOCIATE PERMISSION GROUP TO CREATE']/..//following-sibling::div//div[@role='button']//*[name()='svg']"));
                e1.click();
            }
        }
        if(optionalfield.equals("ASSOCIATE PERMISSION GROUP TO EDIT")) {
            for(WebElement e1:tmEditTaskTypePage.roleDeleteIcon3rdPerm){
                e1 = Driver.getDriver().findElement(By.xpath("//h5[text()='ASSOCIATE PERMISSION GROUP TO EDIT']/..//following-sibling::div//div[@role='button']//*[name()='svg']"));
                e1.click();
            }
        }
        click(tmEditTaskTypePage.editSaveButton);
    }


    @Then("I should be able to save Task Type record successfully")
    public void save_task_successfully() {
        Assert.assertTrue(tmEditTaskTypePage.successMessage.isDisplayed(), "Success Message is not displayed");
        Assert.assertTrue(tmEditTaskTypePage.editSuccessMesg.isDisplayed(), "The Task type page is not in View details screen");
//        Assert.assertTrue(tmEditTaskTypePage.taskTypeEDITButton.isDisplayed(), "The Task type page is not in View details screen");
//        Assert.assertTrue(tmEditTaskTypePage.taskTypeSAVECancelButton.size() == 0, "SAVE and Cancel button is displayed");
     }


    @And("I should see value get cleared for the optional permission field")
    public void clearedValueOptionalField() {
        Assert.assertTrue(tmEditTaskTypePage.optionalFieldValue.isEnabled(), "Permission Value is displayed");
    }



    @And("I update task type permission {string}")
    public void updatePermissionFieldEditTaskType(String field) {
        scrollToElement(tmEditTaskTypePage.taskAssociateScreen);
        if(field.equals("ASSOCIATE PERMISSION GROUP TO CREATE")){
            tmEditTaskTypePage.lstPermisionGroupToCreate.click();
            Driver.getDriver().findElement(By.xpath("//li[contains(text(), 'Csr')]")).click();
        }
        if(field.equals("ASSOCIATE PERMISSION GROUP TO EDIT")){
            tmEditTaskTypePage.lstPermisionGroupToEdit.click();
            Driver.getDriver().findElement(By.xpath("//li[contains(text(), 'Csr')]")).click();
        }

    }

    @And("I click on available {string} option")
    public void clickOnEditTaskTypeButton(String button){
        switch (button){
            case "Cancel":
                waitForVisibility(tmEditTaskTypePage.editCancelButton,10);
                click(tmEditTaskTypePage.editCancelButton);
                break;

            case "Back Arrow":
                waitForVisibility(tmEditTaskTypePage.backArrow,10);
                click(tmEditTaskTypePage.backArrow);
                break;

           case "User icon":
                waitForVisibility(tmEditTaskTypePage.UserIcon,10);
                tmEditTaskTypePage.UserIcon.click();
                break;
        }

    }

    @And("I click on cancel option on warning message")
    public void clickOnCancelOfWarningMessage(){
        waitForVisibility(tmEditTaskTypePage.warningMsgCancelBtn,10);
        click(tmEditTaskTypePage.warningMsgCancelBtn);
    }


    @Then("I verify user is on edit task type screen and information should not save")
    public void verify_remain_on_editTaskType_screen(){
        Assert.assertTrue(tmEditTaskTypePage.taskTypeDetails.isDisplayed(),"It is not remain on the same page");
        Assert.assertTrue(!isElementDisplayed(tmEditTaskTypePage.taskTypeEDITButton),"Edit button is displaying");
   }

    @And("I click on Continue option in warning message")
    public void clickOnContinueOfWarningMessage(){
        waitForVisibility(tmEditTaskTypePage.btnContinueWarningWindow,10);
        click(tmEditTaskTypePage.warningMsgContinueBtn);
        //waitFor(2);
    }

    @Then("I verify user is redirected to task type list screen")
    public void verifyUserIsRedirectedToTaskTypeListScreen() {
        try {
            Assert.assertTrue(tmEditTaskTypePage.lblTaskType.isDisplayed(), "task type configuration page is not displayed");
        }catch(Exception e){
            Assert.assertTrue(tmEditTaskTypePage.userListScreen.isDisplayed(), "task type configuration page is not displayed");
        }
    }


    @And("I update permission {string} with {string} and click on Save")
    public void updatePermissionField(String fieldName, String value) {
        scrollToElement(tmEditTaskTypePage.taskAssociateScreen);
        if(fieldName.equals("ASSOCIATE PERMISSION GROUP TO CREATE")){
            tmEditTaskTypePage.lstPermisionGroupToCreate.click();
            Driver.getDriver().findElement(By.xpath("//span[contains(text(), '"+value+"')]")).click();
        }
        if(fieldName.equals("ASSOCIATE PERMISSION GROUP TO EDIT")){
            tmEditTaskTypePage.lstPermisionGroupToEdit.click();
            Driver.getDriver().findElement(By.xpath("//span[contains(text(), '"+value+"')]")).click();
        }
        click(tmEditTaskTypePage.editSaveButton);

    }

    @Then("I verify edit task is saved successfully and redirect to View Task screen")
    public void editTaskSavedSuccessfully() {
        save_task_successfully();
    }


    @And("I provide all information for creating task without permission field")
    public void enterRequiredTaskTypeInformationWithoutPermissionField() {
        waitForVisibility(tmEditTaskTypePage.txtTaskType, 5);
        taskType = RandomStringUtils.randomAlphabetic(5);
        clearAndFillText(tmEditTaskTypePage.txtTaskType, taskType);
        clearAndFillText(tmEditTaskTypePage.txtDueInDays, RandomStringUtils.randomNumeric(2));
        selectDropDown(tmEditTaskTypePage.lstTaskPriority, "3");
        selectDropDown(tmEditTaskTypePage.lstAssociateScreen, "Active Contact");
    }

    @And("I click on Add Save button")
    public void clickEditSaveButton() {
        click(tmEditTaskTypePage.editSaveButton);
        BrowserUtils.waitFor(3);
    }

    @Then("I verify warning message displayed for required permission field")
    public void verifyWarningMEsg() {
        Assert.assertTrue(tmEditTaskTypePage.errorMesg1.isDisplayed(), "The Permission Group Name is required and cannot be left blank.");
        Assert.assertTrue(tmEditTaskTypePage.errorMesg2.isDisplayed(), "The Permission Group Name is required and cannot be left blank.");


    }

    @And("I navigate to the newly created task")
    public void iNavigateToTheNewlyCreatedTask() {
        WebElement el = Driver.getDriver().findElement(By.xpath("//a[text()='" + taskType + "']"));
        waitForVisibility(el, 5);
        hover(el);
        click(el);
        BrowserUtils.waitFor(3);
    }

    @Then("I verify that Systematically Close checkbox is not mandatory on Edit Task Type page")
    public void verifySystematicallyCloseCheckBoxIsMandatoryFieldOnEditTaskTypePage() {
        waitForVisibility(tmEditTaskTypePage.successMessage, 5);
        Assert.assertTrue(tmEditTaskTypePage.editSuccessMesg.isDisplayed(),"Systematically close checkbox is mandatory");
    }
    @Then("I verify that restrict duplicate checkbox is not mandatory on Edit Task Type page")
    public void verifyrestrictduplicateCheckBoxIsMandatoryFieldOnEditTaskTypePage() {
        waitForVisibility(tmEditTaskTypePage.successMessage, 5);
        Assert.assertTrue(tmEditTaskTypePage.editSuccessMesg.isDisplayed(),"restrict duplicate checkbox is mandatory");
    }






}
