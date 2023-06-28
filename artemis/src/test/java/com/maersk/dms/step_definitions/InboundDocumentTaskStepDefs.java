package com.maersk.dms.step_definitions;

import com.github.javafaker.Faker;
import com.google.gson.*;
import com.maersk.crm.api_step_definitions.*;
import com.maersk.crm.pages.crm.CRMTaskSearchPage;
import com.maersk.crm.step_definitions.CRM_GeneralTaskStepDef;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.pages.InboundCorrespondenceSearchPage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maersk.crm.utilities.World.generalSave;
import static com.maersk.crm.utilities.World.save;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class InboundDocumentTaskStepDefs extends CRMUtilities implements ApiBase {
    private final ThreadLocal<APIClassUtil> postInboundType = ThreadLocal.withInitial(() -> APIClassUtil.getApiClassUtilThreadLocal(true));
    private final ThreadLocal<APIClassUtil> searchInboundType = ThreadLocal.withInitial(() -> APIClassUtil.getApiClassUtilThreadLocal(true));
    private final ThreadLocal<APIClassUtil> taskList = ThreadLocal.withInitial(() -> APIClassUtil.getApiClassUtilThreadLocal(true));
    private final ThreadLocal<APIClassUtil> latestTask = ThreadLocal.withInitial(() -> APIClassUtil.getApiClassUtilThreadLocal(true));
    private final ThreadLocal<APIClassUtil> sendEvent = ThreadLocal.withInitial(() -> APIClassUtil.getApiClassUtilThreadLocal(true));
    private final ThreadLocal<ApiTestDataUtil> sendEventTdu = ThreadLocal.withInitial(() -> ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
    private final ThreadLocal<ApiTestDataUtil> expectedInbDoc = ThreadLocal.withInitial(() -> ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
    private final ThreadLocal<ApiTestDataUtil> postInboundTypeTdu = ThreadLocal.withInitial(() -> ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
    private final ThreadLocal<ApiTestDataUtil> lastTaskTdu = ThreadLocal.withInitial(() -> ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
    private final ThreadLocal<JsonObject> sendEventRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> postInboundTypeRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> latestTaskReq = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> searchInboundRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> expectedDocIdResponse = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<Map<String, String>> typeConfiguration = ThreadLocal.withInitial(HashMap::new);
    private String inboundTypeEndPoint = "/inboundCorrespondenceDefinition";
    private String postInboundTypeEndPoint = "/inboundcorrespondencedefinition";
    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    private final ThreadLocal<String> inboundType = ThreadLocal.withInitial(String::new);
    ;//inbound type for all methods here, set by feature file
    private final ThreadLocal<String> taskTriggerDate = ThreadLocal.withInitial(String::new);
    private String duplicateTaskBaseUrl = ConfigurationReader.getProperty("apiTasksDuplicate");
    private String duplicateTaskEndPoint = "/tasks/duplicate";

    public synchronized void setLatestTaskId(Integer latest) {
        this.latest.set(latest);
    }

    private final ThreadLocal<Integer> latest = ThreadLocal.withInitial(() -> 0);//latest task id
    private InboundCorrespondenceSearchPage inboundCorrespondenceSearchPage = new InboundCorrespondenceSearchPage();
    private final ThreadLocal<String> setid = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<List<String>> matchedEventIDs = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<String> adjusted = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> randomeString500 = ThreadLocal.withInitial(() -> RandomStringUtils.random(500, true, true));
    private InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();
    private ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ThreadLocal<APICreateConsumerContactController> apiCreateConsumerContactController = ThreadLocal.withInitial(APICreateConsumerContactController::new);
    final ThreadLocal<Random> random = ThreadLocal.withInitial(Random::new);
    final ThreadLocal<Faker> faker = ThreadLocal.withInitial(Faker::new);
    private final ThreadLocal<JsonObject> taskRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> enrollmentRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonArray> otherSegmentRequest = ThreadLocal.withInitial(JsonArray::new);
    private final ThreadLocal<JsonObject> request = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<Map<String, JsonArray>> inboundTaskRulesMap = ThreadLocal.withInitial(HashMap::new);
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
    private final ThreadLocal<List<JsonObject>> listOfRequest = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<List<String>> listOfCases = ThreadLocal.withInitial(ArrayList::new);
    private final String taskManagementBaseURI = ConfigurationReader.getProperty("apiTaskManagementURI");
    private final ThreadLocal<String> getTaskByTaskId = ThreadLocal.withInitial(()->"mars/taskmanagement/tasks/");
    final ThreadLocal<CRM_GeneralTaskStepDef> generalTaskStepDef = ThreadLocal.withInitial(CRM_GeneralTaskStepDef::new);
    final ThreadLocal<APIATSSendEventAndCreateLinksController> sendEventAndCreateLinksController = ThreadLocal.withInitial(APIATSSendEventAndCreateLinksController::new);


    public void initiateUpdateRequest(String typeName) {
        postInboundType.get().setbaseUri(ConfigurationReader.getProperty("apiInboundType"));
        postInboundType.get().setEndPoint(postInboundTypeEndPoint);
        postInboundTypeRequest.set(postInboundTypeTdu.get().getJsonFromFile("dms/InboundCorrespondenceType.json").jsonElement.getAsJsonObject());
        postInboundTypeRequest.get().addProperty("projectId", validNumberFilter(postInboundType.get().getProjectId()));
        postInboundTypeRequest.get().addProperty("name", typeName);
        if (searchInboundType.get().jsonPathEvaluator.get("inboundCorrespondence.barcode") == null)
            postInboundTypeRequest.get().add("barcode", JsonNull.INSTANCE);
        else
            postInboundTypeRequest.get().addProperty("barcode", Integer.parseInt(searchInboundType.get().jsonPathEvaluator.get("inboundCorrespondence.barcode").toString()));
    }

    public void sendUpdateTypeRequest() {
        postInboundType.get().PostAPIWithParameter(postInboundTypeRequest.get());
        Assert.assertEquals(postInboundType.get().statusCode, 200, "Post Update failed");
    }

    /**
     * @param typeName - inbound type name
     * @return returns task id of latest task created by the task type ids configured in "typeName" inbound type
     */
    public Integer checkLastTasksCreated(String typeName) {
        lastTaskTdu.get().getJsonFromFile("dms/taskSearch.json");//configured to sort by taskId desc
        latestTaskReq.set(lastTaskTdu.get().jsonElement.getAsJsonObject());
        //add all task type ids from World.generalSave "inboundTypeIds" List<Integer>
        for (Integer type : (List<Integer>) World.generalSave.get().get("inboundTypeIds")) {
            latestTaskReq.get().getAsJsonArray("taskTypeId").add(type);
        }
        latestTask.get().setbaseUri(ConfigurationReader.getProperty("apiInboundTaskSearch"));
        latestTask.get().setEndPoint("/app/crm/search/taskrecords");
        JsonPath response = latestTask.get().PostAPIWithParameter(latestTaskReq.get()).jsonPathEvaluator;
        Assert.assertEquals(latestTask.get().statusCode, 200);
        return response.get("taskRecords[0].taskId");
    }

    public Integer checkLastTasksCreated() {
        lastTaskTdu.get().getJsonFromFile("dms/taskSearch.json");//configured to sort by taskId desc
        latestTaskReq.set(lastTaskTdu.get().jsonElement.getAsJsonObject());
        //add all task type ids from World.generalSave "inboundTypeIds" List<Integer>
        for (Integer type : (List<Integer>) World.generalSave.get().get("inboundTypeIds")) {
            latestTaskReq.get().getAsJsonArray("taskTypeId").add(type);
        }
        latestTask.get().setbaseUri(ConfigurationReader.getProperty("apiInboundTaskSearch"));
        latestTask.get().setEndPoint("/app/crm/search/taskrecords");
        JsonPath response = latestTask.get().PostAPIWithParameter(latestTaskReq.get()).jsonPathEvaluator;
        Assert.assertEquals(latestTask.get().statusCode, 200);
        return response.get("taskRecords[0].taskId");
    }

    public List<Integer> searchInboundType(String typeName) {
        inboundType.set(typeName); // sets inbound type name for all methods in this class that aren't explicitly set
        searchInboundType.get().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition"));
        searchInboundType.get().setEndPoint("/inboundCorrespondenceDefinition");
        searchInboundRequest.get().addProperty("name", typeName);
        JsonPath response = searchInboundType.get().PostAPIWithParameter(searchInboundRequest.get()).jsonPathEvaluator;
        Assert.assertEquals(searchInboundType.get().statusCode, 200, "Search by inb name " + typeName + " failed > " + searchInboundType.get().statusCode);
        //save response in world
        World.generalSave.get().put("inboundTypeResponse", response);
        return response.getList("inboundCorrespondence.inboundCorrespondenceTaskRule.taskTypeId");
    }

    /**
     * @param typeName  - inbound type name
     * @param dataTable - add task rule json to post inbound type request
     */
    public void addTaskRule(String typeName, Map<String, String> dataTable) {
        JsonObject taskRules = new JsonObject();
        ZonedDateTime nowUTC = ZonedDateTime.now(ZoneOffset.UTC);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(nowUTC.toInstant(), ZoneOffset.UTC);
        taskRules.addProperty("createdBy", "Automation");
//        taskRules.addProperty("createdDatetime", localDateTime.toString());
        Assert.assertTrue(typeName.equalsIgnoreCase(postInboundTypeRequest.get().get("name").toString().replaceAll("\"", "")), "Type from Feature file not the same as in request");
        for (String element : dataTable.keySet()) {
            switch (element.toUpperCase()) {
                case "REQUIREDDATAELEMENTS":
                    if ("null".equalsIgnoreCase(dataTable.get(element))) {
                        taskRules.add("requiredDataElements", JsonNull.INSTANCE);
                        break;
                    }
                    taskRules.addProperty("requiredDataElements", dataTable.get(element).toString());
                    break;
                case "RANK":
                    taskRules.addProperty("rank", dataTable.get(element).toString());
                    break;
                case "TASKTYPEID":
                    taskRules.addProperty("taskTypeId", Integer.valueOf(dataTable.get(element).toString()));
                    break;
                case "LINKSAMESETTASK":
                    if ("null".equalsIgnoreCase(dataTable.get(element))) {
                        taskRules.add("linkSameSetTaskFlag", JsonNull.INSTANCE);
                        break;
                    }
                    taskRules.addProperty("linkSameSetTaskFlag", Boolean.parseBoolean(dataTable.get(element)));
                    break;
                case "NOSIBLINGCORRESPONDENCETYPES":
                    if (!dataTable.containsKey("noSiblingCorrespondenceTypes")) {
                        taskRules.add("noSiblingCorrespondenceTypes", JsonNull.INSTANCE);
                        break;
                    } else if ("null".equalsIgnoreCase(dataTable.get(element))) {
                        taskRules.add("noSiblingCorrespondenceTypes", JsonNull.INSTANCE);
                        break;
                    }
                    JsonObject noSibTypes = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertStringArrayToJsonArray(dataTable.get(element));
                    taskRules.add("noSiblingCorrespondenceTypes", noSibTypes);
                    break;
                default:
                    Assert.fail("Failed to add Task Rule to Update Request, check what is passed in feature file (element) - " + element);
            }
        }
        postInboundTypeRequest.get().getAsJsonArray("inboundCorrespondenceTaskRule").add(taskRules);
    }

    /*
    search for existing inbound type by typeName and get correct type id and add to request
     */
    public void addDefinitionIdToRequest(String typeName) {
        Assert.assertFalse(searchInboundType.get().jsonPathEvaluator.getString("inboundCorrespondence").isEmpty(), "search inbound types response.jsonPathEvaluator.get(inboundCorrespondence) is empty");
        try {
            postInboundTypeRequest.get().getAsJsonObject().addProperty("inboundCorrespondenceDefinitionId", Integer.valueOf(searchInboundType.get().jsonPathEvaluator.getString("inboundCorrespondence.inboundCorrespondenceDefinitionId")));
        } catch (NumberFormatException numberFormatException) {
            Assert.fail("Failed to format number - " + searchInboundType.get().jsonPathEvaluator.getString("inboundCorrespondenceDefinitionId"));
        } catch (Exception exception) {
            Assert.fail("Failed to add inboundCorrespondenceDefinitionId to update request - " + typeName);
            exception.printStackTrace();
        }
    }

    /**
     * @return - return true if inbound type is configured same as inbound type request
     */
    public boolean compareRequestToInboundNameSearchResponse() {
        boolean isAlreadyConfigured = true;
        Assert.assertNotNull(World.generalSave.get().get("inboundTypeResponse"), "inboundType response is not saved");
        //get the search inbound types by name response
        JsonPath response = (JsonPath) World.generalSave.get().get("inboundTypeResponse");
        //checking barcode
        if (response.get("inboundCorrespondence.barcode") == null)
            return true;
        else if (Integer.parseInt(postInboundTypeRequest.get().get("barcode").toString()) != Integer.parseInt(response.get("inboundCorrespondence.barcode").toString())) {
            return false;
        }
        //check id
        if (Integer.parseInt(postInboundTypeRequest.get().get("inboundCorrespondenceDefinitionId").toString()) != Integer.parseInt(response.get("inboundCorrespondence.inboundCorrespondenceDefinitionId").toString())) {
            return false;
        }
        //check project id
        if (Integer.parseInt(validNumberFilter(postInboundTypeRequest.get().get("projectId").toString())) != Integer.parseInt(response.get("inboundCorrespondence.projectId").toString())) {
            return false;
        }
        //check task rule sizes
        if (postInboundTypeRequest.get().getAsJsonArray("inboundCorrespondenceTaskRule").size() != response.getList("inboundCorrespondence.inboundCorrespondenceTaskRule").size()) {
            return false;
        }
        //checking task rules | rank, requiredDataElements, taskTypeId
        List<Map<String, Object>> responseTaskRules = response.getList("inboundCorrespondence.inboundCorrespondenceTaskRule");
        JsonArray requestTaskRules = postInboundTypeRequest.get().getAsJsonArray("inboundCorrespondenceTaskRule");

        for (int index = 0; index < requestTaskRules.size(); index++) {
            for (Map<String, Object> responseTaskRule : responseTaskRules) {
                //compare task rules with same rank
                if (Integer.parseInt(responseTaskRule.get("rank").toString()) == Integer.parseInt(validNumberFilter(requestTaskRules.get(index).getAsJsonObject().get("rank").toString()))) {
                    //if either is null
                    if (responseTaskRule.get("requiredDataElements") == null ^ requestTaskRules.get(index).getAsJsonObject().get("requiredDataElements") == null) {
                        return false;
                    } else if (responseTaskRule.get("requiredDataElements") == null && requestTaskRules.get(index).getAsJsonObject().get("requiredDataElements") == null) {
                        continue;
                    } else if (!compareRequiredElements(responseTaskRule.get("requiredDataElements").toString(), requestTaskRules.get(index).getAsJsonObject().get("requiredDataElements").toString().replaceAll("\"", ""))) {
                        return false;
                    } else if (!compareRequiredElements(responseTaskRule.get("taskTypeId").toString(), requestTaskRules.get(index).getAsJsonObject().get("taskTypeId").toString().replaceAll("\"", ""))) {
                        return false;
                    }
                }
            }
        }
        return isAlreadyConfigured;
    }

    private boolean compareRequiredElements(String requirement1, String requirement2) {
        boolean result = true;
        if (requirement1.contains(",") ^ requirement2.contains(",")) {
            return false;//returns false if one has more than 1 requirement and the other does not
        } else if (requirement1.contains(",") && requirement2.contains(",")) {
            //both have commas, then compare them as split up arrays on the commas
            String[] req1 = requirement1.split(",");
            String[] req2 = requirement2.split(",");
            result = compareRequirementArrays(req1, req2);
        } else {
            result = requirement1.equalsIgnoreCase(requirement2);
        }
        return result;
    }

    private boolean compareRequirementArrays(String[] req1, String[] req2) {
        if (req1.length != req2.length) {
            return false;
        }
        if (req1.length > 2) {
            return false;//return false if more than 2, which should not happen
        }
        boolean first = false;
        boolean second = false;
        for (int index = 0; index < req1.length; index++) {
            if (req1[0].equalsIgnoreCase(req2[index])) {
                first = true;
            }
            if (req1[1].equalsIgnoreCase(req2[index])) {
                second = true;
            }
        }
        return first && second;
    }

    public void initiateSendEventRequest() {
        sendEventRequest.set(sendEventTdu.get().getJsonFromFile("dms/sendEventBasic.json").jsonElement.getAsJsonObject());
        Assert.assertNotNull(World.generalSave.get().get("InboundDocumentId"));
        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentHandle", String.valueOf(World.generalSave.get().get("InboundDocumentId")));
        //setting inbound type from inboundType class level variable
        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentType", inboundType.get());
        //using phone number as a unique identifier in task creation
        World.generalSave.get().put("From Phone", RandomStringUtils.random(10, false, true));
        JsonObject phone = new JsonObject();
        phone.addProperty("keywordTypeName", "FromPhoneNumber");
        phone.addProperty("value", World.generalSave.get().get("From Phone").toString());
        JsonObject channel = new JsonObject();
        phone.addProperty("keywordTypeName", "Channel");
        phone.addProperty("value", "Mail");
        sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(channel);
        //add process type
        JsonObject process = new JsonObject();
        process.addProperty("keywordTypeName", "ProcessType");
        process.addProperty("value", "INBOUND CORRESPONDENCE");
        sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(process);
        sendEvent.get().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        sendEvent.get().setEndPoint("/ecms/connectionpoint/sendevent");
    }

    public void sendEventRequest() {
        World.generalSave.get().put("sendEventResponse", sendEvent.get().PostAPIWithParameter(String.valueOf(sendEventRequest.get())).jsonPathEvaluator);
        World.generalSave.get().put("sendEventTraceId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        try {
            String responseMessage = sendEvent.get().jsonPathEvaluator.get("message").toString();
            System.out.println("Response message: " + responseMessage);
            String str = responseMessage.substring(responseMessage.indexOf("[") + 1, responseMessage.indexOf("]"));
            CRM_TaskManagementStepDef.srID.set(str);
            System.out.println("Created SR id: " + CRM_TaskManagementStepDef.srID.get());
            APITaskManagementController.taskId.set(Integer.parseInt(str));
            System.out.println("Created task id for APITaskManagement: " + APITaskManagementController.taskId.get());
            CRM_TaskManagementStepDef.taskId.set(String.valueOf(Integer.parseInt(str) + 1));
            World.save.get().put("createdTask", str);
        } catch (Exception e) {
            System.out.println("THIS IS AN EXPECTED EXCEPTION CAUGHT AND NOT HANDLED BUT ACCOUNTED FOR ELSEWHERE, SINCE RESPONSE WILL NOT ALWAYS HAVE A TASK OR POSITIVE RESPONSE");
            e.printStackTrace();
        }
    }

    public void sendEventStringRequest() {
        World.generalSave.get().put("sendEventResponse", sendEvent.get().PostAPIWithParameter(String.valueOf(sendEventRequest.get())).jsonPathEvaluator);
        try {
            String responseMessage = sendEvent.get().jsonPathEvaluator.get("message").toString();
            String str = responseMessage.substring(responseMessage.indexOf("[") + 1, responseMessage.indexOf("]"));
            CRM_TaskManagementStepDef.srID.set(str);
            APITaskManagementController.taskId.set(Integer.parseInt(str));
            CRM_TaskManagementStepDef.taskId.set(String.valueOf(Integer.parseInt(str) + 1));
        } catch (Exception e) {
            System.out.println("THIS IS AN EXPECTED EXCEPTION CAUGHT AND NOT HANDLED BUT ACCOUNTED FOR ELSEWHERE, SINCE RESPONSE WILL NOT ALWAYS HAVE A TASK OR POSITIVE RESPONSE");
            e.printStackTrace();
        }
    }

    public void verifyTaskCreated() {
        Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("task(s) created with Id(s)"), "TASK WAS NOT CREATED |" + sendEvent.get().jsonPathEvaluator.get("status") + " | " + sendEvent.get().jsonPathEvaluator.get("message").toString());
        verifyTaskCreatedInCorrectProject();
        verifyFromPhoneNumberInTask();
    }

    public void verifyTaskCreation(String status) {
        if (status.equalsIgnoreCase("notcreated")) {
            Assert.assertFalse(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("task(s) created with Id(s)"), "TASK WAS CREATED |" + sendEvent.get().jsonPathEvaluator.get("status") + " | " + sendEvent.get().jsonPathEvaluator.get("message").toString());
            Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("Document created successfully"), "TASK WAS CREATED |" + sendEvent.get().jsonPathEvaluator.get("status") + " | " + sendEvent.get().jsonPathEvaluator.get("message").toString());
        } else if (status.equalsIgnoreCase("created")) {
            Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("task(s) created with Id(s)"), "TASK WAS CREATED |" + sendEvent.get().jsonPathEvaluator.get("status") + " | " + sendEvent.get().jsonPathEvaluator.get("message").toString());
        }
    }

    public void verifyTaskCreated(String taskTypeId) {
        Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("task(s) created with Id(s)"), "TASK WAS NOT CREATED |" + sendEvent.get().jsonPathEvaluator.get("status") + " | " + sendEvent.get().jsonPathEvaluator.get("message").toString());
        verifyTaskCreatedInCorrectProject();
        verifyFromPhoneNumberInTask();
        verifyTaskTypeIdOfTask(taskTypeId);
    }

    private void verifyTaskTypeIdOfTask(String taskTypeId) {
        JsonPath task = apiAutoUtilities.get().getTaskByTaskId(String.valueOf(latest.get()));
        Integer actualTaskTypeId = task.get("tasks[0].taskVO.taskTypeId");
        Assert.assertTrue(actualTaskTypeId == Integer.parseInt(taskTypeId), "Task Type of actual >> " + actualTaskTypeId + " | Task Type Expected >> " + taskTypeId);
    }

    public String getTaskType(String taskId) {
        JsonPath task = apiAutoUtilities.get().getTaskByTaskId(taskId);
        String actualTaskTypeId = task.getString("tasks[0].taskVO.taskTypeId");
        return actualTaskTypeId;
    }

    public String getTaskDetailsIdByTaskId(String taskId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskByTaskId.get() + taskId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        String taskDetailsId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[0].taskDetailsId");
        return taskDetailsId;
    }

    private boolean verifyTaskTypeIdOfTask(String taskId, String taskTypeId) {
        JsonPath task = apiAutoUtilities.get().getTaskByTaskId(String.valueOf(taskId));
        Integer actualTaskTypeId = task.get("tasks[0].taskVO.taskTypeId");
        return actualTaskTypeId == Integer.parseInt(taskTypeId);
    }

    /**
     * verify phone number is present in task
     */
    public void verifyFromPhoneNumberInTask() {
        //verify that random "FromPhoneNumber" generated is in the task by api call to get task by task id
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("From Phone")) && (json.get("selectionVarchar").equalsIgnoreCase(World.generalSave.get().get("From Phone").toString()))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Phone Number NOT found in Task");
    }

    public void verifyFromPhoneNumberInTask(String phone) {
        //verify that random "FromPhoneNumber" generated is in the task by api call to get task by task id
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("From Phone")) && (json.get("selectionVarchar").equalsIgnoreCase(phone))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Phone Number NOT found in Task");
    }

    public void verifyTaskCreatedInCorrectProject() {
        //check what task id last created from response
        Integer earlier = (Integer) World.generalSave.get().get("latestTaskId");
        //make another api call to check last task id, inboundType set by
        latest.set(Integer.valueOf(BrowserUtils.validNumberFilter(sendEvent.get().responseBody.jsonPath().getString("message"))));
        //verify task created in proper project
        Assert.assertTrue(earlier < latest.get(), "no task created in project in > " + ConfigurationReader.getProperty("projectName") + " | Earlier most recent task id - " + earlier + " | Now most recent task id - " + latest + " |");
    }

    public void uploadInboundDocument(String inboundType) {
        World.generalSave.get().put("InboundDocumentId", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getnewInboundDocId(inboundType)));
    }

    public void rescanInboundDocument(Map<String, String> keywords) {
        World.generalSave.get().put("GETNEWRESCANINBOUNDDOCID", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getNewRescannedInboundDocId(keywords)));
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().sendDocumentToOnBase(keywords), "SUCCESS", "Failed");
    }


    /**
     * @param keywords creates send event request from keyword map
     */
    public void initiateSendEventRequest(Map<String, String> keywords) {
        sendEventRequest.set(sendEventTdu.get().getJsonFromFile("dms/sendEventBasic.json").jsonElement.getAsJsonObject());
        if (!keywords.containsKey("documentHandle")) {
            Assert.assertNotNull(World.generalSave.get().get("InboundDocumentId"), "World.generalSave.get().get(InboundDocumentId) is null");
            sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentHandle", String.valueOf(World.generalSave.get().get("InboundDocumentId")));
        } else if (keywords.get("documentHandle").equals("fromInbound")) {
            System.out.println("Inb doc id digital:" + World.generalSave.get().get("InboundDocumentIdDigital").toString());
            sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentHandle", String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital").toString()));
        }

        //setting inbound type from inboundType class level variable
        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentType", inboundType.get());
        //using phone number as a unique identifier in task creation
        World.generalSave.get().put("From Phone", RandomStringUtils.random(10, false, true));
        JsonObject phone = new JsonObject();
        phone.addProperty("keywordTypeName", "FromPhoneNumber");
        phone.addProperty("value", World.generalSave.get().get("From Phone").toString());
        sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(phone);
        sendEvent.get().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        sendEvent.get().setEndPoint("/ecms/connectionpoint/sendevent");
        for (String key : keywords.keySet()) {
            switch (key.toUpperCase()) {
                case "TYPE":
                    sendEventRequest.get().addProperty("type", keywords.get("TYPE"));
                    break;
                case "CASEID":
                    JsonObject caseId = new JsonObject();
                    String caseid = "";
                    caseId.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        caseId.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            caseid = RandomStringUtils.random(5, false, true);
                        } else if (keywords.get(key).equalsIgnoreCase("PreviouslyCreated")) {
                            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                            caseid = caseConsumerId.get("caseId");
                        } else if ("previouslyCreatedForApplication".equalsIgnoreCase(keywords.get(key))) {
                            caseid = listOfCases.get().get(new Random().nextInt(listOfCases.get().size()));
                        } else {
                            caseid = keywords.get(key);
                        }
                        caseId.addProperty("value", caseid);
                        CRM_GeneralTaskStepDef.taskValues.get().put("caseID", caseid);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(caseId);
                    World.generalSave.get().put("CASE", caseid);
                    break;
                case "CONSUMERID":
                    JsonObject consumerId = new JsonObject();
                    String consumerid = "";
                    consumerId.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        consumerId.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            consumerid = RandomStringUtils.random(5, false, true);
                        } else if (keywords.get(key).equalsIgnoreCase("PreviouslyCreated")) {
                            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                            consumerid = caseConsumerId.get("consumerId");
                        } else {
                            consumerid = keywords.get(key);
                        }
                        consumerId.addProperty("value", consumerid);
                        CRM_GeneralTaskStepDef.taskValues.get().put("consumerID", consumerid);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(consumerId);
                    World.generalSave.get().put("CONSUMER", consumerid);
                    break;
                case "CHANNEL":
                    JsonObject channel = new JsonObject();
                    String Channel = "";
                    channel.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        channel.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            Channel = RandomStringUtils.random(5, false, true);
                        } else {
                            Channel = keywords.get(key);
                        }
                        channel.addProperty("value", Channel);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(channel);
                    World.generalSave.get().put("CHANNEL", Channel);
                    break;
                case "FROMEMAIL":
                    JsonObject fromEmail = new JsonObject();
                    String email = "";
                    fromEmail.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        fromEmail.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            email = RandomStringUtils.random(5, true, false) + "@" + RandomStringUtils.random(5, true, false) + ".com";
                        } else {
                            email = keywords.get(key);
                        }
                        fromEmail.addProperty("value", email);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(fromEmail);
                    World.generalSave.get().put("FROM EMAIL", email);
                    break;
                case "FROMNAME":
                    JsonObject fromName = new JsonObject();
                    String from = "";
                    fromName.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        fromName.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            from = RandomStringUtils.random(5, false, true);
                        } else {
                            from = keywords.get(key);
                        }
                        fromName.addProperty("value", from);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(fromName);
                    World.generalSave.get().put("FROM NAME", from);
                    break;
                case "SETID":
                    JsonObject setId = new JsonObject();
                    String setid = "";
                    setId.addProperty("keywordTypeName", "SetId");
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        setId.add("value", JsonNull.INSTANCE);
                    } else if (keywords.get(key).equalsIgnoreCase("previouslyCreated")) {
                        setId.addProperty("value", String.valueOf(World.generalSave.get().get("setId")));
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            setid = RandomStringUtils.random(10, false, true);
                        } else {
                            setid = keywords.get(key);
                        }
                        setId.addProperty("value", setid);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(setId);
                    World.generalSave.get().put("SET ID", setid);
                    break;
                case "UPDATEDBYUSER":
                    JsonObject updatedBy = new JsonObject();
                    String updatedby = "";
                    updatedBy.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        updatedBy.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            updatedby = RandomStringUtils.random(5, true, false);
                        } else {
                            updatedby = keywords.get(key);
                        }
                        updatedBy.addProperty("value", updatedby);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(updatedBy);
                    World.generalSave.get().put("UPDATED BY", updatedby);
                    break;
                case "CREATEDBYUSER":
                    JsonObject createdBy = new JsonObject();
                    String by = "";
                    createdBy.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        createdBy.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            by = RandomStringUtils.random(5, true, false);
                        } else {
                            by = keywords.get(key);
                        }
                        createdBy.addProperty("value", by);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(createdBy);
                    World.generalSave.get().put("CREATED BY", by);
                    break;
                case "NOTIFICATIONID":
                    JsonObject notificationId = new JsonObject();
                    String nid = "";
                    notificationId.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        notificationId.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            nid = RandomStringUtils.random(5, false, true);
                            World.generalSave.get().put("nidRandom", nid);
                        } else if ("previouslyCreated".equalsIgnoreCase(keywords.get(key))) {
                            List<Map<String, Object>> notifications = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString()).getList("recipients[0].notifications");
                            nid = notifications.get(0).get("notificationId").toString();
                            World.generalSave.get().put("NOTIFICATIONID", nid);
                        } else {
                            nid = keywords.get(key);
                        }
                        notificationId.addProperty("value", nid);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(notificationId);
                    World.generalSave.get().put("NOTIFICATION ID", nid);
                    break;
                case "PROCESSTYPE":
                    JsonObject processType = new JsonObject();
                    String process = "";
                    processType.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        processType.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            process = RandomStringUtils.random(5, true, false);
                        } else {
                            process = keywords.get(key);
                        }
                        processType.addProperty("value", process);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(processType);
                    break;
                case "UPDATEDON":
                    JsonObject updatedOn = new JsonObject();
                    String upOn = "";
                    updatedOn.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        updatedOn.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            upOn = RandomStringUtils.random(5, false, true);
                        } else {
                            upOn = keywords.get(key);
                        }
                        updatedOn.addProperty("value", upOn);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(updatedOn);
                    World.generalSave.get().put("NOTIFICATION ID", upOn);
                    break;
                case "STATUS":
                    JsonArray statusFromFile = sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields");
                    boolean statusFound = false;
                    for (int index = 0; index < statusFromFile.size(); index++) {
                        if ("{\"keywordTypeName\":\"Status\",\"value\":\"TRANSMITTING\"}".equalsIgnoreCase(String.valueOf(statusFromFile.get(index)))) {
                            statusFromFile.remove(index);
                            statusFound = true;
                            break;
                        }
                    }
                    Assert.assertTrue(statusFound);
                    JsonObject status = new JsonObject();
                    String stats = "";
                    status.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        status.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            stats = RandomStringUtils.random(5, true, false);
                        } else {
                            stats = keywords.get(key);
                        }
                        status.addProperty("value", stats);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(status);
                    break;
                case "STATUSSETON":
                    JsonArray statusSetOn = sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields");
                    boolean statusSet = false;
                    for (int index = 0; index < statusSetOn.size(); index++) {
                        if ("{\"keywordTypeName\":\"StatusSetOn\",\"value\":\"2020-07-01T23:57:51.176000+00:00\"}".equalsIgnoreCase(String.valueOf(statusSetOn.get(index)))) {
                            statusSetOn.remove(index);
                            statusSet = true;
                            break;
                        }
                    }
                    Assert.assertTrue(statusSet);
                    JsonObject statusSetON = new JsonObject();
                    String statSetOn = "";
                    statusSetON.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        statusSetON.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            statSetOn = RandomStringUtils.random(5, false, true);
                        } else {
                            statSetOn = keywords.get(key);
                        }
                        statusSetON.addProperty("value", statSetOn);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(statusSetON);
                    World.generalSave.get().put("NOTIFICATION ID", statSetOn);
                    break;
                case "ORIGIN":
                    JsonObject origin = new JsonObject();
                    String orig = "";
                    origin.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        origin.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            orig = RandomStringUtils.random(5, true, false);
                        } else {
                            orig = keywords.get(key);
                        }
                        origin.addProperty("value", orig);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(origin);
                    break;
                case "ORIGINITEMID":
                    JsonObject originItemId = new JsonObject();
                    String origItemId = "";
                    originItemId.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        originItemId.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            origItemId = RandomStringUtils.random(5, false, true);
                        } else {
                            origItemId = keywords.get(key);
                        }
                        originItemId.addProperty("value", origItemId);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(originItemId);
                    World.generalSave.get().put("NOTIFICATION ID", origItemId);
                    break;
                case "ORIGINSETID":
                    JsonObject originSetId = new JsonObject();
                    String origSetId = "";
                    originSetId.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        originSetId.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            origSetId = RandomStringUtils.random(5, false, true);
                        } else {
                            origSetId = keywords.get(key);
                        }
                        originSetId.addProperty("value", origSetId);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(originSetId);
                    World.generalSave.get().put("NOTIFICATION ID", origSetId);
                    break;
                case "SCANNEDON":
                    JsonObject scannedOn = new JsonObject();
                    String scanOn = "";
                    scannedOn.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        scannedOn.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            scanOn = RandomStringUtils.random(5, false, true);
                        } else {
                            scanOn = keywords.get(key);
                        }
                        scannedOn.addProperty("value", scanOn);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(scannedOn);
                    World.generalSave.get().put("NOTIFICATION ID", scanOn);
                    break;
                case "FROMPHONENUMBER":
                    JsonObject fromPhoneNum = new JsonObject();
                    String fromPhoNum = "";
                    fromPhoneNum.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        fromPhoneNum.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            fromPhoNum = RandomStringUtils.random(5, false, true);
                        } else {
                            fromPhoNum = keywords.get(key);
                        }
                        fromPhoneNum.addProperty("value", fromPhoNum);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(fromPhoneNum);
                    World.generalSave.get().put("NOTIFICATION ID", fromPhoNum);
                    break;
                case "RETURNEDREASON":
                    JsonObject returnReason = new JsonObject();
                    String rereason = "";
                    returnReason.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        returnReason.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            rereason = RandomStringUtils.random(5, true, false);
                        } else {
                            rereason = keywords.get(key);
                        }
                        returnReason.addProperty("value", rereason);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(returnReason);
                    break;
                case "FORMVERSION":
                    JsonObject formVersion = new JsonObject();
                    String formVer = "";
                    formVersion.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        formVersion.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            formVer = RandomStringUtils.random(5, false, true);
                        } else {
                            formVer = keywords.get(key);
                        }
                        formVersion.addProperty("value", formVer);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(formVersion);
                    World.generalSave.get().put("NOTIFICATION ID", formVer);
                    break;
                case "LANGUAGE":
                    JsonObject language = new JsonObject();
                    String lang = "";
                    language.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        language.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            lang = RandomStringUtils.random(5, true, false);
                        } else {
                            lang = keywords.get(key);
                        }
                        language.addProperty("value", lang);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(language);
                    break;
                case "REPLACEMENTDOCUMENTID":
                    JsonObject rePlaDocId = new JsonObject();
                    String reDocId = "";
                    rePlaDocId.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        rePlaDocId.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            reDocId = RandomStringUtils.random(5, false, true);
                        } else {
                            reDocId = keywords.get(key);
                        }
                        rePlaDocId.addProperty("value", reDocId);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(rePlaDocId);
                    World.generalSave.get().put("NOTIFICATION ID", reDocId);
                    break;
                case "RESCANOFDOCUMENTID":
                    JsonObject reScanDocId = new JsonObject();
                    String reScanId = "";
                    reScanDocId.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        reScanDocId.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            reScanId = RandomStringUtils.random(5, false, true);
                        } else if (keywords.get(key).equalsIgnoreCase("OriginalDocumentId")) {
                            reScanId = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
                        } else {
                            reScanId = keywords.get(key);
                        }
                        reScanDocId.addProperty("value", reScanId);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(reScanDocId);
                    World.generalSave.get().put("NOTIFICATION ID", reScanId);
                    break;
                case "BATCH #":
                    JsonObject batchNum = new JsonObject();
                    String batNumber = "";
                    batchNum.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        batchNum.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            batNumber = RandomStringUtils.random(5, false, true);
                        } else {
                            batNumber = keywords.get(key);
                        }
                        batchNum.addProperty("value", batNumber);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(batchNum);
                    World.generalSave.get().put("NOTIFICATION ID", batNumber);
                    break;
                case "BARCODEID":
                    JsonObject barcodeId = new JsonObject();
                    String barId = "";
                    barcodeId.addProperty("keywordTypeName", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        barcodeId.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            barId = RandomStringUtils.random(5, false, true);
                        } else {
                            barId = keywords.get(key);
                        }
                        barcodeId.addProperty("value", barId);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(barcodeId);
                    World.generalSave.get().put("NOTIFICATION ID", barId);
                    break;
                case "DOCUMENTTYPE":
                    if ("null".equalsIgnoreCase(keywords.get("documentType"))) {
                        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").add("documentType", JsonNull.INSTANCE);
                    } else {
                        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentType", keywords.get("documentType"));
                    }
                    sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentName", keywords.get("documentType") + " - " + DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH).format(LocalDateTime.now()));
                    sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentTypeName", keywords.get("documentType"));
                    break;
                case "DOCUMENTDATE":
                    String todayDate = "";
                    if ("null".equalsIgnoreCase(keywords.get("documentDate"))) {
                        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").add("documentDate", JsonNull.INSTANCE);
                    } else if ("YYYY-MM-DD".equalsIgnoreCase(keywords.get("documentDate")) || "today".equalsIgnoreCase(keywords.get("documentDate"))) {
                        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentDate", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getTodayDateString("yyyy-MM-dd"));
                    } else {
                        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentDate", keywords.get("documentDate"));
                    }
                    break;
                case "DOCUMENTHANDLE":
                    Integer inbId = -1;
                    if ("OriginalDocumentId".equalsIgnoreCase(keywords.get(key))) {
                        inbId = Integer.valueOf(String.valueOf(World.generalSave.get().get("InboundDocumentId")));
                    } else if ("GETNEWRESCANINBOUNDDOCID".equalsIgnoreCase(keywords.get(key))) {
                        inbId = Integer.valueOf(String.valueOf(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID")));
                    } else if ("InboundDocumentIdDigital".equalsIgnoreCase(keywords.get(key))) {
                        inbId = Integer.valueOf(String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")));
                    } else if ("SameSetInboundDocument".equalsIgnoreCase(keywords.get(key))) {
                        inbId = Integer.valueOf(String.valueOf(World.generalSave.get().get("SameSetInboundDocument")));
                    } else if ("null".equalsIgnoreCase(keywords.get(key))) {
                        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").add("documentHandle", JsonNull.INSTANCE);
                        break;
                    } else if ("fromInbound".equalsIgnoreCase(keywords.get("documentHandle"))) {
                        inbId = Integer.valueOf(String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital").toString()));
                        break;
                    } else {
                        inbId = Integer.valueOf(keywords.get("documentHandle"));
                    }
                    sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentHandle", inbId);
                    break;
                case "CONSUMERIDS":
                    JsonObject outerConsumer = new JsonObject();
                    JsonObject consumerNumber = new JsonObject();
                    consumerNumber.addProperty("keywordTypeName", "ConsumerID");
                    outerConsumer.addProperty("name", "Consumer");
                    JsonArray consMetadata = new JsonArray();
                    String consumerString = "";
                    consumerNumber.addProperty("value", key);
                    if (keywords.get(key).equalsIgnoreCase("null")) {
                        consumerNumber.add("value", JsonNull.INSTANCE);
                        break;
                    } else {
                        //if for random value from feature file
                        if (keywords.get(key).equalsIgnoreCase("random")) {
                            consumerString = RandomStringUtils.random(5, false, true);
                        } else if (keywords.get(key).equalsIgnoreCase("previouslyCreated")) {
                            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                            consumerString = caseConsumerId.get("consumerId");
                        } else {
                            consumerString = keywords.get(key);
                        }
                        consumerNumber.addProperty("value", consumerString);
                        consMetadata.add(consumerNumber);
                        outerConsumer.add("keywords", consMetadata);
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordRecords").add(outerConsumer);
                    World.generalSave.get().put("CONSUMER", consumerString);
                    break;
                case "APPLICATIONID":
                    JsonObject applicationId = new JsonObject();
                    applicationId.addProperty("keywordTypeName", key);
                    if ("previouslyCreatedATS".equalsIgnoreCase(keywords.get(key)))
                        applicationId.addProperty("value", APIATSApplicationController.applicationIdAPI.get());
                    else if ("previouslyCreated".equalsIgnoreCase(keywords.get(key))) {
                        applicationId.addProperty("value", World.save.get().get("appID"));
                    } else
                        applicationId.addProperty("value", keywords.get(key));
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(applicationId);
                    break;
                case "MISSING INFORMATION ITEM ID":
                    JsonObject missingItemId = new JsonObject();
                    if ("previouslyCreated".equalsIgnoreCase(keywords.get(key))) {
                        missingItemId.addProperty("keywordTypeName", key);
                        missingItemId.addProperty("value", String.valueOf(World.generalSave.get().get("MissingInformationItemId")));
                    }
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(missingItemId);
                    break;
                case "EXTERNAL APPLICATION ID":
                    JsonObject externalApplicationId = new JsonObject();
                    externalApplicationId.addProperty("keywordTypeName", key);
                    if ("previouslyCreated".equalsIgnoreCase(keywords.get(key)))
                        externalApplicationId.addProperty("value", String.valueOf(World.generalSave.get().get("External Application ID")));
                    else
                        externalApplicationId.addProperty("value", keywords.get(key));
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(externalApplicationId);
                    break;
                case "IN RID":
                    // this will be used for adding Medicaid IN RID keywords to the send event request, it is needed for Just Cause SR
                    JsonObject inRID = new JsonObject();
                    inRID.addProperty("keywordTypeName", key);
                    if ("externalId".equalsIgnoreCase(keywords.get(key)))
                        inRID.addProperty("value", generalTaskStepDef.get().externalConsumerID.get());
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(inRID);

                    JsonObject externalConsumerIDType = new JsonObject();
                    externalConsumerIDType.addProperty("keywordTypeName", "External Consumer ID Type");
                    externalConsumerIDType.addProperty("value", "MEDICAID");
                    sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(externalConsumerIDType);
                    break;

                default:
                    Assert.fail("Key Provided does not match cases >>> " + key);
            }
        }

    }

    public void verifyTaskLinksCreated(Map<String, String> dataTable) {
        Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("task(s) created with Id(s)"), "TASK WAS NOT CREATED |" + sendEvent.get().jsonPathEvaluator.get("status") + " | " + "\n" + sendEvent.get().jsonPathEvaluator.get("message").toString());
        verifyTaskCreatedInCorrectProject();
        verifyFromPhoneNumberInTask();
        for (String key : dataTable.keySet()) {
            verifyLinkInTask(key, dataTable.get(key));
        }
    }

    /**
     * verify Links is present in task
     */
    public void verifyLinkInTask(String key, String value) {
        //verify that key is in task
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        if (value.equalsIgnoreCase("fromRequest")) {
            value = World.generalSave.get().get(key).toString();
        }
        List<Map<String, String>> taskLinks = jsonPath.getList("tasks[0].taskLinksVOS");
        Assert.assertTrue(taskLinks.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : taskLinks) {
            if ((json.get("externalLinkRefType").equalsIgnoreCase(key)) && (json.get("externalLinkRefId").equalsIgnoreCase(value))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, key + " Link NOT found in Task" + "\n" + jsonPath.toString());
    }

    public void verifyTaskNOTCreated() {
        Assert.assertFalse(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("task(s) created with Id(s)"), "TASK WAS CREATED |" + sendEvent.get().jsonPathEvaluator.get("status") + " | " + "\n" + sendEvent.get().jsonPathEvaluator.get("message").toString());
    }

    private void verifyTaskNotCreatedInProject() {
        //check what task id last created from response
        Integer earlier = (Integer) World.generalSave.get().get("latestTaskId");
        //make another api call to check last task id, inboundType set by
        latest.set(checkLastTasksCreated());
        //verify task created in proper project
        Assert.assertTrue(earlier == latest.get(), "no task created in project in > " + ConfigurationReader.getProperty("projectName") + " | Earlier most recent task id - " + earlier + " | Now most recent task id - " + latest.get() + " |");
    }

    public void verifyLinksInUI(Map<String, String> dataTable) {
        waitFor(7);
        scrollDownUsingActions(10);
        List<WebElement> actualLinksNames = inboundCorrespondenceSearchPage.linksNameColumn;
        List<WebElement> actualLinksIds = inboundCorrespondenceSearchPage.linksIdColumn;
        List<String> actualNames = new ArrayList<>();
        List<String> actualIds = new ArrayList<>();
        actualLinksNames.stream().forEach((k) -> {
            actualNames.add(k.getText());
        });
        actualLinksIds.stream().forEach((k) -> {
            actualIds.add(k.getText());
        });
        Collections.sort(actualIds);
        Collections.sort(actualNames);
        List<String> expectedNames = new ArrayList<>();
        List<String> expectedIds = new ArrayList<>();
        expectedNames.addAll(dataTable.keySet());
        expectedIds.addAll(dataTable.values());
        Collections.sort(expectedIds);
        Collections.sort(expectedNames);
        for (int index = 0; index < expectedIds.size(); index++) {
            Assert.assertEquals(actualLinksIds.get(index).getText().trim(), expectedIds.get(index));
        }
        for (int index = 0; index < expectedNames.size(); index++) {
            Assert.assertEquals(actualLinksNames.get(index).getText().trim(), expectedNames.get(index));

        }
    }

    public void verifyLinksInUI() {
        waitFor(7);
        scrollDownUsingActions(10);
        List<WebElement> actualLinksNames = inboundCorrespondenceSearchPage.linksNameColumn;
        List<WebElement> actualLinksIds = inboundCorrespondenceSearchPage.linksIdColumn;
        List<String> actualNames = new ArrayList<>();
        List<String> actualIds = new ArrayList<>();
        actualLinksNames.stream().forEach((k) -> {
            actualNames.add(k.getText());
        });
        actualLinksIds.stream().forEach((k) -> {
            actualIds.add(k.getText());
        });
        Collections.sort(actualIds);
        Collections.sort(actualNames);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(String.valueOf(World.generalSave.get().get("InboundDocumentId")));
        List<String> expectedIds = new ArrayList<>();
        List<String> expectedNames = new ArrayList<>();
        response.getList("externalLinkDetails.content.name").stream().forEach((k) -> {
            expectedNames.add(String.valueOf(k));
        });
        response.getList("externalLinkDetails.content.id").stream().forEach((k) -> {
            expectedIds.add(String.valueOf(k));
        });
        Collections.sort(expectedIds);
        Collections.sort(expectedNames);
        for (int index = 0; index < expectedIds.size(); index++) {
            Assert.assertEquals(actualIds.get(index).trim(), expectedIds.get(index));
        }
        for (int index = 0; index < expectedNames.size(); index++) {
            Assert.assertEquals(actualNames.get(index).trim(), expectedNames.get(index));

        }
    }

    public void verifyTaskLCreated(Map<String, String> dataTable) {
        Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("task(s) created with Id(s)"), "TASK WAS NOT CREATED |" + sendEvent.get().jsonPathEvaluator.get("status") + " | " + "\n" + sendEvent.get().jsonPathEvaluator.get("message").toString());
        verifyTaskCreatedInCorrectProject();
        for (String key : dataTable.keySet()) {
            switch (key.toUpperCase()) {
                case "CHANNEL":
                    verifyChannelInTask(dataTable.get(key));
                    break;
                case "FROM EMAIL":
                    verifyEmailInTask(dataTable.get(key));
                    break;
                case "FROM NAME":
                    verifyFromNameInTask(dataTable.get(key));
                    break;
                case "NOTIFICATION ID":
                    if ("random".equalsIgnoreCase(dataTable.get(key))) {
                        verifyNIDInTask(World.generalSave.get().get("nidRandom").toString());
                        break;
                    }
                    verifyNIDInTask(dataTable.get(key));
                    break;
                case "INBOUND CORRESPONDENCE TYPE":
                    verifyInbTypeInTask(dataTable.get(key));
                    break;
                case "RETURNED MAIL REASON":
                    verifyReturnedReasonInTask(dataTable.get(key));
                    break;
                case "FROM PHONE":
                    verifyFromPhoneNumberInTask(dataTable.get(key));
                    break;
                /**
                 * below are the cases that aren't passed in the request,but are found in
                 * task type configuration
                 */
                case "PRIORITY":
                    verifyPriorityInTask(dataTable.get(key));
                    break;
                case "DUEDATE":
                    verifyDueDateInTask(dataTable.get(key));
                    break;
                case "STATUS":
                    verifyStatusInTask(dataTable.get(key));
                    break;
                case "STATUSDATE":
                    verifyStatusDateInTask(dataTable.get(key));
                    break;
                case "SOURCE":
                    verifySourceInTask(dataTable.get(key));
                    break;
                case "CREATEDON":
                    verifyCreatedOnInTask(dataTable.get(key));
                    break;
                case "CREATEDBY":
                    verifyCreatedByInTask(dataTable.get(key));
                    break;
                case "TASKTYPE":
                    verifyTaskTypeInTask(dataTable.get(key));
                    break;
                default:
                    Assert.fail("Key Provided does not match cases >>> " + key);
            }
        }
    }

    private void verifyTaskTypeInTask(String type) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        if (jsonPath.get("tasks[0].taskVO.taskTypeId").toString().equalsIgnoreCase(type)) {
            found = true;
        }
        Assert.assertTrue(found, type + " NOT found in Task");
    }

    private void verifyCreatedByInTask(String createdBy) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        if (createdBy.equalsIgnoreCase("Ecms Service")) {
            createdBy = "8939";
        }
        if (jsonPath.get("tasks[0].taskVO.createdBy").toString().equalsIgnoreCase(createdBy)) {
            found = true;
        }
        Assert.assertTrue(found, createdBy + " NOT found in Task");
    }

    private void verifyCreatedOnInTask(String createdOn) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        createdOn = apiAutoUtilities.get().getDateTimeNow(createdOn);
        if (apiAutoUtilities.get().compareDateTimes(jsonPath.get("tasks[0].taskVO.statusDate").toString(), createdOn)) {
            found = true;
        }
        Assert.assertTrue(found, createdOn + " NOT found in Task");
    }

    private void verifySourceInTask(String source) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        if ((jsonPath.get("tasks[0].taskVO.source").toString().equalsIgnoreCase(source))) {
            found = true;
        }
        Assert.assertTrue(found, source + " NOT found in Task");
    }

    private void verifyStatusDateInTask(String statusDate) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        statusDate = apiAutoUtilities.get().getDateTimeNow(statusDate);
        if (apiAutoUtilities.get().compareDateTimes(jsonPath.get("tasks[0].taskVO.statusDate").toString(), statusDate)) {
            found = true;
        }
        Assert.assertTrue(found, statusDate + " NOT found in Task");
    }

    private void verifyStatusInTask(String status) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        if ((jsonPath.get("tasks[0].taskVO.taskStatus").toString().equalsIgnoreCase(status))) {
            found = true;
        }
        Assert.assertTrue(found, status + " NOT found in Task");
    }

    private void verifyDueDateInTask(String due) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        if ((jsonPath.get("tasks[0].taskVO.dueInDays").toString().equalsIgnoreCase(due))) {
            found = true;
        }
        Assert.assertTrue(found, due + " NOT found in Task"+"\nfound - "+found+"\ndue - "+due+"\n"+jsonPath.prettyPrint());
    }

    private void verifyPriorityInTask(String priority) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        if ((jsonPath.get("tasks[0].taskVO.defaultPriority").toString().equalsIgnoreCase(priority))) {
            found = true;
        }
        Assert.assertTrue(found, priority + " NOT found in Task");
    }

    private void verifyReturnedReasonInTask(String reason) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("RETURNED MAIL REASON")) && (json.get("selectionVarchar").equalsIgnoreCase(reason))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, reason + " NOT found in Task");
    }

    private void verifyInbTypeInTask(String type) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("INBOUND CORRESPONDENCE TYPE")) && (json.get("selectionVarchar").equalsIgnoreCase(type))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, type + " NOT found in Task");
    }

    private void verifyNIDInTask(String nid) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("NOTIFICATION ID")) && (json.get("selectionVarchar").equalsIgnoreCase(nid))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, nid + " NOT found in Task");
    }

    private void verifyFromNameInTask(String name) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("FROM NAME")) && (json.get("selectionVarchar").equalsIgnoreCase(name))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, name + " NOT found in Task");
    }

    private void verifyEmailInTask(String email) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("FROM EMAIL")) && (email.equalsIgnoreCase(json.get("selectionVarchar")))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, email + " NOT found in Task");
    }

    private void verifyChannelInTask(String channel) {
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("CHANNEL")) && (json.get("selectionVarchar").equalsIgnoreCase(channel))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, channel + " NOT found in Task");
    }

    private void verifyCaseInTask(String caseid) {
        //verify that random "CASE" generated is in the task by api call to get task by task id
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(latest.get().toString());
        boolean found = false;
        List<Map<String, String>> selectionFieldName = jsonPath.getList("tasks[0].taskVO.taskDetails");
        Assert.assertTrue(selectionFieldName.size() > 0, "Jsonpath for Task Details returning nothing");
        for (Map<String, String> json : selectionFieldName) {
            if ((json.get("selectionFieldName").equalsIgnoreCase("CASE")) && (json.get("selectionVarchar").equalsIgnoreCase(caseid))) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, caseid + " NOT found in Task");
    }

    public void getEventIDfromSetID(String eventType) {
        //getting EVENT ID by passing randomized setID
        setid.set(String.valueOf(World.generalSave.get().get("InboundDocumentId")));
        matchedEventIDs.set(apiAutoUtilities.get().getEventId(eventType, setid.get()));
    }

    public void findEventId() {
        JsonPath eventByEventId = apiAutoUtilities.get().getEventByEventId(matchedEventIDs.get().get(0));
        Assert.assertTrue(apiAutoUtilities.get().getEventByEventId(matchedEventIDs.get().get(0)) != null, "No matching Event Id found");
        adjusted.set(eventByEventId.get("eventsList.content[0].payload").toString());
        adjusted.set(adjusted.get().replace("\"", "").replace(":", ""));
    }

    public void verifyCreatedValuesWithEventValues() {
        String frontportion = "typeINBOUND_CORRESPONDENCE_SAVE_EVENT,documentkeywordFields";
        String created = sendEventRequest.get().toString().replace("\"", "").replace("{", "").replace("keywordTypeName", "").replace("value", "").replace("}", "").
                replace("[", "").replace("]", "").replace(":", "").replace(frontportion, "");
        int begainCutIndex = 27;
        created = created.substring(begainCutIndex);
        int cutOffCharIndex = created.indexOf("propertiescreatedBy") - 1;
        created = created.toLowerCase().substring(0, cutOffCharIndex);
        List<String> createdList = new ArrayList<>(Arrays.asList(created.split(",")));
        for (String each : createdList) {
            if ("entkeywordfieldsstatus".equals(each)) {
                each = "status";
            }
            if ("barcodeid".equals(each)) {
                each = "barcode";
            }
            if ("updatedbyuser".equals(each)) {
                each = "updatedby";
            }
            if ("notificationid".equals(each)) {
                each = "returnednotificationid";
            }
            if ("replacementdocumentid".equals(each)) {
                each = "replacementcorrespondenceid";
            }
            if ("rescanofdocumentid".equals(each)) {
                each = "rescanofcorrespondenceid";
            }
            if ("batch #".equals(each)) {
                each = "batchnumber";
            }
            if ("statusseton".equals(each)) {
                each = "statusdate";
            }
            if ("createdbyuser".equals(each)) {
                each = "createdby";
            }
            Assert.assertTrue(adjusted.get().toLowerCase().contains(each), "missing or incorrect field :" + each);
        }
    }

    public void verifyPostRequestIsBad() {
        World.generalSave.get().put("sendEventResponse", sendEvent.get().PostAPIWithParameter(sendEventRequest.get()).jsonPathEvaluator);
        Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("message").toString().contains("not valid"));
    }

    public void initiateinvalidTypevalueSendRequest(String typeValue) {
        sendEventRequest.set(sendEventTdu.get().getJsonFromFile("dms/sendEventBasic.json").jsonElement.getAsJsonObject());
        Assert.assertNotNull(World.generalSave.get().get("InboundDocumentId"), "World.generalSave.get().get(InboundDocumentId) is null");
        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentHandle", String.valueOf(World.generalSave.get().get("InboundDocumentId")));
        //setting inbound type from inboundType class level variable
        sendEventRequest.get().getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentType", inboundType.get());
        //  sendEventRequest.getAsJsonObject("type").
        sendEventRequest.get().addProperty("type", typeValue);
        //using phone number as a unique identifier in task creation
        World.generalSave.get().put("From Phone", RandomStringUtils.random(10, false, true));
        JsonObject phone = new JsonObject();
        phone.addProperty("keywordTypeName", "FromPhoneNumber");
        phone.addProperty("value", World.generalSave.get().get("From Phone").toString());
        sendEventRequest.get().get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(phone);
//        JsonObject channel = new JsonObject();
        phone.addProperty("keywordTypeName", "Channel");
        phone.addProperty("value", "Mail");
//        sendEventRequest.get("document").getAsJsonObject().getAsJsonArray("keywordFields").add(channel);
        sendEvent.get().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        sendEvent.get().setEndPoint("/ecms/connectionpoint/sendevent");
    }

    public void uploadInboundDocument(String inboundType, String caseNum) {
        World.generalSave.get().put("InboundDocumentId", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getnewInboundDocId(inboundType, caseNum)));
    }

    public void getInbDocCorrespondenceByDocID(String docId) {
        if ("RANDOM TEN".equals(docId)) {
            docId = RandomStringUtils.random(10, false, true);
        }
        String baseuri = "https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/inbound/";
        String endpoint = "document/" + docId;
        sendEvent.get().setbaseUri(baseuri);
        sendEvent.get().setEndPoint(endpoint);
        sendEventRequest.set(sendEvent.get().getAPI().apiJsonObject);
        Assert.assertEquals(sendEvent.get().statusCode, 200);
    }

    public void verifyInbDocResponseJsonByDocId(String docId) {
        String actualId = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.id").toString();
        expectedDocIdResponse.set(expectedInbDoc.get().getJsonFromFile("dms/inboundDocument17934.json").jsonElement.getAsJsonObject());
        Assert.assertEquals(sendEventRequest.get(), expectedDocIdResponse.get());
        Assert.assertEquals(actualId, docId);
    }

    public void verifyStatusFailedInbCorrDocResponse() {
        String actualStatus = sendEvent.get().jsonPathEvaluator.get("status").toString().toLowerCase().trim();
        Assert.assertEquals(actualStatus, "failed", "Status did not fail. Actual status: " + actualStatus);
    }

    public void initiateSendDocumentRequest(Map<String, String> dataTable) {
        Map<String, String> request = new HashedMap();
        request.putAll(dataTable);
        if ("previouslyCreated".equalsIgnoreCase(dataTable.get("id"))) {
            request.put("id", String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")));
        }
        JSONObject docRequest = new JSONObject(request);
        if ("RandomString500".equals(dataTable.get("note"))) {
            docRequest.put("note", randomeString500.get());
        }
        String stringDocRequest = docRequest.toString();
        if (dataTable.containsKey("consumerId")) {
            stringDocRequest = stringDocRequest.replace("\"[", "[").replace("]\"", "]");
        }
        if ("null".equals(dataTable.get("consumerId"))) {
            stringDocRequest = stringDocRequest.replace("\"null\"", "null");
        }
        if ("null".equals(dataTable.get("caseId"))) {
            stringDocRequest = stringDocRequest.replace("\"null\"", "null");
        }
        if ("null".equals(dataTable.get("replacementDocumentId"))) {
            stringDocRequest = stringDocRequest.replace("\"null\"", "null");
        }
        String docUrl = ConfigurationReader.getProperty("apiBffCorrespondence") + "/ecms/correspondence/inbound/document";
        apiAutoUtilities.get().postInboundDocument(docUrl, stringDocRequest);
        Assert.assertEquals(apiAutoUtilities.get().statusCode, 200);
    }

    public void verifyAtleastOneOtherFieldRequired() {
        String actualMsg = apiAutoUtilities.get().jsonPathEvaluator.get("message").toString().toLowerCase().trim();
        String expectedFieldReqMsg = "There needs to be atleast one field to update with ID".toLowerCase();
        Assert.assertEquals(actualMsg, expectedFieldReqMsg, "Actaual message: " + actualMsg + " Expected message: " + expectedFieldReqMsg);
    }

    public void getInbDocVerifyValues(String docId, Map<String, String> dataTable) {
        if ("previouslyCreated".equalsIgnoreCase(docId)) {
            docId = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        getInbDocCorrespondenceByDocID(docId);
        for (String key : dataTable.keySet()) {
            switch (key.toUpperCase()) {
                case "UPDATEDBY":
                    String actualtedUpBy = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.updatedBy").toString();
                    String expectedUpBy = dataTable.get(key);
                    Assert.assertEquals(actualtedUpBy, expectedUpBy, "Actual UpdatedBy: " + actualtedUpBy + " Expected UpdatedBy: " + expectedUpBy);
                    break;
                case "UPDATEDON":
                    String expectedUpOn = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.updatedOn").toString();
                    Assert.assertFalse(expectedUpOn.isEmpty());
                    apiAutoUtilities.get().getDateTimeNow("Current_SysDate");
                    break;
                case "STATUSSETON":
                    String statusSetOn = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.statusDate").toString();
                    Assert.assertFalse(statusSetOn.isEmpty());
                    apiAutoUtilities.get().getDateTimeNow("Current_SysDate");
                    break;
                case "STATUS":
                    String actualInbStatus = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.status").toString();
                    String expectedInbStatus = dataTable.get(key);
                    Assert.assertEquals(actualInbStatus, expectedInbStatus, "Actual Status: " + actualInbStatus + " Expected Status: " + expectedInbStatus);
                    break;
                case "CASEID":
                    if ("null".equals(dataTable.get("caseId"))) {
                        Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.caseId") == null);
                        break;
                    }
                    String actualCaseId = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.caseId").toString();
                    String expectedCaseId = dataTable.get(key);
                    Assert.assertEquals(actualCaseId, expectedCaseId, "Actual Status: " + actualCaseId + " Expected Status: " + expectedCaseId);
                    break;
                case "REPLACEMENTDOCUMENTID":
                    if ("null".equals(dataTable.get("replacementDocumentId"))) {
                        Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.replacementCorrespondenceId") == null);
                        break;
                    }
                    String actualReDocId = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.replacementCorrespondenceId").toString();
                    String expectedReDocIdId = dataTable.get(key);
                    Assert.assertEquals(actualReDocId, expectedReDocIdId, "Actual replacementDocumentId: " + actualReDocId + " Expected replacementDocumentId: " + expectedReDocIdId);
                    break;
                case "CONSUMERID":
                    if ("null".equals(dataTable.get("consumerId")) || "[]".equals(dataTable.get("consumerId"))) {
                        Assert.assertTrue(sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.consumerId") == null);
                        break;
                    }
                    String actualConId = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.consumerId").toString().replace(" ", "").replace("[", "").replace("]", "");
                    String expectedConId = dataTable.get(key).replace(" ", "").replace("[", "").replace("]", "");
                    String[] actualArry = actualConId.split(",");
                    String[] expectedArry = expectedConId.split(",");
                    List<String> actList = new ArrayList<>(Arrays.asList(actualArry));
                    List<String> expList = new ArrayList<>(Arrays.asList(expectedArry));
                    Collections.sort(actList);
                    Collections.sort(expList);
                    Assert.assertEquals(actList, expList, "Actual ConsumerId: " + actList + " Expected ConsumerId: " + expList);
                    break;
                case "NOTE":
                    String actualNote = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.notes[0].text").toString().trim();
                    String expectedNote = dataTable.get(key).toUpperCase();
                    if ("RandomString500".equals(dataTable.get("note"))) {
                        expectedNote = randomeString500.get().replaceAll("[,]", "").toUpperCase().trim();
                    }
                    Assert.assertEquals(actualNote, expectedNote, "Actual note: " + actualNote + " Expected note: " + expectedNote);
                    break;
                case "DOCUMENTTYPE":
                    String actualDocType = sendEvent.get().jsonPathEvaluator.get("inboundCorrespondence.correspondenceType").toString();
                    String expectedDocType = dataTable.get(key);
                    Assert.assertEquals(actualDocType, expectedDocType, "Actual Document Type: " + actualDocType + " Expected Document Type: " + expectedDocType);
                    break;
                default:
                    Assert.fail("Key Provided does not match cases >>> " + key);
            }
        }
    }

    public void verifyIdIsRequiredMsg(String errorMsg) {
        String actualmsg = apiAutoUtilities.get().jsonPathEvaluator.get("message").toString().trim();
        Assert.assertEquals(actualmsg, errorMsg);
    }

    public void initiateSendDocumentWithOutID(Map<String, String> dataTable) {
        JSONObject docRequest = new JSONObject(dataTable);
        String stringDocRequest = docRequest.toString();
        String docUrl = ConfigurationReader.getProperty("apiBffCorrespondence") + "/ecms/correspondence/inbound/document";
        apiAutoUtilities.get().postInboundDocument(docUrl, stringDocRequest);
        Assert.assertEquals(apiAutoUtilities.get().statusCode, 400);
    }

    public void verifyLinkedToCaseUI(String cid, String caseId) {
        BrowserUtils.waitFor(4);
        waitForPageToLoad(10);
        if ("fromRequest".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentId").toString();
        } else if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentIdDigital").toString();
        }
        if ("RANDOM".equalsIgnoreCase(caseId)) {
            caseId = World.generalSave.get().get("CASE").toString();
            if (caseId.charAt(0) == '0')
                caseId = caseId.substring(1, caseId.length());
        } else if ("previouslyCreated".equalsIgnoreCase(caseId)) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            caseId = caseConsumerId.get("caseId");
        }
        if ("ApplicationCaseId".equalsIgnoreCase(caseId)) {
            caseId = World.generalSave.get().get("ApplicationCaseId").toString();
        } else if ("previouslyCreated".equalsIgnoreCase(caseId)) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            caseId = caseConsumerId.get("caseId");
        }
        Assert.assertTrue(inboundCorrespondencePage.cid.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.cid.getText().trim().equalsIgnoreCase(cid));
        //updated logic irrespective of position
        /*  scrollToElement(inboundCorrespondencePage.firstLink.get(2));
        BrowserUtils.waitFor(1);
        Assert.assertTrue(inboundCorrespondencePage.firstLink.get(2).getText().trim().equalsIgnoreCase("Case"));
        Assert.assertTrue(inboundCorrespondencePage.firstLink.get(1).getText().trim().equalsIgnoreCase(caseId));
        */
        boolean status = false;
        List<WebElement> listofids = inboundCorrespondencePage.iblinkids;
        List<WebElement> listofnames = inboundCorrespondencePage.iblinknames;

        for (int i = 0; i < listofids.size(); i++) {
            if (listofids.get(i).getText().equalsIgnoreCase(caseId) && listofnames.get(i).getText().equalsIgnoreCase("Case")) {
                status = true;
                break;
            }
        }
        if (status)
            Assert.assertTrue(true);
        else
            Assert.assertFalse(false);
    }

    public void verifyLinkedToTaskUI(String cid, String taskid) {
        waitForPageToLoad(10);
        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("inboundDocumentId".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }

        Assert.assertTrue(inboundCorrespondencePage.cid.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.cid.getText().trim().equalsIgnoreCase(cid));

        if ("previouslycreatedtaskId".equalsIgnoreCase(taskid)) {
            taskid = CRM_TaskManagementStepDef.srID.get();
        } else if ("previouslycreatedServicetaskId".equalsIgnoreCase(taskid)) {
            taskid = CRM_TaskManagementStepDef.taskId.get();
        }

        boolean status = false;
        List<WebElement> listofids = inboundCorrespondencePage.iblinkids;
        List<WebElement> listofnames = inboundCorrespondencePage.iblinknames;

        for (int i = 0; i < listofids.size(); i++) {
            if (listofids.get(i).getText().equalsIgnoreCase(taskid) && listofnames.get(i).getText().equalsIgnoreCase("Task")) {
                status = true;
                break;
            }
        }
        if (status)
            Assert.assertTrue(true);
        else
            Assert.assertFalse(false);
    }

    public boolean verifyLinkedToApplicationSRUI(String cid, String appsrId) {
        waitForPageToLoad(10);
        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("inboundDocumentId".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }

        Assert.assertTrue(inboundCorrespondencePage.cid.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.cid.getText().trim().equalsIgnoreCase(cid));

        boolean status = false;
        if ("previouslycreated".equalsIgnoreCase(appsrId))
            appsrId = String.valueOf(World.generalSave.get().get("Application SR ID"));

        List<WebElement> listofids = inboundCorrespondencePage.iblinkids;
        List<WebElement> listofnames = inboundCorrespondencePage.iblinknames;
        List<WebElement> listoftypes = inboundCorrespondencePage.iblinktypes;

        for (int i = 0; i < listofids.size(); i++) {
            if (listofids.get(i).getText().equalsIgnoreCase(appsrId) && listofnames.get(i).getText().equalsIgnoreCase("Service Request") && listoftypes.get(i).getText().equalsIgnoreCase("Application SR")) {
                status = true;
                break;
            }
        }

        return status;
    }


    public void verifyMultipleTaskTypesCreated(List<String> taskTypes) {
        String[] tasks = EventsUtilities.getGenericMatch(sendEvent.get().jsonPathEvaluator.get("message").toString(), "\\[(.*?)\\]").split(",");
        boolean found = false;
        String failType = "";
        for (String task : tasks) {//for each task
            for (String taskType : taskTypes) {//check if it's an expected task type
                if (verifyTaskTypeIdOfTask(BrowserUtils.validNumberFilter(task), taskType)) {
                    found = true;
                    break;
                } else {
                    failType = "Task Id - " + task + " | Expected Task Type Id - " + taskType;
                }
            }
            Assert.assertTrue(found, failType);
            found = false;
        }


        boolean foundTask = false;
        String failTask = "";
        for (String taskType : taskTypes) {//for each type id
            for (String task : tasks) {//check if all are found
                if (verifyTaskTypeIdOfTask(BrowserUtils.validNumberFilter(task), taskType)) {
                    foundTask = true;
                    break;
                } else {
                    failTask = "Task Id - " + task + " | Expected Task Type Id - " + taskType;
                }
            }
            Assert.assertTrue(foundTask, failTask);
            foundTask = false;
        }
    }

    public void verifyLinksComponentIsDisplayed() {
        BrowserUtils.waitFor(4);
        waitForPageToLoad(10);
        Assert.assertTrue(inboundCorrespondencePage.linkComponentHeader.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.linkComponentHeader.getText().trim().equalsIgnoreCase("link"));
        Assert.assertTrue(inboundCorrespondencePage.linkComponentHeader2.getText().trim().contains("LINKS"));
    }

    public void verifyLinkedToConsumerUI(String cid, String consumerId) {
        BrowserUtils.waitFor(4);
        waitForPageToLoad(10);
        if ("fromRequest".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentId").toString();
        } else if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentIdDigital").toString();
        }
        Assert.assertTrue(inboundCorrespondencePage.cid.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.cid.getText().trim().equalsIgnoreCase(cid));

        /*if ("Consumer Profile".equalsIgnoreCase(inboundCorrespondencePage.firstLink.get(2).getText().trim())) {
            Assert.assertTrue(inboundCorrespondencePage.cid.isDisplayed());
            Assert.assertTrue(inboundCorrespondencePage.cid.getText().trim().equalsIgnoreCase(cid));
            scrollToElement(inboundCorrespondencePage.secondLink.get(2));
            BrowserUtils.waitFor(1);
            Assert.assertTrue(inboundCorrespondencePage.firstLink.get(2).getText().trim().equalsIgnoreCase("Consumer Profile"));
            Assert.assertTrue(inboundCorrespondencePage.firstLink.get(1).getText().trim().equalsIgnoreCase(consumerId));
        } else {
            Assert.assertTrue(inboundCorrespondencePage.cid.isDisplayed());
            Assert.assertTrue(inboundCorrespondencePage.cid.getText().trim().equalsIgnoreCase(cid));
            scrollToElement(inboundCorrespondencePage.secondLink.get(2));
            BrowserUtils.waitFor(1);
            Assert.assertTrue(inboundCorrespondencePage.secondLink.get(2).getText().trim().equalsIgnoreCase("Consumer Profile"));
            Assert.assertTrue(inboundCorrespondencePage.secondLink.get(1).getText().trim().equalsIgnoreCase(consumerId));
        }*/

        boolean status = false;
        List<WebElement> listofids = inboundCorrespondencePage.iblinkids;
        List<WebElement> listofnames = inboundCorrespondencePage.iblinknames;

        for (int i = 0; i < listofids.size(); i++) {
            if (listofids.get(i).getText().equalsIgnoreCase(consumerId) && listofnames.get(i).getText().equalsIgnoreCase("Consumer Profile")) {
                status = true;
                break;
            }
        }
        if (status)
            Assert.assertTrue(true);
        else
            Assert.assertFalse(false);
    }

    public void clickOnRescannedCID() {
        inboundCorrespondencePage.rescannedCid.click();
        BrowserUtils.waitFor(4);
    }

    public void clickOnCID() {
        inboundCorrespondencePage.cid.click();
        BrowserUtils.waitFor(4);
    }


    public void verifyRescannedIdAndInformation(String cid, String rescannedCid) {
        BrowserUtils.waitFor(4);
        waitForPageToLoad(10);
        Assert.assertTrue(inboundCorrespondencePage.cid.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.cid.getText().trim().equalsIgnoreCase(cid));
        Assert.assertTrue(inboundCorrespondencePage.rescannedCid.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.rescannedCid.getText().trim().equalsIgnoreCase(rescannedCid));
    }

    public void verifyRescannedId(String rescannedCid) {
        BrowserUtils.waitFor(4);
        waitForPageToLoad(10);
        Assert.assertTrue(inboundCorrespondencePage.rescannedCid.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.rescannedCid.getText().trim().equalsIgnoreCase(rescannedCid));
    }

    public void verifyCid(String cid) {
        BrowserUtils.waitFor(4);
        waitForPageToLoad(10);
        Assert.assertTrue(inboundCorrespondencePage.cid.isDisplayed());
        Assert.assertTrue(inboundCorrespondencePage.cid.getText().trim().equalsIgnoreCase(cid));
    }

    public void createRescannedInboundDocument(Map<String, String> dataTable) {
        World.generalSave.get().put("GETNEWRESCANINBOUNDDOCID", Integer.parseInt(BrowserUtils.validNumberFilter(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createRescannedInboundDocument(dataTable))));
    }

    public void verifyOriginalDocumentUpdated(Map<String, String> dataTable) {
        JsonPath actual = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(String.valueOf(World.generalSave.get().get("InboundDocumentId")));
        for (String expected : dataTable.keySet()) {
            switch (expected) {
                case "ReplacementDocumentId":
                    Assert.assertTrue(String.valueOf(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID")).equalsIgnoreCase(actual.get("inboundCorrespondence." + "replacementCorrespondenceId") + ""));
                    break;
                case "updatedBy":
                    Assert.assertTrue("3353".equalsIgnoreCase(actual.get("inboundCorrespondence." + "updatedBy")));
                    break;
                case "updatedOn":
                    Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(actual.get("inboundCorrespondence." + "updatedOn").toString().substring(0, 10)), getCurrentDate()));
                    break;
                default:
                    Assert.assertEquals(actual.get("inboundCorrespondence." + expected), dataTable.get(expected));
            }
        }
    }

    public void verifyTasksNotLinkedToRescannedDocument() {
        JsonPath original = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("InboundDocumentId").toString());
        JsonPath rescan = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
        List<Map<String, Object>> originalDocumentLinks = original.getList("externalLinkDetails.content");
        List<Map<String, Object>> rescannedDocumentLinks = rescan.getList("externalLinkDetails.content");
        List<Integer> expected = new ArrayList<>();
        List<Integer> actual = new ArrayList<>();
        for (Map<String, Object> originalDocumentLink : originalDocumentLinks) {
            if (("Task".equalsIgnoreCase(String.valueOf(originalDocumentLink.get("name")))) || ("Service Request".equalsIgnoreCase(String.valueOf(originalDocumentLink.get("name"))))) {
                expected.add(Integer.parseInt(String.valueOf(originalDocumentLink.get("id"))));
            }
        }
        for (Map<String, Object> rescannedDocumentLink : rescannedDocumentLinks) {
            if (("Task".equalsIgnoreCase(String.valueOf(rescannedDocumentLink.get("name")))) || ("Service Request".equalsIgnoreCase(String.valueOf(rescannedDocumentLink.get("name"))))) {
                actual.add(Integer.parseInt(String.valueOf(rescannedDocumentLink.get("id"))));
            }
        }
        Collections.sort(expected);
        Collections.sort(actual);
        Assert.assertNotEquals(actual, expected, " list ARE the same | " + actual + "\n" + expected);

    }

    public void verifyTaskTypesLinkedToRescannedDocument(List<Integer> expected) {
        JsonPath rescan = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
        List<Map<String, Object>> rescannedDocumentLinks = rescan.getList("externalLinkDetails.content");
        List<JsonPath> taskDetails = new ArrayList<>();
        List<Integer> actualTask = new ArrayList<>();
        boolean found = false;
        for (Map<String, Object> rescannedDocumentLink : rescannedDocumentLinks) {
            if ("Task".equalsIgnoreCase(String.valueOf(rescannedDocumentLink.get("name")))) {
                taskDetails.add(apiAutoUtilities.get().getTaskByTaskId(rescannedDocumentLink.get("id") + ""));
                found = true;
            }
        }
        for (JsonPath taskType : taskDetails) {
            actualTask.add(Integer.valueOf(taskType.getString("tasks[0].taskVO.taskTypeId")));
        }
        Collections.sort(expected);
        Collections.sort(actualTask);
        Assert.assertEquals(actualTask.size(), expected.size());
        Assert.assertTrue(found, "no tasks found");
        for (int index = 0; index < expected.size(); index++) {
            Assert.assertEquals(actualTask.get(index), expected.get(index));
        }
    }

    public void clickTaskLinkByType(String taskType) {
        waitFor(9);
        scrollDown();
        waitFor(1);
        List<WebElement> tasks = viewInboundCorrespondenceDetailsUIAPIPage.linkTaskIdColumn;
        String id = "";
        for (int index = 0; index < tasks.size(); index++) {
            if (viewInboundCorrespondenceDetailsUIAPIPage.linkTaskTypeCoumn.get(index).getText().equalsIgnoreCase(taskType)) {
                id = BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.linkTaskIdColumn.get(index).getText().trim());
                viewInboundCorrespondenceDetailsUIAPIPage.linkTaskIdColumn.get(index).click();
            }
        }
        waitFor(5);
        waitForPageToLoad(10);
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//*[contains(text(),'description')]")).isDisplayed());
    }

    public void verifyCancelledTasksNotLinkedRescannedDocument() {
        JsonPath original = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("InboundDocumentId").toString());
        JsonPath rescan = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
        List<Map<String, Object>> originalDocumentLinks = original.getList("externalLinkDetails.content");
        List<Map<String, Object>> rescannedDocumentLinks = rescan.getList("externalLinkDetails.content");

        Map<String, String> originalTasks = new HashedMap();
        boolean found = false;
        for (Map<String, Object> originalDocumentLink : originalDocumentLinks) {
            if ("Task".equalsIgnoreCase(originalDocumentLink.get("name") + "")) {
                found = true;
                String id = originalDocumentLink.get("id") + "";
                originalTasks.put(id, apiAutoUtilities.get().getTaskByTaskId(id).getString("tasks[0].taskVO.taskStatus"));
            }
        }
        Assert.assertTrue(found);
        found = false;
        for (Map.Entry<String, String> task : originalTasks.entrySet()) {
            if ("Cancelled".equalsIgnoreCase(task.getValue()) || "Complete".equalsIgnoreCase(task.getValue())) {
                found = true;
                for (Map<String, Object> rescannedDocumentLink : rescannedDocumentLinks) {
                    Assert.assertFalse(task.getKey().equalsIgnoreCase(rescannedDocumentLink.get("id") + ""));
                }
            }
        }
        Assert.assertTrue(found);
    }

    public void verifySuccessResponse() {
        Assert.assertTrue("success".equalsIgnoreCase(sendEvent.get().jsonPathEvaluator.getString("status")));
    }

    public void sendEventRequestForServiceRequest() {
        World.generalSave.get().put("sendEventResponse", sendEvent.get().PostAPIWithParameter(sendEventRequest.get()).jsonPathEvaluator);
        Assert.assertEquals(sendEvent.get().statusCode, 200, "send event api call failed - " + sendEvent.get().statusCode);
        String responseMessage = sendEvent.get().jsonPathEvaluator.get("message").toString();
        CRM_TaskManagementStepDef.taskId.set(responseMessage.substring(responseMessage.indexOf("[") + 1, responseMessage.indexOf("]")));
    }

    public void verifySuccessResponseNoTask() {
        Assert.assertEquals(sendEvent.get().statusCode, 200);
    }

    public void verifyErrorMessage(String statusCode, String message) {
        Assert.assertEquals(Integer.parseInt(statusCode), sendEvent.get().statusCode);
        Assert.assertTrue(message.equalsIgnoreCase(sendEvent.get().jsonPathEvaluator.getString("message")));
    }

    /**
     * @param cid
     * @param dataTable if multiple tasks or any other type of link
     *                  pass in the follow format
     *                  key - "type" eg "task"
     *                  value - "id,id"
     */
    public void verifyLinksToInboundDocument(String cid, Map<String, String> dataTable) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, String>> expectedLinks = response.getList("externalLinkDetails.content");
        for (String links : dataTable.keySet()) {
            ArrayList<String> expected = new ArrayList<>();
            ArrayList<String> actual = new ArrayList<>();
            Map<String, List<String>> inbDoc = new HashedMap();
            ArrayList<String> taskTypeIds = new ArrayList<>();
            getLinkIdsByTypeAndFillList(expectedLinks, actual, links);
            inbDoc.put(String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")), actual);
            World.generalSave.get().put("SameSetTasks", inbDoc);
            if (dataTable.get(links).contains(",")) {
                expected.addAll(Arrays.asList(dataTable.get(links).split(",")));
                Assert.assertEquals(expected.size(), actual.size());
                //check task type ids
                for (String s : actual) {
                    taskTypeIds.add(getTaskType(s));
                }
                for (String link : expected) {
                    Assert.assertTrue(taskTypeIds.contains(link));
                }
            } else {
                Assert.assertEquals(actual.size(), 1, "more than one " + links + " type links exist");
                Assert.assertEquals(dataTable.get(links), getTaskType(actual.get(0)));
            }
        }
    }

    private void storeTaskLinksByIdInWorld(String cid, String taskTypeId) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, String>> expectedLinks = response.getList("externalLinkDetails.content");
        List<String> cids = new ArrayList<>();
        List<String> idsSameType = new ArrayList<>();
        Map<String, List<String>> taskIdsSameType = new HashedMap();
        getLinkIdsByTypeAndFillList(expectedLinks, cids, "Task");
        for (String id : cids) {
            if (taskTypeId.equalsIgnoreCase(getTaskType(id))) {
                idsSameType.add(id);
            }
        }
        taskIdsSameType.put(taskTypeId, idsSameType);
        World.generalSave.get().put(cid, taskIdsSameType);
    }


    private void getLinkIdsByTypeAndFillList(List<Map<String, String>> linksInResponse, List<String> idsList, String type) {
        for (Map<String, String> expectedLink : linksInResponse) {
            if (type.equalsIgnoreCase(expectedLink.get("name"))) {
                idsList.add(String.valueOf(expectedLink.get("id")));
            }
        }
    }

    public void verifySameSetTasksLinkedToNewDocument(String cid, String cid2) {
        JsonPath original = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, String>> originalLinks = original.getList("externalLinkDetails.content");
        JsonPath sameSet = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid2);
        List<Map<String, String>> sameSetLinks = sameSet.getList("externalLinkDetails.content");
        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        getLinkIdsByTypeAndFillList(originalLinks, expected, "Task");
        getLinkIdsByTypeAndFillList(sameSetLinks, actual, "Task");
        Assert.assertEquals(actual, expected);
    }

    public void verifySameSetTasksNOTLinkedToNewDocument(String cid, String cid2) {
        JsonPath original = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, String>> originalLinks = original.getList("externalLinkDetails.content");
        JsonPath sameSet = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid2);
        List<Map<String, String>> sameSetLinks = sameSet.getList("externalLinkDetails.content");
        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        getLinkIdsByTypeAndFillList(originalLinks, expected, "Task");
        getLinkIdsByTypeAndFillList(sameSetLinks, actual, "Task");
        Assert.assertNotEquals(actual, expected);
    }

    public boolean hasTaskTypes(String cid, List<String> data) {
        JsonPath links = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> actualLinks = links.getList("externalLinkDetails.content");
        boolean found = false;
        Map<String, Integer> actual = new HashMap<>();
        for (Map<String, Object> link : actualLinks) {
            if (String.valueOf(link.get("name")).equalsIgnoreCase("task")) {
                actual.put(String.valueOf(link.get("id")), API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getTaskType((Integer) link.get("id")));
                waitFor(4);
            }

        }
        for (String expected : data) {
            found = actual.containsValue(Integer.valueOf(expected));
            if (!found) {
                return false;
            }
        }
        return found;
    }

    public void verifyActiveLinksAPI(String cid) {
        JsonPath links = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> actualResponse = links.getList("externalLinkDetails.content");
        for (Map<String, Object> actual : actualResponse) {
            Assert.assertEquals(String.valueOf(actual.get("createdBy")), String.valueOf(actual.get("updatedBy")));
            Assert.assertEquals(String.valueOf(actual.get("createdOn")), String.valueOf(actual.get("updatedOn")));
            Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(actual.get("effectiveStartDate"))));
            Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(actual.get("createdOn"))));
            Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(actual.get("updatedOn"))));
            Assert.assertNotNull(actual.get("id"));
            Assert.assertNotNull(actual.get("internalId"));
            Assert.assertNotNull(actual.get("internalRefType"));
            Assert.assertNotNull(actual.get("name"));
            Assert.assertNotNull(actual.get("externalLinkId"));
            Assert.assertNotNull(actual.get("externalRefType"));
            Assert.assertNotNull(actual.get("correlationId"));
            Assert.assertNull(actual.get("effectiveEndDate"));
        }
    }

    public void unlinkFirstRandomLinkIBDocument(String cid) {
        JsonPath links = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> actualResponse = links.getList("externalLinkDetails.content");
        String name = String.valueOf(actualResponse.get(0).get("name"));
        Integer id = Integer.valueOf(String.valueOf(actualResponse.get(0).get("id")));
        World.generalSave.get().put("id", id);
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().unlinkfromInboundDocument(cid, name, String.valueOf(id));
    }

    public void verifyInactiveActiveLinksAPI(String cid) {
        JsonPath inactiveLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceInactiveLinks(cid);
        JsonPath links = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> actualResponse = links.getList("externalLinkDetails.content");
        List<Map<String, Object>> actualinactiveLinksResponse = inactiveLinks.getList("externalLinkDetails.content");
        for (Map<String, Object> link : actualinactiveLinksResponse) {
            for (Map<String, Object> exp : actualResponse) {
                if (Integer.parseInt(String.valueOf(link.get("id"))) == Integer.parseInt(String.valueOf(exp.get("id")))) {
                    Assert.assertEquals(String.valueOf(link.get("createdBy")), String.valueOf(exp.get("createdBy")));
                    Assert.assertEquals(String.valueOf(link.get("createdOn")), String.valueOf(exp.get("createdOn")));
                    Assert.assertEquals(String.valueOf(link.get("effectiveStartDate")), String.valueOf(exp.get("effectiveStartDate")));
                    Assert.assertEquals(String.valueOf(link.get("internalRefType")), String.valueOf(exp.get("internalRefType")));
                    Assert.assertEquals(String.valueOf(link.get("name")), String.valueOf(exp.get("name")));
                    Assert.assertEquals(String.valueOf(link.get("externalRefType")), String.valueOf(exp.get("externalRefType")));
                    if (Integer.parseInt(String.valueOf(link.get("id"))) == Integer.parseInt(String.valueOf(World.generalSave.get().get("id")))) {
                        Assert.assertNull(link.get("effectiveEndDate"));
                    }
                }
            }
        }
    }

    public void createRequestToUpdateServiceRequest(Map<String, String> dataTable) {
        taskRequest.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/updateServiceRequest.json").jsonElement);
        taskRequest.get().getAsJsonObject("task").addProperty("taskId", CRM_TaskManagementStepDef.srID.get());
        List<String> listOfPlans = Arrays.asList("ANTHEM", "CARESOURCE", "MDWISE", "MHS", "UNITEDHEALTHCARE");
        List<String> listOfPrograms = Arrays.asList("Healthy Indiana Plan", "Hoosier Care Connect", "Hoosier Healthwise");
        List<String> listOfReasons = Arrays.asList("ANOTHER_MCE_DRUG_LIST_BETTER_MEETS_NEED", "FSSA_OR_DESIGNEES_FOUND_POOR_QUALITY_HEALTHCARE_COVERAGE", "FSSA_TOOK_CORRECTION_ACTION_AGAINST_PLAN", "LACK_OF_ACCESS_TO_NECESSARY_SVCS_COVERED_UNDER_PLANS_CONTRACT",
                "LIMITED_ACCESS_TO_PCP_OR_OTHER_HEALTH_SVCS_CLOSE_TO_HOME", "MAJOR_LANGUAGE_OR_CULTURAL_BIAS", "MEMBERS_PCP_LEFT_THE_MCE_AND_IS_AVAILABLE_IN_ANOTHER_MCE", "MEMBER_NEEDS_RELATED_SVCS_BUT_NOT_ALL_SVCS_AVAILABLE",
                "PLAN_CONTRACTOR_DID_NOT_PROVIDER_COVERED_SVCS", "PLAN_DID_NOT_COMPLY_WITH_STANDARDS_OF_CARE", "PLAN_DID_NOT_HAVE_PROVIDERS_EXPERIENCED_IN_MEMBERS_NEEDS", "RECEIVING_POOR_QUALITY_OF_CARE", "SVC_NOT_COVERED_BY_PLAN_FOR_MORAL_RELIGIOUS_OJBECTIONS");
        for (String keyword : dataTable.keySet()) {
            JsonObject tempObject = new JsonObject();
            switch (keyword) {
                case "RID":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("selectionFieldName", "RID #");
                        tempObject.addProperty("selectionNumeric", getRandomNumber(12));
                    }
                    break;
                case "Grievance":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("selectionFieldName", "Grievance #");
                        tempObject.addProperty("selectionVarchar", getRandomNumber(10));
                    }
                    break;
                case "FromName":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("selectionFieldName", "From Name");
                        tempObject.addProperty("selectionVarchar", faker.get().name().firstName() + " " + faker.get().name().lastName());
                    }
                    break;
                case "FromPhone":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("selectionFieldName", "From Phone");
                        tempObject.addProperty("selectionVarchar", getRandomNumber(10));
                    }
                    break;
                case "Email":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("selectionFieldName", "Email");
                        tempObject.addProperty("selectionVarchar", faker.get().internet().emailAddress());
                    }
                    break;
                case "ProgramRequired":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("selectionFieldName", "Program Required");
                        tempObject.addProperty("selectionVarchar", listOfPrograms.get(random.get().nextInt(listOfPrograms.size())));
                    }
                    break;
                case "CurrentPlan":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("selectionFieldName", "Current Plan");
                        tempObject.addProperty("selectionVarchar", listOfPlans.get(random.get().nextInt(listOfPlans.size())));
                    }
                    break;
                case "DesiredPlan":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("selectionFieldName", "Desired Plan");
                        tempObject.addProperty("selectionVarchar", listOfPlans.get(random.get().nextInt(listOfPlans.size())));
                    }
                    break;
                case "Reason":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("fieldGroup", "Reason and Explanation");
                        tempObject.addProperty("selectionFieldName", "Reason");
                        tempObject.addProperty("selectionVarchar", listOfReasons.get(random.get().nextInt(listOfReasons.size())));
                    }
                    break;
                case "Explanation":
                    if (dataTable.get(keyword).equalsIgnoreCase("random")) {
                        tempObject.addProperty("fieldGroup", "Reason and Explanation");
                        tempObject.addProperty("selectionFieldName", "Explanation");
                        tempObject.addProperty("selectionVarchar", faker.get().lorem().characters(10, 20));
                    }
                    break;
                case "DateGrievanceReceived":
                    if (dataTable.get(keyword).equalsIgnoreCase("todayDate")) {
                        tempObject.addProperty("selectionFieldName", "Date Grievance Received");
                        tempObject.addProperty("selectionDate", BrowserUtils.getFutureDateFormatYYYYMMdd(random.get().nextInt(10)));
                    }
                    break;
                case "NewPlanStartDate":
                    if (dataTable.get(keyword).equalsIgnoreCase("todayDate")) {
                        tempObject.addProperty("selectionFieldName", "New Plan Start Date");
                        tempObject.addProperty("selectionDate", BrowserUtils.getFutureDateFormatYYYYMMdd(random.get().nextInt(10)));
                    }
                    break;
            }
            taskRequest.get().getAsJsonObject("task").getAsJsonArray("taskDetails").add(tempObject);
        }
    }

    public void updateServiceRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskManagementURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("mars/servicerequest/save");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(taskRequest.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public void retrieveServiceRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskManagementURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/taskmanagement/tasks/" + CRM_TaskManagementStepDef.srID.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        int size = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("tasks[0].taskVO.taskDetails.size()");
        for (int i = 0; i < size; i++) {
            switch (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionFieldName")) {
                case "Current Plan":
                    World.save.get().put("currentPlanName", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar"));
                    break;
                case "Desired Plan":
                    World.save.get().put("desiredPlanName", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar"));
                    break;
                case "New Plan Start Date":
                    World.save.get().put("newPlanStartDate", BrowserUtils.convertyyyyMMddToMMddyyyy(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionDate")));
                    break;
                case "RID #":
                    World.save.get().put("rid", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionNumeric"));
                    break;
                case "Grievance #":
                    World.save.get().put("grievanceNumber", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar"));
                    break;
                case "Date Grievance Received":
                    World.save.get().put("grievanceDate", BrowserUtils.convertyyyyMMddToMMddyyyy(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionDate")));
                    break;
                case "From Name":
                    String fullName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar");
                    String[] array = fullName.split(" ");
                    World.save.get().put("contactFirstName", array[0]);
                    World.save.get().put("contactLastName", array[1]);
                    break;
                case "From Phone":
                    World.save.get().put("contactPhone", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar"));
                    break;
                case "Email":
                    World.save.get().put("contactEmail", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar"));
                    break;
                case "Program Required":
                    World.save.get().put("program", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar"));
                    break;
                case "Reason":
                    switch (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar")) {
                        case "ANOTHER_MCE_DRUG_LIST_BETTER_MEETS_NEED":
                            World.save.get().put("reason", "reasonDrugList");
                            break;
                        case "FSSA_OR_DESIGNEES_FOUND_POOR_QUALITY_HEALTHCARE_COVERAGE":
                            World.save.get().put("reason", "reasonFssaQuality");
                            break;
                        case "FSSA_TOOK_CORRECTION_ACTION_AGAINST_PLAN":
                            World.save.get().put("reason", "reasonFssaCorrective");
                            break;
                        case "LACK_OF_ACCESS_TO_NECESSARY_SVCS_COVERED_UNDER_PLANS_CONTRACT":
                            World.save.get().put("reason", "reasonLackAccess");
                            break;
                        case "LIMITED_ACCESS_TO_PCP_OR_OTHER_HEALTH_SVCS_CLOSE_TO_HOME":
                            World.save.get().put("reason", "reasonCloseHome");
                            break;
                        case "MAJOR_LANGUAGE_OR_CULTURAL_BIAS":
                            World.save.get().put("reason", "reasonLanguageCultural");
                            break;
                        case "MEMBERS_PCP_LEFT_THE_MCE_AND_IS_AVAILABLE_IN_ANOTHER_MCE":
                            World.save.get().put("reason", "reasonPmpLeft");
                            break;
                        case "MEMBER_NEEDS_RELATED_SVCS_BUT_NOT_ALL_SVCS_AVAILABLE":
                            World.save.get().put("reason", "reasonRelatedServices");
                            break;
                        case "PLAN_CONTRACTOR_DID_NOT_PROVIDER_COVERED_SVCS":
                            World.save.get().put("reason", "reasonCoveredServices");
                            break;
                        case "PLAN_DID_NOT_COMPLY_WITH_STANDARDS_OF_CARE":
                            World.save.get().put("reason", "reasonEstablishedStandards");
                            break;
                        case "PLAN_DID_NOT_HAVE_PROVIDERS_EXPERIENCED_IN_MEMBERS_NEEDS":
                            World.save.get().put("reason", "reasonProviderExperience");
                            break;
                        case "RECEIVING_POOR_QUALITY_OF_CARE":
                            World.save.get().put("reason", "reasonPoorQuality");
                            break;
                        case "SVC_NOT_COVERED_BY_PLAN_FOR_MORAL_RELIGIOUS_OJBECTIONS":
                            World.save.get().put("reason", "reasonMoralReligious");
                            break;
                    }
                    break;
                case "Explanation":
                    World.save.get().put("explanationValue", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskDetails[" + i + "].selectionVarchar"));
                    break;
            }
        }

        int num = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("tasks[0].taskLinksVOS.size()");
        for (int i = 0; i < num; i++) {
            switch (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskLinksVOS[" + i + "].externalLinkRefType")) {
                case "OUTBOUND_CORRESPONDENCE":
                    World.save.get().put("outboundCorrId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskLinksVOS[" + i + "].externalLinkRefId"));
                    World.generalSave.get().put("externalRefId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskLinksVOS[" + i + "].internalRefId"));
                    break;
            }
        }

        String fullDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.createdOn");
        String expectedDate = fullDate.substring(0, fullDate.indexOf("T"));
        World.save.get().put("srCreateDate", BrowserUtils.convertyyyyMMddToMMddyyyy(expectedDate));
    }

    public void getCaseCorrespondenceAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/casecorrespondence");
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/caseCorrespondence.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", caseConsumerId.get("consumerId"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        World.save.get().put("memberName", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[0].firstName") + " " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[0].lastName"));
        World.save.get().put("addressLine1", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[0].channels.mail.addresses[0].addressLine1"));
        World.save.get().put("addressLine2", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[0].channels.mail.addresses[0].addressLine2"));
        World.save.get().put("addressCity", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[0].channels.mail.addresses[0].city"));
        World.save.get().put("addressState", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[0].channels.mail.addresses[0].state"));
        World.save.get().put("addressZip", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[0].channels.mail.addresses[0].zip"));
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public void getCaseCorrespondenceAPIforCase() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/casecorrespondence");
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/caseCorrespondenceAllChannels.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", caseConsumerId.get("caseId"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        for (int i = 0; i < API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("consumers").size(); i++) {
            World.generalList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].consumerId"));
            World.save.get().put("consumerFirstName" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].firstName"));
            World.save.get().put("consumerLastName" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].lastName"));
            World.save.get().put("defaultFlag" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].defaultFlag"));
            World.save.get().put("consumerRole" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].consumerRole"));
            World.save.get().put("mailUsability" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].channels.mail.usability.usableFlag"));
            World.save.get().put("emailUsability" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].channels.email.usability.usableFlag"));
            World.save.get().put("faxUsability" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].channels.fax.usability.usableFlag"));
            World.save.get().put("textUsability" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].channels.text.usability.usableFlag"));
        }
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public void getCaseCorrespondenceAPI(Map<String, String> datatable) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/casecorrespondence");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/caseCorrespondenceAllChannels.json");
        if ("DCEBcaseid".equalsIgnoreCase(datatable.get("caseId"))) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", datatable.get("caseId"));
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("includeNonDefaultRecipients", datatable.get("includeNonDefaultRecipients"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        for (int i = 0; i < API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("consumers").size(); i++) {
            World.generalList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].consumerId"));
            World.save.get().put("consumerId" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].consumerId"));
            World.save.get().put("consumerFirstName" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].firstName"));
            World.save.get().put("consumerLastName" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].lastName"));
            World.save.get().put("consumerMiddleName" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].middleName"));
            World.save.get().put("consumerSuffixName" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].suffix"));
        }
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public void retrieveLetterBodyDataByTypeAndConsumerID(String type) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiECMSLetterData"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/ecms/v1/letterdata");
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        JsonObject request = new JsonObject();
        request.addProperty("correspondenceDefinitionMMSCode", type);
        JsonObject anchor = new JsonObject();
        JsonArray consumerIDs = new JsonArray();
        consumerIDs.add(caseConsumerId.get("consumerId"));
        anchor.add("regardingConsumerId", consumerIDs);
        request.add("anchor", anchor);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        World.save.get().put("expectedBodyData", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data"));
    }

    public void retrieveBodyDataSourceByType(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiECMSLetterData"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/ecms/v1/letterdata");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        for (String keyword : data.keySet()) {
            switch (keyword) {
                case "correspondenceDefinitionMMSCode":
                    request.addProperty(keyword, data.get(keyword));
                    break;
                case "caseId":
                    if ("DCEBcaseid".equalsIgnoreCase(data.get(keyword))) {
                        anchor.addProperty(keyword, API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId());
                    } else {
                        anchor.addProperty(keyword, data.get(keyword));
                    }
                    break;
                case "regardingConsumerId":
                    String[] consumerids = data.get(keyword).split(",");
                    JsonArray consumerIDs = new JsonArray();
                    for (int i = 0; i < consumerids.length; i++) {
                        if (consumerids[i].equalsIgnoreCase("consumerId[" + i + "]"))
                            consumerids[i] = save.get().get("consumerId" + i);
                        consumerIDs.add(consumerids[i]);
                    }
                    anchor.add(keyword, consumerIDs);
                    break;
                default:
                    Assert.fail("No matching parameter");

            }
        }
        request.add("anchor", anchor);
        System.out.println(request);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        World.save.get().put("BodyDataSource", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data"));
    }

    public void validateBodyDataSource(Map<String, String> data) {
        for (String keyword : data.keySet()) {
            switch (keyword) {
                case "statusCode":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, Integer.parseInt(data.get(keyword)));
                    break;
                case "websitePin":
                    if (data.get(keyword).equalsIgnoreCase("previouslycreatedcaseidpin"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.websitePin"), World.save.get().get("Websitepin"));
                    else
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.websitePin"), data.get(keyword));
                    break;
                case "dueDate":
                    try {
                        if (data.get(keyword).equalsIgnoreCase("changeAllowedUntil_consumerId[0]"))
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.dueDate"), World.save.get().get("changeAllowedUntil_consumerId[0]"));
                        else if (data.get(keyword).equalsIgnoreCase("changeAllowedUntil_earliestdate")) {
                            String duedate = null;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                            if (sdf.parse(World.save.get().get("changeAllowedUntil_consumerId[0]")).before(sdf.parse(World.save.get().get("changeAllowedUntil_consumerId[1]"))))
                                duedate = World.save.get().get("changeAllowedUntil_consumerId[0]");
                            else
                                duedate = World.save.get().get("changeAllowedUntil_consumerId[1]");
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.dueDate"), duedate);
                        } else
                            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.dueDate"), data.get(keyword));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "firstName":
                    if (data.get(keyword).equalsIgnoreCase("consumerId[0].firstName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[0].firstName"), World.save.get().get("consumerFirstName0"));
                    if (data.get(keyword).equalsIgnoreCase("consumerId[1].firstName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[1].firstName"), World.save.get().get("consumerFirstName1"));
                    if (data.get(keyword).equalsIgnoreCase("consumerId[2].firstName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[2].firstName"), World.save.get().get("consumerFirstName2"));
                    break;
                case "lastName":
                    if (data.get(keyword).equalsIgnoreCase("consumerId[0].lastName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[0].lastName"), World.save.get().get("consumerLastName0"));
                    if (data.get(keyword).equalsIgnoreCase("consumerId[1].lastName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[1].lastName"), World.save.get().get("consumerLastName1"));
                    if (data.get(keyword).equalsIgnoreCase("consumerId[2].lastName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[2].lastName"), World.save.get().get("consumerLastName2"));
                    break;
                case "middleName":
                    if (data.get(keyword).equalsIgnoreCase("consumerId[0].middleName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[0].middleInitial"), World.save.get().get("consumerMiddleName0"));
                    if (data.get(keyword).equalsIgnoreCase("consumerId[1].middleName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[1].middleInitial"), World.save.get().get("consumerMiddleName1"));
                    if (data.get(keyword).equalsIgnoreCase("consumerId[2].middleName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[2].middleInitial"), World.save.get().get("consumerMiddleName2"));
                    break;
                case "suffixName":
                    if (data.get(keyword).equalsIgnoreCase("consumerId[0].suffixName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[0].suffix"), World.save.get().get("consumerSuffixName0"));
                    if (data.get(keyword).equalsIgnoreCase("consumerId[1].suffixName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[1].suffix"), World.save.get().get("consumerSuffixName1"));
                    if (data.get(keyword).equalsIgnoreCase("consumerId[2].suffixName"))
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.members[2].suffix"), World.save.get().get("consumerSuffixName2"));
                    break;
                case "errorMessage":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("errors[0].message"), data.get(keyword));
                    break;
                case "caseIderrorMessage":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("status"), data.get(keyword));
                    break;
                default:
                    Assert.fail("No matching parameter");

            }
        }

    }

    public void retrievewebsitepin(String caseid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiuam"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/case/pin?caseId=" + caseid + "");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        World.save.get().put("Websitepin", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("pin"));
        System.out.println("Websitepin: " + World.save.get().get("Websitepin"));
    }


    public void retrievebenefitstatus(String consumerId) {
        String consumerid = null;
        if (consumerId.equalsIgnoreCase("consumerId[0]"))
            consumerid = save.get().get("consumerId0");
        else if (consumerId.equalsIgnoreCase("consumerId[1]"))
            consumerid = save.get().get("consumerId1");
        else
            consumerid = consumerId;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiEligibilityURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/eb/enrollments/" + consumerid + "/benefitstatues");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertEquals("Enroll", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].consumerActions[0].consumerAction"));
        World.save.get().put("changeAllowedUntil_" + consumerId, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].consumerActions[0].changeAllowedUntil").split("T")[0]);
        System.out.println("changeAllowedUntil: " + consumerId + World.save.get().get("changeAllowedUntil"));
    }

    public void updatebenefitstatus(String consumerId, Map<String, String> dataTable) {
        if (consumerId.equalsIgnoreCase("consumerId[0]"))
            consumerId = save.get().get("consumerId0");
        else if (consumerId.equalsIgnoreCase("consumerId[1]"))
            consumerId = save.get().get("consumerId1");
        retrievebenefitstatus(consumerId);

        JsonArray benefitRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        JsonObject objectData = benefitRecords.get(0).getAsJsonObject();

        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "changeAllowedUntil":
                    objectData.getAsJsonArray("consumerActions").get(0).getAsJsonObject().addProperty("changeAllowedUntil", dataTable.get(keyword) + "T00:00:00.000000+00:00");
                    break;
                default:
                    Assert.fail("no matching parameter");
            }
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiEligibilityURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/eb/enrollments/benefitstatues");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(objectData.toString());
    }

    public void verifyLetterBodyData(List<String> dataTable) {
        String explanationAttribute = World.save.get().get("reason").replace("reason", "explanation");
        for (String element : dataTable) {
            if (element.equals("reasonAndExplanation")) {
                Map<String, Object> maps = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data");
                for (String key : maps.keySet()) {
                    if (key.startsWith("reason")) {
                        if (key.equals(World.save.get().get("reason"))) {
                            Assert.assertEquals(maps.get(key), "[X]", "Expected value for " + key + " is true");
                        } else {
                            Assert.assertEquals(maps.get(key), "[  ]", "Expected value for " + key + " is false");
                        }
                    } else if (key.startsWith("explanation")) {
                        if (key.equals(explanationAttribute)) {
                            Assert.assertEquals(maps.get(key), World.save.get().get("explanationValue"), "Expected value for " + key + " is " + World.save.get().get("explanationValue"));
                        } else {
                            Assert.assertNull(maps.get(key), "Expected value for " + key + " is null");
                        }
                    }
                }
            } else {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data." + element), World.save.get().get(element), element + " verification failed");
            }
        }
    }

    public void createRequestForEnrollment() {
        enrollmentRequest.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/createEnrollment.json").jsonElement);
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        String consumerId = caseConsumerId.get("consumerId");
        enrollmentRequest.get().addProperty("consumerId", consumerId);
        enrollmentRequest.get().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("consumerId", consumerId);
        enrollmentRequest.get().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("consumerId", consumerId);
    }

    public void createRequestForEnrollment(String consumerId) {
        enrollmentRequest.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/createEnrollmentDCEB.json").jsonElement);
        if (consumerId.equalsIgnoreCase("consumerId[0]"))
            consumerId = save.get().get("consumerId0");
        else if (consumerId.equalsIgnoreCase("consumerId[1]"))
            consumerId = save.get().get("consumerId1");
        enrollmentRequest.get().addProperty("consumerId", consumerId);
        enrollmentRequest.get().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("consumerId", consumerId);
    }

    public void sendEnrollmentRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiEligibilityURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/eb/enrollment/start");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(enrollmentRequest.get());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public void createRequestForOtherSegment(Map<String, String> dataTable) {
        JsonObject otherSegmentObject = new JsonObject();
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "consumerId":
                    if (dataTable.get(keyword).equals("previouslyCreated")) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        otherSegmentObject.addProperty(keyword, caseConsumerId.get("consumerId"));
                    }
                    break;
                case "startDate":
                    if (dataTable.get(keyword).equals("pastDate")) {
                        otherSegmentObject.addProperty(keyword, String.valueOf(LocalDate.now().minusMonths(random.get().nextInt(7) + 1)));
                    } else if (dataTable.get(keyword).equals("futureDate")) {
                        otherSegmentObject.addProperty(keyword, String.valueOf(LocalDate.now().plusMonths(random.get().nextInt(7) + 1)));
                    } else if (dataTable.get(keyword).equals("todayDate")) {
                        otherSegmentObject.addProperty(keyword, String.valueOf(LocalDate.now()));
                    }
                    break;
                case "endDate":
                    if (dataTable.get(keyword).equals("futureDate")) {
                        otherSegmentObject.addProperty(keyword, String.valueOf(LocalDate.now().plusMonths(random.get().nextInt(7) + 1)));
                    } else if (dataTable.get(keyword).equals("pastDate")) {
                        otherSegmentObject.addProperty(keyword, String.valueOf(LocalDate.now().minusMonths(random.get().nextInt(7) + 1)));
                    } else if (dataTable.get(keyword).equals("todayDate")) {
                        otherSegmentObject.addProperty(keyword, String.valueOf(LocalDate.now()));
                    }
                    break;
                case "segmentTypeCode":
                    otherSegmentObject.addProperty(keyword, dataTable.get(keyword));
                    break;
            }
        }
        otherSegmentRequest.get().add(otherSegmentObject);
    }

    public void sendOtherSegmentRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiEligibilityURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("mars/eb/enrollments/otherSegments");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(otherSegmentRequest.get());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public void getOtherEnrollmentSegmentAPI() {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiEligibilityURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("mars/eb/otherEnrollmentSegments/?consumerIds=" + caseConsumerId.get("consumerId") + "&segTypeCode=LILO");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public String getRCPValueByOtherEnrollmentSegmentResponse() {
        LocalDate startDate = LocalDate.parse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].startDate"));
        LocalDate endDate = LocalDate.parse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].endDate"));
        String segmentType = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].segmentTypeCode");
        if ((startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now()))
                && (endDate.isAfter(LocalDate.now()) || endDate.isEqual(LocalDate.now()))
                && segmentType.equals("LILO")) {
            return "[X]";
        } else {
            return "[  ]";
        }
    }

    public boolean verifyGivenDateIsPastDate(String startDt) {
        boolean result = false;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date actualStart = format.parse(startDt);
            Date expectedStart = format.parse(getCurrentDateInYearFormat());
            result = actualStart.before(expectedStart);
        } catch (ParseException e) {
            Assert.fail("Exception in verifying given date is in past Date");
        }
        return result;
    }

    public void createRequestForExternalTask(Map<String, String> dataTable) {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        request.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/externalTask.json").jsonElement);
        for (String data : dataTable.keySet()) {
            JsonObject tempObject = new JsonObject();
            switch (data) {
                case "CASE":
                    if (dataTable.get(data).equals("previouslyCreated")) {
                        tempObject.addProperty("externalRefId", caseConsumerId.get("caseId"));
                        tempObject.addProperty("externalRefType", data);
                    } else if (dataTable.get("taskTypeId").equals("16277")) {
                        tempObject.addProperty("externalRefId", apiCreateConsumerContactController.get().getCaseId());
                        tempObject.addProperty("externalRefType", data);
                    } else {
                        tempObject.addProperty("externalRefId", dataTable.get(data));
                        tempObject.addProperty("externalRefType", "CASE");
                    }
                    request.get().getAsJsonArray("externalLinks").add(tempObject);
                    break;
                case "CONSUMER":
                    if (dataTable.get(data).equals("previouslyCreated")) {
                        tempObject.addProperty("externalRefId", caseConsumerId.get("consumerId"));
                        tempObject.addProperty("externalRefType", data);
                    } else if (dataTable.get("taskTypeId").equals("16277")) {
                        tempObject.addProperty("externalRefId", apiCreateConsumerContactController.get().getExternalConsumerId());
                        tempObject.addProperty("externalRefType", data);
                    } else {
                        tempObject.addProperty("externalRefId", dataTable.get(data));
                        tempObject.addProperty("externalRefType", "CONSUMER");
                    }
                    request.get().getAsJsonArray("externalLinks").add(tempObject);
                    break;
                case "APPLICATION":
                    tempObject.addProperty("externalRefId", dataTable.get(data));
                    tempObject.addProperty("externalRefType", "APPLICATION");
                    request.get().getAsJsonArray("externalLinks").add(tempObject);
                    break;
                case "taskTypeId":
                    request.get().addProperty("taskTypeId", dataTable.get(data));
                    break;
                case "createdBy":
                    request.get().addProperty("createdBy", dataTable.get(data));
                    break;
                case "taskTriggerDate":
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar date = Calendar.getInstance();
                    date.add(Calendar.DATE, +30);
                    taskTriggerDate.set(dateFormat.format(date.getTime())); //30 days from today
                    request.get().addProperty("taskTriggerDate", taskTriggerDate.get());
                    break;
                case "correlationId":
                    request.get().addProperty("correlationId", dataTable.get(data));
                    break;
            }
        }
        System.out.println(request.get());
    }

    public void sendExternalTaskRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskManagementURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("mars/taskmanagement/externaltask");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request.get());
        System.out.println(request.get() + " REQUEST PRINT");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        CRM_TaskManagementStepDef.srID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("taskId"));
        World.generalSave.get().put("TASKID", CRM_TaskManagementStepDef.srID.get());
        System.out.println("taskid : " + CRM_TaskManagementStepDef.srID.get());
    }

    public List<String> getListOfIBDefinitionNamesByProjectId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/inboundCorrespondenceDefinition/correspondenceType/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("content.name");
    }

    public Map<String, JsonArray> getIBTaskRulesForGivenDefinition(List<String> definitionsList) {
        String taskId;

        for (String definitionName : definitionsList) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition"));
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/inboundCorrespondenceDefinition");
            synchronized (request) {
                request.set(new JsonObject());
            }
            request.get().addProperty("name", definitionName);
            JsonObject response = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request.get()).apiJsonObject;
            //add task name
            int taskCount = response.getAsJsonObject("inboundCorrespondence").getAsJsonArray("inboundCorrespondenceTaskRule").size();
            for (int i = 0; i < taskCount; i++) {
                taskId = response.getAsJsonObject("inboundCorrespondence").getAsJsonArray("inboundCorrespondenceTaskRule").get(i).getAsJsonObject().get("taskTypeId").getAsString();
                response.getAsJsonObject("inboundCorrespondence").getAsJsonArray("inboundCorrespondenceTaskRule").get(i).getAsJsonObject().addProperty("taskTypeName", getTaskTypeNameByTaskId(taskId));
            }
            inboundTaskRulesMap.get().put(definitionName, response.getAsJsonObject().getAsJsonObject("inboundCorrespondence").getAsJsonArray("inboundCorrespondenceTaskRule"));
        }
        return inboundTaskRulesMap.get();
    }

    public String getTaskTypeNameByTaskId(String taskId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskTemplateURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/tm/tasktype/" + taskId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("taskTypeVO.taskTypeName");
    }

    public void verifyGetIBDefinitionByProjectIdResponse() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/inboundCorrespondenceDefinition/correspondenceType/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        String definitionName;
        JsonObject response = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
        JsonArray actualJson = response.getAsJsonArray("content");
        int definitionCount = actualJson.getAsJsonArray().size();
        for (int i = 0; i < definitionCount; i++) {
            definitionName = actualJson.getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString();
            Assert.assertEquals(apiAutoUtilities.get().sortJsonArray(actualJson.getAsJsonArray().get(i).getAsJsonObject().getAsJsonArray("inboundCorrespondenceTaskRule")), apiAutoUtilities.get().sortJsonArray(inboundTaskRulesMap.get().get(definitionName)));
        }
    }

    public void retrieveTaskRequest(String exptaskstatus) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskManagementURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/taskmanagement/tasks/" + APITaskManagementController.taskId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        String acttaskduedate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.defaultDueDate");
        String acttaskStatus = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskStatus");

        System.out.println("due date : " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.defaultDueDate"));
        System.out.println("status : " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskStatus"));

        if (exptaskstatus.equalsIgnoreCase("")) {
            World.save.get().put("taskduedate", acttaskduedate);
        } else if (exptaskstatus.equalsIgnoreCase("cancelled") || exptaskstatus.equalsIgnoreCase("complete")) {
            Assert.assertTrue(World.save.get().get("taskduedate").equalsIgnoreCase(acttaskduedate));
            Assert.assertTrue(acttaskStatus.equalsIgnoreCase(exptaskstatus));
        } else if (exptaskstatus.equalsIgnoreCase("created") || exptaskstatus.equalsIgnoreCase("onhold") || exptaskstatus.equalsIgnoreCase("escalated")) {
            Assert.assertFalse(World.save.get().get("taskduedate").equalsIgnoreCase(acttaskduedate));
            Assert.assertTrue(acttaskStatus.equalsIgnoreCase(exptaskstatus));
        }

    }

    public void navigatetotaskdetailspage(String taskID) {

        waitForVisibility(taskSearchPage.taskId, 10);
        taskSearchPage.taskId.sendKeys(taskID);
        taskSearchPage.searchBtn.click();
        waitFor(3);
        Driver.getDriver().findElement(By.xpath("//b[contains(text(),'" + taskID + "')]")).click();
        waitFor(5);
    }

    public void createRequestToLinkApplicationToCases(String createdApplication, int casesCount) {
        Map<String, String> caseConsumerId = new HashMap<>();
        synchronized (listOfRequest){
            listOfRequest.set(new ArrayList<>());
        }
        for (int i = 0; i < casesCount; i++) {
            caseConsumerId = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId();
            listOfCases.get().add(caseConsumerId.get("caseId"));
            request.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/LinkApplicationToCase.json").jsonElement);
            request.get().addProperty("caseId", caseConsumerId.get("caseId"));
            if (createdApplication.equalsIgnoreCase("previouslyCreated")) {
                request.get().getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().addProperty("applicationConsumerId", save.get().get("appConsumerID"));
            }
            listOfRequest.get().add(request.get());
        }
    }

    public void sendRequestToLinkApplicationToCases() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiApplicationDataServices"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/application/" + save.get().get("appID") + "/link/case");
        for (JsonObject request : listOfRequest.get()) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        }
    }

    public void verifyLinkFromTaskToCases() {
        JsonPath response = apiAutoUtilities.get().getTaskByTaskId(World.save.get().get("createdTask"));
        String traceID = "";
        int linksCount = response.getInt("tasks[0].taskLinksVOS.size()");
        int actualCount = 0;
        for (int i = 0; i < linksCount; i++) {
            for (String caseId : listOfCases.get()) {
                if (response.getString("tasks[0].taskLinksVOS[" + i + "].externalLinkRefType").equalsIgnoreCase("Case") &&
                        response.getString("tasks[0].taskLinksVOS[" + i + "].externalLinkRefId").equalsIgnoreCase(caseId)) {
                    traceID = response.getString("tasks[0].taskLinksVOS[" + i + "].correlationId");
                    actualCount++;
                    break;
                }
            }
        }
        save.get().put("taskCaseTraceID", traceID);
        Assert.assertEquals(actualCount, listOfCases.get().size(), "Link from created Task to Case/s not found");
    }

    public void verifyMissingInfoItemLinked(String missingInformationItemId) {
        String cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        JsonPath inbLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> links = inbLinks.getList("externalLinkDetails.content");
        boolean found = false;
        for (Map<String, Object> link : links) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("MISSING_INFO")) {
                found = true;
                Assert.assertEquals(missingInformationItemId, String.valueOf(link.get("id")));
                Assert.assertEquals("Missing Information", link.get("name"));
                Assert.assertEquals("INBOUND_CORRESPONDENCE", link.get("internalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
            }
        }
        Assert.assertTrue(found);
        JsonPath mitemLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getMissingItemLinks(missingInformationItemId);
        List<Map<String, Object>> miLink = mitemLinks.getList("externalLinkDetails.content");
        found = false;
        for (Map<String, Object> link : miLink) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("INBOUND_CORRESPONDENCE")) {
                found = true;
                Assert.assertEquals(cid, String.valueOf(link.get("id")));
                Assert.assertEquals("MISSING_INFO", link.get("internalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
            }
        }
        Assert.assertTrue(found);
    }


    public void verifyServiceRequestLinked(String serviceRequest) {
        String cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        JsonPath inbLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        String srId = "";
        boolean found = false;
        List<Map<String, Object>> links = inbLinks.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("SERVICE_REQUEST")) {
                found = true;
                Assert.assertEquals(serviceRequest, String.valueOf(link.get("id")));
                srId = String.valueOf(link.get("id"));
                Assert.assertEquals("Service Request", link.get("name"));
                Assert.assertEquals("INBOUND_CORRESPONDENCE", link.get("internalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
            }
        }
        Assert.assertTrue(found);
        Assert.assertTrue(srId.length() > 1);
        latest.set(Integer.valueOf(srId));
        verifyLinkInTask("INBOUND_CORRESPONDENCE", cid);
    }

    public void verifyServiceRequestLinked(String type, String inbId) {
        String cid = String.valueOf(World.generalSave.get().get(inbId));
        JsonPath inbLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        String srId = "";
        boolean found = false;
        List<Map<String, Object>> links = inbLinks.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("SERVICE_REQUEST")) {
                found = true;
                srId = String.valueOf(link.get("id"));
                Assert.assertEquals(type, link.get("name"));
                Assert.assertEquals("INBOUND_CORRESPONDENCE", link.get("internalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
            }
        }
        Assert.assertTrue(found);
        Assert.assertTrue(srId.length() > 1);
        latest.set(Integer.valueOf(srId));
        verifyLinkInTask("INBOUND_CORRESPONDENCE", cid);
    }

    public void verifySameServiceRequestLinked(String serviceRequest) {
        String cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        JsonPath inbLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        String srId = "";
        boolean found = false;
        int count = 0;
        List<Map<String, Object>> links = inbLinks.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("SERVICE_REQUEST")) {
                found = true;
                count++;
                Assert.assertEquals(serviceRequest, String.valueOf(link.get("id")));
                srId = String.valueOf(link.get("id"));
                Assert.assertEquals("Service Request", link.get("name"));
                Assert.assertEquals("INBOUND_CORRESPONDENCE", link.get("internalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(1, count);
        Assert.assertTrue(srId.length() > 1);
        latest.set(Integer.valueOf(srId));
        verifyLinkInTask("INBOUND_CORRESPONDENCE", cid);
    }

    public void verifySameTasktLinked(String task) {
        String cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        JsonPath inbLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        String srId = "";
        boolean found = false;
        int count = 0;
        List<Map<String, Object>> links = inbLinks.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("TASK")) {
                found = true;
                count++;
                Assert.assertEquals(task, String.valueOf(link.get("id")));
                srId = String.valueOf(link.get("id"));
                Assert.assertEquals("Task", link.get("name"));
                Assert.assertEquals("INBOUND_CORRESPONDENCE", link.get("internalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(1, count);
        Assert.assertTrue(srId.length() > 1);
        latest.set(Integer.valueOf(srId));
        verifyLinkInTask("INBOUND_CORRESPONDENCE", cid);
    }

    public void verifySameCaseFronIBLinkedToSR(String caseid) {
        String cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        JsonPath inbLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        String actualCaseId = "";
        String srId = "";
        boolean found = false;
        List<Map<String, Object>> links = inbLinks.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("CASE")) {
                found = true;
                Assert.assertEquals(caseid, String.valueOf(link.get("id")));
                actualCaseId = String.valueOf(link.get("id"));
            } else if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("SERVICE_REQUEST")) {
                found = true;
                Assert.assertEquals(caseid, String.valueOf(link.get("id")));
                srId = String.valueOf(link.get("id"));
            }
        }
        Assert.assertTrue(found);
        Assert.assertTrue(actualCaseId.length() > 1);
        verifyCaseLinkedToServiceRequest(actualCaseId, srId);
        verifyLinkInTask("CASE", cid);
    }

    public void verifySameCaseFronIBLinkedToSR(String caseid, String type) {
        String cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        JsonPath inbLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        String actualCaseId = "";
        String srId = "";
        boolean found = false;
        List<Map<String, Object>> links = inbLinks.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("CASE")) {
                found = true;
                Assert.assertEquals(caseid, String.valueOf(link.get("id")));
                actualCaseId = String.valueOf(link.get("id"));
            } else if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("SERVICE_REQUEST")) {
                found = true;
                srId = String.valueOf(link.get("id"));
            }
        }
        Assert.assertTrue(found);
        Assert.assertTrue(actualCaseId.length() > 1);
        verifyCaseLinkedToServiceRequest(actualCaseId, srId);
        setLatestTaskId(Integer.parseInt(srId));
        verifyLinkInTask("CASE", actualCaseId);
    }

    public void verifyCaseLinkedToServiceRequest(String caseId, String srId) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCaseLinks(caseId);
        boolean found = false;
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (String.valueOf(link.get("externalRefType")).equalsIgnoreCase("SERVICE_REQUEST")) {
                found = true;
                Assert.assertEquals(srId, String.valueOf(link.get("id")));
                Assert.assertEquals("Service Request", link.get("name"));
                Assert.assertEquals("CASE", link.get("internalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
            }
        }
        Assert.assertTrue(found);
    }

    public void i_will_get_ID_from_the_ATS_response(String component) {
        waitFor(3);
        List<Map<String, Object>> linkData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links");
        for (int i = 0; i < linkData.size(); i++) {
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.name").get(i).toString().equalsIgnoreCase(component)) {
                switch (component) {
                    case "Case":
                        String caseid = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.id").get(i).toString();
                        World.generalSave.get().put("ApplicationCaseId", caseid);
                        break;
                    default:
                        Assert.fail("Task Type Didn't match");
                }
            }
        }
    }

    public void verifyDuplicateTaskTypePayload(Map<String, String> dataTable) {
        request.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/task/verifyDuplicateTaskType.json").jsonElement);
        for (String data : dataTable.keySet()) {
            JsonObject tempObject = new JsonObject();
            switch (data) {
                case "CASE":
                case "CONSUMER":
                case "APPLICATION":
                    tempObject.addProperty("externalRefId", dataTable.get(data));
                    tempObject.addProperty("externalRefType", data);
                    request.get().getAsJsonArray("links").add(tempObject);
                    break;
                case "taskTypeId":
                    request.get().addProperty("taskTypeId", dataTable.get(data));
                    break;
            }
        }
        System.out.println(request.get());
    }

    public void sendDuplicateTaskRequest() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(duplicateTaskBaseUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(duplicateTaskEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request.get());
        System.out.println(request.get());
    }

    public void verifyDuplicateTaskResponse(String responseType, String typeDuplicate) {
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        sa.get().assertThat(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("status")).hasToString("success");
        sa.get().assertThat(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("errorMessage")).isNull();
        JsonObject duplicateTaskInfo = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("duplicateTaskInfo");
        String isDuplicate = responseType.equalsIgnoreCase("restricted") ? "true" : "false";
        sa.get().assertThat(duplicateTaskInfo.get("duplicate").toString()).hasToString(isDuplicate);
        if (isDuplicate.equalsIgnoreCase("true")) {
            sa.get().assertThat(duplicateTaskInfo.get("taskId")).isNotNull();
            switch (typeDuplicate) {
                case "CASE":
                case "CONSUMER":
                case "APPLICATION":
                    sa.get().assertThat(duplicateTaskInfo.get("duplicateTaskMessage").toString())
                            .containsIgnoringCase("A task of the same type with ID")
                            .containsIgnoringCase("already exists")
                            .containsIgnoringCase("linked to " + typeDuplicate + "")
                            .containsIgnoringCase("you may not create a duplicate.");
                    break;
                case "CASE and CONSUMER":
                    sa.get().assertThat(duplicateTaskInfo.get("duplicateTaskMessage").toString())
                            .containsIgnoringCase("A task of the same type with ID")
                            .containsIgnoringCase("already exists")
                            .containsIgnoringCase("linked to CONSUMER 164196, CASE 92095, you may not create a duplicate.");
                    break;
                default:
                    fail();
            }

        } else {
            sa.get().assertThat(duplicateTaskInfo.get("taskId").toString()).isEqualToIgnoringCase("null");
            sa.get().assertThat(duplicateTaskInfo.get("links").toString()).isEqualToIgnoringCase("null");
            sa.get().assertThat(duplicateTaskInfo.get("duplicateTaskMessage").getAsString()).isEmpty();
        }
    }

    public void verifyDuplicateTaskResponseForExternalTaskCreation(String type_link, String restricted_or_allowed) {
        String isDuplicate = restricted_or_allowed.equalsIgnoreCase("restricted") ? "true" : "false";
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        if (isDuplicate.equalsIgnoreCase("true")) {
            sa.get().assertThat(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("status")).hasToString("duplicate");
            JsonObject duplicateTaskInfo = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("duplicateTaskInfo");
            sa.get().assertThat(duplicateTaskInfo.get("duplicate").toString()).hasToString("true");
            sa.get().assertThat(duplicateTaskInfo.get("taskId")).isNotNull();
            switch (type_link) {
                case "CASE":
                case "CONSUMER":
                case "APPLICATION":
                    sa.get().assertThat(duplicateTaskInfo.get("duplicateTaskMessage").toString())
                            .containsIgnoringCase("A task of the same type with ID")
                            .containsIgnoringCase("already exists")
                            .containsIgnoringCase("linked to " + type_link + "")
                            .containsIgnoringCase("you may not create a duplicate.");
                    break;
                case "CASE and CONSUMER":
                    sa.get().assertThat(duplicateTaskInfo.get("duplicateTaskMessage").toString())
                            .containsIgnoringCase("A task of the same type with ID")
                            .containsIgnoringCase("already exists")
                            .containsIgnoringCase("linked to CONSUMER 164196, CASE 92095, you may not create a duplicate.");
                    break;
                default:
                    fail();
            }

        } else {
            sa.get().assertThat(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("status")).hasToString("success");
            sa.get().assertThat(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("errorResponse")).isNull();
            sa.get().assertThat(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("taskId")).isNotNull();
            sa.get().assertThat(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("duplicateTaskInfo")).isNull();
        }
    }

    public JsonObject updateTaskStatus(String taskStatus) {
        JsonObject request = new JsonObject();
        String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        JsonPath jsonPath = apiAutoUtilities.get().getTaskByTaskId(String.valueOf(generalSave.get().get("TASKID")));
        HashMap<String, Object> taskVO1 = new HashMap<>(jsonPath.getJsonObject("tasks[0].taskVO"));
        taskVO1.put("taskStatus", taskStatus);
        if (taskStatus.equalsIgnoreCase("Escalated")) {
            taskVO1.put("escalatedFlag", true);
        }
        taskVO1.put("statusDate", curentDateTime);
        taskVO1.put("updatedOn", curentDateTime);
        System.out.println("Printing request String" + request);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskManagementURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("mars/taskmanagement/task");
        JSONObject json = new JSONObject(taskVO1);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(json.toString());
        System.out.println("Printing Response String" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        return request;
    }

    public void verifyTaskUpdated(String taskStatus) {
        System.out.println("Updated Task ID" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId").toString() != null);
        String updatedTaskId = String.valueOf(generalSave.get().get("TASKID"));
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId").toString(), updatedTaskId);
        generalSave.get().put("TASKSTATUS", taskStatus);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskStatus").toString().equals(taskStatus));
    }
}