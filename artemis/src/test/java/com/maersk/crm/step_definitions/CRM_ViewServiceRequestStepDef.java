package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.crm.CRMSRViewEditPage;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.LinkedList;
import java.util.List;

import static com.maersk.crm.step_definitions.LoginStepDef.projectName1;
import static org.testng.Assert.*;

public class CRM_ViewServiceRequestStepDef extends BrowserUtils {

    CRMSRViewEditPage srViewEditPage = new CRMSRViewEditPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();

    @Then("I verify the SR details displayed on view SR page")
    public void verifySRDetails() {
        waitFor(7);
        sa.get().assertThat(isElementDisplayed(srViewEditPage.srDetailsTab)).as("View SR Details is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(srViewEditPage.viewServiceRequest)).as("View SR Header is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(srViewEditPage.serviceRequestDetails)).as("SR Details Header is not displayed").isTrue();
        sa.get().assertThat(myTask.lblSource.getText()).as("Source is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("source"));
        sa.get().assertThat(myTask.lblCreatedBy.getText()).as("CreatedBy is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
        sa.get().assertThat(myTask.lblCreatedOn.getText()).as("CreatedOn is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_on"));
        sa.get().assertThat(srViewEditPage.viewSRCategory.getText()).as("SR Category is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("srCategory"));
        sa.get().assertThat(myTask.lblStatusDate.getText()).as("Status Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("startDate"));
        sa.get().assertThat(myTask.lblDueDate.getText()).as("Due Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate"));
        String dueIn = CRM_GeneralTaskStepDef.taskValues.get().get("dueIn");
        if (dueIn.equals("1 Business Days")) {
            dueIn = "1 Business Day";
        }
        waitFor(3);
        sa.get().assertThat(myTask.lblDueIn.getText()).as("Due In is mismatch").isEqualTo(dueIn);
        sa.get().assertThat(srViewEditPage.viewSrType.getText()).as("SR Type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"));
        sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
        sa.get().assertThat(myTask.lblStatus.getText()).as("Status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
        sa.get().assertThat(isElementDisplayed(myTask.lblAssignee)).as("Assignee field is displayed in view SR page").isFalse();
        if (CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").isEmpty() ||
                CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo") == null) {
            sa.get().assertThat(srViewEditPage.viewSRInfo.getText()).as("SR information is mismatch").isEqualTo("-- --");
        } else {
            sa.get().assertThat(srViewEditPage.viewSRInfo.getText()).as("SR Information is mismatch").isEqualToIgnoringCase(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
        }
        /*
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote")) {
            sa.get().assertThat(srViewEditPage.viewSRNote.getText()).as("SR Note is mismatch").isEqualToIgnoringCase(CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
        } else {
            sa.get().assertThat(srViewEditPage.viewSRNote.getText()).as("SR note is mismatch").isEqualTo("-- --");
        }
         */
        for (String key : CRM_GeneralTaskStepDef.taskValues.get().keySet()) {
            switch (key) {
                case "preferredPhone":
                    String phNumber = CRM_GeneralTaskStepDef.taskValues.get().get("preferredPhone").equalsIgnoreCase("-- --") ?
                            myTask.preferredPhoneVlu.getText() : myTask.preferredPhoneVlu.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
                    sa.get().assertThat(phNumber).as("Preferred Phone is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preferredPhone"));
                    break;
                case "complaintAbout":
                    sa.get().assertThat(myTask.cmplAbtValue.getText()).as("Complaint About drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("complaintAbout"));
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
                case "preferredCallBackDate":
                    sa.get().assertThat(myTask.preferredCallBackDateVlu.getText()).as("preferredCallBackDate is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preferredCallBackDate"));
                    break;
                case "preferredCallBackTime":
                    sa.get().assertThat(myTask.preferredCallBackTimeVlu.getText()).as("preferredCallBackTime is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preferredCallBackTime"));
                    break;
                case "contactName":
                    sa.get().assertThat(myTask.contactName.getText()).as("Contact Name value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactName"));
                    break;
                case "escalated":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("escalated"))
                        sa.get().assertThat(myTask.escalatedVlu.isSelected()).as("Escalated checkbox is unchecked").isTrue();
                    else
                        sa.get().assertThat(myTask.escalatedVlu.isSelected()).as("Escalated checkbox is checked").isFalse();
                    break;
                case "escalationReason":
                    sa.get().assertThat(myTask.escalationReasonVlu.getText()).as("Escalated Reason value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("escalationReason"));
                    break;
                case "organizationDD":
                    sa.get().assertThat(myTask.otherEntityVlu.getText()).as("Other Entity is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("organizationDD"));
                    break;
                case "disposition":
                    sa.get().assertThat(myTask.dispositionVlu.getText()).as("Disposition is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("disposition"));
                    break;
                case "reasonForEdit":
                    sa.get().assertThat(myTask.reasonForEdit.getText()).as("Reason For Edit is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForEdit"));
                    break;
                case "applicationType":
                    sa.get().assertThat(myTask.applicationType.getText()).as("applicationType note is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"));
            }
        }
    }

    @Then("I verify the updated information in view sr details page")
    public void verifyMUpdatedInfoInViewTaskDetailsPage() {
        waitFor(2);
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("priority")) {
            sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("status")) {
            sa.get().assertThat(myTask.lblStatus.getText()).as("Status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskInfo")) {
            sa.get().assertThat(srViewEditPage.viewSRInfo.getText()).as("Task Information is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
        } else
            sa.get().assertThat(srViewEditPage.viewSRInfo.getText()).as("Task info is mismatch").isEqualTo("-- --");
       /*
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote")) {
            sa.get().assertThat(srViewEditPage.viewSRNote.getText()).as("taskNote is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
        } else {
            sa.get().assertThat(srViewEditPage.viewSRNote.getText()).as("Task note is mismatch").isEqualTo("-- --");
        }
        */
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("externalApplicationId")) {
            try {
                sa.get().assertThat(myTask.exAppIdValue.getText()).as("External Application Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
            } catch (Exception e) {
                sa.get().assertThat(myTask.appIdValue.getText()).as("External Application Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
            }
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("documentDueDate")) {
            sa.get().assertThat(myTask.documentDueDate.getText()).as("Due Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("documentDueDate"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("disposition")) {
            sa.get().assertThat(myTask.dispositionVlu.getText()).as("Disposition is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("disposition"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForEdit")) {
            sa.get().assertThat(myTask.reasonForEdit.getText()).as("Reason For Edit is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForEdit"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("applicationType")) {
            sa.get().assertThat(myTask.applicationType.getText()).as("applicationType note is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("denialReason")) {
            sa.get().assertThat(myTask.denialReason.getText()).as("denialReason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("denialReason"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("renewalProcessingDueDate")){
            sa.get().assertThat(myTask.renewalProcessingDueDateValue.getText()).as("renewalProcessingDueDate is mismatch").isEqualTo(getFutureDateInFormat("29"));
        }
    }

    @Then("I verify should I navigated to view SR page")
    public void verifyNavigateBackToViewSRDetailsPage() {
        waitForVisibility(srViewEditPage.serviceRequestDetails, 30);
        assertTrue(isElementDisplayed(srViewEditPage.viewServiceRequest), "View SR Header is not displayed");
        assertTrue(isElementDisplayed(srViewEditPage.serviceRequestDetails), "SR Details Header is not displayed");
        assertTrue(isElementDisplayed(srViewEditPage.btnEditSR));
    }

    @Then("I verify should I navigate to service request details")
    public void verifyUserNaviagtedToSrPageDetails() {
        waitForVisibility(srViewEditPage.serviceRequestDetails, 30);
        sa.get().assertThat(isElementDisplayed(srViewEditPage.serviceRequestDetails)).as("SR Details Header is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(srViewEditPage.btnEditSR)).as("Edit SR Button is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(srViewEditPage.viewSRCategory)).as("SRCategory is not displayed").isTrue();
//        sa.get().assertThat(isElementDisplayed(srViewEditPage.viewSRNote)).as("SR Note is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(myTask.lblCreatedBy)).as("CreatedBy is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(myTask.lblCreatedOn)).as("CreatedOn is not displayed").isTrue();
        sa.get().assertThat(isElementDisplayed(myTask.lblSource)).as("Source is not displayed").isTrue();
    }

    @Then("I verify the sr status is updated to close and disposition is set to {string}")
    public void verifySRStatusUpdateToCloseAndDisposition(String disposition) {
        CRM_GeneralTaskStepDef.taskValues.get().put("srStatus", "Closed");
        waitForVisibility(myTask.dispositionVlu, 500);
        if(!projectName1.get().equalsIgnoreCase("DC-EB") && !srViewEditPage.viewSrType.getText().equalsIgnoreCase("Auto Assignment SR") && !createGeneralTask.linkrowsize.isEmpty()){
            sa.get().assertThat(createGeneralTask.taskLinkInfo.get(4).getText().equalsIgnoreCase("Complete") ||
                    createGeneralTask.taskLinkInfo.get(4).getText().equalsIgnoreCase("Cancelled") ||
                    createGeneralTask.taskLinkInfo.get(4).getText().equalsIgnoreCase("Created") &&  createGeneralTask.taskLinkInfo.get(2).getText().equalsIgnoreCase("State Escalated Complaint")).as("Status is not completed/cancelled/Created").isTrue();
        }
        sa.get().assertThat(myTask.lblStatus.getText()).as("Status is mismatch").isEqualTo("Closed");
        sa.get().assertThat(myTask.dispositionVlu.getText()).as("Disposition is mismatch").isEqualTo(disposition);
        sa.get().assertThat(myTask.dueIn.getText()).as("Due in days is mismatch").isEqualTo("-- --");
    }

    @Then("I verify all linked tasks are updated to Cancelled")
    public void i_verify_all_linked_tasks_are_updated_to_Cancelled() {
        int numberOfTasks = 0;
        for (int i = 0; i < createGeneralTask.contactLinkrowsize.size(); i++) {
            if (createGeneralTask.linkedNames.get(i).getText().equalsIgnoreCase("Task")) {
                numberOfTasks++;
            }
        }
        System.out.println("Number of tasks: " + numberOfTasks);

        for (int i = 0; i < createGeneralTask.contactLinkrowsize.size(); i++) {
            if (numberOfTasks > 1 && createGeneralTask.linkedNames.get(i).getText().equals("Task")){
                System.out.println(i+" task: "+createGeneralTask.linkedStatues.get(i).getText());
                assertTrue(createGeneralTask.linkedStatues.get(i).getText().equals("Complete") ||
                        createGeneralTask.linkedStatues.get(i).getText().equals("Cancelled"));
            }else if (numberOfTasks==1 && createGeneralTask.linkedNames.get(i).getText().equals("Task")){
                sa.get().assertThat(createGeneralTask.linkedStatues.get(i).getText()).as("Status is not Cancelled").isEqualTo("Cancelled");
            } else{
                sa.get().fail("Task isnt found for status verification");
            }
        }
    }

    @Then("I Verify Task Notes field is not displayed on View SR page")
    public void i_verify_Task_Notes_field_is_not_displayed_on_View_SR_page() {
        sa.get().assertThat(isElementDisplayed(srViewEditPage.viewSRNote)).as("Task Notes is present in View task ui").isFalse();
    }
    @And("I verify Task Notes field is not displayed on Edit SR page")
    public void i_verify_Task_Notes_field_is_not_displayed_on_Edit_SR_page() {
        sa.get().assertThat(isElementDisplayed(srViewEditPage.viewSRNote)).as("Task Notes is present in Edit task ui").isFalse();
    }

    @Then("I verify the {string} updated to {string}")
    public void verifyFieldUpdatedOnTheSR(String field,String value) {
        waitFor(2);
        switch (field){
            case "MISSING INFO REQUIRED":
                sa.get().assertThat(myTask.missingInfoRequiredTxt.getText()).as(field + " is not updated to " + value).isEqualTo(value);
                break;
            case "STATUS":
                sa.get().assertThat(myTask.status.getText()).as(field + " is not updated to " + value).isEqualTo(value);
                break;
            case "DISTRICT DISPOSITION":
                sa.get().assertThat(myTask.districtDisposition.getText()).as(field + " is not updated to " + value).isEqualTo(value);
                break;
            case "INBOUND CORRESPONDENCE TYPE":
                sa.get().assertThat(myTask.inboundCorrespondenceTypeVlu.getText()).as(field + " is not updated to " + value).isEqualTo(value);
                break;
            case "CURRENT ESCALATION TYPE":
                sa.get().assertThat(myTask.currentEscalationType.getText()).as(field + " is not updated to " + value).isEqualTo(value);
                break;
            default: throw new IllegalArgumentException("Incorrect argument : " + field);
        }
    }

    @Then("I verify {string} field is not displayed on Edit SR Page")
    public void i_verify_field_is_not_displayed_on_Edit_SR_page(String field) {
        switch (field) {
            case "Approval Reason":
                 sa.get().assertThat(isElementDisplayed(createGeneralTask.approvalReason)).as("Approval reason field is present in View SR Page").isFalse();
                 break;
            default: throw new IllegalArgumentException("Incorrect argument : " + field);
        }
    }
}

