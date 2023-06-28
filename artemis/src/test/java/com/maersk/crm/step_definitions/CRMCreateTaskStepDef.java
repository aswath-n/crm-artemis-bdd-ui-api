package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class CRMCreateTaskStepDef extends CRMUtilities implements ApiBase {

    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    Actions actions = new Actions(Driver.getDriver());
    final ThreadLocal<CRM_GeneralTaskStepDef> generalTask = ThreadLocal.withInitial(CRM_GeneralTaskStepDef::new);
    CRM_EditTaskStepDef editTaskStepDef= new CRM_EditTaskStepDef();


    @And("I will provide following information before creating task")
    public void provideTheTaskInformation(Map<String, String> data) {
        data=editTaskStepDef.replaceNullValuesInMap(data,"");
        waitFor(10);
        scrollToTop();
        for (String key : data.keySet()) {
            if (!data.get(key).isEmpty()) {
                switch (key) {
                    case "taskType":
                        selectDropDown(createGeneralTask.lstTaskType, data.get("taskType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("taskType", data.get("taskType"));
                        break;
                    case "status":
                        selectDropDown(createGeneralTask.taskStatus, data.get("status"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("status", data.get("status"));
                        break;
                    case "taskInfo":
                        //max length is increased to 3000 characters
                        if (data.get("taskInfo").equals("maxlength")) {
                            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", getRandomString(3000));
                            clearAndFillText(createGeneralTask.txtTaskInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                            sendKeyToTextField(createGeneralTask.txtTaskInfo, getRandomString(45));
                            assertEquals(createGeneralTask.txtTaskInfo.getAttribute("value").length(), 3000, "Task Information is accepting more then 1000 character");
                        } else if (data.get("taskInfo").equals("random")) {
                            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", getRandomString(300));
                            clearAndFillText(createGeneralTask.txtTaskInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                           // sendKeyToTextField(createGeneralTask.txtTaskInfo, getRandomString(45));
                            assertEquals(createGeneralTask.txtTaskInfo.getAttribute("value").length(), 300, "Task Information is accepting more then 300 character");
                        } else if (data.get("taskInfo").equals("Line Break")) {
                            clearAndFillText(createGeneralTask.txtTaskInfo, CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", "abdc\n" + "xyz\n" + "345\n" + "ABC1234%\n" + "ASHJ_9087"));
                            sendKeyToTextField(createGeneralTask.txtTaskInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
                        } else if (!data.get("taskInfo").isEmpty()) {
                            clearAndFillText(createGeneralTask.txtTaskInfo, data.get("taskInfo"));
                            CRM_GeneralTaskStepDef.taskValues.get().put("taskInfo", data.get("taskInfo"));
                        }
                        break;
                    case "assignee":
                        sendKeyToTextField(createGeneralTask.lstAssignee, data.get("assignee"));
                        break;
                    case "assigneeBusinessUnit":
                        selectDropDown(createGeneralTask.lstAssigneeBusinessUnit, data.get("assigneeBusinessUnit"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("assigneeBusinessUnit", data.get("assigneeBusinessUnit"));
                        break;
                    case "externalApplicationId":
                        if (data.get("externalApplicationId").equals("random")) {
                            CRM_GeneralTaskStepDef.taskValues.get().put("externalApplicationId", "T"+getRandomNumber(8));
                            clearAndFillText(createGeneralTask.externalApplicationId, CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                        } else if (!data.get("externalApplicationId").isEmpty()) {
                            clearAndFillText(createGeneralTask.externalApplicationId, data.get("externalApplicationId"));
                            CRM_GeneralTaskStepDef.taskValues.get().put("externalApplicationId", data.get("externalApplicationId"));
                        }
                        break;
                    case "externalCaseId":
                        sendKeyToTextField(createGeneralTask.externalCaseId, data.get("externalCaseId"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("externalCaseId", data.get("externalCaseId"));
                        break;
                    case "externalConsumerID":
                        sendKeyToTextField(createGeneralTask.externalConsumerID, data.get("externalConsumerID"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("externalConsumerID", data.get("externalConsumerID"));
                        break;
                    case "contactReason":
                        if (data.get("contactReason").contains(",")) {
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.cantactReason, data.get("contactReason").
                                    split(",")[0]);
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.cantactReason, data.get("contactReason").
                                    split(",")[1]);
                        } else {
                            selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.cantactReason, data.get("contactReason"));
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("contactReason", data.get("contactReason"));
                        break;
                    case "name":
                        sendKeyToTextField(createGeneralTask.name, data.get("name"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("name", data.get("name"));
                        break;
                    case "preferredPhone":
                        sendKeyToTextField(createGeneralTask.preferredPhoneTxtBox, data.get("preferredPhone"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("preferredPhone", data.get("preferredPhone"));
                        break;
                    case "preferredCallBackDate":
                        sendKeyToTextField(createGeneralTask.preferredCallBackDate, getDate(data.get("preferredCallBackDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("preferredCallBackDate", createGeneralTask.preferredCallBackDate.getAttribute("value"));
                        break;
                    case "preferredCallBackTime":
                        sendKeyToTextField(createGeneralTask.preferredCallBackTime, data.get("preferredCallBackTime"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("preferredCallBackTime", data.get("preferredCallBackTime"));
                        break;
                    case "Status":
                        selectDropDown(createGeneralTask.taskStatus, data.get("Status"));
                        break;
                    case "disposition":
                        selectDropDown(createGeneralTask.disposition, data.get("disposition"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("disposition", data.get("disposition"));
                        break;
                    case "priority":
                        selectDropDown(createGeneralTask.lstTaskPriority, data.get("priority"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("priority", data.get("priority"));
                        break;
                    case "complaintAbout":
                        selectDropDown(createGeneralTask.complaintAboutDrDn, data.get("complaintAbout"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("complaintAbout", data.get("complaintAbout"));
                        break;
                    case "reason":
                        selectDropDown(createGeneralTask.reasonDrDn, data.get("reason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("reason", data.get("reason"));
                        break;
                    case "actionTaken":
                        if (data.get("actionTaken").equals("-- --")) {
                            CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", data.get("actionTaken"));
                        } else {
                            synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                            if (!createGeneralTask.actionTaken.getText().isEmpty()) {
                                generalTask.get().deselectValueFromMultiSelectDropDown(createGeneralTask.actionTaken);
                            }
                            createGeneralTask.actionTaken.click();
                            CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", data.get("actionTaken"));
                            String[] expectedValues = data.get("actionTaken").split(",");
                            for (int i = 0; i < expectedValues.length; i++) {
                                if (i != 0) {
                                    CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                                } else {
                                    synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                        CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                    }
                                }
                                Driver.getDriver().findElement(By.xpath("//div[@id='menu-actionTaken']//ul/li[text()='" + expectedValues[i] + "']")).click();
                            }
                            actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        }
                        break;
                    case "channel":
                        selectDropDown(createGeneralTask.channel, data.get("channel"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("channel", data.get("channel"));
                        break;
                    case "newAddressLine1":
                        sendKeyToTextField(createGeneralTask.newAddressLine1TxtBox, data.get("newAddressLine1"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("newAddressLine1", data.get("newAddressLine1"));
                        break;
                    case "oldAddressLine1":
                        sendKeyToTextField(createGeneralTask.oldAddressLine1TxtBox, data.get("oldAddressLine1"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("oldAddressLine1", data.get("oldAddressLine1"));
                        break;
                    case "providerAddressLine1":
                        sendKeyToTextField(createGeneralTask.providerAddressLine1TxtBox, data.get("providerAddressLine1"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerAddressLine1", data.get("providerAddressLine1"));
                        break;
                    case "applicationType":
                        selectDropDown(createGeneralTask.applicationType, data.get("applicationType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("applicationType", data.get("applicationType"));
                        break;
                    case "informationType":
                        selectDropDown(createGeneralTask.informationType, data.get("informationType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("informationType", data.get("informationType"));
                        break;
                    case "language":
                        selectDropDown(createGeneralTask.preferredLanguage, data.get("language"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("language", data.get("language"));
                        break;
                    case "dateTranslationEscalated":
                        sendKeyToTextField(createGeneralTask.dateTranslationEscalated, getDate(data.get("dateTranslationEscalated")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateTranslationEscalated", createGeneralTask.dateTranslationEscalated.getAttribute("value"));
                        break;
                    case "dateTranslationReceived":
                        sendKeyToTextField(createGeneralTask.dateTranslationReceived, getDate(data.get("dateTranslationReceived")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateTranslationReceived", createGeneralTask.dateTranslationReceived.getAttribute("value"));
                        break;
                    case "dateTranslationMailed":
                        sendKeyToTextField(createGeneralTask.dateTranslationMailed, getDate(data.get("dateTranslationMailed")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateTranslationMailed", createGeneralTask.dateTranslationMailed.getAttribute("value"));
                        break;
                    case "actionTakenSingle":
                        selectDropDown(createGeneralTask.actionTakenSingle, data.get("actionTakenSingle"));
                        if (data.get("actionTakenSingle").isEmpty()) {
                            CRM_GeneralTaskStepDef.taskValues.get().put("actionTakenSingle", "-- --");
                        } else {
                            CRM_GeneralTaskStepDef.taskValues.get().put("actionTakenSingle", data.get("actionTakenSingle"));
                        }
                        break;
                    case "contactRecordSingle":
                        selectDropDown(createGeneralTask.contactReason, data.get("contactRecordSingle"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("contactReason", data.get("contactRecordSingle"));
                        break;
                    case "documentDueDate":
                        sendKeyToTextField(createGeneralTask.dueDate, getDate(data.get("documentDueDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("documentDueDate", createGeneralTask.dueDate.getAttribute("value"));
                        break;
                    case "cpCRID":
                        sendKeyToTextField(createGeneralTask.cpCRId, data.get("cpCRID"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("cpCRID", data.get("cpCRID"));
                        break;
                    case "cpSRID":
                        sendKeyToTextField(createGeneralTask.cpSRId, data.get("cpSRID"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("cpSRID", data.get("cpSRID"));
                        break;
                    case "cpTaskID":
                        sendKeyToTextField(createGeneralTask.cpTaskId, data.get("cpTaskID"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("cpTaskID", data.get("cpTaskID"));
                        break;
                    case "outcome":
                        selectDropDown(createGeneralTask.outcome, data.get("outcome"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("outcome", data.get("outcome"));
                        break;
                    case "program":
                        selectOptionFromMultiSelectDropDown(createGeneralTask.program, data.get("program"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("program", data.get("program"));
                        break;
                    case "InbDocType":
                        synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                        createGeneralTask.inboundCorrespondenceType.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("InbDocType", data.get("InbDocType"));
                        CRM_EditTaskStepDef.editHistory.get().put("InbDocType",data.get("InbDocType"));
                        waitForVisibility(createGeneralTask.inbDocTyDrDnValues.get(0), 5);
                        String[] expectedValues = data.get("InbDocType").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                        CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                    }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-inboundCorrespondenceType']//ul/li[text()='" + expectedValues[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        break;
                    case "remandReason":
                        synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                        createGeneralTask.remandReason.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("remandReason", data.get("remandReason"));
                        waitForVisibility(Driver.getDriver().findElements(By.xpath("//div[@id='menu-remandReason']//ul/li")).get(0), 5);
                        expectedValues = data.get("remandReason").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                        CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                    }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-remandReason']//ul/li[text()='" + expectedValues[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        break;
                    case "eligibilityDecision":
                        selectDropDown(createGeneralTask.eligibilityDecision, data.get("eligibilityDecision"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("eligibilityDecision", data.get("eligibilityDecision"));
                        break;
                    case "remandCompletionDate":
                        sendKeyToTextField(createGeneralTask.remandCompletionDate, getDate(data.get("remandCompletionDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("remandCompletionDate", createGeneralTask.remandCompletionDate.getAttribute("value"));
                        break;
                    case "remandDueDate":
                        sendKeyToTextField(createGeneralTask.remandDueDate, getDate(data.get("remandDueDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("remandDueDate", createGeneralTask.remandDueDate.getAttribute("value"));
                        break;
                    case "receivedDate":
                        sendKeyToTextField(createGeneralTask.receivedDate, getDate(data.get("receivedDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("receivedDate", createGeneralTask.receivedDate.getAttribute("value"));
                        break;
                    case "locality":
                        selectDropDown(createGeneralTask.taskLocality, data.get("locality"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("locality", data.get("locality"));
                        break;
                    case "requestType":
                        selectDropDown(createGeneralTask.taskRequestType, data.get("requestType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("requestType", data.get("requestType"));
                        break;
                    case "issueType":
                        selectDropDown(createGeneralTask.taskIssueType, data.get("issueType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("issueType", data.get("issueType"));
                        break;
                    case "caseWorkerFirstName":
                        sendKeyToTextField(createGeneralTask.caseWorkerFirstNameTxtBox, data.get("caseWorkerFirstName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("caseWorkerFirstName", data.get("caseWorkerFirstName"));
                        break;
                    case "caseWorkerLastName":
                        sendKeyToTextField(createGeneralTask.caseWorkerLastNameTxtBox, data.get("caseWorkerLastName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("caseWorkerLastName", data.get("caseWorkerLastName"));
                        break;
                    case "complaintType":
                        selectDropDown(createGeneralTask.taskComplaintType, data.get("complaintType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("complaintType", data.get("complaintType"));
                        break;
                    case "returnedMailReason":
                        selectDropDown(createGeneralTask.returnedMailReason, data.get("returnedMailReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("returnedMailReason", data.get("returnedMailReason"));
                        break;
                    case "businessUnit":
                        selectDropDown(createGeneralTask.taskBusinessUnit, data.get("businessUnit"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("businessUnit", data.get("businessUnit"));
                        break;
                    case "dateResent":
                        sendKeyToTextField(createGeneralTask.dateResent, getDate(data.get("dateResent")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateResent", createGeneralTask.dateResent.getAttribute("value"));
                        break;
                    case "accessibilityNeeded":
                        synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                        createGeneralTask.accessibilityNeeded.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("accessibilityNeeded", data.get("accessibilityNeeded"));
                        waitForVisibility(Driver.getDriver().findElements(By.xpath("//div[@id='menu-accessibilityNeeded']//ul/li")).get(0), 5);
                        expectedValues = data.get("accessibilityNeeded").split(",");
                        for (int i = 0; i < expectedValues.length; i++) {
                            if (i != 0) {
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                        CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                    }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-accessibilityNeeded']//ul/li[text()='" + expectedValues[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        break;
                    case "appointmentDate":
                        sendKeyToTextField(createGeneralTask.appointmentDate, getDate(data.get("appointmentDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("appointmentDate", createGeneralTask.appointmentDate.getAttribute("value"));
                        break;
                    case "appointmentTime":
                        sendKeyToTextField(createGeneralTask.appointmentTime, data.get("appointmentTime"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("appointmentTime", data.get("appointmentTime"));
                        break;
                    case "facilityType":
                        if (!createGeneralTask.facilityType.getAttribute("class").contains("Mui-disabled"))
                            selectDropDown(createGeneralTask.facilityType, data.get("facilityType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("facilityType", data.get("facilityType"));
                        break;
                    case "facilityName":
                        selectDropDown(createGeneralTask.facilityName, data.get("facilityName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("facilityName", data.get("facilityName"));
                        CRM_EditTaskStepDef.editHistory.get().put("facilityName",data.get("facilityName"));
                        if(CRM_GeneralTaskStepDef.taskValues.get().get("facilityName").equals("Bristol City Jail")){
                            CRM_GeneralTaskStepDef.taskValues.get().put("facilityType", "Local Jail");
                            CRM_EditTaskStepDef.editHistory.get().put("facilityType", "Local Jail");
                        }
                        break;
                    case "providerFirstName":
                        sendKeyToTextField(createGeneralTask.providerFirstNameTxtBox, data.get("providerFirstName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerFirstName", data.get("providerFirstName"));
                        break;
                    case "providerLastName":
                        sendKeyToTextField(createGeneralTask.providerLastNameTxtBox, data.get("providerLastName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerLastName", data.get("providerLastName"));
                        break;
                    case "providerPhone":
                        sendKeyToTextField(createGeneralTask.providerPhoneTxtBox, data.get("providerPhone"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerPhone", data.get("providerPhone"));
                        break;
                    case "providerEmail":
                        sendKeyToTextField(createGeneralTask.providerEmail, data.get("providerEmail"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerEmail", data.get("providerEmail"));
                        break;
                    case "ARFirstName":
                        sendKeyToTextField(createGeneralTask.ARFirstNameTxtBox, data.get("ARFirstName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ARFirstName", data.get("ARFirstName"));
                        break;
                    case "ARLastName":
                        sendKeyToTextField(createGeneralTask.ARLastNameTxtBox, data.get("ARLastName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ARLastName", data.get("ARLastName"));
                        break;
                    case "ARPhone":
                        sendKeyToTextField(createGeneralTask.ARPhone, data.get("ARPhone"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ARPhone", data.get("ARPhone"));
                        break;
                    case "AREmail":
                        sendKeyToTextField(createGeneralTask.AREmail, data.get("AREmail"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("AREmail", data.get("AREmail"));
                        break;
                    case "Organization":
                        sendKeyToTextField(createGeneralTask.organization, data.get("Organization"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("Organization", data.get("Organization"));
                        break;
                    case "myWorkSpaceDate":
                        sendKeyToTextField(createGeneralTask.myWorkSpaceDate, getDate(data.get("myWorkSpaceDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("myWorkSpaceDate", createGeneralTask.myWorkSpaceDate.getAttribute("value"));
                        break;
                    case "returnedMailReceivedDate":
                        sendKeyToTextField(createGeneralTask.returnedMailReceivedDate, getDate(data.get("returnedMailReceivedDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("returnedMailReceivedDate", createGeneralTask.returnedMailReceivedDate.getAttribute("value"));
                        break;
                    case "ARAddressLine1":
                        sendKeyToTextField(createGeneralTask.arAddressLine1TxtBox, data.get("ARAddressLine1"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ARAddressLine1", data.get("ARAddressLine1"));
                        break;
                    case "ARAddressLine2":
                        sendKeyToTextField(createGeneralTask.arAddressLine2TxtBox, data.get("ARAddressLine2"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ARAddressLine2", data.get("ARAddressLine2"));
                        break;
                    case "ARCity":
                        sendKeyToTextField(createGeneralTask.arCityTxtBox, data.get("ARCity"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ARCity", data.get("ARCity"));
                        break;
                    case "ARState":
                        sendKeyToTextField(createGeneralTask.arStateTxtBox, data.get("ARState"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ARState", data.get("ARState"));
                        break;
                    case "ARZipCode":
                        sendKeyToTextField(createGeneralTask.arZipCodeTxtBox, data.get("ARZipCode"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ARZipCode", data.get("ARZipCode"));
                        break;
                    case "missingInfoRequired":
                        selectDropDown(createGeneralTask.missingInfoRequired, data.get("missingInfoRequired"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("missingInfoRequired", data.get("missingInfoRequired"));
                        break;
                    case "vclDueDate":
                        sendKeyToTextField(createGeneralTask.vclDueDate, getDate(data.get("vclDueDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("vclDueDate", createGeneralTask.vclDueDate.getAttribute("value"));
                        break;
                    case "noOfApplicantsInHousehold":
                        sendKeyToTextField(createGeneralTask.noOfApplicantsInHousehold, data.get("noOfApplicantsInHousehold"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("noOfApplicantsInHousehold", data.get("noOfApplicantsInHousehold"));
                        break;
                    case "applicationReceivedDate":
                        if(data.get("applicationReceivedDate").equals("empty")){
                            sendKeyToTextField(createGeneralTask.appReceivedDate, "");
                        } else {
                            sendKeyToTextField(createGeneralTask.appReceivedDate, getDate(data.get("applicationReceivedDate")));
                            waitFor(3);
                            CRM_GeneralTaskStepDef.taskValues.get().put("applicationReceivedDate", createGeneralTask.appReceivedDate.getAttribute("value"));
                            if (Optional.ofNullable(CRM_GeneralTaskStepDef.taskValues.get().get("taskType")).orElse("").equals("Renewal SR") && data.get("applicationReceivedDate").equals("today")) {
                                actions.moveToElement(createGeneralTask.renewalProcessingDueDate);
                                CRM_GeneralTaskStepDef.taskValues.get().put("renewalProcessingDueDate", getFutureDate(29));
                            }
                        }
                        break;
                    case "applicationStatus":
                        selectDropDown(createGeneralTask.appStatus, data.get("applicationStatus"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("applicationStatus", data.get("applicationStatus"));
                        break;
                    case "vclSentDate":
                        sendKeyToTextField(createGeneralTask.vclSentDate, getDate(data.get("vclSentDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("vclSentDate", createGeneralTask.vclSentDate.getAttribute("value"));
                        break;
                    case "caseStatus":
                        selectDropDown(createGeneralTask.caseStatus, data.get("caseStatus"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("caseStatus", data.get("caseStatus"));
                        break;
                    case "preHearingOutcome":
                        if (!data.get("preHearingOutcome").isEmpty()) {
                            synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                            createGeneralTask.preHearingOutcome.click();
                            waitForVisibility(createGeneralTask.preHearingOutcomeDrpDow.get(0), 5);
                            expectedValues = data.get("preHearingOutcome").split(",");
                            for (int i = 0; i < expectedValues.length; i++) {
                                if (i != 0) {
                                    CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                                } else {
                                    synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                        CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                    }
                                }
                                click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-pre-HearingOutcome']//ul/li[text()='" + expectedValues[i] + "']")));
                            }
                            actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                            CRM_GeneralTaskStepDef.taskValues.get().put("preHearingOutcome", data.get("preHearingOutcome"));
                        } else {
                            selectOptionFromMultiSelectDropDown(createGeneralTask.preHearingOutcome, data.get("preHearingOutcome"));
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("preHearingOutcome", data.get("preHearingOutcome"));
                        break;
                    case "preHearingReason":
                        if (!data.get("preHearingReason").isEmpty()) {
                            synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                            createGeneralTask.preHearingReason.click();
                            waitForVisibility(createGeneralTask.preHearingReasonDrpDow.get(0), 5);
                            expectedValues = data.get("preHearingReason").split(",");
                            for (int i = 0; i < expectedValues.length; i++) {
                                if (i != 0) {
                                    CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                                } else {
                                    synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                        CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                                    }
                                }
                                click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-pre-HearingReason']//ul/li[text()='" + expectedValues[i] + "']")));
                            }
                            actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                            CRM_GeneralTaskStepDef.taskValues.get().put("preHearingReason", data.get("preHearingReason"));
                        } else {
                            selectOptionFromMultiSelectDropDown(createGeneralTask.preHearingReason, data.get("preHearingReason"));
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("preHearingReason", data.get("preHearingReason"));
                        break;

                    case "decisionSource":
                        selectDropDown(createGeneralTask.decisionSource, data.get("decisionSource"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("decisionSource", data.get("decisionSource"));
                        break;

                    case "denialReason":
                        selectFromMultiSelectDropDownForWithEscapedKey(createGeneralTask.denialReason, data.get("denialReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("denialReason", data.get("denialReason"));
                        break;

                    case "ldssReceivedDate":
                        sendKeyToTextField(createGeneralTask.ldssReceivedDate, getDate(data.get("ldssReceivedDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("ldssReceivedDate", createGeneralTask.ldssReceivedDate.getAttribute("value"));
                        break;

                    case "miReceivedDate":
                        sendKeyToTextField(createGeneralTask.miReceivedDate, getDate(data.get("miReceivedDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("miReceivedDate", createGeneralTask.miReceivedDate.getAttribute("value"));
                        break;

                    case "applicationSignatureDate":
                        sendKeyToTextField(createGeneralTask.applicatiSignatureDate, getDate(data.get("applicationSignatureDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("applicationSignatureDate", createGeneralTask.applicatiSignatureDate.getAttribute("value"));
                        break;

                    case "applicationUpdateDate":
                        sendKeyToTextField(createGeneralTask.applicationUpdateDate, getDate(data.get("applicationUpdateDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("applicationUpdateDate", createGeneralTask.applicationUpdateDate.getAttribute("value"));
                        break;

                    case "noOfApprovedApplicants":
                        clearAndFillTextWithActionClass(createGeneralTask.numberOfApprovedApplicants, data.get("noOfApprovedApplicants"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("noOfApprovedApplicants", data.get("noOfApprovedApplicants"));
                        break;

                    case "expedited":
                        myTask.expeditedCheckbox.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("expedited", data.get("expedited"));
                        break;

                    case "hpe":
                        myTask.hpeCheckBox.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("hpe", data.get("hpe"));
                        break;

                    case "closedRenewal":
                        myTask.closedRenewalCheckBox.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("closedRenewal", data.get("closedRenewal"));
                        break;

                    case "rid":
                        scrollToElement(createGeneralTask.rid,false);
                        sendKeyToTextField(createGeneralTask.rid, data.get("rid"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("rid", data.get("rid"));
                        break;

                    case "grievance":
                        sendKeyToTextField(createGeneralTask.grievance, data.get("grievance"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("grievance", data.get("grievance"));
                        break;

                    case "contactName":
                        sendKeyToTextField(createGeneralTask.contactName, data.get("contactName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("contactName", data.get("contactName"));
                        break;

                    case "contactPhone":
                        clearAndFillText(createGeneralTask.contactPhone, data.get("contactPhone"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("contactPhone", data.get("contactPhone"));
                        break;

                    case "desiredPlan":
                        selectDropDown(createGeneralTask.desiredPlan, data.get("desiredPlan"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("desiredPlan", data.get("desiredPlan"));
                        break;

                    case "planChangeReason":
                        selectDropDown(createGeneralTask.planChangeReason, data.get("planChangeReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("planChangeReason", data.get("planChangeReason"));
                        break;

                    case "memberName":
                        if(data.get("memberName").equals("consumerName")){
                            String consumerName=APIUtilitiesForUIScenarios.consumerFirstName.get()+" "+APIUtilitiesForUIScenarios.consumerLastName.get();
                            System.out.println("Dropdown consumer value: "+consumerName);
                            selectDropDown(createGeneralTask.memberNameDrpdown, consumerName);
                            CRM_GeneralTaskStepDef.taskValues.get().put("memberName", consumerName);
                        } else {
                            sendKeyToTextField(createGeneralTask.memberName, data.get("memberName"));
                            CRM_GeneralTaskStepDef.taskValues.get().put("memberName", data.get("memberName"));
                        }
                        break;

                    case "planName":
                        selectDropDown(createGeneralTask.planName, data.get("planName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("planName", data.get("planName"));
                        break;

                    case "consumerSatisfied":
                        selectDropDown(createGeneralTask.consumerSatisfied, data.get("consumerSatisfied"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("consumerSatisfied", data.get("consumerSatisfied"));
                        break;

                    case "finalDecision":
                        selectDropDown(createGeneralTask.finalDecision, data.get("finalDecision"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("finalDecision", data.get("finalDecision"));
                        break;

                    case "dateFollowupEmailSent":
                        scrollToElement(createGeneralTask.dateFollowUpEmailSent,true);
                        sendKeyToTextField(createGeneralTask.dateFollowUpEmailSent, getDate(data.get("dateFollowupEmailSent")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateFollowupEmailSent", createGeneralTask.dateFollowUpEmailSent.getAttribute("value"));
                        break;

                    case "dateReceivedGrievance":
                        sendKeyToTextField(createGeneralTask.dateReceivedGrievance, getDate(data.get("dateReceivedGrievance")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateReceivedGrievance", createGeneralTask.dateReceivedGrievance.getAttribute("value"));
                        break;

                    case "dateHealthPlanContacted":
                        sendKeyToTextField(createGeneralTask.dateHealthPlanContacted, getDate(data.get("dateHealthPlanContacted")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateHealthPlanContacted", createGeneralTask.dateHealthPlanContacted.getAttribute("value"));
                        break;

                    case "dateSateNotified":
                        sendKeyToTextField(createGeneralTask.dateSateNotified, getDate(data.get("dateSateNotified")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateSateNotified", createGeneralTask.dateSateNotified.getAttribute("value"));
                        break;

                    case "dateDecisionLetterSent":
                        clearAndFillTextWithActionClass(createGeneralTask.dateDecisionLetterSent, getDate(data.get("dateDecisionLetterSent")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateDecisionLetterSent", createGeneralTask.dateDecisionLetterSent.getAttribute("value"));
                        break;

                    case "decision":
                        selectDropDown(createGeneralTask.decision, data.get("decision"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("decision", data.get("decision"));
                        break;

                    case "requestedNewPlan":
                        myTask.requestedNewPlanCheckBox.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("requestedNewPlan", data.get("requestedNewPlan"));
                        break;

                    case "invalid":
                        if (data.get("invalid").equalsIgnoreCase("true")) {
                            myTask.invalidCheckBox.click();
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("invalid", data.get("invalid"));
                        break;

                    case "denialReasonSingle":
                        selectDropDown(createGeneralTask.denialReason, data.get("denialReasonSingle"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("denialReasonSingle", data.get("denialReasonSingle"));
                        break;

                    case "other":
                        if (data.get("other").equals("maxlength")) {
                            String random = "A" + getRandomString(99);
                            CRM_GeneralTaskStepDef.taskValues.get().put("other", random);
                            clearAndFillText(createGeneralTask.other, CRM_GeneralTaskStepDef.taskValues.get().get("other"));
                            sendKeyToTextField(createGeneralTask.other, getRandomString(45));
                            sa.get().assertThat(createGeneralTask.other.getAttribute("value")).as("Other is accepting more then 100 character").hasSize(100);
                        } else if (!data.get("other").isEmpty()) {
                            clearAndFillText(createGeneralTask.other, data.get("other"));
                            CRM_GeneralTaskStepDef.taskValues.get().put("other", data.get("other"));
                        }
                        break;

                    case "currentPlan":
                        selectDropDown(createGeneralTask.currentPlan, data.get("currentPlan"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("currentPlan", data.get("currentPlan"));
                        break;

                    case "disenrollmentReason":
                        selectDropDown(createGeneralTask.disenrollmentReason, data.get("disenrollmentReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("disenrollmentReason", data.get("disenrollmentReason"));
                        break;
                    case "source":
                        selectDropDown(createGeneralTask.source, data.get("source"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("appSource", data.get("source"));
                        break;
                    case "DesiredPlan":
                        selectDropDown(createGeneralTask.desiredPlantxt, data.get("DesiredPlan"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("DesiredPlan", data.get("DesiredPlan"));
                        break;
                    case "disenrollmentDate":
                        sendKeyToTextField(createGeneralTask.disenrollmentDate, getDate(data.get("disenrollmentDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("disenrollmentDate", createGeneralTask.disenrollmentDate.getAttribute("value"));
                        break;
                    case "fromName":
                        sendKeyToTextField(createGeneralTask.fromName, getDate(data.get("fromName")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("fromName", createGeneralTask.fromName.getAttribute("value"));
                        break;
                    case "incidentDate":
                        sendKeyToTextField(createGeneralTask.incidentDate, getDate(data.get("incidentDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("incidentDate", createGeneralTask.incidentDate.getAttribute("value"));
                        break;
                    case "renewalDate":
                        sendKeyToTextField(createGeneralTask.renewalDate, getDate(data.get("renewalDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("renewalDate", createGeneralTask.renewalDate.getAttribute("value"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("renewalCutoffDate", generalTask.get().getRenewalCutoffDate());
                        break;
                    case "escalated":
                        if (data.get("escalated").equalsIgnoreCase("true")) {
                            createGeneralTask.escalated.click();
                        } else if (data.get("escalated").equalsIgnoreCase("false") && createGeneralTask.escalated.isSelected()) {
                            createGeneralTask.escalated.click();
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("escalated", data.get("escalated"));
                        break;
                    case "escalationReason":
                        selectDropDown(createGeneralTask.escalationReason, data.get("escalationReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("escalationReason", data.get("escalationReason"));
                        break;
                    case "organizationDD":
                        selectDropDown(createGeneralTask.organizationDD, data.get("organizationDD"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("organizationDD", data.get("organizationDD"));
                        break;
                    case "csrName":
                        createGeneralTask.csrName.sendKeys(data.get("csrName"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("csrName", data.get("csrName"));
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        break;
                    case "invalidReason":
                        selectDropDown(createGeneralTask.invalidReasonDD, data.get("invalidReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("invalidReason", data.get("invalidReason"));
                        break;
                    case "reasonExplanation":
                        int index = createGeneralTask.reasonExplanation.size() - 1;
                        selectDropDown(createGeneralTask.reasonExplanation.get(index), data.get("reasonExplanation"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("reasonExplanation", data.get("reasonExplanation"));
                        break;
                    case "explanation":
                        int index1 = createGeneralTask.explanation.size() - 1;
                        sendKeyToTextField(createGeneralTask.explanation.get(index1), getDate(data.get("explanation")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("explanation", data.get("explanation"));
                        break;
                    case "desiredPlanJC":
                        selectDropDown(createGeneralTask.desiredPlantxt, data.get("desiredPlanJC"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("desiredPlanJC", data.get("desiredPlanJC"));
                        break;
                    case "programRequired":
                        selectDropDown(createGeneralTask.programRequired, data.get("programRequired"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("programRequired", data.get("programRequired"));
                        break;
                    case "newPlanStartDate":
                        sendKeyToTextField(createGeneralTask.newPlanStartDate, getDate(data.get("newPlanStartDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("newPlanStartDate", createGeneralTask.newPlanStartDate.getAttribute("value"));
                        break;
                    case "sentNOADate":
                        sendKeyToTextField(createGeneralTask.sentNOADate, getDate(data.get("sentNOADate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("sentNOADate", createGeneralTask.sentNOADate.getAttribute("value"));
                        break;
                    case "medicaidId":
                        sendKeyToTextField(createGeneralTask.externalConsumerID, data.get("medicaidId"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("medicaidId", data.get("medicaidId"));
                        break;
                    case "requestedDate":
                        sendKeyToTextField(createGeneralTask.requestedDate, getDate(data.get("requestedDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("requestedDate", createGeneralTask.requestedDate.getAttribute("value"));
                        break;
                    case "planStartDate":
                        sendKeyToTextField(createGeneralTask.planStartDate, getDate(data.get("planStartDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("planStartDate", createGeneralTask.planStartDate.getAttribute("value"));
                        break;
                    case "usedTobaccoInTheLastSixMonths":
                        selectDropDown(createGeneralTask.usedTobaccoInTheLastSixMonths, data.get("usedTobaccoInTheLastSixMonths"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("usedTobaccoInTheLastSixMonths", data.get("usedTobaccoInTheLastSixMonths"));
                        break;
                    case "dateOfVoicemail":
                        sendKeyToTextField(createGeneralTask.dateOfVoicemail, getDate(data.get("dateOfVoicemail")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateOfVoicemail", createGeneralTask.dateOfVoicemail.getAttribute("value"));
                        break;
                    case "timeOfVoicemail":
                        sendKeyToTextField(createGeneralTask.timeOfVoicemail, data.get("timeOfVoicemail"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("timeOfVoicemail", data.get("timeOfVoicemail"));
                        break;
                    case "addressType":
                        selectDropDown(createGeneralTask.addressType, data.get("addressType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("addressType", data.get("addressType"));
                        break;
                    case "addressSource":
                        selectDropDown(createGeneralTask.addressSource, data.get("addressSource"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("addressSource", data.get("addressSource"));
                        break;
                    case "addressZipCode":
                        clearAndFillText(createGeneralTask.addressZipCode, data.get("addressZipCode"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("addressZipCode", data.get("addressZipCode"));
                        break;
                    case "AddressLine1":
                        clearAndFillText(createGeneralTask.arAddressLine1TxtBox, data.get("AddressLine1"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("AddressLine1", data.get("AddressLine1"));
                        break;
                    case "AddressLine2":
                        clearAndFillText(createGeneralTask.arAddressLine2TxtBox, data.get("AddressLine2"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("AddressLine2", data.get("AddressLine2"));
                        break;
                    case "City":
                        clearAndFillText(createGeneralTask.arCityTxtBox, data.get("City"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("City", data.get("City"));
                        break;
                    case "State":
                        clearAndFillText(createGeneralTask.arStateTxtBox, data.get("State"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("State", data.get("State"));
                        break;
                    case "applicationId":
                        if (data.get("applicationId").equals("random")) {
                            CRM_GeneralTaskStepDef.taskValues.get().put("applicationId", getRandomNumber(9));
                            clearAndFillText(createGeneralTask.applicationId, CRM_GeneralTaskStepDef.taskValues.get().get("applicationId"));
                        } else if (!data.get("applicationId").isEmpty()) {
                            sendKeyToTextField(createGeneralTask.applicationId, data.get("applicationId"));
                            CRM_GeneralTaskStepDef.taskValues.get().put("applicationId", data.get("applicationId"));
                        }
                        break;
                    case "applicationSource":
                        selectDropDown(createGeneralTask.applicationSource, data.get("applicationSource"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("applicationSource", data.get("applicationSource"));
                        break;
                    case "dateOfBirth":
                        sendKeyToTextField(createGeneralTask.dateOfBirth, getDate(data.get("dateOfBirth")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dateOfBirth",createGeneralTask.dateOfBirth.getAttribute("value"));
                        break;
                    case "fromPhone":
                        sendKeyToTextField(createGeneralTask.fromPhone, data.get("fromPhone"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("fromPhone", data.get("fromPhone"));
                        break;
                    case "fromEmail":
                        sendKeyToTextField(createGeneralTask.fromEmail, data.get("fromEmail"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("fromEmail", data.get("fromEmail"));
                        break;
                    case "inboundCorrespondenceId":
                        sendKeyToTextField(createGeneralTask.inboundCorrespondenceId, data.get("inboundCorrespondenceId"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("inboundCorrespondenceId", data.get("inboundCorrespondenceId"));
                        break;
                    case "invalidAddressReason":
                        selectDropDown(createGeneralTask.invalidAddressReason, data.get("invalidAddressReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("invalidAddressReason", data.get("invalidAddressReason"));
                        break;
                    case "newAddressLine2":
                        sendKeyToTextField(createGeneralTask.newAddressLine2TxtBox, data.get("newAddressLine2"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("newAddressLine2", data.get("newAddressLine2"));
                        break;
                    case "newAddressCity":
                        sendKeyToTextField(createGeneralTask.newAddressCity, data.get("newAddressCity"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("newAddressCity", data.get("newAddressCity"));
                        break;
                    case "newAddressState":
                        sendKeyToTextField(createGeneralTask.newAddressState, data.get("newAddressState"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("newAddressState", data.get("newAddressState"));
                        break;
                    case "newAddressZipCode":
                        sendKeyToTextField(createGeneralTask.newAddressZipCode, data.get("newAddressZipCode"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("newAddressZipCode", data.get("newAddressZipCode"));
                        break;
                    case "notificationId":
                        sendKeyToTextField(createGeneralTask.notificationId, data.get("notificationId"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("notificationId", data.get("notificationId"));
                        break;
                    case "oldAddressLine2":
                        sendKeyToTextField(createGeneralTask.oldAddressLine2TxtBox, data.get("oldAddressLine2"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("oldAddressLine2", data.get("oldAddressLine2"));
                        break;
                    case "oldAddressCity":
                        sendKeyToTextField(createGeneralTask.oldAddressCity, data.get("oldAddressCity"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("oldAddressCity", data.get("oldAddressCity"));
                        break;
                    case "oldAddressState":
                        sendKeyToTextField(createGeneralTask.oldAddressState, data.get("oldAddressState"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("oldAddressState", data.get("oldAddressState"));
                        break;
                    case "oldAddressZipCode":
                        sendKeyToTextField(createGeneralTask.oldAddressZipCode, data.get("oldAddressZipCode"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("oldAddressZipCode", data.get("oldAddressZipCode"));
                        break;
                    case "outBoundCorrespondenceId":
                        sendKeyToTextField(createGeneralTask.outboundCorrespondenceId, data.get("outBoundCorrespondenceId"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("outBoundCorrespondenceId", data.get("outBoundCorrespondenceId"));
                        break;
                    case "outreachLocation":
                        synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                        createGeneralTask.outreachLocation.click();
                        CRM_GeneralTaskStepDef.taskValues.get().put("outreachLocation", data.get("outreachLocation"));
                        waitForVisibility(createGeneralTask.outreachLocationDrDw.get(0), 5);
                        String[] expectedVlus = data.get("outreachLocation").split(",");
                        for (int i = 0; i < expectedVlus.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedVlus[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                    CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedVlus[i]);
                                }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-outreachLocation']//ul/li[text()='" + expectedVlus[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        break;
                    case "planId":
                        selectDropDown(createGeneralTask.planId, data.get("planId"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("planId", data.get("planId"));
                        break;
                    case "providerAddressLine2":
                        sendKeyToTextField(createGeneralTask.providerAddressLine2TxtBox, data.get("providerAddressLine2"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerAddressLine2", data.get("providerAddressLine2"));
                        break;
                    case "providerAddressCity":
                        sendKeyToTextField(createGeneralTask.providerAddressCity, data.get("providerAddressCity"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerAddressCity", data.get("providerAddressCity"));
                        break;
                    case "providerAddressState":
                        sendKeyToTextField(createGeneralTask.providerAddressState, data.get("providerAddressState"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerAddressState", data.get("providerAddressState"));
                        break;
                    case "providerAddressZipCode":
                        sendKeyToTextField(createGeneralTask.newAddressZipCode, data.get("providerAddressZipCode"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerAddressZipCode", data.get("providerAddressZipCode"));
                        break;
                    case "providerCounty":
                        selectDropDown(createGeneralTask.providerAddressCounty, data.get("providerCounty"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerCounty", data.get("providerCounty"));
                        break;
                    case "providerStateId":
                        sendKeyToTextField(createGeneralTask.providerStateIdTxtBox, data.get("providerStateId"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerStateId", data.get("providerStateId"));
                        break;
                    case "providerNpi":
                        sendKeyToTextField(createGeneralTask.providerNpiTxtBox, data.get("providerNpi"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("providerNpi", data.get("providerNpi"));
                        break;
                    case "requestedNewProviderChkBx":
                        if (data.get("requestedNewProviderChkBx").equalsIgnoreCase("true")) {
                            createGeneralTask.requestedNewProviderChkBx.click();
                        } else if (data.get("requestedNewProviderChkBx").equalsIgnoreCase("false") && createGeneralTask.requestedNewProviderChkBx.isSelected()) {
                            createGeneralTask.requestedNewProviderChkBx.click();
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("requestedNewProviderChkBx", data.get("requestedNewProviderChkBx"));
                        break;
                    case "urgentAccessToCareChkBx":
                        if (data.get("urgentAccessToCareChkBx").equalsIgnoreCase("true")) {
                            createGeneralTask.urgentAccessToCareChkBx.click();
                        } else if (data.get("urgentAccessToCareChkBx").equalsIgnoreCase("false") && createGeneralTask.urgentAccessToCareChkBx.isSelected()) {
                            createGeneralTask.urgentAccessToCareChkBx.click();
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("urgentAccessToCareChkBx", data.get("urgentAccessToCareChkBx"));
                        break;
                    case "requestedNewPlanChkBx":
                        if (data.get("requestedNewPlanChkBx").equalsIgnoreCase("true")) {
                            createGeneralTask.requestedNewPlanChkBx.click();
                        } else if (data.get("requestedNewPlanChkBx").equalsIgnoreCase("false") && createGeneralTask.requestedNewPlanChkBx.isSelected()) {
                            createGeneralTask.requestedNewPlanChkBx.click();
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("requestedNewPlanChkBx", data.get("requestedNewPlanChkBx"));
                        break;
                    case "inboundCorrespondenceWorkableFlag":
                        if (data.get("inboundCorrespondenceWorkableFlag").equalsIgnoreCase("true")) {
                            createGeneralTask.inboundCorrespondenceWorkableFlag.click();
                        } else if (data.get("inboundCorrespondenceWorkableFlag").equalsIgnoreCase("false") && createGeneralTask.inboundCorrespondenceWorkableFlag.isSelected()) {
                            createGeneralTask.inboundCorrespondenceWorkableFlag.click();
                        }
                        CRM_GeneralTaskStepDef.taskValues.get().put("inboundCorrespondenceWorkableFlag", data.get("inboundCorrespondenceWorkableFlag"));
                        break;
                    case "transferReason":
                        selectDropDown(createGeneralTask.transferReason, data.get("transferReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("transferReason", data.get("transferReason"));
                        break;
                    case "businessUnitAssigneeTo":
                        selectDropDown(createGeneralTask.businessUnitAssignedTo, data.get("businessUnitAssigneeTo"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("businessUnitAssigneeTo", data.get("businessUnitAssigneeTo"));
                        break;
                    case "addressLineOne":
                        sendKeyToTextField(createGeneralTask.addressLineOne, data.get("addressLineOne"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("addressLineOne", data.get("addressLineOne"));
                        break;
                    case "addressLineTwo":
                        sendKeyToTextField(createGeneralTask.businessUnitAssignedTo, data.get("addressLineTwo"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("addressLineTwo", data.get("addressLineTwo"));
                        break;
                    case "addressDetailsCity":
                        sendKeyToTextField(createGeneralTask.addressDetailsCity, data.get("addressDetailsCity"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("addressDetailsCity", data.get("addressDetailsCity"));
                        break;
                    case "addressDetailsState":
                        sendKeyToTextField(createGeneralTask.addressDetailsState, data.get("addressDetailsState"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("addressDetailsState", data.get("addressDetailsState"));
                        break;
                    case "addressDetailsZip":
                        sendKeyToTextField(createGeneralTask.addressDetailsZip, data.get("addressDetailsZip"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("addressDetailsZip", data.get("addressDetailsZip"));
                        break;
                    case "referredTo":
                        selectDropDown(createGeneralTask.complaintAboutDrDn, data.get("referredTo"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("referredTo", data.get("referredTo"));
                        break;
                    case "requestReason":
                        selectDropDown(createGeneralTask.requestReasonDropdown, data.get("requestReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("requestReason", data.get("requestReason"));
                        break;
                    case "memberNameForRequestReason":
                        sendKeyToTextField(createGeneralTask.memberNameForRequestReason, data.get("memberNameForRequestReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("memberNameForRequestReason", data.get("memberNameForRequestReason"));
                        break;
                    case "notesForRequestReason":
                        clearAndFillText(createGeneralTask.notesForRequestReason, data.get("notesForRequestReason"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("notesForRequestReason", data.get("notesForRequestReason"));
                        break;
                    case "requestedPlan":
                        selectDropDown(createGeneralTask.requestedPlan, data.get("requestedPlan"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("requestedPlan", data.get("requestedPlan"));
                        break;
                    case "requestedEffectiveDate":
                        sendKeyToTextField(createGeneralTask.requestedEffectiveDate, getDate(data.get("requestedEffectiveDate")));
                        CRM_GeneralTaskStepDef.taskValues.get().put("requestedEffectiveDate", createGeneralTask.requestedEffectiveDate.getAttribute("value"));
                        break;
                    case "arcIndicator":
                        selectDropDown(createGeneralTask.arcIndicatorDropdown, data.get("arcIndicator"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("arcIndicator", data.get("arcIndicator"));
                        break;
                    case "districtDisposition":
                        waitFor(3);
                        selectDropDown(createGeneralTask.districtDispositionDropdown, data.get("districtDisposition"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("districtDisposition", data.get("districtDisposition"));
                        break;
                    case "notesForRequestReasonSecond":
                        clearAndFillText(createGeneralTask.notesForRequestReasonSecond, data.get("notesForRequestReasonSecond"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("notesForRequestReasonSecond", data.get("notesForRequestReasonSecond"));
                        break;
                    case "memberNameForRequestReasonSecond":
                        sendKeyToTextField(createGeneralTask.memberNameForRequestReasonSecond, data.get("memberNameForRequestReasonSecond"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("memberNameForRequestReasonSecond", data.get("memberNameForRequestReasonSecond"));
                        break;
                    case "outboundCorrespondenceType":
                        synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                        String[] expectedValuesOutbound = data.get("outboundCorrespondenceType").split(",");
                        createGeneralTask.outboundCorrespondenceType.click();
                        for (int i = 0; i < expectedValuesOutbound.length; i++) {
                            if (i != 0) {
                                CRM_GeneralTaskStepDef.selectValue.get().add(expectedValuesOutbound[i]);
                            } else {
                                synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                    CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValuesOutbound[i]);
                                }
                            }
                            click(Driver.getDriver().findElement(By.xpath("//div[@id='menu-outboundCorrespondenceType']//ul/li[text()='" + expectedValuesOutbound[i] + "']")));
                        }
                        actions.moveToElement(createGeneralTask.txtTaskInfo).click().perform();
                        break;
                    case "lockedInPolicyExplained":
                        selectDropDown(createGeneralTask.missingInfoRequired, data.get("lockedInPolicyExplained"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("lockedInPolicyExplained", data.get("lockedInPolicyExplained"));
                        break;
                    case "documentType":
                        selectDropDown(createGeneralTask.inboundCorrespondenceType, data.get("documentType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("documentType", data.get("documentType"));
                        break;
                    case "completedDate":
                        if(data.get("completedDate").equals("delete")){
                          clearTextField(createGeneralTask.completedDate);
                        }
                        else {
                            sendKeyToTextField(createGeneralTask.completedDate, getDate(data.get("completedDate")));
                            CRM_GeneralTaskStepDef.taskValues.get().put("completedDate", createGeneralTask.completedDate.getAttribute("value"));
                        }break;
                    case "callerName":
                        if (data.get("callerName").equals("maxlength")) {
                            CRM_GeneralTaskStepDef.taskValues.get().put("callerName", getRandomString(100));
                            clearAndFillText(createGeneralTask.callerName, CRM_GeneralTaskStepDef.taskValues.get().get("callerName"));
                            sendKeyToTextField(createGeneralTask.callerName, getRandomString(45));
                            assertEquals(createGeneralTask.callerName.getAttribute("value").length(), 100, "Caller name is accepting more then 100 character");
                        } else {
                            sendKeyToTextField(createGeneralTask.callerName, data.get("callerName"));
                            CRM_GeneralTaskStepDef.taskValues.get().put("callerName", data.get("callerName"));
                        }
                        break;
                    case "memberNameFc":
                        sendKeyToTextField(createGeneralTask.memberNameFc, data.get("memberNameFc"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("memberNameFc", data.get("memberNameFc"));
                        break;
                    case "referredBy":
                        selectDropDown(createGeneralTask.referredBy, data.get("referredBy"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("referredBy", data.get("referredBy"));
                        break;
                    case "originatingEscalationType":
                        selectDropDown(createGeneralTask.originatingEscalationType, data.get("originatingEscalationType"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("originatingEscalationType", data.get("originatingEscalationType"));
                        break;

                    default:
                        sa.get().fail("Switch case not match for key " + key);
                        break;
                }
            } else {
                switch (key) {
                    case "taskType":
                        selectDropDownByIndex(createGeneralTask.lstTaskType, myTask.priorities, 0);
                        break;
                    case "priority":
                        selectDropDownByIndex(createGeneralTask.lstTaskPriority, myTask.priorities, 0);
                        break;
                    case "status":
                        selectDropDownByIndex(createGeneralTask.taskStatus, myTask.priorities, 0);
                        break;
                }
                CRM_GeneralTaskStepDef.taskValues.get().put(key, "-- --");
            }

        }
    }

    @When("I will verify the following Facility Name and Facility Type set to {string}")
    public void IwillprovidetheFacilityName(String facilityType, List expectedValues) {
        waitForVisibility(createGeneralTask.facilityName, 10);
        for (int i = 1; i < expectedValues.size(); i++) {
            createGeneralTask.facilityName.click();
            waitFor(2);
            Driver.getDriver().findElement(By.xpath("//ul/li[text()='" + expectedValues.get(i) + "']")).click();
            sa.get().assertThat(createGeneralTask.facilityType.getText()).as("Error message not displayed").isEqualToIgnoringCase(facilityType);
            sa.get().assertThat(createGeneralTask.facilityType.getAttribute("aria-disabled")).as("Facility Type is not disable").isEqualTo("true");
        }
    }

    @When("Verify the Assignee field and the autocomplete dropdown list displays")
    public void veifyTheAssigneeFieldAndTheAutocompleteDropdownListDisplays() {
        assertTrue(createGeneralTask.lstAssignee.isDisplayed());
        createGeneralTask.lstAssignee.click();
        assertEquals(createGeneralTask.lstAssignee.getAttribute("aria-controls"), "auto-staffAssignedTo-popup");
    }


    @Then("Verify Assignee field not display System users")
    public void verifyAssigneeFieldNotDisplaySystemUsers() {
        List userNames = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.staffList.content[*].firstName");

        for (Object name : userNames) {
            clearAndFillText(createGeneralTask.lstAssignee, name.toString().replaceAll("[^a-zA-Z0-9]", ""));
            createGeneralTask.assigNDrop.click();
            waitFor(2);
            assertFalse(isElementDisplayed(createGeneralTask.assigNDrop));
        }

    }

    @Then("I verify when I saving Task with Phone field less than 10 chars getting error {string}")
    public void iVerifyWhenISavingPhoneFieldLessThanCharsGettingError(String error) {
        clearAndFillText(createGeneralTask.contactPhone, "15");
        jsClick(myTask.saveBtn);
        waitFor(4);
        assertEquals(createGeneralTask.phoneErrorMsg.getText(), error, "No Error message for Phone");
    }


    @Then("I verify CSR Name field is an autocomplete dropdown of all Active users")
    public void iVerifyCSRNameFieldIsAnAutocompleteDropdownOfAllActiveUsers() {
        createGeneralTask.csrName.sendKeys("Service");
        waitFor(1);
        assertEquals(createGeneralTask.csrNameAutoComp.getAttribute("aria-controls"), "auto-csrName-popup",
                "CSR Name is not autocomplete");
        createGeneralTask.csrName.clear();
    }

    @When("I click on Add Reason")
    public void clickOnAddReason() {
        scrollToElement(myTask.addReasonButton);
        jsClick(myTask.addReasonButton);
    }

    @When("I remove {int} row of reason and explanation")
    public void clickOnRemoveReasonAndExplanation(int row) {
        scrollUpUsingActions(1);
        jsClick(myTask.listRemoveIcon.get(row - 1));
    }

    @Then("I verify {int} row of reason {string} and explanation {string}")
    public void verifyReasonAndExplanation(int row, String reason, String explanation) {
        sa.get().assertThat(createGeneralTask.reasonExplanation.get(row - 1).getText()).as("Reason is mismatch").isEqualTo(reason);
        sa.get().assertThat(createGeneralTask.explanation.get(row - 1).getAttribute("value")).as("Explanation is mismatch").isEqualTo(explanation);
    }

    @Then("I verify error message for reason and explanation fields {string}")
    public void verifyErrorMessageForJCSR(String fieldName) {
        int index = createGeneralTask.reasonExplanation.size() - 1;
        scrollToElement(createGeneralTask.reasonExplanation.get(index));
        generalTask.get().verifyTaskTypeIsMandatoryField(fieldName);
    }
    @And("Verify {string} auto suggestion value is displaying for csr name")
    public void verifyAutopopulatedValueIsDisplaying(String name, List<String> expectedValues) {
        createGeneralTask.csrName.click();
        clearAndFillText(createGeneralTask.csrName,name);
        sa.get().assertThat(createGeneralTask.csrNameAutopopulateVlus)
                .as( "CSR Auto-complete DD Size does not match").hasSize(expectedValues.size())
                .as("CSR Auto-complete DD has duplicate values").doesNotHaveDuplicates()
                .as("CSR Auto-complete DD drop down values not match ").extracting(WebElement::getText).containsExactlyInAnyOrderElementsOf(expectedValues);
        /*for(int i=0; i < createGeneralTask.csrNameAutopopulateVlus.size(); i++){
            sa.get().assertThat(createGeneralTask.csrNameAutopopulateVlus.get(i).getText()).as("CSR Name value is mismatch").isEqualTo(expectedValues.get(i));
        }*/
    }

    @And("I verify that update all and cancel sr checkboxes are displayed")
    public void I_verify_that_update_all_and_cancel_sr_checkboxes_are_displayed(){
        assertTrue(createGeneralTask.cancelSRcheckbox.isDisplayed(),"Cancel SR checkbox is not getting displayed");
        assertTrue(createGeneralTask.updateALLcheckbox.isDisplayed(),"Update all checkbox is not getting displayed");
    }

    @Then("I click on {string} button on Create Task")
    public void i_click_on_button_on_Create_Task(String buttonType) {
       switch (buttonType) {
           case "Add Request Reason":
               jsClick(createGeneralTask.addButtonForMemberRequest);
               waitFor(2);
               break;
           case "Update All":
               jsClick(createGeneralTask.updateALLcheckbox);
               waitFor(1);
               scrollDownUsingActions(4);
               break;
       }
    }

    @Then("I verify both request reason are getting populated")
    public void i_verify_both_request_reason_are_getting_populated() {
        waitFor(3);
       assertEquals(createGeneralTask.requestReasonDropdown.getText(),createGeneralTask.requestReasonDropdownSecond.getText(),"Request reason values are not same");
       assertNotEquals(createGeneralTask.memberNameForRequestReason.getAttribute("value"),createGeneralTask.memberNameForRequestReasonSecond.getAttribute("value"),"Member names are same");
       assertEquals(createGeneralTask.notesForRequestReason.getAttribute("value"),createGeneralTask.notesForRequestReasonSecond.getAttribute("value"),"Notes are not same");
       assertEquals(createGeneralTask.requestedPlan.getText(),createGeneralTask.requestedPlanSecond.getText(),"Requested plans are not same");
       assertEquals(createGeneralTask.requestedEffectiveDate.getAttribute("value"),createGeneralTask.requestedEffectiveDateSecond.getAttribute("value"),"Requested effective date are not same");
       assertEquals(createGeneralTask.arcIndicatorDropdown.getText(),createGeneralTask.arcIndicatorDropdownSecond.getText(),"ARC indicator values are not same");
       assertEquals(createGeneralTask.districtDispositionDropdown.getText(),createGeneralTask.districtDispositionDropdownSecond.getText(),"District disposition values are not same");
    }

    @Then("I verify that second member request reasons are not getting changed")
    public void i_verify_that_second_member_request_reasons_are_not_getting_changed(){
        waitFor(2);
        assertFalse(createGeneralTask.requestReasonDropdown.getText().equals(createGeneralTask.requestReasonDropdownSecond.getText()),"Request reason values are same");
        assertFalse(createGeneralTask.memberNameForRequestReason.getAttribute("value").equals(createGeneralTask.memberNameForRequestReasonSecond.getAttribute("value")),"Member names are not same");
        assertFalse(createGeneralTask.notesForRequestReason.getAttribute("value").equals(createGeneralTask.notesForRequestReasonSecond.getAttribute("value")),"Notes are same");
        assertFalse(createGeneralTask.requestedPlan.getText().equals(createGeneralTask.requestedPlanSecond.getText()),"Requested plans are same");
        assertTrue(createGeneralTask.requestedEffectiveDate.getAttribute("value").equals(createGeneralTask.requestedEffectiveDateSecond.getAttribute("value")),"Requested effective date are same");
        assertFalse(createGeneralTask.arcIndicatorDropdown.getText().equals(createGeneralTask.arcIndicatorDropdownSecond.getText()),"ARC indicator values are same");
        assertFalse(createGeneralTask.districtDispositionDropdown.getText().equals(createGeneralTask.districtDispositionDropdownSecond.getText()),"District disposition values are same");
    }

    @When("Validate error message should be displayed save without the case linked")
    public void validateErrorMessageShouldBeDisplayedSaveWithoutTheCaseLinked() {
        sa.get().assertThat(createGeneralTask.errorFCNoCaseLink.isDisplayed()).as("Error message not displayed").isTrue();
    }
}