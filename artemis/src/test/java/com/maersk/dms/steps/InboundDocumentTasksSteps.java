package com.maersk.dms.steps;

import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.World;
import com.maersk.dms.step_definitions.InboundCorrespondenceStepDefs;
import com.maersk.dms.step_definitions.InboundDocumentTaskStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.assertTrue;

public class InboundDocumentTasksSteps extends CRMUtilities implements ApiBase {
    private final ThreadLocal<InboundDocumentTaskStepDefs> inboundDocumentTaskStepDefs = ThreadLocal.withInitial(InboundDocumentTaskStepDefs::new);
    private final ThreadLocal<InboundCorrespondenceStepDefs> inboundCorrespondenceStepDefs = ThreadLocal.withInitial(InboundCorrespondenceStepDefs::new);
    public final ThreadLocal<Map<String, String>> saveEventKeyValues = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<String> eventPayload = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonPath> response = new ThreadLocal<>();

    @When("I want to edit an Inbound Correspondence Type by the name of {string}")
    public void editInboundCorrespondenceType(String typeName) {
        //get possible task type ids from "typeName" variable inbound types and save List<Integer>
        World.generalSave.get().put("inboundTypeIds", inboundDocumentTaskStepDefs.get().searchInboundType(typeName));
        //get task id for most recently created task
        World.generalSave.get().put("latestTaskId", inboundDocumentTaskStepDefs.get().checkLastTasksCreated(typeName));
        //initiate request for inbound type post request
        inboundDocumentTaskStepDefs.get().initiateUpdateRequest(typeName);
    }

    @And("I send the request to update the Inbound Correspondence Type {string} - if not already setup as intended in the request")
    public void iSendTheRequestToUpdateTheInboundCorrespondenceTypeIfNotAlreadySetupAsIntendedInTheRequest(String typeName) {
        boolean isConfiguredProperly = false;
//        boolean isConfiguredProperly = inboundDocumentTaskStepDefs.get().compareRequestToInboundNameSearchResponse();
        if (!isConfiguredProperly) {
            inboundDocumentTaskStepDefs.get().addDefinitionIdToRequest(typeName);
            inboundDocumentTaskStepDefs.get().sendUpdateTypeRequest();
        }
    }

    @And("I want to add a task rule to the Inbound Correspondence Type {string} with the following properties;")
    public void addTaskRuleToRequestPayload(String typeName, Map<String, String> dataTable) {
        //add task rule to inbound type post request
        inboundDocumentTaskStepDefs.get().addTaskRule(typeName, dataTable);
    }

    @And("I have an Inbound Correspondence Save event Request with no Case ID in addition to no Consumer ID")
    public void iHaveAnInboundCorrespondenceSaveEventRequestWithNoCaseIDInAdditionToNoConsumerID() {
        inboundDocumentTaskStepDefs.get().initiateSendEventRequest();
    }

    @When("I send the request to create the Inbound Correspondence Save Event")
    public void iSendTheRequestToCreateTheInboundCorrespondenceSaveEvent() {
        inboundDocumentTaskStepDefs.get().sendEventRequest();
    }

    @When("I send the String request to create the Inbound Correspondence Save Event")
    public void iSendTheStringRequestToCreateTheInboundCorrespondenceSaveEvent() {
        inboundDocumentTaskStepDefs.get().sendEventStringRequest();
    }

    @Then("I should that a Task has been created containing all the values sent in the request")
    public void iShouldThatATaskHasBeenCreatedContainingAllTheValuesSentInTheRequest() {
        inboundDocumentTaskStepDefs.get().verifyTaskCreated();
    }

    @Then("I should validate Task has been {string}")
    public void iShouldThatATaskHasNotBeenCreated(String taskstatus) {
        inboundDocumentTaskStepDefs.get().verifyTaskCreation(taskstatus);
    }

    @And("I have a Inbound Document that with the Inbound Document Type of {string}")
    public void iHaveAInboundDocumentThatWithTheInboundDocumentTypeOf(String inboundType) {
        inboundDocumentTaskStepDefs.get().uploadInboundDocument(inboundType);
    }

    @And("I have an Inbound Correspondence Save event Request with the following values")
    public void iHaveAnInboundCorrespondenceSaveEventRequestWithTheFollowingValues(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().initiateSendEventRequest(dataTable);
    }

    @Then("I should see that a Task has been created containing the following Links")
    public void iShouldSeeThatATaskHasBeenCreatedContainingTheFollowingValues(Map<String, String> dataTable) {
        //use all uppercase for keys | eg... "externalLinkRefType": "CASE" (which is found in task)
        inboundDocumentTaskStepDefs.get().verifyTaskLinksCreated(dataTable);
        System.out.println("finished");
    }

    @Then("I should that a Task has NOT been created")
    public void iShouldThatATaskHasNOTBeenCreated() {
        inboundDocumentTaskStepDefs.get().verifyTaskNOTCreated();
    }

    @Then("I should see that a Task has been created with the taskTypeID of {string}")
    public void iShouldSeeThatATaskHasBeenCreatedWithTheTaskTypeIDOf(String taskTypeId) {
        inboundDocumentTaskStepDefs.get().verifyTaskCreated(taskTypeId);
    }

    @Then("I should see a link to the follow entities in connection point")
    public void iShouldSeeALinkToTheFollowEntitiesInConnectionPoint(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().verifyLinksInUI();
    }

    @And("I should see that a Task has been created containing the following values")
    public void verifyTaskValues(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().verifyTaskLCreated(dataTable);
    }

    @Then("I verify the values contained in the payload of the save events")
    public void iVerifyTheValuesContainedInThePayloadOfTheSaveEvents() {
        inboundDocumentTaskStepDefs.get().verifyCreatedValuesWithEventValues();
    }

    @Then("I send the request to create the Inbound Correspondence Save Event then I should see status value is {string}")
    public void iSendTheRequestToCreateTheInboundCorrespondenceSaveEventThenIShouldSeeStatusValueIs(String status) {
        inboundDocumentTaskStepDefs.get().verifyPostRequestIsBad();
    }

    @And("I initiate post search {string} list to find the Event ID created")
    public void iInitiatePostSearchListToFindTheEventIDCreated(String eventType) {
        inboundDocumentTaskStepDefs.get().getEventIDfromSetID(eventType);
    }

    @And("I initiate post search Event list to find the Event ID created from following")
    public void iInitiatePostSearchEventListToFindTheEventIDCreatedFromFollowing(String event) {
        inboundDocumentTaskStepDefs.get().getEventIDfromSetID(event);
    }

    @And("I initiate get inbound correspondence event save event by the eventID found in list of Inbound Correspondence Save Event")
    public void iInitiateGetInboundCorrespondenceEventSaveEventByTheEventIDFoundInListOfInboundCorrespondenceSaveEvent() {
        inboundDocumentTaskStepDefs.get().findEventId();
    }

    @And("I have an Inbound Correspondence Save Event with {string} as value for the type")
    public void iHaveAnInboundCorrespondenceSaveEventWithAsValueForTheType(String typeValue) {
        inboundDocumentTaskStepDefs.get().initiateinvalidTypevalueSendRequest(typeValue);
    }

    @When("I initiate Get inbound correspondence document by {string} document Id")
    public void iInitiateGetInboundCorrespondenceDocumentByDocumentId(String docId) {
        inboundDocumentTaskStepDefs.get().getInbDocCorrespondenceByDocID(docId);
    }

    @Then("I verify the response from Get Inbound Correspondence by document Id {string} returns valid response")
    public void iVerifyTheResponseFromGetInboundCorrespondenceByDocumentIdReturnsValidResponse(String docId) {
        inboundDocumentTaskStepDefs.get().verifyInbDocResponseJsonByDocId(docId);
    }

    @Then("I verify the response from Get Inbound Correspondence by document by Random ten returns a Failed status")
    public void iVerifyTheResponseFromGetInboundCorrespondenceByDocumentByRandomTenReturnsAFailedStatus() {
        inboundDocumentTaskStepDefs.get().verifyStatusFailedInbCorrDocResponse();
    }

    @When("I send a request to update the Inbound Correspondence document without an ID")
    public void iSendARequestToUpdateTheInboundCorrespondenceDocumentWithoutAnID(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().initiateSendDocumentWithOutID(dataTable);
    }

    @Then("I should see the update failed because Id was required")
    public void iShouldSeeTheUpdateFailedBecauseIdWasRequired() {
        inboundDocumentTaskStepDefs.get().verifyStatusFailedInbCorrDocResponse();
    }

    @Then("I should see the update failed because one other field was required")
    public void iShouldSeeTheUpdateFailedBecauseOneOtherFieldWasRequired() {
        inboundDocumentTaskStepDefs.get().verifyAtleastOneOtherFieldRequired();
    }

    @When("I send a request to update the Inbound Correspondence with only an id")
    public void iSendARequestToUpdateTheInboundCorrespondenceWithOnlyAnId(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().initiateSendDocumentWithOutID(dataTable);
    }

    @When("I send a request to update the Inbound Correspondence Document")
    public void iSendARequestToUpdateTheInboundCorrespondenceDocument(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().initiateSendDocumentRequest(dataTable);
    }

    @Then("I initiate Get inbound correspondence document by {string} document Id and verify the following")
    public void iInitiateGetInboundCorrespondenceDocumentByDocumentIdAndVerifyTheFollowing(String docId, Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().getInbDocVerifyValues(docId, dataTable);
    }

    @Then("I should see the update failed because Id was required with error message")
    public void iShouldSeeTheUpdateFailedBecauseIdWasRequiredWithErrorMessage(String errorMsg) {
        inboundDocumentTaskStepDefs.get().verifyIdIsRequiredMsg(errorMsg);
    }

    @And("I update the Inbound Document {string} to {string}")
    public void iUpdateTheInboundDocumentTo(String attribute, String value) {
        JsonObject request = new JsonObject();
        request.addProperty("id", World.generalSave.get().get("InboundDocumentId").toString());
        if ("RANDOM".equalsIgnoreCase(value)) {
            String[] statuses = {"Received", "Barcode", "Standardized", "Routing", "Transmitting", "Rescan Requested"};
            int rnd = new Random().nextInt(statuses.length);
            value = statuses[rnd];
            World.generalSave.get().put("RANDOMSTATUS", value);
        }
        request.addProperty(attribute, value);
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateInboundDocument(World.generalSave.get().get("InboundDocumentId").toString(), request.toString()));
    }

    @Then("I should see the Inbound Document {string} is linked to caseId {string}")
    public void iShouldSeeTheInboundDocumentIsLinkedToCaseId(String cid, String caseId) {
        inboundDocumentTaskStepDefs.get().verifyLinkedToCaseUI(cid, caseId);
    }

    @Then("I should see the Inbound Document {string} is linked to TaskId {string}")
    public void iShouldSeeTheInboundDocumentIsLinkedToTaskId(String cid, String taskId) {
        inboundDocumentTaskStepDefs.get().verifyLinkedToTaskUI(cid, taskId);
    }

    @Then("I should see that Tasks for the following Task Type Ids have been created")
    public void iShouldSeeThatTasksForTheFollowTaskTypeIdsHaveBeenCreated(List<String> taskTypes) {
        inboundDocumentTaskStepDefs.get().verifyMultipleTaskTypesCreated(taskTypes);
    }

    @Then("I should see the Inbound Document Links component is present Inbound Correspondence Details Page")
    public void iShouldSeeTheInboundDocumentLinksComponentIsPresentInboundCorrespondenceDetailsPage() {
        inboundDocumentTaskStepDefs.get().verifyLinksComponentIsDisplayed();
    }

    @Then("I should see the Inbound Document {string} is linked to ConsumerId {string}")
    public void iShouldSeeTheInboundDocumentIsLinkedToConsumerId(String cid, String consumerId) {
        if ("random".equalsIgnoreCase(consumerId) || "previouslyCreated".equalsIgnoreCase(consumerId)) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            consumerId = caseConsumerId.get("consumerId");
        }
        inboundDocumentTaskStepDefs.get().verifyLinkedToConsumerUI(cid, consumerId);
    }

    @And("I receive a Rescanned Inbound Document with RescanOfDocumentId {string}")
    public void iReceiveARescannedInboundDocumentWithRescanOfDocumentId(String cid) {
        //Steps (Upload documents to onBase)
        Map<String, String> dataTable = new HashMap();
        if (cid.contains("CREATEDDOCUMENT")) {
            dataTable.put("type", "CREATEDDOCUMENT");
            dataTable.put("RescanOfDocumentId", cid.split("_")[0]);
            dataTable.put("ProcessType", "RESCAN");
        } else {
            dataTable.put("type", "PROCESSEDDOCUMENT");
            dataTable.put("RescanOfDocumentId", cid);
            dataTable.put("ProcessType", "RESCAN");
        }
        inboundDocumentTaskStepDefs.get().rescanInboundDocument(dataTable);
    }


    @Then("The RESCANNED AS CID field is populated and is a hyperlink that can be selected")
    public void the_RESCANNED_AS_CID_field_is_populated_and_is_a_hyperlink_that_can_be_selected() {
        // Write code here that turns the phrase above into concrete actions
        inboundDocumentTaskStepDefs.get().verifyRescannedIdAndInformation(World.generalSave.get().get("InboundDocumentId").toString(), World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
    }

    @Then("I click on the RESCANNED AS CID to navigate to rescanned document")
    public void i_click_on_the_RESCANNED_AS_CID_to_navigate_to_rescanned_document() {
        // Write code here that turns the phrase above into concrete actions
        inboundDocumentTaskStepDefs.get().clickOnRescannedCID();
    }

    @Then("The RESCANNED OF CID field is populated and is a hyperlink that can be selected")
    public void the_RESCANNED_OF_CID_field_is_populated_and_is_a_hyperlink_that_can_be_selected() {
        // Write code here that turns the phrase above into concrete actions
        inboundDocumentTaskStepDefs.get().verifyRescannedIdAndInformation(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString(), World.generalSave.get().get("InboundDocumentId").toString());
    }

    @Then("I click on the RESCANNED OF CID to navigate to rescanned document")
    public void i_click_on_the_RESCANNED_OF_CID_to_navigate_to_rescanned_document() {
        // Write code here that turns the phrase above into concrete actions
        inboundDocumentTaskStepDefs.get().clickOnCID();
    }


    @Then("Verify the RESCANNED AS CID field is populated and is a hyperlink")
    public void verify_the_RESCANNED_AS_CID_field_is_populated_and_is_a_hyperlink() {
        // Write code here that turns the phrase above into concrete actions
        inboundDocumentTaskStepDefs.get().verifyRescannedId(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
    }

    @Then("The system displays the View Inbound Correspondence Details screen for that item")
    public void the_system_displays_the_View_Inbound_Correspondence_Details_screen_for_that_item() {
        // Write code here that turns the phrase above into concrete actions
        inboundDocumentTaskStepDefs.get().verifyCid(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
    }

    @Then("The Rescan of CID field is populated and is a hyperlink that can be selected")
    public void the_Rescan_of_CID_field_is_populated_and_is_a_hyperlink_that_can_be_selected() {
        // Write code here that turns the phrase above into concrete actions
        inboundDocumentTaskStepDefs.get().verifyRescannedId(World.generalSave.get().get("InboundDocumentId").toString());
    }

    @Then("The system displays the Original View Inbound Correspondence Details screen for that item")
    public void the_system_displays_the_Original_View_Inbound_Correspondence_Details_screen_for_that_item() {
        // Write code here that turns the phrase above into concrete actions
        inboundDocumentTaskStepDefs.get().verifyCid(World.generalSave.get().get("InboundDocumentId").toString());
        inboundDocumentTaskStepDefs.get().verifyRescannedId(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
    }


    @And("I receive a Rescanned Inbound Document with the following values")
    public void iReceiveARescannedInboundDocumentWithTheFollowingValues(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().createRescannedInboundDocument(dataTable);

    }

    @And("I have an Inbound Correspondence that needs to be Rescanned with the following values")
    public void iHaveAnInboundCorrespondenceThatNeedsToBeRescannedWithTheFollowingValues(Map<String, String> dataTable) {
        World.generalSave.get().put("InboundDocumentId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createRescannedInboundDocument(dataTable));
    }

    @Then("I should see that the orignal Inbound Document has been updated with the following values")
    public void iShouldSeeThatTheOrignalInboundDocumentHasBeenUpdatedWithTheFollowingValues(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().verifyOriginalDocumentUpdated(dataTable);
    }

    @Then("I should see that the following values are showing in the Inbound Document Details page of the original document")
    public void iShouldSeeThatTheFollowingValuesAreShowingInTheInboundDocumentDetailsPageOfTheOriginalDocument(Map<String, String> dataTable) {
        inboundCorrespondenceStepDefs.get().verifyOriginalDocumentUI(dataTable);
    }

    @And("I cancel the task of task type {string}")
    public void iCancelTheTaskOfTaskType(String taskType) {
        inboundCorrespondenceStepDefs.get().cancelTask(taskType);
    }

    @Then("I should see that the Cancelled task is not linked to the Rescanned Inbound Document")
    public void iShouldSeeThatTheCancelledTaskIsNotLinkedToTheRescannedInboundDocument() {
        inboundDocumentTaskStepDefs.get().verifyTasksNotLinkedToRescannedDocument();
    }

    @And("I should see that a new task of the following task type id has been created and linked to the Rescanned Inbound Document")
    public void iShouldSeeThatANewTaskOfTheFollowingTaskTypeIdHasBeenCreatedAndLinkedToTheRescannedInboundDocument(List<String> taskTypes) {
        List<Integer> taskTypeList = new ArrayList<>();
        for (String type : taskTypes) {
            taskTypeList.add(Integer.valueOf(type));
        }
        inboundDocumentTaskStepDefs.get().verifyTaskTypesLinkedToRescannedDocument(taskTypeList);
    }

    @And("I change the status to complete of the task with task type {string}")
    public void iChangeTheStatusToCompleteOfTheTaskWithTaskType(String status) {
        inboundCorrespondenceStepDefs.get().completeTask(status);
    }

    @And("I click on the link to the task type {string}")
    public void iClickOnTheLinkToTheTaskType(String taskType) {
        inboundDocumentTaskStepDefs.get().clickTaskLinkByType(taskType);
    }

    @Then("I should see that the Cancelled task from the origin Inbound Document is not linked to the Rescanned Inbound Document")
    public void iShouldSeeThatTheCancelledTaskFromTheOriginInboundDocumentIsNotLinkedToTheRescannedInboundDocument() {
        inboundDocumentTaskStepDefs.get().verifyCancelledTasksNotLinkedRescannedDocument();
    }

    @Then("I should see that the Inbound Correspondence Save Event returned a success response")
    public void iShouldSeeThatTheInboundCorrespondenceSaveEventReturnedASuccessResponse() {
        inboundDocumentTaskStepDefs.get().verifySuccessResponse();
    }

    @When("I send the request to create the Inbound Correspondence Save Event related to SR")
    public void iSendTheRequestToCreateTheInboundCorrespondenceSaveEventRelatedToSR() {
        inboundDocumentTaskStepDefs.get().sendEventRequestForServiceRequest();
    }

    @Then("I should see the new task in the Links Component of the Inbound Document Details Page")
    public void iShouldSeeTheNewTaskInTheLinksComponentOfTheInboundDocumentDetailsPage() {
        inboundDocumentTaskStepDefs.get().verifyLinksInUI();
    }

    @Then("I should see the event from onbase has been received by Connection Point")
    public void iShouldSeeTheEventFromOnbaseHasBeenReceivedByConnectionPoint() {
        inboundDocumentTaskStepDefs.get().verifySuccessResponseNoTask();
    }

    @And("I see that a specific amount of tasks have been created")
    public void iSeeThatASpecificAmountOfTasksHaveBeenCreated() {
        World.generalSave.get().put("links", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(String.valueOf(World.generalSave.get().get("InboundDocumentId"))).getList("externalLinkDetails.content").size());
    }

    @Then("I should see the same amount of tasks are linked to the Inbound Document as before the type update")
    public void iShouldSeeTheSameAmountOfTasksAreLinkedToTheInboundDocumentAsBeforeTheTypeUpdate() {
        int before = Integer.parseInt(String.valueOf(World.generalSave.get().get("links")));
        int after = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(String.valueOf(World.generalSave.get().get("InboundDocumentId"))).getList("externalLinkDetails.content").size();
        Assert.assertEquals(after, before);
    }

    @Then("I should see the response from Connection Point is status {string} with message {string}")
    public void iShouldSeeTheResponseFromConnectionPointIsStatusWithMessage(String statusCode, String message) {
        inboundDocumentTaskStepDefs.get().verifyErrorMessage(statusCode, message);
    }

    @And("I should see the following types of links have been linked to the Inbound Correspondence that was {string}")
    public void iShouldSeeTheFollowingTypesOfLinksHaveBeenLinkedToTheInboundCorrespondenceThatWas(String cid, Map<String, String> dataTable) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("createdFromDigital".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }
        inboundDocumentTaskStepDefs.get().verifyLinksToInboundDocument(cid, dataTable);
    }

    @Then("I should see the {string} tasks on the first Inbound Correspondence received in a set is linked to the newly received to the Inbound Correspondence in the same set")
    public void iShouldSeeTheTasksOnTheFirstInboundCorrespondenceReceivedInASetIsLinkedToTheNewlyReceivedToTheInboundCorrespondenceInTheSameSet(String cid) {
        String cid2 = "";
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
            cid2 = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
            cid2 = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        inboundDocumentTaskStepDefs.get().verifySameSetTasksLinkedToNewDocument(cid, cid2);
    }

    @Then("I should see the {string} tasks on the first Inbound Correspondence received in a set is NOT linked to the newly received to the Inbound Correspondence in the same set")
    public void iShouldSeeTheTasksOnTheFirstInboundCorrespondenceReceivedInASetIsNOTLinkedToTheNewlyReceivedToTheInboundCorrespondenceInTheSameSet(String cid) {
        String cid2 = "";
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
            cid2 = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
            cid2 = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        inboundDocumentTaskStepDefs.get().verifySameSetTasksNOTLinkedToNewDocument(cid, cid2);
    }

    @Then("I should see a task of the following task types have been linked to the {string} Inbound Correspondence")
    public void iShouldSeeATaskOfTheFollowingTaskTypesHaveBeenLinkedToTheInboundCorrespondence(String cid, List<String> types) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        Assert.assertTrue(inboundDocumentTaskStepDefs.get().hasTaskTypes(cid, types));
    }

    @Then("I should see that there are no tasks linked to the {string} Inbound Correspondence")
    public void iShouldSeeThatThereAreNoTasksLinkedToTheInboundCorrespondence(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        JsonPath links =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        Assert.assertEquals(links.getString("totalElements"), "0");
    }

    @Then("I should see the bff links api returns active links for the {string} Inbound Correspondence")
    public void iShouldSeeTheBffLinksApiReturnsActiveLinksForTheInboundCorrespondence(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        }
        inboundDocumentTaskStepDefs.get().verifyActiveLinksAPI(cid);
    }

    @And("I Unlink the {string} Task from the Inbound Correspondence")
    public void iUnlinkTheTaskFromTheInboundCorrespondence(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        }
        BrowserUtils.waitFor(10);
        inboundDocumentTaskStepDefs.get().unlinkFirstRandomLinkIBDocument(cid);
    }

    @Then("I should see the bff links api returns ALL links for the {string} Inbound Correspondence")
    public void iShouldSeeTheBffLinksApiReturnsALLLinksForTheInboundCorrespondence(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        }
        inboundDocumentTaskStepDefs.get().verifyInactiveActiveLinksAPI(cid);
    }

    @And("I have request to update service request with the following values")
    public void i_have_request_to_update_service_request_with_the_following_values(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().createRequestToUpdateServiceRequest(dataTable);
    }

    @When("I send the request to update service request")
    public void i_send_the_request_to_update_service_request() {
        inboundDocumentTaskStepDefs.get().updateServiceRequest();
    }

    @And("I initiate get service request API by ID")
    public void i_initiate_get_service_request_API_by_ID() {
        inboundDocumentTaskStepDefs.get().retrieveServiceRequest();
    }

    @And("I initiate Case Correspondence API for created Consumer")
    public void i_initiate_Case_Correspondence_API_for_created_Consumer() {
        inboundDocumentTaskStepDefs.get().getCaseCorrespondenceAPI();
    }

    @And("I initiate Case Correspondence API for created Case")
    public void i_initiate_Case_Correspondence_API_for_created_Case() {
        inboundDocumentTaskStepDefs.get().getCaseCorrespondenceAPIforCase();
    }



    @And("I initiate get letter body data api by type {string}, Consumer ID and verify")
    public void i_initiate_get_letter_body_data_api_by_type_Consumer_ID_and_verify(String type, List<String> dataTable) {
        BrowserUtils.waitFor(30);
        inboundDocumentTaskStepDefs.get().retrieveLetterBodyDataByTypeAndConsumerID(type);
        inboundDocumentTaskStepDefs.get().verifyLetterBodyData(dataTable);
    }

    @And("I have a request to create a new Enrollment")
    public void i_have_a_request_to_create_a_new_Enrollment() {
        inboundDocumentTaskStepDefs.get().createRequestForEnrollment();
    }

    @And("I have a request to create a new Enrollment for {string}")
    public void i_have_a_request_to_create_a_new_Enrollment(String consumerid) {
        inboundDocumentTaskStepDefs.get().createRequestForEnrollment(consumerid);
    }

    @And("I send the request to create a new Enrollment")
    public void i_send_the_request_to_create_a_new_Enrollment() {
        inboundDocumentTaskStepDefs.get().sendEnrollmentRequest();
    }

    @And("I have request to add other Segment to enrollment with the following values")
    public void i_have_request_to_add_other_Segment_to_enrollment_with_the_following_values(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().createRequestForOtherSegment(dataTable);
    }

    @And("I send the request to add other Segment to enrollment")
    public void i_send_the_request_to_add_other_Segment_to_enrollment() {
        inboundDocumentTaskStepDefs.get().sendOtherSegmentRequest();
    }

    @And("I initiate get Other Enrollment Segment to get RCP value")
    public void i_initiate_get_Other_Enrollment_Segment_to_get_RCP_value() {
        inboundDocumentTaskStepDefs.get().getOtherEnrollmentSegmentAPI();
        World.save.get().put("rcp", String.valueOf(inboundDocumentTaskStepDefs.get().getRCPValueByOtherEnrollmentSegmentResponse()));

    }

    @When("I have a request to retrieve a Inbound Correspondence Definition by mmsCode {string}")
    public void iHaveARequestToRetrieveAInboundCorrespondenceDefinitionByMmsCode(String mmsCode) {
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundDefinitionByMmsCode(mmsCode));
    }

    @And("I verify the correspondence definition has the following values")
    public void iVerifyCorrDefinitionHasExpectedValues(Map<String, String> dataTable) {
        Map map = (Map) response.get().get("correspondenceData");
        Assert.assertEquals(dataTable.get("MMSID"), map.get("mmsId").toString());
        Assert.assertEquals(dataTable.get("CORRESPONDENCENAME"), map.get("correspondenceName").toString());
        Assert.assertEquals(dataTable.get("DESCRIPTION"), map.get("description").toString());
        if (dataTable.get("END DATE").equalsIgnoreCase("null")) {
            Assert.assertEquals(null, map.get("endDate"));
        }
        Assert.assertEquals(dataTable.get("STATEID"), map.get("stateReferenceId").toString());
        Assert.assertEquals(dataTable.get("USERMAYREQUEST"), map.get("userMayRequest").toString());
        Assert.assertEquals(dataTable.get("END REASON"), map.get("reasonForEnding").toString());
        String startDt = map.get("startDate").toString().split("T")[0];
        Assert.assertTrue(inboundDocumentTaskStepDefs.get().verifyGivenDateIsPastDate(startDt));
        String ibcorrDefId = map.get("inboundCorrespondenceDefinitionId").toString();
        inboundDocumentTaskStepDefs.get().searchInboundType(dataTable.get("IBCORRESPONDENCETYPE"));
        JsonPath response = (JsonPath) World.generalSave.get().get("inboundTypeResponse");
        Assert.assertEquals(Integer.parseInt(ibcorrDefId), response.getInt("inboundCorrespondence.inboundCorrespondenceDefinitionId"));
    }

    @And("I verify the correspondence definition has the following required Key")
    public void iVerifyCorrDefinitionHasRequiredKey(Map<String, String> dataTable) {
        Map map = (Map) response.get().get("correspondenceData");
        List correspondenceRequiredKeysList = (List) map.get("correspondenceRequiredKeys");
        if (correspondenceRequiredKeysList.size() > 0) {
            Map m = (Map) correspondenceRequiredKeysList.get(0);
            String actualReqdKey = m.get("requiredKeyValue").toString();
            Assert.assertEquals(dataTable.get("REQUIREDKEYVALUE"), actualReqdKey, " Reqd keys dont match " + actualReqdKey);
        }
    }

    @And("I verify the correspondence definition has the following Channel Values")
    public void iVerifyCorrDefinitionHasChannelValues(Map<String, String> dataTable) {
        Map map = (Map) response.get().get("correspondenceData");
        List corrList = (List) map.get("channelDefinitions");
        List langTemp = new ArrayList();
        if (corrList.size() > 0) {
            Map m = (Map) corrList.get(0);
            Assert.assertEquals(dataTable.get("CHANNELTYPE"), m.get("channelType").toString());
            Assert.assertEquals(dataTable.get("SEND IMMEDIATELY CHECKBOX"), m.get("sendImmediately").toString());
            Assert.assertEquals(dataTable.get("MANDATORY CHECKBOX"), m.get("mandatory").toString());
            Assert.assertEquals(dataTable.get("END REASON"), m.get("endReason").toString());
            String startDt = m.get("startDate").toString().split("T")[0];
            Assert.assertTrue(inboundDocumentTaskStepDefs.get().verifyGivenDateIsPastDate(startDt));
            Assert.assertEquals(dataTable.get("SENDER EMAIL"), m.get("senderEmailId").toString());
            if (dataTable.get("END DATE").equalsIgnoreCase("null")) {
                Assert.assertEquals(null, m.get("endDate"));
            }
            langTemp = (List) m.get("languageTemplate");
        }
        List<String> expectedLangTemp = Arrays.asList(dataTable.get("LANGUAGE").split(","));
        List<String> actualLangTemp = new ArrayList<>();
        for (int i = 0; i < langTemp.size(); i++) {
            Map langMap = (Map) langTemp.get(i);
            actualLangTemp.add(langMap.get("language").toString());
        }
        Assert.assertTrue(actualLangTemp.containsAll(expectedLangTemp));
    }

    @And("I verify the correspondence definition has approval Required Value")
    public void iVerifyCorrDefinitionHasApprovalReqdValues(Map<String, String> dataTable) {
        Map map = (Map) response.get().get("correspondenceData");
        Assert.assertEquals(dataTable.get("APPROVAL REQUIRED"), map.get("approvalRequired").toString());
    }

    @When("I have a request to create External task with the following values")
    public void i_have_a_request_to_create_External_task_with_the_following_values(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().createRequestForExternalTask(dataTable);
    }

    @Then("I send the request for the External task endpoint")
    public void i_send_the_request_for_the_External_task_endpoint() {
        inboundDocumentTaskStepDefs.get().sendExternalTaskRequest();
    }

    @And("I retrieve and store task rules for each inbound correspondence definition")
    public void i_retrieve_and_store_task_rules_for_each_inbound_correspondence_definition() {
        List<String> definitionsList = inboundDocumentTaskStepDefs.get().getListOfIBDefinitionNamesByProjectId();
        inboundDocumentTaskStepDefs.get().getIBTaskRulesForGivenDefinition(definitionsList);
    }

    @Then("I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name")
    public void i_verify_the_response_from_Get_Inbound_Correspondence_definition_by_project_Id_returns_task_rules_and_task_type_name() {
        inboundDocumentTaskStepDefs.get().verifyGetIBDefinitionByProjectIdResponse();
    }

    @And("I validate get task request API for {string} status")
    public void i_initiate_get_task_request_API_by_ID(String status) {
        inboundDocumentTaskStepDefs.get().retrieveTaskRequest(status);
    }

    @And("I navigate to {string} created task details page")
    public void i_navigate_to_task_deatisl_page(String tasktype) {
        String taskid = "";
        if (tasktype.equalsIgnoreCase("previouslycreatedtask"))
            taskid = APITaskManagementController.taskId.get() + "";
        inboundDocumentTaskStepDefs.get().navigatetotaskdetailspage(taskid);
    }

    @When("I have request to link {string} Application to the multiple cases {int}")
    public void i_have_request_to_link_Application_to_the_multiple_cases(String createdApplication, Integer casesCount) {
        inboundDocumentTaskStepDefs.get().createRequestToLinkApplicationToCases(createdApplication, casesCount);
    }

    @Then("I send a request to link created Application to the cases")
    public void i_send_a_request_to_link_created_Application_to_the_cases() {
        inboundDocumentTaskStepDefs.get().sendRequestToLinkApplicationToCases();
    }

    @Given("I verify link from new Task to Case\\(s) is created")
    public void i_verify_link_from_new_Task_to_Case_s_is_created() {
        inboundDocumentTaskStepDefs.get().verifyLinkFromTaskToCases();
    }


    @Then("I should see the {string} has been linked to the Inbound Correspondence")
    public void iShouldSeeTheHasBeenLinkedToTheInboundCorrespondence(String linkedItem) {
        String missingItemId = String.valueOf(World.generalSave.get().get("MissingInformationItemId"));
        String srId = CRM_TaskManagementStepDef.srID.get();
        World.generalSave.get().put("firstTaskId", srId);
        switch (linkedItem) {
            case "MissingInformationItemId":
                inboundDocumentTaskStepDefs.get().verifyMissingInfoItemLinked(missingItemId);
                break;
            case "Service Request":
                inboundDocumentTaskStepDefs.get().verifyServiceRequestLinked(srId);
                break;
            default:
                Assert.fail("feature parameter not matching any case");
        }
    }

    @And("I close the Service Request of task type {string}")
    public void iCloseTheServiceRequestOfTaskType(String type) {
        inboundCorrespondenceStepDefs.get().closeServiceRequest(type);
    }

    @Then("I should see the {string} has been linked to the Inbound Correspondence with the same set id")
    public void iShouldSeeTheHasBeenLinkedToTheInboundCorrespondenceWithTheSameSetId(String linkedItem) {
        World.generalSave.get().put("InboundDocumentIdDigital", String.valueOf(World.generalSave.get().get("SameSetInboundDocument")));
        iShouldSeeTheHasBeenLinkedToTheInboundCorrespondence(linkedItem);
    }

    @Then("I should see the {string} is linked to the same {string}")
    public void iShouldSeeTheIsLinkedToTheSame(String id1, String id2) {
        switch (id2) {
            case "MissingInformationItemId":
                String missingItemId = String.valueOf(World.generalSave.get().get("MissingInformationItemId"));
                inboundDocumentTaskStepDefs.get().verifyMissingInfoItemLinked(missingItemId);
                break;
            case "Service Request":
                String srId = String.valueOf(World.generalSave.get().get("firstTaskId"));
                inboundDocumentTaskStepDefs.get().verifySameServiceRequestLinked(srId);
                break;
            case "Task":
                String srId2 = String.valueOf(World.generalSave.get().get("firstTaskId"));
                inboundDocumentTaskStepDefs.get().verifySameTasktLinked(srId2);
                break;
            case "Case From Inbound Correspondence":
                Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                String caseid = caseConsumerId.get("caseId");
                inboundDocumentTaskStepDefs.get().verifySameCaseFronIBLinkedToSR(caseid);
                break;
            case "Case to Service Request":
                Map<String, String> caseConsumerId2 = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                String caseid2 = caseConsumerId2.get("caseId");
                inboundDocumentTaskStepDefs.get().verifySameCaseFronIBLinkedToSR(caseid2, id1);
                break;
            default:
                Assert.fail("feature parameter not matching any case");
        }
    }

    @Then("I will get {string} ID from the application links response")
    public void i_will_get_ID_with_type_from_the_response(String name) {
        inboundDocumentTaskStepDefs.get().i_will_get_ID_from_the_ATS_response(name);
    }

    @Then("I should see the {string} has been linked to the {string} Inbound Correspondence")
    public void iShouldSeeTheHasBeenLinkedToTheInboundCorrespondence(String arg0, String arg1) {
        inboundDocumentTaskStepDefs.get().verifyServiceRequestLinked(arg0, arg1);
    }
    @Then("I should see the Inbound Document {string} is linked to Application SR {string}")
    public void iShouldSeeTheInboundDocumentIsLinkedToApplicationSRId(String cid, String appsrId) {
        Assert.assertTrue(inboundDocumentTaskStepDefs.get().verifyLinkedToApplicationSRUI(cid, appsrId),"Inbound Document not linked to Application SR");
    }

    @When("I have a request to verify duplicate task type with the following values")
    public void i_have_a_request_to_verify_duplicate_task_with_the_following_values(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().verifyDuplicateTaskTypePayload(dataTable);
    }

    @Then("I send the request for the duplicate task endpoint")
    public void i_send_the_request_for_the_duplicate_task_endpoint() {
        inboundDocumentTaskStepDefs.get().sendDuplicateTaskRequest();
    }

    @Then("I verify {string} duplicate task response for {string}")
    public void i_verify_duplicate_response(String responseType,String typeDuplicate) {
        inboundDocumentTaskStepDefs.get().verifyDuplicateTaskResponse(responseType,typeDuplicate);
    }

    @Then("I verify error message for {string} with {string} access")
    public void i_verify_duplicate_response_for_external_task(String type_link,String restricted_or_allowed) {
        inboundDocumentTaskStepDefs.get().verifyDuplicateTaskResponseForExternalTaskCreation(type_link,restricted_or_allowed);
    }
    @Then("I update task status to {string}")
    public void iUpdateTaskStatusTo(String taskStatus) {
        inboundDocumentTaskStepDefs.get().updateTaskStatus(taskStatus);
    }

    @Then("I verify task status successfully updated to {string}")
    public void iVerifyTaskStatusSuccessfullyUpdatedTo(String taskStatus) {
        inboundDocumentTaskStepDefs.get().verifyTaskUpdated(taskStatus);
    }

}
