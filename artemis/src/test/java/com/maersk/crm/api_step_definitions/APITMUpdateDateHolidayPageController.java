package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APITMUpdateDateHolidayPageController extends CRMUtilities implements ApiBase {

    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String holidaySaveEndPoint = "mars/tm/project/holiday/save";
    private String holidayGetEndPoint = "/mars/tm/project/holiday/holidaylist/";
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);

    final ThreadLocal<String> projectId = ThreadLocal.withInitial(String::new);

    @When("I added the holiday for a project")
    public void addHolidayForProject() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(holidaySaveEndPoint);

        requestParams.set(getHolidaySaveParamters());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.print("Response for Holiday Save: ");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");
    }

    @And("I initiated holiday GET API for {string} and {string}")
    public void initiateHolidatGetAPI(String projectId, String year) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (projectId.isEmpty()) {
            this.projectId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        } else if (projectId.equals("BLCRM")) {
            this.projectId.set(ConfigurationReader.getProperty("projectName").split(" ")[0]);
        }
        holidayGetEndPoint = holidayGetEndPoint + this.projectId + "/" + year;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(holidayGetEndPoint);
    }

    @When("I run the holiday GET API for update Holiday page")
    public void runHolidayAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");
    }

    @Then("I verify date is yyyy-mm-dd format in the response")
    public void verifyDateFormat() {
        List holidaysList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("holidaysList");
        assertTrue(holidaysList.size() > 0);

        for (int i = 0; i < holidaysList.size(); i++) {
            HashMap holiday = (HashMap) holidaysList.get(i);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(holiday.get("holidayDate").toString()));
        }
    }

    private JsonObject getHolidaySaveParamters() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiUpdateHolidayPage.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("holidaysList").get(12).getAsJsonObject().addProperty("holidayDate", "2020-07-23");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("holidaysList").get(12).getAsJsonObject().addProperty("holidayName", "test");

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }
}
