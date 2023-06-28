package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APITMProjectRoleRestController extends CRMUtilities implements ApiBase {
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> projectId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> roleName= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> roleDesc= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> roleId= ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> roleStartDate = ThreadLocal.withInitial(String::new);
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String createProjectRoleEndPoint = "mars/tm/project/role";
    private String searchProjectsRolesEndPoint = "mars/tm/getRoles/{projectId}";
    private String searchProjectsRoleByProjectIdEndPoint = "mars/tm/project/role/{projectId}";
    private String searchAssignedRoleEndPoint = "mars/tm/getRoleDescription/{projectId}/{roleName}";
    private String searchRoles = "mars/tm/searchRoles";
    private String clonePermission = "mars/tm/project/clonepermissions";
    private String supervisorDetailsEndPoint = "mars/tm/project/user/supervisor/teammembers/{projectId}/{userId}";
    private String provisioningString = "Active,Inactive,Pending";
    final ThreadLocal<List<String>> teamNames = ThreadLocal.withInitial(ArrayList::new);

    @Given("I initiated Create Project Role API")
    public void i_initiated_create_project_role_api() throws Exception {
        //throw new Exception("Step Disabled untill further notification");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createProjectRoleEndPoint);
    }

    @Given("I initiated Search Project Role API")
    public void i_initiated_search_project_role_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchRoles);
    }

    @Given("I initiated clone role permissions API")
    public void I_initiated_Search_Clone_role_permissions_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(clonePermission);
    }

    @And("I initiated get Supervisor and Team Details API with {string} and {string}")
    public void iInitiatedGetSupervisorAndTeamDetailsAPIWithAnd(String projectId, String userId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        supervisorDetailsEndPoint = supervisorDetailsEndPoint.replace("{projectId}", projectId).replace("{userId}", userId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(supervisorDetailsEndPoint);
    }

    @When("I can provide role details with {string} {string} {string}")
    public void i_can_search_project_by(String projectId, String roleName, String roleDesc) {
        requestParams.set(new JsonObject());
        if (projectId.isEmpty() || projectId.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            projectId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber;
        }
        requestParams.get().addProperty("projectId", projectId);

        if (roleName.isEmpty() || roleName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            this.roleName.set("Role" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }
        requestParams.get().addProperty("roleName", this.roleName.get());

        if (roleDesc.isEmpty() || roleDesc.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            this.roleDesc.set("Description" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }
        requestParams.get().addProperty("roleDesc", this.roleDesc.get());

        // long curentDateTime = System.currentTimeMillis();
        // long curentDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
        //         ZoneOffset.UTC).toString().substring(0,10);
        //requestParams.addProperty("startDate", roleStartDate);
        roleStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0, 10));
        ;
        requestParams.get().addProperty("startDate", roleStartDate.get());
        //apitdu.jsonElement.getAsJsonObject().addProperty("effectiveStartDate", roleStartDate);

    }

    @And("I run the create project role API")
    public void i_run_the_create_project_role_api() throws Exception {
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        projectId.set(requestParams.get().get("projectId").toString().replace("\"", ""));
        roleName.set(requestParams.get().get("roleName").toString().replace("\"", ""));
        roleId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("projectRoles").get(0).getAsJsonObject().get("projectRoleId").getAsString());


    }

    @And("I run the get Supervisor and Team Details API")
    public void i_run_the_get_Supervisor_and_Team_Details_api() throws Exception {
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I verify the API returns details of team whose supervisor is {string}")
    public void iVerifyTheAPIReturnsDetailsOfTeamWhoseSupervisorIs(String userId) {
        JsonArray teamUsers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamUsers");
        Assert.assertFalse(teamUsers.isJsonNull());
        int count = teamUsers.size();
        for (int i = 0; i < count; i++) {
            JsonObject team = teamUsers.get(i).getAsJsonObject();
            Assert.assertFalse(team.isJsonNull());
            teamNames.get().add(team.get("teamName").toString());
        }
    }

    @Then("I verify {string} is a supervisor for following teams")
    public void iVerifyIsASupervisorForFollowingTeams(String userId, List<String> expectedTeamNames) {
        assertEquals(expectedTeamNames, teamNames);
    }

    @Then("I can search the project role by role name to validate is {string}")
    public void i_can_verify_api_response_will_be_success(String success) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println("Role Name : " + roleName);

//        projectId = "1";
        i_initiated_project_role_by_project_id_via_api(projectId.get());
        i_can_get_the_assign_role_of_a_project_via_api();
        i_can_verify_the_project_role_field_search_api("roleName", roleName.get(), success);
    }

    @Given("I initiated Project Roles Search By Project ID via API")
    public void i_initiated_project_roles_search_by_project_id_via_api(String project_id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        searchProjectsRolesEndPoint = searchProjectsRolesEndPoint.replace("{projectId}", project_id);
        System.out.println(searchProjectsRolesEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchProjectsRolesEndPoint);
    }

    @Given("I initiated Project Role By Project ID via API {string}")
    public void i_initiated_project_role_by_project_id_via_api(String project_id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        searchProjectsRoleByProjectIdEndPoint = searchProjectsRoleByProjectIdEndPoint.replace("{projectId}", project_id);
        System.out.println(searchProjectsRoleByProjectIdEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchProjectsRoleByProjectIdEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @Given("I initiated Assigned Role Search By {string} and {string} via API")
    public void i_initiated_assigned_role_search_by_project_id_and_role_name_via_api(String project_id, String role_name) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        searchAssignedRoleEndPoint = searchAssignedRoleEndPoint.replace("{projectId}", project_id).replace("{roleName}", role_name);
        System.out.println(searchAssignedRoleEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchAssignedRoleEndPoint);
    }

    @When("I can Search Project Role by {string} with value {string}")
    public void i_can_search_project_role_by(String item, String value) {
        //requestParams = new JsonObject();
        search_project_role_by(requestParams.get(), item, value);
    }

    private void search_project_role_by(JsonObject requestParams, String item, String value) {
        // -- Make consumer search by specific property. --
        switch (item) {
            case "roleName":
                if (value.isEmpty() || value.equals("{random}")) value = roleName.get();
                requestParams.addProperty("roleName", value);
                break;
            case "roleDesc":
                if (value.isEmpty() || value.equals("{random}")) value = roleDesc.get();
                requestParams.addProperty("roleDesc", value);
                break;
            case "projectId":
                requestParams.addProperty("projectId", value);
                break;
            case "roleStatus":
                requestParams.addProperty("roleStatus", value);
                break;
            case "modified_date":
                requestParams.addProperty("modified_date", value);
                break;
            case "creation_date":
                requestParams.addProperty("creation_date", value);
                break;
        }
    }

    @And("I run get roles of a project via API")
    public void i_run_get_roles_of_a_project_via_api() {
        Map<String, String> queryParams = new HashMap();
        queryParams.put("searchType", "nonsolace");
        queryParams.put("size", "5");
        queryParams.put("page", "0");
        queryParams.put("sort", "startDate");

        System.out.print("------------");
        System.out.println(requestParams);
        System.out.print("------------");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameterAndQuery(requestParams.get(), queryParams);
        // api.PostAPIWithParameter(requestParams);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(requestParams);
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I can verify on project role search API response will be {string}")
    public void i_can_verify_the_project_role_search_api_response(String success) {
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");
    }

    @Then("I can verify each role has all required information displayed")
    public void i_can_verify_required_info_on_search_results() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("roleName"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("roleDesc"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("startDate"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("endDate"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("createdOn"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("updatedOn"));
    }

    @Then("I verify each role has exact role name as the search criteria")
    public void exact_role_name_returned() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(roleName.get()));
    }

    @Then("I can verify the duplicate role error message on API response")
    public void i_can_verify_the_project_role_search_api_response() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
    }

    @And("I can get the assign role of a project via API")
    public void i_can_get_the_assign_role_of_a_project_via_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I can verify {string} with value {string} on project role API response will be {string}")
    public void i_can_verify_the_project_role_field_search_api(String field, String value, String success) {
        i_can_verify_the_project_role_search_api_response(success);
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            switch (field) {
                case "roleName":
                    List<List> projectRoleLst = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("list.projectRoles.roleName");
                    System.out.println(projectRoleLst);
                    List<String> orjectRoleObj = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("projectRoles.roleName");
                    System.out.println(orjectRoleObj);
                    assert orjectRoleObj.contains(value);
                    break;
                case "roleStatus":
                    List<List> projectRoleStatus = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("list.projectRoles.status");
                    System.out.println(projectRoleStatus);
                    List<String> prrjectRoleObj = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("projectRoles.status");
                    System.out.println(prrjectRoleObj);
                    assert prrjectRoleObj.contains(value);
                    break;
            }
        }
    }

    @When("I provide role details {string} {string} {string} with {string}")
    public void i_provide_role_details_with(String projectId, String roleName, String roleDesc, String rolePermission) {
        requestParams.set(new JsonObject());
        if (projectId.isEmpty() || projectId.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            projectId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber;
        }
        requestParams.get().addProperty("projectId", projectId);

        if (roleName.isEmpty() || roleName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
            roleName = "Role" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString;
        }
        requestParams.get().addProperty("roleName", roleName);

        if (roleDesc.isEmpty() || roleDesc.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            roleDesc = "Description" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString;
        }
        requestParams.get().addProperty("roleDesc", roleDesc);

        if (rolePermission.isEmpty() || rolePermission.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
            rolePermission = "Permission" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString;
        }
        requestParams.get().addProperty("projectRolePermissionResponse", rolePermission);

        long currentDateTime = System.currentTimeMillis();
        requestParams.get().addProperty("startDate", currentDateTime);
    }

    @Then("I verify only active roles are returned")
    public void verify_only_active_roles_are_returned() {

    }
}
