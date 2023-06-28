package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIEventsRestController;
import com.maersk.crm.api_step_definitions.APITMEventController;
import com.maersk.crm.pages.tm.TMAddBusinessUnitPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMTaskTypePage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class TM_AddBusinessUnitStepDef extends BrowserUtils {
    TMTaskTypePage tmTaskTypePage = new TMTaskTypePage();
    TMListOfProjectsPage tmProjectlistPage = new TMListOfProjectsPage();
    TMAddBusinessUnitPage businessUnitPage = new TMAddBusinessUnitPage();
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();

    final ThreadLocal<TM_CreateProjectStepDef> tmCreateProjectStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
    final ThreadLocal<TM_SearchProjectStepDefs> tmSearchProjectStepDefs = ThreadLocal.withInitial(TM_SearchProjectStepDefs::new);
    final ThreadLocal<TM_EditProjectInformationStepDef> tmEditProjectInformationStepDef = ThreadLocal.withInitial(TM_EditProjectInformationStepDef::new);

    public static final ThreadLocal<String> businessUnitValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> startDateValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> endDateValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> descriptionValue = ThreadLocal.withInitial(String::new);

    final ThreadLocal<List<String>> actualTaskTypeToCompare = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> ExpectedTaskTypeName = ThreadLocal.withInitial(ArrayList::new);



    /*This method to Login to tenant manager and set the project context
        Author -Vidya
    */

    @Given("I logged into Tenant Manager and set the project context {string} value {string}")
    public void navigateToBusinessUnitDetailsPage(String field, String value) {
        tmCreateProjectStepDef.get().i_logged_into_Tenant_Manager_Project_list_page();
        tmSearchProjectStepDefs.get().i_search_for_a_project_by_value(field, value);
        tmEditProjectInformationStepDef.get().i_expand_a_random_project_to_view_the_details();
    }

     /*This method to Login to tenant manager and set the project context
        Author -Vidya
    */

    @Given("I logged into new Tenant Manager and set the project context {string} value {string}")
    public void navigateToBusinessUnitDetailsPage_1(String field, String value) {
        tmCreateProjectStepDef.get().i_logged_into_Tenant_Manager_Project_list_page();
        tmSearchProjectStepDefs.get().i_search_for_a_project_by_value(field, value);
        tmEditProjectInformationStepDef.get().i_expand_a_random_project_to_view_the_details();
    }

    /*This method to navigate to Business Unit Details Page
        Author -Vidya
    */

    @When("I navigate to business unit details page")
    public void navigateToBusinessUnitDetailsPage() {
        Assert.assertTrue(tmProjectDetailsPage.businessUnitIcon.isDisplayed(), "Business unit icon is  not displayed");
        tmProjectDetailsPage.businessUnitIcon.click();
        waitForVisibility(businessUnitPage.addButton, 10);
        Assert.assertTrue(businessUnitPage.addButton.isDisplayed(), "Add Business Unit button is not displayed");
        businessUnitPage.addButton.click();
        waitForVisibility(businessUnitPage.businessUnitLabl, 5);
        Assert.assertTrue(businessUnitPage.businessUnitLabl.isDisplayed(), "Business Unit Details page is not displayed");
    }

    /*This method to click on Add Button present on Business Unit List Page
        Author -Vidya
    */

    @When("I click on Add button")
    public void clickOnAddButton() {
        waitForVisibility(businessUnitPage.addButton, 10);
        businessUnitPage.addButton.click();
        waitForVisibility(businessUnitPage.businessUnitLabl, 5);
    }

    /*This method verifies All fields are displayed when I click on (+) Add Button
    Author -Vidya
     */
    @Then("I verify all the fields are displayed")
    public void verifyAllFieldsAreDisplayed() {
        Assert.assertTrue(businessUnitPage.businessUnitName.isDisplayed(), "Business Unit Name field is not displayed");
        Assert.assertTrue(businessUnitPage.startDate.isDisplayed(), "Start Date field is not displayed");
        Assert.assertTrue(businessUnitPage.endDate.isDisplayed(), "End Date field is not displayed");
        Assert.assertTrue(businessUnitPage.businessUnitDescription.isDisplayed(), "Description field is not displayed");
        Assert.assertTrue(businessUnitPage.saveButton.isDisplayed(), "Save button is not displayed");
        Assert.assertTrue(businessUnitPage.cancelButton.isDisplayed(), "Cancel button is not displayed");
        Assert.assertTrue(businessUnitPage.backArrow.isDisplayed(), "Back Arrow is not displayed");
        Assert.assertTrue(businessUnitPage.taskTypeDropDown.isDisplayed(), "Task type drop down");
    }

    /*This method verifies that Business Unit Name accepts only alphanumeric & spaces
    Input paramter - number of characters
    Author -Vidya
     */

    @Then("I verify business unit name accepts {int} alphanumeric spaces are allowed")
    public void businessUnitNameFieldValidation(Integer int1) {
        String text = "Business Unit 1 New Test verify 50 Character Allow";
        businessUnitPage.businessUnitName.sendKeys(text);
        Assert.assertTrue(businessUnitPage.businessUnitName.getAttribute("value").length() == int1, "Business Unit name field length is not 50 characters");
        text = "Business Unit 1 New Test verify 50 Character Allow1fghj";
        clearAndFillText(businessUnitPage.businessUnitName, text);
        Assert.assertTrue(businessUnitPage.businessUnitName.getAttribute("value").length() == int1, "Business Unit name field length is not 50 characters");
    }

    /*This method verifies that Description accepts only alphanumeric & spaces
    Input paramter - number of characters
    Author -Vidya
     */

    @Then("I verify description accepts {int} alphanumeric spaces are allowed")
    public void descriptionFieldValidation(Integer int1) {
        businessUnitPage.businessUnitDescription.sendKeys(getRandomString(150));
        Assert.assertTrue(businessUnitPage.businessUnitDescription.getAttribute("value").length() == int1, "Description field length is not 150 characters");
        clearAndFillText(businessUnitPage.businessUnitDescription, getRandomString(175));
        Assert.assertTrue(businessUnitPage.businessUnitDescription.getAttribute("value").length() == int1, "Description field length is not 150 characters");
    }

    /*This method enter data to all fields of Business Unit Details Page
        Author -Vidya
    */

    @When("I populate data on business unit details page {string},{string},{string},{string}")
    public void i_populate_data_on_project_role_page(String businessUnitName, String desc, String startDate, String endDate) {
        if (businessUnitName.equals("{random}")) {
            businessUnitValue.set(getRandomString(8));
            if (Character.isLowerCase(businessUnitValue.get().charAt(0))) {
                businessUnitValue.set(Character.toUpperCase(businessUnitValue.get().charAt(0)) +
                        businessUnitValue.get().substring(1));
            }
            clearAndFillText(businessUnitPage.businessUnitName, businessUnitValue.get());
        } else {
            clearAndFillText(businessUnitPage.businessUnitName, businessUnitValue.get());
        }

        descriptionValue.set("");
        if (desc.equals("{random}")) {
            descriptionValue.set(getRandomString(7));
            clearAndFillText(businessUnitPage.businessUnitDescription, descriptionValue.get());
        }

        startDateValue.set(startDate);
        if (startDate.equals("today")) startDateValue.set(getCurrentDate());
        else if (startDate.contains("-")) {
            startDateValue.set(getPriorDate(Integer.parseInt(startDate.replace("-", ""))));
        } else if (startDate.contains("+")) {
            startDateValue.set(getGreaterDate(Integer.parseInt(startDate.replace("+", ""))));
        }
        if (!startDateValue.get().equals("") && startDateValue.get() != null) {
            clearAndFillText(businessUnitPage.startDate, startDateValue.get());
        }

        endDateValue.set(endDate);
        if (endDate.equals("today")) endDateValue.set(getCurrentDate());
        else if (endDate.contains("-")) {
            endDateValue.set(getPriorDate(Integer.parseInt(endDate.replace("-", ""))));
        } else if (endDate.contains("+")) {
            endDateValue.set(getGreaterDate(Integer.parseInt(endDate.replace("+", ""))));
        }
        if (!endDateValue.get().equals("") && endDateValue.get() != null) {
            clearAndFillText(businessUnitPage.endDate, endDateValue.get());
        }
    }

    /*This method to click on Save button present on Business Unit Details Page
        Author -Vidya
    */

    @And("I click on Save button on business unit details page")
    public void clickOnSaveButton() {
        waitForVisibility(businessUnitPage.saveButton, 10);
        click(businessUnitPage.saveButton);
    }

    /*This method to verify End Date and Description fields are optional
        Author -Vidya
    */

    @Then("verify end date and description are optional")
    public void verifyOptionalFields() {
        waitForClickablility(businessUnitPage.businessUnitListPage, 10);
        Assert.assertTrue(businessUnitPage.businessUnitListPage.isDisplayed(), "End Date and Description fields are not optional");
        Assert.assertTrue(businessUnitPage.addButton.isDisplayed(), "End Date and Description fields are not optional");
    }

    /*This method to verify new Business Unit created Successfully
        Author -Vidya
    */

    @Then("I verify Business Unit Successfully Created message is displayed and I go back to Business Unit List page")
    public void verifySaveFunctionality() {
        waitForVisibility(businessUnitPage.successMessage, 10);
        Assert.assertTrue(businessUnitPage.successMessage.isDisplayed(), "Success Message is not displayed");
        Assert.assertTrue(businessUnitPage.successMessageTxt.isDisplayed(), "Success Message text is not displayed");
        Assert.assertTrue(businessUnitPage.businessUnitListPage.isDisplayed(), "It should not go to Business Unit List page");
        Assert.assertTrue(businessUnitPage.addButton.isDisplayed(), "It should not go to Business Unit List Page");
    }

    /*This method to verify can I add any no of Business Unit
        Author -Vidya
    */

    @Then("I verify can I add unlimited business Units")
    public void verifyCanIAddOneMoreBusinessUnit() {
        clickOnSaveButton();
        verifySaveFunctionality();
        clickOnAddButton();
    }

    /*This method to click on different button accourding to user requirement
        Author -Vidya
    */

    @And("I click on {string}")
    public void clickOnButton(String button) {
        switch (button) {
            case "Cancel":
                waitForVisibility(businessUnitPage.cancelButton, 10);
                click(businessUnitPage.cancelButton);
                break;

            case "Back Arrow":
                waitForVisibility(businessUnitPage.backArrow, 10);
                click(businessUnitPage.backArrow);
                break;

            case "Team Icon":
                waitForVisibility(tmProjectDetailsPage.teamIcon, 10);
                click(tmProjectDetailsPage.teamIcon);
                break;
        }

    }

    /*This method to verify Warning Message Popup
        Author -Vidya
    */

    @Then("I verify Warning message and Warning text is displayed with Continue and Cancel button")
    public void verifyWarningMessage() {
        waitForVisibility(businessUnitPage.warningMessage, 10);
        Assert.assertTrue(businessUnitPage.warningMessage.isDisplayed(), "Warning Message is not displayed");
        Assert.assertTrue(businessUnitPage.warningMessageTxt.isDisplayed(), "Warning message text is not displayed");
        Assert.assertTrue(businessUnitPage.warningMsgContinueBtn.isDisplayed(), "Warning message Continue button is not displayed");
        Assert.assertTrue(businessUnitPage.warningMsgCancelBtn.isDisplayed(), "Warning message Cancel button is not displayed");
    }

    @Then("I verify it should display {string}")
    public void verifyItShouldDisplayBusinessUnitListPage(String page) {
        switch (page) {
            case "Business Unit List page":
                waitForVisibility(businessUnitPage.businessUnitListPage, 10);
                Assert.assertTrue(businessUnitPage.businessUnitListPage.isDisplayed(), "Business Unit List page is not displayed");
                Assert.assertTrue(businessUnitPage.addButton.isDisplayed(), "Business Unit List page is not displayed");
                break;

            case "Team List page":
                waitForVisibility(businessUnitPage.teamListPage, 10);
                Assert.assertTrue(businessUnitPage.teamListPage.isDisplayed(), "Team List page is not displayed");
                Assert.assertTrue(businessUnitPage.addButton.isDisplayed(), "Team List page is not displayed");
                break;

            case "Project List page":
                waitForVisibility(tmProjectlistPage.projectList, 10);
                Assert.assertTrue(tmProjectlistPage.projectList.isDisplayed(), "Project List page is not displayed");
                Assert.assertTrue(tmProjectlistPage.createProjectButton.isDisplayed(), "Project List page is not displayed");
                break;
        }
    }

    /*This method to click on cancel button present on warning message
        Author -Vidya
    */

    @And("I click on cancel button on warning message in TM App")
    public void clickOnCancelButtonOfWarningMessage() {
        waitForVisibility(businessUnitPage.warningMsgCancelBtn, 10);
        click(businessUnitPage.warningMsgCancelBtn);
    }

    @Then("I verify it should remain on the same page and information should not save")
    public void verifyItShouldRemainsOnTheSamePage() {
        Assert.assertTrue(businessUnitPage.businessUnitLabl.isDisplayed(), "It is not remains on same page");
        Assert.assertNotEquals(businessUnitPage.businessUnitName.getAttribute("value"), "", "Business Unit Name field value is cleared");
        // Assert.assertNotEquals(businessUnitPage.startDate.);
    }


    /*This method to click on continue button present on warning message
        Author -Vidya
    */

    @And("I click on continue button on warning message in TM APP")
    public void clickOnContinueButtonOfWarningMsg() {
        waitForVisibility(businessUnitPage.warningMsgContinueBtn, 10);
        jsClick(businessUnitPage.warningMsgContinueBtn);
    }


    /*This method to verify filed level error messages
        Author -Vidya
    */

    @When("I see {string} message under the {string} field on business unit details page")
    public void i_see_message_under_the_field(String message, String field) {
        switch (field) {
            case "BusinessUnitName":
                waitForVisibility(businessUnitPage.businessUnitNameError, 10);
                assertTrue(businessUnitPage.businessUnitNameError.isDisplayed());
                assertTrue(businessUnitPage.businessUnitNameError.getText().toLowerCase().equals(message.toLowerCase()));
                break;

            case "StartDate":
                waitForVisibility(businessUnitPage.startDateError, 10);
                assertTrue(businessUnitPage.startDateError.isDisplayed());
                assertTrue(businessUnitPage.startDateError.getText().toLowerCase().equals(message.toLowerCase()));
                break;

            case "EndDate":
                waitForVisibility(businessUnitPage.endDateError, 10);
                assertTrue(businessUnitPage.endDateError.isDisplayed());
                assertTrue(businessUnitPage.endDateError.getText().toLowerCase().equals(message.toLowerCase()));
                break;
        }
    }

    /*This method to select task type
        Author -Vidya
        Refactored by Shruti 03/25/2019
    */

    @And("I select associate task type {string}")
    public void selectTaskType(String taskType) {
        waitForVisibility(businessUnitPage.taskTypeDropDown, 10);
        click(businessUnitPage.taskTypeDropDown);
        waitFor(1);
       /* System.out.println(Driver.getDriver().findElement(By.xpath("//*[contains(text(), 'eOIjXNnYCU')]")).getTagName());
        System.out.println(Driver.getDriver().findElement(By.xpath("//*[contains(text(), 'eOIjXNnYCU')]/..")).getTagName());*/
        waitForVisibility(Driver.getDriver().findElement(By.xpath("//li[text()= '" + taskType + "']")),
                10);
        jsClick(Driver.getDriver().findElement(By.xpath("//li[text()= '" + taskType + "']")));/*.click();*/

    }


    /*This method to select multiple task type
        Author -Paramita - CP-1909
    */
    @And("I select multiple associate task type")
    public void associateMultipleTaskType() {
        waitForVisibility(businessUnitPage.taskTypeDropDown, 10);
        for (int i = 0; i <= 1; i++) {
            click(businessUnitPage.taskTypeDropDown);
            waitFor(1);
            businessUnitPage.taskTypeDropDownValue.get(i).click();
        }

    }

    /*This method tode-associate single task type
       Author -Paramita - CP-1909
   */
    @And("I de-associate a single task type")
    public void deassociateSingleTaskType() {
        waitFor(1);
        click(businessUnitPage.frstTask);
        waitFor(2);
    }


    /*This method tode-associate all selected task type
        Author -Paramita - CP-1909
    */
    @And("de-associate all selected Task type")
    public void deassociateAllTaskType() {
        click(businessUnitPage.taskTypeDeleteAllDropdownValue.get(0));
    }

    /*This method to select a task type of active and future status from task type screen
           Author -Paramita - CP-1909
     */
    @And("I select a task type of active and future status")
    public void i_select_a_task_type_of_active_and_future_status() {

        for (int i = 0; i < tmTaskTypePage.tasktypeRecords.size(); i++) {
            scrollDown();
            if (tmTaskTypePage.tasktypeStatus.get(i).getText().equals("ACTIVE") ||
                    tmTaskTypePage.tasktypeStatus.get(i).getText().equals("FUTURE")) {
                actualTaskTypeToCompare.get().add(tmTaskTypePage.tasktypeName.get(i).getText());

            }
        }

    }


    /*This method to click on Associate Task Type dropdown
               Author -Paramita - CP-1909
     */
    @And("I click on Associate Task Type dropdown")
    public void i_click_on_associate_task_type_dropdown() {
        waitForVisibility(businessUnitPage.taskTypeDropDown, 10);
        click(businessUnitPage.taskTypeDropDown);
    }


    /*This method to see the dropdown values are displayed based on active and future task type values
              Author -Paramita - CP-1909
    */
    @Then("I should see the dropdown values are displayed based on active and future task type values")
    public void dropdownValuesDisplayedBasedOnActiveFutureTaskType() {
        for (WebElement we : businessUnitPage.taskTypeDropDownValue) {
            ExpectedTaskTypeName.get().add(we.getText());
        }
        Assert.assertTrue(actualTaskTypeToCompare.get().equals(ExpectedTaskTypeName.get()), "Task type not matched");
    }


    @And("I associate randomly one more TaskType")
    public void iAssociateRandomlyOneMoreTaskType() {
        waitFor(1);
        selectRandomDropDownOption(businessUnitPage.selectTaskType);
    }
}
