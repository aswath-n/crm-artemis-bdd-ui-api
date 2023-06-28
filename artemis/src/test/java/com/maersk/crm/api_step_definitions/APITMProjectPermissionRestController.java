package com.maersk.crm.api_step_definitions;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APITMProjectPermissionRestController extends CRMUtilities implements ApiBase {
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private APITMProjectRestController apiRestController = new APITMProjectRestController();
    private APITMProjectRestController projectRestController = new APITMProjectRestController();
    private final ThreadLocal<JsonObject> requestParams= ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestBodyParameters=ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> projectId =ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> roleName=ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> roleDesc=ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> projectRoleId=ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> groupName=ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> groupDesc=ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> permissionGroupId=ThreadLocal.withInitial(String::new);
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String getProjectsPermissionEndPoint = "mars/tm/project/permission/{projectId}";
    private String saveProjectPermissionEndPoint ="mars/tm/project/permission";
    private String configModifyPermissionEndPoint = "mars/tm/project/config";
    private String configPermissionv2Endpoint="mars/tm/v2/project/config";
    private String searchRoles = "mars/tm/searchRoles";
    private String createProjectRoleEndPoint = "mars/tm/project/role";
    private String getProjectPermissionGrouDetails= "mars/tm/projectdetails/{userId}/{projectId}";

    private Map<Integer, String> pagesIdsKeys = new HashMap<>();
    public ThreadLocal<String> permissionGroupName = ThreadLocal.withInitial(String::new);

    @Given("I can get project id for permission by project name {string}")
    public void i_can_get_a_project_id_for_permission_by_project_name(String projectName) {
        if (projectName.isEmpty()){
            projectName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName();
        }
        projectRestController.get_project_id_by_project_name(projectName);
        projectId.set(projectRestController.projectId);
    }

    @Given("I initiated Create Project Permission API")
    public void i_initiated_create_project_permission_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(saveProjectPermissionEndPoint);
    }

    @Given("I initiated Create Project Permission API v2")
    public void i_initiated_create_project_permission_api_v2() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(configPermissionv2Endpoint);
    }

    @Given("I initiated get project permission API by Projecr ID {string}")
    public void i_initiated_get_project_permission_api(String projectID) {
        if (projectID != null && !projectID.isEmpty()) {
            projectId.set(projectID);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getProjectsPermissionEndPoint=getProjectsPermissionEndPoint.replace("{projectId}", projectId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getProjectsPermissionEndPoint);
    }

    @And("I run the create project permission API")
    public void i_run_the_create_project_permission_api() {
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get().toString());
//        api.PutAPI(requestParams);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        projectId.set(requestParams.get().get("projectId").toString().replace("\"", ""));
        roleName.set(requestParams.get().get("roleName").toString().replace("\"", ""));
    }

    @And("I run get project permission API with Query parameters {string},{string} and {string}")
    public void i_run_get_project_permission_api_with_query(String page, String size, String sort) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!page.isEmpty()) {
            map.put("page", page);
        }
        if (!size.isEmpty()) {
            map.put("size", size);
        }
        if (!sort.isEmpty()) {
            map.put("sort", sort);
        }
        System.out.println(map);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPIAndQuery(map);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println(requestParams);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get permission response success is {string}")
    public void verify_get_permission_response_success_is(String success) {
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
        }
    }

    @Then("I can verify get permission list size is equal or less than {string}")
    public void i_can_verify_get_permission_list_size_equal_or_less_than_size(String size) {
        String permisionListTotal = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("permissionRoleResponse.numberOfElements").toString();
        System.out.println("Number of Rows: " + permisionListTotal);
        assertTrue(Integer.parseInt(permisionListTotal) <= Integer.parseInt(size));
    }

    @Then("I can search the project permission by permission name to validate is {string}")
    public void i_can_verify_project_permission_api_response(String success) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println("Role Name : " + roleName);
//        projectId = "1";
//        i_initiated_project_role_by_project_id_via_api(projectId);
//        i_can_get_the_assign_role_of_a_project_via_api();
//        i_can_verify_the_project_role_field_search_api("roleName", roleName, success);
    }

    public void getPermissionGroupIdAndGroupName(){
        ArrayList groupIds = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("permissionRoleResponse.content.permissionGroupId");
        ArrayList groupNames = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("permissionRoleResponse.content.permissionGroupName");
        System.out.println(groupIds.size());
        int upper = groupIds.size();
        int lower = 0;
        int randomIndex = (int) (Math.random() * (upper - lower)) + lower;
        permissionGroupId.set(groupIds.get(randomIndex).toString());
        permissionGroupName.set(groupNames.get(randomIndex).toString());
    }


    @Given("I initiated and run Create Project Role API for Project Permission Group with {string} {string} {string}")
    public void i_initiated_and_run_Create_Project_Role_API_for_Project_Permission_Group_with(String projectId, String roleName, String roleDesc) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createProjectRoleEndPoint);
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

        long curentDateTime = System.currentTimeMillis();
        requestParams.get().addProperty("startDate", curentDateTime);

        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get().toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        projectRoleId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("projectRoles[0].projectRoleId").toString());
        this.projectId.set(requestParams.get().get("projectId").toString().replace("\"", ""));
        this.roleName.set(requestParams.get().get("roleName").toString().replace("\"", ""));
    }

    @Given("I run the create project permission API with {string} {string}")
    public void i_run_the_create_project_permission_api_with(String grName, String grDesc) {
        requestBodyParameters.set(new JsonObject());
        if (grName.isEmpty() || grName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            groupName.set("Permission"+ API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }
        requestParams.get().addProperty("roleName", this.roleName.get());

        if (grDesc.isEmpty() || grDesc.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            groupDesc.set("Permission Desc"+ API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }

        requestBodyParameters.get().addProperty("permissionGroupName", groupName.get());
        requestBodyParameters.get().addProperty("permissionGroupDescription", groupDesc.get());
        requestBodyParameters.get().addProperty("projectRoleId", projectRoleId.get());

        System.out.println(requestBodyParameters);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestBodyParameters.get().toString());
        permissionGroupId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("permissionGroupId").toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Given("I verify successful creation of project permission")  // verification of response body:
    public void i_verify_successful_creation_of_project_permission() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("status").toString().replace("\"", ""), "success");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("permissionGroupName").toString().replace("\"", ""), groupName);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("permissionGroupDescription").toString().replace("\"", ""), groupDesc);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("roleName").toString().replace("\"", ""), roleName);
    }

    @When("I re-run the create project permission API")
    public void i_re_run_the_create_project_permission_API() {
        requestBodyParameters.get().addProperty("permissionGroupName", groupName.get());
        requestBodyParameters.get().addProperty("permissionGroupDescription", groupDesc.get());
        requestBodyParameters.get().addProperty("projectRoleId", projectRoleId.get());

        System.out.println(requestBodyParameters);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestBodyParameters.get().toString());
    }

    @Then("I can verify the duplicate project permission message on API response")
    public void i_can_verify_the_duplicate_project_permission_message_on_API_response() {
      assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail"); //"failed"
    }

    @Given("I initiated Search Project API and Run by ID {string}")
    public void i_initiated_Search_Project_API_and_Run_by_ID(String projecID) {
        if(projecID.isEmpty()) {
            projecID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        apiRestController.i_initiated_project_search_api_by_id(projecID);
        apiRestController.i_run_the_search_project_api_by_project_id();
/*
        System.out.println(apiRestController.api.jsonPathEvaluator.get("projectResponse.projectRequest.pages.pageId").toString());
        List<Integer> pageIds = apiRestController.api.jsonPathEvaluator.getList("projectResponse.projectRequest.pages.pageId");
        List<String> pageKeys = apiRestController.api.jsonPathEvaluator.getList("projectResponse.projectRequest.pages.key");

        for (int i = 0; i < pageIds.size(); i++) {
            pagesIdsKeys.put(pageIds.get(i), pageKeys.get(i));
        };*/
    }

    @Given("I initiated Config Permission API")
    public void i_initiated_Config_Permission_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(configModifyPermissionEndPoint);
    }

    @Given("I run Config Permission API with")
    public void i_run_Config_Permission_API_with() {
        for (Map.Entry<Integer, String> page : pagesIdsKeys.entrySet()) {
            JsonParser par = new JsonParser();
            JsonObject object = (JsonObject) par.parse("{\n" +
                    "  \"transactionType\": \"page\",\n" +
                    "  \"projectId\": " + projectId + ",\n" +
                    "  \"pagePayload\": [\n" +
                    "    {\n" +
                    "      \"pageId\": " + page.getKey() + ",\n" +
                    "      \"projectId\": " + projectId + ",\n" +
                    "      \"key\": \"" + page.getValue() + "\",\n" +
                    "      \"createdOn\": null,\n" +
                    "      \"createdBy\": null,\n" +
                    "      \"updatedOn\": null,\n" +
                    "      \"updatedBy\": null,\n" +
                    "      \"correlationId\": null,\n" +
                    "      \"uiid\": null,\n" +
                    "      \"sections\": [],\n" +
                    "      \"permissions\": [\n" +
                    "        {\n" +
                    "          \"read\": true,\n" +
                    "          \"write\":false,\n" +
                    "          \"applyMask\": true,\n" +
                    "          \"noPermission\": true,\n" +
                    "          \"effectiveStartDate\": null,\n" +
                    "          \"effectiveEndDate\": null,\n" +
                    "          \"objectId\": " + page.getKey() +",\n" +
                    "          \"objectType\": \"page\",\n" +
                    "          \"permissionGroupId\": " + permissionGroupId + "\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"transactionType\": \"page\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"roleId\": " + projectRoleId + "\n" +
                    "}");

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(object);
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }

    }


    @When("I run Config Permission API using grid payload to update with {string} permission for key {string} grid {string} and section {string}")
    public void i_run_Config_Permission_API_with_Page_Payload(String permissionType, String key, String gridId, String sectionId) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiPermissionGroupGridLevel.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectId", projectId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("roleId", projectRoleId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("gridPayload").get(0).getAsJsonObject().addProperty("key", key);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("gridPayload").get(0).getAsJsonObject().addProperty("gridId", gridId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("gridPayload").get(0).getAsJsonObject().addProperty("sectionId", sectionId);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("gridPayload").get(0).getAsJsonObject().getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("permissionGroupId", permissionGroupId.get());

        setPermissionDetails("gridPayload", permissionType);

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

    }

    @Then("I verify success status for group permission api")
    public void verifySuccessForRunPermission(){
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @Given("I initiated get project permission group details API by User ID {string} and Project Id {string}")
    public void initiateProjectPermissionGrouDetailsApi(String userId, String projectID) {
        if (projectID.isEmpty()) {
            projectId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        } else {
            projectId.set(projectID);
        }
        System.out.println("Project ID :"+projectId.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getProjectPermissionGrouDetails=getProjectPermissionGrouDetails.replace("{projectId}", projectId.get()).replace("{userId}", userId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getProjectPermissionGrouDetails);
    }

    @When("I run get project permission group details API")
    public void runProjectPermissionGrouDetailsApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify permission {string} is updated for section {string} and grid {string}")
    public void verifyPermissionUpdatedForGivenGrid(String permissionType, String sectionId, String gridId) {
        boolean recordFound = false;
        Map<String, Object> permissionDetails = null;
       ArrayList pages = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("pages");
       for(int i=0;i<pages.size();i++){
         Map<String, Object> pageDetails =  (Map<String, Object>)pages.get(i);
           ArrayList sections = (ArrayList)pageDetails.get("sections");
           for(int j=0;j<sections.size();j++){
               Map<String, Object> secionDetails =  (Map<String, Object>)sections.get(j);
               String secId = secionDetails.get("sectionId").toString();
               if(secId.equalsIgnoreCase(sectionId)){
                   ArrayList grids = (ArrayList)secionDetails.get("grids");
                   for(int k=0;k<grids.size();k++){
                       Map<String, Object> gridDetails =  (Map<String, Object>)grids.get(k);
                       if(gridDetails.get("gridId").toString().equalsIgnoreCase(gridId)){
                           ArrayList permissions = (ArrayList)gridDetails.get("permissions");
                           for(int l=0;l<permissions.size();l++){
                               permissionDetails =  (Map<String, Object>)permissions.get(l);
                               if(permissionDetails.get("permissionGroupId")!=null && permissionDetails.get("permissionGroupId").toString().equalsIgnoreCase(permissionGroupId.get())){
                                   recordFound = true;
                                   break;
                               }
                           }
                       }
                       if(recordFound)
                           break;
                   }
               }
               if(recordFound)
                   break;
           }
           if(recordFound)
               break;
       }
        assertTrue(recordFound, "No permission record found");
        verifyExpectedPermission(permissionDetails, permissionType);
    }
    @When("I run Config Permission API using section payload to update with {string} permission for key {string} section {string} and page {string}")
    public void i_run_Config_Permission_API_with_Section_Payload(String permissionType, String key, String sectionId, String pageId) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiPermissionGroupSectionLevel.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectId", projectId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("roleId", projectRoleId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("sectionPayload").get(0).getAsJsonObject().addProperty("key", key);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("sectionPayload").get(0).getAsJsonObject().addProperty("pageId", pageId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("sectionPayload").get(0).getAsJsonObject().addProperty("sectionId", sectionId);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("sectionPayload").get(0).getAsJsonObject().getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("permissionGroupId", permissionGroupId.get());

        setPermissionDetails("sectionPayload", permissionType);

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

    }

    @Then("I verify permission {string} is updated for page {string} and section {string}")
    public void verifyPermissionUpdatedForGivenSection(String permissionType, String pageId, String sectionId) {
        boolean recordFound = false;
        Map<String, Object> permissionDetails = null;
        ArrayList pages = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("pages");
        for(int i=0;i<pages.size();i++){
            Map<String, Object> pageDetails =  (Map<String, Object>)pages.get(i);
            if(pageDetails.get("pageId").toString().equalsIgnoreCase(pageId)){
                ArrayList sections = (ArrayList)pageDetails.get("sections");
                for(int j=0;j<sections.size();j++){
                    Map<String, Object> secionDetails =  (Map<String, Object>)sections.get(j);
                    if(secionDetails.get("sectionId").toString().equalsIgnoreCase(sectionId)){
                        ArrayList permissions = (ArrayList)secionDetails.get("permissions");
                        for(int l=0;l<permissions.size();l++){
                            permissionDetails =  (Map<String, Object>)permissions.get(l);
                            if(permissionDetails.get("permissionGroupId")!=null && permissionDetails.get("permissionGroupId").toString().equalsIgnoreCase(permissionGroupId.get())){
                                recordFound = true;
                                break;
                            }
                        }
                    }
                    if(recordFound)
                        break;
                }
                if(recordFound)
                    break;
            }
        }
        assertTrue(recordFound, "No permission record found");
        verifyExpectedPermission(permissionDetails, permissionType);
    }

    private void verifyExpectedPermission(Map<String, Object> permissionDetails, String permissionType){
        String readValue = permissionDetails.get("read").toString();
        String writeValue = permissionDetails.get("write").toString();
        String noPermissionValue = permissionDetails.get("noPermission").toString();
        switch(permissionType){
            case "View":
                assertEquals(readValue, "true");
                assertEquals(writeValue, "false");
                assertEquals(noPermissionValue, "false");
                break;
            case "Edit":
                assertEquals(readValue, "true");
                assertEquals(writeValue, "true");
                assertEquals(noPermissionValue, "false");
                break;
            case "NoPermission":
                assertEquals(readValue, "false");
                assertEquals(writeValue, "false");
                assertEquals(noPermissionValue, "true");
                break;
        }
    }

    @When("I run Config Permission API using page payload to update with {string} permission for key {string} and page {string}")
    public void i_run_Config_Permission_API_with_Page_Payload(String permissionType, String key, String pageId) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiPermissionGroupPageLevel.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectId", projectId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("roleId", projectRoleId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("pagePayload").get(0).getAsJsonObject().addProperty("key", key);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("pagePayload").get(0).getAsJsonObject().addProperty("pageId", pageId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("pagePayload").get(0).getAsJsonObject().addProperty("projectId", projectId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("pagePayload").get(0).getAsJsonObject().getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("permissionGroupId", permissionGroupId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("pagePayload").get(0).getAsJsonObject().getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("objectId", pageId);
        setPermissionDetails("pagePayload", permissionType);

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    private void setPermissionDetails(String payLoadType, String permissionType){
        switch(permissionType){
            case "View":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("noPermission", false);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("write", false);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("read", true);
                break;
            case "Edit":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("noPermission", false);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("write", true);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("read", true);
                break;
            case "NoPermission":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("noPermission", true);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("write", false);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(payLoadType).get(0).getAsJsonObject()
                        .getAsJsonArray("permissions").get(0).getAsJsonObject().addProperty("read", false);
                break;
        }
    }

    @Then("I verify permission {string} is updated for page {string}")
    public void verifyPermissionUpdatedForGivenPage(String permissionType, String pageId) {
        boolean recordFound = false;
        Map<String, Object> permissionDetails = null;
        ArrayList pages = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("pages");
        for(int i=0;i<pages.size();i++){
            Map<String, Object> pageDetails =  (Map<String, Object>)pages.get(i);
            if(pageDetails.get("pageId").toString().equalsIgnoreCase(pageId)){
                ArrayList permissions = (ArrayList)pageDetails.get("permissions");
                for(int l=0;l<permissions.size();l++){
                    permissionDetails =  (Map<String, Object>)permissions.get(l);
                    if(permissionDetails.get("permissionGroupId")!=null && permissionDetails.get("permissionGroupId").toString().equalsIgnoreCase(permissionGroupId.get())){
                        recordFound = true;
                        break;
                    }
                }
            }
        }
        assertTrue(recordFound, "No permission record found");
        verifyExpectedPermission(permissionDetails, permissionType);
    }
}
