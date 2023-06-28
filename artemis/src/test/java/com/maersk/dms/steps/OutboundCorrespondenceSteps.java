package com.maersk.dms.steps;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.pages.crm.CRMApplicationTrackingPage;
import com.maersk.crm.pages.crm.CRMContactHistoryPage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.pages.crm.CRMTaskEditHistoryPage;
import com.maersk.crm.step_definitions.CRM_ContactRecordUIStepDef;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import com.maersk.dms.pages.ViewOutboundCorrespondenceDetailsPage;
import com.maersk.dms.step_definitions.*;
import com.maersk.dms.utilities.PdfUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import com.sun.mail.imap.IMAPMessage;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

import javax.mail.Message;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.maersk.crm.utilities.World.*;
import static com.maersk.dms.steps.OutboundCorrespondenceGlobalSearch.currentWindowHandle;
import static org.testng.Assert.assertEquals;

public class OutboundCorrespondenceSteps extends CRMUtilities implements ApiBase {
    private final ThreadLocal<OutboundCorrespondenceStepDefs> stepDefs = ThreadLocal.withInitial(OutboundCorrespondenceStepDefs::new);
    private OutboundCorrespondenceRequestsStepDefs outboundCorrespondenceRequestsStepDefs = new OutboundCorrespondenceRequestsStepDefs();
    private CreateOutboundCorrespondencePage createOutboundCorrespondencePage = new CreateOutboundCorrespondencePage();
    private ViewOutboundCorrespondenceDetailsPage viewOutboundCorrespondenceDetailsPage = new ViewOutboundCorrespondenceDetailsPage();
    private CreateOutboundCorrespondenceRequestStepDefs createOutboundCorrespondenceRequestStepDefs = new CreateOutboundCorrespondenceRequestStepDefs();
    private ViewOutboundCorrespondenceStepDefs viewOutboundCorrespondenceStepDefs = new ViewOutboundCorrespondenceStepDefs();
    private final ThreadLocal<String> language = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> request = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> notificationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> template = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Object> response = new ThreadLocal<>();
    private final ThreadLocal<OutboundCorrespondenceGlobalSearchStepDefs> outboundCorrespondenceGlobalSearchStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceGlobalSearchStepDefs::new);
    private final ThreadLocal<Map<String, String>> randomCaseId = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<Map<String, List<String>>> beforeUpdateStatusMap = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<Map<String, List<String>>> afterUpdateStatusMap = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<Map<String, List<String>>> updatedNidStatusMap = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<InboundDocumentTaskStepDefs> inboundDocumentTaskStepDefs = ThreadLocal.withInitial(InboundDocumentTaskStepDefs::new);

    private CRMContactHistoryPage crmContactHistoryPage = new CRMContactHistoryPage();
    private CRMDemographicContactInfoPage crmDemographicContactInfoPage = new CRMDemographicContactInfoPage();
    private CRMApplicationTrackingPage crmApplicationTrackingPage = new CRMApplicationTrackingPage();
    private CRMTaskEditHistoryPage crmTaskEditHistoryPage = new CRMTaskEditHistoryPage();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);


    @Given("I have specified the following values in the request for an Outbound Correspondence")
    public void setRequestValuesOutboundCorrespondenceStep(Map<String, String> dataTable) {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().setEndPoint();
        stepDefs.get().startJsonFromCorrespondenceFile();
        stepDefs.get().addDataTableValuesToJson(dataTable);
        stepDefs.get().addProjectId(ConfigurationReader.getProperty("projectName"));
        if (!dataTable.containsKey("correspondenceDefinitionMMSCode")) {
            stepDefs.get().addMMSCode();
        }
//        stepDefs.get().addChannelDefIdForNotification(0);

//        stepDefs.get().setEndPoint("/correspondence");
    }

    @When("I send the request for an Outbound Correspondence to the service")
    public void sendOutboundCorrespondenceRequestStep() {
        stepDefs.get().sendPostRequest();
        stepDefs.get().saveCorrespondenceId();
        //stepDefs.get().saveDateTime(); not implemented in payload
    }

    @Then("I should not get a response indicating it was a bad request")
    public void verifyResponseIsNotBadRequestStep() {
        Assert.assertNotEquals(400, API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.statusCode());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.statusCode(), 201);
    }

    @Given("I have specified more than one Consumer ID in a request for an Outbound Correspondence")
    public void addMoreThanOneConsumerIDStep() {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().startJsonFromCorrespondenceFile("dms/correspondence2.json");
//        stepDefs.get().addMultipleConsumerIDSToJson();
        stepDefs.get().setEndPoint();
        stepDefs.get().addProjectId(ConfigurationReader.getProperty("projectName"));
//        stepDefs.get().addMMSCode();
    }

    @Given("I have specified more than one Notification in a request for an Outbound Correspondence")
    public void addMoreThanOneNotificationStep() {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().startJsonFromCorrespondenceFile("dms/correspondence3.json");
        stepDefs.get().addMMSCode();
        stepDefs.get().addRequiredKeys(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());
//        stepDefs.get().addMoreThanOneFaxNotification(2);
//        stepDefs.get().setEndPoint("correspondence");
    }

    @Given("I have specified the following types of Notification Channels in the request for an Outbound Correspondence")
    public void addChannelsToNotificationStep(List<String> channels) {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().startJsonFromCorrespondenceFile("dms/correspondence3.json");
        stepDefs.get().addRequiredKeys(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());
//        stepDefs.get().addChannelsToNotificaitonToJson(channels);
//        stepDefs.get().setEndPoint("correspondence");
    }

    @Given("I have a valid Outbound Correspondence request")
    public void createValidRandomOutboundCorrespondenceRequestStep() {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().setEndPoint();
        stepDefs.get().startJsonFromCorrespondenceFile();
        stepDefs.get().addProjectId(ConfigurationReader.getProperty("projectName"));
        stepDefs.get().addMMSCode();
        stepDefs.get().addRequiredKeys(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());
    }

    @When("I retrieve the Outbound Correspondence by Correspondence ID")
    public void getOutboundCorrespondenceRequestbyIdStep() {
        stepDefs.get().setEndPoint("getCorrespondence");
        stepDefs.get().sendGetRequest();
//        stepDefs.get().saveCorrespondenceId();
//        apiAutoUtilities.getCorrespondence(Integer.parseInt(World.getWorld().outboundCorrespondenceBean.get().correspondenceId));
    }

    @Then("I should see a Correspondence ID is generated in the response")
    public void verifyCorrespondenceIdInResponseStep() {
        stepDefs.get().verifyCorrespondenceIdInResponse();
    }

    @Then("I should see a success status in the response")
    public void verifySuccessStatusInResponseStep() {
        stepDefs.get().verifySuccessStatusInResponse();
    }

    @Then("I should see a Correspondence is created in response")
    public void verifyCorrCreatedInResponse() {
        Assert.assertFalse(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString().isEmpty());
    }

    @Then("I should see a date and time was stored for the Outbound Correspondence Request")
    public void verifyDateTimeStoredStep() {
        stepDefs.get().verifyDateTimeStored();
    }

    @Then("I should see the status of the Outbound Correspondence Request")
    public void verifyStatusInResponseStep() {
        stepDefs.get().verifyStatusInResponse();
    }

    @Given("I have a valid Outbound Correspondence request with the following values;")
    public void addValuesOutboundCorrespondenceStep(Map<String, String> dataTable) {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().startJsonFromCorrespondenceFile();
//        stepDefs.get().addDataTableValuesToJson(dataTable);
        stepDefs.get().saveDataTableValuesToWorld(dataTable);
        stepDefs.get().setEndPoint("correspondence");
    }

    @Then("I should see the following values of the Outbound Correspondence Get request as were previously sent")
    public void verifyOutboundCorrespondenceValuesStep(Map<String, String> dataTable) {
        stepDefs.get().verifyOutboundCorrespondenceValues(dataTable);
    }

    @And("that Outbound Correspondence request contains a Notification with the following values;")
    public void addNotificationValuesToOutboundCorrespondenceStep(Map<String, String> dataTable) {
//        stepDefs.get().addNotificationValuesToOutboundCorrespondence(dataTable);
    }

    @Then("I should see the following Notification values of the Outbound Correspondence request has the same values as were previously sent")
    public void verifyNotificationValuesStoredStep(Map<String, String> dataTable) {
        stepDefs.get().verifyNotificationValuesStored(dataTable);
    }

    @Given("I have an Outbound Correspondence request without a Correspondence Type")
    public void outboundCorrespondenceNoCorrespondenceTypeStep() {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().setEndPoint();
        stepDefs.get().startJsonFromCorrespondenceFile("dms/InvalidCorrespondence.json");
//        stepDefs.get().addDataTableValuesToJson(dataTable);
        stepDefs.get().addProjectId(ConfigurationReader.getProperty("projectName"));
//        stepDefs.get().addChannelDefIdForNotification(0);

//        stepDefs.get().setEndPoint("/correspondence");
    }

    @Then("I should see failed status and the reason for the failure in the response")
    public void verifyBadRequestStep() {
        stepDefs.get().verifyBadRequest();
    }

    @Then("I should see failed status and the reason for the failure as follows")
    public void verifyBadRequestDetails(Map<String, String> map) {
        stepDefs.get().verifyBadRequestDetails(map);
    }

    @Given("I have an Outbound Correspondence request without a Language")
    public void outboundCorrespondenceNoLanguageStep() {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().startJsonFromCorrespondenceFile("crm/contactrecords/Blank.json");
        stepDefs.get().noLanguageFromJson();
        stepDefs.get().setEndPoint("correspondence");
    }

    @When("I send the request for an Outbound Correspondence to the service with no correspondence type")
    public void iSendTheRequestForAnOutboundCorrespondenceToTheServiceWithNoCorrespondenceType() {
        stepDefs.get().sendPostRequest();
    }


    @And("I initate Get letter data by the retrieved NID")
    public void iInitateGetLetterDataByTheRetrievedNID() {
        stepDefs.get().getLetterDataByNId();
    }

    @And("I initiate post outbound correspondence with one recipient")
    public void iInitiatePostOutboundCorrespondenceWithOneRecipient() {
        stepDefs.get().sendoutboundWithOnerecipient();
        stepDefs.get().saveCorrespondenceId();
    }

    @When("I initiate Get Correspondence by retrieved Correspondence ID")
    public void iInitiateGetCorrespondenceByRetrievedCorrespondenceID() {
        stepDefs.get().getCorrespondenceWithID();
    }

    @And("I verify body is created in the response of Get Letter Data")
    public void iVerifyBodyIsCreatedInTheResponseOfGetLetterData() {
        stepDefs.get().verifyBodyInLetterData();
    }

    @And("I initiate post search {string} list to find the Event ID created with Correspondence Id")
    public void iInitiatePostSearchListToFindTheEventIDCreatedWithCorrespondenceId(String eventType) {
        stepDefs.get().getEventIDfromCorrespondenceID(eventType);
    }

    @And("I initiate correspondence save event by the eventID found in list of Inbound Correspondence Save Event")
    public void iInitiateCorrespondenceSaveEventByTheEventIDFoundInListOfInboundCorrespondenceSaveEvent() {
        stepDefs.get().findEventId();
    }

    @And("I verify created Correspondence Id matches the Correspondence Id in the Letter Data Save Event")
    public void iVerifyCreatedCorrespondenceIdMatchesTheCorrespondenceIdInTheLetterDataSaveEvent() {
        stepDefs.get().verifyCreatedCorrIdWithEventId();
    }

    @And("I initiate post outbound correspondence with no recipient")
    public void iInitiatePostOutboundCorrespondenceWithNoRecipient() {
        stepDefs.get().sendoutboundWithNoRecipient();
    }

    @Given("I have an existing Outbound Correspondence with regarding values consisting of the following")
    public void iHaveAnOutboundCorrespondenceWithRegardingValuesConsistingOfTheFollowing(Map<String, String> dataTable) {
        Map<String, String> caseConsumerId = null;
        Map<String, String> consumerOnlyId = null;
        Map<String, String> applicationOnlyId = null;
        String type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType();
        JsonObject bodyData = new JsonObject();

        JsonArray exlinks = new JsonArray();

        boolean consumerOnly = false, applicationOnly = false;
        String taskid = "";
        for (String data : dataTable.keySet()) {
            switch (BrowserUtils.filterForLettersOnly(data)) {
                // switch (data) {
                case "CaseId":
                    if ("Random".equalsIgnoreCase(dataTable.get(data))) {
                        caseConsumerId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId();
                        World.generalSave.get().put("caseConsumerId", caseConsumerId);
                        World.generalSave.get().put("caseId", caseConsumerId);

                    } else if ("previouslyCreated".equalsIgnoreCase(dataTable.get("CaseId"))) {
                        caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        World.generalSave.get().put("caseConsumerId", caseConsumerId);
                        World.generalSave.get().put("caseId", caseConsumerId);
                    } else if ("CaseOnly".equalsIgnoreCase(dataTable.get(data))) {
                        caseConsumerId = new HashedMap();
                        Map<String, String> caseConsumerIds = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        caseConsumerId.put("CaseId", caseConsumerIds.get("caseId"));
                        World.generalSave.get().put("caseConsumerId", caseConsumerId);
                    } else {
                        caseConsumerId = new HashedMap();
                        caseConsumerId.put("CaseId", dataTable.get(data));
                        caseConsumerId.put("ConsumerId", dataTable.get("ConsumerId"));
                    }
                    break;
                case "ConsumerId":
                    if ("ConsumerOnly".equalsIgnoreCase(dataTable.get(data))) {
                        consumerOnlyId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId();
                        consumerOnly = true;
                        World.generalSave.get().put("ConsumerOnly", consumerOnlyId);
                    } else if ("INEBConsumerOnly".equalsIgnoreCase(dataTable.get(data)) || "DCEBConsumerOnly".equalsIgnoreCase(dataTable.get(data))) {
                        consumerOnlyId = new HashedMap();
                        Map<String, String> caseConsumerIds = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        consumerOnlyId.put("ConsumerId", caseConsumerIds.get("consumerId"));
                        World.generalSave.get().put("ConsumerOnly", consumerOnlyId);
                        consumerOnly = true;
                    }
                    break;
                case "ApplicationId":
                    applicationOnly = true;
                    applicationOnlyId = new HashedMap();
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get(data))) {
                        applicationOnlyId.put("applicationId", World.save.get().get("appID"));
                    } else {
                        applicationOnlyId.put("applicationId", dataTable.get(data));
                    }
                    World.generalSave.get().put("ApplicationOnly", applicationOnlyId);
                    break;
                case "type":
                    if ("CCONLY".equalsIgnoreCase(dataTable.get("type")) || "CCSONLY".equalsIgnoreCase(dataTable.get("type")) || "CONONLY".equalsIgnoreCase(dataTable.get("type")) || "CAONLY".equalsIgnoreCase(dataTable.get("type")) || "AppId".equalsIgnoreCase(dataTable.get("type")) || "NOKEYS".equalsIgnoreCase(dataTable.get("type")) || "formTest".equalsIgnoreCase(dataTable.get("type"))) {
                        type = dataTable.get("type");
                    } else
                        type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getValidTypeBasedOnAnchor(dataTable.keySet());
                    break;
                case "bodyData":
                    if ("Random".equalsIgnoreCase(dataTable.get("bodyData"))) {
                        Map<String, String> del = new HashedMap();
                        String key = RandomStringUtils.random(5, true, false);
                        String value = RandomStringUtils.random(5, true, false);
                        del.put(key, value);
                        bodyData.addProperty(key, value);
                        World.generalSave.get().put("bodyData", del);
                    } else {
                        String[] body = dataTable.get("bodyData").split(":");
                        bodyData.addProperty(body[0], body[1]);
                        World.generalSave.get().put("bodyData", bodyData);
                    }
                    break;
                case "taskId":
                    if ("previouslycreatedtaskId".equalsIgnoreCase(dataTable.get("taskId"))) {
                        taskid = CRM_TaskManagementStepDef.srID.get();
                    } else if ("previouslycreatedServicetaskId".equalsIgnoreCase(dataTable.get("taskId"))) {
                        taskid = CRM_TaskManagementStepDef.taskId.get();
                    }
                    break;
                case "externallink":
                    JsonObject externallinks = new JsonObject();
                    String extlinks[] = dataTable.get(data).split(",");

                    if (extlinks[0].equalsIgnoreCase("empty"))
                        externallinks.addProperty("externalRefType", "");
                    else
                        externallinks.addProperty("externalRefType", extlinks[0]);
                    if (extlinks[1].equalsIgnoreCase("empty"))
                        externallinks.addProperty("externalRefId", "");
                    else if (extlinks[1].equalsIgnoreCase("random")) {
                        World.generalSave.get().put("externalRefId", ApiTestDataUtil.getApiTestDataUtilThreadLocal().getRandomNumber(5).randomNumber);
                        externallinks.addProperty("externalRefId", World.generalSave.get().get("externalRefId").toString());
                    } else if (extlinks[1].equalsIgnoreCase("CRMTaskId")) {
                        World.generalSave.get().put("externalRefId", CRM_TaskManagementStepDef.taskId.get());
                        externallinks.addProperty("externalRefId", CRM_TaskManagementStepDef.taskId.get());
                    } else
                        externallinks.addProperty("externalRefId", extlinks[1]);
                    exlinks.add(externallinks);
                    break;
                default:
                    Assert.fail("no matching cases");
            }
        }
        if (applicationOnly) {
            assert applicationOnlyId != null;
            JsonPath resp;
            resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, applicationOnlyId, bodyData);
            System.out.println("\n -------- Response ----------- \n");
            resp.prettyPrint();
            World.generalSave.get().put("OutboundCorrespondenceResponse", resp);
            World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), resp.get("data.id").toString());

        } else if (consumerOnly) {
            assert consumerOnlyId != null;
            JsonPath resp;
            if (exlinks.size() != 0)
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, caseConsumerId, bodyData, taskid, exlinks);
            else if (taskid.isEmpty())
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, consumerOnlyId, bodyData);
            else
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, consumerOnlyId, bodyData, taskid);
            System.out.println("\n -------- Response ----------- \n");
            resp.prettyPrint();
            World.generalSave.get().put("OutboundCorrespondenceResponse", resp);
            World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), resp.get("data.id").toString());

        } else {
            assert caseConsumerId != null;
            JsonPath resp;
            if (exlinks.size() != 0)
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, caseConsumerId, bodyData, taskid, exlinks);
            else if (taskid.isEmpty())
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, caseConsumerId, bodyData);
            else
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, caseConsumerId, bodyData, taskid);
            System.out.println("\n -------- Response ----------- \n");
            resp.prettyPrint();
            World.generalSave.get().put("OutboundCorrespondenceResponse", resp);
            World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), resp.get("data.id").toString());
        }
    }

    @And("I create Outbound Correspondence with regarding values consisting of the following")
    public void iCreateOutboundCorrespondenceWithRegardingValuesConsistingOfTheFollowing(Map<String, String> dataTable) {
        Map<String, String> caseConsumerId = null;
        Map<String, String> consumerOnlyId = null;
        Map<String, String> applicationOnlyId = null;
        String type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType();
        String header_forceCreateIndicatorParameter = null;
        JsonObject bodyData = new JsonObject();
        boolean consumerOnly = false, applicationOnly = false;
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "CaseId":
                    if ("Same".equalsIgnoreCase(dataTable.get(data))) {
                        caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");

                    } else if ("Different".equalsIgnoreCase(dataTable.get(data))) {
                        caseConsumerId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId();
                        World.generalSave.get().put("newCaseConsumerId", caseConsumerId);
                    } else if ("CaseOnly".equalsIgnoreCase(dataTable.get(data))) {
                        Map<String, String> caseConsumerIds = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId();
                        caseConsumerId = new HashedMap();
                        caseConsumerId.put("CaseId", caseConsumerIds.get("caseId"));
                        World.generalSave.get().put("caseConsumerId", caseConsumerId);
                    }
                    break;
                case "ConsumerId":
                    if ("ConsumerOnly".equalsIgnoreCase(dataTable.get(data))) {
                        consumerOnlyId = (Map<String, String>) World.generalSave.get().get("ConsumerOnly");
                        consumerOnly = true;
                    } else if ("Different".equalsIgnoreCase(dataTable.get(data))) {
                        caseConsumerId.put("consumerId1", RandomStringUtils.random(2, false, true));
                        caseConsumerId.put("consumerId2", RandomStringUtils.random(2, false, true));
                    }
                    break;
                case "ApplicationId":
                    if ("same".equalsIgnoreCase(dataTable.get(data))) {
                        applicationOnlyId = (Map<String, String>) World.generalSave.get().get("ApplicationOnly");
                    } else if ("Different".equalsIgnoreCase(dataTable.get(data))) {
                        applicationOnlyId.put("applicationId", RandomStringUtils.random(2, false, true));
                    }
                    applicationOnly = true;
                    break;
                case "type":
                    if (dataTable.get(data).equalsIgnoreCase("Different"))
                        type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getValidTypeBasedOnAnchor(dataTable.keySet());
                    else
                        type = dataTable.get(data);
                    break;
                case "bodyData":
                    if ("Same".equalsIgnoreCase(dataTable.get("bodyData"))) {
                        Map<String, String> del = (Map<String, String>) World.generalSave.get().get("bodyData");
                        for (Map.Entry<String, String> bd : del.entrySet()) {
                            bodyData.addProperty(String.valueOf(bd.getKey()), String.valueOf(bd.getValue()));
                        }
                    } else if ("Different".equalsIgnoreCase(dataTable.get("bodyData"))) {
                        Map<String, String> del = new HashedMap();
                        String key = RandomStringUtils.random(5, true, false);
                        String value = RandomStringUtils.random(5, true, false);
                        del.put(key, value);
                        bodyData.addProperty(key, value);
                        World.generalSave.get().put("bodyData", bodyData);
                    }
                    break;
                case "Force-Create-Indicator":
                    header_forceCreateIndicatorParameter = dataTable.get("Force-Create-Indicator");
                    break;
                default:
                    Assert.fail("no matching cases");
            }
        }
        if (header_forceCreateIndicatorParameter != null) {
            JsonPath resp;
            if (applicationOnlyId != null)
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(header_forceCreateIndicatorParameter, type, applicationOnlyId, bodyData);
            else if (consumerOnlyId != null)
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(header_forceCreateIndicatorParameter, type, consumerOnlyId, bodyData);
            else
                resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(header_forceCreateIndicatorParameter, type, caseConsumerId, bodyData);
            System.out.println("\n -------- Response ----------- \n");
            resp.prettyPrint();
            World.generalSave.get().put("OutboundCorrespondenceResponse", resp);
        } else if (applicationOnly) {
            assert applicationOnlyId != null;
            JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, applicationOnlyId, bodyData);
            System.out.println("\n -------- Response ----------- \n");
            resp.prettyPrint();
            World.generalSave.get().put("OutboundCorrespondenceResponse", resp);

        } else if (consumerOnly) {
            assert consumerOnlyId != null;
            JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, consumerOnlyId, bodyData);
            System.out.println("\n -------- Response ----------- \n");
            resp.prettyPrint();
            World.generalSave.get().put("OutboundCorrespondenceResponse", resp);

        } else {
            assert caseConsumerId != null;
            JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, caseConsumerId, bodyData);
            System.out.println("\n -------- Response ----------- \n");
            resp.prettyPrint();
            World.generalSave.get().put("OutboundCorrespondenceResponse", resp);
        }


    }

    @Then("I should see the request to create an Outbound Correspondence was ignored")
    public void iShouldSeeTheRequestToCreateAnOutboundCorrespondenceWasIgnored() {
        JsonPath response = (JsonPath) World.generalSave.get().get("OutboundCorrespondenceResponse");
        Assert.assertEquals(response.get("errors[0].message"), "Request is duplicate and is ignored");
    }

    @Then("I should see the warning message while creating an Outbound Correspondence with invalid header")
    public void iShouldSeeThewarningmessage() {
        JsonPath response = (JsonPath) World.generalSave.get().get("OutboundCorrespondenceResponse");
        Assert.assertEquals(response.get("message"), "Force-Create-Indicator is boolean value and can accept true OR false only");
    }

    @Then("I should see the Outbound Correspondence was updated with the following values")
    public void iShouldSeeTheOutboundCorrespondenceWasUpdatedWithTheFollowingValues(Map<String, String> dataTable) {
        JsonPath outbRetrieve = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        JsonParser jsonParser = new JsonParser();
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "bodyData":
                    Map<String, String> map = outbRetrieve.getMap("bodyData");
                    Gson gson = new Gson();
                    String actual = gson.toJson(map);
                    System.out.println("1 - \n" + actual + "\n");
                    System.out.println("2 - \n" + String.valueOf((JsonObject) World.generalSave.get().get("bodyData")));
                    Assert.assertEquals(jsonParser.parse(actual), jsonParser.parse(String.valueOf((JsonObject) World.generalSave.get().get("bodyData"))));
                    break;
                case "ConsumerId":
                    Map<String, String> existingConsumerIds = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                    int count = 1;
                    for (String existing : existingConsumerIds.keySet()) {
                        if (existing.contains("consumer") || existing.contains("Consumer")) {
                            System.out.println(count + " - " + outbRetrieve.getString("anchor") + "\n");
                            Assert.assertTrue(outbRetrieve.getString("anchor").contains(existingConsumerIds.get(existing)));
                        }
                    }
                    break;
            }
        }
    }

    @Then("I should see a new Outbound Correspondence has been created not updating the previous Correspondence")
    public void iShouldSeeANewOutboundCorrespondenceHasBeenCreatedNotUpdatingThePreviousCorrespondence() {
        JsonPath response = (JsonPath) World.generalSave.get().get("OutboundCorrespondenceResponse");
        String expected = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        Assert.assertTrue(response.get("data.id") != null);
        Assert.assertNotEquals(response.get("data.id"), expected);
    }

    @And("I change the Correspondence status of the Outbound Correspondence to {string}")
    public void iChangeTheCorrespondenceStatusOfTheOutboundCorrespondenceTo(String status) {
        int cid = Integer.parseInt(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateCorrespondenceStatus(cid, status);
    }

    @Then("I should see that I have been navigated to the Outbound Correspondence that was {string}")
    public void iShouldSeeThatIHaveBeenNavigatedToTheOutboundCorrespondenceThatWas(String cid) {
        browserUtils.get().waitForPageToLoad(10);
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        }
        stepDefs.get().verifyNavigatedToOutboundDetailsPage(cid);
    }

    @And("I have a consumer on a case that wants to send an Outbound Correspondence")
    public void iHaveAConsumerOnACaseThatWantsToSendAnOutboundCorrespondence() {
        World.generalSave.get().put("caseConsumerId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId());
        APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("firstName")));
        APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("lastName")));
    }

    @And("I have a consumer on a case that wants to send an Outbound Correspondence with following Values")
    public void iHaveAConsumerOnACaseThatWantsToSendAnOutboundCorrespondenceWithValues(Map<String, String> dataTable) {
        World.generalSave.get().put("caseConsumerId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId(dataTable));
        APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("firstName")));
        APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("lastName")));
    }

    @And("I select an Outbound Correspondence Type")
    public void iSelectAnOutboundCorrespondenceType() {
        browserUtils.get().waitForPageToLoad(10);
        String type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundTypeName();
        createOutboundCorrespondenceRequestStepDefs.selectOutboundCorrespondencetype(type);
    }

    @And("I select a specific Outbound Correspondence Type {string}")
    public void iSelectAnOutboundCorrespondenceType(String definition) {
        browserUtils.get().waitForPageToLoad(10);
        String type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType(definition);
        stepDefs.get().selectOutboundtypeUI(type);
    }

    @And("I select a {string} Outbound Correspondence recipient with a {string} Channel")
    public void iSelectAOutboundCorrespondenceRecipientWithAChannel(String recipient, String channel) {
        if ("Random".equalsIgnoreCase(recipient)) {
            stepDefs.get().selectRandomRecipientUI();
        } else {
//            stepDefs.get().selectRecipientUI(recipient);
        }
        if ("Random".equalsIgnoreCase(channel)) {
            stepDefs.get().selectRandomChannelUI();
        } else {
//            stepDefs.get().selectRandomChannelUI(channel);
        }
    }

    @And("I enter {string} destination information for {string} channel for an Outbound Correspondence")
    public void iEnterDestinationInformationForChannelForAnOutboundCorrespondence(String dest, String channel) {
        stepDefs.get().selectOtherDestination();
        if ("Mail".equalsIgnoreCase(channel) && "Random".equalsIgnoreCase(dest)) {
            stepDefs.get().fillInFirstRecipRandomMailAddress();
        }
    }

    @When("I click to save the Outbound Correspondence Request")
    public void iClickToSaveTheOutboundCorrespondenceRequest() {
        browserUtils.get().scrollDown();
        BrowserUtils.waitFor(1);
        createOutboundCorrespondencePage.saveButton.click();
    }

    @Then("I should see the task has been linked to the newly created Outbound Correspondence")
    public void iShouldSeeTheTaskHasBeenLinkedToTheNewlyCreatedOutboundCorrespondence() {
        stepDefs.get().verifyOutboundLinkedToTask();
    }

    @And("I have a consumer not on a case that wants to send an Outbound Correspondence")
    public void iHaveAConsumerNotOnACaseThatWantsToSendAnOutboundCorrespondence() {
        Map<String, String> consumerId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId();
        World.generalSave.get().put("ConsumerId", consumerId);

    }

    @And("I click on the first case and consumer search result")
    public void iClickOnTheFirstCaseAndConsumerSearchResult() {
        BrowserUtils.waitFor(2);
        try {
            createOutboundCorrespondencePage.firstResultFromConsumerSearch.click();
        } catch (ElementNotFoundException e) {
            createOutboundCorrespondencePage.listOfResultFromConsumerSearch.get(0).click();
        } catch (Exception e) {
            createOutboundCorrespondencePage.firstResultFromConsumerOnlySearch.click();
        }
        BrowserUtils.waitFor(5);
    }

    @And("I click on the case Id that I previously created")
    public void clickCaseIdPreviouslyCreated() {
        BrowserUtils.waitFor(3);
        createOutboundCorrespondencePage.caseIdPreviouslyCreated.click();
        BrowserUtils.waitFor(3);
    }

    @And("I note the newly created Outbound Correspondence Id")
    public void iNoteTheNewlyCreatedOutboundCorrespondenceId() {
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCEID", createOutboundCorrespondencePage.firstOutbCidCaseContactDetailsTab.getText());
    }

    @And("I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of {string} language")
    public void iHaveAnOutboundDefinitionThatHasChannelIsMailPlusSendImmediatelyIsTrueOfLanguage(String language) {
        this.language.set(language);
    }

    @When("I Create an Outbound Correspondence Request of {string} language to be sent immediately")
    public void iCreateAnOutboundCorrespondenceRequestOfLanguageToBeSentImmediately(String language) {
        request.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateSendNowOutb(language));
        stepDefs.get().addRequiredKeys(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getSendNowTypes(), request.get());
        String firstName = RandomStringUtils.random(7, true, false);
        World.generalSave.get().put("firstName", firstName);
        request.get().getAsJsonObject("bodyData").addProperty("firstName", firstName);
        JsonPath obResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().VA_INEBcreateOutboundCorrespondence(request.get());
        String cid = obResponse.getString("data.id");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        World.generalSave.get().put("sendNowResponse", response);
        String nid = response.getString("recipients[0].notifications[0].notificationId");
        World.generalSave.get().put("sendNowNID", nid);
        World.generalSave.get().put("sendNowCID", cid);
    }

    @Then("I should see the status of the notification and correspondence is {string}")
    public void iShouldSeeTheStatusOfTheNotificationAndCorrespondenceIs(String status) {
        JsonPath response = (JsonPath) World.generalSave.get().get("sendNowResponse");
        Assert.assertEquals(response.getString("status"), status);
        Assert.assertEquals(response.getString("recipients[0].notifications[0].notificationStatus.status"), status);
    }

    @And("I should see the {string} has been uploaded with the previously created notification Id")
    public void iShouldSeeTheHasBeenUploadedWithThePreviouslyCreatedNotificationId(String onbaseType) {
        Map<String, String> request = new HashMap<>();
        request.put("NotificationId", String.valueOf(World.generalSave.get().get("sendNowNID")));
        JsonPath onbaseResults = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().onbaseSearch(request);
        Long cidLong = stepDefs.get().getLatestOnbaseID(onbaseResults);
        String cid = String.valueOf(cidLong);
        String actual = stepDefs.get().downloadOutboundCorrespondence(onbaseType, cid);
        ArrayList<String> expected = stepDefs.get().getCoverVaSendNowbdyData();
        for (String expectedValue : expected) {
            Assert.assertTrue(actual.contains(expectedValue));
        }
    }

    @Given("I send the request for an Outbound Correspondence to the service and save traceID")
    public void i_send_the_request_for_an_Outbound_Correspondence_to_the_service_and_save_traceID() {
        stepDefs.get().sendPostRequestAndSaveTraceID();
    }

    @Given("I retrieve event by trace id and verify")
    public void i_retrieve_event_by_trace_id_and_verify(List<String> dataTableList) {
        stepDefs.get().verifyOutboundEvent(dataTableList);
    }

    @Given("I send the request for an Outbound Correspondence to the service with created definition")
    public void i_send_the_request_for_an_Outbound_Correspondence_to_the_service_with_created_definition() {
        stepDefs.get().postOutboundCorrespondenceToServerWithCreatedDef();
    }

    @Given("I should see the Outbound Correspondence response body has Body Data Structure Version is {int}")
    public void i_should_see_the_Outbound_Correspondence_response_body_has_Body_Data_Structure_Version_is(Integer expectedVersion) {
        stepDefs.get().verifyBodyDataStructureInResponse(expectedVersion);
    }

    @Then("I should see Body Data Structure Version is {int} when initiate Get Correspondence by retrieved {string}")
    public void i_should_see_Body_Data_Structure_Version_is_when_initiate_Get_Correspondence_by_retrieved(Integer expectedVersion, String correspondenceId) {
        stepDefs.get().getCorrespondenceWithID(correspondenceId);
        Integer actualVersion = Integer.valueOf(World.save.get().get("actualVersionFromGet"));
        System.out.println("Actual version from get call " + actualVersion);
        Assert.assertEquals(actualVersion, expectedVersion, "Body Data Structure Version verification failed");
    }

    @Then("I should see the notification was changed to {string} from notification status history")
    public void iShouldSeeTheNotificationWasChangedToFromNotificationStatusHistory(String status) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getNotificationStatusHistory(String.valueOf(World.generalSave.get().get("sendNowNID")));
        boolean present = stepDefs.get().verifyNotificationStatusHistoryPastHistory(response, status);
        Assert.assertTrue(present);
    }

    @And("I should see a file with the name of previously created notification Id is placed in the configured resource for {string} tenant")
    public void iShouldSeeAFileWithTheNameOfPreviouslyCreatedNotificationIdIsPlacedInTheConfiguredSResourceForTenant(String tenant) {
        BrowserUtils.waitFor(30);
        List<File> list = PdfUtilities.getFolderContents(ConfigurationReader.getProperty("coverVAExportedBucket"));
        String nid = String.valueOf(World.generalSave.get().get("sendNowNID"));
        boolean found = false;
        for (File file : list) {
            if (file.getName().contains(nid)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    @And("I verify the contact record is visible in the links section of the Create OB Correspondence page")
    public void iVerifyTheContactRecordIsVisibleInTheLinksSectionOfTheCreateOBCorrespondencePage() {
        BrowserUtils.waitFor(3);
        browserUtils.get().scrollDown();
        Assert.assertTrue(createOutboundCorrespondencePage.contactRecordLinkName.isDisplayed());
        Assert.assertTrue(createOutboundCorrespondencePage.contactRecordLinkID.isDisplayed());
        World.generalSave.get().put("contactRecord", createOutboundCorrespondencePage.contactRecordLinkID.getText());

    }

    @Then("I should see that a link from contact record to Outbound Correspondence has been created")
    public void iShouldSeeThatALinkFromContactRecordToOutboundCorrespondenceHasBeenCreated() {
        BrowserUtils.waitFor(5);
        JsonPath contactRecordLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getContactRecordLinks(String.valueOf(World.generalSave.get().get("contactRecord")));
        stepDefs.get().verifyContactRecordLinkedOBCorrespondence(contactRecordLinks);
    }

    @Then("I should see that a link from Outbound Correspondence to contact record has been created")
    public void iShouldSeeThatALinkFromOutboundCorrespondenceToContactRecordHasBeenCreated() {
        String obID = String.valueOf(World.generalSave.get().get("OBID"));
        JsonPath OBLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundCorrespondenceLinks(obID);
        stepDefs.get().verifyOBLinkedToContactRecord(OBLinks);
    }

    @And("I navigate to the Create Outbound Correspondence page")
    public void iNavigateToTheCreateOutboundCorrespondencePage() {
        BrowserUtils.waitFor(5);
        new CRM_ContactRecordUIStepDef().createOutBoundCorrespondence();
        BrowserUtils.waitFor(7);
    }

    @Then("I should see the Updated By value on the Correspondence level displays the user name of the Connection Point User")
    public void iShouldSeeTheUpdatedByValueOnTheCorrespondenceLevelDisplaysTheUserNameOfTheConnectionPointUser() {
        BrowserUtils.waitFor(10);
        String value = viewOutboundCorrespondenceDetailsPage.updatedByValue.getText();
        Assert.assertTrue(value.contains("Service"), "value is " + value);
    }

    @And("I have request to assemble an Outbound Correspondence with the following values")
    public void iHaveRequestToAssembleAnOutboundCorrespondenceWithTheFollowingValues(Map<String, String> data) {
        notificationId.set(data.get("notificationId"));
        template.set(data.get("template."));
    }

    @When("I attempt to assemble the Outbound Correspondence")
    public void iAttemptToAssembleTheOutboundCorrespondence() {
        if ("previouslyCreated Template".equalsIgnoreCase(template.get())) {
            template.set("148020");
        } else if ("Template not a word document".equalsIgnoreCase(template.get())) {
            template.set("148018");
        } else if ("previouslyCreated NID".equalsIgnoreCase(notificationId.get())) {
            notificationId.set("100");
        }
        synchronized (response) {
            response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateOutboundPdf(notificationId.get(), template.get()));
        }
    }

    @Then("I should see the assemble Outbound Correspondence return an error")
    public void iShouldSeeTheAssembleOutboundCorrespondenceReturnAnError() {
        APIClassUtil apiClassUtil = (APIClassUtil) response.get();
        Assert.assertNotEquals(200, apiClassUtil.statusCode);
    }

    @And("I have an existing Outbound Correspondence with multiple consumer Ids and channels")
    public void iHaveAnExistingOutboundCorrespondenceWithMultipleConsumerIdsAndChannels() {
        Map<String, String> consumerOnlyId = new HashMap<>();
        int count = 1;
        consumerOnlyId.put("consumer" + count++, API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
        consumerOnlyId.put("consumer" + count++, API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
        String caseId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("caseId");
        String type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType();
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createRandomOBCorrespondenceMultipleChannels(type, consumerOnlyId, caseId);
        response.prettyPrint();
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), response.get("data.id").toString());
    }

    @And("I send a request for Outbound Correspondence with multiple consumer Ids and channels")
    public void iSendRequestForOutboundCorrespondenceWithMultipleConsumerIdsAndChannels(Map<String, String> dataTable) {
        String type = dataTable.get("correspondenceDefinitionMMSCode");
        Map<String, String> consumerIds = new HashMap<>();
        consumerIds.put("consumer1", dataTable.get("consumerId").split(",")[0]);
        consumerIds.put("consumer2", dataTable.get("consumerId").split(",")[1]);
        String caseId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("caseId");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createRandomOBCorrespondenceMultipleChannels(type, consumerIds, caseId);
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), response.get("data.id").toString());
    }

    @Then("I should see that there is an ellipsis in OB search results for when there are multiple channels and recipients")
    public void iShouldSeeThatThereIsAnEllipsisInOBSearchResultsForWhenThereAreMultipleChannelsAndRecipients() {
        BrowserUtils.waitFor(5);
        browserUtils.get().scrollDown();
        outboundCorrespondenceGlobalSearchStepDefs.get().verifyToolTipsSearchResults();
    }

    @And("I update the Outbound Correspondence Notification with the following values")
    public void iUpdateTheOutboundCorrespondenceNotificationWithTheFollowingValues(Map<String, String> data) {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath getCID = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<String> notificationIds = getCID.getList("recipients[0].notifications.notificationId");
        String nid = notificationIds.get(0);
        if (data.containsKey("Returned")) {
            generalSave.get().put("ReturnedNotificationNotificationId" + Hooks.nameAndTags.get(), nid);
            String recipientId = getCID.getString("recipients[0].recipientInfo.correspondenceRecipientId");
            String consumerId = getCID.getString("recipients[0].recipientInfo.consumerId");
            generalSave.get().put("ReturnedNotificationConsumerIdRecipientRecord" + Hooks.nameAndTags.get(), recipientId);
            generalSave.get().put("ReturnedNotificationConsumerId" + Hooks.nameAndTags.get(), consumerId);
            stepDefs.get().storeReturnedNotificationDestinationValues(String.valueOf(generalSave.get().get("ReturnedNotificationConsumerIdChannel" + Hooks.nameAndTags.get())), getCID);
        }
        for (String nidStatus : data.keySet()) {
            String statusReason = data.get(nidStatus);
            if (data.get(nidStatus).equalsIgnoreCase("random")) {
                statusReason = stepDefs.get().getRandomStatusReason(nidStatus);
            }
            API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateNotification(nid, nidStatus, statusReason);
            System.out.println("updating - " + nid + " " + nidStatus + " " + data.get(nidStatus));
        }
    }

    @And("I update Outbound Correspondence Notification {string} with the following values")
    public void iUpdateAnOutboundCorrespondenceNotificationIdWithTheFollowingValues(String num, Map<String, String> data) {
        List<String> notificationIds = World.generalList.get();
        String nid = notificationIds.get(Integer.parseInt(num));
        for (String s : data.keySet()) {
            API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateNotification(nid, s, data.get(s));
            updatedNidStatusMap.get().put(nid, new ArrayList<>(Arrays.asList(s, data.get(s))));
            System.out.println("updating - " + nid + " " + s + " " + data.get(s));
        }
    }

    @Then("I should see the notification status is {string} along with reason popuplated with {string}")
    public void iShouldSeeTheNotificationStatusIsAlongWithReasonPopuplatedWith(String status, String reason) {
        BrowserUtils.waitFor(7);
        stepDefs.get().verifyNotificationStatusandReason(status, reason);
    }

    @When("I have a consumer that wants to create an Outbound Correspondence with a written language preference as {string}")
    public void iHaveAConsumerThatWantsToCreateAnOutboundCorrespondenceWithAWrittenLanguagePreferenceAs(String language) {
        randomCaseId.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId(language));
    }

    @And("I the default language for the project {string} is {string}")
    public void iTheDefaultLanguageForTheProjectIs(String project, String language) {
//      API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().setObSettings();
        project = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId();
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObSettings(project);
        Assert.assertEquals(project, response.getString("correspondenceSettingsResponse.projectId"));
        Assert.assertEquals(language, response.getString("correspondenceSettingsResponse.defaultLanguageName"));

    }

    @And("I have a Outbound Correspondence with language specified as {string}")
    public void iHaveAOutboundCorrespondenceWithLanguageSpecifiedAs(String language) {
        JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType(), randomCaseId.get(), language);
        Assert.assertEquals(resp.getString("status"), "SUCCESS");
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), resp.get("data.id").toString());
    }

    @Then("I should see the language used is {string} in the letter data that is generated")
    public void iShouldSeeTheLanguageUsedIsInTheLetterDataThatIsGenerated(String language) {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        String nid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid).getString("recipients[0].notifications[0].notificationId");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        language = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLanguageCode(language);
        Assert.assertEquals(response.getString("sections[0].template.languageCode"), language);
    }

    @Then("I should see the language used is {string} in the letter data that is generated for {string} channel")
    public void iShouldSeeTheLanguageUsedIsInTheLetterDataThatIsGenerated(String language, String channel) {
        String nid = null;
        if (channel.equalsIgnoreCase("mail"))
            nid = World.save.get().get("Mail");
        else if (channel.equalsIgnoreCase("email"))
            nid = World.save.get().get("Email");
        else if (channel.equalsIgnoreCase("text"))
            nid = World.save.get().get("Text");
        else if (channel.equalsIgnoreCase("fax"))
            nid = World.save.get().get("Fax");

        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        language = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLanguageCode(language);
        Assert.assertEquals(language, response.getString("sections[0].template.languageCode"));
    }


    @When("I have a Outbound Correspondence with Random Destination information for {string} channel")
    public void iHaveAOutboundCorrespondenceWithRandomDestinationInformationForChannel(String channel) {
        JsonObject request = stepDefs.get().createObRequestwithSpecifiedChannel(channel);
        stepDefs.get().addRequiredKeys(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType(), request);
        JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createCorrespondence(request, ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences");
        generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), resp.getString("data.id"));
//        response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
    }

    @Then("I should see the Random Destination information for {string} channel is present in the letter data that is generated")
    public void iShouldSeeTheRandomDestinationInformationForChannelIsPresentInTheLetterDataThatIsGenerated(String channel) {
        BrowserUtils.waitFor(3);
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        Assert.assertNotNull(cid);
        JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        String nid = resp.getString("recipients[0].notifications[0].notificationId");
        JsonPath letterDataResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        stepDefs.get().verifyDestinationInformation(letterDataResponse, channel);
    }

    @Then("I should see the language template information for {string} template is found on the letter data that is generated")
    public void iShouldSeeTheLanguageTemplateInformationForTemplateIsFoundOnTheLetterDataThatIsGenerated(String language) {
        JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        String nid = resp.getString("recipients[0].notifications[0].notificationId");
        JsonPath letterDataResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        language = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLanguageCode(language);
        Assert.assertEquals(language, letterDataResponse.getString("sections[0].template.languageCode"));
    }

    @Given("I retrieve Outbound Correspondence Name by Type")
    public void iRetrieveOutboundCorrespondenceNameByType() {
        stepDefs.get().getCorrespondenceNameByMMSId();
    }

    @Given("I retrieve the Outbound Correspondence by {string} criteria search")
    public void i_retrieve_the_Outbound_Correspondence_by_criteria_search(String searchCriteria) {
        stepDefs.get().sendSearchRequestByValue(searchCriteria);
    }

    @Given("I should see {string} attribute in the response, when retrieving by {string} method")
    public void i_should_see_attribute_in_the_response_when_retrieving_by_method(String value, String method) {
        stepDefs.get().verifyBodyDataAttribute(value, method);
    }

    @Given("I should validate following attributes in the obcorrespondencesearch by caseId")
    public void i_should_validate_obsearchcorrespondence_by_caseIdcase_response(Map<String, String> searchCriteria) {
        stepDefs.get().verifysearchcrtieria(searchCriteria);
    }

    @Given("I retrieve the Outbound Correspondence by {string} Case ID")
    public void i_retrieve_the_Outbound_Correspondence_by_Case_ID(String caseid) {
        stepDefs.get().searchForCorByCaseId(caseid);
    }

    @Then("I should NOT see the Links section under Save button")
    public void i_should_NOT_see_the_Links_section_under_Save_button() {
        Assert.assertFalse(stepDefs.get().verifyLinksSectionIsDisplayed(), "Links section displayed, verification failed");
    }

    @Then("I retrieve Status and Reason from correspondence response before Notification update")
    public void iRetrieveStatusAndReasonFromCorrespondenceBeforeUpdates() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        System.out.println("Correspondence id " + cid);
        beforeUpdateStatusMap.set(stepDefs.get().getStatusAndReasonFromCorrespondence(cid));
    }

    @Then("I retrieve Status and Reason from correspondence response after notification update")
    public void iRetrieveStatusAndReasonFromCorrespondenceAfterUpdates() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        System.out.println("Correspondence id " + cid);
        afterUpdateStatusMap.set(stepDefs.get().getStatusAndReasonFromCorrespondence(cid));
    }

    @Then("I verify Notification Status and Reason in correspondence response as updated above")
    public void iVerifyNotificationStatusAndReasonInCorrespondence() {
        for (String s : updatedNidStatusMap.get().keySet()) {
            if (afterUpdateStatusMap.get().containsKey(s)) {
                Assert.assertTrue(afterUpdateStatusMap.get().get(s).get(0).equalsIgnoreCase(updatedNidStatusMap.get().get(s).get(0)), "Notification Status Message mismatch");
                Assert.assertTrue(afterUpdateStatusMap.get().get(s).get(1).equalsIgnoreCase(updatedNidStatusMap.get().get(s).get(1)), "Notification Status Reason mismatch");
            } else {
                Assert.fail("Correspondence does not have the expected Notification Id");
            }
        }
    }

    @Then("I verify Correspondence Status and Reason")
    public void iVerifyCorrespondenceStatusAndReason(Map<String, String> data) {
        List<String> statusAndReason = afterUpdateStatusMap.get().get("CorrespondenceStatusAndResponse");
        for (String s : data.keySet()) {
            String reason = data.get(s).trim();
            if(reason.equalsIgnoreCase("empty")){
                reason = "";
            }
            Assert.assertEquals(statusAndReason.get(0).trim(), s.trim(), "Correspondence Status mismatch");

            Assert.assertEquals(statusAndReason.get(1).trim(), data.get(s).trim(), "Correspondence Status Message mismatch");
        }
    }

    @Then("I verify that there is no update on Correspondence Status and Reason")
    public void iVerifyNoChangeToCorrespondenceStatusAndReason() {
        List<String> afterStatusAndReason = afterUpdateStatusMap.get().get("CorrespondenceStatusAndResponse");
        List<String> beforeStatusAndReason = beforeUpdateStatusMap.get().get("CorrespondenceStatusAndResponse");
        if (afterStatusAndReason.size() != beforeStatusAndReason.size()) {
            Assert.fail("Issue in collecting status and Reason for before and after udpates");
        } else {
            Assert.assertEquals(beforeStatusAndReason.get(0).trim(), afterStatusAndReason.get(0).trim());
            Assert.assertEquals(beforeStatusAndReason.get(1).trim(), afterStatusAndReason.get(1).trim());
        }
    }

    @Given("I retrieve the Outbound Correspondence and verify Template id, Channel Definition id and Template version")
    public void i_retrieve_the_Outbound_Correspondence_and_verify_Template_id_Channel_Definition_id_and_Template_version() {
        stepDefs.get().getChannelByMMSIDAndVerifyChannelTemplate();
    }

    @Given("I update address {string} for OB correspondence request")
    public void i_update_address_for_OB_correspondence_request(String stateValue) {
        stepDefs.get().updateStateInRequest(stateValue);
    }

    @Then("I should see a {string} in OB correspondence response with {string}")
    public void i_should_see_a_in_OB_correspondence_response_with(String message, String statusCode) {
        stepDefs.get().verifyResponseMessage(message);
        stepDefs.get().verifyResponseStatusCode(statusCode);
    }

    @Given("I have specified the following values in the request for an Outbound Correspondence {string}")
    public void i_have_specified_the_following_values_in_the_request_for_an_Outbound_Correspondence(String projectName, Map<String, String> dataTable) {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().setEndPoint();
        stepDefs.get().startJsonFromCorrespondenceFile();
        stepDefs.get().addDataTableValuesToJson(dataTable);
        //add more tenants here if needed
        if ("IN-EB".equals(projectName)) {
            stepDefs.get().addProjectId(ConfigurationReader.getProperty("INEBProjectName"));
        }
        if (!dataTable.containsKey("correspondenceDefinitionMMSCode")) {
            stepDefs.get().addMMSCode();
        }
    }

    @Given("I have specified the following values in the request for a Just Cause Outbound Correspondence {string}")
    public void i_have_specified_the_following_values_in_the_request_for_an_Outbound_Correspondence2(String projectName, Map<String, String> dataTable) {
        stepDefs.get().createOutboundCorrespondenceInstance();
        stepDefs.get().setEndPoint();
        stepDefs.get().startJsonFromCorrespondenceFileNoBodyData();
        stepDefs.get().addDataTableValuesToJson(dataTable);
        //add more tenants here if needed
        if ("IN-EB".equals(projectName)) {
            stepDefs.get().addProjectId(ConfigurationReader.getProperty("INEBProjectName"));
        }
        if (!dataTable.containsKey("correspondenceDefinitionMMSCode")) {
            stepDefs.get().addMMSCode();
        }
    }

    @And("I have an Outbound Correspondence with {string} Consumer Ids in the Regarding Section")
    public void iHaveAnOutboundCorrespondenceWithConsumerIdsInTheRegardingSection(String consumers) {
        List<String> consumerIds = new ArrayList<>();
        for (int index = 0; index < Integer.parseInt(consumers); index++) {
            Map<String, String> caseConsumerIds = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId();
            consumerIds.add(caseConsumerIds.get("consumerId"));
            generalSave.get().put("caseId", caseConsumerIds.get("caseId"));
            APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("firstName")));
            APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("lastName")));
        }
        generalSave.get().put("consumerIds", consumerIds);
        JsonArray recipients = new JsonArray();
        JsonArray regardingConsumerIds = new JsonArray();
        consumerIds.stream()
                .forEach(consumerId ->
                {
                    recipients.add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateObRecipient(consumerId, "English"));
                    regardingConsumerIds.add(consumerId);
                });
        JsonObject request = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonObject anchor = new JsonObject();
        anchor.addProperty("caseId", String.valueOf(generalSave.get().get("caseId")));
        anchor.add("regardingConsumerId", regardingConsumerIds);
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.add("requester", requester);
        request.addProperty("correspondenceDefinitionMMSCode", "CCSONLY");
        request.add("anchor", anchor);
        request.add("recipients", recipients);
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOBCorrespondence(String.valueOf(request));
    }

    @Given("I store Contact Record ID from Outbound Correspondence details page")
    public void i_store_Contact_Record_ID_from_Outbound_Correspondence_details_page() {
        BrowserUtils.waitFor(3);
        browserUtils.get().scrollDown();
        World.generalSave.get().put("contactRecord", viewOutboundCorrespondenceDetailsPage.contactRecordLinkID.getText());
    }

    @Given("I send the request for an Outbound Correspondence With empty strings to the service and save traceID")
    public void i_send_the_request_for_an_Incorrect_Outbound_Correspondence_to_the_service_and_save_traceID() {
        stepDefs.get().sendPostRequestWithEmptyStringsAndSaveTraceID();
    }

    @And("I send the custom request Outbound Correspondence to the service with the following values")
    public void i_send_custom_request_OBCorrespondence(Map<String, String> dataTable) {
        stepDefs.get().postCustomCorrespondence(dataTable);
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), save.get().get("CorrespondenceId"));
    }

    @And("I send the custom request OB Correspondence to the service with the following values")
    public void i_send_custom_request_OBCorr(Map<String, String> dataTable) {
        stepDefs.get().postCustomCorrespondence(dataTable);
    }

    @And("I send the custom request Outbound Correspondence to the service")
    public void i_send_custom_request_OBCorrespondence_fromCase() {
        Map<String, String> map = new HashMap<>();
        map.put("correspondenceDefinitionMMSCode", "0SPANISH");
        map.put("language", "Spanish");
        String caseId = ((Map) World.generalSave.get().get("caseConsumerId")).get("caseId").toString();
        System.out.println(caseId);
        map.put("CaseID", caseId);
        stepDefs.get().postCustomCorrespondence(map);
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), save.get().get("CorrespondenceId"));
    }

    @Given("I retrieve event by trace id and verify for empty strings")
    public void i_retrieve_event_by_trace_id_and_verifyForEmptyStrings(List<String> dataTableList) {
        stepDefs.get().verifyOutboundEventForEmptyStrings(dataTableList);
    }


    @Then("I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as {string}")
    public void iShouldSeeTheNotificationandCorrespondnedestatusdetails(String status) {
        JsonPath response = (JsonPath) API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(World.generalSave.get().get("sendNowCID").toString());
        Assert.assertEquals(response.getString("status"), status);
        Assert.assertEquals(response.getString("recipients[0].notifications[0].notificationStatus.status"), status);

        if (status.equalsIgnoreCase("Assembled")) {
            Assert.assertTrue(response.getString("recipients[0].notifications[0].objectReceivedOn") != null);
            Assert.assertEquals(response.getString("recipients[0].notifications[0].updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
            Assert.assertTrue(response.getString("recipients[0].notifications[0].updatedDatetime") != null);
        }
        if (status.equalsIgnoreCase("Error")) {
            Assert.assertTrue(response.getString("recipients[0].notifications[0].objectReceivedOn") == null);
            Assert.assertEquals(response.getString("recipients[0].notifications[0].updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
            Assert.assertTrue(response.getString("recipients[0].notifications[0].updatedDatetime") != null);
            Assert.assertEquals(response.getString("recipients[0].notifications[0].notificationStatus.statusReason"), "Assembly Error");
            Assert.assertEquals(response.getString("statusMessage"), "Assembly Error");

        }
        if (status.equalsIgnoreCase("Requested")) {
            Assert.assertTrue(response.getString("recipients[0].notifications[0].objectReceivedOn") == null);
            Assert.assertEquals(response.getString("recipients[0].notifications[0].updatedBy"), "2492");
            Assert.assertTrue(response.getString("recipients[0].notifications[0].updatedDatetime") != null);
        }


    }

    @Then("I send api call to generate pdf with {string} notificationid and {string} userid")
    public void isendapicalltogeneratepdf(String notificationId, String userId) {
        if (notificationId.equalsIgnoreCase("previouslycreated"))
            notificationId = World.generalSave.get().get("sendNowNID").toString();
        else if (notificationId.equalsIgnoreCase("invalid"))
            notificationId = "1234567890";
        else if (notificationId.equalsIgnoreCase("empty"))
            notificationId = "";
        else if (notificationId.equalsIgnoreCase("null"))
            notificationId = null;

        if (userId.equalsIgnoreCase("currentuser"))
            userId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId;
        else if (userId.equalsIgnoreCase("empty"))
            userId = "";
        else if (userId.equalsIgnoreCase("null"))
            userId = null;

        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().pdfgenerator(notificationId, userId);
        String status = response.getString("status");

        if (status.equalsIgnoreCase("SUCCESS")) {
            Assert.assertEquals(response.getString("data.status"), "Assembled");
            Assert.assertEquals(response.getString("data.message"), "Correspondence object assembled successfully");
            Assert.assertTrue(response.getString("data.errorDetails") == null);
            Assert.assertTrue(!response.getString("data.documentId").isEmpty());
        } else if (status.equalsIgnoreCase("ERROR")) {

            if (notificationId == null || (notificationId.equalsIgnoreCase("empty") && !userId.equalsIgnoreCase("empty"))) {
                Assert.assertTrue(response.getString("data") == null);
                Assert.assertTrue(response.getString("errors.message").contains("notificationId is a required field and can not be null or empty"));
            } else if (userId == null || (!notificationId.equalsIgnoreCase("empty") && userId.equalsIgnoreCase("empty"))) {
                Assert.assertTrue(response.getString("data") == null);
                Assert.assertTrue(response.getString("errors.message").contains("userId is a required field and can not be null or empty"));
            } else if ((notificationId == null && userId == null) || (notificationId.equalsIgnoreCase("empty") && userId.equalsIgnoreCase("empty"))) {
                Assert.assertTrue(response.getString("data") == null);
                Assert.assertTrue(response.getString("errors[0].message").contains("notificationId is a required field and can not be null or empty"));
                Assert.assertTrue(response.getString("errors[1].message").contains("userId is a required field and can not be null or empty"));

            } else if (notificationId.equalsIgnoreCase("invalid")) {
                Assert.assertTrue(response.getString("data") == null);
                Assert.assertTrue(response.getString("errors.message").contains("Notification ID not found"));
            } else if (notificationId.equalsIgnoreCase("previouslycreated")) {
                Assert.assertTrue(response.getString("data.status").contains("ERROR"));
                Assert.assertTrue(response.getString("data.statusReason").contains("Assembly Error"));
                Assert.assertTrue(response.getString("data.message").contains("Failed to generate letter object"));
                Assert.assertTrue(response.getString("data.errorDetails").contains("following error returned from bff downloadFile endpoint: 404 Not Found"));
                //Assert.assertTrue(response.getString("data.documentId")==null);
            }

        }

    }

    @Given("I initiate Get letter data by the retrieved NID")
    public void i_I_initiate_Get_letter_data_by_the_retrieved_NID() {
        stepDefs.get().getLetterDataByNotificationId();
    }

    @Given("I verify get letter data response contains expected body data")
    public void i_verify_get_letter_data_response_contains_expected_body_data() {
        stepDefs.get().verifyLetterDataResponseContainsBodyData();
    }


    @And("I verify response has the created by and on values from above request")
    public void iVerifyCreatedByOnValues() {
        stepDefs.get().verifyCreatedByAndDateDetails();
    }

    @And("I get the Links for Correspondence response")
    public void iGetLinksForCorrespondence() {
        String corrId = World.save.get().get("CorrespondenceId");
        synchronized (response) {
            response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLinkForCorrespondence(corrId));
        }

    }

    @And("I verify Link is created for correspondence and Contact Id")
    public void iVerifyLinkBetweenCorrespondenceAndContactId() {
        stepDefs.get().verifyLinkBetweenContactAndCorrId(response.get());
    }

    @And("I verify Link is created for correspondence and Task Id")
    public void iVerifyLinkBetweenCorrespondenceAndTaskId() {
        stepDefs.get().verifyLinkBetweenContactAndTaskId(response.get());
    }

    @And("I verify get letter data response contains following values")
    public void i_verify_get_letter_data_response_contains_expected_body_data(Map<String, String> dataTable) {
        stepDefs.get().verifyLetterDataResponseContainsExpectedData(dataTable);
    }

    @And("I verify get letter data response contains section Element with following values")
    public void i_verify_get_letter_data_response_section_Element(Map<String, String> dataTable) {
        stepDefs.get().verifyLetterDataResponseContainsSection(dataTable);
    }

    @Given("I initiate Get letter data by the retrieved NID from OBCorrespondence")
    public void iGetLetterDataByNidFromObResponse() {
        stepDefs.get().getLetterDataByNotificationIdFromObcResponse();
    }

    @Given("I initiate Get letter data by notificationId for {string}")
    public void iGetLetterDataByNid(String nid) {
        stepDefs.get().getLetterDataByNotificationId(nid);
    }

    @Given("I initiated Get Notifications retrieved from {string} CID")
    public void iGetnotificationsResponse(String cid) {
        if (cid.equalsIgnoreCase("previouslyCreated"))
            cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
        World.generalSave.get().put("GETOBNOTIFICATIONRESPONSE", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getnotifications(cid));
    }

    @When("I have request to create Application with type {string} and name {string}")
    public void i_have_request_to_create_Application_with_type_and_name(String appType, String appName) {
        stepDefs.get().createApplicationRequest(appType, appName);
    }

    @Then("I send a request to create Application")
    public void i_send_a_request_to_create_Application() {
        stepDefs.get().sendApplicationRequest();
    }

    @Then("I retrieve the Application details by application ID")
    public void i_retrieve_the_Application_by_application_ID() {
        stepDefs.get().retrieveApplicationDetailsByID();
    }

    @Given("I validate Application under links section contains following values")
    public void i_validate_Application_under_links_section_contains_following_values(Map<String, String> dataTable) {
        stepDefs.get().verifyApplicationValuesUnderLinksSection(dataTable);
    }

    @Given("I update Application status to {string} with reason {string}")
    public void i_update_Application_status_to_with_reason(String status, String reason) {
        stepDefs.get().updateApplicationStatus(status, reason);
    }


    @And("I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request")
    public void iInvokeTheEndpointToRetrieveDestinationsForTheRecipientsOnTheOutboundCorrespondenceRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().retrieveNotifications(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
    }

    @And("I invoke provision endpoint accepts and processes list of outbound correspondence order IDs")
    public void iInvokeTheEndpointToRetrieveDestinationsForTheRecipientsOnTheOutboundCorrespondenceRequest(List<String> obcids) {
        String[] cids = new String[obcids.size()];
        for (int i = 0; i < obcids.size(); i++) {
            if ("previouslyCreated".equalsIgnoreCase(obcids.get(i)))
                cids[i] = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
            else if ("previouslyCreated1".equalsIgnoreCase(obcids.get(i)))
                cids[i] = World.random.get().get("previouslyCreated1").toString();
            else if ("previouslyCreated2".equalsIgnoreCase(obcids.get(i)))
                cids[i] = World.random.get().get("previouslyCreated2").toString();

            else
                cids[i] = obcids.get(i);
        }
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().retrieveNotifications(cids);
    }

    @And("I have a consumer on a case that wants to send an Outbound Correspondence with the following values")
    public void iHaveAConsumerOnACaseThatWantsToSendAnOutboundCorrespondenceWithTheFollowingValues(Map<String, String> caseData) {
        Map<String, String> data = new HashMap<>();
        data.putAll(caseData);
        if (caseData.containsKey("mailDestination") && caseData.get("mailDestination").contains("random")) {
            data = stepDefs.get().getCaseLoaderRandomMailAddress(data);
        }
        if (caseData.containsKey("emailDestination") && caseData.get("emailDestination").contains("random")) {
            data = stepDefs.get().getCaseLoaderRandomEmailAddress(data);
        }
        if (caseData.containsKey("faxDestination") && caseData.get("faxDestination").contains("random")) {
            data = stepDefs.get().getCaseLoaderRandomFax(data);
        }
        if (caseData.containsKey("textDestination") && caseData.get("textDestination").contains("random")) {
            data = stepDefs.get().getCaseLoaderRandomText(data);
        }
        if (caseData.containsKey("secondMailDestination") && caseData.get("secondMailDestination").contains("random")) {
            data = stepDefs.get().getCaseLoaderSecondRandomMailAddress(data);
        }
        if (caseData.containsKey("secondFaxDestination") && caseData.get("secondFaxDestination").contains("random")) {
            data = stepDefs.get().getCaseLoaderSecondRandomFax(data);
        }
        if (caseData.containsKey("secondTextDestination") && caseData.get("secondTextDestination").contains("random")) {
            data = stepDefs.get().getCaseLoaderSecondRandomText(data);
        }
        generalSave.get().put("caseLoaderRequestMap", data);
        JsonObject request = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().buildCaseLoaderRequestEcms(data);
        generalSave.get().put("previousCaseLoaderRequest", request);
        randomCaseId.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateRandomCaseId(request));
        if (null == randomCaseId.get()) {
            API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().retryGenerateRandomCaseId(randomCaseId.get(), 3);
        }
        generalSave.get().put("caseConsumerId", randomCaseId.get());
        APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("consumerFirstName")));
        APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("consumerLastName")));
    }

    @And("I have specified the following values in the request for an Outbound Correspondence without recipients in the request")
    public void iHaveSpecifiedTheFollowingValuesInTheRequestForAnOutboundCorrespondenceWithoutRecipientsInTheRequest(Map<String, String> data) {
        String type = "default";
        Map<String, String> caseId = (Map<String, String>) generalSave.get().get("caseConsumerId");
        if ("DC-EB".equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())) {
            String tempDcCaseId = API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId();
            String tempDcConsumerId = API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getExternalConsumerId();
            Map<String, String> tempCaseConsumerId = new HashMap<>();
            tempCaseConsumerId.put("caseId", tempDcCaseId);
            tempCaseConsumerId.put("consumerId", tempDcConsumerId);
            caseId = tempCaseConsumerId;
            generalSave.get().put("caseConsumerId", tempCaseConsumerId);
        }
        Map<String, String> correspondenceRequest = new HashMap<>();
        Map<String, String> secondConsumer = new HashMap<>();
        if (generalSave.get().containsKey("secondConsumer")) {
            secondConsumer = (Map<String, String>) generalSave.get().get("secondConsumer");
        }
        for (String featureFileData : data.keySet()) {
            switch (featureFileData) {
                case "caseId":
                    if (!data.get("caseId").equalsIgnoreCase("previouslyCreated")) {
                        correspondenceRequest.put("caseId", data.get("caseId"));
                    } else {
                        correspondenceRequest.put("caseId", caseId.get("caseId"));
                    }
                    break;
                case "regardingConsumerId":
                    correspondenceRequest.put("regardingConsumerId", caseId.get("consumerId"));
                    break;
                case "correspondenceDefinitionMMSCode":
                    if (data.containsKey("correspondenceDefinitionMMSCode")) {
                        type = data.get("correspondenceDefinitionMMSCode");
                    }
                    correspondenceRequest.put(featureFileData, type);
                    break;
                case "secondRegardingConsumerId":
                    System.out.println("adding second consumer id - " + secondConsumer.get("consumerId"));
                    correspondenceRequest.put("regardingConsumerId2", secondConsumer.get("consumerId"));
                    break;
                default:
                    if ("null".equalsIgnoreCase(data.get(featureFileData))) {
                        continue;
                    } else {
                        correspondenceRequest.put(featureFileData, data.get(featureFileData));
                    }
            }
        }
        generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, correspondenceRequest).getString("data.id"));
        generalSave.get().put("OBREQUESTTRACEID" + Hooks.nameAndTags.get(), APIClassUtil.traceId.get());
    }

    @And("I have specified the following values in the request for a Bulk Outbound Correspondence")
    public void iHaveSpecifiedTheFollowingValuesInTheRequestFoaABulkOutboundCorrespondence(Map<String, String> data) {
        Response response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createBulkCorrespondence(data);
    }

    @And("I have specified the following values in the Create Outbound Notification request")
    public void iHaveSpecifiedTheFollowingValuesInTheRequestForCreateObNotificaion(Map<String, String> data) {
        Response response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createObNotification(data);
    }

    @And("I have a consumer on a case that wants to send an Outbound Correspondence but has changed his {string} contact information")
    public void iHaveAConsumerOnACaseThatWantsToSendAnOutboundCorrespondenceButHasChangedHisContactInformation(String channel) {
        Assert.assertNotNull(generalSave.get().get("previousCaseLoaderRequest"));
        JsonObject request = (JsonObject) generalSave.get().get("previousCaseLoaderRequest");
        if ("mailDestination".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomMailAddress(request);
        } else if ("emailDestination".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomEmailAddress(request);
        } else if ("faxDestination".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomFax(request);
        } else if ("textDestination".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomText(request);
        }
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateRandomCaseId(request);
    }

    @And("I verify that the {string} contact information has been updated for the consumer on a case that wants to send an Outbound Correspondence")
    public void iVerifyThatTheContactInformationHasBeenUpdatedForTheConsumerOnACaseThatWantsToSendAnOutboundCorrespondence(String channel) {
        Map<String, String> caseId = (Map<String, String>) generalSave.get().get("caseConsumerId");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRecipientsInfoByCaseNumber(caseId.get("caseId"));
        if (channel.contains("email")) {
            //future story
        } else if (channel.contains("mail")) {
            Map<String, Object> address = (Map<String, Object>) generalSave.get().get("mailAddress");
            Assert.assertEquals("true", String.valueOf(response.getBoolean("consumers[0].mail.usability.usableFlag")));
            Assert.assertEquals(address.get("addressCity"), response.getString("consumers[0].mail.addresses[0].city"));
            Assert.assertEquals(address.get("addressState"), response.getString("consumers[0].mail.addresses[0].state"));
            Assert.assertEquals(address.get("addressStreet1"), response.getString("consumers[0].mail.addresses[0].addressLine1"));
            Assert.assertEquals(address.get("addressStreet2"), response.getString("consumers[0].mail.addresses[0].addressLine2"));
            Assert.assertEquals(address.get("addressType"), response.getString("consumers[0].mail.addresses[0].addressType"));
            Assert.assertEquals(address.get("addressVerified"), response.getString("consumers[0].mail.addresses[0]"));
            Assert.assertEquals(String.valueOf(address.get("primaryIndicator")), String.valueOf(response.getBoolean("consumers[0].mail.addresses[0].primaryFlag")));
            Assert.assertEquals(address.get("addressZip"), response.getString("consumers[0].mail.addresses[0].zip"));
        } else if (channel.contains("fax")) {
            //future story
        } else if (channel.contains("text")) {
            //future story
        }
    }

    @Then("I should see a {string} notification that has the most updated contact information with the following values")
    public void iShouldSeeANotificationThatHasTheMostUpdatedContactInformationWithTheFollowingValues(String channel, Map<String, String> data) {
        BrowserUtils.waitFor(8);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        if (channel.contains("email")) {
            Map<String, Object> emailAddress = (Map<String, Object>) generalSave.get().get("emailAddress");
            Assert.assertEquals(response.getString("recipients[0].recipientInfo.emailAddress"), emailAddress.get("emailAddress"));
        } else if (channel.contains("mail")) {
            Map<String, Object> mailAddress = (Map<String, Object>) generalSave.get().get("mailAddress");
            Assert.assertEquals(mailAddress.get("addressCity"), response.getString("recipients[0].recipientInfo.city"));
            Assert.assertEquals(mailAddress.get("addressState"), response.getString("recipients[0].recipientInfo.state"));
            Assert.assertEquals(mailAddress.get("addressStreet1"), response.getString("recipients[0].recipientInfo.streetAddress"));
            Assert.assertEquals(mailAddress.get("addressStreet2"), response.getString("recipients[0].recipientInfo.streetAddionalLine1"));
            Assert.assertEquals(mailAddress.get("addressZip"), response.getString("recipients[0].recipientInfo.zipcode"));
        } else if (channel.startsWith("text")) {
            Map<String, Object> textAddress = (Map<String, Object>) generalSave.get().get("textAddress");
            Assert.assertEquals(textAddress.get("phoneNumber"), response.getString("recipients[0].recipientInfo.textNumber"));
        } else if (channel.startsWith("fax")) {
            Map<String, Object> faxAddress = (Map<String, Object>) generalSave.get().get("faxAddress");
            Assert.assertEquals(faxAddress.get("phoneNumber"), response.getString("recipients[0].recipientInfo.faxNumber"));
        } else if (channel.startsWith("secondTextDestination")) {
            Map<String, Object> secondText = (Map<String, Object>) generalSave.get().get("secondText");
            Assert.assertEquals(secondText.get("phoneNumber"), response.getString("recipients[0].recipientInfo.textNumber"));
        } else if (channel.startsWith("secondFaxDestination")) {
            Map<String, Object> secondFax = (Map<String, Object>) generalSave.get().get("secondFax");
            Assert.assertEquals(secondFax.get("phoneNumber"), response.getString("recipients[0].recipientInfo.faxNumber"));
        }
    }

    @Then("I should see the Outbound Correspondence is updated with the following values but Notifications are not created")
    public void iShouldSeeTheOutboundCorrespondenceIsUpdatedWithTheFollowingValues(Map<String, String> data) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        Assert.assertEquals(data.get("status"), response.getString("status"));
        Assert.assertEquals(data.get("statusReason"), response.getString("statusMessage"));
    }

    @And("I should see the Outbound Correspondence Notification is created with the following values")
    public void iShouldSeeTheOutboundCorrespondenceNotificationIsCreatedWithTheFollowingValues(Map<String, String> data) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        generalSave.get().put("notificationId", response.getString("recipients[0].notifications[0].notificationId"));
        Assert.assertEquals(data.get("status"), response.getString("recipients[0].notifications[0].notificationStatus.status"));
        Assert.assertEquals(data.get("statusReason"), response.getString("recipients[0].notifications[0].notificationStatus.statusReason"));
    }

    @And("I should see the Outbound Correspondence Notification is created with the following values for {string} channel for {string} Outbound Correspondence")
    public void iShouldSeeTheOutboundCorrespondenceNotificationIsCreatedWithTheFollowingValues(String channel, String OBcid, Map<String, String> data) {
        if (OBcid.equalsIgnoreCase("previouslycreated")) {
            OBcid = (String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(OBcid);

        List<Map<String, Object>> notifications = response.getList("recipients[0].notifications");
        for (int i = 0; i < notifications.size(); i++) {
            if ("notificationId".equalsIgnoreCase(channel)) {
                Assert.assertEquals(response.getString("recipients[0].notifications[" + i + "].notificationStatus.status"), data.get("status"));
                Assert.assertEquals(response.getString("recipients[0].notifications[" + i + "].notificationStatus.statusReason"), data.get("statusReason"));

            }
        }
    }

    @And("I add another {string} on the {string} case that will receive an Outbound Correspondence")
    public void iAddAnotherOnTheCaseThatWillReceiveAnOutboundCorrespondence(String role, String caseId) {
        Assert.assertNotNull(generalSave.get().get("previousCaseLoaderRequest"));
        JsonObject request = (JsonObject) generalSave.get().get("previousCaseLoaderRequest");
        if ("previouslyCreated".equalsIgnoreCase(caseId)) {
            request = stepDefs.get().editCaseLoaderAddConsumer(request, role);
        }
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateRandomCaseId(request);
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().addSecondCaseConsumerId();
    }

    @And("I add another {string} on the {string} case with a new {string} that will receive an Outbound Correspondence")
    public void iAddAnotherOnTheCaseWithANewThatWillReceiveAnOutboundCorrespondence(String role, String caseId, String channel) {
        Assert.assertNotNull(generalSave.get().get("previousCaseLoaderRequest"));
        JsonObject request = (JsonObject) generalSave.get().get("previousCaseLoaderRequest");
        if ("previouslyCreated".equalsIgnoreCase(caseId)) {
            request = stepDefs.get().editCaseLoaderAddConsumer(request, role);
        }
        if ("mailDestination".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomMailAddress(request);
        } else if ("emailDestination".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomEmailAddress(request);
        } else if ("faxDestination".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomFax(request);
        } else if ("textDestination".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomText(request);
        } else if ("allchannels".equalsIgnoreCase(channel)) {
            request = stepDefs.get().editCaseLoaderRandomMailAddress(request);
            request = stepDefs.get().editCaseLoaderRandomEmailAddress(request);
            request = stepDefs.get().editCaseLoaderRandomText(request);
            request = stepDefs.get().editCaseLoaderRandomFax(request);
        } else if ("Mail,Fax,Text,Email".equalsIgnoreCase(channel)) {
            //same destinations from original request.get()
        }
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateRandomCaseId(request);
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().addSecondCaseConsumerId();
    }

    @Then("I verify the {string} Outbound Correspondence is linked to the {string} task")
    public void iVerifyTheOutboundCorrespondenceIsLinkedToTheTask(String cid, String task) {
        stepDefs.get().verifyOutboundLinkedToTask();
        inboundDocumentTaskStepDefs.get().setLatestTaskId(Integer.valueOf(String.valueOf(generalSave.get().get("TASKID"))));
        inboundDocumentTaskStepDefs.get().verifyLinkInTask("OUTBOUND_CORRESPONDENCE", String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
    }

    @And("I create an Outbound Correspondence with the {string} task Id in the request")
    public void iCreateAnOutboundCorrespondenceWithTheTaskIdInTheRequest(String taskId) {
        if ("previouslyCreated".equalsIgnoreCase(taskId)) {
            taskId = String.valueOf(APITaskManagementController.taskId.get());
            generalSave.get().put("TASKID", taskId);
        }
        stepDefs.get().createObLinkedToTask(taskId);
    }

    @Then("I should see the Inbound Type defined in the Outbound type is included in data object")
    public void iShouldSeeTheInboundTypeDefinedInTheOutboundTypeIsIncludedInDataObject() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        String nid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid).getString("recipients[0].notifications[0].notificationId");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        JsonPath inbTypeBarcode = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().searchInboundType("maersk Case + Consumer");
        String barcode = inbTypeBarcode.getString("inboundCorrespondence.barcode");
        Assert.assertEquals(barcode, response.getString("sections[0].template.form"));
    }

    @Then("I verify I navigated back to {string}")
    public void iVerifyINavigatedBackTo(String expectedPageHeader) {
        switch (expectedPageHeader.toUpperCase()) {
            case "APPLICATION_TRACKING_PAGE":
                Assert.assertTrue(crmApplicationTrackingPage.applicationInformationPanel.isDisplayed(), "User didn't navigate to the " + expectedPageHeader);
                break;
            case "CASE_CONSUMER_DETAILS_PAGE":
                Assert.assertTrue(crmDemographicContactInfoPage.primaryIndividualsLabel.isDisplayed(), "User didn't navigate to the " + expectedPageHeader);
                break;
            case "CONTACT_HISTORY_PAGE":
                Assert.assertTrue(crmContactHistoryPage.generalConsumerInContact.isDisplayed(), "User didn't navigate to the " + expectedPageHeader);
                break;
            case "TASK_DETAILS_PAGE":
                Assert.assertTrue(crmTaskEditHistoryPage.taskDetailsTab.isDisplayed(), "User didn't navigate to the " + expectedPageHeader);
                break;
            default:
                Assert.fail(expectedPageHeader + " is mismatched");
        }
    }

    @And("I should see that no letter data was created for the {string} Notification for {string} channel")
    public void iShouldSeeThatNoLetterDataWasCreatedForTheChannelNotification(String cid, String channel) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        }
        JsonPath correspondenceDetails = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, Object>> notifications = correspondenceDetails.getList("recipients[0].notifications");
        for (Map<String, Object> notification : notifications) {
            if (channel.equalsIgnoreCase(String.valueOf(notification.get("channelType")))) {
                JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(String.valueOf(notification.get("notificationId")));
                Assert.assertEquals("Letter Data does not exists for Notification id : " + String.valueOf(notification.get("notificationId")).trim(), response.getString("errors[0].message"));
            }
        }
    }

    @And("I should see the Outbound Correspondence Notification for {string} channel is created with the following values")
    public void iShouldSeeTheOutboundCorrespondenceNotificationForChannelIsCreatedWithTheFollowingValues(String channel, Map<String, String> data) {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath correspondenceDetails = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, Object>> notifications = correspondenceDetails.getList("recipients[0].notifications");
        for (Map<String, Object> notification : notifications) {
            if (channel.equalsIgnoreCase(String.valueOf(notification.get("channelType")))) {
                String enid = String.valueOf(notification.get("notificationId"));
                JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(String.valueOf(notification.get("notificationId")));
                Map<String, Object> statuses = (Map<String, Object>) notification.get("notificationStatus");
                if (data.containsKey("statusReason")) {
                    if ("null".equalsIgnoreCase(data.get("statusReason"))) {
                        Assert.assertNull(response.getString("statusReason"), "\nnid - " + enid + "\ncid - " + cid);
                    } else {
                        Assert.assertEquals(data.get("statusReason"), statuses.get("statusReason"), "\nnid - " + enid + "\ncid - " + cid);
                    }
                }
                Assert.assertEquals(String.valueOf(statuses.get("status")), data.get("status"), "\nnid - " + enid + "\ncid - " + cid);
                if (data.get("status").equalsIgnoreCase("Precluded")) {
                    Assert.assertEquals("Letter Data does not exists for Notification id : " + String.valueOf(notification.get("notificationId")).trim(), response.getString("errors[0].message"));
                }
            }
        }
    }

    @Then("I should see a Mail, Email, Text, Fax notification that has the most updated contact information with the following values")
    public void iShouldSeeAMailEmailTextFaxNotificationThatHasTheMostUpdatedContactInformationWithTheFollowingValues() {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        Map<String, Object> address = (Map<String, Object>) generalSave.get().get("mailAddress");
        Map<String, Object> emailAddress = (Map<String, Object>) generalSave.get().get("emailAddress");
        Map<String, Object> faxAddress = (Map<String, Object>) generalSave.get().get("secondFaxAddress");
        Map<String, Object> textAddress = (Map<String, Object>) generalSave.get().get("secondTextAddress");

        Assert.assertEquals(emailAddress.get("emailAddress"), response.getString("recipients[0].recipientInfo.emailAddress"));

        Assert.assertEquals(address.get("addressCity"), response.getString("recipients[0].recipientInfo.city"));
        Assert.assertEquals(address.get("addressState"), response.getString("recipients[0].recipientInfo.state"));
        Assert.assertEquals(address.get("addressStreet1"), response.getString("recipients[0].recipientInfo.streetAddress"));
        Assert.assertEquals(address.get("addressStreet2"), response.getString("recipients[0].recipientInfo.streetAddionalLine1"));
        Assert.assertEquals(address.get("addressZip"), response.getString("recipients[0].recipientInfo.zipcode"));

        Assert.assertEquals(textAddress.get("secondTextphoneNumber"), response.getString("recipients[0].recipientInfo.textNumber"));

        Assert.assertEquals(faxAddress.get("secondFaxphoneNumber"), response.getString("recipients[0].recipientInfo.faxNumber"));
    }

    @And("I should see the language used for all notifications that were created is {string} in the letter data that is generated")
    public void iShouldSeeTheLanguageUsedForAllNotificationsThatWereCreatedIsInTheLetterDataThatIsGenerated(String language) {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        List<Map<String, Object>> notifications = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid).getList("recipients[0].notifications");
        List<String> nids = notifications.stream().map(notification -> String.valueOf(notification.get("notificationId"))).collect(Collectors.toList());
        language = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLanguageCode(language);
        for (String nid : nids) {
            JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
            Assert.assertEquals(response.getString("sections[0].template.languageCode"), language, "\n language - " + language);
        }
    }

    @Then("I should see a Mail, Email, Text, Fax notification for each recipient that has the most updated contact information with the following values")
    public void iShouldSeeAMailEmailTextFaxNotificationForEachRecipientThatHasTheMostUpdatedContactInformationWithTheFollowingValues() {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        Map<String, Object> address = (Map<String, Object>) generalSave.get().get("mailAddress");
        Map<String, Object> emailAddress = (Map<String, Object>) generalSave.get().get("emailAddress");
        Map<String, Object> faxAddress = (Map<String, Object>) generalSave.get().get("secondFaxAddress");
        Map<String, Object> textAddress = (Map<String, Object>) generalSave.get().get("secondTextAddress");
        List<Map<String, Object>> consumers = response.getList("recipients");

        for (int recip = 0; recip < consumers.size(); recip++) {
            Assert.assertEquals(emailAddress.get("emailAddress"), response.getString("recipients[" + recip + "].recipientInfo.emailAddress"));

            Assert.assertEquals(address.get("addressCity"), response.getString("recipients[" + recip + "].recipientInfo.city"));
            Assert.assertEquals(address.get("addressState"), response.getString("recipients[" + recip + "].recipientInfo.state"));
            Assert.assertEquals(address.get("addressStreet1"), response.getString("recipients[" + recip + "].recipientInfo.streetAddress"));
            Assert.assertEquals(address.get("addressStreet2"), response.getString("recipients[" + recip + "].recipientInfo.streetAddionalLine1"));
            Assert.assertEquals(address.get("addressZip"), response.getString("recipients[" + recip + "].recipientInfo.zipcode"));

            Assert.assertEquals(textAddress.get("secondTextphoneNumber"), response.getString("recipients[" + recip + "].recipientInfo.textNumber"));

            Assert.assertEquals(faxAddress.get("secondFaxphoneNumber"), response.getString("recipients[" + recip + "].recipientInfo.faxNumber"));
        }
    }

    @And("I should see that a letter data record is created for the notification that has a {string} Channel")
    public void iShouldSeeThatALetterDataRecordIsCreatedForTheNotificationThatHasAChannel(String channel) {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath correspondenceDetails = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, Object>> notifications = correspondenceDetails.getList("recipients[0].notifications");
        for (Map<String, Object> notification : notifications) {
            if (channel.equalsIgnoreCase(String.valueOf(notification.get("channelType")))) {
                JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(String.valueOf(notification.get("notificationId")));
                Assert.assertEquals(response.get("notification.notificationId"), String.valueOf(notification.get("notificationId")));
                Assert.assertEquals(response.get("sections[0].template.languageTemplateId"), String.valueOf(notification.get("template")));

            }
        }
    }

    @Then("I should see the Outbound Correspondence Order is updated to the following status with status message")
    public void iShouldSeeTheOutboundCorrespondenceOrderIsUpdatedToTheFollowingStatusWithStatusMessage(Map<String, String> data) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        Assert.assertEquals(data.get("status"), response.getString("status"));
        if ("null".equalsIgnoreCase(data.get("statusMessage"))) {
            Assert.assertNull(response.getString("statusMessage"));
        } else {
            Assert.assertEquals(data.get("statusMessage"), response.getString("statusMessage"));
        }
    }

    @Then("I stored details of each notification for {string} ob correspondence")
    public void iStoredLanguagefortheNIDchannels(String CID) {
        if (CID.equalsIgnoreCase("previouslycreated"))
            CID = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        List<Map<String, String>> createdCorrespondences = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(CID).getList("recipients[0].notifications");
        for (Map<String, String> nids : createdCorrespondences) {
            World.save.get().put(nids.get("notificationId"), nids.get("language"));
            World.save.get().put(nids.get("channelType"), nids.get("notificationId"));
        }
    }

    @Then("I stored details of each recipient notification for {string} ob correspondence")
    public void iStorednotificationdetailsfortheNIDchannels(String CID) {
        if (CID.equalsIgnoreCase("previouslycreated"))
            CID = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath createdCorrespondences = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(CID);

        String nid = createdCorrespondences.getString("recipients[0].notifications[0].notificationId");
        String nid_channel = createdCorrespondences.getString("recipients[0].notifications[0].channelType");
        String resendnid = createdCorrespondences.getString("recipients[1].notifications[0].notificationId");
        String resendnid_channel = createdCorrespondences.getString("recipients[1].notifications[0].channelType");

        World.save.get().put(nid_channel, nid);
        World.save.get().put(resendnid_channel, resendnid);

    }

    @And("I stored DCEB case and consumer id to caseConsumerId variable")
    public void IstoredDCEBcaseandconsumeridtocaseConsumerIdvariable() {
        Map<String, String> caseConsumerId = new HashMap<>();
        caseConsumerId.put("caseId", API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId());
        caseConsumerId.put("consumerId", API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getExternalConsumerId());
        generalSave.get().put("caseConsumerId", caseConsumerId);
    }


    @And("I build an Outbound Correspondence request with a recipient containing the following values")
    public void iBuildAnOutboundCorrespondenceRequestWithARecipientContainingTheFollowingValues(Map<String, String> requestData) {
        Map<String, Object> correspondenceRequest = new HashMap<>();
        Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
        if ("DC-EB".equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())) {
            String tempDcCaseId = API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId();
            Map<String, String> tempCaseConsumerId = new HashMap<>();
            tempCaseConsumerId.put("caseId", tempDcCaseId);
            caseConsumerId = tempCaseConsumerId;
            generalSave.get().put("caseConsumerId", tempCaseConsumerId);
        }
        Assert.assertNotNull(caseConsumerId);
        List<String> channels = new ArrayList<>();
        //filter for valid keywords for correspondence level
        for (String requestInfo : requestData.keySet()) {
            switch (BrowserUtils.filterForLettersOnly(requestInfo)) {
                case "channel":
                    if (requestData.get(requestInfo).contains(",")) {
                        String[] channelArray = requestData.get(requestInfo).split(",");
                        for (String ch : channelArray) {
                            channels.add(ch);
                        }
                    } else {
                        channels.add(requestData.get(requestInfo));
                        generalSave.get().put("ReturnedNotificationConsumerIdChannel" + Hooks.nameAndTags.get(), requestData.get(requestInfo));
                    }
                    break;
                case "emailAddress":
                    //do nothing here
                    break;
                case "consumerId":
                    //do nothing here
                    break;
                case "textNumber":
                    //do nothing here
                    break;
                case "recipients":
                    //do nothing here
                    break;
                case "zip":
                    //do nothing here
                    break;
                case "mail":
                    //do nothing here
                    break;
                case "externalRefType":
                    //do nothing here
                    break;
                case "externalRefId":
                    //do nothing here
                    break;
                case "digitallyRead":
                    generalSave.get().put("digitallyRead" + Hooks.nameAndTags.get(), requestData.get(requestInfo));
                    break;
                case "externalCaseIdCHIP":
                    String externalCaseIdCHIP = requestData.get(requestInfo);
                    if ("random".equalsIgnoreCase(requestData.get(requestInfo))) {
                        externalCaseIdCHIP = RandomStringUtils.random(30, true, true);
                    }
                    generalSave.get().put("externalCaseIdCHIP" + Hooks.nameAndTags.get(), externalCaseIdCHIP);
                    break;
                case "externalConsumerIdCHIP":
                    String externalConsumerIdCHIP = requestData.get(requestInfo);
                    if ("random".equalsIgnoreCase(requestData.get(requestInfo))) {
                        externalConsumerIdCHIP = RandomStringUtils.random(30, true, true);
                    }
                    generalSave.get().put("externalConsumerIdCHIP" + Hooks.nameAndTags.get(), externalConsumerIdCHIP);
                    break;
                case "externalCaseIdMedicaid":
                    String externalCaseIdMedicaid = requestData.get(requestInfo);
                    if ("random".equalsIgnoreCase(requestData.get(requestInfo))) {
                        externalCaseIdMedicaid = RandomStringUtils.random(30, true, true);
                    }
                    generalSave.get().put("externalCaseIdMedicaid" + Hooks.nameAndTags.get(), externalCaseIdMedicaid);
                    break;
                case "externalConsumerIdMedicaid":
                    String externalConsumerIdMedicaid = requestData.get(requestInfo);
                    if ("random".equalsIgnoreCase(requestData.get(requestInfo))) {
                        externalConsumerIdMedicaid = RandomStringUtils.random(30, true, true);
                    }
                    generalSave.get().put("externalConsumerIdMedicaid" + Hooks.nameAndTags.get(), externalConsumerIdMedicaid);
                    break;
                case "correspondenceDefinitionMMSCode":
                    correspondenceRequest.put(requestInfo, requestData.get(requestInfo));
                    break;
                case "language":
                    correspondenceRequest.put(requestInfo, requestData.get(requestInfo));
                    break;
                case "caseId":
                    if ("previouslyCreated".equalsIgnoreCase(requestData.get("caseId"))) {
                        correspondenceRequest.put(requestInfo, caseConsumerId.get("caseId"));
                    } else if ("DCEBcaseid".equalsIgnoreCase(requestData.get("caseId"))) {
                        String tempDcCaseId = API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId();
                        correspondenceRequest.put(requestInfo, tempDcCaseId);
                        Map<String, String> tempCaseConsumerId = new HashMap<>();
                        tempCaseConsumerId.put("caseId", tempDcCaseId);
                        generalSave.get().put("caseConsumerId", tempCaseConsumerId);
                    } else {
                        correspondenceRequest.put(requestInfo, requestData.get("caseId"));
                    }
                    break;
                case "regardingConsumerId":
                    if (requestData.get(requestInfo).equalsIgnoreCase("consumerNotOnACase")) {
                        correspondenceRequest.put(requestInfo, BrowserUtils.validNumberFilter(World.generalSave.get().get("ConsumerId").toString()));
                    } else if ("DCEBconsumerid".equalsIgnoreCase(requestData.get("regardingConsumerId"))) {
                        correspondenceRequest.put(requestInfo, API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getExternalConsumerId());
                    } else {
                        correspondenceRequest.put(requestInfo, caseConsumerId.get("consumerId"));
                    }
                    break;
                default:
                    Assert.fail("no matching cases for - \"" + requestInfo + "\"");

            }
        }
        request.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().buildCorrespondenceRequest(correspondenceRequest));

        //build add recipient from caseCorrespondence
        if (requestData.containsKey("recipients") && requestData.get("recipients").equalsIgnoreCase("random")) {
            if (!requestData.containsKey("caseId")) {
                synchronized (request) {
                    request.set(stepDefs.get().addRecipientToObRequestFromConsumerNumber(BrowserUtils.validNumberFilter(World.generalSave.get().get("ConsumerId").toString()), channels, requestData, request.get()));

                }
            } else {
                request.set(stepDefs.get().addRecipientToObRequestFromCase(String.valueOf(correspondenceRequest.get("caseId")), channels, requestData, request.get()));
            }
        /* else {
            request.get() = stepDefs.get().addRecipientToObRequestFromCase(caseConsumerId.get("caseId"), channels, requestData, request.get());
        }*/

        }
        generalSave.get().put("ObFullRequestJson", request.get());
    }

    @When("I send the request {string} to create an Outbound Correspondence")
    public void iSendTheRequestToCreateAnOutboundCorrespondence(String request) {
        JsonObject requestJson = new JsonObject();
        if ("previouslyCreated".equalsIgnoreCase(request)) {
            requestJson = (JsonObject) generalSave.get().get("ObFullRequestJson");
        } else {
            Assert.fail("other sources for request.get() not found");
        }
        String cid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOBCorrespondence(requestJson).getString("data.id");
        Assert.assertNotNull(cid);
        generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), cid);
        System.out.println("OB created, cid - \"" + cid + "\"");
        String nid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid).getString("recipients[0].notifications[0].notificationId");
        generalSave.get().put("nid", nid);
    }


    @When("I send the request {string} to create an Outbound Correspondence and save traceId")
    public void iSendTheRequestToCreateAnOutboundCorrespondenceAndSaveTraceId(String request) {
        JsonObject requestJson = new JsonObject();
        if ("previouslyCreated".equalsIgnoreCase(request)) {
            requestJson = (JsonObject) generalSave.get().get("ObFullRequestJson");
        } else {
            Assert.fail("other sources for request.get() not found");
        }

        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createCorrespondence(requestJson, ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences");
        String cid = response.getString("data.id");
        Assert.assertNotNull(cid);
        generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), cid);
        System.out.println("OB created, cid - \"" + cid + "\"");
    }

    @And("the Email Notification that was created is sent to Send Grid End Point")
    public void theEmailNotificationThatWasCreatedIsSentToSendGridEndPoint() {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        String nid = response.getString("recipients[0].notifications.notificationId[0]");
        JsonPath sendGridResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().exportEmailSendGrid(BrowserUtils.validNumberFilter(nid), "2493");
        generalSave.get().put("sendGridResponse", sendGridResponse);
    }

    @Then("I should see that Send Grid Response was successful")
    public void iShouldSeeThatSendGridResponseWasSuccessful() {
        JsonPath response = (JsonPath) generalSave.get().get("sendGridResponse");
        Assert.assertEquals(response.getString("status"), "SUCCESS");
    }

    @And("I should see that the Email Notification was updated to {string} Successfully")
    public void iShouldSeeThatTheEmailNotificationWasUpdatedToSuccessfully(String status) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        if ("Error".equalsIgnoreCase(status)) {
            Assert.assertEquals(BrowserUtils.filterTextFor(response.getString("recipients[0].notifications.notificationStatus.status")), "Error");
            Assert.assertEquals(BrowserUtils.filterTextFor(response.getString("recipients[0].notifications.notificationStatus.errorDetail")), "Error exporting correspondence");
            Assert.assertEquals(BrowserUtils.validNumberFilter(String.valueOf(response.getString("recipients[0].notifications.updatedBy"))), "2493");
        } else {
            Assert.assertEquals(BrowserUtils.filterTextFor(response.getString("recipients[0].notifications.notificationStatus.status")), "Exported");
            Assert.assertEquals(BrowserUtils.validNumberFilter(String.valueOf(response.getString("recipients[0].notifications.updatedBy"))), "2493");
        }

    }

    @And("I should see that the Mail Notification was updated to {string} Successfully")
    public void iShouldSeeThatTheMailNotificationWasUpdatedToSuccessfully(String status) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID")));
        if ("Error".equalsIgnoreCase(status)) {
            Assert.assertEquals(BrowserUtils.filterTextFor(response.getString("recipients[0].notifications.notificationStatus.status")), "Error");
            Assert.assertEquals(BrowserUtils.filterTextFor(response.getString("recipients[0].notifications.notificationStatus.errorDetail")), "Error exporting correspondence");
            Assert.assertEquals(BrowserUtils.validNumberFilter(String.valueOf(response.getString("recipients[0].notifications.updatedBy"))), "2493");
        } else {
            Assert.assertEquals(BrowserUtils.filterTextFor(response.getString("recipients[0].notifications.notificationStatus.status")), "Exported");
            Assert.assertEquals(BrowserUtils.validNumberFilter(String.valueOf(response.getString("recipients[0].notifications.updatedBy"))), "2493");
        }

    }

    @And("I validate warning message for missing {string} for external OB links")
    public void IvalidatewarningmessageformissingexternalOBlinks(String value, Map<String, String> dataTable) {
        String type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType();
        Map<String, String> caseConsumerId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId();
        JsonArray exlinks = new JsonArray();
        JsonObject externallinks = new JsonObject();
        String extlinks[] = dataTable.get("externallink").split(",");

        if (extlinks[0].equalsIgnoreCase("empty"))
            externallinks.addProperty("externalRefType", "");
        else
            externallinks.addProperty("externalRefType", extlinks[0]);
        if (extlinks[1].equalsIgnoreCase("empty"))
            externallinks.addProperty("externalRefId", "");
        else
            externallinks.addProperty("externalRefId", extlinks[1]);
        exlinks.add(externallinks);

        JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceNoRecip(type, caseConsumerId, null, null, exlinks);


        if (value.equalsIgnoreCase("externalRefId"))
            Assert.assertTrue(resp.getString("errors[0].message").contains("externalLinks[0].externalRefId can not be null or empty"));
        if (value.equalsIgnoreCase("externalRefType"))
            Assert.assertTrue(resp.getString("errors[0].message").contains("externalLinks[0].externalRefType can not be null or empty"));
        if (value.equalsIgnoreCase("externalRefId_and_externalRefType")) {
            Assert.assertTrue(resp.getString("errors[0].message").contains("externalLinks[0].externalRefType can not be null or empty"));
            Assert.assertTrue(resp.getString("errors[1].message").contains("externalLinks[0].externalRefId can not be null or empty"));
        }
    }

    @And("I verify that the Email message was received from Send Grid for {string} address")
    public void iVerifyThatTheEmailMessageWasReceivedFromSendGridForAddress(String emailAddress) {
        Message message = stepDefs.get().retrieveEmailByRecipientName(APIUtilitiesForUIScenarios.consumerFirstName.get(), APIUtilitiesForUIScenarios.consumerLastName.get(), emailAddress);
        Assert.assertNotNull(message, "message not retrieved");
        generalSave.get().put("emailMessage", message);
        JsonPath obSettingsResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObSettings(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId());
        try {
            //verify recipient names
            Assert.assertTrue(message.getAllRecipients()[0].toString().replace("<" + String.valueOf(generalSave.get().get("emailAddress")) + ">", "").trim().equalsIgnoreCase(APIUtilitiesForUIScenarios.consumerFirstName.get() + " " + APIUtilitiesForUIScenarios.consumerLastName.get()));
            //verify sender name
            Assert.assertEquals(obSettingsResponse.getString("correspondenceSettingsResponse.outboundSMTPDetails[0].senderEmailName"), ((IMAPMessage) message).getSender().toString());
            //verify reply to email address
            Assert.assertEquals(obSettingsResponse.getString("correspondenceSettingsResponse.outboundSMTPDetails[0].senderEmailName"), ((IMAPMessage) message).getSender().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @And("I verify that the Email message not received from Send Grid for {string}")
    public void iVerifyThatTheEmailMessageNotReceivedFromSendGridFor(String emailAddress) {
        Assert.assertFalse(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().ismaerskMessageFound(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().fetchEmails(emailAddress)), "maersk Email found");
    }

    @And("I clear all old messages from {string} inbox")
    public void iClearAllOldMessagesFromInbox(String email) {
        generalSave.get().put("emailAddress", email);
        stepDefs.get().clearOldEmails(email);
    }

    @And("I verify the sender email address is taken from Outbound Correspondence Settings")
    public void iVerifyTheSenderEmailAddressIsTakenFromOutboundCorrespondenceSettings() {
        stepDefs.get().verifySenderEmailFromSettings();
    }

    @And("I verify the sender email address is taken from Outbound Correspondence Channel Definition")
    public void iVerifyTheSenderEmailAddressIsTakenFromOutboundCorrespondenceChannelDefinition() {
        stepDefs.get().verifySenderEmailFromChannelDef();
    }

    @Then("I should see that Send Grid Response has returned an Error")
    public void iShouldSeeThatSendGridResponseHasReturnedAnError() {
        JsonPath response = (JsonPath) generalSave.get().get("sendGridResponse");
        Assert.assertEquals(response.getString("status"), "ERROR");
    }

    @Then("I should see following values on the {string} Outbound Correspondence record")
    public void iShouldSeeFollowingValuesOnTheOutboundCorrespondenceRecord(String cid, Map<String, String> data) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        }
        BrowserUtils.waitFor(10);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        String nid = response.getString("recipients[0].notifications[0].notificationId");
        Assert.assertNotNull(nid);
        generalSave.get().put("nid" + Hooks.nameAndTags.get(), nid);
        stepDefs.get().verifyZipCodeInRecipientInfo(response, data);
    }

    @And("I should see the following values in the {string} Outbound Correspondence letter data record")
    public void iShouldSeeTheFollowingValuesInTheOutboundCorrespondenceLetterDataRecord(String nid, Map<String, String> data) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        stepDefs.get().verifyZipCodeInLetterData(response, data);
    }

    @Then("I should see the {string} destination is visible on the Outbound Correspondence Search Results")
    public void iShouldSeeTheDestinationIsVisibleOnTheOutboundCorrespondenceSearchResults(String channel) {
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        switch (channel.toLowerCase()) {
            case "mail":
                stepDefs.get().verifyMailAddressObSearchResultsInUI(response);
                break;
            default:
                Assert.fail("no matching cases");
        }
    }

    @And("I should see the {string} destination is visible on the Outbound Correspondence Details Page")
    public void iShouldSeeTheDestinationIsVisibleOnTheOutboundCorrespondenceDetailsPage(String channel) {
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        switch (channel.toLowerCase()) {
            case "mail":
                stepDefs.get().verifyMailAddressObDetailsPageInUI(response);
                break;
            default:
                Assert.fail("no matching cases");
        }
    }

    @And("The Email Notification that was created is sent to SendGridEvent End Point")
    public void theEmailNotificationThatWasCreatedIsSentToSendGridEventEndPoint(Map<String, String> requestData) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        String nid = response.getString("recipients[0].notifications.notificationId");
        JsonPath sendGridEventResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().processEmailSendGridEvent(requestData, BrowserUtils.validNumberFilter(nid));
        generalSave.get().put("sendGridEventResponse", sendGridEventResponse);
    }

    @And("The Text Notification that was created is sent to TwilioEvent End Point")
    public void theTextNotificationThatWasCreatedIsSentToTwilioEventEndPoint(Map<String, String> requestData) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        String nid = response.getString("recipients[0].notifications.notificationId");
        JsonPath twilioeventResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().processTextTwilioEvent(requestData, BrowserUtils.validNumberFilter(nid));
        generalSave.get().put("TwilioEventResponse", twilioeventResponse);
    }

    @And("I receive an update to the {string} Outbound Correspondence Notification that it is read to be viewed")
    public void iReceiveAnUpdateToTheOutboundCorrespondenceNotificationThatItIsReadToBeViewed(String nid) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            nid = String.valueOf(World.generalSave.get().get("nid"));
        }
        String ecmsDocumentId = String.valueOf(generalSave.get().get("ecmsDocumentId"));
        Assert.assertNotNull(ecmsDocumentId);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().patchNotificationObjectReceived(nid, ecmsDocumentId);
        Assert.assertEquals(response.getString("status").trim().toUpperCase(), "SUCCESS");
    }

    @When("I receive an update to the {string} Outbound Correspondence Notification that it is read to be viewed with {string} notification Id")
    public void iReceiveAnUpdateToTheOutboundCorrespondenceNotificationThatItIsReadToBeViewedWithNotificationId(String cid, String nid) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            nid = String.valueOf(World.generalSave.get().get("nid"));
        }
        String ecmsDocumentId = "1111";
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().patchInvalidNotificationObjectReceived(nid, ecmsDocumentId);
        generalSave.get().put("patchInvalidNotificationObjectReceivedResponse" + Hooks.nameAndTags.get(), response);
    }

    @Then("I should see Outbound Correspondence Notification patch endpoint returns {string} message")
    public void iShouldSeeOutboundCorrespondenceNotificationPatchEndpointReturnsMessage(String message) {
        JsonPath response = (JsonPath) generalSave.get().get("patchInvalidNotificationObjectReceivedResponse" + Hooks.nameAndTags.get());
        System.out.println(response.prettyPrint());
        if ("Notification ID not found".equalsIgnoreCase(message)) {
            Assert.assertEquals(response.getString("message"), message);
        } else {
            Assert.assertEquals(response.getString("errors[0].message"), message);
        }
    }

    @Then("I verify the header of the correspondence image as {string}")
    public void iVerifyTheHeaderOfTheCorrespondenceImageAs(String headerText) {
        viewOutboundCorrespondenceStepDefs.verifyHeaderOfViewImage(headerText);
    }

    @Then("I verify body of the correspondence image contains {string}")
    public void iVerifyBodyOfTheCorrespondenceImageAs(String bodyText) {
        viewOutboundCorrespondenceStepDefs.verifyBodyOfHTMLViewImageContains(bodyText);
    }

    @And("I switch to opened window")
    public void iSwitchToOpenedWindow() {
        viewOutboundCorrespondenceStepDefs.switchToOpenedWindow(currentWindowHandle.get());
    }

    @And("I retrieve a phone number that will receive a sms text from {string}")
    public void iRetrieveAPhoneNumberThatWillReceiveASmsTextFrom(String recipient) {
        switch (recipient) {
            case "receiveSMS":
                generalSave.get().put("receiveSMS", UIAutoUitilities.getTextPhoneNumberReceiveSMS());
                break;
            default:
                Assert.fail("no matching cases");
        }
    }

    @And("I should see the text message was received by {string} from sender phone number {string}")
    public void iShouldSeeTheTextMessageWasReceivedByFromSenderPhoneNumber(String recipient, String phoneNumber) {
        if ("receiveSMS".equalsIgnoreCase(phoneNumber)) {
            phoneNumber = String.valueOf(generalSave.get().get("receiveSMS"));
            ;
        }
        boolean found = false;
        switch (recipient) {
            case "receiveSMS":
                for (int retries = 7; retries > 0; retries--) {
                    if (stepDefs.get().verifyReceivedSMSText(phoneNumber)) {
                        found = true;
                        break;
                    }
                }
                Assert.assertTrue(found);
                break;
            default:
                Assert.fail("no matching cases");
        }
    }

    @And("I should see the text message is not received by {string}")
    public void iShouldSeeTheTextMessageIsNotReceivedByForPhoneNumber(String recipient) {
        boolean found = false;
        switch (recipient) {
            case "receiveSMS":
                for (int retries = 7; retries > 0; retries--) {
                    if (stepDefs.get().verifyReceivedSMSText(recipient)) {
                        found = true;
                        break;
                    }
                }
                Assert.assertFalse(found, "SMS found for phone Number " + recipient);
                break;
            default:
                Assert.fail("no matching cases");
        }
    }

    @And("the Text Notification that was {string} is sent to Twilio end point")
    public void theTextNotificationThatWasCreatedIsSentToTwilioEndPoint(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        }
        JsonPath obDetails = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        generalSave.get().put("twilioObDetails", obDetails);
        String actualRecipientPhoneNumber = obDetails.getString("recipients[0].recipientInfo.textNumber");
        String nid = obDetails.getString("recipients[0].notifications[0].notificationId");
        generalSave.get().put("nidTwilio", nid);
        BrowserUtils.waitFor(5);//do not remove
        JsonPath twilioResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().sendTextTwilio(actualRecipientPhoneNumber, nid); //will fail if phone number is not the one retrieved from retrieveSMS
        generalSave.get().put("twilioResponse", twilioResponse);
    }

    @Then("I should see that Twilio Response was successful")
    public void iShouldSeeThatTwilioResponseWasSuccessful() {
        JsonPath twilioResponse = (JsonPath) generalSave.get().get("twilioResponse");
        Assert.assertEquals(twilioResponse.getString("status"), "SUCCESS");
        Assert.assertEquals(twilioResponse.getString("message"), "Correspondence SMS exported successfully");
    }

    @And("I should see that the text Notification was updated to {string} Successfully")
    public void iShouldSeeThatTheTextNotificationWasUpdatedToSuccessfully(String status) {
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath obDetails = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        Map<String, String> notificationStatus = obDetails.get("recipients[0].notifications[0].notificationStatus");
        Assert.assertEquals(notificationStatus.get("status"), status);
        Assert.assertEquals(notificationStatus.get("changedBy"), "2495");
        Assert.assertNotEquals(obDetails.getString("recipients[0].notifications[0].createdBy"), notificationStatus.get("statusChangedDate"), "statusChangedDate not updated");
        Assert.assertNotEquals(obDetails.getString("recipients[0].notifications[0].updatedBy"), obDetails.getString("recipients[0].notifications[0].createdBy"), "updatedBy not updated");
        Assert.assertEquals(obDetails.get("recipients[0].notifications[0].updatedBy"), "2495");
        Assert.assertEquals(obDetails.get("recipients[0].notifications[0].updatedDatetime"), notificationStatus.get("statusChangedDate"));
        if ("Error".equalsIgnoreCase(status)) {
            Assert.assertEquals(notificationStatus.get("status"), "Error");
            Assert.assertEquals(notificationStatus.get("statusReason"), "Export Error");
            JsonPath twilioResponse = (JsonPath) generalSave.get().get("twilioResponse");
            Assert.assertEquals(notificationStatus.get("errorDetail"), twilioResponse.getString("errorDetails"));
        }
        Assert.fail("temporary fail because text must be verified manually | cid - " + cid);//temp failing all text bc must be verified manually
    }

    @And("I verify that the text message was received from Twilio")
    public void iVerifyThatTheTextMessageWasReceivedFromTwilio() {
        Driver.getDriver().findElement(By.xpath("//*[contains(text(),'Update Messages')]")).click();
    }

    @Then("I should see that Twilio Response has returned an Error")
    public void iShouldSeeThatTwilioResponseHasReturnedAnError() {
        JsonPath twilioResponse = (JsonPath) generalSave.get().get("twilioResponse");
        Assert.assertEquals(twilioResponse.getString("status"), "FAILURE");
        Assert.assertEquals(twilioResponse.getString("message"), "Error exporting correspondence");
    }

    @And("I should see the status {string} has cascaded to the Correspondence level")
    public void iShouldSeeTheStatusHasCascadedTheTheCorrespondenceLevel(String status) {
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath obDetails = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        Assert.assertEquals(obDetails.getString("status"), status);
    }

    @And("I should see the status reason {string} has cascaded the the Correspondence level")
    public void iShouldSeeTheStatusRreasonHasCascadedTheTheCorrespondenceLevel(String statusReason) {
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath obDetails = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        Assert.assertEquals(obDetails.getString("statusMessage"), statusReason);
    }

    @And("I should see the Twilio notification is viewed as {string}")
    public void iShouldSeeTheTwilioNotificationIsViewedAs(String expectedText) {
        viewOutboundCorrespondenceStepDefs.verifyTwilioTemplate(expectedText);
    }

    @And("I verify I get {int} error while viewing the Twilio Template")
    public void iVerifyIGetErrorWhileViewingTheTwilioTemplate(int statusCode) {
        viewOutboundCorrespondenceStepDefs.verifyErrorCodeOfRetrieveTemplateEndPoint("Text", statusCode);
    }

    @And("I initiate Case Correspondence API")
    public void i_initiate_Case_Correspondence_API(Map<String, String> data) {
        inboundDocumentTaskStepDefs.get().getCaseCorrespondenceAPI(data);
    }

    @And("I retrieve Website pin for {string} caseId")
    public void i_retrieve_websitepin_for_caseid(String caseid) {
        if ("DCEBcaseid".equalsIgnoreCase(caseid)) {
            caseid = API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId();
        }
        inboundDocumentTaskStepDefs.get().retrievewebsitepin(caseid);
    }

    @And("I initiate body data source api")
    public void i_initiate_body_data_Source_api(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().retrieveBodyDataSourceByType(dataTable);
    }

    @And("I validate body data source api")
    public void i_validate_body_data_Source_api(Map<String, String> dataTable) {
        inboundDocumentTaskStepDefs.get().validateBodyDataSource(dataTable);
    }

    @Given("I initiate get benefit status for consumer id {string}")
    public void initiatedGetBenefitStatusByConsumerID(String consumerId) {
        inboundDocumentTaskStepDefs.get().retrievebenefitstatus(consumerId);
    }

    @Then("I update following data in consumer actions for consumerId {string}")
    public void I_update_latest_consumer_actions_by_consumerId_with_data(String consumerId, Map<String, String> data) {
        inboundDocumentTaskStepDefs.get().updatebenefitstatus(consumerId, data);
    }

    @And("I receive an update to the {string} Outbound Correspondence Notification for digitallyRead as {string}")
    public void iReceiveAnUpdateToTheOutboundCorrespondenceNotificationHasBeenViewed(String nid, String digitallyRead) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            nid = String.valueOf(World.generalSave.get().get("nid"));
        }
        boolean read = Boolean.parseBoolean(digitallyRead);
        generalSave.get().put("digitallyRead" + Hooks.nameAndTags.get(), digitallyRead);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().patNotificationIsViewed(nid, read);
        Assert.assertEquals(response.getString("status").trim().toUpperCase(), "SUCCESS");
    }

    @When("I receive an update to the {string} Outbound Correspondence Notification that it been viewed with {string} notification Id")
    public void iReceiveAnUpdateToTheOutboundCorrespondenceNotificationThatItBeenViewedWithNotificationId(String cid, String nid) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            nid = String.valueOf(World.generalSave.get().get("nid"));
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().patchInvalidNotificationViewed(nid, "true");
        generalSave.get().put("patchInvalidNotificationObjectReceivedResponse" + Hooks.nameAndTags.get(), response);
    }

    @Then("I should see that the {string} end point is showing the digitallyRead for the {string} Outbound Correspondence Notification")
    public void iShouldSeeThatTheEndPointIsShowingTheDigitallyReadForTheOutboundCorrespondenceNotification(String endPoint, String nid) {
        switch (endPoint) {
            case "/searchcorrespondence":
                stepDefs.get().validateSearchCorrespondenceDigitallyRead(nid);
                break;
            case "/outboundcorrespondence":
                stepDefs.get().validateGlobalSearchCorrespondenceDigitallyRead(nid);
                break;
            case "/correspondences/{correspondenceid}/notifications":
                stepDefs.get().validateGetNotificationsCorrespondenceDigitallyRead(nid);
                break;
            case "/correspondences/{correspondenceid}":
                stepDefs.get().validateGetCorrespondenceDigitallyRead(nid);
                break;
        }
    }

    @Then("I should see that the {string} end point is showing the externalIds for the {string} Outbound Correspondence Notification")
    public void iShouldSeeThatTheEndPointIsShowingTheExternalIdsForTheOutboundCorrespondenceNotification(String endPoint, String nid) {
        switch (endPoint) {
            case "/searchcorrespondence":
                stepDefs.get().validateSearchCorrespondenceExternalids(nid);
                break;
            case "/outboundcorrespondence":
                stepDefs.get().validateGlobalSearchCorrespondenceExternalids(nid);
                break;
            case "/correspondences/{correspondenceid}/notifications":
                stepDefs.get().validateGetNotificationsCorrespondenceExternalids(nid);
                break;
            case "/correspondences/{correspondenceid}":
                if ("newlyCreatedNotificationAndRecipient".equalsIgnoreCase(nid)) {
                    stepDefs.get().validateGetCorrespondenceExternalids();
                } else if ("newlyProvisionedRecipient".equalsIgnoreCase(nid)) {
                    stepDefs.get().validateGetCorrespondenceExternalidsOutline(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName().equalsIgnoreCase("DC-EB"));
                } else {
                    stepDefs.get().validateGetCorrespondenceExternalids(nid);
                }
                break;
        }
    }

    @Then("I should see that the {string} end point is showing the externalIds for the {string} Outbound Correspondence Notification for each recipient")
    public void iShouldSeeThatTheEndPointIsShowingTheExternalIdsForTheOutboundCorrespondenceNotificationForEachRecipient(String endPoint, String nid) {
        switch (endPoint) {
            case "/searchcorrespondence":
                stepDefs.get().validateSearchCorrespondenceExternalids(nid);
                stepDefs.get().validateSearchCorrespondenceExternalids(nid, Integer.parseInt(String.valueOf(generalSave.get().get("externalIdsSecondRecipient" + Hooks.nameAndTags.get()))));
                break;
            case "/outboundcorrespondence":
                stepDefs.get().validateGlobalSearchCorrespondenceExternalids(nid);
                stepDefs.get().validateGlobalSearchCorrespondenceExternalids(nid, Integer.parseInt(String.valueOf(generalSave.get().get("externalIdsSecondRecipient" + Hooks.nameAndTags.get()))));
                break;
            case "/correspondences/{correspondenceid}/notifications":
                stepDefs.get().validateGetNotificationsCorrespondenceExternalids(nid);
                stepDefs.get().validateGetNotificationsCorrespondenceExternalids(nid, Integer.parseInt(String.valueOf(generalSave.get().get("externalIdsSecondRecipient" + Hooks.nameAndTags.get()))));
                break;
            case "/correspondences/{correspondenceid}":
                stepDefs.get().validateGetCorrespondenceExternalids(nid);
                stepDefs.get().validateGetCorrespondenceExternalids(nid, Integer.parseInt(String.valueOf(generalSave.get().get("externalIdsSecondRecipient" + Hooks.nameAndTags.get()))));
                break;
        }
    }

    @And("I create a Notification with the {string} new Outbound Correspondence with the following values")
    public void iCreateANotificationWithTheNewOutboundCorrespondenceWithTheFollowingValues(String cid, Map<String, String> data) {
        String nid = String.valueOf(World.generalSave.get().get("nid"));
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        }
        stepDefs.get().createNotification(cid, nid, data);
    }

    @And("I save the notification Id from the recently created Outbound Correspondence")
    public void iSaveTheNotificationIdFromTheRecentlyCreatedOutboundCorrespondence() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath getCID = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        String notificationId = getCID.getString("recipients[0].notifications[0].notificationId");
        generalSave.get().put("nid", notificationId);
    }

    @And("I get the notification {string} using order service")
    public void iGetTheNotificationUsingOrderService(String notificationId) {
        switch (notificationId.toUpperCase()) {
            case "PREVIOUSLY_CREATED":
                notificationId = generalSave.get().get("notificationId").toString();
                break;
            case "LOWER_THAN_0":
                notificationId = String.valueOf(randomNumberBetweenTwoNumbers(-10000, 0));
                break;
            case "INVALID_GREATER_THAN_0":
                notificationId = String.valueOf(randomNumberBetweenTwoNumbers(100000, 9999999));
                break;
            default:
                Assert.fail("No matching cases found");
        }
        generalSave.get().replace("notificationId", notificationId);
        stepDefs.get().getNotificationFromOrderService(notificationId);
    }

    @And("I verify response is Base64 encoded")
    public void iVerifyResponseIsBaseEncoded() {
        stepDefs.get().verifyGetNotificationResponseIsBase64Encoded();
    }

    @And("I verify error message as {string} for get notification API in Order Service")
    public void iVerifyErrorMessageAsForGetNotificationAPIInOrderService(String errorMessage) {
        stepDefs.get().verifyGetNotificationFromOrderServiceErrorMessage(errorMessage);
    }

    @And("I verify response is Base64 encoded as {string}")
    public void iVerifyResponseIsBaseEncodedAs(String expectedString) {
        stepDefs.get().verifyDecodedNotificationString(expectedString);
    }

    @Then("I should see the newly created notification is attached to the {string} recipient record")
    public void iShouldSeeTheNewlyCreatedNotificationIsAttachedToTheSameRecipientRecordAttachedToTheNotification(String nid) {
        if ("newlyCreated".equalsIgnoreCase(nid)) {
            stepDefs.get().verifyNewRecipientRecordCreated();
        } else if ("previouslyCreatedNowUpdated".equalsIgnoreCase(nid)) {
            stepDefs.get().verifyOldRecipientRecordUpdated();
        } else if ("exactSamePreviouslyCreated".equalsIgnoreCase(nid)) {
            stepDefs.get().verifyOldRecipientRecordStayedSame();
        }
    }

    @Then("I should see the option to add a new Notification for the recipient titled {string}")
    public void iShouldSeeTheOptionToAddANewNotificationForTheRecipientTitled(String button) {
        browserUtils.get().waitForVisibility(viewOutboundCorrespondenceDetailsPage.addNotificationButton, 10);
        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.addNotificationButton.isDisplayed());
    }

    @Then("I validate Add Notification button is {string}")
    public void ivalidateAddANewNotificationButton(String button) {
        if (button.equalsIgnoreCase("not displayed")) {
            try {
                browserUtils.get().waitForVisibility(viewOutboundCorrespondenceDetailsPage.addNotificationButton, 10);
                Assert.assertFalse(true, "Add Notification button displayed");
            } catch (Exception e) {
                Assert.assertTrue(true, "Add Notification button not displayed");
            }
        } else if (button.contains("displayed")) {
            String count = button.split(" ")[0];
            browserUtils.get().waitForVisibility(viewOutboundCorrespondenceDetailsPage.addNotificationButton, 10);
            Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.addNotificationButtoncount.size() == Integer.parseInt(count));
        } else if (button.equalsIgnoreCase("clicked")) {
            browserUtils.get().waitForVisibility(viewOutboundCorrespondenceDetailsPage.addNotificationButton, 10);
            viewOutboundCorrespondenceDetailsPage.addNotificationButton.click();
        }
    }

    @And("I should see the Random Destination information from the newly added Notification for {string} channel is present in the letter data that is generated")
    public void iShouldSeeTheRandomDestinationInformationFromTheNewlyAddedNotificationForChannelIsPresentInTheLetterDataThatIsGenerated(String channel) {
        //this method only used for newly created recipient records from add notification endpoint
        BrowserUtils.waitFor(3);
        String nid = String.valueOf(generalSave.get().get("newNid" + Hooks.nameAndTags.get()));
        String recipientId = String.valueOf(generalSave.get().get("NewlyCreatedCorrespondenceRecipientId" + Hooks.nameAndTags.get()));
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath actualJson = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        Assert.assertNotNull(nid);
        JsonPath letterDataResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        Map<String, String> expectedRecipientInformation = stepDefs.get().getRecipientInformation(actualJson, recipientId);
        stepDefs.get().addDestinationInformation(channel, expectedRecipientInformation);
        stepDefs.get().verifyDestinationInformationInLetterDataPostNotification(expectedRecipientInformation, letterDataResponse, channel);
    }

    @Then("I should see that the {string} letter data contains the following values")
    public void iShouldSeeThatTheLetterDataContainsTheFollowingValues(String nid, Map<String, String> requestData) {
        if (requestData.containsKey("externalCaseIdCHIP") || requestData.containsKey("externalConsumerIdCHIP") || requestData.containsKey("externalCaseIdMedicaid") || requestData.containsKey("externalConsumerIdMedicaid")) {
            if ("previouslyCreated".equalsIgnoreCase(nid)) {
                stepDefs.get().verifyLetterDataHasExternalIds(0);
            } else if ("previouslyCreatedSecondRecipient".equalsIgnoreCase(nid)) {
                stepDefs.get().verifyLetterDataHasExternalIds(0);
                stepDefs.get().verifyLetterDataHasExternalIds(1);
            } else if ("newlyCreatedRecipient".equalsIgnoreCase(nid)) {
                stepDefs.get().verifyLetterDataHasExternalIds(3);
            } else if ("newlyProvisionedRecipient".equalsIgnoreCase(nid) && APIClassUtil.getApiClassUtilThreadLocal().getProjectName().equalsIgnoreCase("DC-EB")) {
                stepDefs.get().verifyLetterDataHasExternalIds(5);
            } else if ("newlyProvisionedRecipient".equalsIgnoreCase(nid)) {
                stepDefs.get().verifyLetterDataHasExternalIds(4);
            }
        }
        if (requestData.containsKey("NewNotificationChannel")) {
            iShouldSeeTheRandomDestinationInformationFromTheNewlyAddedNotificationForChannelIsPresentInTheLetterDataThatIsGenerated(requestData.get("NewNotificationChannel"));
        }

    }

    @Then("I store the following values from case correspondence for the previously created {string} for verification purposes")
    public void storeCaseCorrespondenceData(String caseOrConsumerId, List<String> data) {
        JsonPath caseCorrespondenceResponse;
        if(APIClassUtil.getApiClassUtilThreadLocal().getProjectName().equalsIgnoreCase("DC-EB")){
             caseCorrespondenceResponse = stepDefs.get().getCaseCorrespondence(caseOrConsumerId, Arrays.asList("Mail", "Email", "Text"));
        }else{
             caseCorrespondenceResponse = stepDefs.get().getCaseCorrespondence(caseOrConsumerId, Arrays.asList("Mail", "Email", "Fax", "Text"));
        }
        stepDefs.get().storeValuesFromCaseCorrespondence(caseCorrespondenceResponse, data);
    }

    @And("I initiated post languagetemplateobject endpoint")
    public void thePostlanguagetemplateobjectEndPoint(Map<String, String> requestData) {
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().postlanguagetemplateobject(requestData);
    }

    @And("I validate the languagetemplateobject response")
    public void iValidatelanguagetemplateobjectresponse(Map<String, String> requestData) {
        String actualresponse = World.generalSave.get().get("languagetemplateobjectResponse").toString();

        Assert.assertEquals(requestData.get("statuscode"), World.generalSave.get().get("languagetemplateobjectStatuscode").toString());
        Assert.assertTrue(actualresponse.contains(requestData.get("message")));
    }

    @And("I initiated get languagetemplateobject endpoint")
    public void theGetlanguagetemplateobjectEndPoint(Map<String, String> requestData) {

        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getlanguagetemplateobject(requestData.get("languageTemplateId"));

        if (requestData.get("statuscode").equalsIgnoreCase("200")) {
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
            Assert.assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.isEmpty());
        } else if (requestData.get("statuscode").equalsIgnoreCase("404")) {
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 404);
            Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.isEmpty());
        }

    }

    @And("I verify {string} letter data contains mail flag as {string}")
    public void iVerifyLetterDataContainsMailFlagAs(String nid, String mailFlag) {
        if (!mailFlag.equalsIgnoreCase("no letter data")) {
            stepDefs.get().verifyMailFlagForLetterData(nid, mailFlag);
        }
    }
}
