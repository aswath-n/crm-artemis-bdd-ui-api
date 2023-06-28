package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APICreateConsumerContactController;
import com.maersk.crm.api_step_definitions.APITMBusinessDayRestController;
import com.maersk.crm.api_step_definitions.APITMHolidayController;
import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
import static com.maersk.crm.step_definitions.LoginStepDef.projectName1;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.testng.Assert.*;

public class CRM_GeneralTaskStepDef extends CRMUtilities implements ApiBase {

    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();
    CRMTaskSearchPage taskSearch = new CRMTaskSearchPage();
    CRMEditConsumerProfilePage crmEditConsumerProfilePageOne = new CRMEditConsumerProfilePage();
    CRMLinksComponentPage linksComponentPage = new CRMLinksComponentPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();

    public static final ThreadLocal<HashMap<String, String>> taskValues = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<ArrayList<String>> orderOfFields = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<ArrayList<String>>  displayedInOrder = ThreadLocal.withInitial(ArrayList::new);

    CRMDashboardPage dashboard = new CRMDashboardPage();
    public static final ThreadLocal<String> actionTaken1stVlu = ThreadLocal.withInitial(String::new);
    Actions actions = new Actions(Driver.getDriver());
    public static final ThreadLocal<ArrayList<String>> selectValue = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<String> projectDate = ThreadLocal.withInitial(String::new);
    ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();
    InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
//    CRM_CaseConsumerProfileSearchStepDef caseConsumerProfileSearchStepDef = new CRM_CaseConsumerProfileSearchStepDef();
    CRMSRViewEditPage srViewEditPage = new CRMSRViewEditPage();
//    CRM_NavigateThroughContactDashboardStepDef contactDashboardStepDef = new CRM_NavigateThroughContactDashboardStepDef();
    CRMTaskServiceRequiestTabPage crmTaskServiceTab = new CRMTaskServiceRequiestTabPage();
    CRMApplicationTrackingPage trackingPage = new CRMApplicationTrackingPage();
    private final ThreadLocal<APICreateConsumerContactController> apiCreateConsumerContactController = ThreadLocal.withInitial(APICreateConsumerContactController::new);

    final ThreadLocal<String> ConsumerID = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> CaseID = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> taskId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> externalConsumerID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> externalCaseID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> taskId1 = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<ArrayList<String>> assigneeBUlist = ThreadLocal.withInitial(ArrayList::new);
    CRMTaskEditHistoryPage editHistory = new CRMTaskEditHistoryPage();

    @Given("I navigate to {string} task page")
    public void navigateToGeneralTask(String taskName) {
        waitForVisibility(dashBoard.btnMenuList, 25);
        click(dashBoard.btnMenuList);
        waitFor(4);
        hover(dashBoard.createTaskMenu);
        waitFor(4);
        WebElement task = Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']"));
        waitFor(3);
        scrollToElement(task);
        waitFor(4);
        try {
            task.click();
        } catch (StaleElementReferenceException e) {
            Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']")).click();
        }
        if (!taskName.contains(createGeneralTask.lblTaskType.getText())) {
            click(createGeneralTask.lblBackArrow);
            waitForVisibility(dashBoard.btnMenuList, 20);
            dashBoard.btnMenuList.click();
            waitFor(2);
            waitForVisibility(dashBoard.createTaskMenu, 10);
            waitFor(2);
            dashBoard.createTaskMenu.click();
            waitForVisibility(dashBoard.subMenuList, 10);
            scrollToElement(task);
            waitFor(2);
            try {
                task.click();
            } catch (StaleElementReferenceException e) {
                Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']")).click();
            }
        }
        waitFor(1);
    }


    @When("I navigate to given {string} task page")
    public void navigateToGivenTaskScreen(String taskName) {
        waitFor(5);
        click(dashBoard.btnMenuList);
        waitFor(5);
        click(dashBoard.createTaskMenu);
        waitForVisibility(dashBoard.subMenuList, 10);
        System.out.println("//span[text()='" + taskName + "']");
        List<WebElement> task = Driver.getDriver().findElements(By.xpath("//div[@id='sub-menu-list']//ul/li"));
        for (WebElement taskname : task) {
            scrollToElement(taskname);
            if (taskname.getText().contains(taskName)) {
                Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']")).click();
            }
            break;
        }

    }

    @Then("I verify create task fields displayed")
    public void verifyFieldsInCreateTask() {
        waitFor(2);
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblCaseId)).as("Case Id is not displayed on create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblConsumerId)).as("Consumer Id is not displayed on create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblDueDate)).as("Due Date is not displayed on create task page").isTrue();
        sa.get().assertThat(createGeneralTask.lblDueIn.getText()).as("Due In value is wrong in create task page").isEqualTo("5 Business Days");
        sa.get().assertThat(createGeneralTask.lblSource.getText()).as("Source value is wrong in create task page").isEqualTo("User");
        sa.get().assertThat(createGeneralTask.lblStatusDate.getText()).as("Status Date value is wrong in create task page").isEqualTo(createGeneralTask.projectDateAtHeader.getText());
        sa.get().assertThat(createGeneralTask.lstTaskType.getText()).as("Task Type value is wrong in create task page").isEqualTo("General");
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lstAssignee)).as("Assignee is not displayed on create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lstAssigneeBusinessUnit)).as("Assignee Business Unit is not displayed on create task page").isTrue();
        sa.get().assertThat(createGeneralTask.lstTaskPriority.getText()).as("Priority value is wrong in create task page").isEqualTo("2");
        sa.get().assertThat(isElementDisplayed(createGeneralTask.txtTaskInfo)).as("Task Info is not displayed on create task page").isTrue();
        sa.get().assertThat(createGeneralTask.txtTaskStatus.getText()).as("Status value is wrong in create task page").isEqualTo("Created");
    }

    @Then("I verify create task fields displayed on screen")
    public void verifyFieldsInCreateTaskPage() {
        waitFor(2);
        if (!projectName1.get().equalsIgnoreCase("CoverVA")) {
            sa.get().assertThat(isElementDisplayed(createGeneralTask.lblCaseId)).as("case id is not present in create task ui").isTrue();
        } else {
            sa.get().assertThat(isElementDisplayed(createGeneralTask.lblCaseId)).as("case id is present in create task ui for coverVA Project").isFalse();
        }
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblConsumerId)).as("Consumer ID is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblDueDate)).as("Due Date is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblDueIn)).as("DueIn filed is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblSource)).as("Source filed is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblStatusDate)).as("Status Date filed is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lstTaskType)).as("Task Type filed is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lstAssignee)).as("Assignee filed is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lstTaskPriority)).as("Priority filed is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.txtTaskInfo)).as("Task Info filed is not displaying in create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.txtTaskStatus)).as("Status filed is not displaying in create task page").isTrue();
    }

    @When("I click on save button in create task page")
    public void clickSaveButton() {
        waitForVisibility(createGeneralTask.btnSave, 10);
        taskValues.get().put("startDate", createGeneralTask.lblStatusDate.getText());
        taskValues.get().put("dueDate", createGeneralTask.lblDueDate.getText());
        if (createGeneralTask.txtTaskStatus.getText().equalsIgnoreCase("Complete"))
            taskValues.get().put("dueIn", "-- --");
        else
            taskValues.get().put("dueIn", createGeneralTask.lblDueIn.getText());
        CRM_TaskManagementStepDef.dueIn.set(createGeneralTask.lblDueIn.getText());
        taskValues.get().put("taskType", createGeneralTask.lstTaskType.getText());
        taskValues.get().put("priority", createGeneralTask.lstTaskPriority.getText());
        taskValues.get().put("status", createGeneralTask.txtTaskStatus.getText());
        if (!projectName1.get().equalsIgnoreCase("CoverVA")) {
            taskValues.get().put("caseID", createGeneralTask.lblCaseId.getText());
        }
        taskValues.get().put("consumerID", createGeneralTask.lblConsumerId.getText());
        if (createGeneralTask.lstAssignee.getAttribute("value").isEmpty() || createGeneralTask.lstAssignee.getAttribute("value").equals(" "))
            taskValues.get().put("assignee", "-- --");
        else
            taskValues.get().put("assignee", createGeneralTask.lstAssignee.getAttribute("value"));

        if (createGeneralTask.txtTaskInfo.getText().isEmpty())
            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", "-- --");
        else
            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", createGeneralTask.txtTaskInfo.getText());
        taskValues.get().put("source", "User");
        taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
        taskValues.get().put("created_by", createGeneralTask.userAccountName.getText());
        taskValues.get().put("created_on", createGeneralTask.lblStatusDate.getText());
        if (isElementDisplayed(createGeneralTask.lblContactRecordId)) {
            taskValues.get().put("contactRecord", createGeneralTask.lblContactRecordId.getText());
        }
        scrollUpRobotKey();
        waitFor(1);
        scrollDownUsingActions(1);
        waitFor(3);
        click(createGeneralTask.btnSave);
        waitFor(1);
    }

    @Then("I verify task mandatory fields error message {string}")
    public void verifyTaskTypeIsMandatoryField(String expectedMessage) {
        expectedMessage = expectedMessage + " is required and cannot be left blank";
        try {
            sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath
                    ("//p[text()='" + expectedMessage + "']")))).as(expectedMessage + " Mandatory Field level error message is not displayed").isTrue();
        } catch (NoSuchElementException e) {
            sa.get().fail(expectedMessage + " Mandatory Field level error message is not displayed");
        }
    }

    @When("I select task type as {string} priority as {string} assignee as {string} and I enter task info as {string}")
    public void SelectTaskDetails(String taskType, String priority, String assignee, String taskInfo) {
        waitForVisibility(createGeneralTask.lstTaskPriority, 10);
        if (!priority.isEmpty()) {
            selectDropDown(createGeneralTask.lstTaskPriority, priority);
            taskValues.get().put("priority", priority);
        }
        if (!assignee.isEmpty()) {
            if (assignee.equals("Service")) {
                assignee = createGeneralTask.userAccountName.getText();
            }
            sendKeyToTextField(createGeneralTask.lstAssignee, assignee);
            waitFor(1);
        }
        if (!taskInfo.isEmpty())
            sendKeyToTextField(createGeneralTask.txtTaskInfo, taskInfo);
    }

    @Then("I verify task type is selected as {string}")
    public void verifySelectedTaskType(String taskType) {
        waitFor(2);
        waitForVisibility(createGeneralTask.lstTaskType, 10);
        sa.get().assertThat(createGeneralTask.lblSelectedTaskType.getText()).as("Task Type name miss match").isEqualToIgnoringCase(taskType);
    }

    @Then("I verify Assignee Business Unit dropdown displays only BU that are associated to the task type")
    public void verifyBUassociatedToTheTask() {
        waitForVisibility(createGeneralTask.lstAssigneeBusinessUnit, 10);
        createGeneralTask.lstAssigneeBusinessUnit.click();
        synchronized (assigneeBUlist){assigneeBUlist.set(new ArrayList<>());}
        for (int i = 1; i < createGeneralTask.assigneeBusinessUnitListValues.size(); i++) {
            assigneeBUlist.get().add(createGeneralTask.assigneeBusinessUnitListValues.get(i).getText());
        }
        assertEquals(APITaskManagementController.businessUnitNames.get(), assigneeBUlist.get());
    }

    @Then("I verify Contact information is populated on the screen")
    public void i_verify_Contact_information_is_populated_on_the_screen() {

        String linkId = null;
        String linkName = null;
        boolean recordFound = false;
        for (WebElement row : createGeneralTask.linkRecordRows) {
            linkId = row.findElement(By.xpath("./td[3]")).getText();
            linkName = row.findElement(By.xpath("./td[4]")).getText();
            if (linkName.equalsIgnoreCase("Contact Record")) {
                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);
        assertNotNull(linkId);
    }

    @Then("I verify Success message is displayed for task")
    public void i_verify_Success_message_is_displayed_for_task() {
        waitForVisibility(createGeneralTask.lblSuccessMessage, 10);
        assertTrue(isElementDisplayed(createGeneralTask.lblSuccessMessage));
    }

    @When("I click on cancel button on create task type screen")
    public void i_click_on_cancel_button_on_create_task_type_screen() {
        waitFor(5);
        scrollToElement(createGeneralTask.btnCancel,false);
        createGeneralTask.btnCancel.click();
    }

    @Then("I verify warning message is displayed for task")
    public void i_verify_warning_message_is_displayed_for_task() {
        waitForClickablility(createGeneralTask.lblWarningMessage, 10);
        assertTrue(isElementDisplayed(createGeneralTask.lblWarningMessage));
    }

    @Then("I click on continue button on create task warning message")
    public void i_click_on_continue_button_on_create_task_warning_message() {
        waitForVisibility(createGeneralTask.btnContinueWarningPopUp, 10);
        click(createGeneralTask.btnContinueWarningPopUp);
    }

    @Then("I will be take to the same screen where I initiated create task")
    public void i_will_be_take_to_the_same_screen_where_I_initiated_create_task() {
        waitForVisibility(activeContactPage.unlinkContactRecord, 10);
        assertTrue(activeContactPage.unlinkContactRecord.isDisplayed());
        waitFor(3);
        assertTrue(dashBoard.activeContactLink.getAttribute("class").contains("active"),
                "It is not navigate back to active contact screen");
    }

    @When("I Navigate to create task link")
    public void navigateToCreateTaskLink() {
        waitForVisibility(dashBoard.btnMenuList, 30);
        click(dashBoard.btnMenuList);
        waitForVisibility(dashBoard.createTaskMenu, 10);
        click(dashBoard.createTaskMenu);
    }

    @Then("I should not able to created {string} task")
    public void verifyGeneralTaskIsDisplayedOrNot(String taskName) {
        try {
            assertFalse(Driver.getDriver().findElement(By.xpath("//ul/li//span[text()='" + taskName + "']")).isDisplayed());
        } catch (NoSuchElementException ele) {
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(false, "Element is identified by selenium");
        }
    }

    @Then("I click on cancel button on create task warning message")
    public void clickOnCancelBtnOnWarningMessage() {
        waitFor(5);
        actions.moveToElement(createGeneralTask.btnCancelWarningPopUp).click().perform();
        waitFor(3);
    }

    @Then("I verify user remains on same create task page")
    public void verifyUserRemainsOnSameScreen() {
        waitForVisibility(createGeneralTask.createTaskPageHeader, 10);
        assertTrue(createGeneralTask.createTaskPageHeader.isDisplayed(), "User is not in same page");
        assertFalse(createGeneralTask.txtTaskInfo.getText().isEmpty(), "Entered Information is cleared");
    }

    @Then("I should return back to Create Task screen")
    public void verifyReturnBackToCreateTaskScreen() {
        waitForVisibility(createGeneralTask.createTaskPageHeader, 10);
        assertTrue(createGeneralTask.createTaskPageHeader.isDisplayed(), "It is not navigate back to Create Task Page");
        assertTrue(createGeneralTask.lstAssignee.getAttribute("value").isEmpty(), "Assignee dropdown not showing empty");
        assertTrue(createGeneralTask.txtTaskInfo.getText().isEmpty(), "Task Info not showing empty");

    }

    @Then("I verify fields order in PlanProvider create task page")
    public void verifyAdditionalFieldsInCreateTaskPage() {
        waitFor(2);
        if (isElementDisplayed(dashboard.createTaskSign)) {
            verifyFieldsInCreateTaskPage();
        }
        for (int i = 0; i < APITaskManagementController.fieldOrder.get().size(); i++) {
            if (createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase().equals("provider first name") &&
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("provider last name")) {
                assertEquals(createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase(),
                        APITaskManagementController.fieldOrder.get().get(i + 2).toLowerCase(), "Field order mismatch");
            } else if (createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase().equals("provider last name") &&
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("provider first name")) {
                assertEquals(createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase(),
                        APITaskManagementController.fieldOrder.get().get(i).toLowerCase(), "Field order mismatch");
            } else {
                assertEquals(APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase(),
                        createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase(),
                        "Field order mismatch");
            }
        }
    }

    @Then("I verify all check box are able to select")
    public void verifycheckboxAreAbleToSelect() {
        waitFor(1);
        scrollToElement(createGeneralTask.requestedNewPlanChkBx);
        waitFor(1);
        assertEquals(createGeneralTask.requestedNewPlanChkBx.getAttribute("type").toLowerCase(), "checkbox", "Request new Plan is not a check box");
        assertEquals(createGeneralTask.requestedNewProviderChkBx.getAttribute("type").toLowerCase(), "checkbox", "Request new Provider is not a check box");
        assertEquals(createGeneralTask.urgentAccessToCareChkBx.getAttribute("type").toLowerCase(), "checkbox", "Urgent Access To Care is not a check box");
        createGeneralTask.requestedNewPlanChkBx.click();
        createGeneralTask.requestedNewProviderChkBx.click();
        createGeneralTask.urgentAccessToCareChkBx.click();
        assertTrue(createGeneralTask.requestedNewPlanChkBx.isSelected(), "Request new Plan check box is not able to select");
        assertTrue(createGeneralTask.requestedNewProviderChkBx.isSelected(), "Request new Provider check box is not able to select");
        assertTrue(createGeneralTask.urgentAccessToCareChkBx.isSelected(), "Urgent Access To Care check box is not able to select");
    }

    @Then("I verify all text box fields values")
    public void verifyAllTextBoxFieldsValue() {
        scrollToElement(createGeneralTask.providerFirstNameTxtBox);
        createGeneralTask.providerNpiTxtBox.sendKeys("ABCD 1234 XYZ");
        assertTrue(createGeneralTask.providerNpiTxtBox.getAttribute("value").equals("ABCD 1234 XYZ"), "Provider Npi field value is wrong");

        createGeneralTask.providerStateIdTxtBox.sendKeys("ABCD 1234 XYZ");
        assertTrue(createGeneralTask.providerStateIdTxtBox.getAttribute("value").equals("ABCD 1234 XYZ"), "Provider State Id field value is wrong");

        createGeneralTask.providerPhoneTxtBox.sendKeys("1234567898abcd");
        assertTrue(createGeneralTask.providerPhoneTxtBox.getAttribute("value").equals("123-456-7898"), "Provider Phone field value is wrong");
    }

    @Then("I verify Plan Id drop down value")
    public void verifyPlanIdDropDownValues(List expectedValues) {
        waitForVisibility(createGeneralTask.planId, 5);
        createGeneralTask.planId.click();
        List<String> actualValues = new LinkedList<String>();
        waitForVisibility(createGeneralTask.planIdDrDw.get(0), 5);
        for (int i = 1; i < createGeneralTask.planIdDrDw.size(); i++) {
            actualValues.add(createGeneralTask.planIdDrDw.get(i).getText());
        }
        Assert.assertEquals(actualValues, expectedValues, "Plan Id drop down values are incorrect");
        click(createGeneralTask.planIdDrDw.get(1));
    }

    @And("I verify Plan Name drop down value")
    public void verifyPlanNameDropDownValues(List expectedValues) {
        waitFor(1);
        createGeneralTask.planName.click();
        List<String> actualValues = new LinkedList<String>();
        waitForVisibility(createGeneralTask.planNameDrDw.get(0), 5);
        for (int i = 1; i < createGeneralTask.planNameDrDw.size(); i++) {
            actualValues.add(createGeneralTask.planNameDrDw.get(i).getText());
        }
        Assert.assertEquals(actualValues, expectedValues, "Plan Name drop down values are incorrect");
        click(createGeneralTask.planNameDrDw.get(1));
    }

    @And("I verify Plan change reason drop down value")
    public void verifyPlanChangeReasonDropDownValues(List expectedValues) {
        waitFor(1);
        createGeneralTask.planChangeReason.click();
        List<String> actualValues = new LinkedList<>();
        waitForVisibility(createGeneralTask.planChangeReasonDrDw.get(0), 5);
        for (int i = 1; i < createGeneralTask.planChangeReasonDrDw.size(); i++) {
            actualValues.add(createGeneralTask.planChangeReasonDrDw.get(i).getText());
        }
        Assert.assertEquals(actualValues, expectedValues, "Plan Change Reason drop down values are incorrect");
        click(createGeneralTask.planChangeReasonDrDw.get(1));
    }

    @And("I verify Provider record issue drop down value")
    public void verifyProviderRecordIssueDropDownValues(List expectedValues) {
        waitFor(1);
        createGeneralTask.providerRecordIssue.click();
        List<String> actualValues = new LinkedList<String>();
        waitForVisibility(createGeneralTask.providerRecordIssueDrDw.get(0), 5);
        for (int i = 1; i < createGeneralTask.providerRecordIssueDrDw.size(); i++) {
            actualValues.add(createGeneralTask.providerRecordIssueDrDw.get(i).getText());
        }
        Assert.assertEquals(actualValues, expectedValues, "Provider Record issue drop down values are incorrect");
        click(createGeneralTask.providerRecordIssueDrDw.get(1));
    }

    @Then("I verify create task fields displayed and all additional fields of Address part and they are in group")
    public void verifyAdditionalFieldsOfAddressInCreateTaskPage() {
        verifyFieldsInCreateTaskPage();
        for (int i = 0; i < createGeneralTask.providerAddressLineFields.size(); i++) {
            assertTrue(isElementDisplayed(createGeneralTask.providerAddressLineFields.get(i)), "Provider Address fields are not displayed");
        }

        for (int i = 0; i < createGeneralTask.newAddressLineFields.size(); i++) {
            assertTrue(isElementDisplayed(createGeneralTask.newAddressLineFields.get(i)), "New Address fields are not displayed");
        }

        for (int i = 0; i < createGeneralTask.oldAddressLineFields.size(); i++) {
            assertTrue(isElementDisplayed(createGeneralTask.oldAddressLineFields.get(i)), "Old Address fields are not displayed");
        }
    }

    @Then("I verify fields length for new old and provider address line 1 and 2")
    public void verifyAllTextBoxFieldsValueOnCreateAddressTaskPage() {
        scrollToElement(createGeneralTask.providerAddressLine1TxtBox);

        createGeneralTask.providerAddressLine1TxtBox.sendKeys("Verify ProviderAddressLine1 Test 50Character Allow");
        assertEquals(createGeneralTask.providerAddressLine1TxtBox.getAttribute("value").length(),
                50, "Provider Address Line 1 field length is not 50 characters");
        createGeneralTask.providerAddressLine1TxtBox.sendKeys("more then 50");
        assertEquals(createGeneralTask.providerAddressLine1TxtBox.getAttribute("value").length(),
                50, "Provider Address Line 1 field length is not 50 characters");

        createGeneralTask.providerAddressLine2TxtBox.sendKeys("Verify Provider Address Line 2");
        assertEquals(createGeneralTask.providerAddressLine2TxtBox.getAttribute("value").length(),
                30, "Provider Address Line 2 field length is not 30 characters");
        createGeneralTask.providerAddressLine2TxtBox.sendKeys("more then 30");
        assertEquals(createGeneralTask.providerAddressLine2TxtBox.getAttribute("value").length(),
                30, "Provider Address Line 2 field length is not 30 characters");

        createGeneralTask.newAddressLine1TxtBox.sendKeys("Verify New Address Line1 Test 50 Character Allowed");
        assertEquals(createGeneralTask.newAddressLine1TxtBox.getAttribute("value").length(),
                50, "New Address Line 1 field length is not 50 characters");
        createGeneralTask.newAddressLine1TxtBox.sendKeys("more then 50");
        assertEquals(createGeneralTask.newAddressLine1TxtBox.getAttribute("value").length(),
                50, "New Address Line 1 field length is not 50 characters");

        createGeneralTask.newAddressLine2TxtBox.sendKeys("Verify New Address Line 2 Test");
        assertEquals(createGeneralTask.newAddressLine2TxtBox.getAttribute("value").length(),
                30, "New Address Line 2 field length is not 30 characters");
        createGeneralTask.newAddressLine2TxtBox.sendKeys("more then 30");
        assertEquals(createGeneralTask.newAddressLine2TxtBox.getAttribute("value").length(),
                30, "New Address Line 2 field length is not 30 characters");

        createGeneralTask.oldAddressLine1TxtBox.sendKeys("Verify Old Address Line1 Test 50 Character Allowed");
        assertEquals(createGeneralTask.oldAddressLine1TxtBox.getAttribute("value").length(),
                50, "Old Address Line 1 field length is not 50 characters");
        createGeneralTask.oldAddressLine1TxtBox.sendKeys("more then 50");
        assertEquals(createGeneralTask.oldAddressLine1TxtBox.getAttribute("value").length(),
                50, "Old Address Line 1 field length is not 50 characters");

        createGeneralTask.oldAddressLine2TxtBox.sendKeys("Verify Old Address Line 2 Test");
        assertEquals(createGeneralTask.oldAddressLine2TxtBox.getAttribute("value").length(),
                30, "Old Address Line 2 field length is not 30 characters");
        createGeneralTask.oldAddressLine2TxtBox.sendKeys("more then 30");
        assertEquals(createGeneralTask.oldAddressLine2TxtBox.getAttribute("value").length(),
                30, "Old Address Line 2 field length is not 30 characters");
    }

    @And("I verify all Drop Down fields values on create Address task page")
    public void verifyAlleDropDownValuesOfAddressPage() {
        scrollToElement(createGeneralTask.providerAddressCity);
        createGeneralTask.providerAddressCity.sendKeys("Agawam");
        assertEquals(createGeneralTask.providerAddressCity.getAttribute("value"), "Agawam", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.providerAddressCity.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");

        createGeneralTask.providerAddressState.sendKeys("California");
        assertEquals(createGeneralTask.providerAddressState.getAttribute("value"), "California", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.providerAddressState.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");

        selectDropDown(createGeneralTask.providerAddressCounty, "Clinton");

        createGeneralTask.providerAddressSZipCode.sendKeys("544");
        assertEquals(createGeneralTask.providerAddressSZipCode.getAttribute("value"), "544", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.providerAddressSZipCode.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");

        createGeneralTask.newAddressCity.sendKeys("Agawam");
        assertEquals(createGeneralTask.newAddressCity.getAttribute("value"), "Agawam", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.newAddressCity.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");

        createGeneralTask.newAddressState.sendKeys("California");
        assertEquals(createGeneralTask.newAddressState.getAttribute("value"), "California", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.newAddressState.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");

        createGeneralTask.newAddressZipCode.sendKeys("544");
        assertEquals(createGeneralTask.newAddressZipCode.getAttribute("value"), "544", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.newAddressZipCode.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");

        createGeneralTask.oldAddressCity.sendKeys("Agawam");
        assertEquals(createGeneralTask.oldAddressCity.getAttribute("value"), "Agawam", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.oldAddressCity.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");

        createGeneralTask.oldAddressState.sendKeys("California");
        assertEquals(createGeneralTask.oldAddressState.getAttribute("value"), "California", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.oldAddressState.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");

        createGeneralTask.oldAddressZipCode.sendKeys("544");
        assertEquals(createGeneralTask.oldAddressZipCode.getAttribute("value"), "544", "Autocomplete drop down does not take value");
        assertFalse(createGeneralTask.oldAddressZipCode.getAttribute("aria-controls").isEmpty(), "Autocomplete drop down List is not displayed");
    }

    @And("I select below list values in contact Reason multi select drop down")
    public void iSelectValuesInCantctReasonDropDown(List expectedValues) {
        synchronized (selectValue){selectValue.set(new ArrayList<>());}
        createGeneralTask.cantactReason.click();
        waitForVisibility(createGeneralTask.contactReasonDrDw.get(0), 5);
        for (int i = 0; i < expectedValues.size(); i++) {
            if (i != 0) {
                selectValue.get().add(expectedValues.get(i).toString());
            } else {
                synchronized (actionTaken1stVlu){
                    actionTaken1stVlu.set(expectedValues.get(i).toString());
                }
            }
            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-contactReason']//ul/li[text()='" + expectedValues.get(i) + "']")));
        }
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(createGeneralTask.preferredCallBackTime).click().perform();
    }

    @And("I verify +# link is displayed on screen")
    public void iVerifyMultiSelectLinkIsAvailble() {
        waitForVisibility(createGeneralTask.multiselectValueLink, 5);
        sa.get().assertThat(isElementDisplayed(createGeneralTask.multiselectValueLink)).as("+# Link is not displayed").isTrue();
        assertEquals(createGeneralTask.multiselectValueLink.getText(), "+" + (selectValue.get().size()),
                "+# Number which is displayed is not correct");
        waitForVisibility(Driver.getDriver().findElement(By.xpath("//*[text()='" + actionTaken1stVlu.get() + "']")), 5);
        sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//*[text()='" + actionTaken1stVlu.get() + "']")))).
                as("1st selected value in multi select drop down is not displayed").isTrue();
    }

    @And("I mouse over to +# to verify all selected values are displayed")
    public void verifyToolTipValue() {
        hover(createGeneralTask.multiselectValueLink);
        List<String> actualValues = new LinkedList<>();
        waitForVisibility(createGeneralTask.selectedToolTipValue.get(0), 5);
        for (int i = 0; i < createGeneralTask.selectedToolTipValue.size(); i++) {
            actualValues.add(createGeneralTask.selectedToolTipValue.get(i).getText());
        }
        Collections.sort(selectValue.get());
        Collections.sort(actualValues);
        sa.get().assertThat(actualValues).hasSize(selectValue.get().size()).as("Mouse Over values are incorrect").isEqualTo(selectValue.get());
    }

    @Then("I verify fields order in General CRM EB create task page")
    public void verifyAdditionalFieldsInGeneralCRMEBCreateTaskPage() {
        verifyFieldsInCreateTaskPage();

        for (int i = 0; i < APITaskManagementController.fieldOrder.get().size(); i++) {
            if (createGeneralTask.generalCRMEBAdditionalFlds.get(i).getText().toLowerCase().equals("case worker first name") &&
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("case worker last name")) {
                assertEquals(createGeneralTask.generalCRMEBAdditionalFlds.get(i).getText().toLowerCase(),
                        APITaskManagementController.fieldOrder.get().get(i + 2).toLowerCase(), "Field order mismatch");
            } else if (createGeneralTask.generalCRMEBAdditionalFlds.get(i).getText().toLowerCase().equals("case worker last name") &&
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("case worker first name")) {
                assertEquals(createGeneralTask.generalCRMEBAdditionalFlds.get(i).getText().toLowerCase(),
                        APITaskManagementController.fieldOrder.get().get(i).toLowerCase(), "Field order mismatch");
            } else {
                assertTrue(createGeneralTask.generalCRMEBAdditionalFlds.get(i).getText().toLowerCase().
                                contains(APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase()),
                        "Field order mismatch");
            }
        }
    }

    @Then("I verify channel drop down value")
    public void verifyChannelDropDownValues(List expectedValues) {
        waitForVisibility(createGeneralTask.channel, 5);
        createGeneralTask.channel.click();
        List<String> actualValues = new LinkedList<String>();
        waitForVisibility(createGeneralTask.channelDrDw.get(0), 5);
        for (int i = 1; i < createGeneralTask.channelDrDw.size(); i++) {
            actualValues.add(createGeneralTask.channelDrDw.get(i).getText());
        }
        assertEquals(actualValues, expectedValues, "Channel drop down values are incorrect");
        click(createGeneralTask.channelDrDw.get(1));
        taskValues.get().put("channel", createGeneralTask.channel.getText());
    }

    @Then("I verify Contact Reason drop down value")
    public void verifyContactReasonDropDownValues(List expectedValues) {
        waitFor(1);
        waitForVisibility(createGeneralTask.contactReason, 5);
        createGeneralTask.contactReason.click();
        List actualValues = new LinkedList();
        waitForVisibility(createGeneralTask.contactReasonDrDw.get(0), 5);
        for (int i = 0; i < createGeneralTask.contactReasonDrDw.size(); i++) {
            if (!createGeneralTask.contactReasonDrDw.get(i).getText().isEmpty())
                actualValues.add(createGeneralTask.contactReasonDrDw.get(i).getText());
        }
        List expectedValuess = new LinkedList(expectedValues);
        Collections.sort(actualValues);
        Collections.sort(expectedValuess);
        assertEquals(actualValues, expectedValuess, "Contact Reason down values are incorrect");
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
    }

    @Then("I verify Outreach Location drop down value")
    public void verifyOutReachLocationDropDownValues(List expectedValues) {
        waitFor(1);
        waitForVisibility(createGeneralTask.outreachLocation, 5);
        createGeneralTask.outreachLocation.click();
        List<String> actualValues = new LinkedList<String>();
        waitForVisibility(createGeneralTask.outreachLocationDrDw.get(0), 5);
        for (int i = 0; i < createGeneralTask.outreachLocationDrDw.size(); i++) {
            actualValues.add(createGeneralTask.outreachLocationDrDw.get(i).getText());
        }
        assertEquals(actualValues, expectedValues, "Outreach Location drop down values are incorrect");
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
    }

    @Then("I verify Program drop down value")
    public void verifyProgramDropDownValues(List expectedValues) {
        waitFor(1);
        waitForVisibility(createGeneralTask.program, 5);
        createGeneralTask.program.click();
        List<String> actualValues = new LinkedList<String>();
        waitForVisibility(createGeneralTask.programDrDw.get(0), 5);
        for (int i = 1; i < createGeneralTask.programDrDw.size(); i++) {
            actualValues.add(createGeneralTask.programDrDw.get(i).getText());
        }
        assertEquals(actualValues, expectedValues, "Program drop down values are incorrect");
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
    }

    @And("I click on Link Case or Consumer button under LINK section")
    public void i_click_on_link_caseconsumer_button_under_link_section() {
        waitFor(3);
        i_click_on_link_contactlink_button_under_link_section();
        createGeneralTask.caseConsumerBtn.click();
    }

    @Then("I see Case& Consumer Search section display")
    public void i_see_case_consumer_search_section_display() {
        assertTrue(createGeneralTask.caseConsumerSection.isDisplayed(), "Link text not showing");
    }

    @When("I search for consumer have First Name as {string} and Last Name as {string}")
    public void i_search_for_consumer_have_first_name_as_something_and_last_name_as_something(String fname, String lname) {
        clearAndFillText(createGeneralTask.consumerFirstName, fname);
        clearAndFillText(createGeneralTask.consumerLastName, lname);
        CRM_WorkQueueStepDef.consumerName.set(fname + " " + lname);
        taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
    }

    @When("I search for consumer by consumerId")
    public void i_search_for_consumer_by_consumerId() {
        clearAndFillText(createGeneralTask.internalconsumerId, apiCreateConsumerContactController.get().getExternalConsumerId());
    }

    @When("I search for newly created consumer by firstname and lastname")
    public void i_search_for_consumer_by_fname_lname() {
        clearAndFillText(createGeneralTask.consumerFirstName, apiCreateConsumerContactController.get().getConsumerfirstName());
        clearAndFillText(createGeneralTask.consumerLastName, apiCreateConsumerContactController.get().getConsumerlastName());
        //CRM_WorkQueueStepDef.consumerName.set(fname + " " + lname;
        //taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName);
    }

    @And("I click on Search option")
    public void i_click_on_search_option() {
        waitForVisibility(createGeneralTask.consumerSearchBtn, 10);
        click(createGeneralTask.consumerSearchBtn);
    }

    @Then("I should able to Manually Link the Task to a Case or Consumer by search operation")
    public void i_should_able_to_manually_link_the_task_to_a_case_or_consumer_by_search_operation() {
        waitFor(2);
        assertTrue(createGeneralTask.search_recordCount.size() > 0, "Search  result is not displaying");
    }

    @When("I expand the first record of the search result")
    public void i_expand_the_first_record_of_the_search_result() {
        for (int i = 0; i < createGeneralTask.search_recordCount.size(); i++) {
            if (!createGeneralTask.caseIDValue.get(i).getText().isEmpty()) {
                createGeneralTask.click_searchRecord.get(i).click();
                synchronized (externalCaseID){externalCaseID.set(createGeneralTask.caseIDValue.get(i).getText());}
                System.out.println("External case id from Manual Case/Consumer View: " + externalCaseID.get());
                synchronized (externalConsumerID){externalConsumerID.set(createGeneralTask.consumerIDValue.get(i).getText());}
                System.out.println("External consumer id from Manual Case/Consumer View: " + externalConsumerID.get());
                taskValues.get().put("consumerID", createGeneralTask.consumerIDValue.get(i).getText());
                taskValues.get().put("caseID", createGeneralTask.caseIDValue.get(i).getText());
                synchronized (ConsumerID){ConsumerID.set(createGeneralTask.consumerIDValue.get(i).getText());}
                System.out.println("Consumer ID: " + ConsumerID.get());
                synchronized (CaseID){CaseID.set(createGeneralTask.caseIDValue.get(i).getText());}
                System.out.println("Case ID: " + CaseID.get());
                break;
            }
        }
    }

    @Then("I see radio button is associated with a Case or Consumer")
    public void i_see_radio_button_associated_with_a_case_or_consumer() {
        scrollDown();
        scrollDown();
        for (WebElement enabled_radiobutton : createGeneralTask.consumer_radioItems) {
            waitForVisibility(enabled_radiobutton, 10);
            assertEquals(enabled_radiobutton.getAttribute("aria-disabled"), "false", "radio button not showing enabled");
            assertTrue(isElementDisplayed(enabled_radiobutton), "Radio items are not displaying");
        }

    }

    @And("I see 'Link to Case Only' checkbox and not selected")
    public void i_see_link_to_case_only_checkbox() {
        assertTrue(isElementDisplayed(createGeneralTask.linkCaseOnly_Chkbox), "Link to Case Checkbox is not displayed");
        assertFalse(createGeneralTask.linkCaseOnly_Chkbox.isSelected(), "Link to Case Checkbox showing checked");
        assertFalse(isElementDisplayed(createGeneralTask.linkRecord_Consumerbutton), "Link Record button is displayed");
        assertFalse(isElementDisplayed(createGeneralTask.linkRecord_Casebutton), "Link Record button is displayed");

    }

    @When("I select Consumer radio button")
    public void i_select_consumer_radio_button() {
        waitFor(1);
        createGeneralTask.consumer_radioItems.get(0).click();

    }

    @When("I select 'Link to Case Only' checkbox")
    public void i_select_link_to_case_only_checkbox() {
        if (!createGeneralTask.linkCaseOnly_Chkbox.isSelected()) {
            createGeneralTask.linkCaseOnly_Chkbox.click();
        }
    }

    @Then("I see Link Record Case button get displayed")
    public void link_record_case_button_get_displayed() {
        waitForVisibility(createGeneralTask.linkRecord_Casebutton, 10);
        assertTrue(isElementDisplayed(createGeneralTask.linkRecord_Casebutton), "Link Record button not displayed");
    }

    @Then("I see Link Record Consumer button get displayed")
    public void link_record_consumer_button_get_displayed() {
        waitForVisibility(createGeneralTask.linkRecord_Consumerbutton, 10);
        assertTrue(isElementDisplayed(createGeneralTask.linkRecord_Consumerbutton), "Link Record button not displayed");
    }

    @Then("I see all Consumer Radio Buttons become disabled")
    public void consumer_radio_buttons_become_disabled() {
        for (WebElement radiobutton : createGeneralTask.consumer_radioItems) {
            assertEquals(radiobutton.getAttribute("aria-disabled"), "true", "radio button not showing disabled");
        }
    }

    @When("I select 'Link to Case Only' checkbox once again")
    public void select_link_to_case_only_checkbox() {
        waitFor(2);
        if (createGeneralTask.linkCaseOnly_Chkbox1.isSelected()) {
            createGeneralTask.linkCaseOnly_Chkbox1.click();
        }
    }

    @Then("I see all Consumer Radio Buttons become enabled")
    public void consumer_radio_buttons_become_enabled() {
        for (WebElement enabled_radiobutton : createGeneralTask.consumer_radioItems) {
            assertEquals(enabled_radiobutton.getAttribute("aria-disabled"), "false", "radio button not showing enabled");
        }
    }

    @And("I see Link Record button will not get display")
    public void link_Record_button_not_display() {
        assertFalse(isElementDisplayed(createGeneralTask.linkRecord_Casebutton), "Link Record Case button displayed");
        assertFalse(isElementDisplayed(createGeneralTask.linkRecord_Consumerbutton), "Link Record Consumer button displayed");
    }

    @Then("I verify 'Link to Case Only' checkbox disabled for the record whose CASE ID is blank")
    public void expand_the_record_with_No_caseID() {
        assertEquals(createGeneralTask.linkCaseOnly_Chkbox.getAttribute("aria-disabled"), "true", "radio button not showing disabled");
    }

    @Then("I verify 'Link to Case Only' checkbox disabled")
    public void verify_link_to_case_only_disabled_and_selected() {
        assertEquals(createGeneralTask.linkCaseOnly_Chkbox.getAttribute("aria-disabled"), "true", "checkbox is disabled");
        assertFalse(isElementDisplayed(createGeneralTask.linkRecord_Consumerbutton), "Link Record Consumer button displayed");
    }

    @When("I click on Link Record Consumer button")
    public void click_link_consumer_button() {
        if (isElementDisplayed(createGeneralTask.linkRecord_Consumerbutton)) {
            click(createGeneralTask.linkRecord_Consumerbutton);
        } else {
            click(createGeneralTask.linkConsumerbutton);
        }
    }

    @When("I click on Link Case button")
    public void click_link_case_button() {
        click(createGeneralTask.linkRecord_Casebutton);
    }

    @Then("I verify task is linked with Consumer ID")
    public void verify_linked_consumer_value() {
        scrollToElement(createGeneralTask.consumerInfo.get(0),false);
        System.out.println(createGeneralTask.consumerInfo.get(0).getText());
        if (createGeneralTask.taskLinkInfo.size() != 0 && projectName1.get().equalsIgnoreCase("IN-EB"))
            sa.get().assertThat(createGeneralTask.consumerInfo.get(0).getText()).as("Consumer id is not correct").isEqualTo(externalConsumerID.get());
        else if (taskValues.get().containsKey("srType") && (taskValues.get().get("srType").equalsIgnoreCase("Appeal") || taskValues.get().get("srType").equalsIgnoreCase("Review Appeals Form") || taskValues.get().get("srType").equalsIgnoreCase("GCNJ Appeals Acknowledgement Letter") || taskValues.get().get("srType").equalsIgnoreCase("Follow-Up on Appeal") || taskValues.get().get("srType").equalsIgnoreCase("GCNJ Resolve Appeal") || taskValues.get().get("srType").equalsIgnoreCase("Generate IDR Successful Letter") || taskValues.get().get("srType").equalsIgnoreCase("Generate IDR Unsuccessful Letter") || (taskValues.get().get("srType").equalsIgnoreCase("Auto Assignment SR") || (taskValues.get().get("srType").equalsIgnoreCase("Complaint SR")) || (taskValues.get().get("srType").equalsIgnoreCase("AA Outbound Call")))))
            sa.get().assertThat(createGeneralTask.consumerInfo.get(0).getText()).as("Consumer Id is empty").isNotBlank();
        else if (taskValues.get().containsKey("consumerID"))
            sa.get().assertThat(createGeneralTask.consumerInfo.get(0).getText().equals(taskValues.get().get("consumerID")) || createGeneralTask.consumerInfo.get(0).getText().equals("Incomplete App")|| createGeneralTask.linkConsumerId.getText().equals("Consumer")).as("ConsumerID Value is missing").isTrue() ;
        else
            sa.get().assertThat(createGeneralTask.consumerInfo.get(0).getText()).as("Consumer Id is empty").isNotBlank();
        if (taskValues.get().containsKey("consumerName")) {
            String str[] = createGeneralTask.consumerInfo.get(1).getText().split(" ");
            String consumerName = str[0] + " " + str[str.length - 1];
            sa.get().assertThat(consumerName).as("Name Value is missing").isEqualTo(taskValues.get().get("consumerName"));
        } else
            sa.get().assertThat(createGeneralTask.consumerInfo.get(1).getText()).as("Consumer Name is empty").isNotBlank();
        sa.get().assertThat(createGeneralTask.consumerInfo.get(2).getText()).as("Type Value is missing").isEqualTo("Consumer");
        sa.get().assertThat(verifyDateFormat(createGeneralTask.consumerInfo.get(3))).as("Consumer Status date is wrong").isTrue();
        sa.get().assertThat(createGeneralTask.consumerInfo.get(4).getText()).as("Status Value is missing").isNotBlank();

    }

    @Then("I verify task is linked with Case ID")
    public void verify_linked_case_value() {
        scrollDown();
        waitFor(2);
        if (taskValues.get().containsKey("srType") && (taskValues.get().get("srType").equalsIgnoreCase("Appeal") || taskValues.get().get("srType").equalsIgnoreCase("Review Appeals Form") || taskValues.get().get("srType").equalsIgnoreCase("GCNJ Appeals Acknowledgement Letter") || taskValues.get().get("srType").equalsIgnoreCase("Follow-Up on Appeal") || taskValues.get().get("srType").equalsIgnoreCase("GCNJ Resolve Appeal") || taskValues.get().get("srType").equalsIgnoreCase("Generate IDR Successful Letter") || taskValues.get().get("srType").equalsIgnoreCase("Generate IDR Unsuccessful Letter")))
            sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("CaseId is empty").isNotBlank();
        else if (projectName1.get().equalsIgnoreCase("IN-EB")) {
            sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("External case id value is missing").isEqualTo(externalCaseID.get());
        } else if (taskValues.get().containsKey("srType") && taskValues.get().get("srType").equalsIgnoreCase("General Three")) {
            sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("CaseId is mismatch").isEqualTo("NaN");
        } else if (projectName1.get().equalsIgnoreCase("BLCRM") || projectName1.get().equalsIgnoreCase("DC-EB"))
            sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("CaseId is empty").isNotBlank();
        else
            sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("CaseID Value is missing").isEqualTo(taskValues.get().get("caseID"));
        sa.get().assertThat(createGeneralTask.caseInfo.get(3).getText()).as("Name Value is missing").isEqualTo("Case");
        assertTrue(createGeneralTask.caseInfo.get(4).getText().equals("-- --") || createGeneralTask.caseInfo.get(4).getText().equalsIgnoreCase("Null"),
                "Type Value is not showing null");
        sa.get().assertThat(createGeneralTask.caseInfo.get(5).getText()).as("Status Date Value is not showing blank").isEqualTo("--/--/----");
        sa.get().assertThat(createGeneralTask.caseInfo.get(6).getText()).as("Status Value is not showing null").isEqualTo("-- --");

    }

    public void verify_case_on_cr_and_active_cr_page() {
        scrollDown();
        waitFor(2);
        if (taskValues.get().containsKey("srType") && (taskValues.get().get("srType").equalsIgnoreCase("Appeal") || taskValues.get().get("srType").equalsIgnoreCase("Review Appeals Form") || taskValues.get().get("srType").equalsIgnoreCase("GCNJ Appeals Acknowledgement Letter") || taskValues.get().get("srType").equalsIgnoreCase("Follow-Up on Appeal")))
            sa.get().assertThat(createGeneralTask.caseInfo.get(1).getText()).as("CaseId is empty").isNotBlank();
        else if (projectName1.get().equalsIgnoreCase("IN-EB")) {
            sa.get().assertThat(createGeneralTask.caseInfo.get(1).getText()).as("External case id value is missing").isEqualTo(externalCaseID.get());
        } else
            sa.get().assertThat(createGeneralTask.caseInfo.get(1).getText()).as("CaseID Value is missing").isEqualTo(taskValues.get().get("caseID"));
        sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("Name Value is missing").isEqualTo("Case");
        assertTrue(createGeneralTask.caseInfo.get(3).getText().equals("-- --") || createGeneralTask.caseInfo.get(4).getText().equalsIgnoreCase("Null"),
                "Type Value is not showing null");
        sa.get().assertThat(createGeneralTask.caseInfo.get(4).getText()).as("Status Date Value is not showing blank").isEqualTo("--/--/----");
        sa.get().assertThat(createGeneralTask.caseInfo.get(5).getText()).as("Status Value is not showing null").isEqualTo("-- --");
    }

    @Then("I verify inbound correspondence linked to sr")
    public void verify_inbound_corespondence(String srType) {
        sa.get().assertThat(String.valueOf(World.generalSave.get().get("InboundDocumentId"))).as("Inbound document id doesnt match").isEqualTo(createGeneralTask.linkedInboundInfo.get(0).getText());
        sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(1).getText()).as("Inbound correspondence Name Value is missing").isEqualTo("Inbound Correspondence");
        switch (srType) {
            case "Application SR":
                sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(2).getText()).as("Type Value is not correct for Application SR").isEqualTo("VACV Application");
                break;
            case "Application Renewal SR":
            case "Renewal SR":
                sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(2).getText()).as("Type Value is not correct for Renewal SR").isEqualTo("VACV Application Renewal");
                break;
            case "Appeals SR":
            case "Fair Hearing":
                sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(2).getText()).as("Type Value is not correct for Fair Hearing or Appeal SR").isEqualTo("VACV Appeal");
                break;
            case "Complaint SR":
            case "Review Complaint":
            case "Complaint Escalation":
                sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(2).getText()).as("Type Value is not correct for Complaint SR").isEqualTo("VACV Complaint");
                break;
            case "Verification Document":
                sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(2).getText()).as("Type Value is not correct for Verification Document task").isEqualTo("VACV Verification Document");
                break;
            case "Appeal":
            case "GCNJ Appeals Acknowledgement Letter":
            case "Review Appeals Form":
            case "Follow-Up on Appeal":
            case "GCNJ Resolve Appeal":
            case "Generate IDR Successful Letter":
            case "Generate IDR Unsuccessful Letter":
                sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(2).getText()).as("Type Value is not correct for Appeal SR").isEqualTo("NJ SBE Appeal Form");
                break;
            case "Customer Service Complaint":
                sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(2).getText()).as("Type Value is not correct for Complaint SR").isEqualTo("INEB Unknown");
                break;
            case "General Service Request":
                sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(2).getText()).as("Type Value is not correct for Complaint SR").isEqualTo("maersk Unknown");
                break;
            default:
                sa.get().fail("Switch case is not match");
                break;
        }

        sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(3).getText()).as("Status Date Value is not correct for Inbound Correspondence").isEqualTo("-- --");
        sa.get().assertThat(createGeneralTask.linkedInboundInfo.get(4).getText()).as("Status Value is not Received for Inbound Correspondence").isEqualTo("RECEIVED");
    }

    @Then("I verify outbound correspondence linked to sr")
    public void verify_outbound_correspondence(String srType) {
        sa.get().assertThat(createGeneralTask.outboundCorrName.getText()).as("Outbound correspondence Name Value is missing").isEqualTo("Outbound Correspondence");
        switch (srType) {
            case "Auto Assignment SR":
                if(projectName1.get().equalsIgnoreCase("BLCRM")) {
                    sa.get().assertThat(createGeneralTask.outboundCorrType.getText()).as("Type Value is not correct for BLCRM Auto Assignment SR").isEqualTo("WL1 Welcome Letter 1 Baseline");
                }else if(projectName1.get().equalsIgnoreCase("DC-EB")){
                    sa.get().assertThat(createGeneralTask.outboundCorrType.getText()).as("Type Value is not correct for DC-EB Auto Assignment SR").isEqualTo("100 DCHF Enrollment Packet");
                    sa.get().assertThat(createGeneralTask.outboundCorr2Type.getText()).as("Type 2 Value is not correct for DC-EB Auto Assignment SR").isEqualTo("102 DCHF Reminder Notice");
            }
                break;
        }
        sa.get().assertThat(createGeneralTask.outboundCorrStatusDate.getText()).as("Status Date Value is not correct for Outbound Correspondence").isEqualTo(ApiTestDataUtil.getApiTestDataUtilThreadLocal().getCurrentDateAndTime("MM/dd/YYYY"));
        sa.get().assertThat(createGeneralTask.outboundCorrStatusValue.getText()).as("Status Value is not Received for Outbound Correspondence").isEqualTo("Requested");
    }

    @Then("I verify task is linked with sr")
    public void verify_linked_sr_value(String srType) {
        int taskId = 0;
        if (srType.equals("AA Outbound Call")) {
            taskId = (Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()));
        } else {
            taskId = (Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) - 1);
        }
        sa.get().assertThat(Integer.parseInt(createGeneralTask.taskLinkInfo.get(0).getText()) <= taskId).as("SR Id Value is missing").isTrue();
        sa.get().assertThat(createGeneralTask.taskLinkInfo.get(1).getText()).as("Name Value is missing").isEqualTo("Service Request");
        switch (srType) {
            case "Review Complaint":
            case "Complaint Escalation":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct").isEqualTo("Complaint SR");
                break;
            case "Process App V1":
            case "Missing Information":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText().equalsIgnoreCase("App SR V1") ||
                        createGeneralTask.taskLinkInfo.get(2).getText().equalsIgnoreCase("App Renewal SR V1")).as("Type value is mismatch").isTrue();
                break;
            case "Process Application":
            case "Verification Document":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText().equalsIgnoreCase("Application SR") ||
                                createGeneralTask.taskLinkInfo.get(2).getText().equalsIgnoreCase("Application Renewal SR") ||
                        createGeneralTask.taskLinkInfo.get(2).getText().equalsIgnoreCase("Renewal SR")).as("Type value is mismatch").isTrue();
                break;
            case "Just Cause Request":
            case "Just Cause Follow-up Email":
            case "Just Cause Resolution":
            case "Just Cause State Discussion":
            case "Just Cause Decision Letter":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct").isEqualTo("Just Cause");
                break;
            case "Review Appeal":
            case "Fair Hearing":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct").isEqualTo("Appeals SR");
                break;
            case "GCNJ Appeals Acknowledgement Letter":
            case "Follow-Up on Appeal":
            case "Review Appeals Form":
            case "GCNJ Resolve Appeal":
            case "Generate IDR Successful Letter":
            case "Generate IDR Unsuccessful Letter":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct").isEqualTo("Appeal");
                break;
            case "QA Review Complaint":
            case "Supervisor Review Complaint":
            case "Complaint Outbound Call":
            case "Outbound Call":
            case "State Escalated Complaint":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct").isEqualTo("Customer Service Complaint");
                break;
            case "HCC Outbound Call":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct for HCC Outbound Call SR").isEqualTo("HCC Outbound Call SR");
                break;
            case "General Three":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct for HCC Outbound Call SR").isEqualTo("General Service Request");
                break;
            case "AA Outbound Call":
            //case "AA WL1 Letter Task": obsolete task type
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct for Auto Assignment SR").isEqualTo("Auto Assignment SR");
                break;
            case "FC Review":
            case "FC Missing Information":
            case "Invalid FC Member Notification":
            case "FC District Outreach":
            case "FC District 2nd Outreach":
            case "FC Member NotificationTask":
            case "FC Cancellation Request Confirmation":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct").isEqualTo("For Cause SR");
                break;
            case "Survey Outbound Call":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct for HRA/CAHMI Survey SR").isEqualTo("HRA/CAHMI Survey SR");
                break;
            case "Support Level 1":
            case "Support Level 2":
            case "Support Level 3":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct for Tiered Support SR").isEqualTo("Tiered Support SR");
            case "Supervisor Escalation":
            case "Escalation Resolved Member Notification":
            case "Escalation Unresolved Member Notification":
            case "Escalation Member Outreach":
                sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct for Tiered Support SR").isEqualTo("Tiered Support SR");
                break;
            default:
                sa.get().fail("switch case not match " + srType);
                break;

        }
        sa.get().assertThat(createGeneralTask.taskLinkInfo.get(3).getText().equals(taskValues.get().get("startDate"))
                || createGeneralTask.taskLinkInfo.get(3).getText().equals(createGeneralTask.projectDateAtHeader.getText()))
                .as("Status Date Value is not correct").isTrue();
        if ((taskValues.get().containsKey("status") && taskValues.get().get("status").equalsIgnoreCase("Closed")) || (taskValues.get().containsKey("srStatus") && taskValues.get().get("srStatus").equalsIgnoreCase("Closed")))
            sa.get().assertThat(createGeneralTask.taskLinkInfo.get(4).getText()).as("Status Value is mismatch").isEqualTo("Closed");
        else
            sa.get().assertThat(createGeneralTask.taskLinkInfo.get(4).getText()).as("Status Value is not Created").isEqualTo("Open");
    }

    @Then("I verify sr is linked with task")
    public void verify_linked_task_value(String srType, int i) {
        waitFor(3);
        waitForPageToLoad(4);
        String taskTypeList = Driver.getDriver().findElements(By.xpath("//i[text()='assignment']/../following-sibling::td//parent::tr")).get(i).getText();
        System.out.println(taskTypeList);
        sa.get().assertThat(taskTypeList).as("task id value is missing").isNotEmpty()
                .as("task type value is missing").containsIgnoringCase("Task")
                .as("status date is missing").containsIgnoringCase(createGeneralTask.projectDateAtHeader.getText());
        sa.get().assertThat(taskTypeList.contains("Created") || taskTypeList.contains("Complete")).as("status value is not not correct").isTrue();
        switch (srType) {
            case "App SR V1":
            case "App Renewal SR V1":
                sa.get().assertThat(taskTypeList.contains("Process App V1") || taskTypeList.contains("Missing Information")).as("Type Value is not correct").isTrue();
                break;
            case "Appeals SR":
                sa.get().assertThat(taskTypeList).as("Type Value is not correct").contains("Review Appeal");
                break;
            case "Complaint SR":
                sa.get().assertThat(taskTypeList).as("Type Value is not correct").contains("Review Complaint");
                break;
            case "General Service Request":
                sa.get().assertThat(taskTypeList).as("Type Value is not correct").contains("General Three");
                break;
            case "HCC Outbound Call SR":
                System.out.println("Task type list:" + taskTypeList);
                sa.get().assertThat(taskTypeList).as("Type Value is not correct").containsIgnoringCase("HCC Outbound Call");
                break;
            case "Application SR":
            case "Application Renewal SR":
            case "Renewal SR":
                sa.get().assertThat(taskTypeList).as("Type Value is not correct").contains("Process Application");
                break;
            case "Appeal":
                sa.get().assertThat(taskTypeList.contains("Review Appeals Form") || taskTypeList.contains("GCNJ Appeals Acknowledgement Letter") || taskTypeList.contains("Follow-Up on Appeal") || taskTypeList.contains("GCNJ Resolve Appeal") || taskTypeList.contains("Generate IDR Successful Letter") || taskTypeList.contains("Generate IDR Unsuccessful Letter")).as("Type Value is not correct").isTrue();
                break;
            case "Just Cause":
                sa.get().assertThat(taskTypeList.contains("Just Cause Request") || taskTypeList.contains("JC Outbound Call")).as("Type Value is not correct").isTrue();
                break;
            case "Customer Service Complaint":
                sa.get().assertThat(taskTypeList.contains("QA Review Complaint") || taskTypeList.contains("Supervisor Review Complaint") || taskTypeList.contains("Outbound Call") || taskTypeList.contains("Complaint Outbound Call") || taskTypeList.contains("State Escalated Complaint")).as("Type Value is not correct").isTrue();
                break;
            case "Auto Assignment SR":
                sa.get().assertThat(taskTypeList.contains("AA Outbound Call")).as("Type Value is not correct").isTrue();
                break;
            case "For Cause SR":
                sa.get().assertThat(taskTypeList.contains("FC Review") || taskTypeList.contains("FC Missing Information")
                        || taskTypeList.contains("Invalid FC Member Notification") || taskTypeList.contains("FC District Outreach")
                        || taskTypeList.contains("FC District 2nd Outreach")).as("Type Value is not correct").isTrue();
                break;
            case "HRA/CAHMI Survey SR":
                sa.get().assertThat(taskTypeList.contains("Survey Outbound Call")).as("Type Value is not correct").isTrue();
                break;
            case "Tiered Support SR":
                sa.get().assertThat(taskTypeList.contains("Support Level 1")
                || taskTypeList.contains("Support Level 2")
                || taskTypeList.contains("Support Level 3")
                || taskTypeList.contains("Supervisor Escalation")
                || taskTypeList.contains("Escalation Resolved Member Notification")
                || taskTypeList.contains("Escalation Unresolved Member Notification")
                || taskTypeList.contains("Escalation Member Outreach")).as("Type Value is not correct").isTrue();
                break;
            default:
                sa.get().fail("Incorrect switch argument " + srType);
        }
    }

    public void validateSrLinkedToTaskOnContactPage(int additionalTaskID, String status, String taskName) {
        String xpathForTaskRow = "//td[text()='task_name']//parent::tr//td";
        List<WebElement> rowByTask = getDynamicWebElements(xpathForTaskRow, "task_name", taskName);
        assertEquals(rowByTask.get(1).getText(), (Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) + additionalTaskID) + "", "TaskId Value is missing");
        assertEquals(rowByTask.get(2).getText(), "Task", "Name Value is missing");
        assertEquals(rowByTask.get(3).getText(), taskName, "Type Value is not correct");
        assertEquals(rowByTask.get(5).getText(), status, "Status Value is not Created");
        assertTrue(rowByTask.get(4).getText().equals(taskValues.get().get("startDate"))
                || rowByTask.get(4).getText().equals(getCurrentDate()), "Status Date Value is not correct");
    }

    @Then("I verify link section contains {int} {string}")
    public void verify_linked_sr_linked_task(int count, String srType) {
        List<WebElement> name = getDynamicWebElements("//td[text()='task_name']//parent::tr//td[4]", "task_name", srType);
        List<WebElement> type = getDynamicWebElements("//td[text()='task_name']//parent::tr//td[5]", "task_name", srType);
        List<WebElement> statusDate = getDynamicWebElements("//td[text()='task_name']//parent::tr//td[6]", "task_name", srType);
        List<WebElement> status = getDynamicWebElements("//td[text()='task_name']//parent::tr//td[7]", "task_name", srType);
        name.forEach(x -> assertEquals(x.getText(), "Task"));
        type.forEach(x -> assertEquals(x.getText(), srType));
        statusDate.forEach(x -> assertEquals(x.getText(), taskValues.get().get("startDate")));
        statusDate.forEach(x -> assertEquals(x.getText(), getCurrentDate()));
        status.forEach(x -> assertEquals(x.getText(), "Complete"));
        assertEquals(count, name.size());
    }

    @Then("I verify Consumer is lined to Contact Record on the Contact Record & Active Contact Page")
    public void verify_consumer_on_cr_and_active_cr_page() {
        waitFor(3);
        String consumerId = projectName1.get().equalsIgnoreCase("IN-EB") ? externalConsumerID.get() : taskValues.get().get("consumerID");
        assertEquals(createGeneralTask.consumerInfo.get(0).getText(), consumerId, "ConsumerID Value is missing");
        assertEquals(createGeneralTask.consumerInfo.get(1).getText(), "Consumer Profile", "Name Value is missing");
        assertEquals(createGeneralTask.consumerInfo.get(2).getText(), "Consumer", "Type Value is missing");
        assertTrue(verifyDateFormat(createGeneralTask.consumerInfo.get(3)), "Consumer Status date is wrong");
        assertFalse(createGeneralTask.consumerInfo.get(4).getText().isEmpty(), "Status Value is missing");
    }

    @Then("I verify Task is lined to Contact Record on the Contact Record & Active Contact Page")
    public void verify_task_value_on_cr_and_active_cr_page(String srType) {
        switch (srType) {
            case "App SR V1":
            case "Application Renewal SR":
            case "Renewal SR":
                validateSrLinkedToTaskOnContactPage(1, "Created", "Process App V1");
                break;
            case "Appeals SR":
                validateSrLinkedToTaskOnContactPage(1, "Created", "Review Appeal");
                break;
            case "Complaint SR":
                validateSrLinkedToTaskOnContactPage(1, "Created", "Review Complaint");
                break;
            case "General Service Request":
                validateSrLinkedToTaskOnContactPage(1, "Created", "General Three");
                break;
            case "Application SR":
                validateSrLinkedToTaskOnContactPage(1, "Created", "Process Application");
                break;
            case "Just Cause":
                List<WebElement> listOfTypeTask = createGeneralTask.taskLinkInfo.stream()
                        .filter(x -> x.getText()
                                .equalsIgnoreCase("JC Outbound Call") || x.getText()
                                .equalsIgnoreCase("Just Cause Request"))
                        .collect(Collectors.toList());
                if (listOfTypeTask.size() == 2) {
                    listOfTypeTask.forEach(x -> {
                        validateSrLinkedToTaskOnContactPage(1, "Complete", "Just Cause Request");
                        validateSrLinkedToTaskOnContactPage(2, "Created", "JC Outbound Call");
                    });
                } else validateSrLinkedToTaskOnContactPage(1, "Created", "Just Cause Request");
                break;
            case "Customer Service Complaint":
                validateSrLinkedToTaskOnContactPage(1, "Created", "QA Review Complaint");
                break;
            default:
                fail("Incorrect switch argument " + srType);
        }
    }

    @Then("I verify SR is lined to Contact Record on the Contact Record & Active Contact Page")
    public void verify_sr_link_value_on_cr_and_active_cr_page() {
        waitFor(3);
        assertEquals(createGeneralTask.taskLinkInfo.get(0).getText(), CRM_TaskManagementStepDef.srID.get(), "SR id is missing");
        assertEquals(createGeneralTask.taskLinkInfo.get(1).getText(), "Service Request", "Name Value is missing");
        assertEquals(createGeneralTask.taskLinkInfo.get(2).getText(), taskValues.get().get("taskType"), "Type Value is not correct");
        assertEquals(createGeneralTask.taskLinkInfo.get(3).getText(), taskValues.get().get("startDate"), "Status Date Value is not correct");
        assertEquals(createGeneralTask.taskLinkInfo.get(4).getText(), "Open", "Status Value is not Created");
    }

    @And("I navigate to View Task details")
    public void navigate_View_Task_screen() {
        CRM_TaskManagementStepDef.srID.set(createGeneralTask.taskIDs.get(0).getText());
        createGeneralTask.taskIDs.get(0).click();
    }

    @And("I verify Case & Consumer Search section do not display")
    public void case_consumer_search_section_not_display() {
        assertTrue(isElementDisplayed(createGeneralTask.unlinkCaseOrConsumer), "unlink case/consumer button is not is displayed");
        click(createGeneralTask.unlinkCaseOrConsumer);
        i_click_on_link_contactlink_button_under_link_section();
        assertTrue(isElementDisplayed(createGeneralTask.caseConsumerBtn), "Case and Consumer Search section is displayed");
        createGeneralTask.caseConsumerBtn.click();
        assertTrue(createGeneralTask.caseConsumerSection.isDisplayed(), "Link text not showing");
    }

    @And("I select the record whose Case ID Blank")
    public void select_record_CaseID_blank() {
        for (int i = 0; i < createGeneralTask.search_recordCount.size(); i++) {
            if (createGeneralTask.caseIDValue.get(i).getText().contains("--")) {
                createGeneralTask.click_searchRecord.get(i).click();
                taskValues.get().put("consumerID", createGeneralTask.consumerIDValue.get(i).getText());
                synchronized (ConsumerID){ConsumerID.set(createGeneralTask.consumerIDValue.get(i).getText());}
                break;
            }
        }
    }

    @Then("I verify task is linked with ConsumerID and CaseID")
    public void verify_ConsumerID_CaseID_linkComponent() {
        waitFor(2);
        boolean caseLinked = false, consumer = false;

        if (createGeneralTask.linkrowsize.get(0).getText().contains("Case")) {
            caseLinked = true;
            verify_linked_case_value();
        } else {
            consumer = true;
            verify_linked_consumer_value();
        }
        if (createGeneralTask.linkrowsize.get(1).getText().contains("Case")) {
            caseLinked = true;
            verify_linked_case_value();
        } else {
            consumer = true;
            verify_linked_consumer_value();
        }
        assertTrue(caseLinked && consumer, "Case and consumer is not showed in link component");
    }


    @And("I verify link to task more than one search result cannot be done for {string}")
    public void verifyTaskLinkNotMoreThanOne(String condition) {
        switch (condition) {
            case "Case":
                i_expand_the_first_record_of_the_search_result();
                i_select_link_to_case_only_checkbox();
                link_record_case_button_get_displayed();
                click_link_case_button();
                case_consumer_search_section_not_display();
                break;

            case "Consumer on Case":
                i_expand_the_first_record_of_the_search_result();
                i_see_radio_button_associated_with_a_case_or_consumer();
                i_select_consumer_radio_button();
                link_record_consumer_button_get_displayed();
                click_link_consumer_button();
                case_consumer_search_section_not_display();
                break;

            case "Consumer":
                select_record_CaseID_blank();
                click_link_consumer_button();
                case_consumer_search_section_not_display();
                break;
        }
    }

    @And("I verify All Fields create task page has all the fields")
    public void verifyAllFieldsCreateTaskPageHasAllTheFields() {
        waitForVisibility(createGeneralTask.actionTaken, 5);
        assertTrue(createGeneralTask.actionTaken.isDisplayed(), "actionTaken field is not displayed");
        //assertTrue(createGeneralTask.externalApplicationId.isDisplayed(), "externalApplicationId field is not displayed");
        assertTrue(createGeneralTask.applicationSource.isDisplayed(), "applicationSource field is not displayed");
        assertTrue(createGeneralTask.appointmentDate.isDisplayed(), "appointmentDate field is not displayed");
        assertTrue(createGeneralTask.appointmentTime.isDisplayed(), "appointmentTime field is not displayed");
        assertTrue(createGeneralTask.caseWorkerFirstNameTxtBox.isDisplayed(), "caseWorkerFirstNameTxtBox field is not displayed");
        assertTrue(createGeneralTask.caseWorkerLastNameTxtBox.isDisplayed(), "caseWorkerLastNameTxtBox field is not displayed");
        assertTrue(createGeneralTask.channel.isDisplayed(), "channel field is not displayed");
        assertTrue(createGeneralTask.dateOfBirth.isDisplayed(), "dateOfBirth field is not displayed");
        assertTrue(createGeneralTask.fromPhone.isDisplayed(), "fromPhone field is not displayed");
        assertTrue(createGeneralTask.fromEmail.isDisplayed(), "fromEmail field is not displayed");
        assertTrue(createGeneralTask.fromName.isDisplayed(), "fromName field is not displayed");
        assertTrue(createGeneralTask.inboundCorrespondenceType.isDisplayed(), "inboundCorrespondenceType field is not displayed");
        assertTrue(createGeneralTask.inboundCorrespondenceId.isDisplayed(), "inboundCorrespondenceId field is not displayed");
        assertTrue(createGeneralTask.inboundCorrespondenceWorkableFlag.isDisplayed(), "inboundCorrespondenceWorkableFlag field is not displayed");
        assertTrue(createGeneralTask.informationType.isDisplayed(), "informationType field is not displayed");
        assertTrue(createGeneralTask.invalidAddressReason.isDisplayed(), "invalidAddressReason field is not displayed");
        assertTrue(createGeneralTask.newAddressLine1TxtBox.isDisplayed(), "newAddressLine1TxtBox field is not displayed");
        assertTrue(createGeneralTask.newAddressLine2TxtBox.isDisplayed(), "newAddressLine2TxtBox field is not displayed");
        assertTrue(createGeneralTask.newAddressCity.isDisplayed(), "newAddressCity field is not displayed");
        assertTrue(createGeneralTask.newAddressState.isDisplayed(), "newAddressState field is not displayed");
        assertTrue(createGeneralTask.newAddressZipCode.isDisplayed(), "newAddressZipCode field is not displayed");
        assertTrue(createGeneralTask.notificationId.isDisplayed(), "notificationId field is not displayed");
        assertTrue(createGeneralTask.oldAddressLine1TxtBox.isDisplayed(), "oldAddressLine1TxtBox field is not displayed");
        assertTrue(createGeneralTask.oldAddressLine2TxtBox.isDisplayed(), "oldAddressLine2TxtBox field is not displayed");
        assertTrue(createGeneralTask.oldAddressCity.isDisplayed(), "oldAddressCity field is not displayed");
        assertTrue(createGeneralTask.oldAddressState.isDisplayed(), "oldAddressState field is not displayed");
        assertTrue(createGeneralTask.oldAddressZipCode.isDisplayed(), "oldAddressZipCode field is not displayed");
        assertTrue(createGeneralTask.outboundCorrespondenceType.isDisplayed(), "outboundCorrespondenceType field is not displayed");
        assertTrue(createGeneralTask.outboundCorrespondenceId.isDisplayed(), "outboundCorrespondenceId field is not displayed");
        assertTrue(createGeneralTask.outreachLocation.isDisplayed(), "outreachLocation field is not displayed");
        assertTrue(createGeneralTask.planEffectiveDate.isDisplayed(), "planEffectiveDate field is not displayed");
        assertTrue(createGeneralTask.planChangeReason.isDisplayed(), "planChangeReason field is not displayed");
        assertTrue(createGeneralTask.planId.isDisplayed(), "planId field is not displayed");
        assertTrue(createGeneralTask.planName.isDisplayed(), "planName field is not displayed");
        assertTrue(createGeneralTask.planStartDate.isDisplayed(), "planStartDate field is not displayed");
        assertTrue(createGeneralTask.preferredCallBackDate.isDisplayed(), "preferredCallBackDate field is not displayed");
        assertTrue(createGeneralTask.preferredCallBackTime.isDisplayed(), "preferredCallBackTime field is not displayed");
        assertTrue(createGeneralTask.preferredLanguage.isDisplayed(), "preferredLanguage field is not displayed");
        assertTrue(createGeneralTask.preferredPhoneTxtBox.isDisplayed(), "preferredPhoneTxtBox field is not displayed");
        assertTrue(createGeneralTask.program.isDisplayed(), "program field is not displayed");
        assertTrue(createGeneralTask.providerAddressLine1TxtBox.isDisplayed(), "providerAddressLine1TxtBox field is not displayed");
        assertTrue(createGeneralTask.providerAddressLine2TxtBox.isDisplayed(), "providerAddressLine2TxtBox field is not displayed");
        assertTrue(createGeneralTask.providerAddressCity.isDisplayed(), "providerAddressCity field is not displayed");
        assertTrue(createGeneralTask.providerAddressState.isDisplayed(), "providerAddressState field is not displayed");
        assertTrue(createGeneralTask.providerAddressSZipCode.isDisplayed(), "providerAddressSZipCode field is not displayed");
        assertTrue(createGeneralTask.providerAddressCounty.isDisplayed(), "providerAddressCounty field is not displayed");
        assertTrue(createGeneralTask.providerFirstNameTxtBox.isDisplayed(), "providerFirstNameTxtBox field is not displayed");
        assertTrue(createGeneralTask.providerLastNameTxtBox.isDisplayed(), "providerLastNameTxtBox field is not displayed");
        assertTrue(createGeneralTask.providerNpiTxtBox.isDisplayed(), "providerNpiTxtBox field is not displayed");
        assertTrue(createGeneralTask.providerPhoneTxtBox.isDisplayed(), "providerPhoneTxtBox field is not displayed");
        assertTrue(createGeneralTask.providerRecordIssue.isDisplayed(), "providerRecordIssue field is not displayed");
        assertTrue(createGeneralTask.providerStateIdTxtBox.isDisplayed(), "providerStateIdTxtBox field is not displayed");
        assertTrue(createGeneralTask.cantactReason.isDisplayed(), "cantactReason field is not displayed");
        assertFalse(createGeneralTask.requestedNewPlanChkBx.isSelected(), "requestedNewPlanChkBx field is not displayed");
        assertFalse(createGeneralTask.requestedNewProviderChkBx.isSelected(), "requestedNewProviderChkBx field is not displayed");
        assertTrue(createGeneralTask.returnedMailReason.isDisplayed(), "returnedMailReason field is not displayed");
        assertFalse(createGeneralTask.urgentAccessToCareChkBx.isSelected(), "urgentAccessToCareChkBx field is not displayed");
    }

    @When("I select {string} option in task type drop down")
    public void selectValueInTaskTypeDropDown(String taskType) {
        waitFor(3);
        waitForVisibility(createGeneralTask.lstTaskType, 5);
        selectDropDownSlider(createGeneralTask.lstTaskType, taskType);
        waitFor(3);
    }

    @When("I populate required data to create task {string} {string}")
    public void populateRequiredDataToCreateTask(String contactReason, String preferredPhone) {
        waitFor(5);
        waitForVisibility(createGeneralTask.cantactReason, 5);
        Actions actions = new Actions(Driver.getDriver());
        selectDropDown(createGeneralTask.cantactReason, contactReason);
        actions.moveToElement(createGeneralTask.preferredPhoneTxtBox).click().build().perform();
        waitFor(1);
        clearAndFillText(createGeneralTask.preferredPhoneTxtBox, preferredPhone);
        waitFor(2);
    }

    @When("Get the order of field displayed")
    public void getTheOrderOfFieldDisplayed() {
        waitForVisibility(createGeneralTask.complaintAboutDrDn, 10);
        for (int i = 0; i < createGeneralTask.njTaskAdditionalFields.size(); i++) {
            orderOfFields.get().add(createGeneralTask.njTaskAdditionalFields.get(i).getText());
        }
    }

    @Then("I click Initiate and save the changes")
    public void iClickInitiate() {
        waitFor(5);
        click(createGeneralTask.btnSave);
        waitFor(7);
        click(createGeneralTask.taskAndService);
        waitFor(2);
        scrollUpUsingActions(2);
        waitFor(1);
        click(createGeneralTask.initiate);
        waitFor(2);
        click(createGeneralTask.taskDetsave);
    }

    @And("I am navigated to the Active Contact page and Verify Link Component for Task")
    public void iAmNavigatedToTheActiveContactPageAndVerifyLinkComponentForTask() {
        click(createGeneralTask.activeCon);
        synchronized (ConsumerID){ConsumerID.set(createGeneralTask.acTconsumerId.getText());}
        Assert.assertNotNull(createGeneralTask.firstIdLink);
        synchronized (taskId){taskId.set(createGeneralTask.secondIdLink.getText());}
        assertNotNull(createGeneralTask.secondIdLink);
        assertEquals(createGeneralTask.firstNameLink.getText(), "Consumer Profile");
        assertEquals(createGeneralTask.secondNameLink.getText(), "Task");
        assertEquals(createGeneralTask.firstTypeLink.getText(), "Consumer");
        assertEquals(createGeneralTask.scndTypeLink.getText(), "General");
        assertEquals(createGeneralTask.scndstatusDateLink.getText(), getCurrentDate());
        assertEquals(createGeneralTask.scndStatusLink.getText(), "Open");
    }

    @And("I search Task by Task Id for record")
    public void iSearchTaskByTaskIdForRecord() {
        linksComponentPage.taskIdForLinkVerification.sendKeys(taskId.get());
        waitFor(2);
        createGeneralTask.taskSearchbt.click();
    }

    @When("I search Task by current status date for record")
    public void iSearchTaskByStatusDateForRecord() {
        waitFor(2);
        //clearAndFillText(taskSearch.statusDate, getCurrentDate());
        clearAndFillText(taskSearch.statusDate, "07/19/2022");
        waitFor(1);
        singleSelectFromDropDown(taskSearch.priorityDropdown, "3");
        waitFor(2);
        taskSearch.searchBtn.click();
        waitFor(3);
    }

    @When("I click on second Task ID on Task Search")
    public void iClickOnSecondTaskIDOnTaskSearch() {
        scrollDownUsingActions(3);
        waitFor(4);
        jsClick(createGeneralTask.firstTaskIdBt);
        waitFor(3);
    }

    @Then("Verify Link Component for Contact Record")
    public void veriflyLinkComponentForContactRecord() {
        waitFor(1);
        assertEquals(createGeneralTask.taskSearchId.getText(), taskId.get());
        assertEquals(createGeneralTask.taskSearhType.getText(), "General");
        assertNotNull(createGeneralTask.taskSearchStatus.getText());
        assertEquals(createGeneralTask.taskSearchStatusDate.getText(), getCurrentDate());
        assertEquals(createGeneralTask.taskConsumerId.getText(), ConsumerID.get());
    }

    public void SelectTaskDetailsWithStatus(String taskType, String priority, String assignee, String taskInfo, String status) {
        waitForVisibility(createGeneralTask.lstTaskPriority, 10);
        if (!taskType.isEmpty()) {
            selectDropDown(createGeneralTask.lstTaskType, taskType);
            taskValues.get().put("taskType", taskType);
        }
        if (!status.isEmpty()) {
            selectDropDown(createGeneralTask.taskStatus, status);
            taskValues.get().put("status", status);
        }
        if (!priority.isEmpty()) {
            selectDropDown(createGeneralTask.lstTaskPriority, priority);
            taskValues.get().put("priority", priority);
        }
        if (!assignee.isEmpty()) {
            if (assignee.equals("Service")) {
                assignee = createGeneralTask.userAccountName.getText();
            }
            sendKeyToTextField(createGeneralTask.lstAssignee, assignee);
            waitFor(1);
        }
        if (!taskInfo.isEmpty())
            sendKeyToTextField(createGeneralTask.txtTaskInfo, taskInfo);
    }

    @Then("I will see the following field labels in New Address section")
    public void i_will_see_the_following_field_labels_in_New_Address_section(List<String> fields) {
        waitFor(1);
        displayedInOrder.get().clear();
        for (int j = 0; j < createGeneralTask.newAddressDetailsLabels.size(); j++) {
            displayedInOrder.get().add(createGeneralTask.newAddressDetailsLabels.get(j).getText());
        }
        assertEquals(displayedInOrder.get(), fields, "New Address section fields not displayed in order");
    }

    @Then("I will see the following field labels in Old Address section")
    public void i_will_see_the_following_field_labels_in_Old_Address_section(List<String> fields) {
        displayedInOrder.get().clear();
        for (int j = 0; j < createGeneralTask.oldAddressDetailsLabels.size(); j++) {
            displayedInOrder.get().add(createGeneralTask.oldAddressDetailsLabels.get(j).getText());
        }
        Assert.assertEquals(displayedInOrder.get(), fields, "Old Address section fields not displayed in order");
    }

    @Then("I will see the following field labels in Provider Address section")
    public void i_will_see_the_following_field_labels_in_Provider_Address_section(List<String> fields) {
        displayedInOrder.get().clear();
        for (int j = 0; j < createGeneralTask.providerAddressDetailsLabels.size(); j++) {
            displayedInOrder.get().add(createGeneralTask.providerAddressDetailsLabels.get(j).getText());
        }
        assertEquals(displayedInOrder.get(), fields, "Provider Address section fields not displayed in order");
    }

    @Then("I verify Field Groups are ordered in Address create task page")
    public void i_verify_address_field_groups_should_be_ordered() {
        verifyFieldsInCreateTaskPage();
        int j = 1;
        for (int i = 0; i < createGeneralTask.addressesOrder.size(); i++) {
            assertTrue(APITaskManagementController.fieldOrder.get().get(j).toLowerCase().contains(createGeneralTask.
                    addressesOrder.get(i).getText().replace("DETAILS", "").toLowerCase()));
            if (APITaskManagementController.fieldOrder.get().get(j).toLowerCase().contains("provider address")) {
                j += 6;
            } else {
                j += 5;
            }
        }
    }

    @Then("I will see the following field labels in New Address section in view task")
    public void i_will_see_the_following_field_labels_in_New_Address_section_in_view_task(List<String> fields) {
        waitFor(1);
        displayedInOrder.get().clear();
        for (int j = 0; j < createGeneralTask.newAddressDetailsLabelsOnViewTask.size(); j++) {
            displayedInOrder.get().add(createGeneralTask.newAddressDetailsLabelsOnViewTask.get(j).getText());
        }
        Assert.assertEquals(displayedInOrder.get(), fields, "New Address section fields not displayed in order in View task page");
    }

    @Then("I will see the following field labels in Old Address section in view task")
    public void i_will_see_the_following_field_labels_in_Old_Address_section_in_view_task(List<String> fields) {
        displayedInOrder.get().clear();
        for (int j = 0; j < createGeneralTask.oldAddressDetailsLabelsOnViewTask.size(); j++) {
            displayedInOrder.get().add(createGeneralTask.oldAddressDetailsLabelsOnViewTask.get(j).getText());
        }
        Assert.assertEquals(displayedInOrder.get(), fields, "Old Address section fields not displayed in order in view task");
    }

    @Then("I will see the following field labels in Provider Address section in view task")
    public void i_will_see_the_following_field_labels_in_Provider_Address_section_in_view_task(List<String> fields) {
        displayedInOrder.get().clear();
        for (int j = 0; j < createGeneralTask.providerAddressDetailsLabelsOnViewTask.size(); j++) {
            displayedInOrder.get().add(createGeneralTask.providerAddressDetailsLabelsOnViewTask.get(j).getText());
        }
        Assert.assertEquals(displayedInOrder.get(), fields, "Provider Address section fields not displayed in order in view task");
    }

    @Then("I verify Address Field Groups are ordered in View task")
    public void i_verify_address_field_groups_should_be_ordered_in_view_task(List<String> expectedOrder) {
        waitFor(3);
        List<String> actualOrder = new ArrayList<>();
        for (WebElement value : createGeneralTask.addressesOrderInViewTask) {
            actualOrder.add(value.getText());
        }
        Assert.assertEquals(actualOrder, expectedOrder, "Address Field Groups not in ordered");

    }

    @Then("I verify task is linked with Contact ID")
    public void verify_linked_contact_value() {
        scrollDown();
        waitFor(2);
        assertTrue(createGeneralTask.contactRecordInfo.get(2).getText().equals(taskValues.get().get("contactRecord")) ||
                createGeneralTask.contactRecordInfo.get(2).getText().chars().allMatch(Character::isDigit), "ContactID Value is missing");
        assertEquals(createGeneralTask.contactRecordInfo.get(3).getText(), "Contact Record", "Name Value is missing");
        assertEquals(createGeneralTask.contactRecordInfo.get(4).getText(), "General", "Type Value is not showing General");
        assertEquals(createGeneralTask.contactRecordInfo.get(5).getText(), taskValues.get().get("startDate"), "Status Date Value is not correct");
        assertEquals(createGeneralTask.contactRecordInfo.get(6).getText(), "Incomplete", "Status Value is not Incomplete");
    }

    @Then("I verify the link section has case consumer and contact id information")
    public void verify_ConsumerID_CaseID_ContactID_linkComponent() {
        waitFor(5);
        scrollDownUsingActions(6);
        boolean caseLink = false, consumer = false, contact = false;

        for (int i = 0; i < createGeneralTask.linkrowsize.size(); i++) {
            String linkType = null;
            if (createGeneralTask.linkrowsize.get(i).getText().equalsIgnoreCase(taskValues.get().get("consumerName"))) {
                linkType = createGeneralTask.consumerInfo.get(2).getText();
            } else {
                linkType = createGeneralTask.linkrowsize.get(i).getText();
            }
            switch (linkType) {
                case "Contact Record":
                    verify_linked_contact_value();
                    contact = true;
                    break;
                case "Case":
                    verify_linked_case_value();
                    caseLink = true;
                    break;
                case "Consumer":
                    verify_linked_consumer_value();
                    consumer = true;
                    break;
            }
        }

        if (createGeneralTask.linkrowsize.size() == 1) {
            assertTrue(contact, "Link section is not displayed contact record information");
        } else {
            assertTrue(caseLink && consumer && contact,
                    "Link section is not displayed case/consumer/contact record information");
        }
    }

    @Then("I verify the link section has case consumer and task information")
    public void verify_ConsumerID_CaseID_TaskID_linkComponent() {
        waitFor(2);
        scrollDownUsingActions(5);
        boolean caseLink = false, consumer = false, task = false;

        for (int i = 0; i < createGeneralTask.contactLinkrowsize.size(); i++) {
            String linkType = createGeneralTask.contactLinkrowsize.get(i).getText();
            switch (linkType) {
                case "Task":
                    if (!CRM_TaskManagementStepDef.taskId.get().isEmpty())
                        sa.get().assertThat(createGeneralTask.taskLinkInfo.get(0).getText()).as("TaskId Value is missing").isEqualTo(CRM_TaskManagementStepDef.taskId.get());
                    else
                        sa.get().assertThat(createGeneralTask.taskLinkInfo.get(0).getText()).as("TaskId Value is missing").isNotBlank();
                    sa.get().assertThat(createGeneralTask.taskLinkInfo.get(1).getText()).as("Name Value is missing").isEqualTo("Task");
                    sa.get().assertThat(createGeneralTask.taskLinkInfo.get(2).getText()).as("Type Value is not correct").isEqualTo(taskValues.get().get("taskType"));
                    sa.get().assertThat(createGeneralTask.taskLinkInfo.get(3).getText()).as("Status Date Value is not correct").isEqualTo(taskValues.get().get("startDate"));
                    sa.get().assertThat(createGeneralTask.taskLinkInfo.get(4).getText().equals("Created") ||
                            createGeneralTask.taskLinkInfo.get(4).getText().equals("In-Progress")).as("Status Value is not Created").isTrue();
                    task = true;
                    break;
                case "Case":
                    sa.get().assertThat(createGeneralTask.caseInfo.get(1).getText()).as("CaseID Value is missing").isEqualTo(taskValues.get().get("caseID"));
                    sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("Name Value is missing").isEqualTo("Case");
                    sa.get().assertThat(createGeneralTask.caseInfo.get(3).getText()).as("Type Value is not showing null").isEqualTo("-- --");
                    sa.get().assertThat(createGeneralTask.caseInfo.get(4).getText()).as("Status Date Value is not showing blank").isEqualTo("--/--/----");
                    sa.get().assertThat(createGeneralTask.caseInfo.get(5).getText()).as("Status Value is not showing null").isEqualTo("-- --");
                    caseLink = true;
                    break;
//                     refactoring by priyal on 14-09-2021
                case "Consumer Profile":
                    sa.get().assertThat(createGeneralTask.consumerInfo.get(0).getText().equals(taskValues.get().get("consumerID")) || createGeneralTask.consumerInfo.get(0).getText().equals("Incomplete App")).as("ConsumerID Value is missing").isTrue();
                    sa.get().assertThat(createGeneralTask.consumerInfo.get(1).getText()).as("Name Value is missing").isEqualTo("Consumer Profile");
                    sa.get().assertThat(createGeneralTask.consumerInfo.get(2).getText()).as("Type Value is missing").isEqualTo("Consumer");
                    sa.get().assertThat(verifyDateFormat(createGeneralTask.consumerInfo.get(3))).as("Consumer Status date is wrong").isTrue();
                    sa.get().assertThat(createGeneralTask.consumerInfo.get(4).getText()).as("Status Value is missing").isNotBlank();
                    consumer = true;
                    break;
            }
        }
        if (isElementDisplayed(activeContactPage.generalConsumerInContactSighn)) {
            scrollDownUsingActions(2);
            if (createGeneralTask.activeContactList.size() == 1)
                sa.get().assertThat(task).as("Link section is not displayed task information").isTrue();
        } else if (createGeneralTask.contactLinkrowsize.size() == 1) {
            sa.get().assertThat(task).as("Link section is not displayed task information").isTrue();
        } else {
            sa.get().assertThat(caseLink && consumer && task).as("Link section is not displayed case/consumer/task").isTrue();
        }
    }

    @When("I create {string} task with priority as {string} taskStatus as {string} assignee as {string} task info as {string} complaintAbout as {string} name as {string} contactReason as {string} prefCallBackDate as {string} prefCallBackTime as {string} prefPhone as {string} extCaseID as {string} appID as {string}")
    public void SelectTaskDetailsForNJ(String taskType, String priority, String taskStatus, String assignee, String taskInfo,
                                       String complaintAbout, String name, String contactReason,
                                       String prefCallBackDate, String prefCallBackTime, String prefPhone,
                                       String extCaseID, String exAppId) {
        waitForVisibility(createGeneralTask.lstTaskPriority, 10);
        SelectTaskDetails(taskType, priority, assignee, taskInfo);

        if (!taskStatus.isEmpty()) {
            selectDropDown(createGeneralTask.taskStatus, taskStatus);
            taskValues.get().put("taskStatus", taskStatus);
        }
        if (!complaintAbout.isEmpty()) {
            selectDropDown(createGeneralTask.complaintAboutDrDn, complaintAbout);
            taskValues.get().put("complaintAbout", complaintAbout);
        }
        if (!name.isEmpty()) {
            sendKeyToTextField(createGeneralTask.name, name);
            taskValues.get().put("name", name);
        }
        if (!contactReason.isEmpty()) {
            selectOptionFromMultiSelectDropDown(createGeneralTask.contactReason, contactReason);
            taskValues.get().put("contactReason", contactReason);
        }
        if (!prefCallBackDate.isEmpty()) {
            clearAndFillText(createGeneralTask.preferredCallBackDate, prefCallBackDate);
            taskValues.get().put("prefCallBackDate", prefCallBackDate);
        }
        if (!prefCallBackTime.isEmpty()) {
            clearAndFillText(createGeneralTask.preferredCallBackTime, prefCallBackTime);
            taskValues.get().put("prefCallBackTime", prefCallBackTime);
        }
        if (!prefPhone.isEmpty()) {
            clearAndFillText(createGeneralTask.preferredPhoneTxtBox, prefPhone);
            taskValues.get().put("prefPhone", prefPhone);
        }
        if (!extCaseID.isEmpty()) {
            sendKeyToTextField(createGeneralTask.externalCaseId, extCaseID);
            taskValues.get().put("extCaseID", extCaseID);
        }
        if (!exAppId.isEmpty()) {
            sendKeyToTextField(createGeneralTask.externalApplicationId, exAppId);
            taskValues.get().put("exAppId", exAppId);
        }
    }

    @And("I verify text box Date and Time field value and error message for following fields")
    public void verifyDateAndTimeFieldValueAndErrorMessage(List<String> fieldsName) {
        waitForVisibility(createGeneralTask.lblDueDate, 5);
        synchronized (orderOfFields){
            orderOfFields.set(new ArrayList<>(fieldsName));
        }
        for (int i = 0; i < orderOfFields.get().size(); i++) {
            switch (orderOfFields.get().get(i)) {
                case "PREFERRED CALL BACK DATE":
                    validateDateField(createGeneralTask.preferredCallBackDate, "PREFERRED CALL BACK DATE");
                    break;

                case "PREFERRED CALL BACK TIME":
                    createGeneralTask.preferredCallBackTime.sendKeys("15");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.invalidTimeFormat)).as(" preferredCallBackTime field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.preferredCallBackTime, "1234A");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.invalidTimeFormat)).as("preferredCallBackTime field error message is displayed").isFalse();
                    break;

                case "PREFERRED PHONE":
                    validatePhoneField(createGeneralTask.preferredPhoneTxtBox, "PREFERRED PHONE");
                    break;

                case "NAME":
                    createGeneralTask.name.sendKeys(getRandomString(60));
                    sa.get().assertThat(createGeneralTask.name.getAttribute("value")).as("Name field length is not 60 characters").hasSize(60);
                    clearAndFillText(createGeneralTask.name, getRandomString(100));
                    sa.get().assertThat(createGeneralTask.name.getAttribute("value")).as("Name field length is not 60 characters").hasSize(60);
                    break;

                case "Date Translation Escalated":
                    validateDateField(createGeneralTask.dateTranslationEscalated, "Date Translation Escalated");
                    break;

                case "Date Translation Received":
                    validateDateField(createGeneralTask.dateTranslationReceived, "Date Translation Received");
                    break;

                case "Date Translation Mailed":
                    validateDateField(createGeneralTask.dateTranslationMailed, "Date Translation Mailed");
                    break;

                case "VaCMS Case ID":
                    clearAndFillText(createGeneralTask.externalCaseId, "1234Test 56Test 789 987");
                    sa.get().assertThat(createGeneralTask.externalCaseId.getAttribute("value"))
                            .as("VaCMS Case ID field length is not 9 characters").hasSize(9)
                            .as("VaCMS Case ID field accepting alphabets + space").isEqualTo("123456789");
                    break;

                case "Medicaid ID/RID":
                case "MMIS Member ID":
                    clearAndFillText(createGeneralTask.externalConsumerID, "1234Test 56Test 789 0998");
                    sa.get().assertThat(createGeneralTask.externalConsumerID.getAttribute("value"))
                            .as("MMIS Member ID/Medicaid ID/RID field length is not 12 characters").hasSize(12)
                            .as("MMIS Member ID/Medicaid ID/RID field accepting alphabets + space").isEqualTo("123456789099");
                    break;

                case "Application Id":
                    clearAndFillText(createGeneralTask.externalApplicationId, "Test 12345 Test");
                    sa.get().assertThat(createGeneralTask.externalApplicationId.getAttribute("value"))
                            .as("Application Id field length is not 9 characters").hasSize(9)
                            .as("Application Id field accepting more then 9 characters ").isEqualTo("Test 1234");
                    break;

                case "Due Date":
                    validateDateField(createGeneralTask.dueDate, "Due Date");
                    break;

                case "CP contact record Id":
                    createGeneralTask.cpCRId.sendKeys("12345Test");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.cpCRErrMsg)).as("CP contact record Id field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.cpCRId, "12345");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.cpCRErrMsg)).as("CP contact record Id field error message is displayed").isFalse();
                    break;

                case "CP SR Id":
                    createGeneralTask.cpSRId.sendKeys("12345Test");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.cpSRErrMsg)).as("CP SR Id field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.cpSRId, "12345");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.cpSRErrMsg)).as("CP SR Id field error message is displayed").isFalse();
                    break;

                case "CP Task Id":
                    createGeneralTask.cpTaskId.sendKeys("12345Test");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.cpTaskErrMsg)).as("CP Task Id field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.cpTaskId, "12345");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.cpTaskErrMsg)).as("CP Task Id field error message is displayed").isFalse();
                    break;

                case "Received Date":
                    validateDateField(createGeneralTask.receivedDate, "Received Date");
                    break;

                case "Date Resent":
                    validateDateField(createGeneralTask.dateResent, "Date Resent");
                    break;

                case "Remand Due Date":
                    validateDateField(createGeneralTask.remandDueDate, "Remand Due Date");
                    break;

                case "Remand Completion Date":
                    validateDateField(createGeneralTask.remandCompletionDate, "Remand Completion Date");
                    break;

                case "Case Worker First Name":
                    createGeneralTask.caseWorkerFirstNameTxtBox.sendKeys("Robert55teddy");
                    jsClick(myTask.saveBtn);
                    if (isElementDisplayed(createGeneralTask.cwFirstNameErrMsg))
                        sa.get().assertThat(createGeneralTask.cwFirstNameErrMsg.isDisplayed()).as("Case Worker First Name field error message is not displayed").isTrue();
                    else
                        sa.get().assertThat(isElementDisplayed(createGeneralTask.cwFirstNameErrMsg1)).as("Case Worker First Name field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.caseWorkerFirstNameTxtBox, "Solar  McKenzo");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.cwFirstNameErrMsg)).as("Case Worker First Name field error message is displayed").isFalse();
                    sa.get().assertThat(createGeneralTask.caseWorkerFirstNameTxtBox.getAttribute("value")).as("Case Worker First Name accepts incorrect input").isEqualTo("Solar  McKenzo");
                    createGeneralTask.caseWorkerFirstNameTxtBox.sendKeys(getRandomString(60));
                    sa.get().assertThat(createGeneralTask.caseWorkerFirstNameTxtBox.getAttribute("value").length()).as("Case Worker First Name field length is not 50 characters").isEqualTo(50);
                    break;

                case "Case Worker Last Name":
                    createGeneralTask.caseWorkerLastNameTxtBox.sendKeys("Robert55teddy");
                    jsClick(myTask.saveBtn);
                    if (isElementDisplayed(createGeneralTask.cwLastNameErrMsg))
                        sa.get().assertThat(createGeneralTask.cwLastNameErrMsg.isDisplayed()).as("Case Worker Last Name field error message is not displayed").isTrue();
                    else
                        sa.get().assertThat(isElementDisplayed(createGeneralTask.cwLastNameErrMsg1)).as("Case Worker Last Name field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.caseWorkerLastNameTxtBox, "Solar  McKenzo");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.cwLastNameErrMsg)).as("Case Worker Last Name field error message is displayed").isFalse();
                    sa.get().assertThat(createGeneralTask.caseWorkerLastNameTxtBox.getAttribute("value")).as("Case Worker Last Name accepts incorrect input").isEqualTo("Solar  McKenzo");
                    createGeneralTask.caseWorkerLastNameTxtBox.sendKeys(getRandomString(60));
                    sa.get().assertThat(createGeneralTask.caseWorkerLastNameTxtBox.getAttribute("value").length()).as("Case Worker Last Name field length is not 50 characters").isEqualTo(50);
                    break;

                case "Appointment Time":
                    validateTimeField(createGeneralTask.appointmentTime, "Appointment Time");
                    break;

                case "Appointment Date":
                    validateDateField(createGeneralTask.appointmentDate, "Appointment Date");
                    break;

                case "Provider Phone":
                    validatePhoneField(createGeneralTask.providerPhoneTxtBox, "Provider Phone");
                    break;

                case "Provider First Name":
                    createGeneralTask.providerFirstNameTxtBox.sendKeys("Robert55teddy");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.providerFirstNameErrMsg)).as("provider First Name field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.providerFirstNameTxtBox, "James  Bernard");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.providerFirstNameErrMsg)).as("provider First Name field error message is displayed").isFalse();
                    sa.get().assertThat(createGeneralTask.providerFirstNameTxtBox.getAttribute("value")).as("provider First Name accepts incorrect input").isEqualTo("James  Bernard");
                    createGeneralTask.providerFirstNameTxtBox.sendKeys(getRandomString(60));
                    sa.get().assertThat(createGeneralTask.providerFirstNameTxtBox.getAttribute("value").length()).as("providerFirstName field length is not 50 characters").isEqualTo(50);
                    break;

                case "Provider Last Name":
                    createGeneralTask.providerLastNameTxtBox.sendKeys("Robert55teddy");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.providerLastNameErrMsg)).as("provider Last Name field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.providerLastNameTxtBox, "James  Bernard");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.providerLastNameErrMsg)).as("provider Last Name field error message is displayed").isFalse();
                    sa.get().assertThat(createGeneralTask.providerLastNameTxtBox.getAttribute("value")).as("James  Bernard", "provider Last Name accepts incorrect input").isEqualTo("James  Bernard");
                    createGeneralTask.providerLastNameTxtBox.sendKeys(getRandomString(60));
                    sa.get().assertThat(createGeneralTask.providerLastNameTxtBox.getAttribute("value").length()).as("providerLastName field length is not 50 characters").isEqualTo(50);
                    break;

                case "Provider Email":
                    validateEmailField(createGeneralTask.providerEmail, "Provider Email", createGeneralTask.providerEmailError);
                    break;

                case "AR Email":
                    validateEmailField(createGeneralTask.AREmail, "AR Email", createGeneralTask.AREmailError);
                    break;

                case "AR Phone":
                    validatePhoneField(createGeneralTask.ARPhone, "AR Phone");
                    break;

                case "Organization":
                    sendKeyToTextField(createGeneralTask.organization, "1234 Test5@1234 Test5$1234 Test5#1234 Test5@");
                    sa.get().assertThat(createGeneralTask.organization.getAttribute("value")).as("Organization field length is not 30 characters").hasSize(30);
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(createGeneralTask.organizationErrorMessage.isDisplayed()).as("ORGANIZATION Must Contain Alphabetical Values Only error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.organization, "maersk");
                    break;

                case "AR First Name":
                    createGeneralTask.ARFirstNameTxtBox.sendKeys("Robert55teddy");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.ARFirstNameError)).as("AR First Name field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.ARFirstNameTxtBox, "James  Bernard");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.ARFirstNameError)).as("AR First Name field error message is displayed").isFalse();
                    sa.get().assertThat(createGeneralTask.ARFirstNameTxtBox.getAttribute("value")).as("AR First Name accepts incorrect input").isEqualTo("James  Bernard");
                    createGeneralTask.ARFirstNameTxtBox.sendKeys(getRandomString(25));
                    sa.get().assertThat(createGeneralTask.ARFirstNameTxtBox.getAttribute("value")).as("ARFirstName field length is not 15 characters").hasSize(15);
                    break;

                case "AR Last Name":
                    createGeneralTask.ARLastNameTxtBox.sendKeys("Robert55teddy");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.ARLastNameError)).as("AR Last Name field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.ARLastNameTxtBox, "James  Bernard");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.ARLastNameError)).as("AR Last Name field error message is displayed").isFalse();
                    sa.get().assertThat(createGeneralTask.ARLastNameTxtBox.getAttribute("value")).as("AR Last Name accepts incorrect input").isEqualTo("James  Bernard");
                    createGeneralTask.ARLastNameTxtBox.sendKeys(getRandomString(25));
                    sa.get().assertThat(createGeneralTask.ARLastNameTxtBox.getAttribute("value")).as("AR LastName field length is not 15 characters").hasSize(15);
                    break;

                case "AR Address Line 1":
                    createGeneralTask.arAddressLine1TxtBox.sendKeys("333");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.arAddressLine1ErrorMsg)).as("AR Address Line 1 field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.arAddressLine1TxtBox, "Main St");
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.arAddressLine1ErrorMsg)).as("AR Address Line 1 field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.arAddressLine1TxtBox, RandomStringUtils.random(52, true, true));
                    sa.get().assertThat(createGeneralTask.arAddressLine1TxtBox.getAttribute("value")).as("AR Address Line 1 field length is not 50 characters").hasSize(50);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.arAddressLine1ErrorMsg)).as("AR Address Line 1 field error message is displayed").isFalse();
                    clearAndFillText(createGeneralTask.arAddressLine1TxtBox, "33 Main St@#$%^");
                    sa.get().assertThat(createGeneralTask.arAddressLine1TxtBox.getAttribute("value")).as("AR Address Line 1 value is invalid").isEqualTo("33 Main St");
                    break;

                case "AR Address Line 2":
                    createGeneralTask.arAddressLine2TxtBox.sendKeys(RandomStringUtils.random(32, true, true));
                    sa.get().assertThat(createGeneralTask.arAddressLine2TxtBox.getAttribute("value")).as("AR Address Line 2 field length is not 30 characters").hasSize(30);
                    clearAndFillText(createGeneralTask.arAddressLine2TxtBox, "33 Main St@#$%^");
                    sa.get().assertThat(createGeneralTask.arAddressLine2TxtBox.getAttribute("value")).as("AR Address Line 2 value is invalid").isEqualTo("33 Main St");
                    break;

                case "AR City":
                    createGeneralTask.arCityTxtBox.sendKeys(RandomStringUtils.random(32, true, true));
                    sa.get().assertThat(createGeneralTask.arCityTxtBox.getAttribute("value")).as("AR City field length is not 30 characters").hasSize(30);
                    clearAndFillText(createGeneralTask.arCityTxtBox, "Denver@#$%^");
                    sa.get().assertThat(createGeneralTask.arCityTxtBox.getAttribute("value")).as("AR City value is invalid").isEqualTo("Denver");
                    break;

                case "AR State":
                    createGeneralTask.arStateTxtBox.sendKeys(RandomStringUtils.random(32, true, true));
                    sa.get().assertThat(createGeneralTask.arStateTxtBox.getAttribute("value")).as("AR State field length is not 30 characters").hasSize(30);
                    clearAndFillText(createGeneralTask.arStateTxtBox, "DC@#$%^");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.arStateErrorMsg)).as("AR State field error message is not displayed").isTrue();
                    break;

                case "AR Zip Code":
                    createGeneralTask.arZipCodeTxtBox.sendKeys("223");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.arZipCodeMustContainFiveCharErrorMessage)).as("AR Zip Code field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.arZipCodeTxtBox, "80001");
                    actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.arZipCodeErrorMsg)).as("AR Zip Code field error message is displayed").isFalse();
                    break;

                case "Number Of Applicants In Household":
                    createGeneralTask.numberOfApplicantsInHousehold.sendKeys("1Test56");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.errorMessageOnlyCanAcceptNumericValuesForHouseHold)).as("# of Applicants In Household Field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.numberOfApplicantsInHousehold, "152");
                    sa.get().assertThat(createGeneralTask.numberOfApplicantsInHousehold.getAttribute("value"))
                            .as("# of approved applicants field length is not 2 characters").hasSize(2)
                            .as("# of approved applicants field accepting alphabets + space ").isEqualTo("15");
                    clearAndFillText(createGeneralTask.numberOfApplicantsInHousehold, "0");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.getNumberOfApprovedApplicantsHouseholdErrorMsg)).as("# of Approved Applicant Household Field error message is not displayed").isTrue();
                    break;

                case "Number Of Approved Applicants":
                    createGeneralTask.numberOfApprovedApplicants.sendKeys("1n");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.errorMessageOnlyCanAcceptNumericValuesForApprovedApplicants)).as("# of Approved Applicant Field error message is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.numberOfApprovedApplicants, "152");
                    sa.get().assertThat(createGeneralTask.numberOfApprovedApplicants.getAttribute("value"))
                            .as("# of approved applicants field length is not 2 characters").hasSize(2)
                            .as("# of approved applicants field accepting alphabets + space ").isEqualTo("15");
                    clearAndFillText(createGeneralTask.numberOfApprovedApplicants, "0");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.getNumberOfApprovedApplicantsErrorMsg)).as("# of Approved Applicant Field error message is not displayed").isTrue();
                    break;

                case "Ldss Received Date":
                    validateDateField(createGeneralTask.ldssReceivedDate, "Ldss Received Date");
                    break;

                case "My Workspace Date":
                    validateDateField(createGeneralTask.myWorkSpaceDate, "My Workspace Date");
                    break;

                case "Application Received Date":
                    validateDateField(createGeneralTask.appReceivedDate, "Application Received Date");
                    break;

                case "Application Signature Date":
                    validateDateField(createGeneralTask.applicatiSignatureDate, "Application Signature Date");
                    break;

                case "Application Update Date":
                    validateDateField(createGeneralTask.applicationUpdateDate, "Application Update Date");
                    break;

                case "Vcl Due Date":
                    validateDateField(createGeneralTask.vclDueDate, "Vcl Due Date");
                    break;

                case "Vcl Sent Date":
                    validateDateField(createGeneralTask.vclSentDate, "Vcl Sent Date");
                    break;

                case "Mi Received Date":
                    validateDateField(createGeneralTask.miReceivedDate, "Mi Received Date");
                    break;

                case "Expedited":
                    validateCheckboxField(myTask.expeditedCheckbox, "Expedited");
                    break;

                case "Hpe":
                    validateCheckboxField(myTask.hpeCheckBox, "HPE");
                    break;

                case "Closed Renewal":
                    validateCheckboxField(myTask.closedRenewalCheckBox, "CLOSED RENEWAL");
                    break;

                case "Other":
                    clearAndFillText(createGeneralTask.other, "1234Test 56Test 789 987&*()^%$ Test!@$#%^8976 test 1234Test 56Test 789 987&*()^%$ Test!@$#%^897 test86876fhgfhfh#$#^##^##");
                    sa.get().assertThat(createGeneralTask.other.getAttribute("value"))
                            .as("Other field length is not 100 characters").hasSize(100)
                            .as("Other field not accepting alphabets + number + space + special character").isEqualToIgnoringCase("1234Test 56Test 789 987&*()^%$ Test!@$#%^8976 test 1234Test 56Test 789 987&*()^%$ Test!@$#%^897 test");
                    break;

                case "Disenrollment Date":
                    validateDateField(createGeneralTask.disenrollmentDate, "Disenrollment Date");
                    break;

                case "Requested Date":
                    validateDateField(createGeneralTask.requestedDate, "Requested Date");
                    break;

                case "Plan Effective Date":
                    validateDateField(createGeneralTask.planEffectiveDate, "Plan Effective Date");
                    break;

                case "Plan Start Date":
                    validateDateField(createGeneralTask.planStartDate, "Plan Start Date");
                    break;

                case "Date Of Birth":
                    validateDateField(createGeneralTask.dateOfBirth, "Date Of Birth");
                    break;

                case "CONTACT NAME":
                    createGeneralTask.contactName.sendKeys(getRandomString(110));
                    sa.get().assertThat(createGeneralTask.contactName.getAttribute("value")).as("CONTACT NAME field length is not 100 characters").hasSize(100);
                    clearAndFillText(createGeneralTask.contactName, "Vidya @1234");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.contactNameErrorMsg)).as("Contact Name field error message is not displayed").isTrue();
                    break;

                case "Contact Phone":
                    validatePhoneField(createGeneralTask.contactPhone, "Contact Phone");
                    break;

                case "Time Of VoiceMail":
                    validateTimeField(createGeneralTask.timeOfVoicemail, "Time Of VoiceMail");
                    break;

                case "Date Of VoiceMail":
                    validateDateField(createGeneralTask.dateOfVoicemail, "Date Of VoiceMail");
                    break;
                case "AddressLine1":
                    clearAndFillText(createGeneralTask.arAddressLine1TxtBox, getRandomString(50));
                    createGeneralTask.btnSave.click();
                    sa.get().assertThat(createGeneralTask.addressLn1.getText()).as("addressLine1 field value is wrong")
                            .isEqualTo("ADDRESS LINE 1 must contain both numeric and alphabetic characters to be valid");

                    clearAndFillText(createGeneralTask.arAddressLine1TxtBox, randomAlphanumeric(55));
                    taskValues.get().put("AddressLine1", createGeneralTask.arAddressLine1TxtBox.getAttribute("value"));
                    sa.get().assertThat(createGeneralTask.arAddressLine1TxtBox.getAttribute("value").length()).as("addressLine1 field value is wrong").isEqualTo(50);
                    break;
                case "AddressLine2":
                    clearAndFillText(createGeneralTask.arAddressLine2TxtBox, randomAlphanumeric(55));
                    taskValues.get().put("AddressLine2", createGeneralTask.arAddressLine2TxtBox.getAttribute("value"));
                    sa.get().assertThat(createGeneralTask.arAddressLine2TxtBox.getAttribute("value").length()).as("addressLine1 field value is wrong").isEqualTo(50);
                    break;
                case "City":
                    clearAndFillText(createGeneralTask.arCityTxtBox, randomAlphanumeric(55));
                    taskValues.get().put("City", createGeneralTask.arCityTxtBox.getAttribute("value"));
                    sa.get().assertThat(createGeneralTask.arCityTxtBox.getAttribute("value").length()).as("addressLine1 field value is wrong").isEqualTo(50);
                    break;
                case "State":
                    clearAndFillText(createGeneralTask.arStateTxtBox, "California");
                    taskValues.get().put("State", createGeneralTask.arStateTxtBox.getAttribute("value"));
                    sa.get().assertThat(createGeneralTask.arStateTxtBox.getAttribute("value")).as("addressLine1 field value is wrong").
                            isEqualTo("California");
                    break;
                case "Contact Email":
                    validateEmailField(createGeneralTask.contactEmail, "Contact Email", createGeneralTask.contactEmailError);
                    break;
                case "Invalid":
                    validateCheckboxField(myTask.invalidCheckBox, "Invalid");
                    break;
                case "Explanation":
                    createGeneralTask.explanation.get(0).sendKeys(getRandomString(200));
                    sa.get().assertThat(createGeneralTask.explanation.get(0).getAttribute("value")).as("Explanation field length is not 200 characters").hasSize(200);
                    clearAndFillText(createGeneralTask.explanation.get(0), getRandomString(220));
                    sa.get().assertThat(createGeneralTask.explanation.get(0).getAttribute("value")).as("Explanation field length is not 200 characters").hasSize(200);
                    break;
                case "Date Health Plan Contacted":
                    validateDateField(createGeneralTask.dateHealthPlanContacted, "Date Health Plan Contacted");
                    break;
                case "Date Received Grievance":
                    validateDateField(createGeneralTask.dateReceivedGrievance, "Date Received Grievance");
                    break;
                case "Date Follow-up Email Sent":
                    validateDateField(createGeneralTask.dateFollowUpEmailSent, "Date Follow-up Email Sent");
                    break;
                case "Date State Notified":
                    validateDateField(createGeneralTask.dateSateNotified, "Date State Notified");
                    break;
                case "New Plan Start Date":
                    validateDateField(createGeneralTask.newPlanStartDate, "New Plan Start Date");
                    break;
                case "Date Decision Letter Sent":
                    validateDateField(createGeneralTask.dateDecisionLetterSent, "Date Decision Letter Sent");
                    break;
                case "Rid #":
                    clearAndFillText(createGeneralTask.rid, "1234");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.ridErrorMessageForLength)).as("Error for rid field length is not displayed").isTrue();
                    clearAndFillText(createGeneralTask.rid, "1234Test $$Test 789 0998");
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.ridErrorMessage)).as("Error for rid field is not displayed").isTrue();
                    sa.get().assertThat(createGeneralTask.rid.getAttribute("value"))
                            .as("Rid # field length is not 12 characters").hasSize(12);
                    clearAndFillText(createGeneralTask.rid, randomNumeric(12));
                    jsClick(myTask.saveBtn);
                    sa.get().assertThat(isElementDisplayed(createGeneralTask.ridErrorMessage)).as("Error for rid field is displayed").isFalse();
                    break;
                case "Grievance #":
                    clearAndFillText(createGeneralTask.grievance, "56Tast 7&$()");
                    sa.get().assertThat(createGeneralTask.grievance.getAttribute("value"))
                            .as("Grievance # field length is not 10 characters").hasSize(10)
                            .as("Grievance # field is not accepting alphabets + space + special").isEqualTo("56Tast 7&$");
                    break;
                case "END DATE":
                    validateDateField(createGeneralTask.endDate, "END Date");
                    break;
                case "Reason":
                    clearAndFillText(createGeneralTask.reasonField, getRandomString(510));
                    sa.get().assertThat(createGeneralTask.reasonField.getAttribute("value"))
                            .as("Reason field length is not 500 characters").hasSize(500);
                    break;

                case "Start Date":
                    validateDateField(createGeneralTask.startDate, "Start Date");
                    break;

                case "Incident Date":
                    validateDateField(createGeneralTask.incidentDate, "Incident Date");
                    break;

                case "Escalated":
                    validateCheckboxField(myTask.escalatedVlu, "Escalated");
                    break;

                case "Member NAME":
                    createGeneralTask.name.sendKeys(getRandomString(100));
                    sa.get().assertThat(createGeneralTask.name.getAttribute("value")).as("Name field length is not 60 characters").hasSize(100);
                    clearAndFillText(createGeneralTask.name, getRandomString(120));
                    sa.get().assertThat(createGeneralTask.name.getAttribute("value")).as("Name field length is not 60 characters").hasSize(100);
                    break;

                case "CALLER NAME":
                    clearAndFillText(createGeneralTask.callerName, getRandomString(120));
                    sa.get().assertThat(createGeneralTask.callerName.getAttribute("value")).as("Name field length is not 100 characters").hasSize(100);
                    break;

                default:
                    sa.get().fail("Switch case is not match for key " + orderOfFields.get().get(i));
                    break;
            }
        }
    }

    @Then("I verify minimum lenght error message {string}")
    public void verifyMinLengthErrorMessage(String expectedMessage) {
        expectedMessage = expectedMessage + " must be at least 4 characters";
        sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath
                ("//p[text()='" + expectedMessage + "']")))).as(expectedMessage + " Mandatory Field level error message is not displayed").isTrue();
    }

    @Then("I verify fields error message {string} for accepting number")
    public void verifyFieldLevelErrorMessage(String expectedMessage) {
        expectedMessage = expectedMessage + " cannot have numbers";
        sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//p[text()='" + expectedMessage + "']")))).
                as(expectedMessage + " Field level error message is not displayed").isTrue();
    }

    @Then("I verify fields order in Correspondence create task page")
    public void verifyFieldsAppearsOnTheCreateTaskScreen() {
        verifyFieldsInCreateTaskPage();
        for (int i = 0; i < APITaskManagementController.fieldOrder.get().size(); i++) {
            assertEquals(APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase(),
                    createGeneralTask.correspondenceAdditionalFlds.get(i).getText().toLowerCase(),
                    "Field order mismatch");
        }
    }

    @And("Verify Email error with wrong input")
    public void verifyEmailErrorWithWrongInput() {
        createGeneralTask.fromEmail.sendKeys(getRandomString(8));
        waitFor(1);
        createGeneralTask.btnSave.click();
        waitFor(1);
        assertTrue(createGeneralTask.emailError.isDisplayed(),
                "FROM EMAIL field level error message is not displaying");
    }

    @Then("Verify fields listed in alphabetical order")
    public void verifyFieldsListedInAlphabeticalOrder() {
        Assert.assertTrue(ascendingOrderTexts(createGeneralTask.listofTaskTemplateFields), "Task Template fields are not is ascending order");
    }

    @And("Verify field values on the Create Task screen")
    public void verifyFieldValuesOnTheCreateTaskScreen() {
        clearAndFillText(createGeneralTask.outboundCorrespondenceId, "7845665874abcd");
        assertEquals(createGeneralTask.outboundCorrespondenceId.getAttribute("value"), "7845665874", "outboundCorrespondenceId field accepting numbers");
        createGeneralTask.inboundCorrespondenceId.sendKeys("7845665874abcd");
        assertEquals(createGeneralTask.inboundCorrespondenceId.getAttribute("value"), "7845665874", "inboundCorrespondenceId field value is wrong");
        createGeneralTask.fromPhone.sendKeys("7845665874abcd");
        assertEquals(createGeneralTask.fromPhone.getAttribute("value"), "784-566-5874", "fromPhone field value is wrong");
        createGeneralTask.fromName.sendKeys("sdasdas5454");
        createGeneralTask.notificationId.sendKeys("123456789011121314");
        assertEquals(createGeneralTask.notificationId.getAttribute("value"), "1234567890", "NotificationId field are failed");
    }

    @Then("Verify OUTBOUND CORRESPONDANCE TYPE dropdown values in Create Task screen")
    public void verifyOUTBOUNDCORRESPONDANCETYPEDropdownValuesInCreateTaskScreen() {
        List<String> dropvalues = new ArrayList<>();
        List<String> apiCorresList = new ArrayList<>();
        for (int i = 0; i < API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.listCorrespondence[*].correspondenceName").size(); i++) {
            apiCorresList.add(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.listCorrespondence[*].correspondenceName").get(i).asText().trim());
        }
        waitFor(1);
        waitForVisibility(createGeneralTask.outboundCorrespondenceType, 5);
        createGeneralTask.outboundCorrespondenceType.click();
        waitForVisibility(createGeneralTask.outboundCorresTypeDrDw.get(0), 5);
        for (int i = 1; i < createGeneralTask.outboundCorresTypeDrDw.size(); i++) {
            dropvalues.add(createGeneralTask.outboundCorresTypeDrDw.get(i).getText().trim());
        }

        assertEquals(dropvalues, apiCorresList, "OUTBOUND CORRESPONDANCE TYPE drop down values are incorrect");
    }

    @Then("Verify Invalid Address Reason dropdown")
    public void verifyInvalidAddressReasonDropdown(List<String> addRes) {
        for (String res : addRes) {
            selectDropDown(createGeneralTask.invalidAddressReason, res);
        }
    }

    @Then("Verify Returned Mail Reason dropdown")
    public void verifyReturnedMailReasonDropdown(List<String> mailRes) {
        for (String res : mailRes) {
            selectDropDown(createGeneralTask.returnedMailReason, res);
        }
    }

    @And("Verify FROM NAME field input")
    public void verifyFROMNAMEFieldInput() {
        clearFiled(createGeneralTask.fromEmail);
        String moreFifty = getRandomString(60);
        clearAndFillText(createGeneralTask.fromName, moreFifty);
        assertTrue(createGeneralTask.fromName.getAttribute("value").length() <= 50, "From Name field are failed");
        String frName = getRandomString(47) + "785";
        clearAndFillText(createGeneralTask.fromName, frName);
        waitFor(1);
        createGeneralTask.btnSave.click();
        assertTrue(createGeneralTask.fromNameError.isDisplayed(), "From Name field level error message is not displaying");
    }

    @And("I verify Name drop down fields value")
    public void nameAutoCompleteDropDownValues(List expectedValues) {
        waitFor(1);
        if (isElementDisplayed(myTask.remove))
            myTask.remove.click();
        waitFor(2);
        scrollDown();
        waitFor(2);
        scrollUpRobot();
        waitFor(2);
        scrollDownUsingActions(2);
        waitFor(2);
        scrollUpUsingActions(1);
        waitFor(2);

        List<String> actualValues = new LinkedList<String>();
        createGeneralTask.name.click();
        waitFor(5);
        for (int i = 0; i < createGeneralTask.nameDrDn.size(); i++) {
            actualValues.add(createGeneralTask.nameDrDn.get(i).getText());
        }
        assertEquals(actualValues, expectedValues, "Name drop down values are incorrect");
    }

    @Then("I verify Complaint About drop down value")
    public void verifyComplaintAboutDropDownValues(List expectedValues) {
        waitFor(1);
        waitForVisibility(createGeneralTask.complaintAboutDrDn, 5);
        createGeneralTask.complaintAboutDrDn.click();
        List<String> actualValues = new LinkedList<>();
        waitForVisibility(createGeneralTask.complaintAboutDrDnValues.get(1), 5);
        for (int i = 1; i < createGeneralTask.complaintAboutDrDnValues.size(); i++) {
            actualValues.add(createGeneralTask.complaintAboutDrDnValues.get(i).getText());
        }
        assertEquals(actualValues, expectedValues, "Complaint About drop down values are incorrect");
        actions.moveToElement(createGeneralTask.name).click().perform();
    }

    @Then("I verify external application id accepts {int} alphanumeric spaces are allowed")
    public void externalApplicationIdFieldValidation(Integer int1) {
        String text = getRandomString(28) + "99";
        createGeneralTask.externalApplicationId.sendKeys(text);
        assertTrue(createGeneralTask.externalApplicationId.getAttribute("value").length() == int1, "external application id field length is not 30 characters");
        text = getRandomString(30) + "99";
        clearAndFillText(createGeneralTask.externalApplicationId, text);
        assertTrue(createGeneralTask.externalApplicationId.getAttribute("value").length() == int1, "external application id field length is not 30 characters");
    }

    @Then("I verify external case id accepts {int} alphanumeric spaces are allowed")
    public void externalCaseIdFieldValidation(Integer int1) {
        String text = getRandomString(28) + "99";
        createGeneralTask.externalCaseId.sendKeys(text);
        assertTrue(createGeneralTask.externalCaseId.getAttribute("value").length() == int1, "external case id field length is not 30 characters");
        text = getRandomString(30) + "99";
        clearAndFillText(createGeneralTask.externalCaseId, text);
        assertTrue(createGeneralTask.externalCaseId.getAttribute("value").length() == int1, "external case id field length is not 30 characters");
    }

    @Then("I verify user can enter a Name {string} that does not exist in the consumers on the case or consumer profile")
    public void VerifyUserCanEnterTextInTheNameField(String name) {
        waitForVisibility(createGeneralTask.name, 5);
        clearAndFillText(createGeneralTask.name, name);
        assertTrue(createGeneralTask.name.getAttribute("value").equals(name), "User not able to enter text in name field");
    }

    @Then("I verify Reason drop down value")
    public void verifyReasonDropDownValues(List expectedValues) {
        waitFor(3);
        waitForVisibility(createGeneralTask.reasonDrDn, 5);
        createGeneralTask.reasonDrDn.click();
        List<String> actualValues = new LinkedList<String>();
        waitForVisibility(createGeneralTask.reasonDrDnValues.get(1), 5);
        for (int i = 1; i < createGeneralTask.reasonDrDnValues.size(); i++) {
            actualValues.add(createGeneralTask.reasonDrDnValues.get(i).getText());
        }
        List expectedValuess = new LinkedList(expectedValues);
        assertEquals(actualValues, expectedValuess, "Reason down values are incorrect");
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
    }

    @Then("I verify Success message is displayed")
    public void i_verify_Success_message_is_displayed() {
        assertTrue(isElementDisplayed(createGeneralTask.lblSuccessMessage));
    }

    @Then("I verify create task field values are changed")
    public void verifyFieldValuesAreChangedInCreateTask() {
        waitFor(2);
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblCaseId)).as("Case Id is not displayed on create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblConsumerId)).as("Consumer Id is not displayed on create task page").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblDueDate)).as("Due Date is not displayed on create task page").isTrue();
        sa.get().assertThat(createGeneralTask.lblDueIn.getText()).as("Due In value is wrong in create task page").isEqualTo("6 Business Days");
        sa.get().assertThat(createGeneralTask.lblSource.getText()).as("Source value is wrong in create task page").isEqualTo("User");
        sa.get().assertThat(createGeneralTask.lblStatusDate.getText()).as("Status Date value is wrong in create task page").isEqualTo(createGeneralTask.projectDateAtHeader.getText());
        sa.get().assertThat(createGeneralTask.lstTaskType.getText()).as("Task Type value is wrong in create task page").isEqualTo("Inbound Task");
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lstAssignee)).as("Assignee is not displayed on create task page").isTrue();
        sa.get().assertThat(createGeneralTask.lstTaskPriority.getText()).as("Priority value is wrong in create task page").isEqualTo("1");
        sa.get().assertThat(isElementDisplayed(createGeneralTask.txtTaskInfo)).as("Task Info is not displayed on create task page").isTrue();
        sa.get().assertThat(createGeneralTask.txtTaskStatus.getText()).as("Status value is wrong in create task page").isEqualTo("Created");
    }

    @Then("I verify due date field in create task page for {int} {string}")
    public void i_verify_due_date_value_in_create_task_page(int days, String taskDay) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (taskDay.equals("Business Day")) {
            sa.get().assertThat(createGeneralTask.lblDueDate.getText()).as("Due date value not matches").isEqualTo(addDaysSkippingWeekends(LocalDate.now(), days).format(formatter));
        } else if (taskDay.equals("Calendar Day")) {
            LocalDate result = LocalDate.now();
            result = result.plusDays(days);
            sa.get().assertThat(createGeneralTask.lblDueDate.getText()).as("Due date value not matches").isEqualTo(result.format(formatter));
        }
    }

    public LocalDate addDaysSkippingWeekends(LocalDate date, int days) throws Exception {
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().i_initiated_create_business_unit_api(projectName1.get(), "2022");
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().getHolidayList();
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().i_initiated_create_business_unit_api(projectName1.get());
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().getTimeFrameConfiguredForProject();

        LocalDate result = date;
        int addedDays = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (addedDays < days) {
            result = result.plusDays(1);
            if (APITMBusinessDayRestController.businessDay.get().contains(result.getDayOfWeek().toString())) {
                if (!APITMHolidayController.holiday.contains(result.format(formatter))) {
                    addedDays++;
                }
            }
        }
        return result;
    }

    @Then("I verify unlink button is not displayed")
    public void verifyUnLinkCaseConsumerButtonIsNotDisplayed() {
        waitForVisibility(myTask.saveBtn, 10);
        assertFalse(isElementDisplayed(createGeneralTask.unlinkCaseOrConsumer),
                "unlink case/consumer button is displayed");
    }

    @Then("I verify unlink button is displayed")
    public void verifyUnLinkCaseConsumerButtonIsDisplayed() {
        waitForVisibility(myTask.saveBtn, 10);
        assertTrue(isElementDisplayed(createGeneralTask.unlinkCaseOrConsumer),
                "unlink case/consumer button is not displayed");
    }

    @Then("I click on unlink button")
    public void clickOnUnlinkButton() {
        click(createGeneralTask.unlinkCaseOrConsumer);
    }

    @Then("I will check weather case or consumer is unlinked from task")
    public void verifyCaseConsumerIsUnlinked() {
        assertTrue(createGeneralTask.link.isDisplayed(), "Link text not showing");
        i_click_on_link_contactlink_button_under_link_section();
        assertTrue(createGeneralTask.caseConsumerBtn.isDisplayed(), "Link text not showing");
        scrollUpUsingActions(2);
    }

    @Then("I verify in view page link component is empty")
    public void verifyLinkComponentIsEmptyInViewPage() {
        assertEquals(createGeneralTask.linkrowsize.size(), 0, "Link Component is not empty");
    }

    @Then("I verify task is linked with Inbound ID")
    public void verify_linked_inbound_value() {
        scrollDown();
        waitFor(2);
        assertEquals(createGeneralTask.linkedInboundInfo.get(0).getText(), taskValues.get().get("inboundId"),
                "Inbound Id Value is missing");
        assertEquals(createGeneralTask.linkedInboundInfo.get(1).getText(), "Inbound Correspondence",
                "Name Value is missing");
        assertEquals(createGeneralTask.linkedInboundInfo.get(2).getText(), "maersk Other",
                "Type Value is not showing null");
        assertEquals(createGeneralTask.linkedInboundInfo.get(3).getText(), "04/26/2021",
                "Status Date Value is not showing blank");
        assertEquals(createGeneralTask.linkedInboundInfo.get(4).getText(), "DOCUMENT SEPARATION",
                "Status Value is not showing null");
    }

    @Then("I verify only active task type is displayed in create task link")
    public void verifyOnlyActiveTaskTypeIsDisplayed() {
        waitForVisibility(dashBoard.btnMenuList, 20);
        click(dashBoard.btnMenuList);
        waitForVisibility(dashBoard.createTaskMenu, 10);
        dashBoard.createTaskMenu.click();
        waitForVisibility(dashBoard.subMenuList, 10);
        synchronized (displayedInOrder){
            displayedInOrder.set(new ArrayList<>());
        }
        for (int i = 0; i < createGeneralTask.taskTypeList.size(); i++) {
            displayedInOrder.get().add(createGeneralTask.taskTypeList.get(i).getText().toLowerCase().trim());
        }
        Collections.sort(APITaskManagementController.allFieldsValue.get(), String.CASE_INSENSITIVE_ORDER);
        assertEquals(displayedInOrder.get(), APITaskManagementController.allFieldsValue.get(),
                "Task Types are not Active or Task types are not listed in alphabetical order");
        click(createGeneralTask.taskTypeList.get(1));
    }

    @Then("I verify only active task type is displayed in task type drop down in create task page")
    public void verifyOnlyActiveTaskTypeIsDisplayedInTaskTypeDropDown() {
        waitForVisibility(createGeneralTask.lstTaskType, 12);
        createGeneralTask.lstTaskType.click();
        synchronized (displayedInOrder){
            displayedInOrder.set(new ArrayList<>());
        }
        for (int i = 1; i < createGeneralTask.taskTypeListValues.size(); i++) {
            displayedInOrder.get().add(createGeneralTask.taskTypeListValues.get(i).getText().toLowerCase().trim());
        }
        Collections.sort(APITaskManagementController.allFieldsValue.get(), String.CASE_INSENSITIVE_ORDER);
        assertEquals(displayedInOrder, APITaskManagementController.allFieldsValue.get(),
                "Task Types are not Active or Task types are not listed in alphabetical order");
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().build().perform();
    }

    @Then("I verify Success message is displayed for task update")
    public void i_verify_Success_message_is_displayed_for_task_update() {
        waitForVisibility(createGeneralTask.lblSuccessMessage, 10);
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblSuccessMessage)).isTrue();
        sa.get().assertThat(isElementDisplayed(myTask.taskUpdatedTxt)).as("Success message text is not displayed").isTrue();
    }

    @Then("I verify the optional fields in Review Appeal Form create task page")
    public void verifyAdditionalFieldsInReviewAppealFormCreateTaskPage() {
        verifyFieldsInCreateTaskPage();
        assertEquals(createGeneralTask.reviewAppealAdditionalFlds.size(), 2,
                "There are more optional fields in create task page");
        assertTrue(createGeneralTask.reviewAppealAdditionalFlds.get(0).getText().contains("ACTION TAKEN"),
                "Field name is not equal to ACTION TAKEN");
        assertTrue(createGeneralTask.reviewAppealAdditionalFlds.get(1).getText().contains("DISPOSITION"),
                "Field name is not equal to DISPOSITION");
    }

    @Then("I verify Action Taken drop down value")
    public void verifyCActionTakenDropDownValues(List expectedValues) {
        waitFor(1);
        waitForVisibility(createGeneralTask.actionTaken, 5);
        createGeneralTask.actionTaken.click();
        List<String> actualValues = new LinkedList<String>();
        waitForVisibility(createGeneralTask.actionTakenDrDnValues.get(0), 5);
        for (int i = 0; i < createGeneralTask.actionTakenDrDnValues.size(); i++) {
            actualValues.add(createGeneralTask.actionTakenDrDnValues.get(i).getText());
        }
        assertEquals(actualValues, expectedValues, "Action Taken drop down values are incorrect");
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
    }

    @Then("I will check action taken is not mandatory when task status is not complete")
    public void verifyFieldIsNotMandatory() {
        sa.get().assertThat(isElementDisplayed(createGeneralTask.atMandatoryErrMsg)).as("Mandatory Field level error message is displayed").isFalse();
        sa.get().assertThat(isElementDisplayed(myTask.actionTakenMandatory)).as("* mark is displayed on action taken field").isFalse();
    }

    @Then("I verify field level error message for address line 1")
    public void verifyAddressLine1ErrorMessage(List expectedValues) {
        assertTrue(isElementDisplayed(createGeneralTask.addressLine1ErrMsg.get(0)),
                "Address Line 1 error message is not displaying");
        assertEquals(createGeneralTask.addressLine1ErrMsg.size(), expectedValues.size(),
                "Error message is not displayed for all Address Line 1 fields");
    }

    @Then("I verify Future or InActive task types are not displayed in create task link")
    public void verifyFutureOrInactiveTaskTypeIsNotDisplayed() {
        waitForVisibility(dashBoard.btnMenuList, 20);
        click(dashBoard.btnMenuList);
        waitForVisibility(dashBoard.createTaskMenu, 10);
        dashBoard.createTaskMenu.click();
        waitForVisibility(dashBoard.subMenuList, 10);
        for (int i = 0; i < createGeneralTask.taskTypeList.size(); i++) {
            assertFalse(APITaskManagementController.allFieldsValue.get().
                            contains(createGeneralTask.taskTypeList.get(i).getText().toLowerCase().trim()),
                    "Future or InActive task types are present in create task 3 dot link");
        }
        click(createGeneralTask.taskTypeList.get(1));
    }

    @Then("I verify Future or InActive task types are is displayed in task type drop down in create task page")
    public void verifyFutureOrInActiveTaskTypeIsNotDisplayedInTaskTypeDropDown() {
        waitForVisibility(createGeneralTask.lstTaskType, 10);
        createGeneralTask.lstTaskType.click();
        for (int i = 1; i < createGeneralTask.taskTypeListValues.size(); i++) {
            assertFalse(APITaskManagementController.allFieldsValue.get().
                            contains(createGeneralTask.taskTypeListValues.get(i).getText().toLowerCase().trim()),
                    "Future or InActive task types are present in task type drop down");
        }
    }

    @Then("I will get project time zone date")
    public void iWillGetProjectTimeZoneDate() {
        waitForVisibility(createGeneralTask.projectDateAtHeader, 10);
        synchronized (projectDate){projectDate.set(createGeneralTask.projectDateAtHeader.getText());}
    }

    @Then("I save Link changes in Task")
    public void iSaveLinkChangesInTask() {
        selectDropDown(createGeneralTask.reasonForEditDrpDn, "Corrected Case/Consumer Link");
        waitFor(3);
        createGeneralTask.btnSave.click();
    }

    @Then("I verify fields order in All Fields create task page")
    public void verifyAllAdditionalFieldsInCreateTaskPage() {
        verifyFieldsInCreateTaskPage();
        for (int i = 0; i < APITaskManagementController.fieldOrder.get().size(); i++) {
            if (createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase().equals("provider first name") &&
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("provider last name")) {
                assertEquals(createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase(),
                        APITaskManagementController.fieldOrder.get().get(i + 2).toLowerCase(), "Field order mismatch");
            } else if (createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase().equals("provider last name") &&
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("provider first name")) {
                assertEquals(createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase(),
                        APITaskManagementController.fieldOrder.get().get(i).toLowerCase(), "Field order mismatch");
            } else if (createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase().equals("case worker first name") &&
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("case worker last name")) {
                assertEquals(createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase(),
                        APITaskManagementController.fieldOrder.get().get(i + 2).toLowerCase(), "Field order mismatch");
            } else if (createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase().equals("case worker last name") &&
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("case worker first name")) {
                assertEquals(createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase(),
                        APITaskManagementController.fieldOrder.get().get(i).toLowerCase(), "Field order mismatch");
            } else if (createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase().contains("address")) {
                assertTrue(APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().contains(
                        createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase()),
                        "Field order mismatch");
            } else {
                assertTrue(createGeneralTask.planProviderAdditionalFlds.get(i).getText().toLowerCase().
                                contains(APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase()),
                        "Field order mismatch");
            }
            if (APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().contains("provider address")) {
                i += 5;
            } else if (APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().contains("new address") ||
                    APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().contains("old address")) {
                i += 4;
            }
        }
    }

    @Then("I verify {string} link section has all the business object linked")
    public void verify_ConsumerID_TaskID_ContactID_linkComponent(String srType) {
        waitFor(2);
        scrollDownUsingActions(5);
        boolean task = false, consumer = false, contact = false, inb_correspondence = false, sr = false, linkcase = false, linkapplication = false;
        for (int i = 0; i < createGeneralTask.linkrowsize.size(); i++) {
            String linkType = null;
            if (createGeneralTask.linkrowsize.get(i).getText().equalsIgnoreCase(taskValues.get().get("consumerName")) || createGeneralTask.linkType.get(i).getText().equalsIgnoreCase("Consumer")) {
                linkType = createGeneralTask.consumerInfo.get(2).getText();
            } else {
                linkType = createGeneralTask.linkrowsize.get(i).getText();
            }
            switch (linkType) {
                case "Contact Record":
                    verify_linked_contact_value();
                    contact = true;
                    System.out.println("Contact record link:" + contact);
                    break;
                case "Task":
                    waitFor(4);
                    verify_linked_task_value(srType, i);
                    task = true;
                    break;
                case "Service Request":
                    verify_linked_sr_value(srType);
                    sr = true;
                    break;
                case "Consumer":
                    verify_linked_consumer_value();
                    consumer = true;
                    System.out.println("Consumer link:" + consumer);
                    break;
                case "Inbound Correspondence":
                    verify_inbound_corespondence(srType);
                    inb_correspondence = true;
                    break;
                case "Case":
                    verify_linked_case_value();
                    linkcase = true;
                    System.out.println("Case link:" + linkcase);
                    break;
                case "Application":
                    verify_task_is_linked_to_application();
                    linkapplication = true;
                    System.out.println("Application link: " + linkapplication);
                    break;
            }
        }
        System.out.println(sr + " " + inb_correspondence + " " + consumer);
        if (createGeneralTask.linkrowsize.size() == 2)
            assertTrue((task && consumer) || (task && contact) || (task && inb_correspondence) || (sr && inb_correspondence) || (sr && consumer), "Link section is not displayed consumer/task/contact/inb correspondence/SR information");
        else
            assertTrue((task && consumer && contact) || (sr && inb_correspondence && consumer) || (task && consumer && linkcase) || (linkapplication), "Link section is not displayed consumer/task/contact record information");
    }

    @Then("I verify link section has {string} and other business object")
    public void verify_ConsumerID_SRId_TaskID_linkComponent(String srType) {
        waitFor(2);
        scrollDownUsingActions(5);
        scrollDown();
        boolean sr = false, consumer = false, task = false;

        for (int i = 0; i < createGeneralTask.contactLinkrowsize.size(); i++) {
            String linkType = createGeneralTask.contactLinkrowsize.get(i).getText();
            switch (linkType) {
                case "Service Request":
                    assertEquals(createGeneralTask.taskLinkInfo.get(0).getText(), CRM_TaskManagementStepDef.taskId.get(), "TaskId Value is missing");
                    assertEquals(createGeneralTask.taskLinkInfo.get(1).getText(), "Service Request", "Name Value is missing");
                    assertEquals(createGeneralTask.taskLinkInfo.get(2).getText(), taskValues.get().get("taskType"), "Type Value is not correct");
                    assertEquals(createGeneralTask.taskLinkInfo.get(3).getText(), taskValues.get().get("startDate"), "Status Date Value is not correct");
                    assertEquals(createGeneralTask.taskLinkInfo.get(4).getText(), "Open", "Status Value is not Created");
                    sr = true;
                    break;
                case "Task":
                    assertEquals(createGeneralTask.taskLinkInfo.get(5).getText(),
                            (Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) + 1) + "", "TaskId Value is missing");
                    assertEquals(createGeneralTask.taskLinkInfo.get(6).getText(), "Task", "Name Value is missing");
                    if (srType.equalsIgnoreCase("Application SR") || srType.equalsIgnoreCase("Application Renewal SR")|| srType.equalsIgnoreCase("Renewal SR")) {
                        assertEquals(createGeneralTask.taskLinkInfo.get(7).getText(), "Process App V1", "Type Value is not correct");
                    } else if (srType.equalsIgnoreCase("Appeals SR")) {
                        assertEquals(createGeneralTask.taskLinkInfo.get(2).getText(), "Review Appeal", "Type Value is not correct for Appeals SR");
                    } else if (srType.equalsIgnoreCase("Complaint SR")) {
                        assertEquals(createGeneralTask.taskLinkInfo.get(7).getText(), "Review Complaint", "Type Value is not correct for Complaint SR");
                    }
                    assertEquals(createGeneralTask.taskLinkInfo.get(8).getText(), taskValues.get().get("startDate"),
                            "Status Date Value is not correct");
                    assertEquals(createGeneralTask.taskLinkInfo.get(9).getText(), "Created",
                            "Status Value is not Created");
                    task = true;
                    break;
                // refactoring on 30-08-2021 by priyal
                case "Consumer Profile":
                    scrollToElement(createGeneralTask.consumerInfo.get(0));
                    assertEquals(createGeneralTask.consumerInfo.get(0).getText(), taskValues.get().get("consumerID"), "ConsumerID Value is missing");
                    assertEquals(createGeneralTask.consumerInfo.get(1).getText(), "Consumer Profile", "Name Value is missing");
                    assertEquals(createGeneralTask.consumerInfo.get(2).getText(), "Consumer", "Type Value is missing");
                    assertTrue(verifyDateFormat(createGeneralTask.consumerInfo.get(3)), "Consumer Status date is wrong");
                    assertFalse(createGeneralTask.consumerInfo.get(4).getText().isEmpty(), "Status Value is missing");
                    consumer = true;
                    break;
            }
        }
        if (createGeneralTask.linkrowsize.size() == 2) {
            assertTrue(task && sr, "Link section is not displayed consumer/task information");
        } else if (createGeneralTask.linkrowsize.size() == 1) {
            assertTrue(sr || task);
        } else {
            assertTrue(sr && consumer && task,
                "Link section is not displayed consumer/task/Sr information");
    }
}

    @Then("I verify {string} link section has all the business object linked : {string}")
    public void validateLinkedObjectsInLinkSection(String srType, String expectedObjects) {
        waitFor(5);
        scrollToElement(createGeneralTask.LinksText,false);
        int taskCount = 0;
        List<String> expectedLinksObjects = Arrays.stream(expectedObjects.split(",")).sorted().collect(Collectors.toList());
        List<String> actualLinkObjects = new ArrayList<>(Collections.emptyList());
        for (int i = 0; i < createGeneralTask.linkrowsize.size(); i++) {
            String linkType;
            if (createGeneralTask.linkrowsize.get(i).getText().equalsIgnoreCase(taskValues.get().get("consumerName")) || createGeneralTask.linkType.get(i).getText().equalsIgnoreCase("Consumer")) {
                linkType = createGeneralTask.consumerInfo.get(2).getText();
                actualLinkObjects.add(createGeneralTask.consumerInfo.get(2).getText());
            } else {
                linkType = createGeneralTask.linkrowsize.get(i).getText();
                actualLinkObjects.add(linkType);
            }
            switch (linkType) {
                case "Contact Record":
                    verify_linked_contact_value();
                    break;
                case "Case":
                    taskValues.get().put("srType", srType);
                    verify_linked_case_value();
                    break;
                case "Task":
                    verify_linked_task_value(srType,taskCount);
                    taskCount++;
                    break;
                case "Service Request":
                    verify_linked_sr_value(srType);
                    break;
                case "Consumer":
                    taskValues.get().put("srType", srType);
                    verify_linked_consumer_value();
                    break;
                case "Inbound Correspondence":
                    verify_inbound_corespondence(srType);
                    break;
                case "Outbound Correspondence":
                    verify_outbound_correspondence(srType);
                    break;
                default:
                    fail("Incorrect switch argument " + linkType);
            }
        }
        assertEquals(actualLinkObjects.stream().sorted().collect(Collectors.toList()), expectedLinksObjects, "Link section contains incorrect objects");
        assertEquals(actualLinkObjects.size(), expectedLinksObjects.size(), "Link section contains incorrect objects");
    }

    @Then("I verify contact record page link section has {string} and other business object: {string}")
    @And("I verify active contact page link section has {string} and other business object: {string}")
    public void iVerifyContactRecordAndActiveContactPageLinkSectionHasAndOtherBusinessObject(String srType, String expectedObjects) {
        waitFor(2);
        scrollDownUsingActions(5);
        List<String> expectedLinksObjects = Arrays.stream(expectedObjects.split(",")).sorted().collect(Collectors.toList());
        ArrayList<String> actualLinkObjects = new ArrayList<>(Collections.emptyList());
        for (int i = 0; i < createGeneralTask.contactLinkNames.size(); i++) {
            String linkType = createGeneralTask.contactLinkNames.get(i).getText();
            actualLinkObjects.add(linkType);
            switch (linkType) {
                case "Service Request":
                    verify_sr_link_value_on_cr_and_active_cr_page();
                    break;
                case "Task":
                    verify_task_value_on_cr_and_active_cr_page(srType);
                    break;
                case "Consumer Profile":
                    verify_consumer_on_cr_and_active_cr_page();
                    break;
                case "Case":
                    verify_case_on_cr_and_active_cr_page();
                    break;
                default:
                    fail("Incorrect switch argument " + linkType);
            }
        }
        assertEquals(actualLinkObjects.stream().sorted().collect(Collectors.toList()), expectedLinksObjects, "Link section contains incorrect objects");
        assertEquals(expectedLinksObjects.size(), actualLinkObjects.size(), "Link section contains incorrect objects");
    }

    @And("I will expand the consumer record")
    public void expandTheConsumerRecord() {
        waitFor(1);
        createGeneralTask.click_searchRecord.get(0).click();
        taskValues.get().put("consumerID", createGeneralTask.consumerIDValue.get(0).getText());
        synchronized (ConsumerID){ConsumerID.set(createGeneralTask.consumerIDValue.get(0).getText());}
        synchronized (externalConsumerID){externalConsumerID.set(createGeneralTask.consumerIDValue.get(0).getText());}
        synchronized (externalCaseID){externalCaseID.set(createGeneralTask.caseIDValue.get(0).getText());}
    }

    @And("I click on srId in active contact page and navigate to view sr details page")
    public void clickOnSRIDInActiveContactPage() {
        waitFor(4);
        CRM_TaskManagementStepDef.taskId.set(createGeneralTask.taskLinkInfo.get(0).getText());
        CRM_TaskManagementStepDef.srID.set(createGeneralTask.taskLinkInfo.get(0).getText());
        click(createGeneralTask.taskLinkInfo.get(0));
        if(isElementDisplayed(myTask.taskDetailsHeader)){
            sa.get().assertThat(isElementDisplayed(editHistory.taskDetailsTab)).as("Header is not displayed").isTrue();
        }else
            sa.get().assertThat(isElementDisplayed(srViewEditPage.srDetailsTab)).as("View SR Details is not displayed").isTrue();
    }

    @Then("I verify {string} multi select drop down value")
    public void verifyCActionTakenDropDownValues(String ddName, List<String> expectedValues) {
        waitFor(1);
        WebElement dropdown =
                Driver.getDriver().findElement(By.xpath("//input[@id='" + ddName + "']/.."));
        hover(dropdown);
        waitFor(2);
        dropdown.click();
        waitFor(2);
        //List<String> actualValues = new LinkedList<String>();
        List<WebElement> dropdownVlu =
                Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + ddName + "')]//ul/li"));
        /*for (int i = 0; i < dropdownVlu.size(); i++) {
            actualValues.add(dropdownVlu.get(i).getText());
        }
        softAssert.assertEquals(actualValues, expectedValues, ddName + " drop down values are incorrect");*/
        sa.get().assertThat(dropdownVlu)
                .as(ddName +" Size does not match").hasSize(expectedValues.size())
                .as(ddName+" has duplicate values").doesNotHaveDuplicates()
                .as(ddName + " drop down values not match").extracting(WebElement::getText).containsExactlyInAnyOrderElementsOf(expectedValues);
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();


    }

    @Then("I verify {string} single select drop down value")
    public void verifySingleSelectDropDownValues(String ddName, List<String> expectedValues) {
        waitFor(3);
        WebElement dropdown =
                Driver.getDriver().findElement(By.xpath("//input[@id='" + ddName + "']/.."));
        hover(dropdown);
        waitFor(2);
        dropdown.click();
        waitFor(3);
        if(ddName.equalsIgnoreCase("task-priority")) {
            ddName = "menu-taskPriority";
        }else if(ddName.equalsIgnoreCase("task-status") && isElementDisplayed(myTask.taskDetailsHeader))
            ddName="menu-taskStatus";
        List<WebElement> dropdownVlu = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + ddName + "')]//ul/li[string-length() > 0]"));
        sa.get().assertThat(dropdownVlu)
                .as(ddName +" Size does not match").hasSize(expectedValues.size())
                .as(ddName+" has duplicate values").doesNotHaveDuplicates()
                .as(ddName + " drop down values not match ").extracting(WebElement::getText).containsExactlyInAnyOrderElementsOf(expectedValues);
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
    }

    @Then("I verify {string} field does not contains value on {string} page")
    public void iVerifyFieldDoesNotContainsValue(String ddName, String page, List<String> expectedValues) {
        waitFor(3);
        WebElement dropdown = Driver.getDriver().findElement(By.xpath("//input[@id='" + ddName + "']/.."));
        hover(dropdown);
        dropdown.click();
        waitFor(2);
        if(ddName.equalsIgnoreCase("task-priority")) {
            ddName = "menu-taskPriority";
        }else if(ddName.equalsIgnoreCase("task-status") && isElementDisplayed(myTask.taskDetailsHeader))
            ddName="menu-taskStatus";
        List<WebElement> dropdownVlu = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + ddName + "')]//ul/li[string-length() > 0]"));
        sa.get().assertThat(dropdownVlu)
                .as(ddName + " drop down values contains unexpected value ").extracting(WebElement::getText).doesNotContainAnyElementsOf(expectedValues);
        if (page.equalsIgnoreCase("task slider")) {
            actions.moveToElement(myTask.txtTaskNotes).click().perform();
        } else actions.moveToElement(createGeneralTask.lstTaskPriority).click().perform();
    }

    @Then("I click id of {string} in Links section")
    public void ClickIDinLinksSection(String type) {
        waitFor(5);
        scrollToElement(createGeneralTask.LinksText,false);
        boolean flag = false;
        waitFor(5);
        for (int i = 0; i < createGeneralTask.linkType.size(); i++) {
            if (createGeneralTask.linkType.get(i).getText().equalsIgnoreCase(type)) {
                waitFor(5);
                createGeneralTask.contactLinkrowsize.get(i).click();
                waitFor(5);
                flag = true;
                if (!type.contains("SR") && !type.equalsIgnoreCase("Appeal")
                        && !type.equalsIgnoreCase("Customer Service Complaint")
                        && !type.equalsIgnoreCase("Just Cause")) {
                    CRM_TaskManagementStepDef.taskId.set(myTask.taskIdColumn.getText().replaceAll("\\D",""));
                    CRM_TaskManagementStepDef.srID.set(createGeneralTask.contactLinkrowsize.get(0).getText());
                } else {
                    CRM_TaskManagementStepDef.srID.set(srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D",""));
                    CRM_TaskManagementStepDef.taskId.set(createGeneralTask.contactLinkrowsize.get(i).getText());
//                    CRM_TaskManagementStepDef.taskId.get() = (Integer.parseInt(createGeneralTask.contactLinkrowsize.get(i).getText())) + 1 + "";
                }
                break;
            }
        }
        waitFor(3);
        assertTrue(flag, "Record not found");
        waitFor(2);
    }

    @And("I click on id of {string} in Links section of {string} page")
    public void ClickIDinLinksSectionOnContactRecordPage(String type, String page) {
        waitFor(5);
        scrollToElement(createGeneralTask.LinksText);
        String xpathForTaskRow = "//td[text()='type']//parent::tr//td";
        List<WebElement> rowByTask = getDynamicWebElements(xpathForTaskRow, "type", type);
        if (page.contains("Contact Record")) {
            rowByTask.get(1).click();
        } else rowByTask.get(2).click();
    }

    @And("I click on task id with status created in the link section")
    public void clickOnIdInLinkSectionBySrOrTaskType() {
        waitForVisibility(createGeneralTask.taskIdByCreatedStatus, 9);
        scrollToElement(createGeneralTask.taskIdByCreatedStatus);
        jsClick(createGeneralTask.taskIdByCreatedStatus);
        CRM_TaskManagementStepDef.srID.set(createGeneralTask.contactLinkrowsize.get(0).getText());
        CRM_TaskManagementStepDef.taskId.set(myTask.taskIdColumn.getText().replaceAll("\\D",""));
        waitFor(2);
    }

    @Then("I verify following {string} type are not present in 3 dot create task or sr link")
    public void verifyTaskRequiredForSRIsNotListedIn3DotLink(String type, List expectedValues) {
        waitForVisibility(dashBoard.btnMenuList, 20);
        click(dashBoard.btnMenuList);
        if(type.equals("task")){
            waitForVisibility(dashBoard.createTaskMenu, 10);
            dashBoard.createTaskMenu.click();
        }
        else{
            waitForVisibility(dashBoard.createSRMenu, 10);
            dashBoard.createSRMenu.click();
        }
        waitForVisibility(dashBoard.subMenuList, 10);
        for (int i = 0; i < createGeneralTask.taskTypeList.size(); i++) {
            assertFalse(expectedValues.contains(createGeneralTask.taskTypeList.get(i).getText().toLowerCase().trim()),"Create task link has unexpected values");
        }
        click(createGeneralTask.taskTypeList.get(1));
    }

    @Then("I verify following task or sr type are not present in task or sr type drop down on create task or sr page")
    public void verifyTaskRequiredForSRIsNotListedInTaskTypeDropDown(List expectedValues) {
        waitForVisibility(createGeneralTask.lstTaskType, 10);
        createGeneralTask.lstTaskType.click();
        for (int i = 1; i < createGeneralTask.taskTypeListValues.size(); i++) {
            assertFalse(expectedValues.contains(createGeneralTask.taskTypeListValues.get(i).getText().toLowerCase().trim()),"Task Type DD has unexpected values");
        }
    }

    @Then("I verify assignee field is disable in coverVa project")
    public void verifyAssigneeFieldIsDisableInCoverVA() {
        waitFor(2);
        sa.get().assertThat(createGeneralTask.lstAssignee.isEnabled()).as("Assignee Field is enabled in coverVA Project for SVC Tester 3 account").isFalse();
    }

    @Then("If any case consumer already linked and unlink that")
    public void clickOnUnlinkButtonIfCaseOrConsumerLinked() {
        if (isElementDisplayed(createGeneralTask.unlinkCaseOrConsumer))
            click(createGeneralTask.unlinkCaseOrConsumer);
    }

    @Then("I should see I am navigated to the Inbound Correspondence Details Page for {string}")
    public void i_should_see_I_am_navigated_to_the_Inbound_Correspondence_Details_Page_for(String srType) {
        waitForVisibility(viewInboundCorrespondenceDetailsUIAPIPage.inboundCorrDetHeader, 500);
        assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.inboundCorrDetHeader.isDisplayed(), "Page header isnt displayed");
        switch (srType) {
            case "CoverVA Appeals":
                assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText(), "VACV Appeal", "Inb corresponce type is not matching");
                break;
            case "CoverVA Complaint":
                assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText(), "VACV Complaint", "Inb corresponce type is not matching");
                break;
            case "NJ Appeal":
                assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText(), "NJ SBE Appeal Form", "Inb corresponce type is not matching");
                break;
            case "CoverVA Application SR":
                assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText(), "VACV Verification Document", "Inb corresponce type is not matching");
                break;
            case "INEB Complaint":
                assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText(), "INEB Unknown", "Inb corresponce type is not matching");
                break;
            case "General SR":
                assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText(), "maersk Unknown", "Inb corresponce type is not matching");
                break;
        }

    }

    @Then("I verify that Inbound Correspondence Page has all {string} linked objects")
    public void i_verify_that_Inbound_Correspondence_Page_has_all_linked_objects(String SRType) {
        waitFor(3);
        scrollDownUsingActions(5);
        waitForVisibility(inboundCorrespondencePage.inboundLinkNames.get(0), 10);
        boolean task = false, sr = false, caseLink = false, consumer = false; int k=0;
        for (int i = 0; i < inboundCorrespondencePage.inboundLinkNames.size(); i++) {
            String linkType = inboundCorrespondencePage.inboundLinkNames.get(i).getText();
            waitFor(5);
            switch (linkType) {
                case "Task":
                    if (inboundCorrespondencePage.inboundLinkTypes.get(i).getText().equalsIgnoreCase("Verification Document") ||
                            SRType.equalsIgnoreCase("Appeal") || SRType.equalsIgnoreCase("Complaint SR"))
                        sa.get().assertThat(inboundCorrespondencePage.inboundCIDValues.get(i).getText()).as("Task id is empty").isNotEmpty();
                    else
                        sa.get().assertThat(inboundCorrespondencePage.inboundCIDValues.get(i).getText()).as ("TaskId Value is missing").isEqualTo((Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) - 1) + "");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkNames.get(i).getText()).as("Name Value is missing").isEqualTo( "Task");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatusDates.get(i).getText().equals(taskValues.get().get("startDate")) || inboundCorrespondencePage.inboundLinkStatusDates.get(i).getText().equals(getCurrentDate())).as("Status Date Value is not correct").isTrue();
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatus.get(i).getText()).as("Status Value is not Created").isEqualTo("Created");
                    if (SRType.equalsIgnoreCase("Appeals SR"))
                        sa.get().assertThat(inboundCorrespondencePage.inboundLinkTypes.get(i).getText()).as("Type Value is not correct for Appeals SR").isEqualTo("Review Appeal");
                    else if (SRType.equalsIgnoreCase("Complaint SR"))
                        sa.get().assertThat(inboundCorrespondencePage.inboundLinkTypes.get(i).getText()).as("Type Value is not correct for Complaint SR").isEqualTo("Review Complaint");
                    else if (SRType.equalsIgnoreCase("Application SR"))
                        sa.get().assertThat(inboundCorrespondencePage.inboundLinkTypes.get(i).getText()).as("Type Value is not correct for Application SR").isEqualTo("Verification Document");
                    else if (SRType.equalsIgnoreCase("Appeal"))
                        sa.get().assertThat(inboundCorrespondencePage.inboundLinkTypes.get(i).getText().equals("Review Appeals Form") ||
                                inboundCorrespondencePage.inboundLinkTypes.get(i).getText().equals("GCNJ Appeals Acknowledgement Letter")).as("Type Value is not correct for Appeal SR").isTrue();
                    task = true;k++;
                    break;

                case "Service Request":
                    sa.get().assertThat(inboundCorrespondencePage.inboundCIDValues.get(i).getText().equals(CRM_TaskManagementStepDef.srID.get()) ||
                            inboundCorrespondencePage.inboundCIDValues.get(i).getText().equals(Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) - 1 + "")||
                            inboundCorrespondencePage.inboundCIDValues.get(i).getText().equals(CRM_TaskManagementStepDef.taskId.get())).as("Service request Id doesnt match").isTrue();
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkNames.get(i).getText()).as("Service Request Name Value is missing").isEqualTo("Service Request");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatusDates.get(i).getText().equals(taskValues.get().get("startDate")) || inboundCorrespondencePage.inboundLinkStatusDates.get(i).getText().equals(getCurrentDate()) ||
                            inboundCorrespondencePage.inboundLinkStatusDates.get(i).getText().equals(projectDate)).as("Status Date Value is not correct").isTrue();
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("status"))
                        sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatus.get(i).getText()).as("Status Value is mistach").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
                    else
                        sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatus.get(i).getText()).as("Status Value is not Open").isEqualTo("Open");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkTypes.get(i).getText()).as("Type Value is not correct for Appeals SR").isEqualTo(SRType);
                    sr = true;
                    break;
                case "Consumer Profile":
                    if(SRType.equals("Appeal") || SRType.equals("CoverVA") && inboundCorrespondencePage.inboundLinkStatus.get(i).getText().equals("Closed")) {
                        sa.get().assertThat(inboundCorrespondencePage.inboundCIDValues.get(i).getText()).as("Consumer Id is empty").isNotBlank();
                    }else
                        sa.get().assertThat(inboundCorrespondencePage.inboundCIDValues.get(i).getText()).as("Consumer id Value is missing").isEqualTo(taskValues.get().get("consumerID"));
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkNames.get(i).getText()).as("Name Value is missing").isEqualTo("Consumer Profile");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkTypes.get(i).getText()).as("Type Value is missing").isEqualTo("Consumer");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatusDates.get(i).getText()).as("Consumer Status date is wrong").isEqualTo("--/--/----");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatus.get(i).getText()).as("Status Value is missing").isEqualTo("Active");
                    consumer = true;
                    break;
                case "Case":
                    if(SRType.equals("Appeal")){
                        sa.get().assertThat(inboundCorrespondencePage.inboundCIDValues.get(i).getText()).as("Case Id is empty").isNotBlank();
                    }else
                        sa.get().assertThat(inboundCorrespondencePage.inboundCIDValues.get(i).getText()).as("CaseID Value is missing").isEqualTo(taskValues.get().get("caseID"));
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkTypes.get(i).getText()).as("Type Value is not showing null").isEqualTo("Null");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatusDates.get(i).getText()).as("Status Date Value is not showing blank").isEqualTo("--/--/----");
                    sa.get().assertThat(inboundCorrespondencePage.inboundLinkStatus.get(i).getText()).as("Status Value is not showing null").isEqualTo("Null");
                    caseLink = true;
                    break;
            }
        }
        if(SRType.equals("Appeal")) {
            sa.get().assertThat((task && caseLink && consumer && sr)||(task && sr)).as("Link section is not displayed case/consumer/sr information").isTrue();
        }else if(SRType.equals("General Service Request") || projectName1.get().equals("CoverVA")){
            sa.get().assertThat((consumer && sr) || (task && consumer && sr) || (task && sr)).as("Link section is not displayed consumer/sr information").isTrue();
        }else
            sa.get().assertThat(sr && caseLink && consumer).as("Link section is not displayed case/consumer/sr information").isTrue();
        if(SRType.equals("Appeal"))
            sa.get().assertThat(k).as("Task link is incorrect").isEqualTo(2);
        else if(SRType.equals("Complaint SR") || SRType.equalsIgnoreCase("Application SR") || SRType.equals("Application Renewal SR") || SRType.equals("Renewal SR"))
            sa.get().assertThat(k).as("Task link is incorrect").isEqualTo(1);
    }

    public void deselectValueFromMultiSelectDropDown(WebElement ele) {
        ele.click();
        hover(ele);
        for (int i = 0; i < myTask.multiSelectedVlus.size(); ) {
            myTask.multiSelectedVlus.get(i++).click();
            i--;
            waitFor(1);
        }
        actions.click(createGeneralTask.txtTaskInfo).build().perform();
    }

    @Then("I verify {string} task is not created")
    public void iVerifyTaskIsNotCreated(String type) {
        waitForVisibility(createGeneralTask.linkType.get(0), 900);
        scrollDownUsingActions(5);
        boolean flag = true;
        for (int i = 0; i < createGeneralTask.linkType.size(); i++) {
            if (createGeneralTask.linkType.get(i).getText().equalsIgnoreCase(type)) {
                flag = false;
                break;
            }
        }
        assertTrue(flag, type + " Task is created");
    }

    @Then("I verify {string} filed is not mandatory for the task")
    public void verifyFieldIsNotMandatoryForTask(String expectedMessage) {
        waitFor(5);
        expectedMessage = expectedMessage + " is required and cannot be left blank";
        try {
            assertFalse(Driver.getDriver().findElement(By.xpath("//p[text()='" + expectedMessage + "']")).isDisplayed(),
                    expectedMessage + " Mandatory Field level error message is displayed");
        } catch (Exception NoSuchElementException) {
            assertTrue(true, "Did not get NoSuchElementException");
        }
        expectedMessage = expectedMessage.trim();
        try {
            assertFalse(Driver.getDriver().findElement(By.xpath("//label[text()='" + expectedMessage + "']//span[text()='*']")).isDisplayed(),
                    "* mark is displayed on" + expectedMessage + " field");
        } catch (Exception NoSuchElementException) {
            assertTrue(true, "Did not get NoSuchElementException");
        }
        scrollUpUsingActions(1);
    }

    @When("I search specific task by task id {string}")
    public void i_search_specific_task_by_task_id(String taskID) {
        waitFor(1);
        waitForVisibility(taskSearchPage.taskId, 10);
        taskSearchPage.taskId.sendKeys(taskID);
        waitFor(1);
        click(taskSearchPage.searchBtn);

    }

    @Then("I expand above task and very Contact Record Link type {string}")
    public void i_expand_above_task_and_very_Contact_Record_Link_type(String contactRecordType) {
        waitFor(10);
        click(createGeneralTask.firstTaskIdBtNew);
        waitFor(7);
        if (contactRecordType.equalsIgnoreCase("Unidentified Contact")) {
            assertEquals(createGeneralTask.unidentifiedCRType.getText(), contactRecordType);
        } else {
            assertEquals(createGeneralTask.contactRecordType.getText(), contactRecordType);
        }
    }
    @Then("I expand above task and very Contact Record Link type and verify the type {string}")
    public void i_expand_above_task_and_very_Contact_Record_Link_type_and_verify_ContactRecordType(String contactRecordType) {
        waitFor(5);
        click(createGeneralTask.firstTaskIdBtNew);
        waitFor(5);
        if (contactRecordType.equalsIgnoreCase("Unidentified Contact")) {
            assertEquals(createGeneralTask.contactRecordTypeUnidentified.getText(), contactRecordType);
        } else if (contactRecordType.equalsIgnoreCase("General")){
            assertEquals(createGeneralTask.contactRecordTypeGeneral.getText(), contactRecordType);
        }else if (contactRecordType.equalsIgnoreCase("Third Party")){
            assertEquals(createGeneralTask.contactRecordTypeThirdParty.getText(), contactRecordType);
        }
    }

    @Then("I click id of {string} in link section of inbound page")
    public void ClickTaskIDinLinksSection(String type) {
        waitForVisibility(inboundCorrespondencePage.inboundDateReceivedValues.get(0), 900);
        scrollDownUsingActions(5);
        boolean flag = false;
        for (int i = 0; i < inboundCorrespondencePage.inboundDateReceivedValues.size(); i++) {
            if (inboundCorrespondencePage.inboundDateReceivedValues.get(i).getText().equalsIgnoreCase(type)) {
                CRM_TaskManagementStepDef.taskId.set(inboundCorrespondencePage.inboundCIDValues.get(i).getText());
                click(inboundCorrespondencePage.inboundCIDValues.get(i));
                waitFor(1);
                flag = true;
                break;
            }
        }
        assertTrue(flag, "Record not found");
    }

    @Then("I expand above task and verify that when clicking on consumerID without CASE I am navigated to see {string}, {string} and {string} screen")
    public void i_expand_above_task_and_verify_that_when_clicking_on_consumerID_without_CASE_I_am_navigated_to_see_and_screen(String string, String string2, String string3) {
        waitFor(10);
        click(createGeneralTask.firstTaskIdBt);
        waitFor(7);
        createGeneralTask.consumerIDfromTaskPage.click();
        waitFor(4);
        assertTrue(activeContactPage.demographicInfoTab.isDisplayed());
        assertTrue(activeContactPage.caseContactDetailsTab.isDisplayed());
        assertTrue(activeContactPage.taskServiceRequest.isDisplayed());

    }

    @Then("I link to {string} using consumer as {string} and Last Name as {string}")
    public void linkToUsingConsumerAsAndLastNameAs(String linkType, String firstName, String lastName) {
        i_click_on_link_caseconsumer_button_under_link_section();
        i_see_case_consumer_search_section_display();
        i_search_for_consumer_have_first_name_as_something_and_last_name_as_something(firstName, lastName);
        i_click_on_search_option();
        i_should_able_to_manually_link_the_task_to_a_case_or_consumer_by_search_operation();
        i_expand_the_first_record_of_the_search_result();
        switch (linkType) {
            case "Case Only":
                i_select_link_to_case_only_checkbox();
                link_record_case_button_get_displayed();
                click_link_case_button();
                verify_linked_case_value();
                break;
            case "Consumer Only":
                click_link_consumer_button();
                break;
            case "Case Consumer":
                i_select_consumer_radio_button();
                click_link_consumer_button();
                break;
            default:
                fail("Invalid switch argument");
        }
    }
    @And("I click on contact link button under LINK section")
    public void i_click_on_link_contactlink_button_under_link_section() {
        scrollDownUsingActions(2);
        waitFor(1);
        scrollToElement(createGeneralTask.contactLinks);
        assertTrue(createGeneralTask.link.isDisplayed(), "Link text not showing");
        waitForVisibility(createGeneralTask.contactLinks, 3);
//        actions.moveToElement(createGeneralTask.contactLinks).click().build().perform();
        jsClick(createGeneralTask.contactLinks);
     //   createGeneralTask.contactLinks.click();
    }

    @When("I click on Case and Consumer button")
    public void i_click_on_Case_and_Consumer_button() {
        waitForVisibility(createGeneralTask.caseConsumerBtn, 30);
        jsClick(createGeneralTask.caseConsumerBtn);
     //   createGeneralTask.caseConsumerBtn.click();
    }

    @Then("Verify Create Task page for Process Appeals Supporting Documentation visible")
    public void verifyCreateTaskPageForProcessAppealsSupportingDocumentationVisible() {
        assertEquals( createGeneralTask.taskType.getText(),"Process Appeals Supporting Documentation","TaskType is incorrect");
    }

    @And("Verify required fields are available on Edit Task")
    public void verifyRequiredFieldsAreAvailableOnEditTask() {
        waitForVisibility(createGeneralTask.dispos,10);
        assertTrue(createGeneralTask.dispos.getText().equalsIgnoreCase("Resolved"));
        assertTrue(myTask.viewExtCaseId.isDisplayed());
    }

    @And("Verify Plan StartDate and Plan EffectiveDate")
    public void verifyPlanStartDateAndPlanEffectiveDate() {
        assertTrue(createGeneralTask.coverageStartDate.isDisplayed());
        assertTrue(createGeneralTask.coverageEndtDate.isDisplayed());
    }


    @Then("Validate View Task UI fields Display fields")
    public void validateViewTaskUIFieldsForEnrollmentEscalation(List<String> fields) {
        waitForVisibility(createGeneralTask.viewFields(fields.get(1)),7);
        for (String field:fields) {
            assertTrue(createGeneralTask.viewFields(field).isDisplayed());
        }
    }

    @Then("Validate Date of Contact displayed on view task")
    public void ValidateDateofContactdisplayedonviewtask(String date) {
        waitForVisibility(createGeneralTask.dateOfContact(date),7);
        assertTrue(createGeneralTask.dateOfContact(date).getText().equalsIgnoreCase(date));

    }

    @And("I select {string} in the create link section")
    public void linkToCaseConsumerOrTaskServiceRequest(String linkType){
        waitFor(5);
        if(linkType.equalsIgnoreCase("CASE/CONSUMER")){
            createGeneralTask.caseConsumerBtn.click();
        }
        else if(linkType.equalsIgnoreCase("TASK/SERVICE REQUEST")){
            waitFor(5);
            myTask.taskOrSRLinkBtn.click();
        }
        else if(linkType.equalsIgnoreCase("Unlink Case/Consumer")){
            createGeneralTask.unlinkCaseOrConsumer.click();
        }
        else fail("Incorrect argument provide " + linkType);
    }

    @Then("I verify {string} contains : {string} on {string} link section")
    public void validateTaskContainsCorrectLinkObjects(String srType, String expectedType,String page) {
        waitFor(5);
        scrollDownUsingActions(5);
        String name = page.equalsIgnoreCase("SR") ? "Task":"Service Request";
        List<String> expectedLinkType = Arrays.stream(expectedType.split(",")).sorted().collect(Collectors.toList());
        List<String> actualLinkType =  getDynamicWebElements("//td[text()='name']//parent::tr//td[5]","name",name).stream().map(WebElement::getText).collect(Collectors.toList());
        assertEquals(actualLinkType.stream().sorted().collect(Collectors.toList()), expectedLinkType, "Link section contains incorrect objects");
        assertEquals(actualLinkType.size(), expectedLinkType.size(), "Link section contains incorrect objects");
    }

    @And("I unlink {string} from link section")
    public void unlinkObjectFromLinkSection(String taskName){
        waitFor(5);
        String xPath = "//td[text()='taskName']//parent::tr//td//following-sibling::button";
        List<WebElement> rowByTask = getDynamicWebElements(xPath, "taskName",taskName);
        scrollToElement(rowByTask.get(0));
        waitFor(5);
        rowByTask.get(0).click();
    }

    @Then("I verify unlink button is not displayed for {string} in link the section")
    public void verifyUnlinkButtonIsNotDispayedForTask(String taskName) {
        waitFor(2);
        scrollDownUsingActions(2);
        String xPath = "//td[text()='taskName']//parent::tr//td//following-sibling::button";
        List<WebElement> webElement = getDynamicWebElements(xPath, "taskName",taskName);
        assertTrue(webElement.isEmpty(),"unlink button is displayed");
    }
    @Then("I verify new consumer is linked to the task")
    public void i_verify_new_consumer_is_linked_to_the_task() {
        assertTrue(createGeneralTask.createTaskPageHeader.isDisplayed(), "Not on create TASK page");
        assertTrue(isElementDisplayed(createGeneralTask.consumerLinks.get(0)));

    }
    @Then("I verify link section does not contains business objects")
    public void validateLinkSectionIsEmpty(){
        waitFor(2);
        scrollDownUsingActions(1);
        assertTrue(createGeneralTask.linkrowsize.isEmpty(),"Link contains objects");
    }

    @When("I click on validate link button")
    public void i_click_on_validate_link_button() {
        waitForVisibility(createGeneralTask.validateTaskbutton, 30);
        createGeneralTask.validateTaskbutton.click();
    }

    @When("I store external consumer id from Demographic Info Page")
    public void i_store_external_consumer_id_from_Demographic_Info_Page() {
        waitFor(1);
        synchronized (externalConsumerID){externalConsumerID.set(crmEditConsumerProfilePageOne.convertedExternalConsumerId.getText());}
    }

    @When("I store external case id from Demographic Info Page")
    public void i_store_external_case_id_from_Demographic_Info_Page() {
        waitFor(1);
        synchronized (externalCaseID){externalCaseID.set(crmEditConsumerProfilePageOne.convertedCaseId.getText());}
    }

    @When("I store external case id from Create Task Links Component")
    public void i_store_external_case_id_from_Create_Task_Links_Component() {
        scrollDown();
        waitFor(5);
        for (int i = 0; i < createGeneralTask.contactLinkrowsize.size(); i++) {
            if(createGeneralTask.linkedNames.get(i).getText().equalsIgnoreCase("Case")){
                synchronized (externalCaseID){externalCaseID.set(createGeneralTask.linkedIds.get(i).getText());}
            }
        }
    }

    @When("I store external consumer id from Create Task Links Component")
    public void i_store_external_consumer_id_from_Create_Task_Links_Component() {
        for (int i = 0; i < createGeneralTask.contactLinkrowsize.size(); i++) {
            if(createGeneralTask.linkedTypes.get(i).getText().equalsIgnoreCase("Consumer")){
                synchronized (externalConsumerID){externalConsumerID.set(createGeneralTask.linkedIds.get(i).getText());}
            }
        }
    }
    @And("I Verify Task service Request Button is not displaying in the create link section for SR Task Type")
    public void verifyTaskServiceRequestButtonIsNotisplaying(){
        assertFalse(isElementDisplayed(myTask.taskOrSRLinkBtn));
    }

    @And("I verify create link button disappear from link section")
    public void verifyCreateLinkButtonDisappearFromLinkSection() {
        assertFalse(isElementDisplayed(createGeneralTask.contactLinks));
    }

    @And("I verify case consumer link button is not present in create link list")
    public void verifyCaseConsumerLinkButtonNotPresentInCreateLinkList() {
        i_click_on_link_contactlink_button_under_link_section();
        assertFalse(isElementDisplayed(createGeneralTask.caseConsumerBtn));
    }

    @When("I search Task by type for record")
    public void iSearchTaskTypeForRecord() {
        waitFor(2);
        singleSelectFromDropDown(taskSearch.taskTypeDropdown, "Enrollment Escalation");
        waitFor(2);
        taskSearch.searchBtn.click();
        waitFor(3);
    }

    @Then("I verify {string} single select drop down value in task search page")
    public void verifySingleSelectDropDownValuesInTaskSearchPage(String ddName, List<String> expectedValues) {
        waitFor(3);
        WebElement dropdown = Driver.getDriver().findElement(By.xpath("//input[@id='" + ddName + "']/.."));
        hover(dropdown);
        dropdown.click();
        waitFor(2);
        List<WebElement> dropdownVlu = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + ddName + "')]//ul/li[string-length() > 0]"));
        List<String> actualValues = dropdownVlu.stream().map(WebElement::getText).collect(Collectors.toList());
        assertEquals(actualValues, expectedValues, ddName + " drop down values are incorrect");
        actions.moveToElement(taskSearchPage.taskSearchHeader).click().perform();
    }

    public void validateDateField(WebElement ele, String fieldName){
        clearAndFillText(ele, "22/34/0934");
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.invalidDateFormat)).as("Error message is not displayed for invalid date " +ele.getAttribute("value") +" in "+fieldName).isTrue();

        clearAndFillText(ele, getGreaterDate(5));
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.invalidDateFormat)).as("Error message is displayed for correct date " +ele.getAttribute("value") +" in "+fieldName).isFalse();

        clearAndFillText(ele, getCurrentDate());
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.invalidDateFormat)).as("Error message is displayed for correct date " +ele.getAttribute("value") +" in "+fieldName).isFalse();
    }

    public void validateTimeField(WebElement ele, String fieldName){
        clearAndFillText(ele, "09:00 AM");
        sa.get().assertThat(ele.getAttribute("value")).as(fieldName+" Time is wrong").isEqualTo("09:00 AM");
    }

    public void validatePhoneField(WebElement ele, String fieldName) {
        clearAndFillText(ele,"abcd1234567898");
        sa.get().assertThat(ele.getAttribute("value")).as(fieldName+" field value is wrong").isEqualTo("123-456-7898");
    }

    public void validateEmailField(WebElement ele, String fieldName, WebElement errorMsg){
        ele.sendKeys("me@info@healthcare.us");
        jsClick(myTask.saveBtn);
        sa.get().assertThat(isElementDisplayed(errorMsg)).as(fieldName+" field error message is not displayed").isTrue();
        clearAndFillText(ele, "test@co.g..");
        jsClick(myTask.saveBtn);
        sa.get().assertThat(isElementDisplayed(errorMsg)).as(fieldName+" field error message is not displayed").isTrue();
        clearAndFillText(ele, "!user_name@test!gov");
        jsClick(myTask.saveBtn);
        sa.get().assertThat(isElementDisplayed(errorMsg)).as(fieldName+" field error message is not displayed").isTrue();
        clearAndFillText(ele, "test@test.4v");
        jsClick(myTask.saveBtn);
        sa.get().assertThat(isElementDisplayed(errorMsg)).as(fieldName+" field error message is not displayed").isTrue();
        clearAndFillText(ele, "test@gmail.com");
        jsClick(myTask.saveBtn);
        sa.get().assertThat(isElementDisplayed(errorMsg)).as(fieldName+" field error message not displayed").isFalse();
        clearAndFillText(ele, getRandomString(110));
        sa.get().assertThat(ele.getAttribute("value")).as(fieldName+" field length is not 100 characters").hasSize(100);
    }
    public void validateCheckboxField(WebElement ele, String fieldName){
        ele.click();
        sa.get().assertThat(ele.isSelected()).as(fieldName+" checkbox is not selected").isTrue();
        sa.get().assertThat(ele.getAttribute("type")).as(fieldName+" field is not checkbox").isEqualTo("checkbox");
    }

    @Then("I verify {string} task status is updated to {string} on the link section")
    public void iVerifyTaskStatusIsUpdatedToOnTheLinkSection(String taskName, String status) {
        String xpathForTaskRow = "//td[text()='task_name']//parent::tr//td";
        List<WebElement> rowByTask = getDynamicWebElements(xpathForTaskRow, "task_name", taskName);
        sa.get().assertThat(rowByTask.get(6).getText()).as("Status is mismatch").isEqualTo(status);
    }
    @Then("click on close button on error Popup")
    public void clickOnCloseButtonOnErrorPopup(){
        waitForVisibility(createGeneralTask.closeButton,2);
        createGeneralTask.closeButton.click();
    }
//   it's a duplicate method and we are not using this method anywhere in our script so, I am connect for right now

   /*@Then("I verify task optional fields {string}")
    public void verifyTaskTypeIsOptionalField(String expectedMessage) {
        expectedMessage = expectedMessage + " is required and cannot be left blank";
        try {
            sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath
                    ("//p[text()='" + expectedMessage + "']")))).as(expectedMessage + " Mandatory Field level error message is displayed").isFalse();
        } catch (NoSuchElementException e) {
            System.out.println(expectedMessage + " is optional as expected");
        }
    }*/

    @Then("I verify Due In is calculated correct Business days by skipping the Holiday and Weekends")
    public void IverifyDueIniscalculatedcorrectBusinessdaysbyskippingtheHolidayandWeekends() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        waitFor(6);
        LocalDate enddate = LocalDate.parse(createGeneralTask.lblDueDate.getText(),formatter);
        LocalDate startdate = LocalDate.parse(getCurrentDate(),formatter);

        sa.get().assertThat(gettWorkingDays(startdate, enddate))
                .as("Due In date value not matches").hasSize(Integer.parseInt(createGeneralTask.lblDueIn.getText().replaceAll("[^0-9]", "")));
    }

    public List<LocalDate> gettWorkingDays(LocalDate startDate, LocalDate endDate) throws Exception {
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().i_initiated_create_business_unit_api(projectName1.get(), "2023");
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().getHolidayList();
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().i_initiated_create_business_unit_api(projectName1.get());
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().getTimeFrameConfiguredForProject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Predicate<LocalDate> isHoliday = date ->APITMHolidayController.holiday.contains(date.format(formatter));


        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween<0){
            daysBetween=Math.abs(-daysBetween);
        }

        return Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(daysBetween)
                .filter(isHoliday.negate())
                .collect(Collectors.toList());
    }

    @Then("I verify Due In is calculated correct Business days by skipping the Holiday and Weekends for Task Slider")
    public void IverifyDueIniscalculatedcorrectBusinessdaysbyskippingtheHolidayandWeekendsForTaskSlider() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        waitFor(6);
        LocalDate enddate = LocalDate.parse(createGeneralTask.dueDatesList.get(0).getText(),formatter);
        LocalDate startdate = LocalDate.parse(getCurrentDate(),formatter);

        sa.get().assertThat(gettWorkingDays(startdate, enddate))
                .as("Due In date value not matches").hasSize(Integer.parseInt(createGeneralTask.lblDueIn.getText().replaceAll("[^0-9]", "")));
    }

    @Then("I verify Due In is calculated correct in My task List")
    public void iVerifyDueInIsCalculatedCorrectInMyTaskList() throws Exception {
        for (int i = 0; i < myTask.DueDatesmyTasks.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            waitFor(6);

            LocalDate enddate = LocalDate.parse(myTask.DueDatesmyTasks.get(i).getText(),formatter);
            LocalDate startdate = LocalDate.parse(getCurrentDate(),formatter);

            sa.get().assertThat(gettWorkingDays(startdate,enddate ))
                    .as("Due In date value not matches").hasSize(Integer.parseInt(myTask.dueInMyTasks.get(i).getText().replaceAll("[^0-9]", "")));
        }
    }

    @Then("I verify Due In is calculated correct in Task Search result List")
    public void iVerifyDueInIsCalculatedCorrectInTaskSearchresultList() throws Exception {
        for (int i = 0; i < taskSearch.dueInValues.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            waitFor(6);

            LocalDate enddate = LocalDate.parse(taskSearch.dueDateValues.get(i).getText(),formatter);
            LocalDate startdate = LocalDate.parse(getCurrentDate(),formatter);

            sa.get().assertThat(gettWorkingDays(startdate,enddate ))
                    .as("Due In date value not matches").hasSize(Integer.parseInt(taskSearch.dueInValues.get(i).getText().replaceAll("[^0-9]", "")));
        }
    }

    @Then("I verify Due In is calculated correct in Task Service Requests List")
    public void iVerifyDueInIsCalculatedCorrectInTaskServiceRequeststList() throws Exception {
        for (int i = 0; i < crmTaskServiceTab.dueDates.size(); i++) {
            if (crmTaskServiceTab.taskStatuses.get(i).getText().equals("Complete") || crmTaskServiceTab.taskStatuses.get(i).getText().equals("Cancelled")) {
                break;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            waitFor(6);

            LocalDate enddate = LocalDate.parse(crmTaskServiceTab.dueDates.get(i).getText(), formatter);
            LocalDate startdate = LocalDate.parse(getCurrentDate(), formatter);
            sa.get().assertThat(gettWorkingDays(startdate, enddate))
                    .as("Due In date value not matches").hasSize(Integer.parseInt(crmTaskServiceTab.dueIns.get(i).getText().replaceAll("[^0-9]", "")));
        }
    }

    @And("Verify only one CaseId is linked to task")
    public void verifyOnlyOneCaseIdIsLinkedToTask() {
        sa.get().assertThat(crmTaskServiceTab.CasesInTask.size()).as("Linked Case IDS not matches").isEqualTo(1);
    }
    @Then("I verify {string} task status is updated to {string} in the link section for multiple tasks")
    public void iVerifyTaskStatusIsUpdatedInLinkSection(String srType, String taskStatus) {
        List<WebElement> type = getDynamicWebElements("//td[text()='task_name']//parent::tr//td[3]", "task_name", srType);
        List<WebElement> status = getDynamicWebElements("//td[text()='task_name']//parent::tr//td[7]", "task_name", srType);
        for (int i=0; i<type.size();i++){
            if (type.get(i).getText().equalsIgnoreCase(taskId1.get())) {
                waitFor(1);
                sa.get().assertThat(status.get(i).getText()).as("Status is mismatch").isEqualTo(taskStatus);
            }
        }
    }
    @And("I store the task id for created status from the link section")
    public void storeTheTaskIdFromLinkSection() {
        waitFor(2);
        scrollDownUsingActions(5);
        String statusVlu = "Created";
        List<WebElement> xpathForTaskRow = getDynamicWebElements("//td[text()='status']//parent::tr//td", "status", statusVlu);
        if (isElementDisplayed(xpathForTaskRow.get(7))) {
            synchronized (taskId1){taskId1.set(xpathForTaskRow.get(2).getText());}
        }
    }

    @Then("I verify {string} does not contains : {string} on link section")
    public void validateTaskDoesNotContainsLinkObjects(String srType, String expectedType) {
        waitFor(5);
        scrollDownUsingActions(5);
        List<String> actualLinkType =  getDynamicWebElements("//td[text()='name']//parent::tr//td[5]","name",expectedType).stream().map(WebElement::getText).collect(Collectors.toList());
        sa.get().assertThat(actualLinkType).as(expectedType + " is displayed ").hasSize(0);
    }

    @Then("I verify Success message is displayed for SR update")
    public void i_verify_Success_message_is_displayed_for_sr_update() {
        waitForVisibility(createGeneralTask.lblSuccessMessage, 10);
        assertTrue(isElementDisplayed(createGeneralTask.lblSuccessMessage));
        assertTrue(isElementDisplayed(myTask.srUpdatedTxt), "Success message text is not displayed");
    }


    @Then("I verify link section has {string} and other business object on Active Contact")
    public void verify_ConsumerID_SRId_TaskID_linkComponentonActiveContact(String srType) {
        scrollDownUsingActions(5);
        boolean sr = false, consumer = false, task = false;

        for (int i = 0; i < createGeneralTask.activecontactLinkrowsize.size(); i++) {
            String linkType = createGeneralTask.activecontactLinkrowsize.get(i).getText();
            switch (linkType) {
                case "Service Request":
                    System.out.println("Service Request");
                    assertEquals(createGeneralTask.taskLinkInfo.get(0).getText(), CRM_TaskManagementStepDef.taskId.get(), "TaskId Value is missing");
                    assertEquals(createGeneralTask.taskLinkInfo.get(1).getText(), "Service Request", "Name Value is missing");
                    assertEquals(createGeneralTask.taskLinkInfo.get(2).getText(), taskValues.get().get("taskType"), "Type Value is not correct");
                    assertEquals(createGeneralTask.taskLinkInfo.get(3).getText(), taskValues.get().get("startDate"), "Status Date Value is not correct");
                    assertEquals(createGeneralTask.taskLinkInfo.get(4).getText(), "Open", "Status Value is not Created");
                    sr = true;
                    break;
                case "Task":
                    System.out.println("Task");
                    assertEquals(createGeneralTask.taskLinkInfo.get(5).getText(),
                            (Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) + 1) + "", "TaskId Value is missing");
                    assertEquals(createGeneralTask.taskLinkInfo.get(6).getText(), "Task", "Name Value is missing");
                    if (srType.equalsIgnoreCase("Application SR") || srType.equalsIgnoreCase("Application Renewal SR")|| srType.equalsIgnoreCase("Renewal SR")) {
                        assertEquals(createGeneralTask.taskLinkInfo.get(7).getText(), "Process App V1", "Type Value is not correct");
                    } else if (srType.equalsIgnoreCase("Appeals SR")) {
                        assertEquals(createGeneralTask.taskLinkInfo.get(2).getText(), "Review Appeal", "Type Value is not correct for Appeals SR");
                    } else if (srType.equalsIgnoreCase("Complaint SR")) {
                        assertEquals(createGeneralTask.taskLinkInfo.get(7).getText(), "Review Complaint", "Type Value is not correct for Complaint SR");
                    }
                    assertEquals(createGeneralTask.taskLinkInfo.get(8).getText(), taskValues.get().get("startDate"),
                            "Status Date Value is not correct");
                    assertEquals(createGeneralTask.taskLinkInfo.get(9).getText(), "Created",
                            "Status Value is not Created");
                    task = true;
                    break;
                case "Consumer Profile":
                    System.out.println("Consumer Profile");
                    assertEquals(createGeneralTask.consumerInfo.get(0).getText(), taskValues.get().get("consumerID"), "ConsumerID Value is missing");
                    assertEquals(createGeneralTask.consumerInfo.get(1).getText(), "Consumer Profile", "Name Value is missing");
                    assertEquals(createGeneralTask.consumerInfo.get(2).getText(), "Consumer", "Type Value is missing");
                    assertTrue(verifyDateFormat(createGeneralTask.consumerInfo.get(3)), "Consumer Status date is wrong");
                    assertTrue(!createGeneralTask.consumerInfo.get(4).getText().isEmpty(), "Status Value is missing");
                    consumer = true;
                    break;
            }
        }
        if (createGeneralTask.linkrowsize.size() == 2) {
            assertTrue(task && sr, "Link section is not displayed consumer/task information");
        } else {
            System.out.println(sr);
            System.out.println(consumer);
            System.out.println(task);
            assertTrue(sr && consumer && task,
                    "Link section is not displayed consumer/task/Sr information");
        }

    }

    public void verify_application_active_cr_page() {
        scrollDown();
        waitFor(2);
        if (taskValues.get().containsKey("srType") && (taskValues.get().get("srType").equalsIgnoreCase("Appeal") || taskValues.get().get("srType").equalsIgnoreCase("Review Appeals Form") || taskValues.get().get("srType").equalsIgnoreCase("GCNJ Appeals Acknowledgement Letter") || taskValues.get().get("srType").equalsIgnoreCase("Follow-Up on Appeal")))
            sa.get().assertThat(createGeneralTask.caseInfo.get(1).getText()).as("CaseId is empty").isNotBlank();
        else if (projectName1.get().equalsIgnoreCase("IN-EB")) {
            sa.get().assertThat(createGeneralTask.caseInfo.get(1).getText()).as("External case id value is missing").isEqualTo(externalCaseID.get());
        } else
            sa.get().assertThat(createGeneralTask.caseInfo.get(1).getText()).as("CaseID Value is missing").isEqualTo(taskValues.get().get("caseID"));
        sa.get().assertThat(createGeneralTask.caseInfo.get(2).getText()).as("Name Value is missing").isEqualTo("Case");
        assertTrue(createGeneralTask.caseInfo.get(3).getText().equals("-- --") || createGeneralTask.caseInfo.get(4).getText().equalsIgnoreCase("Null"),
                "Type Value is not showing null");
        sa.get().assertThat(createGeneralTask.caseInfo.get(4).getText()).as("Status Date Value is not showing blank").isEqualTo("--/--/----");
        sa.get().assertThat(createGeneralTask.caseInfo.get(5).getText()).as("Status Value is not showing null").isEqualTo("-- --");
    }

    @Then("I verify task is linked to application")
    public void verify_task_is_linked_to_application(){
        scrollDownUsingActions(3);
        assertEquals(applicationIdAPI.get(), trackingPage.linkIDs.get(0).getText(), "Application id isnt verified on View Task Links Component");
        assertEquals(trackingPage.linkNames.get(0).getText(), "Application", "Application link name isnt verified on View Task Links Component");
        assertEquals(trackingPage.linkTypes.get(0).getText(), "New - Medical Assistance", "Application type isnt verified on View Task Links Component");
        assertEquals(createGeneralTask.projectDateAtHeader.getText(), trackingPage.linkStatusDates.get(0).getText(), "Status date isnt verified on View Task Links Component");
        assertTrue(trackingPage.linkStatus.get(0).getText().equalsIgnoreCase("Insufficient"),"Status isnt verified on View Task Links Component");
        System.out.println("Application link verification is completed on View Task API");
    }

    //this method can be used if there is only one of type linked object
    @And("I click on {string} id on View Task Page")
    public void click_on_id_on_View_Task_Page(String type) {
        waitForVisibility(createGeneralTask.linkType.get(0), 1000);
        scrollDownUsingActions(5);
        boolean flag = false;
        for (int i = 0; i < createGeneralTask.linkType.size(); i++) {
            if (createGeneralTask.linkNames.get(i).getText().equalsIgnoreCase(type)) {
                jsClick(createGeneralTask.contactLinkrowsize.get(i));
            }
        }
    }

    @Then("I verify error message displayed on the {string} if I select future day")
    public void hasPastOrFutureDateErrorMessage(String fieldName) {
        String xPath = "//label[text()='" + fieldName + "']//following-sibling::div//following-sibling::p[text()='Date cannot be in the future']";
        try {
            sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath(xPath))))
                    .as(fieldName + " error message is not displayed for future day").isTrue();
        } catch (NoSuchElementException e) {
            sa.get().fail(fieldName + " error message is not displayed for future day");
        }
    }

    @When("I store external case id and consumer id")
    public void i_store_external_case_id_consuder_id() {
        synchronized (externalCaseID){externalCaseID.set(crmEditConsumerProfilePageOne.caseIdOnCaseConsumerSearch.getText());}
        synchronized (externalConsumerID){externalConsumerID.set(crmEditConsumerProfilePageOne.consumerIdOnCaseConsumerSearch.getText());}
    }

    @Then("I verify {string} task and {string} SR is linked to inbound correspondence")
    public void i_verify_task_and_SR_is_linked_to_inbound_correspondence(String tasktype, String srtype){
        scrollDownUsingActions(5);
        boolean sr = false, task = false;
        waitFor(5);
        for (int i = 0; i < inboundCorrespondencePage.inboundLinkNames.size(); i++) {
            String linkType = inboundCorrespondencePage.inboundLinkNames.get(i).getText();
            switch (linkType) {
                case "Service Request":
                    System.out.println("Service Request");
                    assertEquals(inboundCorrespondencePage.inboundLinkTypes.get(i).getText(),srtype, "SR Type is not correct");
                    assertEquals(inboundCorrespondencePage.inboundLinkNames.get(i).getText(), "Service Request", "Name Value is missing");
                    assertEquals(inboundCorrespondencePage.inboundLinkStatus.get(i).getText(), "Open", "Status Value is not Created");
                    sr = true;
                    break;
                case "Task":
                    System.out.println("Task");
                        assertEquals(inboundCorrespondencePage.iblinkids.get(i).getText(),Integer.toString(APITaskManagementController.taskId.get()), "Task id is incorrect");
                    assertEquals(inboundCorrespondencePage.inboundLinkTypes.get(i).getText(),tasktype, "Task type is not correct");
                    assertEquals(inboundCorrespondencePage.inboundLinkNames.get(i).getText(),"Task","Task name isnt verified");
                    assertEquals(inboundCorrespondencePage.inboundLinkStatus.get(i).getText(), "Created", "Status Value is not Created");
                    task = true;
                    break;

            }
        }
        if (inboundCorrespondencePage.inboundLinkTypes.size() == 2) {
            assertTrue(task && sr, "Link section is not displayed sr and task information");
        }
    }

    @Then("I verify new completed date field is displayed with today's date")
    public void i_verify_new_completed_date_field_is_displayed_with_today_s_date() {
        assertTrue(createGeneralTask.completedDate.isDisplayed(),"Completed date isnt displayed");
        assertTrue(createGeneralTask.completedDate.getAttribute("value").equals(getDate("today")),"Completed date value is not correct");
        assertTrue(createGeneralTask.completedDate.isEnabled(),"Completed date field is disabled");
    }

    @Then("I verify renewal processing due date is populated after {int} days application received date")
    public void i_verify_renewal_processing_due_date_is_populated_after_days_application_received_date(int numberOfDays) {
        //putting numbers considering what we choose as app received date, -> app received date + 30days
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
        if (numberOfDays == 0) {
            assertTrue(createGeneralTask.renewalProcessingDueDate.getAttribute("value").isEmpty(), "Renewal processing due date value is not correct. Expected value:" + "" + " but found:" + createGeneralTask.renewalProcessingDueDate.getAttribute("value"));
        } else {
            System.out.println("Processing due date from verification: " + createGeneralTask.renewalProcessingDueDate.getAttribute("value"));
            assertTrue(createGeneralTask.renewalProcessingDueDate.getAttribute("value").equals(getGreaterDateFormatMMddyyyy(numberOfDays)), "Renewal processing due date value is not correct. Expected value:" + getGreaterDateFormatMMddyyyy(numberOfDays) + " but found:" + createGeneralTask.renewalProcessingDueDate.getAttribute("value"));
        }
    }

    @Then("I verify {string} task field is disabled")
    public void i_verify_task_field_is_disabled(String fieldName) {
        switch (fieldName) {
            case "renewal processing dueDate":
                 assertTrue(createGeneralTask.renewalProcessingDueDate.isDisplayed(), "Renewal due date isnt displayed");
                 assertFalse(createGeneralTask.renewalProcessingDueDate.isEnabled(), "Renewal due date field is enabled");
                 break;
            case "current escalation":
                assertTrue(createGeneralTask.currentEscalationType.isDisplayed(),"Current escalation field isnt displayed");
                assertFalse(createGeneralTask.currentEscalationType.isEnabled(),"Current escalation field is enabled and editable");
                break;
            case "originating escalation type":
                assertTrue(createGeneralTask.originatingEscalationType.isDisplayed(),"Originating escalation type field isnt displayed");
                assertEquals(createGeneralTask.originatingEscalationType.getAttribute("aria-disabled"), "true","Originating escalation type field is enabled and editable");
                break;
        }
    }

    @Then("I get the Renewal Cutoff Date")
    public String getRenewalCutoffDate(){
        String []renewalDateArr=createGeneralTask.renewalDate.getAttribute("value").split("/");
        String strRenewalCutoffDate=renewalDateArr[0]+"/"+"15/"+renewalDateArr[2];
        return strRenewalCutoffDate;
    }

    @Then("I verify Renewal Cutoff Date is auto-populated based on the value entered in the Renewal Date")
    public void i_verify_renewal_cutoff_date_is_populated_after_days_application_received_date() {
        assertTrue(createGeneralTask.renewalCutoffDate.getAttribute("value").equals(getRenewalCutoffDate()));
    }
}
