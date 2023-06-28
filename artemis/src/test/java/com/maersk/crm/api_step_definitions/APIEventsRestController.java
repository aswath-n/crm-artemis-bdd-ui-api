package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class APIEventsRestController  extends CRMUtilities implements ApiBase {
    private final ThreadLocal<String> baseEventsURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEventsURI"));
    private final ThreadLocal<String> searchEventsEndPoint = ThreadLocal.withInitial(()->"app/crm/events");

    private JsonObject requestParams;
    /*private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/

    @Given("I initiated search events api")
    public void initiateSearchEvents() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseEventsURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchEventsEndPoint.get());
    }

    @When("I can provide event information to search event with correlation id {string}")
    public void prepareRequestBodyWithCorrelationIdForSearchEvents(String caseCorrelationId) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("events/searchEvents.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", caseCorrelationId);
        requestParams = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @And("I can run search event API")
    public void runSearchEvent(){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify event name {string} and payload containd id {string} in events response ")
    public void verifyCaseIdInEvents(String eventName, String id){
        boolean recordFound = false;
        HashMap<String, Object> eventsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList");
        for(int i=0;i<((ArrayList)eventsList.get("content")).size();i++){
            HashMap<String, Object> content = (HashMap<String, Object>)((ArrayList)eventsList.get("content")).get(i);
            String actEventName = content.get("eventName").toString();
            String payload = content.get("payload").toString();
            if(eventName.equalsIgnoreCase(actEventName) && payload.contains(id))
                recordFound = true;
        }
        Assert.assertTrue(recordFound);

    }

    @Given("I can provide event information to search event as")
    public void i_can_provide_event_information_to_search_event_as(List<Map<String, String>> data) {
        Map<String, String> inputData = data.get(0);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement = new JsonObject();
        for (String key : inputData.keySet()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(key, inputData.get(key));
        }
        requestParams = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        System.out.println("Input body: " + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @Given("I can run search event API with query parameters")
    public void i_can_run_search_event_API_with_query_parameters(List<Map<String, String>> data) {
        Map<String, String> queryParams = data.get(0);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameterAndQuery(requestParams, queryParams);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }
}

