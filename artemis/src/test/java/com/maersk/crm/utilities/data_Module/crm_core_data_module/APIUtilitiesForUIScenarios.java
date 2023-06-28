
package com.maersk.crm.utilities.data_Module.crm_core_data_module;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ConfigurationReader;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import static com.maersk.crm.utilities.APIClassUtil.getApiProjectNameThreadLocal();
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;


public class APIUtilitiesForUIScenarios extends CRMUtilities implements ApiBase {

    private final static ThreadLocal<APIUtilitiesForUIScenarios> instance = ThreadLocal.withInitial(APIUtilitiesForUIScenarios::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private String baseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String baseCaseloaderURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createConsumerEndPoint = "app/crm/consumer";
    private String createCaseConsumerEndPoint = "app/crm/caseloader";
    private ThreadLocal<String> consumerID = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> caseID = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date todayDate = new Date();
    private String conDOB;
    private String conSSN;
    private final String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
    private String exterConsumerId;
    private String createContactRecordURI = ConfigurationReader.getProperty("apiCrPath");
    private String createContactRecord = "app/crm/contactrecord";
    private String linkContact = "mars/crm/link";
    private String contactReason = "app/crm/contactrecord/contactreason";
    private ThreadLocal<String> contactRecordId = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> phoneNumber = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> contactEmail = ThreadLocal.withInitial(String::new);
    private String apiTmUsers = ConfigurationReader.getProperty("apiTmUsers");
    private String apiTmUsersendPoint="users";

    private APIUtilitiesForUIScenarios() {
    }

    public static APIUtilitiesForUIScenarios getInstance() {
        if(instance.get()==null){
            synchronized (instance){
                instance.set(new APIUtilitiesForUIScenarios());
            }
        }
        return instance.get();
    }

    public synchronized static APIUtilitiesForUIScenarios getInstance(boolean forceCreate) {
        if(forceCreate){
            return new APIUtilitiesForUIScenarios();
        }
        return instance.get();
    }

    private JsonObject generateConsumerRequest() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");

        String conFirstName = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString;
        String consLastName = "ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString);
        String consumerDOB = dateFormat.format(todayDate);
        String consumerSSN = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        setConsumerFirstName(conFirstName);
        setConsumerLastName(consLastName);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", conFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", consLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", consumerDOB);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerIdentificationNumber").get(0).
                getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private void searchForConsumer() {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_search_consumer_by_the(getConsumerFirstName(), getConsumerLastName());
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_initiated_consumer_search_api();
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_run_the_consumer_search_api();
        setConsumerId(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerId.get());
    }

    private void searchForCaseConsumer() {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_search_consumer_by_the(getConsumerFirstName(), getConsumerLastName());
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_initiated_consumer_search_api();
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_run_the_caseConsumer_search_api();
        setCaseId(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerId.get());
    }

    public void createAConsumerAndGetConsumerID() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerEndPoint);
        synchronized (requestParams){
            requestParams.set(generateConsumerRequest());
        }
        System.out.println("Request Params: " + requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        searchForConsumer();
    }

    public void initiateContactRecordApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(createContactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createContactRecord);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/initiateContactRecord.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(json);

        String contactRecordId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getList("contactRecords.contactRecordId").get(0).toString();
        setContactRecordId(contactRecordId);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Initiate Contact Record API is FAIL >>");
    }

    public void linkConsumerToContact() {
        Map<String, String> data = new HashMap<>();

        data.put("externalLinkPayload.internalId", getContactRecordId());
        data.put("externalLinkPayload.externalId", getConsumerId());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(createContactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(linkContact);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/link/link.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Link Contact Record API is FAIL >>");
    }

    public void createContactReasons(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(createContactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactReason);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/createContactReason.json");
        Map<String, String> reasonData = new HashMap<>();
        reasonData.put("contactReasonActions[0].contactRecordId", getContactRecordId());
        reasonData.putAll(data);

        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, reasonData));
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Create Contact Reason API is FAIL >>");
    }

    public void createContactRecordApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(createContactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createContactRecord);

        Map<String, String> data = new HashMap<>();
        data.put("contactRecordId", getContactRecordId());
        data.put("linkRefId", getConsumerId());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/createContactApi.json");

        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Create Contact Record API is FAIL >>");
    }

    public void createContactRecordApi(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(createContactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createContactRecord);

        Map<String, String> conTactRecData = new HashMap<>();
        conTactRecData.put("contactRecordId", getContactRecordId());
        conTactRecData.put("linkRefId", getConsumerId());
        conTactRecData.putAll(data);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/createContactApi.json");

        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, conTactRecData));

        String phoneNumber = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getList("contactRecords.consumerPhone").get(0).toString();
        String email = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getList("contactRecords.consumerEmail").get(0).toString();
        setContactEmail(email);
        setPhoneNumber(phoneNumber);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Create Contact Record API is FAIL >>");
    }

    public void createAConsumerWithDataAndGetConsumerID(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerEndPoint);
        synchronized (requestParams){
            requestParams.set(generateConsumerRequest());
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(requestParams.get().toString(), data));

        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        searchForConsumer();
    }

    public void createContactReasonsNodata() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(createContactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(contactReason);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/createContactReason.json");
        Map<String, String> reasonData = new HashMap<>();
        reasonData.put("contactReasonActions[0].contactRecordId", getContactRecordId());


        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, reasonData));
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Create Contact Reason API is FAIL >>");
    }

    public synchronized void setContactRecordId(String contactRecordId) {
        this.contactRecordId.set(contactRecordId);
    }

    public synchronized String getContactRecordId() {
        return contactRecordId.get();
    }

    public String getConsumerId() {
        return consumerID.get();
    }

    public synchronized void setConsumerId(String consumerID) {
        this.consumerID.set(consumerID);
    }

    public String getConsumerFirstName() {
        return consumerFirstName.get();
    }

    public synchronized void setConsumerFirstName(String consumerFirstName) {
        this.consumerFirstName.set(consumerFirstName);
    }

    public String getConsumerLastName() {
        return consumerLastName.get();
    }

    public synchronized void setConsumerLastName(String consumerLastName) {
        this.consumerLastName.set(consumerLastName);
    }

    public synchronized String getPhoneNumber() {
        return phoneNumber.get();
    }

    public synchronized void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }


    public synchronized String getContactEmail() {
        return contactEmail.get();
    }

    public synchronized void setContactEmail(String contactEmail) {
        this.contactEmail.set(contactEmail);
    }

    private JsonObject generateCaseloaderRequest() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        String conFirstName = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString;
        String consLastName = "ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString);
        String consumerDOB = dateFormat.format(todayDate);
        String consumerSSN = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber;
        String externalConsumerId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber;
        String externalCaseId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(11).randomNumber;

        setConsumerFirstName(conFirstName);
        setConsumerLastName(consLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest")
                .get(0).getAsJsonObject().getAsJsonObject("case")
                .addProperty("caseIdentificationNumber", externalCaseId);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equals("IN-EB")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                    .getAsJsonArray("caseLoaderRequest")
                    .get(0).getAsJsonObject().getAsJsonObject("case")
                    .addProperty("caseIdentificationNumberType", "State");
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                    .getAsJsonArray("caseLoaderRequest")
                    .get(0).getAsJsonObject().getAsJsonObject("case")
                    .addProperty("caseIdentificationNumberType", "Medicaid");
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerDateOfBirth", consumerDOB);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerFirstName", conFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerLastName", consLastName);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest")
                .get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .getAsJsonArray("consumerIdentificationNumber").get(0)
                .getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
        /*if (apiprojectName.equals("IN-EB")) {
            apitdu.jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest")
                    .get(0).getAsJsonObject().getAsJsonObject("case")
                    .getAsJsonArray("consumers").get(0).getAsJsonObject()
                    .getAsJsonArray("consumerIdentificationNumber").get(0)
                    .getAsJsonObject().addProperty("identificationNumberType", "MEDICAID_RID");
        } else {*/
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest")
                    .get(0).getAsJsonObject().getAsJsonObject("case")
                    .getAsJsonArray("consumers").get(0).getAsJsonObject()
                    .getAsJsonArray("consumerIdentificationNumber").get(0)
                    .getAsJsonObject().addProperty("identificationNumberType", "Medicaid");
        //}
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject()
                .getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject()
                .addProperty("consumerRole", "Primary Individual");

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public void createCaseConsumerWithDataAndGetCaseID() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseCaseloaderURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerEndPoint);
        synchronized (requestParams){
            requestParams.set(generateCaseloaderRequest());
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(String.valueOf(requestParams.get()));

        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        searchForCaseConsumer();
    }

    public synchronized void setCaseId(String caseID) {
        this.caseID.set(caseID);
    }

    public synchronized void getUserNamesTMwithAccType(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiTmUsers);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(apiTmUsersendPoint);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/apiGetUserListbyAccType.json");

        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "<< Get USER Names API is FAIL >>");
    }

}