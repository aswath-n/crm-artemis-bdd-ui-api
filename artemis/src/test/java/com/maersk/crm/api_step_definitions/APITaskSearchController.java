package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static org.testng.Assert.*;

public class APITaskSearchController extends CRMUtilities implements ApiBase {
    private String taskSearchBaseURI = ConfigurationReader.getProperty("contactRecordSearchURI");
    private String taskSearchEndURI = "app/crm/search/taskrecords";
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);

    //Vidya-- Method to initiate put Inquiry request
    @And("I initiate task search post APi")
    public void initiateTaskSearchPostApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(taskSearchEndURI);
    }

    @When("I will provide below information to search a task")
    public void iCanprovideTaskDetails(Map<String, String> data) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/task/taskSearch.json");

        for (String key : data.keySet()) {
            if (key.equals("source") || key.equals("taskTypeId") || key.equals("taskStatus") || key.equals("defaultPriority"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray(key).add(data.get(key));
            else if (key.equals("staffAssignedTo"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(key, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
            else
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(key, data.get(key));
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @And("I run the task search post APi")
    public void i_run_the_create_inquiry_task_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify following information in task search response")
    public void iVerifyTheResponseStructure(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "moreResults":
                    if (data.get(key).equalsIgnoreCase("true"))
                        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get(key).getAsBoolean(), "More result indication is false");
                    else
                        assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get(key).getAsBoolean(), "More result indication is ture");
                    break;
                case "count":
                    if (data.get(key).equalsIgnoreCase("500"))
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get(key).getAsInt(), 500, "task search response has less then 500 records");
                    else
                        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get(key).getAsInt() < 500, "Task search response has more then 500 records");
                    break;
                case "status":
                    assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get(key).getAsString(), data.get(key), "Status is mismatch");
                    break;
                default:
                    assertTrue(false, "Switch case not match");
                    break;
            }
        }
    }
}
