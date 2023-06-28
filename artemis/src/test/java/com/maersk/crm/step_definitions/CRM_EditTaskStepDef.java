package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class CRM_EditTaskStepDef extends BrowserUtils {

    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    Actions actions = new Actions(Driver.getDriver());
    final ThreadLocal<CRM_GeneralTaskStepDef> generalTask = ThreadLocal.withInitial(CRM_GeneralTaskStepDef::new);
    public static final ThreadLocal<HashMap<String, String>> editHistory = ThreadLocal.withInitial(HashMap::new);


    @And("I will update the following information in edit task page")
    public void i_will_update_the_following_information_in_edit_task_page(Map<String, String> data) {
        data=replaceNullValuesInMap(data,"");
        waitFor(10);
        if (LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA")) {
            sa.get().assertThat(isElementDisplayed(createGeneralTask.editLblCaseId)).as("case id is present in Edit task ui").isFalse();
        }
        String random = "";
        for (String key : data.keySet()) {
            switch (key) {
                case "status":
                    waitFor(5);
                    editHistory.get().put("status", CRM_GeneralTaskStepDef.taskValues.get().get("status"));
                    scrollToElement(myTask.editPageStatusDropDown,false);
                    waitFor(5);
                    selectSingleSelectDD(data.get("status"), "status", myTask.editPageStatusDropDown);
                    CRM_TaskManagementStepDef.taskStatus.set(data.get("status"));
                    CRM_GeneralTaskStepDef.taskValues.get().put("status", data.get("status"));
                    if (myTask.editPageStatusDropDown.getText().equalsIgnoreCase("Complete") || myTask.editPageStatusDropDown.getText().equalsIgnoreCase("Closed")) {
                        CRM_TaskManagementStepDef.dueIn.set(CRM_GeneralTaskStepDef.taskValues.get().get("dueIn"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dueIn", "-- --");
                    }
                    break;
                case "priority":
                    if (!data.get("priority").isEmpty()) {
                        selectDropDown(createGeneralTask.lstTaskPriority, data.get("priority"));
                        editHistory.get().put("priority", CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("priority", data.get("priority"));
                    } else
                        selectDropDownByIndex(createGeneralTask.lstTaskPriority, myTask.priorities, 0);
                    break;
                case "assignee":
                    editHistory.get().put("assignee", CRM_GeneralTaskStepDef.taskValues.get().get("assignee"));
                    if (!data.get("assignee").isEmpty()) {
                        clearAndFillText(createGeneralTask.lstAssignee, data.get("assignee"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("assignee", data.get("assignee"));
                    } else {
                        createGeneralTask.lstAssignee.click();
                        if (!createGeneralTask.lstAssignee.getAttribute("value").isEmpty()) {
                            myTask.clearText.click();
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("assignee", "-- --");
                    }
                    break;
                case "taskInfo":
                    if (!data.get("taskInfo").isEmpty() && data.get("taskInfo").equals("random")) {
                        random = "A" + getRandomString(299);
                        CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", random);
                        clearAndFillText(createGeneralTask.txtTaskInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                        //sendKeyToTextField(createGeneralTask.txtTaskInfo, getRandomString(45));
                        sa.get().assertThat(createGeneralTask.txtTaskInfo.getAttribute("value")).as("Task Information is accepting more then 300 character").hasSize(300);
                    } else if (!data.get("taskInfo").isEmpty() && data.get("taskInfo").equals("maxlength")) {
                        random = "A" + getRandomString(2999);
                        CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", random);
                        clearAndFillText(createGeneralTask.txtTaskInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                        sendKeyToTextField(createGeneralTask.txtTaskInfo, getRandomString(45));
                        sa.get().assertThat(createGeneralTask.txtTaskInfo.getAttribute("value")).as("Task Information is accepting more then 3000 character").hasSize(3000);
                    } else if (!data.get("taskInfo").isEmpty() && data.get("taskInfo").equals("Line Break")) {
                        CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", "abd\n" + "xyz\n" + "345\n" + "ABC1234%\n" + "ASHJ_9087");
                        clearAndFillText(createGeneralTask.txtTaskInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                    } else
                        fillText(data.get("taskInfo"), "taskInfo", createGeneralTask.txtTaskInfo);
                    break;
                    /*
                case "taskNote":
                    if (!data.get("taskNote").isEmpty() && data.get("taskNote").equals("random")) {
                        random = "A" + getRandomString(299);
                        CRM_GeneralTaskStepDef.taskValues.get().put("taskNote", random);
                        clearAndFillText(myTask.editTaskNotes, CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
                        sendKeyToTextField(myTask.editTaskNotes, getRandomString(45));
                        sa.get().assertThat(myTask.editTaskNotes.getAttribute("value")).as("Task Note is accepting more then 300 character").hasSize(300);
                    } else if (!data.get("taskNote").isEmpty() && data.get("taskNote").equals("maxLength")) {
                        random = "A" + getRandomString(999);
                        CRM_GeneralTaskStepDef.taskValues.get().put("taskNote", random);
                        clearAndFillText(myTask.editTaskNotes, CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
                        sendKeyToTextField(myTask.editTaskNotes, getRandomString(45));
                        sa.get().assertThat(myTask.editTaskNotes.getAttribute("value")).as("Task Note is accepting more then 1000 character").hasSize(1000);
                    } else
                        fillText(data.get("taskNote"), "taskNote", myTask.editTaskNotes);
                    break;
                     */
                case "externalApplicationId":
                    if (!data.get("externalApplicationId").isEmpty() && data.get("externalApplicationId").equals("random")) {
                        editHistory.get().put("externalApplicationId", CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                        random = "T" + getRandomNumber(8);
                        CRM_GeneralTaskStepDef.taskValues.get().put("externalApplicationId", random);
                        clearAndFillText(createGeneralTask.externalApplicationId, CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                        if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE") || CRM_GeneralTaskStepDef.taskValues.get().get("taskType").equals("NJ")) {
                            sa.get().assertThat(createGeneralTask.externalApplicationId.getAttribute("value")).as("EXTERNAL APPLICATION ID is not accepting 30 character").hasSize(30);
                        } else {
                            sa.get().assertThat(createGeneralTask.externalApplicationId.getAttribute("value")).as("EXTERNAL APPLICATION ID is accepting more then 9 character").hasSize(9);
                        }
                    } else
                        fillText(data.get("externalApplicationId"), "externalApplicationId", createGeneralTask.externalApplicationId);
                    break;
                case "externalCaseId":
                    if (!data.get("externalCaseId").isEmpty() && data.get("externalCaseId").equals("random")) {
                        random = "A" + getRandomString(29);
                        CRM_GeneralTaskStepDef.taskValues.get().put("externalCaseId", random);
                        clearAndFillText(createGeneralTask.externalCaseId, CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId"));
                        sendKeyToTextField(createGeneralTask.externalCaseId, getRandomString(10));
                        sa.get().assertThat(createGeneralTask.externalCaseId.getAttribute("value")).as("EXTERNAL CASE ID is accepting more then 300 character").hasSize(30);
                    } else
                        fillText(data.get("externalCaseId"), "externalCaseId", createGeneralTask.externalCaseId);
                    break;
                case "externalConsumerID":
                    if (!data.get("externalConsumerID").isEmpty() && data.get("externalConsumerID").equals("random")) {
                        random = "A" + getRandomString(29);
                        CRM_GeneralTaskStepDef.taskValues.get().put("externalConsumerID", random);
                        clearAndFillText(createGeneralTask.externalConsumerID,
                                CRM_GeneralTaskStepDef.taskValues.get().get("externalConsumerID"));
                        sendKeyToTextField(createGeneralTask.externalConsumerID, getRandomString(10));
                        sa.get().assertThat(createGeneralTask.externalConsumerID.getAttribute("value")).as("EXTERNAL CASE ID is accepting more then 300 character").hasSize(30);
                    } else
                        fillText(data.get("externalConsumerID"), "externalConsumerID", createGeneralTask.externalConsumerID);
                    break;
                case "reasonForEdit":
                    if (!data.get("reasonForEdit").isEmpty()) {
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        waitFor(3);
                        actions.moveToElement(myTask.reasonForEditDropDown).click().perform();
                        waitFor(3);
                        actions.moveToElement(createGeneralTask.reasonForEditCorrectedDataEntry).click().perform();
                        waitFor(3);
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        CRM_GeneralTaskStepDef.taskValues.get().put("reasonForEdit", data.get("reasonForEdit"));
                    } else {
                        CRM_GeneralTaskStepDef.taskValues.get().put("reasonForEdit", "-- --");
                    }
                    break;
                case "reasonForOnHold":
                    editHistory.get().put("reasonForOnHold", "-- --");
                    if (!data.get("reasonForOnHold").isEmpty()) {
                        selectDropDown(myTask.reasonForHoldDropDown, data.get("reasonForOnHold"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("reasonForOnHold", data.get("reasonForOnHold"));
                    } else {
                        if (!myTask.reasonForHoldDropDown.getText().isEmpty())
                            selectDropDownByIndex(myTask.reasonForHoldDropDown, myTask.reasonDropDownVlu, 0);
                    }
                    break;
                case "complaintAbout":
                    selectSingleSelectDD(data.get("complaintAbout"), "complaintAbout", createGeneralTask.complaintAboutDrDn);
                    break;
                case "reason":
                    selectSingleSelectDD(data.get("reason"), "reason", createGeneralTask.reasonDrDn);
                    break;
                case "transferReason":
                    selectSingleSelectDD(data.get("transferReason"), "transferReason", createGeneralTask.transferReason);
                    break;
                case "name":
                    if (!data.get("name").isEmpty() && data.get("name").equals("random")) {
                        random = "A" + getRandomString(59);
                        CRM_GeneralTaskStepDef.taskValues.get().put("name", random);
                        clearAndFillText(createGeneralTask.name, CRM_GeneralTaskStepDef.taskValues.get().get("name"));
                        createGeneralTask.name.sendKeys(getRandomString(10));
                        assertEquals(createGeneralTask.name.getAttribute("value").length(), 60, "NAME is accepting more then 60 character");
                    } else
                        fillText(data.get("name"), "name", createGeneralTask.name);
                    break;
                case "deselectActionTaken":
                    if (!data.get("deselectActionTaken").isEmpty()) {
                        if (data.get("deselectActionTaken").contains(",")) {
                            String[] actionTakenValues = data.get("deselectActionTaken").split(",");
                            for (String actionTakenValue : actionTakenValues) {
                                waitFor(1);
                                selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.actionTaken, actionTakenValue.trim());
                            }
                        } else {
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.actionTaken, data.get("deselectActionTaken"));
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", "-- --");
                    }
                    break;
                case "actionTaken":
                    editHistory.get().put("actionTaken", CRM_GeneralTaskStepDef.actionTaken1stVlu.get());
                    if (!createGeneralTask.actionTaken.getText().isEmpty()) {
                        //in order to update the dd it needs to empty it first
                        generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.actionTaken);
                    }
                    synchronized (CRM_GeneralTaskStepDef.selectValue) {
                        CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());
                    }
                    if (data.get("actionTaken").isEmpty()) {
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", "-- --");
                    } else {
                        scrollToElement(createGeneralTask.actionTaken, false);
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", data.get("actionTaken"));
                        createGeneralTask.actionTaken.click();
                        String[] expectedValues = data.get("actionTaken").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu) {
                                    CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                }
                            }
                            Driver.getDriver().findElement(By.xpath("//div[@id='menu-actionTaken']//ul/li[text()='" + expectedValues[i] + "']")).click();
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                    }
                    break;
                case "contactReason":
                    if (!createGeneralTask.cantactReason.getText().isEmpty()) {
                        //in order to update the dd it needs to empty it first
                        generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.cantactReason);
                    }
                    if (!data.get("contactReason").isEmpty()) {
                        if (data.get("contactReason").contains(",")) {
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.cantactReason, data.get("contactReason").split(",")[0]);
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.cantactReason, data.get("contactReason").split(",")[1]);
                        } else {
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.cantactReason, data.get("contactReason"));
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("contactReason", data.get("contactReason"));
                    } else {
                        createGeneralTask.cantactReason.click();
                        hover(createGeneralTask.cantactReason);
                        for (int i = 0; i < myTask.multiSelectedVlus.size(); ) {
                            click(myTask.multiSelectedVlus.get(i++));
                            i--;
                            waitFor(1);
                        }
                        actions.click(createGeneralTask.txtTaskInfo).build().perform();
                        CRM_GeneralTaskStepDef.taskValues.get().put("contactReason", "-- --");
                    }
                    break;
                case "preferredCallBackDate":
                    fillDate(data.get("preferredCallBackDate"), "preferredCallBackDate", createGeneralTask.preferredCallBackDate);
                    break;
                case "preferredCallBackTime":
                    fillText(data.get("preferredCallBackTime"), "preferredCallBackTime", createGeneralTask.preferredCallBackTime);
                    break;
                case "preferredPhone":
                    fillText(data.get("preferredPhone"), "preferredPhone", createGeneralTask.preferredPhoneTxtBox);
                    break;
                case "documentDueDate":
                    fillDate(data.get("documentDueDate"), "documentDueDate", createGeneralTask.dueDate);
                    break;
                case "contactRecordSingle":
                    selectSingleSelectDD(data.get("contactRecordSingle"), "contactReason", createGeneralTask.contactReason);
                    break;
                case "cpCRID":
                    fillText(data.get("cpCRID"), "cpCRID", createGeneralTask.cpCRId);
                    break;
                case "cpSRID":
                    fillText(data.get("cpSRID"), "cpSRID", createGeneralTask.cpSRId);
                    break;
                case "cpTaskID":
                    fillText(data.get("cpTaskID"), "cpTaskID", createGeneralTask.cpTaskId);
                    break;
                case "actionTakenSingle":
                    //   selectFromMultiSelectDropDownForWithEscapedKey(myTask.editActionTakenSingleDD, data.get("actionTakenSingle"));
                    if (data.get("actionTakenSingle").isEmpty()) {
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTakenSingle", "-- --");
                    } else {
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTakenSingle", data.get("actionTakenSingle"));
                    }
                    waitFor(2);
                    selectSingleSelectDD(data.get("actionTakenSingle"), "actionTakenSingle", myTask.editActionTakenSingleDD);
                    break;
                case "InbDocType":
                    if (!data.get("InbDocType").isEmpty()) {
                        if (!createGeneralTask.inboundCorrespondenceType.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.inboundCorrespondenceType);
                        CRM_GeneralTaskStepDef.taskValues.get().put("InbDocType", "-- --");
                        editHistory.get().put("InbDocType", "-- --");
                        if (data.get("InbDocType").contains(",")) {
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.inboundCorrespondenceType, data.get("InbDocType").split(",")[0]);
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.inboundCorrespondenceType, data.get("InbDocType").split(",")[1]);
                        } else
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.inboundCorrespondenceType, data.get("InbDocType"));
                        editHistory.get().put("InbDocType", CRM_GeneralTaskStepDef.taskValues.get().get("InbDocType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("InbDocType", data.get("InbDocType"));
                    } else {
                        if (!createGeneralTask.inboundCorrespondenceType.getText().isEmpty()) {
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.inboundCorrespondenceType);
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("InbDocType", "-- --");
                        editHistory.get().put("InbDocType", "-- --");
                    }
                    break;
                case "program":
                    if (!data.get("program").isEmpty()) {
                        if (!createGeneralTask.program.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.program);
                        selectOptionFromMultiSelectDropDown(createGeneralTask.program, data.get("program"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("program", data.get("program"));
                    } else {
                        if (!createGeneralTask.program.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.program);
                        CRM_GeneralTaskStepDef.taskValues.get().put("program", "-- --");
                    }
                    break;
                case "dateResent":
                    fillDate(data.get("dateResent"), "dateResent", createGeneralTask.dateResent);
                    break;
                case "receivedDate":
                    fillDate(data.get("receivedDate"), "receivedDate", createGeneralTask.receivedDate);
                    break;
                case "businessUnit":
                    selectSingleSelectDD(data.get("businessUnit"), "businessUnit", createGeneralTask.taskBusinessUnit);
                    break;
                case "assigneeBusinessUnit":
                    selectDropDown(createGeneralTask.lstAssigneeBusinessUnit, data.get("assigneeBusinessUnit"));
                    CRM_GeneralTaskStepDef.taskValues.get().put("assigneeBusinessUnit", data.get("assigneeBusinessUnit"));
                    break;
                case "returnedMailReason":
                    selectSingleSelectDD(data.get("returnedMailReason"), "returnedMailReason", createGeneralTask.returnedMailReason);
                    break;
                case "disposition":
                    selectSingleSelectDD(data.get("disposition"), "disposition", createGeneralTask.disposition);
                    break;
                case "ARFirstName":
                    fillText(data.get("ARFirstName"), "ARFirstName", createGeneralTask.ARFirstNameTxtBox);
                    break;
                case "ARLastName":
                    fillText(data.get("ARLastName"), "ARLastName", createGeneralTask.ARLastNameTxtBox);
                    break;
                case "ARPhone":
                    fillText(data.get("ARPhone"), "ARPhone", createGeneralTask.ARPhone);
                    break;
                case "AREmail":
                    fillText(data.get("AREmail"), "AREmail", createGeneralTask.AREmail);
                    break;
                case "Organization":
                    fillText(data.get("Organization"), "Organization", createGeneralTask.organization);
                    break;
                case "returnedMailReceivedDate":
                    fillDate(data.get("returnedMailReceivedDate"), "returnedMailReceivedDate", createGeneralTask.returnedMailReceivedDate);
                    break;
                case "locality":
                    selectSingleSelectDD(data.get("locality"), "locality", createGeneralTask.taskLocality);
                    break;
                case "requestType":
                    selectSingleSelectDD(data.get("requestType"), "requestType", createGeneralTask.taskRequestType);
                    break;
                case "issueType":
                    selectSingleSelectDD(data.get("issueType"), "issueType", createGeneralTask.taskIssueType);
                    break;
                case "caseWorkerFirstName":
                    fillText(data.get("caseWorkerFirstName"), "caseWorkerFirstName", createGeneralTask.caseWorkerFirstNameTxtBox);
                    break;
                case "caseWorkerLastName":
                    fillText(data.get("caseWorkerLastName"), "caseWorkerLastName", createGeneralTask.caseWorkerLastNameTxtBox);
                    break;
                case "outcome":
                    selectSingleSelectDD(data.get("outcome"), "outcome", createGeneralTask.outcome);
                    break;
                case "informationType":
                    selectSingleSelectDD(data.get("informationType"), "informationType", createGeneralTask.informationType);
                    break;
                case "language":
                    selectSingleSelectDD(data.get("language"), "language", createGeneralTask.preferredLanguage);
                    break;
                case "dateTranslationEscalated":
                    fillDate(data.get("dateTranslationEscalated"), "dateTranslationEscalated", createGeneralTask.dateTranslationEscalated);
                    break;
                case "dateTranslationReceived":
                    fillDate(data.get("dateTranslationReceived"), "dateTranslationReceived", createGeneralTask.dateTranslationReceived);
                    break;
                case "dateTranslationMailed":
                    fillDate(data.get("dateTranslationMailed"), "dateTranslationMailed", createGeneralTask.dateTranslationMailed);
                    break;
                case "remandReason":
                    if (!data.get("remandReason").isEmpty()) {
                        if (!createGeneralTask.remandReason.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.remandReason);
                        synchronized (CRM_GeneralTaskStepDef.selectValue) {
                            CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());
                        }
                        createGeneralTask.remandReason.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("remandReason", data.get("remandReason"));
                        waitForVisibility(Driver.getDriver().findElements(By.xpath("//div[@id='menu-remandReason']//ul/li")).get(0), 5);
                        String[] expectedValues = data.get("remandReason").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu) {
                                    CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-remandReason']//ul/li[text()='" + expectedValues[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                    } else {
                        if (!createGeneralTask.remandReason.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.remandReason);
                        CRM_GeneralTaskStepDef.taskValues.get().put("remandReason", "-- --");
                    }
                    break;
                case "eligibilityDecision":
                    selectSingleSelectDD(data.get("eligibilityDecision"), "eligibilityDecision", createGeneralTask.eligibilityDecision);
                    break;
                case "remandCompletionDate":
                    fillDate(data.get("remandCompletionDate"), "remandCompletionDate", createGeneralTask.remandCompletionDate);
                    break;
                case "remandDueDate":
                    fillDate(data.get("remandDueDate"), "remandDueDate", createGeneralTask.remandDueDate);
                    break;
                case "missingInfoRequired":
                    selectSingleSelectDD(data.get("missingInfoRequired"), "missingInfoRequired", createGeneralTask.missingInfoRequired);
                    break;
                case "vclDueDate":
                    fillDate(data.get("vclDueDate"), "vclDueDate", createGeneralTask.vclDueDate);
                    break;
                case "applicationReceivedDate":
                    fillDate(data.get("applicationReceivedDate"), "applicationReceivedDate", createGeneralTask.appReceivedDate);
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("taskType").equals("Renewal SR") && data.get("applicationReceivedDate").equals("today")) {
                        CRM_GeneralTaskStepDef.taskValues.get().put("renewalProcessingDueDate", getFutureDate(29));
                    }
                    break;
                case "applicationStatus":
                    selectSingleSelectDD(data.get("applicationStatus"), "applicationStatus", createGeneralTask.appStatus);
                    break;
                case "vclSentDate":
                    fillDate(data.get("vclSentDate"), "vclSentDate", createGeneralTask.vclSentDate);
                    break;
                case "noOfApplicantsInHousehold":
                    fillText(data.get("noOfApplicantsInHousehold"), "noOfApplicantsInHousehold", createGeneralTask.noOfApplicantsInHousehold);
                    break;
                case "channel":
                    selectSingleSelectDD(data.get("channel"), "channel", createGeneralTask.channel);
                    break;
                case "appointmentDate":
                    fillDate(data.get("appointmentDate"), "appointmentDate", createGeneralTask.appointmentDate);
                    break;
                case "appointmentTime":
                    fillText(data.get("appointmentTime"), "appointmentTime", createGeneralTask.appointmentTime);
                    break;
                case "caseStatus":
                    selectSingleSelectDD(data.get("caseStatus"), "caseStatus", createGeneralTask.caseStatus);
                    break;
                case "preHearingOutcome":
                    if (!data.get("preHearingOutcome").isEmpty()) {
                        if (!createGeneralTask.preHearingOutcome.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.preHearingOutcome);
                        synchronized (CRM_GeneralTaskStepDef.selectValue) {
                            CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());
                        }
                        createGeneralTask.preHearingOutcome.click();
                        waitForVisibility(createGeneralTask.preHearingOutcomeDrpDow.get(0), 5);
                        String[] expectedValues = data.get("preHearingOutcome").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu) {
                                    CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-pre-HearingOutcome']//ul/li[text()='" + expectedValues[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        CRM_GeneralTaskStepDef.taskValues.get().put("preHearingOutcome", data.get("preHearingOutcome"));
                    } else {
                        if (!createGeneralTask.preHearingOutcome.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.preHearingOutcome);
                        CRM_GeneralTaskStepDef.taskValues.get().put("preHearingOutcome", "-- --");
                    }
                    break;
                case "preHearingReason":
                    if (!data.get("preHearingReason").isEmpty()) {
                        if (!createGeneralTask.preHearingReason.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.preHearingReason);
                        synchronized (CRM_GeneralTaskStepDef.selectValue) {
                            CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());
                        }
                        createGeneralTask.preHearingReason.click();
                        waitForVisibility(createGeneralTask.preHearingReasonDrpDow.get(0), 5);
                        String[] expectedValues = data.get("preHearingReason").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu) {
                                    CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-pre-HearingReason']//ul/li[text()='" + expectedValues[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        CRM_GeneralTaskStepDef.taskValues.get().put("preHearingReason", data.get("preHearingReason"));
                    } else {
                        if (!createGeneralTask.preHearingReason.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.preHearingReason);
                        CRM_GeneralTaskStepDef.taskValues.get().put("preHearingReason", "-- --");
                    }
                    break;
                case "ARAddressLine1":
                    fillText(data.get("ARAddressLine1"), "ARAddressLine1", createGeneralTask.arAddressLine1TxtBox);
                    break;
                case "ARAddressLine2":
                    fillText(data.get("ARAddressLine2"), "ARAddressLine2", createGeneralTask.arAddressLine2TxtBox);
                    break;
                case "ARCity":
                    fillText(data.get("ARCity"), "ARCity", createGeneralTask.arCityTxtBox);
                    break;
                case "ARState":
                    fillText(data.get("ARState"), "ARState", createGeneralTask.arStateTxtBox);
                    break;
                case "ARZipCode":
                    fillText(data.get("ARZipCode"), "ARZipCode", createGeneralTask.arZipCodeTxtBox);
                    break;
                case "facilityType":
                    if (!data.get("facilityType").isEmpty()) {
                        selectSingleSelectDD(data.get("facilityType"), "facilityType", createGeneralTask.facilityType);
                    } else {
                        CRM_GeneralTaskStepDef.taskValues.get().put("facilityType", "-- --");
                    }
                    break;
                case "facilityName":
                    if (!data.get("facilityName").isEmpty()) {
                        selectSingleSelectDD(data.get("facilityName"), "facilityName", createGeneralTask.facilityName);
                        CRM_GeneralTaskStepDef.taskValues.get().put("facilityName", data.get("facilityName"));
                    } else {
                        CRM_GeneralTaskStepDef.taskValues.get().put("facilityName", "-- --");
                    }
                    break;
                case "myWorkSpaceDate":
                    fillDate(data.get("myWorkSpaceDate"), "myWorkSpaceDate", createGeneralTask.myWorkSpaceDate);
                    break;
                case "vcccResponseDate":
                    fillDate(data.get("vcccResponseDate"), "vcccResponseDate", createGeneralTask.vcccResponseDate);
                    break;
                case "dataSentToVcc":
                    fillDate(data.get("dataSentToVcc"), "dataSentToVcc", createGeneralTask.dateSenttoVccc);
                    break;
                case "applicationSignatureDate":
                    fillDate(data.get("applicationSignatureDate"), "applicationSignatureDate", createGeneralTask.applicatiSignatureDate);
                    break;
                case "denialReason":
                    if (!data.get("denialReason").isEmpty()) {
                        if (!createGeneralTask.denialReason.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.denialReason);
                        synchronized (CRM_GeneralTaskStepDef.selectValue) {
                            CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());
                        }
                        createGeneralTask.denialReason.click();
                        editHistory.get().put("denialReason", CRM_GeneralTaskStepDef.taskValues.get().get("denialReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("denialReason", data.get("denialReason"));
                        waitForVisibility(Driver.getDriver().findElements(By.xpath("//div[@id='menu-denialReason']//ul/li")).get(0), 5);
                        String[] expectedValues = data.get("denialReason").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu) {
                                    CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-denialReason']//ul/li[text()='" + expectedValues[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                    } else {
                        if (!createGeneralTask.denialReason.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.denialReason);
                        CRM_GeneralTaskStepDef.taskValues.get().put("denialReason", "-- --");
                    }
                    break;
                case "complaintType":
                    selectSingleSelectDD(data.get("complaintType"), "complaintType", createGeneralTask.taskComplaintType);
                    break;
                case "applicationType":
                    selectSingleSelectDD(data.get("applicationType"), "applicationType", createGeneralTask.applicationType);
                    break;
                case "denialReasonSingle":
                    selectSingleSelectDD(data.get("denialReasonSingle"), "denialReasonSingle", createGeneralTask.denialReason);
                    break;
                case "dmasReceivedDate":
                    fillDate(data.get("dmasReceivedDate"), "dmasReceivedDate", createGeneralTask.dmasReceivedDate);
                    break;
                case "coverVAReceivedDate":
                    fillDate(data.get("coverVAReceivedDate"), "coverVAReceivedDate", createGeneralTask.coverVAReceivedDate);
                    break;
                case "appealReason":
                    if (!data.get("appealReason").isEmpty()) {
                        if (!createGeneralTask.appealReason.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.appealReason);
                        CRM_GeneralTaskStepDef.taskValues.get().put("appealReason", data.get("appealReason"));
                        selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.appealReason, data.get("appealReason"));
                    } else {
                        if (!createGeneralTask.appealReason.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.appealReason);
                        CRM_GeneralTaskStepDef.taskValues.get().put("appealReason", "-- --");
                    }
                    break;
                case "appealCaseSummaryDueDate":
                    fillDate(data.get("appealCaseSummaryDueDate"), "appealCaseSummaryDueDate", createGeneralTask.appealCaseSummaryDueDate);
                    break;
                case "appealsCaseSummaryHyperlink":
                    fillText(data.get("appealsCaseSummaryHyperlink"), "appealsCaseSummaryHyperlink", createGeneralTask.appealsCaseSummaryHyperlink);
                    break;
                case "appealCaseSummaryStatus":
                    selectSingleSelectDD(data.get("appealCaseSummaryStatus"), "appealCaseSummaryStatus", createGeneralTask.appealCaseSummaryStatus);
                    break;
                case "reviewOutcome":
                    if (!data.get("reviewOutcome").isEmpty()) {
                        if (!createGeneralTask.reviewOutcome.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.reviewOutcome);
                        synchronized (CRM_GeneralTaskStepDef.selectValue) {
                            CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());
                        }
                        createGeneralTask.reviewOutcome.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("reviewOutcome", data.get("reviewOutcome"));
                        waitForVisibility(Driver.getDriver().findElements(By.xpath("//div[@id='menu-reviewOutcome']//ul/li")).get(0), 5);
                        String[] expectedValues = data.get("reviewOutcome").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu) {
                                    CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-reviewOutcome']//ul/li[text()='" + expectedValues[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                    } else {
                        if (!createGeneralTask.reviewOutcome.getText().isEmpty())
                            generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.reviewOutcome);
                        CRM_GeneralTaskStepDef.taskValues.get().put("reviewOutcome", "-- --");
                    }
                    break;

                case "decisionSource":
                    scrollUpUsingActions(1);
                    selectSingleSelectDD(data.get("decisionSource"), "decisionSource", createGeneralTask.decisionSource);
                    break;

                case "interimStatus":
                    selectSingleSelectDD(data.get("interimStatus"), "interimStatus", createGeneralTask.interimStatus);
                    break;

                case "other":
                    if (!data.get("other").isEmpty() && data.get("other").equals("maxlength")) {
                        random = "A%*# 12345" + getRandomString(90);
                        CRM_GeneralTaskStepDef.taskValues.get().put("other", random);
                        clearAndFillText(createGeneralTask.other, CRM_GeneralTaskStepDef.taskValues.get().get("other"));
                        sendKeyToTextField(createGeneralTask.other, getRandomString(45));
                        assertEquals(createGeneralTask.other.getAttribute("value").length(), 100, "Other is accepting more then 1000 character");
                    } else
                        fillText(data.get("other"), "other", createGeneralTask.other);
                    break;
                case "currentPlan":
                    selectSingleSelectDD(data.get("currentPlan"), "currentPlan", createGeneralTask.currentPlan);
                    break;
                case "planChangeReason":
                    selectSingleSelectDD(data.get("planChangeReason"), "planChangeReason", createGeneralTask.planChangeReason);
                    break;
                case "memberName":
                    fillText(data.get("memberName"), "memberName", createGeneralTask.memberName);
                    break;
                case "disenrollmentReason":
                    selectSingleSelectDD(data.get("disenrollmentReason"), "disenrollmentReason", createGeneralTask.disenrollmentReason);
                    break;
                case "disenrollmentDate":
                    fillDate(data.get("disenrollmentDate"), "disenrollmentDate", createGeneralTask.disenrollmentDate);
                    break;
                case "source":
                    selectSingleSelectDD(data.get("source"), "appSource", createGeneralTask.source);
                    break;
                case "DesiredPlan":
                    selectSingleSelectDD(data.get("DesiredPlan"), "DesiredPlan", createGeneralTask.desiredPlantxt);
                    break;
                case "contactName":
                    fillText(data.get("contactName"), "contactName", createGeneralTask.contactName);
                    break;
                case "escalated":
                    checkBox(data.get("escalated"), "escalated", createGeneralTask.escalated);
                    break;
                case "escalationReason":
                    selectSingleSelectDD(data.get("escalationReason"), "escalationReason", createGeneralTask.escalationReason);
                    break;
                case "organizationDD":
                    selectSingleSelectDD(data.get("organizationDD"), "organizationDD", createGeneralTask.organizationDD);
                    break;
                case "invalidReason":
                    waitFor(1);
                    selectSingleSelectDD(data.get("invalidReason"), "invalidReason", createGeneralTask.invalidReasonDD);
                    break;
                case "dateFollowupEmailSent":
                    fillDate(data.get("dateFollowupEmailSent"), "dateFollowupEmailSent", createGeneralTask.dateFollowUpEmailSent);
                    break;
                case "rid":
                    scrollToElement(createGeneralTask.rid,false);
                    fillText(data.get("rid"), "rid", createGeneralTask.rid);
                    break;
                case "decision":
                    selectSingleSelectDD(data.get("decision"), "decision", createGeneralTask.decision);
                    break;
                case "dateDecisionLetterSent":
                    fillDate(data.get("dateDecisionLetterSent"), "dateDecisionLetterSent", createGeneralTask.dateDecisionLetterSent);
                    break;
                case "finalDecision":
                    selectSingleSelectDD(data.get("finalDecision"), "finalDecision", createGeneralTask.finalDecision);
                    break;
                case "dateSateNotified":
                    scrollToElement(createGeneralTask.dateSateNotified,false);
                    fillDate(data.get("dateSateNotified"), "dateSateNotified", createGeneralTask.dateSateNotified);
                    break;
                case "consumerSatisfied":
                    selectSingleSelectDD(data.get("consumerSatisfied"), "consumerSatisfied", createGeneralTask.consumerSatisfied);
                    break;
                case "sentNOADate":
                    fillDate(data.get("sentNOADate"), "sentNOADate", createGeneralTask.sentNOADate);
                    break;
                case "medicaidId":
                    fillText(data.get("medicaidId"), "medicaidId", createGeneralTask.externalConsumerID);
                    break;
                case "requestedDate":
                    fillDate(data.get("requestedDate"), "requestedDate", createGeneralTask.requestedDate);
                    break;
                case "planName":
                    selectSingleSelectDD(data.get("planName"), "planName", createGeneralTask.planName);
                    break;
                case "planStartDate":
                    fillDate(data.get("planStartDate"), "planStartDate", createGeneralTask.planStartDate);
                    break;
                case "reasonExplanation":
                    int index = createGeneralTask.reasonExplanation.size() - 1;
                    selectSingleSelectDD(data.get("reasonExplanation"), "reasonExplanation", createGeneralTask.reasonExplanation.get(index));
                    break;
                case "explanation":
                    int index1 = createGeneralTask.explanation.size() - 1;
                    fillText(data.get("explanation"), "explanation", createGeneralTask.explanation.get(index1));
                    break;
                case "usedTobaccoInTheLastSixMonths":
                    selectSingleSelectDD(data.get("usedTobaccoInTheLastSixMonths"), "usedTobaccoInTheLastSixMonths", createGeneralTask.usedTobaccoInTheLastSixMonths);
                    break;
                case "grievance":
                    fillText(data.get("grievance"), "grievance", createGeneralTask.grievance);
                    break;
                case "contactPhone":
                    fillText(data.get("contactPhone"), "contactPhone", createGeneralTask.contactPhone);
                    break;
                case "desiredPlanJC":
                    selectSingleSelectDD(data.get("desiredPlanJC"), "desiredPlanJC", createGeneralTask.desiredPlantxt);
                    break;
                case "programRequired":
                    selectSingleSelectDD(data.get("programRequired"), "programRequired", createGeneralTask.programRequired);
                    break;
                case "dateHealthPlanContacted":
                    fillDate(data.get("dateHealthPlanContacted"), "dateHealthPlanContacted", createGeneralTask.dateHealthPlanContacted);
                    break;
                case "dateReceivedGrievance":
                    fillDate(data.get("dateReceivedGrievance"), "dateReceivedGrievance", createGeneralTask.dateReceivedGrievance);
                    break;
                case "invalid":
                    checkBox(data.get("invalid"), "invalid", myTask.invalidCheckBox);
                    break;
                case "dateOfVoicemail":
                    fillDate(data.get("dateOfVoicemail"), "dateOfVoicemail", createGeneralTask.dateOfVoicemail);
                    break;
                case "timeOfVoicemail":
                    fillText(data.get("timeOfVoicemail"), "timeOfVoicemail", createGeneralTask.timeOfVoicemail);
                    break;
                case "providerEmail":
                    fillText(data.get("providerEmail"), "providerEmail", createGeneralTask.providerEmail);
                    break;
                case "providerFirstName":
                    fillText(data.get("providerFirstName"), "providerFirstName", createGeneralTask.providerFirstNameTxtBox);
                    break;
                case "providerLastName":
                    fillText(data.get("providerLastName"), "providerLastName", createGeneralTask.providerLastNameTxtBox);
                    break;
                case "noOfApprovedApplicants":
                    fillText(data.get("noOfApprovedApplicants"), "noOfApprovedApplicants", createGeneralTask.numberOfApprovedApplicants);
                    break;
                case "addressType":
                    selectSingleSelectDD(data.get("addressType"), "addressType", createGeneralTask.addressType);
                    break;
                case "addressSource":
                    selectSingleSelectDD(data.get("addressSource"), "addressSource", createGeneralTask.addressSource);
                    break;
                case "addressZipCode":
                    fillText(data.get("addressZipCode"), "addressZipCode", createGeneralTask.addressZipCode);
                    break;
                case "providerPhone":
                    fillText(data.get("providerPhone"), "providerPhone", createGeneralTask.providerPhoneTxtBox);
                    break;
                case "hpe":
                    checkBox(data.get("hpe"), "hpe", myTask.hpeCheckBox);
                    break;
                case "closedRenewal":
                    checkBox(data.get("closedRenewal"), "closedRenewal", myTask.closedRenewalCheckBox);
                    break;
                case "csrName":
                    editHistory.get().put("csrName", CRM_GeneralTaskStepDef.taskValues.get().get("csrName"));
                    if (!data.get("csrName").isEmpty()) {
                        clearAndFillText(createGeneralTask.csrName, data.get("csrName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("csrName", data.get("csrName"));
                    } else {
                        createGeneralTask.csrName.click();
                        clearTextField(createGeneralTask.csrName);
                        CRM_GeneralTaskStepDef.taskValues.get().put("csrName", "-- --");
                    }
                    actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                    break;
                case "incidentDate":
                    fillDate(data.get("incidentDate"), "incidentDate", createGeneralTask.incidentDate);
                    break;
                case "businessUnitAssigneeTo":
                    selectSingleSelectDD(data.get("businessUnitAssigneeTo"), "businessUnitAssigneeTo", createGeneralTask.businessUnitAssignedTo);
                    break;
                case "actionTknSingle":
                    selectSingleSelectDD(data.get("actionTknSingle"), "actionTknSingle", myTask.editActionTakenSingleDD);
                    if (data.get("actionTknSingle").isEmpty()) {
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTknSingle", "-- --");
                    } else {
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTknSingle", data.get("actionTknSingle"));
                    }
                    break;
                case "districtDisposition":
                    scrollToElement(createGeneralTask.districtDispositionDropdown, false);
                    selectDropDownWithText(createGeneralTask.districtDispositionDropdown, data.get("districtDisposition"));
                    CRM_GeneralTaskStepDef.taskValues.get().put("districtDisposition", data.get("districtDisposition"));
                    break;
                case "cancelSR":
                    checkBox(data.get("cancelSR"), "cancelSR", createGeneralTask.cancelSRcheckbox);
                    break;
                case "renewalDate":
                    fillDate(data.get("renewalDate"), "renewalDate", createGeneralTask.renewalDate);
                    CRM_GeneralTaskStepDef.taskValues.get().put("renewalDate", data.get("renewalDate"));
                    CRM_GeneralTaskStepDef.taskValues.get().put("renewalCutoffDate", generalTask.get().getRenewalCutoffDate());
                    break;
                case "miReceivedDate":
                    fillDate(data.get("miReceivedDate"), "miReceivedDate", createGeneralTask.miReceivedDate);
                    CRM_GeneralTaskStepDef.taskValues.get().put("miReceivedDate", createGeneralTask.miReceivedDate.getAttribute("value"));
                    break;
                default:
                    if (!key.equals("reasonForCancel"))
                        sa.get().fail("Switch case not match for key " + key);
                    break;
            }
        }
        if (data.containsKey("reasonForCancel") && !data.get("reasonForCancel").isEmpty()) {
            selectDropDown(myTask.reasonForCancelDropDown, data.get("reasonForCancel"));
            editHistory.get().put("reasonForCancel", "-- --");
            CRM_GeneralTaskStepDef.taskValues.get().put("reasonForCancel", data.get("reasonForCancel"));
            CRM_TaskManagementStepDef.dueIn.set(CRM_GeneralTaskStepDef.taskValues.get().get("dueIn"));
            CRM_GeneralTaskStepDef.taskValues.get().put("dueIn", "-- --");
        } else if (!data.containsKey("reasonForCancel") && CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForCancel")) {
            CRM_GeneralTaskStepDef.taskValues.get().remove("reasonForCancel");
        }
    }

    public void checkBox(String key, String value, WebElement ele) {
        if (CRM_GeneralTaskStepDef.taskValues.get().get(value) != null)
            editHistory.get().put(value, CRM_GeneralTaskStepDef.taskValues.get().get(value));
        else
            editHistory.get().put(value, "-- --");
        if (!key.isEmpty() && key.contains("true") && !ele.isSelected()) {
            ele.click();
            CRM_GeneralTaskStepDef.taskValues.get().put(value, key);
        } else if (!key.isEmpty() && key.contains("false") && ele.isSelected()) {
            ele.click();
            CRM_GeneralTaskStepDef.taskValues.get().put(value, "-- --");
        } else
            CRM_GeneralTaskStepDef.taskValues.get().put(value, "-- --");
    }

    public void fillDate(String key, String value, WebElement ele) {
        if (CRM_GeneralTaskStepDef.taskValues.get().get(value) != null)
            editHistory.get().put(value, CRM_GeneralTaskStepDef.taskValues.get().get(value));
        else
            editHistory.get().put(value, "-- --");
        if (!key.isEmpty()) {
            sendKeyToTextField(ele, getDate(key));
            CRM_GeneralTaskStepDef.taskValues.get().put(value, ele.getAttribute("value"));
        } else if (ele.isEnabled()) {
            clearTextField(ele);
            CRM_GeneralTaskStepDef.taskValues.get().put(value, "-- --");
        } else
            CRM_GeneralTaskStepDef.taskValues.get().put(value, "-- --");
    }

    public void selectSingleSelectDD(String key, String value, WebElement ele) {
        if (CRM_GeneralTaskStepDef.taskValues.get().get(value) != null)
            editHistory.get().put(value, CRM_GeneralTaskStepDef.taskValues.get().get(value));
        else
            editHistory.get().put(value, "-- --");
        if (!key.isEmpty()) {
            selectDropDownSlider(ele, key);
            CRM_GeneralTaskStepDef.taskValues.get().put(value, key);
        } else if (!ele.getAttribute("class").contains("Mui-disabled")) {
            selectDropDownByIndex(ele, myTask.priorities, 0);
            CRM_GeneralTaskStepDef.taskValues.get().put(value, "-- --");
        } else {
            CRM_GeneralTaskStepDef.taskValues.get().put(value, "-- --");
        }
    }

    public void fillText(String key, String value, WebElement ele) {
        if (CRM_GeneralTaskStepDef.taskValues.get().get(value) != null)
            editHistory.get().put(value, CRM_GeneralTaskStepDef.taskValues.get().get(value));
        else
            editHistory.get().put(value, "-- --");
        if (!key.isEmpty()) {
            clearAndFillText(ele, key);
            CRM_GeneralTaskStepDef.taskValues.get().put(value, key);
        } else if (ele.isEnabled()) {
            clearTextField(ele);
            CRM_GeneralTaskStepDef.taskValues.get().put(value, "-- --");
        } else
            CRM_GeneralTaskStepDef.taskValues.get().put(value, "-- --");
    }

    @And("I verify that outbound correspondence type is not editable and read only")
    public void I_verify_that_outbound_correspondence_type_is_not_editable_and_read_only() {
        try {
            createGeneralTask.outboundCorrespondenceType.isEnabled();
            System.out.println("Element is clickable");
        } catch (Exception e) {
            assertTrue(isAttribtueAvailable(createGeneralTask.outboundCorrespondenceType, "aria-disabled"));
            assertFalse(createGeneralTask.outboundCorrespondenceType.isEnabled(), " add button should not be enable on this page");
        }
    }

    public static Map<String, String> replaceNullValuesInMap(Map<String, String> my_map, String val) {
        Map<String, String> newTable = new LinkedHashMap<>();
        for (Map.Entry<String,String> i : my_map.entrySet()){
            if (i.getValue() == null){
                newTable.put(i.getKey(),val);

            }else{
                newTable.put(i.getKey(),i.getValue());
            }

        }
        return newTable;
    }
}