package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class APIIVRRestController extends CRMUtilities implements ApiBase {
    private final ThreadLocal<String> baseURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiIVRURI"));
    private final ThreadLocal<String> ivrResource = ThreadLocal.withInitial(()->"mars/ivr/consumer/getDnis?apikey=BvRmuodYbL23mAWIwfv4CESavHzOMbuW");

    /*private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/
    private final ThreadLocal<JsonObject> ivrApiParams = ThreadLocal.withInitial(JsonObject::new);

    @Given("I initiated ivr authentication API")
    public void initiateCtiScreenPopApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(ivrResource.get());
    }

    @When("I can provide ivr authentication information {string} and {string}")
    public void provideCtiScreenPopInfo(String ssn, String dob){
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("ivr/apiAuthenticate.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("ssn", ssn);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dob", dob);

        ivrApiParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(ivrApiParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(ivrApiParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify the response status code for ivr api")
    public void verifyCtiScreenPopApiResponse(){
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify the response body has {string}, {string} and {string}")
    public void verifyStatusSsnDob(String status, String ssn, String dob){
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("status").toString(), status);
        ArrayList consumer =  API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("consumer");
        HashMap<String, String> consumerMap = (HashMap<String, String>) consumer.get(0);
        assertEquals(consumerMap.get("consumerSSN"), ssn);
        assertEquals(consumerMap.get("consumerDateOfBirth"), dob);
    }
}
