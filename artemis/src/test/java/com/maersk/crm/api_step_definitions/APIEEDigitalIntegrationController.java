package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

import java.util.*;

import static org.testng.Assert.*;

public class APIEEDigitalIntegrationController extends CRMUtilities implements ApiBase {

    private String baseURI = ConfigurationReader.getProperty("apiEligibilityURI");
    private String getDigitalEnrollmentProviderDetailsEndPoint = "mars/digital/enrollmentproviders?consumerIds={consumerIds}";
    private String digitalPlanTransferEndPoint = "mars/digital/plantransfer";
    private String getEnrollmentsForConsumerEndPoint = "mars/eb/enrollments/{consumerId}";
    private String createEnrollmentBenefitStatusEndPoint = "mars/eb/enrollments/benefitstatues";
    private String getConsumerEligiblePlansEndPoint = "mars/digital/eligiblePlan?consumerIds={consumerId}";
    private String getDigitalEnrollmentStatusEndPoint = "mars/digital/enrollmentstatus?consumerids={consumerIds}";
    private String getDigitalEnrollmentPlansEndPoint = "mars/digital/enrollmentplans?consumerIds={consumerIds}";
    private String getEligibleEnrollmentPlansEndPoint = "mars/eb/eligibleplans/enroll?consumerIds={consumerIds}&from=0&size=10";
    private String getPlansAvailableForPlanTransfer = "mars/eb/eligiblePlans?consumerIds={consumerIds}&from=0&size=10";


    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();

    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private static final ThreadLocal <String> consumerIds = ThreadLocal.withInitial(()->"");

    public void setConsumerIds(String consumerIds) {
        this.consumerIds.set(consumerIds);
    }

    @Given("I initiated get digital enrollment provider by consumer ids {string}")
    public void initiatedGetConsumersDetails(String commaSeperatedConsumerIds) {
        if (commaSeperatedConsumerIds.isEmpty())
            commaSeperatedConsumerIds = consumerIds.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getDigitalEnrollmentProviderDetailsEndPoint = getDigitalEnrollmentProviderDetailsEndPoint.replaceAll("\\{consumerIds}", commaSeperatedConsumerIds);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getDigitalEnrollmentProviderDetailsEndPoint);
    }

    @When("I run get digital enrollment provider API")
    public void runGetConsumersDetails() {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify digital enrollment provider details for {string}")
    public void verifyConsumersDetails(String enrollmentType, Map<String, String> data) {

        JsonArray consumersData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        JsonObject consumerData = consumersData.get(0).getAsJsonObject();

        if (enrollmentType.equalsIgnoreCase("currentEnrollment")) {
            assertTrue(consumerData.getAsJsonArray("currentEnrollmentProviders").get(0).getAsJsonObject().get("providerFirstName") != null);
            assertTrue(consumerData.getAsJsonArray("currentEnrollmentProviders").get(0).getAsJsonObject().get("providerLastName") != null);
            assertTrue(consumerData.getAsJsonArray("currentEnrollmentProviders").get(0).getAsJsonObject().get("providerNpiNumber") != null);
            assertTrue(consumerData.getAsJsonArray("currentEnrollmentProviders").get(0).getAsJsonObject().has("planProviderID"));
            assertTrue(consumerData.getAsJsonArray("currentEnrollmentProviders").get(0).getAsJsonObject().get("providerTypeCd") != null);
            assertEquals(consumerData.getAsJsonArray("currentEnrollmentProviders").get(0).getAsJsonObject().get("consumerId").getAsString(), consumerIds.get());
            for(String key:data.keySet()){
                switch(key){
                    case "providerId":
                        assertEquals(consumerData.getAsJsonArray("currentEnrollmentProviders").get(0).getAsJsonObject().get("planProviderID").getAsString(),
                                API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)));
                        break;
                }
            }

            assertTrue(consumerData.has("futureEnrollmentProviders"));
        } else if (enrollmentType.equalsIgnoreCase("futureEnrollment")) {
            assertTrue(consumerData.getAsJsonArray("futureEnrollmentProviders").get(0).getAsJsonObject().get("providerFirstName") != null);
            assertTrue(consumerData.getAsJsonArray("futureEnrollmentProviders").get(0).getAsJsonObject().get("providerLastName") != null);
            assertTrue(consumerData.getAsJsonArray("futureEnrollmentProviders").get(0).getAsJsonObject().get("providerNpiNumber") != null);
            assertTrue(consumerData.getAsJsonArray("futureEnrollmentProviders").get(0).getAsJsonObject().get("providerTypeCd") != null);
            assertTrue(consumerData.getAsJsonArray("futureEnrollmentProviders").get(0).getAsJsonObject().has("planProviderID"));
            assertEquals(consumerData.getAsJsonArray("futureEnrollmentProviders").get(0).getAsJsonObject().get("consumerId").getAsString(), consumerIds.get());

            for(String key:data.keySet()){
                switch(key){
                    case "providerId":
                        assertEquals(consumerData.getAsJsonArray("futureEnrollmentProviders").get(0).getAsJsonObject().get("planProviderID").getAsString(),
                                API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)));
                        break;
                }
            }
            assertTrue(consumerData.has("currentEnrollmentProviders"));
        }
    }

    @Given("I initiated digital plan transfer API")
    public void initiatedDigitalPlanTransfer() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(digitalPlanTransferEndPoint);
    }

    @When("I provide details for digital plan transfer API {string}, {string}, {string}, {string}")
    public void provideDetailsForDigitalPlanTransfer(String planCode, String disenrollmentReason, String planProviderID, String consumerId) {
      if(consumerId.isEmpty())
          consumerId = consumerIds.get();
       JsonObject digitalPlanTransferObj = new JsonObject();
        digitalPlanTransferObj.addProperty("planCode", planCode);
        digitalPlanTransferObj.addProperty("disenrollmentReason", disenrollmentReason);
        if(planProviderID.equalsIgnoreCase("null"))
            digitalPlanTransferObj.addProperty("planProviderID", (String)null);
        else
            digitalPlanTransferObj.addProperty("planProviderID", planProviderID);
        digitalPlanTransferObj.addProperty("consumerId", consumerId);

        requestParams.set(digitalPlanTransferObj);

    }

    @When("I run Digital Plan Transfer API with or without {string} random contactRecordId and channel {string} with data")
    public void I_run_Digital_Plan_Transfer_API_with_random_contactRecordId_and_channel_with_data(String withContactRecordId, String channel, Map<String, List<String>> data) {
        Random r = new Random();
        JsonObject digitalPlanTransferObj = new JsonObject();
        if (withContactRecordId.equals("with")) {
            digitalPlanTransferObj.addProperty("contactRecordId", Integer.toString(r.nextInt(10000000)));
        }
        if (!channel.equals("null")) {
            digitalPlanTransferObj.addProperty("channel", channel);
        }
        digitalPlanTransferObj.add("enrollmentInfo", new JsonArray());
        for (String eachConsumer : data.keySet()) {
            String id = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(eachConsumer).toString();
            String consumerId = id.contains(".") ? id.substring(0, id.indexOf(".")) : id;
            //String consumerId = id.substring(0, id.indexOf("."));
            List<String> currentConsumerInfo = data.get(eachConsumer);
            JsonObject currentConsumerDataJson = new JsonObject();
            currentConsumerDataJson.addProperty("consumerId", consumerId);
            if(currentConsumerInfo.get(0).equalsIgnoreCase("null")){
                currentConsumerDataJson.add("disenrollmentReason", JsonNull.INSTANCE );
            }else{
                currentConsumerDataJson.addProperty("disenrollmentReason", currentConsumerInfo.get(0));
            }
            currentConsumerDataJson.addProperty("planCode", currentConsumerInfo.get(1));
            currentConsumerDataJson.add("selectedProviders", new JsonArray());
            if(currentConsumerInfo.get(2).equalsIgnoreCase("NA")){
                currentConsumerDataJson.remove("selectedProviders");
            }else if (!currentConsumerInfo.get(2).equalsIgnoreCase("null") && !currentConsumerInfo.get(2).isEmpty()) {
                for(String planProviderId:currentConsumerInfo.get(2).split(",")){
                    JsonObject planProviderIdJsonObj = new JsonObject();
                    planProviderIdJsonObj.addProperty("planProviderID", planProviderId);
                    currentConsumerDataJson.getAsJsonArray("selectedProviders")
                            .add(planProviderIdJsonObj);
                }
            }else if(currentConsumerInfo.get(2).equalsIgnoreCase("null")){
                currentConsumerDataJson.remove("selectedProviders");
                currentConsumerDataJson.add("selectedProviders", JsonNull.INSTANCE);
            }
            currentConsumerDataJson.addProperty("consumerAction", currentConsumerInfo.get(3));

            digitalPlanTransferObj.getAsJsonArray("enrollmentInfo").add(currentConsumerDataJson);
        }
        initiatedDigitalPlanTransfer();
        requestParams.set(digitalPlanTransferObj);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @When("I run digital plan transfer API with expected status {string}")
    public void runDigitalPlanTransferAPIWithExpectedStatus(String status) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), status);
    }

    @Then("I validate the status {string} and consumer id {string} in digital plan transfer api response")
    public void validateDigitalTransferAPI(String status, String consumerId) {
        if (consumerId.isEmpty())
            consumerId = consumerIds.get();
        // assertEquals(api.apiJsonObject.getAsJsonObject("data").get("status"), status);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data").get("status").getAsString(), status);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data").get("consumerId").getAsString(), consumerId);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data").get("effectiveDate").getAsString(), apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data").get("successInd").getAsString(), "Y");
    }

    @Then("I validate error message {string} in digital plan transfer api response")
    public void validateErrorsInDigitalTransferAPI(String message) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("errors").get(0).getAsJsonObject().get("message").getAsString(), message);
    }

    @Given("I initaite get enrollments for consumerID {string}")
    public void initiatedGetEnrollmentsForConsumer(String consumerId) {
        if (consumerId.isEmpty())
            consumerId = consumerIds.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getEnrollmentsForConsumerEndPoint = getEnrollmentsForConsumerEndPoint.replaceAll("\\{consumerId}", consumerId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnrollmentsForConsumerEndPoint);
    }

    @When("I run get enrollments for consumer with expected status {string}")
    public void runGetEnrollmentsForConsumer(String status) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), status);
    }

    @Then("I verify previous enrollment is ended with current date and with end reason {string}")
    public void validatePreviousEnrollmentEndedWIthPresentDate(String planEndDateReason) {
        JsonObject presentEnrollment;
        JsonObject previousEnrollment;
        JsonArray enrollments = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        if (enrollments.get(0).getAsJsonObject().get("enrollmentId").getAsInt() > enrollments.get(1).getAsJsonObject().get("enrollmentId").getAsInt()) {
            presentEnrollment = enrollments.get(0).getAsJsonObject();
            previousEnrollment = enrollments.get(1).getAsJsonObject();
        } else {
            presentEnrollment = enrollments.get(1).getAsJsonObject();
            previousEnrollment = enrollments.get(0).getAsJsonObject();
        }

        assertEquals(previousEnrollment.get("endDate").getAsString(), apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 0));
        assertEquals(previousEnrollment.get("planEndDateReason").getAsString(), planEndDateReason);

    }

    @Then("I verify present enrollment details")
    public void validatePresentEnrollmentDetails() {
        JsonObject presentEnrollment;
        JsonObject previousEnrollment;
        JsonArray enrollments = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        if (enrollments.get(0).getAsJsonObject().get("enrollmentId").getAsInt() > enrollments.get(1).getAsJsonObject().get("enrollmentId").getAsInt()) {
            presentEnrollment = enrollments.get(0).getAsJsonObject();
            previousEnrollment = enrollments.get(1).getAsJsonObject();
        } else {
            presentEnrollment = enrollments.get(1).getAsJsonObject();
            previousEnrollment = enrollments.get(0).getAsJsonObject();
        }

        assertEquals(presentEnrollment.get("startDate").getAsString(), apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0));
        assertEquals(presentEnrollment.get("enrollmentType").getAsString(), "MEDICAL");
        assertEquals(presentEnrollment.get("programTypeCode").getAsString(), "MEDICAID");
        assertEquals(presentEnrollment.get("subProgramTypeCode").getAsString(), APIEligibilityController.subProgramTypeCode);
        assertEquals(presentEnrollment.get("txnStatus").getAsString(), "Ready_To_Upload");
        assertEquals(presentEnrollment.get("channel").getAsString(), "Web");
        assertEquals(presentEnrollment.get("selectionType").getAsString(), "Change");

    }

    @Given("I initiated create enrollment benefit status API")
    public void initiatedCreateEnrollmentBenefitStatusAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createEnrollmentBenefitStatusEndPoint);
    }

    @When("I provide details for create enrollment benefit status API")
    public void provideDetailsForDigitalPlanTransfer(DataTable datatable) {
        Map<String, String> data = datatable.asMap(String.class, String.class);
        apitdu = apitdu.getJsonFromFile("crm/enrollment/createEnrollmentBenefitStatus.json");
        apitdu.jsonElement.getAsJsonObject().addProperty("consumerId", consumerIds.get());
        String value = "";
        for (String key : data.keySet()) {
            switch (key) {
                case "consumerId":
                    if (data.get(key).isEmpty())
                        apitdu.jsonElement.getAsJsonObject().addProperty("consumerId", consumerIds.get());
                    else
                        apitdu.jsonElement.getAsJsonObject().addProperty("consumerId", data.get(key));
                    break;
                case "benefitStatus":
                    apitdu.jsonElement.getAsJsonObject().addProperty("benefitStatus", data.get(key));
                    break;
                case "startDate":
                    apitdu.jsonElement.getAsJsonObject().addProperty("eligStartDate", getDate(data.get(key)));
                    break;
                case "consumerAction":
                    apitdu.jsonElement.getAsJsonObject().addProperty("consumerAction", data.get(key));
                    break;
                case "eligStatusCode":
                    apitdu.jsonElement.getAsJsonObject().addProperty("eligStatusCode", data.get(key));
                    break;
            }
        }
        apitdu.jsonElement.getAsJsonObject().addProperty("enrollmentId", APIEnrollmentController.enrollmentId.get());
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
    }

    @When("I run create enrollment benefit status API with expected status {string}")
    public void runCreateEnrollmentBenefitStatus(String expectedStatus) {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), expectedStatus);
    }

    @Then("I verify consumer benefit status id in response")
    public void verifyConsumerBenefitStatus() {

        int benefitStatusId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("data").get("consumerbenefitStatusId").getAsInt();
        assertTrue(benefitStatusId > 0);
    }

    @Given("I initiated get consumer eligible plans for consumer {string}")
    public void initiatedGetConsumerEligiblePlansAPI(String commaSeperatedConsumerIds) {
        String consumersList = "";
        if (commaSeperatedConsumerIds.isEmpty()) {
            commaSeperatedConsumerIds = consumerIds.get();
        }
        for (String consumerId : commaSeperatedConsumerIds.split(",")) {
            String id = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString();
            if (id.contains("."))
            consumersList = consumersList + (consumersList.length() == 0 ? "" : "&consumerIds=") + id.substring(0, id.indexOf("."));
            else {
                consumersList = consumersList + (consumersList.length() == 0 ? "" : "&consumerIds=") + id;
            }
        }
        System.out.println("consumersList = " + consumersList);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getConsumerEligiblePlansEndPoint = getConsumerEligiblePlansEndPoint.replaceAll("\\{consumerId}", consumersList);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerEligiblePlansEndPoint);
    }

    @When("I run get consumer eligible plans API")
    public void runGetConsumerEligiblePlansAPI() {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify consumer eligible plans for consumer {string}")
    public void verifyConsumerEligiblePlans(String commaSeperatedConsumerIds) {
        List<String> consumersList = new ArrayList<>();
        if (commaSeperatedConsumerIds.isEmpty()) {
            commaSeperatedConsumerIds = consumerIds.get();
        }
        for (String consumerId : commaSeperatedConsumerIds.split(",")) {
            String id = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString();
            if (id.contains("."))
                consumersList.add(id.substring(0, id.indexOf(".")));
            else
                consumersList.add(id);
        }
        JsonArray plans = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        assertTrue(plans.size() > 0);
        for (int i = 0; i < plans.size() && 1 < consumersList.size(); i++) {
            JsonObject plan = plans.get(i).getAsJsonObject();
            assertTrue(consumersList.contains(plan.get("consumerId").getAsString()));
            for (JsonElement eachPlan : plan.getAsJsonArray("eligiblePlans")) {
                assertNotNull(eachPlan.getAsJsonObject().get("planName"));
                assertEquals(eachPlan.getAsJsonObject().get("planCode").getAsString().length(), 2);
            }
        }
    }

    public String getDate(String date) {
        String dateToReturn = "";
        switch (date) {
            case "current":
                date = apitdu.addDaysToPresentDate("yyyy-MM-dd", 0, 0, 0);
                break;
            case "past":
                dateToReturn = apitdu.addDaysToPresentDate("yyyy-MM-dd", -29, 0, 0);
                break;
            case "future":
                dateToReturn = apitdu.addDaysToPresentDate("yyyy-MM-dd", 10, 10, 1);
                break;
            case "1stDayofNextMonth":
                dateToReturn = apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0);
                break;
            case "1stDayofLastMonth":
                dateToReturn = apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToReduceForTheFirstOfThisMonth(), -1, 0);
                break;
            case "1stDayofPresentMonth":
                dateToReturn = apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToReduceForTheFirstOfThisMonth(), 0, 0);
                break;
        }
        return dateToReturn;
    }

    @Given("I initiated get digital enrollment status by consumer ids {string}")
    public void initiatedGetDigitalEnrollmentStatusAPI(String commaSeperatedConsumerIds) {
        if (commaSeperatedConsumerIds.isEmpty())
            commaSeperatedConsumerIds = consumerIds.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getDigitalEnrollmentStatusEndPoint = getDigitalEnrollmentStatusEndPoint.replaceAll("\\{consumerIds}", commaSeperatedConsumerIds);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getDigitalEnrollmentStatusEndPoint);
    }

    @When("I run get digital enrollment status API")
    public void runGetDigitalEnrollmentStatusAPI() {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify digital enrollment status details")
    public void verifyDigitalEnrollmentStatusDetails(DataTable datatable) {
        Map<String, String> data = datatable.asMap(String.class, String.class);
        String expConsumerIds = consumerIds.get();
        if (!data.get("consumerIds").isEmpty())
            expConsumerIds = data.get("consumerIds");
        JsonArray enrollmentStatusArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        for (int i = 0; i < enrollmentStatusArray.size(); i++) {
            JsonObject enrollmentStatusData = enrollmentStatusArray.get(i).getAsJsonObject();
            assertEquals(enrollmentStatusData.get("consumerId").getAsString(), expConsumerIds);
            assertEquals(enrollmentStatusData.get("consumerActions").getAsString(), data.get("consumerAction"));
            assertEquals(enrollmentStatusData.get("eligibilityStatus").getAsString(), data.get("eligStatusCode"));
            assertEquals(enrollmentStatusData.get("enrollmentStatus").getAsString(), data.get("benefitStatus"));
            assertTrue(enrollmentStatusData.get("changeAllowedUntil") != null);
            assertTrue(enrollmentStatusData.get("changeAllowedFrom") != null);
        }
    }

    @Given("I initiated get digital enrollment plans by consumer ids {string}")
    public void initiatedGetDigitalEnrollmentPlansAPI(String commaSeperatedConsumerIds) {
        if (commaSeperatedConsumerIds.isEmpty())
            commaSeperatedConsumerIds = consumerIds.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getDigitalEnrollmentPlansEndPoint = getDigitalEnrollmentPlansEndPoint.replaceAll("\\{consumerIds}", commaSeperatedConsumerIds);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getDigitalEnrollmentPlansEndPoint);
    }

    @When("I run get digital enrollment plans API")
    public void runGetDigitalEnrollmentPlansAPI() {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify digital enrollment plans details")
    public void verifyDigitalEnrollmentPlansDetails(DataTable datatable) {
        List<Map<String, String>> dataList = datatable.asMaps(String.class, String.class);
        String expConsumerIds = consumerIds.get();
        if (!dataList.get(0).get("consumerIds").isEmpty())
            expConsumerIds = dataList.get(0).get("consumerIds");
        JsonArray enrollmentPlansArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        Map<String, Map<String, String>> expectedChannelDetails = APIEnrollmentController.getEnrollmentChannelDetails();
        assertTrue(enrollmentPlansArray.size() > 0, "No enrollment is returned");
        for (int i = 0; i < enrollmentPlansArray.size(); i++) {
            JsonObject enrollmentStatusData = enrollmentPlansArray.get(i).getAsJsonObject();
            assertEquals(enrollmentStatusData.get("consumerId").getAsString(), expConsumerIds);
            assertTrue(enrollmentStatusData.get("planCode") != null);
            assertTrue(enrollmentStatusData.get("planStartDate") != null);
            assertTrue(enrollmentStatusData.has("planEndDate"));
            assertTrue(enrollmentStatusData.get("channel") != null);
            String plaCode = enrollmentStatusData.get("planCode").getAsString();
            if(enrollmentStatusData.get("timeframe").getAsString().equalsIgnoreCase("current")){
                assertEquals("Active".toLowerCase(), expectedChannelDetails.get(plaCode).get("enrollmentTimeframe").toLowerCase());
            }else{
                assertEquals(enrollmentStatusData.get("timeframe").getAsString().toLowerCase(), expectedChannelDetails.get(plaCode).get("enrollmentTimeframe").toLowerCase());
            }

            assertEquals(enrollmentStatusData.get("channel").getAsString().toLowerCase(), expectedChannelDetails.get(plaCode).get("channel").toLowerCase());
        }
    }

    @Then("I verify digital enrollment plans details with data")
    public void I_verify_digital_enrollment_plans_details_with_data(Map<String, String> data) {
        JsonArray planTransferResults = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonArray.getAsJsonArray();
        for (String key : data.keySet()) {
            int indexOfObjectInJsonArray = Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));
            String fieldName = key.substring(key.indexOf(".") + 1);
            switch (fieldName) {
                case "consumerId":
                    assertEquals(planTransferResults.get(indexOfObjectInJsonArray).getAsJsonObject().get(fieldName).getAsString(),
                            API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString().split("\\.")[0], fieldName + " mismatch!");
                    break;
                case "effectiveDate":
                    assertEquals(planTransferResults.get(indexOfObjectInJsonArray).getAsJsonObject().get(fieldName).getAsString(),
                            apitdu.getStartDate(data.get(key)), fieldName + " mismatch!");
                    break;
                default:
                    assertEquals(planTransferResults.get(indexOfObjectInJsonArray).getAsJsonObject().get(fieldName).getAsString(),
                            data.get(key), fieldName + " mismatch!");
            }
        }

    }

    @Given("I initiated get eligible and enrollment plans by consumer ids {string}")
    public void initiatedGetEligibleEnrollmentPlansAPI(String commaSeperatedConsumerIds) {
        if (commaSeperatedConsumerIds.isEmpty())
            commaSeperatedConsumerIds = consumerIds.get();
        else if (!commaSeperatedConsumerIds.contains(","))
            commaSeperatedConsumerIds = String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(commaSeperatedConsumerIds)).replace(".0", "");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getEligibleEnrollmentPlansEndPoint = getEligibleEnrollmentPlansEndPoint.replaceAll("\\{consumerIds}", commaSeperatedConsumerIds);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEligibleEnrollmentPlansEndPoint);
    }

    @Given("I initiated get available plans  for Plan Transfer by consumer ids {string}")
    public void initiatedGetAvailablePlansForPlanTransferAPI(String commaSeperatedConsumerIds) {
        if (commaSeperatedConsumerIds.isEmpty())
            commaSeperatedConsumerIds = consumerIds.get();
        else if (!commaSeperatedConsumerIds.contains(","))
            commaSeperatedConsumerIds = String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(commaSeperatedConsumerIds)).replace(".0", "");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        getPlansAvailableForPlanTransfer = getPlansAvailableForPlanTransfer.replaceAll("\\{consumerIds}", commaSeperatedConsumerIds);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getPlansAvailableForPlanTransfer);
    }

    @When("I run get eligible and enrollment plans API")
    public void runGetEligibleEnrollmentPlansAPI() {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }


    @Then("I verify eligible and enrollment plans details")
    public void verifyEligibleEnrollmentPlansDetails(DataTable datatable) {
        List<Map<String, String>> dataList = datatable.asMaps(String.class, String.class);
        JsonArray enrollmentPlansArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        for (int i = 0; i < enrollmentPlansArray.size(); i++) {
            JsonObject enrollmentStatusData = enrollmentPlansArray.get(i).getAsJsonObject();
            assertEquals(enrollmentStatusData.get("planName").getAsString(), dataList.get(i).get("planName"));
            assertTrue(enrollmentStatusData.get("planId").getAsInt() > 0);
            assertEquals(enrollmentStatusData.get("enrollmentSegmentEndDate").getAsJsonNull(), new JsonNull());
            String expStartDate = "";
            if (dataList.get(i).containsKey("populationType") && dataList.get(i).get("populationType").equalsIgnoreCase("NEWBORN"))
                expStartDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", -apitdu.getNumberOfDaysToReduceForTheFirstOfThisMonth(), 0, 0);
            else
                expStartDate = apitdu.addDaysToPresentDate("yyyy-MM-dd", apitdu.getNumberOfDaysToAddForNextFirstOfMonth(), 0, 0);
            assertEquals(expStartDate, enrollmentStatusData.get("enrollmentSegmentStartDate").getAsString());
        }
    }

    public List<String> getPlanCodes(boolean isForPlanTransfer){
        if(isForPlanTransfer){
            initiatedGetAvailablePlansForPlanTransferAPI("");
        }else{
            initiatedGetEligibleEnrollmentPlansAPI("");
        }

        runGetEligibleEnrollmentPlansAPI();
        List<String> plaCodes = new ArrayList<>();
        JsonArray dataArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        for(int i=0;i<dataArray.size();i++){
            plaCodes.add(dataArray.get(i).getAsJsonObject().get("planCode").getAsString());
        }
        return plaCodes;
    }



}