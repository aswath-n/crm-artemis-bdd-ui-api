package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMThirdPartyContactRecPage;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertNotNull;

public class APIThirdPartyContactController extends CRMUtilities implements ApiBase {


    final ThreadLocal<HashMap<String, Object>> newConsumer = ThreadLocal.withInitial(() -> new HashMap<>(getNewTestData2()));
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> tPFirstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> tPLastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> tPPhone = ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactChannelType = ThreadLocal.withInitial(() -> "Phone");
    private final ThreadLocal<String> contactDispositionsValue = ThreadLocal.withInitial(() -> "Complete");
    private final ThreadLocal<String> contactSearchRecordType = ThreadLocal.withInitial(() -> "General");
    private final ThreadLocal<String> consumerExternalId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(() -> newConsumer.get().get("ssn").toString());
    private final ThreadLocal<String> externalId = ThreadLocal.withInitial(() -> newConsumer.get().get("externalId").toString());


    private String consumerRecordURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String createConsumerRecord = "app/crm/consumer";
    private String createContactRecordURI = ConfigurationReader.getProperty("apiContactRecordURI");
    private String createContactRecord = "app/crm/contactrecord";
    private String contactRecordURI = ConfigurationReader.getProperty("contactRecordSearchURI");
    private String getcontactSearchList = "app/crm/search/contactrecords";


    private final ThreadLocal<JsonObject> requestParamsCreateThirdPartyContactRecord = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);

    @Given("I Initiated Create consumer API for creating consumer for third party contact Record")
    public void initiateContactRecordAPIToCreateThirdPartyContactRecord() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerRecord);
    }

    @When("I create consumer for {string} Third Party Contact using API")
    public void iCreateConsumerForThirdPartyContact(String contactType) {
        requestParams.set(generateConsumerRecordAPI());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(createContactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createContactRecord);

        requestParamsCreateThirdPartyContactRecord.set(createThirdPartyContactRecod(contactType));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsCreateThirdPartyContactRecord.get());
        System.out.println("API Response:" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I initiate and search for third party contact record details")
    public void initiateAndSearchForThirdPartyContactRecord() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(contactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getcontactSearchList);

        requestParams.set(searchContactRecordAPI());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("API Response:" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I am able to verify the complete {string} third party Contact Record")
    public void ableToVerifyThirdPartyContactRecord(String contactType) {
        ArrayList<HashMap<String, String>> contactRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("contactRecords");
        assertTrue(contactRecords.size() > 0);
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);

            assertEquals(recordMap.get("contactRecordType"), contactType);
            assertTrue(verifyDateFormat(recordMap.get("contactRecordStartTime")));
            assertTrue(verifyDateFormat(recordMap.get("contactRecordEndTime")));
            assertEquals(recordMap.get("consumerFirstName"), firstName);
            assertEquals(recordMap.get("consumerLastName"), lastName);
            assertEquals(recordMap.get("consumerType"), "Agency");
            assertEquals(recordMap.get("organizationName"), "tpOrg");
            assertEquals(recordMap.get("preferredLanguageCode"), "English");
            assertEquals(recordMap.get("contactChannelType"), "Phone");
            assertEquals(recordMap.get("consumerPhone"), tPPhone);

            if (contactType.equalsIgnoreCase("Inbound")) {
                assertEquals(recordMap.get("inboundCallQueue"), "Eligibility");
            } else if (contactType.equalsIgnoreCase("Outbound")) {
                assertEquals(recordMap.get("contactCampaignType"), "Enrollment Reminder");
                assertEquals(recordMap.get("contactOutcome"), "Reached Successfully");
            }
        }
    }

    private JsonObject searchContactRecordAPI() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiSearchContact.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", 85271);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private boolean verifyDateFormat(String dateField) {
        if (dateField == null)
            return true;
        boolean dateFormatCheck = false;
        String dateFieldFormatted = dateField.substring(1, dateField.length() - 1);
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateFieldFormatted);
            if (date1 != null)
                dateFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return dateFormatCheck;
    }

    private JsonObject createThirdPartyContactRecod(String contactType) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiCreateThirdPartyContactRecord.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", "85271");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", contactType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("preferredLanguageCode", "English");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", tPPhone.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", firstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", lastName.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

        if (contactType.equals("Inbound")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("inboundCallQueue", "Eligibility");
        } else if (contactType.equals("Outbound")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactCampaignType", "Enrollment Reminder");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactOutcome", "Reached Successfully");
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", "Phone");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("organizationName", "tpOrg");

        JsonObject programType = new JsonObject();
        programType.addProperty("programType", "Program A");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("programTypes").add(programType);

        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private JsonObject generateConsumerRecordAPI() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");

        System.out.println("First name of consumer created:" + firstName);
        System.out.println("Last name of consumer created:" + lastName);

        long currentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", currentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", firstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", lastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", consumerSSN.get());
        ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", newConsumer.get().get("phone").toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", newConsumer.get().get("zip").toString());

        Long correlationId = Long.parseLong(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }
}
