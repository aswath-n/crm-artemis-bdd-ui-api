package com.maersk.crm.api_step_definitions;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import com.google.gson.JsonElement;
import com.maersk.crm.step_definitions.TM_EditProjectInformationStepDef;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.ConfigurationReader;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;

import static com.maersk.crm.api_step_definitions.APICRMCoreDynamicPayloadUtil.getUserActivityPayload;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;


public class APITMProjectRestController extends CRMUtilities implements ApiBase {
    //public APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private final ThreadLocal<String> user_id = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String baseURIRefresh = ConfigurationReader.getProperty("apiRefreshRoleList");
    private String createProjectEndPoint = "mars/tm/project";
    private String searchProjectsEndPoint = "mars/tm/projects";
    private String searchProjectUserEndPoint = "mars/tm/project/users";
    private String projectUserActivity = "mars/tm/project/useractivity";
    private String getTenantRecordByIdEndPoint = "mars/tm/project/{projectId}";
    private String getStaffRecordEndPoint = "mars/tm/project/user/{staffId}/{projectId}";
    private String getProjectApproverByIdEndPoint = "mars/tm/project/approver/{projectId}";
    private String getProjectRefreshEndPoint = "mars/cp/project/refresh/{projectId}";
    private String getProjectDisplaynameEndPoint = "mars/tm/project/user/displayname";

    public ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    public ThreadLocal<JSONObject> payloadObject = ThreadLocal.withInitial(JSONObject::new);
    public ThreadLocal<Integer> projectStatusId = ThreadLocal.withInitial(() -> 0);
    public ThreadLocal<Integer> project_Id = ThreadLocal.withInitial(() -> 0);
    private String baseAPIUrl = ConfigurationReader.getProperty("apiEventsURI");
    private String projectRecordEventsEndPoint = "/app/crm/events?size=100000000&page=0&sort=eventId,desc";
    public ThreadLocal<String> dataObject = ThreadLocal.withInitial(String::new);
    ThreadLocal<String> projTimeZone = ThreadLocal.withInitial(String::new);

    private final ThreadLocal<String> provisioningString = ThreadLocal.withInitial(() -> "Active,Inactive,Pending");

    public static String userIdFromApigee;
    private String apiUserID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getEncodedAPIMaxId();

    private String apiPassword = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getEncodedAPIPassword();
    public String projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
    private String baseTMURI = ConfigurationReader.getProperty("apiTMURI");
    private String getApigee = "mars/security/login/apigee";
    APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    String userID = "";
    //private APITMEventController apitmEventController = new APITMEventController();


    @Given("I initiated Create Project API")
    public void i_initiated_create_project_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createProjectEndPoint);
    }

    @Given("I initiated Search Project API")
    public void i_initiated_project_search_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchProjectsEndPoint);
    }

    @Given("I initiated Get Project List API")
    public void i_initiated_project_list_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchProjectsEndPoint);
    }

    @Given("I initiated project user search")
    public void i_initiated_project_user_search_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchProjectUserEndPoint);
    }

    @Given("I initiated get Staff Record API By Staff ID {string} and Project ID {string}")
    public void i_initiated_project_search_api_by_id(String staffID, String projectID) {
        if (projectID != null && !projectID.isEmpty()) {
            projectId = projectID;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getStaffRecordEndPoint = getStaffRecordEndPoint.replace("{staffId}", staffID);
        getStaffRecordEndPoint = getStaffRecordEndPoint.replace("{projectId}", projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getStaffRecordEndPoint);
    }

    private String get_user_id(String user_name) {
        user_id.set("");
        i_initiated_project_user_search_api();
        i_can_define_user_search_on_a_project_using_api();
        i_can_post_user_search_on_a_project_using_api();
        String staffId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("staffList").getAsJsonObject().getAsJsonArray("content").get(0).getAsJsonObject().get("staffId").toString();
        System.out.println("Staff ID: " + staffId);
        i_initiated_project_search_api_by_id(staffId, projectId);
        i_run_staff_recrod_search_under_a_project_api();
        user_id.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("staff").getAsJsonObject().get("user").getAsJsonObject().get("userId").toString());
        System.out.println("User ID: " + user_id);
        return user_id.get();
    }

    @Given("I initiated project user activity")
    public void i_initiated_project_user_activity_api() {
        String user_name = ConfigurationReader.getProperty("login");
        user_id.set(get_user_id(user_name));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(projectUserActivity);
    }

    @Given("I initiate userActivity end point for REST api call")
    public void i_initiate_user_activity_end_point_for_rest_api_call() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(projectUserActivity);
    }

    @Given("I initiated Search Project API By Project ID {string}")
    public void i_initiated_project_search_api_by_id(String projectID) {
        if (projectID != null && !projectID.isEmpty()) {
            projectId = projectID;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getTenantRecordByIdEndPoint = getTenantRecordByIdEndPoint.replace("{projectId}", projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTenantRecordByIdEndPoint);
    }

    @Given("I initiated project approver API by project Id {string}")
    public void i_initiated_project_approver_api_by_id(String projectID) {
        if (projectID != null && !projectID.isEmpty()) {
            projectId = projectID;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getProjectApproverByIdEndPoint = getProjectApproverByIdEndPoint.replace("{projectId}", projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getProjectApproverByIdEndPoint);
    }

    @When("I initiated project approver API by project name {string}")
    public void i_initiated_project_approver_api_by_name(String projectName) {
        JsonObject project_detail = get_project_detail_by_project_name(projectName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getProjectApproverByIdEndPoint = getProjectApproverByIdEndPoint.replace("{projectId}", projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getProjectApproverByIdEndPoint);
    }

    @Given("I initiated refresh API for {string}")
    public void i_initiated_refresh_api_for_something(String projectname) throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURIRefresh);
        getProjectRefreshEndPoint = getProjectRefreshEndPoint.replace("{projectId}", projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getProjectRefreshEndPoint);
    }

    @Given("I initiated displayname API for {string}")
    public void i_initiated_displayname_api_for_something(String projectname) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getProjectDisplaynameEndPoint);
    }

    @When("I get the userID from {string} and {string}")
    public void i_get_the_userid_from_and(String firstname, String lastname)  {
       userID=apiClassUtil.getApiUserIdbyFirstNameAndLastname(firstname,lastname);
    }

    @And("User send Api call with payload {string} to return display label using userID")
    public void apiCallwithPayloadusinguserID(String payload, Map<String, String> data) throws JsonProcessingException {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);
        temp.put("userId", userID);
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, temp));
    }

    @And("User send Api call with payload {string} to return display label")
    public void apiCallwithPayload(String payload, Map<String, String> data) throws JsonProcessingException {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/" + payload + ".json");
        Map<String, String> temp = new HashMap<>(data);
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, temp));
    }

    @And("User send Api call with payload {string} for project details Update")
    public void updateProject(String payload, Map<String, String> data) throws JsonProcessingException {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/" + payload + ".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }


    @When("I can provide project information to create a {string} project on {string}")
    public void i_can_provide_project_information_to_create_project(String provisioning, String state) {
        System.out.println("Project is initiated on '" + state + "' state.");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateProject.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("state", state);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("country", "USA");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contractId", (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        String curentDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1h", 5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contractStartDate", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("programName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("stateAgencyName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("productId", 1);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList(provisioningString.get(), false, provisioning);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("projectStatus").addProperty("provisioningStatus", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomListValue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("projectStatus").addProperty("effectiveStartDate", curentDateTime);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @When("I can create a {string} project on {string} with contract number more than 6 digits")
    public void i_can_provide_project_information_to_create_project_with_contract_number_more_than_six(String provisioning, String state) {
        System.out.println("Project is initiated on '" + state + "' state.");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateProject.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("state", state);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("country", "USA");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contractId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        long curentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contractStartDate", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("programName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("stateAgencyName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("productId", 1);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList(provisioningString.get(), false, provisioning);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("projectStatus").addProperty("provisioningStatus", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomListValue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("projectStatus").addProperty("effectiveStartDate", curentDateTime);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // System.out.println(apitdu.jsonElement.toString());
    }

    @When("I can provide project information to create a {string} project on {string} with contract start date as Yesterday date")
    public void i_can_provide_project_information_to_create_project_with_start_date_as_yesterday_date(String provisioning, String state) {
        System.out.println("Project is initiated on '" + state + "' state.");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateProject.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("state", state);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("country", "USA");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contractId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        long yesterdayDateTime = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contractStartDate", yesterdayDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("programName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("stateAgencyName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("productId", 1);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList(provisioningString.get(), false, provisioning);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("projectStatus").addProperty("provisioningStatus", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomListValue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("projectStatus").addProperty("effectiveStartDate", yesterdayDateTime);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // System.out.println(apitdu.jsonElement.toString());
    }

    @When("I can provide project information to create a {string} project on {string} with end date greater than today")
    public void i_can_provide_project_information_to_create_project_with_end_date_greater_than_today(String provisioning, String state) {
        System.out.println("Project is initiated on '" + state + "' state.");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateProject.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("state", state);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("country", "USA");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contractId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        //long tomorrowDateTime = System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000;
        String tomorrowDateTime = (String) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().calculateDateTime("+1d", 5);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contractStartDate", tomorrowDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("programName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(8);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("stateAgencyName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("productId", 1);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList(provisioningString.get(), false, provisioning);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("projectStatus").addProperty("provisioningStatus", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomListValue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("projectStatus").addProperty("effectiveStartDate", tomorrowDateTime);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // System.out.println(apitdu.jsonElement.toString());
    }

    private void search_project_by(JsonObject requestParams, String item, String value) {
        // -- Make consumer search by specific property. --
        switch (item) {
            case "state":
                requestParams.addProperty("state", value);
                break;
            case "programName":
                requestParams.addProperty("programName", value);
                break;
            case "projectName":
                requestParams.addProperty("projectName", value);
                break;
            case "stateAgencyName":
                requestParams.addProperty("stateAgencyName", value);
                break;
        }
    }

    @When("I can provide all blank project information")
    public void i_can_provide_al_blank_project_information() {
        requestParams.set(new JsonObject());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateProject.json");
        System.out.println(requestParams);
    }

    @When("I can Search Project by {string} with value {string}")
    public void i_can_search_project_by(String item, String value) {
        requestParams.set(new JsonObject());
        if (item.trim() != "state") {
            search_project_by(requestParams.get(), "state", "");
        }
        if (item.trim() != "programName") {
            search_project_by(requestParams.get(), "programName", "");
        }
        if (item.trim() != "projectName") {
            search_project_by(requestParams.get(), "projectName", "");
        }
        if (item.trim() != "stateAgencyName") {
            search_project_by(requestParams.get(), "stateAgencyName", "");
        }
        search_project_by(requestParams.get(), item, value);
    }

    @When("I can Search Project by {string} with value {string}, {string} with value {string}, {string} with value {string} and {string} with value {string}")
    public void i_can_search_project_by_node_and_value(String node1, String value1, String node2, String value2, String node3, String value3, String node4, String value4) {
        requestParams.set(new JsonObject());
        if (node1 != null && !node1.isEmpty()) {
            search_project_by(requestParams.get(), node1, value1);
        }
        if (node2 != null && !node2.isEmpty()) {
            search_project_by(requestParams.get(), node2, value2);
        }
        if (node3 != null && !node3.isEmpty()) {
            search_project_by(requestParams.get(), node3, value3);
        }
        if (node4 != null && !node4.isEmpty()) {
            search_project_by(requestParams.get(), node4, value4);
        }
    }

    @And("I run the search project API")
    public void i_run_the_search_project_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @And("I run the get project list API")
    public void i_run_the_get_project_list_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().GetAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }


    @And("I run the search project API By ProjectID")
    public void i_run_the_search_project_api_by_project_id() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I run staff record search ujnder a project API By ProjectID")
    public void i_run_staff_recrod_search_under_a_project_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I can get the project approver detail")
    public void i_can_get_the_project_detail() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I run the create project API")
    public void i_run_the_creat_project_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I run the create project API again")
    public void i_run_the_creat_project_api_again() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("errorResponse"));
    }

    @And("I run the create empty project API")
    public void i_run_the_creat_empty_project_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can search the project by project name to validate is {string}")
    public void i_can_verify_api_response_will_be_success(String success) {
        JsonObject rp = requestParams.get();
        String projectName = rp.get("projectName").toString().replace("\"", "");
        System.out.println("Project Name : " + projectName);
        i_initiated_project_search_api();
        i_can_search_project_by("projectName", projectName);
        i_run_the_search_project_api();
        i_can_verify_the_project_search_api_response(success);
    }

    @Then("I can verify on project search API response will be {string}")
    public void i_can_verify_the_project_search_api_response(String success) {
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
        }
    }

    @Then("I can verify the project error message on API response")
    public void i_can_verify_the_duplicate_project_error_message_api_response() {
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
    }

    @Then("I can search the project by project name to validate is success")
    public void i_can_verify_the_project_search_api_response() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I can verify Project ID on project search API response will be {string}")
    public void i_can_verify_project_id_on_project_search_api_response(String success) {
        i_can_verify_the_project_search_api_response(success);
        int projId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("projectResponse.projectRequest.projectStatus.projectId");
        System.out.println(projId);
        assertEquals(projId + "", projectId);
    }

    @Then("I can verify get user approver detail API response will be {string}")
    public void i_can_verify_get_user_approver_detail_api_response(String success) {
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
        }
    }

    @Then("I can verify Refresh API response will be {string}")
    public void i_can_verify_refresh_api_response(String success) {
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
        }
    }

    @Then("I verify response contain error message for Invalid Project name")
    public void i_verify_response_contain_error_message_for_invalid_project_name() {
        JsonObject errorResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("errorResponse").getAsJsonObject();
        assertTrue(errorResponse.get("errorCode").isJsonNull());
        assertTrue(errorResponse.get("traceId").isJsonNull());
        assertTrue(errorResponse.get("errorMessage").toString().contains("Invalid Project name. Only A-Za-z0-9-_ are allowed"));
    }

    @And("I verify response returns valid {string} and {string} values")
    public void i_verify_response_returns_valid_something_and_something_values(String firstname, String lastname) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userInfo").get("firstName").toString().replace("\"", ""), firstname, "Unmatched firstName value");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userInfo").get("lastName").toString().replace("\"", ""), lastname, "Unmatched lastName value");
    }

    @Then("I can verify {string} with value {string} on project API response will be {string}")
    public void i_can_verify_the_project_search_api(String field, String value, String success) {
        i_can_verify_the_project_search_api_response(success);
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            switch (field) {
                case "state":
                    List<List> stateLst = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("list.projectRequest.state");
                    for (ListIterator<List> iter = stateLst.listIterator(); iter.hasNext(); ) {
                        Object element = iter.next();
                        assertTrue(Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE).matcher(element.toString()).find());
                    }
                    break;
                case "programName":
                    List<List> programLst = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("list.projectRequest.programName");
                    for (ListIterator<List> iter = programLst.listIterator(); iter.hasNext(); ) {
                        Object element = iter.next();
                        assertTrue(Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE).matcher(element.toString()).find());
                    }
                    break;
                case "projectName":
                    List<List> projectLst = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("list.projectRequest.projectName");
                    for (ListIterator<List> iter = projectLst.listIterator(); iter.hasNext(); ) {
                        Object element = iter.next();
                        assertTrue(Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE).matcher(element.toString()).find());
                    }
                    break;
                case "stateAgencyName":
                    List<List> stateAgencyNameLst = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("list.projectRequest.stateAgencyName");
                    for (ListIterator<List> iter = stateAgencyNameLst.listIterator(); iter.hasNext(); ) {
                        Object element = iter.next();
                        assertTrue(Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE).matcher(element.toString()).find());
                    }
                    break;
            }
        }
    }

    @And("I can get ProjectID")
    public void i_can_get_project_id() {
        i_can_verify_the_project_search_api_response("True");
        List<String> projectIdLst = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("list.projectRequest.projectStatus.projectId");
        // System.out.println(projectIdLst);
        Object projId = projectIdLst.get(0);
        projectId = projId.toString();
        System.out.println("Project ID : " + projectId);
    }


    public void get_project_id_by_project_name(String projectName) {
        /*This function will set Project Id based on prject Name */
        i_initiated_project_search_api();
        i_can_search_project_by("projectName", projectName);
        i_run_the_search_project_api();
        i_can_get_project_id();
    }

    public JsonObject get_project_detail_by_id(String projectID) {
        /*This function will return Project Detail response in a JsonObject*/
        if (projectID != null && !projectID.isEmpty()) {
            projectId = projectID;
        }
        i_initiated_project_search_api_by_id(projectID);
        i_run_the_search_project_api_by_project_id();
        i_can_verify_project_id_on_project_search_api_response("True");
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }


    public JsonObject get_project_detail_by_project_name(String projectName) {
        /*This function will return Project Detail response in a JsonObject*/
        get_project_id_by_project_name(projectName);
        return get_project_detail_by_id("");
    }

    @When("I can define user search on a project using api")
    public void i_can_define_user_search_on_a_project_using_api() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiProjectUsers.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("maxId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiUserId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("user").getAsJsonObject().addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @And("I can post the the user search on a project using API")
    public void i_can_post_user_search_on_a_project_using_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        //usingFileOutputStream(api.responseString);
    }

    @When("I can define user activity for project using api")
    public void i_can_define_user_activity_for_project_using_api() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiUserActivity.json");
        long curentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("referenceobjectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("sessionTime", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("userId", user_id.get());

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());

    }

    @And("I can verify the the user activity session using API")
    public void i_can_verify_user_activity_session_using_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    //Aswath
    /*
     * Initate the Apigee API
     * Author: Aswath  Date of scripted: 08/19/2019
     * */
    @Given("I initiated apigee api")
    public void initiateApigeeApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseTMURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getApigee);
    }

    /*
     * Define the Apigee API
     * Author: Aswath  Date of scripted: 08/19/2019
     * */
    @Then("I can define apigee using api")
    public void defineApigeeInfomration() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiApigee.json");
        System.out.println("Printing api,maxid,password, project ID" + apiUserID + apiPassword + projectId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("maxId", apiUserID);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("password", apiPassword);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectId", projectId);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());

    }

    /*
     * Execute the Apigee API
     * Author: Aswath  Date of scripted: 08/19/2019
     * */
    @When("I run initiate apigee API")
    public void runInitiateApigee() {
        System.out.println("Printing request String" + requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("Printing Response String" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    /*
     * Verification the Apigee API
     * Author: Aswath  Date of scripted: 08/19/2019
     * */
    @Then("I verify apigee API successfully")
    public void verifyApigeeApi() {
        /*System.out.println ("Prinitng user ID "+ api.jsonPathEvaluator.get("user.staffVO.userId").toString () );
        assertTrue(api.jsonPathEvaluator.get("staffVO.userId").toString()!=null);*/

        Map userIds = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("user.staffVO.user");
        assertTrue(userIds.size() > 0);

    }

    /*
     * Retriving the user from the Apigee API response
     * Author: Aswath  Date of scripted: 08/19/2019
     * */
    @Then("I verify user details retrieved by get api")
    public void verifyUserId() {

        Map user = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("user.staffVO.user");
        System.out.println("Printing response" + user);
        APITaskManagementController e = new APITaskManagementController();
        userIdFromApigee = String.valueOf(user.get("userId"));
        assertTrue(!userIdFromApigee.isEmpty(), "UserId is null");
        System.out.println("Printing UserId from response " + userIdFromApigee);
    }


    /*
     *Need to remove this method
     *  Fetches userID by Apigee API
     * Author: Aswath  Date of scripted: 08/19/2019
     * */
    @Then("I run the Apigee API to get user ID")
    public void iRunApigeeAPI() {
        initiateApigeeApi();

        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        defineApigeeInfomration();
        runInitiateApigee();
        verifyApigeeApi();
        verifyUserId();
    }



    /* Code to initiate Post Event API
     *  Author : Paramita  Date : 14/01/2020 */

    @When("I initiate POST Event API")
    public void initiated_project_search_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(projectRecordEventsEndPoint);
    }


    /* Code to pass request parameters to a JSON body
     *  Author : Paramita  Date : 14/01/2020 */

    @And("I provide {string} and {string} and {string} in the body for Save or Update Events")
    public void provide_evenName_and_module_in_the_body(String eventName, String module, String correlation_Id) {
        projTimeZone.set(TM_EditProjectInformationStepDef.TimeZoneValue);
        correlationId.set(EventsUtilities.getLogs("traceid", projTimeZone.get()).get(0));
        System.out.println("Log gets correlation id:  " + correlationId);

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("eventName", eventName);
        requestParams.get().addProperty("module", module);
        requestParams.get().addProperty("correlationId", correlationId.get());

        System.out.println(requestParams.get().get("eventName") + "  " + requestParams.get().get("module") + "  " + requestParams.get().get("correlationId"));
    }


    /* Code to get request payload with respect to CorerelationID for project Update Event
     *  Author : Paramita  Date : 14/01/2020 */

    @Then("I will run the Event POST API")
    public JSONObject event_POST_API_and_get_the_payload() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        payloadObject.set(new JSONObject(new JSONObject(json.get(0).toString()).get("payload").toString()));
        return payloadObject.get();
    }

    @Given("I initiated project GET API for {string}")
    public void i_initiated_Peoject_get_api(String projectId) throws Exception {
        //throw new Exception("Step Disabled untill further notification");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (projectId.isEmpty()) {
            this.projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        } else if (projectId.equals("GACRM")) {
            this.projectId = ConfigurationReader.getProperty("gaProjectName").split(" ")[0];
        }
        getTenantRecordByIdEndPoint = getTenantRecordByIdEndPoint.replace("{projectId}", this.projectId);
        System.out.println(getTenantRecordByIdEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTenantRecordByIdEndPoint);
    }

    @When("I run the project GET API")
    public void runTheProjectGetAPI() throws InterruptedException {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");
    }

    //Author: Vidya Date:06-04-2020
    @Then("I will verify {string},{string},{string},{string},{string},{string},{string},{string},{string},{string} fields values in project response")
    public void verifyProjectResponseAllFieldsValue(String prjName, String prjState, String prgName, String contractId,
                                                    String stateAgencyName, String startDate, String endDate,
                                                    String goLiveDate, String provStatus, String timeZone) {

        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("projectName").getAsString(), prjName, "Project Name is wrong");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("state").getAsString(), prjState, "State value is wrong");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("programName").getAsString(), prgName, "Program name is wrong");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("contractId").getAsString(), contractId, "Contract Id is wrong");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("stateAgencyName").getAsString(), stateAgencyName, "State Agency Name is wrong");
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("contractStartDate").getAsString().contains(startDate), "Start Date is wrong");
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("contractEndDate").getAsString().contains(endDate), "End date is wrong");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("goLiveDate").getAsString(), goLiveDate, "Go live date is wrong");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                .getAsJsonArray("provisioningStatus")
                .get(0).getAsJsonObject().get("provisioningStatus").getAsString(), provStatus, "Status value is wrong");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("timeZone").getAsString(), timeZone, "Time zone is wrong");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest").
                get("projectId").getAsString(), this.projectId, "Project Id is wrong");
    }

    //Author: Vidya Date:06-04-2020
    @Then("I will verify the Contact Details for {string},{string},{string},{string},{string},{string} in project response")
    public void verifyContactDetaisInProjectResponse(String Role, String fName, String mName, String lName,
                                                     String phone, String email) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                .getAsJsonArray("projectContacts").size(), 4, "Array size is not 4");

        for (int i = 0; i < API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                .getAsJsonArray("projectContacts").size(); i++) {
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                    .getAsJsonArray("projectContacts")
                    .get(i).getAsJsonObject().get("projectRole").getAsString().equalsIgnoreCase(Role) &&
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                            .getAsJsonArray("projectContacts")
                            .get(i).getAsJsonObject().get("contactFirstName").getAsString().equalsIgnoreCase(fName)) {
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                        .getAsJsonArray("projectContacts").get(i).getAsJsonObject().get("contactFirstName").
                                getAsString(), fName, Role + " First Name is not correct");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                        .getAsJsonArray("projectContacts").get(i).getAsJsonObject().get("contactMiddleName").
                                getAsString(), mName, Role + " Middle Name is not correct");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                        .getAsJsonArray("projectContacts").get(i).getAsJsonObject().get("contactLastName").
                                getAsString(), lName, Role + " Last Name is not correct");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                        .getAsJsonArray("projectContacts").get(i).getAsJsonObject().get("contactPhoneNumber").
                                getAsString(), phone, Role + " Phone number is not correct");
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("projectResponse").getAsJsonObject("projectRequest")
                        .getAsJsonArray("projectContacts").get(i).getAsJsonObject().get("contactEmail").
                                getAsString(), email, Role + " Email is not correct");
                break;
            } else if (i == 3) {
                Assert.assertTrue(false, "Array values is not match with any expected values");
            }
        }
    }

    @Then("I construct userActivity request body with an actionId value as {string}")
    public void i_construct_user_activity_request_body_payload(String actionId) {
        Gson gson = new Gson();
        String body = getUserActivityPayload(Integer.parseInt(actionId)).toString();
        JsonElement element = gson.fromJson(body, JsonElement.class);
        requestParams.set(element.getAsJsonObject());
    }
}
