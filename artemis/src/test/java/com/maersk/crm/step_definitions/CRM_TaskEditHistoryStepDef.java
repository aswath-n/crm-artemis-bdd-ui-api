package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CRM_TaskEditHistoryStepDef extends BrowserUtils {
    CRMTaskEditHistoryPage editHistory = new CRMTaskEditHistoryPage();
    CRMSRViewEditPage srViewEditPage = new CRMSRViewEditPage();
    CRMApplicationSearchPage crmSearchApplicationPage = new CRMApplicationSearchPage();
    CRMSavedTaskSearchPage savedTaskSrchPage = new CRMSavedTaskSearchPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearch=new CRMManualCaseConsumerSearchPage();
    public final ThreadLocal<String> pageNameCache = ThreadLocal.withInitial(String::new);

    @Then("I navigated to the Edit History tab ON Edit Task")
    public void InavigatedtotheEditHistorytabONEditTask() {
        jsClick(editHistory.editHistoryTab);
        sa.get().assertThat(isElementDisplayed(editHistory.taskEditHistory)).as("TASK EDIT HISTORY NOT DISPLAYED").isTrue();
    }

    @Then("Validate Edit History shows {int} records at a time with standard pagination controls")
    public void validateEditHistoryShowsRecordsAtATimeWithStandardPaginationControls(int size) {
        sa.get().assertThat(editHistory.listEditedOn).as("TASK EDIT HISTORY NOT DISPLAYED").hasSize(size);
        sa.get().assertThat(isElementDisplayed(editHistory.scndPag)).as("Pagination NOT DISPLAYED").isTrue();
        sa.get().assertThat(isElementDisplayed(editHistory.scndPag)).as("Arrow Forward NOT DISPLAYED").isTrue();
    }


    @And("Verify Edit History table reflects both transactions as one for LINKandUNLINK with ConsumerIds")
    public void verifyEditHistoryTableReflectsBothTransactionsAsOneForLINKUNLINKWithConsumerIds(List<String> conIds) {
        sa.get().assertThat(editHistory.reasonForEditVlu.get(0).getText().equalsIgnoreCase("Link")).as("Reason For Edit Field NOT DISPLAYED").isTrue();
        sa.get().assertThat(editHistory.reasonForEditVlu.get(1).getText().equalsIgnoreCase("Unlink")).as("Reason For Edit Field NOT DISPLAYED").isTrue();
        sa.get().assertThat(editHistory.fieldEditHistory.get(0).getText().equalsIgnoreCase("CONSUMER")).as("Field NOT DISPLAYED").isTrue();
        sa.get().assertThat(editHistory.fieldEditHistory.get(1).getText().equalsIgnoreCase("CONSUMER")).as("Field NOT DISPLAYED").isTrue();
        sa.get().assertThat(editHistory.previousEditHistory.get(0).getText().equalsIgnoreCase("-- --")).as("Previous Field NOT DISPLAYED").isTrue();
        sa.get().assertThat(editHistory.previousEditHistory.get(1).getText().equalsIgnoreCase(conIds.get(0))).as("Previous Field NOT DISPLAYED").isTrue();
        sa.get().assertThat(editHistory.updatedEditHistory.get(0).getText().equalsIgnoreCase(conIds.get(1))).as("Updated Field NOT DISPLAYED").isTrue();
        sa.get().assertThat(editHistory.updatedEditHistory.get(1).getText().equalsIgnoreCase("-- --")).as("Updated Field NOT DISPLAYED").isTrue();
    }

    @Then("I verify edit history page has all the edited data information")
    public void iVerifyWasAbleUpdatedFieldsOnEditHistoryTab() {
        String editOn=CRM_EditTaskStepDef.editHistory.get().get("editOn").split(" ")
                  [0]+CRM_EditTaskStepDef.editHistory.get().get("editOn").split(" ")
                  [1]+CRM_EditTaskStepDef.editHistory.get().get("editOn").split(" ")
                  [2]+CRM_EditTaskStepDef.editHistory.get().get("editOn").split(" ")
                  [3];
        System.out.println("Edit History: " + CRM_EditTaskStepDef.editHistory);

        for (int p = 0; p < editHistory.fieldEditHistory.size(); p++){
            if (editHistory.fieldEditHistory.get(p).getText().equalsIgnoreCase("TASK")) {
                sa.get().assertThat(editHistory.reasonForEditVlu.get(p).getText()).as("Reason For Edit is Mismatch").isEqualTo("Link");
                sa.get().assertThat(editHistory.listEditedBy.get(p).getText()).as("Edited By is wrong").isEqualTo("Task Mgmt Service");
                String createdOn = editHistory.listEditedOn.get(p).getText().replaceAll(" "," ").split(" ")[0];
                sa.get().assertThat(createdOn).as("Edited on is wrong").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_on"));
            } else {
                sa.get().assertThat(editHistory.reasonForEditVlu.get(p).getText()).as("Reason For Edit is Mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForEdit"));
                sa.get().assertThat(editHistory.listEditedBy.get(p).getText()).as("Edited By is wrong").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
                sa.get().assertThat(editHistory.listEditedOn.get(p).getText().replaceAll(" ","")).as("Edited on is wrong").isEqualTo(editOn.replaceAll(" ",""));
            }

            switch (editHistory.fieldEditHistory.get(p).getText()){
               case "Status":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("status"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
                   CRM_EditTaskStepDef.editHistory.get().remove("status");
                   break;
               case "Assignee":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("assignee"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
                   CRM_EditTaskStepDef.editHistory.get().remove("assignee");
                   break;
               case "Task Information":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("taskInfo"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                   CRM_EditTaskStepDef.editHistory.get().remove("taskInfo");
                   break;
               case "Reason For Hold":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("reasonForOnHold"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForOnHold"));
                   CRM_EditTaskStepDef.editHistory.get().remove("reasonForOnHold");
                   break;
               case "Priority":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("priority"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
                   CRM_EditTaskStepDef.editHistory.get().remove("priority");
                   break;
               case "Vacms Case Id":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("externalCaseId"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId"));
                   CRM_EditTaskStepDef.editHistory.get().remove("externalCaseId");
                   break;
               case "Application Id":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("externalApplicationId"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                   CRM_EditTaskStepDef.editHistory.get().remove("externalApplicationId");
                   break;
               case "Connectionpoint Contact Record Id":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("cpCRID"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("cpCRID"));
                   CRM_EditTaskStepDef.editHistory.get().remove("cpCRID");
                   break;
               case "Connectionpoint Sr Id":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("cpSRID"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("cpSRID"));
                   CRM_EditTaskStepDef.editHistory.get().remove("cpSRID");
                   break;
               case "Connectionpoint Task Id":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("cpTaskID"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("cpTaskID"));
                   CRM_EditTaskStepDef.editHistory.get().remove("cpTaskID");
                   break;
               case "Contact Reason":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("contactReason"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactReason"));
                   CRM_EditTaskStepDef.editHistory.get().remove("contactReason");
                   break;
               case "Due Date":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("documentDueDate"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("documentDueDate"));
                   CRM_EditTaskStepDef.editHistory.get().remove("documentDueDate");
                   break;
               case "Action Taken":
                   if(CRM_EditTaskStepDef.editHistory.get().get("actionTakenSingle")!=null){
                       sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("actionTakenSingle"));
                       sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("actionTakenSingle"));
                       CRM_EditTaskStepDef.editHistory.get().remove("actionTakenSingle");
                   }else if(CRM_EditTaskStepDef.editHistory.get().get("actionTknSingle")!=null){
                       sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("actionTknSingle"));
                       sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("actionTknSingle"));
                       CRM_EditTaskStepDef.editHistory.get().remove("actionTknSingle");
                   }
                   else{
                       sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("actionTaken"));
                       sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken"));
                       CRM_EditTaskStepDef.editHistory.get().remove("actionTaken");
                   }
                   break;
               case "Disposition":
                   sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("disposition"));
                   sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("disposition"));
                   CRM_EditTaskStepDef.editHistory.get().remove("disposition");
                   System.out.println(CRM_EditTaskStepDef.editHistory);
                   break;
                case "Reason For Cancel":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("reasonForCancel"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel"));
                    CRM_EditTaskStepDef.editHistory.get().remove("reasonForCancel");
                    break;

                case "Case Worker First Name":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("caseWorkerFirstName"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseWorkerFirstName"));
                    CRM_EditTaskStepDef.editHistory.get().remove("caseWorkerFirstName");
                    break;

                case "Case Worker Last Name":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("caseWorkerLastName"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseWorkerLastName"));
                    CRM_EditTaskStepDef.editHistory.get().remove("caseWorkerLastName");
                    break;

                case "Decision Source":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("decisionSource"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("decisionSource"));
                    CRM_EditTaskStepDef.editHistory.get().remove("decisionSource");
                    break;

                case "Denial Reason":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("denialReason"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("denialReason"));
                    CRM_EditTaskStepDef.editHistory.get().remove("denialReason");
                    break;

                case "Missing Info Required?":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("missingInfoRequired"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("missingInfoRequired"));
                    CRM_EditTaskStepDef.editHistory.get().remove("missingInfoRequired");
                    break;

                case "# Of Applicants In Household":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("noOfApplicantsInHousehold"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("noOfApplicantsInHousehold"));
                    CRM_EditTaskStepDef.editHistory.get().remove("noOfApplicantsInHousehold");
                    break;

                case "# Of Approved Applicants":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("noOfApprovedApplicants"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("noOfApprovedApplicants"));
                    CRM_EditTaskStepDef.editHistory.get().remove("noOfApprovedApplicants");
                    break;

                case "Transfer Reason":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("reason"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reason"));
                    CRM_EditTaskStepDef.editHistory.get().remove("reason");
                    break;

                case "Facility Name":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("facilityName"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("facilityName"));
                    CRM_EditTaskStepDef.editHistory.get().remove("facilityName");
                    break;

                case "Facility Type":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("facilityType"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("facilityType"));
                    CRM_EditTaskStepDef.editHistory.get().remove("facilityType");
                    break;

                case "Document Type":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("InbDocType"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("InbDocType"));
                    CRM_EditTaskStepDef.editHistory.get().remove("InbDocType");
                    break;

                case "Locality":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("locality"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("locality"));
                    CRM_EditTaskStepDef.editHistory.get().remove("locality");
                    break;

                case "Channel":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("channel"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("channel"));
                    CRM_EditTaskStepDef.editHistory.get().remove("channel");
                    break;

                case "Application Sub Status":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("applicationStatus"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationStatus"));
                    CRM_EditTaskStepDef.editHistory.get().remove("applicationStatus");
                    break;

                case "Application Type":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("applicationType"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"));
                    CRM_EditTaskStepDef.editHistory.get().remove("applicationType");
                    break;

                case "TASK":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo("-- --");
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo((Integer.parseInt(CRM_TaskManagementStepDef.taskId.get()) + 1) + "");
                    break;

                case "Task Notes":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("taskNote"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
                    CRM_EditTaskStepDef.editHistory.get().remove("taskNote");
                    break;

                case "Renewal Processing Due Date":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("renewalProcessingDueDate"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("renewalProcessingDueDate"));
                    CRM_EditTaskStepDef.editHistory.get().remove("renewalProcessingDueDate");
                    break;

                case "Renewal Date":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("renewalDate"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("renewalDate"));
                    CRM_EditTaskStepDef.editHistory.get().remove("renewalDate");
                    break;

                case "Renewal Cutoff Date":
                    sa.get().assertThat(editHistory.previousEditHistory.get(p).getText()).as("Previous Value is mismatch").isEqualTo(CRM_EditTaskStepDef.editHistory.get().get("renewalCutoffDate"));
                    sa.get().assertThat(editHistory.updatedEditHistory.get(p).getText()).as("Updated Value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("renewalCutoffDate"));
                    CRM_EditTaskStepDef.editHistory.get().remove("renewalCutoffDate");
                    break;

                default:
                   sa.get().fail("Switch case is not present for key "+editHistory.fieldEditHistory.get(p).getText());
                   break;
           }
        }
        CRM_EditTaskStepDef.editHistory.get().remove("editOn");
        sa.get().assertThat(CRM_EditTaskStepDef.editHistory.get().keySet()).as("All updated records are not present is edit history").hasSize(0);
    }

    @Then("I navigated to the Task Details tab ON Edit Task")
    public void InavigatedtotheTaskDetailsTabONEditTask() {
        jsClick(editHistory.taskDetailsTab);
    }

    @Then("I navigated to the sr Details tab ON Edit Task")
    public void InavigatedtotheSrDetailsTabONEditTask() {
        jsClick(srViewEditPage.srDetailsTab);
    }

    //refactored to make it dynamic, based on pageName and dd value
    @Then("I select records count in pagination dropdown as {string} in {string} page")
    public void i_select_Item_Count_in_search_result_page_as(String itemCount, String pageName) {
        pageNameCache.set(pageName);
        waitFor(3);
        switch (pageName) {
            case "My Task":
            case "Work Queue":
                myTask.paginationDD.click();
                crmSearchApplicationPage.itemCountList.stream().filter(x -> x.getText().equalsIgnoreCase(itemCount)).findFirst().ifPresent(WebElement::click);
                break;
            case "Case Consumer Task Search":
                contactRecord.resultsOptions.click();
                crmSearchApplicationPage.itemCountList.stream().filter(x -> x.getText().equalsIgnoreCase(itemCount)).findFirst().ifPresent(WebElement::click);
                break;
            case "Case Consumer Search":
                manualCaseConsumerSearch.pageDD.click();
                crmSearchApplicationPage.itemCountList.stream().filter(x -> x.getText().equalsIgnoreCase(itemCount)).findFirst().ifPresent(WebElement::click);
                break;
            case "Task Search":
            case "Edit History":
                savedTaskSrchPage.pageNationDD.click();
                crmSearchApplicationPage.itemCountList.stream().filter(x -> x.getText().equalsIgnoreCase(itemCount)).findFirst().ifPresent(WebElement::click);
                break;
        }
    }
}
