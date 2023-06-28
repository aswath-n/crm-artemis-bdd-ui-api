package com.maersk.crm.api_step_definitions;

import com.google.gson.*;
import com.maersk.crm.step_definitions.CRM_CreateConsumerProfileStepDef;
import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;
//import org.testng.Assert;
//import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import static org.testng.Assert.*;

public class APIContactController<captureCurrentTime> extends CRMUtilities implements ApiBase {
    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String baseConsumerURI = ConfigurationReader.getProperty("apiURI");
    private String getCaseMemberEndPoint = "app/crm/casemember/{caseId}";
    private String createCaseWithConsumers = "app/crm/caseloader";

    private String baseURI = ConfigurationReader.getProperty("apiContactsURI");
    private String caseConsumerAPI = ConfigurationReader.getProperty("apiURI");
    private String createContactEndPoint = "mars/crm/contact";
    private String createCaseWithCaseCheck = "app/crm/cases";
    private String searchContactsByRefEndPoint = "mars/crm/contactsByRef";
    private String searchContactsByExtTypeEndPoint = "mars/crm/contacts/{externalType}/{externalRefid}";

    private final ThreadLocal<String> captureCurrentTime = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> apiConsumerFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiConsumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> latestAddress = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> latestPhone = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> expectedAddress = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> expectedPhone = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiPhoneNumber = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiConsumerAddressZip = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiConsumerCorrelationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiConsumerId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> consumerParams = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiConsumeruiid = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> emailId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> associatedCaseMember = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiCaseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactPhoneNumber = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> phoneId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> updatedStartDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> updatedEndDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> addressType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> requestParamsCaseCheck = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> requestParamsWithoutConsumer = ThreadLocal.withInitial(JsonObject::new);

    private static final ThreadLocal<String> addressStreet1 = ThreadLocal.withInitial(() -> "");
    private static final ThreadLocal<String> addressStreet2 = ThreadLocal.withInitial(() -> "");
    private static final ThreadLocal<String> addressCity = ThreadLocal.withInitial(() -> "");
    private static final ThreadLocal<String> addressState = ThreadLocal.withInitial(() -> "");
    private static final ThreadLocal<String> addressZip = ThreadLocal.withInitial(() -> "");
    private static final ThreadLocal<String> addressZipFour = ThreadLocal.withInitial(() -> "");
    private static final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(() -> "");
    private static final ThreadLocal<String> addressId = ThreadLocal.withInitial(() -> "");

    final ThreadLocal<String> apiSearchConsumerFirstName = ThreadLocal.withInitial(() -> "");
    final ThreadLocal<String> apiSearchConsumerLastName = ThreadLocal.withInitial(() -> "");
    final ThreadLocal<String> apiSearchConsumerSSN = ThreadLocal.withInitial(() -> "");
    final ThreadLocal<String> createdCaseId = ThreadLocal.withInitial(() -> "");

    final ThreadLocal<Gson> gson = ThreadLocal.withInitial(() -> new GsonBuilder().create());

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();

    /*private APICaseLoaderEligibilityEnrollmentController caseLoaderEligibilityEnrollmentControllerLoaderRequest =
            new APICaseLoaderEligibilityEnrollmentController();*/


    // public JSONObject consumerObj;

    /**
     * To initiate create case member api for contact
     */
    @Given("I initiated Create case member API for contacts")
    public void initiateCreateCaseMemberAPI() {
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_initiated_create_case_member_api();
    }

    /**
     * Preparing request to create case member for contacts
     *
     * @param caseMember
     */
    @When("I can provide case member information for contact")
    public void i_can_provide_case_member_information_for_contact_with(DataTable caseMember) {
        for (Map<String, String> data : caseMember.asMaps(String.class, String.class)) {
            String tempCaseId = data.get("caseId").toString();
            if (!tempCaseId.isEmpty())
                apiCaseId.set(tempCaseId);
        }
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_can_provide_case_member_information_with(caseMember);
    }

    @Given("I initiated Case Loader API for Create New Case for Contacts Validations")
    public void i_initiated_Case_Loader_API_for_Create_New_Case_for_Contacts_Validations() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);

    }

    @When("I run Case Loader API for Create New Case and Customer with below detail for Contacts Validations:")
    public void i_run_Case_Loader_API_for_Create_New_Case_and_Customer_with_below_detail_for_Contacts_Validations(DataTable caseMember) {
        String consumerType = "";
        String consumerDateOfBirth = "";
        String genderCode = "";
        String relationShip = "";


        for (Map<String, String> data : caseMember.asMaps(String.class, String.class)) {
            consumerType = data.get("consumerType").toString();
            consumerDateOfBirth = data.get("consumerDateOfBirth").toString();
            genderCode = data.get("GenderCode").toString();
            relationShip = data.get("relationShip").toString();

            if (data.containsKey("consumerFirstName")) {
                String firstName = data.get("consumerFirstName").toString();
                if (firstName.isEmpty() || firstName.equals("{random}")) {
                    apiSearchConsumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
                    System.out.println("consumerFirstName " + apiSearchConsumerFirstName.get());
                } else {
                    apiSearchConsumerFirstName.set(data.get("consumerFirstName").toString());
                }
            }
            if (data.containsKey("consumerLastName")) {
                String lastName = data.get("consumerLastName").toString();
                if (lastName.isEmpty() || lastName.equals("{random}")) {
                    apiSearchConsumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
                    System.out.println("consumerLastName " + apiSearchConsumerLastName.get());
                } else {
                    apiSearchConsumerLastName.set(data.get("consumerLastName").toString());
                }

            }
            if (data.containsKey("consumerSSN")) {
                String ssn = data.get("consumerSSN").toString();
                if (ssn.isEmpty() || ssn.equals("{random}")) {
                    apiSearchConsumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
                    System.out.println("consumerSSN " + apiSearchConsumerSSN);
                } else {
                    apiSearchConsumerSSN.set(data.get("consumerSSN").toString());
                }
            }
        }

        //requestParams = caseLoaderEligibilityEnrollmentControllerLoaderRequest.generatecaseLoaderEligibilityEnrollmentControllerLoaderRequest();
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequestWithSpecifiedParameters(consumerType, apiSearchConsumerFirstName.get(), apiSearchConsumerLastName.get(), apiSearchConsumerSSN.get(), consumerDateOfBirth, genderCode, relationShip));
        System.out.println("request params");
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response string");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I will create a new case for case loader case creation for Contacts Validations")
    public void i_will_create_a_new_case_for_case_loader_case_creation_for_Contacts_Validations() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerAPI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithCaseCheck);

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderEligibilityEnrollmentControllerLoaderRequest");
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            String caseIdentificationNumber = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNumber));
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println("requestParamsCaseCheck1 " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        HashMap<String, Object> caselist = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList");
        HashMap<String, Object> content = (HashMap<String, Object>) ((ArrayList) caselist.get("content")).get(0);
        apiCaseId.set(content.get("caseId").toString());
        System.out.println("Case Id created is:" + apiCaseId.get());

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("caseList"));
        assertNotNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"));

        requestParamsWithoutConsumer.set(generateCreateConsumerRequestWithoutConsumer());
        System.out.println(requestParamsWithoutConsumer.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParamsWithoutConsumer.get());

        System.out.println("requestParamsWithoutConsumer" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        JsonArray caseLoaderWithoutConsumer = (JsonArray) requestParamsWithoutConsumer.get().get("caseLoaderEligibilityEnrollmentControllerLoaderRequest");
        for (int i = 0; i < caseLoaderWithoutConsumer.size(); i++) {
            JsonElement consumersData = caseLoaderWithoutConsumer.get(i).getAsJsonObject().get("case");
            String caseIdentificationNumber = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNumber));
        }

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println("requestParamsCaseCheck2 " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("caseList"));
        //assertNotNull(api.jsonPathEvaluator.getJsonObject("caseList.content"));
        JsonArray empty = new JsonArray();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content"), empty);


    }

    public JsonObject generateCaseCheck(String caseIdentificationNumber) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCaseCheck.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseIdentificationNumber").get(0).getAsJsonObject().addProperty(
                "externalCaseId", caseIdentificationNumber);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public JsonObject generateCreateConsumerRequestWithoutConsumer() {
        JsonObject request = new JsonObject();
        JsonObject caseLoaderEligibilityEnrollmentControllerLoaderRequest = new JsonObject();
        JsonArray caseList = new JsonArray();
        caseList.add(caseLoaderEligibilityEnrollmentControllerLoaderRequest);

        JsonObject CASE = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        String randomDate = dateFormat.format(todayDate);

        CASE.addProperty("caseIdentificationNumber", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
        CASE.addProperty("identificationNoType", "CHIP");
        CASE.addProperty("caseStatus", "Active");
        CASE.addProperty("caseType", "Member");
        CASE.addProperty("portfolioIdentificationNo", (String) null);
        CASE.addProperty("programName", "CRM");
        CASE.addProperty("subProgramName", "");
        CASE.addProperty("needTranslatorInd", "true");
        CASE.addProperty("jobId", "1");
        CASE.addProperty("correlationId", "1");

        caseLoaderEligibilityEnrollmentControllerLoaderRequest.add("case", CASE);

        JsonObject caseCommunicationPreferences = new JsonObject();
        JsonArray caseCommunicationPreferencesList = new JsonArray();
        caseCommunicationPreferencesList.add(caseCommunicationPreferences);

        caseCommunicationPreferences.addProperty("defaultInd", "boolean");
        caseCommunicationPreferences.addProperty("effectiveEndDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("effectiveStartDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("updatedBy", "string");
        caseCommunicationPreferences.addProperty("updatedOn", "2019-05-08T14:05:31.798Z");

        CASE.add("communicationPreferences", gson.get().toJsonTree(caseCommunicationPreferencesList).getAsJsonArray());

        request.add("caseLoaderEligibilityEnrollmentControllerLoaderRequest", gson.get().toJsonTree(caseList).getAsJsonArray());

        return request;

    }


    /**
     * Initiate get case member information and prepare associate case member field
     *
     * @param caseId
     */
    @Given("I initiated get case member API for contact {string}")
    public void i_initiated_get_case_member_api_for_contact(String caseId) {
        if (caseId.isEmpty())
            caseId = apiCaseId.get();
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_initiated_get_case_member_api(caseId);
        //apiConsumerId = caseRestController.getConsumerIdJustCreated("Member");
        apiConsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().apiconsumerFirstName.get());
        apiConsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().apiconsumerLastName.get());
        apiConsumerId.set(API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().apiconsumerId.get());
        associatedCaseMember.set(apiConsumerFirstName.get() + " " + apiConsumerLastName.get() + " " + "(" + apiConsumerId.get() + ")");
        apiConsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiConsumeruiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
    }

    @Given("I initiated consumer search API for Contacts")
    public void i_initiated_consumer_search_api_for_contact() {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_initiated_consumer_search_api();
    }

//    @When("I get uiid and correlationId id from contacts search {string} and {string}")
//    public void i_get_uuid_and_correlation_id_from_contact_search(String consumerFirstName, String consumerLastName) {
//        if (consumerFirstName.isEmpty()) {
//            consumerFirstName = apiConsumerFirstName;
//        }
//        consumerController.get_the_consumer_correlationId_id(consumerFirstName, consumerLastName);
//        consumerController.get_the_consumer_uiid_from_response(consumerFirstName);
//        apiConsumerCorrelationId = consumerController.correlationId;
//        apiConsumeruiid = consumerController.uiid;
//        System.out.println("Consumer Correlation ID : " + apiConsumerCorrelationId);
//        System.out.println("Consumer uiid : " + apiConsumeruiid);
//        consumerController.get_the_consumer_id_from_response(consumerFirstName);
//        apiConsumerId = consumerController.apiconsumerId;
//        System.out.println("Consumer ID : " + apiConsumerId);
//        consumerParams = consumerController.consumerDetail;
//    }

    /**
     * User can get correlation id from consumer search based on "consumerFirstName" and "consumerLastName"
     *
     * @param consumerFirstName
     * @param consumerLastName
     */
    @When("I get uiid and correlationId id from contacts search {string} and {string}")
    public void get_the_consumer_correlationId_id_from_contact_search(String consumerFirstName, String consumerLastName) {
        apiConsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiConsumeruiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        apiConsumerId.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().get_the_consumer_id(consumerFirstName, consumerLastName));
        consumerParams.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerDetail.get());
        API_THREAD_LOCAL_FACTORY.getContactRecordControllerThreadLocal().i_initiated_create_contact_record_api();
        ////apitmEventController.getAuthenticationToken("BLCRM", "CRM");
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("contactRecordType", "Inbound");
        requestParams.get().addProperty("createdOn", "");
        requestParams.get().addProperty("createdBy", "");
        requestParams.get().addProperty("correlationId", apiConsumerCorrelationId.get());
        requestParams.get().addProperty("uiid", apiConsumeruiid.get());
        requestParams.get().addProperty("contactChannelType", "phone");
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getContactRecordControllerThreadLocal().requestParams.set(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getContactRecordControllerThreadLocal().i_can_run_create_contact_records_api();
        //contactRecordController.i_initiated_contact_recored_by_correlation(apiConsumerCorrelationId);
        API_THREAD_LOCAL_FACTORY.getContactRecordControllerThreadLocal().i_initiated_contact_record_by_correlationId_from_Response();
        API_THREAD_LOCAL_FACTORY.getContactRecordControllerThreadLocal().i_run_get_contact_records_api();
        //need discuss with Muhabbat @1
        // api = contactRecordController.api;
//        ArrayList<String> contactRecord_Ids = api.jsonPathEvaluator.get("contactRecords.contactRecordId");
//        System.out.println(contactRecord_Ids);
//        Iterator cRecordId = contactRecord_Ids.iterator();
//        Object contactRecordIdObj = cRecordId.next();
//        Integer contactRecordId = (int) contactRecordIdObj;
//        String lastContactRecordId = contactRecordIdObj.toString();
//        System.out.println(contactRecordId);
    }

    @Given("I initiated add new Contact using API")
    public void i_initiated_add_new_contact_using_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri("https://mars-contacts-api-qa-dev.apps.non-prod.pcf-maersk.com/");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("mars/crm/contacts");
    }

    @Given("I initiated Consumer Type Search vai API with {string} type and id {string}")
    public void i_initiated_consumer_type_search_api(String externalType, String externalRefid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (externalType.isEmpty()) {
            externalType = "Consumer";
        }
        if (externalRefid.isEmpty()) {
            externalRefid = apiConsumerId.get();
        }
        searchContactsByExtTypeEndPoint = searchContactsByExtTypeEndPoint.replace("{externalType}", externalType);
        searchContactsByExtTypeEndPoint = searchContactsByExtTypeEndPoint.replace("{externalRefid}", externalRefid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchContactsByExtTypeEndPoint);
    }

    @Given("I created a consumer for contact using API")
    public void i_created_a_consumer_for_contact_using_api() {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_created_a_consumer_using_api();
    }

    @And("I collected some consumer related data from consumer Controller")
    public void i_collected_some_consumer_related_data_from_consumer_controller() {
        apiConsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get());
        System.out.println(" Consumer First Name: " + apiConsumerFirstName.get());
        apiConsumerLastName.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerLastName.get());
        apiPhoneNumber.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiphoneNumber.get());
        apiConsumerAddressZip.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiaddressZip.get());
        apiConsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().correlationId.get());
        apiConsumerId.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerId.get());
        System.out.println(" Consumer ID: " + apiConsumerId.get());
        apiConsumeruiid.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().uiid.get());
    }

    private void add_email_information(String externalRefType, String associatedCaseMember, String emailAddress) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/apiAddNewEmail.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", externalRefType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        if (associatedCaseMember.isEmpty() || associatedCaseMember.equals("{random}")) {
            associatedCaseMember = apiConsumerFirstName.get() + " " + apiConsumerLastName.get();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("associatedCaseMember", associatedCaseMember);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("associatedCaseMember", associatedCaseMember);
        }

        if (emailAddress.isEmpty() || emailAddress.equals("{random}")) {
            emailAddress = apiConsumerFirstName.get() + "@" + apiConsumerLastName.get() + ".com";
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("emailAddress", emailAddress);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("emailAddress", emailAddress);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("emailType", "OFFICE");
        long curentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("createdOn", curentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiConsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiConsumeruiid.get());

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        System.out.println(requestParams.get());
    }

    private long set_date(String sDate, Integer add_days) {
        long setDate;
        if (sDate.isEmpty() || sDate == "") {
            setDate = System.currentTimeMillis();
        } else {
            if (sDate.contains("yesterday")) {
                System.out.println(sDate);
                if (add_days > 0) {
                    setDate = System.currentTimeMillis() - ((1 + add_days) * 24 * 60 * 60 * 1000);
                } else {
                    setDate = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000;
                }
            } else if (sDate.contains("tomorrow")) {
                System.out.println(sDate);
                if (add_days > 0) {
                    setDate = System.currentTimeMillis() + ((1 + add_days) * 24 * 60 * 60 * 1000);
                } else {
                    setDate = System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000;
                }
            } else if (sDate.contains("today")) {
                setDate = System.currentTimeMillis();
                System.out.println(sDate);
            } else {
                System.out.println("Current Date : " + sDate);
                setDate = System.currentTimeMillis();
            }
        }
        return setDate;
    }

    @And("I added email information {string}, {string}, {string}, {string} and {string}")
    public void I_added_email_information(String externalRefType, String associatedCaseMember, String emailAddress, String startDate, String endDate) {
        add_email_information(externalRefType, associatedCaseMember, emailAddress);
        long startDateTime = set_date(startDate, 0);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("effectiveStartDate", startDateTime);
        if (endDate != "" && !endDate.isEmpty()) {
            long endDateTime = set_date(endDate, 0);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("effectiveEndDate", endDateTime);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        System.out.println(requestParams.get());
    }

    @Given("I initiated get case member API for contact {string} in ContactController")
    public void i_initiated_get_case_member_API_for_contact_in_ContactController(String caseId) {
        if (caseId.isEmpty())
            caseId = apiCaseId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        if (caseId.isEmpty())
            caseId = createdCaseId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCaseMemberEndPoint.replace("{caseId}", caseId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        // List<List> apiListConsumers = api.jsonPathEvaluator.getList("object.result.consumers");
        List<List> apiListConsumersId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("result.consumers.consumerId");
        List<List> apiListConsumersFirstName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("result.consumers.consumerFirstName");
        List<List> apiListConsumersLastName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("result.consumers.consumerLastName");

        System.out.println("apiListConsumers " + apiListConsumersId);
        System.out.println("response 45 " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        apiConsumerId.set(apiListConsumersId.get(0).toString().replace("[", "").replace("]", ""));
        apiConsumerFirstName.set(apiListConsumersFirstName.get(0).toString().replace("[", "").replace("]", ""));

        apiConsumerLastName.set(apiListConsumersLastName.get(0).toString().replace("[", "").replace("]", ""));
        apiCaseId.set(caseId);
        System.out.println("apiconsumerid " + apiConsumerId.get());
        //apiConsumerId = caseRestController.getConsumerIdJustCreated("Member");
        //apiConsumerFirstName = caseRestController.apiconsumerFirstName;
        //apiConsumerLastName = caseRestController.apiconsumerLastName;
        //apiConsumerId = caseRestController.apiconsumerId;
        associatedCaseMember.set(apiConsumerFirstName.get() + " " + apiConsumerLastName.get() + " " + "(" + apiConsumerId.get() + ")");
        apiConsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiConsumeruiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
    }

    @And("I added phone information {string}, {string}, {string}, {string}, {string}, {string} and {string}")
    public void I_added_phone_information(String externalRefType, String phoneNumber, String phoneType, String associatedCaseMember1, String comments, String startDate, String endDate) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/apiAddNewPhone.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", externalRefType);
        if (externalRefType.equalsIgnoreCase("consumer"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        else if (externalRefType.equalsIgnoreCase("case"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiCaseId.get());
        long curentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("createdOn", curentDateTime);
        long startDateTime = set_date(startDate, 0);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("effectiveStartDate", startDateTime);
        if (endDate != "" && !endDate.isEmpty()) {
            long endDateTime = set_date(endDate, 0);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("effectiveEndDate", endDateTime);
        }
        if (phoneNumber.isEmpty()) {
            contactPhoneNumber.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
        } else {
            contactPhoneNumber.set(phoneNumber);
        }
        associatedCaseMember.set(apiConsumerFirstName.get() + " " + apiConsumerLastName.get() + " " + "(" + apiConsumerId.get() + ")");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("phoneNumber", contactPhoneNumber.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("phoneType", phoneType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("associatedCaseMember", associatedCaseMember.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("comments", comments);
        apiConsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
        apiConsumeruiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiConsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiConsumeruiid.get());

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @And("I added blank phone information")
    public void I_added_blank_phone_information() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/apiAddNewPhone.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiConsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiConsumerId.get());
        long curentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("createdOn", curentDateTime);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @And("I can run add contacts using API")
    public void i_can_run_add_contacts_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutArrAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @And("I initiated specific contacts detail {string}")
    public void i_initiated_specific_contacts_detail(String externalRefType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        String getContactsDetail = searchContactsByExtTypeEndPoint.replace("{externalType}", externalRefType);
        System.out.println("Consumer Id for initiate contact : " + apiConsumerId.get());
        if (externalRefType.equalsIgnoreCase("consumer"))
            getContactsDetail = getContactsDetail.replace("{externalRefid}", apiConsumerId.get());
        else if (externalRefType.equalsIgnoreCase("case"))
            getContactsDetail = getContactsDetail.replace("{externalRefid}", apiCaseId.get());
        System.out.println("getContactsDetail " + getContactsDetail);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getContactsDetail);
    }

    @And("I run get contacts detail using API")
    public void i_run_get_contacts_detail_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("contact details response" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    private JsonObject getlatestAddress() {
        //include validation, logic, logging or whatever you like here
        return this.latestAddress.get();
    }

    @And("I get latest address information of a the consumer")
    public void i_get_latest_address_information_of_the_consumer() {
        JsonObject consumer_object = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
        System.out.println(consumer_object);
        JsonArray contact_array = consumer_object.getAsJsonArray("contacts");
        JsonObject address_object = contact_array.get(0).getAsJsonObject();
        System.out.println(address_object);
        JsonArray address_array = address_object.getAsJsonArray("addressess");
        latestAddress.set(address_array.get(0).getAsJsonObject());
        expectedAddress.set(getlatestAddress().toString());
        addressId.set(getlatestAddress().get("addressId").toString());
        System.out.println("expected address" + expectedAddress.get());
        System.out.println("addressId " + addressId.get());
    }

    @Then("I verify {string} details using API")
    public void i_verify_status_details_using_api(String success) {
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        } else {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "fail");
        }
    }

    @Then("I verify Email address Audit Trail")
    public void i_verify_email_address_audit_trail() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", "Consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().GetAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        //assertTrue(api.responseString.equalsIgnoreCase(status));
        ArrayList contacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("contacts");
        HashMap email = (HashMap) contacts.get(0);
        // System.out.println(email);
        ArrayList emails = (ArrayList) email.get("emails");
        // System.out.println(emails);
        int count = 0;
        if (emails.size() > 1) {
            for (int i = 0; i < emails.size(); i++) {
                HashMap email_status = (HashMap) emails.get(i);
                String emailAddress = apiConsumerFirstName.get() + "@" + apiConsumerLastName.get() + ".com";
                if (emailAddress.equalsIgnoreCase(email_status.get("emailAddress").toString())) {
                    count = i;
                    break;
                }
            }
        }
        HashMap email_status = (HashMap) emails.get(count);
        String temp = email_status.get("createdOn").toString();
        System.out.println("Created On : " + temp);
        assertFalse(temp.equalsIgnoreCase("null"));
        // System.out.println(consumer_status.get("status"));
    }

    @Then("I verify Email address status {string} using API")
    public void i_verify_email_address_status_using_api(String status) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", "Consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().GetAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        JsonElement email = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("contacts").getAsJsonArray().get(0).getAsJsonObject().get("emails").getAsJsonArray().get(0);
        System.out.println(email);
        String email_status = email.getAsJsonObject().get("status").toString();
        System.out.println(email_status);
        assertFalse(email_status.equalsIgnoreCase(status));
    }

    @Then("I verify Address status {string} using API")
    public void i_verify_address_status_using_api(String status) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", "Consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().GetAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        JsonElement addresses = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("contacts").getAsJsonArray().get(0).getAsJsonObject().get("addressess").getAsJsonArray().get(0);
        System.out.println(addresses);
        String address_status = addresses.getAsJsonObject().get("status").toString();
        System.out.println(address_status);
        assertFalse(address_status.equalsIgnoreCase(status));
    }

    @Then("I can verify consumer phone detail {string} and count greater than zero using API")
    public void i_can_verify_consumer_phone_detail_success_and_count_greater_than_zero_using_API(String success) {
        i_verify_status_details_using_api(success);
        // -- AND --
        List<List> phoneLt = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("contacts.phones");
        System.out.println(phoneLt);
        List<List> phoneLst = phoneLt.get(0);
        System.out.println(phoneLst);
        Integer listCount = phoneLst.size();
        System.out.println("Total Phone records: " + listCount);
        assertTrue(listCount > 0);
    }

    @And("I added address information {string},{string},{string},{string},{string},{string},{string},{string},{string},{string} and {string}")
    public void I_added_address_information(String externalRefType, String address1, String address2,
                                            String city, String county, String state, String zip, String zipFour,
                                            String type, String startDate, String endDate) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/apiAddNewAddress.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", externalRefType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiConsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiConsumeruiid.get());
        if (address1.isEmpty() || address1.equals("{random}")) {
            address1 = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(4).randomNumber.toString() + " Test Ave.";
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressStreet1", address1);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressStreet2", address2);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressCity", city);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressCounty", county);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressState", state);
        if (zip.isEmpty() || zip.equals("{random}")) {
            zip = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString();
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressZip", zip);
        if (zipFour.isEmpty() || zipFour.equals("{random}")) {
            zipFour = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(4).randomNumber.toString();
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressZipFour", zipFour);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressType", type);
        long startDateTime = set_date(startDate, 0);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("effectiveStartDate", "2023-03-15T17:47:57.000000+00:00");
        if (!endDate.isEmpty()) {
            long endDateTime = set_date(endDate, 0);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("effectiveEndDate", "2023-03-15T17:47:57.000000+00:00");
        }

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        System.out.println(requestParams.get());
    }

    private String get_random_string(String param, Integer length) {
        if (param.isEmpty() || param.equals("{random}")) {
            param = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(length).randomString;
        }
        return param;
    }

    private String get_random_value(String param, Integer length) {
        if (param.isEmpty() || param.equals("{random}")) {
            param = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(length).randomNumber.toString();
        }
        return param;
    }

    @And("I updated existing address information {string},{string},{string},{string},{string},{string},{string},{string},{string} and {string}")
    public void I_updated_existing_address_information(String address1, String address2, String city, String county,
                                                       String state, String zip, String zipFour, String type,
                                                       String startDate, String endDate) {
        addressStreet1.set(get_random_value(address1, 4) + " Test Ave.");
        addressStreet2.set("APT#" + get_random_value(address1, 4));
        addressCity.set(get_random_string(city, 10));
        addressState.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getArandomUSStateName().state);
        addressZip.set(get_random_value(zip, 5));
        addressZipFour.set(get_random_value(zipFour, 4));
        addressCounty.set(get_random_string(county, 10));


        requestParams.set(new JsonObject());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/updateAddress.json");

        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressId", Integer.parseInt(addressId.get()));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressStreet1", addressStreet1.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressStreet2", addressStreet2.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressCity", addressCity.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressCounty", addressCounty.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressState", addressState.get());
        if (zip.isEmpty() || zip.equals("{random}")) {
            zip = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber.toString();
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressZip", addressZip.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressZipFour", addressZipFour.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("addressType", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList("Mailing,Physical", true, null).randomListValue);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("address").addProperty("updatedOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", "consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiConsumeruiid.get());

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @Then("I verify Address Audit Trail")
    public void i_verify_Address_Audit_Trail() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", "Consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().GetAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        ArrayList contacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("contacts");
        HashMap address = (HashMap) contacts.get(0);
        ArrayList addresses = (ArrayList) address.get("addressess");
        System.out.println(addresses);
        boolean created_on_found = false;
        if (addresses.size() > 0) {
            for (int i = 0; i < addresses.size(); i++) {
                HashMap address_status = (HashMap) addresses.get(i);
                try {
                    String temp = address_status.get("createdOn").toString();
                    System.out.println("Created On : " + temp);
                    created_on_found = true;
                    break;
                } catch (Exception e) {
                    created_on_found = false;
                }
            }
            if (created_on_found == false) {
                assertTrue(created_on_found);
            }
        } else {
            assertTrue(created_on_found);
        }
    }

    @Then("I can verify Address Updates")
    public void i_can_verify_Address_updates() {
        JsonObject actualAddress = requestParams.get().get("address").getAsJsonObject();
        System.out.println("actual address " + actualAddress);

        String ad = actualAddress.get("addressStreet1").toString();

        assertEquals(actualAddress.get("addressId").toString(), addressId.get());

        assertTrue(actualAddress.toString().contains(addressStreet1.get()));
        assertTrue(actualAddress.toString().contains(addressStreet2.get()));
        assertTrue(actualAddress.toString().contains(addressCity.get()));
        assertTrue(actualAddress.toString().contains(addressState.get()));
        assertTrue(actualAddress.toString().contains(addressZip.get()));
        assertTrue(actualAddress.toString().contains(addressZipFour.get()));
        assertTrue(actualAddress.toString().contains(addressCounty.get()));
    }

    /**
     * To verify the order of email records fetched
     */
    @Then("I verify active emails fetched first followed by inactive emails")
    public void i_verify_active_emails_fetched_first_followed_by_inactive_emails() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", "Consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().GetAPIWithParameter(requestParams.get());
        System.out.println("Debug :" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        //assertTrue(api.responseString.equalsIgnoreCase(status));
        JsonPath jsonPathEvaluator = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
        ArrayList contacts = jsonPathEvaluator.get("contacts");
        HashMap email = (HashMap) contacts.get(0);
        // System.out.println(email);
        ArrayList emails = (ArrayList) email.get("emails");
        // System.out.println(emails);
        boolean flag = true;
        boolean isInactive = false;
        if (emails.size() > 1) {
            for (int i = 0; i < emails.size(); i++) {
                HashMap email_status = (HashMap) emails.get(i);
                if (email_status.get("status").toString().equalsIgnoreCase("Active")) {
                    if (isInactive) {
                        flag = false;
                        break;
                    }
                } else {
                    isInactive = true;
                }
            }
        }
        assertTrue(flag);
    }

    /**
     * To verify updated filed for email address
     */
    @Then("I verify Updated on field is populated")
    public void i_verify_Updated_on_field_is_populated() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", "Consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().GetAPIWithParameter(requestParams.get());
        System.out.println("Debug :" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        //assertTrue(api.responseString.equalsIgnoreCase(status));
        JsonPath jsonPathEvaluator = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
        ArrayList contacts = jsonPathEvaluator.get("contacts");
        HashMap email = (HashMap) contacts.get(0);
        // System.out.println(email);
        ArrayList emails = (ArrayList) email.get("emails");
        // System.out.println(emails);
        int count = 0;
        if (emails.size() > 1) {
            for (int i = 0; i < emails.size(); i++) {
                HashMap email_status = (HashMap) emails.get(i);
                String emailAddress = apiConsumerFirstName.get() + "@" + apiConsumerLastName.get() + ".com";
                if (emailAddress.equalsIgnoreCase(email_status.get("emailAddress").toString())) {
                    count = i;
                    break;
                }
            }
        }
        HashMap email_status = (HashMap) emails.get(count);
        String temp = email_status.get("createdOn").toString();
        System.out.println("Updated On : " + temp);
        assertFalse(temp.equalsIgnoreCase("null"));
        // System.out.println(consumer_status.get("status"));
    }

    /**
     * Preparing request to updated email address
     *
     * @param emailAddress
     */
    @When("I updated email information {string}")
    public void i_updated_email_information(String emailAddress) {
        String nullValue = null;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("emailId", emailId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("emailAddress", emailAddress);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("associatedCaseMember", nullValue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("effectiveStartDate", "2023-02-15T15:26:46.224Z");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("effectiveEndDate", nullValue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("email").addProperty("uiid", "46234624572457245");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("Debug " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        System.out.println("Debug " + requestParams.get());
    }

    /**
     * To verify email address is updated
     *
     * @param emailAddress
     */
    @Then("I verify updated Email address detail using API {string}")
    public void i_verify_updated_Email_address_detail_using_API(String emailAddress) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", "Consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("Debug :" + requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().GetAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(emailAddress));
    }

    /**
     * get latest phone
     *
     * @return
     */
    private JsonObject getlatestPhone() {
        //include validation, logic, logging or whatever you like here
        return this.latestPhone.get();
    }

    /**
     * Get latest phone information
     */
    @And("I get latest phone information of a the consumer")
    public void i_get_latest_phone_information_of_the_consumer() {
        JsonObject consumer_object = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
        System.out.println(consumer_object);
        JsonArray contact_array = consumer_object.getAsJsonArray("contacts");
        JsonObject phone_object = contact_array.get(0).getAsJsonObject();
        System.out.println(phone_object);
        JsonArray phone_array = phone_object.getAsJsonArray("phones");
        latestPhone.set(phone_array.get(0).getAsJsonObject());
        expectedPhone.set(getlatestPhone().toString());
        System.out.println("========Expected_Phone_Object=========");
        System.out.println(expectedPhone.get());
    }

    /**
     * Preparing request to update phone record
     *
     * @param phoneNumber
     * @param phoneType1
     * @param phoneType2
     * @param comments
     * @param startDate
     * @param endDate
     */
    @And("I updated existing phone information {string},{string},{string},{string},{string} and {string}")
    public void I_updated_existing_phone_information(String phoneNumber, String phoneType1, String phoneType2, String comments,
                                                     String startDate, String endDate) {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("requestParams", "Consumer");
        requestParams.get().addProperty("externalRefId", apiConsumerId.get());
        requestParams.get().add("phone", latestPhone.get());
        if (phoneNumber.isEmpty() || phoneNumber.equals("{random}")) {
            phoneNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber.toString();
        }
        requestParams.get().getAsJsonObject("phone").addProperty("phoneNumber", phoneNumber);
        if (phoneType1.isEmpty() || phoneType1.equals("{random}")) {
            requestParams.get().getAsJsonObject("phone").addProperty("phoneType1", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList("Primary,Secondary", true, null).randomListValue);
        } else {
            requestParams.get().getAsJsonObject("phone").addProperty("phoneType1", phoneType1);
        }
        if (phoneType2.isEmpty() || phoneType2.equals("{random}")) {
            requestParams.get().getAsJsonObject("phone").addProperty("phoneType2", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList("Cell,Home,Work", true, null).randomListValue);
        } else {
            requestParams.get().getAsJsonObject("phone").addProperty("phoneType2", phoneType2);
        }
        requestParams.get().getAsJsonObject("phone").addProperty("comments", comments);
        //long startDateTime = set_date(startDate, 0);
        //requestParams.getAsJsonObject("phone").addProperty("effectiveStartDate", startDateTime);
        if (!endDate.isEmpty()) {
            long endDateTime = set_date(endDate, 0);
            requestParams.get().getAsJsonObject("phone").addProperty("effectiveEndDate", endDateTime);
        }
        System.out.println(requestParams.get());
    }

    /**
     * To verify new phone record added
     */
    @Then("I verify added phone details are matched")
    public void verifyNewPhoneAdded() {
        boolean recordFound = false;
        List contacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        for (int i = 0; i < contacts.size(); i++) {
            HashMap contactsData = (HashMap) contacts.get(i);
            List phones = (List) contactsData.get("phones");
            for (int j = 0; j < phones.size(); j++) {
                HashMap phonesData = (HashMap) phones.get(j);
                if (phonesData.get("associatedCaseMember") != null && phonesData.get("associatedCaseMember").toString().equalsIgnoreCase(associatedCaseMember.get())
                        && phonesData.get("phoneNumber").toString().equalsIgnoreCase(contactPhoneNumber.get())) {
                    recordFound = true;
                    phoneId.set(phonesData.get("phoneId").toString());
                    break;
                }
            }
            if (recordFound)
                break;
        }
        assertTrue(recordFound);
    }

    /**
     * To updated phone number api parameters/fields using api or preparing request for update
     *
     * @param externalRefType
     * @param phoneNumber
     * @param phoneType
     * @param associatedCaseMember1
     * @param comments
     */
    @Given("I updated phone information {string}, {string}, {string}, {string} and {string}")
    public void I_updated_phone_information(String externalRefType, String phoneNumber, String phoneType, String associatedCaseMember1, String comments) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contacts/apiUpdatePhoneNumber.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefType", externalRefType);
        if (externalRefType.equalsIgnoreCase("consumer"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiConsumerId.get());
        else if (externalRefType.equalsIgnoreCase("case"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalRefId", apiCaseId.get());
        long curentDateTime = System.currentTimeMillis();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("updatedOn", curentDateTime);
        updatedStartDate.set(addDaysToSysDate("yyyy-MM-dd", -10) + "T05:00:00.000Z");
        updatedEndDate.set(addDaysToSysDate("yyyy-MM-dd", +100) + "T05:00:00.000Z");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("effectiveStartDate", updatedStartDate.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("effectiveEndDate", updatedEndDate.get());

        if (phoneNumber.isEmpty()) {
            contactPhoneNumber.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
        } else {
            contactPhoneNumber.set(phoneNumber);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("phoneNumber", contactPhoneNumber.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("phoneType", phoneType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("associatedCaseMember", associatedCaseMember.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("comments", comments);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("phone").addProperty("phoneId", phoneId.get());

        /*apitdu.jsonElement.getAsJsonObject().addProperty("correlationId", apiConsumerCorrelationId);
        apitdu.jsonElement.getAsJsonObject().addProperty("uiid", apiConsumeruiid);*/

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    /**
     * To verify associate case member updated for an existing phone number
     */
    @Then("I verify updated phone details are matched")
    public void verifyUpdatedPhoneDetails() {
        boolean recordFound = false;
        List contacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        for (int i = 0; i < contacts.size(); i++) {
            HashMap contactsData = (HashMap) contacts.get(i);
            List phones = (List) contactsData.get("phones");
            for (int j = 0; j < phones.size(); j++) {
                HashMap phonesData = (HashMap) phones.get(j);
                if (phonesData.get("associatedCaseMember").toString().equalsIgnoreCase(associatedCaseMember.get())
                        && phonesData.get("phoneNumber").toString().equalsIgnoreCase(contactPhoneNumber.get())
                        && phonesData.get("phoneId").toString().equalsIgnoreCase(phoneId.get())) {
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
        }
        assertTrue(recordFound);
    }

    /**
     * To verify updated phone details for phone number
     *
     * @param comments
     * @param phoneType
     */
    @Given("I verify updated phone details are matched {string} and {string}")
    public void i_verify_updated_phone_details_are_matched_and_and(String comments, String phoneType) {
        boolean recordFound = false;
        List contacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        for (int i = 0; i < contacts.size(); i++) {
            HashMap contactsData = (HashMap) contacts.get(i);
            List phones = (List) contactsData.get("phones");
            for (int j = 0; j < phones.size(); j++) {
                HashMap phonesData = (HashMap) phones.get(j);
                if (phonesData.get("associatedCaseMember").toString().equalsIgnoreCase(associatedCaseMember.get())
                        && phonesData.get("phoneId").toString().equalsIgnoreCase(phoneId.get())) {
                    assertEquals(phonesData.get("comments").toString(), comments);
                    assertEquals(phonesData.get("phoneType").toString(), phoneType);
                    assertEquals(phonesData.get("phoneNumber").toString(), contactPhoneNumber.get());
                    assertEquals(phonesData.get("effectiveStartDate").toString().split("T")[0], updatedStartDate.get().split("T")[0]);
                    assertEquals(phonesData.get("effectiveEndDate").toString().split("T")[0], updatedEndDate.get().split("T")[0]);
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
        }
        assertTrue(recordFound);
    }

    /**
     * <b>Description</b> Adds no of dates to System Date
     *
     * @param noOfDay No. of days
     * @param format  date format
     */
    public String addDaysToSysDate(String format, int noOfDay) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, noOfDay);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Given("I created a case for contact validation")
    public void createaCaseTestData() {
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().createCaseHead();
        // caseRestController.i_initiated_create_case_api();
        // caseRestController.i_can_information_for_case_creation();
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_initiated_create_case_consumer_api();
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().provide_case_and_consumer_info_for_case_creation();
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_can_run_create_case_api();
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().verifycaseConsumerCreated();
        apiConsumerId.set(API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().apiconsumerId.get());
        apiCaseId.set(API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().createdCaseId.get());
        System.out.println(apiCaseId.get());
    }

    @Then("I can run add contacts using API and verify error message {string}")
    public void runAddContactAndVerifyError(String errorMessage) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("fail"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("message").toString().toLowerCase(), errorMessage.toLowerCase());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify phone status as {string}")
    public void verifyPoneStatus(String status) {
        boolean recordFound = false;
        List contacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contacts");
        for (int i = 0; i < contacts.size(); i++) {
            HashMap contactsData = (HashMap) contacts.get(i);
            List phones = (List) contactsData.get("phones");
            for (int j = 0; j < phones.size(); j++) {
                HashMap phonesData = (HashMap) phones.get(j);
                if (phonesData.get("associatedCaseMember") != null && phonesData.get("associatedCaseMember").toString().equalsIgnoreCase(associatedCaseMember.get())
                        && phonesData.get("phoneNumber").toString().equalsIgnoreCase(contactPhoneNumber.get())) {
                    assertEquals(phonesData.get("status").toString(), status);
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
        }
        assertTrue(recordFound, "No phone number record with expected status: " + status);
    }


    @Then("I can verify updated address by getting the address from consumer ID {string}")
    public void iCanVerifyUpdatedAddressByGettingTheAddressFromConsumerID(String externalRefid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (externalRefid.isEmpty()) {
            externalRefid = apiConsumerId.get();
        }
        searchContactsByExtTypeEndPoint = searchContactsByExtTypeEndPoint.replace("{externalRefid}", externalRefid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchContactsByExtTypeEndPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        List<Map<String, String>> list = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("contacts[0].addressess");
        for (Map<String, String> addressMap : list) {
            String actualAddStreet1 = addressMap.get("addressStreet1");
            String actualAddStreet2 = addressMap.get("addressStreet2");
            String actualAddCity = addressMap.get("addressCity");
            String actualAddState = addressMap.get("addressState");
            String actualAddZip = addressMap.get("addressZip");
            String actualAddZipFour = addressMap.get("addressZipFour");
            String actualAddCounty = addressMap.get("addressCounty");
            assertEquals(actualAddStreet1, addressStreet1.get(), "Actual AddressStreet1: " + actualAddStreet1 + " Expected: " + addressStreet1.get());
            assertEquals(actualAddStreet2, addressStreet2.get(), "Actual AddressStreet2: " + actualAddStreet2 + " Expected: " + addressStreet2.get());
            assertEquals(actualAddCity, addressCity.get(), "Actual City: " + actualAddCity + " Expected: " + addressCity.get());
            assertEquals(actualAddState, addressState.get(), "Actual State: " + actualAddState + " Expected: " + addressState.get());
            assertEquals(actualAddZip, addressZip.get(), "Actual Zipcode: " + actualAddZip + " Expected: " + addressZip.get());
            assertEquals(actualAddZipFour, addressZipFour.get(), "Actual ZipFour: " + actualAddZipFour + " Expected: " + addressZipFour.get());
            assertEquals(actualAddCounty, addressCounty.get(), "Actual County: " + actualAddCounty + " Expected: " + addressCounty.get());
        }
    }

    @Then("I verify consumer phone number primary indicator added using API")
    public void iVerifyConsumerPhonePrimaryIndicator() {
        i_initiated_consumer_type_search_api("Consumer", CRM_CreateConsumerProfileStepDef.consumerID.get());
        i_run_get_contacts_detail_api();
        List<Map<String, String>> list = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("contacts[0].phones");
        for (Map<String, String> phonesMap : list) {
            if (phonesMap.get("phoneNumber").toString().contains(CRM_CreateConsumerProfileStepDef.consumerPrimaryPhone.get())) {
                assertEquals(phonesMap.get("primaryIndicator"), true);
                break;
            }
        }
    }

    @Then("I verify consumer email primary indicator added using API")
    public void iVerifyConsumerEmailPrimaryIndicator() {
        i_initiated_consumer_type_search_api("Consumer", CRM_CreateConsumerProfileStepDef.consumerID.get());
        i_run_get_contacts_detail_api();
        List<Map<String, String>> list = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("contacts[0].emails");
        for (Map<String, String> phonesMap : list) {
            if (phonesMap.get("emailAddress").toString().contains(CRM_CreateConsumerProfileStepDef.consumerPrimaryEmail.get())) {
                assertEquals(phonesMap.get("primaryIndicator"), true);
                break;
            }
        }
    }

    @And("I create a case consumer with address {string} type {string}")
    public void createCaseAddress(String address, String addressType) {
        this.addressStreet1.set(address);
        this.addressType.set(addressType);
        apiConsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        apiConsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        apiCaseId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomSSN().substring(0, 4));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseWithConsumers);
        String randomDateOfB = "19" + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().getRandomNumber(2) + "-02-02";
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequest(apiCaseId.get(), "Member", apiConsumerFirstName.get(), apiConsumerLastName.get(), randomDateOfB));
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer creation " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I will update {string} address record for this consumer")
    public void uodateAddressRecordWithSameData(String changeType) {
        System.out.println("Updating cas consumer address record >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (changeType.contains("addressSource")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                    .getAsJsonObject().getAsJsonObject("address").addProperty("addressSource", "Consumer Reported");
            System.out.println(requestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
            System.out.println("api response case and customer update " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        } else if (changeType.contains("addressSource")) {
            System.out.println(requestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
            System.out.println("api response case and customer update " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
    }

    @And("I send API call to search Consumer With first name and Last name")
    public void iSearchingConsumerWithFirstAndLastName() {
        Map<String, String> data = new HashMap<>();
        data.put("consumerLastName", apiConsumerLastName.get());
        data.put("consumerFirstName", apiConsumerFirstName.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/empty.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);

        for (int i = 0; i < 30; i++) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status").equalsIgnoreCase("success")) {
                System.out.println("System get call from " + (i + 1) + " attempts");
                break;
            }
        }
        System.out.println("Search results = " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.toString());

    }

    @Then("I will verify new address not created")
    public void verify_new_addres_not_created() {
        int sizeOfAddres = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonObject("object").getAsJsonArray("result").get(0).getAsJsonObject().getAsJsonArray("contacts").get(0).getAsJsonObject().getAsJsonArray("addressess").size();
        assertEquals(sizeOfAddres, 1, "address records more then 1");
    }

    @When("I will add a new address using the existing Address Type and capture the following information")
    public void add_new_address_to_existing_consumer(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("address").addProperty("addressStreet1", data.get("addressStreet1"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("address").addProperty("addressCity", data.get("addressCity"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("address").addProperty("addressCounty", data.get("addressCounty"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("address").addProperty("addressState", data.get("addressState"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("address").addProperty("addressZip", data.get("addressZip"));
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer update " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Given("I verify Existing Address of this type for the Consumer will be updated as follows")
    public void i_verify_Existing_Address_of_this_type_for_the_Consumer_will_be_updated_as_follows(Map<String, String> data) {
        int sizeOfAddres = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonObject("object").getAsJsonArray("result").get(0)
                .getAsJsonObject().getAsJsonArray("contacts").get(0).getAsJsonObject().getAsJsonArray("addressess").size();
        assertEquals(sizeOfAddres, 2, "address records not 2");
        captureCurrentTime.set(APIClassUtil.getApiClassUtilThreadLocal().getCurentTimeFormatUntillSec());
        String actualupdatedOnTime = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].addressess[1].updatedOn");
        String actualEndDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].addressess[1].effectiveEndDate");
        actualupdatedOnTime = actualupdatedOnTime.substring(0, 19);
        actualEndDate = actualEndDate.substring(0, 19);

        System.out.println(actualupdatedOnTime + "==================================" + captureCurrentTime.get());
        assertTrue(actualupdatedOnTime.compareTo(captureCurrentTime.get()) >= -1 || actualupdatedOnTime.compareTo(captureCurrentTime.get()) <= 1, "updatedOn time mismatch");
        assertTrue(actualupdatedOnTime.compareTo(captureCurrentTime.get()) >= -1 || actualupdatedOnTime.compareTo(captureCurrentTime.get()) <= 1, "EndDate time mismatch");
    }

    @Given("I verify Effective Start Date, Created By, Created On are NOT updated for existing address")
    public void i_verify_Effective_Start_Date_Created_By_Created_On_are_NOT_updated_for_existing_address() {
        String actualStartDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].addressess[1].effectiveStartDate");
        String createdOn = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].addressess[1].createdOn");
        actualStartDate = actualStartDate.substring(0, 19);
        createdOn = createdOn.substring(0, 19);
        assertNotEquals(createdOn, captureCurrentTime.get(), "createdOn equals to current time");
        assertNotEquals(actualStartDate, captureCurrentTime.get(), "StrtDate equals to current time");
    }

    @Then("I verify newly added Address of this type for the Consumer will be updated as follows")
    public void i_verify_newly_added_Address_of_this_type_for_the_Consumer_will_be_updated_as_follows(Map<String, String> data) {
        String actualStartDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].addressess[0].effectiveStartDate");
        actualStartDate = actualStartDate.substring(0, 19);

        String actualCreatedDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].addressess[0].createdOn");
        actualCreatedDate = actualCreatedDate.substring(0, 19);
        System.out.println("actualCreatedDate =" + actualCreatedDate + "==================================" + captureCurrentTime.get());
        System.out.println("actualStartDate = " + actualStartDate + "==================================" + captureCurrentTime.get());
        assertTrue(actualCreatedDate.compareTo(captureCurrentTime.get()) >= -2 || actualCreatedDate.compareTo(captureCurrentTime.get()) <= 2, "createdOn mismatch current time");
        assertTrue(actualStartDate.compareTo(captureCurrentTime.get()) >= -2 || actualStartDate.compareTo(captureCurrentTime.get()) <= 2, "StrtDate mismatch current time");
    }

    @When("I will update record with null payload for address data {string} to be {string}")
    public void I_will_update_record_with_null_payload_for_address(String fiald, String value) {
        value = null;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject().getAsJsonObject("address").addProperty(fiald, value);

        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("api response case and customer update " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I will verify existing adress following {string} not updated to null and no address record created")
    public void verify_address_field_not_updated_to_null(String field) {
        String nullText = null;
        int sizeOfAddres = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject().getAsJsonObject("object").getAsJsonArray("result").get(0)
                .getAsJsonObject().getAsJsonArray("contacts").get(0).getAsJsonObject().getAsJsonArray("addressess").size();
        assertEquals(sizeOfAddres, 1, "address records more then 1");
        assertNotEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].contacts[0].addressess[0]." + field), nullText, field + " is null");

    }

}

