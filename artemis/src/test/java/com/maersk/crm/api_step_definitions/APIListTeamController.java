package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;

import static org.testng.Assert.*;

public class APIListTeamController extends CRMUtilities implements ApiBase {

    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String getTeamEndPoint = "mars/tm/teams/{projectId}/0";
    private APITMAddTeamController addTeamController = new APITMAddTeamController();
    APITMListBusinessUnitController buListController = new APITMListBusinessUnitController();
    public final ThreadLocal<Integer> teamId = ThreadLocal.withInitial(() -> 0);
    public static final ThreadLocal<ArrayList<Integer>> teamIDList = ThreadLocal.withInitial(ArrayList::new);

    @Given("I initiated Team By Project ID via API {string}")
    public void runTheTeamGetAPI(String projectId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);

        if (projectId.isEmpty()) {
            projectId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }

        getTeamEndPoint = getTeamEndPoint.replace("{projectId}", projectId);
        System.out.println(getTeamEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTeamEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @Then("I can verify Team get API response will be {string}")
    public void iCanVerifyTheTeamGetApiResponseStatus(String status) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), status);
    }

    @Then("I can verify each Team has all required information displayed")
    public void iCanVerifyRequiredInfoOnGetResults() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("teamName"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("description"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("effectiveStartDate"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("effectiveEndDate"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("createdBy"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("updatedBy"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("teamStatus"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("businessUnitName"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("businessUnitId"));
    }

    @And("I can verify Team {string} in get Api")
    public void verifyTeamStatusInGetAPI(String buStatus) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamList");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getInt("teamId") == APITMAddTeamController.teamId.get()) {
                assertEquals(temp.getString("teamStatus"), buStatus, "Status is not matched");
                break;
            } else if (i == json.size() - 1) {
                assertFalse(true, "No team with id " +
                        APITMAddTeamController.teamId.get());
            }
        }
    }

    @And("I can verify all fields of team has proper value")
    public void verifyAllFieldsValueOfTeamGetApi() {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamList");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getInt("teamId") == APITMAddTeamController.teamId.get()) {
                assertEquals(temp.getString("teamName"), APITMAddTeamController.teamName);
                assertEquals(temp.getString("description"), APITMAddTeamController.teamDesc);
                assertEquals(temp.getString("effectiveStartDate").substring(0, 16),
                        APITMAddTeamController.teamStartDate);
                if (!temp.isNull("effectiveEndDate")) {
                    assertEquals(temp.getString("effectiveEndDate").substring(0, 10),
                            APITMAddTeamController.teamEndDate);
                }
                assertEquals(temp.getString("createdOn").substring(0, 16), APITMAddTeamController.teamCreatedOn);
                assertEquals(temp.getString("updatedOn").substring(0, 16), APITMAddTeamController.teamUpdatedOn);
                assertEquals(temp.getString("createdBy"), APITMAddTeamController.teamCreatedBy);
                assertEquals(temp.getString("updatedBy"), APITMAddTeamController.teamUpdatedBy);
                assertEquals(temp.getInt("businessUnitId"), APITMListBusinessUnitController.businessUnit);
                break;
            } else if (i == json.size() - 1) {
                assertFalse(true, "No business unit with id " +
                        APITMAddTeamController.teamId.get());
            }
        }
    }

    @When("I check if it has 5 team records if not then I create 5 team records")
    public void create5Teams() throws Exception {
        runTheTeamGetAPI("");
        iCanVerifyTheTeamGetApiResponseStatus("success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamList");
        if (json.size() < 5) {
            String[] startDate = {"+2", "-2", "+3", "-3", "+1"};
            String[] endDate = {"", "-2", "", "-3", ""};
            buListController.verifyAtLeastOneBuHas("");
            addTeamController.i_initiated_create_team_api();
            for (int i = 0; i < 5; i++) {
                addTeamController.iCanProvideTeamDetails
                        ("", "{random}", "{random}", startDate[i], endDate[i], "");
                addTeamController.i_run_the_create_team_api();
                addTeamController.statusCheck("success", "");
            }
        }
    }

    @And("I can verify in team GET API teams are according to sorting order")
    public void verifyingSortingOrder() {
        boolean is = false;
        String topText;
        String nextBelowText;
        ArrayList<String> active = new ArrayList<>();
        ArrayList<String> future = new ArrayList<>();
        ArrayList<String> inActive = new ArrayList<>();
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamList");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            switch (temp.getString("teamStatus")) {
                case "active":
                    active.add(temp.getString("teamName"));
                    break;
                case "future":
                    future.add(temp.getString("teamName"));
                    break;
                case "inactive":
                    inActive.add(temp.getString("teamName"));
                    break;
            }
            if (i + 1 == json.size()) {
                is = true;
                continue;
            } else {
                JSONObject temp1 = new JSONObject(json.get(i + 1).toString());
                topText = temp.getString("teamStatus");
                nextBelowText = temp1.getString("teamStatus");
                if (topText.equals("") || nextBelowText.equals("")) {
                    is = true;
                    continue;
                } else {
                    if (topText.charAt(0) == nextBelowText.charAt(0)) {
                        is = true;
                    } else {
                        is = (topText.charAt(0) < nextBelowText.charAt(0));
                    }
                }
            }
        }
        if (is) {
            ArrayList<String> copy = new ArrayList(active);
            Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
            assertEquals(active, copy, "With in Active status records are not sorted according to Team Name");

            copy = new ArrayList(future);
            Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
            assertEquals(future, copy, "With in Future status records are not sorted according to Team Name");

            copy = new ArrayList(inActive);
            Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
            assertEquals(inActive, copy, "With in Inactive status records are not sorted according to Team Name");
        }
    }

    @Given("I ensure Team Get API has at least one team record for {string}")
    public void verifyAtLeastOneTeamHas(String projectId) throws Exception {
        runTheTeamGetAPI(projectId);
        iCanVerifyTheTeamGetApiResponseStatus("success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamList");
        if (json.size() == 0) {
            addTeamController.i_initiated_create_team_api();
            addTeamController.iCanProvideTeamDetails(projectId, "{random}", "{random}", "currentDate", "", buListController.businessUnit + "");
            addTeamController.i_run_the_create_team_api();
            addTeamController.statusCheck("success", "");
        }

    }

    @Given("I ensure Team Get API has at least one team user record for {string}")
    public void verifyAtLeastOneTeamUserHas(String projectId) throws Exception {
        runTheTeamGetAPI(projectId);
        iCanVerifyTheTeamGetApiResponseStatus("success");
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamList");
        boolean flag = true;
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getJSONArray("users").length() != 0) {
                addTeamController.teamId.set(temp.getJSONArray("users").
                        getJSONObject(0).getJSONObject("teamUSer").getInt("teamId"));
                addTeamController.teamUserId.set(temp.getJSONArray("users").
                        getJSONObject(0).getJSONObject("teamUSer").getInt("teamUserId"));
                addTeamController.userId.set(temp.getJSONArray("users").
                        getJSONObject(0).getJSONObject("teamUSer").getInt("userId"));
                addTeamController.supervisorFlag.set(temp.getJSONArray("users").
                        getJSONObject(0).getJSONObject("teamUSer").getBoolean("supervisorFlag"));
                addTeamController.effectiveStartDate.set(temp.getJSONArray("users").
                        getJSONObject(0).getJSONObject("teamUSer").getString("effectiveStartDate"));
                addTeamController.teamCreatedOn.set(temp.getJSONArray("users").
                        getJSONObject(0).getJSONObject("teamUSer").getString("createdOn"));
                addTeamController.teamCreatedBy.set(temp.getJSONArray("users").
                        getJSONObject(0).getJSONObject("teamUSer").getString("createdBy"));
                flag = false;
                break;
            }
            if (flag) {
                addTeamController.teamId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamList").
                        get(0).getAsJsonObject().get("teamId").getAsInt());
                //addTeamController.getTeamList(projectId);
                addTeamController.getUserList(projectId);
                addTeamController.initiateCreateTeamUserApi();
                addTeamController.provideTeamUserDetails();
                addTeamController.runCreateTeamUser();
                addTeamController.validateTeamUser();
            }
        }
    }

    @And("I Get all the active team Ids from API response")
    public void getAllTheActiveTeamIds() {
        teamIDList.set(new ArrayList<>());
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("teamList");
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (APITMListBusinessUnitController.businessUnitIDList.contains(temp.getInt("businessUnitId")) && temp.getString("teamStatus").equals("active")) {
                teamIDList.get().add(temp.getInt("teamId"));
            }
        }
        System.out.println(teamIDList.get());
    }

}
