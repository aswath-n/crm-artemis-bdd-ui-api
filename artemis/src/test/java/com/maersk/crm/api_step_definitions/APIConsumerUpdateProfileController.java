package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class APIConsumerUpdateProfileController extends CRMUtilities implements ApiBase {

    private String consumerSearchBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String updateConsumer = "app/crm/consumer";
    private String searchUpdatedConsumer = "app/crm/case/consumers";
    private String apiEventsURI = ConfigurationReader.getProperty("apiEventsURI");
    private String eventBaseURI = "/app/crm/event";

   /* private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    APITMProjectRestController projectRestCntrl=new APITMProjectRestController();*/

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsSearchConsumer = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiconsumerFirstName = ThreadLocal.withInitial(() -> "fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
    private final ThreadLocal<String> apiconsumerLastName = ThreadLocal.withInitial(() -> "ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
    private final ThreadLocal<String> apiconsumerDOB = ThreadLocal.withInitial(() -> "12/09/1991");

    @Given("I initiated update consumer API for profile search")
    public void initiateUpdateConsumerAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateConsumer);
    }

    @When("I provide information to update consumer for profile search")
    public void provideInfoToUpdateConsumer() {
        requestParams.set(getUpdateConsumerRequest());
    }

    @And("I run update consumer API for profile search")
    public void runUpdateConsumerAPI() {
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @And("I search for consumer profile updated using API")
    public void searchUpdatedConsumerProfile() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchUpdatedConsumer);

        JsonObject event = new JsonObject();
        event.addProperty("consumerId", "38977");
        requestParamsSearchConsumer.set(event);

        System.out.println(requestParamsSearchConsumer.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsSearchConsumer.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I am able to verify the updated consumer profile")
    public void verifyUpdatedConsumerProfile() {
        ArrayList resultList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");

        boolean recordFound = false;
        for (int i = 0; i < resultList.size(); i++) {
            HashMap resultData = (HashMap) resultList.get(i);
            List consumerList = (List) resultData.get("consumers");
            System.out.println("consumerList " + consumerList);
            for (int j = 0; j < consumerList.size(); j++) {
                HashMap consumerData = (HashMap) consumerList.get(i);
                if (consumerData.get("consumerFirstName").equals(apiconsumerFirstName.get())) {
                    assertEquals(consumerData.get("consumerLastName"), apiconsumerLastName.get());
                    assertEquals(consumerData.get("consumerDateOfBirth"), apiconsumerDOB.get());
                    recordFound = true;
                    break;
                }
            }
        }

        assertTrue(recordFound, "Verified Updated Consumer Profile");
    }

    @Then("I am able to verify the updated by information")
    public void verifyUpdatedByInformation() {
        ArrayList resultList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");

        boolean recordFound = false;
        for (int i = 0; i < resultList.size(); i++) {
            HashMap resultData = (HashMap) resultList.get(i);
            List consumerList = (List) resultData.get("consumers");
            System.out.println("consumerList " + consumerList);
            for (int j = 0; j < consumerList.size(); j++) {
                HashMap consumerData = (HashMap) consumerList.get(i);
                if (consumerData.get("consumerFirstName").equals(apiconsumerFirstName.get())) {
                    assertNotNull(consumerData.get("updatedOn"));
                    assertNotNull(consumerData.get("updatedBy"));
                    recordFound = true;
                    break;
                }
            }
        }

        assertTrue(recordFound, "Verified Updated Consumer Profile");
    }

    private JsonObject getUpdateConsumerRequest() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiUpdateConsumer.json");

        System.out.println("Consumer First Name :" + apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());

        System.out.println("Consumer Last Name :" + apiconsumerLastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());

        System.out.println("Consuumer Date of Birth :" + apiconsumerDOB.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", apiconsumerDOB.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
        correlationId.set((API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", API_THREAD_LOCAL_FACTORY.getProjectRestCntrlThreadLocal().userIdFromApigee);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", System.currentTimeMillis());

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @And("I change Physical Address by consumerId {string} with data")
    public void I_change_Physical_Address_by_consumerId_with_data(String consumerId, Map<String, String> data) {
        if (consumerId.isEmpty()) {
            consumerId = APIConsumerPopulationDmnController.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiEventsURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventBaseURI);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/updatePhysicalAddress.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("externalRefId", consumerId);
        for (String key : data.keySet()) {
            switch (key) {
                case "addressType":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("addressType", data.get(key));
                    break;
                case "addressStreet1":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("addressStreet1", data.get(key));
                    break;
                case "addressStreet2":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("addressStreet2", data.get(key));
                    break;
                case "addressCity":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("addressCity", data.get(key));
                    break;
                case "addressState":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("addressState", data.get(key));
                    break;
                case "addressCounty":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("addressCounty", data.get(key));
                    break;
                case "addressZip":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("addressZip", data.get(key));
                    break;
                case "updatedBy":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("updatedBy", data.get(key));
                    break;
                case "eventCreatedOn":
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").getAsJsonObject("dataObject").addProperty("eventCreatedOn",
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get(key) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'")));
                    break;
            }
        }
        String payload = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("payload", payload);
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(json);
    }
}
