package com.maersk.dms.step_definitions;

import com.maersk.crm.step_definitions.CRM_GeneralTaskStepDef;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.pages.InboundCorrespondenceSearchPage;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.World.save;

public class InboundCorrespondenceNoteEventStepDefs extends CRMUtilities implements ApiBase  {
    private InboundCorrespondenceSearchPage inboundCorrespondenceSearchPage = new InboundCorrespondenceSearchPage();
    private InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();
    private final static ThreadLocal<String> randomString = ThreadLocal.withInitial(()-> "");
    private final static ThreadLocal<String> eventId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Actions> action = ThreadLocal.withInitial(() -> new Actions(Driver.getDriver()));
    final ThreadLocal<List<String>> listOfTaskIdsBeforeTypeChanged = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> listOfTaskIdsAfterTypeChanged = ThreadLocal.withInitial(ArrayList::new);
//    private final ThreadLocal <BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);

    public void fillInSearchCriteria(String searchCriteria, String searchValue) {
        switch (filterForLettersOnly(searchCriteria).toUpperCase()) {
            case "CASEID":
                String caseId = searchValue;
                if ("previouslyCreated".equalsIgnoreCase(searchValue)) {
                    Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                    caseId = caseConsumerId.get("caseId");
                }
                inboundCorrespondenceSearchPage.caseID.sendKeys(caseId);
                break;
            case "CID":
                if ("fromRequest".equalsIgnoreCase(searchValue)) {
                    //works with an inbound document step created on the fly inboundDocumentTaskStepDefs.uploadInboundDocument(inboundType)
                    inboundCorrespondenceSearchPage.correspondenceId.clear();
                    inboundCorrespondenceSearchPage.correspondenceId.sendKeys(String.valueOf(World.generalSave.get().get("InboundDocumentId")));
                    break;
                } else if ("GETNEWRESCANINBOUNDDOCID".equalsIgnoreCase(searchValue)) { //works with an inbound document step created on the fly inboundDocumentTaskStepDefs.uploadInboundDocument(inboundType)
                    inboundCorrespondenceSearchPage.correspondenceId.sendKeys(String.valueOf(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID")));
                    break;
                } else if ("InboundDocument".equalsIgnoreCase(searchValue)) {
                    inboundCorrespondenceSearchPage.correspondenceId.sendKeys(String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")));
                    break;
                } else if ("SameSetInboundDocument".equalsIgnoreCase(searchValue)) {
                    inboundCorrespondenceSearchPage.correspondenceId.sendKeys(String.valueOf(World.generalSave.get().get("SameSetInboundDocument")));
                    break;
                }
                inboundCorrespondenceSearchPage.correspondenceId.sendKeys(searchValue);
                break;
            case "ADDDEMOPHONENUMBER":
                String ADDDEMOPHONENUMBER = searchValue;
                if("previouslyCreated".equalsIgnoreCase(searchValue)){
                    ADDDEMOPHONENUMBER = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getIbDetailSpecificValue("fromPhoneNumber");
                }
                inboundCorrespondenceSearchPage.addDemoPhoneNumber.sendKeys(ADDDEMOPHONENUMBER);
                break;
            case "FROMEMAIL":
                inboundCorrespondenceSearchPage.fromEmailAddress.sendKeys(searchValue);
                break;
            case "FROMNAME":
                inboundCorrespondenceSearchPage.fromName.sendKeys(searchValue);
                break;
            case "CSETID":
                inboundCorrespondenceSearchPage.correspondenceSetID.sendKeys(searchValue);
                break;
            case "ORIGINALCID":
                inboundCorrespondenceSearchPage.sourceCorrespondenceID.sendKeys(searchValue);
                break;
            case "ORIGINALCSETID":
                inboundCorrespondenceSearchPage.sourceCorrespondenceSetID.sendKeys(searchValue);
                break;
            case "CONSUMERID":
                inboundCorrespondenceSearchPage.consumerID.sendKeys(searchValue);
                break;
            case "CORRESPONDENCETYPE":
                selectOptionFromMultiSelectDropDown(inboundCorrespondenceSearchPage.typeDropDown, searchValue);
                World.generalList.get().add(searchValue);
                break;
            case "CHANNEL":
                selectOptionFromMultiSelectDropDown(inboundCorrespondenceSearchPage.channelDropdown, searchValue);
                World.generalList.get().add(searchValue);
                break;
            case "STATUS":
                selectOptionFromMultiSelectDropDown(inboundCorrespondenceSearchPage.statusDropDown, searchValue);
                World.generalList.get().add(searchValue);
                break;
            case "DATERECEIVED":
                LocalDate datePriorNDays = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String today = datePriorNDays.format(formatter).split("/")[1];
                if (today.charAt(0) == '0') {
                    today = today.substring(1);
                }
                inboundCorrespondenceSearchPage.calendar.click();
                waitFor(4);
                action.get().moveToElement(Driver.getDriver().findElement(By.xpath("//button/span/p[.='" + today + "']"))).click().build().perform();
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//button/span[.='OK']")).click();
                waitFor(1);
                break;

            case "BEFOREDATERECEIVED":
                LocalDate before = LocalDate.now();
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String priorToday = before.format(formatter2).split("/")[1];
                if (priorToday.charAt(0) == '0') {
                    priorToday = priorToday.substring(1);
                }
                inboundCorrespondenceSearchPage.equalSymbols.click();
                waitFor(3);
                inboundCorrespondenceSearchPage.lessThan.click();
                waitFor(3);
                inboundCorrespondenceSearchPage.calendar.click();
                waitFor(4);
                action.get().moveToElement(Driver.getDriver().findElement(By.xpath("//button/span/p[.='" + priorToday + "']"))).click().build().perform();
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//button/span[.='OK']")).click();
                waitFor(1);
                break;
            case "CUSTOMEDATERECEIVED":
                inboundCorrespondenceSearchPage.dateReceived.sendKeys(searchValue);
                break;
            default:
                Assert.fail("Name of field from feature file does not match up to any case - " + searchValue);
        }
    }

    public void selectFirstInList() {
        hover(inboundCorrespondenceSearchPage.cidList.get(0));
        waitFor(1);
        CRM_GeneralTaskStepDef.taskValues.get().put("InboundDocumentId", inboundCorrespondenceSearchPage.cidList.get(0).getText());
        inboundCorrespondenceSearchPage.cidList.get(0).click();
    }

    public void selectSpecificCidInList(String cid) {
        hover(inboundCorrespondenceSearchPage.getCid(cid));
        waitFor(1);
        inboundCorrespondenceSearchPage.getCid(cid).click();
    }

    public void addRandomNote() {
        synchronized (randomString){
            randomString.set(RandomStringUtils.random(500, true, true));
        }
        inboundCorrespondencePage.addNoteInput.sendKeys(randomString.get());
        waitFor(2);
        inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
    }

    public void addNote(String text) {
        inboundCorrespondencePage.addNoteInput.sendKeys(text);
        waitFor(2);
        inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
    }

    public void pasteNote() {
        inboundCorrespondencePage.addNoteInput.sendKeys(Keys.CONTROL, "v");
        waitFor(2);
    }

    public String getTraceId() {
        String result = EventsUtilities.getLogs("traceId", "note").get(0);
        System.out.println(result);
        return result;
    }

    public APIClassUtil getEventByTraceId(String traceId) {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri("https://max-nonprod-qa.apigee.pcf-maersk.com/mars-event-api");
        apiClassUtil.setEndPoint("/app/crm/event/correlation/" + traceId);
        apiClassUtil.getAPI();
        Assert.assertEquals(apiClassUtil.statusCode, 200);
        return apiClassUtil;
    }

    public void verifyInbNoteSaveEvent(JsonPath response) {
        eventId.set(response.get("events[0].eventId").toString());
        String projectId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId();
        String projectName = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectName();
        Assert.assertEquals(response.get("events[0].eventName"), "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT");
        Assert.assertTrue(response.get("events[0].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(response.get("events[0].payload").toString().contains("projectName\":\"" + projectName + "\""));
        Assert.assertTrue(response.get("events[0].payload").toString().contains("\"action\":\"Create\""));
        Assert.assertTrue(response.get("events[0].payload").toString().contains(randomString.get()));
    }

    public void verifyEventSentDPBI(String event) {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiEventSubRecords"));
        apiClassUtil.setEndPoint("/12700/" + eventId.get());
        JsonPath response = apiClassUtil.getAPI().jsonPathEvaluator;
        Assert.assertEquals(apiClassUtil.statusCode, 200);
        Assert.assertEquals(response.getList("eventSubscriberRecords").size(), 1);
        Assert.assertEquals(response.get("eventSubscriberRecords[0].eventName"), event);
    }

    public void selectlastInList() {
        hover(inboundCorrespondenceSearchPage.cidList.get(inboundCorrespondenceSearchPage.cidList.size() - 1));
        waitFor(1);
        inboundCorrespondenceSearchPage.cidList.get(inboundCorrespondenceSearchPage.cidList.size() - 1).click();
    }

    public String getTraceId(String additionalparameter) {
        String result = EventsUtilities.getLogs("traceId", additionalparameter).get(0);
        System.out.println(result);
        return result;
    }

    public String getTraceId(String additionalparameter, String linktype) {
        String result = "";
        if (linktype.equalsIgnoreCase("case") || linktype.equalsIgnoreCase("consumer")) {
            result = EventsUtilities.getLogs("traceId", additionalparameter).get(12);
        }
        if (linktype.equalsIgnoreCase("caseandconsumer")) {
            result = EventsUtilities.getLogs("traceId", additionalparameter).get(14);
        }
        System.out.println("traceId : " + result);
        return result;
    }

    public void verifyInbCoresspondenceUpdateEvent(JsonPath response) {
        String date = ApiTestDataUtil.getApiTestDataUtilThreadLocal().getEndDate("current");
        eventId.set(response.get("events[0].eventId").toString());
        String projectId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId();
        String projectName = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectName();
        Assert.assertEquals(response.get("events[0].eventName"), "INBOUND_CORRESPONDENCE_UPDATE_EVENT");
        String payload = response.get("events[0].payload").toString().replaceAll("\"", "");
        System.out.println("payload : " + payload);
        Assert.assertTrue(payload.contains("projectId:" + projectId + ""));
        Assert.assertTrue(payload.contains("projectName:" + projectName + ""));
        Assert.assertTrue(payload.contains("action:Update"));
        // Assert.assertTrue(payload.contains("correspondenceType:maersk Application"));
        Assert.assertTrue(payload.contains("receivedDate:" + date + ""));

    }

    public void verifyInbCoresspondenceTypeUpdateEvent(JsonPath response) {
        String projectId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId();
        String projectName = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectName();
        String payload="";

        int eventsCount = response.getInt("events.size()");

        for (int i = 0; i < eventsCount; i++) {
            System.out.println("events name : " + response.get("events["+i+"].eventName"));

           if( response.get("events["+i+"].eventName").equals("INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT")) {
               payload = response.get("events["+i+"].payload").toString().replaceAll("\"", "");
               eventId.set(response.get("events["+i+"].eventId").toString());
               System.out.println("payload : " + payload);
               Assert.assertTrue(payload.contains("projectId:" + projectId + ""));
               Assert.assertTrue(payload.contains("projectName:" + projectName + ""));
               Assert.assertTrue(payload.contains("action:Update"));

           }

        }
    }

    public void verifyLinksCreatedIBCorrepondence(JsonPath response, String linktype) {
        String projectId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId();
        String projectName = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectName();

        String payload0 = response.get("events[0].payload").toString().replaceAll("\"", "");
        System.out.println("payload[0] : " + payload0);
        System.out.println("----------------------------------------------------------------------------------");
        String payload1 = response.get("events[1].payload").toString().replaceAll("\"", "");
        System.out.println("payload[1] : " + payload1);
        System.out.println("----------------------------------------------------------------------------------");
        String payload2 = response.get("events[2].payload").toString().replaceAll("\"", "");
        System.out.println("payload[2] : " + payload2);
        System.out.println("----------------------------------------------------------------------------------");
        String actual = response.prettify();
        String pretty_actual = actual.replaceAll("\"", "");
        System.out.println("actualresponse : " + pretty_actual);


        Assert.assertEquals(response.get("events[0].eventName").toString(), "INBOUND_CORRESPONDENCE_UPDATE_EVENT");
        Assert.assertTrue(payload0.contains("projectId:" + projectId + ",projectName:" + projectName + ",action:Update"));

        Assert.assertEquals(response.get("events[1].eventName").toString(), "LINK_EVENT");
        // Assert.assertTrue(payload1.contains("internalRefType:"+linktype+",externalRefType:INBOUND_CORRESPONDENCE"));
        Assert.assertTrue(pretty_actual.contains("\\internalRefType\\:\\" + linktype + "\\,\\externalRefType\\:\\INBOUND_CORRESPONDENCE\\"));
        Assert.assertTrue(payload1.contains("projectId:" + projectId + ",projectName:" + projectName + ",action:Create"));

        Assert.assertEquals(response.get("events[2].eventName").toString(), "LINK_EVENT");
        //  Assert.assertTrue(payload2.contains("internalRefType:INBOUND_CORRESPONDENCE,externalRefType:"+linktype+""));
        Assert.assertTrue(pretty_actual.contains("\\internalRefType\\:\\INBOUND_CORRESPONDENCE\\,\\externalRefType\\:\\" + linktype + "\\"));
        Assert.assertTrue(payload2.contains("projectId:" + projectId + ",projectName:" + projectName + ",action:Create"));

    }

    public void storeAllTasks() {
        waitFor(3);
        listOfTaskIdsBeforeTypeChanged.set( getElementsText(inboundCorrespondencePage.listOfTaskIDs));
        System.out.println(listOfTaskIdsBeforeTypeChanged.get());
    }

    public void clickOnNewlyAddedTask() {
        waitFor(6);
        listOfTaskIdsAfterTypeChanged.set( getElementsText(inboundCorrespondencePage.listOfTaskIDs));
        System.out.println(listOfTaskIdsAfterTypeChanged.get());
        boolean isTaskAdded = false;
        for (int i = 0; i < listOfTaskIdsAfterTypeChanged.get().size(); i++) {
            if (!listOfTaskIdsBeforeTypeChanged.get().contains(listOfTaskIdsAfterTypeChanged.get().get(i))) {
                isTaskAdded = true;
                World.save.get().put("createdTask", inboundCorrespondencePage.listOfTaskIDs.get(i).getText());
                System.out.println("Created task is " + inboundCorrespondencePage.listOfTaskIDs.get(i).getText());
                inboundCorrespondencePage.listOfTaskIDs.get(i).click();
            }
        }
        org.junit.Assert.assertTrue("After type was changed new task not found", isTaskAdded);
    }

    public void verifyLinkInLinkSection(String value) {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        String id = "";
        switch (value) {
            case "Consumer profile":
                id = caseConsumerId.get("consumerId");
                break;
            case "Case":
                id = caseConsumerId.get("caseId");
                break;
        }
        org.junit.Assert.assertTrue("Link to " + value + " not found", Driver.getDriver().findElement(By.xpath("//td[.='" + id + "']")).isDisplayed());
        clickOnGivenID(id);
    }

    private void clickOnGivenID(String id) {
        Driver.getDriver().findElement(By.xpath("//td[.='" + id + "']")).click();
    }

    public void verifyTaskAddedToTaskList() {
        waitFor(2);
        String expectedTask = World.save.get().get("createdTask");
        org.junit.Assert.assertTrue(expectedTask + " missed in task list on Task and Service request page", Driver.getDriver().findElement(By.xpath("//td[.='" + expectedTask + "']")).isDisplayed());
    }

    public void verifyLinkFromTaskToApplication() {
        String applicationId = World.save.get().get("appID");
        String traceID = "";
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getTaskByTaskId(World.save.get().get("createdTask"));
        int linksCount = response.getInt("tasks[0].taskLinksVOS.size()");
        boolean isLinkedToApplication = false;
        for (int i = 0; i < linksCount; i++) {
            if (response.getString("tasks[0].taskLinksVOS[" + i + "].externalLinkRefType").equalsIgnoreCase("Application") &&
                    response.getString("tasks[0].taskLinksVOS[" + i + "].externalLinkRefId").equalsIgnoreCase(applicationId)) {
                isLinkedToApplication = true;
                traceID = response.getString("tasks[0].taskLinksVOS[" + i + "].correlationId");
                break;
            }
        }
        save.get().put("taskApplicationTraceID", traceID);
        Assert.assertTrue(isLinkedToApplication, "Link from created Task to Application not found");
    }

    public void verifyLinkFromApplicationToTask() {
        String applicationId = World.save.get().get("appID");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLinksToApplication(applicationId);
        int linksCount = response.getInt("data.applicationExternalLinks.size()");
        boolean isLinkedToTask = false;
        for (int i = 0; i < linksCount; i++) {
            if (response.getString("data.applicationExternalLinks[" + i + "].internalRefType").equalsIgnoreCase("Application") &&
                    response.getString("data.applicationExternalLinks[" + i + "].internalRefId").equalsIgnoreCase(applicationId) &&
                    response.getString("data.applicationExternalLinks[" + i + "].externalLinkRefId").equalsIgnoreCase(World.save.get().get("createdTask"))) {
                isLinkedToTask = true;
                break;
            }
        }
        Assert.assertTrue(isLinkedToTask, "Link from Application to new Task not found");
    }

    public void verifyLinkEventTaskToApplication(String traceID){
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceID);
        boolean isTaskLinkedToApplication = false;
        for (int i = 0; i < jsonPath.getList("events").size(); i++) {
            if (jsonPath.getString("events["+i+"].payload").contains("\"internalRefType\":\"TASK\",\"externalRefType\":\"APPLICATION\"")
            && jsonPath.getString("events["+i+"].payload").contains(World.save.get().get("appID"))
                    && jsonPath.getString("events["+i+"].payload").contains(World.save.get().get("createdTask"))){
                isTaskLinkedToApplication = true;
                break;
            }
        }
        Assert.assertTrue(isTaskLinkedToApplication, "No link found between Task and Application");
    }

    public void verifyLinkEventTaskToCases(String traceID){
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceID);
        int countOfActualLinks = 0;
        for (int i = 0; i < jsonPath.getList("events").size(); i++) {
            if (jsonPath.getString("events["+i+"].payload").contains("\"internalRefType\":\"TASK\",\"externalRefType\":\"CASE\"")){
                for (int j = 0; j < InboundDocumentTaskStepDefs.listOfCases.get().size(); j++) {
                    if(jsonPath.getString("events["+i+"].payload").contains(World.save.get().get("createdTask"))
                            && jsonPath.getString("events["+i+"].payload").contains(InboundDocumentTaskStepDefs.listOfCases.get().get(j))){
                        countOfActualLinks++;
                        break;
                    }
                }
            }
        }
        Assert.assertEquals(InboundDocumentTaskStepDefs.listOfCases.get().size(), countOfActualLinks, "Links between task and cases not found");
    }

    public void verifyLinkEventInbToMiItem(String traceId) {
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId);
        int countOfActualLinks = 0;
        for (int i = 0; i < jsonPath.getList("events").size(); i++) {
            if (jsonPath.getString("events["+i+"].payload").contains("\"internalRefType\":\"INBOUND_CORRESPONDENCE\",\"externalRefType\":\"MISSING_INFO\"")){
                for (int j = 0; j < InboundDocumentTaskStepDefs.listOfCases.get().size(); j++) {
                    if(jsonPath.getString("events["+i+"].payload").contains(World.save.get().get("createdTask"))
                            && jsonPath.getString("events["+i+"].payload").contains(InboundDocumentTaskStepDefs.listOfCases.get().get(j))){
                        countOfActualLinks++;
                        break;
                    }
                }
            }
        }
        Assert.assertEquals(InboundDocumentTaskStepDefs.listOfCases.get().size(), countOfActualLinks, "Links between task and cases not found");
    }
}
