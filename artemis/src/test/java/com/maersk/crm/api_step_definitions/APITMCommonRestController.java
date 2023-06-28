package com.maersk.crm.api_step_definitions;

import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.google.gson.JsonObject;

import com.maersk.crm.utilities.ConfigurationReader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APITMCommonRestController extends CRMUtilities implements ApiBase {

    private final ThreadLocal<String> maxId = ThreadLocal.withInitial(String::new);
    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String getMaxIDEndPoint = "mars/tm/common/activedirectory/{maxId}";

    @Given("I initiated get employee detail by MaxID {string}")
    public void i_initiated_get_employee_detail_by_maxid(String maerskID) {
        if (maerskID != null && !maerskID.isEmpty()) {
            maxId.set(maerskID);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getMaxIDEndPoint = getMaxIDEndPoint.replace("{maxId}", maxId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getMaxIDEndPoint);
    }

    @When("I run the employee detail API by MaxID")
    public void i_run_the_employee_detail_api_by_max_id() {
        String url = baseURI + "/" + getMaxIDEndPoint;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Request:");
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
//        assertTrue(api.responseString.contains("success"));
//        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @Then("I can verify ge user approver detail API response will be {string}")
    public void i_can_verify_get_employee_detail_api_response(String success) {
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
        }
    }
}
