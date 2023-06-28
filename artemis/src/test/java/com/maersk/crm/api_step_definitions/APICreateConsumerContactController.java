package com.maersk.crm.api_step_definitions;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.etl_util.ETLBaseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.Date;

import static org.testng.Assert.*;
import static org.testng.Assert.assertNotNull;

public class APICreateConsumerContactController extends CRMUtilities implements ETLBaseClass, ApiBase {

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseWithConsumers = "app/crm/caseloader";
    private String caseConsumerBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String getConsumers = "app/crm/case/consumers";
    private String consumerContactBaseURI = ConfigurationReader.getProperty("apiConsumerContactURI");
    private String consumerContactEndPoint = "mars/crm/contacts/Consumer";
    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String eventsEndPoint = "/app/crm/events?size=10000000&page=0&sort=eventId,desc";
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";
    private String subscribersEndPoint = "/app/crm/es/event/subscribers";
    private String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    private String caseNoteBaseURI = ConfigurationReader.getProperty("digitalPrimaryContactBaseURI");
    private String reevaluatepi = "app/crm/task/reevaluatepi";
    private String reevaluatepiV2 = "/crm/task/reevaluatepi";
    private String consumersURI = ConfigurationReader.getProperty("apiConsumersURI");
    private String consumersEndPoint = "/app/crm/consumers";
    private String consumersV_2_URI = ConfigurationReader.getProperty("consumer-v2_URL");
    private String transaction_statusEndPoint = "/crm/transaction-status/";
    private String contactsAPI = "/crm/contacts/?profileType=consumer reported";
    /*
    private ApiTestDataUtil API_THREAD_LOCAL_FACTORY.getApitduThreadLocal() = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private APIClassUtil apiEvent = APIClassUtil.getApiClassUtilThreadLocal();
    private APITMEventController apitmEventController = new APITMEventController();*/
    private APIClassUtil apiEvent = APIClassUtil.getApiClassUtilThreadLocal();
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsConsumer = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsEvent = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> consumerRequestParams = ThreadLocal.withInitial(JsonObject::new);
    private static final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> middleName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> suffix = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerDob = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> externalconsumerId = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> priorConsumerProfile = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> profileId = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> caseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> eventId = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<String> UUID = ThreadLocal.withInitial(String::new);
    final ThreadLocal<Map<String, String>> newData = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<String> addressCity = ThreadLocal.withInitial(() -> "Dallas");
    private final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(() -> "Dallas");
    private final ThreadLocal<String> addressState = ThreadLocal.withInitial(() -> "TX");
    private static final ThreadLocal<String> addressStreet1 = ThreadLocal.withInitial(() -> getStaticRandomNumber(4) + " test ln");
    private final ThreadLocal<String> addressType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Boolean> addressVerified = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<String> addressZip = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressZipFour = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Boolean> addressPrimaryInd = ThreadLocal.withInitial(() -> false);
    private final ThreadLocal<String> createdBy = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> emailAddress = ThreadLocal.withInitial(() -> getStaticRandomString(6).toLowerCase(Locale.ROOT) + "@cpgmail.com");
    private final ThreadLocal<String> emailType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Boolean> emailPrimaryInd = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<String> phoneNumber = ThreadLocal.withInitial(() -> getStaticRandomNumber(10));
    private final ThreadLocal<String> phoneType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Boolean> smsEnabledInd = ThreadLocal.withInitial(() -> false);
    private final ThreadLocal<Boolean> phonePrimaryInd = ThreadLocal.withInitial(() -> false);
    private final ThreadLocal<String> TXN_ID = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> externalcaseId = ThreadLocal.withInitial(String::new);

    public static String getUUID() {
        return UUID.get();
    }

    public static String getExternalcaseId() {
        return externalcaseId.get();
    }

    public static String getExternalconsumerId() {
        return externalconsumerId.get();
    }

    public static String getConsumerfirstName() {
        return consumerFirstName.get();
    }

    public static String getConsumerlastName() {
        return consumerLastName.get();
    }

    public static String getProfileId() {
        return profileId.get();
    }

    public static String getAddressStreet1() {
        return addressStreet1.get();
    }

    public static String getAddressZip() {
        return addressZip.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public static void setConsumerFirstName(String consumerFirstName) {
        APICreateConsumerContactController.consumerFirstName.set(consumerFirstName);
    }

    public static void setConsumerLastName(String consumerLastName) {
        APICreateConsumerContactController.consumerLastName.set(consumerLastName);
    }

    @Given("I initiated Case Loader API for Create New Consumer Contact Information")
    public void createNewConsumerContactInfo() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
        createdBy.set(apiEvent.getApiUserInfo());
    }

    @When("I run Case Loader API for Create New Consumer Contact Information")
    public void createNewConsumerContactCaseLoader() {
        synchronized (consumerFirstName) {
            consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        }
        synchronized (consumerLastName) {
            consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        }
        synchronized (consumerSSN) {
            consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        }


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(),
                consumerDob.get(), "", ""));
        System.out.println("request params: " + requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response string");
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @When("I get consumer Id of the created consumer for Contact Information")
    public void getConsumerIdCreatedConsumer() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumers);

        requestParamsConsumer.set(generateConsumerRequest(consumerFirstName.get(), consumerLastName.get()));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsConsumer.get());
        System.out.println("api response consumer search: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List<List> consumerListID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerId");
        Iterator consumerIdIter = consumerListID.iterator();
        Object consumerIdObj = consumerIdIter.next();
        consumerId.set((consumerIdObj.toString()).toLowerCase());
        System.out.println("consumerId : " + consumerId);
        assertFalse(consumerId.get().isEmpty());
        APIDigitalAuthentication.staticCaseId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result.cases.caseId").toString());
        APIDigitalAuthentication.staticCaseId.set(APIDigitalAuthentication.staticCaseId.get().replace("[", ""));
        APIDigitalAuthentication.staticCaseId.set(APIDigitalAuthentication.staticCaseId.get().replace("]", ""));
        consumerId.set(consumerId.get().replace("[", ""));
        consumerId.set(consumerId.get().replace("]", ""));
        if (consumerId.get().contains(",")) {
            String consumerIds[] = consumerId.get().split(",");
            consumerContactEndPoint = consumerContactEndPoint + "/" + consumerIds[0];
        } else consumerContactEndPoint = consumerContactEndPoint + "/" + consumerId.get();

    }

    @When("I capture the contact information of the created consumer")
    public void captureContactInfoCreatedConsumer() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerContactBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(consumerContactEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("api response customer contact information: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I will verify address email and phone information for Create New Consumer Contact Information")
    public void captureContactInformation() {
        List contactsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        assertTrue(contactsContent.size() > 0);

        for (int i = 0; i < contactsContent.size(); i++) {
            HashMap contactsData = (HashMap) contactsContent.get(i);
            System.out.println(contactsData.get("addressess"));

            List addressData = (List) contactsData.get("addressess");
            HashMap addressDetail = (HashMap) addressData.get(0);
            assertEquals(addressDetail.get("addressStreet1"), addressStreet1.get());
            assertEquals(addressDetail.get("addressCity"), addressCity.get());
            assertEquals(addressDetail.get("addressState"), addressState.get());
            assertEquals(addressDetail.get("addressZip"), addressZip.get());
            assertEquals(addressDetail.get("addressZipFour"), addressZipFour.get());
            assertEquals(addressDetail.get("addressCounty"), addressCounty.get());
            assertEquals(addressDetail.get("addressType"), addressType.get());
            assertEquals(addressDetail.get("addressVerified"), addressVerified.get());
            assertEquals(addressDetail.get("primaryIndicator"), addressPrimaryInd.get());
            assertEquals(addressDetail.get("createdBy"), "3455");
            assertTrue(verifyDatetimeFormat(addressDetail.get("createdOn").toString()));

            System.out.println(contactsData.get("emails"));

            List emailData = (List) contactsData.get("emails");
            HashMap emailDetail = (HashMap) emailData.get(0);
            assertEquals(emailDetail.get("emailAddress"), emailAddress.get());
            assertEquals(emailDetail.get("emailType"), emailType.get());
            assertEquals(emailDetail.get("primaryIndicator"), emailPrimaryInd.get());
            assertEquals(emailDetail.get("createdBy"), "3455");
            assertTrue(verifyDatetimeFormat(emailDetail.get("createdOn").toString()));

            System.out.println(contactsData.get("phones"));

            List phoneData = (List) contactsData.get("phones");
            HashMap phoneDetail = (HashMap) phoneData.get(0);
            assertEquals(phoneDetail.get("phoneType"), phoneType.get());
            assertEquals(phoneDetail.get("phoneNumber"), phoneNumber.get());
            assertEquals(phoneDetail.get("smsEnabledInd"), smsEnabledInd.get());
            assertEquals(phoneDetail.get("primaryIndicator"), phonePrimaryInd.get());
            assertEquals(phoneDetail.get("createdBy"), "3455");
            assertTrue(verifyDatetimeFormat(phoneDetail.get("createdOn").toString()));
        }
    }

    @Then("I will verify phone number type for Create New Consumer Contact Information")
    public void verifyPhoneTypeConsumerContactInfo() {
        List contactsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        assertTrue(contactsContent.size() > 0);

        for (int i = 0; i < contactsContent.size(); i++) {
            HashMap contactsData = (HashMap) contactsContent.get(i);
            System.out.println(contactsData.get("phones"));

            List phoneData = (List) contactsData.get("phones");
            HashMap phoneDetail = (HashMap) phoneData.get(0);
            assertEquals(phoneDetail.get("phoneType"), phoneType.get());
            assertEquals(phoneDetail.get("phoneNumber"), phoneNumber.get());
        }
    }

    @Then("I will verify start date for Create New Consumer Contact Information")
    public void verifyStartDateConsumerContactInfo() {
        List contactsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        assertTrue(contactsContent.size() > 0);

        for (int i = 0; i < contactsContent.size(); i++) {
            HashMap contactsData = (HashMap) contactsContent.get(i);
            System.out.println(contactsData.get("phones"));

            List phoneData = (List) contactsData.get("phones");
            HashMap phoneDetail = (HashMap) phoneData.get(0);
            assertEquals(phoneDetail.get("phoneType"), phoneType.get());
            assertEquals(phoneDetail.get("phoneNumber"), phoneNumber.get());
            assertEquals(phoneDetail.get("createdBy"), "3455");
            assertTrue(verifyDatetimeFormat(phoneDetail.get("createdOn").toString()));
            //also check date is today's date
        }
    }

    @Then("I will verify primary phone indicator for Create New Consumer Contact Information")
    public void verifyPrimaryPhoneIndConsumerContactInfo() {
        List contactsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        assertTrue(contactsContent.size() > 0);

        for (int i = 0; i < contactsContent.size(); i++) {
            HashMap contactsData = (HashMap) contactsContent.get(i);
            System.out.println(contactsData.get("phones"));

            List phoneData = (List) contactsData.get("phones");
            HashMap phoneDetail = (HashMap) phoneData.get(0);
            assertEquals(phoneDetail.get("phoneType"), phoneType.get());
            assertEquals(phoneDetail.get("phoneNumber"), phoneNumber.get());
            assertEquals(phoneDetail.get("smsEnabledInd"), smsEnabledInd.get());
            assertEquals(phoneDetail.get("primaryIndicator"), phonePrimaryInd.get());
        }
    }

    @Then("I will verify an {string} is created for {string} event for Create New Consumer Contact Information")
    public void verifyThePhoneEmailAddressEvents(String eventName, String eventType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsEndPoint);
        ////apitmEventController.getAuthenticationToken("BLCRM", "CRM");

        requestParamsEvent.set(new JsonObject());
        requestParamsEvent.get().addProperty("eventName", eventName);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsEvent.get());
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        if (eventType.equals("phone")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
                eventId.set((int) eventsData.get("eventId"));

                assertEquals(payLoadData.get("action").getAsString(), "Create");
                assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("PhoneResponse"));
                assertTrue(verifyDatetimeFormat(payLoadData.get("eventCreatedOn").getAsString()));
                assertNotNull(payLoadData.get("dataObject"));
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                assertEquals(dataObjectData.get("phoneType").getAsString(), phoneType.get());
                assertEquals(dataObjectData.get("phoneNumber").getAsString(), phoneNumber.get());
//                assertTrue(dataObjectData.get("associatedCaseMember").isJsonNull());
                assertTrue(dataObjectData.get("comments").isJsonNull());
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertTrue(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("phoneId"));
                assertTrue(verifyDatetimeFormat(dataObjectData.get("effectiveStartDate").getAsString()));
                if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                    assertTrue(verifyDatetimeFormat(dataObjectData.get("effectiveEndDate").getAsString()));
                }
                assertTrue(verifyDatetimeFormat(dataObjectData.get("createdOn").getAsString()));
                assertEquals(dataObjectData.get("createdBy").getAsString(), "3455");

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "SUCCESS");
                    break;
                }
            }
        } else if (eventType.equals("email")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
                eventId.set((int) eventsData.get("eventId"));

                assertEquals(payLoadData.get("action").getAsString(), "Create");
                assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("EmailResponse"));
                assertNotNull(payLoadData.get("dataObject"));
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                assertEquals(dataObjectData.get("emailAddress").getAsString(), emailAddress.get());
                assertEquals(dataObjectData.get("emailType").getAsString(), emailType.get());
                //  assertTrue(dataObjectData.get("associatedCaseMember").isJsonNull());
                assertTrue(verifyDatetimeFormat(dataObjectData.get("effectiveStartDate").getAsString()));
                if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                    assertTrue(verifyDatetimeFormat(dataObjectData.get("effectiveEndDate").getAsString()));
                }
                assertTrue(verifyDatetimeFormat(dataObjectData.get("createdOn").getAsString()));
                assertEquals(dataObjectData.get("createdBy").getAsString(), "3455");
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertTrue(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("primaryIndicator"));

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "SUCCESS");
                    break;
                }
            }
        } else if (eventType.equals("address")) {
            List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
            assertTrue(eventsContent.size() > 0);

            for (int i = 0; i < eventsContent.size(); i++) {
                HashMap eventsData = (HashMap) eventsContent.get(i);
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
                eventId.set((int) eventsData.get("eventId"));

                assertEquals(payLoadData.get("action").getAsString(), "Create");
                assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("AddressResponse"));
                assertTrue(verifyDatetimeFormat(payLoadData.get("eventCreatedOn").getAsString()));
                assertNotNull(payLoadData.get("dataObject"));
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                assertTrue(dataObjectData.get("addressStreet1").getAsString().equalsIgnoreCase(addressStreet1.get()));
                assertTrue(dataObjectData.get("addressCity").getAsString().equalsIgnoreCase(addressCity.get()));
                assertTrue(dataObjectData.get("addressZip").getAsString().equalsIgnoreCase(addressZip.get()));
                assertTrue(dataObjectData.get("addressType").getAsString().equalsIgnoreCase(addressType.get()));
                assertTrue(verifyDatetimeFormat(dataObjectData.get("effectiveStartDate").getAsString()));
                if (dataObjectData.get("effectiveEndDate") != null && !dataObjectData.get("effectiveEndDate").isJsonNull()) {
                    assertTrue(verifyDatetimeFormat(dataObjectData.get("effectiveEndDate").getAsString()));
                }

                assertTrue(verifyDatetimeFormat(dataObjectData.get("createdOn").getAsString()));
                assertEquals(dataObjectData.get("createdBy").getAsString(), "3455");
                assertTrue(dataObjectData.get("externalRefType").getAsString().equalsIgnoreCase("consumer"));
                assertTrue(dataObjectData.get("primaryIndicator").getAsBoolean());
                assertNotNull(dataObjectData.get("primaryIndicator"));

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "SUCCESS");
                    break;
                }
            }
        }
    }

    @Then("I will verify subscriber received {string} event for {string} is created for Create New Consumer Contact Information")
    public void dpbiEventGenerationCheck(String eventName, String subscriberName) {
        boolean recordCreated = true;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersEndPoint);

        requestParams.set(new JsonObject());
        requestParams.get().addProperty("subscriberName", subscriberName);

        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray jsonSubscriber = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("subscriberEntity");
        String subscriberId = new JSONObject(jsonSubscriber.get(0).toString()).get("subscriberId").toString();
        JSONArray subscriberList = new JSONArray(new JSONObject(jsonSubscriber.get(0).toString()).get("subscriberList").toString());

        for (int i = 0; i < subscriberList.length(); i++) {
            JSONObject temp = new JSONObject(subscriberList.get(i).toString());
            if (temp.get("eventName").toString().trim().equals(eventName.trim())) {
                Assert.assertEquals(temp.get("subscriberId").toString(), subscriberId, "Subscriber Id is wrong");
                Assert.assertEquals(temp.get("eventName").toString().trim(), eventName, "Event Name is wrong");
                Assert.assertTrue(temp.get("eventSubscriberMappingId").toString().
                        chars().allMatch(Character::isDigit));
                recordCreated = true;
                break;
            }
        }
        Assert.assertTrue(recordCreated, eventName + " is not published to DBPI");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        subscribersRecordEndPoint = subscribersRecordEndPoint + subscriberId + "/" + eventId;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(subscribersRecordEndPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        Assert.assertEquals(temp.getString("eventName"), eventName);
        System.out.println("Event name on DPBI:  " + eventName);

        Assert.assertEquals(temp.getInt("eventId"), eventId.get());
        System.out.println("Event id on DPBI:  " + eventId.get());

        Assert.assertEquals(temp.getInt("subscriberId") + "", subscriberId);
        System.out.println("Subscriber id on DPBI:   " + subscriberId);
        System.out.println("Event is subscribed to DPBI list");
    }

    @Then("I will verify created on and created by for Create New Consumer Contact Information")
    public void verifyCreatedFieldsConsumerContactInfo() {
        List contactsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        assertTrue(contactsContent.size() > 0);

        for (int i = 0; i < contactsContent.size(); i++) {
            HashMap contactsData = (HashMap) contactsContent.get(i);
            System.out.println(contactsData.get("addressess"));

            List addressData = (List) contactsData.get("addressess");
            HashMap addressDetail = (HashMap) addressData.get(0);
            assertEquals(addressDetail.get("createdBy"), "3455");
            assertTrue(verifyDatetimeFormat(addressDetail.get("createdOn").toString()));

            System.out.println(contactsData.get("emails"));

            List emailData = (List) contactsData.get("emails");
            HashMap emailDetail = (HashMap) emailData.get(0);
            assertEquals(emailDetail.get("createdBy"), "3455");
            assertTrue(verifyDatetimeFormat(emailDetail.get("createdOn").toString()));

            System.out.println(contactsData.get("phones"));

            List phoneData = (List) contactsData.get("phones");
            HashMap phoneDetail = (HashMap) phoneData.get(0);
            assertEquals(phoneDetail.get("createdBy"), "3455");
            assertTrue(verifyDatetimeFormat(phoneDetail.get("createdOn").toString()));
        }
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                        String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " + caseIdentificationNumber);
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
                .addProperty("createdBy", createdBy.get());

        addressType.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("addressType").getAsString());
        addressVerified.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("addressVerified").getAsBoolean());
        addressZip.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("addressZip").getAsString());
        addressZipFour.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("addressZipFour").getAsString());
        addressPrimaryInd.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("primaryIndicator").getAsBoolean());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .addProperty("emailAddress", emailAddress.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .addProperty("createdBy", createdBy.get());

        emailType.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .get("emailType").getAsString());
        emailPrimaryInd.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .get("primaryIndicator").getAsBoolean());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .addProperty("phoneNumber", phoneNumber.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .addProperty("createdBy", createdBy.get());

        phoneType.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .get("phoneType").getAsString());
        smsEnabledInd.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .get("smsEnabledInd").getAsBoolean());
        phonePrimaryInd.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .get("primaryIndicator").getAsBoolean());

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private JsonObject generateConsumerRequest(String firstName, String lastName) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");

        JsonObject consumerRequestParams = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        consumerRequestParams.addProperty("consumerFirstName", firstName);
        consumerRequestParams.addProperty("consumerLastName", lastName);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private boolean verifyDatetimeFormat(String datetimeField) {
        boolean datetimeFormatCheck = false;
        DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
        System.out.println(datetimeField);

        try {
            LocalDateTime parsedDate = dtf.parseLocalDateTime(datetimeField);
            if (parsedDate != null)
                datetimeFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return datetimeFormatCheck;
    }

    @Then("I Verify created onBy and updated onBy fields for Create New Consumer Contact Information")
    public void iVerifyCreatedOnByAndUpdatedOnByFieldsForCreateNewConsumerContactInformation() {
        List contactsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        assertTrue(contactsContent.size() > 0);
        String presentTimeStamp = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("yyyy-MM-dd");
        for (int i = 0; i < contactsContent.size(); i++) {
            HashMap contactsData = (HashMap) contactsContent.get(i);
            System.out.println(contactsData.get("addressess"));

            List addressData = (List) contactsData.get("addressess");
            HashMap addressDetail = (HashMap) addressData.get(0);
            assertEquals(addressDetail.get("createdBy"), "3455");
            assertEquals(addressDetail.get("updatedBy"), "3455");
            assertTrue(verifyDatetimeFormat(addressDetail.get("createdOn").toString()));
            assertTrue(addressDetail.get("createdOn").toString().contains(presentTimeStamp));
            assertTrue(addressDetail.get("updatedOn").toString().contains(presentTimeStamp));

            System.out.println(contactsData.get("emails"));

            List emailData = (List) contactsData.get("emails");
            HashMap emailDetail = (HashMap) emailData.get(0);
            assertEquals(emailDetail.get("createdBy"), "3455");
            assertEquals(addressDetail.get("updatedBy"), "3455");
            assertTrue(verifyDatetimeFormat(emailDetail.get("createdOn").toString()));
            assertTrue(emailDetail.get("createdOn").toString().contains(presentTimeStamp));
            assertTrue(addressDetail.get("updatedOn").toString().contains(presentTimeStamp));

            System.out.println(contactsData.get("phones"));

            List phoneData = (List) contactsData.get("phones");
            HashMap phoneDetail = (HashMap) phoneData.get(0);
            assertEquals(phoneDetail.get("createdBy"), "3455");
            assertEquals(addressDetail.get("updatedBy"), "3455");
            assertTrue(verifyDatetimeFormat(phoneDetail.get("createdOn").toString()));
            assertTrue(phoneDetail.get("createdOn").toString().contains(presentTimeStamp));
            assertTrue(addressDetail.get("updatedOn").toString().contains(presentTimeStamp));
        }
    }

    @When("I run Case Loader API for Create New Consumer Contact Information without CaseIdNo")
    public void createNewConsumerContactCaseLoaderWithOutCaseIdNo() {
        synchronized (consumerFirstName) {
            consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        }

        synchronized (consumerLastName) {
            consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        }

        synchronized (consumerSSN) {
            consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        requestParams.set(generateCaseLoaderRequestWithSpecifiedParametersWithOutCaseIdN("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(),
                consumerDob.get(), "", ""));
        System.out.println("request params: " + requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response string");
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParametersWithOutCaseIdN(String consumerType, String consumerFirstName, String consumerLastName,
                                                                                      String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " + caseIdentificationNumber);
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
                .addProperty("createdBy", createdBy.get());

        addressType.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("addressType").getAsString());
        addressVerified.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("addressVerified").getAsBoolean());
        addressZip.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("addressZip").getAsString());
        addressZipFour.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("addressZipFour").getAsString());
        addressPrimaryInd.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("address")
                .get("primaryIndicator").getAsBoolean());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .addProperty("emailAddress", emailAddress.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .addProperty("createdBy", createdBy.get());

        emailType.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .get("emailType").getAsString());
        emailPrimaryInd.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("email")
                .get("primaryIndicator").getAsBoolean());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .addProperty("phoneNumber", phoneNumber.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .addProperty("createdBy", createdBy.get());

        phoneType.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .get("phoneType").getAsString());
        smsEnabledInd.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .get("smsEnabledInd").getAsBoolean());
        phonePrimaryInd.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone")
                .get("primaryIndicator").getAsBoolean());

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @When("I run reevaluatepi API")
    public void I_run_reevaluatepi_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseNoteBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(reevaluatepi);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/ReevaluatePI.payload.json");
        for (int i = 0; i < 3; i++) {
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPIWithoutJsonResponse(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode == 200, "statusCode not 200");
//        assertTrue(api.responseString.contains("success"), "status is not success");

    }

    @When("I run DC PI reevaluatepi API")
    public void I_run_DC_PI_reevaluatepi_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumersURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(reevaluatepi);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/empty.json");
        for (int i = 0; i < 3; i++) {
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPIWithoutJsonResponse(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode == 200, "statusCode not 200");
//        assertTrue(api.responseString.contains("success"), "status is not success");

    }

    @Given("I initiate consumers V2 API")
    public void i_initiate_consumers_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumersURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(consumersEndPoint);
    }

    @When("I run consumers API name {string} with following payload")
    public void i_run_consumers_API_for_with_following_payload(String name, Map<String, String> data) {
        newData.set(new HashMap<>());
        final int maxAge = 100 * 12 * 31;
        synchronized (UUID) {
            UUID.set("UIID-" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        }

        synchronized (consumerFirstName) {
            consumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        }

        synchronized (consumerLastName) {
            consumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        }

        synchronized (consumerSSN) {
            consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN());
        }

        suffix.set("DDS");
        middleName.set("J");

        synchronized (externalconsumerId) {
            externalconsumerId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        }

        synchronized (externalcaseId) {
            externalcaseId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        }
        synchronized (priorConsumerProfile) {
            priorConsumerProfile.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        }

        synchronized (addressZip) {
            addressZip.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_Zip());
        }

        for (String key : data.keySet()) {
            if (data.get(key).contains("random")) {
                switch (key) {
                    case "consumerRequests[0].uuid":
                        newData.get().put("consumerRequests[0].uuid", UUID.get());
                        break;
                    case "consumerRequests[0].firstName":
                        newData.get().put("consumerRequests[0].firstName", consumerFirstName.get());
                        break;
                    case "consumerRequests[0].lastName":
                        newData.get().put("consumerRequests[0].lastName", consumerLastName.get());
                        break;
                    case "consumerRequests[0].ssn":
                        newData.get().put("consumerRequests[0].ssn", consumerSSN.get());
                        break;
                    case "consumerRequests[0].dateOfBirth":
                        consumerDob.set(String.valueOf(LocalDate.now().minusDays(new Random().nextInt(maxAge))));
                        newData.get().put("consumerRequests[0].dateOfBirth", consumerDob.get());
                        break;
                    case "consumerRequests[0].middleName":
                        newData.get().put("consumerRequests[0].middleName", middleName.get());
                        break;
                    case "consumerRequests[0].suffix":
                        newData.get().put("consumerRequests[0].suffix", suffix.get());
                        break;
                    case "consumerRequests[0].consumerProfile.externalId":
                        newData.get().put("consumerRequests[0].consumerProfile.externalId", externalconsumerId.get());
                        break;
                    case "consumerRequests[0].case.externalId":
                        newData.get().put("consumerRequests[0].case.externalId", externalcaseId.get());
                        break;
                    case "consumerRequests[0].priorConsumerProfile.externalId":
                        newData.get().put("consumerRequests[0].priorConsumerProfile.externalId", priorConsumerProfile.get());
                        break;
                    case "consumerRequests[0].contact.addresses[0].addressStreet1":
                        newData.get().put("consumerRequests[0].contact.addresses[0].addressStreet1", addressStreet1.get());
                        break;
                    case "consumerRequests[0].contact.addresses[0].addressZip":
                        newData.get().put("consumerRequests[0].contact.addresses[0].addressZip", addressZip.get());
                        break;
                    case "consumerRequests[0].contact.phones[0].phoneNumber":
                        newData.get().put("consumerRequests[0].contact.phones[0].phoneNumber", phoneNumber.get());
                        break;
                    case "consumerRequests[0].contact.emails[0].emailAddress":
                        newData.get().put("consumerRequests[0].contact.emails[0].emailAddress", emailAddress.get());
                        break;
                }
            } else {

                newData.get().put(key, data.get(key));
            }
            if (key.equals("consumerRequests[0].case")) {
                if (data.get("consumerRequests[0].case").contains("null")) {
                    newData.get().put("consumerRequests[0].case", null);
                }
            }
        }
        if (data.get("consumerRequests[0].consumerProfile.externalId").contains("null")) {
            newData.get().put("consumerRequests[0].consumerProfile.externalId", null);
        }
        if (data.containsKey("consumerRequests[0].priorConsumerProfile.externalId") && data.get("consumerRequests[0].priorConsumerProfile.externalId").contains("null")) {
            newData.get().put("consumerRequests[0].priorConsumerProfile.externalId", null);
        }
        if (data.get("consumerRequests[0].dateOfBirth").contains("<18")) {
            System.out.println(getPriorYearAPIyyyyMMdd(17, 0) + "!@#$%^&*()(*&^%$#@!@#$%^&*()O(*&^T%R$#@");
            newData.get().put("consumerRequests[0].dateOfBirth", getPriorYearAPIyyyyMMdd(17, 0));
        } else if (data.get("consumerRequests[0].dateOfBirth").contains("18")) {
            newData.get().put("consumerRequests[0].dateOfBirth", getPriorYearAPIyyyyMMdd(18, 0));
        } else if (data.get("consumerRequests[0].dateOfBirth").contains(">18")) {
            newData.get().put("consumerRequests[0].dateOfBirth", getPriorYearAPIyyyyMMdd(18, 1));
        } else if (data.get("consumerRequests[0].dateOfBirth").contains("18-1")) {
            newData.get().put("consumerRequests[0].dateOfBirth", getPriorYearAPIyyyyMMdd(18, -1));
        }


        System.out.println("consumerFirstName ------------ " + consumerFirstName.get() + "\n" +
                "consumerLastName ------------ " + consumerLastName.get() + "\n" +
                "consumerSSN ------------ " + consumerSSN.get() + "\n" +
                "consumerDob ------------ " + consumerDob.get() + "\n" +
                "consumerExternalId ------------ " + consumerId.get() + "\n" +
                "UUID ------------ " + UUID.get() + "\n" +
                "suffix ------------ " + suffix.get() + "\n" +
                "middleName ------------ " + middleName.get() + "\n" +
                "priorConsumerProfile ------------ " + priorConsumerProfile.get() + "\n"
        );

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/consumersAPIpayLoad.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, newData.get()));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        consumerId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].consumerId"));
        profileId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].profileId"));
        caseId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].caseId"));
        externalconsumerId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].consumer.profiles[0].externalId"));

    }

    public String getExternalConsumerId() {
        return this.profileId.get();
    }

    public static String getConsumerId() {
        return consumerId.get();
    }

    public String getCaseId() {
        return this.caseId.get();
    }

    @Then("I validate response have following data")
    public void i_validate_response_have_following_data(Map<String, String> data) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode == 200, "Failed, status code = " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].uuid"), UUID.get(), "Failed");
        assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].errors"), "Failed");
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].profileId"), "profileId is Null");
        if (!data.get("passedRules").equals("null")) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].passedRules[0]"), data.get("passedRules"), "Failed");
        } else {
            assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].passedRules"), "Failed");
        }
        if (!data.get("profileId").equals("new")) {
            assertNotEquals("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].profileId"), profileId.get(), "profileId the same");
        }
    }


    @When("I update consumers API name {string} with following payload")
    public void i_run_update_API_for_with_following_payload(String name, Map<String, String> data) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/consumersAPIpayLoad.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, newData.get()));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I search for created consumer using search api by {string}")
    public void i_search_for_created_consumer_using_search_api_by(String key) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equalsIgnoreCase("DC-EB");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCaseDC.json");

        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        switch (key) {
            case "consumerSSN":
                consumerRequestParams.get().getAsJsonArray("otherSearches").get(0).getAsJsonObject().addProperty("key", "ssn");
                consumerRequestParams.get().getAsJsonArray("otherSearches").get(0).getAsJsonObject().addProperty("value", "444441135");
                break;
            case "Medicaid":
                consumerRequestParams.get().getAsJsonArray("otherSearches").get(0).getAsJsonObject().addProperty("key", "Medicaid");
                consumerRequestParams.get().getAsJsonArray("otherSearches").get(0).getAsJsonObject().addProperty("value", "456789098765");
                break;
            case "profileId":
                consumerRequestParams.get().getAsJsonArray("otherSearches").get(0).getAsJsonObject().addProperty("key", "profileId");
                consumerRequestParams.get().getAsJsonArray("otherSearches").get(0).getAsJsonObject().addProperty("value", "584");
                break;
        }
        System.out.println("consumerRequestParams " + consumerRequestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(consumerRequestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println("Search Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify consumer details are available based on {string} search parameters")
    public void i_verify_consumer_details_are_available_based_on_search_parameters(String key) {

        List consumerContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.consumers");
        System.out.println(consumerContent);
        for (int i = 0; i < consumerContent.size(); i++) {
            HashMap consumersData = (HashMap) consumerContent.get(i);

            switch (key) {
                case "consumerSSN":
                    List profileSSN = (List) consumersData.get("profiles");
                    HashMap SSN = (HashMap) profileSSN.get(0);
                    assertEquals(SSN.get("ssn"), "444441135");
                    break;
                case "Medicaid":
                    List profileExtID = (List) consumersData.get("profiles");
                    HashMap medExtID = (HashMap) profileExtID.get(0);
                    assertEquals(medExtID.get("externalId"), "456789098765");
                    break;
            }
        }
    }

    @Then("I verify mergedProfileId field is displayed")
    public void i_verify_merged_profile_id_field_is_displayed() {
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[0].mergedProfileId"));
    }

    @Then("I verify consumer profiles details are in expected order")
    public void i_verify_consumer_profiles_details_are_in_expectedorder(List<String> types) {
        List consumerProfiles = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("consumers[0].profiles");

        HashMap profileDetails = (HashMap) consumerProfiles.get(0);
        assertEquals(profileDetails.get("type"), types.get(0));
        profileDetails = (HashMap) consumerProfiles.get(1);
        assertEquals(profileDetails.get("type"), types.get(1));
        if (consumerProfiles.size() == 3) {
            profileDetails = (HashMap) consumerProfiles.get(2);
            assertEquals(profileDetails.get("type"), types.get(1));
        }

    }

    @When("I run reevaluatepi API for V_2")
    public void i_run_reevaluatepi_API_for_V_2() {
        synchronized (UUID) {
            UUID.set("UIID-" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumersV_2_URI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(reevaluatepiV2);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/ReevaluatePI.payload.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uuid", UUID.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPIWithoutJsonResponse(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode == 200, "statusCode not 200");
        waitFor(3);

    }

    @Then("I sholud get following response structure")
    public void i_sholud_get_following_response_structure(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
        TXN_ID.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("txnId"));
        int recordCount = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("recordCount");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("tenant"), data.get("tenant"));
        if (data.get("error").equals("null")) {
            assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("error"));
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("error"), data.get("error"));
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("status"), data.get("status"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("status"), data.get("status"));
        if (!data.get("txnId").equals("not null")) {
            assertNull(TXN_ID.get());
        }
        assertNotNull(TXN_ID.get());
        assertTrue(recordCount > 0);

    }

    @When("I will call transaction-status GET API by TXN_ID")
    public void i_will_call_transaction_status_GET_API_by_TXN_ID() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumersV_2_URI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(transaction_statusEndPoint + TXN_ID.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
    }

    @Then("I will verify TXN COMPLETED and has following response")
    public void i_will_verify_TXN_COMPLETED_and_has_following_response(Map<String, String> data) {
        String actualTxnId = "" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("txnId");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("tenant"), data.get("tenant"));
        if (data.get("error").equals("null")) {
            assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("error"));
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("error"), data.get("error"));
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("status"), data.get("status"));
        assertEquals(actualTxnId, TXN_ID.get());
    }

    @When("I initiate contacts V2 API")
    public void I_initiate_contacts_V2_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumersV_2_URI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/crm/contacts/?profileType=consumer" + " " + "reported");
    }

    @And("I initiate and run Sync Contacts API")
    public void I_run_contacts_V2_API_with_following_data() {
        API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().initiateSyncContactInfoForDC();
        API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().runSyncContactInfoForDC();
    }

    @And("I initiate and run Sync Contacts API without Mailing address")
    public void I_run_contacts_V2_API_with_following_data_update() {
        API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().initiateSyncContactInfoForDC();
        API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().runSyncContactInfoForDCwithoutAddress();
    }

    @And("I run contacts V2 API with following data")
    public void I_run_contacts_V2_API_with_following_data(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/newV2ContactsAPIpayload.json");
        if (data.get("requestBy").contains("profileId")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("profileId", profileId.get());
        }
        if (data.get("requestBy").contains("external")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("type", "MEDICAID");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("externalId", externalconsumerId.get());
        }
        if (data.get("requestBy").contains("notExtistingExternalID")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("type", "MEDICAID");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("externalId", "notExtistingExternalID001");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }


    @Then("I verify new profile created")
    public void I_verify_new_profile_created() {
        int newProfileId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.profileId");
        System.out.println("newProfileId === " + newProfileId);
        assertTrue(newProfileId > Integer.parseInt(profileId.get()));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("status"), "success");
        profileId.set(newProfileId + "");
    }

    @When("I search consumer profile using v2 search API")
    public void I_search_consumer_profile_using_v2_search_API() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCaseDC.json");
        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        consumerRequestParams.get().getAsJsonArray("otherSearches").get(0).getAsJsonObject().addProperty("key", "profileId");
        consumerRequestParams.get().getAsJsonArray("otherSearches").get(0).getAsJsonObject().addProperty("value", profileId.get());
        System.out.println("consumerRequestParams " + consumerRequestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(consumerRequestParams.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println("Search Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify search response has following data")
    public void I_verify_search_response_has_following_data(Map data) {
        String expectedProfileId = "" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].profileId");
        String expectedTypeofProfileID = "" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].type");
        assertEquals(expectedProfileId, profileId.get(), "New created profileID not exist on search response");
        assertEquals(expectedTypeofProfileID, data.get("type"), "externalId Type incorrect");

    }

    @Then("I add new incorrect {string} for created profile to check error code")
    public void validate_error_messege_from_API(String contact) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/newV2ContactsAPIpayload.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("profileId", profileId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uuid", profileId.get() + generateRandomNumberChars(10));

        switch (contact) {
            case "address":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonArray("addresses").getAsJsonArray().get(0).getAsJsonObject().addProperty("addressStreet1", "");
                break;
            case "phone":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("consumerAttributes").getAsJsonArray("phones").get(0).getAsJsonObject().addProperty("phoneNumber", getRandomNumber(9));
                break;
            case "email":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("consumerAttributes").getAsJsonArray("emails").get(0).getAsJsonObject().addProperty("emailAddress", getRandomNumber(9) + getRandomString(3));
                break;
            case "addressType":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonArray("addresses").getAsJsonArray().get(0).getAsJsonObject().addProperty("addressType", "Wrong");
                break;
            case "phoneType":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("consumerAttributes").getAsJsonArray("phones").get(0).getAsJsonObject().addProperty("phoneType", "Wrong");
                break;
            case "emailType":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("consumerAttributes").getAsJsonArray("emails").get(0).getAsJsonObject().addProperty("emailType", "Wrong");
                break;

        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 400);
    }

    @Then("I verify response contains following eror code {string} {string}")
    public void I_verify_response_contais_Following_eror_code(String errorCode, String errorMassage) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("status"), "fail");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("errors[0].errorCode"), errorCode);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("errors[0].message").toString().contains(errorMassage), "actiual error code + " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("errors[0].errorMessage"));
    }

    @When("I sent with request {string} is {string}")
    public void request_with_opt_true_or_false(String opt, String indicator) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/newV2ContactsAPIpayload.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("profileId", profileId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uuid", profileId.get() + generateRandomNumberChars(10));

        switch (opt) {
            case "optAddress":
                if (indicator.equals("false")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("optInAddress", false);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("optInAddress", true);
                }
                break;
            case "optPhone":
                if (indicator.equals("false")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInPhone").addProperty("optInPhone", false);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInPhone").addProperty("optInPhone", true);
                }
                break;
            case "optEmail":
                if (indicator.equals("false")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInEmail").addProperty("optInEmail", false);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInEmail").addProperty("optInEmail", true);
                }
                break;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I should see in search response {string} is null")
    public void check_search_response_for_opt(String opt) {
        switch (opt) {
            case "optInAddress":
                Object optAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.optInAddress");
                assertNull(optAddress, "optAddress is not null, actual = " + optAddress);
                break;
            case "optInPhone":
                Object optInPhone = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.optInPhone");
                assertNull(optInPhone, "optInPhone is not null, actual = " + optInPhone);
                break;
            case "optInEmail":
                Object optInEmail = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.optInEmail");
                assertNull(optInEmail, "optInEmail is not null, actual = " + optInEmail);
                break;
        }
    }

    @Then("I should see in search response {string} is set to true")
    public void checkSearchSesponceForOptTrue(String opt) {
        switch (opt) {
            case "optInAddress":
                Object optAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.optInAddress");
                assertNotNull(optAddress, "optAddress is null, actual = " + optAddress);
                break;
            case "optInPhone":
                Object optInPhone = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.optInPhone");
                assertNotNull(optInPhone, "optInPhone is null, actual = " + optInPhone);
                break;
            case "optInEmail":
                Object optInEmail = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.optInEmail");
                assertNotNull(optInEmail.equals(true), "optInEmail is null, actual = " + optInEmail);
                break;
        }
    }

    @Then("I send request with inactiveInd true to validate deleting contacts")
    public void sent_request_with_inactiveInd_true() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/newV2ContactsAPIpayload.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("profileId", profileId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uuid", profileId.get() + generateRandomNumberChars(10));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonArray("addresses").getAsJsonArray().get(0).getAsJsonObject().addProperty("inactiveInd", true);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonArray("phones").getAsJsonArray().get(0).getAsJsonObject().addProperty("inactiveInd", true);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonArray("emails").getAsJsonArray().get(0).getAsJsonObject().addProperty("inactiveInd", true);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify search response contains empty contact object")
    public void verify_response_contains_empty_contact_object() {
        ArrayList addressArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.addresses");
        assertTrue(addressArray.isEmpty());
        ArrayList phonesArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.phones");
        assertTrue(phonesArray.isEmpty());
        ArrayList emailsArray = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[1].contact.emails");
        assertTrue(emailsArray.isEmpty());
    }

    @When("I update just created {string} case consumer")
    public void updateCasaConsumerV2(String name, Map<String, String> data) {
        for (String key : data.keySet()) {
            newData.get().put(key, data.get(key));
        }
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, newData.get()));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I add this contact optin {string} in to the payload")
    public void addDataInToContactsPayload(String contact) {
        synchronized (phoneNumber) {
            phoneNumber.set(RandomStringUtils.randomNumeric(10));
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/newV2ContactsAPIpayload.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("profileId", profileId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uuid", profileId.get() + generateRandomNumberChars(10));

        if (contact.contains("phone")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInPhone").addProperty("optInPhone", true);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInPhone").addProperty("phoneNumber", phoneNumber.get());
        } else if (contact.contains("@")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInEmail").addProperty("optInEmail", true);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInEmail").addProperty("emailAddress", contact);
        } else if (contact.contains("mail")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("optInAddress", true);
        }
    }

    @And("I send request to consumer reported V2 API")
    public void I_send_request_to_consumer_reported_V2_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @When("I send request to stop a {string} using CONSUMER_SUBSCRIPTION_UPDATE API")
    public void call_CONSUMER_SUBSCRIPTION_UPDATE_API(String contact) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumersV_2_URI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("crm/event/publish?eventName=CONSUMER_SUBSCRIPTION_UPDATE");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/CONSUMER_SUBSCRIPTION_UPDATE_payload.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").addProperty("userId", "GENID" + profileId.get());
        if (contact.contains("phone")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").addProperty("textNumber", phoneNumber.get());
        } else if (contact.contains("@")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("payload").addProperty("emailAddress", contact);

        }
        try {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        } catch (Exception e) {
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        APIContactRecordEventsController.phoneNumberToBeValidated.set(phoneNumber.get());
    }

    @When("I search consumer by first name and last name getting from newborn file")
    public void searchByFandLname() {
        db.createConnection();
        db.runQuery("SELECT * FROM `mars-etl-dceb`.ETL_REPORT order by JOB_ID DESC");
        String jobID = db.getFirstRowFirstColumn();
        db.runQuery("select Recip_First_Name as 'FirtsName' FROM `mars-etl-dceb`.ETL_NEWBORN_DCEB_INT where job_id='" + jobID + "'");
        String FirstName = db.getFirstRowFirstColumn();
        db.runQuery("select Recip_Last_Name as 'LastName' FROM `mars-etl-dceb`.ETL_NEWBORN_DCEB_INT where job_id='" + jobID + "'");
        String LastName = db.getFirstRowFirstColumn();
        System.out.println("++++++++++++++++++++++++++++++++"+FirstName);
        System.out.println("++++++++++++++++++++++++++++++++"+LastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/searchV2Payload.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("name", LastName + "," + FirstName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify search response case is null")
    public void I_verify_search_response_caseIsNULL() {
        String expectedCase = "" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data.consumers[0].profiles[0].cases[0]");
        assertEquals(expectedCase, "null", "Case not null");
    }

    @When("generate response for consumer reported API with following data")
    public void gen_response_for_consumer_rep_API(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/consumerRep_payloadV2.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("searchConsumerProfile").addProperty("profileId", profileId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uuid", profileId.get() + generateRandomNumberChars(10));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", getCurrentDateInYearFormat() + "T15:01:34.533Z");
        synchronized (addressCity) {
            addressCity.set(getRandomString(7));
        }
        addressState.set("CA");
        synchronized (addressStreet1) {
            addressStreet1.set(getRandomNumber(4) + " ConsumerRep address ct");
        }

        for (String each : data.keySet()) {
            if (each.contains("Address")) {
                if (data.get(each).equals("true")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("optInAddress", true);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("optInAddress", false);
                }
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("addressStreet1", addressStreet1.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("addressCity", addressCity.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("addressState", addressState.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("addressZip", "11111");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInAddress").addProperty("addressZipFour", "4444");
            }
            if (each.contains("Phone")) {
                if (data.get(each).equals("true")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInPhone").addProperty("optInPhone", true);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInPhone").addProperty("optInPhone", false);
                }
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInPhone").addProperty("phoneNumber", phoneNumber.get());
            }
            if (each.contains("Email")) {
                if (data.get(each).equals("true")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInEmail").addProperty("optInEmail", true);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInEmail").addProperty("optInEmail", false);
                }
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("consumerAttributes").getAsJsonObject().getAsJsonObject("optInEmail").addProperty("emailAddress", emailAddress.get());
            }


        }
    }

    @When("I call consumer reported API with following payload")
    public void callConsumerReportedAPIWith(Map<String, String> data) {
        Map<String, String> newData = new HashMap<>();
        UUID.set("UIID-" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        for (String key : data.keySet()) {
            if (data.get(key).contains("null")) {
                newData.put(key, null);
            } else
                newData.put(key, data.get(key));
        }
        newData.put("searchConsumerProfile.profileId", APICreateConsumerContactController.getProfileId());
        newData.put("uuid", UUID.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/newV2ContactsAPIpayload.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, newData));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        profileId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().get("data.profileId"));
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }


    @When("I update just created prior id {string} case consumer")
    public void updatePriorIdCaseConsumerV2(String name, Map<String, String> data) {
        newData.set(new HashMap<>());
        final int maxAge = 100 * 12 * 31;
        synchronized (UUID) {
            UUID.set("UIID2-" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        }
        synchronized (consumerFirstName) {
            consumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        }

        synchronized (consumerLastName) {
            consumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        }

        synchronized (consumerSSN) {
            consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN());
        }

        suffix.set("DDS");
        middleName.set("J");

        synchronized (externalconsumerId) {
            externalconsumerId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        }

        synchronized (externalcaseId) {
            externalcaseId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        }
        synchronized (priorConsumerProfile) {
            priorConsumerProfile.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
        }

        for (String key : data.keySet()) {
            if (data.get(key).contains("random")) {
                switch (key) {
                    case "consumerRequests[0].uuid":
                        newData.get().put("consumerRequests[0].uuid", UUID.get());
                        break;
                    case "consumerRequests[0].firstName":
                        newData.get().put("consumerRequests[0].firstName", consumerFirstName.get());
                        break;
                    case "consumerRequests[0].lastName":
                        newData.get().put("consumerRequests[0].lastName", consumerLastName.get());
                        break;
                    case "consumerRequests[0].ssn":
                        newData.get().put("consumerRequests[0].ssn", consumerSSN.get());
                        break;
                    case "consumerRequests[0].dateOfBirth":
                        consumerDob.set(String.valueOf(LocalDate.now().minusDays(new Random().nextInt(maxAge))));
                        newData.get().put("consumerRequests[0].dateOfBirth", consumerDob.get());
                        break;
                    case "consumerRequests[0].middleName":
                        newData.get().put("consumerRequests[0].middleName", middleName.get());
                        break;
                    case "consumerRequests[0].suffix":
                        newData.get().put("consumerRequests[0].suffix", suffix.get());
                        break;
                    case "consumerRequests[0].consumerProfile.externalId":
                        newData.get().put("consumerRequests[0].consumerProfile.externalId", externalconsumerId.get());
                        break;
                    case "consumerRequests[0].case.externalId":
                        newData.get().put("consumerRequests[0].case.externalId", externalcaseId.get());
                        break;
                    case "consumerRequests[0].priorConsumerProfile.externalId":
                        newData.get().put("consumerRequests[0].priorConsumerProfile.externalId", priorConsumerProfile.get());
                        break;
                }

            } else {

                newData.get().put(key, data.get(key));
            }
            if (key.equals("consumerRequests[0].case")) {
                if (data.get("consumerRequests[0].case").contains("null")) {
                    newData.get().put("consumerRequests[0].case", null);
                }
            }
        }
        if (data.get("consumerRequests[0].consumerProfile.externalId").contains("null")) {
            newData.get().put("consumerRequests[0].consumerProfile.externalId", null);
        }
        if (data.get("consumerRequests[0].priorConsumerProfile.externalId").contains("null")) {
            newData.get().put("consumerRequests[0].priorConsumerProfile.externalId", null);
        }


        System.out.println("consumerFirstName ------------ " + consumerFirstName.get() + "\n" +
                "consumerLastName ------------ " + consumerLastName.get() + "\n" +
                "consumerSSN ------------ " + consumerSSN.get() + "\n" +
                "consumerDob ------------ " + consumerDob.get() + "\n" +
                "consumerExternalId ------------ " + consumerId.get() + "\n" +
                "UPDATED - UUID ------------ " + UUID.get() + "\n" +
                "suffix ------------ " + suffix.get() + "\n" +
                "middleName ------------ " + middleName.get() + "\n" +
                "priorConsumerProfile ------------ " + priorConsumerProfile.get() + "\n"
        );


        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, newData.get()));
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        consumerId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].consumerId"));
        profileId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].profileId"));
        caseId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].caseId"));
        externalconsumerId.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].consumer.profiles[0].externalId"));
        UUID.set("" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("data[0].uuid"));
    }

//    @When("I update just created {string} case consumer")
//    public void updateCasaConsumerV2(String name, Map<String, String> data) {
//        final int maxAge = 100 * 12 * 31;
//        synchronized (UUID) {
//            UUID.set("UIID-" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
//        }
//
//        synchronized (consumerFirstName) {
//            consumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
//        }
//
//        synchronized (consumerLastName) {
//            consumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
//        }
//
//        synchronized (consumerSSN) {
//            consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN());
//        }
//
//        suffix.set("DDS");
//        middleName.set("J");
//
//        synchronized (externalconsumerId) {
//            externalconsumerId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
//        }
//
//        synchronized (externalcaseId) {
//            externalcaseId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
//        }
//        synchronized (proirExternalconsumerId) {
//            externalcaseId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_externalId());
//        }
//
//        for (String key : data.keySet()) {
//            if (data.get(key).contains("random")) {
//                switch (key) {
//                    case "consumerRequests[0].uuid":
//                        newData.get().put("consumerRequests[0].uuid", UUID.get());
//                        break;
//                    case "consumerRequests[0].firstName":
//                        newData.get().put("consumerRequests[0].firstName", consumerFirstName.get());
//                        break;
//                    case "consumerRequests[0].lastName":
//                        newData.get().put("consumerRequests[0].lastName", consumerLastName.get());
//                        break;
//                    case "consumerRequests[0].ssn":
//                        newData.get().put("consumerRequests[0].ssn", consumerSSN.get());
//                        break;
//                    case "consumerRequests[0].dateOfBirth":
//                        consumerDob.set(String.valueOf(LocalDate.now().minusDays(new Random().nextInt(maxAge))));
//                        newData.get().put("consumerRequests[0].dateOfBirth", consumerDob.get());
//                        break;
//                    case "consumerRequests[0].middleName":
//                        newData.get().put("consumerRequests[0].middleName", middleName.get());
//                        break;
//                    case "consumerRequests[0].suffix":
//                        newData.get().put("consumerRequests[0].suffix", suffix.get());
//                        break;
//                    case "consumerRequests[0].consumerProfile.externalId":
//                        newData.get().put("consumerRequests[0].consumerProfile.externalId", externalconsumerId.get());
//                        break;
//                    case "consumerRequests[0].case.externalId":
//                        newData.get().put("consumerRequests[0].case.externalId", externalcaseId.get());
//                        break;
//                    case "consumerRequests[0].priorConsumerProfile.externalId":
//                        newData.get().put("consumerRequests[0]priorConsumerProfile.externalId", proirExternalconsumerId.get());
//                        break;
//                }
//            } else {
//
//                newData.get().put(key, data.get(key));
//            }
//            if (key.equals("consumerRequests[0].case")) {
//                if (data.get("consumerRequests[0].case").contains("null")) {
//                    newData.get().put("consumerRequests[0].case", null);
//                }
//            }
//        }
//        if (data.get("consumerRequests[0].consumerProfile.externalId").contains("null")) {
//            newData.get().put("consumerRequests[0].consumerProfile.externalId", null);
//        }if (data.get("consumerRequests[0].priorConsumerProfile.externalId").contains("null")) {
//            newData.get().put("consumerRequests[0].priorConsumerProfile.externalId", null);
//        }
//        if (data.get("consumerRequests[0].dateOfBirth").contains("<18")) {
//            System.out.println(getPriorYearAPIyyyyMMdd(17, 0) + "!@#$%^&*()(*&^%$#@!@#$%^&*()O(*&^T%R$#@");
//            newData.get().put("consumerRequests[0].dateOfBirth", getPriorYearAPIyyyyMMdd(17, 0));
//        } else if (data.get("consumerRequests[0].dateOfBirth").contains("18")) {
//            newData.get().put("consumerRequests[0].dateOfBirth", getPriorYearAPIyyyyMMdd(18, 0));
//        } else if (data.get("consumerRequests[0].dateOfBirth").contains(">18")) {
//            newData.get().put("consumerRequests[0].dateOfBirth", getPriorYearAPIyyyyMMdd(18, 1));
//        } else if (data.get("consumerRequests[0].dateOfBirth").contains("18-1")) {
//            newData.get().put("consumerRequests[0].dateOfBirth", getPriorYearAPIyyyyMMdd(18, -1));
//        }
//
//
//        System.out.println("consumerFirstName ------------ " + consumerFirstName.get() + "\n" +
//                "consumerLastName ------------ " + consumerLastName.get() + "\n" +
//                "consumerSSN ------------ " + consumerSSN.get() + "\n" +
//                "consumerDob ------------ " + consumerDob.get() + "\n" +
//                "consumerExternalId ------------ " + consumerId.get() + "\n" +
//                "UUID ------------ " + UUID.get() + "\n" +
//                "suffix ------------ " + suffix.get() + "\n" +
//                "middleName ------------ " + middleName.get() + "\n"
//        );
//        for (String key : data.keySet()) {
//            newData.get().put(key, data.get(key));
//        }
//        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
//        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, newData.get()));
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
//
//    }

}
