package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.*;

import static org.testng.Assert.*;

public class APITMBusinessDayRestController extends CRMUtilities implements ApiBase {

    private String baseURI = ConfigurationReader.getProperty("apiTMURI");
    private String businessDayGetEndPoint = "/mars/tm/businessdays/project/";
    private String businessDayPutEndPoint = "/mars/tm/businessdays";
    private final ThreadLocal<String> projectId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<ArrayList<String>> businessDay = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal<String> date1 = ThreadLocal.withInitial(()->API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("current"));
    final ThreadLocal<String> requestParams = ThreadLocal.withInitial(String::new);
    final ThreadLocal<HashMap<String, Object>> businessDayRecord = ThreadLocal.withInitial(HashMap::new);

    @Given("I initiated business day GET API for {string}")
    public void i_initiated_create_business_unit_api(String projectName) throws Exception {
        //throw new Exception("Step Disabled untill further notification");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (projectName.isEmpty() || projectName.equals("BLCRM")) {
            this.projectId.set(ConfigurationReader.getProperty("projectName").split(" ")[0]);
        } else if (projectName.equals("NJ-SBE")) {
            this.projectId.set(ConfigurationReader.getProperty("njProjectName").split(" ")[0]);
        } else if (projectName.equals("CoverVA")) {
            this.projectId.set(ConfigurationReader.getProperty("coverVAProjectName").split(" ")[0]);
        } else if (projectName.equals("IN-EB")) {
            this.projectId.set(ConfigurationReader.getProperty("INEBProjectName").split(" ")[0]);
        }
        String buEndPoint = businessDayGetEndPoint;
        buEndPoint = buEndPoint + this.projectId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(buEndPoint);
    }

    @When("I run the business day GET API")
    public void runTheBusinessUnitGetAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").toString(), "success");
    }

    @And("I verify response of business day get api has all the valid data")
    public void verifyAllFieldsValueOfHolidayGetApi() throws ParseException {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessDays");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            assertTrue(temp.getInt("businessDaysId") != 0, "businessDaysId is wrong");
            assertEquals(temp.getInt("projectId") + "", projectId.get(), "projectId is wrong");
            assertTrue(!temp.isNull("businessDayName") && !temp.getString("businessDayName").isEmpty(),
                    "businessDayName value is wrong");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("startDate")), "startDate value is wrong");
            assertTrue(temp.isNull("endDate") || API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDateFormat(temp.getString("endDate")),
                    "endDate value is wrong");
            assertTrue(!temp.isNull("businessDays") && !temp.getString("businessDays").isEmpty(),
                    "businessDays value is wrong");
            assertTrue(temp.getBoolean("taskIndicator") || !temp.getBoolean("taskIndicator"),
                    "taskIndicator value is wrong");
            assertTrue(temp.getBoolean("srIndicator") || !temp.getBoolean("srIndicator"),
                    "srIndicator value is wrong");
            assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                    "createdOn date time field IS NOT valid");
            assertTrue(EventsUtilities.isValidDate(temp.getString("updatedOn")),
                    "updatedOn date time field IS NOT valid");
            assertTrue(!temp.getString("createdBy").isEmpty() || !temp.isNull("createdBy"),
                    "createdBy value is wrong");
            assertTrue(!temp.getString("updatedBy").isEmpty() || !temp.isNull("updatedBy"),
                    "updatedBy value is wrong");
        }
    }

    @Given("I initiated business day put API")
    public void i_initiated_create_business_bay_put_api() throws Exception {
        //throw new Exception("Step Disabled untill further notification");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(businessDayPutEndPoint);
    }

    @When("I Provide a body to create business day record")
    public void provideTInformationToBusinessDayRecord() {
        JSONArray json = new JSONArray();

        JSONObject temp = new JSONObject();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
        businessDayRecord.get().put("businessDayName", "BUDay" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        temp.put("businessDayName", businessDayRecord.get().get("businessDayName"));

        temp.put("businessDays", "[\"MON\",\"TUE\",\"WED\",\"THU\",\"FRI\"]");
        businessDayRecord.get().put("businessDays", "[\"MON\",\"TUE\",\"WED\",\"THU\",\"FRI\"]");

        temp.put("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("current"));
        businessDayRecord.get().put("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("current"));

        temp.put("endDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("currentPlusOne"));
        businessDayRecord.get().put("endDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("currentPlusOne"));

        temp.put("taskIndicator", false);
        temp.put("srIndicator", false);

        this.projectId.set(ConfigurationReader.getProperty("tmRegressionProjectName").split(" ")[0]);
        temp.put("projectId", projectId.get());
        businessDayRecord.get().put("projectId", projectId.get());

        temp.put("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        businessDayRecord.get().put("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        temp.put("createdOn", System.currentTimeMillis());

        temp.put("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        businessDayRecord.get().put("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        temp.put("updatedOn", System.currentTimeMillis());
        json.put(temp);

        requestParams.set(json.toString());
    }

    @Then("I run the business day Put API")
    public void i_run_the_incomplete_task_api() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify the error message for duplicate name business day Put API")
    public void i_run_the_incomplete_task_api_with_error() throws Exception {
        JsonParser parser = new JsonParser();
        String businessDayName = parser.parse(requestParams.get()).getAsJsonArray().get(0).getAsJsonObject().get("businessDayName").getAsString();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("errorResponse").get("errorMessage").getAsString(), "BusinessName " + businessDayName + " already exists");
    }

    @Then("I verify response of business day put api")
    public void verifyResponseOfBusinessDayPutAPI() {
        JSONObject temp = new JSONObject(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data").toString());

        if (businessDayRecord.get().containsKey("businessDaysId")) {
            assertEquals(temp.getInt("businessDaysId") + "", businessDayRecord.get().get("businessDaysId").toString(),
                    "businessDaysId is mismatch");
        } else {
            assertTrue(temp.getInt("businessDaysId") != 0, "businessDaysId is mismatch");
            businessDayRecord.get().put("businessDaysId", temp.getInt("businessDaysId"));
        }
        assertEquals(temp.getInt("projectId") + "", businessDayRecord.get().get("projectId"),
                "projectId is mismatch");
        assertEquals(temp.getString("businessDayName"), businessDayRecord.get().get("businessDayName"),
                "businessDayName is mismatch");
        assertEquals(temp.getString("startDate"), businessDayRecord.get().get("startDate"),
                "startDate is mismatch");
        assertEquals(temp.getString("endDate"), businessDayRecord.get().get("endDate"),
                "endDate is mismatch");
        assertEquals(temp.getString("businessDays"), businessDayRecord.get().get("businessDays"),
                "businessDays is mismatch");
        assertFalse(temp.getBoolean("taskIndicator"), "taskIndicator is mismatch");
        assertFalse(temp.getBoolean("srIndicator"), "srIndicator is mismatch");
        assertEquals(temp.getString("createdBy"), businessDayRecord.get().get("createdBy"),
                "createdBy is mismatch");
        assertEquals(temp.getString("updatedBy"), businessDayRecord.get().get("updatedBy"),
                "updatedBy is mismatch");
        assertTrue(EventsUtilities.isValidDate(temp.getString("createdOn")),
                "createdOn date time field IS NOT valid");
        businessDayRecord.get().put("createdOn", temp.getString("createdOn"));
    }

    @When("I provide a body to update the business day record")
    public void updateInformationToBusinessDayRecord() {
        JSONArray json = new JSONArray();

        JSONObject temp = new JSONObject();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5);
        businessDayRecord.get().put("businessDayName", "BUDayUpdate" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        temp.put("businessDayName", businessDayRecord.get().get("businessDayName"));
        temp.put("businessDaysId", businessDayRecord.get().get("businessDaysId"));

        businessDayRecord.get().put("businessDays", "[\"MON\",\"TUE\",\"WED\",\"THU\"]");
        temp.put("businessDays", businessDayRecord.get().get("businessDays"));
        temp.put("startDate", businessDayRecord.get().get("startDate"));
        temp.put("endDate", businessDayRecord.get().get("endDate"));
        temp.put("taskIndicator", false);
        temp.put("srIndicator", false);
        temp.put("projectId", businessDayRecord.get().get("projectId"));
        temp.put("createdBy", businessDayRecord.get().get("createdBy"));
        temp.put("createdOn", businessDayRecord.get().get("createdOn"));
        businessDayRecord.get().put("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        temp.put("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        temp.put("updatedOn", System.currentTimeMillis());
        json.put(temp);

        requestParams.set(json.toString());
    }

    @And("I will get the Time Frame Configured for project")
    public void getTimeFrameConfiguredForProject() throws ParseException {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("businessDays");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().dateComparision(date1.get(), temp.getString("startDate")) &&
                    (temp.isNull("endDate") || API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().dateComparision(temp.getString("endDate"), date1.get()))) {
                String[] str = temp.getString("businessDays").replaceAll("\\\"", "").
                        replaceAll("\\\"", "").replace("[", "").replace("]", "" +
                                "").replace("MON", "MONDAY").replace("TUE", "TUESDAY").replace("WED", "WEDNESDAY").
                        replace("THU", "THURSDAY").replace("FRI", "FRIDAY").replace("SAT", "SATURDAY").
                        replace("SUN", "SUNDAY").replaceAll(" ", "").split(",");
                businessDay.set(new ArrayList<>(Arrays.asList(str)));
                break;
            }
        }
        if (businessDay.get().size() == 0 || businessDay.get().get(0).isEmpty())
            businessDay.set(new ArrayList<>(Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY")));
        System.out.println(businessDay.get());
    }
}
