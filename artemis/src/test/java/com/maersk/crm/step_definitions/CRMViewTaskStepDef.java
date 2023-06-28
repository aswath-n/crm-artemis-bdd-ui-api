package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APITMHolidayController;
import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.crm.CRMTaskEditHistoryPage;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Then;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.testng.Assert.*;

public class CRMViewTaskStepDef extends CRMUtilities implements ApiBase {

    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    final ThreadLocal<CRM_GeneralTaskStepDef> generalTask = ThreadLocal.withInitial(CRM_GeneralTaskStepDef::new);
    //APITMHolidayController tmHoliday = new APITMHolidayController();
    public static final ThreadLocal<String> date = ThreadLocal.withInitial(String::new);
    CRMTaskEditHistoryPage editHistory = new CRMTaskEditHistoryPage();

    @Then("I verify process application task fields displayed")
    public void verifyProcessApplicationTaskFields() {
        waitFor(2);
        assertEquals(myTask.applicationID.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"), "Application ID value not matches");
        assertEquals(myTask.applicationType.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"), "ApplicationType value not matches");
        assertEquals(myTask.myWorkspaceDate.getText(), "-- --", "MyWorkspaceDate value not matches");
        assertEquals(myTask.lDSSReceivedDate.getText(), "-- --", "LDSSReceivedDate value not matches");
        assertEquals(myTask.applicationReceivedDate.getText(), "-- --", "ApplicationReceivedDate value not matches");
        assertEquals(myTask.applicationSignatureDate.getText(), "-- --", "ApplicationSignatureDate value not matches");
        assertEquals(myTask.applicationUpdateDate.getText(), "-- --", "ApplicationUpdateDate value not matches");
        assertEquals(myTask.facilityName.getText(), "-- --", "FacilityName value not matches");
        assertEquals(myTask.facilityType.getText(), "-- --", "FacilityType value not matches");
        assertEquals(myTask.channelValue.getText(), "-- --", "Channel value not matches");
        assertEquals(myTask.decisionSource.getText(), "-- --", "DecisionSource value not matches");
        assertFalse(myTask.hpeCheckBox.getAttribute("value").equalsIgnoreCase("false"), "HpeCheckBox value not matches");
        assertEquals(myTask.noOFApplicantsInHousehold.getText(), "-- --", "NoOFApplicantsInHousehold value not matches");
        assertEquals(myTask.missingInfoRequired.getText(), "-- --", "MissingInfoRequired value not matches");
        assertEquals(myTask.applicationSubStatus.getText(), "-- --", "ApplicationSubStatus value not matches");
        assertEquals(myTask.noOFApprovedApplicants.getText(), "-- --", "NoOFApprovedApplicants value not matches");
        assertEquals(myTask.denialReason.getText(), "-- --", "DenialReason value not matches");
        assertEquals(myTask.txtActionTaken.getText(), "-- --", "ActionTaken value not matches");
    }

    @Then("I verify {string} task fields are displayed")
    public void verifyProcessApplicationTaskFields(String taskName) throws Exception {
        waitFor(2);
        sa.get().assertThat(editHistory.taskDetailsTab.isDisplayed()).as("Header is not displayed").isTrue();
        switch (taskName) {
            case "Process Application":
                sa.get().assertThat(myTask.applicationType.getText()).as("ApplicationType value not matches").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"));
                sa.get().assertThat(myTask.applicationID.getText()).as("Application ID value not matches").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("actionTakenSingle")) {
                    sa.get().assertThat(myTask.txtActionTaken.getText()).as("Action Taken is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("actionTakenSingle"));
                } else
                    sa.get().assertThat(myTask.txtActionTaken.getText()).as("ActionTaken value not matches").isEqualTo("-- --");
                if (CRM_GeneralTaskStepDef.taskValues.get().get("applicationType").equalsIgnoreCase("MAGI - PW"))
                    sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("1");
                else
                    sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("2");
                sa.get().assertThat(isElementDisplayed(myTask.dueDateValue)).as("Due Date is not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(myTask.dueIn)).as("Due In is not displayed").isTrue();
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Process application Due Date is did not match").isEqualTo(validateDueDateOfProcessApplication("localDate", taskName, 4));
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;
            case "Review Complaint":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("1");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getDueDate("localDate", 1, "CoverVA"));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("1 Business Day");
                sa.get().assertThat(myTask.vacmsCaseIdValue.getText()).as("VACMS CaseId value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.mmisMemberIdValue.getText()).as("MMIS MemberId value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.complaintTypeValue.getText()).as("Complaint Type value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;
            case "Final Application Review":
                sa.get().assertThat(myTask.applicationType.getText()).as("ApplicationType value not matches").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("applicationType"));
                sa.get().assertThat(myTask.applicationID.getText()).as("Application ID value not matches").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("1");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getGreaterDateFormatMMddyyyy(0));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("0 Business Days");
                sa.get().assertThat(myTask.txtActionTaken.getText()).as("ActionTaken value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;
            case "Missing Information":
                sa.get().assertThat(myTask.applicationID.getText()).as("Application ID value not matches").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("1");
                if (CRM_GeneralTaskStepDef.taskValues.get().get("applicationType").equalsIgnoreCase("MAGI - PW")) {
                    sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getDueDate("localDate", 5, "CoverVA"));
                    sa.get().assertThat(myTask.dueIn.getText().equals("5 Days") || myTask.dueIn.getText().equals("6 Days") || myTask.dueIn.getText().equals("7 Days")).as("Due In is not displayed").isTrue();
                } else {
                    sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getGreaterDateFormatMMddyyyy(8));
                    sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("8 Days");
                }
                sa.get().assertThat(myTask.txtActionTaken.getText()).as("ActionTaken value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.denialReason.getText()).as("Denial Reason value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.documentDueDate.getText()).as("Due Date value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.txtDocType.getText()).as("DOCUMENT TYPE value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.noOFApprovedApplicants.getText()).as("noOFApprovedApplicants value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.receivedDate.getText()).as("Received Date value not matches").isEqualTo("-- --");
                if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("assignee") && !CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty())
                    sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
                else
                    sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;
            case "Verification Document":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("2");
                if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("externalApplicationId"))
                    sa.get().assertThat(myTask.applicationID.getText()).as("Application ID value not matches").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                else
                    sa.get().assertThat(myTask.applicationID.getText()).as("Application id is empty").isNotBlank();
                sa.get().assertThat(myTask.vacmsCaseIdValue.getText()).as("VACMS CaseId value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.mmisMemberIdValue.getText()).as("MMIS MemberId value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.txtDocType.getText()).as("DOCUMENT TYPE value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.txtActionTaken.getText()).as("ActionTaken value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getDueDate(date.get(), 3, "CoverVA"));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isNotBlank();

                break;

            case "Complaint Escalation":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("1");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getGreaterDateFormatMMddyyyy(1));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("1 Business Day");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;

            case "Fair Hearing":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("3");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getGreaterDateFormatMMddyyyy(55));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("55 Days");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;

            case "HCC Outbound Call":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("2");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getDueDate("localDate", 3, "IN-EB"));
                waitFor(2);
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("3 Business Days");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;

            case "Review Appeals Form":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("2");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getGreaterDateFormatMMddyyyy(7));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("7 Days");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                sa.get().assertThat(myTask.txtActionTaken.getText()).as("ActionTaken value not matches").isEqualTo("-- --");
                break;

            case "GCNJ Appeals Acknowledgement Letter":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("1");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getDueDate("localDate", 7, "NJ-SBE"));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("7 Business Days");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;

            case "Follow-Up on Appeal":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("3");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getGreaterDateFormatMMddyyyy(1));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("1 Day");
                sa.get().assertThat(myTask.txtActionTaken.getText()).as("ActionTaken value not matches").isEqualTo("-- --");
                if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("assignee") && !CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty())
                    sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
                else
                    sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;

            case "GCNJ Resolve Appeal":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("3");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getGreaterDateFormatMMddyyyy(1));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("1 Day");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;

            case "QA Review Complaint":
            case "Supervisor Review Complaint":
            case "State Escalated Complaint":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("1");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getDueDate("localDate", 1, "IN-EB"));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("1 Business Day");
                sa.get().assertThat(myTask.txtActionTaken.getText()).as("ActionTaken value not matches").isEqualTo("-- --");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;
            case "Outbound Call":
            case "Complaint Outbound Call":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("1");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getDueDate("localDate", 1, "IN-EB"));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("1 Business Day");
                sa.get().assertThat(myTask.txtActionTaken.getText()).as("ActionTaken value not matches").isEqualTo("-- --");
                if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("csrName") && myTask.taskTypeValue.getText().equals("Outbound Call")) {
                    sa.get().assertThat(myTask.lblAssignee.getText()).as("Assignee value not matches").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("csrName"));
                } else
                    sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                sa.get().assertThat(myTask.contactName.getText()).as("Name is mismatch").isEqualTo("-- --");
                sa.get().assertThat(myTask.preferredPhoneVlu.getText()).as("Preferred Phone is mismatch").isEqualTo("-- --");
                sa.get().assertThat(myTask.preferredCallBackDateVlu.getText()).as("Preferred Date is mismatch").isEqualTo("-- --");
                sa.get().assertThat(myTask.preferredCallBackTimeVlu.getText()).as("Preferred Time is mismatch").isEqualTo("-- --");
                break;

            case "Generate IDR Successful Letter":
            case "Generate IDR Unsuccessful Letter":
                sa.get().assertThat(myTask.priorityValue.getText()).as("Priority is mismatch").isEqualTo("5");
                sa.get().assertThat(myTask.dueDateValue.getText()).as("Due Date is not displayed").isEqualTo(getDueDate("localDate", 7, "NJ-SBE"));
                sa.get().assertThat(myTask.dueIn.getText()).as("Due In is not displayed").isEqualTo("7 Business Days");
                sa.get().assertThat(myTask.assigneeValue.getText()).as("Assignee is mismatch").isEqualTo("-- --");
                break;
        }

        sa.get().assertThat(myTask.statusValue.getText()).as("Task Status is mismatch").isEqualTo("Created");
        sa.get().assertThat(myTask.taskTypeValue.getText().trim()).as("Task Type value not matches").isEqualTo(taskName);
        waitFor(1);
        sa.get().assertThat(myTask.source.getText()).as("Source is mismatch").isEqualTo("System");
        sa.get().assertThat(myTask.lblTaskInfo.getText()).as("Task Information is mismatch").isEqualTo("-- --");
        //sa.get().assertThat(myTask.taskNotesValue.getText()).as("Task Note is mismatch").isEqualTo("-- --");
        sa.get().assertThat(myTask.reasonForEdit.getText()).as("Reason For Edit is mismatch").isEqualTo("-- --");
        sa.get().assertThat(myTask.createdByValue.getText().trim()).as("CreatedBy is mismatch").isEqualTo("Task Mgmt Service");
        sa.get().assertThat(myTask.createdOnValue.getText()).as("Created on is mismatch").isEqualTo(createGeneralTask.projectDateAtHeader.getText());
        sa.get().assertThat(myTask.statusDateValue.getText()).as("Status Date on is mismatch").isEqualTo(createGeneralTask.projectDateAtHeader.getText());
        sa.get().assertThat(checkTaskCreatedByCamunda(LoginStepDef.projectName1.get())).as("Task is not created from SR").isEqualTo("CREATED_BY_SERVICE_REQUEST");
    }

    public String getDueDate(String date, int days, String project) throws Exception {
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().i_initiated_create_business_unit_api(project, "2022");
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().getHolidayList();
        LocalDate result = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        if (!date.equalsIgnoreCase("localDate")) {
            formatter = formatter.withLocale(Locale.US);
            result = LocalDate.parse(date, formatter1);
        }
        int addedDays = 0;

        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                if (!APITMHolidayController.holiday.contains(result.format(formatter))) {
                    addedDays++;
                }
            }
        }
        return result.format(formatter1);
    }

    @Then("I click on Create Link button and choose task or sr link")
    public void manuallyLinkTaskOrSRToInboundDocument() {
        waitForVisibility(myTask.receivedDate, 100);
        date.set(myTask.receivedDate.getText());
        click(myTask.createLinkBtn);
        click(myTask.taskOrSRLinkBtn);
    }

    @Then("I click on refresh button")
    public void clickOnRefreshButton() {
        waitForVisibility(myTask.refreshBtn, 10);
        click(myTask.refreshBtn);
    }

    public String checkTaskCreatedByCamunda(String projectName) throws Exception {
        API_THREAD_LOCAL_FACTORY.getProviderControllerThreadLocal().getAuthenticationToken(projectName, "CRM");
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().initiateGetTaskByTaskId("getFromUi");
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().getTaskByTaskId();
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        sa.get().assertThat(temp.isNull("genericField1Txt")).as("Task is not created by camunda").isFalse();
        return temp.getString("genericField1Txt");
    }

    public String validateDueDateOfProcessApplication(String timeZone, String taskSRType, int days) throws Exception {
        String actualDate = null;
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().i_initiated_create_business_unit_api("CoverVA", "2022");
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getTmHolidayThreadLocal().getHolidayList();
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().i_initiated_create_business_unit_api("CoverVA");
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().runTheBusinessUnitGetAPI();
        API_THREAD_LOCAL_FACTORY.getBusinessDayThreadLocal().getTimeFrameConfiguredForProject();
        String dateStr = CRM_GeneralTaskStepDef.taskValues.get().get("myWorkSpaceDate");
        DateTimeFormatter dateFormat1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateStr, dateFormat1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (!timeZone.equalsIgnoreCase("localDate")) {
            formatter = formatter.withLocale(Locale.US);
            date = LocalDate.parse(dateStr, dateFormat1);
        }
        int addedDays = 0;
        switch (taskSRType) {
            case "Process Application":
                while (addedDays < days) {
                    date = date.plusDays(1);
                    if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                        if (!APITMHolidayController.holiday.contains(date.format(formatter))) {
                            addedDays++;
                        }
                    }
                }
                break;
            case "Application SR":
                while (addedDays < days) {
                    date = date.plusDays(44);
                    if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                        if (!APITMHolidayController.holiday.contains(date.format(formatter))) {
                            addedDays++;
                        } else {
                            date = date.minusDays(1);
                        }
                    } else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                        date = date.minusDays(1);
                    } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        date = date.minusDays(2);
                    }
                    addedDays++;
                }
                break;
            case "Application Renewal SR":
            case "Renewal SR":
                while (addedDays < days) {
                    date = date.plusDays(29);
                    if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                        if (!APITMHolidayController.holiday.contains(date.format(formatter))) {
                            addedDays++;
                        } else {
                            date = date.minusDays(1);
                        }
                    } else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                        date = date.minusDays(1);
                    } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        date = date.minusDays(2);
                    }
                    addedDays++;
                }
                break;
            default:
                sa.get().fail("No due date for" + taskSRType);
        }
        actualDate = date.format(dateFormat1);
        return actualDate;
    }

    public String validateDueIn(String taskOdSrType) throws Exception {
        return (((daysDifference(validateDueDateOfProcessApplication("localDate", taskOdSrType, 1), getDate("today"))) - 1) + "");
    }

    @Then("I verify Task or SR linked to")
    public void iVerifyTaskOrSRLinkedTo(List<String> data) {
        List<String> linkTypes = new ArrayList<>();
        for (WebElement link : createGeneralTask.linkType) {
            linkTypes.add(link.getText());
        }
        sa.get().assertThat(linkTypes.containsAll(data)).as("Links are mismatch").isTrue();
    }
}
