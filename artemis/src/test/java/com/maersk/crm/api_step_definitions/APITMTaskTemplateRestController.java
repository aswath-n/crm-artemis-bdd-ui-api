package com.maersk.crm.api_step_definitions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.ConfigurationReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;


public class APITMTaskTemplateRestController extends CRMUtilities implements ApiBase {

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<Integer> projectId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<String> apitaskTypeId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apitaskTemplateId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> taskTypeResponseString = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> taskTemplateResponseString = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Long> recordStartTime = new ThreadLocal<>();
    private final ThreadLocal<String> apiconsumerCorrelationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiTemplateCorrelationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiconsumerUiid = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiTemplateUiid = ThreadLocal.withInitial(String::new);
    private String baseURI = ConfigurationReader.getProperty("apiTaskTemplateURI");
    private String createTaskTypeEndPoint = "/mars/tm/tasktype/save";
    private String taskTypeListEndPoint = "/mars/tm/tasktype/project/{projectId}";
    private String TaskTemplateListEndPoint = "/mars/tm/tasktemplate/project/{projectId}";
    private String createTaskTemplateEndPOint = "mars/tm/tasktemplate/save";
    private String tmBaseAPIurl = ConfigurationReader.getProperty("apiTMurl");
    private String getPageTemplateFieldGridEndpoint = "mars/tm/v2/templateDetails";
    private String createPageEndpoint = "/mars/tm/v2/page";
    private String getPagesEndpoint = "/mars/tm/v2/pages";
    private String getPageByPageIdEndpoint = "/mars/tm/v2/page";

    private String createTemplateEndpoint = "mars/tm/v2/template";
    private String getTemplatesEndpoint = "mars/tm/v2/templates";
    private String getTemplateByTemplateIdEndpoint = "/mars/tm/v2/template";

    private String createFieldEndpoint = "mars/tm/v2/field";
    private String getFieldsEndpoint = "mars/tm/v2/fields";
    private String getFieldByFieldIdEndpoint = "mars/tm/v2/field";

    private String createGridEndpoint = "mars/tm/v2/grid";
    private String getGridsEndpoint = "mars/tm/v2/grids";
    private String getGridByGridIdEndpoint = "mars/tm/v2/grids";

    private String createGridColumnEndpoint = "mars/tm/v2/grid/column";
    private String getColumnByColumnId = "mars/tm/v2/grid/column";

    APITMProjectPermissionRestController projectPermissions = new APITMProjectPermissionRestController();
    public final ThreadLocal<String> permissionGroupId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> permissionGroupName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> taskTypeName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> taskTypeDesc = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> taskTemplateId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> templateDescription = ThreadLocal.withInitial(String::new);
    final ThreadLocal<HashMap<String, Object>> actualTaskTypeMap = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<HashMap<String, Object>> apiResponseTaskTypeMap = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<ArrayList> listOfTemplateIds = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> templateIdsUsed = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<Integer> maxValueOfTaskTypeId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<Integer> taskTypeId = ThreadLocal.withInitial(() -> 0);
    public final ThreadLocal<String> pageKey = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> templateKey = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> pageId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> templateId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> fieldKey = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> fieldId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> gridKey = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> gridId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> columnKey = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> columnId = ThreadLocal.withInitial(String::new);


    /**
     * Initiate Create Task Type API
     */
    @Given("I initiated Create Task Type API")
    public void i_initiated_create_task_type_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createTaskTypeEndPoint);
    }

    /**
     * Initiate List Task Template API
     */
    @Given("I initiate Template List API")
    public void i_initiated_template_list_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(TaskTemplateListEndPoint);
    }

    /**
     * Provide Task Type creation information entry from test input
     *
     * @param dataTable
     */
    @When("I can provide details with following information:")
    public void i_can_provide_information_to_create_task_type(Map<String, String> dataTable) {
        recordStartTime.set(System.currentTimeMillis());
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        System.out.println(apiconsumerCorrelationId.get());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        System.out.println(apiconsumerUiid);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TaskTemplate/apiCreateTaskType.json");
        JsonObject taskTypeObj = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().get("taskType").getAsJsonObject();
        JsonArray permissionsArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().get("permissions").getAsJsonArray();
        JsonArray templatesArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().get("templates").getAsJsonArray();
        taskTypeObj.addProperty("createdOn", recordStartTime.get());
        taskTypeObj.addProperty("correlationId", apiconsumerCorrelationId.get());
        taskTypeObj.addProperty("uiid", apiconsumerUiid.get());
        taskTypeObj.addProperty("taskTypeName", "Task " + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(3));

        for (int i = 0; i < permissionsArray.size(); i++) {
            permissionsArray.get(i).getAsJsonObject().addProperty("startDate", recordStartTime.get());
            permissionsArray.get(i).getAsJsonObject().addProperty("rolePermissionId", permissionGroupId.get());
            permissionsArray.get(i).getAsJsonObject().addProperty("permissionGroupName", permissionGroupName.get());
            permissionsArray.get(i).getAsJsonObject().addProperty("endDate", recordStartTime.get());
            apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
            System.out.println(apiconsumerCorrelationId.get());
            apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
            System.out.println(apiconsumerUiid.get());
            permissionsArray.get(i).getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
            permissionsArray.get(i).getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        }

        for (int i = 0; i < templatesArray.size(); i++) {
            templatesArray.get(i).getAsJsonObject().addProperty("startDate", recordStartTime.get());
            templatesArray.get(i).getAsJsonObject().addProperty("taskTemplateId", taskTemplateId.get());
            apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
            System.out.println(apiconsumerCorrelationId);
            apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
            System.out.println(apiconsumerUiid.get());
            templatesArray.get(i).getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
            templatesArray.get(i).getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        }

        for (String data : dataTable.keySet()) {
            String key = data;
            String value = dataTable.get(data);
            if (value.equalsIgnoreCase("{random}")) {
                value = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10).randomString;
            }
            if (key.equalsIgnoreCase("taskTypeName")) {
                taskTypeName.set(value);
            } else if (key.equalsIgnoreCase("taskTypeDesc")) {
                taskTypeDesc.set(value);
            }
            switch (data) {
                case "projectId":
                    if (value.isEmpty()) {
                        value = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
                    }
                    taskTypeObj.addProperty(key, value);
                    break;
                case "serviceRequestInd":
                    taskTypeObj.addProperty(key, Boolean.parseBoolean(value));
                    break;
                case "srCategory":
                    if (value.equalsIgnoreCase("random")) {
                        String[] vals = {"Inquiry", "Appeal", "Complaint", "Outreach", "Research/Analysis"};
                        Random r = new Random();
                        int randomNumber = r.nextInt(vals.length);
                        value = vals[randomNumber];
                    }
                    taskTypeObj.addProperty(key, value);
                    break;
                case "taskTypeId":
                    if (value.isEmpty()) {
                        taskTypeId.set(maxValueOfTaskTypeId.get().intValue() + 1);
                    } else if (value.equalsIgnoreCase("{existingId}")) {
                        taskTypeId.set(maxValueOfTaskTypeId.get());
                    } else {
                        taskTypeId.set(maxValueOfTaskTypeId.get());
                    }
                    taskTypeObj.addProperty("taskTypeId", taskTypeId.get());
                    break;
                default:
                    taskTypeObj.addProperty(key, value);
            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    /**
     * Post Task Type Creation via API
     */
    @And("I can run create task type API")
    public void i_can_run_create_task_type_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(taskTypeResponseString.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        taskTypeResponseString.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTypeVO").toString());
        apitaskTypeId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTypeVO").getAsJsonObject().get("taskTypeId").toString());
        taskTypeName.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTypeVO").getAsJsonObject().get("taskTypeName").toString());
    }

    public JsonObject run_all_task_type_list_by_project_id(String project_id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(taskTypeListEndPoint.replace("{projectId}", project_id));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    public JsonObject run_all_task_template_list_by_project_id(String project_id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(TaskTemplateListEndPoint.replace("{projectId}", project_id));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    /**
     * Initiate Create Task Template API
     */
    @Given("I initiated Create Task Template API")
    public void i_initiated_create_task_template_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createTaskTemplateEndPOint);
    }

    /**
     * Provide Task Template creation information entry from test input
     *
     * @param dataTable
     */
    @When("I can provide details with following information to create task template:")
    public void i_can_provide_information_to_create_task_template(Map<String, String> dataTable) {
        recordStartTime.set(System.currentTimeMillis());
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        System.out.println(apiconsumerCorrelationId.get());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        System.out.println(apiconsumerUiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TaskTemplate/apiCreateTaskTemplate.json");
        JsonObject taskTemplateObj = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject();
        taskTemplateObj.addProperty("correlationId", apiconsumerCorrelationId.get());
        taskTemplateObj.addProperty("uiid", apiconsumerUiid.get());
        JsonArray taskFieldNamesArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().get("taskFieldVO").getAsJsonArray();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5);
        String name_supffix = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber.toString();
        for (String data : dataTable.keySet()) {
            switch (data) {
                default:
                    if (data.contains("taskFieldVO")) {
                        if (!dataTable.get(data).isEmpty()) {
                            JsonArray jArray = new JsonArray();
                            String all_values_string = dataTable.get(data);
                            String[] items = all_values_string.split(",");
                            for (String item : items) {
                                jArray.add(item.replace("\"", ""));
                            }
                            taskTemplateObj.add("taskFieldVO", jArray);
                        }
                    } else {
                        if (data.contains("templateName")) {
                            if (!dataTable.get(data).isEmpty()) {
                                String templateName = dataTable.get(data) + name_supffix;
                                System.out.println(templateName);
                                taskTemplateObj.addProperty("templateName", templateName);
                            }
                        } else if (data.contains("templateDescription")) {
                            if (!dataTable.get(data).isEmpty()) {
                                templateDescription.set(dataTable.get(data) + " " + name_supffix + " Desc");
                                taskTemplateObj.addProperty(data, templateDescription.get());
                            }
                        } else if (data.contains("projectId")) {
                            if (dataTable.get(data).isEmpty()) {
                                projectId.set(Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId()));
                            }
                            taskTemplateObj.addProperty(data, projectId.get());
                        } else {
                            taskTemplateObj.addProperty(data, dataTable.get(data));
                        }
                    }
            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @Then("I can search the Task Type to validate based on projectId {string} and taskTypeId {string}")
    public void i_can_verify_task_type_api_response_will_be_success(String project_id, String taskTypeId) {
        if (taskTypeId.isEmpty()) {
            taskTypeId = apitaskTypeId.get();
        }
        if (project_id.isEmpty()) {
            project_id = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        JsonObject responseObj = run_all_task_type_list_by_project_id(project_id);
        JsonArray taskTypeListObj = responseObj.getAsJsonArray("taskTypeList");
        boolean foundTaskType;
        foundTaskType = false;
        JsonElement taskTypeElem;
        taskTypeElem = null;
        for (JsonElement task : taskTypeListObj)
            if ((task.getAsJsonObject().get("taskTypeId").toString()).equals(taskTypeId)) {
                taskTypeElem = task;
                foundTaskType = true;
                break;
            }
        assertTrue(foundTaskType, "Record for Task Type by TaskTypeId '" + taskTypeId + "' is not found.");
        System.out.println(taskTypeElem.toString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            //Convert JSON To Map
            actualTaskTypeMap.set((HashMap<String, Object>) mapper.readValue(taskTypeElem.toString(), new TypeReference<Map<String, Object>>() {
            }));
            //Print JSON output
            System.out.println(actualTaskTypeMap.get());
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //Convert JSON To Map
            apiResponseTaskTypeMap.set((HashMap<String, Object>) mapper.readValue(taskTypeResponseString.get(), new TypeReference<Map<String, Object>>() {
            }));
            //Print JSON output
            System.out.println(apiResponseTaskTypeMap.get());
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //assertTrue(apitdu.compareHashMaps(actualTaskTypeMap, apiResponseTaskTypeMap));
        assertEquals(apiResponseTaskTypeMap.get().get("taskTypeId").toString().toLowerCase(), actualTaskTypeMap.get().get("taskTypeId").toString().toLowerCase());
        assertEquals(apiResponseTaskTypeMap.get().get("projectId").toString().toLowerCase(), actualTaskTypeMap.get().get("projectId").toString().toLowerCase());
        assertEquals(apiResponseTaskTypeMap.get().get("taskTypeName").toString().toLowerCase(), actualTaskTypeMap.get().get("taskTypeName").toString().toLowerCase());
        assertEquals(apiResponseTaskTypeMap.get().get("taskTypeDesc").toString().toLowerCase(), actualTaskTypeMap.get().get("taskTypeDesc").toString().toLowerCase());
        assertEquals(apiResponseTaskTypeMap.get().get("priority").toString().toLowerCase(), actualTaskTypeMap.get().get("priority").toString().toLowerCase());
    }

    /**
     * Provide Task Template creation information entry from test input
     */
    @When("I provide template details to task type:")
    public void i_provide_template_details_to_task_type() {
        long startTime2 = recordStartTime.get() + TimeUnit.DAYS.toMillis(4);
        apiTemplateUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        System.out.println(apiTemplateUiid.get());
        apiTemplateCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        System.out.println(apiTemplateCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TaskTemplate/apiCreateTaskType.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().get("taskType").getAsJsonObject().addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        JsonArray templateArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().get("templates").getAsJsonArray();

        templateArray.remove(0);

        int maxNumOFTemplateIds = listOfTemplateIds.get().size();
        Random r = new Random();
        if (maxNumOFTemplateIds > 5)
            maxNumOFTemplateIds = 4;
        int randomNumber = 1 + r.nextInt(maxNumOFTemplateIds);
        for (int i = 0; i < randomNumber; i++) {
            JsonObject templateObj = new JsonObject();
            templateObj.addProperty("uiid", apiTemplateUiid.get());
            templateObj.addProperty("correlationId", apiTemplateCorrelationId.get());
            templateObj.addProperty("createdOn", recordStartTime.get());
            templateObj.addProperty("startDate", recordStartTime.get());
            templateObj.addProperty("taskTemplateId", listOfTemplateIds.get().get(i).toString());
            templateIdsUsed.get().add(listOfTemplateIds.get().get(i).toString());
            templateArray.add(templateObj);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @Then("I verify associated task type is displayed")
    public void verify_associated_task_type() {
        JsonObject responseObj = run_all_task_template_list_by_project_id(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        JsonArray taskTemplateListObj = responseObj.getAsJsonArray("taskTemplateVOList");
        JsonArray taskTypeListObj;
        boolean foundTaskTemplate, foundTaskType;
        foundTaskType = false;
        foundTaskTemplate = false;
        JsonElement taskTypeElem;
        taskTypeElem = null;
        JsonElement taskTempElem;
        taskTempElem = null;

        for (String id : templateIdsUsed.get()) {
            for (JsonElement template : taskTemplateListObj) {
                if ((template.getAsJsonObject().get("taskTemplateId").toString()).equals(id)) {
                    taskTempElem = template;
                    foundTaskTemplate = true;
                    break;
                }
                taskTypeListObj = template.getAsJsonObject().getAsJsonArray("taskTypeVOList");
                for (JsonElement task : taskTypeListObj) {
                    if ((task.getAsJsonObject().get("taskTypeName").toString()).equals(taskTypeName)) {
                        taskTypeElem = template;
                        foundTaskType = true;
                        break;
                    }
                }
            }
        }
        assertTrue(foundTaskType, "Associated task type is not found");
    }


    @Given("I get project permission API by Project ID {string} page {string} size {string} and sort {string}")
    public void i_initiated_get_project_permission_api(String projectID, String page, String size, String sort) {
        if (projectID.isEmpty()) {
            projectID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        projectPermissions.i_initiated_get_project_permission_api(projectID);
        projectPermissions.i_run_get_project_permission_api_with_query(page, size, sort);
        projectPermissions.getPermissionGroupIdAndGroupName();
        permissionGroupId.set(projectPermissions.permissionGroupId.get());
        permissionGroupName.set(projectPermissions.permissionGroupName.get());
    }

    @And("I can provide the following group permission types")
    public void providePermissionGroupType(List<String> permissionGroupTypes) {
        recordStartTime.set(System.currentTimeMillis());
        long endDateTime = recordStartTime.get() + TimeUnit.MINUTES.toMillis(200);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().getAsJsonArray("permissions").remove(0);
        for (String permissionType : permissionGroupTypes) {
            projectPermissions.getPermissionGroupIdAndGroupName();
            permissionGroupId.set(projectPermissions.permissionGroupId.get());
            permissionGroupName.set(projectPermissions.permissionGroupName.get());
            apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
            apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
            JsonObject permission = new JsonObject();
            permission.addProperty("startDate", recordStartTime.get());
            permission.addProperty("rolePermissionId", permissionGroupId.get());
            permission.addProperty("permissionGroupType", permissionType);
            permission.addProperty("permissionGroupName", permissionGroupName.get());
            permission.addProperty("correlationId", apiconsumerCorrelationId.get());
            permission.addProperty("uiid", apiconsumerUiid.get());
            permission.addProperty("endDate", endDateTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("payload").getAsJsonObject().getAsJsonArray("permissions").add(permission);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @Then("I verify created task type details")
    public void verifyTaskDetails() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTypeVO").getAsJsonObject().get("createdOn").toString() != null);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTypeVO").getAsJsonObject().get("createdBy").toString() != null);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTypeVO").getAsJsonObject().get("taskTypeName").toString(), taskTypeName);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTypeVO").getAsJsonObject().get("taskTypeDesc").toString().replaceAll("\"", ""), taskTypeDesc);
    }

    /**
     * Post Task Template Creation via API
     */
    @And("I can run create task template API")
    public void i_can_run_create_task_template_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        taskTemplateResponseString.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTemplateVO").toString());
        // System.out.println(taskTemplateResponseString);
        apitaskTemplateId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTemplateVO").getAsJsonObject().get("taskTemplateId").toString());
        // System.out.println(apitaskTemplateId);
    }

    @Given("I initiated Create Page API")
    public void i_initiated_create_page_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createPageEndpoint);
    }

    @Given("I initiated Create template API")
    public void i_initiated_create_template_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createTemplateEndpoint);
    }

    @Given("I initiated Create Field API")
    public void i_initiated_create_field_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createFieldEndpoint);
    }

    @Given("I initiated Create Grid API")
    public void i_initiated_create_grid_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createGridEndpoint);
    }

    @Given("I initiated Create Grid column API")
    public void i_initiated_create_grid_column_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createGridColumnEndpoint);
    }

    @Given("I initiated get Page,Template,Field,Grid details API")
    public void i_initiated_Page_Template_Field_Grid_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getPageTemplateFieldGridEndpoint);
    }

    @When("I initiated get list of pages API in project ID {string}")
    public void i_initiated_get_list_of_pages_in_project_id_something(String projectid) throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getPagesEndpoint + "/" + projectid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @When("I initiated get list of templates API in page ID {string}")
    public void i_initiated_get_list_of_templates_api_in_page_id_something(String pageid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTemplatesEndpoint + "/" + pageid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @When("I initiated get list of fields API in project")
    public void i_initiated_get_list_of_fields_API_in_project() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getFieldsEndpoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @When("I initiated get list of grids API in project")
    public void i_initiated_get_list_of_grids_API_in_project() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getGridsEndpoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @Then("I verify the pages got created and can be fetched")
    public void i_verify_the_page_has_got_created_and_can_be_fetched() throws Throwable {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        List<String> pages = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.key");
        assertTrue(pages.contains(pageKey), "Get Pages API not feched the page newly created");
        System.out.println(pages);
    }

    @Then("I verify the fields got created and can be fetched")
    public void i_verify_the_fields_has_got_created_and_can_be_fetched() throws Throwable {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        List<Integer> fields = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.fieldId");
        int fId = Integer.parseInt(fieldId.get());
        assertTrue(fields.contains(fId), "Get fields API not feched the fields newly created");
    }

    @Then("I verify the grids got created and can be fetched")
    public void i_verify_the_grids_has_got_created_and_can_be_fetched() throws Throwable {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        List<Integer> grids = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.griId");
        int gId = Integer.parseInt(gridId.get());
        assertTrue(grids.contains(gId), "Get grids API not feched the grids newly created");
    }

    @Then("I verify the templates got created and can be fetched")
    public void i_verify_the_templates_has_got_created_and_can_be_fetched() throws Throwable {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        List<Integer> temps = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.templateId");
        int tId = Integer.parseInt(templateId.get());
        assertTrue(temps.contains(tId), "Get templates API not feched the template newly created");
    }

    @And("User send Api call with payload {string} to get page, template, field, grid details")
    public void apiCallwithPayloadtogetDetails(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TMenhancement/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, temp));
    }

    @And("User send Api call with payload {string} to create page")
    public void apiCallwithPayloadtoCreatePage(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TMenhancement/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);
        if (temp.get("name").equals("random"))
            temp.put("name", "Page" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        pageKey.set("Pkey" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        if (temp.get("key").equals("random"))
            temp.put("key", pageKey.get());
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, temp));
    }

    @And("User send Api call with payload {string} to create template")
    public void apiCallwithPayloadtoCreateTemplate(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TMenhancement/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);
        if (temp.get("name").equals("random"))
            temp.put("name", "Template" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        templateKey.set("Tkey" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        if (temp.get("key").equals("random"))
            temp.put("key", templateKey.get());
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, temp));
    }

    @And("User send Api call with payload {string} to create field")
    public void apiCallwithPayloadtoCreateField(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TMenhancement/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);
        if (temp.get("name").equals("random"))
            temp.put("name", "Field" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        fieldKey.set("Fkey" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        if (temp.get("key").equals("random"))
            temp.put("key", fieldKey.get());
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, temp));
    }

    @And("User send Api call with payload {string} to create grid")
    public void apiCallwithPayloadtoCreateGrid(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TMenhancement/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);
        if (temp.get("name").equals("random"))
            temp.put("name", "Grid" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        gridKey.set("Gkey" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        if (temp.get("key").equals("random"))
            temp.put("key", gridKey.get());
        temp.put("templateId", templateId.get());
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, temp));
    }

    @And("User send Api call with payload {string} to create grid column")
    public void apiCallwithPayloadtoCreateGridColumn(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TMenhancement/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);
        if (temp.get("name").equals("random"))
            temp.put("name", "Col" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        columnKey.set("Ckey" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(5).randomString);

        if (temp.get("key").equals("random"))
            temp.put("key", columnKey.get());
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, temp));
    }

    @Then("I verify the response status code success for getting details for page,template,field,grid")
    public void i_verify_the_response_status_code_with_details_page_template_field_grid() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify the response status as {string} and permissions for template")
    public void iVerifyTheResponseStatusAsAndPermissionsForTemplate(String status) {
        status = status.toLowerCase();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), status);
        if (status.equals("Success")) {
            ArrayList<Object> permission = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.permissions");
            assertFalse(permission.isEmpty(), "FAIL! Response does not contain permissions");
        }
    }

    @Then("I verify the response status as {string} for template in project {string}")
    public void iVerifyTheResponseStatusAsForTemplate(String status, String pName) {
        status = status.toLowerCase();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), status);
        if (status.equalsIgnoreCase("fail")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errorResponse.errorMessage"), "Project ID not found/invalid for project name = " + pName);
        }
    }

    @Then("I verify the response status code success with page creation status")
    public void i_verify_the_response_status_code_with_page_creation_status() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        pageId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.pageId"));
        System.out.println("PAGEID : " + pageId.get());
    }

    @Then("I verify the response status code success with permission configuration status")
    public void i_verify_the_response_status_code_with_permission_status() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify the response status code success with template creation status")
    public void i_verify_the_response_status_code_with_template_creation_status() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        templateId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.templateId"));
        System.out.println("TEMPLATEID : " + templateId.get());

    }

    @Then("I verify the response status code success with field creation status")
    public void i_verify_the_response_status_code_with_field_creation_status() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        fieldId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.fieldId"));
        System.out.println("FIELD ID : " + fieldId.get());
    }

    @Then("I verify the response status code success with grid creation status")
    public void i_verify_the_response_status_code_with_grid_creation_status() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        gridId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.gridId"));
        System.out.println("GRID ID : " + gridId.get());
    }

    @Then("I verify the response status code success with grid column creation status")
    public void i_verify_the_response_status_code_with_grid_column_creation_status() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        columnId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.gridColumnId"));
        System.out.println("GRID COLUMN ID : " + columnId.get());
    }

    @When("I initiated get page by pageid")
    public void i_initiated_get_page_by_pageid() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getPageByPageIdEndpoint + "/" + pageId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @When("I initiated get template by templateId")
    public void i_initiated_get_template_by_templateid() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTemplateByTemplateIdEndpoint + "/" + templateId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @When("I initiated get field by fieldId")
    public void i_initiated_get_field_by_fieldid() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getFieldByFieldIdEndpoint + "/" + fieldId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @When("I initiated get grid by gridId")
    public void i_initiated_get_grid_by_gridId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getGridByGridIdEndpoint + "/" + gridId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @When("I initiated get column details by columnId")
    public void i_initiated_get_column_by_columnId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(tmBaseAPIurl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getColumnByColumnId + "/" + columnId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @And("I verify page details are getting fetched")
    public void i_verify_page_details_are_getting_fetched() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(pageId, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.pageId").toString());
    }

    @And("I verify template details are getting fetched")
    public void i_verify_template_details_are_getting_fetched() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(templateId, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.templateId").toString());
    }

    @And("I verify field details are getting fetched")
    public void i_verify_field_details_are_getting_fetched() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(fieldId, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.fieldId").toString());
    }

    @And("I verify grid details are getting fetched")
    public void i_verify_grid_details_are_getting_fetched() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(gridId, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.gridId").toString());
    }

    @And("I verify column details are getting fetched")
    public void i_verify_column_details_are_getting_fetched() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(columnId, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.gridColumnId").toString());
    }

    @Then("I can verify the newly created Task Template based on projectId {string} and taskTemplateId {string}")
    public void i_can_verify_the_newly_created_task_template_api(String project_id, String taskTemplateId) {
        if (taskTemplateId.isEmpty()) {
            taskTemplateId = apitaskTemplateId.get();
        }
        if (project_id.isEmpty()) {
            project_id = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        JsonElement taskTemplate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTemplateVO");
        System.out.println(taskTemplate.toString());
        assertTrue(taskTemplate.getAsJsonObject().get("templateName").toString() != null);
        assertTrue(taskTemplate.getAsJsonObject().get("versionId").getAsInt() > 0);
        assertTrue(taskTemplate.getAsJsonObject().get("taskTemplateId").toString() != null);
        assertTrue(taskTemplate.getAsJsonObject().get("templateDescription").toString() != null);
        assertTrue(taskTemplate.getAsJsonObject().get("createdOn").toString() != null);
        assertTrue(taskTemplate.getAsJsonObject().get("createdBy").toString() != null);
        assertTrue(taskTemplate.getAsJsonObject().get("correlationId").toString() != null);
        assertTrue(taskTemplate.getAsJsonObject().get("uiid").toString() != null);
        assertTrue(taskTemplate.getAsJsonObject().get("projectId").toString().contains(project_id));
    }

    @Given("I get task template id from project {string}")
    public void getskTypeTemplate(String projectId) {
        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        JsonObject responseObj = run_all_task_template_list_by_project_id(projectId);
        //taskTemplateId = ((ArrayList)((ArrayList)((ArrayList)((ArrayList)api.jsonPathEvaluator.get("taskTemplateVOList.taskTypeVOList.taskTypeTemplates.taskTemplateId")).get(0))).get(0)).get(0).toString();
        //  listOfTemplateIds = ((ArrayList)api.jsonPathEvaluator.get("taskTemplateVOList.taskTemplates.taskTemplateId"));
        //  taskTemplateId = ((ArrayList)api.jsonPathEvaluator.get("taskTemplateVOList.taskTemplates.taskTemplateId")).get(0).toString();
        JsonArray taskTemplateId = responseObj.getAsJsonArray("taskTemplateId");
        System.out.println(taskTemplateId);
    }

    @Then("I verify task type id by searching based on projectId {string} and taskTypeId {string}")
    public void i_can_verify_task_type_idInSearchByProject(String project_id, String taskTypeId) {
        if (taskTypeId.isEmpty()) {
            taskTypeId = apitaskTypeId.get();
        }
        if (project_id.isEmpty()) {
            project_id = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        JsonObject responseObj = run_all_task_type_list_by_project_id(project_id);
        JsonArray taskTypeListObj = responseObj.getAsJsonArray("taskTypeList");
        boolean foundTaskType;
        foundTaskType = false;
        JsonElement taskTypeElem;
        taskTypeElem = null;
        for (JsonElement task : taskTypeListObj)
            if ((task.getAsJsonObject().get("taskTypeId").toString()).equals(taskTypeId)) {
                taskTypeElem = task;
                foundTaskType = true;
                break;
            }
        assertTrue(foundTaskType, "Record for Task Type by TaskTypeId '" + taskTypeId + "' is not found.");

    }

    @Then("I verify Service request details in task type")
    public void i_can_verify_sr_task_type_idInSearchByProject() {
        assertEquals(apiResponseTaskTypeMap.get().get("serviceRequestInd").toString().toLowerCase(), actualTaskTypeMap.get().get("serviceRequestInd").toString().toLowerCase());
        assertEquals(apiResponseTaskTypeMap.get().get("srCategory").toString().toLowerCase(), actualTaskTypeMap.get().get("srCategory").toString().toLowerCase());
    }

    @Given("I initiated search task type by project API for {string}")
    public void initiateSearchTaskTypeByProjectAppi(String project_id) {
        if (project_id.isEmpty()) {
            project_id = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(taskTypeListEndPoint.replace("{projectId}", project_id));
    }

    @When("I run search task type by project API")
    public void runSearchTaskTypeByProjectApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        List<Integer> taskTypeIds = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("taskTypeList.taskTypeId");
        Collections.sort(taskTypeIds);
        Collections.reverse(taskTypeIds);
        maxValueOfTaskTypeId.set(taskTypeIds.get(0));
        System.out.println(maxValueOfTaskTypeId);
    }

    @Then("I verify task is created wth given task type id")
    public void verifyTaskTypeId() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("taskTypeVO").getAsJsonObject().get("taskTypeId").toString(), taskTypeId + "");
    }


    @Then("I verify error message for duplicate task type id")
    public void verifyDuplicateTaskTypeId() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errorResponse.errorCode").toString(), "Duplicate Task type ID " + taskTypeId + " found");
    }

    @And("I can run create task type API for invalid values")
    public void runCreateTaskTypeAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(taskTypeResponseString.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @And("User send Api call with payload {string} to configure permission for {string}")
    public void apiCallwithPayloadtoconfigurepermission(String payload, String ObjectType, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/TMenhancement/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).
                getAsJsonObject().addProperty("pageId", pageId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("read", data.get("read"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("write", data.get("write"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("noPermission", data.get("noPermission"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("applyMask", data.get("applyMask"));


        switch (ObjectType) {
            case "page":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).
                        getAsJsonObject().addProperty("key", pageKey.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectId", pageId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectType", "pagev2");

                break;
            case "template":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).
                        getAsJsonObject().addProperty("key", templateKey.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectId", templateId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectType", "template");
                break;
            case "field":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).
                        getAsJsonObject().addProperty("key", fieldKey.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectId", fieldId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectType", "fieldv2");
                break;
            case "grid":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).
                        getAsJsonObject().addProperty("key", gridKey.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectId", gridId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectType", "gridv2");
                break;
            case "gridColumn":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).
                        getAsJsonObject().addProperty("key", columnKey.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectId", columnId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("pageV2Payload").getAsJsonArray().get(0).getAsJsonObject().
                        get("permissions").getAsJsonArray().get(0).getAsJsonObject().addProperty("objectType", "columnv2");
                break;

        }

        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(json);
    }

}
