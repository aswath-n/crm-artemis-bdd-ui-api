package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class APILookUpController extends CRMUtilities implements ApiBase {
    /*public api api = new api();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private api api = new api();
    public BrowserUtils br=new BrowserUtils();*/
    public final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);

    private String baseURI = ConfigurationReader.getProperty("apiLookupURI");
    private String getLookupEndPoint = "mars/common/lookup?bservice={service}&tableName={table}";

    private static String lookUpCon = ConfigurationReader.getProperty("apiLookupURI");
    private String dataBase = "mars/common/lookup/tables";
    private String tableName = "mars/common/lookup";
    private String addLookUp = "mars/common/lookup/enum";
    SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");



    @Given("I initiated Look up API with service {string} and table name {string}")
    public void initiateLookUpApi(String service, String table) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getLookupEndPoint = getLookupEndPoint.replace("{service}", service).replace("{table}", table);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getLookupEndPoint);
    }

    @And("I run get look up api")
    public void runLookUpApi(){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("API Response:");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @And("I verify values for {string}")
    public void verifyConsumerTypeValues(String tableName, List<String> expValues){
        List<String> actValues = new ArrayList<>();
        ArrayList<HashMap<String, String>> consumerTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject(tableName);
        for(int i=0;i<consumerTypes.size();i++){
            HashMap<String, String> consumerDetails = consumerTypes.get(i);
            actValues.add(consumerDetails.get("value"));
        }
        assertEquals(actValues.size(), expValues.size());
        assertTrue(actValues.containsAll(expValues));
    }

    @And("I initiated Select DataBase for LOOKUP CONFIGURATION")
    public void iInitiatedSelectDataBaseForLOOKUPCONFIGURATION() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(lookUpCon);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(dataBase);
    }

    @Given("Get Select DataBase for LOOKUP CONFIGURATION")
    public void getSelectDataBaseForLOOKUPCONFIGURATION(Map<String,String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequestWithQuery(data);
    }

    @And("Verify response status code is {int}")
    public void verifyResponseStatusCodeIs(int statusCode) {
        assertEquals(statusCode, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
    }

    @Then("Verify response for Select DataBase")
    public void verifyResponseForSelectDataBase() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.enumTables[*]").size() > 0);
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().isValueExist("c.enumTables.ENUM_RACE_CODE"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().isValueExist("c.enumTables.ENUM_CASE_STATUS"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().isValueExist("c.enumTables.ENUM_LANGUAGE"));
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().isValueExist("c.enumTables.ENUM_CONSUMER_STATUS"));

    }

    @And("I initiated Select Data Table for LOOKUP CONFIGURATION")
    public void iInitiatedSelectDataTableForLOOKUPCONFIGURATION() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(lookUpCon);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(tableName);
    }

    @Given("Get Select Select ENUM Data Table for LOOKUP CONFIGURATION")
    public void getSelectSelectENUMDataTableForLOOKUPCONFIGURATION(Map<String,String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequestWithQuery(data);
    }

    @Then("Verify Enum Data Table response")
    public void verifyEnumDataTableResponse() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.ENUM_RACE_CODE[*]").size() > 0);

    }

    @And("I initiated ADD LOOKUP for LOOKUP CONFIGURATION")
    public void iInitiatedADDLOOKUPForLOOKUPCONFIGURATION() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(lookUpCon);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(addLookUp);
    }

    @Then("I add new Lookup payload {string} for LOOKUP CONFIGURATION")
    public void iAddNewLookupPayloadForLOOKUPCONFIGURATION(String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/LookupConfiguration/"+payload+".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json,data));
    }

    @Then("Verify Data table value is {string} are added")
    public void verifyDataTableValueIsAreAdded(String val) {
        System.out.println("here"+ API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.ENUM_RACE_CODE[?(@.value=='"+val+"')]"));
        assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.ENUM_RACE_CODE[?(@.value=='"+val+"')]").isEmpty());
    }

    @Then("Verify Null value not added to the Table Name")
    public void verifyNullValueNotAddedToTheTableName() {
        assertFalse(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().isValueExist("c.ENUM_RACE_CODE[?(@.value== null)]"));
    }

    @Then("Verify Enum Date response")
    public void verifyEnumDateResponse() throws ParseException {
        assertTrue(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().isValueExist("c.ENUM_RACE_CODE[?(@.effectiveEndDate== null)]"));
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
       // Date current=format.parse(br.getCurrentDateInYearFormat());
        Date current=format.parse(BrowserUtils.getCurrentDateWithFormat());
        int size = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.ENUM_RACE_CODE[*].effectiveStartDate").size();
        for (int i =0;i<size;i++){
            String dateResponse = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asList("c.ENUM_RACE_CODE[*].effectiveStartDate").get(i).asText();
            Date efStartDate=format.parse(dateResponse);
            assertTrue(efStartDate.getTime() <= current.getTime());
            System.out.println(efStartDate.getTime() +"  ________  "+ current.getTime());
        }

    }
}
