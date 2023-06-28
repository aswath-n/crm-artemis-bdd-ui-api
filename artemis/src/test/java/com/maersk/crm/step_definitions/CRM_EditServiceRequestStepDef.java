package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.crm.CRMSRViewEditPage;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;


import static org.testng.Assert.*;

public class CRM_EditServiceRequestStepDef extends CRMUtilities implements ApiBase {

    CRMSRViewEditPage srViewEditPage =new CRMSRViewEditPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    final ThreadLocal<CRM_GeneralTaskStepDef> generalTask = ThreadLocal.withInitial(CRM_GeneralTaskStepDef::new);

    @And("I click on edit service request button")
    public void clickOnEditButtonInViewSRPage() {
        waitFor(1);
        for (int i = 0; i <= 3 ; i++) {
            jsClick(srViewEditPage.btnEditSR);
            waitFor(2);
            if (!isElementDisplayed(srViewEditPage.btnEditSR)) {
                break;
            }
        }
        sa.get().assertThat(myTask.reasonForEditDropDown.getText()).as("Reason For Edit is not cleared out as per CP-28816").isBlank();
        CRM_GeneralTaskStepDef.taskValues.get().put("reasonForEdit", "-- --");
        scrollUpUsingActions(2);
    }

    @And("I store sr id on edit page")
    public void storeSrIdOnEditPage() {
        waitForVisibility(srViewEditPage.srIdOnViewPage,5);
        CRM_TaskManagementStepDef.srID.set(srViewEditPage.srIdOnViewPage.getText().replaceAll("\\D",""));
    }

    @And("I store sr info on edit page")
    public void storeSrInfoOnEditPage() {
        waitForVisibility(srViewEditPage.viewSRInfo,5);
        String srInfo = srViewEditPage.viewSRInfo.getText();
        System.out.println("Here is your sr info "+srInfo);
        CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo",srInfo) ;
    }

    @Then("I verify success message for update service request")
    public void i_verify_Success_message_is_displayed_for_SR_Update() {
        waitForVisibility(createGeneralTask.lblSuccessMessage, 10);
        assertTrue(isElementDisplayed(createGeneralTask.lblSuccessMessage));
        assertTrue(isElementDisplayed(srViewEditPage.updateSuccessMessageText));
    }
    @And ("I verify status is not editable for closed sr on edit page")
    public void I_verify_fields_are_editable(){
        CRM_GeneralTaskStepDef.taskValues.get().put("status",createGeneralTask.taskStatus.getText());
        CRM_GeneralTaskStepDef.taskValues.get().put("dueDate",createGeneralTask.lblDueDate.getText());
        sa.get().assertThat(createGeneralTask.taskStatus.getAttribute("aria-disabled")).as("Status drop down is not disabled").isEqualTo("true");
        sa.get().assertThat(createGeneralTask.dueDate.isEnabled()).as("Document Due Date is not editable").isTrue();
        sa.get().assertThat(createGeneralTask.disposition.isEnabled()).as("Disposition is not editable").isTrue();
        sa.get().assertThat(myTask.expeditedCheckbox.isEnabled()).as("Expedited is not editable").isTrue();
        sa.get().assertThat(myTask.reasonForEditDropDown.isEnabled()).as("Reason For Edit is not editable").isTrue();
        sa.get().assertThat(createGeneralTask.lstTaskPriority.isEnabled()).as("Priority is not editable").isTrue();
    }

    @Then("I verify Disposition field is display on screen")
    public void I_verify_disposition_field_is_display_on_screen() {
        if (CRM_GeneralTaskStepDef.taskValues.get().get("status").equalsIgnoreCase("Closed")) {
            sa.get().assertThat(isElementDisplayed(createGeneralTask.disposition)).as("Disposition Value is display").isTrue();
        } else
            sa.get().assertThat(isElementDisplayed(createGeneralTask.disposition)).as("Disposition is present when status is open").isFalse();
    }

    public void validateDateOrTextFiledDisableAndClearOut(WebElement ele, String fieldName){
        sa.get().assertThat(ele.isEnabled()).as(fieldName+ " is not disable").isFalse();
        sa.get().assertThat(ele.getAttribute("value")).as(fieldName+" is not cleared out").isBlank();

    }public void validateDDFiledDisableAndClearOut(WebElement ele, String fieldName){
        sa.get().assertThat(ele.getAttribute("aria-disabled")).as(fieldName+ " is not disable").isEqualTo("true");
        sa.get().assertThat(ele.getText()).as(fieldName+" is not cleared out").isBlank();

    }
    @Then("I verify {string} field is disable and cleared out")
    public void verifyFieldsIsDisableAndClearedOut(String str) {
        waitFor(1);
        switch (str){
            case "VCL Due Date":
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.vclDueDate,"VLC Due Date");
                 break;
            case "VCL Sent Date":
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.vclSentDate,"VLC Sent date");
                break;
            case "Document Type":
                validateDDFiledDisableAndClearOut(createGeneralTask.inboundCorrespondenceType,"Document Type");
                break;
            case "Locality":
                validateDDFiledDisableAndClearOut(createGeneralTask.taskLocality,"Locality");
                break;
            case "Case Worker Name":
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.caseWorkerFirstNameTxtBox,"Case Worker First Name");
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.caseWorkerLastNameTxtBox,"Case Worker Last Name");
                break;
            case "Transfer Reason":
                validateDDFiledDisableAndClearOut(createGeneralTask.reasonDrDn,"Transfer Reason");
                break;
            case "Denial Reason":
            case "DENIAL REASON":
                validateDDFiledDisableAndClearOut(createGeneralTask.denialReason,"Denial Reason");
                break;
            case "Other":
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.other,"Other");
                CRM_GeneralTaskStepDef.taskValues.get().put("other", "-- --");
                break;
            case "DisenrollmentDate":
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.disenrollmentDate,"Disenrollment Date");
                break;
            case "Decision":
                validateDDFiledDisableAndClearOut(createGeneralTask.eligibilityDecision,"Decision");
                break;
            case "Escalation Reason":
                validateDDFiledDisableAndClearOut(createGeneralTask.escalationReason,"Escalation Reason");
                break;
            case "Other Entity":
                validateDDFiledDisableAndClearOut(createGeneralTask.organizationDD,"Other Entity");
                break;
            case "Facility Type":
                validateDDFiledDisableAndClearOut(createGeneralTask.facilityType,"Facility Type");
                break;
            case "Facility Name":
                validateDDFiledDisableAndClearOut(createGeneralTask.facilityName,"Facility Name");
                break;
            case "Invalid Reason":
                validateDDFiledDisableAndClearOut(createGeneralTask.invalidReasonDD,"Invalid Reason");
                assertEquals(createGeneralTask.invalidReasonDD.getAttribute("aria-disabled"), "true", "Invalid Reason is not disable");
                assertTrue(createGeneralTask.invalidReasonDD.getText().isEmpty(),"Invalid Reason is not cleared out");
                CRM_GeneralTaskStepDef.taskValues.get().put("invalidReason", "-- --");
                if(CRM_GeneralTaskStepDef.taskValues.get().containsKey("disposition"))
                    CRM_GeneralTaskStepDef.taskValues.get().remove("disposition");
                break;
            case "VCCC Response Date":
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.vcccResponseDate,"VCCC Response Date");
                break;
            case "sentNOADate":
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.sentNOADate,"SENT NOA DATE");
                CRM_GeneralTaskStepDef.taskValues.get().put("sentNOADate", "-- --");
                break;
            case "Number Of Approved Applicants":
                validateDateOrTextFiledDisableAndClearOut(createGeneralTask.numberOfApprovedApplicants,"# OF APPROVED APPLICANTS");
                break;
            case "Decision Source":
                //validateDateOrTextFiledDisableAndClearOut(createGeneralTask.decisionSource,"DECISION SOURCE");
                validateDDFiledDisableAndClearOut(createGeneralTask.decisionSource,"DECISION SOURCE");
                break;
            case "Approval Reason":
                validateDDFiledDisableAndClearOut(createGeneralTask.approvalReason,"APPROVAL REASON ");
                break;
            default: throw new IllegalArgumentException("Incorrect argument : " + str);
        }
    }

    public void verifyFieldIsEnableAndMandatory(WebElement ele, String fieldName){
        sa.get().assertThat(ele.isEnabled()).as(fieldName+"is not enable").isTrue();
        generalTask.get().verifyTaskTypeIsMandatoryField(fieldName);
    }
    @Then("I verify {string} field is enable and required")
    public void verifyFieldsIsEnabledAndDisplayed(String str) {
        switch (str){
            case "VCL Due Date":
                verifyFieldIsEnableAndMandatory(createGeneralTask.vclDueDate,"VCL DUE DATE ");
                break;
            case "VCL Sent Date":
                verifyFieldIsEnableAndMandatory(createGeneralTask.vclSentDate,"VCL SENT DATE ");
                break;
            case "Document Type":
                verifyFieldIsEnableAndMandatory(createGeneralTask.inboundCorrespondenceType,"DOCUMENT TYPE ");
                break;
            case "Locality":
                verifyFieldIsEnableAndMandatory(createGeneralTask.taskLocality,"LOCALITY ");
                break;
            case "Case Worker Name":
                verifyFieldIsEnableAndMandatory(createGeneralTask.caseWorkerFirstNameTxtBox,"CASE WORKER FIRST NAME ");
                verifyFieldIsEnableAndMandatory(createGeneralTask.caseWorkerLastNameTxtBox,"CASE WORKER LAST NAME ");
                break;
            case "Transfer Reason":
                verifyFieldIsEnableAndMandatory(createGeneralTask.reasonDrDn,"TRANSFER REASON");
                break;
            case "Denial Reason":
            case "DENIAL REASON":
                verifyFieldIsEnableAndMandatory(createGeneralTask.denialReason,"DENIAL REASON ");
                break;
            case "Other":
                verifyFieldIsEnableAndMandatory(createGeneralTask.other,"OTHER ");
                break;
            case "DisenrollmentDate":
                verifyFieldIsEnableAndMandatory(createGeneralTask.disenrollmentDate,"DISENROLLMENT DATE ");
                break;
            case "Decision":
                verifyFieldIsEnableAndMandatory(createGeneralTask.eligibilityDecision,"DECISION ");
                break;
            case "Escalation Reason":
                verifyFieldIsEnableAndMandatory(createGeneralTask.escalationReason,"ESCALATION REASON ");
                break;
            case "Other Entity":
                verifyFieldIsEnableAndMandatory(createGeneralTask.organizationDD,"OTHER ENTITY ");
                break;
            case "VCCC Response Date":
                verifyFieldIsEnableAndMandatory(createGeneralTask.vcccResponseDate,"VCCC RESPONSE DATE ");
                break;
            case "Invalid Reason":
                verifyFieldIsEnableAndMandatory(createGeneralTask.invalidReasonDD,"INVALID REASON ");
                break;
            case "sentNOADate":
                verifyFieldIsEnableAndMandatory(createGeneralTask.sentNOADate,"SENT NOA DATE");
                break;
            case "Number Of Approved Applicants":
                verifyFieldIsEnableAndMandatory(createGeneralTask.numberOfApprovedApplicants,"# OF APPROVED APPLICANTS ");
                break;
            case "Decision Source":
                verifyFieldIsEnableAndMandatory(createGeneralTask.decisionSource,"DECISION SOURCE ");
                break;
            case "VACMS Case ID":
                verifyFieldIsEnableAndMandatory(createGeneralTask.externalCaseId,"VACMS CASE ID ");
                break;
            case "APPROVAL REASON":
                verifyFieldIsEnableAndMandatory(createGeneralTask.approvalReason,"APPROVAL REASON ");
                break;
            case "Renewal Processing Due Date":
                verifyFieldIsEnableAndMandatory(createGeneralTask.renewalProcessingDueDate,"RENEWAL PROCESSING DUE DATE ");
                break;
            case "Renewal Date":
                verifyFieldIsEnableAndMandatory(createGeneralTask.renewalDate,"RENEWAL DATE ");
                break;
            case "Caller Name":
                verifyFieldIsEnableAndMandatory(createGeneralTask.callerName,"CALLER NAME ");
                break;
            case "Preferred Phone":
                verifyFieldIsEnableAndMandatory(createGeneralTask.preferredPhoneTxtBox,"PREFERRED PHONE ");
                break;
            case "Member Name":
                verifyFieldIsEnableAndMandatory(createGeneralTask.memberNameDrpdown,"MEMBER NAME");
                break;
            case "Originating Escalation Type":
                verifyFieldIsEnableAndMandatory(createGeneralTask.originatingEscalationType,"ORIGINATING ESCALATION TYPE ");
                break;

            default: throw new IllegalArgumentException("Incorrect argument : " + str);
        }
        scrollUpUsingActions(1);
    }

    @Then("I verify task's status is Cancelled")
    public void i_verify_task_status_is_cancelled() {
        waitFor(2);
        assertEquals(myTask.status.getText(), "Cancelled", "Task status is not *Cancelled*");
    }

    @And("I store task id on edit page")
    public void storeTaskIdOnEditPage() {
        waitForVisibility(myTask.taskIdColumn,5);
        CRM_TaskManagementStepDef.taskId.set(myTask.taskIdColumn.getText().replaceAll("\\D",""));
    }

    public void validateDateOrTextFiledDisableAndNotEmpty(WebElement ele, String fieldName) {
        sa.get().assertThat(ele.isEnabled()).as(fieldName + " is not disable").isFalse();
        sa.get().assertThat(ele.getAttribute("value")).as(fieldName+" is not cleared out").isNotEmpty();
    }

    public void verifyFieldIsEnabled(WebElement ele, String fieldName){
        sa.get().assertThat(ele.isEnabled()).as(fieldName+" is not enabled").isTrue();
    }

    @Then("I verify {string} field is disable and not empty")
    public void verifyFieldsIsDisable(String str) {
        waitFor(1);
        switch (str){
            case "VCL Sent Date":
                validateDateOrTextFiledDisableAndNotEmpty(createGeneralTask.vclSentDate,"VLC Sent date");
                break;
            case "INBOUND CORRESPONDENCE TYPE":
                validateDateOrTextFiledDisableAndNotEmpty(createGeneralTask.inboundCorrespondenceTypeInput,"INBOUND CORRESPONDENCE TYPE");
                sa.get().assertThat(createGeneralTask.inboundCorrespondenceTypeInput.getAttribute("value")).isEqualTo("DCEB Enrollment Form");
                break;
            default: throw new IllegalArgumentException("Incorrect argument : " + str);
        }
    }

    @Then("I verify {string} field is enabled")
    public void verifyFieldsIsEnabled(String str) {
        waitFor(1);
        switch (str){
            case "VCL Due Date":
                verifyFieldIsEnabled(createGeneralTask.vclDueDate,"VCL Due Date");
                break;
            case "Number Of Approved Applicants":
                verifyFieldIsEnabled(createGeneralTask.numberOfApprovedApplicants,"# OF APPROVED APPLICANTS ");
                break;
            case "Decision Source":
                verifyFieldIsEnabled(createGeneralTask.decisionSource,"DECISION SOURCE ");
                break;
            case "Approval Reason":
                verifyFieldIsEnabled(createGeneralTask.approvalReason,"APPROVAL REASON ");
                break;
            case "Renewal Processing Due Date":
                verifyFieldIsEnabled(createGeneralTask.renewalProcessingDueDate,"Renewal Processing Due Date");
                break;
            case "Application Id":
                verifyFieldIsEnabled(createGeneralTask.externalApplicationId,"Application Id");
                break;
            case "Contact Reason":
                verifyFieldIsEnabled(createGeneralTask.cantactReason,"Contact Reason");
                break;
            default: throw new IllegalArgumentException("Incorrect argument : " + str);
        }
    }

    @And("I store static case id {string}")
    public void storeCaseID(String caseID) {
        CRM_GeneralTaskStepDef.taskValues.get().put("caseID",caseID) ;
    }

    public void verifyFieldIsOptional(WebElement ele, String fieldName) {
        sa.get().assertThat(getAttributesFromTheTag(ele).contains("required")).as(fieldName + " is not optional").isFalse();
    }

    @Then("I verify {string} field is optional")
    public void verifyFieldsIsOptional(String str) {
        switch (str){
            case "MI RECEIVED DATE":
                verifyFieldIsOptional(createGeneralTask.miReceivedDate,"MI RECEIVED DATE");
                break;
            case "APPLICATION ID":
                verifyFieldIsOptional(createGeneralTask.externalApplicationId,"APPLICATION ID");
                break;
            default: throw new IllegalArgumentException("Incorrect argument : " + str);
        }
    }

    @Then("I verify {string} field is required")
    public void verifyFieldsIsRequired(String str) {
        switch (str){
            case "APPEAL REASON":
                verifyFieldIsRequired(createGeneralTask.appealReason,"APPEAL REASON");
                break;
            case "ASSIGNEE BUSINESS UNIT":
                verifyFieldIsRequired(createGeneralTask.businessUnitAssignedTo,"ASSIGNEE BUSINESS UNIT");
                break;
            default: throw new IllegalArgumentException("Incorrect argument : " + str);
        }
    }

    public void verifyFieldIsRequired(WebElement ele, String fieldName) {
        sa.get().assertThat(getAttributesFromTheTag(ele).contains("required")).as(fieldName + " is not required").isTrue();
    }

}
