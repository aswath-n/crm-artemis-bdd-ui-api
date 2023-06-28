package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class APICaseConsumerSearchNJController extends CRMUtilities implements ApiBase {

    private String caseConsumerBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String searchCaseConsumer = "app/crm/case/consumers";
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(()->"Julia");
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(()->"Edwards");
    private final ThreadLocal<String> dob = ThreadLocal.withInitial(()->"1995-01-01");

    @Given("I initiate the case consumer API for project NJ")
    public void initiateCaseConsumerAPIForNJ() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchCaseConsumer);
    }

    @When("I run the case consumer API for project NJ with only {string}")
    public void runCaseConsumerAPIForNJ(String input) {
        requestParams.set(new JsonObject());

        if (input.contains("-")) {
            String[] splitString = input.split("-");
            for (String str: splitString) {
                addParamForRequest(str, requestParams.get());
            }
        } else {
            addParamForRequest(input, requestParams.get());
        }

        System.out.println("Request Params: " + requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameterForProject(requestParams.get(), "NJ-SBE");
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "Invalid");
    }

    @Then("I verify the error code message from the response for only {string}")
    public void verifyErrorCodeFromResponse(String input) {
        List validationList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("validationList");
        assertTrue(validationList.size() > 0);

        if (!input.equals("consumerDateOfBirth")) {
            for (int i = 0; i < validationList.size(); i++) {
                HashMap validationListData = (HashMap) validationList.get(i);

                if (input.equals("consumerFirstName") || input.equals("consumerFirstName-consumerDateOfBirth")) {
                    assertEquals(validationListData.get("fieldName"), "consumerLastName");
                    assertEquals(validationListData.get("validationErrorCode"), "Last Name is required when First Name is populated or DOB is populated.");
                } else if (input.equals("consumerLastName") || input.equals("consumerLastName-consumerDateOfBirth")) {
                    assertEquals(validationListData.get("fieldName"), "consumerFirstName");
                    assertEquals(validationListData.get("validationErrorCode"), "First Name is required when Last Name is populated or DOB is populated.");
                }
            }
        } else {
            for (int i = 0; i < validationList.size(); i++) {
                HashMap validationListData = (HashMap) validationList.get(i);

                if (validationListData.get("fieldName").equals("consumerFirstName")) {
                    assertEquals(validationListData.get("validationErrorCode"), "First Name is required when Last Name is populated or DOB is populated.");
                } else if (validationListData.get("fieldName").equals("consumerLastName")) {
                    assertEquals(validationListData.get("validationErrorCode"), "Last Name is required when First Name is populated or DOB is populated.");
                }
            }
        }
    }

    private void addParamForRequest(String value, JsonObject request) {
        if (value.equals("consumerFirstName")) {
            request.addProperty("consumerFirstName", firstName.get());
        } else if (value.equals("consumerLastName")) {
            request.addProperty("consumerLastName", lastName.get());
        } else if (value.equals("consumerDateOfBirth")) {
            request.addProperty("consumerDateOfBirth", dob.get());
        }
    }
}
