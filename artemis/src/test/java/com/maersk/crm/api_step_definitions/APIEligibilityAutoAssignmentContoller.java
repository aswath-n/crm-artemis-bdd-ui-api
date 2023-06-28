package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APIEligibilityAutoAssignmentContoller extends CRMUtilities implements ApiBase {

    private String enrollmentBaseUri = ConfigurationReader.getProperty("apiEligibilityURI");
    private String autoAssignmentTxnConfigEndPoint = "mars/eb/autoAssignmentTxn/configuration";
    private String autoAssignmentTxnPlanConfigEndPoint = "mars/eb/autoAssignmentTxn/planconfiguration";
    private String getAutoAssignmentConfigEndPoint = "mars/eb/autoAssignmentTxn/configuration?population=MEDICAID-GENERAL&programTypeCode=MEDICAID&subprogramTypeCode=MEDICAIDGF";
    private String autoAssignmentTxnPlanAssignmentEndPoint = "mars/eb/autoAssignmentTxn/planassignment";
    private String autoAssignmentPlanAssignmentBatchEndPoint = "mars/eb/autoAssignmentTxn/planassignmentbatch/start?programTypeCode=MEDICAID&subprogramTypeCode=MEDICAIDGF&population=MEDICAID-GENERAL";
    private String autoAssignmentPlanAssignmentBatchEndPointImmigrantChildren = "mars/eb/autoAssignmentTxn/planassignmentbatch/start?programTypeCode=MEDICAID&subprogramTypeCode=ImmigrantChildren";
    private String autoAssignmentPlanAssignmentBatchEndPointAlliance = "mars/eb/autoAssignmentTxn/planassignmentbatch/start?programTypeCode=MEDICAID&subprogramTypeCode=Alliance";
    private String autoAssignmentPlanAssignmentBatchEndPointDCHF = "mars/eb/autoAssignmentTxn/planassignmentbatch/start?programTypeCode=MEDICAID&subprogramTypeCode=DCHF";
    private String updateAutoAssignmentTxnEndPoint = "mars/eb/autoAssignmentTxn/update";
    private String getAutoAssignmentTxnEndPoint = "mars/eb/autoAssignmentTxn/find";
    private String fetchAutoAssignmentPlanConfigurationsEndPoint = "mars/eb/autoAssignmentTxn/fetchplanconfigurations?programTypeCode=MEDICAID&subprogramTypeCode=MEDICAIDGF&population=MEDICAID-GENERAL";
    private String getEnrollmentByConsumerId = "/mars/eb/enrollments/{consumerId}";
    private String getTaskByTaskId = "mars/taskmanagement/tasks/{taskId}";
    private String taskManagementBaseUri = ConfigurationReader.getProperty("apiTaskManagementURI");

    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private static final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private static final ThreadLocal <JsonArray> requestParamsArray = ThreadLocal.withInitial(JsonArray::new);
    private final ThreadLocal <Map<String, String>> configData = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal <Integer> autoAssignmentTransactionId = ThreadLocal.withInitial(()->0);
    private static final ThreadLocal <List<Integer>> ExistingAutoAssignmentPlanPercentageIds = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal <List<String>> ExistingAutoAssignmentPlanPercentages = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal <List<String>> ExistingAutoAssignmentPlanCodes = ThreadLocal.withInitial(ArrayList::new);
    private static Map<String,Integer> autoAssignedPlanCodes = new HashMap<>();
    public static final ThreadLocal <Integer> serviceRequestId = ThreadLocal.withInitial(()->0);
    private static final ThreadLocal <String>  enrollmentDueDate = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal <String>  taskDefaultDueDate = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal <String>  changeAllowedUntil = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> srIdFromLink = ThreadLocal.withInitial(()-> "");

    private static final ThreadLocal <String>  resolution = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal <String>  status = ThreadLocal.withInitial(String::new);;
    private static final ThreadLocal <String>  planCode = ThreadLocal.withInitial(String::new);;

    private static final ThreadLocal <Integer> taskId = ThreadLocal.withInitial(()-> 0);

    @And("I initiated auto assignment configuration Api")
    public void initiateAutoAssignmentCOnfigurationEndpoint() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentTxnConfigEndPoint);
    }

    @And("I provided auto assignment configuration details")
    public void provideAutoAssignmentConfigurationDetails(Map<String, String> configDataToUpdate) {
        configData.set(configDataToUpdate);
        apitdu = apitdu.getJsonFromFile("crm/enrollment/createUpdateAutoAssignmentConfiguration.json");
        for(String key:configData.get().keySet()){
            String attribute = key.split("\\.")[1];
            if(key.startsWith("priorPlan")){
                switch(attribute){
                    case "isPcpMandatory":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("priorPlan").getAsJsonObject().addProperty("isPcpMandatory", Boolean.parseBoolean(configData.get().get(key)));
                        break;
                    case "isMostRecentPlan":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("priorPlan").getAsJsonObject().get("preferredPlanToSelect").getAsJsonObject().addProperty("isMostRecentPlan", Boolean.parseBoolean(configData.get().get(key)));
                        break;
                    case "isLrgerDurationPlan":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("priorPlan").getAsJsonObject().get("preferredPlanToSelect").getAsJsonObject().addProperty("isLrgerDurationPlan", Boolean.parseBoolean(configData.get().get(key)));
                        break;
                    case "priorEnrollmentNoOfMonths":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("priorPlan").getAsJsonObject().addProperty("priorEnrollmentNoOfMonths", Integer.parseInt(configData.get().get(key)));
                        break;
                }

            }if(key.startsWith("familyPlan")){
                switch(attribute){
                    case "determinationBasedOn":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("familyPlan").getAsJsonObject().addProperty("determinationBasedOn", configData.get().get(key));
                        break;
                    case "priorEnrollmentNoOfMonths":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("familyPlan").getAsJsonObject().addProperty("priorEnrollmentNoOfMonths", Integer.parseInt(configData.get().get(key)));
                        break;
                    case "isCompanionPlanAllowed":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("familyPlan").getAsJsonObject().addProperty("isCompanionPlanAllowed", Boolean.parseBoolean(configData.get().get(key)));
                        break;
                    case "isPendingPlan":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("familyPlan").getAsJsonObject().get("typeOfEnrollment").getAsJsonObject().addProperty("isPendingPlan", Boolean.parseBoolean(configData.get().get(key)));
                        break;
                    case "isEnrolledPlan":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("familyPlan").getAsJsonObject().get("typeOfEnrollment").getAsJsonObject().addProperty("isEnrolledPlan", Boolean.parseBoolean(configData.get().get(key)));
                        break;
                    case "isEnrolledAndPendingPlan":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("familyPlan").getAsJsonObject().get("typeOfEnrollment").getAsJsonObject().addProperty("isEnrolledAndPendingPlan", Boolean.parseBoolean(configData.get().get(key)));
                        break;
                    case "isNearestAge":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("familyPlan").getAsJsonObject().get("planSelectionPriotiry").getAsJsonObject().addProperty("isNearestAge", Boolean.parseBoolean(configData.get().get(key)));
                        break;
                    case "isMajorityPlan":
                        apitdu.jsonElement.getAsJsonObject().getAsJsonObject("configuration").get("familyPlan").getAsJsonObject().get("planSelectionPriotiry").getAsJsonObject().addProperty("isMajorityPlan", Boolean.parseBoolean(configData.get().get(key)));
                        break;

                }
            }
        }
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
    }

    @When("I run auto assignment configuration post API")
    public void runAutoAssignmentConfigPostApi() {
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @And("I initiated auto assignment configuration get Api")
    public void initiateAutoAssignmentConfigurationGetEndpoint() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getAutoAssignmentConfigEndPoint);
    }

    @And("I run auto assignment configuration get API")
    public void runAutoAssignmentConfigGetApi() {
       API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @And("I validate auto assignment configuration values updated")
    public void verifyAutoAssignemtnConfigValuesUpdated() {
        JsonObject configResponseObj = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data");
        for(String key:configData.get().keySet()){
            String attribute = key.split("\\.")[1];
            if(key.startsWith("priorPlan")){
                switch(attribute){
                    case "isPcpMandatory":
                    case "priorEnrollmentNoOfMonths":
                        Assert.assertEquals(configResponseObj.getAsJsonObject("configuration").getAsJsonObject("priorPlan").get(attribute).getAsString(), configData.get().get(key));
                        break;
                    case "isMostRecentPlan":
                    case "isLrgerDurationPlan":
                        Assert.assertEquals(configResponseObj.getAsJsonObject("configuration").getAsJsonObject("priorPlan").getAsJsonObject("preferredPlanToSelect").get(attribute).getAsString(), configData.get().get(key));
                        break;
                }

            }if(key.startsWith("familyPlan")){
                switch(attribute){
                    case "determinationBasedOn":
                    case "priorEnrollmentNoOfMonths":
                    case "isCompanionPlanAllowed":
                        Assert.assertEquals(configResponseObj.getAsJsonObject("configuration").getAsJsonObject("familyPlan").get(attribute).getAsString(), configData.get().get(key));
                        break;
                    case "isPendingPlan":
                    case "isEnrolledPlan":
                    case "isEnrolledAndPendingPlan":
                        Assert.assertEquals(configResponseObj.getAsJsonObject("configuration").getAsJsonObject("familyPlan").getAsJsonObject("typeOfEnrollment").get(attribute).getAsString(), configData.get().get(key));
                        break;
                    case "isNearestAge":
                    case "isMajorityPlan":
                        Assert.assertEquals(configResponseObj.getAsJsonObject("configuration").getAsJsonObject("familyPlan").getAsJsonObject("planSelectionPriotiry").get(attribute).getAsString(), configData.get().get(key));
                       break;

                }
            }
        }
    }

    @And("I initiated auto assignment transaction plan assignment Api")
    public void initiateAutoAssignmentTxnPlanAssignmentEndPoint() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentTxnPlanAssignmentEndPoint);
    }

    @And("I provided auto assignment transaction plan assignment details")
    public void prepareAutoAssignmentTxnPlanAssignmentRequest() {
       JsonObject json = new JsonObject();
       json.addProperty("consumerId", APIEnrollmentController.consumerId.get());
        json.addProperty("serviceRequestId", 123);
        json.addProperty("status", (String)null);
        json.addProperty("resolution", "Selection Not Made");

        requestParamsArray.get().add(json);
    }

    @And("I run auto assignment transaction plan assignment API")
    public void runAutoAssignmentTxnPlanAssignmentRequest() {
      API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsArray.toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @And("I initiated auto assignment plan batch process Api")
    public void initiateAutoAssignmentPlanAssignmentBAtchEndPoint() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentPlanAssignmentBatchEndPoint);
    }

    @And("I initiated auto assignment plan batch process Api for Immigrant Children")
    public void initiateAutoAssignmentPlanAssignmentBAtchEndPointForImmigrantChildren() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentPlanAssignmentBatchEndPointImmigrantChildren);
    }

    @And("I initiated auto assignment plan batch process Api for Alliance")
    public void initiateAutoAssignmentPlanAssignmentBAtchEndPointForAlliance() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentPlanAssignmentBatchEndPointAlliance);
    }

    @And("I initiated auto assignment plan batch process Api for {string}")
    public void initiateAutoAssignmentPlanAssignmentBAtchEndPointForAlliance(String subProgramTypeCode) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        switch (subProgramTypeCode.toLowerCase()){
            case "alliance":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentPlanAssignmentBatchEndPointAlliance);
                break;
            case "immigrantchildren":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentPlanAssignmentBatchEndPointImmigrantChildren);
                break;
            case "dchf":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentPlanAssignmentBatchEndPoint);
                break;
            default:
                System.out.println("No existing subProgramTypeCode has been selected for Auto Assignment Endpoint setup!!!");
        }
    }

    @And("I run auto assignment plan batch process Api")
    public void runAutoAssignmentPlanAssignmentBAtchEndPoint() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @And("I remove {string} from the request")
    public void provideInvalidCOnfigurationDetails(String objectToremove) {
        apitdu = apitdu.getJsonFromFile("crm/enrollment/createUpdateAutoAssignmentConfiguration.json");
        apitdu.jsonElement.getAsJsonObject().remove(objectToremove);
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
    }

    @And("I validate auto assignment configuration error message {string}")
    public void verifyErrorMessageForInvalidConfigurationDetails(String objectToremove) {
        //implement validation
    }

    @And("I provide invalid market share percetages")
    public void provideInvalidMarketShareDetails(Map<String, String> marketSharePlanDetails) {
        apitdu = apitdu.getJsonFromFile("crm/enrollment/createUpdateAutoAssignmentConfiguration.json");
        apitdu.jsonElement.getAsJsonObject().get("marketShare").getAsJsonObject().getAsJsonArray("planAssignmentPercentage").remove(0);
        for(String key:marketSharePlanDetails.keySet()){
            JsonObject obj = new JsonObject();
            obj.addProperty("planCode", key);
            obj.addProperty("assignmentPercentage", Integer.parseInt(marketSharePlanDetails.get(key)));
            apitdu.jsonElement.getAsJsonObject().get("marketShare").getAsJsonObject().getAsJsonArray("planAssignmentPercentage").add(obj);
        }
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
    }

    @And("I initiated update auto assignment transaction Api")
    public void initiateAutoAssignmentTxnUpdateEndPoint() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateAutoAssignmentTxnEndPoint);
    }

    @And("I provide details for updating auto assignment transaction api")
    public void provideDetailsUpdateAutoAssignmentTxn(Map<String, String> data) {
        apitdu = apitdu.getJsonFromFile("crm/enrollment/updateAutoAssignmentTxn.json");
        for(String key:data.keySet()){
            if(key.equalsIgnoreCase("consumerId") && data.get(key).isEmpty())
                apitdu.jsonElement.getAsJsonObject().addProperty(key, Integer.parseInt(APIEnrollmentController.consumerId.get()));
            else if (key.equalsIgnoreCase("consumerId") && !data.get(key).isEmpty()){
                apitdu.jsonElement.getAsJsonObject().addProperty(key, String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId"))).replace(".0", ""));
            }
            else
                apitdu.jsonElement.getAsJsonObject().addProperty(key, data.get(key));
        }
        apitdu.jsonElement.getAsJsonObject().addProperty("autoAssignmentTransactionId", autoAssignmentTransactionId.get());

        requestParams.set(apitdu.jsonElement.getAsJsonObject());
    }

    @And("I run auto assignment transaction update Api")
    public void runAutoAssignmentTxnUpdateApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @And("I initiated get auto assignment transaction Api")
    public void initiateAutoAssignmentTxnGetEndPoint() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getAutoAssignmentTxnEndPoint);
    }

    @And("I provide details to get auto assignment transaction api")
    public void provideDetailsGetAutoAssignmentTxn(Map<String, String> data) {
        JsonObject req = new JsonObject();
        for(String key:data.keySet()){
            if(key.equalsIgnoreCase("consumerId") && data.get(key).isEmpty())
                req.addProperty(key, Integer.parseInt(APIEnrollmentController.consumerId.get()));
            else if (key.equalsIgnoreCase("consumerId") && !data.get(key).isEmpty()){
                req.addProperty(key, String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId"))).replace(".0", ""));
            }
            else
                req.getAsJsonObject().addProperty(key, data.get(key));
        }
        requestParams.set(req);
    }

    @And("I run auto assignment transaction get Api")
    public void runAutoAssignmentTxnGetApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        if(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").size()>0) {
            autoAssignmentTransactionId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("autoAssignmentTransactionId").getAsInt());
            serviceRequestId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("serviceRequestId").getAsInt());
            enrollmentDueDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("enrollmentDueDate").getAsString());
            taskId.set(serviceRequestId.get());
//            resolution = api.apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("resolution").getAsString();
//            status = api.apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("status").getAsString();
//            planCode = api.apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("planCode").getAsString();
        }
        System.out.println("Service Request Id : " + serviceRequestId.get());
        System.out.println("Enrollment Due Date: " + enrollmentDueDate.get());
        System.out.println(autoAssignmentTransactionId.get());
    }

    @Then("I verify auto assignment transaction with data")
    public void i_verify_auto_assignment_transaction_with_data(Map<String, String> data) {
        JsonObject enrollmentRecord = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject();
        for (String key : data.keySet()) {
            switch (key) {
                case "resolution":
                case "status":
                case "planCode":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(enrollmentRecord.get(key).isJsonNull(), key + " is not null! - FAIL!");
                    } else if (data.get(key).equalsIgnoreCase("not null")) {
                        assertTrue(!enrollmentRecord.get(key).isJsonNull(), key + " is null! - FAIL!");
                    } else {
                        Assert.assertEquals(data.get(key), enrollmentRecord.get(key).getAsString());
                    }
            }
        }
    }


    @And("I run below provided auto assignment plan configuration details")
    public void runProvidedAutoAssignmentPlanConfigurationDetails(Map<String, String> data) {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autoAssignmentTxnPlanConfigEndPoint);
        configData.set(data);
        List<Double> newAutoAssignmentPlanPercentages = new ArrayList<>();
        List<String> newAutoAssignmentPlanCodes = new ArrayList<>();

        for (String key : configData.get().keySet()) {
            switch (key.split("\\.")[1]) {
                case "planCode":
                    newAutoAssignmentPlanCodes.add(data.get(key));
                    break;
                case "planPercentage":
                    newAutoAssignmentPlanPercentages.add(Double.parseDouble(data.get(key)));
                    break;

            }
        }

        boolean oldPlanCodeExistsInDB = false;
        for (int i = 0; i < ExistingAutoAssignmentPlanCodes.get().size(); i++) {
                if (!newAutoAssignmentPlanCodes.contains(ExistingAutoAssignmentPlanCodes.get().get(i))) oldPlanCodeExistsInDB = true;
        }

        if (newAutoAssignmentPlanCodes.size() < ExistingAutoAssignmentPlanCodes.get().size()){
            System.out.println("Some of the Plan Codes in Auto Assignment Plan Percentage Configuration table should be deleted!");
        } else if (oldPlanCodeExistsInDB) {
            System.out.println("Unused Plan Codes in Auto Assignment Plan Percentage Configuration table should be deleted!");
        } else {
                int planPercentageId=0;
                for (int i = 0; i < newAutoAssignmentPlanCodes.size(); i++) {
                    System.out.println("*********************************");
                    System.out.println("Request Body " + i + " : ");
                    apitdu = apitdu.getJsonFromFile("crm/enrollment/createUpdateAutoAssignmentPlanConfiguration.json");
                    apitdu.jsonElement.getAsJsonObject().addProperty("planCode", newAutoAssignmentPlanCodes.get(i));

                    if (ExistingAutoAssignmentPlanCodes.get().contains(newAutoAssignmentPlanCodes.get(i))){
                        planPercentageId = ExistingAutoAssignmentPlanPercentageIds.get().get(ExistingAutoAssignmentPlanCodes.get().indexOf(newAutoAssignmentPlanCodes.get(i)));
                        apitdu.jsonElement.getAsJsonObject().addProperty("autoAssignmentPlanPercentageId", planPercentageId);
                    }
                    apitdu.jsonElement.getAsJsonObject().addProperty("planPercentage", newAutoAssignmentPlanPercentages.get(i));
                    requestParams.set(apitdu.jsonElement.getAsJsonObject());
                    System.out.println(requestParams);
                    API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
                }
            }
        }


    @And("I get existing auto assignment plan configuration details")
    public void getExistingAutoAssignmentPlanConfigurationDetails() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(fetchAutoAssignmentPlanConfigurationsEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        ExistingAutoAssignmentPlanPercentageIds.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.autoAssignmentPlanPercentageId",Integer.class));
        ExistingAutoAssignmentPlanPercentages.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.planPercentage"));
        ExistingAutoAssignmentPlanCodes.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.planCode"));
    }


    @When("I save the consumer ids and plan codes auto assigned to below consumers")
    public void saveTheConsumerIdsAndPlanCodeAssignedToConsumers(List<String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);

        for (String planCode: ExistingAutoAssignmentPlanCodes.get()) {
        if (!autoAssignedPlanCodes.keySet().contains(planCode)){
            autoAssignedPlanCodes.put(planCode,0);
            }
        }
        System.out.println("Plan Codes Distribution Before Auto Assignment: " + autoAssignedPlanCodes);

        String planCode;
        for (String consumer : data) {
            String consumerID = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumer).toString().replace(".0", "");
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentByConsumerId.replace("{consumerId}", consumerID));
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
            planCode = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].planCode");
                if (!autoAssignedPlanCodes.keySet().contains(planCode)){
                    autoAssignedPlanCodes.put(planCode,1);
                }else{
                    autoAssignedPlanCodes.put(planCode,autoAssignedPlanCodes.get(planCode)+1);
                }
        }
        System.out.println("Plan Codes Distribution After Auto Assignment: " + autoAssignedPlanCodes);
    }


    @Then("I verify the plans in market share are assigned according to plan percentage distribution")
    public void iVerifyThePlansInMarketShareAreAssignedAccordingToPlanPercentageDistribution(Map<String, String> data) {
        for (String key : data.keySet()) {
            String planCode = key.split("\\.")[0];
            if (key.split("\\.")[1].equalsIgnoreCase("Min")){
                Assert.assertTrue(autoAssignedPlanCodes.get(planCode) >= Integer.parseInt(data.get(key)));
            }else{
                Assert.assertTrue(autoAssignedPlanCodes.get(planCode) <= Integer.parseInt(data.get(key)));
            }
        }
    }
    @Then("I verify service request id is a non zero value")
    public void i_verify_service_request_id_is_a_non_zero_value() {
        int serviceRequestId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("serviceRequestId").getAsInt();
        Assert.assertTrue(serviceRequestId > 0);
        srIdFromLink.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.serviceRequestId").toString().replace("[", "").replace("]", "").replace(" ", ""));
    }

    @Then("I verify auto assignment transaction is not generated")
    public void i_verify_auto_assignment_transaction_is_not_generated() {
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data").size()==0);
    }

    @And("I run service request details get Api")
    public void initiateServiceRequestDetailsGetEndpoint() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskByTaskId.replace("{taskId}", String.valueOf(serviceRequestId.get())));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        taskDefaultDueDate.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("tasks[0].taskVO.defaultDueDate"));
        System.out.println("TASK DEFAULT DUE DATE: " + taskDefaultDueDate);
    }

    @And("I verify task management tasks with data")
    public void iVerifyTaskManagementTasks(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "Status":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").get("taskStatus").getAsString(), data.get(key));
                    break;
                case "Disposition":
                    if (data.get(key).isEmpty() || data.get(key).equals("null")) {
                        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO")
                                .getAsJsonArray("taskDetails").get(0).getAsJsonObject().get("selectionVarchar").isJsonNull(), key + " is not null! - FAIL!");
                    } else {
                        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO")
                                .getAsJsonArray("taskDetails").get(0).getAsJsonObject().get("selectionVarchar").getAsString(),data.get(key));
                    }
                    break;
                default:
                    System.out.println(key + " field either does not exist or needs to be implemented if exists! ");
                    break;
            }
        }
    }


    @And("I save changeAllowedUntil date in benefit status")
    public void saveChangeAllowedUntilDateInBenefitStatus() {
        changeAllowedUntil.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data[0].consumerActions[0].changeAllowedUntil"));
        changeAllowedUntil.set(changeAllowedUntil.get().substring(0,changeAllowedUntil.get().indexOf("T")));
        System.out.println("CHANGE ALLOWED UNTIL IN BENEFIT STATUS: " + changeAllowedUntil.get());
    }


    @Then("I verify auto assignment due date is correct")
    public void i_verify_auto_assignment_due_date_is_correct() {
        enrollmentDueDate.set(enrollmentDueDate.get().substring(0,enrollmentDueDate.get().indexOf("T")));
        Assert.assertEquals( enrollmentDueDate.get(), changeAllowedUntil.get());
    }


    @Then("I verify task default due date in service request details is correct")
    public void i_verify_task_default_due_date_in_service_request_details_is_correct() {
        Assert.assertEquals( taskDefaultDueDate.get(), enrollmentDueDate.get());
    }

    @Then("I verify tasks for newly created service request whose id is {int} greater than the original with data")
    public void i_verify_newly_created_service_request_ids_with_data(int count, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskByTaskId.replace("{taskId}", String.valueOf(serviceRequestId.get()+count)));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        for (String key : data.keySet()) {
            switch (key) {
                case "recordCount":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().get("recordCount").getAsString(), data.get(key));
                    break;
                case "taskStatus":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").get("taskStatus").getAsString(), data.get(key));
                    break;
                case "taskTypeId":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").get("taskTypeId").getAsString(), data.get(key));
                    break;
                case "[0].taskFieldId":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").
                            getAsJsonArray("taskDetails").get(0).getAsJsonObject().get("taskFieldId").getAsString(), data.get(key));
                    break;
                case "[0].selectionFieldName":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").
                            getAsJsonArray("taskDetails").get(0).getAsJsonObject().get("selectionFieldName").getAsString(), data.get(key));
                    break;
                case "[0].selectionVarchar":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").
                            getAsJsonArray("taskDetails").get(0).getAsJsonObject().get("selectionVarchar").getAsString(), data.get(key));
                    break;
                case "[1].taskFieldId":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").
                            getAsJsonArray("taskDetails").get(1).getAsJsonObject().get("taskFieldId").getAsString(), data.get(key));
                    break;
                case "[1].selectionFieldName":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").
                            getAsJsonArray("taskDetails").get(1).getAsJsonObject().get("selectionFieldName").getAsString(), data.get(key));
                    break;
                default:
                    System.out.println(key + " field either does not exist or needs to be implemented if exists! ");
                    break;
            }
        }
    }

    @Then("I verify no task of type id {string} is created")
    public void i_verify_no_task_of_type_id_is_created(String taskTypeId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(taskManagementBaseUri);
        taskId.set(taskId.get()+1);
    //    ++taskId;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getTaskByTaskId.replace("{taskId}", String.valueOf(taskId.get())));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Boolean isTaskTypeId16402created = false;

        try{
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("tasks").get(0).getAsJsonObject().getAsJsonObject("taskVO").get("taskTypeId").getAsString().equals("16402")) isTaskTypeId16402created = true;
        } catch (Exception e){
            if (!API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().get("status").getAsString().equalsIgnoreCase("no results returned")) isTaskTypeId16402created = true;
        }

        Assert.assertFalse(isTaskTypeId16402created, "Failed since Task Type Id of 16402 is created!");
    }

}
