package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.pages.InboundCorrespondencePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.*;
import static org.testng.Assert.*;

public class CRM_CreateServiceRequestStepDef extends CRMUtilities implements ApiBase {

    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMCreateSRPage createSRPage = new CRMCreateSRPage();
    CRMTSRTabForSRPage trsTabSRPage = new CRMTSRTabForSRPage();
    public static final ThreadLocal<ArrayList<String>> displayedInOrder = ThreadLocal.withInitial(ArrayList::new);
    CRMSRViewEditPage srViewEditPage=new CRMSRViewEditPage();
    InboundCorrespondencePage inbondCorrPage =new InboundCorrespondencePage();
    Actions actions = new Actions(Driver.getDriver());

    @Given("I navigate to {string} service request page")
    public void navigateToCreateServiceRequestPage(String taskName) {
        waitForVisibility(dashBoard.btnMenuList, 20);
        System.out.println("1");
        click(dashBoard.btnMenuList);
        waitFor(10);
        hover(dashBoard.createSRMenu);
        dashBoard.createSRMenu.click();
        waitFor(3);
//        waitForVisibility(dashBoard.subMenuList, 15);
       WebElement task = Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']"));
        scrollToElement(task);
        waitFor(2);
        try {
            task.click();
        } catch (StaleElementReferenceException e) {
            Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']")).click();
        }
        waitFor(2);
        CRM_GeneralTaskStepDef.taskValues.get().put("taskType", taskName);
    }

    @Then("I verify create service request fields displayed")
    public void verifyFieldsInCreateServiceRequest() {
        waitFor(2);
        sa.get().assertThat(createGeneralTask.lblCaseId.getText()).as("Case Id is not -- --").isEqualTo("-- --");
        sa.get().assertThat(createGeneralTask.lblConsumerId.getText()).as("Consumer Id is not -- --").isEqualTo("-- --");
        sa.get().assertThat(isElementDisplayed(createGeneralTask.lblDueDate)).as("Due Date is not empty").isTrue();
        sa.get().assertThat(createGeneralTask.lblDueIn.getText()).as("Due In is not match").isEqualTo("3 Business Days");
        sa.get().assertThat(createGeneralTask.lblSource.getText()).as("Source is not match").isEqualTo("User");
        sa.get().assertThat(createGeneralTask.lblStatusDate.getText()).as("Status Date is not current date").isEqualTo(getCurrentDate());
        sa.get().assertThat(createGeneralTask.lstTaskType.getText()).as("Task Type is mismatch").isEqualTo("General Service Request");
        sa.get().assertThat(createGeneralTask.lstTaskPriority.getText()).as("Priority is mismatch").isEqualTo("3");
        sa.get().assertThat(isElementDisplayed(createGeneralTask.txtTaskInfo)).as("Task Info is mismatch").isTrue();
        sa.get().assertThat(createGeneralTask.txtTaskStatus.getText()).as("Status is mismatch").isEqualTo("Open");
        sa.get().assertThat(isElementDisplayed(createGeneralTask.btnSave)).as("Save button is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.btnCancel)).as("Cancel button is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(createGeneralTask.disposition)).as("Disposition field is displayed").isFalse();
    }

    @Then("I verify Status drop down is not editable field")
    public void verifyApplyFilterDDisDisabled() {
        sa.get().assertThat(createGeneralTask.taskStatus.getAttribute("aria-disabled")).as("Status drop down is not disabled").isEqualTo("true");
    }

    @Then("I verify only active service request is displayed in create SR link")
    public void verifyOnlyActiveServiceRequestIsDisplayed() {
        waitForVisibility(dashBoard.btnMenuList, 20);
        click(dashBoard.btnMenuList);
        waitForVisibility(dashBoard.createTaskMenu, 10);
        dashBoard.createSRMenu.click();
        waitForVisibility(dashBoard.subMenuList, 10);
        synchronized (displayedInOrder){
            displayedInOrder.set(new ArrayList<>());
        }

        for(int i=0;i<createGeneralTask.taskTypeList.size();i++){
            displayedInOrder.get().add(createGeneralTask.taskTypeList.get(i).getText().toLowerCase().trim());
        }
        Collections.sort(APITaskManagementController.allFieldsValue.get(),String.CASE_INSENSITIVE_ORDER);
        assertEquals(displayedInOrder.get(), APITaskManagementController.allFieldsValue.get(),
                "SR are not Active or SR are not listed in alphabetical order");
        waitFor(2);
        click(createGeneralTask.taskTypeList.get(0));
    }

    @Then("I verify only active service request is displayed in service request drop down in create SR page")
    public void verifyOnlyActiveServiceRequestIsDisplayedInTaskTypeDropDown() {
        waitForVisibility(createGeneralTask.lstTaskType, 10);
        createGeneralTask.lstTaskType.click();
        synchronized (displayedInOrder){
            displayedInOrder.set(new ArrayList<>());
        }
        for(int i=1;i<createGeneralTask.taskTypeListValues.size();i++){
            displayedInOrder.get().add(createGeneralTask.taskTypeListValues.get(i).getText().toLowerCase().trim());
        }
        Collections.sort(APITaskManagementController.allFieldsValue.get(),String.CASE_INSENSITIVE_ORDER);
        assertEquals(displayedInOrder.get(), APITaskManagementController.allFieldsValue.get(),
                "SR are not Active or SR are not listed in alphabetical order");
        actions.moveToElement(createGeneralTask.txtTaskInfo).click().build().perform();
    }

    @When("I will provide following information before creating service request")
    public void provideTheTaskInformation(Map<String, String> data) {
        synchronized (CRM_GeneralTaskStepDef.taskValues){
            CRM_GeneralTaskStepDef.taskValues.set(new HashMap<>());
        }
        waitForVisibility(createSRPage.lstSRPriority, 10);

        if (data.containsKey("taskInfo") && !data.get("taskInfo").isEmpty() && data.get("taskInfo").equals("random")) {
            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", getRandomString(300));
            clearAndFillText(createSRPage.txtSRInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
            sendKeyToTextField(createSRPage.txtSRInfo, getRandomString(45));

            assertEquals(createSRPage.txtSRInfo.getAttribute("value").length(), 300, "Task Information is accepting more then 300 character");
        } else if (data.containsKey("taskInfo") && !data.get("taskInfo").isEmpty()) {
            clearAndFillText(createSRPage.txtSRInfo, data.get("taskInfo"));
            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", data.get("taskInfo"));
        } else if (data.containsKey("taskInfo") && data.get("taskInfo").isEmpty()) {
            clearFiled(createSRPage.txtSRInfo);
            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", "-- --");
        }
    }

    @When("I click on save button in create service request page")
    public void clickSaveButton() {
        waitForVisibility(createGeneralTask.btnSave, 10);
        CRM_GeneralTaskStepDef.taskValues.get().put("startDate", createGeneralTask.lblStatusDate.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("dueDate", createGeneralTask.lblDueDate.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("dueIn", createGeneralTask.lblDueIn.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("taskType", createGeneralTask.lstTaskType.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("priority", createGeneralTask.lstTaskPriority.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("status", createGeneralTask.txtTaskStatus.getText());
        if(LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA")) {
            CRM_GeneralTaskStepDef.taskValues.get().put("consumerID", createGeneralTask.lblConsumerId.getText());
        } else {
            CRM_GeneralTaskStepDef.taskValues.get().put("caseID", createGeneralTask.lblCaseId.getText());
            CRM_GeneralTaskStepDef.taskValues.get().put("consumerID", createGeneralTask.lblConsumerId.getText());
        }
        if(createGeneralTask.txtTaskInfo.getText().isEmpty())
            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo","-- --");
        else
            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", createGeneralTask.txtTaskInfo.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("source", "User");
        CRM_GeneralTaskStepDef.taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
        CRM_GeneralTaskStepDef.taskValues.get().put("created_by", createGeneralTask.userAccountName.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("created_on", createGeneralTask.lblStatusDate.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("srCategory", createGeneralTask.srCategory.getText());
        if (isElementDisplayed(createGeneralTask.lblContactRecordId)) {
            CRM_GeneralTaskStepDef.taskValues.get().put("contactRecord", createGeneralTask.lblContactRecordId.getText());
        }
        //scrollUpRobotKey();
        waitFor(2);
        scrollDownUsingActions(1);
        jsClick(createGeneralTask.btnSave);
        waitFor(3);
    }

    @Then("I verify Success message is displayed for SR")
    public void i_verify_Success_message_is_displayed_for_task() {
        waitForVisibility(createGeneralTask.lblSuccessMessage, 10);
        assertTrue(isElementDisplayed(createGeneralTask.lblSuccessMessage));
        assertTrue(isElementDisplayed(createSRPage.successMessageText));
    }

    @Then("I verify CREATE SERVICE REQUEST header displayed in CSR page")
    public void verifyCreateServiceRequestHeader() {
        waitForVisibility(createSRPage.csrHeader, 5);
        assertTrue(isElementDisplayed(createSRPage.csrHeader));
        assertTrue(isElementDisplayed(createSRPage.csrPage));
        assertEquals(createSRPage.csrPage.getText(), "CREATE SERVICE REQUEST");
    }

    @Then("I will verify following fields exist")
    public void verifyFollowingFieldsExist(List<String> fieldName) {

        for (int i = 0; i < fieldName.size(); i++) {
            assertTrue(isElementDisplayed(Driver.getDriver().findElement(By.xpath
                            ("//*[text()='" + fieldName.get(i) + "']"))),
                    fieldName.get(i) + " does not exist");
        }
    }

    @Then("I will verify following fields not exist")
    public void verifyFollowingFieldsDoesNotExist(List<String> fieldName) {

        for (int i = 0; i < fieldName.size(); i++) {
            try {
                assertFalse(Driver.getDriver().findElement(By.xpath
                                ("//*[text()='" + fieldName.get(i) + "']")).isDisplayed(),
                        fieldName.get(i) + " exists");
            }catch (NoSuchElementException e){
            }
        }
    }

    @When("I click on linked SR Id on task view page")
    public void clickOnLinkedSrId() {
        waitForVisibility(createGeneralTask.linkedSrId, 3);
        click(createGeneralTask.linkedSrId);
        waitFor(1);
    }

    @When("I verify system returns an error {string}")
    public void iVerifySystemReturnsAnErrorMessage(String errorMsg) {
        waitForVisibility(createGeneralTask.lblPopUpErrorMessage, 3);
        assertEquals(createGeneralTask.lblPopUpErrorMessage.getText(), errorMsg,"Duplicate Application id is saved");
    }

    @When("I click on Inbound Correspondence Link from View Task Page")
    public void i_click_on_Inbound_Correspondence_Link_from_View_Task_Page() {
        waitForVisibility(createGeneralTask.linkedInboundInfo.get(0),500);
        click(createGeneralTask.linkedInboundInfo.get(0));
    }

    @When("I click on {string} sr id on Inbound Correspondence Page link section")
    public void i_click_on_SR_ID_Link_from_View_Correspondence_Page(String srType) {
        waitForVisibility(inbondCorrPage.inboundLinkTypes.get(0), 500);
        for (int i = 0; i < inbondCorrPage.inboundLinkTypes.size(); i++) {
            waitFor(3);
            if (inbondCorrPage.inboundLinkTypes.get(i).getText().equals(srType)) {
                click(inbondCorrPage.inboundCIDValues.get(i));
                break;
            }
        }
        waitFor(2);
    }

    @Then("I verify application id format warning message is displayed")
    public void i_verify_application_id_format_warning_message_is_displayed(){
        waitFor(2);
        scrollToElement(createGeneralTask.externalApplicationId);
        sa.get().assertThat(isElementDisplayed(createGeneralTask.applicationIdFormatError)).as("Application id format error is not displayed");

    }
}
