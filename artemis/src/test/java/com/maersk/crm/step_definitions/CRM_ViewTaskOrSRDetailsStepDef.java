package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Then;

import static org.testng.Assert.*;

public class CRM_ViewTaskOrSRDetailsStepDef extends CRMUtilities implements ApiBase {

    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
    CRMSRViewEditPage srViewEditPage=new CRMSRViewEditPage();
    final ThreadLocal<CRMViewTaskStepDef> viewTaskStepDef= ThreadLocal.withInitial(CRMViewTaskStepDef::new);

    @Then("I will verify {string} Details view SR details page")
    public void verifyMyTaskDetails(String taskOdSrType) throws Exception {
        sa.get().assertThat(isElementDisplayed(srViewEditPage.srDetailsTab)).as("View SR Details is not displayed").isTrue();
        waitFor(2);
        switch (taskOdSrType){
            case "Application SR":
            case "Application Renewal SR":
            case "Renewal SR":
                sa.get().assertThat(myTask.lblDueDate.getText()).as("Application SR Due Date is did not match").isEqualTo(viewTaskStepDef.get().validateDueDateOfProcessApplication("localDate",taskOdSrType, 1));
                sa.get().assertThat(myTask.lblDueIn.getText().replace("s","").replace("-","")).as("Due In is mismatch").isEqualTo(viewTaskStepDef.get().validateDueIn(taskOdSrType).replace("-","")+" Day");
                sa.get().assertThat(myTask.srType.getText().trim()).as("SR Type is mismatch").isEqualTo(taskOdSrType);
                sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
                sa.get().assertThat(myTask.appIdValue.getText()).as("Application Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                sa.get().assertThat(myTask.applicationType.getText()).as("applicationType note is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"));
                sa.get().assertThat(myTask.decisionSource.getText()).as("decisionSource note is mismatch").isEqualTo("-- --");
                sa.get().assertThat(myTask.srCategory.getText()).as("srCategory  is mismatch").isEqualTo("Application");
                sa.get().assertThat(myTask.applicationID.getText()).as("Application ID value not matches").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                break;

            case "Appeals SR":
                sa.get().assertThat(myTask.lblDueDate.getText()).as("Due Date is mismatch").isNotBlank();
                waitFor(2);
                sa.get().assertThat(myTask.lblDueIn.getText().replaceAll(" ","")).as("Due In is mismatch").isEqualTo("60Days");
                sa.get().assertThat(myTask.srType.getText()).as("SR Type is mismatch").isEqualTo(taskOdSrType);
                sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is mismatch").isEqualTo("3");
                sa.get().assertThat(myTask.appIdValue.getText()).as("Application Id is mismatch").isEqualTo("-- --");
                sa.get().assertThat(myTask.srCategory.getText()).as("srCategory  is mismatch").isEqualTo("Appeal");
                sa.get().assertThat(myTask.documentDueDate.getText()).as("duedate doesnt match").isEqualTo("-- --");
                sa.get().assertThat(myTask.expeditedCheckbox.isSelected()).as("Expedited checkbox is selected").isFalse();
                break;

            case "Complaint SR":
                sa.get().assertThat(myTask.lblDueDate.getText()).as("Due Date is mismatch").isNotBlank();
                sa.get().assertThat(myTask.lblDueIn.getText().replaceAll(" ","")).as("Due In is mismatch").isEqualTo("2BusinessDays");
                sa.get().assertThat(myTask.srType.getText()).as("SR Type is mismatch").isEqualTo(taskOdSrType);
                sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is mismatch").isEqualTo("1");
                sa.get().assertThat(myTask.srCategory.getText()).as("srCategory  is mismatch").isEqualTo("Complaint");
                break;

            case "Appeal":
                sa.get().assertThat(myTask.lblDueDate.getText()).as("Due Date is mismatch").isEqualTo(getGreaterDateFormatMMddyyyy(7));
                sa.get().assertThat(myTask.lblDueIn.getText().replaceAll(" ","")).as("Due In is mismatch").isEqualTo("7Days");
                sa.get().assertThat(myTask.srType.getText()).as("SR Type is mismatch").isEqualTo(taskOdSrType);
                sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is mismatch").isEqualTo("3");
                sa.get().assertThat(myTask.srCategory.getText()).as("srCategory  is mismatch").isEqualTo("Appeal");
                break;
        }
        if (!(CRM_GeneralTaskStepDef.taskValues.get().containsKey("source"))) {
            sa.get().assertThat(myTask.lblSource.getText()).as("Source is mismatch").isEqualTo("System");
        } else {
            sa.get().assertThat(myTask.lblSource.getText()).as("Source is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("source"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("created_by")) {
            sa.get().assertThat(myTask.lblCreatedBy.getText()).as("CreatedBy is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
        } else {
            sa.get().assertThat(myTask.lblCreatedBy.getText().trim()).as("CreatedBy is mismatch").isEqualTo("ECMS Service");
        }
        sa.get().assertThat(myTask.lblStatus.getText()).as("Status is mismatch").isEqualTo("Open");
        sa.get().assertThat(myTask.lblSRInfo.getText()).as("SR information is mismatch").isEqualTo("-- --");
        //sa.get().assertThat(myTask.lblSRNote.getText()).as("SR note is mismatch").isEqualTo("-- --");
        if(!(CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForEdit"))) {
            sa.get().assertThat(myTask.reasonForEdit.getText()).as("reasonForEdit note is mismatch").isEqualTo("-- --");
        }else{
            sa.get().assertThat(myTask.reasonForEdit.getText()).as("reasonForEdit note is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForEdit"));
        }
        sa.get().assertThat(myTask.lblCreatedOn.getText()).as("CreatedOn is mismatch").isEqualTo(createGeneralTask.projectDateAtHeader.getText());
        sa.get().assertThat(myTask.lblStatusDate.getText()).as("Status Date is mismatch").isEqualTo(createGeneralTask.projectDateAtHeader.getText());
    }
    @Then("I verify due in days after close status for SR")
    public void verifyDueInDays() {
        if (isElementDisplayed(srViewEditPage.serviceRequestDetails)) {
                assertEquals(myTask.dueIn.getText(), "-- --", "Due in days is mismatch");
        } else {
            if(LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE")) {
                for (int i = 0; i < taskSearchPage.taskListRows.size(); i++) {
                    assertEquals(taskSearchPage.taskCaseID.get(i).getText(), "-- --", "Due in days is mismatch");
                }
            }else {
                for (int i = 0; i < taskSearchPage.taskListRows.size(); i++) {
                    waitFor(3);
                    assertEquals(taskSearchPage.taskDueIns.get(i).getText(), "-- --", "Due in days is mismatch");
                }
            }
        }
    }
}
