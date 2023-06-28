package com.maersk.crm.api_step_definitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/30/2020
 */
public class APITMEditPlanContactDetails extends CRMUtilities implements ApiBase {
    //private api api = new api();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private static String planManagementBaseURI = ConfigurationReader.getProperty("apiPlanManagementURI");
    private String getPlanNames = "mars/eb/plan/plannames";
    private String searchplans = "mars/eb/plan/plans";
    private String updatePlan = "mars/eb/plan/planupdate";
    private String getplandetails = "mars/eb/plan/plans/v1";
    private static String lookUpCon = ConfigurationReader.getProperty("apiLookupURI");
    private String dataBase = "mars/common/lookup/tables";
    private String tableName = "mars/common/lookup";
    private String addLookUp = "mars/common/lookup/enum?bservice=consumer";

    @Given("I initiated Plan Management for Plan Search")
    public void SearchPlanManagment() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchplans);
    }

    @Given("I initiated Plan Management for Plan Update")
    public void setPlanManagementUpdatePlan() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updatePlan);
    }

    @And("User send Api call with payload {string} for Plan search")
    public void planSearch(String payload, Map<String, String> data) throws JsonProcessingException {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/PlanController/"+payload+".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json,data));
    }

    @And("User send Api call with payload {string} for Plan Update")
    public void updatePlan (String payload, Map<String, String> data) throws JsonProcessingException {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/PlanController/"+payload+".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json,data));
    }

    @Then("I verify the response status code {int} and status {string}")
    public void i_verify_the_response_status_code_with_creation_status(int statusCode, String messageCreation) {
        Assert.assertEquals(statusCode, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(messageCreation));
    }

    @Given("I initiated Plan Management for getting plan details")
    public void getdetailsPlanManagment() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getplandetails);
    }

    @And("User send Api call with payload {string} for getting plan details")
    public void getplandetails(String payload, Map<String, String> data) throws JsonProcessingException {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/PlanController/"+payload+".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(json);
    }

    @Then("I verify the plan details response")
    public void i_verify_the_response1(Map<String, String> fields) throws Throwable {
        String temp=null;

        for (String key : fields.keySet()) {
            System.out.println(key);
            temp=fields.get(key);
            if(fields.get(key).isEmpty()  )
                temp=null;
            Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.data[0]."+key),temp);
        }

    }

    @Then("Verify Plan details response for {string}")
    public void verifyPlanDetailsResponse(String plan) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.mcoContracts[0].planName"),plan);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.mcoContracts[0].planShortName"),plan);
    }

    @Then("Verify provider search results sorted from nearest to farthest")
    public void verify_provider_search_results_sorted_from_nearest_to_farthest() {
        float count = Float.parseFloat(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.providerSearchResponse.numberOfElements"));
        List<Double> list1 = new ArrayList<Double>();
        List<Double> list2 = new ArrayList<Double>();
        for (int i = 0; i < count; i++) {
            String temp  = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.providerSearchResponse.content[" + i + "].providerAddress[0].normalizedPhysicalAddress.distance");
            if (temp == null)
                temp  = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.providerSearchResponse.content[" + i + "].providerAddress[1].normalizedPhysicalAddress.distance");
            Double f = Double.parseDouble(temp);
            Assert.assertTrue(f <= 15.0);
            list1.add(f);
            list2.add(f);
        }
        Collections.sort(list2);
        Assert.assertEquals(list1, list2);
    }


    @Then("Verify provider search results displaying values")
    public void verify_provider_search_results_displaying_values(Map<String, String> fields) {
        float count = Float.parseFloat(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.providerSearchResponse.numberOfElements"));
        String value = null;
        for (String key : fields.keySet()) {
            for (int i = 0; i < count; i++) {
                value = fields.get(key);
                if (fields.get(key).isEmpty())
                    value = null;
                Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.providerSearchResponse.content[" + i + "]." + key), value);
            }
        }
    }
}
