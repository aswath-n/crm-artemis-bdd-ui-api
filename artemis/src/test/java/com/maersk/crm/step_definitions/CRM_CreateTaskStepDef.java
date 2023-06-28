package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.exception.CucumberException;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_CreateTaskStepDef extends CRMUtilities implements ApiBase {

    final ThreadLocal<CRM_GeneralTaskStepDef> generalTaskStepDef = ThreadLocal.withInitial(CRM_GeneralTaskStepDef::new);
    final ThreadLocal<CRM_ContactRecordUIStepDef> contactRecordUIStepDef = ThreadLocal.withInitial(CRM_ContactRecordUIStepDef::new);
    final ThreadLocal<CRM_CaseMemberStepDef> caseMemberStepDef = ThreadLocal.withInitial(CRM_CaseMemberStepDef::new);
    final ThreadLocal<CRM_TaskManagementStepDef> taskManagementStepDef = ThreadLocal.withInitial(CRM_TaskManagementStepDef::new);
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();

    /* Log into CRM and Creates the task
   Author - Aswath
   Date - 09/26/2019
   Parameters - NA
   */
    @Then("I logged into CRM and create a task")
    public void i_logging_into_crm_and_create_a_task() throws InterruptedException {
        contactRecordUIStepDef.get().i_logged_into_CRM_and_click_on_initiate_contact();
        caseMemberStepDef.get().i_searched_customer_have_First_Name_as_and_Last_Name_as("Emma", "Jones");
        contactRecordUIStepDef.get().i_link_the_contact_to_an_existing_Case_or_Consumer_Profile();
        generalTaskStepDef.get().navigateToGeneralTask("General");
        waitFor(2);
        generalTaskStepDef.get().SelectTaskDetails("", "", "Service", getRandomString(8));
        generalTaskStepDef.get().clickSaveButton();
        taskManagementStepDef.get().verifyTaskSaveSuccessMessage();
        waitFor(5);
        taskManagementStepDef.get().i_navigate_to_task_Managments_page("My Task");
    }

    /* Log into CRM and Creates the task
   Author - Aswath
   Date - 09/26/2019
   Parameters - NA
   */
    @Then("I logged into CRM and create a task w\\/o assignee")
    public void i_logging_into_crm_and_create_a_task_without_assigne() throws InterruptedException {
        contactRecordUIStepDef.get().i_logged_into_CRM_and_click_on_initiate_contact();
        caseMemberStepDef.get().i_searched_customer_have_First_Name_as_and_Last_Name_as("Emma", "Jones");
        contactRecordUIStepDef.get().i_link_the_contact_to_an_existing_Case_or_Consumer_Profile();
        generalTaskStepDef.get().navigateToGeneralTask("General");
        waitFor(2);
        generalTaskStepDef.get().SelectTaskDetails("", "", "", getRandomString(8));
        generalTaskStepDef.get().clickSaveButton();
        //generalTaskStepDef.get().i_verify_Success_message_is_displayed_for_task ();
        taskManagementStepDef.get().verifyTaskSaveSuccessMessage();
        taskManagementStepDef.get().i_navigate_to_task_Managments_page("My Task");
    }


    @Then("I logged into CRM and create a task with Task Type {string} and Status {string}")
    public void i_logging_into_crm_and_create_a_task_with_TaskType(String tasktype, String status) throws InterruptedException {
        contactRecordUIStepDef.get().i_logged_into_CRM_and_click_on_initiate_contact();
        caseMemberStepDef.get().i_searched_customer_have_First_Name_as_and_Last_Name_as("Emma", "Jones");
        contactRecordUIStepDef.get().i_link_the_contact_to_an_existing_Case_or_Consumer_Profile();
        generalTaskStepDef.get().navigateToGeneralTask(tasktype);
        waitFor(2);
        generalTaskStepDef.get().SelectTaskDetails("", "", "Service", getRandomString(8));
        selectDropDown(createGeneralTask.createTaskStatus, status);
        waitFor(2);
    }


    @Then("Verify the Disposition options are visible")
    public void verifyTheDispositionOptionsAre(List<String> options) {
        waitFor(1);
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Consumer not reached")) {
                selectDropDown(createGeneralTask.dispos, opt);
            } else if (opt.equalsIgnoreCase("Consumer reached")) {
                selectDropDown(createGeneralTask.dispos, opt);
            } else if (opt.equalsIgnoreCase("User closed")) {
                selectDropDown(createGeneralTask.dispos, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @And("Update the task slider status and verify disposition")
    public void updateTheTaskSliderStatusAs(List<String> options) {
        waitFor(3);
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Complete")) {
                waitFor(2);
                selectDropDown(createGeneralTask.createTaskStatus, opt);
            } else if (opt.equalsIgnoreCase("In Progress")) {
                waitFor(2);
                selectDropDown(createGeneralTask.createTaskStatus, opt);
                Assert.assertFalse(isElementDisplayed(createGeneralTask.dispositionTask));
            } else if (opt.equalsIgnoreCase("Escalated")) {
                waitFor(2);
                selectDropDown(createGeneralTask.createTaskStatus, opt);
                Assert.assertFalse(isElementDisplayed(createGeneralTask.dispositionTask));
            } else if (opt.equalsIgnoreCase("Cancel")) {
                waitFor(2);
                selectDropDown(createGeneralTask.createTaskStatus, opt);
                Assert.assertFalse(isElementDisplayed(createGeneralTask.dispositionTask));
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @Then("I select save without selecting a value for the Disposition field")
    public void iSelectSaveWithoutSelectingAValueForTheDispositionField() {
        waitFor(1);
        createGeneralTask.taskDetsave.click();
    }

    //comparre the error messasge
    @And("Verify the system displays an Error Message: {string}")
    public void verifyTheSystemDisplaysAnErrorMessage(String msg) {
        waitForVisibility(createGeneralTask.errorMessageTask, 10);
        Assert.assertTrue(createGeneralTask.errorMessageTask.isDisplayed());
    }

    @Then("I staying same UI and selecting value {string} from Disposition field and saving")
    public void iStayingSameUIAndSelectingValueFromDispositionFieldAndSaving(String val) {
        waitFor(2);
        selectDropDown(createGeneralTask.dispos, val);
        waitFor(1);
        createGeneralTask.taskDetsave.click();
    }

    @Then("I create task with status {string} and Disposition {string}")
    public void iCreateTaskWithStatusAndDisposition(String status, String dis) {
        waitFor(1);
        selectDropDown(createGeneralTask.createTaskStatus, status);
        waitFor(2);
        selectDropDown(createGeneralTask.dispos, dis);
        createGeneralTask.taskDetsave.click();
    }
}