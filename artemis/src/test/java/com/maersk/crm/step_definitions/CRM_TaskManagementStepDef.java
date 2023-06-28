package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.pages.LoginPage;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.pages.tm.TMAddNewUserPage;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class CRM_TaskManagementStepDef extends CRMUtilities implements ApiBase {


    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRM_GeneralTaskStepDef generalTaskStepDef = new CRM_GeneralTaskStepDef();
    CRMWOrkQueuePage workQueue = new CRMWOrkQueuePage();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
    CRMSavedTaskSearchPage savedTaskSrchPage = new CRMSavedTaskSearchPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTaskQueueFilterPage taskQueueFilter = new CRMTaskQueueFilterPage();
    CRMTaskServiceRequiestTabPage taskSRTabPage = new CRMTaskServiceRequiestTabPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRMTSRTabForSRPage trsTabSRPage = new CRMTSRTabForSRPage();
    LoginPage loginPage = new LoginPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    TMAddNewUserPage newUserPage = new TMAddNewUserPage();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    Actions actions = new Actions(Driver.getDriver());
    CRMApplicationTrackingPage applicationTrackingPage = new CRMApplicationTrackingPage();
    
    public static final ThreadLocal<String> taskId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> taskType =ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> taskDetailsId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> assignee = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> created_by = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> taskInfo = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> source = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> created_on = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> status_date = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> case_ID = ThreadLocal.withInitial(String::new);
//    private APITaskManagementController apiTaskManagementController = new APITaskManagementController();
//    private final ThreadLocal<String> largestTaskId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> dueIn = ThreadLocal.withInitial(String::new);
    private WebElement initate;
    public static final ThreadLocal<String> taskNotesEntered = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> taskStatus = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> pageNameCache = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Boolean> flag = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<String> srID = ThreadLocal.withInitial(String::new);
    final ThreadLocal<List<String>> expectedData = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> tabs = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<CRM_GeneralTaskStepDef> generalTask = ThreadLocal.withInitial(CRM_GeneralTaskStepDef::new);
    CRMTaskEditHistoryPage editHistory = new CRMTaskEditHistoryPage();
    CRMApplicationSearchPage crmSearchApplicationPage = new CRMApplicationSearchPage();
    CRMSRViewEditPage srViewEditPage = new CRMSRViewEditPage();


    @When("I expend first Task from the list by clicking in Task ID")
    public void i_expend_first_Task_from_the_list_by_clicking_in_Task_ID() {
        waitFor(5);
        this.taskId.set(myTask.taskIDs.get(CRM_WorkQueueStepDef.position.get()).getText());
        this.srID.set(String.valueOf(Integer.parseInt(this.taskId.get()) - 1));
        scrollUpRobotKey();
        jsClick(myTask.taskIDs.get(CRM_WorkQueueStepDef.position.get()));
    }

    @Then("I am Navigated to Task details page")
    public void i_am_Navigated_to_Task_details_page() {
        sa.get().assertThat(myTask.taskDetailsSection.isDisplayed()).as("View Task details page is not displayed").isTrue();
        sa.get().assertThat(myTask.viewTaskHeader.isDisplayed()).as("Is not on My task Details page").isTrue();
    }

    @Then("I am able to navigate back to My Task or Work Queue page")
    public void i_am_able_to_navigate_back_to_My_Task_Work_Queue_page() {
        myTask.backArrowInViewPage.click();
        waitFor(2);
        assertTrue(myTask.taskList.isDisplayed());
    }

    @Then("I verify the link ids are displayed")
    public void verifyLinkIdDisplayed() {
        for (WebElement row : myTask.linkTableRows) {
            assertNotNull(row.findElement(By.xpath("./td[2]")).getText());
        }
    }

    @Then("I verify task id and edit task button are displayed")
    public void verifyTaskIdEditLink() {
        assertTrue(isElementDisplayed(myTask.lblTaskId), "Task Id is not present on view page");
        assertTrue(isElementDisplayed(myTask.btnEditTask), "Edit button is not present on view page");
    }

    @Then("I verify task list has five records with pagination")
    public void verifyTaskListWithPageNation() {
        sa.get().assertThat(myTask.taskListRows.size() <= 5).as("task list does not have five records with pagination").isTrue();
    }

    @Then("I verify expand and initiate button present in each row of task list")
    public void verifyExpandInitiateButtons() {
        waitFor(8);
        int size = 1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
        }

        for (int i = 1; i <= size; i++) {
            try {
                for (WebElement row : myTask.taskListRows) {
                    if (!row.findElement(By.xpath("./td[3]")).getText().equals("Incomplete Contact Record")) {
                        Assert.assertTrue(isElementDisplayed(row.findElement(By.xpath("./td[1]/button"))));
                        Assert.assertTrue(isElementDisplayed(row.findElement(By.xpath("./td[12]/button"))));
                    }
                }
            } catch (NoSuchElementException nse) {
                fail("Expand or Initiate button is not displayed");
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }

        }

    }

    @When("I expand the first row in task list")
    public void expandATaskRecordromTaskList() {
        waitFor(1);
        myTask.expandTaskArrows.get(CRM_WorkQueueStepDef.position.get()).click();
    }

    @Then("I verify task records are displayed in ascending order of their {string}")
    public void verifyAscendingOrder(String columnsName) {
        waitFor(1);
        int columnNum = 0;
        switch (columnsName) {
            case "TaskId":
                columnNum = 2;
                break;
            case "Priority":
                columnNum = 4;
                break;
            case "StatusDate":
                columnNum = 5;
                break;
            case "DueDate":
                columnNum = 7;
                break;
            case "CaseId":
                columnNum = 9;
                break;
        }
        waitFor(3);
        ArrayList<String> displayedInOrder = new ArrayList();
        ArrayList<String> toSort = new ArrayList();
        int size = 1;
        if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(1);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }
            for (int i = 1; i <= size; i++) {
                for (WebElement row : myTask.taskListRows) {
                    displayedInOrder.add(row.findElement(By.xpath("./td[" + columnNum + "]")).getText());
                    toSort.add(row.findElement(By.xpath("./td[" + columnNum + "]")).getText());
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
        Collections.sort(toSort);
        Assert.assertEquals(displayedInOrder, toSort);
    }

    @Then("I verify task records are displayed in descending order of their {string}")
    public void verifyDescendingOrder(String columnsName) {
        waitFor(1);
        int columnNum = 0;
        switch (columnsName) {
            case "TaskId":
                columnNum = 2;
                break;
            case "Priority":
                columnNum = 4;
                break;
            case "StatusDate":
                columnNum = 5;
                break;
            case "DueDate":
                columnNum = 7;
                break;
            case "CaseId":
                columnNum = 8;
                break;
        }
        ArrayList<String> displayedInOrder = new ArrayList();
        ArrayList<String> toSort = new ArrayList();
        int size = 1;
        if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(1);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }
            for (int i = 1; i <= size; i++) {
                for (WebElement row : myTask.taskListRows) {
                    displayedInOrder.add(row.findElement(By.xpath("./td[" + columnNum + "]")).getText());
                    toSort.add(row.findElement(By.xpath("./td[" + columnNum + "]")).getText());
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
        Collections.sort(toSort, Collections.reverseOrder());
        Assert.assertEquals(displayedInOrder, toSort);
    }

    @When("I click on {string} column on My Tasks Page")
    public void i_click_on_column_on_My_Tasks_Page(String columnName) {
        waitFor(2);
        String value = "";
        switch (columnName) {
            case "TaskId":
                myTask.taskIdColumn.click();
                waitFor(1);
                myTask.taskIdColumn.click();
                break;
            case "Priority":
                myTask.priorityColumn.click();
                waitFor(1);
                myTask.priorityColumn.click();
                break;
            case "StatusDate":
                myTask.statusDateColumn.click();
                waitFor(1);
                myTask.statusDateColumn.click();
                break;
            case "CaseId":
                myTask.caseIdColumn.click();
                waitFor(1);
                myTask.caseIdColumn.click();
                break;
            case "ConsumerId":
                myTask.consumerIdColumn.click();
                waitFor(1);
                myTask.consumerIdColumn.click();
                break;
        }
    }

    @When("I create {string} task with priority as {string} assignee as {string} and task info as {string}")
    public void createTask(String task_type, String priority, String assignee, String task_info) {
        System.out.println(assignee);
        CRM_GeneralTaskStepDef crm_generalTaskStepDef = new CRM_GeneralTaskStepDef();
        crm_generalTaskStepDef.navigateToGeneralTask(task_type);
        crm_generalTaskStepDef.SelectTaskDetails(task_type, priority, assignee, task_info);
        crm_generalTaskStepDef.clickSaveButton();
//        crm_generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
        if (assignee.isEmpty()) {
            this.assignee.set( "-- --");
            CRM_GeneralTaskStepDef.taskValues.get().put("assignee", "-- --");
        } else {
            this.assignee.set( assignee);
            CRM_GeneralTaskStepDef.taskValues.get().put("assignee", assignee);
        }

        CRM_GeneralTaskStepDef.taskValues.get().put("created_by", "Service AccountOne");
        this.created_by.set( "Service AccountOne");
        CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", task_info);
        this.taskInfo.set( task_info);
        this.source.set( "User");
        CRM_GeneralTaskStepDef.taskValues.get().put("created_on", getCurrentDate());
        this.created_on.set( getCurrentDate());
        this.status_date.set( getCurrentDate());
        this.case_ID.set( "-- --");
        waitFor(1);
    }

    @And("I click on Initiate button")
    public void clickOnInitiateButton() {
        actions.click(myTask.initiateLink).build().perform();
    }

    @And("I click on save button")
    public void clickOnISaveButton() {
        myTask.saveButton.click();
    }

    @And("I ensure My task page has at least one task with status other than {string} and I navigate to view task")
    public void ensureAtLeastOneTaskWithStatusOtherThanOnHold(String status) {
        waitFor(5);
        boolean flag = true;
        int size = 1;
        if (myTask.taskIDs.size() != 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(2);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }
            l1:
            for (int j = 1; j <= size; j++) {
                for (int i = 0; i < myTask.getStatusValues.size(); i++) {
                    waitFor(1);
                    if (myTask.taskTypes.get(i).getText().equalsIgnoreCase("General") &&
                            !myTask.getStatusValues.get(i).getText().equalsIgnoreCase(status)) {
                        flag = false;
                        taskId.set(myTask.taskIDs.get(i).getText());
                        myTask.taskIDs.get(i).click();
                        break l1;
                    }
                }
                if (j != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                    waitFor(2);
                }
            }
        }
        if (flag) {
            generalTaskStepDef.navigateToGeneralTask("General");
            generalTaskStepDef.SelectTaskDetails("", "", "Service", "");
            generalTaskStepDef.clickSaveButton();
            waitFor(5);
            myTask.taskIdColumn.click();
            taskId.set(myTask.taskIDs.get(0).getText());
            myTask.taskIDs.get(0).click();
        }
    }

    @And("I click on edit button on view task page")
    public void clickOnEditButton() {
        waitFor(1);
        for (int i = 0; i <= 3; i++) {
            jsClick(myTask.btnEditTask);
            waitFor(2);
            if (!isElementDisplayed(myTask.btnEditTask)) {
                break;
            }
        }
        sa.get().assertThat(myTask.reasonForEditDropDown.getText()).as("Reason For Edit is not cleared out as per CP-28816").isBlank();
        CRM_GeneralTaskStepDef.taskValues.get().put("reasonForEdit", "-- --");
        scrollUpUsingActions(2);
    }

    @And("I click on edit button on view task page for In-progress status")
    public void clickOnEditButtonForInProgressStatusTask() {
        jsClick(myTask.btnEditTask);
        this.flag.set(true);
    }

    @And("I changed status on edit page to {string}")
    public void changeTheStatusOnEditPage(String status) {
        waitForVisibility(myTask.editPageStatusDropDown, 10);
        scrollUpRobot();
        waitFor(2);
        if (isElementDisplayed(myTask.onHoldStatus)) {
            myTask.onHoldStatus.click();
        }
        selectDropDown(myTask.editPageStatusDropDown, status);
        taskStatus.set(status);
    }

    @Then("I verify {string} drop down is displayed as mandatory field")
    public void verifyMandatoryErrorMessage(String field) {
        click(myTask.saveBtn);
        switch (field) {
            case "REASON FOR HOLD":
                waitForVisibility(myTask.reasonForHoldDropDown, 10);
                sa.get().assertThat(isElementDisplayed(myTask.reasonForHoldDropDown)).as("Reason For Hold field is not displaying").isTrue();
                generalTask.get().verifyTaskTypeIsMandatoryField("REASON FOR HOLD");
                break;

            case "REASON FOR CANCEL":
                waitForVisibility(createGeneralTask.cancelReason, 10);
                sa.get().assertThat(isElementDisplayed(createGeneralTask.cancelReason)).as("Reason For Cancel field is not displaying").isTrue();
                generalTask.get().verifyTaskTypeIsMandatoryField("REASON FOR CANCEL");
                generalTask.get().verifyFieldIsNotMandatoryForTask("REASON FOR EDIT");
                break;

            case "REASON FOR EDIT":
                actions.moveToElement(myTask.reasonForEditDropDown).perform();
                sa.get().assertThat(isElementDisplayed(myTask.reasonForEditDropDown)).as("Reason For Edit field is not displaying").isTrue();
                generalTask.get().verifyTaskTypeIsMandatoryField("REASON FOR EDIT");
                sa.get().assertThat(isElementDisplayed(myTask.reasonForCancel)).as("Reason For Cancel field is displaying").isFalse();
                break;
        }
    }

    @And("I verify {string} dropdown has values")
    public void verifyDropDownValue(String field) {
        switch (field) {
            case "REASON FOR HOLD":
                String[] str = {"Application On Hold", "Missing Information", "State Directed"};
                click(myTask.reasonForHoldDropDown);
                for (int i = 1; i < myTask.reasonDropDownVlu.size(); i++) {
                    assertEquals(myTask.reasonDropDownVlu.get(i).getText(), str[i - 1]);
                }
                break;

            case "REASON FOR CANCEL":
                String[] str1 = {"Created Incorrectly", "Duplicate Task", "Task No Longer Required"};
                click(myTask.reasonForCancelDropDown);
                for (int i = 1; i < myTask.reasonDropDownVlu.size(); i++) {
                    assertEquals(myTask.reasonDropDownVlu.get(i).getText(), str1[i - 1]);
                }
                break;

            case "REASON FOR EDIT":
                String[] str2 = {"Corrected Case/Consumer Link", "Corrected Data Entry", "Corrected Error(s)", "Entered Additional Info"};
                click(myTask.reasonForEditDropDown);
                for (int i = 0; i < myTask.reasonDropDownVlu.size(); i++) {
                    assertEquals(myTask.reasonDropDownVlu.get(i).getText(), str2[i]);
                }
                break;
        }
        staticWait(500);
        List<WebElement> listElements = Driver.getDriver().findElements(By.xpath("//li[@tabindex]"));
        WebElement single = null;
        single = listElements.get(1);
        scrollToElement(single);
        hover(single);
        single.click();
        waitFor(1);
    }

    @And("I select value in {string} drop down")
    public void selectValueInReasonDropDown(String field) {
        switch (field) {
            case "REASON FOR HOLD":
                waitForVisibility(myTask.reasonForHoldDropDown, 10);
                selectDropDown(myTask.reasonForHoldDropDown, "Application On Hold");
                break;

            case "REASON FOR CANCEL":
                waitForVisibility(myTask.reasonForCancelDropDown, 10);
                selectDropDown(myTask.reasonForCancelDropDown, "Created Incorrectly");
                CRM_GeneralTaskStepDef.taskValues.get().put("reasonForCancel", "Created Incorrectly");
                break;

            case "REASON FOR EDIT":
                if (myTask.reasonForEditDropDown.getText().isEmpty())
                    selectFromMultiSelectDropDownForWithEscapedKey(myTask.reasonForEditDropDown, "Corrected Data Entry");
                break;
        }
    }

    @And("I click on save button on task edit page")
    public void clickOnSaveButton() {
        scrollDownUsingActions(1);
        CRM_GeneralTaskStepDef.taskValues.get().put("dueDate", createGeneralTask.lblDueDate.getText());
        jsClick(myTask.saveBtn);
        if (Integer.parseInt(dashboard.projectDateAtHeader.getText().replace("\n", " ").split(" ")[1].split(":")[0]) < 10)
            CRM_EditTaskStepDef.editHistory.get().put("editOn", dashboard.projectDateAtHeader.getText().replace("\n", " 0"));
        else
            CRM_EditTaskStepDef.editHistory.get().put("editOn", dashboard.projectDateAtHeader.getText().replace("\n", " "));
        waitFor(3);
    }

    @Then("I verify status is changed as {string} and status date is updated")
    public void verifyStatusOnViewPage(String status) {
        waitFor(2);
        waitForVisibility(myTask.statusValue, 10);
        assertEquals(myTask.statusValue.getText(), status, "Status is not updated in view page");
        assertEquals(myTask.statusDateValue.getText(), getCurrentDate(), "Status date is not updated");
    }

    @Then("I verify should I navigated to view task page")
    public void verifyShouldINavigatedToViewTaskPage() {
        waitForVisibility(myTask.btnEditTask, 10);
        assertTrue(myTask.btnEditTask.isDisplayed());
    }

    @Then("I verify Due In field become blank")
    public void verifyDueInFieldBecomeBlank() {
        Assert.assertEquals(myTask.lblDueIn.getText(), "-- --");
    }

    @And("I navigate to view task just assign before on My task queue")
    public void navigateToViewTaskJustAssignBefore() {
        waitFor(1);
        myTask.backArrowInViewPage.click();
        myTask.myTasksTab.click();
        waitFor(5);
        int size = 1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        l1:
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < myTask.taskIDs.size(); i++) {
                waitFor(1);
                if (myTask.taskIDs.get(i).getText().equals(CRM_WorkQueueStepDef.taskId.get())) {
                    taskId.set(myTask.taskIDs.get(i).getText());
                    myTask.taskIDs.get(i).click();
                    break l1;
                }
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(2);
            }
        }
    }

    @Then("I verify status is as {string}")
    public void verifyStatusIsAsOnHold(String status) {
        String xPath = "//b[text()='" + taskId.get() + "']//following::p[text()='" + status + "']";
        sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath(xPath)))).as("task id with " + status + " +is not displayed").isTrue();
    }

    @And("I verify Complete or Cancelled task is NOT in My Tasks queue")
    public void verifyCancelledTaskIsNotInMyTasksQueue() {
        waitFor(5);
        int size = myTask.lnkPageNations.size();
        boolean flag = true;
        if (myTask.lnkPageNations.size() > 1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        l1:
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < myTask.getStatusValues.size(); i++) {
                waitFor(1);
                if (myTask.taskIDs.get(i).getText().equals(taskId.get())) {
                    flag = false;
                    break l1;
                }
                assertFalse(myTask.getStatusValues.get(i).getText().equalsIgnoreCase("Complete") &&
                        myTask.getStatusValues.get(i).getText().equalsIgnoreCase("Cancelled"));
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(flag, "Cancelled status task is still exist in my task queue");
    }

    @Then("I verify Task is successfully edited")
    public void verifyTaskIsSuccessfullyEdited() {
        waitForVisibility(myTask.successMessage, 10);
        assertTrue(myTask.successMessage.isDisplayed());
    }

    @And("I ensure My task page has at least one task with type {string} and I navigate to view task")
    public void ensureAtLeastOneTaskWithTypeGeneral(String type) {
        waitFor(1);
        int size = 1;
        boolean recordFound = false;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(2);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < myTask.taskTypes.size(); i++) {
                waitFor(1);
                if (myTask.taskTypes.get(i).getText().equalsIgnoreCase(type)) {
                    taskId.set(myTask.taskIDs.get(i).getText());
                    taskType.set(myTask.taskTypes.get(i).getText());
                    waitFor(1);
                    myTask.taskIDs.get(i).click();
                    waitFor(2);
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
            else if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @Then("I should not able see edit option to edit the {string}")
    public void editButtonShouldNotBeDisplayed(String taskType) {
        try {
            if (taskType.equals("task")) {
                Assert.assertFalse(myTask.editBtn.isDisplayed());
            } else {
                Assert.assertFalse(trsTabSRPage.btnEditSR.isDisplayed());
            }
        } catch (NoSuchElementException ele) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertFalse(false, "Element is identified by selenium");
        }
    }

    @And("I select the assignee as {string} in my task edit page")
    public void selectAssigneeInEditPage(String assignee) {
        clearAndFillText(myTask.assigneeDropDown, assignee);
        waitFor(3);
    }

    public void verifyColorInMyTaskPage() {
        waitFor(1);
        int size = 1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < myTask.taskListRows.size(); j++) {
                waitFor(1);
                if (!myTask.taskStatuses.get(j).getText().equals("Complete") && myTask.taskDueIns.get(j).getText().contains("-")) {
                    assertEquals(getColorCode(myTask.taskListRows.get(j)), "#ffebee", "Task not highlighted in red color");
                }
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @Then("I verify Task on the list are highlighted whose Due Date is past and not completed")
    public void validatePastDueTaskHighlightedMode() {
        if (myTask.taskIDs.size() != 0) {
            verifyColorInMyTaskPage();
        }
        else throw new IllegalArgumentException("My task does not contains tasks");
    }

    @Then("I click on save in Task Slider")
    public void clickOnSaveTaskSlider() {
        waitForVisibility(myTask.taskSilderSave, 10);
        waitFor(1);
        if (!myTask.taskSilderTaskNotes.getText().isEmpty()) {
            CRM_GeneralTaskStepDef.taskValues.get().put("createOn", dashboard.projectDateAtHeader.getText());
            CRM_GeneralTaskStepDef.taskValues.get().put("createdBy", createGeneralTask.userAccountName.getText());
        }
        actions.click(myTask.taskSilderSave).build().perform();
    }

    @Then("I verify navigate back to My Task or Work Queue page")
    public void i_verify_to_navigate_back_to_My_Task_Work_Queue_page() {
        assertTrue(myTask.taskListPage.isDisplayed(), "Task list page displayed");
        waitFor(1);
    }

    @When("I click on the priority in dashboard")
    public void clickPriority() {
        waitFor(3);
        waitForPageToLoad(10);
        waitForVisibility(myTask.priorityDashboard, 10);
        myTask.priorityDashboard.click();
    }

    @Then("I verify the error message on working single active task")
    public void i_verify_the_error_single_active_task() {
        waitForVisibility(myTask.initsecondTaskErrorMsg, 10);
        String errorSingle = myTask.initsecondTaskErrorMsg.getText();
        assertTrue(myTask.initsecondTaskErrorMsg.isDisplayed(), "Error message pop not displayed");
        assertTrue(errorSingle.contains("You cannot have more than one task"), "Error Message is not displayed or incorrect");
    }

    @When("I Verify fields on the Task slider")
    public void i_verify_the_fields_on_the_task_slider() {
        sa.get().assertThat(myTask.taskSilderTaskNotes.isDisplayed()).as("Task Notes field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderStatus.isDisplayed()).as("Task Status field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderSource.isDisplayed()).as("Source field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderCreatedBy.isDisplayed()).as("Created By field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderCreatedOn.isDisplayed()).as("Created date field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderStatusOn.isDisplayed()).as("Status Date field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderCaseId.isDisplayed()).as("Case field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderConsumerID.isDisplayed()).as("Status Date field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderTaskType.isDisplayed()).as("Task Type field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderAssignee.isDisplayed()).as("Assignee field is not not displayed in the Task Slider").isTrue();
        sa.get().assertThat(myTask.taskSilderPriority.isDisplayed()).as("Priority field is not not displayed in the Task Slider").isTrue();
    }

    @When("I verify the field values on the Task slider")
    public void i_verify_the_fields_values_on_the_task_slider() {
        waitForVisibility(myTask.taskSilderSource, 10);
        sa.get().assertThat(myTask.taskSilderSource.getText()).as("Source field value is not displayed in the Task Slider").isNotBlank();
        sa.get().assertThat(myTask.taskSilderTaskType.getText()).as("Task Type field value is not displayed in the Task Slider").isNotBlank();
        sa.get().assertThat(myTask.taskSilderStatus.getText()).as("Status field value is not displayed in the Task Slider").isNotBlank();
        sa.get().assertThat(myTask.taskSilderCreatedBy.getText()).as("Created by field value is not displayed in the Task Slider").isNotBlank();
        sa.get().assertThat(myTask.taskSilderCreatedOn.getText()).as("Created on field value is not displayed in the Task Slider").isNotBlank();
        sa.get().assertThat(myTask.taskSilderPriority.getText()).as("Priority field value is not displayed in the Task Slider").isNotBlank();
        sa.get().assertThat(myTask.taskSilderDueDate.getText()).as("Due Date field value is not displayed in the Task Slider").isNotBlank();
        sa.get().assertThat(myTask.taskSilderDueIn.getText()).as("Due In field value is not displayed in the Task Slider").isNotBlank();
    }

    @When("I Verify Task slider is collasped")
    public void verify_task_slider_is_collasped() {
        try {
            waitFor(2);
        } catch (Exception e) {
            assertFalse(myTask.taskSliderdetailView.isDisplayed(), "Task Slider is displayed on collasped mode");

        }
    }

    @When("I Verify Task slider is Expand")
    public void verify_task_slider_is_expand() {
        waitFor(5);
        assertTrue(myTask.taskSliderdetailView.isDisplayed(), "Task Slider is not displayed");
    }

    @When("I click on initiate randomly")
    public void clickInitiate() {
        taskId.set(workQueue.allRowTaskID.get(0).getText());
        expectedData.get().add(taskId.get());
        expectedData.get().add(myTask.priorities.get(0).getText());
        expectedData.get().add(myTask.taskDueIns.get(0).getText());
        waitForVisibility(myTask.initiateLink, 10);
        actions.click(myTask.initiateLink).build().perform();
        taskStatus.set("Open");
        CRM_GeneralTaskStepDef.taskValues.get().put("assignee", contactRecord.headerUsername.getText());
    }

    @Then("I verify task save success message")
    public void verifyTaskSaveSuccessMessage() {
        waitForVisibility(myTask.successMessage, 10);
        String successMsg = myTask.successMessage.getText();
        assertEquals(successMsg, "SUCCESS MESSAGE", "Task is not saved");
    }

    @Then("I verify the priority in dashboard")
    public void priorityDashboard() {
        waitForPageToLoad(10);
        waitForVisibility(myTask.priorityDashboard, 10);

        List<String> actualData = new ArrayList<String>();
        actualData.add(myTask.dashboardTaskID.getText());
        actualData.add(myTask.dashboardPriority.getText());
        String subDDueIn1 = myTask.dashboardDueIn.getText();
        actualData.add(subDDueIn1.replace("DAYS", "Days"));
        assertEquals(expectedData.get(), actualData, "Secondary Header is not matching ");
    }

    @When("I update the task status in task slider as {string}")
    public void i_update_the_task_status_in_task_slider_as(String status) {
        waitForVisibility(myTask.statusDropdown, 500);
        myTask.statusDropdown.click();
        waitFor(2);
        actions.moveToElement(Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + status + "')]"))).click().build().perform();
        waitFor(1);
        assertTrue(myTask.statusDropdown.getText().contains(status), "Selector " + status + " - wasn't selected");
        taskStatus.set(status);
        if (status.equalsIgnoreCase("Cancel"))
            CRM_GeneralTaskStepDef.taskValues.get().put("status", "Cancelled");
        else
            CRM_GeneralTaskStepDef.taskValues.get().put("status", status);
        if (status.equalsIgnoreCase("Cancel") || status.equalsIgnoreCase("Complete")) {
            CRM_TaskManagementStepDef.dueIn.set( CRM_GeneralTaskStepDef.taskValues.get().get("dueIn"));
            CRM_GeneralTaskStepDef.taskValues.get().put("dueIn", "-- --");
        }

    }

    @Then("I check first record task status is {string}")
    public void verifyFirstRecordTaskStatus(String status) {
        for (int i = 0; i < myTask.taskIDs.size(); i++) {
            waitFor(2);
            if (taskId.get().equalsIgnoreCase(myTask.taskIDs.get(i).getText())) {
                Assert.assertEquals(myTask.getStatusValues.get(i).getText().toLowerCase(), status.toLowerCase(), "Status is miss match");
                break;
            }
        }
    }

    @When("I enter text in task note field")
    public void enterTaskDetails() {
        taskNotesEntered.set(getRandomString(7));
        CRM_GeneralTaskStepDef.taskValues.get().put("noteValue", taskNotesEntered.get());
        clearAndFillText(myTask.taskSilderTaskNotes, taskNotesEntered.get());
    }

    @Then("I verify Task Notes are saved for the task")
    public void verifyTaskNoteOfFirstRecordinMyTaskPage() {
        for (int i = 0; i < myTask.taskIDs.size(); i++) {
            waitFor(2);
            if (taskId.get().equalsIgnoreCase(myTask.taskIDs.get(i).getText())) {
                CRM_WorkQueueStepDef.position.set( i);
                myTask.expandTaskArrows.get(i).click();
                waitFor(1);
                assertEquals(myTask.taskNoteValues.get(i).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"), "Task note is not invalid");
                break;
            }
        }
    }

    @Then("I verify Assignee is unchanged")
    public void verifyAssigneeIsUnchanged() {
        assertEquals(myTask.lblAssignee.getText(), createGeneralTask.userAccountName.getText(),
                "Task slider assignee is mismatched");
    }

    @Then("I Verify user is navigate back to My task page")
    public void verifyUserIsNavigateBackToMyTaskPage() {
        assertTrue(myTask.myTasksTab.isDisplayed(), "User is not able to navigate back to My task page");
    }

    @And("I will ensure assignee contains at least one {string} task if not I will create task")
    public void checkWhetherHasTaskIfNotAddTAsk(String taskName) {
        waitFor(1);
        boolean recordFound = false;
        assignee.set( myTask.userAccountName.getText());
        int size = myTask.lnkPageNations.size();
        waitFor(5);
        if (workQueue.allRowTaskID.size() == 0 && workQueue.workQueueColumns.size() == 0) {
            if (isElementDisplayed(workQueue.noRecord)) {
                generalTaskStepDef.navigateToGeneralTask(taskName);
                generalTaskStepDef.SelectTaskDetails("", "", assignee.get(), "");
                generalTaskStepDef.clickSaveButton();
                generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
                waitFor(2);
            }
        } else if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (myTask.lnkPageNations.size() > 1 && isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(1);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < myTask.taskListRows.size(); j++) {
                    waitFor(1);
                    if (myTask.taskTypes.get(j).getText().equalsIgnoreCase(taskName)) {
                        recordFound = true;
                        break;
                    }
                }
                if (recordFound)
                    break;
                else if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }

            }
            if (!recordFound) {
                generalTaskStepDef.navigateToGeneralTask(taskName);
                assignee.set( myTask.userAccountName.getText());
                generalTaskStepDef.SelectTaskDetails("", "", assignee.get(), "");
                generalTaskStepDef.clickSaveButton();
                generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
                waitFor(2);
            }

        }
    }

    @When("I click on cancel button on task slider")
    public void clickCancelTaskSlider() {
        waitFor(1);
        click(myTask.taskSilderCancel);
    }

    @When("I verify warning is displayed upon clicking Cancel button on Task slider")
    public void verifyWarringMsg() {
        waitForVisibility(myTask.cancelWarningPopup, 10);
        assertTrue(myTask.cancelWarningPopup.isDisplayed(), "Warning message is not displayed");
        assertTrue(myTask.cancelWarningCancel.isDisplayed());
        assertTrue(myTask.cancelWarningContinue.isDisplayed());
    }

    @When("I enter task notes {string} and select task status as {string}")
    public void enterTaskDetails(String notes, String status) {
        clearAndFillText(myTask.taskSilderTaskNotes, notes);
        selectDropDown(myTask.lstTaskStatus, status);
    }

    @When("I verify user remains on same page and the data captured will not be cleared")
    public void verifyUserRemainsOnSamePage() {
        assertEquals(myTask.txtTaskNotes.getText(), "test Notes");
        assertEquals(myTask.lstTaskStatus.getText(), "Complete");
    }

    @When("I click on continue on task details warning window")
    public void clickContinue() {
        click(myTask.btnContinueTaskWarning);
    }

    @When("I verify user navigate back to previous UI and no updates will be saved")
    public void verifyUserNavigateBackToPreviousUIPage() {
        waitFor(2);
        boolean recordFound = false;
        for (int i = 0; i < myTask.taskIDs.size(); i++) {
            if (myTask.taskIDs.get(i).getText().equals(taskId.get())) {
                assertTrue(myTask.myTasksTab.isDisplayed(), "User is not able to navigate back to My task page");
                try {
                    assertNotEquals(myTask.getStatusValues.get(0).getText(), "Complete");
                } catch (StaleElementReferenceException e) {
                    assertNotEquals(Driver.getDriver().findElements(By.xpath("//th[text()='STATUS']/../../following-sibling::tbody//td[6]//p")).get(0).getText(), "Complete");
                }
                click(workQueue.expandArrow);
                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound, "Record not found");
    }

    @When("I will verify all record status are not {string}")
    public void verifyStatus(String status) {
        waitForVisibility(workQueue.allRowStatus.get(0), 10);
        boolean flag = true;
        l1:
        for (int j = 0; j < myTask.lnkPageNations.size(); j++) {
            waitFor(2);
            for (int i = 0; i < workQueue.allRowStatus.size(); i++) {
                if (workQueue.allRowStatus.get(i).getText().equalsIgnoreCase(status)) {
                    flag = false;
                    break l1;
                }
            }
            if (j + 1 < myTask.lnkPageNations.size())
                myTask.lnkPageNations.get(j + 1).click();
            waitFor(3);
        }
        assertTrue(flag, "User role has Escalated permission");
    }

    @When("I will verify Escalated task is sits in work queue page")
    public void verifyUserHasEscalatedPermission() {
        waitFor(1);
        waitForVisibility(workQueue.allRowStatus.get(0), 10);
        int size = 10;
        boolean flag = true;
        l1:
        for (int j = 0; j <= size; j++) {
            for (int i = 0; i < workQueue.allRowTaskID.size(); i++) {
                waitFor(1);
                if (workQueue.allRowTaskID.get(i).getText().equals(taskId.get())) {
                    flag = false;
                    assertEquals(workQueue.allRowStatus.get(i).getText(), "Escalated");
                    break l1;
                }
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        assertFalse(flag, "task escalated is not sits in work queue page");
    }

    @Then("I verify Priority is displayed at First Glance")
    public void i_verify_Priority_is_displayed_at_First_Glance() {
        assertTrue(myTask.priorityColumn.isDisplayed(), "Priority column is not displayed");
        for (int k = 0; k < myTask.priorities.size(); k++) {
            assertNotEquals(myTask.priorities.get(k), null, "Priorities should not be null");
        }
    }

    @When("I click on {string} column on My Tasks Page to set to descending")
    public void i_click_on_column_on_My_Tasks_Page_to_set_to_descending(String columnName) {
        waitFor(2);
        switch (columnName) {
            case "TaskId":
                myTask.taskIdColumn.click();
                break;
            case "DueDate":
                myTask.dueDateColumn.click();
                break;
            case "Priority":
                myTask.priorityColumn.click();
                break;
            case "StatusDate":
                myTask.statusDateSearchPage.click();
                break;
            case "CaseId":
                myTask.caseIdColumn.click();
                break;
            case "ConsumerId":
                myTask.consumerIdColumn.click();
                break;
        }
        waitFor(1);
    }

    @When("I verify the status drop down value of task with {string} status")
    public void verifyStatusValue(String status, List<String> options) {
        ArrayList<String> actual = new ArrayList<>(options);
        if (flag.get()) {
            ArrayList<String> value = new ArrayList<>();

            click(myTask.editPageStatusDropDown);
            assertTrue(myTask.taskStatusVlu.size() > 0, "Drop down does not have any value");

            for (int i = 0; i < myTask.taskStatusVlu.size(); i++) {
                value.add(myTask.taskStatusVlu.get(i).getText());
            }

            assertEquals(value, actual, "Status drop down values are mismatch");

            switch (status) {
                case "Created":
                    assertFalse(value.contains("Open"), "Status drop down contains Open value");
                    assertFalse(value.contains("In-Progress"), "Status drop down contains In-Progress value");
                    assertFalse(value.contains("Reassigned"), "Status drop down contains Reassigned value");
                    Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), 'Cancelled')]")).click();
                    break;

                case "Open":
                    assertFalse(value.contains("Created"), "Status drop down contains Created value");
                    assertFalse(value.contains("In-Progress"), "Status drop down contains In-Progress value");
                    assertFalse(value.contains("Reassigned"), "Status drop down contains Reassigned value");
                    Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), 'Complete')]")).click();
                    break;

                case "In-Progress":
                    assertFalse(value.contains("Open"), "Status drop down contains Open value");
                    assertFalse(value.contains("Created"), "Status drop down contains Created value");
                    assertFalse(value.contains("Reassigned"), "Status drop down contains Reassigned value");
                    Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), 'OnHold')]")).click();
                    break;

                default:
                    assertFalse(value.contains("Created"), "Status drop down contains Created value");
                    assertFalse(value.contains("In-Progress"), "Status drop down contains In-Progress value");
                    assertFalse(value.contains("Open"), "Status drop down contains Open value");
                    assertFalse(value.contains("Reassigned"), "Status drop down contains Reassigned value");
                    break;
            }
            waitFor(3);
        }
    }

    @And("I ensure My task page has at least one task with status {string} and I navigate to view task")
    public void ensureAtLeastOneTaskWithStatus(String status) {
        boolean flag = true;
        if (myTask.taskIDs.size() == 0) {
            generalTaskStepDef.navigateToGeneralTask("General");
            generalTaskStepDef.SelectTaskDetails("", "", "Service", "");
            generalTaskStepDef.clickSaveButton();
            myTask.taskIdColumn.click();
            taskId.set(myTask.taskIDs.get(0).getText());
            myTask.taskIDs.get(0).click();
        }
        l1:
        for (int j = 0; j < myTask.lnkPageNations.size(); j++) {
            for (int i = 0; i < myTask.getStatusValues.size(); i++) {
                waitFor(2);
                if (myTask.getStatusValues.get(i).getText().equalsIgnoreCase(status)) {
                    flag = false;
                    this.flag.set(true);
                    taskId.set(myTask.taskIDs.get(i).getText());
                    myTask.taskIDs.get(i).click();
                    break l1;
                }
            }
            if (j + 1 < myTask.lnkPageNations.size())
                myTask.lnkPageNations.get(j + 1).click();
            waitFor(3);
        }
        if (flag) {
            try {
                myTask.lnkArrowBack.click();
            } catch (StaleElementReferenceException e) {
                Driver.getDriver().findElement(By.xpath("//span[text()='arrow_back']/parent::a")).click();
            } catch (NoSuchElementException e) {
                Driver.getDriver().findElement(By.xpath("//a[text()='1']")).click();
            }
            waitFor(1);

            switch (status) {
                case "Open":
                    clickInitiate();
                    clickOnSaveTaskSlider();
                    taskId.set(myTask.taskIDs.get(0).getText());
                    myTask.taskIDs.get(0).click();
                    this.flag.set(true);
                    break;

                case "Onhold":
                    taskId.set(myTask.taskIDs.get(0).getText());
                    myTask.taskIDs.get(0).click();
                    clickOnEditButton();
                    changeTheStatusOnEditPage("OnHold");
                    selectValueInReasonDropDown("REASON FOR HOLD");
                    clickOnSaveButton();
                    verifyShouldINavigatedToViewTaskPage();
                    this.flag.set(true);
                    break;

                case "Created":
                    generalTaskStepDef.navigateToGeneralTask("General");
                    generalTaskStepDef.SelectTaskDetails("", "", "Service", "");
                    generalTaskStepDef.clickSaveButton();
                    myTask.taskIdColumn.click();
                    taskId.set(myTask.taskIDs.get(0).getText());
                    myTask.taskIDs.get(0).click();
                    this.flag.set(true);
                    break;
            }
        }
    }

    @And("I ensure it has at least one task with status {string} and I navigate to view task")
    public void ensureAtLeastOneTaskWithStatusInTSRTab(String status) {
        boolean flag = true;
        scrollDown();
        waitFor(2);
        scrollUpRobot();
        waitFor(2);
        if (myTask.taskIDs.size() == 0) {
            generalTaskStepDef.navigateToGeneralTask("General");
            generalTaskStepDef.SelectTaskDetails("", "", "", "");
            generalTaskStepDef.clickSaveButton();
            myTask.taskIdColumn.click();
            taskId.set(myTask.taskIDs.get(0).getText());
            myTask.taskIDs.get(0).click();
        }
        l1:
        for (int j = 0; j < myTask.lnkPageNations.size(); j++) {
            for (int i = 0; i < taskSRTabPage.taskStatuses.size(); i++) {
                if (taskSRTabPage.taskStatuses.get(i).getText().equalsIgnoreCase(status)) {
                    flag = false;
                    this.flag.set(true);
                    taskId.set( taskSRTabPage.taskIDs.get(i).getText());
                    taskSRTabPage.taskIDs.get(i).click();
                    break l1;
                }
            }
            if (j + 1 < myTask.lnkPageNations.size())
                myTask.lnkPageNations.get(j + 1).click();
        }

        if (flag && !status.equals("In-Progress")) {
            switch (status) {
                case "Complete":
                    taskId.set(taskSRTabPage.taskIDs.get(0).getText());
                    taskSRTabPage.taskIDs.get(0).click();
                    clickOnEditButton();
                    changeTheStatusOnEditPage("Complete");
                    selectValueInReasonDropDown("REASON FOR EDIT");
                    clickOnSaveButton();
                    verifyShouldINavigatedToViewTaskPage();
                    this.flag.set(true);
                    break;
                case "Cancelled":
                    taskId.set(taskSRTabPage.taskIDs.get(0).getText());
                    taskSRTabPage.taskIDs.get(0).click();
                    clickOnEditButton();
                    changeTheStatusOnEditPage("Cancelled");
                    selectValueInReasonDropDown("REASON FOR CANCEL");
                    clickOnSaveButton();
                    verifyShouldINavigatedToViewTaskPage();
                    this.flag.set(true);
                    break;
            }

        }
    }

    @When("I navigate to {string} page")
    public void i_navigate_to_task_Managments_page(String pageName) {
        waitFor(5);
        dashboard.taskManagementTab.click();
        waitFor(5);
        dashboard.expandTaskManagementSideTab.click();
        pageNameCache.set(pageName);
        waitFor(5);
        switch (pageName) {
            case "My Task":
                waitFor(5);
                dashboard.myTaskWorkQueueSideTab.click();
                waitFor(3);
                hover(myTask.workQueueTab);
                try {
                    myTask.workQueueTab.isDisplayed();
                } catch (NoSuchElementException e) {
                    dashboard.myTaskWorkQueueSideTab.click();
                    waitFor(2);
                    hover(myTask.workQueueTab);
                }
                break;

            case "Work Queue":
                waitFor(4);
                dashboard.myTaskWorkQueueSideTab.click();
                waitFor(5);
                myTask.workQueueTab.click();
                waitFor(5);
                hover(myTask.workQueueTab);
                try {
                    myTask.workQueueTab.isDisplayed();
                } catch (NoSuchElementException e) {
                    dashboard.myTaskWorkQueueSideTab.click();
                    waitFor(2);
                    jsClick(myTask.workQueueTab);
                    waitFor(1);
                    hover(myTask.workQueueTab);
                }
                break;

            case "Task Search":
                waitFor(5);
                dashboard.taskSearchSideTab.click();
                waitFor(5);
                hover(taskSearchPage.statusDate);
                waitFor(5);
                hover(taskSearchPage.dueDate);
                try {
                    taskSearchPage.taskId.isDisplayed();
                } catch (NoSuchElementException e) {
                    dashboard.taskSearchSideTab.click();
                    waitFor(2);
                    hover(taskSearchPage.statusDate);
                }
                break;

            case "Manage Queue Filter":
                dashboard.manageQueueFilterTab.click();
                waitFor(2);
                hover(taskQueueFilter.createFilterBt);
                try {
                    taskQueueFilter.createFilterBt.isDisplayed();
                } catch (NoSuchElementException e) {
                    dashboard.manageQueueFilterTab.click();
                    waitFor(2);
                    hover(taskQueueFilter.createFilterBt);
                }
                break;

            case "Saved Task Search":
                dashboard.savedTaskSearchSideTab.click();
                try {
                    waitFor(2);
                    hover(savedTaskSrchPage.deleteBtn.get(0));
                } catch (Exception e) {
                    dashboard.savedTaskSearchSideTab.click();
                }
                break;
        }
        waitFor(4);
    }

    @And("I make sure assignee contains at least one {string} task if not I will create task")
    public void checkAssigneeHasTaskIfNotAddTAsk(String taskName) {
        waitFor(1);
        boolean recordFound = false;
        assignee.set( myTask.userAccountName.getText());
        int size = 1;
        waitFor(3);

        if (workQueue.allRowTaskID.size() == 0 && workQueue.workQueueColumns.size() == 0) {
            if (isElementDisplayed(workQueue.noRecord)) {
                generalTaskStepDef.navigateToGeneralTask(taskName);
                generalTaskStepDef.SelectTaskDetails("", "", assignee.get(), "");
                generalTaskStepDef.clickSaveButton();
//                generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
                waitFor(1);
            }
        } else if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(2);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < myTask.taskTypes.size(); j++) {
                    waitFor(1);
                    if (myTask.taskTypes.get(j).getText().equalsIgnoreCase(taskName)) {
                        recordFound = true;
                        break;
                    }
                }
                if (recordFound)
                    break;
                else if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }

            }

            if (!recordFound) {
                generalTaskStepDef.navigateToGeneralTask(taskName);
                assignee.set( myTask.userAccountName.getText());
                generalTaskStepDef.SelectTaskDetails("", "", assignee.get(), "");
                generalTaskStepDef.clickSaveButton();
//                generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
                waitFor(3);
            }

        }
        if (isElementDisplayed(myTask.lnkArrowBack)) {
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
    }

    @Then("I verify the field values on View Task details")
    public void verifyFieldValuesOnTaskDetails() {
        waitFor(13);
        sa.get().assertThat(myTask.taskID.getText()).as("Task ID field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.source.getText()).as("Source field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.taskCreatedBy.getText()).as("Created BY field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.taskCreatedOn.getText()).as("Created ON field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.statusDate.getText()).as("Status date field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.dueDate.getText()).as("Due Date field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.dueIn.getText()).as("Due In field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.taskType.getText()).as("Task Type field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.priority.getText()).as("Priority field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.status.getText()).as("Status field value is not displayed in the Task Details").isNotBlank();
        sa.get().assertThat(myTask.assignee.getText()).as("Assignee field validation").isNotBlank();
        sa.get().assertThat(myTask.taskInformation.isEnabled()).as("Task Info field is enabled").isTrue();
        //sa.get().assertThat(myTask.taskNotes.isEnabled()).as("Task notes field is enabled").isTrue();
    }

    @When("I click on initiate button")
    public void clickInitiateButton() {
        taskId.set(workQueue.allRowTaskID.get(0).getText());
        scrollDownUsingActions(2);
        waitFor(2);
        initate = Driver.getDriver().findElement(By.xpath("//button[text()='INITIATE']"));
        click(initate);
        waitFor(1);
    }


    @And("I create {string} task with priority {string} assignee as {string} and task info as {string} and status as {string}")
    public void createTaskWithStatus(String task_type, String priority, String assignee, String task_info, String status) {
        System.out.println(assignee);
        CRM_GeneralTaskStepDef crm_generalTaskStepDef = new CRM_GeneralTaskStepDef();
        crm_generalTaskStepDef.navigateToGeneralTask(task_type);
        crm_generalTaskStepDef.SelectTaskDetailsWithStatus(task_type, priority, assignee, task_info, status);
        waitFor(5);
        crm_generalTaskStepDef.clickSaveButton();
        crm_generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
        if (assignee.isEmpty()) {
            this.assignee.set( "-- --");
            CRM_GeneralTaskStepDef.taskValues.get().put("assignee", "-- --");
        } else {
            this.assignee.set( assignee);
            CRM_GeneralTaskStepDef.taskValues.get().put("assignee", assignee);
        }

        CRM_GeneralTaskStepDef.taskValues.get().put("created_by", "Service AccountOne");
        this.created_by.set( "Service AccountOne");
        CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", task_info);
        this.taskInfo.set( task_info);
        this.source.set( "User");
        CRM_GeneralTaskStepDef.taskValues.get().put("created_on", getCurrentDate());
        this.created_on.set( getCurrentDate());
        this.status_date.set( getCurrentDate());
        this.case_ID.set( "-- --");
        waitFor(3);
    }


    @Then("I will verify {string} status task not present in Task list")
    public void i_will_verify_cancelled_or_complet_status_task_not_present(String taskStatus) {
        waitFor(3);
        int size = 1;
        if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(1);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < myTask.taskListRows.size(); j++) {
                    assertNotEquals(taskStatus, myTask.taskStatuses.get(j).getText(), "Task Status matches");
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
    }

    @Then("I Verify {string} task with {string} status does not have Initiate button")
    public void verifyTaskDoesNotHaveInitiateButton(String taskTypeExpected, String taskStatusExpected) {
        waitFor(4);
        boolean recordFound = false;
        int size = 1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < myTask.taskListRows.size(); j++) {
                waitFor(3);
                if (myTask.taskTypes.get(j).getText().equals(taskTypeExpected) && myTask.taskStatuses.get(j).getText().equals(taskStatusExpected)) {
                    assertTrue(myTask.taskInitiateButtonsText.get(j).getText().isEmpty(), "Initiate button displayed");
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
            else if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        if (!recordFound)
            fail("Task Type and status not matches");
        if (isElementDisplayed(myTask.lnkArrowBack))
            click(myTask.lnkArrowBack);
    }

    @Then("I Verify {string} task with {string} status does have Initiate button")
    public void verifyTaskDoesHaveInitiateButton(String taskTypeExpected, String taskStatusExpected) {
        waitFor(2);
        boolean recordFound = false;
        int size = 1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < myTask.taskTypes.size(); j++) {
                waitFor(1);
                if (myTask.taskTypes.get(j).getText().equals(taskTypeExpected) && myTask.taskStatuses.get(j).getText().equals(taskStatusExpected)) {
                    assertTrue(isElementDisplayed(myTask.taskInitiateButtons.get(j)), "Initiate button not displayed");
                    this.taskId.set(myTask.taskIDs.get(j).getText());
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
            else if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        if (!recordFound)
            fail(" Task type not matches");
        waitFor(2);
        if (isElementDisplayed(myTask.lnkArrowBack))
            click(myTask.lnkArrowBack);
    }

    @And("I change task status to {string}")
    public void changeTaskStatus(String status) {
        boolean flag = true;
        switch (status) {
            case "Open":
                taskId.set(myTask.taskIDs.get(0).getText());
                clickInitiate();
                clickOnSaveTaskSlider();
                waitFor(2);
                this.flag.set(true);
                break;

            case "OnHold":
                taskId.set(myTask.taskIDs.get(0).getText());
                myTask.taskIDs.get(0).click();
                clickOnEditButton();
                changeTheStatusOnEditPage("OnHold");
                selectValueInReasonDropDown("REASON FOR HOLD");
                clickOnSaveButton();
                verifyShouldINavigatedToViewTaskPage();
                i_am_able_to_navigate_back_to_My_Task_Work_Queue_page();
                this.flag.set(true);
                break;

            case "Created":
                generalTaskStepDef.navigateToGeneralTask("General");
                generalTaskStepDef.SelectTaskDetails("", "", "Service", "");
                generalTaskStepDef.clickSaveButton();
                myTask.taskIdColumn.click();
                taskId.set(myTask.taskIDs.get(0).getText());
                myTask.taskIDs.get(0).click();
                this.flag.set(true);
                break;
        }
    }

    @Then("I verify assignee is the user who logged in {string}")
    public void i_verify_assignee_is_the_user_who_logged_in(String assigneeExpected) {
        waitFor(3);
        int size = 1;
        if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(1);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < myTask.taskListTabletbodies.size(); j++) {
                    click(myTask.expandTaskArrows.get(j));
                    waitFor(1);
                    assertEquals(assigneeExpected, myTask.taskListTabletbodies.get(j).findElement(By.xpath("//tr[2]/td//p[text()='ASSIGNEE']/following-sibling::p")).getText(), "Assignee is not the user whom Login");
                    click(taskSearchPage.arrowDownKey);
                    waitFor(1);
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
    }

    @Then("I verify the task details are displayed in my task when expanded")
    public void verifyTaskDetailsWhenExpanded() {
        waitFor(3);

        sa.get().assertThat(workQueue.createdByVlu.get(CRM_WorkQueueStepDef.position.get()).getText()).as("createdBy value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
        if (CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo") == null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").isEmpty()) {
            sa.get().assertThat(workQueue.taskInfoVlu.get(CRM_WorkQueueStepDef.position.get()).getText()).as("Task Information is mismatch").isEqualTo("-- --");
        } else {
            sa.get().assertThat(workQueue.taskInfoVlu.get(CRM_WorkQueueStepDef.position.get()).getText()).as("Task Information is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
        }
        sa.get().assertThat(workQueue.sourceVlu.get(CRM_WorkQueueStepDef.position.get()).getText()).as("Source value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("source"));
        sa.get().assertThat(workQueue.createdOnVlu.get(CRM_WorkQueueStepDef.position.get()).getText()).as("CreatedOn is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_on"));
        sa.get().assertThat(workQueue.assigneeVlu.get(CRM_WorkQueueStepDef.position.get()).getText()).as("Assignee value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
        //sa.get().assertThat(workQueue.taskNoteVlu.get(CRM_WorkQueueStepDef.position.get()).getText()).as("Task Note is mismatch").isEqualTo("-- --");
    }


    @When("I will ensure assignee contains at least one {string} task if not I will create task priority as {string} taskStatus as {string} assignee as {string} task info as {string} complaintAbout as {string} name as {string} reason as {string} contactReason as {string} prefCallBackDate as {string} prefCallBackTime as {string} prefPhone as {string} extCaseID as {string} appID as {string}")
    public void checkWhetherHasTaskIfNotAddTaskForNJ(String taskType, String priority, String taskStatus, String assignee, String taskInfo,
                                                     String complaintAbout, String name, String reason, String contactReason,
                                                     String prefCallBackDate, String prefCallBackTime, String prefPhone,
                                                     String extCaseID, String appID) {
        waitFor(1);
        boolean recordFound = false;
        int size = 1;
        waitFor(3);

        if (workQueue.allRowTaskID.size() == 0 && workQueue.workQueueColumns.size() == 0) {
            if (isElementDisplayed(workQueue.noRecord)) {
                generalTaskStepDef.navigateToGeneralTask(taskType);
                assignee = myTask.userAccountName.getText();
                generalTaskStepDef.SelectTaskDetailsForNJ("", priority, taskStatus, assignee, taskInfo, complaintAbout, name, contactReason, prefCallBackDate, prefCallBackTime, prefPhone, extCaseID, appID);
                generalTaskStepDef.clickSaveButton();
                generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
                waitFor(3);
            }
        }

        if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(1);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < myTask.taskListRows.size(); j++) {
                    if (myTask.taskTypes.get(j).getText().equals(taskType)) {
                        recordFound = true;
                        break;
                    }
                }
                if (recordFound)
                    break;
                else if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }

            }

            if (!recordFound) {
                generalTaskStepDef.navigateToGeneralTask(taskType);
                generalTaskStepDef.SelectTaskDetailsForNJ("", priority, taskStatus, assignee, taskInfo, complaintAbout, name, contactReason, prefCallBackDate, prefCallBackTime, prefPhone, extCaseID, appID);
                generalTaskStepDef.clickSaveButton();
                generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
                waitFor(3);
            }

        }

    }

    @Then("I will ensure there is no task in progress status")
    public void i_will_ensure_there_is_no_task_inprogres_status() {
        int size = 1;
        waitFor(2);
        if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(2);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(2);
            }

            l1:
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < myTask.taskListRows.size(); j++) {
                    waitFor(2);
                    if (myTask.taskStatuses.get(j).getText().equals("In-Progress")) {
                        assertTrue(isElementDisplayed(myTask.taskInitiateButtons.get(j)), "Initiate button not displayed");
                        click(myTask.taskInitiateButtons.get(j));
                        waitFor(2);
                        clickOnSaveTaskSlider();
                        break l1;
                    }
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(2);
                }
            }
        }
        if (isElementDisplayed(myTask.lnkArrowBack)) {
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
    }

    @When("I create {string} task with priority as {string} taskStatus as {string} assignee as {string} task info as {string} complaintAbout as {string} name as {string} reason as {string} contactReason as {string} prefCallBackDate as {string} prefCallBackTime as {string} prefPhone as {string} extCaseID as {string} appID as {string}")
    public void createTaskForNJ(String taskType, String priority, String taskStatus, String assignee, String taskInfo,
                                String complaintAbout, String name, String reason, String contactReason,
                                String prefCallBackDate, String prefCallBackTime, String prefPhone,
                                String extCaseID, String appID) {
        System.out.println(assignee);
        CRM_GeneralTaskStepDef crm_generalTaskStepDef = new CRM_GeneralTaskStepDef();
        crm_generalTaskStepDef.navigateToGeneralTask(taskType);
        generalTaskStepDef.SelectTaskDetailsForNJ(taskType, priority, taskStatus, assignee, taskInfo, complaintAbout, name, contactReason, prefCallBackDate, prefCallBackTime, prefPhone, extCaseID, appID);

        waitFor(5);
        crm_generalTaskStepDef.clickSaveButton();
        crm_generalTaskStepDef.i_verify_Success_message_is_displayed_for_task();
        if (assignee.isEmpty()) {
            this.assignee.set( "-- --");
            CRM_GeneralTaskStepDef.taskValues.get().put("assignee", "-- --");
        } else {
            this.assignee.set( assignee);
            CRM_GeneralTaskStepDef.taskValues.get().put("assignee", assignee);
        }

        CRM_GeneralTaskStepDef.taskValues.get().put("created_by", "Service AccountOne");
        this.created_by.set( "Service AccountOne");
        CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", taskInfo);
        this.taskInfo.set( taskInfo);
        this.source.set( "User");
        CRM_GeneralTaskStepDef.taskValues.get().put("created_on", getCurrentDate());
        this.created_on.set( getCurrentDate());
        this.status_date.set( getCurrentDate());
        this.case_ID.set( "-- --");
        waitFor(3);
    }

    @When("I will get the index of {string} task and click on initiate button for that")
    public void getTheIndexOfTaskAndClickOnInitiateButton(String taskTypeExpected) {
        waitFor(1);
        int size = myTask.lnkPageNations.size();
        boolean recordFound = false;
        if (myTask.lnkPageNations.size() > 1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < myTask.taskListRows.size(); j++) {
                waitFor(1);
                String webElement = "(//h5[text()='TASK LIST']/..//table/tbody/tr//td[12])" + "[" + (j + 1) + "]/button";
                if (myTask.taskTypes.get(j).getText().trim().equalsIgnoreCase(taskTypeExpected.trim()) && !Driver.getDriver().findElements(By.xpath(webElement)).isEmpty()) {
                    taskId.set(myTask.taskIDs.get(j).getText());
                    expectedData.get().add(taskId.get());
                    expectedData.get().add(myTask.priorities.get(j).getText());
                    expectedData.get().add(myTask.taskDueIns.get(j).getText().replace("Business ", ""));
                    actions.click(Driver.getDriver().findElement(By.xpath(webElement))).build().perform();
                    CRM_GeneralTaskStepDef.taskValues.get().put("status", "Open");
                    CRM_GeneralTaskStepDef.taskValues.get().put("assignee", createGeneralTask.userAccountName.getText());
                    taskStatus.set("Open");
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
            else if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @When("I update the dispostion field in task slider as {string}")
    public void i_update_the_disposition_in_task_slider_as(String disposition) {
        waitFor(2);
        selectDropDown(createGeneralTask.disposition, disposition);
        CRM_GeneralTaskStepDef.taskValues.get().put("disposition", disposition);
    }

    @When("Verify External Application Id accept only 30 character")
    public void verifyExternalApplicationIdAcceptOnly30Character() {
        taskStatus.set("Open");
        sendKeyToTextField(createGeneralTask.externalApplicationId, getRandomString(45));
        assertEquals(createGeneralTask.externalApplicationId.getAttribute("value").length(), 30, "External Application Id is accepting more then 30 character");
        clearAndFillText(createGeneralTask.externalApplicationId, "Test CP10930");
        CRM_GeneralTaskStepDef.taskValues.get().put("externalApplicationId", "Test CP10930");
    }

    @Then("I verify view page has external Application Id which we recently added")
    public void verifyViewPageHasExternalApplicationIdValue() {
        assertEquals(myTask.exAppIdValue.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"),
                "External Application Id is mismatch");
    }

    @When("I will clear the external application id if present in task slider")
    public void clearTheExternalApplicationIdInTaskSlider() {
        waitForVisibility(createGeneralTask.externalApplicationId, 10);
        clearFiled(createGeneralTask.externalApplicationId);
        CRM_GeneralTaskStepDef.taskValues.get().put("externalApplicationId", "-- --");
    }

    @Then("I verify the updated information in view task details page")
    public void verifyMUpdatedInfoInViewTaskDetailsPage() {
        waitFor(2);
        if (isElementDisplayed(myTask.taskDetailsHeader)) {
            sa.get().assertThat(isElementDisplayed(editHistory.taskDetailsTab)).as("Header is not displayed").isTrue();
        } else
            sa.get().assertThat(isElementDisplayed(srViewEditPage.srDetailsTab)).as("View SR Details is not displayed").isTrue();
        for (String key : CRM_GeneralTaskStepDef.taskValues.get().keySet()) {
            switch (key) {
                case "source":
                    sa.get().assertThat(myTask.lblSource.getText()).as("Source is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("source"));
                    break;
                case "created_by":
                    sa.get().assertThat(myTask.lblCreatedBy.getText()).as("CreatedBy is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
                    break;
                case "created_on":
                    sa.get().assertThat(myTask.lblCreatedOn.getText()).as("CreatedOn is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_on"));
                    break;
                case "dueDate":
                    sa.get().assertThat(myTask.lblDueDate.getText()).as("Due Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate"));
                    break;
                case "dueIn":
                    String dueIn = CRM_GeneralTaskStepDef.taskValues.get().get("dueIn");
                    if (dueIn.equals("1 Business Days"))
                        dueIn = "1 Business Day";
                    waitFor(3);
                    sa.get().assertThat(myTask.lblDueIn.getText()).as("Due In is mismatch").isEqualTo(dueIn);
                    break;
                case "taskType":
                    try {
                        sa.get().assertThat(myTask.lblTaskType.getText()).as("Task Type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.srType.getText()).as("SR Type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"));
                    }
                    break;
                case "priority":
                    sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
                    break;
                case "status":
                    sa.get().assertThat(myTask.lblStatus.getText()).as("Status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
                    break;
                case "assignee":
                    sa.get().assertThat(myTask.lblAssignee.getText()).as("Assignee is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
                    break;
                case "taskInfo":
                    try {
                        sa.get().assertThat(myTask.lblTaskInfo.getText()).as("Task Information is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.lblSRInfo.getText()).as("SR Information is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                    }
                    break;
                case "externalApplicationId":
                    try {
                        sa.get().assertThat(myTask.exAppIdValue.getText()).as("External Application Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.appIdValue.getText()).as("External Application Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                    }
                    break;
                case "externalCaseId":
                    try {
                        sa.get().assertThat(myTask.exCaseIdValue.getText()).as("External Case Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.vacmsCaseIdValue.getText()).as("External Case Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId"));
                    }
                    break;
                case "externalConsumerID":
                    try {
                        sa.get().assertThat(myTask.mmisMemberIdValue.getText()).as("MMIS Member Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalConsumerID"));
                    } catch (Exception el) {
                        sa.get().assertThat(myTask.medicaidIDRidVlu.getText()).as("Medicaid ID/RID is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalConsumerID"));
                    }
                    break;
                case "name":
                    sa.get().assertThat(myTask.nameValue.getText()).as("Name is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("name"));
                    break;
                case "preferredPhone":
                    String phNumber = CRM_GeneralTaskStepDef.taskValues.get().get("preferredPhone").equalsIgnoreCase("-- --") ?
                            myTask.preferredPhoneVlu.getText() : myTask.preferredPhoneVlu.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
                    sa.get().assertThat(phNumber).as("Preferred Phone is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preferredPhone"));
                    break;
                case "preferredCallBackDate":
                    sa.get().assertThat(myTask.preferredCallBackDateVlu.getText()).as("preferredCallBackDate is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preferredCallBackDate"));
                    break;
                case "preferredCallBackTime":
                    sa.get().assertThat(myTask.preferredCallBackTimeVlu.getText()).as("preferredCallBackTime is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preferredCallBackTime"));
                    break;
                case "disposition":
                    sa.get().assertThat(myTask.dispositionVlu.getText()).as("Disposition is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("disposition"));
                    break;
                case "documentDueDate":
                    sa.get().assertThat(myTask.documentDueDate.getText()).as("Due Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("documentDueDate"));
                    break;
                case "contactRecordSingle":
                    sa.get().assertThat(myTask.contactReasonVlu.getText()).as("Contact Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactRecordSingle"));
                    break;
                case "cpCRID":
                    sa.get().assertThat(myTask.cpCRId.getText()).as("CP Contact Reason ID is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("cpCRID"));
                    break;
                case "cpSRID":
                    sa.get().assertThat(myTask.cpSRId.getText()).as("CP SR ID is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("cpSRID"));
                    break;
                case "cpTaskID":
                    sa.get().assertThat(myTask.cpTaskId.getText()).as("CP Task ID is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("cpTaskID"));
                    break;
                case "reasonForEdit":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("reasonForEdit").contains(",")) {
                        generalTaskStepDef.iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.reasonForEdit.getText()).as("Reason For Edit is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForEdit"));
                    break;
                case "actionTakenSingle":
                    sa.get().assertThat(myTask.txtActionTaken.getText()).as("Action Taken is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("actionTakenSingle"));
                    break;
                case "InbDocType":
                    sa.get().assertThat(myTask.txtDocType.getText()).as("Document Type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("InbDocType"));
                    break;
                case "program":
                    sa.get().assertThat(myTask.txtProgram.getText()).as("Program is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("program"));
                    break;
                case "ARFirstName":
                    sa.get().assertThat(myTask.ARFirstNameValue.getText()).as("AR First Name is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("ARFirstName"));
                    break;
                case "ARLastName":
                    sa.get().assertThat(myTask.ARLastNameValue.getText()).as("AR Last Name is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("ARLastName"));
                    break;
                case "ARPhone":
                    String ARphNumber = myTask.ARPhoneValue.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
                    sa.get().assertThat(ARphNumber).as("AR Phone number is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("ARPhone"));
                    break;
                case "Organization":
                    sa.get().assertThat(myTask.OrganizationValue.getText()).as("Organization is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("Organization"));
                    break;
                case "AREmail":
                    sa.get().assertThat(myTask.AREmailValue.getText()).as("AR Email is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("AREmail"));
                    break;
                case "returnedMailReason":
                    sa.get().assertThat(myTask.returnedMailReason.getText()).as("Returned Mail Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("returnedMailReason"));
                    break;
                case "returnedMailReceivedDate":
                    sa.get().assertThat(myTask.returnedMailReceivedDate.getText()).as("Returned Mail Received Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("returnedMailReceivedDate"));
                    break;
                case "businessUnit":
                    sa.get().assertThat(myTask.businessUnit.getText()).as("Business Unit is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("businessUnit"));
                    break;
                case "dateResent":
                    sa.get().assertThat(myTask.dateResent.getText()).as("Date Resent is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dateResent"));
                    break;
                case "locality":
                    sa.get().assertThat(myTask.localityValue.getText()).as("Locality is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("locality"));
                    break;
                case "requestType":
                    sa.get().assertThat(myTask.requestTypeValue.getText()).as("Request Type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("requestType"));
                    break;
                case "issueType":
                    sa.get().assertThat(myTask.issueTypeValue.getText()).as("Issue Type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("issueType"));
                    break;
                case "caseWorkerFirstName":
                    sa.get().assertThat(myTask.cwFirstNameValue.getText()).as("Case Worker First Name is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseWorkerFirstName"));
                    break;
                case "caseWorkerLastName":
                    sa.get().assertThat(myTask.cwLastNameValue.getText()).as("Case Worker Last Name is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseWorkerLastName"));
                    break;
                case "outcome":
                    sa.get().assertThat(myTask.outComeValue.getText()).as("outcome is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("outcome"));
                    break;
                case "preferredLanguage":
                    sa.get().assertThat(myTask.languageValue.getText()).as("LANGUAGE is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preferredLanguage"));
                    break;
                case "informationType":
                    sa.get().assertThat(myTask.informationTypeValue.getText()).as("Information Type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("informationType"));
                    break;
                case "dateTranslationEscalated":
                    sa.get().assertThat(myTask.dateTranslationEscalatedValue.getText()).as("Date Translation Escalate is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationEscalated"));
                    break;
                case "dateTranslationReceived":
                    sa.get().assertThat(myTask.dateTranslationReceivedValue.getText()).as("Date Translation Received is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationReceived"));
                    break;
                case "dateTranslationMailed":
                    sa.get().assertThat(myTask.dateTranslationMailedValue.getText()).as("Date Translation Mailed is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationMailed"));
                    break;
                case "remandReason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("remandReason").contains(",")) {
                        generalTaskStepDef.iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.remandReasonValue.getText()).as("Remand Reason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("remandReason"));
                    break;
                case "eligibilityDecision":
                    try {
                        sa.get().assertThat(myTask.eligibilityDecisionValue.getText()).as("Eligibility Decision is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("eligibilityDecision"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.decisionValue.getText()).as("Decision is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("eligibilityDecision"));
                    }
                    break;
                case "remandCompletionDate":
                    sa.get().assertThat(myTask.remandCompletionDateValue.getText()).as("Remand Completion Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("remandCompletionDate"));
                    break;
                case "remandDueDateValue":
                    sa.get().assertThat(myTask.remandDueDateValue.getText()).as("remand Due Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("remandDueDateValue"));
                    break;
                case "receivedDate":
                    sa.get().assertThat(myTask.receivedDate.getText()).as("Received Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("receivedDate"));
                    break;
                case "contactReason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").contains(",")) {
                        sa.get().assertThat(myTask.contactReasonVlu.getText()).as("Contact Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").split(",")[0]);
                        hover(createGeneralTask.multiselectValueLink);
                        waitForVisibility(createGeneralTask.selectedToolTipValue.get(0), 5);
                        List<String> actualValues = createGeneralTask.selectedToolTipValue.stream().map(WebElement::getText).collect(Collectors.toList());
                        sa.get().assertThat(actualValues).as("Mouse Over values are incorrect").contains(CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").split(",")[1]);
                    } else
                        sa.get().assertThat(myTask.contactReasonVlu.getText()).as("Contact Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactReason"));
                    break;
                case "missingInfoRequired":
                    sa.get().assertThat(myTask.missingInfoRequired.getText()).as("Missing Info Required is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("missingInfoRequired"));
                    break;
                case "vclDueDate":
                    sa.get().assertThat(myTask.vclDueDateValue.getText()).as("VCL Due Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("vclDueDate"));
                    break;
                case "noOfApplicantsInHousehold":
                    sa.get().assertThat(myTask.noOFApplicantsInHousehold.getText()).as("# Of Applicants In Household is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("noOfApplicantsInHousehold"));
                    break;
                case "applicationReceivedDate":
                    sa.get().assertThat(myTask.applicationReceivedDate.getText()).as("Application Received Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationReceivedDate"));
                    break;
                case "applicationStatus":
                    sa.get().assertThat(myTask.applicationSubStatus.getText()).as("Application Status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationStatus"));
                    break;
                case "vclSentDate":
                    sa.get().assertThat(myTask.vclSentDateValue.getText()).as("VCL Sent Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("vclSentDate"));
                    break;
                case "appointmentDate":
                    sa.get().assertThat(myTask.appointmentDateVlu.getText()).as("Appointment Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("appointmentDate"));
                    break;
                case "appointmentTime":
                    sa.get().assertThat(myTask.appointmentTimeVlu.getText()).as("Appointment Time is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("appointmentTime"));
                    break;
                case "caseStatus":
                    sa.get().assertThat(myTask.caseStatusVlu.getText()).as("Case Status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseStatus"));
                    break;
                case "preHearingReason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("preHearingReason").contains(",")) {
                        generalTaskStepDef.iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.preHearingReasonVlu.getText()).as("preHearingReason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preHearingReason"));
                    break;
                case "preHearingOutcome":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("preHearingOutcome").contains(",")) {
                        generalTaskStepDef.iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.preHearingOutcomeVlu.getText()).as("preHearingOutcome drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preHearingOutcome"));
                    break;
                case "dmasReceivedDate":
                    assertEquals(myTask.dmasReceivedDate.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("dmasReceivedDate"), "dmasReceivedDate is mismatch");
                    break;
                case "coverVAReceivedDate":
                    assertEquals(myTask.coverVAReceivedDate.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("coverVAReceivedDate"), "coverVAReceivedDate is mismatch");
                    break;
                case "appealReason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("appealReason").contains(",")) {
                        generalTaskStepDef.iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.appealReason.getText()).as("appealReason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("appealReason"));
                    break;
                case "appealCaseSummaryDueDate":
                    sa.get().assertThat(myTask.appealCaseSummaryDueDate.getText()).as("appealCaseSummaryDueDate is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("appealCaseSummaryDueDate"));
                    break;
                case "appealsCaseSummaryHyperlink":
                    sa.get().assertThat(myTask.appealsCaseSummaryHyperlink.getText()).as("appealsCaseSummaryHyperlink is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("appealsCaseSummaryHyperlink"));
                    break;
                case "appealCaseSummaryStatus":
                    sa.get().assertThat(myTask.appealCaseSummaryStatus.getText()).as("appealCaseSummaryStatus is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("appealCaseSummaryStatus"));
                    break;
                case "reviewOutcome":
                    sa.get().assertThat(myTask.reviewOutcome.getText()).as("reviewOutcome is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reviewOutcome"));
                    break;
                case "facilityType":
                    sa.get().assertThat(myTask.facilityType.getText()).as("facilityType is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("facilityType"));
                    break;
                case "facilityName":
                    sa.get().assertThat(myTask.facilityName.getText()).as("facilityName is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("facilityName"));
                    break;
                case "providerFirstName":
                    sa.get().assertThat(myTask.providerFirstName.getText()).as("providerFirstName is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("providerFirstName"));
                    break;
                case "providerLastName":
                    sa.get().assertThat(myTask.providerLastName.getText()).as("providerLastName is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("providerLastName"));
                    break;
                case "providerPhone":
                    String actualProviderPhone = myTask.providerPhone.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
                    String expectedProviderPhone = CRM_GeneralTaskStepDef.taskValues.get().get("providerPhone").replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
                    sa.get().assertThat(actualProviderPhone).as("providerPhone is mismatch").isEqualTo(expectedProviderPhone);
                    break;
                case "providerEmail":
                    sa.get().assertThat(myTask.providerEmail.getText()).as("providerEmail is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("providerEmail"));
                    break;
                case "other":
                    sa.get().assertThat(myTask.other.getText()).as("other is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("other"));
                    break;
                case "invalidReason":
                    sa.get().assertThat(myTask.invalidReason.getText()).as("Invalid Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("invalidReason"));
                    break;
                case "complaintAbout":
                    sa.get().assertThat(myTask.cmplAbtValue.getText()).as("Complaint About drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("complaintAbout"));
                    break;
                case "reason":
                    sa.get().assertThat(myTask.reasonValue.getText()).as("Reason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reason"));
                    break;
                case "actionTaken":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").contains(",")) {
                        generalTaskStepDef.iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.txtActionTaken.getText()).as("actionTaken drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken"));
                    break;
                case "channel":
                    sa.get().assertThat(myTask.channelValue.getText()).as("channel drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("channel"));
                    break;
                case "ARAddressLine1":
                    sa.get().assertThat(myTask.arAddressLine1Value.getText()).as("AR Address Line 1 is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("ARAddressLine1"));
                    break;
                case "ARAddressLine2":
                    sa.get().assertThat(myTask.arAddressLine2Value.getText()).as("AR Address Line 2 is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("ARAddressLine2"));
                    break;
                case "ARCity":
                    sa.get().assertThat(myTask.arCityValue.getText()).as("AR City is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("ARCity"));
                    break;
                case "ARState":
                    sa.get().assertThat(myTask.arStateValue.getText()).as("AR State is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("ARState"));
                    break;
                case "ARZipCode":
                    sa.get().assertThat(myTask.arZipCodeValue.getText()).as("AR Zip Code is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("ARZipCode"));
                    break;
                case "denialReason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("denialReason").contains(",")) {
                        generalTaskStepDef.iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.denialReason.getText()).as("denialReasondrop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("denialReason"));
                    break;
                case "decisionSource":
                    sa.get().assertThat(myTask.decisionSource.getText()).as("decisionSource is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("decisionSource"));
                    break;
                case "denialReasonSingle":
                    sa.get().assertThat(myTask.denialReason.getText()).as("Denial Reason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("denialReasonSingle"));
                    break;
                case "medicaidId":
                    sa.get().assertThat(myTask.medicaidIDRidVlu.getText()).as("Medicaid ID is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("medicaidId"));
                    break;
                case "currentPlan":
                    sa.get().assertThat(myTask.currentPlan.getText()).as("Current Plan drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("currentPlan"));
                    break;
                case "disenrollmentDate":
                    sa.get().assertThat(myTask.disenrollmentDate.getText()).as("Disenrollment Date value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("disenrollmentDate"));
                    break;
                case "appSource":
                    sa.get().assertThat(myTask.appSource.getText()).as("AppSource value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("appSource"));
                    break;
                case "disenrollmentReason":
                    sa.get().assertThat(myTask.disenrollmentReason.getText()).as("Disenrollment Reason value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("disenrollmentReason"));
                    break;
                case "language":
                    sa.get().assertThat(myTask.languageValue.getText()).as("LANGUAGE is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("language"));
                    break;
                case "sentNOADate":
                    sa.get().assertThat(myTask.sentNoaDateVlu.getText()).as("sent NOA Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("sentNOADate"));
                    break;
                case "applicationType":
                    sa.get().assertThat(myTask.applicationType.getText()).as("applicationType value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"));
                    break;
                case "myWorkSpaceDate":
                    sa.get().assertThat(myTask.myWorkspaceDate.getText()).as("myWorkSpaceDate value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("myWorkSpaceDate"));
                    break;
                case "applicationSignatureDate":
                    sa.get().assertThat(myTask.applicationSignatureDate.getText()).as("applicationSignatureDate value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationSignatureDate"));
                    break;
                case "contactPhone":
                    try {
                        sa.get().assertThat(myTask.phoneVlu.getText().replaceAll("\\D", "")).as("Phone number is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactPhone"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.contactPhone.getText().replaceAll("\\D", "")).as("Phone number is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactPhone"));
                    }
                    break;
                case "requestedDate":
                    sa.get().assertThat(myTask.requestedDate.getText()).as("Requested Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("requestedDate"));
                    break;
                case "planChangeReason":
                    sa.get().assertThat(myTask.planChangeReason.getText()).as("planChangeReason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("planChangeReason"));
                    break;
                case "DesiredPlan":
                    sa.get().assertThat(myTask.desiredPlan.getText()).as("DesiredPlan drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("DesiredPlan"));
                    break;
                case "planStartDate":
                    sa.get().assertThat(myTask.planStartDate.getText()).as("Plan Start Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("planStartDate"));
                    break;
                case "planName":
                    sa.get().assertThat(myTask.planName.getText()).as("planNamee is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("planName"));
                    break;
                case "usedTobaccoInTheLastSixMonths":
                    sa.get().assertThat(myTask.usedTobaccoInTheLastSixMonths.getText()).as("used tobacco in the last six months drop downn value mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("usedTobaccoInTheLastSixMonths"));
                    break;
                case "contactName":
                    sa.get().assertThat(myTask.contactName.getText()).as("contactName number is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactName"));
                    break;
                case "dateOfVoicemail":
                    sa.get().assertThat(myTask.dateOfVoicemail.getText()).as("dateOfVoicemail is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dateOfVoicemail"));
                    break;
                case "timeOfVoicemail":
                    sa.get().assertThat(myTask.timeOfVoicemail.getText()).as("timeOfVoicemail is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("timeOfVoicemail"));
                    break;
                case "remandDueDate":
                    sa.get().assertThat(myTask.remandDueDateValue.getText()).as("remandDueDate is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("remandDueDate"));
                    break;
                case "noOfApprovedApplicants":
                    sa.get().assertThat(myTask.noOFApprovedApplicants.getText()).as("# Of Approved Applicants is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("noOfApprovedApplicants"));
                    break;
                case "AddressLine1":
                    sa.get().assertThat(myTask.AddressLine1Value.getText()).as("AddressLine1 is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("AddressLine1"));
                    break;
                case "AddressLine2":
                    sa.get().assertThat(myTask.AddressLine2Value.getText()).as("AddressLine2 is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("AddressLine2"));
                    break;
                case "City":
                    sa.get().assertThat(myTask.CityValue.getText()).as("City is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("City"));
                    break;
                case "State":
                    sa.get().assertThat(myTask.StateValue.getText()).as("State is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("State"));
                    break;
                case "addressZipCode":
                    sa.get().assertThat(myTask.zipCode.getText()).as("addressZipCode field value is wrong").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("addressZipCode"));
                    break;
                case "addressSource":
                    sa.get().assertThat(myTask.addressSource.getText()).as("addressSource dropdown value is wrong").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("addressSource"));
                    break;
                case "addressType":
                    sa.get().assertThat(myTask.addressType.getText()).as("addressType dropdown value is wrong").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("addressType"));
                    break;
                case "taskID":
                    sa.get().assertThat(CRM_GeneralTaskStepDef.taskValues.get().get("taskID")).as("taskID is empty").isNotEmpty();
                    break;
                case "transferReason":
                    sa.get().assertThat(myTask.transferReason.getText()).as("Transfer Reason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("transferReason"));
                    break;
                case "hpe":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("hpe").equals("true"))
                        sa.get().assertThat(myTask.hpeCheckBox.isSelected()).as("hpe checkbox is not selected").isTrue();
                    else
                        sa.get().assertThat(myTask.hpeCheckBox.isSelected()).as("hpe checkbox is selected").isFalse();
                    break;
                case "closedRenewal":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("closedRenewal").equals("true"))
                        sa.get().assertThat(myTask.closedRenewalCheckBox.isSelected()).as("closedRenewal checkbox is not selected").isTrue();
                    else
                        sa.get().assertThat(myTask.closedRenewalCheckBox.isSelected()).as("closedRenewal checkbox is selected").isFalse();
                    break;
                case "srCategory":
                    sa.get().assertThat(myTask.srCategory.getText()).as("SR Category is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("srCategory"));
                    break;
                case "incidentDate":
                    sa.get().assertThat(myTask.incidentDateVlu.getText()).as("Incident Date value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("incidentDate"));
                    break;
                case "complaintType":
                    sa.get().assertThat(myTask.complaintTypeValue.getText()).as("Complaint Type drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("complaintType"));
                    break;
                case "memberName":
                    sa.get().assertThat(myTask.memberName.getText()).as("Member Name value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("memberName"));
                    break;
                case "csrName":
                    sa.get().assertThat(myTask.csrNameVlu.getText()).as("CSR Name value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("csrName"));
                    break;
                case "escalated":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("escalated") && CRM_GeneralTaskStepDef.taskValues.get().get("escalated").equals("true"))
                        sa.get().assertThat(myTask.escalatedVlu.isSelected()).as("Escalated checkbox is unchecked").isTrue();
                    else
                        sa.get().assertThat(myTask.escalatedVlu.isSelected()).as("Escalated checkbox is checked").isFalse();
                    break;
                case "escalationReason":
                    sa.get().assertThat(myTask.escalationReasonVlu.getText()).as("Escalated Reason value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("escalationReason"));
                    break;
                case "organizationDD":
                    sa.get().assertThat(myTask.otherEntityVlu.getText()).as("Other entity is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("organizationDD"));
                    break;
                case "businessUnitAssigneeTo":
                    sa.get().assertThat(myTask.lblBusinessUnitAssigneeTo.getText()).as("businessUnitAssigneeTo drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("businessUnitAssigneeTo"));
                    break;
                case "actionTknSingle":
                    sa.get().assertThat(myTask.txtActionTaken.getText()).as("actionTknSingle is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("actionTknSingle"));
                    break;
                case "renewalProcessingDueDate":
                    sa.get().assertThat(myTask.renewalProcessingDueDateValue.getText()).as("Renewal Processing Due Date is mismatch").isEqualTo(getFutureDateInFormat("29"));
                    break;
                case "renewalDate":
                    sa.get().assertThat(myTask.renewalDateValue.getText()).as("Renewal Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("renewalDate"));
                    break;
                case "renewalCutoffDate":
                    sa.get().assertThat(myTask.renewalCutoffDateValue.getText()).as("Renewal Cutoff Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("renewalCutoffDate"));
                    break;
                case "miReceivedDate":
                    sa.get().assertThat(myTask.miReceivedDateValue.getText()).as("MI Received Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("miReceivedDate"));
                    break;
                default:
                    if (!(key.equals("consumerID") || key.equals("caseID") || key.equals("startDate") || key.equals("consumerName")
                            || key.equals("taskNote") || key.equals("reasonForCancel") || key.equals("reasonForOnHold") || key.equals("contactRecord")))
                        sa.get().fail("Switch case not match for key " + key);
                    break;
            }
        }
        /*
        if (!CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote"))
            CRM_GeneralTaskStepDef.taskValues.get().put("taskNote","-- --");
        try {
            sa.get().assertThat(myTask.lblTaskNote.getText()).as("taskNote is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
        } catch (Exception e) {
            sa.get().assertThat(myTask.lblSRNote.getText()).as("taskNote is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
        }
        */
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForCancel"))
            sa.get().assertThat(myTask.reasonForCancel.getText()).as("Reason For Cancel is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel"));
        else
            sa.get().assertThat(isElementDisplayed(myTask.reasonForCancel)).as("Reason For Cancel is present in view page").isFalse();
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForOnHold"))
            sa.get().assertThat(myTask.reasonForHold.getText()).as("Reason For Hold is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForOnHold"));
        else
            sa.get().assertThat(isElementDisplayed(myTask.reasonForHold)).as("Reason For Hold is present in view page").isFalse();

    }

    @Then("I verify Task status drop down values in slider")
    public void verifyStatusDropDownValues(List<String> expectedValues) {
        waitFor(1);
        waitForVisibility(myTask.statusDropdown, 5);
        myTask.statusDropdown.click();
        waitForVisibility(myTask.statusDrDw.get(0), 5);
        /*List<String> actualValues = myTask.statusDrDw.stream().map(WebElement::getText).collect(Collectors.toList());
        List expectedValuess = new LinkedList(expectedValues);
        Collections.sort(actualValues);
        Collections.sort(expectedValuess);
        Assert.assertEquals(actualValues, expectedValuess, "Status down values are incorrect i Slider");*/
        sa.get().assertThat(myTask.statusDrDw)
                .as("status DD Size does not match").hasSize(expectedValues.size())
                .as("status DD has duplicate values").doesNotHaveDuplicates()
                .as("status DD drop down values not match ").extracting(WebElement::getText).containsExactlyInAnyOrderElementsOf(expectedValues);
        actions.moveToElement(myTask.taskInfo).click().perform();
    }

    @Then("I verify dispostion field values in slider")
    public void verifyDispositionDropDownValues(List<String> expectedValues) {
        waitFor(1);
        waitForVisibility(createGeneralTask.disposition, 5);
        createGeneralTask.disposition.click();
        waitForVisibility(myTask.dispositionDrDw.get(0), 5);
        /*int k=0;
        if(myTask.dispositionDrDw.get(0).getText().isEmpty())
            k++;
        List<String> actualValues = myTask.dispositionDrDw.stream().map(WebElement::getText).collect(Collectors.toList());
        List expectedValuess = new LinkedList(expectedValues);
        Collections.sort(actualValues);
        Collections.sort(expectedValuess);
        Assert.assertEquals(actualValues, expectedValuess, "Disposition down values are incorrect in Slider");*/
        sa.get().assertThat(myTask.dispositionDrDw)
                .as("Disposition DD Size does not match").hasSize(expectedValues.size())
                .as("Disposition DD has duplicate values").doesNotHaveDuplicates()
                .as("Disposition DD drop down values not match ").extracting(WebElement::getText).containsExactlyInAnyOrderElementsOf(expectedValues);
        actions.moveToElement(myTask.dispositionDrDw.get(0)).click().perform();
    }

    @When("verify task note field length in task slider")
    public void verifyTaskNoteAcceptOnly1000Character() {
        clearAndFillText(myTask.taskSilderTaskNotes, "ABC");
        generalTaskStepDef.verifyMinLengthErrorMessage("TASK NOTES");
        if (LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA")) {
            clearAndFillText(myTask.taskSilderTaskNotes, getRandomString(1010));
            sa.get().assertThat(isElementDisplayed(myTask.maxLengthError1)).as("Max length error message is not displayed").isTrue();
            CRM_GeneralTaskStepDef.taskValues.get().put("noteValue", getRandomString(1000));
            clearAndFillText(myTask.taskSilderTaskNotes, CRM_GeneralTaskStepDef.taskValues.get().get("noteValue"));
            sa.get().assertThat(myTask.taskSilderTaskNotes.getAttribute("value")).as("Task Note is accepting more then 1000 character").hasSize(1000);
        } else {
            clearAndFillText(myTask.taskSilderTaskNotes, getRandomString(310));
            sa.get().assertThat(isElementDisplayed(myTask.maxLengthError)).as("Max length error message is not displayed").isTrue();
            CRM_GeneralTaskStepDef.taskValues.get().put("noteValue", getRandomString(300));
            clearAndFillText(myTask.taskSilderTaskNotes, CRM_GeneralTaskStepDef.taskValues.get().get("noteValue"));
            sa.get().assertThat(myTask.taskSilderTaskNotes.getAttribute("value")).as("Task Note is accepting more then 300 character").hasSize(300);

        }
    }

    @Then("I verify view page has task note which we recently added")
    public void verifyViewPageHasTaskNoteValue() {
        sa.get().assertThat(myTask.lblTaskNote.getText()).as("taskNote is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
    }

    @And("I minimize the active call icon")
    public void minimizeTheActiveCallIcon() {
        waitFor(2);
        myTask.remove.click();
        waitFor(2);
    }

    @And("I click on My task tab")
    public void clickOnMyTaskTab() {
        waitFor(2);
        click(myTask.myTasksTab);
    }

    @When("I verify the data in slider is unchanged")
    public void i_verify_the_data_in_silder_is_unchanged() {
        waitForVisibility(myTask.sliderStatus, 10);
        assertEquals(myTask.sliderStatus.getText(), taskStatus.get(), "Status is updated in view page");

    }

    @And("I verify the case ID and consumer ID in the task slider")
    public void i_verify_the_caseId_consumerId_in_task_task_slider() {
        generalTaskStepDef.verify_linked_case_value();
        assertEquals(myTask.taskSilderCaseId.getText(), generalTaskStepDef.taskValues.get().get("caseID"), "Case Id is not matached");
        assertEquals(myTask.taskSilderConsumerID.getText(), generalTaskStepDef.taskValues.get().get("consumerID"), "Consumer ID is not matached");
    }

    @When("I verify the task status in task slider is in progress")
    public void i_update_the_task_status_in_task_slider_is_inprogress() {
        waitForVisibility(myTask.statusDropdown, 5);
        assertEquals(myTask.statusDropdown.getText(), "In Progress", "Status not found");
    }

    @And("I ensure My task page has at least one task with status other than {string} and I initate task")
    public void ensureAtLeastOneTaskWithStatus_initiateTask(String status) {
        if (myTask.taskIDs.isEmpty()) {
            createGeneralTaskAndInitiate();
        } else {
            l1:
            for (WebElement x : myTask.lnkPageNations) {
                waitFor(3);
                List<WebElement> rowStr = Driver.getDriver().findElements(By.xpath("//button[text()='INITIATE']//parent::td"));
                List<WebElement> initiateButtons = Driver.getDriver().findElements(By.xpath("//button[text()='INITIATE']"));
                for (WebElement webElement : rowStr) {
                    waitFor(2);
                    int index = Integer.parseInt(webElement.getAttribute("data-row"));
                    if (!myTask.getStatusValues.get(index).getText().equalsIgnoreCase(status) &&
                            !myTask.getTaskTypesValues.get(index).getText().equalsIgnoreCase("Member Matching")) {
                        waitFor(2);
                        click(initiateButtons.get(index));
                        break l1;
                    }
                }
                if (isElementDisplayed(myTask.lnkArrowForward)) {
                    click(x);
                }
            }
        }
    }

    @And("I verify the my task pagination and update status to complete")
    public void verifyPaginatioin_UpdateTaskStatus() {
        while (myTask.lnkArrowForward.isDisplayed() && myTask.lnkPageNations.size() > 3) {

            System.out.println(myTask.lnkPageNations.size() + " size and true");
            for (int j = 0; j < 5; j++) {
                waitForPageToLoad(10);
                waitFor(5);
                System.out.println("Printing loop value " + j);
                String taskStatusValue = myTask.getTaskTypesValues.get(1).getText();
                System.out.println("Printing task Type" + taskStatusValue);
                switch (taskStatusValue) {

                    case "NJ":
                        initate = Driver.getDriver().findElement(By.xpath("(//button[text()='INITIATE'])[1]"));
                        waitForVisibility(initate, 10);
                        waitFor(5);
                        click(initate);
                        selectDropDown(myTask.statusDropdown, "Complete");
                        myTask.taskSilderSave.click();
                        waitFor(1);
                        contactHistory.cancelEditButton.click();
                        waitFor(2);
                        contactHistory.warningMessageContinueButton.click();
                        i_navigate_to_task_Managments_page(pageNameCache.get());
                        break;

                    case "General Two":
                        initate = Driver.getDriver().findElement(By.xpath("(//button[text()='INITIATE'])[1]"));
                        waitForVisibility(initate, 10);
                        waitFor(5);
                        click(initate);
                        waitFor(1);
                        selectDropDown(myTask.statusDropdown, "Complete");
                        selectRandomDropDownOption(myTask.taskSilderDisposition);
                        myTask.taskSilderSave.click();
                        break;

                    case "Inquiry/Complaint":
                        initate = Driver.getDriver().findElement(By.xpath("(//button[text()='INITIATE'])[1]"));
                        waitForVisibility(initate, 10);
                        waitFor(5);
                        click(initate);
                        waitFor(3);
                        waitForVisibility(myTask.statusDropdown, 10);
                        selectDropDown(myTask.statusDropdown, "Complete");
                        waitForVisibility(myTask.taskSilderDisposition, 10);
                        selectRandomDropDownOption(myTask.taskSilderDisposition);
                        myTask.taskSilderSave.click();
                        break;

                    case "Incomplete Contact Record":
                        System.out.println("No initate button for Incomplete contact record");
                        waitFor(5);
                        click(workQueue.taskIDColumnHeader);
                        break;

                    default:
                        initate = Driver.getDriver().findElement(By.xpath("(//button[text()='INITIATE'])[1]"));
                        waitForVisibility(initate, 10);
                        waitFor(5);
                        click(initate);
                        selectDropDown(myTask.statusDropdown, "Complete");
                        myTask.taskSilderSave.click();
                        waitFor(1);
                        break;
                }
            }
        }
    }

    @Then("I will verify user gets error message pop up is displayed")
    public void verifyUserGetErrorMessageWhenTheyInitiateMoreThenOneTask() {
        scrollToElement(taskSearchPage.taskIDColumnHeader);
        waitFor(2);
        click(Driver.getDriver().findElement(By.xpath("//button[text()='INITIATE']")));
        assertTrue(isElementDisplayed(myTask.errorMessageForInitiate), "User can initiate more then one task");
    }

    @And("I will ensure assignee has at least one In-Progress with task type {string}")
    public void ensureAssigneeHasAt(String taskType) {
        boolean flag = true;
        waitFor(2);
        if (myTask.taskIDs.size() == 0) {
            generalTaskStepDef.navigateToGeneralTask(taskType);
            generalTaskStepDef.SelectTaskDetails("", "", "Service", "");
            generalTaskStepDef.clickSaveButton();
            waitFor(2);
        }
        waitForVisibility(workQueue.allRowStatus.get(0), 10);
        int size = 1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        l1:
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < myTask.getStatusValues.size(); i++) {
                waitFor(2);
                if (myTask.getStatusValues.get(i).getText().equalsIgnoreCase("In-Progress")) {
                    taskId.set(myTask.taskIDs.get(0).getText());
                    flag = false;
                    break l1;
                }
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        if (flag) {
            clickInitiate();
            waitForVisibility(workQueue.taskDetailsSlider, 10);
            assertTrue(workQueue.taskDetailsSlider.isDisplayed(), "Task slider is not displayed");
            waitFor(2);
            clickPriority();
        }
    }

    @And("Initiate a task which is status {string}")
    public void i_will_initiate_task_inprogres_status(String status) {
        waitFor(3);
        boolean recordFound = false;
        int size = 1;
        if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(1);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }

            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < myTask.taskStatuses.size(); j++) {
                    if (myTask.taskStatuses.get(j).getText().equals(status)) {
                        Assert.assertTrue(isElementDisplayed(myTask.taskInitiateButtonsText.get(j)), "Initiate button not displayed");
                        taskId.set(myTask.taskIDs.get(j).getText());
                        click(myTask.taskInitiateButtonsText.get(j));
                        recordFound = true;
                        break;
                    }
                }
                if (recordFound)
                    break;
                else if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
        if (isElementDisplayed(myTask.lnkArrowBack)) {
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
    }

    @Then("I verify Active contact screen displayed with initiate call")
    public void checkActiveContactScreenIsDisplayed() {
        waitForVisibility(contactRecord.contactInProgressGreenSign, 10);
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed(), "Call is not initiated");
        assertTrue(contactRecord.contactStart.isDisplayed(), "Call is not initiated");
        assertTrue(contactRecord.contactDurationValue.isDisplayed(), "Call is not initiated");
        assertTrue(activeContact.contactRecordSign.isDisplayed(), "Active contact screen is not displayed");
        assertTrue(activeContact.generalConsumerInContactSighn.isDisplayed(), "Active contact screen is not displayed");
    }

    @Then("I verify error message displayed and user remains on active contact screen and task slider closed")
    public void checkErrorMessageAndActiveContactScreenIsDisplayed() {
        waitForVisibility(myTask.contactRecordErMsg, 10);
        assertTrue(myTask.contactRecordErMsg.isDisplayed(), "Error Message pop Up is not displayed");
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed(), "Call is not initiated");
        assertTrue(contactRecord.contactStart.isDisplayed(), "Call is not initiated");
        assertTrue(contactRecord.contactDurationValue.isDisplayed(), "Call is not initiated");
        assertTrue(activeContact.contactRecordSign.isDisplayed(), "Active contact screen is not displayed");
        assertTrue(activeContact.generalConsumerInContactSighn.isDisplayed(), "Active contact screen is not displayed");
        assertFalse(isElementDisplayed(workQueue.taskDetailsSlider), "Task slider is still displayed");
    }

    @Then("I verify error message displayed and contact record is saved")
    public void checkErrorMessageAndContactRecordSaved() {
        sa.get().assertThat(isElementDisplayed(myTask.taskErMsg)).as("Case Id is not displayed on create task page").isTrue();
    }

    @Then("I verify task is NOT past due and is NOT highlighted in red")
    public void validateTaskIsNotPastDueDateIsNotHighlihtedInRed() {
        waitFor(3);
        int size = 1;
        if (myTask.lnkPageNations.size() > 1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < myTask.taskDueDates.size(); j++) {
                if (myTask.taskDueDates.get(j).getText().equals(getCurrentDate())) {
                    assertNotEquals(getColorCode(myTask.taskListRows.get(j)), "#ffebee", "Task not highlighted in red color");
                    waitFor(1);
                }
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @Then("I verify internal {string} id is search by default")
    public void verifyInternalCaseOrConsumerIdIsSearchByDefault(String type) {
        waitForVisibility(contactRecord.contactInProgressGreenSign, 10);
        if (type.equalsIgnoreCase("Case")) {
            System.out.println(contactRecord.uniqueIDTextbox.getAttribute("value") + " VAAAAAAAA");
            System.out.println(CRM_GeneralTaskStepDef.taskValues.get().get("caseID")+ "DAAAAAAAAA");
            sa.get().assertThat(contactRecord.uniqueIDTextbox.getAttribute("value"))
                    .as("Case Id is not searched").hasToString(CRM_GeneralTaskStepDef.taskValues.get().get("caseID"));
        } else if (type.equalsIgnoreCase("Consumer")) {
            sa.get().assertThat(contactRecord.consumerIDTextbox.getAttribute("value"))
                    .as("Consumer Id is not searched").hasToString(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"));
        }
        sa.get().assertThat(contactRecord.contactType.getText())
                .as("Contact Type is not Outbound").hasToString("Outbound");
    }

    @Then("I verify error message is displayed for task in progress and not assign to me")
    public void verifyErrorMessageWhenTaskIsInProgessNotAssignToMe() {
        assertTrue(myTask.inProgressErrorMsg.isDisplayed(),
                "Task is In-Progress and not assign to me can be editable");
    }

    @Then("I click validate and link Consumer & Case for task for edit task")
    public void iClickValidateAndLinkConsumerCaseForTaskForEditTask() {
        scrollDownUsingActions(2);
        waitFor(5);
        createGeneralTask.validateTaskbutton.click();
        waitForVisibility(createGeneralTask.linkCaseConsumerButtonAfterValidation, 10);
        createGeneralTask.linkCaseConsumerButtonAfterValidation.click();
        waitFor(5);
        scrollDownUsingActions(2);
        waitForVisibility(createGeneralTask.linkedNames.get(0), 10);

        scrollUpUsingActions(1);
        waitFor(1);
        taskStatus.set(createGeneralTask.taskStatus.getText());
        selectRandomDropDownOption(createGeneralTask.reasonForEditDrpDn);
        selectRandomDropDownOption(createGeneralTask.selectReasonDropdown);
        selectRandomDropDownOption(createGeneralTask.complaintAboutDropdown);
        clearAndFillText(createGeneralTask.name, getRandomString(8));
        waitFor(2);
        scrollDownUsingActions(1);
        waitFor(1);
        createGeneralTask.saveEditTask.click();

    }

    @And("I get rawLogs for {string} Verify updatedBy and timeStamp")
    public void iGetRawLogsForVerifyUpdatedByAndTimeStamp(String arg0) {
        List<String> externalCaseIdsString = EventsUtilities.getRawLogs(arg0);
        String firstLog = externalCaseIdsString.get(0);
        String updatedBy = Driver.getDriver().findElement(By.xpath("//p[text()='Service  AccountOne']/parent::div/p[2]")).getText().replace("ID ", "");
        String presentTimeStamp = apitdu.getCurrentDateAndTime("yyyy-MM-dd'T'");
        Assert.assertTrue(firstLog.contains(updatedBy));
        Assert.assertTrue(firstLog.contains(presentTimeStamp));
    }

    @And("I get rawLogs for {string} Verify updatedBy and effectiveStartDate")
    public void iGetRawLogsForVerifyUpdatedByAndEffectiveStartDate(String arg0) {
        String updatedBy = Driver.getDriver().findElement(By.xpath("//p[text()='Service  AccountEight']/parent::div/p[2]")).getText().replace("ID ", "");
        String presentTimeStamp = apitdu.getCurrentDateAndTime("yyyy-MM-dd'T'");
        List<String> externalCaseIdsString = EventsUtilities.getRawLogs(updatedBy);
        String firstLog = externalCaseIdsString.get(0);
        Assert.assertTrue(firstLog.contains(updatedBy));
        Assert.assertTrue(firstLog.contains(presentTimeStamp));
    }

    @When("I will verify task note is not displayed in edit page when task is in create status")
    public void verifyTaskNoteIsNotDisplayedWhenTaskStatusIsCreate() {
        assertFalse(isElementDisplayed(myTask.taskInfo), "Task Note is displayed when status is created");
    }

    @Then("I verify task updated Successfully")
    public void i_verify_task_update_message() {
        Assert.assertTrue(myTask.taskUpdatedTxt.isDisplayed(), "Update Message text is not displayed");
    }

    @Then("I will verify an Error Message You must save your changes to the Task before saving on the Task Slider")
    public void i_will_verify_an_error_message_you_must_save_your_changes_to_the_task_before_saving_on_the_task_slider() {
        Assert.assertTrue(myTask.taskErrorMsgYouMustSaveYourChangesBeforeSaving.isDisplayed(), "Error Message text is not displayed");
    }

    @Then("I verify Error saving task message displayed in the screen")
    public void i_verify_error_saving_task_message_displayed_in_the_screen() {
        Assert.assertTrue(myTask.errorSavingTaskMsg.isDisplayed(), "Error saving task message is not displayed");
    }

    @Then("I verify 'Case is required' error message displayed for FC SR")
    public void i_verify_error_case_is_required_for_FC_SR() {
        waitForVisibility(myTask.caseIsRequiredForSRErrBeforeSaving,5);
        Assert.assertTrue(myTask.caseIsRequiredForSRErrBeforeSaving.isDisplayed(), "Error message is not displayed");
    }

    @Then("I close the error popup")
    public void close_error_popup(){
        myTask.closeErrorPopup.click();
    }

    @Then("I verify the task information in task slider page")
    public void verifyUpdatedInfoInTaskSliderPage() {
        waitFor(2);
        assertEquals(myTask.taskSilderPriority.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("priority"),
                "Priority is mismatch in task slider");
        assertEquals(myTask.taskSilderStatus.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("status"),
                "Status is mismatch in task slider");
        assertTrue(myTask.taskSilderAssignee.getText().contains(CRM_GeneralTaskStepDef.taskValues.get().get("assignee")),
                "Assignee is mismatch");
        assertEquals(myTask.taskSilderTaskInformation.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"),
                "Task Information is mismatch");
//        assertEquals(myTask.taskSilderTaskNotes.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"),
//                "Task Notes is mismatch");

        if (!CRM_GeneralTaskStepDef.taskValues.get().get("status").equalsIgnoreCase("OnHold"))
            assertEquals(myTask.statusDropdown.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("status"),
                    "Task slider Status dd value not matches");
        else
            assertTrue(myTask.statusDropdown.getText().isEmpty(), "Task slider Status dd value not matches");
    }

    @And("I minimize Genesys popup if populates")
    public void minimizeGenesysPopupIfPopulates() {
        waitFor(10);
        if (myTask.callManagement.size() != 0) {
            jsClick(myTask.callManagement.get(0));
        }
        tabs.set(new ArrayList<>(Driver.getDriver().getWindowHandles()));
        if (tabs.get().size() > 1) {
            Driver.getDriver().switchTo().window(tabs.get().get(1));
            waitFor(5);
            Driver.getDriver().close();
        }
        waitFor(3);
        Driver.getDriver().switchTo().window(tabs.get().get(0));
        waitFor(2);
    }

    @Then("I Verify {string} task with {string} status not present in task list")
    public void verifyTaskNotPresentInTaskList(String taskTypeExpected, String taskStatusExpected) {
        waitFor(2);
        myTask.lnkPageNations.stream().limit(10).forEach(pag -> {
            for (int i = 0; i < myTask.taskTypes.size(); i++) {
                if (myTask.taskTypes.get(i).getText().equalsIgnoreCase(taskTypeExpected)) {
                    assertNotEquals(taskStatusExpected, myTask.taskStatuses.get(i).getText());
                }
            }
            waitFor(2);
            click(pag);
        });
    }

    @And("I logout from the app and login back")
    public void logoutAndLoginBack() {
        Driver.getDriver().navigate().refresh();
        try {
            Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
            selectDropDown(loginPage.projectId, CRM_ContactRecordUIStepDef.projectName.get());
            waitFor(3);
            click(loginPage.continueBtn);
            waitFor(6);
            click(loginPage.acceptAndContinueBtn);
            waitForVisibility(loginPage.userIcon, 5);
        } catch (Exception e) {
            click(loginPage.wrngMsgOkBtn);
            selectDropDown(loginPage.projectId, CRM_ContactRecordUIStepDef.projectName.get());
            click(loginPage.continueBtn);
            waitFor(3);
            click(loginPage.acceptAndContinueBtn);
        }
    }

    @Then("I verify Action Taken value")
    public void verifyActionTakenDDValue() {
        waitFor(2);
        assertEquals((myTask.txtActionTaken.getText()), CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken"), "Action Taken Value is incorrect");
    }

    @When("I update status to {string} on task")
    public void iUpdateStatusToOnEditPage(String status) {
        waitForVisibility(myTask.statusDropdown, 5);
        selectDropDownNoCheck(myTask.statusDropdown, status);
    }

    @And("Verify TaskList not displaying duplicate TaskIds and task status is {string}")
    public void verifyTaskListNotDisplayingDuplicatTaskIds(String status) {
        waitForVisibility(taskSRTabPage.taskIds.get(0), 7);
        List<String> taskIdList = taskSRTabPage.taskIds.stream().map(WebElement::getText).collect(Collectors.toList());
        Set<String> uniqueTasks = new HashSet<String>(taskIdList);
        assertEquals(taskIdList.size(), uniqueTasks.size(), "TaskId values not unique");
        assertEquals(taskSRTabPage.taskStatuses.get(1).getText(), status, "Task status not " + status);
    }

    @And("I logOut while Working a Task")
    public void IlogOutwhileWorkingaTask() {
        waitForVisibility(dashboard.logOutDrop, 5);
        dashboard.logOutDrop.click();
        waitForVisibility(dashboard.logOutbutton, 4);
        dashboard.logOutbutton.click();
        waitFor(5);
        if (isElementDisplayed(dashboard.warningPopupContinueBtn))
            dashboard.warningPopupContinueBtn.click();
    }

    @Then("Verify in My Task first task status is {string}")
    public void verifyInMyTaskFirstTaskStatusIs(String taskStatus) {
        waitForVisibility(myTask.taskStatuses.get(1), 4);
        assertEquals(myTask.taskStatuses.get(0).getText(), taskStatus, "Task status not " + taskStatus);
    }

    @Then("Verify Facility Type and Facility Name fields are enabled with Application Type")
    public void verifyFacilityTypeAndFacilityNameFieldsAreEnabledWithApplicationType(List<String> appTypes) {
        for (String type : appTypes) {
            selectDropDown(newUserPage.applicationType, type);
            sa.get().assertThat(createGeneralTask.facilityType.getAttribute("aria-disabled")).as("Facility Type field is disable for " + type).isBlank();
            sa.get().assertThat(createGeneralTask.facilityName.getAttribute("aria-disabled")).as("Facility Name field is disable for " + type).isBlank();
        }
    }

    @Then("I will verify Manage Queue Filter is hide for user")
    public void iVerifyManageQueueFilterIsHideForUser() {
        waitFor(2);
        int i = 0;
        while (i == 0 || !isElementDisplayed(dashboard.myTaskWorkQueueSideTab)) {
            actions.moveToElement(dashboard.taskManagementTab).click().build().perform();
            waitFor(2);
            actions.moveToElement(dashboard.expandTaskManagementSideTab).click().build().perform();
            i++;
            if (i == 10)
                break;
        }
        assertTrue(isElementDisplayed(dashboard.myTaskWorkQueueSideTab), "MyTask/work queue link is hide for user");
        assertFalse(isElementDisplayed(dashboard.manageQueueFilterTab), "Manage Queue Filter link is not hide for user");
    }

    @Then("I navigate to {string} from task view page")
    public void Inavigatetofrotaskviewpage(String pageName) {
        waitFor(2);
        int i = 0;
        while (i == 0 || !isElementDisplayed(dashboard.myTaskWorkQueueSideTab)) {
            actions.moveToElement(dashboard.taskManagementTab).build().perform();
            waitFor(2);
            actions.moveToElement(dashboard.expandTaskManagementSideTab).click().build().perform();
            i++;
            if (i == 10)
                break;
        }
        pageNameCache.set(pageName);
        waitFor(3);
        switch (pageName) {
            case "My Task":
                jsClick(dashboard.myTaskWorkQueueSideTab);
                waitFor(3);
                if (isElementDisplayed(applicationTrackingPage.withdrawContinueButtonInsideWarning)) {
                    applicationTrackingPage.withdrawContinueButtonInsideWarning.click();
                }
                hover(myTask.workQueueTab);
                break;
            case "Work Queue":
                dashboard.myTaskWorkQueueSideTab.click();
                waitFor(4);
                if (isElementDisplayed(applicationTrackingPage.withdrawContinueButtonInsideWarning)) {
                    applicationTrackingPage.withdrawContinueButtonInsideWarning.click();
                }
                jsClick(myTask.workQueueTab);
                waitFor(2);
                hover(myTask.workQueueTab);
                break;
            case "Task Search":
                dashboard.taskSearchSideTab.click();
                waitFor(2);
                if (isElementDisplayed(applicationTrackingPage.withdrawContinueButtonInsideWarning)) {
                    applicationTrackingPage.withdrawContinueButtonInsideWarning.click();
                }
                hover(taskSearchPage.statusDate);
                break;
            case "Manage Queue Filter":
                dashboard.manageQueueFilterTab.click();
                waitFor(2);
                if (isElementDisplayed(applicationTrackingPage.withdrawContinueButtonInsideWarning)) {
                    applicationTrackingPage.withdrawContinueButtonInsideWarning.click();
                }
                hover(taskQueueFilter.createFilterBt);
                break;
            case "Saved Task Search":
                dashboard.savedTaskSearchSideTab.click();
                try {
                    waitFor(2);
                    if (isElementDisplayed(applicationTrackingPage.withdrawContinueButtonInsideWarning)) {
                        applicationTrackingPage.withdrawContinueButtonInsideWarning.click();
                    }
                    hover(savedTaskSrchPage.deleteBtn.get(0));
                } catch (Exception e) {
                }
                break;
        }
        waitFor(2);
    }

    @Then("Verify tasks linked to Case ID {string}")
    public void verifyTasksLinkedToCaseID(String casId) {
        waitFor(3);
        assertTrue(createGeneralTask.caseInfo.stream().anyMatch(t -> t.getText().trim().equals(casId)));
    }

    @Then("Verify tasks linked to Consumer ID {string}")
    public void verifyTasksLinkedToConsumerID(String consumer) {
        assertTrue(createGeneralTask.consumerInfo.stream().anyMatch(t -> t.getText().trim().equals(consumer)));
    }

    @Then("Verify search results displayed GetInsuredCASEID {string}")
    public void VerifysearchresultsdisplayedGetInsuredCASEID(String casId) {
        for (WebElement el : taskSearchPage.taskGetInsuredCaseID) {
            assertEquals(casId, el.getText());
        }
    }

    @Then("Verify search results displayed GetInsuredConsumerID {string}")
    public void VerifysearchresultsdisplayedGetInsuredConsumerID(String consumerId) {
        for (WebElement el : taskSearchPage.taskGetInsuredConsumerID) {
            assertEquals(consumerId, el.getText());
        }
    }

    @When("I expend first SrId from the list by clicking in Task ID")
    public void IexpendfirstSrIdfromthelistbyclickinginTaskID() {
        waitFor(2);
        this.taskId.set(myTask.taskIDs.get(CRM_WorkQueueStepDef.position.get()).getText());
        this.srID.set(String.valueOf(Integer.parseInt(this.taskId.get()) - 2));
        scrollUpRobotKey();
        jsClick(myTask.taskIDs.get(CRM_WorkQueueStepDef.position.get()));
    }

    @And("I click due in on priority dashboard to close task details")
    public void iClickDueInOnPriorityDashboardToCloseTaskDetails() {
        myTask.dashboardDueIn.click();
    }

    @And("I click on edit button on view task page OR view SR page")
    public void IclickoneditbuttononviewtaskpageORviewSRpage() {
        waitFor(1);
        for (int i = 0; i <= 3; i++) {
            jsClick(myTask.editButtonToTaskOrSr);
            waitFor(2);
            if (!isElementDisplayed(myTask.editButtonToTaskOrSr)) {
                break;
            }
        }
        sa.get().assertThat(myTask.reasonForEditDropDown.getText()).as("Reason For Edit is not cleared out as per CP-28816").isBlank();
        CRM_GeneralTaskStepDef.taskValues.get().put("reasonForEdit", "-- --");
        scrollUpUsingActions(2);
    }

    @Then("I Verify Task Notes field is not displayed on View Task page")
    public void i_verify_Task_Notes_field_is_not_displayed_on_View_Task_page() {
        sa.get().assertThat(isElementDisplayed(myTask.taskNotes)).as("Task Notes is present in View Task ui").isFalse();
    }

    @And("I verify Task Notes field is not displayed on Edit Task page")
    public void i_verify_Task_Notes_field_is_not_displayed_on_Edit_Task_page() {
        sa.get().assertThat(isElementDisplayed(myTask.lblTaskNote)).as("Task Notes is present in Edit Task ui").isFalse();
    }

    @And("I verify Task Notes field is not displayed on Task List expanded view page")
    public void i_verify_Task_Notes_field_is_not_displayed_on_Task_List_expanded_view_page() {
        assertTrue(workQueue.taskNoteVlu.size() < 1);
    }

    @And("I click on first task id from TSR tab")
    public void clickOnFirstTaskId() {
        waitFor(2);
        scrollUpRobotKey();
        jsClick(myTask.taskIDs.get(CRM_WorkQueueStepDef.position.get()));
    }

    private void createGeneralTaskAndInitiate() {
        generalTaskStepDef.navigateToGeneralTask("General");
        generalTaskStepDef.SelectTaskDetails("", "", "Service", "");
        waitFor(20);
        generalTaskStepDef.clickSaveButton();
        myTask.taskIdColumn.click();
        taskId.set(workQueue.allRowTaskID.get(0).getText());
        initate = Driver.getDriver().findElement(By.xpath("//button[text()='INITIATE']"));
        waitForVisibility(initate, 10);
        click(initate);
    }

    @When("I click on first task id in my task")
    public void i_click_on_first_task() {
        waitFor(2);
        taskId.set(myTask.taskIDs.get(CRM_WorkQueueStepDef.position.get()).getText());
        scrollUpRobotKey();
        jsClick(myTask.taskIDs.get(CRM_WorkQueueStepDef.position.get()));
    }

    @Then("I verify Task Management panel and it's DD value")
    public void i_verify_Task_Management_Panel(List<String> expectedValues) {
        waitFor(3);
        actions.moveToElement(dashboard.taskManagementTab).click().build().perform();
        System.out.println("Text from task/sr management: "+dashboard.taskManagementLeftMenu.getText());
     //   sa.get().assertThat(dashboard.taskManagementLeftMenu.getText()).as("Task Management is not matching").isEqualTo("TASK/SR MANAGEMENT");
        waitFor(3);
        actions.moveToElement(dashboard.expandTaskManagementSideTab).click().build().perform();
        sa.get().assertThat(dashboard.taskMangDDVlu)
                .as("TaskManagement DD Size does not match").hasSize(expectedValues.size())
                .as("TaskManagement DD has duplicate values").doesNotHaveDuplicates()
                .as("TaskManagement DD drop down values not match ").extracting(WebElement::getText).containsExactlyInAnyOrderElementsOf(expectedValues);
        actions.moveToElement(dashboard.expandTaskManagementSideTab).click().build().perform();
    }

    @Then("I verify the link section has case consumer and task information on Active Contact screen")
    public void verify_ConsumerID_CaseID_TaskID_linkComponent_On_Active_Contact_Screen() {
        waitFor(2);
        scrollDownUsingActions(5);
        boolean caseLink = false, consumer = false, task = false;

        for (int i = 0; i < createGeneralTask.activeContactList.size(); i++) {
            String linkType = createGeneralTask.activeContactList.get(i).getText();
            switch (linkType) {
                case "Task":
                    if (!CRM_TaskManagementStepDef.taskId.get().isEmpty())
                        sa.get().assertThat(createGeneralTask.taskLinkInfo.get(0).getText()).as("TaskId Value is missing").isEqualTo(CRM_TaskManagementStepDef.taskId.get());
                    else
                        sa.get().assertThat(createGeneralTask.taskLinkInfo.get(0).getText()).as("TaskId Value is missing").isNotBlank();
                    sa.get().assertThat(createGeneralTask.taskLinkInfo.get(1).getText()).as("Name Value is missing").isEqualTo("Task");
                    sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"));
                    sa.get().assertThat(createGeneralTask.taskLinkInfo.get(3).getText()).as("Status Date Value is not correct").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("startDate"));
                    sa.get().assertThat(createGeneralTask.taskLinkInfo.get(4).getText().equals("Created") ||
                            createGeneralTask.taskLinkInfo.get(4).getText().equals("In-Progress")).as("Status Value is not Created").isTrue();
                    task = true;
                    break;
                case "Case":
                    sa.get().assertThat(createGeneralTask.caseInfo.get(1).getText()).as("CaseID Value is missing").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseID"));
                    sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("Name Value is missing").isEqualTo("Case");
                    sa.get().assertThat(createGeneralTask.caseInfo.get(3).getText()).as("Type Value is not showing null").isEqualTo("-- --");
                    sa.get().assertThat(createGeneralTask.caseInfo.get(4).getText()).as("Status Date Value is not showing blank").isEqualTo("--/--/----");
                    sa.get().assertThat(createGeneralTask.caseInfo.get(5).getText()).as("Status Value is not showing null").isEqualTo("-- --");
                    caseLink = true;
                    break;
                case "Consumer Profile":
                    sa.get().assertThat(createGeneralTask.consumerInfo.get(0).getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID")) || createGeneralTask.consumerInfo.get(0).getText().equals("Incomplete App")).as("ConsumerID Value is missing").isTrue();
                    sa.get().assertThat(createGeneralTask.consumerInfo.get(1).getText()).as("Name Value is missing").isEqualTo("Consumer Profile");
                    sa.get().assertThat(createGeneralTask.consumerInfo.get(2).getText()).as("Type Value is missing").isEqualTo("Consumer");
                    sa.get().assertThat(verifyDateFormat(createGeneralTask.consumerInfo.get(3))).as("Consumer Status date is wrong").isTrue();
                    sa.get().assertThat(createGeneralTask.consumerInfo.get(4).getText()).as("Status Value is missing").isNotBlank();
                    consumer = true;
                    break;
            }
        }
        System.out.println("Task-> " + task);
        if (createGeneralTask.activeContactList.size() == 1) {
            sa.get().assertThat(task).as("Link section is not displayed task information").isTrue();
        } else {
            sa.get().assertThat(caseLink && consumer && task).as("Link section is not displayed case/consumer/task").isTrue();
        }
    }

    @Then("I verify state notified date is updated to current date on the view page")
    public void verify_state_notified_date_is_updated_to_current_date() {
        sa.get().assertThat(myTask.dateStateNotified.getText()).isEqualTo(getCurrentDate());
    }

    @When("I will grab task id from active contact page after initiating task")
    public void i_will_grab_task_id_from_active_contact_page_after_initiating_task() {
        waitFor(2);
        taskId.set(myTask.taskIdOnTaskSlider.getText());
        System.out.println("Task id from task slider: "+taskId.get());
    }

}
