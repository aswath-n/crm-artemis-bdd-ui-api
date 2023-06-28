package com.maersk.crm.api_step_definitions;

import com.google.gson.*;
import com.maersk.crm.step_definitions.CRM_GeneralTaskStepDef;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.step_definitions.LoginStepDef;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.testng.Assert.*;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.taskIDFromLinks;
import static com.maersk.crm.utilities.Api_Body.*;
import static com.maersk.crm.utilities.data_Module.crm_core_data_module.TaskFields.convertFieldNamesToVariableAndMapWithValues;
import static com.maersk.crm.utilities.data_Module.crm_core_data_module.TaskTypes.convertTaskTypeNameToVariable;
import static com.maersk.crm.api_step_definitions.APIEligibilityAutoAssignmentContoller.srIdFromLink;

public class APITaskManagementController extends CRMUtilities implements ApiBase {
    private String taskManagementBaseURI = ConfigurationReader.getProperty("apiTaskManagementURI");
    private String taskTemplateBaseURI = ConfigurationReader.getProperty("apiTaskTemplateURI");
    private String baseTMURI = ConfigurationReader.getProperty("apiTMURI");
    private String activeBusinessUnitsAndTeamsEndPoint = "/mars/tm/business/activebusinessunitsandteams";
    private String activeUsers = "/mars/tm/project/activeusers";
    private String corresDefService = ConfigurationReader.getProperty("apiCorresDefMicroservice");
    private String apiCorresLink = ConfigurationReader.getProperty("apiCorresLink");
    private String getTaskTypeByProjectEndPoint = "mars/tm/tasktype/project/{projectId}";
    private String saveTaskEndPoint = "mars/taskmanagement/task";
    private String getTaskTypeByName = "mars/tm/tasktype/bytaskname";
    private String getTaskByTaskId = "mars/taskmanagement/tasks/{taskId}";
    private String getTaskListByUser = "mars/taskmanagement/tasks";
    private String initiateTask = "mars/taskmanagement/task";
    private String saveTaskSearch = "mars/taskmanagement/tasksearch";
    private String getInquiries = "mars/taskmanagement/inquiries/";
    private String putInquiry = "/mars/taskmanagement/inquiry";
    private String externalTask = "/mars/taskmanagement/externaltask";
    private String incompleteTaskEndPoint = "/mars/taskmanagement/incomplete";
    private String workQueueTasks = "/mars/taskmanagement/workqueue/tasks";
    private String taskTypeIdsByPermissionId = "/mars/tm/tasktype/permissiongroup/";
    private String getTemplateInfo = "/mars/tm/tasktemplate/{templateId}";
    private String getTaskHisByTaskId = "mars/taskmanagement/tasks/{taskId}/history";
    private String getIVRTaskByTaskId = "mars/taskmanagement/externalrequest?identifier={taskId}";
    APIATSSendEventAndCreateLinksController sendEventAndCreateLinksController = new APIATSSendEventAndCreateLinksController();
    APIATSApplicationController applicationController = new APIATSApplicationController();

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> apiconsumerFirstName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> taskTypeId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> uiid = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> dueInDays = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> taskPriority = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> taskTypeName = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> apiTaskId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiSrId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> projectId = ThreadLocal.withInitial(() -> API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
    private final ThreadLocal<String> SaveName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> taskSearchId = ThreadLocal.withInitial(() -> 0);
    public static final ThreadLocal<String> msgSubject = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> msgBody = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> traceId = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> traceId1 = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> taskInfo = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> assignee = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<Integer> taskId = ThreadLocal.withInitial(() -> 0);
    final ThreadLocal<ArrayList<Integer>> inquiryIdsList = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<ArrayList<String>> allFieldsValue = ThreadLocal.withInitial(ArrayList::new);
    private String corresListCrm = "/activecorrespondences/6203?allActive=true";
    private String outBoundcorresLink = "?size=5&page=0";
    public static final ThreadLocal<HashMap<String, String>> taskVO1 = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<HashMap<Integer, String>> fieldOrder = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<ArrayList<Integer>> businessUnitIDs = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<ArrayList<String>> businessUnitNames = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<Integer>> businessUnitIDs1 = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<Integer>> teamIDs = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> activeUserValues = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<Integer>> taskTypeIds = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<JsonArray> taskHisCall = ThreadLocal.withInitial(JsonArray::new);
    final ThreadLocal<JsonArray> taskInfoCall = ThreadLocal.withInitial(JsonArray::new);
    private String updateDueDate = "mars/servicerequest/updateduedate";
    public final ThreadLocal<JSONObject> payloadObject = ThreadLocal.withInitial(JSONObject::new);
    final ThreadLocal<Map<String, String>> businessUnitData = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<ArrayList<Object>> businessUnit = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<Object>> taskTypeTemp = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<Object>> businessUnitList = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<Integer>> taskTypeIdList = ThreadLocal.withInitial(ArrayList::new);

    //Modified to generic project ID--Aswath
    @Given("I initiated get task types API for project {string}")
    public void initiateGetTaskTypeApi(String newProjectId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskTemplateBaseURI);
        if (newProjectId.isEmpty()) {
            getTaskTypeByProjectEndPoint = getTaskTypeByProjectEndPoint.replace("{projectId}", projectId.get());
        } else if (newProjectId.equals("NJ-SBE")) {
            projectId.set(ConfigurationReader.getProperty("njProjectName").split(" ")[0]);
            getTaskTypeByProjectEndPoint = getTaskTypeByProjectEndPoint.replace("{projectId}", projectId.get());
        } else if (newProjectId.equals("IN-EB")) {
            projectId.set(ConfigurationReader.getProperty("INEBProjectName").split(" ")[0]);
            getTaskTypeByProjectEndPoint = getTaskTypeByProjectEndPoint.replace("{projectId}", projectId.get());
        } else if (newProjectId.equals("CoverVA")) {
            projectId.set(ConfigurationReader.getProperty("coverVAProjectName").split(" ")[0]);
            getTaskTypeByProjectEndPoint = getTaskTypeByProjectEndPoint.replace("{projectId}", projectId.get());
        } else if (newProjectId.equals("DC-EB")) {
            projectId.set(ConfigurationReader.getProperty("DCEBProjectName").split(" ")[0]);
            getTaskTypeByProjectEndPoint = getTaskTypeByProjectEndPoint.replace("{projectId}", projectId.get());
        } else {
            getTaskTypeByProjectEndPoint = getTaskTypeByProjectEndPoint.replace("{projectId}", newProjectId);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskTypeByProjectEndPoint);
    }

    @When("I run get task type API")
    public void runGetTaskTypeApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @When("I get task type id for task type name {string}")
    public void getTaskTypeId(String taskTypeName) {
        String variableName = convertTaskTypeNameToVariable(taskTypeName);
        ArrayList taskTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("taskTypeList");
        for (int i = 0; i < taskTypes.size(); i++) {
            Map<String, Object> taskType = (Map) taskTypes.get(i);
            if (taskType.get("taskTypeName").toString().equalsIgnoreCase(variableName)) {
                taskTypeId.set(taskType.get("taskTypeId").toString());
                dueInDays.set(taskType.get("dueInDays").toString());
                taskPriority.set(taskType.get("priority").toString());
                break;
            }
        }
    }

    @Given("I initiated create task API")
    public void initiateCreateTask() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(saveTaskEndPoint);
    }

    public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                addedDays++;
            }
        }
        return result;
    }

    @When("I provide task information to create task")
    public void provideTaskInformation() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/createTask.json");
        correlationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
        uiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);
        String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", taskTypeId.get());
        System.out.println("TaskType from Payload  " + taskTypeId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", uiid.get());
        if (taskPriority.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultPriority", taskPriority.get());

        if (dueInDays.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dueInDays", dueInDays.get());

        int i = Integer.parseInt(dueInDays.get());
        String tempdate = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        String X = tempdate.substring(0, 10);

        LocalDate localDate = addDaysSkippingWeekends(LocalDate.parse(X), i);
        String dueDate = localDate + "T23:59:05.755Z";
        System.out.println("Printing1 " + localDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultDueDate", dueDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", uiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("staffAssignedTo", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        if (taskPriority.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultPriority", taskPriority.get());

        if (dueInDays.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dueInDays", dueInDays.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("statusDate", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());

    }

    @When("I run create task API")
    public void runCreateTask() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify task created successfully")
    public void verifyTaskCreted() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId").toString() != null);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskTypeId").toString(), taskTypeId.get());
        apiTaskId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId").toString());
        System.out.println(apiTaskId.get());
        taskId.set(Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId").toString()));
        System.out.println("Newly Task ID" + taskId.get());
        traceId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
    }

    @Given("I initiated get task type by name")
    public void initiateGetTaskTypeApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskTemplateBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskTypeByName);
    }

    @When("I provide project id as {string} and task type name as {string} to get task type")
    public void provideTaskNameProjectName(String projectId, String taskTypeName) {
        JsonObject payLoad = new JsonObject();

        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        payLoad.addProperty("projectId", projectId);
        payLoad.addProperty("taskTypeName", taskTypeName);
        requestParams.set(payLoad);
    }

    @When("I run get task type by name API")
    public void runGetTaskTypeByName() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList taskTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("taskTypeList");
        Map<String, Object> taskType = (Map) taskTypes.get(0);
        taskTypeId.set(taskType.get("taskTypeId").toString());
        taskPriority.set(taskType.get("priority").toString());
        taskTypeName.set(taskType.get("taskTypeName").toString());
        dueInDays.set(taskType.get("dueInDays").toString());
    }

    //modifiedBy: Vidya Date: 01/14/2020 Reason: Changed to take task type id for event validation of BU
    @Then("I verify Task type records are retrieved")
    public void i_verify_Task_type_records_are_retrieved() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        ArrayList taskTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("taskTypeList");
        assertTrue(taskTypes.size() > 0);
        JsonArray arr = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getAsJsonObject().get("taskTypeName").getAsString().equalsIgnoreCase("General")) {
                taskTypeId.set(arr.get(i).getAsJsonObject().get("taskTypeId").getAsString());
                taskPriority.set(arr.get(i).getAsJsonObject().get("priority").getAsString());
                dueInDays.set(arr.get(i).getAsJsonObject().get("dueInDays").getAsString());
                break;
            }
        }
    }

    @Given("I initiated get task by task id {string}")
    public void initiateGetTaskByTaskId(String taskId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        if (taskId.equals("getFromUi")) {
            apiTaskId.set(CRM_TaskManagementStepDef.taskId.get());
        } else if (taskId.equals("getFromApplicationLinks")) {
            apiTaskId.set(taskIDFromLinks.get() + 1);
        } else if (taskId.equals("getFromApplicationLinksATS")) {
            apiTaskId.set(taskIDFromLinks.get());
        } else if (taskId.equals("getSRIDFromUi")) {
            apiTaskId.set(CRM_TaskManagementStepDef.srID.get());
        } else if (taskId.equals("getSRIdFromAPI")) {
            apiTaskId.set(srIdFromLink.get());
            System.out.println("id-> " + apiTaskId.get());
        } else if (!taskId.isEmpty()) {
            apiTaskId.set(taskId);
        } else {
            apiTaskId.set(this.taskId.get() + "");
        }
        String getTaskByTaskId1 = getTaskByTaskId.replace("{taskId}", apiTaskId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskByTaskId1);
    }

    @When("I run get task by task id API")
    public void getTaskByTaskId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success") || API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("no results returned"));
    }

    @Given("I initiated get task list by staff assigned to")
    public void getTaskLisByUser() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskListByUser);
    }

    @When("I provide staff assigned to as {string}")
    public void provideTaskNameProjectName(String staffAssignedTo) {
        JSONObject user = new JSONObject();
        if (staffAssignedTo == null) {
            user.put("staffAssignedTo", "");
        } else if (staffAssignedTo.contains("consumerId")) {
            user.put("staffAssignedTo", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(staffAssignedTo)).replace(".0", ""));
        } else {
            user.put("staffAssignedTo", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        }
        JSONArray arr = new JSONArray();
        arr.put("Complete");
        arr.put("Cancelled");
        user.put("excludeStatusList", arr);
        JsonObject payLoad = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        payLoad.add("payload", (JsonObject) jsonParser.parse(user.toString()));
        requestParams.set(payLoad);
        System.out.println(requestParams.get().toString());

    }

    @When("I run get task list by staff assigned to")
    public void runGetTaskList() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "status/HTTP code is not equal to 200");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success", "Response Status is not success");
    }

    @Then("I verify task record details retrieved by get api")
    public void verifyTaskDetails() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            temp = temp.getJSONObject("taskVO");
            assertFalse(temp.isNull("taskStatus") || temp.getString("taskStatus").isEmpty(),
                    "Task Status is empty or null");
            assertTrue(!temp.isNull("taskId") || temp.getInt("taskId") != 0,
                    "Task Id is empty or null");
            assertTrue(!temp.isNull("taskTypeId") || temp.getInt("taskTypeId") != 0,
                    "taskTypeId is empty or null");
            assertTrue(!temp.isNull("defaultPriority") || temp.getInt("defaultPriority") != 0,
                    "defaultPriority is empty or null");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")),
                    "defaultDueDate is miss match");
            assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                    "statusDate date time field IS NOT valid");
            assertEquals(temp.getString("staffAssignedTo"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
            assertTrue(temp.isNull("dueIn"), "dueIn is not null");
            assertTrue(!temp.isNull("dueInDays") || temp.getInt("dueInDays") != 0,
                    "Due In Days has wrong data");
            assertTrue(temp.getString("source").equalsIgnoreCase("User") ||
                            temp.getString("source").equalsIgnoreCase("System"),
                    "Source has wrong data");
            assertTrue(temp.isNull("taskInfo") || !temp.getString("taskInfo").isEmpty(),
                    "Task Info is wrong");
            assertTrue(temp.isNull("editReason") || !temp.getString("editReason").isEmpty(),
                    "Edit reason has wrong data");
            assertTrue(temp.isNull("holdReason") || !temp.getString("holdReason").isEmpty(),
                    "Hold Reason has wrong data");
            assertTrue(temp.isNull("cancelReason") || !temp.getString("cancelReason").isEmpty(),
                    "Cancel Reason is wrong");
            assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
            assertTrue(temp.isNull("createdBy") || !temp.getString("createdBy").isEmpty(),
                    "Created By has wrong data");
            assertTrue(temp.isNull("updatedBy") || !temp.getString("updatedBy").isEmpty(),
                    "Updated By has wrong data");
            assertTrue(temp.isNull("createdOn") || !temp.getString("createdOn").isEmpty(),
                    "Created On has wrong data");
            assertTrue(temp.isNull("updatedOn") || !temp.getString("updatedOn").isEmpty(),
                    "Updated On has wrong data");
        }

    }

    /*RInitate the Task Author: Aswath  Date of scripted: 08/19/2019 */
    @Given("I initiate task")
    public void initiatePutTaskApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(initiateTask);
    }

    /*Provide task detials to Task API, Author: Aswath  Date of scripted: 08/19/2019
     * RefactorBy:Vidya Date:07-04-2020*/
    @Then("I provide task information to initiate {string}")
    public void provideTaskInformationToInitiate(String taskStatus) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/initaiteTask.json");
        String identificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber;
        correlationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
        uiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", taskTypeId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskId", apiTaskId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskStatus", taskStatus);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskNotes", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(300).randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", uiid.get());

        if (taskPriority.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultPriority", taskPriority.get());

        if (dueInDays.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dueInDays", dueInDays.get());

        //If Task status is cancelled then Reason for cancel and edit reason fields get updates--Added on the 09/24/2019
        if (taskStatus.equals("Cancelled")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("editReason", "CORRECTED_DATA_ENTRY");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("cancelReason", "CREATED_INCORRECTLY");
        }

        //If Task status is hold then Reason for hold  field get updates--Added on the 09/24/2019
        if (taskStatus.equals("OnHold")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("holdReason", "APPLN_ON_HOLD");
        }
        String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("statusDate", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", curentDateTime);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

    }

    /*Run the initaite Task API Author: Aswath  Date of scripted: 08/19/2019*/
    @When("I run initiate task API")
    public void runInitaiteTask() {
        System.out.println("Printing request String" + requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("Printing Response String" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    /*Verification task status and successful update details, Author: Aswath  Date of scripted: 08/19/2019@CP-19363-01*/
    @Then("I verify task initaited successfully {string}")
    public void verifyTaskInitaited(String taskStatus) {
        System.out.println("Updated Task ID" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId").toString() != null);
        String updatedTaskId = String.valueOf(taskId.get());
        assertEquals((API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskId")).toString(), updatedTaskId);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskVO.taskStatus").toString().equals(taskStatus));
    }

    /*Below methods are related to Save Task Search Post API Verification, Author:-Vidya  Date of scripted: 09/25/2019*/
    @Given("I initiated Save Task Search API")
    public void initiateSaveTaskSearch() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(saveTaskSearch);
    }

    @When("I can provide search criteria and task save name")
    public void iCanprovideSaveTaskSearchDetails() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/SaveTaskSearch.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
        SaveName.set("Srch" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskSearchName", SaveName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", System.currentTimeMillis());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("userId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("taskSearchParam").addProperty("taskId", apiTaskId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("taskSearchParam").getAsJsonArray("taskStatus").add("Complete");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @And("I run the Save Task Search API")
    public void iRunTheSaveTaskSearchAPI() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I can get API response status as {string}")
    public void statusCheck(String status) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    /*Below methods are related to Saved Task Search GET API Verification, Author:-Vidya  Date of scripted: 10/03/2019*/
    @Given("I create one save task search")
    public void createOneSaveTaskSearch() throws Exception {
        initiateSaveTaskSearch();
        iCanprovideSaveTaskSearchDetails();
        iRunTheSaveTaskSearchAPI();
    }

    @When("I initiated Saved Task Search Get API")
    public void initiateSavedTaskSearchGetApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        String savedTaskSearch = "mars/taskmanagement/tasksearch/user/";
        savedTaskSearch = savedTaskSearch + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(savedTaskSearch);
    }

    @And("I run the Saved Task Search Get API")
    public void iRunTheSavedTaskSearchGetAPI() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @And("I verify that response has save task search which I newly created")
    public void verifyResponseHasNewlyCreatedSaveTaskSearch() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskSearchList");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getString("taskSearchName").equals(SaveName.get())) {
                assertEquals(temp.getString("taskSearchName"), SaveName.get(),
                        "Newly created save task search is not present");
                taskSearchId.set(temp.getInt("taskSearchId"));
                break;
            } else if (i == json.size() - 1) {
                assertFalse(true, "Newly created save task search is not present ");
            }
        }
    }

    @And("I get the task search id from newly created record")
    public void getTheTaskSearchIdFromNewlyCreatedRecord() throws Exception {
        initiateSavedTaskSearchGetApi();
        iRunTheSavedTaskSearchGetAPI();
        statusCheck("success");
        verifyResponseHasNewlyCreatedSaveTaskSearch();
    }

    @And("I initiated Delete Saved Task Search API")
    public void initiateDeleteSavedTaskSearchApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(saveTaskSearch);
    }

    @When("I can provide Delete information")
    public void provideDeleteInformation() {
        requestParams.set(new JsonObject());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = df.format(new Date());
        requestParams.get().addProperty("endDate", formatted);
        requestParams.get().addProperty("taskSearchId", taskSearchId.get());
    }

    @And("I run the Delete Saved Task Search API")
    public void runDeleteSavedTaskSearchApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @And("I verify deleted task search id is not there in Saved Task Search GET API response")
    public void verifyDeletedTaskSearchIdIsNotThereInSavedTaskSearchGETAPI() throws Exception {
        initiateSavedTaskSearchGetApi();
        iRunTheSavedTaskSearchGetAPI();
        statusCheck("success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskSearchList");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            assertFalse(temp.getInt("taskSearchId") == taskSearchId.get());
        }
    }

    //Aswath-- Added for negative test data required for the validating the coloring in the task management
    @And("I create task with negative due in")
    public void provideTaskInformationForNegativedueDate() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/createTask.json");
        correlationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
        uiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().addProperty("taskTypeId", taskTypeId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().addProperty("createdOn", getPriorDate(365));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().addProperty("uiid", uiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().addProperty("staffAssignedTo", APITMProjectRestController.userIdFromApigee);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().addProperty("createdBy", APITMProjectRestController.userIdFromApigee);

        if (taskPriority.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().addProperty("defaultPriority", taskPriority.get());

        if (dueInDays.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().addProperty("dueInDays", dueInDays.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());

    }

    //Aswath-- Added for negative test data required for the validating the coloring in the task management
    public void createTaskInformationForNegativedueDate() {
        initiateCreateTask();
        provideTaskInformationForNegativedueDate();
        runCreateTask();
        verifyTaskCreted();
    }

    //Vidya-- Method to initiate Get the list of Inquiries
    @And("I initiated Inquiries Get API for {string} and {string} from {string} and size {string}")
    public void initiateInquiriesGetApi(String caseId, String channel, String from, String size) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        getInquiries = getInquiries + caseId + "/" + channel + "?from=" + from + "&size=" + size;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getInquiries);
    }

    //Vidya-- Method to initiate Get the list of Inquiries
    @And("I initiated Inquiries Get API end point as from {string} and size {string}")
    public void initiateInquiriesGetApi(String from, String size) {
        getInquiries = getInquiries.replaceAll("from=&size=", "") + "from=" + from + "&size=" + size;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getInquiries);
    }

    @And("I run the Inquiries Get API")
    public void runInquiriesGetApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    //Vidya-- Method to initiate put Inquiry request
    @And("I initiate create Inquiry task APi")
    public void initiateInquiryPutApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(putInquiry);
    }

    @When("I provide inquiry task details as {string} {string} {string} {string} {string} {string}")
    public void iCanprovideTaskDetails(String caseId, String msgSubject, String msgBody, String phone, String email, String channel) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/inquiryCreateTask.json");

        if (!caseId.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", caseId);
        }
        if (!msgSubject.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(Integer.parseInt(msgSubject));
            this.msgSubject.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("messageSubject", this.msgSubject.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(Integer.parseInt(msgBody));
        this.msgBody.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("messageBody", this.msgBody.get());

        if (!phone.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("respondToPhone", phone);
        }
        if (!email.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("respondToEmail", email);
        }
        if (!channel.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("channel", channel);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @And("I run the create Inquiry task API")
    public void i_run_the_create_inquiry_task_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorCode").isJsonNull()) {
            taskId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("inquiryId").getAsInt());
            traceId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
        }
    }

    @Then("I verify the response of inquiry task has {string} {string} {string} and other details")
    public void verifyInquiryTaskGetAPI(String caseId, String phone, String email) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorCode").isJsonNull(), "errorCode is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorMessage").isJsonNull(), "errorMessage is not null");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("from").getAsInt(), 0, "from is not equal to 0");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("size").getAsInt(), 100, "Size is not equal to 100");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("totalNoOfResults").getAsString().chars().allMatch(Character::isDigit));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("results").get("caseId").getAsString(), caseId, "caseId is wrong");
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("results").
                getAsJsonArray("inquiries").get(0).toString());
        assertEquals(temp.getInt("inquiryId"), taskId.get(), "InquiryId/TaskId is mismatch");
        assertEquals(temp.getString("messageSubject"), msgSubject.get(), "Message Subject is giving wrong value");
        assertEquals(temp.getString("messageBody"), msgBody.get(), "Message Body is giving wrong value");
        assertEquals(temp.getString("respondToPhone"), phone, "Phone number is not correct");
        assertEquals(temp.getString("respondToEmail"), email, "Email Address is not correct");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "CreatedOn date time field IS NOT valid");
    }

    @And("Store record from {int} position to {int}")
    public void storeRecordFrom5thTo15thPosition(int from, int size) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("results").getAsJsonArray("inquiries");
        for (int i = from; i <= size - 1; i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            inquiryIdsList.get().add(temp.getInt("inquiryId"));
        }
    }

    @Then("Check we return record result from 5th to 15th position")
    public void checkWeReturnRecordFrom5thTo15ThPosition() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("results").getAsJsonArray("inquiries");
        assertTrue(json.size() <= 10, "Records return is more then 10");
        ArrayList<Integer> inquiryIds = new ArrayList<>();
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            inquiryIds.add(temp.getInt("inquiryId"));
        }
        assertEquals(inquiryIdsList.get(), inquiryIds, "We are not return records from 5th to 15th position");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("from").getAsInt(), 5, "from is not equal to 5");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("size").getAsInt(), 10, "Size is not equal to 10");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("totalNoOfResults").getAsInt() <= 10, "Not Less then 10");
    }

    @Then("verify errorCode and errorMessage is return in response")
    public void verifyErrorCodeAndErrorMesssageInResponse() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorCode").getAsString(), "TASK-002", "errorCode is wrong");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorMessage").getAsString(), "No inquiries found for this caseId", "errorMessage is wrong");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("from").getAsInt(), 0, "from is not equal to 0");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("size").getAsInt(), 0, "Size is not equal to 0");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("totalNoOfResults").getAsInt(), 0, "totalNoOfResults is not equal to 0");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("results").isJsonNull(), "results is not null");
    }

    @Then("verify sorting order of taskId descending")
    public void verifySortingOrderOfTaskIdDescending() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("results").getAsJsonArray("inquiries");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            inquiryIdsList.get().add(temp.getInt("inquiryId"));
        }
        ArrayList<String> copy = new ArrayList(inquiryIdsList.get());
        Collections.sort(copy, Collections.reverseOrder());
        assertEquals(inquiryIdsList.get(), copy, "Tas are not sorted on Descending order");
    }

    //Vidya-- Method to initiate put External request
    @And("I initiated external create task api")
    public void initiateExternalCreateTaskApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(externalTask);
    }

    @When("I will provide required information to create external task with {string} {string} {string} {string} {string}")
    public void iWillProvideInfoToCreateExternalTask(String caseId, String consumerId, String inboundId, String taskInfo, String triggerDate, List<String> additionalFlds) {
        allFieldsValue.set(new ArrayList<>(additionalFlds));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/externalCreateTask.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", Integer.parseInt(taskTypeId.get()));
        if (taskInfo.equalsIgnoreCase("Validate IVR"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", "7422");
        else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo());

        traceId1.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", traceId1.get());

        this.taskInfo.set(null);
        if (!taskInfo.isEmpty()) {
            this.taskInfo.set(taskInfo);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskInfo", taskInfo);
        }
        if (!triggerDate.isEmpty()) {
            String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTriggerDate", curentDateTime);
        }
        if (!inboundId.isEmpty() && !inboundId.equalsIgnoreCase("takeFromAPI")) {
            CRM_GeneralTaskStepDef.taskValues.get().put("inboundId", inboundId);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(0).getAsJsonObject().addProperty("externalRefId", inboundId);
        } else if (!inboundId.isEmpty() && inboundId.equalsIgnoreCase("takeFromAPI")) {
            CRM_GeneralTaskStepDef.taskValues.get().put("inboundId", World.generalSave.get().get("InboundDocumentId").toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(0).getAsJsonObject().addProperty("externalRefId", World.generalSave.get().get("InboundDocumentId").toString());
        }
        if (!caseId.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(1).getAsJsonObject().addProperty("externalRefId", caseId);
        }
        if (!consumerId.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(2).getAsJsonObject().addProperty("externalRefId", consumerId);
        }
        if (allFieldsValue.get().size() > 1) {
            for (int i = 0; i < allFieldsValue.get().size(); i++) {
                JsonObject temp = new JsonObject();
                temp.add("selectionNumeric", null);
                temp.add("selectionBoolean", null);
                temp.add("selectionDate", null);
                temp.add("selectionDateTime", null);
                temp.add("fieldGroup", null);
                temp.addProperty("selectionFieldName", allFieldsValue.get().get(i));
                i++;
                temp.addProperty("selectionVarchar", allFieldsValue.get().get(i));
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").add(temp);
            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @And("I run the create external task API and check the status code is {string}")
    public void i_run_the_create_external_task_api(String statusCode) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode + "", statusCode);
        if (statusCode.equals("200"))
            this.taskId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskId").getAsInt());
        CRM_TaskManagementStepDef.taskId.set(this.taskId.get() + "");
        traceId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
    }

    @When("I will provide required information and defaultPriority to create external task")
    public void iWillProvideInfoAndDefaultPriorityToCreateExternalTask() {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/externalCreateTask.json");
        initiateGetTaskTypeApi(projectId.get());
        runGetTaskTypeApi();
        i_verify_Task_type_records_are_retrieved();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", Integer.parseInt(taskTypeId.get()));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultPriority", 5);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @Then("In response I will check the {string} value in Task Details and Task Details History table")
    public void verify3kValueIsStoredInTaskDetailsDB(String msgBody) {
        JsonArray json = taskInfoCall.get();
        JsonArray json1 = taskHisCall.get();

        boolean flag = false, flag1 = false;
        assertTrue(json.size() == json1.size(), "Task Details and Task Details History Table array size is mismatch");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp3 = new JSONObject(json1.get(i).toString());
            if (temp1.getString("selectionFieldName").equalsIgnoreCase("Message")) {
                flag = true;
                if (Integer.parseInt(msgBody) == 3000) {
                    assertTrue(temp1.isNull("createdBy"), "createdBy is ot null have some value");
                    assertTrue(temp1.isNull("updatedBy"), "updatedBy is ot null have some value");
                    assertTrue(temp1.isNull("uiid"), "uiid is ot null have some value");
                    assertTrue(temp1.getString("selectionVarchar2").length() == 3000, "3k Char is not stored in Task Details DB");
                } else {
                    assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null have some value");
                    assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is  null have some value");
                    assertEquals(temp1.getString("uiid"), uiid.get(), "uiid is null have some value");
                    assertTrue(temp1.getString("selectionVarchar2").length() == 4000, "4k Char is not stored in Task Details DB");
                }
                assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                        "taskDetailsId is null or equal to 0");
                assertEquals(temp1.getInt("taskId"), taskId.get(), "TaskId is miss match");
                assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                        "taskFieldId is null or equal to 0");
                assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                        "CreatedOn date time field IS NOT valid");
                assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                        "updatedOn date time field IS NOT valid");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is ot null have some value");
                assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is ot null have some value");
                assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is ot null have some value");
                assertTrue(temp1.isNull("selectionDate"), "selectionDate is ot null have some value");
                assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is ot null have some value");
                assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is ot null have some value");
                assertTrue(!temp1.getString("correlationId").isEmpty() && !temp1.isNull("correlationId"),
                        "correlationId is null or has empty string");
                assertEquals(temp1.getString("selectionVarchar2"), this.msgBody.get(), "Message boday text is not matching");
            }
            if (temp3.getString("selectionFieldName").equalsIgnoreCase("Message")) {
                flag1 = true;
                if (Integer.parseInt(msgBody) == 3000) {
                    assertTrue(temp3.isNull("createdBy"), "createdBy is ot null have some value");
                    assertTrue(temp3.isNull("updatedBy"), "updatedBy is ot null have some value");
                    assertTrue(temp3.isNull("uiid"), "uiid is ot null have some value");
                    assertTrue(temp3.getString("selectionVarchar2").length() == 3000, "3k Char is not stored in Task Details DB");
                } else {
                    assertEquals(temp3.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null have some value");
                    assertEquals(temp3.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is  null have some value");
                    assertEquals(temp3.getString("uiid"), uiid.get(), "uiid is null have some value");
                    assertTrue(temp3.getString("selectionVarchar2").length() == 4000, "4k Char is not stored in Task Details DB");
                }
                assertTrue(temp3.getInt("taskDetailHistoryId") != 0 && !temp3.isNull("taskDetailHistoryId"),
                        "taskDetailHistoryId is null or equal to 0");
                assertTrue(temp3.getInt("taskDetailsId") == temp1.getInt("taskDetailsId"),
                        "taskDetailsId in task details ad details history table is mismatch");
                assertEquals(temp3.getInt("taskId"), taskId.get(), "TaskId is miss match");
                assertTrue(temp3.getInt("taskFieldId") == temp1.getInt("taskFieldId"),
                        "taskFieldId in task details ad details history table is mismatch");
                assertTrue(EventsUtilities.isValidDate(temp3.getString("createdOn")),
                        "CreatedOn date time field IS NOT valid");
                assertTrue(EventsUtilities.isValidDate(temp3.getString("updatedOn")),
                        "updatedOn date time field IS NOT valid");
                assertTrue(temp3.isNull("selectionVarchar"), "selectionVarchar is ot null have some value");
                assertTrue(temp3.isNull("selectionBoolean"), "selectionBoolean is ot null have some value");
                assertTrue(temp3.isNull("selectionNumeric"), "selectionNumeric is ot null have some value");
                assertTrue(temp3.isNull("selectionDate"), "selectionDate is ot null have some value");
                assertTrue(temp3.isNull("selectionDateTime"), "selectionDateTime is ot null have some value");
                assertTrue(temp3.isNull("fieldGroup"), "fieldGroup is ot null have some value");
                assertTrue(temp1.getString("correlationId").equals(temp3.getString("correlationId")),
                        "correlationId in task details ad details history table is mismatch");
                assertEquals(temp3.getString("selectionVarchar2"), this.msgBody.get(), "Message boday text is not matching");
            }
            if (flag && flag1) {
                break;
            }
        }
        assertTrue(flag && flag1, "Message body is not there in Task Details and Task Details History Table");
    }

    @When("I provide task information and {string} to create task")
    public void provideTaskInformation(String mesBody) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/createTaskWithAdditionalFields.json");
        uiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", taskTypeId.get());
        String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", curentDateTime);
        int i = Integer.parseInt(dueInDays.get()) + 1;
        String dueDate = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+" + i + "d", 5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultDueDate", dueDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", uiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("staffAssignedTo", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        if (taskPriority != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultPriority", taskPriority.get());

        if (dueInDays.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dueInDays", dueInDays.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("statusDate", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").get(0).getAsJsonObject().
                addProperty("uiid", uiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").get(0).getAsJsonObject().
                addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").get(0).getAsJsonObject().
                addProperty("createdOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").get(0).getAsJsonObject().
                addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").get(0).getAsJsonObject().
                addProperty("updatedOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(Integer.parseInt(mesBody));
        this.msgBody.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").get(0).getAsJsonObject().
                addProperty("selectionVarchar2", msgBody.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @Then("I verify Missing required parameters Error Message in response")
    public void iVerifyMissingRequiredParametersErrorMessageInResponse() throws Exception {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorCode").getAsString(), "TASK-003", "errorCode is wrong");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorMessage").getAsString(),
                "Request requires caseId and channel to be populated.  Also requires: respondToEmail contains '@' and '.com', respondToPhone contains at least 10 but not more than 12 characters (may contain dashes), messageSubject is 140 characters or less, messageBody limit is 3,000 characters or less, channel is 'Web' or 'IVR'.",
                "errorMessage is wrong");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("caseId").isJsonNull(), "caseId is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("inquiryId").isJsonNull(), "inquiryId is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("createdOn").isJsonNull(), "createdOn is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("messageSubject").isJsonNull(), "messageSubject is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("messageBody").isJsonNull(), "messageBody is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("respondToPhone").isJsonNull(), "respondToPhone is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("respondToEmail").isJsonNull(), "respondToEmail is not null");
    }

    @Then("I verify the response structure has {string}  {string} {string}")
    public void iVerifyTheResponseStructure(String caseId, String phone, String email) throws Exception {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorCode").isJsonNull(), "errorCode is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorMessage").isJsonNull(), "errorMessage is is not null");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("caseId").getAsInt() + "", caseId, "caseId is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("inquiryId").getAsInt() != 0 &&
                !API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("inquiryId").isJsonNull(), "inquiryId is null or 0");
        assertTrue(EventsUtilities.isValidDate(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("createdOn").getAsString()),
                "CreatedOn date time field IS NOT valid");
        if (msgSubject.get() == null) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("messageSubject").isJsonNull(), "messageSubject is not null");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("messageSubject").getAsString(), msgSubject.get(), "messageSubject is wrong");
        }
        if (phone.isEmpty()) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("respondToPhone").isJsonNull(), "respondToPhone is not null");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("respondToPhone").getAsString(), phone, "respondToPhone is wrong");
        }
        if (email.isEmpty()) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("respondToEmail").isJsonNull(), "respondToEmail is not null");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("respondToEmail").getAsString(), email, "respondToEmail is wrong");
        }

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("messageBody").getAsString(), msgBody.get(), "messageBody is wrong");
    }

    @Then("I verify Task table information in response")
    public void verifyTaskTableInformationInResponse() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        assertEquals(temp.getInt("taskId"), taskId.get(), "TaskId is miss match");
        assertEquals(temp.getInt("taskTypeId") + "", taskTypeId.get(), "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", taskPriority.get(), "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", dueInDays.get(), "dueInDays is miss match");
        assertEquals(temp.getString("source"), "System", "source is miss match");
        assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertTrue(temp.isNull("createdBy"), "createdBy is not null");
        assertTrue(temp.isNull("updatedBy"), "updatedBy is not null");
        assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        if (traceId.get() == null || traceId.get().isEmpty()) {
            assertTrue(!temp.isNull("correlationId") || !temp.getString("correlationId").isEmpty(),
                    "correlationId should not be null or empty");
        } else {
            assertEquals(temp.getString("correlationId"), traceId.get(), "correlationId is miss match");
        }
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertTrue(temp.isNull("action"), "action is not null");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @And("Get the task type information of {string} for project {string}")
    public void getTaskTypeInformation(String taskType, String projectId) {
        initiateGetTaskTypeApi(projectId);
        runGetTaskTypeApi();
        getTaskTypeId(taskType);
    }

    @Then("I verify Task Details and Task Details History table information in response {string} {string} {string}")
    public void verify3informationInTaskDetailsAndHistoryDB(String phone, String email, String channel) {
        JsonArray json = taskInfoCall.get();
        JsonArray json1 = taskHisCall.get();

        String str[] = {"Message Subject", "Channel", "From Email", "From Phone", "Message"};
        String value[] = {msgSubject.get(), channel, email, phone, msgBody.get()};

        assertTrue(json.size() != 0, "Task Details Array is empty");
        assertTrue(json.size() == json1.size(), "Task Details and Task Details History Table array size is mismatch");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp2 = new JSONObject(json1.get(i).toString());

            assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp1.getInt("taskId"), taskId.get(), "TaskId is miss match");
            assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(temp1.isNull("createdBy"), "createdBy is ot null have some value");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp1.isNull("updatedBy"), "updatedBy is ot null have some value");
            if (str[i].equals("Message")) {
                assertEquals(temp1.getString("selectionVarchar2"), value[i],
                        "selectionVarchar2 is ot null have some value");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
            } else {
                assertEquals(temp1.getString("selectionVarchar"), value[i],
                        "selectionVarchar is ot null have some value");
                assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            }
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertEquals(temp1.getString("selectionFieldName"), str[i],
                    "selectionVarchar is ot null have some value");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null have some value");
            if (traceId.get() == null || traceId.get().isEmpty()) {
                assertTrue(!temp1.isNull("correlationId") || !temp1.getString("correlationId").isEmpty(),
                        "correlationId should not be null or empty");
            } else {
                assertEquals(temp1.getString("correlationId"), traceId.get(), "correlationId is miss match");
            }
            assertTrue(temp1.isNull("uiid"), "uiid is not null have some value");

            assertTrue(temp2.getInt("taskDetailHistoryId") != 0 && !temp2.isNull("taskDetailHistoryId"),
                    "taskDetailHistoryId is null or equal to 0");
            assertTrue(temp2.getInt("taskDetailsId") != 0 && !temp2.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp2.getInt("taskId"), taskId.get(), "TaskId is miss match");
            assertTrue(temp2.getInt("taskFieldId") != 0 && !temp2.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(temp2.isNull("createdBy"), "createdBy is not null have some value");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp2.isNull("updatedBy"), "updatedBy is not null have some value");
            if (str[i].equals("Message")) {
                assertEquals(temp2.getString("selectionVarchar2"), value[i],
                        "selectionVarchar2 is ot null have some value");
                assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar is not null");
            } else {
                assertEquals(temp2.getString("selectionVarchar"), value[i],
                        "selectionVarchar is ot null have some value");
                assertTrue(temp2.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            }
            assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp2.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp2.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp2.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertEquals(temp2.getString("selectionFieldName"), str[i],
                    "selectionVarchar is ot null have some value");
            assertTrue(temp2.isNull("fieldGroup"), "fieldGroup is not null have some value");
            if (traceId.get() == null || traceId.get().isEmpty()) {
                assertTrue(!temp2.isNull("correlationId") || !temp2.getString("correlationId").isEmpty(),
                        "correlationId should not be null or empty");
            } else {
                assertEquals(temp2.getString("correlationId"), traceId.get(), "correlationId is miss match");
            }
            assertTrue(temp2.isNull("uiid"), "uiid is not null have some value");
        }
    }

    @Then("I verify External Link table information in response has {string}")
    public void verifyTInformationInExternalLinkTable(String caseId) {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonArray("taskLinksVOS").get(0).getAsJsonObject().toString());

        assertTrue(temp.getInt("taskLinkId") != 0 && !temp.isNull("taskLinkId"),
                "taskLinkId is null or equal to 0");
        assertEquals(temp.getInt("internalRefId"), taskId.get(), "internalRefId is miss match");
        assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
        assertEquals(temp.getString("externalLinkRefType"), "CASE", "externalLinkRefType is miss match");
        assertEquals(temp.getInt("externalLinkRefId") + "", caseId, "externalLinkRefId is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                "effectiveStartDate date time field IS NOT valid");
        assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(temp.isNull("createdBy"), "createdBy is not null have some value");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        assertTrue(temp.isNull("updatedBy"), "updatedBy is not null have some value");
        if (traceId.get() == null || traceId.get().isEmpty()) {
            assertTrue(!temp.isNull("correlationId") || !temp.getString("correlationId").isEmpty(),
                    "correlationId should not be null or empty");
        } else {
            assertEquals(temp.getString("correlationId"), traceId.get(), "correlationId is miss match");
        }
        assertTrue(temp.isNull("uiid"), "uiid is not null have some value");
    }

    @When("I run create task API for error file")
    public void runCreateTaskAPIForErrorFile() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("errorResponse"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "error");

    }

    @Then("I verify error message is getting in response as {string}")
    public void verifyErrorMessageInResponseFile(String errorCode) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("errorResponse").
                get("errorCode").getAsString().contains(errorCode));
    }

    //Author: Basha
    @When("I provide all fields task information to create task {string}")
    public void provideTaskInformationAllFields(String selectValueType, List<String> values) {
        allFieldsValue.set(new ArrayList<String>(values));

        if (selectValueType.equalsIgnoreCase("withSingleVlu")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/createTaskForAllFields.json");
        } else if (selectValueType.equalsIgnoreCase("withMultipleVlu")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/AllFieldsCreateTaskWithMultiDDValues.json");
        } else if (selectValueType.equalsIgnoreCase("missingField")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/createTaskWithMissingField.json");
        }

        uiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", taskTypeId.get());
        System.out.println("TaskType from Payload  " + taskTypeId.get());
        String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", curentDateTime);
        int i = Integer.parseInt(dueInDays.get()) + 1;
        String dueDate = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+" + i + "d", 5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultDueDate", dueDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", uiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("staffAssignedTo", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        assignee.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("statusDate", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        if (taskPriority.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultPriority", taskPriority.get());

        if (dueInDays != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dueInDays", dueInDays.get());

        JsonArray json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails");
        for (int j = 0; j < values.size(); j++) {
            String fldName = json.get(j).getAsJsonObject().get("selectionFieldName").getAsString();
            if (fldName.equalsIgnoreCase("Appointment Date") ||
                    fldName.equalsIgnoreCase("Date of Birth") ||
                    fldName.equalsIgnoreCase("Appointment Date") ||
                    fldName.equalsIgnoreCase("Plan Effective Date") ||
                    fldName.equalsIgnoreCase("Plan Start Date") ||
                    fldName.equalsIgnoreCase("Preferred Call Back Date")) {
                json.get(j).getAsJsonObject().getAsJsonObject().addProperty("selectionDate", values.get(j));
            } else if (fldName.equalsIgnoreCase("Inbound Correspondence Workable Flag") ||
                    fldName.equalsIgnoreCase("Requested New Plan") ||
                    fldName.equalsIgnoreCase("Requested New Provider") ||
                    fldName.equalsIgnoreCase("Urgent Access to Care")) {
                json.get(j).getAsJsonObject().getAsJsonObject().addProperty("selectionBoolean", true);
            } else if (!fldName.equalsIgnoreCase("Inbound Correspondence Type")) {
                json.get(j).getAsJsonObject().getAsJsonObject().addProperty("selectionVarchar", values.get(j));
            }
            json.get(j).getAsJsonObject().getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
            json.get(j).getAsJsonObject().getAsJsonObject().addProperty("createdOn", curentDateTime);
            json.get(j).getAsJsonObject().getAsJsonObject().addProperty("uiid", uiid.get());
            json.get(j).getAsJsonObject().getAsJsonObject().addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
            json.get(j).getAsJsonObject().getAsJsonObject().addProperty("updatedOn", curentDateTime);
        }
        taskInfo.set(null);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    //Author: Basha
    @Then("I verify task table record")
    public void verifyTaskTableRecord() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());

        assertEquals(temp.getInt("taskId"), taskId.get(), "TaskId is miss match");
        assertEquals(temp.getInt("taskTypeId"), Integer.parseInt(taskTypeId.get()), "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority"), Integer.parseInt(taskPriority.get()), "defaultPriority is miss match");

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate IS NOT valid");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        if (assignee.get() == null) {
            assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        } else {
            assertEquals(temp.getString("staffAssignedTo"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
        }
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays"), Integer.parseInt(dueInDays.get()), "dueInDays is miss match");
        assertEquals(temp.getString("source"), "User", "taskStatus is miss match");
        if (taskInfo.get() == null) {
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        } else {
            assertEquals(temp.getString("taskInfo"), taskInfo.get(), "taskInfo is null");
        }
        assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
        assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not False");
        assertTrue(!temp.getString("correlationId").isEmpty() && !temp.isNull("correlationId"), "correlationId is null or has empty string");
        assertEquals(temp.getString("uiid"), uiid.get(), "uiid is null have some value");
        assertTrue(temp.isNull("action"), "action is not null");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "CreatedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")), "updatedOn date time field IS NOT valid");
    }

    //Author: Basha
    @Then("I verify task details and task history details table records")
    public void verifyTaskDetailsAndTaskHistoryDetailsTableRecords() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("taskDetails");
        JsonArray json1 = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskHistoryVO").getAsJsonArray("taskDetails");

        assertTrue(json.size() == json1.size(), "Task Details and Task Details History Table array size is mismatch");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp2 = new JSONObject(json1.get(i).toString());

            String fldName = temp1.getString("selectionFieldName");

            if (fldName.equalsIgnoreCase("Appointment Date") ||
                    fldName.equalsIgnoreCase("Date of Birth") ||
                    fldName.equalsIgnoreCase("Plan Effective Date") ||
                    fldName.equalsIgnoreCase("Plan Start Date") ||
                    fldName.equalsIgnoreCase("Preferred Call Back Date")) {
                assertEquals(temp1.getString("selectionDate"), allFieldsValue.get().get(i), "selectionDate is miss match");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
                assertEquals(temp2.getString("selectionDate"), allFieldsValue.get().get(i), "selectionDate is miss match");
                assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null");
            } else if (fldName.equalsIgnoreCase("Inbound Correspondence Workable Flag") ||
                    fldName.equalsIgnoreCase("Requested New Plan") ||
                    fldName.equalsIgnoreCase("Requested New Provider") ||
                    fldName.equalsIgnoreCase("Urgent Access to Care")) {
                assertTrue(temp1.getBoolean("selectionBoolean"), "selectionBoolean is not true");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null");
                assertTrue(temp2.getBoolean("selectionBoolean"), "selectionBoolean is not true");
                assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp2.isNull("selectionDate"), "selectionDate is not null");
            } else if (fldName.equalsIgnoreCase("Inbound Correspondence Type")) {
                assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null");
                assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null");
                assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp2.isNull("selectionDate"), "selectionDate is not null");
            } else {
                assertEquals(temp1.getString("selectionVarchar"), allFieldsValue.get().get(i), "selectionVarchar is miss match");
                assertTrue(temp1.isNull("selectionDate"), "selectionVarchar is not null");
                assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
                assertEquals(temp2.getString("selectionVarchar"), allFieldsValue.get().get(i), "selectionVarchar is miss match");
                assertTrue(temp2.isNull("selectionDate"), "selectionDate is not null");
                assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null");
            }
            assertEquals(temp1.getInt("taskId"), taskId.get(), "taskId is miss match");
            assertTrue(temp1.getInt("taskDetailsId") != 0 || !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal 0");
            assertTrue(temp1.getInt("taskFieldId") != 0 || !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal 0");
            assertEquals(temp1.getInt("taskId"), taskId.get(), "taskId is miss match");
            assertEquals(temp1.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp1.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp1.getString("uiid"), uiid.get(), "uiid is miss match");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(!temp1.getString("correlationId").isEmpty() || !temp1.isNull("correlationId"),
                    "correlationId is null or empty string");

            assertEquals(temp2.getInt("taskId"), taskId.get(), "taskId is miss match");
            assertTrue(temp2.getInt("taskDetailsId") != 0 || !temp2.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal 0");
            assertTrue(temp2.getInt("taskDetailHistoryId") != 0 || !temp2.isNull("taskDetailHistoryId"),
                    "taskDetailHistoryId is null or equal 0");
            assertTrue(temp2.getInt("taskFieldId") != 0 || !temp2.isNull("taskFieldId"),
                    "taskFieldId is null or equal 0");
            assertEquals(temp2.getInt("taskId"), taskId.get(), "taskId is miss match");
            assertEquals(temp2.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp2.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp2.getString("uiid"), uiid.get(), "uiid is miss match");
            assertTrue(temp2.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp2.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp2.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(!temp2.getString("correlationId").isEmpty() || !temp2.isNull("correlationId"),
                    "correlationId is null or empty string");

            if (fldName.contains("Case Worker")) {
                assertEquals(temp1.getString("fieldGroup"), "CASE_WORKER", "fieldGroup is miss match");
                assertEquals(temp2.getString("fieldGroup"), "CASE_WORKER", "fieldGroup is miss match");
            } else if (fldName.contains("New Addres")) {
                assertEquals(temp1.getString("fieldGroup"), "NEW_ADDRESS", "fieldGroup is miss match");
                assertEquals(temp2.getString("fieldGroup"), "NEW_ADDRESS", "fieldGroup is miss match");
            } else if (fldName.contains("Old Address")) {
                assertEquals(temp1.getString("fieldGroup"), "OLD_ADDRESS", "fieldGroup is miss match");
                assertEquals(temp2.getString("fieldGroup"), "OLD_ADDRESS", "fieldGroup is miss match");
            } else if (fldName.contains("Provider Address")) {
                assertEquals(temp1.getString("fieldGroup"), "PROVIDER_ADDRESS", "fieldGroup is miss match");
                assertEquals(temp2.getString("fieldGroup"), "PROVIDER_ADDRESS", "fieldGroup is miss match");
            } else if (fldName.contains("Provider First Name")) {
                assertEquals(temp1.getString("fieldGroup"), "PROVIDER", "fieldGroup is miss match");
                assertEquals(temp2.getString("fieldGroup"), "PROVIDER", "fieldGroup is miss match");
            } else if (fldName.contains("Provider Last Name")) {
                assertEquals(temp1.getString("fieldGroup"), "PROVIDER", "fieldGroup is miss match");
                assertEquals(temp2.getString("fieldGroup"), "PROVIDER", "fieldGroup is miss match");
            } else {
                assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null");
                assertTrue(temp2.isNull("fieldGroup"), "fieldGroup is not null");
            }
        }
    }

    @Then("I verify Task table information in response of external task")
    public void verifyTaskTableInformationInResponseExternalTask() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        assertEquals(temp.getInt("taskId"), taskId.get(), "TaskId is miss match");
        assertEquals(temp.getInt("taskTypeId") + "", taskTypeId.get(), "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", taskPriority.get(), "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", String.valueOf(Integer.parseInt(dueInDays.get()) + 1), "dueInDays is miss match");
        assertEquals(temp.getString("source"), "System", "source is miss match");
        System.out.println("Vidya " + taskInfo.get() + "    " + temp.get("taskInfo").toString());
        if (taskInfo.get() == null || taskInfo.get().isEmpty() || taskInfo.get().equals("")) {
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        } else {
            assertEquals(temp.getString("taskInfo"), taskInfo, "taskInfo is null");
        }
        assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
        assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not false");
        assertEquals(temp.getString("correlationId"), traceId1.get(), "correlationId is miss match");
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertTrue(temp.isNull("action"), "action is not null");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I verify Task history table information in response of external task")
    public void verifyTaskHistoryTableInformationInResponseExternalTask() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskHistoryVOs").get(0).
                getAsJsonObject().toString());
        if (CRM_TaskManagementStepDef.taskId.get().isEmpty()) {
            assertEquals(temp.getInt("taskId") + "", APITaskManagementController.taskId.get(), "TaskId is miss match");
        } else {
            assertEquals(temp.getInt("taskId") + "", CRM_TaskManagementStepDef.taskId.get(), "TaskId is miss match");
        }
        assertTrue(temp.getInt("taskHistoryId") != 0 || !temp.isNull("taskHistoryId"),
                "taskHistoryId is null or equal 0");
        assertTrue(temp.get("taskTypeId").toString().equals(taskTypeId.get()) || temp.getInt("taskTypeId") != 0 || !temp.isNull("taskTypeId"),
                "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertTrue(temp.get("defaultPriority").toString().equals(taskPriority.get()) ||
                        temp.get("defaultPriority").toString().equals(CRM_GeneralTaskStepDef.taskValues.get().get("priority")),
                "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertTrue(temp.getString("taskStatus").equals("Created") || temp.getString("taskStatus").equals(CRM_GeneralTaskStepDef.taskValues.get().get("status")),
                "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("assignee"))
            assertTrue(temp.getInt("staffAssignedTo") != 0 || !temp.isNull("staffAssignedTo"),
                    "staffAssignedTo is null or equal 0");
        else
            assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertTrue(temp.get("dueInDays").toString().equals(dueInDays.get()) || temp.getInt("dueInDays") != 0 || !temp.isNull("dueInDays"),
                "dueInDays is miss match");
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status") != null)
            assertEquals(temp.getString("source"), "User", "source is miss match");
        else
            assertEquals(temp.getString("source"), "System", "source is miss match");
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskInfo") && !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("-- --") &&
                !CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("")) {
            assertEquals(temp.getString("taskInfo"), CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"), "taskInfo is null");
        } else if (!this.taskInfo.get().isEmpty()) {
            assertEquals(temp.getString("taskInfo"), this.taskInfo.get(), "taskInfo is incorrect");
        } else {
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        }
        assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("status") && CRM_GeneralTaskStepDef.taskValues.get().get("status") != null) {
            assertFalse(temp.isNull("correlationId"), "correlationId is null");
        } else {
            if (temp.get("correlationId") instanceof Integer) {
                assertTrue(temp.getInt("correlationId") != 0, "correlationId is miss match");
            } else {
                assertTrue(temp.get("correlationId").toString().equals(traceId.get()) || !temp.isNull("correlationId"),
                        "correlationId is miss match");
            }
        }
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertEquals(temp.getString("action"), "INSERT", "action is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("actionedOn")),
                "actionedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I verify Task Details and Task Details History table information in response")
    public void verify3informationInTaskDetailsAndHistoryDB() {
        JsonArray json = taskInfoCall.get();
        JsonArray json1 = taskHisCall.get();

        assertTrue(json.size() != 0, "Task Details Array is empty");
        assertTrue(json.size() == json1.size(), "Task Details and Task Details History Table array size is mismatch");

        int j = 0;
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp2 = new JSONObject(json1.get(i).toString());

            assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp1.getInt("taskId"), taskId.get(), "TaskId is miss match");
            assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertEquals(temp1.getString("selectionFieldName"), allFieldsValue.get().get(j++),
                    "selectionFieldName is value is mismatch");
            assertEquals(temp1.getString("selectionVarchar"), allFieldsValue.get().get(j--),
                    "selectionVarchar is value is mismatch");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertEquals(temp1.getString("correlationId"), traceId.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp1.isNull("uiid"), "uiid is not null have some value");

            assertTrue(temp2.getInt("taskDetailHistoryId") != 0 && !temp2.isNull("taskDetailHistoryId"),
                    "taskDetailHistoryId is null or equal to 0");
            assertTrue(temp2.getInt("taskDetailsId") != 0 && !temp2.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp2.getInt("taskId"), taskId.get(), "TaskId is miss match");
            assertTrue(temp2.getInt("taskFieldId") != 0 && !temp2.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertEquals(temp2.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp2.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp2.getString("selectionFieldName"), allFieldsValue.get().get(j++),
                    "selectionFieldName is value is mismatch");
            assertEquals(temp2.getString("selectionVarchar"), allFieldsValue.get().get(j++),
                    "selectionVarchar is value is mismatch");
            assertTrue(temp2.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp2.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp2.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp2.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(temp2.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertEquals(temp2.getString("correlationId"), traceId.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp2.isNull("uiid"), "uiid is not null have some value");
        }
    }

    @Then("I verify External Link table information in response has {string} {string} {string}")
    public void verifyTInformationInExternalLinkTable(String caseId, String consumerId, String inboundId) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonArray("taskLinksVOS");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            assertTrue(temp.getInt("taskLinkId") != 0 && !temp.isNull("taskLinkId"),
                    "taskLinkId is null or equal to 0");
            assertEquals(temp.getInt("internalRefId"), taskId.get(), "internalRefId is miss match");
            assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
            if (i == 0) {
                assertEquals(temp.getString("externalLinkRefType"), "INBOUND_CORRESPONDENCE", "externalLinkRefType is miss match");
                assertEquals(temp.getInt("externalLinkRefId") + "", inboundId, "externalLinkRefId is miss match");
            } else if (i == 1) {
                assertEquals(temp.getString("externalLinkRefType"), "CASE", "externalLinkRefType is miss match");
                assertEquals(temp.getInt("externalLinkRefId") + "", caseId, "externalLinkRefId is miss match");
            } else if (i == 1) {
                assertEquals(temp.getString("externalLinkRefType"), "CONSUMER", "externalLinkRefType is miss match");
                assertEquals(temp.getInt("externalLinkRefId") + "", consumerId, "externalLinkRefId is miss match");
            }

            assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                    "effectiveStartDate date time field IS NOT valid");
            assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
            assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                    "createdOn date time field IS NOT valid");
            assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp.getString("correlationId"), traceId.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp.isNull("uiid"), "uiid is not null have some value");
        }
    }

    @And("I run the create Inquiry task API without traceId")
    public void i_run_the_create_inquiry_task_api_without_traceId() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPIWithoutTraceId(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorCode").isJsonNull()) {
            taskId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("inquiryId").getAsInt());
        }
        traceId.set(null);
    }

    @And("I will verify task details and external link table data is empty")
    public void verifyTaskDetailsAndExternalLinkTableInfoIsEmpty() throws Exception {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("taskDetails");

        JsonArray json1 = taskHisCall.get();

        JsonArray json2 = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonArray("taskLinksVOS");

        assertTrue(json.size() == 0 && json1.size() == 0 && json2.size() == 0);
    }

    @When("I will provide required information to create external task with {string} {string} {string} {string} {string} {string}")
    public void iWillProvideInfoToCreateExternalTask1(String caseId, String consumerId, String inboundId, String inboundId2, String taskInfo, String triggerDate, List<String> additionalFlds) {
        allFieldsValue.set(new ArrayList<>(additionalFlds));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/externalCreateTaskWithMulInboundIDs.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", Integer.parseInt(taskTypeId.get()));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());

        this.taskInfo.set(null);
        if (!taskInfo.isEmpty()) {
            this.taskInfo.set(taskInfo);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskInfo", taskInfo);
        }
        if (!triggerDate.isEmpty()) {
            String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTriggerDate", curentDateTime);
        }
        if (!inboundId.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(0).getAsJsonObject()
                    .addProperty("externalRefId", inboundId);
        }
        if (!inboundId2.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(1).getAsJsonObject()
                    .addProperty("externalRefId", inboundId);
        }
        if (!caseId.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(2).getAsJsonObject()
                    .addProperty("externalRefId", caseId);
        }
        if (!consumerId.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("externalLinks").get(3).getAsJsonObject()
                    .addProperty("externalRefId", consumerId);
        }
        if (allFieldsValue.get().size() > 1) {
            for (int i = 0; i < allFieldsValue.get().size(); i++) {
                JsonObject temp = new JsonObject();
                temp.add("selectionNumeric", null);
                temp.add("selectionBoolean", null);
                temp.add("selectionDate", null);
                temp.add("selectionDateTime", null);
                temp.add("fieldGroup", null);
                temp.addProperty("selectionFieldName", allFieldsValue.get().get(i));
                i++;
                temp.addProperty("selectionVarchar", allFieldsValue.get().get(i));
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").add(temp);
            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }


    public void createTaskReturnTaskID() {
        initiateGetTaskTypeApi("");
        runGetTaskTypeApi();
        getTaskTypeId("General");
        initiateCreateTask();
        provideTaskInformation();
        runCreateTask();
        verifyTaskCreted();
    }

    @Given("I initiated get Correspondence List API")
    public void iInitiatedGetCorrespondenceListAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(corresDefService);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(corresListCrm);
    }

    @And("I run get Api request for Correspondence List")
    public void iRunGetApiRequestForCorrespondenceList() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
    }

    //Author: Basha
    @Then("I verify task details and task history details table records for NJ task")
    public void verifyTaskDetailsAndTaskHistoryDetailsTableRecordsForNJTask() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("taskDetails");
        JsonArray json1 = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskHistoryVOs").getAsJsonArray("taskDetails");

        assertTrue(json.size() == json1.size(), "Task Details and Task Details History Table array size is mismatch");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp2 = new JSONObject(json1.get(i).toString());
            String fldName = temp1.getString("selectionFieldName");

            if (fldName.equalsIgnoreCase("Complaint About")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("complaintAbout")), "complaintAbout is miss match");
                assertTrue(temp2.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("complaintAbout")), "complaintAbout is miss match");
            } else if (fldName.equalsIgnoreCase("External Application ID")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("exAppId")), "exAppId is mismatch");
                assertTrue(temp2.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("exAppId")), "exAppId is mismatch");
            } else if (fldName.equalsIgnoreCase("External Case ID")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("extCaseID")), "extCaseID is mismatch");
                assertTrue(temp2.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("extCaseID")), "extCaseID is mismatch");
            } else if (fldName.equalsIgnoreCase("Name")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("name")), "name is mismatch");
                assertTrue(temp2.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("name")), "name is mismatch");
            } else if (fldName.equalsIgnoreCase("Reason")) {
                assertTrue(temp1.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("reason")), "reason is mismatch");
                assertTrue(temp2.getString("selectionVarchar").equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("reason")), "reason is mismatch");
            }

            assertEquals(temp1.getInt("taskId"), Integer.parseInt(apiTaskId.get()), "TaskId is miss match");
            assertTrue(temp1.getInt("taskDetailsId") != 0 || !temp1.isNull("taskDetailsId"), "taskDetailsId is null or equal 0");
            assertTrue(temp1.getInt("taskFieldId") != 0 || !temp1.isNull("taskFieldId"), "taskFieldId is null or equal 0");
            assertEquals(temp1.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp1.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")), "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")), "updatedOn date time field IS NOT valid");

            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
            assertTrue(temp1.isNull("selectionDate"), "selectionVarchar is not null");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null");
            assertTrue(!temp1.getString("correlationId").isEmpty() || !temp1.isNull("correlationId"), "correlationId is null or empty string");
            assertEquals(temp2.getInt("taskId"), Integer.parseInt(apiTaskId.get()), "TaskId is miss match");

            assertTrue(temp2.getInt("taskDetailsId") != 0 || !temp2.isNull("taskDetailsId"), "taskDetailsId is null or equal 0");
            assertTrue(temp2.getInt("taskDetailHistoryId") != 0 || !temp2.isNull("taskDetailHistoryId"), "taskDetailHistoryId is null or equal 0");
            assertTrue(temp2.getInt("taskFieldId") != 0 || !temp2.isNull("taskFieldId"), "taskFieldId is null or equal 0");
            assertEquals(temp2.getInt("createdBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp2.getInt("updatedBy") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("createdOn")), "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("updatedOn")), "updatedOn date time field IS NOT valid");

            assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null");
            assertTrue(temp2.isNull("selectionDate"), "selectionVarchar is not null");
            assertTrue(temp2.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp2.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp2.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp2.isNull("fieldGroup"), "fieldGroup is not null");
            assertTrue(!temp2.getString("correlationId").isEmpty() || !temp2.isNull("correlationId"), "correlationId is null or empty string");
        }
    }

    //Author: Basha
    @Then("I verify task table record for the task")
    public void verifyTaskTableRecordForTheTask() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        JSONObject temp2 = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().toString());

        if (apiTaskId.get() == null && apiSrId.get() != null) {
            assertEquals(temp.getInt("taskId"), Integer.parseInt(apiSrId.get()), "SRId is miss match");
        } else {
            assertEquals(temp.getInt("taskId"), Integer.parseInt(apiTaskId.get()), "TaskId is miss match");
        }
        assertTrue(temp.getInt("taskTypeId") != 0 && !temp.isNull("taskTypeId"),
                "taskTypeId is 0 or null");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "",
                CRM_GeneralTaskStepDef.taskValues.get().get("priority"), "defaultPriority is miss match");
        assertEquals(temp.getString("defaultDueDate"),
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dueDate")), "defaultDueDate IS NOT valid");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"),
                CRM_GeneralTaskStepDef.taskValues.get().get("status"), "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("assignee") == null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee").equals("-- --") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("assignee").isEmpty()) {
            assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        } else {
            assertEquals(temp.getInt("staffAssignedTo") + "", getUserIdByUserNameAndProjectName(),
                    "staffAssignedTo is miss match");
        }
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        /*assertEquals(temp.getInt("dueInDays") + "",
                CRM_GeneralTaskStepDef.taskValues.get().get("dueIn").split(" ")[0], "dueInDays is miss match");*/
        assertEquals(temp.getString("source"),
                CRM_GeneralTaskStepDef.taskValues.get().get("source"), "taskStatus is miss match");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("-- --"))
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        else
            assertEquals(temp.getString("taskInfo"),
                    CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"), "taskInfo is miss match");
        if (!CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskNote") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("taskNote").equals("-- --"))
            assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        else
            assertEquals(temp.getString("taskNotes"),
                    CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"), "taskNotes is miss match");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        if (!CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForCancel") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel").equals("-- --")) {
            assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        } else
            assertEquals(temp.getString("cancelReason").replaceAll("_", " "), CRM_GeneralTaskStepDef.taskValues.get().get("reasonForCancel").toUpperCase(), "reasonForCancel is miss match");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), getUserIdByUserNameAndProjectName(), "createdBy is miss match");
        assertEquals(temp.getString("updatedBy"), getUserIdByUserNameAndProjectName(), "updatedBy is miss match");
        //assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not False");  Wrong assertion: EscalationFlag can vary
        assertTrue(!temp.getString("correlationId").isEmpty() && !
                temp.isNull("correlationId"), "correlationId is null or has empty string");
        assertTrue(temp.isNull("action"), "action is not null");
        assertTrue(temp.isNull("genericField1Txt"), "genericField1Txt is not null");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "CreatedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
        if (CRM_GeneralTaskStepDef.taskValues.get().get("businessUnitAssigneeTo") == null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("businessUnitAssigneeTo").equals("-- --") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("businessUnitAssigneeTo").isEmpty()) {
            assertTrue(temp.isNull("businessUnitAssignedTo"), "businessUnitAssignedTo is not null");
        } else {
            String businessUnitsIds = "";
            businessUnitsIds = temp.get("businessUnitAssignedTo").toString();
            assertTrue(businessUnitData.get().containsKey(businessUnitsIds));
            assertEquals(CRM_GeneralTaskStepDef.taskValues.get().get("businessUnitAssigneeTo"), businessUnitData.get().get(businessUnitsIds));
        }
        initiateGetTaskTypeApi("");
        runGetTaskTypeApi();
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            if (temp1.getInt("taskTypeId") == temp.getInt("taskTypeId")) {
                assertEquals(temp2.getBoolean("serviceRequestInd"), temp1.getBoolean("serviceRequestInd"), "serviceRequestInd is miss match");
                break;
            }
        }
    }

    @Then("I run get Api request for Outbound Correspondence Link")
    public void iRunGetApiRequestForOutboundCorrespondenceLink() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
    }

    @Then("I initiated get Api request with CaseId {string} for Outbound Correspondence Link")
    public void iRunInitiatedApiRequestWithCaseIdForOutboundCorrespondenceLink(String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiCorresLink);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseId + outBoundcorresLink);
    }

    @Then("Verify response id for Outbound Correspondence Link")
    public void verifyResponseIdForOutboundCorrespondenceLink() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalLinkDetails.content[0].name").equalsIgnoreCase("Case"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.externalLinkDetails.content[0].internalId") == 68);
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalLinkDetails.content[0].effectiveStartDate"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalLinkDetails.content[0].createdOn"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalLinkDetails.content[0].createdOn"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.externalLinkDetails.content[1].id") == 287);
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalLinkDetails.content[1].name").equalsIgnoreCase("Consumer Profile"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.externalLinkDetails.content[1].internalId") == 68);
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalLinkDetails.content[1].effectiveStartDate"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalLinkDetails.content[1].createdOn"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.externalLinkDetails.content[1].createdOn"));
    }


    @When("I provide task information to create task with custom data {string}")
    public void provideTaskInformationWithcustomdata(String assignee) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/createTask.json");
        correlationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
        uiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);
        String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", taskTypeId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", uiid.get());
        if (taskPriority.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultPriority", taskPriority.get());

        if (dueInDays.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dueInDays", dueInDays.get());

        int i = Integer.parseInt(dueInDays.get());
        String tempdate = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        String X = tempdate.substring(0, 10);

        LocalDate localDate = addDaysSkippingWeekends(LocalDate.parse(X), i);
        String dueDate = localDate + "T23:59:05.755Z";
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultDueDate", dueDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", uiid.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        if (taskPriority.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("defaultPriority", taskPriority.get());

        if (dueInDays.get() != null)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dueInDays", dueInDays.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("statusDate", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        switch (assignee) {
            case "Service Account1":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("staffAssignedTo", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                this.assignee.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                break;
            case "null":
                this.assignee.set(null);
                break;
            default:
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("staffAssignedTo", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                this.assignee.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                break;
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        taskInfo.set("Task created by API automation");
    }

    @Then("I provide task information to initiate task {string}")
    public void provideTaskInformationToInitiatetask(String taskStatus) {

        String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("taskVO").toString());
        HashMap<String, Object> taskVO1 = new HashMap<>();
        taskVO1.put("taskId", temp.getInt("taskId"));
        taskVO1.put("taskTypeId", temp.getInt("taskTypeId"));
        taskVO1.put("defaultDueDate", temp.getString("defaultDueDate"));
        taskVO1.put("overridePriority", temp.get("overridePriority"));
        taskVO1.put("defaultPriority", temp.getInt("defaultPriority"));
        taskVO1.put("overrideDueDate", temp.get("overrideDueDate"));
        taskVO1.put("taskStatus", taskStatus);
        taskVO1.put("staffWorkedBy", temp.get("staffWorkedBy"));
        taskVO1.put("staffAssignedTo", temp.get("staffAssignedTo"));
        taskVO1.put("staffForwardBy", temp.get("staffForwardBy"));
        taskVO1.put("overridePerformedBy", temp.get("overridePerformedBy"));
        taskVO1.put("overridePerformedDate", temp.get("overridePerformedDate"));
        taskVO1.put("dueIn", temp.get("dueIn"));
        taskVO1.put("dueInDays", temp.getInt("dueInDays"));
        taskVO1.put("source", temp.getString("source"));
        taskVO1.put("taskInfo", null);
        taskVO1.put("taskNotes", null);
        taskVO1.put("actionTaken", temp.get("actionTaken"));
        taskVO1.put("editReason", temp.get("editReason"));
        taskVO1.put("holdReason", temp.get("holdReason"));
        taskVO1.put("cancelReason", temp.get("cancelReason"));
        taskVO1.put("taskDisposition", temp.get("taskDisposition"));
        taskVO1.put("createdBy", temp.getString("createdBy"));
        taskVO1.put("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        if (taskStatus.equalsIgnoreCase("Escalated")) {
            taskVO1.put("escalatedFlag", true);
        } else {
            taskVO1.put("escalatedFlag", temp.getBoolean("escalatedFlag"));
        }
        taskVO1.put("correlationId", temp.getString("correlationId"));
        taskVO1.put("uiid", temp.get("uiid"));
        taskVO1.put("action", temp.get("action"));
        taskVO1.put("actionedOn", temp.get("actionedOn"));
        taskVO1.put("taskDetails", new JSONArray());

        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(taskVO1.toString());
        gsonObject.addProperty("statusDate", curentDateTime);
        gsonObject.addProperty("updatedOn", curentDateTime);
        gsonObject.addProperty("createdOn", temp.getString("createdOn"));
        requestParams.set(gsonObject);
    }

    //Vidya-- Method to initiate incomplete task put api
    @And("I initiate Incomplete task put APi")
    public void initiateIncompleteTaskPutApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(incompleteTaskEndPoint);
    }

    @When("I will provide below information to create incomplete task using api")
    public void provideTInformationToCreateIncompleteTask(Map<String, String> data) {
        taskVO1.set(new HashMap<String, String>(data));
        JSONArray json = new JSONArray();

        Collection<String> values = data.values();
        allFieldsValue.set(new ArrayList<String>(values));

        for (int i = 1; i < allFieldsValue.get().size(); i++) {
            JSONObject temp = new JSONObject();
            temp.put("externalRefId", allFieldsValue.get().get(i++));
            temp.put("externalRefType", allFieldsValue.get().get(i));
            json.put(temp);
        }

        JSONObject temp = new JSONObject();
        temp.put("externalLinks", json);

        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(temp.toString());
        if (data.get("createdBy").equalsIgnoreCase("currentUser")) {
            gsonObject.addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        } else {
            gsonObject.addProperty("createdBy", data.get("createdBy"));
        }
        requestParams.set(gsonObject);
    }

    @Then("I run the Incomplete task put APi and verify the response")
    public void i_run_the_incomplete_task_api() throws Exception {
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("validationList").isJsonNull(), "validationList is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorResponse").isJsonNull(), "errorResponse is is not null");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("status").getAsString().
                equalsIgnoreCase("success"), "status is not success");
        this.taskId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskId").getAsInt());
        CRM_TaskManagementStepDef.taskId.set(this.taskId.get() + "");
        traceId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
    }

    @Then("I verify Task table information in response of incomplete contact record task")
    public void verifyTaskTableInformationInResponseInCompleteCRTask() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        assertEquals(temp.getInt("taskId"), taskId.get(), "TaskId is miss match");
        assertEquals(temp.getInt("taskTypeId"), 13014, "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertEquals(temp.getString("staffAssignedTo"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId,
                "staffAssignedTo is mismatching");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays"), 3, "dueInDays is miss match");
        assertEquals(temp.getString("source"), "System", "source is miss match");
        assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        assertTrue(temp.getString("taskNotes").equalsIgnoreCase
                        ("Contact Record marked Incomplete due to the user being logged out during Active Contact"),
                "taskNotes is mismatch");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
        assertTrue(temp.isNull("escalatedFlag"), "escalatedFlag is not null");
        assertEquals(temp.getString("correlationId"), traceId.get(), "correlationId is miss match");
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertTrue(temp.isNull("action"), "action is not null");
        assertTrue(temp.isNull("actionedOn"), "actionedOn is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I verify Task history table information in response of incomplete contact record task")
    public void verifyTaskHistoryTableInformationInResponseIncompleteTask() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskHistoryVOs").get(0).
                getAsJsonObject().toString());
        assertEquals(temp.getInt("taskId"), taskId.get(), "TaskId is miss match");
        assertTrue(temp.getInt("taskHistoryId") != 0 || !temp.isNull("taskHistoryId"),
                "taskHistoryId is null or equal 0");
        assertEquals(temp.getInt("taskTypeId"), 13014, "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority"), 3, "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        assertEquals(temp.getString("staffAssignedTo"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is mismatch");
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays"), 3, "dueInDays is miss match");
        assertEquals(temp.getString("source"), "System", "source is miss match");
        assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        assertTrue(temp.getString("taskNotes").equalsIgnoreCase
                        ("Contact Record marked Incomplete due to the user being logged out during Active Contact"),
                "taskNotes is mismatch");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
        assertEquals(temp.getString("correlationId"), traceId.get(), "correlationId is miss match");
        assertTrue(temp.isNull("uiid"), "uiid is not null");
        assertEquals(temp.getString("action"), "INSERT", "action is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("actionedOn")),
                "actionedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @Then("I verify Task Details and Task Details History table information in incomplete response")
    public void verifyInformationInTaskDetailsAndHistoryDBForIncompleteTask() {
        JsonArray json = taskInfoCall.get();
        JsonArray json1 = taskHisCall.get();

        assertEquals(json.size(), 1, "Task Details Array is empty");
        assertEquals(json1.size(), json.size(),
                "Task Details and Task Details History Table array size is mismatch");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp2 = new JSONObject(json1.get(i).toString());

            assertTrue(temp1.getInt("taskDetailsId") != 0 && !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp1.getInt("taskId"), taskId.get(), "TaskId is miss match");
            assertTrue(temp1.getInt("taskFieldId") != 0 && !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertEquals(temp1.getString("selectionFieldName"), "Disposition",
                    "selectionFieldName is value is mismatch");
            assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp1.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertEquals(temp1.getString("correlationId"), traceId.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp1.isNull("uiid"), "uiid is not null have some value");

            assertTrue(temp2.getInt("taskDetailHistoryId") != 0 && !temp2.isNull("taskDetailHistoryId"),
                    "taskDetailHistoryId is null or equal to 0");
            assertTrue(temp2.getInt("taskDetailsId") != 0 && !temp2.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal to 0");
            assertEquals(temp2.getInt("taskId"), taskId.get(), "TaskId is miss match");
            assertTrue(temp2.getInt("taskFieldId") != 0 && !temp2.isNull("taskFieldId"),
                    "taskFieldId is null or equal to 0");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertEquals(temp2.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp2.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp2.getString("selectionFieldName"), "Disposition",
                    "selectionFieldName is value is mismatch");
            assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar is not null");
            assertTrue(temp2.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null have some value");
            assertTrue(temp2.isNull("selectionNumeric"), "selectionNumeric is not null have some value");
            assertTrue(temp2.isNull("selectionDate"), "selectionDate is not null have some value");
            assertTrue(temp2.isNull("selectionDateTime"), "selectionDateTime is not null have some value");
            assertTrue(temp2.isNull("fieldGroup"), "fieldGroup is not null have some value");
            assertEquals(temp2.getString("correlationId"), traceId.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp2.isNull("uiid"), "uiid is not null have some value");
        }
    }

    @Then("I verify External Link table information in response for incomplete CR Task")
    public void verifyTInformationInExternalLinkTableForIncompleteTask() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonArray("taskLinksVOS");
        boolean taskToCase = false, taskToConsumer = false, taskToCR = false;

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            assertTrue(temp.getInt("taskLinkId") != 0 && !temp.isNull("taskLinkId"),
                    "taskLinkId is null or equal to 0");
            assertEquals(temp.getInt("internalRefId"), taskId.get(), "internalRefId is miss match");
            assertEquals(temp.getString("internalRefType"), "TASK", "internalRefType is miss match");
            if (temp.getString("externalLinkRefType").equals("CASE")) {
                taskToCase = true;
                assertEquals(temp.getString("externalLinkRefType"),
                        taskVO1.get().get("externalRefTypeCase"), "externalLinkRefType is miss match");
                assertEquals(temp.getInt("externalLinkRefId") + "",
                        taskVO1.get().get("externalRefIdCase"), "externalLinkRefId is miss match");
            } else if (temp.getString("externalLinkRefType").equals("CONSUMER")) {
                taskToConsumer = true;
                assertEquals(temp.getString("externalLinkRefType"),
                        taskVO1.get().get("externalRefTypeConsumer"), "externalLinkRefType is miss match");
                assertEquals(temp.getInt("externalLinkRefId") + "",
                        taskVO1.get().get("externalRefIdConsumer"), "externalLinkRefId is miss match");
            } else if (temp.getString("externalLinkRefType").equals("CONTACT_RECORD")) {
                taskToCR = true;
                assertEquals(temp.getString("externalLinkRefType"),
                        taskVO1.get().get("externalRefTypeCR"), "externalLinkRefType is miss match");
                assertEquals(temp.getInt("externalLinkRefId") + "",
                        taskVO1.get().get("externalRefIdCR"), "externalLinkRefId is miss match");
            }

            assertTrue(EventsUtilities.isValidDate(temp.getString("effectiveStartDate")),
                    "effectiveStartDate date time field IS NOT valid");
            assertTrue(temp.isNull("effectiveEndDate"), "effectiveEndDate is not null have some value");
            assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                    "createdOn date time field IS NOT valid");
            assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
            assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
            assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertEquals(temp.getString("correlationId"), traceId.get(),
                    "correlationId is null or has empty string");
            assertTrue(temp.isNull("uiid"), "uiid is not null have some value");
        }
        assertTrue(taskToCase && taskToConsumer && taskToCR, "Links are not created");
    }

    @And("I will get the all {string} task type which has {string} permission to create and edit for project {string}")
    public void getAllActiveTaskType(String status, String permission, String projectId) throws ParseException {
        initiateGetTaskTypeApi(projectId);
        runGetTaskTypeApi();

        allFieldsValue.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());

            if (status.equals("Active") && !temp.getBoolean("serviceRequestInd") &&
                    !temp.isNull("taskTypeTemplates") &&
                    ((!temp.isNull("startDate") && (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().dateComparision(
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.projectDate.get()), temp.getString("startDate"))))) &&
                    (temp.isNull("endDate") || API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().dateComparision(temp.getString("endDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.projectDate.get())))) {
                JSONArray arr = temp.getJSONArray("taskTypePermissions");
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject obj = new JSONObject(arr.get(j).toString());
                    if (obj.getString("permissionGroupType").equalsIgnoreCase("CREATE_TASK") &&
                            obj.getString("permissionGroupName").equalsIgnoreCase(permission)) {
                        allFieldsValue.get().add(temp.getString("taskTypeName").toLowerCase().trim());
                    }
                }
            } else if (status.equals("Future") && !temp.getBoolean("serviceRequestInd") &&
                    ((!temp.isNull("startDate") && !(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().dateComparision(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate
                            (CRM_GeneralTaskStepDef.projectDate.get()), temp.getString("startDate")))))) {
                JSONArray arr = temp.getJSONArray("taskTypePermissions");
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject obj = new JSONObject(arr.get(j).toString());
                    if (obj.getString("permissionGroupType").equalsIgnoreCase("CREATE_TASK") &&
                            obj.getString("permissionGroupName").equalsIgnoreCase(permission)) {
                        allFieldsValue.get().add(temp.getString("taskTypeName").toLowerCase().trim());
                    }
                }
            } else if (status.equals("Inactive") && !temp.getBoolean("serviceRequestInd") &&
                    ((temp.isNull("startDate") && temp.isNull("endDate")) ||
                            (!temp.isNull("endDate") &&
                                    !(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().dateComparision(temp.getString("endDate"),
                                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.projectDate.get())))))) {
                JSONArray arr = temp.getJSONArray("taskTypePermissions");
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject obj = new JSONObject(arr.get(j).toString());
                    if (obj.getString("permissionGroupType").equalsIgnoreCase("CREATE_TASK") &&
                            obj.getString("permissionGroupName").equalsIgnoreCase(permission)) {
                        allFieldsValue.get().add(temp.getString("taskTypeName").toLowerCase().trim());
                    }
                }
            }
        }
    }

    @And("I will get the all active service request which has {string} permission to create and edit for project {string}")
    public void getAllActiveServiceRequest(String permission, String projectId) {
        initiateGetTaskTypeApi(projectId);
        runGetTaskTypeApi();

        allFieldsValue.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getString("status").equalsIgnoreCase("Active") &&
                    temp.getBoolean("serviceRequestInd")) {

                JSONArray arr = temp.getJSONArray("taskTypePermissions");
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject obj = new JSONObject(arr.get(j).toString());
                    if (obj.getString("permissionGroupType").equalsIgnoreCase("CREATE_TASK") &&
                            obj.getString("permissionGroupName").equalsIgnoreCase(permission)) {
                        allFieldsValue.get().add(temp.getString("taskTypeName").toLowerCase().trim());
                    }
                }
            }
        }
    }

    @Then("I added task type id to the list")
    public void addTaskTypeIdToTheList() {
        taskTypeIds.get().add(Integer.parseInt(taskTypeId.get()));
    }

    @When("I provide taskTypeId for active business units and teams {string}")
    public void provideTaskTypeIDForActiveBUAndTeams(String taskTypeIdVal) {
        if (taskTypeIdVal.equalsIgnoreCase("singleVal"))
            requestParams.set((JsonObject) new JsonParser().parse(new JSONObject().
                    put("taskTypeId", new JSONArray().put(Integer.parseInt(taskTypeId.get()))).toString()));
        else if (taskTypeIdVal.equalsIgnoreCase("multipleVal"))
            requestParams.set((JsonObject) new JsonParser().parse(new JSONObject().
                    put("taskTypeId", taskTypeIds.get()).toString()));
    }

    @And("I can run active bu and teams api")
    public void runActiveBUandTeamsApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @Given("I initiated active business units and teams api")
    public void initiateActiveBusinessUnitsAndTeamsApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseTMURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(activeBusinessUnitsAndTeamsEndPoint);
    }

    @Then("I get business unit and team ids from business units and teams response api")
    public void getBUiDsFromBUandteamsResponceAPI() {
        businessUnitIDs.set(new ArrayList<>());
        teamIDs.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("commonResponse");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            businessUnitIDs.get().add(temp.getInt("id"));
        }

        JsonArray json1 = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("commonTeamResponse");
        for (int i = 0; i < json1.size(); i++) {
            JSONObject temp1 = new JSONObject(json1.get(i).toString());
            teamIDs.get().add(temp1.getInt("id"));
        }
    }

    @Then("I get business unit names from business units and teams response api")
    public void getBUNamesFromBUandteamsResponceAPI() {
        businessUnitNames.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("commonResponse");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            businessUnitNames.get().add(temp.getString("value"));
        }
        System.out.println("BU-> " + businessUnitNames.get());
    }

    @Given("I initiated Business Unit api by passing business unit ID {string}")
    public void runTheBusinessUnitGetAPIByPassingBUiD(String taskTypeIdValues) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskTemplateBaseURI);
        String endPoint;
        businessUnitIDs1.set(APITMListBusinessUnitController.businessUnitIDList);

        for (int i = 0; i < businessUnitIDs1.get().size(); i++) {
            boolean elementPresent = false;
            endPoint = "mars/tm/tasktype/link/refId/" + businessUnitIDs1.get().get(i) + "/refType/BUSINESS_UNIT";
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endPoint);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");

            JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");
            for (int j = 0; j < json.size(); j++) {
                JSONObject temp = new JSONObject(json.get(j).toString());

                if (taskTypeIdValues.equalsIgnoreCase("singleVal")) {
                    if (temp.getInt("taskTypeId") == Integer.parseInt(taskTypeId.get())) {
                        elementPresent = true;
                        break;
                    }
                } else if (taskTypeIdValues.equalsIgnoreCase("multipleVal")) {
                    for (Integer typeId : taskTypeIds.get()) {
                        if (temp.getInt("taskTypeId") == typeId) {
                            elementPresent = true;
                            break;
                        }
                    }
                }
            }
            if (!elementPresent) {
                System.out.println(taskTypeId.get() + " not available in response file. Hence removing from list");
                businessUnitIDs1.get().remove(i);
                i--;
            }
        }
    }

    @And("I verify active business unit ids are matches")
    public void verifyActiveBUiDs() {
        businessUnitIDs.get().sort(Collections.reverseOrder());
        businessUnitIDs1.get().sort(Collections.reverseOrder());
        assertTrue(businessUnitIDs.get().containsAll(businessUnitIDs1.get()), "Active BU ids are not matches");
    }

    @And("I verify active team ids are matches")
    public void verifyActiveTeamiDs() {
        teamIDs.get().sort(Collections.reverseOrder());
        APIListTeamController.teamIDList.get().sort(Collections.reverseOrder());
        if (!APIListTeamController.teamIDList.get().isEmpty()) {
            assertTrue(teamIDs.get().containsAll(APIListTeamController.teamIDList.get()), "Active team ids are not matches");
        } else {
            fail("No active teams exist under given condition");
        }
    }

    @Given("I initiated active users api")
    public void initiateActiveUsersApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseTMURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(activeUsers);
    }

    @When("I provide permission group id {string} for active users")
    public void providePermissionGroupIdForActiveUsers(String permissionGroupId) {
        requestParams.set((JsonObject) new JsonParser().parse(new JSONObject().
                put("permissionGroupId", new JSONArray().put(Integer.parseInt(permissionGroupId))).toString()));
        System.out.println(requestParams.get());
    }

    @And("I can run active users api")
    public void runActiveUsersApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @Then("I get active users from response api")
    public void getUsersFromResponceAPI() {
        activeUserValues.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("commonResponse");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            activeUserValues.get().add(temp.getString("value"));
        }
    }

    @And("I verify active user values are matches")
    public void verifyActiveUsers() {
        activeUserValues.get().sort(Collections.reverseOrder());
        ArrayList<String> expectedActiveUserValues = new ArrayList<String>(
                Arrays.asList("Amelia Hilbig", "Rayhan Imamuddin", "Dreyke Boone"));
        expectedActiveUserValues.sort(Collections.reverseOrder());
        assertEquals(expectedActiveUserValues, activeUserValues.get(), "Active Users are not matches");
    }

    @Then("I verify Task history table information in response")
    public void verifyTaskHistoryTableInformationInResponse() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskHistoryVOs").get(0).
                getAsJsonObject().toString());
        assertEquals(temp.getInt("taskId"), taskId.get(), "TaskId is miss match");
        assertTrue(temp.getInt("taskHistoryId") != 0 || !temp.isNull("taskHistoryId"),
                "taskHistoryId is null or equal 0");
        assertEquals(temp.getInt("taskTypeId") + "", taskTypeId.get(), "taskTypeId is miss match");
        assertTrue(temp.isNull("overridePriority"), "overridePriority is not null");
        assertEquals(temp.getInt("defaultPriority") + "", taskPriority.get(), "defaultPriority is miss match");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                "statusDate date time field IS NOT valid");
        assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
        if (assignee.get() == null) {
            assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
        } else {
            assertEquals(temp.getString("staffAssignedTo"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "staffAssignedTo is miss match");
        }
        assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
        assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
        assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
        assertTrue(temp.isNull("dueIn"), "dueIn is not null");
        assertEquals(temp.getInt("dueInDays") + "", dueInDays.get(), "dueInDays is miss match");
        assertEquals(temp.getString("source"), "User", "source is miss match");
        if (taskInfo.get() == null) {
            assertTrue(temp.isNull("taskInfo"), "taskInfo is not null");
        } else {
            assertEquals(temp.getString("taskInfo"), taskInfo.get(), "taskInfo is null");
        }
        assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
        assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
        assertTrue(temp.isNull("editReason"), "editReason is not null");
        assertTrue(temp.isNull("holdReason"), "holdReason is not null");
        assertTrue(temp.isNull("cancelReason"), "cancelReason is not null");
        assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is null");
        assertEquals(temp.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is null");
        assertTrue(!temp.getString("correlationId").isEmpty() && !temp.isNull("correlationId"), "correlationId is null or has empty string");
        assertEquals(temp.getString("uiid"), uiid.get(), "uiid is null have some value");
        assertEquals(temp.getString("action"), "INSERT", "action is not null");
        assertTrue(EventsUtilities.isValidDate(temp.getString("actionedOn")),
                "actionedOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                "updatedOn date time field IS NOT valid");
    }

    @And("I will get the task type ids for {string} role permission")
    public void getAllTaskTypeIdsForCsrRolePermission(String role) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskTemplateBaseURI);
        //650 & 651 is permissionId for Csr and Non Csr role in BLCRM it won't change since we are maintaining single project
        switch (role) {
            case "Csr":
                taskTypeIdsByPermissionId = taskTypeIdsByPermissionId + "650";
                break;
            case "Non Csr":
                taskTypeIdsByPermissionId = taskTypeIdsByPermissionId + "651";
                break;
            default:
                taskTypeIdsByPermissionId = taskTypeIdsByPermissionId + "650";
                break;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(taskTypeIdsByPermissionId);
        runGetTaskTypeApi();

        String str = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObjectMap.get("taskTypeIdList").toString();

        String[] taskTypeIds = str.replace("[", "").replace("]", "").
                replaceAll(" ", "").split(",");

        allFieldsValue.set(new ArrayList<>(Arrays.asList(taskTypeIds)));
    }

    @Given("I initiated get task list for work queue")
    public void getTaskLisForWorkQueue() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(workQueueTasks);
    }

    @When("I provide task type ids for {string} permission")
    public void provideTaskTypeIdsForCSRRolePermission(String role) {
        JsonParser parser = new JsonParser();
        JSONArray jsArray2 = new JSONArray(allFieldsValue.get());
        JSONObject obj = new JSONObject();

        obj.put("taskTypeIds", jsArray2);
        if (role.equalsIgnoreCase("Csr"))
            obj.put("isEscalatedEnable", true);
        else if (role.equalsIgnoreCase("Non Csr"))
            obj.put("isEscalatedEnable", false);
        else
            obj.put("isEscalatedEnable", true);

        requestParams.set((JsonObject) parser.parse(obj.toString()));
        System.out.println(requestParams.get().toString());

    }

    @When("I run work queue post api to get the list of task")
    public void runWorkQueuePostApiToGetTaskList() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        //assertTrue(api.responseString.contains("success"));
        //assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify all task records status in the response for {string}")
    public void verifyAllTaskRecordstatusInTheResponse(String role) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            temp = temp.getJSONObject("taskVO");

            if (role.equalsIgnoreCase("Csr")) {
                assertTrue(temp.getString("taskStatus").equalsIgnoreCase("Created") ||
                                temp.getString("taskStatus").equalsIgnoreCase("Escalated"),
                        "Task Status has wrong value");
                assertTrue(temp.getBoolean("escalatedFlag") || !temp.getBoolean("escalatedFlag"),
                        "Escalated Flag has wrong data");
                assertTrue(temp.isNull("taskNotes") || !temp.getString("taskNotes").isEmpty(),
                        "Task Notes has wrong data");
            } else if (role.equalsIgnoreCase("Non Csr")) {
                assertTrue(temp.getString("taskStatus").equalsIgnoreCase("Created"),
                        "Task Status has wrong value");
                assertFalse(temp.getBoolean("escalatedFlag"), "escalatedFlag is not null");
                assertTrue(temp.isNull("taskNotes"), "taskNotes is not null");
            }
            assertTrue(!temp.isNull("taskId") || temp.getInt("taskId") != 0,
                    "Task Id has wrong data");
            assertTrue(allFieldsValue.get().contains((temp.getInt("taskTypeId") + "")),
                    "Task Type Id not has permission");
            assertTrue(temp.isNull("overridePriority"));
            assertTrue(!temp.isNull("defaultPriority") || temp.getInt("defaultPriority") != 0);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")),
                    "defaultDueDate is miss match");
            assertTrue(temp.isNull("overrideDueDate"), "overrideDueDate is not null");
            assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")),
                    "statusDate date time field IS NOT valid");
            assertTrue(temp.isNull("staffWorkedBy"), "staffWorkedBy is not null");
            assertTrue(temp.isNull("staffAssignedTo"), "staffAssignedTo is not null");
            assertTrue(temp.isNull("staffForwardBy"), "staffForwardBy is not null");
            assertTrue(temp.isNull("overridePerformedBy"), "overridePerformedBy is not null");
            assertTrue(temp.isNull("overridePerformedDate"), "overridePerformedDate is not null");
            assertTrue(temp.isNull("dueIn"), "dueIn is not null");
            assertTrue(!temp.isNull("dueInDays") || temp.getInt("dueInDays") != 0,
                    "Due In Days has wrong data");
            assertTrue(temp.getString("source").equalsIgnoreCase("User") ||
                            temp.getString("source").equalsIgnoreCase("System"),
                    "Source has wrong data");
            assertTrue(temp.isNull("taskInfo") || !temp.getString("taskInfo").isEmpty(),
                    "Task Info is wrong");
            assertTrue(temp.isNull("actionTaken"), "actionTaken is not null");
            assertTrue(temp.isNull("editReason") || !temp.getString("editReason").isEmpty(),
                    "Edit reason has wrong data");
            assertTrue(temp.isNull("holdReason") || !temp.getString("holdReason").isEmpty(),
                    "Hold Reason has wrong data");
            assertTrue(temp.isNull("cancelReason") || !temp.getString("cancelReason").isEmpty(),
                    "Cancel Reason is wrong");
            assertTrue(temp.isNull("taskDisposition"), "taskDisposition is not null");
            assertTrue(temp.isNull("createdBy") || !temp.getString("createdBy").isEmpty(),
                    "Created By has wrong data");
            assertTrue(temp.isNull("updatedBy") || !temp.getString("updatedBy").isEmpty(),
                    "Updated By has wrong data");
            assertTrue(temp.isNull("createdOn") || !temp.getString("createdOn").isEmpty(),
                    "Created On has wrong data");
            assertTrue(temp.isNull("updatedOn") || !temp.getString("updatedOn").isEmpty(),
                    "Updated On has wrong data");
        }
    }

    @Then("I verify task details and task history details table records has selected action taken values")
    public void verifyTaskDetailsAndTaskHistoryDetailsTableRecordshasSelecedActionTakenValues() {
        JsonArray json = taskHisCall.get();
        JsonArray json1 = taskInfoCall.get();

        assertTrue(json.size() == json1.size(), "Task Details and Task Details History Table array size is mismatch");
        String[] str = CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").split(",");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp2 = new JSONObject(json1.get(i).toString());

            if (temp1.getString("selectionFieldName").equals("Disposition")) {
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar is not null");
            } else {
                assertEquals(temp1.getString("selectionVarchar").replaceAll("_", " "),
                        str[i].toUpperCase(), "selectionVarchar value is miss match");
                assertEquals(temp2.getString("selectionVarchar").replaceAll("_", " "),
                        str[i].toUpperCase(), "selectionVarchar value is miss match");
                assertEquals(temp1.getString("selectionFieldName"), "Action Taken",
                        "selectionFieldName value is miss match");
                assertEquals(temp2.getString("selectionFieldName"), "Action Taken",
                        "selectionFieldName value is miss match");
            }

            assertEquals(temp1.getInt("taskId"), Integer.parseInt(apiTaskId.get()),
                    "TaskId is miss match");
            assertTrue(temp1.getInt("taskDetailsId") != 0 || !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal 0");
            assertTrue(temp1.getInt("taskFieldId") != 0 || !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal 0");
            assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
            assertTrue(temp1.isNull("selectionDate"), "selectionVarchar is not null");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null");
            assertTrue(!temp1.getString("correlationId").isEmpty() ||
                    !temp1.isNull("correlationId"), "correlationId is null or empty string");

            assertEquals(temp2.getInt("taskId"), Integer.parseInt(apiTaskId.get()), "TaskId is miss match");
            assertTrue(temp2.getInt("taskDetailsId") != 0 || !temp2.isNull("taskDetailsId"), "taskDetailsId is null or equal 0");
            assertTrue(temp1.getInt("taskDetailHistoryId") != 0 || !temp1.isNull("taskDetailHistoryId"), "taskDetailHistoryId is null or equal 0");
            assertTrue(temp2.getInt("taskFieldId") != 0 || !temp2.isNull("taskFieldId"), "taskFieldId is null or equal 0");
            assertEquals(temp2.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp2.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null");
            assertTrue(temp2.isNull("selectionDate"), "selectionVarchar is not null");
            assertTrue(temp2.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp2.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp2.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp2.isNull("fieldGroup"), "fieldGroup is not null");
            assertTrue(!temp2.getString("correlationId").isEmpty() ||
                    !temp2.isNull("correlationId"), "correlationId is null or empty string");
        }
    }

    @And("I will get the all service request which are present in BLCRM")
    public void getAllServiceRequest() {
        initiateGetTaskTypeApi(projectId.get());
        runGetTaskTypeApi();

        allFieldsValue.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getBoolean("serviceRequestInd")) {
                allFieldsValue.get().add(temp.getString("taskTypeName").toLowerCase().trim());
            }
        }
    }

    @And("I will get the all active task type which are present in BLCRM")
    public void getAllTaskType() {
        initiateGetTaskTypeApi(projectId.get());
        runGetTaskTypeApi();

        allFieldsValue.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getString("status").equalsIgnoreCase("Active") &&
                    !temp.getBoolean("serviceRequestInd")) {
                allFieldsValue.get().add(temp.getString("taskTypeName").toLowerCase().trim());
            }
        }
    }

    @And("I will get the field order of the {string} template")
    public void iWillGetTheFieldOrderOfTheTemplate(String templateName) {
        fieldOrder.set(new HashMap<>());
        switch (templateName) {
            case "Plan/provider":
                getTemplateInfo = getTemplateInfo.replace("{templateId}", ConfigurationReader.getProperty("PlanProviderTemplateId"));
                break;
            case "General/CRM/EB":
                getTemplateInfo = getTemplateInfo.replace("{templateId}", ConfigurationReader.getProperty("GeneralCRMEBTemplateId"));
                break;
            case "Address":
                getTemplateInfo = getTemplateInfo.replace("{templateId}", ConfigurationReader.getProperty("AddressTemplateId"));
                break;
            case "Correspondence":
                getTemplateInfo = getTemplateInfo.replace("{templateId}", ConfigurationReader.getProperty("CorrespondenceTemplateId"));
                break;
            case "All fields1":
                getTemplateInfo = getTemplateInfo.replace("{templateId}", ConfigurationReader.getProperty("AllFields1TemplateId"));
                break;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskTemplateBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTemplateInfo);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("taskTemplateVO").
                getAsJsonArray("taskFieldVO");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (!temp.isNull("fieldOrder")) {
                fieldOrder.get().put(temp.getInt("fieldOrder"), temp.getString("taskFieldName"));
            }
        }
    }

    @Then("I verify task details information for task fields for coverVA")
    public void verifyTaskDetailsInformationForTaskFieldsForCoverVA() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("taskDetails");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            String field = temp.getString("selectionFieldName").toLowerCase();
            switch (field) {
                case "external consumer id":
                    assertEquals(temp.getInt("selectionNumeric") + "",
                            CRM_GeneralTaskStepDef.taskValues.get().get("externalConsumerID"),
                            "selectionNumeric value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "external case id":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId") == null ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId").equals("-- --") ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId").isEmpty()) {
                        assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    } else {
                        assertEquals(temp.getInt("selectionNumeric") + "",
                                CRM_GeneralTaskStepDef.taskValues.get().get("externalCaseId"),
                                "selectionNumeric value is miss match");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "preferred language":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("language").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "information type":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("informationType").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "action taken":
                    String actionTaken = "";
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").contains("(")) {
                        actionTaken = CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").replace("(", "").
                                replace(")", "");
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                                actionTaken.toUpperCase(), "selectionVarchar value is miss match");
                    } else {
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                                CRM_GeneralTaskStepDef.taskValues.get().get("actionTaken").toUpperCase(),
                                "selectionVarchar value is miss match");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date translation escalated":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationEscalated")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date translation received":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationReceived")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date translation mailed":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateTranslationMailed")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "disposition":
                    if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("DISPOSITION")) {
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                                CRM_GeneralTaskStepDef.taskValues.get().get("DISPOSITION").toUpperCase(), "selectionVarchar value is miss match");

                    } else if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("disposition")) {
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                                CRM_GeneralTaskStepDef.taskValues.get().get("disposition").toUpperCase(), "selectionVarchar value is miss match");
                    } else {
                        assertTrue(temp.isNull("selectionVarchar"),
                                "selectionVarchar is not null");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "external application id":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "connectionpoint contact record id":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("cpCRID") == null ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("cpCRID").equals("-- --") ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("cpCRID").isEmpty()) {
                        assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    } else {
                        assertEquals(temp.getInt("selectionNumeric") + "",
                                CRM_GeneralTaskStepDef.taskValues.get().get("cpCRID"),
                                "selectionNumeric value is miss match");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "connectionpoint sr id":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("cpSRID") == null ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("cpSRID").equals("-- --") ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("cpSRID").isEmpty()) {
                        assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    } else {
                        assertEquals(temp.getInt("selectionNumeric") + "",
                                CRM_GeneralTaskStepDef.taskValues.get().get("cpSRID"),
                                "selectionNumeric value is miss match");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "connectionpoint task id":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("cpTaskID") == null ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("cpTaskID").equals("-- --") ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("cpTaskID").isEmpty()) {
                        assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    } else {
                        assertEquals(temp.getInt("selectionNumeric") + "",
                                CRM_GeneralTaskStepDef.taskValues.get().get("cpTaskID"),
                                "selectionNumeric value is miss match");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "document due date":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("documentDueDate")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "contact reason":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "outcome":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " ").toUpperCase(),
                            CRM_GeneralTaskStepDef.taskValues.get().get("outcome").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "application type":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("applicationType").replace(" -", "")
                                    .toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "program":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("program").replace("- ", "")
                                    .toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "inbound correspondence type":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("InbDocType").replace("- ", "")
                                    .toUpperCase(), "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "remand reason":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("remandReason").toUpperCase()
                                    .replace("- ", ""),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "eligibility decision":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("eligibilityDecision").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "remand completion date":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("remandCompletionDate") == null ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("remandCompletionDate").equals("-- --") ||
                            CRM_GeneralTaskStepDef.taskValues.get().get("remandCompletionDate").isEmpty()) {
                        assertTrue(temp.isNull("selectionDate"), "remandCompletionDate is not null");
                    } else {
                        assertEquals(temp.getString("selectionDate"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("remandCompletionDate")), "selectionDate value is miss match");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "remand due date":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("remandDueDate")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "received date":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("receivedDate")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "locality":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("locality").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "request type":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("requestType").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "issue type":
                    if (CRM_GeneralTaskStepDef.taskValues.get().get("issueType").contains("CPU Error")) {
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " ")
                                , CRM_GeneralTaskStepDef.taskValues.get().get("issueType").replace(" (CPU Error)", "")
                                        .toUpperCase(), "selectionVarchar value is miss match");
                    } else {
                        assertEquals(temp.getString("selectionVarchar").replaceAll("_", " ")
                                , CRM_GeneralTaskStepDef.taskValues.get().get("issueType").toUpperCase(),
                                "selectionVarchar value is miss match");
                    }
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "case worker first name":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("caseWorkerFirstName"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "CASE_WORKER", "fieldGroup value is invalid");
                    break;

                case "case worker last name":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("caseWorkerLastName"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "CASE_WORKER", "fieldGroup value is invalid");
                    break;

                case "complaint type":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("complaintType").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "date resent":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("dateResent")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "returned mail reason":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("returnedMailReason").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "business unit":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("businessUnit").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "appointment date":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("appointmentDate")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "accessibility needed":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("accessibilityNeeded").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "appointment time":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("appointmentTime"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "provider first name":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("providerFirstName"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "PROVIDER", "fieldGroup value is invalid");
                    break;

                case "provider last name":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("providerLastName"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "PROVIDER", "fieldGroup value is invalid");
                    break;

                case "provider phone":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("providerPhone"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "provider email":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("providerEmail"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "facility name":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("facilityName").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "facility type":
                    assertEquals(temp.getString("selectionVarchar").replaceAll("_", " "),
                            CRM_GeneralTaskStepDef.taskValues.get().get("facilityType").toUpperCase(),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;
                case "returned mail received date":
                    assertEquals(temp.getString("selectionDate"),
                            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(CRM_GeneralTaskStepDef.taskValues.get().get("returnedMailReceivedDate")),
                            "selectionDate value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionVarchar"), "selectionVarchar is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "first name":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("ARFirstName"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "NAME", "fieldGroup value is invalid");
                    break;

                case "last name":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("ARLastName"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "NAME", "fieldGroup value is invalid");
                    break;

                case "phone":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("ARPhone"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "email":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("AREmail"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertTrue(temp.isNull("fieldGroup"), "fieldGroup is not null");
                    break;

                case "address line 1":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("ARAddressLine1"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "ADDRESS", "fieldGroup value is invalid");
                    break;

                case "address line 2":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("ARAddressLine2"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "ADDRESS", "fieldGroup value is invalid");
                    break;

                case "address city":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("ARCity"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "ADDRESS", "fieldGroup value is invalid");
                    break;

                case "address state":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("ARState"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "ADDRESS", "fieldGroup value is invalid");
                    ;
                    break;

                case "address zip code":
                    assertEquals(temp.getString("selectionVarchar"),
                            CRM_GeneralTaskStepDef.taskValues.get().get("ARZipCode"),
                            "selectionVarchar value is miss match");
                    assertTrue(temp.isNull("selectionBoolean"), "selectionBoolean is not null");
                    assertTrue(temp.isNull("selectionDate"), "selectionDate is not null");
                    assertTrue(temp.isNull("selectionDateTime"), "selectionDateTime is not null");
                    assertTrue(temp.isNull("selectionNumeric"), "selectionNumeric is not null");
                    assertTrue(temp.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
                    assertEquals(temp.getString("fieldGroup"), "ADDRESS", "fieldGroup value is invalid");
                    break;
            }

            assertEquals(temp.getInt("taskId") + "", CRM_TaskManagementStepDef.taskId.get(),
                    "TaskId is miss match");
            assertTrue(temp.getInt("taskDetailsId") != 0 || !temp.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal 0");
            assertTrue(temp.getInt("taskFieldId") != 0 || !temp.isNull("taskFieldId"),
                    "taskFieldId is null or equal 0");
            assertEquals(temp.getString("createdBy"), "3652", "createdBy is miss match");
            assertEquals(temp.getString("updatedBy"), "3652", "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(!temp.getString("correlationId").isEmpty() ||
                    !temp.isNull("correlationId"), "correlationId is null or empty string");
        }
    }

    @Then("I verify edit reason information for SR in DB")
    public void verifyEditReasonDetailsForSRInDB() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("editReasons");
        JSONObject temp = new JSONObject(json.get(0).toString());
        assertEquals(temp.getString("editReason"), CRM_GeneralTaskStepDef.taskValues.get().get("reasonForEdit").
                toUpperCase().replace(" ", "_"), "Reason for edit value mismatch");
        assertEquals(temp.getInt("taskId") + "", CRM_TaskManagementStepDef.srID.get(), "SR ID mismatch");
        assertTrue(temp.getInt("taskHistoryId") != 0 || !temp.isNull("taskHistoryId"),
                "taskHistoryId is null or equal 0");
        assertTrue(temp.getInt("editReasonId") != 0 || !temp.isNull("editReasonId"),
                "editReasonId is null or equal 0");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "CreatedOn date time field IS NOT valid");
        assertEquals(temp.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
    }

    @And("I verify task is not created for Future or Inactive task types")
    public void i_run_the_create_external_task_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode + "", "200");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("errorResponse").get("errorCode").getAsString(),
                "The requested task type is not active. No task was created.",
                "Task is created for Inactive or future task type");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("status").getAsString(), "400 BAD_REQUEST",
                "400 Bad request ststus message is not getting");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskId").isJsonNull(), "Task Id is getting");
    }


    @And("I create request body with below field params and assign it to created user {string}")
    public void provideRequestBodyForCreateTaskApi(String assignIt, DataTable fieldNamesAndValues) {
        if (assignIt.equals("true") || assignIt.equals("false")) {
            boolean staffAssignedTo = Boolean.parseBoolean(assignIt);
            Gson gson = new Gson();
            String body = createRequestBodyForSpecificTask(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, convertFieldNamesToVariableAndMapWithValues(fieldNamesAndValues), staffAssignedTo).toString();
            System.out.println(body);
            JsonElement element = gson.fromJson(body, JsonElement.class);
            requestParams.set(element.getAsJsonObject());
        } else {
            throw new IllegalArgumentException("\"assignIt\" value can only be \"true\" or \"false\"");
        }

    }

    @Given("I initiated get task by {string} for task history")
    public void initiateGetTaskByVal(String taskId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        if (taskId.equals("getFromUi")) {
            apiTaskId.set(CRM_TaskManagementStepDef.taskId.get());
        } else if (taskId.equals("getSRIDFromUi")) {
            apiTaskId.set(CRM_TaskManagementStepDef.srID.get());
        } else if (!taskId.isEmpty()) {
            apiTaskId.set(taskId);
        } else {
            apiTaskId.set(this.taskId.get() + "");
        }
        getTaskHisByTaskId = getTaskHisByTaskId.replace("{taskId}", apiTaskId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskHisByTaskId);
    }

    @Then("I store task history response")
    public void iStoreTaskHistoryResponse() {
        taskHisCall.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskHistoryVOs").get(0).
                getAsJsonObject().getAsJsonArray("taskDetails"));
    }

    @Then("I store task details response")
    public void iStoreTaskDetailsResponse() {
        taskInfoCall.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("taskDetails"));
    }

    @Then("I verify SR details and SR history details table records has selected action taken values")
    public void verifySrDetailsAndSrHistoryDetailsTableRecordshasSelecedActionTakenValues() {
        JsonArray json = taskHisCall.get();
        JsonArray json1 = taskInfoCall.get();

        assertTrue(json.size() == json1.size(), "SR Details and SR Details History Table array size is mismatch");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            JSONObject temp2 = new JSONObject(json1.get(i).toString());

            if (temp1.getString("selectionFieldName").equals("Disposition")) {
                assertTrue(temp1.isNull("selectionVarchar"), "selectionVarchar is not null");
                assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar is not null");
            } else {
                assertEquals(temp1.getString("selectionFieldName"), "Action Taken",
                        "selectionFieldName value is miss match");
                assertEquals(temp2.getString("selectionFieldName"), "Action Taken",
                        "selectionFieldName value is miss match");
            }

            assertEquals(temp1.getInt("taskId"), Integer.parseInt(apiTaskId.get()),
                    "TaskId is miss match");
            assertTrue(temp1.getInt("taskDetailsId") != 0 || !temp1.isNull("taskDetailsId"),
                    "taskDetailsId is null or equal 0");
            assertTrue(temp1.getInt("taskFieldId") != 0 || !temp1.isNull("taskFieldId"),
                    "taskFieldId is null or equal 0");
            assertEquals(temp1.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp1.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp1.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp1.isNull("selectionBoolean"), "selectionBoolean is not null");
            assertTrue(temp1.isNull("selectionDate"), "selectionVarchar is not null");
            assertTrue(temp1.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp1.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp1.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp1.isNull("fieldGroup"), "fieldGroup is not null");
            assertTrue(!temp1.getString("correlationId").isEmpty() ||
                    !temp1.isNull("correlationId"), "correlationId is null or empty string");

            assertEquals(temp2.getInt("taskId"), Integer.parseInt(apiTaskId.get()), "TaskId is miss match");
            assertTrue(temp2.getInt("taskDetailsId") != 0 || !temp2.isNull("taskDetailsId"), "taskDetailsId is null or equal 0");
            assertTrue(temp2.getInt("taskFieldId") != 0 || !temp2.isNull("taskFieldId"), "taskFieldId is null or equal 0");
            assertEquals(temp2.getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "createdBy is miss match");
            assertEquals(temp2.getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "updatedBy is miss match");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("createdOn")),
                    "CreatedOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp2.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(temp2.isNull("selectionBoolean"), "selectionBoolean is not null");
            assertTrue(temp2.isNull("selectionDate"), "selectionVarchar is not null");
            assertTrue(temp2.isNull("selectionDateTime"), "selectionDateTime is not null");
            assertTrue(temp2.isNull("selectionNumeric"), "selectionNumeric is not null");
            assertTrue(temp2.isNull("selectionVarchar2"), "selectionVarchar2 is not null");
            assertTrue(temp2.isNull("fieldGroup"), "fieldGroup is not null");
            assertTrue(!temp2.getString("correlationId").isEmpty() ||
                    !temp2.isNull("correlationId"), "correlationId is null or empty string");
        }
    }

    @And("I will get the all service request for project {string}")
    public void getAllActiveServiceRequest(String projectId) {
        initiateGetTaskTypeApi(projectId);
        runGetTaskTypeApi();

        allFieldsValue.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getBoolean("serviceRequestInd")) {
                allFieldsValue.get().add(temp.getString("taskTypeName").toLowerCase().trim());
            }
        }
    }

    @And("I verify Task status and Action Taken Values")
    public void iVerifyTaskStatusAndActionTakenValues(Map<String, String> expectedValues) {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        assertEquals(temp.getInt("taskId"), Integer.parseInt(apiTaskId.get()), "TaskId is miss match");
        assertEquals(temp.getString("taskStatus"), expectedValues.get("Status"), "taskStatus is miss match");
        JsonArray json = taskInfoCall.get();
        boolean actionTakenValue = false, dispositionValue = false;
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp2 = new JSONObject(json.get(i).toString());
            if (temp2.getString("selectionFieldName").equals("Action Taken")) {
                assertEquals(temp2.getString("selectionFieldName"), "Action Taken",
                        "selectionFieldName value is miss match");
                assertEquals(temp2.getString("selectionVarchar"), expectedValues.get("Action Taken"),
                        "selectionVarchar value is miss match");
                actionTakenValue = true;
                assertTrue(actionTakenValue, "Action Taken Value isn't verified");
            } else
                assertFalse(actionTakenValue, "Action Taken Field isn't Displaying");
            if (temp2.getString("selectionFieldName").equals("Disposition")) {
                if (temp2.get("selectionVarchar").equals(null)) {
                    assertTrue(temp2.isNull("selectionVarchar"), "selectionVarchar value is miss match");
                } else {
                    assertEquals(temp2.getString("selectionFieldName"), "Disposition",
                            "selectionFieldName value is miss match");
                    assertEquals(temp2.getString("selectionVarchar"), expectedValues.get("Disposition"),
                            "selectionVarchar value is miss match");
                }
                dispositionValue = true;
                assertTrue(dispositionValue, "Disposition Value isn't verified");
            } else
                assertFalse(dispositionValue, "Disposition Field isn't displaying");
        }
    }

    @Then("I verify Member Matching task details based on created source {string}")
    public void i_verify_Member_Matching_task_details_based_on_created_source(String sourceType) {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        JsonArray jsonLinks = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonArray("taskLinksVOS");
        assertEquals(temp.getInt("taskId"), Integer.parseInt(apiTaskId.get()), "TaskId is miss match");
        assertEquals(temp.getString("taskStatus"), "Created", "taskStatus is miss match");
        assertEquals(temp.getInt("defaultPriority"), 2, "Priority is mismatched");
        assertEquals(temp.getInt("dueInDays"), 2, "Due in days is mismatched");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("defaultDueDate")), "defaultDueDate is miss match");
        assertTrue(EventsUtilities.isValidDate(temp.getString("statusDate")), "statusDate date time field IS NOT valid");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "statusDate date time field IS NOT valid");
        assertTrue(temp.getString("source").equals("System"), "Source is mismatched");
        JsonArray json = taskInfoCall.get();

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp2 = new JSONObject(json.get(i).toString());
            if (temp2.getString("selectionFieldName").equals("Action Taken")) {
                assertEquals(temp2.getString("selectionFieldName"), "Action Taken",
                        "selectionFieldName value is miss match");
                assertTrue(temp2.isNull("selectionVarchar"),
                        "selectionVarchar is not null");
            }
            if (sourceType.equals("API") && temp2.getString("selectionFieldName").equals("Inbound Correspondence Type")) {
                assertEquals(temp2.getString("selectionFieldName"), "Inbound Correspondence Type",
                        "selectionFieldName value is miss match");
                assertTrue(temp2.isNull("selectionVarchar"),
                        "selectionVarchar is not null");
            }
            if (sourceType.equals("ECMS") && temp2.getString("selectionFieldName").equals("Inbound Correspondence Type")) {
                assertEquals(temp2.getString("selectionFieldName"), "Inbound Correspondence Type",
                        "selectionFieldName value is miss match");
                assertTrue(temp2.getString("selectionVarchar").equals("maersk Application"),
                        "selectionVarchar is not null");
            }
            if (sourceType.equals("API") && temp2.getString("selectionFieldName").equals("Channel")) {
                assertEquals(temp2.getString("selectionFieldName"), "Channel",
                        "selectionFieldName value is miss match");
                assertTrue(temp2.getString("selectionVarchar").equals("Web"),
                        "selectionVarchar is not null");
            }
            if (sourceType.equals("ECMS") && temp2.getString("selectionFieldName").equals("Channel")) {
                assertEquals(temp2.getString("selectionFieldName"), "Channel",
                        "selectionFieldName value is miss match");
                assertTrue(temp2.getString("selectionVarchar").equalsIgnoreCase("Email"),
                        "selectionVarchar is not null");
            }
            if (sourceType.equals("API")) {
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("internalRefType").getAsString().equals("TASK"));
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("externalLinkRefType").getAsString().equals("APPLICATION"));
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("taskLinkId").getAsString().chars().allMatch(Character::isDigit));
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("internalRefId").getAsString().equals(apiTaskId.get()));
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("externalLinkRefId").getAsString().equals(applicationController.applicationIdAPI.get()));
            }
            if (sourceType.equals("ECMS")) {
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("internalRefType").getAsString().equals("TASK"));
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("externalLinkRefType").getAsString().equals("APPLICATION"));
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("taskLinkId").getAsString().chars().allMatch(Character::isDigit));
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("internalRefId").getAsString().equals(apiTaskId.get()));
                assertTrue(jsonLinks.get(0).getAsJsonObject().get("externalLinkRefId").getAsString().equals(sendEventAndCreateLinksController.inbCreatedAppId));

                assertTrue(jsonLinks.get(1).getAsJsonObject().get("internalRefType").getAsString().equals("TASK"));
                assertTrue(jsonLinks.get(1).getAsJsonObject().get("externalLinkRefType").getAsString().equals("INBOUND_CORRESPONDENCE"));
                assertTrue(jsonLinks.get(1).getAsJsonObject().get("taskLinkId").getAsString().chars().allMatch(Character::isDigit));
                assertTrue(jsonLinks.get(1).getAsJsonObject().get("internalRefId").getAsString().equals(apiTaskId.get()));
                assertTrue(jsonLinks.get(1).getAsJsonObject().get("externalLinkRefId").getAsString().equals(sendEventAndCreateLinksController.documentId));
            }

        }
    }

    @Then("I verify request object passes csr name and userId for the SR")
    public void IverifyrequestobjectpassestheusersuserIdfortheSR() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("taskDetails");
        List<String> selecField = new ArrayList<>();
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp1 = new JSONObject(json.get(i).toString());
            selecField.add(temp1.getString("selectionFieldName"));
            if (temp1.getString("selectionFieldName").equals("CSR Name")) {
                assertEquals(temp1.getString("selectionVarchar"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
            }
        }
        assertTrue(selecField.contains("CSR Name"));
    }


    @Then("I verify response contains {string} for the SR")
    public void verifyTaskDetailsField(String selectionFieldName, List<String> selectVarChar) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").getAsJsonArray("taskDetails");
        List<String> taskDetails = new ArrayList<>();
        for (int i = 0; i < json.size(); i++) {
            JSONObject taskDetail = new JSONObject(json.get(i).toString());
            if (taskDetail.getString("selectionFieldName").equalsIgnoreCase(selectionFieldName)) {
                taskDetails.add(taskDetail.getString("selectionVarchar"));
            }
        }
        sa.get().assertThat(taskDetails).containsAll(selectVarChar);
        sa.get().assertThat(taskDetails).hasSize(selectVarChar.size());
    }

    @Given("I initiated IVR get task by task id {string}")
    public void initiateIVRGetTaskByTaskId(String taskId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        if (taskId.equals("getFromUi")) {
            apiTaskId.set(CRM_TaskManagementStepDef.taskId.get());
        } else if (!taskId.isEmpty()) {
            apiTaskId.set(taskId);
        } else {
            apiTaskId.set(this.taskId.get() + "");
        }
        getIVRTaskByTaskId = getIVRTaskByTaskId.replace("{taskId}", apiTaskId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getIVRTaskByTaskId);
    }

    @When("I run IVR get task by task id API")
    public void getIVRTaskByTaskId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify response get from IVR get task api")
    public void verifyIVRGetTaskAPIResponse() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().toString());
        assertTrue(temp.getInt("ivrRecordId") != 0 || !temp.isNull("ivrRecordId"), "ivrRecordId is null or equal 0");
        assertEquals(temp.getInt("taskId"), this.taskId.get(), "TaskId is miss match");
        assertEquals(temp.getString("method"), "PUT", "method is mismatch");
        assertEquals(temp.getString("endpointInvoked"), externalTask, "endpointInvoked is mismatch");
        assertEquals(temp.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName(), "projectName is mismatch");
        assertEquals(temp.getString("createdBy"), "7422", "createdBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")), "createdOn date time field IS NOT valid");

        JSONObject temp1 = (new JSONObject(temp.get("requestPayload").toString()));
        JSONObject temp2 = (new JSONObject(temp.get("responsePayload").toString()));

        assertEquals(temp1.getInt("taskTypeId") + "", taskTypeId.get(), "taskTypeId is miss match");
        assertEquals(temp1.getString("taskInfo"), this.taskInfo.get(), "taskInfo is mismatch");
        assertEquals(temp1.getString("createdBy"), "7422", "createdBy is mismatch");
        assertEquals(temp1.getString("correlationId"), traceId1.get(), "correlationId is mismatch");
        assertTrue(temp1.isNull("taskTriggerDate"), "taskTriggerDate is not null");
        assertTrue(temp1.getJSONArray("taskDetails").length() == 0, "taskDetails is mismatch");
        assertTrue(temp1.getJSONArray("externalLinks").length() == 0, "taskDetails is mismatch");

        assertEquals(temp2.getInt("taskId"), this.taskId.get(), "taskId is miss match");
        assertEquals(temp2.getString("status"), "success", "status is mismatch");
        assertTrue(temp2.isNull("validationList"), "validationList is not null");
        assertTrue(temp2.isNull("errorResponse"), "errorResponse is not null");
    }

    @When("I will provide required information to create external task with {string} {string}")
    public void iWillProvideInfoToCreateExternalTaskAPI(String taskInfo, String triggerDate, List<String> additionalFlds) {
        allFieldsValue.set(new ArrayList<>(additionalFlds));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/externalCreateTaskEE.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTypeId", Integer.parseInt(taskTypeId.get()));
        if (taskInfo.equalsIgnoreCase("Validate IVR"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", "7422");
        else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiUserInfo());

        traceId1.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().traceId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", traceId1.get());

        this.taskInfo.set(null);
        if (!taskInfo.isEmpty()) {
            this.taskInfo.set(taskInfo);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskInfo", taskInfo);
        }
        if (!triggerDate.isEmpty()) {
            String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("taskTriggerDate", curentDateTime);
            if (allFieldsValue.get().size() > 1) {
                for (int i = 0; i < allFieldsValue.get().size(); i++) {
                    JsonObject temp = new JsonObject();
                    temp.add("selectionNumeric", null);
                    temp.add("selectionBoolean", null);
                    temp.add("selectionDate", null);
                    temp.add("selectionDateTime", null);
                    temp.add("fieldGroup", null);
                    temp.addProperty("selectionFieldName", allFieldsValue.get().get(i));
                    i++;
                    temp.addProperty("selectionVarchar", allFieldsValue.get().get(i));
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskDetails").add(temp);
                }
            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get().toString());
    }

    @When("I initiated Task Update DueDate API")
    public void IinitiatedTaskUpdateDueDateAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateDueDate);
    }

    @Then("I run API call for Update DueDate with data")
    public void iRunAPICallForUpdateDueDateWithData(Map<String, String> field) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("events");
        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));
        CRM_GeneralTaskStepDef.taskValues.get().put("taskID", String.valueOf(payloadObject.get().getJSONObject("dataObject").getInt("taskId")));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/dueDateCalculation.json");
        Map<String, String> reasonData = new HashMap<>();
        reasonData.put("tasks[0].taskId", String.valueOf(payloadObject.get().getJSONObject("dataObject").getInt("taskId")));
        reasonData.putAll(field);

        String json1 = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json1, reasonData));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Update Due Date API is FAIL >>");

    }

    @Then("I get the business unit data from TM")
    public void iGetDataForBUFromTM() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessUnits");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            businessUnitData.get().put(temp.get("businessUnitId").toString(), temp.get("businessUnitName").toString());
        }
    }

    @Then("I get business unit and team ids from business units response api")
    public void getBUiDsFromBUResponceAPI() {
        businessUnitIDs.set(new ArrayList<>());
        teamIDs.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("commonResponse");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            businessUnitIDs.get().add(temp.getInt("id"));
        }
    }

    @And("User send Api call with payload {string} to get BU for {string}")
    public void apiCallwithBusinessUnit(String payload, String taskTypeId) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/" + payload + ".json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("taskTypeId").add(taskTypeId);
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(json);
        businessUnit.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("commonResponse"));
        for (int i = 0; i < businessUnit.get().size(); i++) {
            businessUnitList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("commonResponse[" + i + "].id"));
        }
        System.out.println("map-> " + businessUnitList.get());
    }

    @And("User send Api call with payload to get Task for given BU {string}")
    public void apiCallwithTaskForGivenBU(String taskTypeId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskTemplateBaseURI);
        String endPoint;
        for (int i = 0; i < businessUnitList.get().size(); i++) {
            boolean elementPresent = false;
            endPoint = "mars/tm/tasktype/link/refId/" + businessUnitList.get().get(i) + "/refType/BUSINESS_UNIT";
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endPoint);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");
            taskTypeTemp.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskTypeList"));
            for (int j = 0; j < taskTypeTemp.get().size(); j++) {
                taskTypeIdList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("taskTypeList[" + j + "].taskTypeId"));
            }
            assertTrue(taskTypeIdList.get().contains(Integer.parseInt(taskTypeId)));
        }


    }

    @And("I verify Task status and Action Taken Values for ATS created tasks")
    public void iVerifyTaskStatusAndActionTakenValuesforATSCreatedTasks(Map<String, String> expectedValues) {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).
                getAsJsonObject().getAsJsonObject("taskVO").toString());
        assertEquals(temp.getInt("taskId"), Integer.parseInt(apiTaskId.get()), "TaskId is miss match");
        assertEquals(temp.getString("taskStatus"), expectedValues.get("Status"), "taskStatus is miss match");
        JsonArray json = taskInfoCall.get();

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp2 = new JSONObject(json.get(i).toString());
            if (temp2.getString("selectionFieldName").equals("Action Taken")) {
                System.out.println("Selection field name:" + temp2.getString("selectionFieldName"));
                assertEquals(temp2.getString("selectionFieldName"), "Action Taken",
                        "selectionFieldName value is miss match");
                System.out.println("Selection var char:" + temp2.getString("selectionVarchar"));
                assertEquals(temp2.getString("selectionVarchar"), expectedValues.get("Action Taken"),
                        "selectionVarchar value is miss match");
            }
        }
    }

    public String getUserIdByUserNameAndProjectName() {
        String userId = "";
        if (LoginStepDef.projectName1.get().equalsIgnoreCase("CoverVA")) {
            userId = "3652";
        } else if (LoginStepDef.projectName1.get().equalsIgnoreCase("NJ-SBE")
                && LoginStepDef.loginUserName.get().equalsIgnoreCase("SVC_mars_user_2")) {
            userId = "3348";
        } else if (LoginStepDef.projectName1.get().equalsIgnoreCase("IN-EB")
                && LoginStepDef.loginUserName.get().equalsIgnoreCase("SVC_mars_tester_2")) {
            userId = "3778";
        } else if (LoginStepDef.projectName1.get().equalsIgnoreCase("BLCRM")
                && LoginStepDef.loginUserName.get().equalsIgnoreCase("SVC_mars_user_1")) {
            userId = "8369";
        } else userId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId;
        return userId;
    }
}