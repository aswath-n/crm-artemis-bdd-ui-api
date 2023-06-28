package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class APIContactEventsNoEmptyStringContoller extends CRMUtilities implements ApiBase {

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseConsumerForEvents = "app/crm/caseloader";
    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> correlationId= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> firstName= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastName= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerSSN= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String >consumerDob= ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressCity = ThreadLocal.withInitial(()->"Dallas");
    private final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(()->"Dallas");
    private final ThreadLocal<String> addressState = ThreadLocal.withInitial(()->"TX");
    private final ThreadLocal<String> addressStreet1 = ThreadLocal.withInitial(()->getRandomNumber(4) + " test ln");
    private final ThreadLocal<String> emailAddress = ThreadLocal.withInitial(()->getRandomString(6) + "@gmail.com");

    @Given("I Initiated Case Loader API for no empty string check")
    public void initiateCaseLoaderNoEmptyString() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);
    }

    @When("I create new consumer using case loader for no empty string check")
    public void createConsumerNoEmptyString() {
        firstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        lastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", firstName.get(), lastName.get(), consumerSSN.get(),
                consumerDob.get(), "", "", ""));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        correlationId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        eventsCorrelationIdEndPoint=eventsCorrelationIdEndPoint+correlationId.get();

        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @When("I will take correlation Id for {string} for {string} no empty strings event check")
    public void i_will_take_trace_Id_for_Events(String event, String eventType) {
        correlationId.set(EventsUtilities.getLogs("traceid", eventType).get(1));
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @Then("I will verify an {string} is created with no empty string fields in payload for {string} event")
    public void verifyThePhoneEmailAddressEventsNoEmptyString(String eventName, String eventType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        ////apitmEventController.getAuthenticationToken("BLCRM", "CRM");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        if (eventType.equals("phone")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

                if (payLoadData.get("recordType").getAsString().equalsIgnoreCase("phone")) {
                    if (eventName.contains("SAVE"))
                        assertEquals(payLoadData.get("action").getAsString(), "Create");
                    else
                        assertEquals(payLoadData.get("action").getAsString(), "Update");

                    assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("Phone"));
                    assertNotNull(payLoadData.get("dataObject"));
                    JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                    assertTrue(dataObjectData.get("associatedCaseMember").isJsonNull());
                    assertTrue(dataObjectData.get("effectiveEndDate").isJsonNull());
                    assertTrue(dataObjectData.get("comments").isJsonNull());

                    if (eventsData.get("eventName").equals(eventName)) {
                        assertEquals(eventsData.get("status"), "success");
                        break;
                    }
                }
            }
        } else if (eventType.equals("email")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

                if (payLoadData.get("recordType").getAsString().equalsIgnoreCase("email")) {
                    if (eventName.contains("SAVE"))
                        assertEquals(payLoadData.get("action").getAsString(), "Create");
                    else
                        assertEquals(payLoadData.get("action").getAsString(), "Update");

                    assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("email"));
                    assertNotNull(payLoadData.get("dataObject"));
                    JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                    assertTrue(dataObjectData.get("associatedCaseMember").isJsonNull());
                    assertTrue(dataObjectData.get("effectiveEndDate").isJsonNull());

                    if (eventsData.get("eventName").equals(eventName)) {
                        assertEquals(eventsData.get("status"), "success");
                        break;
                    }
                }
            }
        } else if (eventType.equals("address")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

                if (payLoadData.get("recordType").getAsString().equalsIgnoreCase("address")) {
                    if (eventName.contains("SAVE"))
                        assertEquals(payLoadData.get("action").getAsString(), "Create");
                    else
                        assertEquals(payLoadData.get("action").getAsString(), "Update");

                    assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("address"));
                    assertNotNull(payLoadData.get("dataObject"));
                    JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                    assertTrue(dataObjectData.get("addressStreet2").isJsonNull());
                    assertTrue(dataObjectData.get("addressZipFour").isJsonNull());
                    assertTrue(dataObjectData.get("addrAttn").isJsonNull());
                    assertTrue(dataObjectData.get("addrHouseCode").isJsonNull());
                    assertTrue(dataObjectData.get("effectiveEndDate").isJsonNull());

                    if (eventsData.get("eventName").equals(eventName)) {
                        assertEquals(eventsData.get("status"), "success");
                        break;
                    }
                }
            }
        }
    }

    public JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                       String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip, String consumerRole) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " +caseIdentificationNumber);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDateOfBirth);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .addProperty("addressCity", addressCity.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .addProperty("addressCounty", addressCounty.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .addProperty("addressState", addressState.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .addProperty("addressStreet1", addressStreet1.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .addProperty("addressZipFour", "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .addProperty("emailAddress", emailAddress.get());

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }
}
