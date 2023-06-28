    package com.maersk.crm.api_step_definitions;

    import com.github.javafaker.Faker;
    import com.google.common.collect.Ordering;
    import com.google.gson.*;
    import com.google.gson.internal.LinkedTreeMap;
    import com.maersk.crm.pages.crm.*;
    import com.maersk.crm.utilities.*;
    import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
    import com.maersk.dms.utilities.EventsUtilities;
    import io.cucumber.datatable.DataTable;
    import io.cucumber.java.en.And;
    import io.cucumber.java.en.Given;
    import io.cucumber.java.en.Then;
    import io.cucumber.java.en.When;
    import org.apache.commons.lang3.RandomStringUtils;
    import org.apache.commons.lang3.time.DurationFormatUtils;
    import org.openqa.selenium.By;
    import org.testng.Assert;

    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
    import java.time.LocalDate;
    import java.time.OffsetDateTime;
    import java.time.ZoneOffset;
    import java.time.format.DateTimeFormatter;
    import java.util.*;
    import java.util.concurrent.TimeUnit;

    import static com.maersk.crm.utilities.World.getWorld;
    import static org.testng.Assert.*;

//import java.util.jar.JarOutputStream;
//import static org.testng.Assert.assertFalse;
//import static org.testng.Assert.assertNotSame;
//import static sun.java2d.loops.CompositeType.General;

public class APIContactRecordController extends CRMUtilities implements ApiBase {
    public final ThreadLocal<Faker> faker = ThreadLocal.withInitial(() -> new Faker());
    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseWithConsumers = "app/crm/caseloader";
    private String baseURI = ConfigurationReader.getProperty("apiContactRecordURI");
    private String contactRecordSearchURI = ConfigurationReader.getProperty("contactRecordSearchURI");
    private String contactRecordEndPoint = "app/crm/contactrecord";
    private String medChatContactRecordEndPoint = "app/crm/external/contactrecord";
    private String contactRecordAdvancedSearch = "/app/crm/search/contactrecords";
    private String contactReasonEndPoint = "app/crm/contactrecord/contactreason";
    private String searchContactRecordsEndPoint = "app/crm/contactrecords";
    private String getContactRecordDetail = "app/crm/contactrecord/{contactId}";
    private String getCorrelationContactRecordDetail = "app/crm/correlation/contactrecord/{contactId}";
    private String contactRecordCustomersEndPoint = "app/crm/contactrecord/customers";
    private String getContactRecordCommentsByContactIdEndPOint = "app/crm/contactrecord/comments/{contactId}";
    private String contactRecordCommentEndPoint = "app/crm/contactrecord/comment";
    private String contactRecordReasonEndPoint = "app/crm/contactrecord/contactreason";
    private String contactRecordCorrelationEndPoint = "app/crm/correlation/contactrecord/{correlationId}";
    private String linkEndPoint = "mars/crm/link";
    public final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> ConsumerTypes = ThreadLocal.withInitial(() -> "ConsumerType,Member,Non-Member,Authorized Representative");
    private final ThreadLocal<String> contactRecordOptions = ThreadLocal.withInitial(() -> "Inbound,Member,Outbound");
    private final ThreadLocal<String> consumerLanguageCodes = ThreadLocal.withInitial(() -> "English,Spanish");
    private String getContactRecordEditHistory = "app/crm/contactrecordedithistory/{contactId}";
    private String IVRcontactRecord = "app/crm/ivr/contactrecord";
    private String communityOutreachURI = ConfigurationReader.getProperty("apiCommunityOutreachURI");
    private String createCommunityOutreach = "/save";
    private String searchCommunityOutreach = "search?page=0&size=1";
    private String marsContactRecordBase = ConfigurationReader.getProperty("apiMarContactRecord");
    private String taskManagementURI = ConfigurationReader.getProperty("apiTaskManagementURI");
    private String marsEventURI = ConfigurationReader.getProperty("apiMarsEvent");
    private String marsContactRecordendpoint = "/app/crm/ivr/calldata";
    private String externalEndpoint = "/app/crm/cases/external";
    private String appCasememberEndpoint = "/app/crm/casemember/";
    private String marsCrmLinkEndpoint = "/mars/crm/link";
    private String marsEventEndpoint = "?size=10&page=0&sort=eventId,desc";
    private String createDisplayField = "app/crm/contactrecord/contactrecordDisplayFields";
    private String facilityNameEndpoint = "app/crm/lookup?tableName=ENUM_FACILITY_NAME";
    private String baseURIfacilityName = "https://max-nonprod-qa-dev.apigee.pcf-maersk.com/mars-cp-web/mars-task-management-api/";
    BrowserUtils browserUtils = new BrowserUtils();

    private final ThreadLocal<String> apiconsumerFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiconsumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiconsumerCorrelationId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Integer> apicontactRecordId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<String> apiconsumerCreatedOn = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiconsumerUiid = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiconsumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastContactRecordId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> contactRecordId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<Long> recordStartTime = ThreadLocal.withInitial(() -> null);
    public final ThreadLocal<String> consumerParams = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<JsonObject> consumerObj = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> contactRecordReasonId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactRecordActionId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Long> randomWrapup = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> wrapUpTime = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<Integer> apiCRId = ThreadLocal.withInitial(() -> 0);
    public final ThreadLocal<Integer> IVRcontactRecordID = ThreadLocal.withInitial(() -> 0);
    public final ThreadLocal<String> ivr_phone_number = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> ivr_consumer_name = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> ivr_consumer_Fname = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> ivr_consumer_Lname = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> ivr_consumer_DOB = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> ivr_consumer_SSN = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> ivr_consumer_ExternalID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Integer> caseID = ThreadLocal.withInitial(() -> 0);
    public static final ThreadLocal<Integer> apicommunityOutreachId = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<String> interactionId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> appId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Map<String, String>> jsonData = ThreadLocal.withInitial(() -> new HashMap<>());
    private final ThreadLocal<String> apiIvrContactId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiIvrcaseIdfromGet = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> currentTimeStamp = ThreadLocal.withInitial(() -> browserUtils.getCurrentDateInYearFormat() + "T00:00:00.274000+00:00");

    final ThreadLocal<String> apiSearchConsumerFirstName = ThreadLocal.withInitial(() -> "");
    final ThreadLocal<String> apiSearchConsumerLastName = ThreadLocal.withInitial(() -> "");
    final ThreadLocal<String> apiSearchConsumerSSN = ThreadLocal.withInitial(() -> "");

   /* private String contactChannelType;
    private String contactChannelValue;
    private String contactRecordType;
    private String consumerType;*/

    private final ThreadLocal<String> contactPhoneNumber = ThreadLocal.withInitial(() -> "");
    private final ThreadLocal<String> surveyPhoneNumber = ThreadLocal.withInitial(() -> "");
    private final ThreadLocal<String> contactEmailAddress = ThreadLocal.withInitial(() -> "");
    private final ThreadLocal<List> inboundCallQueueList = ThreadLocal.withInitial(() -> Arrays.asList("Eligibility", "Enrollment", "General Program Questions"));
    private final ThreadLocal<List> contactCallCampaignList = ThreadLocal.withInitial(() -> Arrays.asList("Enrollment Reminder", "Payment Reminder", "Program Information"));
    private final ThreadLocal<List> outcomeOfContactList = ThreadLocal.withInitial(() -> Arrays.asList("Did Not Reach/Left Voicemail", "Did Not Reach/No Voicemail", "Invalid Phone Number", "Reached Successfully"));
    private final ThreadLocal<String> contactCallCampaign = ThreadLocal.withInitial(() -> "");
    private final ThreadLocal<String> inboundCallQueue = ThreadLocal.withInitial(() -> "");
    private final ThreadLocal<String> outcomeOfContact = ThreadLocal.withInitial(() -> "");
    private final ThreadLocal<String> organizationName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactTypesValue = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactRecordTypesValue = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactRecordStatusTypeValue = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<HashMap<String, String>> previousValuesToCompareEditedValues = ThreadLocal.withInitial(() -> new HashMap<String, String>());

    private String caseConsumerAPI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String createCaseWithCaseCheck = "app/crm/cases";
    private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private final ThreadLocal<JsonObject> requestParamsCaseCheck = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> apiCaseId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiTaskId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> requestParamsWithoutConsumer = ThreadLocal.withInitial(JsonObject::new);
    final ThreadLocal<Gson> gson = ThreadLocal.withInitial(() -> new GsonBuilder().create());
    public final ThreadLocal<JsonObject> callDataPayload = ThreadLocal.withInitial(() -> getRequestbodyFromTestData("crm/contacts/ivrCallData.json"));
    public final ThreadLocal<JsonObject> linkDataPayload = ThreadLocal.withInitial(() -> getRequestbodyFromTestData("crm/contacts/ivrCrmLink.json"));
    private final ThreadLocal<String> uiid = ThreadLocal.withInitial(String::new);

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRM_Edit_ContactRecord_Page edit_cont_Rec = new CRM_Edit_ContactRecord_Page();
    CRM_CORE_IVR_ContactRecordPage ivrContactRecordPage = new CRM_CORE_IVR_ContactRecordPage();
    CRMEditConsumerProfilePage consumerProfilePage = new CRMEditConsumerProfilePage();
    CRM_CORE_CommunityOutreachPage communityOutreach = new CRM_CORE_CommunityOutreachPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();

    public static final ThreadLocal<String> oneLoginJWT_token = ThreadLocal.withInitial(() -> "");

    CRMAddAuthorizedRepresentativePage crmAddAuthorizedRepresentativePage = new CRMAddAuthorizedRepresentativePage();
    /*public APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private APIConsumerRestController consumerController = new APIConsumerRestController();
    private APITMEventController  apitmEventController = new APITMEventController();
    private APICaseRestController caseController = new APICaseRestController();
    private APITaskManagementController taskManagementController = new APITaskManagementController();
    private APICaseLoaderEligibilityEnrollmentController caseLoaderRequest = new APICaseLoaderEligibilityEnrollmentController();*/


    /**
     * Initiate the Create Contact Records API Call
     */
    @Given("I initiated Create Contact Records API")
    public void i_initiated_create_contact_record_api() {
        api.setbaseUri(baseURI);
        api.setEndPoint(contactRecordEndPoint);
    }

    /**
     * Initiate the Create Contact Records Customers API Call
     */
    @Given("I initiated Contact Records Customers API")
    public void i_initiated_contact_record_customers_api() {
        api.setbaseUri(baseURI);
        api.setEndPoint(contactRecordCustomersEndPoint);
    }

    /**
     * Initiate add Contact Reason API Call
     */
    @Given("I initiated contact reason addition")
    public void i_initiated_contact_reason_addition() {
        api.setbaseUri(baseURI);
        api.setEndPoint(contactReasonEndPoint);
    }

    /**
     * Initiate specific contact search API call
     *
     * @param contactId
     */
    @And("I initiated specific contact Search {string}")
    public void i_initiated_specific_contact_search(String contactId) {
        api.setbaseUri(baseURI);
        String getContactRecordDtl;
        if (contactId.isEmpty() || contactId.equals("")) {
            getContactRecordDtl = getContactRecordDetail.replace("{contactId}", contactRecordId.toString());
        } else {
            getContactRecordDtl = getContactRecordDetail.replace("{contactId}", contactId);
        }
        api.setEndPoint(getContactRecordDtl);
    }

    /**
     * Initiate specific contact record comments API Call
     *
     * @param contactId
     */
    @And("I initiated specific contact Record Comments {string} using API")
    public void i_initiated_specific_contact_record_comments_api(String contactId) {
        api.setbaseUri(baseURI);
        String getContactRecordCmnt;
        if (contactId.isEmpty() || contactId.equals("")) {
            getContactRecordCmnt = getContactRecordCommentsByContactIdEndPOint.replace("{contactId}", contactRecordId.toString());
        } else {
            getContactRecordCmnt = getContactRecordCommentsByContactIdEndPOint.replace("{contactId}", contactId);
        }
        api.setEndPoint(getContactRecordCmnt);
    }

    /**
     * Initiate Correlation Contact Search API Call
     *
     * @param contactId
     */
    @And("I initiated correlation contact Search {string}")
    public void i_initiated_correlation_contact_search(String contactId) {
        api.setbaseUri(baseURI);
        String getCorrelationContactRecordDtl;
        if (contactId.isEmpty() || contactId.equals("")) {
            getCorrelationContactRecordDtl = getCorrelationContactRecordDetail.replace("{contactId}", lastContactRecordId.get());
        } else {
            getCorrelationContactRecordDtl = getCorrelationContactRecordDetail.replace("{contactId}", contactId);
        }
        api.setEndPoint(getCorrelationContactRecordDtl);
    }

    /**
     * Initiate Consumer search API for contact
     */
    @Given("I initiated consumer search API for Contact")
    public void i_initiated_consumer_search_api_for_contact() {
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_initiated_search_case_api();
    }

    /**
     * User can search consumer for contact by "item" and it's "value" after initiate API search
     *
     * @param item
     * @param value
     */
    @When("I can search consumer for contact by {string} with value {string}")
    public void i_can_search_consumer_for_contact(String item, String value) {
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_can_search_case_by(item, value);
    }

    /**
     * User can perform consumer search via API for contact
     *
     * @param item
     * @param value
     */
    @And("I run the consumer search API for contact")
    public void i_run_the_consumer_search_api_for_contact(String item, String value) {
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_run_the_case_search_api();
    }

    /**
     * User can get correlation id from consumer search basef on "consumerFirstName" and "consumerLastName"
     *
     * @param consumerFirstName
     * @param consumerLastName
     */
    @When("I get correlationId id from consumer search {string} and {string}")
    public void i_get_correlation_id_from_consumer_search(String consumerFirstName, String consumerLastName) {
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        if (!consumerFirstName.equalsIgnoreCase("{random}") && !consumerLastName.equalsIgnoreCase("{random}"))
            apiconsumerId.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().get_the_consumer_id(consumerFirstName, consumerLastName));
        i_initiated_create_contact_record_api();
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("contactRecordType", "Inbound");
        requestParams.get().addProperty("createdOn", "");
        requestParams.get().addProperty("createdBy", "");
        requestParams.get().addProperty("correlationId", apiconsumerCorrelationId.get());
        requestParams.get().addProperty("uiid", apiconsumerUiid.get());
        requestParams.get().addProperty("contactChannelType", "phone");
        System.out.println(requestParams.get());
        i_can_run_create_contact_records_api();
        /*i_initiated_contact_recored_by_correlation(apiconsumerCorrelationId);
        i_run_get_contact_records_api();*/
        ArrayList<String> contactRecord_Ids = api.jsonPathEvaluator.get("contactRecords.contactRecordId");
        System.out.println(contactRecord_Ids);
        Iterator cRecordId = contactRecord_Ids.iterator();
        Object contactRecordIdObj = cRecordId.next();
        contactRecordId.set((int) contactRecordIdObj);
        lastContactRecordId.set(contactRecordIdObj.toString());
        System.out.println(contactRecordId.get());
        i_initiated_create_contact_record_api();
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("consumerAuthenticatedInd", true);
        requestParams.get().addProperty("contactRecordId", contactRecordId.get());
        requestParams.get().addProperty("linkRefId", apiconsumerId.get());
        requestParams.get().addProperty("linkRefType", "consumer");
        System.out.println(requestParams.get());
        i_can_run_create_contact_records_api();
    }

    /**
     * User can add a new reason "option" with list of "actions" using API
     *
     * @param option
     * @param actions
     */
    @When("I add new reason {string} for Contact Record Id {string} with actions using API")
    public void i_add_new_reason_with_actions_api(String option, String specificContactRecordId, List<String> actions) {
        // apitdu.getJsonFromFile("crm/contactrecords/apiAddContactRecord.json");
        // apitdu.getJsonFromFile("crm/contactrecords/ReasonNew.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/MultipleReason.json");
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("createdOn", offsetTime);

        if (!specificContactRecordId.isEmpty()) {
            //apitdu.jsonElement.getAsJsonObject().addProperty("contactRecordId", specificContactRecordId);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("contactRecordId", specificContactRecordId);
        } else {
            //apitdu.jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("contactRecordId", Integer.valueOf(contactRecordId.get()));
        }
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("contactRecordReasonType", option);
        for (int i = 0; i < actions.size(); i++) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().get("contactRecordActions")
                    .getAsJsonArray().get(0).getAsJsonObject().addProperty("contactRecordActionType", actions.get(0));

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject()
                    .get("contactRecordActions").getAsJsonArray().get(0).getAsJsonObject()
                    .addProperty("contactRecordActionId", "");
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("notes", "API Contact Reason from Automation");
        // apitdu.jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("contactRecordReasonId", "");

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    /**
     * User can initiate a contact record search via API
     */
    @Given("I initiated search contact records API")
    public void i_initiated_search_contact_records_api() {
        api.setbaseUri(baseURI);
        api.setEndPoint(searchContactRecordsEndPoint);
    }

    /**
     * Make contact Records search via API by Request Parameter, item and value
     *
     * @param requestParams
     * @param item
     * @param value
     */
    private void search_contact_records_by(JsonObject requestParams, String item, String value) {
        switch (item) {
            case "consumerFirstName":
                if (value.equalsIgnoreCase("{reandom}"))
                    value = API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get();
                requestParams.addProperty("consumerFirstName", value);
                break;
            case "consumerLastName":
                if (value.equalsIgnoreCase("{reandom}"))
                    value = API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerLastName.get();
                requestParams.addProperty("consumerLastName", value);
                break;
            case "updatedOn":
                requestParams.addProperty("updatedOn", value);
                break;
        }
    }

    /**
     * USer can search contact record by item and it's value
     *
     * @param item
     * @param value
     */
    @When("I can search Contact Record by {string} with value {string}")
    public void i_can_search_contact_record_by(String item, String value) {
        requestParams.set(new JsonObject());
        search_contact_records_by(requestParams.get(), item, value);
        System.out.println(requestParams.get());
    }

    /**
     * User can search contact records for link by contact record Id
     */
    @And("I can search contact records for link by contactRecordId")
    public void i_can_search_contact_records_for_link_by_contact_record_id() {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("contactRecordId", contactRecordId.get());
        System.out.println(requestParams.get());
    }

    /**
     * User can set a link of consumer ID as reference Id
     */
    @And("I can set link consumer Id as reference Id")
    public void i_can_set_link_reference_id_as_contact_record_id() {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("linkRefType", "Consumer");
        requestParams.get().addProperty("linkRefId", apiconsumerId.get());
        System.out.println(requestParams.get());
    }

    /**
     * User can provide a correlation id.
     */
    @When("I can provide correlation id")
    public void i_can_provide_correlation_id() {
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        requestParams.get().addProperty("correlationId", apiconsumerCorrelationId.get());
        requestParams.get().addProperty("uiid", apiconsumerUiid.get());
//        requestParams.getAsJsonArray("contactRecordReasons").get(0).getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId);
        System.out.println(requestParams.get());
    }

    /**
     * Generate contact records information with consumer first and last name
     *
     * @param consumerFirstName
     * @param consumerLastName
     */
    public void generate_contact_records_information_with(String consumerFirstName, String consumerLastName) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiCreateContactRecords.json");
        long currentDateTime = System.currentTimeMillis();
        //apitdu.jsonElement.getAsJsonObject().addProperty("contactRecordActionTimeStamp", currentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", currentDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", currentDateTime);

        /*if (consumerFirstName.isEmpty() || consumerFirstName.equals("{random}")){
            apitdu.getRandomString(10);
            apiconsumerFirstName = apitdu.randomString;
            apitdu.jsonElement.getAsJsonObject().addProperty("consumerFirstName", apitdu.randomString);
        } else {
            apitdu.jsonElement.getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        }

        if (consumerLastName.isEmpty() || consumerLastName.equals("{random}")){
            apitdu.getRandomString(10);
            apiconsumerLastName = apitdu.randomString;
            apitdu.jsonElement.getAsJsonObject().addProperty("consumerLastName", apitdu.randomString);
        } else {
            apitdu.jsonElement.getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        }*/
    }

    /**
     * User can provide contact records information with consumer first and last name.
     *
     * @param consumerFirstName
     * @param consumerLastName
     */
    @When("I can provide contact records information with {string} and {string}")
    public void i_can_provide_contact_records_information_with(String consumerFirstName, String consumerLastName) {
        generate_contact_records_information_with(consumerFirstName, consumerLastName);
        // Adding Consumer Type randomly
        /*apitdu.getARandomList(ConsumerTypes, true, "");
        apitdu.jsonElement.getAsJsonObject().addProperty("consumerType", apitdu.randomListValue);*/
        // Adding Contact Records Option randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList(contactRecordOptions.get(), true, "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomListValue);
        // Adding Consumer Language Code Option randomly
        /*apitdu.getARandomList(consumerLanguageCodes, true, "");
        apitdu.jsonElement.getAsJsonObject().addProperty("consumerLanguageCode", apitdu.randomListValue);*/
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @When("I can provide contact records with {string}, {string} and {string}")
    public void i_can_provide_contact_records_information_with_consumer_type(String consumerFirstName, String consumerLastName, String consumerType) {
        generate_contact_records_information_with(consumerFirstName, consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", consumerType);
        // Adding Contact Records Option randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList(contactRecordOptions.get(), true, "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomListValue);
        // Adding Consumer Language Code Option randomly
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getARandomList(consumerLanguageCodes.get(), true, "");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLanguageCode", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomListValue);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @Given("I created a contact record using API")
    public void i_created_a_contact_record_using_api() {
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        i_initiated_create_contact_record_api();
        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("correlationId", apiconsumerCorrelationId.get());
        requestParams.get().addProperty("uiid", apiconsumerUiid.get());
        requestParams.get().addProperty("contactRecordType", "Inbound");
        requestParams.get().addProperty("createdBy", "");
        requestParams.get().addProperty("contactChannelType", "phone");
        requestParams.get().addProperty("createdOn", "");
        System.out.println(requestParams.get());
        i_can_run_create_contact_records_api();
        i_initiated_contact_recored_by_correlation(apiconsumerCorrelationId.get());
        i_run_get_contact_records_api();
        ArrayList<String> contactRecord_Ids = api.jsonPathEvaluator.get("contactRecords.contactRecordId");
        System.out.println(contactRecord_Ids);
        Iterator cRecordId = contactRecord_Ids.iterator();
        Object contactRecordIdObj = cRecordId.next();
        contactRecordId.set((int) contactRecordIdObj);
        System.out.println(contactRecordId.get());
    }

    @When("I can provide contact records as anonymous {string}, {string}, {string} and {string}")
    public void i_can_provide_contact_record_as_anonymous(String consumerLanguageCode, String inboundCallQueue, String contactChannelType, String contactChannelValue) {
        //i_created_a_contact_record_using_api();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiCreateUnidentifiedContactRecords.json");
        //long currentDateTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);
        //currentDateTime = System.currentTimeMillis();
        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", "Inbound");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", "Anonymous");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactType", "Unidentified Contact");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("preferredLanguageCode", consumerLanguageCode);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("inboundCallQueue", inboundCallQueue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", contactChannelType);
        if (contactChannelType == "Phone") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
        } else if (contactChannelType == "SMS Text") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
        } else if (contactChannelType == "Email") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerEmail", contactChannelValue);
        } else if (contactChannelType == "Web Chat") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerEmail", contactChannelValue);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @And("I can provide contact records information for link")
    public void i_can_provide_contact_records_info_for_link() {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("consumerAuthenticatedInd", true);
        requestParams.get().addProperty("contactRecordId", contactRecordId.get());
        requestParams.get().addProperty("linkRefId", Integer.valueOf(apiconsumerId.get()));
        requestParams.get().addProperty("linkRefType", "consumer");
        System.out.println(requestParams.get());
    }

    @And("I can provide contact records information for unlink")
    public void i_can_provide_contact_records_info_for_unlink() {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("consumerAuthenticatedInd", true);
        requestParams.get().addProperty("contactRecordId", lastContactRecordId.get());
        requestParams.get().addProperty("linkRefId", "");
        requestParams.get().addProperty("linkRefType", "");
        System.out.println(requestParams.get());
    }

    @And("I can close contact records information")
    public void i_can_close_contact_records_info_for_link() {
        JsonParser jsonParser = new JsonParser();
        requestParams.set((JsonObject) jsonParser.parse(consumerParams.get()));
        requestParams.get().addProperty("contactRecordStatusType", "Dropped");
        System.out.println(requestParams.get());
    }

    @And("I run the post contact records API with Query parameters with {string},{string} and {string}")
    public void i_run_the_post_contact_records_api_with_query_parameters(String page, String size, String sort) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!page.isEmpty()) {
            map.put("page", page);
        }
        if (!size.isEmpty()) {
            map.put("size", size);
        }
        if (!sort.isEmpty()) {
            map.put("sort", sort);
        }
        System.out.println(map);
        api.PostAPIWithParameterAndQuery(requestParams.get(), map);
        System.out.println("API Response:");
        System.out.println(api.responseString);
        consumerParams.set(api.responseString);
    }

    @And("I run the search contact records API")
    public void i_run_the_search_contact_records_api() {
        api.PostAPIWithParameter(requestParams.get());
        System.out.println("API Response:");
        System.out.println(api.responseString);
        consumerParams.set(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
        System.out.println(api.responseString);
        consumerParams.set(api.responseString);
    }

    @And("I run the search blank contact records API")
    public void i_run_the_search_blank_contact_records_api() {
        try {
            api.PostAPIWithParameter(requestParams.get());
            assertEquals(api.statusCode, 500);
        } catch (Exception e) {
            System.out.println("As a user we cannot search a contact record for a blank case or consumer.");
        }
    }

    @And("I run add contact record reason using API")
    public void i_run_add_contact_record_reason_using_api() {
        api.PostAPIWithParameter(requestParams.get());
        System.out.println("API Response:");
        System.out.println(api.responseString);
        consumerParams.set(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @And("I run Get contact records API")
    public void i_run_get_contact_records_api() {
        api.getAPI();
        System.out.println("API Response:");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
        System.out.println(api.responseString);
    }

    @And("I run Get contact records comments using API")
    public void i_run_get_contact_record_comments_api() {
        api.getAPI();
        System.out.println("API Response:");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @And("I can run create contact records API")
    public int i_can_run_create_contact_records_api() {
        api.PutAPI(requestParams.get());
        System.out.println("Aswath API Response:");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        System.out.println("1");
        assertTrue(api.responseString.contains("success"));
        System.out.println("2");
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
        ArrayList<String> apiConsumerCorrelationIdsList = api.jsonPathEvaluator.get("contactRecords.correlationId");
        apiconsumerCorrelationId.set(apiConsumerCorrelationIdsList.get(0));
        System.out.println("id " + api.jsonPathEvaluator.get("contactRecords.contactRecordId"));
        ArrayList<Integer> apiContactRecordIdsList = api.jsonPathEvaluator.get("contactRecords.contactRecordId");
        System.out.println("apiContactRecordIdsList " + apicontactRecordId.get());
        apicontactRecordId.set(apiContactRecordIdsList.get(0));
        ArrayList<String> apiConsumerCreatedOnList = api.jsonPathEvaluator.get("contactRecords.createdOn");

        System.out.println("apicontactRecordId: " + apicontactRecordId.get());

        System.out.println("apiConsumerCorrelationId " + apiconsumerCorrelationId.get());
        return apicontactRecordId.get();

    }

    @Given("I initiated contact record by correlationId from Response")
    public void i_initiated_contact_record_by_correlationId_from_Response() {
        System.out.println("correlation id " + apiconsumerCorrelationId.get());
        api.setbaseUri(baseURI);
        String getCorrelationContactRecordDtl;
        getCorrelationContactRecordDtl = contactRecordCorrelationEndPoint.replace("{correlationId}", apiconsumerCorrelationId.get());
        api.setEndPoint(getCorrelationContactRecordDtl);
    }

    @And("I can run update contact records API")
    public void i_can_run_update_contact_records_api() {
        api.PutAPI(requestParams.get());
        System.out.println("API Response:");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
        System.out.println(api.responseString);
    }

    @And("I get the last contactRecordId from API response")
    public void get_last_contact_record_id_from_response() {
        Object contactRecordIdObj = api.jsonPathEvaluator.get("contactrecords.totalElements");
        System.out.println(contactRecordIdObj);
        lastContactRecordId.set(contactRecordIdObj.toString());
        System.out.println("Last Contact Record ID: " + lastContactRecordId.get());
        contactRecordId.set(Integer.valueOf(lastContactRecordId.get()) + 3);
        System.out.println("Next Contact Record ID: " + contactRecordId.get());
    }

    @Then("I can verify contact records search response is {string}")
    public void verify_contact_records_search_response_is(String success) {
        if (Boolean.valueOf(success) == Boolean.TRUE) {
            assertEquals(api.jsonPathEvaluator.get("status"), "success");
        } else {
            assertEquals(api.jsonPathEvaluator.get("status"), "fail");
        }
    }

    @Then("I can verify consumer contact by consumerLastName with value {string} on API response")
    public void i_can_verify_the_consumer_contact_by_last_name_search_api(String value) {
        if (value.equals("{random}")) {
            value = apiconsumerLastName.get();
        }
        System.out.println("consumerLastName : " + value);
        System.out.println("Initiating contact Records Search API...");
        i_initiated_search_contact_records_api();
        System.out.println("Consumer Search API informaiton loading...");
        i_can_search_contact_record_by("consumerLastName", value);
        System.out.println("Consumer Contact Search API running...");
        i_run_the_search_contact_records_api();
        verify_contact_records_search_response_is("True");
    }

    @When("I get contact detail from consumer first name search {string}")
    public void i_get_contact_detail_from_consumer_search(String consumerFirstName) {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().get_the_consumer_correlationId_id_from_response(consumerFirstName);
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().correlationId.get());
        System.out.println("Consumer Correlation ID : " + apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().get_the_consumer_id_from_response(consumerFirstName);
        apiconsumerId.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerId.get());
        System.out.println("Consumer Consumer ID : " + apiconsumerId.get());
        consumerParams.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerDetail.get());
        i_initiated_correlation_contact_search(apiconsumerCorrelationId.get());
        i_run_get_contact_records_api();
        i_initiated_search_contact_records_api();
        i_can_search_contact_record_by("consumerFirstName", "");
        i_run_the_search_contact_records_api();
        get_last_contact_record_id_from_response();
    }

    @Then("I can verify contact record Size is less than or equal of {string}")
    public void i_can_verify_contact_record(String size) {
        String contactRecordTotal = api.jsonPathEvaluator.get("contactrecords.numberOfElements").toString();
        System.out.println(contactRecordTotal);
        assertTrue(Integer.parseInt(contactRecordTotal) <= Integer.parseInt(size));
    }

    @Then("I can verify contact record preferred language equal to {string}")
    public void i_can_verify_contact_preferred_language(String language) {
        String contactRecordTotal = api.jsonPathEvaluator.get("contactrecords.numberOfElements").toString();
        System.out.println(contactRecordTotal);
        ArrayList<List> contactRecord_preferred_languages = api.jsonPathEvaluator.get("contactrecords.content.consumer.preferredLanguage");
        System.out.println(contactRecord_preferred_languages);
        Iterator iter = contactRecord_preferred_languages.iterator();
        String contactRecord_preferred_language = iter.next().toString();
        System.out.println(contactRecord_preferred_language);
        assertTrue(contactRecord_preferred_language.contains(language), "Actual<>Expected : '" + contactRecord_preferred_language + "' <> '" + language + "'");
    }

    @Then("I can verify contact record ID are descending")
    public void i_can_verify_contact_record_id_descding() {
        ArrayList<Integer> contactRecordIds = api.jsonPathEvaluator.get("contactrecords.content.contactRecordId");
        System.out.println(contactRecordIds);
        assertTrue(Ordering.natural().reverse().isOrdered(contactRecordIds));
    }

    @Then("I verify all selected actions found on API response by {string}")
    public void i_verify_all_select_actions_found_on_api(String contactId) {
        if (contactId.isEmpty())
            contactId = contactRecordId.get() + "";
        i_initiated_specific_contact_record_comments_api(contactId);
        i_run_get_contact_record_comments_api();
        ArrayList<List> contactRecordActionTypes = api.jsonPathEvaluator.get("contactRecordReasonList.contactRecordActions.contactRecordActionType");
        System.out.println(contactRecordActionTypes.get(0));
        System.out.println("contactRecordActionTypes " + contactRecordActionTypes);
        assertTrue(contactRecordActionTypes.get(0).get(0).toString().contains("Provided"), "'Provided Appeal Information' is not found.");
        assertTrue(contactRecordActionTypes.get(0).get(1).toString().contains("Provided"), "'Provided Case Status/Information' is not found.");
        assertTrue(contactRecordActionTypes.get(0).get(2).toString().contains("Provided"), "'Provided Eligibility Status/Information' is not found.");
        assertTrue(contactRecordActionTypes.get(0).get(3).toString().contains("Provided"), "'Provided Enrollment Status/Information' is not found.");
        assertTrue(contactRecordActionTypes.get(0).get(4).toString().contains("Provided"), "'Provided Financial Information' is not found.");
        assertTrue(contactRecordActionTypes.get(0).get(5).toString().contains("Provided"), "'Provided Program Information' is not found.");
    }

    @And("The valid consumer has contact record containing the following values:")
    public void createContactRecordUsingCorrelationIDStep(Map<String, String> dataTable) {
        api.setbaseUri(baseURI);
        api.setEndPoint(contactRecordEndPoint);
        generateContactRecordFromProvidedData(dataTable);
        System.out.println(requestParams.get());
        recordStartTime.set(System.currentTimeMillis());
        long endDateTime = recordStartTime.get() + TimeUnit.MINUTES.toMillis(20);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", recordStartTime.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", endDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", endDateTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("callDuration", endDateTime - recordStartTime.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", getWorld().contactBean.get().getCorrelationID());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", "consumer");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", Integer.valueOf(getWorld().contactBean.get().getConsumerID()));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", getWorld().contactBean.get().getUniqueID());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", getWorld().contactBean.get().getPhoneNumber());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", 9474);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
        api.PutAPI(requestParams.get());
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
        //getWorld().contactRecordBean.get().setContactRecordId(getContactRecordIDWithCorrelationIDFromAPI(getWorld().contactBean.get().getCorrelationID()).get(0));
    }

//    public List<Integer> getContactRecordIDWithCorrelationIDFromAPI(String correlationID) {
//        api.setbaseUri(baseURI);
//        api.setEndPoint(searchContactRecordsEndPoint);
//        apitdu.getJsonFromFile("crm/contactrecords/Blank.json");
//        apitdu.jsonElement.getAsJsonObject().addProperty("correlationId", correlationID);
//        requestParams = apitdu.jsonElement.getAsJsonObject();
//        api.PostAPIWithParameter(requestParams);
//        assertEquals(api.statusCode, 200);
//        return api.jsonPathEvaluator.getList("contactrecords.content.contactRecordId");
//    }

    @And("The contact record has a reason with the following values:")
    public void createContactReasonFromDataTable(Map<String, String> dataTable) {
        api.setbaseUri(baseURI);
        api.setEndPoint(contactRecordReasonEndPoint);
        generateContactReasonJsonFromDataTable(dataTable);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        requestParams.get().addProperty("contactRecordId", Integer.valueOf(getWorld().contactBean.get().getConsumerID()));
        System.out.println(requestParams.get());
        api.PostAPIWithParameter(requestParams.get());
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify the Inbound Contact Record has correct values that were previously created")
    public void verifyInbounContactRecordValuesStep() {
        verifyInboundContactRecordValues();
    }

    @Then("I verify the Outbound Contact Record has right values that were previously created")
    public void verifyOutboundContactRecordValuesStep() {
        verifyOutboundContactRecordValues();
    }

    @Then("I verify the Contact Record Reason has correct values that were previously sent")
    public void verifyContactRecordReasonValuesStep() {
        verifyContactReasonValues();
    }

    public void verifyInboundContactRecordValues() {
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.contactRecordType").get(0), getWorld().contactRecordBean.get().getContactType());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.inboundCallQueue").get(0), getWorld().contactRecordBean.get().getInboundCallQueue());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.contactChannelType").get(0), getWorld().contactRecordBean.get().getContactChannel());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.consumerPhone").get(0), getWorld().contactBean.get().getPhoneNumber());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.contactRecordStatusType").get(0), getWorld().contactRecordBean.get().getContactDispositions());
    }

    public void verifyOutboundContactRecordValues() {
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.contactRecordType").get(0), getWorld().contactRecordBean.get().getContactType());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.contactCampaignType").get(0), getWorld().contactRecordBean.get().getContactCampaignType());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.contactChannelType").get(0), getWorld().contactRecordBean.get().getContactChannel());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.consumerPhone").get(0), getWorld().contactBean.get().getPhoneNumber());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.contactRecordStatusType").get(0), getWorld().contactRecordBean.get().getContactDispositions());
        assertEquals(api.responseBody.jsonPath().getList("contactRecords.contactOutcome").get(0), getWorld().contactRecordBean.get().getContactOutcome());
    }

    public void verifyContactReasonValues() {
        List<List> list = (List<List>) api.responseBody.jsonPath().getList("contactRecordReasonList.contactRecordActions.contactRecordActionType").get(0);
        assertEquals(list.get(0), getWorld().contactRecordBean.get().getReasonsList().get(0).getContactAction());
        assertEquals(api.responseBody.jsonPath().getList("contactRecordReasonList.contactRecordReasonType").get(0), getWorld().contactRecordBean.get().getReasonsList().get(0).getContactReason());
        assertEquals(api.responseBody.jsonPath().getList("contactRecordReasonList.notes").get(0), getWorld().contactRecordBean.get().getReasonsList().get(0).getReasonComments());
    }

    public void generateContactRecordFromProvidedData(Map<String, String> dataTable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/Blank.json");
        for (String data : dataTable.keySet()) {
            switch (data) {
                default:
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(data, dataTable.get(data));
            }
        }
        saveContactRecordFromDataTable(dataTable);
    }

    public void saveContactRecordFromDataTable(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "contactRecordType":
                    getWorld().contactRecordBean.get().setContactType(dataTable.get(data));
                    break;
                case "contactRecordStatusType":
                    getWorld().contactRecordBean.get().setContactDispositions(dataTable.get(data));
                    break;
                case "contactCampaignType":
                    getWorld().contactRecordBean.get().setContactCampaignType(dataTable.get(data));
                    break;
                case "inboundCallQueue":
                    getWorld().contactRecordBean.get().setInboundCallQueue(dataTable.get(data));
                    break;
                case "contactChannelType":
                    getWorld().contactRecordBean.get().setContactChannel(dataTable.get(data));
                    break;
                case "contactOutcome":
                    getWorld().contactRecordBean.get().setContactOutcome(dataTable.get(data));
                    break;
            }
        }
    }

    public void generateContactReasonJsonFromDataTable(Map<String, String> dataTable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/Reason.json");
        recordStartTime.set(System.currentTimeMillis());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", recordStartTime.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", getWorld().contactBean.get().getCorrelationID());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", getWorld().contactRecordBean.get().getContactRecordId());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", getWorld().contactBean.get().getUniqueID());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordReasonType", dataTable.get("contactReason"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("notes", dataTable.get("reasonComments"));
        getWorld().contactRecordBean.get().setSingleContactReasonData(dataTable);
    }

    @When("I search for Contact Record that was previously created")
    public void searchContactRecordCommentStep() {
        api.setbaseUri(baseURI);
        api.setEndPoint(contactRecordEndPoint + "/" + getWorld().contactRecordBean.get().getContactRecordId());
        api.getAPI();
        assertEquals(api.statusCode, 200);
        System.out.println(api.responseString);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    /**
     * User can search contact record reason that is previously created
     */
    @When("I search for Contact Record Reason that was previously created")
    public void searchContactRecordReasonStep() {
        api.setbaseUri(baseURI);
        api.setEndPoint(contactRecordReasonEndPoint + "/" + getWorld().contactRecordBean.get().getContactRecordId());
        api.getAPI();
        System.out.println(api.responseString);
    }

    /**
     * User can initiate contact record by correlation id
     *
     * @param correlationId
     */
    @And("I initiated contact record by correlation {string}")
    public void i_initiated_contact_recored_by_correlation(String correlationId) {
        api.setbaseUri(baseURI);
        String getCorrelationContactRecordDtl;
        getCorrelationContactRecordDtl = contactRecordCorrelationEndPoint.replace("{correlationId}", correlationId.toString());
        api.setEndPoint(getCorrelationContactRecordDtl);
    }

    @Given("I initiated Case Loader API for Create New Case for Contacts Record Validation")
    public void i_initiated_Case_Loader_API_for_Create_New_Case_for_Contacts_Record_Validation() {
        api.setbaseUri(caseLoaderBaseURI);
        api.setEndPoint(createCaseWithConsumers);
    }

    @When("I run Case Loader API for Create New Case and Customer with below detail for Contacts Record Validations:")
    public void i_run_Case_Loader_API_for_Create_New_Case_and_Customer_with_below_detail_for_Contacts_Record_Validations(DataTable caseMember) {
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
                    System.out.println("consumerSSN " + apiSearchConsumerSSN.get());
                } else {
                    apiSearchConsumerSSN.set(data.get("consumerSSN").toString());
                }
            }
        }

        //requestParams = caseLoaderRequest.generateCaseLoaderRequest();
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().generateCaseLoaderRequestWithSpecifiedParameters(consumerType, apiSearchConsumerFirstName.get(), apiSearchConsumerLastName.get(), apiSearchConsumerSSN.get(), consumerDateOfBirth, genderCode, relationShip));
        System.out.println("request params");
        System.out.println(requestParams.get());

        api.PutAPI(requestParams.get());
        System.out.println("api response string");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);

        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    public JsonObject generateCaseCheck(String caseIdentificationNumber) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCaseCheck.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseIdentificationNumber").get(0).getAsJsonObject().addProperty(
                "externalCaseId", caseIdentificationNumber);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public JsonObject generateCreateConsumerRequestWithoutConsumer() {
        JsonObject request = new JsonObject();
        JsonObject caseLoaderRequest = new JsonObject();
        JsonArray caseList = new JsonArray();
        caseList.add(caseLoaderRequest);

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

        caseLoaderRequest.add("case", CASE);

        JsonObject caseCommunicationPreferences = new JsonObject();
        JsonArray caseCommunicationPreferencesList = new JsonArray();
        caseCommunicationPreferencesList.add(caseCommunicationPreferences);

        caseCommunicationPreferences.addProperty("defaultInd", "boolean");
        caseCommunicationPreferences.addProperty("effectiveEndDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("effectiveStartDate", "2019-05-08T14:05:31.798Z");
        caseCommunicationPreferences.addProperty("updatedBy", "string");
        caseCommunicationPreferences.addProperty("updatedOn", "2019-05-08T14:05:31.798Z");

        CASE.add("communicationPreferences", gson.get().toJsonTree(caseCommunicationPreferencesList).getAsJsonArray());

        request.add("caseLoaderRequest", gson.get().toJsonTree(caseList).getAsJsonArray());

        return request;

    }

    @Then("I will create a new case for case loader case creation for Contacts Record Validations")
    public void i_will_create_a_new_case_for_case_loader_case_creation_for_Contacts_Record_Validations() {
        api.setbaseUri(caseConsumerAPI);
        api.setEndPoint(createCaseWithCaseCheck);

        JsonArray caseLoader = (JsonArray) requestParams.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoader.size(); i++) {
            JsonElement consumersData = caseLoader.get(i).getAsJsonObject().get("case");
            String caseIdentificationNumber = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNumber));
        }

        api.PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println("requestParamsCaseCheck1 " + api.responseString);
        assertEquals(api.statusCode, 200);

        HashMap<String, Object> caselist = api.jsonPathEvaluator.getJsonObject("caseList");
        HashMap<String, Object> content = (HashMap<String, Object>) ((ArrayList) caselist.get("content")).get(0);
        apiCaseId.set(content.get("caseId").toString());
        System.out.println("Case Id created is:" + apiCaseId.get());

        assertTrue(api.responseString.contains("caseList"));
        assertNotNull(api.jsonPathEvaluator.getJsonObject("caseList.content"));

        requestParamsWithoutConsumer.set(generateCreateConsumerRequestWithoutConsumer());
        System.out.println(requestParamsWithoutConsumer.get());

        api.PutAPI(requestParamsWithoutConsumer.get());

        System.out.println("requestParamsWithoutConsumer" + api.responseString);
        assertEquals(api.statusCode, 200);

        JsonArray caseLoaderWithoutConsumer = (JsonArray) requestParamsWithoutConsumer.get().get("caseLoaderRequest");
        for (int i = 0; i < caseLoaderWithoutConsumer.size(); i++) {
            JsonElement consumersData = caseLoaderWithoutConsumer.get(i).getAsJsonObject().get("case");
            String caseIdentificationNumber = consumersData.getAsJsonObject().get("caseIdentificationNumber").getAsString();
            requestParamsCaseCheck.set(generateCaseCheck(caseIdentificationNumber));
        }

        api.PostAPIWithParameter(requestParamsCaseCheck.get());
        System.out.println("requestParamsCaseCheck2 " + api.responseString);
        assertEquals(api.statusCode, 200);

        assertTrue(api.responseString.contains("caseList"));
        //assertNotNull(api.jsonPathEvaluator.getJsonObject("caseList.content"));
        JsonArray empty = new JsonArray();
        assertEquals(api.jsonPathEvaluator.getJsonObject("caseList.content"), empty);
    }

    /**
     * <b>Preparing request parameters to create third party contact record<b/>
     *
     * @param contactRecordType
     * @param consumerType
     * @param consumerLanguageCode
     * @param inboundCallQueue
     * @param contactChannelType
     * @param contactChannelValue
     * @param linkRefType
     * @param linkRefId
     */
    @When("I can provide contact record details {string}, {string}, {string}, {string}, {string}, {string}, {string} and {string}")
    public void i_can_provide_contact_record_as_agency_For_ThirdParty(String contactRecordType, String consumerType, String consumerLanguageCode, String inboundCallQueue, String contactChannelType, String contactChannelValue, String linkRefType, String linkRefId) {
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiCreateThirdPartyContactRecord.json");
        //long currentDateTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);
        // currentDateTime = System.currentTimeMillis();
        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", offsetTime);
        System.out.println(contactRecordId.get());
        String wrapUpTime = "9" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(2).randomNumber;

        organizationName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);

        //storing the existing values before update for history validation
        previousValuesToCompareEditedValues.get().put("organizationName", organizationName.get());
        previousValuesToCompareEditedValues.get().put("apiconsumerFirstName", apiconsumerFirstName.get());
        previousValuesToCompareEditedValues.get().put("apiconsumerLastName", apiconsumerLastName.get());
        previousValuesToCompareEditedValues.get().put("consumerType", consumerType);
        previousValuesToCompareEditedValues.get().put("contactRecordType", contactRecordType);
        previousValuesToCompareEditedValues.get().put("consumerLanguageCode", consumerLanguageCode);
        previousValuesToCompareEditedValues.get().put("contactRecordStatusType", "Complete");
        previousValuesToCompareEditedValues.get().put("contactReasonEditType", "Updating Contact Record Disposition");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", "00:00:02");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("organizationName", organizationName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", contactRecordType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", consumerType);
        //apitdu.jsonElement.getAsJsonObject().addProperty("contactType", "Unidentified Contact");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("preferredLanguageCode", consumerLanguageCode);
        if (!inboundCallQueue.isEmpty())
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("inboundCallQueue", inboundCallQueue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", contactChannelType);
        if (contactChannelType == "Phone") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
        } else if (contactChannelType == "SMS Text") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
        } else if (contactChannelType == "Email") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerEmail", contactChannelValue);
        } else if (contactChannelType == "Web Chat") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerEmail", contactChannelValue);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
        }
        if (!linkRefType.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", linkRefType);
            if (linkRefId.isEmpty()) {
                if (linkRefType.equalsIgnoreCase("case"))
                    //  apitdu.jsonElement.getAsJsonObject().addProperty("linkRefId", caseController.createdCaseId);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiCaseId.get());
                else
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiconsumerId.get());
            } else {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", linkRefId);
            }

        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());


    }

    /**
     * User verify thir party contact record created
     */
    @Then("I verify third party contact record is created by calling get contact record api")
    public void verifyThirdPartyRecordCreated() {
        i_initiated_contact_recored_by_correlation(apiconsumerCorrelationId.get());
        i_run_get_contact_records_api();
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecords");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);
            assertEquals(recordMap.get("contactType"), "Third Party");

        }
    }

    /**
     * User verify the following fields in third party contact record
     *
     * @param consumerType
     * @param contactRecordType
     * @param contactChannelType
     * @param contactChannelValue
     */
    @Then("I verify third party contact record details {string}, {string}, {string} and {string}")
    public void verifyThirdPartyRecordDetails(String consumerType, String contactRecordType, String contactChannelType, String contactChannelValue) {
        i_initiated_contact_recored_by_correlation(apiconsumerCorrelationId.get());
        i_run_get_contact_records_api();
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecords");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);
            assertEquals(recordMap.get("consumerFirstName"), apiconsumerFirstName.get());
            assertEquals(recordMap.get("consumerLastName"), apiconsumerLastName.get());
            assertEquals(recordMap.get("organizationName"), organizationName.get());
            assertEquals(recordMap.get("consumerType"), consumerType);
            assertEquals(recordMap.get("contactRecordType"), contactRecordType);
            assertEquals(recordMap.get("contactChannelType"), contactChannelType);

            if (contactChannelType == "Phone" || contactChannelType == "SMS Text") {
                assertEquals(recordMap.get("consumerPhone"), contactChannelValue);
            } else if (contactChannelType == "Email" || contactChannelType == "Web Chat") {
                assertEquals(recordMap.get("consumerEmail"), contactChannelValue);
            } else {
                assertEquals(recordMap.get("consumerPhone"), contactChannelValue);
            }
            //   assertTrue(recordMap.get("createdOn")!=null);
            // assertTrue(recordMap.get("createdBy")!=null);


        }
    }

    /**
     * User verify link reference details in third party contact record
     *
     * @param linkRefType
     * @param linkRefId
     */
    @Then("I verify third party contact record link details {string} and {string}")
    public void verifyLinkDetails(String linkRefType, String linkRefId) {
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecords");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);
            System.out.println("record map " + recordMap);
            assertEquals(recordMap.get("linkRefType"), linkRefType);
            if (linkRefId.isEmpty()) {
                if (linkRefType.equalsIgnoreCase("case"))
                    //linkRefId = caseController.createdCaseId;
                    linkRefId = apiCaseId.get();
                else
                    linkRefId = apiconsumerId.get();
            }

            assertEquals(recordMap.get("linkRefId"), Integer.parseInt(linkRefId));

        }
    }

    /**
     * User provide third party contact details for out bound
     *
     * @param contactCampaignType
     * @param contactRecordStatusType
     * @param programTypes
     * @param contactOutcome
     */
    @When("I can provide additional contact record details  {string},  {string},  {string} and {string}")
    public void i_can_provide_additional_contact_record_details_and(String contactCampaignType, String contactRecordStatusType, String programTypes, String contactOutcome) {

        if (!contactCampaignType.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactCampaignType", contactCampaignType);
        }

        if (!contactRecordStatusType.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStatusType", contactRecordStatusType);
        }

        if (!contactOutcome.isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactOutcome", contactOutcome);
        }

        if (!programTypes.isEmpty()) {
            for (String type : programTypes.split(",")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("programTypes").add(type);
            }

        }

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());


    }

    @When("I can provide general contact record details {string}, {string}, {string}, {string}, {string}, {string}, {string} and {string}")
    public void prepareRequestParameterForGeneralContactRecord(String contactRecordType, String consumerType, String consumerLanguageCode, String inboundCallQueue, String contactChannelType, String contactChannelValue, String linkRefType, String linkRefId) {
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiGeneralContactRecord.json");
        //long currentDateTime = System.currentTimeMillis();

        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
//        System.out.println("current date offset" +offsetTime);
//
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);
        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
//        System.out.println("current date offset" +offsetTime);
//        System.out.println(contactRecordId);
//        String wrapUpTime = "9"+apitdu.getRandomNumber(2).randomNumber;
//
//        apitdu.jsonElement.getAsJsonObject().addProperty("wrapUpTime", "00:00:07");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
//        apitdu.jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", contactRecordType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", consumerType);
//        //apitdu.jsonElement.getAsJsonObject().addProperty("createdOn", offsetTime);
//        //apitdu.jsonElement.getAsJsonObject().addProperty("contactType", "Unidentified Contact");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("preferredLanguageCode", consumerLanguageCode);
//        if(!inboundCallQueue.isEmpty())
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("inboundCallQueue", inboundCallQueue);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", contactChannelType);
//        if (contactChannelType=="Phone") {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
//        } else if (contactChannelType=="SMS Text") {
//            apitdu.jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
//        } else if (contactChannelType=="Email") {
//            apitdu.jsonElement.getAsJsonObject().addProperty("consumerEmail", contactChannelValue);
//        } else if (contactChannelType=="Web Chat") {
//            apitdu.jsonElement.getAsJsonObject().addProperty("consumerEmail", contactChannelValue);
//        } else {
//            apitdu.jsonElement.getAsJsonObject().addProperty("consumerPhone", contactChannelValue);
        //    }
        //      if(!linkRefType.isEmpty()){
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", linkRefType);
        //if(linkRefId.isEmpty()){
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiconsumerId.get());
        //}else{
        //  apitdu.jsonElement.getAsJsonObject().addProperty("linkRefId", linkRefId);
        //}

        //}
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params");
        System.out.println(requestParams.get());
    }

    @And("I provide contact reason details to create contact reason {string}, {string} and {string}")
    public void iCanProvideContactReasonDetails(String contactRecordReasonType, String notes, String contactRecordActionType) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/Reason.json");
        recordStartTime.set(System.currentTimeMillis());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordReasonType", contactRecordReasonType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("notes", notes);
        if (contactRecordReasonId.get() != null) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", recordStartTime.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordReasonId", contactRecordReasonId.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("contactRecordActions").get(0).getAsJsonObject().addProperty("contactRecordActionId", contactRecordActionId.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", recordStartTime.get());
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("contactRecordActions").get(0).getAsJsonObject().addProperty("contactRecordActionType", contactRecordActionType);

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @And("I ran create contact record reason API")
    public void runCreateContactReasonApi() {
        api.PostAPIWithParameter(requestParams.get());
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @And("I verify contact record reason details {string}, {string} and {string}")
    public void verifyContactRecordReasonDetails(String contactRecordReasonType, String notes, String contactRecordActionType) {
        ArrayList<HashMap<String, Object>> contactRecords = api.jsonPathEvaluator.get("contactRecordReasonList");
        HashMap<String, Object> recordMap = contactRecords.get(0);
        assertEquals(recordMap.get("notes").toString(), notes);
        assertEquals(recordMap.get("contactRecordReasonType").toString(), contactRecordReasonType);
        contactRecordReasonId.set(recordMap.get("contactRecordReasonId").toString());
        ArrayList<HashMap<String, Object>> actions = (ArrayList<HashMap<String, Object>>) recordMap.get("contactRecordActions");
        contactRecordActionId.set(actions.get(0).get("contactRecordActionId").toString());

        assertEquals(actions.get(0).get("contactRecordActionType").toString(), contactRecordActionType);
    }

    @And("I edit contact reason details to create contact reason {string}, {string} and {string}")
    public void editContactRecordReason(String contactRecordReasonType, String notes, String contactRecordActionType) {
        iCanProvideContactReasonDetails(contactRecordReasonType, notes, contactRecordActionType);
    }

    @And("I verify edited contact record reason details {string}, {string} and {string}")
    public void verifyEditedContactRecordReasonDetails(String contactRecordReasonType, String notes, String contactRecordActionType) {
        verifyContactRecordReasonDetails(contactRecordReasonType, notes, contactRecordActionType);

    }

    @Given("I created a consumer to link contact record")
    public void i_created_a_consumer_using_api() {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_initiated_create_consumer_api();
        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_provide_consumers_information_randomly();
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_run_create_consumer_api();
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_verify_the_case_search_api_response();
        apiconsumerId.set(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().get_the_consumer_id_from_response(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get()));
        System.out.println("apiconsumerId " + apiconsumerId.get());
    }


    @When("I can provide contact record edite details")
    public void contactRecordDetailsToEdit(Map<String, String> editDetails) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiEditContactRecords.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", editDetails.get("linkRefType"));
        if (editDetails.get("linkRefType").isEmpty()) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiconsumerId.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiconsumerId.get());
        }

        //long currentDateTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();

        String wrapUpTime = "9" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(2).randomNumber;
        organizationName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", Integer.parseInt(wrapUpTime));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("organizationName", organizationName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactReasonEditType", editDetails.get("contactReasonEditType"));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("preferredLanguageCode", editDetails.get("preferredLanguageCode"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStatusType", editDetails.get("contactRecordStatusType"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", editDetails.get("consumerType"));


        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());


    }

    /**
     * Validating the edited values are saved and fetched in get record details
     *
     * @param editDetails
     */
    @Then("I verify edited third party contact record details")
    public void verifyThirdPartyRecordEditedDetails(Map<String, String> editDetails) {
        i_initiated_contact_recored_by_correlation(apiconsumerCorrelationId.get());
        i_run_get_contact_records_api();
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecords");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);
            assertEquals(recordMap.get("consumerFirstName"), apiconsumerFirstName.get());
            assertEquals(recordMap.get("consumerLastName"), apiconsumerLastName.get());
            assertEquals(recordMap.get("organizationName"), organizationName.get());
            assertEquals(recordMap.get("consumerType"), editDetails.get("consumerType"));
            assertEquals(recordMap.get("contactReasonEditType"), editDetails.get("contactReasonEditType"));
            assertEquals(recordMap.get("contactRecordStatusType"), editDetails.get("contactRecordStatusType"));
        }
    }

    /**
     * Initiate the get contact record edit history API Call
     */
    @Given("I initiated contact record edit history API")
    public void initiateContactRecordEditHistory() {
        api.setbaseUri(baseURI);
        getContactRecordEditHistory = getContactRecordEditHistory.replace("{contactId}", contactRecordId.toString());
        api.setEndPoint(getContactRecordEditHistory);
    }

    /**
     * Run get api to fetch contact record edit history
     */
    @And("I run contact record edit history API")
    public void runGetContactRecordEditHistory() {
        api.getAPI();
        System.out.println("API Response:");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
        System.out.println(api.responseString);
    }

    /**
     * Verifying edited details on contact record history
     *
     * @param editDetails
     */
    @Then("I verify edited third party contact record history details")
    public void verifyContactRecordEditHistory(Map<String, String> editDetails) {
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecordEditHistory.content");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);
            System.out.println(recordMap.get("fieldLabel"));
           /* assertEquals(recordMap.get("consumerFirstName"), apiconsumerFirstName);
            assertEquals(recordMap.get("consumerLastName"), apiconsumerLastName);*/
            String actPreviousValue = recordMap.get("previousValue");
            String actUpdatedValue = recordMap.get("updatedValue");
            String expPreviousValue = null;
            String expUpdatedValue = null;
            switch (recordMap.get("fieldLabel")) {
                case "Contact Reason Disposition":
                    expPreviousValue = previousValuesToCompareEditedValues.get().get("contactRecordStatusType");
                    expUpdatedValue = editDetails.get("contactRecordStatusType");
                    break;
                case "Reason For Edit":
                    expPreviousValue = previousValuesToCompareEditedValues.get().get("contactReasonEditType");
                    expUpdatedValue = editDetails.get("contactReasonEditType");
                    break;
                case "Consumer Type":
                    expPreviousValue = previousValuesToCompareEditedValues.get().get("consumerType");
                    expUpdatedValue = editDetails.get("consumerType");
                    break;
                case "Preferred Language":
                    expPreviousValue = previousValuesToCompareEditedValues.get().get("consumerLanguageCode");
                    expUpdatedValue = editDetails.get("preferredLanguageCode");
                    break;
                case "Organization Name":
                    expPreviousValue = previousValuesToCompareEditedValues.get().get("organizationName");
                    expUpdatedValue = organizationName.get();
                    break;
            }
            assertEquals(actUpdatedValue, expUpdatedValue);
        }
    }

    /**
     * Validating edited by and edited on details on contact record history
     */
    @Then("I verify edited by and edited on third party contact record history details")
    public void verifyUpdatedByUpdatedOnContactRecordEditHistory() {
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecordEditHistory.content");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);
            assertTrue(recordMap.get("editedOn") != null && !recordMap.get("editedOn").isEmpty());
            assertTrue(recordMap.get("editedBy") != null && !recordMap.get("editedBy").isEmpty());
        }
    }

    @And("I can provide following contact record information for phone:")
    public void i_can_provide_following_contact_record_info(Map<String, String> dataTable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiCreateContactRecords.json");
        //recordStartTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);

        long endDateTime = recordStartTime.get() + TimeUnit.MINUTES.toMillis(200);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", "227");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", "227");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);

        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        System.out.println("1");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", offsetTime);
        System.out.println("2");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("callDuration", endDateTime - recordStartTime.get());
        System.out.println("3");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        System.out.println("4");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", "consumer");
        System.out.println("5");
        System.out.println("apiconsumerId " + apiconsumerId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", Integer.valueOf(apiconsumerId.get()));
        System.out.println("6");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        //apitdu.jsonElement.getAsJsonObject().addProperty("programTypes", "["+Character.toString((char)34)+"Program A"+Character.toString((char)34)+"]");
        System.out.println("11");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
        System.out.println("12");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
        System.out.println("13");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", "00:00:03");
        System.out.println("14");
        for (String data : dataTable.keySet()) {
            System.out.println("15");
            switch (data) {
                default:
                    System.out.println("16");
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(data, dataTable.get(data));
                    System.out.println("17");
            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("18");
        System.out.println(requestParams.get());
    }

    @Then("I can verify following inbound contact record information:")
    public void i_can_verify_inbound_contact_record_info(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                default:
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(data, dataTable.get(data));
                    assertEquals(api.responseBody.jsonPath().getList("contactRecords." + data).get(0), dataTable.get(data));
            }
        }
    }

    @And("I can add the contact record reason with the following values:")
    public void i_can_add_the_contact_record_reason_with_values(Map<String, String> dataTable) {
        api.setbaseUri(baseURI);
        api.setEndPoint(contactRecordReasonEndPoint);
        //apitdu.getJsonFromFile("crm/contactrecords/Reason.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/ReasonNew.json");
        //recordStartTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().get("contactRecordActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("contactRecordActionType", dataTable.get("contactAction"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("contactRecordId", Integer.valueOf(contactRecordId.get()));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("contactRecordReasonType", dataTable.get("contactReason"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("contactReasonActions").getAsJsonArray().get(0).getAsJsonObject().addProperty("notes", dataTable.get("reasonComments"));
        getWorld().contactRecordBean.get().setSingleContactReasonData(dataTable);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // requestParams.addProperty("contactRecordId", Integer.valueOf(contactRecordId));
        System.out.println(requestParams.get());
        api.PostAPIWithParameter(requestParams.get());
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify contact reason actions by contact with following values:")
    public void i_verify_all_select_actions_found_on_api(Map<String, String> dataTable) {
        System.out.println("contactRecordId : " + contactRecordId.get());
        i_initiated_specific_contact_record_comments_api(contactRecordId.get().toString());
        i_run_get_contact_record_comments_api();
        ArrayList<List> contactRecordActionTypes = api.jsonPathEvaluator.get("contactRecordReasonList.contactRecordActions.contactRecordActionType");
        System.out.println("contactRecordActionTypes " + contactRecordActionTypes);
        System.out.println(contactRecordActionTypes.get(0));
        for (String data : dataTable.keySet()) {
            switch (data) {
                default:
                    assertTrue(contactRecordActionTypes.get(0).contains(dataTable.get(data)), "'" + dataTable.get(data) + "' is not found.");
            }
        }
    }


    @Then("I verify consumer details on contact record created")
    public void verifyConsumerDetailsOnContactRecord() {
        ArrayList<String> linkRefId = api.jsonPathEvaluator.get("contactRecords.linkRefId");
        ArrayList<String> linkRefType = api.jsonPathEvaluator.get("contactRecords.linkRefType");
        assertEquals(linkRefId.get(0), Integer.parseInt(apiconsumerId.get()));
        assertEquals(linkRefType.get(0), "");
        System.out.println(linkRefId);
    }

    /**
     * User verify the following fields in third party contact record
     *
     * @param consumerType
     * @param contactRecordType
     * @param contactChannelType
     * @param contactChannelValue
     */
    @Then("I verify general contact record details {string}, {string}, {string} and {string}")
    public void verifyGeneralRecordDetails(String consumerType, String contactRecordType, String contactChannelType, String contactChannelValue) {
        i_initiated_contact_recored_by_correlation(apiconsumerCorrelationId.get());
        i_run_get_contact_records_api();
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecords");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);

            assertEquals(recordMap.get("consumerType"), consumerType);
            assertEquals(recordMap.get("contactRecordType"), contactRecordType);
            assertEquals(recordMap.get("contactChannelType"), contactChannelType);

            if (contactChannelType == "Phone" || contactChannelType == "SMS Text") {
                assertEquals(recordMap.get("consumerPhone"), contactChannelValue);
            } else if (contactChannelType == "Email" || contactChannelType == "Web Chat") {
                assertEquals(recordMap.get("consumerEmail"), contactChannelValue);
            } else {
                assertEquals(recordMap.get("consumerPhone"), contactChannelValue);
            }
        }
    }

    @Then("I verify edited contact disposition has updated value")
    public void verifyUpdatedContactDispostion() {
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecordEditHistory.content");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);
            if (recordMap.get("fieldLabel").equalsIgnoreCase("Contact Reason Disposition"))
                assertTrue(recordMap.get("previousValue").equalsIgnoreCase("Complete") && recordMap.get("updatedValue").equalsIgnoreCase("Incomplete"));
        }
    }

    @When("I provide the wrapup time for the contact")
    public void updateWrapUpTime() {
        randomWrapup.set((long) (Math.random() * (7200000 - 1000)) + 1000);
        String millsTime = DurationFormatUtils.formatDuration(randomWrapup.get(), "HH:mm:ss,SSS");
        int millis = Integer.parseInt(millsTime.split(",")[1]);
        if (millis >= 500) {
            randomWrapup.set(randomWrapup.get() - 500);
        }

        String wrapUpTimeInput = DurationFormatUtils.formatDuration(randomWrapup.get(), "HH:mm:ss");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", wrapUpTimeInput);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @Then("I verify wrapup time is captured in hh:mm:ss format")
    public void verifyWrapupTime() {
        String actWrapup = null;
        String expWrapup = null;
        i_initiated_contact_recored_by_correlation(apiconsumerCorrelationId.get());
        i_run_get_contact_records_api();
        ArrayList<HashMap<String, String>> contactRecords = api.jsonPathEvaluator.get("contactRecords");
        for (int i = 0; i < contactRecords.size(); i++) {
            HashMap<String, String> recordMap = contactRecords.get(i);
            actWrapup = recordMap.get("wrapUpTime");
        }


        expWrapup = DurationFormatUtils.formatDuration(randomWrapup.get(), "HH:mm:ss");
        assertEquals(actWrapup, expWrapup);
    }

    @Given("I created a case required for validation")
    public void createaCaseTestData() {
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().createCaseHead();
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_initiated_create_case_api();
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_can_information_for_case_creation();
        API_THREAD_LOCAL_FACTORY.getApiCaseControllerThreadLocal().i_can_run_create_case_api();
    }


    @Given("I can provide advanced contact details information:")
    public void i_can_provide_advanced_contact_details_information(Map<String, String> dataTable) {
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiGeneralContactRecord.json");

        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);
        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactType", "General");

        String[] contactRecordStatusType = new String[]{"Complete", "Dropped", "Escalate", "Incomplete", "Outbound Incomplete", "Requested Call Back",
                "Transfer"};
        contactRecordStatusTypeValue.set(CRMUtilities.getRandomValueFromArray(contactRecordStatusType));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStatusType", contactRecordStatusTypeValue.get());

        //apitdu.jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());

        String[] contactRecordTypes = new String[]{"Inbound", "Outbound"};
        contactRecordTypesValue.set(CRMUtilities.getRandomValueFromArray(contactRecordTypes));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", contactRecordTypesValue.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", dataTable.get("consumerType"));


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("preferredLanguageCode", dataTable.get("preferredLanguageCode"));


        if (contactRecordTypesValue.get().equals("Outbound")) {
            contactCallCampaign.set(CRMUtilities.getRandomStringFromList(contactCallCampaignList.get()));
            System.out.println("contactCallCampaign " + contactCallCampaign.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactCampaignType", contactCallCampaign.get());

            outcomeOfContact.set(CRMUtilities.getRandomStringFromList(outcomeOfContactList.get()));
            System.out.println("outcomeOfContact " + outcomeOfContact.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactOutcome", outcomeOfContact.get());

        } else if (contactRecordTypesValue.get().equals("Inbound")) {
            inboundCallQueue.set(CRMUtilities.getRandomStringFromList(inboundCallQueueList.get()));
            System.out.println("inboundCallQueue " + inboundCallQueue.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("inboundCallQueue", inboundCallQueue.get());
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", dataTable.get("contactChannelType"));

        contactPhoneNumber.set(RandomStringUtils.random(10, false, true));
        contactEmailAddress.set(RandomStringUtils.random(10, true, false) + "@maerskTest" + RandomStringUtils.random(3, true, true) + ".com");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactPhoneNumber.get());
        //apitdu.jsonElement.getAsJsonObject().addProperty("consumerEmail",contactEmailAddress);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", dataTable.get("linkRefType"));

        if (dataTable.get("linkRefType").equals("case")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiCaseId.get());
        } else if (dataTable.get("linkRefType").equals("consumer")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiconsumerId.get());
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", offsetTime);
        apiconsumerCreatedOn.set(offsetTime);
        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(2).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerLastName.get());
        //apitdu.jsonElement.getAsJsonObject().addProperty("updatedOn",offsetTime );

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params");
        System.out.println(requestParams.get());

    }

    @Then("I initiated Contact Records Advance Search API")
    public void i_initiated_Contact_Records_Advance_Search_API() {
        api.setbaseUri(contactRecordSearchURI);
        api.setEndPoint(contactRecordAdvancedSearch);
    }

    @Then("I can provide advanced contact search criteria information based on PhoneNumber:")
    public void i_can_provide_advanced_contact_search_criteria_information_based_on_PhoneNumber() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/advancedContactRecordSearch.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("phoneNumber", contactPhoneNumber.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params search" + requestParams.get());
    }


    @Then("I can provide advanced contact search criteria information based on ContactRecordID")
    public void i_can_provide_advanced_contact_search_criteria_information_based_on_ContactRecordID() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/advancedContactRecordSearch.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", apicontactRecordId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params search" + requestParams.get());
    }

    @Then("I search for an existing contact record with api by {string}")
    public void i_search_for_an_existing_contact_record_with_api_by(String searchCriteria) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/advancedContactRecordSearch.json");
        if (searchCriteria.equals("createdOn")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", apiconsumerCreatedOn.get());
        } else if (searchCriteria.equals("consumerFirstName")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get());
        } else if (searchCriteria.equals("consumerLastName")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerLastName.get());
        } else if (searchCriteria.equals("consumerId")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", apiconsumerId.get());
        } else if (searchCriteria.equals("contactRecordType")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("contactRecordType").add(contactRecordTypesValue.get().toString());
        } else if (searchCriteria.equals("contactType")) {
            String[] contactTypes;
            contactTypes = new String[]{"General", "Third Party", "Unidentified Contact"};
            Random r = new Random();
            int randomNumber = r.nextInt(contactTypes.length);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactType", contactTypes[randomNumber]);
        } else if (searchCriteria.equals("contactRecordStatusType")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("contactRecordStatusType").add(contactRecordStatusTypeValue.get().toString());
        } else if (searchCriteria.equals("caseId")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", apiCaseId.get());
        }


        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params search" + requestParams.get());

    }

    @Then("I can provide advanced contact search criteria information based on Email:")
    public void i_can_provide_advanced_contact_search_criteria_information_based_on_Email() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/advancedContactRecordSearch.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("emailAddress", contactEmailAddress.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params search" + requestParams.get());
    }

    @Then("I can provide advanced contact search criteria information based on Inbound Call Queue:")
    public void i_can_provide_advanced_contact_search_criteria_information_based_on_Inbound_Call_Queue() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/advancedContactRecordSearch.json");
        //apitdu.jsonElement.getAsJsonObject().addProperty("inboundCallQueue", inboundCallQueue);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("inboundCallQueue").add(inboundCallQueue.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("contactCampaignType").add(contactCallCampaign.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("contactOutcome").add(outcomeOfContact.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params search" + requestParams.get());
    }

    @Then("I can provide advanced contact search criteria information based on CreatedById:")
    public void i_can_provide_advanced_contact_search_criteria_information_based_on_CreatedById() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/advancedContactRecordSearch.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdById", ConfigurationReader.getProperty("createdByIdServiceAccountTwo"));
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params search" + requestParams.get());
    }

    @Then("I can provide advanced contact search criteria information based on ThirdPartyOrganization:")
    public void i_can_provide_advanced_contact_search_criteria_information_based_on_ThirdPartyOrganization() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/advancedContactRecordSearch.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("organizationName", organizationName.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params search" + requestParams.get());
    }

    @Then("I run advanced contact search API")
    public void i_run_advanced_contact_search_API() {
        api.PostAPIWithParameter(requestParams.get());
        System.out.println("API Response:");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
        System.out.println(api.responseString);
    }

    @Then("I can verify that correct contact record was found based on EmailAddress")
    public void i_can_verify_that_correct_contact_record_was_found_based_on_EmailAddress() {
        System.out.println("after search ");
        System.out.println(api.jsonPathEvaluator.get("contactRecords").toString());
        System.out.println("one field ");
        System.out.println(api.responseBody.jsonPath().getList("contactRecords." + "consumerEmail").get(0));

        assertTrue(api.responseString.contains(contactPhoneNumber.get()));
        assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "consumerEmail").get(0), contactEmailAddress.get());
    }

    @Then("I can verify that correct contact record was found based on PhoneNumber")
    public void i_can_verify_that_correct_contact_record_was_found_based_on_PhoneNumber() {
        System.out.println("after search ");
        System.out.println(api.jsonPathEvaluator.get("contactRecords").toString());
        System.out.println("one field ");
        System.out.println(api.responseBody.jsonPath().getList("contactRecords." + "consumerPhone").get(0));

        assertTrue(api.responseString.contains(contactPhoneNumber.get()));
        assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "consumerPhone").get(0), contactPhoneNumber.get());
    }

    @Then("I can verify that correct contact record was found based on ContactRecordID")
    public void i_can_verify_that_correct_contact_record_was_found_based_on_ContactRecordID() {
        System.out.println("after search ");
        System.out.println(api.jsonPathEvaluator.get("contactRecords").toString());
        System.out.println("one field ");
        System.out.println(api.responseBody.jsonPath().getList("contactRecords." + "contactRecordId").get(0));

        assertTrue(api.responseString.contains(apicontactRecordId.toString()));
        assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "contactRecordId").get(0), apicontactRecordId.get());
    }

    @Then("I can verify that correct contact record was found based on {string}")
    public void i_can_verify_that_correct_contact_record_was_found_based_on(String searchCriteria) {
        System.out.println(api.jsonPathEvaluator.get("contactRecords").toString());
        System.out.println("one field ");
        if (searchCriteria.equals("createdOn")) {
            System.out.println(api.responseBody.jsonPath().getList("contactRecords." + searchCriteria).get(0));
            assertTrue(api.responseString.contains(apicontactRecordId.toString()));
            assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "searchCriteria").get(0), apiconsumerCreatedOn.get());
        } else if (searchCriteria.equals("consumerFirstName")) {
            assertTrue(api.responseString.contains(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get()));
            assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "searchCriteria").get(0), API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get());
        } else if (searchCriteria.equals("consumerLastName")) {
            assertTrue(api.responseString.contains(API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get()));
            assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "searchCriteria").get(0), API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerLastName.get());
        } else if (searchCriteria.equals("caseId")) {
            assertTrue(api.responseString.contains("1555"));
            assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "searchCriteria").get(0), "1555");
        } else if (searchCriteria.equals("consumerId")) {
            assertTrue(api.responseString.contains(apiconsumerId.get()));
            assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "searchCriteria").get(0), apiconsumerId.get());
        } else if (searchCriteria.equals("caseId")) {
            assertTrue(api.responseString.contains(apiCaseId.get()));
            assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "searchCriteria").get(0), apiCaseId.get());
        }
    }

    @Then("I can verify that search result of contact record search is in excess")
    public void i_can_verify_that_search_result_of_contact_record_search_is_in_excess() {
        api.PostAPIWithParameter(requestParams.get());
        System.out.println("API Response:");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        System.out.println(api.responseString);
        assertTrue(api.responseString.contains("Search results in excess, please enter additional search criteria to limit search results."));
    }

    @Then("I can verify that search result of contact record search is in excess or found")
    public void i_can_verify_that_search_result_of_contact_record_search_is_in_excess_or_found() {
        api.PostAPIWithParameter(requestParams.get());
        System.out.println("api response " + api.responseString);
        if (api.responseString.contains("Search results in excess, please enter additional search criteria to limit search results.")) {
            assertTrue(api.responseString.contains("Search results in excess, please enter additional search criteria to limit search results."));
        } else {
            System.out.println("third party");
            System.out.println("api response " + api.responseString);
            assertTrue(api.responseBody.jsonPath().getList("contactRecords." + "consumerLastName").size() > 0);
        }
    }


    @Then("I can verify that correct contact record was found based on InboundCallQueue, callCampaign, contactOfOutcome")
    public void i_can_verify_that_correct_contact_record_was_found_based_on_InboundCallQueue_callCampaign_contactOfOutcome() {
        System.out.println("after search ");
        System.out.println(api.jsonPathEvaluator.get("contactRecords").toString());
        System.out.println("one field ");
        System.out.println(api.responseBody.jsonPath().getList("contactRecords." + "consumerPhone").get(0));

        assertTrue(api.responseString.contains(inboundCallQueue.get()));
        System.out.println("result 1");
        assertTrue(api.responseString.contains(outcomeOfContact.get()));
        System.out.println("result 2");
        assertTrue(api.responseString.contains(contactCallCampaign.get()));
        System.out.println("result 3");
        assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "inboundCallQueue").get(0), inboundCallQueue.get());
        System.out.println("result 4");
    }

    @Then("I can verify that correct contact record was found based on CreatedById")
    public void i_can_verify_that_correct_contact_record_was_found_based_on_CreatedById() {
        System.out.println("after search ");
        System.out.println(api.jsonPathEvaluator.get("contactRecords").toString());
        assertTrue(api.responseString.length() > 0);
    }

    @Then("I can verify that correct contact record was found based on ThirdPartyOrganization")
    public void i_can_verify_that_correct_contact_record_was_found_based_on_ThirdPartyOrganization() {
        System.out.println("after search ");
        System.out.println(api.jsonPathEvaluator.get("contactRecords").toString());

        assertTrue(api.responseString.contains(organizationName.get()));
        System.out.println("result 3");
        assertEquals(api.responseBody.jsonPath().getList("contactRecords." + "organizationName").get(0), organizationName.get());
        System.out.println("result 4");
    }

    @Given("I can provide contact record details for Third Party Contact Record creation:")
    public void i_can_provide_contact_record_details_for_Third_Party_Contact_Record_creation(Map<String, String> dataTable) {
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        apiconsumerUiid.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiCreateThirdPartyContactRecord.json");
        //long currentDateTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);
        // currentDateTime = System.currentTimeMillis();
        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", offsetTime);
        System.out.println(contactRecordId.get());
        String wrapUpTime = "9" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(2).randomNumber;

        organizationName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);

        //storing the existing values before update for history validation
        previousValuesToCompareEditedValues.get().put("organizationName", organizationName.get());
        previousValuesToCompareEditedValues.get().put("apiconsumerFirstName", apiconsumerFirstName.get());
        previousValuesToCompareEditedValues.get().put("apiconsumerLastName", apiconsumerLastName.get());
        previousValuesToCompareEditedValues.get().put("consumerType", dataTable.get("consumerType"));
        previousValuesToCompareEditedValues.get().put("contactRecordType", dataTable.get("contactRecordType"));
        previousValuesToCompareEditedValues.get().put("consumerLanguageCode", dataTable.get("consumerLanguageCode"));
        previousValuesToCompareEditedValues.get().put("contactRecordStatusType", "Complete");
        previousValuesToCompareEditedValues.get().put("contactReasonEditType", "Updating Contact Record Disposition");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", "00:00:02");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("organizationName", organizationName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", dataTable.get("contactRecordType"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", dataTable.get("consumerType"));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("preferredLanguageCode", dataTable.get("preferredLanguageCode"));
        if (!inboundCallQueue.get().isEmpty())
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("inboundCallQueue", inboundCallQueue.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", dataTable.get("contactChannelType"));
        if (dataTable.get("contactChannelType") == "Phone") {
            contactPhoneNumber.set(RandomStringUtils.random(10, false, true));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", contactPhoneNumber.get());
        }


        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", dataTable.get("linkRefType"));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", apiconsumerId.get());


        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());

    }

    /**
     * Initiate the get contact record edit history API Call
     */
    @Given("I initiated Link API")
    public void initiateLinkAPI() {
        api.setbaseUri(baseURI);
        api.setEndPoint(linkEndPoint);
    }


    @Then("I provide details link the {string} {string} to {string}")
    public void i_link_the_something_to_something(String linkTo, String type1, String type2) {
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        apiconsumerCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_correlation_id());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/link/link.json");
        switch (linkTo) {
            case "Cont2Cons":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apiconsumerId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apicontactRecordId.get());
                break;
            case "Cons2Cont":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apicontactRecordId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apiconsumerId.get());
                break;
            case "Cont2Task":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apiTaskId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apicontactRecordId.get());
                break;
            case "Cont2Case":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apiCaseId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apicontactRecordId.get());
                break;
            case "Case2Task":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apiTaskId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apiCaseId.get());
                break;
            case "Task2Case":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apiCaseId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apiTaskId.get());
                break;
            case "Case2Cons":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apiconsumerId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apicontactRecordId.get());
                break;
            case "Case2Cont":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apicontactRecordId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apiCaseId.get());
                break;
            case "Task2Cont":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apicontactRecordId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apiTaskId.get());
                break;
            case "Task2Cons":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apiconsumerId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apiTaskId.get());
                break;
            case "Cons2task":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", apiTaskId.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", apiconsumerId.get());
                break;
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("createdBy", api.userId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("updatedBy", api.userId);
        //apitdu.jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("updatedon", offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalRefType", type1);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalRefType", type2);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("effectiveStartDate", offsetTime);
        //apitdu.jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("effectiveEndDate", "");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println("request params for Linking" + requestParams.get());
    }

    @Then("I can run link API")
    public void i_run_link_api() {
        api.PutAPI(requestParams.get());
    }

    @Then("I can verify that link the {string} to {string}")
    public void i_verify_that_link_the_something_to_something(String type1, String type2) {
        System.out.println(" Link API Response:" + type1 + " to linked " + type2);
        System.out.println("api response " + api.responseString);
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @Given("I created a case consumer to link contact record")
    public void i_created_a_case_consumer_using_api() {
        API_THREAD_LOCAL_FACTORY.getAPICaseLoaderEligibilityEnrollmentController().createCase();
        APIConsumerSearchController consumerSearchController = new APIConsumerSearchController();
        consumerSearchController.initiatedCaseConsumerSearchAPI();
        apiCaseId.set(consumerSearchController.runCaseConsumerSearchAPI("consumerSSN"));

    }

    @Then("I created a Task to link case")
    public void i_Create_Task_for_link() {
        API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().createTaskReturnTaskID();
        System.out.println("Tasking in CR" + API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().apiTaskId.get());
        apiTaskId.set(API_THREAD_LOCAL_FACTORY.getTaskManagementControllerTheadLocal().apiTaskId.get());
        System.out.println("Tasking in CR1" + apiTaskId.get());
    }

    @When("I can provide following contact record information for systematic CR creation:")
    public void i_can_provide_following_contact_record_information_for_systematic_CR_creation(Map<String, String> dataTable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiCreateContactRecordSystematically.json");
        //recordStartTime = System.currentTimeMillis();
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);

        long endDateTime = recordStartTime.get() + TimeUnit.MINUTES.toMillis(200);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", "227");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", "227");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);

        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("callDuration", endDateTime - recordStartTime.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", "consumer");
        System.out.println("apiconsumerId " + apiconsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", Integer.valueOf(apiconsumerId.get()));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        //apitdu.jsonElement.getAsJsonObject().addProperty("programTypes", "["+Character.toString((char)34)+"Program A"+Character.toString((char)34)+"]");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", "00:00:03");

        for (String data : dataTable.keySet()) {

            switch (data) {
                default:

                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(data, dataTable.get(data));

            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        System.out.println(requestParams);
    }


    @When("I link consumer to the above contact record via api")
    public void i_link_consumer_to_the_above_contact_record_via_api() {
        api.setbaseUri(baseURI);
        api.setEndPoint("/mars/crm/link");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiLinkConsumerToContactRecord.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("externalLinkPayload").getAsJsonObject().addProperty("internalId", apicontactRecordId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());

        api.PutAPI(requestParams.get());
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
    }

    @When("I initiated Med Chat Create Contact Records API")
    public void i_initiated_Med_Chat_Create_Contact_Records_API() {
        api.setbaseUri(baseURI);
        api.setEndPoint(medChatContactRecordEndPoint);
    }


    @When("I can provide following contact record information for Med Chat contact record creation:")
    public void i_can_provide_following_contact_record_information_for_Med_Chat_contact_record_creation(Map<String, String> dataTable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/medChatApiCreateContactRecord.json");

        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        long endDateTime = recordStartTime.get() + TimeUnit.MINUTES.toMillis(200);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", "3183");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", "3183");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);

        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", "00:00:03");

        for (String data : dataTable.keySet()) {

            switch (data) {
                default:

                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(data, dataTable.get(data));

            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        System.out.println(requestParams.get());
    }

    @And("User send Api call with name {string} payload {string} for Med Chat")
    public void userSendApiCallWithNamePayloadForMedChat(String name, String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/" + payload + ".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        api.PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @And("I send API CALL for init CONTACT with Contact Reasons")
    public void IsendAPICALLfornitCONTACTwithContactReasons(Map<String, String> data) {
        APIUtilitiesForUIScenarios.getInstance().createAConsumerAndGetConsumerID();
        APIUtilitiesForUIScenarios.getInstance().initiateContactRecordApi();
        APIUtilitiesForUIScenarios.getInstance().linkConsumerToContact();
        APIUtilitiesForUIScenarios.getInstance().createContactReasons(data);
        APIUtilitiesForUIScenarios.getInstance().createContactRecordApi();
    }

    @And("I send API CALL for CONTACT Record with Contact Reasons")
    public void IsendAPICALLforCONTACTRecordwithContactReasons(Map<String, String> data) {
        APIUtilitiesForUIScenarios.getInstance().initiateContactRecordApi();
        APIUtilitiesForUIScenarios.getInstance().linkConsumerToContact();
        APIUtilitiesForUIScenarios.getInstance().createContactReasons(data);
    }

    @And("I send API CALL for create CONTACT Record with data")
    public void IsendAPICALLforcreateCONTACTRecordwithdata(Map<String, String> data) {
        APIUtilitiesForUIScenarios.getInstance().createContactRecordApi(data);
    }

    @And("I send API CALL for Create Consumer with Data")
    public void IsendAPICALLforCreateConsumerwithData(Map<String, String> data) {
        APIUtilitiesForUIScenarios.getInstance().createAConsumerWithDataAndGetConsumerID(data);
    }

    @And("I send API CALL for Create CaseConsumer")
    public void IsendAPICALLforCreateCaseConsumer() {
        APIUtilitiesForUIScenarios.getInstance().createCaseConsumerWithDataAndGetCaseID();
        caseID.set(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asInt("c.object.result[0].cases.caseId"));
    }

    @And("I send API CALL for create CONTACT Record with new Consumer with Data")
    public void IsendAPICALLforcreateCONTACTRecordwithnewConsumerwithData(Map<String, String> data) {
        APIUtilitiesForUIScenarios.getInstance().createAConsumerWithDataAndGetConsumerID(data);
        APIUtilitiesForUIScenarios.getInstance().initiateContactRecordApi();
        APIUtilitiesForUIScenarios.getInstance().linkConsumerToContact();
        APIUtilitiesForUIScenarios.getInstance().createContactReasonsNodata();
        APIUtilitiesForUIScenarios.getInstance().createContactRecordApi();
    }

    @And("I send API CALL for create CONTACT Record after creating new Consumer with data")
    public void IsendAPICALLforcreateCONTACTRecordaftercreatingnewConsumerwithdata(Map<String, String> data) {
        APIUtilitiesForUIScenarios.getInstance().initiateContactRecordApi();
        APIUtilitiesForUIScenarios.getInstance().linkConsumerToContact();
        APIUtilitiesForUIScenarios.getInstance().createContactReasonsNodata();
        APIUtilitiesForUIScenarios.getInstance().createContactRecordApi(data);
    }

    @When("I can provide following contact record information for WebChat creation with no email and no phone:")
    public void i_can_provide_following_contact_record_information_for_WebChat_creation_with_no_email_and_no_phone(Map<String, String> dataTable) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/coverVAWebChatContactRecord.json");

        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);

        long endDateTime = recordStartTime.get() + TimeUnit.MINUTES.toMillis(200);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdBy", 227);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedBy", 227);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);

        offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("callDuration", endDateTime - recordStartTime.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiconsumerCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefType", "consumer");
        System.out.println("apiconsumerId " + apiconsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("linkRefId", Integer.valueOf(apiconsumerId.get()));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiconsumerUiid.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("wrapUpTime", "00:00:03");

        for (String data : dataTable.keySet()) {

            switch (data) {
                default:

                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(data, dataTable.get(data));

            }
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        System.out.println(requestParams.get());
    }

    @Then("I send API CALL to TM for get user list with accountType")
    public void iSendAPICALLToTMForGetUserListWithAccountType(Map<String, String> data) {
        APIUtilitiesForUIScenarios.getInstance().getUserNamesTMwithAccType(data);
    }

    @Given("I initiated Contact Records IVR API for {string}")
    public void i_initiated_Contact_Records_IVR_API_for(String string) {
        api.setbaseUri(baseURI);
        api.setEndPoint(IVRcontactRecord);
    }

    @Given("I can provide IVR contact record details {string}, {string}, {string}, {string} {string} and {string}")
    public void i_can_provide_IVR_contact_record_details_and(String firstName, String lastName, String DOB, String SSN, String consumerPhone, String interactionID) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/IVRContactRecordCoverVA.json");
        ivr_consumer_name.set(firstName);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerFirstName", ivr_consumer_name.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerLastName", lastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerDateOfBirth", DOB);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerSSN", SSN);

        ivr_phone_number.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);

        if (consumerPhone.equalsIgnoreCase("random")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", ivr_phone_number.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", consumerPhone);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams);
    }

    @Then("I can run IVR create contact records API")
    public int i_can_run_IVR_create_contact_records_API() {
        api.PutAPI(requestParams.get());
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        IVRcontactRecordID.set(api.jsonPathEvaluator.get("contactRecordId"));
        System.out.println("contactRecordID " + IVRcontactRecordID.get());
        return IVRcontactRecordID.get();
    }

    @Given("I initiate GET ContactRecord Display Fields API and validate status code")
    public void iInitiatedGetDisplayFieldAPI() {
        api.setbaseUri(baseURI);
        api.setEndPoint(createDisplayField);
        api.getAPI();
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
    }

    @Given("I initiate GET Facility Name Fields API and validate status code")
    public void iInitiatedGetFaciliFtyFieldAPI() {
        api.setbaseUri(baseURIfacilityName);
        api.setEndPoint(facilityNameEndpoint );
        api.getAPI();
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
    }

    @When("I verify the following fields {string} {string} {string} {string} {string} in response")
    public void i_verify_the_fields_in_response(String effectiveStartdate, String createdOn, String createdBy, String updatedBy, String updatedOn) {
        List<String> effectiveStartDates=api.jsonPathEvaluator.get(effectiveStartdate);
        for (int i = 0; i <effectiveStartDates.size() ; i++) {
            assertNotEquals(effectiveStartDates.get(i), null);
            assertNotEquals(effectiveStartDates.get(i),"");
        }

        List<String> createdBys=api.jsonPathEvaluator.get(createdOn);
        for (int i = 0; i <createdBys.size() ; i++) {
            assertNotEquals(createdBys.get(i), null);
            assertNotEquals(createdBys.get(i),"");
        }

        List<String> createdOns=api.jsonPathEvaluator.get(createdBy);
        for (int i = 0; i <createdOns.size() ; i++) {
            assertNotEquals(createdOns.get(i), null);
            assertNotEquals(createdOns.get(i),"");
        }

        List<String> updatedBys=api.jsonPathEvaluator.get(updatedBy);
        for (int i = 0; i <updatedBys.size() ; i++) {
            assertNotEquals(updatedBys.get(i), null);
            assertNotEquals(updatedBys.get(i),"");
        }

        List<String> updatedOns=api.jsonPathEvaluator.get(updatedOn);
        for (int i = 0; i <updatedOns.size() ; i++) {
            assertNotEquals(updatedOns.get(i), null);
            assertNotEquals(updatedOns.get(i),"");
        }
    }

    @When("I verify the following field {string} in response")
    public void i_verify_the_field_in_response(String field) {
        List<String> fields=api.jsonPathEvaluator.get(field);
        for (int i = 0; i <fields.size() ; i++) {
            assertEquals(fields.get(i), null, "invalid facilityName: "+field);
        }
    }

    @Then("Verify the facility name field value in dropdown in UI and in api response")
    public void verify_the_facility_name_dropdown_values_in_dropdown_and_in_API_reponse() {

        List<String> facilityNameFields=api.jsonPathEvaluator.get("ENUM_FACILITY_NAME.description");
        String facilityName=facilityNameFields.get(0);
        Assert.assertTrue(facilityNameFields.contains(facilityName));

        waitForVisibility(activeContact.facilityName, 3);
        activeContact.facilityName.click();
        List<String> actualValues = new ArrayList<>();
        for (int i = 0; i < activeContact.facilityNameDropdownList.size(); i++) {
            if (!activeContact.facilityNameDropdownList.get(i).getText().isEmpty())
                actualValues.add(activeContact.facilityNameDropdownList.get(i).getText());
        }
        Assert.assertTrue(actualValues.contains(facilityName));
    }

    @When("I search for IVR created contact record with contactRecordID")
    public void i_search_for_IVR_created_contact_record_with_contactRecordID() {
        waitFor(4);
        contactRecord.contactRecordId.sendKeys(IVRcontactRecordID.get().toString());
        System.out.println("id for search " + IVRcontactRecordID.get());
        waitFor(4);
        contactRecord.SearchButton.click();
        waitFor(2);
        edit_cont_Rec.frstRecord.click();
        waitFor(2);
    }

    @Then("I verify phoneNumber, interactionID, consumerName, and rest of details for IVR contact record")
    public void i_verify_phoneNumber_interactionID_consumerName_and_rest_of_details_for_IVR_contact_record() {
        ivrContactRecordPage.selfServiceContactType.isDisplayed();
        ivrContactRecordPage.channelIVR.isDisplayed();
        ivrContactRecordPage.IVRdisposition.isDisplayed();
        ivrContactRecordPage.interactionID.isDisplayed();
        ivrContactRecordPage.contactReason.isDisplayed();
        ivrContactRecordPage.additionalComments.isDisplayed();
        ivrContactRecordPage.contactStartTime.isDisplayed();
        assertEquals(ivrContactRecordPage.businessUnit.getText(), "CVCC");

        ivrContactRecordPage.ivr_phone(ivr_phone_number.get().substring(0, 3)).isDisplayed();
        ivrContactRecordPage.consumerName(ivr_consumer_name.get()).isDisplayed();

    }

    @Given("I provide IVR contact record details")
    public void i_provide_IVR_contact_record_details_with_test_data(List<String> testData) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/IVRContactRecordCoverVA.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerFirstName", "Adam");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerLastName", "Smith");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", "5555555554");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerDateOfBirth", "07012000");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerSSN", "019951491");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonArray("consumerIdentificationNumberList").get(0).getAsJsonObject().addProperty("externalConsumerId", testData.get(0));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonArray("consumerIdentificationNumberList").get(1).getAsJsonObject().addProperty("externalConsumerId", testData.get(1));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").addProperty("addressStreet1", testData.get(2));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").addProperty("addressStreet2", testData.get(3));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").addProperty("addressCity", testData.get(4));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").addProperty("addressState", testData.get(5));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").addProperty("addressType", testData.get(6));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").addProperty("addressZip", testData.get(7));

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @Then("I verify VaCMS, MMIS and address for IVR contact record")
    public void i_verify_VaCMS_MMIS_and_address_for_IVR_contact_record() {

        String vacms = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonArray("consumerIdentificationNumberList").get(0).getAsJsonObject().get("externalConsumerId").toString();
        String mmis = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonArray("consumerIdentificationNumberList").get(1).getAsJsonObject().get("externalConsumerId").toString();
        String addressStreet1 = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").get("addressStreet1").toString().replaceAll("\"", "");
        String addressStreet2 = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").get("addressStreet2").toString().replaceAll("\"", "");
        String addressCity = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").get("addressCity").toString().replaceAll("\"", "");
        String addressState = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").get("addressState").toString().replaceAll("\"", "");
        String addressType = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").get("addressType").toString().replaceAll("\"", "");
        String addressZip = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonObject("physicalAddress").get("addressZip").toString().replaceAll("\"", "");
        String expectedFullAddress = addressStreet1.concat(", ").concat(addressStreet2).concat(", ").concat(addressCity).concat(", ").concat(addressState).concat(", ").concat(addressZip);
        waitFor(5);
        ivrContactRecordPage.VaCMSconsumerID.click();
        String fullAddress = Driver.getDriver().findElement(By.xpath(String.format("//tbody[@class='MuiTableBody-root']//td[contains(text(), '%s')]", expectedFullAddress))).getText();
        assertEquals(ivrContactRecordPage.mmisID.getText(), mmis.replace("\"", ""));
        assertEquals(ivrContactRecordPage.vacmsID.getText(), vacms.replace("\"", ""));
        assertTrue(fullAddress.contains(addressStreet1));
        assertTrue(fullAddress.contains(addressStreet2));
        assertTrue(fullAddress.contains(addressCity));
        assertTrue(fullAddress.contains(addressState));
        assertTrue(fullAddress.contains(addressZip));
    }

    private static String getRandomDob(boolean withSeparator) {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1970, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        String birthDate = randomBirthDate.format(formatters);
        return withSeparator ? birthDate : birthDate.replace("/", "");
    }

    @Given("I can provide random IVR contact record details for new consumer creation {string}, {string}, {string}, {string} {string} and {string}")
    public void i_can_provide_random_IVR_contact_record_details_for_new_consumer_creation_and(String firstName, String lastName, String DOB, String SSN, String consumerPhone, String externalID) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/IVRContactRecordCoverVA.json");
        ;
        ivr_consumer_name.set(firstName);
        ivr_consumer_Fname.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        ivr_consumer_Lname.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(6).randomString);
        ivr_consumer_DOB.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);
        ivr_consumer_SSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        ivr_consumer_ExternalID.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber);

        if (firstName.equalsIgnoreCase("random")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerFirstName", ivr_consumer_Fname.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerFirstName", firstName);
        }

        if (lastName.equalsIgnoreCase("random")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerLastName", ivr_consumer_Lname.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerLastName", lastName);
        }

        if (DOB.equalsIgnoreCase("random")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerDateOfBirth", ivr_consumer_DOB.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerDateOfBirth", DOB);
        }

        if (SSN.equalsIgnoreCase("random")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerSSN", ivr_consumer_SSN.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").addProperty("consumerSSN", SSN);
        }

        if (externalID.equalsIgnoreCase("random")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonArray("caseIdentificationNumberList").get(0).getAsJsonObject().addProperty("externalCaseId", ivr_consumer_ExternalID.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonArray("consumerIdentificationNumberList").get(0).getAsJsonObject().addProperty("externalConsumerId", ivr_consumer_ExternalID.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("authenticatedConsumerData").getAsJsonArray("consumerIdentificationNumberList").get(1).getAsJsonObject().addProperty("externalConsumerId", ivr_consumer_ExternalID.get());
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @Then("I verify consumerFName, consumerLName, consumerDOB, consumerSSN and external ID for newly created consumer")
    public void i_verify_consumerFName_consumerLName_consumerDOB_consumerSSN_and_external_ID_for_newly_created_consumer() {
        ivrContactRecordPage.VaCMSconsumerID.click();
        waitFor(5);
        assertEquals(ivrContactRecordPage.consumerNameDemogPage(ivr_consumer_Fname.get() + "  " + ivr_consumer_Lname.get()).getText(), ivr_consumer_Fname.get() + " " + ivr_consumer_Lname.get());
        consumerProfilePage.ssnUnMaskUpdatePage.click();
        waitFor(2);
        assertEquals(consumerProfilePage.consumerSSN.getText().replace("-", "").substring(0, 9), ivr_consumer_SSN.get());
        assertEquals(ivrContactRecordPage.mmisID.getText(), ivr_consumer_ExternalID.get());
        assertEquals(ivrContactRecordPage.vacmsID.getText(), ivr_consumer_ExternalID.get());

    }

    @When("I search for IVr created contact recod and verify correct icon is displayed")
    public void i_search_for_IVr_created_contact_recod_and_verify_correct_icon_is_displayed() {
        waitFor(4);
        contactRecord.contactRecordId.sendKeys(IVRcontactRecordID.get().toString());
        System.out.println("id for search " + IVRcontactRecordID.get());
        waitFor(4);
        contactRecord.SearchButton.click();
        waitFor(2);
        assertTrue(ivrContactRecordPage.ivrContactRecordIcon.isDisplayed());
    }

    @Given("I initiated community outreach API")
    public void i_initiated_community_outreach_API() {
        api.setbaseUri(communityOutreachURI);
        api.setEndPoint(createCommunityOutreach);
    }

    @When("I can provide following information for community outreach creation")
    public void i_can_provide_following_information_for_community_outreach_creation() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/communityOutreachCreate.json");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }


    @When("I can run create community outreach API")
    public Integer i_can_run_create_community_outreach_API() {
        api.PutAPI(requestParams.get());
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");

        ArrayList<Integer> apiCommunityOutreachIds = api.jsonPathEvaluator.get("communityOutreachSession.communityOutreachId");
        apicommunityOutreachId.set(apiCommunityOutreachIds.get(0));
        System.out.println(apicommunityOutreachId.get());
        return apicommunityOutreachId.get();
    }

    @When("I initiated specific community outreach search {string}")
    public void i_initiated_specific_community_outreach_search(String string) {
        api.setbaseUri(communityOutreachURI);
        api.setEndPoint(searchCommunityOutreach);
    }

    @Given("I can provide following information for community outreach search")
    public void i_can_provide_following_information_for_community_outreach_search() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/communityOutreachSearch.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("communityOutreachSession").addProperty("communityOutreachId", apicommunityOutreachId.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        System.out.println(requestParams.get());
    }

    @When("I run search community outreach API")
    public void i_run_search_community_outreach_API() {
        api.PostAPIWithParameterForProject(requestParams.get(), "BLCRM");
        System.out.println(api.responseString);
        assertEquals(api.statusCode, 200);
        assertTrue(api.responseString.contains("success"));
        assertEquals(api.jsonPathEvaluator.get("status"), "success");

    }

    @Then("I can verify following community outreach information:")
    public void i_can_verify_following_community_outreach_information(Map<String, String> dataTable) {
        assertTrue(api.responseString.contains(dataTable.get("sessionType")));
    }

    @Then("I verify contact record information as created by userID and userName")
    public void i_verify_contact_record_info_as_created_by_userName_and_userId() {
        waitFor(2);
        Assert.assertTrue(ivrContactRecordPage.createdByUserName.isDisplayed(), "Created by UserName is not displayed");
        Assert.assertTrue(ivrContactRecordPage.createdByUserId.isDisplayed(), "Created by UserID is not displayed");
        Assert.assertTrue(ivrContactRecordPage.createdByUserNameSystem.isDisplayed(), "Created by Username is not displayed as System name");
    }

    @Then("I verify contact record information as received by userID and userName")
    public void i_verify_contact_record_info_as_received_by_userName_and_userId() {
        waitFor(2);
        Assert.assertTrue(ivrContactRecordPage.receivedByUserName.isDisplayed(), " received by UserName is not displayed");
        Assert.assertTrue(ivrContactRecordPage.receivedByUserId.isDisplayed(), "received by UserId is not displayed");
    }

    @Then("I verify created by info not same with received by for IVR call")
    public void i_verify_that_created_info_is_different_than_received_info() {
        waitFor(7);
        Assert.assertFalse(ivrContactRecordPage.createdByUserName.equals(ivrContactRecordPage.receivedByUserName), "created by userName and received by userName is not same");
        Assert.assertFalse(ivrContactRecordPage.createdByUserId.equals(ivrContactRecordPage.receivedByUserId), "created by userID and received by userID is not same");
        Assert.assertFalse(ivrContactRecordPage.createdByUserIdValue.getText().equals(ivrContactRecordPage.receivedByUserId.getText()), "created by useID value and received by userID value is not same");
        Assert.assertFalse(ivrContactRecordPage.createdByUserNameValue.getText().equals(ivrContactRecordPage.receivedByUserNameValue.getText()), "created by userName value and received by userName value is not same");
    }

    @And("I initiate IVR calldata api with the following and send POST")
    public void iInitiateIVRCalldataApiWithTheFollowingAndSendPOST(List<Map<String, String>> dataTable) {
        api.setbaseUri(marsContactRecordBase);
        api.setEndPoint(marsContactRecordendpoint);
        Map<String, String> data = dataTable.get(0);
        callDataFieldsrandomFiller(data);
        for (String keys : data.keySet()) {
            if ("others".equals(keys)) {
                break;
            } else {
                jsonData.get().put(keys, data.get(keys));
            }
        }
        callDataRequestBuilder(jsonData.get());
        api.PostAPIWithParameter(callDataPayload.get());
        api.jsonPathEvaluator.prettyPrint();
        assertNotNull(api.jsonPathEvaluator.get("ivrDetails.contactRecordId"), "contactRecordId found to be null");

        apiIvrContactId.set(api.jsonPathEvaluator.get("ivrDetails.contactRecordId").toString());
    }

    @And("I initiate IVR calldata api with the following data and send POST for DC_EB")
    public void iInitiateIVRCalldataApiWithTheFollowingDataAndSendPOSTforDCEB(List<Map<String, String>> dataTable) {
        api.setbaseUri(marsContactRecordBase);
        api.setEndPoint(marsContactRecordendpoint);
        Map<String, String> data = dataTable.get(0);
        callDataFieldsrandomFillerForDCEB(data);
        for (String keys : data.keySet()) {
            if ("others".equals(keys)) {
                break;
            } else {
                jsonData.get().put(keys, data.get(keys));
            }
        }
        callDataRequestBuilder(jsonData.get());
        api.PostAPIWithParameter(callDataPayload.get());
        api.jsonPathEvaluator.prettyPrint();
        assertEquals(api.jsonPathEvaluator.get("responseCode"), "200");
    }

    @And("I verify the response for DC_EB ivr calldata")
    public void i_verify_the_response_for_DC_EB_ivr_calldata() {
        waitFor(2);
        assertEquals(api.jsonPathEvaluator.get("ivrDetails.tenant"),"DC-EB");
        assertNull(api.jsonPathEvaluator.get("ivrDetails.contactRecordId"), "contactRecordId is not null");
        assertEquals(api.jsonPathEvaluator.get("responseCode"), "200");
        assertEquals(api.jsonPathEvaluator.get("responseStatus"), "success");
    }

    private void callDataRequestBuilder(Map<String, String> keysToOverride) {
        for (String key : keysToOverride.keySet()) {
            switch (key) {
                case "tenant":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "interactionId":
                    if ("RANDOM".equals(keysToOverride.get(key))) {
                        interactionId.set(RandomStringUtils.randomNumeric(5));
                        callDataPayload.get().addProperty(key, interactionId.get());
                    } else {
                        callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "consumerPhone":
                    if ("RANDOM".equals(keysToOverride.get(key))) {
                        contactPhoneNumber.set("7" + RandomStringUtils.randomNumeric(9));
                        callDataPayload.get().addProperty(key, contactPhoneNumber.get());
                    } else {
                        callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "surveyPhoneNumber":
                    if ("RANDOM".equals(keysToOverride.get(key))) {
                        surveyPhoneNumber.set("7" + RandomStringUtils.randomNumeric(9));
                        callDataPayload.get().addProperty(key, surveyPhoneNumber.get());
                    } else {
                        callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "consumerFirstName":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "consumerLastName":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "consumerSSN":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "consumerDateOfBirth":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "consumerIdType":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "consumerID":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "caseIdType":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "caseID":
                    callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    break;
                case "applicationId":
                    if ("RANDOM".equals(keysToOverride.get(key))) {
                        appId.set(RandomStringUtils.randomNumeric(5));
                        callDataPayload.get().addProperty(key, appId.get());
                    } else {
                        callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "createdOn":
                    if ("CURRENT".equals(keysToOverride.get(key))) {
                        callDataPayload.get().addProperty(key, currentTimeStamp.get());
                    } else {
                        callDataPayload.get().addProperty(key, keysToOverride.get(key));
                    }
                    break;
            }
        }
    }

    private void callDataFieldsrandomFiller(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "others":
                    if ("njsbe random filled".equals(data.get(key))) {
                        jsonData.get().put("interactionId", "RANDOM");
                        jsonData.get().put("consumerPhone", "RANDOM");
                        jsonData.get().put("surveyPhoneNumber", "RANDOM");
                        jsonData.get().put("consumerIdType", "Medicaid");
                        jsonData.get().put("consumerID", "Incomplete App");
                        jsonData.get().put("applicationId", "RANDOM");
                    }
                    break;
            }
        }
    }
    private void callDataFieldsrandomFillerForDCEB(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "others":
                    if ("dc_eb random filled".equals(data.get(key))) {
                        jsonData.get().put("callType", "Inbound");
                        jsonData.get().put("inboundCallQueue", "queueinbound");
                        jsonData.get().put("contactCampaignType", "DMI");
                        jsonData.get().put("contactChannelType", "Phone");
                        jsonData.get().put("surveyPhoneNumber", "RANDOM");
                        jsonData.get().put("consumerIdType", "Medicaid");
                        jsonData.get().put("consumerID", "Incomplete App");
                        jsonData.get().put("caseIdType", "Medicaid");
                        jsonData.get().put("caseID", "SBE100000189");
                        jsonData.get().put("createdOn", "2023-06-09T00:00:00.274000+00:00");
                        jsonData.get().put("createdBy", "IVRUser");
                    }
                    break;
            }
        }
    }

    @And("I initiate cases external api with the following and send PUT")
    public void iInitiateCasesExternalApiWithTheFollowingAndSendPUT(List<Map<String, String>> dataTable) {
        api.setbaseUri(caseLoaderBaseURI);
        api.setEndPoint(externalEndpoint);
        Map<String, String> keysToOverride = dataTable.get(0);
        JsonObject request = new JsonObject();
        for (String key : keysToOverride.keySet()) {
            switch (key) {
                case "externalCaseId":
                    JsonArray exCaseId = new JsonArray();
                    exCaseId.add(keysToOverride.get(key));
                    request.add(key, exCaseId);
                    break;
                case "createdBy":
                    request.addProperty(key, keysToOverride.get(key));
                    break;
            }
        }
        api.PutAPI(request);
        assertNotNull(api.jsonPathEvaluator.get("ids[0].caseId"), "Case Id is found null in response");
        apiCaseId.set(api.jsonPathEvaluator.get("ids[0].caseId"));
    }

    @And("I initiated casemember api with {string} and send GET")
    public void iInitiatedCasememberApiWithAndSendGET(String idType) {
        api.setbaseUri(caseConsumerAPI);
        switch (idType) {
            case "ID FROM EXTERNAL":
                api.setEndPoint(appCasememberEndpoint + apiCaseId.get());
                break;
            default:
                api.setEndPoint(appCasememberEndpoint + idType);
        }
        api.getAPI();
        assertNotNull(api.jsonPathEvaluator.get("result[0].consumers[0].caseConsumers[0].caseId"), "caseId found to be null");
        apiIvrcaseIdfromGet.set(api.jsonPathEvaluator.get("result[0].consumers[0].caseConsumers[0].caseId").toString());
    }

    @When("I initiate crm link api with the following and send PUT")
    public void iInitiateCrmLinkApiWithTheFollowingAndSendPUT(List<Map<String, String>> data) {
        api.setbaseUri(taskManagementURI);
        api.setEndPoint(marsCrmLinkEndpoint);
        Map<String, String> keysToOverride = data.get(0);
        for (String key : keysToOverride.keySet()) {
            switch (key) {
                case "correlationId":
                    switch (keysToOverride.get(key)) {
                        case "exInteractionId":
                            linkDataPayload.get().getAsJsonObject().addProperty(key, interactionId.get());
                            break;
                        default:
                            linkDataPayload.get().getAsJsonObject().addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "internalId":
                    switch (keysToOverride.get(key)) {
                        case "from external api":
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, apiIvrContactId.get());
                            break;
                        default:
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, keysToOverride.get(key));
                    }
                default:
                    break;
                case "externalId":
                    switch (keysToOverride.get(key)) {
                        case "from get caseId":
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, apiIvrcaseIdfromGet.get());
                            break;
                        default:
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "createdOn":
                    switch (keysToOverride.get(key)) {
                        case "CURRENT":
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, currentTimeStamp.get());
                            break;
                        default:
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "updatedOn":
                    switch (keysToOverride.get(key)) {
                        case "CURRENT":
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, currentTimeStamp.get());
                            break;
                        default:
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "effectiveStartDate":
                    switch (keysToOverride.get(key)) {
                        case "CURRENT":
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, currentTimeStamp.get());
                            break;
                        default:
                            linkDataPayload.get().getAsJsonObject("externalLinkPayload").addProperty(key, keysToOverride.get(key));
                    }
                    break;
                case "uiid":
                    switch (keysToOverride.get(key)) {
                        case "random":
                            uiid.set(RandomStringUtils.randomNumeric(5));
                            linkDataPayload.get().getAsJsonObject().addProperty(key, uiid.get());
                            break;
                        default:
                            linkDataPayload.get().getAsJsonObject().addProperty(key, keysToOverride.get(key));
                    }
                    break;
            }
        }
        api.PutAPI(linkDataPayload.get());
        assertEquals(api.jsonPathEvaluator.getString("status"), "success");
    }

    @Then("I verify the successful contact record to consumer link in the LINK EVENT history")
    public void iVerifyTheSuccessfulContactRecordToConsumerLinkInTheLINKEVENTHistory() {
        waitFor(15);
        api.setbaseUri(marsEventURI);
        api.setEndPoint(marsEventEndpoint);
        JsonObject request = new JsonObject();
        request.addProperty("eventName", "LINK_EVENT");
        api.PostAPIWithParameter(request);
        api.jsonPathEvaluator.prettyPrint();
        assertEquals(api.statusCode, 200);
        assertEquals(api.jsonPathEvaluator.get("status"), "success");
        boolean linkEventOne = false;
        boolean linkEventTwo = false;
        List<Map<String, Object>> events = new ArrayList<>();
        Map<String, Object> content = api.jsonPathEvaluator.getJsonObject("eventsList");
        events = (ArrayList) content.get("content");
        for (Map<String, Object> event : events) {
            Map<String, Object> payload = new Gson().fromJson(event.get("payload").toString(), HashMap.class);
            Map<String, Object> dataObject = (LinkedTreeMap) payload.get("dataObject");
            Map<String, Object> externalLinkPayload = (LinkedTreeMap) dataObject.get("externalLinkPayload");
            int internalId = (int) Math.round((Double) externalLinkPayload.get("internalId"));
            int externalId = (int) Math.round((Double) externalLinkPayload.get("externalId"));
            String internalRefType = externalLinkPayload.get("internalRefType").toString();
            String externalRefType = externalLinkPayload.get("externalRefType").toString();
            if ("CONSUMER".equals(internalRefType) && "CONTACT_RECORD".equals(externalRefType) && String.valueOf(internalId).equals(apiCaseId.get()) && String.valueOf(externalId).equals(apiIvrContactId.get())) {
                linkEventOne = true;
            }
            if ("CONTACT_RECORD".equals(internalRefType) && "CONSUMER".equals(externalRefType) && String.valueOf(externalId).equals(apiCaseId.get()) && String.valueOf(internalId).equals(apiIvrContactId.get())) {
                linkEventTwo = true;
            }
        }
        assertTrue(linkEventOne, "Contact record Link Event {internalRefType: CASE / externalRefType: CONTACT_RECORD} Not Found");
        assertTrue(linkEventTwo, "Contact record Link Event {internalRefType: CONTACT_RECORD / externalRefType: CASE} Not Found");
    }

    public JsonObject getRequestbodyFromTestData(String filePath) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile(filePath);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @When("I search community outreach session with ID from API response")
    public void i_search_community_outreach_session_with_ID_from_API_response() {
        waitFor(2);
        communityOutreach.sessionID.click();
        waitFor(3);
        communityOutreach.sessionID.sendKeys(apicommunityOutreachId.get().toString());
        waitFor(2);
        communityOutreach.searchButton.click();
    }

    @When("I click on first found community outreach session")
    public void i_click_on_first_found_community_outreach_session() {
        communityOutreach.firstFoundContactRecordFromSearch(apicommunityOutreachId.get()).click();
    }

    @And("I get oneLoginJWT token")
    public void i_get_onelogin_jwt_token() {
        List<String> list = EventsUtilities.getRawLogs("onelogin-jwt");
        int i;
        //System.out.print(list);
        for (i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (str.contains("\":method\":\"POST"))
                break;
        }
        String logs = list.get(i).toString();
        oneLoginJWT_token.set(EventsUtilities.parseOneloginJWT(logs));
        System.out.println("OneLogin JWT token is: " + oneLoginJWT_token.get());
        api.tokenRequestWithOneLoginJWTToken();
    }

    @Then("I click on Calendar button and able to see the Export button")
    public void i_click_on_calendar_button_and_able_to_see_the_export_button() {
        waitFor(2);
        communityOutreach.calendarViewButton.click();
        waitFor(2);
        assertTrue(communityOutreach.exportButton.isDisplayed(), "Export button is not displayed");
    }

    @And("I verify the search results date")
    public void i_verify_the_search_results_date() {
        waitFor(2);
        String monthAndYear = new SimpleDateFormat("MMMMMMMMM").format(Calendar.getInstance().getTime()) + " " + Calendar.getInstance().get(Calendar.YEAR);
        assertEquals(communityOutreach.communityOutreachSearchDate.getText() , Driver.getDriver().findElement(By.xpath("//h2[contains(text(), '" + monthAndYear + "')][1]")).getText());
    }
}