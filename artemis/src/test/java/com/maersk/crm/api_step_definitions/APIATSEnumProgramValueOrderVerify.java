package com.maersk.crm.api_step_definitions;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.util.*;

public class APIATSEnumProgramValueOrderVerify extends CRMUtilities implements ApiBase {
//    private final APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private final String baseLookUpUrl = ConfigurationReader.getProperty("apiLookupURI");
    private final String endPointEnumProg = "/mars/common/lookup";
    private final String apiProjectName = ConfigurationReader.getProperty("apiProjectNameBL");

    private final String baseAppDataUrl = ConfigurationReader.getProperty("apiApplicationDataServices");
    private final String endPointScope = "/scope";

    private final String baseAppLookupUrl = ConfigurationReader.getProperty("apiATSDataServices");
    private final String baseMILookupUrl = ConfigurationReader.getProperty("apiMissingInfoAPi");
    private final String endAppLookup = "/app/crm/lookup";
    @Given("I send Get request for ATS Lookup API with tableName {string} and bservice {string}")
    public void i_send_Get_request_for_ATS_Lookup_API_with_tableName_and_bservice(String tableName, String bservice) {
        switch (bservice){
            case "applicationdata":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAppLookupUrl);
                break;
            case "missinginformation":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseMILookupUrl);
                break;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endAppLookup);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("tableName", tableName);
      //queryParams.put("bservice", bservice);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPIAndQuery(queryParams);
    }

    @Given("I send Get request for ATS Lookup API with tableName {string}")
    public void i_send_Get_request_for_ATS_Lookup_API_with_tableName(String tableName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAppLookupUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endAppLookup);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("includeEndDateInd", "true");
        queryParams.put("tableName", tableName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPIAndQuery(queryParams);
    }

    @Given("I initialize and send application scope API for status {string}")
    public void i_initialize_and_send_application_scope_api_for_status(String status){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAppDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endPointScope+"/"+status);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @Then("the call returns list of values for application scope as {string}")
    public void the_call_returns_list_of_values_for_application_scope_as(String expectedData) {
        System.out.println("Status code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        String actualScope = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data").toString();
        Assert.assertEquals(expectedData, actualScope);
    }
    @Then("The call returns list of values for application intake {string} as")
    public void the_call_returns_list_of_values_for_application_intake_as(String tableName, List<Map<String, String>> expectedData) {
        System.out.println("Status code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        List<LinkedHashMap> actualEnumPrograms = (ArrayList<LinkedHashMap>) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.as(Map.class).get(tableName);

        Assert.assertEquals(actualEnumPrograms.size(),expectedData.size(), "Expected count of programs is: " + expectedData.size());

        for (Map<String, String> expectedProgram : expectedData) {
            Map<String, Object> actualProgram = getProgramByOrderByDefault(expectedProgram.get("orderbyDefault"), actualEnumPrograms);

            Assert.assertEquals(expectedProgram.get("orderbyDefault"), String.valueOf(actualProgram.get("orderbyDefault")));
            Assert.assertEquals(expectedProgram.get("value"), String.valueOf(actualProgram.get("value")));
            Assert.assertEquals(expectedProgram.get("description"), String.valueOf(actualProgram.get("description")));
            Assert.assertEquals(expectedProgram.get("reportLabel"), String.valueOf(actualProgram.get("reportLabel")));
            Assert.assertEquals(expectedProgram.get("scope").toString(), String.valueOf(actualProgram.get("scope")));
            }
        }

    @Then("Call returns list of values for application intake as")
    public void call_returns_list_of_values_for_application_intake_as(List<Map<String, String>> expectedData) {
        System.out.println("Status code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        List<LinkedHashMap> actualEnumPrograms = (ArrayList<LinkedHashMap>) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.as(Map.class).get("ENUM_PROGRAMS");

        Assert.assertEquals(expectedData.size(), actualEnumPrograms.size(), "Expected count of programs is: " + expectedData.size());

        for (Map<String, String> expectedProgram : expectedData) {
            Map<String, Object> actualProgram = getProgramByOrderByDefault(expectedProgram.get("orderbyDefault"), actualEnumPrograms);
            Assert.assertEquals(expectedProgram.get("orderbyDefault"), actualProgram.get("orderbyDefault").toString());
            Assert.assertEquals(expectedProgram.get("value"), actualProgram.get("value").toString());
            Assert.assertEquals(expectedProgram.get("description"), actualProgram.get("description").toString());
            Assert.assertEquals(expectedProgram.get("reportLabel"), actualProgram.get("reportLabel").toString());
            Assert.assertEquals(expectedProgram.get("scope"), actualProgram.get("scope").toString());
        }
    }

    /**
     * This method to get Program by orderByDefault
     *
     * @param orderByDefault
     * @param programs
     * @return
     */
    private Map<String, Object> getProgramByOrderByDefault(String orderByDefault, List<LinkedHashMap> programs) {
        for (LinkedHashMap<String, Object> program : programs) {
            if (program.get("orderbyDefault").toString().equals(orderByDefault)) {
                return program;
            }
        }
        throw new RuntimeException("Program with orderbyDefault: " + orderByDefault + " does not exist");
    }

    @Then("Call returns list of values for application status {string} as")
    public void Call_returns_list_of_values_for_application_status_as(String tableName, List<Map<String, String>> expectedData) {
        System.out.println("Status code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        List<LinkedHashMap> actualEnumStatus = (ArrayList<LinkedHashMap>) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.as(Map.class).get(tableName);

        for (Map<String, String> expectedStatus : expectedData) {
            Map<String, Object> actualProgram = getStatusByValue(expectedStatus.get("value"), actualEnumStatus);

            Assert.assertEquals(expectedStatus.get("value"), String.valueOf(actualProgram.get("value")));
            Assert.assertEquals(expectedStatus.get("description"), String.valueOf(actualProgram.get("description")));
            Assert.assertEquals(expectedStatus.get("reportLabel"), String.valueOf(actualProgram.get("reportLabel")));
            Assert.assertEquals(expectedStatus.get("scope").toString(), String.valueOf(actualProgram.get("scope")));
        }
    }

    private Map<String, Object> getStatusByValue(String value, List<LinkedHashMap> statuses) {
        for (LinkedHashMap<String, Object> appStatus : statuses) {
            if (appStatus.get("value").toString().equals(value)) {
                return appStatus;
            }
        }
        throw new RuntimeException("Application Status with value: " + value + " does not exist");
    }


}
