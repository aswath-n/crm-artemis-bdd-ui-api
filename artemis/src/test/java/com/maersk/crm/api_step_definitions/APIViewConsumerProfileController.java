package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class APIViewConsumerProfileController extends CRMUtilities implements ApiBase {

    private final String consumerRecordURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private final String createConsumerRecord = "app/crm/consumer";
    private final String getConsumerSearchList = "app/crm/case/consumers";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsConsumerCreation = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(HashMap::new);

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(()->newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(()->newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> dateOfBirth = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);


    @Given("I created consumer through API for viewing consumer profile")
    public void createConsumerForViewConsumerProfile() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerRecord);
        requestParamsConsumerCreation.set(generateConsumerRecordAPI());
        System.out.println(requestParamsConsumerCreation.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsConsumerCreation.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @When("I initiated consumer profile search for viewing consumer profile")
    public void initiatedConsumerProfileSearch() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerSearchList);
    }

    @When("I searched for the created Consumer for viewing consumer profile")
    public void searchedForConsumer() {
        requestParams.set(generateConsumerSearchRequest(firstName.get()));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify the consumer search result for viewing consumer profile")
    public void verifyTheConsumerSearchResult() {
        List result = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result");
        System.out.println("Result: " + result);

        assertNotNull(result);

        HashMap consumersData = (HashMap) result.get(0);
        List consumersList = (List) consumersData.get("consumers");
        HashMap consumerIdData = (HashMap) consumersList.get(0);
        String firstNameSearched = consumerIdData.get("consumerFirstName").toString();
        System.out.println("First Name from consumer search: " + firstNameSearched);

        assertEquals(firstName.get(), firstNameSearched);
    }

    @Then("I verify the consumer profile for viewing consumer profile")
    public void verifyTheConsumerProfile() {
        List resultList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");
        System.out.println("Result: " + resultList);

        for (int i = 0; i < resultList.size(); i++) {
            HashMap consumersData = (HashMap) resultList.get(i);
            List consumersList = (List) consumersData.get("consumers");
            HashMap consumerIdData = (HashMap) consumersList.get(0);
            //assertNotNull(consumerIdData.get("consumerId"));
            assertEquals(firstName.get(), consumerIdData.get("consumerFirstName"));
            assertEquals(lastName.get(), consumerIdData.get("consumerLastName"));
            assertEquals(dateOfBirth, consumerIdData.get("consumerDateOfBirth"));

            ArrayList genderValues = new ArrayList();
            genderValues.add("Male");
            genderValues.add("Female");
            assertTrue(genderValues.contains(consumerIdData.get("genderCode")));

            assertEquals(consumerSSN.get(), consumerIdData.get("consumerSSN"));
            assertTrue(requestParamsConsumerCreation.get().get("preferredLanguage").toString().contains(consumerIdData.get("preferredLanguage").toString()));

            ArrayList consumerStatusValues = new ArrayList();
            consumerStatusValues.add("Inactive");
            consumerStatusValues.add("Active");
            consumerStatusValues.add("Future");
            assertTrue(consumerStatusValues.contains(consumerIdData.get("consumerStatus")));
        }
    }

    private JsonObject generateConsumerRecordAPI() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");

        long currentDateTime = System.currentTimeMillis();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        dateOfBirth.set(formatter1.format(currentDateTime));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", currentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", firstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", lastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", dateOfBirth.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", consumerSSN.get());
        ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", newConsumer.get().get("phone").toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", newConsumer.get().get("zip").toString());

        Long correlationId = Long.parseLong(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private JsonObject generateConsumerSearchRequest(String fn) {
        JsonObject request = new JsonObject();

        request.addProperty("consumerFirstName", fn);
        request.addProperty("consumerLastName", "");
        request.addProperty("consumerMiddleName", "");
        request.addProperty("consumerDateOfBirth", "");
        request.addProperty("consumerSSN", "");
        request.addProperty("consumerIdentificationNo", "");
        request.addProperty("consumerAddress1", "");
        request.addProperty("consumerAddress2", "");
        request.addProperty("consumerCity", "");
        request.addProperty("consumerState", "");
        request.addProperty("consumerCounty", "");
        request.addProperty("consumerPhoneNumber", "");
        request.addProperty("consumerEmail", "");
        request.addProperty("zipCode", "");
        request.addProperty("caseType", "");
        request.addProperty("caseId", (String) null);
        request.addProperty("caseIdentificationNumber", "");
        request.addProperty("consumerType", "");
        request.addProperty("consumerId", (String) null);

        return request;
    }
}
