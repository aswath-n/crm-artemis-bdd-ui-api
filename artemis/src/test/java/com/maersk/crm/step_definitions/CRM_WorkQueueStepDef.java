package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import io.cucumber.core.exception.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.*;

public class CRM_WorkQueueStepDef extends CRMUtilities implements ApiBase {


    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMWOrkQueuePage workQueue = new CRMWOrkQueuePage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();

    final ThreadLocal<APITaskManagementController> apitm = ThreadLocal.withInitial(APITaskManagementController::new);
    final ThreadLocal<CRM_TaskManagementStepDef> taskManagementStepDef = ThreadLocal.withInitial(CRM_TaskManagementStepDef::new);
    final ThreadLocal<CRM_NavigateThroughContactDashboardStepDef> navigateThroughContactDashboardStepDef = ThreadLocal.withInitial(CRM_NavigateThroughContactDashboardStepDef::new);
    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    Actions actions = new Actions(Driver.getDriver());
    public static final ThreadLocal<String> taskId = ThreadLocal.withInitial(String::new);

    private final ThreadLocal<String> startDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> dueDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> dueIn = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> taskType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> priority = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> status = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> caseID = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerID = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> largestTaskId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> userNameData = ThreadLocal.withInitial(() -> createGeneralTask.userAccountName.getText());
    public final ThreadLocal<String> taskID = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> taskSliderUrl = ThreadLocal.withInitial(() -> "https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/mars/crm/case_consumer-search/demographic");
    private final ThreadLocal<String> workQueueUrl = ThreadLocal.withInitial(() -> "mars/crm/task-management/dashboard");
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    final ThreadLocal<ArrayList<String>> tabs = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<Integer> position = ThreadLocal.withInitial(() -> 0);



    /*This method to create a new Task on create task page
    Author -Vidya
     */

    @When("I Create the {string} Task on Create task page")
    public void i_create_the_task_on_create_task_page(String taskName) {
        waitForVisibility(dashBoard.btnMenuList, 30);
        click(dashBoard.btnMenuList);
        waitForVisibility(dashBoard.createTaskMenu, 10);
        click(dashBoard.createTaskMenu);
        waitForVisibility(dashBoard.subMenuList, 10);
        System.out.println(".//span[text()='" + taskName + "']");
        WebElement task = Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']"));
        scrollToElement(task);
        click(task);
        waitFor(2);
        waitForVisibility(createGeneralTask.lblDueIn, 10);
        startDate.set(createGeneralTask.lblStatusDate.getText());
        dueDate.set(createGeneralTask.lblDueDate.getText());
        dueIn.set(createGeneralTask.lblDueIn.getText());
        dueIn.set(dueIn.get().replace("Business Days", "Days"));
        taskType.set(createGeneralTask.lstTaskType.getText());
        priority.set(createGeneralTask.lstTaskPriority.getText());
        status.set(createGeneralTask.txtTaskStatus.getAttribute("value"));
        caseID.set(createGeneralTask.lblCaseId.getText());
        consumerID.set(createGeneralTask.lblConsumerId.getText());
        System.out.println(consumerID.get());
        waitForVisibility(createGeneralTask.btnSave, 10);
        jsClick(createGeneralTask.btnSave);
        waitFor(1);
    }

    /*This method to newly created task by clicking on TaskID column header on Work Queue Page
    Author -Vidya
     */
    //Getting stale element error hence added below wait
    @And("I navigate to newly created task by clicking on TaskID column header")
    public void i_navigate_to_newly_created_task() {
        waitFor(2);
        jsClick(workQueue.taskIDColumnHeader);
        waitFor(2);
    }

    /*This method to validate newly created task status should be Created
    Author -Vidya
     */

    @Then("The newly created task status should be Created")
    public void statusCheck() {
        assertEquals(workQueue.statusCreate.getText(), "Created", "Status is not created");
    }

    /*This method to verify all columns are displayed on Work Queue page
    Author -Vidya
     */

    @Then("I verify all columns are displayed on Work Queue page")
    public void columnsCheck() {
        String[] str = {"TASK ID", "TYPE", "PRIORITY", "STATUS DATE", "STATUS", "DUE DATE", "DUE IN", "CASE ID", "CONSUMER ID"};
        for (int i = 1; i < workQueue.workQueueColumns.size() - 1; i++) {
            sa.get().assertThat(workQueue.workQueueColumns.get(i).getText()).as(str[i - 1] + " column is not displayed").isEqualTo(str[i - 1]);
        }
    }

    /*This method to verify all columns values are displayed on newly created task on Work Queue Page
    Author -Vidya
     */

    @Then("I verify all columns values are displayed on newly created task")
    public void columnsValueCheck() {
        String[] str = {taskType.get(), priority.get(), startDate.get(), status.get(), dueDate.get(), dueIn.get(), caseID.get(), consumerID.get()};
        int k = 0;
        for (int j = 1; j <= 9; j++) {
            sa.get().assertThat(workQueue.workQueueColumnsValues.get(j).getText()).as("Column value is not displayed as " + str[k]).isEqualTo(str[k]);
            k++;
        }
    }

    /*This method to click on Expand Arrow link
    Author -Vidya
     */

    @And("I click on Expand Arrow link")
    public void clickOnExpandArrow() {
        waitFor(3);
        //jsClick(workQueue.expandArrow);
        click(workQueue.expandArrow);
    }

    /*This method to verify all headers are displayed when we expand the newly created task on
        Work Queue Page
    Author -Vidya
     */

    @Then("I verify all headers are displayed on newly created task")
    public void expandedHeaderCheck() {
        String[] str = {"Source", "CREATED BY", "CREATED ON", "CONSUMER NAME", "TASK INFORMATION", "TASK NOTES"};
        int j = 0;
        for (int i = 0; i < workQueue.expandedHeader.size(); i++) {
            if (i != 4) {
                sa.get().assertThat(workQueue.expandedHeader.get(i).getText()).as(str[j] + " Header is not displayed").isEqualTo(str[j]);
                j++;
            }
        }
    }


    @And("I will fill the required filed in create consumer page")
    //@Vidya - The script in this stem def is duplicated from "I populate Create Consumer fields for a new consumer"
    public void i_populate_Create_Consumer_fields_for_a_new_consumer() {
        clearAndFillTextWithActionClass(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillTextWithActionClass(createConsumer.consumerLN, newConsumer.get().get("surname").toString());

        consumerName.set(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        if (newConsumer.get().get("gender").equals("female")) {
            selectDropDown(createConsumer.consumerGender, "Female");
        } else {
            selectDropDown(createConsumer.consumerGender, "Male");
        }
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        waitFor(1);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
    }

    /*This method to verify all header values are displayed when we expand the newly created task
        and Consumer Id is displayed on Work Queue Page
    Author -Vidya
     */

    @Then("I verify all header values are displayed on newly created task and Consumer Id is displayed")
    public void checkHeaderValueAndConsumerID() {
        waitFor(2);
        String[] str = {"User", userNameData.get(), CRM_GeneralTaskStepDef.taskValues.get().get("startDate"), consumerName.get(), "-- --"};
        int j = 0;
        for (int i = 0; i < workQueue.expandedHeaderValue.size(); i++) {
            if (i != 4) {
                sa.get().assertThat(workQueue.expandedHeaderValue.get(i).getText()).as(str[j] + " Header is not displayed").isEqualTo(str[j]);
                j++;
                if (i == 5)
                    break;
            }
        }
        sa.get().assertThat(workQueue.workQueueColumnsValues.get(9).getText()).as("Column value is not displayed as " + CRM_GeneralTaskStepDef.taskValues.get().get("consumerID")).isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"));
    }

    /*This method to verify linked Case ID is displayed on Work Queue Page
    Author -Vidya
     */

    @Then("I verify linked Case ID is displayed")
    public void checkCaseId() {
        assertEquals(workQueue.workQueueColumnsValues.get(8).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("caseID"), "Column value is not displayed as " + caseID.get());
    }

    /*This method to create the general task in multiple times
    Author -Vidya
     */

    @When("I Create {string} {string} task")
    public void createNumberOfTask(String number, String taskName) {
        for (int i = 0; i < Integer.parseInt(number); i++) {
            i_create_the_task_on_create_task_page(taskName);
        }
    }

    /*This method click on claim next button present on work queue page
    Author -Vidya
     */

    @And("I click on Claim Next button")
    public void selectClaimNext() {
        waitForVisibility(workQueue.taskID, 10);
        taskID.set(workQueue.taskID.getText());
        waitFor(3);
        waitForVisibility(workQueue.claimNext, 10);
        click(workQueue.claimNext);
    }

    /*This method to Verify task slider is displayed
    Author -Vidya
     */

    @Then("Verify task slider is displayed")
    public void checkTaskSlider() {
        waitForVisibility(workQueue.taskDetailsSlider, 20);
        assertTrue(workQueue.taskDetailsSlider.isDisplayed(), "Task slider is not displayed");
        waitFor(2);
        //assertEquals(Driver.getDriver().getCurrentUrl(),taskSliderUrl,"Task slider is not displayed");
    }

    /*This method to click on save button on task slider
    Author -Vidya
     */
    @And("I click on save button on task slider")
    public void clickOnSaveButton() {
        waitForVisibility(workQueue.saveBtn, 10);
        workQueue.saveBtn.click();
    }

    /*This method to verify user is redirected to work queue page
    Author -Vidya
     */
    @Then("I verify user is redirected to work queue page")
    public void verifyIgoBackToWorkQueuePage() {
        waitForVisibility(myTask.workQueueTab, 10);
        Assert.assertTrue(myTask.workQueueTab.isDisplayed(), "User is not redirect to work queue pagee");
    }

    @Given("I will check whether it contains at least one {string} task if not I will create task")
    public void checkWhetherHasTaskIfNotAddTAsk(String taskName) {
        waitFor(6);
        if (workQueue.allRowTaskID.size() == 0 && workQueue.workQueueColumns.size() == 0) {
            if (workQueue.noRecord.isDisplayed())
                i_create_the_task_on_create_task_page(taskName);
        }
    }

    @Then("I verify all task status is Created or Escalated")
    public void verifyAllTaskStatusIsCreatedOrEscalated() {
        waitForVisibility(workQueue.allRowStatus.get(0), 10);
        int size = 1;
        if (myTask.lnkPageNations.size() > 1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
            size = Math.min(size, 10);
        }
        for (int i = 1; i <= size; i++) {
            workQueue.allRowStatus.forEach(x -> {
                waitFor(1);
                sa.get().assertThat(x.getText().equalsIgnoreCase("Created") || x.getText().equalsIgnoreCase("Escalated")).isTrue();
            });
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @Then("I verify all columns values are displayed is valid")
    public void verifyAllColumnsValuesAreDisplayedIsValid() {
        waitForVisibility(workQueue.allRowStatus.get(0), 10);
        sa.get().assertThat(hasOnlyDigits(workQueue.allRowTaskID.get(position.get()).getText())).as("Is not digit").isTrue();
        sa.get().assertThat(workQueue.allRowType.get(position.get()).getText()).as("Task type is mismatch").isEqualToIgnoringCase(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"));
        sa.get().assertThat(workQueue.allRowPriority.get(position.get()).getText()).as("priority is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
        sa.get().assertThat(workQueue.allRowStatusDate.get(position.get()).getText()).as("statusDate is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("startDate"));
        sa.get().assertThat(workQueue.allRowStatus.get(position.get()).getText()).as("status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
        sa.get().assertThat(workQueue.allRowDueDate.get(position.get()).getText()).as("dueDate is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate"));
        System.out.println("Due in from create task: " + CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").replace("Business ", ""));
        System.out.println("Due in from work queue: " + workQueue.allRowDueIn.get(position.get()).getText());
        sa.get().assertThat(workQueue.allRowDueIn.get(position.get()).getText()).as("dueIn is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").replace("Business ", ""));
        if (!CRM_GeneralTaskStepDef.taskValues.get().get("caseID").equals("") || !CRM_GeneralTaskStepDef.taskValues.get().get("caseID").isEmpty() || CRM_GeneralTaskStepDef.taskValues.get().get("caseID") != null)
            sa.get().assertThat(workQueue.allRowCaseId.get(position.get()).getText()).as("caseId is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseID"));
        if (!CRM_GeneralTaskStepDef.taskValues.get().get("consumerID").equals("") || !CRM_GeneralTaskStepDef.taskValues.get().get("consumerID").isEmpty() || CRM_GeneralTaskStepDef.taskValues.get().get("consumerID") != null)
            sa.get().assertThat(workQueue.allRowConsumerId.get(position.get()).getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID")) || workQueue.allRowConsumerId.get(position.get()).getText().equals("Incomplete App")).as("consumerId is mismatch").isTrue();
    }

    //refactorBy:Vidya Date:03-08-2020
    @Then("I verify all headers are displayed when I expand the task")
    public void verifyHeadersWhenIExpandTheTask() {
        waitForVisibility(workQueue.allRowExpandArrow.get(0), 10);
        String[] str = {"SOURCE", "CREATED BY", "CREATED ON", "NAME", "TASK INFORMATION"};
        int n = 0;
        scrollUpRobot();
        waitFor(1);
        //   jsClick(workQueue.allRowExpandArrow.get(0));
        for (int i = 0; i < str.length; i++) {
            assertTrue(Driver.getDriver().findElement(By.xpath("//p[text()='" + str[i] + "']")).isDisplayed());
        }
    }

    @Then("I verify all headers values are displayed when I expand the task")
    public void verifyHeadersValuesWhenIExpandTheTask() {
        waitForVisibility(workQueue.allRowExpandArrow.get(0), 10);
        int n = 0;
        scrollUpRobot();
        waitFor(1);
        for (int j = 0; j < workQueue.pageNumbers.size(); j++) {
            if (j != 0) {
                jsClick(workQueue.pageNumbers.get(j));
            }
            for (int i = 0; i < workQueue.allRowExpandArrow.size(); i++) {
                jsClick(workQueue.allRowExpandArrow.get(i));
                int g = i + 1;
                String s = g + "";
                List<WebElement> expandedHeader = Driver.getDriver().findElements(
                        By.xpath("(//h5[text()='TASK LIST']/..//table/tbody/tr[1])[" + s + "]/following-sibling::tr//p[2]"));
                n = 0;
                for (int k = 0; k < expandedHeader.size(); k++) {
                    if (k != 4) {
                        assertTrue(expandedHeader.get(k).isEnabled(), " Header Value is not displayed");
                        n++;
                    }
                }
                jsClick(workQueue.allRowExpandArrow.get(i));
            }
        }
    }

    @Then("I verify all tasks has initiate button")
    public void verifyAllTasksHasInitiateButton() {
        waitForVisibility(workQueue.allRowExpandArrow.get(0), 10);
        int size = 1;
        if (myTask.lnkPageNations.size() > 1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < workQueue.allRowStatus.size(); j++) {
                waitFor(1);
                if (!workQueue.taskTypes.get(j).getText().equals("Incomplete Contact Record") || !workQueue.taskTypes.get(j).getText().equals("General Three")) {
                    assertTrue(workQueue.allRowInitiateBtn.get(j).isDisplayed(),
                            "Initiate Button is not displayed");
                }
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        if (isElementDisplayed(myTask.lnkArrowBack))
            myTask.lnkArrowBack.click();
    }

    @Then("I verify first task Initiate button is working")
    public void verifyFirstTaskInitiateButton() {
        waitForVisibility(workQueue.allRowInitiateBtn.get(0), 10);
        jsClick(workQueue.allRowInitiateBtn.get(0));
        checkTaskSlider();
    }

    @And("I will check whether it contains at least six {string} task if not I will create task")
    public void verify6TasksAreThereIfNotAddTask(String taskName) {
        if (workQueue.allRowTaskID.size() < 6 && workQueue.pageNumbers.size() == 1) {
            int k = 6 - workQueue.allRowTaskID.size();
            for (int i = 0; i < k; i++) {
                i_create_the_task_on_create_task_page(taskName);
            }
        }
    }

    @Then("I verify pagination is available when more than five records are added")
    public void verify5RecordsAreThereInEachPage() {
        assertEquals(workQueue.allRowTaskID.size(), 5, "More than 5 records are present in page");
        assertTrue(workQueue.pageNumbers.size() > 1, "Pagination is not correct");
    }

    @Then("I verify sorting order of the tasks By Due Date")
    public void verifySortingOrderOfTask() {
        waitForVisibility(workQueue.allRowExpandArrow.get(0), 10);
        int n = 0;
        scrollUpRobot();
        waitFor(1);
        if (isElementDisplayed(myTask.lnkArrowForward))
            myTask.lnkArrowForward.click();
        waitFor(1);
        int size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
        if (isElementDisplayed(myTask.lnkArrowBack))
            click(myTask.lnkArrowBack);
        for (int j = 1; j <= size; j++) {
            assertTrue(ascendingOrderDates(workQueue.allRowDueDate),
                    "Tasks due date are not in according to sorting order");
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(2);
            }
        }
    }

    @Then("I check if the task has status Escalate if not update to Escalate")
    public void verifyTaskStatus() {
        waitForVisibility(workQueue.allRowStatus.get(0), 10);
        waitFor(5);
        taskId.set(workQueue.allRowTaskID.get(0).getText());
        if (!workQueue.allRowStatus.get(0).getText().equals("Escalated")) {
            jsClick(workQueue.allRowInitiateBtn.get(0));
            checkTaskSlider();
            selectDropDown(workQueue.taskSliderStatus, "Escalated");
            taskManagementStepDef.get().clickOnSaveTaskSlider();
            waitFor(1);
            verifyIgoBackToWorkQueuePage();
            i_navigate_to_newly_created_task();
        }
        CRM_TaskManagementStepDef.flag.set(true);
    }

    //refactorBy: Vidya Date:03-08-2020
    @And("I will change the task status from Escalate to Open")
    public void changeStatusEscalateToInProgress() {
        waitFor(1);
        jsClick(workQueue.allRowInitiateBtn.get(0));
        checkTaskSlider();
        taskManagementStepDef.get().clickOnSaveTaskSlider();
        verifyIgoBackToWorkQueuePage();
    }

    @And("I navigate to MyTask tab")
    public void navigateToMyTaskTab() {
        waitForVisibility(myTask.myTasksTab, 10);
        waitFor(1);
        jsClick(myTask.myTasksTab);
    }

    //refactorBy: Vidya Date:03-08-2020
    @And("I will again change the task status to Escalate")
    public void reChangeTheTaskStatusToEscalate() {
        waitForVisibility(workQueue.allRowTaskID.get(0), 10);
        waitFor(2);
        for (int i = 0; i < workQueue.allRowTaskID.size(); i++) {
            System.out.println(workQueue.allRowTaskID.get(i).getText() + "   " + taskId.get());
            if (workQueue.allRowTaskID.get(i).getText().equals(taskId.get())) {
                jsClick(workQueue.allRowInitiateBtn.get(i));
                checkTaskSlider();
                selectDropDown(workQueue.taskSliderStatus, "Escalated");
                taskManagementStepDef.get().clickOnSaveTaskSlider();
                break;
            }
        }
    }

    @And("I navigate to WorkQueue tab")
    public void navigateToWorkQueueTab() {
        waitForVisibility(myTask.workQueueTab, 10);
        waitFor(2);
        jsClick(myTask.workQueueTab);
    }

    @Then("I verify Re-Escalate previously Escalated task")
    public void verifyReEscalateThePreviouslyEscalatedTask() {
        waitForVisibility(workQueue.allRowTaskID.get(0), 10);
        waitFor(1);
        boolean flag = false;
        for (int i = 0; i < workQueue.allRowTaskID.size(); i++) {
            System.out.println(workQueue.allRowTaskID.get(i).getText() + "  " + CRM_TaskManagementStepDef.taskId.get());
            if (workQueue.allRowTaskID.get(i).getText().equals(CRM_TaskManagementStepDef.taskId.get())) {
                assertEquals(workQueue.allRowStatus.get(i).getText(), "Escalated",
                        "previously Escalated task is not Re-Escalated");
                flag = true;
                break;
            }
        }
        assertTrue(flag, "previously Escalated task is not Re-Escalated");
    }

    @Then("I verify claim next button is present on screen")
    public void verifyClaimNextButtonIsPresent() {
        waitForVisibility(workQueue.claimNext, 12);
        assertTrue(workQueue.claimNext.isDisplayed(), "Claim Next button is not present");
    }

    @Then("I verify all task status is Created")
    public void verifyAllTaskStatusIsCreated() {
        waitForVisibility(workQueue.allRowStatus.get(0), 10);
        for (int j = 0; j < workQueue.pageNumbers.size(); j++) {
            if (j != 0) {
                jsClick(workQueue.pageNumbers.get(j));
            }
            for (int i = 0; i < workQueue.allRowStatus.size(); i++) {
                Assert.assertEquals(workQueue.allRowStatus.get(i).getText(), "Created");
            }
        }
    }

    @And("I ensure Work Queue page has at least one task with status other than {string} and I navigate to view task")
    public void ensureAtLeastOneTaskWithStatusOtherThanOnHold(String status) {
        boolean flag = true;
        if (myTask.taskIDs.size() == 0) {
            flag = true;
            /*if(myTask.noRecordsMsg.isDisplayed()){
            }*/
        } else {
            l1:
            for (int j = 0; j < myTask.lnkPageNations.size(); j++) {
                for (int i = 0; i < myTask.getStatusValues.size(); i++) {
                    waitFor(3);
                    if (!myTask.getStatusValues.get(i).getText().equalsIgnoreCase(status)) {
                        flag = false;
                        taskId.set(myTask.taskIDs.get(i).getText());
                        myTask.taskIDs.get(i).click();
                        break l1;
                    }
                }
                if (j + 1 < myTask.lnkPageNations.size())
                    myTask.lnkPageNations.get(j + 1).click();
                waitFor(3);
            }
        }
        if (flag) {
            i_create_the_task_on_create_task_page("General");
            myTask.taskIDs.get(0).click();
        }
    }

    @When("I Create the Task on Create task page")
    public void i_create_the_task_on_create_task_page() {
        waitForVisibility(dashBoard.btnMenuList, 30);
        click(dashBoard.btnMenuList);
        waitForVisibility(dashBoard.createTaskMenu, 10);
        click(dashBoard.createTaskMenu);
        waitForVisibility(dashBoard.subMenuList, 10);
        click(dashBoard.subMenuList);
        waitForVisibility(createGeneralTask.lblStatusDate, 10);
        startDate.set(createGeneralTask.lblStatusDate.getText());
        dueDate.set(createGeneralTask.lblDueDate.getText());
        dueIn.set(createGeneralTask.lblDueIn.getText());
        dueIn.set(dueIn.get().replace("Business Days", "Days"));
        taskType.set(createGeneralTask.lstTaskType.getText());
        priority.set(createGeneralTask.lstTaskPriority.getText());
        status.set(createGeneralTask.txtTaskStatus.getAttribute("value"));
        caseID.set(createGeneralTask.lblCaseId.getText());
        consumerID.set(createGeneralTask.lblConsumerId.getText());
        System.out.println(consumerID.get());
        waitForVisibility(createGeneralTask.btnSave, 10);
        jsClick(createGeneralTask.btnSave);
        waitForVisibility(createGeneralTask.btnOkSuccessDialog, 10);
        click(createGeneralTask.btnOkSuccessDialog);
    }

    @And("I click on Work Queue tab")
    public void clickOnWorkQueueTab() {
        waitFor(2);
        click(myTask.workQueueTab);
    }

    @Then("Verify view page has {string} {string} {string} {string} {string} fields ad values")
    public void verifyTaskDetails(String cmplAbt, String exAppId, String exCaseId, String name, String reason) {
        waitForVisibility(myTask.cmplAbtDrDn, 10);
        assertEquals(myTask.cmplAbtValue.getText(), cmplAbt, "Complaint About drop down value is wrong");
        assertTrue(myTask.cmplAbtDrDn.isDisplayed(), "Complaint About drop down header is not displayed");
        assertEquals(myTask.exAppIdValue.getText(), exAppId, "External Application id value is wrong");
        assertTrue(myTask.exAppIdTxBx.isDisplayed(), "External Application id header is not displayed");
        assertEquals(myTask.exCaseIdValue.getText(), exCaseId, "External Case Id value is wrong");
        assertTrue(myTask.exCaseIdTxBx.isDisplayed(), "External Case Id header is not displayed");
        assertEquals(myTask.nameValue.getText(), name, "Name value is wrong");
        assertTrue(myTask.nameTxBx.isDisplayed(), "Name header is not displayed");
        assertEquals(myTask.reasonValue.getText(), reason, "Reason drop down value is wrong");
        assertTrue(myTask.reasonDrDn.isDisplayed(), "Reason drop down header is not displayed");
    }

    @And("Verify the order of fields displayed in view page is same as NJ create task page")
    public void verifyOrderOfFieldDisplayedIViewTaskPage() {
        waitForVisibility(myTask.cmplAbtDrDn, 10);
        for (int i = 0; i < myTask.njViewAdditionalFields.size(); i++) {
            assertTrue(CRM_GeneralTaskStepDef.orderOfFields.get().get(i).contains(myTask.njViewAdditionalFields.get(i).getText()),
                    CRM_GeneralTaskStepDef.orderOfFields.get().get(i) + "is not displayed in order of create page");
        }
    }

    @Then("Verify PDF viewer is initiated for linked Correspondence ID")
    public void verify_PDF_viewer_is_initiated_for_linked_Correspondence_ID() {
        waitFor(5);
        tabs.set(new ArrayList<>(Driver.getDriver().getWindowHandles()));
        if (tabs.get().size() > 1) {
            Driver.getDriver().switchTo().window(tabs.get().get(1));
            String pdf_url = Driver.getDriver().getCurrentUrl();
            assertTrue(pdf_url.contains("https://max-nonprod-qa.apigee.pcf-maersk.com"), "PDF url not matches");
        } else fail("PDF window is not displayed");
        Driver.getDriver().close();
        waitFor(3);
        Driver.getDriver().switchTo().window(tabs.get().get(0));
        waitFor(3);
    }

    @Then("User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID")
    public void user_is_navigated_to_the_View_Correspondence_Details_UI_for_the_linked_Inbound_Correspondence_ID() {
        waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.inboundCorrDetHeader, 10);
        assertTrue(isElementDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.inboundCorrDetHeader));

    }

    @When("I Navigate to task which we created from api using task id and click on initiate button")
    public void getTheIndexOfTaskIDAndClickOnInitiateButton() {
        waitFor(1);
        int size = 1;
        boolean recordFound = false;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }

        l1:
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < myTask.taskListRows.size(); j++) {
                waitFor(1);
                String taskIDFromUI = myTask.taskIDs.get(j).getText();
                if (myTask.taskIDs.get(j).getText().equals(String.valueOf(apitm.get().taskId.get()))) {
                    CRM_TaskManagementStepDef.taskId.set(myTask.taskIDs.get(j).getText());
                    CRM_TaskManagementStepDef.srID.set(String.valueOf(Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) - 1));
                    actions.click(myTask.taskInitiateButtons.get(j)).build().perform();
                    waitFor(5);
                    recordFound = true;
                    break l1;
                }
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Task ID not found");
    }

    //Task list is not displaying and Getting stale element error hence added below wait :Basha
    @And("I click task id to get the results in descending order")
    public void click_task_id_to_get_the_results_in_descending_order() {
        boolean sorted = false;
        for (int i = 0; i <= 3; i++) {
            //waitForVisibility(workQueue.taskIDColumnHeader, 15);
            waitFor(10);
            scrollUpUsingActions(3);
            jsClick(workQueue.taskIDColumnHeader);
            waitFor(2);
            List<Integer> displayedInOrder = new ArrayList<>();
            List<Integer> toSort = new ArrayList<>();
            for (int j = 0; j < myTask.taskIDs.size(); j++) {
                int taskID = Integer.parseInt(myTask.taskIDs.get(j).getText());
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


    @Then("Verify PDF viewer is not initiated")
    public void verify_PDF_viewer_is_not_initiated() {
        waitForPageToLoad(5);
        tabs.set(new ArrayList<>(Driver.getDriver().getWindowHandles()));
        assertEquals(tabs.get().size(), 1, "PDF Viewer is initiated");
    }

    @When("I Navigate to view task details page by using taskId which we created from api")
    public void getTheIndexOfTaskIDAndClickOnTaskId() {
        waitFor(2);
        boolean recordFound = false;

        for (int i = 1; i <= myTask.lnkPageNations.size(); i++) {
            for (WebElement row : myTask.taskIDs) {
                if (row.getText().equals(String.valueOf(apitm.get().taskId.get()))) {
                    click(row);
                    waitFor(2);
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
            else if (i != myTask.lnkPageNations.size()) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        if (!recordFound)
            fail("Task ID not found");
    }

    @Then("Verify all the dynamic fields of templates are displayed with null value")
    public void verifyDynamicFieldsOfCorrespondanceInViewPage(List<String> additionalFlds) {
        tabs.set(new ArrayList<>(additionalFlds));
        for (int i = 0; i < tabs.get().size(); i++) {
            assertTrue(isElementDisplayed(Driver.getDriver().
                            findElement(By.xpath("//p[text()='" + tabs.get().get(i) + "']"))),
                    " Dynamic field " + tabs.get().get(i) + " are not displayed");
            assertTrue(Driver.getDriver().
                            findElement(By.xpath("//p[text()='" + tabs.get().get(i) + "']/following-sibling::h6")).isEnabled(),
                    " Dynamic field " + tabs.get().get(i) + " are not displayed");
        }
    }

    @Then("I verify the task details are displayed in work queue when expanded")
    public void verifyTaskDetailsWhenExpanded() {
        waitFor(3);

        sa.get().assertThat(workQueue.createdByVlu.get(position.get()).getText()).as("createdBy value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
        if (CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("") || CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo") == null || CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").isEmpty())
            sa.get().assertThat(workQueue.taskInfoVlu.get(position.get()).getText()).as("Task Information is mismatch").isEqualTo("-- --");
        else
            sa.get().assertThat(workQueue.taskInfoVlu.get(position.get()).getText()).as("Task Information is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
        sa.get().assertThat(workQueue.sourceVlu.get(position.get()).getText()).as("Source value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("source"));
        sa.get().assertThat(workQueue.createdOnVlu.get(position.get()).getText()).as("CreatedOn is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_on"));
        System.out.println("Consumer name details: " + CRM_GeneralTaskStepDef.taskValues.get().get("consumerName"));
        if (CRM_GeneralTaskStepDef.taskValues.get().get("consumerName").isEmpty()) {
            sa.get().assertThat(workQueue.nameVlu.get(position.get()).getText()).as("consumerName value is mismatch for not linked consumer").isEqualTo("-- --");
        } else {
            sa.get().assertThat(workQueue.nameVlu.get(position.get()).getText()).as("consumerName value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("consumerName"));
            //sa.get().assertThat(workQueue.taskNoteVlu.get(position.get()).getText()).as("Task Note is mismatch").isEqualTo("-- --");
        }
    }

    @Then("I verify Task slider Status drop down values")
    public void verifyTaskSliderStatusDropDownValues(List<String> expectedValues) {
        waitForVisibility(workQueue.taskSliderStatus, 5);
        workQueue.taskSliderStatus.click();
        waitFor(1);
        List<String> actualValues = new LinkedList<>();
        for (int i = 0; i < workQueue.taskSliderStatusDrDw.size(); i++) {
            actualValues.add(workQueue.taskSliderStatusDrDw.get(i).getText());
        }
        Assert.assertEquals(actualValues, expectedValues, "Status drop down values are incorrect");
        click(workQueue.taskSliderStatusDrDw.get(0));
        waitFor(3);
    }

    @And("I select value {string} from Task slider Status drop down")
    public void SelectValueFromTaskSliderStatusDD(String taskStatus) {
        waitForVisibility(workQueue.taskSliderStatus, 5);
        selectDropDown(workQueue.taskSliderStatus, taskStatus);
        waitFor(3);
    }

    @Then("I verify Task slider Disposition drop down values")
    public void verifyTaskSliderDispositionDropDownValues(List<String> expectedValues) {
        waitForVisibility(workQueue.taskSliderDisposition, 5);
        workQueue.taskSliderDisposition.click();
        waitFor(2);
        List<String> actualValues = new LinkedList<>();
        for (int i = 1; i < workQueue.taskSliderDispositionDrDw.size(); i++) {
            actualValues.add(workQueue.taskSliderDispositionDrDw.get(i).getText());
        }
        Assert.assertEquals(actualValues, expectedValues, "Disposition drop down values are incorrect");
        click(workQueue.taskSliderDispositionDrDw.get(1));
        waitFor(3);
    }

    @Then("I click save without selecting Disposition dd value to verify mandatory error message")
    public void verifyMandatoryErrorMsgForDispositionField() {
        myTask.taskSilderSave.click();
        waitForVisibility(workQueue.taskSliderDisposition, 10);
        Assert.assertTrue(workQueue.taskSliderDisposition.isDisplayed(), "Disposition drop down is not displayed");
        Assert.assertTrue(workQueue.mandatoryErrorMsgFrDisposition.isDisplayed(), "Mandatory error message is not displayed");
    }

    @And("I select value {string} from Task slider Disposition drop down")
    public void SelectValueFromTaskSliderDispositionDD(String taskDisposition) {
        waitForVisibility(workQueue.taskSliderDisposition, 5);
        selectDropDown(workQueue.taskSliderDisposition, taskDisposition);
        waitFor(3);
    }

    @And("Verify Task routed to Users with User Role")
    public void verifyTaskRoutedToUsersWithUserRole() {
        waitFor(3);
        taskID.set(workQueue.getTaskID.getText());
        assertEquals(workQueue.taskinfovalue.getText(), "Different Role", "Task info value Not correct");
        assertEquals(workQueue.createdByValue.getText(), "Service AccountOne", "Account Role is Not correct");
    }

    @And("Verify I will NOT see General Tasks with non CSR Role")
    public void verifyIWillNOTSeeGeneralTasksWithNonCSRRole() {
        waitForVisibility(workQueue.allRowStatus.get(0), 10);
        int size = 1;
        if (myTask.lnkPageNations.size() > 1 && isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < workQueue.taskTypes.size(); j++) {
                waitFor(1);
                assertNotEquals(workQueue.taskTypes.get(j).getText(), "General",
                        "User has task type which they don't have permission to work on ");
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    /*ModifiedBy: vidya Date:-11/01/2020 Reason: Escap character problem resolved*/
    @Then("I verify all field are displayed on My Task or Work Queue page")
    public void i_verify_all_field_are_displayed_on_My_Task_Work_Queue_page() {
        sa.get().assertThat(myTask.myTasksTab.isDisplayed()).as("My Tab tab is not displayed").isTrue();
        sa.get().assertThat(myTask.workQueueTab.isDisplayed()).as("Work Queue tab is not displayed").isTrue();
        sa.get().assertThat(myTask.taskList.isDisplayed()).as("Task List header is not displayed").isTrue();
        sa.get().assertThat(myTask.taskIdColumn.isDisplayed()).as("Task Id is not displayed").isTrue();
        sa.get().assertThat(myTask.typeColumn.isDisplayed()).as("Task Type is not displayed").isTrue();
        sa.get().assertThat(myTask.statusColumn.isDisplayed()).as("Status is not displayed").isTrue();
        sa.get().assertThat(myTask.statusDateColumn.isDisplayed()).as("Status Date is not displayed").isTrue();
        sa.get().assertThat(myTask.dueDateColumn.isDisplayed()).as("Due Date is not displayed").isTrue();
        sa.get().assertThat(myTask.dueInColumn.isDisplayed()).as("Due In is not displayed").isTrue();
        if (LoginStepDef.projectName1.get().equals("NJ-SBE")) {
            sa.get().assertThat(myTask.getInsuredCaseIdColumn.isDisplayed()).as("Case Id is not displayed").isTrue();
            sa.get().assertThat(myTask.getInsuredConsumerIdColumn.isDisplayed()).as("Consumer Id is not displayed").isTrue();
        } else {
            sa.get().assertThat(myTask.caseIdColumn.isDisplayed()).as("Case Id is not displayed").isTrue();
            sa.get().assertThat(myTask.consumerIdColumn.isDisplayed()).as("Consumer Id is not displayed").isTrue();
        }
    }

    @And("I verify service request id which I get from response is does not appear in work queue")
    public void verifySRsAreNotPresentInWorkQueue() {
        myTask.lnkPageNations.stream().limit(10).forEach(page -> {
            myTask.taskIDs.forEach(taskId -> assertNotEquals(taskId.getText(), CRM_TaskManagementStepDef.taskId.get()));
            click(page);
            waitFor(2);
        });
    }

    @When("I will select reason for cancel drop down value as {string}")
    public void i_update_the_disposition_in_task_slider_as(String reasonForCancel) {
        waitFor(2);
        selectDropDown(myTask.reasonForCancelDropDown, reasonForCancel);
        CRM_GeneralTaskStepDef.taskValues.get().put("reasonForCancel", reasonForCancel);
    }

    @When("I will click on newly created task id")
    public void iWillClickOnNewlyCreatedTask() {
        waitFor(2);
        boolean recordFound = false;
        l1:
        for (int j = 1; j <= 5; j++) {
            for (int i = 0; i < myTask.taskTypes.size(); i++) {
                waitFor(1);
                if (myTask.taskTypes.get(i).getText().equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"))) {
                    CRM_TaskManagementStepDef.taskId.set(myTask.taskIDs.get(i).getText());
                    CRM_TaskManagementStepDef.srID.set(String.valueOf(Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) - 1));
                    scrollUpRobotKey();
                    jsClick(myTask.taskIDs.get(i));
                    recordFound = true;
                    break l1;
                }
            }
            if (j != 5) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Newly created Task is not available");
    }

    @And("I get the position of newly created task")
    public void Ik() {
        waitFor(2);
        for (int i = 0; i < workQueue.allRowTaskID.size(); i++) {
            if (workQueue.allRowTaskID.get(i).getText().equalsIgnoreCase(CRM_TaskManagementStepDef.taskId.get())) {
                position.set(i);
                break;
            }
        }
    }

    @Then("I verify task slider is closed")
    public void checkTaskSliderIsClosed() {
        assertFalse(isElementDisplayed(workQueue.taskDetailsSlider), "Task slider is displayed");
    }

    @When("I will navigate to newly created task by clicking on task id")
    public void iWillClickOnNewlyCreatedTaskId() {
        waitFor(2);
        boolean recordFound = false;
        l1:
        for (int j = 1; j <= 5; j++) {
            for (int i = 0; i < myTask.taskTypes.size(); i++) {
                waitFor(1);
                if (myTask.taskIDs.get(i).getText().equalsIgnoreCase(CRM_TaskManagementStepDef.taskId.get())) {
                    System.out.println(myTask.taskIDs.get(i).getText() + " " + CRM_TaskManagementStepDef.taskId.get());
                    CRM_TaskManagementStepDef.srID.set(String.valueOf(Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) - 1));
                    scrollUpRobotKey();
                    jsClick(myTask.taskIDs.get(i));
                    recordFound = true;
                    break l1;
                }
            }
            if (j != 5) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Newly created Task is not available");
    }

    @When("I verify that first task is {string} and linked to consumer")
    public void i_verify_that_first_task_is_and_linked_to_consumer(String taskType) {
        assertEquals(taskType, workQueue.taskTypes.get(0).getText(), "Task type isnt validated in work queue");
        assertEquals(navigateThroughContactDashboardStepDef.get().caseConsumerId.get(), workQueue.allRowConsumerId.get(0).getText(), "Consumer id isnt validated in work queue");

    }

    @When("I verify the number {int} of search results")
    public void i_verify_number_of_search_results(int number) {
        //WebElement pageNumber5=Driver.getDriver().findElement(By.xpath("//a[@aria-label='Go to page number 5']"));
        //waitForVisibility(pageNumber5,35);;
        waitFor(2);
        waitFor(60);
        int numberOfpages = workQueue.pageNumbers.size() - 1;
        System.out.println("numberOfpages = " + numberOfpages);
        System.out.println("numberOfpages = " + numberOfpages);
        int resultsPerPage = contactRecord.defaultResults.size();
        System.out.println("resultsPerPage = " + resultsPerPage);
        int totalResultSet = numberOfpages * resultsPerPage;
        System.out.println("totalResultSet = " + totalResultSet);
        assertEquals(totalResultSet, number, "result set should be 100");

    }

    @When("I verify the number {int} of search results for each page when you navigate back")
    public void i_verify_number_of_search_results_for_each_page_when_you_navigate_back(int number) {

        List<WebElement> pageNumbers = workQueue.pageNumbers;
        for (int i = pageNumbers.size() - 1; i >= 0; i--) {
            pageNumbers.get(i).click();
            assertEquals(contactRecord.defaultResults.size(), number);

        }

    }

    @When("I verify the default {int} in dropdown")
    public void i_verify_default_and_all_diplay_numbers_in_dropdown(int num) {
        waitFor(2);
        //default show 20
        Assert.assertTrue(contactRecord.show20DropDown.isDisplayed(), num + " is not displayed");

    }

    @When("I verify all display numbers in dropdown")
    public void i_verify_default_and_all_diplay_numbers_in_dropdown() {
        contactRecord.showDropdown.click();
        waitFor(4);
        Assert.assertTrue(contactRecord.show5Dropdown.isDisplayed());
        waitFor(2);
        Assert.assertTrue(contactRecord.show50Dropdown.isDisplayed());
        waitFor(2);
        Assert.assertTrue(contactRecord.show10Dropdown.isDisplayed());
        waitFor(2);

    }

}

