package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class API_CRMStoreAppCaseDetaisFromVaCMS extends CRMUtilities implements ApiBase {

    String baseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    String vaCMSEndpoint = "/app/crm/vacms/details";
    String conversationId = "mko99mknono8-dfs9d8-mko9-mko9-nji9mko9mko9";
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<ResponseBody> appCaseDetailsResponse = new ThreadLocal<>();

    @Given("I initiate Case Details request and responses from VaCMS")
    public void iInitiateCaseDetailsRequestAndResponsesFromVaCMS() throws Throwable {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(vaCMSEndpoint);
    }

    private JsonObject generateVaCMSAppCaseRequest(String systemName, String vaCMSNum, String numQualifier) {
       API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiAppCaseDetailsVaCMS.json");

       API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("conversationId", conversationId);
       API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("systemName", systemName);
       API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("vaCMSNum", vaCMSNum);
       API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("numQualifier", numQualifier);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }


    @Then("I run Case Details request and responses with {string}, {string}, {string}")
    public void iRunCaseDetailsRequestAndResponsesWith(String systemName, String vaCMSNum, String numQualifier) {
        requestParams.set(generateVaCMSAppCaseRequest(systemName, vaCMSNum, numQualifier));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("request params: " + requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        synchronized (appCaseDetailsResponse){
            appCaseDetailsResponse.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        }
    }

    @Then("I validate successful response containing expected parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void iValidateSuccessfulResponseContainingExpectedParameters(String systemName, String vaCMSNum, String numQualifier, String responseCode, String responseStatus, String responseDescription, String maProgStatus) {
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
        System.out.println(jsonPath.get("sourceTransID").toString() + "+++++++++++++++++++++++++++++++++++++++++++");
        assertTrue(!jsonPath.get("sourceTransID").toString().isEmpty());
        assertTrue(jsonPath.get("responseCode").toString().equalsIgnoreCase(responseCode));
        assertTrue(jsonPath.get("responseStatus").toString().equalsIgnoreCase(responseStatus));
        assertTrue(jsonPath.get("responseDescription").toString().equalsIgnoreCase(responseDescription));
        if (numQualifier.equalsIgnoreCase("A")) {
            assertTrue(jsonPath.get("applicationStatusesList[0].appNumber").toString().equalsIgnoreCase(vaCMSNum));
        } else {
            assertTrue(jsonPath.get("vacmsCaseNum").toString().equalsIgnoreCase(vaCMSNum));
        }

    }


    @Then("I validate Failed response containing expected parameters {string}, {string}, {string}, {string}, {string}, {string}")
    public void iValidateFailedResponseContainingExpectedParameters(String systemName, String vaCMSNum, String numQualifier, String responseCode, String responseStatus, String responseDescription) throws Throwable {
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
        System.out.println(jsonPath.get("sourceTransID").toString() + "+++++++++++++++++++++++++++++++++++++++++++");
        assertTrue(!jsonPath.get("sourceTransID").toString().isEmpty());
        assertTrue(jsonPath.get("responseCode").toString().equalsIgnoreCase(responseCode));
        assertTrue(jsonPath.get("responseStatus").toString().equalsIgnoreCase(responseStatus));
        assertTrue(jsonPath.get("responseDescription").toString().equalsIgnoreCase(responseDescription));

    }
}

