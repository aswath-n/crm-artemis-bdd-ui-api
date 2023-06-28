package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.*;

public class CRM_TaskServiceRequestTabStepDef extends BrowserUtils {
    CRMTaskServiceRequiestTabPage TaskSRTabPage = new CRMTaskServiceRequiestTabPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTSRTabForSRPage trsTabSRPage = new CRMTSRTabForSRPage();
    public static final ThreadLocal<ArrayList<String>> dynamicFlds = ThreadLocal.withInitial(ArrayList::new);

    @Then("I verify all fields are displayed on Task Summary page")
    public void i_verify_all_field_are_displayed_on_Task_Summary_page() {
        sa.get().assertThat(myTask.taskListLabel.isDisplayed()).as("Task List Label is not displayed").isTrue();
        sa.get().assertThat(TaskSRTabPage.columnTaskId.isDisplayed()).as("Task ID is not displayed").isTrue();
        sa.get().assertThat(TaskSRTabPage.typeColumn.isDisplayed()).as("Task Type is not displayed").isTrue();
        sa.get().assertThat(TaskSRTabPage.priorityColumn.isDisplayed()).as("Priority is not displayed").isTrue();
        sa.get().assertThat(TaskSRTabPage.statusColumn.isDisplayed()).as("Status Date is not displayed").isTrue();
        sa.get().assertThat(TaskSRTabPage.dueDateColumn.isDisplayed()).as("Due Date is not displayed").isTrue();
        sa.get().assertThat(TaskSRTabPage.dueInColumn.isDisplayed()).as("Due IN is not displayed").isTrue();
        sa.get().assertThat(TaskSRTabPage.consumerNameColumn.isDisplayed()).as("Consumer Name is not displayed").isTrue();
    }

    @Then("I verify all headers are displayed in task service request tab")
    public void verifyHeadersWhenIExpandTheTask() {
        waitFor(1);
        String[] str = {"ASSIGNEE", "CASE ID", "CREATED BY", "TASK INFORMATION", "SOURCE", "CREATED ON", "STATUS DATE"};
        for (int i = 0; i < str.length; i++) {
            sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//p[text()='" + str[i] + "']")))).as(str[i]+" is not displaying").isTrue();
        }
    }

    @And("I navigate to newly created task in Task & Service Request Tab")
    public void navigateToNewlyCreatedTask() {
        scrollDown();
        waitFor(3);
        scrollUpRobot();
        waitFor(2);
        waitForVisibility(TaskSRTabPage.columnTaskId, 12);
        jsClick(TaskSRTabPage.columnTaskId);
        waitFor(2);
        CRM_TaskManagementStepDef.taskId.set(TaskSRTabPage.taskIDs.get(0).getText());
    }

    @Then("I verify all fields values on Task & Service Request Tab")
    public void verifyAllFieldsValuesOnTaskAndServiceRequestTab() {
        waitFor(2);
        sa.get().assertThat(hasOnlyDigits(TaskSRTabPage.taskIDs.get(0).getText())).as("Is not digit").isTrue();
        sa.get().assertThat(TaskSRTabPage.taskTypes.get(0).getText()).as("Task type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"));
        sa.get().assertThat(TaskSRTabPage.priorities.get(0).getText()).as("Priority is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
        sa.get().assertThat(TaskSRTabPage.taskStatuses.get(0).getText()).as("Status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
        sa.get().assertThat(TaskSRTabPage.dueDates.get(0).getText()).as("Due date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate"));
        String dueIn= CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").replace("Business ","");
        sa.get().assertThat(TaskSRTabPage.dueIns.get(0).getText()).as("Due In is mismatch").isEqualTo(dueIn);
        sa.get().assertThat(TaskSRTabPage.consumerNames.get(0).getText()).as("Consumer Name is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("consumerName"));
    }

    @Then("I verify the task details are displayed when expanded")
    public void verifyTaskDetailsWhenExpanded() {
        waitFor(1);
        if(CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee")==null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty()){
            sa.get().assertThat(TaskSRTabPage.assignees.get(0).getText()).as("Assignee is mismatch").isEqualTo("-- --");
        }
        else{
            sa.get().assertThat(TaskSRTabPage.assignees.get(0).getText()).as("Assignee is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
        }
        if (!LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA"))
            sa.get().assertThat(TaskSRTabPage.caseIds.get(0).getText()).as("Case id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseID"));
        sa.get().assertThat(TaskSRTabPage.createdBys.get(0).getText()).as("CreatedBy is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
        if(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo")==null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").isEmpty()){
            sa.get().assertThat(TaskSRTabPage.taskInfos.get(0).getText()).as("Task Information is mismatch").isEqualTo("-- --");
        }
        else{
            sa.get().assertThat(TaskSRTabPage.taskInfos.get(0).getText()).as("Task information is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
        }
        sa.get().assertThat(TaskSRTabPage.sources.get(0).getText()).as("Source is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("source"));
        sa.get().assertThat(TaskSRTabPage.createdOns.get(0).getText()).as("CreatedOn is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_on"));
        sa.get().assertThat(TaskSRTabPage.statusdates.get(0).getText()).as("Status date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("startDate"));
        if(LoginStepDef.projectName1.get().equals("NJ-SBE") && !(CRM_GeneralTaskStepDef.taskValues.get().get("caseID").isEmpty())){
            sa.get().assertThat(TaskSRTabPage.getInsuredCaseIdTSRTab.getText()).as("GetInsured caseId is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseID"));
        }
    }

    @Then("I verify initiate button is displayed")
    public void verifyInitiateButton() {
        assertTrue(isElementDisplayed(TaskSRTabPage.initiateButton),"Initiate button is not displayed");
    }

    /*Author:- vidya date:-01/09/2020
        this method to verify user is navigate back to Task and service request page*/
    @Then("I Verify user is navigate back to Task and service Request Page")
    public void verifyUserIsNavigateBackToTaskandServiceRequestPage() {
        waitForVisibility(myTask.taskListHeader, 10);
        assertTrue(myTask.taskListHeader.isDisplayed(),
                "User is not able to navigate back to Task and service request page");
        assertTrue(myTask.taskAndServiceRequestTab.isDisplayed());
        if (TaskSRTabPage.taskListRows.size() > 0 && isElementDisplayed(TaskSRTabPage.taskListRows.get(0))) {
            assertTrue(isElementDisplayed(TaskSRTabPage.taskListRows.get(0)), "Task Records are not loaded");
        } else {
            assertTrue(isElementDisplayed(trsTabSRPage.srIDs.get(0)), "SR Records are not loaded");
        }
    }

    /*Author:- vidya date:-01/09/2020
        this method to verify user is navigate back to Task and service request page*/
    @Then("I will click on back arrow on view task page")
    public void clickBackArrowInViewTaskPage() {
        waitFor(2);
        click(myTask.backArrowInViewPage);
    }

    @Then("I verify task is not highlighted in red for task status {string}")
    public void verifyTaskIsNotHighlightedInRedColor(String taskStatusExpected)
    {
        waitFor(1);
        boolean recordFound = false;
        int size = 1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(TaskSRTabPage.lnkPageNations.get(TaskSRTabPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < TaskSRTabPage.taskStatuses.size(); j++) {
                if (TaskSRTabPage.taskStatuses.get(j).getText().equals(taskStatusExpected)) {
                    assertNotEquals(getColorCode(TaskSRTabPage.taskListRows.get(j)), "#ffebee", "Task highlighted in red color");
                    waitFor(2);
                    recordFound=true;
                }
            }
            if(i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound,"Records not found");
    }


    @When("Verify tasks should always appear first on top Completed tasks")
    public void VerifyTasksShouldAlwaysAppearFirst() {
        waitFor(1);
        boolean recordFound = false;
        List<String> status = new ArrayList<>();
        int size=1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(TaskSRTabPage.lnkPageNations.get(TaskSRTabPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < TaskSRTabPage.taskStatuses.size(); i++) {
                status.add(TaskSRTabPage.taskStatuses.get(i).getText());
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        for (int k = 0; k < status.size(); k++) {
            recordFound=true;
            if (status.get(k).equalsIgnoreCase("Complete") ||
                    status.get(k).equalsIgnoreCase("Cancelled")) {
                int i = k;
                for (int j = i; j < status.size(); j++) {
                    assertTrue(status.get(j).equalsIgnoreCase("Complete") ||
                                    status.get(j).equalsIgnoreCase("Cancelled"),
                            "Task sorting order is not correct");
                }
                break;
            }
        }
        assertTrue(recordFound,"Records not found");
    }

    @Then("Verify Tasks are sorted in descending order by Date")
    public void verifyTasksAreSortedInDescendingOrderByDate() {
        waitFor(1);
        boolean recordFound = false;
        List<String> statusDate = new ArrayList<>();
        int size=1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(TaskSRTabPage.lnkPageNations.get(TaskSRTabPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < TaskSRTabPage.taskStatuses.size(); i++) {
                if (TaskSRTabPage.taskStatuses.get(i).getText().equalsIgnoreCase("Complete") ||
                        TaskSRTabPage.taskStatuses.get(i).getText().equalsIgnoreCase("Cancelled")) {
                    TaskSRTabPage.expandTaskArrows.get(i).click();
                    statusDate.add(TaskSRTabPage.statusdates.get(0).getText());
                    TaskSRTabPage.expandTaskArrows.get(i).click();
                    recordFound=true;
                }
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        //reverted this one we have to take a look since AC says: IF Task is Completed or Cancelled, Then Tasks are sorted in descending order by Completed or Cancelled Date
        //need to verify status date as Completed or Cancelled date, instead of dueDate
        assertTrue(descendingOrderDatesString(statusDate),"Task are not sorted by Status date");
        assertTrue(recordFound,"Records not found");
    }

    @Then("Verify Tasks are sorted in ascending order order by Date when Task is not in a Completed or Cancelled status")
    public void verifyTasksAreSortedInAscendingOrderOrderByDateWhenTaskIsNotInACompletedOrCancelledStatus() {
        waitFor(1);
        boolean recordFound = false;
        List<String> dueDate = new ArrayList<>();
        int size=1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(TaskSRTabPage.lnkPageNations.get(TaskSRTabPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < TaskSRTabPage.taskStatuses.size(); i++) {
                if (!TaskSRTabPage.taskStatuses.get(i).getText().equalsIgnoreCase("Complete") &
                        !TaskSRTabPage.taskStatuses.get(i).getText().equalsIgnoreCase("Cancelled")) {
                    dueDate.add(TaskSRTabPage.dueDates.get(i).getText());
                    recordFound=true;
                }
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(ascendingOrderDatesString(dueDate),"Task are not sorted by Due date");
        assertTrue(recordFound,"Records not found");
    }

    @Then("I verify dynamic fields are dispayed")
    public void verifyDynamicFieldsAreDisplayed(List<String> additionalFlds) {
        dynamicFlds.set(new ArrayList<>(additionalFlds));
        for(int i=0;i<dynamicFlds.get().size();i++){
            assertTrue(isElementDisplayed(Driver.getDriver().
                            findElement(By.xpath("//label[text()='"+ dynamicFlds.get().get(i) +"']/..//input"))),
                    " Dynamic field "+dynamicFlds.get().get(i)+" are not displayed");
        }
    }

    @And("I Navigate to Contact Record details page")
    public void i_Navigate_to_Contact_Record_details_page() {
        waitFor(2);
        createGeneralTask.contactId.click();
    }


    @When("I will get the index of {string} task and click on initiate button for that in Task SR page")
    public void getTheIndexOfTaskAndClickOnInitiateButton(String taskTypeExpected) {
        waitFor(1);
        int size = 1;
        boolean recordFound = false;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(TaskSRTabPage.lnkPageNations.get(TaskSRTabPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < myTask.taskListRows.size(); j++) {
                if (TaskSRTabPage.taskTypes.get(j).getText().equals(taskTypeExpected)) {
                    click(TaskSRTabPage.taskInitiateButtons.get(j));
                    waitFor(5);
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

    @Then("I verify view page has disposition value")
    public void verifyViewPageHasDispositionValue() {
        waitFor(2);
        sa.get().assertThat(myTask.dispositionVlu.getText()).as("Disposition is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("disposition"));
    }

    @Then("verify disposition field is not displayed")
    public void verifyViewPageDoesNotDisplayDispositionValue() {
        assertFalse(isElementDisplayed(myTask.lblDisposition), "Disposition is displaying in view page");
    }

    @Then("I Verify {string} task with {string} status does not have Initiate button Task SR tab")
    public void verifyTaskDoesNotHaveInitiateButtonTaskSRTab(String taskTypeExpected, String taskStatusExpected)
    {
        waitFor(2);
        boolean recordFound = false;
        int size = 1;
        if (myTask.lnkPageNations.size()>1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(2);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < TaskSRTabPage.taskListRows.size(); j++) {
                if (TaskSRTabPage.taskTypes.get(j).getText().equals(taskTypeExpected) &&
                        TaskSRTabPage.taskStatuses.get(j).getText().equals(taskStatusExpected)) {
                    assertTrue(TaskSRTabPage.taskInitiateButtonsText.get(j).getText().isEmpty(),
                            "Initiate button displayed");
                    recordFound = true;
                    break;
                }
            }
            if(recordFound)
                break;
            else if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Task Type and status not matches");
        if(isElementDisplayed(myTask.lnkArrowBack))
            click(myTask.lnkArrowBack);
    }

    @Then("I Verify {string} task with {string} status does have Initiate button Task SR tab")
    public void verifyTaskDoesHaveInitiateButtonTaskSRTab(String taskTypeExpected, String taskStatusExpected) {

        waitFor(1);
        boolean recordFound = false;
        int size = 1;
        if (myTask.lnkPageNations.size()>1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(TaskSRTabPage.lnkPageNations.get(TaskSRTabPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < TaskSRTabPage.taskListRows.size(); j++) {
                if (TaskSRTabPage.taskTypes.get(j).getText().equals(taskTypeExpected) &&
                        TaskSRTabPage.taskStatuses.get(j).getText().equals(taskStatusExpected)) {
                    assertTrue(isElementDisplayed(TaskSRTabPage.taskInitiateButtons.get(j)),
                            "Initiate button not displayed");
                    recordFound = true;
                    break;
                }
            }
            if(recordFound)
                break;
            else if(i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, " Task type not matches");
    }

    @Then("I verify Case Id {string} and Consumer name in Task List")
    public void verifyCaseIdAndConsumerNameInTaskList(String caseId)
    {
        waitFor(1);
        int size = 1;
        String consFullNameFromTopLeftScreen = TaskSRTabPage.consumerFullNameFromTopLeftScreen.getText();
        if(caseId.equalsIgnoreCase("takeFromUI"))

            caseId = getDynamicWebElement("//p[contains(text(),'consumer_name')]//following-sibling::p","consumer_name",consFullNameFromTopLeftScreen).getText();

        if (myTask.lnkPageNations.size()>1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(TaskSRTabPage.lnkPageNations.get(TaskSRTabPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < TaskSRTabPage.taskListRows.size(); j++) {
                assertEquals(TaskSRTabPage.consumerNames.get(j).getText(), consFullNameFromTopLeftScreen, "Consumer Full name not matches");
                click(TaskSRTabPage.expandTaskArrows.get(j));
                waitFor(1);
                assertEquals(TaskSRTabPage.caseId.getText(), caseId, "Case ID is not matching");
                click(TaskSRTabPage.expandTaskArrows.get(j));
                waitFor(2);
            }
            if(i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @Then("I click on task id in link component")
    public void i_click_on_task_id_in_link_component() {
        createGeneralTask.taskLinkInfo.get(2).click();
    }

    @And("I select {string} option in status drop down")
    public void selectValueFromStatuaDropDown(String value) {
        waitForClickablility(myTask.statusDropDown, 10);
        selectDropDown(myTask.statusDropDown, value);
        waitFor(3);
    }

    /*Author:- vidya date:-01/09/2020
        this method to to navigate to task and service request tab*/
    @And("I navigate to Task and service Request Page")
    public void naviagteToTaskServiceRequestTab() {
        click(myTask.taskAndServiceRequestTab);
        scrollUpRobot();
    }

    @And("I will check CaseId field is not present in TSR tab")
    public void verifyCaseIdIsNotPresentInTSRTab() {
        assertFalse(isElementDisplayed(TaskSRTabPage.caseId),"Case Id field is present in TSR tab");
    }

    @And("I click first on first TaskId")
    public void IclickfirstonfirstTaskId() {
        waitForVisibility(TaskSRTabPage.frstTaskId,10);
        TaskSRTabPage.frstTaskId.click();
    }

    @And("Verify I am able to navigated to the Service Request")
    public void VerifyIambletonavigatedtotheServiceRequest() {
        waitForVisibility(TaskSRTabPage.frstTaskId,10);
        TaskSRTabPage.serReqId.click();
        waitForVisibility(TaskSRTabPage.serviceReqDet,10);
        assertTrue(TaskSRTabPage.serviceReqDet.getText().contains("SERVICE REQUEST DETAILS"),"Task Not SERVICE REQUEST DETAILS");
    }
}
