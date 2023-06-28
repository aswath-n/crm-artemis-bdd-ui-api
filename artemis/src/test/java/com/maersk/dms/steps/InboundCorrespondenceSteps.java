package com.maersk.dms.steps;

import com.maersk.crm.step_definitions.*;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.pages.CaseAndContactDetailsPage;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.step_definitions.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static com.maersk.crm.utilities.World.generalSave;

public class InboundCorrespondenceSteps extends CRMUtilities implements ApiBase {
    final ThreadLocal<InboundCorrespondenceStepDefs> inboundStepDefs = ThreadLocal.withInitial(InboundCorrespondenceStepDefs::new);
    final ThreadLocal<ViewOutboundCorrespondenceStepDefs> viewOutboundCorrespondenceStepDefs = ThreadLocal.withInitial(ViewOutboundCorrespondenceStepDefs::new);
    CRM_DoNotCacheSearchParametersStepDef crm_doNotCacheSearchParametersStepDef = new CRM_DoNotCacheSearchParametersStepDef();
    final ThreadLocal<CRM_ContactRecordUIStepDef> crm_contactRecordUIStepDef = ThreadLocal.withInitial(CRM_ContactRecordUIStepDef::new);
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private final ThreadLocal<JsonPath> retrieveInbResponse = new ThreadLocal<>();
    InboundCorrespondencePage inbound = new InboundCorrespondencePage();
    private final ThreadLocal<InboundDocumentTaskStepDefs> inboundDocumentTaskStepDefs = ThreadLocal.withInitial(InboundDocumentTaskStepDefs::new);
    private final ThreadLocal<InboundCorrespondenceSearchStepDef> inboundCorrespondenceSearchStepDef = ThreadLocal.withInitial(InboundCorrespondenceSearchStepDef::new);
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);
    private CaseAndContactDetailsPage caseAndContactDetailsPage = new CaseAndContactDetailsPage();


    @And("I End the Live Contact and save the Inbound Correspondence in Contact Details")
    public void iEndTheLiveContactAndSaveTheInboundCorrespondence() {
        inboundStepDefs.get().endContact();
        inboundStepDefs.get().saveInboundCorrespondence();
    }

    @When("I navigate to the case and contact details tab")
    public void iNavigateToTheCaseAndContactDetailsTab() {
        inboundStepDefs.get().navigateToCaseContactDetailsScreen();
    }

    @Then("I should see the CID for the correspondence as a hyperlink")
    public void iShouldSeeTheCIDForTheCorrespondenceAsHyperlink() {
        inboundStepDefs.get().verifyCidHyperLink();
    }

    @When("I populate only {string} for an Active Contact")
    public void iPopulateOnlyFirstNameForAnActiveContact(String user) {
        inboundStepDefs.get().consumerFirstName(user);
    }

    @Then("I link the active contact to an existing contact by {string}")
    public void iLinkTheActiveContactBy(String firstName) throws AWTException {
        inboundStepDefs.get().linkContactByFirstName(firstName);
    }

    @Given("I initiated contact")
    public void i_initiated_contact() {
        crm_contactRecordUIStepDef.get().initiateContact();
    }

    @Then("I should see the type is visible for each correspondence")
    public void iShouldSeeTheTypeIsVisibleForEachCorrespondence() {
        inboundStepDefs.get().consumerTypeResultVisible();
    }

    @Then("I should see the date inbound correspondence is received is in the correct format for each correspondence")
    public void i_should_see_the_date_inbound_correspondence_is_received_is_in_the_correct_format_for_each_correspondence() {
        inboundStepDefs.get().verifyDateFormat();
    }

    @Then("I should see the First and Last names of Case members associated are visible.")
    public void i_should_see_the_First_and_Last_names_of_Case_members_associated_are_visible() {
        inboundStepDefs.get().consumerResultVisible();
    }

    @Then("I should see that initial display will show {int} most recent inbound correspondence")
    public void i_should_see_that_initial_display_will_show_most_recent_inbound_correspondence(Integer int1) {
        inboundStepDefs.get().verifyFiveMostRecent();
    }

    @Then("I should see that screen invokes BFF api which invokes Onbase api to retrieve the correspondence items associated with the case and consumer profile, passing either the case id or consumer id")
    public void i_should_see_that_screen_invokes_BFF_api_which_invokes_Onbase_api_to_retrieve_the_correspondence_items_associated_with_the_case_and_consumer_profile_passing_either_the_case_id_or_consumer_id() {
        inboundStepDefs.get().searchCorrespondenceApiWasCalled();
        inboundStepDefs.get().verifyOnbase();
    }

    @Then("I verify that the Status Code is Success")
    public void i_verify_that_the_Status_Code_is_Success() {
        //api call to be added
    }

    @Then("I should see if there is more than one Case member on the case, name field will display first name then ellipsis pop up with the rest")
    public void i_should_see_if_there_is_more_than_one_Case_member_on_the_case_name_field_will_display_first_name_then_ellipsis_pop_up_with_the_rest() throws AWTException {
        inboundStepDefs.get().multipleCaseMembers();
    }

    @And("I click on the view icon for the correspondence {string}")
    public void iClickOnTheViewIconForTheCorrespondence(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("id");
        } else if ("CREATEDFROMAPI".equalsIgnoreCase(cid)) {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()));
        }
        viewOutboundCorrespondenceStepDefs.get().viewCorrespondence(cid);
    }

    @Then("I should see the exact {string} document that was sent")
    public void i_should_see_the_exact_document_that_was_sent(String string) throws IOException {
        viewOutboundCorrespondenceStepDefs.get().verifyNotificationViewed(string);
    }

    @Then("I should be able to zoom in and out of document")
    public void i_should_be_able_to_zoom_in_and_out_of_document() throws AWTException {
        viewOutboundCorrespondenceStepDefs.get().zoomInOutOfDoc();
    }

    @Then("I should be able to scroll and view all of document")
    public void i_should_be_able_to_scroll_and_view_all_of_document() throws AWTException {
        viewOutboundCorrespondenceStepDefs.get().scrollDoc();
    }

    @Then("I should be able to see how many pages and can choose which page to display")
    public void i_should_be_able_to_see_how_many_pages_and_can_choose_which_page_to_display() throws AWTException, IOException {
        viewOutboundCorrespondenceStepDefs.get().viewHowManyPages();
    }

    @Then("I should see the notification id in dev tools during retrieval")
    public void i_should_see_the_notification_id_in_dev_tools_during_retrieval() throws IOException {
        viewOutboundCorrespondenceStepDefs.get().verifyNotificationViewed("Mail");
    }


    @Then("I should see there is a link between Case Id {string} and Inbound Correspondence Document {string}")
    public void iShouldSeeThereIsALinkBetweenCaseIdAndInboundCorrespondenceDocument(String caseId, String cid) {
        inboundStepDefs.get().verifyCaseLinkedToInbDocument(caseId, cid);
    }

    @Then("I should see there is a link between Inbound Correspondence Document {string} and Case Id {string}")
    public void iShouldSeeThereIsALinkBetweenInboundCorrespondenceDocumentAndCaseId(String cid, String caseId) {
        inboundStepDefs.get().verifyInbDocmentLinkedToCase(cid, caseId);
    }

    @Then("I should see there is a link between Inbound Correspondence Document {string} and Consumer Id {string}")
    public void iShouldSeeThereIsALinkBetweenInboundCorrespondenceDocumentAndConsumerId(String cid, String consumerId) {
        inboundStepDefs.get().verifyInboundLinkedToConsumer(cid, consumerId);
    }

    @Then("I should see there is a link between Consumer Id {string} and Inbound Correspondence Document {string}")
    public void iShouldSeeThereIsALinkBetweenConsumerIdAndInboundCorrespondenceDocument(String consumerId, String cid) {
        inboundStepDefs.get().verifyConsumerLinkedToInboundDocument(consumerId, cid);
    }

    @Then("I should see there is a link between Notification Id {string} and Inbound Correspondence Document {string}")
    public void iShouldSeeThereIsALinkBetweenNotificationIdAndInboundCorrespondenceDocument(String nid, String cid) {
        inboundStepDefs.get().verifyInbLinkedToOutboundDocument(nid, cid);
    }

    @Then("I should see the Inbound Document {string} is linked to Outbound Correspondence {string}")
    public void iShouldSeeTheInboundDocumentIsLinkedToOutboundCorrespondence(String inbCid, String outCid) {
        inboundStepDefs.get().verifyInbLinkedToOutboundDocumentUI(inbCid, outCid);
    }

    @Then("I should see there is a link between Inbound Correspondence Id {string} and Outbound Correspondence {string}")
    public void iShouldSeeThereIsALinkBetweenInboundCorrespondenceIdAndOutboundCorrespondence(String inbCid, String outCid) {
        inboundStepDefs.get().verifyOutboundLinkedToInboundDocument(outCid, inbCid);
    }

    @And("I click on the previous created CID {string}")
    public void iClickOnThePreviousCreatedCID(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("id");
        }
        waitFor(10);
        OutboundCorrespondenceRequestSteps.actualStatus.set(Driver.getDriver().findElement(By.xpath("//*[text()='" + cid + "']/../td[7]")).getText());
        viewOutboundCorrespondenceStepDefs.get().viewCorrespondenceCID(cid);
    }


    @And("I verify Status is changed for notification on UI")
    public void iVerifyStatusIsChangedForNotificationOnUI() {
        List<WebElement> nidStatuses = Driver.getDriver().findElements(By.xpath("//p[.='STATUS']/../h6"));
        List<WebElement> nids = Driver.getDriver().findElements(By.xpath("//p[.='NID']/../h6"));
        for (int i = 1; i <= nidStatuses.size() - 1; i++) {
            Assert.assertEquals(nidStatuses.get(i).getText(), DPBIOutboundCorrespondenceConfigEventsSteps.updatedInfo.get().get("status" + (i - 1)));
            Assert.assertEquals(nidStatuses.get(i).getText(), DPBIOutboundCorrespondenceConfigEventsSteps.updatedInfo.get().get("status" + (i - 1)));
            Assert.assertEquals(nidStatuses.get(i).getText(), DPBIOutboundCorrespondenceConfigEventsSteps.updatedInfo.get().get("status" + (i - 1)));
            Assert.assertTrue(nids.get(i - 1).getText().equals(DPBIOutboundCorrespondenceConfigEventsSteps.updatedInfo.get().get("NID" + (i - 1))));
            Assert.assertTrue(nids.get(i - 1).getText().equals(DPBIOutboundCorrespondenceConfigEventsSteps.updatedInfo.get().get("NID" + (i - 1))));
            Assert.assertTrue(nids.get(i - 1).getText().equals(DPBIOutboundCorrespondenceConfigEventsSteps.updatedInfo.get().get("NID" + (i - 1))));
        }
    }

    @And("I verify Cascade Correspondence {string} {string}")
    public void iVerifyCascadeCorrespondence(String status, String reason) {
        viewOutboundCorrespondenceStepDefs.get().viewVerifyOutboundStatus(status);
        viewOutboundCorrespondenceStepDefs.get().viewVerifyOutboundStatusReason(reason);
    }

    @And("I verify Status History {string} {string}")
    public void iVerifyStatusHistory(String status, String changedBy) {
        List<WebElement> statusHistory = Driver.getDriver().findElements(By.xpath("//span[text()='HISTORY']"));
        for (int i = 0; i <= statusHistory.size() - 1; i++) {
            statusHistory.get(i).click();
            waitFor(5);
            WebElement historyStatus = Driver.getDriver().findElement(By.xpath("(//h5[text()='HISTORY']/../div/table/tbody/tr/td[1])[" + (i + 1) + "]"));
            WebElement historyDate = Driver.getDriver().findElement(By.xpath("(//h5[text()='HISTORY']/../div/table/tbody/tr/td[3])[" + (i + 1) + "]"));
            WebElement historyChangedBy = Driver.getDriver().findElement(By.xpath("(//h5[text()='HISTORY']/../div/table/tbody/tr/td[4])[" + (i + 1) + "]"));
            WebElement historyChangedOn = Driver.getDriver().findElement(By.xpath("(//h5[text()='HISTORY']/../div/table/tbody/tr/td[5])[" + (i + 1) + "]"));
            String presentTimeStamp = apitdu.getCurrentDateAndTime("MM/dd/yyyy");
            Assert.assertTrue(status.equalsIgnoreCase(historyStatus.getText()));
            Assert.assertTrue(presentTimeStamp.equalsIgnoreCase(historyDate.getText()));
            Assert.assertTrue(presentTimeStamp.equalsIgnoreCase(historyChangedOn.getText()));
            Assert.assertTrue(changedBy.equalsIgnoreCase(historyChangedBy.getText()));
        }
    }


    @Then("I should see that the tasks linked to the original Inbound Correspondence are now linked to the Rescanned Inbound Correspondence")
    public void iShouldSeeThatTheTasksLinkedToTheOriginalInboundCorrespondenceAreNowLinkedToTheRescannedInboundCorrespondence() {
        inboundStepDefs.get().verifyRescannedLinksFromOriginalDocument();
        inboundStepDefs.get().verifyCaseLinkedToInbDocumentUI();
    }

    @And("I click on the link to the task that is linked to the Inbound Document")
    public void iClickOnTheLinkToTheTaskThatIsLinkedToTheInboundDocument() {
        inboundStepDefs.get().clickFirstTask();
    }

    @And("I hide the call management tab out of the way")
    public void iHideTheCallManagementTabOutOfTheWay() {
        waitFor(3);
        Driver.getDriver().findElement(By.xpath("//*[contains(text(),'INTERACTION MANAGEMENT')]")).click();
        waitFor(2);
    }

    @And("I have a request to create an Inbound Document using the endpoint for Digital with the following values")
    public void iHaveARequestToCreateAnInboundDocumentUsingTheEndpointForDigitalWithTheFollowingValues(Map<String, String> dataTable) {
        //form version keyword validation removed per CP-29740
        inboundStepDefs.get().createMetaDataInboundDocumentDigitalRequest(dataTable);
    }

    @And("I have the request contains Meta Data Records for Document Type {string} with random data instances for the following")
    public void iHaveTheRequestContainsMetaDataRecordsForDocumentTypeWithRandomDataInstancesForTheFollowing(String type, Map<String, String> dataTable) {
        switch (type) {
            case "VACV Newborn Form":
                inboundStepDefs.get().addVACVNewbornFormMember(dataTable);
                break;
            case "maersk Case + Consumers Requiring Metadata":
                inboundStepDefs.get().addRandomConsumerMetaDataRecords(dataTable);
                break;
            default:
                Assert.fail("no matching case");
        }
    }

    @When("I send the request for the Inbound Correspondence using the endpoint for Digital")
    public void iSendTheRequestForTheInboundCorrespondenceUsingTheEndpointForDigital() {
        inboundStepDefs.get().sendRequestInboundDocumentDigitalAPI();
    }

    @And("I retrieve the Inbound Correspondence Details for document {string}")
    public void iRetrieveTheInboundCorrespondenceDetailsForDocument(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("createdFromDigital".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        retrieveInbResponse.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(cid));
    }

    @Then("I should see that the following values exist for the Inbound Document that was previously retrieved")
    public void iShouldSeeThatTheFollowingValuesExistForTheInboundDocumentThatWasPreviouslyRetrieved(Map<String, String> dataTable) {
        inboundStepDefs.get().verifyInbDocumentKeywords(retrieveInbResponse.get(), dataTable);
    }

    @And("I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document")
    public void iShouldSeeThatTheFollowingMultiInstanceKeywordRecordOccurrencesThatWerePreviouslyCreatedArePresentInTheInboundDocument(Map<String, String> dataTable) {
        for (String keywordRecords : dataTable.keySet()) {
            switch (keywordRecords) {
                case "Consumer":
                    inboundStepDefs.get().verifyConsumerKeywordRecord(retrieveInbResponse.get(), dataTable);
                    break;
                case "VACVNewborn":
                    inboundStepDefs.get().verifyNewbornMember(retrieveInbResponse.get(), dataTable);
                    break;
                case "VACVNewbornParents":
                    inboundStepDefs.get().verifyNewbornMember(retrieveInbResponse.get(), dataTable);
                    break;
                default:
                    Assert.fail("no matching case");

            }
        }
    }

    @And("I update the Inbound Correspondence {string} with the following values")
    public void iUpdateTheInboundCorrespondenceWithTheFollowingValues(String cid, Map<String, String> dataTable) {
        inboundStepDefs.get().updateInboundDocument(cid, dataTable);
    }

    @When("I click on the edit button on the Inbound Document Details Page")
    public void iClickOnTheEditButtonOnTheInboundDocumentDetailsPage() {
        inboundStepDefs.get().clickEditButton();
    }

    @And("I select {string} from the type drop down on the Inbound Document Details Page")
    public void iSelectFromTheTypeDropDownOnTheInboundDocumentDetailsPage(String type) {
        inboundStepDefs.get().selectInboundTypeUI(type);
    }

    @And("I should see a pop up message saying {string}")
    public void iShouldSeeAPopUpMessageSaying(String message) {
        waitFor(2);
        inboundStepDefs.get().verifyTypePopupMessage(message);
    }

    @And("I click on the save button to save changes for the Inbound Document")
    public void iClickOnTheSaveButtonToSaveChangesForTheInboundDocument() {
        inboundStepDefs.get().clickSaveButton();
    }

    @And("I click on the get image button on the Inbound Document Details Page")
    public void iClickOnTheGetImageButtonOnTheInboundDocumentDetailsPage() {
        inboundStepDefs.get().clickGetImageButton();
    }

    @Then("I should see the document icon on the Inbound Document Details Page")
    public void iShouldSeeTheDocumentIconOnTheInboundDocumentDetailsPage() {
        inboundStepDefs.get().verifyDocumentSetDetailsIcon();
    }

    @And("I click on the Document Set Details Tab on the Inbound Document Details Page")
    public void iClickOnTheDocumentSetDetailsTabOnTheInboundDocumentDetailsPage() {
        inboundStepDefs.get().navToDocumentSetDetailsTab();
    }

    @Then("I should see that there is a view icon for each Inbound Document for the set")
    public void iShouldSeeThatThereIsAViewIconForEachInboundDocumentForTheSet() {
        inboundStepDefs.get().verifyDocumentsInSetUI();
    }

    @Then("I should see the view Inbound Document is next to the CID")
    public void iShouldSeeTheViewInboundDocumentIsNextToTheCID() {
        inboundStepDefs.get().verifyViewIcon();
    }

    @Then("I should see the Type drop down in the Inbound Document Details page is in alphabetical order")
    public void iShouldSeeTheTypeDropDownInTheInboundDocumentDetailsPageIsInAlphabeticalOrder() {
        inboundStepDefs.get().verifyTypeDropDownValuesAlphabeticalOrder();
    }

    @Then("I should see that the Request Rescan button is available in the Inbound Correspondence Details Page")
    public void iShouldSeeThatTheRequestRescanButtonIsAvailableInTheInboundCorrespondenceDetailsPage() {
        inboundStepDefs.get().verifyRequestRescanButtonVisible();
    }

    @Then("I click on the Rescan button in the Inbound Correspondence Details Page")
    public void iClickOnTherRescanButtonInTheInboundCorrespondenceDetailsPage() {
        inboundStepDefs.get().clickRescanButton();
        inboundStepDefs.get().saveUserName();
    }

    @Then("I should see that the Cancel Rescan button is available in the Inbound Correspondence Details Page")
    public void iShouldSeeThatTheCancelRescanButtonIsAvailableInTheInboundCorrespondenceDetailsPage() {
        inboundStepDefs.get().verifyCancelRequestButton();
    }

    @And("I click on the Cancel Rescan button in the Inbound Correspondence Details Page")
    public void iClickOnTheCancelRescanButtonInTheInboundCorrespondenceDetailsPage() {
        inboundStepDefs.get().clickCancelRescanButton();
    }

    @Then("I should see the Document Set Details on the Inbound Document Details Page")
    public void iShouldSeeTheDocumentSetDetailsOnTheInboundDocumentDetailsPage() {
        inboundStepDefs.get().verifyDocumentSetDetailsTab();
    }

    @Then("I should verify all the labels on the Document Set Details tab on the Inbound Document Details Page")
    public void iShouldSeeTheFollowingLabelsOnTheDocumentSetDetailsTabOnTheInboundDocumentDetailsPage() {
        inboundStepDefs.get().verifyDocumentSetDetailsLabels();
    }

    @Then("I should verify the Document Set Details tab message says {string} on the Inbound Document Details Page")
    public void iShouldSeeTheDocumentSetDetailsTabMessageSaysOnTheInboundDocumentDetailsPage(String message) {
        inboundStepDefs.get().verifyDocumentSetDetailsMessage(message);
    }

    @Then("I should see multiple Consumers Ellipsis for Inbound Documents that have more than one Consumer Id")
    public void iShouldSeeMultipleConsumersEllipsisForInboundDocumentsThatHaveMoreThanOneConsumerId() {
        inboundStepDefs.get().verifyMultipleConsumersEllipsis();
    }

    @And("I wait for five minutes after clicking on get image button on the Inbound Document Details Page")
    public void iWaitForFiveMinutesAfterClickingOnGetImageButtonOnTheInboundDocumentDetailsPage() {
        BrowserUtils.waitFor(360);
    }

    @When("I send the request for the Inbound Correspondence in the same set as the previously created document")
    public void iSendTheRequestForTheInboundCorrespondenceInTheSameSetAsThePreviouslyCreatedDocument() {
        inboundStepDefs.get().sendDigitalSameSetInbDocument();
    }

    @And("I click on the link to the Outbound Correspondence from the Inbound Details page that was {string}")
    public void iClickOnTheLinkToTheOutboundCorrespondenceFromTheInboundDetailsPageThatWas(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            inboundStepDefs.get().clickOnPreviouslyCreatedOutboundCorrespondence();
        } else {
            inboundStepDefs.get().clickOnPreviouslyCreatedOutboundCorrespondence(cid);
        }
    }

    @Then("I should see that I have been navigated back to the Inbound Details page that was {string}")
    public void iShouldSeeThatIHaveBeenNavigatedBackToTheInboundDetailsPageThatWas(String cid) {
        inboundStepDefs.get().verifyNavigatedToInboundDetailsPage(cid);
    }

    @And("I send the request for another Inbound Correspondence using the endpoint for Digital")
    public void iSendTheRequestForAnotherInboundCorrespondenceUsingTheEndpointForDigital() {
        inboundStepDefs.get().sendAnotherInboundDocumentDigitalAPI();
    }

    @And("I should not see the {string} Inbound Correspondence in the search results")
    public void iShouldNotSeeTheInboundCorrespondenceInTheSearchResults(String cid) {
        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("second".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SecondInboundDocumentId"));
        }
        int expected = Integer.parseInt(cid);
        JsonPath results = (JsonPath) World.generalSave.get().get("searchResults");
        List<Integer> ids = results.getList("correspondences.id");
        boolean found = false;
        for (Integer id : ids) {
            if (expected == id) {
                found = true;
            }
        }
        Assert.assertFalse(found);
    }

    @Given("I search and verify the latest {string}")
    public void i_search_and_verify_the_latest(String eventType) {
        inboundStepDefs.get().verifyLatestInboundEvent(eventType);
    }

    @And("I click on the HISTORY tab of the Inbound Correspondence Details Page")
    public void iClickOnTheSTATUSHISTORYTabOfTheInboundCorrespondenceDetailsPage() {
        inboundStepDefs.get().navigateStatusHistoryTab();
    }

    @Then("I should see the view changed by should display the Connection Point user name of the user who updated the document")
    public void iShouldSeeTheViewChangedByShouldDisplayTheConnectionPointUserNameOfTheUserWhoUpdatedTheDocument() {
        inboundStepDefs.get().verifyChangedByCPUSER();
    }

    @Then("I should see the view changed by should display the raw value stored of the user not in Connection Point")
    public void iShouldSeeTheViewChangedByShouldDisplayTheRawValueStoredOfTheUserNotInConnectionPoint() {
        inboundStepDefs.get().verifyChangedByRawValue();
    }

    @Then("I should see the Updated By value on the Inbound Correspondence Details Page shows the CP username")
    public void iShouldSeeTheUpdatedByValueOnTheInboundCorrespondenceDetailsPageShowsTheCPUsername() {
        Assert.assertTrue(inboundStepDefs.get().verifyUpdatedByIsCPUserName(true));
    }

    @Then("I should see the Updated By value on the Inbound Correspondence Details Page shows the Raw Value that is stored")
    public void iShouldSeeTheUpdatedByValueOnTheInboundCorrespondenceDetailsPageShowsTheRawValueThatIsStored() {
        Assert.assertTrue(inboundStepDefs.get().verifyUpdatedByIsCPUserName(false));
    }

    @Then("I should see the edit button on the Inbound Document Details Page is available")
    public void iShouldSeeTheEditButtonOnTheInboundDocumentDetailsPageIsAvailable() {
        inboundStepDefs.get().verifyEditButtonIBDetailsPage();
    }

    @Then("I should see the cancel and save button is visible on the Inbound Document Details Page")
    public void iShouldSeeTheCancelAndSaveButtonIsVisibleOnTheInboundDocumentDetailsPage() {
        inboundStepDefs.get().verifySaveAndCancel();
    }


    @And("I click on Edit Inbound Correspondence details Page Save button")
    public void iClickOnInboundCorrespondenceDetailsPageSavebutton() {
        inboundStepDefs.get().ibcorrdetailspagesavebutton();
    }

    @And("I click on the EDIT HISTORY tab of the Inbound Correspondence Details Page")
    public void iClickOnTheEDITHISTORYTabOfTheInboundCorrespondenceDetailsPage() {
        inboundStepDefs.get().navigateEditHistoryTab();
    }

    @And("I validate the Edited On time is as per project timezone")
    public void ivalidateEditedOnasperProjecttimezone() {
        inboundStepDefs.get().editedonasperprojecttimezone();
    }


    @And("I capture date and time in required format")
    public void icapturedateandtimeinrequiredformat() {
        inboundStepDefs.get().dateandtimeinreqformat();
    }

    @And("I have a consumer on a case that will receive an Inbound Correspondence")
    public void iHaveAConsumerOnACaseThatWillReceiveAnInboundCorrespondence() {
        World.generalSave.get().put("caseConsumerId",API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId());
        APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("firstName")));
        APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("lastName")));
    }

    @And("I select the Inbound Correspondence that was {string} on the case and contact details tab")
    public void iSelectTheInboundCorrespondenceThatWasOnTheCaseAndContactDetailsTab(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        waitFor(5);
        browserUtils.get().scrollDown();
        WebElement id = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'INBOUND')]/../following-sibling::div//tbody/tr/td[contains(text(),'" + cid + "')]"));
        browserUtils.get().hover(id);
        waitFor(1);
        id.click();
        waitFor(7);
    }

    @Given("I should see a view image Icon to the right of the Unlink icon")
    public void i_should_see_a_view_image_Icon_to_the_right_of_the_Unlink_icon() {
        Assert.assertTrue(inboundStepDefs.get().isViewImageIconDisplayed());
    }

    @Given("I should not see a view image Icon to the right of the Unlink icon")
    public void i_should_not_see_a_view_image_Icon_to_the_right_of_the_Unlink_icon() {
        Assert.assertFalse(inboundStepDefs.get().isViewImageIconDisplayed());
    }

    @And("I validate Edit History tab columns names")
    public void iValidateTheEDITHISTORYTabColumnNames(List<String> colnames) {
        inboundStepDefs.get().validateEditHistoryTabColumnNames(colnames);
    }

    @And("I validate Link History column values")
    public void iValidateEditLinkHistoryColumnValues(List<String> columnnames) {
        inboundStepDefs.get().validateLinkHistoryTabColumnValues(columnnames);
    }

    @And("I validate the History Tab values for {int} row")
    public void iValidateTheHistoryTabValuesForRow(int columnOrder, List<String> columnValues) {
        LinkedList columnLinkedList = new LinkedList(columnValues);
        inboundStepDefs.get().validateEditHistoryTabColumnValues(columnOrder, columnLinkedList);
    }

    @And("I initiate and run Get Inbound Correspondence inactive Links Call for {string}")
    public JsonPath inboundCorrespondenceLinks(String ibdocid) {
        String documentId = "";
        if (ibdocid.equalsIgnoreCase("fromRequest"))
            documentId = World.generalSave.get().get("InboundDocumentId").toString();
        else if (ibdocid.equalsIgnoreCase("InboundDocument"))
            documentId = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        else
            documentId = ibdocid;

        api.setbaseUri(ConfigurationReader.getProperty("apiLinksLink"));
        api.setEndPoint("/links/inbound_correspondence/" + documentId + "?inactivelinks=true&size=5&page=0");
        api.getAPI();
        Assert.assertEquals(api.statusCode, 200, "wrong status code");
        return api.jsonPathEvaluator;
    }

    @And("I store the inactive links api call details to 2D array")
    public void icaptureInactiveLinksto2DArray(List<String> columnnames) {
        inboundStepDefs.get().captureInactiveLinksto2DArray(api.jsonPathEvaluator, columnnames);
    }

    @And("I compare the Inactive Api values to CP Link History column values")
    public void compareLinkHistoryValues() {
        inboundStepDefs.get().comparevalues();
    }

    @And("I store the inactive unlinks api call details to 2D array")
    public void icaptureInactiveUnLinksto2DArray(List<String> columnnames) {
        inboundStepDefs.get().captureInactiveUnLinksto2DArray(api.jsonPathEvaluator, columnnames);
    }

    @And("I validate reverse chronological order of Edited On column")
    public void ivalidatereversechronologicaloderofeditedcolumn() {
        inboundStepDefs.get().reversechronologicaloderofeditedcolumn();
    }

    @And("I capture the task ID from inactive Links")
    public void iCaptureTheTaskIDFromInactiveLinks() {
        inboundStepDefs.get().captureTheTaskIdFromInactiveLinks(api.jsonPathEvaluator);
    }

    @Given("I Validate Endpoint to Retrieve Inbound Correspondence Edit History to Type for {string}")
    public void i_Validate_Endpoint_to_Retrieve_Inbound_Correspondence_Edit_History_to_Type(String ibcid, Map<String, String> dataTable) {
        if (ibcid.equalsIgnoreCase("InboundDocument"))
            ibcid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));

        JSONArray jsonArr =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceEditHistoryType(ibcid);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            Assert.assertEquals(jsonObj.get("fieldLabel"), dataTable.get("fieldLabel"));
            Assert.assertTrue(jsonObj.get("editedOn") != null);
            Assert.assertTrue(jsonObj.get("editedBy") != null);
            Assert.assertEquals(jsonObj.get("updatedValue"), dataTable.get("updatedValue"));
            Assert.assertEquals(jsonObj.get("previousValue"), dataTable.get("previousValue"));
            break;
        }

    }

    @Then("I should see an array of consumerIDs in the search results, when search by {string}")
    public void i_should_see_an_array_of_consumerIDs_in_the_search_results_when_search_by(String key) {
        switch (key) {
            case "dateReceived":
                JsonPath dateSearchResult = (JsonPath) World.generalSave.get().get("searchResults");
                String CID = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
                int searchSize = dateSearchResult.getInt("correspondences.size()");
                for (int i = 0; i < searchSize; i++) {
                    if (dateSearchResult.getString("correspondences[" + i + "].id").equals(CID)) {
                        String[] actualArrayOfIds = dateSearchResult.getString("correspondences[" + i + "].consumerId").replace("[", "").replace("]", "").split(", ");
                        List<Map<String, String>> expectedListOfMaps = (List<Map<String, String>>) World.generalSave.get().get("PLANS");
                        for (int j = 0; j < expectedListOfMaps.size(); j++) {
                            Assert.assertTrue(expectedListOfMaps.get(j).get("ConsumerID").contains(actualArrayOfIds[j]));
                        }
                    }
                }
                break;
            default:
                JsonPath results = (JsonPath) World.generalSave.get().get("searchResults");
                String[] actualArrayOfIds = results.getString("correspondences[0].consumerId").replace("[", "").replace("]", "").split(", ");
                List<Map<String, String>> expectedListOfMaps = (List<Map<String, String>>) World.generalSave.get().get("PLANS");
                for (int i = 0; i < expectedListOfMaps.size(); i++) {
                    Assert.assertTrue(expectedListOfMaps.get(i).get("ConsumerID").contains(actualArrayOfIds[i]));
                }
        }
    }


    @Then("I should see that the Service Request linked to the original Inbound Correspondence are now linked to the Rescanned Inbound Correspondence")
    public void iShouldSeeThatTheServiceRequestLinkedToTheOriginalInboundCorrespondenceAreNowLinkedToTheRescannedInboundCorrespondence() {
        inboundStepDefs.get().verifyRescannedLinksFromOriginalDocument();
        inboundStepDefs.get().verifySRLinkedToInbDocumentUI();
    }


    @And("I validate Received Date is editable")
    public void i_validate_Received_Date_is_editable() {
        inboundStepDefs.get().validatereceiveddate();
    }

    @And("I validate {string} without selecting New Received Date")
    public void i_validate_saving_cancel_without_selecting_Received_Date(String actiontype) {
        inboundStepDefs.get().validatesavingwithoutselectingreceiveddate(actiontype);
    }

    @Given("I validate Received date changes persisted to OnBase for {string}")
    public void i_validate_Received_date_changes_persisted_to_OnBase_for(String cid) {
        if ("fromRequest".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("InboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        inboundStepDefs.get().validatereceiveddatepersistedtoOnBase(cid);
    }

    @Then("I update the Inbound Correspondence {string} field with value {string} via {string}")
    public void i_update_field_with_value(String fieldtype, String fieldvalue, String modeofentry) {
        inboundStepDefs.get().updateibfield(fieldtype, fieldvalue, modeofentry);
    }


    @Then("I verify MetaData Information matches with Inbound document created")
    public void iVerifyMetaDataInformationWithCreatedIBDocument() {
        inboundStepDefs.get().verifyMetaDataInformation();
    }

    @Then("I verify MetaData Records matches with Inbound document created")
    public void iVerifyMetaDataRecordsWithCreatedIBDocument() {
        inboundStepDefs.get().verifyMetaDataRecords();
    }

    @Given("I retrieve response for {string} and verify for empty strings")
    public void i_retrieve_response_verifyForEmptyStrings(String eventType) {
        JsonPath jsonPath = null;
        BrowserUtils.waitFor(15);
        switch (eventType) {
            case "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT":
                InboundCorrespondenceNoteEventStepDefs inboundCorrespondenceNoteEventStepDefs = new InboundCorrespondenceNoteEventStepDefs();
                String traceId = inboundCorrespondenceNoteEventStepDefs.getTraceId();
                jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId);
                break;
            case "INBOUND_CORRESPONDENCE_SAVE_EVENT":
                jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(APIClassUtil.traceId.get());
                break;
            case "INBOUND_CORRESPONDENCE_UPDATE_EVENT":
                // DPBIEventsSteps dpbiEventsSteps = new DPBIEventsSteps();
                //jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(save.get().get("INBOUND_CORRESPONDENCE_UPDATE_EVENT_TRACEID"));
                jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(APIClassUtil.traceId.get());
                break;
            case "INBOUND_CORRESPONDENCE_INDEXED_EVENT":
                // DPBIEventsSteps dpbiEventsSteps = new DPBIEventsSteps();
                //jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(save.get().get("INBOUND_CORRESPONDENCE_UPDATE_EVENT_TRACEID"));
                jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(APIClassUtil.traceId.get());
                break;
            default:
                Assert.fail(" UnExpected eventType");
                break;
        }
        inboundStepDefs.get().verifyEventForEmptyStrings(jsonPath, eventType);
    }


    @And("I save the Digital Correspondence Id created to Inbound Correspondence Id")
    public void i_Save_DigitalId_To_InboundCorrId() {
        if (!String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")).isEmpty()) {
            World.generalSave.get().put("InboundDocumentId", String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")));
        }
    }

    @Then("I verified that user is on {string} page title")
    public void i_verified_that_user_is_on_page(String pageTitle) {
        waitFor(5);
        System.out.println(Driver.getDriver().getTitle());
        Assert.assertTrue(Driver.getDriver().getTitle().equals(pageTitle));
    }

    @Then("I save the field data on inbound correspondence")
    public void iSaveIBCorrespondenceData() {
        Map<String, String> map = inboundStepDefs.get().getInboundCorrespondenceData();
        World.generalSave.get().put("IBData", map);
    }

    @Then("I validate that field data did not change")
    public void iValidateIBCorrespondenceDataDidNotChange() {
        waitFor(5);
        Map<String, String> actualMap = inboundStepDefs.get().getInboundCorrespondenceData();
        waitFor(3);
        inboundStepDefs.get().compareMapValues(actualMap, (Map) World.generalSave.get().get("IBData"));
    }

    @And("I click the cancel button on the Inbound Correspondence Detail Page")
    public void iClickTheCancelButtonOnTheInboundCorrDetailPage() {
        inboundStepDefs.get().clickCancelButton();
    }

    @Then("I should see a warning message allowing me to either discard or cancel changes")
    public void iShouldSeeAWarningMessageAllowingMeToEitherDiscardOrCancelChanges() {
        inboundStepDefs.get().validateWarningMessagePopUp();
    }

    @And("I have the following metaDataRecords to add to the request for document type {string}")
    public void iHaveARequestToCreateMetaDataRecWithGivenValues(String docType, Map<String, String> dataTable) {
        inboundStepDefs.get().createMetaDataRecordsForIBRequest(docType, dataTable);
    }

    @And("I have a request to send Events - VACV Translation Request from Inbound Document Request")
    public void iHaveARequestToCreateMetaDataRecWithGivenValues() {
        inboundStepDefs.get().initiateSendEventRequestWithFieldValuesFromInboundRequest();
    }

    @Then("I verify payload data matches inbound request")
    public void iVerifyPayLoadDataMatchesIBRequest() {
        inboundStepDefs.get().verifyPayLoadMetaDataMatchesIBRequest();
        inboundStepDefs.get().verifyPayLoadMetaDataRecordsMatchesIBRequest();
    }

    @When("I have a request to update Inbound Correspondence definition for type {string} and {string} with the following values")
    public void i_have_a_request_to_update_Inbound_Correspondence_definition_for_type_and_with_the_following_values(String inboundCorType, String tenant, Map<String, String> dataTable) {
        inboundStepDefs.get().createRequestToUpdateInboundDefinition(inboundCorType, tenant, dataTable);
    }

    @Then("I send a request to update the Inbound Correspondence definition")
    public void i_send_a_request_to_update_the_Inbound_Correspondence_definition() {
        inboundStepDefs.get().sendRequestToUpdateInboundDefinition();
    }

    @Then("I verify the service reject the request for type {string}")
    public void i_verify_the_service_reject_the_request_for_type(String type) {
        inboundStepDefs.get().verifyServiceIgnoreRequest(type);
    }

    @Then("I verify the Missing Item status has been updated to {string}")
    public void iVerifyTheMissingItemStatusHasBeenUpdatedTo(String status) {
        inboundStepDefs.get().verifyMissingItemStatus(status);
    }

    @Then("I should see the Inbound Document with an Invalid Document Type has been rejected")
    public void iShouldSeeTheInboundDocumentWithAnInvalidDocumentTypeHasBeenRejected() {
        JsonPath inbResponse = (JsonPath) World.generalSave.get().get("inbResponse");
        Assert.assertEquals("Failed", inbResponse.getString("status"));
        Assert.assertTrue(inbResponse.getString("message").contains("The provided DocumentType was not found"), "Expected message - \nThe provided DocumentType was not found \n was not found");
    }

    @And("I click on the link to the Service Request that is linked to the Inbound Document")
    public void iClickOnTheLinkToTheServiceRequestThatIsLinkedToTheInboundDocument() {
        inboundStepDefs.get().clickFirstSR();
    }

    @And("I should see that the {string} has been unlinked from the Inbound Document")
    public void iShouldSeeThatTheHasBeenUnlinkedFromTheInboundDocument(String link) {
        Map<String, String> caseConsumerId = new HashMap<>();
        caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        switch (link) {
            case "caseId":
                String caseId = caseConsumerId.get("caseId");
                inboundStepDefs.get().verifyCaseUnlinkedToIB(String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")), caseId);
                break;
            case "consumerId":
                String consumerId = caseConsumerId.get("consumerId");
                inboundStepDefs.get().verifyConsumerUnlinkedToIB(String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")), consumerId);
                break;
        }
    }

    @And("I should see that the {string} keyword value has been removed from the Inbound Document")
    public void iShouldSeeThatTheKeywordValueHasBeenRemovedFromTheInboundDocument(String link) {
        inboundDocumentTaskStepDefs.get().setLatestTaskId(Integer.valueOf(CRM_TaskManagementStepDef.srID.get()));
        JsonPath inbKeywords =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")));
        Map<String, Object> keywords = inbKeywords.get("inboundCorrespondence");
        boolean found = false;
        for (Map.Entry<String, Object> keyword : keywords.entrySet()) {
            if (link.equalsIgnoreCase(keyword.getKey())) {
                Assert.assertNull(keyword.getValue());
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    @And("I should see the previously linked Task is still Linked to the Inbound Document")
    public void iShouldSeeThePreviouslyLinkedTaskIsStillLinkedToTheInboundDocument() {
        inboundDocumentTaskStepDefs.get().verifyLinkInTask("INBOUND_CORRESPONDENCE", String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")));
        inboundDocumentTaskStepDefs.get().verifyLinkedToTaskUI(String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")), CRM_TaskManagementStepDef.srID.get());
    }

    @Then("I should see there is a link between Application SR {string} and Inbound Correspondence Document {string}")
    public void iShouldSeeThereIsALinkBetweenApplicationSRAndInboundCorrespondenceDocument(String applicationSR, String cid) {
        inboundStepDefs.get().verifyApplicationSRLinkedToInbDocument(applicationSR, cid);
    }

    @Then("I should see there is a link between Inbound Correspondence Document {string} and Application SR {string}")
    public void iShouldSeeThereIsALinkBetweenInboundCorrespondenceDocumentAndApplicationSR(String applicationSR, String cid) {
        Assert.assertTrue(inboundStepDefs.get().verifyInbDocmentLinkedToApplicationSR(applicationSR, cid), "Inbound Document not linked to Application SR");
    }

    @Then("I should not see there is a link between Inbound Correspondence Document {string} and Application SR {string}")
    public void iShouldNotSeeThereIsALinkBetweenInboundCorrespondenceDocumentAndApplicationSR(String applicationSR, String cid) {
        Assert.assertFalse(inboundStepDefs.get().verifyInbDocmentLinkedToApplicationSR(applicationSR, cid), "Inbound Document linked to Application SR");
    }

    @Then("I store {string} External ApplicationID and {string} Application SRID")
    public void iShouldSeeThereIsALinkBetweenInboundCorrespondenceDocumentAndApplicationSRId(String externalAppId, String applicationSRId) {
        if (externalAppId.equalsIgnoreCase("previouslyCreated"))
            externalAppId = CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId");
        if (applicationSRId.equalsIgnoreCase("previouslyCreated"))
            applicationSRId = CRM_TaskManagementStepDef.srID.get();

        World.generalSave.get().put("External Application ID", externalAppId);
        World.generalSave.get().put("Application SR ID", applicationSRId);
    }

    @Then("I validate following values unavailable in {string} dropdown in IB correspondence details page")
    public void iShouldSeeThatIHaveSelectedTheFollowingValuesForCORRESPONDENCETYPE(String fieldname, List<String> dataTable) {
        boolean status = inboundStepDefs.get().validateibfieldvalues(fieldname, dataTable);
        Assert.assertFalse(status, dataTable.toString() + "values visible in Ib type dropdown in details page");
    }

    @Then("I should see the document viewer has automatically closed")
    public void iShouldSeeTheDocumentViewerHasAutomaticallyClosed() {
        BrowserUtils.waitFor(7);
        Assert.assertEquals(Driver.getDriver().getWindowHandles().size(), Integer.parseInt(String.valueOf(World.generalSave.get().get("openWindows"))) - 1);
    }

    @And("I store the current count of windows open")
    public void iStoreTheCurrentCountOfWindowsOpen() {
        World.generalSave.get().put("openWindows", String.valueOf(Driver.getDriver().getWindowHandles().size()));
    }

    @And("I switch back to the {string} window")
    public void iSwitchBackToTheWindow(String window) {
        BrowserUtils.waitFor(7);
        Driver.getDriver().switchTo().window(String.valueOf(World.generalSave.get().get(window)));
    }

    @And("I store the handle of the main window")
    public void iStoreTheHandleOfTheMainWindow() {
        World.generalSave.get().put("mainWindow", String.valueOf(Driver.getDriver().getWindowHandle()));
    }

    @And("I click on a view icon of {string} Outbound Search Results")
    public void iClickOnAViewIconOfOutboundSearchResults(String obdoc) {
        BrowserUtils.waitFor(2);
        inboundCorrespondenceSearchStepDef.get().clickViewInbDocument(obdoc);
    }

    @Then("I should see the document viewer has opened")
    public void iShouldSeeTheDocumentViewerHasOpened() {
        Assert.assertEquals(Driver.getDriver().getWindowHandles().size(), Integer.parseInt(String.valueOf(World.generalSave.get().get("openWindows"))) + 1);
    }

    @When("I send the invalid request for the Inbound Correspondence using the endpoint for Digital")
    public void iSendTheInvalidRequestForTheInboundCorrespondenceUsingTheEndpointForDigital() {
        inboundStepDefs.get().sendIBInvalidDigitalRequest();
    }

    @And("I see that a Document has been uploaded to Onbase for the {string} Outbound Correspondence Notification")
    public void iSeeThatADocumentHasBeenUploadedToOnbaseForTheOutboundCorrespondenceNotification(String nid) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+Hooks.nameAndTags.get())));
            nid = response.getString("recipients[0].notifications[0].notificationId");
            generalSave.get().put("nid", nid);
        } else if (nid.equalsIgnoreCase("mail") || nid.equalsIgnoreCase("email") || nid.equalsIgnoreCase("text") || nid.equalsIgnoreCase("fax")) {
            JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get())));
            List<Object> channelList = response.getList("recipients[0].notifications.channelType");
            List<Object> notificationIdList = response.getList("recipients[0].notifications.notificationId");
            for (int i = 0; i < channelList.size(); i++) {
                if (channelList.get(i).toString().equalsIgnoreCase(nid)) {
                    nid = notificationIdList.get(i).toString();
                }
            }
            generalSave.get().put("nid", nid);
        }
        if (nid.equalsIgnoreCase("mail2") || nid.equalsIgnoreCase("email2") || nid.equalsIgnoreCase("text2")) {
            JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get())));
            List<Object> channelList = response.getList("recipients[1].notifications.channelType");
            List<Object> notificationIdList = response.getList("recipients[1].notifications.notificationId");
            for (int i = 0; i < channelList.size(); i++) {
                if (channelList.get(i).toString().equalsIgnoreCase(nid)) {
                    nid = notificationIdList.get(i).toString();
                }
            }
            generalSave.get().put("nid", nid);
        }
        String ecmsDocumentId =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().uploadOutboundCorrespondence(nid);
        World.generalSave.get().put("ecmsDocumentId", ecmsDocumentId);
    }

    @And("I see that a Document has been uploaded to Onbase for the {string} Outbound Correspondence Notification for {string} Channel")
    public void iSeeThatADocumentHasBeenUploadedToOnbaseForTheOutboundCorrespondenceNotificationForChannel(String nid, String channel) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get())));
            nid = response.getString("recipients[0].notifications[0].notificationId");
            generalSave.get().put("nid", nid);
        }
        String ecmsDocumentId =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().uploadOutboundCorrespondence(nid, channel);
        World.generalSave.get().put("ecmsDocumentId", ecmsDocumentId);
    }

    @Then("I should see there is a link between Task Id {string} and Inbound Correspondence Document {string} and total of {int} Links.")
    public void iShouldSeeThereIsALinkBetweenTaskIdAndInboundCorrespondenceDocumentAndTotalOfLinks(String taskId, String cid, int count) {
        Assert.assertTrue(inboundStepDefs.get().verifyInbDocmentLinkedToTask(taskId, cid, count), "Inbound Document not linked to Task");
    }

    @Then("I should not see there is a link between Task Id {string} and Inbound Correspondence Document {string} and total of {int} Links.")
    public void iShouldNotSeeThereIsALinkBetweenTaskIdAndInboundCorrespondenceDocumentAndTotalOfLinks(String taskId, String cid, int count) {
        Assert.assertFalse(inboundStepDefs.get().verifyInbDocmentLinkedToTask(taskId, cid, count), "Inbound Document not linked to Task");
    }

    @Then("I should see there is a link between Inbound Correspondence Document {string} and SR {string} total of {int} different SR Linked.")
    public void iShouldSeeThereIsALinkBetweenInboundCorrespondenceDocumentAndSRTotalOfDifferentSRLinked(String cid, String taskId, int count) {
        Assert.assertTrue(inboundStepDefs.get().verifySRLinkedToInbDocumentAndSrCountLinkedIBDocument(cid, taskId, count), "Task not linked to IB document");
    }

    @Then("I verify the {string} while the {string} is not produced")
    public void iVerifyTheWhileTheIsNotProduced(String produced, String notProduced) {
        BrowserUtils.waitFor(15);
        JsonPath jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(APIClassUtil.traceId.get());
        boolean producedFound = false;
        boolean notProducedFound = false;
        List<Map<String, Object>> events = jsonPath.getList("events");
        for (Map<String, Object> event : events) {
            if (produced.equalsIgnoreCase(String.valueOf(event.get("eventName")))) {
                producedFound = true;
            }
            if (notProduced.equalsIgnoreCase(String.valueOf(event.get("eventName")))) {
                notProducedFound = true;
            }
        }
        Assert.assertTrue(producedFound);
        Assert.assertFalse(notProducedFound);
    }

    @Then("I verify the {string} while the {string} is not produced when IB Document is updated")
    public void iVerifyTheUpdateEventsWhileTheIsNotProduced(String produced, String notProduced) {
        waitFor(5);
        JsonPath jsonPath =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(World.generalSave.get().get("postInboundDocumentTraceId").toString());
        List<String> producedFound = Arrays.asList(produced.split(","));
        List<String> notProducedFound = Arrays.asList(notProduced.split(","));
        List<Map<String, Object>> events = jsonPath.getList("events");
        for (Map<String, Object> event : events) {
            Assert.assertTrue(producedFound.contains(String.valueOf(event.get("eventName"))));
            Assert.assertFalse(notProducedFound.contains(String.valueOf(event.get("eventName"))));
        }
    }

    @And("I retrieve the Details for the {string} Inbound Correspondence from api")
    public void iRetrieveTheDetailsForTheInboundCorrespondenceFromApi(String cid) {
        if ("fromRequest".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("GETNEWRESCANINBOUNDDOCID".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID"));
        } else if ("InboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }
        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(cid);
        generalSave.get().put("InboundCidForScenario" + Hooks.nameAndTags.get(), response);
    }
}