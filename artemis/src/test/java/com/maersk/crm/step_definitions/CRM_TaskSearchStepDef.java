package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.pages.tm.TMAddNewUserPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.*;

import static com.maersk.crm.step_definitions.LoginStepDef.projectName1;
import static org.testng.Assert.*;


public class CRM_TaskSearchStepDef extends CRMUtilities implements ApiBase {

    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMCreateGeneralTaskPage createTask = new CRMCreateGeneralTaskPage();
    CRMTaskServiceRequiestTabPage crmTaskServiceTab = new CRMTaskServiceRequiestTabPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    TMAddNewUserPage tmAddNewUserPage = new TMAddNewUserPage();
    CRMBulkUpdatePage bulkUpdatePage = new CRMBulkUpdatePage();


    public static final ThreadLocal<String> searchName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<List<String>> taskIDs = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<Boolean> isInitiateButtonClicked = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<HashMap<String, String>> searchParameter = ThreadLocal.withInitial(HashMap::new);
    public final ThreadLocal<JSONObject> payloadObject = ThreadLocal.withInitial(JSONObject::new);
    public static final ThreadLocal<String> taskIdLocalVar = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<List<String>> uncheckedTaskId = ThreadLocal.withInitial(ArrayList::new);
    Actions actions = new Actions(Driver.getDriver());
    public static final ThreadLocal<List<String>>checkedTaskId = ThreadLocal.withInitial(ArrayList::new);
//    CRMViewTaskStepDef viewTaskStepDef = new CRMViewTaskStepDef();
    public final ThreadLocal<String> pageNameCache = ThreadLocal.withInitial(String::new);
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    public static final ThreadLocal<ArrayList<String>> displayedInOrder = ThreadLocal.withInitial(ArrayList::new);
    /*This method to populate data to task search page fields to search the task
    Author -Vidya
     */

    @And("I populate required fields to do task search {string},{string},{string},{string},{string},{string},{string},{string}," +
            "{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void populateDataToSearchTasks(String taskId, String taskType,String srType, String status, String statusDate,
                                          String Priority, String dueDate, String searchCase, String caseId,
                                          String searchConsumer, String consumerId, String advanceSearch,
                                          String consumerFN, String consumerLN, String source, String assignee,
                                          String createdBy, String createdOn, String contactReason) {
        waitForVisibility(taskSearchPage.taskId, 10);
        if (!taskId.isEmpty() && !taskId.equalsIgnoreCase("getFromUI")) {
            taskSearchPage.taskId.sendKeys(taskId);
        } else if (!taskId.isEmpty() && taskId.equalsIgnoreCase("getFromUI")) {
            taskSearchPage.taskId.sendKeys(CRM_TaskManagementStepDef.taskId.get());
        }
        if (!taskType.isEmpty()) {
            selectMultiSelectDropDown(taskSearchPage.taskTypeDropdown, taskType);
        }
        if (!srType.isEmpty()) {
            selectMultiSelectDropDown(taskSearchPage.srTypeDropDown, srType);
        }
        if (!status.isEmpty()) {
            waitFor(2);
            waitForVisibility(taskSearchPage.taskStatusDropdown, 5);
            selectOptionFromMultiSelectDropDown(taskSearchPage.taskStatusDropdown, status);
        }
        if (!statusDate.isEmpty()) {
            waitFor(1);
            if (statusDate.equals("today")) {
                clearAndFillText(taskSearchPage.statusDate, createTask.projectDateAtHeader.getText());
            }
            if (statusDate.contains("+")) {
                taskSearchPage.statusDate.sendKeys(getGreaterDate(Integer.parseInt(statusDate.replace("+", ""))));
            }
            if (statusDate.contains("-")) {
                taskSearchPage.statusDate.sendKeys(getPriorDate(Integer.parseInt(statusDate.replace("-", ""))));
            }
        }
        if (!Priority.isEmpty()) {
            selectOptionFromMultiSelectDropDown(taskSearchPage.priorityDropdown, Priority);
        }
        if (!dueDate.isEmpty()) {
            //taskSearchPage.dueDate.sendKeys(dueDate);
            waitFor(1);
            if (dueDate.equals("today")) {
                clearAndFillText(taskSearchPage.dueDate, createTask.projectDateAtHeader.getText());
            }
            if (dueDate.contains("+")) {
                taskSearchPage.dueDate.sendKeys(getGreaterDate(Integer.parseInt(dueDate.replace("+", ""))));
            }
            if (dueDate.contains("-")) {
                taskSearchPage.dueDate.sendKeys(getPriorDate(Integer.parseInt(dueDate.replace("-", ""))));
            }
            if (dueDate.contains("/")) {
                taskSearchPage.dueDate.sendKeys(dueDate);
            }
        }
        if (!searchCase.isEmpty()) {
            selectDropDown(taskSearchPage.searchCaseDropdown, searchCase);
        }
        if (!caseId.isEmpty()) {
            taskSearchPage.caseId.sendKeys(caseId);
        }
        if (!searchConsumer.isEmpty()) {
            selectDropDown(taskSearchPage.searchConsumerDropdown, searchConsumer);
        }
        if (!consumerId.isEmpty()) {
            taskSearchPage.consumerId.sendKeys(consumerId);
        }
        if (advanceSearch.equals("true")) {
            waitForVisibility(taskSearchPage.advanceSearch, 10);
            click(taskSearchPage.advanceSearch);
            waitFor(2);
            if (!consumerFN.isEmpty()) {
                taskSearchPage.consumerFirstName.sendKeys(consumerFN);
            }
            if (!consumerLN.isEmpty()) {
                taskSearchPage.consumerLastName.sendKeys(consumerLN);
            }
            if (!source.isEmpty()) {
                waitFor(1);
                selectDropDown(taskSearchPage.sourceDropdown, source);
            }
            if (!assignee.isEmpty()) {
                waitFor(3);
                autoCompleteSingleSelectDropDown(taskSearchPage.assignee, assignee);
            }
            if (!createdBy.isEmpty()) {
                autoCompleteSingleSelectDropDown(taskSearchPage.createdBy, createdBy);
            }
            if (!createdOn.isEmpty()) {
                if (createdOn.equals("today")) {
                    taskSearchPage.createdOn.sendKeys(createTask.projectDateAtHeader.getText());
                }
                if (createdOn.contains("+")) {
                    taskSearchPage.createdOn.sendKeys(getGreaterDate(Integer.parseInt(createdOn.replace("+", ""))));
                }
                if (createdOn.contains("-")) {
                    taskSearchPage.createdOn.sendKeys(getPriorDate(Integer.parseInt(createdOn.replace("-", ""))));
                }
            }
            if (!contactReason.isEmpty()) {
                waitFor(1);
                selectDropDown(taskSearchPage.contactReason, contactReason);
            }
        }
    }

    /*This method to click on search button on task search page
    Author -Vidya
     */

    @And("I click on search button on task search page")
    public void clickOnSearchButton() {
        waitFor(5);
        click(taskSearchPage.searchBtn);
        waitFor(5);
        // this breaks existing functionality
     //   if (isElementDisplayed(dashboard.warningPopupContinueBtn))
     //       dashboard.warningPopupContinueBtn.click();
        taskIDs.set(getTaskIds());
    }

    /*This method to verify save task search section is displayed when we search any task
    Author -Vidya
     */

    @Then("I verify save task search section is displayed")
    public void verifySaveTaskSearchSectionIsDisplayed() {
        scrollUpUsingActions(1);
        waitForVisibility(taskSearchPage.saveTaskSearchLabel, 10);
        assertTrue(taskSearchPage.saveTaskSearchLabel.isDisplayed(), "Save Task Search section is not displayed");
        assertTrue(taskSearchPage.searchName.isDisplayed(), "Save Task Search section is not displayed");
    }

    /*This method verifies that Search Name accepts only alphanumeric & spaces
    Input paramter - number of characters
    Author -Vidya
     */

    @Then("I verify search name accepts {int} alphanumeric spaces are allowed")
    public void searchNameFieldValidation(Integer int1) {
        String text = "SavedTaskSearchName Test verify 50 Character Allow1fghj";
        clearAndFillText(taskSearchPage.searchName, text);
        assertEquals((int) int1, taskSearchPage.searchName.getAttribute("value").length(), "Search name field length is not 50 characters");
        searchName.set("SavedTaskSearchName Test Verify 50 Character A" + getRandomString(4));
        clearAndFillText(taskSearchPage.searchName, searchName.get());
        assertEquals((int) int1, taskSearchPage.searchName.getAttribute("value").length(), "Search name field length is not 50 characters");
    }

    /*This method to provide value to search name filed present on save task search section
    Author -Vidya
     */

    @And("I enter value to search name field")
    public void enterValueInSearchNameField() {
        searchName.set("Srch" + getRandomString(9));
        taskSearchPage.searchName.sendKeys(searchName.get());
    }

    /*This method to click on save button present on save task search section
    Author -Vidya
     */

    @And("I click on save button on task search page")
    public void clickOnSaveButton() {
        click(taskSearchPage.saveBtn);
    }

    /*This method to verify success message and text are displayed when we save any task search
    Author -Vidya
     */

    @Then("I verify success message and text are displayed task search page")
    public void verifySuccessMsgIsDisplayed() {
        waitForVisibility(taskSearchPage.successMsg, 10);
        assertTrue(taskSearchPage.successMsg.isDisplayed(), "Task search is not saved");
        assertTrue(taskSearchPage.successMsgTxt.isDisplayed(), "Success Message text is not displayed");
    }

    /*This method to click on cancel button present on save task search section
    Author -Vidya
     */

    @And("I click on cancel button on saved task search section")
    public void clickOnCancelButton() {
        click(taskSearchPage.saveTaskSearchCancelBtn);
        waitFor(1);
    }

    /*This method to verify warning message and text are displayed when we cancel any save task search
    Author -Vidya
     */

    @Then("I verify Warning message and text are displayed on task search page")
    public void verifyWarningMsgIsDisplayed() {
        waitForVisibility(taskSearchPage.warningMsg, 10);
        assertTrue(taskSearchPage.warningMsg.isDisplayed(), "Warning message is not displayed");
        assertTrue(taskSearchPage.warningMsgTxt.isDisplayed(), "Warning Message text is not displayed");
    }

    /*This method to click on cancel button present on warning message
    Author -Vidya
     */

    @And("I click on cancel button on warning message of task search page")
    public void clickOnCancelBtnOnWarningMsg() {
        jsClick(taskSearchPage.warningMsgCancelBtn);
    }

    /*This method to verify if Cancel is selected, Then the User will remain on the
    Task Search Result UI and the Task Search Name & Task Search Parameters Entered will not be cleared
    nor will it have been saved.
    Author -Vidya
     */

    @Then("Verify will I remain on the same page,Task Search Name & Task Search Parameters Entered will not be cleared")
    public void verifyCancelFunctionalityIsAsExpected() {
        waitForVisibility(taskSearchPage.saveTaskSearchLabel, 10);
        assertTrue(taskSearchPage.saveTaskSearchLabel.isDisplayed(), "Save Task Search section is not displayed");
        assertTrue(taskSearchPage.searchName.isDisplayed(), "Save Task Search section is not displayed");
        assertTrue(taskSearchPage.searchName.getAttribute("value").contains("Srch"));
        assertEquals(taskSearchPage.taskTypeDropdown.getText(), "General", "Task type isnt verified");
        assertEquals(taskSearchPage.priorityDropdown.getText(), "4", "Task priority isnt verified");
    }

    /*This method to click on Continue button present on warning message
    Author -Vidya
     */

    @And("I click on continue button on warning message of task search page")
    public void clickOnContinueBtnOnWarningMsg() {
        click(taskSearchPage.warningMsgContinueBtn);
    }

    /*This method to verify if Continue is selected, Then the User will remain on the
    Task Search Result UI and the Task Search will not be saved
    but the Task Search Parameters used to perform the Search will remain populated
    Author -Vidya
     */

    @Then("Verify will I remain on the same page,Task Search Name is cleared & Task Search Parameters Entered will not be cleared")
    public void verifyContinueFunctionalityIsAsExpected() {
        waitForVisibility(taskSearchPage.saveTaskSearchLabel, 10);
        assertTrue(taskSearchPage.saveTaskSearchLabel.isDisplayed(), "Save Task Search section is not displayed");
        assertTrue(taskSearchPage.searchName.isDisplayed(), "Save Task Search section is not displayed");
        assertTrue(taskSearchPage.searchName.getAttribute("value").isEmpty());
        assertEquals(taskSearchPage.taskTypeDropdown.getText(), "General");
        assertEquals(taskSearchPage.priorityDropdown.getText(), "4");
    }

    @Then("I verify save task search section is not displayed")
    public void verifySaveTaskSectionIsNotDisplayed() {
        assertFalse(isElementDisplayed(taskSearchPage.saveTaskSearchLabel));
        assertFalse(isElementDisplayed(taskSearchPage.searchName));
    }

    @And("I will select save task search value from Search by Saved Task Search drop down")
    public void selectAnyValueFromSearchBySavedTaskSearchDropDown() {
        waitForVisibility(taskSearchPage.taskSearchNameDropDown, 10);
        selectDropDown(taskSearchPage.taskSearchNameDropDown, searchName.get());
    }

    @Then("I verify Search Parameters will auto populated {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void iVerifySearchParametersWillAutoPopulated(String taskId, String taskType, String status, String statusDate,
                                                         String Priority, String dueDate, String searchCase, String caseId,
                                                         String searchConsumer, String consumerId, String advanceSearch,
                                                         String consumerFN, String consumerLN, String source, String assignee,
                                                         String createdBy, String createdOn) {
        waitForVisibility(taskSearchPage.taskStatusDropdown, 10);
        assertEquals(taskSearchPage.taskStatusDropdown.getText(), status, "Status is wrong");
        assertEquals(taskSearchPage.taskTypeDropdown.getText(), taskType, "Tak Type is wrong");
        assertEquals(taskSearchPage.priorityDropdown.getText(), Priority, "Priority is wrong");
        assertEquals(taskSearchPage.taskId.getText(), taskId, "Task Id is wrong");
        assertEquals(taskSearchPage.statusDate.getText(), statusDate, "Start date is wrong");
        assertEquals(taskSearchPage.dueDate.getText(), dueDate, "Due date is wrong");
        if (searchCase.equals("")) {
            assertEquals(taskSearchPage.searchCaseDropdown.getText(), "Internal", "Search case is wrong");
        } else {
            assertEquals(taskSearchPage.searchCaseDropdown.getText(), searchCase, "Search case is wrong");
        }
        assertEquals(taskSearchPage.caseId.getText(), caseId, "Case id is wrong");
        if (searchConsumer.equals("")) {
            assertEquals(taskSearchPage.searchConsumerDropdown.getText(), "Internal", "Search consumer is wrong");
        } else {
            assertEquals(taskSearchPage.searchConsumerDropdown.getText(), searchConsumer, "Search consumer is wrong");
        }
        assertEquals(taskSearchPage.consumerId.getText(), consumerId, "Consumer id is wrong");
        if (advanceSearch.equals("true")) {
            assertEquals(taskSearchPage.consumerFirstName.getText(), consumerFN, "Consumer FN is wrong");
            assertEquals(taskSearchPage.consumerLastName.getText(), consumerLN, " Consumer LN is wrong");
            assertEquals(taskSearchPage.sourceDropdown.getText(), source, "Source is wrong");
            assertEquals(taskSearchPage.assignee.getText(), assignee, "Assignee is wrong");
            assertEquals(taskSearchPage.createdBy.getText(), createdBy, "Created by is wrong");
            assertEquals(taskSearchPage.createdOn.getText(), createdOn, "Created on is wrong");
        }
    }

    @And("I update the Search parameter {string},{string},{string}")
    public void updateSearchParameterAndPerformSearchAction(String updateType, String updateStatus, String updatePriority) {
        selectFromMultiSelectDropDownForWithEscapedKey(taskSearchPage.taskTypeDropdown,updateType);
        selectFromMultiSelectDropDownForWithEscapedKey(taskSearchPage.priorityDropdown,updatePriority);
        selectFromMultiSelectDropDownForWithEscapedKey(taskSearchPage.taskStatusDropdown,updateStatus);
    }

    @Then("I will verify newly created saved task search is updated with new search parameter {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void verifyUpadetdSearchParameterTaskSearchIsCreated(String taskId, String taskType, String status, String statusDate,
                                                                String Priority, String dueDate, String searchCase, String caseId,
                                                                String searchConsumer, String consumerId, String advanceSearch,
                                                                String consumerFN, String consumerLN, String source, String assignee,
                                                                String createdBy, String createdOn, String updateType,
                                                                String updateStatus, String updatePriority) {
        waitForVisibility(taskSearchPage.taskStatusDropdown, 10);
        assertTrue(taskSearchPage.taskStatusDropdown.getText().contains(status), "Status is wrong");
        assertTrue(taskSearchPage.taskTypeDropdown.getText().contains(taskType), "Tak Type is wrong");
        assertTrue(taskSearchPage.priorityDropdown.getText().contains(Priority), "Priority is wrong");
        assertEquals(taskSearchPage.taskId.getAttribute("value"), taskId, "Task Id is wrong");
        if(statusDate.equals("today")) {
            assertEquals(taskSearchPage.statusDate.getAttribute("value"), getCurrentDate(), "Status date is wrong");
        }
        if(dueDate.equals("today")) {
            assertEquals(taskSearchPage.dueDate.getAttribute("value"), getCurrentDate(), "Due date is wrong");
        }
        if (searchCase.equals("")) {
            assertEquals(taskSearchPage.searchCaseDropdown.getText(), "Internal", "Search case is wrong");
        } else {
            assertEquals(taskSearchPage.searchCaseDropdown.getText(), searchCase, "search case is wrong");
        }
        assertEquals(taskSearchPage.caseId.getAttribute("value"), caseId, "Case id is wrong");
        if (searchConsumer.equals("")) {
            assertEquals(taskSearchPage.searchConsumerDropdown.getText(), "Internal", "Search consumer is wrong");
        } else {
            assertEquals(taskSearchPage.searchConsumerDropdown.getText(), searchConsumer, "search consumer is wrong");
        }
        assertEquals(taskSearchPage.consumerId.getAttribute("value"), consumerId, "Consumer id is wrong");
        if (advanceSearch.equals("true")) {
            taskSearchPage.advanceSearch.click();
            waitFor(2);
            assertEquals(taskSearchPage.consumerFirstName.getAttribute("value"), consumerFN, "Consumer FN is wrong");
            assertEquals(taskSearchPage.consumerLastName.getAttribute("value"), consumerLN, " Consumer LN is wrong");
            assertEquals(taskSearchPage.sourceDropdown.getText(), source, "Source is wrong");
            assertEquals(taskSearchPage.assignee.getAttribute("value"), assignee, "Assignee is wrong");
            assertEquals(taskSearchPage.createdBy.getAttribute("value"), createdBy, "Created by is wrong");
            if(createdOn.equals("today")) {
                assertEquals(taskSearchPage.createdOn.getAttribute("value"), getCurrentDate(), "Created on is wrong");
            }
        }
        assertTrue(taskSearchPage.taskTypeDropdown.getText().contains(updateType), "Updated Task Type is wrong");
        assertTrue(taskSearchPage.taskStatusDropdown.getText().contains(updateStatus), "Updated status is wrong");
        assertTrue(taskSearchPage.priorityDropdown.getText().contains(updatePriority), "updated priority is wrong");
    }

    /*This method to click on search button on task search page
    Author -Vidya
     */
    @And("In search result click on task id to navigate to view page")
    public void clickOnTaskIdInSearchResult() {
        waitFor(5);
        scrollToElement(taskSearchPage.taskIDs.get(0));
        if(!isElementDisplayed(taskSearchPage.mainCheckbox) && !isElementDisplayed(bulkUpdatePage.bulkUpdateBtn)) {
            CRM_TaskManagementStepDef.taskId.set(taskSearchPage.taskIDsOnSearchPage.get(0).getText());
            CRM_TaskManagementStepDef.srID.set(taskSearchPage.taskIDsOnSearchPage.get(0).getText());
            taskIdLocalVar.set(taskSearchPage.taskId.getText());
            waitFor(1);
            jsClick(taskSearchPage.taskIDsOnSearchPage.get(0));
          //  click(taskSearchPage.taskIDsOnSearchPage.get(0));
            waitFor(3);
        }else{
            CRM_TaskManagementStepDef.taskId.set(taskSearchPage.taskIDs.get(0).getText());
            CRM_TaskManagementStepDef.srID.set(taskSearchPage.taskIDs.get(0).getText());
            taskIdLocalVar.set(taskSearchPage.taskId.getText());
            waitFor(1);
            jsClick(taskSearchPage.taskIDs.get(0));
         //   click(taskSearchPage.taskIDs.get(0));
            waitFor(3);
        }
    }

    /*This method to click on search button on task search page
    Author -Vidya
     */
    @And("I verify user is navigate back to task search page")
    public void verifyUserIsNavigateTOTaskSerachPage() {
        waitForVisibility(taskSearchPage.taskSearchHeader, 10);
        assertTrue(taskSearchPage.taskSearchHeader.isDisplayed());
        assertTrue(taskSearchPage.taskTypeDropdown.isDisplayed());
    }

    @When("I select escalate Flag checkbox")
    public void selectEscalateCheckbox() {
        waitFor(1);
        taskSearchPage.escalated.click();
    }

    @Then("I verify results {string} in the results table")
    public void i_verify_result_task_status_in_the_results_table(String taskStatus) {
        assertEquals(taskSearchPage.taskStatuses.get(0).getText(), taskStatus, "Task Status is wrong");
    }

    @And("I click task id to get the results in descending order in Task Search")
    public void click_task_id_for_descending_order() {
        waitFor(5);
        scrollDownUsingActions(3);
        jsClick(taskSearchPage.taskIDColumnHeader);
        if (isElementDisplayed(dashboard.warningPopupContinueBtn))
            dashboard.warningPopupContinueBtn.click();
        waitFor(2);
        jsClick(taskSearchPage.taskIDColumnHeader);
        if (isElementDisplayed(dashboard.warningPopupContinueBtn))
            dashboard.warningPopupContinueBtn.click();
        waitFor(2);
        ArrayList<String> displayedInOrder = new ArrayList();
        ArrayList<String> toSort = new ArrayList();
        waitFor(2);
        if(!isElementDisplayed(taskSearchPage.mainCheckbox) && !isElementDisplayed(bulkUpdatePage.bulkUpdateBtn)) {
            for (WebElement row : taskSearchPage.taskListRows) {
                waitFor(2);
                displayedInOrder.add(row.findElement(By.xpath("//td[2]")).getText());
                toSort.add(row.findElement(By.xpath("//td[2]")).getText());
            }
        }else {
            for (WebElement row : taskSearchPage.taskListRows) {
                waitFor(2);
                displayedInOrder.add(row.findElement(By.xpath("//td[3]")).getText());
                toSort.add(row.findElement(By.xpath("//td[3]")).getText());
            }
        }
        Collections.sort(toSort, Collections.reverseOrder());
        assertEquals(displayedInOrder, toSort);
    }

    @Then("I verify task is not highlighted in red for task status {string} in Task Search screen")
    public void verifyTaskIsNotHighlightedInRedColor(String taskStatusExpected) {
        waitFor(1);
        int size = 1;
        size = getSizeOfPagination(size);
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.taskListRows.size(); j++) {
                if (taskSearchPage.taskStatuses.get(j).getText().equals(taskStatusExpected) && taskSearchPage.taskDueIns.get(j).getText().contains("-")) {
                    assertNotEquals(getColorCode(taskSearchPage.taskListRows.get(j)), "#ffebee", "Task highlighted in red color");
                    waitFor(2);
                }
                break;
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @When("I will search with taskId")
    public void searchWithTaskId() {
        waitFor(2);
        waitForVisibility(taskSearchPage.taskId, 12);
        if(CRM_TaskManagementStepDef.taskId.get().isEmpty())    {
            taskSearchPage.taskId.sendKeys(String.valueOf(APITaskManagementController.taskId.get()));
            CRM_TaskManagementStepDef.srID.set(String.valueOf(APITaskManagementController.taskId.get()));
            System.out.println("Provided taskid: "+String.valueOf(APITaskManagementController.taskId.get()));
        }else {
            taskSearchPage.taskId.sendKeys(CRM_TaskManagementStepDef.taskId.get());
            CRM_TaskManagementStepDef.srID.set(CRM_TaskManagementStepDef.taskId.get());
            System.out.println("Provided taskid: "+CRM_TaskManagementStepDef.taskId.get());
        }
        waitFor(2);
        clickOnSearchButton();

    }

    @Then("I verify tasks are have initiate button since searched parameter has permission to work")
    public void verifyTaskDoesHaveInitiateButton() {
        waitFor(2);
        boolean recordFound = false;
        int size = 1;
        size = getSizeOfPagination(size);
        if(!isElementDisplayed(taskSearchPage.mainCheckbox) && !isElementDisplayed(bulkUpdatePage.bulkUpdateBtn)) {
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < taskSearchPage.taskIDsOnSearchPage.size(); j++) {
                    assertFalse(taskSearchPage.taskGetInsuredCaseID.get(j).getText().isEmpty(),
                            "Initiate button is not displayed");
                    recordFound = true;
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }else{
                for (int i = 1; i <= size; i++) {
                    for (int j = 0; j < taskSearchPage.taskIDs.size(); j++) {
                        assertFalse(taskSearchPage.initiateTasks.get(j).getText().isEmpty(),
                                "Initiate button is not displayed");
                        recordFound = true;
                    }
                    if (i != size) {
                        click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                        waitFor(1);
                    }
                }
            }
        assertTrue(recordFound, "No Record Found message displayed");
    }

    @Then("I verify tasks are does not have initiate button since searched parameter does not have permission to work")
    public void verifyTaskDoesNotHaveInitiateButton() {
        waitFor(3);
        boolean recordFound = false;
        int size = 1;
        size = getSizeOfPagination(size);
        if(!isElementDisplayed(taskSearchPage.mainCheckbox) && !isElementDisplayed(bulkUpdatePage.bulkUpdateBtn)) {
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < taskSearchPage.taskIDsOnSearchPage.size(); j++) {
                    if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE")) {
                        assertTrue(taskSearchPage.initiateTasks.get(j).getText().isEmpty(),
                                "Initiate button is displayed");
                    } else
                        assertTrue(taskSearchPage.taskGetInsuredCaseID.get(j).getText().isEmpty(),
                                "Initiate button is displayed");
                    recordFound = true;
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }else {
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < taskSearchPage.taskIDs.size(); j++) {
                    if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE")) {
                        System.out.println("button......" + taskSearchPage.initiateTasksForNJ.get(j).getText());
                        assertTrue(taskSearchPage.initiateTasksForNJ.get(j).getText().isEmpty(),
                                "Initiate button is displayed");
                    } else
                        assertTrue(taskSearchPage.initiateTasks.get(j).getText().isEmpty(),
                                "Initiate button is displayed");
                    recordFound = true;
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
        assertTrue(recordFound, "No Record Found message displayed");
    }

    @Then("In result I verify task is saved with status {string}")
    public void verifyStatusOfTaskInResult(String status) {
        waitFor(1);
        waitForVisibility(taskSearchPage.taskStatuses.get(0), 10);
        System.out.println("project name: "+projectName1);
        if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE")) {
            assertEquals(taskSearchPage.taskStatuses.get(0).getText(), status, "Status is not " + status);
        } else {
            assertEquals(taskSearchPage.taskStatuses.get(0).getText(), status, "Status is not " + status);
        }
    }

    /*This method to newly created task by clicking on TaskID column header on Task Search Page
    Author -Vidya */
    @And("I navigate to latest task")
    public void i_navigate_to_newly_created_task() {
        boolean sorted = false;
        for (int i = 0; i <= 3; i++) {
            //waitForVisibility(workQueue.taskIDColumnHeader, 15);
            waitFor(10);
            scrollUpUsingActions(3);
            jsClick(taskSearchPage.taskIDColumnHeader);
            waitFor(2);
            List<Integer> displayedInOrder = new ArrayList<>();
            List<Integer> toSort = new ArrayList<>();
            for (int j = 0; j < taskSearchPage.taskIDs.size(); j++) {
                int taskID = Integer.parseInt(taskSearchPage.taskIDs.get(j).getText());
                displayedInOrder.add(taskID);
                toSort.add(taskID);
            }
            Collections.sort(toSort, Collections.reverseOrder());
            if (displayedInOrder.equals(toSort)) {
                sorted = true;
                break;
            }
        }
    }

    @When("I click on initiate button in task search page")
    public void clickInitiateButton() {waitFor(3);
        CRM_TaskManagementStepDef.taskId.set(taskSearchPage.taskIDs.get(0).getText());
        scrollToElement(taskSearchPage.taskIDColumnHeader);
        waitFor(3);
        taskIDs.set(getTaskIds());
        isInitiateButtonClicked.set(true);
        click(Driver.getDriver().findElement(By.xpath("//button[text()='INITIATE']")));
        waitFor(10);
        CRM_GeneralTaskStepDef.taskValues.get().put("assignee", contactRecord.headerUsername.getText());
    }

    @Then("I verify tasks have {string} on expanded view")
    public void i_verify_tasks_have_on_expanded_view(String assignee) {
        waitFor(3);
        boolean recordFound = false;
        int size = 1;
        if (isElementDisplayed(taskSearchPage.lnkArrowBack)) {
            click(taskSearchPage.lnkArrowBack);
            waitFor(1);
        }
        if (isElementDisplayed(taskSearchPage.lnkArrowForward)) {
            click(taskSearchPage.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskSearchPage.lnkPageNations.get(taskSearchPage.lnkPageNations.size() - 1).getText());
            click(taskSearchPage.lnkArrowBack);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.taskIDs.size(); j++) {
                jsClick(taskSearchPage.expandBtns.get(j));
                waitFor(1);
                assertEquals(taskSearchPage.assigneeExpandedView.get(j).getText().trim(),assignee.trim(), "Assignee has different value than userId");
                taskSearchPage.arrowDownKey.click();
                waitFor(1);
                recordFound = true;
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "No Record Found message displayed");
    }

    /*This method to verify task search parameters are cleared
    Author -Vidya*/
    @Then("I verify task search parameter are cleared")
    public void verifyTaskSearchParameterAreCleared() {
        scrollUpUsingActions(1);
        assertFalse(isElementDisplayed(taskSearchPage.saveTaskSearchLabel),
                "Save Task Search section is displayed");
        assertFalse(isElementDisplayed(taskSearchPage.searchName),
                "Save Task Search section is displayed");
        assertFalse(isElementDisplayed(taskSearchPage.searchResultsHeader),
                "Task Search result is displayed");
        assertTrue(taskSearchPage.taskId.getAttribute("value").isEmpty(),
                "Task Id field is not empty");
        assertTrue(taskSearchPage.taskTypeDropdown.getText().isEmpty(), "Task Type field is not empty");
        assertTrue(taskSearchPage.taskStatusDropdown.getText().isEmpty(), "Status field is not empty");
        assertTrue(taskSearchPage.statusDate.getAttribute("value").isEmpty(),
                "Status Date field is not empty");
        assertTrue(taskSearchPage.priorityDropdown.getText().isEmpty(), "Priority field is not empty");
        assertTrue(taskSearchPage.dueDate.getAttribute("value").isEmpty(),
                "Due Date field is not empty");
        assertTrue(taskSearchPage.searchCaseDropdown.getText().equals("Internal") ||
                        taskSearchPage.searchCaseDropdown.getText().equals("GetInsured"),
                "Search Case field is not empty");
        assertTrue(taskSearchPage.caseId.getAttribute("value").isEmpty(),
                "Case Id field is not empty");
        assertTrue(taskSearchPage.searchConsumerDropdown.getText().equals("Internal") ||
                        taskSearchPage.searchConsumerDropdown.getText().equals("GetInsured"),
                "Search Consumer field is not empty");
        assertTrue(taskSearchPage.consumerId.getAttribute("value").isEmpty(),
                "Consumer Id field is not empty");
        click(taskSearchPage.advanceSearch);
        waitFor(2);
        assertTrue(taskSearchPage.consumerFirstName.getAttribute("value").isEmpty(),
                "Consumer FN field is not empty");
        assertTrue(taskSearchPage.consumerLastName.getAttribute("value").isEmpty(),
                "Consumer LN field is not empty");
        assertTrue(taskSearchPage.sourceDropdown.getText().isEmpty(), "Source field is not empty");
        assertTrue(taskSearchPage.assignee.getAttribute("value").isEmpty(),
                "Assignee field is not empty");
        assertTrue(taskSearchPage.createdBy.getAttribute("value").isEmpty(),
                "CreatedBy field is not empty");
        assertTrue(taskSearchPage.createdOn.getAttribute("value").isEmpty(),
                "CreatedOn field is not empty");
    }

    /*This method to click on cancel button present on task search page
    Author -Vidya*/
    @And("I click on cancel button on task search page")
    public void clickOnCancelButtonOnTaskSearchPage() {
        scrollToElement(taskSearchPage.cancelBtn);
        waitFor(5);
        jsClick(taskSearchPage.cancelBtn);
      //  click(taskSearchPage.cancelBtn);
    }

    /*This method to click on cancel button present on task search page
    Author -Vidya*/
    @And("I verify task type drop down values are listed in alphabetical order")
    public void verifyTaskTypeDropDownValuesAreListedInAlphabeticalOrder() {
        waitForVisibility(taskSearchPage.taskTypeDropdown, 10);
        taskSearchPage.taskTypeDropdown.click();
        ArrayList<String> displayedInOrder = new ArrayList<>();
        for (int i = 1; i < taskSearchPage.taskTypeDDVlu.size(); i++) {
            displayedInOrder.add(taskSearchPage.taskTypeDDVlu.get(i).getText().toLowerCase().trim());
        }
        ArrayList<String> copy = new ArrayList(displayedInOrder);
        Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
        assertEquals(displayedInOrder, copy,
                "Task Types are not Active or Task types are not listed in alphabetical order");
    }

    @When("I will search for the task created for SR")
    public void searchWithLatestTaskID() {
        waitForVisibility(taskSearchPage.taskId, 500);
        taskSearchPage.taskId.sendKeys((Integer.parseInt(CRM_TaskManagementStepDef.srID.get()) + 1) + "");
        clickOnSearchButton();
    }

    @Then("I verify search case and case id fields are not displayed in search parameter")
    public void verifySearchCaseAndCaseIDFieldsAreNotDisplayedInSearchParameter() {
        assertFalse(isElementDisplayed(taskSearchPage.searchCaseDropdown), "Search Case field is displayed");
        assertFalse(isElementDisplayed(taskSearchPage.caseId), "Case Id field is displayed");
    }

    @And("I enter already created saved task search name")
    public void enterAlreadyExitSaveTaskSearchName() {
        taskSearchPage.searchName.sendKeys(searchName.get());
        clickOnSaveButton();
    }

    @Then("Verify the system displays the following {string}")
    public void verifyErrorMessage(String errorMsg) {
        waitForVisibility(taskSearchPage.errorMsgTxt, 10);
        assertEquals(taskSearchPage.errorMsgTxt.getText(), errorMsg,
                "System is allowing us to create saved task search with already exist name");
    }

    @Then("I verify task search results are displayed")
    public void verifyTaskSearchResultsAreDisplayed() {
        waitForVisibility(taskSearchPage.taskSearchResultHeader, 10);
        assertTrue(isElementDisplayed(taskSearchPage.taskSearchResultHeader), "Search Result is not displayed");
        waitForVisibility(taskSearchPage.taskIDs.get(0), 10);
        assertTrue(isElementDisplayed(taskSearchPage.taskIDs.get(0)), "Search Result is not displayed");
    }

    @Then("I will verify task search page retains search criteria {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void verifyUpdatedSearchParameterTaskSearchIsCreated(String taskId, String taskType,String srType, String status, String statusDate,
                                                                String Priority, String dueDate, String searchCase, String caseId,
                                                                String searchConsumer, String consumerId, String advanceSearch,
                                                                String consumerFN, String consumerLN, String source, String assignee,
                                                                String createdBy, String createdOn) {
        waitFor(3);
        if (isElementDisplayed(dashboard.warningPopupContinueBtn))
            dashboard.warningPopupContinueBtn.click();
        waitForVisibility(taskSearchPage.taskStatusDropdown, 10);
        assertTrue(taskSearchPage.taskStatusDropdown.getText().contains(status), "Status is wrong");
        assertTrue(taskSearchPage.taskTypeDropdown.getText().contains(taskType), "Task Type is wrong");
        assertTrue(taskSearchPage.srTypeDropDown.getText().contains(srType), "SR Type is wrong");
        assertTrue(taskSearchPage.priorityDropdown.getText().contains(Priority), "Priority is wrong");
        assertEquals(taskSearchPage.taskId.getText(), taskId, "Task Id is wrong");
        assertEquals(taskSearchPage.statusDate.getText(), statusDate, "Start date is wrong");
        assertEquals(taskSearchPage.dueDate.getText(), dueDate, "Due date is wrong");

        // this step has to verify search parameters, not search results
        //if search results need to be verified, we need to create seperate step for it
     //   assertEquals(taskIDs, getTaskIds(), "Results are not the same");

        if (!LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA")) {
            if (searchCase.equals("")) {
                if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE"))
                    assertEquals(taskSearchPage.searchCaseDropdown.getText(), "GetInsured", "Search case is wrong");
                else
                    assertEquals(taskSearchPage.searchCaseDropdown.getText(), "Internal", "Search case is wrong");
            } else
                assertEquals(taskSearchPage.searchCaseDropdown.getText(), searchCase, "Search case is wrong");
            assertEquals(taskSearchPage.caseId.getText(), caseId, "Case id is wrong");
        }
        if (searchConsumer.equals("")) {
            if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE"))
                assertEquals(taskSearchPage.searchConsumerDropdown.getText(), "GetInsured", "Search consumer is wrong");
            else
                assertEquals(taskSearchPage.searchConsumerDropdown.getText(), "Internal", "Search consumer is wrong");
        } else
            assertEquals(taskSearchPage.searchConsumerDropdown.getText(), searchConsumer, "search consumer is wrong");
        assertEquals(taskSearchPage.consumerId.getText(), consumerId, "Consumer id is wrong");
        if (advanceSearch.equals("true")) {
            waitFor(1);
            hover(taskSearchPage.advanceSearch);
            taskSearchPage.advanceSearch.click();
            waitFor(1);
            assertEquals(taskSearchPage.consumerFirstName.getAttribute("value"), consumerFN, "Consumer FN is wrong");
            assertEquals(taskSearchPage.consumerLastName.getAttribute("value"), consumerLN, " Consumer LN is wrong");
            assertEquals(taskSearchPage.sourceDropdown.getText(), source, "Source is wrong");
            assertEquals(taskSearchPage.assignee.getAttribute("value"), assignee, "Assignee is wrong");
            assertEquals(taskSearchPage.createdBy.getAttribute("value"), createdBy, "Created by is wrong");
            assertEquals(taskSearchPage.createdOn.getAttribute("value"), createdOn, "Created on is wrong");
        }
    }

    @Then("I navigate to the {string} page from the left navigation menu")
    public void clickOnTaskManagementIcon(String pageName) {
        waitFor(3);
        hover(dashboard.sideNavMenu);
        waitFor(2);
        dashboard.expandTaskManagementSideTab.click();
        waitForVisibility(dashboard.myTaskWorkQueueSideTab, 10);

        if ("My Task".equals(pageName)) {
            jsClick(dashboard.myTaskWorkQueueSideTab);
            waitFor(2);
        }
    }

    private List<String> getTaskIds() {
        List<String> lst = new ArrayList<>();
        waitFor(5);
        if (isInitiateButtonClicked.get()) {
            for (int i = 0; i < taskSearchPage.taskStatuses.size() - 1; i++) {
                lst.add(taskSearchPage.taskIDs.get(i).getText());
            }
        } else {
            for (int i = 1; i < taskSearchPage.taskStatuses.size(); i++) {
                waitFor(1);
                lst.add(taskSearchPage.taskIDs.get(i).getText());
            }
        }
        return lst;
    }

    @And("I perform a Task Search by following fields")
    public void populateDataToTaskSearchParameter(Map<String, String> data) {
        waitForVisibility(taskSearchPage.taskId, 10);
        searchParameter.set(new HashMap<>());
        ArrayList<String> list = new ArrayList<String>(data.keySet());
        for (String key : list) {
            switch (key) {
                case "advanceSearch":
                    waitForVisibility(taskSearchPage.advanceSearch, 10);
                    click(taskSearchPage.advanceSearch);
                    waitFor(2);
                    break;
                case "dateOfContact":
                    if (data.get(key).equals("today"))
                        taskSearchPage.dateOfContact.sendKeys(getCurrentDate());
                    if (data.get(key).contains("+"))
                        taskSearchPage.dateOfContact.sendKeys(getFutureDate(Integer.parseInt(data.get(key).replace("+", ""))));
                    if (data.get(key).contains("-"))
                        taskSearchPage.dateOfContact.sendKeys(getPriorDate(Integer.parseInt(data.get(key).replace("-", ""))));
                    else
                        taskSearchPage.dateOfContact.sendKeys(data.get(key));
                    searchParameter.get().put("dateOfContact", taskSearchPage.dateOfContact.getAttribute("value"));
                    break;
                case "applicationId":
                    if (data.get(key).equals("getFromCreatePage"))
                        taskSearchPage.applicationId.sendKeys(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                    else
                        taskSearchPage.applicationId.sendKeys(data.get(key));
                    searchParameter.get().put("applicationId", taskSearchPage.applicationId.getAttribute("value"));
                    break;
                case "expedited":
                    waitFor(1);
                    taskSearchPage.expedited.click();
                    waitFor(1);
                    break;
                case "consumerId":
                    if (data.get(key).equals("getFromCreatePage"))
                        taskSearchPage.consumerId.sendKeys(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"));
                    else
                        taskSearchPage.consumerId.sendKeys(data.get(key));
                    searchParameter.get().put("consumerId", taskSearchPage.consumerId.getAttribute("value"));
                    break;
                case "caseId":
                    if (data.get(key).equals("getFromCreatePage"))
                        taskSearchPage.caseId.sendKeys(CRM_GeneralTaskStepDef.taskValues.get().get("caseID"));
                    else
                        taskSearchPage.caseId.sendKeys(data.get(key));
                    searchParameter.get().put("caseId", taskSearchPage.caseId.getAttribute("value"));
                    break;
                case "searchCase":
                    selectDropDown(taskSearchPage.searchCaseDropdown, data.get(key));
                    break;
                case "searchConsumer":
                    selectDropDown(taskSearchPage.searchConsumerDropdown, data.get(key));
                    break;
                case "assignmentStatus":
                    selectDropDown(taskSearchPage.assignmentStatusDD, data.get(key));
                    break;
                case "status":
                    taskSearchPage.taskStatusDropdown.click();
                    String[] expectedValues = data.get(key).split(",");
                    for (String value : expectedValues) {
                        click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-taskStatus']//ul/li[text()='" + value + "']")));
                    }
                    actions.moveToElement(taskSearchPage.taskId).click().perform();
                    break;
                case "taskTypes":
                    taskSearchPage.taskTypeDropdown.click();
                    expectedValues = data.get(key).split(",");
                    for (String expectedValue : expectedValues) {
                        click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-taskTypeId']//ul/li[text()='" + expectedValue + "']")));
                    }
                    actions.moveToElement(taskSearchPage.taskId).click().perform();
                    break;
                case "source":
                    selectDropDown(taskSearchPage.sourceDropdown, data.get(key));
                    break;
                case "srTypes":
                    taskSearchPage.srTypeDropDown.click();
                    expectedValues = data.get(key).split(",");
                    for (String expectedValue : expectedValues) {
                        click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-srTypeId']//ul/li[text()='" + expectedValue + "']")));
                    }
                    actions.moveToElement(taskSearchPage.taskId).click().perform();
                    break;
                case "assigneeTeam":
                    selectMultiSelectDropDown(taskSearchPage.assigneeTeam, data.get(key));
                    break;
                case "assigneeBusinessUnit":
                    selectMultiSelectDropDown(taskSearchPage.assigneeBusinessUnit, data.get(key));
                    break;
                case "createdOn":
                    if (data.get(key).equals("today"))
                        taskSearchPage.createdOn.sendKeys(getCurrentDate());
                    if (data.get(key).contains("+"))
                        taskSearchPage.createdOn.sendKeys(getFutureDate(Integer.parseInt(data.get(key).replace("+", ""))));
                    if (data.get(key).contains("-"))
                        taskSearchPage.createdOn.sendKeys(getPriorDate(Integer.parseInt(data.get(key).replace("-", ""))));
                    else
                        taskSearchPage.createdOn.sendKeys(data.get(key));
                    searchParameter.get().put("dateOfContact", taskSearchPage.createdOn.getAttribute("value"));
                    break;
                default:
                    fail("No matched Switch case for " + key);
            }
        }
    }

    @Then("I verify dateOfContact is renamed as {string}")
    public void verifyDateOfContactLabel(String label) {
        waitForVisibility(taskSearchPage.dateOfContactLabel, 10);
        assertEquals(taskSearchPage.dateOfContactLabel.getText(), label, "Label is not displayed as " + label);
    }

    @Then("I verify task search result has my work space date which we provide the search parameter")
    public void verifyMyWorkSpaceDateInSearchResult() {
        boolean recordFound = false;
        waitForVisibility(taskSearchPage.columnNames.get(2), 90);
        int size = 1;
        size = getSizeOfPagination(size);
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.taskDueIns.size(); j++) {
                waitFor(1);
                assertEquals(taskSearchPage.taskDueIns.get(j).getText().trim(), searchParameter.get().get("dateOfContact").trim(), "My work space date is mismatch");
                recordFound = true;
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "No Record Found message displayed");
    }

    @Then("I verify task search result has {string} column")
    public void verifyDateOfContactLabelInSearchResult(String label) {
        waitForVisibility(taskSearchPage.columnNames.get(8), 5);
        sa.get().assertThat(isElementDisplayed(getDynamicWebElement("//th[text()='columnName']","columnName",label))).as("Label is not displayed as " + label).isTrue();
    }

    @When("I expand the first row in search result list")
    public void expandAsearchRecordromTaskList() {
        waitFor(3);
        jsClick(taskSearchPage.expandSearchResultArrows.get(CRM_WorkQueueStepDef.position.get()));
    }

    @Then("I verify the task details are displayed in search result when expanded")
    public void verifysearchDetailsWhenExpanded() throws Exception {
        waitFor(3);
        assertTrue(taskSearchPage.createdByVluSearchResult.getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("created_by")) || taskSearchPage.createdByVluSearchResult.getText().equals("Task Mgmt Service"),
                "createdBy value is mismatch");
        if (taskSearchPage.createdByVluSearchResult.getText().equals("Task Mgmt Service")) {
            assertEquals(taskSearchPage.lblSourceSearchResult.getText(), "System", "Source value is mismatch");
        } else
            assertEquals(taskSearchPage.lblSourceSearchResult.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("source"),
                    "Source value is mismatch");
        assertEquals(taskSearchPage.lblCreatedOnSearchResult.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("created_on"),
                "CreatedOn is mismatch");
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("csrName") && taskSearchPage.taskTypes.get(0).getText().equals("Outbound Call")) {
            assertEquals(CRM_GeneralTaskStepDef.taskValues.get().get("csrName"), taskSearchPage.lblAssigneeSearchResult.getText());
        } else if (CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee") == null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty()) {
            assertEquals(taskSearchPage.lblAssigneeSearchResult.getText(), "-- --", "Assignee is mismatch");
        } else {
            assertEquals(taskSearchPage.lblAssigneeSearchResult.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("assignee"),
                    "Assignee is mismatch");
        }
        /*
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote")) {
            assertEquals(taskSearchPage.lblTaskNoteSearchResult.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"), "Task Note is mismatch");
        } else
            assertEquals(taskSearchPage.lblTaskNoteSearchResult.getText(), "-- --", "Task NOTE is mismatch");
         */
        if (CRM_GeneralTaskStepDef.taskValues.get().get("consumerName").equals("") || CRM_GeneralTaskStepDef.taskValues.get().get("consumerName") == null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("consumerName").isEmpty()) {
            assertEquals(taskSearchPage.lblNameSearchResult.getText(), "-- --", "Name is mismatch");
        } else
            assertEquals(taskSearchPage.lblNameSearchResult.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("consumerName"),
                    "Name value is mismatch");
        assertEquals(taskSearchPage.lblTaskInfoSearchresult.getText(), CRM_GeneralTaskStepDef.taskValues.get().getOrDefault("taskInfo", "-- --"), "Task Info is mismatch");
    }

    @Then("I verify task search result has application id which we passed in search parameter")
    public void verifyApplicationIDInSearchResult() {
        boolean recordFound = false;
        waitForVisibility(taskSearchPage.columnNames.get(2), 90);
        int size = 1;
        size = getSizeOfPagination(size);
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.initiateTasksForNJ.size(); j++) {
                waitFor(1);
                assertEquals(taskSearchPage.initiateTasksForNJ.get(j).getText().trim(), searchParameter.get().get("applicationId").trim(), "Application id did not match");
                recordFound = true;
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "No Record Found message displayed");
    }

    @Then("I validate column {string} is displayed on the task search result")
    public void validateColumnIsDisplayedOnTaskSearchResultSection(String columnName) {
        waitForVisibility(taskSearchPage.columnNames.get(0), 5);
        assertTrue(taskSearchPage.columnNames.stream().anyMatch(x -> x.getText().equalsIgnoreCase(columnName)), columnName + " column is not displayed");
    }

    @Then("I validate consumer name {string} on the search result")
    public void validateConsumerNameOnTaskSearchResult(String searchName) {
        waitForVisibility(taskSearchPage.consumerNameSearchResult.get(0), 5);
        assertEquals(searchName, taskSearchPage.consumerNameSearchResult.get(0).getText());
    }

    @When("I click on Link button in task search")
    public void click_link_button() {
        waitFor(5);
        scrollToElement(taskSearchPage.linksBtn.get(0));
        synchronized (CRM_GeneralTaskStepDef.projectDate){CRM_GeneralTaskStepDef.projectDate.set(taskSearchPage.taskStatusDate.get(0).getText());}
        CRM_GeneralTaskStepDef.taskValues.get().put("startDate", taskSearchPage.taskStatusDate.get(0).getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("status", taskSearchPage.taskStatuses.get(0).getText());
        CRM_TaskManagementStepDef.taskId.set(taskSearchPage.taskIDs.get(0).getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("created_by", createTask.userAccountName.getText());
        taskSearchPage.linksBtn.get(0).click();
        waitFor(3);
    }

    @When("I will search with srId")
    public void searchWithSRId() {
        waitFor(5);
        waitForVisibility(taskSearchPage.taskId, 5);
        System.out.println("SR id as search parameter: "+CRM_TaskManagementStepDef.srID.get());
        hover(taskSearchPage.dueDate);
        waitFor(2);
        clearAndFillText(taskSearchPage.taskId,(CRM_TaskManagementStepDef.srID.get()));
        clickOnSearchButton();
    }

    @Then("I verify task search result has {string} checkbox {string}")
    public void verifyEscalatedCheckBoxInSearchResult(String checkbox, String expectedCondition) {
        boolean recordFound = false;
        scrollToElement(taskSearchPage.columnNames.get(2));
     //   waitForVisibility(taskSearchPage.columnNames.get(2), 90);
        if (taskSearchPage.pageNationDD.getText().equals("show 20"))
            selectDropDown(taskSearchPage.pageNationDD, "show 5");
        int size = 1;
        size = getSizeOfPagination(size);
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.expandSearchResultArrows.size(); j++) {
                waitFor(1);
                System.out.println("Size is: "+taskSearchPage.expandSearchResultArrows.size());
                taskSearchPage.expandSearchResultArrows.get(j).click();
                waitFor(2);
                switch (checkbox) {
                    case "escalated":
                        if (expectedCondition.equalsIgnoreCase("checked")) {
                            assertTrue(taskSearchPage.escalatedCheckBox.get(j).isSelected(), "Escalated checkBox is unchecked");
                        } else {
                            assertFalse(taskSearchPage.escalatedCheckBox.get(j).isSelected(), "Escalated checkBox is checked");
                        }
                        recordFound = true;
                        break;
                    case "expedited":
                        if (expectedCondition.equalsIgnoreCase("checked"))
                            assertTrue(taskSearchPage.expeditedCheckBox.get(j).isSelected(), "Expedited checkBox is unchecked");
                        else
                            assertFalse(taskSearchPage.expeditedCheckBox.get(j).isSelected(), "Expedited checkBox is checked");
                        recordFound = true;
                        break;
                    default:
                        fail("case condition not match");
                        break;
                }

            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "No Record Found message displayed");
    }

    private int getSizeOfPagination(int size) {
        if (isElementDisplayed(taskSearchPage.lnkArrowForward)) {
            jsClick(taskSearchPage.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskSearchPage.lnkPageNations.get(taskSearchPage.lnkPageNations.size() - 1).getText());
            jsClick(taskSearchPage.lnkArrowBack);
            waitFor(1);
        }
        return size;
    }

    @Then("I will search for newly created task on Task Search")
    public void searchForNewlyCreatedTaskByAPIOnTaskSearchPage() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");
        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));

        waitForVisibility(taskSearchPage.taskSearchHeader, 10);
        CRM_GeneralTaskStepDef.taskValues.get().put("taskID", String.valueOf(payloadObject.get().getJSONObject("dataObject").getInt("taskId")));
        System.out.println("Newly created taskid for task search: "+String.valueOf(payloadObject.get().getJSONObject("dataObject").getInt("taskId")));
        hover(taskSearchPage.dueDate);
        taskSearchPage.taskId.sendKeys(String.valueOf(payloadObject.get().getJSONObject("dataObject").getInt("taskId")));
        waitFor(2);
        jsClick(taskSearchPage.searchBtn);
     //   taskSearchPage.searchBtn.click();

    }

    @When("I select {string} sign for due date in task search")
    public void selectIconForDueDate(String comparisonSign) {
        waitForVisibility(taskSearchPage.comparisonSignButton, 500);
        click(taskSearchPage.comparisonSignButton);
        getDynamicWebElements("//li[text()='compSign']", "compSign", comparisonSign).forEach(WebElement::click);
    }

    @Then("Validate due date column contains {string} values {string}")
    public void validateDueDateValuesAreCorrect(String day, String sign) {
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            myTask.lnkPageNations.forEach(x -> {
                if (isElementDisplayed(myTask.lnkArrowForward)) {
                    click(x);
                }
                switch (sign) {
                    case "=":
                        taskSearchPage.taskDueDateDate.forEach(t -> {
                            assertEquals(convertStringToDate(t.getText()), convertStringToDate(getFutureDateInFormat(day)));
                        });
                        break;
                    case "<":
                        taskSearchPage.taskDueDateDate.forEach(l -> {
                            assertTrue(convertStringToDate(l.getText()).before(convertStringToDate(getDate(day))));
                        });
                        break;
                    case ">":
                        List<WebElement> dueDateValues = projectName1.get().equalsIgnoreCase("NJ-SBE") ? taskSearchPage.dueDateValuesForNj : taskSearchPage.taskDueDateDate;
                        dueDateValues.forEach(f -> {
                            assertTrue(convertStringToDate(f.getText()).after(convertStringToDate(getDate(day))));
                        });
                        break;
                    default:
                        fail("Incorrect date provided " + day);
                }
            });
        } else fail("List is empty or doesn't has forward arrow button");
    }

////refactored to make it dynamic, based on pageName and dd value
    @Then("I verify view pagination {string} in {string} page")
    public void i_verify_default_pagination_in_permission_page(String expectedPagination, String pageName) {
        List<WebElement> listDisplayedPagination = getDynamicWebElements("//div[contains(text(),'pagination')]", "pagination", expectedPagination);
        assertEquals(expectedPagination, listDisplayedPagination.get(0).getText());
        pageNameCache.set(pageName);
        waitFor(3);
        switch (pageName) {
            case "My Task":
            case "Work Queue":
                assertEquals(Integer.parseInt(expectedPagination.replaceAll("\\D", "")), myTask.taskIDs.size());
                break;
            case "Task Search":
                assertEquals(Integer.parseInt(expectedPagination.replaceAll("\\D", "")), taskSearchPage.taskIDs.size());
        }
    }

    @When("I navigate to {string} page in search result")
    public void validateUserReturnedToSearchPage(String pageNumber) {
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            for (WebElement element : myTask.lnkPageNations) {
                if (isElementDisplayed(myTask.lnkArrowForward)) {
                    waitFor(1);
                    click(element);
                    if (element.getText().equalsIgnoreCase(pageNumber)) {
                        taskIDs.set(getTaskIds());
                        break;
                    }
                }
            }
        }
    }

    @Then("Verify {string} pages listed in the task search result")
    public void verifyNumberOfPagesListedInTaskSearchResult(String pageNumber) {
        waitForVisibility(myTask.lnkArrowForward, 5);
        click(myTask.lnkArrowForward);
        assertEquals(pageNumber, Driver.getDriver().findElements(By.xpath("//ul[@class='pagination']/li//a")).stream().reduce((first, second) -> second).get().getText());
    }

    @Then("I verify user returned to the {string} in search page")
    public void verifySearchPageRetainsPaginationAfterUserNavigatedToViewTask(String pageNumber) {
        waitForVisibility(taskSearchPage.currentlyOnPaginationButton, 5);
        assertEquals(pageNumber, taskSearchPage.currentlyOnPaginationButton.getText());
    }

    @And("I verify task ids list retains in search page")
    public void verifyTaskIdsRetains() {
        assertEquals(taskIDs.get(), getTaskIds(), "Results are not the same");
    }

    @And("I verify search parameters and search results are cleared out")
    public void Iverifysearchparametersandsearchresultsareclearedout() {
        waitForVisibility(taskSearchPage.taskSearchHeader, 10);
        assertFalse(isElementDisplayed(taskSearchPage.taskIDColumnHeader));
        assertTrue(taskSearchPage.taskId.getText().isEmpty(), "Results not cleared out");
    }

    @And("I verify search parameters and search results are remain")
    public void Iverifysearchparametersandsearchresultsareremain() {
        waitForVisibility(taskSearchPage.taskSearchHeader, 10);
        assertEquals(taskIdLocalVar.get(), taskSearchPage.taskId.getText(), "Results not cleared out");
        assertTrue(isElementDisplayed(taskSearchPage.taskId));
    }

    @Then("I verify Go to page section is displayed")
    public void i_verify_Go_to_page_section_is_displayed() {
        scrollDown();
        assertTrue(taskSearchPage.goToPageSection.isDisplayed());
    }

    @Then("I provide number {string} on the go to page section")
    public void i_provide_number_on_the_go_to_page_section(String pageNumber) {
        clearAndFillText(taskSearchPage.goToPageSection, pageNumber);
        jsClick(taskSearchPage.sendIconForGoToPage);
    }

    @Then("I verify that expected page number {string} results are displayed")
    public void i_verify_that_expected_page_number_results_are_displayed(String pageNumber) {
        assertTrue(taskSearchPage.selectedPagefromGoToPage.getText().contains(pageNumber));
    }

    @Then("I verify that error message for invalid page number is getting displayed")
    public void i_verify_that_error_message_for_invalid_page_number_is_getting_displayed() {
        assertTrue(taskSearchPage.errorMessageForInvalidGoToPageNumber.isDisplayed());
    }

    @And("I verify following information in task search result")
    public void verifyFollowingInformationInSearchResult(Map<String, String> data) {
        waitForVisibility(taskSearchPage.taskIDs.get(0), 10);
        CRM_TaskManagementStepDef.srID.set(taskSearchPage.taskIDs.get(0).getText());
        CRM_TaskManagementStepDef.taskId.set(taskSearchPage.taskIDs.get(0).getText());

        ArrayList<String> list = new ArrayList<String>(data.keySet());
        for (String key : list) {
            switch (key) {
                case "taskType":
                    if (data.get(key).equals("getFromCreatePage"))
                        assertTrue(taskSearchPage.taskTypes.get(0).getText().contains(CRM_GeneralTaskStepDef.taskValues.get().get("taskType")), "Task type is mismatch in task search result");
                    else
                        assertEquals(taskSearchPage.taskTypes.get(0).getText(), data.get(key), "Task type is mismatch in task search result");
                    break;
                case "consumerID":
                    if (data.get(key).equals("getFromCreatePage"))
                        assertEquals(taskSearchPage.taskConsumerID.get(0).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "consumerID is mismatch in task search result");
                    else if (data.get(key).equals("getFromExnlID"))
                        assertEquals(taskSearchPage.taskConsumerID.get(0).getText(), CRM_GeneralTaskStepDef.externalConsumerID.get(), "consumerID is mismatch in task search result");
                    else
                        assertEquals(taskSearchPage.taskConsumerID.get(0).getText(), data.get(key), "consumerID is mismatch in task search result");
                    break;
                case "consumerID1":
                    if (data.get(key).equals("getFromCreatePage"))
                        assertEquals(taskSearchPage.taskGetInsuredConsumerID.get(0).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "consumerID is mismatch in task search result");
                    else
                        assertEquals(taskSearchPage.taskGetInsuredConsumerID.get(0).getText(), data.get(key), "consumerID is mismatch in task search result");
                    break;
                case "consumerIdVA":
                    if (data.get(key).equals("getFromCreatePage"))
                        assertEquals(taskSearchPage.taskConsumerIdCoverVA.get(0).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"), "consumerID is mismatch in task search result");
                    else
                        assertEquals(taskSearchPage.taskConsumerIdCoverVA.get(0).getText(), data.get(key), "consumerID is mismatch in task search result");
                    break;
                case "caseID":
                    if (data.get(key).equals("getFromCreatePage"))
                        assertEquals(taskSearchPage.taskCaseID.get(0).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "caseID is mismatch in task search result");
                    else if (data.get(key).equals("getFromExnlID"))
                        assertEquals(taskSearchPage.taskCaseID.get(0).getText(), CRM_GeneralTaskStepDef.externalCaseID.get(), "caseID is mismatch in task search result");
                    else
                        assertEquals(taskSearchPage.taskCaseID.get(0).getText(), data.get(key), "caseID is mismatch in task search result");
                    break;
                case "caseID1":
                    if (data.get(key).equals("getFromCreatePage"))
                        assertEquals(taskSearchPage.taskGetInsuredCaseID.get(0).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "caseID is mismatch in task search result");
                    else
                        assertEquals(taskSearchPage.taskGetInsuredCaseID.get(0).getText(), data.get(key), "caseID is mismatch in task search result");
                    break;
                default:
                    fail("No matched Switch case");
            }
        }
    }

    @And("I click task id to get the results in ascending order in Task Search")
    public void click_task_id_for_ascending_order() {
        waitFor(10);
        scrollDownUsingActions(3);
        jsClick(taskSearchPage.taskIDColumnHeader);
        if (isElementDisplayed(dashboard.warningPopupContinueBtn))
            dashboard.warningPopupContinueBtn.click();
        waitFor(2);
        ArrayList<String> displayedInOrder = new ArrayList();
        ArrayList<String> toSort = new ArrayList();
        for (WebElement row : taskSearchPage.taskListRows) {
            displayedInOrder.add(row.findElement(By.xpath("//td[3]")).getText());
            toSort.add(row.findElement(By.xpath("//td[3]")).getText());
        }
        Collections.sort(toSort);
        assertEquals(displayedInOrder, toSort);
    }

    @Then("I verify task search result not returned any SR and has task with assignee filed {string}")
    public void verifyEscalatedCheckBoxInSearchResult(String expectedCondition) {
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().getAllActiveServiceRequest(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiprojectId);
        System.out.println(APITaskManagementController.allFieldsValue.get());
        boolean recordFound = false;
        waitForVisibility(taskSearchPage.columnNames.get(2), 90);
        if (taskSearchPage.pageNationDD.getText().equals("show 20"))
            selectDropDown(taskSearchPage.pageNationDD, "show 5");
        int size = 1;
        size = getSizeOfPagination(size);
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.expandSearchResultArrows.size(); j++) {
                assertFalse(APITaskManagementController.allFieldsValue.get().contains(taskSearchPage.taskTypes.get(j).getText().toLowerCase().trim()), "SR present in task search result");
                taskSearchPage.expandSearchResultArrows.get(j).click();
                if (expectedCondition.equals("-- --"))
                    assertEquals(taskSearchPage.assigneeExpandedView.get(j).getText(), expectedCondition, "Task is assigned to someone");
                else
                    assertTrue(!taskSearchPage.assigneeExpandedView.get(j).getText().isEmpty() && !taskSearchPage.assigneeExpandedView.get(j).getText().equals("-- --"), "Task is not assigned");
                taskSearchPage.expandSearchResultArrows.get(j).click();
                waitFor(1);
                recordFound = true;
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "No Record Found message displayed");
    }

    @Then("I verify Main Check box is selected")
    public void verifyMainCheckboxIsSelected() {
        if (taskSearchPage.pageNationDD.getText().equals("show 20"))
            selectDropDown(taskSearchPage.pageNationDD, "show 5");
        int size = 1;
        size = getSizeOfPagination(size);
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.taskIDs.size(); j++) {
                waitFor(1);
                if (size <= 20)
                    checkedTaskId.get().add(taskSearchPage.checkbox.get(j).getText());
                jsClick(taskSearchPage.checkbox.get(j));
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(taskSearchPage.mainCheckbox.isSelected(), "Main CheckBox is not selected");
        click(taskSearchPage.lnkArrowBack);
    }

    @Then("I verify Main Check box is Unselected")
    public void verifyMainCheckboxIsUnselected() {
        if (taskSearchPage.pageNationDD.getText().equals("show 20"))
            selectDropDown(taskSearchPage.pageNationDD, "show 5");
        int size = 1;
        size = getSizeOfPagination(size);
        if (!taskSearchPage.mainCheckbox.isSelected())
            jsClick(taskSearchPage.mainCheckbox);
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.taskIDs.size(); j++) {
                if (i <= 20) {
                    jsClick(taskSearchPage.checkbox.get(0));
                    uncheckedTaskId.get().add(taskSearchPage.taskIDs.get(j).getText());
                }
                break;
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertFalse(taskSearchPage.mainCheckbox.isSelected(), "Main CheckBox is selected");
    }

    @Then("I verify Case Id {string} and Consumer ID are {string} in Task Search results")
    public void IverifyCaseIdandConsumerIDarinTaskSearchresults(String caseId, String consumerId) {
        assertTrue(taskSearchPage.taskConsumerID.get(0).getText().equalsIgnoreCase(consumerId));
        assertTrue(taskSearchPage.taskCaseID.get(0).getText().equalsIgnoreCase(caseId));

    }

    @Then("I verify search result type is {string} and CaseId is {string}")
    public void iVerifySearchResultTypeIsAndCaseIdIs(String type, String caseId) {
        assertTrue(taskSearchPage.taskTypes.get(0).getText().equalsIgnoreCase(type));
        assertTrue(taskSearchPage.taskCaseID.get(0).getText().equalsIgnoreCase(caseId));
    }

    @Then("Verify SR and Task linked to correspondence")
    public void verifySRTaskLinkedToCorrespondence() {
        assertTrue(crmTaskServiceTab.frstTaskId.getText().equalsIgnoreCase(CRM_TaskManagementStepDef.srID.get()));
    }

    @Then("I verify search result type is {string} and ConsumerId is {string}")
    public void iVerifySearchResultTypeIsAndConsumerIdIs(String type, String consumerId) {
        assertTrue(taskSearchPage.taskTypes.get(0).getText().equalsIgnoreCase(type));
        assertTrue(taskSearchPage.taskConsumerID.get(0).getText().equalsIgnoreCase(consumerId));
    }

    @And("I verify that displayed taskId matches with expected taskId")
    public void iVerifyThatDisplayedTaskIdMatchesWithExpectedTaskId() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");
        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));
        System.out.println("Expected taskId: " + String.valueOf(payloadObject.get().getJSONObject("dataObject").getInt("taskId")));
        System.out.println("Found task id: " + taskSearchPage.taskIDs.get(0).getText());
        assertEquals(String.valueOf(payloadObject.get().getJSONObject("dataObject").getInt("taskId")), taskSearchPage.taskIDs.get(0).getText());
    }

    @Then("I will verify user gets error message pop up for linking task created by SR to another SR")
    public void verifyUserGetErrorMessageWhenTheyLinktheTasktoanotherSR() {
        waitFor(12);
        sa.get().assertThat(isElementDisplayed(myTask.errorMessageForTask)).as(" No error message was displayed").isTrue();
    }

    @And("I click search button on task search page")
    public void clickOnSearchButtonOnTaskSearchPage() {
        waitFor(1);
        click(taskSearchPage.searchBtn);
    }

    @Then("I validate the system does not displays an Warning Message: {string}")
    public void verifyTheSystemDisplaysAnWarningMessage(String msg) {
        assertFalse(isElementDisplayed(taskSearchPage.errorMsg), msg + " is displayed");
    }

    @Then("I verify task search field {string} has default value {string}")
    public void verifyTaskSearchFieldHasDefaultValue(String label, String value) {
        switch (label) {
            case "SEARCH CASE":
                assertEquals(taskSearchPage.searchCase.getText(), value);
                break;
            case "SEARCH CONSUMER":
                assertEquals(taskSearchPage.searchConsumer.getText(), value);
                break;
            default:
                fail("incorrect switch condition");
        }
    }

    @Then("I verify dateOfContact field is removed in search page")
    public void verifyDateOfContactFieldIsRemovedInAdvancedSearchPage() {
        assertFalse(isElementDisplayed(taskSearchPage.dateOfContact), "Date of Contact field is displayed not removed");
    }

    @Then("I verify dateOfContact column is removed in search result")
    public void verifyDateOfContactColumnIsRemovedInAdvancedSearchPage() {
        assertFalse(isElementDisplayed(taskSearchPage.dateOfContactColumn), "Date of Contact column is displayed not removed");
    }

    @When("I will search with taskId created by API")
    public void searchWithTaskIdcreatedbyAPI() {
        waitFor(1);
        waitForVisibility(taskSearchPage.taskId, 10);
        taskSearchPage.taskId.sendKeys(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId").toString());
        CRM_TaskManagementStepDef.srID.set(CRM_TaskManagementStepDef.taskId.get());
        clickOnSearchButton();
    }

    @And("I verify application id in task search section is not displayed")
    public void verifyApplicationIDisNotDisplayedInTheTaskSearch() {
        sa.get().assertThat(isElementDisplayed(taskSearchPage.applicationID)).as("Application Id is displayed on the task search").isFalse();
    }

    @When("I verify Link button is displayed in task search page")
    public void verify_link_button() {
        sa.get().assertThat(isElementDisplayed(taskSearchPage.linksBtn.get(0))).as("Link button is not displayed in task search page").isTrue();
        sa.get().assertThat(taskSearchPage.linksBtn.size() >= 1).as("Link button is not displayed in task search page").isTrue();
    }

    @Then("I Verify {string} task with {string} status does have Initiate button Task Search Page")
    public void verifyTaskDoesHaveInitiateButtonTaskSearchPage(String taskTypeExpected, String taskStatusExpected) {

        waitFor(1);
        boolean recordFound = false;
        int size = 1;
        if (myTask.lnkPageNations.size() > 1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskSearchPage.lnkPageNations.get(taskSearchPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        scrollUpUsingActions(1);
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskSearchPage.taskListRows.size(); j++) {
                if (!isElementDisplayed(taskSearchPage.mainCheckbox) && !isElementDisplayed(bulkUpdatePage.bulkUpdateBtn)) {
                    if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE") &&
                            taskSearchPage.taskTypes.get(j).getText().equals(taskTypeExpected) && taskSearchPage.taskStatuses.get(j).getText().equals(taskStatusExpected)) {
                        waitFor(2);
                        assertTrue(isElementDisplayed(taskSearchPage.initiateTasks.get(j)),
                                "Initiate button not displayed");
                    } else if (taskSearchPage.taskTypes.get(j).getText().equals(taskTypeExpected) && taskSearchPage.taskPriority.get(j).getText().equals(taskStatusExpected))
                        assertTrue(isElementDisplayed(taskSearchPage.taskConsumerID.get(j)),
                                "Initiate button not displayed");
                    recordFound = true;
                } else {
                    if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE") &&
                            taskSearchPage.taskTypes.get(j).getText().equals(taskTypeExpected) && taskSearchPage.taskStatusforNJ.get(j).getText().equals(taskStatusExpected)) {
                        waitFor(2);
                        assertTrue(isElementDisplayed(taskSearchPage.initiateTasksForNJ.get(j)),
                                "Initiate button not displayed");
                    } else if (taskSearchPage.taskTypes.get(j).getText().equals(taskTypeExpected) && taskSearchPage.taskStatuses.get(j).getText().equals(taskStatusExpected))
                        assertTrue(isElementDisplayed(taskSearchPage.initiateTasks.get(j)),
                                "Initiate button not displayed");
                    recordFound = true;
                }
            }
            if (recordFound)
                break;
            else if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Task type not matches");
    }


    @Then("I verify DueDate updated on Task")
    public void iVerifyDueDateUpdatedOnTask() {
        sa.get().assertThat(myTask.taskSilderDueDate.getText()).as("Due Date Not updated").isEqualTo(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.response[0].triggerDate"));
    }

    @Then("I verify Task Notes field is not displayed in Search Results page")
    public void i_verify_Task_Notes_field_is_not_displayed_in_Search_results_page() {
        sa.get().assertThat(isElementDisplayed(taskSearchPage.lblTaskNoteSearchResult)).as("Task Notes is present Task Search-Expanded View").isFalse();
    }
    @Then("I verify search fields in Task SR Types on Search page")
    public void iVerifySearchFieldsInTask_SRPage() {
        sa.get().assertThat(isElementDisplayed(taskSearchPage.taskSearchPageHearder)).as("Task/SR Search Header is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.taskId)).as("ID field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.taskTypeDropdown)).as("taskTypeDropdown field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.srTypeDropDown)).as("srTypeDropDown field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.taskStatusDropdown)).as("taskStatusDropdown field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.statusDate)).as("statusDate field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.priorityDropdown)).as("priorityDropdown field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.dueDate)).as("dueDate field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.taskId)).as("ID field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.searchCase)).as("searchCase field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.searchConsumer)).as("searchConsumer field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.consumerId)).as("consumerId field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.assignmentStatusDD)).as("assignmentStatusDD field is not displaying").isTrue();
        if(!LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA"))
            sa.get().assertThat(isElementDisplayed(taskSearchPage.caseId)).as("caseId field is not displaying").isTrue();
        click(taskSearchPage.advanceSearch);
        waitFor(1);
        sa.get().assertThat(isElementDisplayed(taskSearchPage.taskSearchPageHearder)).as("Task/SR Search Header field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.consumerFirstName)).as("consumerFirstName field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.consumerLastName)).as("consumerLastName field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.sourceDropdown)).as("sourceDropdown field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.assignee)).as("assignee field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.createdBy)).as("createdBy field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.createdOn)).as("createdOn field is not displaying").isTrue();
        if(!LoginStepDef.projectName1.get().equalsIgnoreCase("IN-EB")) {
            sa.get().assertThat(isElementDisplayed(taskSearchPage.dateOfContactLabel)).as("dateOfContactLabel field is not displaying").isTrue();
            sa.get().assertThat(isElementDisplayed(taskSearchPage.applicationId)).as("applicationId field is not displaying").isTrue();
            sa.get().assertThat(isElementDisplayed(taskSearchPage.expedited)).as("expedited field is not displaying").isTrue();
        }
        sa.get().assertThat(isElementDisplayed(taskSearchPage.assigneeTeamLabel)).as("assignee team field is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(taskSearchPage.assigneeBusinessUnitLabel)).as("assignee bu field is not displaying").isTrue();
    }
    @Then("I verify only active service request is displayed in service request drop down in Task Search page")
    public void verifyOnlyActiveServiceRequestIsDisplayedInSRTypeDropDownOnTaskSearchPage() {
        waitForVisibility(taskSearchPage.srTypeDropDown, 10);
        taskSearchPage.srTypeDropDown.click();
        displayedInOrder.set(new ArrayList<>());
        for(int i=1;i<taskSearchPage.srTypeListValues.size();i++){
            displayedInOrder.get().add(taskSearchPage.srTypeListValues.get(i).getText().toLowerCase().trim());
        }
        Collections.sort(APITaskManagementController.allFieldsValue.get(),String.CASE_INSENSITIVE_ORDER);
        assertEquals(displayedInOrder, APITaskManagementController.allFieldsValue.get(),
                "SR are not Active or SR are not listed in alphabetical order");
        actions.moveToElement(taskSearchPage.taskId).click().build().perform();
    }
    @Then("I Verify {string} multi select DD value in search page {string}")
    public void verifyMultiSelectDDValueInSearchPageForTaskTypes(String typeField,String taskSrTypeExpectedVlu) {
        waitFor(2);
        switch(typeField){
            case "SR Type":
                sa.get().assertThat(taskSearchPage.srTypes.getText()).as("SRTypes is not multi select and not matched").isEqualTo(taskSrTypeExpectedVlu);
                break;
            case "Task Type" :
                sa.get().assertThat(createGeneralTask.taskType.getText()).as("TaskTypes is not multi select and not matched").isEqualTo(taskSrTypeExpectedVlu);
                break;
            case "Correspondence Type":
                sa.get().assertThat(createGeneralTask.outboundCorrespondenceType.getText()).as("Outbound Correspondence type is not multi select").isEqualTo(taskSrTypeExpectedVlu);
                break;
        }
        waitFor(1);
        sa.get().assertThat(isElementDisplayed(taskSearchPage.taskIDColumnHeader)).as("Header Name is not displaying").isTrue();
    }

    @And("I click taskId column in the task search result")
    public void click_task_id_in_task_search_page() {
        waitFor(1);
        scrollDownUsingActions(3);
        jsClick(taskSearchPage.taskIDColumnHeader);
        waitFor(1);
    }

    @And("I verify {string} icon displays on {string}")
    public void verify_icon_displays_on_task_or_sr(String colorIcon,String typeTask) {
        scrollToElement(taskSearchPage.taskSearchResultHeader);
        waitFor(2);
        String xpathValue = colorIcon.equalsIgnoreCase("blue") ? "assignment":"list_alt";
        sa.get().assertThat(taskSearchPage.listIconsOnTaskSearchResult.stream().allMatch(x->x.getText().equalsIgnoreCase(xpathValue))).as(colorIcon + " icon is not displayed on " + typeTask).isTrue();
    }

    @Then("I verify {string} task types displays on search result")
    public void verify_tasktype_displays_on_the_search_result(String typeTaskExpected) {
        scrollToElement(taskSearchPage.taskSearchResultHeader);
        waitFor(4);
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            myTask.lnkPageNations.forEach(x -> {
                if (isElementDisplayed(myTask.lnkArrowForward)) {
                    click(x);
                }
                sa.get().assertThat(taskSearchPage.typeName.stream().allMatch(typeTask->typeTask.getText().equalsIgnoreCase(typeTaskExpected))).isTrue();
            });
        }
    }

    @And("Verify I will NOT be able to perform a Task Search")
    public void VerifyIwillNOTbeabletoperformaTaskSearch() {
        waitFor(3);
        sa.get().assertThat(isElementDisplayed(taskSearchPage.saveTaskSearchLabel));
    }

    @And("Verify Search results display Task with related Contact Reasons {string}")
    public void verifySearchResultsDisplayTaskWithRelatedContactReasons(String reason) {
        sa.get().assertThat(taskSearchPage.contactReasonResults.stream()
                .filter(reasons ->reasons.getText().equalsIgnoreCase(reason)));
    }

    @And("Verify Search Results Display Page Column Names")
    public void verifySearchResultsDisplayPageColumnNames(List<String> colums) {
       for (String colum : colums) {
           sa.get().assertThat(taskSearchPage.columnNames.stream()
                   .filter(reasons -> reasons.getText().equalsIgnoreCase(colum)));
       }
    }

    @Then("I click on {int} task on search results")
    public void i_click_on_task_on_search_results(int taskOrder) {
        waitFor(2);
        scrollDownUsingActions(5);
        System.out.println("Task order: "+(taskOrder-1));
            CRM_TaskManagementStepDef.taskId.set(taskSearchPage.taskIDs.get(taskOrder-1).getText());
            System.out.println("Clicked taskId: "+CRM_TaskManagementStepDef.taskId.get());
            jsClick(taskSearchPage.taskIDs.get(taskOrder-1));
            waitFor(5);
    }



    @Then("I verify that I am back to task search page and previously selected {int} row is highlighted")
    public void i_verify_that_I_am_back_to_task_search_page_and_previously_selected_row_is_highlighted(int taskOrder) {
        assertTrue(taskSearchPage.searchResultsHeader.isDisplayed(),"Task search results are not displayed");
        assertTrue(taskSearchPage.taskSearchHeader.isDisplayed(),"Task search header isnt displayed");
        waitFor(2);
        assertTrue(taskSearchPage.taskResultsForHighlight.get(taskOrder-1).getAttribute("class").contains("mx-table-row-highlight"),"Row not highlighted");
    }
}
