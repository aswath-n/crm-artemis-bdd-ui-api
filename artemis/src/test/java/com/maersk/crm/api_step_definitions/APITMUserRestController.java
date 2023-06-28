package com.maersk.crm.api_step_definitions;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.testng.Assert.*;

public class APITMUserRestController extends CRMUtilities implements ApiBase {

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String arrayOfUserId = "mars/tm/project/users/userdetails/";
    public String oauthURI = "/mars/security/tm/login/apigee";
    public String activedirectoryendPoint = "/mars/tm/common/activedirectory/";
    private String getUserListEndPoint = "mars/tm/project/users";
    private String searchProjectsRoleByProjectIdEndPoint = "mars/tm/project/role/{projectId}?sort=startDate%2Cdesc";
    String getUserRoleDetailsByEmailAndProjectId = "mars/cp/userrole/{emailId}/{projectId}";
    private String createProjectRoleEndPoint = "mars/tm/project/role";
    private String updateRoleEndPoint = "mars/tm/project/user";
    private String getUserAssignedRolesList = "mars/tm/roles/{projectId}";
    private final ThreadLocal<String> roleName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> roleDesc = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<Integer> roleId = ThreadLocal.withInitial(()->0);
    final ThreadLocal<String> projectId = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> emailId = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> projectRoleName = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> userFirstName = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> userLastName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> userStaffId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Integer> userRoleID = ThreadLocal.withInitial(()->0);

    final ThreadLocal<String> startDate = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> endDate = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<List<String>> listOfRoleIds = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal<List<String>> listOfRoleName = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal<List<String>> listOfRoledesc = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<List>> projectRoleLst = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal<List<String>> userProjectId = ThreadLocal.withInitial(ArrayList::new);

    private final ThreadLocal<Integer> userId1 = ThreadLocal.withInitial(() -> 0), userId2 = ThreadLocal.withInitial(() -> 0);
    final ThreadLocal<String> staffId = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> user1FN = ThreadLocal.withInitial(String::new), user1LN = ThreadLocal.withInitial(String::new), user2FN = ThreadLocal.withInitial(String::new), user2LN = ThreadLocal.withInitial(String::new);

    @Given("I will get the user id for current {string} and staffId {string}")
    public void getTheUserIdForCurrentProject(String projectId, String staffId) {
        if (projectId.isEmpty()) {
            this.projectId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
            projectId = this.projectId.get();
        } else {
            this.projectId.set(projectId);
        }
        if (staffId.isEmpty()) {
            this.projectId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
            staffId = this.staffId.get();
        } else {
            this.staffId.set(staffId);
        }
        String getUserIdEndPoint = "mars/tm/project/user/" + staffId + "/" + projectId;


        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUserIdEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");

        if (userId1.get() == 0) {

            userId1.set(Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("staff").getAsJsonObject("user").
                    get("userId").toString()));

        } else {

            userId2.set(Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("staff").getAsJsonObject("user").
                    get("userId").toString()));

        }
    }

    @When("I initiated Array of user id API")
    public void i_initiated_array_of_user_id_api() throws Exception {
        arrayOfUserId = arrayOfUserId + projectId;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(arrayOfUserId);
    }

    @And("I will send single userId in body")
    public void i_will_send_single_userId_in_body() {
        requestParams.set((JsonObject) new JsonParser().parse(new JSONObject().
                put("userId", new JSONArray().put(userId1)).toString()));
        System.out.println(requestParams);
    }

    @And("I run the Array of user id API")
    public void i_run_the_array_of_user_api() throws Exception {
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify the API response parameter")
    public void i_verify_the_api_response_parameter() {
        String str[] = {user1FN.get(), user1LN.get(), userId1 + "", projectId.get(), user2FN.get(), user2LN.get(), userId2 + ""};
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("userDetails");
        int j = 0;
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            assertEquals(temp.getString("firstName"), str[i + j]);
            j++;
            assertTrue(temp.getString("lastName").equals(str[1]) ||
                    temp.getString("lastName").equals(str[5]));
            j++;
            assertTrue(temp.getInt("userId") == Integer.parseInt(str[2]) ||
                    temp.getInt("userId") == Integer.parseInt(str[6]));
            assertEquals(temp.getInt("projectId") + "", str[3]);
            j++;
        }
    }

    @And("I will send array of userId in body")
    public void i_will_send_array_userId_in_body() {
        JSONArray arr = new JSONArray();
        arr.put(userId1.get());
        arr.put(userId2.get());
        requestParams.set((JsonObject) new JsonParser().parse(new JSONObject().
                put("userId", arr).toString()));
        System.out.println(requestParams);
    }

    @And("I will send array of userIds with one user Id which does not exist in body")
    public void i_will_send_array_userId_with_one_does_not_exit_in_body() {
        JSONArray arr = new JSONArray();
        arr.put(userId1);
        arr.put(userId2);
        arr.put(000);
        requestParams.set((JsonObject) new JsonParser().parse(new JSONObject().
                put("userId", arr).toString()));
        System.out.println(requestParams);
    }

    @Given("I will get the staff id for maxId {string}")
    public void getStaffIdForSelectedMaxId(String maxId) {
        String maxPassword = ConfigurationReader.getProperty("password");
        maxId = ConfigurationReader.getProperty(maxId);
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("loginName", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getEncryptedString(maxId));
        requestParams.get().addProperty("passwordValue", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getEncryptedString(maxPassword));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(oauthURI);
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());

        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));

        staffId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").get("staffId").toString());
        if (user1FN.get().isEmpty() || user1LN.get().isEmpty()) {
            user1FN.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").
                    get("firstName").getAsString());
            user1LN.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").
                    get("lastName").getAsString());
        } else {
            user2FN.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").get("firstName").getAsString());
            user2LN.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").get("lastName").getAsString());
        }
    }

    @When("I will initiate user active directory API for {string}")
    public void i_initiated_user_active_directory_api(String MaxId) throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        activedirectoryendPoint = activedirectoryendPoint + MaxId;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(activedirectoryendPoint);
    }

    @When("I hit the GET API of user active directory")
    public void iWillHitTheGetAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");
    }

    @Then("I verify the response has accountExpiry 0 value")
    public void i_verify_the_api_response_has_accountExpiry_0_Value() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("accountExpiry").toString(), "0", "Account Expiry is not 0");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("email").toString(), "LoriHarry@maersk.com", "email address is not matching");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("firstName").toString(), "Lori", "First name is not matching");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("lastName").toString(), "Harry", "last name is not matching");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("validationList").get(0).getAsJsonObject().get("fieldName").getAsJsonNull().isJsonNull(), "Is not null value");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("validationList").get(0).getAsJsonObject().get("validationErrorCode").getAsString(), "account is active", "validationErrorCode value is wrong");
    }

    @Then("I verify the response has accountExpiry other than 0 value")
    public void i_verify_the_api_response_has_accountExpiry_other_than_0_value() {
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator);
        assertNotEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("accountExpiry").toString(), "0", "Account Expiry is 0");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("email").toString(), "ServiceAccount1@maersk.com", "email address is not matching");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("firstName").toString(), "Service", "First name is not matching");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("lastName").toString(), "AccountOne", "last name is not matching");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("validationList").get(0).getAsJsonObject().get("fieldName").getAsJsonNull().isJsonNull(), "Is not null value");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("validationList").get(0).getAsJsonObject().get("validationErrorCode").getAsString(), "account is active", "validationErrorCode value is wrong");
    }


    //CP-502 Author-Paramita - User Role Update

    @And("provide role details with {string} {string} {string}")
    public void i_create_role(String projectId, String roleName, String roleDesc) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createProjectRoleEndPoint);

        requestParams.set(new JsonObject());
        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
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

        long curentDateTime = System.currentTimeMillis();
        requestParams.get().addProperty("startDate", curentDateTime);

    }

    @And("I run create project role API and get new role ID")
    public void i_run_the_create_project_role_api() throws Exception {
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("projectRoles");
        JSONObject temp = new JSONObject(json.get(0).toString());
        if (temp.has("roleName") && !temp.isNull("roleName") && !temp.get("roleName").toString().equals("")) {
            roleId.set(temp.getInt("projectRoleId"));
        }

        projectId.set(requestParams.get().get("projectId").toString().replace("\"", ""));
        roleName.set(requestParams.get().get("roleName").toString().replace("\"", ""));
        roleDesc.set(requestParams.get().get("roleDesc").toString().replace("\"", ""));


    }

    @And("I check for exiting roles for {string} and {string}")
    public void initiate_existing_role(String maxid, String projectId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        getUserAssignedRolesList = getUserAssignedRolesList.replace("{projectId}", projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUserAssignedRolesList);

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("maxId", maxid);
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("projectRoles");
        if (json.size() > 0) {
            JSONObject temp = new JSONObject(json.get(0).toString());
            if (temp.has("roleName") && !temp.isNull("roleName") && !temp.get("roleName").toString().equals("")) {
                userRoleID.set(temp.getInt("projectRoleId"));
            }
        }

    }

    @And("I check whether user of given maxid exits in {string} or will create user with {string}")
    public void checkProjectUser(String projectId, String maxId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUserListEndPoint);


        requestParams.set(new JsonObject());
        requestParams.get().addProperty("maxId", maxId);
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();


        }
        List<String> project_Id = Arrays.asList(projectId);


        userProjectId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("staffList.content.usersList.projectId"));
        String output = userProjectId.get().toString().replaceAll("(^\\[|\\]$)", "");


        if (output.contains(projectId)) {
            userStaffId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("staffList").getAsJsonArray("content").get(0).getAsJsonObject().
                    get("staffId").getAsString());


        } else if (!userProjectId.get().contains(project_Id)) {

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateProjectUser.json");
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateRoleEndPoint);


            if (projectId.isEmpty()) {
                projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
            }

            String startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0, 16);

            String endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0, 16);

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", startDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("projectId", projectId);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("startDate", startDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("createdOn", startDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

            JsonArray teamRoleArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole");
            JsonObject roleUserObj3 = teamRoleArray.get(0).getAsJsonObject();
            roleUserObj3.addProperty("startDate", startDate);
            roleUserObj3.addProperty("endDate", "");
            roleUserObj3.addProperty("projectId", projectId);
            roleUserObj3.addProperty("createdOn", startDate);
            roleUserObj3.addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);


            requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);


            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUserListEndPoint);

            requestParams.set(new JsonObject());
            requestParams.get().addProperty("maxId", maxId);
            System.out.println(requestParams);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

            userStaffId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("staffList").getAsJsonArray("content").get(0).getAsJsonObject().
                    get("staffId").getAsString());


        }
    }

    @And("I get userID for existing maxid {string} for {string}")
    public void get_UserID_maxID(String maxid, String projectId) {
        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }

        String getUserIdEndPoint = "mars/tm/project/user/" + userStaffId + "/" + projectId;


        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUserIdEndPoint);


        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");

        projectRoleLst.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("staff.userProjectRoleVOList.projectRoles.projectRoleId"));

        //System.out.println("StaffAssigRole" +projectRoleLst);


        if (userId1.get() == 0) {

            userId1.set(Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("staff").getAsJsonObject("user").
                    get("userId").toString()));

        } else {

            userId2.set(Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("staff").getAsJsonObject("user").
                    get("userId").toString()));

        }


    }


    @Given("I initiated Update User Role API")
    public void initiate_Update_role() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateRoleEndPoint);
    }


    @When("I provide single user role Update details for {string} for {string}")
    public void userRole_Update_details(String projectId, String maxid) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiUpdateUserRole.json");

        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }

        String startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0, 16);

        String endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0, 16);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("maxId", maxid);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("projectId", projectId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("userId", userId1.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("inactivateImmediately", "false");


        JsonArray teamRoleArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole");
        JsonObject roleUserObj3 = teamRoleArray.get(0).getAsJsonObject();
        roleUserObj3.addProperty("projectRoleId", roleId.get());
        roleUserObj3.addProperty("roleName", roleName.get());
        roleUserObj3.addProperty("roleDesc", roleDesc.get());
        roleUserObj3.addProperty("startDate", startDate);
        roleUserObj3.addProperty("endDate", endDate);
        roleUserObj3.addProperty("projectId", projectId);
        roleUserObj3.addProperty("createdOn", startDate);
        roleUserObj3.addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        roleUserObj3.addProperty("updatedOn", startDate);
        roleUserObj3.addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);


        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams);

    }


    @When("I provide role Update details with date value {string} {string} for {string} and {string}")
    public void userRole_DateVlue_Update_details(String startdate, String enddate, String projectId, String maxid) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiUpdateUserRole.json");
        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }

        if (startdate.equalsIgnoreCase("currentDate")) {
            startDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0, 16));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("startDate", System.currentTimeMillis());
        } else if (!startdate.isEmpty() && startdate.contains("+")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(Integer.parseInt(startdate.replace("+", "")));
            startDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates);
        } else if (!startdate.isEmpty() && startdate.contains("-")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(Integer.parseInt(startdate.replace("-", "")));
            startDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates);
        }

        if (enddate.equalsIgnoreCase("currentDate")) {
            endDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0, 16));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("endDate", System.currentTimeMillis());
        } else if (!enddate.isEmpty() && enddate.contains("+")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(Integer.parseInt(enddate.replace("+", "")));
            endDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("endDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates);
        } else if (!enddate.isEmpty() && enddate.contains("-")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(Integer.parseInt(enddate.replace("-", "")));
            endDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates),
                    ZoneOffset.UTC).toString().substring(0, 16));
            ;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("endDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("maxId", maxid);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("projectId", projectId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("userId", userId1.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("inactivateImmediately", "false");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("roleName", roleName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("roleDesc", roleDesc.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("startDate", startDate.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("endDate", endDate.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("projectId", projectId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("projectRoleId", roleId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("createdOn", startDate.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("updatedOn", startDate.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject().addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());


    }

    @And("I run update user role API")
    public void runUserROleUpdateAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
    }

    @Then("I verify success status for Update User Role API")
    public void verifySuccessResponseMesgForUpdateUserRole() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        System.out.println("ResponseString" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }


    @And("I verify User role is updated with all required information displayed")
    public void verifyRequiredInfoInResponse() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("projectRoleId"), "Project Role Id not displaying");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("projectId"), "Project Id no displaying");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("roleName"), "Role name is empty");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("roleDesc"), "Role Description showing empty");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("startDate"), "Start date value is not displaying");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("endDate"), "End date is not showing");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("createdOn"), "Created On is not showing");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("createdBy"), "Created By is not showing");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("updatedOn"), "Update On is not displaying");
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("updatedBy"), "Updated By not displaying");
    }


    @And("I verify user role field value is updated sucessfully")
    public void verifyUpdatedUserRoleResponse() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("projectRoleId").getAsInt(), roleId, "Role ID value showing wrong");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("roleName").getAsString().replace("\"", ""), roleName, "Role name is not matching");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("roleDesc").getAsString().replace("\"", ""), roleDesc, "Role Desc not matching");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("createdBy").getAsString().replace("\"", ""), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "Created BY is missing");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("updatedBy").getAsString().replace("\"", ""), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId, "Updated By is missing");


        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("createdOn").getAsString(), "Created On value is showing null");
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("updatedOn").getAsString(), "Updated On value is showing null");
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("startDate").getAsString(), "Start Date value is showing null");
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("endDate").getAsString(), "End date value is showing null");
    }


    @And("I verify user role field value is updated sucessfully with date value")
    public void roleDateCheck() {
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("startDate").getAsString(), "Start Date value is missing");
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("endDate").getAsString(), "End date value is missing");

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("endDate").toString().substring(1, 17), endDate, "Actual and Expected End Date value is not matching");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("userProjectRoleRequest").getAsJsonArray("userProjectRoleVOList").get(0).getAsJsonObject().getAsJsonArray("projectRole").get(0).getAsJsonObject()
                .get("startDate").toString().substring(1, 17), startDate, "Actual and Expected Start Date value is not matching");
    }


    @And("I get role list for {string} and {string}")
    public void rolelistProjectID(String maxid, String projectId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        getUserAssignedRolesList = getUserAssignedRolesList.replace("{projectId}", projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUserAssignedRolesList);

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("maxId", maxid);
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        ArrayList<Object> roleList = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("projectRoles");
        for (Object roleId : roleList) {
            HashMap<String, Object> roleMap = (HashMap<String, Object>) roleId;
            listOfRoleIds.get().add(roleMap.get("projectRoleId").toString());
            listOfRoleName.get().add(roleMap.get("roleName").toString());
        }
        System.out.println(listOfRoleIds.get());
        System.out.println(listOfRoleName.get());

    }

    @And("I provide the details for creating or update project user")
    public void provideProjectUserDetails(Map<String, String> data) {

        if (data.get("action").equalsIgnoreCase("create"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateProjectUser.json");
        if (data.get("projectId").isEmpty()) {
            projectId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        }

        String startDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDate("yyyy-MM-dd");
        String createdOn = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDate("yyyy-MM-dd") + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.SSS") + "Z";

        for (String key : data.keySet()) {
            switch (key) {
                case "firstName":
                    userFirstName.set("John" + RandomStringUtils.randomAlphabetic(2));
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", userFirstName.get());
                    break;
                case "lastName":
                    userLastName.set("William" + RandomStringUtils.randomAlphabetic(2));
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("lastName", userLastName.get());
                    break;
                case "email":
                    emailId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("firstName").getAsString()
                            + "." + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("lastName").getAsString() + "@gmail.com");
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("email", emailId.get());
                    break;
            }
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", createdOn);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("projectId", projectId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("startDate", startDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("createdOn", createdOn);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("user").addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);

        JsonObject roleUserObj3 = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("userProjectRole").get(0).getAsJsonObject();
        roleUserObj3.addProperty("startDate", startDate);
        roleUserObj3.addProperty("endDate", (String) null);
        // roleUserObj3.addProperty("projectId", projectId);
        roleUserObj3.addProperty("createdOn", createdOn);
        roleUserObj3.addProperty("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        roleUserObj3.addProperty("projectRoleId", roleId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @And("I run create or update project user with role API")
    public void runCreateUpdateUser() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("ResponseString" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify success status for create or update project Userwith  Role API")
    public void verifySuccessResponseMesgForCreateUpdateUserRole() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @Then("I verify error message is displayed for create or update project Userwith  Role API")
    public void verifyErrorResponseMesgForCreateUpdateUserRole() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("errorResponse").get("errorCode").getAsString(), "User you are trying to create already is an active user in the system");
    }

    @Given("I initiated get project role by {string} API")
    public void initiateGetProjectRoleByProject(String projectId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (projectId.isEmpty())
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        searchProjectsRoleByProjectIdEndPoint = searchProjectsRoleByProjectIdEndPoint.replace("{projectId}", projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchProjectsRoleByProjectIdEndPoint);
    }

    @And("I run get project role by project API")
    public void runGetProjectRoleByProjectId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("ResponseString" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        roleId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("projectRoles").get(0).getAsJsonObject().get("projectRoleId").getAsInt());
        projectRoleName.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("projectRoles").get(0).getAsJsonObject().get("roleName").getAsString());
    }

    @Given("I initiated get user role details by {string} and {string} API")
    public void initiateGetUserRoleDetailsByEmailID(String projectId, String emailId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (projectId.isEmpty())
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        if (emailId.isEmpty() || emailId.contains("random"))
            emailId = this.emailId.get();
        getUserRoleDetailsByEmailAndProjectId = getUserRoleDetailsByEmailAndProjectId.replace("{projectId}", projectId).replace("{emailId}", emailId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUserRoleDetailsByEmailAndProjectId);
    }

    @And("I run get user role by project and emaild API")
    public void runGetUserByProjectAndEmail() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("ResponseString" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify user role details are displayed  for the Non maersk user")
    public void verifyUserRoleDetails() {
        int actProjectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonArray("projectRole").get(0).getAsJsonObject().get("projectId").getAsInt();
        int actroleId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonArray("projectRole").get(0).getAsJsonObject().get("projectRoleId").getAsInt();
        String actRoleNAme = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonArray("projectRole").get(0).getAsJsonObject().get("roleName").getAsString();
        String startDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").getAsJsonObject("user").get("startDate").getAsString();
        assertEquals(actProjectId, Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId()));
        assertEquals(actroleId, roleId);
        assertEquals(actRoleNAme, projectRoleName);
        assertEquals(startDate, API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDate("yyy-MM-dd"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").get("email").getAsString(), emailId);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").get("firstName").getAsString(), userFirstName);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").get("lastName").getAsString(), userLastName);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").get("maxId").getAsString(), "");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").getAsJsonObject("user").get("userStatus").getAsString(), "ACTIVE");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").getAsJsonObject("user").get("applicationType").getAsString(), "ConnectionPoint");
    }
}


