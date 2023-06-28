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

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APITMListBusinessUnitController extends CRMUtilities implements ApiBase {

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
   // private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String getBusinessUnitEndPoint = "mars/tm/businessunit/{projectId}";
    //private String baseTMURI = ConfigurationReader.getProperty("apiTMURI");
    public static int businessUnit;
    public static ArrayList<Integer> businessUnitIDList;
    public static ArrayList<Integer> teamIDList;
    APITMAddBusinessUnitController addBuPutAPI= new APITMAddBusinessUnitController();

    @Given("I initiated Business Unit By Project ID via API {string}")
    public void runTheBusinessUnitGetAPI(String projectId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);

        if(projectId.isEmpty()){
            projectId= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
        }

        getBusinessUnitEndPoint = getBusinessUnitEndPoint.replace("{projectId}", projectId);
        System.out.println(getBusinessUnitEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getBusinessUnitEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @Then("I can verify Business Unit get API response will be {string}")
    public void iCanVerifyTheBUGetApiResponseStatus(String status) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), status);
    }

    @Then("I can verify each Business unit has all required information displayed")
    public void iCanVerifyRequiredInfoOnGetResults(){
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("businessUnitName"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("description"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("effectiveStartDate"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("effectiveEndDate"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("createdBy"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("updatedBy"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("businessUnitStatus"));
    }

    @And("I can verify Business unit {string} in get Api")
    public void verifyBUStatusInGetAPI(String buStatus){
        JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessUnits");
        for(int i=0;i<json.size();i++){
            JSONObject temp=new JSONObject(json.get(i).toString());
            if(temp.getInt("businessUnitId")==APITMAddBusinessUnitController.businessUnitId.get()){
                Assert.assertEquals(temp.getString("businessUnitStatus"),buStatus,"Status is not matched");
                break;
            }
            else if(i==json.size()-1){
                Assert.assertFalse(true,"No business unit with id "+
                        APITMAddBusinessUnitController.businessUnitId);
            }
        }
    }

    @And("I can verify all fields of business unit has proper value")
    public void verifyAllFieldsValueOfBusinessUnitGetApi(){
        JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessUnits");
        for(int i=0;i<json.size();i++){
            JSONObject temp=new JSONObject(json.get(i).toString());
            if(temp.getInt("businessUnitId")==APITMAddBusinessUnitController.businessUnitId.get()){
               Assert.assertEquals(temp.getString("businessUnitName"),
                       APITMAddBusinessUnitController.businessUnitName);
                Assert.assertEquals(temp.getString("description"),
                        APITMAddBusinessUnitController.businessUnitDesc);
                Assert.assertEquals(temp.getString("effectiveStartDate").substring(0,16),
                        APITMAddBusinessUnitController.buStartDate);
                if(!temp.isNull("effectiveEndDate")){
                    Assert.assertEquals(temp.getString("effectiveEndDate").substring(0,16),
                            APITMAddBusinessUnitController.buEndDate);
                }
                Assert.assertEquals(temp.getString("createdOn").substring(0,16),
                        APITMAddBusinessUnitController.buCreatedOn);
                Assert.assertEquals(temp.getString("updatedOn").substring(0,16),
                        APITMAddBusinessUnitController.buUpdatedOn);
                Assert.assertEquals(temp.getString("createdBy"),APITMAddBusinessUnitController.buCreatedBy);
                Assert.assertEquals(temp.getString("updatedBy"), APITMAddBusinessUnitController.buUpdatedBy);
                break;
            }
            else if(i==json.size()-1){
                Assert.assertFalse(true,"No business unit with id "+
                        APITMAddBusinessUnitController.businessUnitId);
            }
        }
    }

    @Given("I ensure Business Unit Get API has at least one business unit for {string}")
    public void verifyAtLeastOneBuHas(String projectId)throws Exception{
        runTheBusinessUnitGetAPI(projectId);
        iCanVerifyTheBUGetApiResponseStatus("success");
        JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessUnits");
        if(json.size()==0){
            addBuPutAPI.i_initiated_create_business_unit_api();
            addBuPutAPI.iCanprovideBUDetails(projectId,"{random}","{random}","currentDate","","");
            addBuPutAPI.i_run_the_create_business_unit_api();
            addBuPutAPI.statusCheck("success","");
            businessUnit=addBuPutAPI.businessUnitId.get();
        }
        else{
            for(int i=0;i<json.size();i++){
                JSONObject temp=new JSONObject(json.get(i).toString());
                if(temp.has("businessUnitName") && !temp.isNull("businessUnitName") &&
                        !temp.get("businessUnitName").toString().equals("")){
                    if(temp.has("effectiveStartDate") && !temp.isNull("effectiveStartDate") &&
                            !temp.get("effectiveStartDate").toString().equals("")){
                        businessUnit=temp.getInt("businessUnitId");
                        break;
                    }
                }
            }
        }
    }

    @When("I check if it has 5 business unit records if not then I create 5 records")
    public void create5BU() throws Exception{
        runTheBusinessUnitGetAPI("");
        iCanVerifyTheBUGetApiResponseStatus("success");
        JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessUnits");
        if(json.size()<5){
            String[] startDate={"+2","-2","+3","-3","+1"};
            String[] endDate={"","-2","","-3",""};
            addBuPutAPI.i_initiated_create_business_unit_api();
            for(int i=0;i<5;i++){
                addBuPutAPI.iCanprovideBUDetails
                        ("","{random}","{random}",startDate[i],endDate[i],"");
                addBuPutAPI.i_run_the_create_business_unit_api();
                addBuPutAPI.statusCheck("success","");
            }
        }
    }

    @And("I can verify in business unit GET API records are according to sorting order")
    public void verifyingSortingOrder(){
        boolean is = false;
        String topText;
        String nextBelowText;
        ArrayList<String> active=new ArrayList<>();
        ArrayList<String> future=new ArrayList<>();
        ArrayList<String> inActive=new ArrayList<>();
        JsonArray json= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessUnits");
        for(int i=0;i<json.size();i++){
            JSONObject temp=new JSONObject(json.get(i).toString());
            switch(temp.getString("businessUnitStatus")){
                case "active":
                    active.add(temp.getString("businessUnitName"));
                    break;
                case "future":
                    future.add(temp.getString("businessUnitName"));
                    break;
                case "inactive":
                    inActive.add(temp.getString("businessUnitName"));
                    break;
            }
            if (i + 1 == json.size()) {
                is = true;
                continue;
            }else {
                JSONObject temp1=new JSONObject(json.get(i+1).toString());
                topText = temp.getString("businessUnitStatus");
                nextBelowText = temp1.getString("businessUnitStatus");
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
        if(is){
            ArrayList<String> copy = new ArrayList(active);
            Collections.sort(copy,String.CASE_INSENSITIVE_ORDER);
            Assert.assertEquals(active,copy,"With in Active status records are not sorted according to Business Unit Name");

            copy = new ArrayList(future);
            Collections.sort(copy,String.CASE_INSENSITIVE_ORDER);
            Assert.assertEquals(future,copy,"With in Future status records are not sorted according to Business Unit Name");

            copy = new ArrayList(inActive);
            Collections.sort(copy,String.CASE_INSENSITIVE_ORDER);
            Assert.assertEquals(inActive,copy,"With in Inactive status records are not sorted according to Business Unit Name");
        }
    }

    @And("I Get all the active business units from API response")
    public void getAllTheActiveBusinessUnits(){
        businessUnitIDList = new ArrayList<>();
        JsonArray json1= API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessUnits");
        for(int i=0;i<json1.size();i++) {
            JSONObject temp1 = new JSONObject(json1.get(i).toString());
            if (temp1.getString("businessUnitStatus").equalsIgnoreCase("active"))
                businessUnitIDList.add(temp1.getInt("businessUnitId"));
        }
    }
}
