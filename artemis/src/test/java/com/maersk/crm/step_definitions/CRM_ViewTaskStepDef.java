package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class CRM_ViewTaskStepDef extends BrowserUtils {

    CRMViewTaskPage viewTaskPage = new CRMViewTaskPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMTaskEditHistoryPage editHistory = new CRMTaskEditHistoryPage();

    final ThreadLocal<CRM_GeneralTaskStepDef> generalTaskStepDef = ThreadLocal.withInitial(CRM_GeneralTaskStepDef::new);
    public static final ThreadLocal<ArrayList<String>> dynamicFlds = ThreadLocal.withInitial(ArrayList::new);


    @Then("I verify fields order in PlanProvider View task page")
    public void verifyAdditionalFieldsInViewPage() {
        for (int i = 0; i < APITaskManagementController.fieldOrder.get().size(); i++) {
            if (myTask.planProviderViewFieldlbl.get(i).getText().toLowerCase().equals("provider first name") && APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("provider last name"))
                assertEquals(myTask.planProviderViewFieldlbl.get(i).getText().toLowerCase(), APITaskManagementController.fieldOrder.get().get(i + 2).toLowerCase(), "Field order mismatch");
            else if (myTask.planProviderViewFieldlbl.get(i).getText().toLowerCase().equals("provider last name") && APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase().equals("provider first name"))
                assertEquals(myTask.planProviderViewFieldlbl.get(i).getText().toLowerCase(), APITaskManagementController.fieldOrder.get().get(i).toLowerCase(), "Field order mismatch");
            else
                assertEquals(APITaskManagementController.fieldOrder.get().get(i + 1).toLowerCase(), myTask.planProviderViewFieldlbl.get(i).getText().toLowerCase(), "Field order mismatch");
        }
    }

    @Then("I verify task following fields are present on view page")
    public void verifyDynamicFieldsAreDisplayed(List<String> additionalFlds) {
        dynamicFlds.set(new ArrayList<>(additionalFlds));
        //added wait because ui take some time to load the dynamic fields
        waitFor(2);
        for (int i = 0; i < dynamicFlds.get().size(); i++) {
            sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//p[text()='" + dynamicFlds.get().get(i) + "']/..//h6")))).as(" Dynamic field " + dynamicFlds.get().get(i) + " are not displayed").isTrue();
        }
    }

    @Then("I verify the below task details are displayed in my task")
    public void verifyMyTaskDetails() {
        waitFor(2);
        sa.get().assertThat(editHistory.taskDetailsTab.isDisplayed()).as("Header is not displayed").isTrue();
        sa.get().assertThat(myTask.taskDetailsHeader.isDisplayed()).as("Header is not displayed").isTrue();
        if (LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA")) {
            sa.get().assertThat(isElementDisplayed(myTask.lblCaseId)).as("case id is present in View task ui").isFalse();
        }
        if (isElementDisplayed(myTask.priorityDashboard)) {
            sa.get().assertThat(myTask.lblStatus.getText().replace("-", " ")).as("Status is mismatch").isEqualTo(myTask.statusDropDown.getText());
        } else {
            sa.get().assertThat(myTask.lblStatus.getText()).as("Status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
        }
        if (CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").isEmpty() || CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo") == null)
            sa.get().assertThat(myTask.lblTaskInfo.getText()).as("Task information is mismatch").isEqualTo("-- --");
        else
            sa.get().assertThat(myTask.lblTaskInfo.getText()).as("Task Information is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
        /*
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote"))
            sa.get().assertThat(myTask.lblTaskNote.getText()).as("taskNote is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
        else
            sa.get().assertThat(myTask.lblTaskNote.getText()).as("Task note is mismatch").isEqualTo("-- --");
        */
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForCancel"))
            sa.get().assertThat(myTask.reasonForCancel.getText()).as("Reason For Cancel is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel"));
        else
            sa.get().assertThat(isElementDisplayed(myTask.reasonForCancel)).as("Reason Foe Cancel is present in view page").isFalse();
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForOnHold"))
            sa.get().assertThat(myTask.reasonForHold.getText()).as("Reason For Hold is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reasonForOnHold"));
        else
            sa.get().assertThat(isElementDisplayed(myTask.reasonForHold)).as("Reason For Hold is present in view page").isFalse();
        for (String key : CRM_GeneralTaskStepDef.taskValues.get().keySet()) {
            switch (key) {
                case "source":
                    sa.get().assertThat(myTask.lblSource.getText()).as("Source is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("source"));
                    break;
                case "startDate":
                    sa.get().assertThat(myTask.lblStatusDate.getText()).as("Status Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("startDate"));
                    break;
                case "created_by":
                case "created By":
                case "createdBy":
                    sa.get().assertThat(myTask.lblCreatedBy.getText()).as("CreatedBy is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
                    break;
                case "created_on":
                case "created On":
                case "createOn":
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
                    sa.get().assertThat(myTask.lblTaskType.getText()).as("Task Type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"));
                    break;
                case "priority":
                    sa.get().assertThat(myTask.lblPriority.getText()).as("Priority is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
                    break;
                case "assignee":
                    sa.get().assertThat(myTask.lblAssignee.getText()).as("Assignee is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
                    break;
                case "externalApplicationId":
                    try {
                        sa.get().assertThat(myTask.exAppIdValue.getText()).as("External Application Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.appIdValue.getText()).as("Application Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                    }
                    break;
                case "externalConsumerID":
                    try {
                        sa.get().assertThat(myTask.mmisMemberIdValue.getText()).as("MMIS Member Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalConsumerID"));
                    } catch (Exception el) {
                        sa.get().assertThat(myTask.medicaidIDRidVlu.getText()).as("Medicaid ID/RID is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalConsumerID"));
                    }

                    break;
                case "externalCaseId":
                    try {
                        sa.get().assertThat(myTask.exCaseIdValue.getText()).as("External Case Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.vacmsCaseIdValue.getText()).as("External Case Id is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId"));
                    }
                    break;
                case "contactReason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").contains(",")) {
                        sa.get().assertThat(myTask.contactReasonVlu.getText()).as("Contact Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").split(",")[0]);
                        hover(createGeneralTask.multiselectValueLink);
                        List<String> actualValues = createGeneralTask.selectedToolTipValue.stream().map(WebElement::getText).collect(Collectors.toList());
                        sa.get().assertThat(actualValues).as("Mouse Over values are incorrect").contains(CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").split(",")[1]);
                    } else
                        sa.get().assertThat(myTask.contactReasonVlu.getText()).as("Contact Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactReason"));
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
                case "complaintAbout":
                    sa.get().assertThat(myTask.cmplAbtValue.getText()).as("Complaint About drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("complaintAbout"));
                    break;
                case "reason":
                    sa.get().assertThat(myTask.reasonValue.getText()).as("Reason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("reason"));
                    break;
                case "actionTaken":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").contains(",")) {
                        generalTaskStepDef.get().iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.get().verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.txtActionTaken.getText()).as("actionTaken drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken"));
                    break;
                case "channel":
                    sa.get().assertThat(myTask.channelValue.getText()).as("channel drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("channel"));
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
                        generalTaskStepDef.get().iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.get().verifyToolTipValue();
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
                case "dateResent":
                    sa.get().assertThat(myTask.dateResent.getText()).as("Date Resent is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("dateResent"));
                    break;
                case "receivedDate":
                    sa.get().assertThat(myTask.receivedDate.getText()).as("Received Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("receivedDate"));
                    break;
                case "businessUnit":
                    sa.get().assertThat(myTask.businessUnit.getText()).as("Business Unit is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("businessUnit"));
                    break;
                case "returnedMailReason":
                    sa.get().assertThat(myTask.returnedMailReason.getText()).as("Returned Mail Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("returnedMailReason"));
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
                case "returnedMailReceivedDate":
                    sa.get().assertThat(myTask.returnedMailReceivedDate.getText()).as("Returned Mail Received Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("returnedMailReceivedDate"));
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
                case "remandReason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("remandReason").contains(",")) {
                        generalTaskStepDef.get().iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.get().verifyToolTipValue();
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
                        generalTaskStepDef.get().iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.get().verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.preHearingReasonVlu.getText()).as("preHearingReason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("preHearingReason"));
                    break;
                case "preHearingOutcome":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("preHearingOutcome").contains(",")) {
                        generalTaskStepDef.get().iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.get().verifyToolTipValue();
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
                        generalTaskStepDef.get().iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.get().verifyToolTipValue();
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
                    String providerPhone = CRM_GeneralTaskStepDef.taskValues.get().get("providerPhone").equalsIgnoreCase("-- --") ?
                            myTask.providerPhone.getText() : myTask.providerPhone.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
                    sa.get().assertThat(providerPhone).as("providerPhone is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("providerPhone"));
                    break;
                case "providerEmail":
                    sa.get().assertThat(myTask.providerEmail.getText()).as("providerEmail is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("providerEmail"));
                    break;
                case "denialReason":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("denialReason").contains(",")) {
                        generalTaskStepDef.get().iVerifyMultiSelectLinkIsAvailble();
                        generalTaskStepDef.get().verifyToolTipValue();
                    } else
                        sa.get().assertThat(myTask.denialReason.getText()).as("denial Reason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("denialReason"));
                    break;
                case "decisionSource":
                    sa.get().assertThat(myTask.decisionSource.getText()).as("decisionSource is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("decisionSource"));
                    break;
                case "other":
                    sa.get().assertThat(myTask.other.getText()).as("other is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("other"));
                    break;
                case "invalidReason":
                    sa.get().assertThat(myTask.invalidReason.getText()).as("Invalid Reason is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("invalidReason"));
                    break;
                case "denialReasonSingle":
                    sa.get().assertThat(myTask.denialReason.getText()).as("Denial Reason drop down value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("denialReasonSingle"));
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
                case "disposition":
                    sa.get().assertThat(myTask.dispositionVlu.getText()).as("Disposition is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("disposition"));
                    break;
                case "contactPhone":
                    try {
                        sa.get().assertThat(myTask.phoneVlu.getText().replaceAll("\\D", "")).as("Phone number is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactPhone"));
                    } catch (Exception e) {
                        sa.get().assertThat(myTask.contactPhone.getText().replaceAll("\\D", "")).as("Phone number is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactPhone"));
                    }
                    break;
                case "sentNOADate":
                    sa.get().assertThat(myTask.sentNoaDateVlu.getText()).as("sent NOA Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("sentNOADate"));
                    break;
                case "medicaidId":
                    assertEquals(myTask.medicaidIDRidVlu.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("medicaidId"), "Medicaid Id is mismatch");
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
                case "contactName":
                    sa.get().assertThat(myTask.contactName.getText()).as("contactName number is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("contactName"));
                    break;
                case "planStartDate":
                    sa.get().assertThat(myTask.planStartDate.getText()).as("Plan Start Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("planStartDate"));
                    break;
                case "planName":
                    sa.get().assertThat(myTask.planName.getText()).as("planNamee is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("planName"));
                    break;
                case "usedTobaccoInTheLastSixMonths":
                    sa.get().assertThat(myTask.usedTobaccoInTheLastSixMonths.getText()).as("used tobacco in the last six months drop down value mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("usedTobaccoInTheLastSixMonths"));
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
                    sa.get().assertThat(myTask.AddressLine1Value.getText()).as("Addressline1 is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("AddressLine1"));
                    break;
                case "AddressLine2":
                    sa.get().assertThat(myTask.AddressLine2Value.getText()).as("Addressline2 is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("AddressLine2"));
                    break;
                case "City":
                    sa.get().assertThat(myTask.CityValue.getText()).as("City is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("City"));
                    break;
                case "State":
                    sa.get().assertThat(myTask.StateValue.getText()).as("State is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("State"));
                    break;
                case "addressZipCode":
                    sa.get().assertThat(myTask.zipCode.getText()).as("addressZipCode field value is wrong").
                            isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("addressZipCode"));
                    break;
                case "addressSource":
                    sa.get().assertThat(myTask.addressSource.getText()).as("addressSource dropdown value is wrong").
                            isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("addressSource"));
                    break;
                case "addressType":
                    sa.get().assertThat(myTask.addressType.getText()).as("addressType dropdown value is wrong").
                            isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("addressType"));
                    break;
                case "taskID":
                    sa.get().assertThat(CRM_GeneralTaskStepDef.taskValues.get().get("taskID")).as("taskID is empty").isNotEmpty();
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
                case "applicationSignatureDate":
                    sa.get().assertThat(myTask.applicationSignatureDate.getText()).as("Application Signature Date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationSignatureDate"));
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
                case "applicationType":
                    sa.get().assertThat(myTask.applicationType.getText()).as("applicationType value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"));
                    break;
                case "myWorkSpaceDate":
                    sa.get().assertThat(myTask.myWorkspaceDate.getText()).as("myWorkSpaceDate value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("myWorkSpaceDate"));
                    break;
                case "businessUnitAssigneeTo":
                    sa.get().assertThat(myTask.lblBusinessUnitAssigneeTo.getText()).as("businessUnitAssigneeTo DD value is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("businessUnitAssigneeTo"));
                    break;
                default:
                    if (!(key.equals("consumerID") || key.equals("caseID") || key.equals("taskInfo") || key.equals("status") || key.equals("consumerName")
                            || key.equals("noteValue") || key.equals("reasonForCancel") || key.equals("reasonForOnHold") || key.equals("contactRecord")))
                        sa.get().fail("Switch case not match for key " + key);
                    break;
            }
        }
    }

    @Then("I verify task following checkbox are present on view page")
    public void verifyCheckboxAreDisplayed(List<String> additionalFlds) {
        dynamicFlds.set(new ArrayList<>(additionalFlds));
        for (int i = 0; i < dynamicFlds.get().size(); i++) {
            sa.get().assertThat(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//input[@id='" + dynamicFlds.get().get(i) + "']")))).as(" checkbox " + dynamicFlds.get().get(i) + " are not displayed").isTrue();
        }
    }

    @Then("I verify that Incomplete Contact Record Task is systematically created and has expected details")
    public void i_verify_that_Incomplete_Contact_Record_Task_is_systematically_created_and_has_expected_details() {
        waitFor(3);
        assertTrue(myTask.taskDetailsHeader.isDisplayed());

        assertEquals(myTask.lblSource.getText(), "System", "Source is mismatch for Incomplete Contact Record task");
        assertEquals(myTask.lblCreatedBy.getText(), "Service TesterTwo", "CreatedBy is mismatch for Incomplete Contact Record task");
        assertTrue(myTask.lblTaskType.getText().equalsIgnoreCase("Incomplete Contact Record"), "Task Type is mismatch for Incomplete Contact Record task");
        assertEquals(myTask.lblPriority.getText(), "3", "Priority is mismatch for Incomplete Contact Record task");
        assertEquals(myTask.lblStatus.getText(), "Created", " Status isnt verified for Incomplete Contact record task");
        //  task notes arent displayed on view task page anymore
        //   assertEquals(myTask.lblTaskNote.getText(), "Contact Record marked Incomplete due to the user being logged out during Active Contact","Tasknotes arent verified for Incomplete Contact Record task");
        //Two additional fields
        assertEquals(myTask.mmisMemberIdValue.getText(), "-- --", "External consumer Id field isnt appeared");
        assertEquals(myTask.vacmsCaseIdValue.getText(), "-- --", "VACMS case id field isnt appeared");


    }
}
