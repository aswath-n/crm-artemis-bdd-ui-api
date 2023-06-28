package com.maersk.crm.api_step_definitions;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import com.maersk.crm.beans.CreateAContactBean;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APIContactRecordSearchController extends CRMUtilities implements ApiBase {

    private String consumerRecordURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String createConsumerRecord = "app/crm/consumer";
    private String searchConsumerRecord = "app/crm/case/consumers";
    private String contactRecordURI = ConfigurationReader.getProperty("apiContactRecordURI");
    private String createContactRecord = "app/crm/contactrecord";
    private String contactRecordCorrelationEndPoint = "app/crm/correlation/contactrecord/{correlationId}";
    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <String> contactRecordId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> apiConsumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> apiConsumerCorrelationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> apiConsumeruiid = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> contactChannelType = ThreadLocal.withInitial(()->"Phone");
    private final ThreadLocal <String> contactDispositionsValue = ThreadLocal.withInitial(()->"Complete");
    private final ThreadLocal <String> contactSearchRecordType = ThreadLocal.withInitial(()->"General");

    final ThreadLocal <Map<String, Object>> newConsumer = ThreadLocal.withInitial(()->getNewTestData2());

    private final ThreadLocal <String> firstName = ThreadLocal.withInitial(()->newConsumer.get().get("name").toString());
    private final ThreadLocal <String> lastName = ThreadLocal.withInitial(()->newConsumer.get().get("surname").toString());
    private final ThreadLocal <String> consumerSSN = ThreadLocal.withInitial(()->newConsumer.get().get("ssn").toString());

    @Given("I create consumer and link Contact Record through api")
    public void createConsumerAndLinkForContactRecord() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerRecord);

        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");


        requestParams.set(generateConsumerRecordAPI());
        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        createContactRecordAPI();
    }

    @When("I run contact search api for Contact Record")
    public void runContactSearchApiForContactRecord() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(contactRecordURI);
        String getCorrelationContactRecordDtl;
        getCorrelationContactRecordDtl = contactRecordCorrelationEndPoint.replace("{correlationId}", apiConsumerCorrelationId.toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCorrelationContactRecordDtl);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("API Response:"+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I validate the search results for Contact Record")
    public void validateTheSearchResultsForContactRecord() {
        ArrayList<HashMap<String, String>> contactRecords = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("contactRecords");
        String contactChannelValue = "123-456-7890";
        for(int i=0;i<contactRecords.size();i++){
            HashMap<String, String> recordMap = contactRecords.get(i);

            assertEquals(recordMap.get("consumerType"), "Consumer");
            assertEquals(recordMap.get("contactRecordType"), "Inbound");
            assertEquals(recordMap.get("contactChannelType"), contactChannelType.get());

            if (contactChannelType.get().equals("Phone") || contactChannelType.get().equals("SMS Text")) {
                assertEquals(recordMap.get("consumerPhone"), contactChannelValue);
            }  else if (contactChannelType.get().equals("Email") || contactChannelType.get().equals("Web Chat")) {
                assertEquals(recordMap.get("consumerEmail"), contactChannelValue);
            } else {
                assertEquals(recordMap.get("consumerPhone"), contactChannelValue);
            }
        }
    }

    private JsonObject generateConsumerRecordAPI() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");

        long currentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", currentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", firstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", lastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", consumerSSN.get());
        ((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement).getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", newConsumer.get().get("phone").toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address").addProperty("addressZip", newConsumer.get().get("zip").toString());

        Long correlationId = Long.parseLong(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private void createContactRecordAPI() {
        apiConsumerId.set(getConsumerId());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(contactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createContactRecord);

        apiConsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        String apiconsumerUiid = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiGeneralContactRecord.json");

        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" +offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);
        offsetTime =OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" +offsetTime);
        System.out.println(contactRecordId);
        String wrapUpTime = "9"+ API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(2).randomNumber;
        String inboundCallQueue = "Enrollment";

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", "00:00:07");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiConsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", "Inbound");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", "Consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("preferredLanguageCode", "English");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("inboundCallQueue", inboundCallQueue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", contactChannelType.get());
        if (contactChannelType.get().equals("Phone")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", "123-456-7890");
        } else if (contactChannelType.get().equals("SMS Text")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", "123-456-7890");
        } else if (contactChannelType.get().equals("Email")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerEmail", "123-456-7890");
        } else if (contactChannelType.get().equals("Web Chat")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerEmail", "123-456-7890");
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", "123-456-7890");
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", "Consumer");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params:"+requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("API Response:"+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

    }

    private String getConsumerId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchConsumerRecord);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        JsonObject consumerRequestParams = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        consumerRequestParams.addProperty("consumerFirstName", firstName.get());

        System.out.println(consumerRequestParams);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(consumerRequestParams);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List<List> consumerIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerId");

        System.out.println(consumerIdList.get(0).get(0));
        return consumerIdList.get(0).get(0).toString();
    }

    public static Map getNewTestData2() {
        Map<String, Object> data = new HashMap<>();
        Faker faker = new Faker();
        Object name = faker.name().firstName();
        Object surname = faker.name().lastName();
        if (name.toString().length() > 10) {
            data.put("name", ((String) name).substring(0, 10) + RandomStringUtils.random(5, true, false));
        } else {
            data.put("name", name + RandomStringUtils.random(5, true, false));
        }
        if (surname.toString().length() > 10) {
            data.put("surname", ((String) surname).substring(0, 10) + RandomStringUtils.random(5, true, false));
        } else {
            data.put("surname", surname + RandomStringUtils.random(5, true, false));
        }
//        data.put("name",faker.name().firstName()+RandomStringUtils.random(5, true, false));
//        data.put("surname",faker.name().lastName()+RandomStringUtils.random(5, true, false));
        data.put("email", RandomStringUtils.random(5, true, false) + "@maerskTest" + RandomStringUtils.random(3, true, true) + ".com");
        data.put("birthday", convertMilliSecondsToDateFormatMMddyyyy(LocalDateTime.now().minusYears(faker.random().nextInt(18, 65)).atZone(ZoneId.of("US/Eastern")).toInstant().toEpochMilli()));//String
        data.put("ssn", RandomStringUtils.random(9, false, true));
        data.put("phone", RandomStringUtils.random(10, false, true));
        data.put("zip", RandomStringUtils.random(5, false, true));
        data.put("gender", System.currentTimeMillis() % 2 == 0 ? CreateAContactBean.Gender.MALE.getGender() : CreateAContactBean.Gender.FEMALE.getGender());
        return data;
    }
}
