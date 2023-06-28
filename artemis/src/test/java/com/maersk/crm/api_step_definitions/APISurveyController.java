package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APISurveyController extends CRMUtilities implements ApiBase {

    private String surveyResponseSubmitBaseURI = ConfigurationReader.getProperty("apiSubmitSurveyResponse");
    private String surveyResponseFetchBaseURI = ConfigurationReader.getProperty("apiFetchSurveyResponse");

    private String surveyTemplateAndDetailsSubmitBaseURI = ConfigurationReader.getProperty("apiFetchSurveyResponse");
    private String surveyResponseSubmitEndPoint = "mars/assessment/survey-response";
    private String surveyResponseFetchEndPoint = "mars/assessment/search-survey-response";
    private String surveyTemplateAndDetailsSubmitEndPoint = "mars/assessment/survey-template";
    private String surveyTemplateFetchEndPoint = "mars/assessment/templates";
    private String surveyDetailsFetchEndPoint = "mars/assessment";
    private final ThreadLocal<JsonArray> requestParamsArray = ThreadLocal.withInitial(JsonArray::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private static final ThreadLocal<Integer> randomReferenceId = ThreadLocal.withInitial(() -> 0);

    public static final ThreadLocal<String> consumerId = ThreadLocal.withInitial(() -> "6271");
    private static final ThreadLocal<Integer> numberOfQuestions = ThreadLocal.withInitial(() -> 0);

    private static final ThreadLocal<List<Integer>> randomSurveyDetailsIds = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<Integer> randomSurveyDetailsId = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Integer> randomSurveyTemplateId = ThreadLocal.withInitial(() -> 0);

    private static final ThreadLocal<Integer> min = ThreadLocal.withInitial(() -> 1000000);
    private static final ThreadLocal<Integer> max = ThreadLocal.withInitial(() -> 2000000);

    @When("I initiate survey response submission API")
    public void initiateSurveyResponseSubmissionAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(surveyResponseSubmitBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(surveyResponseSubmitEndPoint);
    }

    @When("I prepare survey response for submission")
    public void prepareSurveyResponseSubmitAPI(Map<String, String> data) {
        randomSurveyDetailsIds.set(new ArrayList<>());
        if (data.containsKey("consumerId")) {
            consumerId.set(String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("referenceId"))).replace(".0", ""));
        }
        apitdu = apitdu.getJsonFromFile("crm/survey/submitSurveyResponse.json");
        if (data.containsKey("referenceType"))
            apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("referenceType", data.get("referenceType"));
        if (data.containsKey("languageCode"))
            apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("languageCode", data.get("languageCode"));
        if (data.containsKey("referenceId")) {
            if (data.get("referenceId").contains(".consumerId")) {
                consumerId.set(String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("referenceId"))).replace(".0", ""));
                apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("referenceId", Integer.parseInt(consumerId.get()));
            } else if (data.get("referenceId").contains("random")) {
                randomReferenceId.set(Integer.parseInt(RandomStringUtils.random(8, false, true)));
                apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("referenceId", randomReferenceId.get());
            } else {
                apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("referenceId", data.get("referenceId"));
            }
        }
        if (data.containsKey("surveyDate"))
            apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("surveyDate", apitdu.getStartDate(data.get("surveyDate")));
        if (data.containsKey("surveyResponse")) {
            JsonArray json = apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("surveyResponse");
            numberOfQuestions.set(Integer.parseInt(data.get("numberOfQuestions")));
            for (int i = 0; i < numberOfQuestions.get(); i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(min.get(), max.get() + 1);
                randomSurveyDetailsIds.get().add(randomNum);
                if (data.get((i + 1) + ".surveyDetailsId").equalsIgnoreCase("random")) {
                    json.get(i).getAsJsonObject().addProperty("surveyDetailsId", randomSurveyDetailsIds.get().get(i));
                } else {
                    json.get(i).getAsJsonObject().addProperty("surveyDetailsId", Integer.parseInt(data.get((i + 1) + ".surveyDetailsId")));
                }
                json.get(i).getAsJsonObject().addProperty("questionNumber", data.get((i + 1) + ".questionNumber"));
                json.get(i).getAsJsonObject().addProperty("questionText", data.get((i + 1) + ".questionText"));
                json.get(i).getAsJsonObject().addProperty("parentSurveyDetailId", Integer.parseInt(data.get((i + 1) + ".parentSurveyDetailId")));
            }
        }
        if (data.containsKey("status"))
            apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("status", data.get("status"));
        if (data.containsKey("createdOn"))
            apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("createdOn", apitdu.getCurrentDateAndTime("createdOn"));
        if (data.containsKey("createdBy"))
            apitdu.jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("createdBy", data.get("createdBy"));

        requestParamsArray.set(apitdu.jsonElement.getAsJsonArray());
    }

    @When("I run survey response submission")
    public void runSurveyResponseSubmitAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsArray.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("status"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @When("I initiate survey response fetch API")
    public void initiateSurveyResponseFetchAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(surveyResponseFetchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(surveyResponseFetchEndPoint);
    }

    @When("I prepare survey response for fetch")
    public void prepareSurveyResponseFetchAPI(Map<String, String> data) {
        numberOfQuestions.set(Integer.parseInt(data.get("size")));
        apitdu = apitdu.getJsonFromFile("crm/survey/fetchSurveyResponse.json");
        if (data.containsKey("referenceType"))
            apitdu.jsonElement.getAsJsonObject().addProperty("referenceType", data.get("referenceType"));
        if (data.containsKey("referenceId")) {
            if (data.get("referenceId").contains(".consumerId")) {
                apitdu.jsonElement.getAsJsonObject().addProperty("referenceId", String.valueOf(consumerId.get()));
            } else if (data.get("referenceId").contains("random")) {
                apitdu.jsonElement.getAsJsonObject().addProperty("referenceId", randomReferenceId.get());
            } else {
                apitdu.jsonElement.getAsJsonObject().addProperty("referenceId", data.get("referenceId"));
            }
        }
        if (data.containsKey("startDate"))
            apitdu.jsonElement.getAsJsonObject().addProperty("startDate", apitdu.getStartDate(data.get("startDate")));
        if (data.containsKey("endDate"))
            apitdu.jsonElement.getAsJsonObject().addProperty("endDate", apitdu.getEndDate(data.get("endDate")));
        if (data.containsKey("size"))
            apitdu.jsonElement.getAsJsonObject().addProperty("size", Integer.parseInt(data.get("size")));
        if (data.containsKey("from"))
            apitdu.jsonElement.getAsJsonObject().addProperty("from", Integer.parseInt(data.get("from")));
        if (data.containsKey("status"))
            apitdu.jsonElement.getAsJsonObject().addProperty("status", data.get("status"));

        requestParams.set(apitdu.jsonElement.getAsJsonObject());
    }

    @And("I run fetch survey response API")
    public void runFetchSurveyResponseAPI() {
        System.out.println("Request Fetch Survey Response: " + requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

    }

    @When("I verify fetch survey response with data")
    public void iVerifyFetchSurveyResponseWithData(Map<String, String> data) {
        List surveyList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("survey");
        String actual = "";
        String expected = "";
        for (int i = 0; i < surveyList.size(); i++) {
            JsonObject dataObjectData = null;
            HashMap survey = (HashMap) surveyList.get(i);

            for (String key : data.keySet()) {
                switch (key) {
                    case "referenceId":
                        if (data.get(key).contains(".consumerId")) {
                            expected = String.valueOf(consumerId.get());
                        } else if (data.get(key).equalsIgnoreCase("random")) {
                            expected = String.valueOf(randomReferenceId.get());
                        } else {
                            expected = data.get(key);
                        }
                        System.out.println("expected " + key + " value : " + expected);
                        if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                            assertTrue(survey.get(key) == null, key + " is not null! - FAIL!");
                        } else {
                            actual = survey.get(key).toString();
                            System.out.println("actual " + key + " value   : " + actual);
                            Assert.assertEquals(actual, expected, key + " mismatch!");
                        }
                        break;

                    case "status":
                    case "languageCode":
                    case "referenceType":
                    case "createdBy":
                    case "updatedBy":
                        expected = data.get(key);
                        System.out.println("expected " + key + " value : " + expected);
                        if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                            assertTrue(survey.get(key) == null, key + " is not null! - FAIL!");
                        } else {
                            actual = survey.get(key).toString();
                            System.out.println("actual " + key + " value   : " + actual);
                            Assert.assertEquals(actual, expected, key + " mismatch!");
                        }
                        break;

                    case "createTs":
                    case "updateTs":
                        expected = apitdu.getStartDate(data.get(key));
                        System.out.println("expected " + key + " value : " + expected);
                        if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                            assertTrue(survey.get(key) == null, key + " is not null! - FAIL!");
                        } else {
                            actual = survey.get(key).toString().substring(0, 10);
                            System.out.println("actual " + key + " value   : " + actual);
                            Assert.assertEquals(actual, apitdu.getStartDate(data.get(key)), key + " mismatch!");
                        }
                        break;

                    case "surveyDate":
                        actual = survey.get("surveyDate").toString();
                        expected = apitdu.getStartDate(data.get(key)).substring(0, 10);
                        System.out.println("expected " + key + " value : " + expected);
                        System.out.println("actual " + key + " value   : " + actual);
                        Assert.assertEquals(actual, expected, key + " mismatch!");
                        break;
                }
            }
        }
    }


    @When("I initiate survey template and survey details submission API")
    public void initiateSurveyTemplateAndDetailsSubmissionAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(surveyTemplateAndDetailsSubmitBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(surveyTemplateAndDetailsSubmitEndPoint);
    }


    @When("I prepare survey template and survey details for submission")
    public void prepareSurveyTemplateAndDetailsSubmitAPI(Map<String, String> data) {
        randomSurveyDetailsIds.set(new ArrayList<>());
        randomSurveyTemplateId.set(ThreadLocalRandom.current().nextInt(min.get(), max.get() + 1));
        apitdu = apitdu.getJsonFromFile("crm/survey/submitSurveyTemplateAndDetails.json");
        JsonArray surveyDetails = apitdu.jsonElement.getAsJsonObject().getAsJsonArray("surveyDetails");
        JsonArray surveyTemplates = apitdu.jsonElement.getAsJsonObject().getAsJsonArray("surveyTemplates");
        System.out.println("surveyDetails:");
        System.out.println(surveyDetails);
        System.out.println("surveyTemplates:");
        System.out.println(surveyTemplates);
        for (String field : data.keySet()) {
            switch (field) {
                case "surveyDetailsId":
                    if (data.get("surveyDetailsId").contains("random")) {
                        randomSurveyDetailsId.set(ThreadLocalRandom.current().nextInt(min.get(), max.get() + 1));
                        surveyDetails.get(0).getAsJsonObject().addProperty("surveyDetailsId", randomSurveyDetailsId.get());
                    } else {
                        surveyDetails.get(0).getAsJsonObject().addProperty("surveyDetailsId", data.get("surveyDetailsId"));
                    }
                    break;
                case "surveyTemplateId":
                    if (data.get("surveyTemplateId").contains("random")) {
                        randomSurveyTemplateId.set(ThreadLocalRandom.current().nextInt(min.get(), max.get() + 1));
                        surveyDetails.get(0).getAsJsonObject().addProperty("surveyTemplateId", randomSurveyTemplateId.get());
                        surveyTemplates.get(0).getAsJsonObject().addProperty("surveyTemplateId", randomSurveyTemplateId.get());
                    } else {
                        surveyDetails.get(0).getAsJsonObject().addProperty("surveyTemplateId", data.get("surveyTemplateId"));
                        surveyTemplates.get(0).getAsJsonObject().addProperty("surveyTemplateId", data.get("surveyTemplateId"));
                    }
                    break;
                case "questionNumber":
                case "questionTextForm":
                case "questionText":
                case "answerText":
                case "parentSurveyDetailId":
                case "answerTrigger":
                case "questionType":
                    surveyDetails.get(0).getAsJsonObject().addProperty(field, data.get(field));
                    break;
                case "description":
                case "templateType":
                    surveyTemplates.get(0).getAsJsonObject().addProperty(field, data.get(field));
                    break;
                case "effectiveStartDate":
                case "createdOn":
                case "updatedOn":
                    apitdu.jsonElement.getAsJsonObject().addProperty(field, apitdu.getStartDate(data.get(field)));
                    break;
                case "effectiveEndDate":
                    apitdu.jsonElement.getAsJsonObject().addProperty(field, apitdu.getEndDate(data.get(field)));
                    break;
                case "createdBy":
                case "updatedBy":
                    apitdu.jsonElement.getAsJsonObject().addProperty(field, data.get(field));
                    break;
            } // switch
        } // for
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }


    @When("I prepare survey template for submission")
    public void prepareSurveyTemplateSubmitAPI(Map<String, String> data) {
        randomSurveyDetailsIds.set(new ArrayList<>());
        randomSurveyTemplateId.set(ThreadLocalRandom.current().nextInt(min.get(), max.get() + 1));
        apitdu = apitdu.getJsonFromFile("crm/survey/submitSurveyTemplate.json");
        JsonArray surveyTemplates = apitdu.jsonElement.getAsJsonObject().getAsJsonArray("surveyTemplates");
        System.out.println("surveyTemplates:");
        System.out.println(surveyTemplates);
        for (String field : data.keySet()) {
            switch (field) {
                case "surveyTemplateId":
                    if (data.get("surveyTemplateId").contains("random")) {
                        randomSurveyTemplateId.set(ThreadLocalRandom.current().nextInt(min.get(), max.get() + 1));
                        surveyTemplates.get(0).getAsJsonObject().addProperty("surveyTemplateId", randomSurveyTemplateId.get());
                    } else {
                        surveyTemplates.get(0).getAsJsonObject().addProperty("surveyTemplateId", data.get("surveyTemplateId"));
                    }
                    break;
                case "description":
                case "templateType":
                    surveyTemplates.get(0).getAsJsonObject().addProperty(field, data.get(field));
                    break;
                case "effectiveStartDate":
                case "createdOn":
                case "updatedOn":
                    apitdu.jsonElement.getAsJsonObject().addProperty(field, apitdu.getStartDate(data.get(field)));
                    break;
                case "effectiveEndDate":
                    apitdu.jsonElement.getAsJsonObject().addProperty(field, apitdu.getEndDate(data.get(field)));
                    break;
                case "createdBy":
                case "updatedBy":
                    apitdu.jsonElement.getAsJsonObject().addProperty(field, data.get(field));
                    break;
            }
        }
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }


    @When("I run survey template and survey details submission")
    public void runSurveyTemplateAndDetailsSubmitAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("status"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }


    @When("I initiate survey details fetch API")
    public void initiateSurveyDetailsFetchAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(surveyResponseFetchBaseURI);
        surveyDetailsFetchEndPoint = surveyDetailsFetchEndPoint + "/" + String.valueOf(randomSurveyTemplateId.get());
        System.out.println("surveyDetailsFetchEndPoint" + surveyDetailsFetchEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(surveyDetailsFetchEndPoint);
    }


    @And("I run fetch survey details API")
    public void runFetchSurveyDetailsAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }


    @When("I verify fetch survey details with data")
    public void iVerifyFetchSurveyDetailsWithData(Map<String, String> data) {
        List surveyDetailsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("surveyDetails");
        String actual = "";
        String expected = "";
        for (int i = 0; i < surveyDetailsList.size(); i++) {
            HashMap surveyDetails = (HashMap) surveyDetailsList.get(i);

            for (String key : data.keySet()) {
                switch (key) {
                    case "surveyDetailsId":
                    case "surveyTemplateId":
                        if (data.get(key).equalsIgnoreCase("random")) {
                            if (key.equals("surveyDetailsId")) expected = String.valueOf(randomSurveyDetailsId.get());
                            else if (key.equals("surveyTemplateId")) expected = String.valueOf(randomSurveyTemplateId.get());
                        } else {
                            expected = data.get(key);
                        }
                        System.out.println("expected " + key + " value : " + expected);
                        if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                            assertTrue(surveyDetails.get(key) == null, key + " is not null! - FAIL!");
                        } else {
                            actual = surveyDetails.get(key).toString();
                            System.out.println("actual " + key + " value   : " + actual);
                            Assert.assertEquals(actual, expected, key + " mismatch!");
                        }
                        break;

                    case "questionNumber":
                    case "questionText":
                    case "questionTextForm":
                    case "parentSurveyDetailId":
                    case "answerTrigger":
                    case "questionType":
                    case "answerText":
                    case "answerFieldSize":
                    case "scope":
                    case "createdBy":
                    case "updatedBy":
                        expected = data.get(key);
                        System.out.println("expected " + key + " value : " + expected);
                        if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                            assertTrue(surveyDetails.get(key) == null, key + " is not null! - FAIL!");
                        } else {
                            actual = surveyDetails.get(key).toString();
                            System.out.println("actual " + key + " value   : " + actual);
                            Assert.assertEquals(actual, expected, key + " mismatch!");
                        }
                        break;

                    case "createTs":
                    case "updateTs":
                        expected = apitdu.getStartDate(data.get(key));
                        System.out.println("expected " + key + " value : " + expected);
                        if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                            assertTrue(surveyDetails.get(key) == null, key + " is not null! - FAIL!");
                        } else {
                            actual = surveyDetails.get(key).toString().substring(0, 10);
                            System.out.println("actual " + key + " value   : " + actual);
                            Assert.assertEquals(actual, apitdu.getStartDate(data.get(key)), key + " mismatch!");
                        }
                        break;
                }
            }
        }
    }


    @When("I initiate {string} survey template fetch API")
    public void initiateSurveyTemplateFetchAPI(String status) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(surveyResponseFetchBaseURI);
        surveyTemplateFetchEndPoint = surveyTemplateFetchEndPoint + "/" + status;
        System.out.println("surveyTemplateFetchEndPoint" + surveyTemplateFetchEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(surveyTemplateFetchEndPoint);
    }


    @And("I run fetch survey template API")
    public void runFetchSurveyTemplateAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @When("I verify fetch survey template contains given survey template ids")
    public void iVerifyFetchSurveyTemplateWithData(List<String> data) {
        List<Integer> surveyTemplatesIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("surveyTemplateList.surveyTemplateId", Integer.TYPE);
        System.out.println("surveyTemplatesIdList:" + surveyTemplatesIdList);
        for (String id : data) {
            Assert.assertTrue(surveyTemplatesIdList.contains(Integer.parseInt(id)));
        }
    }


}
