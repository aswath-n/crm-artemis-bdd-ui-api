package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.testng.Assert.*;

public class APITMAddTeamController extends CRMUtilities implements ApiBase {

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String createTeamEndPoint = "mars/tm/team";
    APITMListBusinessUnitController BuGetAPI=new APITMListBusinessUnitController();
    public static final ThreadLocal<Integer> teamId = ThreadLocal.withInitial(() -> 0);
    public static final ThreadLocal<String> teamName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamDesc = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamStartDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamEndDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamCreatedOn = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamUpdatedOn = ThreadLocal.withInitial(String::new);;
    public static final ThreadLocal<String> teamUpdatedBy = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamCreatedBy = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Integer> userId = ThreadLocal.withInitial(() -> 0);
    public static final ThreadLocal<Integer> teamUserId = ThreadLocal.withInitial(() -> 0);
    public static final ThreadLocal<String> effectiveStartDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Boolean> supervisorFlag = ThreadLocal.withInitial(() -> false);

    private String createTeamUserEndPoint = "mars/tm/project/teamUser";
    private String getUserListEndPoint = "mars/tm/project/users?sort=firstName";
    private String getTeamsUserListforBUEndPoint = "mars/tm/teams/{projectId}/{businessUnitId}";
    private String getTeamsListEndPoint = "mars/tm/teams/{projectId}/0";
    public final ThreadLocal<ArrayList<String>> listOfUserIds= ThreadLocal.withInitial(ArrayList::new);

    @When("I initiated Create Team API")
    public void i_initiated_create_team_api() throws Exception{
        //throw new Exception("Step Disabled untill further notification");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createTeamEndPoint);
    }

    @When("I can provide team details with {string} {string} {string} {string} {string} {string}")
    public void iCanProvideTeamDetails(String projectId, String teamName,String teamDesc,String startDate,String endDate,String bu) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateTeam.json");

        if(projectId.isEmpty()){
            projectId= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("projectId", projectId);

        if(bu.isEmpty()){
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("businessUnitId", BuGetAPI.businessUnit);
        }

        if (teamName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            this.teamName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }
        else if(teamName.isEmpty()){
            this.teamName.set(null);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("teamName", this.teamName.get());

        if (teamDesc.isEmpty() || teamDesc.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(1);
            this.teamDesc.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("description", this.teamDesc.get());

        if(startDate.equalsIgnoreCase("currentDate")){
            teamStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0,10));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", teamStartDate.get());
        }
        else if(!startDate.isEmpty() && startDate.contains("+")){
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(Integer.parseInt(startDate.replace("+","")));
            teamStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates),
                    ZoneOffset.UTC).toString().substring(0,10));
            teamStartDate.set(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(Integer.parseInt(startDate.replace("+",""))).toString().substring(0,10));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", teamStartDate.get());
        }else if(!startDate.isEmpty() && startDate.contains("-")){
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(Integer.parseInt(startDate.replace("-","")));
            teamStartDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates),
                    ZoneOffset.UTC).toString().substring(0,10));
            teamStartDate.set(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(Integer.parseInt(startDate.replace("-",""))).toString().substring(0,10));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", teamStartDate.get());
        }
        else{
            String date=null;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", date);
        }

        if(endDate.equalsIgnoreCase("currentDate")){
            teamEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                    ZoneOffset.UTC).toString().substring(0,10));;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", teamEndDate.get());
        }
        else if(!endDate.isEmpty() && endDate.contains("+")){
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(Integer.parseInt(endDate.replace("+","")));
            teamEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates),
                    ZoneOffset.UTC).toString().substring(0,16));
            teamStartDate.set(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(Integer.parseInt(startDate.replace("+",""))).toString().substring(0,10));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", teamStartDate.get());
        }
        else if(!endDate.isEmpty() && endDate.contains("-")){
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(Integer.parseInt(endDate.replace("-","")));
            teamEndDate.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates),
                    ZoneOffset.UTC).toString().substring(0,16));
            teamStartDate.set(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(Integer.parseInt(startDate.replace("-",""))).toString().substring(0,10));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", teamStartDate.get());
        }
        else{
            String date=null;
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", date);
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toString()+"Z");
        teamCreatedOn.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0,16));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toString()+"Z");
        teamUpdatedOn.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0,16));

        teamCreatedBy.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("createdBy").toString().replaceAll("\"",""));
        teamUpdatedBy.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("updatedBy").toString().replaceAll("\"",""));

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @And("I run the create team API")
    public void i_run_the_create_team_api() throws Exception{
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I can get team API response status as {string} and error code as {string}")
    public void statusCheck(String status,String errorCode){
        switch(status){
            case "success":
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
                teamId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("teamRequest").get("teamId").getAsInt());
                break;

            case "fail":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "database transaction error");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("errorResponse").
                        get("errorCode").getAsString(), errorCode);
                break;

        }
    }

    @When("I initiate create team user api")
    public void initiateCreateTeamUserApi() throws Exception{
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createTeamUserEndPoint);
    }

    @When("I provide team user details")
    public void provideTeamUserDetails() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateTeamUser.json");

        String effectiveStartDate= LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0,16);

        JsonArray teamUserArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("teamUserVO");
        JsonObject teamUserObj = teamUserArray.get(0).getAsJsonObject();
        teamUserObj.addProperty("effectiveStartDate", effectiveStartDate);
        teamUserObj.addProperty("createdOn", effectiveStartDate);
        teamUserObj.addProperty("userId", listOfUserIds.get().get(0));
        teamUserObj.addProperty("teamId", teamId.get());
        Random r = new Random();
        int randomNum = 0+r.nextInt(1);
        if(randomNum==0)
            teamUserObj.addProperty("supervisorFlag", false);
        else
            teamUserObj.addProperty("supervisorFlag", true);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @And("I run create team user api")
    public void runCreateTeamUser() throws Exception{
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @When("I get user list for the {string}")
    public void getUserList(String projectId){

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUserListEndPoint);

        if(projectId.isEmpty()){
            projectId= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        JsonObject requestParams = new JsonObject();
        JsonObject userObj = new JsonObject();
        userObj.addProperty("projectId", projectId);
        requestParams.add("user", userObj);
        System.out.println(requestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        HashMap<String, Object> staffList = (HashMap<String, Object>) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("staffList");
        ArrayList<Object> contentList = (ArrayList)staffList.get("content");
        for(Object content:contentList){
            HashMap<String, Object> contentMap = (HashMap<String, Object>)content;
            ArrayList<Object> usersList = (ArrayList)contentMap.get("usersList");
            for(Object user:usersList){
                HashMap<String, Object> userMap = (HashMap<String, Object>)user;
                if(userMap.get("projectId").toString().equalsIgnoreCase(projectId)){
                    listOfUserIds.get().add(userMap.get("userId").toString());
                }
            }
        }

    }

    @When("I get team list for the {string}")
    public void getTeamList(String projectId){

        if(projectId.isEmpty()){
            projectId= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }
        getTeamsListEndPoint = getTeamsListEndPoint.replace("{projectId}", projectId);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTeamsListEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);


        ArrayList<Object> teamList = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("teamList");
        for(Object team:teamList){
            HashMap<String, Object> teamMap = (HashMap<String, Object>)team;
            if(teamMap.get("teamName").toString().equalsIgnoreCase(teamName.get())){
                teamId.set(Integer.parseInt(teamMap.get("teamId").toString()));
                break;
            }

        }
    }

    @Then("I verify team user is saved")
    public void validateTeamUser(){

        ArrayList<Object> teamUsers = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("teamUsers");
        for(Object teamUser:teamUsers){
            HashMap<String, Object> teamUserMap = (HashMap<String, Object>)teamUser;
            assertNotNull(teamUserMap.get("teamUserId").toString());
        }
        teamId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamUsers").get(0).getAsJsonObject().
                get("teamId").getAsInt());
        teamUserId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamUsers").get(0).getAsJsonObject().
                get("teamUserId").getAsInt());
        userId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamUsers").get(0).getAsJsonObject().
                get("userId").getAsInt());
        supervisorFlag.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamUsers").get(0).getAsJsonObject().
                get("supervisorFlag").getAsBoolean());
        teamCreatedBy.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamUsers").get(0).getAsJsonObject().
                get("createdBy").getAsString());
        teamCreatedOn.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamUsers").get(0).getAsJsonObject().
                get("createdOn").getAsString());
        effectiveStartDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamUsers").get(0).getAsJsonObject().
                get("effectiveStartDate").getAsString());
    }

    @Then("I verify created on and created by values in team user")
    public void validateTeamUserCreatedOnAndCreatedBy(){
        ArrayList<Object> teamUsers = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("teamUsers");
        for(Object teamUser:teamUsers){
            HashMap<String, Object> teamUserMap = (HashMap<String, Object>)teamUser;
            assertNotNull(teamUserMap.get("createdOn").toString());
            assertNotNull(teamUserMap.get("createdBy").toString());
        }
    }

    @When("I provide multiple team user details")
    public void provideMultipleTeamUserDetails() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiCreateTeamUser.json");

        String effectiveStartDate= LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0,16);

        JsonArray teamUserArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("teamUserVO");
        JsonObject teamUserObj = teamUserArray.get(0).getAsJsonObject();
        teamUserObj.addProperty("effectiveStartDate", effectiveStartDate);
        teamUserObj.addProperty("createdOn", effectiveStartDate);
        teamUserObj.addProperty("userId", listOfUserIds.get().get(0));
        teamUserObj.addProperty("teamId", teamId.get());
        teamUserObj.addProperty("supervisorFlag", true);

        Random r = new Random();
        int maxUserId = listOfUserIds.get().size();
        if(maxUserId>5)
            maxUserId=5;

        int numberOfUsersToAdd = 1+r.nextInt(maxUserId);

        for(int i=1;i<=numberOfUsersToAdd;i++){
            JsonObject teamUserObj1 = new JsonObject();
            teamUserObj1.addProperty("effectiveStartDate", effectiveStartDate);
            teamUserObj1.addProperty("createdOn", effectiveStartDate);
            teamUserObj1.addProperty("userId", listOfUserIds.get().get(i));
            teamUserObj1.addProperty("teamId", teamId.get());
            teamUserObj1.addProperty("supervisorFlag", false);
            teamUserObj1.addProperty("createdBy", teamUserObj.get("createdBy").toString());
            teamUserObj1.addProperty("updatedOn", (String) null);
            teamUserObj1.addProperty("updatedBy",  (String) null);
            teamUserArray.add(teamUserObj1);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @And("I provide team user update details")
    public void provideUpdateDetailsForTeamUser(){
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiUpdateTeamUser.json");
        JsonArray teamUserArray = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("teamUserVO");
        JsonObject teamUserObj = teamUserArray.get(0).getAsJsonObject();

        teamUserObj.addProperty("effectiveStartDate", effectiveStartDate.get());
        teamUserObj.addProperty("createdOn", teamCreatedOn.get());
        teamUserObj.addProperty("userId", userId.get());
        teamUserObj.addProperty("teamId", teamId.get());
        teamUserObj.addProperty("teamUserId", teamUserId.get());
        teamUserObj.addProperty("createdBy",teamCreatedBy.get());
        if(supervisorFlag.get()){
            supervisorFlag.set(false);
        }
         else{
             supervisorFlag.set(true);
        }
        teamUserObj.addProperty("supervisorFlag", supervisorFlag.get());
        teamUserObj.addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        teamUpdatedOn.set(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneOffset.UTC).toString().substring(0,16));
        teamUserObj.addProperty("updatedOn", teamUpdatedOn.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @Then("I verify updated on and updated by values in team user")
    public void validateTeamUserUpdatedOnAndUpdatedBy(){
        ArrayList<Object> teamUsers = (ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("teamUsers");
        for(Object teamUser:teamUsers){
            HashMap<String, Object> teamUserMap = (HashMap<String, Object>)teamUser;
            assertNotNull(teamUserMap.get("updatedOn").toString());
            assertNotNull(teamUserMap.get("updatedBy").toString());
            assertEquals(teamUserMap.get("updatedBy").toString(), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
            Assert.assertEquals(teamUserMap.get("updatedOn").toString().substring(0,10),teamUpdatedOn.get().substring(0,10));
        }
    }
}
