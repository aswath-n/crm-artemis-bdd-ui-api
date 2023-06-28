package com.maersk.crm.api_step_definitions;

import com.google.gson.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APICaseCheckController  extends CRMUtilities implements ApiBase {

    public String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    public String consumerBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    public String getConsumerListByUser = "app/crm/cases";
    public String createCaseWithConsumers = "app/crm/caseloader";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsForCaseLoader = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> caseTypeValue = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> caseIdentificationNoValue = ThreadLocal.withInitial(String::new);

    @Given("I run the Case Loader API for {string}")
    public void runCaseCheckAPI(String caseType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);

        String caseIdentificationNumber = null;

        if(caseType.equals("existing case")) {
            requestParamsForCaseLoader.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest());
            JsonElement caseLoaderBody = requestParamsForCaseLoader.get().getAsJsonArray("caseLoaderRequest").get(0);
            caseIdentificationNumber = caseLoaderBody.getAsJsonObject().get("case").getAsJsonObject().get("caseIdentificationNumber").toString().replaceAll("^\"|\"$", "");
            System.out.println(caseIdentificationNumber);

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsForCaseLoader.get());
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        } else {
            caseIdentificationNumber = "000";
        }
        caseTypeValue.set(caseType);
        caseIdentificationNoValue.set(caseIdentificationNumber);
    }

    @When("I initiated Case Check API for search case")
    public void initiatedCaseCheckAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerListByUser);

        requestParams.set(generateConsumerContactRequest(caseTypeValue.get(), caseIdentificationNoValue.get()));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("status"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I will find the case for search case")
    public void noCaseWillBeReturned() {
        JsonArray caseIdList = requestParams.get().getAsJsonArray("caseIdentificationNumber");
        String caseId = null;
        String numberType = null;
        for(int i=0; i<caseIdList.size(); i++){
            JsonArray caseIdData = caseIdList.getAsJsonArray();
            caseId = caseIdData.get(i).getAsJsonObject().get("externalCaseId").getAsString();
            numberType = caseIdData.get(i).getAsJsonObject().get("identificationNumberType").getAsString();
        }

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List caseContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content");
        System.out.println(caseContent);
        for(int i=0;i<caseContent.size();i++) {
            HashMap caseData = (HashMap)caseContent.get(i);
            System.out.println(caseData);
            List caseDataList = (List) caseData.get("caseIdentificationNumber");
            for(int j=0; j<caseDataList.size(); j++){
                HashMap caseIdsData = (HashMap)caseDataList.get(i);
                assertEquals(caseIdsData.get("externalCaseId"), caseId);
                assertEquals(caseIdsData.get("identificationNumberType"), numberType);
            }
        }
    }

    @Then("I will not find the case for search case")
    public void selectThatCase() {
        List caseContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content");
        assertEquals(caseContent.size(), 0);
    }

    private JsonObject generateConsumerContactRequest(String caseType, String caseIdentificationNo){
        System.out.println("caseType:"+ caseType);
        System.out.println("caseIdentificationNo:"+ caseIdentificationNo);
        JsonObject request = new JsonObject();
        JsonObject caseIdentificationNumber = new JsonObject();
        JsonArray caseIdList = new JsonArray();
        caseIdList.add(caseIdentificationNumber);

        request.addProperty("caseType", "Member");
        request.addProperty("caseStatus", "Active");

        if(caseType.equals("existing case")){
            caseIdentificationNumber.addProperty("externalCaseId", String.valueOf(caseIdentificationNo));
        } else {
            caseIdentificationNumber.addProperty("externalCaseId", "");
        }
        caseIdentificationNumber.addProperty("identificationNumberType", "CHIP");

        Gson gson = new GsonBuilder().create();
        request.add("caseIdentificationNumber", gson.toJsonTree(caseIdList).getAsJsonArray());

        return request;

    }
}