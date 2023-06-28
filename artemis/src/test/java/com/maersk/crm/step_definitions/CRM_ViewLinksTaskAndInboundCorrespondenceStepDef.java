package com.maersk.crm.step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.tm.TMProjectListPage;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.CaseAndContactDetailsPage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import com.maersk.dms.step_definitions.ObtainCorrespondenceItemsFromECMSRequestStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_ViewLinksTaskAndInboundCorrespondenceStepDef extends CRMUtilities implements ApiBase {

    private final String apiBffCorrespondence = ConfigurationReader.getProperty("apiBffCorrespondence");
    private final String postEvent = "/ecms/connectionpoint/sendevent";
    private final String indexDocument = "/ecms/correspondence/inbound/document";

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private apitdu apitdu = new apitdu();
    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    final ThreadLocal<ObtainCorrespondenceItemsFromECMSRequestStepDefs> obtainCorrespondenceItemsFromECMSRequestStepDefs = ThreadLocal.withInitial(ObtainCorrespondenceItemsFromECMSRequestStepDefs::new);


    CRMCreateGeneralTaskPage crmCreateGeneralTaskPage = new CRMCreateGeneralTaskPage();
    TMProjectListPage tmProjectListPage = new TMProjectListPage();
    CaseAndContactDetailsPage correspondence = new CaseAndContactDetailsPage();
    private ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();

    private final String projectNameNJ = "NJ-SBE";
    private final ThreadLocal<String> correspondenceId = ThreadLocal.withInitial(String::new);
    private final String correspondenceName = "Inbound Correspondence";
    private final String correspondenceTypeBLCRM = "maersk Case + Consumer";
    private final String correspondenceTypeNJ = "NJ SBE General";
    private final ThreadLocal<String> correspondenceStatus = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> taskId = ThreadLocal.withInitial(String::new);
    private final String taskName = "Task";
    private final String taskType = "Correspondence";
    private final String taskTypeNJ = "Process Inbound Document";
    private final ThreadLocal<String> taskStatus = ThreadLocal.withInitial(()->"Created");
    private final ThreadLocal<String> statusDate = ThreadLocal.withInitial(String::new);

    @Given("I create a Task of type Inbound Task that is linked to an Inbound Correspondence for {string}")
    public void createTaskInboundCorrespondence(String projectType) throws IOException {
        //Create Inbound Correspondence Document Id
        if (projectType.equals("BLCRM")) {
            correspondenceId.set(obtainCorrespondenceItemsFromECMSRequestStepDefs.get().inboundCorrespondenceInOnbaseForMail(correspondenceTypeBLCRM));
            World.generalSave.get().put("InboundCorrespondenceId", correspondenceId.get());
        } else if (projectType.equals("NJ")) {
            correspondenceId.set(obtainCorrespondenceItemsFromECMSRequestStepDefs.get().inboundCorrespondenceInOnbaseForMail(correspondenceTypeNJ));
            World.generalSave.get().put("InboundCorrespondenceId", correspondenceId.get());
        }
        System.out.println("Inbound Correspondence Created: " + correspondenceId.get());

        //Create Task from the created Inbound Correspondence
        String postEventEndpoint = apiBffCorrespondence + postEvent;

        APIClassUtil sendEventResponse = null;
        if (projectType.equals("BLCRM")) {
            requestParams.set(getInboundCorrespondenceEventParams());
            sendEventResponse = apiAutoUtilities.get().PostCorrespondence(requestParams.get(), postEventEndpoint);
        } else if (projectType.equals("NJ")) {
            requestParams.set(getInboundCorrespondenceEventParamsNJ());
            sendEventResponse = apiAutoUtilities.get().PostCorrespondenceNJ(requestParams.get(), postEventEndpoint, projectNameNJ);
        }

        System.out.println(sendEventResponse.responseString);
        assertTrue(sendEventResponse.responseString.contains("success"));
        assertEquals(sendEventResponse.jsonPathEvaluator.get("status"), "SUCCESS");

        taskId.set(BrowserUtils.validNumberFilter(sendEventResponse.jsonPathEvaluator.get("message").toString().split(":")[1].trim()));
        System.out.println("Task Created: " + taskId.get());

        //Index Document
        String indexDocEndPoint = apiBffCorrespondence + indexDocument;

        requestParams.set(getInboundDocumentIndexingParams());
        APIClassUtil indexingResponse = null;
        if (projectType.equals("BLCRM")) {
            indexingResponse = apiAutoUtilities.get().indexInboundDocument(requestParams.get(), indexDocEndPoint);
        } else if (projectType.equals("NJ")) {
            indexingResponse = apiAutoUtilities.get().indexInboundDocumentNJ(requestParams.get(), indexDocEndPoint, projectNameNJ);
        }

        System.out.println(indexingResponse.responseString);
        assertEquals(indexingResponse.statusCode, 200);
        assertTrue(indexingResponse.responseString.contains("SUCCESS"));
        assertEquals(indexingResponse.jsonPathEvaluator.get("status"), "SUCCESS");
    }

    @And("I search for Inbound Correspondence with the one created")
    public void searchInboundCorrespondence() {
        clearAndFillText(crmCreateGeneralTaskPage.correspondenceIdSearch, correspondenceId.get());
        tmProjectListPage.search.click();
    }

    @And("I click on the Inbound Correspondence Id result")
    public void clickInboundCorrespondenceResult() {
        waitForVisibility(correspondence.correspondenceID, 10);
        World.generalSave.get().put("CID", correspondence.correspondenceID.getText().trim());
        correspondence.correspondenceID.click();
        waitFor(2);
        scrollDownUsingActions(2);
        waitFor(2);
    }

    @When("I click on the Task Id to navigate to the Task details screen")
    public void clickTaskIdInboundCorrespondence() {
        waitForVisibility(crmCreateGeneralTaskPage.taskIdInboundCorrespondence, 10);
        crmCreateGeneralTaskPage.taskIdInboundCorrespondence.click();
        waitFor(2);
        scrollDownUsingActions(2);
        waitFor(2);
    }

    @Then("I should see a Link to the Inbound Correspondence in the Links Section for {string}")
    public void verifyLinkToInboundCorrespondence(String projectType) {
        waitForVisibility(crmCreateGeneralTaskPage.correspondenceLinkNameTaskDetail, 10);
        waitFor(2);

        String inboundCorrespondenceName = crmCreateGeneralTaskPage.correspondenceLinkNameTaskDetail.getText();
        if (projectType.equals("BLCRM")) {
            Assert.assertEquals(inboundCorrespondenceName, correspondenceName);
        } else if (projectType.equals("NJ")) {
            Assert.assertEquals(inboundCorrespondenceName, correspondenceName);
        }

        String taskStatusTaskPage = getUITableText("//table/tbody/tr[@class='MuiTableRow-root']",4,correspondenceName,7);
        synchronized (taskStatus){
            taskStatus.set(apiAutoUtilities.get().getInbDocumentStatus(correspondenceId.get()));
        }
        Assert.assertEquals(taskStatusTaskPage, taskStatus.get());
    }

    @When("I click on the Link to the Inbound Correspondence")
    public void clickInboundCorrespondenceTaskIdPage() {
        waitForVisibility(crmCreateGeneralTaskPage.correspondenceLinkIdTaskDetail, 10);
        crmCreateGeneralTaskPage.correspondenceLinkIdTaskDetail.click();
    }

    @Then("I should see that I am navigated to the Inbound Correspondence Details Screen for {string}")
    public void verifyInboundCorrespondenceDetailsPage(String projectType) {
        waitForVisibility(crmCreateGeneralTaskPage.inboundCorrepondenceId, 10);
        String inboundCorrespondenceId = crmCreateGeneralTaskPage.inboundCorrepondenceId.getText().split(":")[1].trim();
        Assert.assertEquals(inboundCorrespondenceId, correspondenceId.get());

//        String inboundCorrespondenceType = crmCreateGeneralTaskPage.TypeInboundCorrespondence.getText();
        if (projectType.equals("BLCRM")) {
            Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText().trim(), correspondenceTypeBLCRM);
        } else if (projectType.equals("NJ")) {
            Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText().trim(), correspondenceTypeNJ);
        }
    }

    @And("the Link to the Inbound Correspondence should display the following values")
    public void verifyInboundCorrespondenceValues(Map<String, String> valueTypes) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        String currentDate = dtf.format(now);
        waitFor(2);
        correspondenceStatus.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInbDocumentStatus(World.generalSave.get().get("CID").toString()));
        statusDate.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInbDocumentStatusDate(World.generalSave.get().get("CID").toString()));
//        if (statusDate.charAt(0) != 0) {
//            statusDate = "0" + statusDate;
//        }
        SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat after = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = before.parse(statusDate.get());
            statusDate.set(after.format(date));
        } catch (ParseException e) {
            Assert.fail("failed to format status date");
        }
        for (String val : valueTypes.keySet()) {
            switch (val) {
                case "ID":
                    Assert.assertEquals(crmCreateGeneralTaskPage.correspondenceLinkIdTaskDetail.getText(), correspondenceId.get());
                    break;
                case "Name":
                    Assert.assertEquals(crmCreateGeneralTaskPage.correspondenceLinkNameTaskDetail.getText(), correspondenceName);
                    break;
                case "Type-BLCRM":
                    Assert.assertEquals(crmCreateGeneralTaskPage.correspondenceLinkTypeTaskDetail.getText(), correspondenceTypeBLCRM);
                    break;
                case "Type-NJ":
                    Assert.assertEquals(crmCreateGeneralTaskPage.correspondenceLinkTypeTaskDetail.getText(), correspondenceTypeNJ);
                    break;
                case "Status Date":
                    Assert.assertEquals(EventsUtilities.getGenericMatch(crmCreateGeneralTaskPage.correspondenceLinkStatusDateTaskDetail.getText().trim(), "\\d{2}\\/\\d{2}\\/\\d{4}").trim(), statusDate.get().trim());
                    break;
                case "Status":
                    Assert.assertEquals(crmCreateGeneralTaskPage.correspondenceLinkStatusDetail.getText(), correspondenceStatus.get());
                    break;
            }
        }
    }

    @Then("I should see a Link to the Task in the Links Section")
    public void verifyLinkToTask() {
        waitForVisibility(crmCreateGeneralTaskPage.taskIdInboundCorrespondence, 10);
        String taskType = crmCreateGeneralTaskPage.taskIdInboundCorrespondence.getText();
        Assert.assertEquals(taskId.get(), BrowserUtils.validNumberFilter(taskType).trim());
    }

    @And("the Link to the Task should display the following values")
    public void verifyTaskIdValues(Map<String, String> valueTypes) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        String currentDate = dtf.format(now);
        waitFor(2);

        for (String val : valueTypes.keySet()) {
            switch (val) {
                case "ID":
                    Assert.assertEquals(crmCreateGeneralTaskPage.taskIdInboundCorrespondence.getText(), taskId.get());
                    break;
                case "Name":
                    Assert.assertEquals(crmCreateGeneralTaskPage.taskNameInboundCorrespondence.getText(), valueTypes.get("Name"));
                    break;
                case "Type":
                    Assert.assertEquals(crmCreateGeneralTaskPage.taskTypeInboundCorrespondence.getText(), valueTypes.get("Type"));
                    break;
                case "Type-BLCRM":
                    Assert.assertEquals(crmCreateGeneralTaskPage.taskTypeInboundCorrespondence.getText(), taskType);
                    break;
                case "Type-NJ":
                    Assert.assertEquals(crmCreateGeneralTaskPage.taskTypeInboundCorrespondence.getText(), taskTypeNJ);
                    break;
                case "Status Date":
                    Assert.assertEquals(crmCreateGeneralTaskPage.taskStatusDateInboundCorrespondence.getText(), currentDate);
                    break;
                case "Status":
                    Assert.assertEquals(crmCreateGeneralTaskPage.taskStatusInboundCorrespondence.getText(), taskStatus.get());
                    break;
                default:
                    Assert.fail("Type didn't match");
            }
        }
    }

    private JsonObject getInboundCorrespondenceEventParams() {
        JsonObject jsonObject = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/dms/eventCorrespondence.json").jsonElement.getAsJsonObject();

        jsonObject.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentHandle", correspondenceId.get());

        return jsonObject;
    }

    private JsonObject getInboundCorrespondenceEventParamsNJ() {
        JsonObject jsonObject = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/dms/eventCorrespondence.json").jsonElement.getAsJsonObject();
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
        jsonObject.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentHandle", correspondenceId.get());
        jsonObject.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentType", correspondenceTypeNJ);
        jsonObject.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentName", correspondenceTypeNJ + " - " + formatter.format(dt));
        jsonObject.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentTypeName", correspondenceTypeNJ);
        return jsonObject;
    }

    private JsonObject getInboundDocumentIndexingParams() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("status", "INDEXING");
        jsonObject.addProperty("id", Integer.parseInt(correspondenceId.get()));

        return jsonObject;
    }
}
