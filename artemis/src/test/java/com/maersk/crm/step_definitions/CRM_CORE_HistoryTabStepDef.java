package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.*;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.*;

public class CRM_CORE_HistoryTabStepDef extends CRMUtilities implements ApiBase {

    APIConsumerPopulationDmnController eAndEapiController = new APIConsumerPopulationDmnController();
    APIEnrollmentEventsCheckController enrollEventController = new APIEnrollmentEventsCheckController();
    CRMContactRecordUIPage contactRecordUIPage = new CRMContactRecordUIPage();
    CRMProgramAndBenefitInfoPage programAndBenefitInfoPage = new CRMProgramAndBenefitInfoPage();
    CRMEnrollmentUpdatePage enrollmentUpdatePage = new CRMEnrollmentUpdatePage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRM_ViewContactRecordHistoryStepDef viewContRecHistory = new CRM_ViewContactRecordHistoryStepDef();
    CRMEnrollmentUpdateStepDef enrollStepDef = new CRMEnrollmentUpdateStepDef();
    CRM_ContactRecordUIStepDef contactRecordUIStepDef = new CRM_ContactRecordUIStepDef();
    APIEnrollmentController apiEnrollmentController = new APIEnrollmentController();
    CRMContactHistoryPage contactHistoryPage = new CRMContactHistoryPage();
    CRMTaskManageMyTasksPage taskManageMyTasksPage = new CRMTaskManageMyTasksPage();
    CRMHistoryTabPage historyTabPage = new CRMHistoryTabPage();
    CRM_Edit_ContactRecord_Page edit_cont_Rec = new CRM_Edit_ContactRecord_Page();

    private Actions action = new Actions(Driver.getDriver());

    final ThreadLocal<Map<String, String>> eAndEData = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<Map<String, String>> updateEnrollData = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<Map<String, String>> processOutboundData = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<Map<String, String>> triggerEnrollData = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<Map<String, String>> eventMapDataTracker = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<Map<String, String>> planTransfer = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<List<String>> primaryIndividualList = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> membersList = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<String> contactId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> classContactId = new ThreadLocal<>();
    final ThreadLocal<String> nameOfContact = new ThreadLocal<>();
    final ThreadLocal<String> taskId = new ThreadLocal<>();
    final ThreadLocal<String> wrapUpTime = new ThreadLocal<>();


    @And("I create {string} enrollment event for use in history tab")
    public void iCreateEnrollmentEventForUseInHistoryTab(String eventType) {
        switch (eventType) {
            case "NEW_ENROLLMENT_REJECT_RESPONSE":
                enrollmentRejectEvent();
                break;
            case "PLAN_CHANGE_REJECT_RESPONSE":
                planChangeRejectEvent();
                break;
            case "NEW_RETROACTIVE_ENROLLMENT":
                newRetroEnrollmentEvent();
                break;
            default:
                fail("Mismatch in case values");
        }
    }

    public void eAndEMapSet() {
        eAndEData.get().put("[0].consumerId", "15063-01.consumerId");
        eAndEData.get().put("isEligibilityRequired", "yes");
        eAndEData.get().put("otherSegments", "facilityInfo");
        eAndEData.get().put("isEnrollemntRequired", "no");
        eAndEData.get().put("recordId", "3");
        eAndEData.get().put("isEnrollmentProviderRequired", "no");
        eAndEData.get().put("enrollmentStartDate", "1stDayofNextMonth");
        eAndEData.get().put("eligibilityStartDate", "1stDayofPresentMonth");
        eAndEData.get().put("txnStatus", "Accepted");
        eAndEData.get().put("programCode", "M");
    }

    public void updateEnrollMapSet() {
        updateEnrollData.get().put("[0].consumerId", "15063-01.consumerId");
        updateEnrollData.get().put("[0].planId", "int::145");
        updateEnrollData.get().put("[0].planCode", "84");
        updateEnrollData.get().put("[0].startDate", "fdofmnth::");
    }

    public void processOutMapSet() {
        processOutboundData.get().put("[0].planId", "145");
        processOutboundData.get().put("[0].planCode", "84");
        processOutboundData.get().put("[0].consumerId", "15063-01.consumerId");
        processOutboundData.get().put("[0].startDate", "fdofmnth::");
        processOutboundData.get().put("[0].enrollmentId", "c.data[0].enrollmentId");
    }

    public void triggerEnrollMapSet() {
        triggerEnrollData.get().put("consumerId", "15063-01.consumerId");
        triggerEnrollData.get().put("enrollments.[0].consumerId", "15063-01.consumerId");
        triggerEnrollData.get().put("enrollments.[0].subProgramTypeCode", "C.data[0].subProgramTypeCode");
        triggerEnrollData.get().put("enrollments.[0].planId", "145");
        triggerEnrollData.get().put("enrollments.[0].planCode", "84");
        triggerEnrollData.get().put("enrollments.[0].rejectionReason", "Consumer is not eligible");
        triggerEnrollData.get().put("recordId", "int::16");
        triggerEnrollData.get().put("enrollments.[0].startDate", "fdofmnth::");
        triggerEnrollData.get().put("enrollments.[0].endDate", "null::");
        triggerEnrollData.get().put("enrollments.[0].createdOn", "delete::");
    }

    public Map<String, String> planEligibilityCreateMapSet(String type) {
        Map<String, String> planEligibilityCreate = new HashMap<>();
        planEligibilityCreate.put("[0].consumerId", "QW1.consumerId");
        planEligibilityCreate.put("otherSegments", "");
        planEligibilityCreate.put("isEnrollmentProviderRequired", "no");
        planEligibilityCreate.put("eligibilityStartDate", "1stDayofPresentMonth");
        planEligibilityCreate.put("txnStatus", "Accepted");
        planEligibilityCreate.put("programCode", "M");
        planEligibilityCreate.put("anniversaryDate", "anniversaryDate");
        planEligibilityCreate.put("requestedBy", "8369");
        switch (type) {
            case "ONE":
                planEligibilityCreate.put("isEligibilityRequired", "yes");
                planEligibilityCreate.put("isEnrollemntRequired", "no");
                planEligibilityCreate.put("recordId", "3");
                planEligibilityCreate.put("enrollmentStartDate", "");
                break;
            case "TWO":
                planEligibilityCreate.put("isEligibilityRequired", "no");
                planEligibilityCreate.put("isEnrollemntRequired", "yes");
                planEligibilityCreate.put("recordId", "4");
                planEligibilityCreate.put("enrollmentStartDate", "1stDayofPresentMonth");
                planEligibilityCreate.put("planCode", "84");
                planEligibilityCreate.put("planId", "145");
                planEligibilityCreate.put("serviceRegionCode", "East");
                break;
            case "THREE":
                planEligibilityCreate.put("isEligibilityRequired", "no");
                planEligibilityCreate.put("isEnrollemntRequired", "yes");
                planEligibilityCreate.put("recordId", "18");
                planEligibilityCreate.put("enrollmentStartDate", "1stDayofNextMonth");
                planEligibilityCreate.put("eligibilityStartDate", "");
                planEligibilityCreate.put("planCode", "85");
                planEligibilityCreate.put("planId", "145");
                planEligibilityCreate.put("serviceRegionCode", "East");
                planEligibilityCreate.put("rejectionReason", "Consumer is not eligible");
                break;
            default:
                fail("Mismatch in provided type to case");
        }
        return planEligibilityCreate;
    }

    public void planTransferMapSet() {
        planTransfer.get().put("[0].consumerId", "QW1.consumerId");
        planTransfer.get().put("[0].planId", "145");
        planTransfer.get().put("[0].planCode", "85");
        planTransfer.get().put("[0].startDate", "fdnxtmth::");
        planTransfer.get().put("[0].subProgramTypeCode", "MEDICAIDGF");
        planTransfer.get().put("[0].serviceRegionCode", "East");
        planTransfer.get().put("[0].selectionReason", "delete::");
        planTransfer.get().put("[0].anniversaryDate", "anniversaryDate::");
        planTransfer.get().put("[0].channel", "SYSTEM_INTEGRATION");
        planTransfer.get().put("[0].planEndDateReason", "PLAN_TERMINATED");
    }

    public Map<String, String> rejectSelectionMapSet() {
        Map<String, String> rejectSelection = new HashMap<>();
        rejectSelection.put("[0].consumerId", "QW1.consumerId");
        rejectSelection.put("[0].planId", "delete::");
        rejectSelection.put("[0].planCode", "84");
        rejectSelection.put("[0].enrollmentId", "QW1a.discontinuedEnrollmentId");
        rejectSelection.put("[0].status", "DISENROLL_SUBMITTED");
        rejectSelection.put("[0].txnStatus", "DISENROLL_SUBMITTED");
        rejectSelection.put("[0].startDate", "fdofmnth::");
        rejectSelection.put("[0].endDate", "lstdaymnth::");
        rejectSelection.put("[0].enrollmentType", "delete::");
        rejectSelection.put("[0].subProgramTypeCode", "MEDICAIDGF");
        rejectSelection.put("[0].serviceRegionCode", "East");
        rejectSelection.put("[1].planId", "delete::");
        rejectSelection.put("[1].planCode", "85");
        rejectSelection.put("[1].consumerId", "QW1.consumerId");
        rejectSelection.put("[1].enrollmentId", "QW1a.selectedEnrollmentId");
        rejectSelection.put("[1].status", "SUBMITTED_TO_STATE");
        rejectSelection.put("[1].txnStatus", "SUBMITTED_TO_STATE");
        rejectSelection.put("[1].startDate", "fdnxtmth::");
        rejectSelection.put("[1].subProgramTypeCode", "MEDICAIDGF");
        rejectSelection.put("[1].serviceRegionCode", "East");
        return rejectSelection;
    }

    public void newRetroEnrollmentEvent() {
        eAndEapiController.initiateEnrollmentEligibilityCreateApi();
        eAndEapiController.createEligibilityAndEnrollemntRecord(planEligibilityCreateMapSet("ONE"));
        eAndEapiController.runEnrollmentEligibilityCreateApi();
        waitFor(5);
        eAndEapiController.createEligibilityAndEnrollemntRecord(planEligibilityCreateMapSet("TWO"));
        eAndEapiController.runEnrollmentEligibilityCreateApi();
    }

    public void enrollmentRejectEvent() {
        eAndEapiController.initiateEnrollmentEligibilityCreateApi();
        eAndEMapSet();
        eAndEapiController.createEligibilityAndEnrollemntRecord(eAndEData.get());
        eAndEapiController.runEnrollmentEligibilityCreateApi();
        waitFor(5);
        updateEnrollMapSet();
        eAndEapiController.iSendAPICallWithNameStringForUpdateEnrollmentInformation("C", updateEnrollData.get());
        processOutMapSet();
        eAndEapiController.iSendAPICallWithNameForProcessOutboundUpdate("PO5", processOutboundData.get());
        triggerEnrollMapSet();
        eAndEapiController.iSendAPICallWithNameForTriggerEnrollmentStart("ST5", triggerEnrollData.get());
        waitFor(15);
        verifyBusinessEvent("NEW_ENROLLMENT_REJECT_RESPONSE");
    }

    public void planChangeRejectEvent() {
        eAndEapiController.initiateEnrollmentEligibilityCreateApi();
        eAndEapiController.createEligibilityAndEnrollemntRecord(planEligibilityCreateMapSet("ONE"));
        eAndEapiController.runEnrollmentEligibilityCreateApi();
        waitFor(5);
        eAndEapiController.createEligibilityAndEnrollemntRecord(planEligibilityCreateMapSet("TWO"));
        eAndEapiController.runEnrollmentEligibilityCreateApi();
        waitFor(5);
        planTransferMapSet();
       eAndEapiController.i_perform_plan_transfer_via_api_to_new_plan_with_data(planTransfer.get());
        waitFor(5);
        apiEnrollmentController.initiatedGetEnrolmentByConsumerID("QW1.consumerId");
        apiEnrollmentController.runGetEnrollmentByConsumerId();
        apiEnrollmentController.I_save_enrollment_ids_by_discontinued_and_selected_with_name("QW1a");
        eAndEapiController.iSendAPICallWithNameForRejectedSelectionTaskProcessOutboundUpdate("OU", rejectSelectionMapSet());
        waitFor(5);
        eAndEapiController.initiateEnrollmentEligibilityCreateApi();
        eAndEapiController.createEligibilityAndEnrollemntRecord(planEligibilityCreateMapSet("THREE"));
        eAndEapiController.runEnrollmentEligibilityCreateApi();
        waitFor(5);
        apiEnrollmentController.initiatedGetBenefitStatusByConsumerID("QW1.consumerId");
        apiEnrollmentController.runGetEnrollmentByConsumerId();
        apiEnrollmentController.verifyBenefitStatusRecordsDisplayedWithExpectedScenarioOutput("New Retroactive Eligibility", "Plan Change Reject");
        waitFor(5);
        verifyBusinessEvent("PLAN_CHANGE_REJECT_RESPONSE");
    }

    public void verifyBusinessEvent(String value) {
        switch (value) {
            case "NEW_ENROLLMENT_REJECT_RESPONSE":
                eventMapDataTracker.get().put("externalCaseId", "15063-01.caseIdentificationNumber");
                eventMapDataTracker.get().put("externalConsumerId", "15063-01.externalConsumerId");
                eventMapDataTracker.get().put("consumerId", "15063-01.consumerId");
                eventMapDataTracker.get().put("consumerName", "15063-01");
                break;
            case "PLAN_CHANGE_REJECT_RESPONSE":
                eventMapDataTracker.get().put("externalCaseId", "QW1.caseIdentificationNumber");
                eventMapDataTracker.get().put("externalConsumerId", "QW1.externalConsumerId");
                eventMapDataTracker.get().put("consumerId", "QW1.consumerId");
                eventMapDataTracker.get().put("consumerName", "QW1");
                break;
            default:
                fail("Mismatch in provided case value");
        }
        eventMapDataTracker.get().put("eventName", value);
        enrollEventController.verifyBusinessEvents(eventMapDataTracker.get());
    }

    @And("I store {string} for use in verification")
    public void iStoreForUseInVerification(String data) {
        switch (data) {
            case "CONTACT ID AND NAME OF CONTACT":
                waitFor(3);
                contactId.set(contactRecordUIPage.contactRecordIdList.get(0).getText());
                nameOfContact.set(contactRecordUIPage.contactRecordConsumerList.get(0).getText());
                synchronized (classContactId){
                    classContactId.set(contactId.get());
                }
                break;
            case "TASK ID":
                waitFor(2);
                synchronized (taskId){
                    taskId.set(contactRecordUIPage.taskIdList.get(0).getText());
                }
                break;
            case "PRIMARY AND MEMBERS NAMES":
                waitFor(3);
                contactRecordUIPage.demoPagePIList.forEach(each -> primaryIndividualList.get().add(each.getText()));
                contactRecordUIPage.demoPageMembersList.forEach(each -> membersList.get().add(each.getText()));
                System.out.println("test");
                break;
            case "PRIMARY INDIVIDUAL":
                waitFor(3);
                contactRecordUIPage.demoPagePIList.forEach(each -> primaryIndividualList.get().add(each.getText()));
                break;
            default:
                fail("Provided key did not match");
        }
    }

    @And("I select {string} for {string} field for enrollment eligibility process")
    public void iSelectForFieldForEnrollmentEligibilityProcess(String value, String field) {
        switch (field) {
            case "START DATE":
                switch (value) {
                    case "FUTURE DATE":
                        sendKeyToTextField(enrollmentUpdatePage.startDateInputValue, getFutureDate(30));
                        break;
                    case "FUTURE EDIT":
                        sendKeyToTextField(enrollmentUpdatePage.startDateInputValue, getFutureDate(31));
                        break;
                }
                break;
            case "OVER RIDE REASON":
                selectDropDown(enrollmentUpdatePage.enrolmentOverrideReasonbttn, value);
                break;
            case "REASON":
                selectDropDown(enrollmentUpdatePage.enrolmentUpdateReasonbttn, value);
                break;
        }
    }

    @And("I enter First Name as {string} and Last Name as {string} in case consumer search page")
    public void iEnterFirstNameAsAndLastNameAsInCaseConsumerSearchPage(String firstName, String lastName) {
        String firstNameRes = firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString();
        String lastNameRes = lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString();
        clearAndFillText(createGeneralTask.consumerFirstName, firstNameRes);
        clearAndFillText(createGeneralTask.consumerLastName, lastNameRes);
        contactRecordUIPage.searchButton.click();
    }

    @And("I enter First Name as {string} and Last Name as {string} in manual contact record search page")
    public void iEnterFirstNameAsAndLastNameAsInManualContactRecordSearchPage(String firstName, String lastName) {
        String firstNameRes = firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString();
        String lastNameRes = lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString();
        clearAndFillText(createGeneralTask.consumerFirstName, firstNameRes);
        clearAndFillText(createGeneralTask.consumerLastName, lastNameRes);
        contactRecordUIPage.searchButton.click();
        waitForVisibility(edit_cont_Rec.frstRecord, 10);
        Assert.assertTrue(edit_cont_Rec.frstRecord.isDisplayed(),"Contact Id Not Displayed");
        Assert.assertTrue(edit_cont_Rec.consumerName.getText().contains(firstNameRes), "Contact name Created not matching with search result"+"Contact namecreated: "+firstNameRes+" search result: "+edit_cont_Rec.consumerName.getText());

    }

    @And("I complete active contact by filling out all minimal required fields")
    public void iCompleteActiveContactByFillingOutAllMinimalRequiredFields() {
        viewContRecHistory.i_choose_a_contact_reason_from_reason_dropdown_list();
        viewContRecHistory.i_choose_a_contact_action_from_action_dropdown_list();
        jsClick(contactRecordUIPage.reasonSaveButton);
        contactRecordUIStepDef.i_close_active_contact_after_selecting_all_mandatory_fields();
    }

    public WebElement getEventLinkObjectWebElement(String eventType, String idType) {
        waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//h5[.='" + eventType + "']/..//button[@id='" + idType + "']"));
    }

    @And("I verify {string} Event {string} object displays the following list of labels and values")
    public void iVerifyEventObjectDisplaysTheFollowingListOfLabelsAndValues(String eventPanelType, String idType, List<String> datatable) {
        waitFor(2);
        switch (idType) {
            case "CONTACT RECORD":
                switch (eventPanelType) {
                    case "New Enrollment":
                        scrollDownWithArrow(10);
                        WebElement newEnroll = getEventLinkObjectWebElement(eventPanelType, idType);
                        assertTrue(newEnroll.getText().contains(contactId.get()));
                        newEnroll.click();
                        waitFor(2);
                        linkObjectLabelValueVerify(datatable);
                        break;
                    case "Enrollment Edit":
                        WebElement enrollEdit = getEventLinkObjectWebElement(eventPanelType, idType);
                        assertTrue(enrollEdit.getText().contains(contactId.get()));
                        enrollEdit.click();
                        waitFor(2);
                        linkObjectLabelValueVerify(datatable);
                        break;
                    case "Disregarded New Enrollment":
                        WebElement rejectResponse = getEventLinkObjectWebElement(eventPanelType, idType);
                        assertTrue(rejectResponse.getText().contains(contactId.get()));
                        rejectResponse.click();
                        waitFor(2);
                        linkObjectLabelValueVerify(datatable);
                        break;
                    case "Plan Change":
                        scrollDownWithArrow(10);
                        WebElement planChange = getEventLinkObjectWebElement(eventPanelType, idType);
                        assertTrue(planChange.getText().contains(contactId.get()), "plan change: " + planChange.getText() + " \ncontactId: " + contactId.get());
                        planChange.click();
                        waitFor(2);
                        linkObjectLabelValueVerify(datatable);
                        break;
                    case "Plan Change Edit":
                        WebElement planEdit = getEventLinkObjectWebElement(eventPanelType, idType);
                        assertTrue(planEdit.getText().contains(contactId.get()));
                        planEdit.click();
                        waitFor(2);
                        linkObjectLabelValueVerify(datatable);
                        break;
                    case "Disregarded Plan Change":
                        WebElement disregardedPlan = getEventLinkObjectWebElement(eventPanelType, idType);
                        assertTrue(disregardedPlan.getText().contains(contactId.get()));
                        disregardedPlan.click();
                        waitFor(2);
                        linkObjectLabelValueVerify(datatable);
                        break;
                }
                break;
            case "TASK":
                switch (eventPanelType) {
                    case "New Enrollment Reject Response":
                        scrollDownUsingActions(1);
                        waitFor(1);
                        WebElement enrollReject = getEventLinkObjectWebElement(eventPanelType, idType);
                        assertTrue(enrollReject.getText().contains(taskId.get()));
                        enrollReject.click();
                        waitFor(2);
                        linkObjectLabelValueVerify(datatable);
                        break;
                    case "Plan Change Reject Response":
                        scrollDownUsingActions(1);
                        waitFor(1);
                        WebElement planReject = getEventLinkObjectWebElement(eventPanelType, idType);
                        assertTrue(planReject.getText().contains(taskId.get()));
                        planReject.click();
                        waitFor(3);
                        linkObjectLabelValueVerify(datatable);
                        break;
                }
                break;
        }
    }

    public void linkObjectLabelValueVerify(List<String> dataTable) {
        for (String each : dataTable) {
            switch (each) {
                case "NAME OF CONTACT":
                    assertEquals(each, contactRecordUIPage.conRecNameLabel.getText());
                    assertEquals(nameOfContact.get(), contactRecordUIPage.conRecNameValue.getText());
                    break;
                case "CONTACT START":
                    assertEquals(each, contactRecordUIPage.conRecConStartLabel.getText());
                    assertEquals(getCurrentDateInYearFormat(), contactRecordUIPage.conRecConStartValue.getText());
                    break;
                case "WRAP UP TIME WITH VIEW BUTTON":
                    assertEquals("WRAP UP TIME", contactRecordUIPage.conRecWrapUpLabel.getText());
                    wrapUpTime.set(contactRecordUIPage.conRecWrapUpValue.getText());
                    contactRecordUIPage.viewLinkButton.click();
                    waitFor(4);
                    assertTrue(contactHistoryPage.contactRecordInfoHeader.isDisplayed());
                    assertEquals(wrapUpTime.get(), contactHistoryPage.WrapUpTime_Time.getText());
                    contactHistoryPage.backButton.click();
                    waitFor(3);
                    break;
                case "TASK":
                    assertEquals("TYPE", contactRecordUIPage.taskTypelabel.getText());
                    assertEquals(contactRecordUIPage.taskTypeValue.getText(), "Rejected Selection");
                    break;
                case "STATUS":
                    assertEquals(each, contactRecordUIPage.taskStatusLabel.getText());
                    assertEquals(contactRecordUIPage.taskStatusValue.getText(), "Created");
                    break;
                case "STATUS DATE":
                    assertEquals(each, contactRecordUIPage.taskStatusDateLabel.getText());
                    assertEquals(getCurrentDateInYearFormat(), contactRecordUIPage.taskStatusDateValue.getText());
                    break;
                case "VIEW TASK BUTTON":
                    contactRecordUIPage.viewLinkButton.click();
                    waitForVisibility(taskManageMyTasksPage.taskDetailsHeader, 5);
                    Assert.assertTrue(taskManageMyTasksPage.taskDetailsHeader.isDisplayed(), "Task Details tab verification failed");
                    taskManageMyTasksPage.backArrowInViewPage.click();
                    waitFor(3);
                    break;

            }
        }
    }

    @And("I perform {string} event save on Program & Benefit Info Tab")
    public void iPerformEventSaveOnProgramBenefitInfoTab(String eventType) {
        switch (eventType) {
            case "ENROLL":
                enrollStepDef.iClickButtonForAConsumer("enroll");
                enrollStepDef.i_select_A_plan_from_Available_Plans();
                iSelectForFieldForEnrollmentEligibilityProcess("FUTURE DATE", "START DATE");
                // below step in qa-dev, not in qa-rls
                //     iSelectForFieldForEnrollmentEligibilityProcess("Good Cause Exception", "OVER RIDE REASON");
                enrollStepDef.clickSubmit();
                break;
            case "ENROLL EDIT":
                programAndBenefitInfoPage.futureEnrollmentEditBttn.click();
                iSelectForFieldForEnrollmentEligibilityProcess("FUTURE EDIT", "START DATE");
                iSelectForFieldForEnrollmentEligibilityProcess("Consumer changed mind", "REASON");
                enrollStepDef.clickSubmit();
                break;
            case "ENROLL DISREGARD":
                enrollStepDef.iClickOnDisregardButtonOnProgramBenefitsPage();
                enrollStepDef.iClickOnContinueForDisregardEnrollment();
                break;
            case "PLAN CHANGE":
                enrollStepDef.iClickButtonForAConsumer("plan change");
                enrollStepDef.i_select_A_plan_from_Available_Plans();
                iSelectForFieldForEnrollmentEligibilityProcess("FUTURE DATE", "START DATE");
                enrollStepDef.i_select_a_reason_from_drop_down_on_Enrollment_Update_page();
                enrollStepDef.clickSubmit();
                break;
            case "PLAN EDIT":
                programAndBenefitInfoPage.currentEnrollmentPlanChangeBttn.click();
                iSelectForFieldForEnrollmentEligibilityProcess("FUTURE EDIT", "START DATE");
                enrollStepDef.i_select_a_reason_from_drop_down_on_Enrollment_Update_page();
                enrollStepDef.clickSubmit();
                break;
            case "PLAN DISREGARD":
                enrollStepDef.iClickOnDisregardButtonOnProgramBenefitsPage();
                enrollStepDef.iClickOnContinueForDisregardEnrollment();
                break;
        }
    }

    @And("I click on the contact Id from the search result")
    public void iClickOnTheContactIdFromTheSearchResult() {
        waitFor(3);
        contactRecordUIPage.caseIdFirstRecord.click();
    }

    @And("I verify Primary Individual consumer is first then all case members listed alphabetically by First Name in the History Tab consumer dropdown")
    public void iVerifyPrimaryIndividualConsumerIsFirstThenAllCaseMembersListedAlphabeticallyByFirstNameInTheHistoryTabConsumerDropdown() {
        action.moveToElement(contactRecordUIPage.conNameDropdown).click().build().perform();
        waitFor(2);
        List actualNamesInDropdown = new ArrayList<>();
        contactRecordUIPage.namesInHistoryTabConsumerDropList.forEach(each -> actualNamesInDropdown.add(each.getText().replace(" R ", " ")));
        System.out.println("Actual Primary individual: " + actualNamesInDropdown.get(0));
        System.out.println("Expected Primary individual: " + primaryIndividualList.get().get(0));
        assertEquals(actualNamesInDropdown.get(0), primaryIndividualList.get().get(0), "First name shown in consumer dropdown: " + actualNamesInDropdown.get(0) + " Primary Individual Name: " + primaryIndividualList.get().get(0));
        Collections.sort(membersList.get());
        System.out.println("Expected member List = " + membersList.get());
        System.out.println("Actual member List: ");
        for (int i = 1; i < actualNamesInDropdown.size(); i++) {
            System.out.print(actualNamesInDropdown.get(i) + " ");
        }
        for (int i = 0; i < membersList.get().size(); i++) {
            assertEquals(actualNamesInDropdown.get(i + 1), membersList.get().get(i));
        }
        action.moveToElement(contactRecordUIPage.namesInHistoryTabConsumerDropList.get(0)).click().build().perform();
    }

    @Then("Verify I will see the default values for History screen")
    public void verifyIWillSeeTheDefaultValuesForHistoryScreen() {
        assertEquals(contactRecordUIPage.conNameDropLabel.getText(), "CONSUMER NAME");
        assertEquals(contactRecordUIPage.businessEventDropLabel.getText(), "BUSINESS EVENTS");
        assertEquals(contactRecordUIPage.processDateRangeDropLabel.getText(), "PROCESS DATE RANGE");
        assertEquals(contactRecordUIPage.channelDropLabel.getText(), "CHANNEL");
        assertTrue(contactRecordUIPage.noRecordsAvailable.getText().contains("No Records Available, Please Select Consumer"));
    }

    @Then("I verify the values for Channel dropdown on History screen")
    public void iVerifyTheValuesForChannelDropdownOnHistoryScreen(List<String> data) {
        waitFor(1);
        action.moveToElement(contactRecordUIPage.channelDropdown).click().build().perform();
        for (int i = 0; i < contactRecordUIPage.channelValueDropList.size(); i++) {
            assertEquals(contactRecordUIPage.channelValueDropList.get(i).getText(), data.get(i));
        }
    }

    @And("I select Consumer Name dropdown value at {string} position in the History Tab")
    public void iSelectConsumerNameDropdownValueAtPositionInTheHistoryTab(String dropDownPosition) {
        action.moveToElement(contactRecordUIPage.conNameDropdown).click().build().perform();
        switch (dropDownPosition) {
            case "FIRST":
                System.out.println("First Consumer Name chosen");
                action.moveToElement(Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li")).get(0)).click().build().perform();
                break;
            case "SECOND":
                System.out.println("Second Consumer Name chosen");
                action.moveToElement(Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li")).get(1)).click().build().perform();
                break;

        }
    }

    @Then("I verify Primary Individual name is selected in the Consumer Name dropdown for a case with only one consumer")
    public void iVerifyPrimaryIndividualNameIsSelectedInTheConsumerNameDropdownForACaseWithOnlyOneConsumer() {
        waitFor(2);
        assertEquals(contactRecordUIPage.conNameDropdown.getText().replace(" R ", " "), primaryIndividualList.get().get(0));
    }

    @Then("Verify I will see the default values for History screen for {string} consumer in a case")
    public void verifyIWillSeeTheDefaultValuesForHistoryScreenForConsumerInACase(String type) {
        switch (type) {
            case "SINGLE":
                assertEquals(contactRecordUIPage.conNameDropLabel.getText(), "CONSUMER NAME");
                assertEquals(contactRecordUIPage.businessEventDropLabel.getText(), "BUSINESS EVENTS");
                assertEquals(contactRecordUIPage.processDateRangeDropLabel.getText(), "PROCESS DATE RANGE");
                assertEquals(contactRecordUIPage.channelDropLabel.getText(), "CHANNEL");
                assertTrue(contactRecordUIPage.listOfBusinessEvents.size() > 2);
                break;
            case "MULTIPLE":
                assertEquals(contactRecordUIPage.conNameDropLabel.getText(), "CONSUMER NAME");
                assertEquals(contactRecordUIPage.businessEventDropLabel.getText(), "BUSINESS EVENTS");
                assertEquals(contactRecordUIPage.processDateRangeDropLabel.getText(), "PROCESS DATE RANGE");
                assertEquals(contactRecordUIPage.channelDropLabel.getText(), "CHANNEL");
                assertTrue(contactRecordUIPage.noRecordsAvailable.getText().contains("No Records Available, Please Select Consumer"));
                break;
        }
    }

    @And("I navigate to the bottom of the History tab to load more existing Events")
    public void iNavigateToTheBottomOfTheHistoryTabToLoadMoreExistingEvents() {
        waitFor(2);
        scrollDownUsingActions(10);
        scrollUpUsingActions(1);
        scrollDownWithArrow(40);
        waitFor(5);
    }


    @And("I select {string} dropdown and select the following options in the History Tab")
    public void iSelectDropdownAndSelectTheFollowingOptionsInTheHistoryTab(String type, List<String> data) {
        switch (type) {
            case "BUSINESS UNIT":
                historyTabPage.businessEventsDropdown.click();
                waitFor(2);
                selectingMultiDropInHistoryTab(data);
                break;
            case "CHANNEL":
                historyTabPage.channelDropdown.click();
                waitFor(2);
                selectingMultiDropInHistoryTab(data);
                break;
        }
    }

    private void selectingMultiDropInHistoryTab(List<String> data) {
        action.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='All']"))).click().build().perform();
        for (String each : data) {
            action.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='" + each + "']"))).click().build().perform();
        }
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    @Then("I verify events displayed by the selected dropdown values")
    public void iVerifyEventsDisplayedByTheSelectedDropdownValues(List<String> data) {
       waitFor(3);
        for (String each : data) {
            switch (each) {
                case "Enrollment":
                    List<String> actualHeaderList = new ArrayList<>();
                    historyTabPage.eventHistoryHeaderList.forEach(headerWebelement -> actualHeaderList.add(headerWebelement.getText()));
                    for (String s : actualHeaderList) {
                        assertTrue(s.contains("Enrollment"));
                    }
                    break;
                case "Phone":
                    List<String> actualChannelList = new ArrayList<>();
                    historyTabPage.channelIconList.forEach(channelWebelement -> actualChannelList.add(channelWebelement.getText()));
                    for (String s : actualChannelList) {
                        assertTrue(s.contains("phone"));
                    }
                    break;
                default:
                    fail("Mismatch in provided case key");
            }
        }

    }
}
