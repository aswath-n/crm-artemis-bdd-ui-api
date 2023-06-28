package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class APITMHolidayController extends CRMUtilities implements ApiBase {
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private final ThreadLocal<JSONObject> requestParams =ThreadLocal.withInitial(JSONObject::new);
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String holidayGetEndPoint = "/mars/tm/project/holiday/holidaylist/";
    public final ThreadLocal<String> projectId = ThreadLocal.withInitial(String::new);
    public static ArrayList<String> holiday=new ArrayList();

    @Given("I initiated holyday GET API for {string} and {string}")
    public void i_initiated_create_business_unit_api(String projectId, String year) throws Exception{
        //throw new Exception("Step Disabled untill further notification");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if(projectId.isEmpty()){
            this.projectId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiprojectId);
        }
        else if(projectId.equals("BLCRM")){
            this.projectId.set(ConfigurationReader.getProperty("projectName").split(" ")[0]);
        }
        else if(projectId.equals("GACRM")){
            this.projectId.set(ConfigurationReader.getProperty("gaProjectName").split(" ")[0]);
        }
        else if(projectId.equals("CoverVA")){
            this.projectId.set(ConfigurationReader.getProperty("coverVAProjectName").split(" ")[0]);
        }
        else if(projectId.equals("IN-EB")) {
            this.projectId.set(ConfigurationReader.getProperty("INEBProjectName").split(" ")[0]);
        }
        else if(projectId.equals("NJ-SBE")) {
            this.projectId.set(ConfigurationReader.getProperty("njProjectName").split(" ")[0]);
        }
        String holidayGetEndPoint1=holidayGetEndPoint;
        holidayGetEndPoint1=holidayGetEndPoint1+this.projectId.get()+"/"+year;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(holidayGetEndPoint1);
    }

    @When("I run the holiday GET API")
    public void runTheBusinessUnitGetAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");
    }

    public void getHolidayList(){
        holiday=new ArrayList<>();
        if(!API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("holidaysList").isJsonNull()){
            JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("holidaysList");
            for(int i=0;i<json.size();i++){
                JSONObject temp=new JSONObject(json.get(i).toString());
                holiday.add(temp.getString("holidayDate"));
            }
            System.out.println(holiday);
        }
    }

    @And("I verify response has all the valid data")
    public void verifyAllFieldsValueOfHolidayGetApi(){
        String[] selectDate={"2020-11-11","2020-11-26","2020-07-03","2020-11-27","2020-05-25","2020-04-10",
        "2020-09-07","2020-10-12","2020-12-24"};
        String[] holidayName={"Veterans Day","Thanksgiving Day ","4th of July","State Holiday","Memorial Day",
                "State Holiday","Labor Day","Columbus Day","Washington’s Birthday"};

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("holidayYear").toString(), "2020","Yesr is not 2020");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("period").toString(), "1/1/2020-12/31/2020",
                "Year period is not matching");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("projectId").toString(), this.projectId, "ProjectId is wrong");
        JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("holidaysList");

        for(int i=0;i<json.size();i++){
            JSONObject temp=new JSONObject(json.get(i).toString());
            Assert.assertEquals(temp.getString("holidayName"),holidayName[i],"Holiday Name is wrong");
            Assert.assertEquals(temp.getString("holidayDate").
                    substring(0,temp.getString("holidayDate").indexOf("T")),selectDate[i],"Holiday date is wrong");
            Assert.assertFalse(temp.getBoolean("excludeForTaskInd"),"excludeForTaskInd value is true");
            Assert.assertFalse(temp.getBoolean("excludeForSrInd"),"excludeForSrInd value is true");
        }
    }
}
