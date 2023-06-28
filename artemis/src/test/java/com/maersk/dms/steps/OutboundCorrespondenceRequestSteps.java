package com.maersk.dms.steps;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.step_definitions.OutboundCorrespondenceRequestsStepDefs;
import com.maersk.dms.step_definitions.ViewOutboundCorrespondenceStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
import static com.maersk.crm.api_step_definitions.APIContactRecordController.apicontactRecordId;
//import static com.maersk.crm.utilities.ApiBaseClass.browserutils;
import static com.maersk.crm.utilities.BrowserUtils.*;
import static com.maersk.crm.utilities.World.generalSave;

import java.text.ParseException;
import java.util.*;

public class OutboundCorrespondenceRequestSteps extends CRMUtilities implements ApiBase {

    private final ThreadLocal<OutboundCorrespondenceRequestsStepDefs> outboundCorrespondenceRequestsStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceRequestsStepDefs::new);
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    public static final ThreadLocal<String> notificationReason = ThreadLocal.withInitial(()->null);
    public static final ThreadLocal<String> actualStatus = ThreadLocal.withInitial(()->null);


    @And("I update Outbound Correspondence CId {string} to status {string}")
    public void iUpdateOutboundCorrespondenceCIdToStatus(String cId, String status) {
        if ("previouslyCreated".equalsIgnoreCase(cId)) {
            cId = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()).toString();
        }
        outboundCorrespondenceRequestsStepDefs.get().sendStatusupdate(cId, status);
    }

    @And("I select on the Outbound Correspondence by {string} CId in the case and contacts details tab")
    public void iSelectOnTheOutboundCorrespondenceByCIdInTheCaseAndContactsDetailsTab(String cId) {
        uiAutoUitilities.get().findInOutboundCorrespondenceListByCID(cId);
        outboundCorrespondenceRequestsStepDefs.get().clickOnCId(cId);
    }

    @Then("I verify {string} is in the Outbound Correspondence status dropdown")
    public void iVerifyIsInTheOutboundCorrespondenceStatusDropdown(String dropdownValue) {
        outboundCorrespondenceRequestsStepDefs.get().selectOnStatusDropdown();
        waitFor(2);
        outboundCorrespondenceRequestsStepDefs.get().verifyStatusDropdownValue(dropdownValue);
    }

    @And("I verify the dropdown values in the status reason of Outbound correspondence details edit for Canceled Status")
    public void iVerifyTheDropdownValuesInTheStatusReasonOfOutboundCorrespondenceDetailsEditForCanceledStatus() {
        outboundCorrespondenceRequestsStepDefs.get().selectOnStatusReasonDropdown();
        waitFor(2);
        outboundCorrespondenceRequestsStepDefs.get().verifyStatusReasonDropdownValuesForCanceledStatus();
    }

    @And("I click on Save button in outbound correspondence edit")
    public void iClickOnSaveButtonInOutboundCorrespondenceEdit() {
        outboundCorrespondenceRequestsStepDefs.get().clickOnSaveEditButton();
    }

    @Then("I verify the status reason is required message is dispalyed")
    public void iVerifyTheStatusReasonIsRequiredMessageIsDispalyed() {
        waitFor(2);
        outboundCorrespondenceRequestsStepDefs.get().verifyStatusRequiredErrorMsg();
    }

    @And("I click on edit button on view Outbound Correspondence details")
    public void iClickOnEditButtonOnViewOutboundCorrespondenceDetails() {
        outboundCorrespondenceRequestsStepDefs.get().clickOnEditButton();
    }

    @And("I click on status dropdown and select {string} in Outbound Correspondence details page")
    public void iClickOnStatusDropdownAndSelectInOutboundCorrespondenceDetailsPage(String statusDropdownValue) {
        outboundCorrespondenceRequestsStepDefs.get().selectOnStatusDropdown();
        waitFor(1);
        outboundCorrespondenceRequestsStepDefs.get().clickOnStatusValue(statusDropdownValue);
    }

    @And("I select {string} for Status and {string} for status reason in Outbound Correspondence edit")
    public void iSelectForStatusAndForStatusReasonInOutboundCorrespondenceEdit(String statusValue, String reasonValue) {
        iClickOnStatusDropdownAndSelectInOutboundCorrespondenceDetailsPage(statusValue);
        waitFor(1);
        outboundCorrespondenceRequestsStepDefs.get().selectOnStatusReasonDropdown();
        waitFor(1);
        outboundCorrespondenceRequestsStepDefs.get().clickOnStatusReasonValue(reasonValue);
    }

    @Then("I verify Consumers Correspondence Preference dropdown option for confirm or not confirm is given")
    public void iVerifyConsumersCorrespondencePreferenceDropdownOptionForConfirmOrNotConfirmIsGiven() {
        waitFor(2);
        outboundCorrespondenceRequestsStepDefs.get().verifyCancelWarningMsg();
    }

    @Then("I initiated GET outbound Correspondence API with {string} CId")
    public void iInitiatedGETOutboundCorrespondenceAPIWithCId(String cId) {
        if ("previouslyCreated".equalsIgnoreCase(cId)) {
            cId = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()).toString();
        }
        BrowserUtils.waitFor(10);
        outboundCorrespondenceRequestsStepDefs.get().getOutCorrWithCId(cId);
    }

    @Then("I verify the following values in the correspondence response")
    public void iVerifyTheFollowingValuesInTheCorrespondenceResponse(Map<String, String> datatable) {
        outboundCorrespondenceRequestsStepDefs.get().verifyGetExpectedValues(datatable);
    }

    @And("I click yes when given the option to confirm cancel or not cancel")
    public void iClickYesWhenGivenTheOptionToConfirmCancelOrNotCancel() {
        outboundCorrespondenceRequestsStepDefs.get().clickYesOnCancelConfirmMsg();
    }

    @And("I update Outbound Notification Id {string} to status {string}")
    public void iUpdateOutboundNotificationIdToStatus(String nId, String status) {

        if (nId.equalsIgnoreCase("previouslyCreatedlistofnid")) {
            for (String nid : World.generalList.get())
                API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateNotification(nid, status);
        } else {
            API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateNotification(nId, status);
        }
    }

    @And("I click on notification actions dropdown to select {string}")
    public void iClickOnNotificationActionsDropdownToSelect(String nadValue) {
        outboundCorrespondenceRequestsStepDefs.get().clickNADandSelect(nadValue);
    }

    @And("I have access to reason dropdown and return date dropdown")
    public void iHaveAccessToReasonDropdownAndReturnDateDropdown() {
        outboundCorrespondenceRequestsStepDefs.get().accessToReasonAndReturnDateDropdown();
    }

    @Then("I verify status has changed to {string}")
    public void iVerifyStatusHasChangedTo(String notificationStatusValue) {
        outboundCorrespondenceRequestsStepDefs.get().verifyNotificationStatusValue(notificationStatusValue);
    }

    @And("I click on the notification returned reason dropdown")
    public void iClickOnTheNotificationReturnedReasonDropdown() {
        outboundCorrespondenceRequestsStepDefs.get().selectOnNotificationReasonDropdown();
    }

    @Then("I verify the reason values contained are following")
    public void iVerifyTheReasonValuesContainedAreFollowing(List<String> reasonValues) {
        outboundCorrespondenceRequestsStepDefs.get().VerifyReturnedNotiReasonsValues(reasonValues);
    }

    @And("I click on save button in notifications details")
    public void iClickOnSaveButtonInNotificationsDetails() {
        outboundCorrespondenceRequestsStepDefs.get().clickOnSaveEditButton();
    }

    @Then("I verify the following error messages appears when Reason field is left blank")
    public void iVerifyTheFollowingErrorMessagesAppearsWhenReasonFieldIsLeftBlank(String errorMsg) {
        outboundCorrespondenceRequestsStepDefs.get().checkReasonsBlankErrorMsg(errorMsg);
    }

    @Then("I verify the following error messages appears when RETURN DATE fields is left blank")
    public void iVerifyTheFollowingErrorMessagesAppearsWhenRETURNDATEFieldsIsLeftBlank(String errorMsg) {
        outboundCorrespondenceRequestsStepDefs.get().checkReturnDatesBlankErrorMsg(errorMsg);
    }

    @And("I select {string} and {string} for reasons and returned date dropdowns")
    public void iSelectAndForReasonsAndReturnedDateDropdowns(String reasons, String date) {
        outboundCorrespondenceRequestsStepDefs.get().selectReturnedReasons(reasons);
        outboundCorrespondenceRequestsStepDefs.get().selectReturnedReturnDate(date);
    }

    @Then("I verify the following values in the correspondence notification response")
    public void iVerifyTheFollowingValuesInTheCorrespondenceNotificationResponse(Map<String, String> datatable) {
        outboundCorrespondenceRequestsStepDefs.get().verifyGetNotification(datatable);
    }

    @Then("I verify the following values in the correspondence notification response for second Recipient")
    public void iVerifyTheFollowingValuesInTheCorrespondenceNotificationResponseForSecondRecipient(Map<String, String> datatable) {
        outboundCorrespondenceRequestsStepDefs.get().verifyGetNotificationForSecondRecipient(datatable);
    }

    @Then("I verify the correspondence status is {string}")
    public void iVerifyTheCorrespondenceStatusIs(String corrStatus) {
        outboundCorrespondenceRequestsStepDefs.get().verifyCorrespondenceStatus(corrStatus);
    }

    @Then("I verify the correspondence language is {string}")
    public void iVerifyTheCorrespondenceLanguageIs(String corrlang) {
        outboundCorrespondenceRequestsStepDefs.get().verifyCorrespondenceLanguage(corrlang);
    }

    @And("I click on Cancel button on the notification")
    public void iClickOnCancelButtonOnTheNotification() {
        outboundCorrespondenceRequestsStepDefs.get().clickOnNotificationCancel();
    }

    @Then("I should see a warning message when navigating away {string}")
    public void iShouldSeeAWarningMessageWhenNavigatingAway(String warning) {
        outboundCorrespondenceRequestsStepDefs.get().verifyNavAwayMsg(warning);
    }

    @And("I click on the back arrow button on Outbound Correspondence details")
    public void iClickOnTheBackArrowButtonOnOutboundCorrespondenceDetails() {
        outboundCorrespondenceRequestsStepDefs.get().clickOnBackArrowBtn();
    }

    @And("I verify Notification action dropdown does not contain Returned")
    public void iVerifyNotificationActionDropdownDoesNotContainReturned() {
        outboundCorrespondenceRequestsStepDefs.get().verifyReturnedNotContained();
    }

    @Then("I verify Notification action dropdown contains Returned")
    public void iVerifyNotificationActionDropdownContainsReturned() {
        outboundCorrespondenceRequestsStepDefs.get().verifyReturnedContained();
    }

    @When("I click on the Notifications detail page status history button")
    public void iClickOnTheNotificationsDetailPageStatusHistoryButton() {
        outboundCorrespondenceRequestsStepDefs.get().clickOnStatusHistorBtn();
    }

    @Then("I verify the Returned status is visible in status history")
    public void iVerifyTheReturnedStatusIsVisibleInStatusHistory() {
        outboundCorrespondenceRequestsStepDefs.get().verifyReturnedIsVisibleInHistory();
    }

    @And("I initiate post outbound correspondence with four recipient")
    public void iInitiatePostOutboundCorrespondenceWithFourRecipient() {
        outboundCorrespondenceRequestsStepDefs.get().postOutboundCorrWithFour();
    }

    @And("I search for the Outbound Correspondence with the new created CId")
    public void iSearchForTheOutboundCorrespondenceWithTheNewCreatedCId() {
        outboundCorrespondenceRequestsStepDefs.get().searchWithcreatedCId();
    }

    @And("I click on the correspondence id of the search result")
    public void iClickOnTheCorrespondenceIdOfTheSearchResult() {
        outboundCorrespondenceRequestsStepDefs.get().clickOnCidResult();
    }

    @Then("I verify Outbound correspondence is published for the canceled Outbound Correspondence")
    public void iVerifyOutboundCorrespondenceIsPublishedForTheCanceledOutboundCorrespondence() {
        outboundCorrespondenceRequestsStepDefs.get().verifyCanceledEventPublished();
    }

    @And("I change the status of the created notification status to")
    public void iChangeTheStatusOfTheCreatedNotificationStatusTo(List<String> notificationStatus) {
        outboundCorrespondenceRequestsStepDefs.get().getNotificationID();
        outboundCorrespondenceRequestsStepDefs.get().updatedNotiStatuswithNId(notificationStatus);
    }

    @Then("I verify notification status has changed for each status notification for following")
    public void iVerifyNotificationStatusHasChangedForEachStatusNotificationForFollowing(List<String> expectedStatusList) {
        outboundCorrespondenceRequestsStepDefs.get().verifyAllNotificationStatus(expectedStatusList);
    }

    @And("I update the created Outbound Correspondence to {string}")
    public void iUpdateTheCreatedOutboundCorrespondenceTo(String status) {
        outboundCorrespondenceRequestsStepDefs.get().changeCreatedCorrespondenceStatus(status);
    }

    @Then("I verify created outbound correspondence notification status is unchanged")
    public void iVerifyCreatedOutboundCorrespondenceNotificationStatusIsUnchanged() {
        outboundCorrespondenceRequestsStepDefs.get().getCreatedOutCorr();
        outboundCorrespondenceRequestsStepDefs.get().checkNotiStatusUnchanged();
    }


    @And("I change the status of the created notification status to Precluded")
    public void iChangeTheStatusOfTheCreatedNotificationStatusToPrecluded() {
        outboundCorrespondenceRequestsStepDefs.get().getNotificationID();
        outboundCorrespondenceRequestsStepDefs.get().postNotificationStatusToPrecluded();
    }

    @And("I have Outbound Correspondence {string} is linked to caseId {string}")
    public void iHaveOutboundCorrespondenceIsLinkedToCaseId(String cid, String caseId) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()).toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(caseId)) {
            Map<String, String> caseconsumerid = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            caseId = caseconsumerid.get("caseId");
        }
        outboundCorrespondenceRequestsStepDefs.get().verifyOutboundLinkedToCase(cid, caseId);
    }

    @And("I click on notification actions dropdown to select cancel")
    public void iClickOnNotificationActionsDropdownToSelectCancel() {
        if (outboundCorrespondenceRequestsStepDefs.get().notificationStatus().equalsIgnoreCase("Sent") ||
                outboundCorrespondenceRequestsStepDefs.get().notificationStatus().equalsIgnoreCase("Returned") ||
                outboundCorrespondenceRequestsStepDefs.get().notificationStatus().equalsIgnoreCase("Canceled")) {
            Assert.assertTrue(false, "Can not cancel Notification status");
        } else {
            outboundCorrespondenceRequestsStepDefs.get().clickAndSelectCancel();
        }
    }

    @And("I verify user not allowed to cancel notifications")
    public void iVerifyUserNotAllowedToCancelNotifications() {
        outboundCorrespondenceRequestsStepDefs.get().verifyCancelNotDisplayed();
    }

    @And("I verify error message for notification reason")
    public void iVerifyErrorMessageForNotificationReason() {
        outboundCorrespondenceRequestsStepDefs.get().verifyNotificationReasonError();
    }

    @Then("I verify notification status and reason is updated to Cancel")
    public void iVerifyNotificationStatusAndReasonIsUpdatedToCancel() {
        waitFor(5);
        Assert.assertTrue(outboundCorrespondenceRequestsStepDefs.get().notificationStatus().equalsIgnoreCase("canceled"), "Status is not updated");
        Assert.assertEquals(notificationReason.get(), Driver.getDriver().findElement(By.xpath("(//p[.='REASON'])/../h6")).getText());
    }

    @And("I get rawLogs for notification verify all infos")
    public void iGetRawLogsForNotificationVerifyAllInfos() {
        waitFor(5);
        List<String> caseMemberSave = EventsUtilities.getRawLogs("currentStatus");
        String firstLog = caseMemberSave.get(0);
        String updatedBy = Driver.getDriver().findElement(By.xpath("//p[text()='Service  AccountOne']/parent::div/p[2]")).getText().replace("ID ", "");
        String presentTimeStamp = apitdu.getCurrentDateAndTime("yyyy-MM-dd");
        Assert.assertTrue(firstLog.contains("\\\"updatedOn\\\":\\\"" + presentTimeStamp));
        Assert.assertTrue(firstLog.contains("\"createdDate\":\"" + presentTimeStamp));
        Assert.assertTrue(firstLog.contains("\"statusChangedDate\":\"" + presentTimeStamp));
        Assert.assertTrue(firstLog.contains("\"statusReason\":\"" + notificationReason.get()));
        Assert.assertTrue(firstLog.contains("\"changedBy\":\"" + updatedBy));
        Assert.assertTrue(firstLog.contains("\"status\":\"" + actualStatus.get()));
        Assert.assertTrue(firstLog.contains("\"status\":\"Canceled\""));

    }

    @And("I validated for {string} notification status")
    public void iValidatedforNotificationStatus(String expectednotificationstatus) {
        String actualnotificationstatus = outboundCorrespondenceRequestsStepDefs.get().notificationStatus();
        Assert.assertEquals(actualnotificationstatus, expectednotificationstatus, "Actual notification status is " + actualnotificationstatus + " --- " + expectednotificationstatus + " is Expected");
    }

    @And("I validate the {string} dropdown values in outbound correspondence")
    public void ivalidatedropdownvaluesinoutboundcorrespondence(String dropdownname, List<String> dropdownoptions) {
        outboundCorrespondenceRequestsStepDefs.get().iVerifyThecOutboundCorrespondenceDropdownValuesFor(dropdownname, dropdownoptions);
    }

    @And("I verify error message for notification status reason in Notification grid")
    public void iVerifyErrorMessageForNotificationStatusReason() {
        outboundCorrespondenceRequestsStepDefs.get().verifyNotificationStatusReasonErrorMsg();
    }

    @And("verify the Status date field for sent status")
    public void verifytheStatusdatefieldforsentstatus() {
        if (outboundCorrespondenceRequestsStepDefs.get().notificationStatus().equalsIgnoreCase("Sent")) {
            outboundCorrespondenceRequestsStepDefs.get().verifyNotificationStatusdate();
        }
    }

    @And("I verify error message for notification status date in Notification grid")
    public void iVerifyErrorMessageForNotificationStatusDate() {
        outboundCorrespondenceRequestsStepDefs.get().verifyNotificationStatusDateErrorMsg();
    }

    @And("I verify notification status reason is in disable mode")
    public void iVerifydiableNotificationStatusResaon() {
        outboundCorrespondenceRequestsStepDefs.get().verifydisableNotificationStatus();
    }

    @And("I verify error message for notification Return date in Notification grid")
    public void iVerifyErrorMessageForNotificationReturnedDate() {
        outboundCorrespondenceRequestsStepDefs.get().verifyNotificationReturnedDateErrorMsg();
    }

    @And("I Update the Return date field with {string} for Returned status")
    public void updateReturndateforReturnedstatus(String date) {
        if (outboundCorrespondenceRequestsStepDefs.get().notificationStatus().equalsIgnoreCase("Returned")) {
            outboundCorrespondenceRequestsStepDefs.get().updateNotificationReturndate(date);
        }
    }

    @And("I cleared the date from calendar")
    public void iclearedDate() {
        outboundCorrespondenceRequestsStepDefs.get().clearcalendardate();
    }

    @And("I select {string} value from {string} dropdown")
    public void iSelectvaluefromdropdown(String ddvalue, String ddname) {
        outboundCorrespondenceRequestsStepDefs.get().iSelectcustomvaluefromdropdown(ddvalue, ddname);
    }

    @And("I verify correspondence status reason is in disable mode")
    public void iVerifydiableCorrespondenceStatusResaon() {
        outboundCorrespondenceRequestsStepDefs.get().verifydisablecorrespndenceStatusreason();
    }

    @And("I initiate post outbound correspondence with four channels")
    public void i_initiate_post_outbound_correspondence_with_four_channels() {
        outboundCorrespondenceRequestsStepDefs.get().postOutboundCorrWithFourChannels();
    }

    @And("I verify View Outbound Correspondence Details page")
    public void i_verify_View_Outbound_Correspondence_Details_page() {
        outboundCorrespondenceRequestsStepDefs.get().verifyCorrespondenceId();
        outboundCorrespondenceRequestsStepDefs.get().verifyCreatedByLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyCreatedOnLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyUpdatedByLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyUpdatedOnLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyStatusDateLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyResponseDueDateLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyCorrespondenceTypeLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyLanguageLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyStatusLabelAndFormat();
        outboundCorrespondenceRequestsStepDefs.get().verifyRegardingSection();
        outboundCorrespondenceRequestsStepDefs.get().verifyEditButtonAndStatusDropdowns();
    }

    @And("I verify Outbound Correspondence Notification for one recipient and four Channels")
    public void i_verify_Outbound_Correspondence_Notification_for_one_recipient_and_four_Channels() throws ParseException {
        outboundCorrespondenceRequestsStepDefs.get().verifyRecipientsInfoInNotifications();
        outboundCorrespondenceRequestsStepDefs.get().verifyStatusHistoryAndBurgerOptionsFunctionality();
    }

    @When("I change the status for any created notification to Precluded")
    public void i_change_the_status_for_any_created_notification_to_Precluded() {
        outboundCorrespondenceRequestsStepDefs.get().updateStatusToPrecluded();
    }

    @Then("I will not see Preclude notification on IU")
    public void i_will_not_see_preclude_notification_on_IU() {
        outboundCorrespondenceRequestsStepDefs.get().verifyChannelUpdatedToPrecludeNotDisplayed();
    }

    @And("I click No when given the option to confirm cancel or not cancel")
    public void iClickNoWhenGivenTheOptionToConfirmCancelOrNotCancel() {
        outboundCorrespondenceRequestsStepDefs.get().clickNOOnCancelConfirmMsg();
    }

    @Then("I verify the correspondence status reason is {string}")
    public void iVerifyTheCorrespondenceStatusReasonIs(String corrStatusReason) {
        outboundCorrespondenceRequestsStepDefs.get().verifyCorrespondenceStatusReason(corrStatusReason);
    }

    @And("I validated for {string} notification status reason")
    public void iValidatedforNotificationStatusReason(String expectednotificationstatusreason) {
        String actualnotificationstatus = outboundCorrespondenceRequestsStepDefs.get().notificationStatusreason();
        Assert.assertEquals(actualnotificationstatus, expectednotificationstatusreason, "Actual notification status reason is " + actualnotificationstatus + " --- " + expectednotificationstatusreason + " is Expected");
    }

    @And("I invoke unprovisioned endpoint to return a list of OBcorrespondence orders that are in Requested status and have no recipients")
    public void iInvokeTheEndpointreturnslistofOBcorrespondenceordersthatareinRequestedstatusandhavenorecipientst() {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().retrieveUnprovisionedObCorrespondences();
        List<Integer> obcids = new ArrayList<>(response.getList("cids"));
        for (int cid : obcids)
            outboundCorrespondenceRequestsStepDefs.get().validateunprovisionedcid(String.valueOf(cid));
        World.generalSave.get().put("unprovisionedObcids", obcids);
    }

    @And("I validate Outbound Correspondence order {string} in the unprovisioned list")
    public void iValidateObCorrOrderinUnprovisionedlist(String occurence, List<String> obcids) {
        ArrayList<Integer> unprovisionedcids = (ArrayList<Integer>) World.generalSave.get().get("unprovisionedObcids");

        for (String cid : obcids) {
            boolean status = false;

            if ("previouslyCreated".equalsIgnoreCase(cid)) {
                cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()).toString();
            }

            for (Integer uncid : unprovisionedcids) {
                if (Objects.equals(uncid, Integer.parseInt(cid))) {
                    status = true;
                    break;
                }
            }
            if (occurence.equalsIgnoreCase("available") && status)
                Assert.assertTrue(status);
            else if (occurence.equalsIgnoreCase("available") && !status)
                Assert.assertTrue(status, "Ob corr order not found in unprovisionedlist : " + cid);
            else if (occurence.equalsIgnoreCase("unavailable") && !status)
                Assert.assertFalse(status);
            else if (occurence.equalsIgnoreCase("unavailable") && status)
                Assert.assertFalse(status, "Ob corr order found in unprovisionedlist : " + cid);

        }

    }

    @And("I validate Outbound Correspondence order {string} in the provisioned list")
    public void iValidateObCorrOrderinprovisionedlist(String occurence, List<String> obcids) {

        for (String cid : obcids) {
            if ("previouslyCreated".equalsIgnoreCase(cid))
                cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()).toString();
            else if ("previouslyCreated1".equalsIgnoreCase(cid))
                cid = World.random.get().get("previouslyCreated1").toString();
            else if ("previouslyCreated2".equalsIgnoreCase(cid))
                cid = World.random.get().get("previouslyCreated2").toString();

            boolean status = outboundCorrespondenceRequestsStepDefs.get().validateprovisionedcid(cid);

            if (occurence.equalsIgnoreCase("processed"))
                Assert.assertFalse(status, "Ob corr order unprocessed : " + cid);
            else if (occurence.equalsIgnoreCase("unprocessed"))
                Assert.assertTrue(status, "Ob corr order processed : " + cid);

        }
    }

    @And("I store {string} outbound correspondence id in random map as {string}")
    public void iValidateObCorrOrderinprovisionedlist(String obcid, String obcidname) {
        if ("previouslyCreated".equalsIgnoreCase(obcid))
            obcid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()).toString();
        World.random.get().put(obcidname, obcid);
    }

    @And("I verify outbound correspondence NotificationId Language")
    public void iVerifyNotificationLanguage(Map<String, String> dataTable) {
        outboundCorrespondenceRequestsStepDefs.get().verifyNotificationDeatailsUI(dataTable);
    }

    @And("I retrieve the Outbound Correspondence details again")
    public void iRetrieveTheOutboundCorrespondenceDetailsAgain() {
        JsonPath response = (JsonPath) World.generalSave.get().get("sendNowResponse");
        String cid = response.getString("id");
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get(), cid);
        response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        World.generalSave.get().put("sendNowResponse", response);
    }

    @And("I verify updated on and updated by values on Outbound Correspondence Details")
    public void iVerifyUpdatedOnAndUpdatedByValuesOnOutboundCorrespondenceDetails() {
        outboundCorrespondenceRequestsStepDefs.get().verifyUpdatedByValue();
        outboundCorrespondenceRequestsStepDefs.get().verifyUpdatedOnValue();
    }

    @And("I verify status history of notification status for {string}")
    public void iVerifyStatusHistoryOfNotificationStatusFor(String notificationStatus) throws ParseException {
        outboundCorrespondenceRequestsStepDefs.get().verifyStatusHistoryForNotificationStatus(notificationStatus);
    }

    @And("I validate the Bulk Outbound Correspondence is created")
    public void iRetrieveTheBulkOutboundCorrespondenceDetails() {
        JsonPath response = (JsonPath) World.generalSave.get().get("BULKOUTBOUNDCORRESPONDENCERESPONSE");
        String cid = response.get("data.id").toString();
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get(), cid);
        Assert.assertTrue(!cid.isEmpty());
    }

    @And("I validate the error message for Bulk Outbound Correspondence for missing {string}")
    public void iValidateWarningmessageforBulkOutboundCorrespondenceDetails(String field) {
        boolean isWarningMessageFound = false;
        String expectedErrorMessage = outboundCorrespondenceRequestsStepDefs.get().returnExpectedErrorMessageForBulkAPI(field);
        JsonPath response = (JsonPath) World.generalSave.get().get("BULKOUTBOUNDCORRESPONDENCERESPONSE");
        List<Map<String, Object>> errors = response.getList("errors");
        for (Map<String, Object> error : errors) {
            String warningmsg = error.get("message").toString();
            if (warningmsg.equalsIgnoreCase(expectedErrorMessage)) {
                isWarningMessageFound = true;
            }
        }
        Assert.assertTrue(isWarningMessageFound, field + "Warning Message is not found in Bulk API response ");
    }


    @And("I validate only {int} ErrorMessages Returned for used Bulk Correspondence Response")
    public void iValidateOnlyErrorMessagesReturnedForUsedBulkCorrespondenceResponse(int expectedErrorCount) {
        JsonPath response = (JsonPath) World.generalSave.get().get("BULKOUTBOUNDCORRESPONDENCERESPONSE");
        List<Map<String, Object>> errors = response.getList("errors");
        Assert.assertTrue(errors.size() == expectedErrorCount, "Error count didn't match.Expected " + expectedErrorCount + " .But found " + errors.size());
    }

    @And("I validate following values in Create Outbound Notification response")
    public void ivalidateCreateOutboundNotificationDetails(Map<String, String> data) {
        JsonPath response= (JsonPath) World.generalSave.get().get("CREATEOBNOTIFICATIONRESPONSE");
        for (String key : data.keySet()) {
            switch (key) {
                case "middleName":
                    Assert.assertEquals(response.get("recipient.middleName").toString(), data.get(key));
                    break;
                case "nameSuffix":
                    Assert.assertEquals(response.get("recipient.nameSuffix").toString(), data.get(key));
                case "notificationid":
                    Assert.assertFalse(response.get("notificationId").toString().isEmpty());
                    World.generalSave.get().put("RESENDNOTIFICATIONID", response.get("notificationId").toString());
                    break;
                default:
                    Assert.fail("no matching keys for - \"" + key + "\"");
            }
        }
    }

    @And("I validate following values in GET Outbound Notification response")
    public void ivalidateGetOutboundNotificationDetails(Map<String, String> data) {
        JsonPath response = (JsonPath) World.generalSave.get().get("GETOBNOTIFICATIONRESPONSE");

        for (String key : data.keySet()) {
            switch (key) {
                case "middleName":
                    Assert.assertEquals(response.getList("recipient.middleName").get(1), data.get(key));
                    break;
                case "nameSuffix":
                    Assert.assertEquals(response.getList("recipient.nameSuffix").get(1), data.get(key));
                    break;
                default:
                    Assert.fail("no matching keys for - \"" + key + "\"");
            }
        }
    }

    @Then("I should see there is a link between {string} {string} and Outbound Correspondence {string}")
    public void iShouldSeeThereIsALinkBetweenAndOutboundCorrespondence(String entityType, String entityId, String cid) {
        cid = cid.equalsIgnoreCase("previouslyCreated") ? (String) World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()) : cid;
        if (entityId.equalsIgnoreCase("previouslyCreated")) {
            switch (entityType.toLowerCase(Locale.ROOT)) {
                case "service request":
                case "task":
                    entityId = CRM_TaskManagementStepDef.taskId.get();
                    break;
                case "case":
                case "consumer profile":
                    Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
                    if (entityType.equalsIgnoreCase("case")) {
                        entityId = caseConsumerId.get("caseId");
                    } else {
                        entityId = caseConsumerId.get("consumerId");
                    }
                    break;
                case "inbound correspondence":
                    entityId = World.generalSave.get().get("InboundDocumentId").toString();
                    break;
                case "application":
                    entityId = applicationIdAPI.get();
                    break;
                case "missing information":
                    entityId = String.valueOf(World.generalSave.get().get("MissingInformationItemId"));
                    break;
                case "contact record":
                    entityId = apicontactRecordId.get().toString();
                    break;

            }
            outboundCorrespondenceRequestsStepDefs.get().verifyOutboundLinkedToEntity(entityType, cid, entityId);
        }
    }

    @Then("I should see there is a link between Outbound Correspondence {string} and {string} {string}")
    public void iShouldSeeThereIsALinkBetweenOutboundCorrespondenceAnd(String cid, String entityType, String entityId) {
        cid = cid.equalsIgnoreCase("previouslyCreated") ? (String) World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()) : cid;
        if (entityId.equalsIgnoreCase("previouslyCreated")) {
            switch (entityType.toLowerCase(Locale.ROOT)) {
                case "service request":
                case "task":
                    entityId = CRM_TaskManagementStepDef.taskId.get();
                    break;
                case "case":
                case "consumer profile":
                    Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
                    if (entityType.equalsIgnoreCase("case")) {
                        entityId = caseConsumerId.get("caseId");
                    } else {
                        entityId = caseConsumerId.get("consumerId");
                    }
                    break;
                case "inbound correspondence":
                    entityId = World.generalSave.get().get("InboundDocumentId").toString();
                    break;
                case "application":
                    entityId = applicationIdAPI.get();
                    break;
                case "missing information":
                    entityId = String.valueOf(World.generalSave.get().get("MissingInformationItemId"));
                    break;
                case "contact record":
                    entityId = apicontactRecordId.get().toString();
                    break;

            }
        }
        outboundCorrespondenceRequestsStepDefs.get().verifyEntityLinkedToOutbound(entityType, cid, entityId);
    }

    @And("I shouldn't see a link between Outbound Correspondence {string} and {string} {string}")
    public void iShouldnTSeeALinkBetweenOutboundCorrespondenceAnd(String cid, String entityType, String entityId) {
        cid = cid.equalsIgnoreCase("previouslyCreated") ? (String) World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get()) : cid;
        if (entityId.equalsIgnoreCase("previouslyCreated")) {
            switch (entityType.toLowerCase(Locale.ROOT)) {
                case "service request":
                case "task":
                    entityId = CRM_TaskManagementStepDef.taskId.get();
                    break;
                case "case":
                case "consumer":
                    Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
                    if (entityType.equalsIgnoreCase("case")) {
                        entityId = caseConsumerId.get("caseId");
                    } else {
                        entityId = caseConsumerId.get("consumerId");
                    }
                    break;
                case "inbound correspondence":
                    entityId = World.generalSave.get().get("InboundDocumentId").toString();
                    break;
                case "application":
                    entityId = applicationIdAPI.get();
                    break;
                case "missing information":
                    entityId = String.valueOf(World.generalSave.get().get("MissingInformationItemId"));
                    break;
                case "contact record":
                    entityId = apicontactRecordId.get().toString();
                    break;

            }
        }
        outboundCorrespondenceRequestsStepDefs.get().verifyEntityNotLinkedToOutbound(entityType, cid, entityId);
    }

    @And("I validate {string} dropdown initially blank")
    public void iValidateLanguagedropdownavailable(String field) {
        outboundCorrespondenceRequestsStepDefs.get().resendropdown(field);
    }


    @And("I validate following values for Add or Resend Notification")
    public void iValidatechanneldropdown(Map<String, String> data) {
        outboundCorrespondenceRequestsStepDefs.get().add_resend_notification(data);
    }

    @And("I validate following required fields warning msgs for Add or Resend Notification")
    public void iValidatewarningmsgs(Map<String, String> data) {
        outboundCorrespondenceRequestsStepDefs.get().add_resend_warningmsgs(data);
    }

}

