package com.maersk.crm.step_definitions;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_TaskBulkUpdateStepDef extends CRMUtilities implements ApiBase {
    CRMBulkUpdatePage bulkUpdatePage = new CRMBulkUpdatePage();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMTaskNoteComponentPage noteComp = new CRMTaskNoteComponentPage();

    public static final ThreadLocal<List<String>> bulkUpdateIDs = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<HashMap<String, String>> bulkUpdate = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<Multimap<String, String>> multimap = ThreadLocal.withInitial(ArrayListMultimap::create);
    public static final ThreadLocal<Multimap<String, String>> multimap1 = ThreadLocal.withInitial(ArrayListMultimap::create);
    public static final ThreadLocal<List<String>> assignee = ThreadLocal.withInitial(ArrayList::new);

    @And("Click on Bulk Update button")
    public void clickOnbulkUpdateButton(){
        click(bulkUpdatePage.bulkUpdateBtn);
        waitFor(5);
    }

    @And("I verify Task Ids in Bulk Update Screen")
    public void verifyTaskIdsInBulkUpdateScreen(){
        int size = 1;
        if (isElementDisplayed(taskSearchPage.lnkArrowForward)){
            click(taskSearchPage.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskSearchPage.lnkPageNations.get(taskSearchPage.lnkPageNations.size()-1).getText());
            click(taskSearchPage.lnkArrowBack);
            waitFor(1);
        }
        assertTrue(size<=20, "More then 100 records are selected");
        for (int i = 1; i <= size; i++){
            for (int j = 0; j < bulkUpdatePage.taskIdBulkUpdate.size(); j++){
                bulkUpdateIDs.get().add(bulkUpdatePage.taskIdBulkUpdate.get(j).getText());
                assertFalse(CRM_TaskSearchStepDef.uncheckedTaskId.get().contains(bulkUpdateIDs.get()),"Selected records is displaying");
            }
            if (i != size){
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }
    @And("I verify All Selected Task Ids in Bulk Update Screen")
    public void verifyAllSelectedTaskIdsInBulkUpdateScreen() {
        int size = 1;
        if (isElementDisplayed(taskSearchPage.lnkArrowForward)) {
            click(taskSearchPage.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskSearchPage.lnkPageNations.get(taskSearchPage.lnkPageNations.size() - 1).getText());
            click(taskSearchPage.lnkArrowBack);
            waitFor(1);
        }
        assertTrue(size<=20, "More then 100 records are selected");
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < bulkUpdatePage.taskIdBulkUpdate.size(); j++) {
                bulkUpdateIDs.get().add(bulkUpdatePage.taskIdBulkUpdate.get(j).getText());
                assertTrue(CRM_TaskSearchStepDef.checkedTaskId.get().contains(bulkUpdateIDs.get()), "Selected records is displaying");
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @And("I verify bulk update button is {string}")
    public void verifyBulkUpdateTaskButtonIsDisable(String functionality){
        if(functionality.equalsIgnoreCase("disable"))
            sa.get().assertThat(bulkUpdatePage.bulkUpdateBtn.isEnabled()).as("Bulk Update Button is enable").isFalse();
        else
            sa.get().assertThat(bulkUpdatePage.bulkUpdateBtn.isEnabled()).as("Bulk Update Button is disable").isTrue();
    }

    @And("I select main check box")
    public void selectMainCheckBox(){
        taskSearchPage.mainCheckbox.click();
    }

    @And("I click on Next button in bulk update screen")
    public void clickOnNextButton(){
        click(bulkUpdatePage.nextBtn);
    }

    @And("I update following information in bulk update screen")
    public void i_will_update_the_following_information_in_bulk_update_page(Map<String, String> data) {
        //waitForVisibility(bulkUpdatePage.priorityDD, 12);
        for (String key : data.keySet()) {
            switch (key) {
                case "priority":
                    selectDropDown(bulkUpdatePage.priorityDD,data.get(key));
                    bulkUpdate.get().put("priority",data.get(key));
                    break;
                case "assignee":
                    selectDropDown(bulkUpdatePage.assigneeDD,data.get(key));
                    bulkUpdate.get().put("assignee",data.get(key));
                    break;
                case "note":
                    if (data.containsKey("note") && !data.get("note").isEmpty() && data.get("note").equals("String")) {
                        bulkUpdate.get().put("note", getRandomString(50));
                        sendKeyToTextField(bulkUpdatePage.noteTxt,bulkUpdate.get().get("note"));
                    } else if (data.containsKey("note") && !data.get("note").isEmpty() && data.get("note").equals("Number")) {
                        bulkUpdate.get().put("note", getRandomNumber(50));
                        sendKeyToTextField(bulkUpdatePage.noteTxt,bulkUpdate.get().get("note"));
                    } else if (data.containsKey("note") && !data.get("note").isEmpty() && data.get("note").equals("String + Number")) {
                        bulkUpdate.get().put("note", getRandomString(900)  + getRandomNumber(100));
                        sendKeyToTextField(bulkUpdatePage.noteTxt,bulkUpdate.get().get("note"));
                    }  else if (data.containsKey("note") && !data.get("note").isEmpty() && data.get("note").equals("Line Break")){
                        bulkUpdate.get().put("note", "abd\n" + "xyz\n" + "345\n" + "ABC1234%\n" + "ASHJ_9087");
                        sendKeyToTextField(bulkUpdatePage.noteTxt, bulkUpdate.get().get("note"));
                    }else if (data.containsKey("note") && !data.get("note").isEmpty()) {
                        sendKeyToTextField(bulkUpdatePage.noteTxt, data.get("note"));
                        bulkUpdate.get().put("note", data.get("note"));
                    }
                    bulkUpdate.get().put("note",data.get(key));
                    break;
                default:
                    sa.get().fail("Switch case not match");
            }
        }
    }

    @And("I click on cancel button in bulk update task page")
    public void clickOnCancelButton(){
        click(bulkUpdatePage.cancelBtn);
    }

    @Then("I select {int} pages records check box")
    public void verifyMainCheckboxIsSelected(int pages) {
        assignee.set(new ArrayList<>());
        if (taskSearchPage.pageNationDD.getText().equals("show 20"))
            selectDropDown(taskSearchPage.pageNationDD, "show 5");
        for (int i = 1; i <= pages; i++){
            for (int j = 0; j < taskSearchPage.taskIDs.size(); j++){
                waitFor(1);
                multimap.get().put("taskId",taskSearchPage.taskIDs.get(j).getText());
                multimap.get().put("taskType",taskSearchPage.taskTypes.get(j).getText());
                multimap.get().put("priority",taskSearchPage.taskPriority.get(j).getText());
                switch (LoginStepDef.projectName1.get()){
                    case "CoverVA":
                        multimap.get().put("status",taskSearchPage.taskStatuses.get(j).getText());
                        multimap.get().put("statusDate",taskSearchPage.taskStatusDate.get(j).getText());
                        multimap.get().put("dueDate",taskSearchPage.taskDueDateDate.get(j).getText());
                        multimap.get().put("myWorkSpaceDate",taskSearchPage.taskDueIns.get(j).getText());
                        multimap.get().put("dueIn",taskSearchPage.taskCaseID.get(j).getText());
                        multimap.get().put("caseId",taskSearchPage.taskConsumerID.get(j).getText());
                        multimap.get().put("consumerId",taskSearchPage.initiateTasks.get(j).getText());
                        multimap.get().put("applicationId",taskSearchPage.initiateTasksForNJ.get(j).getText());
                        break;
                    case "IN-EB":
                    case "BLCRM":
                        multimap.get().put("status",taskSearchPage.taskStatuses.get(j).getText());
                        multimap.get().put("statusDate",taskSearchPage.taskStatusDate.get(j).getText());
                        multimap.get().put("dueDate",taskSearchPage.taskDueDateDate.get(j).getText());
                        multimap.get().put("dueIn",taskSearchPage.taskDueIns.get(j).getText());
                        multimap.get().put("caseId",taskSearchPage.taskCaseID.get(j).getText());
                        multimap.get().put("consumerId",taskSearchPage.taskConsumerID.get(j).getText());
                        break;
                    case "NJ-SBE":
                        multimap.get().put("consumerName",taskSearchPage.taskStatuses.get(j).getText());
                        multimap.get().put("status",taskSearchPage.taskStatusDate.get(j).getText());
                        multimap.get().put("statusDate",taskSearchPage.taskDueDateDate.get(j).getText());
                        multimap.get().put("dueDate",taskSearchPage.taskDueIns.get(j).getText());
                        multimap.get().put("dueIn",taskSearchPage.taskCaseID.get(j).getText());
                        multimap.get().put("caseId",taskSearchPage.taskConsumerID.get(j).getText());
                        multimap.get().put("consumerId",taskSearchPage.initiateTasks.get(j).getText());
                        break;
                }
                jsClick(taskSearchPage.checkbox.get(j));
                if(pages==1){
                    taskSearchPage.expandBtns.get(j).click();
                    assignee.get().add(taskSearchPage.assigneeExpandedView.get(j).getText());
                    taskSearchPage.arrowDownKey.click();
                }
            }
            if (i != pages){
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @Then("I verify all selected records in bulk update screen")
    public void verifyMainCheckboxIsSelected() {
        int size = 1;
        if (isElementDisplayed(taskSearchPage.lnkArrowForward)) {
            click(taskSearchPage.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskSearchPage.lnkPageNations.get(taskSearchPage.lnkPageNations.size() - 1).getText());
            click(taskSearchPage.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++){
            for (int j = 0; j < bulkUpdatePage.taskIdBulkUpdate.size(); j++){
                waitFor(1);
                multimap1.get().put("taskId",bulkUpdatePage.taskIdBulkUpdate.get(j).getText());
                multimap1.get().put("taskType",bulkUpdatePage.taskTypes.get(j).getText());
                multimap1.get().put("priority",bulkUpdatePage.priorities.get(j).getText());
                switch (LoginStepDef.projectName1.get()){
                    case "CoverVA":
                        multimap1.get().put("status",bulkUpdatePage.statuses.get(j).getText());
                        multimap1.get().put("statusDate",bulkUpdatePage.statusDates.get(j).getText());
                        multimap1.get().put("dueDate",bulkUpdatePage.dueDates.get(j).getText());
                        multimap1.get().put("myWorkSpaceDate",bulkUpdatePage.myWorkSpaceDates.get(j).getText());
                        multimap1.get().put("dueIn",bulkUpdatePage.dueIns.get(j).getText());
                        multimap1.get().put("caseId",bulkUpdatePage.caseIds.get(j).getText());
                        multimap1.get().put("consumerId",bulkUpdatePage.consumerIds.get(j).getText());
                        multimap1.get().put("applicationId",bulkUpdatePage.applicationIds.get(j).getText());
                        break;
                    case "IN-EB":
                    case "BLCRM":
                        multimap1.get().put("status",bulkUpdatePage.statuses.get(j).getText());
                        multimap1.get().put("statusDate",bulkUpdatePage.statusDates.get(j).getText());
                        multimap1.get().put("dueDate",bulkUpdatePage.dueDates.get(j).getText());
                        multimap1.get().put("dueIn",bulkUpdatePage.myWorkSpaceDates.get(j).getText());
                        multimap1.get().put("caseId",bulkUpdatePage.dueIns.get(j).getText());
                        multimap1.get().put("consumerId",bulkUpdatePage.caseIds.get(j).getText());
                        break;
                    case "NJ-SBE":
                        multimap1.get().put("consumerName",bulkUpdatePage.statuses.get(j).getText());
                        multimap1.get().put("status",bulkUpdatePage.statusDates.get(j).getText());
                        multimap1.get().put("statusDate",bulkUpdatePage.dueDates.get(j).getText());
                        multimap1.get().put("dueDate",bulkUpdatePage.myWorkSpaceDates.get(j).getText());
                        multimap1.get().put("dueIn",bulkUpdatePage.dueIns.get(j).getText());
                        multimap1.get().put("caseId",bulkUpdatePage.caseIds.get(j).getText());
                        multimap1.get().put("consumerId",bulkUpdatePage.consumerIds.get(j).getText());
                        break;
                }
            }
            if (i != size){
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertEquals(multimap,multimap1,"Records are not match in bulk update task page");
    }

    @And("I click on confirm bulk update button")
    public void clickOnBulkUpdateButton(){
        click(bulkUpdatePage.confirmBulkUpdatesBtn);
    }

    @And("I verify system displays Success Message")
    public void verifySuccessMsg(){
        assertTrue(isElementDisplayed(bulkUpdatePage.bulkUpdateSuccessMsg),"Bulk update success message is not displayed");
        assertTrue(isElementDisplayed(bulkUpdatePage.successMsg),"Task update success message is not displayed");
    }

    @And("I click on back to task search button")
    public void clickOnBackToTaskSearchBtn(){
        bulkUpdatePage.backToTaskSearchBtn.click();
        waitFor(3);
        if(isElementDisplayed(dashboard.warningPopupContinueBtn))
            dashboard.warningPopupContinueBtn.click();
    }

    @And("I verify all task are updated with {string}")
    public void i_will_update_the_following_information_in_bulk_update_page(String type) {
        //waitForVisibility(bulkUpdatePage.priorityDD, 12);
        int i=0;
        ArrayList<String> priority = new ArrayList<>(multimap.get().get("priority"));
        System.out.println(multimap.get().get("taskId"));
        for (String taskId:multimap.get().get("taskId")) {
            click(taskSearchPage.cancelBtn);
            taskSearchPage.taskId.sendKeys(taskId);
            click(taskSearchPage.searchBtn);
            waitFor(2);
            click(taskSearchPage.taskIDs.get(0));
            waitFor(2);
            switch (type){
                case "Only Assignee":
                    sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is miss match for task "+taskId).isEqualTo(priority.get(i));
                    sa.get().assertThat(myTask.lblAssignee.getText()).as("Assignee Value is wrong for task "+taskId).isEqualTo(bulkUpdate.get().get("assignee").replace(" null",""));
                    break;
                case "Only Priority":
                    sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is miss match for task "+taskId).isEqualTo(bulkUpdate.get().get("priority"));
                    sa.get().assertThat(myTask.lblAssignee.getText()).as("Assignee Value is wrong for task "+taskId).isEqualTo(assignee.get().get(i));
                    break;
                case "Only Note":
                    sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is miss match for task "+taskId).isEqualTo(priority.get(i));
                    sa.get().assertThat(myTask.lblAssignee.getText()).as("Assignee Value is wrong for task "+taskId).isEqualTo(assignee.get().get(i));
                    sa.get().assertThat(noteComp.noteTxtValue.get(0).getText()).as("Note component value is wrong for taskId "+taskId).isEqualToIgnoringCase(bulkUpdate.get().get("note"));
                    break;
                case "Assignee,Priority and Note":
                    break;
                default:
                    sa.get().fail("Switch case is not match");
                    sa.get().assertThat(myTask.lblAssignee.getText()).as("Assignee Value is wrong for task "+taskId).isEqualTo(bulkUpdate.get().get("assignee").replace(" null",""));
                    sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is miss match for task "+taskId).isEqualTo(bulkUpdate.get().get("priority"));
                    sa.get().assertThat(noteComp.noteTxtValue.get(0).getText()).as("Note component value is wrong for taskId "+taskId).isEqualToIgnoringCase(bulkUpdate.get().get("note"));
                    break;
            }
            myTask.backArrowInViewPage.click();
            i++;
        }
    }
    @And("Verify Bulk Update button and CheckBox is displaying")
    public void verifybulkUpdateButtonAndCheckBoxIsDisplaying(){
        waitFor(1);
        sa.get().assertThat(isElementDisplayed(taskSearchPage.mainCheckbox)).as("Check Box is not displaying").isTrue();
        sa.get().assertThat(isElementDisplayed(bulkUpdatePage.bulkUpdateBtn)).as("Bulk Update button is not displaying").isTrue();
    }
    @And("Verify Bulk Update button and CheckBox is not displaying")
    public void verifybulkUpdateButtonAndCheckBoxIsNotDisplaying(){
        waitFor(1);
        sa.get().assertThat(isElementDisplayed(taskSearchPage.mainCheckbox)).as("Check Box is displaying").isFalse();
        sa.get().assertThat(isElementDisplayed(bulkUpdatePage.bulkUpdateBtn)).as("Bulk Update button is displaying").isFalse();
    }
}