package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APIEnumsController extends CRMUtilities implements ApiBase {
    private final ThreadLocal<String> enumBaseURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiLookupURI"));
    private final ThreadLocal<String> enumNewBaseURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiEnrollment"));
    private final ThreadLocal<String> enumEndURI = ThreadLocal.withInitial(()->"/mars/common/lookup?tableName={tableName}&bservice={serviceName}");
    private final ThreadLocal<String> enumNewEndURI = ThreadLocal.withInitial(()->"/app/crm/lookup");
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    final ThreadLocal<ArrayList<String>> actual=ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> expected=ThreadLocal.withInitial(ArrayList::new);

    @Given("I initiated enum get api for {string} and {string}")
    public void initiateGetEnumApi(String tableName,String serviceName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enumBaseURI.get());
        enumEndURI.set(enumEndURI.get().replace("{tableName}",tableName).replace("{serviceName}",serviceName));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(enumEndURI.get());
    }

    @When("I run enum get api")
    public void iRunTheEnumGetAPI() throws Exception {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify action taken enum values for task type {string}")
    public void verifyActionTakenEnumValues(String taskType) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("ENUM_ACTION_TAKEN");
        actual.set(new ArrayList<>());

        switch (taskType){
            case "Review Appeal Form":
                String[] strNJ={"No Action Taken", "Outbound Call Successful","Outbound Call Unsuccessful"};
                String[] strBlcrm={"Acknowledgement Letter Generated", "IDR Resolved Letter Generated","No Action Taken",
                        "Outbound Call Successful", "Outbound Call Unsuccessful"};

                expected.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equalsIgnoreCase("BLCRM")? new ArrayList<>(Arrays.asList(strBlcrm)) :
                        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equalsIgnoreCase("NJ-SBE")? new ArrayList<>(Arrays.asList(strNJ)): null);
                for (int i = 0; i < json.size(); i++) {
                    JSONObject temp = new JSONObject(json.get(i).toString());
                    if(!temp.isNull("scope") && API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equalsIgnoreCase("BLCRM") &&
                            temp.getString("scope").contains("13370")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                    else if(!temp.isNull("scope") && API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equalsIgnoreCase("NJ-SBE") &&
                            temp.getString("scope").contains("13366")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                }
                break;

            case "Follow-Up on Appeal":
                String[] str1={"IDR Resolved Letter Generated","IDR Unresolved Letter Generated",
                        "No Action Taken"};
                expected.set(new ArrayList<>(Arrays.asList(str1)));
                for (int i = 0; i < json.size(); i++) {
                    JSONObject temp = new JSONObject(json.get(i).toString());
                    if(!temp.isNull("scope") && temp.getString("scope").contains("13369")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                }
                break;

            case "NJ-Follow-Up on Appeal":
                String[] str2={"Acknowledgement Letter Generated","No Action Taken",
                        "Outbound Call Successful", "Outbound Call Unsuccessful"};
                expected.set(new ArrayList<>(Arrays.asList(str2)));
                for (int i = 0; i < json.size(); i++) {
                    JSONObject temp = new JSONObject(json.get(i).toString());
                    if(!temp.isNull("scope") && temp.getString("scope").contains("13368")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                }
                break;

            case "Review OAL Appeal Decision":
                String[] str3={"Final Agency Decision Letter Generated","No Action Taken"};
                expected.set(new ArrayList<>(Arrays.asList(str3)));
                for (int i = 0; i < json.size(); i++) {
                    JSONObject temp = new JSONObject(json.get(i).toString());
                    if(!temp.isNull("scope") && temp.getString("scope").contains("13371")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                }
                break;
        }
        Collections.sort(expected.get());
        Collections.sort(actual.get());
        assertEquals(actual.get(), expected.get(), "Action taken enum values are wrong for task type "+ taskType);
    }

    @Then("I verify disposition enum values for task type {string}")
    public void verifyDispositionEnumValues(String taskType) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("ENUM_DISPOSITION");
        actual.set(new ArrayList<>());

        switch (taskType){
            case "Review Appeal Form":
                String[] str={"IDR Successful","IDR Unsuccessful","Not a Valid Appeal"};
                expected.set(new ArrayList<>(Arrays.asList(str)));
                for (int i = 0; i < json.size(); i++) {
                    JSONObject temp = new JSONObject(json.get(i).toString());
                    if(!temp.isNull("scope") && temp.getString("scope").contains("13366")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                }
                break;

            case "Follow-Up on Appeal":
                String[] str1={"IDR Successful","IDR Unsuccessful"};
                expected.set(new ArrayList<>(Arrays.asList(str1)));
                for (int i = 0; i < json.size(); i++) {
                    JSONObject temp = new JSONObject(json.get(i).toString());
                    if(!temp.isNull("scope") && temp.getString("scope").contains("13368")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                }
                break;

            case "GCNJ Resolve Appeal":
                String[] str2={"IDR Successful","IDR Unsuccessful"};
                expected.set(new ArrayList<>(Arrays.asList(str2)));
                for (int i = 0; i < json.size(); i++) {
                    JSONObject temp = new JSONObject(json.get(i).toString());
                    if(!temp.isNull("scope") && temp.getString("scope").contains("13367")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                }
                break;

            case "Rejected Response":
                String[] str3={"User Correction / Override","Systems Data Update","MMIS Correction"};
                expected.set(new ArrayList<>(Arrays.asList(str3)));
                for (int i = 0; i < json.size(); i++) {
                    JSONObject temp = new JSONObject(json.get(i).toString());
                    if(!temp.isNull("scope") && temp.getString("scope").contains("13365")){
                        actual.get().add(temp.getString("reportLabel"));
                    }
                }
                break;
        }
        Collections.sort(expected.get());
        Collections.sort(actual.get());
        assertEquals(actual.get(), expected.get(), "Disposition enum values are wrong for task type "+ taskType);
    }

    @Then("I verify information type enum values for task type {string}")
    public void verifyInformationTypeEnumValues(String taskType) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("ENUM_INFORMATION_TYPE");

        String[] str={"New Enrollment","Plan Change", "Disenroll Request"};
        expected.set(new ArrayList<>(Arrays.asList(str)));

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if(expected.get().contains(temp.getString("reportLabel"))){
                expected.get().remove(temp.getString("reportLabel"));
            }
        }
        assertTrue(expected.get().size()==0,
                "INFORMATION_TYPE enum values are wrong for task type "+ taskType);
    }

    @Then("I verify {string} enum values for task type {string}")
    public void verifyAccessibilityNeededEnumValues(String tableName, String taskType, List expectedValues) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray(tableName);

        expected.set(new ArrayList<String>(expectedValues));
        actual.set(new ArrayList<>());

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if(!temp.isNull("scope") && temp.getString("scope").contains(taskType)) {
                actual.get().add(temp.getString("reportLabel"));
            }
        }
        Collections.sort(expected.get());
        Collections.sort(actual.get());
        assertEquals(actual.get(), expected.get(),"Accessibility Needed enum values are wrong for task type "+ taskType);
    }

    @When("I initiated an updated enum get api for {string}")
    public void i_initiated_an_updated_enum_get_api_for(String tableName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enumNewBaseURI.get());
        enumNewEndURI.set(enumNewEndURI.get() + "?tableName="+ tableName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(enumNewEndURI.get());
    }

    @When("I verify table {string} size is {int}")
    public void i_verify_table_has(String tableName, int size){
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray(tableName);
        Assert.assertEquals(json.size(),size);
    }

    @When("I verify table {string} field {string} has {string}")
    public void i_verify_contents(String tableName, String field, String values){
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray(tableName);
        expected.set(new ArrayList<>(Arrays.asList(values.split(", "))));
        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            Assert.assertEquals(temp.getString(field), expected.get().get(i));
        }

    }

    @When("I verify table {string} field {string} has {string} fields value")
    public void i_verify_contents_Value(String tableName, String field, String values){
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray(tableName);
        expected.set(new ArrayList<>(Arrays.asList(values.split(", "))));
        List<String> listEnums= new ArrayList<>();
        for (int i = 0; i < json.size(); i++) {
            listEnums.add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get(""+tableName+"[" + i + "]."+field+""));
        }
        Assert.assertTrue(listEnums.containsAll(expected.get()));

    }

}